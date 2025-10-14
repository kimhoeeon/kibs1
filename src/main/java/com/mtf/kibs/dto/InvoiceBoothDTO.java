package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InvoiceBoothDTO {
    private int invoiceSeq;
    private String exhibitorSeq;
    private String invoiceCode;
    private String title;
    private String recipientEmail;

    // 금액 스냅샷
    private int boothPrcSum;
    private int discountPrcSum;
    private int prcSum;
    private int prcVat;
    private int prcTotal;

    private String filePath;

    // 발송 관련 정보
    private String sendStatus;
    private String sendResult;
    private String sendResultMsg;
    private String sendDttm;
    private String endDttm;

    // 상태 및 관리 정보
    private String delYn;
    private String initRegiDttm;
    private String finalRegiPic;
    private String finalRegiDttm;

}