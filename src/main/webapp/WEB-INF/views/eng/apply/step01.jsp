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
                        <div class="tabOptAct">Exhibitor Registration</div>
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
                        <li class="active"><a href="/eng/apply/step01.do">Basic Information</a></li>
                        <li>
                            <a href="/eng/apply/step2_1.do">Exhibition Req.</a>
                            <ul class="list2">
                                <li><a href="/eng/apply/step2_1.do">Exhibitor Booth</a></li>
                                <li><a href="/eng/apply/step2_2.do">Fascia Board Title</a></li>
                                <li><a href="/eng/apply/step2_3.do">Utilities</a></li>
                                <li><a href="/eng/apply/step2_4.do">Exhibitor Pass</a></li>
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
                <div class="apply_tit">Basic Information</div>
                <div class="apply_step_wrap">
                    <div class="apply_step_box step1">
                        <div class="gubun">
                            <p>Step 1</p>Basic Information
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="5" max="100"></progress>
                        </div>
                        <div class="txt">Proceeding...</div>
                    </div>
                    <div class="apply_step_box step2">
                        <div class="gubun">
                            <p>Step 2</p>Exhibition Req.
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="0" max="100"></progress>
                        </div>
                        <div class="txt">Condition Not Met</div>
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

                    <!-- 회원계정정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Account Information</div>
                            <div class="small">ID and your password is essential when facilities and other application
                                registration and participation application that you want to modify.
                            </div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>ID</p>
                                </div>
                                <div class="input">
                                    <input type="text" placeholder="ID" class="w50">
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Password</p>
                                </div>
                                <div class="input">
                                    <input type="password" placeholder="Choose Password" class="w50">
                                    <div class="cmnt">Please make your password With 8 characters, a mix of alphabets,
                                        special characters, and numbers.
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Confirm Password</p>
                                </div>
                                <div class="input">
                                    <input type="password" placeholder="Confirm Password" class="w50">
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Name</p>
                                </div>
                                <div class="input">
                                    <input type="text" class="w50">
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
                                    <div class="check mktCheck">
                                        <p>E-mail marketing consent</p>
                                        <label><input type="radio" name="mktC" id="mktCY">Agree</label>
                                        <label><input type="radio" name="mktC" id="mktCN">Disagree</label>
                                    </div>
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
                        </ul>
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
                                    <input type="text">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
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
                                    <input type="tel" class="onlyNumh" placeholder="Please include a hyphen (-)">
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
                                <div class="item">
                                    <p>Fax</p>
                                </div>
                                <div class="input">
                                    <input type="tel" class="onlyNumh" placeholder="Please include a hyphen (-)">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Business License</p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file1" class="upload_hidden">
                                    <label for="img_file1">Upload</label>
                                    <div class="cmnt">PDF, JPG, PNG format, 10MB or less only.</div>
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Business Number</p>
                                </div>
                                <div class="input">
                                    <input type="text" class="onlyNumh" placeholder="Please include a hyphen (-)">
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>Year of participation year</p>
                                </div>
                                <div class="input check">
                                    <label><input type="checkbox" name="partYear">One's first participation</label>
                                    <label><input type="checkbox" name="partYear">2016</label>
                                    <label><input type="checkbox" name="partYear">2017</label>
                                    <label><input type="checkbox" name="partYear">2018</label>
                                    <label><input type="checkbox" name="partYear">2019</label>
                                    <label><input type="checkbox" name="partYear">2020</label>
                                    <label><input type="checkbox" name="partYear">2021</label>
                                    <label><input type="checkbox" name="partYear">2022</label>
                                    <label><input type="checkbox" name="partYear">2023</label>
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>Willingness to participate</p>
                                </div>
                                <div class="input check">
                                    <label><input type="radio" name="memberKm" checked>Willing to continue to participate in boat shows</label>
                                    <label><input type="radio" name="memberKm">Not Applicable</label>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Member of Korea Marine Industry Association</p>
                                </div>
                                <div class="input check">
                                    <label><input type="radio" name="memberKm">Yes</label>
                                    <label><input type="radio" name="memberKm">No</label>
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
                            <div class="small">Up to 3 people can register</div>
                        </div>
                        <ul class="form_box managerInfoBox">
                            <li class="form_in_tit">
                                Contact Information #<span class="managerInfoNum">1</span>
                                <span class="del_btn managerInfoDel">Delete</span>
                            </li>
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
                        <div class="formAddBtn"><span class=" managerInfoAdd">Add</span></div>
                    </div>

                    <!-- 참가분야 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Participation Categories</div>
                            <div class="small">Up to 3 duplicate checks available</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>Participation Categories</p>
                                </div>
                                <div class="input check">
                                    <label><input type="radio" name="fieldPart">Yacht & Boat World</label>
                                    <label><input type="radio" name="fieldPart">Paddler’s World</label>
                                    <label><input type="radio" name="fieldPart">Workboat World</label>
                                    <label><input type="radio" name="fieldPart">Marine Leisure</label>
                                    <label><input type="radio" name="fieldPart">Caravan Show</label>
                                    <%--<label><input type="radio" name="fieldPart">Aramarina Education and Experience Program</label>--%>
                                    <label><input type="radio" name="fieldPart">Marine Equipment,Safety & Marina Industry Show</label>
                                    <%--<label><input type="radio" name="fieldPart">Eco Friendly Marine</label>--%>
                                    <label><input type="radio" name="fieldPart">Marine Tourism</label>
                                    <%--<label><input type="radio" name="fieldPart">Boat Maintenance</label>--%>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <!-- 전시정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Category / Item of Business</div>
                        </div>
                        <ul class="form_box exhiInfoBox">
                            <li class="form_in_tit">
                                Category / Item of Business #<span class="exhiInfoNum">1</span>
                                <span class="del_btn exhiInfoDel">Delete</span>
                            </li>
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
                        <div class="formAddBtn"><span class="exhiInfoAdd">Add</span></div>
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
                            <li>
                                <div class="item req">
                                    <p>Introduction of Exhibiting Products</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>Introduction of New Product</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Introduction of Exhibiting Products</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Plan For Prizes / Events</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Special Requests</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Logo</p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file_logo" class="upload_hidden">
                                    <label for="img_file_logo">Upload</label>
                                    <div class="cmnt">JPG, PNG format, 10MB or less only. (Recommended size : 150x150px)</div>
                                </div>
                            </li>
                            <li class="form_in_tit">PR image</li>
                            <li class="proImgBox">
                                <div class="item req">
                                    <p>PR image <span class="proImgNum">1</span></p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file_promotion1" class="upload_hidden">
                                    <label for="img_file_promotion1">Upload</label>
                                    <span class="proImgDel file_box_del" >Delete</span>
                                    <div class="cmnt">JPG, PNG format, 10MB or less only. (Recommended size : 1000x250px)</div>
                                </div>
                            </li>
                            <li class="proImgBox">
                                <div class="item req">
                                    <p>PR image <span class="proImgNum">2</span></p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file_promotion2" class="upload_hidden">
                                    <label for="img_file_promotion2">Upload</label>
                                    <span class="proImgDel file_box_del" >Delete</span>
                                    <div class="cmnt">JPG, PNG format, 10MB or less only.</div>
                                </div>
                            </li>
                            <li class="proImgBox">
                                <div class="item req">
                                    <p>PR image <span class="proImgNum">3</span></p>
                                </div>
                                <div class="input file_box">
                                    <input type="text" class="upload_name" value="" disabled="disabled">
                                    <input type="file" id="img_file_promotion3" class="upload_hidden">
                                    <label for="img_file_promotion3">Upload</label>
                                    <span class="proImgDel file_box_del" >Delete</span>
                                    <div class="cmnt">JPG, PNG format, 10MB or less only.</div>
                                </div>
                            </li>
                        </ul>
                        <div class="formAddBtn"><span class="proImgAdd">Add</span></div>
                    </div>

                    <!-- 온라인 전시관 정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">About The Online Exhibition</div>
                        </div>
                        <!-- 230810 추가 -->
                        <ul class="form_box">
                            <li class="form_in_tit">Company#1</li>
                            <li>
                                <div class="item">
                                    <p>Company introduction video</p>
                                </div>
                                <div class="input">
                                    <input type="text" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                    <div class="cmnt">Only videos uploaded to YouTube can be posted.</div>
                                </div>
                            </li>
                        </ul>
                        <!-- //230810 추가 -->
                        <ul class="form_box onlineInfoBox">
                            <li class="form_in_tit">
                                The Product #<span class="onlineInfoNum">1</span>
                                <span class="del_btn onlineInfoDel">Delete</span>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Product Photo</p>
                                </div>
                                <div class="input file_box">
                                    <div class="cmnt2">Up to 5 can be registered</div>
                                    <div class="onlinePrdBox">
                                        <input type="text" class="upload_name" value="" disabled="disabled">
                                        <input type="file" id="img_file_online" class="upload_hidden">
                                        <label for="img_file_online">Upload</label>
                                        <span class="onlinePrdAdd" style="cursor: pointer">Add</span>
                                        <span class="onlinePrdDel" style="cursor: pointer">Delete</span>
                                        <div class="cmnt">JPG, PNG format, 10MB or less only. (Recommended size : 1000x750px)</div>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item req">
                                    <p>Product Description</p>
                                </div>
                                <div class="input">
                                    <textarea></textarea>
                                </div>
                            </li>
                            <!-- 230810 추가 -->
                            <li>
                                <div class="item">
                                    <p>Product video</p>
                                </div>
                                <div class="input">
                                    <input type="text" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                    <div class="cmnt">Only videos uploaded to YouTube can be posted.</div>
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>the whole length(m)</p>
                                </div>
                                <div class="input">
                                    <input type="text" class="onlyNumDec">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>horsepower</p>
                                </div>
                                <div class="input">
                                    <input type="text" class="onlyNumDec">
                                </div>
                            </li>
                            <!-- //230810 추가 -->
                        </ul>
                        <div class="formAddBtn"><span class="onlineInfoAdd">Add</span></div>
                    </div>

                    <!-- 수출상담회 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">An Export Consultation Meeting</div>
                            <div class="small">The 2025 Gyeonggi International Boat Show will invite domestic and
                                foreign buyers and hold 1:1 export consultations.
                            </div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item req">
                                    <p>Whether You Wish To Participate</p>
                                </div>
                                <div class="input check">
                                    <label><input type="radio" name="fieldPart" id="fieldPartY">Participation</label>
                                    <label><input type="radio" name="fieldPart" id="fieldPartN" checked>Not
                                        participating</label>
                                </div>
                            </li>
                        </ul>

                        <div class="form_chuga form_add_buyer">
                            <ul class="form_box" style="border-top:0;">
                                <li class="form_in_tit">Buyer Invitation</li>
                            </ul>
                            <div class="form_chuga_list">
                                <div class="cont">
                                    <div class="name">프리미어케어</div>
                                    <div class="gubun">대한민국 / 기획팀 홍길동 팀장</div>
                                </div>
                                <div href="#" class="modifyFormBuyer modifyFormList">Modify</div>
                                <div href="#" class="delFormBuyer delFormList">Delete</div>
                            </div>
                            <div class="form_chuga_list">
                                <div class="cont">
                                    <div class="name">프리미어케어</div>
                                    <div class="gubun">대한민국 / 기획팀 홍길동 팀장</div>
                                </div>
                                <div href="#" class="modifyFormBuyer modifyFormList">Modify</div>
                                <div href="#" class="delFormBuyer delFormList">Delete</div>
                            </div>
                            <a href="#" class="addFormBuyer addFormList btnSt01">Click here to register your Buyer</a>
                        </div>
                    </div>


                    <div class="form_btn" style="justify-content:flex-end;">
                        <a href="/eng/apply/step2_1.do#apply_s" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Exhibitor Booth</div>
                        </a>
                    </div>

                </div>
            </div>
            <!-- //section -->
        </div>
    </div>

</div>

<!-- 초청 희망 바이어 팝업 -->
<div class="popFormBuyer popForm popup">
    <div class="popup_inner">
        <div class="popup_wrap">
            <div class="pop_tit">
                <div class="tit">Register/Modify Invitation Desired</div>
                <a class="close" href="#close"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- 폼박스 -->
                    <div class="form_s">
                        <!-- 회원계정정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Buyer Information</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>Corporate Name</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Nation</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Location</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Homepage</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Department</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Position</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
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
                                <li>
                                    <div class="item">
                                        <p>Telephone</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Phone Number</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>FAX</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" class="onlyTel" placeholder="Please enter numbers only">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Handled Item</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Reason For Invitation</p>
                                    </div>
                                    <div class="input">
                                        <textarea></textarea>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Contract Progress Status</p>
                                    </div>
                                    <div class="input">
                                        <select>
                                            <option>-select-</option>
                                            <option>YES</option>
                                            <option>NO</option>
                                        </select>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Wishful Thinking</p>
                                    </div>
                                    <div class="input">
                                        <input type="text">
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

<!-- 전환페이지 설정 -->
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
<script type="text/javascript">
    var _nasa={};
    if(window.wcs) _nasa["cnv"] = wcs.cnv("4","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
</script>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>