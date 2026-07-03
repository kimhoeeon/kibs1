// 행사별 설문 항목 설정
const surveyConfig = {
    "경기국제보트쇼": {
        observationGbn: [
            { value: "조종면허 보유자", text: "Boat License Holder" },
            { value: "보트 소유자", text: "Boat Owner" },
            { value: "보트 구매 예정자", text: "Prospective Boat Buyer" },
            { value: "관련 업종 종사자", text: "Industry Professional" },
            { value: "낚시 매니아", text: "Fishing Enthusiast" },
            { value: "캠핑카 매니아", text: "RV Enthusiast" },
            { value: "학생", text: "Student" },
            { value: "일반관람", text: "General Visitor" },
            { value: "기타", text: "Others" }
        ],
        visitPurpose: [
            { value: "업계동향 파악 및 정보수집", text: "Trend Analysis & Information Gathering" },
            { value: "제품구매 및 기술도입 상담", text: "Purchasing & Tech Consultation" },
            { value: "기존 거래업체 방문", text: "Visiting Existing Partners" },
            { value: "차기 전시회 참가여부 파악", text: "Assessing Future Participation" },
            { value: "일반관람", text: "General Viewing" },
            { value: "기타", text: "Others" }
        ],
        visitPurposeTitle: "Purpose of Visit",
        interestItem: [
            { value: "보트&요트", text: "Boats & Yachts" },
            { value: "무동력보트", text: "Non-powered Boats" },
            { value: "워크보트", text: "Work Boats" },
            { value: "해양부품&장비", text: "Marine Parts & Equipment" },
            { value: "안전&마리나", text: "Safety & Marina" },
            { value: "해양관광", text: "Marine Tourism" },
            { value: "해양레저", text: "Marine Leisure" },
            { value: "서핑", text: "Surfing" },
            { value: "수중레저", text: "Diving Leisure" }
        ],
        recognizePath: [
            { value: "뉴스레터", text: "Newsletter" },
            { value: "옥외광고물", text: "Outdoor Advertisement" },
            { value: "홈페이지", text: "Website" },
            { value: "지인추천", text: "Friend's Recommendation" },
            { value: "온라인 커뮤니티", text: "Online Community" },
            { value: "유관 협회안내", text: "Association Notice" },
            { value: "소셜 네트워크(SNS)", text: "Social Media (SNS)" },
            { value: "초청장(모바일/지류)", text: "Invitation (Mobile/Paper)" },
            { value: "방송광고", text: "Broadcast Ads" },
            { value: "KIBS 참석", text: "Attended past KIBS" },
            { value: "낚시박람회", text: "Fishing Show" },
            { value: "기타", text: "Others" }
        ],
        preObservationGbn: [
            { value: "첫 참관", text: "First Time" },
            { value: "2008", text: "2008" }, { value: "2009", text: "2009" }, { value: "2010", text: "2010" },
            { value: "2011", text: "2011" }, { value: "2012", text: "2012" }, { value: "2013", text: "2013" },
            { value: "2014", text: "2014" }, { value: "2015", text: "2015" }, { value: "2016", text: "2016" },
            { value: "2017", text: "2017" }, { value: "2018", text: "2018" }, { value: "2019", text: "2019" },
            { value: "2020", text: "2020" }, { value: "2021", text: "2021" }, { value: "2022", text: "2022" },
            { value: "2023", text: "2023" }, { value: "2024", text: "2024" }, { value: "2025", text: "2025" },
            { value: "2026", text: "2026" }
        ],
        hasPreObservation: true // 지난 전시회 참관 여부 표시
    },
    "코리아서프쇼": {
        observationGbn: [
            { value: "서핑 매니아/입문", text: "Surfing Enthusiast/Beginner" },
            { value: "다이빙 매니아/입문", text: "Diving Enthusiast/Beginner" },
            { value: "해양레저 관심자", text: "Interested in Marine Leisure" },
            { value: "관련 업종 종사자", text: "Industry Professional" },
            { value: "해양레저 체험 관람", text: "Marine Leisure Experience" },
            { value: "학생", text: "Student" },
            { value: "일반관람", text: "General Visitor" },
            { value: "기타", text: "Others" }
        ],
        visitPurpose: [
            { value: "업계동향 파악 및 정보수집", text: "Trend Analysis & Info" },
            { value: "서핑·다이빙 관련 용품 구매 상담", text: "Surfing/Diving Equipment Purchase" },
            { value: "기존 거래업체 방문", text: "Visiting Existing Partners" },
            { value: "체험 프로그램 참가", text: "Participating in Experience Programs" },
            { value: "일반관람", text: "General Viewing" },
            { value: "기타", text: "Others" }
        ],
        visitPurposeTitle: "Purpose of Visit",
        interestItem: [
            { value: "서핑장비", text: "Surfing Equipment" },
            { value: "의류·패션", text: "Apparel & Fashion" },
            { value: "체험 및 교육 프로그램", text: "Experience & Education Program" },
            { value: "라이프스타일", text: "Lifestyle" }
        ],
        recognizePath: [
            { value: "뉴스레터", text: "Newsletter" },
            { value: "옥외광고물", text: "Outdoor Advertisement" },
            { value: "홈페이지", text: "Website" },
            { value: "지인추천", text: "Friend's Recommendation" },
            { value: "온라인 커뮤니티(서프엑스 등)", text: "Online Community" },
            { value: "유관 협회안내", text: "Association Notice" },
            { value: "소셜 네트워크(SNS)", text: "Social Media (SNS)" },
            { value: "모바일 초청장", text: "Mobile Invitation" },
            { value: "방송광고", text: "Broadcast Ads" },
            { value: "KIBS 참석", text: "Attended past KIBS" },
            { value: "기타", text: "Others" }
        ],
        hasPreObservation: false
    },
    "해양관광전": {
        observationGbn: [
            { value: "해양관광 예정자", text: "Prospective Marine Tourist" },
            { value: "해양관광 관심자", text: "Interested in Marine Tourism" },
            { value: "지역관광 관심자", text: "Interested in Local Tourism" },
            { value: "관련 업종 종사자", text: "Industry Professional" },
            { value: "요트투어 희망자", text: "Yacht Tour Hopeful" },
            { value: "가족여행 목적 관람객", text: "Family Trip Visitor" },
            { value: "지자체 공무원", text: "Local Government Official" },
            { value: "학생", text: "Student" },
            { value: "일반관람", text: "General Visitor" },
            { value: "기타", text: "Others" }
        ],
        visitPurpose: [
            { value: "업계동향 파악 및 정보수집", text: "Trend Analysis & Info" },
            { value: "지역 관광상품 및 프로그램 파악", text: "Local Tourism Products & Programs" },
            { value: "기존 거래업체 방문", text: "Visiting Existing Partners" },
            { value: "해양관광상품 상담", text: "Marine Tourism Products Consultation" },
            { value: "일반관람", text: "General Viewing" },
            { value: "기타", text: "Others" }
        ],
        visitPurposeTitle: "Purpose of Visit",
        interestItem: [
            { value: "해양레저 관광상품", text: "Marine Leisure Tourism Products" },
            { value: "지역관광 콘텐츠", text: "Regional Tourism Content" },
            { value: "치유 및 생태관광", text: "Healing & Ecotourism" },
            { value: "체험 콘텐츠", text: "Experience Content" }
        ],
        recognizePath: [
            { value: "뉴스레터", text: "Newsletter" },
            { value: "옥외광고물", text: "Outdoor Advertisement" },
            { value: "홈페이지", text: "Website" },
            { value: "지인추천", text: "Friend's Recommendation" },
            { value: "온라인 커뮤니티", text: "Online Community" },
            { value: "소셜 네트워크(SNS)", text: "Social Media (SNS)" },
            { value: "모바일 초청장", text: "Mobile Invitation" },
            { value: "방송광고", text: "Broadcast Ads" },
            { value: "KIBS 참석", text: "Attended past KIBS" },
            { value: "유관기간 공문 안내", text: "Official Notice from Related Organizations" },
            { value: "기타", text: "Others" }
        ],
        hasPreObservation: false
    }
};

// 설문 항목 동적 렌더링 함수
function renderSurveyItems(eventName, savedData) {
    const config = surveyConfig[eventName];
    if (!config) return;

    // 1. 관람 구분
    makeCheckboxList('observationGbn', config.observationGbn, savedData?.observationGbn);

    // 2. 방문 목적
    $('#visitPurposeTitle').text(config.visitPurposeTitle);
    makeCheckboxList('visitPurpose', config.visitPurpose, savedData?.visitPurpose);

    // 3. 관심 품목
    makeCheckboxList('interestItem', config.interestItem, savedData?.interestItem);

    // 4. 인지 경로
    makeCheckboxList('recognizePath', config.recognizePath, savedData?.recognizePath);

    // 5. 지난 전시회 참관 여부 (수정됨: 동적 생성 방식 적용)
    if (config.hasPreObservation) {
        $('#preObservationGbnLi').show();
        // ID를 ul_preObservationGbn으로 타겟팅하여 생성
        makeCheckboxList('preObservationGbn', config.preObservationGbn, savedData?.preObservationGbn);
    } else {
        $('#preObservationGbnLi').hide();
        $('#ul_preObservationGbn').empty(); // 숨길 때 내용 비우기
    }
}

// 체크박스 HTML 생성 헬퍼
function makeCheckboxList(name, items, savedString) {
    // JSP에서 부여한 ID (예: ul_observationGbn)
    const $container = $('#ul_' + name);
    $container.empty();

    let savedArr = [];
    if (savedString) {
        savedArr = savedString.split(',').map(s => s.trim());
    }

    let html = '';
    items.forEach((opt, index) => {
        let id = name + '_' + index;
        // value 값을 기준으로 체크 여부를 확인합니다.
        let checked = savedArr.includes(opt.value) ? 'checked' : '';

        // 관리자 상세 페이지용 구조 (isDetail hidden input 값 확인)
        if ($('#isDetail').val() === 'Y') {
            html += `
                <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                    <input class="form-check-input form-control-solid-bg" type="checkbox" id="${id}" name="${name}" value="${opt.value}" ${checked} />
                    <label class="form-check-label text-hover-primary" for="${id}">${opt.text}</label>
                </div>
            `;
        } else { // 사용자(신청/수정) 페이지용 구조
            // 노출은 opt.text, 전송 값은 opt.value
            html += `<label><input type="checkbox" name="${name}" value="${opt.value}" ${checked}>${opt.text}</label>`;
        }
    });

    // [요청사항 적용] 복수응답가능 문구 추가
    if ($('#isDetail').val() === 'Y') {
        html += '<div class="col-lg-12 mt-2 text-primary fw-bold">* Multiple selections allowed</div>';
    } else {
        // 사용자 페이지 스타일 (.cmnt 클래스 사용)
        html += '<div class="cmnt" style="width:100%; display:block; margin-top:5px;">Multiple selections allowed</div>';
    }

    $container.html(html);
}

// 지난 전시회 참관 여부 "첫 참관" 배타적 선택 로직
$(document).on('change', 'input[name="preObservationGbn"]', function() {
    let currentVal = $(this).val();
    let isChecked = $(this).is(':checked');

    if (currentVal === '첫 참관') {
        if (isChecked) {
            // "첫 참관" 선택 시 나머지 모두 해제
            $('input[name="preObservationGbn"]').not(this).prop('checked', false);
        }
    } else {
        if (isChecked) {
            // 다른 연도 선택 시 "첫 참관" 해제
            $('input[name="preObservationGbn"][value="첫 참관"]').prop('checked', false);
        }
    }
});