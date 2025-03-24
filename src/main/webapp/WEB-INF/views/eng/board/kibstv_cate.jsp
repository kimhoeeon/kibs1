<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</head>

<body>
<c:import url="../header.jsp" charEncoding="UTF-8"/>

<div id="container">

    <!-- section -->
    <div class="sub_online_top online_tv_top">
        <div class="inner">
            <div class="online_top_tit">
                <div class="big">KIBS Tube</div>
                <div class="small">Watch the various videos prepared by the Korean International Boat Show</div>
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
                        <div class="tabOptAct">News</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2025</a>
                            <a href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a href="/eng/buyer/apply.do">Buyer</a>
                            <a class="active" href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">KIBS Tube</div>
                        <div class="tabOptSel">
                            <a href="/eng/board/notice.do">Notice</a>
                            <a class="active" href="/eng/board/kibstv.do">KIBS Tube</a>
                            <%--<a href="/eng/board/media.do">KIBS in Media</a>--%>
                            <%--<a href="/eng/board/newsletter.do">Newsletter</a>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="board_search padding_t">
        <div class="inner">
            <div class="search">
                <form class="search_box" onsubmit="return false;">
                    <span class="select">
                        <select id="search_box">
                            <option value="title" selected>Subject</option>
                        </select>
                    </span>
                    <span class="search">
                        <input type="text" id="search_text" placeholder="Please enter a search term.">
                        <button type="button" onclick="kibstvList(1, '전체보기')" style="cursor: pointer"></button>
                    </span>
                </form>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="tv_tab">
        <div class="inner">
            <%--<div class="tv_more">더보기</div>--%>
            <div class="tv_cate">
                <a href="/eng/board/kibstv.do">by Year</a>
                <a href="/eng/board/kibstv_cate.do" class="active">by Category</a>
            </div>
            <ul class="field">
                <li class="active">
                    <a class="cate" data-value="전체보기" onclick="f_kibstv_category_search(this, '전체보기')" style="cursor: pointer">ALL</a>
                </li>
                <li>
                    <a class="cate" data-value="2025 경기국제보트쇼" onclick="f_kibstv_category_search(this, '2025 경기국제보트쇼')" style="cursor: pointer">2025 KIBS</a>
                </li>
                <li>
                    <a class="cate" data-value="해양레저 지식백과" onclick="f_kibstv_category_search(this, '해양레저 지식백과')" style="cursor: pointer">Encyclopedia Of Marine Leisure</a>
                </li>
                <li>
                    <a class="cate" data-value="부스투어" onclick="f_kibstv_category_search(this, '부스투어')" style="cursor: pointer">Booth Tour</a>
                </li>
                <li>
                    <a class="cate" data-value="해양레저 컨퍼런스" onclick="f_kibstv_category_search(this, '해양레저 컨퍼런스')" style="cursor: pointer">Marine Leisure Conference</a>
                </li>
                <li>
                    <a class="cate" data-value="숏츠" onclick="f_kibstv_category_search(this, '숏츠')" style="cursor: pointer">Shorts</a>
                </li>
                <li>
                    <a class="cate" data-value="마린리뷰쇼" onclick="f_kibstv_category_search(this, '마린리뷰쇼')" style="cursor: pointer">Marine Review</a>
                </li>
                <li>
                    <a class="cate" data-value="해양레저 다이어리" onclick="f_kibstv_category_search(this, '해양레저 다이어리')" style="cursor: pointer">Marine Leisure Diary</a>
                </li>
                <li>
                    <a class="cate" data-value="보트클래스" onclick="f_kibstv_category_search(this, '보트클래스')" style="cursor: pointer">Boat Class</a>
                </li>
                <li>
                    <a class="cate" data-value="렛츠고 보트피싱" onclick="f_kibstv_category_search(this, '렛츠고 보트피싱')" style="cursor: pointer">Let's Go Fishing</a>
                </li>
                <li>
                    <a class="cate" data-value="참가기업 영상" onclick="f_kibstv_category_search(this, '참가기업 영상')" style="cursor: pointer">Exhibitors Video</a>
                </li>
                <li>
                    <a class="cate" data-value="2018 이전 영상" onclick="f_kibstv_category_search(this, '2018 이전 영상')" style="cursor: pointer">Before 2018</a>
                </li>
            </ul>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="tv_item padding_b">
        <div class="inner">
            <div class="sub_cont_tit" style="margin: 1% 1% 15px;">
                <div class="small">Results : <span class="total" style="font-weight: bold; color: #1d5cad; font-size: 2rem;"></span></div>
            </div>
            <div class="tvList">
                <ul>
                    <%--<li class="viewKibsTv">
                        <a>
                            <div class="thumb169 thumbBox"><img src="/img/thumb_tv_01.jpg" class="thumbImg">
                            </div>
                            <div class="title">해양레저 관광상품 개발 전문 "세운여행사" [마린리뷰쇼]</div>
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

<div class="popKibsTv popup">
    <div class="popup_inner">
        <div class="popup_wrap">
            <div class="pop_tit">
                <div class="tit">게시물 제목 노출</div>
                <a class="close" href="#close"><img src="/img/close_w.png"></a>
            </div>
            <div class="pop_box">
                <div class="pop_cont">
                    <!-- 비디오 -->
                    <div class='embed-container'>
                        <iframe src='' frameborder='0' allowfullscreen></iframe>
                    </div>
                    <!-- //비디오 -->
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

<script src="/js/front/kibstv.js?ver=<%=System.currentTimeMillis()%>"></script>
</body>
</html>