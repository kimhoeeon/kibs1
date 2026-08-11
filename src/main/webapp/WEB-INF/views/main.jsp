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
    <meta name="author" content="Kibs 2027">
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
    <title>2027 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <jsp:include page="inc/common_css.jsp" flush="true"/>
    <jsp:include page="inc/common_js_ko.jsp" flush="true"/>
    <jsp:include page="inc/ad_scripts.jsp" flush="true"/>

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
                    <div class="tit"><img src="/img/main_top_tit32_kor.png"></div>
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
                        <iframe src="https://www.youtube.com/embed/oZb5vu3fyC0?autoplay=1&mute=1&controls=0&loop=1&playlist=oZb5vu3fyC0" frameborder="0" allowfullscreen></iframe>
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
                    <div class="big">한눈에 보는 2026 KIBS</div>
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
                            <div class="data">1,313+</div>
                            <div class="nae">부스규모</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_03.png"></div>
                        <div class="text">
                            <div class="data">62,000+</div>
                            <div class="nae">참관객 수</div>
                        </div>
                    </li>
                    <li>
                        <div class="icon"><img src="/img/icon_main_data_04.png"></div>
                        <div class="text">
                            <div class="data">150+</div>
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
            <div class="main_ai">
                <div class="inner">
                    <div class="main_tit">
                        <div class="top">AI News Clipping</div>
                        <div class="big">AI 뉴스 클리핑</div>
                    </div>
                    <div class="ai_clipping_box" id="mainAiContainer" style="display: none;">
                        <div class="ai_top">
                            <div class="tit" id="mainAiTitle"></div>
                            <!-- 공유하기 클릭 시 카운트 증가 및 클립보드 복사 -->
                            <button type="button" class="more" id="btnShareAi">공유하기</button>
                        </div>
                        <div class="nae" id="mainAiContent"></div>
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
            <div class="main_newsletter">
                <div class="inner">
                    <div class="main_tit">
                        <div class="top">Newsletter</div>
                        <div class="big">뉴스레터 구독하기</div>
                        <div class="nae">경기국제보트쇼의 소식을 전달합니다.<br/>
                            다양한 컨퍼런스와 놓쳐서는 안될 유익한 트렌드를 큐레이팅하고,<br class="pc_only">
                            새로운 관점으로 이슈를 전합니다.
                        </div>
                    </div>
                    <div class="news_letter_box">
                        <div class="form_wrap">
                            <ul class="form_box">
                                <li>
                                    <div class="item"><p>이메일</p></div>
                                    <div class="input">
                                        <input type="text" id="nlEmail" placeholder="이메일을 입력해주세요">
                                    </div>
                                </li>
                                <li>
                                    <div class="item"><p>이름</p></div>
                                    <div class="input">
                                        <input type="text" id="nlName" placeholder="이름을 입력해주세요">
                                    </div>
                                </li>
                                <li>
                                    <div class="input check">
                                        <label><input type="checkbox" id="nlPrivacy">(필수) <span class="pri_btn">개인정보 수집 및 이용</span>에 동의합니다.</label>
                                        <label><input type="checkbox" id="nlAd">(필수) <span class="adv_btn">광고성 정보 수신</span>에 동의합니다.</label>
                                    </div>
                                    <div class="comnt">
                                        * 뉴스레터 수신 거부를 원하시는 분께서는 수신하신 뉴스레터 하단의 “수신거부”를 클릭하시면 수신거부 처리가 완료됩니다.
                                    </div>
                                </li>
                            </ul>
                            <div class="subscribe_btn">
                                <button type="button" onclick="subscribeNewsletter()">뉴스레터 구독하기</button>
                            </div>
                        </div>
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

    <div class="popPrivacy popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">[개인정보 수집 이용 동의] 약관</div>
                    <a class="close" href="#close"><img src="../../img/close_w.png"></a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <b>[개인정보 수집 및 이용 동의]</b> <br />
                        경기국제보트쇼 사무국은 뉴스레터 발송을 위해 아래와 같이 개인정보를 수집·이용합니다.<br /><br />
                        1. 수집 항목<br />
                        이름<br />
                        이메일 주소<br /><br />

                        2. 수집 및 이용 목적<br />
                        경기국제보트쇼 뉴스레터 발송<br />
                        행사 소식, 공지사항, 참가/참관 안내 제공<br />
                        전시회 관련 산업 뉴스 및 콘텐츠 제공<br />
                        뉴스레터 신청자 관리 및 문의 응대<br /><br />

                        3. 보유 및 이용 기간 수집된 개인정보는 뉴스레터 수신 동의 철회 시까지 보유·이용하며, 수신자가 구독 해지를 요청하거나 개인정보 삭제를 요청하는 경우 지체 없이 파기합니다.<br />
                        단, 관계 법령에 따라 보관이 필요한 경우 해당 법령에서 정한 기간 동안 보관할 수 있습니다.<br /><br />
                        4. 동의 거부 권리 및 불이익 이용자는 개인정보 수집 및 이용에 대한 동의를 거부할 권리가 있습니다.<br />
                        다만, 동의를 거부할 경우 뉴스레터 신청 및 수신이 제한될 수 있습니다.<br /><br />

                        위와 같이 개인정보를 수집·이용하는 데 동의합니다.

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="popAdver popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">[광고성 정보 수신 동의]</div>
                    <a class="close" href="#close"><img src="../../img/close_w.png"></a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        경기국제보트쇼 사무국은 뉴스레터를 통해 경기국제보트쇼 및 관련 행사, 전시, 산업 정보, 홍보 콘텐츠 등을 안내하기 위해 아래와 같이 광고성 정보를 발송할 수 있습니다.<br /><br />

                        1. 수신 항목<br />
                        경기국제보트쇼 행사 안내<br />
                        참가업체 및 참관객 대상 주요 공지<br />
                        전시회 참가/참관 안내<br />
                        관련 산업 뉴스, 콘텐츠, 이벤트 및 홍보 정보<br />
                        사무국에서 제공하는 마케팅성 안내<br /><br />

                        2. 수신 방법<br />
                        이메일<br /><br />

                        3. 보유 및 이용 기간 광고성 정보 수신 동의 철회 시까지 보유·이용합니다. 수신자는 뉴스레터 하단의 수신 거부 또는 사무국 문의를 통해 언제든지 수신 동의를 철회할 수 있습니다.<br /><br />

                        4. 동의 거부 권리 및 불이익 이용자는 광고성 정보 수신에 대한 동의를 거부할 권리가 있습니다. 다만, 동의를 거부할 경우 행사 홍보, 이벤트, 산업 뉴스 등 마케팅성 정보 수신이 제한될 수 있습니다.<br /><br />

                        위와 같이 광고성 정보를 수신하는 데 동의합니다.

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

            // 1. 메인 페이지 최신 AI 클리핑 불러오기
            $.ajax({
                url: '/api/clipping/latest',
                type: 'GET',
                success: function(res) {
                    if(res.resultCode === "0" && res.data) {
                        let aiData = res.data;
                        $('#mainAiTitle').text(aiData.title);
                        // HTML 태그가 이미 포함되어 있으므로 html()로 삽입
                        $('#mainAiContent').html(aiData.content);
                        $('#btnShareAi').data('seq', aiData.seq);
                        $('#mainAiContainer').show();
                    }
                }
            });

            // 2. AI 클리핑 공유하기 액션
            $('#btnShareAi').on('click', function() {
                let seq = $(this).data('seq');
                if(!seq) return;

                // 공유 횟수 증가 API
                $.post('/api/clipping/share/' + seq, function() {
                    // 현재 사이트 URL 복사 로직 등 추가 가능
                    let dummy = document.createElement("input");
                    document.body.appendChild(dummy);
                    dummy.value = window.location.origin + "/board/clipping.do";
                    dummy.select();
                    document.execCommand("copy");
                    document.body.removeChild(dummy);

                    alert("게시판 URL이 클립보드에 복사되었습니다.");
                });
            });
        });

        // 3. 뉴스레터 구독 기능
        function subscribeNewsletter() {
            let email = $('#nlEmail').val().trim();
            let name = $('#nlName').val().trim();
            let agreePrivacy = $('#nlPrivacy').is(':checked') ? 'Y' : 'N';
            let agreeAd = $('#nlAd').is(':checked') ? 'Y' : 'N';

            if (!email || !name) {
                alert("이름과 이메일을 모두 입력해주세요.");
                return;
            }

            if (agreePrivacy === 'N' || agreeAd === 'N') {
                alert("필수 약관에 모두 동의해 주셔야 구독이 가능합니다.");
                return;
            }

            let payload = {
                email: email,
                name: name,
                privacyAgreeYn: agreePrivacy,
                adAgreeYn: agreeAd
            };

            $.ajax({
                url: '/api/newsletter/subscribe',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(payload),
                success: function(res) {
                    if (res.resultCode === "0") {
                        alert("뉴스레터 구독 신청이 완료되었습니다.");
                        $('#nlEmail').val('');
                        $('#nlName').val('');
                        $('#nlPrivacy').prop('checked', false);
                        $('#nlAd').prop('checked', false);
                    } else {
                        alert(res.resultMsg || "오류가 발생했습니다.");
                    }
                },
                error: function(xhr) {
                    let err = xhr.responseJSON;
                    alert(err && err.resultMsg ? err.resultMsg : "구독 처리 중 서버 오류가 발생했습니다.");
                }
            });
        }
    </script>

</body>
</html>