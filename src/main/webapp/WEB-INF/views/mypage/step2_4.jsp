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
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')">출입증 신청</a></li>
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
                            <div class="apply_tit">출입증 신청</div>
                            <!-- 안내문구 -->
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <li>본 양식은 전시회에 참가하는 모든 업체에 해당되며 전시기간 중 전시장에 상주하실 귀사의 상주 요원 성명 및 직위를 제출하여 주시면 공사기간 및 전시기간 중
                                        전시장에 자유롭게 출입할 수 있는 Badge를 제작하여 장치공사기간 동안 전시장 입구 혹은 주최자 사무실에서 배부하여 드립니다.
                                    </li>
                                    <li>Badge를 착용하지 않은 인원은 전시장 입구에서 출입을 통제하게 되오니 정확히 파악하시어 제출하여 주시기 바랍니다.</li>
                                    <%--<li>2025 경기국제보트쇼 종료로 인해 <span class="txtRed">전시 정보 수정이 불가</span>합니다.</li>--%>
                                </ul>
                            </div>

                            <!-- 출입증 신청 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">출입증 신청</div>
                                    <div class="small">회사명 : ${exhibitorInfo.companyNameKo} (${exhibitorInfo.companyNameEn})</div>
                                </div>
                                <div class="form_tb form_pass form_add">
                                    <ul class="formPassHd formTbHd">
                                        <li class="passNum"><p>번호</p></li>
                                        <li class="passNameK req"><p>성명 (국문)</p></li>
                                        <li class="passNameE req"><p>성명 (영문)</p></li>
                                        <li class="passPosK req"><p>직책 (국문)</p></li>
                                        <li class="passPosE req"><p>직책 (영문)</p></li>
                                        <li class="passNote"><p>비고</p></li>
                                        <%--<li class="passDel"><p>삭제</p></li>--%>
                                    </ul>
                                    <c:if test="${empty passList}">
                                        <ul class="formPassBd formTbBd">
                                            <input type="hidden" name="passSeq" value="">
                                            <li class="passNum addNum">1</li>
                                            <li class="passNameK"><input type="text" name="pass_name" placeholder="성명" <%--disabled--%>></li>
                                            <li class="passNameE">
                                                <input type="text" name="pass_first_name" class="onlyNumEng w50" placeholder="이름" <%--disabled--%>>
                                                <input type="text" name="pass_last_name" class="onlyNumEng w50" placeholder="성" <%--disabled--%>>
                                            </li>
                                            <li class="passPosK"><input type="text" name="pass_position_ko" placeholder="직책(국문)" <%--disabled--%>></li>
                                            <li class="passPosE"><input type="text" name="pass_position_en" class="onlyNumEng" placeholder="직책(영문)" <%--disabled--%>></li>
                                            <li class="passNote"><input type="text" name="pass_note" placeholder="비고" <%--disabled--%>></li>
                                            <li class="passDel addDel"><img src="/img/icon_del.png"></li>
                                        </ul>
                                    </c:if>

                                    <c:if test="${not empty passList}">
                                        <c:forEach var="pass" items="${passList}" begin="0" end="${passList.size()}" step="1" varStatus="status">
                                        <ul class="formPassBd formTbBd">
                                            <input type="hidden" name="passSeq" value="${pass.seq}">
                                            <li class="passNum addNum">${status.index + 1}</li>
                                            <li class="passNameK"><input type="text" name="pass_name" value="${pass.passName}" placeholder="성명" <%--disabled--%>></li>
                                            <li class="passNameE">
                                                <input type="text" name="pass_first_name" class="onlyNumEng w50" value="${pass.passFirstName}" placeholder="이름" <%--disabled--%>>
                                                <input type="text" name="pass_last_name" class="onlyNumEng w50" value="${pass.passLastName}" placeholder="성" <%--disabled--%>>
                                            </li>
                                            <li class="passPosK"><input type="text" name="pass_position_ko" value="${pass.passPositionKo}" placeholder="직책(국문)" <%--disabled--%>></li>
                                            <li class="passPosE"><input type="text" name="pass_position_en" class="onlyNumEng" value="${pass.passPositionEn}" placeholder="직책(영문)" <%--disabled--%>></li>
                                            <li class="passNote"><input type="text" name="pass_note" value="${pass.passNote}" placeholder="비고" <%--disabled--%>></li>
                                            <li class="passDel addDel"><img src="/img/icon_del.png"></li>
                                        </ul>
                                        </c:forEach>
                                    </c:if>
                                    <div class="form_tb_btn form_add_btn">추가</div>
                                </div>

                            </div>

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">이전</div>
                                    <div class="small">유틸리티 신청</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_mypage_comp('2_4','${info.seq}')" class="form_btn_next">
                                    <div class="big">다음</div>
                                    <div class="small">경품제공 신청</div>
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