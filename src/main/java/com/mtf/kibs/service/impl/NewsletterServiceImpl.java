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
            String mailBody = content + "<br><br><div style='text-align:center; color:#888; font-size:12px;'><a href='" + unsubscribeUrl + "'>뉴스레터 수신거부(Unsubscribe)</a></div>";
            mailReq.setBody(mailBody);

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

        // 실제 운용 가능하도록 주석 해제 및 DB 반영 로직 활성화
        AiClippingDTO updDto = new AiClippingDTO();
        updDto.setSeq(clippingSeq);
        updDto.setSendSuccessCnt(successCnt);
        updDto.setSendFailCnt(failCnt);
        aiClippingMapper.updateSendCnt(updDto);
    }
}