$(document).ready(function () {

    let lang = f_lang_get();

    let sel_txt = '선택';

    // 제품 검색하기 옵션
    var product0 = [sel_txt, "보트&요트", "무동력보트", "워크보트", "해양부품&장비", "안전&마리나", "해양레저", "수중레저", "서핑", "해양관광"];
    // 1. 보트&요트
    var product1 = [sel_txt, "파워보트", "세일요트", "고무보트", "콤비보트", "FRP보트", "알루미늄보트", "카본보트", "복합소재보트"];
    // 2. 무동력보트
    var product2 = [sel_txt, "카누", "카약", "조정", "노보트", "SUP", "딩기요트", "무동력보트"];
    // 3. 워크보트
    var product3 = [sel_txt, "관공선", "소방선", "구조선", "감시선", "행정선", "고속단정", "특수선박"];
    // 4. 해양부품&장비
    var product4 = [sel_txt, "선외기", "선내기", "스턴드라이브 엔진", "가이드모터", "프로펠러", "마린스피커", "케이블류", "앵커", "무어링", "어군탐지기", "네비게이션", "레이더", "무선통신장비", "기타"];
    // 5. 안전&마리나
    var product5 = [sel_txt, "선박 보관 임대", "선박 유지보수", "방제장비", "워터프론트 개발", "보트용 전자장비", "도시/광택", "보트 소재 및 원료"];
    // 6. 해양레저
    var product6 = [sel_txt, "수상오토바이", "수상스키", "웨이크보드", "해양레저 서비스"];
    // 7. 수중레저
    var product7 = [sel_txt, "다이빙 장비", "다이빙 서비스", "다이빙 교육", "스킨스쿠버", "스노우쿨링"];
    // 8. 서핑
    var product8 = [sel_txt, "서핑장비", "의류&패션", "체험&교육프로그램", "라이프스타일"];
    // 9. 해양관광
    var product9 = [sel_txt, "해양레저 관광상품", "지역관광콘텐츠", "치유&생태관광", "체험 콘텐츠"];

    /*if(lang === 'E'){
        sel_txt = 'Select';

        product0 = [sel_txt, "boat&yacht", "paddler's world", "work boat", "marine leisure", "surfing", "underwater leisure", "marine tourism", "parts&equipment", "safety&marina", "Others"];
        product1 = [sel_txt, "Power Boat", "Sailing Yacht", "Inflatable Boat", "Rigid-hulled Inflatable Boat(RHIB)", "Fiberglass Boat", "Aluminum Boat", "Carbon Fiber Boat", "Composite Boat"];
        product2 = [sel_txt, "Canoe", "Kayak", "Rowing Shell", "Rowboat", "Stand-Up Paddleboard", "Dinghy", "Sail-Only Yacht"];
        product3 = [sel_txt, "Government Vessel", "Firefighting Vessel", "Rescue Vessel", "Patrol Vessel", "Public Service Vessel", "High-Speed RIB", "Special Purpose Vessel"];
        product4 = [sel_txt, "Personal Watercraft", "Water Skis", "Wakeboard", "Marine Leisure Services"];
        product5 = [sel_txt, "Surfing Equipment", "Surfwear & Lifestyle", "Surfing Experience"];
        product6 = [sel_txt, "Diving Equipment", "Diving Services", "Diving Education", "Scuba Diving", "Snorkeling"];
        product7 = [sel_txt, "Tourism Services", "Boat Rental", "Yacht Rental", "Tour Products"];
        product8 = [sel_txt, "Outboard Engine", "Inboard Engine", "Stern Drive Engine", "Trolling Motor", "Propeller", "Marine Speaker", "Marine Cables", "Anchor", "Mooring", "Fish Finder", "Marine Navigation System", "Marine Radar", "Marine Radio Equipment", "ETC"];
        product9 = [sel_txt, "Boat Storage and Rental", "Boat Maintenance and Repair", "Spill Response Equipment", "Waterfront Development", "Marine Electronics for Boats", "Boat Polishing/Detailing", "Boat Materials and Raw Components"];
        product10= [sel_txt, "Trailer", "Towing Equipment", "Recreational Vehicle", "Outdoor Equipment", "Others"];
    }*/

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


