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

    // 1. 매일 아침 9시 자동 실행 (무조건 발송)
    /*@Scheduled(cron = "0 0 9 * * MON-FRI", zone = "Asia/Seoul")
    public void generateAndSendAiClippingScheduled() {
        System.out.println("========== AI 클리핑 스케줄러 자동 실행 ==========");
        processAiClipping(true); // 스케줄러는 항상 생성+발송 처리
    }*/

    // 2. 실제 클리핑 수집, 생성, 발송을 담당하는 코어 로직
    public void processAiClipping(boolean isSend) {
        System.out.println("========== AI 클리핑 코어 프로세스 시작 (발송여부: " + isSend + ") ==========");

        try {
            // 다중 도메인 환경에서 5개의 스케줄러가 동시 실행되는 것을 방지하기 위해 0~10초 랜덤 대기
            int sleepTime = new Random().nextInt(10000);
            Thread.sleep(sleepTime);

            String todayDate = java.time.LocalDate.now().toString();
            String generatedTitle = "[경기국제보트쇼 AI 클리핑] " + todayDate + " 해양레저산업 주요 동향";

            // 오늘 날짜의 기사가 이미 DB에 생성되었는지 확인
            AiClippingDTO searchDto = new AiClippingDTO();
            searchDto.setTitle(todayDate);
            int todayClippingCount = aiClippingMapper.selectAiClippingCount(searchDto);

            if (todayClippingCount > 0) {
                System.out.println("========== 오늘 날짜의 AI 클리핑이 이미 존재하여 중복 실행을 취소합니다 ==========");
                return; // 이미 다른 도메인 스레드에서 생성 및 발송을 마쳤다면 즉시 종료
            }

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
            List<String[]> usedSources = new ArrayList<>(); // {언론사, 링크, 제목}

            // 4. 추출된 키워드들로 기사 수집
            for (AiClippingKeywordDTO kw : selectedKeywords) {
                String targetKeyword = kw.getKeyword();
                usedKeywords.add(targetKeyword); // 사용된 키워드 기록
                System.out.println("-> 수집 키워드: " + targetKeyword);

                // 크롤러에 출처 리스트(usedSources)를 넘겨서 데이터를 채워오도록 처리
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

            // DB에 저장
            AiClippingDTO clippingDTO = new AiClippingDTO();
            clippingDTO.setTitle(generatedTitle);
            clippingDTO.setContent(generatedContent);

            int insertCnt = aiClippingMapper.insertAiClipping(clippingDTO);

            if (insertCnt > 0 && clippingDTO.getSeq() != null) {
                System.out.println("========== AI 클리핑 생성 완료 (SEQ: " + clippingDTO.getSeq() + ") ==========");

                // 발송 여부(isSend)에 따른 분기 처리
                if (isSend) {
                    newsletterService.sendClippingNewsletter(
                            clippingDTO.getSeq(),
                            clippingDTO.getTitle(),
                            clippingDTO.getContent()
                    );
                    System.out.println("========== AI 클리핑 뉴스레터 발송 완료 ==========");
                } else {
                    System.out.println("========== AI 클리핑 뉴스레터 발송 생략 (생성만 수행) ==========");
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
                String link = titleElement.tagName().equals("a") ? titleElement.attr("href") : titleElement.select("a").attr("href");
                if (link == null || link.isEmpty()) {
                    link = element.select("a").first().attr("href");
                }

                String summary = element.select(".conts-desc").text();

                // 신문사(언론사) 파싱 로직
                Element pubElement = element.select(".info_news").first();
                if (pubElement == null) pubElement = element.select(".txt_info").first(); // 대체 클래스 확인
                String publisher = pubElement != null ? pubElement.text() : "언론사";
                // 가독성을 위해 불필요한 '다음뉴스' 나 '|' 기호 등 제거
                publisher = publisher.replace("다음뉴스", "").replace("|", "").trim();

                articlesBuilder.append("제목 : ").append(title).append("\n");
                articlesBuilder.append("원본링크 : ").append(link).append("\n");
                articlesBuilder.append("내용 : ").append(summary).append("\n\n");

                // 출처 데이터 누적: {언론사, 링크, 기사제목}
                usedSources.add(new String[]{publisher, link, title});

                count++;
            }
        } catch (Exception e) {
            System.err.println("Daum 크롤링 실패 [" + keyword + "]: " + e.getMessage());
        }
        return articlesBuilder.toString();
    }

    // OpenAI API 통신 메서드
    private String generateDetailedArticleViaOpenAI(String rawArticles, List<String> keywords, List<String[]> sources) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String prompt = "다음 수집된 해양레저 관련 기사 데이터를 바탕으로, 뉴스레터 독자들을 위한 심층적이고 상세한 분석 기사를 작성해줘.\n" +
                "반드시 아래의 요구사항을 엄격하게 지켜서 HTML 태그 형식으로만 답변해.\n\n" +
                "[요구사항]\n" +
                "1. 전체 내용을 최소 4~5개의 소주제(섹션)로 나누어 아주 길고 상세하게 서술할 것.\n" +
                "2. (가장 중요: 여백 확보) 각 섹션의 제목은 반드시 아래 형식의 <h3> 태그를 사용할 것. (단락 구분 여백 포함)\n" +
                "   -> <h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>섹션 제목</h3>\n" +
                "3. (가장 중요: 단락 띄어쓰기) 본문 내용은 가독성을 위해 단락마다 반드시 아래 형식의 <p> 태그를 사용할 것. (문단 사이에 여백이 크게 생기도록 margin-bottom을 꼭 넣을 것)\n" +
                "   -> <p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>본문 내용...</p>\n" +
                "4. 제공된 기사 데이터의 '원본링크'를 반드시 활용하여, 본문 문맥 중 텍스트에 <a> 태그로 하이퍼링크를 걸어줄 것.\n" +
                "   -> 하이퍼링크 스타일 양식: <a href='원본링크' target='_blank' style='color:#222222; text-decoration:underline; text-underline-offset:4px; font-weight:bold;'>키워드</a>\n" +
                "5. 단순 요약이 아닌, 독자에게 인사이트를 제공하는 전문적인 기사 톤으로 작성할 것.\n" +
                "6. 인사말이나 맺음말 없이 바로 <h3> 태그로 시작하는 본문 HTML만 출력하고, 문장이 중간에 잘리지 않도록 반드시 끝맺음을 완벽하게 할 것.\n\n" +
                "[수집된 기사 데이터]\n" + rawArticles;

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", Collections.singletonList(message));
        body.put("max_completion_tokens", 8000);
        body.put("temperature", 0.6);

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

                // 출처 표기를 위한 HTML 생성
                StringBuilder sourceHtml = new StringBuilder();
                sourceHtml.append("<div style='margin-top: 15px; padding-top: 15px; border-top: 1px dashed #ccc;'>");

                // 1. 수집 키워드 표기 (#키워드1 #키워드2)
                sourceHtml.append("   <strong style='color: #1d5cad; display:block; margin-bottom:5px;'>■ 수집 키워드</strong>");
                sourceHtml.append("   <span style='color: #555; font-size: 13px; font-weight: bold;'>");
                for (String kw : keywords) {
                    sourceHtml.append("#").append(kw).append(" ");
                }
                sourceHtml.append("   </span><br><br>");

                // 2. 출처 표기 (출처1) 신문사 | 원문링크)
                sourceHtml.append("   <strong style='color: #1d5cad; display:block; margin-bottom:5px;'>■ 기사 출처</strong>");
                sourceHtml.append("   <ul style='list-style: none; padding: 0; margin: 0;'>");
                for (int i = 0; i < sources.size(); i++) {
                    String[] src = sources.get(i);
                    String pub = src[0];    // 언론사
                    String url = src[1];    // 링크
                    String title = src[2];  // 기사 제목

                    sourceHtml.append("<li style='font-size: 12px; color: #666; margin-bottom: 5px; line-height: 1.4;'>");
                    sourceHtml.append("출처").append(i + 1).append(") ").append(pub).append(" | ");
                    sourceHtml.append("<a href='").append(url).append("' target='_blank' style='color:#666; text-decoration:underline;'>원문링크</a>");
                    sourceHtml.append(" <span style='color:#999;'>- ").append(title).append("</span>");
                    sourceHtml.append("</li>");
                }
                sourceHtml.append("   </ul>");
                sourceHtml.append("</div>");

                // 최종 꼬리말 조립
                String footerHtml = "<div style='margin-top: 50px; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #1d5cad; border-radius: 5px; text-align: left; font-size: 14px; color: #444; line-height: 1.6;'>" +
                        "   <strong style='color: #1d5cad;'>■ 작성자 :</strong> 경기국제보트쇼 AI 브리핑 봇<br>" +
                        "   <strong style='color: #1d5cad;'>■ 생성 모델 :</strong> OpenAI " + openAiModel + "<br>" +
                        "   <strong style='color: #1d5cad;'>■ 생성 일시 :</strong> " + currentTime + "<br>" +
                        "   <span style='font-size: 12px; color: #888; display: block; margin-top: 8px; margin-bottom: 12px;'>* 본 기사는 인공지능 모델이 자동 수집 및 요약한 내용으로, 원본 기사의 논조와 일부 다를 수 있습니다.</span>" +
                        sourceHtml.toString() +
                        "</div>";

                return aiContent + footerHtml;
            }
        } catch (Exception e) {
            System.err.println("OpenAI API 호출 실패: " + e.getMessage());
        }

        return "<h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>오늘의 해양레저 주요 동향</h3><p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>기사 요약을 생성하는 중 일시적인 오류가 발생했습니다. 자세한 내용은 홈페이지를 참조해 주세요.</p>";
    }
}