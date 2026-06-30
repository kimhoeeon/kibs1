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
                            <div id="kt_app_toolbar_container" class="app-container container-full d-flex flex-stack">
                                <!--begin::Page title-->
                                <div class="page-title d-flex flex-column justify-content-center flex-wrap me-3">
                                    <!--begin::Title-->
                                    <h1 class="page-heading d-flex text-dark fw-bold fs-3 flex-column justify-content-center my-0">
                                        온라인 전시관</h1>
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
                                        <li class="breadcrumb-item text-muted">참가신청서 관리 (2027~)</li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item">
                                            <span class="bullet bg-gray-400 w-5px h-2px"></span>
                                        </li>
                                        <!--end::Item-->
                                        <!--begin::Item-->
                                        <li class="breadcrumb-item text-muted">온라인 전시관</li>
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
                                <form id="onlineForm" name="onlineForm" method="post" onsubmit="return false;">
                                    <input type="hidden" id="seq" name="seq" value="${info.seq}"/>
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">제품 노출 정보</h3>
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
                                                            <input type="text" id="companyIntroVideo" name="companyIntroVideo" class="form-control form-control-lg form-control-solid-bg" value="${info.companyIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M" readonly/>
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
                                                                <input class="form-check-input form-control-solid-bg" type="radio" id="companyOnlineViewY" name="companyOnlineViewYn" value="Y" <c:if test="${info.companyOnlineViewYn eq 'Y'}">checked</c:if> disabled/>
                                                                <label class="form-check-label" for="companyOnlineViewY">
                                                                    노출허용
                                                                </label>
                                                                <input class="form-check-input form-control-solid-bg ml20" type="radio" id="companyOnlineViewN" name="companyOnlineViewYn" value="N" <c:if test="${info.companyOnlineViewYn eq 'N' or empty info.companyOnlineViewYn}">checked</c:if> disabled/>
                                                                <label class="form-check-label" for="companyOnlineViewN">
                                                                    노출차단
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <!--begin::Label-->
                                                    </div>
                                                    <!--end::Input group-->
                                                    <!--begin::Input group-->
                                                    <div class="row mb-6" id="companyBadgeForm">
                                                        <!--begin::Label-->
                                                        <label class="col-lg-2 col-form-label fw-semibold fs-6">기업 뱃지</label>
                                                        <!--end::Label-->
                                                        <!--begin::Col-->
                                                        <div class="col-lg-10">
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge1" name="companyBadge" value="보트쇼 3회 이상 참가" <c:if test="${fn:contains(info.companyBadge, '보트쇼 3회 이상 참가')}">checked</c:if>/>
                                                                <label class="form-check-label" for="companyBadge1">
                                                                    보트쇼 3회 이상 참가
                                                                </label>
                                                            </div>
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge2" name="companyBadge" value="올해의 제품상 수상" <c:if test="${fn:contains(info.companyBadge, '올해의 제품상 수상')}">checked</c:if>/>
                                                                <label class="form-check-label" for="companyBadge2">
                                                                    올해의 제품상 수상
                                                                </label>
                                                            </div>
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge3" name="companyBadge" value="보트쇼와 제작한 영상" <c:if test="${fn:contains(info.companyBadge, '보트쇼와 제작한 영상')}">checked</c:if>/>
                                                                <label class="form-check-label" for="companyBadge3">
                                                                    보트쇼와 제작한 영상
                                                                </label>
                                                            </div>
                                                            <div class="form-check form-check-custom form-check-lg mb-2">
                                                                <input class="form-check-input form-control-solid-bg" type="checkbox" id="companyBadge4" name="companyBadge" value="제품 등록 우수" <c:if test="${fn:contains(info.companyBadge, '제품 등록 우수')}">checked</c:if>/>
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

                                                <c:if test="${not empty onlineList}">
                                                    <c:forEach var="online" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                                        <!--begin::info box-->
                                                        <div class="online_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    <input type="hidden" name="onlineItemSeq" value="${online.seq}">
                                                                    제품 #<span class="onlineInfoNum">${status.index + 1}</span>
                                                                    <%--<span class="onlineInfoDel float-end cursor-pointer">삭제</span>--%>
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <%--<div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">제품 사진</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row onlinePrdBox mb-3">
                                                                        <c:forEach var="productImageFile" items="${onlineImageFileList}" begin="0" end="${onlineImageFileList.size()}" step="1">
                                                                            <c:set var="onlineInfoNumIdx" value="${status.index + 1}"/>
                                                                            <c:if test="${fn:contains(productImageFile.note, 'productImage'.concat(onlineInfoNumIdx.toString()))}">
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
                                                            </div>--%>
                                                            <!--end::Input group-->

                                                            <c:if test="${onlineImageFileList ne null and not empty onlineImageFileList}">

                                                                <!--begin::Input group-->
                                                                <div class="row mb-6 preValueList">
                                                                    <!--begin::Label-->
                                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">제품 사진 파일목록</label>
                                                                    <!--end::Label-->
                                                                    <!--begin::Col-->
                                                                    <div class="col-lg-10">
                                                                        <ul>
                                                                        <c:forEach var="onlineImageFile" items="${onlineImageFileList}" begin="0" end="${onlineImageFileList.size()}" step="1">
                                                                            <c:set var="idx" value="${status.index + 1}"/>
                                                                            <c:if test="${fn:contains(onlineImageFile.note, 'onlineImage'.concat(idx.toString()).concat('_'))}">
                                                                                <li class="onlineImageFile_li">
                                                                                    <c:set var="onlineImageFileSrc" value="${fn:replace(onlineImageFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                                    <img src="${onlineImageFileSrc}" class="w-100px mr10" style="border: 1px solid #009ef7"/>
                                                                                    <%--<a href="/file/download.do?path=exhibitor/company/${onlineImageFile.folderPath}&fileName=${onlineImageFile.fullFileName}">${onlineImageFile.fileName}</a>--%>
                                                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${onlineImageFile.folderPath}', '${onlineImageFile.fullFileName}')">${onlineImageFile.fileName}</a>
                                                                                    <input type="hidden" name="onlineImageUploadFile" id="${onlineImageFile.id}" value="${onlineImageFile.fullFilePath}">
                                                                                    <%--<button type="button" class="ml10" onclick="f_file_remove(this,'${onlineImageFile.id}')">
                                                                                        <i class="ki-duotone ki-abstract-11">
                                                                                            <i class="path1"></i>
                                                                                            <i class="path2"></i>
                                                                                        </i>
                                                                                    </button>--%>
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
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제품명</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row">
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <input type="text" name="onlineNameKo" class="form-control form-control-lg form-control-solid-bg" placeholder="국문" value="${online.onlineNameKo}" disabled/>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <input type="text" name="onlineNameEn" class="form-control form-control-lg form-control-solid-bg" placeholder="영문" value="${online.onlineNameEn}" disabled/>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                    </div>
                                                                    <!--end::Row-->
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <%--<div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제품분류</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row">
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <select name="onlineOptionBig" class="form-select form-control-solid-bg"></select>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6">
                                                                            <select name="onlineOptionSmall" class="form-select form-control-solid-bg"></select>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                    </div>
                                                                    <!--end::Row-->
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>--%>
                                                            <!--end::Input group-->

                                                            <!--begin::Input group-->
                                                            <div class="row mb-6 preValueList">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제품분류<%--(기존값)--%></label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <!--begin::Row-->
                                                                    <div class="row">
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6 col-form-label text-primary">
                                                                            <input type="text" name="onlineOptionBig" class="form-control form-control-lg form-control-solid-bg" value="${online.onlineOptionBig}" placeholder="제품분류(대)" disabled/>
                                                                        </div>
                                                                        <!--end::Col-->
                                                                        <!--begin::Col-->
                                                                        <div class="col-lg-6 col-form-label text-primary">
                                                                            <input type="text" name="onlineOptionSmall" class="form-control form-control-lg form-control-solid-bg" value="${online.onlineOptionSmall}" placeholder="제품분류(소)" disabled/>
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
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제품 설명</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <textarea name="onlineIntroKo" class="form-control form-control-solid-bg resize-none h-100px" placeholder="국문" disabled>${online.onlineIntroKo}</textarea>
                                                                    <textarea name="onlineIntroEn" class="form-control form-control-solid-bg resize-none h-100px mt5" placeholder="영문" disabled>${online.onlineIntroEn}</textarea>
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
                                                                    <input type="text" name="onlineLink" class="form-control form-control-lg form-control-solid-bg" value="${online.onlineLink}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M" readonly/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">길이(cm)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="onlineLength" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.onlineLength}" placeholder="길이(cm)" readonly/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">너비(cm)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="onlineWidth" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.onlineWidth}" placeholder="너비(cm)" readonly/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">높이(cm)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="onlineHeight" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.onlineHeight}" placeholder="높이(cm)" readonly/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">중량(kg)</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="onlineWeight" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.onlineWeight}" placeholder="중량(kg)" readonly/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">소재</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="onlineMaterial" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.onlineMaterial}" placeholder="소재" readonly/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">연식</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="onlineYear" class="form-control form-control-lg form-control-solid-bg onlyNumDec" value="${online.onlineYear}" placeholder="연식" readonly/>
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
                                            <%--<div class="card-footer d-flex justify-content-end py-6 px-9">
                                                <span class="btn btn-primary" id="kt_online_info_add">추가</span>
                                            </div>--%>
                                            <!--end::Actions-->
                                        </div>
                                        <!--end::Content-->
                                    </div>
                                    <!--end::Basic info-->

                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitorNewNew/application/online.do" class="btn btn-info btn-active-light-info cursor-pointer">목록</a>
                                            </div>
                                            <div>
                                                <%--<button type="button" onclick="f_application_booth_modify_init_set('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_application_booth_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>--%>
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

    <script src="/js/jquery.number.min.js"></script>
    <!--end::Custom Javascript-->

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=20260630"></script>
    <script src="/js/custom/online_new_new.js?ver=20260630"></script>

    <%--<script type="text/javascript">
        $(function(){
            $('#form_add_total').val($('.booth_cost')[0].innerText); // 초기 총액 : 등록비 합계 Set
        });
    </script>--%>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>