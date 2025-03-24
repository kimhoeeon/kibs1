package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PassDTO {
    String seq; //SEQ
    String id; //업체ID
    String userId; //ID
    String transferYear; //연도
    String passName; //출입증 성명(국문)
    String passLastName; //출입증 성(영문)
    String passFirstName; //출입증 이름(영문)
    String passPositionKo; //직책(국문)
    String passPositionEn; //직책(영문)
    String passNote; //출입증 비고
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}