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
                            <li>
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Exhibition Info</a>
                                <ul class="list2">
                                    <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Booth</a></li>
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
                <div class="my_main my_form form_s">
                    <div class="inner">
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Participation Fee Payment Status :
                                    <c:if test="${info.prcYn eq '0'}"><span class="txtRed">NON PAYMENT</span></c:if>
                                    <c:if test="${info.prcYn eq '1'}"><span class="txtBlue">Free booth</span></c:if>
                                    <c:if test="${info.prcYn eq '2'}"><span class="txtBlue">Participation Fee (50%) Payment</span></c:if>
                                    <c:if test="${info.prcYn eq '3'}"><span class="txtBlue">Participation Fee (100%) Payment</span></c:if>
                                    <c:if test="${info.prcYn eq '4'}"><span class="txtBlue">Full payment (participation fee/facility fees)</span></c:if>
                                </div>
                            </div>
                            <div class="my_main_pay">
                                <ul class="payList">
                                    <li>
                                        <div class="cont_box">
                                            <div class="icon"><img src="/img/icon_my01.png"></div>
                                            <div class="txt_box">
                                                <div class="gubun">Booth Participation Fee</div>
                                                <div class="txt">
                                                    <c:set var="specialDicountTotal" value="0"/>
                                                    <c:choose>
                                                        <c:when test="${info.memberCompanyYn eq 'Y'}">
                                                            <c:set var="baseSpecialAmount" value="${info.boothPrcSum + info.utilityPrcSum - info.discountPrcSum}"/>
                                                            <c:if test="${info.discountSpecial1Yn}">
                                                                <c:set var="specialDicountTotal" value="${baseSpecialAmount * 0.5}"/>
                                                            </c:if>
                                                            <c:if test="${info.discountSpecial2Yn}">
                                                                <c:set var="specialDicountTotal" value="${specialDicountTotal + info.discountSpecial2Amount}"/>
                                                            </c:if>
                                                            <c:if test="${info.discountSpecial3Yn}">
                                                                <c:set var="specialDicountTotal" value="${specialDicountTotal + info.discountSpecial3Amount}"/>
                                                            </c:if>
                                                            ￦ <fmt:formatNumber value="${info.boothPrcSum + ((info.boothPrcSum + info.utilityPrcSum - info.discountPrcSum - specialDicountTotal) * 0.1)}" pattern="#,###"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ￦ <fmt:formatNumber value="${info.boothPrcSum}" pattern="#,###"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                        <a class="btn" href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">┼</a>
                                    </li>
                                    <li>
                                        <div class="cont_box">
                                            <div class="icon"><img src="/img/icon_my02.png"></div>
                                            <div class="txt_box">
                                                <div class="gubun">Utilities</div>
                                                <div class="txt">￦ <fmt:formatNumber value="${info.utilityPrcSum}" pattern="#,###"/></div>
                                            </div>
                                        </div>
                                        <a class="btn" href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_3.do','${info.seq}')">┼</a>
                                    </li>
                                    <li>
                                        <div class="cont_box">
                                            <div class="icon"><img src="/img/icon_my04.png"></div>
                                            <div class="txt_box">
                                                <div class="gubun">Discount</div>
                                                <div class="txt">
                                                    <c:set var="baseTotal" value="${info.boothPrcSum - info.discountPrcSum}"/>
                                                    <c:set var="baseSpecialDiscount" value="0"/>
                                                    <c:if test="${info.discountSpecial1Yn}">
                                                        <c:set var="baseSpecialDiscount" value="${baseTotal * 0.5}"/>
                                                    </c:if>
                                                    <c:if test="${info.discountSpecial2Yn}">
                                                        <c:set var="baseSpecialDiscount" value="${baseSpecialDiscount + info.discountSpecial2Amount}"/>
                                                    </c:if>
                                                    <c:if test="${info.discountSpecial3Yn}">
                                                        <c:set var="baseSpecialDiscount" value="${baseSpecialDiscount + info.discountSpecial3Amount}"/>
                                                    </c:if>
                                                    ￦ <fmt:formatNumber value="${info.discountPrcSum + baseSpecialDiscount}" pattern="#,###"/>
                                                </div>
                                            </div>
                                        </div>
                                        <a class="btn" href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">┼</a>
                                    </li>
                                    <li>
                                        <div class="cont_box">
                                            <div class="icon"><img src="/img/icon_my05.png"></div>
                                            <div class="txt_box">
                                                <div class="gubun">Total (Excl. VAT)</div>
                                                <div class="txt">￦ <fmt:formatNumber value="${info.prcSum}" pattern="#,###"/></div>
                                            </div>
                                        </div>
                                        <a class="btn" href="javascript:void(0);" onclick="f_page_move('/eng/mypage/total.do','${info.seq}')">┼</a>
                                    </li>
                                </ul>
                                <div class="payTotal">
                                    <span class="amount">￦ <fmt:formatNumber value="${info.prcSum}" pattern="#,###"/></span>
                                    <span class="vat">VAT ￦ <fmt:formatNumber value="${info.prcVat}" pattern="#,###"/></span>
                                    <span class="total">TOTALS ￦ <fmt:formatNumber value="${info.prcTotal}" pattern="#,###"/></span>
                                </div>
                            </div>
                        </div>
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Application Form <span class="txtRed">How to Fix It</span></div>
                            </div>
                            <div>
                                <ul>
                                    <li style="margin-bottom: 15px; font-size: 1.8rem;">
                                        1 . In the left tab of My Page, click the tab you want to edit and enter the information you want to modify.
                                    </li>
                                    <li style="font-size: 1.8rem;">
                                        2 . After entering the information you want to edit, click the [Next] button at the bottom right to save. If a notification window like "Booth application information has been saved" appears, the information has been properly updated.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- //section -->

            </div>
        </div>
        <!-- //section -->

    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>
</c:if>
</body>
</html>