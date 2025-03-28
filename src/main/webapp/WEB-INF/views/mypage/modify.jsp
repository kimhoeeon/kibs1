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
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">

                            <!-- 회원계정정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">회원 계정 정보</div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item req">
                                            <p>아이디</p>
                                        </div>
                                        <div class="input">
                                            ${info.id}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>비밀번호 변경</p>
                                        </div>
                                        <div class="input">
                                            <input type="password" id="password" name="password" value="${info.password}" onchange="f_pw_status_change(this)" placeholder="비밀번호 입력" class="w50">
                                            <input type="button" onclick="f_pw_check(this)" style="margin-left: 10px;" value="비밀번호 유효성 검사">
                                            <div class="cmnt">8 ~ 16자의 영문, 숫자, 특수문자를 사용해 주세요.</div>
                                            <div class="cmnt">비밀번호 유효성을 검사해주세요.</div>
                                            <c:if test="${not empty info}"><input type="hidden" id="pwCheck" value="true"></c:if>
                                            <c:if test="${empty info}"><input type="hidden" id="pwCheck" value="false"></c:if>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>비밀번호 확인</p>
                                        </div>
                                        <div class="input">
                                            <input type="password" id="passwordCheck" onchange="f_pw_confirm_check(this)" placeholder="비밀번호 확인" class="w50">
                                            <div class="cmnt">비밀번호를 다시 입력해주세요.</div>
                                            <c:if test="${not empty info}"><input type="hidden" id="pwConfirmCheck" value="true"></c:if>
                                            <c:if test="${empty info}"><input type="hidden" id="pwConfirmCheck" value="false"></c:if>
                                        </div>
                                    </li>
                                    <li class="form_in_tit">대표담당자</li>
                                    <li>
                                        <div class="item req">
                                            <p>성명</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="name" class="w50" value="${info.name}" placeholder="성명">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>직위</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="position" name="position" value="${info.position}" placeholder="직위" class="w50">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>부서</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="depart" name="depart" value="${info.depart}" placeholder="부서" class="w50">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>이메일</p>
                                        </div>
                                        <div class="input email">
                                            <input type="email" id="email_input1" name="email1" value="${fn:split(info.email,'@')[0]}" placeholder="이메일" class="email_input1">
                                            <span>@</span>
                                            <input type="email" id="email_input2" name="email2" value="${fn:split(info.email,'@')[1]}" class="email_input2">
                                            <select id="email_select">
                                                <c:set var="domain" value="${fn:split(info.email,'@')[1]}"/>
                                                <option selected>직접입력</option>
                                                <option <c:if test="${domain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                <option <c:if test="${domain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                <option <c:if test="${domain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                <option <c:if test="${domain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                <option <c:if test="${domain eq 'hotmail.com'}">selected</c:if> >hotmail.com</option>
                                                <option <c:if test="${domain eq 'yahoo.co.kr'}">selected</c:if> >yahoo.co.kr</option>
                                                <option <c:if test="${domain eq 'empal.com'}">selected</c:if> >empal.com</option>
                                                <option <c:if test="${domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                <option <c:if test="${domain eq 'hanmir.com'}">selected</c:if> >hanmir.com</option>
                                                <option <c:if test="${domain eq 'dreamwiz.com'}">selected</c:if> >dreamwiz.com</option>
                                                <option <c:if test="${domain eq 'orgio.net'}">selected</c:if> >orgio.net</option>
                                                <option <c:if test="${domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                <option <c:if test="${domain eq 'hitel.net'}">selected</c:if> >hitel.net</option>
                                            </select>
                                            <div class="check mktCheck">
                                                <p>E-mail 마케팅정보 수신동의</p>
                                                <label><input type="radio" name="email_marketing_yn" value="Y" <c:if test="${info.emailMarketingYn eq 'Y'}">checked</c:if>>수신동의</label>
                                                <label><input type="radio" name="email_marketing_yn" value="N" <c:if test="${info.emailMarketingYn eq 'N'}">checked</c:if>>동의 안함</label>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item req">
                                            <p>전화번호</p>
                                        </div>
                                        <div class="input">
                                            <input type="tel" id="tel" name="tel" value="${info.tel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item req">
                                            <p>휴대전화</p>
                                        </div>
                                        <div class="input">
                                            <input type="tel" id="phone" name="phone" value="${info.phone}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <!-- 회원계정정보 -->

                            <div class="modify_btn_box">
                                <a onclick="f_personal_info_save('${info.seq}')" class="btn_save btnSt01" style="cursor: pointer;">저장</a>
                                <%--<a href="#" class="btn_out btnSt02 memberOut">탈퇴하기</a>--%>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="popMemberOut popup">
            <div class="popup_inner">
                <div class="popup_wrap">
                    <div class="pop_tit">
                        <div class="tit">회원 탈퇴 안내</div>
                        <a class="close" href="#close"><img src="/img/close_w.png"></a>
                    </div>
                    <div class="pop_box">
                        <div class="pop_cont">
                            <!-- 탈퇴안내 -->
                            <ul class="out_guide">
                                <li>사용하고 계신 아이디(meetingfan)는 탈퇴할 경우 재사용 및 복구가 불가능합니다.</li>
                                <li>탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가능하오니 신중하게 선택하시기 바랍니다.</li>
                                <li>탈퇴 후 회원정보 및 서비스 이용기록은 모두 삭제됩니다.</li>
                            </ul>
                            <div class="out_check">
                                <label>
                                    <input type="checkbox">위 안내 사항을 모두 확인하였으며, 이에 동의합니다.
                                </label>
                            </div>
                            <div class="btn_box">
                                <a href="" class="btnSt01">탈퇴하기</a>
                                <a href="" class="close btnSt03">취소</a>
                            </div>
                            <!-- //탈퇴안내 -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    </c:if>

</body>
</html>