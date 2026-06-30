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

    <!-- pdf추출용 width=794px -->
    <div class="inv_wrap">
        <%-- ========================= ▼ PDF 1페이지 시작 ▼ ========================= --%>
        <!-- box -->
        <div class="top_box">
            <div class="box">
                <div class="event">
                    <div class="logo"><img src="/img/invoce_logo_kibs.png"></div>
                    <div class="date">2027.3.12(금) ~ 2027.3.14.(일), KINTEX</div>
                </div>
                <div class="doc">부대시설비 INVOICE</div>
            </div>
            <div class="box">
                <div class="info">
                    <div class="secr">2027 경기국제보트쇼 사무국</div>
                    <div>경기도 고양시 일산서구 킨텍스로 217-60 킨텍스 제1전시장</div>
                    <div>전화 : 031-995-8946/8912</div>
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
                    <tbody>
                    <tr>
                        <th>업체명</th>
                        <td colspan="3">${exhibitorBaseInfo.companyNameKo}</td>
                    </tr>
                    <tr>
                        <th>전화</th>
                        <td>${exhibitorBaseInfo.phone}</td>
                        <th>E-mail</th>
                        <td>${exhibitorBaseInfo.email}</td>
                    </tr>
                    <tr>
                        <th>담당자</th>
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
            <div class="title">2. 부대시설비 내역</div>
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
                        <c:choose>
                            <%-- 1. 유틸리티 총액(utilityPrcSum)이 0일 경우 --%>
                            <c:when test="${invoiceInfo.utilityPrcSum == 0}">
                                <tr>
                                    <td colspan="5" class="text-center">부대시설 신청 내역이 없습니다.</td>
                                </tr>
                            </c:when>

                            <%-- 2. 유틸리티 신청 내역이 하나라도 있을 경우 (기존 로직) --%>
                            <c:otherwise>

                                <c:set var="utilityIdx" value="1"/>
                                <c:if test="${invoiceInfo.utilityJuganCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>주간 단상 220V / 1kw</td><td>${invoiceInfo.utilityJuganCnt}개</td><td>100,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityJuganFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityDayCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>24시간용 220V / 1kw</td><td>${invoiceInfo.utilityDayCnt}개</td><td>150,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityDayFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityWorkCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>작업전기 / 1kw</td><td>${invoiceInfo.utilityWorkCnt}개</td><td>100,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityWorkFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityCompressedAirCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>압축공기 기본형</td><td>${invoiceInfo.utilityCompressedAirCnt}개</td><td>250,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityCompressedAirFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityWaterBasicCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>급배수 기본형</td><td>${invoiceInfo.utilityWaterBasicCnt}개</td><td>250,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityWaterBasicFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityInternetCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>인터넷</td><td>${invoiceInfo.utilityInternetCnt}개</td><td>250,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityInternetFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityPytexNewCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>파이텍스(신)</td><td>${invoiceInfo.utilityPytexNewCnt}개</td><td>100,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityPytexNewFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityPytexReCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>파이텍스(재)</td><td>${invoiceInfo.utilityPytexReCnt}개</td><td>50,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityPytexReFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                                <c:if test="${invoiceInfo.utilityBarcodeCnt > 0}">
                                    <tr>
                                        <td>${utilityIdx}</td><td>참관객/바이어 바코드 리더기</td><td>${invoiceInfo.utilityBarcodeCnt}개</td><td>250,000원</td>
                                        <td class="text-r"><fmt:formatNumber value="${invoiceInfo.utilityBarcodeFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                                    </tr><c:set var="utilityIdx" value="${utilityIdx + 1}"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                    <tfoot>
                        <tr><td colspan="5" class="none"></td></tr>
                        <tr>
                            <td colspan="3" class="none"></td>
                            <th>공급가액</th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.prcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                        <tr>
                            <td colspan="3" class="none"></td>
                            <th>부가세</th>
                            <td class="text-r sub_total"><fmt:formatNumber value="${invoiceInfo.prcVat}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        </tr>
                        <tr>
                            <td colspan="3" class="none"></td>
                            <th>합계</th>
                            <td class="text-r total"><fmt:formatNumber value="${invoiceInfo.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
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
            <div class="title">3. 납부 기한</div>
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
                        <td>합계</td>
                        <td class="final_total"><fmt:formatNumber value="${invoiceInfo.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>원</td>
                        <td style="color: red; font-size: 15px; font-weight: 700;">인보이스 발행일로부터 7일 이내</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- table -->
            <div class="cmnt">
                <div>부스비 및 부대시설비의 세금계산서는 행사 종료 후 E-mail을 통해 전자세금계산서 일괄 발행</div>
                <div><u>납부기한 엄수하여 납부 부탁드립니다.</u></div>
            </div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="title">4. 납부 계좌 정보</div>
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
            <div style="margin-top: 10px; font-size: 12px;">※ 부대시설비용 신청 시 추가 청구서 별도 발행 / 부스비 및 부대 시설비의 세금계산서는 E-mail을 통해 전자세금계산서 발행</div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="cont_box">
            <div class="cmnt">
                <div>문의처 : 킨텍스 경기국제보트쇼 사무국 (Tel : 031-995-8946/8912) (E-mail : kibs@kintex.com)</div>
                <div>귀사의 관심과 참여에 깊은 감사 드립니다.</div>
                <div>동 전시회를 통해 조기 목표하신 성과를 거양하시도록 최선을 다하겠습니다.</div>
            </div>
        </div>
        <!-- //box -->

        <!-- box -->
        <div class="bot_box">
            <div class="secr">2027 경기국제보트쇼 사무국<span class="sm">(관인생략)</span></div>
            <div class="logo"><img src="/img/invoce_logo_kintex.png"></div>
        </div>
        <!-- //box -->
        <%-- ========================= ▲ PDF 2페이지 종료 ▲ ========================= --%>
    </div>

</body>
<!--end::Body-->
</html>