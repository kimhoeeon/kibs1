package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MngProductDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String transferYear;
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
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
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시

    String boatEntryYn;
    String name;
    String position;
    String phone;
    int productCount;
}