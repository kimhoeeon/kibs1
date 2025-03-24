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

    <link href="/css/reset.css" rel="stylesheet">
    <link href="/css/font.css" rel="stylesheet">
    <link href="/css/style.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/responsive.css" rel="stylesheet">

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
                    <a href="/eng/board/newsletter.do" class="hd_top_sns">
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
                    <a href="/eng/index.do">
                        <img src="/img/logo_eng.png">
                    </a>
                </h1>
                <div class="hd_top_right">
                    <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');" class="login">LOGIN</a>
                    <a href="/" class="language">KOR</a>
                    <a href="#a" class="m_menu">
                        <span>Menu</span>
                    </a>
                </div>
            </div>

            <div class="hd_nav">
                <div class="nav">
                    <ul class="dept1">
                        <li>
                            <a href="/eng/guide/summary.do"><span>KIBS 2025</span></a>
                            <ul class="dept2">
                                <li><a href="/eng/guide/summary.do"><span>Overview</span></a></li>
                                <li><a href="/eng/guide/venue.do"><span>Venue</span></a></li>
                                <li><a href="/eng/guide/bi.do"><span>KIBS BI</span></a></li>
                                <li><a href="/eng/guide/brochure.do"><span>E-Brochure</span></a></li>
                                <li><a href="/eng/guide/certify.do"><span>International Certification&Awards</span></a></li>
                                <li><a href="/eng/guide/contact.do"><span>Contact us</span></a></li>
                                <li><a href="/eng/guide/program.do"><span>Program</span></a></li>
                                <li><a href="https://www.kofish.kr/main/main.php" class="outLink" target="_blank"><span>Held Concurrently with KOFISH</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/eng/exhibitor/categories.do"><span>Exhibitors</span></a>
                            <ul class="dept2">
                                <li><a href="/eng/exhibitor/categories.do"><span>Exhibitors Categories</span></a></li>
                                <li><a href="/eng/exhibitor/list.do"><span>Exhibitor List</span></a></li>
                                <li><a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>"><span>Exhibitor Registration</span></a></li>
                                <li><a href="/eng/exhibitor/glance.do"><span>Korea at a Glance</span></a></li>
                                <li><a href="/eng/exhibitor/marina.do"><span>Marine Industry in korea</span></a></li>
                                <li><a href="/eng/exhibitor/match.do"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                                <li><a href="/eng/exhibitor/qna.do"><span>QnA</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/eng/buyer/apply.do"><span>Buyers</span></a>
                            <ul class="dept2">
                                <li><a href="https://www.investkorea.org/ik-en/index.do" target="_blank"><span>Investor program</span></a></li>
                                <li><a href="/eng/buyer/apply.do"><span>Pre-registration</span></a></li>
                                <li><a href="/eng/buyer/glance.do"><span>Korea at a Glance</span></a></li>
                                <li><a href="/eng/buyer/marina.do"><span>Marine Industry in korea</span></a></li>
                                <li><a href="/eng/buyer/match.do"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                                <li><a href="/eng/buyer/qna.do"><span>QnA</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/eng/board/notice.do"><span>News</span></a>
                            <ul class="dept2">
                                <li><a href="/eng/board/notice.do"><span>Notice</span></a></li>
                                <li><a href="/eng/board/kibstv.do"><span>KIBS Tube</span></a></li>
                                <%--<li><a href="/eng/board/media.do"><span>KIBS in Media</span></a></li>--%>
                                <%--<li><a href="/eng/board/newsletter.do"><span>Newsletter</span></a></li>--%>
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
                        <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');" class="login">LOGIN</a>
                        <a href="/" class="language">KOR</a>
                    </div>
                    <div class="site_map_top_sns">
                        <%--<a href="/eng/board/newsletter.do" class="hd_top_sns"><img src="/img/top_sns_n.png"></a>--%>
                        <a href="https://www.instagram.com/kibskintex/" target="_blank" class="hd_top_sns"><img src="/img/top_sns_i.png"></a>
                        <a href="https://www.facebook.com/boatshowgg/" target="_blank" class="hd_top_sns"><img src="/img/top_sns_f.png"></a>
                        <a href="https://www.youtube.com/c/KIBSKINTEX" target="_blank" class="hd_top_sns"><img src="/img/top_sns_y.png"></a>
                    </div>
                </div>
                <div class="site_map_nav">
                    <ul class="dept1">
                        <li>
                            <a href="/eng/guide/summary.do"><span>KIBS 2025</span></a>
                            <ul class="dept2">
                                <li><a href="/eng/guide/summary.do"><span>Overview</span></a></li>
                                <li>
                                    <a href="/eng/guide/venue.do"><span>Venue</span></a>
                                    <ul class="dept3">
                                        <li><a href="/eng/guide/venue.do"><span>Venue</span></a></li>
                                        <li><a href="/eng/guide/to_kintex.do"><span>To Kintex</span></a></li>
                                        <li><a href="/eng/guide/to_ara.do"><span>To ARA Marina</span></a></li>
                                    </ul>
                                </li>
                                <li><a href="/eng/guide/bi.do"><span>KIBS BI</span></a></li>
                                <li><a href="/eng/guide/brochure.do"><span>E-Brochure</span></a></li>
                                <li><a href="/eng/guide/certify.do"><span>International Certification&Awards</span></a></li>
                                <li><a href="/eng/guide/contact.do"><span>Contact us</span></a></li>
                                <li><a href="/eng/guide/program.do"><span>Program</span></a></li>
                                <li><a href="https://www.kofish.kr/main/main.php" class="outLink" target="_blank"><span>Held Concurrently with KOFISH</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/eng/exhibitor/categories.do"><span>Exhibitors</span></a>
                            <ul class="dept2">
                                <li><a href="/eng/exhibitor/categories.do"><span>Exhibitors Categories</span></a></li>
                                <li><a href="/eng/exhibitor/list.do"><span>Exhibitor List</span></a></li>
                                <li><a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>"><span>Exhibitor Registration</span></a></li>
                                <li><a href="/eng/exhibitor/glance.do"><span>Korea at a Glance</span></a></li>
                                <li><a href="/eng/exhibitor/marina.do"><span>Marine Industry in korea</span></a></li>
                                <li><a href="/eng/exhibitor/match.do"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                                <li><a href="/eng/exhibitor/qna.do"><span>QnA</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/eng/buyer/apply.do"><span>Buyers</span></a>
                            <ul class="dept2">
                                <li><a href="https://www.investkorea.org/ik-en/index.do" target="_blank"><span>Investor program</span></a></li>
                                <li><a href="/eng/buyer/apply.do"><span>Pre-registration</span></a></li>
                                <li><a href="/eng/buyer/glance.do"><span>Korea at a Glance</span></a></li>
                                <li><a href="/eng/buyer/marina.do"><span>Marine Industry in korea</span></a></li>
                                <li><a href="/eng/buyer/match.do"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                                <li><a href="/eng/buyer/qna.do"><span>QnA</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/eng/board/notice.do"><span>News</span></a>
                            <ul class="dept2">
                                <li><a href="/eng/board/notice.do"><span>Notice</span></a></li>
                                <li><a href="/eng/board/kibstv.do"><span>KIBS Tube</span></a></li>
                                <%--<li><a href="/eng/board/media.do"><span>KIBS in Media</span></a></li>--%>
                                <%--<li><a href="/eng/board/newsletter.do"><span>Newsletter</span></a></li>--%>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- //site map & mobile -->

    </div>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://unpkg.com/swiper@7/swiper-bundle.min.js"></script>
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