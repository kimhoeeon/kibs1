package com.mtf.kibs.interceptor;

import com.mtf.kibs.dto.AccessDTO;
import com.mtf.kibs.dto.ResponseDTO;
import com.mtf.kibs.service.KibsMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminAccessInterceptor implements HandlerInterceptor {

    @Autowired
    private KibsMngService kibsMngService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 프록시(L4/WAF) 환경을 고려한 실제 IP 추출
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) clientIp = request.getHeader("Proxy-Client-IP");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) clientIp = request.getHeader("WL-Proxy-Client-IP");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) clientIp = request.getRemoteAddr();
        if (clientIp != null && clientIp.contains(",")) clientIp = clientIp.split(",")[0].trim();

        // 2. IP 화이트리스트 검사
        AccessDTO dto = new AccessDTO();
        dto.setIp(clientIp);
        ResponseDTO res = kibsMngService.processSelectAccessIp(dto);

        // 3. 미등록 IP인 경우 메인 페이지로 강제 리다이렉트
        if (res == null || Integer.parseInt(res.getCustomValue()) == 0) {
            // AJAX 요청인지 확인
            String ajaxHeader = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajaxHeader)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied IP");
            } else {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().write("<script>alert('접근 권한이 없는 IP입니다.'); location.href='/';</script>");
            }
            return false;
        }
        return true; // 인가된 IP면 통과
    }
}