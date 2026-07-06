$(document).ready(function () {

    let sel_txt = 'Select';

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
    var product0 = [sel_txt, "Boat&Yacht", "Non-powered Boats", "Work Boats", "Marine Leisure"];
    // 보트&요트
    var product1 = [sel_txt, "Power Boats", "Sailing Yachts", "Inflatable Boats", "RIB Boats", "FRP Boats", "Aluminum Boats", "Carbon Boats", "Composite Boats"];
    // 무동력보트
    var product2 = [sel_txt, "Canoes", "Kayaks", "Rowing Boats", "Rowboats", "SUP", "Dinghies", "Non-powered Boats"];
    // 워크보트
    var product3 = [sel_txt, "Tour Boats", "Fire Boats", "Rescue Boats", "Patrol Boats", "Government Boats", "Speed Boats", "Special Purpose Vessels"];
    // 해양레저
    var product4 = [sel_txt, "Water Motorcycle", "Water Skiing", "Wakeboard", "Marine Leisure Services"];

    // 제품 검색하기 선택 박스 초기화
    $("select[name^=productOptionBig]").each(function () {
        $selproductOptionBig = $(this);
        $.each(eval(product0), function () {
            $selproductOptionBig.append("<option value='" + this + "'>" + this + "</option>");
        });
        $selproductOptionBig.next().append("<option value=''>" + sel_txt + "</option>");
    });

    // 옵션 1차 선택시 하위옵션 설정
    $("select[name^=productOptionBig]").on('change', function () {
        var product = "product" + $("option", $(this)).index($("option:selected", $(this)));
        var $productOptionSmall = $(this).next();
        $("option", $productOptionSmall).remove();

        if (product == "product0") {
            $productOptionSmall.prop("disabled", true).append("<option value=''>" + sel_txt + "</option>");
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


