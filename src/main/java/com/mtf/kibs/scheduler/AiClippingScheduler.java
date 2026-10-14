package com.mtf.kibs.scheduler;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AiClippingScheduler {

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private NewsletterService newsletterService;

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${openai.model.summarize}")
    private String openAiModel;

    // 1. [생성 담당] 매주 금요일 오전 8시 자동 실행 (미노출/미발송 상태로 생성만 수행)
    @Scheduled(cron = "0 0 8 * * FRI", zone = "Asia/Seoul")
    public void generateAiClippingAt10AM() {
        System.out.println("========== [금요일 08:00 AM] AI 클리핑 자동 생성 스케줄러 시작 (미노출/미발송) ==========");
        processAiClipping(false); // false를 전달하여 뉴스레터 자동 발송 차단 (임시저장)
    }

    // 2. [발행 담당] 매주 월요일 오전 8시 자동 실행 (금요일에 생성되어 검수 완료된 기사를 노출로 변경 후 발송)
    @Scheduled(cron = "0 0 8 * * MON", zone = "Asia/Seoul")
    public void publishAndSendAiClippingAt8AM() {
        System.out.println("========== [월요일 08:00 AM] AI 클리핑 자동 승인 및 발송 스케줄러 시작 ==========");

        try {
            // DB에서 가장 최근에 생성된 미노출(display_yn='N') 기사 1건을 조회
            AiClippingDTO searchDto = new AiClippingDTO();
            searchDto.setDisplayYn("N");
            searchDto.setLimit(1);
            searchDto.setOffset(0);

            List<AiClippingDTO> pendingList = aiClippingMapper.selectAiClippingList(searchDto);

            if (pendingList != null && !pendingList.isEmpty()) {
                AiClippingDTO target = pendingList.get(0);

                // 1) 노출 상태를 'Y'로 업데이트하여 홈페이지에 게시
                Map<String, String> param = new HashMap<>();
                param.put("seq", target.getSeq());
                param.put("displayYn", "Y");
                aiClippingMapper.updateDisplayStatus(param);
                System.out.println("-> 게시물 노출 상태 [게시중(Y)]으로 변경 완료 (SEQ: " + target.getSeq() + ")");

                // 2) 구독자에게 뉴스레터 메일 발송
                newsletterService.sendClippingNewsletter(target.getSeq(), target.getTitle(), target.getContent());
                System.out.println("-> 뉴스레터 자동 발송 완료");

                System.out.println("========== [월요일 08:00 AM] AI 클리핑 승인/발송 프로세스 정상 종료 ==========");
            } else {
                System.out.println("========== [월요일 08:00 AM] 발송 대기 중인(미노출) 기사가 없습니다. ==========");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== [월요일 08:00 AM] AI 클리핑 승인/발송 중 오류 발생 ==========");
        }
    }

    // 3. 실제 클리핑 수집 및 생성을 담당하는 코어 로직
    public boolean processAiClipping(boolean isSend) {
        System.out.println("========== AI 클리핑 코어 프로세스 시작 (발송여부: " + isSend + ") ==========");

        try {
            int sleepTime = new Random().nextInt(10000);
            Thread.sleep(sleepTime);

            String todayDate = LocalDate.now().toString();
            String generatedTitle = "[경기국제보트쇼 AI 클리핑] " + todayDate + " 해양레저산업 주요 동향";

            List<AiClippingKeywordDTO> keywordList = aiClippingMapper.selectKeywordList();

            if (keywordList == null || keywordList.isEmpty()) {
                System.out.println("========== 등록된 키워드가 없어 AI 클리핑을 종료합니다 ==========");
                return false;
            }

            Collections.shuffle(keywordList);

            StringBuilder rawArticlesBuilder = new StringBuilder();
            List<String> usedKeywords = new ArrayList<>();
            List<String[]> usedSources = new ArrayList<>();

            int successKeywordCount = 0;
            int maxKeywordLimit = 5;

            for (AiClippingKeywordDTO kw : keywordList) {
                String targetKeyword = kw.getKeyword();
                System.out.println("-> 수집 시도 키워드: " + targetKeyword);

                // [개선 1] Daum에서 후보 기사들만 일단 긁어옴 (단순 태그/깡통 링크만 1차 제거)
                List<String[]> candidateArticles = fetchCandidateArticlesFromDaum(targetKeyword);

                if (candidateArticles.isEmpty()) {
                    System.out.println("   [스킵] 크롤링 결과 없음.");
                    continue;
                }

                // [개선 2] 능동적 필터링: AI에게 이 기사들이 해양레저 산업/비즈니스와 직결되는지 물어보고, 통과된 것만 반환받음
                String validArticleFromAi = filterAndSummarizeValidArticle(targetKeyword, candidateArticles, usedSources);

                if (validArticleFromAi != null && !validArticleFromAi.trim().isEmpty()) {
                    rawArticlesBuilder.append(validArticleFromAi).append("\n\n");
                    usedKeywords.add(targetKeyword);
                    successKeywordCount++;
                    System.out.println("   [성공] AI 필터링을 통과한 유효 기사 확보 (현재 수집 키워드: " + successKeywordCount + "/" + maxKeywordLimit + ")");
                } else {
                    System.out.println("   [스킵] 수집된 후보 기사들이 AI 적합성 심사(산업 무관, 단순 사고 등)에서 모두 탈락함.");
                }

                if (successKeywordCount >= maxKeywordLimit) {
                    break;
                }

                Thread.sleep(1500);
            }

            String rawArticles = rawArticlesBuilder.toString();

            if (rawArticles.trim().isEmpty()) {
                System.out.println("========== 전체 키워드를 탐색했으나 AI 필터링을 통과한 유효 기사가 없어 종료합니다 ==========");
                return false; // 컨트롤러에 실패(false) 반환
            }

            // 4. 최종 확보된 유효 기사 모음(rawArticles)으로 최종 뉴스레터 HTML 생성
            String generatedContent = generateFinalNewsletter(rawArticles, usedKeywords, usedSources);

            AiClippingDTO clippingDTO = new AiClippingDTO();
            clippingDTO.setTitle(generatedTitle);
            clippingDTO.setContent(generatedContent);

            int insertCnt = aiClippingMapper.insertAiClipping(clippingDTO);

            if (insertCnt > 0 && clippingDTO.getSeq() != null) {
                System.out.println("========== AI 클리핑 생성 완료 (SEQ: " + clippingDTO.getSeq() + ", 상태: 미노출) ==========");

                if (isSend) {
                    newsletterService.sendClippingNewsletter(
                            clippingDTO.getSeq(),
                            clippingDTO.getTitle(),
                            clippingDTO.getContent()
                    );
                    System.out.println("========== AI 클리핑 뉴스레터 즉시 발송 완료 ==========");
                }
                return true; // 컨트롤러에 성공(true) 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== AI 클리핑 프로세스 실행 중 오류 발생 ==========");
        }
        return false;
    }

    // 1단계: Daum에서 후보 기사 크롤링 (단순 필터링만 수행)
    private List<String[]> fetchCandidateArticlesFromDaum(String keyword) {
        List<String[]> candidates = new ArrayList<>();
        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            String url = "https://search.daum.net/search?w=news&q=" + encodedKeyword + "&sort=rec";

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(5000).get();

            Elements newsElements = doc.select(".c-item-content");
            int count = 0;

            for (Element element : newsElements) {
                if (count >= 3) break;

                Element titleElement = element.select(".item-title").first();
                if (titleElement == null) continue;

                String title = titleElement.text();
                String summary = element.select(".conts-desc").text();

                // 최소한의 찌꺼기 텍스트만 1차 방어
                if (summary.contains("function()") || summary.contains("document.") || summary.contains("{")) {
                    continue;
                }

                String link = "";
                if (titleElement.tagName().equals("a")) link = titleElement.attr("href");
                else if (titleElement.select("a").first() != null) link = titleElement.select("a").first().attr("href");
                else if (element.select("a").first() != null) link = element.select("a").first().attr("href");

                // 깡통 링크 차단
                if (link == null || link.trim().isEmpty() || link.startsWith("#") || link.contains("search.daum.net")) {
                    continue;
                }
                if (!link.startsWith("http")) link = "https:" + (link.startsWith("/") ? "" : "/") + link;

                // 날짜 및 언론사 추출 (기존 정규식 로직 동일)
                String fullText = element.text().replace(title, "").replace(summary, "").replaceAll("[|·ⓒ]", " ").trim();
                String articleDate = "";
                Matcher matcher = Pattern.compile("(\\d{4}\\.\\d{2}\\.\\d{2}\\.?|\\d+\\s*(시간|분|일|주|개월)\\s*전|어제)").matcher(fullText);
                if (matcher.find()) articleDate = matcher.group(1).trim();
                if (articleDate.isEmpty()) articleDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                else if (articleDate.endsWith(".")) articleDate = articleDate.substring(0, articleDate.length() - 1);

                String publisher = "언론사";
                try {
                    Document articleDoc = Jsoup.connect(link).timeout(2000).get();
                    Element ogSiteName = articleDoc.selectFirst("meta[property=og:site_name]");
                    if (ogSiteName != null) publisher = ogSiteName.attr("content").replace("Daum", "").replace("|", "").trim();
                } catch (Exception e) {
                    publisher = fullText.replaceAll("(?<!\\S)\\d+(?!\\S)", "").replaceAll("\\s{2,}", " ").trim();
                }
                if (publisher.isEmpty() || publisher.matches("^[0-9\\s\\p{P}]+$")) publisher = "언론사";

                candidates.add(new String[]{publisher, link, title, articleDate, summary});
                count++;
            }
        } catch (Exception e) {
            System.err.println("Daum 크롤링 오류: " + e.getMessage());
        }
        return candidates;
    }

    // 2단계: AI에게 후보 기사를 던져주고, "진짜배기 산업 뉴스" 1개만 골라서 리턴하게 함 (능동적 필터링)
    private String filterAndSummarizeValidArticle(String keyword, List<String[]> candidates, List<String[]> usedSources) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("너는 해양레저산업 비즈니스 전문가야. 다음은 '").append(keyword).append("' 키워드로 검색된 최신 뉴스 후보들이야.\n\n");
        promptBuilder.append("[판단 기준 - 매우 엄격함]\n");
        promptBuilder.append("1. **부정적/사건사고 기사 절대 배제**: 바다에서 일어난 조난, 구조, 갯벌 고립, 실종, 사망, 침몰, 해경 적발 등 단순 사회면/사건사고 기사는 무조건 버려.\n");
        promptBuilder.append("2. **타 산업/가십 절대 배제**: 정치인 가십, 골프 클럽, 부동산, 아파트 분양 등 해양레저(보트, 요트, 마리나, 해양관광) 산업/비즈니스 동향과 관련 없는 기사는 키워드가 있더라도 무조건 버려.\n");
        promptBuilder.append("3. 위 두 기준을 통과한, 업계 관계자에게 유의미한 '산업 동향, 기술 발전, 전시회, 비즈니스, 관광 정책' 관련 기사만 합격이야.\n\n");
        promptBuilder.append("[요구사항]\n");
        promptBuilder.append("- 합격한 기사가 있다면, 그 중 가장 중요한 1개의 기사만 골라내.\n");
        promptBuilder.append("- 출력 형식은 반드시 아래와 같이 딱 4줄 텍스트로만 해. (다른 말은 절대 금지)\n");
        promptBuilder.append("선택번호: (1,2,3 중 하나)\n");
        promptBuilder.append("제목: (선택한 기사 제목)\n");
        promptBuilder.append("링크: (선택한 기사 링크)\n");
        promptBuilder.append("내용: (해당 기사의 핵심 내용을 3~4문장으로 요약)\n\n");
        promptBuilder.append("- 만약 모든 기사가 판단 기준에 미달하여 합격한 기사가 0개라면, 딱 한 단어 'REJECT' 라고만 출력해.\n\n");
        promptBuilder.append("[기사 후보 목록]\n");

        for (int i = 0; i < candidates.size(); i++) {
            String[] c = candidates.get(i);
            promptBuilder.append((i + 1)).append(". 제목: ").append(c[2]).append("\n");
            promptBuilder.append("   내용: ").append(c[4]).append("\n\n");
        }

        // OpenAI 통신 (필터링 용도)
        String aiResponse = callOpenAI(promptBuilder.toString(), 1000);

        if (aiResponse == null || aiResponse.contains("REJECT")) {
            return null; // 모든 기사가 불합격
        }

        // AI가 합격시킨 기사의 원본 링크와 언론사 정보를 찾아 usedSources에 등록
        try {
            String selectedLink = "";
            String selectedTitle = "";
            String selectedSummary = "";

            String[] lines = aiResponse.split("\n");
            for (String line : lines) {
                if (line.startsWith("링크:")) selectedLink = line.replace("링크:", "").trim();
                else if (line.startsWith("제목:")) selectedTitle = line.replace("제목:", "").trim();
                else if (line.startsWith("내용:")) selectedSummary = line.replace("내용:", "").trim();
            }

            // 선택된 링크를 바탕으로 원본 출처 정보(언론사, 날짜) 매핑
            for (String[] c : candidates) {
                if (c[1].equals(selectedLink)) {
                    usedSources.add(new String[]{c[0], c[1], selectedTitle, c[3]}); // 언론사, 링크, AI가 고른 제목, 날짜
                    break;
                }
            }

            // 최종 뉴스레터 작성기로 넘어갈 텍스트 포맷
            return "제목: " + selectedTitle + "\n원본링크: " + selectedLink + "\n내용: " + selectedSummary;

        } catch (Exception e) {
            return null; // 파싱 실패 시 버림
        }
    }

    // 3단계: 최종 엄선된 기사들로 뉴스레터 HTML 코드 생성
    private String generateFinalNewsletter(String rawArticles, List<String> keywords, List<String[]> sources) {
        String prompt = "다음은 AI가 1차적으로 엄선한 해양레저 산업 관련 핵심 기사 요약본들이야. 이를 바탕으로 독자(업계 관계자)를 위한 전문적인 B2B 뉴스레터 본문을 작성해줘.\n\n" +
                "[작성 요구사항]\n" +
                "1. 전달받은 기사 내용을 바탕으로 4~5개의 소주제(섹션)로 나눌 것. 분량이 너무 길지 않도록 **각 섹션당 본문은 반드시 정확히 2개의 문단**으로만 핵심을 요약해서 작성할 것.\n" +
                "2. (여백 확보 및 순번 표기) 각 섹션의 제목은 반드시 '1.', '2.', '3.' 처럼 순번을 매겨서 아래 형식의 <h3> 태그를 사용할 것. (단락 구분 여백 포함)\n" +
                "   -> <h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>1. 섹션 제목</h3>\n" +
                "3. (단락 띄어쓰기) 본문 내용은 가독성을 위해 단락마다 반드시 아래 형식의 <p> 태그를 사용할 것. (각 섹션당 <p>태그는 딱 2개씩만 생성할 것)\n" +
                "   -> <p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>본문 내용...</p>\n" +
                "4. 선별하여 요약에 반영한 기사의 '원본링크'를 반드시 활용하여, 본문 문맥 중 핵심 텍스트에 <a> 태그로 하이퍼링크를 1개 이상 걸어줄 것.\n" +
                "   -> 하이퍼링크 스타일 양식: <a href='원본링크' target='_blank' style='color:#222222; text-decoration:underline; text-underline-offset:4px; font-weight:bold;'>키워드</a>\n" +
                "5. 단순 요약이 아닌, 독자(업계 관계자)에게 인사이트를 제공하는 전문적인 기사 톤으로 작성할 것.\n" +
                "6. 인사말이나 맺음말 없이 바로 <h3> 태그로 시작하는 본문 HTML만 출력하고, 문장이 중간에 잘리지 않도록 반드시 끝맺음을 완벽하게 할 것.\n" +
                "7. 매일 비슷하거나 정형화된 서론, 결론, 문장 구조가 반복되지 않도록 주의할 것. 매번 새로운 시각, 다채로운 어휘, 그리고 트렌디한 표현 방식을 도입하여 독자가 지루하지 않게 작성할 것.\n\n" +
                "[엄선된 기사 데이터]\n" + rawArticles;

        String aiContent = callOpenAI(prompt, 6000);
        if (aiContent == null) return "<p>생성 중 오류 발생</p>";

        // 하단 출처 표기 및 조립 로직
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);

        List<Map<String, Object>> parsedSources = new ArrayList<>();
        for (String[] src : sources) {
            Map<String, Object> map = new HashMap<>();
            String pub = src[0];
            String url = src[1];
            String title = src[2];
            String rawDate = src.length > 3 ? src[3] : "";

            LocalDate calculatedDate = LocalDate.now();
            try {
                if (rawDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
                    calculatedDate = LocalDate.parse(rawDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                } else if (rawDate.contains("어제")) {
                    calculatedDate = LocalDate.now().minusDays(1);
                } else if (rawDate.matches("\\d+\\s*일\\s*전")) {
                    int days = Integer.parseInt(rawDate.replaceAll("[^0-9]", ""));
                    calculatedDate = LocalDate.now().minusDays(days);
                }
            } catch (Exception e) {
                calculatedDate = LocalDate.now();
            }

            String displayDate = calculatedDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "(" + calculatedDate.format(DateTimeFormatter.ofPattern("E", java.util.Locale.KOREAN)) + ")";
            map.put("publisher", pub);
            map.put("url", url);
            map.put("title", title);
            map.put("date", calculatedDate);
            map.put("displayDate", displayDate);
            parsedSources.add(map);
        }

        parsedSources.sort((m1, m2) -> ((LocalDate) m2.get("date")).compareTo((LocalDate) m1.get("date")));

        String dateRange = "";
        if (!parsedSources.isEmpty()) {
            DateTimeFormatter mdFormatter = DateTimeFormatter.ofPattern("M. d.");
            String startDate = ((LocalDate) parsedSources.get(parsedSources.size() - 1).get("date")).format(mdFormatter);
            String endDate = ((LocalDate) parsedSources.get(0).get("date")).format(mdFormatter);
            dateRange = startDate.equals(endDate) ? startDate : startDate + " ~ " + endDate;
        }

        StringBuilder headerHtml = new StringBuilder();
        headerHtml.append("<div style='margin-bottom: 40px; padding: 20px; background-color: #f8f9fa; border-top: 4px solid #1d5cad; border-bottom: 1px solid #ddd;'>");
        headerHtml.append("   <strong style='color: #1d5cad; display:block; margin-bottom:15px; font-size: 16px;'>■ 기사</strong>");
        headerHtml.append("   <ul style='list-style: none; padding: 0; margin: 0;'>");
        for (int i = 0; i < parsedSources.size(); i++) {
            Map<String, Object> ps = parsedSources.get(i);
            headerHtml.append("<li style='font-size: 14px; color: #333; margin-bottom: 8px;'>");
            headerHtml.append("출처 (").append(i + 1).append(") ").append(ps.get("displayDate")).append(" | ");
            headerHtml.append("<a href='").append(ps.get("url")).append("' target='_blank' style='color:#1d5cad; font-weight:bold; text-decoration:underline; text-underline-offset:2px;'>").append(ps.get("title")).append("</a>");
            headerHtml.append(" | ").append(ps.get("publisher")).append("</li>");
        }
        headerHtml.append("   </ul></div>");

        if (!dateRange.isEmpty()) {
            headerHtml.append("<div style='background-color: #1d5cad; color: #ffffff; text-align: center; padding: 25px 15px; font-size: 24px; font-weight: bold; margin-bottom: 40px; border-radius: 5px;'>");
            headerHtml.append("해양레저산업 기사 요약 (기간: ").append(dateRange).append(")</div>");
        }

        StringBuilder footerHtml = new StringBuilder("<div style='margin-top: 50px; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #1d5cad; border-radius: 5px; text-align: left; font-size: 14px; color: #444; line-height: 1.6;'>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 수집 키워드 :</strong> ");
        for (String kw : keywords) footerHtml.append("#").append(kw).append(" ");
        footerHtml.append("<br><br>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 작성자 :</strong> 경기국제보트쇼 AI 브리핑 봇<br>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 생성 모델 :</strong> OpenAI ").append(openAiModel).append("<br>");
        footerHtml.append("   <strong style='color: #1d5cad;'>■ 생성 일시 :</strong> ").append(currentTime).append("<br>");
        footerHtml.append("   <span style='font-size: 12px; color: #888; display: block; margin-top: 8px; margin-bottom: 12px;'>* 본 기사는 인공지능 모델이 자동 수집 및 요약한 내용으로, 원본 기사의 논조와 일부 다를 수 있습니다.</span></div>");

        return headerHtml.toString() + aiContent + footerHtml.toString();
    }

    // 공통 OpenAI API 호출 헬퍼
    private String callOpenAI(String prompt, int maxTokens) {
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
        body.put("temperature", 0.7);

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
            System.err.println("OpenAI API 호출 에러: " + e.getMessage());
        }
        return null;
    }
}