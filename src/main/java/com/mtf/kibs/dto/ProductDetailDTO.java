package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDetailDTO {
    // exhibitor_new 테이블 정보
    private String exhibitorSeq;
    private String companyNameKo;

    // product_new 테이블 정보
    private String productOptionBig;
    private String productOptionSmall;
    private String productIsNew;
    private String productNameKo;
    private Integer productQty;
    private String productBrand;
    private String productFeature;
    private Integer productLength;
    private Integer productWidth;
    private Integer productHeight;
    private Integer productWeight;
    private String productMaterial;
    private Integer productYear;
}