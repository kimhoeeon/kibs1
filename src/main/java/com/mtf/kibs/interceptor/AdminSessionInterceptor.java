package com.mtf.kibs.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // 세션이 없거나, 로그인 ID가 없으면 처리
        if (session == null || session.getAttribute("id") == null) {
            String ajaxHeader = request.getHeader("X-Requested-With");

            // AJAX 요청일 경우 401 에러 반환 (JS에서 에러를 캐치해 리다이렉트 하도록)
            if ("XMLHttpRequest".equals(ajaxHeader)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session Expired");
            } else {
                // 일반 페이지 요청일 경우 로그인 페이지로 리다이렉트
                response.sendRedirect("/mng/index.do");
            }
            return false;
        }
        return true;
    }
}