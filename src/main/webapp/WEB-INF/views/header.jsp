<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: slhge
  Date: 2023-06-12
  Time: 오전 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <script type="application/ld+json">
        {
            "@context": "http://schema.org",
            "@type": "BreadcrumbList",
            "itemListElement": [
                {
                    "@type": "ListItem",
                    "position": 1,
                    "item": {
                        "@id": "https://kibs.com/guide/notice.do",
                        "name": "KIBS 안내"
                    }
                },
                {
                    "@type": "ListItem",
                    "position": 2,
                    "item": {
                        "@id": "https://kibs.com/exhibitor/guide.do",
                        "name": "참가업체"
                    }
                },
                {
                    "@type": "ListItem",
                    "position": 3,
                    "item": {
                        "@id": "https://kibs.com/visitor/guide_off.do",
                        "name": "참관객"
                    }
                },
                {
                    "@type": "ListItem",
                    "position": 4,
                    "item": {
                        "@id": "https://kibs.com/board/gallery.do",
                        "name": "자료실"
                    }
                },
                {
                    "@type": "ListItem",
                    "position": 5,
                    "item": {
                        "@id": "https://kibs.com/online/kibstv.do",
                        "name": "온라인 전시관"
                    }
                }
            ]
        }
    </script>

    <!-- Google Tag Manager -->
    <script>(function (w, d, s, l, i) {
        w[l] = w[l] || [];
        w[l].push({
            'gtm.start':
                new Date().getTime(), event: 'gtm.js'
        });
        var f = d.getElementsByTagName(s)[0],
            j = d.createElement(s), dl = l != 'dataLayer' ? '&l=' + l : '';
        j.async = true;
        j.src =
            'https://www.googletagmanager.com/gtm.js?id=' + i + dl;
        f.parentNode.insertBefore(j, f);
    })(window, document, 'script', 'dataLayer', 'GTM-NFTN7B47');</script>
    <!-- End Google Tag Manager -->

    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-CTSHXGZXXF"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'G-CTSHXGZXXF');
    </script>
    <!-- Google tag (gtag.js) -->

    <!-- swiper 외부 라이브러리 -->
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />

    <link href="/css/reset.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/font.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/style.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/responsive.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">

</head>
<body>
    <!-- Google Tag Manager (noscript) -->
    <noscript>
        <iframe src="https://www.googletagmanager.com/ns.html?id=GTM-NFTN7B47" height="0" width="0" style="display:none;visibility:hidden"></iframe>
    </noscript>
    <!-- End Google Tag Manager (noscript) -->

    <div id="header">

        <!-- header menu -->
        <div class="inner">
            <div class="hd_top">
                <div class="hd_top_left">
                    <a href="https://www.ufi.org/" target="_blank">
                        <img src="/img/top_logo_01.png">
                    </a>
                    <a href="https://ifbso.com/" target="_blank">
                        <img src="/img/top_logo_02.png">
                    </a>
                    <a href="/board/newsletter.do" class="hd_top_sns">
                        <img src="/img/top_sns_n.png" onmouseover="this.src='/img/top_sns_n_on.png'" onmouseout="this.src='/img/top_sns_n.png'">
                    </a>
                    <a href="https://www.instagram.com/kibskintex/" target="_blank" class="hd_top_sns">
                        <img src="/img/top_sns_i.png" onmouseover="this.src='/img/top_sns_i_on.png'" onmouseout="this.src='/img/top_sns_i.png'">
                    </a>
                    <a href="https://www.facebook.com/boatshowgg/" target="_blank" class="hd_top_sns">
                        <img src="/img/top_sns_f.png" onmouseover="this.src='/img/top_sns_f_on.png'" onmouseout="this.src='/img/top_sns_f.png'">
                    </a>
                    <a href="https://www.youtube.com/c/KIBSKINTEX" target="_blank" class="hd_top_sns">
                        <img src="/img/top_sns_y.png" onmouseover="this.src='/img/top_sns_y_on.png'" onmouseout="this.src='/img/top_sns_y.png'">
                    </a>
                </div>
                <h1 class="hd_logo">
                    <a href="/">
                        <img src="/img/logo.png">
                    </a>
                </h1>
                <div class="hd_top_right">
                    <c:if test="${empty status}">
                        <a href="/login.do" class="login">LOGIN</a>
                    </c:if>
                    <c:if test="${not empty status}">
                        <a href="/logout.do" class="logout">LOGOUT</a>
                        <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do',{ id: `${sessionScope.id}` , transferYear: new Date().getFullYear().toString() })" class="mypage">MYPAGE</a>
                    </c:if>
                    <a href="/eng/index.do" class="language">ENG</a>
                    <a href="#a" class="m_menu">
                        <span>메뉴</span>
                    </a>
                </div>
            </div>

            <div class="hd_nav">
                <div class="nav">
                    <ul class="dept1">
                        <li>
                            <a href="/guide/notice.do"><span>KIBS 안내</span></a>
                            <ul class="dept2">
                                <li><a href="/guide/notice.do"><span>공지사항</span></a></li>
                                <li><a href="/guide/summary.do"><span>행사소개</span></a></li>
                                <li><a href="/guide/program.do"><span>2025 주요프로그램</span></a></li>
                                <li><a href="https://www.kofish.kr/main/main.php" class="outLink" target="_blank"><span>한국낚시박람회 동시개최</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/exhibitor/guide.do"><span>참가기업</span></a>
                            <ul class="dept2">
                                <li><a href="/exhibitor/guide.do"><span>참가기업 안내</span></a></li>
                                <li><a href="/apply/step01.do"><span>참가기업 신청</span></a></li>
                                <li><a href="/exhibitor/login.do"><span>참가신청확인</span></a></li>
                                <li><a href="/exhibitor/faq.do"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/visitor/guide_off.do"><span>참관객</span></a>
                            <ul class="dept2">
                                <li><a href="/visitor/guide_off.do"><span>참관안내</span></a></li>
                                <li><a href="/visitor/apply.do"><span>사전등록</span></a></li>
                                <li><a href="/visitor/login.do"><span>참관신청확인</span></a></li>
                                <li><a href="/visitor/faq.do"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/board/gallery.do"><span>자료실</span></a>
                            <ul class="dept2">
                                <li><a href="/board/gallery.do"><span>갤러리</span></a></li>
                                <li><a href="/board/press.do"><span>기사 및 칼럼</span></a></li>
                                <li><a href="/board/history24.do"><span>지난 전시회</span></a></li>
                                <li><a href="/board/newsletter.do"><span>뉴스레터</span></a></li>
                                <li><a href="/board/bi.do"><span>KIBS BI</span></a></li>
                                <li><a href="/board/cerify.do"><span>인증 및 수상현황</span></a></li>
                                <li><a href="/board/award.do"><span>올해의 제품상</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/online/kibstv.do"><span>온라인 전시관</span></a>
                            <ul class="dept2">
                                <li><a href="/online/kibstv.do"><span>KIBS Tube</span></a></li>
                                <li><a href="/online/company.do"><span>참가기업 미리보기</span></a></li>
                                <li><a href="/online/product.do"><span>제품 검색하기</span></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- header menu -->

        <!-- site map & mobile -->
        <div class="hd_site_map">
            <div class="site_map_box">
                <div class="site_map_top">
                    <div class="site_map_top_btn">
                        <a href="/login.do" class="login">LOGIN</a>
                        <a href="/eng/index.do" class="language">ENG</a>
                    </div>
                    <div class="site_map_top_sns">
                        <a href="/board/newsletter.do" class="hd_top_sns">
                            <img src="/img/top_sns_n.png">
                        </a>
                        <a href="https://www.instagram.com/kibskintex/" target="_blank" class="hd_top_sns">
                            <img src="/img/top_sns_i.png">
                        </a>
                        <a href="https://www.facebook.com/boatshowgg/" target="_blank" class="hd_top_sns">
                            <img src="/img/top_sns_f.png">
                        </a>
                        <a href="https://www.youtube.com/c/KIBSKINTEX" target="_blank" class="hd_top_sns">
                            <img src="/img/top_sns_y.png">
                        </a>
                    </div>
                </div>
                <div class="site_map_nav">
                    <ul class="dept1">
                        <li>
                            <a href="/guide/notice.do"><span>KIBS 안내</span></a>
                            <ul class="dept2">
                                <li><a href="/guide/notice.do"><span>공지사항</span></a></li>
                                <li><a href="/guide/summary.do"><span>행사소개</span></a></li>
                                <li><a href="/guide/program.do"><span>2025 주요프로그램</span></a></li>
                                <li><a href="https://www.kofish.kr/main/main.php" class="outLink" target="_blank"><span>한국낚시박람회 동시개최</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/exhibitor/guide.do"><span>참가기업</span></a>
                            <ul class="dept2">
                                <li><a href="/exhibitor/guide.do"><span>참가기업 안내</span></a></li>
                                <li><a href="/apply/step01.do"><span>참가기업 신청</span></a></li>
                                <li><a href="/exhibitor/login.do"><span>참가신청확인</span></a></li>
                                <li><a href="/exhibitor/faq.do"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/visitor/guide_off.do"><span>참관객</span></a>
                            <ul class="dept2">
                                <li>
                                    <a href="/visitor/guide_off.do"><span>참관안내</span></a>
                                    <ul class="dept3">
                                        <li><a href=""><span>온라인</span></a></li>
                                        <li><a href="/visitor/guide_off.do"><span>오프라인</span></a></li>
                                    </ul>
                                </li>
                                <li><a href="/visitor/apply.do"><span>사전등록</span></a></li>
                                <li><a href="/visitor/login.do"><span>참관신청확인</span></a></li>
                                <li><a href="/visitor/faq.do"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/board/gallery.do"><span>자료실</span></a>
                            <ul class="dept2">
                                <li><a href="/board/gallery.do"><span>갤러리</span></a></li>
                                <li>
                                    <a href="/board/press.do"><span>기사 및 칼럼</span></a>
                                    <ul class="dept3">
                                        <li><a href="/board/press.do"><span>보도자료</span></a></li>
                                        <li><a href="/board/column.do"><span>칼럼</span></a></li>
                                    </ul>
                                </li>
                                <li><a href="/board/history24.do"><span>지난 전시회</span></a></li>
                                <li><a href="/board/newsletter.do"><span>뉴스레터</span></a></li>
                                <li><a href="/board/bi.do"><span>KIBS BI</span></a></li>
                                <li><a href="/board/cerify.do"><span>인증 및 수상현황</span></a></li>
                                <li><a href="/board/award.do"><span>올해의 제품상</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/online/kibstv.do"><span>온라인 전시관</span></a>
                            <ul class="dept2">
                                <li><a href="/online/kibstv.do"><span>KIBS Tube</span></a></li>
                                <li><a href="/online/company.do"><span>참가기업 미리보기</span></a></li>
                                <li><a href="/online/product.do"><span>제품 검색하기</span></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- //site map & mobile -->

    </div>
    <!-- swiper 외부 라이브러리 -->
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
    <script src="https://unpkg.com/swiper@7/swiper-bundle.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.5/dist/sweetalert2.all.min.js"></script>
    <%--script
    <script src="/js/jquery-1.9.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
    --%>
    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/jquery-migrate-3.3.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
    <script src="/js/jquery.cookie.min.js"></script>
    <script src="/js/jquery.ui.touch-punch.min.js"></script>

    <script src="/js/jquery.number.min.js"></script>
    <script src="/js/resize/compressor.js"></script>
    <script src="/js/script.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/swiper.js"></script>
    <script src="/js/main.js?ver=<%=System.currentTimeMillis()%>"></script>

</body>
</html>