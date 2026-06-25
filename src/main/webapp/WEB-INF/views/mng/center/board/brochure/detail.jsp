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
                                        브로슈어</h1>
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
                                        <li class="breadcrumb-item text-muted">게시판 관리</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">브로슈어</li>
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
                                <form id="brochure_form" class="form d-flex flex-column flex-lg-row"
                                      method="post" enctype="multipart/form-data"
                                      action="/mng/center/brochure/update.do"> <%-- 저장 로직은 이 URL로 가정 --%>

                                    <div class="d-flex flex-column flex-row-fluid gap-7 gap-lg-10">
                                        <div class="card card-flush py-4">
                                            <div class="card-header">
                                                <div class="card-title">
                                                    <h2>브로슈어 파일 관리</h2>
                                                </div>
                                            </div>
                                            <div class="card-body pt-0">

                                                <div class="mb-10 fv-row">
                                                    <div class="form-text fs-6">
                                                        * 표시할 파일명을 입력하지 않을 경우 첨부한 파일명으로 자동입력되며, 표시할 파일명은 수정 가능합니다.<br>
                                                        * 파일은 <span class="text-danger fw-bold">PDF</span> 형식만 첨부 가능합니다.
                                                    </div>
                                                </div>

                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-bold fs-6">메인 국문 브로슈어</label>
                                                    <div class="col-lg-10">
                                                        <input type="text" name="mainKoTitle" class="form-control mb-3 brochure-title" placeholder="표시할 파일명 입력 (예: 2026 경기국제보트쇼 브로슈어)" value="${brochureInfo.mainKoTitle}" data-file-input-id="mainKoFile" />
                                                        <input type="file" name="mainKoFile" id="mainKoFile" class="form-control brochure-file-input" accept=".pdf" />
                                                        <c:if test="${not empty brochureInfo.mainKoPath}">
                                                            <c:set var="pathParts" value="${fn:split(brochureInfo.mainKoPath, '/')}" />
                                                            <c:set var="originalFileName" value="${pathParts[fn:length(pathParts) - 1]}" />
                                                            <div class="form-text text-primary mt-2">
                                                                * 현재 파일:
                                                                <a href="${brochureInfo.mainKoPath}" download="${brochureInfo.mainKoTitle}">
                                                                    ${brochureInfo.mainKoTitle}
                                                                </a>
                                                                <span class="text-muted ms-3">(원본: ${brochureInfo.mainKoOriginalName})</span>
                                                                <input type="hidden" name="mainKoFile_existing" value="${brochureInfo.mainKoPath}" />
                                                                <input type="hidden" name="mainKoOriginalName_existing" value="${brochureInfo.mainKoOriginalName}" />
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-bold fs-6">메인 영문 브로슈어</label>
                                                    <div class="col-lg-10">
                                                        <input type="text" name="mainEnTitle" class="form-control mb-3 brochure-title" placeholder="표시할 파일명 입력 (예: 2026 KIBS Brochure)" value="${brochureInfo.mainEnTitle}" data-file-input-id="mainEnFile" />
                                                        <input type="file" name="mainEnFile" id="mainEnFile" class="form-control brochure-file-input" accept=".pdf" />
                                                        <c:if test="${not empty brochureInfo.mainEnPath}">
                                                            <c:set var="pathParts" value="${fn:split(brochureInfo.mainEnPath, '/')}" />
                                                            <c:set var="originalFileName" value="${pathParts[fn:length(pathParts) - 1]}" />
                                                            <div class="form-text text-primary mt-2">
                                                                * 현재 파일:
                                                                <a href="${brochureInfo.mainEnPath}" download="${brochureInfo.mainEnTitle}">
                                                                    ${brochureInfo.mainEnTitle}
                                                                </a>
                                                                <span class="text-muted ms-3">(원본: ${brochureInfo.mainEnOriginalName})</span>
                                                                <input type="hidden" name="mainEnFile_existing" value="${brochureInfo.mainEnPath}" />
                                                                <input type="hidden" name="mainEnOriginalName_existing" value="${brochureInfo.mainEnOriginalName}" />
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-bold fs-6">KISS 브로슈어</label>
                                                    <div class="col-lg-10">
                                                        <input type="text" name="kissTitle" class="form-control mb-3 brochure-title" placeholder="표시할 파일명 입력 (예: 2026 코리아서프쇼 브로슈어)" value="${brochureInfo.kissTitle}" data-file-input-id="kissFile" />
                                                        <input type="file" name="kissFile" id="kissFile" class="form-control brochure-file-input" accept=".pdf" />
                                                        <c:if test="${not empty brochureInfo.kissPath}">
                                                            <c:set var="pathParts" value="${fn:split(brochureInfo.kissPath, '/')}" />
                                                            <c:set var="originalFileName" value="${pathParts[fn:length(pathParts) - 1]}" />
                                                            <div class="form-text text-primary mt-2">
                                                                * 현재 파일:
                                                                <a href="${brochureInfo.kissPath}" download="${brochureInfo.kissTitle}">
                                                                    ${brochureInfo.kissTitle}
                                                                </a>
                                                                <span class="text-muted ms-3">(원본: ${brochureInfo.kissOriginalName})</span>
                                                                <input type="hidden" name="kissFile_existing" value="${brochureInfo.kissPath}" />
                                                                <input type="hidden" name="kissOriginalName_existing" value="${brochureInfo.kissOriginalName}" />
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-bold fs-6">KMTS 브로슈어</label>
                                                    <div class="col-lg-10">
                                                        <input type="text" name="kmtsTitle" class="form-control mb-3 brochure-title" placeholder="표시할 파일명 입력 (예: 2026 해양관광전 브로슈어)" value="${brochureInfo.kmtsTitle}" data-file-input-id="kmtsFile" />
                                                        <input type="file" name="kmtsFile" id="kmtsFile" class="form-control brochure-file-input" accept=".pdf" />
                                                        <c:if test="${not empty brochureInfo.kmtsPath}">
                                                            <c:set var="pathParts" value="${fn:split(brochureInfo.kmtsPath, '/')}" />
                                                            <c:set var="originalFileName" value="${pathParts[fn:length(pathParts) - 1]}" />
                                                            <div class="form-text text-primary mt-2">
                                                                * 현재 파일:
                                                                <a href="${brochureInfo.kmtsPath}" download="${brochureInfo.kmtsTitle}">
                                                                    ${brochureInfo.kmtsTitle}
                                                                </a>
                                                                <span class="text-muted ms-3">(원본: ${brochureInfo.kmtsOriginalName})</span>
                                                                <input type="hidden" name="kmtsFile_existing" value="${brochureInfo.kmtsPath}" />
                                                                <input type="hidden" name="kmtsOriginalName_existing" value="${brochureInfo.kmtsOriginalName}" />
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                <button type="reset" class="btn btn-light btn-active-light-primary me-2">초기화</button>
                                                <button type="submit" id="brochure_submit_btn" class="btn btn-primary" data-kt-indicator="off">
                                                    <span class="indicator-label">
                                                        저장
                                                    </span>
                                                    <span class="indicator-progress">
                                                        처리 중...
                                                        <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                                                    </span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
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

    <!--begin::Custom Javascript-->

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
    <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/custom/brochure.js?ver=<%=System.currentTimeMillis()%>"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>