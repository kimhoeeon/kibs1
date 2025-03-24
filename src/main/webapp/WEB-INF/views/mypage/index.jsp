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
                        <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do','${info.seq}')" class="btnSt01">마이페이지</a>
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
                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                    </ul>
                </div>
            </div>

            <!-- section -->
            <div class="my_main my_form form_s">
                <div class="inner">
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">참가비 결제 현황 :
                                <c:if test="${info.prcYn eq '0'}"><span class="txtRed">미납</span></c:if>
                                <c:if test="${info.prcYn eq '1'}"><span class="txtBlue">참가비 납부</span></c:if>
                                <c:if test="${info.prcYn eq '2'}"><span class="txtBlue">50% 납부</span></c:if>
                                <c:if test="${info.prcYn eq '3'}"><span class="txtBlue">전액 납부</span></c:if>
                                <c:if test="${info.prcYn eq '4'}"><span class="txtBlue">완납</span></c:if>
                            </div>
                        </div>
                        <div class="my_main_pay">
                            <ul class="payList">
                                <li>
                                    <div class="cont_box">
                                        <div class="icon"><img src="/img/icon_my01.png"></div>
                                        <div class="txt_box">
                                            <div class="gubun">부스 참가비</div>
                                            <div class="txt"><fmt:formatNumber value="${info.boothPrcSum}" pattern="#,###"/> 원</div>
                                        </div>
                                    </div>
                                    <a class="btn" href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">┼</a>
                                </li>
                                <li>
                                    <div class="cont_box">
                                        <div class="icon"><img src="/img/icon_my02.png"></div>
                                        <div class="txt_box">
                                            <div class="gubun">유틸리티</div>
                                            <div class="txt"><fmt:formatNumber value="${info.utilityPrcSum}" pattern="#,###"/> 원</div>
                                        </div>
                                    </div>
                                    <a class="btn" href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">┼</a>
                                </li>
                                <%--<li>
                                    <div class="cont_box">
                                        <div class="icon"><img src="/img/icon_my03.png"></div>
                                        <div class="txt_box">
                                            <div class="gubun">기타</div>
                                            <div class="txt">0 원</div>
                                        </div>
                                    </div>
                                    <a class="btn" href="javascript:void(0);">┼</a>
                                </li>--%>
                                <li>
                                    <div class="cont_box">
                                        <div class="icon"><img src="/img/icon_my04.png"></div>
                                        <div class="txt_box">
                                            <div class="gubun">할인</div>
                                            <div class="txt"><fmt:formatNumber value="${info.discountPrcSum}" pattern="#,###"/> 원</div>
                                        </div>
                                    </div>
                                    <a class="btn" href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">┼</a>
                                </li>
                                <li>
                                    <div class="cont_box">
                                        <div class="icon"><img src="/img/icon_my05.png"></div>
                                        <div class="txt_box">
                                            <div class="gubun">합계(VAT 별도)</div>
                                            <div class="txt"><fmt:formatNumber value="${info.prcSum}" pattern="#,###"/> 원</div>
                                        </div>
                                    </div>
                                    <a class="btn" href="javascript:void(0);" onclick="f_page_move('/mypage/total.do','${info.seq}')">┼</a>
                                </li>
                            </ul>
                            <div class="payTotal">
                                <span class="amount"><fmt:formatNumber value="${info.prcSum}" pattern="#,###"/> 원</span>
                                <span class="vat"><fmt:formatNumber value="${info.prcVat}" pattern="#,###"/> 원(VAT)</span>
                                <span class="total">총합계 <fmt:formatNumber value="${info.prcTotal}" pattern="#,###"/> 원</span>
                            </div>
                        </div>
                    </div>
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">참가신청서 <span class="txtRed">수정 방법</span></div>
                        </div>
                        <div>
                            <ul>
                                <li style="margin-bottom: 15px; font-size: 1.8rem;">
                                    1 . 마이페이지 좌측의 탭 중에서, 수정하고자 하는 탭을 클릭하신 후 수정할 정보를 입력해 주세요.
                                </li>
                                <li style="font-size: 1.8rem;">
                                    2 . 수정할 정보 입력 후, 우측 하단의 [다음] 버튼을 눌러 저장하세요. “부스 신청 정보가 저장되었습니다“ 와 같은 안내 창이 뜨면 정상적으로 정보가 변경된 것입니다.
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!-- //section -->

        </div>
    </div>
    <!-- //section -->

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>
</c:if>
</body>
</html>