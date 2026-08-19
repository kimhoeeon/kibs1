package com.mtf.kibs.controller;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.NewsletterSendHistoryDTO;
import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.mapper.NewsletterMapper;
import com.mtf.kibs.scheduler.AiClippingScheduler;
import com.mtf.kibs.service.NewsletterService;
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
            // 1. 백엔드 2차 검증: 오늘 날짜 기사가 이미 있는지 DB 조회
            String todayDate = java.time.LocalDate.now().toString();
            AiClippingDTO searchDto = new AiClippingDTO();
            searchDto.setTitle(todayDate); // 제목에 오늘 날짜가 포함되어 있는지 LIKE 검색

            int todayClippingCount = aiClippingMapper.selectAiClippingCount(searchDto);

            if (todayClippingCount > 0) {
                // 이미 존재한다면 스케줄러를 호출하지 않고 에러 메시지 반환
                result.put("resultCode", "-1");
                result.put("resultMsg", "오늘 날짜의 AI 클리핑 기사가 이미 존재합니다.\n새로 생성하시려면 기존 기사를 먼저 삭제해 주세요.");
                return ResponseEntity.ok(result);
            }

            // 2. 중복이 없을 경우에만 정상적으로 생성/발송 프로세스 실행
            boolean isSend = "Y".equals(sendYn);
            aiClippingScheduler.processAiClipping(isSend);

            result.put("resultCode", "0");
            result.put("resultMsg", isSend ? "AI 클리핑 기사 생성 및 뉴스레터 발송이 완료되었습니다." : "AI 클리핑 기사가 생성되었습니다. (발송 생략)");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", "-1");
            result.put("resultMsg", "생성 중 오류가 발생했습니다.");
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
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> params = new HashMap<>();
        params.put("clippingSeq", clippingSeq);
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

}