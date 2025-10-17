package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DepositHistoryDTO {
    private int depositSeq;
    private String exhibitorSeq;
    private int amount;
    private String contentType;
    private String taxInvoiceStatus;
    private String depositDate; // 날짜를 String으로 처리할 경우
    private String scheduledDate;
    private String depositorName;
    private String confirmerName;
    private String comment;
    private String paymentStatus;
    private String initRegiDttm;
    private String finalRegiDttm;
}