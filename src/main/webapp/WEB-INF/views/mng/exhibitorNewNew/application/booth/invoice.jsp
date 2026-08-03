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

    <link rel="stylesheet" href="/css/invoice.css?ver=20260630">

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
    <c:set var="isEng" value="${exhibitorNewInfo.lang eq 'EN'}" />
    <c:set var="currMark" value="${isEng ? 'USD ' : '￦ '}" />
    <c:set var="currTail" value="${isEng ? '' : '원'}" />

    <!-- pdf추출용 width=794px -->
    <div class="inv_wrap">

        <%-- ========================= ▼ PDF 1페이지 시작 ▼ ========================= --%>

        <!-- box -->
        <div class="top_box">
            <div class="box">
                <div class="event">
                    <div class="logo"><img src="/img/invoce_logo_kibs.png"></div>
                    <div class="date">
                        <c:choose>
                            <c:when test="${isEng}">Mar 12 - 14, 2027, KINTEX</c:when>
                            <c:otherwise>2027.3.12(금) ~ 2027.3.14.(일), KINTEX</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="doc">
                    <c:choose>
                        <c:when test="${isEng}">INVOICE</c:when>
                        <c:otherwise>견적서</c:otherwise>
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
                    <colgroup>
                        <col width="20%">
                        <col width="30%">
                        <col width="20%">
                        <col width="30%">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Company Name</c:when><c:otherwise>업체명</c:otherwise></c:choose></th>
                        <td colspan="3"><c:choose><c:when test="${isEng}">${exhibitorNewInfo.companyNameEn}</c:when><c:otherwise>${exhibitorNewInfo.companyNameKo}</c:otherwise></c:choose></td>
                    </tr>
                    <tr>
                        <c:set var="totalBoothCnt" value="${exhibitorNewInfo.standAloneBoothCnt + exhibitorNewInfo.assemblyBoothCnt + exhibitorNewInfo.onlineBoothCnt}"/>
                        <c:set var="physicalBoothCnt" value="${exhibitorNewInfo.standAloneBoothCnt + exhibitorNewInfo.assemblyBoothCnt}"/>
                        <th><c:choose><c:when test="${isEng}">Scale</c:when><c:otherwise>참가규모</c:otherwise></c:choose></th>
                        <td>${totalBoothCnt} <c:choose><c:when test="${isEng}">Booths</c:when><c:otherwise>부스</c:otherwise></c:choose></td>
                        <th><c:choose><c:when test="${isEng}">Area</c:when><c:otherwise>면적</c:otherwise></c:choose></th>
                        <td>${physicalBoothCnt * 9}㎡</td>
                    </tr>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Tel</c:when><c:otherwise>전화</c:otherwise></c:choose></th>
                        <td>${exhibitorNewInfo.phone}</td>
                        <th>E-mail</th>
                        <td>${exhibitorNewInfo.email}</td>
                    </tr>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Contact Person</c:when><c:otherwise>담당자</c:otherwise></c:choose></th>
                        <td colspan="2">${exhibitorNewInfo.name}</td>
                        <td>${exhibitorNewInfo.position}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- table -->
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title">2. <c:choose><c:when test="${isEng}">Registration Fee</c:when><c:otherwise>등록비</c:otherwise></c:choose></div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup>
                        <col width="50">
                        <col width="50">
                        <col width="200">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><c:choose><c:when test="${isEng}">Description</c:when><c:otherwise>구분</c:otherwise></c:choose></th>
                        <th><c:choose><c:when test="${isEng}">Amount</c:when><c:otherwise>금액</c:otherwise></c:choose></th>
                        <th><c:choose><c:when test="${isEng}">Remarks</c:when><c:otherwise>비고</c:otherwise></c:choose></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><c:choose><c:when test="${isEng}">Registration Fee</c:when><c:otherwise>등록비</c:otherwise></c:choose></td>
                        <td><c:choose><c:when test="${isEng}">USD 0</c:when><c:otherwise>100,000원</c:otherwise></c:choose></td>
                        <td>
                            <c:choose>
                                <c:when test="${isEng}">Registration fee is exempt for overseas exhibitors.</c:when>
                                <c:otherwise>
                                    2019년부터 참가업체의 홍보 지원을 위하여 업체당 참가신청 등록비를 받습니다.<br>
                                    등록비는 참가업체의 각종 온라인 홍보 및 대외 홍보에 사용됩니다.
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- table -->
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title">3. <c:choose><c:when test="${isEng}">Booth Fee</c:when><c:otherwise>참가비 내역</c:otherwise></c:choose></div>
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
                            <th><c:choose><c:when test="${isEng}">Amount</c:when><c:otherwise>금액</c:otherwise></c:choose></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="boothIdx" value="1"/>
                        <c:if test="${exhibitorNewInfo.standAloneBoothCnt > 0}">
                            <tr>
                                <td>${boothIdx}</td>
                                <td><c:choose><c:when test="${isEng}">Raw Space Booth (9㎡ = 3m×3m)</c:when><c:otherwise>독립부스(9㎡ = 3m×3m)</c:otherwise></c:choose></td>
                                <td>${exhibitorNewInfo.standAloneBoothCnt} <c:choose><c:when test="${isEng}">Units</c:when><c:otherwise>부스</c:otherwise></c:choose></td>
                                <td><c:choose><c:when test="${isEng}">USD 3,000</c:when><c:otherwise>1,800,000원</c:otherwise></c:choose></td>
                                <td class="text-r"><fmt:formatNumber value="${exhibitorNewInfo.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                            </tr>
                            <c:set var="boothIdx" value="${boothIdx + 1}"/>
                        </c:if>
                        <c:if test="${exhibitorNewInfo.assemblyBoothCnt > 0}">
                            <tr>
                                <td>${boothIdx}</td>
                                <td><c:choose><c:when test="${isEng}">Shell Scheme Booth (9㎡ = 3m×3m)</c:when><c:otherwise>조립부스(9㎡ = 3m×3m)</c:otherwise></c:choose></td>
                                <td>${exhibitorNewInfo.assemblyBoothCnt} <c:choose><c:when test="${isEng}">Units</c:when><c:otherwise>부스</c:otherwise></c:choose></td>
                                <td><c:choose><c:when test="${isEng}">USD 3,300</c:when><c:otherwise>2,100,000원</c:otherwise></c:choose></td>
                                <td class="text-r"><fmt:formatNumber value="${exhibitorNewInfo.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                            </tr>
                            <c:set var="boothIdx" value="${boothIdx + 1}"/>
                        </c:if>
                    </tbody>
                </table>
            </div>
            <!-- table -->
        </div>
        <!-- //box -->

        <%-- ▼▼▼ 발전기금 계산 로직 추가 ▼▼▼ --%>
        <c:set var="developmentFundToShow" value="0" /> <%-- 표시할 발전기금 변수 초기화 --%>
        <%-- 참가업체 정보(${exhibitorNewInfo})에서 회원사 여부 또는 협회 할인 여부 확인 --%>
        <c:if test="${exhibitorNewInfo.memberCompanyYn eq 'Y' or exhibitorNewInfo.discountLeisure}">
            <%-- 발전기금 기준액 = 부스비 총액(${invoiceInfo.boothPrcSum}) - 총 할인액(${invoiceInfo.discountPrcSum}) --%>
            <c:set var="baseAmountForFund" value="${invoiceInfo.boothPrcSum - invoiceInfo.discountPrcSum}" />
            <c:if test="${baseAmountForFund < 0}">
                <c:set var="baseAmountForFund" value="0" /> <%-- 음수 방지 --%>
            </c:if>
            <%-- 발전기금 계산 (기준액의 10%, 소수점 버림) --%>
            <c:set var="developmentFundToShow" value="${baseAmountForFund * 0.1}" />
            <fmt:formatNumber value="${developmentFundToShow}" pattern="#0" var="developmentFundToShow"/> <%-- 정수로 변환 --%>
        </c:if>
        <%-- ▲▲▲ 발전기금 계산 로직 종료 ▲▲▲ --%>

        <c:if test="${!isEng}">
            <!-- box -->
            <div class="cont_box">
                <div class="title">4. 할인액 내역</div>
                <!-- table -->
                <div class="table">
                    <table>
                        <colgroup>
                            <col width="50"><col><col width="100"><col width="150"><col width="150">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>번호</th><th>구분</th><th>수량</th><th>단가</th><th>금액</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="discountIdx" value="1"/>
                        <c:if test="${exhibitorNewInfo.discountEarly1}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>(1차) 조기신청 할인</td><td>${physicalBoothCnt}부스</td><td>300,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 300000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountEarly2}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>(2차) 조기신청 할인</td><td>${physicalBoothCnt}부스</td><td>200,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 200000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountFirstUnder10}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>첫 참가 할인 (10부스 미만)</td><td>${physicalBoothCnt}부스</td><td>500,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 500000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountFirstOver10}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>첫 참가 할인 (10부스 이상)</td><td>${physicalBoothCnt}부스</td><td>300,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 300000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountRe}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>재참가 할인</td><td>${physicalBoothCnt}부스</td><td>200,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 200000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountScale1}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>규모 할인 (10부스 이상)</td><td>${physicalBoothCnt}부스</td><td>400,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 400000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountScale2}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>규모 할인 (20부스 이상)</td><td>${physicalBoothCnt}부스</td><td>650,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 650000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountScale3}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>규모 할인 (30부스 이상)</td><td>${physicalBoothCnt}부스</td><td>750,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 750000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountScale4}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>규모 할인 (40부스 이상)</td><td>${physicalBoothCnt}부스</td><td>800,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 800000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountScale5}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>규모 할인 (50부스 이상)</td><td>${physicalBoothCnt}부스</td><td>850,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 850000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountScale6}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>규모 할인 (100부스 이상)</td><td>${physicalBoothCnt}부스</td><td>900,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 900000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountLeisure}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>한국해양레저산업협회 할인</td><td>${physicalBoothCnt}부스</td><td>200,000원</td>
                                <td class="text-r">- <fmt:formatNumber value="${physicalBoothCnt * 200000}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountSpecial1Yn}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>특별 할인: 올해의 제품상</td><td colspan="2">공급가액의 50%</td>
                                <td class="text-r">- <fmt:formatNumber value="${(exhibitorNewInfo.boothPrcSum - exhibitorNewInfo.discountPrcSum) * 0.5}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountSpecial2Yn}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>특별 할인: ${exhibitorNewInfo.discountSpecial2Reason}</td><td colspan="2">${exhibitorNewInfo.discountSpecial2Note}</td>
                                <td class="text-r">- <fmt:formatNumber value="${exhibitorNewInfo.discountSpecial2Amount}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        <c:if test="${exhibitorNewInfo.discountSpecial3Yn}"><c:set var="discountIdx" value="${discountIdx + 1}"/>
                            <tr><td>${discountIdx-1}</td><td>특별 할인: ${exhibitorNewInfo.discountSpecial3Reason}</td><td colspan="2">${exhibitorNewInfo.discountSpecial3Note}</td>
                                <td class="text-r">- <fmt:formatNumber value="${exhibitorNewInfo.discountSpecial3Amount}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
                        </tbody>
                    </table>
                </div>
                <!-- table -->
            </div>
            <!-- //box -->
        </c:if>

        <c:if test="${!isEng}">
            <c:if test="${exhibitorNewInfo.memberCompanyYn == 'Y'}">
                <div class="cont_box">
                    <div class="title">5. 한국해양레저산업협회 발전기금</div>
                    <div class="table">
                        <table>
                            <colgroup>
                                <col width="50"><col><col width="150">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>번호</th>
                                <th>구분</th>
                                <th>금액</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>한국해양레저산업협회 발전기금 (참가비 합계의 10%)</td>
                                <td class="text-r">
                                    <fmt:formatNumber value="${developmentFundToShow}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </c:if>

        <div class="cont_box">
            <div class="table">
                <table class="total_sum">
                    <colgroup>
                        <col>
                        <col width="300">
                        <col width="150">
                    </colgroup>
                    <tbody>
                        <c:if test="${!isEng}">
                            <tr>
                                <td class="none"></td>
                                <th>소계(등록비 및 할인 포함)</th>
                                <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.boothPrcSum - invoiceInfo.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                            </tr>
                            <tr>
                                <td class="none"></td>
                                <th>발전 기금</th>
                                <td class="text-r sub_total"><fmt:formatNumber value="${developmentFundToShow}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                            </tr>
                        </c:if>
                        <tr>
                            <td class="none"></td>
                            <th><c:choose><c:when test="${isEng}">Sub Total</c:when><c:otherwise>공급가액 (Sub Total)</c:otherwise></c:choose></th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                        </tr>
                        <tr>
                            <td class="none"></td>
                            <th><c:choose><c:when test="${isEng}">V.A.T (Exempt)</c:when><c:otherwise>부가세 (V.A.T)</c:otherwise></c:choose></th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${(invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) * 0.1}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                        </tr>
                        <tr>
                            <td class="none"></td>
                            <th><c:choose><c:when test="${isEng}">Total</c:when><c:otherwise>최종 합계 (Total)</c:otherwise></c:choose></th>
                            <td class="text-r final_total"><fmt:formatNumber value="${(invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) + ((invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) * 0.1)}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%-- ========================= ▲ PDF 1페이지 종료 ▲ ========================= --%>

        <%-- ========================= ▼ PDF 2페이지 시작 ▼ ========================= --%>
        <!-- box -->
        <div class="cont_box">
            <c:set var="deadlineIdx" value="${isEng ? 4 : (exhibitorNewInfo.memberCompanyYn == 'Y' ? 6 : 5)}"/>
            <div class="title">${deadlineIdx}. <c:choose><c:when test="${isEng}">Payment Deadline</c:when><c:otherwise>납부 기한</c:otherwise></c:choose></div>
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
                        <td><c:choose><c:when test="${isEng}">Participation Fee</c:when><c:otherwise>참가비</c:otherwise></c:choose></td>
                        <%-- DB에 저장된 최종 금액 사용 --%>
                        <td class="final_total"><fmt:formatNumber value="${(invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) + ((invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) * 0.1)}" type="currency" maxFractionDigits="0" currencySymbol="${currMark}"/>${currTail}</td>
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
            <c:set var="bankIdx" value="${isEng ? 5 : (exhibitorNewInfo.memberCompanyYn == 'Y' ? 7 : 6)}"/>
            <div class="title">${bankIdx}. <c:choose><c:when test="${isEng}">Bank Account Information</c:when><c:otherwise>납부 계좌 정보</c:otherwise></c:choose></div>
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