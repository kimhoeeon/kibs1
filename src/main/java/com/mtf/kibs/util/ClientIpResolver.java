package com.mtf.kibs.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 클라이언트 IP 추출 공통 모듈.
 *
 * X-Forwarded-For 등 프록시 헤더는 클라이언트가 임의로 넣을 수 있으므로,
 * 기본값(false)에서는 무시하고 request.getRemoteAddr() 만 신뢰한다.
 * 리버스 프록시/WAF 뒤에서 운영하여 실제 IP 가 헤더로만 전달되는 환경에서는
 * application.properties 에 security.trust-proxy-headers=true 를 설정한다.
 */
@Component
public class ClientIpResolver {

    @Value("${security.trust-proxy-headers:false}")
    private boolean trustProxyHeaders;

    public String resolve(HttpServletRequest request) {
        if (request == null) return null;

        if (trustProxyHeaders) {
            String ip = request.getHeader("X-Forwarded-For");
            if (isBlank(ip)) ip = request.getHeader("Proxy-Client-IP");
            if (isBlank(ip)) ip = request.getHeader("WL-Proxy-Client-IP");
            if (!isBlank(ip)) {
                // "client, proxy1, proxy2" 형태일 경우 최초 클라이언트 IP 사용
                if (ip.contains(",")) ip = ip.split(",")[0];
                return ip.trim();
            }
        }
        return request.getRemoteAddr();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty() || "unknown".equalsIgnoreCase(s.trim());
    }
}
