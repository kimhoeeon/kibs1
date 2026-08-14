package com.mtf.kibs.scheduler;

import com.mtf.kibs.dto.AiClippingDTO;
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
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void generateAndSendAiClippingScheduled() {
        System.out.println("========== AI 클리핑 스케줄러 자동 실행 ==========");
        processAiClipping(true); // 스케줄러는 항상 생성+발송 처리
    }

    // 2. 실제 클리핑 수집, 생성, 발송을 담당하는 코어 로직
    public void processAiClipping(boolean isSend) {
        System.out.println("========== AI 클리핑 코어 프로세스 시작 (발송여부: " + isSend + ") ==========");

        try {
            // 5가지 카테고리별 세부 키워드 배열 정의
            String[][] keywordGroups = {
                    {"보트", "요트", "레저보트", "파워보트", "세일링 요트", "고무보트", "낚시보트"},
                    {"마리나", "마리나 산업", "마리나 항만"},
                    {"해양관광", "해양레저", "수상레저", "해양스포츠"},
                    {"전기보트", "친환경 선박", "수소선박", "하이브리드 선박"},
                    {"보트 엔진", "선외기", "해양레저장비", "선박 기자재", "해양 안전"}
            };

            StringBuilder rawArticlesBuilder = new StringBuilder();
            Random random = new Random();

            // 5개 카테고리에서 각각 랜덤하게 키워드를 1개씩 추출하여 검색
            for (String[] group : keywordGroups) {
                String targetKeyword = group[random.nextInt(group.length)];
                System.out.println("-> 수집 키워드: " + targetKeyword);

                String articles = fetchArticlesFromDaum(targetKeyword);
                rawArticlesBuilder.append(articles);

                // 연속적인 크롤링으로 인한 차단 방지를 위해 1.5초 대기
                Thread.sleep(1500);
            }

            String rawArticles = rawArticlesBuilder.toString();

            if (rawArticles.trim().isEmpty()) {
                System.out.println("========== 수집된 기사가 없어 AI 클리핑을 종료합니다 ==========");
                return;
            }

            // OpenAI API 연동하여 기사 상세 작성 (링크 포함)
            String generatedContent = generateDetailedArticleViaOpenAI(rawArticles);

            String todayDate = java.time.LocalDate.now().toString();
            String generatedTitle = "[경기국제보트쇼 AI 클리핑] " + todayDate + " 해양레저산업 주요 동향";

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
    private String fetchArticlesFromDaum(String keyword) {
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

                articlesBuilder.append("제목: ").append(title).append("\n");
                articlesBuilder.append("원본링크: ").append(link).append("\n");
                articlesBuilder.append("내용: ").append(summary).append("\n\n");
                count++;
            }
        } catch (Exception e) {
            System.err.println("Daum 크롤링 실패 [" + keyword + "]: " + e.getMessage());
        }
        return articlesBuilder.toString();
    }

    // OpenAI API 통신 메서드
    private String generateDetailedArticleViaOpenAI(String rawArticles) {
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

                // 3단계: 현재 시간 가져오기 및 AI 꼬리말 포맷팅 생성
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTime = LocalDateTime.now().format(formatter);

                // 깔끔한 박스 형태의 꼬리말 HTML
                String footerHtml = "<div style='margin-top: 50px; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #1d5cad; border-radius: 5px; text-align: left; font-size: 14px; color: #444; line-height: 1.6;'>" +
                        "   <strong style='color: #1d5cad;'>■ 작성자 :</strong> 경기국제보트쇼 AI 브리핑 봇<br>" +
                        "   <strong style='color: #1d5cad;'>■ 생성 모델 :</strong> OpenAI " + openAiModel + "<br>" +
                        "   <strong style='color: #1d5cad;'>■ 생성 일시 :</strong> " + currentTime + "<br>" +
                        "   <span style='font-size: 12px; color: #888; display: block; margin-top: 8px;'>* 본 기사는 인공지능 모델이 자동 수집 및 요약한 내용으로, 원본 기사의 논조와 일부 다를 수 있습니다.</span>" +
                        "</div>";

                // 원본 콘텐츠 끝에 꼬리말을 덧붙여서 반환
                return aiContent + footerHtml;
            }
        } catch (Exception e) {
            System.err.println("OpenAI API 호출 실패: " + e.getMessage());
        }

        return "<h3 style='margin-top: 40px; margin-bottom: 20px; font-size: 22px; color: #1d5cad; border-bottom: 2px solid #1d5cad; padding-bottom: 10px;'>오늘의 해양레저 주요 동향</h3><p style='margin-bottom: 25px; line-height: 1.8; font-size: 16px; color: #333;'>기사 요약을 생성하는 중 일시적인 오류가 발생했습니다. 자세한 내용은 홈페이지를 참조해 주세요.</p>";
    }
}