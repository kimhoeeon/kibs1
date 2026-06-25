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
<body id="kt_app_body" data-kt-app-layout="dark-sidebar" data-kt-app-header-fixed="true"
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
                                        <li class="breadcrumb-item text-muted">참가신청서 관리 (~2025)</li>
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
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">부스 신청 정보</h3>
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
                                                                    <div class="cost booth_cost">
                                                                        <span class="fw-semibold d-block fs-7">￦ 100,000</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="quantity">
                                                                        <p class="j_num">
                                                                            <input type="text" id="registration_cnt" placeholder="수량 입력" class="form-control form-control-lg form-control-solid-bg onlyNum qty" value="1" readonly>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="amount">
                                                                        <p class="price">
                                                                            <input type="text" id="registration_fee" class="num_sum" value="￦ 100,000" style="background:unset; border: 0; text-align: center;" disabled>
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
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <div class="cate">
                                                                        <span class="fw-semibold d-block fs-7">독립부스</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="cost booth_cost">
                                                                        <span class="fw-semibold d-block fs-7">￦ 1,800,000</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="quantity">
                                                                        <p class="j_num">
                                                                            <input type="text" id="stand_alone_booth_cnt" value="${info.standAloneBoothCnt}" placeholder="수량 입력" class="form-control form-control-lg form-control-solid-bg onlyNum qty" onblur="minCnt(this,2);" onkeyup="checkBooth();autoSum(1);autoTotalSum();">
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="amount">
                                                                        <p class="price">
                                                                            <input type="text" id="stand_alone_booth_fee" class="num_sum" value="<fmt:formatNumber value="${info.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" style="background:unset; border: 0; text-align: center;" disabled>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">독립부스는 2부스부터 신청 가능합니다.</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <div class="cate">
                                                                        <span class="fw-semibold d-block fs-7">조립부스</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="cost booth_cost">
                                                                        <span class="fw-semibold d-block fs-7">￦ 2,100,000</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="quantity">
                                                                        <p class="j_num">
                                                                            <input type="text" id="assembly_booth_cnt" value="${info.assemblyBoothCnt}" placeholder="수량 입력" class="form-control form-control-lg form-control-solid-bg onlyNum qty" onkeyup="checkBooth();autoSum(2);autoTotalSum();">
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="amount">
                                                                        <p class="price">
                                                                            <input type="text" id="assembly_booth_fee" class="num_sum" value="<fmt:formatNumber value="${info.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" style="background:unset; border: 0; text-align: center;" disabled>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7"></span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <div class="cate">
                                                                        <span class="fw-semibold d-block fs-7">온라인부스</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="cost booth_cost">
                                                                        <span class="fw-semibold d-block fs-7">￦ 1,000,000</span>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="quantity">
                                                                        <p class="j_num">
                                                                            <select id="online_booth_cnt" class="form-select form-select-solid" onchange="checkBooth();autoSum(3);autoTotalSum();">
                                                                                <option <c:if test="${info.onlineBoothCnt eq 0}">selected</c:if> >0</option>
                                                                                <option <c:if test="${info.onlineBoothCnt eq 1}">selected</c:if> >1</option>
                                                                            </select>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div class="amount">
                                                                        <p class="price">
                                                                            <input type="text" id="online_booth_fee" class="num_sum" value="<fmt:formatNumber value="${info.onlineBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" style="background:unset; border: 0; text-align: center;" disabled>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">영상 제작 별도 : 영상 제작 희망 시 사무국 문의</span>
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
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">할인적용 선택</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_discount_info">
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
                                                                <th>구분</th>
                                                                <th>할인</th>
                                                                <th>비고</th>
                                                            </tr>
                                                        </thead>
                                                        <!--end::Table head-->
                                                        <!--begin::Table body-->
                                                        <tbody>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount1" name="discount" value="300000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '300000');" <c:if test="${info.discountEarly1}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1차 조기신청</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 300,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1차 조기신청 할인 / ~ 2025.11.21 (금)까지 신청 시</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount2" name="discount" value="200000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '200000');" <c:if test="${info.discountEarly2}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">2차 조기신청</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 200,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">2차 조기신청 할인 / ~ 2025.12.19 (금)까지 신청 시</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount3" name="discount" value="200000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '200000');" <c:if test="${info.discountReAll}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">재참가할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 200,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">2015년 ~ 2025년 경기국제보트쇼 참가기업</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount5" name="discount" value="300000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '300000');" <c:if test="${info.discountScale2}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">규모할인 1 (20부스 이상)</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 300,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">20부스 이상 참가기업</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount6" name="discount" value="450000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '450000');" <c:if test="${info.discountScale3}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">규모할인 2 (40부스 이상)</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 450,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">40부스 이상 참가기업</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount7" name="discount" value="500000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '500000');" <c:if test="${info.discountFirst}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">첫 참가 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 500,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">경기국제보트쇼 최초 참가기업</span>
                                                                </td>
                                                            </tr>
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">
                                                                        <input type="checkbox" id="discount8" name="discount" value="200000" class="form-check-input form-control-solid-bg" onchange="autoDiscountSum(this, '200000');" <c:if test="${info.discountLeisure}">checked</c:if> >
                                                                    </span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">한국해양레저산업협회 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">1 부스당 200,000 원 할인</span>
                                                                </td>
                                                                <td>
                                                                    <span class="fw-semibold d-block fs-7">한국해양레저산업협회 회원사</span>
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

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex align-items-center justify-content-end py-6 px-9">
                                            <div class="">
                                                <span class="fw-semibold fs-2">총액 (VAT 미포함)</span>
                                            </div>
                                            <div>
                                                <c:if test="${info.boothPrcSum eq 0}">
                                                    <input type="text" class="num_sum fw-bold fs-3x text-end mw-300px" id="form_add_total" value="<fmt:formatNumber value="${info.registrationFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" style="background:unset; border: 0;" disabled>
                                                </c:if>
                                                <c:if test="${info.boothPrcSum ne 0}">
                                                <input type="text" class="num_sum fw-bold fs-3x text-end mw-300px" id="form_add_total" value="<fmt:formatNumber value="${info.boothPrcSum - info.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" style="background:unset; border: 0;" disabled>
                                                </c:if>
                                            </div>
                                        </div>
                                        <!--end::Actions-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitor/application/booth.do" class="btn btn-info btn-active-light-info cursor-pointer">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_application_booth_modify_init_set('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_application_booth_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
                                            </div>
                                        </div>
                                        <!--end::Actions-->
                                    </div>
                                    <!--end::Basic info-->
                                </form>
                                <!--end::form-->
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

    <!--begin::Javascript-->

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

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/custom/booth.js?ver=<%=System.currentTimeMillis()%>"></script>

    <%--<script type="text/javascript">
        $(function(){
            $('#form_add_total').val($('.booth_cost')[0].innerText); // 초기 총액 : 등록비 합계 Set
        });
    </script>--%>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>