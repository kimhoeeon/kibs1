package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PassDetailDTO {
    private String companyNameKo;
    private String representativeName; // 대표 담당자명 (e.g., info.name)
    private String representativePhone; // 대표 담당자 연락처 (e.g., info.phone)
    private String approvalStatus;
    private String initRegiDttm;

    // pass_new 테이블에서 가져올 정보
    private String passName;
    private String passPositionKo;

    // 계산된 값
    private int passTotalCount; // 해당 업체의 총 출입증 신청 인원
}