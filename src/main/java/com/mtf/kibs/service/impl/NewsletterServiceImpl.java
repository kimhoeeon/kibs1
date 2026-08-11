package com.mtf.kibs.service.impl;

import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.MailRequestDTO;
import com.mtf.kibs.dto.NewsletterSendHistoryDTO;
import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.dto.ResponseDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.mapper.NewsletterMapper;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsletterServiceImpl implements NewsletterService {

    @Autowired
    private NewsletterMapper newsletterMapper;

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private KibsMngService kibsMngService;

    @Override
    @Transactional
    public Map<String, Object> subscribe(NewsletterSubscriberDTO dto) {
        Map<String, Object> result = new HashMap<>();

        if (newsletterMapper.checkEmailDuplicate(dto.getEmail()) > 0) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "이미 구독 중인 이메일입니다.");
            return result;
        }

        int insertCnt = newsletterMapper.insertSubscriber(dto);
        if (insertCnt > 0) {
            result.put("resultCode", "0");
            result.put("resultMsg", "구독 신청이 완료되었습니다.");
        } else {
            result.put("resultCode", "-1");
            result.put("resultMsg", "구독 신청 중 오류가 발생했습니다.");
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> unsubscribe(String email) {
        Map<String, Object> result = new HashMap<>();
        NewsletterSubscriberDTO dto = new NewsletterSubscriberDTO();
        dto.setEmail(email);
        dto.setReceiveStatus("수신거부");

        int updateCnt = newsletterMapper.updateReceiveStatus(dto);
        if (updateCnt > 0) {
            result.put("resultCode", "0");
            result.put("resultMsg", "수신 거부 처리가 완료되었습니다.");
        } else {
            result.put("resultCode", "-1");
            result.put("resultMsg", "등록되지 않은 이메일이거나 이미 처리된 요청입니다.");
        }
        return result;
    }

    @Override
    @Transactional
    public void sendClippingNewsletter(String clippingSeq, String title, String content) {
        List<NewsletterSubscriberDTO> activeSubscribers = newsletterMapper.selectActiveSubscribers();

        if (activeSubscribers == null || activeSubscribers.isEmpty()) {
            return;
        }

        int successCnt = 0;
        int failCnt = 0;

        for (NewsletterSubscriberDTO subscriber : activeSubscribers) {
            MailRequestDTO mailReq = new MailRequestDTO();
            mailReq.setSubject("[경기국제보트쇼 AI 클리핑] " + title);

            String encodedEmail = "";
            try {
                encodedEmail = URLEncoder.encode(subscriber.getEmail(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encodedEmail = subscriber.getEmail();
                e.printStackTrace();
            }

            String unsubscribeUrl = "https://kibs.com/api/newsletter/unsubscribe?email=" + encodedEmail;

            // 이메일 전용 HTML 템플릿 적용 (인라인 CSS 사용)
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<div style=\"max-width: 650px; margin: 0 auto; font-family: 'Malgun Gothic', '맑은 고딕', 'Apple SD Gothic Neo', sans-serif; border: 1px solid #e0e0e0; border-top: 4px solid #1d5cad; border-radius: 4px; overflow: hidden;\">");

            // 헤더 영역 (로고 및 안내)
            htmlBuilder.append("    <div style=\"background-color: #f8f9fa; padding: 40px 20px; text-align: center; border-bottom: 1px solid #eee;\">");
            htmlBuilder.append("        <img src=\"https://kibs.com/img/logo.png\" alt=\"경기국제보트쇼\" style=\"max-width: 220px; margin-bottom: 20px; border: 0;\">");
            htmlBuilder.append("        <h2 style=\"margin: 0; font-size: 26px; color: #222; letter-spacing: -1px; font-weight: 700;\">AI 뉴스 클리핑</h2>");
            htmlBuilder.append("        <p style=\"margin: 10px 0 0; font-size: 15px; color: #666; letter-spacing: -0.5px;\">오늘의 해양레저산업 주요 동향을 전해드립니다.</p>");
            htmlBuilder.append("    </div>");

            // 본문 영역 (타이틀 및 내용)
            htmlBuilder.append("    <div style=\"padding: 40px 30px; background-color: #ffffff;\">");
            htmlBuilder.append("        <h3 style=\"margin-top: 0; color: #1d5cad; font-size: 20px; border-bottom: 2px solid #1d5cad; padding-bottom: 15px; margin-bottom: 25px; word-break: keep-all; line-height: 1.4;\">").append(title).append("</h3>");
            htmlBuilder.append("        <div style=\"font-size: 16px; color: #444; line-height: 1.8; word-break: keep-all;\">");
            htmlBuilder.append(             content);
            htmlBuilder.append("        </div>");
            htmlBuilder.append("    </div>");

            // 푸터 영역 (수신거부 및 카피라이트)
            htmlBuilder.append("    <div style=\"background-color: #f1f3f5; padding: 30px 20px; text-align: center; border-top: 1px solid #e5e5e5;\">");
            htmlBuilder.append("        <p style=\"margin: 0 0 15px; font-size: 13px; color: #777; line-height: 1.6;\">");
            htmlBuilder.append("            본 메일은 경기국제보트쇼 뉴스레터 수신에 동의하신 분들께 발송되었습니다.<br>");
            htmlBuilder.append("            더 이상 메일 수신을 원하지 않으시면 아래 버튼을 클릭해 주세요.");
            htmlBuilder.append("        </p>");
            htmlBuilder.append("        <a href=\"").append(unsubscribeUrl).append("\" style=\"display: inline-block; padding: 10px 20px; background-color: #ffffff; border: 1px solid #cccccc; color: #555555; text-decoration: none; font-size: 13px; font-weight: bold; border-radius: 4px;\">뉴스레터 수신거부 (Unsubscribe)</a>");
            htmlBuilder.append("        <p style=\"margin: 20px 0 0; font-size: 12px; color: #aaaaaa;\">");
            htmlBuilder.append("            © 2026 Korea International Boat Show. All rights reserved.");
            htmlBuilder.append("        </p>");
            htmlBuilder.append("    </div>");
            htmlBuilder.append("</div>");

            mailReq.setBody(htmlBuilder.toString());

            List<MailRequestDTO.Receiver> receivers = new ArrayList<>();
            MailRequestDTO.Receiver receiver = new MailRequestDTO.Receiver();
            receiver.setEmail(subscriber.getEmail());
            receiver.setName(subscriber.getName());
            receivers.add(receiver);
            mailReq.setReceiver(receivers);

            ResponseDTO sendResponse = kibsMngService.processMailSend(mailReq);

            NewsletterSendHistoryDTO historyDto = new NewsletterSendHistoryDTO();
            historyDto.setClippingSeq(clippingSeq);
            historyDto.setSubscriberEmail(subscriber.getEmail());

            if (CommConstants.RESULT_CODE_SUCCESS.equals(sendResponse.getResultCode())) {
                historyDto.setSendResult("발송성공");
                historyDto.setSendResultMsg("정상 발송");
                successCnt++;
                newsletterMapper.updateLastSendDate(subscriber.getEmail());
            } else {
                historyDto.setSendResult("발송실패");
                historyDto.setSendResultMsg(sendResponse.getResultMessage());
                failCnt++;
            }
            newsletterMapper.insertSendHistory(historyDto);
        }

        AiClippingDTO updDto = new AiClippingDTO();
        updDto.setSeq(clippingSeq);
        updDto.setSendSuccessCnt(successCnt);
        updDto.setSendFailCnt(failCnt);
        aiClippingMapper.updateSendCnt(updDto);
    }
}