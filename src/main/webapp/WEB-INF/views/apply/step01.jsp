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
    <meta name="autocomplete" content="off"/>
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

<c:choose>
    <c:when test="${mode eq 'end'}">
        <script>
            alert('참가기업 신청이 마감되었습니다.');
            window.location.href = '/guide/summary.do';
        </script>
    </c:when>
    <c:otherwise>

        <c:if test="${status eq 'logon'}">
            <script>
                alert("이미 신청된 아이디로 로그인 중입니다.\n신규 참가신청은 로그아웃 후 시도해주세요.");
                window.location.href = '/';
            </script>
        </c:if>

        <c:if test="${status ne 'logon'}">

            <c:import url="../header.jsp" charEncoding="UTF-8"/>

            <%-- 24.02.29 참가업체신청 종료--%>
            <div id="container">

                <!-- section -->
                <div class="sub_top">
                    <div class="inner">
                        <div class="sub_top_box">
                            <div class="sub_top_nav">
                                <span>홈</span><span>참가기업</span><span>참가기업 신청</span>
                            </div>
                            <div class="sub_top_tit">참가기업 신청</div>
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
                                    <div class="tabOptAct">참가기업</div>
                                    <div class="tabOptSel">
                                        <a href="/guide/notice.do">KIBS 안내</a>
                                        <a class="active" href="/exhibitor/guide.do">참가기업</a>
                                        <a href="/visitor/guide_off.do">참관객</a>
                                        <a href="/board/gallery.do">자료실</a>
                                        <a href="/online/kibstv.do">온라인 전시관</a>
                                    </div>
                                </div>
                                <div class="tabOpt2 tabOption">
                                    <div class="tabOptAct">참가기업 신청</div>
                                    <div class="tabOptSel">
                                        <a href="/exhibitor/guide.do">참가기업</a>
                                        <a class="active" href="/apply/step01.do">참가기업 신청</a>
                                        <a href="/exhibitor/login.do">참가신청확인</a>
                                        <a href="/exhibitor/faq.do">FAQ</a>
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
                            <!-- <div class="apply_nav_tit">기본정보</div> -->
                            <div class="apply_nav_list">
                                <ul class="list1">
                                    <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/apply/step01.do','${info.seq}')">기본정보</a></li>
                                    <li>
                                        <a>전시 신청 정보</a>
                                        <ul class="list2">
                                            <li><a>전시부스 신청</a></li>
                                            <li><a>상호간판 신청</a></li>
                                            <li><a>유틸리티 신청</a></li>
                                            <li><a>출입증 신청</a></li>
                                            <li><a>경품제공 신청</a></li>
                                            <%--<li><a href="/apply/step2_6.do">웹 배너 신청</a></li>--%>
                                            <%--<li><a href="/apply/step2_7.do">온라인 전시관 신청</a></li>--%>
                                            <li><a>디렉토리 정보</a></li>
                                        </ul>
                                    </li>
                                    <li><a>전시참가 신청 완료</a></li>
                                </ul>
                            </div>
                        </div>

                        <div class="apply_step">

                            <div class="apply_top">
                                <div class="nti">참가기업 신청 양식입니다</div>
                                <a href="/visitor/apply.do" class="btn">참관객 신청 바로가기</a>
                            </div>

                            <div class="apply_tit">기본 정보</div>
                            <div class="apply_step_wrap">
                                <div class="apply_step_box step1">
                                    <div class="gubun">
                                        <p>Step 1</p>기본정보 입력
                                    </div>
                                    <div class="gauge">
                                        <progress class="gauge_bar" value="5" max="100"></progress>
                                    </div>
                                    <div class="txt">하단의 참가기업 정보를 입력해 주세요</div>
                                </div>
                                <div class="apply_step_box step2">
                                    <div class="gubun">
                                        <p>Step 2</p>전시 신청 정보
                                    </div>
                                    <div class="gauge">
                                        <progress class="gauge_bar" value="0" max="100"></progress>
                                    </div>
                                    <div class="txt">이전 단계를 먼저 완료해 주세요</div>
                                </div>
                                <div class="apply_step_box step3">
                                    <div class="gubun">
                                        <p>Step 3</p>전시참가 신청 완료
                                    </div>
                                    <div class="gauge">
                                        <progress class="gauge_bar" value="0" max="100"></progress>
                                    </div>
                                    <div class="txt">이전 단계를 먼저 완료해 주세요</div>
                                </div>
                            </div>
                        </div>

                        <!-- section -->
                        <div class="apply_form form_s padding_t">
                            <div class="inner">

                                <!-- 약관 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">전시회 참가규정</div>
                                    </div>
                                    <div class="form_pri">
                                        <div class="form_pri_box">
                                            <div class="form_pri_titt">제1조 (용어의 정의)</div>
                                            1. “전시회”라 함은 “2025 경기국제보트쇼(KIBS 2025)"를 말한다.<br>
                                            2. ‘주최자’라 함은 경기도 및 공동주최기관을 말한다.<br>
                                            3. “주관자”라 함은 전시회를 주관하는 ㈜킨텍스를 말한다.<br>
                                            4. “전시자”라 함은 전시회 참가를 위해 참가신청서 제출과 함께 계약금을 납부한 회사·조합· 단체 및 개인 등의 전시 참가자를 말한다.<br><br>

                                            <div class="form_pri_titt">제 2 조 (참가신청 및 계약)</div>
                                            1. 전시회 참가신청을 하고자 하는 전시자는 온라인을 통한 부스 참가신청을 완료하고, 주관자가 발급하는 청구 서류에 따라 참가비 전액을 납입함으로써 참가신청이 완료된다.<br>
                                            2. 2조 1항에 따라 참가 신청이 완료되지 않은 경우, 전시회의 어떠한 행사에도 참가할 수 없다.<br>
                                            3. 참가비는 독립부스 1,800,000원/부스, 조립부스 2,100,000원/부스이다. (부가가치세 별도 / 현금납부 / 할인 사항 별도적용) 단, 독립부스의 경우 최소 2부스 이상부터 신청이 가능하다.<br>
                                            4. 유틸리티 서비스는 참가비 전액 납입 완료 후 전시자의 필요에 따라 별도로 신청하며, 주관자가 발급하는 청구 서류에 따라 기술지원 서비스 사용료 전액을 납입한 경우 상응하는 서비스를 제공한다. <br>
                                            5. 유틸리티 서비스 및 조립부스 장치공사는 전시자의 신청에 따라 주관자가 제공하며, 참가신청 및 유틸리티 서비스 신청 내용에 변동사항이 발생할 경우 즉시 주관자에게 이를 전자메일로 알려야 한다. 미통보로 인한 불이익에 대해서는 전시자가 책임을 진다.<br>
                                            6. 전시자는 참가신청 상의 내용에 변동사항이 발생할 경우 즉시 주관자에게 이를 서면으로 통보하여야 하며, 미통보로 인한 불이익에 대해서는 전시자가 책임을 진다.<br>
                                            7. 전시장 면적이 소진된 경우와 출품 품목이 전시회에 적합하지 않다고 판단할 경우 주관자는 참가신청 접수를 거부할 수 있다.<br><br>

                                            <div class="form_pri_titt">제 3 조 (납입조건)</div>
                                            전시자는 참가비 50%에 해당하는 대금을 신청서 제출 후 15일 이내, 잔금은 신청서 제출 후 30일이내 / 신청마감일(2024년 12월 31일)까지 납입해야한다.<br>
                                            단, 2025년 1월 1일 이후 참가신청 시에는 신청과 동시에 참가비를 100% 완납하여야 한다.<br><br>

                                            <div class="form_pri_titt">제 4 조 (전시면적 및 위치 배정)</div>
                                            1. 주관자는 참가신청 접수순서, 전시품목의 특성, 신청면적 및 기타 합리적인 기준에 의거하여 전시 위치를 배정하며, 전시자는 이에 이의를 제기할 수 없다.<br>
                                            2. 주관자는 효율적인 전시관 구성을 위해서 기 배정된 부스의 위치, 규모 등을 변경할 수 있으며, 전시자는 불가항력적인 경우를 제외하고 주관자의 요구에 협조하여야 한다.<br>
                                            3. 전시자는 주관자의 사전 승낙 없이 배정받은 전시면적의 일부 또는 전부를 타 전시자에게 일체 전대 또는 교환할 수 없다.<br><br>

                                            <div class="form_pri_titt">제 5 조 (장치 및 전시품 진열)</div>
                                            1. 전시자는 배정된 전시면적 안에서 지정기간 내에 장치 및 전시품 반입, 진열을 완료하여야 한다. 사용시간 외 추가작업이 필요한 때에는 시간 외 사용료(임대료)를 납부하여야 한다.(시간당 1백만원, VAT별도)<br>
                                            2. 모든 장치공사는 KINTEX에 등록된 장치업체 중에서 선정하여야 한다.<br><br>

                                            <div class="form_pri_titt">제 6 조 (전시품 및 장치물 반출)</div>
                                            1. 전시자는 지정기간 내에 모든 전시품 및 장치물을 반출하여야 하며, 반출을 지연할 경우 주관자가 부담하게 될 제반 비용을 부담하여야 한다.<br>
                                            2. 반입ㆍ반출 기간에는 상하차 작업 외에는 전시품 상하차 지역 및 반입ㆍ반출 지역에 주ㆍ정차를 할 수 없으며 물품 운송이 끝난 즉시 이동하여 통행 흐름을 방해하지 않도록 해야 한다.<br>
                                            3. 전시품 반입ㆍ반출기간에 승용차 및 승합차는 화물주차장에 진입할 수 없다.<br><br>

                                            <div class="form_pri_titt">제 7 조 (전시실 관리)</div>
                                            1. 전시자는 참가신청서에 명시한 전시품을 전시하고 상주요원을 배치하여 자사 부스(Booth) 관리에 만전을 기하여야 한다. 전시자의 사유로 인해 전시품 전시 및 상주요원 배치가 불가능한 경우 주관자에게 서면으로 통보하며, 주관자는 배정된 부스신청을 취소할 수 있다. 이 경우 참가비는 반환되지 않는다.<br>
                                            2. 전시자가 참가신청서에 명시한 전시품과 상이한 물품을 전시하거나 전시회 성격에 부합되지 않는 물품을 전시할 경우, 또는 주관자의 허가 없이 현장 직매행위를 하는 경우 주관자는 즉시 전시 중지, 철거 또는 반출을 명할 수 있다. 이 경우 참가비는 반환되지 아니하며, 전시자는 이에 따른 배상을 청구할 수 없다.<br>
                                            3. 주관자는 필요한 경우, 특정인의 전시장 출입을 제한할 수 있다.<br>
                                            4. 전시자는 전시실의 바닥, 천정, 기둥, 벽면 등에 페인트칠, 못질 등의 원상변경을 할 수 없으며, 전시실의 손상에 대하여 원상 복구 등 주관자의 손해를 배상하여야 한다.<br><br>

                                            <div class="form_pri_titt">제 8 조 (안전관리)</div>
                                            1. 전시자는 개장시간 이전에 전시품 및 부스 환경을 확인하여 안전한 행사 운영이 이루어질 수 있도록 하고 폐장 시 모든 안전 요소를 점검ㆍ관리하여야 한다.<br>
                                            2. 주관자는 안전한 행사환경 조성을 위해 장치, 행사, 철거기간에 경비용역 서비스를 제공한다. 이때, 전시자 및 기타 용역업체는 경비요원의 지시에 따라야 한다.<br>
                                            3. 필요시 전시자는 인명ㆍ재산피해에 대비하여 보험에 가입하는 등 대책을 강구해야 한다.<br>
                                            4. 전시장에서 발생한 모든 사고 및 전시품ㆍ제반장비ㆍ시설의 최종적인 보호 및 분실에 대한 책임은 전시자에 있으며, 상황 발생 시 즉시 사무국에 통보하고 사무국을 면책하여야 한다.<br>
                                            5. 전시자는 전시장 내 모든 시설물을 보호하여야 하며, 전시자의 과실(전시자 발주 용역사 포함)로 인한 오염ㆍ파손ㆍ손상ㆍ훼손 등 발생 시 전시장 규정에 맞추어 원상복구의 책임을 진다.<br>
                                            6. 전시자는 전시자가 지정한 협력업체에게 킨텍스의 규정을 사전 공지하여야 하며, 행사 분야별 현장대리인 안전가이드를 취합하여 사무국이 지정한 기간 내에 사무국에 제출하여야 한다. 이때 협력업체 관리 책임은 전시자에 있으며 협력업체가 규정을 준수하지 않아 발생하는 모든 책임은 전시자에게 있다.<br>
                                            7. 전시품의 실연(實演)이 이루어지는 장소 주변에는 적절한 안전보호시설을 설치하여야 한다.<br>
                                            8. 폭발물, 가연성 물질 등 위험물의 전시장 내 반입은 금지한다. 다만, 전시품 가동에 반드시 필요한 경우, 『위험물 사용 승인신청서』를 사무국이 지정한 기간 내에 사무국에 제출하여야 한다.<br>
                                            9. 전기ㆍ가스ㆍ압축공기ㆍ급배수 사용 시 안전사고의 예방을 위하여 수시로 해당 용역업체 책임자가 부스 내 설비와 시운전 상황을 점검하도록 해야 하며, 매일 행사 종료 후에는 적절한 안전 조치가 되었는지 확인 후 퇴장하여야 한다. 또한, 불량 소재가 발견될 때에는 즉시 보수를 지시하고 사무국에 알리는 등 적절한 조치를 취하여야 한다.<br><br>

                                            <div class="form_pri_titt">제 9 조 (전시장 경비, 위험부담 및 보험)</div>
                                            1. 주관자는 부스 설치, 전시기간 및 철거기간 동안 경비용역 업체와 계약을 체결하여 전시장 출입통제 및 질서유지를 목적으로 경비 서비스를 제공한다. 전시품 및 기타 물품에 대한 최종적인 책임은 해당 참가기업에 있으며, 도난, 파손 등에 대한 보상을 청구할 수 없다. 따라서, 참가기업은 부스 설치, 전시기간 및 철거기간 동안 자사 전시품 및 기타 물품에 대한 손해, 손실 등에 대하여 보험가입 등 대책을 강구해야 하며, 부스 및 전시품 관리에 만전을 기해야 한다.<br>
                                            2. 주관자는 사무국의 귀책사유가 아닌, 천재지변 등의 불가항력이나 제3자에 의한 방화, 절도, 파손 등으로 인한 모든 피해에 대해서 책임을 지지 않는다.<br>
                                            3. 참가기업의 전시품 또는 서비스의 하자, 기타 전시회 관련 활동에 의하여 발생하는 하자담보책임, 손해배상책임 등 모든 민‧ 형사행정상 법적 책임은 전적으로 참가기업에게 있다. 만일 제3자가 참가기업의 명백한 사유로 전시로 인한 하자담보책임, 손해배상책임 등 민형사상 법적 책임을 사무국에 청구하는 경우 참가기업은 민원처리, 소송 기타 법적 대응 등 사무국을 면책시키는 조치를 취해야 한다. 또한, 위 제3자의 청구에 대한 대응을 위하여 사무국이 비용을 지출한 경우 참가기업은 즉시 그 비용을 사무국에 보상해 주어야 한다. 다만, 전시장 자체의 하자 등 전시장 및 사무국의 책임 있는 사유로 인한 경우가 명백한 경우는 예외로 한다.<br>
                                            4. 참가기업은 부스 설치, 전시기간 및 철거기간에 자사 전시와 관련하여 발생한 모든 제반 사고에 대해 책임을 지며, 사고 발생 시 즉시 사무국에 통보하고 사고대응 조치에 최선을 다해야 한다. 또한, 참가기업이 고의 또는 과실로 화재, 도난, 파손, 기타 사고를 발생케 하여 사무국, 전시장 및 제3자에게 손해를 입힌 때에는 참가기업이 배상책임을 져야한다.<br><br>

                                            <div class="form_pri_titt">제 10 조 (부스설치 제한 및 방화 규칙)</div>
                                            1. 모든 장치물의 높이는 전시규모, 위치 등을 고려하여 주관자가 지정한 높이 이상을 초과할 수 없다.<br>
                                            2. 전시장 내 장치물의 자재는 소방법규에 따라 불연 처리 되어야 한다. 주관자는 필요에 따라 전시자에게 화재방지와 관련하여 시정을 요구할 수 있으며, 전시자는 이러한 시정조치에 대하여 즉시 이행하여야 하며 전시자가 이에 불응할 경우 주관자는 전시 중단, 철거 및 반출을 명할 수 있다.<br><br>

                                            <div class="form_pri_titt">제 11 조 (계약해지)</div>
                                            1. 전시자가 배정된 전시부스의 전부 또는 일부의 사용을 거부하거나 참가비를 기한 내 납부하지 않을 경우 주관자는 일방적으로 계약을 해지할 수 있으며, 이 경우 기 납부된 참가비는 반환하지 않는다. 전시자가 주최자의 승인 없이 참가를 포기할 경우에도 참가비를 반환하지 않는다.<br>
                                            2. 전시자가 참가신청 후 참가를 취소하고자 할 경우 반드시 주관자에게 서면(공문)으로 취소 신청을 하여야 하며, 이 경우 다음에 정한 위약금 전액을 참가 취소 후 7일 이내에 주관자에게 지불하여야 한다.
                                            단, 기납입된 참가비는 동 위약금으로 차감하며, 부족 시 추가 납입하여야 하고 잉여 시 반환한다.<br>
                                            - ~2024.12.31. 까지 취소 시: 총 참가비의 50%를 위약금으로 주관자에게 납부<br>
                                            - 2025.1.1.~2025.1.31. 내 취소 시: 총 참가비의 80%를 위약금으로 주관자에게 납부<br>
                                            - 2025.2.1. 이후 취소 시: 총 참가비의 100%를 위약금으로 주관자에게 납부<br><br>

                                            <div class="form_pri_titt">제 12 조 (전시회 취소 또는 변경)</div>
                                            1. 주관자가 행정명령 또는 기타 내부적인 사유로 전시회 개최를 취소하는 경우 이미 납입된 참가비 전액을 전시자에게 반환한다.<br>
                                            2. 전시기간 중 행정명령에 의해 전시장이 폐쇄될 경우, 전시회를 개최할 수 없게됨에 따라 전시일(3일)에 대한 참가비를 일할 계산하여 환불한다.<br>
                                            3. 다만, 1항의 경우를 제외한 불가항력이나 주관사의 귀책사유가 아닌 특별한 사정으로 전시회의 취소, 개최일의 변경 또는 축소의 경우에는 이를 반환하지 않는다. 이 경우 전시자는 주관자에게 보상을 청구할 수 없다.<br><br>

                                            <div class="form_pri_titt">제 13 조 (전시회 만족도 설문조사)</div>
                                            1. 주관자는 경기국제보트쇼 운영과 해양레저산업 발전을 위하여 참가자에게 만족도 설문조사 및 실태 설문조사를 진행한다.<br>
                                            2. 주관자는 객관적인 설문 및 평가를 위하여 전문 리서치 기관을 이용하여 참가자에게 설문을 할 수 있다.<br><br>

                                            <div class="form_pri_titt">제 14 조 (참가자 매뉴얼 및 보충규정 준수)</div>
                                            1. 주관자는 전시업무 수행에 필요한 참가자 매뉴얼을 참가자에게 제공한다.<br>
                                            2. 주관자는 필요 시 본 규정 이외에도 보충규정을 제정할 수 있으며, 참가자는 본 규정 뿐만 아니라 보충규정을 준수하여야 한다.<br><br>

                                            <div class="form_pri_titt">제 15 조 (규정의 해석 및 분쟁해결)</div>
                                            1. 본 규정의 해석에 관하여 주관자와 전시자 간의 이견이 있을 경우 주관자의 해석과 결정에 따른다.<br>
                                            2. 본 전시회에 관하여 발생하는 권리 및 의무에 대한 분쟁은 서울소재 대한상사중재원의 중재판정에 따른다.
                                        </div>
                                        <div class="form_pri_agree">
                                            <label><input type="radio" name="agree1" value="Y" <c:if test="${not empty info}">checked</c:if> >동의함</label>
                                            <label><input type="radio" name="agree1" value="N">동의하지 않음</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- //약관 -->

                                <!-- 약관 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">개인정보 취급방침</div>
                                    </div>
                                    <div class="form_pri">
                                        <div class="form_pri_box">
                                            <div class="form_pri_titt">킨텍스 개인정보 처리방침</div>
                                            킨텍스(이하 ‘회사’라 한다)는 개인정보 보호법 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리지침을 수립․공개합니다.<br><br>

                                            <div class="form_pri_titt">제 1 조 (개인정보의 처리목적)</div>
                                            회사는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 개인정보 보호법 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.<br>
                                            1. 홈페이지 회원 가입 및 관리<br>
                                            회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별․인증, 회원자격 유지․관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 만 14세 미만 아동의 개인정보 처리시 법정대리인의 동의여부 확인, 각종 고지․통지, 고충처리 등을 목적으로 개인정보를 처리합니다.<br>
                                            2. 재화 또는 서비스 제공<br>
                                            전시/컨벤션 서비스 제공(부스참가, 전시회 입장 등), 콘텐츠 제공, 맞춤서비스 제공, 본인인증, 연령인증, 비용결제․정산등을 목적으로 개인정보를 처리합니다.<br>
                                            3. 고충처리<br>
                                            민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락․통지, 처리결과 통보 등의 목적으로 개인정보를 처리합니다.<br>
                                            4. 마케팅 및 광고에의 활용<br>
                                            신규 서비스 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 , 인구통계학적 특성에 따른 서비스 제공 및 광고등을 목적으로 개인정보를 처리합니다.<br>
                                            또한 재화나 서비스의 홍보 또는 판매 권유 등을 위하여 해당 개인정보를 이용하여 정보주체에게 연락할 수 있습니다.<br>
                                            5. 개인정보 수집항목 및 현황<br>
                                            - 개인정보 수집항목 : 로그인ID, 이름, 전화번호, 이메일, 비밀번호, 연령, 성별,출생년도, 지역, 직장명(단체명), 설문조사결과, 주소<br>
                                            - 수집방법 : 홈페이지, 킨텍스 앱, 주관전시 홈페이지 사전등록, 서면양식, 온,오프라인 이벤트 행사<br>
                                            - 보유근거 : 회원가입시 개인정보처리방침 동의 (개인정보 마케팅활용 : 5년)<br><br>

                                            <div class="form_pri_titt">제 2 조 (개인정보의 처리 및 보유기간)</div>
                                            1. 회사는 법령에 따른 개인정보 보유․이용기간 또는 정보주체로부터 개인정보를 수집시에 동의받은 개인정보 보유․이용기간 내에서 개인정보를 처리․보유합니다.<br>
                                            2. 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.<br>
                                            1) 홈페이지 회원 가입 및 관리 : 홈페이지 탈퇴시까지<br>
                                            다만, 다음의 사유에 해당하는 경우에는 해당 사유 종료시까지<br>
                                            - 관계 법령 위반에 따른 수사․조사 등이 진행중인 경우에는 해당 수사․조사 종료시까지<br>
                                            2) 재화 또는 서비스 제공 : 재화․서비스 공급완료 및 요금결제․정산 완료시까지<br>
                                            다만, 다음의 사유에 해당하는 경우에는 해당 기간 종료시까지<br>
                                            - 「전자상거래 등에서의 소비자 보호에 관한 법률」에 따른 표시․광고, 계약내용 및 이행 등 거래 에 관한 기록<br>
                                            • 표시․광고에 관한 기록 : 6개월<br>
                                            • 계약 또는 청약철회, 대금결제, 재화 등의 공급기록 : 5년<br>
                                            • 소비자 불만 또는 분쟁처리에 관한 기록 : 3년<br><br>

                                            <div class="form_pri_titt">제 3 조 (개인정보의 제3자 제공)</div>
                                            1. 회사는 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조에 해당하는 경우에 개인정보를 제3자에게 제공할 수 있습니다.<br>
                                            2. 회사는 다음과 같이 개인정보를 제3자에게 제공할 수 있습니다.<br>
                                            - 개인정보를 제공받는 자 : 킨텍스 앱의 무료/유료 서비스를 사용하는 전시회 /컨벤션 행사 주최자<br>
                                            • 제공받는 자의 개인정보 이용목적 : 전시회/컨벤션의 무료 서비스, 유료 결제 서비스, 이벤트 공동개최, 마케팅, 광고 등<br>
                                            • 제공하는 개인정보 항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도, 직장명(단체), 추천인(ID또는 이름), 전시회/컨벤션 별 설문조사 항목 정보<br>
                                            - 개인정보를 제공받는 자 : 전시산업진흥회<br>
                                            • 제공받는 자의 개인정보 이용목적 : 전시산업진흥회 전시회 인증을 위한 정보 제공<br>
                                            • 제공하는 개인정보 항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도, 직장명(단체), 추천인(ID또는 이름), 전시회/컨벤션 별 설문조사 항목 정보<br><br>

                                            <div class="form_pri_titt">제 4 조 (개인정보처리의 위탁)</div>
                                            1. 회사는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.<br>
                                            - (위탁자) : 킨텍스 (수탁자) : 에스브이엠플래닛<br>
                                            - 위탁하는 업무의 내용 : 경기국제보트쇼 홈페이지 관리<br>
                                            - 위탁기간 : 위탁계약 종료 시<br>
                                            2. 회사는 위탁계약 체결시 개인정보 보호법 제②5조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.<br>
                                            3. 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.<br><br>

                                            <div class="form_pri_titt">제 5 조 (정보주체의 권리․의무 및 행사방법)</div>
                                            1. 정보주체는 회사에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.<br>
                                            - 개인정보 열람요구<br>
                                            - 오류 등이 있을 경우 정정 요구<br>
                                            - 삭제요구<br>
                                            - 처리정지 요구<br>
                                            2. 제1항에 따른 권리 행사는 회사에 대해 서면, 전화, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 회사는 이에 대해 지체없이 조치하겠습니다.<br>
                                            3. 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 회사는 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.<br>
                                            4. 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.<br>
                                            5. 정보주체는 개인정보 보호법 등 관계법령을 위반하여 회사가 처리하고 있는 정보주체 본인이나 타인의 개인정보 및 사생활을 침해하여서는 아니됩니다.<br><br>

                                            <div class="form_pri_titt">제 6 조 (처리하는 개인정보 항목)</div>
                                            회사는 다음의 개인정보 항목을 처리하고 있습니다.<br>
                                            1. 홈페이지/앱 회원 가입 및 관리<br>
                                            - 필수항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도<br>
                                            - 선택항목 : 직장인(단체), 추천인(ID또는이름), 설문조사<br>
                                            2. 서비스 제공<br>
                                            - 필수항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도<br>
                                            - 선택항목 : 직장인(단체), 추천인(ID또는이름), 설문조사, 과거 구매내역<br>
                                            3. 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로 생성되어 수집될 수 있습니다.<br>
                                            - IP주소, 쿠키, MAC주소, 서비스 이용기록, 방문기록, 불량 이용기록 등<br><br>

                                            <div class="form_pri_titt">제 7 조 (개인정보의 파기)</div>
                                            1. 회사는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.<br>
                                            2. 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.<br>
                                            3. 개인정보 파기의 절차 및 방법은 다음과 같습니다.<br>
                                            - 파기절차 : 회사는 파기 사유가 발생한 개인정보를 즉시 파기합니다.<br>
                                            - 파기방법 : 회사는 전자적 파일 형태로 기록․저장된 개인정보는 기록을 재생할 수 없도록 DB데이터 삭제 등의 방법을 이용하여 파기하며, 종이 문서에 기록․저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.<br><br>

                                            <div class="form_pri_titt">제 8 조 (개인정보의 안전성 확보조치)</div>
                                            회사는 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.<br>
                                            1. 관리적 조치 : 내부관리계획 수립․시행, 정기적 직원 교육 등<br>
                                            2. 기술적 조치 : 개인정보처리시스템 등의 접근권한 관리, 접근통제, 고유식별정보 등의 암호화, 보안프로그램 설치<br>
                                            3. 물리적 조치 : 전산실, 자료보관실 등의 접근통제<br><br>

                                            <div class="form_pri_titt">제 9 조 (개인정보 자동 수집 장치의 설치∙운영 및 거부에 관한 사항)</div>
                                            1. 회사는 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용할 수 있습니다.<br>
                                            2. 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자들의 PC 컴퓨터내의 하드디스크에 저장되기도 합니다.<br>
                                            - 쿠키의 사용목적: 이용자가 방문한 각 서비스와 웹 사이트들에 대한 방문 및 이용형태, 인기 검색어, 보안접속 여부, 등을 파악하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.<br>
                                            - 쿠키의 설치∙운영 및 거부 : 웹브라우저 상단의 도구>인터넷 옵션>개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부 할 수 있습니다.<br>
                                            - 쿠키 저장을 거부할 경우 맞춤형 서비스 이용에 어려움이 발생할 수 있습니다.<br><br>

                                            <div class="form_pri_titt">제 10 조 (개인정보 보호책임자)</div>
                                            1. 회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.<br><br>
                                            ▶ 개인정보 보호책임자 : 사업부사장<br><br>
                                            ▶ 개인정보 보호 관리자<br>
                                            부서명 : 전시사업3팀<br>
                                            관리자 : 구기훈 팀장<br>
                                            연락처 : 031-995-8771 / ghkoo@kintex.com<br><br>

                                            ▶ 개인정보 보호 담당자<br>
                                            부서명 : 전시사업3팀<br>
                                            담당자 : 송욱선 과장<br>
                                            연락처 : 031-995-8779 / wooksun5150@kintex.com<br><br>

                                            2. 정보주체는 회사의 서비스(또는 사업)을 이용하면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 담당부서로 문의하실 수 있습니다. 회사는 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.<br><br>

                                            <div class="form_pri_titt">제 11 조 (개인정보 열람청구)</div>
                                            정보주체는 개인정보 보호법 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다. 회사는 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.<br><br>
                                            ▶ 개인정보 열람청구 접수․처리 부서 : 전시사업3팀<br><br>

                                            <div class="form_pri_titt">제 12 조 (권익침해 구제방법)</div>
                                            정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.<br>
                                            &lt;아래의 기관은 회사와는 별개의 기관으로서, 회사의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다&gt;<br><br>

                                            ▶ 개인정보 침해신고센터 (한국인터넷진흥원 운영)<br>
                                            - 소관업무 : 개인정보 침해사실 신고, 상담 신청<br>
                                            - 홈페이지 : privacy.kisa.or.kr<br>
                                            - 전화 : (국번없이) 118<br>
                                            - 주소 : (58324) 전남 나주시 진흥길 9(빛가람동 301-2) 3층 개인정보침해신고센터<br><br>

                                            ▶ 개인정보 분쟁조정위원회<br>
                                            - 소관업무 : 개인정보 분쟁조정신청, 집단분쟁조정 (민사적 해결)<br>
                                            - 홈페이지 : www.kopico.go.kr<br>
                                            - 전화 : (국번없이) 1833-6972<br>
                                            - 주소 : (03171)서울특별시 종로구 세종대로 209 정부서울청사 4층<br><br>

                                            ▶ 대검찰청 사이버범죄수사단 : 02-3480-3573 (www.spo.go.kr)<br><br>

                                            ▶ 경찰청 사이버안전국 : 182 (http://cyberbureau.police.go.kr)<br><br>


                                            <div class="form_pri_titt">제 13 조 (영상정보처리기기 설치․운영)</div>
                                            1. 킨텍스는 아래와 같이 영상정보처리기기를 설치․운영하고 있습니다.<br>
                                            - 영상정보처리기기 설치근거․목적 : 킨텍스의 시설안전․화재예방<br>
                                            - 설치 대수, 설치 위치, 촬영 범위 : 전시장 로비․전시실 등 주요시설물에 설치, 촬영범위는 주요시설물의 전 공간을 촬영<br>
                                            - 관리책임자, 담당부서 및 영상정보에 대한 접근권한자 : 시설운영팀<br>
                                            - 영상정보 촬영시간, 보관기간, 보관장소, 처리방법<br>
                                            • 촬영시간 : 24시간 촬영<br>
                                            • 보관장소 및 처리방법 : 영상정보처리기기 통제실에 보관․처리<br>
                                            - 영상정보 확인 방법 및 장소 : 시설운영팀<br>
                                            - 정보주체의 영상정보 열람 등 요구에 대한 조치 : 개인영상정보 열람․존재확인 청구서로 신청하여야 하며, 정보주체 자신이 촬영된 경우 또는 명백히 정보주체의 생명․신체․재산 이익을 위해 필요한 경우에 한해 열람을 허용함<br>
                                            - 영상정보 보호를 위한 기술적․관리적․물리적 조치 : 내부관리계획 수립, 접근통제 및 접근권한 제한, 영상정보의 안전한 저장․전송기술 적용, 처리기록 보관 및 위․변조 방지조치, 보관시설 마련 및 잠금장치 설치 등<br><br>

                                            <div class="form_pri_titt">제 14 조 (영상정보(사진 및 동영상 등) 촬영에 관한 동의)</div>
                                            1) 박람회 현장에서 사진 촬영 및 영상 녹화가 진행됩니다.<br>
                                            2) 촬영목적<br>
                                            ⓵ 본 행사 및 추후 개최될 전시회와 컨퍼런스 홍보<br>
                                            ⓶ 박람회 관련 보도자료 및 문건 첨부<br>
                                            3) 촬영된 자료는 다른 목적으로 사용되지 않습니다.
                                        </div>
                                        <div class="form_pri_agree">
                                            <label><input type="radio" name="agree2" value="Y" <c:if test="${not empty info}">checked</c:if> >동의함</label>
                                            <label><input type="radio" name="agree2" value="N">동의하지 않음</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- //약관 -->

                                <form id="exhibitor_apply_form" method="post" onsubmit="return false;" enctype="multipart/form-data">
                                    <!-- 회원계정정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">회원 계정 정보</div>
                                            <div class="small">아이디(사업자등록번호) 및 비밀번호는 참가신청 후 부대시설 및 기타신청 등록 및 수정할 때 꼭 필요합니다.</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item req">
                                                    <p>아이디</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="id" name="id" value="${info.id}" maxlength="10" placeholder="사업자등록번호를 입력해주세요. (하이픈 제외)" class="w50 onlyNum" onkeyup="f_id_license_num_set(this)" autocomplete="false">
                                                    <input type="button" onclick="f_id_duplicate_check(this)" style="margin-left: 10px;" value="중복검사">
                                                    <c:if test="${not empty info}"><input type="hidden" id="idCheck" value="true"></c:if>
                                                    <c:if test="${empty info}">
                                                        <div class="cmnt" style="color: #AD1D1D;">아이디 중복 검사 버튼을 클릭해주세요.</div>
                                                        <input type="hidden" id="idCheck" value="false">
                                                    </c:if>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>비밀번호</p>
                                                </div>
                                                <div class="input">
                                                    <input type="password" id="password" name="password" maxlength="16" value="${info.password}" onchange="f_pw_status_change(this)" placeholder="비밀번호 입력" class="w50" autocomplete="new-password">
                                                    <input type="button" onclick="f_pw_check(this)" style="margin-left: 10px;" value="비밀번호 유효성 검사">
                                                    <div class="cmnt">8 ~ 16자의 영문, 숫자, 특수문자를 사용해 주세요.</div>
                                                    <c:if test="${not empty info}"><input type="hidden" id="pwCheck" value="true"></c:if>
                                                    <c:if test="${empty info}">
                                                        <div class="cmnt" style="color: #AD1D1D;">비밀번호 유효성을 검사해주세요.</div>
                                                        <input type="hidden" id="pwCheck" value="false">
                                                    </c:if>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>비밀번호 확인</p>
                                                </div>
                                                <div class="input">
                                                    <input type="password" id="passwordCheck" maxlength="16" onkeyup="f_pw_confirm_check(this)" placeholder="비밀번호 확인" class="w50">
                                                    <c:if test="${not empty info}"><input type="hidden" id="pwConfirmCheck" value="true"></c:if>
                                                    <c:if test="${empty info}">
                                                        <div class="cmnt" style="color: #AD1D1D;">비밀번호를 다시 입력해주세요.</div>
                                                        <input type="hidden" id="pwConfirmCheck" value="false">
                                                    </c:if>
                                                </div>
                                            </li>
                                            <li class="form_in_tit">대표담당자</li>
                                            <li>
                                                <div class="item req">
                                                    <p>성명</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="name" name="name" value="${info.name}" placeholder="성명" class="w50">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>직위</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="position" name="position" value="${info.position}" placeholder="직위" class="w50">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>부서</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="depart" name="depart" value="${info.depart}" placeholder="부서" class="w50">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>이메일</p>
                                                </div>
                                                <div class="input email">
                                                    <input type="email" id="email_input1" name="email1" value="${fn:split(info.email,'@')[0]}" placeholder="이메일" class="email_input1">
                                                    <span>@</span>
                                                    <input type="email" id="email_input2" name="email2" value="${fn:split(info.email,'@')[1]}" class="email_input2" placeholder="직접입력">
                                                    <select id="email_select">
                                                        <c:set var="domain" value="${fn:split(info.email,'@')[1]}"/>
                                                        <option selected>직접입력</option>
                                                        <option value="daum.net" <c:if test="${domain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                        <option value="nate.com" <c:if test="${domain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                        <option value="hanmail.net" <c:if test="${domain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                        <option value="naver.com" <c:if test="${domain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                        <option value="hotmail.com" <c:if test="${domain eq 'hotmail.com'}">selected</c:if> >hotmail.com</option>
                                                        <option value="yahoo.co.kr" <c:if test="${domain eq 'yahoo.co.kr'}">selected</c:if> >yahoo.co.kr</option>
                                                        <option value="empal.com" <c:if test="${domain eq 'empal.com'}">selected</c:if> >empal.com</option>
                                                        <option value="korea.com" <c:if test="${domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                        <option value="hanmir.com" <c:if test="${domain eq 'hanmir.com'}">selected</c:if> >hanmir.com</option>
                                                        <option value="dreamwiz.com" <c:if test="${domain eq 'dreamwiz.com'}">selected</c:if> >dreamwiz.com</option>
                                                        <option value="orgio.net" <c:if test="${domain eq 'orgio.net'}">selected</c:if> >orgio.net</option>
                                                        <option value="korea.com" <c:if test="${domain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                        <option value="hitel.net" <c:if test="${domain eq 'hitel.net'}">selected</c:if> >hitel.net</option>
                                                    </select>
                                                    <div class="check mktCheck">
                                                        <p>E-mail 마케팅정보 수신동의</p>
                                                        <label><input type="radio" name="email_marketing_yn" value="Y" <c:if test="${info.emailMarketingYn eq 'Y' or empty info}">checked</c:if>>수신동의</label>
                                                        <label><input type="radio" name="email_marketing_yn" value="N" <c:if test="${info.emailMarketingYn eq 'N'}">checked</c:if>>동의 안함</label>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>전화번호</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" id="tel" name="tel" value="${info.tel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>휴대전화</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" id="phone" name="phone" value="${info.phone}" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                        </ul>
                                    </div>

                                    <!-- 참가업체 정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">참가업체 정보</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item req">
                                                    <p>회사명</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="companyNameKo" name="companyNameKo" value="${info.companyNameKo}" placeholder="국문" class="w50">
                                                    <input type="text" id="companyNameEn" name="companyNameEn" value="${info.companyNameEn}" placeholder="영문" class="w50 onlyNumEng">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>본사 주소</p>
                                                </div>
                                                <div class="input address">
                                                    <div class="address_box">
                                                        <input type="text" id="companyAddress" name="companyAddress" value="${info.companyAddress}" class="w50" style="margin-right: 10px;" placeholder="본사 주소">
                                                        <input type="button" onclick="execDaumPostcode('companyAddress','companyAddressDetail')" value="주소 검색"><br>
                                                    </div>
                                                    <div class="address_box" style="margin-top: 10px;">
                                                        <input type="text" id="companyAddressDetail" name="companyAddressDetail" value="${info.companyAddressDetail}" placeholder="본사 상세 주소" class="w50">
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>공장 주소</p>
                                                </div>
                                                <div class="input address">
                                                    <div class="address_box">
                                                        <input type="text" id="factoryAddress" name="factoryAddress" value="${info.factoryAddress}" class="w50" style="margin-right: 10px;" placeholder="공장 주소">
                                                        <input type="button" onclick="execDaumPostcode('factoryAddress','factoryAddressDetail')" value="주소 검색"><br>
                                                    </div>
                                                    <div class="address_box" style="margin-top: 10px;">
                                                        <input type="text" id="factoryAddressDetail" name="factoryAddressDetail" value="${info.factoryAddressDetail}" placeholder="공장 상세 주소" class="w50">
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>대표자</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="companyCeo" name="companyCeo" value="${info.companyCeo}" placeholder="대표자명">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>전화</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" id="companyTel" name="companyTel" value="${info.companyTel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>홈페이지</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="companyHomepage" name="companyHomepage" value="${info.companyHomepage}" placeholder="https://">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Fax</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" id="companyFax" name="companyFax" value="${info.companyFax}" class="onlyTel" maxlength="25" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>산업 분류</p>
                                                </div>
                                                <div class="input">
                                                    <select name="industryPart" id="industryPart">
                                                        <option value="요트/보트 제조" <c:if test="${info.industryPart eq '요트/보트 제조'}">selected</c:if> >요트/보트 제조</option>
                                                        <option value="요트/보트 유통(수입 판매)" <c:if test="${info.industryPart eq '요트/보트 유통(수입 판매)'}">selected</c:if> >요트/보트 유통(수입 판매)</option>
                                                        <option value="해양 부품(엔진 및 구성품) 제조" <c:if test="${info.industryPart eq '해양 부품(엔진 및 구성품) 제조'}">selected</c:if> >해양 부품(엔진 및 구성품) 제조</option>
                                                        <option value="해양 부품(엔진 및 구성품) 판매" <c:if test="${info.industryPart eq '해양 부품(엔진 및 구성품) 판매'}">selected</c:if> >해양 부품(엔진 및 구성품) 판매</option>
                                                        <option value="해양 관련 기자재 제조 및 판매" <c:if test="${info.industryPart eq '해양 관련 기자재 제조 및 판매'}">selected</c:if> >해양 관련 기자재 제조 및 판매</option>
                                                        <option value="수상레저 용품 제조 및 판매" <c:if test="${info.industryPart eq '수상레저 용품 제조 및 판매'}">selected</c:if> >수상레저 용품 제조 및 판매</option>
                                                        <option value="수중레저 용품 제조 및 판매" <c:if test="${info.industryPart eq '수중레저 용품 제조 및 판매'}">selected</c:if> >수중레저 용품 제조 및 판매</option>
                                                        <option value="아웃도어 용품 제조 및 판매" <c:if test="${info.industryPart eq '아웃도어 용품 제조 및 판매'}">selected</c:if> >아웃도어 용품 제조 및 판매</option>
                                                        <option value="마리나산업" <c:if test="${info.industryPart eq '마리나산업'}">selected</c:if> >마리나산업</option>
                                                        <option value="해양관광산업" <c:if test="${info.industryPart eq '해양관광산업'}">selected</c:if> >해양관광산업</option>
                                                        <option value="교육/면허" <c:if test="${info.industryPart eq '교육/면허'}">selected</c:if> >교육/면허</option>
                                                        <option value="수리" <c:if test="${info.industryPart eq '수리'}">selected</c:if> >수리</option>
                                                        <option value="기타" <c:if test="${info.industryPart eq '기타'}">selected</c:if> >기타</option>
                                                    </select>
                                                    <input type="text" id="industryPartEtc" name="industryPartEtc" value="${info.industryPartEtc}" placeholder="직접 입력" disabled>
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>임직원 수</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" id="employeeCnt" name="employeeCnt" value="${info.employeeCnt}" class="onlyNum" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item req">
                                                    <p>사업자등록증</p>
                                                </div>
                                                <div class="input file_box d-flex align-items-center">
                                                    <input type="text" id="companyLicense" name="companyLicense" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" id="companyLicenseFile" class="upload_hidden" accept=".png, .jpg, .jpeg, .pdf">
                                                    <label for="companyLicenseFile">파일선택</label>
                                                    <div class="cmnt">PDF, JPG, PNG형식, 10MB 이하만 가능합니다.</div>
                                                </div>
                                            </li>

                                            <c:if test="${companyLicenseFile ne null and not empty companyLicenseFile}">
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>사업자등록증 파일</p>
                                                    </div>
                                                    <div class="input file_box">
                                                        <ul>
                                                            <li class="companyLicenseFile_li" style="align-items: center;">
                                                                <c:set var="companyLicenseFileSrc" value="${fn:replace(companyLicenseFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                <c:if test="${not fn:contains(companyLicenseFileSrc, '.ai') and not fn:contains(companyLicenseFileSrc, '.pdf')}">
                                                                    <img src="${companyLicenseFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                                </c:if>
                                                                <%--<a href="/file/download.do?path=exhibitor/company/${companyLicenseFile.folderPath}&fileName=${companyLicenseFile.fullFileName}">${companyLicenseFile.fileName}</a>--%>
                                                                <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${companyLicenseFile.folderPath}', '${companyLicenseFile.fullFileName}')">${companyLicenseFile.fileName}</a>
                                                                <input type="hidden" name="companyLicenseUploadFile" id="${companyLicenseFile.id}" value="${companyLicenseFile.fullFilePath}">
                                                                <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${companyLicenseFile.id}')">X</button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </li>
                                            </c:if>

                                            <li class="w50">
                                                <div class="item req">
                                                    <p>사업자등록번호</p>
                                                </div>
                                                <div class="input" style="display: flex; align-items: center;">
                                                    <input type="text" id="companyLicenseNum" name="companyLicenseNum" value="${info.companyLicenseNum}" class="onlyNum" placeholder="아이디와 동일한 값이 자동 입력됩니다." readonly>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>기참가연도</p>
                                                </div>
                                                <div class="input check">
                                                    <label><input type="checkbox" name="prePartYear" value="first" <c:if test="${fn:contains(info.prePartYear, 'first')}">checked</c:if>/>첫 참가</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2014" <c:if test="${fn:contains(info.prePartYear, '2014')}">checked</c:if>/>2014</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2015" <c:if test="${fn:contains(info.prePartYear, '2015')}">checked</c:if>/>2015</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2016" <c:if test="${fn:contains(info.prePartYear, '2016')}">checked</c:if>/>2016</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2017" <c:if test="${fn:contains(info.prePartYear, '2017')}">checked</c:if>/>2017</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2018" <c:if test="${fn:contains(info.prePartYear, '2018')}">checked</c:if>/>2018</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2019" <c:if test="${fn:contains(info.prePartYear, '2019')}">checked</c:if>/>2019</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2020" <c:if test="${fn:contains(info.prePartYear, '2020')}">checked</c:if>/>2020</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2021" <c:if test="${fn:contains(info.prePartYear, '2021')}">checked</c:if>/>2021</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2022" <c:if test="${fn:contains(info.prePartYear, '2022')}">checked</c:if>/>2022</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2023" <c:if test="${fn:contains(info.prePartYear, '2023')}">checked</c:if>/>2023</label>
                                                    <label><input type="checkbox" name="prePartYear" value="2024" <c:if test="${fn:contains(info.prePartYear, '2024')}">checked</c:if>/>2024</label>
                                                </div>
                                            </li>
                                            <%--<li>
                                                <div class="item">
                                                    <p>참가의향</p>
                                                </div>
                                                <div class="input check">
                                                    <label><input type="radio" name="memberKm" checked>지속적으로 보트쇼에 참가할 의향 있음</label>
                                                    <label><input type="radio" name="memberKm">없음</label>
                                                </div>
                                            </li>--%>
                                            <li>
                                                <div class="item req">
                                                    <p>한국해양레저산업협회 회원사 여부</p>
                                                </div>
                                                <div class="input check">
                                                    <label><input type="radio" name="memberCompanyYn" value="Y" <c:if test="${info.memberCompanyYn eq 'Y'}">checked</c:if> />예</label>
                                                    <label><input type="radio" name="memberCompanyYn" value="N" <c:if test="${info.memberCompanyYn eq 'N' or empty info}">checked</c:if> />아니요</label>
                                                </div>
                                            </li>
                                            <li class="form_in_tit">SNS (주소기입)</li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>블로그</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="snsBlog" name="snsBlog" value="${info.snsBlog}" placeholder="https://">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>페이스북</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="snsFacebook" name="snsFacebook" value="${info.snsFacebook}" placeholder="https://">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>인스타그램</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="snsInstagram" name="snsInstagram" value="${info.snsInstagram}" placeholder="https://">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>기타</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="snsEtc" name="snsEtc" value="${info.snsEtc}" placeholder="https://">
                                                </div>
                                            </li>
                                        </ul>
                                    </div>

                                    <!-- 부담당자 정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">부담당자 정보</div>
                                            <div class="small">최대 3명까지 등록 가능</div>
                                        </div>
                                        <c:if test="${empty chargeList}">
                                        <ul class="form_box managerInfoBox">
                                            <li class="form_in_tit">
                                                <input type="hidden" name="chargeSeq" value="">
                                                부담당자 #<span class="managerInfoNum">1</span>
                                                <span class="del_btn managerInfoDel">삭제</span>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>성명</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="chargePersonName" class="w50" placeholder="부담당자명">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>직위</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="chargePersonPosition" class="w50" placeholder="직위">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>부서</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="chargePersonDepart" class="w50" placeholder="부서">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>전화번호</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" name="chargePersonTel" class="onlyTel" maxlength="13" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>휴대전화</p>
                                                </div>
                                                <div class="input">
                                                    <input type="tel" name="chargePersonPhone" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>이메일</p>
                                                </div>
                                                <div class="input email">
                                                    <input type="email" name="chargePersonEmail" placeholder="이메일" class="email_input1">
                                                    <span>@</span>
                                                    <input type="email" name="chargePersonDomain" placeholder="직접입력" class="email_input2">
                                                    <select name="chargePersonEmail_sel">
                                                        <option selected>직접입력</option>
                                                        <option value="daum.net">daum.net</option>
                                                        <option value="nate.com">nate.com</option>
                                                        <option value="hanmail.net">hanmail.net</option>
                                                        <option value="naver.com">naver.com</option>
                                                        <option value="hotmail.com">hotmail.com</option>
                                                        <option value="yahoo.co.kr">yahoo.co.kr</option>
                                                        <option value="empal.com">empal.com</option>
                                                        <option value="korea.com">korea.com</option>
                                                        <option value="hanmir.com">hanmir.com</option>
                                                        <option value="dreamwiz.com">dreamwiz.com</option>
                                                        <option value="orgio.net">orgio.net</option>
                                                        <option value="korea.com">korea.com</option>
                                                        <option value="hitel.net">hitel.net</option>
                                                    </select>
                                                </div>
                                            </li>
                                        </ul>
                                        </c:if>

                                        <c:if test="${not empty chargeList}">
                                        <c:forEach var="charge" items="${chargeList}" begin="0" end="${chargeList.size()}" step="1" varStatus="status">
                                            <ul class="form_box managerInfoBox">
                                                <li class="form_in_tit">
                                                    <input type="hidden" name="chargeSeq" value="${charge.seq}">
                                                    부담당자 #<span class="managerInfoNum">${status.index + 1}</span>
                                                    <span class="del_btn managerInfoDel">삭제</span>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>성명</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="chargePersonName" value="${charge.chargePersonName}" maxlength="50" class="w50" placeholder="부담당자명">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>직위</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="chargePersonPosition" value="${charge.chargePersonPosition}" class="w50" placeholder="직위">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>부서</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="chargePersonDepart" value="${charge.chargePersonDepart}" class="w50" placeholder="부서">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>전화번호</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="tel" name="chargePersonTel" value="${charge.chargePersonTel}" class="onlyTel" maxlength="13" placeholder="숫자만 입력해주세요.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>휴대전화</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="tel" name="chargePersonPhone" value="${charge.chargePersonPhone}" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="숫자만 입력해주세요.">
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>이메일</p>
                                                    </div>
                                                    <div class="input email">
                                                        <input type="email" name="chargePersonEmail" value="${fn:split(charge.chargePersonEmail,'@')[0]}" placeholder="이메일" class="email_input1">
                                                        <span>@</span>
                                                        <input type="email" name="chargePersonDomain" value="${fn:split(charge.chargePersonEmail,'@')[1]}" class="email_input2" placeholder="직접입력">
                                                        <select name="chargePersonEmail_sel">
                                                            <c:set var="chargePersonDomain" value="${fn:split(charge.chargePersonEmail,'@')[1]}"/>
                                                            <option selected>직접입력</option>
                                                            <option value="daum.net" <c:if test="${chargePersonDomain eq 'daum.net'}">selected</c:if> >daum.net</option>
                                                            <option value="nate.com" <c:if test="${chargePersonDomain eq 'nate.com'}">selected</c:if> >nate.com</option>
                                                            <option value="hanmail.net" <c:if test="${chargePersonDomain eq 'hanmail.net'}">selected</c:if> >hanmail.net</option>
                                                            <option value="naver.com" <c:if test="${chargePersonDomain eq 'naver.com'}">selected</c:if> >naver.com</option>
                                                            <option value="hotmail.com" <c:if test="${chargePersonDomain eq 'hotmail.com'}">selected</c:if> >hotmail.com</option>
                                                            <option value="yahoo.co.kr" <c:if test="${chargePersonDomain eq 'yahoo.co.kr'}">selected</c:if> >yahoo.co.kr</option>
                                                            <option value="empal.com" <c:if test="${chargePersonDomain eq 'empal.com'}">selected</c:if> >empal.com</option>
                                                            <option value="korea.com" <c:if test="${chargePersonDomain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                            <option value="hanmir.com" <c:if test="${chargePersonDomain eq 'hanmir.com'}">selected</c:if> >hanmir.com</option>
                                                            <option value="dreamwiz.com" <c:if test="${chargePersonDomain eq 'dreamwiz.com'}">selected</c:if> >dreamwiz.com</option>
                                                            <option value="orgio.net" <c:if test="${chargePersonDomain eq 'orgio.net'}">selected</c:if> >orgio.net</option>
                                                            <option value="korea.com" <c:if test="${chargePersonDomain eq 'korea.com'}">selected</c:if> >korea.com</option>
                                                            <option value="hitel.net" <c:if test="${chargePersonDomain eq 'hitel.net'}">selected</c:if> >hitel.net</option>
                                                        </select>
                                                    </div>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                        </c:if>

                                        <div class="formAddBtn"><span class="managerInfoAdd">추가</span></div>
                                    </div>

                                    <!-- 참가분야 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">참가분야</div>
                                            <div class="small">최대 3개 선택 가능</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item req">
                                                    <p>참가분야</p>
                                                </div>
                                                <div class="input check">
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="요트·보트전 (Yacht & Boat World)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '요트·보트전')}">checked</c:if>/>
                                                        요트·보트전 (Yacht & Boat World)
                                                    </label>
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="무동력보트전 (Paddler’s World)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '무동력보트전')}">checked</c:if>/>
                                                        무동력보트전 (Paddler’s World)
                                                    </label>
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="워크보트전 (Workboat World)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '워크보트전')}">checked</c:if>/>
                                                        워크보트전 (Workboat World)
                                                    </label>
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="해양레저관 (Marine Leisure)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '해양레저관')}">checked</c:if>/>
                                                        해양레저관 (Marine Leisure)
                                                    </label>
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="카라반쇼 (Caravan Show)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '카라반쇼')}">checked</c:if>/>
                                                        카라반쇼 (Caravan Show)
                                                    </label>
                                                    <%--<label>
                                                        <input type="checkbox" name="fieldPart" value="아라마리나 교육 및 체험 프로그램 (Aramarina Education and Experience Program)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '아라마리나 교육 및 체험 프로그램')}">checked</c:if>/>
                                                        아라마리나 교육 및 체험 프로그램 (Aramarina Education and Experience Program)
                                                    </label>--%>
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="해양부품·안전·마리나산업전 (Marine Equipment, Safety & Marina Industry Show)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '해양부품·안전·마리나산업전')}">checked</c:if>/>
                                                        해양부품·안전·마리나산업전 (Marine Equipment, Safety & Marina Industry Show)
                                                    </label>
                                                    <%--<label>
                                                        <input type="checkbox" name="fieldPart" value="친환경 특별전 (Eco Friendly Marine)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '친환경 특별전')}">checked</c:if>/>
                                                        친환경 특별전 (Eco Friendly Marine)
                                                    </label>--%>
                                                    <label>
                                                        <input type="checkbox" name="fieldPart" value="한국해양관광전 (Marine Tourism)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '한국해양관광전')}">checked</c:if>/>
                                                        한국해양관광전 (Marine Tourism)
                                                    </label>
                                                    <%--<label>
                                                        <input type="checkbox" name="fieldPart" value="보트정비관 (Boat Maintenance)" onclick="check_count(this);" <c:if test="${fn:contains(info.fieldPart, '보트정비관')}">checked</c:if>/>
                                                        보트정비관 (Boat Maintenance)
                                                    </label>--%>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>

                                    <!-- 전시정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">전시정보</div>
                                        </div>

                                        <c:if test="${empty displayList}">
                                        <ul class="form_box exhiInfoBox">
                                            <li class="form_in_tit">
                                                <input type="hidden" name="displayItemSeq" value="">
                                                전시정보 #<span class="exhiInfoNum">1</span>
                                                <span class="del_btn exhiInfoDel">삭제</span>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>전시품목</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="displayItem" placeholder="전시품목명">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>전시품목<br>브랜드명</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="displayBrand" placeholder="전시품목 브랜드명">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>실물 보트수<br>(단위:척)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="displayBoatCnt" class="onlyNum" placeholder="단위 제외, 숫자만 입력해 주세요.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>전시품소개</p>
                                                </div>
                                                <div class="input">
                                                    <textarea name="displayItemIntroKo" placeholder="국문">${info.displayItemIntroKo}</textarea>
                                                    <textarea name="displayItemIntroEn" placeholder="영문" class="onlyNumEng">${info.displayItemIntroEn}</textarea>
                                                </div>
                                            </li>
                                        </ul>
                                        </c:if>

                                        <c:if test="${not empty displayList}">
                                            <c:forEach var="display" items="${displayList}" begin="0" end="${displayList.size()}" step="1" varStatus="status">
                                                <ul class="form_box exhiInfoBox">
                                                    <li class="form_in_tit">
                                                        <input type="hidden" name="displayItemSeq" value="${display.seq}">
                                                        전시정보 #<span class="exhiInfoNum">${status.index + 1}</span>
                                                        <span class="del_btn exhiInfoDel">삭제</span>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>전시품목</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="displayItem" value="${display.displayItem}" placeholder="전시품목">
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>전시품목<br>브랜드명</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="displayBrand" value="${display.displayBrand}" placeholder="전시품목 브랜드명">
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>실물 보트수<br>(단위:척)</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="displayBoatCnt" value="${display.displayBoatCnt}" class="onlyNum" placeholder="실물 보트수">
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>전시품소개</p>
                                                        </div>
                                                        <div class="input">
                                                            <textarea name="displayItemIntroKo" placeholder="국문">${display.displayItemIntroKo}</textarea>
                                                            <textarea name="displayItemIntroEn" placeholder="영문" class="onlyNumEng">${display.displayItemIntroEn}</textarea>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </c:forEach>
                                        </c:if>
                                        <div class="formAddBtn"><span class="exhiInfoAdd">추가</span></div>
                                    </div>

                                    <!-- 상세정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">상세정보</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item req">
                                                    <p>회사소개</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="companyIntroKo" name="companyIntroKo" placeholder="국문">${info.companyIntroKo}</textarea>
                                                    <textarea id="companyIntroEn" name="companyIntroEn" placeholder="영문" class="onlyNumEng">${info.companyIntroEn}</textarea>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>KIBS 참가목적</p>
                                                </div>
                                                <div class="input">
                                                    <select name="companyPurposeKo" id="companyPurposeKo">
                                                        <option value="제품 홍보 및 브랜드 인지도 제고" <c:if test="${info.companyPurposeKo eq '제품 홍보 및 브랜드 인지도 제고'}">selected</c:if> >제품 홍보 및 브랜드 인지도 제고</option>
                                                        <option value="비즈니스 상담 및 바이어 확보" <c:if test="${info.companyPurposeKo eq '비즈니스 상담 및 바이어 확보'}">selected</c:if> >비즈니스 상담 및 바이어 확보</option>
                                                        <option value="업계 네트워킹" <c:if test="${info.companyPurposeKo eq '업계 네트워킹'}">selected</c:if> >업계 네트워킹</option>
                                                        <option value="업계 트렌드 파악" <c:if test="${info.companyPurposeKo eq '업계 트렌드 파악'}">selected</c:if> >업계 트렌드 파악</option>
                                                        <option value="올해의 제품상 신청" <c:if test="${info.companyPurposeKo eq '올해의 제품상 신청'}">selected</c:if> >올해의 제품상 신청</option>
                                                        <option value="기타" <c:if test="${info.companyPurposeKo eq '기타'}">selected</c:if> >기타</option>
                                                    </select>
                                                    <select name="companyPurposeEn" id="companyPurposeEn">
                                                        <option value="Product Promotion & Branding" <c:if test="${info.companyPurposeEn eq 'Product Promotion & Branding'}">selected</c:if> >Product Promotion & Branding</option>
                                                        <option value="Business Meeting" <c:if test="${info.companyPurposeEn eq 'Business Meeting'}">selected</c:if> >Business Meeting</option>
                                                        <option value="Networking" <c:if test="${info.companyPurposeEn eq 'Networking'}">selected</c:if> >Networking</option>
                                                        <option value="Trend Research" <c:if test="${info.companyPurposeEn eq 'Trend Research'}">selected</c:if> >Trend Research</option>
                                                        <option value="Awards" <c:if test="${info.companyPurposeEn eq 'Awards'}">selected</c:if> >Awards</option>
                                                        <option value="Others" <c:if test="${info.companyPurposeEn eq 'Others'}">selected</c:if> >Others</option>
                                                    </select>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>신제품출품 사항 소개</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="newItemIntroKo" name="newItemIntroKo" placeholder="국문">${info.newItemIntroKo}</textarea>
                                                    <textarea id="newItemIntroEn" name="newItemIntroEn" placeholder="영문" class="onlyNumEng">${info.newItemIntroEn}</textarea>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>우리 기업 부스는 꼭 들려야 될 이유가 있다면?</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="boothVisitReason" name="boothVisitReason" placeholder="우리 기업 부스는 꼭 들려야 될 이유가 있다면?">${info.boothVisitReason}</textarea>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>행사/이벤트 진행계획</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="eventPlan" name="eventPlan" placeholder="행사/이벤트 진행계획">${info.eventPlan}</textarea>
                                                    <div class="cmnt">2월 24일 자정부터는 신청 이후 수정이 불가합니다.</div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item req">
                                                    <p>로고</p>
                                                </div>
                                                <div class="input file_box">
                                                    <input type="text" id="logo" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" id="logoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                    <label for="logoFile">파일선택</label>
                                                    <div class="cmnt">JPG, PNG형식, 10MB 이하만 가능합니다. (권장 사이즈 : 150x150px)</div>
                                                </div>
                                            </li>

                                            <c:if test="${logoFile ne null and not empty logoFile}">
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>로고 파일</p>
                                                    </div>
                                                    <div class="input file_box">
                                                        <ul>
                                                            <li class="logoFile_li" style="align-items: center;">
                                                                <c:set var="logoFileSrc" value="${fn:replace(logoFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                <c:if test="${not fn:contains(logoFileSrc, '.ai') and not fn:contains(logoFileSrc, '.pdf')}">
                                                                    <img src="${logoFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                                </c:if>
                                                                <%--<a href="/file/download.do?path=exhibitor/company/${logoFile.folderPath}&fileName=${logoFile.fullFileName}">${logoFile.fileName}</a>--%>
                                                                <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${logoFile.folderPath}', '${logoFile.fullFileName}')">${logoFile.fileName}</a>
                                                                <input type="hidden" name="logoUploadFile" id="${logoFile.id}" value="${logoFile.fullFilePath}">
                                                                <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${logoFile.id}')">X</button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </li>
                                            </c:if>

                                            <li class="form_in_tit">홍보 이미지</li>
                                            <li class="proImgBox">
                                                <div class="item req">
                                                    <p>홍보 이미지<span class="proImgNum">1</span></p>
                                                </div>
                                                <div class="input file_box">
                                                    <input type="text" name="promotionImage" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" id="promotionImageFile1" name="promotionImageFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                    <label for="promotionImageFile1">파일선택</label>
                                                    <span class="proImgDel file_box_del">삭제</span>
                                                    <div class="cmnt">JPG, PNG형식, 10mb 이하만 가능합니다. (권장 사이즈 : 1000x250px)</div>
                                                </div>
                                            </li>
                                            <li class="proImgBox">
                                                <div class="item">
                                                    <p>홍보 이미지<span class="proImgNum">2</span></p>
                                                </div>
                                                <div class="input file_box">
                                                    <input type="text" name="promotionImage" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" id="promotionImageFile2" name="promotionImageFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                    <label for="promotionImageFile2">파일선택</label>
                                                    <span class="proImgDel file_box_del">삭제</span>
                                                    <div class="cmnt">JPG, PNG형식, 10mb 이하만 가능합니다. (권장 사이즈 : 1000x250px)</div>
                                                </div>
                                            </li>
                                            <li class="proImgBox">
                                                <div class="item">
                                                    <p>홍보 이미지<span class="proImgNum">3</span></p>
                                                </div>
                                                <div class="input file_box">
                                                    <input type="text" name="promotionImage" class="upload_name" value="" disabled="disabled">
                                                    <input type="file" id="promotionImageFile3" name="promotionImageFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                    <label for="promotionImageFile3">파일선택</label>
                                                    <span class="proImgDel file_box_del">삭제</span>
                                                    <div class="cmnt">JPG, PNG형식, 10mb 이하만 가능합니다. (권장 사이즈 : 1000x250px)</div>
                                                </div>
                                            </li>

                                            <c:if test="${promotionImageFileList ne null and not empty promotionImageFileList}">
                                                <li>
                                                    <div class="item">
                                                        <p>홍보 이미지 파일</p>
                                                    </div>
                                                    <div class="input">
                                                        <ul>
                                                        <c:forEach var="promotionImageFile" items="${promotionImageFileList}" begin="0" end="${promotionImageFileList.size()}" step="1">
                                                            <li class="promotionImageFile_li" style="margin-bottom: 5px; align-items: center;">
                                                                <c:set var="promotionImageSrc" value="${fn:replace(promotionImageFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                <img src="${promotionImageSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                                <%--<a href="/file/download.do?path=exhibitor/company/${promotionImageFile.folderPath}&fileName=${promotionImageFile.fullFileName}">${promotionImageFile.fileName}</a>--%>
                                                                <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${promotionImageFile.folderPath}', '${promotionImageFile.fullFileName}')">${promotionImageFile.fileName}</a>
                                                                <input type="hidden" name="promotionImageUploadFile" id="${promotionImageFile.id}" value="${promotionImageFile.fullFilePath}"/>
                                                                <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${promotionImageFile.id}')">X</button>
                                                            </li>
                                                        </c:forEach>
                                                        </ul>
                                                    </div>
                                                </li>
                                            </c:if>

                                        </ul>

                                        <div class="cmnt_bot">홍보 이미지는 경기국제보트쇼 뉴스레터에 사용될 수 있습니다.</div>

                                        <div class="formAddBtn">
                                            <!-- 이미지교체방법추가 231107 -->
                                            <div class="img_replace_cmnt">
                                                <div class="btn">이미지 교체 방법</div>
                                                <div class="text">
                                                    이미지를 수정(교체) 업로드 하신 후 페이지 가장 하단에 있는 [다음] 버튼을 눌러 저장하세요.<br>
                                                    반드시 [다음] 버튼을 눌러 저장하셔야, 미리보기 이미지가 수정된 이미지로 보입니다.
                                                </div>
                                            </div>
                                            <!-- //이미지교체방법추가 231107 -->
                                            <%--<span class="proImgAdd">추가</span>--%>
                                        </div>
                                    </div>

                                    <!-- 온라인 전시관 정보 -->
                                    <script src="/js/product.js"></script>
                                    <div class="form_wrap">
                                        <div class="form_tit"><div class="big">온라인 전시관 정보</div></div>
                                        <!-- 추가 230810 -->
                                        <ul class="form_box">
                                            <li class="form_in_tit">회사#1</li>
                                            <li>
                                                <div class="item">
                                                    <p>회사소개영상</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="companyIntroVideo" name="companyIntroVideo" value="${info.companyIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                    <div class="cmnt">유튜브에 업로드된 영상만 등록 가능합니다. (숏츠,릴스 게시 불가)</div>
                                                    <div class="cmnt">유튜브 영상 하단의 [공유] 버튼을 클릭하여, 뜨는 링크를 복사/붙여넣기 해주세요.</div>
                                                </div>
                                            </li>
                                        </ul>

                                        <c:if test="${empty onlineList}">
                                            <ul class="form_box onlineInfoBox">
                                                <li class="form_in_tit">
                                                    <input type="hidden" name="onlineItemSeq" value="">
                                                    제품 #<span class="onlineInfoNum">1</span>
                                                    <span class="del_btn onlineInfoDel">삭제</span>
                                                </li>
                                                <li>
                                                    <div class="item req">
                                                        <p>제품명</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="productNameKo" placeholder="국문" class="w50">
                                                        <input type="text" name="productNameEn" placeholder="영문" class="w50 onlyNumEng">
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item req">
                                                        <p>제품분류</p>
                                                    </div>
                                                    <div class="input">
                                                        <select name="productOptionBig" id="productOptionBig" class="w50"></select>
                                                        <select name="productOptionSmall" id="productOptionSmall" class="w50"></select>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item req">
                                                        <p>제품사진</p>
                                                    </div>
                                                    <div class="input file_box">
                                                        <div class="cmnt2">1개 제품 당 제품사진 최대 5개 등록 가능</div>
                                                        <div class="onlinePrdBox">
                                                            <input type="text" id="productImage1_1" name="productImage" class="upload_name" value="" disabled="disabled">
                                                            <input type="file" id="productImageFile1_1" name="productImageFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                            <label for="productImageFile1_1">파일선택</label>
                                                            <span class="onlinePrdAdd" style="cursor: pointer">추가</span>
                                                            <span class="onlinePrdDel" style="cursor: pointer">삭제</span>
                                                            <div class="cmnt">JPG, PNG형식, 10MB 이하만 가능합니다. (권장 사이즈 : 1000x750px)</div>
                                                            <div class="cmnt">첫 번째로 등록한 이미지가 메인 이미지로 적용됩니다.</div>
                                                        </div>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item req">
                                                        <p>제품 설명</p>
                                                    </div>
                                                    <div class="input">
                                                        <textarea name="productIntroKo" placeholder="국문"></textarea>
                                                        <textarea name="productIntroEn" placeholder="영문" class="onlyNumEng"></textarea>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>제품 영상</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="productIntroVideo" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                        <div class="cmnt">유튜브에 업로드된 영상만 등록 가능합니다. (숏츠,릴스 게시 불가)</div>
                                                        <div class="cmnt">유튜브 영상 하단의 [공유] 버튼을 클릭하여, 뜨는 링크를 복사/붙여넣기 해주세요.</div>
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>전장(m)</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="productWidth" class="onlyNumDec" placeholder="단위 제외, 숫자만 입력해주세요.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>마력</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="productHorsePower" class="onlyNumDec" placeholder="단위 제외, 숫자만 입력해주세요.">
                                                    </div>
                                                </li>
                                            </ul>
                                        </c:if>

                                        <c:if test="${not empty onlineList}">
                                            <c:forEach var="online" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                                <ul class="form_box onlineInfoBox">
                                                    <li class="form_in_tit">
                                                        <input type="hidden" name="onlineItemSeq" value="${online.seq}">
                                                        제품 #<span class="onlineInfoNum">${status.index + 1}</span>
                                                        <span class="del_btn onlineInfoDel">삭제</span>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>제품명</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="productNameKo" value="${online.productNameKo}" placeholder="국문" class="w50">
                                                            <input type="text" name="productNameEn" value="${online.productNameEn}" placeholder="영문" class="w50 onlyNumEng">
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>제품분류</p>
                                                        </div>
                                                        <div class="input">
                                                            <select name="productOptionBig" class="w50"></select>
                                                            <select name="productOptionSmall" class="w50"></select>
                                                            <c:if test="${online.productOptionBig ne null and online.productOptionBig ne '' and online.productOptionSmall ne null and online.productOptionSmall ne ''}">
                                                                <span class="preOptionList" style="margin-left: 10px; display: block;">
                                                                    저장된 제품 분류 값 : &nbsp;
                                                                        <input type="text" name="preBigValue" value="${online.productOptionBig}" style="width: unset;" readonly="readonly"/>&nbsp;
                                                                        <input type="text" name="preSmallValue" value="${online.productOptionSmall}" style="width: unset;" readonly="readonly"/>
                                                                </span>
                                                            </c:if>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="item req">
                                                            <p>제품사진</p>
                                                        </div>
                                                        <div class="input file_box">
                                                            <div class="cmnt2">1개 제품 당 제품사진 최대 5개 등록 가능</div>
                                                            <div class="onlinePrdBox">
                                                                <c:set var="productImageNumIdx" value="0" />
                                                                <c:forEach var="productImageFile" items="${productImageFileList}" begin="0" end="${productImageFileList.size()}" step="1">
                                                                    <c:set var="onlineInfoNumIdx" value="${status.index + 1}"/>
                                                                    <c:if test="${fn:contains(productImageFile.note , 'productImage'.concat(onlineInfoNumIdx.toString()).concat('_'))}">
                                                                        <c:set var="productImageNumIdx" value="${productImageNumIdx + 1}" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <input type="text" id="productImage${status.index + 1}_${productImageNumIdx + 1}" name="productImage" class="upload_name" value="" disabled="disabled">
                                                                <input type="file" id="productImageFile${status.index + 1}_${productImageNumIdx + 1}" name="productImageFile" accept=".png, .jpg, .jpeg" class="upload_hidden">
                                                                <label for="productImageFile${status.index + 1}_${productImageNumIdx + 1}">파일선택</label>
                                                                <span class="onlinePrdAdd" style="cursor: pointer">추가</span>
                                                                <span class="onlinePrdDel" style="cursor: pointer">삭제</span>
                                                                <div class="cmnt">JPG, PNG형식, 10MB 이하만 가능합니다. (권장 사이즈 : 1000x750px)</div>
                                                                <div class="cmnt">첫 번째로 등록한 이미지가 메인 이미지로 적용됩니다.</div>
                                                            </div>
                                                        </div>
                                                    </li>

                                                    <c:if test="${productImageFileList ne null and not empty productImageFileList}">
                                                        <li class="preValueList">
                                                            <div class="item">
                                                                <p>제품 사진 파일</p>
                                                            </div>
                                                            <div class="input file_box">
                                                                <ul>
                                                                    <c:forEach var="productImageFile" items="${productImageFileList}" begin="0" end="${productImageFileList.size()}" step="1">
                                                                        <c:set var="idx" value="${status.index + 1}"/>
                                                                        <c:if test="${fn:contains(productImageFile.note, 'productImage'.concat(idx.toString()).concat('_'))}">
                                                                            <li class="productImageFile_li" style="align-items: center;">
                                                                                <c:set var="productImageFileSrc" value="${fn:replace(productImageFile.fullFilePath, '/usr/local/tomcat/webapps', '/../../../..')}" />
                                                                                <img src="${productImageFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin-right: 10px;"/>
                                                                                <%--<a href="/file/download.do?path=exhibitor/company/${productImageFile.folderPath}&fileName=${productImageFile.fullFileName}">${productImageFile.fileName}</a>--%>
                                                                                <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${productImageFile.folderPath}', '${productImageFile.fullFileName}')">${productImageFile.fileName}</a>
                                                                                <input type="hidden" name="productImageUploadFile" id="${productImageFile.id}" value="${productImageFile.fullFilePath}">
                                                                                <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${productImageFile.id}')">X</button>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </div>
                                                        </li>
                                                    </c:if>

                                                    <li>
                                                        <div class="item req">
                                                            <p>제품 설명</p>
                                                        </div>
                                                        <div class="input">
                                                            <textarea name="productIntroKo" placeholder="국문">${online.productIntroKo}</textarea>
                                                            <textarea name="productIntroEn" placeholder="영문" class="onlyNumEng">${online.productIntroEn}</textarea>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="item">
                                                            <p>제품 영상</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="productIntroVideo" value="${online.productIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                            <div class="cmnt">유튜브에 업로드된 영상만 등록 가능합니다. (숏츠,릴스 게시 불가)</div>
                                                            <div class="cmnt">유튜브 영상 하단의 [공유] 버튼을 클릭하여, 뜨는 링크를 복사/붙여넣기 해주세요.</div>
                                                        </div>
                                                    </li>
                                                    <li class="w50">
                                                        <div class="item">
                                                            <p>전장(m)</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="productWidth" value="${online.productWidth}" class="onlyNumDec" placeholder="단위 제외, 숫자만 입력해주세요.">
                                                        </div>
                                                    </li>
                                                    <li class="w50">
                                                        <div class="item">
                                                            <p>마력</p>
                                                        </div>
                                                        <div class="input">
                                                            <input type="text" name="productHorsePower" value="${online.productHorsePower}" class="onlyNumDec" placeholder="단위 제외, 숫자만 입력해주세요.">
                                                        </div>
                                                    </li>
                                                </ul>
                                            </c:forEach>
                                        </c:if>
                                        <div class="formAddBtn">
                                            <!-- 이미지교체방법추가 231107 -->
                                            <div class="img_replace_cmnt">
                                                <div class="btn">이미지 교체 방법</div>
                                                <div class="text">
                                                    이미지를 수정(교체) 업로드 하신 후 페이지 가장 하단에 있는 [다음] 버튼을 눌러 저장하세요.<br>
                                                    반드시 [다음] 버튼을 눌러 저장하셔야, 미리보기 이미지가 수정된 이미지로 보입니다.
                                                </div>
                                            </div>
                                            <!-- //이미지교체방법추가 231107 -->
                                            <span class="onlineInfoAdd">추가</span>
                                        </div>
                                    </div>

                                    <!-- 비즈니스 상담 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">비즈니스 상담</div>
                                            <div class="small">2025 경기국제보트쇼에서는 국내외바이어 초청 및 1:1 비즈니스 상담을 진행합니다.</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item req">
                                                    <p>비즈니스 상담 참가 희망 여부</p>
                                                </div>
                                                <div class="input check">
                                                    <label>
                                                        <input type="radio" id="exportMeetingY" name="exportMeetingYn" value="Y" <c:if test="${info.exportMeetingYn eq 'Y'}">checked</c:if> />
                                                        참가
                                                    </label>
                                                    <label>
                                                        <input type="radio" id="exportMeetingN" name="exportMeetingYn" value="N" <c:if test="${info.exportMeetingYn eq 'N' or info.exportMeetingYn eq null or info.exportMeetingYn eq ''}">checked</c:if>/>
                                                        참가 안 함
                                                    </label>
                                                </div>
                                            </li>
                                        </ul>

                                        <div class="form_chuga form_add_buyer">
                                            <ul class="form_box" style="border-top:0;">
                                                <li class="form_in_tit">초청 희망 바이어</li>
                                            </ul>

                                            <c:if test="${buyerList ne null and not empty buyerList}">
                                                <c:forEach var="buyer" items="${buyerList}" begin="0" end="${buyerList.size()}" step="1">
                                                    <input type="hidden" name="buyerItemSeq" value="${buyer.seq}">
                                                    <div class="form_chuga_list">
                                                        <div class="cont">
                                                            <div class="name">${buyer.buyerCompanyName}</div>
                                                            <div class="gubun">${buyer.buyerCompanyCountry} / ${buyer.buyerCompanyLocation} / ${buyer.buyerCompanyDepart} ${buyer.buyerCompanyPosition}</div>
                                                        </div>
                                                        <div class="modifyFormBuyer modifyFormList" onclick="f_buyer_modify_modal('select','${buyer.seq}')">수정</div>
                                                        <div class="delFormBuyer delFormList" onclick="f_buyer_remove('select', '${buyer.seq}')">삭제</div>
                                                        <input type="hidden" name="buyerCompanyName" value="${buyer.buyerCompanyName}">
                                                        <input type="hidden" name="buyerCompanyCountry" value="${buyer.buyerCompanyCountry}">
                                                        <input type="hidden" name="buyerCompanyLocation" value="${buyer.buyerCompanyLocation}">
                                                        <input type="hidden" name="buyerCompanyHomepage" value="${buyer.buyerCompanyHomepage}">
                                                        <input type="hidden" name="buyerCompanyDepart" value="${buyer.buyerCompanyDepart}">
                                                        <input type="hidden" name="buyerCompanyPosition" value="${buyer.buyerCompanyPosition}">
                                                        <input type="hidden" name="buyerCompanyEmail" value="${buyer.buyerCompanyEmail}">
                                                        <input type="hidden" name="buyerCompanyTel" value="${buyer.buyerCompanyTel}">
                                                        <input type="hidden" name="buyerCompanyPhone" value="${buyer.buyerCompanyPhone}">
                                                        <input type="hidden" name="buyerCompanyFax" value="${buyer.buyerCompanyFax}">
                                                        <input type="hidden" name="buyerCompanyItem" value="${buyer.buyerCompanyItem}">
                                                        <input type="hidden" name="buyerCompanyInviteReason" value="${buyer.buyerCompanyInviteReason}">
                                                        <input type="hidden" name="buyerCompanyProgressYn" value="${buyer.buyerCompanyProgressYn}">
                                                        <input type="hidden" name="buyerCompanyHope" value="${buyer.buyerCompanyHope}">
                                                    </div>
                                                </c:forEach>
                                            </c:if>

                                            <a href="#" id="buyer_add_btn" class="addFormBuyer addFormList btnSt01">여기를 클릭하여 바이어 등록</a>
                                        </div>
                                    </div>

                                    <div class="form_btn" style="justify-content:flex-end;">
                                    <%--href="/apply/step2_1.do#apply_s"--%>
                                        <a href="javascript:void(0);" onclick="f_apply_comp('01','${info.seq}')" class="form_btn_next">
                                        <div class="big">다음</div>
                                        <div class="small">전시부스 신청</div>
                                    </a>
                                </div>
                                </form>
                            </div>
                        </div>
                        <!-- //section -->
                    </div>
                </div>

            </div>

            <!-- 초청 희망 바이어 팝업 -->
            <div id="buyer_popup" class="popFormBuyer popForm popup">
                <div class="popup_inner">
                    <div class="popup_wrap">
                        <div class="pop_tit">
                            <div class="tit">새 초청 희망 바이어 등록/수정</div>
                            <a class="close" href="javascript:f_buyer_init();"><img src="/img/close_w.png"></a>
                        </div>
                        <div class="pop_box">
                            <div class="pop_cont">
                                <!-- 폼박스 -->
                                <div class="form_s">
                                    <!-- 회원계정정보 -->
                                    <div class="form_wrap">
                                        <div class="form_tit">
                                            <div class="big">바이어 정보</div>
                                        </div>
                                        <ul class="form_box">
                                            <li>
                                                <div class="item">
                                                    <p>회사명</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_company_name" name="buyerCompanyName" placeholder="회사명">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>국가</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_country" name="buyerCompanyCountry" placeholder="국가">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>소재지</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_location" name="buyerCompanyLocation" placeholder="소재지">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>홈페이지</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_homepage" name="buyerCompanyHomepage" placeholder="https://">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>부서</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_depart" name="buyerCompanyDepart" placeholder="부서">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>직책</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_position" name="buyerCompanyPosition" placeholder="직책">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>이메일</p>
                                                </div>
                                                <div class="input email">
                                                    <input type="text" id="buyer_email_input1" name="buyerCompanyEmail" class="email_input1" placeholder="이메일"/>
                                                    <span>@</span>
                                                    <input type="text" id="buyer_email_input2" name="buyerCompanyDomain" class="email_input2" placeholder="직접입력"/>
                                                    <select id="buyer_email_select">
                                                        <option selected>직접입력</option>
                                                        <option value="daum.net">daum.net</option>
                                                        <option value="nate.com">nate.com</option>
                                                        <option value="hanmail.net">hanmail.net</option>
                                                        <option value="naver.com">naver.com</option>
                                                        <option value="hotmail.com">hotmail.com</option>
                                                        <option value="yahoo.co.kr">yahoo.co.kr</option>
                                                        <option value="empal.com">empal.com</option>
                                                        <option value="korea.com">korea.com</option>
                                                        <option value="hanmir.com">hanmir.com</option>
                                                        <option value="dreamwiz.com">dreamwiz.com</option>
                                                        <option value="orgio.net">orgio.net</option>
                                                        <option value="korea.com">korea.com</option>
                                                        <option value="hitel.net">hitel.net</option>
                                                    </select>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>전화번호</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_tel" name="buyerCompanyTel" class="onlyTel" maxlength="13" placeholder="하이픈(-)을 포함하여 입력해 주세요."/>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>휴대전화</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_phone" name="buyerCompanyPhone" class="onlyTel" maxlength="13" onblur="f_phone_number_valid_check(this)" placeholder="하이픈(-)을 포함하여 입력해 주세요."/>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>팩스</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_fax" name="buyerCompanyFax" class="onlyNumh" placeholder="하이픈(-)을 포함하여 입력해 주세요."/>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>취급품목</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_item" name="buyerCompanyItem" placeholder="취급품목"/>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>초청사유</p>
                                                </div>
                                                <div class="input">
                                                    <textarea id="buyer_invite_reason" name="buyerCompanyInviteReason" rows="3" placeholder="초청사유"></textarea>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>계약진행 여부</p>
                                                </div>
                                                <div class="input">
                                                    <select id="buyer_progress_yn" name="buyerCompanyProgressYn">
                                                        <option value="Y">YES</option>
                                                        <option value="N">NO</option>
                                                    </select>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>희망사항</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" id="buyer_hope" name="buyerCompanyHope" placeholder="희망사항"/>
                                                </div>
                                            </li>
                                        </ul>
                                        <a class="btnSave" href="javascript:f_buyer_add('${info.seq}')">등록 및 수정</a>
                                    </div>
                                </div>
                                <!-- //폼박스 -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 전환페이지 설정 -->
            <script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
            <script type="text/javascript">
                var _nasa={};
                if(window.wcs) _nasa["cnv"] = wcs.cnv("4","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
            </script>

            <c:import url="../footer.jsp" charEncoding="UTF-8"/>

            <c:if test="${info ne null}">
                <c:if test="${info.exportMeetingYn eq 'Y'}">
                    <script> $(function(){ document.querySelector('.form_add_buyer').style.display = 'block'; }); </script>
                </c:if>
                <c:if test="${info.exportMeetingYn eq 'N'}">
                    <script> $(function(){ document.querySelector('.form_add_buyer').style.display = 'none'; }); </script>
                </c:if>
            </c:if>

            <%--<script>
                $(function () {
                    if (getCurrentDate() >= '20250224000000') {
                        $('#eventPlan').prop('readonly', true);
                        $('#eventPlan').on('keyup', function () {
                            alert('2월 24일 자정부터는 입력 및 수정이 불가합니다.');
                        })
                    }
                });
            </script>--%>

        </c:if>

    </c:otherwise>
</c:choose>

</body>
</html>