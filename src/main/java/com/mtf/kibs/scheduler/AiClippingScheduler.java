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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    // 1. [생성 담당] 매일 오전 10시 자동 실행 (미노출/미발송 상태로 생성만 수행)
    @Scheduled(cron = "0 0 10 * * MON-FRI", zone = "Asia/Seoul")
    public void generateAiClippingAt10AM() {
        System.out.println("========== [10:00 AM] AI 클리핑 자동 생성 스케줄러 시작 (미노출/미발송) ==========");
        processAiClipping(false); // false를 전달하여 뉴스레터 자동 발송 차단 (임시저장)
    }

    // 2. [발행 담당] 매일 오전 8시 자동 실행 (검수 완료된 기사를 노출로 변경 후 발송)
    @Scheduled(cron = "0 0 8 * * MON-FRI", zone = "Asia/Seoul")
    public void publishAndSendAiClippingAt8AM() {
        System.out.println("========== [08:00 AM] AI 클리핑 자동 승인 및 발송 스케줄러 시작 ==========");

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

                System.out.println("========== [08:00 AM] AI 클리핑 승인/발송 프로세스 정상 종료 ==========");
            } else {
                System.out.println("========== [08:00 AM] 발송 대기 중인(미노출) 기사가 없습니다. ==========");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== [08:00 AM] AI 클리핑 승인/발송 중 오류 발생 ==========");
        }
    }

    // 3. 실제 클리핑 수집 및 생성을 담당하는 코어 로직
    public void processAiClipping(boolean isSend) {
        System.out.println("========== AI 클리핑 코어 프로세스 시작 (발송여부: " + isSend + ") ==========");

        try {
            // 다중 도메인 환경에서 여러 스케줄러가 동시 실행되는 것을 방지하기 위해 0~10초 랜덤 대기
            int sleepTime = new Random().nextInt(10000);
            Thread.sleep(sleepTime);

            String todayDate = java.time.LocalDate.now().toString();
            String generatedTitle = "[경기국제보트쇼 AI 클리핑] " + todayDate + " 해양레저산업 주요 동향";

            // 1. DB에서 전체 키워드 목록 조회 (동적 로드)
            List<AiClippingKeywordDTO> keywordList = aiClippingMapper.selectKeywordList();

            if (keywordList == null || keywordList.isEmpty()) {
                System.out.println("========== 등록된 키워드가 없어 AI 클리핑을 종료합니다 ==========");
                return;
            }

            // 2. 매일 똑같은 기사가 생성되지 않도록 전체 키워드를 무작위로 섞음
            Collections.shuffle(keywordList);

            // 3. 최대 5개까지만 추출 (토큰 한도 및 크롤링 부하 방지)
            int limit = Math.min(keywordList.size(), 5);
            List<AiClippingKeywordDTO> selectedKeywords = keywordList.subList(0, limit);

            StringBuilder rawArticlesBuilder = new StringBuilder();

            // 하단 출처 표기를 위한 리스트
            List<String> usedKeywords = new ArrayList<>();
            List<String[]> usedSources = new ArrayList<>(); // {언론사, 링크, 제목, 날짜}

            // 4. 추출된 키워드들로 기사 수집
            for (AiClippingKeywordDTO kw : selectedKeywords) {
                String targetKeyword = kw.getKeyword();
                usedKeywords.add(targetKeyword); // 사용된 키워드 기록
                System.out.println("-> 수집 키워드: " + targetKeyword);

                String articles = fetchArticlesFromDaum(targetKeyword, usedSources);
                rawArticlesBuilder.append(articles);

                Thread.sleep(1500);
            }

            String rawArticles = rawArticlesBuilder.toString();

            if (rawArticles.trim().isEmpty()) {
                System.out.println("========== 수집된 기사가 없어 AI 클리핑을 종료합니다 ==========");
                return;
            }

            // 수집된 키워드와 출처 데이터를 AI 문서 생성기에 파라미터로 전달
            String generatedContent = generateDetailedArticleViaOpenAI(rawArticles, usedKeywords, usedSources);

            // DB에 저장 (Mapper에서 기본적으로 display_yn='N' 상태로 INSERT 됨)
            AiClippingDTO clippingDTO = new AiClippingDTO();
            clippingDTO.setTitle(generatedTitle);
            clippingDTO.setContent(generatedContent);

            int insertCnt = aiClippingMapper.insertAiClipping(clippingDTO);

            if (insertCnt > 0 && clippingDTO.getSeq() != null) {
                System.out.println("========== AI 클리핑 생성 완료 (SEQ: " + clippingDTO.getSeq() + ", 상태: 미노출) ==========");

                // 발송 여부(isSend)에 따른 분기 처리 (10시 스케줄러는 false이므로 여기를 타지 않음)
                if (isSend) {
                    newsletterService.sendClippingNewsletter(
                            clippingDTO.getSeq(),
                            clippingDTO.getTitle(),
                            clippingDTO.getContent()
                    );
                    System.out.println("========== AI 클리핑 뉴스레터 수동 즉시 발송 완료 ==========");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== AI 클리핑 프로세스 실행 중 오류 발생 ==========");
        }
    }

    // Daum 뉴스 크롤링 메서드
    private String fetchArticlesFromDaum(String keyword, List<String[]> usedSources) {
        StringBuilder articlesBuilder = new StringBuilder();
        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            String url = "https://search.daum.net/search?w=news&q=" + encodedKeyword + "&sort=rec";

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(5000)
                    .get();

            Elements newsElements = doc.select(".c-item-content");
            int count = 0;
            for (Element element : newsElements) {
                if (count >= 3) break;

                Element titleElement = element.select(".item-title").first();
                if (titleElement == null) continue;

                String title = titleElement.text();
                String link = "";
                if (titleElement.tagName().equals("a")) {
                    link = titleElement.attr("href");
                } else if (titleElement.select("a").first() != null) {
                    link = titleElement.select("a").first().attr("href");
                } else if (element.select("a").first() != null) {
                    link = element.select("a").first().attr("href");
                }

                if (link != null && !link.isEmpty() && !link.startsWith("http")) {
                    link = "https://search.daum.net/search" + (link.startsWith("/") ? "" : "/") + link;
                }

                if (link == null || link.isEmpty()) {
                    link = url;
                }

                String summary = element.select(".conts-desc").text();

                // 1. [날짜 추출] 검색 목록 페이지 텍스트에서 날짜만 추출
                String fullText = element.text();
                fullText = fullText.replace(title, "").replace(summary, "");
                fullText = fullText.replace("동영상 첨부된 문서", "")
                        .replace("사진 첨부된 문서", "")
                        .replace("음성 첨부된 문서", "")
                        .replace("다음뉴스", "")
                        .replaceAll("관련기사\\s*\\d+건?", "")
                        .replaceAll("관련뉴스\\s*\\d+건?", "")
                        .replaceAll("[|·ⓒ]", " ")
                        .trim();

                String articleDate = "";
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d{4}\\.\\d{2}\\.\\d{2}\\.?|\\d+\\s*(시간|분|일|주|개월)\\s*전|어제)");
                java.util.regex.Matcher matcher = pattern.matcher(fullText);

                if (matcher.find()) {
                    articleDate = matcher.group(1).trim();
                }

                if (articleDate.isEmpty()) {
                    articleDate = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                } else if (articleDate.endsWith(".")) {
                    articleDate = articleDate.substring(0, articleDate.length() - 1);
                }

                // 2. [언론사명 추출] 완벽 복구된 기사 링크 직접 접속(메타 태그 크롤링) 로직
                String publisher = "언론사";
                try {
                    Document articleDoc = Jsoup.connect(link)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                            .timeout(3000)
                            .get();

                    Element ogSiteName = articleDoc.selectFirst("meta[property=og:site_name]");
                    if (ogSiteName != null && ogSiteName.hasAttr("content")) {
                        String siteName = ogSiteName.attr("content");
                        // "Daum | 국민일보" 같은 형태에서 "Daum | " 제거
                        publisher = siteName.replace("Daum", "").replace("|", "").trim();
                    }
                } catch (Exception e) {
                    // 링크 접속 실패 시(타임아웃 등) fallback 로직: 기존 소거 텍스트 활용
                    String leftover = fullText;
                    if (!articleDate.isEmpty()) {
                        leftover = leftover.replace(articleDate, "");
                    }
                    publisher = leftover.replaceAll("(?<!\\S)\\d+(?!\\S)", "").replaceAll("\\s{2,}", " ").trim();
                }

                // 최종 방어 로직
                if (publisher.isEmpty() || publisher.matches("^[0-9\\s\\p{P}]+$")) {
                    publisher = "언론사";
                }

                articlesBuilder.append("제목 : ").append(title).append("\n");
                articlesBuilder.append("원본링크 : ").append(link).append("\n");
                articlesBuilder.append("내용 : ").append(summary).append("\n\n");

                usedSources.add(new String[]{publisher, link, title, articleDate});
                count++;
            }
        } catch (Exception e) {
            System.err.println("Daum 크롤링 실패 [" + keyword + "]: " + e.getMessage());
        }
        return articlesBuilder.toString();
    }

    // OpenAI API 통신 메서드
    private String generateDetailedArticleViaOpenAI(String rawArticles, List<String> keywords, List<String[]> sources) {

        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String prompt = "다음 수집된 해양레저 관련 기사 데이터를 바탕으로, 뉴스레터 독자들을 위한 심층적이고 상세한 분석 기사를 작성해줘.\n" +
                "반드시 아래의 요구사항을 엄격하게 지켜서 HTML 태그 형식으로만 답변해.\n\n" +
                "[요구사항]\n" +
                "1. 전체 내용을 4~5개의 소주제(섹션)로 나누되, 분량이 너무 길지 않도록 **각 섹션당 본문은 반드시 정확히 2개의 문단**으로만 핵심을 요약해서 작성할 것.\n" +
                "2. (가장 중요: 여백 확보 및 순번 표기) 각 섹션의 제목은 반드시 '1.', '2.', '3.' 처럼 순번을 매겨서 아래 형식의 <h3> 태그를 사용할 것. (단락 구분 여백 포함)\n" +
                "   -> <h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>1. 섹션 제목</h3>\n" +
                "3. (가장 중요: 단락 띄어쓰기) 본문 내용은 가독성을 위해 단락마다 반드시 아래 형식의 <p> 태그를 사용할 것. (각 섹션당 <p>태그는 딱 2개씩만 생성할 것)\n" +
                "   -> <p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>본문 내용...</p>\n" +
                "4. 제공된 기사 데이터의 '원본링크'를 반드시 활용하여, 본문 문맥 중 텍스트에 <a> 태그로 하이퍼링크를 걸어줄 것.\n" +
                "   -> 하이퍼링크 스타일 양식: <a href='원본링크' target='_blank' style='color:#222222; text-decoration:underline; text-underline-offset:4px; font-weight:bold;'>키워드</a>\n" +
                "5. 단순 요약이 아닌, 독자에게 인사이트를 제공하는 전문적인 기사 톤으로 작성할 것.\n" +
                "6. 인사말이나 맺음말 없이 바로 <h3> 태그로 시작하는 본문 HTML만 출력하고, 문장이 중간에 잘리지 않도록 반드시 끝맺음을 완벽하게 할 것.\n" +
                "7. [매우 중요] 매일 비슷하거나 정형화된 서론, 결론, 문장 구조가 반복되지 않도록 주의할 것. 매번 새로운 시각, 다채로운 어휘, 그리고 트렌디한 표현 방식을 도입하여 독자가 지루하지 않게 작성할 것.\n\n" +
                "[수집된 기사 데이터]\n" + rawArticles;

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", Collections.singletonList(message));
        body.put("max_completion_tokens", 8000);
        body.put("temperature", 0.8);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", request, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> messageResp = (Map<String, Object>) choices.get(0).get("message");
                String aiContent = (String) messageResp.get("content");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTime = LocalDateTime.now().format(formatter);

                List<Map<String, Object>> parsedSources = new ArrayList<>();

                for (String[] src : sources) {
                    Map<String, Object> map = new HashMap<>();
                    String pub = src[0];
                    String url = src[1];
                    String title = src[2];
                    String rawDate = src.length > 3 ? src[3] : "";

                    java.time.LocalDate calculatedDate = java.time.LocalDate.now();
                    try {
                        if (rawDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
                            calculatedDate = java.time.LocalDate.parse(rawDate, java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                        } else if (rawDate.contains("어제")) {
                            calculatedDate = java.time.LocalDate.now().minusDays(1);
                        } else if (rawDate.matches("\\d+\\s*일\\s*전")) {
                            int days = Integer.parseInt(rawDate.replaceAll("[^0-9]", ""));
                            calculatedDate = java.time.LocalDate.now().minusDays(days);
                        }
                    } catch (Exception e) {
                        calculatedDate = java.time.LocalDate.now();
                    }

                    String displayDate = calculatedDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "(" + calculatedDate.format(java.time.format.DateTimeFormatter.ofPattern("E", java.util.Locale.KOREAN)) + ")";

                    map.put("publisher", pub);
                    map.put("url", url);
                    map.put("title", title);
                    map.put("date", calculatedDate);
                    map.put("displayDate", displayDate);

                    parsedSources.add(map);
                }

                // 날짜 내림차순 정렬 (가장 최신 기사가 위로 오도록)
                parsedSources.sort((m1, m2) -> ((java.time.LocalDate) m2.get("date")).compareTo((java.time.LocalDate) m1.get("date")));

                // 대제목 블록에 사용할 기간(최소~최대) 텍스트 추출 (내림차순이므로 인덱스 역순)
                String dateRange = "";
                if (!parsedSources.isEmpty()) {
                    java.time.format.DateTimeFormatter mdFormatter = java.time.format.DateTimeFormatter.ofPattern("M. d.");
                    String startDate = ((java.time.LocalDate) parsedSources.get(parsedSources.size() - 1).get("date")).format(mdFormatter);
                    String endDate = ((java.time.LocalDate) parsedSources.get(0).get("date")).format(mdFormatter);

                    if (startDate.equals(endDate)) {
                        dateRange = startDate;
                    } else {
                        dateRange = startDate + " ~ " + endDate;
                    }
                }

                StringBuilder headerHtml = new StringBuilder();

                // 1. 출처 리스트 영역 먼저 추가
                headerHtml.append("<div style='margin-bottom: 40px; padding: 20px; background-color: #f8f9fa; border-top: 4px solid #1d5cad; border-bottom: 1px solid #ddd;'>");
                headerHtml.append("   <strong style='color: #1d5cad; display:block; margin-bottom:15px; font-size: 16px;'>■ 기사</strong>");
                headerHtml.append("   <ul style='list-style: none; padding: 0; margin: 0;'>");

                for (int i = 0; i < parsedSources.size(); i++) {
                    Map<String, Object> ps = parsedSources.get(i);
                    headerHtml.append("<li style='font-size: 14px; color: #333; margin-bottom: 8px;'>");
                    headerHtml.append("출처 (").append(i + 1).append(") ").append(ps.get("displayDate")).append(" | ");
                    headerHtml.append("<a href='").append(ps.get("url")).append("' target='_blank' style='color:#1d5cad; font-weight:bold; text-decoration:underline; text-underline-offset:2px;'>").append(ps.get("title")).append("</a>");
                    headerHtml.append(" | ").append(ps.get("publisher"));
                    headerHtml.append("</li>");
                }
                headerHtml.append("   </ul>");
                headerHtml.append("</div>");

                // 2. 대제목 블록 나중에 추가
                if (!dateRange.isEmpty()) {
                    headerHtml.append("<div style='background-color: #1d5cad; color: #ffffff; text-align: center; padding: 25px 15px; font-size: 24px; font-weight: bold; margin-bottom: 40px; border-radius: 5px;'>");
                    headerHtml.append("해양레저산업 기사 요약 (기간: ").append(dateRange).append(")");
                    headerHtml.append("</div>");
                }

                String footerHtml = "<div style='margin-top: 50px; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #1d5cad; border-radius: 5px; text-align: left; font-size: 14px; color: #444; line-height: 1.6;'>" +
                        "   <strong style='color: #1d5cad;'>■ 수집 키워드 :</strong> ";
                for (String kw : keywords) {
                    footerHtml += "#" + kw + " ";
                }
                footerHtml += "<br><br>" +
                        "   <strong style='color: #1d5cad;'>■ 작성자 :</strong> 경기국제보트쇼 AI 브리핑 봇<br>" +
                        "   <strong style='color: #1d5cad;'>■ 생성 모델 :</strong> OpenAI " + openAiModel + "<br>" +
                        "   <strong style='color: #1d5cad;'>■ 생성 일시 :</strong> " + currentTime + "<br>" +
                        "   <span style='font-size: 12px; color: #888; display: block; margin-top: 8px; margin-bottom: 12px;'>* 본 기사는 인공지능 모델이 자동 수집 및 요약한 내용으로, 원본 기사의 논조와 일부 다를 수 있습니다.</span>" +
                        "</div>";

                return headerHtml.toString() + aiContent + footerHtml;
            }
        } catch (Exception e) {
            System.err.println("OpenAI API 호출 실패: " + e.getMessage());
        }

        return "<h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>오늘의 해양레저 주요 동향</h3><p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>기사 요약을 생성하는 중 일시적인 오류가 발생했습니다. 자세한 내용은 홈페이지를 참조해 주세요.</p>";
    }
}