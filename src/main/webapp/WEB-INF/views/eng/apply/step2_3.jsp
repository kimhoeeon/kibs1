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
                                <li class="active"><a href="/eng/apply/step2_3.do">Utilities</a></li>
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
                <div class="apply_tit">Utilities</div>
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
                            <progress class="gauge_bar" value="20" max="80"></progress>
                        </div>
                        <div class="txt">Proceeding...(2/7)</div>
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

                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>Participating companies that have applied only for the exhibition area must identify the
                                power required for lighting or equipment before applying.
                                In particular, please identify the power required for the equipment and fill it out. In
                                addition, electricity construction is not allowed in the exhibition hall except for the
                                company selected by the organizer.
                            </li>
                            <li>The requested electricity will only be constructed up to your booth distribution box,
                                and from the booth distribution box to the equipment must be constructed directly by the
                                participating companies.
                            </li>
                        </ul>
                    </div>

                    <!-- 유틸리티 신청정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Utility Application Information</div>
                        </div>
                        <div class="form_util form_ptag">
                            <div class="form_ptag_box">
                                <ul class="form_ptag_list">
                                    <li class="form_ptag_hd">
                                        <div class="cate">Item</div>
                                        <div class="cost">Unit Price</div>
                                        <div class="quantity">Quantity</div>
                                        <div class="amount">Amount</div>
                                        <div class="note">Note</div>
                                    </li>
                                    <li>
                                        <div class="cate">220v For Day Use</div>
                                        <div class="cost">￦ 70,000/1kw</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_jugan_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_jugan_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Available during the event (3 days)</div>
                                    </li>
                                    <li>
                                        <div class="cate">220v For 24 Hours</div>
                                        <div class="cost">￦ 80,000/1kw</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_day_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_day_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Available during the event (3 days)</div>
                                    </li>
                                    <li>
                                        <div class="cate">Compressed Air Basic Type</div>
                                        <div class="cost">￦ 180,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_compressed_air_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_compressed_air_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Installation and usage fees</div>
                                    </li>
                                    <li>
                                        <div class="cate">Multiplication Basic Type</div>
                                        <div class="cost">￦ 180,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_water_basic_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_water_basic_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Installation and usage fees</div>
                                    </li>
                                    <li>
                                        <div class="cate">Internet</div>
                                        <div class="cost">￦ 160,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_internet_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_internet_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Per line</div>
                                    </li>
                                    <li>
                                        <div class="cate">Pytex (New product)</div>
                                        <div class="cost">￦ 80,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_pytex_new_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_pytex_new_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Unit price per booth</div>
                                    </li>
                                    <li>
                                        <div class="cate">Pytex (Reuse)</div>
                                        <div class="cost">￦ 50,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_pytex_re_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_pytex_re_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Unit price per booth</div>
                                    </li>
                                    <li>
                                        <div class="cate">Visitor/Buyer Barcode Reader</div>
                                        <div class="cost">￦ 200,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" placeholder="Enter" class="num_sum onlyNum" id="utility_barcode_cnt">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="utility_barcode_fee" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Available during the event (3 days)</div>
                                    </li>
                                </ul>
                            </div>
                            <div class="form_ptag_sum">
                                <div class="cate2">Sum amount (VAT Excluded)</div>
                                <div class="amount2">
                                    <p class="price">
                                        <input type="text" class="num_sum" id="form_add_total" value="￦ 0" disabled>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- //유틸리티 신청정보 -->

                    <div class="form_btn">
                        <a href="/eng/apply/step2_2.do#apply_s" class="form_btn_prev">
                            <div class="big">PREV</div>
                            <div class="small">Fascia Board Title</div>
                        </a>
                        <a href="/eng/apply/step2_4.do#apply_s" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Exhibitor Pass</div>
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