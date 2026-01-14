package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DirectoryDTO {
    String seq; //SEQ
    String lang; //언어
    String exSeq;
    String id; //ID
    String userId; //User ID
    String transferYear; //연도
    String approvalStatus; //승인여부
    String prcYn; //입금여부
    String companyLicenseNum; //업체사업자등록번호
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
    String companyAddress; //주소
    String companyAddressDetail; //상세주소
    String factoryAddress; //공장주소
    String factoryAddressDetail; //공장상세주소
    String companyCeo; //업체대표
    String companyTel; //업체전화번호
    String companyHomepage; //업체홈페이지
    String companyFax; //업체FAX
    String industryPart; //산업분류
    String industryPartEtc; //산업분류기타
    String employeeCnt; //임직원수
    String companyIntroVideo; //회사영상소개
    String companyIntroKo; //회사소개(국문)
    String companyIntroEn; //회사소개(영문)
    String companyPurposeKo; //참가목적(국문)
    String companyPurposeEn; //참가목적(영문)
    String newItemIntroKo; //신제품(국문)
    String newItemIntroEn; //신제품(영문)
    String promotionPlan; //프로모션정보
    String name;
    String position;
    String depart;
    String tel;
    String phone;
    String email;
    String productOptionBig;
    String productOptionSmall;
    String productNameKo;
    String productQty;
    String productBrand;
    String productFeature;
    String productLength;
    String productWidth;
    String productHeight;
    String productWeight;
    String productMaterial;
    String productYear;
    String productIntroKo;
    String productIntroEn;
    String productLink;
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}