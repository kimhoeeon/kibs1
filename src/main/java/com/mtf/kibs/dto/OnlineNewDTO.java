package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OnlineNewDTO {
    String seq; //SEQ
    String exSeq; //Exhibitor SEQ
    String onlineOptionBig; //제품분류(대)
    String onlineOptionSmall; //제품분류(소)
    String onlineNameKo; //제품명(국문)
    String onlineNameEn; //제품명(영문)
    String onlineIntroKo; //제품설명(국문)
    String onlineIntroEn; //제품설명(영문)
    String onlineLink; //제품영상
    int onlineLength; //길이
    int onlineWidth; //너비
    int onlineHeight; //높이
    int onlineWeight; //중량
    String onlineMaterial; //소재
    int onlineYear; //연식
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
}