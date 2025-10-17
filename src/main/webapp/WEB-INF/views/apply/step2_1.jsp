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

    <style>
        .discount-item.disabled label {
            color: #999;
            cursor: not-allowed;
        }
        .discount-item.disabled input[type="checkbox"] {
            cursor: not-allowed;
        }
    </style>
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
                    <!-- <div class="apply_nav_tit">기본정보</div> -->
                    <div class="apply_nav_list">
                        <ul class="list1">
                            <li><a>기본정보</a></li>
                            <li class="active">
                                <a href="javascript:void(0);" onclick="f_page_move('/apply/step2_1.do','${info.seq}')">전시 신청 정보</a>
                                <ul class="list2">
                                    <li class="active"><a>전시부스 신청</a></li>
                                    <li><a>전시품 정보</a></li>
                                    <li><a>상호간판 신청</a></li>
                                    <li><a>유틸리티 신청</a></li>
                                    <li><a>출입증 신청</a></li>
                                    <li><a>경품제공 신청</a></li>
                                    <%--<li><a href="/apply/step2_6.do">웹 배너 신청</a></li>--%>
                                    <%--<li><a href="/apply/step2_7.do">온라인 전시관 신청</a></li>--%>
                                    <li><a>디렉토리 정보</a></li>
                                </ul>
                            </li>
                            <li><a>전시참가 신청 완료</a></li>
                        </ul>
                    </div>
                </div>

                <div class="apply_step">
                    <div class="apply_tit">전시부스 신청</div>
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
                                <progress class="gauge_bar" value="3" max="70"></progress>
                            </div>
                            <div class="txt">진행중...<p>(총 <span>7</span>개 중 <span>0</span> 완료)</p></div>
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
                                <li>부스 기본단위는 3m x 3m(9㎡, 1부스)이며, 최소 신청 규모는 독립부스 2부스(18㎡), 조립부스 1부스(9㎡) 입니다.</li>
                                <li>조립부스 제공사항(1부스 기준): 바닥 면적(9㎡), 상호간판(국/영문), 실내 조명, 바닥 파이텍스, 안내 데스크 1개, 접의자 1개 , 상담테이블 1개,
                                    의자 3개, 휴지통 1개, 전기 1KW (220V 콘센트)
                                    <span class="em">(조립부스 제공 사항은 추후 변경될 수 있습니다.)</span>
                                </li>
                                <li>인보이스 수령 후 <span class="em">7일 이내 참가비 전액 납부</span> 시 참가신청이 완료됩니다.
                                </li>
                                <li>자세한 참가규정 및 계약조건은
                                    <a onclick="window.open('/exhibitor/privacy.do','pop','menubar=no,status=no,scrollbars=no,resizable=no,width=500,height=300,top=50,left=50');"
                                        class="em">여기</a> 참고
                                </li>
                            </ul>
                        </div>

                        <input type="hidden" id="prcTotal" value="${info.prcTotal}"/>

                        <!-- 등록비 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">등록비</div>
                            </div>
                            <div class="form_booth form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list">
                                        <li class="form_ptag_hd">
                                            <div class="cate">구분</div>
                                            <div class="amount">금액 (원)</div>
                                            <div class="note">비고</div>
                                        </li>
                                        <li>
                                            <div class="cate">등록비</div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="registrationFee" value="￦ 100,000" disabled>
                                                </p>
                                            </div>
                                            <div class="note">
                                                2019년부터 참가업체의 홍보 지원을 위하여 업체당 참가신청 등록비를 받습니다.<br>
                                                등록비는 참가업체의 각종 온라인 홍보 및 대외 홍보에 사용됩니다.
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- //등록비-->

                        <!-- 부스 신청 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">부스 신청</div>
                            </div>
                            <div class="form_booth form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list">
                                        <li class="form_ptag_hd">
                                            <div class="cate">구분</div>
                                            <div class="cost">단가</div>
                                            <div class="quantity">신청수량</div>
                                            <div class="amount">금액 (원)</div>
                                            <div class="note">비고</div>
                                        </li>
                                        <li>
                                            <div class="cate">독립부스</div>
                                            <div class="cost">￦ 1,800,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="number" id="standAloneBoothCnt" placeholder="수량 입력" min="0" value="${info.standAloneBoothCnt eq null ? 0 : info.standAloneBoothCnt}" onblur="minCnt(this,2);" onkeyup="checkBooth();">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="standAloneBoothFee" value="<fmt:formatNumber value="${info.standAloneBoothFee eq null ? 0 : info.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">독립부스는 2부스부터 신청 가능합니다.</div>
                                        </li>
                                        <li>
                                            <div class="cate">조립부스</div>
                                            <div class="cost">￦ 2,100,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="number" id="assemblyBoothCnt" placeholder="수량 입력" min="0" value="${info.assemblyBoothCnt eq null ? 0 : info.assemblyBoothCnt}" onkeyup="checkBooth();">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="assemblyBoothFee" value="<fmt:formatNumber value="${info.assemblyBoothFee eq null ? 0 : info.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note"></div>
                                        </li>
                                        <li>
                                            <div class="cate">온라인부스</div>
                                            <div class="cost">￦ 1,000,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <select id="onlineBoothCnt" onchange="checkBooth();">
                                                        <option value="0" <c:if test="${info.onlineBoothCnt eq 0}">selected</c:if> >0</option>
                                                        <option value="1" <c:if test="${info.onlineBoothCnt eq 1}">selected</c:if> >1</option>
                                                    </select>
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="onlineBoothFee" value="<fmt:formatNumber value="${info.onlineBoothFee eq null ? 0 : info.onlineBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">영상 제작 별도 : 영상 제작 희망 시 사무국 문의</div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- //부스 신청-->

                        <!-- 할인적용 선택 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">할인적용 선택</div>
                                <div class="small">* 참가신청 및 부스 신청 정보에 따라 자동 반영됩니다.</div>
                            </div>
                            <div class="form_disc form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list form_grid">
                                        <li class="form_ptag_hd">
                                            <div class="select">선택</div>
                                            <div class="cate">구분</div>
                                            <div class="note">내용</div>
                                            <div class="discount">할인금액(부스당)</div>
                                        </li>
                                        <li class="form_span">
                                            <div class="select">
                                                <label class="discount-item" id="discountItem1">
                                                    <input type="checkbox" id="discountEarly1" name="discount" data-discount="300000" onchange="calculateTotal();" <c:if test="${info.discountEarly1}">checked</c:if> <%--disabled--%>>
                                                </label>
                                            </div>
                                            <div class="cate rowspan-2">조기신청 할인<%-- (종료)--%></div>
                                            <div class="note">300,000 원</div>
                                            <div class="discount">(1차) 조기신청 할인 (~2025.11.14 금)</div>

                                            <div class="select">
                                                <label class="discount-item" id="discountItem2">
                                                    <input type="checkbox" id="discountEarly2" name="discount" data-discount="200000" onchange="calculateTotal();" <c:if test="${info.discountEarly2}">checked</c:if> <%--disabled--%>>
                                                </label>
                                            </div>
                                            <div class="note">200,000 원</div>
                                            <div class="discount">(2차) 조기신청 할인 (~2025.12.12 금)</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale1" name="discount" data-discount="400000" onchange="calculateTotal();" <c:if test="${info.discountScale1}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="cate rowspan-6">규모 할인</div>
                                            <div class="note">400,000 원</div>
                                            <div class="discount">10부스 이상 참가업체</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale2" name="discount" data-discount="650000" onchange="calculateTotal();" <c:if test="${info.discountScale2}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">650,000 원</div>
                                            <div class="discount">20부스 이상 참가업체</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale3" name="discount" data-discount="750000" onchange="calculateTotal();" <c:if test="${info.discountScale3}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">750,000 원</div>
                                            <div class="discount">30부스 이상 참가업체</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale4" name="discount" data-discount="800000" onchange="calculateTotal();" <c:if test="${info.discountScale4}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">800,000 원</div>
                                            <div class="discount">40부스 이상 참가업체</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale5" name="discount" data-discount="850000" onchange="calculateTotal();" <c:if test="${info.discountScale5}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">850,000 원</div>
                                            <div class="discount">50부스 이상 참가업체</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale6" name="discount" data-discount="900000" onchange="calculateTotal();" <c:if test="${info.discountScale6}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">900,000 원</div>
                                            <div class="discount">100부스 이상 참가업체</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountRe" name="discount" data-discount="200000" onchange="calculateTotal();" data-db-val="${info.prePartYear}" ${info.prePartYear != 'first' ? 'checked' : ''}>
                                                </label>
                                            </div>
                                            <div class="cate">재참가 할인</div>
                                            <div class="note">200,000 원</div>
                                            <div class="discount">2015년 ~ 2025년 참가 업체</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountFirstUnder10" name="discount" data-discount="500000" onchange="calculateTotal();" data-db-val="${info.prePartYear}" ${info.prePartYear == 'first' && info.standAloneBoothCnt + info.assemblyBoothCnt < 10 ? 'checked' : ''}>
                                                </label>
                                            </div>
                                            <div class="cate rowspan-2">첫참가 할인</div>
                                            <div class="note">500,000 원</div>
                                            <div class="discount">최초 참가업체 (10부스 미만 참가)</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountFirstOver10" name="discount" data-discount="300000" onchange="calculateTotal();" data-db-val="${info.prePartYear}" ${info.prePartYear == 'first' && info.standAloneBoothCnt + info.assemblyBoothCnt >= 10 ? 'checked' : ''}>
                                                </label>
                                            </div>
                                            <div class="note">300,000 원</div>
                                            <div class="discount">최초 참가업체 (10부스 이상 참가)</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountLeisure" name="discount" data-discount="200000" onchange="calculateTotal();" data-db-lock="${info.memberCompanyYn}" ${info.memberCompanyYn == 'Y' ? 'checked' : ''} >
                                                </label>
                                            </div>
                                            <div class="cate">협회 할인</div>
                                            <div class="note">200,000 원</div>
                                            <div class="discount">한국해양레저산업협회 회원사</div>
                                        </li>
                                    </ul>
                                    <div style="margin-top: 10px;">※ 중복할인 가능</div>
                                </div>
                                <div class="form_ptag_sum">
                                    <div class="cate2">총액(VAT 미포함)</div>
                                    <div class="amount2">
                                        <p class="price">
                                            <input type="text" id="totalAmount" value="<fmt:formatNumber value="${info.boothPrcSum - info.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- //할인적용 선택-->

                        <div class="form_btn">
                            <a href="javascript:void(0);" onclick="f_page_move('/apply/step01.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">이전</div>
                                <div class="small">전시 신청 정보</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_apply_comp('2_1','${info.seq}')" class="form_btn_next">
                                <div class="big">다음</div>
                                <div class="small">전시품 정보</div>
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
        $(function(){

            // --- 모든 할인 체크박스의 '임의 체크/해제 시도'를 원천 차단하는 로직 ---
            // mousedown 시점에 체크박스의 현재 상태를 저장
            $(document).on('mousedown', 'input[name="discount"]', function() {
                $(this).data('waschecked', this.checked);
            });

            // click 시점에 상태 변경이 있었는지 확인
            $(document).on('click', 'input[name="discount"]', function(e) {
                // 클릭 전과 후의 상태가 달라졌다면 (체크 시도 또는 해제 시도)
                if ($(this).data('waschecked') !== this.checked) {
                    // 1. 상태 변경 동작을 막습니다.
                    e.preventDefault();
                    // 2. 통합 경고창을 띄웁니다.
                    alert('참가신청 및 부스 신청 정보에 따라 자동 반영되므로,\n임의 체크 및 해제 불가합니다.');
                }
            });

            // --- 모든 자동 계산을 시작하는 핵심 트리거 ---
            // 부스 수량 변경 시 실시간으로 총액을 다시 계산합니다.
            $('#standAloneBoothCnt, #assemblyBoothCnt, #onlineBoothCnt').on('input change', calculateTotal);

            // 페이지 로드 시 초기 계산 및 할인 상태 설정
            handleDiscountEarly1();
            handleDiscountEarly2();
            calculateTotal();

        });
    </script>

</body>
</html>