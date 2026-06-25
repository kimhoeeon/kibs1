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
                                        참가자 목록</h1>
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
                                        <li class="breadcrumb-item text-muted">부대행사</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">올해의 제품</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">참가자 목록</li>
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
                                <form id="productForm" name="productForm" method="post" onsubmit="return false;">
                                    <input type="hidden" name="seq" value="${info.seq}">
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">참가자 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_visitor_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">수상연도</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Select2-->
                                                        <select id="awardYear" name="awardYear" class="form-select form-select-solid" data-control="select2"
                                                                data-hide-search="true" data-placeholder="- 수상연도 -">
                                                            <option></option>
                                                            <option value="" disabled>- 수상연도 -</option>
                                                            <option value="2023" <c:if test="${info.awardYear eq '2023'}"> selected </c:if> >2023</option>
                                                            <option value="2022" <c:if test="${info.awardYear eq '2022'}"> selected </c:if> >2022</option>
                                                            <option value="2021" <c:if test="${info.awardYear eq '2021'}"> selected </c:if> >2021</option>
                                                            <option value="2020" <c:if test="${info.awardYear eq '2020'}"> selected </c:if> >2020</option>
                                                            <option value="2019" <c:if test="${info.awardYear eq '2019'}"> selected </c:if> >2019</option>
                                                            <option value="2018" <c:if test="${info.awardYear eq '2018'}"> selected </c:if> >2018</option>
                                                            <option value="2017" <c:if test="${info.awardYear eq '2017'}"> selected </c:if> >2017</option>
                                                        </select>
                                                        <!--end::Select2-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">분류</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn1" name="gbn" value="END_PRD"
                                                                   <c:if test="${fn:contains(info.gbn, 'END_PRD')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn1">완성품</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn2" name="gbn" value="PART_PRD"
                                                                   <c:if test="${fn:contains(info.gbn, 'PART_PRD')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn2">부품</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn3" name="gbn" value="ABR_BOAT"
                                                                   <c:if test="${fn:contains(info.gbn, 'ABR_BOAT')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn3">해외 요트/보트</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn4" name="gbn" value="SKILL_BU"
                                                                   <c:if test="${fn:contains(info.gbn, 'SKILL_BU')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn4">기술 [부품]</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn5" name="gbn" value="IMPRV_BU"
                                                                   <c:if test="${fn:contains(info.gbn, 'IMPRV_BU')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn5">개선도 [부품]</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn6" name="gbn" value="IMPRV_END"
                                                                   <c:if test="${fn:contains(info.gbn, 'IMPRV_END')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn6">개선도 [완제품]</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn7" name="gbn" value="MARK_END"
                                                                   <c:if test="${fn:contains(info.gbn, 'MARK_END')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn7">시장성 [완제품]</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn8" name="gbn" value="IDEA_END"
                                                                   <c:if test="${fn:contains(info.gbn, 'IDEA_END')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn8">아이디어 [완제품]</label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="gbn9" name="gbn" value="INVATE"
                                                                   <c:if test="${fn:contains(info.gbn, 'INVATE')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="gbn9">인비테이션어워드</label>
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
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">업체명(국문)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyNameKo" name="companyNameKo" class="form-control form-control-lg form-control-solid-bg"
                                                               value="${info.companyNameKo}" placeholder="업체명(국문)" maxlength="30"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">업체명(영문)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyNameEn" name="companyNameEn" class="form-control form-control-lg form-control-solid-bg"
                                                               value="${info.companyNameEn}" placeholder="업체명(영문)" maxlength="30"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품명(국문)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="productNameKo" name="productNameKo" class="form-control form-control-lg form-control-solid-bg"
                                                               value="${info.productNameKo}" placeholder="제품명(국문)" maxlength="50"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품명(영문)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="productNameEn" name="productNameEn" class="form-control form-control-lg form-control-solid-bg"
                                                               value="${info.productNameEn}" placeholder="제품명(영문)" maxlength="50"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품사진</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="productPhoto" name="productPhoto" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="제품사진" disabled/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="file" id="productPhotoFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                <label class="btn btn-primary" for="productPhotoFile">파일선택</label>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->

                                                <c:if test="${productPhotoFile ne null and not empty productPhotoFile}">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">제품사진 파일목록</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <ul>
                                                                <li class="productPhotoFile_li">
                                                                    <c:set var="productPhotoFileSrc" value="${fn:replace(productPhotoFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                    <c:if test="${not fn:contains(productPhotoFileSrc, '.ai') and not fn:contains(productPhotoFileSrc, '.pdf')}">
                                                                        <img src="${productPhotoFileSrc}" class="w-100px mr10" style="border: 1px solid #009ef7"/>
                                                                    </c:if>
                                                                    <%--<a href="/file/download.do?path=event/product/${productPhotoFile.folderPath}&fileName=${productPhotoFile.fullFileName}">${productPhotoFile.fileName}</a>--%>
                                                                    <a href="javascript:void(0);" onclick="f_file_download('event/product/${productPhotoFile.folderPath}', '${productPhotoFile.fullFileName}')">${productPhotoFile.fileName}</a>
                                                                    <input type="hidden" name="productPhotoUploadFile" id="${productPhotoFile.id}" value="${productPhotoFile.fullFilePath}">
                                                                    <button type="button" class="ml10" onclick="f_file_remove(this,'${productPhotoFile.id}')">
                                                                        <i class="ki-duotone ki-abstract-11">
                                                                            <i class="path1"></i>
                                                                            <i class="path2"></i>
                                                                        </i>
                                                                    </button>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                </c:if>

                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">상세설명(국문)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea type="text" id="productIntroKo" name="productIntroKo" class="form-control form-control-lg form-control-solid-bg h-225px" placeholder="상세설명(국문)">${info.productIntroKo}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">상세설명(영문)</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea type="text" id="productIntroEn" name="productIntroEn" class="form-control form-control-lg form-control-solid-bg h-225px" placeholder="상세설명(영문)">${info.productIntroEn}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">홈페이지</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="homepage" name="homepage" class="form-control form-control-lg form-control-solid-bg" value="${info.homepage}" placeholder="홈페이지주소 ex) https://"/>
                                                    </div>
                                                    <!--end::Col-->
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
                                                <a href="/mng/event/product.do" class="btn btn-info btn-active-light-info">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_event_product_modify_init_set('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_event_product_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
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
    <script src="/js/custom/eventProduct.js?ver=<%=System.currentTimeMillis()%>"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>