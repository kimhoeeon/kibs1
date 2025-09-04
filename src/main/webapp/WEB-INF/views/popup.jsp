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
    </span>    <%-- Naver SEO --%>
    <meta name="naver-site-verification" content="adc5540381e65e9f24333fe41cde2f8b36ae9690" />

    <link href="/css/reset.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/font.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/style.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/responsive.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">

    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/jquery-migrate-3.3.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
    <script src="/js/jquery.cookie.min.js"></script>
    <script src="/js/jquery.ui.touch-punch.min.js"></script>
    <script src="/js/main.js?ver=<%=System.currentTimeMillis()%>"></script>

    <style>
        /* 기본 리셋 및 전체 설정 */
        html, body {
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        * {
            box-sizing: border-box;
        }

        /* Flexbox 레이아웃 설정 */
        .mainLayerPopup {
            display: flex;
            flex-direction: column;
            width: auto;
            min-height: 100px;
            height: auto; /* 내용에 맞게 높이 자동 조절 */
            background: #fff;
            z-index: 9999;
        }

        /* 콘텐츠 영역 (중복 제거 및 통합) */
        .mainLayerPopup .popupBox {
            flex: 1; /* 남는 공간 모두 차지 */
            text-align: center;
            min-height: 150px;
        }

        /* 콘텐츠 내부 이미지 */
        .mainLayerPopup .popupBox img {
            display: block;
            margin: 0;
        }

        /* 하단 닫기 영역 */
        .mainLayerPopup .popupClose {
            text-align: right;
            background: #111;
            padding: 10px;
        }

        .mainLayerPopup .popupClose input {
            vertical-align: middle;
        }

        .mainLayerPopup .popupClose label {
            color: #fff;
            font-size: 14px;
            vertical-align: middle;
            margin-left: 3px;
        }

        .mainLayerPopup .popupClose input[type="checkbox"] {
            border: 1px solid #ccc;
            background: #fff;
            width: 20px;
            height: 20px;
            margin: 0;
            vertical-align: middle;
            border-radius: 0;
        }
    </style>
</head>

<body>

    <div class="mainLayerPopup" id="id_popup_${info.id}">
        <div class="popupBox ql-editor" style="padding:0;">
            <c:if test="${info.linkUrl eq null or info.linkUrl eq ''}">
                ${fn:replace(fn:replace(info.content,'&lt;','<'),'&gt;','>')}
            </c:if>
            <c:if test="${info.linkUrl ne null and info.linkUrl ne ''}">
                <a href="${info.linkUrl}" target="_blank">
                    ${fn:replace(fn:replace(info.content,'&lt;','<'),'&gt;','>')}
                </a>
            </c:if>
        </div>
        <div class="popupClose">
            <input type="checkbox" id="id_today_${info.id}" onclick="closeMainPopup('${info.id}')">
            <label for="id_today_${info.id}"> 오늘 하루 그만보기 </label>
        </div>
    </div>

    <script>
        $(window).on('load', function() {

            // 1. 실제 콘텐츠의 높이를 측정합니다.
            const contentHeight = $('.mainLayerPopup').outerHeight();
            const contentWidth = $('.mainLayerPopup').outerWidth();

            // 2. (핵심) 현재 창의 내부 높이가 콘텐츠 높이보다 크거나 같다면,
            //    이미 크기가 충분하므로 스크립트를 즉시 종료합니다.
            if (window.innerHeight >= contentHeight) {
                return;
            }

            // 3. 창이 콘텐츠보다 작을 때만 아래 리사이징 로직을 실행합니다.
            const browserChromeHeight = window.outerHeight - window.innerHeight;
            const browserChromeWidth = window.outerWidth - window.innerWidth;

            const finalHeight = contentHeight + browserChromeHeight + 7;
            const finalWidth = contentWidth + browserChromeWidth + 5;

            window.resizeTo(finalWidth, finalHeight);
        });
    </script>
</body>
</html>