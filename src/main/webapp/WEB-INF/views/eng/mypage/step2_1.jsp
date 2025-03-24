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
                                <li class="active"><a href="/eng/mypage/step2_1.do">Exhibitor Booth</a></li>
                                <li><a href="/eng/mypage/step2_2.do">Fascia Board Title</a></li>
                                <li><a href="/eng/mypage/step2_3.do">Utilities</a></li>
                                <li><a href="/eng/mypage/step2_4.do">Exhibitor Pass</a></li>
                                <li><a href="/eng/mypage/step2_5.do">Prize & Giveaway</a></li>
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
                    <div class="apply_tit">Exhibitor Booth</div>
                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>The basic unit of the booth is 3mx3m(9m2, 1booth) and the minimum application unit is 2
                                independent booths(18m2)and 1 assembly booth(9m2 ).
                            </li>
                            <li>Assembly Booth Offerings: Floor area(9m2), Signboard(Kor/Eng), Interior lighting, floor
                                fidex, 1 information desk, 1 folding chair, 1 consultation table, 3 chairs, 1 trash can,
                                electric 1KW (220V outlet)
                                <span class="em">(Assembly booth provision may be changed later)</span>
                            </li>
                            <li>The down payment (50% of the total participation fee) must be deposited within 7 days of
                                receipt of the booth fee invoice, <span class="em">and the balance must be paid by 0/0/2024 (Friday).</span>
                            </li>
                            <li>Please refer to <a
                                    onclick="javascript_:window.open('/eng/exhibitor/privacy.do','pop','menubar=no,status=no,scrollbars=no,resizable=no,width=500,height=300,top=50,left=50');"
                                    class="em">here</a> for detailed regulations and terms of participation.
                            </li>
                        </ul>
                    </div>

                    <!-- 부스 신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Booth</div>
                        </div>
                        <div class="form_booth form_ptag">
                            <div class="form_ptag_box">
                                <ul class="form_ptag_list">
                                    <li class="form_ptag_hd">
                                        <div class="cate">Booth Type</div>
                                        <div class="cost">Unit Price</div>
                                        <div class="quantity">Scale</div>
                                        <div class="amount">Sum Amount</div>
                                        <div class="note">Note</div>
                                    </li>
                                    <li>
                                        <div class="cate">Registration Fee</div>
                                        <div class="cost booth_cost">￦ 100,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" id="registration_cnt" placeholder="Enter" class="onlyNum qty" value="1" disabled>
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" id="registration_fee" class="num_sum" value="￦ 100,000" disabled>
                                            </p>
                                        </div>
                                        <div class="note">
                                            Beginning in 2019, we receive registration fees for each company
                                            to support the promotion of the participating companies.
                                            Registration fees are used for various online and international promotions
                                            for the participating companies.
                                        </div>
                                    </li>
                                    <li>
                                        <div class="cate">Space only</div>
                                        <div class="cost booth_cost">￦ 1,800,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" id="stand_alone_booth_cnt" placeholder="Enter" class="onlyNum qty" onblur="minCnt(this,2);" onkeyup="checkBooth();autoSum(1);autoTotalSum();">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" id="stand_alone_booth_fee" class="num_sum" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">독립부스는 2부스부터 신청 가능합니다.</div>
                                    </li>
                                    <li>
                                        <div class="cate">Shell scheme package</div>
                                        <div class="cost booth_cost">￦2,100,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <input type="text" id="assembly_booth_cnt" placeholder="Enter" class="onlyNum qty" onkeyup="checkBooth();autoSum(2);autoTotalSum();">
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" id="assembly_booth_fee" class="num_sum" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note"></div>
                                    </li>
                                    <li>
                                        <div class="cate">online booth</div>
                                        <div class="cost booth_cost">￦ 1,000,000</div>
                                        <div class="quantity">
                                            <p class="j_num">
                                                <select id="online_booth_cnt" onchange="checkBooth();autoSum(3);autoTotalSum();">
                                                    <option selected>0</option>
                                                    <option>1</option>
                                                </select>
                                            </p>
                                        </div>
                                        <div class="amount">
                                            <p class="price">
                                                <input type="text" id="online_booth_fee" class="num_sum" value="￦ 0" disabled>
                                            </p>
                                        </div>
                                        <div class="note">Video production is separate : If you want to make a video,
                                            please contact the secretariat.
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!-- //부스 신청-->

                    <!-- 할인적용 선택 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Booth Discount Choices</div>
                            <div class="small">* You may have penalties such as registration drop if you enter incorrect
                                information.
                            </div>
                        </div>
                        <div class="form_disc form_ptag">
                            <div class="form_ptag_box">
                                <ul class="form_ptag_list">
                                    <li class="form_ptag_hd">
                                        <div class="select">Choice</div>
                                        <div class="cate">Sortation</div>
                                        <div class="discount">Discount</div>
                                        <div class="note">Note</div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount1" onchange="autoDiscountSum(this, '300000');"></label></div>
                                        <div class="cate">1st Early Application</div>
                                        <div class="discount">Discount ￦ 300,000 per booth</div>
                                        <div class="note">Early participation application discount/~If you apply by November 17, 2023 (Fri)</div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount2" onchange="autoDiscountSum(this, '200000');"></label></div>
                                        <div class="cate">2nd Early Application</div>
                                        <div class="discount">Discount ￦ 200,000 per booth</div>
                                        <div class="note">2nd Early Application Discount/~If you apply by December 1, 2023 (Fri)</div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount3" onchange="autoDiscountSum(this, '400000');"></label></div>
                                        <div class="cate">A Re-entry Discount 1 (In 2019-2022)</div>
                                        <div class="discount">Discount ￦ 400,000 per booth</div>
                                        <div class="note">(In 2019-2022)</div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount4" onchange="autoDiscountSum(this, '200000');"></label></div>
                                        <div class="cate">A Re-entry Discount 2 (In 2016-2018)</div>
                                        <div class="discount">Discount ￦ 200,000 per booth</div>
                                        <div class="note">(In 2016-2018)</div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount5" onchange="autoDiscountSum(this, '100000');"></label></div>
                                        <div class="cate">A Re-entry Discount 3 (In 2013-2015)</div>
                                        <div class="discount">Discount ￦ 100,000 per booth</div>
                                        <div class="note">(In 2013-2015)</div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount6" onchange="autoDiscountSum(this, '450000');"></label></div>
                                        <div class="cate">A Discount On Scale 1(40 or more booths)</div>
                                        <div class="discount">Discount ￦ 450,000 per booth</div>
                                        <div class="note"></div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount7" onchange="autoDiscountSum(this, '300000');"></label></div>
                                        <div class="cate">A Discount On Scale 2(20 or more booths)</div>
                                        <div class="discount">Discount ￦ 300,000 per booth</div>
                                        <div class="note"></div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount8" onchange="autoDiscountSum(this, '200000');"></label></div>
                                        <div class="cate">A Discount On Scale 3(10 or more booths)</div>
                                        <div class="discount">Discount ￦ 200,000 per booth</div>
                                        <div class="note"></div>
                                    </li>
                                    <li>
                                        <div class="select"><label><input type="checkbox" id="discount9" onchange="autoDiscountSum(this, '200000');"></label></div>
                                        <div class="cate">A First-time Discount</div>
                                        <div class="discount">Discount ￦ 200,000 per booth</div>
                                        <div class="note"></div>
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
                    <!-- //할인적용 선택-->

                    <div class="form_btn">
                        <a href="/eng/mypage/step01.do#apply_s" class="form_btn_prev">
                            <div class="big">PREV</div>
                            <div class="small">Basic Information</div>
                        </a>
                        <a href="/eng/mypage/step2_2.do#apply_s" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Fascia Board Title</div>
                        </a>
                    </div>

                </div>
            </div>
            <!-- //section -->
        </div>
    </div>

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

<script type="text/javascript">
    $(function(){
        $('#form_add_total').val($('.booth_cost')[0].innerText); // 초기 총액 : 등록비 합계 Set
    });
</script>
</body>
</html>