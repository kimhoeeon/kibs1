package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GiftDTO {
    String seq; //SEQ
    String id; //업체seq
    String userId; //ID
    String transferYear; //연도
    String giftGbn; //경품구분
    Integer giftCnt; //경품수량
    String giftClassify; //분류
    String giftName; //품목명
    String giftContent; //경품설명
    String giftPhoto; //경품사진
    String giftCompanyLogo; //회사로고
    Integer giftPrice; //소비자가
    Integer giftSponsorPrice; //협찬가
    String giftNote; //경품비고
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}