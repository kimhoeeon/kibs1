package com.mtf.kibs.util;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2027 참가기업 / 참관객 신청 기간 판정 공통 모듈.
 *
 * - 국문/영문 컨트롤러와 저장 API 가 같은 기준을 쓰도록 한 곳에 모았다.
 * - 화면용 mode 파라미터(?mode=open 등)와 기간 외 저장은 테스터(접근 IP 화이트리스트 IP 또는 관리자 세션)에게만 허용한다.
 *   (일반 사용자가 URL 로 오픈 전 신청 폼을 여는 우회를 막기 위함, 판정은 TesterAccessChecker)
 */
public final class ApplyPeriod {

    public static final String MODE_SOON = "soon";
    public static final String MODE_OPEN = "open";
    public static final String MODE_CLOSE = "close";

    /* 참가기업 신청 기간 (yyyyMMddHHmm, 초과 시 다음 단계) */
    private static final long EXHIBITOR_OPEN_AFTER = 202610210859L;
    private static final long EXHIBITOR_CLOSE_AFTER = 202612312359L;

    /* 참관객 사전등록 기간 (yyyyMMddHHmm, 초과 시 다음 단계) */
    private static final long VISITOR_OPEN_AFTER = 202701010859L;
    private static final long VISITOR_CLOSE_AFTER = 202703092359L;

    private ApplyPeriod() {}

    private static long now() {
        return Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }

    private static String modeOf(long openAfter, long closeAfter) {
        long today = now();
        if (today > openAfter) {
            return today > closeAfter ? MODE_CLOSE : MODE_OPEN;
        }
        return MODE_SOON;
    }

    /** 참가기업 신청 현재 상태 (soon / open / close) */
    public static String exhibitorMode() {
        return modeOf(EXHIBITOR_OPEN_AFTER, EXHIBITOR_CLOSE_AFTER);
    }

    /** 참관객 사전등록 현재 상태 (soon / open / close) */
    public static String visitorMode() {
        return modeOf(VISITOR_OPEN_AFTER, VISITOR_CLOSE_AFTER);
    }

    public static boolean isExhibitorOpen() {
        return MODE_OPEN.equals(exhibitorMode());
    }

    public static boolean isVisitorOpen() {
        return MODE_OPEN.equals(visitorMode());
    }

    /**
     * 관리자 세션 여부.
     * 관리자 로그인 시 adminYn=Y 가 세션에 들어가며, 관리자가 "이 업체로 로그인" 한 경우에도 admin_origin_id 가 남는다.
     */
    public static boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        return "Y".equals(session.getAttribute("adminYn")) || session.getAttribute("admin_origin_id") != null;
    }

    /**
     * 화면에 내려줄 mode 결정.
     * 요청 파라미터 mode 는 테스터(접근 IP 화이트리스트 등록 IP 또는 관리자 세션, TesterAccessChecker 참고)일 때만
     * 그대로 쓰고(미리보기 용도), 그 외에는 날짜 기준 값을 쓴다.
     */
    public static String resolveMode(String requestedMode, String computedMode, boolean tester) {
        if (requestedMode != null && !requestedMode.isEmpty() && tester) {
            return requestedMode;
        }
        return computedMode;
    }

    /** 요청에 mode 파라미터가 있는지 (있을 때만 테스터 판정을 수행해 불필요한 DB 조회를 피한다) */
    public static boolean hasRequestedMode(String requestedMode) {
        return requestedMode != null && !requestedMode.isEmpty();
    }
}
