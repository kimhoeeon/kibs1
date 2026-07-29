<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

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
    <title>2027 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <jsp:include page="../../inc/common_css.jsp" flush="true"/>
    <jsp:include page="../../inc/common_js_en.jsp" flush="true"/>
    <jsp:include page="../../inc/ad_scripts.jsp" flush="true"/>

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
                        <span>Home</span><span>Exhibitors</span><span>Exhibitor Registration</span>
                    </div>
                    <div class="sub_top_tit">Exhibitor Registration</div>
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
                            <div class="tabOptAct">Exhibitors</div>
                            <div class="tabOptSel">
                                <a href="/eng/guide/summary.do">KIBS 2027</a>
                                <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                                <a href="/eng/buyer/glance.do">Buyers</a>
                                <a href="/eng/board/notice.do">News</a>
                            </div>
                        </div>
                        <div class="tabOpt2 tabOption">
                            <div class="tabOptAct">Exhibitor Registration</div>
                            <div class="tabOptSel">
                                <a href="/eng/exhibitor/categories.do">Exhibitor Categories</a>
                                <a class="active" href="/eng/apply/step01.do">Exhibitor Registration</a>
                                <a href="/eng/exhibitor/glance.do">Korea at a Glance</a>
                                <a href="/eng/exhibitor/marina.do">Marine Industry in Korea</a>
                                <a href="/eng/exhibitor/match.do">Exhibitor-Buyer Matchmaking Program</a>
                                <a href="/eng/online/company.do">Exhibitor List</a>
                                <a href="/eng/exhibitor/qna.do">FAQ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->

        <!-- section -->
        <div class="apply_s padding_tb" id="apply_s">
            <div class="inner">

                <div class="apply_nav">
                    <div class="apply_nav_list">
                        <ul class="list1">
                            <li><a>Basic Info</a></li>
                            <li class="active">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step2_1.do','${info.seq}')">Exhibition Info</a>
                                <ul class="list2">
                                    <li><a>Booth</a></li>
                                    <li><a>Yacht/Boat</a></li>
                                    <li><a>Signboard</a></li>
                                    <li><a>Utilities</a></li>
                                    <li class="active"><a>Badges</a></li>
                                    <li><a>Giveaways</a></li>
                                    <li><a>Directory</a></li>
                                </ul>
                            </li>
                            <li><a>Complete</a></li>
                        </ul>
                    </div>
                </div>

                <div class="apply_step">
                    <div class="apply_tit">Badge Application</div>
                    <div class="apply_step_wrap">
                        <div class="apply_step_box step1">
                            <div class="gubun">
                                <p>Step 1</p>Basic Info
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="100" max="100"></progress>
                            </div>
                            <div class="txt">Completed</div>
                        </div>
                        <div class="apply_step_box step2">
                            <div class="gubun">
                                <p>Step 2</p>Exhibition Info
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="62.5" max="100"></progress>
                            </div>
                            <div class="txt">In Progress <p>(<span>4</span> of <span>7</span> completed)</p></div>
                        </div>
                        <div class="apply_step_box step3">
                            <div class="gubun">
                                <p>Step 3</p>Complete
                            </div>
                            <div class="gauge">
                                <progress class="gauge_bar" value="0" max="100"></progress>
                            </div>
                            <div class="txt">Requirement Not Met</div>
                        </div>
                    </div>
                </div>

                <!-- section -->
                <div class="apply_form form_s padding_t">
                    <div class="inner">

                        <!-- 안내문구 -->
                        <div class="form_wrap">
                            <ul class="form_guide">
                                <li>Please provide the names and positions of all personnel who will stay at your booth during the move-in, construction, and exhibition periods.</li>
                                <li>Badges will be issued for access to the exhibition hall and distributed at the exhibition hall entrance or the Organizer’s office during the move-in period.</li>
                                <li>Personnel without badges may be restricted from entering the exhibition hall. Please make sure to enter the information accurately.</li>
                            </ul>
                        </div>

                        <!-- 출입증 신청 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Badge Application</div>
                                <div class="small">Company Name : ${exhibitorInfo.companyNameEn}<c:if test="${exhibitorInfo.companyNameKo ne exhibitorInfo.companyNameEn}"> (${exhibitorInfo.companyNameKo})</c:if></div>
                            </div>
                            <div class="form_tb form_pass_eng form_add">
                                <ul class="formPassHd formTbHd">
                                    <li class="passNum"><p>No</p></li>
                                    <li class="passNameE req"><p>First Name</p></li>
                                    <li class="passNameE req"><p>Last Name</p></li>
                                    <li class="passPosE req"><p>Position</p></li>
                                    <li class="passNote"><p>Remarks</p></li>
                                    <li class="passDel"><p>Delete</p></li>
                                </ul>
                                <c:if test="${empty passList}">
                                    <ul class="formPassBd formTbBd">
                                        <input type="hidden" name="passSeq" value="">
                                        <li class="passNum addNum">1</li>
                                        <li class="passNameE"><input type="text" name="pass_first_name" class="onlyEngCompany" placeholder="First Name"></li>
                                        <li class="passNameE"><input type="text" name="pass_last_name" class="onlyEngCompany" placeholder="Last Name"></li>
                                        <li class="passPosE"><input type="text" name="pass_position_en" class="onlyEngCompany" placeholder="Position"></li>
                                        <li class="passNote"><input type="text" name="pass_note" placeholder="Remarks"></li>
                                        <li class="passDel addDel"><img src="/img/icon_del.png"></li>
                                    </ul>
                                </c:if>

                                <c:if test="${not empty passList}">
                                    <c:forEach var="pass" items="${passList}" begin="0" end="${passList.size()}" step="1" varStatus="status">
                                        <ul class="formPassBd formTbBd">
                                            <input type="hidden" name="passSeq" value="${pass.seq}">
                                            <li class="passNum addNum">${status.index + 1}</li>
                                            <li class="passNameE"><input type="text" name="pass_first_name" class="onlyEngCompany" value="${pass.passFirstName}" placeholder="First Name"></li>
                                            <li class="passNameE"><input type="text" name="pass_last_name" class="onlyEngCompany" value="${pass.passLastName}" placeholder="Last Name"></li>
                                            <li class="passPosE"><input type="text" name="pass_position_en" class="onlyEngCompany" value="${pass.passPositionEn}" placeholder="Position"></li>
                                            <li class="passNote"><input type="text" name="pass_note" value="${pass.passNote}" placeholder="Remarks"></li>
                                            <li class="passDel addDel"><img src="/img/icon_del.png"></li>
                                        </ul>
                                    </c:forEach>
                                </c:if>
                                <div class="form_tb_btn form_add_btn">Add</div>
                            </div>

                        </div>

                        <div class="form_btn">
                            <a href="javascript:void(0);" onclick="f_page_move('/eng/apply/step2_3.do','${info.seq}')" class="form_btn_prev">
                                <div class="big">PREV</div>
                                <div class="small">Utility Services</div>
                            </a>
                            <a href="javascript:void(0);" onclick="f_apply_comp('2_4','${info.seq}')" class="form_btn_next">
                                <div class="big">NEXT / SKIP</div>
                                <div class="small">Giveaway Application</div>
                            </a>
                        </div>

                    </div>
                </div>
                <!-- //section -->
            </div>
        </div>

    </div>

    <c:import url="../footer.jsp" charEncoding="UTF-8"/>

    <script type="text/javascript">
        $(document).ready(function() {
            // 출입증 신청 기간 마감 체크
            // 마감일시: 2026-07-24 06:05:00 (한국 시간 기준)
            var deadline = new Date("2027-07-24T06:05:00+09:00");
            var now = new Date();

            if (now > deadline) {
                // 마감 시간이 지났을 경우 Swal 팝업 노출
                Swal.fire({
                    icon: 'warning',
                    title: '[ Badge Application ]',
                    html: '<span style="font-size: 1.2em;">Badge applications are now closed.<br>For additional requests, please contact the Secretariat.</span>',
                    allowOutsideClick: false,    // 배경 클릭으로 닫기 방지
                    allowEscapeKey: false,       // ESC 키로 닫기 방지
                    showConfirmButton: true,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: 'OK'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // 확인 버튼 클릭 시 이전 페이지나 지정된 페이지로 강제 이동
                        // (필요 시 /eng/index.do 또는 마이페이지 메인으로 URL 수정 가능)
                        window.location.href = '/eng/mypage/index.do';
                    }
                });

                // 마감되었으므로 폼 내부 모든 input 비활성화 처리 (추가 안전장치)
                $('.form_pass_eng input').prop('disabled', true);
                $('.form_add_btn').hide(); // Add 버튼 숨김
            }
        });
    </script>
</body>
</html>