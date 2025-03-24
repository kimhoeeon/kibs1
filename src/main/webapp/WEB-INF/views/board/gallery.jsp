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
                    <span>홈</span><span>자료실</span><span>갤러리</span>
                </div>
                <div class="sub_top_tit">갤러리</div>
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
                        <div class="tabOptAct">자료실</div>
                        <div class="tabOptSel">
                            <a href="/guide/notice.do">KIBS 안내</a>
                            <a href="/exhibitor/guide.do">참가기업</a>
                            <a href="/visitor/guide_off.do">참관객</a>
                            <a class="active" href="/board/gallery.do">자료실</a>
                            <a href="/online/kibstv.do">온라인 전시관</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">갤러리</div>
                        <div class="tabOptSel">
                            <a class="active" href="/board/gallery.do">갤러리</a>
                            <a href="/board/press.do">기사 및 칼럼</a>
                            <a href="/board/history24.do">지난 전시회</a>
                            <a href="/board/newsletter.do">뉴스레터</a>
                            <a href="/board/bi.do">KIBS BI</a>
                            <a href="/board/cerify.do">인증 및 수상현황</a>
                            <a href="/board/award.do">올해의 제품상</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="year_tab padding_t">
        <div class="inner">
            <div class="year_tab_box">
                <a class="cate active" data-value="전체" onclick="f_gallery_category_search(this, '전체')" style="cursor: pointer">전체</a>
                <a class="cate" data-value="2025" onclick="f_gallery_category_search(this, '2025')" style="cursor: pointer">2025</a>
                <a class="cate" data-value="2024" onclick="f_gallery_category_search(this, '2024')" style="cursor: pointer">2024</a>
                <a class="cate" data-value="2023" onclick="f_gallery_category_search(this, '2023')" style="cursor: pointer">2023</a>
                <a class="cate" data-value="2022" onclick="f_gallery_category_search(this, '2022')" style="cursor: pointer">2022</a>
                <a class="cate" data-value="2021" onclick="f_gallery_category_search(this, '2021')" style="cursor: pointer">2021</a>
                <a class="cate" data-value="2019" onclick="f_gallery_category_search(this, '2019')" style="cursor: pointer">2019</a>
                <a class="cate" data-value="2018" onclick="f_gallery_category_search(this, '2018')" style="cursor: pointer">2018</a>
                <a class="cate" data-value="2017" onclick="f_gallery_category_search(this, '2017')" style="cursor: pointer">2017</a>
                <a class="cate" data-value="2016" onclick="f_gallery_category_search(this, '2016')" style="cursor: pointer">2016</a>
                <a class="cate" data-value="2015" onclick="f_gallery_category_search(this, '2015')" style="cursor: pointer">2015</a>
                <a class="cate" data-value="2014" onclick="f_gallery_category_search(this, '2014')" style="cursor: pointer">2014</a>
                <a class="cate" data-value="2013" onclick="f_gallery_category_search(this, '2013')" style="cursor: pointer">2013</a>
                <a class="cate" data-value="2012" onclick="f_gallery_category_search(this, '2012')" style="cursor: pointer">2012</a>
                <a class="cate" data-value="2011" onclick="f_gallery_category_search(this, '2011')" style="cursor: pointer">2011</a>
                <a class="cate" data-value="2010" onclick="f_gallery_category_search(this, '2010')" style="cursor: pointer">2010</a>
                <a class="cate" data-value="2009" onclick="f_gallery_category_search(this, '2009')" style="cursor: pointer">2009</a>
                <a class="cate" data-value="2008" onclick="f_gallery_category_search(this, '2008')" style="cursor: pointer">2008</a>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="board_search">
        <div class="inner">
            <div class="search">
                <form class="search_box" onsubmit="return false;">
                <span class="select">
                    <select id="search_box">
                        <option value="title" selected>제목</option>
                        <%--<option value="content">내용</option>
                        <option value="all">내용+제목</option>--%>
                    </select>
                </span>
                    <span class="search">
                    <input type="text" id="search_text" placeholder="검색어를 입력해 주세요.">
                    <button type="button" onclick="galleryList(1, '전체')" style="cursor: pointer"></button>
                </span>
                </form>
            </div>
        </div>
    </div>
    <!-- section -->

    <div class="board_gallery padding_b">
        <div class="inner">
            <div class="sub_cont_tit" style="margin: 1% 1% 15px;">
                <div class="small">검색 결과 : <span class="total" style="font-weight: bold; color: #1d5cad; font-size: 2rem;"></span> 개</div>
            </div>
            <div class="board_gallery_box">
                <ul>
                    <%--<li>
                        <a href="#" class="viewGallery">
                            <div class="thumb75 thumbBox">
                                <img class="thumbImg" src="/img/sample_img.jpg">
                            </div>
                            <div class="txtBox">
                                <div class="tit">갤러리 게시물 제목</div>
                                <div class="date">2024.01.01</div>
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


</div>

<div class="popGallery popup">
    <div class="popup_inner">
        <div class="popup_wrap">
            <div class="pop_tit">
                <div class="tit">게시물 제목 노출</div>
                <a class="close" href="#close"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- 슬라이드박스 -->
                    <div class="swiper_box">
                        <div class="swiper swiper_gallery">
                            <ul class="swiper-wrapper">
                                <%--<li class="swiper-slide thumb75 thumbBox">
                                    <img class="thumbImg" src="/img/thumb_exh_img_01.jpg">
                                </li>--%>
                            </ul>
                        </div>
                        <div class="swiper-button-prev swiper_gallery_prev"></div>
                        <div class="swiper-button-next swiper_gallery_next"></div>
                    </div>
                    <!-- //슬라이드박스 -->
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

<script src="/js/front/gallery.js?ver=<%=System.currentTimeMillis()%>"></script>
</body>
</html>