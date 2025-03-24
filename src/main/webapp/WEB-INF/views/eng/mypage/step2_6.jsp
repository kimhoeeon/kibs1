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
                    <span>Home</span><span>Exhibitors</span><span>My Page</span>
                </div>
                <div class="sub_top_tit">My Page</div>
            </div>
        </div>
    </div>
    <!-- //section -->


    <!-- section -->
    <div class="apply_s padding_tb" id="apply_s">
        <div class="inner">

            <div class="apply_nav">
                <div class="apply_nav_list">
                    <div class="apply_nav_txt">
                        <div class="txt">Welcome, Meetingfan!</div>
                        <div class="btn">
                            <a href="/eng/mypage/modify.do" class="btnSt01">Member Info</a>
                            <a href="/eng/mypage/index.do" class="btnSt01">My Page</a>
                        </div>
                    </div>
                    <ul class="list1">
                        <li><a href="/eng/mypage/step01.do">Basic Information</a></li>
                        <li class="active">
                            <a href="/eng/mypage/step2_1.do">Exhibition Req.</a>
                            <ul class="list2">
                                <li><a href="/eng/mypage/step2_1.do">Exhibitor Booth</a></li>
                                <li><a href="/eng/mypage/step2_2.do">Fascia Board Title</a></li>
                                <li><a href="/eng/mypage/step2_3.do">Utilities</a></li>
                                <li><a href="/eng/mypage/step2_4.do">Exhibitor Pass</a></li>
                                <li><a href="/eng/mypage/step2_5.do">Prize & Giveaway</a></li>
                                <li class="active"><a href="/eng/mypage/step2_6.do">Web Banner</a></li>
                                <%--<li><a href="/eng/mypage/step2_7.do">Online Exhibition</a></li>--%>
                                <li><a href="/eng/mypage/step2_8.do">Directory</a></li>
                            </ul>
                        </li>
                        <li><a href="/eng/mypage/step03.do">Complete Registration</a></li>
                    </ul>
                </div>
            </div>


            <!-- section -->
            <div class="my_form form_s">
                <div class="inner">
                    <div class="apply_tit">Web Banner</div>
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Web Banner Guide</div>
                        </div>
                        <ul class="form_guide">
                            <li>Your banner will be posted on the main page of the Gyeonggi International Boat Show
                                website(www.kibs.com).
                            </li>
                            <li><span class="em">Please send a high-resolution promotional image file(jpg/png) and a company logo AI file that meets the minimum standard(1200px X 1000px) for the yellow box.</span>
                            </li>
                            <li>Banner will be posted on a first-come, first-served basis.</li>
                            <li>Banner that is difficult to recognize due to low resolution cannot be posted.</li>
                        </ul>
                    </div>

                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Web Banner Publishing Example</div>
                        </div>
                        <div class="form_guide_img">
                            <img src="/img/web_b_ex.jpg">
                        </div>
                    </div>

                    <!-- 부스신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Web Banner Application</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item">
                                    <p>Content Text</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>Attach Image</p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file1" class="upload_hidden">
                                    <label for="img_file1">Upload</label>
                                    <div class="cmnt">Please make sure to upload the image as the original (without the logo)</div>
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>Attach Logo</p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file2" class="upload_hidden">
                                    <label for="img_file2">Upload</label>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div class="form_btn">
                        <a href="/eng/mypage/step2_5.do#apply_s" class="form_btn_prev">
                            <div class="big">PREV</div>
                            <div class="small">Prize & Giveaway</div>
                        </a>
                        <a href="/eng/mypage/step2_6.do#apply_s" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Directory</div>
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