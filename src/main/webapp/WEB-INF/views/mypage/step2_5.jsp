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

    <c:if test="${status ne 'logon'}">
        <script>
            alert("로그인해 주세요.");
            location.href = '/login.do';
        </script>
    </c:if>

    <c:if test="${status eq 'logon'}">

        <c:import url="../header.jsp" charEncoding="UTF-8"/>

        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>홈</span><span>참가기업</span><span>마이페이지</span>
                        </div>
                        <div class="sub_top_tit">마이페이지</div>
                    </div>
                </div>
            </div>
            <!-- //section -->


            <!-- section -->
            <div class="apply_s padding_tb" id="apply_s">
                <div class="inner">

                    <div class="apply_nav">
                        <div class="apply_nav_txt">
                            <div class="txt">${sessionScope.id} 님 환영합니다!</div>
                            <div class="btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/modify.do','${info.seq}')" class="btnSt01">회원정보관리</a>
                                <a href="javascript:void(0);" onclick="f_page_move('/mypage/index.do','${info.seq}')" class="btnSt01">마이페이지</a>
                            </div>
                        </div>
                        <div class="apply_nav_list">
                            <ul class="list1">
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step01.do','${info.seq}')">기본정보</a></li>
                                <li class="active">
                                    <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시 신청 정보</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_1.do','${info.seq}')">전시부스 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_2.do','${info.seq}')">상호간판 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_3.do','${info.seq}')">유틸리티 신청</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')">출입증 신청</a></li>
                                        <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_5.do','${info.seq}')">경품제공 신청</a></li>
                                        <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_6.do','${info.seq}')">웹 배너 신청</a></li>--%>
                                        <%--<li><a href="/mypage/step2_7.do">온라인 전시관 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_8.do','${info.seq}')">디렉토리 정보</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step03.do','${info.seq}')">전시참가 신청 완료</a></li>
                            </ul>
                        </div>
                    </div>


                    <!-- section -->
                    <div class="my_form form_s">
                        <div class="inner">
                            <div class="apply_tit">경품제공 신청</div>
                            <!-- 안내문구 -->
                            <div class="form_wrap">
                                <ul class="form_guide">
                                    <%--<li>2025 경기국제보트쇼 종료로 인해 <span class="txtRed">전시 정보 수정이 불가</span>합니다.</li>--%>
                                </ul>
                            </div>

                            <!-- 경품 관련 안내사항 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">경품 관련 안내사항</div>
                                </div>
                                <div class="form_guide_table table_box">
                                    <table>
                                        <colgroup>
                                            <col width="15%">
                                            <col width="35%">
                                            <col width="50%">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>구분</th>
                                            <th>내용</th>
                                            <th>활동내역</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>행사용 경품</td>
                                            <td>
                                                <p>2/28~3/2 보트쇼 행사 간 경품 추첨용</p>
                                            </td>
                                            <td>
                                                <p>보트쇼 전 참관객에게 배포되는 경품 응모권과 응모함에 경품협찬 업체의 회사로고 삽입</p>
                                                <p>각종 미디어/매체에 경품제공 내역 노출</p>
                                                <p>3일의 전시기간 고려하여, 경품 수량 3배수로 협찬 희망</p>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <!-- 경품제공 신청 -->
                            <div class="form_wrap">
                                <div class="form_tit">
                                    <div class="big">경품제공 신청</div>
                                </div>
                                <div class="form_chuga">
                                    <input type="hidden" name="exhibitorSeq" value="${info.seq}">
                                    <c:if test="${not empty giftList}">
                                        <c:forEach var="gift" items="${giftList}" begin="0" end="${giftList.size()}" step="1" varStatus="status">
                                            <div class="form_chuga_list">
                                                <input type="hidden" name="giftSeq" value="${gift.seq}">
                                                <div class="cont">
                                                    <div class="name">${gift.giftName}</div>
                                                    <div class="gubun">${gift.giftGbn} / ${gift.giftCnt} 개</div>
                                                </div>
                                                <div onclick="f_gift_modify_modal('select', '${gift.seq}');" class="modifyFormGift modifyFormList" style="cursor: pointer;">수정</div>
                                                <div onclick="f_gift_remove('select', '${gift.seq}');" class="delFormGift delFormList" style="cursor: pointer;">삭제</div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <a id="gift_add_btn" class="addFormGift addFormList btnSt01" style="cursor: pointer">여기를 클릭하여 경품 등록</a>
                                </div>
                            </div>
                        </div>

                        <div class="form_btn">
                            <a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_4.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">이전</div>
                                <div class="small">출입증 신청</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_mypage_comp('2_5','${info.seq}')" class="form_btn_next">
                                <div class="big">다음</div>
                                <div class="small">웹배너 신청</div>
                            </a>
                        </div>

                    </div>
                </div>
                <!-- //section -->
            </div>
        </div>

        </div>

        <!-- 경품등록팝업 -->
        <div class="popFormGift popForm popup">
            <div class="popup_inner">
                <div class="popup_wrap">
                    <div class="pop_tit">
                        <div class="tit">경품제공 신청 등록/수정</div>
                        <a class="close" href="javascript:f_gift_init();" style="cursor: pointer"><img src="/img/close_w.png"></a>
                    </div>
                    <div class="pop_box">
                        <form id="giftForm" name="giftForm" method="post" onsubmit="return false;">
                            <div class="pop_cont">
                                <!-- 폼박스 -->
                                <div class="form_s">
                                <!-- 회원계정정보 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">경품제공 신청서</div>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item req">
                                                <p>구분</p>
                                            </div>
                                            <div class="input">
                                                <label><input type="radio" id="gift_gbn" value="행사용" checked>행사용</label>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>수량</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="gift_cnt" value="0" class="onlyNum">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>분류</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="gift_classify">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>품목명</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="gift_name">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>경품설명</p>
                                            </div>
                                            <div class="input">
                                                <textarea id="gift_content"></textarea>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>경품사진</p>
                                            </div>
                                            <div class="input file_box">
                                                <input type="text" id="gift_photo" class="upload_name" value="" disabled="disabled">
                                                <input type="file" name="giftPhotoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                <label>파일선택</label>
                                                <span class="giftPhotoBox"></span>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>회사로고</p>
                                            </div>
                                            <div class="input file_box">
                                                <input type="text" id="gift_company_logo" class="upload_name" value="" disabled="disabled">
                                                <input type="file" name="giftCompanyLogoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                <label>파일선택</label>
                                                <span class="giftCompanyLogoBox"></span>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>소비자가</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="gift_price" value="0" class="onlyNum">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>협찬가</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="gift_sponsor_price" value="0" class="onlyNum">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>비고</p>
                                            </div>
                                            <div class="input">
                                                <textarea id="gift_note"></textarea>
                                            </div>
                                        </li>
                                    </ul>
                                    <a class="btnSave" onclick="f_gift_add()">등록 및 수정</a>
                                </div>
                            </div>
                                <!-- //폼박스 -->
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    </c:if>
</body>
</html>