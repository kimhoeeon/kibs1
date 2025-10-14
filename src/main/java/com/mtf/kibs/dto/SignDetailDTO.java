package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignDetailDTO {
    // 기본정보
    private String companyNameKo;
    private String representativeName; // 대표 담당자명
    private String representativePosition; // 대표 담당자 직책
    private String representativePhone; // 대표 담당자 연락처

    // 상호간판 신청 정보
    private String boothType; // 독립/조립
    private String companySignNameKo;
    private String companySignNameEn;
    private String initRegiDttm;
    private String approvalStatus;
}