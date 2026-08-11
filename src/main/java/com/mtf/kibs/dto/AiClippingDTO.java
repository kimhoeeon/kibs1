package com.mtf.kibs.dto;

import lombok.Data;

@Data
public class AiClippingDTO {
    private String seq;
    private String title;
    private String content;
    private int viewCnt;
    private int shareCnt;
    private int sendSuccessCnt;
    private int sendFailCnt;
    private String delYn;
    private String regDate;
    private String modDate;

    // 페이징 및 검색용 필드 (SearchDTO 상속 대신 내부 처리 시)
    private int offset;
    private int limit;
    private String prevSeq;
    private String nextSeq;
}