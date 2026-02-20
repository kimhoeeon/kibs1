<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
    <jsp:include page="../inc/ad_scripts.jsp" flush="true"/>
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
</head>

<body>

    <c:import url="../header.jsp" charEncoding="UTF-8"/>

    <div id="container">

        <!-- section -->
        <div class="sub_top">
            <div class="inner">
                <div class="sub_top_box">
                    <div class="sub_top_nav">
                        <span>홈</span><span>참관객</span><span>체험 프로그램</span>
                    </div>
                    <div class="sub_top_tit">체험 프로그램</div>
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
                            <div class="tabOptAct">참관객</div>
                            <div class="tabOptSel">
                                <a href="/guide/summary.do">KIBS 안내</a>
                                <a href="/exhibitor/guide.do">참가기업</a>
                                <a class="active" href="/visitor/guide_off.do">참관객</a>
                                <a href="/guide/notice.do">미디어센터</a>
                                <a href="/online/kibstv.do">온라인 전시관</a>
                                <a href="/guide/maritime_expo.do">해상전시회(시흥 거북섬마리나)</a>
                                <a href="/guide/summary_kiss.do">KISS(코리아 서프쇼)</a>
                                <a href="/guide/summary_kmts.do">KMTS(해양관광전)</a>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">체험 프로그램</div>
                            <div class="tabOptSel">
                                <a href="/visitor/guide_off.do">참관안내</a>
                                <a href="/visitor/apply.do">사전등록</a>
                                <a href="/guide/conference.do">컨퍼런스</a>
                                <a class="active" href="/guide/program.do">체험 프로그램</a>
                                <a href="/visitor/login.do">참관신청확인</a>
                                <a href="/visitor/faq.do">FAQ</a>
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

                <!-- <div class="img_box" style="margin-bottom: 40px;"><img src="/img/img_soon_kor.jpg"></div> -->
                <div class="img_box" style="margin-bottom: 40px; text-align: center;"><img src="/img/program_0220.png?ver=20260214"></div>

                <!-- <div class="btn_box"><a class="btnSt01" href="program_2025.html">전년도 체험 프로그램 보기</a></div> -->
                <div class="sub_cont_tit">
                    <div class="big">체험프로그램 사전등록</div>
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
                                <th>체험프로그램</th>
                                <th>내용</th>
                                <th>일정</th>
                                <th>참여대상</th>
                                <th>신청기한</th>
                                <th>사전신청 안내</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>수도권 주요 마리나 투어</td>
                                <td>수도권 주요 마리나를 직접 방문하는 현장 투어 프로그램</td>
                                <td>
                                    <span style="font-weight: bold;">2026. 3. 7.(토), 3. 8.(일) 10:20–14:20</span><br>
                                    (토) 인천 왕산마리나, 김포 아라마리나<br>
                                    (일) 전곡마리나, 제부마리나
                                </td>
                                <td>마리나 실제 사용예정자 및 관련자</td>
                                <td>~ 2026. 2. 27. (선착순)</td>
                                <td>
                                    <a href="https://kibs.com/guide/noticeView.do?id=N0000153" target="_blank" class="btnSt01" style="padding: 10px 0;">바로가기</a></td>
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
                                <th>체험프로그램</th>
                                <th>내용</th>
                                <th>일정</th>
                                <th>소요시간</th>
                                <th>신청기한</th>
                                <th>사전신청 안내</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>최운정 낚시프로와 함께하는 도슨트 투어</td>
                                <td>전문가 해설과 함께 주요 참가기업 및 특별관을 순회하며 전시 품목과 기술을 설명하는 프로그램</td>
                                <td>
                                    <span style="font-weight: bold;">2026. 3. 7.(토), 3. 8.(일) 14:00</span>
                                </td>
                                <td>약 60분</td>
                                <td>~ 2026. 2. 26. (선착순)</td>
                                <td>
                                    <a href="https://kibs.com/guide/noticeView.do?id=N0000152" target="_blank" class="btnSt01" style="padding: 10px 0;">바로가기</a>
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
                                <th>체험프로그램</th>
                                <th>내용</th>
                                <th>일정</th>
                                <th>체험시간</th>
                                <th>신청기한</th>
                                <th>사전신청 안내</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>서핑라이더 체험 (서핑큐브)</td>
                                <td>실내 인공파도를 활용하여 서핑을 체험하는 프로그램</td>
                                <td>
                                    <span style="font-weight: bold;">2026. 3. 6.(금)</span><br>13:00 / 14:30 (1일 2회)<br><br>
                                    <span style="font-weight: bold;">2026. 3. 7.(토), 3. 8.(일)</span><br>11:00 / 14:00 / 15:00 / 16:00 (1일 4회)
                                </td>
                                <td>회당 약 50분</td>
                                <td>~ 2026. 2. 26. (선착순)</td>
                                <td>
                                    <a href="https://kibs.com/guide/noticeView.do?id=N0000155" target="_blank" class="btnSt01" style="padding: 10px 0;">바로가기</a>
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