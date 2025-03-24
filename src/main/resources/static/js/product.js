$(document).ready(function () {
    var prdLengthValues, prdHpValues; // 슬라이더의 값을 저장할 변수 선언

    // 제품검색하기 범위 슬라이드
    var slider = $("#prdLength").slider({
        range: true,
        min: 0,
        max: 40,
        values: [0, 40],
        slide: function (event, ui) {
            $("#prdLengthAmount").val(ui.values[0] + ' - ' + ui.values[1]);
            prdLengthValues = ui.values; // 슬라이더의 값을 저장
        }
    }).slider("disable", true).addClass("disabled-slider");
    $("#prdLengthAmount").val(slider.slider("values", 0) + ' - ' + slider.slider("values", 1));
    $("#prdLength").siblings().prop("disabled", true).addClass("disabled-slider");

    var slider = $("#prdHp").slider({
        range: true,
        min: 0,
        max: 500,
        values: [0, 500],
        slide: function (event, ui) {
            $("#prdHpAmount").val(ui.values[0] + ' - ' + ui.values[1]);
            prdHpValues = ui.values; // 슬라이더의 값을 저장
        }
    }).slider("disable", true).addClass("disabled-slider");   
    $("#prdHpAmount").val(slider.slider("values", 0) + ' - ' + slider.slider("values", 1));
    $("#prdHp").siblings().prop("disabled", true).addClass("disabled-slider");

    // 제품 검색하기 옵션
    var product0 = ["선택", "요트·보트", "무동력보트", "워크보트", "워터스포츠", "다이빙", "아웃도어", "해양관광", "해양부품", "마리나산업"];
    // 요트 보트
    var product1 = ["선택", "파워보트", "세일요트", "고무보트", "콤비보트", "FRP보트", "알루미늄보트", "카본보트", "복합소재보트"];
    // 무동력보트
    var product2 = ["선택", "카누", "카약", "조정", "노보트", "SUP", "딩기요트", "무동력보트"];
    // 워크보트
    var product3 = ["선택", "관공선", "소방선", "구조선", "감시선", "행정선", "고속단정", "특수선박"];
    // 워터스포츠
    var product4 = ["선택", "수상오토바이", "수상스키", "웨이크보드", "서핑 장비 및 의류"];
    // 다이빙
    var product5 = ["선택", "다이빙 장비", "다이빙 서비스", "다이빙 교육", "스킨스쿠버", "스노우쿨링"];
    // 아웃도어
    var product6 = ["선택", "트레일러", "견인장치", "캠핑카", "아웃도어용품"];
    // 해양관광
    var product7 = ["선택", "관광 서비스", "해양레저 서비스", "보트대여", "요트대여"];
    // 해양부품
    var product8 = ["선택", "선외기", "선내기", "스턴드라이브 엔진", "가이드보터", "프로펠러", "마린스피커", "케이블류", "엘커링", "무어링", "어군탐지기", "네비게이션", "레이더", "무선통신장비", "기타"];
    // 마리나산업
    var product9 = ["선택", "선박 보관 임대", "선박 유지보수", "방제장비", "워터프론트 개발", "보트용 전자장비", "도시/광택", "보트 소재 및 원료"];

    // 제품 검색하기 선택 박스 초기화
    $("select[name^=productOptionBig]").each(function () {
        $selproductOptionBig = $(this);
        $.each(eval(product0), function () {
            $selproductOptionBig.append("<option value='" + this + "'>" + this + "</option>");
        });
        $selproductOptionBig.next().append("<option value=''>선택</option>");
    });

    // 옵션 1차 선택시 하위옵션 설정
    $("select[name^=productOptionBig]").on('change', function () {
        var product = "product" + $("option", $(this)).index($("option:selected", $(this)));
        var $productOptionSmall = $(this).next();
        $("option", $productOptionSmall).remove();

        if (product == "product0") {
            $productOptionSmall.prop("disabled", true).append("<option value=''>선택</option>");
            $("#prdLength").slider("disable", true);
            $("#prdHp").slider("disable", true);
        } else {
            $.each(eval(product), function () {
                $productOptionSmall.append("<option value='" + this + "'>" + this + "</option>");
            });
            $productOptionSmall.prop("disabled", false);
        }

        // 슬라이더 값 초기화
        if (product !== "product1") {
            prdLengthValues = [0, 40]; // 초기값으로 설정
            $("#prdLengthAmount").val(prdLengthValues[0] + ' - ' + prdLengthValues[1]);
            $("#prdLength").slider("values", prdLengthValues);
        }

        if (product !== "product8") {
            prdHpValues = [0, 500]; // 초기값으로 설정
            $("#prdHpAmount").val(prdHpValues[0] + ' - ' + prdHpValues[1]);
            $("#prdHp").slider("values", prdHpValues);
        }
        
        if (product === "product1") {
            $("#prdLength").slider("enable");
            $("#prdLength").removeClass("disabled-slider");
            $("#prdLength").prevAll().removeClass("disabled-slider");
            $("#prdLength").nextAll().removeClass("disabled-slider");
        } else {
            $("#prdLength").slider("disable");
            $("#prdLength").addClass("disabled-slider");
            $("#prdLength").prevAll().addClass("disabled-slider");
            $("#prdLength").nextAll().addClass("disabled-slider");
        }
        
        if (product === "product8") {
            $("#prdHp").slider("enable");
            $("#prdHp").removeClass("disabled-slider");
            $("#prdHp").prevAll().removeClass("disabled-slider");
            $("#prdHp").nextAll().removeClass("disabled-slider");
        } else {
            $("#prdHp").slider("disable");
            $("#prdHp").addClass("disabled-slider");
            $("#prdHp").prevAll().addClass("disabled-slider");
            $("#prdHp").nextAll().addClass("disabled-slider");
        }

    });
    
    // 옵션1 선택 시 하위옵션 활성화
    $("select[name^=productOptionBig]").next().prop("disabled", true);

});


