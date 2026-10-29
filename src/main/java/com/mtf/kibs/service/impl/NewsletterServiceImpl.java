package com.mtf.kibs.service.impl;

import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.AiClippingArticleDTO;
import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.MailRequestDTO;
import com.mtf.kibs.dto.NewsletterSendHistoryDTO;
import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.dto.ResponseDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.mapper.NewsletterMapper;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.service.NewsletterService;
import com.mtf.kibs.util.AES256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NewsletterServiceImpl implements NewsletterService {

    @Autowired
    private NewsletterMapper newsletterMapper;

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private KibsMngService kibsMngService;

    @Autowired
    private AES256Util aes256Util;

    /** 메일 하단 "최신 관련 기사" 최대 건수 (발주사 확정 2026-10-29: 날짜순 최대 150건, 웹 상세와 동일) */
    @Value("${ai.clipping.related-max:150}")
    private int clippingRelatedMax;

    /** 메일 본문(HTML) 크기 상한 KB. Gmail 은 약 102KB 를 넘으면 뒷부분(관련 기사·수신거부 링크)을 잘라 "메시지 잘림"으로 표시하므로 여유를 두고 90KB. 넘으면 관련 기사 건수를 자동으로 줄이고 "홈페이지에서 보기" 링크를 넣는다. */
    @Value("${ai.clipping.mail-max-kb:90}")
    private int clippingMailMaxKb;

    /** 푸터(수신거부 버튼·카피라이트) 몫으로 미리 빼 두는 바이트 */
    private static final int FOOTER_RESERVE_BYTES = 2048;

    private static int utf8Bytes(CharSequence v) {
        return v == null ? 0 : v.toString().getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    @Transactional
    public Map<String, Object> subscribe(NewsletterSubscriberDTO dto) {
        Map<String, Object> result = new HashMap<>();

        // 1. 암호화 처리 추가
        String encryptedEmail = aes256Util.encrypt(dto.getEmail());
        dto.setEmail(encryptedEmail);

        // 1. 해당 이메일의 가입 이력 및 수신 상태 조회
        String currentStatus = newsletterMapper.checkSubscriberStatus(encryptedEmail);

        // 2. 가입 이력이 존재하는 경우 분기 처리
        if (currentStatus != null) {
            if ("수신중".equals(currentStatus)) {
                // 이미 수신 중인 경우 가입 차단
                result.put("resultCode", "-1");
                result.put("resultMsg", "이미 구독 중인 이메일입니다.");
                return result;

            } else if ("수신거부".equals(currentStatus)) {
                // 수신 거부 상태인 경우 기존 정보 갱신(재구독) 처리
                newsletterMapper.updateReSubscribe(dto);
                result.put("resultCode", "0");
                result.put("resultMsg", "다시 구독해 주셔서 감사합니다! 뉴스레터 구독이 정상적으로 처리되었습니다.");
                return result;
            }
        }

        // 3. 가입 이력이 전혀 없는 완전 신규 구독인 경우
        int insertCnt = newsletterMapper.insertSubscriber(dto);
        if (insertCnt > 0) {
            result.put("resultCode", "0");
            result.put("resultMsg", "뉴스레터 구독 신청이 완료되었습니다.");
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
        dto.setEmail(aes256Util.encrypt(email));
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

    /**
     * 클리핑의 "최신 관련 기사" 목록 HTML (메일용 인라인 스타일).
     * 전처리·연관도 심사를 통과한 기사(SELECTED/LISTED)만 날짜 내림차순으로 최대 related-max 건.
     * 0점·블랙리스트 기사는 조회 조건에서 제외된다. 기사가 없으면(legacy 글 등) 빈 문자열.
     * budgetBytes 안에 들어가는 건수만 넣고, 다 넣지 못하면 "전체 N건은 홈페이지에서 보기" 링크를 붙인다(메일 잘림 방지).
     */
    private String buildRelatedArticlesHtml(String clippingSeq, int budgetBytes) {
        List<AiClippingArticleDTO> articles;
        try {
            articles = aiClippingMapper.selectClippingArticlesForList(Long.parseLong(clippingSeq), clippingRelatedMax);
        } catch (Exception e) {
            System.err.println("뉴스레터 관련 기사 조회 실패 (seq=" + clippingSeq + "): " + e.getMessage());
            return "";
        }
        if (articles == null || articles.isEmpty()) {
            return "";
        }

        List<String> items = new ArrayList<>();
        for (AiClippingArticleDTO a : articles) {
            String date = a.getArticleDate() == null ? "" : a.getArticleDate().replace("-", ".");
            items.add("<li style=\"font-size: 14px; color: #333; margin-bottom: 8px; line-height: 1.6; word-break: keep-all;\">"
                    + "<span style=\"color:#666;\">" + escapeHtml(date) + "</span> | "
                    + "<a href=\"" + escapeHtml(a.getLink()) + "\" target=\"_blank\" style=\"color:#1d5cad; text-decoration:underline;\">"
                    + escapeHtml(a.getTitle()) + "</a> | "
                    + escapeHtml(a.getPublisher())
                    + "</li>");
        }

        String viewAllHtml = "            <p style=\"margin: 15px 0 0; font-size: 14px; color: #333;\">전체 " + articles.size()
                + "건은 <a href=\"https://kibs.com/board/clippingView.do?seq=" + escapeHtml(clippingSeq)
                + "\" target=\"_blank\" style=\"color:#1d5cad; font-weight:700; text-decoration:underline;\">홈페이지에서 보기</a></p>";
        String open = "        <div style=\"margin-top: 40px; padding: 20px; background-color: #f8f9fa; border-top: 4px solid #1d5cad; border-bottom: 1px solid #ddd;\">";
        String ulOpen = "            <ul style=\"list-style: none; padding: 0; margin: 0;\">";
        String ulClose = "            </ul>";
        String close = "        </div>";

        // 예산 안에 들어가는 건수 계산 (제목 줄·링크 줄 몫은 미리 뺀다)
        int used = utf8Bytes(open) + 160 + utf8Bytes(ulOpen) + utf8Bytes(ulClose) + utf8Bytes(viewAllHtml) + utf8Bytes(close);
        int fit = 0;
        for (String li : items) {
            int b = utf8Bytes(li);
            if (used + b > budgetBytes) break;
            used += b;
            fit++;
        }
        boolean truncated = fit < items.size();
        if (truncated) {
            System.out.println("[뉴스레터] 메일 크기 상한(" + clippingMailMaxKb + "KB)으로 관련 기사 " + items.size() + "건 중 " + fit + "건만 포함 (seq=" + clippingSeq + ")");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(open);
        sb.append("            <p style=\"margin: 0 0 15px; color: #1d5cad; font-size: 16px; font-weight: 700;\">■ 최신 관련 기사 (");
        if (truncated) {
            sb.append("전체 ").append(articles.size()).append("건 중 최근 ").append(fit).append("건");
        } else {
            sb.append(articles.size()).append("건");
        }
        sb.append(")</p>");
        if (fit > 0) {
            sb.append(ulOpen);
            for (int i = 0; i < fit; i++) {
                sb.append(items.get(i));
            }
            sb.append(ulClose);
        }
        if (truncated) {
            sb.append(viewAllHtml);
        }
        sb.append(close);
        return sb.toString();
    }

    private static String escapeHtml(String v) {
        if (v == null) return "";
        return v.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    @Override
    public void sendClippingNewsletter(String clippingSeq, String title, String content) {
        List<NewsletterSubscriberDTO> activeSubscribers = newsletterMapper.selectActiveSubscribers();

        if (activeSubscribers == null || activeSubscribers.isEmpty()) {
            return;
        }

        MailRequestDTO mailReq = new MailRequestDTO();
        mailReq.setSubject(title);

        String unsubscribeUrl = "https://kibs.com/api/newsletter/unsubscribe?email=[$EMAIL]";

        // 이메일 전용 HTML 템플릿 적용 (인라인 CSS 사용)
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div style=\"max-width: 650px; margin: 0 auto; font-family: 'Malgun Gothic', '맑은 고딕', 'Apple SD Gothic Neo', sans-serif; border: 1px solid #e0e0e0; border-top: 4px solid #1d5cad; border-radius: 4px; overflow: hidden;\">");

        // 헤더 영역 (로고 및 안내)
        htmlBuilder.append("    <div style=\"background-color: #f8f9fa; padding: 40px 20px; text-align: center; border-bottom: 1px solid #eee;\">");
        htmlBuilder.append("        <img src=\"https://kibs.com/img/logo.png\" alt=\"경기국제보트쇼\" style=\"max-width: 220px; margin-bottom: 20px; border: 0;\">");
        htmlBuilder.append("        <h2 style=\"margin: 0; font-size: 26px; color: #222; letter-spacing: -1px; font-weight: 700;\">AI 뉴스 클리핑</h2>");
        htmlBuilder.append("        <p style=\"margin: 10px 0 0; font-size: 15px; color: #666; letter-spacing: -0.5px;\">오늘의 해양레저산업 주요 동향을 전해드립니다.</p>");
        // 상단 수신거부 링크: 메일 클라이언트가 긴 본문을 잘라도 수신거부 경로가 보이도록 푸터와 별도로 한 번 더 둔다
        htmlBuilder.append("        <p style=\"margin: 12px 0 0; font-size: 12px; color: #999;\"><a href=\"").append(unsubscribeUrl)
                   .append("\" style=\"color: #999; text-decoration: underline;\">수신거부</a></p>");
        htmlBuilder.append("    </div>");

        // 본문 영역 (타이틀 및 내용)
        htmlBuilder.append("    <div style=\"padding: 40px 30px; background-color: #ffffff;\">");
        htmlBuilder.append("        <h3 style=\"margin-top: 0; color: #1d5cad; font-size: 20px; border-bottom: 2px solid #1d5cad; padding-bottom: 15px; margin-bottom: 25px; word-break: keep-all; line-height: 1.4;\">").append(title).append("</h3>");
        htmlBuilder.append("        <div style=\"font-size: 16px; color: #444; line-height: 1.8; word-break: keep-all;\">");
        htmlBuilder.append(             content);
        htmlBuilder.append("        </div>");
        // 최신 관련 기사 목록 (발주사 요청 2026-10-29: 메일에도 포함, 날짜순 최대 related-max 건).
        // 메일 전체가 mail-max-kb 를 넘지 않도록 남은 예산 안에서 건수를 자동 조절한다.
        int budgetBytes = clippingMailMaxKb * 1024 - utf8Bytes(htmlBuilder) - FOOTER_RESERVE_BYTES;
        htmlBuilder.append(buildRelatedArticlesHtml(clippingSeq, budgetBytes));
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
        System.out.println("[뉴스레터] 메일 본문 크기 " + (utf8Bytes(htmlBuilder) / 1024) + "KB (seq=" + clippingSeq + ")");

        // 1. 전체 구독자 데이터를 하나의 Receiver List에 꾹꾹 담아줍니다.
        List<MailRequestDTO.Receiver> receivers = new ArrayList<>();
        for (NewsletterSubscriberDTO subscriber : activeSubscribers) {
            MailRequestDTO.Receiver receiver = new MailRequestDTO.Receiver();
            receiver.setEmail(subscriber.getEmail());
            receiver.setName(subscriber.getName());
            receivers.add(receiver);
        }
        mailReq.setReceiver(receivers);

        // 2. 단 1번의 API 호출로 대량 발송 처리
        ResponseDTO sendResponse = kibsMngService.processNewsletterBulkSend(mailReq);

        int successCnt = 0;
        int failCnt = 0;
        boolean isSuccess = CommConstants.RESULT_CODE_SUCCESS.equals(sendResponse.getResultCode());

        // 3. 발송 결과에 따라 DB 이력 일괄 기록 처리
        for (NewsletterSubscriberDTO subscriber : activeSubscribers) {
            NewsletterSendHistoryDTO historyDto = new NewsletterSendHistoryDTO();
            historyDto.setClippingSeq(clippingSeq);
            historyDto.setSubscriberEmail(subscriber.getEmail());

            if (isSuccess) {
                historyDto.setSendResult("발송성공");
                historyDto.setSendResultMsg("정상 발송 접수");
                successCnt++;
                newsletterMapper.updateLastSendDate(aes256Util.encrypt(subscriber.getEmail()));
            } else {
                historyDto.setSendResult("발송실패");
                historyDto.setSendResultMsg(sendResponse.getResultMessage());
                failCnt++;
            }
            newsletterMapper.insertSendHistory(historyDto);
        }

        // 4. 취합된 최종 성공/실패 횟수를 클리핑 원본 테이블에 업데이트
        AiClippingDTO updDto = new AiClippingDTO();
        updDto.setSeq(clippingSeq);
        updDto.setSendSuccessCnt(successCnt);
        updDto.setSendFailCnt(failCnt);
        aiClippingMapper.updateSendCnt(updDto);
    }

}