<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <style>
        #spinner { width: 100%; height: 100%; top: 0; left: 0; opacity: .6; background: silver; position: absolute; z-index: 9999; display: none;}
        #spinner div { width: 100%; height: 100%; display: table; }
        #spinner span { display: table-cell; text-align: center; vertical-align: middle; }
        #spinner img { background: white; padding: 1em; border-radius: .7em; }
    </style>
</head>

<body>

<c:choose>
    <c:when test="${mode eq 'soon'}">
        <script>
            alert('We are preparing to apply for 2027 KIBS buyer pre-registration.');
            window.location.href = '/eng/index.do';
        </script>
    </c:when>
    <c:when test="${mode eq 'close'}">
        <script>
            alert('The 2027 KIBS buyer pre-registration application deadline is now closed.');
            window.location.href = '/eng/index.do';
        </script>
    </c:when>
    <c:otherwise>

        <c:import url="../header.jsp" charEncoding="UTF-8"/>
        <script src="/js/custom/visitor_en.js?ver=20260630"></script>

        <%-- 25.02.25 참관신청 종료--%>
        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>Home</span><span>Buyers</span><span>Pre-registration</span>
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
                        <a href="javascript:void(0);" onclick="home('en');" class="iconHome"><img src="/img/icon_home.png"></a>
                        <div class="optionBox">
                            <div class="tabOpt1 tabOption">
                                <div class="tabOptAct">Buyers</div>
                                <div class="tabOptSel">
                                    <a href="/eng/guide/summary.do">KIBS 2027</a>
                                    <a href="/eng/exhibitor/categories.do">Exhibitors</a>
                                    <a class="active" href="/eng/buyer/glance.do">Buyers</a>
                                    <a href="/eng/board/notice.do">News</a>
                                </div>
                            </div>
                            <div class="tabOpt2 tabOption">
                                <div class="tabOptAct">Pre-registration</div>
                                <div class="tabOptSel">
                                    <a href="https://www.investkorea.org/ik-en/index.do" target="_blank">Visitor Guide</a>
                                    <a class="active" href="/eng/buyer/apply.do">Pre-registration</a>
                                    <a href="/eng/buyer/glance.do">Korea at a Glance</a>
                                    <a href="/eng/buyer/marina.do">Marine Industry in Korea</a>
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
            <div class="form_s padding_tb">
                <div class="inner">
                    <!-- 약관 -->
                    <div class="form_wrap">
                        <div class="form_tit">
                            <div class="big">Privacy Policy & Consent</div>
                        </div>
                        <div class="form_pri">
                            <div class="form_pri_box">
                                <div class="form_pri_titt">KINTEX Privacy Policy</div>
                                KINTEX (hereinafter referred to as the “Company”) establishes and discloses the following Privacy Policy in accordance with Article 30 of the Personal Information Protection Act in order to protect the personal information of data subjects and to promptly and effectively address related grievances.<br><br>

                                <div class="form_pri_titt">Article 1 (Purpose of Processing Personal Information)</div>
                                The Company processes personal information for the following purposes. The personal information being processed will not be used for any purpose other than those specified below. If the purpose of use changes, the Company will take necessary measures, including obtaining separate consent, in accordance with Article 18 of the Personal Information Protection Act.<br>
                                1. Website Membership Registration and Management<br>
                                The Company processes personal information for purposes including confirmation of membership registration intent, identification and authentication for member services, maintenance and management of membership status, identity verification under the limited verification system, prevention of unauthorized use of services, verification of parental consent when processing personal information of children under the age of 14, notifications and announcements, and complaint handling.<br>

                                2. Provision of Goods or Services<br>
                                The Company processes personal information for purposes including the provision of exhibition and convention services (such as booth participation and exhibition admission), content provision, customized services, identity verification, age verification, payment processing, and settlement.<br>

                                3. Complaint Handling<br>
                                The Company processes personal information for purposes including verification of the complainant’s identity, confirmation of complaint details, communication and notification for fact-finding investigations, and notification of processing results.
                                <br>
                                4. Marketing and Advertising<br>
                                The Company processes personal information for purposes including providing new and customized services, delivering event and promotional information, offering opportunities to participate in events, and providing services and advertisements based on demographic characteristics.<br>
                                The Company may also contact data subjects using such personal information for promotional activities or solicitation related to goods or services.<br>
                                5. Categories and Status of Personal Information Collected<br>
                                - Personal Information Collected: Login ID, name, telephone number, email address, password, age, gender, age group, region, company/organization name, survey results, and address<br>
                                - Collection Methods: Website, KINTEX App, pre-registration through organizer exhibition websites, written forms, online and offline events<br>
                                - Basis for Retention: Consent to the Privacy Policy upon membership registration (personal information for marketing purposes retained for five years)<br><br>

                                <div class="form_pri_titt">Article 2 (Processing and Retention Period of Personal Information)</div>
                                1. The Company processes and retains personal information within the retention and usage period prescribed by applicable laws or agreed to by the data subject at the time of collection.<br>
                                2. The retention periods for each category of personal information are as follows:<br>
                                1) Website Membership Registration and Management: Until membership withdrawal<br>
                                However, personal information may be retained until the relevant matter is resolved in the following cases:<br>
                                - Where investigations or inquiries related to violations of applicable laws are ongoing, until such investigations or inquiries are completed<br>
                                2) Provision of Goods or Services: Until the supply of goods or services and payment settlement have been completed<br>
                                However, personal information may be retained until the applicable retention period expires in the following cases:<br>
                                -  Records relating to labeling, advertising, contract details, and performance of contracts under the Act on Consumer Protection in Electronic Commerce, etc.<br>
                                • Records on labeling and advertising: 6 months<br>
                                • Records on contracts, subscription withdrawals, payment processing, and supply of goods: 5 years<br>
                                • Records on consumer complaints and dispute resolution: 3 years<br><br>

                                <div class="form_pri_titt">Article 3 (Provision of Personal Information to Third Parties)</div>
                                1. The Company processes personal information only within the scope specified in Article 1 (Purpose of Processing Personal Information). Personal information may be provided to third parties only with the consent of the data subject or where permitted under Article 17 of the Personal Information Protection Act and other applicable laws.
                                <br>
                                2. The Company may provide personal information to third parties as follows:<br>
                                - Recipient: Organizers of exhibitions and convention events using the free or paid services of the KINTEX App<br>
                                • Purpose of Use: Provision of free exhibition/convention services, paid payment services, joint event hosting, marketing, advertising, and related activities<br>
                                • Information Provided: User ID, name, mobile phone number, country/region, gender, age group, company/organization name, referrer (ID or name), and survey information related to each exhibition/convention event
                                <br><br>

                                - Recipient: Association of Korean Exhibition Industry (AKEI)<br>
                                • Purpose of Use: Provision of information for exhibition certification by the Association of Korean Exhibition Industry<br>
                                • Information Provided: User ID, name, mobile phone number, country/region, gender, age group, company/organization name, referrer (ID or name), and survey information related to each exhibition/convention event<br><br>

                                <div class="form_pri_titt">Article 4 (Entrustment of Personal Information Processing)</div>
                                1. The Company entrusts the following personal information processing tasks for efficient management of personal information.<br>
                                - Entrusting Party: KINTEX<br>
                                - Entrusted Party: SVM Planet<br>
                                - Scope of Entrusted Work: Management and operation of the Korea International Boat Show website<br>
                                - Entrustment Period: Until termination of the entrustment agreement<br>
                                2. When entering into an entrustment agreement, the Company specifies matters concerning responsibilities such as prohibition of personal information processing beyond the purpose of the entrusted work, technical and administrative safeguards, restrictions on re-entrustment, supervision and management of the entrusted party, and liability for damages in accordance with the Personal Information Protection Act. The Company also supervises whether the entrusted party processes personal information safely.<br>
                                3. If the details of the entrusted work or the entrusted party change, the Company will disclose such changes without delay through this Privacy Policy.<br><br>

                                <div class="form_pri_titt">Article 5 (Rights and Obligations of Data Subjects and Methods of Exercise)</div>
                                1. Data subjects may exercise the following rights related to personal information protection at any time:<br>
                                - Request access to personal information<br>
                                - Request correction of inaccurate information<br>
                                - Request deletion of personal information<br>
                                - Request suspension of processing<br>
                                2. These rights may be exercised through written requests, telephone, email, fax, or other methods, and the Company will take action without delay.<br>
                                3. If a data subject requests correction or deletion of personal information, the Company shall not use or provide the relevant personal information until such correction or deletion has been completed.<br>
                                4. The rights under Paragraph 1 may also be exercised through a legal representative or authorized agent. In such cases, a power of attorney in the form prescribed by the Enforcement Rules of the Personal Information Protection Act must be submitted.<br>
                                5. Data subjects shall not infringe upon their own or others’ personal information and privacy by violating the Personal Information Protection Act or other applicable laws.<br><br>

                                <div class="form_pri_titt">Article 6 (Categories of Personal Information Processed)</div>
                                The Company processes the following categories of personal information:<br>
                                1. Website/App Membership Registration and Management<br>
                                - Required Information: User ID, name, mobile phone number, country/region, gender, age group<br>
                                - Optional Information: Company/organization affiliation, referrer (ID or name), survey responses<br>
                                2. Provision of Services<br>
                                - Required Information: User ID, name, mobile phone number, country/region, gender, age group<br>
                                - Optional Information: Company/organization affiliation, referrer (ID or name), survey responses, previous purchase history<br>
                                3. During the use of Internet services, the following information may be automatically generated and collected:<br>
                                - IP address, cookies, MAC address, service usage records, visit history, records of improper use, and similar information<br><br>

                                <div class="form_pri_titt">Article 7 (Destruction of Personal Information)</div>
                                1. The Company shall promptly destroy personal information when it becomes unnecessary, such as upon expiration of the retention period or achievement of the purpose of processing.<br>
                                2. If personal information must be retained in accordance with other applicable laws even after the retention period agreed upon by the data subject has expired or the purpose of processing has been achieved, such personal information shall be transferred to a separate database (DB) or stored in a different location.<br>
                                3. The procedures and methods for destroying personal information are as follows:<br>
                                - Destruction Procedure: The Company shall immediately destroy personal information when grounds for destruction arise.<br>
                                - Destruction Method: Personal information stored in electronic file form shall be destroyed using methods such as deletion of database records to prevent recovery. Personal information recorded or stored in paper documents shall be destroyed by shredding or incineration.<br><br>

                                <div class="form_pri_titt">Article 8 (Measures to Ensure the Security of Personal Information)</div>
                                The Company takes the following measures to ensure the security of personal information:<br>
                                1. Administrative Measures: Establishment and implementation of internal management plans, regular employee training, etc.<br>
                                2. Technical Measures: Management of access rights to personal information processing systems, access control, encryption of unique identification information, installation of security software, etc.<br>
                                3. Physical Measures: Access control to computer rooms, document storage rooms, and other restricted areas.<br><br>

                                <div class="form_pri_titt">Article 9 (Installation, Operation, and Refusal of Automatic Personal Information Collection Devices)</div>
                                1. The Company may use “cookies” to store and retrieve user information in order to provide customized services.<br>
                                2. Cookies are small amounts of information sent by the website server (HTTP) to the user's web browser and may be stored on the hard drive of the user's computer.<br>
                                - Purpose of Cookie Use: Cookies are used to analyze visit patterns, service usage, popular search terms, security access status, and other information regarding services and websites visited by users in order to provide optimized information.<br>
                                - Installation, Operation, and Refusal of Cookies: Users may refuse the storage of cookies by adjusting the settings under Tools > Internet Options > Privacy in their web browser.<br>
                                - Refusing the storage of cookies may result in difficulties in using customized services.<br><br>

                                <div class="form_pri_titt">Article 10 (Chief Privacy Officer)</div>
                                1. The Company designates the following personnel as responsible for overseeing personal information processing and handling complaints and remedies related to personal information protection.<br>
                                ▶ Chief Privacy Officer: Executive Vice President, Business Division<br>
                                ▶ Personal Information Protection Manager<br>
                                Department: Exhibition Business Team 3<br>
                                Manager: Ki-Hoon Koo, Team Leader<br>
                                Contact: +82-31-995-8771 / ghkoo@kintex.com<br><br>

                                ▶ Personal Information Protection Officer<br>
                                Department: Exhibition Business Team 3<br>
                                Officer: Byung-Hoon Oh, Deputy General Manager<br>
                                Contact: +82-31-995-8777 / sky@kintex.com<br><br>

                                2. Data subjects may contact the department responsible for personal information protection regarding any inquiries, complaints, or requests for remedies arising from the use of the Company's services or business activities. The Company will respond and process such inquiries without delay.<br><br>

                                <div class="form_pri_titt">Article 11 (Request for Access to Personal Information)</div>
                                Data subjects may request access to their personal information pursuant to Article 35 of the Personal Information Protection Act through the department listed below. The Company will make every effort to ensure that such requests are processed promptly.<br>
                                ▶ Department Responsible for Receiving and Processing Requests for Access to Personal Information: Exhibition Business Team 2<br><br>

                                <div class="form_pri_titt">Article 12 (Remedies for Infringement of Rights and Interests)</div>
                                Data subjects may contact the following organizations for consultation, dispute resolution, or remedies related to personal information infringement.<br>
                                (The organizations listed below are independent of the Company. If you are not satisfied with the Company's handling of personal information complaints or remedies, or if you require further assistance, please contact the relevant organization directly.)<br><br>

                                ▶ Personal Information Infringement Report Center (operated by the Korea Internet & Security Agency, KISA)<br>
                                - Responsibilities: Reporting personal information infringements and requesting consultation<br>
                                - Website: privacy.kisa.or.kr<br>
                                - Phone: 118 (without area code)<br>
                                - Address: Personal Information Infringement Report Center, 3rd Floor, 9 Jinheung-gil, Naju-si, Jeollanam-do 58324, Republic of Korea<br><br>

                                ▶ Personal Information Dispute Mediation Committee<br>
                                - Responsibilities: Personal information dispute mediation and collective dispute mediation (civil resolution)<br>
                                - Website: www.kopico.go.kr<br>
                                - Phone: 1833-6972 (without area code)<br>
                                - Address: 4th Floor, Government Complex-Seoul, 209 Sejong-daero, Jongno-gu, Seoul 03171, Republic of Korea<br><br>

                                ▶ Cyber Crime Investigation Division, Supreme Prosecutors’ Office: 02-3480-3573 (www.spo.go.kr)<br><br>

                                ▶ Cyber Bureau, National Police Agency: 182 (http://cyberbureau.police.go.kr)<br><br>

                                <div class="form_pri_titt">Article 13 (Installation and Operation of Video Information Processing Devices)</div>
                                1. KINTEX installs and operates video information processing devices as follows:<br>
                                - Purpose of Installation and Operation: Facility security and fire prevention at KINTEX<br>
                                - Number of Devices, Installation Locations, and Recording Scope: Installed in major facilities such as exhibition hall lobbies and exhibition halls, recording all areas of major facilities<br>
                                - Responsible Department and Authorized Personnel with Access to Video Information: Facility Operations Team<br>
                                - Recording Hours, Retention Period, Storage Location, and Processing Method<br>
                                • Recording Hours: 24-hour continuous recording<br>
                                • Storage Location and Processing Method: Stored and managed in the video information control room<br>
                                - Method and Location for Accessing Video Information: Facility Operations Team<br>
                                - Procedures for Requests to Access Video Information: Requests must be submitted using a Personal Video Information Access/Existence Confirmation Request Form. Access is granted only when the data subject appears in the footage or when access is clearly necessary to protect the life, body, or property interests of the data subject.<br>
                                - Technical, Administrative, and Physical Measures for Protecting Video Information: Establishment of internal management plans, access control and restrictions, secure storage and transmission technologies, maintenance of processing records and prevention of forgery or alteration, provision of secure storage facilities and locking devices, etc.<br><br>

                                <div class="form_pri_titt">Article 14 (Consent to Photography and Video Recording)</div>
                                1) Photography and video recording may take place at the exhibition venue.<br>
                                2) Purpose of Recording<br>
                                ① Promotion of this event and future exhibitions and conferences<br>
                                ② Inclusion in exhibition-related press releases and official documents<br>
                                ③ Use in promotional materials for the Korea International Boat Show<br>
                                3) Recorded materials will not be used for any purpose other than those specified above.<br><br>

                                * You have the right to refuse consent to the collection and use of personal information and the provision of personal information to third parties. However, if you refuse to provide consent, certain services may be restricted.
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
                            <div class="big">Third-Party Information Sharing Consent</div>
                        </div>
                        <div class="form_pri">
                            <div class="form_pri_box">
                                ① Recipient of Personal Information: Participating companies of the 2027 Korea International Boat Show<br>
                                ② Purpose of Use by the Recipient: Visitor consultation, customer management, marketing and sales activities, and analysis of visitor interests<br>
                                ③ Personal Information Provided: Name, phone number, email address, company/organization name, country/region, gender, age group, items of interest, and survey responses<br>
                                ④ Retention and Use Period by the Recipient: Two (2) years from the date of provision<br>
                                ⑤ You have the right to refuse consent to the provision of your personal information to third parties. However, if you refuse to provide consent, certain consultation or business matching services may be limited.
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
                            <div class="big">Safety Compliance Agreement</div>
                        </div>
                        <div class="form_pri">
                            <div class="form_pri_box">
                                1. Visitors must not lean on, apply force to, or damage any structures, installations, exhibits, or products within the exhibition venue or exhibitor booths.<br>
                                2. When viewing or using any structures, installations, exhibits, or products within the exhibition venue or exhibitor booths, visitors must take due care to prevent accidents, follow the instructions of event staff, and comply with all safety guidelines.<br><br>
                                I have read and understood the above safety guidelines. The Organizer shall not be held responsible for any accidents, injuries, or damages resulting from failure to comply with these guidelines.
                            </div>
                            <div class="form_pri_agree">
                                <label><input type="radio" name="agree3" value="Y">Agree</label>
                                <label><input type="radio" name="agree3" value="N">Disagree</label>
                            </div>
                        </div>
                    </div>
                    <!-- //약관 -->

                    <!-- 안내문구 -->
                    <div class="form_wrap">
                        <div class="form_pri">
                            <div class="form_pri_item">
                                &lt;Early Registration (Round 1)&gt;<br>
                                - Period : January 1, 2026 – February 14, 2027<br>
                                - Fee : Free<br><br>
                                &lt;Early Registration (Round 2)&gt;<br>
                                - Period : February 15 – March 9, 2027<br>
                                - Fee : KRW 5,000 (50% discount) * Payment must be made on-site.<br>

                                <br>
                                ※ A confirmation email will be sent after registration. Please enter your information accurately.<br>
                                ※ Pre-registration is valid for one admission day during March 12–14, 2027.
                            </div>
                        </div>
                    </div>
                    <!-- //안내문구 -->

                    <!--begin::Form-->
                    <form id="joinForm" name="joinForm" method="post" onsubmit="return false;">

                        <!-- 참관 구분 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Visitor Type</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="input check w-100">
                                        <label><input type="radio" name="partGbn" value="바이어">Buyer</label>
                                        <label><input type="radio" name="partGbn" value="일반관람">General Visitor</label>
                                        <div class="cmnt">Visitor type cannot be changed after registration.</div>
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <!-- 행사 구분 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Event</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="input check w-100">
                                        <label><input type="radio" name="eventGbn" value="경기국제보트쇼">Korea International Boat Show(KIBS)·Korea International Fishing Show(KOFISH)</label>
                                        <label><input type="radio" name="eventGbn" value="코리아서프쇼">Korea International Surf Show(KISS)</label>
                                        <label><input type="radio" name="eventGbn" value="해양관광전">Korea Marine Travel Show(KMTS)</label>
                                        <div class="cmnt">Please select the event you will attend.</div>
                                    </div>
                                </li>
                            </ul>
                        </div>

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
                                        <select id="telCode">
                                            <option value="" selected disabled hidden>Select</option>
                                            <option value="+82">+82 (KOR)</option>
                                            <option value="+1">+1 (USA/CAN)</option>
                                            <option value="+44">+44 (GBR)</option>
                                            <option value="+49">+49 (DEU)</option>
                                            <option value="+61">+61 (AUS)</option>
                                            <option value="+81">+81 (JPN)</option>
                                            <option value="+86">+86 (CHN)</option>
                                            <option value="+91">+91 (IND)</option>
                                        </select>
                                        <input type="tel" id="tel" name="tel" class="onlyGeneralTel" maxlength="20" placeholder="Numbers only. Hyphens are added automatically.">
                                    </div>
                                </li>
                                <li class="w50">
                                    <div class="item req">
                                        <p>Phone</p>
                                    </div>
                                    <div class="input">
                                        <select id="phoneCode">
                                            <option value="" selected disabled hidden>Select</option>
                                            <option value="+82">+82 (KOR)</option>
                                            <option value="+1">+1 (USA/CAN)</option>
                                            <option value="+44">+44 (GBR)</option>
                                            <option value="+49">+49 (DEU)</option>
                                            <option value="+61">+61 (AUS)</option>
                                            <option value="+81">+81 (JPN)</option>
                                            <option value="+86">+86 (CHN)</option>
                                            <option value="+91">+91 (IND)</option>
                                        </select>
                                        <input type="tel" id="phone" name="phone" class="onlyTel" inputmode="numeric"
                                               pattern="[0-9]*"
                                               autocomplete="off" placeholder="Numbers only. Hyphens are added automatically.">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>E-mail</p>
                                    </div>
                                    <div class="input email">
                                        <input type="text" id="email" name="email" placeholder="E-mail" class="email_input1">
                                        <span>@</span>
                                        <input type="text" id="domain" name="domain" placeholder="Domain" class="email_input2">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req" id="companyNameDiv">
                                        <p>Company/Organization Name</p>
                                    </div>
                                    <div class="input companyNameBox" style="flex-wrap: wrap">
                                        <input class="companyNameInput" type="text" id="companyName" name="companyName" placeholder="Enter your company or organization name">
                                        <input type="text" id="companyAddress" name="companyAddress" placeholder="Enter your company address">
                                    </div>
                                </li>
                                <li>
                                    <div class="item">
                                        <p>Accompanying Minor</p>
                                    </div>
                                    <div class="input check">
                                        <label><input type="radio" name="partnerYn" id="Y" value="Y">Yes</label>
                                        <label><input type="radio" name="partnerYn" id="N" value="N" checked>No</label>
                                        <div class="cmnt">Only accompanying minors (aged 0-18) may be registered.</div>
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
                                        <input type="text" name="partnerAge" class="onlyChildAge" maxlength="2" placeholder="Age (0~18 years old)">
                                    </div>
                                </li>
                            </ul>
                            <div class="formAddBtn"><span class="visitPartnerAdd">Add</span></div>
                        </div>
                        <!-- //참관객 정보 -->

                        <!-- 설문항목 -->
                        <div class="form_wrap">
                            <div class="form_tit">
                                <div class="big">Survey</div>
                            </div>
                            <ul class="form_box">
                                <li>
                                    <div class="item req">
                                        <p>Gender</p>
                                    </div>
                                    <div class="input check">
                                        <label><input type="radio" name="sex" value="남자">Male</label>
                                        <label><input type="radio" name="sex" value="여자">Female</label>
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Country / Region</p>
                                    </div>
                                    <div class="input">
                                        <!-- 국가 선택 -->
                                        <select name="country" class="w50" style="line-height: 1;">
                                            <option value="" disabled selected hidden>Select Country</option>
                                            <option value="United States">United States</option>
                                            <option value="United Kingdom">United Kingdom</option>
                                            <option value="Australia">Australia</option>
                                            <option value="China">China</option>
                                            <option value="Japan">Japan</option>
                                            <option value="South Korea">South Korea</option>
                                            <!-- 필요에 따라 주요 국가 옵션 추가 -->
                                            <option value="Other">Other</option>
                                        </select>

                                        <!-- 주/지역 텍스트 입력 (기존 region_si 컬럼 활용) -->
                                        <input type="text" name="sido" class="w50" placeholder="State / Province / Region" value="${info.regionSi}">
                                    </div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Age Group</p>
                                    </div>
                                    <div class="input check">
                                        <label><input type="radio" name="ageGroup" value="10">Teenager</label>
                                        <label><input type="radio" name="ageGroup" value="20">20s</label>
                                        <label><input type="radio" name="ageGroup" value="30">30s</label>
                                        <label><input type="radio" name="ageGroup" value="40">40s</label>
                                        <label><input type="radio" name="ageGroup" value="50">50s</label>
                                        <label><input type="radio" name="ageGroup" value="60">60+</label>
                                    </div>
                                </li>
                                <li id="observationGbnDiv">
                                    <div class="item req">
                                        <p>Viewing Categories</p>
                                    </div>
                                    <div class="input check" id="ul_observationGbn"></div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p id="visitPurposeTitle">Purpose of Visit</p>
                                    </div>
                                    <div class="input check" id="ul_visitPurpose"></div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>Items of interest</p>
                                    </div>
                                    <div class="input check" id="ul_interestItem"></div>
                                </li>
                                <li>
                                    <div class="item req">
                                        <p>How did you hear about KIBS?</p>
                                    </div>
                                    <div class="input check" id="ul_recognizePath"></div>
                                </li>
                                <li id="preObservationGbnLi" style="display:none;">
                                    <div class="item">
                                        <p>Have you visited KIBS before?</p>
                                    </div>
                                    <div class="input check" id="ul_preObservationGbn"></div>
                                </li>
                            </ul>
                        </div>
                        <!-- //설문항목 -->

                    </form>

                    <div class="form_btn_visitor">
                        <a href="javascript:void(0);" onclick="f_visitor_apply('I')" class="btnSt01">
                            <div class="big">Submit Pre-registration</div>
                        </a>
                    </div>

                </div>
            </div>
            <!-- //section -->
        </div>

        <!-- 직장명 검색 팝업 -->
        <div class="popCompanyName popForm popup">

            <div class="popup_inner">
                <div class="popup_wrap">
                    <div class="pop_tit">
                        <div class="tit">Search by workplace name</div>
                        <a class="close" href="#close">
                            <img src="/img/close_w.png">
                        </a>
                    </div>
                    <div class="pop_box">
                        <div class="pop_cont">
                            <div class="searchBox">
                                <span class="search">
                                    <input type="text" id="search_companyName" placeholder="Enter workplace name">
                                    <button type="button" onclick="f_company_search()">
                                        <img src="/img/icon_search.png">
                                    </button>
                                </span>
                            </div>
                            <div class="directBox">
                                <div class="txt">If you do not have a registered workplace name<br>Please enter it yourself.</div>
                                <div class="btn btnSt01 directCompanyName">Direct Input</div>
                            </div>
                            <ul class="companyList">
                                <%--<li>
                                    <a href="">
                                        <div class="name">미팅팬2</div>
                                        <div class="address">경기도 부천시 원미구</div>
                                    </a>
                                </li>--%>
                            </ul>
                            <!-- <div class="companyNot">
                                검색결과가 없습니다.
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
                        <div class="tit">Enter your workplace name directly</div>
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
                                            <input type="text" id="pop_companyName" placeholder="Enter company name">
                                        </div>
                                    </li>
                                    <li>
                                        <div class="item req">
                                            <p>Address</p>
                                        </div>
                                        <div class="input address">
                                            <div class="address_box">
                                                <input type="text" id="pop_address" placeholder="Enter address">
                                                <input type="button" onclick="execDaumPostcode('pop_address','pop_addressDetail')" value="Search"><br>
                                                <div id="map1" style="width:300px;height:300px;margin-top:10px;display:none"></div>
                                            </div>
                                            <div class="address_box" style="margin-top: 10px;">
                                                <input type="text" id="pop_addressDetail" placeholder="Enter detailed address">
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                                <a class="btnSave" onclick="f_company_info_direct_add()">Add</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- //직장명 직접 입력 팝업 -->

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

        <script>
            $(function(){
                // 1. 행사 구분 변경 이벤트
                $('input[name=eventGbn]').on('change', function() {
                    if(typeof renderSurveyItems === 'function'){
                        renderSurveyItems($(this).val(), null);
                    }
                });

                // 2. 초기 로딩 시: 행사 구분 기본값 설정 (경기국제보트쇼)
                let initialEvent = $('input[name=eventGbn]:checked').val();
                if (!initialEvent) {
                    initialEvent = '경기국제보트쇼';
                    $('input[name=eventGbn][value="경기국제보트쇼"]').prop('checked', true);
                }

                // 설문 항목 렌더링
                if(typeof renderSurveyItems === 'function') {
                    renderSurveyItems(initialEvent, null);
                }

                // 3. 참관 구분 변경 이벤트
                $('input[type=radio][name=partGbn]').on('change', function(){
                    let partGbn = $(this).val();
                    if(partGbn === '바이어'){
                        $('#observationGbnDiv').hide();
                        $('#companyNameDiv').addClass('req');
                    }else{
                        $('#observationGbnDiv').show();
                        $('#companyNameDiv').removeClass('req');
                    }
                });

                // 4. [요청사항 적용] 참관 구분 기본값 설정 (바이어)
                let currentPartGbn = $('input[name=partGbn]:checked').val();
                if(!currentPartGbn) {
                    // 바이어를 기본으로 선택하고 change 이벤트 트리거 (UI 동기화)
                    $('input[name=partGbn][value="바이어"]').prop('checked', true).trigger('change');
                } else {
                    // 이미 선택된 값이 있다면 해당 값으로 UI 동기화
                    $('input[name=partGbn][value="' + currentPartGbn + '"]').trigger('change');
                }
            });
        </script>
    </c:otherwise>
</c:choose>

</body>
</html>