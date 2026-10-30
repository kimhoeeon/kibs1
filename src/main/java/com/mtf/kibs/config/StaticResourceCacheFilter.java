package com.mtf.kibs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 정적 스크립트·스타일시트(/js/**, /css/**)에 Cache-Control 을 붙이는 필터 (2026-10-30 QA 대응).
 *
 * 배경: 서버가 Last-Modified 만 보내고 Cache-Control 이 없으면 브라우저는 휴리스틱으로 수 일~수 주간 재검증 없이
 * 캐시본을 쓴다. 2026-10-29 배포 때 main.js 의 ?ver 를 올리지 않아 QA 브라우저가 옛 main.js(옛 단가·옛 협회 할인 id)로
 * 계산·저장하는 사고가 있었다. no-cache 는 "캐시는 하되 매번 If-Modified-Since 로 재검증"이므로 변경이 없으면 304 로
 * 가볍게 끝나고, 변경이 있으면 ?ver 를 올리지 않아도 새 파일을 받는다.
 *
 * 설정: static.cache-control (기본 "no-cache"). 빈 값이면 헤더를 붙이지 않는다(서버 application.properties 에 항목이 없어도 동작).
 */
@Component
public class StaticResourceCacheFilter extends OncePerRequestFilter {

    @Value("${static.cache-control:no-cache}")
    private String cacheControl;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (cacheControl == null || cacheControl.trim().isEmpty()) {
            return true;
        }
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        if (ctx != null && !ctx.isEmpty() && uri.startsWith(ctx)) {
            uri = uri.substring(ctx.length());
        }
        return !(uri.startsWith("/js/") || uri.startsWith("/css/"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", cacheControl.trim());
        filterChain.doFilter(request, response);
    }
}
