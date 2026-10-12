package com.mtf.kibs.config;

import com.mtf.kibs.interceptor.AdminAccessInterceptor;
import com.mtf.kibs.interceptor.AdminSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAccessInterceptor adminAccessInterceptor;

    @Autowired
    private AdminSessionInterceptor adminSessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. IP 검증을 먼저 수행하고 (/mng/ 하위 전체 적용)
        registry.addInterceptor(adminAccessInterceptor)
                .addPathPatterns("/mng/**");

        // 2. IP 검증을 통과한 요청 중 세션을 검증합니다.
        registry.addInterceptor(adminSessionInterceptor)
                .addPathPatterns("/mng/**")
                .excludePathPatterns(
                        // 1) 로그인/로그아웃 관련 페이지 및 API
                        "/mng/index.do",
                        "/mng/login.do",
                        "/mng/logoutCheck.do", // 세션 만료 시 에러 방지

                        // 2) 정적 리소스 (프로젝트 구조 기반)
                        "/assets/**",
                        "/css/**",
                        "/file/**",     // 첨부파일 다운로드 등
                        "/fonts/**",
                        "/img/**",
                        "/js/**",
                        "/video/**",
                        "/upload/**",   //업로드된 파일 접근 경로
                        "/error"
                );
    }
}