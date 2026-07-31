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

    <c:if test="${sessionScope.get('status') ne 'logon'}">
        <script>
            alert("Please log in.");
            location.href = '/eng/login.do';
        </script>
    </c:if>

    <c:if test="${sessionScope.get('status') eq 'logon'}">

        <c:import url="../header.jsp" charEncoding="UTF-8"/>

        <div id="container">

            <!-- section -->
            <div class="sub_top">
                <div class="inner">
                    <div class="sub_top_box">
                        <div class="sub_top_nav">
                            <span>Home</span><span>Exhibitors</span><span>MYPAGE</span>
                        </div>
                        <div class="sub_top_tit">MYPAGE</div>
                    </div>
                </div>
            </div>
            <!-- //section -->

            <!-- section -->
            <div class="apply_s padding_tb" id="apply_s">
                <div class="inner">

                    <div class="apply_nav">
                        <div class="apply_nav_txt">
                            <div class="txt">Welcome, ${info.companyNameEn}</div>
                            <div class="btn">
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/modify.do','${info.seq}')" class="btnSt01">Member Info</a>
                                <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/index.do', {seq: `${info.seq}`})" class="btnSt01">MYPAGE</a>
                            </div>
                        </div>
                        <div class="apply_nav_list">
                            <ul class="list1">
                                <li class="active"><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step01.do','${info.seq}')">Basic Info</a></li>
                                <li>
                                    <a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Exhibition Info</a>
                                    <ul class="list2">
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_1.do','${info.seq}')">Booth</a></li>
                                        <%--<li><a href="javascript:void(0);" onclick="f_page_move('/mypage/step2_10.do','${info.seq}')">해상전시회 신청</a></li>--%>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_9.do','${info.seq}')">Yacht/Boat</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_2.do','${info.seq}')">Signboard</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_3.do','${info.seq}')">Utilities</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_4.do','${info.seq}')">Badges</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_5.do','${info.seq}')">Giveaways</a></li>
                                        <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step2_8.do','${info.seq}')">Directory</a></li>
                                    </ul>
                                </li>
                                <li><a href="javascript:void(0);" onclick="f_page_move('/eng/mypage/step03.do','${info.seq}')">Complete</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- section -->
                    <div class="my_form form_s">

                        <form id="exhibitor_apply_form" method="post" onsubmit="return false;" enctype="multipart/form-data">
                            <div class="inner">

                                <div class="apply_tit">기본 정보</div>

                                <!-- 약관 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Exhibition Participation Regulations</div>
                                    </div>
                                    <div class="form_pri">
                                        <div class="form_pri_box">

                                            <div class="form_pri_titt">Article 1 (Definitions)</div>
                                            1. The “Exhibition” refers to the “2027 Korea International Boat Show (KIBS 2027)”.<br>
                                            2. The “Organizers” refer to Gyeonggi Province and the co-hosting organizations.<br>
                                            3. The “Organizer” refers to KINTEX Co., Ltd., which manages and operates the Exhibition.<br>
                                            4. The “Exhibitor” refers to any company, association, organization, or individual that has applied to participate in the Exhibition and paid the required deposit or participation fee.<br><br>

                                            <div class="form_pri_titt">Article 2 (Application and Contract)</div>
                                            1. An exhibitor wishing to participate in the Exhibition shall complete the online booth application and pay the full participation fee according to the invoice issued by the Organizer. The application shall be considered complete upon such payment.<br>
                                            2. If the application is not completed in accordance with Paragraph 1 of this Article, the applicant shall not be permitted to participate in any Exhibition-related activities.<br>
                                            3. Participation fees are USD 3,000 per booth for a raw space booth and USD 3,300 per booth for a shell scheme booth. Raw space booths require a minimum application of two (2) booths.
                                            4. After the exhibitor’s application has been reviewed and approved by the Secretariat, an invoice will be issued accordingly. The exhibitor shall pay 100% of the participation fee within seven (7) days from the date of invoice issuance.<br>
                                            5. All overseas bank transfer fees, including intermediary and receiving bank charges, shall be borne by the exhibitor. The full invoiced participation fee must be received by the Organizer.<br>
                                            6. VAT may vary depending on the exhibitor’s country and the applicable Korean tax regulations. Exhibitors shall refer to the invoice issued by the Secretariat for the final payable amount.<br>
                                            7. Submission of the application form does not constitute final confirmation of participation. Participation shall be confirmed only after the application has been reviewed and approved by the Secretariat and the required payment has been completed.<br>
                                            8. Utility services shall be applied for separately according to the exhibitor’s needs after full payment of the participation fee. The Organizer shall provide the requested services upon receipt of the full service fee according to the invoice issued by the Organizer.<br>
                                            9. Utility services and shell scheme booth construction shall be provided by the Organizer upon request. Any changes to the application or utility service request must be immediately reported to the Organizer by email. The exhibitor shall be responsible for any disadvantages resulting from failure to provide such notice.<br>
                                            10. Any changes to the information provided in the application form must be immediately reported in writing to the Organizer. The exhibitor shall be responsible for any disadvantages resulting from failure to provide such notice.<br>
                                            11. The Organizer reserves the right to reject an application if exhibition space is fully booked or if the proposed exhibits are deemed unsuitable for the Exhibition.<br><br>

                                            <div class="form_pri_titt">Article 3 (Payment Terms)</div>
                                            1. The exhibitor shall pay the full participation fee within fourteen (14) days of submitting the application form. However, applications submitted on or after February 1, 2027, must be accompanied by full payment at the time of application.<br>
                                            2. If the exhibitor fails to pay the balance by the designated deadline, the Organizer may terminate the participation agreement. In such cases, any participation fees already paid shall not be refundable.<br><br>

                                            <div class="form_pri_titt">Article 4 (Allocation of Exhibition Space and Booth Location)</div>
                                            1. Booth locations shall be assigned by the Organizer based on the order of application, characteristics of the exhibits, requested booth size, and other reasonable criteria. Exhibitors shall not raise objections to such assignments.<br>
                                            2. The Organizer may change booth locations, sizes, or arrangements as necessary for efficient exhibition management. Exhibitors shall cooperate with such changes except in cases of force majeure.<br>
                                            3. Without prior written approval from the Organizer, exhibitors may not sublease, transfer, exchange, or otherwise assign all or part of their allocated exhibition space to another exhibitor.<br><br>

                                            <div class="form_pri_titt">Article 5 (Booth Installation and Exhibit Display)</div>
                                            1. Exhibitors shall complete booth installation, exhibit setup, and display arrangements within the designated period and within the allocated exhibition space. Additional work outside official working hours shall be subject to overtime charges (rental fee). (KRW 1,000,000 per hour, VAT excluded)<br>
                                            2. All booth construction work shall be carried out by contractors officially registered with KINTEX.<br><br>

                                            <div class="form_pri_titt">Article 6 (Removal of Exhibits and Booth Structures)</div>
                                            1. Exhibitors shall remove all exhibits and booth structures within the designated period. Any costs incurred by the Organizer due to delayed removal shall be borne by the exhibitor.<br>
                                            2. During move-in and move-out periods, vehicles may not stop or park in loading/unloading areas except for loading and unloading operations. Vehicles must leave immediately after cargo handling is completed to avoid obstructing traffic flow.<br>
                                            3. Passenger cars and vans are not permitted to enter the freight parking area during move-in and move-out periods.<br><br>

                                            <div class="form_pri_titt">Article 7 (Exhibition Hall Management)</div>
                                            1. Exhibitors shall display the exhibits specified in their application and assign on-site staff to properly manage their booth. If, due to the exhibitor's circumstances, the exhibits cannot be displayed or on-site staff cannot be assigned, the exhibitor shall notify the Organizer in writing. In such cases, the Organizer may cancel the allocated booth, and the participation fee shall not be refunded.<br>
                                            2. If an exhibitor displays items different from those specified in the application, exhibits items deemed unsuitable for the Exhibition, or conducts direct sales activities without the Organizer's approval, the Organizer may immediately order suspension, removal, or withdrawal of the exhibits. In such cases, the participation fee shall not be refunded, and the exhibitor shall not be entitled to any compensation.<br>
                                            3. The Organizer may restrict access to the Exhibition Hall by specific individuals if deemed necessary.<br>
                                            4. Exhibitors shall not alter the original condition of the exhibition hall, including painting, nailing, or modifying floors, ceilings, pillars, or walls. Any damage caused to the exhibition hall shall be restored at the exhibitor's expense, and the exhibitor shall compensate the Organizer for any resulting losses.<br><br>

                                            <div class="form_pri_titt">Article 8 (Termination of Contract)</div>
                                            1. If an exhibitor refuses to use all or part of the allocated booth space or fails to pay the participation fee within the specified deadline, the Organizer may unilaterally terminate the participation agreement. In such cases, any participation fees already paid shall not be refunded. Participation fees shall also not be refunded if the exhibitor withdraws without the Organizer's approval.<br>
                                            2. If an exhibitor wishes to cancel participation after submitting the application, a written cancellation request (official document) must be submitted to the Organizer. The exhibitor shall pay the cancellation penalty specified below within seven (7) days of cancellation.<br>
                                            Provided that any participation fee already paid shall be deducted from the penalty amount. If the penalty exceeds the amount paid, the exhibitor shall pay the difference; if the amount paid exceeds the penalty, the balance shall be refunded.<br>
                                            - Cancellation by December 31, 2026: 50% of the total participation fee shall be paid as a cancellation penalty.<br>
                                            - Cancellation from January 1, 2027 to January 31, 2027: 80% of the total participation fee shall be paid as a cancellation penalty.<br>
                                            - Cancellation on or after February 1, 2027: 100% of the total participation fee shall be paid as a cancellation penalty.<br><br>

                                            <div class="form_pri_titt">Article 9 (Cancellation or Modification of the Exhibition)</div>
                                            1. If the Organizer cancels the Exhibition due to an administrative order or other internal reasons, the full participation fee already paid shall be refunded to the exhibitor.<br>
                                            2. If the exhibition venue is closed by administrative order during the Exhibition period, making it impossible to continue the Exhibition, the participation fee shall be refunded on a prorated basis according to the affected exhibition days (three days).<br>
                                            3. Except for the circumstances described in Paragraph 1, participation fees shall not be refunded in the event of force majeure or other special circumstances not attributable to the Organizer, including cancellation, postponement, rescheduling, or reduction of the Exhibition. In such cases, exhibitors shall not be entitled to claim compensation from the Organizer.<br><br>

                                            <div class="form_pri_titt">Article 10 (Safety Management)</div>
                                            1. Exhibitors shall inspect their exhibits and booth environment before opening hours to ensure safe event operation and shall inspect and manage all safety-related elements at the close of each day.<br>
                                            2. To maintain a safe exhibition environment, the Organizer shall provide security services during booth installation, exhibition operation, and dismantling periods. Exhibitors and service providers shall comply with the instructions of security personnel.<br>
                                            3. Exhibitors shall take appropriate measures, including obtaining insurance coverage, to protect against personal injury and property damage when necessary.<br>
                                            4. Exhibitors shall bear full responsibility for all accidents occurring within the exhibition venue and for the protection and loss of exhibits, equipment, and facilities. In the event of an incident, the exhibitor shall immediately notify the Secretariat and indemnify the Secretariat from any liability.<br>
                                            5. Exhibitors shall protect all facilities within the exhibition venue. Any contamination, breakage, damage, or destruction caused by the exhibitor's negligence (including contractors engaged by the exhibitor) shall be restored in accordance with venue regulations at the exhibitor's expense.<br>
                                            6. Exhibitors shall inform their designated contractors of all KINTEX regulations in advance and submit safety guidelines for on-site representatives by event category to the Secretariat within the designated period. Exhibitors shall be fully responsible for the management of their contractors and for any consequences arising from their contractors' failure to comply with regulations.<br>
                                            7. Appropriate safety barriers and protective facilities shall be installed around areas where live demonstrations of exhibits are conducted.<br>
                                            8. The entry of hazardous materials, including explosives and flammable substances, into the exhibition venue is prohibited. However, if such materials are essential for operating an exhibit, an "Application for Approval to Use Hazardous Materials" must be submitted to the Secretariat within the designated period.<br>
                                            9. When using electricity, gas, compressed air, water supply, or drainage systems, the responsible contractor shall regularly inspect booth facilities and test operations to prevent accidents. At the end of each exhibition day, exhibitors shall verify that all necessary safety measures have been implemented before leaving the venue. If defective materials or unsafe conditions are identified, immediate corrective action shall be taken and the Secretariat shall be notified without delay.<br><br>

                                            <div class="form_pri_titt">Article 11 (Security, Risk, and Insurance)</div>
                                            1. The Organizer shall provide security services during booth installation, the exhibition period, and dismantling by contracting with a professional security company for access control and order maintenance within the exhibition venue. The exhibiting company shall bear ultimate responsibility for its exhibits and other property and shall not be entitled to claim compensation for theft, damage, or loss. Therefore, exhibitors shall obtain insurance coverage and take appropriate measures against damage or loss of exhibits and property during installation, exhibition, and dismantling periods, and shall exercise due care in managing their booths and exhibits.<br>
                                            2. The Organizer shall not be liable for any loss or damage caused by force majeure, including natural disasters, arson, theft, vandalism, or other acts committed by third parties, except where such loss or damage results from the negligence of the Secretariat.<br>
                                            3. All civil, criminal, and administrative liabilities arising from defects in the exhibitor’s products or services, or from any exhibition-related activities, including product liability and claims for damages, shall be the sole responsibility of the exhibiting company. If a third party asserts claims against the Secretariat due to the exhibitor’s fault, including product liability or damages arising from the exhibition, the exhibitor shall indemnify and hold the Secretariat harmless by taking all necessary actions, including complaint handling, litigation, and other legal proceedings. Furthermore, if the Secretariat incurs expenses in responding to such claims, the exhibitor shall promptly reimburse those expenses. This shall not apply where the loss or damage is clearly attributable to defects in the exhibition venue itself or other causes for which the venue or Secretariat is responsible.<br>
                                            4. The exhibiting company shall be responsible for all incidents and accidents arising in connection with its participation during booth installation, the exhibition period, and dismantling. In the event of an accident, the exhibitor shall immediately notify the Secretariat and make every effort to take appropriate response measures. If the exhibitor intentionally or negligently causes fire, theft, damage, or any other incident resulting in loss to the Secretariat, the venue, or any third party, the exhibitor shall be liable for all resulting damages.<br><br>

                                            <div class="form_pri_titt">Article 12 (Booth Installation Restrictions and Fire Prevention Rules)</div>
                                            1. The height of all structures and installations shall not exceed the maximum height designated by the Organizer, taking into account the scale and location of the exhibition.<br>
                                            2. All materials used for structures and installations within the exhibition hall shall be treated with fire-retardant materials in accordance with applicable fire safety regulations.<br>
                                            3. The Organizer may require exhibitors to take corrective measures related to fire prevention when deemed necessary. Exhibitors shall immediately comply with such requirements. If an exhibitor fails to comply, the Organizer may order suspension of the exhibition, removal, or withdrawal of the relevant exhibits or structures.<br><br>

                                            <div class="form_pri_titt">Article 13 (Exhibition Satisfaction Survey)</div>
                                            1. The Organizer shall conduct satisfaction surveys and industry status surveys for participants to support the operation of the Korea International Boat Show and the development of the marine leisure industry.<br>
                                            2. For objective surveys and evaluations, the Organizer may engage professional research organizations to conduct surveys of participants.<br><br>

                                            <div class="form_pri_titt">Article 14 (Compliance with Exhibitor Manual and Supplementary Regulations)</div>
                                            1. The Organizer shall provide participants with an Exhibitor Manual necessary for the performance of exhibition-related activities.<br>
                                            2. The Organizer may establish supplementary regulations in addition to these rules whenever necessary, and participants shall comply with both these rules and any supplementary regulations.<br><br>

                                            <div class="form_pri_titt">Article 15 (Interpretation of Rules and Dispute Resolution)</div>
                                            1. In the event of any disagreement between the Organizer and an exhibitor regarding the interpretation of these rules, the Organizer’s interpretation and decision shall prevail.<br>
                                            2. Any dispute concerning rights and obligations arising from participation in this Exhibition shall be settled through arbitration by the Korean Commercial Arbitration Board (KCAB) in Seoul, and the arbitral award shall be final and binding upon the parties.
                                        </div>
                                        <div class="form_pri_agree">
                                            <label><input type="radio" name="agree1" checked disabled>Agree</label>
                                            <label><input type="radio" name="agree1" disabled>Disagree</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- //약관 -->

                                <!-- 약관 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Privacy Policy</div>
                                    </div>
                                    <div class="form_pri">
                                        <div class="form_pri_box">

                                            <div class="form_pri_titt">KINTEX Privacy Policy</div>
                                            KINTEX (hereinafter referred to as the “Company”) establishes and discloses the following Privacy Policy in accordance with Article 30 of the Personal Information Protection Act in order to protect the personal information of data subjects and to promptly and smoothly handle related complaints.<br><br>

                                            <div class="form_pri_titt">Article 1 (Purpose of Processing Personal Information)</div>
                                            The Company processes personal information for the following purposes. The personal information being processed will not be used for purposes other than those stated below. If the purpose of use changes, the Company will take necessary measures, including obtaining separate consent, in accordance with Article 18 of the Personal Information Protection Act.<br>
                                            1. Website Membership Registration and Management<br>
                                            Personal information is processed for purposes including confirming membership registration intent, providing member services, identification and authentication of members, maintaining and managing membership status, identity verification under the limited verification system, prevention of unauthorized service use, confirmation of parental consent for children under the age of 14, notices and announcements, and complaint handling.<br>
                                            2. Provision of Goods or Services<br>
                                            Personal information is processed for purposes including the provision of exhibition and convention services (such as booth participation and exhibition admission), content provision, customized services, identity verification, age verification, payment processing, and settlement.<br>
                                            3. Complaint Handling<br>
                                            Personal information is processed for purposes including verifying the identity of complainants, confirming complaint details, communication and notification for fact-finding investigations, and notifying processing results.<br>
                                            4. Marketing and Advertising<br>
                                            Personal information is processed for purposes including providing new services and customized services, offering event and promotional information, providing participation opportunities, delivering services and advertisements based on demographic characteristics, and other marketing activities. The Company may also contact data subjects using such personal information for promotional or sales-related purposes regarding goods or services.<br>
                                            5. Personal Information Collection Items and Status<br>
                                            - Personal Information Collected: Login ID, name, telephone number, email address, password, age, gender, birth year, region, company name (organization name), survey results, and address<br>
                                            - Collection Methods: Website, KINTEX App, pre-registration through organizer exhibition websites, written forms, online and offline event activities<br>
                                            - Legal Basis for Retention: Consent to the Privacy Policy upon membership registration (personal information for marketing purposes: retained for 5 years)<br><br>

                                            <div class="form_pri_titt">Article 2 (Processing and Retention Period of Personal Information)</div>
                                            1. The Company processes and retains personal information within the period prescribed by applicable laws or within the period agreed upon by the data subject at the time of collection.<br>
                                            2. The retention periods for each category of personal information are as follows:<br>
                                            1) Website Membership Registration and Management: Until membership withdrawal from the website<br>
                                            However, if any of the following circumstances apply, personal information shall be retained until the relevant matter is concluded:<br>
                                            - Where investigations or inquiries relating to violations of applicable laws are ongoing, until such investigations or inquiries are completed<br>
                                            2) Provision of Goods or Services: Until the supply of goods or services and payment settlement have been completed<br>
                                            However, if any of the following circumstances apply, personal information shall be retained until the applicable retention period expires:<br>
                                            - Records relating to labeling, advertising, contract details, and performance of contracts under the Act on Consumer Protection in Electronic Commerce, etc.<br>
                                            3) Records Related to Transactions<br>
                                            • Records on labeling and advertising: 6 months<br>
                                            • Records on contracts, withdrawal of subscriptions, payment processing, and supply of goods: 5 years<br>
                                            • Records on consumer complaints and dispute resolution: 3 years<br><br>

                                            <div class="form_pri_titt">Article 3 (Provision of Personal Information to Third Parties)</div>
                                            1. The Company processes personal information only within the scope specified in Article 1 (Purpose of Processing Personal Information). Personal information may be provided to third parties only with the consent of the data subject or in cases permitted under Article 17 of the Personal Information Protection Act and other applicable laws.<br>
                                            2. The Company may provide personal information to third parties as follows:<br>

                                            - Recipient of Personal Information: Organizers of exhibitions and convention events using the free or paid services of the KINTEX App<br>
                                            • Purpose of Use by Recipient: Provision of free exhibition/convention services, paid payment services, joint event hosting, marketing, advertising, and related activities<br>
                                            • Personal Information Provided: User ID, name, mobile phone number, region, gender, birth year, company/organization name, referrer (ID or name), and survey information related to each exhibition/convention event<br><br>

                                            - Recipient of Personal Information: Association of Korean Exhibition Industry (AKEI)<br>
                                            • Purpose of Use by Recipient: Provision of information for exhibition certification by the Association of Korean Exhibition Industry<br>
                                            • Personal Information Provided: User ID, name, mobile phone number, region, gender, birth year, company/organization name, referrer (ID or name), and survey information related to each exhibition/convention event<br><br>

                                            <div class="form_pri_titt">Article 4 (Entrustment of Personal Information Processing)</div>
                                            1. The Company entrusts the following personal information processing tasks to facilitate efficient management of personal information.<br>
                                            - (Entrusting Party): KINTEX / (Entrusted Party): SVM Planet<br>
                                            - Scope of Entrusted Work: Management and operation of the Korea International Boat Show website<br>
                                            - Entrustment Period: Until the termination of the entrustment agreement<br>
                                            2. When entering into an entrustment agreement, the Company specifies matters concerning responsibilities such as prohibition of personal information processing beyond the purpose of the entrusted work, technical and administrative safeguards, restrictions on re-entrustment, supervision and management of the entrusted party, and liability for damages in accordance with Article 26 of the Personal Information Protection Act. The Company also supervises whether the entrusted party processes personal information safely.<br>
                                            3. If the details of the entrusted work or the entrusted party change, the Company will disclose such changes without delay through this Privacy Policy.<br><br>

                                            <div class="form_pri_titt">Article 5 (Rights and Obligations of Data Subjects and Methods of Exercise)</div>
                                            1. Data subjects may exercise the following rights related to personal information protection at any time:<br>
                                            - Request access to personal information<br>
                                            - Request correction of errors or inaccuracies<br>
                                            - Request deletion of personal information<br>
                                            - Request suspension of processing<br>
                                            2. The rights under Paragraph 1 may be exercised by submitting requests in writing, by telephone, email, or other means, and the Company shall take appropriate action without delay.<br>
                                            3. If a data subject requests correction or deletion of personal information, the Company shall not use or provide the relevant personal information until such correction or deletion has been completed.<br>
                                            4. The rights under Paragraph 1 may also be exercised through a legal representative or an authorized agent. In such cases, a power of attorney in the form prescribed by Appendix Form No. 11 of the Enforcement Rules of the Personal Information Protection Act must be submitted.<br>
                                            5. Data subjects shall not infringe upon their own or others’ personal information and privacy by violating the Personal Information Protection Act or other applicable laws while using the services provided by the Company.<br><br>

                                            <div class="form_pri_titt">Article 6 (Categories of Personal Information Processed)</div>
                                            The Company processes the following categories of personal information:<br>
                                            1. Website/App Membership Registration and Management<br>
                                            - Required Information: User ID, name, mobile phone number, country/region, gender, birth year<br>
                                            - Optional Information: Company/organization affiliation, referrer (ID or name), survey responses<br>
                                            2. Provision of Services<br>
                                            - Required Information: User ID, name, mobile phone number, country/region, gender, birth year<br>
                                            - Optional Information: Company/organization affiliation, referrer (ID or name), survey responses, previous purchase history<br>
                                            3. During the use of Internet services, the following information may be automatically generated and collected:<br>
                                            - IP address, cookies, MAC address, service usage records, visit history, records of improper use, and similar information<br><br>

                                            <div class="form_pri_titt">Article 7 (Destruction of Personal Information)</div>
                                            1. The Company shall promptly destroy personal information when it becomes unnecessary, such as upon expiration of the retention period or achievement of the processing purpose.<br>
                                            2. If personal information must be retained in accordance with other applicable laws even after the retention period agreed upon by the data subject has expired or the processing purpose has been achieved, such personal information shall be transferred to a separate database (DB) or stored in a different location.<br>
                                            3. The procedures and methods for destroying personal information are as follows:<br>
                                            - Destruction Procedure: The Company shall immediately destroy personal information upon the occurrence of a valid reason for destruction.<br>
                                            - Destruction Method: Personal information stored in electronic file format shall be destroyed using methods such as deletion of database records to prevent recovery. Personal information recorded or stored in paper documents shall be destroyed by shredding or incineration.<br><br>

                                            <div class="form_pri_titt">Article 8 (Measures to Ensure the Security of Personal Information)</div>
                                            The Company takes the following measures to ensure the security of personal information:<br>
                                            1. Administrative Measures: Establishment and implementation of internal management plans, regular employee training, etc.<br>
                                            2. Technical Measures: Access control and management of access rights to personal information processing systems, encryption of unique identification information, installation of security software, etc.<br>
                                            3. Physical Measures: Access control to computer rooms, document storage rooms, and other restricted areas.<br><br>

                                            <div class="form_pri_titt">Article 9 (Installation, Operation, and Refusal of Automatic Personal Information Collection Devices)</div>
                                            1. The Company may use “cookies” that store and retrieve user information from time to time in order to provide personalized services.<br>
                                            2. Cookies are small amounts of information sent by the server (HTTP) operating the website to the user’s web browser and may be stored on the hard disk of the user’s computer.<br>
                                            - Purpose of Cookie Use: Cookies are used to analyze visit patterns, service usage, popular search terms, security access status, and other information related to services and websites visited by users in order to provide optimized information and services.<br>
                                            - Installation, Operation, and Refusal of Cookies: Users may refuse the storage of cookies by adjusting the settings under Tools > Internet Options > Privacy in their web browser.<br>
                                            - If users refuse the storage of cookies, they may experience difficulties in using customized services.<br><br>

                                            <div class="form_pri_titt">Article 10 (Chief Privacy Officer)</div>
                                            1. The Company designates the following personnel as responsible for overseeing personal information processing and handling complaints, inquiries, and remedies related to personal information protection.<br>
                                            ▶ Chief Privacy Officer: Executive Vice President, Business Division<br>
                                            ▶ Personal Information Protection Manager<br>
                                            Department: Exhibition Business Team 3<br>
                                            Manager: Ki-Hoon Koo, Team Leader<br>
                                            Contact: 031-995-8771 / ghkoo@kintex.com<br><br>

                                            ▶ Personal Information Protection Officer<br>
                                            Department: Exhibition Business Team 3<br>
                                            Officer: Byung-Hoon Oh, Deputy General Manager<br>
                                            Contact: 031-995-8777 / sky@kintex.com<br><br>

                                            2. Data subjects may contact the department responsible for personal information protection regarding all inquiries, complaints, and requests for remedies related to personal information protection arising from the use of the Company's services (or business activities). The Company will respond and process such inquiries without delay.<br><br>

                                            <div class="form_pri_titt">Article 11 (Request for Access to Personal Information)</div>
                                            Data subjects may request access to their personal information pursuant to Article 35 of the Personal Information Protection Act through the department listed below. The Company will make every effort to ensure that such requests are processed promptly.<br>
                                            ▶ Department Responsible for Receiving and Processing Requests for Access to Personal Information: Exhibition Business Team 3<br><br>

                                            <div class="form_pri_titt">Article 12 (Remedies for Infringement of Rights and Interests)</div>
                                            Data subjects may contact the following organizations for consultation, dispute resolution, or remedies related to personal information infringement.<br>
                                            &lt;The organizations listed below are independent of the Company. If you are not satisfied with the Company's handling of personal information complaints or remedies, or if you require further assistance, please contact the relevant organization directly.&gt;<br><br>

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

                                            ▶ Cyber Crime Investigation Division, Supreme Prosecutors' Office: 02-3480-3573 (www.spo.go.kr)<br><br>

                                            ▶ Cyber Bureau, National Police Agency: 182 (http://cyberbureau.police.go.kr)<br><br>

                                            <div class="form_pri_titt">Article 13 (Installation and Operation of Video Information Processing Devices)</div>
                                            1. KINTEX installs and operates video information processing devices as follows:<br>
                                            - Purpose of Installation and Operation: Facility security and fire prevention at KINTEX<br>
                                            - Number of Devices, Installation Locations, and Recording Scope: Installed in major facilities such as exhibition hall lobbies and exhibition halls, recording all areas of major facilities<br>
                                            - Responsible Manager, Department, and Authorized Personnel with Access to Video Information: Facility Operations Team<br>
                                            - Recording Hours, Retention Period, Storage Location, and Processing Method of Video Information<br>
                                            • Recording Hours: 24-hour continuous recording<br>
                                            • Storage Location and Processing Method: Stored and managed in the video information control room<br>
                                            - Method and Location for Accessing Video Information: Facility Operations Team<br>
                                            - Procedures for Requests to Access Video Information: Requests must be submitted using a Personal Video Information Access/Existence Confirmation Request Form. Access is granted only when the data subject appears in the footage or when access is clearly necessary to protect the life, body, or property interests of the data subject.<br>
                                            - Technical, Administrative, and Physical Measures for Protecting Video Information: Establishment of internal management plans, access control and restrictions, secure storage and transmission technologies, maintenance of processing records and prevention of forgery or alteration, provision of secure storage facilities and locking devices, etc.<br><br>

                                            <div class="form_pri_titt">Article 14 (Consent to Photography and Video Recording)</div>
                                            1. Photography and video recording may take place at the exhibition venue.<br>
                                            2. Purpose of Recording<br>
                                            1) Promotion of this event and future exhibitions and conferences<br>
                                            2) Inclusion in exhibition-related press releases and official documents<br>
                                            3. Recorded materials will not be used for any purpose other than those specified above.
                                        </div>
                                        <div class="form_pri_agree">
                                            <label><input type="radio" name="agree2" checked disabled>Agree</label>
                                            <label><input type="radio" name="agree2" disabled>Disagree</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- //약관 -->

                                <!-- 안내문구 -->
                                    <%--<div class="form_wrap">
                                        <ul class="form_guide">
                                            &lt;%&ndash;<li>2025 경기국제보트쇼 종료로 인해 <span class="txtRed">일부 전시 정보 수정이 불가</span>합니다.</li>&ndash;%&gt;
                                        </ul>
                                    </div>--%>
                                <!-- 안내문구 -->

                                <!-- 회원계정정보 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Account Information</div>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item req">
                                                <p>ID</p>
                                            </div>
                                            <div class="input">
                                                <input type="hidden" id="id" name="id" value="${info.id}"/>
                                                ${info.id}
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <!-- 회원계정정보 -->

                                <!-- 참가업체 정보 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Exhibitor Info</div>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item req">
                                                <p>Company Name</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="companyNameEn" name="companyNameEn" value="${info.companyNameEn}" placeholder="Please enter your company name." class="onlyEngCompany">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Address</p>
                                            </div>
                                            <div class="input address" style="flex-wrap: wrap;">

                                                <!-- Country -->
                                                <div class="address_box" style="margin-bottom: 15px; width: 100%; display: flex; flex-direction: column; gap: 5px;">
                                                    <p style="font-size: 1.4rem; font-weight: 700;">Country <span class="txtRed">*</span></p>
                                                    <select id="companyCountry" name="companyCountry" style="width: 100%; max-width: 100%;">
                                                        <option value="" disabled selected hidden>Please select a country.</option>
                                                        <!-- 국가 옵션 추가 필요 -->
                                                        <option value="US" <c:if test="${info.companyCountry eq 'US'}">selected</c:if>>United States</option>
                                                        <option value="KR" <c:if test="${info.companyCountry eq 'KR'}">selected</c:if>>South Korea</option>
                                                        <option value="JP" <c:if test="${info.companyCountry eq 'JP'}">selected</c:if>>Japan</option>
                                                        <option value="CN" <c:if test="${info.companyCountry eq 'CN'}">selected</c:if>>China</option>
                                                    </select>
                                                </div>

                                                <!-- State/Province & City (나란히 배치) -->
                                                <div class="address_box" style="margin-bottom: 15px; width: 100%; display: flex; gap: 20px;">
                                                    <div style="flex: 1; display: flex; flex-direction: column; gap: 5px;">
                                                        <p style="font-size: 1.4rem; font-weight: 700;">State / Province <span class="txtRed">*</span></p>
                                                        <input type="text" id="companyState" name="companyState" value="${info.companyState}" placeholder="Please enter state / province." style="width: 100%;">
                                                    </div>
                                                    <div style="flex: 1; display: flex; flex-direction: column; gap: 5px;">
                                                        <p style="font-size: 1.4rem; font-weight: 700;">City <span class="txtRed">*</span></p>
                                                        <input type="text" id="companyCity" name="companyCity" value="${info.companyCity}" placeholder="Please enter city." style="width: 100%;">
                                                    </div>
                                                </div>

                                                <!-- Postal Code -->
                                                <div class="address_box" style="margin-bottom: 15px; width: 100%; display: flex; flex-direction: column; gap: 5px;">
                                                    <p style="font-size: 1.4rem; font-weight: 700;">Postal Code <span class="txtRed">*</span></p>
                                                    <!-- width: 50% 정도(w50 클래스 역할)로 맞추거나 기존 레이아웃처럼 100% 사용 가능 -->
                                                    <input type="text" id="companyZipcode" name="companyZipcode" value="${info.companyZipcode}" placeholder="Please enter postal code." style="width: calc(50% - 10px);">
                                                </div>

                                                <!-- Address Line 1 -->
                                                <div class="address_box" style="margin-bottom: 15px; width: 100%; display: flex; flex-direction: column; gap: 5px;">
                                                    <p style="font-size: 1.4rem; font-weight: 700;">Address Line 1 <span class="txtRed">*</span></p>
                                                    <input type="text" id="companyAddress" name="companyAddress" value="${info.companyAddress}" placeholder="Please enter address line 1." style="width: 100%;">
                                                </div>

                                                <!-- Address Line 2 -->
                                                <div class="address_box" style="width: 100%; display: flex; flex-direction: column; gap: 5px;">
                                                    <p style="font-size: 1.4rem; font-weight: 700;">Address Line 2</p>
                                                    <input type="text" id="companyAddressDetail" name="companyAddressDetail" value="${info.companyAddressDetail}" placeholder="Please enter address line 2." style="width: 100%;">
                                                </div>

                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>CEO</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="companyCeo" name="companyCeo" value="${info.companyCeo}" placeholder="CEO Name">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Tel</p>
                                            </div>
                                            <div class="input">
                                                <select id="companyTelCode">
                                                    <option value="" <c:if test="${empty info.companyTel}">selected</c:if> disabled hidden>Select</option>
                                                    <option value="+82" <c:if test="${fn:startsWith(info.companyTel,'+82')}">selected</c:if> >+82 (KOR)</option>
                                                    <option value="+1" <c:if test="${fn:startsWith(info.companyTel,'+1')}">selected</c:if> >+1 (USA/CAN)</option>
                                                    <option value="+44" <c:if test="${fn:startsWith(info.companyTel,'+44')}">selected</c:if> >+44 (GBR)</option>
                                                    <option value="+49" <c:if test="${fn:startsWith(info.companyTel,'+49')}">selected</c:if> >+49 (DEU)</option>
                                                    <option value="+61" <c:if test="${fn:startsWith(info.companyTel,'+61')}">selected</c:if> >+61 (AUS)</option>
                                                    <option value="+81" <c:if test="${fn:startsWith(info.companyTel,'+81')}">selected</c:if> >+81 (JPN)</option>
                                                    <option value="+86" <c:if test="${fn:startsWith(info.companyTel,'+86')}">selected</c:if> >+86 (CHN)</option>
                                                    <option value="+91" <c:if test="${fn:startsWith(info.companyTel,'+91')}">selected</c:if> >+91 (IND)</option>
                                                </select>
                                                <input type="tel" id="companyTel" name="companyTel" value="${info.companyTel}" class="onlyGeneralTel" maxlength="13" placeholder="Please enter only the numbers.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Website</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="companyHomepage" name="companyHomepage" value="${info.companyHomepage}" placeholder="https://">
                                                <label><input type="checkbox" name="noPage">No website</label>
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>Fax</p>
                                            </div>
                                            <div class="input">
                                                <select id="companyFaxCode">
                                                    <option value="" <c:if test="${empty info.companyFax}">selected</c:if> disabled hidden>Select</option>
                                                    <option value="+82" <c:if test="${fn:startsWith(info.companyFax,'+82')}">selected</c:if> >+82 (KOR)</option>
                                                    <option value="+1" <c:if test="${fn:startsWith(info.companyFax,'+1')}">selected</c:if> >+1 (USA/CAN)</option>
                                                    <option value="+44" <c:if test="${fn:startsWith(info.companyFax,'+44')}">selected</c:if> >+44 (GBR)</option>
                                                    <option value="+49" <c:if test="${fn:startsWith(info.companyFax,'+49')}">selected</c:if> >+49 (DEU)</option>
                                                    <option value="+61" <c:if test="${fn:startsWith(info.companyFax,'+61')}">selected</c:if> >+61 (AUS)</option>
                                                    <option value="+81" <c:if test="${fn:startsWith(info.companyFax,'+81')}">selected</c:if> >+81 (JPN)</option>
                                                    <option value="+86" <c:if test="${fn:startsWith(info.companyFax,'+86')}">selected</c:if> >+86 (CHN)</option>
                                                    <option value="+91" <c:if test="${fn:startsWith(info.companyFax,'+91')}">selected</c:if> >+91 (IND)</option>
                                                </select>
                                                <input type="tel" id="companyFax" name="companyFax" value="${info.companyFax}" class="onlyNum" placeholder="Please enter only the numbers.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Category</p>
                                            </div>
                                            <div class="input">
                                                <select name="industryPart" id="industryPart">
                                                    <option value="" <c:if test="${empty info.industryPart}">selected</c:if>>selected</option>

                                                    <optgroup label="---- Korea International Boat Show(KIBS) ----">
                                                        <option value="요트/보트 제조" <c:if test="${info.industryPart eq '요트/보트 제조'}">selected</c:if>>Yacht/Boat Manufacturing</option>
                                                        <option value="요트/보트 유통(수입 판매)" <c:if test="${info.industryPart eq '요트/보트 유통(수입 판매)'}">selected</c:if>>Yacht/Boat Distribution</option>
                                                        <option value="해양 부품(엔진 및 구성품) 제조" <c:if test="${info.industryPart eq '해양 부품(엔진 및 구성품) 제조'}">selected</c:if>>Marine Parts Manufacturing</option>
                                                        <option value="해양 부품(엔진 및 구성품) 판매" <c:if test="${info.industryPart eq '해양 부품(엔진 및 구성품) 판매'}">selected</c:if>>Marine Parts Sales</option>
                                                        <option value="해양 관련 기자재 제조 및 판매" <c:if test="${info.industryPart eq '해양 관련 기자재 제조 및 판매'}">selected</c:if>>Marine Equipment</option>
                                                        <option value="수상레저 용품 제조 및 판매" <c:if test="${info.industryPart eq '수상레저 용품 제조 및 판매'}">selected</c:if>>Water Leisure Products</option>
                                                        <option value="수중레저 용품 제조 및 판매" <c:if test="${info.industryPart eq '수중레저 용품 제조 및 판매'}">selected</c:if>>Diving Products</option>
                                                        <option value="아웃도어 용품 제조 및 판매" <c:if test="${info.industryPart eq '아웃도어 용품 제조 및 판매'}">selected</c:if>>Outdoor Products</option>
                                                        <option value="마리나 산업" <c:if test="${info.industryPart eq '마리나 산업'}">selected</c:if>>Marina Industry</option>
                                                        <option value="교육/면허" <c:if test="${info.industryPart eq '교육/면허'}">selected</c:if>>Training & Licensing</option>
                                                        <option value="수리" <c:if test="${info.industryPart eq '수리'}">selected</c:if>>Repair</option>
                                                        <option value="기타(경기국제보트쇼)" <c:if test="${info.industryPart eq '기타(경기국제보트쇼)'}">selected</c:if>>Other</option>
                                                    </optgroup>

                                                    <optgroup label="---- Korea International Surf Show(KISS) ----">
                                                        <option value="서핑장비" <c:if test="${info.industryPart eq '서핑장비'}">selected</c:if>>Surf Equipment</option>
                                                        <option value="의류&패션" <c:if test="${info.industryPart eq '의류&패션'}">selected</c:if>>Apparel & Fashion</option>
                                                        <option value="체험&교육프로그램" <c:if test="${info.industryPart eq '체험&교육프로그램'}">selected</c:if>>Programs & Training</option>
                                                        <option value="라이프스타일" <c:if test="${info.industryPart eq '라이프스타일'}">selected</c:if>>Lifestyle</option>
                                                        <option value="기타(코리아서프쇼)" <c:if test="${info.industryPart eq '기타(코리아서프쇼)'}">selected</c:if>>Other</option>
                                                    </optgroup>

                                                    <optgroup label="---- Korea Marine Travel Show(KMTS) ----">
                                                        <option value="해양레저 관광상품" <c:if test="${info.industryPart eq '해양레저 관광상품'}">selected</c:if>>Marine Tourism</option>
                                                        <option value="지역관광콘텐츠" <c:if test="${info.industryPart eq '지역관광콘텐츠'}">selected</c:if>>Local Tourism</option>
                                                        <option value="치유&생태관광" <c:if test="${info.industryPart eq '치유&생태관광'}">selected</c:if>>Wellness & Ecotourism</option>
                                                        <option value="체험 콘텐츠" <c:if test="${info.industryPart eq '체험 콘텐츠'}">selected</c:if>>Experience Programs</option>
                                                        <option value="기타(해양관광전)" <c:if test="${info.industryPart eq '기타(해양관광전)'}">selected</c:if>>Other</option>
                                                    </optgroup>
                                                </select>
                                                <input type="text" id="industryPartEtc" name="industryPartEtc" class="w50" value="${info.industryPartEtc}" placeholder="Direct input" disabled>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Logo</p>
                                            </div>
                                            <div class="input file_box">
                                                <input type="text" id="logo" class="upload_name" value="" placeholder="File" disabled="disabled">
                                                <input type="file" id="logoFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                <label for="logoFile">Browse</label>
                                                <div class="cmnt">JPG, PNG formats, and only under 10MB are accepted. (Recommended: 150x150px)</div>
                                            </div>
                                        </li>
                                        <c:if test="${logoFile ne null and not empty logoFile}">
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Logo File</p>
                                                </div>
                                                <div class="input file_box">
                                                    <ul>
                                                        <li class="logoFile_li" style="align-items: center;">
                                                            <c:set var="logoFileSrc" value="${fn:replace(logoFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
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
                                        <li>
                                            <div class="item req">
                                                <p>Previous Participation</p>
                                            </div>
                                            <div class="input check">
                                                <label><input type="checkbox" name="prePartYear" value="first" <c:if test="${fn:contains(info.prePartYear, 'first')}">checked</c:if>/>First Time</label>
                                                <label><input type="checkbox" name="prePartYear" value="2008~2014" <c:if test="${fn:contains(info.prePartYear, '2008~2014')}">checked</c:if>/>2008~2014</label>
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
                                                <label><input type="checkbox" name="prePartYear" value="2025" <c:if test="${fn:contains(info.prePartYear, '2025')}">checked</c:if>/>2025</label>
                                                <label><input type="checkbox" name="prePartYear" value="2026" <c:if test="${fn:contains(info.prePartYear, '2026')}">checked</c:if>/>2026</label>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p style="line-height: 1.4;">
                                                    KMIA Membership<span style="color: var(--mainColor1); margin-left: 3px;">*</span><br>
                                                    <span style="font-size: 1.2rem; font-weight: normal; color: #555;">Korea Marine Industry Association</span>
                                                </p>
                                            </div>
                                            <div class="input check">
                                                <label><input type="radio" name="memberCompanyYn" value="Y" <c:if test="${info.memberCompanyYn eq 'Y'}">checked</c:if> />Yes</label>
                                                <label><input type="radio" name="memberCompanyYn" value="N" <c:if test="${info.memberCompanyYn eq 'N' or empty info}">checked</c:if> />No</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <!-- 참가업체 정보 -->

                                <!-- 담당자 정보 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Contact Info</div>
                                        <div class="small">Up to 3 additional contacts allowed.</div>
                                    </div>
                                    <ul class="form_box">
                                        <li class="form_in_tit">Primary Contact</li>
                                        <li>
                                            <div class="item req">
                                                <p>Name</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="name" name="name" value="${info.name}" placeholder="Name" class="w50">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Position</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="position" name="position" value="${info.position}" placeholder="Position" class="w50">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item">
                                                <p>Department</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="depart" name="depart" value="${info.depart}" placeholder="Department" class="w50">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Tel</p>
                                            </div>
                                            <div class="input">
                                                <select id="telCode">
                                                    <option value="" <c:if test="${empty info.tel}">selected</c:if> disabled hidden>Select</option>
                                                    <option value="+82" <c:if test="${fn:startsWith(info.tel,'+82')}">selected</c:if> >+82 (KOR)</option>
                                                    <option value="+1" <c:if test="${fn:startsWith(info.tel,'+1')}">selected</c:if> >+1 (USA/CAN)</option>
                                                    <option value="+44" <c:if test="${fn:startsWith(info.tel,'+44')}">selected</c:if> >+44 (GBR)</option>
                                                    <option value="+49" <c:if test="${fn:startsWith(info.tel,'+49')}">selected</c:if> >+49 (DEU)</option>
                                                    <option value="+61" <c:if test="${fn:startsWith(info.tel,'+61')}">selected</c:if> >+61 (AUS)</option>
                                                    <option value="+81" <c:if test="${fn:startsWith(info.tel,'+81')}">selected</c:if> >+81 (JPN)</option>
                                                    <option value="+86" <c:if test="${fn:startsWith(info.tel,'+86')}">selected</c:if> >+86 (CHN)</option>
                                                    <option value="+91" <c:if test="${fn:startsWith(info.tel,'+91')}">selected</c:if> >+91 (IND)</option>
                                                </select>
                                                <input type="tel" id="tel" name="tel" value="${info.tel}" class="onlyGeneralTel" maxlength="13" placeholder="Please enter only the numbers.">
                                            </div>
                                        </li>
                                        <li class="w50">
                                            <div class="item req">
                                                <p>Mobile</p>
                                            </div>
                                            <div class="input">
                                                <select id="phoneCode">
                                                    <option value="" <c:if test="${empty info.phone}">selected</c:if> disabled hidden>Select</option>
                                                    <option value="+82" <c:if test="${fn:startsWith(info.phone,'+82')}">selected</c:if> >+82 (KOR)</option>
                                                    <option value="+1" <c:if test="${fn:startsWith(info.phone,'+1')}">selected</c:if> >+1 (USA/CAN)</option>
                                                    <option value="+44" <c:if test="${fn:startsWith(info.phone,'+44')}">selected</c:if> >+44 (GBR)</option>
                                                    <option value="+49" <c:if test="${fn:startsWith(info.phone,'+49')}">selected</c:if> >+49 (DEU)</option>
                                                    <option value="+61" <c:if test="${fn:startsWith(info.phone,'+61')}">selected</c:if> >+61 (AUS)</option>
                                                    <option value="+81" <c:if test="${fn:startsWith(info.phone,'+81')}">selected</c:if> >+81 (JPN)</option>
                                                    <option value="+86" <c:if test="${fn:startsWith(info.phone,'+86')}">selected</c:if> >+86 (CHN)</option>
                                                    <option value="+91" <c:if test="${fn:startsWith(info.phone,'+91')}">selected</c:if> >+91 (IND)</option>
                                                </select>
                                                <input type="tel" id="phone" name="phone" value="${info.phone}" class="onlyTel" maxlength="13" placeholder="Please enter only the numbers.">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>E-mail</p>
                                            </div>
                                            <div class="input email">
                                                <input type="email" id="email1" name="email1" value="${fn:split(info.email,'@')[0]}" placeholder="E-mail" class="email_input1">
                                                <span>@</span>
                                                <input type="email" id="email2" name="email2" value="${fn:split(info.email,'@')[1]}" class="email_input2" placeholder="Direct input">
                                            </div>
                                        </li>
                                    </ul>
                                    <c:if test="${empty chargeList}">
                                        <ul class="form_box managerInfoBox">
                                            <li class="form_in_tit">
                                                <input type="hidden" name="chargeSeq" value="">
                                                Contact #<span class="managerInfoNum">1</span>
                                                <span class="del_btn managerInfoDel">Delete</span>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Name</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="chargePersonName" class="w50" placeholder="Name">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Position</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="chargePersonPosition" class="w50" placeholder="Position">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Department</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="chargePersonDepart" class="w50" placeholder="Department">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Tel</p>
                                                </div>
                                                <div class="input">
                                                    <select name="chargePersonTelCode">
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
                                                    <input type="tel" name="chargePersonTel" class="onlyGeneralTel" maxlength="13" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Mobile</p>
                                                </div>
                                                <div class="input">
                                                    <select name="chargePersonPhoneCode">
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
                                                    <input type="tel" name="chargePersonPhone" class="onlyTel" maxlength="20" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>E-mail</p>
                                                </div>
                                                <div class="input email">
                                                    <input type="email" name="chargePersonEmail" placeholder="E-mail" class="email_input1">
                                                    <span>@</span>
                                                    <input type="email" name="chargePersonDomain" placeholder="Direct input" class="email_input2">
                                                </div>
                                            </li>
                                        </ul>
                                    </c:if>

                                    <c:if test="${not empty chargeList}">
                                        <c:forEach var="charge" items="${chargeList}" begin="0" end="${chargeList.size()}" step="1" varStatus="status">
                                            <ul class="form_box managerInfoBox">
                                                <li class="form_in_tit">
                                                    <input type="hidden" name="chargeSeq" value="${charge.seq}">
                                                    Contact #<span class="managerInfoNum">${status.index + 1}</span>
                                                    <span class="del_btn managerInfoDel">Delete</span>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>Name</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="chargePersonName" value="${charge.chargePersonName}" maxlength="50" class="w50" placeholder="Name">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Position</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="chargePersonPosition" value="${charge.chargePersonPosition}" class="w50" placeholder="Position">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Department</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="chargePersonDepart" value="${charge.chargePersonDepart}" class="w50" placeholder="Department">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Tel</p>
                                                    </div>
                                                    <div class="input">
                                                        <select name="chargePersonTelCode">
                                                            <option value="" <c:if test="${empty charge.chargePersonTel}">selected</c:if> disabled hidden>Select</option>
                                                            <option value="+82" <c:if test="${fn:startsWith(charge.chargePersonTel,'+82')}">selected</c:if> >+82 (KOR)</option>
                                                            <option value="+1" <c:if test="${fn:startsWith(charge.chargePersonTel,'+1')}">selected</c:if> >+1 (USA/CAN)</option>
                                                            <option value="+44" <c:if test="${fn:startsWith(charge.chargePersonTel,'+44')}">selected</c:if> >+44 (GBR)</option>
                                                            <option value="+49" <c:if test="${fn:startsWith(charge.chargePersonTel,'+49')}">selected</c:if> >+49 (DEU)</option>
                                                            <option value="+61" <c:if test="${fn:startsWith(charge.chargePersonTel,'+61')}">selected</c:if> >+61 (AUS)</option>
                                                            <option value="+81" <c:if test="${fn:startsWith(charge.chargePersonTel,'+81')}">selected</c:if> >+81 (JPN)</option>
                                                            <option value="+86" <c:if test="${fn:startsWith(charge.chargePersonTel,'+86')}">selected</c:if> >+86 (CHN)</option>
                                                            <option value="+91" <c:if test="${fn:startsWith(charge.chargePersonTel,'+91')}">selected</c:if> >+91 (IND)</option>
                                                        </select>
                                                        <input type="tel" name="chargePersonTel" value="${charge.chargePersonTel}" class="onlyGeneralTel" maxlength="13" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Mobile</p>
                                                    </div>
                                                    <div class="input">
                                                        <select name="chargePersonPhoneCode">
                                                            <option value="" <c:if test="${empty charge.chargePersonPhone}">selected</c:if> disabled hidden>Select</option>
                                                            <option value="+82" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+82')}">selected</c:if> >+82 (KOR)</option>
                                                            <option value="+1" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+1')}">selected</c:if> >+1 (USA/CAN)</option>
                                                            <option value="+44" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+44')}">selected</c:if> >+44 (GBR)</option>
                                                            <option value="+49" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+49')}">selected</c:if> >+49 (DEU)</option>
                                                            <option value="+61" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+61')}">selected</c:if> >+61 (AUS)</option>
                                                            <option value="+81" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+81')}">selected</c:if> >+81 (JPN)</option>
                                                            <option value="+86" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+86')}">selected</c:if> >+86 (CHN)</option>
                                                            <option value="+91" <c:if test="${fn:startsWith(charge.chargePersonPhone,'+91')}">selected</c:if> >+91 (IND)</option>
                                                        </select>
                                                        <input type="tel" name="chargePersonPhone" value="${charge.chargePersonPhone}" class="onlyTel" maxlength="20" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>E-mail</p>
                                                    </div>
                                                    <div class="input email">
                                                        <input type="email" name="chargePersonEmail" value="${fn:split(charge.chargePersonEmail,'@')[0]}" placeholder="E-mail" class="email_input1">
                                                        <span>@</span>
                                                        <input type="email" name="chargePersonDomain" value="${fn:split(charge.chargePersonEmail,'@')[1]}" class="email_input2" placeholder="Direct input">
                                                    </div>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                    </c:if>

                                    <div class="formAddBtn"><span class="managerInfoAdd">Add</span></div>
                                </div>
                                <!-- 담당자 정보 -->

                                <!-- 참가분야 -->
                                <div class="form_wrap" id="eventParticipationModule"
                                     data-event="${info.fieldParticipatory}"
                                     data-field1="${info.fieldParticipatory1}"
                                     data-field2="${info.fieldParticipatory2}"
                                     data-field3="${info.fieldParticipatory3}">
                                    <div class="form_tit">
                                        <div class="big">Event & Category</div>
                                            <%--<div class="small">최대 3개 선택 가능합니다.</div>--%>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item req">
                                                <p>Event</p>
                                            </div>
                                            <div class="input check">
                                                <label><input type="radio" name="fieldParticipatory" value="boatShow">KIBS</label>
                                                <label><input type="radio" name="fieldParticipatory" value="surfShow">KISS</label>
                                                <label><input type="radio" name="fieldParticipatory" value="travelShow">KMTS</label>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Category</p>
                                            </div>
                                            <div class="input" id="participationFields">
                                                <select class="w30" id="field1" name="fieldParticipatory1">
                                                    <option selected disabled hidden>1st</option>
                                                </select>
                                                <select class="w30" id="field2" name="fieldParticipatory2">
                                                    <option value="" selected disabled hidden>2nd</option>
                                                </select>
                                                <select class="w30" id="field3" name="fieldParticipatory3">
                                                    <option value="" selected disabled hidden>3rd</option>
                                                </select>
                                                <div class="cmnt">Select up to 3 categories in order of relevance.</div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>

                                <!-- 상세정보 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Details</div>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item">
                                                <p>Company Video</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="companyIntroVideo" value="${info.companyIntroVideo}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                <div class="cmnt">This information will be displayed in the Online Exhibition Hall.</div>
                                                <div class="cmnt">YouTube videos only. (No Shorts/Reels)</div>
                                                <div class="cmnt">Please click the “Share” button below the YouTube video and paste the shared link.</div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Company Profile</p>
                                            </div>
                                            <div class="input">
                                                <textarea id="companyIntroKo" name="companyIntroKo" placeholder="Korean">${info.companyIntroKo}</textarea>
                                                <textarea id="companyIntroEn" name="companyIntroEn" placeholder="English" class="onlyNumEng">${info.companyIntroEn}</textarea>
                                                <div class="cmnt">This information will be displayed in the Online Exhibition Hall.</div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item req">
                                                <p>Purpose of Participation</p>
                                            </div>
                                            <div class="input">
                                                <select name="companyPurposeEn" id="companyPurposeEn">
                                                    <option value="Product Promotion & Branding" <c:if test="${info.companyPurposeEn eq 'Product Promotion & Branding'}">selected</c:if> >Product Promotion & Branding</option>
                                                    <option value="Business Meeting" <c:if test="${info.companyPurposeEn eq 'Business Meeting'}">selected</c:if> >Business Meeting</option>
                                                    <option value="Networking" <c:if test="${info.companyPurposeEn eq 'Networking'}">selected</c:if> >Networking</option>
                                                    <option value="Trend Research" <c:if test="${info.companyPurposeEn eq 'Trend Research'}">selected</c:if> >Trend Research</option>
                                                    <option value="Awards" <c:if test="${info.companyPurposeEn eq 'Awards'}">selected</c:if> >Awards</option>
                                                    <option value="Others" <c:if test="${info.companyPurposeEn eq 'Others'}">selected</c:if> >Others</option>
                                                </select>
                                                <select name="companyPurposeKo" id="companyPurposeKo">
                                                    <option value="제품 홍보 및 브랜드 인지도 제고" <c:if test="${info.companyPurposeKo eq '제품 홍보 및 브랜드 인지도 제고'}">selected</c:if> >제품 홍보 및 브랜드 인지도 제고</option>
                                                    <option value="비즈니스 상담 및 바이어 확보" <c:if test="${info.companyPurposeKo eq '비즈니스 상담 및 바이어 확보'}">selected</c:if> >비즈니스 상담 및 바이어 확보</option>
                                                    <option value="업계 네트워킹" <c:if test="${info.companyPurposeKo eq '업계 네트워킹'}">selected</c:if> >업계 네트워킹</option>
                                                    <option value="업계 트렌드 파악" <c:if test="${info.companyPurposeKo eq '업계 트렌드 파악'}">selected</c:if> >업계 트렌드 파악</option>
                                                    <option value="올해의 제품상 신청" <c:if test="${info.companyPurposeKo eq '올해의 제품상 신청'}">selected</c:if> >올해의 제품상 신청</option>
                                                    <option value="기타" <c:if test="${info.companyPurposeKo eq '기타'}">selected</c:if> >기타</option>
                                                </select>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>New Product Information</p>
                                            </div>
                                            <div class="input">
                                                <textarea id="newItemIntroKo" name="newItemIntroKo" placeholder="Korean">${info.newItemIntroKo}</textarea>
                                                <textarea id="newItemIntroEn" name="newItemIntroEn" placeholder="English" class="onlyNumEng">${info.newItemIntroEn}</textarea>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Promotion Information</p>
                                            </div>
                                            <div class="input">
                                                <textarea id="promotionPlan" name="promotionPlan" placeholder="-Please describe any discounts or events planned during the exhibition.&#10;-This information may be used for newsletters and social media promotions.">${info.promotionPlan}</textarea>
                                                <div class="cmnt">Please notify the organizer of any booth events in advance.</div>
                                                <div class="cmnt">Submissions after February 1, 2027 may not be included in promotions.</div>
                                            </div>
                                        </li>
                                    </ul>

                                </div>

                                <!-- 제품 노출 정보 -->
                                <script src="/js/online_en.js"></script>
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Product Info</div>
                                        <div class="small">Please enter product information to be displayed in the Online Exhibition Hall.</div>
                                    </div>

                                    <c:if test="${empty onlineList}">
                                        <ul class="form_box onlineInfoBox">
                                            <li class="form_in_tit">
                                                <input type="hidden" name="onlineSeq" value="">
                                                Product #<span class="onlineInfoNum">1</span>
                                                <span class="del_btn onlineInfoDel">Delete</span>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Category</p>
                                                </div>
                                                <div class="input">
                                                    <select id="onlineOptionBig_1" name="onlineOptionBig" class="w50"></select>
                                                    <select id="onlineOptionSmall_1" name="onlineOptionSmall" class="w50"></select>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Name</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineNameKo" placeholder="Korean" class="w50">
                                                    <input type="text" name="onlineNameEn" placeholder="English" class="w50 onlyNumEng">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Image</p>
                                                </div>
                                                <div class="input file_box">
                                                    <div class="cmnt2">Up to 5 images per product</div>
                                                    <div class="onlinePrdBox">
                                                        <input type="text" id="onlineImage1_1" name="onlineImage" class="upload_name" value="" placeholder="File" disabled="disabled">
                                                        <input type="file" id="onlineImageFile1_1" name="onlineImageFile" class="upload_hidden" accept=".png, .jpg, .jpeg">
                                                        <label for="onlineImageFile1_1">Browse</label>
                                                        <span class="onlinePrdAdd" style="cursor: pointer">Add</span>
                                                        <span class="onlinePrdDel" style="cursor: pointer">Delete</span>
                                                        <div class="cmnt">JPG, PNG formats, and only under 10MB are accepted. (Recommended: 1000x750px)</div>
                                                        <div class="cmnt">The first image will be used as the main image.</div>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Description</p>
                                                </div>
                                                <div class="input">
                                                    <textarea name="onlineIntroKo" placeholder="Korean"></textarea>
                                                    <textarea name="onlineIntroEn" placeholder="English" class="onlyNumEng"></textarea>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="item">
                                                    <p>Video</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineLink" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                    <div class="cmnt">YouTube videos only. (No Shorts/Reels)</div>
                                                    <div class="cmnt">Please click the “Share” button below the YouTube video and paste the shared link.</div>
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Length(cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineLength" class="onlyNum" maxlength="10" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Width(cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineWidth" class="onlyNum" maxlength="10" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Height(cm)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineHeight" class="onlyNum" maxlength="10" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Weight(kg)</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineWeight" class="onlyNum" maxlength="10" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Material</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineMaterial" placeholder="Plastic, Aluminum, FRP, etc.">
                                                </div>
                                            </li>
                                            <li class="w50">
                                                <div class="item">
                                                    <p>Year</p>
                                                </div>
                                                <div class="input">
                                                    <input type="text" name="onlineYear" class="onlyNum" maxlength="4" placeholder="Please enter only the numbers.">
                                                </div>
                                            </li>
                                        </ul>
                                    </c:if>

                                    <c:if test="${not empty onlineList}">
                                        <c:forEach var="online" items="${onlineList}" begin="0" end="${onlineList.size()}" step="1" varStatus="status">
                                            <ul class="form_box onlineInfoBox">
                                                <li class="form_in_tit">
                                                    <input type="hidden" name="onlineSeq" value="${online.seq}">
                                                    Product #<span class="onlineInfoNum">${status.index + 1}</span>
                                                    <span class="del_btn onlineInfoDel">Delete</span>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>Category</p>
                                                    </div>
                                                    <div class="input">
                                                        <select id="onlineOptionBig_${status.index + 1}" name="onlineOptionBig" class="w50"></select>
                                                        <select id="onlineOptionSmall_${status.index + 1}" name="onlineOptionSmall" class="w50"></select>
                                                        <script>
                                                            $(document).ready(function () {
                                                                $('#onlineOptionBig_${status.index + 1}').val('${online.onlineOptionBig}').prop('selected', true).trigger('change');
                                                                $('#onlineOptionSmall_${status.index + 1}').val('${online.onlineOptionSmall}').prop('selected', true);
                                                            })
                                                        </script>
                                                            <%--<c:if test="${online.onlineOptionBig ne null and online.onlineOptionBig ne '' and online.onlineOptionSmall ne null and online.onlineOptionSmall ne ''}">
                                                                <span class="preOptionList" style="margin-left: 10px; display: block;">
                                                                    저장된 제품 분류 값 : &nbsp;
                                                                        <input type="text" name="preBigValue" value="${online.onlineOptionBig}" style="width: unset;" readonly="readonly"/>&nbsp;
                                                                        <input type="text" name="preSmallValue" value="${online.onlineOptionSmall}" style="width: unset;" readonly="readonly"/>
                                                                </span>
                                                            </c:if>--%>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>Name</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineNameKo" value="${online.onlineNameKo}" placeholder="Korean" class="w50">
                                                        <input type="text" name="onlineNameEn" value="${online.onlineNameEn}" placeholder="English" class="w50 onlyNumEng">
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>Image</p>
                                                    </div>
                                                    <div class="input file_box">
                                                        <div class="cmnt2">Up to 5 images per product</div>
                                                        <div class="onlinePrdBox">
                                                            <c:set var="onlineImageNumIdx" value="0" />
                                                            <c:forEach var="onlineImageFile" items="${onlineImageFileList}" begin="0" end="${onlineImageFileList.size()}" step="1">
                                                                <c:set var="onlineInfoNumIdx" value="${status.index + 1}"/>
                                                                <c:if test="${fn:contains(onlineImageFile.note , 'onlineImage'.concat(onlineInfoNumIdx.toString()).concat('_'))}">
                                                                    <c:set var="onlineImageNumIdx" value="${onlineImageNumIdx + 1}" />
                                                                </c:if>
                                                            </c:forEach>
                                                            <input type="text" id="onlineImage${onlineInfoNumIdx}_${onlineImageNumIdx + 1}" name="onlineImage" class="upload_name" value="" disabled="disabled">
                                                            <input type="file" id="onlineImageFile${onlineInfoNumIdx}_${onlineImageNumIdx + 1}" name="onlineImageFile" accept=".png, .jpg, .jpeg" class="upload_hidden">
                                                            <label for="onlineImageFile${onlineInfoNumIdx}_${onlineImageNumIdx + 1}">Browse</label>
                                                            <span class="onlinePrdAdd" style="cursor: pointer">Add</span>
                                                            <span class="onlinePrdDel" style="cursor: pointer">Delete</span>
                                                            <div class="cmnt">JPG, PNG formats, and only under 10MB are accepted. (Recommended: 1000x750px)</div>
                                                            <div class="cmnt">The first image will be used as the main image.</div>
                                                        </div>
                                                    </div>
                                                </li>

                                                <c:if test="${onlineImageFileList ne null and not empty onlineImageFileList}">
                                                    <li class="preValueList">
                                                        <div class="item">
                                                            <p>Image File</p>
                                                        </div>
                                                        <div class="input file_box">
                                                            <ul>
                                                                <c:forEach var="onlineImageFile" items="${onlineImageFileList}" begin="0" end="${onlineImageFileList.size()}" step="1">
                                                                    <c:set var="idx" value="${status.index + 1}"/>
                                                                    <c:if test="${fn:contains(onlineImageFile.note, 'onlineImage'.concat(idx.toString()).concat('_'))}">
                                                                        <li class="onlineImageFile_li" style="align-items: center; margin-bottom: 5px;">
                                                                            <c:if test="${fn:substring(onlineImageFile.note, fn:length(onlineImageFile.note)-2, fn:length(onlineImageFile.note)) eq '_1'}">
                                                                                <span style="color: #FF0083">Main Image : </span>
                                                                            </c:if>
                                                                            <c:if test="${fn:substring(onlineImageFile.note, fn:length(onlineImageFile.note)-2, fn:length(onlineImageFile.note)) ne '_1'}">
                                                                                <span style="color: #FF0083"><c:out value="${fn:substring(onlineImageFile.note, fn:indexOf(onlineImageFile.note, '_')+1, fn:length(onlineImageFile.note))}"/> : </span>
                                                                            </c:if>
                                                                            <c:set var="onlineImageFileSrc" value="${fn:replace(onlineImageFile.fullFilePath, '/usr/local/tomcat/webapps', '')}" />
                                                                            <img src="${onlineImageFileSrc}" style="border: 1px solid #009ef7; max-width: 100px; margin: 0 10px;"/>
                                                                                <%--<a href="/file/download.do?path=exhibitor/company/${onlineImageFile.folderPath}&fileName=${onlineImageFile.fullFileName}">${onlineImageFile.fileName}</a>--%>
                                                                            <a href="javascript:void(0);" onclick="f_file_download('exhibitor/company/${onlineImageFile.folderPath}', '${onlineImageFile.fullFileName}')">${onlineImageFile.fileName}</a>
                                                                            <input type="hidden" name="onlineImageUploadFile" id="${onlineImageFile.id}" value="${onlineImageFile.fullFilePath}">
                                                                            <button type="button" style="margin-left: 10px; cursor: pointer;" onclick="f_file_remove(this,'${onlineImageFile.id}')">X</button>
                                                                        </li>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </ul>
                                                        </div>
                                                    </li>
                                                </c:if>

                                                <li>
                                                    <div class="item">
                                                        <p>Description</p>
                                                    </div>
                                                    <div class="input">
                                                        <textarea name="onlineIntroKo" placeholder="Korean">${online.onlineIntroKo}</textarea>
                                                        <textarea name="onlineIntroEn" placeholder="English" class="onlyNumEng">${online.onlineIntroEn}</textarea>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="item">
                                                        <p>Video</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineLink" value="${online.onlineLink}" placeholder="ex) https://www.youtube.com/watch?v=0X_Df4qvN-M">
                                                        <div class="cmnt">YouTube videos only. (No Shorts/Reels)</div>
                                                        <div class="cmnt">Please click the “Share” button below the YouTube video and paste the shared link.</div>
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Length(cm)</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineLength" value="${online.onlineLength}" class="onlyNum" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Width(cm)</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineWidth" value="${online.onlineWidth}" class="onlyNum" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Height(cm)</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineHeight" value="${online.onlineHeight}" class="onlyNum" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Weight(kg)</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineWeight" value="${online.onlineWeight}" class="onlyNum" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Material</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineMaterial" value="${online.onlineMaterial}" placeholder="Plastic, Aluminum, FRP, etc.">
                                                    </div>
                                                </li>
                                                <li class="w50">
                                                    <div class="item">
                                                        <p>Year</p>
                                                    </div>
                                                    <div class="input">
                                                        <input type="text" name="onlineYear" value="${online.onlineYear}" class="onlyNum" placeholder="Please enter only the numbers.">
                                                    </div>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                    </c:if>
                                    <div class="formAddBtn">
                                        <!-- 이미지교체방법추가 231107 -->
                                        <div class="img_replace_cmnt">
                                            <div class="btn">Image Guide</div>
                                            <div class="text">
                                                Please upload and replace the image, then click the [Next] button at the bottom of the page to save your changes.<br>
                                                You must click the [Next] button to save. Otherwise, the preview image will not be updated with the new image.
                                            </div>
                                        </div>
                                        <!-- //이미지교체방법추가 231107 -->
                                        <span class="onlineInfoAdd">Add</span>
                                    </div>
                                </div>

                                <!-- 수출상담회 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Export Consultation</div>
                                        <div class="small">KIBS 2027 offers one-on-one business meetings with domestic and international buyers.</div>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item req">
                                                <p>Participation</p>
                                            </div>
                                            <div class="input check">
                                                <label>
                                                    <input type="radio" id="exportMeetingY" name="exportMeetingYn" value="Y" <c:if test="${info.exportMeetingYn eq 'Y'}">checked</c:if> />
                                                    Yes
                                                </label>
                                                <label>
                                                    <input type="radio" id="exportMeetingN" name="exportMeetingYn" value="N" <c:if test="${info.exportMeetingYn eq 'N' or info.exportMeetingYn eq null or info.exportMeetingYn eq ''}">checked</c:if>/>
                                                    No
                                                </label>
                                            </div>
                                        </li>
                                    </ul>

                                    <div class="form_chuga form_add_buyer">
                                        <ul class="form_box" style="border-top:0;">
                                            <li class="form_in_tit">Buyer Invitation</li>
                                        </ul>

                                        <c:if test="${buyerList ne null and not empty buyerList}">
                                            <c:forEach var="buyer" items="${buyerList}" begin="0" end="${buyerList.size()}" step="1">
                                                <input type="hidden" name="buyerItemSeq" value="${buyer.seq}">
                                                <div class="form_chuga_list">
                                                    <div class="cont">
                                                        <div class="name">${buyer.buyerCompanyName}</div>
                                                        <div class="gubun">${buyer.buyerCompanyCountry} / ${buyer.buyerCompanyLocation} / ${buyer.buyerCompanyDepart} ${buyer.buyerCompanyPosition}</div>
                                                    </div>
                                                    <div class="modifyFormBuyer modifyFormList" onclick="f_buyer_modify_modal('select','${buyer.seq}')">Modify</div>
                                                    <div class="delFormBuyer delFormList" onclick="f_buyer_remove('select', this, '${buyer.seq}')">Delete</div>
                                                    <input type="hidden" name="buyerCompanyName" value="${buyer.buyerCompanyName}">
                                                    <input type="hidden" name="buyerCompanyCountry" value="${buyer.buyerCompanyCountry}">
                                                    <input type="hidden" name="buyerCompanyLocation" value="${buyer.buyerCompanyLocation}">
                                                    <input type="hidden" name="buyerCompanyHomepage" value="${buyer.buyerCompanyHomepage}">
                                                    <input type="hidden" name="buyerCompanyCharge" value="${buyer.buyerCompanyCharge}">
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

                                        <a href="#" id="buyer_add_btn" class="addFormBuyer addFormList btnSt01">Click here to add buyers</a>
                                    </div>
                                </div>
                                <!-- 비즈니스 상담 -->

                                <div class="form_btn" style="justify-content:flex-end;">
                                    <a href="javascript:void(0);" onclick="f_mypage_comp('01','${info.seq}')" class="form_btn_next">
                                        <div class="big">NEXT</div>
                                        <div class="small">Booth</div>
                                    </a>
                                </div>
                            </div>
                        </form>
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
                        <div class="tit">Buyer Invitation Add/Edit</div>
                        <a class="close" href="javascript:f_buyer_init();"><img src="/img/close_w.png"></a>
                    </div>
                    <div class="pop_box">
                        <div class="pop_cont">
                            <!-- 폼박스 -->
                            <div class="form_s">
                                <!-- 회원계정정보 -->
                                <div class="form_wrap">
                                    <div class="form_tit">
                                        <div class="big">Buyer Info</div>
                                    </div>
                                    <ul class="form_box">
                                        <li>
                                            <div class="item">
                                                <p>Corporate Name</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_company_name" name="buyerCompanyName" placeholder="Corporate Name">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Country</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_country" name="buyerCompanyCountry" placeholder="Country">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Location</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_location" name="buyerCompanyLocation" placeholder="Location">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Website</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_homepage" name="buyerCompanyHomepage" placeholder="https://">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Contact Name</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_charge" name="buyerCompanyCharge" placeholder="Contact Name">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Department</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_depart" name="buyerCompanyDepart" placeholder="Department">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Position</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_position" name="buyerCompanyPosition" placeholder="Position">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>E-mail</p>
                                            </div>
                                            <div class="input email">
                                                <input type="text" id="buyer_email_input1" name="buyerCompanyEmail" class="email_input1" placeholder="E-mail"/>
                                                <span>@</span>
                                                <input type="text" id="buyer_email_input2" name="buyerCompanyDomain" class="email_input2" placeholder="Direct input"/>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Tel</p>
                                            </div>
                                            <div class="input">
                                                <select id="buyer_tel_code" style="width: 30%; max-width: 150px;">
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
                                                <input type="text" id="buyer_tel" name="buyerCompanyTel" class="onlyGeneralTel" maxlength="13" placeholder="Please enter only the numbers." style="width: calc(70% - 10px);">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Mobile</p>
                                            </div>
                                            <div class="input">
                                                <select id="buyer_phone_code" style="width: 30%; max-width: 150px;">
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
                                                <input type="tel" id="buyer_phone" name="buyerCompanyPhone" class="onlyTel" maxlength="20" placeholder="Please enter only the numbers." style="width: calc(70% - 10px);">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>FAX</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_fax" name="buyerCompanyFax" class="onlyNum" placeholder="Please enter only the numbers."/>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Products</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_item" name="buyerCompanyItem" placeholder="Products"/>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Reason for Invitation</p>
                                            </div>
                                            <div class="input">
                                                <textarea id="buyer_invite_reason" name="buyerCompanyInviteReason" rows="3" placeholder="Reason for Invitation"></textarea>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item">
                                                <p>Business Status</p>
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
                                                <p>Remarks</p>
                                            </div>
                                            <div class="input">
                                                <input type="text" id="buyer_hope" name="buyerCompanyHope" placeholder="Remarks"/>
                                            </div>
                                        </li>
                                    </ul>
                                    <a class="btnSave" href="javascript:f_buyer_add('${info.seq}')">Registration/Modification</a>
                                </div>
                            </div>
                            <!-- //폼박스 -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="../footer.jsp" charEncoding="UTF-8"/>

        <c:if test="${info ne null}">
            <c:if test="${info.exportMeetingYn eq 'Y'}">
                <script> $(function(){ document.querySelector('.form_add_buyer').style.display = 'block'; }); </script>
            </c:if>
            <c:if test="${info.exportMeetingYn eq 'N'}">
                <script> $(function(){ document.querySelector('.form_add_buyer').style.display = 'none'; }); </script>
            </c:if>
        </c:if>

        <script>
            (function($) {
                $(document).ready(function () {

                    // 페이지 이탈 방지 이벤트 리스너
                    window.addEventListener('beforeunload', function (e) {

                        // 1. 저장이 진행 중이지 않고 (!isSubmitProceeding)
                        // 2. 현재 URL에 '/mng/' (관리자 경로)가 포함되지 않고
                        // 3. iframe 내부가 아닌 경우에만 (최상위 창일 때만) 경고창 표시
                        if (!isSubmitProceeding && !window.location.href.includes('/mng/') && window.self === window.top) {
                            e.preventDefault();
                            e.returnValue = '';
                        }
                    });

                    // 전화번호 및 팩스번호 초기화 (국가코드 중복 노출 방지)
                    let initialTel = $('#companyTel').val() ? $('#companyTel').val().trim() : '';
                    let initialTelCode = $('#companyTelCode').val() || '';
                    if (initialTelCode && initialTel.startsWith(initialTelCode)) {
                        $('#companyTel').val(initialTel.substring(initialTelCode.length).trim());
                    }

                    let initialFax = $('#companyFax').val() ? $('#companyFax').val().trim() : '';
                    let initialFaxCode = $('#companyFaxCode').val() || '';
                    if (initialFaxCode && initialFax.startsWith(initialFaxCode)) {
                        $('#companyFax').val(initialFax.substring(initialFaxCode.length).trim());
                    }

                    // 대표 담당자 전화번호 초기화
                    let initialPersonTel = $('#tel').val() ? $('#tel').val().trim() : '';
                    let initialPersonTelCode = $('#telCode').val() || '';
                    if (initialPersonTelCode && initialPersonTel.startsWith(initialPersonTelCode)) {
                        $('#tel').val(initialPersonTel.substring(initialPersonTelCode.length).trim());
                    }

                    let initialPersonPhone = $('#phone').val() ? $('#phone').val().trim() : '';
                    let initialPersonPhoneCode = $('#phoneCode').val() || '';
                    if (initialPersonPhoneCode && initialPersonPhone.startsWith(initialPersonPhoneCode)) {
                        $('#phone').val(initialPersonPhone.substring(initialPersonPhoneCode.length).trim());
                    }

                    // 부 담당자 전화번호 초기화 (반복문 처리)
                    $('input[name="chargePersonTel"]').each(function() {
                        let $telInput = $(this);
                        let $codeSelect = $telInput.siblings('select[name="chargePersonTelCode"]');
                        let telVal = $telInput.val() ? $telInput.val().trim() : '';
                        let codeVal = $codeSelect.val() || '';
                        if (codeVal && telVal.startsWith(codeVal)) {
                            $telInput.val(telVal.substring(codeVal.length).trim());
                        }
                    });

                    $('input[name="chargePersonPhone"]').each(function() {
                        let $phoneInput = $(this);
                        let $codeSelect = $phoneInput.siblings('select[name="chargePersonPhoneCode"]');
                        let phoneVal = $phoneInput.val() ? $phoneInput.val().trim() : '';
                        let codeVal = $codeSelect.val() || '';
                        if (codeVal && phoneVal.startsWith(codeVal)) {
                            $phoneInput.val(phoneVal.substring(codeVal.length).trim());
                        }
                    });

                    let companyHomepage = '${info.companyHomepage}';
                    if(nvl(companyHomepage,'') === ''){
                        $('input[name=noPage]').prop('checked',true).trigger('change');
                    }

                    const $module = $('#eventParticipationModule');
                    const $field1 = $module.find('#field1');
                    const $field2 = $module.find('#field2');
                    const $field3 = $module.find('#field3');

                    const categoryData = {
                        boatShow: [
                            { value: '보트&요트', text: 'Boats & Yachts' },
                            { value: '무동력보트', text: 'Non-powered Boats' },
                            { value: '워크보트', text: 'Work Boats' },
                            { value: '해양부품&장비', text: 'Marine Parts & Equipment' },
                            { value: '안전&마리나', text: 'Safety & Marina' },
                            { value: '해양관광', text: 'Marine Tourism' },
                            { value: '해양레저', text: 'Marine Leisure' },
                            { value: '서핑', text: 'Surfing' },
                            { value: '수중레저', text: 'Diving Leisure' }
                        ],
                        surfShow: [
                            { value: '서핑장비', text: 'Surfing Equipment' },
                            { value: '의류&패션', text: 'Apparel & Fashion' },
                            { value: '체험&교육프로그램', text: 'Experience & Education Program' },
                            { value: '라이프스타일', text: 'Lifestyle' }
                        ],
                        travelShow: [
                            { value: '해양레저 관광상품', text: 'Marine Leisure Tourism Products' },
                            { value: '지역관광콘텐츠', text: 'Regional Tourism Content' },
                            { value: '치유&생태관광', text: 'Healing & Ecotourism' },
                            { value: '체험콘텐츠', text: 'Experience Content' }
                        ]
                    };

                    /**
                     * 사용자가 직접 form을 변경했을 때 호출되는 함수
                     */
                    function updateFormState() {
                        const selectedEvent = $module.find('input[name="fieldParticipatory"]:checked').val();
                        const fullOptions = categoryData[selectedEvent] || [];

                        const val1 = $field1.val();
                        const val2 = $field2.val();
                        const val3 = $field3.val();

                        // 1순위 세팅
                        $field1.empty().append('<option value="" selected disabled hidden>1st</option>');
                        fullOptions.forEach(opt => {
                            $field1.append($('<option></option>').val(opt.value).text(opt.text));
                        });
                        if (val1 && fullOptions.some(opt => opt.value === val1)) {
                            $field1.val(val1);
                        }

                        const currentVal1 = $field1.val();

                        // 2순위 세팅
                        $field2.empty().append('<option value="" selected disabled hidden>2nd</option>');
                        if (currentVal1) {
                            const optionsForField2 = fullOptions.filter(opt => opt.value !== currentVal1);
                            optionsForField2.forEach(opt => {
                                $field2.append($('<option></option>').val(opt.value).text(opt.text));
                            });
                            if (val2 && optionsForField2.some(opt => opt.value === val2)) {
                                $field2.val(val2);
                            }
                        }

                        const currentVal2 = $field2.val();

                        // 3순위 세팅
                        $field3.empty().append('<option value="" selected disabled hidden>3rd</option>');
                        if (currentVal1 && currentVal2) {
                            const optionsForField3 = fullOptions.filter(opt => opt.value !== currentVal1 && opt.value !== currentVal2);
                            optionsForField3.forEach(opt => {
                                $field3.append($('<option></option>').val(opt.value).text(opt.text));
                            });
                            if (val3 && optionsForField3.some(opt => opt.value === val3)) {
                                $field3.val(val3);
                            }
                        }
                    }

                    /**
                     * 페이지 로딩 시 DB 값을 form에 적용하는 독립적인 함수
                     */
                    function applyDbValues() {
                        const savedEvent = ($module.data('event') || '').toString().trim();
                        const savedField1 = ($module.data('field1') || '').toString().trim();
                        const savedField2 = ($module.data('field2') || '').toString().trim();
                        const savedField3 = ($module.data('field3') || '').toString().trim();

                        if (savedEvent) {
                            // 1. 라디오 버튼 설정
                            $module.find(`input[name="fieldParticipatory"][value="` + savedEvent + `"]`).prop('checked', true);
                            const fullOptions = categoryData[savedEvent] || [];

                            // 2. 1순위 옵션 채우고 값 설정
                            $field1.empty().append('<option value="" selected disabled hidden>1st</option>');
                            fullOptions.forEach(opt => $field1.append($('<option></option>').val(opt.value).text(opt.text)));
                            if (savedField1 && savedField1 !== 'null') {
                                $field1.val(savedField1);
                            }

                            // 3. 2순위 옵션 채우고 값 설정
                            $field2.empty().append('<option value="" selected disabled hidden>2nd</option>');
                            const currentVal1 = $field1.val();
                            if (currentVal1) {
                                const optionsForField2 = fullOptions.filter(opt => opt.value !== currentVal1);
                                optionsForField2.forEach(opt => $field2.append($('<option></option>').val(opt.value).text(opt.text)));

                                if (savedField2 && savedField2 !== 'null') {
                                    $field2.val(savedField2);
                                }
                            }

                            // 4. 3순위 옵션 채우고 값 설정
                            $field3.empty().append('<option value="" selected disabled hidden>3rd</option>');
                            const currentVal2 = $field2.val();
                            if (currentVal1 && currentVal2) {
                                const optionsForField3 = fullOptions.filter(opt => opt.value !== currentVal1 && opt.value !== currentVal2);
                                optionsForField3.forEach(opt => $field3.append($('<option></option>').val(opt.value).text(opt.text)));

                                if (savedField3 && savedField3 !== 'null') {
                                    $field3.val(savedField3);
                                }
                            }

                        } else {
                            // DB 값이 없을 경우 (신규 등록): 기본값 설정 후 화면 업데이트
                            $module.find('input[name="fieldParticipatory"][value="boatShow"]').prop('checked', true);
                            updateFormState(); // 이 경우에만 updateFormState를 호출하여 초기화
                        }
                    }

                    // --- 이벤트 핸들러 연결 ---
                    $module.find('input[name="fieldParticipatory"], #participationFields select').on('change', updateFormState);

                    // --- 페이지 로드 시 실행 ---
                    applyDbValues();
                });
            })(jQuery);
        </script>

    </c:if>
</body>
</html>