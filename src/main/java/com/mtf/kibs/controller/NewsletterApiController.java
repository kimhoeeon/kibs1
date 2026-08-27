package com.mtf.kibs.controller;

import com.mtf.kibs.dto.NewsletterSubscriberDTO;
import com.mtf.kibs.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterApiController {

    @Autowired
    private NewsletterService newsletterService;

    // 구독 신청
    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, Object>> subscribe(@RequestBody NewsletterSubscriberDTO subscriberDTO) {

        Map<String, Object> errorResult = new HashMap<>();

        try {
            // 필수 값 검증
            if(subscriberDTO.getEmail() == null || subscriberDTO.getEmail().isEmpty() ||
                    subscriberDTO.getName() == null || subscriberDTO.getName().isEmpty()) {
                errorResult.put("resultCode", "-1");
                errorResult.put("resultMsg", "이름과 이메일을 입력해주세요.");
                return ResponseEntity.badRequest().body(errorResult);
            }

            if(!"Y".equals(subscriberDTO.getPrivacyAgreeYn()) || !"Y".equals(subscriberDTO.getAdAgreeYn())) {
                errorResult.put("resultCode", "-1");
                errorResult.put("resultMsg", "필수 약관에 동의해야 합니다.");
                return ResponseEntity.badRequest().body(errorResult);
            }

            // 정상 처리
            Map<String, Object> result = newsletterService.subscribe(subscriberDTO);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // 서버 내부 오류 발생 시 안전하게 JSON 포맷으로 에러 응답
            e.printStackTrace();
            errorResult.put("resultCode", "-1");
            errorResult.put("resultMsg", "서버 통신 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    // 프론트엔드 메인 화면에서 '구독 해지' 버튼 클릭 시 (AJAX POST)
    @PostMapping("/unsubscribe")
    public ResponseEntity<Map<String, Object>> unsubscribePost(@RequestBody Map<String, String> param) {
        String email = param.get("email");
        Map<String, Object> result = newsletterService.unsubscribe(email);
        return ResponseEntity.ok(result);
    }

    // 수신 거부 (뉴스레터 이메일 하단 링크 클릭 시 이동 - GET)
    @GetMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String email) {
        Map<String, Object> result = newsletterService.unsubscribe(email);

        String msg = (String) result.get("resultMsg");

        // 브라우저 경고창(Alert)을 띄우고 메인 페이지로 이동시키는 스크립트 반환
        String htmlResponse = "<html><body><script>alert('" + msg + "'); location.href='/';</script></body></html>";

        return ResponseEntity.ok().header("Content-Type", "text/html; charset=UTF-8").body(htmlResponse);
    }
}