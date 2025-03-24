package com.mtf.kibs.dto;

import com.mtf.kibs.entity.AbstractPagingRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChargePersonDTO {
    String seq; //seq
    String id; //exhibitor seq
    String userId; //userId
    String transferYear; //년도
    String chargePersonName; //담당자명
    String chargePersonPosition; //직위
    String chargePersonDepart; //부서
    String chargePersonTel; //전화번호
    String chargePersonPhone; //휴대전화
    String chargePersonEmail; //이메일
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}