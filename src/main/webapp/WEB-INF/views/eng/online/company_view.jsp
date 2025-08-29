<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Kibs 2026">
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
    <title>2026 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
</head>

<body>
<c:import url="../header.jsp" charEncoding="UTF-8"/>

<div id="container">

    <!-- section -->
    <div class="sub_online_top online_exh_top">
        <div class="inner">
            <div class="online_top_tit">
                <div class="big">Exhibitors List</div>
                <div class="small">You can see the companies registered in the online exhibition hall at a glance.</div>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="sub_tab">
        <div class="inner">
            <div class="sub_tab_box">
                <a href="javascript:void(0);" onclick="home('en');" class="iconHome"><img src="/img/icon_home.png"></a>
                <div class="optionBox">
                    <div class="tabOpt1 tabOption">
                        <div class="tabOptAct">Exhibitors</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2026</a>
                            <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a href="/eng/buyer/apply.do">Buyer</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Exhibitor List</div>
                        <div class="tabOptSel">
                            <a href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                            <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : +82 1670-8785');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>">Exhibitor Registration</a>
                            <a href="/eng/exhibitor/glance.do">Korea at a Glance</a>
                            <a href="/eng/exhibitor/marina.do">Marine Industry in korea</a>
                            <a href="/eng/exhibitor/match.do">Exhibitor-Buyer Matchmaking Program</a>
                            <a class="active" href="/eng/online/company.do">Exhibitor List</a>
                            <a href="/eng/exhibitor/qna.do">FAQ</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="exh_view online_view padding_tb">
        <div class="inner">
            <div class="exh_view_box">
                <div class="view_type">
                    <c:choose>
                        <c:when test="${gbn eq 'E'}">
                            <c:if test="${not empty onlineList}">
                                <c:if test="${onlineList.get(0).productOptionBig ne null and onlineList.get(0).productOptionBig ne ''}">
                                    <c:choose>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '요트·보트'}">Yacht & Boat</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '무동력보트'}">Paddling Boat</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '워크보트'}">Work Boat</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '워터스포츠'}">Water Sports</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '다이빙'}">Diving</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '아웃도어'}">Outdoor</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '해양관광'}">Marine Tourism</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '해양부품'}">Marine Equipment</c:when>
                                        <c:when test="${onlineList.get(0).productOptionBig eq '마리나산업'}">Marina Industry</c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <%--<c:if test="${not empty productList}">
                                <c:if test="${productList.get(0).productOptionBig ne null and productList.get(0).productOptionBig ne ''}">
                                    <c:choose>
                                        <c:when test="${productList.get(0).productOptionBig eq '요트·보트'}">Yacht & Boat</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '무동력보트'}">Paddling Boat</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '워크보트'}">Work Boat</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '워터스포츠'}">Water Sports</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '다이빙'}">Diving</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '아웃도어'}">Outdoor</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '해양관광'}">Marine Tourism</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '해양부품'}">Marine Equipment</c:when>
                                        <c:when test="${productList.get(0).productOptionBig eq '마리나산업'}">Marina Industry</c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:if>--%>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="exh_view_img">
                    <div class="subImg">
                        <div class="thumb41 thumbBox">
                            <c:choose>
                                <c:when test="${gbn eq 'E'}">
                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1">
                                        <c:if test="${fileInfo.note eq 'promotionImage1'}">
                                            <c:choose>
                                                <c:when test="${fileInfo.fullFilePath ne null and fileInfo.fullFilePath ne '' and not fn:contains(fileInfo.fullFilePath, '.ai')}">
                                                    <c:set var="promotionImageFilePathSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                    <img src="${promotionImageFilePathSrc}" class="thumbImg">
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- 랜덤 숫자 설정 -->
                                                    <%
                                                        int min = 1;
                                                        int max = 6;
                                                        java.util.Random random = new java.util.Random();
                                                        int value = random.nextInt(max + min) + min;
                                                    %>
                                                    <img src="/img/thumb_exh_img_0<%=value%>.jpg" class="thumbImg">
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1">
                                        <c:if test="${fn:contains(fileInfo.note, 'productImage1_1')}">
                                            <c:choose>
                                                <c:when test="${fileInfo.fullFilePath ne null and fileInfo.fullFilePath ne '' and not fn:contains(fileInfo.fullFilePath, '.ai')}">
                                                    <c:set var="productImageFilePathSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                    <img src="${productImageFilePathSrc}" class="thumbImg">
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- 랜덤 숫자 설정 -->
                                                    <%
                                                        int min = 1;
                                                        int max = 6;
                                                        java.util.Random random = new java.util.Random();
                                                        int value = random.nextInt(max + min) + min;
                                                    %>
                                                    <img src="/img/thumb_exh_img_0<%=value%>.jpg" class="thumbImg">
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="imgLogo">
                            <p>
                                <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1">
                                    <c:if test="${fileInfo.note eq 'logo'}">
                                        <c:choose>
                                            <c:when test="${fileInfo.fullFilePath ne null and fileInfo.fullFilePath ne '' and not fn:contains(fileInfo.fullFilePath, '.ai')}">
                                                <c:set var="logoFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                <img src="${logoFileSrc}">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="/img/thumb_exh_logo_sample.png">
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="exh_view_top">
                    <div class="name">
                        <c:if test="${exhibitorInfo.lang eq 'EN'}">
                            ${exhibitorInfo.companyNameEn}
                        </c:if>
                    </div>
                    <div class="info">
                        <p>
                            <img src="/img/icon_exh_tel.png">${exhibitorInfo.companyTel}
                        </p>
                        <c:if test="${exhibitorInfo.companyHomepage ne null and exhibitorInfo.companyHomepage ne ''}">
                            <p>
                                <img src="/img/icon_exh_home.png"><a href="${exhibitorInfo.companyHomepage}" target="_blank">${exhibitorInfo.companyHomepage}</a>
                            </p>
                        </c:if>
                    </div>
                    <div class="btn">
                        <div class="btnCs">
                            <a href="javascript:void(0);" onclick="f_ask_popup_open()">
                                <img src="/img/icon_exh_cs.png">Contact us
                            </a>
                        </div>
                        <%--<div class="btnLike">
                            <img src="/img/icon_like.png" class="likeImg">좋아요
                        </div>--%>
                        <div class="<%--TODO: 추후 기능 오픈시 해당 클래스 주석 해제 : btnShare--%>">
                            <a href="javascript:alert('It will be updated.');">
                                <img src="/img/icon_share.png">Sharing
                            </a>
                        </div>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${gbn eq 'E'}">
                        <c:if test="${exhibitorInfo.companyIntroVideo ne null and exhibitorInfo.companyIntroVideo ne ''}">
                            <div class="exh_view_video">
                                <div class="view_tit">Company Video</div>
                                <div class="video_wrap">
                                    <div class="embed-container">
                                        <iframe src="https://www.youtube.com/embed/${exhibitorInfo.companyIntroVideo}?autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${exhibitorInfo.companyIntroVideo ne null and exhibitorInfo.companyIntroVideo ne ''}">
                            <c:set var="companyIntroVideo" value="${fn:substring(fn:substringAfter(exhibitorInfo.companyIntroVideo, 'youtu.be/'), 0, fn:indexOf(fn:substringAfter(exhibitorInfo.companyIntroVideo, 'youtu.be/'), '?'))}"/>
                            <div class="exh_view_video">
                                <div class="view_tit">Company Video</div>
                                <div class="video_wrap">
                                    <div class="embed-container">
                                        <iframe src="https://www.youtube.com/embed/${companyIntroVideo}?autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <div class="exh_view_info">
                    <div class="view_tit">Company Introduction</div>
                    <div class="cont">
                        ${exhibitorInfo.companyIntroEn}
                    </div>
                </div>
            </div>

            <div class="exh_view_prd">
                <div class="view_tit">Product Information</div>
                <ul class="prdList">
                    <c:choose>
                        <c:when test="${gbn eq 'E'}">
                            <c:forEach var="onlineInfo" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                <c:if test="${fn:trim(onlineInfo.productNameEn) ne '-' and fn:trim(onlineInfo.productNameEn) ne ''}">
                                    <li>
                                        <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                        <a href="<c:url value="/eng/online/product_view.do?seq=${onlineInfo.seq}"/>">
                                            <div class="thumbnail">
                                                <div class="thumb75 thumbBox">
                                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1">
                                                        <c:if test="${fileInfo.note eq 'productImage'.concat(status.index+1).concat('_1')}">
                                                            <c:set var="productImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${productImageFileSrc}" class="thumbImg">
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="prdTxt">
                                                <div class="name">${exhibitorInfo.companyNameEn}</div>
                                                <div class="prdName">${onlineInfo.productNameEn}</div>
                                                <div class="type">${onlineInfo.productOptionBig} > ${onlineInfo.productOptionSmall}</div>
                                            </div>
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <%--<c:forEach var="productInfo" items="${productList}" begin="0" end="${productList.size()}" step="1" varStatus="status">
                                <c:if test="${fn:trim(productInfo.productNameKo) ne '-' and fn:trim(productInfo.productNameKo) ne ''}">
                                    <li>
                                        &lt;%&ndash;<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>&ndash;%&gt;
                                        <a href="<c:url value="/eng/online/product_view.do?seq=${productInfo.seq}"/>">
                                            <div class="thumbnail">
                                                <div class="thumb75 thumbBox">
                                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1">
                                                        <c:if test="${fileInfo.note eq 'productImage'.concat(status.index+1).concat('_1')}">
                                                            <c:set var="productImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${productImageFileSrc}" class="thumbImg">
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="prdTxt">
                                                <div class="name">${exhibitorInfo.companyNameEn}</div>
                                                <div class="prdName">${productInfo.productNameEn}</div>
                                                <div class="type">${productInfo.productOptionBig} > ${productInfo.productOptionSmall}</div>
                                            </div>
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>--%>
                            <c:forEach var="onlineInfo" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                <c:if test="${fn:trim(onlineInfo.onlineNameEn) ne '-' and fn:trim(onlineInfo.onlineNameEn) ne ''}">
                                    <li>
                                        <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                        <a href="<c:url value="/eng/online/product_view.do?seq=${onlineInfo.seq}"/>">
                                            <div class="thumbnail">
                                                <div class="thumb75 thumbBox">
                                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1">
                                                        <c:if test="${fileInfo.note eq 'onlineImage'.concat(status.index+1).concat('_1')}">
                                                            <c:set var="onlineImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${onlineImageFileSrc}" class="thumbImg">
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="prdTxt">
                                                <div class="name">${exhibitorInfo.companyNameEn}</div>
                                                <div class="prdName">${onlineInfo.onlineNameEn}</div>
                                                <div class="type">${onlineInfo.onlineOptionBig} > ${onlineInfo.onlineOptionSmall}</div>
                                            </div>
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <%--<li>
                        <div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>
                        <a href="/online/product_view.do">
                            <div class="thumbnail">
                                <div class="thumb75 thumbBox"><img src="/img/thumb_exh_img_01.jpg"
                                                                   class="thumbImg"></div>
                                <div class="thumbLogo">
                                    <p><img src="/img/thumb_exh_logo_01.jpg"></p>
                                </div>
                            </div>
                            <div class="prdTxt">
                                <div class="name">현대요트주식회사</div>
                                <div class="prdName">바바리아 VIDA33</div>
                                <div class="type">요트·보트 > 파워보트</div>
                            </div>
                        </a>
                    </li>--%>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- //section -->

</div>

<!-- 공유하기 -->
<div class="popShare popup">
    <div class="popup_inner">
        <div class="popup_wrap">
            <div class="pop_tit">
                <div class="tit">Sharing</div>
                <a class="close" href="#close"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- box -->
                    <div class="pop_share_sns">
                        <a href="#"><img src="/img/icon_shr_ka.png"></a>
                        <a href="#"><img src="/img/icon_shr_fb.png"></a>
                        <a href="#"><img src="/img/icon_shr_tw.png"></a>
                        <a href="#"><img src="/img/icon_shr_bl.png"></a>
                    </div>
                    <div class="pop_share_clip">
                        <input type="text">
                        <input type="button" value="Copy">
                    </div>
                    <!-- //box -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- //공유하기 -->

<!-- 문의하기 -->
<div class="popExhCs popup">
    <div class="popup_inner">
        <div class="popup_wrap">
            <div class="pop_tit">
                <div class="tit">Contact us</div>
                <a class="close" href="javascript:void(0);" onclick="f_ask_popup_close()"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- box -->
                    <ul class="pop_cs_box">
                        <li>
                            <div class="gubun">Company</div>
                            <div class="input">${exhibitorInfo.companyNameEn}</div>
                        </li>
                        <li>
                            <div class="gubun">Name</div>
                            <div class="input"><input type="text" id="askName" maxlength="15" placeholder="Name"></div>
                        </li>
                        <li>
                            <div class="gubun">Phone</div>
                            <div class="input"><input type="tel" id="askPhone" class="onlyTel" maxlength="13" placeholder="Phone"></div>
                        </li>
                        <li class="w100">
                            <div class="gubun">Content ( <span id="smsRemain">90</span> / 90 Bytes )</div>
                            <div class="input"><textarea id="askContent" onblur="smsByteChk(this);" onkeyup="smsByteChk(this);" placeholder="Please enter the contents."></textarea></div>
                        </li>
                    </ul>

                    <div class="pop_cs_btn">
                        <a href="javascript:void(0);" onclick="f_ask_popup_close()" class="btnSt02">Close</a>
                        <a href="javascript:f_ask_request('${exhibitorInfo.companyNameEn}','${exhibitorInfo.seq}')" class="btnSt01">Send</a>
                    </div>
                    <!-- //box -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- //문의하기 -->

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>