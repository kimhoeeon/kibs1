package com.mtf.kibs.scheduler;

import com.mtf.kibs.dto.AiClippingArticleDTO;
import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.AiClippingKeywordDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.service.NewsletterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AI 뉴스 클리핑 V2 파이프라인 (2026-10-28 발주사 요청).
 *
 * <pre>
 * 월 08:00 생성 → 월 10:00 노출+발송 (스케줄 진입점은 AiClippingScheduler, 승인 필수 여부는 ai.clipping.publish-requires-approval)
 *
 * 1. 수집      : 등록 키워드(등록 순) × Daum 뉴스 최신순. 수집 창 = 지난 월요일 08:00 ~ 지금 (날짜 단위 필터 + 링크 중복 제거)
 * 2. 전처리    : 이전 호 링크 제외 → 링크 중복 제거 → 말머리 제거 후 제목 유사도(≥ threshold) 중복 제거 → 제목 블랙리스트 → EXCLUDED_BLACKLIST
 * 3. 연관도    : GPT 가 3대 축 기준 0~100 점 (사고·구조, 해외 관광지/카지노, 연예·가십·정치인 개인, 군함·잠수함, 선거 규칙 = 0점). 0점 → EXCLUDED_SCORE, 나머지 LISTED
 * 4. 본문      : 상위 N건 본문 접속(저장 안 함) → GPT 토픽(최대 5개, [태그] 30자 제목 + 2문단 + 출처) → SELECTED
 * 5. 렌더·저장 : 기존 레이아웃(■ 기사 출처 / 타이틀 밴드 / 섹션 / 푸터) HTML → ai_clipping (display_yn=N, approve_yn=N) → ai_clipping_article 기사별 저장
 * </pre>
 *
 * "최신 관련 기사" 영역은 메일 본문에는 넣지 않고 공개 상세(/board/clippingView.do)에서 ai_clipping_article 로 그린다.
 */
@Component
public class AiClippingPipelineV2 {

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private NewsletterService newsletterService;

    @Autowired
    private AiClippingSupport support;

    /** 브리핑 토픽 목표 수 */
    @Value("${ai.clipping.v2.target-topics:5}")
    private int targetTopics;

    /** 키워드당 탐색할 Daum 검색 결과 페이지 수(페이지당 10건 내외). 수집 창보다 오래된 기사만 나오는 페이지에서 조기 종료 */
    @Value("${ai.clipping.v2.pages-per-keyword:3}")
    private int pagesPerKeyword;

    /** 목록 페이지 간 딜레이(ms) */
    @Value("${daum.search.list-delay-min-ms:1000}")
    private int listDelayMinMs;

    @Value("${daum.search.list-delay-max-ms:2000}")
    private int listDelayMaxMs;

    /** 본문 접속 간 딜레이(ms) */
    @Value("${daum.search.body-delay-min-ms:2000}")
    private int bodyDelayMinMs;

    @Value("${daum.search.body-delay-max-ms:4000}")
    private int bodyDelayMaxMs;

    /** 수집 창 기준 요일/시각 (기본 월요일 08:00). 기준 시각이 지금으로부터 window-min-hours 이내면 한 주 전 기준을 쓴다 */
    @Value("${ai.clipping.v2.window-anchor-day:MONDAY}")
    private String windowAnchorDay;

    @Value("${ai.clipping.v2.window-anchor-hour:8}")
    private int windowAnchorHour;

    @Value("${ai.clipping.v2.window-min-hours:24}")
    private int windowMinHours;

    /** 제목 유사도 중복 판정 기준 (0~1) */
    @Value("${ai.clipping.v2.similarity-threshold:0.8}")
    private double similarityThreshold;

    /** GPT 에 넘기기 전 제목 블랙리스트 (쉼표 구분). 하나라도 포함되면 EXCLUDED_BLACKLIST */
    @Value("${ai.clipping.title-blacklist:여야,탄핵,선거,출마,총선,군함,잠수함,당대표,원내대표,카지노,마리나베이샌즈}")
    private String titleBlacklist;

    /** 토픽 제목 앞 [분야 태그] 고정 목록 (쉼표 구분) */
    @Value("${ai.clipping.topic-tags:레저선박,마리나 인프라,해양관광,해양레저 정책,전시·행사,기술·제품,산업동향}")
    private String topicTags;

    /** GPT 점수 산정 시 한 번에 넘기는 기사 수 */
    @Value("${ai.clipping.v2.scoring-chunk-size:50}")
    private int scoringChunkSize;

    /**
     * 이 점수 미만은 EXCLUDED_SCORE. 기본 10.
     * 채점 프롬프트가 "연결 근거 없으면 0점, 있으면 10점 이상(1~9점 금지)" 이므로 10 은 사실상 0점만 제외하는 값이면서,
     * 모델이 규칙을 어기고 준 1~9점(아예 상관없는 기사에 애매하게 준 점수)까지 함께 걸러내는 안전망이다.
     * 사용자 결정(2026-10-28): 사고·연예는 물론 해양레저와 아예 상관없는 기사는 여지없이 제외. 단, 해양 분야의 주변 기사(10~29점)는 남긴다.
     */
    @Value("${ai.clipping.v2.min-score:10}")
    private int minScore;

    /** 본문 수집 대상(상위 N건) 선정 시 같은 사안으로 보이는 기사는 최대 이 건수까지만 (토픽 다양성 확보) */
    @Value("${ai.clipping.v2.max-per-issue:2}")
    private int maxPerIssue;

    /** 같은 사안 판정: 정규화 제목의 최장 공통 부분문자열 길이가 이 값 이상 */
    @Value("${ai.clipping.v2.same-issue-min-common:8}")
    private int sameIssueMinCommon;

    /** 본문을 접속해 토픽 생성에 넘길 상위 기사 수 */
    @Value("${ai.clipping.v2.body-fetch-count:10}")
    private int bodyFetchCount;

    /** 본문 최대 글자 수 (프롬프트 길이 제한용) */
    @Value("${ai.clipping.v2.body-max-chars:3000}")
    private int bodyMaxChars;

    /** 프롬프트에 넘길 "최근 호에 이미 다룬 기사" 제목 최대 수 */
    private static final int MAX_RECENT_TITLES_FOR_PROMPT = 60;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DOT_DATE_FMT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    // ------------------------------------------------------------------
    // 진입점
    // ------------------------------------------------------------------

    /**
     * 클리핑 1건 생성. 성공 시 생성된 ai_clipping.seq, 실패(기사 없음·오류) 시 null.
     * @param isSend true 면 생성 직후 구독자 발송까지 수행 (관리자 수동 "생성+발송")
     */
    public String generate(boolean isSend) {
        System.out.println("========== [AI 클리핑 V2] 생성 시작 (즉시 발송: " + isSend + ") ==========");
        try {
            List<AiClippingKeywordDTO> keywordList = aiClippingMapper.selectKeywordList();
            if (keywordList == null || keywordList.isEmpty()) {
                System.out.println("[AI 클리핑 V2] 등록된 키워드가 없어 종료합니다.");
                return null;
            }
            List<String> keywords = new ArrayList<>();
            for (AiClippingKeywordDTO kw : keywordList) {
                if (kw.getKeyword() != null && !kw.getKeyword().trim().isEmpty() && !keywords.contains(kw.getKeyword().trim())) {
                    keywords.add(kw.getKeyword().trim());
                }
            }

            LocalDateTime windowStart = resolveWindowStart(LocalDateTime.now(KST));
            LocalDate windowStartDate = windowStart.toLocalDate();
            System.out.println("[AI 클리핑 V2] 수집 창: " + windowStart + " ~ 지금, 키워드 " + keywords.size() + "개");

            // 이전 호 중복 방지 데이터
            Object[] recent = support.loadRecentlyUsed(MAX_RECENT_TITLES_FOR_PROMPT);
            @SuppressWarnings("unchecked") Set<String> previousLinks = (Set<String>) recent[0];
            @SuppressWarnings("unchecked") List<String> previousTitles = (List<String>) recent[1];
            System.out.println("[AI 클리핑 V2] 최근 " + support.getDedupeDays() + "일 호에 실린 링크 " + previousLinks.size() + "건 제외, 제목 " + previousTitles.size() + "건을 '이미 다룬 사안'으로 전달");

            // 1. 수집
            List<AiClippingArticleDTO> collected = new ArrayList<>();
            for (String keyword : keywords) {
                collected.addAll(collectFromDaum(keyword, windowStartDate));
                Thread.sleep(randomDelay(listDelayMinMs, listDelayMaxMs));
            }
            System.out.println("[AI 클리핑 V2] 수집 " + collected.size() + "건");

            // 2. 전처리 (이전 호 링크 → 링크 중복 → 제목 유사도 → 블랙리스트)
            List<AiClippingArticleDTO> excluded = new ArrayList<>();
            List<AiClippingArticleDTO> candidates = preprocess(collected, previousLinks, excluded);
            System.out.println("[AI 클리핑 V2] 전처리 후 후보 " + candidates.size() + "건 (블랙리스트 제외 " + excluded.size() + "건)");

            if (candidates.isEmpty()) {
                System.out.println("[AI 클리핑 V2] 수집 창 내 후보 기사가 없어 종료합니다.");
                return null;
            }

            // 3. 연관도 점수
            scoreArticles(candidates, previousTitles);
            List<AiClippingArticleDTO> listed = new ArrayList<>();
            for (AiClippingArticleDTO a : candidates) {
                if (a.getRelevanceScore() < minScore) {
                    a.setStatus(AiClippingArticleDTO.STATUS_EXCLUDED_SCORE);
                    excluded.add(a);
                } else {
                    a.setStatus(AiClippingArticleDTO.STATUS_LISTED);
                    listed.add(a);
                }
            }
            System.out.println("[AI 클리핑 V2] 연관도 통과 " + listed.size() + "건 / 0점 제외 " + (candidates.size() - listed.size()) + "건");

            if (listed.isEmpty()) {
                System.out.println("[AI 클리핑 V2] 연관도 기준을 통과한 기사가 없어 종료합니다.");
                return null;
            }

            // 점수 내림차순, 같은 점수면 최신순
            listed.sort((a, b) -> {
                int c = Integer.compare(b.getRelevanceScore(), a.getRelevanceScore());
                if (c != 0) return c;
                return String.valueOf(b.getArticleDate()).compareTo(String.valueOf(a.getArticleDate()));
            });

            // 4. 상위 N건 본문 수집 → 토픽 생성 (같은 사안은 maxPerIssue 건까지만 뽑아 토픽 다양성 확보)
            List<AiClippingArticleDTO> top = pickDiverseTop(listed, bodyFetchCount);
            System.out.println("[AI 클리핑 V2] 본문 수집 대상 " + top.size() + "건 선정 (같은 사안 최대 " + maxPerIssue + "건)");
            List<String> bodies = new ArrayList<>();
            for (int i = 0; i < top.size(); i++) {
                AiClippingArticleDTO a = top.get(i);
                String[] fetched = support.fetchArticle(a.getLink(), bodyMaxChars);
                if (fetched[0] != null && !fetched[0].isEmpty()) a.setPublisher(fetched[0]);
                String body = fetched[1];
                if (body == null || body.trim().isEmpty()) body = a.getSummary() == null ? "" : a.getSummary();
                bodies.add(body);
                if (i < top.size() - 1) Thread.sleep(randomDelay(bodyDelayMinMs, bodyDelayMaxMs));
            }

            List<Topic> topics = generateTopics(top, bodies, previousTitles);
            if (topics.isEmpty()) {
                System.out.println("[AI 클리핑 V2] 토픽 생성 결과가 비어 있어 종료합니다.");
                return null;
            }

            // SELECTED 표시
            List<AiClippingArticleDTO> sources = new ArrayList<>();
            for (int t = 0; t < topics.size(); t++) {
                Topic topic = topics.get(t);
                for (Integer idx : topic.sourceIndexes) {
                    if (idx < 1 || idx > top.size()) continue;
                    AiClippingArticleDTO a = top.get(idx - 1);
                    if (!AiClippingArticleDTO.STATUS_SELECTED.equals(a.getStatus())) {
                        a.setStatus(AiClippingArticleDTO.STATUS_SELECTED);
                        a.setTopicNo(t + 1);
                        sources.add(a);
                    }
                }
            }
            if (sources.isEmpty()) {
                // 출처 번호를 못 받은 경우 상위 기사를 출처로 사용
                for (int i = 0; i < Math.min(topics.size(), top.size()); i++) {
                    AiClippingArticleDTO a = top.get(i);
                    a.setStatus(AiClippingArticleDTO.STATUS_SELECTED);
                    a.setTopicNo(i + 1);
                    sources.add(a);
                }
            }

            // 5. 렌더 → 저장
            String title = "[경기국제보트쇼 AI 클리핑] " + LocalDate.now(KST).format(DATE_FMT) + " 해양레저산업 주요 동향";
            String content = renderNewsletter(topics, sources, keywords);

            AiClippingDTO clipping = new AiClippingDTO();
            clipping.setTitle(title);
            clipping.setContent(content);
            int inserted = aiClippingMapper.insertAiClipping(clipping);
            if (inserted <= 0 || clipping.getSeq() == null) {
                System.out.println("[AI 클리핑 V2] ai_clipping 저장 실패");
                return null;
            }
            Long clippingSeq = Long.parseLong(clipping.getSeq());

            int saved = 0;
            List<AiClippingArticleDTO> all = new ArrayList<>(listed);
            all.addAll(excluded);
            for (AiClippingArticleDTO a : all) {
                a.setClippingSeq(clippingSeq);
                try {
                    aiClippingMapper.insertClippingArticle(a);
                    saved++;
                } catch (Exception e) {
                    System.err.println("[AI 클리핑 V2] 기사 저장 실패 (" + a.getLink() + "): " + e.getMessage());
                }
            }
            System.out.println("========== [AI 클리핑 V2] 생성 완료 SEQ=" + clippingSeq + " (토픽 " + topics.size() + ", 출처 " + sources.size()
                    + ", 관련 기사 " + listed.size() + ", 제외 " + excluded.size() + ", 기사 저장 " + saved + "건, 상태: 미노출/미승인) ==========");

            if (isSend) {
                newsletterService.sendClippingNewsletter(clipping.getSeq(), title, content);
                System.out.println("[AI 클리핑 V2] 수동 즉시 발송 완료");
            }
            return clipping.getSeq();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== [AI 클리핑 V2] 생성 중 오류 ==========");
            return null;
        }
    }

    /**
     * 월 10:00 발행: 오늘 생성된 미노출·미삭제 클리핑(requireApproval 이면 승인된 것만) 중 최신 1건을 노출하고 발송.
     * @return 발행한 seq, 대상 없으면 null
     */
    public String publishToday(boolean requireApproval) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("requireApproval", requireApproval);
            List<AiClippingDTO> list = aiClippingMapper.selectTodayPendingClippings(param);
            if (list == null || list.isEmpty()) {
                System.out.println("[AI 클리핑 V2] 오늘 발행할 " + (requireApproval ? "승인된 " : "") + "미노출 클리핑이 없습니다.");
                return null;
            }
            AiClippingDTO target = list.get(0);
            Map<String, String> p = new HashMap<>();
            p.put("seq", target.getSeq());
            p.put("displayYn", "Y");
            aiClippingMapper.updateDisplayStatus(p);
            newsletterService.sendClippingNewsletter(target.getSeq(), target.getTitle(), target.getContent());
            System.out.println("[AI 클리핑 V2] SEQ " + target.getSeq() + " 노출 전환 및 발송 완료");
            return target.getSeq();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[AI 클리핑 V2] 발행 중 오류");
            return null;
        }
    }

    // ------------------------------------------------------------------
    // 1. 수집
    // ------------------------------------------------------------------

    /** 지난 기준 요일·시각(기본 월 08:00). 지금으로부터 windowMinHours 이내면 한 주 전으로 */
    LocalDateTime resolveWindowStart(LocalDateTime now) {
        DayOfWeek day;
        try {
            day = DayOfWeek.valueOf(windowAnchorDay.trim().toUpperCase());
        } catch (Exception e) {
            day = DayOfWeek.MONDAY;
        }
        LocalDateTime start = now.toLocalDate().with(TemporalAdjusters.previousOrSame(day)).atTime(Math.max(0, Math.min(23, windowAnchorHour)), 0);
        if (start.isAfter(now) || Duration.between(start, now).toHours() < windowMinHours) {
            start = start.minusWeeks(1);
        }
        return start;
    }

    /** Daum 뉴스 최신순 검색. 수집 창 시작일 이전 기사만 나오는 페이지에서 종료 */
    private List<AiClippingArticleDTO> collectFromDaum(String keyword, LocalDate windowStartDate) {
        List<AiClippingArticleDTO> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        try {
            String encoded = URLEncoder.encode(keyword, "UTF-8");
            String sd = windowStartDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "000000";
            String ed = LocalDate.now(KST).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "235959";

            for (int page = 1; page <= Math.max(1, pagesPerKeyword); page++) {
                if (page > 1) Thread.sleep(randomDelay(listDelayMinMs, listDelayMaxMs));

                // period=u&sd&ed 는 Daum 이 기간 필터로 해석하면 범위가 줄고, 무시해도 아래 날짜 필터로 안전하다
                String url = "https://search.daum.net/search?w=news&q=" + encoded + "&sort=rec&period=u&sd=" + sd + "&ed=" + ed + "&p=" + page;
                Document doc = Jsoup.connect(url).userAgent(AiClippingSupport.USER_AGENT).timeout(5000).get();
                Elements items = doc.select(".c-item-content");
                if (items.isEmpty()) break;

                int added = 0, olderOnPage = 0;
                for (Element element : items) {
                    Element titleElement = element.select(".item-title").first();
                    if (titleElement == null) continue;
                    String title = titleElement.text().trim();
                    String summary = element.select(".conts-desc").text();
                    if (summary.contains("function()") || summary.contains("document.") || summary.contains("{") || summary.contains("}")) continue;

                    String link = "";
                    if (titleElement.tagName().equals("a")) link = titleElement.attr("href");
                    else if (titleElement.select("a").first() != null) link = titleElement.select("a").first().attr("href");
                    else if (element.select("a").first() != null) link = element.select("a").first().attr("href");
                    if (link == null || link.trim().isEmpty() || link.startsWith("#") || link.contains("search.daum.net")) continue;
                    if (!link.startsWith("http")) link = "https:" + (link.startsWith("/") ? "" : "/") + link;
                    if (!seen.add(support.normalizeLink(link))) continue;

                    String fullText = element.text().replace(title, "").replace(summary, "");
                    fullText = fullText.replace("동영상 첨부된 문서", "").replace("사진 첨부된 문서", "").replace("음성 첨부된 문서", "").replace("다음뉴스", "")
                            .replaceAll("관련기사\\s*\\d+건?", "").replaceAll("관련뉴스\\s*\\d+건?", "").replaceAll("[|·ⓒ]", " ").trim();

                    String rawDate = "";
                    Matcher m = Pattern.compile("(\\d{4}\\.\\d{2}\\.\\d{2}\\.?|\\d+\\s*(시간|분|일|주|개월)\\s*전|어제)").matcher(fullText);
                    if (m.find()) rawDate = m.group(1).trim();
                    LocalDate date = support.parseArticleDate(rawDate);
                    if (date == null) date = LocalDate.now(KST);

                    if (date.isBefore(windowStartDate)) {
                        olderOnPage++;
                        continue;
                    }

                    // 언론사명: 검색 결과 <li> 의 상단 헤더(.c-tit-doc .tit_item .txt_info)에 있다 (.c-item-content 바깥)
                    String publisher = "";
                    Element li = element;
                    while (li != null && !"li".equalsIgnoreCase(li.tagName())) li = li.parent();
                    if (li != null) {
                        Element pub = li.selectFirst(".c-tit-doc .tit_item .txt_info, .c-tit-doc .item-writer .txt_info, .c-tit-doc .tit_item");
                        if (pub != null) publisher = pub.text().trim();
                    }
                    if (publisher.isEmpty()) {
                        String leftover = rawDate.isEmpty() ? fullText : fullText.replace(rawDate, "");
                        publisher = leftover.replaceAll("(?<!\\S)\\d+(?!\\S)", "").replaceAll("\\s{2,}", " ").trim();
                    }
                    if (publisher.isEmpty() || publisher.matches("^[0-9\\s\\p{P}]+$")) publisher = "언론사";

                    AiClippingArticleDTO a = new AiClippingArticleDTO();
                    a.setKeyword(keyword);
                    a.setTitle(title.length() > 500 ? title.substring(0, 500) : title);
                    a.setLink(link.length() > 1000 ? link.substring(0, 1000) : link);
                    a.setPublisher(publisher.length() > 100 ? publisher.substring(0, 100) : publisher);
                    a.setSummary(summary);
                    a.setArticleDate(date.format(DATE_FMT));
                    result.add(a);
                    added++;
                }
                // 최신순이므로 이 페이지의 모든 기사가 창 이전이면 더 볼 필요 없음
                if (added == 0 && olderOnPage > 0) break;
                if (added == 0 && olderOnPage == 0) break;
            }
            System.out.println("   [" + keyword + "] 수집 " + result.size() + "건");
        } catch (Exception e) {
            System.err.println("[AI 클리핑 V2] Daum 수집 실패 [" + keyword + "]: " + e.getMessage());
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 2. 전처리
    // ------------------------------------------------------------------

    private List<AiClippingArticleDTO> preprocess(List<AiClippingArticleDTO> collected, Set<String> previousLinks, List<AiClippingArticleDTO> excludedOut) {
        List<String> blacklist = new ArrayList<>();
        if (titleBlacklist != null) {
            for (String s : titleBlacklist.split(",")) {
                String t = s.trim();
                if (!t.isEmpty()) blacklist.add(t);
            }
        }

        List<AiClippingArticleDTO> kept = new ArrayList<>();
        List<String> keptTitles = new ArrayList<>();
        Set<String> seenLinks = new HashSet<>();
        int prevDup = 0, linkDup = 0, titleDup = 0;

        for (AiClippingArticleDTO a : collected) {
            String norm = support.normalizeLink(a.getLink());
            if (previousLinks.contains(norm)) { prevDup++; continue; }
            if (!seenLinks.add(norm)) { linkDup++; continue; }

            String simTitle = support.normalizeTitleForSimilarity(a.getTitle());
            boolean dup = false;
            for (String kt : keptTitles) {
                if (support.similarity(simTitle, kt) >= similarityThreshold) { dup = true; break; }
            }
            if (dup) { titleDup++; continue; }

            String hit = null;
            for (String b : blacklist) {
                if (a.getTitle().contains(b)) { hit = b; break; }
            }
            if (hit != null) {
                a.setStatus(AiClippingArticleDTO.STATUS_EXCLUDED_BLACKLIST);
                a.setRelevanceScore(0);
                a.setSummary("[블랙리스트: " + hit + "] " + (a.getSummary() == null ? "" : a.getSummary()));
                excludedOut.add(a);
                keptTitles.add(simTitle); // 같은 사안의 다른 기사도 함께 걸러지도록 제목은 기억
                continue;
            }

            kept.add(a);
            keptTitles.add(simTitle);
        }
        System.out.println("[AI 클리핑 V2] 전처리: 이전 호 중복 " + prevDup + ", 링크 중복 " + linkDup + ", 제목 유사 중복 " + titleDup + ", 블랙리스트 " + excludedOut.size());
        return kept;
    }

    // ------------------------------------------------------------------
    // 3. 연관도 점수
    // ------------------------------------------------------------------

    private void scoreArticles(List<AiClippingArticleDTO> candidates, List<String> previousTitles) {
        int chunk = Math.max(5, scoringChunkSize);
        for (int from = 0; from < candidates.size(); from += chunk) {
            List<AiClippingArticleDTO> part = candidates.subList(from, Math.min(from + chunk, candidates.size()));
            Map<Integer, Integer> scores = requestScores(part, previousTitles);
            for (int i = 0; i < part.size(); i++) {
                Integer s = scores.get(i + 1);
                // 응답 누락 기사는 보수적으로 1점(목록 노출, 브리핑 비우선) 처리
                part.get(i).setRelevanceScore(s == null ? Math.max(minScore, 1) : Math.max(0, Math.min(100, s)));
            }
        }
    }

    private Map<Integer, Integer> requestScores(List<AiClippingArticleDTO> part, List<String> previousTitles) {
        StringBuilder sb = new StringBuilder();
        sb.append("너는 경기국제보트쇼(KIBS) 주간 뉴스클리핑의 편집자야. 아래 뉴스 후보 각각에 대해 '해양레저산업 비즈니스 관련도'를 0~100 점으로 매겨.\n\n");
        sb.append(buildPillarsRule());
        sb.append("\n[0점(완전 배제) 규칙 — 해당하면 반드시 0점]\n");
        sb.append("- 해상 사고·조난·구조·실종·사망·침몰·해경 적발 등 단순 사건사고 보도\n");
        sb.append("- 해외 관광지·호텔·카지노 브랜드 소식(마리나베이샌즈 등)처럼 국내 해양레저산업과 무관한 해외 관광 가십\n");
        sb.append("- 연예인·인플루언서 사생활(요트·보트 소유, 여행 포함), 가십\n");
        sb.append("- 정당·정쟁·여야 공방, 후보·예비후보·출마 예정자, 정치인 개인의 행보·발언·치적 홍보 기사 (단, 아래 정책 규칙 참고)\n");
        sb.append("- 군함·잠수함·해군 전력 등 국방·방산 기사\n");
        sb.append("- 골프·부동산·아파트 분양·일반 레저(캠핑 단독) 등 해양레저와 무관한 타 산업\n");
        sb.append("- **해양레저와 아예 상관없는 기사**: 일반 경제·증시·기업 실적·IT·자동차·국제 정세·외교·스포츠 일반(육상 경기, 모터스포츠, 프로스포츠)·문화예술·영화·공연·공시·채용·인사·부고·날씨 등. ")
          .append("검색 키워드(요트, 보트, 마리나, 해양스포츠 등)가 제목이나 요약에 우연히 들어갔을 뿐이거나, 지역명(부산·여수·제주 등)만 겹치는 기사도 0점. ")
          .append("제목과 요약에서 바다·해양·수상 레저 활동, 레저선박, 마리나, 해양관광 중 어느 하나와의 연결 근거를 찾을 수 없으면 애매하다고 낮은 점수를 주지 말고 반드시 0점을 줘.\n");
        sb.append("- ").append(support.buildElectionRule()).append("\n");
        sb.append("\n[정책 규칙]\n");
        sb.append("- 정부·지자체·공공기관 명의의 해양레저·해양관광 정책 발표, 마리나·항만·선착장 인프라 계획, 예산·지원사업, 법제화, 공식 행사(박람회·축제·간담회) 기사는 도지사·시장 등이 등장해도 정책 기사로 보고 정상 채점해. 정치인 개인의 공약·치적·행보가 핵심이면 0점.\n");
        sb.append("\n[채점 가이드] — 1점 이상은 '해양레저와의 연결 근거가 있는 기사'에만 준다\n");
        sb.append("- 90~100: 3대 축의 핵심 산업 동향(시장·투자·신제품·인프라 착공/개장·전시회·정책 확정)\n");
        sb.append("- 60~89: 3대 축과 직접 관련된 일반 보도(행사 안내, 지역 사업 추진, 기업 소식)\n");
        sb.append("- 30~59: 간접 관련(지역 해양관광 일반, 어촌·섬 관광, 해양 축제, 수상 안전 정책)\n");
        sb.append("- 10~29: 해양 분야(수산·조선·항만·해양환경·해양교육)이지만 레저 연관이 약함. 해양레저와 연결되는 요소를 하나는 짚을 수 있을 때만\n");
        sb.append("- 0: 배제 규칙 해당, 또는 위 연결 근거가 없는 기사. 1~9점은 사용하지 말 것(관련 있으면 10점 이상, 없으면 0점)\n");
        if (previousTitles != null && !previousTitles.isEmpty()) {
            sb.append("\n[최근 호에 이미 다룬 기사] — 같은 사안(동일 발표·행사·협약)의 후속 진전이 없는 기사는 점수를 절반 이하로 낮춰\n");
            for (String t : previousTitles) sb.append("- ").append(t).append("\n");
        }
        sb.append("\n[출력 형식] 번호:점수 를 한 줄에 하나씩, 후보 수만큼 모두 출력. 다른 설명·마크다운 금지. 예) 1:85\n\n");
        sb.append("[기사 후보]\n");
        for (int i = 0; i < part.size(); i++) {
            AiClippingArticleDTO a = part.get(i);
            sb.append(i + 1).append(". (").append(a.getArticleDate()).append(", ").append(a.getKeyword()).append(") ").append(a.getTitle()).append("\n");
            if (a.getSummary() != null && !a.getSummary().isEmpty()) {
                String s = a.getSummary();
                sb.append("   요약: ").append(s.length() > 200 ? s.substring(0, 200) : s).append("\n");
            }
        }

        Map<Integer, Integer> result = new HashMap<>();
        String resp = support.callOpenAI(sb.toString(), 2000, 0.2);
        if (resp == null) {
            System.err.println("[AI 클리핑 V2] 점수 응답 없음 (" + part.size() + "건) → 누락 처리");
            return result;
        }
        Matcher m = Pattern.compile("(\\d{1,3})\\s*[:：]\\s*(\\d{1,3})").matcher(resp);
        while (m.find()) {
            try {
                result.put(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            } catch (NumberFormatException ignored) {
            }
        }
        return result;
    }

    private String buildPillarsRule() {
        return "[연관도 판단의 3대 핵심 축]\n" +
                "1) 레저선박·장비: 모터보트, 요트, 선외기, 제트스키, 카약, 전기·수소 레저보트, 부품·소재, 국내외 제조사·브랜드 동향\n" +
                "2) 마리나·계류 인프라: 마리나 항만 개발, 선착장, 레저선박 보관·정비, 해양레저 안전 법제화·제도\n" +
                "3) 해양관광·레저문화: 서핑·스쿠버·요트 체험, 해양레저 축제·전시회, 지자체·정부의 해양레저관광 정책\n" +
                "독자는 해양레저 업계 관계자(제조·유통·마리나 운영·지자체 담당자)다.\n";
    }

    /**
     * 점수순 목록에서 상위 n건을 고르되, 이미 고른 기사와 같은 사안(정규화 제목의 공통 부분문자열 ≥ sameIssueMinCommon)인 기사는
     * 사안당 maxPerIssue 건까지만 넣는다. 그래도 n건이 안 되면 건너뛴 기사를 점수순으로 채운다.
     */
    private List<AiClippingArticleDTO> pickDiverseTop(List<AiClippingArticleDTO> sortedByScore, int n) {
        List<AiClippingArticleDTO> picked = new ArrayList<>();
        List<String> pickedTitles = new ArrayList<>();
        List<Integer> pickedGroup = new ArrayList<>();
        Map<Integer, Integer> groupCount = new HashMap<>();
        List<AiClippingArticleDTO> skipped = new ArrayList<>();
        int nextGroup = 0;

        for (AiClippingArticleDTO a : sortedByScore) {
            if (picked.size() >= n) break;
            String t = support.normalizeTitleForSimilarity(a.getTitle());
            int group = -1;
            for (int i = 0; i < pickedTitles.size(); i++) {
                if (support.longestCommonSubstring(t, pickedTitles.get(i)) >= Math.max(4, sameIssueMinCommon)) {
                    group = pickedGroup.get(i);
                    break;
                }
            }
            if (group >= 0 && groupCount.getOrDefault(group, 0) >= Math.max(1, maxPerIssue)) {
                skipped.add(a);
                continue;
            }
            if (group < 0) group = nextGroup++;
            groupCount.put(group, groupCount.getOrDefault(group, 0) + 1);
            picked.add(a);
            pickedTitles.add(t);
            pickedGroup.add(group);
        }
        for (AiClippingArticleDTO a : skipped) {
            if (picked.size() >= n) break;
            picked.add(a);
        }
        return picked;
    }

    // ------------------------------------------------------------------
    // 4. 토픽 생성
    // ------------------------------------------------------------------

    private static class Topic {
        String tag;
        String title;
        List<String> paragraphs = new ArrayList<>();
        List<Integer> sourceIndexes = new ArrayList<>();
    }

    private List<Topic> generateTopics(List<AiClippingArticleDTO> top, List<String> bodies, List<String> previousTitles) {
        List<String> tags = new ArrayList<>();
        if (topicTags != null) {
            for (String s : topicTags.split(",")) {
                String t = s.trim();
                if (!t.isEmpty()) tags.add(t);
            }
        }
        int topicCount = Math.max(1, Math.min(targetTopics, top.size()));

        StringBuilder sb = new StringBuilder();
        sb.append("다음은 이번 주 해양레저산업 관련 핵심 기사 ").append(top.size()).append("건의 본문이야. 업계 관계자용 B2B 주간 브리핑을 작성해.\n\n");
        sb.append(buildPillarsRule());
        sb.append("\n[작성 요구사항]\n");
        sb.append("1. 서로 다른 사안으로 최대 ").append(topicCount).append("개의 토픽을 만들어. 같은 사안을 다룬 기사들은 하나의 토픽으로 묶고, 기사가 부족하면 토픽 수를 줄여도 돼.\n");
        sb.append("2. 토픽 제목은 반드시 다음 고정 태그 중 하나를 골라 '[태그] 제목' 형식으로 쓰고, 제목은 30자 이내의 명료한 명사형 문장으로. 태그 목록: ").append(String.join(", ", tags)).append("\n");
        sb.append("   예) [마리나 인프라] 경기도, 해양레저 거점 육성 위해 계류시설 500선석 확충\n");
        sb.append("3. 토픽 본문은 정확히 2개의 문단. 각 문단은 3~4문장, 단순 요약이 아니라 업계 시사점을 담은 전문적인 톤으로.\n");
        sb.append("4. 각 토픽의 문단 중 핵심 어구 1개 이상에 출처 기사 링크를 <a href='링크' target='_blank' style='color:#222222; text-decoration:underline; text-underline-offset:4px; font-weight:bold;'>어구</a> 형식으로 걸어. 링크는 아래 기사 목록의 링크를 그대로 사용.\n");
        sb.append("5. 토픽 간 서론·결론·문장 구조가 반복되지 않게 다채롭게. 인사말·맺음말 금지.\n");
        sb.append("6. 정당·정치인 개인 행보, 사건사고, 연예 가십은 다루지 않아. 정부·지자체 정책은 기관 명의로 서술.\n");
        if (previousTitles != null && !previousTitles.isEmpty()) {
            sb.append("7. 아래 [최근 호에 이미 다룬 기사]와 같은 사안은 뚜렷한 후속 진전이 있을 때만 토픽으로 다뤄.\n");
        }
        sb.append("\n[출력 형식] 토픽마다 아래 블록을 그대로 반복. 다른 텍스트·마크다운·코드블록 금지.\n");
        sb.append("###TOPIC\n태그: (태그 목록 중 하나)\n제목: (30자 이내 명사형)\n출처: (사용한 기사 번호, 쉼표 구분. 예 1,3)\n문단: (첫 문단, 한 줄)\n문단: (둘째 문단, 한 줄)\n");
        if (previousTitles != null && !previousTitles.isEmpty()) {
            sb.append("\n[최근 호에 이미 다룬 기사]\n");
            for (String t : previousTitles) sb.append("- ").append(t).append("\n");
        }
        sb.append("\n[기사 목록]\n");
        for (int i = 0; i < top.size(); i++) {
            AiClippingArticleDTO a = top.get(i);
            sb.append("--- 기사 ").append(i + 1).append(" ---\n");
            sb.append("제목: ").append(a.getTitle()).append("\n");
            sb.append("언론사: ").append(a.getPublisher()).append(" | 날짜: ").append(a.getArticleDate()).append(" | 링크: ").append(a.getLink()).append("\n");
            sb.append("본문: ").append(bodies.get(i)).append("\n\n");
        }

        String resp = support.callOpenAI(sb.toString(), 6000, 0.7);
        List<Topic> topics = new ArrayList<>();
        if (resp == null) return topics;

        for (String block : resp.split("###TOPIC")) {
            if (block.trim().isEmpty()) continue;
            Topic t = new Topic();
            for (String rawLine : block.split("\n")) {
                String line = rawLine.trim().replaceAll("^\\*\\*|\\*\\*$", "");
                if (line.isEmpty()) continue;
                if (line.startsWith("태그:")) t.tag = cleanTag(line.substring(3).trim(), tags);
                else if (line.startsWith("제목:")) t.title = line.substring(3).trim().replaceAll("^\\[[^\\]]*\\]\\s*", "");
                else if (line.startsWith("출처:")) {
                    Matcher m = Pattern.compile("\\d+").matcher(line.substring(3));
                    while (m.find()) t.sourceIndexes.add(Integer.parseInt(m.group()));
                } else if (line.startsWith("문단:")) {
                    String p = line.substring(3).trim();
                    if (!p.isEmpty()) t.paragraphs.add(p);
                }
            }
            if (t.title != null && !t.title.isEmpty() && !t.paragraphs.isEmpty()) {
                if (t.tag == null || t.tag.isEmpty()) t.tag = tags.isEmpty() ? "산업동향" : tags.get(tags.size() - 1);
                while (t.paragraphs.size() > 2) t.paragraphs.remove(t.paragraphs.size() - 1);
                topics.add(t);
            }
            if (topics.size() >= topicCount) break;
        }
        return topics;
    }

    /** GPT 가 돌려준 태그를 고정 목록에 맞춘다 (대괄호 제거, 부분 일치 허용, 없으면 null) */
    private String cleanTag(String raw, List<String> tags) {
        String t = raw.replaceAll("[\\[\\]]", "").trim();
        for (String tag : tags) if (tag.equals(t)) return tag;
        String tn = t.replaceAll("[\\s·ㆍ/]", "");
        for (String tag : tags) if (tag.replaceAll("[\\s·ㆍ/]", "").equals(tn)) return tag;
        for (String tag : tags) if (tn.contains(tag.replaceAll("[\\s·ㆍ/]", "")) || tag.replaceAll("[\\s·ㆍ/]", "").contains(tn)) return tag;
        return null;
    }

    // ------------------------------------------------------------------
    // 5. 렌더 (기존 레이아웃 유지: ■ 기사 출처 → 타이틀 밴드 → 섹션 → 푸터)
    // ------------------------------------------------------------------

    private String renderNewsletter(List<Topic> topics, List<AiClippingArticleDTO> sources, List<String> keywords) {
        List<AiClippingArticleDTO> sorted = new ArrayList<>(sources);
        sorted.sort((a, b) -> String.valueOf(b.getArticleDate()).compareTo(String.valueOf(a.getArticleDate())));

        String dateRange = "";
        if (!sorted.isEmpty()) {
            LocalDate newest = LocalDate.parse(sorted.get(0).getArticleDate());
            LocalDate oldest = LocalDate.parse(sorted.get(sorted.size() - 1).getArticleDate());
            DateTimeFormatter md = DateTimeFormatter.ofPattern("M. d.");
            dateRange = oldest.equals(newest) ? newest.format(md) : oldest.format(md) + " ~ " + newest.format(md);
        }

        StringBuilder html = new StringBuilder();
        html.append("<div style='margin-bottom: 40px; padding: 20px; background-color: #f8f9fa; border-top: 4px solid #1d5cad; border-bottom: 1px solid #ddd;'>");
        html.append("   <strong style='color: #1d5cad; display:block; margin-bottom:15px; font-size: 16px;'>■ 기사</strong>");
        html.append("   <ul style='list-style: none; padding: 0; margin: 0;'>");
        for (int i = 0; i < sorted.size(); i++) {
            AiClippingArticleDTO s = sorted.get(i);
            LocalDate d = LocalDate.parse(s.getArticleDate());
            String displayDate = d.format(DOT_DATE_FMT) + "(" + d.format(DateTimeFormatter.ofPattern("E", Locale.KOREAN)) + ")";
            html.append("<li style='font-size: 14px; color: #333; margin-bottom: 8px;'>");
            html.append("출처 (").append(i + 1).append(") ").append(displayDate).append(" | ");
            html.append("<a href='").append(s.getLink()).append("' target='_blank' style='color:#1d5cad; font-weight:bold; text-decoration:underline; text-underline-offset:2px;'>").append(escape(s.getTitle())).append("</a>");
            html.append(" | ").append(escape(s.getPublisher())).append("</li>");
        }
        html.append("   </ul></div>");

        if (!dateRange.isEmpty()) {
            html.append("<div style='background-color: #1d5cad; color: #ffffff; text-align: center; padding: 25px 15px; font-size: 24px; font-weight: bold; margin-bottom: 40px; border-radius: 5px;'>");
            html.append("해양레저산업 기사 요약 (기간: ").append(dateRange).append(")</div>");
        }

        for (int i = 0; i < topics.size(); i++) {
            Topic t = topics.get(i);
            html.append("<h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>")
                .append(i + 1).append(". [").append(escape(t.tag)).append("] ").append(escape(t.title)).append("</h3>");
            for (String p : t.paragraphs) {
                html.append("<p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>").append(p).append("</p>");
            }
        }

        String now = LocalDateTime.now(KST).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        html.append("<div style='margin-top: 50px; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #1d5cad; border-radius: 5px; text-align: left; font-size: 14px; color: #444; line-height: 1.6;'>");
        html.append("   <strong style='color: #1d5cad;'>■ 수집 키워드 :</strong> ");
        for (String kw : keywords) html.append("#").append(escape(kw)).append(" ");
        html.append("<br><br>");
        html.append("   <strong style='color: #1d5cad;'>■ 작성자 :</strong> 경기국제보트쇼 AI 브리핑 봇<br>");
        html.append("   <strong style='color: #1d5cad;'>■ 생성 모델 :</strong> OpenAI ").append(support.getOpenAiModel()).append("<br>");
        html.append("   <strong style='color: #1d5cad;'>■ 생성 일시 :</strong> ").append(now).append("<br>");
        html.append("   <span style='font-size: 12px; color: #888; display: block; margin-top: 8px; margin-bottom: 12px;'>* 본 기사는 인공지능 모델이 자동 수집 및 요약한 내용으로, 원본 기사의 논조와 일부 다를 수 있습니다.</span></div>");
        return html.toString();
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private long randomDelay(int minMs, int maxMs) {
        int min = Math.max(0, minMs), max = Math.max(min, maxMs);
        return min + (long) (Math.random() * (max - min + 1));
    }
}
