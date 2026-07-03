<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
                                    <li class="active"><a>Yacht/Boat</a></li>
                                    <li><a>Signboard</a></li>
                                    <li><a>Utilities</a></li>
                                    <li><a>Badges</a></li>
                                    <li><a>Giveaways</a></li>
                                    <li><a>Directory</a></li>
                                </ul>
                            </li>
                            <li><a>Complete</a></li>
                        </ul>
                    </div>
                </div>

                <div class="apply_step">
                    <div class="apply_tit">Yacht/Boat Info</div>
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
                                <progress class="gauge_bar" value="25" max="100"></progress>
                            </div>
                            <div class="txt">In Progress <p>(<span>7</span> of <span>1</span> completed)</p></div>
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
                <div class="apply_form form_s padding_t">
                    <div class="inner">

                        <form id="exhibitor_apply_form" method="post" onsubmit="return false;" enctype="multipart/form-data">

                            <!-- 요트/보트 출품 정보 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">Yacht/Boat Info</div>
                                    <div class="small">
                                        At least one yacht/boat entry is required.<br><br>
                                        Please select exhibits considering the setup and dismantling schedule.<br><br>
                                        ○ Booth Construction & Exhibit Move-in: March 4 (Wed) – March 5 (Thu), 2026, 08:00–20:00 (2 days)<br>
                                        ○ Exhibition Period: March 6 (Fri) – March 8 (Sun), 2026 (3 days)<br>
                                        ○ Booth Dismantling & Exhibit Move-out: March 8 (Sun), 2026, 08:00–20:00 (Same-day dismantling required)
                                    </div>
                                </div>
                                <ul class="form_box">
                                    <li>
                                        <div class="item req">
                                            <p>Yacht/Boat Exhibit</p>
                                        </div>
                                        <div class="input check">
                                            <label>
                                                <input type="radio" id="boatEntryY" name="boatEntryYn" value="Y" <c:if test="${info.boatEntryYn eq 'Y'}">checked</c:if> />
                                                Yes
                                            </label>
                                            <label>
                                                <input type="radio" id="boatEntryN" name="boatEntryYn" value="N" <c:if test="${info.boatEntryYn eq 'N' or info.boatEntryYn eq null or info.boatEntryYn eq ''}">checked</c:if>/>
                                                No
                                            </label>
                                        </div>
                                    </li>
                                </ul>
                            </div>

                            <!-- 전시정보 -->
                            <script src="/js/product_en.js"></script>
                            <div class="form_wrap productInfoWrap" style="display: none;">

                                <div class="form_tit">
                                    <div class="big">Yacht/Boat Entry Info</div>
                                </div>

                                <c:if test="${empty productList}">
                                    <ul class="form_box exhiInfoBox">
                                        <li class="form_in_tit">
                                            <input type="hidden" name="productSeq" value="">
                                            Yacht/Boat Entry #<span class="exhiInfoNum">1</span>
                                            <span class="del_btn exhiInfoDel">Delete</span>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Category</p>
                                            </div>
                                            <div class="input">
                                                <select id="productOptionBig_1" name="productOptionBig" class="w50"></select>
                                                <select id="productOptionSmall_1" name="productOptionSmall" class="w50"></select>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Name</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productNameKo" placeholder="Enter product name.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Quantity</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productQty" class="onlyNum" maxlength="4" placeholder="Numbers only.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Manufacturer</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productBrand" placeholder="Enter manufacturer (brand).">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Features</p>
                                            </div>
                                            <div class="input">
                                                <textarea name="productFeature" placeholder="Describe the product and its features."></textarea>
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Length (cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productLength" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Width (cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productWidth" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Height (cm)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productHeight" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Weight (kg)</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productWeight" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Material</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productMaterial" placeholder="Plastic, Aluminum, FRP, etc">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Year</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" name="productYear" class="onlyNum" maxlength="4" placeholder="Numbers only.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>New Product</p>
                                            </div>
                                            <div class="input check">
                                                <label><input type="radio" name="productIsNew_1" value="Y">Yes</label>
                                                <label><input type="radio" name="productIsNew_1" value="N" checked>No</label>
                                            </div>
                                        </li>
                                    </ul>
                                </c:if>

                                <c:if test="${not empty productList}">
                                    <c:forEach var="product" items="${productList}" begin="0" end="${productList.size()}" step="1" varStatus="status">
                                        <ul class="form_box exhiInfoBox">
                                            <li class="form_in_tit">
                                                <input type="hidden" name="productSeq" value="${product.seq}">
                                                Yacht/Boat Entry #<span class="exhiInfoNum">${status.index + 1}</span>
                                                <span class="del_btn exhiInfoDel">Delete</span>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Category</p>
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
                                                    <p>Name</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productNameKo" value="${product.productNameKo}" placeholder="Enter product name.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Quantity</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productQty" value="${product.productQty}" class="onlyNum" maxlength="4" placeholder="Numbers only.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Manufacturer</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productBrand" value="${product.productBrand}" placeholder="Enter manufacturer (brand).">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>Features</p>
                                                </div>
                                                <div class="input">
                                                    <textarea name="productFeature" placeholder="Describe the product and its features.">${product.productFeature}</textarea>
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>Length (cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productLength" value="${product.productLength}" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>Width (cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productWidth" value="${product.productWidth}" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>Height (cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productHeight" value="${product.productHeight}" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>Weight (kg)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productWeight" value="${product.productWeight}" class="onlyNum" maxlength="10" placeholder="Numbers only.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>Material</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productMaterial" value="${product.productMaterial}" placeholder="Plastic, Aluminum, FRP, etc">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>Year</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="productYear" value="${product.productYear}" class="onlyNum" maxlength="4" placeholder="Numbers only.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>New Product</p>
                                                </div>
                                                <div class="input check">
                                                    <label>
                                                        <input type="radio" name="productIsNew_${status.index + 1}" value="Y" <c:if test="${product.productIsNew eq 'Y'}">checked</c:if>>Yes
                                                    </label>
                                                    <label>
                                                        <input type="radio" name="productIsNew_${status.index + 1}" value="N" <c:if test="${product.productIsNew ne 'Y'}">checked</c:if>>No
                                                    </label>
                                                </div>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </c:if>
                                <div class="formAddBtn">
                                    <span class="exhiInfoAdd">Add</span>
                                </div>
                            </div>

                            <div class="form_btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step2_1.do','${info.seq}')" class="form_btn_prev">
                                    <div class="big">PREV</div>
                                    <div class="small">Booth</div>
                                </a>
                                <a href="javascript:void(0);" onclick="f_apply_comp('2_9','${info.seq}')" class="form_btn_next">
                                    <div class="big">NEXT / SKIP</div>
                                    <div class="small">Signboard</div>
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

                // 이전 값과 현재 값이 동일하면 아무것도 하지 않고 함수를 종료합니다.
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
                            title: '[ Yacht/Boat Entry ]',
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
                                // '취소'를 누르면 라디오 버튼 선택을 이전 상태로 되돌립니다.
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