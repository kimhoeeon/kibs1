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

    <link rel="stylesheet" href="/css/invoice.css?ver=<%=System.currentTimeMillis()%>">

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

    <!-- pdf추출용 width=794px -->
    <div class="inv_wrap">

        <%-- ========================= ▼ PDF 1페이지 시작 ▼ ========================= --%>

        <!-- box -->
        <div class="top_box">
            <div class="box">
                <div class="event">
                    <div class="logo"><img src="/img/invoce_logo_kibs.png"></div>
                    <div class="date">2026.3.6(FRI) ~ 2025.3.8.(SUN), KINTEX</div>
                </div>
                <div class="doc">견적서</div>
            </div>
            <div class="box">
                <div class="info">
                    <div class="secr">2026 경기국제보트쇼 사무국</div>
                    <div>경기도 고양시 일산서구 킨텍스로 217-60 킨텍스 제1전시장</div>
                    <div>전화 : 031-995-8788/8777</div>
                    <div>팩스 : 031-995-8091</div>
                </div>
                <div class="issue">
                    <table>
                        <tbody>
                        <tr>
                            <th>발행일자</th>
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
            <div class="title">1. 참가업체 기본 정보</div>
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
                        <th>업체명</th>
                        <td colspan="3">${exhibitorNewInfo.companyNameKo}</td>
                    </tr>
                    <tr>
                        <c:set var="totalBoothCnt" value="${exhibitorNewInfo.standAloneBoothCnt + exhibitorNewInfo.assemblyBoothCnt + exhibitorNewInfo.onlineBoothCnt}"/>
                        <c:set var="physicalBoothCnt" value="${exhibitorNewInfo.standAloneBoothCnt + exhibitorNewInfo.assemblyBoothCnt}"/>
                        <th>참가규모</th>
                        <td>${totalBoothCnt} 부스</td>
                        <th>면적</th>
                        <td>${physicalBoothCnt * 9}㎡</td>
                    </tr>
                    <tr>
                        <th>전화</th>
                        <td>${exhibitorNewInfo.phone}</td>
                        <th>E-mail</th>
                        <td>${exhibitorNewInfo.email}</td>
                    </tr>
                    <tr>
                        <th>담당자</th>
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
            <div class="title">2. 등록비</div>
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
                        <th>구분</th>
                        <th>금액</th>
                        <th>비고</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>등록비</td>
                            <td>100,000원</td>
                            <td>
                                2019년부터 참가업체의 홍보 지원을 위하여 업체당 참가신청 등록비를 받습니다.<br>
                                등록비는 참가업체의 각종 온라인 홍보 및 대외 홍보에 사용됩니다.
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
            <div class="title">3. 참가비 내역</div>
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
                            <th>번호</th>
                            <th>구분</th>
                            <th>수량</th>
                            <th>단가</th>
                            <th>소계</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="boothIdx" value="1"/>
                        <c:if test="${exhibitorNewInfo.standAloneBoothCnt > 0}">
                            <tr>
                                <td>${boothIdx}</td><td>독립부스(9㎡ = 3m×3m)</td><td>${exhibitorNewInfo.standAloneBoothCnt}부스</td><td>1,800,000원</td>
                                <td class="text-r"><fmt:formatNumber value="${exhibitorNewInfo.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                            </tr>
                            <c:set var="boothIdx" value="${boothIdx + 1}"/>
                        </c:if>
                        <c:if test="${exhibitorNewInfo.assemblyBoothCnt > 0}">
                            <tr>
                                <td>${boothIdx}</td><td>조립부스(9㎡ = 3m×3m)</td><td>${exhibitorNewInfo.assemblyBoothCnt}부스</td><td>2,100,000원</td>
                                <td class="text-r"><fmt:formatNumber value="${exhibitorNewInfo.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                            </tr>
                            <c:set var="boothIdx" value="${boothIdx + 1}"/>
                        </c:if>
                        <c:if test="${exhibitorNewInfo.onlineBoothCnt > 0}">
                            <tr>
                                <td>${boothIdx}</td><td>온라인 전시회 참가</td><td>${exhibitorNewInfo.onlineBoothCnt} 식</td><td>1,000,000원</td>
                                <td class="text-r"><fmt:formatNumber value="${exhibitorNewInfo.onlineBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                            </tr>
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

        <c:if test="${exhibitorNewInfo.memberCompanyYn == 'Y'}">
            <div class="cont_box">
                <div class="title">4. 한국해양레저산업협회 발전기금</div>
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

        <!-- box -->
        <div class="cont_box">
            <div class="title"><c:choose><c:when test="${exhibitorNewInfo.memberCompanyYn == 'Y'}">5</c:when><c:otherwise>4</c:otherwise></c:choose>. 할인액 내역</div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup>
                        <col width="50"><col><col width="100"><col width="150"><col width="150">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>번호</th><th>구분</th><th>수량</th><th>단가</th><th>소계</th>
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
                            <td class="text-r">- <fmt:formatNumber value="${(exhibitorNewInfo.boothPrcSum + exhibitorNewInfo.utilityPrcSum - exhibitorNewInfo.discountPrcSum) * 0.5}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td></tr></c:if>
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

        <div class="cont_box">
            <div class="table">
                <table class="total_sum">
                    <colgroup>
                        <col>
                        <col width="300">
                        <col width="150">
                    </colgroup>
                    <tbody>
                        <tr>
                            <td class="none"></td>
                            <th>참가비 합계(등록비 및 할인 포함)</th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.boothPrcSum - invoiceInfo.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                        <tr>
                            <td class="none"></td>
                            <th>발전 기금</th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${developmentFundToShow}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                        <tr>
                            <td class="none"></td>
                            <th>공급가액 (Sub Total)</th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                        <tr>
                            <td class="none"></td>
                            <th>부가세 (V.A.T)</th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${(invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) * 0.1}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                        <tr>
                            <td class="none"></td>
                            <th>최종 합계 (Total)</th>
                            <td class="text-r final_total"><fmt:formatNumber value="${(invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) + ((invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) * 0.1)}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%-- ========================= ▲ PDF 1페이지 종료 ▲ ========================= --%>

        <%-- ========================= ▼ PDF 2페이지 시작 ▼ ========================= --%>
        <!-- box -->
        <div class="cont_box">
            <div class="title"><c:choose><c:when test="${exhibitorNewInfo.memberCompanyYn == 'Y'}">6</c:when><c:otherwise>5</c:otherwise></c:choose>. 납부 기한</div>
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
                            <th>구분</th>
                            <th>금액</th>
                            <th>납부기한</th>
                        </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>참가비</td>
                        <%-- DB에 저장된 최종 금액 사용 --%>
                        <td class="final_total"><fmt:formatNumber value="${(invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) + ((invoiceInfo.boothPrcSum + developmentFundToShow - invoiceInfo.discountPrcSum) * 0.1)}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        <td style="color: red; font-size: 15px; font-weight: 700;">인보이스 발행일로부터 7일 이내</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- table -->
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title"><c:choose><c:when test="${exhibitorNewInfo.memberCompanyYn == 'Y'}">7</c:when><c:otherwise>6</c:otherwise></c:choose>. 납부 계좌 정보</div>
            <!-- table -->
            <div class="table">
                <table>
                    <colgroup>
                        <col>
                        <col>
                    </colgroup>
                    <tbody>
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
                    </tbody>
                </table>
            </div>
            <!-- table -->
            <div style="margin-top: 10px; font-size: 12px;">※ 부대시설비용 신청 시 추가 청구서 별도 발행 / 부스비 및 부대시설비의 세금계산서는 E-mail을 통해 전자세금계산서 발행</div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="cmnt">
                <div>문의처 : 킨텍스 경기국제보트쇼 사무국 (Tel : 031-995-8788/8777) (E-mail : kibs@kintex.com)</div>
                <div>귀사의 관심과 참여에 깊은 감사 드립니다.</div>
                <div>동 전시회를 통해 조기 목표하신 성과를 거양하시도록 최선을 다하겠습니다.</div>
            </div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="bot_box">
            <div class="secr">2026 경기국제보트쇼 사무국<span class="sm">(관인생략)</span></div>
            <div class="logo"><img src="/img/invoce_logo_kintex.png"></div>
        </div>
        <!-- //box -->

        <%-- ========================= ▲ PDF 2페이지 종료 ▲ ========================= --%>
    </div>

</body>
<!--end::Body-->
</html>