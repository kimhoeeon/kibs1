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
                        <span>홈</span><span>참관객</span><span>참관신청확인</span>
                    </div>
                    <div class="sub_top_tit">참관신청확인</div>
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
                                <a href="/guide/summary_kiss.do">KISS(코리아 서프쇼)</a>
                                <a href="/guide/summary_kmts.do">KMTS(해양관광전)</a>
                                <%--<a href="/guide/maritime_expo.do">해상전시회(시흥 거북섬마리나)</a>--%>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">참관신청확인</div>
                            <div class="tabOptSel">
                                <a href="/visitor/guide_off.do">참관안내</a>
                                <a href="/visitor/apply.do">사전등록</a>
                                <a href="/guide/conference.do">컨퍼런스</a>
                                <a href="/guide/program.do">체험 프로그램</a>
                                <a class="active" href="/visitor/login.do">참관신청확인</a>
                                <a href="/visitor/faq.do">FAQ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->

        <!-- section -->
        <div class="login_s padding_tb">
            <div class="inner">
                <div class="login_top_txt">사전등록 신청 시 등록하셨던 정보를 입력해 주세요.</div>
                <div class="login_box">
                    <div class="login_form">
                        <div class="loginInfo">
                            <p>이름</p>
                            <input type="text" id="name" name="name" placeholder="이름을 입력해 주세요.">
                        </div>
                        <div class="loginInfo">
                            <p>휴대전화</p>
                            <input type="tel" id="phone" name="phone" class="phoneNumber" maxlength="13" placeholder="숫자만 입력해 주세요.">
                        </div>
                        <div class="btnLogin"><a href="javascript:void(0);" onclick="f_pre_apply_check_login()" class="btnSt01">등록 확인하기</a></div>
                    </div>
                </div>
            </div>
        </div>
        <!-- //section -->

    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    <script type="text/javascript">
        // [중복 방지 플래그 변수] 전역 변수로 선언
        var isLoginProcess = false;

        $(document).ready(function() {

            // 1. [연락처 자동 포맷팅] (입력 시 - 자동 삽입 및 숫자만 입력)
            $('.phoneNumber').on('input', function (e) {
                let $this = $(this);
                let val = $this.val();

                // 숫자만 추출
                let number = val.replace(/[^0-9]/g, "");
                let tel = "";

                // 포맷팅 로직
                if (number.length < 4) {
                    tel = number;
                } else if (number.length < 8) {
                    tel = number.substr(0, 3) + "-" + number.substr(3);
                } else {
                    tel = number.substr(0, 3) + "-" + number.substr(3, 4) + "-" + number.substr(7);
                }

                // 길이 제한 (13자리: 010-0000-0000)
                if (tel.length > 13) {
                    tel = tel.substr(0, 13);
                }

                // 값 변경 (커서 튐 방지)
                if ($this.val() !== tel) {
                    $this.val(tel);
                }
            });

            // 2. [엔터키 이벤트 핸들러]
            $("#name, #phone").on("keydown", function(e) {
                if (e.keyCode === 13) {
                    // 이미 로직(팝업/통신)이 진행 중이면 엔터 무시
                    if (isLoginProcess === true) {
                        e.preventDefault();
                        return false;
                    }

                    // 로직 시작: 잠금 설정
                    isLoginProcess = true;
                    f_pre_apply_check_login();
                }
            });
        });
    </script>

</body>
</html>