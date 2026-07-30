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
                            <div class="txt">Welcome, ${info.companyNameEn}</div>
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
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Booth</a></li>
                                        <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_10.do','${info.seq}')">해상전시회 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_9.do','${info.seq}')">Yacht/Boat</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_2.do','${info.seq}')">Signboard</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_3.do','${info.seq}')">Utilities</a></li>
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
                            <div class="apply_tit">Booth</div>
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <li>The standard booth size is 3m × 3m (9㎡). The minimum application size is two (2) Raw Space Booths (18㎡) or one (1) Shell Scheme Booth (9㎡).</li>
                                    <li>Shell Scheme Booth Package Includes: 9㎡ floor space, company signboard, lighting, carpet, information desk, folding chair, meeting table, three chairs, waste bin, and 1KW power supply (220V outlet).
                                        <span class="em"> Package specifications are subject to change.</span>
                                    </li>
                                    <li>An invoice will be issued after the application has been reviewed and approved by the Secretariat. Participation will be confirmed only after 100% of the participation fee is paid within seven (7) days from the date of invoice issuance.</li>
                                    <li>All booth fees for overseas exhibitors are charged in USD. VAT may vary depending on the exhibitor’s country and applicable Korean tax regulations. Please refer to the invoice issued by the Secretariat for the final payable amount. All overseas bank transfer fees, including intermediary and receiving bank charges, shall be borne by the exhibitor, and the full invoiced amount must be received by the Organizer.</li>
                                    <li>Please <a onclick="window.open('/eng/exhibitor/privacy.do','pop','menubar=no,status=no,scrollbars=no,resizable=no,width=500,height=300,top=50,left=50');" class="em">click here</a> to view the detailed participation rules and contract terms.</li>
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
                                                <div class="amount">Amount (USD)</div>
                                                <div class="note">Remarks</div>
                                            </li>
                                            <li>
                                                <div class="cate">Raw Space Booth</div>
                                                <div class="cost">USD 3,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="number" id="standAloneBoothCnt" placeholder="Qty" min="0" value="${info.standAloneBoothCnt eq null ? 0 : info.standAloneBoothCnt}" onblur="minCnt(this,2);" onkeyup="checkBooth();">
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="standAloneBoothFee" value="<fmt:formatNumber value="${info.standAloneBoothFee eq null ? 0 : info.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="USD "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">Minimum order: 2 booths</div>
                                            </li>
                                            <li>
                                                <div class="cate">Shell Scheme Booth</div>
                                                <div class="cost">USD 3,300</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="number" id="assemblyBoothCnt" placeholder="Qty" min="0" value="${info.assemblyBoothCnt eq null ? 0 : info.assemblyBoothCnt}" onkeyup="checkBooth();">
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="assemblyBoothFee" value="<fmt:formatNumber value="${info.assemblyBoothFee eq null ? 0 : info.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="USD "/>" disabled>
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
                                <%--<div class="form_tit">
                                    <div class="big">Discount Selection</div>
                                    <div class="small">* Applied automatically based on your application and booth selection</div>
                                </div>--%>
                                <div class="form_disc form_ptag">
                                    <%--<div class="form_ptag_box">
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
                                                        <input type="checkbox" id="discountEarly1" name="discount" data-discount="300000" onchange="calculateTotal();" <c:if test="${info.discountEarly1}">checked</c:if> &lt;%&ndash;disabled&ndash;%&gt;>
                                                    </label>
                                                </div>
                                                <div class="cate rowspan-2">Early Bird Discount&lt;%&ndash; (종료)&ndash;%&gt;</div>
                                                <div class="note">￦ 300,000</div>
                                                <div class="discount">1st Early Bird (by ~2025.11.14)</div>

                                                <div class="select">
                                                    <label class="discount-item" id="discountItem2">
                                                        <input type="checkbox" id="discountEarly2" name="discount" data-discount="200000" onchange="calculateTotal();" <c:if test="${info.discountEarly2}">checked</c:if> &lt;%&ndash;disabled&ndash;%&gt;>
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
                                    </div>--%>

                                    <div class="form_ptag_sum">
                                        <div class="cate2">Estimated Total Booth Fee (Excl. VAT)</div>
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
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step01.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">PREV</div>
                                    <div class="small">Basic Info</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_mypage_comp('2_1','${info.seq}')" class="form_btn_next">
                                    <div class="big">NEXT</div>
                                    <div class="small">Yacht/Boat Information</div>
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
    </c:if>
</body>
</html>