package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MngBuyerDTO {
    Integer rownum; //순번
    String seq; //업체SEQ
    String id; //ID
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
    String buyerCompanyName; //업체명
    String buyerCompanyCountry; //국가
    String buyerCompanyLocation; //지역
    String buyerCompanyHomepage; //홈페이지주소
    String buyerCompanyDepart; //부서
    String buyerCompanyPosition; //직위
    String buyerCompanyEmail; //이메일
    String buyerCompanyTel; //전화번호
    String buyerCompanyPhone; //휴대전화
    String buyerCompanyFax; //Fax
    String buyerCompanyItem; //취급품목
    String buyerCompanyInviteReason; //초청사유
    String buyerCompanyProgressYn; //계약진행여부
    String buyerCompanyHope; //희망사항
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}