package com.mtf.kibs.controller;

import com.mtf.kibs.mapper.NewsletterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/mng/center/board")
public class AdminNewsletterController {

    @Autowired
    private NewsletterMapper newsletterMapper;

    // 뉴스레터 구독자 관리 페이지
    @GetMapping("/subscriber.do")
    public String subscriberList(Model model) {
        // 상단 대시보드 통계 데이터 조회
        Map<String, Object> stats = newsletterMapper.selectSubscriberStatistics();
        model.addAttribute("stats", stats);

        return "mng/center/board/subscriber";
    }

    // AI 뉴스 클리핑 관리 페이지
    @GetMapping("/clipping.do")
    public String clippingList() {
        return "mng/center/board/clipping";
    }
}