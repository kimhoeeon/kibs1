package com.mtf.kibs.scheduler;

import com.mtf.kibs.mapper.AiClippingMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AI 클리핑 공용 헬퍼 (OpenAI 호출, 날짜·링크·제목 정규화, 유사도, 본문·언론사 수집, 선거 규칙, 최근 호 중복 데이터).
 * AiClippingPipelineV2 가 사용한다.
 */
@Component
public class AiClippingSupport {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36";

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${openai.model.summarize}")
    private String openAiModel;

    @Value("${ai.clipping.election-dates:2028-04-12,2030-06-03}")
    private String electionDates;

    @Value("${ai.clipping.election-window-days:90}")
    private int electionWindowDays;

    /** 이 일수 내 생성된 클리핑(미노출 포함, 삭제 제외)에 실린 기사 링크·제목을 다음 생성에서 제외 */
    @Value("${ai.clipping.dedupe-days:60}")
    private int dedupeDays;

    public String getOpenAiModel() {
        return openAiModel;
    }

    public int getDedupeDays() {
        return dedupeDays;
    }

    // ------------------------------------------------------------------
    // OpenAI
    // ------------------------------------------------------------------

    /** Chat Completions 호출. 실패 시 null */
    public String callOpenAI(String prompt, int maxTokens, double temperature) {
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", Collections.singletonList(message));
        body.put("max_completion_tokens", maxTokens);
        body.put("temperature", temperature);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> messageResp = (Map<String, Object>) choices.get(0).get("message");
                return (String) messageResp.get("content");
            }
        } catch (Exception e) {
            System.err.println("[AI 클리핑 V2] OpenAI API 호출 에러: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------------------------------------------
    // 날짜 / 링크 / 제목 정규화
    // ------------------------------------------------------------------

    /** "2026.10.20", "3시간 전", "어제", "2일 전", "1주 전", "2개월 전" → LocalDate (해석 불가 시 null) */
    public LocalDate parseArticleDate(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        String t = text.trim();
        LocalDate today = LocalDate.now();
        try {
            if (t.matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.?")) {
                if (t.endsWith(".")) t = t.substring(0, t.length() - 1);
                return LocalDate.parse(t, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            }
            if (t.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(t);
            }
            if (t.contains("어제")) return today.minusDays(1);
            Matcher m = Pattern.compile("(\\d+)\\s*(시간|분|일|주|개월)\\s*전").matcher(t);
            if (m.find()) {
                int n = Integer.parseInt(m.group(1));
                switch (m.group(2)) {
                    case "분":
                    case "시간":
                        return today;
                    case "일":
                        return today.minusDays(n);
                    case "주":
                        return today.minusWeeks(n);
                    case "개월":
                        return today.minusMonths(n);
                    default:
                        return null;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /** 링크 비교용 정규화: 스킴·공백·쿼리스트링·해시·끝 슬래시 제거, 소문자 */
    public String normalizeLink(String link) {
        if (link == null) return "";
        String l = link.trim();
        l = l.replaceFirst("^https?://", "");
        int q = l.indexOf('?');
        if (q >= 0) l = l.substring(0, q);
        int h = l.indexOf('#');
        if (h >= 0) l = l.substring(0, h);
        while (l.endsWith("/")) l = l.substring(0, l.length() - 1);
        return l.toLowerCase();
    }

    /** 제목 앞의 말머리 제거: (포토) [기고] [속보] [인터뷰] 【단독】 등 괄호류로 시작하는 접두어를 모두 제거 */
    public String stripTitlePrefix(String title) {
        if (title == null) return "";
        String t = title.trim();
        String prev;
        do {
            prev = t;
            t = t.replaceFirst("^\\s*[\\[\\(（【〔<《][^\\]\\)）】〕>》]{0,20}[\\]\\)）】〕>》]\\s*", "");
        } while (!t.equals(prev));
        return t.trim();
    }

    /** 유사도 비교용 제목 정규화: 말머리 제거 → 공백·문장부호 제거 → 소문자 */
    public String normalizeTitleForSimilarity(String title) {
        String t = stripTitlePrefix(title);
        t = t.replaceAll("[\\s\\p{Punct}·…‘’“”\"'~ㆍ]", "");
        return t.toLowerCase();
    }

    /** 두 문자열의 유사도 (0.0~1.0) = 1 - 편집거리/최대길이 */
    public double similarity(String a, String b) {
        if (a == null || b == null) return 0.0;
        if (a.equals(b)) return 1.0;
        int la = a.length(), lb = b.length();
        if (la == 0 || lb == 0) return 0.0;
        int[] prev = new int[lb + 1];
        int[] cur = new int[lb + 1];
        for (int j = 0; j <= lb; j++) prev[j] = j;
        for (int i = 1; i <= la; i++) {
            cur[0] = i;
            char ca = a.charAt(i - 1);
            for (int j = 1; j <= lb; j++) {
                int cost = (ca == b.charAt(j - 1)) ? 0 : 1;
                cur[j] = Math.min(Math.min(cur[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            int[] tmp = prev; prev = cur; cur = tmp;
        }
        int dist = prev[lb];
        return 1.0 - ((double) dist / Math.max(la, lb));
    }

    /** 두 문자열의 최장 공통 부분문자열 길이 (같은 사안 판정용: "해양모빌리티안전엑스포" 같은 고유 명사 공유 감지) */
    public int longestCommonSubstring(String a, String b) {
        if (a == null || b == null || a.isEmpty() || b.isEmpty()) return 0;
        int la = a.length(), lb = b.length(), best = 0;
        int[] prev = new int[lb + 1];
        int[] cur = new int[lb + 1];
        for (int i = 1; i <= la; i++) {
            char ca = a.charAt(i - 1);
            for (int j = 1; j <= lb; j++) {
                if (ca == b.charAt(j - 1)) {
                    cur[j] = prev[j - 1] + 1;
                    if (cur[j] > best) best = cur[j];
                } else {
                    cur[j] = 0;
                }
            }
            int[] tmp = prev; prev = cur; cur = tmp;
        }
        return best;
    }

    // ------------------------------------------------------------------
    // 언론사명 / 본문 수집
    // ------------------------------------------------------------------

    /** 기사 페이지의 og:site_name 으로 언론사명 확정 (실패 시 fallback) */
    public String resolvePublisher(String link, String fallback) {
        try {
            Document doc = Jsoup.connect(link).userAgent(USER_AGENT).timeout(3000).get();
            Element og = doc.selectFirst("meta[property=og:site_name]");
            if (og != null) {
                String p = og.attr("content").replace("Daum", "").replace("|", "").trim();
                if (!p.isEmpty() && !p.matches("^[0-9\\s\\p{P}]+$")) return p;
            }
        } catch (Exception ignored) {
        }
        return (fallback == null || fallback.isEmpty()) ? "언론사" : fallback;
    }

    /**
     * 기사 본문 텍스트 수집 (요약 생성에만 일시 사용, DB 저장 금지).
     * 다음 뉴스(v.daum.net) 구조를 우선 시도하고, 그 외는 본문 후보 블록 중 가장 긴 텍스트를 사용한다.
     * 실패 시 빈 문자열.
     */
    public String fetchArticleBody(String link, int maxChars) {
        return fetchArticle(link, maxChars)[1];
    }

    /**
     * 기사 페이지를 1회 접속해 언론사명(og:site_name)과 본문을 함께 얻는다.
     * @return [0]=publisher(없으면 null), [1]=body(실패 시 빈 문자열)
     */
    public String[] fetchArticle(String link, int maxChars) {
        String publisher = null;
        try {
            Document doc = Jsoup.connect(link).userAgent(USER_AGENT).timeout(6000).get();
            Element ogSite = doc.selectFirst("meta[property=og:site_name]");
            if (ogSite != null) {
                String p = ogSite.attr("content").replace("Daum", "").replace("|", "").trim();
                if (!p.isEmpty() && !p.matches("^[0-9\\s\\p{P}]+$")) publisher = p;
            }
            String text = "";

            // 1) 다음 뉴스 본문
            Element daum = doc.selectFirst("#harmonyContainer, div.article_view, section[dmcf-sid]");
            if (daum != null) text = daum.select("p, h3, li").text();

            // 2) 일반적인 본문 컨테이너 후보
            if (text.trim().length() < 200) {
                String[] selectors = {"article", "#articleBody", "#article-body", "#articeBody", "#newsEndContents",
                        ".article_body", ".article-body", ".news_body", ".news-body", "#content .text", "#articleText", ".view_txt", "#news_body_area"};
                for (String sel : selectors) {
                    Element el = doc.selectFirst(sel);
                    if (el != null) {
                        String t = el.text();
                        if (t.length() > text.length()) text = t;
                    }
                }
            }

            // 3) 최후: 문단 텍스트 중 가장 긴 부모 블록
            if (text.trim().length() < 200) {
                String best = "";
                for (Element p : doc.select("p")) {
                    Element parent = p.parent();
                    if (parent == null) continue;
                    String t = parent.select("p").text();
                    if (t.length() > best.length()) best = t;
                }
                if (best.length() > text.length()) text = best;
            }

            // 4) og:description 이라도
            if (text.trim().length() < 80) {
                Element og = doc.selectFirst("meta[property=og:description]");
                if (og != null) text = og.attr("content");
            }

            text = text.replaceAll("\\s+", " ").trim();
            if (maxChars > 0 && text.length() > maxChars) text = text.substring(0, maxChars);
            return new String[]{publisher, text};
        } catch (Exception e) {
            System.err.println("[AI 클리핑 V2] 본문 수집 실패 (" + link + "): " + e.getMessage());
            return new String[]{publisher, ""};
        }
    }

    // ------------------------------------------------------------------
    // 선거 규칙 / 최근 호 중복
    // ------------------------------------------------------------------

    /** 선거 관련 배제 규칙 문구 (항상 + 선거 90일 전 강화) */
    public String buildElectionRule() {
        StringBuilder sb = new StringBuilder();
        sb.append("선거 후보자·예비후보자·출마 예정자의 공약, 유세, 지역 방문 행보, 인터뷰 기사는 해양레저·해양관광 정책 내용이 있어도 0점.");

        LocalDate today = LocalDate.now();
        LocalDate active = null;
        if (electionDates != null) {
            for (String s : electionDates.split(",")) {
                String t = s.trim();
                if (t.isEmpty()) continue;
                try {
                    LocalDate d = LocalDate.parse(t);
                    LocalDate start = d.minusDays(Math.max(0, electionWindowDays));
                    if (!today.isBefore(start) && !today.isAfter(d)) { active = d; break; }
                } catch (Exception ignored) {
                }
            }
        }
        if (active != null) {
            sb.append(" 특히 지금은 ").append(active).append(" 선거일 기준 ").append(electionWindowDays)
              .append("일 전 선거 기간이므로, 현직 도지사·시장·군수·국회의원·지방의원이 등장하는 기사도 공약성·치적 홍보성이면 0점, ")
              .append("기관(도청·시청·군청·부처·공사) 명의로 발표된 정책·사업 공고·인프라 조성 기사만 점수를 줘.");
        }
        return sb.toString();
    }

    /**
     * 최근 dedupeDays 일 내 클리핑 본문에서 외부 기사 링크(정규화)와 출처 제목을 추출한다.
     * @return [0]=Set<String> links(normalized), [1]=List<String> titles(최대 maxTitles)
     */
    public Object[] loadRecentlyUsed(int maxTitles) {
        Set<String> links = new HashSet<>();
        List<String> titles = new ArrayList<>();
        if (dedupeDays <= 0) return new Object[]{links, titles};
        try {
            List<String> contents = aiClippingMapper.selectRecentClippingContents(dedupeDays);
            if (contents == null) return new Object[]{links, titles};
            Pattern anchor = Pattern.compile("<a\\s+[^>]*href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>(.*?)</a>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Set<String> titleSeen = new HashSet<>();
            for (String content : contents) {
                if (content == null) continue;
                Set<String> inThis = new HashSet<>();
                Matcher m = anchor.matcher(content);
                while (m.find()) {
                    String url = m.group(1).trim();
                    if (!url.startsWith("http") || url.contains("kibs.com")) continue;
                    String norm = normalizeLink(url);
                    links.add(norm);
                    if (inThis.add(norm) && titles.size() < maxTitles) {
                        String title = m.group(2).replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").trim();
                        if (title.length() >= 8 && titleSeen.add(title)) titles.add(title);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[AI 클리핑 V2] 최근 클리핑 링크/제목 조회 실패: " + e.getMessage());
        }
        return new Object[]{links, titles};
    }
}
