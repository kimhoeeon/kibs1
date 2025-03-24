package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BuyerDTO {
    String seq; //SEQ
    String id; //ID
    String userId;
    String transferYear;
    String buyerCompanyName; //회사명
    String buyerCompanyCountry; //국가
    String buyerCompanyLocation; //소재지
    String buyerCompanyHomepage; //홈페이지
    String buyerCompanyDepart; //부서
    String buyerCompanyPosition; //직책
    String buyerCompanyEmail; //이메일
    String buyerCompanyTel; //전화번호
    String buyerCompanyPhone; //휴대전화
    String buyerCompanyFax; //팩스
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