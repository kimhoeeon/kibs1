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

    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void generateAndSendAiClipping() {
        System.out.println("========== AI 클리핑 스케줄러 실행 시작 ==========");

        try {
            // 기획안에 명시된 5가지 카테고리별 세부 키워드 배열 정의
            String[][] keywordGroups = {
                    {"보트", "요트", "레저보트", "파워보트", "세일링 요트", "고무보트", "낚시보트"},
                    {"마리나", "마리나 산업", "마리나 항만"},
                    {"해양관광", "해양레저", "수상레저", "해양스포츠"},
                    {"전기보트", "친환경 선박", "수소선박", "하이브리드 선박"},
                    {"보트 엔진", "선외기", "해양레저장비", "선박 기자재", "해양 안전"}
            };

            StringBuilder rawArticlesBuilder = new StringBuilder();
            Random random = new Random();

            // 5개 카테고리에서 각각 랜덤하게 키워드를 1개씩 추출하여 검색 (다양성 확보 및 검색엔진 차단 방지)
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

            // OpenAI API 연동하여 기사 요약
            String summarizedContent = generateSummaryViaOpenAI(rawArticles);

            String todayDate = java.time.LocalDate.now().toString();
            String generatedTitle = "[경기국제보트쇼 AI 클리핑] " + todayDate + " 해양레저산업 주요 동향";

            // DB에 저장
            AiClippingDTO clippingDTO = new AiClippingDTO();
            clippingDTO.setTitle(generatedTitle);
            clippingDTO.setContent(summarizedContent);

            int insertCnt = aiClippingMapper.insertAiClipping(clippingDTO);

            if (insertCnt > 0 && clippingDTO.getSeq() != null) {
                System.out.println("========== AI 클리핑 생성 완료 (SEQ: " + clippingDTO.getSeq() + ") ==========");

                // 생성된 기사를 뉴스레터 구독자에게 발송
                newsletterService.sendClippingNewsletter(
                        clippingDTO.getSeq(),
                        clippingDTO.getTitle(),
                        clippingDTO.getContent()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========== AI 클리핑 스케줄러 실행 중 오류 발생 ==========");
        }
    }

    // Daum 뉴스 크롤링 메서드
    private String fetchArticlesFromDaum(String keyword) {
        StringBuilder articlesBuilder = new StringBuilder();
        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            String url = "https://search.daum.net/search?w=news&q=" + encodedKeyword + "&sort=rec"; // 최신순

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(5000)
                    .get();

            Elements newsElements = doc.select(".c-item-content");
            int count = 0;
            for (Element element : newsElements) {
                if (count >= 3) break; // 각 키워드당 상위 3개 기사만 수집 (토큰 낭비 방지)
                String title = element.select(".item-title").text();
                String summary = element.select(".conts-desc").text();
                articlesBuilder.append("제목: ").append(title).append("\n");
                articlesBuilder.append("내용: ").append(summary).append("\n\n");
                count++;
            }
        } catch (Exception e) {
            System.err.println("Daum 크롤링 실패 [" + keyword + "]: " + e.getMessage());
        }
        return articlesBuilder.toString();
    }

    // OpenAI API 통신 메서드
    private String generateSummaryViaOpenAI(String rawArticles) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String prompt = "다음 수집된 해양레저 관련 기사들을 바탕으로, 오늘 하루의 해양레저산업 동향을 3~4문장으로 깔끔하게 요약해줘. 반드시 HTML <p> 태그와 <br> 태그를 사용하여 문단을 나누어 뉴스레터 본문으로 바로 사용할 수 있게 응답해줘.\n\n[수집된 기사]\n" + rawArticles;

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", Collections.singletonList(message));
        body.put("temperature", 0.5);

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
            System.err.println("OpenAI API 호출 실패: " + e.getMessage());
        }
        return "<p>오늘의 해양레저산업 주요 동향을 안내해 드립니다.</p><p>자세한 내용은 경기국제보트쇼 홈페이지를 참조해 주세요.</p>";
    }
}