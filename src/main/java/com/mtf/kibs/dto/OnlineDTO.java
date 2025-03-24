package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OnlineDTO {
    String seq; //SEQ
    String id; //ID
    String userId;
    String transferYear;
    String productNameKo; //제품명(국문)
    String productNameEn; //제품명(영문)
    String productOptionBig; //제품분류(대)
    String productOptionSmall; //제품분류(소)
    String productIntroKo; //제품설명(국문)
    String productIntroEn; //제품설명(영문)
    String productIntroVideo; //제품영상
    String productWidth; //전장
    String productHorsePower; //마력
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}