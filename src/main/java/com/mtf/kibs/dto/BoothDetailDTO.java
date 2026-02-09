package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoothDetailDTO {
    private String seq;
    private String companyNameKo;
    private String companyNameEn;

    private String invoiceYn; //인보이스존재여부

    private String boothType;
    private Integer registrationCnt;
    private Integer registrationFee; // 등록비 금액
    private Integer standAloneBoothCnt;
    private Integer standAloneBoothFee; // 독립부스 금액
    private Integer assemblyBoothCnt;
    private Integer assemblyBoothFee; // 조립부스 금액
    private Integer onlineBoothCnt;
    private Integer onlineBoothFee; // 온라인부스 금액
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
    private Integer boothPrcSum; //부스신청총액
    private Integer discountPrcSum; //할인총액
    private String memberCompanyYn; //협회할인여부
    private Boolean discountSpecial1Yn;
    private String  discountSpecial1Note;
    private Boolean discountSpecial2Yn;
    private String  discountSpecial2Reason;
    private Integer discountSpecial2Amount;
    private String  discountSpecial2Note;
    private Boolean discountSpecial3Yn;
    private String  discountSpecial3Reason;
    private Integer discountSpecial3Amount;
    private String  discountSpecial3Note;
    private Integer prcSum; //소개
    private Integer prcVat; //부가세
    private Integer prcTotal; //총계
    private String initRegiDttm; //최초 등록 일시
    private String finalRegiDttm; //최종 변경 일시
    private String fieldParticipatory; //참가행사
}