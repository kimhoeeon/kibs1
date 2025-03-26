<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Kibs 2025">
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
    <title>2025 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <style>
        .mainLayerPopup { display:none; position:fixed; width:auto; min-height:100px; top:74px; left:0px; padding:0px;  border:3px solid #242527; background:#fff; overflow:hidden; z-index:9999; }
        .mainLayerPopup .popupBox { text-align: center; padding:35px 0 0; min-height:150px; height:100%}
        .mainLayerPopup .popupBox img { display: block; margin: 0 0;}
        .mainLayerPopup .popupClose {text-align:right; background:#111; padding:10px;}
        .mainLayerPopup .popupClose input {vertical-align:middle;}
        .mainLayerPopup .popupClose label {color:#fff; font-size:14px; vertical-align:middle;  margin-left:3px;}
        .mainLayerPopup .popupClose a{color:#fff; width:15px; display:inline-block; vertical-align:middle; margin-left:10px; }
        .mainLayerPopup .popupClose input[type="checkbox"] {border:1px solid #ccc; background:#fff; width: 20px; height: 20px; margin-right:5px; margin-bottom:5px; vertical-align: middle; border-radius: 0;}
    </style>
</head>

<body>

    <div class="floating">
        <ul class="floating_r">
            <%--<li>
                <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');">
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
                <a href="/file/2025_KIBS_Brochure_ENG.pdf" download>
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
                    <div class="tit"><img src="/img/main_top_tit25_eng.png"></div>
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
                        <iframe src="https://www.youtube.com/embed/R-ZKqYY2zuA?autoplay=1&mute=1&controls=0&loop=1&playlist=R-ZKqYY2zuA" frameborder="0" allowfullscreen></iframe>
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
                            <div class="data">1,454+</div>
                            <div class="nae">Number of Participating Booths</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_03.png"></div>
                        <div class="text">
                            <div class="data">65,000+</div>
                            <div class="nae">Number of Visitors</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_04.png"></div>
                        <div class="text">
                            <div class="data">180+</div>
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

    <%-- 뒷배경 적용 시 주석 해제 <div id="mainLayerPopup" style="display: block;"></div>--%>
    <c:set var="imgLeftPosition" value="0"/>
    <c:forEach var="popup" items="${popupList}" begin="0" end="${popupList.size()}" step="1" varStatus="status">
        <c:if test="${status.index eq 0}">
            <c:set var="imgLeftPosition" value="${popup.leftPixel}"/>
        </c:if>
        <c:if test="${status.index > 0}">
            <c:set var="imgLeftPosition" value="${imgLeftPosition + popup.leftPixel + popupList.get(status.index-1).widthPixel}"/>
        </c:if>
        <div class="mainLayerPopup" id="id_popup_${popup.id}"
             style="position:fixed; top:${popup.topPixel}px; left:${imgLeftPosition}px; width:${popup.widthPixel}px; display: none;">
            <div class="popupBox ql-editor" style="padding:0;">
                <c:if test="${popup.linkUrl eq null or popup.linkUrl eq ''}">
                    ${fn:replace(fn:replace(popup.content,'&lt;','<'),'&gt;','>')}
                </c:if>
                <c:if test="${popup.linkUrl ne null and popup.linkUrl ne ''}">
                    <a href="${popup.linkUrl}" target="_blank">
                            ${fn:replace(fn:replace(popup.content,'&lt;','<'),'&gt;','>')}
                    </a>
                </c:if>
            </div>
            <div class="popupClose">
                <input type="checkbox" id="id_today_${popup.id}" onclick="CloseMainPopup('${popup.id}')">
                <label for="id_today_${popup.id}"> Not seeing you today </label>
                <a href="javascript:CloseMainPopup('${popup.id}');" style="margin-left:20px">
                    <img src="/img/close_w.png">
                </a>
            </div>
        </div>
    </c:forEach>

    <!-- 전환페이지 설정 -->
    <script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
    <script type="text/javascript">
        var _nasa={};
        if(window.wcs) _nasa["cnv"] = wcs.cnv("4","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
    </script>

    <c:import url="footer.jsp" charEncoding="UTF-8"/>

    <script>

        let cookie_first_name = 'popup_';

        function ShowMainPopup(pop_id) {
            let id_popup_name	= "id_popup_" + pop_id;
            let cookie_name		= cookie_first_name + pop_id;

            if( getStorage(cookie_name)){
                return;
            }
            let popupEl = $('#'+id_popup_name);
            let popupSize = popupEl.css('width').replace('px','');
            let popupLeft = parseInt('${imgLeftPosition}');

            let windowWidth = window.innerWidth;
            if(windowWidth >= 900 && windowWidth <= 1024){
                popupEl.css('width', (popupSize * 0.9) + 'px');
                popupEl.css('left', (popupLeft * 0.9) + 'px');
            }else if(windowWidth >= 769 && windowWidth <= 899) {
                popupEl.css('width', (popupSize * 0.7) + 'px');
                popupEl.css('left', (popupLeft * 0.7) + 'px');
            }else if(windowWidth >= 481 && windowWidth <= 768) {
                popupEl.css('width', (popupSize * 0.6) + 'px');
                popupEl.css('left', (popupLeft * 0.1) + 'px');
            }else if(windowWidth <= 480){
                popupEl.css('width', (popupSize * 0.5) + 'px');
                popupEl.css('left', '10px');
            }

            popupEl.show();
        }

        function CloseMainPopup(pop_id) {
            let id_popup_name	= "id_popup_" + pop_id;
            let id_today_name	= 'id_today_' + pop_id;
            let cookie_name		= cookie_first_name + pop_id;

            if( $(":input:checkbox[id='" + id_today_name + "']:checked").size() > 0 ){
                setStorage(cookie_name, 1);
            }

            $('#'+id_popup_name).hide();
        }

        function setStorage(name, exp){
            // 만료 시간 구하기(exp를 ms단위로 변경)
            let date = new Date();
            date = date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);

            // 로칼 스토리지에 저장하기
            // (값을 따로 저장하지 않고 만료 시간을 저장)
            localStorage.setItem(name, date);
        }

        function getStorage(name){
            let now = new Date();
            now = now.setTime(now.getTime());
            // 현재 시각과 스토리지에 저장된 시각을 각각 비교하여
            // 시간이 남아 있으면 true, 아니면 false 리턴
            return parseInt(localStorage.getItem(name)) > now
        }

        $(function(){
            <c:forEach var="popup" items="${popupList}" begin="0" end="${popupList.size()}" step="1">
            ShowMainPopup('${popup.id}');
            </c:forEach>
        });
    </script>

</body>
</html>