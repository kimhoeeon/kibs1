package com.mtf.kibs.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * AI 뉴스 클리핑 스케줄 진입점.
 *
 * 월요일 08:00 생성 → 같은 날 10:00 노출·발송 (발주사 확정 2026-10-28: 검수·승인 불필요, 공휴일 무관).
 * 실제 수집·선정·렌더·저장·발행 로직은 {@link AiClippingPipelineV2} 에 있다.
 *
 * 2026-10-28 이전의 legacy 흐름(금요일 08:00 생성 → 월요일 08:00 발행, 키워드당 1건 GPT 선정)은 V2 전환과 함께 제거했다.
 * 필요하면 git 이력(커밋 c4db276 이전)의 이 파일을 참고.
 */
@Component
public class AiClippingScheduler {

    @Autowired
    private AiClippingPipelineV2 pipelineV2;

    /**
     * 월 10:00 자동 노출·발송을 승인(approve_yn='Y')된 글로 제한할지.
     * 기본 false (생성된 글을 그대로 10:00 에 발송). 발송을 막으려면 10:00 전에 관리자에서 해당 글을 삭제한다.
     * true 로 켜면 관리자 목록에 승인 배지·버튼이 나타나고(AdminNewsletterController → JSP → clipping.js), 승인 글만 발송한다.
     */
    @Value("${ai.clipping.publish-requires-approval:false}")
    private boolean publishRequiresApproval;

    public boolean isPublishRequiresApproval() {
        return publishRequiresApproval;
    }

    /** 1. [생성] 매주 월요일 08:00 — 지난 월요일 08:00 이후 기사로 클리핑 생성 (미노출) */
    @Scheduled(cron = "0 0 8 * * MON", zone = "Asia/Seoul")
    public void generateAt8AM() {
        System.out.println("========== [월요일 08:00 AM] AI 클리핑 생성 스케줄러 시작 (미노출) ==========");
        pipelineV2.generate(false);
    }

    /** 2. [발행] 매주 월요일 10:00 — 오늘 생성된 미노출 글을 노출 전환 후 구독자 발송 */
    @Scheduled(cron = "0 0 10 * * MON", zone = "Asia/Seoul")
    public void publishAt10AM() {
        System.out.println("========== [월요일 10:00 AM] AI 클리핑 발행 스케줄러 시작 (승인 필수: " + publishRequiresApproval + ") ==========");
        pipelineV2.publishToday(publishRequiresApproval);
    }

    /**
     * 관리자 수동 생성 진입점 (/mng/center/board/clipping/manual-update.do).
     * @param isSend true 면 생성 직후 구독자 발송까지 수행
     * @return 생성 성공 여부
     */
    public boolean processAiClipping(boolean isSend) {
        return pipelineV2.generate(isSend) != null;
    }
}
