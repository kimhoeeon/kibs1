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

    <style>
        .discount-item.disabled label {
            color: #999;
            cursor: not-allowed;
        }
        .discount-item.disabled input[type="checkbox"] {
            cursor: not-allowed;
        }
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
                    <a href="javascript:void(0);" onclick="home('ko');" class="iconHome"><img src="/img/icon_home.png"></a>
                    <div class="optionBox">
                        <div class="tabOpt1 tabOption">
                            <div class="tabOptAct">참가기업</div>
                            <div class="tabOptSel">
                                <a href="/guide/summary.do">KIBS 안내</a>
                                <a class="active" href="/exhibitor/guide.do">참가기업</a>
                                <a href="/visitor/guide_off.do">참관객</a>
                                <a href="/guide/notice.do">미디어센터</a>
                                <a href="/online/kibstv.do">온라인 전시관</a>
                                <a href="/guide/maritime_expo.do">해상전시회(시흥 거북섬마리나)</a>
                                <a href="/guide/summary_kiss.do">KISS(코리아 서프쇼)</a>
                                <a href="/guide/summary_kmts.do">KMTS(해양관광전)</a>
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
                    <div class="apply_nav_list">
                        <ul class="list1">
                            <li><a>기본정보</a></li>
                            <li class="active">
                                <a href="javascript:void(0);" onclick="f_page_move('/apply/step2_1.do','${info.seq}')">전시 신청 정보</a>
                                <ul class="list2">
                                    <li><a>전시부스 신청</a></li>
                                    <li><a>해상전시회 신청</a></li>
                                    <li class="active"><a>요트/보트 출품 정보</a></li>
                                    <li><a>상호간판 신청</a></li>
                                    <li><a>유틸리티 신청</a></li>
                                    <li><a>출입증 신청</a></li>
                                    <li><a>경품제공 신청</a></li>
                                    <li><a>디렉토리 정보</a></li>
                                </ul>
                            </li>
                            <li><a>전시참가 신청 완료</a></li>
                        </ul>
                    </div>
                </div>

                <div class="apply_step">
                    <div class="apply_tit">요트/보트 출품 정보</div>
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
                                <progress class="gauge_bar" value="20" max="80"></progress>
                            </div>
                            <div class="txt">진행중...<p>(총 <span>8</span>개 중 <span>2</span> 완료)</p></div>
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
                <div class="apply_form form_s padding_t">
                    <div class="inner">

                        <form id="exhibitor_apply_form" method="post" onsubmit="return false;" enctype="multipart/form-data">

                            <!-- 요트/보트 출품 정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">요트/보트 출품 정보</div>
                                    <div class="small">
                                        신청하실 경우 요트/보트 출품 정보는 1개 이상 필수 등록입니다.<br><br>
                                        2026 경기국제보트쇼 장치 및 철거일정을 고려하여 전시품 선정 부탁드립니다.<br><br>
                                        ○ 장치 및 전시품 반입 : 2026.3.4(수) ~ 3.5(목) 08:00~20:00, 2일간<br>
                                        ○ 행사기간 : 2026.3.6(금) ~ 3.8(일), 3일간<br>
                                        ○ 철거 및 전시품 반출 : 2026.3.8(일) 08:00~20:00, 당일철거
                                    </div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item req">
                                            <p>요트/보트 출품 여부</p>
                                        </div>
                                        <div class="input check">
                                            <label>
                                                <input type="radio" id="boatEntryY" name="boatEntryYn" value="Y" <c:if test="${info.boatEntryYn eq 'Y'}">checked</c:if> />
                                                출품
                                            </label>
                                            <label>
                                                <input type="radio" id="boatEntryN" name="boatEntryYn" value="N" <c:if test="${info.boatEntryYn eq 'N' or info.boatEntryYn eq null or info.boatEntryYn eq ''}">checked</c:if>/>
                                                미출품
                                            </label>
                                        </div>
                                    </li>
                                </ul>
                            </div>

                            <!-- 전시정보 -->
                            <script src="/js/product.js"></script>
                            <div class="form_wrap productInfoWrap" style="display: none;">

                                <div class="form_tit">
                                    <div class="big">요트/보트 출품 정보 입력</div>
                                </div>

                                <c:if test="${empty productList}">
                                    <ul class="form_box exhiInfoBox">
                                        <li class="form_in_tit">
                                            <input type="hidden" name="productSeq" value="">
                                            요트/보트 출품 정보 #<span class="exhiInfoNum">1</span>
                                            <span class="del_btn exhiInfoDel">삭제</span>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>제품 분류(품목)</p>
                                            </div>
                                            <div class="input">
                                                <select id="productOptionBig_1" name="productOptionBig" class="w50"></select>
                                                <select id="productOptionSmall_1" name="productOptionSmall" class="w50"></select>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>제품명</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productNameKo" placeholder="제품명을 입력하세요.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>수량</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productQty" class="onlyNum" maxlength="4" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>제조사(브랜드)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productBrand" placeholder="제품의 제조사(브랜드)를 입력하세요.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>특징</p>
                                            </div>
                                            <div class="input">
                                                <textarea name="productFeature" placeholder="전시품에 대한 설명과 특징을 작성해주세요."></textarea>
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>길이(cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productLength" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>너비(cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productWidth" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>높이(cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productHeight" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>중량(kg)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productWeight" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>소재</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productMaterial" placeholder="플라스틱, 알루미늄, FRP 등">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>연식</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productYear" class="onlyNum" maxlength="4" placeholder="숫자만 입력해 주세요.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>신제품 여부</p>
                                            </div>
                                            <div class="input check">
                                                <label><input type="radio" name="productIsNew_1" value="Y">해당</label>
                                                <label><input type="radio" name="productIsNew_1" value="N" checked>미해당</label>
                                            </div>
                                        </li>
                                    </ul>
                                </c:if>

                                <c:if test="${not empty productList}">
                                    <c:forEach var="product" items="${productList}" begin="0" end="${productList.size()}" step="1" varStatus="status">
                                        <ul class="form_box exhiInfoBox">
                                            <li class="form_in_tit">
                                                <input type="hidden" name="productSeq" value="${product.seq}">
                                                전시정보 #<span class="exhiInfoNum">${status.index + 1}</span>
                                                <span class="del_btn exhiInfoDel">삭제</span>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>제품 분류(품목)</p>
                                                </div>
                                                <div class="input">
                                                    <select id="productOptionBig_${status.index + 1}" name="productOptionBig" class="w50"></select>
                                                    <select id="productOptionSmall_${status.index + 1}" name="productOptionSmall" class="w50"></select>
                                                    <script>
                                                        $(document).ready(function () {
                                                            $('#productOptionBig_${status.index + 1}').val('${product.productOptionBig}').prop('selected', true).trigger('change');
                                                            $('#productOptionSmall_${status.index + 1}').val('${product.productOptionSmall}').prop('selected', true);
                                                        })
                                                    </script>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>제품명</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productNameKo" value="${product.productNameKo}" placeholder="제품명을 입력하세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>수량</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productQty" value="${product.productQty}" class="onlyNum" maxlength="4" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>제조사(브랜드)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productBrand" value="${product.productBrand}" placeholder="제품의 제조사(브랜드)를 입력하세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>특징</p>
                                                </div>
                                                <div class="input">
                                                    <textarea name="productFeature" placeholder="전시품에 대한 설명과 특징을 작성해주세요.">${product.productFeature}</textarea>
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>길이(cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productLength" value="${product.productLength}" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>너비(cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productWidth" value="${product.productWidth}" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>높이(cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productHeight" value="${product.productHeight}" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>중량(kg)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productWeight" value="${product.productWeight}" class="onlyNum" maxlength="10" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>소재</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productMaterial" value="${product.productMaterial}" placeholder="플라스틱, 알루미늄, FRP 등">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>연식</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productYear" value="${product.productYear}" class="onlyNum" maxlength="4" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>신제품 여부</p>
                                                </div>
                                                <div class="input check">
                                                    <label>
                                                        <input type="radio" name="productIsNew_${status.index + 1}" value="Y" <c:if test="${product.productIsNew eq 'Y'}">checked</c:if>>해당
                                                    </label>
                                                    <label>
                                                        <input type="radio" name="productIsNew_${status.index + 1}" value="N" <c:if test="${product.productIsNew ne 'Y'}">checked</c:if>>미해당
                                                    </label>
                                                </div>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </c:if>
                                <div class="formAddBtn">
                                    <span class="exhiInfoAdd">추가</span>
                                </div>
                            </div>

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/apply/step2_10.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">이전</div>
                                    <div class="small">해상전시회 신청</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_apply_comp('2_9','${info.seq}')" class="form_btn_next">
                                    <div class="big">다음 / 건너뛰기</div>
                                    <div class="small">상호간판 신청</div>
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- //section -->

            </div>
        </div>
    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    <script type="text/javascript">
        $(document).ready(function() {
            // 이전 값을 저장할 변수를 선언하고, 페이지 로드 시 선택된 값으로 초기화합니다.
            let previousBoatEntryYn = $('input[type=radio][name=boatEntryYn]:checked').val();

            // --- 페이지 로드 시 초기 상태 설정 ---
            // 기존의 trigger('change')를 대체하여, 확인창 없이 UI만 설정합니다.
            if (previousBoatEntryYn === 'Y') {
                $('.productInfoWrap').show();
            } else {
                $('.productInfoWrap').hide();
            }

            // --- 라디오 버튼 변경 이벤트 핸들러 ---
            $('input[type=radio][name=boatEntryYn]').on('change', function() {
                const currentBoatEntryYn = $(this).val();

                // 💡 이전 값과 현재 값이 동일하면 아무것도 하지 않고 함수를 종료합니다.
                if (currentBoatEntryYn === previousBoatEntryYn) {
                    return;
                }

                if (currentBoatEntryYn === 'N') {
                    let flag = false;
                    const productName_el = $('input[type=text][name=productNameKo]');

                    // 입력된  요트/보트 출품 정보가 있는지 확인
                    productName_el.each(function() {
                        if ($(this).val() !== '') {
                            flag = true;
                            return false; // .each() 루프 중단
                        }
                    });

                    if (flag) {
                        Swal.fire({
                            icon: 'warning',
                            title: '[ 요트/보트 출품 정보 ]',
                            html: '<span style="font-size: 1.2em;">요트/보트 출품 여부 \'미신청\' 으로 변경 시<br>등록한  요트/보트 출품 정보가 모두 삭제됩니다.<br>변경하시겠습니까?</span>',
                            allowOutsideClick: false,
                            showCancelButton: true,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '변경',
                            cancelButtonColor: '#A1A5B7',
                            cancelButtonText: '취소'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                // '변경'을 누르면 필드를 초기화하고, 이전 값을 현재 값으로 업데이트
                                $('.productInfoWrap').hide();
                                $('.exhiInfoBox:not(:first)').remove();
                                $('select').prop('selectedIndex', 0).trigger('change');
                                $('input[type=text]').val('');
                                previousBoatEntryYn = currentBoatEntryYn;
                            } else {
                                // 💡 '취소'를 누르면 라디오 버튼 선택을 이전 상태로 되돌립니다.
                                $('input[type=radio][name=boatEntryYn][value="' + previousBoatEntryYn + '"]').prop('checked', true);
                            }
                        });
                    } else {
                        // 입력된 정보가 없으면 확인 없이 바로 숨김 처리 및 값 업데이트
                        $('.productInfoWrap').hide();
                        $('.exhiInfoBox:not(:first)').remove();
                        $('select').prop('selectedIndex', 0).trigger('change');
                        $('input[type=text]').val('');
                        previousBoatEntryYn = currentBoatEntryYn;
                    }
                } else { // 'Y'를 선택한 경우
                    $('.productInfoWrap').show();
                    previousBoatEntryYn = currentBoatEntryYn;
                }
            });
        });
    </script>

</body>
</html>