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
                                <li><a href="/eng/mypage/step2_6.do">Web Banner</a></li>
                                <%--<li><a href="/eng/mypage/step2_7.do">Online Exhibition</a></li>--%>
                                <li class="active"><a href="/eng/mypage/step2_8.do">Directory</a></li>
                            </ul>
                        </li>
                        <li><a href="/eng/mypage/step03.do">Complete Registration</a></li>
                    </ul>
                </div>
            </div>


            <!-- section -->
            <div class="my_form form_s">
                <div class="inner">
                    <div class="apply_tit">Directory</div>
                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>This form must be applied by all participating companies and is free of charge.</li>
                            <li>The listing company directory is not produced, but it will be used as a promotional
                                material, so please enter it without exception.
                            </li>
                        </ul>
                    </div>

                    <div class="load_btn">
                        <button type="button" onclick="">Get input information</button>
                    </div>

                    <!-- 참가업체 정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Information of Participating Company</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>Name of Company</p>
                                </div>
                                <div class="input">
                                    <input type="text" placeholder="" class="w50">
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Address</p>
                                </div>
                                <div class="input address">
                                    <div class="address_box">
                                        <input type="text" id="address" class="w50" placeholder="Address">
                                        <input type="button" onclick="execDaumPostcode()" value="Search"><br>
                                        <div id="map" style="width:300px;height:300px;margin-top:10px;display:none">
                                        </div>
                                    </div>
                                    <div class="address_box" style="margin-top: 10px;">
                                        <input type="text" id="addressDetail" placeholder="Detailed Address" class="w50">
                                    </div>
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Representative</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Telephone</p>
                                </div>
                                <div class="input">
                                    <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Homepage</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Fax</p>
                                </div>
                                <div class="input">
                                    <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Business License</p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file" class="upload_hidden">
                                    <label for="img_file">Upload</label>
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Business Number</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="form_in_tit">SNS (URL)</li>
                            <li class="w50">
                                <div class="item">
                                    <p>Blog</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Facebook</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Instagram</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>ETC</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                        </ul>
                    </div>

                    <!-- 담당자 정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Contact Information</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>Name</p>
                                </div>
                                <div class="input">
                                    <input type="text" class="w50">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Position</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Department</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Telephone</p>
                                </div>
                                <div class="input">
                                    <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Mobile Phone</p>
                                </div>
                                <div class="input">
                                    <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>E-mail</p>
                                </div>
                                <div class="input email">
                                    <input type="email" placeholder="E-mail" class="email_input1">
                                    <span>@</span>
                                    <input type="email" class="email_input2">
                                    <select>
                                        <option>Direct input</option>
                                        <option>daum.net</option>
                                        <option>nate.com</option>
                                        <option>hanmail.net</option>
                                        <option>naver.com</option>
                                        <option>hotmail.com</option>
                                        <option>yahoo.co.kr</option>
                                        <option>empal.com</option>
                                        <option>korea.com</option>
                                        <option>hanmir.com</option>
                                        <option>dreamwiz.com</option>
                                        <option>orgio.net</option>
                                        <option>korea.com</option>
                                        <option>hitel.net</option>
                                    </select>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <!-- 전시정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Category / Item of Business</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>Exhibition Item</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Exhibits' Brand(s)</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Number of Boat(s) & Yacht(s)</p>
                                </div>
                                <div class="input">
                                    <input type="text">
                                </div>
                            </li>
                        </ul>
                    </div>

                    <!-- 상세정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Exhibitor Profile</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>Company Profile</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Purpose of Participation</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div class="form_btn">
                        <a href="/eng/mypage/step2_6.do#apply_s" class="form_btn_prev">
                            <div class="big">PREV</div>
                            <div class="small">Web Banner</div>
                        </a>
                        <a href="/eng/mypage/step03.do#apply_s" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Complete Registration</div>
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