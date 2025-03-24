<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>2025 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
</head>

<body>
<c:import url="../header.jsp" charEncoding="UTF-8"/>

<div id="container">

    <!-- section -->
    <div class="sub_top">
        <div class="inner">
            <div class="sub_top_box">
                <div class="sub_top_nav">
                    <span>Home</span><span>Exhibitors</span><span>Exhibitor Registration</span>
                </div>
                <div class="sub_top_tit">Exhibitor Registration</div>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="sub_tab">
        <div class="inner">
            <div class="sub_tab_box">
                <a href="" class="iconHome"><img src="/img/icon_home.png"></a>
                <div class="optionBox">
                    <div class="tabOpt1 tabOption">
                        <div class="tabOptAct">Exhibitors</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2025</a>
                            <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a href="/eng/buyer/apply.do">Buyer</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Exhibitors Registration</div>
                        <div class="tabOptSel">
                            <a href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                            <a href="/eng/exhibitor/list.do">Exhibitor List</a>
                            <a class="active" href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>">Exhibitor Registration</a>
                            <a href="/eng/exhibitor/qna.do">QnA</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="apply_s padding_tb" id="apply_s">
        <div class="inner">

            <div class="apply_nav">
                <div class="apply_nav_list">
                    <ul class="list1">
                        <li><a href="/eng/apply/step01.do">Basic Information</a></li>
                        <li class="active">
                            <a href="/eng/apply/step2_1.do">Exhibition Req.</a>
                            <ul class="list2">
                                <li><a href="/eng/apply/step2_1.do">Exhibitor Booth</a></li>
                                <li><a href="/eng/apply/step2_2.do">Fascia Board Title</a></li>
                                <li><a href="/eng/apply/step2_3.do">Utilities</a></li>
                                <li class="active"><a href="/eng/apply/step2_4.do">Exhibitor Pass</a></li>
                                <li><a href="/eng/apply/step2_5.do">Prize & Giveaway</a></li>
                                <li><a href="/eng/apply/step2_6.do">Web Banner</a></li>
                                <%--<li><a href="/eng/apply/step2_7.do">Online Exhibition</a></li>--%>
                                <li><a href="/eng/apply/step2_8.do">Directory</a></li>
                            </ul>
                        </li>
                        <li><a href="/eng/apply/step03.do">Complete Registration</a></li>
                    </ul>
                </div>
            </div>

            <div class="apply_step">
                <div class="apply_tit">Exhibitor Pass</div>
                <div class="apply_step_wrap">
                    <div class="apply_step_box step1">
                        <div class="gubun">
                            <p>Step 1</p>Basic Information
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="100" max="100"></progress>
                        </div>
                        <div class="txt">Complete</div>
                    </div>
                    <div class="apply_step_box step2">
                        <div class="gubun">
                            <p>Step 2</p>Exhibition Req.
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="30" max="80"></progress>
                        </div>
                        <div class="txt">Proceeding...(3/7)</div>
                    </div>
                    <div class="apply_step_box step3">
                        <div class="gubun">
                            <p>Step 3</p>Complete Registration
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="0" max="100"></progress>
                        </div>
                        <div class="txt">Condition Not Met</div>
                    </div>
                </div>
            </div>

            <!-- section -->
            <div class="apply_form form_s padding_t">
                <div class="inner">

                    <!-- 안내문구 -->
                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>Please submit the name and position of your resident agent who will reside in the
                                exhibition hall. During the construction period and the exhibition period, a badge that
                                allows visitors to enter the exhibition hall will be made and distributed at the
                                entrance of the exhibition hall or at the organizer's office. (This form is applicable
                                to all companies participating in the exhibition.)
                            </li>
                            <li>Those who do not wear a badge are restricted from entering the exhibition hall, so
                                please check and submit it in advance.
                            </li>
                        </ul>
                    </div>

                    <!-- 출입증 신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Accredition Card Application</div>
                            <div class="small">Company Name : meetingfan</div>
                        </div>
                        <div class="form_tb form_pass form_add form_pass_eng">
                            <ul class="formPassHd formTbHd">
                                <li class="passNum"><p>Number</p></li>
                                <li class="passNameE req"><p>Name (Eng.)</p></li>
                                <li class="passPosE req"><p>Job title (Eng.)</p></li>
                                <li class="passNote"><p>Note</p></li>
                                <li class="passDel"><p>Delete</p></li>
                            </ul>
                            <ul class="formPassBd formTbBd">
                                <li class="passNum addNum">1</li>
                                <li class="passNameE">
                                    <input type="text" class="onlyNumEng w50" placeholder="First Name">
                                    <input type="text" class="onlyNumEng w50" placeholder="Surname">
                                </li>
                                <li class="passPosE"><input type="text" class="onlyNumEng"></li>
                                <li class="passNote"><input type="text"></li>
                                <li class="passDel addDel"><img src="/img/icon_del.png"></li>
                            </ul>
                            <div class="form_tb_btn form_add_btn">Add More</div>
                        </div>

                    </div>

                    <div class="form_btn">
                        <a href="/eng/apply/step2_3.do#apply_s" class="form_btn_prev">
                            <div class="big">PREV</div>
                            <div class="small">Utilities</div>
                        </a>
                        <a href="/eng/apply/step2_5.do#apply_s" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Prize & Giveaway</div>
                        </a>
                    </div>

                </div>
            </div>
            <!-- //section -->
        </div>
    </div>

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>