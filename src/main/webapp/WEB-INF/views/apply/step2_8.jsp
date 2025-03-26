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
    <title>2025 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <span itemscope="" itemtype="http://schema.org/Organization">
    <link itemprop="url" href="https://kibs.com/">
    <a itemprop="sameAs" href="https://www.koreaboatshow.or.kr/"></a>
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
                    <span>홈</span><span>참가기업</span><span>참가기업 신청</span>
                </div>
                <div class="sub_top_tit">참가기업 신청</div>
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
                        <div class="tabOptAct">참가기업</div>
                        <div class="tabOptSel">
                            <a href="/guide/notice.do">KIBS 안내</a>
                            <a class="active" href="/exhibitor/guide.do">참가기업</a>
                            <a href="/visitor/guide_off.do">참관객</a>
                            <a href="/board/gallery.do">자료실</a>
                            <a href="/online/kibstv.do">온라인 전시관</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">참가기업 신청</div>
                        <div class="tabOptSel">
                            <a href="/exhibitor/guide.do">참가기업 안내</a>
                            <a class="active" href="/apply/step01.do">참가기업 신청</a>
                            <a href="/exhibitor/login.do">참가신청확인</a>
                            <a href="/exhibitor/faq.do">FAQ</a>
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
                <!-- <div class="apply_nav_tit">기본정보</div> -->
                <div class="apply_nav_list">
                    <ul class="list1">
                        <li><a>기본정보</a></li>
                        <li class="active">
                            <a>전시 신청 정보</a>
                            <ul class="list2">
                                <li><a>전시부스 신청</a></li>
                                <li><a>상호간판 신청</a></li>
                                <li><a>유틸리티 신청</a></li>
                                <li><a>출입증 신청</a></li>
                                <li><a>경품제공 신청</a></li>
                                <%--<li><a href="/apply/step2_6.do">웹 배너 신청</a></li>--%>
                                <%--<li><a href="/apply/step2_7.do">온라인 전시관 신청</a></li>--%>
                                <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/apply/step2_8.do','${info.seq}')">디렉토리 정보</a></li>
                            </ul>
                        </li>
                        <li><a>전시참가 신청 완료</a></li>
                    </ul>
                </div>
            </div>

            <div class="apply_step">
                <div class="apply_tit">디렉토리 정보</div>
                <div class="apply_step_wrap">
                    <div class="apply_step_box step1">
                        <div class="gubun">
                            <p>Step 1</p>기본정보 입력
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="100" max="100"></progress>
                        </div>
                        <div class="txt">완료됨</div>
                    </div>
                    <div class="apply_step_box step2">
                        <div class="gubun">
                            <p>Step 2</p>전시 신청 정보
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="50" max="60"></progress>
                        </div>
                        <div class="txt">진행중...<p>(총 <span>6</span>개 중 <span>5</span> 완료)</p></div>
                    </div>
                    <div class="apply_step_box step3">
                        <div class="gubun">
                            <p>Step 3</p>전시참가 신청 완료
                        </div>
                        <div class="gauge">
                            <progress class="gauge_bar" value="0" max="100"></progress>
                        </div>
                        <div class="txt">진행조건이 충족되지 않음</div>
                    </div>
                </div>
            </div>


            <!-- section -->
            <div class="apply_form my_form form_s">
                <div class="inner">
                    <div class="form_wrap">
                        <ul class="form_guide">
                            <li>본 양식은 행사에 참가하는 <span class="txtRed" style="font-weight: 700;">모든 업체가 신청</span>하여야 하며 <span class="txtRed" style="font-weight: 700;">비용은 무료</span>입니다.</li>
                            <li>출품업체 디렉토리는 제작되지 않으나, 홍보자료로 활용될 예정이오니 하기의 사항을 <span class="txtRed" style="font-weight: 700;">빠짐없이 온라인에서 입력하여 확인</span>해 주시기 바랍니다.</li>
                            <li>이전 탭에서 입력한 정보를 기준으로 활용됩니다. <span class="txtRed" style="font-weight: 700;">수정이 필요할 경우 이전 탭이나 마이페이지에서 수정</span>해 주시기 바랍니다.</li>
                            <li><span class="txtRed" style="font-weight: 700;">페이지 우측 최하단 '다음' 버튼을 클릭하여야 최종 제출</span>됩니다.</li>
                        </ul>
                    </div>

                    <%--<div class="load_btn">
                        <button type="button" onclick="f_exhibitor_info_call()">입력정보 불러오기</button>
                    </div>--%>

                    <!-- 참가업체 정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">참가업체 정보</div>
                        </div>
                        <ul class="form_box">
                            <li class="w50">
                                <div class="item">
                                    <p>회사명(국문)</p>
                                </div>
                                <div class="input">
                                    ${info.companyNameKo}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>회사명(영문)</p>
                                </div>
                                <div class="input">
                                    ${info.companyNameEn}
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>본사 주소</p>
                                </div>
                                <div class="input address">
                                    <div class="address_box">
                                        ${info.companyAddress} ${info.companyAddressDetail}
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="item">
                                    <p>공장 주소</p>
                                </div>
                                <div class="input address">
                                    <div class="address_box">
                                        ${info.factoryAddress} ${info.factoryAddressDetail}
                                    </div>
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>대표자</p>
                                </div>
                                <div class="input">
                                    ${info.companyCeo}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>전화</p>
                                </div>
                                <div class="input">
                                    ${info.companyTel}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>홈페이지</p>
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
                                    <p>산업 분류</p>
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
                                    <p>임직원 수</p>
                                </div>
                                <div class="input">
                                    ${info.employeeCnt}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>사업자등록증</p>
                                </div>
                                <div class="input file_box">
                                    <c:set var="companyLicenseFileSrc" value="${fn:replace(companyLicenseFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
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
                                    <p>사업자등록번호</p>
                                </div>
                                <div class="input" style="display: flex; align-items: center;">
                                    ${info.companyLicenseNum}
                                </div>
                            </li>
                            <li class="form_in_tit">SNS (주소기입)</li>
                            <li class="w50">
                                <div class="item">
                                    <p>블로그</p>
                                </div>
                                <div class="input">
                                    ${info.snsBlog}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>페이스북</p>
                                </div>
                                <div class="input">
                                    ${info.snsFacebook}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>인스타그램</p>
                                </div>
                                <div class="input">
                                    ${info.snsInstagram}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>기타</p>
                                </div>
                                <div class="input">
                                    ${info.snsEtc}
                                </div>
                            </li>
                        </ul>
                    </div>

                    <!-- 담당자 정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">담당자 정보</div>
                        </div>

                        <c:if test="${not empty chargeList}">
                            <c:forEach var="charge" items="${chargeList}" begin="0" end="${chargeList.size()}" step="1" varStatus="status">
                                <ul class="form_box managerInfoBox">
                                    <li class="form_in_tit">
                                        담당자 #<span class="managerInfoNum">${status.index + 1}</span>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>성명</p>
                                        </div>
                                        <div class="input">
                                            ${charge.chargePersonName}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>직위</p>
                                        </div>
                                        <div class="input">
                                            ${charge.chargePersonPosition}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>부서</p>
                                        </div>
                                        <div class="input">
                                            ${charge.chargePersonDepart}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>전화번호</p>
                                        </div>
                                        <div class="input">
                                            ${charge.chargePersonTel}
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>휴대전화</p>
                                        </div>
                                        <div class="input">
                                            ${charge.chargePersonPhone}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>이메일</p>
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

                    <!-- 전시정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">전시정보</div>
                        </div>
                        <c:if test="${not empty displayList}">
                            <c:forEach var="display" items="${displayList}" begin="0" end="${displayList.size()}" step="1" varStatus="status">
                                <ul class="form_box exhiInfoBox">
                                    <li class="form_in_tit">
                                        전시정보 #<span class="exhiInfoNum">${status.index + 1}</span>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>전시품목</p>
                                        </div>
                                        <div class="input">
                                            ${display.displayItem}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>전시품목<br>브랜드명</p>
                                        </div>
                                        <div class="input">
                                            ${display.displayBrand}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>실물 보트수<br>(단위:척)</p>
                                        </div>
                                        <div class="input">
                                            ${display.displayBoatCnt}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>전시품소개(국문)</p>
                                        </div>
                                        <div class="input">
                                            ${display.displayItemIntroKo}
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>전시품소개(영문)</p>
                                        </div>
                                        <div class="input">
                                            ${display.displayItemIntroEn}
                                        </div>
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <!-- //전시정보 -->

                    <!-- 상세정보 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">상세정보</div>
                        </div>
                        <ul class="form_box">
                            <li class="w50">
                                <div class="item">
                                    <p>회사소개(국문)</p>
                                </div>
                                <div class="input">
                                    ${info.companyIntroKo}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>회사소개(영문)</p>
                                </div>
                                <div class="input">
                                    ${info.companyIntroEn}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>KIBS 참가목적(국문)</p>
                                </div>
                                <div class="input">
                                    ${info.companyPurposeKo}
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item">
                                    <p>KIBS 참가목적(영문)</p>
                                </div>
                                <div class="input">
                                    ${info.companyPurposeEn}
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- //상세정보 -->

                    <div class="form_btn">
                        <a href="javascript:void(0);" onclick="f_page_move('/apply/step2_5.do','${info.seq}')" class="form_btn_prev">
                            <div class="big">이전</div>
                            <div class="small">경품제공 신청</div>
                        </a>
                        <a href="javascript:void(0);" onclick="f_apply_comp('2_8','${info.seq}')" class="form_btn_next">
                            <div class="big">다음</div>
                            <div class="small">등록 완료</div>
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