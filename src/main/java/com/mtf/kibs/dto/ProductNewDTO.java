package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductNewDTO {
    String seq; //SEQ
    String exSeq; //Exhibitor SEQ
    String productOptionBig; //제품분류(대)
    String productOptionSmall; //제품분류(소)
    String productNameKo; //제품명(국문)
    String productNameEn; //제품명(영문)
    int productQty; //수량
    String productBrand; //브랜드(제조사)
    int productLength; //길이
    int productWidth; //너비
    int productHeight; //높이
    int productWeight; //중량
    String productMaterial; //소재
    int productYear; //연식
    String productIntroKo; //제품설명(국문)
    String productIntroEn; //제품설명(영문)
    String productLink; //제품영상
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}