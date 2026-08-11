package com.mtf.kibs.controller;

import com.mtf.kibs.dto.AiClippingDTO;
import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.mapper.AiClippingMapper;
import com.mtf.kibs.mapper.NewsletterMapper;
import com.mtf.kibs.scheduler.AiClippingScheduler;
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
    public ResponseEntity<Map<String, Object>> manualUpdateClipping() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 스케줄러에 등록된 메인 생성 로직을 수동으로 호출
            aiClippingScheduler.generateAndSendAiClipping();

            result.put("resultCode", "0");
            result.put("resultMsg", "AI 클리핑 수동 생성 및 발송 완료");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resultCode", "-1");
            result.put("resultMsg", "생성 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(result);
    }
}