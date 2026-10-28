package com.mtf.kibs.util;

import com.mtf.kibs.dto.AccessDTO;
import com.mtf.kibs.dto.ResponseDTO;
import com.mtf.kibs.service.KibsMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 신청 기간 외 테스트 허용 대상 판정.
 *
 * 다음 중 하나면 테스터로 본다.
 *  1) 관리자 접근 IP 관리(ACCESS 테이블)에 등록된 IP 에서 온 요청  ← 개발사무실 테스트 용도 (관리자 페이지 접근 권한과 동일 목록)
 *  2) 설정 security.tester-ips 에 나열된 IP            ← 발주사 등, 관리자 페이지 권한 없이 테스트 모드만 허용
 *  3) 관리자 세션 (adminYn=Y 또는 "이 업체로 로그인" 상태)
 *
 * 테스터에게는 ?mode=open|soon|close 화면 미리보기와 기간 외 저장이 허용된다.
 */
@Component
public class TesterAccessChecker {

    @Autowired
    private KibsMngService kibsMngService;

    @Autowired
    private ClientIpResolver clientIpResolver;

    /** 테스트 모드만 허용할 IP 목록(쉼표 구분). 기본값 = 발주사 킨텍스 IP (2026-10-28 요청). 서버 설정으로 덮어쓸 수 있다. */
    @Value("${security.tester-ips:61.80.16.77}")
    private String testerIps;

    public boolean isTester(HttpServletRequest request, HttpSession session) {
        if (ApplyPeriod.isAdmin(session)) return true;
        return isAllowedIp(request);
    }

    /** 접근 IP 화이트리스트(ACCESS 테이블) 또는 설정 security.tester-ips 에 있는 IP 인지 확인 */
    public boolean isAllowedIp(HttpServletRequest request) {
        String ip = clientIpResolver.resolve(request);
        if (ip == null || ip.isEmpty()) return false;
        if (configuredTesterIps().contains(ip.trim())) return true;
        try {
            AccessDTO dto = new AccessDTO();
            dto.setIp(ip);
            ResponseDTO res = kibsMngService.processSelectAccessIp(dto);
            return res != null && res.getCustomValue() != null && Integer.parseInt(res.getCustomValue()) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private Set<String> configuredTesterIps() {
        Set<String> set = new HashSet<>();
        if (testerIps == null) return set;
        for (String s : testerIps.split(",")) {
            String t = s.trim();
            if (!t.isEmpty()) set.add(t);
        }
        return set;
    }
}
