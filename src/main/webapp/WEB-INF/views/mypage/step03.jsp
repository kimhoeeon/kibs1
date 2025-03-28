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
                                <li>
                                    <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시 신청 정보</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시부스 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')">출입증 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_5.do','${info.seq}')">경품제공 신청</a></li>
                                            <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_6.do','${info.seq}')">웹 배너 신청</a></li>--%>
                                            <%--<li><a href="/mypage/step2_7.do">온라인 전시관 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_8.do','${info.seq}')">디렉토리 정보</a></li>
                                    </ul>
                                </li>
                                <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                            </ul>
                        </div>
                    </div>


                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">

                            <c:choose>
                                <c:when test = "${info.approvalStatus eq '작성중' and info.applyComplt eq 'N'}">
                                    <!-- 안내문구 -->
                                    <div class="form_wrap">
                                        <div class="form_guide_comp">
                                            <div class="form_guide_comp_img"><img src="/img/logo3.png"></div>
                                            <div class="form_guide_comp_txt">
                                                귀사는 2025 경기국제보트쇼에 참가하기 위한 필수 신청양식을 모두 입력하셨습니다.<br>
                                                최종적으로 아래의 참가신청 버튼을 눌러 행사 담당자에게 참가승인을 요청하시기 바랍니다.
                                            </div>
                                            <a href="javascript:f_apply_comp('03')" class="form_guide_comp_btn btnSt01">참가신청완료</a>
                                        </div>
                                    </div>
                                    <!-- 안내문구 -->
                                </c:when>
                                <c:when test = "${(info.approvalStatus eq '승인요청' or info.approvalStatus eq '참가승인') and info.applyComplt eq 'Y'}">
                                    <!-- 안내문구 -->
                                    <div class="form_wrap">
                                        <div class="form_guide_comp">
                                            <div class="form_guide_comp_img"><img src="/img/logo3.png"></div>
                                            <div class="form_guide_comp_txt">
                                                귀사는 2025 경기국제보트쇼에 참가하기 위한 필수 신청양식을 모두 입력하셨습니다.<br>
                                                <span class="txtRed">정상적으로 참가 접수가 완료</span>되었으며, 빠른 시일 내에 경기국제보트쇼 사무국에서 연락드리도록 하겠습니다.<br>
                                                감사합니다.
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 안내문구 -->
                                </c:when>
                                <c:otherwise>
                                    <!-- 안내문구 -->
                                    <div class="form_wrap">
                                        <div class="form_guide_comp">
                                            <div class="form_guide_comp_img"><img src="/img/logo3.png"></div>
                                            <div class="form_guide_comp_txt">
                                                귀사는 2025 경기국제보트쇼 <span class="txtRed">참가 취소</span> 되었습니다.<br>
                                                관련 문의사항은 경기국제보트쇼 사무국으로 연락 주시면 안내해 드리도록 하겠습니다.
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 안내문구 -->
                                </c:otherwise>
                            </c:choose>

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