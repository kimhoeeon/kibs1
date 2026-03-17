<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <jsp:include page="../inc/ad_scripts.jsp" flush="true"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Kibs 2026">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="title" content="경기국제보트쇼">
    <meta property="og:title" content="경기국제보트쇼">
    <meta property="og:image" content="/img/og_img.jpg">
    <meta name="color-scheme" content="light only">
    <meta name="supported-color-schemes" content="light">
    <meta itemprop="name" content="경기국제보트쇼">
    <meta itemprop="url" content="https://kibs.com/">
    <link rel="canonical" id="canonical" href="https://kibs.com/">
    <meta name="description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta name="twitter:description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta property="og:description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta itemprop="description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta property="nate:description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta property="og:keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta name="keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta property="twitter:keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta property="og:keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta name="keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <title>2026 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

</head>

<body>

    <div class="floating">
        <ul class="floating_r">
            <%--<li>
                <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : +82 031-995-8946/8912');">
                    <div class="icon"><img src="/img/icon_floating25_reg.png"></div>
                    <div class="txt">
                        <span>Exhibitor</span><br>
                        Application
                    </div>
                </a>
            </li>
            <li>
                <a href="/eng/buyer/apply.do">
                    <div class="icon"><img src="/img/icon_floating25_visitor.png"></div>
                    <div class="txt">
                        <span>Pre-register</span><br>
                        for Free
                    </div>
                </a>
            </li>--%>
            <li>
                <a href="${brochureData.mainEnPath}" download="${brochureData.mainEnTitle}">
                    <div class="icon"><img src="/img/icon_floating25_bro.png"></div>
                    <div class="txt">
                        Brochure<br>
                        Download
                    </div>
                </a>
            </li>
        </ul>
    </div>

    <c:import url="header.jsp" charEncoding="UTF-8"/>

    <div id="container">

        <!-- section -->
        <div class="main_top">
            <div class="bg">
                <div class="wave"></div>
                <!-- slide -->
                <div class="swiper_box slide">
                    <div class="swiper swiperMainTop">
                        <ul class="swiper-wrapper">
                            <li class="swiper-slide"><img src="/img/main_top_slide25_01.jpg"></li>
                            <li class="swiper-slide"><img src="/img/main_top_slide25_02.jpg"></li>
                        </ul>
                    </div>
                </div>
                <!-- //slide -->
            </div>
            <div class="inner">
                <div class="info_box">
                    <div class="tit"><img src="/img/main_top_tit29_eng.png?ver=20260205"></div>
                    <%--<ul class="proc">
                        <li>
                            <div class="txt">
                                <div class="gubun">1st Apply by</div>
                                <div class="date">~24.11.15(Fri)</div>
                            </div>
                        </li>
                        <li>
                            <div class="txt">
                                <div class="gubun">2nd Apply by</div>
                                <div class="date">~24.12.13(Fri)</div>
                            </div>
                        </li>
                        <li>
                            <div class="txt">
                                <div class="gubun">Payment for<br>Additional facilities</div>
                                <div class="date">~25.01.17(Fri)</div>
                            </div>
                        </li>
                    </ul>--%>
                </div>

                <div class="video_box">
                    <div class="embed-container">
                        <iframe src="https://www.youtube.com/embed/uMN97io8RFA?autoplay=1&mute=1&controls=0&loop=1&playlist=uMN97io8RFA" frameborder="0" allowfullscreen></iframe>
                    </div>
                </div>

            </div>
        </div>
        <!-- //section -->

        <!-- section -->
        <div class="main_data">
            <div class="inner">
                <div class="main_tit">
                    <div class="big">Previous Boat Show Data</div>
                </div>
                <ul class="list">
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_05.png"></div>
                        <div class="text">
                            <div class="data">26,487㎡</div>
                            <div class="nae">Exhibition Area</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_02.png"></div>
                        <div class="text">
                            <div class="data">1,313+</div>
                            <div class="nae">Number of Participating Booths</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_03.png"></div>
                        <div class="text">
                            <div class="data">62,000+</div>
                            <div class="nae">Number of Visitors</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_04.png"></div>
                        <div class="text">
                            <div class="data">150+</div>
                            <div class="nae">Number of Exhibition Boats</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- //section -->

        <!-- section sns -->
        <div class="main_bg_sns">
            <!-- section -->
            <div class="main_board board_recent">
                <div class="inner">
                    <div class="main_tit">
                        <div class="big">NOTICE</div>
                    </div>
                    <div class="sec_box">
                        <div class="board_rct_list notice eng">
                            <div class="board_rct_tit">
                                <div class="tit">Notice</div>
                                <a href="/eng/board/notice.do" class="more">more</a>
                            </div>
                            <ul>
                                <c:forEach var="mainNotice" items="${mainNoticeList}" begin="0" end="${mainNoticeList.size()}" step="1">
                                    <li>
                                        <a href="/eng/board/noticeView.do?id=${mainNotice.id}">
                                            <div class="title">${mainNotice.title}</div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!-- //section -->

            <!-- section -->
            <div class="main_tv">
                <div class="inner">
                    <div class="main_tit">
                        <div class="big">KIBS YOUTUBE</div>
                    </div>
                    <div class="tvList">
                        <ul>
                            <c:forEach var="mainKibstv" items="${mainKibstvList}" begin="0" end="${mainKibstvList.size()}" step="1">
                                <li class="viewKibsTv">
                                    <a>
                                        <div class="thumb169 thumbBox">
                                            <img src="https://img.youtube.com/vi/${mainKibstv.youtubeUrl}/mqdefault.jpg" class="thumbImg">
                                        </div>
                                        <div class="title">${mainKibstv.title}</div>
                                    </a>
                                </li>
                            </c:forEach>
                            <%--<li class="swiper-slide viewKibsTv">
                                <a>
                                    <div class="thumb169 thumbBox">
                                        <img src="/img/thumb_tv_01.jpg" class="thumbImg">
                                    </div>
                                    <div class="title">해양레저 관광상품 개발 전문 "세운여행사" [마린리뷰쇼]</div>
                                </a>
                            </li>--%>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- //section -->

            <!-- section -->
            <%--<div class="main_insta">
                <div class="inner">
                    <div class="main_tit">
                        <div class="big">INSTAGRAM</div>
                    </div>
                    <div class="sec_box">
                        <div id="instagram"></div>
                        <script src="/js/instagramapi.js"></script>
                    </div>
                </div>
            </div>--%>
            <!-- //section -->
        </div>
        <!-- //section sns -->

    </div>

    <div class="popKibsTv popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit"></div>
                    <a class="close" href="#close"><img src="/img/close_w.png"></a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <!-- 비디오 -->
                        <div class="embed-container">
                            <iframe src="" frameborder="0" allowfullscreen></iframe>
                        </div>
                        <!-- //비디오 -->
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:import url="footer.jsp" charEncoding="UTF-8"/>

    <script>
        $(function(){
            let popup_param = {};
            <c:forEach var="popup" items="${popupList}" begin="0" end="${popupList.size()}" step="1">
                popup_param = {
                    id: '${popup.id}',
                    lang: '${popup.lang}',
                    widthPixel: '${popup.widthPixel}',
                    leftPixel: '${popup.leftPixel}',
                    topPixel: '${popup.topPixel}',
                    align: '${popup.align}'
                }
                f_show_main_popup(popup_param);
            </c:forEach>
        });
    </script>

</body>
</html>