<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>2025 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <style>
        #spinner { width: 100%; height: 100%; top: 0; left: 0; opacity: .6; background: silver; position: absolute; z-index: 9999; display: none;}
        #spinner div { width: 100%; height: 100%; display: table; }
        #spinner span { display: table-cell; text-align: center; vertical-align: middle; }
        #spinner img { background: white; padding: 1em; border-radius: .7em; }
    </style>
</head>

<body>
<c:import url="../header.jsp" charEncoding="UTF-8"/>

<div id="container">

    <!-- section -->
    <div class="sub_top">
        <div class="inner">
            <div class="sub_top_box">
                <div class="sub_top_nav">
                    <span>Home</span><span>Buyer</span><span>Confirm Application</span>
                </div>
                <div class="sub_top_tit">Confirm Application</div>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="form_s padding_tb">
        <div class="inner">

            <!--begin::Form-->
            <form id="joinForm" name="joinForm" method="post" onsubmit="return false;">

                <input type="hidden" name="seq" value="${info.seq}">
                <input type="hidden" name="visitorSeq" value="${info.seq}">

                <!-- 참관객 정보 -->
                <div class="form_wrap">
                    <div class="form_tit">
                        <div class="big">Visitor Information</div>
                    </div>
                    <ul class="form_box">
                        <li>
                            <div class="item req">
                                <p>Name</p>
                            </div>
                            <div class="input">
                                <input type="text" id="name" name="name" value="${info.name}" placeholder="Name" readonly>
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item">
                                <p>Tel</p>
                            </div>
                            <div class="input">
                                <input type="tel" id="tel" name="tel" value="${info.tel}" class="onlyTel" maxlength="14" placeholder="Please enter only numbers.">
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item req">
                                <p>Mobile</p>
                            </div>
                            <div class="input">
                                <input type="tel" id="phone" name="phone" value="${info.phone}" class="onlyTel" maxlength="13" placeholder="Please enter only numbers." readonly>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>E-mail</p>
                            </div>
                            <div class="input email">
                                <input type="text" id="email" name="email" value="${info.email}" placeholder="email" class="email_input1">
                                <span>@</span>
                                <input type="text" id="domain" name="domain" value="${info.domain}" placeholder="domain" class="email_input2">
                                <select id="email_select">
                                    <option selected disabled>Direct Input</option>
                                    <option value="daum.net" <c:if test="${info.domain eq 'daum.net'}">selected</c:if> disabled>daum.net</option>
                                    <option value="nate.com" <c:if test="${info.domain eq 'nate.com'}">selected</c:if> disabled>nate.com</option>
                                    <option value="hanmail.net" <c:if test="${info.domain eq 'hanmail.net'}">selected</c:if> disabled>hanmail.net</option>
                                    <option value="naver.com" <c:if test="${info.domain eq 'naver.com'}">selected</c:if> disabled>naver.com</option>
                                    <option value="gmail.com" <c:if test="${info.domain eq 'gmail.com'}">selected</c:if> disabled>gmail.com</option>
                                    <option value="hotmail.com" <c:if test="${info.domain eq 'hotmail.com'}">selected</c:if> disabled>hotmail.com</option>
                                    <option value="yahoo.co.kr" <c:if test="${info.domain eq 'yahoo.co.kr'}">selected</c:if> disabled>yahoo.co.kr</option>
                                    <option value="empal.com" <c:if test="${info.domain eq 'empal.com'}">selected</c:if> disabled>empal.com</option>
                                    <option value="korea.com" <c:if test="${info.domain eq 'korea.com'}">selected</c:if> disabled>korea.com</option>
                                    <option value="hanmir.com" <c:if test="${info.domain eq 'hanmir.com'}">selected</c:if> disabled>hanmir.com</option>
                                    <option value="dreamwiz.com" <c:if test="${info.domain eq 'dreamwiz.com'}">selected</c:if> disabled>dreamwiz.com</option>
                                    <option value="orgio.net" <c:if test="${info.domain eq 'orgio.net'}">selected</c:if> disabled>orgio.net</option>
                                    <option value="korea.com" <c:if test="${info.domain eq 'korea.com'}">selected</c:if> disabled>korea.com</option>
                                    <option value="hitel.net" <c:if test="${info.domain eq 'hitel.net'}">selected</c:if> disabled>hitel.net</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <div class="item">
                                <p>Company Name</p>
                            </div>
                            <div class="input companyNameBox" style="flex-wrap: wrap">
                                <input class="companyNameInput" type="text" id="companyName" name="companyName" value="${info.companyName}" placeholder="Company Name">
                                <%--<input class="companyNameSh" type="button" value="Search">--%>
                                <input type="text" id="companyAddress" name="companyAddress" value="${info.companyAddress}" placeholder="Company Address">
                            </div>
                        </li>
                        <li>
                            <div class="item">
                                <p>Companion attendance</p>
                            </div>
                            <div class="input check">
                                <label><input type="radio" name="partnerYn" id="Y" value="Y" <c:if test="${info.partnerYn eq 'Y'}">checked</c:if> >Yes</label>
                                <label><input type="radio" name="partnerYn" id="N" value="N" <c:if test="${info.partnerYn eq 'N' or info.partnerYn eq null}">checked</c:if> >None</label>
                                <div class="cmnt">Please select only in cases such as children(under the age of ten) and elderly who are difficult to apply individual observation.</div>
                            </div>
                        </li>
                    </ul>

                    <c:if test="${empty partnerList}">
                        <ul class="form_box visitPartnerBox" style="border-top:0;">
                            <li class="form_in_tit">
                                Companion #<span class="visitPartnerNum">1</span>
                                <span class="del_btn visitPartnerDel">Delete</span>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Name</p>
                                </div>
                                <div class="input">
                                    <input type="text" name="partnerName">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Age</p>
                                </div>
                                <div class="input">
                                    <input type="text" name="partnerAge" class="onlyNum" maxlength="2">
                                </div>
                            </li>
                        </ul>
                    </c:if>

                    <c:if test="${not empty partnerList}">
                    <c:forEach var="partner" items="${partnerList}" begin="0" end="${partnerList.size()}" step="1" varStatus="status">
                        <ul class="form_box visitPartnerBox" style="border-top:0;">
                            <li class="form_in_tit">
                                Companion #<span class="visitPartnerNum">${status.index + 1}</span>
                                <span class="del_btn visitPartnerDel">Delete</span>
                                <input type="hidden" name="partnerSeq" value="${partner.seq}">
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Name</p>
                                </div>
                                <div class="input">
                                    <input type="text" name="partnerName" value="${partner.partnerName}">
                                </div>
                            </li>
                            <li class="w50">
                                <div class="item req">
                                    <p>Age</p>
                                </div>
                                <div class="input">
                                    <input type="text" name="partnerAge" value="${partner.partnerAge}" class="onlyNum" maxlength="2">
                                </div>
                            </li>
                        </ul>
                    </c:forEach>
                    </c:if>

                    <div class="formAddBtn"><span class="visitPartnerAdd">Add</span></div>
                </div>
                <!-- //참관객 정보 -->

                <!-- 설문항목 -->
                <div class="form_wrap">
                    <div class="form_tit">
                        <div class="big">Visitor Survey</div>
                    </div>
                    <ul class="form_box">
                        <li class="w50">
                            <div class="item req">
                                <p>Gender</p>
                            </div>
                            <div class="input check">
                                <label><input type="radio" name="sex" value="남자" <c:if test="${info.sex eq '남자'}">checked</c:if> >Male</label>
                                <label><input type="radio" name="sex" value="여자" <c:if test="${info.sex eq '여자'}">checked</c:if> >Female</label>
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item req">
                                <p>Country</p>
                            </div>
                            <div class="input">
                                <script src="/js/country.js"></script>
                                <select id="country" name="country">
                                </select>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Age</p>
                            </div>
                            <div class="input check">
                                <label><input type="radio" name="ageGroup" value="10" <c:if test="${info.ageGroup eq '10' or info.ageGroup eq null}">checked</c:if> >10s</label>
                                <label><input type="radio" name="ageGroup" value="20" <c:if test="${info.ageGroup eq '20'}">checked</c:if> >20s</label>
                                <label><input type="radio" name="ageGroup" value="30" <c:if test="${info.ageGroup eq '30'}">checked</c:if> >30s</label>
                                <label><input type="radio" name="ageGroup" value="40" <c:if test="${info.ageGroup eq '40'}">checked</c:if> >40s</label>
                                <label><input type="radio" name="ageGroup" value="50" <c:if test="${info.ageGroup eq '50'}">checked</c:if> >50s</label>
                                <label><input type="radio" name="ageGroup" value="60" <c:if test="${info.ageGroup eq '60'}">checked</c:if> >Over 60s</label>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Classification</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="observationGbn" value="조종면허 보유자" <c:if test="${fn:contains(info.observationGbn, '조종면허 보유자')}">checked</c:if> >Boat driver’s license holder</label>
                                <label><input type="checkbox" name="observationGbn" value="보트 소유자" <c:if test="${fn:contains(info.observationGbn, '보트 소유자')}">checked</c:if> >Boat owner</label>
                                <label><input type="checkbox" name="observationGbn" value="보트 구매 예정자" <c:if test="${fn:contains(info.observationGbn, '보트 구매 예정자')}">checked</c:if> >Potential boat buyer</label>
                                <label><input type="checkbox" name="observationGbn" value="관련 업종 종사자" <c:if test="${fn:contains(info.observationGbn, '관련 업종 종사자')}">checked</c:if> >Marine industry employee</label>
                                <label><input type="checkbox" name="observationGbn" value="낚시 매니아" <c:if test="${fn:contains(info.observationGbn, '낚시 매니아')}">checked</c:if> >Fishing maniac</label>
                                <label><input type="checkbox" name="observationGbn" value="다이빙 매니아" <c:if test="${fn:contains(info.observationGbn, '다이빙 매니아')}">checked</c:if> >Diving maniac</label>
                                <label><input type="checkbox" name="observationGbn" value="캠핑카 매니아" <c:if test="${fn:contains(info.observationGbn, '캠핑카 매니아')}">checked</c:if> >Outdoor/Caravan maniac</label>
                                <label><input type="checkbox" name="observationGbn" value="학생" <c:if test="${fn:contains(info.observationGbn, '학생')}">checked</c:if> >Students</label>
                                <label><input type="checkbox" name="observationGbn" value="일반관람" <c:if test="${fn:contains(info.observationGbn, '일반관람')}">checked</c:if> >Leisure visitor</label>
                                <label><input type="checkbox" name="observationGbn" value="기타" <c:if test="${fn:contains(info.observationGbn, '기타')}">checked</c:if> >Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Purpose of visit</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="visitPurpose" value="업계동향 파악 및 정보수집" <c:if test="${fn:contains(info.visitPurpose, '업계동향 파악 및 정보수집')}">checked</c:if> >Market research</label>
                                <label><input type="checkbox" name="visitPurpose" value="제품구매 및 기술도입 상담" <c:if test="${fn:contains(info.visitPurpose, '제품구매 및 기술도입 상담')}">checked</c:if> >Product / Technology purchasing consultation</label>
                                <label><input type="checkbox" name="visitPurpose" value="기존 거래업체 방문" <c:if test="${fn:contains(info.visitPurpose, '기존 거래업체 방문')}">checked</c:if> >Visit supplier company / clients</label>
                                <label><input type="checkbox" name="visitPurpose" value="차기 전시회 참가여부 파악" <c:if test="${fn:contains(info.visitPurpose, '차기 전시회 참가여부 파악')}">checked</c:if> >Survey on intention of participation to other exhibitions</label>
                                <label><input type="checkbox" name="visitPurpose" value="일반관람" <c:if test="${fn:contains(info.visitPurpose, '일반관람')}">checked</c:if> >General visit/sightseeing</label>
                                <label><input type="checkbox" name="visitPurpose" value="기타" <c:if test="${fn:contains(info.visitPurpose, '기타')}">checked</c:if> >Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Area of interest</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="interestItem" value="요트/보트" <c:if test="${fn:contains(info.interestItem, '요트/보트')}">checked</c:if> >Yacht&Boat</label>
                                <label><input type="checkbox" name="interestItem" value="워크보트" <c:if test="${fn:contains(info.interestItem, '워크보트')}">checked</c:if> >Work boats</label>
                                <label><input type="checkbox" name="interestItem" value="낚시" <c:if test="${fn:contains(info.interestItem, '낚시')}">checked</c:if> >Fishing</label>
                                <label><input type="checkbox" name="interestItem" value="다이빙" <c:if test="${fn:contains(info.interestItem, '다이빙')}">checked</c:if> >Diving</label>
                                <label><input type="checkbox" name="interestItem" value="카누/카약/무동력" <c:if test="${fn:contains(info.interestItem, '카누/카약/무동력')}">checked</c:if> >Canoe / Kayak / Row boats</label>
                                <label><input type="checkbox" name="interestItem" value="워터스포츠" <c:if test="${fn:contains(info.interestItem, '워터스포츠')}">checked</c:if> >Watersports</label>
                                <label><input type="checkbox" name="interestItem" value="해양부품" <c:if test="${fn:contains(info.interestItem, '해양부품')}">checked</c:if> >Marine Equipment</label>
                                <label><input type="checkbox" name="interestItem" value="캠핑" <c:if test="${fn:contains(info.interestItem, '캠핑')}">checked</c:if> >Camping</label>
                                <label><input type="checkbox" name="interestItem" value="친환경" <c:if test="${fn:contains(info.interestItem, '친환경')}">checked</c:if> >Eco Friendly Marine</label>
                                <label><input type="checkbox" name="interestItem" value="해양관광" <c:if test="${fn:contains(info.interestItem, '해양관광')}">checked</c:if> >Marine Tourism</label>
                                <label><input type="checkbox" name="interestItem" value="기타" <c:if test="${fn:contains(info.interestItem, '기타')}">checked</c:if> >Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Where did you find us?</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="recognizePath" value="뉴스레터" <c:if test="${fn:contains(info.recognizePath, '뉴스레터')}">checked</c:if> >E-mail newsletter</label>
                                <label><input type="checkbox" name="recognizePath" value="옥외광고물" <c:if test="${fn:contains(info.recognizePath, '옥외광고물')}">checked</c:if> >Outdoor advertisement</label>
                                <label><input type="checkbox" name="recognizePath" value="홈페이지" <c:if test="${fn:contains(info.recognizePath, '홈페이지')}">checked</c:if> >Website</label>
                                <label><input type="checkbox" name="recognizePath" value="전문지" <c:if test="${fn:contains(info.recognizePath, '전문지')}">checked</c:if> >Specialized Magazine</label>
                                <label><input type="checkbox" name="recognizePath" value="온라인 커뮤니티" <c:if test="${fn:contains(info.recognizePath, '온라인 커뮤니티')}">checked</c:if> >Blog, etc.</label>
                                <label><input type="checkbox" name="recognizePath" value="오프라인매장" <c:if test="${fn:contains(info.recognizePath, '오프라인매장')}">checked</c:if> >Offline partner shop</label>
                                <label><input type="checkbox" name="recognizePath" value="소셜 네트워크" <c:if test="${fn:contains(info.recognizePath, '소셜 네트워크')}">checked</c:if> >SNS</label>
                                <label><input type="checkbox" name="recognizePath" value="초청장" <c:if test="${fn:contains(info.recognizePath, '초청장')}">checked</c:if> >Invitation letter</label>
                                <label><input type="checkbox" name="recognizePath" value="방송광고" <c:if test="${fn:contains(info.recognizePath, '방송광고')}">checked</c:if> >TV advertisement</label>
                                <label><input type="checkbox" name="recognizePath" value="KIBS 2024 참석" <c:if test="${fn:contains(info.recognizePath, 'KIBS 2024 참석')}">checked</c:if> >Participants to KIBS 2024</label>
                                <label><input type="checkbox" name="recognizePath" value="낚시박람회" <c:if test="${fn:contains(info.recognizePath, '낚시박람회')}">checked</c:if> >Fishing Fair</label>
                                <label><input type="checkbox" name="recognizePath" value="기타" <c:if test="${fn:contains(info.recognizePath, '기타')}">checked</c:if> >Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item">
                                <p>Whether you attended the last exhibition</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="preObservationGbn" value="first" <c:if test="${fn:contains(info.preObservationGbn, 'first')}">checked</c:if> >One's first visit </label>
                                <label><input type="checkbox" name="preObservationGbn" value="2008" <c:if test="${fn:contains(info.preObservationGbn, '2008')}">checked</c:if> >2008</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2009" <c:if test="${fn:contains(info.preObservationGbn, '2009')}">checked</c:if> >2009</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2010" <c:if test="${fn:contains(info.preObservationGbn, '2010')}">checked</c:if> >2010</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2011" <c:if test="${fn:contains(info.preObservationGbn, '2011')}">checked</c:if> >2011</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2012" <c:if test="${fn:contains(info.preObservationGbn, '2012')}">checked</c:if> >2012</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2013" <c:if test="${fn:contains(info.preObservationGbn, '2013')}">checked</c:if> >2013</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2014" <c:if test="${fn:contains(info.preObservationGbn, '2014')}">checked</c:if> >2014</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2015" <c:if test="${fn:contains(info.preObservationGbn, '2015')}">checked</c:if> >2015</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2016" <c:if test="${fn:contains(info.preObservationGbn, '2016')}">checked</c:if> >2016</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2017" <c:if test="${fn:contains(info.preObservationGbn, '2017')}">checked</c:if> >2017</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2018" <c:if test="${fn:contains(info.preObservationGbn, '2018')}">checked</c:if> >2018</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2019" <c:if test="${fn:contains(info.preObservationGbn, '2019')}">checked</c:if> >2019</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2020" <c:if test="${fn:contains(info.preObservationGbn, '2020')}">checked</c:if> >2020</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2021" <c:if test="${fn:contains(info.preObservationGbn, '2021')}">checked</c:if> >2021</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2022" <c:if test="${fn:contains(info.preObservationGbn, '2022')}">checked</c:if> >2022</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2023" <c:if test="${fn:contains(info.preObservationGbn, '2023')}">checked</c:if> >2023</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2024" <c:if test="${fn:contains(info.preObservationGbn, '2024')}">checked</c:if> >2024</label>
                            </div>
                        </li>
                    </ul>
                </div>
                <!-- //설문항목 -->

            </form>

            <div class="form_btn_visitor">
                <a href="javascript:void(0);" onclick="f_en_visitor_apply('U')" class="btnSt01">
                    <div class="big">Modify</div>
                </a>
            </div>

        </div>
    </div>
    <!-- //section -->

    <!-- 직장명 검색 팝업 -->
    <div class="popCompanyName popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">Company name search</div>
                    <a class="close" href="#close">
                        <img src="/img/close_w.png">
                    </a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <div class="searchBox">
                                <span class="search">
                                    <input type="text" id="search_companyName" placeholder="Company name">
                                    <button type="button" onclick="f_company_search()">
                                        <img src="/img/icon_search.png">
                                    </button>
                                </span>
                        </div>
                        <div class="directBox">
                            <div class="txt">If the registered company is not a business, enter it directly.</div>
                            <div class="btn btnSt01 directCompanyName">Enter</div>
                        </div>
                        <ul class="companyList">
                            <%--<li>
                                <a href="">
                                    <div class="name">미팅팬</div>
                                    <div class="address">서울특별시 금천구</div>
                                </a>
                            </li>--%>
                        </ul>
                        <!-- <div class="companyNot">
                            No results were found for your search.
                        </div> -->
                    </div>
                </div>
            </div>
        </div>

        <div id="spinner">
            <div>
                <span>
                    <img src="https://cdnjs.cloudflare.com/ajax/libs/galleriffic/2.0.1/css/loader.gif">
                </span>
            </div>
        </div>

    </div>
    <!-- //직장명 검색 팝업 -->

    <!-- 직장명 직접 입력 팝업 -->
    <div class="popDirectName popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">Enter the company directly</div>
                    <a class="close" href="#close">
                        <img src="/img/close_w.png">
                    </a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <div class="form_s">
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>Company Name</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="pop_companyName" placeholder="Company Name">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Address</p>
                                    </div>
                                    <div class="input address">
                                        <div class="address_box">
                                            <input type="text" id="pop_address" placeholder="Address">
                                            <input type="button" onclick="execDaumPostcode('pop_address','pop_addressDetail')" value="Search"><br>
                                            <div id="map" style="width:300px;height:300px;margin-top:10px;display:none">
                                            </div>
                                        </div>
                                        <div class="address_box" style="margin-top: 10px;">
                                            <input type="text" id="pop_addressDetail" placeholder="Detailed Address">
                                        </div>
                                    </div>
                                </li>
                            </ul>
                            <a class="btnSave" onclick="f_company_info_direct_add()">Registration</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- //직장명 직접 입력 팝업 -->
</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

<c:if test="${info ne null}">
    <c:if test="${info.partnerYn eq 'Y'}">
        <script> $(function(){ $('.visitPartnerBox, .visitPartnerAdd').show(); }); </script>
    </c:if>
    <c:if test="${info.partnerYn eq 'N'}">
        <script> $(function(){ $('.visitPartnerBox, .visitPartnerAdd').hide(); }); </script>
    </c:if>

    <c:if test="${info.country ne null and info.country ne ''}">
        <script> $(function(){ $('#country').val('${info.country}'); $('#country').change(); });</script>
    </c:if>
</c:if>

</body>
</html>