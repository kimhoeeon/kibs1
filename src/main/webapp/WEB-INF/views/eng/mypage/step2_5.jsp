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
                                <li class="active"><a href="/eng/mypage/step2_5.do">Prize & Giveaway</a></li>
                                <li><a href="/eng/mypage/step2_6.do">Web Banner</a></li>
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
                    <div class="apply_tit">Prize & Giveaway</div>
                    <!-- 경품 관련 안내사항 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">A Prize Announcement</div>
                        </div>
                        <div class="form_guide_table table_box">
                            <table>
                                <colgroup>
                                    <col width="15%">
                                    <col width="35%">
                                    <col width="50%">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>Sortation</th>
                                    <th>Content</th>
                                    <th>Activity Details</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>An Event Prize</td>
                                    <td>
                                        <p>2/28~3/2 For prize draw during the boat show event</p>
                                    </td>
                                    <td>
                                        <p>Insert the company logo of the prize sponsor into the application box and the
                                            application ticket distributed to all visitors</p>
                                        <p>Exposing the history of giving prizes to various media/media</p>
                                        <p>Considering the exhibition period of 3 days, I would like to sponsor with 3
                                            times the number of prizes</p>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- 경품제공 신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">An Application For A Prize</div>
                        </div>
                        <div class="form_chuga">
                            <div class="form_chuga_list">
                                <div class="cont">
                                    <div class="name">Gift Name</div>
                                    <div class="gubun">for use in events / 20</div>
                                </div>
                                <div href="#" class="modifyFormGift modifyFormList">Modify</div>
                                <div href="#" class="delFormGift delFormList">Delete</div>
                            </div>
                            <div class="form_chuga_list">
                                <div class="cont">
                                    <div class="name">Gift Name</div>
                                    <div class="gubun">for use in events / 20</div>
                                </div>
                                <div href="#" class="modifyFormGift modifyFormList">Modify</div>
                                <div href="#" class="delFormGift delFormList">Delete</div>
                            </div>
                            <a href="#" class="addFormGift addFormList btnSt01">Click here to register your prize</a>
                        </div>
                    </div>
                </div>

                <div class="form_btn">
                    <a href="/eng/mypage/step2_4.do#apply_s" class="form_btn_prev">
                        <div class="big">PREV</div>
                        <div class="small">Exhibitor Pass</div>
                    </a>
                    <a href="/eng/mypage/step2_6.do#apply_s" class="form_btn_next">
                        <div class="big">NEXT</div>
                        <div class="small">Web Banner</div>
                    </a>
                </div>

            </div>
        </div>
        <!-- //section -->
    </div>
</div>

</div>

<!-- 경품등록팝업 -->
<div class="popFormGift popForm popup">
    <div class="popup_inner">
        <div class="popup_wrap">
            <div class="pop_tit">
                <div class="tit">Register/Modify Your Application For A Prize</div>
                <a class="close" href="#close"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- 폼박스 -->
                    <div class="form_s">
                        <!-- 회원계정정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">An Application For A Prize</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>Sortation</p>
                                    </div>
                                    <div class="input">
                                        <label><input type="radio" checked>For use in events</label>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Quantity</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" class="onlyNum">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Classification</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Item</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Description</p>
                                    </div>
                                    <div class="input">
                                        <textarea></textarea>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>A Prize Photo</p>
                                    </div>
                                    <div class="input file_box">
                                        <input type="text" class="upload_name" value="" disabled="disabled">
                                        <input type="file" id="img_file1" class="upload_hidden">
                                        <label for="img_file1">Upload</label>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Company Logo</p>
                                    </div>
                                    <div class="input file_box">
                                        <input type="text" class="upload_name" value="" disabled="disabled">
                                        <input type="file" id="img_file2" class="upload_hidden">
                                        <label for="img_file2">Upload</label>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Retail Price</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Sponsored Price</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Note</p>
                                    </div>
                                    <div class="input">
                                        <textarea></textarea>
                                    </div>
                                </li>
                            </ul>
                            <a class="btnSave" href="#">Registration/Modification</a>
                        </div>
                    </div>
                    <!-- //폼박스 -->
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>