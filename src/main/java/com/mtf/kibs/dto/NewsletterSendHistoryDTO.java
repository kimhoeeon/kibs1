package com.mtf.kibs.dto;

import lombok.Data;

@Data
public class NewsletterSendHistoryDTO {
    private String seq;
    private String clippingSeq;
    private String subscriberEmail;
    private String sendResult;
    private String sendResultMsg;
    private String sendDate;
}