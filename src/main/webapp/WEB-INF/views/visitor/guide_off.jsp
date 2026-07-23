<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
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
    <title>2027 경기국제보트쇼</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <jsp:include page="../inc/common_css.jsp" flush="true"/>
    <jsp:include page="../inc/common_js_ko.jsp" flush="true"/>
    <jsp:include page="../inc/ad_scripts.jsp" flush="true"/>

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
                        <span>홈</span><span>참관객</span><span>참관안내</span>
                    </div>
                    <div class="sub_top_tit">참관안내</div>
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
                                <a href="/online/company.do">온라인 전시관</a>
                                <a href="/guide/summary_kiss.do">KISS(코리아 서프쇼)</a>
                                <a href="/guide/summary_kmts.do">KMTS(해양관광전)</a>
                                <%--<a href="/guide/maritime_expo.do">해상전시회(시흥 거북섬마리나)</a>--%>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">참관안내</div>
                            <div class="tabOptSel">
                                <a class="active" href="/visitor/guide_off.do">참관안내</a>
                                <a href="/visitor/apply.do">사전등록</a>
                                <a href="/guide/conference.do">컨퍼런스</a>
                                <a href="/guide/program.do">체험 프로그램</a>
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
        <div class="ssub_tab padding_t">
            <div class="inner">
                <ul class="ssub_tab_box">
                    <li><a href="/online/company.do">온라인</a></li>
                    <li class="active"><a href="/visitor/guide_off.do">오프라인</a></li>
                </ul>
            </div>
        </div>
        <!-- //section -->

        <!-- section -->
        <div class="vis_g vis_g1 padding_b">
            <div class="inner">
                <div class="sub_cont_tit">
                    <div class="big">참관안내</div>
                </div>
                <div class="vis_g_table">
                    <div class="table_box">
                        <table>
                            <colgroup>
                                <col width="20%">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td class="bgBlue">참관 시간</td>
                                <td>
                                    10:00~17:00</td>
                            </tr>
                            <tr>
                                <td class="bgBlue">무료 입장</td>
                                <td>
                                    미취학 아동, 65세 이상, 장애인, 국가유공자, 홈페이지 사전 등록자(1차), 무료초청장 소지자</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="sub_cont_tit">
                    <div class="big">입장료</div>
                </div>
                <div class="vis_g_table">
                    <div class="table_box">
                        <table>
                            <colgroup>
                                <col width="10%">
                                <col width="40%">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td class="bgBlue" colspan="2">사전 등록 기간</td>
                                <td class="bgBlue">입장료</td>
                            </tr>
                            <tr>
                                <td>1차</td>
                                <td>2026년 1월 1일(목) ~ 2026년 2월 15일(일)</td>
                                <td>무료 입장</td>
                            </tr>
                            <tr>
                                <td>2차</td>
                                <td>2026년 2월 16일(월) ~ 2026년 3월 3일(화)</td>
                                <td>5,000원(50% 할인)<br>- 행사일(3/12~3/14) 현장에서 할인 티켓 구입 必</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="vis_g_table">
                    <div class="table_box">
                        <table>
                            <colgroup>
                                <col width="50%">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td class="bgBlue">현장 티켓 구매</td>
                                <td class="bgBlue">입장료</td>
                            </tr>
                            <tr>
                                <td>2026년 3월 6일(금) ~ 2026년 3월 8일(일)</td>
                                <td>
                                    10,000원<br><br>
                                    동시개최행사 '캠핑&피크닉페어' 참관객은 '경기국제보트쇼' 입장객 구매 시,<br>
                                    60% 할인 적용(당일 출입증에 한함)
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="sub_cont_tit">
                    <div class="big">입장절차</div>
                </div>
                <div class="vis_g1_wrap">
                    <div class="vis_g1_box">
                        <div class="box">
                            <div class="icon"><img src="/img/icon_visitor02.png"></div>
                            <div class="txt1">
                                사전등록자
                            </div>
                            <div class="txt2 flex">
                                <div>
                                    <h3>1차</h3>
                                    <p>등록데스크에서 본인 확인</p>
                                    <p>출입증 수령</p>
                                </div>
                                <div>
                                    <h3>2차</h3>
                                    <p>등록데스크에서 본인 확인</p>
                                    <p>할인 티켓 구입</p>
                                    <p>출입증 수령</p>
                                </div>
                            </div>
                            <div style="margin-top: 20px;">
                                ※ 사전등록은 행사기간 3일 중 1일만 적용 가능
                            </div>
                        </div>
                    </div>
                    <div class="vis_g1_box">
                        <div class="box">
                            <div class="icon"><img src="/img/icon_visitor01.png"></div>
                            <div class="txt1">일반 관람객
                                <p>(현장등록)</p>
                            </div>
                            <div class="txt2">
                                <p>입장권 구매</p>
                                <p>등록카드 작성</p>
                                <p>출입증 수령</p>
                            </div>
                        </div>
                    </div>
                    <div class="vis_g1_box">
                        <div class="box">
                            <div class="icon"><img src="/img/icon_visitor03.png"></div>
                            <div class="txt1">초청장 지참자</div>
                            <div class="txt2">
                                <p>등록카드 작성</p>
                                <p>출입증 수령</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn_box">
                    <%--<a href="javascript:void(0);" onclick="alert('KIBS 2025 사전등록은 준비중입니다');return false;" class="btnSt01">KIBS 사전등록 바로가기</a>--%>
                    <a href="<c:url value="/visitor/apply.do"/>" class="btnSt01">KIBS 사전등록 바로가기</a>
                </div>
            </div>

        </div>
    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>