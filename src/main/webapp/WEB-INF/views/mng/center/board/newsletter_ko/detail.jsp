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

    <style>
        /* 미리보기 영역(#html_preview) 안의 모든 p 태그에 대해 마진 강제 제거 */
        #html_preview p {
            margin: 0 !important;
            padding: 0 !important;
        }

        /* 혹시 모를 div 여백 문제도 방지 */
        #html_preview div {
            margin: 0 !important;
            padding: 0 !important;
        }
    </style>

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
                                        뉴스레터</h1>
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
                                        <li class="breadcrumb-item text-muted">뉴스레터</li>
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
                                <!--begin::Basic info-->
                                <div class="card mb-5 mb-xl-10">
                                    <!--begin::form-->
                                    <form id="newsletterForm" method="post" onsubmit="return false;">
                                        <%--뉴스레터 ID 값--%>
                                        <input type="hidden" name="id" value="${info.id}">
                                        <input type="hidden" name="lang" value="KO">
                                        <!--begin::Card header-->
                                        <div class="card-header border-0">
                                            <!--begin::Card title-->
                                            <div class="card-title m-0">
                                                <h3 class="fw-bold m-0">상세 정보</h3>
                                            </div>
                                            <!--end::Card title-->
                                        </div>
                                        <!--end::Card header-->
                                        <!--begin::Card body-->
                                        <div class="card-body border-top p-9">
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">작성상태</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <c:set var="tempSaveStatus" value="작성중"/>
                                                    <c:if test="${info ne null}">
                                                        <c:choose>
                                                            <c:when test="${info.tempSaveYn eq 'Y'}">
                                                                <c:set var="tempSaveStatus" value="임시저장중 ( 페이지 미노출 상태 )"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="tempSaveStatus" value="저장완료 ( 페이지 노출 상태 )"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                    <input type="text" class="form-control form-control-lg form-control-solid-bg" placeholder="작성상태" value="${tempSaveStatus}" disabled/>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">제목</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <input type="text" id="title" name="title" class="form-control form-control-lg form-control-solid-bg" placeholder="제목" value="${info.title}"/>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">작성자</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <input type="text" id="writer" name="writer" class="form-control form-control-lg form-control-solid-bg" placeholder="작성자" value="${info.writer eq null ? '관리자' : info.writer}"/>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">작성일</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <c:set var="now" value="<%=new java.util.Date()%>"/>
                                                    <fmt:formatDate var="fmNow" value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                                    <c:set var="sysdate" value="${info.writeDate eq null or info.writeDate eq '' ? fmNow : info.writeDate}"/>
                                                    <input class="form-control form-control-solid" id="writeDate" name="writeDate" placeholder="작성일" value="${sysdate}"/>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">사이트 분류</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-8 d-flex flex-wrap">
                                                    <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                        <input class="form-check-input form-control-solid-bg"
                                                               type="checkbox" id="gbn1" name="gbn1"
                                                               <c:if test="${info.gbn1 eq '1'}">checked</c:if>/>
                                                        <label class="form-check-label text-hover-primary" for="gbn1">
                                                            홈페이지
                                                        </label>
                                                    </div>
                                                    <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                        <input class="form-check-input form-control-solid-bg"
                                                               type="checkbox" id="gbn2" name="gbn2"
                                                               <c:if test="${info.gbn2 eq '1'}">checked</c:if>/>
                                                        <label class="form-check-label text-hover-primary" for="gbn2">
                                                            무역관
                                                        </label>
                                                    </div>
                                                    <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                        <input class="form-check-input form-control-solid-bg"
                                                               type="checkbox" id="gbn3" name="gbn3"
                                                               <c:if test="${info.gbn3 eq '1'}">checked</c:if>/>
                                                        <label class="form-check-label text-hover-primary" for="gbn3">
                                                            체류
                                                        </label>
                                                    </div>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">추가 설정</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-8 d-flex flex-wrap">
                                                    <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                                                        <input class="form-check-input form-control-solid-bg"
                                                               type="checkbox" id="noticeGbn" name="noticeGbn"
                                                               <c:if test="${info.noticeGbn eq '1'}">checked</c:if>/>
                                                        <label class="form-check-label text-hover-primary"
                                                               for="noticeGbn">
                                                            공지사항 고정
                                                        </label>
                                                    </div>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6<%-- h-550px--%>">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">내용</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <div id="code_editor" style="height: 500px; min-height: 500px; resize: vertical; overflow-y: auto;">${info.content}</div>
                                                    <input type="hidden" id="html_content" name="content" value="<c:out value="${info.content}" escapeXml="true"/>">
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <div class="row mb-6">
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">미리보기</label>
                                                <div class="col-lg-10">
                                                    <div class="rounded border p-5" style="background-color: #f5f8fa; height: 600px; overflow-y: auto;">
                                                        <div id="html_preview" class="bg-white shadow-sm mx-auto" style="max-width: 800px; min-height: 500px; padding: 0;">
                                                                ${info.content}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">첨부파일</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <!--begin::Excel import-->
                                                    <a href="" class="btn btn-primary btn-active-light-primary ms-auto" data-bs-toggle="modal" data-bs-target="#kt_modal_file_upload">
                                                        <i class="ki-duotone ki-exit-up fs-2">
                                                            <span class="path1"></span>
                                                            <span class="path2"></span>
                                                        </i>첨부파일 업로드</a>
                                                    <!--end::Excel import-->
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::Input group-->
                                            <!--begin::Input group-->
                                            <div class="row mb-6">
                                                <!--begin::Label-->
                                                <label class="col-lg-2 col-form-label fw-semibold fs-6">첨부파일 목록</label>
                                                <!--end::Label-->
                                                <!--begin::Col-->
                                                <div class="col-lg-10">
                                                    <ul id="uploadFileList">
                                                        <c:forEach var="file" items="${fileList}">
                                                            <li class="mb-4">
                                                                <%--<a href="/file/download.do?path=center/board/${file.folderPath}&fileName=${file.fullFileName}">${file.fileName}</a>--%>
                                                                <a href="javascript:void(0);" onclick="f_file_download('center/board/${file.folderPath}', '${file.fileName}')">${file.fileName}</a>
                                                                <input type="hidden" name="uploadFile" id="${file.id}" value="${file.fullFilePath}">
                                                                <button type="button" class="ml10" onclick="f_file_remove(this, '${file.id}')">
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
                                        </div>
                                        <!--end::Card body-->
                                    </form>
                                    <!--end::form-->
                                </div>
                                <!--end::Basic info-->
                                <!--begin::Basic info-->
                                <div class="card mb-5 mb-xl-10">
                                    <!--begin::Actions-->
                                    <div class="card-footer d-flex justify-content-between py-6 px-9">
                                        <div>
                                            <a href="/mng/center/board/newsletter.do" class="btn btn-info btn-active-light-info" id="kt_list_btn">목록</a>
                                        </div>
                                        <div>
                                            <button type="button" onclick="f_board_newsletter_ko_modify_init_set('${info.id}')" class="btn btn-danger btn-active-light-danger">변경내용취소</button>
                                            <button type="button" onclick="f_board_newsletter_ko_temp_save('${info.id}')" class="btn btn-secondary btn-active-light-secondary ms-2 me-2">임시저장</button>
                                            <button type="button" onclick="f_board_newsletter_ko_save('${info.id}')" class="btn btn-primary btn-active-light-primary" id="kt_save_submit">변경내용저장</button>
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

    <!--begin::Modal - 수정이력-->
    <div class="modal fade" id="kt_modal_file_upload" tabindex="-1" aria-hidden="true">
        <!--begin::Modal dialog-->
        <div class="modal-dialog modal-dialog-centered mw-1000px">
            <!--begin::Modal content-->
            <div class="modal-content">
                <!--begin::Modal header-->
                <div class="modal-header" style="background-color: #1e1e2d;">
                    <!--begin::Modal title-->
                    <h2 style="color: #FFFFFF;">첨부파일 업로드</h2>
                    <!--end::Modal title-->
                    <!--begin::Close-->
                    <div class="btn btn-sm btn-icon btn-active-color-primary" onclick="modalClose('attachFile')" data-bs-dismiss="modal">
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
                    <form id="modal_file_upload_form" method="post" onsubmit="return false;">
                        <!--begin::Input group-->
                        <div class="row mb-6">
                            <!--begin::Label-->
                            <label class="col-lg-2 col-form-label required fw-semibold fs-6">File</label>
                            <!--end::Label-->
                            <!--begin::Col-->
                            <div class="col-lg-10">
                                <!--begin::Row-->
                                <div class="row">
                                    <!--begin::Col-->
                                    <div class="col-lg-8 d-inline-block">
                                        <input type="text" name="attachFile" class="form-control form-control-lg form-control-solid-bg upload_name" placeholder="파일명.확장자" disabled/>
                                    </div>
                                    <!--end::Col-->
                                    <!--begin::Col-->
                                    <div class="col-lg-3 d-inline-block ms-3">
                                        <input type="file" id="attachFileInput" name="file" class="d-none upload_hidden">
                                        <label class="btn btn-primary" for="attachFileInput">파일선택</label>
                                    </div>
                                    <!--end::Col-->
                                </div>
                                <!--end::Row-->
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
                            <a onclick="modalClose('attachFile')" class="btn btn-danger" data-bs-dismiss="modal">취소</a>
                            <!--end::Cancel-->
                        </div>
                        <!--end::Col-->
                        <!--begin::Col-->
                        <div class="ms-10">
                            <!--begin::File upload-->
                            <a onclick="f_attach_file_upload('${info.id}','modal_file_upload_form','center/board/newsletter')" class="btn btn-primary">업로드</a>
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
    <script src="/js/mngMain.js?ver=20260630"></script>
    <script src="/js/custom/newsletterKo.js?ver=20260630"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.12/ace.js"></script>
    <script src="/js/html-code-editor.js?ver=20260630"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->

    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>