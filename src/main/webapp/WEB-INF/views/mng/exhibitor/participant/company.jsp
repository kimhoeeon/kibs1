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
                                        전시업체 목록</h1>
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
                                    </ul>
                                    <!--end::Breadcrumb-->
                                </div>
                                <!--end::Page title-->
                                <!--begin::Actions-->
                                <div class="d-flex align-items-center gap-2 gap-lg-3">

                                    <!--begin::Export dropdown-->
                                    <button type="button" onclick="f_directory_excel_export()" class="btn btn-info btn-active-light-info" data-kt-menu-placement="bottom-end">
                                        <i class="ki-duotone ki-exit-down fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>디렉토리 정보 다운로드</button>
                                    <!--end::Export dropdown-->

                                    <!--begin::Export dropdown-->
                                    <%--<button type="button" onclick="f_excel_export('kt_exhibitor_table', '전시업체')" class="btn btn-dark btn-active-light-dark" data-kt-export="excel" data-kt-menu-placement="bottom-end">
                                        <i class="ki-duotone ki-exit-down fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>검색 결과 정보 다운로드</button>--%>
                                    <!--end::Export dropdown-->

                                    <!--begin::Export dropdown-->
                                    <button type="button" onclick="f_exhibitor_excel_export()" class="btn btn-success btn-active-light-success" data-kt-menu-placement="bottom-end">
                                        <i class="ki-duotone ki-exit-down fs-2">
                                            <span class="path1"></span>
                                            <span class="path2"></span>
                                        </i>전시업체 상세 엑셀 다운로드</button>
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
                                            <div class="w-100 mw-150px">
                                                <!--begin::Select2-->
                                                <select id="search_box" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-placeholder="- 검색조건 -">
                                                    <option value="companyNameKo" selected>회사명(국문)</option>
                                                    <option value="companyNameEn">회사명(영문)</option>
                                                    <option value="boothNumber">부스번호</option>
                                                    <option value="id">아이디</option>
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
                                                <input type="text" id="search_text" name="search_text" value="" class="form-control form-control-solid w-250px ps-12" placeholder="Search"/>
                                            </div>
                                            <!--end::Search-->
                                            <!--begin:Action-->
                                            <div class="d-flex align-items-center">
                                                <button type="button" onclick="f_exhibitor_search()" class="btn btn-primary me-5">Search</button>
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
                                            <%--begin::행사구분--%>
                                            <div class="w-100 mw-250px">
                                                <!--begin::Select2-->
                                                <select id="condition_field_participatory" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 행사구분 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 행사구분 -</option>
                                                    <option value="boatShow">경기국제보트쇼 (KIBS)</option>
                                                    <option value="travelShow">해양관광전 (KMTS)</option>
                                                    <option value="surfShow">코리아서프쇼 (KISS)</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::행사구분--%>
                                            <%--begin::부스구분--%>
                                            <div class="w-100 mw-150px">
                                                <!--begin::Select2-->
                                                <select id="condition_booth_type" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 부스구분 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 부스구분 -</option>
                                                    <option value="독립부스">독립부스</option>
                                                    <option value="조립부스">조립부스</option>
                                                    <option value="온라인부스">온라인부스</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::부스구분--%>
                                            <%--begin::할인여부--%>
                                            <div class="w-100 mw-250px">
                                                <!--begin::Select2-->
                                                <select id="condition_discount_yn" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 할인여부 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 할인여부 -</option>
                                                    <option value="Y">적용</option>
                                                    <option value="N">미적용</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::할인여부--%>
                                            <%--begin::세금계산서 발행여부--%>
                                            <div class="w-100 mw-225px">
                                                <!--begin::Select2-->
                                                <select id="condition_tax_yn" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 세금계산서 발행여부 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 세금계산서 발행여부 -</option>
                                                    <option value="Y">발행</option>
                                                    <option value="N">미발행</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::세금계산서 발행여부--%>
                                            <%--begin::참가비 수납여부--%>
                                            <div class="w-100 mw-200px">
                                                <!--begin::Select2-->
                                                <select id="condition_prc_yn" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 참가비 수납여부 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 참가비 수납여부 -</option>
                                                    <option value="0">미납</option>
                                                    <option value="1">참가비 납부</option>
                                                    <option value="2">50% 납부</option>
                                                    <option value="3">전액 납부</option>
                                                    <option value="4">완납(부대시설비)</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::참가비 수납여부--%>

                                        </div>
                                        <!--end::Card toolbar-->
                                        <!--begin::Card toolbar-->
                                        <div class="card-toolbar flex-row-fluid gap-5">
                                            <%--begin::참가신청 언어--%>
                                            <div class="w-100 mw-200px">
                                                <!--begin::Select2-->
                                                <select id="condition_lang" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 참가신청 언어 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 참가신청 언어 -</option>
                                                    <option value="KO">KOR</option>
                                                    <option value="EN">ENG</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::참가신청 언어--%>
                                            <%--begin::신청서완료여부--%>
                                            <div class="w-100 mw-200px">
                                                <!--begin::Select2-->
                                                <select id="condition_apply_complt" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 신청서완료여부 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 신청서완료여부 -</option>
                                                    <option value="Y">작성완료</option>
                                                    <option value="N">작성중</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::신청서완료여부--%>
                                            <%--begin::승인여부--%>
                                            <div class="w-100 mw-200px">
                                                <!--begin::Select2-->
                                                <select id="condition_approval_status" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 참가상태 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 참가상태 -</option>
                                                    <option value="작성중">작성중</option>
                                                    <option value="승인요청">승인요청</option>
                                                    <option value="참가승인">참가승인</option>
                                                    <option value="참가취소(참가의사철회)">참가취소(참가의사철회)</option>
                                                    <option value="참가취소(참가비미납)">참가취소(참가비미납)</option>
                                                    <option value="참가취소(기타)">참가취소(기타)</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::승인여부--%>
                                            <%--begin::참가취소포함여부--%>
                                            <div class="w-100 mw-200px">
                                                <!--begin::Select2-->
                                                <select id="condition_cancel_yn" class="form-select form-select-solid" data-control="select2"
                                                        data-hide-search="true" data-allow-clear="true"
                                                        data-placeholder="- 참가취소포함여부 -" onchange="f_search_condition_box_change()">
                                                    <option></option>
                                                    <option value="" disabled>- 참가취소포함여부 -</option>
                                                    <option value="Y">참가취소포함</option>
                                                    <option value="N">참가취소만보기</option>
                                                </select>
                                                <!--end::Select2-->
                                            </div>
                                            <%--end::참가취소포함여부--%>
                                            <%--begin::환불내역존재--%>
                                            <div class="w-100 mw-150px">
                                                <div class="form-check">
                                                    <label class="form-check-label" for="condition_refund_yn">
                                                        환불내역존재
                                                    </label>
                                                    <input class="form-check-input" type="checkbox" id="condition_refund_yn" onchange="f_search_condition_box_change()"/>
                                                </div>
                                            </div>
                                            <%--end::환불내역존재--%>

                                            <div class="ms-auto d-flex align-items-center gap-2 gap-lg-3">
                                                <!--begin::입금 상태 변경 버튼-->
                                                <button type="button" id="prc_yn_btn" class="btn btn-danger" data-bs-target="#kt_modal_prc_yn_status">
                                                    <i class="ki-duotone ki-arrows-circle fs-3">
                                                        <i class="path1"></i>
                                                        <i class="path2"></i>
                                                    </i> 입금 상태 변경</button>
                                                <!--end::입금 상태 변경 버튼-->

                                                <!--begin::참가 상태 변경 버튼-->
                                                <button type="button" id="approval_status_btn" class="btn btn-danger" data-bs-target="#kt_modal_approval_status">
                                                    <i class="ki-duotone ki-arrows-circle fs-3">
                                                        <i class="path1"></i>
                                                        <i class="path2"></i>
                                                    </i> 참가 상태 변경</button>
                                                <!--end::참가 상태 변경 버튼-->
                                            </div>
                                        </div>
                                        <!--end::Card toolbar-->
                                    </div>
                                    <!--end::Card header-->
                                    <!--begin::Card body-->
                                    <div class="card-body pt-0">
                                        <div class="fw-bold"><span class="mr10">검색결과</span><span id="search_cnt" style="color: #009ef7;">0</span> 개</div>
                                        <!--begin::Table-->
                                        <table class="table align-middle table-row-dashed fs-6 gy-5" id="kt_exhibitor_table">
                                            <thead>
                                                <tr class="text-start text-gray-400 fw-bold fs-7 text-uppercase gs-0">
                                                    <th class="w-10px pe-2">
                                                        <div class="form-check form-check-sm form-check-custom form-check-solid me-3">
                                                            <input class="form-check-input" type="checkbox" data-kt-check="true"
                                                                   data-kt-check-target="#kt_exhibitor_table .form-check-input" value="1"/>
                                                        </div>
                                                    </th>
                                                    <th class="text-center min-w-30px">#</th>
                                                    <th>ID</th>
                                                    <th class="text-center min-w-85px">참가상태</th>
                                                    <th class="text-center min-w-100px">부스</th>
                                                    <th>회사명(국문)</th>
                                                    <th>회사명(영문)</th>
                                                    <th class="text-center min-w-200px">회사명</th>
                                                    <th class="text-center min-w-150px">참가행사</th>
                                                    <th class="text-center min-w-75px">ID(사업자등록번호)</th>
                                                    <th class="text-center min-w-100px">총액</th>
                                                    <th class="text-center min-w-100px">선금</th>
                                                    <th class="text-center min-w-100px">잔액</th>
                                                    <th class="text-center min-w-80px">입금여부</th>
                                                    <th class="text-center min-w-100px">수정일</th>
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
    <div class="modal fade" id="kt_modal_approval_status" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div class="modal-dialog modal-dialog-centered mw-1000px">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">참가 상태 변경</h2>
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
                    <form id="modal_form" method="post" onsubmit="return false;">
                        <!--begin::Input group-->
                        <div class="row mb-6">
                            <!--begin::Label-->
                            <label class="col-lg-2 col-form-label required fw-semibold fs-6">참가 상태 변경할 업체 목록</label>
                            <!--end::Label-->
                            <!--begin::Col-->
                            <div class="col-lg-8 d-flex align-items-center">
                                <span class="exhibitor_list"></span>
                            </div>
                            <!--end::Col-->
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="row mb-6">
                            <!--begin::Label-->
                            <label class="col-lg-2 col-form-label required fw-semibold fs-6">상태</label>
                            <!--end::Label-->
                            <!--begin::Col-->
                            <div class="col-lg-8 d-flex align-items-center">
                                <!--begin::Select2-->
                                <select id="md_approval_stat" class="form-select form-select-solid" data-control="select2"
                                        data-hide-search="true" data-allow-clear="true"
                                        data-placeholder="- 참가상태 -">
                                    <option></option>
                                    <option value="" disabled>- 참가상태 -</option>
                                    <option value="작성중">작성중</option>
                                    <option value="승인요청">승인요청</option>
                                    <option value="참가승인">참가승인</option>
                                    <option value="참가취소(참가의사철회)">참가취소(참가의사철회)</option>
                                    <option value="참가취소(참가비미납)">참가취소(참가비미납)</option>
                                    <option value="참가취소(기타)">참가취소(기타)</option>
                                </select>
                                <!--end::Select2-->
                            </div>
                            <!--end::Col-->
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="row mb-8 d-none">
                            <!--begin::Label-->
                            <label class="col-lg-2 col-form-label required fw-semibold fs-6">참가취소-사유</label>
                            <!--end::Label-->
                            <!--begin::Col-->
                            <div class="col-lg-8 d-flex align-items-center">
                                <input type="text" id="approval_cancel_reason" name="approval_cancel_reason" class="form-control form-control-solid-bg" placeholder="참가취소-사유"/>
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
                            <a onclick="f_approval_status_btn_yn()" class="btn btn-primary">변경하기</a>
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
    <div class="modal fade" id="kt_modal_prc_yn_status" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div class="modal-dialog modal-dialog-centered mw-1000px">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">입금 상태 변경</h2>
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
                    <form id="modal_form2" method="post" onsubmit="return false;">
                        <!--begin::Input group-->
                        <div class="row mb-6">
                            <!--begin::Label-->
                            <label class="col-lg-2 col-form-label required fw-semibold fs-6">입금 상태 변경할 업체 목록</label>
                            <!--end::Label-->
                            <!--begin::Col-->
                            <div class="col-lg-8 d-flex align-items-center">
                                <span class="exhibitor_list2"></span>
                            </div>
                            <!--end::Col-->
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="row mb-6">
                            <!--begin::Label-->
                            <label class="col-lg-2 col-form-label required fw-semibold fs-6">상태</label>
                            <!--end::Label-->
                            <!--begin::Col-->
                            <div class="col-lg-8 d-flex align-items-center">
                                <!--begin::Select2-->
                                <select id="md_prc_yn" class="form-select form-select-solid" data-control="select2"
                                        data-hide-search="true" data-allow-clear="true"
                                        data-placeholder="- 참가비 수납여부 -">
                                    <option></option>
                                    <option value="" disabled>- 참가비 수납여부 -</option>
                                    <option value="0">미납</option>
                                    <option value="1">참가비 납부</option>
                                    <option value="2">50% 납부</option>
                                    <option value="3">전액 납부</option>
                                    <option value="4">완납(부대시설비)</option>
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
                            <a onclick="f_prc_yn_btn()" class="btn btn-primary">변경하기</a>
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
    <div class="modal fade" id="modal_exhibitor_detail_info" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div id="ifDiv" class="modal-dialog modal-dialog-centered <%--mw-1000px--%>" style="max-width: 1280px;">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">참가업체 상세보기</h2>
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
                <div class="modal-body py-lg-5 px-lg-5">
                    <div class="card card-flush">

                        <iframe id="applyDetailForm" name="applyDetailForm" width="100%" height="700" allowtransparency="true"></iframe>

                    </div>
                </div>
                <!--end::Modal body-->
            </div>
            <!--end::Modal content-->
        </div>
        <!--end::Modal dialog-->
    </div>
    <!--end::Modal - 수정이력-->

    <!--begin::Modal - 수정이력-->
    <div class="modal fade" id="modal_exhibitor_mypage_info" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div class="modal-dialog modal-dialog-centered" style="max-width: 95%;">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">이 업체로 로그인</h2>
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
                <div class="modal-body py-lg-5 px-lg-5">
                    <div class="card card-flush">

                        <iframe id="exhibitorDetailForm" name="exhibitorDetailForm" width="100%" height="800" allowtransparency="true"></iframe>

                    </div>
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
    <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/custom/exhibitor.js?ver=<%=System.currentTimeMillis()%>"></script>

    <script>
        $(function(){
            let nameKo = '${nameKo}';
            if(nvl(nameKo,'') !== ''){
                $('#search_text').val(nameKo);
                f_exhibitor_search();
            }
        })
        document.addEventListener("keyup", function(event) {
            if (event.key === 'Enter') {
                f_exhibitor_search();
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