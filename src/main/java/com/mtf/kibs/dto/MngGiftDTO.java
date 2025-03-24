package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MngGiftDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String id; //업체seq
    String companyNameKo; //회사명(국문)
    String companyNameEn; //회사명(영문)
    String giftGbn; //경품구분
    Integer giftCnt; //경품수량
    String giftClassify; //분류
    String giftName; //품목명
    String giftContent; //경품설명
    String giftPhoto;
    String giftCompanyLogo;
    Integer giftPrice; //소비자가
    Integer giftSponsorPrice; //협찬가
    String giftNote; //경품비고
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}