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

    <c:if test="${status ne 'logon'}">
        <script>
            alert("로그인해 주세요.");
            location.href = '/login.do';
        </script>
    </c:if>

    <c:if test="${status eq 'logon'}">

        <c:import url="../header.jsp" charEncoding="UTF-8"/>

        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>홈</span><span>참가기업</span><span>마이페이지</span>
                        </div>
                        <div class="sub_top_tit">마이페이지</div>
                    </div>
                </div>
            </div>
            <!-- //section -->


            <!-- section -->
            <div class="apply_s padding_tb" id="apply_s">
                <div class="inner">

                    <div class="apply_nav">
                        <div class="apply_nav_txt">
                            <div class="txt">${sessionScope.id} 님 환영합니다!</div>
                            <div class="btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/modify.do','${info.seq}')" class="btnSt01">회원정보관리</a>
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do','${info.seq}')" class="btnSt01">마이페이지</a>
                            </div>
                        </div>
                        <div class="apply_nav_list">
                            <ul class="list1">
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step01.do','${info.seq}')">기본정보</a></li>
                                <li class="active">
                                    <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시 신청 정보</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시부스 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')">출입증 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_5.do','${info.seq}')">경품제공 신청</a></li>
                                        <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_6.do','${info.seq}')">웹 배너 신청</a></li>--%>
                                            <%--<li><a href="/mypage/step2_7.do">온라인 전시관 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_8.do','${info.seq}')">디렉토리 정보</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">
                            <div class="apply_tit">유틸리티 신청</div>
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <li>전시면적만(Space Only) 신청하신 참가업체에서는 반드시 조명용 혹은 장비용으로 소요되는 전력을 파악하시어 신청하여 주시기 바랍니다.<br>
                                        (특히, 장비에 필요한 전력은 정확히 파악하시어 작성해 주셔야 하며, 또한, 전력 시공은 주최자가 선정한 업체 외에는 전시장내에서 전기공사 작업이
                                        불가능하므로 반드시 지정된 업체에서 공사를 할 수 있도록 조치를 취해 주시기를 부탁드립니다.)
                                    </li>
                                    <li>신청된 전기는 귀사의 부스 분전반까지만 시공을 하고 부스 분전반에서 장비까지는 참가업체에서 직접 시공을 하셔야 합니다.</li>
                                    <%--<li>2025 경기국제보트쇼 종료로 인해 <span class="emRed">전시 정보 수정이 불가</span>합니다</li>--%>
                                </ul>
                            </div>

                            <!-- 유틸리티 신청정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">유틸리티 신청정보</div>
                                </div>
                                <div class="form_util form_ptag">
                                    <div class="form_ptag_box">
                                        <ul class="form_ptag_list">
                                            <li class="form_ptag_hd">
                                                <div class="cate">품목</div>
                                                <div class="cost">단가</div>
                                                <div class="quantity">신청수량</div>
                                                <div class="amount">금액 (원)</div>
                                                <div class="note">비고</div>
                                            </li>
                                            <li>
                                                <div class="cate">주간 단상 220v</div>
                                                <div class="cost">￦ 70,000/1kw</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_jugan_cnt" class="num_sum onlyNum" value="${info.utilityJuganCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_jugan_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityJuganFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">행사기간(2/28~3/2, 총 3일)동안 사용가능</div>
                                            </li>
                                            <li>
                                                <div class="cate">24시간용 220v</div>
                                                <div class="cost">￦ 80,000/1kw</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_day_cnt" class="num_sum onlyNum" value="${info.utilityDayCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_day_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityDayFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">행사기간(2/28~3/2, 총 3일)동안 사용가능</div>
                                            </li>
                                            <li>
                                                <div class="cate">압축공기 기본형</div>
                                                <div class="cost">￦ 180,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_compressed_air_cnt" class="num_sum onlyNum" value="${info.utilityCompressedAirCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_compressed_air_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityCompressedAirFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">설치 및 사용료</div>
                                            </li>
                                            <li>
                                                <div class="cate">급배수 기본형</div>
                                                <div class="cost">￦ 180,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_water_basic_cnt" class="num_sum onlyNum" value="${info.utilityWaterBasicCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_water_basic_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityWaterBasicFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">설치 및 사용료</div>
                                            </li>
                                            <li>
                                                <div class="cate">인터넷</div>
                                                <div class="cost">￦ 160,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_internet_cnt" class="num_sum onlyNum" value="${info.utilityInternetCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_internet_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityInternetFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">1회선당</div>
                                            </li>
                                            <li>
                                                <div class="cate">파이텍스 (신품)</div>
                                                <div class="cost">￦ 80,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_pytex_new_cnt" class="num_sum onlyNum" value="${info.utilityPytexNewCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_pytex_new_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityPytexNewFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">1부스당 단가</div>
                                            </li>
                                            <li>
                                                <div class="cate">파이텍스 (재사용품)</div>
                                                <div class="cost">￦ 50,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_pytex_re_cnt" class="num_sum onlyNum" value="${info.utilityPytexReCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_pytex_re_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityPytexReFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">1부스당 단가</div>
                                            </li>
                                            <li>
                                                <div class="cate">참관객/바이어 바코드 리더기</div>
                                                <div class="cost">￦ 200,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="utility_barcode_cnt" class="num_sum onlyNum" value="${info.utilityBarcodeCnt}" placeholder="수량 입력" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="utility_barcode_fee" class="num_sum" value="<fmt:formatNumber value="${info.utilityBarcodeFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">행사기간(2/28~3/2, 총 3일)동안 사용가능</div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="form_ptag_sum">
                                        <div class="cate2">총액(VAT 미포함)</div>
                                        <div class="amount2">
                                            <p class="price">
                                                <input type="text" id="form_add_total" class="num_sum" value="<fmt:formatNumber value="${info.utilityPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- //유틸리티 신청정보 -->

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">이전</div>
                                    <div class="small">상호간판 신청</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_mypage_comp('2_3','${info.seq}')" class="form_btn_next">
                                    <div class="big">다음</div>
                                    <div class="small">출입증 신청</div>
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