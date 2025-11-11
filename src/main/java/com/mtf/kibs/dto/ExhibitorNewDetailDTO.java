package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExhibitorNewDetailDTO {

    // 생성자 추가 (transferYear를 받는 경우)
    public ExhibitorNewDetailDTO(String transferYear) {
        this.transferYear = transferYear;
    }

    //  기본 생성자
    public ExhibitorNewDetailDTO() {
    }

    private String seq;
    private String lang;
    private String boothType;
    private Integer deposit; // String -> Integer
    private Integer balance; // String -> Integer
    private String taxYn;
    private String transferYear;

    /* 참가업체정보 */
    private String participationStatus;
    private String approvalStatus;
    private String boothNum;
    private String id;
    private String initRegiDttm;
    private String finalRegiDttm;
    private String prcYn;
    private String bpNum;
    private String contactWriter;
    private String contactDate;
    private String contactContent;
    private String referenceWriter;
    private String referenceDate;
    private String referenceContent;
    private String companyLicenseNum;
    private String companyNameKo;
    private String companyNameEn;
    private String companyAddress;
    private String companyAddressDetail;
    private String factoryAddress;
    private String factoryAddressDetail;
    private String companyCeo;
    private String companyTel;
    private String companyHomepage;
    private String companyFax;
    private String industryPart;
    private String industryPartEtc;
    private Integer employeeCnt; // String -> Integer
    private String companyLicense;
    private String prePartYear;
    private String memberCompanyYn;

    /* 담당자정보 */
    private String name;
    private String position;
    private String depart;
    private String tel;
    private String phone;
    private String email;
    private String emailMarketingYn;
    private String chargePersonName;
    private String chargePersonPosition;
    private String chargePersonDepart;
    private String chargePersonTel;
    private String chargePersonPhone;
    private String chargePersonEmail;

    /* 업체정보 */
    private String companyIntroVideo;
    private String companyIntroKo;
    private String companyIntroEn;
    private String companyPurposeKo;
    private String companyPurposeEn;
    private String newItemIntroKo;
    private String newItemIntroEn;
    private String promotionPlan;
    private String companyOnlineViewYn;

    /* 해상전시회 */
    private String maritimeExhibitionYn;
    private Boolean maritimeExhibitionSea;
    private Boolean maritimeExhibitionLand;

    /* 참가분야 */
    private String fieldPart; // [참고] field_part (GROUP_CONCAT 대상 아님)
    private String fieldParticipatory; //  참가행사
    private String fieldParticipatory1; //  참가분야1
    private String fieldParticipatory2; //  참가분야2
    private String fieldParticipatory3; //  참가분야3

    /* 제품 (GROUP_CONCAT 결과) */
    private String productOptionBig;
    private String productOptionSmall;
    private String productNameKo;
    private String productNameEn;
    private String productQty;
    private String productBrand;
    private String productLength;
    private String productWidth;
    private String productHeight;
    private String productWeight;
    private String productMaterial;
    private String productYear;
    private String productIntroKo;
    private String productIntroEn;
    private String productLink;

    /* 기업뱃지 */
    private String companyBadge;

    /* 온라인 제품 정보 (GROUP_CONCAT 결과) */
    private String onlineOptionBig;
    private String onlineOptionSmall;
    private String onlineNameKo;
    private String onlineNameEn;
    private String onlineIntroKo;
    private String onlineIntroEn;
    private String onlineLink;
    private String onlineLength;
    private String onlineWidth;
    private String onlineHeight;
    private String onlineWeight;
    private String onlineMaterial;
    private String onlineYear;

    /* 참가업체정보 */
    private String exportMeetingYn;

    /* 전시부스 신청 */
    private Integer registrationCnt;
    private Integer registrationFee; //  등록비 금액
    private Integer standAloneBoothCnt;
    private Integer standAloneBoothFee; //  독립부스 금액
    private Integer assemblyBoothCnt;
    private Integer assemblyBoothFee; //  조립부스 금액
    private Integer onlineBoothCnt;
    private Integer onlineBoothFee; //  온라인부스 금액
    private Boolean discountEarly1;
    private Boolean discountEarly2;
    private Boolean discountFirstUnder10;
    private Boolean discountFirstOver10;
    private Boolean discountFirst;
    private Boolean discountRe;
    private Boolean discountScale1;
    private Boolean discountScale2;
    private Boolean discountScale3;
    private Boolean discountScale4;
    private Boolean discountScale5;
    private Boolean discountScale6;
    private Boolean discountLeisure;
    private String discountYn; //  할인적용 여부
    private String discountType; //  할인 타입 문자열

    /* 상호간판 */
    private String companySignNameKo;
    private String companySignNameEn;

    /* 유틸리티 신청 */
    private Integer utilityJuganCnt;
    private Integer utilityJuganFee; // 
    private Integer utilityDayCnt;
    private Integer utilityDayFee; // 
    private Integer utilityCompressedAirCnt;
    private Integer utilityCompressedAirFee; // 
    private Integer utilityWaterBasicCnt;
    private Integer utilityWaterBasicFee; // 
    private Integer utilityTelephoneCnt;
    private Integer utilityInternetCnt;
    private Integer utilityInternetFee; // 
    private Integer utilityPytexNewCnt;
    private Integer utilityPytexNewFee; // 
    private Integer utilityPytexReCnt;
    private Integer utilityPytexReFee; // 
    private Integer utilityBarcodeCnt;
    private Integer utilityBarcodeFee; // 

    /* 출입증 신청 (GROUP_CONCAT 결과) */
    private String passName;
    private String passLastName;
    private String passFirstName;
    private String passPositionKo;
    private String passPositionEn;
    private String passNote;

    /* 바이어 등록 정보 (GROUP_CONCAT 결과) */
    private String buyerCompanyName;
    private String buyerCompanyCountry;
    private String buyerCompanyLocation;
    private String buyerCompanyHomepage;
    private String buyerCompanyDepart;
    private String buyerCompanyPosition;
    private String buyerCompanyEmail;
    private String buyerCompanyTel;
    private String buyerCompanyPhone;
    private String buyerCompanyFax;
    private String buyerCompanyItem;
    private String buyerCompanyInviteReason;
    private String buyerCompanyProgressYn;
    private String buyerCompanyHope;

    /* 경품제공 신청 (GROUP_CONCAT 결과) */
    private String giftGbn;
    private String giftCnt;
    private String giftClassify;
    private String giftName;
    private String giftContent;
    private String giftPhoto;
    private String giftCompanyLogo;
    private String giftPrice;
    private String giftSponsorPrice;
    private String giftNote;

    /* 금액 합계 */
    private Integer boothPrcSum;
    private Integer discountPrcSum;
    private Integer utilityPrcSum;
    private Integer prcSum;
    private Integer prcVat;
    private Integer prcTotal;

    /* 기타 */
    private String note; // 

    // 특별 할인
    private Boolean discountSpecial1Yn; // boolean -> Boolean
    private String  discountSpecial1Note;
    private Boolean discountSpecial2Yn; // boolean -> Boolean
    private String  discountSpecial2Reason;
    private Integer discountSpecial2Amount; // int -> Integer
    private String  discountSpecial2Note;
    private Boolean discountSpecial3Yn; // boolean -> Boolean
    private String  discountSpecial3Reason;
    private Integer discountSpecial3Amount; // int -> Integer
    private String  discountSpecial3Note;

    private String depositAmounts;
    private String depositContents;
    private String depositStatuses;
    private String depositDates;
    private String depositScheduledDates;
    private String depositTaxStatuses;
    private String depositDepositorNames;
    private String depositConfirmerNames;
    private String depositComments;
    private String depositInitRegiDttms;
    private String depositFinalRegiDttms;
}