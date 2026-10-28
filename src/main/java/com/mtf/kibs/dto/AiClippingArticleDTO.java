package com.mtf.kibs.dto;

import lombok.Data;

/**
 * AI 클리핑 기사 단위 레코드 (ai_clipping_article).
 * V2 파이프라인이 수집·전처리·점수·선정 결과를 기사마다 저장한다.
 */
@Data
public class AiClippingArticleDTO {

    public static final String STATUS_SELECTED = "SELECTED";             // 브리핑 토픽에 사용된 기사
    public static final String STATUS_LISTED = "LISTED";                 // 브리핑에는 안 쓰였지만 "최신 관련 기사" 목록에 노출
    public static final String STATUS_EXCLUDED_BLACKLIST = "EXCLUDED_BLACKLIST"; // 제목 블랙리스트로 제외
    public static final String STATUS_EXCLUDED_SCORE = "EXCLUDED_SCORE"; // GPT 연관도 0점(또는 기준 미만)으로 제외

    private Long seq;
    private Long clippingSeq;
    private String keyword;
    private String title;
    private String link;
    private String publisher;
    private String summary;
    private String articleDate;      // yyyy-MM-dd
    private int relevanceScore;
    private String status;
    private Integer topicNo;
    private String regDate;
}
