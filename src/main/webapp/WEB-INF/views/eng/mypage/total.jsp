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

</head>

<body>
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
                <div class="my_form form_s">
                    <div class="inner">

                        <!-- 전시부스 신청 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Booth</div>
                            </div>
                            <div class="form_total_table form_total_booth">
                                <div class="table_box">
                                    <table>
                                        <colgroup>
                                            <col width="5%">
                                            <col width="20%">
                                            <col width="10%">
                                            <col width="15%">
                                            <col width="15%">
                                            <col>
                                            <col>
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>Booth Type</th>
                                                <th>Quantity</th>
                                                <th>Unit Price (USD)</th>
                                                <th>Amount (USD)</th>
                                                <th>Registered Date</th>
                                                <th>Last Updated</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="boothRownum" value="0"/>
                                            <c:if test="${info.standAloneBoothCnt > 0}">
                                                <c:set var="boothRownum" value="${boothRownum + 1}"/>
                                                <tr>
                                                    <td>${boothRownum}</td>
                                                    <td>Raw Space Booth</td>
                                                    <td>${info.standAloneBoothCnt}</td>
                                                    <td>3,000</td>
                                                    <td><fmt:formatNumber value="${info.standAloneBoothFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.assemblyBoothCnt > 0}">
                                                <c:set var="boothRownum" value="${boothRownum + 1}"/>
                                                <tr>
                                                    <td>${boothRownum}</td>
                                                    <td>Shell Scheme Booth</td>
                                                    <td>${info.assemblyBoothCnt}</td>
                                                    <td>3,300</td>
                                                    <td><fmt:formatNumber value="${info.assemblyBoothFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <tr class="txtBold">
                                                <td colspan="6">Estimated Total Booth Fee (Excl. VAT)</td>
                                                <td colspan="1"><fmt:formatNumber value="${info.boothPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="USD "/></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!-- 전시부스 신청 -->

                        <!-- 부스할인 -->
                        <%--<div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Booth Discount Choices</div>
                            </div>
                            <div class="form_total_table form_total_disc">
                                <div class="table_box">
                                    <table>
                                        <colgroup>
                                            <col width="10%">
                                            <col width="20%">
                                            <col>
                                            <col width="10%">
                                            <col width="20%">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Sortation</th>
                                            <th>Discount</th>
                                            <th>Quantity</th>
                                            <th>Total Discount</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>A First-time Discount</td>
                                            <td>Discount ￦ 200,000 per booth</td>
                                            <td>2</td>
                                            <td>- 400,000</td>
                                        </tr>
                                        <tr class="txtBold">
                                            <td colspan="4">Sum Amount</td>
                                            <td>- ￦ 400,000</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>--%>
                        <!-- 부스할인 -->

                        <!-- 유틸리티 신청 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Utility Service</div>
                            </div>
                            <div class="form_total_table form_total_util">
                                <div class="table_box">
                                    <table>
                                        <colgroup>
                                            <col width="5%">
                                            <col width="20%">
                                            <col width="10%">
                                            <col width="15%">
                                            <col width="15%">
                                            <col>
                                            <col>
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>Item</th>
                                                <th>Quantity</th>
                                                <th>Unit Price (USD)</th>
                                                <th>Amount (USD)</th>
                                                <th>Registered Date</th>
                                                <th>Last Updated</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="utilityRownum" value="0"/>
                                            <c:if test="${info.utilityJuganCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>220V Power – Daytime Use</td>
                                                    <td>${info.utilityJuganCnt}</td>
                                                    <td>100/1kw</td>
                                                    <td><fmt:formatNumber value="${info.utilityJuganFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityDayCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>220V Power – 24-hour Use</td>
                                                    <td>${info.utilityDayCnt}</td>
                                                    <td>150/1kw</td>
                                                    <td><fmt:formatNumber value="${info.utilityDayFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityWorkCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Move-in / Installation Power</td>
                                                    <td>${info.utilityWorkCnt}</td>
                                                    <td>100/1kw</td>
                                                    <td><fmt:formatNumber value="${info.utilityWorkFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityCompressedAirCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Compressed Air</td>
                                                    <td>${info.utilityCompressedAirCnt}</td>
                                                    <td>250</td>
                                                    <td><fmt:formatNumber value="${info.utilityCompressedAirFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityWaterBasicCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Water Supply and Drainage</td>
                                                    <td>${info.utilityWaterBasicCnt}</td>
                                                    <td>250</td>
                                                    <td><fmt:formatNumber value="${info.utilityWaterBasicFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityInternetCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Internet</td>
                                                    <td>${info.utilityInternetCnt}</td>
                                                    <td>250</td>
                                                    <td><fmt:formatNumber value="${info.utilityInternetFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityPytexNewCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Needle-punched Carpet / Pytex (New)</td>
                                                    <td>${info.utilityPytexNewCnt}</td>
                                                    <td>100</td>
                                                    <td><fmt:formatNumber value="${info.utilityPytexNewFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityPytexReCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Needle-punched Carpet / Pytex (Reused)</td>
                                                    <td>${info.utilityPytexReCnt}</td>
                                                    <td>50</td>
                                                    <td><fmt:formatNumber value="${info.utilityPytexReFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityBarcodeCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>Visitor/Buyer Barcode Scanner</td>
                                                    <td>${info.utilityBarcodeCnt}</td>
                                                    <td>250</td>
                                                    <td><fmt:formatNumber value="${info.utilityBarcodeFee}" type="number" /></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>

                                            <tr class="txtBold">
                                                <td colspan="6">Estimated Total Utility Fee (Excl. VAT)</td>
                                                <td colspan="1"><fmt:formatNumber value="${info.utilityPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="USD "/></td>
                                            </tr>
                                            <tr class="txtBold">
                                                <td colspan="6">Deposit</td>
                                                <td colspan="1"><fmt:formatNumber value="${info.deposit}" type="currency" maxFractionDigits="0" currencySymbol="USD "/></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!-- 유틸리티 신청 -->

                        <!-- 초청희망바이어 -->
                        <%--<div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Buyer Invitation</div>
                            </div>
                            <div class="form_total_table form_total_buyer">
                                <div class="table_box">
                                    <table>
                                        <colgroup>
                                            <col width="5%">
                                            <col>
                                            <col>
                                            <col>
                                            <col>
                                            <col>
                                            <col>
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Nation</th>
                                            <th>Corporate Name</th>
                                            <th>Name</th>
                                            <th>Department</th>
                                            <th>Position</th>
                                            <th>E-mail</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>대한민국</td>
                                            <td>프리미어케어</td>
                                            <td>홍길동</td>
                                            <td>기획팀</td>
                                            <td>과장</td>
                                            <td>abc@abc.om</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>--%>
                        <!-- 초청희망바이어 -->

                        <!-- 최종금액 -->
                        <div class="form_wrap">
                            <div class="form_total_table form_total_amount">
                                <div class="table_box">
                                    <table style="border-top:1px solid #ddd">
                                        <colgroup>
                                            <col width="30%">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr class="txtBold">
                                            <td class="bgBlue">Total Amount (Excl. VAT)</td>
                                            <td class="txtBlue" style="font-size: 2rem;"><fmt:formatNumber value="${info.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="USD "/></td>
                                        </tr>
                                        <tr class="txtBold">
                                            <td class="bgBlue" rowspan="2">Payment History</td>
                                            <td>Deposit : <fmt:formatNumber value="${info.deposit}" type="currency" maxFractionDigits="0" currencySymbol="USD "/></td>
                                        </tr>
                                        <tr class="txtBold">
                                            <td class="txtRed">Balance Due : <fmt:formatNumber value="${info.prcTotal - info.deposit}" type="currency" maxFractionDigits="0" currencySymbol="USD "/></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!-- //최종금액 -->

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="popMemberOut popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">Account Deletion Information</div>
                    <a class="close" href="#close"><img src="/img/close_w.png"></a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <ul class="out_guide">
                            <li>Once you delete your account, your current ID cannot be reused or recovered.</li>
                            <li>The deleted ID cannot be used by you or anyone else, so please choose carefully.</li>
                            <li>Upon deletion, all your membership information and service usage records will be permanently deleted.</li>
                        </ul>
                        <div class="out_check">
                            <label>
                                <input type="checkbox"> I have read and agree to all of the above information.
                            </label>
                        </div>
                        <div class="btn_box">
                            <a href="" class="close btnSt03">Cancel</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>