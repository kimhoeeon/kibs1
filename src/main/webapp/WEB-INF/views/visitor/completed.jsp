<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <c:import url="../header.jsp" charEncoding="UTF-8"/>

    <div id="container">

        <!-- section -->
        <div class="sub_top">
            <div class="inner">
                <div class="sub_top_box">
                    <div class="sub_top_nav">
                        <span>홈</span><span>참관객</span><span>사전등록</span>
                    </div>
                    <div class="sub_top_tit">사전등록</div>
                </div>
            </div>
        </div>
        <!-- //section -->

        <!-- section -->
        <div class="sub_tab">
            <div class="inner">
                <div class="sub_tab_box">
                    <a href="javascript:void(0);" onclick="home('ko');" class="iconHome"><img src="/img/icon_home.png"></a>
                    <div class="optionBox">
                        <div class="tabOpt1 tabOption">
                            <div class="tabOptAct">참관객</div>
                            <div class="tabOptSel">
                                <a href="/guide/summary.do">KIBS 안내</a>
                                <a href="/exhibitor/guide.do">참가기업</a>
                                <a class="active" href="/visitor/guide_off.do">참관객</a>
                                <a href="/guide/notice.do">미디어센터</a>
                                <a href="/online/kibstv.do">온라인 전시관</a>
                                <a href="/guide/maritime_expo.do">해상전시회(시흥 거북섬마리나)</a>
                                <a href="/guide/summary_kiss.do">KISS(코리아 서프쇼)</a>
                                <a href="/guide/summary_kmts.do">KMTS(해양관광전)</a>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">사전등록</div>
                            <div class="tabOptSel">
                                <a href="/visitor/guide_off.do">참관안내</a>
                                <a class="active" href="/visitor/apply.do">사전등록</a>
                                <a href="/visitor/login.do">참관신청확인</a>
                                <a href="/visitor/faq.do">FAQ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->

        <!-- section -->
        <div class="form_s padding_t">
            <div class="inner">

                <!-- 안내문구 -->
                <div class="form_wrap">
                    <div class="form_guide_comp" style="background: #fff;">
                        <div class="form_guide_comp_img"><img src="/img/logo3.png"></div>
                        <div class="form_guide_comp_txt">
                            2026 경기국제보트쇼 사전등록 신청이 완료되었습니다.<br>
                            신청내역은 마이페이지에서 확인하실 수 있습니다.
                        </div>
                        <a href="/visitor/login.do" class="form_guide_comp_btn btnSt01">신청확인하기</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- //section -->

    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    <script type="text/javascript">
        // Meta 전환
        fbq('track', 'CompleteRegistration');

        // Google 전환
        gtag('event', 'conversion', {
            'send_to': 'AW-866426231/bkbKCIjC0OwbEPe6kp0D',
            'value': 1.0,
            'currency': 'KRW'
        });

        // Naver 전환 (Lead 타입)
        // 공통 스크립트(ad_scripts.jsp)에서 wcslog.js 로드 및 계정 ID(s_3ee90871e561) 설정됨
        if(window.wcs){
            var _conv = {};
            _conv.type = 'lead';
            wcs.trans(_conv);
        }
    </script>

</body>
</html>