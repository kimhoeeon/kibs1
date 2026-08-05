package com.mtf.kibs.service;

import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import java.util.List;
import java.util.Map;

public interface NewsletterService {
    // 구독 신청
    Map<String, Object> subscribe(NewsletterSubscriberDTO dto);

    // 수신 거부
    Map<String, Object> unsubscribe(String email);

    // 생성된 클리핑 기사를 구독자에게 발송
    void sendClippingNewsletter(String clippingSeq, String title, String content);
}