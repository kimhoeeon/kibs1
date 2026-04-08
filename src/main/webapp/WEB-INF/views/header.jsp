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

    <!-- swiper 외부 라이브러리 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />

    <link href="/css/reset.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/font.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/style.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/responsive.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">

</head>
<body>
    <div id="header">

        <!-- header menu -->
        <div class="inner">
            <div class="hd_top">
                <div class="hd_top_left">
                    <a href="https://www.ufi.org/" target="_blank">
                        <img src="/img/top_logo_01.png">
                    </a>
                    <a href="https://www.ifbso.com/home" target="_blank">
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
                    <a href="<c:url value="/"/>">
                        <img src="/img/logo.png">
                    </a>
                </h1>
                <div class="hd_top_right">
                    <c:if test="${empty sessionScope.get('status')}">
                        <a href="<c:url value="/login.do"/>" class="login">LOGIN</a>
                    </c:if>
                    <c:if test="${not empty sessionScope.get('status')}">
                        <a href="/logout.do" class="logout">LOGOUT</a>
                        <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do',{ id: `${sessionScope.get('id')}` , transferYear: '2026' })" class="mypage">MYPAGE</a>
                    </c:if>
                    <a href="javascript:void(0);" onclick="home('en')" class="language">ENG</a>
                    <a href="#a" class="m_menu">
                        <span>메뉴</span>
                    </a>
                </div>
            </div>

            <div class="hd_nav">
                <div class="nav">
                    <ul class="dept1">
                        <li>
                            <a href="<c:url value="/guide/summary.do"/>"><span>KIBS 안내</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/guide/summary.do"/>"><span>행사소개</span></a></li>
                                <li><a href="<c:url value="/board/history26.do"/>"><span>지난 전시회</span></a></li>
                                <li><a href="<c:url value="/board/award.do"/>"><span>올해의 제품상</span></a></li>
                                <li><a href="<c:url value="/board/bi.do"/>"><span>KIBS BI</span></a></li>
                                <li><a href="<c:url value="/board/cerify.do"/>"><span>인증 및 수상현황</span></a></li>
                                <li><a href="https://www.kofish.kr/main/main.php" class="outLink" target="_blank"><span>동시개최행사<br>[한국국제낚시박람회]</span></a></li>
                                <li><a href="https://www.campingfair.co.kr/camping/" class="outLink" target="_blank"><span>[캠핑&피크닉페어]</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/exhibitor/guide.do"/>"><span>참가기업</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/exhibitor/guide.do"/>"><span>참가기업 안내</span></a></li>
                                <li><a href="<c:url value="/apply/step01.do"/>"><span>참가신청</span></a></li>
                                <li><a href="<c:url value="/exhibitor/login.do"/>"><span>참가신청확인</span></a></li>
                                <li><a href="<c:url value="/exhibitor/faq.do"/>"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/visitor/guide_off.do"/>"><span>참관객</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/visitor/guide_off.do"/>"><span>참관안내</span></a></li>
                                <li><a href="<c:url value="/visitor/apply.do"/>"><span>사전등록</span></a></li>
                                <li><a href="<c:url value="/guide/conference.do"/>"><span>컨퍼런스</span></a></li>
                                <li><a href="<c:url value="/guide/program.do"/>"><span>체험 프로그램</span></a></li>
                                <li><a href="<c:url value="/visitor/login.do"/>"><span>참관신청확인</span></a></li>
                                <li><a href="<c:url value="/visitor/faq.do"/>"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/guide/notice.do"/>"><span>미디어센터</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/guide/notice.do"/>"><span>공지사항</span></a></li>
                                <li><a href="<c:url value="/board/gallery.do"/>"><span>갤러리</span></a></li>
                                <li><a href="<c:url value="/online/kibstv.do"/>"><span>KIBS Tube</span></a></li>
                                <li><a href="<c:url value="/board/press.do"/>"><span>기사 및 칼럼</span></a></li>
                                <li><a href="<c:url value="/board/newsletter.do"/>"><span>뉴스레터</span></a></li>
                                <li><a href="<c:url value="/board/contest.do"/>"><span>해양레저 사진 공모전</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/online/company.do"/>"><span>온라인 전시관</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/online/company.do"/>"><span>참가기업 미리보기</span></a></li>
                                <li><a href="<c:url value="/online/product.do"/>"><span>제품 검색하기</span></a></li>
                            </ul>
                        </li>
                        <li class="kiss_nav">
                            <a href="<c:url value="/guide/summary_kiss.do"/>"><span>KISS<br>(코리아 서프쇼)</span></a>
                        </li>
                        <li class="kmts_nav">
                            <a href="<c:url value="/guide/summary_kmts.do"/>"><span>KMTS<br>(해양관광전)</span></a>
                        </li>
                        <%--<li class="expo">
                            <a href="<c:url value="/guide/maritime_expo.do"/>"><span>해상전시회<br>(시흥 거북섬마리나)</span></a>
                        </li>--%>
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
                        <c:if test="${empty sessionScope.get('status')}">
                            <a href="<c:url value="/login.do"/>" class="login">LOGIN</a>
                            <a href="javascript:void(0);" onclick="home('en')" class="language">ENG</a>
                        </c:if>
                        <c:if test="${not empty sessionScope.get('status')}">
                            <a href="/logout.do" class="logout">LOGOUT</a>
                            <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do',{ id: `${sessionScope.get('id')}` , transferYear: '2026' })" class="mypage">MYPAGE</a>
                        </c:if>
                    </div>
                    <div class="site_map_top_sns">
                        <a href="<c:url value="/board/newsletter.do"/>" class="hd_top_sns">
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
                            <a href="<c:url value="/guide/summary.do"/>"><span>KIBS 안내</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/guide/summary.do"/>"><span>행사소개</span></a></li>
                                <li><a href="<c:url value="/board/history26.do"/>"><span>지난 전시회</span></a></li>
                                <li><a href="<c:url value="/board/award.do"/>"><span>올해의 제품상</span></a></li>
                                <li><a href="<c:url value="/board/bi.do"/>"><span>KIBS BI</span></a></li>
                                <li><a href="<c:url value="/board/cerify.do"/>"><span>인증 및 수상현황</span></a></li>
                                <li><a href="https://www.kofish.kr/main/main.php" class="outLink" target="_blank"><span>동시개최행사<br>[한국국제낚시박람회]</span></a></li>
                                <li><a href="https://www.campingfair.co.kr/camping/" class="outLink" target="_blank"><span>[캠핑&피크닉페어]</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/exhibitor/guide.do"/>"><span>참가기업</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/exhibitor/guide.do"/>"><span>참가기업 안내</span></a></li>
                                <li><a href="<c:url value="/apply/step01.do"/>"><span>참가기업 신청</span></a></li>
                                <li><a href="<c:url value="/exhibitor/login.do"/>"><span>참가신청확인</span></a></li>
                                <li><a href="<c:url value="/exhibitor/faq.do"/>"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/visitor/guide_off.do"/>"><span>참관객</span></a>
                            <ul class="dept2">
                                <li>
                                    <a href="<c:url value="/visitor/guide_off.do"/>"><span>참관안내</span></a>
                                    <ul class="dept3">
                                        <li><a href="<c:url value="/online/company.do"/>"><span>온라인</span></a></li>
                                        <li><a href="<c:url value="/visitor/guide_off.do"/>"><span>오프라인</span></a></li>
                                    </ul>
                                </li>
                                <li><a href="<c:url value="/visitor/apply.do"/>"><span>사전등록</span></a></li>
                                <li><a href="<c:url value="/guide/conference.do"/>"><span>컨퍼런스</span></a></li>
                                <li><a href="<c:url value="/guide/program.do"/>"><span>체험 프로그램</span></a></li>
                                <li><a href="<c:url value="/visitor/login.do"/>"><span>참관신청확인</span></a></li>
                                <li><a href="<c:url value="/visitor/faq.do"/>"><span>FAQ</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/guide/notice.do"/>"><span>미디어센터</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/guide/notice.do"/>"><span>공지사항</span></a></li>
                                <li><a href="<c:url value="/board/gallery.do"/>"><span>갤러리</span></a></li>
                                <li><a href="<c:url value="/online/kibstv.do"/>"><span>KIBS Tube</span></a></li>
                                <li>
                                    <a href="<c:url value="/board/press.do"/>"><span>기사 및 칼럼</span></a>
                                    <ul class="dept3">
                                        <li><a href="<c:url value="/board/press.do"/>"><span>보도자료</span></a></li>
                                        <li><a href="<c:url value="/board/column.do"/>"><span>칼럼</span></a></li>
                                    </ul>
                                </li>
                                <li><a href="<c:url value="/board/newsletter.do"/>"><span>뉴스레터</span></a></li>
                                <li><a href="<c:url value="/board/contest.do"/>"><span>해양레저 사진 공모전</span></a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<c:url value="/online/company.do"/>"><span>온라인 전시관</span></a>
                            <ul class="dept2">
                                <li><a href="<c:url value="/online/company.do"/>"><span>참가기업 미리보기</span></a></li>
                                <li><a href="<c:url value="/online/product.do"/>"><span>제품 검색하기</span></a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="link_dept1 site_map_new">
                        <li class="kiss_nav">
                            <a href="<c:url value="/guide/summary_kiss.do"/>"><span>KISS<br>(코리아 서프쇼)</span></a>
                        </li>
                        <li class="kmts_nav">
                            <a href="<c:url value="/guide/summary_kmts.do"/>"><span>KMTS<br>(해양관광전)</span></a>
                        </li>
                        <%--<li class="expo">
                            <a href="<c:url value="/guide/maritime_expo.do"/>"><span>해상전시회<br>(시흥 거북섬마리나)</span></a>
                        </li>--%>
                    </ul>
                </div>
            </div>
        </div>
        <!-- //site map & mobile -->

    </div>
    <!-- swiper 외부 라이브러리 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
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