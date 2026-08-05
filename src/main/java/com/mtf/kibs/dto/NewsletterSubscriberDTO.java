package com.mtf.kibs.dto;

import lombok.Data;

@Data
public class NewsletterSubscriberDTO {
    private String seq;
    private String name;
    private String email;
    private String privacyAgreeYn;
    private String adAgreeYn;
    private String receiveStatus; // '수신중', '수신거부'
    private String lastSendDate;
    private String regDate;
}