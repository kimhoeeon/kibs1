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
                        <li>
                            <a href="/eng/mypage/step2_1.do">Exhibition Req.</a>
                            <ul class="list2">
                                <li><a href="/eng/mypage/step2_1.do">Exhibitor Booth</a></li>
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

                    <!-- 전시부스 신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Exhibitor Booth</div>
                        </div>
                        <div class="form_total_table form_total_booth">
                            <div class="table_box">
                                <table>
                                    <colgroup>
                                        <col width="5%">
                                        <col width="15%">
                                        <col width="8%">
                                        <col width="15%">
                                        <col width="15%">
                                        <col>
                                        <col>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Booth Type</th>
                                        <th>Scale</th>
                                        <th>Unit Price</th>
                                        <th>Sum Amount</th>
                                        <th>Registered date</th>
                                        <th>Last updated</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>2</td>
                                        <td>Space only</td>
                                        <td>2</td>
                                        <td>1,800,000</td>
                                        <td>3,600,000</td>
                                        <td>2023-05-04 14:49</td>
                                        <td>2023-05-04 14:49</td>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>Registration Fee</td>
                                        <td>1</td>
                                        <td>100,000</td>
                                        <td>100,000</td>
                                        <td>2023-05-04 14:49</td>
                                        <td>2023-05-04 14:49</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td colspan="6">Total Amount (VAT Excluded)</td>
                                        <td colspan="1">￦ 3,700,000</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 전시부스 신청 -->

                    <!-- 부스할인 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Booth Discount Choices</div>
                        </div>
                        <div class="form_total_table form_total_disc">
                            <div class="table_box">
                                <table>
                                    <colgroup>
                                        <col width="10%">
                                        <col width="20%">
                                        <col>
                                        <col width="10%">
                                        <col width="20%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Sortation</th>
                                        <th>Discount</th>
                                        <th>Quantity</th>
                                        <th>Total Discount</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>A First-time Discount</td>
                                        <td>Discount ￦ 200,000 per booth</td>
                                        <td>2</td>
                                        <td>- 400,000</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td colspan="4">Sum Amount</td>
                                        <td>- ￦ 400,000</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 부스할인 -->

                    <!-- 유틸리티 신청 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Utility Application Information</div>
                        </div>
                        <div class="form_total_table form_total_util">
                            <div class="table_box">
                                <table>
                                    <colgroup>
                                        <col width="5%">
                                        <col width="15%">
                                        <col width="10%">
                                        <col width="15%">
                                        <col width="15%">
                                        <col>
                                        <col>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Sortation</th>
                                        <th>Quantity</th>
                                        <th>Unit Price</th>
                                        <th>Sum Amount</th>
                                        <th>Registered date</th>
                                        <th>Last updated</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>2</td>
                                        <td>220v For Day Use</td>
                                        <td>2</td>
                                        <td>70,000</td>
                                        <td>140,000</td>
                                        <td>2023-05-04 14:49</td>
                                        <td>2023-05-04 14:49</td>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>220v For 24 Hours</td>
                                        <td>1</td>
                                        <td>80,000</td>
                                        <td>80,000</td>
                                        <td>2023-05-04 14:49</td>
                                        <td>2023-05-04 14:49</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td colspan="6">Sum Amount</td>
                                        <td colspan="1">￦ 220,000</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td colspan="6">Deposit</td>
                                        <td colspan="1">￦ 0</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 유틸리티 신청 -->

                    <!-- 초청희망바이어 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Buyer Invitation</div>
                        </div>
                        <div class="form_total_table form_total_buyer">
                            <div class="table_box">
                                <table>
                                    <colgroup>
                                        <col width="5%">
                                        <col>
                                        <col>
                                        <col>
                                        <col>
                                        <col>
                                        <col>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Nation</th>
                                        <th>Corporate Name</th>
                                        <th>Name</th>
                                        <th>Department</th>
                                        <th>Position</th>
                                        <th>E-mail</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>대한민국</td>
                                        <td>프리미어케어</td>
                                        <td>홍길동</td>
                                        <td>기획팀</td>
                                        <td>과장</td>
                                        <td>abc@abc.om</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 초청희망바이어 -->

                    <!-- 최종금액 -->
                    <div class="form_wrap">
                        <div class="form_total_table form_total_amount">
                            <div class="table_box">
                                <table style="border-top:1px solid #ddd">
                                    <colgroup>
                                        <col width="30%">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <td class="bgBlue">Supply Price</td>
                                        <td>￦ 3,520,000</td>
                                    </tr>
                                    <tr>
                                        <td class="bgBlue">Requested Fee</td>
                                        <td>￦ 3,872,000 (VAT Included)</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td class="bgBlue">Total Price</td>
                                        <td class="txtBlue" style="font-size: 2rem;">￦ 3,872,000</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td class="bgBlue" rowspan="2">Payment History</td>
                                        <td>Deposit : ￦ 0</td>
                                    </tr>
                                    <tr class="txtBold">
                                        <td class="txtRed">Remaining Balance : ￦ 3,872,000</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- //최종금액 -->

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
                        <%--<a href="" class="btnSt01">탈퇴하기</a>--%>
                        <a href="" class="close btnSt03">취소</a>
                    </div>
                    <!-- //탈퇴안내 -->
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>