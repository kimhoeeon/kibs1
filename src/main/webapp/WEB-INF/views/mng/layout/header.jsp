<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--begin::Header-->
<div id="kt_app_header" class="app-header">
    <!--begin::Header container-->
    <div class="app-container container-fluid d-flex align-items-stretch justify-content-between"
         id="kt_app_header_container">
        <!--begin::Sidebar mobile toggle-->
        <div class="d-flex align-items-center d-lg-none ms-n3 me-1 me-md-2" title="Show sidebar menu">
            <div class="btn btn-icon btn-active-color-primary w-35px h-35px"
                 id="kt_app_sidebar_mobile_toggle">
                <i class="ki-duotone ki-abstract-14 fs-2 fs-md-1">
                    <span class="path1"></span>
                    <span class="path2"></span>
                </i>
            </div>
        </div>
        <!--end::Sidebar mobile toggle-->
        <!--begin::Mobile logo-->
        <div class="d-flex align-items-center flex-grow-1 flex-lg-grow-0">
            <a href="/mng/main.do" class="d-lg-none">
                <img alt="Logo"
                     src="/img/logo.png"
                     class="h-30px"/>
            </a>
        </div>
        <!--end::Mobile logo-->
        <!--begin::Header wrapper-->
        <div class="d-flex align-items-stretch justify-content-between flex-lg-grow-1"
             id="kt_app_header_wrapper">
            <!--begin::Menu wrapper-->
            <div class="app-header-menu app-header-mobile-drawer align-items-stretch" data-kt-drawer="true"
                 data-kt-drawer-name="app-header-menu" data-kt-drawer-activate="{default: true, lg: false}"
                 data-kt-drawer-overlay="true" data-kt-drawer-width="250px" data-kt-drawer-direction="end"
                 data-kt-drawer-toggle="#kt_app_header_menu_toggle" data-kt-swapper="true"
                 data-kt-swapper-mode="{default: 'append', lg: 'prepend'}"
                 data-kt-swapper-parent="{default: '#kt_app_body', lg: '#kt_app_header_wrapper'}">
                <!--begin::Menu-->
                <div class="menu menu-rounded menu-column menu-lg-row my-5 my-lg-0 align-items-stretch fw-semibold px-2 px-lg-0"
                     id="kt_app_header_menu" data-kt-menu="true">
                    <!--begin:Menu item-->
                    <div data-kt-menu-trigger="hover"
                         data-kt-menu-placement="bottom-start"
                         class="menu-item menu-lg-down-accordion me-0 me-lg-2">
                        <!--begin:Menu link-->
                        <span class="menu-link">
                        <span class="menu-title">시스템 사용 현황</span>
                        <span class="menu-arrow d-lg-none"></span>
                    </span>
                        <!--end:Menu link-->
                        <!--begin:Menu sub-->
                        <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0 w-100 w-lg-600px">
                            <!--begin:Dashboards menu-->
                            <div class="menu-state-bg menu-extended overflow-hidden overflow-lg-visible"
                                 data-kt-menu-dismiss="true">
                                <!--begin:Row-->
                                <div class="row">
                                    <!--begin:Col-->
                                    <div class="col-lg-12 mb-3 mb-lg-0 py-3 px-3 py-lg-6 px-lg-6">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-6 mb-3">
                                                <!--begin:Menu item-->
                                                <div class="menu-item p-0 m-0">
                                                    <!--begin:Menu link-->
                                                    <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');"
                                                       class="menu-link">
                                                    <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                        <i class="ki-duotone ki-element-11 text-primary fs-1">
                                                            <span class="path1"></span>
                                                            <span class="path2"></span>
                                                            <span class="path3"></span>
                                                            <span class="path4"></span>
                                                        </i>
                                                    </span>
                                                        <span class="d-flex flex-column">
                                                        <span class="fs-6 fw-bold text-gray-800">체류 정보 시스템</span>
                                                        <span class="fs-7 fw-semibold text-muted">Residence Information System</span>
                                                    </span>
                                                    </a>
                                                    <!--end:Menu link-->
                                                </div>
                                                <!--end:Menu item-->
                                            </div>
                                            <!--end:Col-->
                                            <!--begin:Col-->
                                            <div class="col-lg-6 mb-3">
                                                <!--begin:Menu item-->
                                                <div class="menu-item p-0 m-0">
                                                    <!--begin:Menu link-->
                                                    <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');"
                                                       class="menu-link">
                                                        <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                        <i class="ki-duotone ki-basket text-danger fs-1">
                                                            <span class="path1"></span>
                                                            <span class="path2"></span>
                                                            <span class="path3"></span>
                                                            <span class="path4"></span>
                                                        </i>
                                                    </span>
                                                        <span class="d-flex flex-column">
                                                        <span class="fs-6 fw-bold text-gray-800">현장 주선 시스템</span>
                                                        <span class="fs-7 fw-semibold text-muted">An On-site Arrangement System</span>
                                                    </span>
                                                    </a>
                                                    <!--end:Menu link-->
                                                </div>
                                                <!--end:Menu item-->
                                            </div>
                                            <!--end:Col-->
                                            <!--begin:Col-->
                                            <div class="col-lg-6 mb-3">
                                                <!--begin:Menu item-->
                                                <div class="menu-item p-0 m-0">
                                                    <!--begin:Menu link-->
                                                    <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');"
                                                       class="menu-link">
                                                    <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                        <i class="ki-duotone ki-abstract-44 text-info fs-1">
                                                            <span class="path1"></span>
                                                            <span class="path2"></span>
                                                        </i>
                                                    </span>
                                                        <span class="d-flex flex-column">
                                                        <span class="fs-6 fw-bold text-gray-800">무역관 시스템</span>
                                                        <span class="fs-7 fw-semibold text-muted">Trade View System</span>
                                                    </span>
                                                    </a>
                                                    <!--end:Menu link-->
                                                </div>
                                                <!--end:Menu item-->
                                            </div>
                                            <!--end:Col-->
                                            <!--begin:Col-->
                                            <div class="col-lg-6 mb-3">
                                                <!--begin:Menu item-->
                                                <div class="menu-item p-0 m-0">
                                                    <!--begin:Menu link-->
                                                    <a href="javascript:alert('현재 사용 중지된 메뉴입니다.');"
                                                       class="menu-link">
                                                    <span class="menu-custom-icon d-flex flex-center flex-shrink-0 rounded w-40px h-40px me-3">
                                                        <i class="ki-duotone ki-color-swatch text-success fs-1">
                                                            <span class="path1"></span><span class="path2"></span><span class="path3"></span><span class="path4"></span><span class="path5"></span><span class="path6"></span><span class="path7"></span><span class="path8"></span><span class="path9"></span><span class="path10"></span><span class="path11"></span><span class="path12"></span><span class="path13"></span><span class="path14"></span><span class="path15"></span><span class="path16"></span><span class="path17"></span><span class="path18"></span><span class="path19"></span><span class="path20"></span><span class="path21"></span>
                                                        </i>
                                                    </span>
                                                        <span class="d-flex flex-column">
                                                        <span class="fs-6 fw-bold text-gray-800">부대행사 시스템</span>
                                                        <span class="fs-7 fw-semibold text-muted">Incidental Event System</span>
                                                    </span>
                                                    </a>
                                                    <!--end:Menu link-->
                                                </div>
                                                <!--end:Menu item-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Col-->
                                </div>
                                <!--end:Row-->
                            </div>
                            <!--end:system menu-->
                        </div>
                        <!--end:Menu sub-->
                    </div>
                    <!--end:Menu item-->
                    <!--begin:Menu item-->
                    <div data-kt-menu-trigger="hover"
                         data-kt-menu-placement="bottom-start" class="menu-item menu-lg-down-accordion me-0 me-lg-2">
                        <!--begin:Menu link-->
                        <span class="menu-link">
                        <span class="menu-title">전시회</span>
                        <span class="menu-arrow d-lg-none"></span>
                    </span>
                        <!--end:Menu link-->
                        <!--begin:Menu sub-->
                        <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                            <!--begin:Pages menu-->
                            <div class="menu-active-bg px-4 px-lg-0">
                                <!--begin:Tabs nav-->
                                <div class="d-flex w-100 overflow-auto">
                                    <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary"
                                               href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_visitor">참가자 관리</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_transfer">이관정보</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_application_new_new">참가신청서 관리 (2027~)</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_application_new">참가신청서 관리 (2026)</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_application">참가신청서 관리 (~2025)</a>
                                        </li>
                                        <!--end:Nav item-->
                                    </ul>
                                </div>
                                <!--end:Tabs nav-->
                                <!--begin:Tab content-->
                                <div class="tab-content py-4 py-lg-8 px-lg-7">
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_visitor">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/participant/visitor.do" class="menu-link">
                                                                <span class="menu-title">참관객 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/member.do" class="menu-link">
                                                                <span class="menu-title">회원 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_transfer">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/transfer/company.do" class="menu-link">
                                                                <span class="menu-title">지난 행사 전시업체 (~2025)</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/transfer/visitor.do" class="menu-link">
                                                                <span class="menu-title">지난 행사 참관객</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_application_new_new">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/participant/company.do" class="menu-link">
                                                                <span class="menu-title">전시업체 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/booth.do" class="menu-link">
                                                                <span class="menu-title">전시부스 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/product.do" class="menu-link">
                                                                <span class="menu-title">요트/보트 출품 정보</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/sign.do" class="menu-link">
                                                                <span class="menu-title">상호간판 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/utility.do" class="menu-link">
                                                                <span class="menu-title">유틸리티 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/pass.do" class="menu-link">
                                                                <span class="menu-title">출입증 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/buyer.do" class="menu-link">
                                                                <span class="menu-title">초청 희망 바이어</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/gift.do" class="menu-link">
                                                                <span class="menu-title">경품제공 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNewNew/application/online.do" class="menu-link">
                                                                <span class="menu-title">온라인 전시관</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_application_new">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/participant/company.do" class="menu-link">
                                                                <span class="menu-title">전시업체 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/booth.do" class="menu-link">
                                                                <span class="menu-title">전시부스 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/maritime.do" class="menu-link">
                                                                <span class="menu-title">해상전시회 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/product.do" class="menu-link">
                                                                <span class="menu-title">요트/보트 출품 정보</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/sign.do" class="menu-link">
                                                                <span class="menu-title">상호간판 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/utility.do" class="menu-link">
                                                                <span class="menu-title">유틸리티 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/pass.do" class="menu-link">
                                                                <span class="menu-title">출입증 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/buyer.do" class="menu-link">
                                                                <span class="menu-title">초청 희망 바이어</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/gift.do" class="menu-link">
                                                                <span class="menu-title">경품제공 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitorNew/application/online.do" class="menu-link">
                                                                <span class="menu-title">온라인 전시관</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-350px" id="kt_app_header_menu_pages_application">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <%--<div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/participant/company.do" class="menu-link">
                                                                <span class="menu-title">전시업체 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>--%>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/booth.do" class="menu-link">
                                                                <span class="menu-title">전시부스 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/sign.do" class="menu-link">
                                                                <span class="menu-title">상호간판 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/utility.do" class="menu-link">
                                                                <span class="menu-title">유틸리티 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/pass.do" class="menu-link">
                                                                <span class="menu-title">출입증 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/buyer.do" class="menu-link">
                                                                <span class="menu-title">초청 희망 바이어</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/gift.do" class="menu-link">
                                                                <span class="menu-title">경품제공 신청</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/exhibitor/application/online.do" class="menu-link">
                                                                <span class="menu-title">온라인 전시관</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                </div>
                                <!--end:Tab content-->
                            </div>
                            <!--end:Pages menu-->
                        </div>
                        <!--end:Menu sub-->
                    </div>
                    <!--end:Menu item-->
                    <!--begin:Menu item-->
                    <div data-kt-menu-trigger="hover"
                         data-kt-menu-placement="bottom-start" class="menu-item menu-lg-down-accordion me-0 me-lg-2">
                        <!--begin:Menu link-->
                        <span class="menu-link">
                        <span class="menu-title">부대행사</span>
                        <span class="menu-arrow d-lg-none"></span>
                    </span>
                        <!--end:Menu link-->
                        <!--begin:Menu sub-->
                        <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                            <!--begin:Pages menu-->
                            <div class="menu-active-bg px-4 px-lg-0">
                                <!--begin:Tabs nav-->
                                <div class="d-flex w-100 overflow-auto">
                                    <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary"
                                               href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_product">올해의 제품</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_company">KIBS 참가업체</a>
                                        </li>
                                        <!--end:Nav item-->
                                    </ul>
                                </div>
                                <!--end:Tabs nav-->
                                <!--begin:Tab content-->
                                <div class="tab-content py-4 py-lg-8 px-lg-7">
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-250px"
                                         id="kt_app_header_menu_pages_product">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/event/product.do" class="menu-link">
                                                                <span class="menu-title">참가자 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-250px" id="kt_app_header_menu_pages_company">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/event/company.do" class="menu-link">
                                                                <span class="menu-title">참가자 목록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                </div>
                                <!--end:Tab content-->
                            </div>
                            <!--end:Pages menu-->
                        </div>
                        <!--end:Menu sub-->
                    </div>
                    <!--end:Menu item-->
                    <!--begin:Menu item-->
                    <div data-kt-menu-trigger="hover"
                         data-kt-menu-placement="bottom-start" class="menu-item menu-lg-down-accordion me-0 me-lg-2">
                        <!--begin:Menu link-->
                        <span class="menu-link">
                        <span class="menu-title">정보센터</span>
                        <span class="menu-arrow d-lg-none"></span>
                    </span>
                        <!--end:Menu link-->
                        <!--begin:Menu sub-->
                        <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                            <!--begin:Pages menu-->
                            <div class="menu-active-bg px-4 px-lg-0">
                                <!--begin:Tabs nav-->
                                <div class="d-flex w-100 overflow-auto">
                                    <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary"
                                               href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_notice">게시판 관리</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_popup">팝업 관리</a>
                                        </li>
                                        <!--end:Nav item-->
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_newsletter">eDM 관리</a>
                                        </li>
                                        <!--end:Nav item-->
                                    </ul>
                                </div>
                                <!--end:Tabs nav-->
                                <!--begin:Tab content-->
                                <div class="tab-content py-4 py-lg-8 px-lg-7">
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-300px"
                                         id="kt_app_header_menu_pages_notice">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/notice.do" class="menu-link">
                                                                <span class="menu-title">공지사항</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/newsletter.do" class="menu-link">
                                                                <span class="menu-title">뉴스레터</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/press.do" class="menu-link">
                                                                <span class="menu-title">보도자료</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/column.do" class="menu-link">
                                                                <span class="menu-title">칼럼</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/faq.do" class="menu-link">
                                                                <span class="menu-title">FAQ</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/dataroom.do" class="menu-link">
                                                                <span class="menu-title">갤러리</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/kibstv.do" class="menu-link">
                                                                <span class="menu-title">KIBS Tube</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/contest.do" class="menu-link">
                                                                <span class="menu-title">공모전</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/board/brochure.do" class="menu-link">
                                                                <span class="menu-title">브로슈어</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-400px" id="kt_app_header_menu_pages_popup">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/popup/add.do" class="menu-link">
                                                                <span class="menu-title">팝업 등록</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-400px" id="kt_app_header_menu_pages_document">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/document/download.do" class="menu-link">
                                                                <span class="menu-title">파일 다운로드 내역</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-400px"
                                         id="kt_app_header_menu_pages_newsletter">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/center/edm/list.do" class="menu-link">
                                                                <span class="menu-title">메일 발송 & 이력</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                </div>
                                <!--end:Tab content-->
                            </div>
                            <!--end:Pages menu-->
                        </div>
                        <!--end:Menu sub-->
                    </div>
                    <!--end:Menu item-->
                    <!--begin:Menu item-->
                    <div data-kt-menu-trigger="hover"
                         data-kt-menu-placement="bottom-start" class="menu-item menu-lg-down-accordion me-0 me-lg-2">
                        <!--begin:Menu link-->
                        <span class="menu-link">
                            <span class="menu-title">개발사</span>
                            <span class="menu-arrow d-lg-none"></span>
                        </span>
                        <!--end:Menu link-->
                        <!--begin:Menu sub-->
                        <div class="menu-sub menu-sub-lg-down-accordion menu-sub-lg-dropdown p-0">
                            <!--begin:Pages menu-->
                            <div class="menu-active-bg px-4 px-lg-0">
                                <!--begin:Tabs nav-->
                                <div class="d-flex w-100 overflow-auto">
                                    <ul class="nav nav-stretch nav-line-tabs fw-bold fs-6 p-0 p-lg-10 flex-nowrap flex-grow-1">
                                        <!--begin:Nav item-->
                                        <li class="nav-item mx-lg-1">
                                            <a class="nav-link py-3 py-lg-6 text-active-primary" href="#"
                                               data-bs-toggle="tab"
                                               data-bs-target="#kt_app_header_menu_pages_request">요청사항 & 문의</a>
                                        </li>
                                        <!--end:Nav item-->
                                    </ul>
                                </div>
                                <!--end:Tabs nav-->
                                <!--begin:Tab content-->
                                <div class="tab-content py-4 py-lg-8 px-lg-7">
                                    <!--begin:Tab pane-->
                                    <div class="tab-pane w-lg-175px" id="kt_app_header_menu_pages_request">
                                        <!--begin:Row-->
                                        <div class="row">
                                            <!--begin:Col-->
                                            <div class="col-lg-12 mb-6 mb-lg-0">
                                                <!--begin:Row-->
                                                <div class="row">
                                                    <!--begin:Col-->
                                                    <div class="col-lg-12">
                                                        <!--begin:Menu item-->
                                                        <div class="menu-item p-0 m-0">
                                                            <!--begin:Menu link-->
                                                            <a href="/mng/request/management/list.do" class="menu-link">
                                                                <span class="menu-title">요청사항 & 문의 관리</span>
                                                            </a>
                                                            <!--end:Menu link-->
                                                        </div>
                                                        <!--end:Menu item-->
                                                    </div>
                                                    <!--end:Col-->
                                                </div>
                                                <!--end:Row-->
                                            </div>
                                            <!--end:Col-->
                                        </div>
                                        <!--end:Row-->
                                    </div>
                                    <!--end:Tab pane-->
                                </div>
                                <!--end:Tab content-->
                            </div>
                            <!--end:Pages menu-->
                        </div>
                        <!--end:Menu sub-->
                    </div>
                    <!--end:Menu item-->
                </div>
                <!--end::Menu-->
            </div>
            <!--end::Menu wrapper-->
            <!--begin::Navbar-->
            <div class="app-navbar flex-shrink-0">
                <!--begin::User menu-->
                <div class="app-navbar-item ms-1 ms-md-3" id="kt_header_user_menu_toggle">
                    <!--begin::Menu wrapper-->
                    <div class="cursor-pointer symbol symbol-30px symbol-md-40px"
                         data-kt-menu-trigger="{default: 'click', lg: 'hover'}" data-kt-menu-attach="parent"
                         data-kt-menu-placement="bottom-end">
                        <i class="ki-solid ki-user-square fs-2qx"></i>
                    </div>
                    <!--begin::User account menu-->
                    <div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-800 menu-state-bg menu-state-color fw-semibold py-4 fs-6 w-275px"
                         data-kt-menu="true">
                        <!--begin::Menu item-->
                        <div class="menu-item px-3">
                            <div class="menu-content d-flex align-items-center px-3">
                                <!--begin::Avatar-->
                                <div class="symbol symbol-50px me-5">
                                    <i class="ki-solid ki-user-square fs-3x"></i>
                                </div>
                                <!--end::Avatar-->
                                <!--begin::Username-->
                                <div class="d-flex flex-column">
                                    <div class="fw-bold d-flex align-items-center fs-5">${sessionScope.id}
                                        <span class="badge badge-light-success fw-bold fs-8 px-2 py-1 ms-2">admin</span>
                                    </div>
                                    KIBS 관리자
                                </div>
                                <!--end::Username-->
                            </div>
                        </div>
                        <!--end::Menu item-->
                        <!--begin::Menu separator-->
                        <div class="separator my-2"></div>
                        <!--end::Menu separator-->
                        <!--begin::Menu item-->
                        <div class="menu-item px-5">
                            <a href="javascript:logout();" class="menu-link px-5">Sign Out</a>
                        </div>
                        <!--end::Menu item-->
                    </div>
                    <!--end::User account menu-->
                    <!--end::Menu wrapper-->
                </div>
                <!--end::User menu-->
                <!--begin::Header menu toggle-->
                <div class="app-navbar-item d-lg-none ms-2 me-n2" title="Show header menu">
                    <div class="btn btn-flex btn-icon btn-active-color-primary w-30px h-30px"
                         id="kt_app_header_menu_toggle">
                        <i class="ki-duotone ki-element-4 fs-1">
                            <span class="path1"></span>
                            <span class="path2"></span>
                        </i>
                    </div>
                </div>
                <!--end::Header menu toggle-->
            </div>
            <!--end::Navbar-->
        </div>
        <!--end::Header wrapper-->
    </div>
    <!--end::Header container-->
</div>
<!--end::Header-->

<script>
    // 헤더 메뉴 깜빡임 및 빈 탭 방지용 즉시 실행 함수
    (function() {
        var currentPath = window.location.pathname;
        var links = document.querySelectorAll('#kt_app_header_menu a.menu-link');

        links.forEach(function(link) {
            var href = link.getAttribute('href');
            if (href && href !== '#' && href.indexOf('javascript') === -1) {
                var baseUrl = href.split('.do')[0];

                if (baseUrl.length > 5 && currentPath.indexOf(baseUrl) > -1) {
                    link.classList.add('active');

                    var menuItem = link.closest('.menu-item');
                    if (menuItem) menuItem.classList.add('here', 'show');

                    var tabPane = link.closest('.tab-pane');
                    if (tabPane) {
                        var tabId = tabPane.getAttribute('id');
                        var megaMenu = link.closest('.menu-sub-lg-dropdown');

                        if (megaMenu) {
                            // 탭 상태 초기화 후 타겟 탭만 오픈
                            var navLinks = megaMenu.querySelectorAll('.nav-link');
                            navLinks.forEach(function(el) { el.classList.remove('active', 'text-active-primary'); });

                            var tabPanes = megaMenu.querySelectorAll('.tab-pane');
                            tabPanes.forEach(function(el) { el.classList.remove('active', 'show'); });

                            var targetNav = megaMenu.querySelector('.nav-link[data-bs-target="#' + tabId + '"]');
                            if (targetNav) targetNav.classList.add('active', 'text-active-primary');
                            tabPane.classList.add('active', 'show');
                        }
                    }
                }
            }
        });

        // 빈 화면(하얀 여백) 방지 처리
        var megaMenus = document.querySelectorAll('#kt_app_header_menu .menu-sub-lg-dropdown');
        megaMenus.forEach(function(megaMenu) {
            var activeNav = megaMenu.querySelector('.nav-link.active');
            if (!activeNav) {
                var firstNav = megaMenu.querySelector('.nav-link');
                if (firstNav) {
                    firstNav.classList.add('active', 'text-active-primary');
                    var targetId = firstNav.getAttribute('data-bs-target');
                    if (targetId) {
                        var targetPane = megaMenu.querySelector(targetId);
                        if (targetPane) targetPane.classList.add('active', 'show');
                    }
                }
            }
        });
    })();
</script>