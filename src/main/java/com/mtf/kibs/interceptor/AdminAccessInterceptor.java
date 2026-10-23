package com.mtf.kibs.interceptor;

import com.mtf.kibs.dto.AccessDTO;
import com.mtf.kibs.dto.ResponseDTO;
import com.mtf.kibs.service.KibsMngService;
import com.mtf.kibs.util.ClientIpResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminAccessInterceptor implements HandlerInterceptor {

    @Autowired
    private KibsMngService kibsMngService;

    @Autowired
    private ClientIpResolver clientIpResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 실제 IP 추출
        //    X-Forwarded-For 등 프록시 헤더는 클라이언트가 위조할 수 있어 기본적으로 무시한다.
        //    (프록시 뒤 운영 시 security.trust-proxy-headers=true 로 설정, ClientIpResolver 참고)
        String clientIp = clientIpResolver.resolve(request);

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