package com.mtf.kibs.scheduler;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AiClippingScheduler {

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private NewsletterService newsletterService;

    // application.properties 에서 설정값 주입
    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${openai.model.summarize}")
    private String openAiModel;

    /**
     * 평일 오전 9시에 실행되는 AI 뉴스 클리핑 스케줄러
     */
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void generateAndSendAiClipping() {
        System.out.println("========== AI 클리핑 스케줄러 실행 시작 ==========");

        try {
            // 1. 외부 검색 API (Daum 검색 API 등) 호출하여 기사 수집 로직 (구현 필요)
            // List<String> articles = fetchArticlesFromDaumAPI();

            // 2. OpenAI API 연동하여 기사 요약 및 HTML 포맷으로 변환 로직 (구현 필요)
            // String summarizedContent = generateSummaryViaOpenAI(articles);

            // 임시 테스트용 데이터
            String todayDate = java.time.LocalDate.now().toString();
            String generatedTitle = "[AI 브리핑] " + todayDate + " 해양레저산업 주요 동향";
            String generatedContent = "<p>오늘의 해양레저, 마리나, 친환경 선박 관련 주요 동향을 요약하여 전달해 드립니다.</p>";

            // 3. DB에 저장
            AiClippingDTO clippingDTO = new AiClippingDTO();
            clippingDTO.setTitle(generatedTitle);
            clippingDTO.setContent(generatedContent);

            int insertCnt = aiClippingMapper.insertAiClipping(clippingDTO);

            if (insertCnt > 0 && clippingDTO.getSeq() != null) {
                System.out.println("========== AI 클리핑 생성 완료 (SEQ: " + clippingDTO.getSeq() + ") ==========");

                // 4. 생성된 기사를 뉴스레터 구독자에게 발송
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
}