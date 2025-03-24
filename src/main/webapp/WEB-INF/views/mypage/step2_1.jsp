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
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시부스 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
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
                            <div class="apply_tit">전시부스 신청</div>
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <li>부스 기본단위는 3m x 3m(9㎡, 1부스)이며, 최소 신청 규모는 독립부스 2부스(18㎡), 조립부스 1부스(9㎡) 입니다.</li>
                                    <li>조립부스 제공사항(1부스 기준): 바닥 면적(9㎡), 상호간판(국/영문), 실내 조명, 바닥 파이텍스, 안내 데스크 1개, 접의자 1개 , 상담테이블 1개,
                                        의자 3개, 휴지통 1개, 전기 1KW (220V 콘센트)
                                        <span class="em">(조립부스 제공 사항은 추후 변경될 수 있습니다.)</span>
                                    </li>
                                    <li>인보이스 수령 후 <span class="em">7일 이내 참가비 전액 납부</span> 시 참가신청이 완료됩니다.
                                    </li>
                                    <li>자세한 참가규정 및 계약조건은
                                        <a onclick="window.open('/exhibitor/privacy.do','pop','menubar=no,status=no,scrollbars=no,resizable=no,width=500,height=300,top=50,left=50');"
                                            class="em">여기</a> 참고
                                    </li>
                                    <%--<li>2025 경기국제보트쇼 종료로 인해 <span class="emRed">전시 정보 수정이 불가</span>합니다</li>--%>
                                </ul>
                            </div>

                            <!-- 부스 신청 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">부스 신청</div>
                                </div>
                                <div class="form_booth form_ptag">
                                    <div class="form_ptag_box">
                                        <ul class="form_ptag_list">
                                            <li class="form_ptag_hd">
                                                <div class="cate">구분</div>
                                                <div class="cost">단가</div>
                                                <div class="quantity">신청수량</div>
                                                <div class="amount">금액 (원)</div>
                                                <div class="note">비고</div>
                                            </li>
                                            <li>
                                                <div class="cate">등록비</div>
                                                <div class="cost booth_cost">￦ 100,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="registration_cnt" placeholder="수량 입력" class="onlyNum qty" value="1" disabled>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="registration_fee" class="num_sum" value="￦ 100,000" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">
                                                    2019년부터 참가업체의 홍보 지원을 위하여 업체당 참가신청 등록비를 받습니다.<br>
                                                    등록비는 참가업체의 각종 온라인 홍보 및 대외 홍보에 사용됩니다.
                                                </div>
                                            </li>
                                            <li>
                                                <div class="cate">독립부스</div>
                                                <div class="cost booth_cost">￦ 1,800,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="stand_alone_booth_cnt" placeholder="수량 입력" class="onlyNum qty" value="${info.standAloneBoothCnt eq null ? 0 : info.standAloneBoothCnt}" onblur="minCnt(this,2);" onkeyup="checkBooth();autoSum(1);autoTotalSum();" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="stand_alone_booth_fee" class="num_sum" value="<fmt:formatNumber value="${info.standAloneBoothFee eq null ? 0 : info.standAloneBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">독립부스는 2부스부터 신청 가능합니다.</div>
                                            </li>
                                            <li>
                                                <div class="cate">조립부스</div>
                                                <div class="cost booth_cost">￦ 2,100,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <input type="text" id="assembly_booth_cnt" placeholder="수량 입력" class="onlyNum qty" value="${info.assemblyBoothCnt eq null ? 0 : info.assemblyBoothCnt}" onkeyup="checkBooth();autoSum(2);autoTotalSum();" <%--disabled--%>>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="assembly_booth_fee" class="num_sum" value="<fmt:formatNumber value="${info.assemblyBoothFee eq null ? 0 : info.assemblyBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note"></div>
                                            </li>
                                            <li>
                                                <div class="cate">온라인부스</div>
                                                <div class="cost booth_cost">￦ 1,000,000</div>
                                                <div class="quantity">
                                                    <p class="j_num">
                                                        <select id="online_booth_cnt" onchange="checkBooth();autoSum(3);autoTotalSum();" <%--disabled--%>>
                                                            <option <c:if test="${info.onlineBoothCnt eq 0}">selected</c:if> >0</option>
                                                            <option <c:if test="${info.onlineBoothCnt eq 1}">selected</c:if> >1</option>
                                                        </select>
                                                    </p>
                                                </div>
                                                <div class="amount">
                                                    <p class="price">
                                                        <input type="text" id="online_booth_fee" class="num_sum" value="<fmt:formatNumber value="${info.onlineBoothFee eq null ? 0 : info.onlineBoothFee}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                                    </p>
                                                </div>
                                                <div class="note">영상 제작 별도 : 영상 제작 희망 시 사무국 문의</div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <!-- //부스 신청-->

                            <!-- 할인적용 선택 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">할인적용 선택</div>
                                    <div class="small">* 실제 정보와 다르게 입력시 참가 취소 등 불이익을 받으실 수 있습니다.</div>
                                </div>
                                <div class="form_disc form_ptag">
                                    <div class="form_ptag_box">
                                        <ul class="form_ptag_list">
                                            <li class="form_ptag_hd">
                                                <div class="select">선택</div>
                                                <div class="cate">구분</div>
                                                <div class="discount">할인</div>
                                                <div class="note">비고</div>
                                            </li>
                                            <li style="color: #8c8c8c">
                                                <div class="select"><label><input type="checkbox" id="discount1" name="discount" value="300000" onchange="autoDiscountSum(this, '300000');" <c:if test="${info.discountEarly1}">checked</c:if> disabled></label></div>
                                                <div class="cate">1차 조기신청 (종료)</div>
                                                <div class="discount">1 부스당 300,000 원 할인</div>
                                                <%--<div class="note">1차 조기신청 할인 / ~ 2024.11.15 (금)까지 신청 시</div>--%>
                                                <div class="note">1차 조기신청 할인이 종료되었습니다.</div>
                                            </li>
                                            <li style="color: #8c8c8c">
                                                <div class="select"><label><input type="checkbox" id="discount2" name="discount" value="200000" onchange="autoDiscountSum(this, '200000');" <c:if test="${info.discountEarly2}">checked</c:if> disabled></label></div>
                                                <div class="cate">2차 조기신청 (종료)</div>
                                                <div class="discount">1 부스당 200,000 원 할인</div>
                                                <%--<div class="note">2차 조기신청 할인 / ~ 2024.12.13 (금)까지 신청 시</div>--%>
                                                <div class="note">2차 조기신청 할인이 종료되었습니다.</div>
                                            </li>
                                            <li>
                                                <div class="select"><label><input type="checkbox" id="discount3" name="discount" value="200000" onchange="autoDiscountSum(this, '200000');" <c:if test="${info.discountReAll}">checked</c:if> <%--disabled--%>></label></div>
                                                <div class="cate">재참가할인</div>
                                                <div class="discount">1 부스당 200,000 원 할인</div>
                                                <div class="note">2014년 ~ 2024년 경기국제보트쇼 참가기업</div>
                                            </li>
                                            <li>
                                                <div class="select"><label><input type="checkbox" id="discount5" name="discount" value="300000" onchange="autoDiscountSum(this, '300000');" <c:if test="${info.discountScale2}">checked</c:if> <%--disabled--%>></label></div>
                                                <div class="cate">규모할인 1 (20부스 이상)</div>
                                                <div class="discount">1 부스당 300,000 원 할인</div>
                                                <div class="note">20부스 이상 참가기업</div>
                                            </li>
                                            <li>
                                                <div class="select"><label><input type="checkbox" id="discount6" name="discount" value="450000" onchange="autoDiscountSum(this, '450000');" <c:if test="${info.discountScale3}">checked</c:if> <%--disabled--%>></label></div>
                                                <div class="cate">규모할인 2 (40부스 이상)</div>
                                                <div class="discount">1 부스당 450,000 원 할인</div>
                                                <div class="note">40부스 이상 참가기업</div>
                                            </li>
                                            <li>
                                                <div class="select"><label><input type="checkbox" id="discount7" name="discount" value="500000" onchange="autoDiscountSum(this, '500000');" <c:if test="${info.discountFirst}">checked</c:if> <%--disabled--%>></label></div>
                                                <div class="cate">첫 참가 할인</div>
                                                <div class="discount">1 부스당 500,000 원 할인</div>
                                                <div class="note">경기국제보트쇼 최초 참가기업</div>
                                            </li>
                                            <li>
                                                <div class="select"><label><input type="checkbox" id="discount8" name="discount" value="200000" onchange="autoDiscountSum(this, '200000');" <c:if test="${info.discountLeisure}">checked</c:if> <%--disabled--%>></label></div>
                                                <div class="cate">한국해양레저산업협회 할인</div>
                                                <div class="discount">1 부스당 200,000 원 할인</div>
                                                <div class="note">한국해양레저산업협회 회원사</div>
                                            </li>
                                        </ul>
                                        <div style="margin-top: 10px;">※ 중복할인 가능하며 자세한 사항은 경기국제보트쇼 사무국으로 문의 바랍니다.</div>
                                    </div>
                                    <div class="form_ptag_sum">
                                        <div class="cate2">총액(VAT 미포함)</div>
                                        <div class="amount2">
                                            <p class="price">
                                                <input type="text" class="num_sum" id="form_add_total" value="<fmt:formatNumber value="${info.boothPrcSum - info.discountPrcSum}" type="currency" maxFractionDigits="0" currencySymbol="￦ "/>" disabled>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- //할인적용 선택-->

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/step01.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">이전</div>
                                    <div class="small">기본정보</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_mypage_comp('2_1','${info.seq}')" class="form_btn_next">
                                    <div class="big">다음</div>
                                    <div class="small">상호간판 신청</div>
                                </a>
                            </div>

                        </div>
                    </div>
                    <!-- //section -->
                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

        <script type="text/javascript">
            $(function(){
                let prcSum = '${info.prcSum}';
                if(nvl(prcSum,'') === '' || parseInt(prcSum) === 0){
                    $('#form_add_total').val($('.booth_cost')[0].innerText); // 초기 총액 : 등록비 합계 Set
                }
            });
        </script>
    </c:if>

</body>
</html>