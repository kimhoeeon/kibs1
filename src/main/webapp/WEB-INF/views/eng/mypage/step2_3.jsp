<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <jsp:include page="../../inc/ad_scripts.jsp" flush="true"/>
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
    <title>2027 KIBS</title>

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

    <c:if test="${sessionScope.get('status') ne 'logon'}">
        <script>
            alert("Please log in.");
            location.href = '/eng/login.do';
        </script>
    </c:if>

    <c:if test="${sessionScope.get('status') eq 'logon'}">

        <c:import url="../header.jsp" charEncoding="UTF-8"/>

        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>Home</span><span>Exhibitors</span><span>Mypage</span>
                        </div>
                        <div class="sub_top_tit">Mypage</div>
                    </div>
                </div>
            </div>
            <!-- //section -->

            <!-- section -->
            <div class="apply_s padding_tb" id="apply_s">
                <div class="inner">

                    <div class="apply_nav">
                        <div class="apply_nav_txt">
                            <div class="txt">Welcome, ${info.companyNameKo}</div>
                            <div class="btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/modify.do','${info.seq}')" class="btnSt01">Member Info</a>
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/index.do', {seq: `${info.seq}`})" class="btnSt01">MYPAGE</a>
                            </div>
                        </div>
                        <div class="apply_nav_list">
                            <ul class="list1">
                                <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step01.do','${info.seq}')">Basic Info</a></li>
                                <li class="active">
                                    <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Exhibition Info</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Booth</a></li>
                                        <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_10.do','${info.seq}')">해상전시회 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_9.do','${info.seq}')">Yacht/Boat</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_2.do','${info.seq}')">Signboard</a></li>
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_3.do','${info.seq}')">Utilities</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_4.do','${info.seq}')">Badges</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_5.do','${info.seq}')">Giveaways</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_8.do','${info.seq}')">Directory</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step03.do','${info.seq}')">Complete</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">
                            <div class="apply_tit">Utilities</div>
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <li>Participating companies that have applied only for the exhibition area must identify the
                                        power required for lighting or equipment before applying.
                                        In particular, please identify the power required for the equipment and fill it out. In
                                        addition, electricity construction is not allowed in the exhibition hall except for the
                                        company selected by the organizer.
                                    </li>
                                    <li>The requested electricity will only be constructed up to your booth distribution box,
                                        and from the booth distribution box to the equipment must be constructed directly by the
                                        participating companies.
                                    </li>
                                </ul>
                            </div>

                                <%-- 1. 부스 정보 --%>
                            <input type="hidden" id="registrationCnt" value="1"/> <%-- 등록비 포함 여부 (보통 1) --%>
                            <input type="hidden" id="hiddenStandAloneCnt" value="${info.standAloneBoothCnt}"/> <%-- 독립부스 수 --%>
                            <input type="hidden" id="hiddenAssemblyCnt" value="${info.assemblyBoothCnt}"/> <%-- 조립부스 수 --%>
                                <%--<input type="hidden" id="hiddenOnlineCnt" value="${info.onlineBoothCnt}"/>--%> <%-- 온라인부스 수 --%>

                                <%-- 2. 기본 할인 적용 여부 --%>
                            <input type="hidden" id="discountEarly1Checked" value="${info.discountEarly1}"/>
                            <input type="hidden" id="discountEarly2Checked" value="${info.discountEarly2}"/>
                            <input type="hidden" id="discountFirstUnder10Checked" value="${info.discountFirstUnder10}"/>
                            <input type="hidden" id="discountFirstOver10Checked" value="${info.discountFirstOver10}"/>
                            <input type="hidden" id="discountReChecked" value="${info.discountRe}"/>
                            <input type="hidden" id="discountScale1Checked" value="${info.discountScale1}"/>
                            <input type="hidden" id="discountScale2Checked" value="${info.discountScale2}"/>
                            <input type="hidden" id="discountScale3Checked" value="${info.discountScale3}"/>
                            <input type="hidden" id="discountScale4Checked" value="${info.discountScale4}"/>
                            <input type="hidden" id="discountScale5Checked" value="${info.discountScale5}"/>
                            <input type="hidden" id="discountScale6Checked" value="${info.discountScale6}"/>
                            <input type="hidden" id="discountLeisureChecked" value="${info.discountLeisure}"/> <%-- 협회 할인 여부 --%>

                                <%-- 3. 특별 할인 정보 --%>
                            <input type="hidden" id="discountSpecial1Yn" value="${info.discountSpecial1Yn}"/>
                            <input type="hidden" id="discountSpecial2Yn" value="${info.discountSpecial2Yn}"/>
                            <input type="hidden" id="discountSpecial2Amount" value="${info.discountSpecial2Amount}"/>
                            <input type="hidden" id="discountSpecial3Yn" value="${info.discountSpecial3Yn}"/>
                            <input type="hidden" id="discountSpecial3Amount" value="${info.discountSpecial3Amount}"/>

                                <%-- 4. 발전기금 계산용 --%>
                            <input type="hidden" id="memberCompanyYn" value="${info.memberCompanyYn}"/>

                                <%-- 5. 선납금 --%>
                            <input type="hidden" id="deposit" value="${info.deposit}"/>

                                <%-- 6. 참가업체 고유번호 --%>
                            <input type="hidden" name="seq" value="${info.seq}"/>

                            <input type="hidden" name="utilityPrcSum" value=""/>
                            <input type="hidden" name="prcSum" value=""/>
                            <input type="hidden" name="prcVat" value=""/>
                            <input type="hidden" name="prcTotal" value=""/>
                            <input type="hidden" name="boothPrcSum" value="${info.boothPrcSum}"/>
                            <input type="hidden" name="discountPrcSum" value="${info.discountPrcSum}"/>

                            <!-- 유틸리티 신청정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Utility</div>
                                </div>
                                <div class="form_util form_ptag">
                                    <div class="form_ptag_box">
                                        <ul class="form_ptag_list">
                                            <li class="form_ptag_hd">
                                                <div class="cate">Item</div>
                                                <div class="cost">Unit Price</div>
                                                <div class="quantity">Quantity</div>
                                                <div class="amount">Amount</div>
                                                <div class="note">Note</div>
                                            </li>
                                            <li>
                                                <div class="cate">220V (Daytime)</div>
                                                <div class="cost">￦ 100,000/1kw</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_jugan_cnt" class="num_sum onlyNum" value="${info.utilityJuganCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_jugan_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityJuganFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Available during the event (3 days)</div>
                                            </li>
                                            <li>
                                                <div class="cate">220V (24 Hours)</div>
                                                <div class="cost">￦ 150,000/1kw</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_day_cnt" class="num_sum onlyNum" value="${info.utilityDayCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_day_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityDayFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Available during the event (3 days)</div>
                                            </li>
                                            <li>
                                                <div class="cate">Installation Power</div>
                                                <div class="cost">￦ 100,000/1kw</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_work_cnt" class="num_sum onlyNum" value="${info.utilityWorkCnt}" placeholder="수량 입력">
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_work_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityWorkFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">장치기간(3/10~3/11, 총 2일)동안 사용가능</div>
                                            </li>
                                            <li>
                                                <div class="cate">Compressed Air Basic Type</div>
                                                <div class="cost">￦ 250,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_compressed_air_cnt" class="num_sum onlyNum" value="${info.utilityCompressedAirCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_compressed_air_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityCompressedAirFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Installation and usage fees</div>
                                            </li>
                                            <li>
                                                <div class="cate">Multiplication Basic Type</div>
                                                <div class="cost">￦ 250,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_water_basic_cnt" class="num_sum onlyNum" value="${info.utilityWaterBasicCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_water_basic_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityWaterBasicFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Installation and usage fees</div>
                                            </li>
                                            <li>
                                                <div class="cate">Internet</div>
                                                <div class="cost">￦ 250,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_internet_cnt" class="num_sum onlyNum" value="${info.utilityInternetCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_internet_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityInternetFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Per line</div>
                                            </li>
                                            <li>
                                                <div class="cate">Pytex (New)</div>
                                                <div class="cost">￦ 100,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_pytex_new_cnt" class="num_sum onlyNum" value="${info.utilityPytexNewCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_pytex_new_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityPytexNewFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Unit price per booth</div>
                                            </li>
                                            <li>
                                                <div class="cate">Pytex (Reuse)</div>
                                                <div class="cost">￦ 50,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_pytex_re_cnt" class="num_sum onlyNum" value="${info.utilityPytexReCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_pytex_re_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityPytexReFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Unit price per booth</div>
                                            </li>
                                            <li>
                                                <div class="cate">Visitor/Buyer Barcode Reader</div>
                                                <div class="cost">￦ 250,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_barcode_cnt" class="num_sum onlyNum" value="${info.utilityBarcodeCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_barcode_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityBarcodeFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Available during the event (3 days)</div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="form_ptag_sum">
                                        <div class="cate2">Total (Excl. VAT)</div>
                                        <div class="amount2">
                                            <p class="price">
                                                <input type="text" id="utilityPrcSumDisplay" class="num_sum" value="" disabled>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- //유틸리티 신청정보 -->

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_2.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">PREV</div>
                                    <div class="small">Signboard</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_mypage_comp('2_3','${info.seq}')" class="form_btn_next">
                                    <div class="big">NEXT</div>
                                    <div class="small">Badges</div>
                                </a>
                            </div>

                        </div>
                    </div>
                    <!-- //section -->
                </div>
            </div>

        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

        <script type="text/javascript">
            /**
             * 화면의 유틸리티 항목별 금액 합계를 계산하여 반환하는 헬퍼 함수
             * (이 함수는 main.js의 calculateTotal 내부에서 호출됩니다)
             */
            function calculateCurrentUtilitySum() {
                let utilityPrcSum = 0;
                utilityPrcSum += parseInt(uncomma($('#utility_jugan_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_day_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_work_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_compressed_air_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_water_basic_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_internet_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_pytex_new_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_pytex_re_fee').val())) || 0;
                utilityPrcSum += parseInt(uncomma($('#utility_barcode_fee').val())) || 0;

                // 화면의 유틸리티 소계 표시 업데이트
                //$('#utilityPrcSumDisplay').val(numberToWon(utilityPrcSum)); // ※ JSP span 필요

                return utilityPrcSum;
            }

            $(function(){
                // --- 유틸리티 항목별 금액 자동 계산 로직 (기존 로직 활용) ---
                function autoUtilitySum(elementId, pricePerUnit) {
                    const qty = parseInt($('#' + elementId + '_cnt').val()) || 0;
                    const totalFee = qty * pricePerUnit;
                    $('#' + elementId + '_fee').val(numberToWon(totalFee));

                    // ★★★ 항목 변경 시 main.js의 calculateTotal 호출 ('utility' 타입 전달) ★★★
                    if (typeof calculateTotal === 'function') {
                        calculateTotal('utility');
                    } else {
                        console.error("main.js의 calculateTotal 함수를 찾을 수 없습니다.");
                    }
                }

                // 각 유틸리티 항목 수량 변경 시 autoUtilitySum 호출
                // ※※※ 단가 확인 필수 ※※※ (CommConstants 값과 일치해야 함)
                const UTILITY_JUGAN_PRICE = 100000;
                const UTILITY_DAY_PRICE = 150000;
                const UTILITY_WORK_PRICE = 100000;
                const UTILITY_COMPRESSED_AIR_PRICE = 250000;
                const UTILITY_WATER_BASIC_PRICE = 250000;
                const UTILITY_INTERNET_PRICE = 250000;
                const UTILITY_PYTEX_NEW_PRICE = 100000;
                const UTILITY_PYTEX_RE_PRICE = 50000;
                const UTILITY_BARCODE_PRICE = 250000;

                $('#utility_jugan_cnt').on('input change', function() { autoUtilitySum('utility_jugan', UTILITY_JUGAN_PRICE); });
                $('#utility_day_cnt').on('input change', function() { autoUtilitySum('utility_day', UTILITY_DAY_PRICE); });
                $('#utility_work_cnt').on('input change', function() { autoUtilitySum('utility_work', UTILITY_WORK_PRICE); });
                $('#utility_compressed_air_cnt').on('input change', function() { autoUtilitySum('utility_compressed_air', UTILITY_COMPRESSED_AIR_PRICE); });
                $('#utility_water_basic_cnt').on('input change', function() { autoUtilitySum('utility_water_basic', UTILITY_WATER_BASIC_PRICE); });
                $('#utility_internet_cnt').on('input change', function() { autoUtilitySum('utility_internet', UTILITY_INTERNET_PRICE); });
                $('#utility_pytex_new_cnt').on('input change', function() { autoUtilitySum('utility_pytex_new', UTILITY_PYTEX_NEW_PRICE); });
                $('#utility_pytex_re_cnt').on('input change', function() { autoUtilitySum('utility_pytex_re', UTILITY_PYTEX_RE_PRICE); });
                $('#utility_barcode_cnt').on('input change', function() { autoUtilitySum('utility_barcode', UTILITY_BARCODE_PRICE); });
                // --- ▲▲▲ ---

                // 페이지 로드 시 초기 계산 ('utility' 타입 전달)
                if (typeof calculateTotal === 'function') {
                    calculateTotal('utility'); // ★★★ 페이지 타입 전달 ★★★
                } else {
                    console.error("main.js의 calculateTotal 함수를 찾을 수 없습니다.");
                }

                /*Swal.fire({
                icon: 'warning',
                title: '[ 유틸리티 신청 ]',
                html: '<span style="font-size: 1.2em;">참가업체 유틸리티 신청은 마감되었습니다.<br>이후 신청 건은 사무국으로 문의 바랍니다.</span>',
                allowOutsideClick: false,
                confirmButtonColor: '#00a8ff',
                confirmButtonText: '확인'
                }).then(async (result) => {
                    if (result.isConfirmed) {

                    }
                });*/
            });
        </script>
    </c:if>
</body>
</html>