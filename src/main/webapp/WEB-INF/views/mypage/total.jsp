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
    <meta name="author" content="Kibs 2025">
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
    <title>2025 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <span itemscope="" itemtype="http://schema.org/Organization">
    <link itemprop="url" href="https://kibs.com/">
    <a itemprop="sameAs" href="https://www.koreaboatshow.or.kr/"></a>
    <a itemprop="sameAs" href="https://koreaboatshow.re.kr/"></a>
    <a itemprop="sameAs" href="https://kibs-online.com"></a>
    <a itemprop="sameAs" href="https://www.youtube.com/channel/UCvcRu_g4M1MOIIuJyllR6Rw"></a>
    <a itemprop="sameAs" href="https://www.youtube.com/@KIBSKINTEX"></a>
    </span>
</head>

<body>

    <c:if test="${status ne 'logon'}">
        <script>
            alert("로그인해 주세요.");
            location.href = '/login.do';
        </script>
    </c:if>

    <c:if test="${status eq 'logon'}">

        <c:import url="../header.jsp" charEncoding="UTF-8"/>

        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>홈</span><span>참가기업</span><span>마이페이지</span>
                        </div>
                        <div class="sub_top_tit">마이페이지</div>
                    </div>
                </div>
            </div>
            <!-- //section -->

            <!-- section -->
            <div class="apply_s padding_tb" id="apply_s">
                <div class="inner">

                    <div class="apply_nav">
                        <div class="apply_nav_txt">
                            <div class="txt">${sessionScope.id} 님 환영합니다!</div>
                            <div class="btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/modify.do','${info.seq}')" class="btnSt01">회원정보관리</a>
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do','${info.seq}')" class="btnSt01">마이페이지</a>
                            </div>
                        </div>
                        <div class="apply_nav_list">
                            <ul class="list1">
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step01.do','${info.seq}')">기본정보</a></li>
                                <li>
                                    <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시 신청 정보</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시부스 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')">출입증 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_5.do','${info.seq}')">경품제공 신청</a></li>
                                            <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_6.do','${info.seq}')">웹 배너 신청</a></li>--%>
                                            <%--<li><a href="/mypage/step2_7.do">온라인 전시관 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_8.do','${info.seq}')">디렉토리 정보</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">

                            <!-- 전시부스 신청 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">전시부스 신청</div>
                                </div>
                                <div class="form_total_table form_total_booth">
                                    <div class="table_box">
                                        <table>
                                            <colgroup>
                                                <col width="5%">
                                                <col width="12%">
                                                <col width="8%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col>
                                                <col>
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>구분</th>
                                                <th>신청수량</th>
                                                <th>단가</th>
                                                <th>금액</th>
                                                <th>등록일</th>
                                                <th>수정일</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="boothRownum" value="0"/>
                                            <c:if test="${info.registrationCnt > 0}">
                                                <c:set var="boothRownum" value="${boothRownum + 1}"/>
                                                <tr>
                                                    <td>${boothRownum}</td>
                                                    <td>등록비</td>
                                                    <td>${info.registrationCnt}</td>
                                                    <td>100,000</td>
                                                    <td><fmt:formatNumber value="${info.registrationFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.standAloneBoothCnt > 0}">
                                                <c:set var="boothRownum" value="${boothRownum + 1}"/>
                                                <tr>
                                                    <td>${boothRownum}</td>
                                                    <td>독립부스</td>
                                                    <td>${info.standAloneBoothCnt}</td>
                                                    <td>1,800,000</td>
                                                    <td><fmt:formatNumber value="${info.standAloneBoothFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.assemblyBoothCnt > 0}">
                                                <c:set var="boothRownum" value="${boothRownum + 1}"/>
                                                <tr>
                                                    <td>${boothRownum}</td>
                                                    <td>조립부스</td>
                                                    <td>${info.assemblyBoothCnt}</td>
                                                    <td>2,100,000</td>
                                                    <td><fmt:formatNumber value="${info.assemblyBoothFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.onlineBoothCnt > 0}">
                                                <c:set var="boothRownum" value="${boothRownum + 1}"/>
                                                <tr>
                                                    <td>${boothRownum}</td>
                                                    <td>온라인부스</td>
                                                    <td>${info.onlineBoothCnt}</td>
                                                    <td>1,000,000</td>
                                                    <td><fmt:formatNumber value="${info.onlineBoothFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <tr class="txtBold">
                                                <td colspan="6">총액 (VAT 미포함)</td>
                                                <td colspan="1"><fmt:formatNumber value="${info.boothPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!-- 전시부스 신청 -->

                            <!-- 부스할인 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">부스할인</div>
                                </div>
                                <div class="form_total_table form_total_disc">
                                    <div class="table_box">
                                        <table>
                                            <colgroup>
                                                <col width="10%">
                                                <col width="15%">
                                                <col>
                                                <col width="10%">
                                                <col width="20%">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>구분</th>
                                                <th>단가</th>
                                                <th>신청개수</th>
                                                <th>할인 합계</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <c:set var="boothCnt" value="${info.standAloneBoothCnt + info.assemblyBoothCnt + info.onlineBoothCnt}"/>
                                            <c:set var="discountRownum" value="0"/>
                                            <c:if test="${info.discountEarly1}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>1차 조기신청</td>
                                                    <td>1 부스당 300,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 300000}"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.discountEarly2}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>2차 조기신청</td>
                                                    <td>1 부스당 200,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 200000}"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.discountReAll}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>재참가</td>
                                                    <td>1 부스당 200,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 200000}"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.discountScale2}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>규모 2 (20부스 이상)</td>
                                                    <td>1 부스당 200,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 300000}"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.discountScale3}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>규모 3 (40부스 이상)</td>
                                                    <td>1 부스당 450,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 450000}"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.discountFirst}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>첫 참가</td>
                                                    <td>1 부스당 500,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 500000}"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.discountLeisure}">
                                                <c:set var="discountRownum" value="${discountRownum + 1}"/>
                                                <tr>
                                                    <td>${discountRownum}</td>
                                                    <td>한국해양레저산업협회</td>
                                                    <td>1 부스당 200,000 원 할인</td>
                                                    <td><c:out value="${boothCnt}"/></td>
                                                    <td>- <fmt:formatNumber value="${boothCnt * 200000}"/></td>
                                                </tr>
                                            </c:if>
                                            <tr class="txtBold">
                                                <td colspan="4">총액</td>
                                                <td>- <fmt:formatNumber value="${info.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!-- 부스할인 -->

                            <!-- 유틸리티 신청 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">유틸리티 신청</div>
                                </div>
                                <div class="form_total_table form_total_util">
                                    <div class="table_box">
                                        <table>
                                            <colgroup>
                                                <col width="5%">
                                                <col width="12%">
                                                <col width="8%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col>
                                                <col>
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>품목</th>
                                                <th>신청수량</th>
                                                <th>단가</th>
                                                <th>합계</th>
                                                <th>등록일</th>
                                                <th>수정일</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="utilityRownum" value="0"/>
                                            <c:if test="${info.utilityJuganCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>주간 단상 220V</td>
                                                    <td>${info.utilityJuganCnt}</td>
                                                    <td>￦ 70,000/1kw</td>
                                                    <td><fmt:formatNumber value="${info.utilityJuganFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityDayCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>24시간용 220V</td>
                                                    <td>${info.utilityDayCnt}</td>
                                                    <td>￦ 80,000/1kw</td>
                                                    <td><fmt:formatNumber value="${info.utilityDayFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityCompressedAirCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>압축공기 기본형</td>
                                                    <td>${info.utilityCompressedAirCnt}</td>
                                                    <td>￦ 180,000</td>
                                                    <td><fmt:formatNumber value="${info.utilityCompressedAirFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityWaterBasicCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>급배수 기본형</td>
                                                    <td>${info.utilityWaterBasicCnt}</td>
                                                    <td>￦ 180,000</td>
                                                    <td><fmt:formatNumber value="${info.utilityWaterBasicFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityInternetCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>인터넷</td>
                                                    <td>${info.utilityInternetCnt}</td>
                                                    <td>￦ 160,000</td>
                                                    <td><fmt:formatNumber value="${info.utilityInternetFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityPytexNewCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>파이텍스 (신품)</td>
                                                    <td>${info.utilityPytexNewCnt}</td>
                                                    <td>￦ 80,000</td>
                                                    <td><fmt:formatNumber value="${info.utilityPytexNewFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityPytexReCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>파이텍스 (재사용품)</td>
                                                    <td>${info.utilityPytexReCnt}</td>
                                                    <td>￦ 50,000</td>
                                                    <td><fmt:formatNumber value="${info.utilityPytexReFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${info.utilityBarcodeCnt > 0}">
                                                <c:set var="utilityRownum" value="${utilityRownum + 1}"/>
                                                <tr>
                                                    <td>${utilityRownum}</td>
                                                    <td>참관객/바이어 바코드 리더기</td>
                                                    <td>${info.utilityBarcodeCnt}</td>
                                                    <td>￦ 200,000</td>
                                                    <td><fmt:formatNumber value="${info.utilityBarcodeFee}"/></td>
                                                    <td>${info.initRegiDttm}</td>
                                                    <td>${info.finalRegiDttm}</td>
                                                </tr>
                                            </c:if>

                                            <tr class="txtBold">
                                                <td colspan="6">총액</td>
                                                <td colspan="1"><fmt:formatNumber value="${info.utilityPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
                                            </tr>
                                            <tr class="txtBold">
                                                <td colspan="6">입금액</td>
                                                <td colspan="1"><fmt:formatNumber value="${info.deposit}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
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
                                    <div class="big">초청희망바이어</div>
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
                                                <th>국가</th>
                                                <th>회사명</th>
                                                <th>바이어명</th>
                                                <th>부서</th>
                                                <th>직책</th>
                                                <th>이메일</th>
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
                                            <tr>
                                                <td class="bgBlue">공급가액</td>
                                                <td><fmt:formatNumber value="${info.prcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
                                            </tr>
                                            <tr>
                                                <td class="bgBlue">신청금액</td>
                                                <td><fmt:formatNumber value="${info.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/> (VAT 포함)</td>
                                            </tr>
                                            <tr class="txtBold">
                                                <td class="bgBlue">최종 결제 금액</td>
                                                <td class="txtBlue" style="font-size: 2rem;"><fmt:formatNumber value="${info.prcTotal}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
                                            </tr>
                                            <tr class="txtBold">
                                                <td class="bgBlue" rowspan="2">결제내역</td>
                                                <td>입금 : <fmt:formatNumber value="${info.deposit}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
                                            </tr>
                                            <tr class="txtBold">
                                                <td class="txtRed">잔금 : <fmt:formatNumber value="${info.prcTotal - info.deposit}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></td>
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
                        <div class="tit">회원 탈퇴 안내</div>
                        <a class="close" href="#close"><img src="/img/close_w.png"></a>
                    </div>
                    <div class="pop_box">
                        <div class="pop_cont">
                            <!-- 탈퇴안내 -->
                            <ul class="out_guide">
                                <li>사용하고 계신 아이디(meetingfan)는 탈퇴할 경우 재사용 및 복구가 불가능합니다.</li>
                                <li>탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가능하오니 신중하게 선택하시기 바랍니다.</li>
                                <li>탈퇴 후 회원정보 및 서비스 이용기록은 모두 삭제됩니다.</li>
                            </ul>
                            <div class="out_check">
                                <label>
                                    <input type="checkbox">위 안내 사항을 모두 확인하였으며, 이에 동의합니다.
                                </label>
                            </div>
                            <div class="btn_box">
                                <%--<a href="" class="btnSt01">탈퇴하기</a>--%>
                                <a href="" class="close btnSt03">취소</a>
                            </div>
                            <!-- //탈퇴안내 -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    </c:if>
</body>
</html>