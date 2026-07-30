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
                                <li class="active">
                                    <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Exhibition Info</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Booth</a></li>
                                        <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_10.do','${info.seq}')">해상전시회 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_9.do','${info.seq}')">Yacht/Boat</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_2.do','${info.seq}')">Signboard</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_3.do','${info.seq}')">Utilities</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_4.do','${info.seq}')">Badges</a></li>
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_5.do','${info.seq}')">Giveaways</a></li>
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
                            <div class="apply_tit">Giveaway Application</div>
                            <!-- 안내문구 -->
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <%--<li>2025 경기국제보트쇼 종료로 인해 <span class="txtRed">전시 정보 수정이 불가</span>합니다.</li>--%>
                                </ul>
                            </div>

                            <!-- 경품 관련 안내사항 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Giveaway Information</div>
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
                                            <th>Type</th>
                                            <th>Description</th>
                                            <th>Usage</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>Promotional Giveaway</td>
                                            <td>
                                                <p>For prize drawings during KIBS 2027 (March 12–14)</p>
                                            </td>
                                            <td>
                                                <p>The sponsor’s logo will be displayed on giveaway entry tickets.</p>
                                                <p>Giveaway sponsorship details may be featured in promotional media.</p>
                                                <p>Please provide giveaway quantities in multiples of three, considering the three-day exhibition period.</p>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <!-- 경품제공 신청 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Giveaway Application</div>
                                </div>
                                <div class="form_chuga">
                                    <input type="hidden" name="exhibitorSeq" value="${info.seq}">
                                    <c:set var="giftApplyYn" value="${not empty giftList ? 'Y' : 'N'}"/>

                                    <div class="input flex mb-20">
                                        <div class="flex_item">
                                            <label><input type="radio" name="giftApplyYn" value="Y" <c:if test="${giftApplyYn eq 'Y'}">checked</c:if>>Apply</label>
                                        </div>
                                        <label><input type="radio" name="giftApplyYn" value="N" <c:if test="${giftApplyYn eq 'N'}">checked</c:if>>Not applied</label>
                                    </div>

                                    <c:if test="${not empty giftList}">
                                        <c:forEach var="gift" items="${giftList}" begin="0" end="${giftList.size()}" step="1" varStatus="status">
                                            <div class="form_chuga_list">
                                                <input type="hidden" name="giftSeq" value="${gift.seq}">
                                                <div class="cont">
                                                    <div class="name">${gift.giftName}</div>
                                                    <div class="gubun">${gift.giftGbn} / ${gift.giftCnt}</div>
                                                </div>
                                                <div onclick="f_gift_modify_modal('select', '${gift.seq}');" class="modifyFormGift modifyFormList" style="cursor: pointer;">Modify</div>
                                                <div onclick="f_gift_remove('select', '${gift.seq}');" class="delFormGift delFormList" style="cursor: pointer;">Delete</div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <a id="gift_add_btn" class="addFormGift addFormList btnSt01" style="cursor: pointer">Add Giveaway</a>
                                </div>
                            </div>
                        </div>

                        <div class="form_btn">
                            <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_4.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">PREV</div>
                                <div class="small">Badge Application</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_mypage_comp('2_5','${info.seq}')" class="form_btn_next">
                                <div class="big">NEXT</div>
                                <div class="small">Directory Information</div>
                            </a>
                        </div>

                    </div>
                </div>
                <!-- //section -->
            </div>
        </div>

        <!-- 경품등록팝업 -->
        <div class="popFormGift popForm popup">
            <div class="popup_inner">
                <div class="popup_wrap">
                    <div class="pop_tit">
                        <div class="tit">Add/Edit Giveaway</div>
                        <a class="close" href="javascript:f_gift_init();" style="cursor: pointer"><img src="/img/close_w.png"></a>
                    </div>
                    <div class="pop_box">
                        <form id="giftForm" name="giftForm" method="post" onsubmit="return false;">
                            <div class="pop_cont">
                                <!-- 폼박스 -->
                                <div class="form_s">
                                    <!-- 회원계정정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">Giveaway Details</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item req">
                                                    <p>Type</p>
                                                </div>
                                                <div class="input">
                                                    <label><input type="radio" id="gift_gbn" value="행사용" checked>Event</label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Quantity</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="gift_cnt" value="0" class="onlyNum">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Category</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="gift_classify">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Item Name</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="gift_name">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Giveaway Description</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="gift_content"></textarea>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Giveaway Image</p>
                                                </div>
                                                <div class="input file_box">
                                                    <input type="text" id="gift_photo" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" name="giftPhotoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                    <label>Browse</label>
                                                    <span class="giftPhotoBox"></span>
                                                    <div style="font-size: 0.7em; color: #666;">
                                                        * Accepted file formats: JPG, JPEG, PNG<br>
                                                        * Maximum file size: 10MB
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Company Logo</p>
                                                </div>
                                                <div class="input file_box">
                                                    <input type="text" id="gift_company_logo" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" name="giftCompanyLogoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                    <label>Browse</label>
                                                    <span class="giftCompanyLogoBox"></span>
                                                    <div style="font-size: 0.7em; color: #666;">
                                                        * Accepted file formats: JPG, JPEG, PNG<br>
                                                        * Maximum file size: 10MB
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Retail Price (USD)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="gift_price" value="0" class="onlyNum" placeholder="Enter amount in USD">
                                                    <div style="font-size: 0.7em; color: #666;">
                                                        * For overseas exhibitors, retail price and sponsored price must be entered in USD.
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Sponsored Price (USD)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="gift_sponsor_price" value="0" class="onlyNum" placeholder="Enter amount in USD">
                                                    <div style="font-size: 0.7em; color: #666;">
                                                        * For overseas exhibitors, retail price and sponsored price must be entered in USD.
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Remarks</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="gift_note"></textarea>
                                                </div>
                                            </li>
                                        </ul>
                                        <a class="btnSave" onclick="f_gift_add('${info.seq}')">Save</a>
                                    </div>
                                </div>
                                <!-- //폼박스 -->
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    </c:if>
</body>
</html>