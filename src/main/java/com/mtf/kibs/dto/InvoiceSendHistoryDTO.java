package com.mtf.kibs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvoiceSendHistoryDTO {
    private int historySeq;
    private int invoiceSeq;
    private String invoiceType;
    private String recipientEmail;
    private String sendStatus;
    private String sendResult;
    private String sendResultMsg;
    private String sendDttm;
}