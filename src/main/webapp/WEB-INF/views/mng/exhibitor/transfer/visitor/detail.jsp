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
                                        지난행사 참관객</h1>
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
                                        <li class="breadcrumb-item text-muted">지난행사 참관객</li>
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">전화번호</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <input type="tel" id="tel" name="tel" class="form-control form-control-lg form-control-solid-bg onlyGeneralTel" value="${info.tel}" placeholder="전화번호" maxlength="13"/>
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
                                                        <input type="tel" id="phone" name="phone" class="form-control form-control-lg form-control-solid-bg onlyTel" value="${info.phone}" placeholder="휴대전화" maxlength="13"/>
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
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6">직장명</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10">
                                                        <!--begin::Row-->
                                                        <div class="row">
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <input type="text" id="companyName" name="companyName" value="${info.companyName}" class="form-control form-control-lg form-control-solid-bg" />
                                                            </div>
                                                            <!--end::Col-->
                                                            <!--begin::Col-->
                                                            <div class="col-lg-6">
                                                                <button type="button" class="btn btn-primary" id="companySearch" onclick="">검색</button>
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
                                                        <div class="w-100 mt10">개별 신청이 어려운 어린이, 노약자일 경우에 선택해 주세요.</div>
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
                                                                    <input type="text" name="partnerName" class="form-control form-control-lg form-control-solid-bg" placeholder="이름" maxlength="10" />
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
                                                                    <input type="text" name="partnerAge" class="form-control form-control-lg form-control-solid-bg onlyNum" maxlength="2" placeholder="나이" />
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
                                                                    <input type="text" name="partnerName" value="${partner.partnerName}" class="form-control form-control-lg form-control-solid-bg" placeholder="이름" maxlength="10" />
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
                                                                    <input type="text" name="partnerAge" value="${partner.partnerAge}" class="form-control form-control-lg form-control-solid-bg onlyNum" maxlength="2" placeholder="나이" />
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
                                                            <input class="form-check-input form-control-solid-bg ml20" type="radio" value="여자" id="sex_woman" name="sex" <c:if test="${info.sex eq '여자' or info.sex eq null}">checked</c:if> />
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
                                                            <select id="sido" name="sido" class="form-select form-control-solid-bg" aria-label="Select sido"></select>
                                                            <select id="gugun" name="gugun" class="form-select form-control-solid-bg ml10" aria-label="Select gugun"></select>
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
                                                <!--begin::Input group-->
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">참관 구분</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn1" name="observationGbn" value="조종면허 보유자"
                                                                    <c:if test="${fn:contains(info.observationGbn, '조종면허 보유자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn1">
                                                                조종면허 보유자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn2" name="observationGbn" value="보트 소유자"
                                                                   <c:if test="${fn:contains(info.observationGbn, '보트 소유자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn2">
                                                                보트 소유자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn3" name="observationGbn" value="보트 구매 예정자"
                                                                   <c:if test="${fn:contains(info.observationGbn, '보트 구매 예정자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn3">
                                                                보트 구매 예정자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn4" name="observationGbn" value="관련 업종 종사자"
                                                                   <c:if test="${fn:contains(info.observationGbn, '관련 업종 종사자')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn4">
                                                                관련 업종 종사자
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn5" name="observationGbn" value="낚시 매니아"
                                                                   <c:if test="${fn:contains(info.observationGbn, '낚시 매니아')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn5">
                                                                낚시 매니아
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn6" name="observationGbn" value="다이빙 매니아"
                                                                   <c:if test="${fn:contains(info.observationGbn, '다이빙 매니아')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn6">
                                                                다이빙 매니아
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn7" name="observationGbn" value="캠핑카 매니아"
                                                                   <c:if test="${fn:contains(info.observationGbn, '캠핑카 매니아')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn7">
                                                                캠핑카 매니아
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn8" name="observationGbn" value="학생"
                                                                   <c:if test="${fn:contains(info.observationGbn, '학생')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn8">
                                                                학생
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn9" name="observationGbn" value="일반관람"
                                                                   <c:if test="${fn:contains(info.observationGbn, '일반관람')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn9">
                                                                일반관람
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="observation_gbn10" name="observationGbn" value="기타"
                                                                   <c:if test="${fn:contains(info.observationGbn, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="observation_gbn10">
                                                                기타
                                                            </label>
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
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">보트쇼 방문 목적</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose1" name="visitPurpose" value="업계동향 파악 및 정보수집"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '업계동향 파악 및 정보수집')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose1">
                                                                업계동향 파악 및 정보수집
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose2" name="visitPurpose" value="제품구매 및 기술도입 상담"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '제품구매 및 기술도입 상담')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose2">
                                                                제품구매 및 기술도입 상담
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose3" name="visitPurpose" value="기존 거래업체 방문"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '기존 거래업체 방문')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose3">
                                                                기존 거래업체 방문
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose4" name="visitPurpose" value="차기 전시회 참가여부 파악"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '차기 전시회 참가여부 파악')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose4">
                                                                차기 전시회 참가여부 파악
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose5" name="visitPurpose" value="일반관람"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '일반관람')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose5">
                                                                일반관람
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="visit_purpose6" name="visitPurpose" value="기타"
                                                                   <c:if test="${fn:contains(info.visitPurpose, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="visit_purpose6">
                                                                기타
                                                            </label>
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
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">관심품목</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item1" name="interestItem" value="요트/보트"
                                                                   <c:if test="${fn:contains(info.interestItem, '요트/보트')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item1">
                                                                요트/보트
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item2" name="interestItem" value="워크보트"
                                                                   <c:if test="${fn:contains(info.interestItem, '워크보트')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item2">
                                                                워크보트
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item3" name="interestItem" value="낚시"
                                                                   <c:if test="${fn:contains(info.interestItem, '낚시')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item3">
                                                                낚시
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item4" name="interestItem" value="다이빙"
                                                                   <c:if test="${fn:contains(info.interestItem, '다이빙')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item4">
                                                                다이빙
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item5" name="interestItem" value="카누/카약/무동력"
                                                                   <c:if test="${fn:contains(info.interestItem, '카누/카약/무동력')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item5">
                                                                카누/카약/무동력
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item6" name="interestItem" value="워터스포츠"
                                                                   <c:if test="${fn:contains(info.interestItem, '워터스포츠')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item6">
                                                                워터스포츠
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item7" name="interestItem" value="해양부품"
                                                                   <c:if test="${fn:contains(info.interestItem, '해양부품')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item7">
                                                                해양부품
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item8" name="interestItem" value="캠핑"
                                                                   <c:if test="${fn:contains(info.interestItem, '캠핑')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item8">
                                                                캠핑
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item9" name="interestItem" value="친환경"
                                                                   <c:if test="${fn:contains(info.interestItem, '친환경')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item9">
                                                                친환경
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item10" name="interestItem" value="해양관광"
                                                                   <c:if test="${fn:contains(info.interestItem, '해양관광')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item10">
                                                                해양관광
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="interest_item11" name="interestItem" value="기타"
                                                                   <c:if test="${fn:contains(info.interestItem, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="interest_item11">
                                                                기타
                                                            </label>
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
                                                <div class="row mb-10">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">인지경로</label>
                                                    <!--end::Label-->
                                                    <!--begin::Col-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path1" name="recognizePath" value="뉴스레터"
                                                                   <c:if test="${fn:contains(info.recognizePath, '뉴스레터')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path1">
                                                                뉴스레터
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path2" name="recognizePath" value="옥외광고물"
                                                                   <c:if test="${fn:contains(info.recognizePath, '옥외광고물')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path2">
                                                                옥외광고물
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path3" name="recognizePath" value="홈페이지"
                                                                   <c:if test="${fn:contains(info.recognizePath, '홈페이지')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path3">
                                                                홈페이지
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path4" name="recognizePath" value="전문지"
                                                                   <c:if test="${fn:contains(info.recognizePath, '전문지')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path4">
                                                                전문지
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path5" name="recognizePath" value="온라인 커뮤니티"
                                                                   <c:if test="${fn:contains(info.recognizePath, '온라인 커뮤니티')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path5">
                                                                온라인 커뮤니티
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path6" name="recognizePath" value="오프라인매장"
                                                                   <c:if test="${fn:contains(info.recognizePath, '오프라인매장')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path6">
                                                                오프라인매장
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path7" name="recognizePath" value="소셜 네트워크"
                                                                   <c:if test="${fn:contains(info.recognizePath, '소셜 네트워크')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path7">
                                                                소셜 네트워크
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path8" name="recognizePath" value="초청장"
                                                                   <c:if test="${fn:contains(info.recognizePath, '초청장')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path8">
                                                                초청장
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path9" name="recognizePath" value="방송광고"
                                                                   <c:if test="${fn:contains(info.recognizePath, '방송광고')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path9">
                                                                방송광고
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path10" name="recognizePath" value="KIBS 참석"
                                                                   <c:if test="${fn:contains(info.recognizePath, 'KIBS 참석')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path10">
                                                                KIBS 참석
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path11" name="recognizePath" value="낚시박람회"
                                                                   <c:if test="${fn:contains(info.recognizePath, '낚시박람회')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path11">
                                                                낚시박람회
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" id="recognize_path12" name="recognizePath" value="기타"
                                                                   <c:if test="${fn:contains(info.recognizePath, '기타')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="recognize_path12">
                                                                기타
                                                            </label>
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
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">지난 전시회 참관 여부</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="first" id="pre_exhibit_yn_ko_first" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, 'first')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_first">
                                                                첫 참관
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2008" id="pre_exhibit_yn_ko_08" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2008')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_08">
                                                                2008
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2009" id="pre_exhibit_yn_ko_09" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2009')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_09">
                                                                2009
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2010" id="pre_exhibit_yn_ko_10" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2010')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_10">
                                                                2010
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2011" id="pre_exhibit_yn_ko_11" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2011')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_11">
                                                                2011
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2012" id="pre_exhibit_yn_ko_12" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2012')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_12">
                                                                2012
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2013" id="pre_exhibit_yn_ko_13" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2013')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_13">
                                                                2013
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2014" id="pre_exhibit_yn_ko_14" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2014')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_14">
                                                                2014
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2015" id="pre_exhibit_yn_ko_15" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2015')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_15">
                                                                2015
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2016" id="pre_exhibit_yn_ko_16" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2016')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_16">
                                                                2016
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2008" id="pre_exhibit_yn_ko_17" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2017')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_17">
                                                                2017
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2018" id="pre_exhibit_yn_ko_18" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2018')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_18">
                                                                2018
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2019" id="pre_exhibit_yn_ko_19" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2019')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_19">
                                                                2019
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2020" id="pre_exhibit_yn_ko_20" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2020')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_20">
                                                                2020
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2021" id="pre_exhibit_yn_ko_21" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2021')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_21">
                                                                2021
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2022" id="pre_exhibit_yn_ko_22" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2022')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_22">
                                                                2022
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2023" id="pre_exhibit_yn_ko_23" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2023')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_23">
                                                                2023
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2024" id="pre_exhibit_yn_ko_24" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2024')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_24">
                                                                2024
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="2025" id="pre_exhibit_yn_ko_25" name="preObservationGbn"
                                                                   <c:if test="${fn:contains(info.preObservationGbn, '2025')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_ko_25">
                                                                2025
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <!--begin::Label-->
                                                </div>
                                                <!--end::Input group-->
                                                <!--begin::Input group-->
                                                <div class="row mb-8">
                                                    <!--begin::Label-->
                                                    <label class="col-lg-2 col-form-label fw-semibold fs-6 required">Whether you attended the last exhibition</label>
                                                    <!--begin::Label-->
                                                    <!--begin::Label-->
                                                    <div class="col-lg-10 d-flex flex-wrap">
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg" type="checkbox" value="first" id="pre_exhibit_yn_en_1" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, 'first')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_1">
                                                                One's first visit
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2008" id="pre_exhibit_yn_en_08" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2008')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_08">
                                                                2008
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2009" id="pre_exhibit_yn_en_09" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2009')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_09">
                                                                2009
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2010" id="pre_exhibit_yn_en_10" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2010')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_10">
                                                                2010
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2011" id="pre_exhibit_yn_en_11" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2011')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_11">
                                                                2011
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2012" id="pre_exhibit_yn_en_12" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2012')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_12">
                                                                2012
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2013" id="pre_exhibit_yn_en_13" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2013')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_13">
                                                                2013
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2014" id="pre_exhibit_yn_en_14" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2014')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_14">
                                                                2014
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2015" id="pre_exhibit_yn_en_15" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2015')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_15">
                                                                2015
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2016" id="pre_exhibit_yn_en_16" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2016')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_16">
                                                                2016
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2008" id="pre_exhibit_yn_en_17" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2017')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_17">
                                                                2017
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2018" id="pre_exhibit_yn_en_18" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2018')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_18">
                                                                2018
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2019" id="pre_exhibit_yn_en_19" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2019')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_19">
                                                                2019
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2020" id="pre_exhibit_yn_en_20" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2020')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_20">
                                                                2020
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2021" id="pre_exhibit_yn_en_21" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2021')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_21">
                                                                2021
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2022" id="pre_exhibit_yn_en_22" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2022')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_22">
                                                                2022
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2023" id="pre_exhibit_yn_en_23" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2023')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_23">
                                                                2023
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2024" id="pre_exhibit_yn_en_24" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2024')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_24">
                                                                2024
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                            <input class="form-check-input form-control-solid-bg ml20" type="checkbox" value="2025" id="pre_exhibit_yn_en_25" name="preObservationGbnEn"
                                                                   <c:if test="${fn:contains(info.preObservationGbnEn, '2025')}">checked</c:if> />
                                                            <label class="form-check-label text-hover-primary" for="pre_exhibit_yn_en_25">
                                                                2025
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
                                        <!--begin::Actions-->
                                        <div class="card-footer d-flex justify-content-between py-6 px-9">
                                            <div>
                                                <a href="/mng/exhibitor/transfer/visitor.do" class="btn btn-info btn-active-light-info">목록</a>
                                            </div>
                                            <div>
                                                <button type="button" onclick="f_transfer_visitor_detail('${info.seq}')" class="btn btn-danger btn-active-light-danger me-2">변경내용취소</button>
                                                <button type="button" onclick="f_transfer_visitor_save('${info.seq}')" class="btn btn-primary btn-active-light-primary">변경내용저장</button>
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
    <script src="/js/custom/transferVisitor.js?ver=20260630"></script>
    <script src="/js/sido.js?ver=20260226"></script>

    <c:if test="${info ne null}">
        <c:if test="${info.partnerYn eq 'Y'}">
            <script> $(function(){ $('.form_add_partner').show(); }); </script>
        </c:if>
        <c:if test="${info.partnerYn eq 'N'}">
            <script> $(function(){ $('.form_add_partner').hide(); }); </script>
        </c:if>

        <c:if test="${info.regionSi ne null and info.regionSi ne ''}">
            <script> $(function(){ $('#sido').val('${info.regionSi}'); $('#sido').change(); });</script>
        </c:if>
        <c:if test="${info.regionGu ne null and info.regionGu ne ''}">
            <script> $(function(){ $('#gugun').val('${info.regionGu}') });</script>
        </c:if>
    </c:if>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>