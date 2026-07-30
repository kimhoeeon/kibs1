<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Kibs 2027">
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
    <title>2027 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <jsp:include page="../../inc/common_css.jsp" flush="true"/>
    <jsp:include page="../../inc/common_js_en.jsp" flush="true"/>
    <jsp:include page="../../inc/ad_scripts.jsp" flush="true"/>

    <span itemscope="" itemtype="http://schema.org/Organization">
        <link itemprop="url" href="https://kibs.com/">
        <a itemprop="sameAs" href="https://koreaboatshow.or.kr/"></a>
        <a itemprop="sameAs" href="https://koreaboatshow.re.kr/"></a>
        <a itemprop="sameAs" href="https://kibs-online.com"></a>
        <a itemprop="sameAs" href="https://www.youtube.com/channel/UCvcRu_g4M1MOIIuJyllR6Rw"></a>
        <a itemprop="sameAs" href="https://www.youtube.com/@KIBSKINTEX"></a>
    </span>
</head>

<body>

    <c:if test="${sessionScope.get('status') ne 'logon'}">
        <script>
            alert("Please log in.");
            location.href = '/eng/login.do';
        </script>
    </c:if>

    <c:if test="${sessionScope.get('status') eq 'logon'}">

        <c:import url="../header.jsp" charEncoding="UTF-8"/>

        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>Home</span><span>Exhibitors</span><span>Mypage</span>
                        </div>
                        <div class="sub_top_tit">Mypage</div>
                    </div>
                </div>
            </div>
            <!-- //section -->

            <!-- section -->
            <div class="apply_s padding_tb" id="apply_s">
                <div class="inner">

                    <div class="apply_nav">
                        <div class="apply_nav_txt">
                            <div class="txt">Welcome, ${info.companyNameEn}</div>
                            <div class="btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/modify.do','${info.seq}')" class="btnSt01">Member Info</a>
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/index.do', {seq: `${info.seq}`})" class="btnSt01">MYPAGE</a>
                            </div>
                        </div>
                        <div class="apply_nav_list">
                            <ul class="list1">
                                <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step01.do','${info.seq}')">Basic Info</a></li>
                                <li>
                                    <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Exhibition Info</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Booth</a></li>
                                            <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_10.do','${info.seq}')">해상전시회 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_9.do','${info.seq}')">Yacht/Boat</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_2.do','${info.seq}')">Signboard</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_3.do','${info.seq}')">Utilities</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_4.do','${info.seq}')">Badges</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_5.do','${info.seq}')">Giveaways</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_8.do','${info.seq}')">Directory</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step03.do','${info.seq}')">Complete</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">

                            <!-- 회원계정정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Account Information</div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item req">
                                            <p>ID</p>
                                        </div>
                                        <div class="input">${info.id}</div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>Password</p>
                                        </div>
                                        <div class="input">
                                            <input type="password" id="password" name="password" value="Encrypted passwords cannot be copied" onchange="f_pw_status_change(this)" placeholder="비밀번호 입력" class="w50">
                                            <input type="button" onclick="f_pw_check(this)" style="margin-left: 10px;" value="Password validation">
                                            <div class="cmnt">Please use 8~16 characters of English, numbers, or special characters.</div>
                                            <div class="cmnt">Please validate your password.</div>
                                            <c:if test="${not empty info}"><input type="hidden" id="pwCheck" value="true"></c:if>
                                            <c:if test="${empty info}"><input type="hidden" id="pwCheck" value="false"></c:if>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>Confirm Password</p>
                                        </div>
                                        <div class="input">
                                            <input type="password" id="passwordCheck" onchange="f_pw_confirm_check(this)" placeholder="Confirm your password" class="w50">
                                            <div class="cmnt">Please enter your password again.</div>
                                            <c:if test="${not empty info}"><input type="hidden" id="pwConfirmCheck" value="true"></c:if>
                                            <c:if test="${empty info}"><input type="hidden" id="pwConfirmCheck" value="false"></c:if>
                                        </div>
                                    </li>
                                    <li class="form_in_tit">Representative Person in Charge</li>
                                    <li>
                                        <div class="item req">
                                            <p>Name</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="name" class="w50" value="${info.name}" placeholder="Name">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>Position</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="position" name="position" value="${info.position}" placeholder="Position" class="w50">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>Department</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="depart" name="depart" value="${info.depart}" placeholder="Department" class="w50">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item req">
                                            <p>Tel</p>
                                        </div>
                                        <div class="input">
                                            <input type="tel" id="tel" name="tel" value="${info.tel}" class="onlyGeneralTel" maxlength="13" placeholder="Please enter only the numbers.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item req">
                                            <p>Phone</p>
                                        </div>
                                        <div class="input">
                                            <input type="tel" id="phone" name="phone" value="${info.phone}" class="onlyTel" maxlength="13" placeholder="Please enter only the numbers.">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>E-mail</p>
                                        </div>
                                        <div class="input email">
                                            <input type="email" id="email_input1" name="email1" value="${fn:split(info.email,'@')[0]}" placeholder="E-mail" class="email_input1">
                                            <span>@</span>
                                            <input type="email" id="email_input2" name="email2" value="${fn:split(info.email,'@')[1]}" class="email_input2">
                                            <select id="email_select">
                                                <c:set var="domain" value="${fn:split(info.email,'@')[1]}"/>
                                                <option selected>Direct Input</option>
                                                <option <c:if test="${domain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                <option <c:if test="${domain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                <option <c:if test="${domain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                <option <c:if test="${domain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                <option <c:if test="${domain eq 'gmail.com'}">selected</c:if> >gmail.com</option>
                                            </select>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <!-- 회원계정정보 -->

                            <div class="modify_btn_box">
                                <a onclick="f_personal_info_save('${info.seq}')" class="btn_save btnSt01" style="cursor: pointer;">Save</a>
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
                        <div class="tit">Notice of withdrawal</div>
                        <a class="close" href="#close"><img src="/img/close_w.png"></a>
                    </div>
                    <div class="pop_box">
                        <div class="pop_cont">
                            <!-- 탈퇴안내 -->
                            <ul class="out_guide">
                                <li>The ID( ) currently in use cannot be reused or restored if you withdraw.</li>
                                <li>The withdrawn ID cannot be reused or restored by both you and others, so please choose
                                    carefully.
                                </li>
                                <li>After withdrawal, all member information and service usage records are deleted.</li>
                            </ul>
                            <div class="out_check">
                                <label>
                                    <input type="checkbox">I've checked all of the above instructions and agree to them.
                                </label>
                            </div>
                            <div class="btn_box">
                                <a href="" class="btnSt01">Withdrawal</a>
                                <a href="" class="close btnSt03">Cancel</a>
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