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
      data-kt-app-sidebar-push-footer="true" data-kt-app-toolbar-enabled="true"
      data-kt-app-page-loading-enabled="true" data-kt-app-page-loading="on"
      class="app-default">
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
                                        팝업 등록</h1>
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
                                        <li class="breadcrumb-item text-muted">정보센터</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">팝업 관리</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">팝업 등록</li>
                                        <!--end::Item-->
                                    </ul>
                                    <!--end::Breadcrumb-->
                                </div>
                                <!--end::Page title-->
                                <!--begin::Actions-->
                                <div class="d-flex align-items-center gap-2 gap-lg-3">
                                    <!--begin::Export dropdown-->
                                    <button type="button" onclick="f_excel_export('kt_center_popup_add_table', '팝업_등록')" class="btn btn-success btn-active-light-success" data-kt-export="excel" data-kt-menu-placement="bottom-end">
                                        <i class="ki-duotone ki-exit-down fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>Export as Excel</button>
                                    <!--end::Export dropdown-->
                                </div>
                                <!--end::Actions-->

                                <!--begin::Hide default export buttons-->
                                <div id="kt_datatable_excel_hidden_buttons" class="d-none"></div>
                                <!--end::Hide default export buttons-->
                            </div>
                            <!--end::Toolbar container-->
                        </div>
                        <!--end::Toolbar-->
                        <!--begin::Content-->
                        <div id="kt_app_content" class="app-content flex-column-fluid">
                            <!--begin::Content container-->
                            <div id="kt_app_content_container" class="app-container container-full">
                                <!--begin::Products-->
                                <div class="card card-flush">
                                    <!--begin::Card header-->
                                    <div class="card-header align-items-center py-5 gap-2">
                                        <!--begin::Card title-->
                                        <div class="card-title w-100">
                                            <%--begin::검색구분--%>
                                            <div class="w-100 mw-125px">
                                                <!--begin::Select2-->
                                                <select id="search_box" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-placeholder="- 제목 -">
                                                    <option></option>
                                                    <option value="title" selected>제목</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::검색구분--%>

                                            <!--begin::Search-->
                                            <div class="d-flex align-items-center position-relative my-1 mr15 ml15">
                                                <i class="ki-duotone ki-magnifier fs-3 position-absolute ms-4">
                                                    <span class="path1"></span>
                                                    <span class="path2"></span>
                                                </i>
                                                <input type="text" id="search_text" name="search_text" value="" class="form-control form-control-solid w-250px ps-12" placeholder="Search"/>
                                            </div>
                                            <!--end::Search-->
                                            <!--begin:Action-->
                                            <div class="d-flex align-items-center">
                                                <button type="button" onclick="f_center_popup_search()" class="btn btn-primary me-5">Search</button>
                                                <button type="button" onclick="f_search_condition_init()" class="btn btn-secondary me-5">
                                                    <i class="ki-duotone ki-arrows-circle fs-3">
                                                        <i class="path1"></i>
                                                        <i class="path2"></i>
                                                    </i>검색조건 초기화</button>
                                            </div>
                                            <!--end:Action-->
                                        </div>
                                        <!--end::Card title-->
                                        <!--begin::Card toolbar-->
                                        <div class="card-toolbar flex-row-fluid gap-5">

                                            <%--begin::참가신청 언어--%>
                                            <div class="w-100 mw-175px">
                                                <!--begin::Select2-->
                                                <select id="lang" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 언어 -" onchange="f_search_condition_sel_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 언어 -</option>
                                                    <option value="KO">KOR</option>
                                                    <option value="EN">ENG</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::참가신청 언어--%>

                                            <%--begin::환불내역존재--%>
                                            <div class="w-100 mw-150px">
                                                <div class="form-check">
                                                    <label class="form-check-label" for="condition_use_yn">
                                                        사용 팝업만 보기
                                                    </label>
                                                    <input class="form-check-input" type="checkbox" id="condition_use_yn" onchange="f_search_condition_sel_change()"/>
                                                </div>
                                            </div>
                                            <%--end::환불내역존재--%>

                                            <!--begin::팝업 등록-->
                                            <a href="/mng/center/popup/add/detail.do" class="btn btn-primary ms-auto">팝업 등록</a>
                                            <!--end::팝업 등록-->
                                        </div>
                                        <!--end::Card toolbar-->
                                    </div>
                                    <!--end::Card header-->
                                    <!--begin::Card body-->
                                    <div class="card-body pt-0">
                                        <div class="fw-bold"><span class="mr10">검색결과</span><span id="search_cnt" style="color: #009ef7;">0</span> 개</div>
                                        <!--begin::Table-->
                                        <table class="table align-middle table-row-dashed fs-6 gy-5" id="kt_center_popup_add_table">
                                            <thead>
                                            <tr class="text-start text-gray-400 fw-bold fs-7 text-uppercase gs-0">
                                                <th class="text-center min-w-50px">번호</th>
                                                <th>SEQ</th>
                                                <th class="text-center min-w-80px">언어</th>
                                                <th class="text-center min-w-100px">사용여부</th>
                                                <th class="text-center min-w-150px">타이틀</th>
                                                <th class="text-center min-w-150px">게시일시</th>
                                                <th class="text-center min-w-150px">만료일시</th>
                                                <th class="text-center min-w-150px">등록일시</th>
                                                <th class="text-center min-w-150px">수정일시</th>
                                                <th class="text-center min-w-100px">기능</th>
                                            </tr>
                                            </thead>
                                            <tbody class="fw-semibold text-gray-600">
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <!--end::Table-->
                                    </div>
                                    <!--end::Card body-->
                                </div>
                                <!--end::Products-->
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
    <!--end::Custom Javascript-->

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=20260630"></script>
    <script src="/js/custom/popup.js?ver=20260630"></script>

    <script>
        document.addEventListener("keyup", function(event) {
            if (event.key === 'Enter') {
                f_center_popup_search();
            }
        });
    </script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>