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
                    <span>홈</span><span>KIBS 안내</span><span>공지사항</span>
                </div>
                <div class="sub_top_tit">공지사항</div>
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
                        <div class="tabOptAct">KIBS 안내</div>
                        <div class="tabOptSel">
                            <a class="active" href="/guide/notice.do">KIBS 안내</a>
                            <a href="/exhibitor/guide.do">참가기업</a>
                            <a href="/visitor/guide_off.do">참관객</a>
                            <a href="/board/gallery.do">자료실</a>
                            <a href="/online/kibstv.do">온라인 전시관</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">공지사항</div>
                        <div class="tabOptSel">
                            <a class="active" href="/guide/notice.do">공지사항</a>
                            <a href="/guide/summary.do">행사소개</a>
                            <a href="/guide/program.do">2025 주요프로그램</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->

    <div class="board_view padding_tb">
        <div class="inner">
            <div class="board_view_box">
                <div class="board_view_tit">${noticeInfo.title}</div>
                <div class="board_view_info">
                    <div class="write">${noticeInfo.writer}</div>
                    <div class="date">
                        <c:set var="writeDate" value="${fn:replace(fn:split(noticeInfo.writeDate,' ')[0],'-','.')}" />
                        ${writeDate}
                    </div>
                    <div class="count">${noticeInfo.viewCnt}</div>
                </div>
                <div class="board_view_cont ql-editor">
                    ${noticeInfo.content}
                </div>
                <div class="board_view_file">
                    <c:if test="${not empty fileList}">
                        <c:forEach var="fileInfo" items="${fileList}" begin="0" end="${fileList.size()}" step="1" varStatus="status">
                            <%--<a href="/file/download.do?path=center/board/${fileInfo.folderPath}&fileName=${fileInfo.fullFileName}">${fileInfo.fileName}</a>--%>
                            <a href="javascript:void(0);" onclick="f_file_download('center/board/${fileInfo.folderPath}', '${fileInfo.fullFileName}')">${fileInfo.fileName}</a>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="board_view_btn">
                    <c:if test="${noticeInfo.prevId ne null and noticeInfo.prevId ne ''}">
                        <a href="/guide/noticeView.do?id=${noticeInfo.prevId}" class="prevBtn">이전글</a>
                    </c:if>
                    <a href="/guide/notice.do" class="listBtn">목록</a>
                    <c:if test="${noticeInfo.nextId ne null and noticeInfo.nextId ne ''}">
                        <a href="/guide/noticeView.do?id=${noticeInfo.nextId}" class="nextBtn">다음글</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>