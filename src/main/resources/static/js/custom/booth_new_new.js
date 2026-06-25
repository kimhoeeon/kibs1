/***
 * mng/exhibitor/application/booth.js
 * 참가신청서 관리 > 전시부스 신청
 * */
var transferYear = '2027';

$(function(){

    let myModalEl = document.getElementById('kt_modal_invoice_form');

    if(myModalEl){

        let myModal = new bootstrap.Modal('#kt_modal_invoice_form', {
            focus: true
        });

        myModalEl.addEventListener('hidden.bs.modal', event => {
            // input init
            $('#detailForm').removeAttr('src');
        })

        $('#previewBtn').on('click', function () {
            let seq = $('input[type=checkbox][name=invoiceSeq]:checked').val();
            if(nvl(seq,'') !== ''){
                $('#detailForm').removeAttr('src');
                $('#detailForm').attr('src','/mng/exhibitorNewNew/application/booth/invoice/detail.do?seq=' + seq);
                myModal.show();
            }else{
                showMessage('', 'info', '[ 인보이스 ]', '인보이스 내역을 선택해 주세요.', '');
            }
        })
    }//myModalEl

});

function f_application_booth_new_search_condition_init(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#transferYear').val('').select2({minimumResultsForSearch: Infinity});
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#boothGbn').val('').select2({minimumResultsForSearch: Infinity});
    $('#discountYn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_booth_new_search();
}

function f_application_booth_new_search(){

    let search_box = $('#search_box option:selected').val();
    let search_text = $('#search_text').val();

    if(search_box === 'boothCnt'){
        let check = /^[0-9]+$/;
        if (!check.test(search_text)) {
            Swal.fire({
                icon: 'info',
                title: '[ 검색 조건 확인 ]',
                html: '<span style="font-size: 1.2em;">검색 조건이 신청갯수일 경우<br>숫자로만 검색 가능합니다.</span>',
                allowOutsideClick: false,
                confirmButtonColor: '#00a8ff',
                confirmButtonText: '확인'
            }).then((result) => {
                if (result.isConfirmed) {
                    $('#search_text').val(''); // 검색박스 초기화
                }
            });
            return false;
        }
    }

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_booth_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    if(nullToEmpty(search_text) === ""){
        jsonObj = {
            transferYear: transferYear
        };
    }else{
        jsonObj = {
            transferYear: transferYear,
            condition: search_box,
            searchText: search_text
        }
    }

    let resData = ajaxConnect('/mng/exhibitorNewNew/application/booth/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_booth_new_table tbody td');
    let cnt = 0;
    jb.each(function(index, item){
        let itemText = $(item).text();
        let itemText_trim = itemText.replaceAll(' ','');
        if(itemText_trim !== '' && !itemText.match('Actions')){
            $(item).attr('data-bs-toggle', 'tooltip');
            $(item).attr('data-bs-trigger', 'hover');
            $(item).attr('data-bs-custom-class', 'tooltip-inverse');
            $(item).attr('data-bs-placement', 'top');
            $(item).attr('title',itemText);
        }
        cnt++;
    })
    jb.tooltip();
}

function f_search_condition_sel_change(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_booth_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let fieldParticipatory = $('#condition_field_participatory option:selected').val();
    let transferYear = $('#transferYear option:selected').val(); //참가년도
    let lang = $('#lang option:selected').val(); //참가신청언어
    let approvalYn = $('#approvalYn option:selected').val(); //승인여부
    let cancelYn = $('#cancelYn option:selected').val(); //참가취소여부
    let boothGbn = $('#boothGbn option:selected').val(); //부스구분
    let discountYn = $('#discountYn option:selected').val(); //부스구분

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        fieldParticipatory: fieldParticipatory,
        transferYear: transferYear,
        lang: lang,
        approvalStatus: approvalYn,
        cancelYn: cancelYn,
        boothType: boothGbn,
        discountYn: discountYn,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitorNewNew/application/booth/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_booth_new_table tbody td');
    let cnt = 0;
    jb.each(function(index, item){
        let itemText = $(item).text();
        let itemText_trim = itemText.replaceAll(' ','');
        if(itemText_trim !== ''){
            $(item).attr('data-bs-toggle', 'tooltip');
            $(item).attr('data-bs-trigger', 'hover');
            $(item).attr('data-bs-custom-class', 'tooltip-inverse');
            $(item).attr('data-bs-placement', 'top');
            $(item).attr('title',itemText);
        }
        cnt++;
    })
    jb.tooltip();
}

function f_application_booth_new_modify_init_set(seq){
    window.location.href = '/mng/exhibitorNewNew/application/booth/detail.do?seq=' + seq;
}

function f_application_booth_new_save(seq){

    Swal.fire({
        icon: 'info',
        title: '[ 전시부스 정보 ]',
        html: '<span style="font-size: 1.2em;">입력된 정보를 저장하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '변경내용저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* form data setting */
            let data = f_booth_form_data_setting(seq);

            $.ajax({
                url: '/mng/exhibitorNewNew/application/booth/update.do',
                method: 'POST',
                async: false,
                data: data,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        Swal.fire({
                            icon: 'info',
                            title: '[ 전시부스 정보 ]',
                            html: '<span style="font-size: 1.2em;">전시 부스 정보가 저장되었습니다.</span>',
                            allowOutsideClick: false,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_application_booth_new_modify_init_set(seq); // 재조회
                            }
                        });

                    } else {
                        showMessage('', 'error', '에러 발생', '전시 부스 정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })

        }//isConfirmed
    })//then

}

function f_booth_form_data_setting(exhibitorSeq){

    // --- 1. 부스 정보 수집 ---
    let boothPrcSum = 0;

    // 부스 타입 문자열 (예: "등록비,독립부스,조립부스")
    let boothType = '등록비';

    // 등록비 (기본 포함)
    const registrationCnt = 1;
    const registrationFee = 100000;
    boothPrcSum += registrationFee;

    // 독립부스
    const standAloneBoothCnt = parseInt($('#standAloneBoothCnt').val()) || 0;
    const standAloneBoothFee = wonToNumber($('#standAloneBoothFee').val());
    if (standAloneBoothCnt > 0) {
        boothType += ',독립부스';
    }
    boothPrcSum += standAloneBoothFee;

    // 조립부스
    const assemblyBoothCnt = parseInt($('#assemblyBoothCnt').val()) || 0;
    const assemblyBoothFee = wonToNumber($('#assemblyBoothFee').val());
    if (assemblyBoothCnt > 0) {
        boothType += ',조립부스';
    }
    boothPrcSum += assemblyBoothFee;

    // 온라인부스
    const onlineBoothCnt = parseInt($('#onlineBoothCnt').val()) || 0;
    const onlineBoothFee = wonToNumber($('#onlineBoothFee').val());
    if (onlineBoothCnt > 0) {
        boothType += ',온라인부스';
    }
    boothPrcSum += onlineBoothFee;


    // --- 2. 할인 정보 수집 및 계산 (전면 수정) ---
    let discountType = '';
    let discountPrcSum = 0;

    // ❗ 중요: 할인은 온라인 부스를 제외한 '오프라인 부스'에만 적용됩니다.
    const physicalBooths = standAloneBoothCnt + assemblyBoothCnt;

    // ✅ 개선: 체크된 모든 할인을 순회하며 할인 총액과 할인 타입을 동적으로 생성합니다.
    $('input[name="discount"]:checked').each(function() {
        const id = $(this).attr('id');
        const discountAmount = parseInt($(this).data('discount')) || 0;

        discountPrcSum += (physicalBooths * discountAmount);

        // discountType 문자열을 ID 기반으로 생성
        switch(id) {
            case 'discountEarly1': discountType += ',(1차)조기신청'; break;
            case 'discountEarly2': discountType += ',(2차)조기신청'; break;
            case 'discountScale1': discountType += ',규모할인(10+)'; break;
            case 'discountScale2': discountType += ',규모할인(20+)'; break;
            case 'discountScale3': discountType += ',규모할인(30+)'; break;
            case 'discountScale4': discountType += ',규모할인(40+)'; break;
            case 'discountScale5': discountType += ',규모할인(50+)'; break;
            case 'discountScale6': discountType += ',규모할인(100+)'; break;
            case 'discountRe': discountType += ',재참가'; break;
            case 'discountFirstUnder10': discountType += ',첫참가(10미만)'; break;
            case 'discountFirstOver10': discountType += ',첫참가(10이상)'; break;
            case 'discountLeisure': discountType += ',해양레저산업협회'; break;
        }
    });

    // 맨 앞의 쉼표(,) 제거
    if (discountType.startsWith(',')) {
        discountType = discountType.substring(1);
    }

    const discountYn = (discountType !== '') ? 'Y' : 'N';

    // --- 3. 서버 전송 데이터 생성 (JSON) ---
    const totalBooths = standAloneBoothCnt + assemblyBoothCnt + onlineBoothCnt;
    if (totalBooths === 0) {
        alert('부스(독립, 조립, 온라인)를 하나 이상 신청해 주세요.');
        return;
    }

    // 서버로 보낼 최종 데이터 객체
    const booth_json_obj = {
        seq: exhibitorSeq,
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registrationCnt,
        registrationFee: registrationFee,
        standAloneBoothCnt: standAloneBoothCnt,
        standAloneBoothFee: standAloneBoothFee,
        assemblyBoothCnt: assemblyBoothCnt,
        assemblyBoothFee: assemblyBoothFee,
        onlineBoothCnt: onlineBoothCnt,
        onlineBoothFee: onlineBoothFee,
        // 각 할인 항목의 선택 여부(true/false)를 전송
        discountEarly1: $('#discountEarly1').is(':checked'),
        discountEarly2: $('#discountEarly2').is(':checked'),
        discountFirstUnder10: $('#discountFirstUnder10').is(':checked'), // 수정
        discountFirstOver10: $('#discountFirstOver10').is(':checked'), // 수정
        discountRe: $('#discountRe').is(':checked'),
        discountScale1: $('#discountScale1').is(':checked'),
        discountScale2: $('#discountScale2').is(':checked'),
        discountScale3: $('#discountScale3').is(':checked'),
        discountScale4: $('#discountScale4').is(':checked'),
        discountScale5: $('#discountScale5').is(':checked'),
        discountScale6: $('#discountScale6').is(':checked'),
        discountLeisure: $('#discountLeisure').is(':checked'),
        discountYn: discountYn,
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum
    };

    return JSON.stringify(booth_json_obj);
}

function minCnt(el, cnt){
    let val = $(el).val() || 0;
    if(val < cnt){
        if(val !== 0){
            alert('독립부스는 2부스부터 신청 가능합니다.');
            $(el).val(0);
            calculateTotal();
        }
    }
}

function checkBooth(){
    let standAloneBoothCnt = $.number((parseInt($('#stand_alone_booth_cnt').val() || 0)));
    let assemblyBoothCnt = $.number((parseInt($('#assembly_booth_cnt').val() || 0)));
    let onlineBoothCnt = $.number((parseInt($('#online_booth_cnt option:selected').val() || 0)));
    if((standAloneBoothCnt > 0 || assemblyBoothCnt > 0) && onlineBoothCnt > 0){
        alert('조립부스 또는 독립부스 신청 시, 온라인 부스는 무료 지원됩니다.');
        $('#online_booth_cnt').val(0);
        $('#online_booth_cnt option').eq(0).prop('selected',true);
        autoSum(3);
    }
}

/***************************************************************************************
 * 할인 조건 Function Start
 * *************************************************************************************/
// --- 전역 상수 설정 ---

const boothPrices = {
    standAlone: 1800000,
    assembly: 2100000,
    online: 1000000
};
const registrationFee = 100000; // 기본 등록비

// 🗑️ 삭제: 새로운 할인 로직에서는 더 이상 사용되지 않는 상수들입니다.
// const discount3BaseAmount = 500000;
// const discount3ChangedAmount = 300000;
// const singleChoiceDiscounts = [ ... ];

// --- 조기신청 할인 기간 설정 ---

// 현재 시간 (페이지 로드 시점 기준)
const now = new Date();

// 1차 조기신청 마감일
const discount1Deadline = new Date('2025-11-14T23:59:59');

// 2차 조기신청 기간
const discount2StartDate = new Date('2025-11-15T00:00:00');
const discount2Deadline = new Date('2025-12-12T23:59:59');

/**
 * 오프라인 부스 수량에 따라 규모 할인을 자동으로 선택하고 비활성화하는 함수
 */
function updateScaleDiscountState(physicalBooths) {
    const scaleCheckboxes = $('.single-choice-discount input[type="checkbox"]');

    // 1. 모든 규모 할인 체크박스를 비활성화하여 사용자가 직접 수정하지 못하게 합니다.
    //scaleCheckboxes.prop('disabled', true);

    // 2. 모든 규모 할인 체크를 초기화합니다.
    scaleCheckboxes.prop('checked', false);

    // 3. 부스 수량에 맞는 할인 항목을 찾아 자동으로 체크합니다.
    let targetCheckbox = null;
    if (physicalBooths >= 100) {
        targetCheckbox = $('#discountScale6');
    } else if (physicalBooths >= 50) {
        targetCheckbox = $('#discountScale5');
    } else if (physicalBooths >= 40) {
        targetCheckbox = $('#discountScale4');
    } else if (physicalBooths >= 30) {
        targetCheckbox = $('#discountScale3');
    } else if (physicalBooths >= 20) {
        targetCheckbox = $('#discountScale2');
    } else if (physicalBooths >= 10) {
        targetCheckbox = $('#discountScale1');
    }

    if (targetCheckbox) {
        targetCheckbox.prop('checked', true);
    }
}

/**
 * 1차 조기신청 할인 체크박스의 상태와 이벤트를 제어합니다.
 * 기간에 따라 자동으로 체크하거나 비활성화합니다.
 */
function handleDiscountEarly1() {
    const discount1Checkbox = $('#discountEarly1');
    const discount1Item = $('#discountItem1');
    const isPrcTotal = $('#prcTotal').val() === "110000"; // 최초 신청 여부

    if (now <= discount1Deadline) { // 1차 할인 기간 내
        // DB에 저장된 값이 없는 최초 신청 시 자동으로 체크
        if (isPrcTotal) {
            discount1Checkbox.prop('checked', true);
        }
        discount1Item.removeClass('disabled');
        discount1Checkbox.prop('disabled', false);

        // 사용자가 직접 할인을 해제하고 다시 선택하지 못하게 하는 로직
        let userUnchecked = false;
        discount1Checkbox.off('change.discountControl').on('change.discountControl', function() {
            if (!$(this).prop('checked')) {
                if(confirm('1차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다. 해제하시겠습니까?')){
                    userUnchecked = true;
                } else {
                    $(this).prop('checked', true);
                }
            } else if (userUnchecked) {
                $(this).prop('checked', false);
                alert('1차 조기신청 할인은 한 번 해제하시면 다시 선택할 수 없습니다.');
            }
            calculateTotal();
        });
    } else { // 1차 할인 기간 종료
        if (discount1Checkbox.prop('checked')) {
            discount1Checkbox.prop('checked', false);
        }
        discount1Checkbox.prop('disabled', true);
        discount1Item.addClass('disabled');
        discount1Item.off('click.preventCheck').on('click.preventCheck', () => {
            alert('1차 조기신청 할인은 기간이 종료되어 선택할 수 없습니다.');
        });
    }
}

/**
 * 2차 조기신청 할인 체크박스의 상태와 이벤트를 제어합니다.
 * 기간에 따라 자동으로 체크하거나 비활성화합니다.
 */
function handleDiscountEarly2() {
    const discount2Checkbox = $('#discountEarly2');
    const discount2Item = $('#discountItem2');
    const isPrcTotal = $('#prcTotal').val() === "110000"; // 최초 신청 여부

    if (now >= discount2StartDate && now <= discount2Deadline) { // 2차 할인 기간 내
        // 1차 할인 기간을 놓친 최초 신청자에게 자동 체크
        if(isPrcTotal) {
            discount2Checkbox.prop('checked', true);
        }
        discount2Item.removeClass('disabled');
        discount2Checkbox.prop('disabled', false);

        let userUnchecked = false;
        discount2Checkbox.off('change.discountControl').on('change.discountControl', function() {
            if (!$(this).prop('checked')) {
                if(confirm('2차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다. 해제하시겠습니까?')){
                    userUnchecked = true;
                } else {
                    $(this).prop('checked', true);
                }
            } else if (userUnchecked) {
                $(this).prop('checked', false);
                alert('2차 조기신청 할인은 한 번 해제하시면 다시 선택할 수 없습니다.');
            }
            calculateTotal();
        });
    } else { // 2차 할인 기간이 아니면
        if (discount2Checkbox.prop('checked')) {
            discount2Checkbox.prop('checked', false);
        }
        discount2Checkbox.prop('disabled', true);
        discount2Item.addClass('disabled');
        discount2Item.off('click.preventCheck').on('click.preventCheck', () => {
            alert('2차 조기신청 할인은 현재 신청 기간이 아닙니다.');
        });
    }
}

/**
 * 첫 참가 할인 선택 여부에 따라 재참가 할인 체크박스를 활성/비활성화 하는 함수
 */
function updateReParticipantDiscountState() {
    const isFirstTimerChecked = $('#discountFirstUnder10').is(':checked') || $('#discountFirstOver10').is(':checked');
    const reParticipantCheckbox = $('#discountRe');

    if (isFirstTimerChecked) {
        // 첫 참가 할인이 선택된 경우, 재참가 할인을 비활성화하고 체크 해제
        reParticipantCheckbox.prop('checked', false);
        reParticipantCheckbox.prop('disabled', true);
    } else {
        // 첫 참가 할인이 선택되지 않은 경우, 재참가 할인을 활성화
        reParticipantCheckbox.prop('disabled', false);
    }
}

/**
 * 참가 신청 관련 총액을 다시 계산하는 최종 수정 함수입니다.
 * 부스비, 할인액 등을 종합하여 최종 금액을 산출하고 화면에 표시합니다.
 */
function calculateTotal() {
    // 1. 부스별 신청 수량 가져오기
    let standAloneQty = parseInt($('#standAloneBoothCnt').val()) || 0;
    let assemblyQty = parseInt($('#assemblyBoothCnt').val()) || 0;
    let onlineQty = parseInt($('#onlineBoothCnt').val()) || 0;

    // 2. 부스 종류별 금액 계산
    let standAloneFee = standAloneQty * boothPrices.standAlone;
    let assemblyFee = assemblyQty * boothPrices.assembly;
    let onlineFee = onlineQty * boothPrices.online;

    // 부스 관련 금액 총합 (등록비 포함)
    let boothPrcSum = registrationFee + standAloneFee + assemblyFee + onlineFee;

    $('#standAloneBoothFee').val('￦ ' + standAloneFee.toLocaleString());
    $('#assemblyBoothFee').val('￦ ' + assemblyFee.toLocaleString());
    $('#onlineBoothFee').val('￦ ' + onlineFee.toLocaleString());

    // 오프라인 부스 총 수량 계산
    let physicalBooths = standAloneQty + assemblyQty;

    // 부스 수량에 따라 규모 할인을 자동으로 업데이트합니다.
    updateScaleDiscountState(physicalBooths);

    const firstUnder10 = $('#discountFirstUnder10');

    // 이 참가자가 '첫 참가'일 경우에만 아래 로직을 실행
    if (firstUnder10.data('db-val') === 'first') {
        const firstOver10 = $('#discountFirstOver10');

        if (physicalBooths > 0 && physicalBooths < 10) {
            // 1~9 부스일 경우: '10부스 미만' 할인 선택
            firstUnder10.prop('checked', true);
            firstOver10.prop('checked', false);
        } else if (physicalBooths >= 10) {
            // 10부스 이상일 경우: '10부스 이상' 할인 선택
            firstUnder10.prop('checked', false);
            firstOver10.prop('checked', true);
        } else {
            // 부스가 0개일 경우 모두 해제
            firstUnder10.prop('checked', false);
            firstOver10.prop('checked', false);
        }
    }

    // 첫 참가 할인 상태가 변경되었으므로, 재참가 할인 상태를 업데이트합니다.
    updateReParticipantDiscountState();

    // 3. 총 할인액 계산
    let totalDiscount = 0;

    // 체크된 모든 할인 항목을 순회하며 할인액을 더함
    $('input[name="discount"]').each(function() {
        if ($(this).prop('checked')) { // 체크된 상태인지 직접 확인
            let discountPerBooth = parseInt($(this).data('discount')) || 0;
            totalDiscount += (physicalBooths * discountPerBooth);
        }
    });

    // --- 발전기금 계산 로직 ---
    let developmentFund = 0;
    // 3. 협회 회원사(DB값)이거나 협회 할인 체크박스가 체크되어 있는지 확인
    const isMember = $('#memberCompanyYn').val() === 'Y'; // hidden input으로 memberCompanyYn 값 필요
    const isLeisureDiscountChecked = $('#discountLeisure').is(':checked');

    if (isMember || isLeisureDiscountChecked) {
        // 발전기금 = (등록비 + 모든 부스비)의 10%
        developmentFund = Math.floor(boothPrcSum * 0.1);
    }

    // 4. 최종 금액 계산
    // 최종 금액 = (부스비 총액 + 발전기금) - 총 할인액
    let finalAmount = (boothPrcSum + developmentFund) - totalDiscount;

    // 화면에 최종 금액 표시 (0원 미만 방지)
    $('#totalAmount').val('￦ ' + Math.max(0, finalAmount).toLocaleString());
}

/***************************************************************************************
 * 할인 조건 Function End
 * *************************************************************************************/

function autoSum(index){
    let boothCost = $('.booth_cost span');
    let sum = $('.num_sum');
    let cost = wonToNumber(boothCost[index].innerText);

    let qty = 0;
    switch (index){
        case 1: qty = $.number((parseInt($('#stand_alone_booth_cnt').val() || 0))); break; //독립부스
        case 2: qty = $.number((parseInt($('#assembly_booth_cnt').val() || 0))); break; //조립부스
        case 3: qty = $.number((parseInt($('#online_booth_cnt option:selected').val() || 0))); break; //온라인부스
        default: break;
    }

    let totalSum = (cost * qty);

    sum[index].value = (numberToWon(totalSum));
}

function autoTotalSum(){
    let sum = $('.num_sum');
    let totalSum = 0;
    for(let i=0; i<4/*sum.length*/; i++){
        totalSum += wonToNumber(sum.eq(i).val());
    }

    $('#form_add_total').val(numberToWon(totalSum));

    // CheckBox 검사
    if ($('#kt_discount_info input[type=checkbox]').is(":checked")){

        let names = [];

        $('.select input:checked').each(function() {
            names.push(this.id); //Check된 Check박스의 "Id"태그 얻기.
        });

        //console.log(names);

        for(let i=0; i<names.length; i++){
            let discountPrc = '0';
            switch (names[i]){
                case 'discount1': discountPrc = '300000'; break;
                case 'discount2': discountPrc = '200000'; break;
                case 'discount3': discountPrc = '200000'; break;
                case 'discount5': discountPrc = '300000'; break;
                case 'discount6': discountPrc = '450000'; break;
                /*case 'discount7': discountPrc = '500000'; break;*/
                case 'discount8': discountPrc = '200000'; break;
                default: break;
            }
            autoDiscountSum($('#'+names[i]),discountPrc);
        }
    }

}

function autoDiscountSum(checkbox, discountPrc){

    let discountId = $(checkbox).prop('id');

    if(discountId === 'discount1'){
        let discount2Checked = $('#discount2').is(':checked');
        if(discount2Checked){
            $('#discount2').prop('checked',false);
        }
    }

    if(discountId === 'discount2'){
        let discount1Checked = $('#discount1').is(':checked');
        if(discount1Checked){
            $('#discount1').prop('checked',false);
        }
    }

    /*if(discountId === 'discount3'){
        let discount7Checked = $('#discount7').is(':checked');
        if(discount7Checked){
            $('#discount7').prop('checked',false);
        }
    }*/

    /*if(discountId === 'discount7'){
        let discount3Checked = $('#discount3').is(':checked');
        if(discount3Checked){
            $('#discount3').prop('checked',false);
        }
    }*/

    if(discountId === 'discount5'){
        let discount6Checked = $('#discount6').is(':checked');
        if(discount6Checked){
            $('#discount6').prop('checked',false);
        }
    }

    if(discountId === 'discount6'){
        let discount5Checked = $('#discount5').is(':checked');
        if(discount5Checked){
            $('#discount5').prop('checked',false);
        }
    }

    // console.log(checkbox.id , $(checkbox).is(":checked"));
    // discount1 - 1 부스당 300,000 원 할인
    // discount2 - 1 부스당 200,000 원 할인
    // discount3 - 1 부스당 200,000 원 할인
    // discount5 - 1 부스당 300,000 원 할인
    // discount6 - 1 부스당 450,000 원 할인
    // discount7 - 1 부스당 500,000 원 할인
    // discount8 - 1 부스당 200,000 원 할인

    // 부스 수량 가져오기
    let boothSum = parseInt($('#stand_alone_booth_cnt').val() || 0)
        + parseInt($('#assembly_booth_cnt').val() || 0)
        + parseInt($('#online_booth_cnt option:selected').val());

    if(boothSum > 0) {

        let boothPrc = (parseInt($('#stand_alone_booth_cnt').val() || 0) * 1800000)
            + (parseInt($('#assembly_booth_cnt').val() || 0) * 2100000)
            + (parseInt($('#online_booth_cnt option:selected').val()) * 1000000)
            + 100000;

        let discount_checkBox = $('input[type=checkbox][name=discount]:checked');
        let discountPrc = 0;
        for(let i=0; i<discount_checkBox.length; i++){
            discountPrc += boothSum * parseInt(discount_checkBox.eq(i).val());
        }

        let totalSum = boothPrc;
        totalSum = wonToNumber(totalSum) - discountPrc;

        if(wonToNumber(totalSum) > 0){
            $('#form_add_total').val(numberToWon(totalSum));
        }else{
            $('#form_add_total').val(numberToWon("0"));
        }

    } else {
        alert("부스 신청 수량을 입력해 주세요.\nPlease enter the booth application quantity.");
        $(checkbox).removeAttr('checked');
    }
}

function wonToNumber(won){
    return Number.parseInt(won.toString().replaceAll("￦ ","").replaceAll(",",""), 10);
}

function numberToWon(number){
    return "￦ " + number.toLocaleString();
}

function wonToInt(won){
    return won.replace(/\s/g, '').replace(/\￦/g, '').replace(/\,/g, '');
}

function f_invoice_checkbox_sel(el){
    let checkboxes = document.getElementsByName(el.name);
    checkboxes.forEach((item) => {
        if (item !== el) {
            item.checked = false;
        }
    });
}

function f_invoice_booth_create(exSeq, companyNameKo){
    Swal.fire({
        icon: 'info',
        title: '[ 인보이스 ]',
        html: '<span style="font-size: 1.2em;">해당 업체의 인보이스를 생성하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '생성',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            KTApp.showPageLoading();

            let data = {
                exSeq: exSeq
            }
            $.ajax({
                url: '/mng/exhibitorNewNew/application/booth/invoice/insert.do',
                method: 'POST',
                async: false,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        let seq = data.customValue; // invoice Seq

                        companyNameKo = companyNameKo.replace(/[^a-zA-Z0-9ㄱ-힣]/g, '');

                        let param = {
                            seq: seq,
                            companyNameKo: companyNameKo
                        }
                        createAndUploadPdfFromIframe(param, 'detailForm', 'exhibitor/invoice/booth/' + exSeq)
                            .then(r =>

                                KTApp.hidePageLoading(),

                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    html: '<span style="font-size: 1.2em;">인보이스가 생성되었습니다.</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {

                                        f_application_booth_new_modify_init_set(exSeq);

                                    }
                                })

                            );
                    }else{
                        KTApp.hidePageLoading();
                        showMessage('', 'error', '에러 발생', '인보이스 생성을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                }
            })
        }
    });
}

function f_invoice_booth_delete(exSeq){
    let sendStatus = $('input[type=checkbox][name=invoiceSeq]:checked').parents('td').siblings('.td_sendStatus').find('span').text();
    if(sendStatus === '미발송'){
        let seq = $('input[type=checkbox][name=invoiceSeq]:checked').val();
        if(nvl(seq,'') !== ''){
            Swal.fire({
                icon: 'info',
                title: '[ 인보이스 ]',
                html: '<span style="font-size: 1.2em;">해당 인보이스 정보를 삭제하시겠습니까?</span>',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#d33',
                confirmButtonText: '삭제',
                cancelButtonColor: '#A1A5B7',
                cancelButtonText: '취소'
            }).then((result) => {
                if (result.isConfirmed) {

                    let data = {
                        seq: seq,
                        delYn : 'Y'
                    }
                    $.ajax({
                        url: '/mng/exhibitorNewNew/application/booth/invoice/delete.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {

                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    html: '<span style="font-size: 1.2em;">인보이스가 삭제되었습니다.</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_application_booth_new_modify_init_set(exSeq); // 재조회
                                    }
                                });

                            }else{
                                showMessage('', 'error', '에러 발생', '인보이스 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                            }
                        }
                    })
                }
            });
        }else{
            showMessage('', 'info', '[ 인보이스 ]', '인보이스 내역을 선택해 주세요.', '');
        }
    }else{
        showMessage('', 'info', '[ 인보이스 ]', '인보이스를 이미 발송하신 경우</br>내역 삭제가 불가합니다.', '');
    }
}

function f_invoice_booth_send(exSeq){
    let invoiceSelect_el = $('input[type=checkbox][name=invoiceSeq]:checked');
    let checkLength = invoiceSelect_el.length;

    if(checkLength > 0){
        let seq = invoiceSelect_el.val();
        let sendStatus = invoiceSelect_el.parents('td').siblings('.td_sendStatus').find('span').text().toString();
        let swal_html = '';
        if(sendStatus === '미발송'){
            swal_html = '<span style="font-size: 1.2em;">해당 인보이스를 발송하시겠습니까?</span>';
        }else{
            // 열람, 미열람
            swal_html = '<span style="font-size: 1.2em;">이미 발송한 인보이스입니다.</br>다시 발송하시겠습니까?</span>';
        }

        Swal.fire({
            icon: 'info',
            title: '[ 인보이스 ]',
            html: swal_html,
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '발송',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let jsonStr = { seq: exSeq };
                let result = ajaxConnect('/mng/getExhibitorNewInfo.do', 'post', jsonStr);
                if(nvl(result,'') !== ''){
                    let companyNameKo = result.companyNameKo;
                    let email = result.email;

                    let filePath = $('input[type=checkbox][name=invoiceSeq]:checked').siblings('input[type=hidden][name=filePath]').val();
                    let folderPath_r = filePath.replace('/usr/local/tomcat/webapps/','');
                    let folderPath_s = folderPath_r.substring(0, folderPath_r.lastIndexOf('/') + 1);
                    let fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length);
                    let uploadFileArr = [{
                        name: encodeURI(fileName)
                    }];
                    let jsonObj = {
                        subject: '[KIBS 2026] ' + companyNameKo + ' 부스 참가비용 인보이스 발송', //제목
                        body: "", //본문
                        template: "161", //템플릿 번호
                        receiver: [{email: email, note1: encodeURI('https://kibs.com/mng/exhibitorNewNew/application/invoice/mail/open/update.do?gbn=IB&seq=' + seq)}],
                        gbn: 'BOOTH',
                        folderPath: encodeURI(folderPath_s),
                        fileUrl: uploadFileArr
                    }
                    let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                    //console.log(i , resData);
                    let sendStatus = '';
                    let sendResult = '';
                    if (resData.resultCode === "0") {
                        sendStatus = '미열람';
                        sendResult = '발송성공';
                    }else{
                        sendStatus = '미발송';
                        sendResult = '발송실패';
                    }

                    let sendResultMsg = resData.resultMessage;

                    let sendObj = {
                        seq: seq,
                        sendStatus: sendStatus,
                        sendResult: sendResult,
                        sendResultMsg: sendResultMsg
                    }

                    $.ajax({
                        url: '/mng/exhibitorNewNew/application/booth/invoice/mail/result/update.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(sendObj),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {

                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    html: '<span style="font-size: 1.2em;">인보이스가 발송되었습니다.</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_application_booth_new_modify_init_set(exSeq); // 재조회
                                    }
                                });
                            }

                        }
                    })

                }else{
                    showMessage('', 'info', '[ 인보이스 ]', '참가업체 데이터를 찾지 못했습니다. 관리자에게 문의해주세요.', '');
                }
            }
        })
    }else{
        showMessage('', 'info', '[ 인보이스 ]', '인보이스 내역을 선택해 주세요.', '');
    }
}

function f_invoice_booth_print(){
    window.frames["detailForm"].focus();
    window.frames["detailForm"].print();
}

function f_invoice_booth_download(){
    const iframe = document.getElementById('detailForm');

    // iframe이 로드되지 않았거나 내용을 가져올 수 없는 경우
    if (!iframe || !iframe.contentWindow || !iframe.contentWindow.document) {
        alert('Iframe이 로드되지 않았거나 접근할 수 없습니다.');
        return;
    }

    const iframeDoc = iframe.contentWindow.document;
    const filePath_val = iframeDoc.getElementById('filePath');
    let filePath = filePath_val.value.toString();
    // /usr/local/tomcat/webapps/upload/exhibitor/invoice/booth/EN0000004/20250704_2026_보트쇼_인보이스_회사명국_전시부스.pdf

    let folderPath_r = filePath.replace('/usr/local/tomcat/webapps/upload/','');
    let folderPath_s = folderPath_r.substring(0, folderPath_r.lastIndexOf('/') + 1);
    let fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length);
    //f_file_download('exhibitor/company/EN0000004', '사업자등록증.jpg')

    let form = document.createElement('form');
    form.setAttribute('method', 'POST'); //POST 메서드 적용
    form.setAttribute('action', '/file/download.do');

    let hiddenField_path = document.createElement('input');
    hiddenField_path.setAttribute('type', 'hidden'); //값 입력
    hiddenField_path.setAttribute('name', 'path');
    hiddenField_path.setAttribute('value', folderPath_s);
    form.appendChild(hiddenField_path);

    let hiddenField_fileName = document.createElement('input');
    hiddenField_fileName.setAttribute('type', 'hidden'); //값 입력
    hiddenField_fileName.setAttribute('name', 'fileName');
    hiddenField_fileName.setAttribute('value', fileName);
    form.appendChild(hiddenField_fileName);

    document.body.appendChild(form);
    form.submit();
}

function f_exhibitor_application_booth_new_excel_export(){
    Swal.fire({
        icon: 'info',
        title: '[ 전체 전시부스 정보 상세 다운로드 ]',
        html: '전체 전시부스 정보 상세를 다운로드하시겠습니까 ?',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '다운로드',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 로딩페이지 */
            loadingBarShow();

            let form = document.createElement('form');
            form.setAttribute('action','/mng/exhibitor/booth/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', '전시부스_정보_' + getCurrentDate() + '.xlsx');

            let obj2 = document.createElement('input');
            obj2.setAttribute('type', 'hidden');
            obj2.setAttribute('name', 'transferYear');
            obj2.setAttribute('value', transferYear);

            form.appendChild(obj);
            form.appendChild(obj2);
            document.body.appendChild(form);
            form.submit();
        }
    });
}