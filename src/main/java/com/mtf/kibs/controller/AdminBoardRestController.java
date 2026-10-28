package com.mtf.kibs.controller;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.AiClippingKeywordDTO;
import com.mtf.kibs.dto.NewsletterSendHistoryDTO;
import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.mapper.NewsletterMapper;
import com.mtf.kibs.scheduler.AiClippingScheduler;
import com.mtf.kibs.service.NewsletterService;
import com.mtf.kibs.util.AES256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mng/center/board")
public class AdminBoardRestController {

    @Autowired
    private NewsletterMapper newsletterMapper;

    @Autowired
    private AiClippingMapper aiClippingMapper;

    @Autowired
    private AiClippingScheduler aiClippingScheduler;

    @Autowired
    private NewsletterService newsletterService;

    @Autowired
    private AES256Util aes256Util;

    /* ================= 뉴스레터 구독자 관리 ================= */

    @PostMapping("/subscriber/delete.do")
    public ResponseEntity<Map<String, Object>> deleteSubscribers(@RequestBody List<String> seqList) {
        Map<String, Object> result = new HashMap<>();
        try {
            for (String seq : seqList) {
                newsletterMapper.deleteSubscriber(seq);
            }
            result.put("resultCode", "0");
            result.put("resultMsg", "삭제 완료");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "삭제 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subscriber/reject.do")
    public ResponseEntity<Map<String, Object>> rejectSubscribers(@RequestBody List<String> seqList) {
        Map<String, Object> result = new HashMap<>();
        try {
            for (String seq : seqList) {
                NewsletterSubscriberDTO dto = new NewsletterSubscriberDTO();
                dto.setSeq(seq);
                dto.setReceiveStatus("수신거부");
                newsletterMapper.updateReceiveStatus(dto);
            }
            result.put("resultCode", "0");
            result.put("resultMsg", "수신거부 처리 완료");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "상태 변경 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/subscriber/list.ajax")
    public ResponseEntity<Map<String, Object>> getSubscriberList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String receiveStatus,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        if (searchText != null && searchText.contains("@")) {
            searchText = aes256Util.encrypt(searchText);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        params.put("offset", (page - 1) * size);
        params.put("searchText", searchText);
        params.put("receiveStatus", receiveStatus);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        // Mapper를 통해 조건에 맞는 목록 및 총 개수 조회
        List<NewsletterSubscriberDTO> list = newsletterMapper.selectSubscriberList(params);
        int totalCount = newsletterMapper.selectSubscriberCount(params);

        Map<String, Object> result = new HashMap<>();
        result.put("resultCode", "0");
        result.put("list", list);
        result.put("totalCount", totalCount);

        return ResponseEntity.ok(result);
    }

    /* ================= AI 클리핑 관리 ================= */

    @GetMapping("/clipping/detail.ajax")
    public ResponseEntity<Map<String, Object>> getClippingDetail(@RequestParam String seq) {
        Map<String, Object> result = new HashMap<>();
        try {
            AiClippingDTO detail = aiClippingMapper.selectAiClippingDetail(seq);
            result.put("resultCode", "0");
            result.put("data", detail);
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "조회 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/update.do")
    public ResponseEntity<Map<String, Object>> updateClipping(@RequestBody AiClippingDTO dto) {
        Map<String, Object> result = new HashMap<>();
        try {
            aiClippingMapper.updateAiClipping(dto);
            result.put("resultCode", "0");
            result.put("resultMsg", "수정 완료");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "수정 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/delete.do")
    public ResponseEntity<Map<String, Object>> deleteClipping(@RequestBody Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();
        try {
            String seq = payload.get("seq");
            aiClippingMapper.deleteAiClipping(seq);
            result.put("resultCode", "0");
            result.put("resultMsg", "삭제 완료");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "삭제 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/manual-update.do")
    public ResponseEntity<Map<String, Object>> manualUpdateClipping(@RequestParam(defaultValue = "Y") String sendYn) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 관리자가 원할 때 언제든 AI 기사를 추가로 생성해 볼 수 있도록 유연성 제공
            boolean isSend = "Y".equals(sendYn);
            boolean isSuccess = aiClippingScheduler.processAiClipping(isSend);

            if (isSuccess) {
                result.put("resultCode", "0");
                String created = aiClippingScheduler.isPublishRequiresApproval()
                        ? "AI 클리핑 기사가 생성되었습니다. (임시저장·미승인 상태, 월요일 10:00 자동 발행은 당일 생성 + 승인된 글만)"
                        : "AI 클리핑 기사가 생성되었습니다. (임시저장 상태, 월요일 10:00 자동 발행은 당일 생성 글만 대상)";
                result.put("resultMsg", isSend ? "AI 클리핑 기사 생성 및 뉴스레터 발송이 완료되었습니다." : created);
            } else {
                // 수집된 유효 기사가 하나도 없어 생성되지 않았을 때
                result.put("resultCode", "-1");
                result.put("resultMsg", "등록된 키워드로 수집할 수 있는 유효한 최신 기사가 없습니다. 나중에 다시 시도해 주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", "-1");
            result.put("resultMsg", "생성 중 시스템 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/send.do")
    public ResponseEntity<Map<String, Object>> sendExistingClipping(@RequestBody Map<String, String> param) {
        Map<String, Object> result = new HashMap<>();
        try {
            String seq = param.get("seq");

            // 1. 발송할 기사의 제목과 내용을 DB에서 다시 불러옴
            AiClippingDTO clipping = aiClippingMapper.selectAiClippingDetail(seq);

            if (clipping != null) {
                // 2. 뉴스레터 발송 서비스 호출
                newsletterService.sendClippingNewsletter(clipping.getSeq(), clipping.getTitle(), clipping.getContent());

                result.put("resultCode", "0");
                result.put("resultMsg", "해당 기사로 뉴스레터 발송이 완료되었습니다.");
            } else {
                result.put("resultCode", "-1");
                result.put("resultMsg", "발송할 기사 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", "-1");
            result.put("resultMsg", "발송 처리 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/clipping/history.ajax")
    public ResponseEntity<Map<String, Object>> getSendHistoryList(
            @RequestParam String clippingSeq,
            @RequestParam(required = false) String sendResult,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> params = new HashMap<>();
        params.put("clippingSeq", clippingSeq);
        params.put("sendResult", sendResult);
        params.put("limit", size);
        params.put("offset", (page - 1) * size);

        List<NewsletterSendHistoryDTO> list = newsletterMapper.selectSendHistoryList(params);
        int totalCount = newsletterMapper.selectSendHistoryCount(params);

        Map<String, Object> result = new HashMap<>();
        result.put("resultCode", "0");
        result.put("list", list);
        result.put("totalCount", totalCount);

        return ResponseEntity.ok(result);
    }

    /* ================= AI 클리핑 키워드 관리 ================= */

    @GetMapping("/clipping/keyword/list.ajax")
    public ResponseEntity<Map<String, Object>> getKeywordList() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<AiClippingKeywordDTO> list = aiClippingMapper.selectKeywordList();
            result.put("resultCode", "0");
            result.put("list", list);
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "조회 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/keyword/add.do")
    public ResponseEntity<Map<String, Object>> addKeyword(@RequestBody AiClippingKeywordDTO dto) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 중복 검사
            int count = aiClippingMapper.checkKeywordDuplicate(dto.getKeyword());
            if (count > 0) {
                result.put("resultCode", "-1");
                result.put("resultMsg", "이미 등록된 키워드입니다.");
                return ResponseEntity.ok(result);
            }

            aiClippingMapper.insertKeyword(dto);
            result.put("resultCode", "0");
            result.put("resultMsg", "추가되었습니다.");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "추가 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/keyword/delete.do")
    public ResponseEntity<Map<String, Object>> deleteKeyword(@RequestBody Map<String, Integer> param) {
        Map<String, Object> result = new HashMap<>();
        try {
            int seq = param.get("seq");
            aiClippingMapper.deleteKeyword(seq);
            result.put("resultCode", "0");
            result.put("resultMsg", "삭제되었습니다.");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "삭제 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/clipping/display.do")
    public ResponseEntity<Map<String, Object>> updateClippingDisplay(@RequestBody Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();
        try {
            aiClippingMapper.updateDisplayStatus(payload);
            result.put("resultCode", "0");
            result.put("resultMsg", "노출 상태가 변경되었습니다.");
        } catch (Exception e) {
            result.put("resultCode", "-1");
            result.put("resultMsg", "상태 변경 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    /* ================= AI 클리핑 V2: 승인 / 기사 목록 ================= */

    /**
     * 승인 상태 변경 (approve_yn). v2 파이프라인에서 월 10:00 자동 노출·발송 대상은 당일 생성 + 승인된 글.
     * 노출(display_yn) 과는 별개: 승인은 "10시에 자동으로 나가도 된다"는 표시이고, 노출은 즉시 게시다.
     */
    @PostMapping("/clipping/approve.do")
    public ResponseEntity<Map<String, Object>> updateClippingApprove(@RequestBody Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();
        try {
            String approveYn = "Y".equals(payload.get("approveYn")) ? "Y" : "N";
            Map<String, String> param = new HashMap<>();
            param.put("seq", payload.get("seq"));
            param.put("approveYn", approveYn);
            int updated = aiClippingMapper.updateApproveStatus(param);
            if (updated > 0) {
                result.put("resultCode", "0");
                result.put("resultMsg", "Y".equals(approveYn) ? "승인되었습니다. 월요일 10:00 에 자동 노출·발송됩니다." : "승인이 취소되었습니다.");
            } else {
                result.put("resultCode", "-1");
                result.put("resultMsg", "대상 글을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", "-1");
            result.put("resultMsg", "승인 상태 변경 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

    /** 해당 클리핑의 기사 단위 수집·선정 결과 (v2 생성 글만 데이터가 있음) */
    @GetMapping("/clipping/articles.ajax")
    public ResponseEntity<Map<String, Object>> getClippingArticles(@RequestParam String seq) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("resultCode", "0");
            result.put("list", aiClippingMapper.selectClippingArticlesAll(Long.parseLong(seq)));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", "-1");
            result.put("resultMsg", "기사 목록 조회 중 오류 발생");
        }
        return ResponseEntity.ok(result);
    }

}