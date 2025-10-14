package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UtilityDetailDTO {
    // 기본정보
    private String companyNameKo;
    private String representativeName;
    private String representativePosition;
    private String representativePhone;

    // 유틸리티 신청 정보
    private String boothType;
    private Integer utilityPrcSum;
    private String initRegiDttm;
}