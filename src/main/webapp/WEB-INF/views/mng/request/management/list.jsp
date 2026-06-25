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
                                            요청사항 & 문의 관리</h1>
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
                                            <li class="breadcrumb-item text-muted">개발사</li>
                                            <!--end::Item-->
                                            <!--begin::Item-->
                                            <li class="breadcrumb-item">
                                                <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                            </li>
                                            <!--end::Item-->
                                            <!--begin::Item-->
                                            <li class="breadcrumb-item text-muted">요청사항 & 문의</li>
                                            <!--end::Item-->
                                            <!--begin::Item-->
                                            <li class="breadcrumb-item">
                                                <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                            </li>
                                            <!--end::Item-->
                                            <!--begin::Item-->
                                            <li class="breadcrumb-item text-muted">요청사항 & 문의 관리</li>
                                            <!--end::Item-->
                                        </ul>
                                        <!--end::Breadcrumb-->
                                    </div>
                                    <!--end::Page title-->
                                    <!--begin::Actions-->
                                    <div class="d-flex align-items-center gap-2 gap-lg-3">
                                        <!--begin::Export dropdown-->
                                        <%--<button type="button" class="btn btn-success btn-active-light-success" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">
                                            <i class="ki-duotone ki-exit-down fs-2">
                                                <span class="path1"></span>
                                                <span class="path2"></span>
                                            </i>Export as Excel</button>--%>
                                        <!--end::Export dropdown-->
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
                                    <!--begin::Products-->
                                    <div class="card card-flush">
                                        <!--begin::Card header-->
                                        <div class="card-header align-items-center py-5 gap-2">
                                            <!--begin::Card title-->
                                            <div class="card-title w-100">
                                                    <%--begin::검색구분--%>
                                                <div class="w-100 mw-150px">
                                                    <!--begin::Select2-->
                                                    <select id="search_box" class="form-select form-select-solid" data-control="select2"
                                                            aria-label="- 검색조건 -" data-placeholder="- 검색조건 -"
                                                            data-allow-clear="true" data-hide-search="true">
                                                        <option></option>
                                                        <option value="" disabled>- 검색조건 -</option>
                                                        <option value="" selected>전체</option>
                                                        <option value="TITLE">제목</option>
                                                        <option value="CONTENT">내용</option>
                                                    </select>
                                                    <!--end::Select2-->
                                                </div>
                                                    <%--end::검색구분--%>
                                                <!--begin::Search-->
                                                <div class="d-flex align-items-center position-relative my-1 ml15 mr15">
                                                    <i class="ki-duotone ki-magnifier fs-3 position-absolute ms-4">
                                                        <span class="path1"></span>
                                                        <span class="path2"></span>
                                                    </i>
                                                    <input type="text" id="search_text" name="search_text" value="" class="form-control form-control-solid w-250px ps-12" placeholder="검색어 입력"/>
                                                </div>
                                                <!--end::Search-->
                                                <!--begin:Action-->
                                                <div class="d-flex align-items-center">
                                                    <button type="button" onclick="f_request_list_search()" class="btn btn-primary me-5">Search</button>
                                                    <button type="button" onclick="f_request_list_search_condition_init()" class="btn btn-secondary me-5">
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
                                                    <%--begin::condition--%>
                                                <div class="w-100 mw-175px">
                                                    <!--begin::Select2-->
                                                    <select id="condition_gbn" class="form-select form-select-solid" data-control="select2"
                                                            data-hide-search="true" data-allow-clear="true"
                                                            data-placeholder="- 요청구분 -" onchange="f_request_list_search()">
                                                        <option></option>
                                                        <option value="" disabled>- 요청구분 -</option>
                                                        <option value="뉴스레터">뉴스레터</option>
                                                        <option value="유지보수">유지보수</option>
                                                        <option value="기능오류">기능오류</option>
                                                    </select>
                                                    <!--end::Select2-->
                                                </div>
                                                    <%--end::condition--%>
                                                    <%--begin::condition--%>
                                                <div class="w-100 mw-150px">
                                                    <!--begin::Select2-->
                                                    <select id="condition_progress_step" class="form-select form-select-solid" data-control="select2"
                                                            data-hide-search="true" data-allow-clear="true"
                                                            data-placeholder="- 진행단계 -" onchange="f_request_list_search()">
                                                        <option></option>
                                                        <option value="" disabled>- 진행단계 -</option>
                                                        <option value="처리대기">처리대기</option>
                                                        <option value="진행중">진행중</option>
                                                        <option value="완료">완료</option>
                                                        <option value="논의필요">논의필요</option>
                                                        <option value="처리불가">처리불가</option>
                                                    </select>
                                                    <!--end::Select2-->
                                                </div>
                                                    <%--end::condition--%>
                                                    <%--begin::condition--%>
                                                <div class="w-100 mw-150px d-flex align-items-center">
                                                    <input type="checkbox" class="form-check-input me-3" id="condition_emergency_yn" onchange="f_request_list_search()"/>
                                                    <label class="form-check-label" for="condition_emergency_yn">긴급 요청만 보기</label>
                                                </div>
                                                    <%--end::condition--%>

                                                <div class="ms-auto d-flex align-items-center gap-2 gap-lg-3">

                                                    <c:if test="${sessionScope.note eq '개발사'}">
                                                        <!--begin::버튼-->
                                                        <button type="button" id="complete_expect_btn" class="btn btn-info" data-bs-target="#modal_complete_expect_status">
                                                            <i class="ki-duotone ki-calendar-tick fs-3">
                                                                <span class="path1"></span>
                                                                <span class="path2"></span>
                                                                <span class="path3"></span>
                                                                <span class="path4"></span>
                                                                <span class="path5"></span>
                                                                <span class="path6"></span>
                                                            </i> 처리 예정 일시 변경</button>
                                                        <!--end::버튼-->
                                                    </c:if>

                                                    <!--begin::버튼-->
                                                    <button type="button" id="progress_step_btn" class="btn btn-danger" data-bs-target="#modal_progress_step_status">
                                                        <i class="ki-duotone ki-arrows-loop fs-3">
                                                            <i class="path1"></i>
                                                            <i class="path2"></i>
                                                        </i> 진행 단계 변경</button>
                                                    <!--end::버튼-->

                                                    <!--begin::등록-->
                                                    <a href="javascript:void(0);" onclick="f_request_list_detail_set('');" class="btn btn-primary ms-auto">신규 등록</a>
                                                    <!--end::등록-->

                                                </div>

                                            </div>
                                            <!--end::Card toolbar-->
                                        </div>
                                        <!--end::Card header-->
                                        <!--begin::Card body-->
                                        <div class="card-body pt-0">
                                            <div class="fw-bold"><span class="mr10">검색결과</span><span id="search_cnt" style="color: #009ef7;">0</span> 개</div>
                                            <!--begin::Table-->
                                            <table class="table align-middle table-row-dashed fs-6 gy-5" id="mng_request_list_table">
                                                <thead>
                                                    <tr class="text-start text-gray-400 fw-bold fs-7 text-uppercase gs-0">
                                                        <th class="w-10px pe-2">
                                                            <div class="form-check form-check-sm form-check-custom form-check-solid me-3">
                                                                <input class="form-check-input" type="checkbox" data-kt-check="true"
                                                                       data-kt-check-target="#mng_request_list_table .form-check-input" value="1"/>
                                                            </div>
                                                        </th>
                                                        <th class="text-center w-75px">번호</th>
                                                        <th>seq</th>
                                                        <th class="text-center min-w-75px">일반/긴급</th>
                                                        <th class="text-center min-w-75px">구분</th>
                                                        <th class="text-center min-w-100px">진행단계</th>
                                                        <th class="text-center min-w-150px">글제목 (댓글)</th>
                                                        <th class="text-center min-w-150px w-175px">처리예정일시</th>
                                                        <th class="text-center min-w-150px w-175px">등록일시</th>
                                                        <th class="text-center w-125px">기능</th>
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

        <!--begin::Modal - 수정이력-->
        <div class="modal fade" id="modal_progress_step_status" tabindex="-1" aria-hidden="true">
            <!--begin::Modal dialog-->
            <div class="modal-dialog modal-dialog-centered mw-1000px">
                <!--begin::Modal content-->
                <div class="modal-content">
                    <!--begin::Modal header-->
                    <div class="modal-header" style="background-color: #1e1e2d;">
                        <!--begin::Modal title-->
                        <h2 style="color: #FFFFFF;">진행 단계 변경</h2>
                        <!--end::Modal title-->
                        <!--begin::Close-->
                        <div class="btn btn-sm btn-icon btn-active-color-primary" data-bs-dismiss="modal">
                            <i class="ki-duotone ki-cross fs-1">
                                <span class="path1"></span>
                                <span class="path2"></span>
                            </i>
                        </div>
                        <!--end::Close-->
                    </div>
                    <!--end::Modal header-->
                    <!--begin::Modal body-->
                    <div class="modal-body py-lg-10 px-lg-10">
                        <!--begin::form-->
                        <form id="progressStepModalForm" method="post" onsubmit="return false;">
                            <!--begin::Input group-->
                            <div class="row mb-6">
                                <!--begin::Label-->
                                <label class="col-lg-2 col-form-label required fw-semibold fs-6">진행 단계 변경 대상 목록</label>
                                <!--end::Label-->
                                <!--begin::Col-->
                                <div class="col-lg-8 d-flex align-items-center">
                                    <span class="target_list"></span>
                                </div>
                                <!--end::Col-->
                            </div>
                            <!--end::Input group-->
                            <!--begin::Input group-->
                            <div class="row mb-6">
                                <!--begin::Label-->
                                <label class="col-lg-2 col-form-label required fw-semibold fs-6">진행 단계</label>
                                <!--end::Label-->
                                <!--begin::Col-->
                                <div class="col-lg-8 d-flex align-items-center">
                                    <!--begin::Select2-->
                                    <select id="md_progress_step" class="form-select form-select-solid" data-control="select2"
                                            data-hide-search="true" data-allow-clear="true"
                                            data-placeholder="- 진행단계 -">
                                        <option></option>
                                        <option value="" disabled>- 진행단계 -</option>
                                        <option value="처리대기">처리대기</option>
                                        <option value="진행중">진행중</option>
                                        <option value="완료">완료</option>
                                        <option value="논의필요">논의필요</option>
                                        <option value="처리불가">처리불가</option>
                                    </select>
                                    <!--end::Select2-->
                                </div>
                                <!--end::Col-->
                            </div>
                            <!--end::Input group-->
                        </form>
                        <!--end::form-->
                        <!--begin::Menu separator-->
                        <div class="separator my-6"></div>
                        <!--end::Menu separator-->
                        <!--begin::Col-->
                        <div class="col-lg-12 d-flex justify-content-center">
                            <!--begin::Col-->
                            <div>
                                <!--begin::Cancel-->
                                <a class="btn btn-danger" data-bs-dismiss="modal">취소</a>
                                <!--end::Cancel-->
                            </div>
                            <!--end::Col-->
                            <!--begin::Col-->
                            <div class="ms-10">
                                <!--begin::File upload-->
                                <a onclick="f_progress_step_change_modal_btn()" class="btn btn-primary">변경하기</a>
                                <!--end::File upload-->
                            </div>
                            <!--end::Col-->
                        </div>
                        <!--end::Col-->
                    </div>
                    <!--end::Modal body-->
                </div>
                <!--end::Modal content-->
            </div>
            <!--end::Modal dialog-->
        </div>
        <!--end::Modal - 수정이력-->

        <!--begin::Modal - 수정이력-->
        <div class="modal fade" id="modal_complete_expect_status" tabindex="-1" aria-hidden="true">
            <!--begin::Modal dialog-->
            <div class="modal-dialog modal-dialog-centered mw-1000px">
                <!--begin::Modal content-->
                <div class="modal-content">
                    <!--begin::Modal header-->
                    <div class="modal-header" style="background-color: #1e1e2d;">
                        <!--begin::Modal title-->
                        <h2 style="color: #FFFFFF;">처리 예정 일시 변경</h2>
                        <!--end::Modal title-->
                        <!--begin::Close-->
                        <div class="btn btn-sm btn-icon btn-active-color-primary" data-bs-dismiss="modal">
                            <i class="ki-duotone ki-cross fs-1">
                                <span class="path1"></span>
                                <span class="path2"></span>
                            </i>
                        </div>
                        <!--end::Close-->
                    </div>
                    <!--end::Modal header-->
                    <!--begin::Modal body-->
                    <div class="modal-body py-lg-10 px-lg-10">
                        <!--begin::form-->
                        <form id="completeExpectModalForm" method="post" onsubmit="return false;">
                            <!--begin::Input group-->
                            <div class="row mb-6">
                                <!--begin::Label-->
                                <label class="col-lg-2 col-form-label required fw-semibold fs-6">처리 예상 일정 변경 대상 목록</label>
                                <!--end::Label-->
                                <!--begin::Col-->
                                <div class="col-lg-8 d-flex align-items-center">
                                    <span class="target_list"></span>
                                </div>
                                <!--end::Col-->
                            </div>
                            <!--end::Input group-->
                            <!--begin::Input group-->
                            <div class="row mb-6">
                                <!--begin::Label-->
                                <label class="col-lg-2 col-form-label required fw-semibold fs-6">일정</label>
                                <!--end::Label-->
                                <!--begin::Col-->
                                <div class="col-lg-8 d-flex align-items-center">
                                    <input class="form-control form-control-solid" id="md_complete_expect" name="md_complete_expect" placeholder="처리 예상 일정"/>
                                </div>
                                <!--end::Col-->
                            </div>
                            <!--end::Input group-->
                        </form>
                        <!--end::form-->
                        <!--begin::Menu separator-->
                        <div class="separator my-6"></div>
                        <!--end::Menu separator-->
                        <!--begin::Col-->
                        <div class="col-lg-12 d-flex justify-content-center">
                            <!--begin::Col-->
                            <div>
                                <!--begin::Cancel-->
                                <a class="btn btn-danger" data-bs-dismiss="modal">취소</a>
                                <!--end::Cancel-->
                            </div>
                            <!--end::Col-->
                            <!--begin::Col-->
                            <div class="ms-10">
                                <!--begin::File upload-->
                                <a onclick="f_complete_expect_change_modal_btn()" class="btn btn-primary">변경하기</a>
                                <!--end::File upload-->
                            </div>
                            <!--end::Col-->
                        </div>
                        <!--end::Col-->
                    </div>
                    <!--end::Modal body-->
                </div>
                <!--end::Modal content-->
            </div>
            <!--end::Modal dialog-->
        </div>
        <!--end::Modal - 수정이력-->

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
        <script src="https://cdn.jsdelivr.net/npm/flatpickr@latest/dist/l10n/ko.js"></script>
        <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
        <script src="/js/custom/list.js?ver=<%=System.currentTimeMillis()%>"></script>

        <script>
            /*alert('메뉴 준비중입니다.');
            window.location.href = '/mng/main.do';*/

            document.addEventListener("keyup", function(event) {
                if (event.key === 'Enter') {
                    f_request_list_search();
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