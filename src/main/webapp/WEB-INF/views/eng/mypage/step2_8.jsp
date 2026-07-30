<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<% pageContext.setAttribute("CRLF", "\r\n"); %>
<% pageContext.setAttribute("LF", "\n"); %>
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
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_5.do','${info.seq}')">Giveaways</a></li>
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_8.do','${info.seq}')">Directory</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step03.do','${info.seq}')">Complete</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">
                            <div class="apply_tit">Directory Info</div>
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <li>This form is required for all exhibitors and is free of charge.</li>
                                    <li>The information below may be used for promotional materials. Please review all information carefully before final submission.</li>
                                    <li>Information entered in previous sections will be displayed here. If any changes are needed, please update the relevant previous section or My Page before submitting.</li>
                                    <li>Click the “Next” button at the bottom right to complete your exhibitor registration.</li>
                                </ul>
                            </div>

                            <%--<div class="load_btn">
                                <button type="button" onclick="">Get input information</button>
                            </div>--%>

                            <!-- 참가업체 정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Exhibitor Info</div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item">
                                            <p>Company Name</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyNameEn}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>Address</p>
                                        </div>
                                        <div class="input address">
                                            <div class="address_box">
                                                ${info.companyAddress} ${info.companyAddressDetail}
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>Factory Address</p>
                                        </div>
                                        <div class="input address">
                                            <div class="address_box">
                                                ${info.factoryAddress} ${info.factoryAddressDetail}
                                            </div>
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>CEO</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyCeo}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Tel</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyTel}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Website</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyHomepage}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Fax</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyFax}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Category</p>
                                        </div>
                                        <div class="input">
                                            <c:choose>
                                                <c:when test="${info.industryPart eq '요트/보트 제조'}">Yacht/Boat Manufacturing</c:when>
                                                <c:when test="${info.industryPart eq '요트/보트 유통(수입 판매)'}">Yacht/Boat Distribution</c:when>
                                                <c:when test="${info.industryPart eq '해양 부품(엔진 및 구성품) 제조'}">Marine Parts Manufacturing</c:when>
                                                <c:when test="${info.industryPart eq '해양 부품(엔진 및 구성품) 판매'}">Marine Parts Sales</c:when>
                                                <c:when test="${info.industryPart eq '해양 관련 기자재 제조 및 판매'}">Marine Equipment</c:when>
                                                <c:when test="${info.industryPart eq '수상레저 용품 제조 및 판매'}">Water Leisure Products</c:when>
                                                <c:when test="${info.industryPart eq '수중레저 용품 제조 및 판매'}">Diving Products</c:when>
                                                <c:when test="${info.industryPart eq '아웃도어 용품 제조 및 판매'}">Outdoor Products</c:when>
                                                <c:when test="${info.industryPart eq '마리나 산업'}">Marina Industry</c:when>
                                                <c:when test="${info.industryPart eq '교육/면허'}">Training & Licensing</c:when>
                                                <c:when test="${info.industryPart eq '수리'}">Repair</c:when>
                                                <c:when test="${info.industryPart eq '기타(경기국제보트쇼)'}">Other(KIBS)</c:when>
                                                <c:when test="${info.industryPart eq '서핑장비'}">Surf Equipment</c:when>
                                                <c:when test="${info.industryPart eq '의류&패션'}">Apparel & Fashion</c:when>
                                                <c:when test="${info.industryPart eq '체험&교육프로그램'}">Programs & Training</c:when>
                                                <c:when test="${info.industryPart eq '라이프스타일'}">Lifestyle</c:when>
                                                <c:when test="${info.industryPart eq '기타(코리아서프쇼)'}">Other(KISS)</c:when>
                                                <c:when test="${info.industryPart eq '해양레저 관광상품'}">Marine Tourism</c:when>
                                                <c:when test="${info.industryPart eq '지역관광콘텐츠'}">Local Tourism</c:when>
                                                <c:when test="${info.industryPart eq '치유&생태관광'}">Wellness & Ecotourism</c:when>
                                                <c:when test="${info.industryPart eq '체험 콘텐츠'}">Experience Programs</c:when>
                                                <c:when test="${info.industryPart eq '기타(해양관광전)'}">Other(KMTS)</c:when>
                                                <c:otherwise>${info.industryPart}</c:otherwise>
                                            </c:choose>
                                            <c:if test="${info.industryPart eq '기타'}">
                                                ( ${info.industryPartEtc} )
                                            </c:if>
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Company Logo</p>
                                        </div>
                                        <div class="input file_box">
                                            <c:set var="logoFileSrc" value="${fn:replace(logoFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                            <c:if test="${not fn:contains(logoFileSrc, '.ai') and not fn:contains(logoFileSrc, '.pdf')}">
                                                <img src="${logoFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                            </c:if>
                                            <%--<a href="/file/download.do?path=exhibitor/company/${logoFile.folderPath}&fileName=${logoFile.fullFileName}">${logoFile.fileName}</a>--%>
                                            <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${logoFile.folderPath}', '${logoFile.fullFileName}')">${logoFile.fileName}</a>
                                            <input type="hidden" name="companyLicenseUploadFile" id="${logoFile.id}" value="${logoFile.fullFilePath}">
                                        </div>
                                    </li>
                                </ul>
                            </div>

                            <!-- 담당자 정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Contact Info</div>
                                </div>
                                <ul class="form_box">
                                    <li class="form_in_tit">Primary Contact</li>
                                    <li>
                                        <div class="item">
                                            <p>Name</p>
                                        </div>
                                        <div class="input">
                                            ${info.name}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Position</p>
                                        </div>
                                        <div class="input">
                                            ${info.position}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Department</p>
                                        </div>
                                        <div class="input">
                                            ${info.depart}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Tel</p>
                                        </div>
                                        <div class="input">
                                            ${info.tel}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>Mobile</p>
                                        </div>
                                        <div class="input">
                                            ${info.phone}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>E-mail</p>
                                        </div>
                                        <div class="input email">
                                            ${info.email}
                                        </div>
                                    </li>
                                </ul>

                                <c:if test="${not empty chargeList}">
                                    <c:forEach var="charge" items="${chargeList}" begin="0" end="${chargeList.size()}" step="1" varStatus="status">
                                        <ul class="form_box managerInfoBox">
                                            <li class="form_in_tit">
                                                Contact #<span class="managerInfoNum">${status.index + 1}</span>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Name</p>
                                                </div>
                                                <div class="input">
                                                    ${charge.chargePersonName}
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Position</p>
                                                </div>
                                                <div class="input">
                                                    ${charge.chargePersonPosition}
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Department</p>
                                                </div>
                                                <div class="input">
                                                    ${charge.chargePersonDepart}
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Tel</p>
                                                </div>
                                                <div class="input">
                                                    ${charge.chargePersonTel}
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Mobile</p>
                                                </div>
                                                <div class="input">
                                                    ${charge.chargePersonPhone}
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>E-mail</p>
                                                </div>
                                                <div class="input email">
                                                    ${charge.chargePersonEmail}
                                                </div>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <!-- //담당자정보 -->

                            <!-- 상세정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Details</div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item">
                                            <p>Company Video</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyIntroVideo}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>Company Profile</p>
                                        </div>
                                        <div class="input">
                                            ${fn:replace(fn:replace(fn:escapeXml(info.companyIntroEn), CRLF, '<br/>'), LF, '<br/>')}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>Purpose of KIBS Participation</p>
                                        </div>
                                        <div class="input">
                                            ${info.companyPurposeEn}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>New Product Info</p>
                                        </div>
                                        <div class="input">
                                            ${fn:replace(fn:replace(fn:escapeXml(info.newItemIntroEn), CRLF, '<br/>'), LF, '<br/>')}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>Promotion Info</p>
                                        </div>
                                        <div class="input">
                                            ${fn:replace(fn:replace(fn:escapeXml(info.promotionPlan), CRLF, '<br/>'), LF, '<br/>')}
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <!-- //상세정보 -->

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step2_5.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">PREV</div>
                                    <div class="small">Giveaway Application</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_apply_comp('2_8','${info.seq}')" class="form_btn_next">
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

    </c:if>

</body>
</html>