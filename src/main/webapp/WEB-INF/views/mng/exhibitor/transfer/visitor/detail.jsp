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
    <title>2025 경기국제보트쇼 관리자</title>
    <meta charset="utf-8"/>
    <meta name="description"
          content="2025 경기국제보트쇼 홈페이지"/>
    <meta name="keywords"
          content="KIBS"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta property="og:locale" content="ko_KR"/>
    <meta property="og:type" content="article"/>
    <meta property="og:title"
          content="KIBS"/>
    <meta property="og:url" content="https://www.koreaboatshow.or.kr/"/>
    <meta property="og:site_name" content="2025 KIBS | 경기국제보트쇼"/>
    <link rel="canonical" href="https://www.koreaboatshow.or.kr/"/>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <!--begin::Fonts(mandatory for all pages)-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Inter:300,400,500,600,700"/>
    <!--end::Fonts-->
    <!--begin::Vendor Stylesheets(used for this page only)-->

    <link href="/assets/plugins/custom/datatables/datatables.bundle.css"
          rel="stylesheet" type="text/css"/>
    <!--end::Vendor Stylesheets-->
    <!--begin::Global Stylesheets Bundle(mandatory for all pages)-->
    <link href="/assets/plugins/global/plugins.bundle.css" rel="stylesheet"
          type="text/css"/>
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
<c:if test="${status ne 'logon'}">
    <script>
        alert("로그인해 주세요.");
        location.href = '/mng/index.do';
    </script>
</c:if>

<c:if test="${status eq 'logon'}">

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
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary"
                                                           href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_visitor">참가자 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 active text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_transfer">이관정보</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_application">참가신청서관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                </ul>
                                            </div>
                                            <!--end:Tabs nav-->
                                            <!--begin:Tab content-->
                                            <div class="tab-content py-4 py-lg-8 px-lg-7">
                                                <!--begin:Tab pane-->
                                                <div class="tab-pane w-lg-350px"
                                                     id="kt_app_header_menu_pages_visitor">
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
                                                                           class="menu-link">
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
                                                                        <a href="/mng/exhibitor/member.do"
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
                                                <div class="tab-pane active w-lg-350px" id="kt_app_header_menu_pages_transfer">
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
                                                                           class="menu-link active">
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
                                                <div class="tab-pane w-lg-350px"
                                                     id="kt_app_header_menu_pages_application">
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
                                                           href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_product">올해의 제품</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
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
                                                           href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_notice">게시판 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_popup">팝업 관리</a>
                                                    </li>
                                                    <!--end:Nav item-->
                                                    <%--<!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                                           data-bs-toggle="tab"
                                                           data-bs-target="#kt_app_header_menu_pages_document">나의
                                                            서류함</a>
                                                    </li>
                                                    <!--end:Nav item-->--%>
                                                    <!--begin:Nav item-->
                                                    <li class="nav-item mx-lg-1">
                                                        <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
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
                                                <div class="tab-pane active w-lg-400px"
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
                                                                            <span class="menu-title">DB 관리</span>
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
                                                KIBS 관리자
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
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion">
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
                                            <a class="menu-link" href="/mng/exhibitor/participant/company.do">
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
                                <div data-kt-menu-trigger="click" class="menu-item menu-accordion hover show">
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
                                            <a class="menu-link active" href="/mng/exhibitor/transfer/visitor.do">
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
                                        <i class="ki-duotone ki-file fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                            <span class="path3"></span>
                                            <span class="path4"></span>
                                            <span class="path5"></span>
                                        </i>
                                    </span>
                                    <span class="menu-title">참가신청서 관리</span>
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
                                        <%--<!--begin:Menu item-->
                                        <div class="menu-item">
                                            <!--begin:Menu link-->
                                            <a class="menu-link" href="/mng/exhibitor/application/banner.do">
                                            <span class="menu-bullet">
                                                <span class="bullet bullet-dot"></span>
                                            </span>
                                                <span class="menu-title">웹 배너 신청</span>
                                            </a>
                                            <!--end:Menu link-->
                                        </div>
                                        <!--end:Menu item-->--%>
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
                                        지난행사 참관객</h1>
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
                                        <li class="breadcrumb-item text-muted">이관정보</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">지난행사 참관객</li>
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
                                <form id="visitorForm" name="visitorForm" method="post" onsubmit="return false;">
                                    <input type="hidden" name="seq" value="${info.seq}">
                                    <input type="hidden" name="visitorSeq" value="${info.seq}">
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">참관객 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_visitor_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">성명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="name" name="name" class="form-control form-control-lg form-control-solid-bg" value="${info.name}" placeholder="성명"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전화번호</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="tel" id="tel" name="tel" class="form-control form-control-lg form-control-solid-bg onlyTel" value="${info.tel}" placeholder="전화번호" maxlength="14"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">
                                                        <span class="required">휴대전화</span>
                                                    </label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="tel" id="phone" name="phone" class="form-control form-control-lg form-control-solid-bg onlyTel" value="${info.phone}" placeholder="휴대전화" maxlength="13"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">이메일</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <div class="input-group">
                                                            <input type="text" id="email" name="email" value="${info.email}" class="form-control form-control-solid-bg" placeholder="이메일" aria-label="Username"/>
                                                            <span class="input-group-text">@</span>
                                                            <input type="text" id="domain" name="domain" value="${info.domain}" class="form-control form-control-solid-bg" placeholder="도메인" aria-label="Server"/>
                                                            <select id="email_select" class="form-select form-control-solid-bg ml10" aria-label="Select Email">
                                                                <option selected>직접입력</option>
                                                                <option value="daum.net" <c:if test="${info.domain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                                <option value="nate.com" <c:if test="${info.domain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                                <option value="hanmail.net" <c:if test="${info.domain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                                <option value="naver.com" <c:if test="${info.domain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                                <option value="hotmail.com" <c:if test="${info.domain eq 'hotmail.com'}">selected</c:if> >hotmail.com</option>
                                                                <option value="yahoo.co.kr" <c:if test="${info.domain eq 'yahoo.co.kr'}">selected</c:if> >yahoo.co.kr</option>
                                                                <option value="empal.com" <c:if test="${info.domain eq 'empal.com'}">selected</c:if> >empal.com</option>
                                                                <option value="korea.com" <c:if test="${info.domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                                <option value="hanmir.com" <c:if test="${info.domain eq 'hanmir.com'}">selected</c:if> >hanmir.com</option>
                                                                <option value="dreamwiz.com" <c:if test="${info.domain eq 'dreamwiz.com'}">selected</c:if> >dreamwiz.com</option>
                                                                <option value="orgio.net" <c:if test="${info.domain eq 'orgio.net'}">selected</c:if> >orgio.net</option>
                                                                <option value="korea.com" <c:if test="${info.domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                                <option value="hitel.net" <c:if test="${info.domain eq 'hitel.net'}">selected</c:if> >hitel.net</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">직장명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="companyName" name="companyName" value="${info.companyName}" class="form-control form-control-lg form-control-solid-bg" />
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <button type="button" class="btn btn-primary" id="companySearch" onclick="">검색</button>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">동반자 여부</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="Y" id="partnerY" name="partnerYn" <c:if test="${info.partnerYn eq 'Y'}">checked</c:if> />
                                                            <label class="form-check-label" for="partnerY">
                                                                있음
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="N" id="partnerN" name="partnerYn" <c:if test="${info.partnerYn eq 'N' or info.partnerYn eq null}">checked</c:if> />
                                                            <label class="form-check-label" for="partnerN">
                                                                없음
                                                            </label>
                                                        </div>
                                                        <div class="w-100 mt10">개별 신청이 어려운 어린이, 노약자일 경우에 선택해 주세요.</div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->

                                                <!--begin::partner add group-->
                                                <div class="form_add_partner">

                                                    <c:if test="${empty partnerList}">
                                                        <!--begin::partner info box-->
                                                        <div class="partner_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    동반자 #<span class="partnerInfoNum">1</span>
                                                                    <span class="partnerInfoDel float-end" style="cursor: pointer;">삭제</span>
                                                                    <input type="hidden" name="partnerSeq" value="">
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">이름</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerName" class="form-control form-control-lg form-control-solid-bg" placeholder="이름" maxlength="10" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">나이</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerAge" class="form-control form-control-lg form-control-solid-bg onlyNum" maxlength="2" placeholder="나이" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                        </div>
                                                        <!--end::partner info box-->
                                                    </c:if>

                                                    <c:if test="${not empty partnerList}">
                                                    <c:forEach var="partner" items="${partnerList}" begin="0" end="${partnerList.size()}" step="1" varStatus="status">
                                                        <!--begin::partner info box-->
                                                        <div class="partner_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    동반자 #<span class="partnerInfoNum">${status.index + 1}</span>
                                                                    <span class="partnerInfoDel float-end" style="cursor: pointer;">삭제</span>
                                                                    <input type="hidden" name="partnerSeq" value="${partner.seq}">
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">이름</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerName" value="${partner.partnerName}" class="form-control form-control-lg form-control-solid-bg" placeholder="이름" maxlength="10" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">나이</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerAge" value="${partner.partnerAge}" class="form-control form-control-lg form-control-solid-bg onlyNum" maxlength="2" placeholder="나이" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                        </div>
                                                        <!--end::partner info box-->
                                                    </c:forEach>
                                                    </c:if>

                                                    <!--begin::Actions-->
                                                    <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                        <span class="btn btn-primary" id="kt_partner_info_add">추가</span>
                                                    </div>
                                                    <!--end::Actions-->
                                                </div>
                                                <!--begin::partner info group-->

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
                                                <h3 class="fw-bold m-0">설문항목</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_field_part_info" class="collapse show">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">성별</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="남자" id="sex_man" name="sex" <c:if test="${info.sex eq '남자'}">checked</c:if> />
                                                            <label class="form-check-label" for="sex_man">
                                                                남자
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="여자" id="sex_woman" name="sex" <c:if test="${info.sex eq '여자' or info.sex eq null}">checked</c:if> />
                                                            <label class="form-check-label" for="sex_woman">
                                                                여자
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">지역</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="input-group">
                                                            <select id="sido" name="sido" class="form-select form-control-solid-bg" aria-label="Select sido"></select>
                                                            <select id="gugun" name="gugun" class="form-select form-control-solid-bg ml10" aria-label="Select gugun"></select>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">연령대</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="10" id="age10" name="age" <c:if test="${info.ageGroup eq '10' or info.ageGroup eq null}">checked</c:if> />
                                                            <label class="form-check-label" for="age10">
                                                                10대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="20" id="age20" name="age" <c:if test="${info.ageGroup eq '20'}">checked</c:if> />
                                                            <label class="form-check-label" for="age20">
                                                                20대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="30" id="age30" name="age" <c:if test="${info.ageGroup eq '30'}">checked</c:if> />
                                                            <label class="form-check-label" for="age30">
                                                                30대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="40" id="age40" name="age" <c:if test="${info.ageGroup eq '40'}">checked</c:if> />
                                                            <label class="form-check-label" for="age40">
                                                                40대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="50" id="age50" name="age" <c:if test="${info.ageGroup eq '50'}">checked</c:if> />
                                                            <label class="form-check-label" for="age50">
                                                                50대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="60" id="age60" name="age" <c:if test="${info.ageGroup eq '60'}">checked</c:if> />
                                                            <label class="form-check-label" for="age60">
                                                                60대 이상
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">참관 구분</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn1" name="observationGbn" value="조종면허 보유자"
                                                                    <c:if test="${fn:contains(info.observationGbn, '조종면허 보유자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn1">
                                                                조종면허 보유자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn2" name="observationGbn" value="보트 소유자"
                                                                   <c:if test="${fn:contains(info.observationGbn, '보트 소유자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn2">
                                                                보트 소유자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn3" name="observationGbn" value="보트 구매 예정자"
                                                                   <c:if test="${fn:contains(info.observationGbn, '보트 구매 예정자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn3">
                                                                보트 구매 예정자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn4" name="observationGbn" value="관련 업종 종사자"
                                                                   <c:if test="${fn:contains(info.observationGbn, '관련 업종 종사자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn4">
                                                                관련 업종 종사자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn5" name="observationGbn" value="낚시 매니아"
                                                                   <c:if test="${fn:contains(info.observationGbn, '낚시 매니아')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn5">
                                                                낚시 매니아
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn6" name="observationGbn" value="다이빙 매니아"
                                                                   <c:if test="${fn:contains(info.observationGbn, '다이빙 매니아')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn6">
                                                                다이빙 매니아
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn7" name="observationGbn" value="캠핑카 매니아"
                                                                   <c:if test="${fn:contains(info.observationGbn, '캠핑카 매니아')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn7">
                                                                캠핑카 매니아
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn8" name="observationGbn" value="학생"
                                                                   <c:if test="${fn:contains(info.observationGbn, '학생')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn8">
                                                                학생
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn9" name="observationGbn" value="일반관람"
                                                                   <c:if test="${fn:contains(info.observationGbn, '일반관람')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn9">
                                                                일반관람
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn10" name="observationGbn" value="기타"
                                                                   <c:if test="${fn:contains(info.observationGbn, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn10">
                                                                기타
                                                            </label>
                                                        </div>
                                                        <!--begin::Col-->
                                                        <div class="col-lg-12 mt-2 text-primary fw-bold">
                                                            * 복수응답가능
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">보트쇼 방문 목적</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose1" name="visitPurpose" value="업계동향 파악 및 정보수집"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '업계동향 파악 및 정보수집')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose1">
                                                                업계동향 파악 및 정보수집
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose2" name="visitPurpose" value="제품구매 및 기술도입 상담"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '제품구매 및 기술도입 상담')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose2">
                                                                제품구매 및 기술도입 상담
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose3" name="visitPurpose" value="기존 거래업체 방문"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '기존 거래업체 방문')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose3">
                                                                기존 거래업체 방문
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose4" name="visitPurpose" value="차기 전시회 참가여부 파악"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '차기 전시회 참가여부 파악')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose4">
                                                                차기 전시회 참가여부 파악
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose5" name="visitPurpose" value="일반관람"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '일반관람')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose5">
                                                                일반관람
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose6" name="visitPurpose" value="기타"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose6">
                                                                기타
                                                            </label>
                                                        </div>
                                                        <!--begin::Col-->
                                                        <div class="col-lg-12 mt-2 text-primary fw-bold">
                                                            * 복수응답가능
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">관심품목</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item1" name="interestItem" value="요트/보트"
                                                                   <c:if test="${fn:contains(info.interestItem, '요트/보트')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item1">
                                                                요트/보트
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item2" name="interestItem" value="워크보트"
                                                                   <c:if test="${fn:contains(info.interestItem, '워크보트')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item2">
                                                                워크보트
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item3" name="interestItem" value="낚시"
                                                                   <c:if test="${fn:contains(info.interestItem, '낚시')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item3">
                                                                낚시
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item4" name="interestItem" value="다이빙"
                                                                   <c:if test="${fn:contains(info.interestItem, '다이빙')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item4">
                                                                다이빙
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item5" name="interestItem" value="카누/카약/무동력"
                                                                   <c:if test="${fn:contains(info.interestItem, '카누/카약/무동력')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item5">
                                                                카누/카약/무동력
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item6" name="interestItem" value="워터스포츠"
                                                                   <c:if test="${fn:contains(info.interestItem, '워터스포츠')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item6">
                                                                워터스포츠
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item7" name="interestItem" value="해양부품"
                                                                   <c:if test="${fn:contains(info.interestItem, '해양부품')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item7">
                                                                해양부품
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item8" name="interestItem" value="캠핑"
                                                                   <c:if test="${fn:contains(info.interestItem, '캠핑')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item8">
                                                                캠핑
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item9" name="interestItem" value="친환경"
                                                                   <c:if test="${fn:contains(info.interestItem, '친환경')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item9">
                                                                친환경
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item10" name="interestItem" value="해양관광"
                                                                   <c:if test="${fn:contains(info.interestItem, '해양관광')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item10">
                                                                해양관광
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item11" name="interestItem" value="기타"
                                                                   <c:if test="${fn:contains(info.interestItem, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item11">
                                                                기타
                                                            </label>
                                                        </div>
                                                        <!--begin::Col-->
                                                        <div class="col-lg-12 mt-2 text-primary fw-bold">
                                                            * 복수응답가능
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">인지경로</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path1" name="recognizePath" value="뉴스레터"
                                                                   <c:if test="${fn:contains(info.recognizePath, '뉴스레터')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path1">
                                                                뉴스레터
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path2" name="recognizePath" value="옥외광고물"
                                                                   <c:if test="${fn:contains(info.recognizePath, '옥외광고물')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path2">
                                                                옥외광고물
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path3" name="recognizePath" value="홈페이지"
                                                                   <c:if test="${fn:contains(info.recognizePath, '홈페이지')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path3">
                                                                홈페이지
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path4" name="recognizePath" value="전문지"
                                                                   <c:if test="${fn:contains(info.recognizePath, '전문지')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path4">
                                                                전문지
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path5" name="recognizePath" value="온라인 커뮤니티"
                                                                   <c:if test="${fn:contains(info.recognizePath, '온라인 커뮤니티')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path5">
                                                                온라인 커뮤니티
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path6" name="recognizePath" value="오프라인매장"
                                                                   <c:if test="${fn:contains(info.recognizePath, '오프라인매장')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path6">
                                                                오프라인매장
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path7" name="recognizePath" value="소셜 네트워크"
                                                                   <c:if test="${fn:contains(info.recognizePath, '소셜 네트워크')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path7">
                                                                소셜 네트워크
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path8" name="recognizePath" value="초청장"
                                                                   <c:if test="${fn:contains(info.recognizePath, '초청장')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path8">
                                                                초청장
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path9" name="recognizePath" value="방송광고"
                                                                   <c:if test="${fn:contains(info.recognizePath, '방송광고')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path9">
                                                                방송광고
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path10" name="recognizePath" value="KIBS 2024 참석"
                                                                   <c:if test="${fn:contains(info.recognizePath, 'KIBS 2024 참석')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path10">
                                                                KIBS 2024 참석
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path11" name="recognizePath" value="낚시박람회"
                                                                   <c:if test="${fn:contains(info.recognizePath, '낚시박람회')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path11">
                                                                낚시박람회
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path12" name="recognizePath" value="기타"
                                                                   <c:if test="${fn:contains(info.recognizePath, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path12">
                                                                기타
                                                            </label>
                                                        </div>
                                                        <!--begin::Col-->
                                                        <div class="col-lg-12 mt-2 text-primary fw-bold">
                                                            * 복수응답가능
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">지난 전시회 참관 여부</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="first" id="pre_exhibit_yn_ko_first" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, 'first')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_first">
                                                                첫 참관
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2008" id="pre_exhibit_yn_ko_08" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2008')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_08">
                                                                2008
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2009" id="pre_exhibit_yn_ko_09" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2009')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_09">
                                                                2009
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2010" id="pre_exhibit_yn_ko_10" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2010')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_10">
                                                                2010
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2011" id="pre_exhibit_yn_ko_11" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2011')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_11">
                                                                2011
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2012" id="pre_exhibit_yn_ko_12" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2012')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_12">
                                                                2012
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2013" id="pre_exhibit_yn_ko_13" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2013')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_13">
                                                                2013
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2014" id="pre_exhibit_yn_ko_14" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2014')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_14">
                                                                2014
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2015" id="pre_exhibit_yn_ko_15" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2015')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_15">
                                                                2015
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2016" id="pre_exhibit_yn_ko_16" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2016')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_16">
                                                                2016
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2008" id="pre_exhibit_yn_ko_17" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2017')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_17">
                                                                2017
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2018" id="pre_exhibit_yn_ko_18" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2018')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_18">
                                                                2018
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2019" id="pre_exhibit_yn_ko_19" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2019')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_19">
                                                                2019
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2020" id="pre_exhibit_yn_ko_20" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2020')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_20">
                                                                2020
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2021" id="pre_exhibit_yn_ko_21" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2021')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_21">
                                                                2021
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2022" id="pre_exhibit_yn_ko_22" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2022')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_22">
                                                                2022
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2023" id="pre_exhibit_yn_ko_23" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2023')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_23">
                                                                2023
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2024" id="pre_exhibit_yn_ko_24" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2024')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_24">
                                                                2024
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">Whether you attended the last exhibition</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="first" id="pre_exhibit_yn_en_1" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, 'first')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_1">
                                                                One's first visit
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2008" id="pre_exhibit_yn_en_08" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2008')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_08">
                                                                2008
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2009" id="pre_exhibit_yn_en_09" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2009')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_09">
                                                                2009
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2010" id="pre_exhibit_yn_en_10" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2010')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_10">
                                                                2010
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2011" id="pre_exhibit_yn_en_11" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2011')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_11">
                                                                2011
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2012" id="pre_exhibit_yn_en_12" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2012')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_12">
                                                                2012
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2013" id="pre_exhibit_yn_en_13" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2013')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_13">
                                                                2013
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2014" id="pre_exhibit_yn_en_14" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2014')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_14">
                                                                2014
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2015" id="pre_exhibit_yn_en_15" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2015')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_15">
                                                                2015
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2016" id="pre_exhibit_yn_en_16" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2016')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_16">
                                                                2016
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2008" id="pre_exhibit_yn_en_17" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2017')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_17">
                                                                2017
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2018" id="pre_exhibit_yn_en_18" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2018')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_18">
                                                                2018
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2019" id="pre_exhibit_yn_en_19" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2019')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_19">
                                                                2019
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2020" id="pre_exhibit_yn_en_20" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2020')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_20">
                                                                2020
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2021" id="pre_exhibit_yn_en_21" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2021')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_21">
                                                                2021
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2022" id="pre_exhibit_yn_en_22" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2022')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_22">
                                                                2022
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2023" id="pre_exhibit_yn_en_23" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2023')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_23">
                                                                2023
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2024" id="pre_exhibit_yn_en_24" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2024')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_24">
                                                                2024
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitor/transfer/visitor.do" class="btn btn-info btn-active-light-info">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_transfer_visitor_detail('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_transfer_visitor_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
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
                            <div class="text-dark order-2 order-md-1"></div>
                            <!--end::Copyright-->
                            <!--begin::Menu-->
                            <ul class="menu menu-gray-600 menu-hover-primary fw-semibold order-1"></ul>
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
    <script src="/assets/js/custom/apps/ecommerce/catalog/tables.js"></script>
    <script src="/assets/js/widgets.bundle.js"></script>
    <script src="/assets/js/custom/widgets.js"></script>
    <script src="/assets/js/custom/apps/chat/chat.js"></script>
    <script src="/assets/js/custom/utilities/modals/upgrade-plan.js"></script>
    <script src="/assets/js/custom/utilities/modals/create-app.js"></script>
    <script src="/assets/js/custom/utilities/modals/users-search.js"></script>
    <!--end::Custom Javascript-->

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/custom/transferVisitor.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/sido.js"></script>

    <c:if test="${info ne null}">
        <c:if test="${info.partnerYn eq 'Y'}">
            <script> $(function(){ $('.form_add_partner').show(); }); </script>
        </c:if>
        <c:if test="${info.partnerYn eq 'N'}">
            <script> $(function(){ $('.form_add_partner').hide(); }); </script>
        </c:if>

        <c:if test="${info.regionSi ne null and info.regionSi ne ''}">
            <script> $(function(){ $('#sido').val('${info.regionSi}'); $('#sido').change(); });</script>
        </c:if>
        <c:if test="${info.regionGu ne null and info.regionGu ne ''}">
            <script> $(function(){ $('#gugun').val('${info.regionGu}') });</script>
        </c:if>
    </c:if>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>