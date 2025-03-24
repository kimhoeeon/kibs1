package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PartnerDTO {
    String seq; //SEQ
    String visitorSeq; //SEQ
    String name; //이름
    String phone; //ID
    String partnerName; //동반자이름
    String partnerAge; //동반자나이
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}