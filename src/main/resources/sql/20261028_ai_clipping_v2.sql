-- =====================================================================
-- AI 뉴스 클리핑 V2 (2026-10-28 발주사 요청) 스키마 변경
-- 배포 전 운영 DB(kibs1)에 1회 실행할 것. 실행하지 않으면 ai_clipping 조회(approve_yn)에서 오류가 난다.
-- =====================================================================

-- 1) 승인 상태: 월요일 10:00 자동 노출·발송은 approve_yn='Y' 인 당일 생성 글만 대상
--    (ai.clipping.publish-requires-approval=false 로 두면 승인 없이도 발송)
ALTER TABLE ai_clipping
    ADD COLUMN approve_yn CHAR(1) NOT NULL DEFAULT 'N' COMMENT '관리자 승인 여부 (월 10시 자동 노출·발송 대상)' AFTER display_yn;

-- 2) 기사 단위 저장 (수집·전처리·점수·선정 결과). 공개 상세의 "최신 관련 기사" 영역과 향후 관리자 기사 교체의 기반
CREATE TABLE IF NOT EXISTS ai_clipping_article (
    seq             BIGINT       NOT NULL AUTO_INCREMENT,
    clipping_seq    BIGINT       NOT NULL COMMENT 'ai_clipping.seq',
    keyword         VARCHAR(100)          COMMENT '검색에 사용된 관리자 키워드',
    title           VARCHAR(500) NOT NULL,
    link            VARCHAR(1000) NOT NULL,
    publisher       VARCHAR(100),
    summary         TEXT                   COMMENT '검색 결과 요약문 (내부 참고용, 공개 영역에는 노출하지 않음)',
    article_date    DATE                   COMMENT '수집 시점에 절대 날짜로 고정',
    relevance_score INT          NOT NULL DEFAULT 0 COMMENT 'GPT 연관도 점수 0~100',
    status          VARCHAR(20)  NOT NULL COMMENT 'SELECTED(브리핑 사용) / LISTED(관련 기사 목록) / EXCLUDED_BLACKLIST / EXCLUDED_SCORE',
    topic_no        INT                    COMMENT '브리핑 토픽 번호 (SELECTED 인 경우)',
    reg_date        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (seq),
    KEY idx_clipping_article_clipping (clipping_seq),
    KEY idx_clipping_article_date (article_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI 클리핑 기사 단위 수집·선정 결과';
