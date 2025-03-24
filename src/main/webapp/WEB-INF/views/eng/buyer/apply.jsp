<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>2025 KIBS</title>

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />

    <style>
        #spinner { width: 100%; height: 100%; top: 0; left: 0; opacity: .6; background: silver; position: absolute; z-index: 9999; display: none;}
        #spinner div { width: 100%; height: 100%; display: table; }
        #spinner span { display: table-cell; text-align: center; vertical-align: middle; }
        #spinner img { background: white; padding: 1em; border-radius: .7em; }
    </style>
</head>

<body>
<script>
    /*alert('Contact Us\nEmail : kibs@kintex.com\nTel : 031-995-8738');*/
    window.location.href = '/visitor/apply.do';
</script>

<c:import url="../header.jsp" charEncoding="UTF-8"/>

<%-- 24.02.29 참관신청 종료
<div id="container">

    <!-- section -->
    <div class="sub_top">
        <div class="inner">
            <div class="sub_top_box">
                <div class="sub_top_nav">
                    <span>Home</span><span>Buyer</span><span>Pre-registration</span>
                </div>
                <div class="sub_top_tit">Pre-registration</div>
            </div>
        </div>
    </div>
    <!-- //section -->

    <!-- section -->
    <div class="sub_tab">
        <div class="inner">
            <div class="sub_tab_box">
                <a href="/" class="iconHome"><img src="/img/icon_home.png"></a>
                <div class="optionBox">
                    <div class="tabOpt1 tabOption">
                        <div class="tabOptAct">Buyer</div>
                        <div class="tabOptSel">
                            <a href="/eng/guide/summary.do">KIBS 2025</a>
                            <a href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a class="active" href="/eng/buyer/apply.do">Buyer</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Pre-registration</div>
                        <div class="tabOptSel">
                            <a href="https://www.investkorea.org/ik-en/index.do" target="_blank">Investor program</a>
                            <a class="active" href="/eng/buyer/apply.do">Pre-registration</a>
                            <a href="/eng/buyer/glance.do">Korea at a Glance</a>
                            <a href="/eng/buyer/marina.do">Marine Industry in korea</a>
                            <a href="/eng/buyer/match.do">Exhibitor-Buyer Matchmaking Program</a>
                            <a href="/eng/buyer/qna.do">QnA</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- section -->


    <!-- section -->
    <div class="form_s padding_tb">
        <div class="inner">
            <!-- 약관 -->
            <div class="form_wrap">
                <div class="form_tit">
                    <div class="big">개인정보수집 및 이용안내</div>
                </div>
                <div class="form_pri">
                    <div class="form_pri_box">
                        <div class="form_pri_titt">킨텍스 개인정보 처리방침</div>
                        킨텍스(이하 ‘회사’라 한다)는 개인정보 보호법 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여
                        다음과 같이 개인정보 처리지침을 수립․공개합니다.<br><br>

                        <div class="form_pri_titt">제1조(개인정보의 처리목적)</div>
                        회사는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 개인정보
                        보호법 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.<br>
                        1. 홈페이지 회원 가입 및 관리<br>
                        회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별․인증, 회원자격 유지․관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 만 14세
                        미만 아동의 개인정보 처리시 법정대리인의 동의여부 확인, 각종 고지․통지, 고충처리 등을 목적으로 개인정보를 처리합니다.<br>
                        2. 재화 또는 서비스 제공<br>
                        전시/컨벤션 서비스 제공(부스참가, 전시회 입장 등), 콘텐츠 제공, 맞춤서비스 제공, 본인인증, 연령인증, 비용결제․정산등을 목적으로 개인정보를 처리합니다.<br>
                        3. 고충처리<br>
                        민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락․통지, 처리결과 통보 등의 목적으로 개인정보를 처리합니다.<br>
                        4. 마케팅 및 광고에의 활용<br>
                        신규 서비스 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 , 인구통계학적 특성에 따른 서비스 제공 및 광고등을 목적으로 개인정보를
                        처리합니다.<br>
                        또한 재화나 서비스의 홍보 또는 판매 권유 등을 위하여 해당 개인정보를 이용하여 정보주체에게 연락할 수 있습니다.<br>
                        5. 개인정보 수집항목 및 현황<br>
                        - 개인정보 수집항목 : 로그인ID, 이름, 전화번호, 이메일, 비밀번호, 연령, 성별,출생년도, 지역, 직장명(단체명), 설문조사결과, 주소<br>
                        - 수집방법 : 홈페이지, 킨텍스 앱, 주관전시 홈페이지 사전등록, 서면양식, 온,오프라인 이벤트 행사<br>
                        - 보유근거 : 회원가입시 개인정보처리방침 동의 (개인정보 마케팅활용 : 5년)<br><br>

                        <div class="form_pri_titt">제2조(개인정보의 처리 및 보유기간)</div>
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

                        <div class="form_pri_titt">제3조(개인정보의 제3자 제공)</div>
                        1. 회사는 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법
                        제17조에 해당하는 경우에 개인정보를 제3자에게 제공할 수 있습니다.<br>
                        2. 회사는 다음과 같이 개인정보를 제3자에게 제공할 수 있습니다.<br>
                        - 개인정보를 제공받는 자 : 킨텍스 앱의 무료/유료 서비스를 사용하는 전시회 /컨벤션 행사 주최자<br>
                        • 제공받는 자의 개인정보 이용목적 : 전시회/컨벤션의 무료 서비스, 유료 결제 서비스, 이벤트 공동개최, 마케팅, 광고 등<br>
                        • 제공하는 개인정보 항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도, 직장명(단체), 추천인(ID또는 이름), 전시회/컨벤션 별 설문조사 항목
                        정보<br><br>

                        - 개인정보를 제공받는 자 : 전시산업진흥회<br>
                        • 제공받는 자의 개인정보 이용목적 : 전시산업진흥회 전시회 인증을 위한 정보 제공<br>
                        • 제공하는 개인정보 항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도, 직장명(단체), 추천인(ID또는 이름), 전시회/컨벤션 별 설문조사 항목
                        정보<br><br>

                        <div class="form_pri_titt">제4조(개인정보처리의 위탁)</div>
                        1. 회사는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.<br>
                        - (위탁자) : 킨텍스 (수탁자) : 에스브이엠플래닛<br>
                        - 위탁하는 업무의 내용 : 경기국제보트쇼 홈페이지 관리<br>
                        - 위탁기간 : 위탁계약 종료 시<br>
                        2. 회사는 위탁계약 체결시 개인정보 보호법 제②5조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리적 보호조치, 재위탁 제한, 수탁자에 대한
                        관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.<br>
                        3. 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.<br><br>

                        <div class="form_pri_titt">제5조(정보주체의 권리․의무 및 행사방법)</div>
                        1. 정보주체는 회사에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.<br>
                        - 개인정보 열람요구<br>
                        - 오류 등이 있을 경우 정정 요구<br>
                        - 삭제요구<br>
                        - 처리정지 요구<br>
                        2. 제1항에 따른 권리 행사는 회사에 대해 서면, 전화, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 회사는 이에 대해 지체없이 조치하겠습니다.<br>
                        3. 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 회사는 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지
                        않습니다.<br>
                        4. 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호
                        서식에 따른 위임장을 제출하셔야 합니다.<br>
                        5. 정보주체는 개인정보 보호법 등 관계법령을 위반하여 회사가 처리하고 있는 정보주체 본인이나 타인의 개인정보 및 사생활을 침해하여서는 아니됩니다.<br><br>

                        <div class="form_pri_titt">제6조(처리하는 개인정보 항목)</div>
                        회사는 다음의 개인정보 항목을 처리하고 있습니다.<br>
                        1. 홈페이지/앱 회원 가입 및 관리<br>
                        - 필수항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도<br>
                        - 선택항목 : 직장인(단체), 추천인(ID또는이름), 설문조사<br>
                        2. 서비스 제공<br>
                        - 필수항목 : 아이디, 이름, 휴대폰번호, 지역구분, 성별, 출생년도<br>
                        - 선택항목 : 직장인(단체), 추천인(ID또는이름), 설문조사, 과거 구매내역<br>
                        3. 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로 생성되어 수집될 수 있습니다.<br>
                        - IP주소, 쿠키, MAC주소, 서비스 이용기록, 방문기록, 불량 이용기록 등<br><br>

                        <div class="form_pri_titt">제7조(개인정보의 파기)</div>
                        1. 회사는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.<br>
                        2. 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당
                        개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.<br>
                        3. 개인정보 파기의 절차 및 방법은 다음과 같습니다.<br>
                        - 파기절차 : 회사는 파기 사유가 발생한 개인정보를 즉시 파기합니다.<br>
                        - 파기방법 : 회사는 전자적 파일 형태로 기록․저장된 개인정보는 기록을 재생할 수 없도록 DB데이터 삭제 등의 방법을 이용하여 파기하며, 종이 문서에 기록․저장된
                        개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.<br><br>

                        <div class="form_pri_titt">제8조(개인정보의 안전성 확보조치)</div>
                        회사는 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.<br>
                        1. 관리적 조치 : 내부관리계획 수립․시행, 정기적 직원 교육 등<br>
                        2. 기술적 조치 : 개인정보처리시스템 등의 접근권한 관리, 접근통제, 고유식별정보 등의 암호화, 보안프로그램 설치<br>
                        3. 물리적 조치 : 전산실, 자료보관실 등의 접근통제<br><br>

                        <div class="form_pri_titt">제9조(개인정보 자동 수집 장치의 설치∙운영 및 거부에 관한 사항)</div>
                        1. 회사는 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용할 수 있습니다.<br>
                        2. 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자들의 PC 컴퓨터내의 하드디스크에 저장되기도
                        합니다.<br>
                        - 쿠키의 사용목적: 이용자가 방문한 각 서비스와 웹 사이트들에 대한 방문 및 이용형태, 인기 검색어, 보안접속 여부, 등을 파악하여 이용자에게 최적화된 정보 제공을
                        위해 사용됩니다.<br>
                        - 쿠키의 설치∙운영 및 거부 : 웹브라우저 상단의 도구>인터넷 옵션>개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부 할 수 있습니다.<br>
                        - 쿠키 저장을 거부할 경우 맞춤형 서비스 이용에 어려움이 발생할 수 있습니다.<br><br>

                        <div class="form_pri_titt">제10조(개인정보 보호책임자)</div>
                        1. 회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를
                        지정하고 있습니다.<br>
                        ▶ 개인정보 보호책임자 : 사업부사장<br>
                        ▶ 개인정보 보호 관리자<br>
                        부서명 : 전시사업2팀<br>
                        관리자 : 양주윤 팀장<br>
                        연락처 : 031-995-8731 / frankyang@kintex.com<br><br>

                        ▶ 개인정보 보호 담당자<br>
                        부서명 : 전시사업2팀<br>
                        담당자 : 김미희 과장<br>
                        연락처 : 031-995-8735 / aileen@kintex.com<br><br>

                        2. 정보주체는 회사의 서비스(또는 사업)을 이용하면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 담당부서로 문의하실 수
                        있습니다. 회사는 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.<br><br>

                        <div class="form_pri_titt">제11조(개인정보 열람청구)</div>
                        정보주체는 개인정보 보호법 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다. 회사는 정보주체의 개인정보 열람청구가 신속하게 처리되도록
                        노력하겠습니다.<br>
                        ▶ 개인정보 열람청구 접수․처리 부서 : 전시사업2팀<br><br>

                        <div class="form_pri_titt">제12조(권익침해 구제방법)</div>
                        정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.<br>
                        (아래의 기관은 회사와는 별개의 기관으로서, 회사의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기
                        바랍니다)<br><br>

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

                        <div class="form_pri_titt">제13조(영상정보처리기기 설치․운영)</div>
                        1. 킨텍스는 아래와 같이 영상정보처리기기를 설치․운영하고 있습니다.<br>
                        - 영상정보처리기기 설치근거․목적 : 킨텍스의 시설안전․화재예방<br>
                        - 설치 대수, 설치 위치, 촬영 범위 : 전시장 로비․전시실 등 주요시설물에 설치, 촬영범위는 주요시설물의 전 공간을 촬영<br>
                        - 관리책임자, 담당부서 및 영상정보에 대한 접근권한자 : 시설운영팀<br>
                        - 영상정보 촬영시간, 보관기간, 보관장소, 처리방법<br>
                        • 촬영시간 : 24시간 촬영<br>
                        • 보관장소 및 처리방법 : 영상정보처리기기 통제실에 보관․처리<br>
                        - 영상정보 확인 방법 및 장소 : 시설운영팀<br>
                        - 정보주체의 영상정보 열람 등 요구에 대한 조치 : 개인영상정보 열람․존재확인 청구서로 신청하여야 하며, 정보주체 자신이 촬영된 경우 또는 명백히 정보주체의
                        생명․신체․재산 이익을 위해 필요한 경우에 한해 열람을 허용함<br>
                        - 영상정보 보호를 위한 기술적․관리적․물리적 조치 : 내부관리계획 수립, 접근통제 및 접근권한 제한, 영상정보의 안전한 저장․전송기술 적용, 처리기록 보관 및
                        위․변조 방지조치, 보관시설 마련 및 잠금장치 설치 등<br><br>

                        <div class="form_pri_titt">제14조(영상정보(사진 및 동영상 등) 촬영에 관한 동의)</div>
                        1) 박람회 현장에서 사진 촬영 및 영상 녹화가 진행됩니다.<br>
                        2) 촬영목적<br>
                        <span style="font-weight: bold;">①</span> 본 행사 및 추후 개최될 전시회와 컨퍼런스 홍보<br>
                        <span style="font-weight: bold;">②</span> 박람회 관련 보도자료 및 문건 첨부<br>
                        <span style="font-weight: bold;">③</span> 경기국제보트쇼 홍보를 위한 제작물에 활용<br>
                        3) 촬영된 자료는 다른 목적으로 사용되지 않습니다<br>

                        * 귀하는 개인정보 수집〮이용 및 제3자 제공에 대한 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 서비스가 제한될 수 있습니다.
                    </div>
                    <div class="form_pri_agree">
                        <label><input type="radio" name="agree1" value="Y">Agree</label>
                        <label><input type="radio" name="agree1" value="N">Disagree</label>
                    </div>
                </div>
            </div>
            <!-- //약관 -->

            <!-- 약관 -->
            <div class="form_wrap">
                <div class="form_tit">
                    <div class="big">개인정보 제3자 제공에 대한 별도 동의</div>
                </div>
                <div class="form_pri">
                    <div class="form_pri_box">
                        ① 개인정보를 제공받는 자 : 2025 경기국제보트쇼 온라인 전시관 참가 업체<br>
                        ② 개인정보를 제공받는 자의 개인정보 이용 목적 : 고객 관리 및 상담 서비스 제공, 마케팅 및 영업 활동, 고객층 분석(성별, 연령, 관심 정보 등)<br>
                        ③ 제공하는 개인정보의 항목 : 성명, 전화번호, E-mail, 소속 회사명, 지역, 성별, 연령대, 관심 품목, 홈페이지 이용기록, 쿠키 등<br>
                        ④ 개인정보를 제공받는 자의 개인정보 보유 및 이용 기간 : 제공 시점으로부터 2년<br>
                        ⑤ 동의를 거부할 수 있으며, 동의 거부시 ‘상담 예약 서비스’가 원활하게 진행되지 않을 수 있습니다.
                    </div>
                    <div class="form_pri_agree">
                        <label><input type="radio" name="agree2" value="Y">Agree</label>
                        <label><input type="radio" name="agree2" value="N">Disagree</label>
                    </div>
                </div>
            </div>
            <!-- //약관 -->

            <!-- 약관 -->
            <div class="form_wrap">
                <div class="form_tit">
                    <div class="big">안전 준수 동의</div>
                </div>
                <div class="form_pri">
                    <div class="form_pri_box">
                        1. 행사장 및 참가업체 부스 내 설치구조물, 제품 등에 기대거나 충격을 주지 않습니다.<br>
                        2. 행사장 및 참가업체 부스 내 설치구조물, 제품 등 이용 시 안전사고에 주의하여 관계자의 안내에 따르며 주의 사항을 반드시 지킵니다.<br><br>
                        상기 주의 사항을 확인하였으며 불이행으로 인한 사고 발생 시 주최 측은 책임지지 않습니다.
                    </div>
                    <div class="form_pri_agree">
                        <label><input type="radio" name="agree3" value="Y">Agree</label>
                        <label><input type="radio" name="agree3" value="N">Disagree</label>
                    </div>
                </div>
            </div>
            <!-- //약관 -->

            <!--begin::Form-->
            <form id="joinForm" name="joinForm" method="post" onsubmit="return false;">

                <!-- 참관객 정보 -->
                <div class="form_wrap">
                    <div class="form_tit">
                        <div class="big">Visitor Information</div>
                    </div>
                    <ul class="form_box">
                        <li>
                            <div class="item req">
                                <p>Name</p>
                            </div>
                            <div class="input">
                                <input type="text" id="name" name="name" placeholder="Please enter your name.">
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item">
                                <p>Tel</p>
                            </div>
                            <div class="input">
                                <input type="tel" id="tel" name="tel" class="onlyTel" maxlength="14" placeholder="Please enter only numbers.">
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item req">
                                <p>Mobile</p>
                            </div>
                            <div class="input">
                                <input type="tel" id="phone" name="phone" class="onlyTel" maxlength="13" placeholder="Please enter only numbers.">
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>E-mail</p>
                            </div>
                            <div class="input email">
                                <input type="text" id="email" name="email" placeholder="email" class="email_input1">
                                <span>@</span>
                                <input type="text" id="domain" name="domain" placeholder="domain" class="email_input2">
                                <select id="email_select">
                                    <option selected>Direct input</option>
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
                                <p>Company Name</p>
                            </div>
                            <div class="input companyNameBox" style="flex-wrap: wrap">
                                <input class="companyNameInput" type="text" id="companyName" name="companyName" placeholder="Company Name">
                                &lt;%&ndash;<input class="companyNameSh" type="button" value="Search">&ndash;%&gt;
                                <input type="text" id="companyAddress" name="companyAddress" placeholder="Company Address">
                            </div>
                        </li>
                        <li>
                            <div class="item">
                                <p>Companion attendance</p>
                            </div>
                            <div class="input check">
                                <label><input type="radio" name="partnerYn" id="Y" value="Y">Yes</label>
                                <label><input type="radio" name="partnerYn" id="N" value="N" checked>None</label>
                                <div class="cmnt">Please select only in cases such as children(under the age of ten) and elderly who are difficult to apply individual observation.</div>
                            </div>
                        </li>
                    </ul>

                    <ul class="form_box visitPartnerBox" style="border-top:0;">
                        <li class="form_in_tit">
                            Companion #<span class="visitPartnerNum">1</span>
                            <span class="del_btn visitPartnerDel">Delete</span>
                        </li>
                        <li class="w50">
                            <div class="item req">
                                <p>Name</p>
                            </div>
                            <div class="input">
                                <input type="text" name="partnerName">
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item req">
                                <p>Age</p>
                            </div>
                            <div class="input">
                                <input type="text" name="partnerAge" class="onlyNum" maxlength="2">
                            </div>
                        </li>
                    </ul>
                    <div class="formAddBtn"><span class="visitPartnerAdd">Add</span></div>
                </div>
                <!-- //참관객 정보 -->

                <!-- 설문항목 -->
                <div class="form_wrap">
                    <div class="form_tit">
                        <div class="big">Visitor Survey</div>
                    </div>
                    <ul class="form_box">
                        <li class="w50">
                            <div class="item req">
                                <p>Gender</p>
                            </div>
                            <div class="input check">
                                <label><input type="radio" name="sex" value="남자">Male</label>
                                <label><input type="radio" name="sex" value="여자">Female</label>
                            </div>
                        </li>
                        <li class="w50">
                            <div class="item req">
                                <p>Country</p>
                            </div>
                            <div class="input">
                                <script src="/js/country.js"></script>
                                <select id="country" name="country">
                                </select>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Age</p>
                            </div>
                            <div class="input check">
                                <label><input type="radio" name="ageGroup" value="10">10s</label>
                                <label><input type="radio" name="ageGroup" value="20">20s</label>
                                <label><input type="radio" name="ageGroup" value="30">30s</label>
                                <label><input type="radio" name="ageGroup" value="40">40s</label>
                                <label><input type="radio" name="ageGroup" value="50">50s</label>
                                <label><input type="radio" name="ageGroup" value="60">Over 60s</label>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Classification</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="observationGbn" value="조종면허 보유자">Boat driver’s license holder</label>
                                <label><input type="checkbox" name="observationGbn" value="보트 소유자">Boat owner</label>
                                <label><input type="checkbox" name="observationGbn" value="보트 구매 예정자">Potential boat buyer</label>
                                <label><input type="checkbox" name="observationGbn" value="관련 업종 종사자">Marine industry employee</label>
                                <label><input type="checkbox" name="observationGbn" value="낚시 매니아">Fishing maniac</label>
                                <label><input type="checkbox" name="observationGbn" value="다이빙 매니아">Diving maniac</label>
                                <label><input type="checkbox" name="observationGbn" value="캠핑카 매니아">Outdoor/Caravan maniac</label>
                                <label><input type="checkbox" name="observationGbn" value="학생">Students</label>
                                <label><input type="checkbox" name="observationGbn" value="일반관람">Leisure visitor</label>
                                <label><input type="checkbox" name="observationGbn" value="기타">Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Purpose of visit</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="visitPurpose" value="업계동향 파악 및 정보수집">Market research</label>
                                <label><input type="checkbox" name="visitPurpose" value="제품구매 및 기술도입 상담">Product / Technology purchasing consultation</label>
                                <label><input type="checkbox" name="visitPurpose" value="기존 거래업체 방문">Visit supplier company / clients</label>
                                <label><input type="checkbox" name="visitPurpose" value="차기 전시회 참가여부 파악">Survey on intention of participation to other exhibitions</label>
                                <label><input type="checkbox" name="visitPurpose" value="일반관람">General visit/sightseeing</label>
                                <label><input type="checkbox" name="visitPurpose" value="기타">Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Area of interest</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="interestItem" value="요트/보트">Yacht&Boat</label>
                                <label><input type="checkbox" name="interestItem" value="워크보트">Work boats</label>
                                <label><input type="checkbox" name="interestItem" value="낚시">Fishing</label>
                                <label><input type="checkbox" name="interestItem" value="다이빙">Diving</label>
                                <label><input type="checkbox" name="interestItem" value="카누/카약/무동력">Canoe / Kayak / Row boats</label>
                                <label><input type="checkbox" name="interestItem" value="워터스포츠">Watersports</label>
                                <label><input type="checkbox" name="interestItem" value="해양부품">Marine Equipment</label>
                                <label><input type="checkbox" name="interestItem" value="캠핑">Camping</label>
                                <label><input type="checkbox" name="interestItem" value="친환경">Eco Friendly Marine</label>
                                <label><input type="checkbox" name="interestItem" value="해양관광">Marine Tourism</label>
                                <label><input type="checkbox" name="interestItem" value="기타">Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item req">
                                <p>Where did you find us?</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="recognizePath" value="뉴스레터">E-mail newsletter</label>
                                <label><input type="checkbox" name="recognizePath" value="옥외광고물">Outdoor advertisement</label>
                                <label><input type="checkbox" name="recognizePath" value="홈페이지">Website</label>
                                <label><input type="checkbox" name="recognizePath" value="전문지">Specialized Magazine</label>
                                <label><input type="checkbox" name="recognizePath" value="온라인 커뮤니티">Blog, etc.</label>
                                <label><input type="checkbox" name="recognizePath" value="오프라인매장">Offline partner shop</label>
                                <label><input type="checkbox" name="recognizePath" value="소셜 네트워크">SNS</label>
                                <label><input type="checkbox" name="recognizePath" value="초청장">Invitation letter</label>
                                <label><input type="checkbox" name="recognizePath" value="방송광고">TV advertisement</label>
                                <label><input type="checkbox" name="recognizePath" value="KIBS 2024 참석">Participants to KIBS 2023</label>
                                <label><input type="checkbox" name="recognizePath" value="낚시박람회">Fishing Fair</label>
                                <label><input type="checkbox" name="recognizePath" value="기타">Others</label>
                                <div class="cmnt">Multiple choice available</div>
                            </div>
                        </li>
                        <li>
                            <div class="item">
                                <p>Whether you attended the last exhibition</p>
                            </div>
                            <div class="input check">
                                <label><input type="checkbox" name="preObservationGbn" value="first">One's first visit </label>
                                <label><input type="checkbox" name="preObservationGbn" value="2019">2019</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2020">2020</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2021">2021</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2022">2022</label>
                                <label><input type="checkbox" name="preObservationGbn" value="2023">2023</label>
                            </div>
                        </li>
                    </ul>
                </div>
                <!-- //설문항목 -->

            </form>

            <div class="form_btn_visitor">
                &lt;%&ndash;<a href="/eng/buyer/completed.do" class="btnSt01">&ndash;%&gt;
                <a href="javascript:void(0);" onclick="f_en_visitor_apply('I')" class="btnSt01">
                    <div class="big">Registration</div>
                </a>
            </div>

        </div>
    </div>
    <!-- //section -->

    <!-- 직장명 검색 팝업 -->
    <div class="popCompanyName popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">Company name search</div>
                    <a class="close" href="#close"><img src="/img/close_w.png"></a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <div class="searchBox">
                                <span class="search">
                                    <input type="text" id="search_companyName" placeholder="Company name">
                                    <button type="button" onclick="f_company_search()">
                                        <img src="/img/icon_search.png">
                                    </button>
                                </span>
                        </div>
                        <div class="directBox">
                            <div class="txt">If the registered company is not a business, enter it directly.</div>
                            <div class="btn btnSt01 directCompanyName">Enter</div>
                        </div>
                        <ul class="companyList">
                            &lt;%&ndash;<li>
                                <a href="">
                                    <div class="name">미팅팬2</div>
                                    <div class="address">경기도 부천시 원미구</div>
                                </a>
                            </li>&ndash;%&gt;
                        </ul>
                        <!-- <div class="companyNot">
                            No results were found for your search.
                        </div> -->
                    </div>
                </div>
            </div>
        </div>

        <div id="spinner">
            <div>
                <span>
                    <img src="https://cdnjs.cloudflare.com/ajax/libs/galleriffic/2.0.1/css/loader.gif">
                </span>
            </div>
        </div>

    </div>
    <!-- //직장명 검색 팝업 -->

    <!-- 직장명 직접 입력 팝업 -->
    <div class="popDirectName popForm popup">
        <div class="popup_inner">
            <div class="popup_wrap">
                <div class="pop_tit">
                    <div class="tit">Enter the company directly</div>
                    <a class="close" href="#close">
                        <img src="/img/close_w.png">
                    </a>
                </div>
                <div class="pop_box">
                    <div class="pop_cont">
                        <div class="form_s">
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>Company Name</p>
                                    </div>
                                    <div class="input">
                                        <input type="text" id="pop_companyName" placeholder="Company Name">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Address</p>
                                    </div>
                                    <div class="input address">
                                        <div class="address_box">
                                            <input type="text" id="pop_address" placeholder="Address">
                                            <input type="button" onclick="execDaumPostcode('pop_address','pop_addressDetail')" value="Search"><br>
                                            <div id="map" style="width:300px;height:300px;margin-top:10px;display:none">
                                            </div>
                                        </div>
                                        <div class="address_box" style="margin-top: 10px;">
                                            <input type="text" id="pop_addressDetail"  placeholder="Detailed Address">
                                        </div>
                                    </div>
                                </li>
                            </ul>
                            <a class="btnSave" onclick="f_company_info_direct_add()">Registration</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- //직장명 직접 입력 팝업 -->

</div>--%>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>