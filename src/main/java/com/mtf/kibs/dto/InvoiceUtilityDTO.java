package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvoiceUtilityDTO {
    private int historySeq;
    private int invoiceSeq;
    private String invoiceType;
    private String exhibitorSeq;
    private String invoiceCode;
    private String title;
    private String recipientEmail;

    // 유틸리티 스냅샷 필드
    private int utilityJuganCnt;
    private int utilityJuganFee;
    private int utilityDayCnt;
    private int utilityDayFee;
    private int utilityCompressedAirCnt;
    private int utilityCompressedAirFee;
    private int utilityWaterBasicCnt;
    private int utilityWaterBasicFee;
    private int utilityInternetCnt;
    private int utilityInternetFee;
    private int utilityPytexNewCnt;
    private int utilityPytexNewFee;
    private int utilityPytexReCnt;
    private int utilityPytexReFee;
    private int utilityBarcodeCnt;
    private int utilityBarcodeFee;
    private int utilityPrcSum;

    // 최종 금액
    private int prcSum;
    private int prcVat;
    private int prcTotal;

    private String filePath;
    private String sendStatus;
    private String sendDttm;
    private String endDttm;

    private String sendResult;      // 발송 결과 (성공/실패)
    private String sendResultMsg;   // 발송 결과 상세 메시지

    private String delYn;
    private String initRegiDttm;

    private String finalRegiPic;
    private String finalRegiDttm;
}