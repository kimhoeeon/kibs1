<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>2027 경기국제보트쇼 관리자</title>
    <meta charset="utf-8"/>
    <meta name="description" content="2027 경기국제보트쇼 홈페이지"/>
    <meta name="keywords" content="KIBS"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta property="og:locale" content="ko_KR"/>
    <meta property="og:type" content="article"/>
    <meta property="og:title" content="KIBS"/>
    <meta property="og:url" content="https://koreaboatshow.or.kr/"/>
    <meta property="og:site_name" content="2027 KIBS | 경기국제보트쇼"/>
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
                        <div id="kt_app_toolbar_container" class="app-container container-xxl d-flex flex-stack">
                            <!--begin::Page title-->
                            <div class="page-title d-flex flex-column justify-content-center flex-wrap me-3">
                                <!--begin::Title-->
                                <h1 class="page-heading d-flex text-dark fw-bold fs-3 flex-column justify-content-center my-0">
                                    통계 현황</h1>
                                <!--end::Title-->
                                <!--begin::Breadcrumb-->
                                <ul class="breadcrumb breadcrumb-separatorless fw-semibold fs-7 my-0 pt-1">
                                    <!--begin::Item-->
                                    <li class="breadcrumb-item text-muted">
                                        <a href="/mng/main.do"
                                           class="text-muted text-hover-primary">Home</a>
                                    </li>
                                    <!--end::Item-->
                                </ul>
                                <!--end::Breadcrumb-->
                            </div>
                            <!--end::Page title-->
                            <!--begin::Export dropdown-->
                            <button type="button" onclick="f_mng_stat_excel_download()" class="btn btn-success btn-active-light-success" data-kt-menu-placement="bottom-end">
                                <i class="ki-duotone ki-exit-down fs-2">
                                    <span class="path1"></span>
                                    <span class="path2"></span>
                                </i>전체 통계 EXCEL 다운로드</button>
                            <!--end::Export dropdown-->
                        </div>
                        <!--end::Toolbar container-->
                    </div>
                    <!--end::Toolbar-->

                    <!--begin::Content-->
                    <div id="kt_app_content" class="app-content flex-column-fluid">
                        <!--begin::Content container-->
                        <div id="kt_app_content_container" class="app-container container-xxl">
                            <!--begin::Stats-->
                            <div class="row g-6 g-xl-9">
                                <div class="col-lg-12 col-xxl-12 mb-xl-8">
                                    <!--begin::Card-->
                                    <div class="card h-100">
                                        <!--begin::Card body-->
                                        <div class="card-body p-9">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bold fs-3 mb-1">안내사항</span>
                                                <span class="text-muted fw-semibold fs-7">Information</span>
                                            </h3>
                                            <div class="mt-5 mb-2">※ 현황 통계는 <span class="text-primary fw-bold">'2027'년 데이터</span>만 나타냅니다.</div>
                                            <div class="mb-2">※ 통계(<span class="text-primary fw-bold">'전체'</span>) 항목은 {(참가기업수-참가취소)+(참관객수-참관취소)} 의 값입니다.</div>
                                            <div class="mb-2">※ 그래프(<span class="text-primary fw-bold">'홈페이지 방문 현황'</span>) 항목은 메인페이지 접속 시에 +1 되어 도출된 값입니다.</div>
                                            <div class="mb-2">※ 그래프(<span class="text-primary fw-bold">'참가 신청 현황'</span>) 항목은 참가기업(취소제외), 참관객(취소제외) 수 현황입니다.</div>
                                            <div>※ 그래프(<span class="text-primary fw-bold">'부스별 신청업체 수'</span>) 항목은 부스별 신청업체 수를 백분율로 표현한 그래프입니다.</div>
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Card-->
                                </div>
                            </div>
                            <!--end::Stats-->

                            <!--begin::Stats-->
                            <div class="row g-6 g-xl-9">
                                <div class="col-lg-3 col-xxl-3 mb-xl-8">
                                    <!--begin::Card-->
                                    <div class="card h-100">
                                        <!--begin::Card body-->
                                        <div class="card-body p-9">
                                            <!--begin::Heading-->
                                            <div class="fs-2x fw-bold">참가기업 <span class="fs-3 text-danger">( 취소 )</span></div>
                                            <div class="fs-7 fw-semibold text-gray-500 mb-7">* 참가 신청 기업 수 ( 취소 신청 기업 수 )</div>
                                            <!--end::Heading-->
                                            <div class="separator"></div>
                                            <!--begin::Wrapper-->
                                            <div class="d-flex flex-wrap justify-content-center mt-5">
                                                <!--begin::Chart-->
                                                <div class="fs-3x d-flex align-items-start">
                                                    <div class="lh-sm fw-bolder">
                                                        ${companyStat.inCount.split(',')[0] eq null ? 0 : companyStat.inCount.split(',')[0]}
                                                            <span class="fs-3 text-danger">( ${companyStat.inCount.split(',')[1] eq null ? 0 : companyStat.inCount.split(',')[1]} )</span>
                                                    </div>
                                                </div>
                                                <!--end::Chart-->
                                            </div>
                                            <!--end::Wrapper-->
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Card-->
                                </div>
                                <div class="col-lg-3 col-xxl-3 mb-xl-8">
                                    <!--begin::Card-->
                                    <div class="card h-100">
                                        <!--begin::Card body-->
                                        <div class="card-body p-9">
                                            <!--begin::Heading-->
                                            <div class="fs-2x fw-bold">참관객 <span class="fs-3 text-danger">( 취소 )</span></div>
                                            <div class="fs-7 fw-semibold text-gray-500 mb-7">* 참관객 수 ( 취소 참관객 수 )</div>
                                            <!--end::Heading-->
                                            <div class="separator"></div>
                                            <!--begin::Wrapper-->
                                            <div class="d-flex flex-wrap justify-content-center mt-5">
                                                <!--begin::Chart-->
                                                <div class="fs-3x d-flex align-items-start">
                                                    <div class="lh-sm fw-bolder">
                                                            ${visitorStat.inCount.split(',')[0] eq null ? 0 : visitorStat.inCount.split(',')[0]}
                                                        <span class="fs-3 text-danger">( ${visitorStat.inCount.split(',')[1] eq null ? 0 : visitorStat.inCount.split(',')[1]} )</span>
                                                    </div>
                                                </div>
                                                <!--end::Chart-->
                                            </div>
                                            <!--end::Wrapper-->
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Card-->
                                </div>
                                <div class="col-lg-3 col-xxl-3 mb-xl-8">
                                    <!--begin::Card-->
                                    <div class="card h-100">
                                        <!--begin::Card body-->
                                        <div class="card-body p-9">
                                            <!--begin::Heading-->
                                            <div class="fs-2x fw-bold">전시품 수<span class="fs-3">( 전체 )</span> <span class="fs-3 text-danger">( 취소 )</span></div>
                                            <div class="fs-7 fw-semibold text-gray-500 mb-7">* 참가업체 전시품 수 합계</div>
                                            <!--end::Heading-->
                                            <div class="separator"></div>
                                            <!--begin::Wrapper-->
                                            <div class="d-flex flex-wrap justify-content-center mt-5">
                                                <!--begin::Chart-->
                                                <div class="fs-3x d-flex align-items-start">
                                                    <div class="lh-sm fw-bolder">
                                                        <c:set var="productYCnt" value="${productStat.inCount.split(',')[0] eq null ? 0 : productStat.inCount.split(',')[0]}"/>
                                                        <c:set var="productNCnt" value="${productStat.inCount.split(',')[1] eq null ? 0 : productStat.inCount.split(',')[1]}"/>
                                                        ${productYCnt + productNCnt}
                                                        <span class="fs-3 text-danger">( ${productNCnt} )</span>
                                                    </div>
                                                </div>
                                                <!--end::Chart-->
                                            </div>
                                            <!--end::Wrapper-->
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Card-->
                                </div>
                                <div class="col-lg-3 col-xxl-3 mb-xl-8">
                                    <!--begin::Card-->
                                    <div class="card h-100">
                                        <!--begin::Card body-->
                                        <div class="card-body p-9">
                                            <!--begin::Heading-->
                                            <div class="fs-2x fw-bold">보트/요트 수</div>
                                            <div class="fs-7 fw-semibold text-gray-500 mb-7">* 참가업체 보트/요트 합계</div>
                                            <!--end::Heading-->
                                            <div class="separator"></div>
                                            <!--begin::Wrapper-->
                                            <div class="d-flex flex-wrap justify-content-center mt-5">
                                                <!--begin::Chart-->
                                                <div class="fs-3x d-flex align-items-start">
                                                    <div class="lh-sm fw-bolder">
                                                        ${productQtyStat.inCount}
                                                    </div>
                                                </div>
                                                <!--end::Chart-->
                                            </div>
                                            <!--end::Wrapper-->
                                        </div>
                                        <!--end::Card body-->
                                    </div>
                                    <!--end::Card-->
                                </div>
                            </div>
                            <!--end::Stats-->

                            <!--begin::Row-->
                            <div class="row g-5 g-xl-8">

                                <div class="col-xl-6">
                                    <!--begin::Charts Widget 3-->
                                    <div class="card card-xl-stretch">
                                        <!--begin::Header-->
                                        <div class="card-header border-0 pt-5">

                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bold fs-3 mb-2">홈페이지 방문 현황</span>
                                                <span class="fs-7 fw-semibold text-gray-500">* 사이트 방문자 수</span>
                                            </h3>

                                            <!--begin::Toolbar-->
                                            <div class="homepage-visit card-toolbar" data-kt-buttons="true">
                                                <a class="btn btn-sm btn-color-muted btn-active btn-active-primary px-4 me-1"
                                                   id="kt_charts_widget_3_day_btn">Day</a>
                                                <a class="btn btn-sm btn-color-muted btn-active btn-active-primary px-4 me-1 active"
                                                   id="kt_charts_widget_3_week_btn">Week</a>
                                                <a class="btn btn-sm btn-color-muted btn-active btn-active-primary px-4 me-1"
                                                   id="kt_charts_widget_3_month_btn">Month</a>
                                            </div>
                                            <!--end::Toolbar-->
                                        </div>
                                        <!--end::Header-->
                                        <!--begin::Body-->
                                        <div class="card-body">
                                            <!--begin::Chart-->
                                            <div id="kt_charts_widget_3_chart" style="height: 350px"></div>
                                            <!--end::Chart-->
                                        </div>
                                        <!--end::Body-->
                                    </div>
                                    <!--end::Charts Widget 3-->
                                </div>


                                <div class="col-xl-6">
                                    <!--begin::Charts Widget 4-->
                                    <div class="card card-xl-stretch">
                                        <!--begin::Header-->
                                        <div class="card-header border-0 pt-5">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bold fs-3 mb-2">참가 신청 현황</span>
                                                <span class="fs-7 fw-semibold text-gray-500">* 초록(참가기업) / 노랑(참관객)</span>
                                            </h3>
                                            <!--begin::Toolbar-->
                                            <div class="participant-stat card-toolbar" data-kt-buttons="true">
                                                <a class="btn btn-sm btn-color-muted btn-active btn-active-primary px-4 me-1"
                                                   id="kt_charts_widget_4_day_btn">Day</a>
                                                <a class="btn btn-sm btn-color-muted btn-active btn-active-primary px-4 me-1 active"
                                                   id="kt_charts_widget_4_week_btn">Week</a>
                                                <a class="btn btn-sm btn-color-muted btn-active btn-active-primary px-4 me-1"
                                                   id="kt_charts_widget_4_month_btn">Month</a>
                                            </div>
                                            <!--end::Toolbar-->
                                        </div>
                                        <!--end::Header-->
                                        <!--begin::Body-->
                                        <div class="card-body">
                                            <!--begin::Chart-->
                                            <div id="kt_charts_widget_4_chart" style="height: 350px"></div>
                                            <!--end::Chart-->
                                        </div>
                                        <!--end::Body-->
                                    </div>
                                    <!--end::Charts Widget 4-->
                                </div>


                                <div class="col-xl-6">
                                    <!--begin::Charts Widget 4-->
                                    <div class="card card-xl-stretch">
                                        <!--begin::Header-->
                                        <div class="card-header border-0 pt-5">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bold fs-3 mb-2">부스별 신청업체 수</span>
                                                <span class="fs-7 fw-semibold text-gray-500">* 부스 신청 유형별 현황 백분율</span>
                                            </h3>
                                        </div>
                                        <!--end::Header-->
                                        <!--begin::Body-->
                                        <div class="card-body d-flex justify-content-center align-items-center pt-0">
                                            <!--begin::Chart-->
                                            <div id="kt_project_list_chart1"></div>
                                            <!--end::Chart-->
                                        </div>
                                        <!--end::Body-->
                                    </div>
                                    <!--end::Charts Widget 4-->
                                </div>

                                <div class="col-xl-6">
                                    <!--begin::Charts Widget 4-->
                                    <div class="card card-xl-stretch">
                                        <!--begin::Header-->
                                        <div class="card-header border-0 pt-5">
                                            <h3 class="card-title align-items-start flex-column">
                                                <span class="card-label fw-bold fs-3 mb-2">참가분야별 신청업체 수</span>
                                                <span class="fs-7 fw-semibold text-gray-500">* 참가분야별 신청 현황 그래프</span>
                                            </h3>
                                        </div>
                                        <!--end::Header-->
                                        <!--begin::Body-->
                                        <div class="card-body d-flex justify-content-end align-items-center pt-0">
                                            <!--begin::Chart-->
                                            <div id="kt_project_list_chart2"></div>
                                            <!--end::Chart-->
                                        </div>
                                        <!--end::Body-->
                                    </div>
                                    <!--end::Charts Widget 4-->
                                </div>

                            </div>
                            <!--end::Row-->
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
<script src="/assets/js/widgets.bundle.js"></script>
<script src="/assets/js/custom/widgets.js"></script>
<script src="/assets/js/custom/apps/chat/chat.js"></script>
<script src="/assets/js/custom/apps/projects/list/list.js"></script>
<script src="/assets/js/custom/utilities/modals/upgrade-plan.js"></script>
<script src="/assets/js/custom/utilities/modals/create-app.js"></script>
<script src="/assets/js/custom/utilities/modals/users-search.js"></script>
<!--end::Custom Javascript-->

<!--begin::Custom Javascript(used for common page)-->
<script src="/js/mngMain.js?ver=20260630"></script>

<!--end::Custom Javascript-->

<!--end::Javascript-->

<!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>