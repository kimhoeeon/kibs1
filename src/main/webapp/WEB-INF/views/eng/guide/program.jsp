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
                        <span>Home</span><span>KIBS 2026</span><span>Program</span>
                    </div>
                    <div class="sub_top_tit">Program</div>
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
                            <div class="tabOptAct">KIBS 2026</div>
                            <div class="tabOptSel">
                                <a class="active" href="/eng/guide/summary.do">KIBS 2026</a>
                                <a href="/eng/exhibitor/categories.do">Exhibitors</a>
                                <a href="/eng/buyer/glance.do">Buyer</a>
                                <a href="/eng/board/notice.do">News</a>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">Program</div>
                            <div class="tabOptSel">
                                <a href="/eng/guide/summary.do">Overview</a>
                                <a href="/eng/guide/certify.do">International Certification & Awards</a>
                                <a href="/eng/guide/conference.do">Conference</a>
                                <a class="active" href="/eng/guide/program.do">Program</a>
                                <a href="/eng/guide/bi.do">KIBS BI</a>
                                <a href="/eng/guide/venue.do">Venue</a>
                                <a href="/eng/guide/contact.do">Contact us</a>
                                <%--<a href="/eng/guide/brochure.do">E-Brochure</a>--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->

        <!-- section -->
        <div class="exh_g exh_g2 padding_tb">
            <div class="inner">

                <!-- <div class="img_box" style="margin-bottom: 40px;"><img src="/img/img_soon_eng.jpg"></div> -->
                <div class="img_box" style="margin-bottom: 40px; text-align: center;"><img src="/img/program_0213_eng.png"></div>

                <!-- <div class="btn_box"><a class="btnSt01" href="program_2025.html">2025 Experience Programs</a></div> -->

                <div class="sub_cont_tit">
                    <div class="big">Experience Program Pre-Registration</div>
                </div>

                <div class="exh_g_table break" style="padding-bottom: 20px;">
                    <div class="table_box">
                        <table>
                            <colgroup>
                                <col width="15%">
                                <col width="20%">
                                <col width="25%">
                                <col width="15%">
                                <col width="15%">
                                <col width="">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>Experience Program</th>
                                <th>Description</th>
                                <th>Schedule</th>
                                <th>Eligible Participants</th>
                                <th>Application Deadline</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Metropolitan Area Major Marina Tour</td>
                                <td>On-site tour program visiting major marinas in the metropolitan area</td>
                                <td>
                                    <span style="font-weight: bold;">2026. 3. 7.(Sat), 3. 8.(Sun) 10:20–14:20</span><br>
                                    (Sat) Incheon Wangsan Marina, Gimpo Ara Marina<br>
                                    (Sun) Jeongok Marina, Jebu Marina
                                </td>
                                <td>Prospective marina users and related personnel</td>
                                <td>~ 2026. 2. 27. (First-come, first-served)</td>
                                <td>
                                    <a href="https://kibs.com/guide/noticeView.do?id=N0000153" target="_blank" class="btnSt01" style="padding: 10px 0;">Learn More</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="exh_g_table" style="padding-bottom: 20px;">
                    <div class="table_box">
                        <table>
                            <colgroup>
                                <col width="15%">
                                <col width="20%">
                                <col width="25%">
                                <col width="15%">
                                <col width="15%">
                                <col width="">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>Experience Program</th>
                                <th>Description</th>
                                <th>Schedule</th>
                                <th>Duration</th>
                                <th>Application Deadline</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Docent Tour with Pro Angler Choi Woon-jung</td>
                                <td>Program providing guided explanations of key exhibitors and special zones with expert commentary</td>
                                <td>
                                    <span style="font-weight: bold;">2026. 3. 7.(Sat), 3. 8.(Sun) 14:00</span>
                                </td>
                                <td>Approximately 60 minutes</td>
                                <td>~ 2026. 2. 26. (First-come, first-served)</td>
                                <td>
                                    <a href="https://kibs.com/guide/noticeView.do?id=N0000152" target="_blank" class="btnSt01" style="padding: 10px 0;">Learn More</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="exh_g_table">
                    <div class="table_box">
                        <table>
                            <colgroup>
                                <col width="15%">
                                <col width="20%">
                                <col width="25%">
                                <col width="15%">
                                <col width="15%">
                                <col width="">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>Experience Program</th>
                                <th>Description</th>
                                <th>Schedule</th>
                                <th>Experience Time</th>
                                <th>Application Deadline</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Surf Rider Experience (Surfing Cube)</td>
                                <td>Program offering a surfing experience using an indoor artificial wave system</td>
                                <td>
                                    <span style="font-weight: bold;">2026. 3. 6.(Fri)</span><br>13:00 / 14:30 (2 sessions per day)<br><br>
                                    <span style="font-weight: bold;">2026. 3. 7.(Sat), 3. 8.(Sun)</span><br>11:00 / 14:00 / 15:00 / 16:00 (4 sessions per day)
                                </td>
                                <td>Approximately 50 minutes per session</td>
                                <td>~ 2026. 2. 26. (First-come, first-served)</td>
                                <td>
                                    <a href="https://kibs.com/guide/noticeView.do?id=N0000155" target="_blank" class="btnSt01" style="padding: 10px 0;">Learn More</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->

    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>