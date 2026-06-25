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
                                        경품 제공 신청</h1>
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
                                        <li class="breadcrumb-item text-muted">참가신청서 관리 (2026)</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">경품 제공 신청</li>
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
                                <form id="giftForm" name="giftForm" method="post" onsubmit="return false;">
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
                                                <h4 class="fw-bold">${giftList.get(0).companyNameKo} / ${giftList.get(0).companyNameEn}</h4>
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->

                                    <%--<!--begin::Basic info-->
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
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">이름</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="name" class="form-control form-control-lg form-control-solid-bg" placeholder="이름"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">전화번호</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="tel" class="form-control form-control-lg form-control-solid-bg" placeholder="전화번호"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">휴대전화</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="phone" class="form-control form-control-lg form-control-solid-bg" placeholder="휴대전화"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">E-Mail</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="email" class="form-control form-control-lg form-control-solid-bg" placeholder="이메일"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">Fax</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="fax" class="form-control form-control-lg form-control-solid-bg" placeholder="FAX"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->--%>

                                    <c:if test="${not empty giftList}">
                                    <c:forEach var="gift" items="${giftList}" begin="0" end="${giftList.size()}" step="1" varStatus="status">
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10 shadow-sm formTbBd">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0 collapsible cursor-pointer rotate" data-bs-toggle="collapse" data-bs-target="#kt_docs_card_collapsible${status.index + 1}">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">경품 신청 등록 정보 #<span class="addNum">${status.index + 1}</span></h3>
                                            </div>
                                            <!--end::Card title-->
                                            <!--begin::Card-toolbar-->
                                            <div class="card-toolbar rotate-180">
                                                <i class="ki-duotone ki-down fs-1"></i>
                                            </div>
                                            <!--end::Card-toolbar-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_docs_card_collapsible${status.index + 1}" class="collapse show">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">구분</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="radio" class="form-check-input form-control-solid-bg" value="${gift.giftGbn}" checked/>
                                                        <label class="form-check-label ms-2">
                                                            ${gift.giftGbn}
                                                        </label>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">수량</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="giftCnt" value="${gift.giftCnt}" class="form-control form-control-lg form-control-solid-bg" placeholder="수량"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">분류</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="giftClassify" value="${gift.giftClassify}" class="form-control form-control-lg form-control-solid-bg" placeholder="분류"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">품목명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="giftName" value="${gift.giftName}" class="form-control form-control-lg form-control-solid-bg" placeholder="품목명"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">경품설명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea name="giftContent" class="form-control form-control-solid-bg resize-none h-100px" rows="3" placeholder="경품설명">${gift.giftContent}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">경품 사진</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="gift_photo" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="경품사진" disabled/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="file" id="giftPhotoFile1" name="giftPhotoFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                <label class="btn btn-primary">파일선택</label>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->

                                                <c:if test="${giftPhotoFileList ne null and not empty giftPhotoFileList}">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6 fileList">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">경품 사진 파일 목록</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <ul>
                                                            <c:forEach var="giftPhotoImageFile" items="${giftPhotoFileList}" begin="0" end="${giftPhotoFileList.size()}" step="1">
                                                                <c:set var="idx" value="${status.index + 1}"/>
                                                                <c:if test="${fn:contains(giftPhotoImageFile.note, 'giftPhoto'.concat(idx.toString()))}">
                                                                <li class="giftPhotoImageFile_li" style="margin-bottom: 5px;">
                                                                    <c:set var="giftPhotoImageSrc" value="${fn:replace(giftPhotoImageFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                    <img src="${giftPhotoImageSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                                    <%--<a href="/file/download.do?path=exhibitor/company/${giftPhotoImageFile.folderPath}&fileName=${giftPhotoImageFile.fullFileName}">${giftPhotoImageFile.fileName}</a>--%>
                                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${giftPhotoImageFile.folderPath}', '${giftPhotoImageFile.fullFileName}')">${giftPhotoImageFile.fileName}</a>
                                                                    <input type="hidden" name="giftPhotoImageUploadFile" id="${giftPhotoImageFile.id}" value="${giftPhotoImageFile.fullFilePath}"/>
                                                                    <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${giftPhotoImageFile.id}')">X</button>
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">경품 브랜드 회사 로고</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="gift_company_logo" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="회사로고" disabled/>
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="file" id="giftCompanyLogoFile1" name="giftCompanyLogoFile" class="d-none upload_hidden" accept=".png, .jpg, .jpeg">
                                                                <label class="btn btn-primary">파일선택</label>
                                                            </div>
                                                            <!--end::Col-->
                                                        </div>
                                                        <!--end::Row-->
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->

                                                <c:if test="${giftCompanyLogoList ne null and not empty giftCompanyLogoList}">
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6 fileList">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">경품 브랜드 회사 로고 파일 목록</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <ul>
                                                                <c:forEach var="giftCompanyLogoImageFile" items="${giftCompanyLogoList}" begin="0" end="${giftCompanyLogoList.size()}" step="1">
                                                                    <c:set var="idx" value="${status.index + 1}"/>
                                                                    <c:if test="${fn:contains(giftCompanyLogoImageFile.note, 'giftCompanyLogo'.concat(idx.toString()))}">
                                                                        <li class="giftCompanyLogoImageFile_li" style="margin-bottom: 5px;">
                                                                            <c:set var="giftCompanyLogoImageSrc" value="${fn:replace(giftCompanyLogoImageFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                            <img src="${giftCompanyLogoImageSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                                            <%--<a href="/file/download.do?path=exhibitor/company/${giftCompanyLogoImageFile.folderPath}&fileName=${giftCompanyLogoImageFile.fullFileName}">${giftCompanyLogoImageFile.fileName}</a>--%>
                                                                            <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${giftCompanyLogoImageFile.folderPath}', '${giftCompanyLogoImageFile.fullFileName}')">${giftCompanyLogoImageFile.fileName}</a>
                                                                            <input type="hidden" name="giftCompanyLogoImageUploadFile" id="${giftCompanyLogoImageFile.id}" value="${giftCompanyLogoImageFile.fullFilePath}"/>
                                                                            <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${giftCompanyLogoImageFile.id}')">X</button>
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">소비자가</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="giftPrice" value="${gift.giftPrice}" class="form-control form-control-lg form-control-solid-bg" placeholder="소비자가"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">협찬가</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" name="giftSponsorPrice" value="${gift.giftSponsorPrice}" class="form-control form-control-lg form-control-solid-bg" placeholder="협찬가"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">비고</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <textarea name="giftNote" class="form-control form-control-solid-bg resize-none h-100px" rows="3" placeholder="비고">${gift.giftNote}</textarea>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->

                                                <!--begin::Input group-->
                                                <div class="row">
                                                    <!--begin::begin button-->
                                                    <div class="d-flex align-items-center justify-content-end">
                                                        <input type="hidden" name="giftSeq" value="${gift.id}">
                                                        <input type="hidden" name="giftId" value="${exSeq}">
                                                        <input type="hidden" name="giftPhoto_hid" value="${gift.giftPhoto}">
                                                        <input type="hidden" name="giftCompanyLogo_hid" value="${gift.giftCompanyLogo}">
                                                        <div class="btn btn-danger addDel">삭제</div>
                                                    </div>
                                                    <!--begin::end button-->
                                                </div>
                                                <!--end::Input group-->
                                            </div>
                                            <!--end::Card body-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->
                                    </c:forEach>
                                    </c:if>

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10 bg-transparent">
                                        <!--begin::begin button-->
                                        <div class="d-flex align-items-center justify-content-end">
                                            <div class="btn btn-primary form_add_btn">경품 추가</div>
                                        </div>
                                        <!--begin::end button-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitorNew/application/gift.do" class="btn btn-info btn-active-light-info cursor-pointer">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_application_gift_new_modify_init_set('${exSeq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_application_gift_new_save('${exSeq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
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
    <script src="/js/mngMain.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/custom/gift_new.js?ver=<%=System.currentTimeMillis()%>"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>