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

<script>
    let gbn = '${gbn}';
    if(gbn === 'PN'){
        let productInfo = '${empty productInfo}';
        if(productInfo === 'true'){
            alert('잘못된 제품 정보 호출입니다. 이전 페이지로 돌아갑니다.');
            history.back();
        }
    }else{
        let onlineInfo = '${empty onlineInfo}';
        if(onlineInfo === 'true'){
            alert('잘못된 제품 정보 호출입니다. 이전 페이지로 돌아갑니다.');
            history.back();
        }
    }
</script>

<c:import url="../header.jsp" charEncoding="UTF-8"/>

<div id="container">

    <!-- section -->
    <div class="sub_online_top online_prd_top">
        <div class="inner">
            <div class="online_top_tit">
                <div class="big">제품 검색하기</div>
                <div class="small">카테고리를 선택하거나 검색을 통해 간편하게 관심 제품을 찾아보세요</div>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="sub_tab">
        <div class="inner">
            <div class="sub_tab_box">
                <a href="javascript:void(0);" onclick="home('ko');" class="iconHome"><img src="/img/icon_home.png"></a>
                <div class="optionBox">
                    <div class="tabOpt1 tabOption">
                        <div class="tabOptAct">온라인 전시관</div>
                        <div class="tabOptSel">
                            <a href="/guide/summary.do">KIBS 안내</a>
                            <a href="/exhibitor/guide.do">참가기업</a>
                            <a href="/visitor/guide_off.do">참관객</a>
                            <a href="/guide/notice.do">미디어센터</a>
                            <a class="active" href="/online/kibstv.do">온라인 전시관</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">제품 검색하기</div>
                        <div class="tabOptSel">
                            <a href="/online/company.do">참가기업 미리보기</a>
                            <a class="active" href="/online/product.do">제품 검색하기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="prd_view online_view padding_tb">
        <div class="inner">
            <div class="prd_view_box">
                <div class="view_type">
                    <c:choose>
                        <c:when test="${gbn eq 'PN'}">
                            ${productInfo.productOptionBig} > ${productInfo.productOptionSmall}
                        </c:when>
                        <c:when test="${gbn eq 'O'}">
                            ${onlineInfo.productOptionBig} > ${onlineInfo.productOptionSmall}
                        </c:when>
                        <c:otherwise>
                            ${onlineInfo.onlineOptionBig} > ${onlineInfo.onlineOptionSmall}
                        </c:otherwise>
                    </c:choose>
                </div>
                <!--이미지 슬라이드-->
                <div class="prd_view_img">
                    <!-- 이미지 -->
                    <div class="prdImg">
                        <div class="swiper_box">
                            <div class="swiper-container slider swiper_prdImg">
                                <div class="swiper-wrapper">
                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1" varStatus="status">
                                        <c:choose>
                                            <c:when test="${gbn eq 'O'}">
                                                <c:if test="${fn:contains(fileInfo.note, 'productImage'.concat(onlineInfo.note).concat('_'))}">
                                                    <div class="swiper-slide">
                                                        <div class="thumb75 thumbBox">
                                                            <c:set var="productImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${productImageFileSrc}" class="thumbImg">
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${gbn eq 'PN'}">
                                                <c:if test="${fn:contains(fileInfo.note, 'productImage'.concat(productInfo.note).concat('_'))}">
                                                    <div class="swiper-slide">
                                                        <div class="thumb75 thumbBox">
                                                            <c:set var="productImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${productImageFileSrc}" class="thumbImg">
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${fn:contains(fileInfo.note, 'onlineImage'.concat(onlineInfo.note).concat('_'))}">
                                                    <div class="swiper-slide">
                                                        <div class="thumb75 thumbBox">
                                                            <c:set var="onlineImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${onlineImageFileSrc}" class="thumbImg">
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                                <div class="swiper-button-prev swiper_prdImg_prev"></div>
                                <div class="swiper-button-next swiper_prdImg_next"></div>
                            </div>
                        </div>
                        <!-- //이미지 -->
                        <!-- 이미지썸네일 -->
                        <div class="prdThumb">
                            <div class="swiper-container swiper_prdThumb">
                                <div class="swiper-wrapper">
                                    <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1" varStatus="status">
                                        <c:choose>
                                            <c:when test="${gbn eq 'O'}">
                                                <c:if test="${fn:contains(fileInfo.note, 'productImage'.concat(onlineInfo.note).concat('_'))}">
                                                    <div class="swiper-slide">
                                                        <div class="thumb11 thumbBox">
                                                            <c:set var="productImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${productImageFileSrc}" class="thumbImg">
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${gbn eq 'PN'}">
                                                <c:if test="${fn:contains(fileInfo.note, 'productImage'.concat(productInfo.note).concat('_'))}">
                                                    <div class="swiper-slide">
                                                        <div class="thumb11 thumbBox">
                                                            <c:set var="productImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${productImageFileSrc}" class="thumbImg">
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${fn:contains(fileInfo.note, 'onlineImage'.concat(onlineInfo.note).concat('_'))}">
                                                    <div class="swiper-slide">
                                                        <div class="thumb11 thumbBox">
                                                            <c:set var="onlineImageFileSrc" value="${fn:replace(fileInfo.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                            <img src="${onlineImageFileSrc}" class="thumbImg">
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <!-- //이미지썸네일 -->
                    </div>
                    <!-- //이미지 -->
                </div>
                <!-- //이미지슬라이드  -->

                <div class="prd_view_top">
                    <div class="name">${exhibitorInfo.companyNameKo}</div>
                    <div class="prdName">
                        <c:choose>
                            <c:when test="${gbn eq 'PN'}">
                                ${productInfo.productNameKo}<br>${productInfo.productNameEn}
                            </c:when>
                            <c:when test="${gbn eq 'O'}">
                                ${onlineInfo.productNameKo}<br>${onlineInfo.productNameEn}
                            </c:when>
                            <c:otherwise>
                                ${onlineInfo.onlineNameKo}<br>${onlineInfo.onlineNameEn}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="btn">
                        <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg">좋아요</div>--%>
                        <div class="btnShare"><img src="/img/icon_share.png">공유하기</div>
                    </div>
                </div>
                <div class="prd_view_info">
                    <div class="prdInfo">
                        <div class="prd_view_dt">
                            <div class="view_tit">제품 정보</div>
                            <ul>
                                <c:choose>
                                    <c:when test="${gbn eq 'PN'}">
                                        <c:if test="${productInfo.productIntroKo ne null and productInfo.productIntroKo ne '' and productInfo.productIntroEn ne null and productInfo.productIntroEn ne ''}">
                                            <li content="w100">
                                                <div class="gubun">제품소개</div>
                                                <div class="cont">
                                                    ${productInfo.productIntroKo}
                                                    <br>
                                                    <br>
                                                    ${productInfo.productIntroEn}
                                                </div>
                                            </li>
                                        </c:if>
                                        <c:if test="${productInfo.productLength ne null and productInfo.productLength ne ''}">
                                            <li>
                                                <div class="gubun">길이 (cm)</div>
                                                <div class="cont">${productInfo.productLength} cm</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${productInfo.productWidth ne null and productInfo.productWidth ne ''}">
                                            <li>
                                                <div class="gubun">너비 (cm)</div>
                                                <div class="cont">${productInfo.productWidth} cm</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${productInfo.productHeight ne null and productInfo.productHeight ne ''}">
                                            <li>
                                                <div class="gubun">높이 (cm)</div>
                                                <div class="cont">${productInfo.productHeight} cm</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${productInfo.productWeight ne null and productInfo.productWeight ne ''}">
                                            <li>
                                                <div class="gubun">중량 (kg)</div>
                                                <div class="cont">${productInfo.productWeight} kg</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${productInfo.productMaterial ne null and productInfo.productMaterial ne ''}">
                                            <li>
                                                <div class="gubun">소재</div>
                                                <div class="cont">${productInfo.productMaterial}</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${productInfo.productYear ne null and productInfo.productYear ne ''}">
                                            <li>
                                                <div class="gubun">연식</div>
                                                <div class="cont">${productInfo.productYear}</div>
                                            </li>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${gbn eq 'O'}">
                                        <c:if test="${onlineInfo.productIntroKo ne null and onlineInfo.productIntroKo ne '' and onlineInfo.productIntroEn ne null and onlineInfo.productIntroEn ne ''}">
                                            <li class="w100">
                                                <div class="gubun">제품소개</div>
                                                <div class="cont">
                                                        ${onlineInfo.productIntroKo}
                                                    <br>
                                                    <br>
                                                        ${onlineInfo.productIntroEn}
                                                </div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.productWidth ne null and onlineInfo.productWidth ne ''}">
                                            <li>
                                                <div class="gubun">전장(m)</div>
                                                <div class="cont">${onlineInfo.productWidth} m</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.productHorsePower ne null and onlineInfo.productHorsePower ne ''}">
                                            <li>
                                                <div class="gubun">마력</div>
                                                <div class="cont">${onlineInfo.productHorsePower} hp</div>
                                            </li>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${onlineInfo.onlineIntroKo ne null and onlineInfo.onlineIntroKo ne '' and onlineInfo.onlineIntroEn ne null and onlineInfo.onlineIntroEn ne ''}">
                                            <li content="w100">
                                                <div class="gubun">제품소개</div>
                                                <div class="cont">
                                                        ${onlineInfo.onlineIntroKo}
                                                    <br>
                                                    <br>
                                                        ${onlineInfo.onlineIntroEn}
                                                </div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.onlineLength ne null and onlineInfo.onlineLength ne ''}">
                                            <li>
                                                <div class="gubun">길이 (cm)</div>
                                                <div class="cont">${onlineInfo.onlineLength} cm</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.onlineWidth ne null and onlineInfo.onlineWidth ne ''}">
                                            <li>
                                                <div class="gubun">너비 (cm)</div>
                                                <div class="cont">${onlineInfo.onlineWidth} cm</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.onlineHeight ne null and onlineInfo.onlineHeight ne ''}">
                                            <li>
                                                <div class="gubun">높이 (cm)</div>
                                                <div class="cont">${onlineInfo.onlineHeight} cm</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.onlineWeight ne null and onlineInfo.onlineWeight ne ''}">
                                            <li>
                                                <div class="gubun">중량 (kg)</div>
                                                <div class="cont">${onlineInfo.onlineWeight} kg</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.onlineMaterial ne null and onlineInfo.onlineMaterial ne ''}">
                                            <li>
                                                <div class="gubun">소재</div>
                                                <div class="cont">${onlineInfo.onlineMaterial}</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${onlineInfo.onlineYear ne null and onlineInfo.onlineYear ne ''}">
                                            <li>
                                                <div class="gubun">연식</div>
                                                <div class="cont">${onlineInfo.onlineYear}</div>
                                            </li>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                        <c:choose>
                            <c:when test="${gbn eq 'PN'}">
                                <c:if test="${productInfo.productLink ne null and productInfo.productLink ne ''}">
                                    <c:set var="productLink" value="${fn:substring(fn:substringAfter(productInfo.productLink, 'youtu.be/'), 0, fn:indexOf(fn:substringAfter(productInfo.productLink, 'youtu.be/'), '?'))}"/>
                                    <div class="prd_view_video">
                                        <div class="view_tit">제품 영상</div>
                                        <div class="video_wrap">
                                            <div class="embed-container">
                                                <iframe src="https://www.youtube.com/embed/${productLink}?autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:when test="${gbn eq 'O'}">
                                <c:if test="${onlineInfo.productIntroVideo ne null and onlineInfo.productIntroVideo ne ''}">
                                    <div class="prd_view_video">
                                        <div class="view_tit">제품 영상</div>
                                        <div class="video_wrap">
                                            <div class="embed-container">
                                                <iframe src="https://www.youtube.com/embed/${onlineInfo.productIntroVideo}?autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${onlineInfo.onlineLink ne null and onlineInfo.onlineLink ne ''}">
                                    <c:set var="onlineLink" value="${fn:substring(fn:substringAfter(onlineInfo.onlineLink, 'youtu.be/'), 0, fn:indexOf(fn:substringAfter(onlineInfo.onlineLink, 'youtu.be/'), '?'))}"/>
                                    <div class="prd_view_video">
                                        <div class="view_tit">제품 영상</div>
                                        <div class="video_wrap">
                                            <div class="embed-container">
                                                <iframe src="https://www.youtube.com/embed/${onlineLink}?autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="prdExhInfo">
                        <div class="prdExhLogo">
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
                        <div class="prdExhName">${exhibitorInfo.companyNameKo}</div>
                        <div class="prdExhBtn">
                            <a href="javascript:void(0);" onclick="f_ask_popup_open()" class="btnSt01">
                                문의하기
                            </a>
                            <c:choose>
                                <c:when test="${gbn eq 'PN'}">
                                    <a href="<c:url value="/online/company_view.do?seq=${productInfo.seq}"/>" class="exhGo btnSt01">기업정보 보러가기</a>
                                </c:when>
                                <c:when test="${gbn eq 'O'}">
                                    <a href="<c:url value="/online/company_view.do?seq=${onlineInfo.id}"/>" class="exhGo btnSt01">기업정보 보러가기</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/online/company_view.do?seq=${onlineInfo.seq}"/>" class="exhGo btnSt01">기업정보 보러가기</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="prd_view_same">
                    <div class="view_tit">같은 회사 제품</div>
                    <ul class="prdList">
                        <c:choose>
                            <c:when test="${gbn eq 'PN'}">
                                <c:choose>
                                    <c:when test="${empty productItemList}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="productItem" items="${productItemList}" begin="0" end="${productItemList.size()}" step="1" varStatus="status">
                                            <c:if test="${productItemList.size() eq 1 and empty onlineItemList and productItem.seq eq productInfo.seq}">
                                                -
                                            </c:if>
                                            <c:if test="${productItem.seq ne productInfo.seq and fn:trim(productItem.productNameKo) ne '-' and fn:trim(productItem.productNameKo) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${productItem.seq}"/>">
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
                                                            <div class="name">${exhibitorInfo.companyNameKo} / ${exhibitorInfo.companyNameEn}</div>
                                                            <div class="prdName">${productItem.productNameKo}</div>
                                                            <div class="type">${productItem.productOptionBig} > ${productItem.productOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                        <c:forEach var="onlineItem" items="${onlineItemList}" begin="0" end="${onlineItemList.size()}" step="1" varStatus="status">
                                            <c:if test="${fn:trim(onlineItem.onlineNameKo) ne '-' and fn:trim(onlineItem.onlineNameKo) ne '' and fn:trim(onlineItem.onlineNameEn) ne '-' and fn:trim(onlineItem.onlineNameEn) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${onlineItem.seq}"/>">
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
                                                            <div class="name">${exhibitorInfo.companyNameKo} / ${exhibitorInfo.companyNameEn}</div>
                                                            <div class="prdName">${onlineItem.onlineNameKo} ${onlineItem.onlineNameEn}</div>
                                                            <div class="type">${onlineItem.onlineOptionBig} > ${onlineItem.onlineOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${gbn eq 'O'}">
                                <c:choose>
                                    <c:when test="${empty onlineItemList}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="onlineItem" items="${onlineItemList}" begin="0" end="${onlineItemList.size()}" step="1" varStatus="status">
                                            <c:if test="${onlineItemList.size() eq 1 and onlineItem.seq eq onlineInfo.seq}">
                                                -
                                            </c:if>
                                            <c:if test="${onlineItem.seq ne onlineInfo.seq and fn:trim(onlineItem.productNameKo) ne '-' and fn:trim(onlineItem.productNameKo) ne '' and fn:trim(onlineItem.productNameEn) ne '-' and fn:trim(onlineItem.productNameEn) ne ''}">
                                            <li>
                                                <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                <a href="<c:url value="/online/product_view.do?seq=${onlineItem.seq}"/>">
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
                                                        <div class="name">${exhibitorInfo.companyNameKo} / ${exhibitorInfo.companyNameEn}</div>
                                                        <div class="prdName">${onlineItem.productNameKo} ${onlineItem.productNameEn}</div>
                                                        <div class="type">${onlineItem.productOptionBig} > ${onlineItem.productOptionSmall}</div>
                                                    </div>
                                                </a>
                                            </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${empty productItemList}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="productItem" items="${productItemList}" begin="0" end="${productItemList.size()}" step="1" varStatus="status">
                                            <c:if test="${fn:trim(productItem.productNameKo) ne '-' and fn:trim(productItem.productNameKo) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${productItem.seq}"/>">
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
                                                            <div class="name">${exhibitorInfo.companyNameKo} / ${exhibitorInfo.companyNameEn}</div>
                                                            <div class="prdName">${productItem.productNameKo}</div>
                                                            <div class="type">${productItem.productOptionBig} > ${productItem.productOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                        <c:forEach var="onlineItem" items="${onlineItemList}" begin="0" end="${onlineItemList.size()}" step="1" varStatus="status">
                                            <c:if test="${onlineItemList.size() eq 1 and empty productItemList and onlineItem.seq eq onlineInfo.seq}">
                                                -
                                            </c:if>
                                            <c:if test="${onlineItem.seq ne onlineInfo.seq and fn:trim(onlineItem.onlineNameKo) ne '-' and fn:trim(onlineItem.onlineNameKo) ne '' and fn:trim(onlineItem.onlineNameEn) ne '-' and fn:trim(onlineInfo.onlineNameEn) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${onlineItem.seq}"/>">
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
                                                            <div class="name">${exhibitorInfo.companyNameKo} / ${exhibitorInfo.companyNameEn}</div>
                                                            <div class="prdName">${onlineItem.onlineNameKo} ${onlineItem.onlineNameEn}</div>
                                                            <div class="type">${onlineItem.onlineOptionBig} > ${onlineItem.onlineOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="prd_view_rt">
                    <div class="view_tit">관련 제품</div>
                    <ul class="prdList">
                        <c:choose>
                            <c:when test="${gbn eq 'PN'}">
                                <c:choose>
                                    <c:when test="${empty relatedItemList}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="relatedItem" items="${relatedItemList}" begin="0" end="${relatedItemList.size()}" step="1">
                                            <c:if test="${fn:trim(relatedItem.productNameKo) ne '-' and fn:trim(relatedItem.productNameKo) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${relatedItem.seq}"/>">
                                                        <div class="thumbnail">
                                                            <div class="thumb75 thumbBox">
                                                                <c:forEach var="relatedItemFileInfo" items="${relatedItemFileList}" begin="0" end="${relatedItemFileList.size()}" step="1">
                                                                    <c:forEach var="relatedItemFileInfoSub" items="${relatedItemFileInfo}" begin="0" end="${relatedItemFileInfo.size()}" step="1">
                                                                        <c:if test="${relatedItem.id eq relatedItemFileInfoSub.userId and (relatedItemFileInfoSub.note eq 'productImage'.concat(relatedItem.note).concat('_1') or relatedItemFileInfoSub.note eq 'onlineImage'.concat(relatedItem.note).concat('_1'))}">
                                                                            <c:set var="relatedItemFileSrc" value="${fn:replace(relatedItemFileInfoSub.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                            <img src="${relatedItemFileSrc}" class="thumbImg">
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        <div class="prdTxt">
                                                            <div class="name">${relatedItem.companyNameKo} / ${relatedItem.companyNameEn}</div>
                                                            <div class="prdName">${relatedItem.productNameKo}</div>
                                                            <div class="type">${relatedItem.productOptionBig} > ${relatedItem.productOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${gbn eq 'O'}">
                                <c:choose>
                                    <c:when test="${empty relatedItemList}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="relatedItem" items="${relatedItemList}" begin="0" end="${relatedItemList.size()}" step="1">
                                            <c:if test="${fn:trim(relatedItem.productNameKo) ne '-' and fn:trim(relatedItem.productNameKo) ne '' and fn:trim(relatedItem.productNameEn) ne '-' and fn:trim(relatedItem.productNameEn) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${relatedItem.seq}"/>">
                                                        <div class="thumbnail">
                                                            <div class="thumb75 thumbBox">
                                                                <c:forEach var="relatedItemFileInfo" items="${relatedItemFileList}" begin="0" end="${relatedItemFileList.size()}" step="1">
                                                                    <c:forEach var="relatedItemFileInfoSub" items="${relatedItemFileInfo}" begin="0" end="${relatedItemFileInfo.size()}" step="1">
                                                                        <c:if test="${relatedItem.id eq relatedItemFileInfoSub.userId and relatedItemFileInfoSub.note eq 'productImage'.concat(relatedItem.note).concat('_1')}">
                                                                            <c:set var="relatedItemFileSrc" value="${fn:replace(relatedItemFileInfoSub.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                            <img src="${relatedItemFileSrc}" class="thumbImg">
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        <div class="prdTxt">
                                                            <div class="name">${relatedItem.companyNameKo} / ${relatedItem.companyNameEn}</div>
                                                            <div class="prdName">${relatedItem.productNameKo} ${relatedItem.productNameEn}</div>
                                                            <div class="type">${relatedItem.productOptionBig} > ${relatedItem.productOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${empty relatedItemList}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="relatedItem" items="${relatedItemList}" begin="0" end="${relatedItemList.size()}" step="1">
                                            <c:if test="${fn:trim(relatedItem.productNameKo) ne '-' and fn:trim(relatedItem.productNameKo) ne '' and fn:trim(relatedItem.productNameEn) ne '-' and fn:trim(relatedItem.productNameEn) ne ''}">
                                                <li>
                                                    <%--<div class="btnLike"><img src="/img/icon_like.png" class="likeImg"></div>--%>
                                                    <a href="<c:url value="/online/product_view.do?seq=${relatedItem.seq}"/>">
                                                        <div class="thumbnail">
                                                            <div class="thumb75 thumbBox">
                                                                <c:forEach var="relatedItemFileInfo" items="${relatedItemFileList}" begin="0" end="${relatedItemFileList.size()}" step="1">
                                                                    <c:forEach var="relatedItemFileInfoSub" items="${relatedItemFileInfo}" begin="0" end="${relatedItemFileInfo.size()}" step="1">
                                                                        <c:if test="${relatedItem.id eq relatedItemFileInfoSub.userId and relatedItemFileInfoSub.note eq 'onlineImage'.concat(relatedItem.note).concat('_1')}">
                                                                            <c:set var="relatedItemFileSrc" value="${fn:replace(relatedItemFileInfoSub.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                            <img src="${relatedItemFileSrc}" class="thumbImg">
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        <div class="prdTxt">
                                                            <div class="name">${relatedItem.companyNameKo} / ${relatedItem.companyNameEn}</div>
                                                            <div class="prdName">${relatedItem.productNameKo} ${relatedItem.productNameEn}</div>
                                                            <div class="type">${relatedItem.productOptionBig} > ${relatedItem.productOptionSmall}</div>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
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
                <div class="tit">공유하기</div>
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
                        <input type="button" value="복사">
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
                <div class="tit">문의하기</div>
                <a class="close" href="javascript:void(0);" onclick="f_ask_popup_close()"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- box -->
                    <ul class="pop_cs_box">
                        <li>
                            <div class="gubun">업체</div>
                            <div class="input">${exhibitorInfo.companyNameKo}</div>
                        </li>
                        <li>
                            <div class="gubun">이름</div>
                            <div class="input"><input type="text" id="askName" maxlength="15" placeholder="이름을 입력해 주세요."></div>
                        </li>
                        <li>
                            <div class="gubun">연락처</div>
                            <div class="input"><input type="tel" id="askPhone" class="onlyTel" maxlength="13" placeholder="하이픈('-') 자동입력"></div>
                        </li>
                        <li class="w100">
                            <div class="gubun">상담요청내용 ( <span id="smsRemain">90</span> / 90 Bytes 남음 )</div>
                            <div class="input"><textarea id="askContent" onblur="smsByteChk(this);" onkeyup="smsByteChk(this);" placeholder="상담요청내용을 작성해 주세요."></textarea></div>
                        </li>
                    </ul>

                    <div class="pop_cs_btn">
                        <a href="javascript:void(0);" onclick="f_ask_popup_close()" class="btnSt02">닫기</a>
                        <a href="javascript:f_ask_request('${exhibitorInfo.companyNameKo}','${exhibitorInfo.seq}')" class="btnSt01">문의</a>
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