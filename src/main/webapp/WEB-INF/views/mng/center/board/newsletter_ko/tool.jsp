<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%
    // 오늘 날짜 구하기 (예: 20240501) - 폴더명 기본값
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
    String today = sdf.format(new java.util.Date());
%>
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
                            <div id="kt_app_content_container" class="app-container container-xxl">

                                <div class="card mb-5 mb-xl-10">
                                    <div class="card-header border-0 pt-6">
                                        <div class="card-title">
                                            <div class="d-flex align-items-center position-relative my-1">
                                                <i class="ki-outline ki-message-text-2 fs-1 text-primary me-3"></i>
                                                <h3>뉴스레터 HTML 변환 도구</h3>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body py-4">

                                        <div class="alert alert-dismissible bg-light-primary border border-primary d-flex align-items-center p-5 mb-10">
                                            <i class="ki-outline ki-information-5 fs-2hx text-primary me-4"></i>

                                            <div class="d-flex flex-column">
                                                <h5 class="mb-1 text-primary">사용 가이드</h5>
                                                <span class="text-gray-700">
                                                    <span class="badge badge-light-primary badge-sm me-1">Step 1</span> 도메인 주소와 저장할 폴더명(날짜)을 확인합니다.<br>
                                                    <span class="badge badge-light-primary badge-sm me-1">Step 2</span> 뉴스레터에 사용된 <b>모든 이미지 파일</b>을 선택하여 업로드합니다.<br>
                                                    <span class="badge badge-light-primary badge-sm me-1">Step 3</span> <b>원본 HTML</b>을 붙여넣고 [변환 실행] 버튼을 클릭합니다.
                                                </span>
                                            </div>
                                        </div>

                                        <form id="convertForm">

                                            <div class="row">
                                                <div class="col-md-6 mb-10">
                                                    <label for="domainUrl" class="required form-label fw-bold">서비스 도메인 (URL)</label>
                                                    <input type="text" class="form-control form-control-solid" id="domainUrl" name="domainUrl"
                                                           placeholder="예: https://kibs.com" value="https://kibs.com" />
                                                    <div class="text-muted fs-7 mt-2">이미지 경로 앞단에 붙을 도메인 주소입니다.</div>
                                                </div>

                                                <div class="col-md-6 mb-10">
                                                    <label for="folderName" class="required form-label fw-bold">저장 폴더명 (날짜)</label>
                                                    <input type="text" class="form-control form-control-solid" id="folderName" name="folderName"
                                                           placeholder="예: 20240501" value="<%=today%>" />
                                                    <div class="text-muted fs-7 mt-2">서버 저장 경로: <code>/upload/newsletter/{폴더명}/</code></div>
                                                </div>
                                            </div>

                                            <div class="mb-10">
                                                <label for="imageFiles" class="required form-label fw-bold">이미지 파일 선택</label>
                                                <input type="file" class="form-control form-control-solid" id="imageFiles" name="imageFiles" multiple accept="image/*" />
                                                <div class="text-muted fs-7 mt-2"><code>img</code> 폴더 내의 모든 이미지를 드래그하여 한 번에 선택하세요.</div>
                                            </div>

                                            <div class="mb-10">
                                                <label for="rawHtml" class="required form-label fw-bold">원본 HTML 소스</label>
                                                <textarea class="form-control form-control-solid" id="rawHtml" name="htmlContent" rows="10"
                                                          placeholder='<html>...<img src="images/main.jpg">...</html>' style="font-family: monospace; font-size: 13px;"></textarea>
                                            </div>

                                            <div class="d-flex justify-content-between">
                                                <a href="/mng/center/board/newsletter.do" class="btn btn-info btn-active-light-info" id="kt_list_btn">목록</a>

                                                <button type="button" class="btn btn-primary" id="btnConvert" onclick="convertNewsletter()">
                                                    <span class="indicator-label d-flex align-items-center">
                                                        <i class="ki-outline ki-cloud-change fs-2 me-2"></i>
                                                        변환 및 업로드 실행
                                                    </span>
                                                    <span class="indicator-progress">
                                                        처리중... <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                                                    </span>
                                                </button>
                                            </div>
                                        </form>

                                        <div id="resultArea" class="mt-15 d-none">
                                            <div class="separator separator-dashed mb-5"></div>

                                            <div class="d-flex justify-content-between align-items-center mb-5">
                                                <div class="d-flex align-items-center">
                                                    <i class="ki-outline ki-check-circle fs-2 text-success me-2"></i>
                                                    <label class="form-label fw-bold text-dark fs-4 mb-0">변환 결과</label>
                                                </div>
                                                <button type="button" class="btn btn-sm btn-light-primary" onclick="copyToClipboard()">
                                                    <i class="ki-outline ki-copy fs-3 me-1"></i>
                                                    코드 복사
                                                </button>
                                            </div>

                                            <textarea class="form-control form-control-solid border-success" id="resultHtml" rows="15" readonly
                                                      style="font-family: monospace; font-size: 13px; background-color: #f9fff9;"></textarea>

                                            <div class="mt-3 text-end">
                                                <span class="text-muted fs-7">* 변환된 코드를 복사하여 뉴스레터 HTML에 사용하세요.</span>
                                            </div>
                                        </div>

                                    </div>
                                </div>
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
    <script src="/js/mngMain.js?ver=20260630"></script>
    <!--end::Custom Javascript-->

    <!--end::Javascript-->
    <script type="text/javascript">
        /**
         * 뉴스레터 변환 및 업로드 실행 함수
         */
        function convertNewsletter() {
            // 1. 유효성 검사
            let domainUrl = $('#domainUrl').val();
            let folderName = $('#folderName').val();
            let imageFiles = $('#imageFiles')[0].files;
            let rawHtml = $('#rawHtml').val();

            if (nvl(domainUrl, '') === '') {
                Swal.fire('경고', '도메인 주소를 입력해주세요.', 'warning');
                return;
            }
            if (nvl(folderName, '') === '') {
                Swal.fire('경고', '폴더명을 입력해주세요.', 'warning');
                return;
            }
            if (imageFiles.length === 0) {
                Swal.fire('경고', '업로드할 이미지를 선택해주세요.', 'warning');
                return;
            }
            if (nvl(rawHtml, '') === '') {
                Swal.fire('경고', '원본 HTML 소스를 입력해주세요.', 'warning');
                return;
            }

            // 2. FormData 생성
            let formData = new FormData();
            formData.append('domainUrl', domainUrl);
            formData.append('folderName', folderName);
            formData.append('htmlContent', rawHtml);

            // 파일 다중 추가
            for (let i = 0; i < imageFiles.length; i++) {
                formData.append('file_' + i, imageFiles[i]); // Key값은 Controller에서 Iterator로 받으므로 크게 중요치 않음
            }

            // 3. 버튼 로딩 상태 변경
            let btn = $('#btnConvert');
            let label = btn.find('.indicator-label');
            let progress = btn.find('.indicator-progress');

            btn.prop('disabled', true);
            label.hide();
            progress.show();

            // 4. AJAX 전송
            $.ajax({
                url: '/mng/newsletter/convert.do',
                method: 'POST',
                data: formData,
                processData: false, // 파일 전송 시 필수
                contentType: false, // 파일 전송 시 필수
                success: function(res) {
                    if (res.resultCode === "0") {
                        // 성공 시 결과 표시
                        $('#resultArea').removeClass('d-none');
                        $('#resultHtml').val(res.processedHtml);

                        Swal.fire({
                            icon: 'success',
                            title: '작업 완료',
                            text: '이미지 업로드 및 HTML 변환이 성공적으로 처리되었습니다.',
                            confirmButtonColor: '#00a8ff'
                        });

                        // 결과 영역으로 스크롤 이동
                        $('html, body').animate({
                            scrollTop: $("#resultArea").offset().top - 100
                        }, 500);

                    } else {
                        Swal.fire('오류', '작업 실패: ' + res.resultMsg, 'error');
                    }
                },
                error: function(xhr, status, error) {
                    console.error(error);
                    Swal.fire('서버 오류', '서버와 통신 중 오류가 발생했습니다.', 'error');
                },
                complete: function() {
                    // 버튼 상태 복구
                    btn.prop('disabled', false);
                    label.show();
                    progress.hide();
                }
            });
        }

        /**
         * 결과 HTML 클립보드 복사
         */
        function copyToClipboard() {
            let content = document.getElementById('resultHtml');
            content.select();
            document.execCommand('copy');

            // 토스트 메시지
            if(typeof Swal !== 'undefined') {
                Swal.fire({
                    toast: true,
                    position: 'top-end',
                    icon: 'success',
                    title: '코드가 복사되었습니다.',
                    showConfirmButton: false,
                    timer: 1500
                });
            } else {
                alert('코드가 복사되었습니다.');
            }
        }

        // nvl 함수 (기존 main.js에 있다면 생략 가능하지만 안전을 위해 추가)
        function nvl(str, defaultVal) {
            if (str === undefined || str === null || str === '') {
                return defaultVal;
            }
            return str;
        }
    </script>
    <!--end::login check-->
</c:if>
</body>
<!--end::Body-->
</html>