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
    <title>2026 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <span itemscope="" itemtype="http://schema.org/Organization">
        <link itemprop="url" href="https://kibs.com/">
        <a itemprop="sameAs" href="https://koreaboatshow.or.kr/"></a>
        <a itemprop="sameAs" href="https://koreaboatshow.re.kr/"></a>
        <a itemprop="sameAs" href="https://kibs-online.com"></a>
        <a itemprop="sameAs" href="https://www.youtube.com/channel/UCvcRu_g4M1MOIIuJyllR6Rw"></a>
        <a itemprop="sameAs" href="https://www.youtube.com/@KIBSKINTEX"></a>
    </span>

</head>

<body>

    <div class="floating">
        <ul class="floating_r">
            <%--<li>
                <a href="/apply/step01.do">
                    <div class="icon"><img src="/img/icon_floating25_reg.png"></div>
                    <div class="txt">
                        <span>참가업체</span><br>
                        신청하기
                    </div>
                </a>
            </li>
            <li>
                <a href="/visitor/apply.do">
                    <div class="icon"><img src="/img/icon_floating25_visitor.png"></div>
                    <div class="txt">
                        <span>참관객</span><br>
                        사전등록
                    </div>
                </a>
            </li>--%>
            <li>
                <a href="${brochureData.mainKoPath}" download="${brochureData.mainKoTitle}">
                    <div class="icon"><img src="/img/icon_floating25_bro.png"></div>
                    <div class="txt">
                        브로슈어<br>
                        다운로드
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
                    <div class="tit"><img src="/img/main_top_tit26_kor.png"></div>
                    <%--<ul class="proc">
                        <li>
                            <div class="txt">
                                <div class="gubun">1차 조기 신청 할인</div>
                                <div class="date">~24.11.15(금)까지(종료)</div>
                            </div>
                        </li>
                        <li>
                            <div class="txt">
                                <div class="gubun">2차 조기 신청 할인</div>
                                <div class="date">~24.12.13(금)까지</div>
                            </div>
                        </li>
                        <li style="width: 100%;">
                            <div class="txt">
                                <div class="gubun">부대시설비 납부</div>
                                <div class="date">~25.01.24(금)까지</div>
                            </div>
                        </li>
                    </ul>--%>
                </div>

                <div class="video_box">
                    <div class="embed-container">
                        <iframe src="https://www.youtube.com/embed/CNKSitiuNN8?autoplay=1&mute=1&controls=0&loop=1&playlist=CNKSitiuNN8" frameborder="0" allowfullscreen></iframe>
                    </div>
                </div>

            </div>
        </div>
        <!-- //section -->

        <!-- section -->
        <div class="main_data">
            <div class="inner">
                <div class="main_tit">
                    <div class="top">Previous Boat Show Data</div>
                    <div class="big">한눈에 보는 2025 KIBS</div>
                </div>
                <ul class="list">
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_05.png"></div>
                        <div class="text">
                            <div class="data">26,487㎡</div>
                            <div class="nae">전시면적</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_02.png"></div>
                        <div class="text">
                            <div class="data">1,454+</div>
                            <div class="nae">부스규모</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_03.png"></div>
                        <div class="text">
                            <div class="data">65,000+</div>
                            <div class="nae">참관객 수</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_04.png"></div>
                        <div class="text">
                            <div class="data">180+</div>
                            <div class="nae">전시보트</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- //section -->

        <!-- section -->
        <div class="main_exh">
            <div class="inner">
                <div class="main_tit">
                    <div class="top">EXHIBITORS</div>
                    <div class="big">참가업체 미리보기</div>
                </div>
                <div class="search_box">
                    <div class="select">
                        <select id="condition">
                            <option value="PRODUCT">제품명</option>
                            <option value="COMPANY">참가기업</option>
                        </select>
                    </div>
                    <div class="input">
                        <input type="text" id="searchText" onkeyup="f_main_exhibitor_preview_search(event)" placeholder="내용을 입력해 주세요.">
                    </div>
                </div>
                <div class="swiper_box">
                    <div class="swiper swiper_exh">
                        <ul class="swiper-wrapper exhList">
                            <c:forEach var="mainOnline" items="${mainOnlineList}" begin="0" end="${mainOnlineList.size()}" step="1" varStatus="status">
                                <c:choose>
                                    <c:when test="${fn:contains(mainOnline.companyBadge, '올해의 제품상 수상')}">
                                        <c:if test="${mainOnline.transferYear eq '2025'}">
                                            <li class="swiper-slide badgeAward badgePrdYear year25">
                                        </c:if>
                                        <c:if test="${mainOnline.transferYear ne '2025'}">
                                            <li class="swiper-slide badgeAward badgePrdYear">
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="swiper-slide">
                                    </c:otherwise>
                                </c:choose>
                                <%--<div class="btnLike">
                                    <img src="/img/icon_like.png" class="likeImg">
                                </div>--%>
                                <a href="/online/company.do">
                                    <div class="thumbnail">
                                        <div class="thumb41 thumbBox">
                                            <c:choose>
                                                <c:when test="${mainOnline.promotionImageFilePath ne null and mainOnline.promotionImageFilePath ne '' and not fn:contains(mainOnline.promotionImageFilePath, '.ai')}">
                                                    <c:set var="promotionImageFilePathSrc" value="${fn:replace(mainOnline.promotionImageFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                    <img src="${promotionImageFilePathSrc}" class="thumbImg">
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- 랜덤 숫자 설정 -->
                                                    <%
                                                        int min = 1;
                                                        int max = 6;
                                                        java.util.Random random = new java.util.Random();
                                                        int value = random.nextInt(max) + min;
                                                    %>
                                                    <img src="/img/thumb_exh_img_0<%=value%>.jpg" class="thumbImg">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="thumbLogo">
                                            <p>
                                                <c:choose>
                                                    <c:when test="${mainOnline.logoFilePath ne null and mainOnline.logoFilePath ne '' and not fn:contains(mainOnline.logoFilePath, '.ai')}">
                                                        <c:set var="companyLicenseFileSrc" value="${fn:replace(mainOnline.logoFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                        <img src="${companyLicenseFileSrc}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="/img/thumb_exh_logo_sample.png">
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="name">${mainOnline.companyNameKo}</div>
                                    <div class="badge">
                                        <c:if test="${not empty mainOnline.companyBadge}">
                                            <c:if test="${fn:contains(mainOnline.companyBadge, '.ai')}">
                                                <img src="/img/exh_badge1.png">
                                            </c:if>
                                            <c:if test="${fn:contains(mainOnline.companyBadge, '보트쇼 3회 이상 참가')}">
                                                <img src="/img/exh_badge2.png">
                                            </c:if>
                                            <c:if test="${fn:contains(mainOnline.companyBadge, '보트쇼와 제작한 영상')}">
                                                <img src="/img/exh_badge3.png">
                                            </c:if>
                                            <c:if test="${fn:contains(mainOnline.companyBadge, '올해의 제품상 수상')}">
                                                <img src="/img/exh_badge4.png">
                                            </c:if>
                                            <c:if test="${fn:contains(mainOnline.companyBadge, '제품 등록 우수')}">
                                                <img src="/img/exh_badge5.png">
                                            </c:if>
                                        </c:if>
                                    </div>
                                    <button type="button" onclick="window.location.href='/online/company.do';" class="btnView">${mainOnline.transferYear} 온라인전시관 바로가기</button>
                                </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="swiper-button-prev swiper_exh_prev"></div>
                    <div class="swiper-button-next swiper_exh_next"></div>
                </div>
            </div>
        </div>
        <!-- //section -->

        <!-- section sns -->
        <div class="main_bg_sns">
            <!-- section -->
            <div class="main_board board_recent">
                <div class="inner">
                    <div class="main_tit">
                        <div class="top">NOTICE & COLUMN/PRESS</div>
                        <div class="big">공지사항&칼럼/기사</div>
                    </div>
                    <div class="sec_box">
                        <div class="board_rct_list notice">
                            <div class="board_rct_tit">
                                <div class="tit">공지사항</div>
                                <a href="/guide/notice.do" class="more">MORE</a>
                            </div>
                            <ul>
                                <c:forEach var="mainNotice" items="${mainNoticeList}" begin="0" end="${mainNoticeList.size()}" step="1">
                                    <li>
                                        <a href="/guide/noticeView.do?id=${mainNotice.id}">
                                            <div class="title">${mainNotice.title}</div>
                                        </a>
                                    </li>
                                </c:forEach>
                                <%--<li>
                                    <a href="">
                                        <div class="gubun">공지사항</div>
                                        <div class="title">'2023 네덜란드 보트부품전(METS) 한국관' 참가업체 모집 공고</div>
                                        <div class="date">2024.01.01</div>
                                    </a>
                                </li>--%>
                            </ul>
                        </div>
                        <div class="board_rct_list column">
                            <div class="board_rct_tit">
                                <div class="tit">칼럼/기사</div>
                                <a href="/board/column.do" class="more">MORE</a>
                            </div>
                            <ul>
                                <c:forEach var="mainColumn" items="${mainColumnList}" begin="0" end="${mainColumnList.size()}" step="1">
                                    <li>
                                        <a href="/board/columnView.do?id=${mainColumn.id}">
                                            <div class="title">${mainColumn.title}</div>
                                        </a>
                                    </li>
                                </c:forEach>
                                <%--<li>
                                    <a href="">
                                        <div class="gubun">칼럼</div>
                                        <div class="title">[현대해양] 해양레저 바로알기➆ 너무나도 중요한 해양레저산업 발전 기본법 제정</div>
                                        <div class="date">2024.01.01</div>
                                    </a>
                                </li>--%>
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
                        <div class="top">MEDIA</div>
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
                        <div class="top">INSTAGRAM</div>
                        <div class="big">인스타그램</div>
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

    <!-- 전환페이지 설정 -->
    <script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
    <script type="text/javascript">
        var _nasa={};
        if(window.wcs) _nasa["cnv"] = wcs.cnv("4","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
    </script>

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