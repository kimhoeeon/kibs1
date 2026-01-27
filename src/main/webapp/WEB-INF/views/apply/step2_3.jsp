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
                                    <li><a>해상전시회 신청</a></li>
                                    <li><a>요트/보트 출품 정보</a></li>
                                    <li><a>상호간판 신청</a></li>
                                    <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/apply/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
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
                    <div class="apply_tit">유틸리티 신청</div>
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
                                <progress class="gauge_bar" value="40" max="80"></progress>
                            </div>
                            <div class="txt">진행중...<p>(총 <span>8</span>개 중 <span>4</span> 완료)</p>
                            </div>
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

                        <div class="form_wrap">
                            <ul class="form_guide">
                                <li>전시면적만(Space Only) 신청하신 참가업체에서는 반드시 조명용 혹은 장비용으로 소요되는 전력을 파악하시어 신청하여 주시기 바랍니다.<br>
                                    (특히, 장비에 필요한 전력은 정확히 파악하시어 작성해 주셔야 하며, 또한, 전력 시공은 주최자가 선정한 업체 외에는 전시장내에서 전기공사 작업이
                                    불가능하므로 반드시 지정된 업체에서 공사를 할 수 있도록 조치를 취해 주시기를 부탁드립니다.)
                                </li>
                                <li>신청된 전기는 귀사의 부스 분전반까지만 시공을 하고 부스 분전반에서 장비까지는 참가업체에서 직접 시공을 하셔야 합니다.</li>
                            </ul>
                        </div>

                        <%-- 1. 부스 정보 --%>
                        <input type="hidden" id="registrationCnt" value="1"/> <%-- 등록비 포함 여부 (보통 1) --%>
                        <input type="hidden" id="hiddenStandAloneCnt" value="${info.standAloneBoothCnt}"/> <%-- 독립부스 수 --%>
                        <input type="hidden" id="hiddenAssemblyCnt" value="${info.assemblyBoothCnt}"/> <%-- 조립부스 수 --%>
                        <input type="hidden" id="hiddenOnlineCnt" value="${info.onlineBoothCnt}"/> <%-- 온라인부스 수 --%>

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
                                <div class="big">유틸리티 신청정보</div>
                            </div>
                            <div class="form_util form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list">
                                        <li class="form_ptag_hd">
                                            <div class="cate">품목</div>
                                            <div class="cost">단가</div>
                                            <div class="quantity">신청수량</div>
                                            <div class="amount">금액 (원)</div>
                                            <div class="note">비고</div>
                                        </li>
                                        <li>
                                            <div class="cate">주간 단상 220v</div>
                                            <div class="cost">￦ 80,000/1kw</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_jugan_cnt" class="num_sum onlyNum" value="${info.utilityJuganCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_jugan_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityJuganFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">행사기간(3/6~3/8, 총 3일)동안 사용가능</div>
                                        </li>
                                        <li>
                                            <div class="cate">24시간용 220v</div>
                                            <div class="cost">￦ 100,000/1kw</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_day_cnt" class="num_sum onlyNum" value="${info.utilityDayCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_day_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityDayFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">행사기간(3/6~3/8, 총 3일)동안 사용가능</div>
                                        </li>
                                        <li>
                                            <div class="cate">작업전기</div>
                                            <div class="cost">￦ 80,000/1kw</div>
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
                                            <div class="note">장치기간(3/4~3/5, 총 2일)동안 사용가능</div>
                                        </li>
                                        <li>
                                            <div class="cate">압축공기 기본형</div>
                                            <div class="cost">￦ 200,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_compressed_air_cnt" class="num_sum onlyNum" value="${info.utilityCompressedAirCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_compressed_air_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityCompressedAirFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">설치 및 사용료</div>
                                        </li>
                                        <li>
                                            <div class="cate">급배수 기본형</div>
                                            <div class="cost">￦ 200,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_water_basic_cnt" class="num_sum onlyNum" value="${info.utilityWaterBasicCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_water_basic_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityWaterBasicFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">설치 및 사용료</div>
                                        </li>
                                        <li>
                                            <div class="cate">인터넷</div>
                                            <div class="cost">￦ 200,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_internet_cnt" class="num_sum onlyNum" value="${info.utilityInternetCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_internet_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityInternetFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">1회선당</div>
                                        </li>
                                        <li>
                                            <div class="cate">파이텍스 (신품)</div>
                                            <div class="cost">￦ 80,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_pytex_new_cnt" class="num_sum onlyNum" value="${info.utilityPytexNewCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_pytex_new_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityPytexNewFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">1부스당 단가</div>
                                        </li>
                                        <li>
                                            <div class="cate">파이텍스 (재사용품)</div>
                                            <div class="cost">￦ 50,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_pytex_re_cnt" class="num_sum onlyNum" value="${info.utilityPytexReCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_pytex_re_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityPytexReFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">1부스당 단가</div>
                                        </li>
                                        <li>
                                            <div class="cate">참관객/바이어 바코드 리더기</div>
                                            <div class="cost">￦ 200,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="text" id="utility_barcode_cnt" class="num_sum onlyNum" value="${info.utilityBarcodeCnt}" placeholder="수량 입력">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="utility_barcode_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityBarcodeFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">행사기간(3/6~3/8, 총 3일)동안 사용가능</div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="form_ptag_sum">
                                    <div class="cate2">총액(VAT 미포함)</div>
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
                            <a href="javascript:void(0);" onclick="f_page_move('/apply/step2_2.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">이전</div>
                                <div class="small">상호간판 신청</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_apply_comp('2_3','${info.seq}')" class="form_btn_next">
                                <div class="big">다음 / 건너뛰기</div>
                                <div class="small">출입증 신청</div>
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
        // ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
        // ※ 기존 유틸리티 계산 관련 함수/로직은 여기서 완전히 삭제합니다. ※
        // ※ (main.js의 calculateTotal 함수를 사용합니다)           ※
        // ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※

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
            const UTILITY_JUGAN_PRICE = 80000;
            const UTILITY_DAY_PRICE = 100000;
            const UTILITY_WORK_PRICE = 80000;
            const UTILITY_COMPRESSED_AIR_PRICE = 200000;
            const UTILITY_WATER_BASIC_PRICE = 200000;
            const UTILITY_INTERNET_PRICE = 200000;
            const UTILITY_PYTEX_NEW_PRICE = 80000;
            const UTILITY_PYTEX_RE_PRICE = 50000;
            const UTILITY_BARCODE_PRICE = 200000;

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
        });
    </script>
</body>
</html>