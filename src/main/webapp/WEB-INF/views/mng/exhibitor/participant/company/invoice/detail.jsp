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
<body id="kt_app_body" data-exhibitor-seq="${info.seq}" data-company-name-ko="${info.companyNameKo}"
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
            <div id="kt_app_header" class="app-header">
                <!--begin::Header container-->
                <div class="app-container container-fluid d-flex align-items-stretch justify-content-between"
                     id="kt_app_header_container">
                    <!--begin::Sidebar mobile toggle-->
                    <div class="d-flex align-items-center d-lg-none ms-n3 me-1 me-md-2" title="Show sidebar menu">
                        <div class="btn btn-icon btn-active-color-primary w-35px h-35px"
                             id="kt_app_sidebar_mobile_toggle">
                            <i class="ki-duotone ki-abstract-14 fs-2 fs-md-1">
                                <span class="path1"></span>
                                <span class="path2"></span>
                            </i>
                        </div>
                    </div>
                    <!--end::Sidebar mobile toggle-->
                    <!--begin::Mobile logo-->
                    <div class="d-flex align-items-center flex-grow-1 flex-lg-grow-0">
                        <a href="/mng/main.do" class="d-lg-none">
                            <img alt="Logo"
                                 src="/assets/media/logos/default-small.svg"
                                 class="h-30px"/>
                        </a>
                    </div>
                    <!--end::Mobile logo-->
                    <!--begin::Header wrapper-->
                    <div class="d-flex align-items-stretch justify-content-between flex-lg-grow-1"
                         id="kt_app_header_wrapper">
                        <!--begin::Menu wrapper-->
                        <div class="app-header-menu app-header-mobile-drawer align-items-stretch" data-kt-drawer="true"
                             data-kt-drawer-name="app-header-menu" data-kt-drawer-activate="{default: true, lg: false}"
                             data-kt-drawer-overlay="true" data-kt-drawer-width="250px" data-kt-drawer-direction="end"
                             data-kt-drawer-toggle="#kt_app_header_menu_toggle" data-kt-swapper="true"
                             data-kt-swapper-mode="{default: 'append', lg: 'prepend'}"
                             data-kt-swapper-parent="{default: '#kt_app_body', lg: '#kt_app_header_wrapper'}">
                            <!--begin::Menu-->
                            <div class="menu menu-rounded menu-column menu-lg-row my-5 my-lg-0 align-items-stretch fw-semibold px-2 px-lg-0"
                                 id="kt_app_header_menu" data-kt-menu="true">
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="{default: 'click', lg: 'hover'}"
                                     data-kt-menu-placement="bottom-start"
                                     class="menu-item menu-here-bg menu-lg-down-accordion me-0 me-lg-2">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-title">시스템 사용 현황</span>
                                    <span class="menu-arrow d-lg-none"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0 w-100 w-lg-600px">
                                        <!--begin:Dashboards menu-->
                                        <div class="menu-state-bg menu-extended overflow-hidden overflow-lg-visible"
                                             data-kt-menu-dismiss="true">
                                            <!--begin:Row-->
                                            <div class="row">
                                                <!--begin:Col-->
                                                <div class="col-lg-12 mb-3 mb-lg-0 py-3 px-3 py-lg-6 px-lg-6">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-6 mb-3">
                                                            <!--begin:Menu item-->
                                                            <div class="menu-item p-0 m-0">
                                                                <!--begin:Menu link-->
                                                                <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');<%--/mng/main.do--%>"
                                                                   class="menu-link">
                                                                <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                                    <i class="ki-duotone ki-element-11 text-primary fs-1">
                                                                        <span class="path1"></span>
                                                                        <span class="path2"></span>
                                                                        <span class="path3"></span>
                                                                        <span class="path4"></span>
                                                                    </i>
                                                                </span>
                                                                    <span class="d-flex flex-column">
                                                                    <span class="fs-6 fw-bold text-gray-800">체류 정보 시스템</span> <%-- 체류 정보 시스템 --%>
                                                                    <span class="fs-7 fw-semibold text-muted">Residence Information System</span>
                                                                </span>
                                                                </a>
                                                                <!--end:Menu link-->
                                                            </div>
                                                            <!--end:Menu item-->
                                                        </div>
                                                        <!--end:Col-->
                                                        <!--begin:Col-->
                                                        <div class="col-lg-6 mb-3">
                                                            <!--begin:Menu item-->
                                                            <div class="menu-item p-0 m-0">
                                                                <!--begin:Menu link-->
                                                                <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');<%--/mng/main.do--%>"
                                                                   class="menu-link"> <%-- 현장 주선 시스템 --%>
                                                                    <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                                    <i class="ki-duotone ki-basket text-danger fs-1">
                                                                        <span class="path1"></span>
                                                                        <span class="path2"></span>
                                                                        <span class="path3"></span>
                                                                        <span class="path4"></span>
                                                                    </i>
                                                                </span>
                                                                    <span class="d-flex flex-column">
                                                                    <span class="fs-6 fw-bold text-gray-800">현장 주선 시스템</span>
                                                                    <span class="fs-7 fw-semibold text-muted">An On-site Arrangement System</span>
                                                                </span>
                                                                </a>
                                                                <!--end:Menu link-->
                                                            </div>
                                                            <!--end:Menu item-->
                                                        </div>
                                                        <!--end:Col-->
                                                        <!--begin:Col-->
                                                        <div class="col-lg-6 mb-3">
                                                            <!--begin:Menu item-->
                                                            <div class="menu-item p-0 m-0">
                                                                <!--begin:Menu link-->
                                                                <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');<%--/mng/main.do--%>"
                                                                   class="menu-link">
                                                                <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                                    <i class="ki-duotone ki-abstract-44 text-info fs-1">
                                                                        <span class="path1"></span>
                                                                        <span class="path2"></span>
                                                                    </i>
                                                                </span>
                                                                    <span class="d-flex flex-column">
                                                                    <span class="fs-6 fw-bold text-gray-800">무역관 시스템</span><%-- 무역관 시스템 --%>
                                                                    <span class="fs-7 fw-semibold text-muted">Trade View System</span>
                                                                </span>
                                                                </a>
                                                                <!--end:Menu link-->
                                                            </div>
                                                            <!--end:Menu item-->
                                                        </div>
                                                        <!--end:Col-->
                                                        <!--begin:Col-->
                                                        <div class="col-lg-6 mb-3">
                                                            <!--begin:Menu item-->
                                                            <div class="menu-item p-0 m-0">
                                                                <!--begin:Menu link-->
                                                                <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');<%--/mng/main.do--%>"
                                                                   class="menu-link">
                                                                <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                                    <i class="ki-duotone ki-color-swatch text-success fs-1">
                                                                        <span class="path1"></span>
                                                                        <span class="path2"></span>
                                                                        <span class="path3"></span>
                                                                        <span class="path4"></span>
                                                                        <span class="path5"></span>
                                                                        <span class="path6"></span>
                                                                        <span class="path7"></span>
                                                                        <span class="path8"></span>
                                                                        <span class="path9"></span>
                                                                        <span class="path10"></span>
                                                                        <span class="path11"></span>
                                                                        <span class="path12"></span>
                                                                        <span class="path13"></span>
                                                                        <span class="path14"></span>
                                                                        <span class="path15"></span>
                                                                        <span class="path16"></span>
                                                                        <span class="path17"></span>
                                                                        <span class="path18"></span>
                                                                        <span class="path19"></span>
                                                                        <span class="path20"></span>
                                                                        <span class="path21"></span>
                                                                    </i>
                                                                </span>
                                                                    <span class="d-flex flex-column">
                                                                    <span class="fs-6 fw-bold text-gray-800">부대행사 시스템</span><%-- 부대행사 시스템 --%>
                                                                    <span class="fs-7 fw-semibold text-muted">Incidental Event System</span>
                                                                </span>
                                                                </a>
                                                                <!--end:Menu link-->
                                                            </div>
                                                            <!--end:Menu item-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Col-->
                                            </div>
                                            <!--end:Row-->
                                        </div>
                                        <!--end:system menu-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="{default: 'click', lg: 'hover'}"
                                     data-kt-menu-placement="bottom-start" class="menu-item here">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-title">전시회</span>
                                    <span class="menu-arrow d-lg-none"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                                        <!--begin:Pages menu-->
                                        <div class="menu-active-bg px-4 px-lg-0">
                                            <!--begin:Tabs nav-->
                                            <div class="d-flex w-100 overflow-auto">
                                                <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary active"
                                                           onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_visitor">참가자 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_transfer">이관정보</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_application_new">참가신청서 관리 (2026~)</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_application">참가신청서 관리 (~2025)</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                </ul>
                                            </div>
                                            <!--end:Tabs nav-->
                                            <!--begin:Tab content-->
                                            <div class="tab-content py-4 py-lg-8 px-lg-7">
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-350px active" id="kt_app_header_menu_pages_visitor">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/participant/company.do"
                                                                           class="menu-link active">
                                                                            <span class="menu-title">전시업체 목록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/participant/visitor.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">참관객 목록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/participant/member.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">회원 목록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_transfer">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/transfer/company.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">지난 행사 전시업체</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/transfer/visitor.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">지난 행사 참관객</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_application_new">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/booth.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">전시부스 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/sign.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">상호간판 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/utility.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">유틸리티 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/pass.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">출입증 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/buyer.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">초청 희망 바이어</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/gift.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">경품제공 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/online.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">온라인 전시관</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitorNew/application/product.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">전시품 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_application">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/booth.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">전시부스 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/sign.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">상호간판 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/utility.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">유틸리티 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/pass.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">출입증 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/buyer.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">초청 희망 바이어</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/gift.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">경품제공 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <%--<!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/banner.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">웹 배너 신청</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->--%>
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/exhibitor/application/online.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">온라인 전시관</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                            </div>
                                            <!--end:Tab content-->
                                        </div>
                                        <!--end:Pages menu-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="{default: 'click', lg: 'hover'}"
                                     data-kt-menu-placement="bottom-start" class="menu-item">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-title">부대행사</span>
                                    <span class="menu-arrow d-lg-none"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                                        <!--begin:Pages menu-->
                                        <div class="menu-active-bg px-4 px-lg-0">
                                            <!--begin:Tabs nav-->
                                            <div class="d-flex w-100 overflow-auto">
                                                <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 active text-active-primary"
                                                           onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_product">올해의 제품</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_company">KIBS
                                                            참가업체</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                </ul>
                                            </div>
                                            <!--end:Tabs nav-->
                                            <!--begin:Tab content-->
                                            <div class="tab-content py-4 py-lg-8 px-lg-7">
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane active w-lg-250px"
                                                     id="kt_app_header_menu_pages_product">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/event/product.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">참가자 목록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-250px" id="kt_app_header_menu_pages_company">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/event/company.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">참가자 목록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                            </div>
                                            <!--end:Tab content-->
                                        </div>
                                        <!--end:Pages menu-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="{default: 'click', lg: 'hover'}"
                                     data-kt-menu-placement="bottom-start" class="menu-item">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-title">정보센터</span>
                                    <span class="menu-arrow d-lg-none"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                                        <!--begin:Pages menu-->
                                        <div class="menu-active-bg px-4 px-lg-0">
                                            <!--begin:Tabs nav-->
                                            <div class="d-flex w-100 overflow-auto">
                                                <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 active text-active-primary"
                                                           onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_notice">게시판 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_popup">팝업 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <%--<!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_document">나의
                                                            서류함</a>
                                                    </li>
                                                    <!--end:Nav item-->--%>
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" onclick=""
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_newsletter">eDM
                                                            관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                </ul>
                                            </div>
                                            <!--end:Tabs nav-->
                                            <!--begin:Tab content-->
                                            <div class="tab-content py-4 py-lg-8 px-lg-7">
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane active w-lg-300px"
                                                     id="kt_app_header_menu_pages_notice">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/notice.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">공지사항</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/newsletter_ko.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">뉴스레터</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <%--<!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/newsletter_en.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">News letters</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->--%>
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/press.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">보도자료</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/faq.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">FAQ</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/column.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">칼럼</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <%--<!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/brochure.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">E-브로슈어</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->--%>
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/dataroom.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">갤러리</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <%--<!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/gallery.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">Photo Gallery</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->--%>
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/kibstv.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">KIBS Tube</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/board/contest.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">공모전</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-400px" id="kt_app_header_menu_pages_popup">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/popup/add.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">팝업 등록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-400px" id="kt_app_header_menu_pages_document">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/document/download.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">파일 다운로드 내역</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-400px"
                                                     id="kt_app_header_menu_pages_newsletter">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/center/edm/list.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">메일 발송 & 이력</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                            </div>
                                            <!--end:Tab content-->
                                        </div>
                                        <!--end:Pages menu-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="{default: 'click', lg: 'hover'}"
                                     data-kt-menu-placement="bottom-start" class="menu-item">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                        <span class="menu-title">개발사</span>
                                        <span class="menu-arrow d-lg-none"></span>
                                    </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                                        <!--begin:Pages menu-->
                                        <div class="menu-active-bg px-4 px-lg-0">
                                            <!--begin:Tabs nav-->
                                            <div class="d-flex w-100 overflow-auto">
                                                <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 active text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_request">요청사항 & 문의</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                </ul>
                                            </div>
                                            <!--end:Tabs nav-->
                                            <!--begin:Tab content-->
                                            <div class="tab-content py-4 py-lg-8 px-lg-7">
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane active w-lg-175px" id="kt_app_header_menu_pages_request">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/request/management/list.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">요청사항 & 문의 관리</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                            </div>
                                            <!--end:Tab content-->
                                        </div>
                                        <!--end:Pages menu-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <%--<div data-kt-menu-trigger="{default: 'click', lg: 'hover'}"
                                     data-kt-menu-placement="bottom-start" class="menu-item">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                        <span class="menu-title">업체DB</span>
                                        <span class="menu-arrow d-lg-none"></span>
                                    </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                                        <!--begin:Pages menu-->
                                        <div class="menu-active-bg px-4 px-lg-0">
                                            <!--begin:Tabs nav-->
                                            <div class="d-flex w-100 overflow-auto">
                                                <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 active text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_parti_company">DB 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                </ul>
                                            </div>
                                            <!--end:Tabs nav-->
                                            <!--begin:Tab content-->
                                            <div class="tab-content py-4 py-lg-8 px-lg-7">
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane active w-lg-250px" id="kt_app_header_menu_pages_parti_company">
                                                    <!--begin:Row-->
                                                    <div class="row">
                                                        <!--begin:Col-->
                                                        <div class="col-lg-12 mb-6 mb-lg-0">
                                                            <!--begin:Row-->
                                                            <div class="row">
                                                                <!--begin:Col-->
                                                                <div class="col-lg-12">
                                                                    <!--begin:Menu item-->
                                                                    <div class="menu-item p-0 m-0">
                                                                        <!--begin:Menu link-->
                                                                        <a href="/mng/participant/company/list.do"
                                                                           class="menu-link">
                                                                            <span class="menu-title">TM 및 잠재DB 목록</span>
                                                                        </a>
                                                                        <!--end:Menu link-->
                                                                    </div>
                                                                    <!--end:Menu item-->
                                                                </div>
                                                                <!--end:Col-->
                                                            </div>
                                                            <!--end:Row-->
                                                        </div>
                                                        <!--end:Col-->
                                                    </div>
                                                    <!--end:Row-->
                                                </div>
                                                <!--end:Tab pane-->
                                            </div>
                                            <!--end:Tab content-->
                                        </div>
                                        <!--end:Pages menu-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>--%>
                                <!--end:Menu item-->
                            </div>
                            <!--end::Menu-->
                        </div>
                        <!--end::Menu wrapper-->
                        <!--begin::Navbar-->
                        <div class="app-navbar flex-shrink-0">
                            <!--begin::Search-->
                            <%--<div class="app-navbar-item align-items-stretch ms-1 ms-md-3">
                                <!--begin::Search-->
                                <div id="kt_header_search" class="header-search d-flex align-items-stretch"
                                     data-kt-search-keypress="true" data-kt-search-min-length="2"
                                     data-kt-search-enter="enter" data-kt-search-layout="menu"
                                     data-kt-menu-trigger="auto"
                                     data-kt-menu-overflow="false" data-kt-menu-permanent="true"
                                     data-kt-menu-placement="bottom-end">
                                    <!--begin::Search toggle-->
                                    <div class="d-flex align-items-center" data-kt-search-element="toggle"
                                         id="kt_header_search_toggle">
                                        <div class="btn btn-icon btn-custom btn-icon-muted btn-active-light btn-active-color-primary w-30px h-30px w-md-40px h-md-40px">
                                            <i class="ki-duotone ki-magnifier fs-2 fs-lg-1">
                                                <span class="path1"></span>
                                                <span class="path2"></span>
                                            </i>
                                        </div>
                                    </div>
                                    <!--end::Search toggle-->
                                    <!--begin::Menu-->
                                    <div data-kt-search-element="content"
                                         class="menu menu-sub menu-sub-dropdown p-7 w-325px w-md-375px">
                                        <!--begin::Wrapper-->
                                        <div data-kt-search-element="wrapper">
                                            <!--begin::Form-->
                                            <form data-kt-search-element="form" class="w-100 position-relative mb-3"
                                                  autocomplete="off">
                                                <!--begin::Icon-->
                                                <i class="ki-duotone ki-magnifier fs-2 text-gray-500 position-absolute top-50 translate-middle-y ms-0">
                                                    <span class="path1"></span>
                                                    <span class="path2"></span>
                                                </i>
                                                <!--end::Icon-->
                                                <!--begin::Input-->
                                                <input type="text"
                                                       class="search-input form-control form-control-flush ps-10"
                                                       name="search" value="" placeholder="Search..."
                                                       data-kt-search-element="input"/>
                                                <!--end::Input-->
                                                <!--begin::Spinner-->
                                                <span class="search-spinner position-absolute top-50 end-0 translate-middle-y lh-0 d-none me-1"
                                                      data-kt-search-element="spinner">
                                                <span class="spinner-border h-15px w-15px align-middle text-gray-400"></span>
                                            </span>
                                                <!--end::Spinner-->
                                                <!--begin::Reset-->
                                                <span class="search-reset btn btn-flush btn-active-color-primary position-absolute top-50 end-0 translate-middle-y lh-0 d-none"
                                                      data-kt-search-element="clear">
                                                <i class="ki-duotone ki-cross fs-2 fs-lg-1 me-0">
                                                    <span class="path1"></span>
                                                    <span class="path2"></span>
                                                </i>
                                            </span>
                                                <!--end::Reset-->
                                            </form>
                                            <!--end::Form-->
                                        </div>
                                        <!--end::Wrapper-->
                                    </div>
                                    <!--end::Menu-->
                                </div>
                                <!--end::Search-->
                            </div>--%>
                            <!--end::Search-->
                            <!--begin::User menu-->
                            <div class="app-navbar-item ms-1 ms-md-3" id="kt_header_user_menu_toggle">
                                <!--begin::Menu wrapper-->
                                <div class="cursor-pointer symbol symbol-30px symbol-md-40px"
                                     data-kt-menu-trigger="{default: 'click', lg: 'hover'}" data-kt-menu-attach="parent"
                                     data-kt-menu-placement="bottom-end">
                                    <i class="ki-solid ki-user-square fs-2qx"></i>
                                </div>
                                <!--begin::User account menu-->
                                <div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-800 menu-state-bg menu-state-color fw-semibold py-4 fs-6 w-275px"
                                     data-kt-menu="true">
                                    <!--begin::Menu item-->
                                    <div class="menu-item px-3">
                                        <div class="menu-content d-flex align-items-center px-3">
                                            <!--begin::Avatar-->
                                            <div class="symbol symbol-50px me-5">
                                                <i class="ki-solid ki-user-square fs-3x"></i>
                                            </div>
                                            <!--end::Avatar-->
                                            <!--begin::Username-->
                                            <div class="d-flex flex-column">
                                                <div class="fw-bold d-flex align-items-center fs-5">${sessionScope.id}
                                                    <span class="badge badge-light-success fw-bold fs-8 px-2 py-1 ms-2">admin</span>
                                                </div>
                                                <a onclick="" class="fw-semibold text-muted text-hover-primary fs-7">KIBS
                                                    관리자</a>
                                            </div>
                                            <!--end::Username-->
                                        </div>
                                    </div>
                                    <!--end::Menu item-->
                                    <!--begin::Menu separator-->
                                    <div class="separator my-2"></div>
                                    <!--end::Menu separator-->
                                    <!--begin::Menu item-->
                                    <div class="menu-item px-5">
                                        <a href="javascript:logout();"
                                           class="menu-link px-5">Sign Out</a>
                                    </div>
                                    <!--end::Menu item-->
                                </div>
                                <!--end::User account menu-->
                                <!--end::Menu wrapper-->
                            </div>
                            <!--end::User menu-->
                            <!--begin::Header menu toggle-->
                            <div class="app-navbar-item d-lg-none ms-2 me-n2" title="Show header menu">
                                <div class="btn btn-flex btn-icon btn-active-color-primary w-30px h-30px"
                                     id="kt_app_header_menu_toggle">
                                    <i class="ki-duotone ki-element-4 fs-1">
                                        <span class="path1"></span>
                                        <span class="path2"></span>
                                    </i>
                                </div>
                            </div>
                            <!--end::Header menu toggle-->
                        </div>
                        <!--end::Navbar-->
                    </div>
                    <!--end::Header wrapper-->
                </div>
                <!--end::Header container-->
            </div>
            <!--end::Header-->
            <!--begin::Wrapper-->
            <div class="app-wrapper flex-column flex-row-fluid" id="kt_app_wrapper">
                <!--begin::Sidebar-->
                <div id="kt_app_sidebar" class="app-sidebar flex-column" data-kt-drawer="true"
                     data-kt-drawer-name="app-sidebar" data-kt-drawer-activate="{default: true, lg: false}"
                     data-kt-drawer-overlay="true" data-kt-drawer-width="225px" data-kt-drawer-direction="start"
                     data-kt-drawer-toggle="#kt_app_sidebar_mobile_toggle">
                    <!--begin::Logo-->
                    <div class="app-sidebar-logo px-6 justify-content-center h-90px" id="kt_app_sidebar_logo">
                        <!--begin::Logo image-->
                        <a href="/mng/main.do">
                            <img alt="Logo"
                                 src="/img/logo2_w.png<%--/static/assets/media/logos/default-dark.svg--%>"
                                 class="h-65px app-sidebar-logo-default"/>
                            <img alt="Logo"
                                 src="/img/logo2_w.png<%--assets/media/logos/default-small.svg--%>"
                                 class="h-65px app-sidebar-logo-minimize"/>
                        </a>
                        <!--end::Logo image-->
                        <!--begin::Sidebar toggle-->
                        <!--begin::Minimized sidebar setup:
                if (isset($_COOKIE["sidebar_minimize_state"]) && $_COOKIE["sidebar_minimize_state"] === "on") {
                    1. "src/js/layout/sidebar.js" adds "sidebar_minimize_state" cookie value to save the sidebar minimize state.
                    2. Set data-kt-app-sidebar-minimize="on" attribute for body tag.
                    3. Set data-kt-toggle-state="active" attribute to the toggle element with "kt_app_sidebar_toggle" id.
                    4. Add "active" class to to sidebar toggle element with "kt_app_sidebar_toggle" id.
                }
            -->
                        <div id="kt_app_sidebar_toggle"
                             class="app-sidebar-toggle btn btn-icon btn-shadow btn-sm btn-color-muted btn-active-color-primary body-bg h-30px w-30px position-absolute top-50 start-100 translate-middle rotate"
                             data-kt-toggle="true" data-kt-toggle-state="active" data-kt-toggle-target="body"
                             data-kt-toggle-name="app-sidebar-minimize">
                            <i class="ki-duotone ki-double-left fs-2 rotate-180">
                                <span class="path1"></span>
                                <span class="path2"></span>
                            </i>
                        </div>
                        <!--end::Sidebar toggle-->
                    </div>
                    <!--end::Logo-->
                    <!--begin::sidebar menu-->
                    <div class="app-sidebar-menu overflow-hidden flex-column-fluid">
                        <!--begin::Menu wrapper-->
                        <div id="kt_app_sidebar_menu_wrapper" class="app-sidebar-wrapper hover-scroll-overlay-y my-5"
                             data-kt-scroll="true" data-kt-scroll-activate="true" data-kt-scroll-height="auto"
                             data-kt-scroll-dependencies="#kt_app_sidebar_logo, #kt_app_sidebar_footer"
                             data-kt-scroll-wrappers="#kt_app_sidebar_menu" data-kt-scroll-offset="5px"
                             data-kt-scroll-save-state="true">
                            <!--begin::Menu-->
                            <div class="menu menu-column menu-rounded menu-sub-indention px-3" id="#kt_app_sidebar_menu"
                                 data-kt-menu="true" data-kt-menu-expand="false">
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-chart fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">시스템 사용 현황</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="javascript:alert('현재 사용 중지된 메뉴입니다.');">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">체류 정보 시스템</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="javascript:alert('현재 사용 중지된 메뉴입니다.');">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">현장 주선 시스템</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="javascript:alert('현재 사용 중지된 메뉴입니다.');">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">무역관 시스템</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="javascript:alert('현재 사용 중지된 메뉴입니다.');">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">부대행사 시스템</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div class="menu-item pt-5">
                                    <!--begin:Menu content-->
                                    <div class="menu-content">
                                        <span class="menu-heading fw-bold text-uppercase fs-7">전시회</span>
                                    </div>
                                    <!--end:Menu content-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion hover show">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-address-book fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">참가자 관리</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link active" href="/mng/exhibitor/participant/company.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">전시업체 목록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/participant/visitor.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">참관객 목록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link"
                                               href="/mng/exhibitor/participant/member.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">회원 목록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-abstract-26 fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                            <span class="path5"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">이관정보</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/transfer/company.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">지난행사 전시업체</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/transfer/visitor.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">지난행사 참관객</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                        <span class="menu-icon">
                                            <i class="ki-duotone ki-some-files fs-2">
                                                <span class="path1"></span>
                                                <span class="path2"></span>
                                            </i>
                                        </span>
                                        <span class="menu-title">참가신청서 관리 (2026~)</span>
                                        <span class="menu-arrow"></span>
                                    </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/booth.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">전시부스 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/sign.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">상호간판 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/utility.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">유틸리티 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/pass.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">출입증 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/buyer.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">초청 희망 바이어</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/gift.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">경품제공 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/online.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">온라인 전시관</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitorNew/application/product.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">전시품 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                        <span class="menu-icon">
                                            <i class="ki-duotone ki-file fs-2">
                                                <span class="path1"></span>
                                                <span class="path2"></span>
                                                <span class="path3"></span>
                                                <span class="path4"></span>
                                                <span class="path5"></span>
                                            </i>
                                        </span>
                                        <span class="menu-title">참가신청서 관리 (~2025)</span>
                                        <span class="menu-arrow"></span>
                                    </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/booth.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">전시부스 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/sign.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">상호간판 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/utility.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">유틸리티 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/pass.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">출입증 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/buyer.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">초청 희망 바이어</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/gift.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">경품제공 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/online.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">온라인 전시관</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div class="menu-item pt-5">
                                    <!--begin:Menu content-->
                                    <div class="menu-content">
                                        <span class="menu-heading fw-bold text-uppercase fs-7">부대행사</span>
                                    </div>
                                    <!--end:Menu content-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-crown-2 fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">올해의 제품</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/event/product.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">참가자 목록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-briefcase fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">KIBS 참가업체</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/event/company.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">참가자 목록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div class="menu-item pt-5">
                                    <!--begin:Menu content-->
                                    <div class="menu-content">
                                        <span class="menu-heading fw-bold text-uppercase fs-7">정보센터</span>
                                    </div>
                                    <!--end:Menu content-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-questionnaire-tablet fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">게시판 관리</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/notice.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">공지사항</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/newsletter_ko.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">뉴스레터</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <%--<!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/newsletter_en.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">News letters</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->--%>
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/press.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">보도자료</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/faq.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">FAQ</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/column.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">칼럼</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <%--<!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/brochure.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">E-브로슈어</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->--%>
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/dataroom.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">갤러리</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <%--<!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/gallery.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">Photo Gallery</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->--%>
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/kibstv.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">KIBS Tube</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/board/contest.do">
                                                <span class="menu-bullet">
                                                    <span class="bullet bullet-dot"></span>
                                                </span>
                                                <span class="menu-title">공모전</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-toggle-on fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">팝업 관리</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/popup/add.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">팝업 등록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <%--<!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-folder fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">나의 서류함</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/document/download.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">파일 다운로드 내역</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->--%>
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-notepad-edit fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">eDM 관리</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/center/edm/list.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">메일 발송 & 이력</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div class="menu-item pt-5">
                                    <!--begin:Menu content-->
                                    <div class="menu-content">
                                        <span class="menu-heading fw-bold text-uppercase fs-7">개발사</span>
                                    </div>
                                    <!--end:Menu content-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                    <span class="menu-icon">
                                        <i class="ki-duotone ki-notification-on fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                            <span class="path5"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">요청사항 & 문의</span>
                                    <span class="menu-arrow"></span>
                                </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/request/management/list.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">요청사항 & 문의 관리</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <%--<div class="menu-item pt-5">
                                    <!--begin:Menu content-->
                                    <div class="menu-content">
                                        <span class="menu-heading fw-bold text-uppercase fs-7">업체DB</span>
                                    </div>
                                    <!--end:Menu content-->
                                </div>--%>
                                <!--end:Menu item-->
                                <!--begin:Menu item-->
                                <%--<div data-kt-menu-trigger="click" class="menu-item menu-accordion">
                                    <!--begin:Menu link-->
                                    <span class="menu-link">
                                        <span class="menu-icon">
                                            <i class="ki-duotone ki-wanchain fs-2">
                                                <i class="path1"></i>
                                                <i class="path2"></i>
                                            </i>
                                        </span>
                                        <span class="menu-title">DB 관리</span>
                                        <span class="menu-arrow"></span>
                                    </span>
                                    <!--end:Menu link-->
                                    <!--begin:Menu sub-->
                                    <div class="menu-sub menu-sub-accordion">
                                        <!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/participant/company/list.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">TM 및 잠재DB 목록</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->
                                    </div>
                                    <!--end:Menu sub-->
                                </div>--%>
                                <!--end:Menu item-->
                            </div>
                            <!--end::Menu-->
                        </div>
                        <!--end::Menu wrapper-->
                    </div>
                    <!--end::sidebar menu-->
                    <!--begin::Footer-->
                    <div class="app-sidebar-footer flex-column-auto pt-2 pb-6 px-6" id="kt_app_sidebar_footer">
                        <a href="https://lookerstudio.google.com/reporting/08c15042-3e9a-4735-af6a-8d095d0dc795/page/I7UcD"
                       class="btn btn-flex flex-center btn-primary overflow-hidden text-nowrap px-0 h-40px w-100"
                       data-bs-toggle="tooltip" data-bs-trigger="hover" data-bs-dismiss-="click"
                       title="방문객 리포트 바로가기" target="_blank">
                            <span class="btn-label">방문객 리포트</span>
                        </a>
                    </div>
                    <!--end::Footer-->
                </div>
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
                                        인보이스 정보</h1>
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
                                        <li class="breadcrumb-item text-muted">전시회</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">참가자 관리</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">전시업체 목록</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">인보이스 정보</li>
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

                                    <input type="hidden" id="baseTotalAmount" value="${info.boothPrcSum - info.discountPrcSum}" />

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
                                                                        1
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
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">전시부스 신청 정보</h3>
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
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">온라인부스</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 1,000,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.onlineBoothCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.onlineBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                    </tbody>
                                                    <!--end::Table body-->
                                                </table>
                                                <!--end::Table-->
                                            </div>
                                            <!--begin::Table container-->
                                            <div class="table-responsive mt-5">
                                                <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border">
                                                    <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                        <tr class="text-center"><th colspan="4">기본 할인 내역</th></tr>
                                                        <tr class="text-center">
                                                            <th style="width: 8%;">선택</th>
                                                            <th style="width: 15%;">구분</th>
                                                            <th>내용</th>
                                                            <th style="width: 20%;">할인금액(부스당)</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountEarly1}">checked</c:if> disabled></td>
                                                            <td rowspan="2">조기신청 할인</td>
                                                            <td>(1차) 조기신청 할인 (~2025.11.14 금)</td>
                                                            <td>300,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountEarly2}">checked</c:if> disabled></td>
                                                            <td>(2차) 조기신청 할인 (~2025.12.12 금)</td>
                                                            <td>200,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountScale1}">checked</c:if> disabled></td>
                                                            <td rowspan="6">규모 할인</td>
                                                            <td>10부스 이상 참가업체</td>
                                                            <td>400,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountScale2}">checked</c:if> disabled></td>
                                                            <td>20부스 이상 참가업체</td>
                                                            <td>650,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountScale3}">checked</c:if> disabled></td>
                                                            <td>30부스 이상 참가업체</td>
                                                            <td>750,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountScale4}">checked</c:if> disabled></td>
                                                            <td>40부스 이상 참가업체</td>
                                                            <td>800,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountScale5}">checked</c:if> disabled></td>
                                                            <td>50부스 이상 참가업체</td>
                                                            <td>850,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountScale6}">checked</c:if> disabled></td>
                                                            <td>100부스 이상 참가업체</td>
                                                            <td>900,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountRe}">checked</c:if> disabled></td>
                                                            <td>재참가 할인</td>
                                                            <td>2015년 ~ 2025년 참가 업체</td>
                                                            <td>200,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountFirstUnder10}">checked</c:if> disabled></td>
                                                            <td rowspan="2">첫참가 할인</td>
                                                            <td>최초 참가업체 (10부스 미만 참가)</td>
                                                            <td>500,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountFirstOver10}">checked</c:if> disabled></td>
                                                            <td>최초 참가업체 (10부스 이상 참가)</td>
                                                            <td>300,000 원</td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><input type="checkbox" class="form-check-input" <c:if test="${info.discountLeisure}">checked</c:if> disabled></td>
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
                                                <h3 class="fw-bold m-0">유틸리티 신청 정보</h3>
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
                                                            <th class="w-150px">품목</th>
                                                            <th class="w-150px">단가</th>
                                                            <th class="w-150px">신청수량</th>
                                                            <th class="w-175px">금액 (원)</th>
                                                        </tr>
                                                    </thead>
                                                    <!--end::Table head-->
                                                    <!--begin::Table body-->
                                                    <tbody>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">주간 단상 220V</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 80,000/1kw</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityJuganCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityJuganFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">24시간용 220V</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 100,000/1kw</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityDayCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityDayFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">압축공기 기본형</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 200,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityCompressedAirCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityCompressedAirFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">급배수 기본형</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 200,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityWaterBasicCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityWaterBasicFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">인터넷</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 200,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityInternetCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityInternetFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">파이텍스 (신품)</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 80,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityPytexNewCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityPytexNewFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">파이텍스 (재사용품)</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 50,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityPytexReCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityPytexReFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
                                                        </tr>
                                                        <tr class="text-center align-middle">
                                                            <td><span class="fw-semibold d-block fs-7">참관객/바이어 바코드 리더기</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">￦ 200,000</span></td>
                                                            <td><span class="fw-semibold d-block fs-7">${info.utilityBarcodeCnt}</span></td>
                                                            <td><span class="fw-semibold d-block fs-7"><fmt:formatNumber value="${info.utilityBarcodeFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/></span></td>
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
                                                        <th class="text-gray-700 fw-semibold fs-6" style="width: 70%;">부스 총액</th>
                                                        <td class="text-end fw-bold fs-6">
                                                            <fmt:formatNumber value="${info.boothPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-700 fw-semibold fs-6">유틸리티 총액</th>
                                                        <td class="text-end fw-bold fs-6">
                                                            + <fmt:formatNumber value="${info.utilityPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-700 fw-semibold fs-6">기본 할인</th>
                                                        <td class="text-end fw-bold fs-6 text-danger">
                                                            - <fmt:formatNumber value="${info.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-700 fw-semibold fs-6">특별 할인</th>
                                                        <td id="summary_special_discount" class="text-end fw-bold fs-6 text-danger">
                                                            - ￦ 0
                                                        </td>
                                                    </tr>
                                                    <tr class="border-top border-gray-300 border-top-dashed">
                                                        <th class="text-gray-800 fw-bolder fs-5">합계 (공급가액)</th>
                                                        <td id="summary_subtotal" class="text-end fw-bolder fs-5">
                                                            ￦ 0
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-gray-800 fw-bolder fs-5">부가세 (VAT)</th>
                                                        <td id="summary_vat" class="text-end fw-bolder fs-5">
                                                            ￦ 0
                                                        </td>
                                                    </tr>
                                                    <tr class="bg-light-primary">
                                                        <th class="text-primary fw-bolder fs-4">최종 합계 (총액)</th>
                                                        <td id="summary_final_total" class="text-end text-primary fw-bolder fs-4">
                                                            ￦ 0
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!--end::Basic info-->

                                    <div class="card mb-5 mb-xl-10">
                                        <div class="card-header border-0">
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">입금 현황</h3>
                                            </div>
                                        </div>
                                        <div class="card-body border-top p-9">
                                            <div id="depositForm" class="mb-10">
                                                <input type="hidden" id="depositSeq" />
                                                <div class="row g-4">
                                                    <div class="col-md-3">
                                                        <label class="form-label required">금액</label>
                                                        <input type="text" id="depositAmount" class="form-control" placeholder="금액 입력"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label">내용</label>
                                                        <select id="depositContentType" class="form-select">
                                                            <option value="선금">선금</option>
                                                            <option value="잔금">잔금</option>
                                                            <option value="부대시설비">부대시설비</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label">세금계산서</label>
                                                        <select id="depositTaxStatus" class="form-select">
                                                            <option value="미발행">미발행</option>
                                                            <option value="발행">발행</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label">참가비 수납 여부</label>
                                                        <select id="depositPaymentStatus" class="form-select">
                                                            <option value="미납">미납</option>
                                                            <option value="참가비 납부">참가비 납부</option>
                                                            <option value="50% 납부">50% 납부</option>
                                                            <option value="전액 납부">전액 납부</option>
                                                            <option value="완납(부대시설비)">완납(부대시설비)</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label required">입금일</label>
                                                        <input type="date" id="depositDate" class="form-control"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label">입금예정일</label>
                                                        <input type="date" id="depositScheduledDate" class="form-control"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label">입금자명</label>
                                                        <input type="text" id="depositDepositorName" class="form-control" placeholder="입금자명 입력"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-label">확인자명</label>
                                                        <input type="text" id="depositConfirmerName" class="form-control" placeholder="확인자명 입력"/>
                                                    </div>
                                                    <div class="col-12">
                                                        <label class="form-label">코멘트</label>
                                                        <input type="text" id="depositComment" class="form-control" placeholder="코멘트 입력"/>
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-end mt-4">
                                                    <button type="button" id="saveDepositBtn" class="btn btn-primary">추가</button>
                                                </div>
                                            </div>

                                            <div class="table-responsive">
                                                <table id="depositHistoryTable" class="table table-bordered align-middle">
                                                    <thead class="bg-light-primary">
                                                    <tr class="fw-bold text-gray-700 text-center">
                                                        <th>금액</th>
                                                        <th>내용</th>
                                                        <th>수납여부</th>
                                                        <th>입금일</th>
                                                        <th>입금예정일</th>
                                                        <th>세금계산서</th>
                                                        <th>입금자</th>
                                                        <th>확인자</th>
                                                        <th>코멘트</th>
                                                        <th>관리</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitor/participant/company.do" class="btn btn-info btn-active-light-info cursor-pointer">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="window.location.reload();" class="btn btn-danger btn-active-light-danger">변경내용취소</button>
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
                                                <table class="table table-bordered table-row-gray-400 table-rounded table-row-bordered border">
                                                    <!--begin::Table head-->
                                                    <thead class="bg-primary fw-semibold fs-6 text-white border-bottom-2 border-gray-200">
                                                        <tr class="text-center">
                                                            <th>선택</th>
                                                            <th>NO</th>
                                                            <th>제목</th>
                                                            <th>발송유형</th>
                                                            <th>수신메일</th>
                                                            <th>수신상태</th>
                                                            <th>발송결과</th>
                                                            <th>발송일시</th>
                                                        </tr>
                                                    </thead>
                                                    <!--end::Table head-->
                                                    <!--begin::Table body-->
                                                    <tbody>
                                                        <c:if test="${empty boothInvoiceList && empty utilityInvoiceList}">
                                                            <tr><td colspan="8" class="text-center">인보이스 내역 없음</td></tr>
                                                        </c:if>

                                                        <%-- 1. 전시부스 인보이스 목록 표시 --%>
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
                                                                <td>${invoice.recipientEmail}</td>
                                                                <td>${invoice.sendStatus}</td>
                                                                <td>[${invoice.sendResult eq null ? '-' : invoice.sendResult}] ${invoice.sendResultMsg eq null ? '-' : invoice.sendResultMsg}</td>
                                                                <td>${invoice.sendDttm eq null ? '-' : invoice.sendDttm}</td>
                                                            </tr>
                                                        </c:forEach>

                                                        <%-- 2. 유틸리티 인보이스 목록 표시 --%>
                                                        <c:forEach var="invoice" items="${utilityInvoiceList}">
                                                            <tr class="text-center align-middle">
                                                                <td>
                                                                    <%-- data-type="utility" 추가 --%>
                                                                    <span class="fw-semibold d-block fs-7 invoice-item">
                                                                        <input type="hidden" name="filePath" value="${invoice.filePath}"/>
                                                                        <input type="checkbox" name="invoiceSeq" value="${invoice.invoiceSeq}" data-type="utility" class="form-check-input">
                                                                    </span>
                                                                </td>
                                                                <td class="td_invoiceCode">${invoice.invoiceCode}</td>
                                                                <td>${invoice.title}</td>
                                                                <td>유틸리티</td>
                                                                <td>${invoice.recipientEmail}</td>
                                                                <td>${invoice.sendStatus}</td>
                                                                <td>[${invoice.sendResult eq null ? '-' : invoice.sendResult}] ${invoice.sendResultMsg eq null ? '-' : invoice.sendResultMsg}</td>
                                                                <td>${invoice.sendDttm eq null ? '-' : invoice.sendDttm}</td>
                                                            </tr>
                                                        </c:forEach>
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
                            <option value="utility">유틸리티</option>
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

    <script>var hostUrl = "/assets/";</script>
    <!--begin::Global Javascript Bundle(mandatory for all pages)-->
    <script src="/assets/plugins/global/plugins.bundle.js"></script>
    <script src="/assets/js/scripts.bundle.js"></script>
    <!--end::Global Javascript Bundle-->
    <!--begin::Vendors Javascript(used for this page only)-->
    <script src="/assets/plugins/custom/datatables/datatables.bundle.js"></script>
    <!--end::Vendors Javascript-->
    <!--begin::Custom Javascript(used for this page only)-->
    <script src="/assets/js/custom/apps/ecommerce/catalog/tables.js"></script>
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
    <script src="/js/custom/invoice.js?ver=<%=System.currentTimeMillis()%>"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>