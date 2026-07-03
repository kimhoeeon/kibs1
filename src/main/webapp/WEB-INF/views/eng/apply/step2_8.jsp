<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<% pageContext.setAttribute("CRLF", "\r\n"); %>
<% pageContext.setAttribute("LF", "\n"); %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <jsp:include page="../../inc/ad_scripts.jsp" flush="true"/>
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
                <a href="javascript:void(0);" onclick="home('en');" class="iconHome"><img src="/img/icon_home.png"></a>
                <div class="optionBox">
                    <div class="tabOpt1 tabOption">
                        <div class="tabOptAct">Exhibitors</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2026</a>
                            <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a href="/eng/buyer/glance.do">Buyers</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Exhibitor Registration</div>
                        <div class="tabOptSel">
                            <a href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                            <a class="active" href="/eng/apply/step01.do">Exhibitor Registration</a>
                            <a href="/eng/exhibitor/glance.do">Korea at a Glance</a>
                            <a href="/eng/exhibitor/marina.do">Marine Industry in korea</a>
                            <a href="/eng/exhibitor/match.do">Exhibitor-Buyer Matchmaking Program</a>
                            <a href="/eng/online/company.do">Exhibitor List</a>
                            <a href="/eng/exhibitor/qna.do">FAQ</a>
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
                        <li><a>Basic Info</a></li>
                        <li class="active">
                            <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step2_1.do','${info.seq}')">Exhibition Info</a>
                            <ul class="list2">
                                <li><a>Booth</a></li>
                                <li><a>Yacht/Boat</a></li>
                                <li><a>Signboard</a></li>
                                <li><a>Utilities</a></li>
                                <li><a>Badges</a></li>
                                <li><a>Giveaways</a></li>
                                <li class="active"><a>Directory</a></li>
                            </ul>
                        </li>
                        <li><a>Complete</a></li>
                    </ul>
                </div>
            </div>

            <div class="apply_step">
                <div class="apply_tit">Directory Info</div>
                <div class="apply_step_wrap">
                    <div class="apply_step_box step1">
                        <div class="gubun">
                            <p>Step 1</p>Basic Info
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="100" max="100"></progress>
                        </div>
                        <div class="txt">Completed</div>
                    </div>
                    <div class="apply_step_box step2">
                        <div class="gubun">
                            <p>Step 2</p>Exhibition Info
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="87.5" max="100"></progress>
                        </div>
                        <div class="txt">In Progress <p>(<span>7</span> of <span>6</span> completed)</p></div>
                    </div>
                    <div class="apply_step_box step3">
                        <div class="gubun">
                            <p>Step 3</p>Complete
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="0" max="100"></progress>
                        </div>
                        <div class="txt">Requirement Not Met</div>
                    </div>
                </div>
            </div>

            <!-- section -->
            <div class="apply_form my_form form_s">
                <div class="inner">
                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>This form is required for all exhibitors and is free of charge.</li>
                            <li>Please complete all information below, as it may be used for promotional materials.</li>
                            <li>Information entered in previous sections will be used. Please update it in the previous section or My Page if needed.</li>
                            <li>Click “Next” at the bottom right to complete your submission.</li>
                        </ul>
                    </div>

                    <%--<div class="load_btn">
                        <button type="button" onclick="f_exhibitor_info_call()">입력정보 불러오기</button>
                    </div>--%>

                    <!-- 참가업체 정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Exhibitor Info</div>
                        </div>
                        <ul class="form_box">
                            <li>
                                <div class="item">
                                    <p>Business No.</p>
                                </div>
                                <div class="input" style="display: flex; align-items: center;">
                                    ${info.companyLicenseNum}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Company Name (Korean)</p>
                                </div>
                                <div class="input">
                                    ${info.companyNameKo}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Company Name (English)</p>
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
                                    ${info.industryPart}
                                    <c:if test="${info.industryPart eq '기타'}">
                                        ( ${info.industryPartEtc} )
                                    </c:if>
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Business License</p>
                                </div>
                                <div class="input file_box">
                                    <c:set var="companyLicenseFileSrc" value="${fn:replace(companyLicenseFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                    <c:if test="${not fn:contains(companyLicenseFileSrc, '.ai') and not fn:contains(companyLicenseFileSrc, '.pdf')}">
                                        <img src="${companyLicenseFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                    </c:if>
                                    <%--<a href="/file/download.do?path=exhibitor/company/${companyLicenseFile.folderPath}&fileName=${companyLicenseFile.fullFileName}">${companyLicenseFile.fileName}</a>--%>
                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${companyLicenseFile.folderPath}', '${companyLicenseFile.fullFileName}')">${companyLicenseFile.fileName}</a>
                                    <input type="hidden" name="companyLicenseUploadFile" id="${companyLicenseFile.id}" value="${companyLicenseFile.fullFilePath}">
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

                    <!-- 요트/보트 출품 정보 -->
                    <%--<div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">요트/보트 출품 정보</div>
                        </div>
                        <c:if test="${not empty productList}">
                            <c:forEach var="product" items="${productList}" begin="0" end="${productList.size()}" step="1" varStatus="status">
                                <ul class="form_box exhiInfoBox">
                                    <li class="form_in_tit">
                                        요트/보트 출품 정보 #<span class="exhiInfoNum">${status.index + 1}</span>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품 분류(품목)</p>
                                        </div>
                                        <div class="input">
                                            ${product.productOptionBig} / ${product.productOptionSmall}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품명</p>
                                        </div>
                                        <div class="input">
                                            ${product.productNameKo}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>수량</p>
                                        </div>
                                        <div class="input">
                                            ${product.productQty}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제조사(브랜드)</p>
                                        </div>
                                        <div class="input">
                                            ${product.productBrand}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>길이(cm)</p>
                                        </div>
                                        <div class="input">
                                            ${product.productLength}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>너비(cm)</p>
                                        </div>
                                        <div class="input">
                                            ${product.productWidth}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>높이(cm)</p>
                                        </div>
                                        <div class="input">
                                            ${product.productHeight}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>중량(kg)</p>
                                        </div>
                                        <div class="input">
                                            ${product.productWeight}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>소재</p>
                                        </div>
                                        <div class="input">
                                            ${product.productMaterial}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>연식</p>
                                        </div>
                                        <div class="input">
                                            ${product.productYear}
                                        </div>
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>--%>
                    <!-- // 요트/보트 출품 정보 -->

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
                            <li class="w50">
                                <div class="item">
                                    <p>Company Profile (Korean)</p>
                                </div>
                                <div class="input">
                                    ${fn:replace(fn:replace(fn:escapeXml(info.companyIntroKo), CRLF, '<br/>'), LF, '<br/>')}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Company Profile (English)</p>
                                </div>
                                <div class="input">
                                    ${fn:replace(fn:replace(fn:escapeXml(info.companyIntroEn), CRLF, '<br/>'), LF, '<br/>')}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Purpose of KIBS Participation (Korean)</p>
                                </div>
                                <div class="input">
                                    ${info.companyPurposeKo}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>Purpose of KIBS Participation (English)</p>
                                </div>
                                <div class="input">
                                    ${info.companyPurposeEn}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>New Product Info (Korean)</p>
                                </div>
                                <div class="input">
                                    ${fn:replace(fn:replace(fn:escapeXml(info.newItemIntroKo), CRLF, '<br/>'), LF, '<br/>')}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>New Product Info (English)</p>
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
                            <div class="small">Giveaways</div>
                        </a>
                        <a href="javascript:void(0);" onclick="f_apply_comp('2_8','${info.seq}')" class="form_btn_next">
                            <div class="big">NEXT</div>
                            <div class="small">Complete</div>
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