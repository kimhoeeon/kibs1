<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<!--
Author: Keenthemes
Product Name: Metronic
Product Version: 8.1.8
Purchase: https://1.envato.market/EA4JP
Website: http://www.keenthemes.com
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
License: For each use you must have a valid license purchased only from above link in order to legally use the theme for your project.
-->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<!--begin::Head-->
<head>
    <base href="/mng/"/>
    <title>2026 경기국제보트쇼 관리자</title>
    <meta charset="utf-8"/>
    <meta name="description" content="2026 경기국제보트쇼 홈페이지"/>
    <meta name="keywords" content="KIBS"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta property="og:locale" content="ko_KR"/>
    <meta property="og:type" content="article"/>
    <meta property="og:title" content="KIBS"/>
    <meta property="og:url" content="https://koreaboatshow.or.kr/"/>
    <meta property="og:site_name" content="2026 KIBS | 경기국제보트쇼"/>
    <link rel="canonical" href="https://koreaboatshow.or.kr/"/>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <!--begin::Fonts(mandatory for all pages)-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Inter:300,400,500,600,700"/>
    <!--end::Fonts-->
    <!--begin::Vendor Stylesheets(used for this page only)-->

    <link href="/assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css"/>
    <!--end::Vendor Stylesheets-->
    <!--begin::Global Stylesheets Bundle(mandatory for all pages)-->
    <link href="/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css"/>
    <link href="/assets/css/style.bundle.css" rel="stylesheet" type="text/css"/>
    <!--end::Global Stylesheets Bundle-->

    <!--begin::custom Mng css-->
    <link href="/css/mngStyle.css" rel="stylesheet" type="text/css"/>
    <!--end::custom Mng css-->
</head>
<!--end::Head-->
<!--begin::Body-->
<body id="kt_app_body" data-exhibitor-seq="${info.seq}" data-company-name-ko="${info.companyNameKo}" data-member-yn="${info.memberCompanyYn}"
      data-kt-app-layout="dark-sidebar" data-kt-app-header-fixed="true"
      data-kt-app-sidebar-enabled="true" data-kt-app-sidebar-fixed="true" data-kt-app-sidebar-hoverable="true"
      data-kt-app-sidebar-push-header="true" data-kt-app-sidebar-push-toolbar="true"
      data-kt-app-sidebar-push-footer="true" data-kt-app-toolbar-enabled="true" class="app-default">
<!--begin::Theme mode setup on page load-->
<script>var defaultThemeMode = "light";
var themeMode;
if (document.documentElement) {
    if (document.documentElement.hasAttribute("data-bs-theme-mode")) {
        themeMode = document.documentElement.getAttribute("data-bs-theme-mode");
    } else {
        if (localStorage.getItem("data-bs-theme") !== null) {
            themeMode = localStorage.getItem("data-bs-theme");
        } else {
            themeMode = defaultThemeMode;
        }
    }
    if (themeMode === "system") {
        themeMode = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
    }
    document.documentElement.setAttribute("data-bs-theme", themeMode);
}</script>
<!--end::Theme mode setup on page load-->

<!--begin::login check-->
<c:if test="${sessionScope.get('status') ne 'logon'}">
    <script>
        alert("로그인해 주세요.");
        location.href = '/mng/index.do';
    </script>
</c:if>

<c:if test="${sessionScope.get('status') eq 'logon'}">

    <!--begin::Page loading(append to body)-->
    <div class="page-loader flex-column bg-dark bg-opacity-25">
        <span class="spinner-border text-primary" role="status"></span>
        <span class="text-gray-800 fs-6 fw-semibold mt-5">Loading...</span>
    </div>
    <!--end::Page loading-->

    <!--begin::App-->
    <div class="d-flex flex-column flex-root app-root" id="kt_app_root">
        <!--begin::Page-->
        <div class="app-page flex-column flex-column-fluid" id="kt_app_page">
            <!--begin::Header-->
            <jsp:include page="/WEB-INF/views/mng/layout/header.jsp" flush="true" />
            <!--end::Header-->
            <!--begin::Wrapper-->
            <div class="app-wrapper flex-column flex-row-fluid" id="kt_app_wrapper">
                <!--begin::Sidebar-->
                <jsp:include page="/WEB-INF/views/mng/layout/sidebar.jsp" flush="true" />
                <!--end::Sidebar-->
                <!--begin::Main-->
                <div class="app-main flex-column flex-row-fluid" id="kt_app_main">
                    <!--begin::Content wrapper-->
                    <div class="d-flex flex-column flex-column-fluid">
                        <!--begin::Toolbar-->
                        <div id="kt_app_toolbar" class="app-toolbar py-3 py-lg-6">
                            <!--begin::Toolbar container-->
                            <div id="kt_app_toolbar_container" class="app-container container-full d-flex flex-stack">
                                <!--begin::Page title-->
                                <div class="page-title d-flex flex-column justify-content-center flex-wrap me-3">
                                    <!--begin::Title-->
                                    <h1 class="page-heading d-flex text-dark fw-bold fs-3 flex-column justify-content-center my-0">
                                        전시부스 신청</h1>
                                    <!--end::Title-->
                                    <!--begin::Breadcrumb-->
                                    <ul class="breadcrumb breadcrumb-separatorless fw-semibold fs-7 my-0 pt-1">
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">
                                            <a href="/mng/main.do" class="text-muted text-hover-primary">Home</a>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">참가신청서 관리 (2027~)</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">전시부스 신청</li>
                                        <!--end::Item-->
                                    </ul>
                                    <!--end::Breadcrumb-->
                                </div>
                                <!--end::Page title-->
                                <!--begin::Actions-->
                                <div class="d-flex align-items-center gap-2 gap-lg-3">
                                    <!--begin::Filter menu-->
                                    <!--end::Filter menu-->
                                    <!--begin::Secondary button-->
                                    <!--end::Secondary button-->
                                    <!--begin::Primary button-->
                                    <!--end::Primary button-->
                                </div>
                                <!--end::Actions-->
                            </div>
                            <!--end::Toolbar container-->
                        </div>
                        <!--end::Toolbar-->
                        <!--begin::Content-->
                        <div id="kt_app_content" class="app-content flex-column-fluid">
                            <!--begin::Content container-->
                            <div id="kt_app_content_container" class="app-container container-full">

                                <!--begin::Form-->
                                <form id="boothForm" name="boothForm" method="post" onsubmit="return false;">
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">업체 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_company_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <h4 class="fw-bold">${info.companyNameKo} / ${info.companyNameEn}</h4>
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->

                                        <%-- 계산 API 호출 시 필요한 기본 정보 --%>
                                    <input type="hidden" id="registrationCnt" value="${info.registrationCnt}"/> <%-- 등록비 포함 여부 (보통 1) --%>
                                    <input type="hidden" id="baseBoothStandAloneCnt" value="${info.standAloneBoothCnt}"/> <%-- 독립부스 수 --%>
                                    <input type="hidden" id="baseBoothAssemblyCnt" value="${info.assemblyBoothCnt}"/> <%-- 조립부스 수 --%>
                                    <%--<input type="hidden" id="baseBoothOnlineCnt" value="${info.onlineBoothCnt}"/>--%> <%-- 온라인부스 수 --%>
                                    <input type="hidden" id="baseUtilitySum" value="${info.utilityPrcSum}"/>  <%-- 유틸리티 총액 (기존에 이미 있음) --%>
                                    <input type="hidden" id="deposit" value="${info.deposit}"/> <%-- 선납금 --%>

                                        <%-- 기본 할인 적용 여부 (DB 값 그대로 전달) --%>
                                    <input type="hidden" id="discountEarly1Checked" value="${info.discountEarly1}"/>
                                    <input type="hidden" id="discountEarly2Checked" value="${info.discountEarly2}"/>
                                    <input type="hidden" id="discountFirstUnder10Checked" value="${info.discountFirstUnder10}"/>
                                    <input type="hidden" id="discountFirstOver10Checked" value="${info.discountFirstOver10}"/>
                                    <input type="hidden" id="discountReChecked" value="${info.discountRe}"/>
                                    <input type="hidden" id="discountScale1Checked" value="${info.discountScale1}"/>
                                    <input type="hidden" id="discountScale2Checked" value="${info.discountScale2}"/>
                                    <input type="hidden" id="discountScale3Checked" value="${info.discountScale3}"/>
                                    <input type="hidden" id="discountScale4Checked" value="${info.discountScale4}"/>
                                    <input type="hidden" id="discountScale5Checked" value="${info.discountScale5}"/>
                                    <input type="hidden" id="discountScale6Checked" value="${info.discountScale6}"/>
                                    <input type="hidden" id="discountLeisureChecked" value="${info.discountLeisure}"/>
                                    <input type="hidden" id="discountLeisureChecked" value="${info.discountLeisure}"/>
                                    <input type="hidden" id="prcSumData" name="prcSum" value=""/>
                                    <input type="hidden" id="prcVatData" name="prcVat" value=""/>
                                    <input type="hidden" id="prcTotalData" name="prcTotal" value=""/>
                                    <input type="hidden" id="balanceData" name="balance" value=""/>
                                    <input type="hidden" id="discountPrcSumData" name="discountPrcSum" value="${info.discountPrcSum}"/>

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">등록비 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_booth_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Table container-->
                                                <div class="table-responsive">
                                                    <!--begin::Table-->
                                                    <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border gy-7 gs-7">
                                                        <!--begin::Table head-->
                                                        <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                            <tr class="text-center">
                                                                <th class="w-150px">구분</th>
                                                                <th class="w-150px">단가</th>
                                                                <th class="w-150px">신청수량</th>
                                                                <th class="w-175px">금액 (원)</th>
                                                                <th>비고</th>
                                                            </tr>
                                                        </thead>
                                                        <!--end::Table head-->
                                                        <!--begin::Table body-->
                                                        <tbody>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <div class="cate">
                                                                        <span class="fw-semibold d-block fs-7">등록비</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="cost">
                                                                        <span class="fw-semibold d-block fs-7">￦ 100,000</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="quantity">
                                                                        <p class="j_num">
                                                                            <input type="text" id="registrationCnt" placeholder="수량 입력" class="form-control form-control-lg form-control-solid-bg" value="1" readonly>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="amount">
                                                                        <p class="price">
                                                                            <input type="text" id="registrationFee" value="￦ 100,000" style="background:unset; border: 0; text-align: center;" disabled>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        2019년부터 참가업체의 홍보 지원을 위하여 업체당 참가신청 등록비를 받습니다.<br>
                                                                        등록비는 참가업체의 각종 온라인 홍보 및 대외 홍보에 사용됩니다.
                                                                    </span>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                        <!--end::Table body-->
                                                    </table>
                                                    <!--end::Table-->
                                                </div>
                                                <!--begin::Table container-->
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->

                                    <input type="hidden" id="memberCompanyYn" value="${info.memberCompanyYn}"/>
                                    <c:if test="${info.memberCompanyYn == 'Y'}">
                                        <!--begin::Basic info-->
                                        <div class="card mb-5 mb-xl-10">
                                            <!--begin::Card header-->
                                            <div class="card-header border-0">
                                                <!--begin::Card title-->
                                                <div class="card-title m-0">
                                                    <h3 class="fw-bold m-0">한국해양레저산업협회 발전기금</h3>
                                                </div>
                                                <!--end::Card title-->
                                            </div>
                                            <!--begin::Card header-->
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Table container-->
                                                <div class="table-responsive">
                                                    <!--begin::Table-->
                                                    <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border">
                                                        <!--begin::Table head-->
                                                        <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                        <tr class="text-center">
                                                            <th class="w-150px">구분</th>
                                                            <th class="w-175px">금액 (원)</th>
                                                            <th>비고</th>
                                                        </tr>
                                                        </thead>
                                                        <!--end::Table head-->
                                                        <!--begin::Table body-->
                                                        <tbody>
                                                        <tr class="text-center align-middle">
                                                            <td>
                                                                <div class="cate">
                                                                    <span class="fw-semibold d-block fs-7">한국해양레저산업협회<br>발전기금</span>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="amount">
                                                                    <p class="price">
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
                                                                        <input type="text" value="<fmt:formatNumber value="${(info.boothPrcSum - (info.discountPrcSum + baseSpecialDiscount)) * 0.1}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" style="background:unset; border: 0; text-align: center; color: #000000;" disabled>
                                                                    </p>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <span class="fw-semibold d-block fs-7">
                                                                    참가비 내역 합계(공급가)의 10%
                                                                </span>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                        <!--end::Table body-->
                                                    </table>
                                                    <!--end::Table-->
                                                </div>
                                                <!--begin::Table container-->
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Basic info-->
                                    </c:if>

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">참가비 내역</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Card body-->
                                        <div class="card-body border-top p-9">
                                            <!--begin::Table container-->
                                            <div class="table-responsive">
                                                <!--begin::Table-->
                                                <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border gy-7 gs-7">
                                                    <!--begin::Table head-->
                                                    <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                        <tr class="text-center">
                                                            <th class="w-150px">구분</th>
                                                            <th class="w-150px">단가</th>
                                                            <th class="w-150px">신청수량</th>
                                                            <th class="w-175px">금액 (원)</th>
                                                        </tr>
                                                    </thead>
                                                    <!--end::Table head-->
                                                    <!--begin::Table body-->
                                                    <tbody>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">독립부스</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 1,800,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.standAloneBoothCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">조립부스</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 2,100,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.assemblyBoothCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <%--<tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">온라인부스</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 1,000,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.onlineBoothCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.onlineBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>--%>
                                                    </tbody>
                                                    <!--end::Table body-->
                                                </table>
                                                <!--end::Table-->
                                            </div>
                                            <!--begin::Table container-->
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">할인액 내역</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Card body-->
                                        <div class="card-body border-top p-9">
                                            <div class="table-responsive mt-5">
                                                <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border">
                                                    <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                    <tr class="text-center"><th colspan="4">기본 할인 내역</th></tr>
                                                    <tr class="text-center">
                                                        <th style="width: 8%;">선택</th>
                                                        <th style="width: 15%;">구분</th>
                                                        <th>할인금액(부스당)</th>
                                                        <th style="width: 20%;">내용</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountEarly1" data-discount="300000" <c:if test="${info.discountEarly1}">checked</c:if> disabled></td>
                                                        <td rowspan="2">조기신청 할인</td>
                                                        <td>(1차) 조기신청 할인 (~2025.11.14 금)</td>
                                                        <td>300,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountEarly2" data-discount="200000" <c:if test="${info.discountEarly2}">checked</c:if> disabled></td>
                                                        <td>(2차) 조기신청 할인 (~2025.12.12 금)</td>
                                                        <td>200,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountScale1" data-discount="400000" <c:if test="${info.discountScale1}">checked</c:if> disabled></td>
                                                        <td rowspan="6">규모 할인</td>
                                                        <td>10부스 이상 참가업체</td>
                                                        <td>400,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountScale2" data-discount="650000" <c:if test="${info.discountScale2}">checked</c:if> disabled></td>
                                                        <td>20부스 이상 참가업체</td>
                                                        <td>650,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountScale3" data-discount="750000" <c:if test="${info.discountScale3}">checked</c:if> disabled></td>
                                                        <td>30부스 이상 참가업체</td>
                                                        <td>750,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountScale4" data-discount="800000" <c:if test="${info.discountScale4}">checked</c:if> disabled></td>
                                                        <td>40부스 이상 참가업체</td>
                                                        <td>800,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountScale5" data-discount="850000" <c:if test="${info.discountScale5}">checked</c:if> disabled></td>
                                                        <td>50부스 이상 참가업체</td>
                                                        <td>850,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountScale6" data-discount="900000" <c:if test="${info.discountScale6}">checked</c:if> disabled></td>
                                                        <td>100부스 이상 참가업체</td>
                                                        <td>900,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountRe" data-discount="200000" <c:if test="${info.discountRe}">checked</c:if> disabled></td>
                                                        <td>재참가 할인</td>
                                                        <td>2015년 ~ 2025년 참가 업체</td>
                                                        <td>200,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountFirstUnder10" data-discount="500000" <c:if test="${info.discountFirstUnder10}">checked</c:if> disabled></td>
                                                        <td rowspan="2">첫참가 할인</td>
                                                        <td>최초 참가업체 (10부스 미만 참가)</td>
                                                        <td>500,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountFirstOver10" data-discount="300000" <c:if test="${info.discountFirstOver10}">checked</c:if> disabled></td>
                                                        <td>최초 참가업체 (10부스 이상 참가)</td>
                                                        <td>300,000 원</td>
                                                    </tr>
                                                    <tr class="text-center align-middle">
                                                        <td><input type="checkbox" class="form-check-input basic-discount" id="discountLeisure" data-discount="200000" <c:if test="${info.discountLeisure}">checked</c:if> disabled></td>
                                                        <td>협회 할인</td>
                                                        <td>한국해양레저산업협회 회원사</td>
                                                        <td>200,000 원</td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">특별 할인</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_discount_special_info">
                                            <div class="card-body border-top p-9">
                                                <div class="table-responsive">
                                                    <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border">
                                                        <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                        <tr class="text-center">
                                                            <th style="width: 8%;">선택</th>
                                                            <th style="width: 25%;">할인 사유</th>
                                                            <th style="width: 25%;">할인 금액(단위: 원)</th>
                                                            <th>비고</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" id="discountSpecial1Yn" class="form-check-input special-discount-checkbox" <c:if test="${info.discountSpecial1Yn}">checked</c:if>></td>
                                                            <td>올해의 제품상 할인</td>
                                                            <td>50%</td>
                                                            <td><input type="text" id="discountSpecial1Note" value="${info.discountSpecial1Note}" class="form-control form-control-solid-bg"/></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" id="discountSpecial2Yn" class="form-check-input special-discount-checkbox" <c:if test="${info.discountSpecial2Yn}">checked</c:if>></td>
                                                            <td><input type="text" id="discountSpecial2Reason" value="${info.discountSpecial2Reason}" placeholder="할인 사유 입력" class="form-control form-control-solid-bg"/></td>
                                                            <td><input type="text" id="discountSpecial2Amount" value="${info.discountSpecial2Amount}" placeholder="할인 금액 입력" class="form-control form-control-solid-bg special-discount-amount"/></td>
                                                            <td><input type="text" id="discountSpecial2Note" value="${info.discountSpecial2Note}" class="form-control form-control-solid-bg"/></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" id="discountSpecial3Yn" class="form-check-input special-discount-checkbox" <c:if test="${info.discountSpecial3Yn}">checked</c:if>></td>
                                                            <td><input type="text" id="discountSpecial3Reason" value="${info.discountSpecial3Reason}" placeholder="할인 사유 입력" class="form-control form-control-solid-bg"/></td>
                                                            <td><input type="text" id="discountSpecial3Amount" value="${info.discountSpecial3Amount}" placeholder="할인 금액 입력" class="form-control form-control-solid-bg special-discount-amount"/></td>
                                                            <td><input type="text" id="discountSpecial3Note" value="${info.discountSpecial3Note}" class="form-control form-control-solid-bg"/></td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <div class="card-body p-9">
                                            <%-- JavaScript에서 계산을 위해 기본값들을 hidden 필드로 저장 --%>
                                            <input type="hidden" id="baseBoothSum" value="${info.boothPrcSum}" />
                                            <input type="hidden" id="baseUtilitySum" value="${info.utilityPrcSum}" />
                                            <input type="hidden" id="baseDiscountSum" value="${info.discountPrcSum}" />

                                            <h3 class="fw-bold m-0 mb-5">계산서</h3>

                                            <div class="table-responsive">
                                                <table class="table table-row-bordered table-row-gray-300 gy-4">
                                                    <tbody>
                                                    <tr>
                                                        <th class="text-gray-700 fw-semibold fs-6" style="width: 70%;">부스 총액 + 등록비</th>
                                                        <td id="summary_booth_total" class="text-end fw-bold fs-6"></td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-700 fw-semibold fs-6">기본 할인</th>
                                                        <td id="summary_basic_discount" class="text-end fw-bold fs-6 text-danger"></td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-700 fw-semibold fs-6">특별 할인</th>
                                                        <td id="summary_special_discount" class="text-end fw-bold fs-6 text-danger"></td>
                                                    </tr>

                                                    <c:if test="${info.memberCompanyYn == 'Y' || info.discountLeisure}">
                                                        <tr id="developmentFundRow">
                                                            <th class="text-gray-700 fw-semibold fs-6">한국해양레저산업협회 발전기금</th>
                                                            <td id="summary_development_fund" class="text-end fw-bold fs-6 text-success"></td>
                                                        </tr>
                                                    </c:if>

                                                    <tr class="border-top border-gray-300 border-top-dashed">
                                                        <th class="text-gray-800 fw-bolder fs-5">소계 (공급가액)</th>
                                                        <td id="summary_booth_subtotal" class="text-end fw-bolder fs-5"></td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-800 fw-bolder fs-5">부가세 (VAT)</th>
                                                        <td id="summary_booth_vat" class="text-end fw-bolder fs-5"></td>
                                                    </tr>
                                                    <tr class="bg-light-primary">
                                                        <th class="text-primary fw-bolder fs-4">최종 합계 (총액)</th>
                                                        <td id="summary_booth_final_total" class="text-end text-primary fw-bolder fs-4"></td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitorNewNew/application/booth.do" class="btn btn-info btn-active-light-info cursor-pointer">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_application_booth_new_modify_init_set('${info.seq}')" class="btn btn-danger btn-active-light-danger">변경내용취소</button>
                                                <button type="button" id="saveSpecialDiscountBtn" class="btn btn-primary btn-active-light-primary ms-2 me-2">변경내용저장</button>
                                                <button type="button" class="btn btn-dark btn-active-light-dark" data-bs-toggle="modal" data-bs-target="#kt_modal_create_invoice">인보이스 생성</button>
                                            </div>
                                        </div>
                                        <!--end::Actions-->
                                    </div>
                                    <!--end::Basic info-->
                                </form>
                                <!--end::form-->

                                <!--begin::Basic info-->
                                <div class="card mb-5 mb-xl-10">
                                    <!--begin::Card header-->
                                    <div class="card-header border-0">
                                        <!--begin::Card title-->
                                        <div class="card-title m-0">
                                            <h3 class="fw-bold m-0">인보이스</h3>
                                        </div>
                                        <!--end::Card title-->
                                        <div class="card-toolbar">
                                            <span class="text-muted me-2">발송 대상 이메일:</span>
                                            <span class="fw-bold">
                                                <input type="hidden" id="defaultEmail" value="${info.email}"/>
                                                ${info.email}
                                            </span>
                                        </div>
                                    </div>
                                    <!--begin::Card header-->
                                    <!--begin::Content-->
                                    <div id="kt_invoice_info">
                                        <!--begin::Card body-->
                                        <div class="card-body border-top p-9">
                                            <!--begin::Table container-->
                                            <div class="table-responsive">
                                                <!--begin::Table-->
                                                <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border gy-7 gs-7">
                                                    <!--begin::Table head-->
                                                    <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                        <tr class="text-center">
                                                            <th>선택</th>
                                                            <th>NO</th>
                                                            <th>제목</th>
                                                            <th>발송유형</th>
                                                            <th>발행일시</th>
                                                            <th>발송이력</th>
                                                        </tr>
                                                    </thead>
                                                    <!--end::Table head-->
                                                    <!--begin::Table body-->
                                                    <tbody>
                                                        <c:if test="${empty boothInvoiceList}">
                                                            <tr><td colspan="6" style="text-align: center;">인보이스 내역 없음</td></tr>
                                                        </c:if>

                                                        <c:if test="${not empty boothInvoiceList}">
                                                            <c:forEach var="invoice" items="${boothInvoiceList}">
                                                                <tr class="text-center align-middle">
                                                                    <td>
                                                                        <%-- data-type="booth" 추가 --%>
                                                                        <span class="fw-semibold d-block fs-7 invoice-item">
                                                                        <input type="hidden" name="filePath" value="${invoice.filePath}"/>
                                                                        <input type="checkbox" name="invoiceSeq" value="${invoice.invoiceSeq}" data-type="booth" class="form-check-input">
                                                                    </span>
                                                                    </td>
                                                                    <td class="td_invoiceCode">${invoice.invoiceCode}</td>
                                                                    <td>${invoice.title}</td>
                                                                    <td>전시부스</td>
                                                                    <td><span class="fw-semibold d-block fs-7">${fn:substring(invoice.initRegiDttm, 0, 16)}</span></td>
                                                                    <td>
                                                                        <button type="button" class="btn btn-sm btn-light-info view-history-btn"
                                                                                data-invoice-seq="${invoice.invoiceSeq}"
                                                                                data-invoice-type="booth"
                                                                                data-invoice-code="${invoice.invoiceCode}">이력보기
                                                                        </button>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:if>
                                                    </tbody>
                                                    <!--end::Table body-->
                                                </table>
                                                <!--end::Table-->
                                            </div>
                                            <!--begin::Table container-->
                                        </div>
                                        <!--end::Card body-->
                                        <!--begin::Basic info-->
                                        <div class="card-footer d-flex justify-content-end py-6 px-9">
                                            <div>
                                                <button type="button" id="deleteInvoicesBtn" class="btn btn-danger btn-active-light-danger">삭제</button>
                                                <button type="button" id="previewBtn" class="btn btn-primary btn-active-light-primary ms-2 me-2">미리보기</button>
                                                <button type="button" id="sendInvoicesBtn" class="btn btn-dark btn-active-light-dark">발송</button>
                                            </div>
                                        </div>
                                        <!--end::Basic info-->
                                    </div>
                                    <!--end::Content-->
                                </div>
                                <!--end::Basic info-->

                            </div>
                            <!--end::Content container-->
                        </div>
                        <!--end::Content-->
                    </div>
                    <!--end::Content wrapper-->

                    <!--begin::Footer-->
                    <div id="kt_app_footer" class="app-footer">
                        <!--begin::Footer container-->
                        <div class="app-container container-fluid d-flex flex-column flex-md-row flex-center flex-md-stack py-3">
                            <!--begin::Copyright-->
                            <div class="text-dark order-2 order-md-1">
                                    <%--<span class="text-muted fw-semibold me-1">2023&copy;</span>
                                    <a href="https://keenthemes.com" target="_blank"
                                       class="text-gray-800 text-hover-primary">Keenthemes</a>--%>
                            </div>
                            <!--end::Copyright-->
                            <!--begin::Menu-->
                            <ul class="menu menu-gray-600 menu-hover-primary fw-semibold order-1">
                                    <%--<li class="menu-item">
                                        <a href="https://keenthemes.com" target="_blank" class="menu-link px-2">About</a>
                                    </li>
                                    <li class="menu-item">
                                        <a href="https://devs.keenthemes.com" target="_blank"
                                           class="menu-link px-2">Support</a>
                                    </li>
                                    <li class="menu-item">
                                        <a href="https://1.envato.market/EA4JP" target="_blank" class="menu-link px-2">Purchase</a>
                                    </li>--%>
                            </ul>
                            <!--end::Menu-->
                        </div>
                        <!--end::Footer container-->
                    </div>
                    <!--end::Footer-->
                </div>
                <!--end:::Main-->
            </div>
            <!--end::Wrapper-->
        </div>
        <!--end::Page-->
    </div>
    <!--end::App-->

    <!--begin::Scrolltop-->
    <div id="kt_scrolltop" class="scrolltop" data-kt-scrolltop="true">
        <i class="ki-duotone ki-arrow-up">
            <span class="path1"></span>
            <span class="path2"></span>
        </i>
    </div>
    <!--end::Scrolltop-->

    <div class="modal fade" id="kt_modal_create_invoice" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered mw-450px">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="fw-bold">인보이스 생성</h2>
                    <div class="btn btn-icon btn-sm btn-active-icon-primary" data-bs-dismiss="modal">
                        <i class="ki-duotone ki-cross fs-1">
                            <span class="path1"></span>
                            <span class="path2"></span>
                        </i>
                    </div>
                </div>
                <div class="modal-body py-10 px-lg-17">
                    <div class="mb-10">
                        <label for="invoiceTypeSelect" class="form-label fw-semibold">인보이스 종류:</label>
                        <select id="invoiceTypeSelect" class="form-select form-select-solid">
                            <option value="booth">전시부스</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer flex-center">
                    <button type="button" class="btn btn-light me-3" data-bs-dismiss="modal">취소</button>
                    <button type="button" id="createInvoiceConfirmBtn" data-company-name="${info.companyNameKo}" class="btn btn-primary">생성</button>
                </div>
            </div>
        </div>
    </div>

    <!--begin::Modal - 수정이력-->
    <div class="modal fade" id="kt_modal_invoice_form" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div class="modal-dialog modal-dialog-centered" style="max-width: 840px;">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">인보이스 정보</h2>
                    <!--end::Modal title-->

                    <div>
                        <%-- 다운로드와 프린트 버튼 추가 --%>
                        <div id="invoiceDownloadBtn" class="btn btn-sm btn-icon btn-active-color-primary" title="PDF 다운로드">
                            <i class="ki-duotone ki-file-down fs-1">
                                <span class="path1"></span>
                                <span class="path2"></span>
                            </i>
                        </div>
                        <div id="invoicePrintBtn" class="btn btn-sm btn-icon btn-active-color-primary" title="인쇄">
                            <i class="ki-duotone ki-printer fs-1">
                                <span class="path1"></span>
                                <span class="path2"></span>
                                <span class="path3"></span>
                                <span class="path4"></span>
                                <span class="path5"></span>
                            </i>
                        </div>
                        <%-- ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ --%>
                        <!--begin::Btn-->
                        <div class="btn btn-sm btn-icon btn-active-color-primary" data-bs-dismiss="modal">
                            <i class="ki-duotone ki-cross fs-1">
                                <span class="path1"></span>
                                <span class="path2"></span>
                            </i>
                        </div>
                        <!--end::Btn-->
                    </div>
                </div>
                <!--end::Modal header-->
                <!--begin::Modal body-->
                <div class="modal-body py-lg-5 px-lg-5">
                    <div class="card card-flush">
                        <iframe id="detailForm" name="detailForm" width="100%" height="700" allowtransparency="true"></iframe>
                    </div>
                </div>
                <!--end::Modal body-->
            </div>
            <!--end::Modal content-->
        </div>
        <!--end::Modal dialog-->
    </div>
    <!--end::Modal - 수정이력-->

    <div class="modal fade" id="kt_modal_send_invoice" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered mw-500px">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="fw-bold">인보이스 발송</h2>
                    <div class="btn btn-icon btn-sm btn-active-icon-primary" data-bs-dismiss="modal">
                        <i class="ki-duotone ki-cross fs-1"><span class="path1"></span><span class="path2"></span></i>
                    </div>
                </div>
                <div class="modal-body py-10 px-lg-17">
                    <input type="hidden" id="sendInvoiceSeq" />
                    <input type="hidden" id="sendInvoiceType" />
                    <input type="hidden" id="sendInvoiceFilePath" />
                    <input type="hidden" id="sendInvoiceCode" />
                    <input type="hidden" id="sendInvoiceIssueDate" />

                    <div class="mb-5">
                        <div class="fs-6 text-gray-600">아래 이메일 주소로 인보이스를 발송합니다.</div>
                        <div id="sendInvoiceCodeDisplay" class="fs-5 fw-bold text-gray-800"></div>
                    </div>

                    <div class="fv-row">
                        <label for="sendInvoiceEmailInput" class="form-label fw-semibold">수신 이메일 주소:</label>
                        <input type="email" id="sendInvoiceEmailInput" class="form-control form-control-solid" placeholder="name@example.com"/>
                    </div>
                </div>
                <div class="modal-footer flex-center">
                    <button type="button" class="btn btn-light me-3" data-bs-dismiss="modal">취소</button>
                    <button type="button" id="sendInvoiceConfirmBtn" class="btn btn-primary">발송</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="kt_modal_send_history" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="fw-bold">인보이스 발송 이력</h2>
                    <div class="btn btn-icon btn-sm btn-active-icon-primary" data-bs-dismiss="modal">
                        <i class="ki-duotone ki-cross fs-1"><span class="path1"></span><span class="path2"></span></i>
                    </div>
                </div>
                <div class="modal-body py-10 px-lg-17">
                    <div class="table-responsive">
                        <table class="table table-row-dashed">
                            <thead>
                            <tr class="fw-bold fs-6 text-gray-800 border-bottom border-gray-200">
                                <th>수신메일</th>
                                <th>수신상태</th>
                                <th>발송결과</th>
                                <th>발송일시</th>
                            </tr>
                            </thead>
                            <tbody id="historyTableBody">
                                <%-- JavaScript가 이 영역을 동적으로 채웁니다. --%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer flex-center">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <script>var hostUrl = "/assets/";</script>
    <!--begin::Global Javascript Bundle(mandatory for all pages)-->
    <script src="/assets/plugins/global/plugins.bundle.js"></script>
    <script src="/assets/js/scripts.bundle.js"></script>
    <!--end::Global Javascript Bundle-->
    <!--begin::Vendors Javascript(used for this page only)-->
    <script src="/assets/plugins/custom/datatables/datatables.bundle.js"></script>
    <!--end::Vendors Javascript-->
    <!--begin::Custom Javascript(used for this page only)-->
    <script src="/assets/js/custom/apps/ecommerce/catalog/tables.js?ver=20260209"></script>
    <script src="/assets/js/widgets.bundle.js"></script>
    <script src="/assets/js/custom/widgets.js"></script>
    <script src="/assets/js/custom/apps/chat/chat.js"></script>
    <script src="/assets/js/custom/utilities/modals/upgrade-plan.js"></script>
    <script src="/assets/js/custom/utilities/modals/create-app.js"></script>
    <script src="/assets/js/custom/utilities/modals/users-search.js"></script>

    <script src="/js/jquery.number.min.js"></script>
    <!--end::Custom Javascript-->

    <%--PDF--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/custom/invoiceNewNew.js?ver=<%=System.currentTimeMillis()%>"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>