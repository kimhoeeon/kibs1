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
                <a href="" class="iconHome"><img src="/img/icon_home.png"></a>
                <div class="optionBox">
                    <div class="tabOpt1 tabOption">
                        <div class="tabOptAct">온라인 전시관</div>
                        <div class="tabOptSel">
                            <a href="/guide/notice.do">KIBS 안내</a>
                            <a href="/exhibitor/guide.do">참가기업</a>
                            <a href="/visitor/guide_off.do">참관객</a>
                            <a href="/board/gallery.do">자료실</a>
                            <a class="active" href="/online/kibstv.do">온라인 전시관</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">제품 검색하기</div>
                        <div class="tabOptSel" style="background: #fff;">
                            <a href="/online/kibstv.do">KIBS Tube</a>
                            <a href="/online/company.do">참가기업 미리보기</a>
                            <a href="/online/product.do" class="active">제품 검색하기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <script src="/js/product.js"></script>
    <div class="prd_search">
        <div class="inner">
            <div class="sec_box">
                <div class="prd_sh_select">
                    <select name="productOptionBig" id="productOptionBig"></select>
                    <select name="productOptionSmall" id="productOptionSmall"></select>
                    <!-- 240103 추가 -->
                    <div class="input_box">
                        <span class="select">
                            <select id="searchCondition">
                                <option value="productName">제품명</option>
                                <option value="companyName">참가기업명</option>
                            </select>
                        </span>
                        <span class="input">
                            <input type="text" id="searchText" placeholder="검색어를 입력해주세요.">
                        </span>
                    </div>
                    <!-- //240103 추가 -->
                </div>
                <div class="prd_sh_slider">
                    <div class="slider">
                        <div class="gubun">전장(m)</div>
                        <div id="prdLength" class="slider-range"></div>
                        <input type="text" id="prdLengthAmount" class="amount" disabled>
                    </div>
                    <div class="slider">
                        <div class="gubun">마력</div>
                        <div id="prdHp" class="slider-range"></div>
                        <input type="text" id="prdHpAmount" class="amount" disabled>
                    </div>
                </div>
                <div class="prd_sh_btn">
                    <a href="javascript:onlineProductList(1)">검색하기</a>
                </div>
            </div>
        </div>
    </div>
    <!-- //section -->


    <!-- section -->
    <div class="prd_item padding_tb" id="page-anchor">
        <div class="inner">
            <div class="sub_cont_tit" style="margin: 1% 1% 15px;">
                <div class="small">제품 검색 결과 : <span class="total" style="font-weight: bold; color: #1d5cad; font-size: 2rem;"></span> 개 제품</div>
            </div>
            <div class="prd_item_box">
                <ul class="prdList">
                    <%--<li>
                        <div class="btnLike">
                            <img src="/img/icon_like.png" class="likeImg">
                        </div>
                        <a href="/online/product_view.do">
                            <div class="thumbnail">
                                <div class="thumb75 thumbBox">
                                    <img src="/img/thumb_exh_img_01.jpg" class="thumbImg">
                                </div>
                                <div class="thumbLogo">
                                    <p>
                                        <img src="/img/thumb_exh_logo_01.jpg">
                                    </p>
                                </div>
                            </div>
                            <div class="prdTxt">
                                <div class="name">현대요트주식회사</div>
                                <div class="prdName">바바리아 VIDA33</div>
                                <div class="type">요트·보트 > 파워보트</div>
                            </div>
                        </a>
                    </li>--%>
                </ul>
            </div>
            <div class="paging">
                <span class="first" id="first_page"><a><img src="/img/btn_first.gif" style="cursor: pointer"></a></span>
                <span class="prev" id="prev_page"><a><img src="/img/btn_prev.gif" style="cursor: pointer"></a></span>
                <ol>
                    <%--<li>
                      <a class="this">1</a>
                    </li>
                    <li>
                      <a class="other">2</a>
                    </li>--%>
                </ol>
                <span class="next" id="next_page"><a><img src="/img/btn_next.gif" style="cursor: pointer"></a></span>
                <span class="last" id="last_page"><a><img src="/img/btn_last.gif" style="cursor: pointer"></a></span>
            </div>
        </div>
    </div>
    <!-- //section -->

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

<script>
    $(function(){
        let productOptionInfo = '${productOptionInfo}';

        if(nvl(productOptionInfo,"") !== ""){
            let productNameKo = '${productOptionInfo.productNameKo}';
            $('#searchText').val(productNameKo);

            /*let productOptionBig = '${productOptionInfo.productOptionBig}';
            let productOptionSmall = '${productOptionInfo.productOptionSmall}';
            let productWidthMin = '${productOptionInfo.productWidthMin}';
            let productWidthMax = '${productOptionInfo.productWidthMax}';
            let productHorsePowerMin = '${productOptionInfo.productHorsePowerMin}';
            let productHorsePowerMax = '${productOptionInfo.productHorsePowerMax}';

            $('#productOptionBig').val(productOptionBig).trigger('change');
            $('#productOptionSmall').val(productOptionSmall);
            let prdLengthValues = [productWidthMin, productWidthMax];
            $("#prdLengthAmount").val(prdLengthValues[0] + ' - ' + prdLengthValues[1]);
            $("#prdLength").slider("values", prdLengthValues);
            let prdHpValues = [productHorsePowerMin, productHorsePowerMax];
            $("#prdHpAmount").val(prdHpValues[0] + ' - ' + prdHpValues[1]);
            $("#prdHp").slider("values", prdHpValues);*/
        }

        document.addEventListener("keyup", function(event) {
            if (event.key === 'Enter') {
                onlineProductList(1);
            }
        });
    });
</script>

<script src="/js/front/product.js?ver=<%=System.currentTimeMillis()%>"></script>

</body>
</html>