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
    <title>2027 경기국제보트쇼</title>

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

    <link href="/css/invoice.css?ver=20260630" rel="stylesheet">

    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/jquery-migrate-3.3.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
    <script src="/js/jquery.cookie.min.js"></script>
    <script src="/js/jquery.ui.touch-punch.min.js"></script>

</head>
<!--end::Head-->

<!--begin::Body-->
<body>

    <input type="hidden" id="filePath" name="filePath" value="${invoiceInfo.filePath}"/>

    <%-- 영문 여부 및 통화 기호 세팅 --%>
    <c:set var="isEng" value="${exhibitorBaseInfo.lang eq 'EN'}" />
    <c:set var="currMark" value="${isEng ? 'USD ' : '￦ '}" />
    <c:set var="currTail" value="${isEng ? '' : '원'}" />

    <!-- pdf추출용 width=794px -->
    <div class="inv_wrap">
        <%-- ========================= ▼ PDF 1페이지 시작 ▼ ========================= --%>
        <!-- box -->
        <div class="top_box">
            <div class="box">
                <div class="event">
                    <div class="logo"><img src="/img/invoce_logo_kibs2027.png"></div>
                    <div class="date">
                        <c:choose>
                            <c:when test="${isEng}">Mar 12 - 14, 2027, KINTEX</c:when>
                            <c:otherwise>2027.3.12(금) ~ 2027.3.14.(일), KINTEX</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="doc">
                    <c:choose>
                        <c:when test="${isEng}">UTILITY INVOICE</c:when>
                        <c:otherwise>부대시설비 INVOICE</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="box">
                <div class="info">
                    <div class="secr">
                        <c:choose>
                            <c:when test="${isEng}">KIBS 2027 Secretariat</c:when>
                            <c:otherwise>2027 경기국제보트쇼 사무국</c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${isEng}">KINTEX 1, 217-60, Kintex-ro, Ilsanseo-gu, Goyang-si, Gyeonggi-do, Korea</c:when>
                            <c:otherwise>경기도 고양시 일산서구 킨텍스로 217-60 킨텍스 제1전시장</c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${isEng}">Tel : +82-31-995-8946/8912</c:when>
                            <c:otherwise>전화 : 031-995-8946/8912</c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${isEng}">Fax : +82-31-995-8091</c:when>
                            <c:otherwise>팩스 : 031-995-8091</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="issue">
                    <table>
                        <tbody>
                            <tr>
                                <th>
                                    <c:choose><c:when test="${isEng}">Date</c:when><c:otherwise>발행일자</c:otherwise></c:choose>
                                </th>
                                <td>${fn:split(invoiceInfo.initRegiDttm,' ')[0]}</td>
                            </tr>
                            <tr>
                                <th>INVOICE</th>
                                <td>${invoiceInfo.invoiceCode}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title">1. <c:choose><c:when test="${isEng}">Exhibitor Information</c:when><c:otherwise>참가업체 기본 정보</c:otherwise></c:choose></div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup><col width="20%"><col width="30%"><col width="20%"><col width="30%"></colgroup>
                    <tbody>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Company Name</c:when><c:otherwise>업체명</c:otherwise></c:choose></th>
                        <td colspan="3"><c:choose><c:when test="${isEng}">${exhibitorBaseInfo.companyNameEn}</c:when><c:otherwise>${exhibitorBaseInfo.companyNameKo}</c:otherwise></c:choose></td>
                    </tr>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Tel</c:when><c:otherwise>전화</c:otherwise></c:choose></th>
                        <td>${exhibitorBaseInfo.phone}</td>
                        <th>E-mail</th>
                        <td>${exhibitorBaseInfo.email}</td>
                    </tr>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Contact Person</c:when><c:otherwise>담당자</c:otherwise></c:choose></th>
                        <td colspan="2">${exhibitorBaseInfo.name}</td>
                        <td>${exhibitorBaseInfo.position}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- table -->
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title">2. <c:choose><c:when test="${isEng}">Utility Fee Details</c:when><c:otherwise>부대시설비 내역</c:otherwise></c:choose></div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup>
                        <col width="50">
                        <col>
                        <col>
                        <col width="150">
                        <col width="150">
                    </colgroup>
                    <thead>
                        <tr>
                            <th><c:choose><c:when test="${isEng}">No.</c:when><c:otherwise>번호</c:otherwise></c:choose></th>
                            <th><c:choose><c:when test="${isEng}">Description</c:when><c:otherwise>구분</c:otherwise></c:choose></th>
                            <th><c:choose><c:when test="${isEng}">Quantity</c:when><c:otherwise>수량</c:otherwise></c:choose></th>
                            <th><c:choose><c:when test="${isEng}">Unit Price</c:when><c:otherwise>단가</c:otherwise></c:choose></th>
                            <th><c:choose><c:when test="${isEng}">Amount</c:when><c:otherwise>소계</c:otherwise></c:choose></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <%-- 1. 유틸리티 총액(utilityPrcSum)이 0일 경우 --%>
                            <c:when test="${invoiceInfo.utilityPrcSum == 0}">
                                <tr>
                                    <td colspan="5" class="text-center">
                                        <c:choose><c:when test="${isEng}">No utility application history.</c:when><c:otherwise>부대시설 신청 내역이 없습니다.</c:otherwise></c:choose>
                                    </td>
                                </tr>
                            </c:when>

                            <%-- 2. 유틸리티 신청 내역이 하나라도 있을 경우 (기존 로직) --%>
                            <c:otherwise>

                                <c:set var="utilityIdx" value="1"/>
                                <c:if test="${invoiceInfo.utilityJuganCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">220V Power – Daytime Use / 1kw</c:when><c:otherwise>주간 단상 220V / 1kw</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityJuganCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 100</c:when><c:otherwise>100,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityJuganFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityDayCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">220V Power – 24-hour Use / 1kw</c:when><c:otherwise>24시간용 220V / 1kw</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityDayCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 150</c:when><c:otherwise>150,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityDayFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityWorkCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Move-in / Installation Power / 1kw</c:when><c:otherwise>작업전기 / 1kw</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityWorkCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 100</c:when><c:otherwise>100,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityWorkFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityCompressedAirCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Compressed Air</c:when><c:otherwise>압축공기 기본형</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityCompressedAirCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 250</c:when><c:otherwise>250,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityCompressedAirFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityWaterBasicCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Water Supply and Drainage</c:when><c:otherwise>급배수 기본형</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityWaterBasicCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 250</c:when><c:otherwise>250,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityWaterBasicFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityInternetCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Internet</c:when><c:otherwise>인터넷</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityInternetCnt}<c:choose><c:when test="${isEng}"> Line(s)</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 250</c:when><c:otherwise>250,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityInternetFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityPytexNewCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Needle-punched Carpet / Pytex (New)</c:when><c:otherwise>파이텍스(신)</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityPytexNewCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 100</c:when><c:otherwise>100,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityPytexNewFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityPytexReCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Needle-punched Carpet / Pytex (Reused)</c:when><c:otherwise>파이텍스(재)</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityPytexReCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 50</c:when><c:otherwise>50,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityPytexReFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityBarcodeCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td>
                                        <td><c:choose><c:when test="${isEng}">Visitor/Buyer Barcode Scanner</c:when><c:otherwise>참관객/바이어 바코드 리더기</c:otherwise></c:choose></td>
                                        <td>${invoiceInfo.utilityBarcodeCnt}<c:choose><c:when test="${isEng}"> EA</c:when><c:otherwise>개</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${isEng}">USD 250</c:when><c:otherwise>250,000원</c:otherwise></c:choose></td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityBarcodeFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                    <tfoot>
                        <tr><td colspan="5" class="none"></td></tr>
                        <tr>
                            <td colspan="3" class="none"></td>
                            <th><c:choose><c:when test="${isEng}">Sub Total</c:when><c:otherwise>공급가액</c:otherwise></c:choose></th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.prcSum}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                        </tr>
                        <tr>
                            <td colspan="3" class="none"></td>
                            <th><c:choose><c:when test="${isEng}">V.A.T (Exempt)</c:when><c:otherwise>부가세</c:otherwise></c:choose></th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.prcVat}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                        </tr>
                        <tr>
                            <td colspan="3" class="none"></td>
                            <th><c:choose><c:when test="${isEng}">Total</c:when><c:otherwise>합계</c:otherwise></c:choose></th>
                            <td class="text-r total"><fmt:formatNumber value="${invoiceInfo.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <!-- table -->
        </div>
        <!-- //box -->
        <%-- ========================= ▲ PDF 1페이지 종료 ▲ ========================= --%>

        <%-- ========================= ▼ PDF 2페이지 시작 ▼ ========================= --%>
        <!-- box -->
        <div class="cont_box">
            <div class="title">3. <c:choose><c:when test="${isEng}">Payment Deadline</c:when><c:otherwise>납부 기한</c:otherwise></c:choose></div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup>
                        <col>
                        <col>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <th><c:choose><c:when test="${isEng}">Description</c:when><c:otherwise>구분</c:otherwise></c:choose></th>
                            <th><c:choose><c:when test="${isEng}">Amount</c:when><c:otherwise>금액</c:otherwise></c:choose></th>
                            <th><c:choose><c:when test="${isEng}">Deadline</c:when><c:otherwise>납부기한</c:otherwise></c:choose></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><c:choose><c:when test="${isEng}">Total Amount</c:when><c:otherwise>합계</c:otherwise></c:choose></td>
                            <td class="final_total"><fmt:formatNumber value="${invoiceInfo.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                            <td style="color: red; font-size: 15px; font-weight: 700;">
                                <c:choose><c:when test="${isEng}">Within 7 days of invoice issuance</c:when><c:otherwise>인보이스 발행일로부터 7일 이내</c:otherwise></c:choose>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- table -->
            <c:if test="${!isEng}">
                <div class="cmnt">
                    <div>부스비 및 부대시설비의 세금계산서는 행사 종료 후 E-mail을 통해 전자세금계산서 일괄 발행</div>
                    <div><u>납부기한 엄수하여 납부 부탁드립니다.</u></div>
                </div>
            </c:if>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title">4. <c:choose><c:when test="${isEng}">Bank Account Information</c:when><c:otherwise>납부 계좌 정보</c:otherwise></c:choose></div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup>
                        <col>
                        <col>
                    </colgroup>
                    <tbody>
                        <c:choose>
                            <c:when test="${isEng}">
                                <tr>
                                    <th>Bank Name</th>
                                    <td>Nonghyup Bank</td>
                                </tr>
                                <tr>
                                    <th>Account No.</th>
                                    <td>301-0350-8985-51</td>
                                </tr>
                                <tr>
                                    <th>Beneficiary Name</th>
                                    <td>KINTEX</td>
                                </tr>
                                <tr>
                                    <th>SWIFT Code</th>
                                    <td>(Please ask Secretariat if needed)</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <th>은행명</th>
                                    <td>농협</td>
                                </tr>
                                <tr>
                                    <th>계좌번호</th>
                                    <td>301-0350-8985-51</td>
                                </tr>
                                <tr>
                                    <th>예금주</th>
                                    <td>(주)킨텍스</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
            <!-- table -->
            <div style="margin-top: 10px; font-size: 12px;">
                <c:choose>
                    <c:when test="${isEng}">※ An additional invoice will be issued for utility applications.</c:when>
                    <c:otherwise>※ 부대시설비용 신청 시 추가 청구서 별도 발행 / 부스비 및 부대 시설비의 세금계산서는 E-mail을 통해 전자세금계산서 발행</c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="cmnt">
                <c:choose>
                    <c:when test="${isEng}">
                        <div>Inquiry: KIBS 2027 Secretariat (Tel: +82-31-995-8946/8912) (E-mail: kibs@kintex.com)</div>
                        <div>Thank you for your interest and participation.</div>
                        <div>We will do our best to help you achieve your goals through this exhibition.</div>
                    </c:when>
                    <c:otherwise>
                        <div>문의처 : 킨텍스 경기국제보트쇼 사무국 (Tel : 031-995-8946/8912) (E-mail : kibs@kintex.com)</div>
                        <div>귀사의 관심과 참여에 깊은 감사 드립니다.</div>
                        <div>동 전시회를 통해 조기 목표하신 성과를 거양하시도록 최선을 다하겠습니다.</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="bot_box">
            <div class="secr">
                <c:choose>
                    <c:when test="${isEng}">KIBS 2027 Secretariat</c:when>
                    <c:otherwise>2027 경기국제보트쇼 사무국<span class="sm">(관인생략)</span></c:otherwise>
                </c:choose>
            </div>
            <div class="logo"><img src="/img/invoce_logo_kintex.png"></div>
        </div>
        <!-- //box -->
        <%-- ========================= ▲ PDF 2페이지 종료 ▲ ========================= --%>
    </div>

</body>
<!--end::Body-->
</html>