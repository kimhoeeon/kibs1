package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InvoiceBoothDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String exSeq; //업체SEQ
    String sendStatus; //수신상태
    String title; //제목
    String email; //발송메일
    String sendDttm; //발송일시
    String endDttm;
    String discountType; //할인구분
    Integer registrationCnt; //등록수량
    Integer registrationFee; //등록비
    Integer standAloneBoothCnt; //독립부스 신청수량
    Integer standAloneBoothFee; //독립부스 신청비용
    Integer assemblyBoothCnt; //조립부스 신청수량
    Integer assemblyBoothFee; //조립부스 신청비용
    Integer onlineBoothCnt; //온라인부스 신청수량
    Integer onlineBoothFee; //온라인부스 신청비용
    Integer boothPrcSum; //부스신청총액
    Boolean discountEarly1; //1차조기신청
    Boolean discountEarly2; //2차조기신청
    Boolean discountFirstUnder10; //첫참가할인(10부스미만)
    Boolean discountFirstOver10; //첫참가할인(10부스이상)
    Boolean discountFirst; //첫참가할인
    Boolean discountRe; //재참가할인
    Boolean discountScale1; //규모할인1(10부스이상)
    Boolean discountScale2; //규모할인2(20부스이상)
    Boolean discountScale3; //규모할인3(30부스이상)
    Boolean discountScale4; //규모할인4(40부스이상)
    Boolean discountScale5; //규모할인5(50부스이상)
    Boolean discountScale6; //규모할인6(100부스이상)
    Boolean discountLeisure; //한국해양레저산업협회할인
    Integer discountPrcSum; //할인총액
    Integer prcTotal; //할인총액
    String sendResult; //발송결과
    String sendResultMsg; //발송결과메시지
    String filePath;
    String delYn;
    String note; //비고
    String initRegiPic; //최초 등록 담당자
    String initRegiDttm; //최초 등록 일시
    String finalRegiPic; //최종 변경 담당자
    String finalRegiDttm; //최종 변경 일시
    
    String invoiceYn; //인보이스존재여부
    String invoiceCode;

}