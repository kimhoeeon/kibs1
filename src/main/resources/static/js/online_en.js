$(document).ready(function () {

    let sel_txt = 'Select';

    // 제품 검색하기 옵션
    var product0 = [sel_txt, "Boats & Yachts", "Non-powered Boats", "Work Boats", "Marine Parts", "Safety & Marina", "Marine Leisure", "Diving Leisure", "Surfing", "Marine Tourism"];
    // 1. 보트&요트
    var product1 = [sel_txt, "Power Boats", "Sailing Yachts", "Inflatable Boats", "RIB Boats", "FRP Boats", "Aluminum Boats", "Carbon Boats", "Composite Boats"];
    // 2. 무동력보트
    var product2 = [sel_txt, "Canoes", "Kayaks", "Rowing Boats", "Rowboats", "SUP", "Dinghies", "Non-powered Boats"];
    // 3. 워크보트
    var product3 = [sel_txt, "Tour Boats", "Fire Boats", "Rescue Boats", "Patrol Boats", "Government Boats", "Speed Boats", "Special Purpose Vessels"];
    // 4. 해양부품&장비
    var product4 = [sel_txt, "Outboard Motors", "Inboard Engines", "Stern Drive Engines", "Trolling Motors", "Propellers", "Marine Speakers", "Cables", "Anchors", "Mooring Equipment", "Fish Finders", "Navigation System", "Radar", "Marine Communication Equipment", "Other"];
    // 5. 안전&마리나
    var product5 = [sel_txt, "Boat Storage & Rental", "Boat Maintenance", "Safety Equipment", "Waterfront Development", "Marine Electronics", "Coatings & Polishing", "Boat Materials"];
    // 6. 해양레저
    var product6 = [sel_txt, "Personal Watercraft", "Water skiing", "Wakeboarding", "Marine Leisure Service"];
    // 7. 수중레저
    var product7 = [sel_txt, "Diving Equipment", "Diving Services", "Diving Training", "Scuba Diving", "Snorkeling"];
    // 8. 서핑
    var product8 = [sel_txt, "Surf Equipment", "Apparel & Fashion", "Training Programs", "Lifestyle"];
    // 9. 해양관광
    var product9 = [sel_txt, "Marine Leisure Tourism", "Local Tourism Content", "Wellness & Ecotourism", "Experience Programs"];

    // 제품 검색하기 선택 박스 초기화
    $("select[name^=onlineOptionBig]").each(function () {
        $selonlineOptionBig = $(this);
        $.each(eval(product0), function () {
            $selonlineOptionBig.append("<option value='" + this + "'>" + this + "</option>");
        });
        $selonlineOptionBig.next().append("<option value=''>" + sel_txt + "</option>");
    });

    // 옵션 1차 선택시 하위옵션 설정
    $("select[name^=onlineOptionBig]").on('change', function () {
        var product = "product" + $("option", $(this)).index($("option:selected", $(this)));
        var $productOptionSmall = $(this).next();
        $("option", $productOptionSmall).remove();

        if (product == "product0") {
            $productOptionSmall.prop("disabled", true).append("<option value=''>" + sel_txt + "</option>");
        } else {
            $.each(eval(product), function () {
                $productOptionSmall.append("<option value='" + this + "'>" + this + "</option>");
            });
            $productOptionSmall.prop("disabled", false);
        }
    });
    
    // 옵션1 선택 시 하위옵션 활성화
    $("select[name^=onlineOptionBig]").next().prop("disabled", true);

});


