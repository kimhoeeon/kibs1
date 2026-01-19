<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">

<head>
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
    <title>2026 KIBS</title>

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
                    <span>Home</span><span>Exhibitors</span><span>Korea at a Glance</span>
                </div>
                <div class="sub_top_tit">Korea at a Glance</div>
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
                            <a href="/eng/guide/summary.do">KIBS 2026</a>
                            <a class="active" href="/eng/exhibitor/categories.do">Exhibitors</a>
                            <a href="/eng/buyer/apply.do">Buyer</a>
                            <a href="/eng/board/notice.do">News</a>
                        </div>
                    </div>
                    <div class="tabOpt2 tabOption">
                        <div class="tabOptAct">Korea at a Glance</div>
                        <div class="tabOptSel">
                            <a href="/eng/exhibitor/categories.do">Exhibitors Categories</a>
                            <a href="javascript:alert('Contact Us\nEmail : kibs@kintex.com\nTel : +82 031-995-8946/8912');<%--TODO : 참가신청 기간에 오픈 /eng/apply/step01.do--%>">Exhibitor Registration</a>
                            <a class="active" href="/eng/exhibitor/glance.do">Korea at a Glance</a>
                            <a href="/eng/exhibitor/marina.do">Marine Industry in korea</a>
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
    <div class="glance_s padding_tb">
        <div class="inner">
            <div class="sub_cont_tit">
                <div class="big">Korea at a Glance</div>
                <div class="small">
                    South Korea’s meteoric rise to become one of the leading Asian economies and its rapid progression
                    tends to be a well-kept secret only known to those who have experienced firsthand the drive and
                    energy of the country. Close to China, low import tariffs and Free Trade Agreements with most of the
                    World’s leading economies together with a mature legal and commercial infrastructure make Korea an
                    easy and safe place to do business.
                </div>
            </div>

            <ul class="list_box">
                <li>
                    <div class="icon"><img src="/img/icon_glance01.png"></div>
                    <div class="txt1">
                        Central and all Provincial<br>
                        Government<br>
                        Annual Expenditure
                    </div>
                    <div class="txt3">
                        US$<span class="big">294</span>billion
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance02.png"></div>
                    <div class="txt1">Population</div>
                    <div class="txt3">
                        <span class="big">51,744,948</span>
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance03.png"></div>
                    <div class="txt1">World’s</div>
                    <div class="txt2">(Source : Ministry of Trade, 2022)</div>
                    <div class="txt3">
                        <span class="big">6th</span>largest exporter
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance04.png"></div>
                    <div class="txt1">World’s</div>
                    <div class="txt2">(by value and completion)</div>
                    <div class="txt3">
                        <span class="big">No.1</span>ship building
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance05.png"></div>
                    <div class="txt1">Nominal GDP</div>
                    <div class="txt2">World Rank 12th<br>(Source : Statistics Korea, 2022)</div>
                    <div class="txt3">
                        $<span class="big">1.73</span>trillion
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance06.png"></div>
                    <div class="txt1">Inflation (CPI)</div>
                    <div class="txt2">(Source : Statistics Korea, 2023)</div>
                    <div class="txt3">
                        <span class="big">1%</span>
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance07.png"></div>
                    <div class="txt1">Land Area</div>
                    <div class="txt3">
                        <span class="big">100,188</span>km²
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance08.png"></div>
                    <div class="txt1">Water Area</div>
                    <div class="txt3">
                        <span class="big">440,000</span>km²
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance09.png"></div>
                    <div class="txt1">Coastline</div>
                    <div class="txt3">
                        <span class="big">15,257</span>km
                    </div>
                </li>
                <li>
                    <div class="icon"><img src="/img/icon_glance10.png"></div>
                    <div class="txt1">Number of Islands</div>
                    <div class="txt3">
                        <span class="big">3,677</span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="glance_s2 padding_b">
        <div class="inner">
            <div class="sub_cont_tit">
                <div class="big">South Korea Summary</div>
            </div>

            <div class="sec_box">

                <div class="box">
                    <div class="img1"><img src="/img/img_glance_02_01.jpg"></div>
                    <div class="text">
                        The Korean Peninsula (lat. 33˚ - 43˚; long. 124˚ - 132˚) lies in the middle of Northeast Asia, flanked by China to its west and Japan to its east.<br>
                        The peninsula is 950km long longitudinally and 540km wide latitudinally.<br>
                        The total area of South Korea is 100,412km². Excluding the northern end, which is joined to the Asian continent, the peninsula is surrounded by water on three sides, with flat land and mountains accounting for 30% and 70% of the entire territory, respectively. Mountains over 1,000m above sea level make up only 15% of the mountainous areas, while mountains lower than 500m account for 65%.<br><br>

                        In 1948, the two Koreas established their respective governments: the Republic of Korea (South Korea) and the Democratic People’s Republic of Korea (North Korea).<br>
                        Defined as two different countries under international law, they joined the United Nations simultaneously. The Constitution of South Korea, however, regards North Korea as part of the Republic of Korea.<br><br>

                        Thanks to the remarkable education system and the high regard for education, the country has many skilled people.<br><br>

                        in virtually all sectors. Korea’s universities produce talented young people specializing in basic science, including physics, and other major sectors, such as electronics, mechanical engineering, business management, economics, and accounting. Many educated adults can make themselves understood in English, some of whom can speak another foreign language. At present, the widespread availability of vocational education sessions at high schools helps students obtain qualifications in specialty areas.<br><br>

                        According to the OECD’s Programme for International Student Assessment (PISA), South Korean students display a high level of academic achievement in reading, mathematics, and science. The PISA 2018 results showed that Korea was among the highest ranked OECD countries, ranking 2nd to 7th in reading, 1st to 4th in mathematics, and 3rd to 5th in science.<br><br>

                        South Korea has a large number of talented people engaged in cutting-edge research. Government-funded research institutes for basic science research, universities, and many world-leading corporations are dedicated to a vast range of projects from basic research to the development of advanced technologies and new commercial innovations.<br><br>

                        In recent years, more research and development projects have focused on the fields of the Fourth Industrial Revolution, including artificial intelligence (AI), electronics, computers, the Internet of Things (IoT), and big data.<br><br>

                        As of 2018, the total number of researchers in South Korea stood at 514,000, showing a high-level equivalent to 14.7 per 1,000 of the economically active population. Their activities result in numerous patent applications, both at home and abroad.<br><br>

                        South Korea is a leading powerhouse in information and communication technology. It is a country full of dynamism. It became the first country in the world to commercialize the CDMA and WiBro technologies as well as established nationwide 4G Long-Term Evolution (LTE) networks. In March 2019, South Korea launched the world’s first nationwide 5G mobile networks.<br><br>

                        In addition, two-way communication systems based on social networking services (SNSs) allow the government to provide useful information for the people, who, in turn, can report any inconveniences in their lives to their government. In 2018, the number of exports of South Korea’s e-government systems totaled 201, whose value reaches USD 258 million.<br><br>

                        According to the 2020 UN E-Government Survey, South Korea ranked second, following Denmark. In the same survey of 2010, 2012, and 2014, South Korea ranked first.<br><br>

                        In South Korea, the development of creative good-quality cultural products, in which human resources are combined with cultural resources, is viewed as an industrial sector that will play a leading role in the 21st century. The representative examples of the country’s successful items as cultural products include not only K-pop, enrapturing the world, but also TV dramas such as Kingdom and Crash Landing on You, and TV animations for infants such as Pororo.<br><br>

                        According to the United Nations Development Programme (UNDP), the country’s Human Development Index (HDI) is rising, apparently as a result of the growing emphasis on investment and the development of human resources as key actors of creative industrial sectors. The Human Development Index (HDI) is a composite statistic of the life expectancy, education, and income indices used to rank countries into four tiers of human development. In 2018, South Korea’s HDI came to 0.906, ranking 22nd among 189 countries.<br><br>

                        Korean commercial contract and consumer laws are based on those of the USA and the system of law supports the principles of natural justice making it easy for inward investment opportunities. Also, Intellectual Property rights are respected and enforced by the Korea Courts.
                    </div>
                    <div class="img2"><img src="/img/img_glance_02_02.jpg"></div>
                </div>

            </div>

        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="glance_s3 padding_b">
        <div class="inner">
            <div class="sub_cont_tit">
                <div class="big">The Korean Economy</div>
            </div>

            <div class="sec_box">

                <div class="box">
                    <div class="img1"><img src="/img/img_glance_03_01.jpg"></div>
                    <div class="text">
                        In the early 1960s, the country pushed ahead with export-oriented economic development plans. At first, the country’s major export items were mainly light industrial products manufactured in small factories, or raw materials. In the 1970s, the country invested in heavy chemical facilities and laid the basis for the export of heavy industrial products. The country is now leading the semiconductor and display sectors.<br><br>

                        Hosting the 1988 Summer Olympic Games, commonly known as Seoul 1988, provided the country with the momentum to join the ranks of semi-advanced countries. The overseas mass media called South Korea one of the four Asian tigers along with Taiwan, Singapore, and Hong Kong. In December 1996, the country became the 29th member country of the OECD, which is largely composed of advanced countries. <br><br>

                        South Korea’s exports, which amounted to only USD 32.82 million in 1960, surpassed the USD 10 billion mark in 1977 and reached USD 542.2 billion in 2019. The country’s GNI per capita was a paltry USD 67 in 1953 around when the government was established, yet rapidly increased to USD 32,115 in 2019.<br><br>

                        By 2010, South Korea had emerged as the world’s 7th largest exporting country. From 2011 to 2014, the country’s trade performance amounted to over USD 1 trillion for four consecutive years. The trade volume retreated slightly in 2015 and 2016 but rebounded to USD 1 trillion in 2017. South Korea’s foreign currency reserves amounted to USD 408.8 billion in 2019, and its short-term foreign debt ratio stood at 32.9% as of 2019, which sits around the middle range among G20 countries. The country’s sovereign credit rating has been at a stable level.
                    </div>
                    <div class="img2"><img src="/img/img_glance_03_02.jpg"></div>
                </div>

            </div>

        </div>
    </div>
    <!-- section -->

    <!-- section -->
    <div class="glance_s4 padding_b">
        <div class="inner">
            <div class="sub_cont_tit">
                <div class="big">
                    Recreational Boating Statistics<br>
                    & Market Development
                </div>
            </div>

            <div class="sec_box">

                <div class="box">
                    <div class="text">
                        The Government supported plan to develop a recreational boating culture has created the fastest growing boating activity in Asia. Since 2009 when the coastal regions were opened for recreational activity the unprecedented numbers of people actively involved in going boating and the annual increases confirm that this is a sustained and fast moving market that has fully embraced “messing about in boats.”
                    </div>
                    <div class="img1"><img src="/img/img_glance_04_01.jpg"></div>
                    <div class="img2"><img src="/img/img_glance_04_02.jpg"></div>
                    <div class="text">
                        IThe large disparity between driving licences issued and registration of leisure boats can be attributed to respect for the Police and controls applied by them to this system compared to the current lack of resources of the Coastguard. It is safe to assume that the number of boats currently in use is probably more than double the number registered.
                    </div>
                </div>

            </div>

        </div>
    </div>
    <!-- section -->

</div>

<c:import url="../footer.jsp" charEncoding="UTF-8"/>

</body>
</html>