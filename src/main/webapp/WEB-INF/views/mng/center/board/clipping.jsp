<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
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

    <div class="d-flex flex-column flex-root app-root" id="kt_app_root">
        <div class="app-page flex-column flex-column-fluid" id="kt_app_page">
            <jsp:include page="/WEB-INF/views/mng/layout/header.jsp" flush="true" />
            <div class="app-wrapper flex-column flex-row-fluid" id="kt_app_wrapper">
                <jsp:include page="/WEB-INF/views/mng/layout/sidebar.jsp" flush="true" />
                <div class="app-main flex-column flex-row-fluid" id="kt_app_main">
                    <div class="d-flex flex-column flex-column-fluid">

                        <div id="kt_app_toolbar" class="app-toolbar py-3 py-lg-6">
                            <div class="app-container container-fluid d-flex flex-stack">
                                <div class="page-title d-flex flex-column justify-content-center flex-wrap me-3">
                                    <h1 class="page-heading d-flex text-dark fw-bold fs-3 flex-column justify-content-center my-0">AI 뉴스 클리핑 관리</h1>
                                </div>
                            </div>
                        </div>

                        <div id="kt_app_content" class="app-content flex-column-fluid">
                            <div class="app-container container-fluid">

                                <!-- AI 클리핑 운영 가이드 안내창 -->
                                <div class="notice d-flex bg-light-primary rounded border-primary border border-dashed p-6 mb-7">
                                    <i class="ki-duotone ki-information-5 fs-2tx text-primary me-4">
                                        <span class="path1"></span><span class="path2"></span><span class="path3"></span>
                                    </i>
                                    <div class="d-flex flex-stack flex-grow-1">
                                        <div class="fw-semibold">
                                            <h4 class="text-gray-900 fw-bold">AI 뉴스 클리핑 자동화 운영 가이드</h4>
                                            <div class="fs-6 text-gray-700 mt-2" style="line-height: 1.6;">
                                                <ul class="mb-0 px-5" style="max-width: 1200px;">
                                                    <c:choose>
                                                        <c:when test="${clippingApprovalRequired}">
                                                <li class="mb-4">
                                                    <strong>자동 생성 및 발송 사이클 (승인 필요)</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1">매주 <b>월요일 오전 8시</b>에 지난 한 주(지난 월요일 8시 이후) 기사를 수집해 새 클리핑이 <b>'임시저장(미노출)'</b> 상태로 자동 생성됩니다.</li>
                                                        <li class="mb-1">관리자가 내용을 검수하고 <b>[발행 승인]</b>을 누르면 같은 날 <b>오전 10시</b>에 홈페이지 게시(노출)와 뉴스레터 발송이 자동으로 진행됩니다.</li>
                                                        <li class="mb-1">10시까지 승인하지 않은 글은 발송되지 않습니다.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>발행 승인 vs 즉시 게시</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1"><b>[발행 승인]</b>은 "10시 자동 발행 대상"으로 표시만 하는 예약 승인입니다. 승인 후에도 10시 전까지는 노출되지 않으며 <b>[승인 취소]</b>로 되돌릴 수 있습니다.</li>
                                                        <li class="mb-1"><b>[즉시 게시]</b>는 누르는 즉시 홈페이지에 노출(게시중)됩니다. 메일은 발송하지 않습니다.</li>
                                                        <li class="mb-1">메일까지 바로 보내려면 기사 상세창의 <b>[이 기사로 뉴스레터 발송]</b>을 이용해 주세요.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>발행 후 문제 발견 시</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1">10시 발행 뒤 내용에 문제가 있으면 게시중 글의 <b>[미노출 변경]</b>을 눌러 주세요.</li>
                                                        <li class="mb-1">홈페이지 목록·메인·상세 페이지(이메일에 실린 링크 포함)에서 즉시 사라집니다.</li>
                                                        <li class="mb-1">이미 발송된 이메일은 회수되지 않습니다. 자동 발송은 월요일 10시 한 번뿐이라 미노출 처리 후 다시 발송되지는 않습니다.</li>
                                                        <li class="mb-1"><b>[수정/상세]</b>에서 내용을 고친 뒤 <b>[즉시 게시]</b>로 다시 노출할 수 있고, 수정본 재발송은 상세창의 <b>[이 기사로 뉴스레터 발송]</b>을 이용합니다.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>수동 생성 및 비상 발송</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1"><b>[AI 클리핑 수동 생성]</b> → <b>'생성만 하기'</b>는 메일 발송 없이 글만 생성(임시저장·미승인)합니다.</li>
                                                        <li class="mb-1">자동 발행 대상은 <b>당일 생성 + 승인</b>된 글입니다. 다른 요일에 수동 생성한 글은 [즉시 게시]와 [이 기사로 뉴스레터 발송]으로 직접 발행해 주세요.</li>
                                                        <li class="mb-1">긴급 발송은 <b>'생성 및 발송'</b>을 이용합니다.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>키워드 풀(Pool) 관리</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1"><b>[키워드 관리]</b>에 등록된 모든 키워드로 기사를 수집합니다.</li>
                                                        <li class="mb-1">제목 금지어와 AI 연관도 심사(해양레저산업 3대 축)를 거쳐 <b>최대 5개 토픽</b>으로 브리핑을 작성합니다.</li>
                                                        <li class="mb-1">심사를 통과한 나머지 기사는 공개 상세 하단 <b>'최신 관련 기사'</b>에 제목·링크·날짜·언론사로 나열됩니다.</li>
                                                        <li class="mb-1"><span class="text-muted">사건사고·연예·정당/정치인 개인 기사는 자동 제외되고, 정부·지자체 정책 기사는 포함됩니다. 걸러야 할 유형이 더 보이면 개발사에 알려주시면 심사 기준에 추가합니다.</span></li>
                                                    </ul>
                                                </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                <li class="mb-4">
                                                    <strong>자동 생성 및 발송 사이클</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1">매주 <b>월요일 오전 8시</b>에 지난 한 주(지난 월요일 8시 이후) 기사를 수집해 새 클리핑이 <b>'임시저장(미노출)'</b> 상태로 자동 생성됩니다.</li>
                                                        <li class="mb-1">같은 날 <b>오전 10시</b>에 홈페이지 게시(노출)와 뉴스레터 구독자 이메일 발송이 <b>별도 승인 없이 자동</b>으로 진행됩니다. 공휴일에도 동일하게 실행됩니다.</li>
                                                        <li class="mb-1">8시~10시 사이에 <b>[수정/상세]</b>에서 내용을 손볼 수 있습니다.</li>
                                                        <li class="mb-1">이번 주 발송을 원하지 않으면 10시 전에 해당 글을 <b>삭제</b>해 주세요. 삭제된 글은 발송되지 않습니다.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>즉시 게시</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1">임시저장 글의 <b>[즉시 게시]</b>는 누르는 즉시 홈페이지에 노출(게시중)됩니다. 메일은 발송하지 않습니다.</li>
                                                        <li class="mb-1">메일까지 바로 보내려면 기사 상세창의 <b>[이 기사로 뉴스레터 발송]</b>을 이용해 주세요.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>발행 후 문제 발견 시</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1">10시 발행 뒤 내용에 문제가 있으면 게시중 글의 <b>[미노출 변경]</b>을 눌러 주세요.</li>
                                                        <li class="mb-1">홈페이지 목록·메인·상세 페이지(이메일에 실린 링크 포함)에서 즉시 사라집니다.</li>
                                                        <li class="mb-1">이미 발송된 이메일은 회수되지 않습니다. 자동 발송은 월요일 10시 한 번뿐이라 미노출 처리 후 다시 발송되지는 않습니다.</li>
                                                        <li class="mb-1"><b>[수정/상세]</b>에서 내용을 고친 뒤 <b>[즉시 게시]</b>로 다시 노출할 수 있고, 수정본 재발송은 상세창의 <b>[이 기사로 뉴스레터 발송]</b>을 이용합니다.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>수동 생성 및 비상 발송</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1"><b>[AI 클리핑 수동 생성]</b> → <b>'생성만 하기'</b>는 메일 발송 없이 글만 생성(임시저장)합니다.</li>
                                                        <li class="mb-1">자동 발행은 <b>월요일 당일 생성 글</b>만 대상입니다. 다른 요일에 수동 생성한 글은 [즉시 게시]와 [이 기사로 뉴스레터 발송]으로 직접 발행해 주세요.</li>
                                                        <li class="mb-1">긴급 발송은 <b>'생성 및 발송'</b>을 이용합니다.</li>
                                                    </ul>
                                                </li>
                                                <li class="mb-4">
                                                    <strong>키워드 풀(Pool) 관리</strong>
                                                    <ul class="mt-1 mb-0 ps-4" style="list-style: disc;">
                                                        <li class="mb-1"><b>[키워드 관리]</b>에 등록된 모든 키워드로 기사를 수집합니다.</li>
                                                        <li class="mb-1">제목 금지어와 AI 연관도 심사(해양레저산업 3대 축)를 거쳐 <b>최대 5개 토픽</b>으로 브리핑을 작성합니다.</li>
                                                        <li class="mb-1">심사를 통과한 나머지 기사는 공개 상세 하단 <b>'최신 관련 기사'</b>에 제목·링크·날짜·언론사로 나열됩니다.</li>
                                                        <li class="mb-1"><span class="text-muted">사건사고·연예·정당/정치인 개인 기사는 자동 제외되고, 정부·지자체 정책 기사는 포함됩니다. 걸러야 할 유형이 더 보이면 개발사에 알려주시면 심사 기준에 추가합니다.</span></li>
                                                    </ul>
                                                </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- // AI 클리핑 운영 가이드 안내창 끝 -->

                                <div class="card card-flush">
                                    <div class="card-header align-items-center py-5 gap-2 gap-md-5">
                                        <div class="card-title">
                                            <div class="d-flex align-items-center position-relative my-1 gap-2">
                                                <!-- 날짜 필터 -->
                                                <input type="date" id="searchStartDate" class="form-control form-control-solid w-150px" placeholder="시작일" />
                                                <span class="fw-bold text-gray-400">~</span>
                                                <input type="date" id="searchEndDate" class="form-control form-control-solid w-150px" placeholder="종료일" />
                                                <!-- 제목 검색 (크기 w-350px로 확장) -->
                                                <div class="d-flex align-items-center position-relative ms-2">
                                                    <i class="ki-duotone ki-magnifier fs-3 position-absolute ms-4"><span class="path1"></span><span class="path2"></span></i>
                                                    <input type="text" id="searchTitle" class="form-control form-control-solid w-350px ps-12" placeholder="기사 제목 검색" />
                                                </div>
                                                <button type="button" id="btnSearchClipping" class="btn btn-sm btn-secondary ms-2">검색</button>
                                                <button type="button" id="btnResetSearch" class="btn btn-sm btn-light ms-2">초기화</button>
                                            </div>
                                        </div>
                                        <div class="card-toolbar flex-row-fluid justify-content-end gap-5">
                                            <button type="button" id="btnManualUpdate" class="btn btn-sm btn-primary">
                                                <i class="ki-duotone ki-arrows-circle fs-2"></i> AI 클리핑 수동 생성
                                            </button>
                                            <button type="button" id="btnKeywordMng" class="btn btn-sm btn-info">
                                                <i class="ki-duotone ki-setting-2 fs-2"></i> 키워드 관리
                                            </button>
                                        </div>
                                    </div>
                                    <div class="card-body pt-0">
                                        <div class="d-flex justify-content-start mb-3">
                                            <span class="fw-bold fs-6 text-gray-700">총 발행 건수 : <span id="clippingTotalCount" class="text-primary fs-5">0</span> 건</span>
                                        </div>

                                        <div class="table-responsive">
                                            <table class="table align-middle table-row-dashed fs-6 gy-5">
                                                <thead>
                                                <tr class="text-start text-gray-400 fw-bold fs-7 text-uppercase gs-0">
                                                    <th class="min-w-50px text-center">연번</th>
                                                    <th class="min-w-300px">제목</th>
                                                    <th class="min-w-100px text-center">조회수</th>
                                                    <th class="min-w-100px text-center">공유횟수</th>
                                                    <th class="min-w-150px text-center">발송 현황</th>
                                                    <th class="min-w-125px text-center">노출 상태</th>
                                                    <th class="min-w-150px text-center">등록일시</th>
                                                    <th class="min-w-100px text-center">상세보기</th>
                                                </tr>
                                                </thead>
                                                <tbody class="fw-semibold text-gray-600 text-center" id="clippingListBody">
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="d-flex justify-content-center mt-5" id="clippingPagination"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--begin::Scrolltop-->
    <div id="kt_scrolltop" class="scrolltop" data-kt-scrolltop="true">
        <i class="ki-duotone ki-arrow-up">
            <span class="path1"></span>
            <span class="path2"></span>
        </i>
    </div>
    <!--end::Scrolltop-->

    <!-- 상세 모달 팝업 (크기 대폭 확장 및 textarea 기반 원본 편집) -->
    <div class="modal fade" id="modalClippingDetail" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered mw-1000px mh-850px">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="fw-bold">클리핑 기사 상세/수정</h2>
                    <div class="btn btn-icon btn-sm btn-active-icon-primary" data-bs-dismiss="modal"><i class="ki-duotone ki-cross fs-1"></i></div>
                </div>
                <div class="modal-body py-10 px-lg-17">
                    <input type="hidden" id="editSeq" />

                    <div class="fv-row mb-7">
                        <label class="form-label fw-bold">제목</label>
                        <input type="text" id="editTitle" class="form-control form-control-solid form-control-lg" />
                    </div>

                    <div class="fv-row mb-7">
                        <label class="form-label fw-bold d-flex justify-content-between">
                            <span>내용 (아래 텍스트를 마우스로 클릭하여 바로 수정하세요)</span>
                        </label>
                        <div style="border: 1px solid #e4e6ef; border-radius: 0.475rem; padding: 1.5rem; height: 500px; overflow-y: auto; background-color: #fff;">
                            <!-- contenteditable="true" 가 핵심! 태그는 숨기고 텍스트만 시각적으로 수정할 수 있게 해줍니다 -->
                            <div id="visualEditor" contenteditable="true" style="outline: none; min-height: 100%;"></div>
                        </div>
                        <div class="text-muted fs-7 mt-2">* 복잡한 HTML 코드는 숨겨집니다. 화면에 보이는 대로 글자를 수정하시면 디자인과 하단 출처는 안전하게 유지됩니다.</div>

                        <%-- 최신 관련 기사: 본문 HTML 과 별도로 기사 단위 테이블에 저장되어 발행 화면·메일·미리보기에서만 본문 아래에 자동으로 붙는다. 편집 대상이 아니므로 여기서는 읽기 전용으로 보여 준다. --%>
                        <div id="editRelatedBox" class="mt-8 p-5 rounded" style="background-color: #f1faff; border: 1px dashed #009ef7;">
                            <div class="d-flex align-items-start">
                                <i class="ki-duotone ki-information-5 fs-2x text-primary me-3"><span class="path1"></span><span class="path2"></span><span class="path3"></span></i>
                                <div class="fs-6">
                                    <div class="fw-bold text-primary mb-1">"최신 관련 기사" 목록은 위 본문에 들어 있지 않습니다.</div>
                                    <div class="text-gray-700">본문과 별도로 저장되며, <b>공개 게시글·구독자 메일·프론트 미리보기</b>에서는 본문 아래에 자동으로 붙습니다.
                                        (수집 후 중복 제거와 연관도 심사를 통과한 기사, 날짜순 최대 150건, 0점·블랙리스트 기사 제외) 이 목록은 편집할 수 없습니다.</div>
                                </div>
                            </div>
                            <div class="separator my-4"></div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold fs-6">■ 최신 관련 기사 <span id="editRelatedCount" class="text-primary">0</span>건</span>
                                <button type="button" id="btnToggleRelated" class="btn btn-sm btn-light-primary">목록 펼치기</button>
                            </div>
                            <div id="editRelatedEmpty" class="text-muted fs-7 mt-3" style="display: none;">이 글은 기사 단위 데이터가 없는 이전 방식(2026-10-28 이전) 글이라 관련 기사 영역이 표시되지 않습니다.</div>
                            <ul id="editRelatedList" class="list-unstyled mt-3 mb-0 fs-7" style="display: none; max-height: 320px; overflow-y: auto; line-height: 1.7;"></ul>
                        </div>
                    </div>
                </div>
                <div class="modal-footer d-flex justify-content-between">
                    <!-- 좌측: 프론트 미리보기 버튼 -->
                    <div>
                        <button type="button" class="btn btn-info" id="btnPreviewClipping">
                            <i class="ki-duotone ki-monitor fs-2"></i> 프론트 미리보기
                        </button>
                    </div>
                    <!-- 우측: 기존 기능 버튼들 -->
                    <div class="d-flex gap-2">
                        <button type="button" class="btn btn-success" id="btnSendNewsletter">이 기사로 뉴스레터 발송</button>
                        <button type="button" class="btn btn-primary" id="btnSaveClipping">수정 저장</button>
                        <button type="button" class="btn btn-danger" id="btnDeleteClipping">삭제</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 발송 이력 모달 팝업 추가 -->
    <div class="modal fade" id="modalSendHistory" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered mw-900px">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="fw-bold">뉴스레터 발송 이력</h2>
                    <div class="btn btn-icon btn-sm btn-active-icon-primary" data-bs-dismiss="modal"><i class="ki-duotone ki-cross fs-1"></i></div>
                </div>
                <div class="modal-body py-10 px-lg-17">
                    <input type="hidden" id="historyClippingSeq" />

                    <div class="d-flex justify-content-end mb-3">
                        <select id="filterSendResult" class="form-select form-select-sm form-select-solid w-150px">
                            <option value="">전체 보기</option>
                            <option value="발송성공">발송 성공</option>
                            <option value="발송실패">발송 실패</option>
                        </select>
                    </div>

                    <div class="table-responsive">
                        <table class="table align-middle table-row-dashed fs-6 gy-5">
                            <thead>
                            <tr class="text-start text-gray-400 fw-bold fs-7 text-uppercase gs-0">
                                <th class="min-w-50px text-center">번호</th>
                                <th class="min-w-200px text-center">수신자 이메일</th>
                                <th class="min-w-100px text-center">결과</th>
                                <th class="min-w-150px text-center">메시지</th>
                                <th class="min-w-150px text-center">발송일시</th>
                            </tr>
                            </thead>
                            <tbody class="fw-semibold text-gray-600 text-center" id="historyListBody">
                            </tbody>
                        </table>
                    </div>
                    <div class="d-flex justify-content-center mt-5" id="historyPagination"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 키워드 관리 모달 팝업 추가 (파일 맨 하단 </body> 바로 위쪽) -->
    <div class="modal fade" id="modalKeywordMng" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered mw-600px">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="fw-bold">수집 키워드 관리</h2>
                    <div class="btn btn-icon btn-sm btn-active-icon-primary" data-bs-dismiss="modal"><i class="ki-duotone ki-cross fs-1"></i></div>
                </div>
                <div class="modal-body py-10 px-lg-17">

                    <div class="d-flex mb-3">
                        <input type="text" id="newKeyword" class="form-control form-control-solid me-2" placeholder="새로운 수집 키워드 입력" />
                        <button type="button" id="btnAddKeyword" class="btn btn-primary min-w-80px">추가</button>
                    </div>
                    <!-- 안내문구 추가 -->
                    <div class="text-gray-600 fs-7 mb-5" style="line-height: 1.5;">
                        * 15개 이하 키워드 등록을 권장하지 않습니다 (중복 기사 가능성이 높아집니다).<br>
                        * 등록된 키워드 풀에서 <strong>5개씩 무작위(랜덤)로 추출</strong>하여 뉴스를 수집합니다.<br>
                        * <strong>5개씩 추출하는 이유:</strong> 너무 적은 개수로 수집하면 비슷한 기사만 쏠려서 수집되고, 너무 많은 개수로 수집하면 OpenAI API 토큰 한도 초과 및 크롤링 차단(Timeout) 위험이 발생하므로, 다양성과 시스템 안정성을 모두 확보할 수 있는 최적의 숫자이기 때문입니다.
                    </div>

                    <div class="d-flex justify-content-end mb-2">
                        <span class="fw-bold fs-6 text-dark">총 등록 키워드 : <span id="keywordTotalCount" class="text-primary fs-5">0</span> 개</span>
                    </div>

                    <div class="table-responsive" style="max-height: 400px; overflow-y: auto;">
                        <table class="table align-middle table-row-dashed fs-6 gy-3">
                            <tbody id="keywordListBody" class="text-gray-700 fw-bold">
                            </tbody>
                        </table>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <script>var hostUrl = "/assets/";</script>
    <!--begin::Global Javascript Bundle(mandatory for all pages)-->
    <script src="/assets/plugins/global/plugins.bundle.js"></script>
    <script src="/assets/js/scripts.bundle.js"></script>
    <!--end::Global Javascript Bundle-->
    <!--begin::Vendors Javascript(used for this page only)-->
    <script src="/assets/plugins/custom/datatables/datatables.bundle.js"></script>
    <!--end::Vendors Javascript-->
    <!--begin::Custom Javascript(used for this page only)-->
    <script src="/assets/js/custom/apps/ecommerce/catalog/tables.js?ver=20260630"></script>
    <script src="/assets/js/widgets.bundle.js"></script>
    <script src="/assets/js/custom/widgets.js"></script>
    <script src="/assets/js/custom/apps/chat/chat.js"></script>
    <script src="/assets/js/custom/utilities/modals/upgrade-plan.js"></script>
    <script src="/assets/js/custom/utilities/modals/create-app.js"></script>
    <script src="/assets/js/custom/utilities/modals/users-search.js"></script>
    <!--end::Custom Javascript-->

    <!--begin::Custom Javascript(used for common page)-->
    <script src="/js/mngMain.js?ver=20260630"></script>
    <script>
        // 발행 승인 버튼은 ai.clipping.publish-requires-approval=true 일 때만 노출 (기본 false: 월 10:00 승인 없이 자동 발송)
        var clippingApprovalRequired = ${clippingApprovalRequired ? 'true' : 'false'};
    </script>
    <script src="/js/custom/clipping.js?ver=20261029b"></script>

</c:if>
</body>
</html>