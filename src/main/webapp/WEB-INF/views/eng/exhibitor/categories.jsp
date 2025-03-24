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
    <div class="sub_top">
        <div class="inner">
            <div class="sub_top_box">
                <div class="sub_top_nav">
                    <span>Home</span><span>Exhibitors</span><span>Exhibitors Categories</span>
                </div>
                <div class="sub_top_tit">Exhibitors Categories</div>
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
                        <div class="tabOptAct">Exhibitors</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2025</a>
                            <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a href="/eng/buyer/apply.do">Buyer</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Exhibitors Categories</div>
                        <div class="tabOptSel">
                            <a class="active" href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                            <a href="/eng/exhibitor/list.do">Exhibitor List</a>
                            <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>">Exhibitor Registration</a>
                            <a href="/eng/exhibitor/glance.do">Korea at a Glance</a>
                            <a href="/eng/exhibitor/marina.do">Marine Industry in korea</a>
                            <a href="/eng/exhibitor/match.do">Exhibitor-Buyer Matchmaking Program</a>
                            <a href="/eng/exhibitor/qna.do">QnA</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="exh_cate padding_tb">
        <div class="inner">
            <ul class="exh_cate_list">
                <li>
                    <div class="icon"><img src="/img/summ_img01.png"></div>
                    <div class="txtBox">
                        <div class="tit">Yacht & Boat</div>
                        <div class="txt">Powerboat, Yacht, Rubber / Combi / FRP / Aluminum / Carbon / Composite Boat
                            etc.
                        </div>
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/summ_img02.png"></div>
                    <div class="txtBox">
                        <div class="tit">Paddler’s World</div>
                        <div class="txt">Canoe, Kayak, Rowing, Rowboat, SUP, Dinghy Yacht, Unpowered Boat etc.</div>
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/summ_img03.png"></div>
                    <div class="txtBox">
                        <div class="tit">Work Boat</div>
                        <div class="txt">Government boats, Fire boats, Rescue ship / life boat, Patrol Boats,
                            Administrative ship, High speed boat, Other special ships and related equipment etc.
                        </div>
                    </div>
                </li>
                <%--<li>
                    <div class="icon"><img src="/img/summ_img04.png"></div>
                    <div class="txtBox">
                        <div class="tit">Ara-Marine Training / Experience Programs</div>
                    </div>
                </li>--%>
                <li class="w100">
                    <div class="icon"><img src="/img/summ_img07.png"></div>
                    <div class="txtBox">
                        <div class="tit">Marine Equipment, Safety & Marina Industry Show</div>
                        <div class="txt">Inboard/ outboard/ sterndrive engines, Trolling motor, Propeller, Marine
                            speaker, Cables & controls, Anchoring & Mooring, Fishfinder, Marine monitor, Trailers, GPS,
                            Trailers, Radar, Communication devices, Painting / polishing, Boat materials, Fixed /
                            floating pontoon, Pontoon / landing stage, Water front development / operation, Boat storage
                            / lease / maintenance, Life jacket, Tube, Emergency kit, Disaster control equipment,
                            Electronic / Mechanical tools and accessories etc.
                        </div>
                    </div>
                </li>
                <%--<li>
                    <div class="icon"><img src="/img/summ_img08.png"></div>
                    <div class="txtBox">
                        <div class="tit">Eco Friendly Marine</div>
                    </div>
                </li>--%>
                <li>
                    <div class="icon"><img src="/img/summ_img06.png"></div>
                    <div class="txtBox">
                        <div class="tit">Marine Tourism</div>
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/summ_img09.png"></div>
                    <div class="txtBox">
                        <div class="tit">Marine Leisure Hall</div>
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/summ_img05.png"></div>
                    <div class="txtBox">
                        <div class="tit">Caravan Show</div>
                        <div class="txt">Outdoor supplies, Caravan, Caravan - Camper van, Trailer, Traction device
                            etc.
                        </div>
                    </div>
                </li>
                <%--<li>
                    <div class="icon"><img src="/img/summ_img12.png"></div>
                    <div class="txtBox">
                        <div class="tit">K-Diving Expo</div>
                    </div>
                </li>--%>
                <%--<li>
                    <div class="icon"><img src="/img/summ_img07.png"></div>
                    <div class="txtBox">
                        <div class="tit">KIBS afloat</div>
                        <div class="txt">Demonstration of various marine leisure products such as boats and yachts,
                            Experience, Demo run, Test run etc.
                        </div>
                    </div>
                </li>--%>
            </ul>
        </div>
    </div>
    <!-- //section -->

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>