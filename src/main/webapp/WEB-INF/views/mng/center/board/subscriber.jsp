<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
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

    <div class="d-flex flex-column flex-root app-root" id="kt_app_root">
        <div class="app-page flex-column flex-column-fluid" id="kt_app_page">
            <jsp:include page="/WEB-INF/views/mng/layout/header.jsp" flush="true"/>
            <div class="app-wrapper flex-column flex-row-fluid" id="kt_app_wrapper">
                <jsp:include page="/WEB-INF/views/mng/layout/sidebar.jsp" flush="true"/>
                <div class="app-main flex-column flex-row-fluid" id="kt_app_main">
                    <div class="d-flex flex-column flex-column-fluid">
                        <!-- Toolbar -->
                        <div id="kt_app_toolbar" class="app-toolbar py-3 py-lg-6">
                            <div id="kt_app_toolbar_container" class="app-container container-fluid d-flex flex-stack">
                                <div class="page-title d-flex flex-column justify-content-center flex-wrap me-3">
                                    <h1 class="page-heading d-flex text-dark fw-bold fs-3 flex-column justify-content-center my-0">
                                        뉴스레터 구독자 관리</h1>
                                    <span class="text-muted mt-1 fw-semibold fs-7">AI 클리핑 게시판 하단 신청 폼을 통해 수집된 이름/이메일 정보를 관리합니다.</span>
                                </div>
                                <div class="d-flex align-items-center gap-2 gap-lg-3">
                                    <button type="button" id="btnExcelDownload" class="btn btn-sm btn-light-success"><i
                                            class="ki-duotone ki-file-down fs-2"></i> 엑셀 다운로드
                                    </button>
                                </div>
                            </div>
                        </div>

                        <!-- Content -->
                        <div id="kt_app_content" class="app-content flex-column-fluid">
                            <div id="kt_app_content_container" class="app-container container-fluid">

                                <!-- Dashboard Cards -->
                                <div class="row g-5 g-xl-8 mb-8">
                                    <div class="col-xl-3">
                                        <div class="card bg-light-primary border border-primary border-2">
                                            <div class="card-body">
                                                <div class="text-primary fw-bold fs-2 mb-2 mt-5">총 구독자</div>
                                                <div class="fw-bolder text-dark fs-1">
                                                    <fmt:formatNumber value="${stats.totalCount}" pattern="#,###"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3">
                                        <div class="card bg-light-success border border-success border-2">
                                            <div class="card-body">
                                                <div class="text-success fw-bold fs-2 mb-2 mt-5">수신 가능</div>
                                                <div class="fw-bolder text-dark fs-1">
                                                    <fmt:formatNumber value="${stats.activeCount}" pattern="#,###"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3">
                                        <div class="card bg-light-danger border border-danger border-2">
                                            <div class="card-body">
                                                <div class="text-danger fw-bold fs-2 mb-2 mt-5">수신 거부</div>
                                                <div class="fw-bolder text-dark fs-1">
                                                    <fmt:formatNumber value="${stats.rejectCount}" pattern="#,###"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-3">
                                        <div class="card bg-light-info border border-info border-2">
                                            <div class="card-body">
                                                <div class="text-info fw-bold fs-2 mb-2 mt-5">오늘 신규</div>
                                                <div class="fw-bolder text-dark fs-1">
                                                    <fmt:formatNumber value="${stats.todayNewCount}" pattern="#,###"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Search Card -->
                                <div class="card mb-8">
                                    <div class="card-body">
                                        <form id="searchForm" class="form">
                                            <div class="row g-8 mb-0">
                                                <div class="col-lg-4">
                                                    <label class="fs-6 form-label fw-bold text-dark">검색어</label>
                                                    <input type="text" id="searchText"
                                                           class="form-control form-control-solid"
                                                           placeholder="이름 또는 이메일 검색">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label class="fs-6 form-label fw-bold text-dark">수신상태</label>
                                                    <select id="receiveStatus" class="form-select form-select-solid">
                                                        <option value="전체">전체</option>
                                                        <option value="수신중">수신중</option>
                                                        <option value="수신거부">수신거부</option>
                                                    </select>
                                                </div>
                                                <div class="col-lg-5">
                                                    <label class="fs-6 form-label fw-bold text-dark">등록일</label>
                                                    <div class="d-flex align-items-center">
                                                        <input type="date" id="startDate"
                                                               class="form-control form-control-solid">
                                                        <span class="mx-4 text-gray-500">~</span>
                                                        <input type="date" id="endDate"
                                                               class="form-control form-control-solid">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="d-flex justify-content-end mt-5">
                                                <button type="button" id="btnReset" class="btn btn-light me-3">초기화</button>
                                                <button type="button" id="btnSearch" class="btn btn-primary">조회</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <!-- List Card -->
                                <div class="card card-flush">
                                    <div class="card-header align-items-center py-5 gap-2 gap-md-5">
                                        <div class="card-title">
                                            <h3 class="fw-bold m-0">구독자 목록</h3>
                                        </div>
                                        <div class="card-toolbar flex-row-fluid justify-content-end gap-5">
                                            <button type="button" id="btnDeleteSelected"
                                                    class="btn btn-sm btn-light-danger">선택 삭제
                                            </button>
                                            <button type="button" id="btnRejectSelected"
                                                    class="btn btn-sm btn-light-warning">수신거부 처리
                                            </button>
                                        </div>
                                    </div>
                                    <div class="card-body pt-0">
                                        <div class="table-responsive">
                                            <table class="table align-middle table-row-dashed fs-6 gy-5"
                                                   id="subscriberTable">
                                                <thead>
                                                <tr class="text-start text-gray-400 fw-bold fs-7 text-uppercase gs-0">
                                                    <th class="w-10px pe-2">
                                                        <div class="form-check form-check-sm form-check-custom form-check-solid me-3">
                                                            <input class="form-check-input" type="checkbox" id="checkAll"/>
                                                        </div>
                                                    </th>
                                                    <th class="min-w-100px text-center">이름</th>
                                                    <th class="min-w-200px text-center">이메일</th>
                                                    <th class="min-w-100px text-center">개인정보 수집 동의</th>
                                                    <th class="min-w-100px text-center">광고성 정보 동의</th>
                                                    <th class="min-w-125px text-center">등록일시</th>
                                                    <th class="min-w-125px text-center">최종 발송일</th>
                                                    <th class="min-w-100px text-center">수신상태</th>
                                                </tr>
                                                </thead>
                                                <tbody class="fw-semibold text-gray-600 text-center" id="listBody">
                                                <!-- JS AJAX Render -->
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- Pagination Area -->
                                        <div class="d-flex justify-content-center mt-5" id="paginationArea"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--begin::Scrolltop-->
    <div id="kt_scrolltop" class="scrolltop" data-kt-scrolltop="true">
        <i class="ki-duotone ki-arrow-up">
            <span class="path1"></span>
            <span class="path2"></span>
        </i>
    </div>
    <!--end::Scrolltop-->

    <script>var hostUrl = "/assets/";</script>
    <!--begin::Global Javascript Bundle(mandatory for all pages)-->
    <script src="/assets/plugins/global/plugins.bundle.js"></script>
    <script src="/assets/js/scripts.bundle.js"></script>
    <!--end::Global Javascript Bundle-->
    <!--begin::Vendors Javascript(used for this page only)-->
    <script src="/assets/plugins/custom/datatables/datatables.bundle.js"></script>
    <!--end::Vendors Javascript-->
    <!--begin::Custom Javascript(used for this page only)-->
    <script src="/assets/js/custom/apps/ecommerce/catalog/tables.js?ver=20260630"></script>
    <script src="/assets/js/widgets.bundle.js"></script>
    <script src="/assets/js/custom/widgets.js"></script>
    <script src="/assets/js/custom/apps/chat/chat.js"></script>
    <script src="/assets/js/custom/utilities/modals/upgrade-plan.js"></script>
    <script src="/assets/js/custom/utilities/modals/create-app.js"></script>
    <script src="/assets/js/custom/utilities/modals/users-search.js"></script>
    <!--end::Custom Javascript-->

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=20260630"></script>
    <script src="/js/custom/subscriber.js?ver=20260805"></script>

</c:if>
</body>
</html>