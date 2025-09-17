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

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />

    <link href="/css/reset.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/font.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/style.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">
    <link href="/css/responsive.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.5/dist/sweetalert2.all.min.js"></script>
    <%--script
    <script src="/js/jquery-1.9.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
    --%>
    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/jquery-migrate-3.3.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
    <script src="/js/jquery.cookie.min.js"></script>
    <script src="/js/jquery.ui.touch-punch.min.js"></script>

    <script src="/js/jquery.number.min.js"></script>
    <script src="/js/resize/compressor.js"></script>
    <script src="/js/script.js?ver=<%=System.currentTimeMillis()%>"></script>
    <script src="/js/swiper.js"></script>
    <script src="/js/main.js?ver=<%=System.currentTimeMillis()%>"></script>

    <style>
        .apply_s .inner { margin-top: 20px; }
    </style>
</head>
<!--end::Head-->

<!--begin::Body-->
<body>

    <!-- section -->
    <div class="apply_s" id="apply_s">
        <div class="inner">

            <!-- section -->
            <div class="form_s">

                <form id="exhibitor_apply_form" method="post" onsubmit="return false;" enctype="multipart/form-data">
                    <div class="inner">

                        <!-- 안내문구 -->
                        <%--<div class="form_wrap">
                            <ul class="form_guide">
                                &lt;%&ndash;<li>2025 경기국제보트쇼 종료로 인해 <span class="txtRed">일부 전시 정보 수정이 불가</span>합니다.</li>&ndash;%&gt;
                            </ul>
                        </div>--%>
                        <!-- 안내문구 -->

                        <!-- 신청정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">신청 정보</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item">
                                        <p>신청서 작성 상태</p>
                                    </div>
                                    <div class="input">
                                        <c:choose>
                                            <c:when test="${info.applyComplt eq 'N'}">
                                                작성중
                                            </c:when>
                                            <c:otherwise>
                                                작성완료
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>참가비 납부 상태</p>
                                    </div>
                                    <div class="input">
                                        <c:choose>
                                            <c:when test="${info.prcYn eq '0'}">미납</c:when>
                                            <c:when test="${info.prcYn eq '1'}">참가비 납부</c:when>
                                            <c:when test="${info.prcYn eq '2'}">50% 납부</c:when>
                                            <c:when test="${info.prcYn eq '3'}">전액 납부</c:when>
                                            <c:when test="${info.prcYn eq '4'}">완납(부대시설비)</c:when>
                                        </c:choose>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>참가 상태</p>
                                    </div>
                                    <div class="input">
                                        ${info.approvalStatus}
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>신청서 작성 여부</p>
                                    </div>
                                    <div class="input">
                                        ${info.applyComplt}
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <!-- 회원계정정보 -->

                        <!-- 회원계정정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">회원 계정 정보</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>아이디</p>
                                    </div>
                                    <div class="input">
                                        <input type="hidden" id="id" name="id" value="${info.id}"/>
                                        ${info.id}
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <!-- 회원계정정보 -->

                        <!-- 참가업체 정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">참가업체 정보</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>사업자등록번호</p>
                                    </div>
                                    <div class="input" style="display: flex; align-items: center;">
                                        <input type="text" id="companyLicenseNum" name="companyLicenseNum" value="${info.companyLicenseNum}" class="onlyNum" placeholder="아이디와 동일한 값이 자동 입력됩니다." readonly>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>회사명</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="companyNameKo" name="companyNameKo" value="${info.companyNameKo}" placeholder="국문" class="w50">
                                        <input type="text" id="companyNameEn" name="companyNameEn" value="${info.companyNameEn}" placeholder="영문" class="w50 onlyNumEng">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>본사 주소</p>
                                    </div>
                                    <div class="input address">
                                        <div class="address_box">
                                            <input type="text" id="companyAddress" name="companyAddress" value="${info.companyAddress}" class="w50" style="margin-right: 10px;" placeholder="본사 주소">
                                            <input type="button" onclick="execDaumPostcode('companyAddress','companyAddressDetail')" value="주소 검색"><br>
                                        </div>
                                        <div class="address_box" style="margin-top: 10px;">
                                            <input type="text" id="companyAddressDetail" name="companyAddressDetail" value="${info.companyAddressDetail}" placeholder="본사 상세 주소" class="w50">
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>공장 주소</p>
                                    </div>
                                    <div class="input address">
                                        <div class="address_box">
                                            <input type="text" id="factoryAddress" name="factoryAddress" value="${info.factoryAddress}" class="w50" style="margin-right: 10px;" placeholder="공장 주소">
                                            <input type="button" onclick="execDaumPostcode('factoryAddress','factoryAddressDetail')" value="주소 검색"><br>
                                        </div>
                                        <div class="address_box" style="margin-top: 10px;">
                                            <input type="text" id="factoryAddressDetail" name="factoryAddressDetail" value="${info.factoryAddressDetail}" placeholder="공장 상세 주소" class="w50">
                                        </div>
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item req">
                                        <p>대표자</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="companyCeo" name="companyCeo" value="${info.companyCeo}" placeholder="대표자명">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item req">
                                        <p>전화</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" id="companyTel" name="companyTel" value="${info.companyTel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item">
                                        <p>홈페이지</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="companyHomepage" name="companyHomepage" value="${info.companyHomepage}" placeholder="https://">
                                        <label><input type="checkbox" name="noPage" <c:if test="${empty info.companyHomepage}">checked</c:if> >홈페이지 없음</label>
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item">
                                        <p>Fax</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" id="companyFax" name="companyFax" value="${info.companyFax}" class="onlyNumh" maxlength="25" placeholder="숫자만 입력해 주세요.">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>산업 분류</p>
                                    </div>
                                    <div class="input">
                                        <select name="industryPart" id="industryPart">
                                            <option value="" <c:if test="${empty info.industryPart}">selected</c:if>>선택</option>

                                            <optgroup label="---- 경기국제보트쇼 ----">
                                                <option value="요트/보트 제조" <c:if test="${info.industryPart eq '요트/보트 제조'}">selected</c:if>>요트/보트 제조</option>
                                                <option value="요트/보트 유통(수입 판매)" <c:if test="${info.industryPart eq '요트/보트 유통(수입 판매)'}">selected</c:if>>요트/보트 유통(수입 판매)</option>
                                                <option value="해양 부품(엔진 및 구성품) 제조" <c:if test="${info.industryPart eq '해양 부품(엔진 및 구성품) 제조'}">selected</c:if>>해양 부품(엔진 및 구성품) 제조</option>
                                                <option value="해양 부품(엔진 및 구성품) 판매" <c:if test="${info.industryPart eq '해양 부품(엔진 및 구성품) 판매'}">selected</c:if>>해양 부품(엔진 및 구성품) 판매</option>
                                                <option value="해양 관련 기자재 제조 및 판매" <c:if test="${info.industryPart eq '해양 관련 기자재 제조 및 판매'}">selected</c:if>>해양 관련 기자재 제조 및 판매</option>
                                                <option value="수상레저 용품 제조 및 판매" <c:if test="${info.industryPart eq '수상레저 용품 제조 및 판매'}">selected</c:if>>수상레저 용품 제조 및 판매</option>
                                                <option value="수중레저 용품 제조 및 판매" <c:if test="${info.industryPart eq '수중레저 용품 제조 및 판매'}">selected</c:if>>수중레저 용품 제조 및 판매</option>
                                                <option value="아웃도어 용품 제조 및 판매" <c:if test="${info.industryPart eq '아웃도어 용품 제조 및 판매'}">selected</c:if>>아웃도어 용품 제조 및 판매</option>
                                                <option value="마리나 산업" <c:if test="${info.industryPart eq '마리나 산업'}">selected</c:if>>마리나 산업</option>
                                                <option value="교육/면허" <c:if test="${info.industryPart eq '교육/면허'}">selected</c:if>>교육/면허</option>
                                                <option value="수리" <c:if test="${info.industryPart eq '수리'}">selected</c:if>>수리</option>
                                                <option value="기타(경기국제보트쇼)" <c:if test="${info.industryPart eq '기타(경기국제보트쇼)'}">selected</c:if>>기타</option>
                                            </optgroup>

                                            <optgroup label="---- 코리아서프쇼 ----">
                                                <option value="서핑장비" <c:if test="${info.industryPart eq '서핑장비'}">selected</c:if>>서핑장비</option>
                                                <option value="의류&패션" <c:if test="${info.industryPart eq '의류&패션'}">selected</c:if>>의류&패션</option>
                                                <option value="체험&교육프로그램" <c:if test="${info.industryPart eq '체험&교육프로그램'}">selected</c:if>>체험&교육프로그램</option>
                                                <option value="라이프스타일" <c:if test="${info.industryPart eq '라이프스타일'}">selected</c:if>>라이프스타일</option>
                                                <option value="기타(코리아서프쇼)" <c:if test="${info.industryPart eq '기타(코리아서프쇼)'}">selected</c:if>>기타</option>
                                            </optgroup>

                                            <optgroup label="---- 코리아마린트레블쇼 ----">
                                                <option value="해양레저 관광상품" <c:if test="${info.industryPart eq '해양레저 관광상품'}">selected</c:if>>해양레저 관광상품</option>
                                                <option value="지역관광콘텐츠" <c:if test="${info.industryPart eq '지역관광콘텐츠'}">selected</c:if>>지역관광콘텐츠</option>
                                                <option value="치유&생태관광" <c:if test="${info.industryPart eq '치유&생태관광'}">selected</c:if>>치유&생태관광</option>
                                                <option value="체험 콘텐츠" <c:if test="${info.industryPart eq '체험 콘텐츠'}">selected</c:if>>체험 콘텐츠</option>
                                                <option value="기타(코리아마린트레블쇼)" <c:if test="${info.industryPart eq '기타(코리아마린트레블쇼)'}">selected</c:if>>기타</option>
                                            </optgroup>
                                        </select>
                                        <input type="text" id="industryPartEtc" name="industryPartEtc" class="w50" value="${info.industryPartEtc}" placeholder="직접 입력" disabled>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>사업자등록증</p>
                                    </div>
                                    <div class="input file_box d-flex align-items-center">
                                        <input type="text" id="companyLicense" name="companyLicense" class="upload_name" value="" placeholder="File" disabled="disabled">
                                        <input type="file" id="companyLicenseFile" class="upload_hidden" accept=".png, .jpg, .jpeg, .pdf">
                                        <label for="companyLicenseFile">파일선택</label>
                                        <div class="cmnt">PDF, JPG, PNG형식, 10MB 이하만 가능합니다.</div>
                                    </div>
                                </li>
                                <c:if test="${companyLicenseFile ne null and not empty companyLicenseFile}">
                                    <li>
                                        <div class="item">
                                            <p>사업자등록증 파일</p>
                                        </div>
                                        <div class="input file_box">
                                            <ul>
                                                <li class="companyLicenseFile_li" style="align-items: center;">
                                                    <c:set var="companyLicenseFileSrc" value="${fn:replace(companyLicenseFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                    <c:if test="${not fn:contains(companyLicenseFileSrc, '.ai') and not fn:contains(companyLicenseFileSrc, '.pdf')}">
                                                        <img src="${companyLicenseFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                    </c:if>
                                                        <%--<a href="/file/download.do?path=exhibitor/company/${companyLicenseFile.folderPath}&fileName=${companyLicenseFile.fullFileName}">${companyLicenseFile.fileName}</a>--%>
                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${companyLicenseFile.folderPath}', '${companyLicenseFile.fullFileName}')">${companyLicenseFile.fileName}</a>
                                                    <input type="hidden" name="companyLicenseUploadFile" id="${companyLicenseFile.id}" value="${companyLicenseFile.fullFilePath}">
                                                    <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${companyLicenseFile.id}')">X</button>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </c:if>
                                <li>
                                    <div class="item req">
                                        <p>로고</p>
                                    </div>
                                    <div class="input file_box">
                                        <input type="text" id="logo" class="upload_name" value="" placeholder="File" disabled="disabled">
                                        <input type="file" id="logoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                        <label for="logoFile">파일선택</label>
                                        <div class="cmnt">JPG, PNG형식, 10MB 이하만 가능합니다. (권장 사이즈 : 150x150px)</div>
                                    </div>
                                </li>
                                <c:if test="${logoFile ne null and not empty logoFile}">
                                    <li class="w50">
                                        <div class="item">
                                            <p>로고 파일</p>
                                        </div>
                                        <div class="input file_box">
                                            <ul>
                                                <li class="logoFile_li" style="align-items: center;">
                                                    <c:set var="logoFileSrc" value="${fn:replace(logoFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                    <c:if test="${not fn:contains(logoFileSrc, '.ai') and not fn:contains(logoFileSrc, '.pdf')}">
                                                        <img src="${logoFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                    </c:if>
                                                        <%--<a href="/file/download.do?path=exhibitor/company/${logoFile.folderPath}&fileName=${logoFile.fullFileName}">${logoFile.fileName}</a>--%>
                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${logoFile.folderPath}', '${logoFile.fullFileName}')">${logoFile.fileName}</a>
                                                    <input type="hidden" name="logoUploadFile" id="${logoFile.id}" value="${logoFile.fullFilePath}">
                                                    <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${logoFile.id}')">X</button>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </c:if>
                                <li>
                                    <div class="item req">
                                        <p>기참가연도</p>
                                    </div>
                                    <div class="input check">
                                        <label><input type="checkbox" name="prePartYear" value="first" <c:if test="${fn:contains(info.prePartYear, 'first')}">checked</c:if>/>첫 참가</label>
                                        <label><input type="checkbox" name="prePartYear" value="2008~2014" <c:if test="${fn:contains(info.prePartYear, '2008~2014')}">checked</c:if>/>2008~2014</label>
                                        <label><input type="checkbox" name="prePartYear" value="2015" <c:if test="${fn:contains(info.prePartYear, '2015')}">checked</c:if>/>2015</label>
                                        <label><input type="checkbox" name="prePartYear" value="2016" <c:if test="${fn:contains(info.prePartYear, '2016')}">checked</c:if>/>2016</label>
                                        <label><input type="checkbox" name="prePartYear" value="2017" <c:if test="${fn:contains(info.prePartYear, '2017')}">checked</c:if>/>2017</label>
                                        <label><input type="checkbox" name="prePartYear" value="2018" <c:if test="${fn:contains(info.prePartYear, '2018')}">checked</c:if>/>2018</label>
                                        <label><input type="checkbox" name="prePartYear" value="2019" <c:if test="${fn:contains(info.prePartYear, '2019')}">checked</c:if>/>2019</label>
                                        <label><input type="checkbox" name="prePartYear" value="2020" <c:if test="${fn:contains(info.prePartYear, '2020')}">checked</c:if>/>2020</label>
                                        <label><input type="checkbox" name="prePartYear" value="2021" <c:if test="${fn:contains(info.prePartYear, '2021')}">checked</c:if>/>2021</label>
                                        <label><input type="checkbox" name="prePartYear" value="2022" <c:if test="${fn:contains(info.prePartYear, '2022')}">checked</c:if>/>2022</label>
                                        <label><input type="checkbox" name="prePartYear" value="2023" <c:if test="${fn:contains(info.prePartYear, '2023')}">checked</c:if>/>2023</label>
                                        <label><input type="checkbox" name="prePartYear" value="2024" <c:if test="${fn:contains(info.prePartYear, '2024')}">checked</c:if>/>2024</label>
                                        <label><input type="checkbox" name="prePartYear" value="2025" <c:if test="${fn:contains(info.prePartYear, '2025')}">checked</c:if>/>2025</label>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>한국해양레저산업협회 회원사 여부</p>
                                    </div>
                                    <div class="input check">
                                        <label><input type="radio" name="memberCompanyYn" value="Y" <c:if test="${info.memberCompanyYn eq 'Y'}">checked</c:if> />예</label>
                                        <label><input type="radio" name="memberCompanyYn" value="N" <c:if test="${info.memberCompanyYn eq 'N' or empty info}">checked</c:if> />아니요</label>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <!-- 참가업체 정보 -->

                        <!-- 담당자 정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">담당자 정보</div>
                                <div class="small">부담당자는 최대 3명까지 등록 가능합니다</div>
                            </div>
                            <ul class="form_box">
                                <li class="form_in_tit">대표담당자</li>
                                <li>
                                    <div class="item req">
                                        <p>성명</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="name" name="name" value="${info.name}" placeholder="성명" class="w50">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item req">
                                        <p>직위</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="position" name="position" value="${info.position}" placeholder="직위" class="w50">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item">
                                        <p>부서</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="depart" name="depart" value="${info.depart}" placeholder="부서" class="w50">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item req">
                                        <p>전화번호</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" id="tel" name="tel" value="${info.tel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item req">
                                        <p>휴대전화</p>
                                    </div>
                                    <div class="input">
                                        <input type="tel" id="phone" name="phone" value="${info.phone}" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="숫자만 입력해 주세요.">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>이메일</p>
                                    </div>
                                    <div class="input email">
                                        <input type="email" id="email1" name="email1" value="${fn:split(info.email,'@')[0]}" placeholder="이메일" class="email_input1">
                                        <span>@</span>
                                        <input type="email" id="email2" name="email2" value="${fn:split(info.email,'@')[1]}" class="email_input2" placeholder="직접입력">
                                        <select id="email_select">
                                            <c:set var="domain" value="${fn:split(info.email,'@')[1]}"/>
                                            <option value="직접입력" selected>직접입력</option>
                                            <option value="naver.com" <c:if test="${domain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                            <option value="daum.net" <c:if test="${domain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                            <option value="nate.com" <c:if test="${domain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                            <option value="hanmail.net" <c:if test="${domain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                            <option value="gmail.com" <c:if test="${domain eq 'gmail.com'}">selected</c:if> >gmail.com</option>
                                        </select>
                                    </div>
                                </li>
                            </ul>
                            <c:if test="${empty chargeList}">
                                <ul class="form_box managerInfoBox">
                                    <li class="form_in_tit">
                                        <input type="hidden" name="chargeSeq" value="">
                                        부담당자 #<span class="managerInfoNum">1</span>
                                        <span class="del_btn managerInfoDel">삭제</span>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>성명</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="chargePersonName" class="w50" placeholder="부담당자명">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>직위</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="chargePersonPosition" class="w50" placeholder="직위">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>부서</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="chargePersonDepart" class="w50" placeholder="부서">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>전화번호</p>
                                        </div>
                                        <div class="input">
                                            <input type="tel" name="chargePersonTel" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>휴대전화</p>
                                        </div>
                                        <div class="input">
                                            <input type="tel" name="chargePersonPhone" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>이메일</p>
                                        </div>
                                        <div class="input email">
                                            <input type="email" name="chargePersonEmail" placeholder="이메일" class="email_input1">
                                            <span>@</span>
                                            <input type="email" name="chargePersonDomain" placeholder="직접입력" class="email_input2">
                                            <select name="chargePersonEmail_sel">
                                                <option value="직접입력" selected>직접입력</option>
                                                <option value="naver.com">naver.com</option>
                                                <option value="daum.net">daum.net</option>
                                                <option value="nate.com">nate.com</option>
                                                <option value="hanmail.net">hanmail.net</option>
                                                <option value="gmail.com">gmail.com</option>
                                            </select>
                                        </div>
                                    </li>
                                </ul>
                            </c:if>

                            <c:if test="${not empty chargeList}">
                                <c:forEach var="charge" items="${chargeList}" begin="0" end="${chargeList.size()}" step="1" varStatus="status">
                                    <ul class="form_box managerInfoBox">
                                        <li class="form_in_tit">
                                            <input type="hidden" name="chargeSeq" value="${charge.seq}">
                                            부담당자 #<span class="managerInfoNum">${status.index + 1}</span>
                                            <span class="del_btn managerInfoDel">삭제</span>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>성명</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="chargePersonName" value="${charge.chargePersonName}" maxlength="50" class="w50" placeholder="부담당자명">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>직위</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="chargePersonPosition" value="${charge.chargePersonPosition}" class="w50" placeholder="직위">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>부서</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="chargePersonDepart" value="${charge.chargePersonDepart}" class="w50" placeholder="부서">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>전화번호</p>
                                            </div>
                                            <div class="input">
                                                <input type="tel" name="chargePersonTel" value="${charge.chargePersonTel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>휴대전화</p>
                                            </div>
                                            <div class="input">
                                                <input type="tel" name="chargePersonPhone" value="${charge.chargePersonPhone}" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>이메일</p>
                                            </div>
                                            <div class="input email">
                                                <input type="email" name="chargePersonEmail" value="${fn:split(charge.chargePersonEmail,'@')[0]}" placeholder="이메일" class="email_input1">
                                                <span>@</span>
                                                <input type="email" name="chargePersonDomain" value="${fn:split(charge.chargePersonEmail,'@')[1]}" class="email_input2" placeholder="직접입력">
                                                <select name="chargePersonEmail_sel">
                                                    <c:set var="chargePersonDomain" value="${fn:split(charge.chargePersonEmail,'@')[1]}"/>
                                                    <option selected>직접입력</option>
                                                    <option value="naver.com" <c:if test="${chargePersonDomain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                    <option value="daum.net" <c:if test="${chargePersonDomain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                    <option value="nate.com" <c:if test="${chargePersonDomain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                    <option value="hanmail.net" <c:if test="${chargePersonDomain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                    <option value="gmail.com" <c:if test="${chargePersonDomain eq 'gmail.com'}">selected</c:if> >gmail.com</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </c:forEach>
                            </c:if>

                            <div class="formAddBtn"><span class="managerInfoAdd">추가</span></div>
                        </div>
                        <!-- 담당자 정보 -->

                        <!-- 참가분야 -->
                        <div class="form_wrap" id="eventParticipationModule"
                             data-event="${info.fieldParticipatory}"
                             data-field1="${info.fieldParticipatory1}"
                             data-field2="${info.fieldParticipatory2}"
                             data-field3="${info.fieldParticipatory3}">
                            <div class="form_tit">
                                <div class="big">참가행사 및 분야</div>
                                <%--<div class="small">최대 3개 선택 가능합니다.</div>--%>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>참가행사</p>
                                    </div>
                                    <div class="input check">
                                        <label><input type="radio" name="fieldParticipatory" value="boatShow">경기국제보트쇼</label>
                                        <label><input type="radio" name="fieldParticipatory" value="surfShow">코리아서프쇼</label>
                                        <label><input type="radio" name="fieldParticipatory" value="travelShow">코리아마린트레블쇼</label>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>참가분야</p>
                                    </div>
                                    <div class="input" id="participationFields">
                                        <select class="w30" id="field1" name="fieldParticipatory1">
                                            <option selected disabled hidden>1순위</option>
                                        </select>
                                        <select class="w30" id="field2" name="fieldParticipatory2">
                                            <option selected disabled hidden>2순위</option>
                                        </select>
                                        <select class="w30" id="field3" name="fieldParticipatory3">
                                            <option selected disabled hidden>3순위</option>
                                        </select>
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <!-- 상세정보 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">상세정보</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item">
                                        <p>회사소개영상</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="companyIntroVideo" value="${info.companyIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                        <div class="cmnt">온라인 전시관에 노출되는 항목입니다.</div>
                                        <div class="cmnt">유튜브에 업로드된 영상만 등록 가능합니다. (숏츠,릴스 게시 불가)</div>
                                        <div class="cmnt">유튜브 영상 하단의 [공유] 버튼을 클릭하여, 뜨는 링크를 복사/붙여넣기 해 주세요.</div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>회사소개</p>
                                    </div>
                                    <div class="input">
                                        <textarea id="companyIntroKo" name="companyIntroKo" placeholder="국문">${info.companyIntroKo}</textarea>
                                        <textarea id="companyIntroEn" name="companyIntroEn" placeholder="영문" class="onlyNumEng">${info.companyIntroEn}</textarea>
                                        <div class="cmnt">온라인 전시관에 노출되는 항목입니다.</div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>KIBS 참가목적</p>
                                    </div>
                                    <div class="input">
                                        <select name="companyPurposeKo" id="companyPurposeKo">
                                            <option value="제품 홍보 및 브랜드 인지도 제고" <c:if test="${info.companyPurposeKo eq '제품 홍보 및 브랜드 인지도 제고'}">selected</c:if> >제품 홍보 및 브랜드 인지도 제고</option>
                                            <option value="비즈니스 상담 및 바이어 확보" <c:if test="${info.companyPurposeKo eq '비즈니스 상담 및 바이어 확보'}">selected</c:if> >비즈니스 상담 및 바이어 확보</option>
                                            <option value="업계 네트워킹" <c:if test="${info.companyPurposeKo eq '업계 네트워킹'}">selected</c:if> >업계 네트워킹</option>
                                            <option value="업계 트렌드 파악" <c:if test="${info.companyPurposeKo eq '업계 트렌드 파악'}">selected</c:if> >업계 트렌드 파악</option>
                                            <option value="올해의 제품상 신청" <c:if test="${info.companyPurposeKo eq '올해의 제품상 신청'}">selected</c:if> >올해의 제품상 신청</option>
                                            <option value="기타" <c:if test="${info.companyPurposeKo eq '기타'}">selected</c:if> >기타</option>
                                        </select>
                                        <select name="companyPurposeEn" id="companyPurposeEn">
                                            <option value="Product Promotion & Branding" <c:if test="${info.companyPurposeEn eq 'Product Promotion & Branding'}">selected</c:if> >Product Promotion & Branding</option>
                                            <option value="Business Meeting" <c:if test="${info.companyPurposeEn eq 'Business Meeting'}">selected</c:if> >Business Meeting</option>
                                            <option value="Networking" <c:if test="${info.companyPurposeEn eq 'Networking'}">selected</c:if> >Networking</option>
                                            <option value="Trend Research" <c:if test="${info.companyPurposeEn eq 'Trend Research'}">selected</c:if> >Trend Research</option>
                                            <option value="Awards" <c:if test="${info.companyPurposeEn eq 'Awards'}">selected</c:if> >Awards</option>
                                            <option value="Others" <c:if test="${info.companyPurposeEn eq 'Others'}">selected</c:if> >Others</option>
                                        </select>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>신제품출품 사항 소개</p>
                                    </div>
                                    <div class="input">
                                        <textarea id="newItemIntroKo" name="newItemIntroKo" placeholder="국문">${info.newItemIntroKo}</textarea>
                                        <textarea id="newItemIntroEn" name="newItemIntroEn" placeholder="영문" class="onlyNumEng">${info.newItemIntroEn}</textarea>
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>프로모션 정보</p>
                                    </div>
                                    <div class="input">
                                        <textarea id="promotionPlan" name="promotionPlan" placeholder="-전시 기간 중 제품 할인이나 기타 이벤트 계획이 있다면 적어주세요.&#10;-사무국 검토 후 뉴스레터, SNS 콘텐츠 등 사전 홍보에 활용될 수 있습니다.">${info.promotionPlan}</textarea>
                                        <div class="cmnt">부스에서 진행 예정인 이벤트가 있다면 행사 전 반드시 사무국에 알려야 합니다.</div>
                                        <div class="cmnt">26년 2월 1일 이후 입력한 사항은 프로모션 홍보가 어려울 수 있습니다.</div>
                                    </div>
                                </li>
                            </ul>

                        </div>

                        <!-- 온라인 전시관 정보 -->
                        <script src="/js/online.js"></script>
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">온라인 전시관 정보</div>
                                <div class="small">온라인 전시관에 추가 전시를 원하는 제품 정보를 입력해 주세요.</div>
                            </div>

                            <c:if test="${empty onlineList}">
                                <ul class="form_box onlineInfoBox">
                                    <li class="form_in_tit">
                                        <input type="hidden" name="onlineSeq" value="">
                                        제품 #<span class="onlineInfoNum">1</span>
                                        <span class="del_btn onlineInfoDel">삭제</span>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품분류</p>
                                        </div>
                                        <div class="input">
                                            <select id="onlineOptionBig_1" name="onlineOptionBig" class="w50"></select>
                                            <select id="onlineOptionSmall_1" name="onlineOptionSmall" class="w50"></select>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품명</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineNameKo" placeholder="국문" class="w50">
                                            <input type="text" name="onlineNameEn" placeholder="영문" class="w50 onlyNumEng">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품사진</p>
                                        </div>
                                        <div class="input file_box">
                                            <div class="cmnt2">1개 제품 당 제품사진 최대 5개 등록 가능</div>
                                            <div class="onlinePrdBox">
                                                <input type="text" id="onlineImage1_1" name="onlineImage" class="upload_name" value="" placeholder="File" disabled="disabled">
                                                <input type="file" id="onlineImageFile1_1" name="onlineImageFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                <label for="onlineImageFile1_1">파일선택</label>
                                                <span class="onlinePrdAdd" style="cursor: pointer">추가</span>
                                                <span class="onlinePrdDel" style="cursor: pointer">삭제</span>
                                                <div class="cmnt">JPG, PNG형식, 10MB 이하만 가능합니다. (권장 사이즈 : 1000x750px)</div>
                                                <div class="cmnt">첫 번째로 등록한 이미지가 메인 이미지로 적용됩니다.</div>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품 설명</p>
                                        </div>
                                        <div class="input">
                                            <textarea name="onlineIntroKo" placeholder="국문"></textarea>
                                            <textarea name="onlineIntroEn" placeholder="영문" class="onlyNumEng"></textarea>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>제품 영상</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineLink" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                            <div class="cmnt">유튜브에 업로드된 영상만 등록 가능합니다. (숏츠,릴스 게시 불가)</div>
                                            <div class="cmnt">유튜브 영상 하단의 [공유] 버튼을 클릭하여, 뜨는 링크를 복사/붙여넣기 해 주세요.</div>
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>길이(cm)</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineLength" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>너비(cm)</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineWidth" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>높이(cm)</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineHeight" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>중량(kg)</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineWeight" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>소재</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineMaterial" placeholder="플라스틱, 알루미늄, FRP 등">
                                        </div>
                                    </li>
                                    <li class="w50">
                                        <div class="item">
                                            <p>연식</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" name="onlineYear" class="onlyNum" maxlength="4" placeholder="숫자만 입력해 주세요.">
                                        </div>
                                    </li>
                                </ul>
                            </c:if>

                            <c:if test="${not empty onlineList}">
                                <c:forEach var="online" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                    <ul class="form_box onlineInfoBox">
                                        <li class="form_in_tit">
                                            <input type="hidden" name="onlineSeq" value="${online.seq}">
                                            제품 #<span class="onlineInfoNum">${status.index + 1}</span>
                                            <span class="del_btn onlineInfoDel">삭제</span>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>제품명</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineNameKo" value="${online.onlineNameKo}" placeholder="국문" class="w50">
                                                <input type="text" name="onlineNameEn" value="${online.onlineNameEn}" placeholder="영문" class="w50 onlyNumEng">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>제품분류</p>
                                            </div>
                                            <div class="input">
                                                <select id="onlineOptionBig_${status.index + 1}" name="onlineOptionBig" class="w50"></select>
                                                <select id="onlineOptionSmall_${status.index + 1}" name="onlineOptionSmall" class="w50"></select>
                                                <script>
                                                    $(document).ready(function () {
                                                        $('#onlineOptionBig_${status.index + 1}').val('${online.onlineOptionBig}').prop('selected', true).trigger('change');
                                                        $('#onlineOptionSmall_${status.index + 1}').val('${online.onlineOptionSmall}').prop('selected', true);
                                                    })
                                                </script>
                                                    <%--<c:if test="${online.onlineOptionBig ne null and online.onlineOptionBig ne '' and online.onlineOptionSmall ne null and online.onlineOptionSmall ne ''}">
                                                        <span class="preOptionList" style="margin-left: 10px; display: block;">
                                                            저장된 제품 분류 값 : &nbsp;
                                                                <input type="text" name="preBigValue" value="${online.onlineOptionBig}" style="width: unset;" readonly="readonly"/>&nbsp;
                                                                <input type="text" name="preSmallValue" value="${online.onlineOptionSmall}" style="width: unset;" readonly="readonly"/>
                                                        </span>
                                                    </c:if>--%>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>제품사진</p>
                                            </div>
                                            <div class="input file_box">
                                                <div class="cmnt2">1개 제품 당 제품사진 최대 5개 등록 가능</div>
                                                <div class="onlinePrdBox">
                                                    <c:set var="onlineImageNumIdx" value="0" />
                                                    <c:forEach var="onlineImageFile" items="${onlineImageFileList}" begin="0" end="${onlineImageFileList.size()}" step="1">
                                                        <c:set var="onlineInfoNumIdx" value="${status.index + 1}"/>
                                                        <c:if test="${fn:contains(onlineImageFile.note , 'onlineImage'.concat(onlineInfoNumIdx.toString()).concat('_'))}">
                                                            <c:set var="onlineImageNumIdx" value="${onlineImageNumIdx + 1}" />
                                                        </c:if>
                                                    </c:forEach>
                                                    <input type="text" id="onlineImage${onlineInfoNumIdx}_${onlineImageNumIdx + 1}" name="onlineImage" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" id="onlineImageFile${onlineInfoNumIdx}_${onlineImageNumIdx + 1}" name="onlineImageFile" accept=".png, .jpg, .jpeg" class="upload_hidden">
                                                    <label for="onlineImageFile${onlineInfoNumIdx}_${onlineImageNumIdx + 1}">파일선택</label>
                                                    <span class="onlinePrdAdd" style="cursor: pointer">추가</span>
                                                    <span class="onlinePrdDel" style="cursor: pointer">삭제</span>
                                                    <div class="cmnt">JPG, PNG형식, 10MB 이하만 가능합니다. (권장 사이즈 : 1000x750px)</div>
                                                    <div class="cmnt">첫 번째로 등록한 이미지가 메인 이미지로 적용됩니다.</div>
                                                </div>
                                            </div>
                                        </li>

                                        <c:if test="${onlineImageFileList ne null and not empty onlineImageFileList}">
                                            <li class="preValueList">
                                                <div class="item">
                                                    <p>제품사진 파일</p>
                                                </div>
                                                <div class="input file_box">
                                                    <ul>
                                                        <c:forEach var="onlineImageFile" items="${onlineImageFileList}" begin="0" end="${onlineImageFileList.size()}" step="1">
                                                            <c:set var="idx" value="${status.index + 1}"/>
                                                            <c:if test="${fn:contains(onlineImageFile.note, 'onlineImage'.concat(idx.toString()).concat('_'))}">
                                                                <li class="onlineImageFile_li" style="align-items: center; margin-bottom: 5px;">
                                                                    <c:if test="${fn:substring(onlineImageFile.note, fn:length(onlineImageFile.note)-2, fn:length(onlineImageFile.note)) eq '_1'}">
                                                                        <span style="color: #FF0083">메인 이미지 : </span>
                                                                    </c:if>
                                                                    <c:if test="${fn:substring(onlineImageFile.note, fn:length(onlineImageFile.note)-2, fn:length(onlineImageFile.note)) ne '_1'}">
                                                                                <span style="color: #FF0083">
                                                                                    <c:out value="${fn:substring(onlineImageFile.note, fn:indexOf(onlineImageFile.note, '_')+1, fn:length(onlineImageFile.note))}"/> : </span>
                                                                    </c:if>
                                                                    <c:set var="onlineImageFileSrc" value="${fn:replace(onlineImageFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                    <img src="${onlineImageFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin: 0 10px;"/>
                                                                        <%--<a href="/file/download.do?path=exhibitor/company/${onlineImageFile.folderPath}&fileName=${onlineImageFile.fullFileName}">${onlineImageFile.fileName}</a>--%>
                                                                    <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${onlineImageFile.folderPath}', '${onlineImageFile.fullFileName}')">${onlineImageFile.fileName}</a>
                                                                    <input type="hidden" name="onlineImageUploadFile" id="${onlineImageFile.id}" value="${onlineImageFile.fullFilePath}">
                                                                    <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${onlineImageFile.id}')">X</button>
                                                                </li>
                                                            </c:if>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </li>
                                        </c:if>

                                        <li>
                                            <div class="item">
                                                <p>제품 설명</p>
                                            </div>
                                            <div class="input">
                                                <textarea name="onlineIntroKo" placeholder="국문">${online.onlineIntroKo}</textarea>
                                                <textarea name="onlineIntroEn" placeholder="영문" class="onlyNumEng">${online.onlineIntroEn}</textarea>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>제품 영상</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineLink" value="${online.onlineLink}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                <div class="cmnt">유튜브에 업로드된 영상만 등록 가능합니다. (숏츠,릴스 게시 불가)</div>
                                                <div class="cmnt">유튜브 영상 하단의 [공유] 버튼을 클릭하여, 뜨는 링크를 복사/붙여넣기 해 주세요.</div>
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>길이(cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineLength" value="${online.onlineLength}" class="onlyNum" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>너비(cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineWidth" value="${online.onlineWidth}" class="onlyNum" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>높이(cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineHeight" value="${online.onlineHeight}" class="onlyNum" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>중량(kg)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineWeight" value="${online.onlineWeight}" class="onlyNum" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>소재</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineMaterial" value="${online.onlineMaterial}" placeholder="플라스틱, 알루미늄, FRP 등">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>연식</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="onlineYear" value="${online.onlineYear}" class="onlyNum" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                    </ul>
                                </c:forEach>
                            </c:if>
                            <div class="formAddBtn">
                                <!-- 이미지교체방법추가 231107 -->
                                <div class="img_replace_cmnt">
                                    <div class="btn">이미지 교체 방법</div>
                                    <div class="text">
                                        이미지를 수정(교체) 업로드 하신 후 페이지 가장 하단에 있는 [다음] 버튼을 눌러 저장하세요.<br>
                                        반드시 [다음] 버튼을 눌러 저장하셔야, 미리보기 이미지가 수정된 이미지로 보입니다.
                                    </div>
                                </div>
                                <!-- //이미지교체방법추가 231107 -->
                                <span class="onlineInfoAdd">추가</span>
                            </div>
                        </div>

                        <!-- 수출상담회 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">수출상담회</div>
                                <div class="small">2026 경기국제보트쇼에서는 국내외바이어 초청 및 1:1 수출상담회를 진행합니다.</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>수출상담회 참가 희망 여부</p>
                                    </div>
                                    <div class="input check">
                                        <label>
                                            <input type="radio" id="exportMeetingY" name="exportMeetingYn" value="Y" <c:if test="${info.exportMeetingYn eq 'Y'}">checked</c:if> />
                                            참가
                                        </label>
                                        <label>
                                            <input type="radio" id="exportMeetingN" name="exportMeetingYn" value="N" <c:if test="${info.exportMeetingYn eq 'N' or info.exportMeetingYn eq null or info.exportMeetingYn eq ''}">checked</c:if>/>
                                            참가 안 함
                                        </label>
                                    </div>
                                </li>
                            </ul>

                            <div class="form_chuga form_add_buyer">
                                <ul class="form_box" style="border-top:0;">
                                    <li class="form_in_tit">초청 희망 바이어</li>
                                </ul>

                                <c:if test="${buyerList ne null and not empty buyerList}">
                                    <c:forEach var="buyer" items="${buyerList}" begin="0" end="${buyerList.size()}" step="1">
                                        <input type="hidden" name="buyerItemSeq" value="${buyer.seq}">
                                        <div class="form_chuga_list">
                                            <div class="cont">
                                                <div class="name">${buyer.buyerCompanyName}</div>
                                                <div class="gubun">${buyer.buyerCompanyCountry} / ${buyer.buyerCompanyLocation} / ${buyer.buyerCompanyDepart} ${buyer.buyerCompanyPosition}</div>
                                            </div>
                                            <div class="modifyFormBuyer modifyFormList" onclick="f_buyer_modify_modal('select','${buyer.seq}')">수정</div>
                                            <div class="delFormBuyer delFormList" onclick="f_buyer_remove('select', this, '${buyer.seq}')">삭제</div>
                                            <input type="hidden" name="buyerCompanyName" value="${buyer.buyerCompanyName}">
                                            <input type="hidden" name="buyerCompanyCountry" value="${buyer.buyerCompanyCountry}">
                                            <input type="hidden" name="buyerCompanyLocation" value="${buyer.buyerCompanyLocation}">
                                            <input type="hidden" name="buyerCompanyHomepage" value="${buyer.buyerCompanyHomepage}">
                                            <input type="hidden" name="buyerCompanyDepart" value="${buyer.buyerCompanyDepart}">
                                            <input type="hidden" name="buyerCompanyPosition" value="${buyer.buyerCompanyPosition}">
                                            <input type="hidden" name="buyerCompanyEmail" value="${buyer.buyerCompanyEmail}">
                                            <input type="hidden" name="buyerCompanyTel" value="${buyer.buyerCompanyTel}">
                                            <input type="hidden" name="buyerCompanyPhone" value="${buyer.buyerCompanyPhone}">
                                            <input type="hidden" name="buyerCompanyFax" value="${buyer.buyerCompanyFax}">
                                            <input type="hidden" name="buyerCompanyItem" value="${buyer.buyerCompanyItem}">
                                            <input type="hidden" name="buyerCompanyInviteReason" value="${buyer.buyerCompanyInviteReason}">
                                            <input type="hidden" name="buyerCompanyProgressYn" value="${buyer.buyerCompanyProgressYn}">
                                            <input type="hidden" name="buyerCompanyHope" value="${buyer.buyerCompanyHope}">
                                        </div>
                                    </c:forEach>
                                </c:if>

                                <a href="#" id="buyer_add_btn" class="addFormBuyer addFormList btnSt01">여기를 클릭하여 바이어 등록</a>
                            </div>
                        </div>
                        <!-- 비즈니스 상담 -->

                        <div class="form_btn" style="justify-content:flex-end;">
                            <a href="javascript:void(0);" onclick="f_mypage_comp('01','${info.seq}')" class="form_btn_next">
                                <div class="big">저장</div>
                            </a>
                        </div>
                    </div>
                </form>
            </div>
            <!-- //section -->
        </div>
    </div>

    <!-- 초청 희망 바이어 팝업 -->
    <div id="buyer_popup" class="popFormBuyer popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">새 초청 희망 바이어 등록/수정</div>
                    <a class="close" href="javascript:f_buyer_init();"><img src="/img/close_w.png"></a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <!-- 폼박스 -->
                        <div class="form_s">
                            <!-- 회원계정정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">바이어 정보</div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item">
                                            <p>회사명</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_company_name" name="buyerCompanyName" placeholder="회사명">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>국가</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_country" name="buyerCompanyCountry" placeholder="국가">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>소재지</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_location" name="buyerCompanyLocation" placeholder="소재지">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>홈페이지</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_homepage" name="buyerCompanyHomepage" placeholder="https://">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>부서</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_depart" name="buyerCompanyDepart" placeholder="부서">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>직책</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_position" name="buyerCompanyPosition" placeholder="직책">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>이메일</p>
                                        </div>
                                        <div class="input email">
                                            <input type="text" id="buyer_email_input1" name="buyerCompanyEmail" class="email_input1" placeholder="이메일"/>
                                            <span>@</span>
                                            <input type="text" id="buyer_email_input2" name="buyerCompanyDomain" class="email_input2" placeholder="직접입력"/>
                                            <select id="buyer_email_select">
                                                <option selected>직접입력</option>
                                                <option value="naver.com">naver.com</option>
                                                <option value="daum.net">daum.net</option>
                                                <option value="nate.com">nate.com</option>
                                                <option value="hanmail.net">hanmail.net</option>
                                                <option value="gmail.com">gmail.com</option>
                                            </select>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>전화번호</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_tel" name="buyerCompanyTel" class="onlyTel" maxlength="13" placeholder="하이픈(-)을 포함하여 입력해 주세요."/>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>휴대전화</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_phone" name="buyerCompanyPhone" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="하이픈(-)을 포함하여 입력해 주세요."/>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>팩스</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_fax" name="buyerCompanyFax" class="onlyNumh" placeholder="하이픈(-)을 포함하여 입력해 주세요."/>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>취급품목</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_item" name="buyerCompanyItem" placeholder="취급품목"/>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>초청사유</p>
                                        </div>
                                        <div class="input">
                                            <textarea id="buyer_invite_reason" name="buyerCompanyInviteReason" rows="3" placeholder="초청사유"></textarea>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>계약진행 여부</p>
                                        </div>
                                        <div class="input">
                                            <select id="buyer_progress_yn" name="buyerCompanyProgressYn">
                                                <option value="Y">YES</option>
                                                <option value="N">NO</option>
                                            </select>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item">
                                            <p>희망사항</p>
                                        </div>
                                        <div class="input">
                                            <input type="text" id="buyer_hope" name="buyerCompanyHope" placeholder="희망사항"/>
                                        </div>
                                    </li>
                                </ul>
                                <a class="btnSave" href="javascript:f_buyer_add('${info.seq}')">등록 및 수정</a>
                            </div>
                        </div>
                        <!-- //폼박스 -->
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${info ne null}">
        <c:if test="${info.exportMeetingYn eq 'Y'}">
            <script> $(function(){ document.querySelector('.form_add_buyer').style.display = 'block'; }); </script>
        </c:if>
        <c:if test="${info.exportMeetingYn eq 'N'}">
            <script> $(function(){ document.querySelector('.form_add_buyer').style.display = 'none'; }); </script>
        </c:if>
    </c:if>

    <script>
        (function($) {
            $(document).ready(function () {
                const $module = $('#eventParticipationModule');
                const $field1 = $module.find('#field1');
                const $field2 = $module.find('#field2');
                const $field3 = $module.find('#field3');

                const optionsData = {
                    boatShow: ['보트&요트', '무동력보트', '워크보트', '해양부품&장비', '안전&마리나', '해양관광', '해양레저', '서핑', '수중레저'],
                    surfShow: ['서핑장비', '의류&패션', '체험&교육프로그램', '라이프스타일'],
                    travelShow: ['해양레저 관광상품', '지역관광콘텐츠', '치유&생태관광', '체험콘텐츠']
                };

                /**
                 * 사용자가 직접 form을 변경했을 때 호출되는 함수
                 */
                function updateFormState() {
                    const selectedEvent = $module.find('input[name="fieldParticipatory"]:checked').val();
                    const fullOptions = optionsData[selectedEvent] || [];

                    const val1 = $field1.val();
                    const val2 = $field2.val();
                    const val3 = $field3.val();

                    $field1.empty().append('<option selected disabled hidden>1순위</option>');
                    fullOptions.forEach(option => {
                        $field1.append($('<option></option>').val(option).text(option));
                    });
                    if (val1 && fullOptions.includes(val1)) {
                        $field1.val(val1);
                    }

                    const currentVal1 = $field1.val();
                    $field2.empty().append('<option selected disabled hidden>2순위</option>');
                    if (currentVal1) {
                        const optionsForField2 = fullOptions.filter(opt => opt !== currentVal1);
                        optionsForField2.forEach(option => {
                            $field2.append($('<option></option>').val(option).text(option));
                        });
                        if (val2 && optionsForField2.includes(val2)) {
                            $field2.val(val2);
                        }
                    }

                    const currentVal2 = $field2.val();
                    $field3.empty().append('<option selected disabled hidden>3순위</option>');
                    if (currentVal1 && currentVal2) {
                        const optionsForField3 = fullOptions.filter(opt => opt !== currentVal1 && opt !== currentVal2);
                        optionsForField3.forEach(option => {
                            $field3.append($('<option></option>').val(option).text(option));
                        });
                        if (val3 && optionsForField3.includes(val3)) {
                            $field3.val(val3);
                        }
                    }
                }

                /**
                 * 페이지 로딩 시 DB 값을 form에 적용하는 독립적인 함수
                 */
                function applyDbValues() {
                    const savedEvent = ($module.data('event') || '').toString().trim();
                    const savedField1 = ($module.data('field1') || '').toString().trim();
                    const savedField2 = ($module.data('field2') || '').toString().trim();
                    const savedField3 = ($module.data('field3') || '').toString().trim();

                    if (savedEvent) {
                        // 1. 라디오 버튼 설정
                        $module.find(`input[name="fieldParticipatory"][value="` + savedEvent + `"]`).prop('checked', true);
                        const fullOptions = optionsData[savedEvent] || [];

                        // 2. 1순위 옵션 채우고 값 설정
                        $field1.empty().append('<option selected disabled hidden>1순위</option>');
                        fullOptions.forEach(option => $field1.append($('<option>').val(option).text(option)));
                        $field1.val(savedField1);

                        // 3. 2순위 옵션 채우고 값 설정
                        $field2.empty().append('<option selected disabled hidden>2순위</option>');
                        const currentVal1 = $field1.val();
                        if (currentVal1) {
                            const optionsForField2 = fullOptions.filter(opt => opt !== currentVal1);
                            optionsForField2.forEach(option => $field2.append($('<option>').val(option).text(option)));
                            $field2.val(savedField2);
                        }

                        // 4. 3순위 옵션 채우고 값 설정
                        $field3.empty().append('<option selected disabled hidden>3순위</option>');
                        const currentVal2 = $field2.val();
                        if (currentVal1 && currentVal2) {
                            const optionsForField3 = fullOptions.filter(opt => opt !== currentVal1 && opt !== currentVal2);
                            optionsForField3.forEach(option => $field3.append($('<option>').val(option).text(option)));
                            $field3.val(savedField3);
                        }

                    } else {
                        // DB 값이 없을 경우 (신규 등록): 기본값 설정 후 화면 업데이트
                        $module.find('input[name="fieldParticipatory"][value="boatShow"]').prop('checked', true);
                        updateFormState(); // 이 경우에만 updateFormState를 호출하여 초기화
                    }
                }

                // --- 이벤트 핸들러 연결 ---
                $module.find('input[name="fieldParticipatory"], #participationFields select').on('change', updateFormState);

                // --- 페이지 로드 시 실행 ---
                applyDbValues();
            });
        })(jQuery);
    </script>
</body>
<!--end::Body-->
</html>