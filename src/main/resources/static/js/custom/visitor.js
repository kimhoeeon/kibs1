// 행사별 설문 항목 설정
const surveyConfig = {
    "경기국제보트쇼": {
        observationGbn: ["조종면허 보유자", "보트 소유자", "보트 구매 예정자", "관련 업종 종사자", "낚시 매니아", "캠핑카 매니아", "학생", "일반관람", "기타"],
        visitPurpose: ["업계동향 파악 및 정보수집", "제품구매 및 기술도입 상담", "기존 거래업체 방문", "차기 전시회 참가여부 파악", "일반관람", "기타"],
        visitPurposeTitle: "보트쇼 방문 목적",
        interestItem: ["보트&요트", "무동력보트", "워크보트", "해양부품&장비", "안전&마리나", "해양관광", "해양레저", "서핑", "수중레저"],
        recognizePath: ["뉴스레터", "옥외광고물", "홈페이지", "지인추천", "온라인 커뮤니티", "유관 협회안내", "소셜 네트워크(SNS)", "초청장(모바일/지류)", "방송광고", "KIBS 참석", "낚시박람회", "기타"],
        preObservationGbn: ["첫 참관", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026"],
        hasPreObservation: true // 지난 전시회 참관 여부 표시
    },
    "코리아서프쇼": {
        observationGbn: ["서핑 매니아/입문", "다이빙 매니아/입문", "해양레저 관심자", "관련 업종 종사자", "해양레저 체험 관람", "학생", "일반관람", "기타"],
        visitPurpose: ["업계동향 파악 및 정보수집", "서핑·다이빙 관련 용품 구매 상담", "기존 거래업체 방문", "체험 프로그램 참가", "일반관람", "기타"],
        visitPurposeTitle: "서프쇼 방문 목적",
        interestItem: ["서핑장비", "의류·패션", "체험 및 교육 프로그램", "라이프스타일"],
        recognizePath: ["뉴스레터", "옥외광고물", "홈페이지", "지인추천", "온라인 커뮤니티(서프엑스 등)", "유관 협회안내", "소셜 네트워크(SNS)", "모바일 초청장", "방송광고", "KIBS 참석", "기타"],
        hasPreObservation: false
    },
    "해양관광전": {
        observationGbn: ["해양관광 예정자", "해양관광 관심자", "지역관광 관심자", "관련 업종 종사자", "요트투어 희망자", "가족여행 목적 관람객", "지자체 공무원", "학생", "일반관람", "기타"],
        visitPurpose: ["업계동향 파악 및 정보수집", "지역 관광상품 및 프로그램 파악", "기존 거래업체 방문", "해양관광상품 상담", "일반관람", "기타"],
        visitPurposeTitle: "해양관광전 방문 목적",
        interestItem: ["해양레저 관광상품", "지역관광 콘텐츠", "치유 및 생태관광", "체험 콘텐츠"],
        recognizePath: ["뉴스레터", "옥외광고물", "홈페이지", "지인추천", "온라인 커뮤니티", "소셜 네트워크(SNS)", "모바일 초청장", "방송광고", "KIBS 참석", "유관기간 공문 안내", "기타"],
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
    items.forEach((item, index) => {
        let id = name + '_' + index;
        let checked = savedArr.includes(item) ? 'checked' : '';

        // 관리자 상세 페이지용 구조 (isDetail hidden input 값 확인)
        if ($('#isDetail').val() === 'Y') {
            html += `
                <div class="form-check form-check-custom form-check-lg mb-3 mr15">
                    <input class="form-check-input form-control-solid-bg" type="checkbox" id="${id}" name="${name}" value="${item}" ${checked} />
                    <label class="form-check-label text-hover-primary" for="${id}">${item}</label>
                </div>
            `;
        } else { // 사용자(신청/수정) 페이지용 구조
            html += `<label><input type="checkbox" name="${name}" value="${item}" ${checked}>${item}</label>`;
        }
    });

    // [요청사항 적용] 복수응답가능 문구 추가
    if ($('#isDetail').val() === 'Y') {
        html += '<div class="col-lg-12 mt-2 text-primary fw-bold">* 복수응답가능</div>';
    } else {
        // 사용자 페이지 스타일 (.cmnt 클래스 사용)
        html += '<div class="cmnt" style="width:100%; display:block; margin-top:5px;">복수응답가능</div>';
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