<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Kibs 2025">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="title" content="경기국제보트쇼">
    <meta property="og:title" content="경기국제보트쇼">
    <meta property="og:image" content="/img/og_img.jpg">
    <meta name="color-scheme" content="light only">
    <meta name="supported-color-schemes" content="light">
    <meta itemprop="name" content="경기국제보트쇼">
    <meta itemprop="url" content="https://kibs.com/">
    <link rel="canonical" id="canonical" href="https://kibs.com/">
    <meta name="description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta name="twitter:description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta property="og:description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta itemprop="description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta property="nate:description" content="아시아 3대 보트쇼, 대한민국 해양레저산업의 채널, '경기국제보트쇼'">
    <meta property="og:keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta name="keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta property="twitter:keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta property="og:keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <meta name="keywords"
          content="경기국제보트쇼, KIBS, kibs, 김포 아라마리나, 요트&amp;보트, 해상전시, 워터스포츠, 무동력보트, 스포츠피싱쇼, 한국다이빙엑스포, 아웃도어, 캠핑카, 렛츠고 보트피싱, 비즈니스 상담, 전시참가, 사전등록, 참관객">
    <title>2025 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <span itemscope="" itemtype="http://schema.org/Organization">
    <link itemprop="url" href="https://kibs.com/">
    <a itemprop="sameAs" href="https://www.koreaboatshow.or.kr/"></a>
    <a itemprop="sameAs" href="https://koreaboatshow.re.kr/"></a>
    <a itemprop="sameAs" href="https://kibs-online.com"></a>
    <a itemprop="sameAs" href="https://www.youtube.com/channel/UCvcRu_g4M1MOIIuJyllR6Rw"></a>
    <a itemprop="sameAs" href="https://www.youtube.com/@KIBSKINTEX"></a>
    </span>
</head>

<body>

<c:if test="${status ne 'logon'}">
    <script>
        alert("로그인해 주세요.");
        location.href = '/login.do';
    </script>
</c:if>

<c:if test="${status eq 'logon'}">

<c:import url="../header.jsp" charEncoding="UTF-8"/>

<div id="container">

    <!-- section -->
    <div class="sub_top">
        <div class="inner">
            <div class="sub_top_box">
                <div class="sub_top_nav">
                    <span>홈</span><span>참가기업</span><span>마이페이지</span>
                </div>
                <div class="sub_top_tit">마이페이지</div>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="apply_s padding_tb" id="apply_s">
        <div class="inner">

            <div class="apply_nav">
                <div class="apply_nav_txt">
                    <div class="txt">${sessionScope.id} 님 환영합니다!</div>
                    <div class="btn">
                        <a href="javascript:void(0);" onclick="f_page_move('/mypage/modify.do','${info.seq}')" class="btnSt01">회원정보관리</a>
                        <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do', {seq: `${info.seq}`})" class="btnSt01">마이페이지</a>
                    </div>
                </div>
                <div class="apply_nav_list">
                    <ul class="list1">
                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step01.do','${info.seq}')">기본정보</a></li>
                        <li class="active">
                            <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시 신청 정보</a>
                            <ul class="list2">
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시부스 신청</a></li>
                                <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')">출입증 신청</a></li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_5.do','${info.seq}')">경품제공 신청</a></li>
                                <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_6.do','${info.seq}')">웹 배너 신청</a></li>--%>
                                    <%--<li><a href="/mypage/step2_7.do">온라인 전시관 신청</a></li>--%>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_8.do','${info.seq}')">디렉토리 정보</a></li>
                            </ul>
                        </li>
                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                    </ul>
                </div>
            </div>

            <!-- section -->
            <div class="my_form form_s">
                <div class="inner">
                    <div class="apply_tit">상호간판 신청</div>
                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>한글과 영어를 별도 표기하여 주십시오.</li>
                            <li>최대 글자 수 : 국문(64) , 영문(64)</li>
                            <li>쉼표, 마침표 등의 문장부호와 ‘(주)’, ‘CO.,LTD.' 등을 포함한 상호명을 정확하게 표기해 주십시오.</li>
                            <li><span class="emRed">상호간판은 '조립부스' 신청 기업만 기입하여 주십시오.</span></li>
                            <%--<li>2025 경기국제보트쇼 종료로 인해 <span class="emRed">전시 정보 수정이 불가</span>합니다</li>--%>
                        </ul>
                    </div>

                    <!-- 부스신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">상호간판 신청</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item">
                                    <p>상호간판명</p>
                                </div>
                                <div class="input">
                                    <input type="hidden" id="standAloneBoothCnt" value="${info.standAloneBoothCnt}">
                                    <input type="hidden" id="assemblyBoothCnt" value="${info.assemblyBoothCnt}">
                                    <input type="text" id="company_sign_name_ko" value="${info.companySignNameKo}" placeholder="국문" <%--disabled--%>>
                                    <input type="text" id="company_sign_name_en" value="${info.companySignNameEn}" placeholder="영문" class=onlyNumEng" <%--disabled--%>>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div class="form_btn">
                        <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')" class="form_btn_prev">
                            <div class="big">이전</div>
                            <div class="small">전시부스 신청</div>
                        </a>
                        <a href="javascript:void(0);" onclick="f_mypage_comp('2_2','${info.seq}')" class="form_btn_next">
                            <div class="big">다음</div>
                            <div class="small">유틸리티 신청</div>
                        </a>
                    </div>

                </div>
            </div>
            <!-- //section -->
        </div>
    </div>

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>
</c:if>
</body>
</html>