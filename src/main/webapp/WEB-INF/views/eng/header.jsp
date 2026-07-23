<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <a href="javascript:void(0);" onclick="home('en')">
                    <img src="/img/logo_eng.png?ver=20260421">
                </a>
            </h1>
            <div class="hd_top_right">
                <c:if test="${empty sessionScope.get('status')}">
                    <a href="<c:url value="/eng/login.do"/>" class="login">LOGIN</a>
                </c:if>
                <c:if test="${not empty sessionScope.get('status')}">
                    <a href="/logout.do" class="logout">LOGOUT</a>
                    <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/index.do',{ id: `${sessionScope.get('id')}` , transferYear: '2027' })" class="mypage">MYPAGE</a>
                </c:if>
                <a href="javascript:void(0);" onclick="home('KO')" class="language">KOR</a>
                <a href="#a" class="m_menu">
                    <span>MENU</span>
                </a>
            </div>
        </div>

        <div class="hd_nav">
            <div class="nav">
                <ul class="dept1">
                    <li>
                        <a href="<c:url value="/eng/guide/summary.do"/>"><span>KIBS 2027</span></a>
                        <ul class="dept2">
                            <li><a href="<c:url value="/eng/guide/summary.do"/>"><span>Overview</span></a></li>
                            <li><a href="<c:url value="/eng/guide/certify.do"/>"><span>International Certification & Awards</span></a></li>
                            <li><a href="<c:url value="/eng/guide/conference.do"/>"><span>Conference</span></a></li>
                            <li><a href="<c:url value="/eng/guide/program.do"/>"><span>Program</span></a></li>
                            <li><a href="<c:url value="/eng/guide/bi.do"/>"><span>KIBS BI</span></a></li>
                            <li><a href="<c:url value="/eng/guide/venue.do"/>"><span>Venue</span></a></li>
                            <li><a href="<c:url value="/eng/guide/contact.do"/>"><span>Contact us</span></a></li>
                            <%--<li><a href="/eng/guide/brochure.do"><span>E-Brochure</span></a></li>--%>
                            <li><a href="https://www.kofish.kr/eng/main/main.php" class="outLink" target="_blank"><span>Concurrently event<br>[KOFISH]</span></a></li>
                            <li><a href="https://www.campingfair.co.kr/eng" class="outLink" target="_blank"><span>[Caravan&Camping]</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="<c:url value="/eng/exhibitor/categories.do"/>"><span>Exhibitors</span></a>
                        <ul class="dept2">
                            <li><a href="<c:url value="/eng/exhibitor/categories.do"/>"><span>Exhibitors Categories</span></a></li>
                            <li><a href="<c:url value="/eng/apply/step01.do"/>"><span>Exhibitor Registration</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/glance.do"/>"><span>Korea at a Glance</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/marina.do"/>"><span>Marine Industry in korea</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/match.do"/>"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                            <li><a href="<c:url value="/eng/online/company.do"/>"><span>Exhibitor List</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/qna.do"/>"><span>FAQ</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="<c:url value="/eng/buyer/glance.do"/>"><span>Buyers</span></a>
                        <ul class="dept2">
                            <li><a href="https://www.investkorea.org/ik-en/index.do" target="_blank"><span>Visitor guide</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/apply.do"/>"><span>Pre-registration</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/glance.do"/>"><span>Korea at a Glance</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/marina.do"/>"><span>Marine Industry in korea</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/match.do"/>"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/qna.do"/>"><span>FAQ</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="<c:url value="/eng/board/notice.do"/>"><span>News</span></a>
                        <ul class="dept2">
                            <li><a href="<c:url value="/eng/board/notice.do"/>"><span>Notice</span></a></li>
                            <li><a href="<c:url value="/eng/board/kibstv.do"/>"><span>KIBS Tube</span></a></li>
                            <li><a href="<c:url value="/eng/board/gallery.do"/>"><span>Gallery</span></a></li>
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
                    <c:if test="${empty sessionScope.get('status')}">
                        <a href="<c:url value="/eng/login.do"/>" class="login">LOGIN</a>
                        <a href="<c:url value="/"/>" class="language">KOR</a>
                    </c:if>
                    <c:if test="${not empty sessionScope.get('status')}">
                        <a href="/logout.do" class="logout">LOGOUT</a>
                        <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/index.do',{ id: `${sessionScope.get('id')}` , transferYear: '2027' })" class="mypage">MYPAGE</a>
                    </c:if>
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
                        <a href="<c:url value="/eng/guide/summary.do"/>"><span>KIBS 2027</span></a>
                        <ul class="dept2">
                            <li><a href="<c:url value="/eng/guide/summary.do"/>"><span>Overview</span></a></li>
                            <li><a href="<c:url value="/eng/guide/certify.do"/>"><span>International Certification & Awards</span></a></li>
                            <li><a href="<c:url value="/eng/guide/conference.do"/>"><span>Conference</span></a></li>
                            <li><a href="<c:url value="/eng/guide/program.do"/>"><span>Program</span></a></li>
                            <li><a href="<c:url value="/eng/guide/bi.do"/>"><span>KIBS BI</span></a></li>
                            <li>
                                <a href="<c:url value="/eng/guide/venue.do"/>"><span>Venue</span></a>
                                <ul class="dept3">
                                    <li><a href="<c:url value="/eng/guide/venue.do"/>"><span>Venue</span></a></li>
                                    <li><a href="<c:url value="/eng/guide/to_kintex.do"/>"><span>To Kintex</span></a></li>
                                    <%--<li><a href="/eng/guide/to_ara.do"><span>To ARA Marina</span></a></li>--%>
                                </ul>
                            </li>
                            <li><a href="<c:url value="/eng/guide/contact.do"/>"><span>Contact us</span></a></li>
                            <%--<li><a href="/eng/guide/brochure.do"><span>E-Brochure</span></a></li>--%>
                            <li><a href="https://www.kofish.kr/eng/main/main.php" class="outLink" target="_blank"><span>Concurrently event<br>[KOFISH]</span></a></li>
                            <li><a href="https://www.campingfair.co.kr/eng" class="outLink" target="_blank"><span>[Caravan&Camping]</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="<c:url value="/eng/exhibitor/categories.do"/>"><span>Exhibitors</span></a>
                        <ul class="dept2">
                            <li><a href="<c:url value="/eng/exhibitor/categories.do"/>"><span>Exhibitors Categories</span></a></li>
                            <li><a href="<c:url value="/eng/apply/step01.do"/>"><span>Exhibitor Registration</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/glance.do"/>"><span>Korea at a Glance</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/marina.do"/>"><span>Marine Industry in korea</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/match.do"/>"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                            <li><a href="<c:url value="/eng/online/company.do"/>"><span>Exhibitor List</span></a></li>
                            <li><a href="<c:url value="/eng/exhibitor/qna.do"/>"><span>FAQ</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="<c:url value="/eng/buyer/glance.do"/>"><span>Buyers</span></a>
                        <ul class="dept2">
                            <li><a href="https://www.investkorea.org/ik-en/index.do" target="_blank"><span>Visitor guide</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/apply.do"/>"><span>Pre-registration</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/glance.do"/>"><span>Korea at a Glance</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/marina.do"/>"><span>Marine Industry in korea</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/match.do"/>"><span>Exhibitor-Buyer Matchmaking Program</span></a></li>
                            <li><a href="<c:url value="/eng/buyer/qna.do"/>"><span>FAQ</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="<c:url value="/eng/board/notice.do"/>"><span>News</span></a>
                        <ul class="dept2">
                            <li><a href="<c:url value="/eng/board/notice.do"/>"><span>Notice</span></a></li>
                            <li><a href="<c:url value="/eng/board/kibstv.do"/>"><span>KIBS Tube</span></a></li>
                            <li><a href="<c:url value="/eng/board/gallery.do"/>"><span>Gallery</span></a></li>
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