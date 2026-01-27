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
    <c:import url="../header.jsp" charEncoding="UTF-8"/>

    <div id="container">

        <!-- section -->
        <div class="sub_top">
            <div class="inner">
                <div class="sub_top_box">
                    <div class="sub_top_nav">
                        <span>홈</span><span>참가기업</span><span>참가기업 신청</span>
                    </div>
                    <div class="sub_top_tit">참가기업 신청</div>
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
                            <div class="tabOptAct">참가기업</div>
                            <div class="tabOptSel">
                                <a href="/guide/summary.do">KIBS 안내</a>
                                <a class="active" href="/exhibitor/guide.do">참가기업</a>
                                <a href="/visitor/guide_off.do">참관객</a>
                                <a href="/guide/notice.do">미디어센터</a>
                                <a href="/online/kibstv.do">온라인 전시관</a>
                                <a href="/guide/maritime_expo.do">해상전시회(시흥 거북섬마리나)</a>
                                <a href="/guide/summary_kiss.do">KISS(코리아 서프쇼)</a>
                                <a href="/guide/summary_kmts.do">KMTS(해양관광전)</a>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">참가기업 신청</div>
                            <div class="tabOptSel">
                                <a href="/exhibitor/guide.do">참가기업 안내</a>
                                <a class="active" href="/apply/step01.do">참가기업 신청</a>
                                <a href="/exhibitor/login.do">참가신청확인</a>
                                <a href="/exhibitor/faq.do">FAQ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->


        <!-- section -->
        <div class="apply_s padding_tb" id="apply_s">
            <div class="inner">

                <div class="apply_nav">
                    <div class="apply_nav_list">
                        <ul class="list1">
                            <li><a>기본정보</a></li>
                            <li class="active">
                                <a>전시 신청 정보</a>
                                <ul class="list2">
                                    <li><a>전시부스 신청</a></li>
                                    <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/apply/step2_10.do','${info.seq}')">해상전시회 신청</a></li>
                                    <li><a>요트/보트 출품 정보</a></li>
                                    <li><a>상호간판 신청</a></li>
                                    <li><a>유틸리티 신청</a></li>
                                    <li><a>출입증 신청</a></li>
                                    <li><a>경품제공 신청</a></li>
                                    <li><a>디렉토리 정보</a></li>
                                </ul>
                            </li>
                            <li><a>전시참가 신청 완료</a></li>
                        </ul>
                    </div>
                </div>

                <div class="apply_step">
                    <div class="apply_tit">해상전시회 신청</div>
                    <div class="apply_step_wrap">
                        <div class="apply_step_box step1">
                            <div class="gubun">
                                <p>Step 1</p>기본정보 입력
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="100" max="100"></progress>
                            </div>
                            <div class="txt">완료됨</div>
                        </div>
                        <div class="apply_step_box step2">
                            <div class="gubun">
                                <p>Step 2</p>전시 신청 정보
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="10" max="80"></progress>
                            </div>
                            <div class="txt">진행중...<p>(총 <span>8</span>개 중 <span>1</span> 완료)</p></div>
                        </div>
                        <div class="apply_step_box step3">
                            <div class="gubun">
                                <p>Step 3</p>전시참가 신청 완료
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="0" max="100"></progress>
                            </div>
                            <div class="txt">진행조건이 충족되지 않음</div>
                        </div>
                    </div>
                </div>

                <!-- section -->
                <div class="apply_form form_s padding_t">
                    <div class="inner">

                        <!-- 참가분야 -->
                        <div class="form_wrap maritime">
                            <div class="form_tit">
                                <div class="big">참가 여부</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>참가 여부</p>
                                    </div>
                                    <div class="input check m_check">
                                        <label><input type="radio" name="maritimeExhibitionYn" value="Y" <c:if test="${info.maritimeExhibitionYn eq 'Y' or empty info.maritimeExhibitionYn}">checked</c:if> />참가</label>
                                        <div id="maritimeDetailBox" style="display: none;">
                                            (
                                            <label><input type="checkbox" name="maritimeExhibitionSea" value="true" <c:if test="${info.maritimeExhibitionSea}">checked</c:if> /> 해상 전시 (시승체험)</label>
                                            <label><input type="checkbox" name="maritimeExhibitionLand" value="true" <c:if test="${info.maritimeExhibitionLand}">checked</c:if> /> 육상 전시</label>
                                            )
                                        </div>
                                        <label><input type="radio" name="maritimeExhibitionYn" value="N" <c:if test="${info.maritimeExhibitionYn eq 'N'}">checked</c:if> />미참가</label>
                                    </div>
                                </li>
                            </ul>
                            <div class="cmnt">※ 해상전시회 개최 장소인 시흥시는 <span class="txtRed" style="font-weight: 700;">거북섬마리나의 시설 규모 및 계류 여건상, </span>전시 가능한 선박 수에 제한이 있습니다.<br> 이에 따라 <span class="txtRed" style="font-weight: 700;">참가신청 업체 중 선정업체 및 전시 허용 수량은 추후 별도 안내드릴 예정</span>입니다.</div>
                            <div class="maritime_cont">
                                <div class="tit">
                                    <div class="big">
                                        <span>육상(3월, 킨텍스) - 해상(6월, 시흥 거북섬마리나)</span> 연계형 통합 홍보 프로그램_<span>해상전시회</span>
                                    </div>
                                </div>
                                <ul>
                                    <li>2026년 6월, <span>경기국제보트쇼 해상전시회 @ 시흥 거북섬마리나</span> 개최</li>
                                    <li>3월 킨텍스 육상전시회와 6월 거북섬마리나 <span>해상전시회를 하나의 연속 프로그램</span>으로 구성</li>
                                    <li>해상 계류 전시장 내 보트 실물 전시 및 시승체험, 선상투어 등을 통해 <span>해상 체험 중심 프로그램</span> 운영</li>
                                </ul>
                                <div class="maritime_img">
                                    <img src="/img/maritime_img01.jpg" alt="해상전시회 사진">
                                    <img src="/img/maritime_img02.jpg" alt="해상전시회 사진">
                                    <img src="/img/maritime_img03.jpg" alt="해상전시회 사진">
                                </div>
                            </div>
                        </div>

                        <div class="form_btn">
                            <a href="javascript:void(0);" onclick="f_page_move('/apply/step2_1.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">이전</div>
                                <div class="small">전시부스 신청</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_apply_comp('2_10','${info.seq}')" class="form_btn_next">
                                <div class="big">다음 / 건너뛰기</div>
                                <div class="small">요트/보트 출품 정보</div>
                            </a>
                        </div>

                    </div>
                </div>
                <!-- //section -->
            </div>
        </div>
    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    <script>
        $(function(){
            // JSP의 info 객체에서 참가업체 SEQ를 가져옵니다. (다음 단계 이동 시 사용)
            const maritimeRadio = $('input[name="maritimeExhibitionYn"]');
            const detailBox = $('#maritimeDetailBox');

            /**
             * '참가 여부' 라디오 버튼의 현재 선택 상태에 따라
             * '참가 항목' 체크박스 영역을 보여주거나 숨깁니다.
             */
            function toggleDetailBox() {
                // 현재 체크된 라디오 버튼의 value가 'Y' (참가)인지 확인합니다.
                if ($('input[name="maritimeExhibitionYn"]:checked').val() === 'Y') {
                    detailBox.show(); // '참가'이면 상세 항목 박스를 보여줍니다.
                } else {
                    detailBox.hide(); // '미참가'이면 상세 항목 박스를 숨깁니다.
                    // 미참가 선택 시, 기존에 체크되어 있던 상세 항목들을 모두 해제합니다.
                    detailBox.find('input[type="checkbox"]').prop('checked', false);
                }
            }

            // 라디오 버튼의 선택 상태가 변경될 때마다 toggleDetailBox 함수를 실행합니다.
            maritimeRadio.on('change', toggleDetailBox);

            // --- 페이지 로드 시 초기 상태 설정 ▼▼▼ ---
            toggleDetailBox();
        })
    </script>
</body>
</html>