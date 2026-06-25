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
                                        지난행사 전시업체</h1>
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
                                        <li class="breadcrumb-item text-muted">지난행사 전시업체</li>
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
                                <form id="companyForm" name="companyForm" method="post" onsubmit="return false;">
                                    <c:if test="${info ne null}">
                                        <input type="hidden" name="seq" value="${info.seq}">
                                        <!--begin::Basic info-->
                                        <div class="card mb-5 mb-xl-10">
                                            <!--begin::Card header-->
                                            <div class="card-header align-items-center py-5 gap-2">
                                                <!--begin::Card title-->
                                                <div class="card-title w-100">
                                                    <!--begin:Action-->
                                                    <div class="d-flex align-items-center">
                                                        <h2 class="fw-bold">${info.companyNameKo} / ${info.companyNameEn}</h2>
                                                    </div>
                                                    <!--end:Action-->
                                                </div>
                                                <!--end::Card title-->
                                            </div>
                                            <!--end::Card header-->
                                        </div>
                                        <!--end::Basic info-->

                                        <!--begin::Basic info-->
                                        <div class="card mb-5 mb-xl-10">
                                            <!--begin::Card header-->
                                            <div class="card-header border-0">
                                                <!--begin::Card title-->
                                                <div class="card-title m-0">
                                                    <h3 class="fw-bold m-0">승인 정보</h3>
                                                </div>
                                                <!--end::Card title-->
                                            </div>
                                            <!--begin::Card header-->
                                            <!--begin::Content-->
                                            <div id="kt_approval_info">
                                                <!--begin::Card body-->
                                                <div class="card-body border-top p-9">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">부스번호</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <input type="text" id="boothNum" name="boothNum" class="form-control form-control-lg form-control-solid-bg" placeholder="부스번호" value="${info.boothNum}"/>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">아이디</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <input type="text" id="id" name="id" class="form-control form-control-lg form-control-solid-bg" placeholder="아이디" value="${info.id}" readonly/>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6 required">이관년도</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <input type="text" id="preTransferYear" class="form-control form-control-lg form-control-solid-bg" placeholder="기존 이관년도 값" value="${info.transferYear}" readonly/>
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
                                    </c:if>

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">관리 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_mng_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">BP번호</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="bpNum" name="bpNum" class="form-control form-control-lg form-control-solid-bg" value="${info.bpNum}" placeholder="BP번호"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-4">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">컨택내역</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-1 d-flex align-items-center">
                                                        작성자
                                                    </div>
                                                    <!--end::Col-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-7">
                                                        <input type="text" id="contactWriter" name="contactWriter" class="form-control form-control-lg form-control-solid-bg" value="${info.contactWriter}" placeholder="작성자"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-4">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label"></label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-1 d-flex align-items-center">
                                                        날짜
                                                    </div>
                                                    <!--end::Col-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-7">
                                                        <input id="contactDate" name="contactDate" class="form-control form-control-lg form-control-solid-bg" value="${info.contactDate}" placeholder="날짜"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label"></label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-1 d-flex align-items-center">
                                                        내용
                                                    </div>
                                                    <!--end::Col-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-7">
                                                        <textarea type="text" id="contactContent" name="contactContent" class="form-control form-control-lg form-control-solid-bg" placeholder="내용">${info.contactContent}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-4">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">참고사항</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-1 d-flex align-items-center">
                                                        작성자
                                                    </div>
                                                    <!--end::Col-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-7">
                                                        <input type="text" id="referenceWriter" name="referenceWriter" class="form-control form-control-lg form-control-solid-bg" value="${info.referenceWriter}" placeholder="작성자"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-4">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label"></label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-1 d-flex align-items-center">
                                                        날짜
                                                    </div>
                                                    <!--end::Col-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-7">
                                                        <input id="referenceDate" name="referenceDate" class="form-control form-control-lg form-control-solid-bg" value="${info.referenceDate}" placeholder="날짜"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label"></label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-1 d-flex align-items-center">
                                                        내용
                                                    </div>
                                                    <!--end::Col-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-7">
                                                        <textarea type="text" id="referenceContent" name="referenceContent" class="form-control form-control-lg form-control-solid-bg" placeholder="내용">${info.referenceContent}</textarea>
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
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">참가업체 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_company_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">회사명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="companyNameKo" name="companyNameKo" class="form-control form-control-lg form-control-solid-bg mb-3 mb-lg-0" value="${info.companyNameKo}" placeholder="국문"/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="companyNameEn" name="companyNameEn" class="form-control form-control-lg form-control-solid-bg" value="${info.companyNameEn}" placeholder="영문"/>
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">본사 주소</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="companyAddress" name="companyAddress" class="form-control form-control-lg form-control-solid-bg" placeholder="주소" value="${info.companyAddress}" readonly/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <button type="button" class="btn btn-primary" onclick="execDaumPostcode('companyAddress','companyAddressDetail')">주소 검색</button>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                        <!--begin::Row-->
                                                        <div class="row mt-3">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="companyAddressDetail" name="companyAddressDetail" class="form-control form-control-lg form-control-solid-bg" value="${info.companyAddressDetail}" placeholder="상세주소"/>
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">대표자</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyCeo" name="companyCeo" class="form-control form-control-lg form-control-solid-bg" value="${info.companyCeo}" placeholder="대표자"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전화</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyTel" name="companyTel" class="form-control form-control-lg form-control-solid-bg" maxlength="13" value="${info.companyTel}" placeholder="전화 ( - 하이픈 포함 )"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">홈페이지</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyHomepage" name="companyHomepage" class="form-control form-control-lg form-control-solid-bg" value="${info.companyHomepage}" placeholder="https://"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">Fax</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyFax" name="companyFax" class="form-control form-control-lg form-control-solid-bg" maxlength="20" value="${info.companyFax}" placeholder="Fax ( - 하이픈 포함 )"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">사업자등록증</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="companyLicense" name="companyLicense" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="사업자등록증" disabled/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="file" id="companyLicenseFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg, .pdf">
                                                                <label class="btn btn-primary" for="companyLicenseFile">파일선택</label>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->

                                                <c:if test="${companyLicenseFile ne null and not empty companyLicenseFile}">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">사업자등록증</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <ul>
                                                                <li class="companyLicenseFile_li">
                                                                    <c:set var="companyLicenseFileSrc" value="${fn:replace(companyLicenseFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                    <c:if test="${not fn:contains(companyLicenseFileSrc, '.ai') and not fn:contains(companyLicenseFileSrc, '.pdf')}">
                                                                        <img src="${companyLicenseFileSrc}" class="w-100px mr10" style="border: 1px solid #009ef7"/>
                                                                    </c:if>
                                                                    <%--<a href="/file/download.do?path=exhibitor/company/${companyLicenseFile.folderPath}&fileName=${companyLicenseFile.fullFileName}">${companyLicenseFile.fileName}</a>--%>
                                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${companyLicenseFile.folderPath}', '${companyLicenseFile.fullFileName}')">${companyLicenseFile.fileName}</a>
                                                                    <input type="hidden" name="companyLicenseUploadFile" id="${companyLicenseFile.id}" value="${companyLicenseFile.fullFilePath}">
                                                                    <button type="button" class="ml10" onclick="f_file_remove(this,'${companyLicenseFile.id}')">
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">사업자등록번호</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyLicenseNum" name="companyLicenseNum" class="form-control form-control-lg form-control-solid-bg" maxlength="30" value="${info.companyLicenseNum}" placeholder="사업자등록번호 ( - 하이픈 포함 )" readonly/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">기참가연도</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-8 d-flex justify-content-between">
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYearFirst" name="prePartYear" value="first" <c:if test="${fn:contains(info.prePartYear, 'first')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYearFirst">
                                                                첫 참가
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2024" name="prePartYear" value="2024" <c:if test="${fn:contains(info.prePartYear, '2024')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2024">
                                                                2024
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2023" name="prePartYear" value="2023" <c:if test="${fn:contains(info.prePartYear, '2023')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2023">
                                                                2023
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2022" name="prePartYear" value="2022" <c:if test="${fn:contains(info.prePartYear, '2022')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2022">
                                                                2022
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2021" name="prePartYear" value="2021" <c:if test="${fn:contains(info.prePartYear, '2021')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2021">
                                                                2021
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2020" name="prePartYear" value="2020" <c:if test="${fn:contains(info.prePartYear, '2020')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2020">
                                                                2020
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2019" name="prePartYear" value="2019" <c:if test="${fn:contains(info.prePartYear, '2019')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2019">
                                                                2019
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2018" name="prePartYear" value="2018" <c:if test="${fn:contains(info.prePartYear, '2018')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2018">
                                                                2018
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2017" name="prePartYear" value="2017" <c:if test="${fn:contains(info.prePartYear, '2017')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2017">
                                                                2017
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2016" name="prePartYear" value="2016" <c:if test="${fn:contains(info.prePartYear, '2016')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2016">
                                                                2016
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2015" name="prePartYear" value="2015" <c:if test="${fn:contains(info.prePartYear, '2015')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2015">
                                                                2015
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="prePartYear2014" name="prePartYear" value="2014" <c:if test="${fn:contains(info.prePartYear, '2014')}">checked</c:if>/>
                                                            <label class="form-check-label" for="prePartYear2014">
                                                                2014
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <%--<div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">참가의향</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-8 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" id="partWantY" name="partWantYn" value="Y" <c:if test="${info.partWantYn eq 'Y'}">checked</c:if> disabled/>
                                                            <label class="form-check-label" for="partWantY">
                                                                지속적으로 보트쇼에 참가할 의향 있음
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" id="partWantN" name="partWantYn" value="N" <c:if test="${info.partWantYn eq 'N'}">checked</c:if> disabled/>
                                                            <label class="form-check-label" for="partWantN">
                                                                없음
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>--%>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">회원사 여부</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-8 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" id="memberCompanyY" name="memberCompanyYn" value="Y" <c:if test="${info.memberCompanyYn eq 'Y'}">checked</c:if> />
                                                            <label class="form-check-label" for="memberCompanyY">
                                                                한국해양레저산업협회 회원사
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" id="memberCompanyN" name="memberCompanyYn" value="N" <c:if test="${info.memberCompanyYn eq 'N'}">checked</c:if> />
                                                            <label class="form-check-label" for="memberCompanyN">
                                                                해당없음
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">SNS (주소기입)</h4>
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">블로그</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="snsBlog" name="snsBlog" class="form-control form-control-lg form-control-solid-bg" value="${info.snsBlog}" placeholder="블로그" />
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">페이스북</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="snsFacebook" name="snsFacebook" class="form-control form-control-lg form-control-solid-bg" value="${info.snsFacebook}" placeholder="페이스북" />
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">인스타그램</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="snsInstagram" name="snsInstagram" class="form-control form-control-lg form-control-solid-bg" value="${info.snsInstagram}" placeholder="인스타그램" />
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">기타</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="snsEtc" name="snsEtc" class="form-control form-control-lg form-control-solid-bg" value="${info.snsEtc}" placeholder="기타" />
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
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">담당자 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_charge_person_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">

                                                <c:if test="${empty chargeList}">
                                                    <div class="charge_info_box">
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                담당자 정보 #<span class="chargeInfoNum">1</span>
                                                            </h4>
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">성명</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" name="chargePersonName" class="form-control form-control-lg form-control-solid-bg" maxlength="50" placeholder="성명" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">직위</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" name="chargePersonPosition" class="form-control form-control-lg form-control-solid-bg" placeholder="직위" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6">부서</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" name="chargePersonDepart" class="form-control form-control-lg form-control-solid-bg" placeholder="부서" />
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
                                                                <input type="tel" name="chargePersonTel" class="form-control form-control-lg form-control-solid-bg onlyGeneralTel" maxlength="13" placeholder="전화번호 ( - 하이픈 포함 )" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">휴대전화</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="tel" name="chargePersonPhone" class="form-control form-control-lg form-control-solid-bg onlyTel" maxlength="13" placeholder="휴대전화 ( - 하이픈 포함 )" />
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
                                                                    <input type="text" name="chargePersonEmail" class="form-control form-control-solid-bg" placeholder="이메일" />
                                                                    <span class="input-group-text">@</span>
                                                                    <input type="text" name="chargePersonDomain" class="form-control form-control-solid-bg" placeholder="도메인" />
                                                                    <select name="chargePersonEmail_sel" class="form-select form-control-solid-bg ml10">
                                                                        <option selected>직접입력</option>
                                                                        <option value="daum.net">daum.net</option>
                                                                        <option value="nate.com">nate.com</option>
                                                                        <option value="hanmail.net">hanmail.net</option>
                                                                        <option value="naver.com">naver.com</option>
                                                                        <option value="hotmail.com">hotmail.com</option>
                                                                        <option value="yahoo.co.kr">yahoo.co.kr</option>
                                                                        <option value="empal.com">empal.com</option>
                                                                        <option value="korea.com">korea.com</option>
                                                                        <option value="hanmir.com">hanmir.com</option>
                                                                        <option value="dreamwiz.com">dreamwiz.com</option>
                                                                        <option value="orgio.net">orgio.net</option>
                                                                        <option value="korea.com">korea.com</option>
                                                                        <option value="hitel.net">hitel.net</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                    </div>
                                                </c:if>

                                                <c:if test="${not empty chargeList}">
                                                <c:forEach var="charge" items="${chargeList}" begin="0" end="${chargeList.size()}" step="1" varStatus="status">
                                                    <div class="charge_info_box">
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                <input type="hidden" id="chargeSeq" name="chargeSeq" value="${charge.seq}">
                                                                담당자 정보 #<span class="chargeInfoNum">${status.index + 1}</span>
                                                                <span class="chargeInfoDel float-end cursor-pointer">삭제</span>
                                                            </h4>
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">성명</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="chargePersonName" name="chargePersonName" class="form-control form-control-lg form-control-solid-bg" maxlength="50" value="${charge.chargePersonName}" placeholder="성명" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">직위</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="chargePersonPosition" name="chargePersonPosition" class="form-control form-control-lg form-control-solid-bg" value="${charge.chargePersonPosition}" placeholder="직위" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6">부서</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="chargePersonDepart" name="chargePersonDepart" class="form-control form-control-lg form-control-solid-bg" value="${charge.chargePersonDepart}" placeholder="부서" />
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
                                                                <input type="tel" id="chargePersonTel" name="chargePersonTel" class="form-control form-control-lg form-control-solid-bg onlyGeneralTel" maxlength="13" value="${charge.chargePersonTel}" placeholder="전화번호 ( - 하이픈 포함 )" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">휴대전화</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="tel" id="chargePersonPhone" name="chargePersonPhone" class="form-control form-control-lg form-control-solid-bg onlyTel" maxlength="13" value="${charge.chargePersonPhone}" placeholder="휴대전화 ( - 하이픈 포함 )" />
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
                                                                    <input type="text" id="charge_person_email_input_1" name="chargePersonEmail" class="form-control form-control-solid-bg" placeholder="이메일" value="${fn:split(charge.chargePersonEmail,'@')[0]}" />
                                                                    <span class="input-group-text">@</span>
                                                                    <input type="text" id="charge_person_email_input_2" name="chargePersonDomain" class="form-control form-control-solid-bg" placeholder="도메인" value="${fn:split(charge.chargePersonEmail,'@')[1]}" />
                                                                    <select name="chargePersonEmail_sel" class="form-select form-control-solid-bg ml10">
                                                                        <c:set var="chargePersonDomain" value="${fn:split(charge.chargePersonEmail,'@')[1]}"/>
                                                                        <option selected>직접입력</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'hotmail.com'}">selected</c:if> >hotmail.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'yahoo.co.kr'}">selected</c:if> >yahoo.co.kr</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'empal.com'}">selected</c:if> >empal.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'hanmir.com'}">selected</c:if> >hanmir.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'dreamwiz.com'}">selected</c:if> >dreamwiz.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'orgio.net'}">selected</c:if> >orgio.net</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                                        <option <c:if test="${chargePersonDomain eq 'hitel.net'}">selected</c:if> >hitel.net</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                    </div>
                                                </c:forEach>
                                                </c:if>

                                            </div>
                                            <!--end::Card body-->

                                            <!--begin::Actions-->
                                            <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                <span class="btn btn-primary" id="kt_charge_info_add">추가</span>
                                            </div>
                                            <!--end::Actions-->
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
                                                <h3 class="fw-bold m-0">참가분야</h3>
                                                <h4 class="ml15 text-gray-500">최대 3개 선택 가능합니다.</h4>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_field_part_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">참가분야</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart1" name="fieldPart" value="요트·보트전 (Yacht & Boat World)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '요트·보트전')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart1">
                                                                요트·보트전 (Yacht & Boat World)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart2" name="fieldPart" value="무동력보트전 (Paddler’s World)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '무동력보트전')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart2">
                                                                무동력보트전 (Paddler’s World)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart3" name="fieldPart" value="워크보트전 (Workboat World)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '워크보트전')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart3">
                                                                워크보트전 (Workboat World)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart4" name="fieldPart" value="해양레저관 (Marine Leisure)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '해양레저관')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart4">
                                                                해양레저관 (Marine Leisure)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart5" name="fieldPart" value="카라반쇼 (Caravan Show)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '카라반쇼')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart5">
                                                                카라반쇼 (Caravan Show)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart6" name="fieldPart" value="아라마리나 교육 및 체험 프로그램 (Aramarina Education and Experience Program)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '아라마리나 교육 및 체험 프로그램')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart6">
                                                                아라마리나 교육 및 체험 프로그램 (Aramarina Education and Experience Program)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart7" name="fieldPart" value="해양부품·안전·마리나산업전 (Marine Equipment, Safety & Marina Industry Show)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '해양부품·안전·마리나산업전')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart7">
                                                                해양부품·안전·마리나산업전 (Marine Equipment, Safety & Marina Industry Show)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart8" name="fieldPart" value="친환경 특별전 (Eco Friendly Marine)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '친환경 특별전')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart8">
                                                                친환경 특별전 (Eco Friendly Marine)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-2">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart9" name="fieldPart" value="한국해양관광전 (Marine Tourism)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '한국해양관광전')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart9">
                                                                한국해양관광전 (Marine Tourism)
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="fieldPart10" name="fieldPart" value="보트정비관 (Boat Maintenance)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '보트정비관')}">checked</c:if> />
                                                            <label class="form-check-label" for="fieldPart10">
                                                                보트정비관 (Boat Maintenance)
                                                            </label>
                                                        </div>
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
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">전시 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_display_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">

                                                <c:if test="${empty displayList}">
                                                    <div class="display_info_box">
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                전시정보 #<span class="exhiInfoNum">1</span>
                                                            </h4>
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전시품목</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="displayItem" name="displayItem" class="form-control form-control-lg form-control-solid-bg" placeholder="전시품목" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전시품목 브랜드명</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="displayBrand" name="displayBrand" class="form-control form-control-lg form-control-solid-bg" placeholder="전시품목 브랜드명" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">실물 보트수 (단위:척)</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" id="displayBoatCnt" name="displayBoatCnt" class="form-control form-control-lg form-control-solid-bg onlyNum" placeholder="실물 보트수" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                    </div>
                                                </c:if>

                                                <c:if test="${not empty displayList}">
                                                    <c:forEach var="display" items="${displayList}" begin="0" end="${displayList.size()}" step="1" varStatus="status">
                                                        <div class="display_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    <input type="hidden" id="displayItemSeq" name="displayItemSeq" value="${display.seq}">
                                                                    전시정보 #<span class="exhiInfoNum">${status.index + 1}</span>
                                                                    <span class="exhiInfoDel float-end cursor-pointer">삭제</span>
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전시품목</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="displayItem" class="form-control form-control-lg form-control-solid-bg" placeholder="전시품목" value="${display.displayItem}" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전시품목 브랜드명</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="displayBrand" class="form-control form-control-lg form-control-solid-bg" placeholder="전시품목 브랜드명" value="${display.displayBrand}" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">실물 보트수 (단위:척)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="displayBoatCnt" class="form-control form-control-lg form-control-solid-bg onlyNum" placeholder="실물 보트수" value="${display.displayBoatCnt}" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                        </div>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                            <!--end::Card body-->
                                            <!--begin::Actions-->
                                            <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                <span class="btn btn-primary" id="kt_display_info_add">추가</span>
                                            </div>
                                            <!--end::Actions-->
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
                                                <h3 class="fw-bold m-0">상세 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_detail_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">회사소개</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="companyIntroKo" name="companyIntroKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" >${info.companyIntroKo}</textarea>
                                                        <textarea id="companyIntroEn" name="companyIntroEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" >${info.companyIntroEn}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">KIBS 참가목적</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="companyPurposeKo" name="companyPurposeKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" >${info.companyPurposeKo}</textarea>
                                                        <textarea id="companyPurposeEn" name="companyPurposeEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" >${info.companyPurposeEn}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전시품 소개</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="displayItemIntroKo" name="displayItemIntroKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" >${info.preDisplayItemIntroKo}</textarea>
                                                        <textarea id="displayItemIntroEn" name="displayItemIntroEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" >${info.preDisplayItemIntroKo}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">신제품출품 사항 소개</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="newItemIntroKo" name="newItemIntroKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" >${info.newItemIntroKo}</textarea>
                                                        <textarea id="newItemIntroEn" name="newItemIntroEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" >${info.newItemIntroEn}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">우리 기업 부스는 꼭 들려야 될 이유가 있다면?</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="boothVisitReason" name="boothVisitReason" class="form-control form-control-solid-bg resize-none h-100px" placeholder="내용 작성" >${info.boothVisitReason}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">행사/이벤트 진행계획</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="eventPlan" name="eventPlan" class="form-control form-control-solid-bg resize-none h-100px" placeholder="내용 작성" >${info.eventPlan}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">사무국 요청사항</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea id="officeRequest" name="officeRequest" class="form-control form-control-solid-bg resize-none h-100px" placeholder="내용 작성" >${info.officeRequest}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label required fw-semibold fs-6">로고</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="logo" name="logo" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="로고" disabled/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="file" id="logoFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg, .png, .ai">
                                                                <label class="btn btn-primary" for="logoFile">파일선택</label>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->

                                                <c:if test="${logoFile ne null and not empty logoFile}">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">로고</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <ul>
                                                                <li class="logoFile_li">
                                                                    <c:set var="logoFileSrc" value="${fn:replace(logoFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                    <c:if test="${not fn:contains(logoFileSrc, '.ai') and not fn:contains(logoFileSrc, '.pdf')}">
                                                                        <img src="${logoFileSrc}" class="w-100px mr10" style="border: 1px solid #009ef7"/>
                                                                    </c:if>
                                                                    <%--<a href="/file/download.do?path=exhibitor/company/${logoFile.folderPath}&fileName=${logoFile.fullFileName}">${logoFile.fileName}</a>--%>
                                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${logoFile.folderPath}', '${logoFile.fullFileName}')">${logoFile.fileName}</a>
                                                                    <input type="hidden" name="logoUploadFile" id="${logoFile.id}" value="${logoFile.fullFilePath}">
                                                                    <button type="button" class="ml10" onclick="f_file_remove(this,'${logoFile.id}')">
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
                                                <div class="row mb-6 mt-8">
                                                    <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                        홍보 이미지
                                                    </h4>
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::info_box-->
                                                <div class="promotion_info_box">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6 required">홍보 이미지<span class="proImgNum">1</span></label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-6">
                                                                    <input type="text" id="promotionImage1" name="promotionImage" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="홍보 이미지" disabled/>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-6">
                                                                    <input type="file" id="promotionImageFile1" name="promotionImageFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                    <label class="btn btn-primary" for="promotionImageFile1">파일선택</label>
                                                                    <span class="proImgDel file_box_del mx-4 my-4 fs-base cursor-pointer">삭제</span>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                </div>
                                                <!--end::info_box-->
                                                <!--begin::info_box-->
                                                <div class="promotion_info_box">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">홍보 이미지<span class="proImgNum">2</span></label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-6">
                                                                    <input type="text" id="promotionImage2" name="promotionImage" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="홍보 이미지" disabled/>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-6">
                                                                    <input type="file" id="promotionImageFile2" name="promotionImageFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                    <label class="btn btn-primary" for="promotionImageFile2">파일선택</label>
                                                                    <span class="proImgDel file_box_del mx-4 my-4 fs-base cursor-pointer">삭제</span>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                </div>
                                                <!--end::info_box-->
                                                <!--begin::info_box-->
                                                <div class="promotion_info_box">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">홍보 이미지<span class="proImgNum">3</span></label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <!--begin::Row-->
                                                            <div class="row">
                                                                <!--begin::Col-->
                                                                <div class="col-lg-6">
                                                                    <input type="text" id="promotionImage3" name="promotionImage" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="홍보 이미지" disabled/>
                                                                </div>
                                                                <!--end::Col-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-6">
                                                                    <input type="file" id="promotionImageFile3" name="promotionImageFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                    <label class="btn btn-primary" for="promotionImageFile3">파일선택</label>
                                                                    <span class="proImgDel file_box_del mx-4 my-4 fs-base cursor-pointer">삭제</span>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Row-->
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                </div>
                                                <!--end::info_box-->

                                                <c:if test="${promotionImageFileList ne null and not empty promotionImageFileList}">
                                                    <!--begin::Input group-->
                                                    <div class="row">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">홍보 이미지</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <ul>
                                                                <c:forEach var="promotionImageFile" items="${promotionImageFileList}" begin="0" end="${promotionImageFileList.size()}" step="1">
                                                                    <li class="promotionImageFile_li mb-4">
                                                                        <c:set var="promotionImageSrc" value="${fn:replace(promotionImageFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                        <img src="${promotionImageSrc}" class="w-100px mr10" style="border: 1px solid #009ef7"/>
                                                                        <%--<a href="/file/download.do?path=exhibitor/company/${promotionImageFile.folderPath}&fileName=${promotionImageFile.fullFileName}">${promotionImageFile.fileName}</a>--%>
                                                                        <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${promotionImageFile.folderPath}', '${promotionImageFile.fullFileName}')">${promotionImageFile.fileName}</a>
                                                                        <input type="hidden" name="promotionImageUploadFile" id="${promotionImageFile.id}" value="${promotionImageFile.fullFilePath}">
                                                                        <button type="button" class="ml10" onclick="f_file_remove(this,'${promotionImageFile.id}')">
                                                                            <i class="ki-duotone ki-abstract-11">
                                                                                <i class="path1"></i>
                                                                                <i class="path2"></i>
                                                                            </i>
                                                                        </button>
                                                                    </li>
                                                                </c:forEach>
                                                            </ul>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                </c:if>

                                            </div>
                                            <!--end::Card body-->
                                            <!--begin::Actions-->
                                            <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                <span class="btn btn-primary" id="kt_promotion_info_add">추가</span>
                                            </div>
                                            <!--end::Actions-->
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
                                                <h3 class="fw-bold m-0">온라인 전시관 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_online_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::info box-->
                                                <div>
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                            회사 #1
                                                        </h4>
                                                    </div>
                                                    <!--end::Input group-->
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">회사소개영상</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <input type="text" id="companyIntroVideo" name="companyIntroVideo" class="form-control form-control-lg form-control-solid-bg" value="${info.companyIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M" />
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6 required">온라인전시회 노출여부</label>
                                                        <!--begin::Label-->
                                                        <!--begin::Label-->
                                                        <div class="col-lg-8 d-flex align-items-center">
                                                            <div class="form-check form-check-custom">
                                                                <input class="form-check-input form-control-solid-bg" type="radio" id="companyOnlineViewY" name="companyOnlineViewYn" value="Y" <c:if test="${info.companyOnlineViewYn eq 'Y'}">checked</c:if> />
                                                                <label class="form-check-label" for="companyOnlineViewY">
                                                                    노출허용
                                                                </label>
                                                                <input class="form-check-input form-control-solid-bg ml20" type="radio" id="companyOnlineViewN" name="companyOnlineViewYn" value="N" <c:if test="${info.companyOnlineViewYn eq 'N' or empty info.companyOnlineViewYn}">checked</c:if> />
                                                                <label class="form-check-label" for="companyOnlineViewN">
                                                                    노출차단
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <!--begin::Label-->
                                                    </div>
                                                    <!--end::Input group-->
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">기업 뱃지</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge1" name="companyBadge" value="보트쇼 3회 이상 참가" <c:if test="${fn:contains(info.companyBadge, '보트쇼 3회 이상 참가')}">checked</c:if> />
                                                                <label class="form-check-label" for="companyBadge1">
                                                                    보트쇼 3회 이상 참가
                                                                </label>
                                                            </div>
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge2" name="companyBadge" value="올해의 제품상 수상" <c:if test="${fn:contains(info.companyBadge, '올해의 제품상 수상')}">checked</c:if> />
                                                                <label class="form-check-label" for="companyBadge2">
                                                                    올해의 제품상 수상
                                                                </label>
                                                            </div>
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge3" name="companyBadge" value="보트쇼와 제작한 영상" <c:if test="${fn:contains(info.companyBadge, '보트쇼와 제작한 영상')}">checked</c:if> />
                                                                <label class="form-check-label" for="companyBadge3">
                                                                    보트쇼와 제작한 영상
                                                                </label>
                                                            </div>
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge4" name="companyBadge" value="제품 등록 우수" <c:if test="${fn:contains(info.companyBadge, '제품 등록 우수')}">checked</c:if> />
                                                                <label class="form-check-label" for="companyBadge4">
                                                                    제품 등록 우수
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <!--end::Col-->
                                                    </div>
                                                    <!--end::Input group-->
                                                </div>
                                                <!--end::info box-->

                                                <c:if test="${empty onlineList}">
                                                    <!--begin::info box-->
                                                    <div class="online_info_box">
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                제품 #<span class="onlineInfoNum">1</span>
                                                                <span class="onlineInfoDel float-end cursor-pointer">삭제</span>
                                                            </h4>
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품 사진</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <!--begin::Row-->
                                                                <div class="row onlinePrdBox mb-3">
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-6">
                                                                        <input type="text" name="productImage" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="제품 사진" disabled/>
                                                                    </div>
                                                                    <!--end::Col-->
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-6">
                                                                        <input type="file" id="productImageFile1_1" name="productImageFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                        <label class="btn btn-primary" for="productImageFile1_1">파일선택</label>
                                                                        <span class="onlinePrdAdd" style="cursor: pointer">추가</span>
                                                                        <span class="onlinePrdDel" style="cursor: pointer">삭제</span>
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
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품명</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <!--begin::Row-->
                                                                <div class="row">
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-6">
                                                                        <input type="text" name="productNameKo" class="form-control form-control-lg form-control-solid-bg" placeholder="국문" />
                                                                    </div>
                                                                    <!--end::Col-->
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-6">
                                                                        <input type="text" name="productNameEn" class="form-control form-control-lg form-control-solid-bg" placeholder="영문" />
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
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품분류</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <!--begin::Row-->
                                                                <div class="row">
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-6">
                                                                        <select name="productOptionBig" class="form-select form-control-solid-bg"></select>
                                                                    </div>
                                                                    <!--end::Col-->
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-6">
                                                                        <select name="productOptionSmall" class="form-select form-control-solid-bg"></select>
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
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품 설명</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <textarea name="productIntroKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" ></textarea>
                                                                <textarea name="productIntroEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" ></textarea>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6">제품 영상</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" name="productIntroVideo" class="form-control form-control-lg form-control-solid-bg" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6">전장(m)</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" name="productWidth" class="form-control form-control-lg form-control-solid-bg onlyNumDec" placeholder="전장(m)" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                        <!--begin::Input group-->
                                                        <div class="row mb-6">
                                                            <!--begin::Label-->
                                                            <label class="col-lg-2 col-form-label fw-semibold fs-6">마력</label>
                                                            <!--end::Label-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-10">
                                                                <input type="text" name="productHorsePower" class="form-control form-control-lg form-control-solid-bg onlyNumDec" placeholder="전장(m)" />
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Input group-->
                                                    </div>
                                                    <!--end::info box-->
                                                </c:if>

                                                <c:if test="${not empty onlineList}">
                                                    <c:forEach var="online" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                                        <!--begin::info box-->
                                                        <div class="online_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    <input type="hidden" name="onlineItemSeq" value="${online.seq}">
                                                                    제품 #<span class="onlineInfoNum">${status.index + 1}</span>
                                                                    <span class="onlineInfoDel float-end cursor-pointer">삭제</span>
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->

                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품 사진</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row onlinePrdBox mb-3">
                                                                        <c:set var="productImageNumIdx" value="0" />
                                                                        <c:forEach var="productImageFile" items="${productImageFileList}" begin="0" end="${productImageFileList.size()}" step="1">
                                                                            <c:set var="onlineInfoNumIdx" value="${status.index + 1}"/>
                                                                            <c:if test="${fn:contains(productImageFile.note , 'productImage'.concat(onlineInfoNumIdx.toString()).concat('_'))}">
                                                                                <c:set var="productImageNumIdx" value="${productImageNumIdx + 1}" />
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <input type="text" id="productImage${status.index + 1}_${productImageNumIdx + 1}" name="productImage" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="제품 사진" disabled/>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <input type="file" id="productImageFile${status.index + 1}_${productImageNumIdx + 1}" name="productImageFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                            <label class="btn btn-primary" for="productImageFile${status.index + 1}_${productImageNumIdx + 1}">파일선택</label>
                                                                            <span class="onlinePrdAdd" style="cursor: pointer">추가</span>
                                                                            <span class="onlinePrdDel" style="cursor: pointer">삭제</span>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                    </div>
                                                                    <!--end::Row-->
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->

                                                            <c:if test="${productImageFileList ne null and not empty productImageFileList}">

                                                                <!--begin::Input group-->
                                                                <div class="row mb-6 preValueList">
                                                                    <!--begin::Label-->
                                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">제품 사진 파일목록</label>
                                                                    <!--end::Label-->
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-10">
                                                                        <ul>
                                                                            <c:forEach var="productImageFile" items="${productImageFileList}" begin="0" end="${productImageFileList.size()}" step="1">
                                                                                <c:set var="idx" value="${status.index + 1}"/>
                                                                                <c:if test="${fn:contains(productImageFile.note, 'productImage'.concat(idx.toString()).concat('_'))}">
                                                                                    <li class="productImageFile_li">
                                                                                        <c:set var="productImageFileSrc" value="${fn:replace(productImageFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                                        <img src="${productImageFileSrc}" class="w-100px mr10" style="border: 1px solid #009ef7"/>
                                                                                        <%--<a href="/file/download.do?path=exhibitor/company/${productImageFile.folderPath}&fileName=${productImageFile.fullFileName}">${productImageFile.fileName}</a>--%>
                                                                                        <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${productImageFile.folderPath}', '${productImageFile.fullFileName}')">${productImageFile.fileName}</a>
                                                                                        <input type="hidden" name="productImageUploadFile" id="${productImageFile.id}" value="${productImageFile.fullFilePath}">
                                                                                        <button type="button" class="ml10" onclick="f_file_remove(this,'${productImageFile.id}')">
                                                                                            <i class="ki-duotone ki-abstract-11">
                                                                                                <i class="path1"></i>
                                                                                                <i class="path2"></i>
                                                                                            </i>
                                                                                        </button>
                                                                                    </li>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </div>
                                                                    <!--end::Col-->
                                                                </div>
                                                                <!--end::Input group-->
                                                            </c:if>

                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품명</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row">
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <input type="text" name="productNameKo" class="form-control form-control-lg form-control-solid-bg" placeholder="국문" value="${online.productNameKo}" />
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <input type="text" name="productNameEn" class="form-control form-control-lg form-control-solid-bg" placeholder="영문" value="${online.productNameEn}" />
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
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품분류</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row">
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <select name="productOptionBig" class="form-select form-control-solid-bg"></select>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <select name="productOptionSmall" class="form-select form-control-solid-bg"></select>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                    </div>
                                                                    <!--end::Row-->
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->

                                                            <!--begin::Input group-->
                                                            <div class="row mb-6 preValueList">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제품분류(기존값)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row">
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6 col-form-label text-primary">
                                                                            <input type="text" name="productOptionBig" class="form-control form-control-lg form-control-solid-bg" value="${online.productOptionBig}" placeholder="제품분류(대)" disabled/>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6 col-form-label text-primary">
                                                                            <input type="text" name="productOptionSmall" class="form-control form-control-lg form-control-solid-bg" value="${online.productOptionSmall}" placeholder="제품분류(소)" disabled/>
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
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품 설명</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <textarea name="productIntroKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" >${online.productIntroKo}</textarea>
                                                                    <textarea name="productIntroEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" >${online.productIntroEn}</textarea>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제품 영상</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="productIntroVideo" class="form-control form-control-lg form-control-solid-bg" value="${online.productIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">전장(m)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="productWidth" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.productWidth}" placeholder="전장(m)" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">마력</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="productHorsePower" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.productHorsePower}" placeholder="전장(m)" />
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->

                                                        </div>
                                                        <!--end::info box-->
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                            <!--end::Card body-->
                                            <!--begin::Actions-->
                                            <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                <span class="btn btn-primary" id="kt_online_info_add">추가</span>
                                            </div>
                                            <!--end::Actions-->
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
                                                <h3 class="fw-bold m-0">비즈니스 상담</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_export_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-0">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">비즈니스 상담 참가 희망 여부</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-8 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" id="exportMeetingY" name="exportMeetingYn" value="Y" <c:if test="${info.exportMeetingYn eq 'Y'}">checked</c:if> />
                                                            <label class="form-check-label" for="exportMeetingY">
                                                                참가
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" id="exportMeetingN" name="exportMeetingYn" value="N" <c:if test="${info.exportMeetingYn eq 'N' or info.exportMeetingYn eq null or info.exportMeetingYn eq ''}">checked</c:if> />
                                                            <label class="form-check-label" for="exportMeetingN">
                                                                참가 안 함
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->

                                                <!--begin::buyer add group-->
                                                <div class="form_add_buyer">
                                                    <!--begin::Input group-->
                                                    <div class="row mt-5 mb-2">
                                                        <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                            초청 희망 바이어
                                                        </h4>
                                                    </div>
                                                    <!--end::Input group-->

                                                    <c:if test="${buyerList ne null and not empty buyerList}">
                                                        <c:forEach var="buyer" items="${buyerList}" begin="0" end="${buyerList.size()}" step="1">
                                                            <input type="hidden" name="buyerItemSeq" value="${buyer.seq}">
                                                            <div class="form_chuga_list">
                                                                <div class="cont">
                                                                    <div class="name">${buyer.buyerCompanyName}</div>
                                                                    <div class="gubun">${buyer.buyerCompanyCountry} / ${buyer.buyerCompanyLocation} / ${buyer.buyerCompanyDepart} ${buyer.buyerCompanyPosition}</div>
                                                                </div>
                                                                <div class="modifyFormBuyer modifyFormList" onclick="f_buyer_modify_modal('select','${buyer.seq}')">수정</div>
                                                                <div class="delFormBuyer delFormList" onclick="f_buyer_remove('select', this, '${buyer.seq}')">삭제</div>
                                                                <input type="hidden" name="buyerCompanyName" value="${buyer.buyerCompanyName}">
                                                                <input type="hidden" name="buyerCompanyCountry" value="${buyer.buyerCompanyCountry}">
                                                                <input type="hidden" name="buyerCompanyLocation" value="${buyer.buyerCompanyLocation}">
                                                                <input type="hidden" name="buyerCompanyHomepage" value="${buyer.buyerCompanyHomepage}">
                                                                <input type="hidden" name="buyerCompanyDepart" value="${buyer.buyerCompanyDepart}">
                                                                <input type="hidden" name="buyerCompanyPosition" value="${buyer.buyerCompanyPosition}">
                                                                <input type="hidden" name="buyerCompanyEmail" value="${buyer.buyerCompanyEmail}">
                                                                <input type="hidden" name="buyerCompanyTel" value="${buyer.buyerCompanyTel}">
                                                                <input type="hidden" name="buyerCompanyPhone" value="${buyer.buyerCompanyPhone}">
                                                                <input type="hidden" name="buyerCompanyFax" value="${buyer.buyerCompanyFax}">
                                                                <input type="hidden" name="buyerCompanyItem" value="${buyer.buyerCompanyItem}">
                                                                <input type="hidden" name="buyerCompanyInviteReason" value="${buyer.buyerCompanyInviteReason}">
                                                                <input type="hidden" name="buyerCompanyProgressYn" value="${buyer.buyerCompanyProgressYn}">
                                                                <input type="hidden" name="buyerCompanyHope" value="${buyer.buyerCompanyHope}">
                                                            </div>
                                                        </c:forEach>
                                                    </c:if>

                                                    <!--begin::Input group-->
                                                    <div class="row">
                                                        <a id="buyer_add_btn" class="btn btn-primary" data-bs-toggle="modal"
                                                           data-bs-target="#kt_buyer_popup">여기를 클릭하여 바이어 등록</a>
                                                    </div>
                                                    <!--end::Input group-->
                                                </div>
                                                <!--end::buyer add group-->
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->
                                </form>
                                <!--end::Form-->

                                <!--begin::Basic info-->
                                <div class="card mb-5 mb-xl-10">
                                    <!--begin::Actions-->
                                    <div class="card-footer d-flex justify-content-between py-6 px-9">
                                        <div>
                                            <a href="javascript:void(0);" onclick="window.location.href = '/mng/exhibitor/transfer/company.do'" class="btn btn-info btn-active-light-info">목록</a>
                                        </div>
                                        <div>
                                            <button type="button" onclick="f_transfer_company_detail('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                            <button type="button" onclick="f_transfer_exhibitor_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
                                        </div>
                                    </div>
                                    <!--end::Actions-->
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

    <!--begin::Modal - 새 초청 희망 바이어 등록/수정-->
    <div class="modal fade" id="kt_buyer_popup" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div class="modal-dialog modal-dialog-centered mw-1000px">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">새 초청 희망 바이어 등록/수정</h2>
                    <!--end::Modal title-->
                    <!--begin::Close-->
                    <div class="btn btn-sm btn-icon btn-active-color-primary" data-bs-dismiss="modal" onclick="f_buyer_init()">
                        <i class="ki-duotone ki-cross fs-1">
                            <span class="path1"></span>
                            <span class="path2"></span>
                        </i>
                    </div>
                    <!--end::Close-->
                </div>
                <!--end::Modal header-->
                <!--begin::Modal body-->
                <div class="modal-body scroll-y px-10 px-lg-15 pt-0 pb-15">
                    <!--begin:Form-->
                    <form id="kt_buyer_modal_form">
                        <!--begin::Heading-->
                        <div class="my-10 text-start">
                            <!--begin::Title-->
                            <h1 class="mb-3">바이어 정보</h1>
                            <!--end::Title-->
                        </div>
                        <!--end::Heading-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>회사명</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyName" name="buyerCompanyName" class="form-control form-control-solid-bg" placeholder="회사명"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>국가</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyCountry" name="buyerCompanyCountry" class="form-control form-control-solid-bg" placeholder="국가"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>소재지</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyLocation" name="buyerCompanyLocation" class="form-control form-control-solid-bg" placeholder="소재지"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>홈페이지</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyHomepage" name="buyerCompanyHomepage" class="form-control form-control-solid-bg" placeholder="홈페이지"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>부서</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyDepart" name="buyerCompanyDepart" class="form-control form-control-solid-bg" placeholder="부서"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>직책</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyPosition" name="buyerCompanyPosition" class="form-control form-control-solid-bg" placeholder="직책"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>이메일</span>
                            </label>
                            <!--end::Label-->
                            <div class="input-group">
                                <input type="text" id="buyer_company_email_input_1" name="buyerCompanyEmail" class="form-control form-control-solid-bg" placeholder="이메일"/>
                                <span class="input-group-text">@</span>
                                <input type="text" id="buyer_company_email_input_2" name="buyerCompanyDomain" class="form-control form-control-solid-bg" placeholder="도메인"/>
                                <select id="buyer_company_email_select" class="form-select form-control-solid-bg ml10">
                                    <option selected>직접입력</option>
                                    <option value="daum.net">daum.net</option>
                                    <option value="nate.com">nate.com</option>
                                    <option value="hanmail.net">hanmail.net</option>
                                    <option value="naver.com">naver.com</option>
                                    <option value="hotmail.com">hotmail.com</option>
                                    <option value="yahoo.co.kr">yahoo.co.kr</option>
                                    <option value="empal.com">empal.com</option>
                                    <option value="korea.com">korea.com</option>
                                    <option value="hanmir.com">hanmir.com</option>
                                    <option value="dreamwiz.com">dreamwiz.com</option>
                                    <option value="orgio.net">orgio.net</option>
                                    <option value="korea.com">korea.com</option>
                                    <option value="hitel.net">hitel.net</option>
                                </select>
                            </div>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>전화번호</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyTel" name="buyerCompanyTel" class="form-control form-control-solid-bg" maxlength="13" placeholder="전화번호 ( - 하이픈 포함 )"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>휴대전화</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyPhone" name="buyerCompanyPhone" class="form-control form-control-solid-bg" maxlength="13" placeholder="휴대전화 ( - 하이픈 포함 )"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>팩스</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyFax" name="buyerCompanyFax" class="form-control form-control-solid-bg" placeholder="팩스"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>취급품목</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyItem" name="buyerCompanyItem" class="form-control form-control-solid-bg" placeholder="취급품목"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>초청사유</span>
                            </label>
                            <!--end::Label-->
                            <textarea id="buyerCompanyInviteReason" name="buyerCompanyInviteReason" class="form-control form-control-solid-bg resize-none h-100px" rows="3" placeholder="초청사유"></textarea>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>계약진행 여부</span>
                            </label>
                            <!--end::Label-->
                            <select id="buyerCompanyProgressYn" name="buyerCompanyProgressYn" class="form-select form-select-solid" data-control="select2" data-hide-search="true" data-placeholder="- select -">
                                <option value="Y">YES</option>
                                <option value="N">NO</option>
                            </select>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Input group-->
                        <div class="d-flex flex-column mb-8">
                            <!--begin::Label-->
                            <label class="d-flex align-items-center fs-6 fw-semibold mb-2">
                                <span>희망사항</span>
                            </label>
                            <!--end::Label-->
                            <input type="text" id="buyerCompanyHope" name="buyerCompanyHope" class="form-control form-control-solid-bg" placeholder="희망사항"/>
                        </div>
                        <!--end::Input group-->
                        <!--begin::Actions-->
                        <div class="text-center">
                            <a class="btn btn-primary w-100" href="javascript:f_buyer_add()">등록 및 수정</a>
                        </div>
                        <!--end::Actions-->
                    </form>
                    <!--end:Form-->
                </div>
                <!--end::Modal body-->
            </div>
            <!--end::Modal content-->
        </div>
        <!--end::Modal dialog-->
    </div>
    <!--end::Modal - 새 초청 희망 바이어 등록/수정-->

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
    <script src="/js/custom/transferCompany.js?ver=<%=System.currentTimeMillis()%>"></script>

    <c:if test="${info ne null}">
        <c:if test="${info.exportMeetingYn eq 'Y'}">
            <script> $(document).ready(function() { document.querySelector('.form_add_buyer').style.display = 'block'; }); </script>
        </c:if>
        <c:if test="${info.exportMeetingYn eq 'N'}">
            <script> $(document).ready(function() { document.querySelector('.form_add_buyer').style.display = 'none'; }); </script>
        </c:if>
    </c:if>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>