package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvoiceUtilityDTO {
    Integer rownum; //순번
    String seq; //SEQ
    String exSeq; //업체SEQ
    String sendStatus; //수신상태
    String title; //제목
    String email; //발송메일
    String sendDttm; //발송일시
    String endDttm;

    Integer utilityJuganCnt; //주간단상 220v 수량
    Integer utilityJuganFee; //주간단상 220v 비용
    Integer utilityDayCnt; //24시간용 220v 수량
    Integer utilityDayFee; //24시간용 220v 비용
    Integer utilityCompressedAirCnt; //압축공기 기본형 수량
    Integer utilityCompressedAirFee; //압축공기 기본형 금액
    Integer utilityWaterBasicCnt; //급배수 기본형 수량
    Integer utilityWaterBasicFee; //급배수 기본형 금액
    Integer utilityInternetCnt; //인터넷 수량
    Integer utilityInternetFee; //인터넷 금액
    Integer utilityPytexNewCnt; //파이텍스 신품 수량
    Integer utilityPytexNewFee; //파이텍스 신품 금액
    Integer utilityPytexReCnt; //파이텍스 재사용품 수량
    Integer utilityPytexReFee; //파이텍스 재사용품 금액
    Integer utilityBarcodeCnt; //바코드 리더기 수량
    Integer utilityBarcodeFee; //바코드 리더기 금액
    Integer utilityPrcSum; //유틸리티신청 총액

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