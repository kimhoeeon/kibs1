<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <jsp:include page="../../inc/ad_scripts.jsp" flush="true"/>
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
    <title>2026 KIBS</title>

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
                    <span>Home</span><span>Buyer</span><span>Marine Industry in korea</span>
                </div>
                <div class="sub_top_tit">Marine Industry in korea</div>
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
                        <div class="tabOptAct">Buyer</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2026</a>
                            <a href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a class="active" href="/eng/buyer/apply.do">Buyer</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Marine Industry in korea</div>
                        <div class="tabOptSel">
                            <a href="https://www.investkorea.org/ik-en/index.do" target="_blank">Visitor guide</a>
                            <a href="/eng/buyer/apply.do">Pre-registration</a>
                            <a href="/eng/buyer/glance.do">Korea at a Glance</a>
                            <a class="active" href="/eng/buyer/marina.do">Marine Industry in korea</a>
                            <a href="/eng/buyer/match.do">Exhibitor-Buyer Matchmaking Program</a>
                            <a href="/eng/buyer/qna.do">FAQ</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="marina_s1 padding_tb">
        <div class="inner">
            <div class="sub_cont_tit">
                <div class="big">
                    The 2nd National Marina<br>
                    Development Plan of Korea(2020~2029)
                </div>
                <div class="small">Central Government of Korea sponsored plan launched in 2029 for the development of 70 marinas.</div>
            </div>

            <div class="sec_box">

                <div class="box">
                    <div class="img1"><img src="/img/img_marina_01_01.jpg"></div>
                    <div class="text">
                        The Government supported plan to develop a recreational boating culture has created the fastest growing boating activity in Asia. Since 2009 when the coastal regions were opened for recreational activity the unprecedented numbers of people actively involved in going boating and the annual increases confirm that this is a sustained and fast moving market that has fully embraced “messing about in boats.”<br><br>
                        The large disparity between driving licences issued and registration of leisure boats can be attributed to respect for the Police and controls applied by them to this system compared to the current lack of resources of the Coastguard. It is safe to assume that the number of boats currently in use is probably more than double the number registered.
                    </div>
                </div>

            </div>

        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="marina_s2 padding_b">
        <div class="inner">
            <div class="sub_cont_tit">
                <div class="big">The New Hub of Korea Marina</div>
                <div class="small">
                    Jeongok Port in Hwaseong City has changed from a small fishing harbour into a modern 200 berth marina with dry storage ashore for a further 500 Boats and a growing café culture making it a weekend leisure destination. Gyeonggi Bay offering abundant sea angling has become the hub for the best boating location with Jebu Marina adding another 300 berths to provide the 2nd largest to provide the largest and best equipped combined facilities in Korea.
                </div>
            </div>

            <div class="sec_box">

                <div class="box">
                    <div class="img1"><img src="/img/img_marina_02_01.jpg"></div>
                    <div class="list">
                        <div class="item">
                            <div class="img"><img src="/img/img_marina_02_02.jpg"></div>
                            <div class="tit">Jeongok Marina(Hwaseong)</div>
                            <div class="descrip">
                                Moorings for 200 yachts (completed in 2011)
                                Main facilities: Marina and supporting facilities, pleasure boat pier, marine park, aquatic leisure facilities.
                            </div>
                        </div>
                        <div class="item">
                            <div class="img"><img src="/img/img_marina_02_03.jpg"></div>
                            <div class="tit">Jebu Marina(Hwaseong)</div>
                            <div class="descrip">
                                Moorings for 300 yachts (completed in 2021) Main facilities: Marina and supporting facilities, indoor/outdoor marine recreation center, marine zoo and botanical garden, observation deck.
                            </div>
                        </div>
                        <div class="item">
                            <div class="img"><img src="/img/img_marina_02_04.jpg"></div>
                            <div class="tit">Ara Marina(Gimpo)</div>
                            <div class="descrip">
                                Moorings for 192 yachts (completed in 2012) Main facilities: Marina and supporting facilities, petrol station pleasure boat pier, accommodations, premium outlet.
                            </div>
                        </div>
                        <div class="item">
                            <div class="img"><img src="/img/img_marina_02_05.jpg"></div>
                            <div class="tit">Ansan Marina(Ansan)</div>
                            <div class="descrip">
                                Moorings for 300 yachts (To be completed in 2027) Main facilities: Marina and supporting facilities, accommodations, auto camping site, oceanic swimming pool, fishing site.
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </div>
    <!-- section -->

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>