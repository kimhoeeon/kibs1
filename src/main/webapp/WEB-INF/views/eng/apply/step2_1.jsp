<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

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
    <title>2027 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <jsp:include page="../../inc/common_css.jsp" flush="true"/>
    <jsp:include page="../../inc/common_js_en.jsp" flush="true"/>
    <jsp:include page="../../inc/ad_scripts.jsp" flush="true"/>

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
                        <span>Home</span><span>Exhibitors</span><span>Exhibitor Registration</span>
                    </div>
                    <div class="sub_top_tit">Exhibitor Registration</div>
                </div>
            </div>
        </div>
        <!-- //section -->

        <!-- section -->
        <div class="sub_tab">
            <div class="inner">
                <div class="sub_tab_box">
                    <a href="javascript:void(0);" onclick="home('en');" class="iconHome"><img src="/img/icon_home.png"></a>
                    <div class="optionBox">
                        <div class="tabOpt1 tabOption">
                            <div class="tabOptAct">Exhibitors</div>
                            <div class="tabOptSel">
                                <a href="/eng/guide/summary.do">KIBS 2027</a>
                                <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                                <a href="/eng/buyer/glance.do">Buyers</a>
                                <a href="/eng/board/notice.do">News</a>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">Exhibitor Registration</div>
                            <div class="tabOptSel">
                                <a href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                                <a class="active" href="/eng/apply/step01.do">Exhibitor Registration</a>
                                <a href="/eng/exhibitor/glance.do">Korea at a Glance</a>
                                <a href="/eng/exhibitor/marina.do">Marine Industry in korea</a>
                                <a href="/eng/exhibitor/match.do">Exhibitor-Buyer Matchmaking Program</a>
                                <a href="/eng/online/company.do">Exhibitor List</a>
                                <a href="/eng/exhibitor/qna.do">FAQ</a>
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
                            <li><a>Basic Info</a></li>
                            <li class="active">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step2_1.do','${info.seq}')">Exhibition Info</a>
                                <ul class="list2">
                                    <li class="active"><a>Booth</a></li>
                                    <li><a>Yacht/Boat</a></li>
                                    <li><a>Signboard</a></li>
                                    <li><a>Utilities</a></li>
                                    <li><a>Badges</a></li>
                                    <li><a>Giveaways</a></li>
                                    <li><a>Directory</a></li>
                                </ul>
                            </li>
                            <li><a>Complete</a></li>
                        </ul>
                    </div>
                </div>

                <div class="apply_step">
                    <div class="apply_tit">Booth Application</div>
                    <div class="apply_step_wrap">
                        <div class="apply_step_box step1">
                            <div class="gubun">
                                <p>Step 1</p>Basic Info
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="100" max="100"></progress>
                            </div>
                            <div class="txt">Completed</div>
                        </div>
                        <div class="apply_step_box step2">
                            <div class="gubun">
                                <p>Step 2</p>Exhibition Info
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="12.5" max="100"></progress>
                            </div>
                            <div class="txt">In Progress <p>(<span>7</span> of <span>0</span> completed)</p></div>
                        </div>
                        <div class="apply_step_box step3">
                            <div class="gubun">
                                <p>Step 3</p>Complete
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="0" max="100"></progress>
                            </div>
                            <div class="txt">Requirement Not Met</div>
                        </div>
                    </div>
                </div>

                <!-- section -->
                <div class="apply_form form_s padding_t">
                    <div class="inner">

                        <div class="form_wrap">
                            <ul class="form_guide">
                                <li>The standard booth size is 3m × 3m (9㎡). The minimum application size is 2 space-only booths (18㎡) or 1 shell scheme booth (9㎡).
                                </li>
                                <li>Shell Scheme Booth Includes (per booth): 9㎡ floor space, company signboard (Korean/English), lighting, carpet, information desk, folding chair, meeting table, 3 chairs, waste bin, and 1KW power supply (220V outlet)
                                    <span class="em"> (Shell scheme booth specifications are subject to change.)</span>
                                </li>
                                <li>Your application will be confirmed upon full payment within 7 days of invoice issuance.</li>
                                <li>
                                    For detailed participation rules and contract terms, <a onclick="window.open('/eng/exhibitor/privacy.do','pop','menubar=no,status=no,scrollbars=no,resizable=no,width=500,height=300,top=50,left=50');" class="em">click here.</a>
                                </li>
                            </ul>
                        </div>

                        <input type="hidden" id="utilityPrcSum" value="${info.utilityPrcSum}"/>
                        <input type="hidden" id="discountSpecial1Yn" value="${info.discountSpecial1Yn}">
                        <input type="hidden" id="discountSpecial2Yn" value="${info.discountSpecial2Yn}">
                        <input type="hidden" id="discountSpecial2Amount" value="${info.discountSpecial2Amount}">
                        <input type="hidden" id="discountSpecial3Yn" value="${info.discountSpecial3Yn}">
                        <input type="hidden" id="discountSpecial3Amount" value="${info.discountSpecial3Amount}">
                        <input type="hidden" id="deposit" value="${info.deposit}"/>
                        <input type="hidden" id="memberCompanyYn" value="${info.memberCompanyYn}">
                        <input type="hidden" id="boothPrcSum" name="boothPrcSum" value="">
                        <input type="hidden" id="discountPrcSum" name="discountPrcSum" value="">
                        <input type="hidden" id="prcSum" name="prcSum" value="">
                        <input type="hidden" id="prcVat" name="prcVat" value="">
                        <input type="hidden" id="prcTotal" name="prcTotal" value="${info.prcTotal}">

                        <!-- 등록비 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Registration Fee</div>
                            </div>
                            <div class="form_booth form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list">
                                        <li class="form_ptag_hd">
                                            <div class="cate">Category</div>
                                            <div class="amount">Amount (KRW)</div>
                                            <div class="note">Remarks</div>
                                        </li>
                                        <li>
                                            <div class="cate">Registration Fee</div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="registrationFee" value="￦ 100,000" disabled>
                                                </p>
                                            </div>
                                            <div class="note">
                                                Since 2019, a registration fee has been charged to support exhibitor promotion. <br />
                                                The registration fee will be used for online and external promotional activities.
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
                                <div class="big">Booth Application</div>
                            </div>
                            <div class="form_booth form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list">
                                        <li class="form_ptag_hd">
                                            <div class="cate">Category</div>
                                            <div class="cost">Unit Price</div>
                                            <div class="quantity">Qty</div>
                                            <div class="amount">Amount (KRW)</div>
                                            <div class="note">Remarks</div>
                                        </li>
                                        <li>
                                            <div class="cate">Space-only Booth</div>
                                            <div class="cost">￦ 1,800,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="number" id="standAloneBoothCnt" placeholder="Qty" min="0" value="${info.standAloneBoothCnt eq null ? 0 : info.standAloneBoothCnt}" onblur="minCnt(this,2);" onkeyup="checkBooth();">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="standAloneBoothFee" value="<fmt:formatNumber value="${info.standAloneBoothFee eq null ? 0 : info.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note">Minimum order: 2 booths</div>
                                        </li>
                                        <li>
                                            <div class="cate">Shell Scheme Booth</div>
                                            <div class="cost">￦ 2,100,000</div>
                                            <div class="quantity">
                                                <p class="j_num">
                                                    <input type="number" id="assemblyBoothCnt" placeholder="Qty" min="0" value="${info.assemblyBoothCnt eq null ? 0 : info.assemblyBoothCnt}" onkeyup="checkBooth();">
                                                </p>
                                            </div>
                                            <div class="amount">
                                                <p class="price">
                                                    <input type="text" id="assemblyBoothFee" value="<fmt:formatNumber value="${info.assemblyBoothFee eq null ? 0 : info.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                </p>
                                            </div>
                                            <div class="note"></div>
                                        </li>
                                        <%--<li>
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
                                        </li>--%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- //부스 신청-->

                        <!-- 할인적용 선택 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Discount Selection</div>
                                <div class="small">* Applied automatically based on your application and booth selection</div>
                            </div>
                            <div class="form_disc form_ptag">
                                <div class="form_ptag_box">
                                    <ul class="form_ptag_list form_grid">
                                        <li class="form_ptag_hd">
                                            <div class="select">Select</div>
                                            <div class="cate">Category</div>
                                            <div class="note">Discount (per booth)</div>
                                            <div class="discount">Description</div>
                                        </li>
                                        <li class="form_span">
                                            <div class="select">
                                                <label class="discount-item" id="discountItem1">
                                                    <input type="checkbox" id="discountEarly1" name="discount" data-discount="300000" onchange="calculateTotal();" <c:if test="${info.discountEarly1}">checked</c:if> <%--disabled--%>>
                                                </label>
                                            </div>
                                            <div class="cate rowspan-2">Early Bird Discount<%-- (종료)--%></div>
                                            <div class="note">￦ 300,000</div>
                                            <div class="discount">1st Early Bird (by ~2025.11.14)</div>

                                            <div class="select">
                                                <label class="discount-item" id="discountItem2">
                                                    <input type="checkbox" id="discountEarly2" name="discount" data-discount="200000" onchange="calculateTotal();" <c:if test="${info.discountEarly2}">checked</c:if> <%--disabled--%>>
                                                </label>
                                            </div>
                                            <div class="note">￦ 200,000</div>
                                            <div class="discount">2nd Early Bird (by ~2025.12.12)</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale1" name="discount" data-discount="400000" onchange="calculateTotal();" <c:if test="${info.discountScale1}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="cate rowspan-6">Volume Discount</div>
                                            <div class="note">￦ 400,000</div>
                                            <div class="discount">10+ Booths</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale2" name="discount" data-discount="650000" onchange="calculateTotal();" <c:if test="${info.discountScale2}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">￦ 650,000</div>
                                            <div class="discount">20+ Booths</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale3" name="discount" data-discount="750000" onchange="calculateTotal();" <c:if test="${info.discountScale3}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">￦ 750,000</div>
                                            <div class="discount">30+ Booths</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale4" name="discount" data-discount="800000" onchange="calculateTotal();" <c:if test="${info.discountScale4}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">￦ 800,000</div>
                                            <div class="discount">40+ Booths</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale5" name="discount" data-discount="850000" onchange="calculateTotal();" <c:if test="${info.discountScale5}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">￦ 850,000</div>
                                            <div class="discount">50+ Booths</div>

                                            <div class="select">
                                                <label class="discount-item single-choice-discount">
                                                    <input type="checkbox" id="discountScale6" name="discount" data-discount="900000" onchange="calculateTotal();" <c:if test="${info.discountScale6}">checked</c:if> >
                                                </label>
                                            </div>
                                            <div class="note">￦ 900,000</div>
                                            <div class="discount">100+ Booths</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountRe" name="discount" data-discount="200000" onchange="calculateTotal();" data-db-val="${info.prePartYear}" ${info.prePartYear != 'first' ? 'checked' : ''}>
                                                </label>
                                            </div>
                                            <div class="cate">Returning Discount</div>
                                            <div class="note">￦ 200,000</div>
                                            <div class="discount">Exhibited from 2015-2026</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountFirstUnder10" name="discount" data-discount="500000" onchange="calculateTotal();" data-db-val="${info.prePartYear}" ${info.prePartYear == 'first' && info.standAloneBoothCnt + info.assemblyBoothCnt < 10 ? 'checked' : ''}>
                                                </label>
                                            </div>
                                            <div class="cate rowspan-2">First-time Discount</div>
                                            <div class="note">￦ 500,000</div>
                                            <div class="discount">First-time Exhibitor (Less than 10 Booths)</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountFirstOver10" name="discount" data-discount="300000" onchange="calculateTotal();" data-db-val="${info.prePartYear}" ${info.prePartYear == 'first' && info.standAloneBoothCnt + info.assemblyBoothCnt >= 10 ? 'checked' : ''}>
                                                </label>
                                            </div>
                                            <div class="note">￦ 300,000</div>
                                            <div class="discount">First-time Exhibitor (10+ Booths)</div>

                                            <div class="select">
                                                <label class="discount-item">
                                                    <input type="checkbox" id="discountLeisure" name="discount" data-discount="200000" onchange="calculateTotal();" data-db-lock="${info.memberCompanyYn}" ${info.memberCompanyYn == 'Y' ? 'checked' : ''} >
                                                </label>
                                            </div>
                                            <div class="cate">Association Member Discount</div>
                                            <div class="note">￦ 200,000</div>
                                            <div class="discount">KMIA Members</div>
                                        </li>
                                    </ul>
                                    <div style="margin-top: 10px;">※ Multiple discounts allowed.</div>

                                    <!-- 한국해양레저산업협회 발전기금 -->
                                    <div style="margin-top: 70px; margin-bottom: 30px;">
                                        <div class="form_tit">
                                            <div class="big">KMIA Development Fund</div>
                                        </div>
                                        <div class="form_booth form_ptag">
                                            <div class="form_ptag_box">
                                                <ul class="form_ptag_list">
                                                    <li class="form_ptag_hd">
                                                        <div class="cost">Eligible Applicants</div>
                                                        <div class="cate">Amount</div>
                                                        <div class="cate">Description</div>
                                                    </li>
                                                    <li>
                                                        <div class="cost">KMIA Members</div>
                                                        <div class="cate">10% of the participation fee (after discounts)</div>
                                                        <div class="cate">Confirmed after application submission (see My Page of Invoice)</div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- //한국해양레저산업협회 발전기금-->

                                </div>
                                <div class="form_ptag_sum">
                                    <div class="cate2">Total (Excl. VAT, KMIA Development Fund)</div>
                                    <div class="amount2">
                                        <p class="price">
                                            <input type="text" id="boothPrcSumDisplay" value="" disabled>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- //할인적용 선택-->

                        <div class="form_btn">
                            <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step01.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">PREV</div>
                                <div class="small">Exhibition Info</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_apply_comp('2_1','${info.seq}')" class="form_btn_next">
                                <div class="big">NEXT</div>
                                <div class="small">Yacht/Boat</div>
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

            // --- 1. 날짜 및 마감일 정의 (KST: UTC+9) ---
            const today = new Date(); // 사용자의 현재 시간
            const deadline1 = new Date('2025-11-14T23:59:59+09:00'); // 1차 마감: 11월 14일 23:59:59
            const deadline2 = new Date('2025-12-12T23:59:59+09:00'); // 2차 마감: 12월 12일 23:59:59

            // --- 2. 체크박스 및 라벨 요소 ---
            const $check1 = $('#discountEarly1');
            const $label1 = $check1.closest('label.discount-item');
            const $check2 = $('#discountEarly2');
            const $label2 = $check2.closest('label.discount-item');

            // --- 3. DB에서 로드된 초기 상태 (1차, 2차 모두 확인) ---
            const isEarly1_DB = $check1.is(':checked');
            const isEarly2_DB = $check2.is(':checked'); // 2차 신청자 확인용

            // --- 4. 조기신청 마감일 로직 (규칙 1, 2, 3 적용) ---

            // [경우 A] 1차 마감일(14일) 이전
            if (today <= deadline1) {
                // 1차 활성화, 2차 비활성화 (1차 기간이므로)
                $check1.prop('disabled', false);
                $label1.removeClass('disabled');
                $check2.prop('disabled', true).prop('checked', false);
                $label2.addClass('disabled');

                // 1차 기간인데 1차가 체크 안되어있으면 (신규) 자동 체크
                if (!isEarly1_DB) {
                    $check1.prop('checked', true);
                }
            }
            // [경우 B] 1차 마감(14일) 이후 ~ 2차 마감(12월 12일) 이전
            else if (today > deadline1 && today <= deadline2) {

                // 1차는 무조건 비활성화
                $check1.prop('disabled', true);
                $label1.addClass('disabled');

                if (isEarly1_DB) {
                    // [규칙 2, 3] 1차에 이미 신청했던 사람
                    // 1차는 (checked, disabled) 상태 유지
                    // 2차는 (unchecked, disabled) (중복 선택 불가)
                    $check2.prop('disabled', true).prop('checked', false);
                    $label2.addClass('disabled');

                } else {
                    // [규칙 1] 14일 이후 최초 신청자 (1차를 놓친 사람)
                    // 1차는 (unchecked, disabled)
                    $check1.prop('checked', false);

                    // 2차는 (checked, enabled) - 2차 할인을 자동 적용
                    // (단, 2차가 이미 체크된 상태가 아니라면)
                    if (!isEarly2_DB) {
                        $check2.prop('checked', true);
                    }
                    $check2.prop('disabled', false);
                    $label2.removeClass('disabled');
                }
            }
            // [경우 C] 2차 마감일(12월 12일) 이후
            else {
                // 모든 조기신청 마감 (기존 신청자는 체크된 채로 비활성화)
                $check1.prop('disabled', true);
                $label1.addClass('disabled');
                $check2.prop('disabled', true);
                $label2.addClass('disabled');

                // 1차 신청자도 아니고, 2차 신청자도 아니었던 경우에만 2차 체크 해제
                if (!isEarly1_DB && !isEarly2_DB) {
                    $check2.prop('checked', false);
                }
            }

            // (기존 할인 체크박스 차단 로직은 그대로 둡니다)
            $(document).on('mousedown', 'input[name=\"discount\"]', function() {
                $(this).data('waschecked', this.checked);
            });
            $(document).on('click', 'input[name=\"discount\"]', function(e) {
                if ($(this).data('waschecked') !== this.checked) {
                    e.preventDefault();
                    alert('It is automatically reflected based on the registration and booth application information, so you cannot check arbitrarily.');
                }
            });

            // --- 모든 자동 계산을 시작하는 핵심 트리거 ---
            // 부스 수량 변경 시 main.js의 calculateTotal 호출 ('booth' 타입 전달)
            $('#standAloneBoothCnt, #assemblyBoothCnt/*, #onlineBoothCnt*/').on('input change', function() {
                if (typeof calculateTotal === 'function') {
                    calculateTotal('booth'); // ★★★ 페이지 타입 전달 ★★★
                } else {
                    console.error("main.js의 calculateTotal 함수를 찾을 수 없습니다.");
                }
            });

            // 페이지 로드 시 최종 계산 (모든 할인 상태가 결정된 후)
            if (typeof calculateTotal === 'function') {
                calculateTotal('booth');
            } else {
                console.error("main.js의 calculateTotal 함수를 찾을 수 없습니다.");
            }
        });
    </script>

</body>
</html>