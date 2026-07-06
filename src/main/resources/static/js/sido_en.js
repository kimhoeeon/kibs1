$('document').ready(function() {

    var area0 = ["Select", "Seoul", "Incheon", "Daejeon", "Gwangju", "Daegu", "Ulsan", "Busan", "Sejong", "Gyeonggi-do", "Gangwon-do", "Chungcheongbuk-do", "Chungcheongnam-do", "Jeollabuk-do", "Jeollanam-do", "Gyeongsangbuk-do", "Gyeongsangnam-do", "Jeju-do", "Foreigner"];
    var area1 = ["Gangnam-gu", "Gangdong-gu", "Gangbuk-gu", "Gangseo-gu", "Gwanak-gu", "Gwangjin-gu", "Guro-gu", "Geumcheon-gu", "Nowon-gu", "Dobong-gu", "Dongdaemun-gu", "Dongjak-gu", "Mapo-gu", "Seodaemun-gu", "Seocho-gu", "Seongdong-gu", "Seongbuk-gu", "Songpa-gu", "Yangcheon-gu", "Yeongdeungpo-gu", "Yongsan-gu", "Eunpyeong-gu", "Jongno-gu", "Jung-gu", "Jungnang-gu"];
    var area2 = ["Gyeyang-gu", "Namdong-gu", "Dong-gu", "Michuhol-gu", "Bupyeong-gu", "Seo-gu", "Yeonsu-gu", "Jung-gu", "Ganghwa-gun", "Ongjin-gun"];
    var area3 = ["Daedeok-gu", "Dong-gu", "Seo-gu", "Yuseong-gu", "Jung-gu"];
    var area4 = ["Gwangsan-gu", "Nam-gu", "Dong-gu", "Buk-gu", "Seo-gu"];
    var area5 = ["Nam-gu", "Dalseo-gu", "Dong-gu", "Buk-gu", "Seo-gu", "Suseong-gu", "Jung-gu", "Dalseong-gun"];
    var area6 = ["Nam-gu", "Dong-gu", "Buk-gu", "Jung-gu", "Ulju-gun"];
    var area7 = ["Gangseo-gu", "Geumjeong-gu", "Nam-gu", "Dong-gu", "Dongnae-gu", "Busanjin-gu", "Buk-gu", "Sasang-gu", "Saha-gu", "Seo-gu", "Suyeong-gu", "Yeonje-gu", "Yeongdo-gu", "Jung-gu", "Haeundae-gu", "Gijang-gun"];
    var area8 = ["-"];
    var area9 = ["Goyang-si", "Gwacheon-si", "Gwangmyeong-si", "Gwangju-si", "Guri-si", "Gunpo-si", "Gimpo-si", "Namyangju-si", "Dongducheon-si", "Bucheon-si", "Seongnam-si", "Suwon-si", "Siheung-si", "Ansan-si", "Anseong-si", "Anyang-si", "Yangju-si", "Yeoju-si", "Osan-si", "Yongin-si", "Uiwang-si", "Uijeongbu-si", "Icheon-si", "Paju-si", "Pyeongtaek-si", "Pocheon-si", "Hanam-si", "Hwaseong-si", "Gapyeong-gun", "Yangpyeong-gun", "Yeoncheon-gun"];
    var area10 = ["Gangneung-si", "Donghae-si", "Samcheok-si", "Sokcho-si", "Wonju-si", "Chuncheon-si", "Taebaek-si", "Goseong-gun", "Yanggu-gun", "Yangyang-gun", "Yeongwol-gun", "Inje-gun", "Jeongseon-gun", "Cheorwon-gun", "Pyeongchang-gun", "Hongcheon-gun", "Hwacheon-gun", "Hoengseong-gun"];
    var area11 = ["Jecheon-si", "Cheongju-si", "Chungju-si", "Goesan-gun", "Danyang-gun", "Boeun-gun", "Yeongdong-gun", "Okcheon-gun", "Eumseong-gun", "Jeungpyeong-gun", "Jincheon-gun"];
    var area12 = ["Gyeryong-si", "Gongju-si", "Nonsan-si", "Dangjin-si", "Boryeong-si", "Seosan-si", "Asan-si", "Cheonan-si", "Geumsan-gun", "Buyeo-gun", "Seocheon-gun", "Yesan-gun", "Cheongyang-gun", "Taean-gun", "Hongseong-gun"];
    var area13 = ["Gunsan-si", "Gimje-si", "Namwon-si", "Iksan-si", "Jeonju-si", "Jeongeup-si", "Gochang-gun", "Muju-gun", "Buan-gun", "Sunchang-gun", "Wanju-gun", "Imsil-gun", "Jangsu-gun", "Jinan-gun"];
    var area14 = ["Gwangyang-si", "Naju-si", "Mokpo-si", "Suncheon-si", "Yeosu-si", "Gangjin-gun", "Goheung-gun", "Gokseong-gun", "Gurye-gun", "Damyang-gun", "Muan-gun", "Boseong-gun", "Sinan-gun", "Yeonggwang-gun", "Yeongam-gun", "Wando-gun", "Jangseong-gun", "Jangheung-gun", "Jindo-gun", "Hampyeong-gun", "Haenam-gun", "Hwasun-gun"];
    var area15 = ["Gyeongsan-si", "Gyeongju-si", "Gumi-si", "Gimcheon-si", "Mungyeong-si", "Sangju-si", "Andong-si", "Yeongju-si", "Yeongcheon-si", "Pohang-si", "Goryeong-gun", "Gunwi-gun", "Bonghwa-gun", "Seongju-gun", "Yeongdeok-gun", "Yeongyang-gun", "Yecheon-gun", "Ulleung-gun", "Uljin-gun", "Uiseong-gun", "Cheongdo-gun", "Cheongsong-gun", "Chilgok-gun"];
    var area16 = ["Geoje-si", "Gimhae-si", "Miryang-si", "Sacheon-si", "Yangsan-si", "Jinju-si", "Jinhae-si", "Changwon-si", "Tongyeong-si", "Geochang-gun", "Goseong-gun", "Namhae-gun", "Sancheong-gun", "Uiryeong-gun", "Changnyeong-gun", "Hadong-gun", "Haman-gun", "Hamyang-gun", "Hapcheon-gun"];
    var area17 = ["Seogwipo-si", "Jeju-si"];

    // Foreigner를 위한 인덱스 18번에 대응
    var area18 = ["-"];

    // 시/도 선택 박스 초기화
    $("select[name^=sido]").each(function() {
        $selsido = $(this);
        $.each(eval(area0), function() {
            $selsido.append("<option value='" + this + "'>" + this + "</option>");
        });
        $selsido.next().append("<option value=''>Select</option>");
    });

    // 시/도 선택시 구/군 설정
    $("select[name^=sido]").on('change', function() {
        var area = "area" + $("option", $(this)).index($("option:selected", $(this))); // 선택지역의 구군 Array
        var $gugun = $(this).next(); // 선택영역 군구 객체
        $("option", $gugun).remove(); // 구군 초기화

        if (area == "area0")
            $gugun.append("<option value=''>Select</option>");
        else {
            $.each(eval(area), function() {
                $gugun.append("<option value='" + this + "'>" + this + "</option>");
            });
        }
    });

});