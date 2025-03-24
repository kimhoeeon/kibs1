package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String kibsYear; //연도
    String categories; //카테고리
    String companyName; //업체명
    String chargePersonName; //담당자명
    String chargePersonPhone; //휴대전화
    String chargePersonEmail; //이메일
    String homepage; //홈페이지주소
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}