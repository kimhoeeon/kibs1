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
                                        참관객 목록</h1>
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
                                        <li class="breadcrumb-item text-muted">참관객 목록</li>
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
                                <form id="visitorForm" name="visitorForm" method="post" onsubmit="return false;">
                                    <input type="hidden" name="seq" value="${info.seq}">
                                    <input type="hidden" name="visitorSeq" value="${info.seq}">
                                    <!--begin::Basic info-->
                                    <div class="card mb-5 mb-xl-10">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">참관 구분</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div>
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">참관 구분</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="바이어" id="partGbnY" name="partGbn" <c:if test="${info.partGbn eq '바이어'}">checked</c:if> />
                                                            <label class="form-check-label" for="partGbnY">
                                                                바이어
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="일반관람" id="partGbnN" name="partGbn" <c:if test="${info.partGbn eq '일반관람'}">checked</c:if> />
                                                            <label class="form-check-label" for="partGbnN">
                                                                일반관람
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">행사 구분</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="경기국제보트쇼" id="eventGbn1" name="eventGbn" <c:if test="${info.eventGbn eq '경기국제보트쇼'}">checked</c:if> />
                                                            <label class="form-check-label" for="eventGbn1">
                                                                경기국제보트쇼(KIBS)·한국국제낚시박람회(KOFISH)
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="코리아서프쇼" id="eventGbn2" name="eventGbn" <c:if test="${info.eventGbn eq '코리아서프쇼'}">checked</c:if> />
                                                            <label class="form-check-label" for="eventGbn2">
                                                                코리아서프쇼(KISS)
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="해양관광전" id="eventGbn3" name="eventGbn" <c:if test="${info.eventGbn eq '해양관광전'}">checked</c:if> />
                                                            <label class="form-check-label" for="eventGbn3">
                                                                해양관광전(KMTS)
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
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
                                                <h3 class="fw-bold m-0">참관객 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_visitor_info">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">성명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="name" name="name" class="form-control form-control-lg form-control-solid-bg" value="${info.name}" placeholder="성명"/>
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
                                                        <input type="tel" id="tel" name="tel" class="form-control form-control-lg form-control-solid-bg onlyGeneralTel" value="${info.tel}" placeholder="전화번호" maxlength="14"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">
                                                        <span class="required">휴대전화</span>
                                                    </label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="tel" id="phone" name="phone" class="form-control form-control-lg form-control-solid-bg onlyTel" value="${info.phone}" placeholder="휴대전화"/>
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
                                                            <input type="text" id="email" name="email" value="${info.email}" class="form-control form-control-solid-bg" placeholder="이메일" aria-label="Username"/>
                                                            <span class="input-group-text">@</span>
                                                            <input type="text" id="domain" name="domain" value="${info.domain}" class="form-control form-control-solid-bg" placeholder="도메인" aria-label="Server"/>
                                                            <select id="email_select" class="form-select form-control-solid-bg ml10" aria-label="Select Email">
                                                                <option selected>직접입력</option>
                                                                <option value="daum.net" <c:if test="${info.domain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                                <option value="nate.com" <c:if test="${info.domain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                                <option value="hanmail.net" <c:if test="${info.domain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                                <option value="naver.com" <c:if test="${info.domain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                                <option value="hotmail.com" <c:if test="${info.domain eq 'hotmail.com'}">selected</c:if> >hotmail.com</option>
                                                                <option value="yahoo.co.kr" <c:if test="${info.domain eq 'yahoo.co.kr'}">selected</c:if> >yahoo.co.kr</option>
                                                                <option value="empal.com" <c:if test="${info.domain eq 'empal.com'}">selected</c:if> >empal.com</option>
                                                                <option value="korea.com" <c:if test="${info.domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                                <option value="hanmir.com" <c:if test="${info.domain eq 'hanmir.com'}">selected</c:if> >hanmir.com</option>
                                                                <option value="dreamwiz.com" <c:if test="${info.domain eq 'dreamwiz.com'}">selected</c:if> >dreamwiz.com</option>
                                                                <option value="orgio.net" <c:if test="${info.domain eq 'orgio.net'}">selected</c:if> >orgio.net</option>
                                                                <option value="korea.com" <c:if test="${info.domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                                <option value="hitel.net" <c:if test="${info.domain eq 'hitel.net'}">selected</c:if> >hitel.net</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 <c:if test="${info.partGbn eq '바이어'}">required</c:if>" id="companyNameDiv">직장명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyName" name="companyName" value="${info.companyName}" class="form-control form-control-lg form-control-solid-bg"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">직장주소</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="text" id="companyAddress" name="companyAddress" value="${info.companyAddress}" class="form-control form-control-lg form-control-solid-bg"/>
                                                    </div>
                                                    <!--end::Col-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-6">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">동반자 여부</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="Y" id="partnerY" name="partnerYn" <c:if test="${info.partnerYn eq 'Y'}">checked</c:if> />
                                                            <label class="form-check-label" for="partnerY">
                                                                있음
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="N" id="partnerN" name="partnerYn" <c:if test="${info.partnerYn eq 'N' or info.partnerYn eq null}">checked</c:if> />
                                                            <label class="form-check-label" for="partnerN">
                                                                없음
                                                            </label>
                                                        </div>
                                                        <div class="w-100 mt10">만 0 ~ 18세만 동반자로 입력 가능합니다.</div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->

                                                <!--begin::partner add group-->
                                                <div class="form_add_partner">

                                                    <c:if test="${empty partnerList}">
                                                        <!--begin::partner info box-->
                                                        <div class="partner_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    동반자 #<span class="partnerInfoNum">1</span>
                                                                    <span class="partnerInfoDel float-end" style="cursor: pointer;">삭제</span>
                                                                    <input type="hidden" name="partnerSeq" value="">
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">이름</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerName" class="form-control form-control-lg form-control-solid-bg" placeholder="이름" maxlength="10"/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">나이</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerAge" class="form-control form-control-lg form-control-solid-bg onlyChildAge" maxlength="2" placeholder="나이"/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                        </div>
                                                        <!--end::partner info box-->
                                                    </c:if>

                                                    <c:if test="${not empty partnerList}">
                                                    <c:forEach var="partner" items="${partnerList}" begin="0" end="${partnerList.size()}" step="1" varStatus="status">
                                                        <!--begin::partner info box-->
                                                        <div class="partner_info_box">
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <h4 class="p-3" style="background:#14214D; color: #FFFFFF;">
                                                                    동반자 #<span class="partnerInfoNum">${status.index + 1}</span>
                                                                    <span class="partnerInfoDel float-end" style="cursor: pointer;">삭제</span>
                                                                    <input type="hidden" name="partnerSeq" value="${partner.seq}">
                                                                </h4>
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">이름</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerName" value="${partner.partnerName}" class="form-control form-control-lg form-control-solid-bg" placeholder="이름" maxlength="10"/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                            <!--begin::Input group-->
                                                            <div class="row mb-6">
                                                                <!--begin::Label-->
                                                                <label class="col-lg-2 col-form-label fw-semibold fs-6 required">나이</label>
                                                                <!--end::Label-->
                                                                <!--begin::Col-->
                                                                <div class="col-lg-10">
                                                                    <input type="text" name="partnerAge" value="${partner.partnerAge}" class="form-control form-control-lg form-control-solid-bg onlyNum" maxlength="2" placeholder="나이"/>
                                                                </div>
                                                                <!--end::Col-->
                                                            </div>
                                                            <!--end::Input group-->
                                                        </div>
                                                        <!--end::partner info box-->
                                                    </c:forEach>
                                                    </c:if>

                                                    <!--begin::Actions-->
                                                    <div class="card-footer d-flex justify-content-end py-6 px-9">
                                                        <span class="btn btn-primary" id="kt_partner_info_add">추가</span>
                                                    </div>
                                                    <!--end::Actions-->
                                                </div>
                                                <!--begin::partner info group-->

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
                                                <h3 class="fw-bold m-0">설문항목</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--begin::Card header-->
                                        <!--begin::Content-->
                                        <div id="kt_field_part_info" class="collapse show">
                                            <!--begin::Card body-->
                                            <div class="card-body border-top p-9">

                                                <%-- 관리자용 플래그 및 저장 데이터 --%>
                                                <input type="hidden" id="isDetail" value="Y">
                                                <input type="hidden" id="h_observationGbn" value="${info.observationGbn}">
                                                <input type="hidden" id="h_visitPurpose" value="${info.visitPurpose}">
                                                <input type="hidden" id="h_interestItem" value="${info.interestItem}">
                                                <input type="hidden" id="h_recognizePath" value="${info.recognizePath}">
                                                <input type="hidden" id="h_preObservationGbn" value="${info.preObservationGbn}">

                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">성별</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="남자" id="sex_man" name="sex" <c:if test="${info.sex eq '남자'}">checked</c:if> />
                                                            <label class="form-check-label" for="sex_man">
                                                                남자
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="여자" id="sex_woman" name="sex" <c:if test="${info.sex eq '여자' or info.sex eq null}">checked</c:if>/>
                                                            <label class="form-check-label" for="sex_woman">
                                                                여자
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">지역</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="input-group">
                                                            <input type="text" id="sido" name="sido" value="${info.regionSi}" class="form-control form-control-lg form-control-solid-bg">
                                                            <input type="text" id="gugun" name="gugun" value="${info.regionGu}" class="form-control form-control-lg form-control-solid-bg">
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">Country</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="input-group">
                                                            <input type="text" id="country" name="country" value="${info.country}" class="form-control form-control-lg form-control-solid-bg">
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">연령대</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex align-items-center">
                                                        <div class="form-check form-check-custom">
                                                            <input class="form-check-input form-control-solid-bg" type="radio" value="10" id="age10" name="age" <c:if test="${info.ageGroup eq '10' or info.ageGroup eq null}">checked</c:if> />
                                                            <label class="form-check-label" for="age10">
                                                                10대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="20" id="age20" name="age" <c:if test="${info.ageGroup eq '20'}">checked</c:if> />
                                                            <label class="form-check-label" for="age20">
                                                                20대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="30" id="age30" name="age" <c:if test="${info.ageGroup eq '30'}">checked</c:if> />
                                                            <label class="form-check-label" for="age30">
                                                                30대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="40" id="age40" name="age" <c:if test="${info.ageGroup eq '40'}">checked</c:if> />
                                                            <label class="form-check-label" for="age40">
                                                                40대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="50" id="age50" name="age" <c:if test="${info.ageGroup eq '50'}">checked</c:if> />
                                                            <label class="form-check-label" for="age50">
                                                                50대
                                                            </label>
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="60" id="age60" name="age" <c:if test="${info.ageGroup eq '60'}">checked</c:if> />
                                                            <label class="form-check-label" for="age60">
                                                                60대 이상
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <div class="row mb-10" id="observationGbnDiv" <c:if test="${info.partGbn eq '바이어'}">style="display:none;"</c:if>>
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">관람 구분</label>
                                                    <div class="col-lg-10 d-flex flex-wrap" id="ul_observationGbn"></div>
                                                </div>
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required" id="visitPurposeTitle">방문 목적</label>
                                                    <div class="col-lg-10 d-flex flex-wrap" id="ul_visitPurpose"></div>
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">관심품목</label>
                                                    <div class="col-lg-10 d-flex flex-wrap" id="ul_interestItem"></div>
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">인지경로</label>
                                                    <div class="col-lg-10 d-flex flex-wrap" id="ul_recognizePath"></div>
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-10" id="preObservationGbnLi" style="display:none;">
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">지난 전시회 참관 여부</label>
                                                    <div class="col-lg-10 d-flex flex-wrap" id="ul_preObservationGbn"></div>
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
                                                <a href="/mng/exhibitor/participant/visitor.do" class="btn btn-info btn-active-light-info">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_participant_visitor_modify_init_set('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_participant_visitor_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
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
    <script src="/js/mngMain.js?ver=20260630"></script>
    <script src="/js/custom/participantVisitor.js?ver=20260630"></script>
    <script src="/js/custom/visitor.js?ver=20260127"></script>
    <script src="/js/sido.js?ver=20260226"></script>

    <c:if test="${info ne null}">
        <c:if test="${info.partnerYn eq 'Y'}">
            <script> $(document).ready(function() { document.querySelector('.form_add_partner').style.display = 'block'; }); </script>
        </c:if>
        <c:if test="${info.partnerYn eq 'N'}">
            <script> $(document).ready(function() { document.querySelector('.form_add_partner').style.display = 'none'; }); </script>
        </c:if>
    </c:if>

    <script>
        $(function(){
            // 1. 행사 구분 변경 이벤트
            $('input[name=eventGbn]').on('change', function(){
                if(typeof renderSurveyItems === 'function'){
                    renderSurveyItems($(this).val(), null);
                }
            });

            // 2. 초기 로딩 시: 행사 구분 기본값 설정
            let initialEvent = $('input[name=eventGbn]:checked').val();
            if (!initialEvent) {
                initialEvent = '경기국제보트쇼';
                $('input[name=eventGbn][value="경기국제보트쇼"]').prop('checked', true);
            }

            if(initialEvent && typeof renderSurveyItems === 'function') {
                let savedData = {
                    observationGbn: $('#h_observationGbn').val(),
                    visitPurpose: $('#h_visitPurpose').val(),
                    interestItem: $('#h_interestItem').val(),
                    recognizePath: $('#h_recognizePath').val(),
                    preObservationGbn: $('#h_preObservationGbn').val()
                };
                renderSurveyItems(initialEvent, savedData);
            }

            // 3. 참관 구분 이벤트 및 초기화
            $('input[type=radio][name=partGbn]').on('change', function(){
                let partGbn = $(this).val();
                if(partGbn === '바이어'){
                    $('#observationGbnDiv').hide();
                    $('#companyNameDiv').addClass('required');
                }else{
                    $('#observationGbnDiv').show();
                    $('#companyNameDiv').removeClass('required');
                }
            });

            // [요청사항 적용] 참관 구분 기본값 (바이어)
            let currentPartGbn = $('input[name=partGbn]:checked').val();
            if(!currentPartGbn) {
                $('input[name=partGbn][value="바이어"]').prop('checked', true).trigger('change');
            } else {
                $('input[name=partGbn][value="' + currentPartGbn + '"]').trigger('change');
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