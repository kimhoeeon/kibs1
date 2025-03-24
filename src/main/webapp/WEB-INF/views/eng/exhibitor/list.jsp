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
                    <span>Home</span><span>Exhibitors</span><span>Exhibitor List</span>
                </div>
                <div class="sub_top_tit">Exhibitor List</div>
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
                        <div class="tabOptAct">Exhibitor List</div>
                        <div class="tabOptSel">
                            <a href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                            <a class="active" href="/eng/exhibitor/list.do">Exhibitor List</a>
                            <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>">Exhibitor Registration</a>
                            <a href="/eng/exhibitor/qna.do">QnA</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="year_tab exh_history_tab padding_t">
        <div class="inner">
            <div class="cmnt">※ 2008-2012 Exhibitor list will be downloaded as a PDF file</div>
            <div class="year_tab_box">
                <a class="cate active" data-value="2025" onclick="f_exhibitor_list_search(this, '2025')" style="cursor: pointer">2025</a>
                <a class="cate" data-value="2024" onclick="f_exhibitor_list_search(this, '2024')" style="cursor: pointer">2024</a>
                <a class="cate" data-value="2023" onclick="f_exhibitor_list_search(this, '2023')" style="cursor: pointer">2023</a>
                <a class="cate" data-value="2022" onclick="f_exhibitor_list_search(this, '2022')" style="cursor: pointer">2022</a>
                <a class="cate" data-value="2019" onclick="f_exhibitor_list_search(this, '2019')" style="cursor: pointer">2019</a>
                <a class="cate" data-value="2018" onclick="f_exhibitor_list_search(this, '2018')" style="cursor: pointer">2018</a>
                <a class="cate" data-value="2017" onclick="f_exhibitor_list_search(this, '2017')" style="cursor: pointer">2017</a>
                <a class="cate" data-value="2016" onclick="f_exhibitor_list_search(this, '2016')" style="cursor: pointer">2016</a>
                <a class="cate" data-value="2015" onclick="f_exhibitor_list_search(this, '2015')" style="cursor: pointer">2015</a>
                <a class="cate" data-value="2014" onclick="f_exhibitor_list_search(this, '2014')" style="cursor: pointer">2014</a>
                <a class="cate" data-value="2013" onclick="f_exhibitor_list_search(this, '2013')" style="cursor: pointer">2013</a>
                <a href="/file/kibs2012.zip" download>2012</a>
                <a href="/file/kibs2011.zip" download>2011</a>
                <a href="/file/kibs2010.zip" download>2010</a>
                <a href="/file/kibs2009.zip" download>2009</a>
                <a href="/file/kibs2008.zip" download>2008</a>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="exh_history padding_b">
        <div class="inner">
            <div class="exh_history_sh">
                <span class="search">
                    <input type="text" id="search_text" placeholder="Company Name">
                    <button type="button" onclick="exhibitorList(1, '')" style="cursor: pointer">Search</button>
                </span>
            </div>
            <div class="sub_cont_tit" style="margin: 1% 1% 15px;">
                <div class="small">Results : <span class="total" style="font-weight: bold; color: #1d5cad; font-size: 2rem;"></span></div>
            </div>
            <div class="exh_history_list">
                <div class="table_box">
                    <table>
                        <thead>
                        <tr>
                            <th>Company</th>
                            <th>Telephone</th>
                            <th>Homepage</th>
                            <th>Category</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%--<tr>
                            <td>
                                <div class="txtBlue">Company name</div>
                            </td>
                            <td>00-0000-0000</td>
                            <td><a href="" target="_blank">www.abc.com</a></td>
                            <td>Category name</td>
                        </tr>--%>
                    </table>
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
    <!-- //section -->

</div>

<script>
    document.addEventListener("keyup", function(event) {
        if (event.key === 'Enter') {
            exhibitorList(1, '');
        }
    });
</script>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

<script src="/js/front/eng/exhibitorList.js?ver=<%=System.currentTimeMillis()%>"></script>
</body>
</html>