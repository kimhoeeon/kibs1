<%
    // 리디렉션할 최종 목적지 URL
    String targetUrl = "https://www.kibs.com/guide/summary_kmts.do";

    // HTTP 301 리디렉션 응답을 보냅니다.
    // 301은 "영구 이동"을 의미하며, 검색 엔진 최적화(SEO)에도 가장 좋은 방식입니다.
    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    response.setHeader("Location", targetUrl);
    response.setHeader("Connection", "close");
%>