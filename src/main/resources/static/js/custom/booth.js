/***
 * mng/exhibitor/application/booth.js
 * 참가신청서 관리 > 전시부스 신청
 * */
var transferYear = (new Date().getFullYear()).toString();

$(function(){

    let datepicker_from = document.querySelector('#datepicker_from');

    if (datepicker_from) {
        $("#datepicker_from").daterangepicker({
                singleDatePicker: true,
                showDropdowns: true,
                minYear: 1901,
                maxYear: parseInt(moment().format("YYYY"),12),
                startDate: '01/01/' + transferYear
            }, function(start, end, label) {
                /*var years = moment().diff(start, "years");
                alert("You are " + years + " years old!");*/
            }
        );

        $('#datepicker_from').on('apply.daterangepicker', function(ev, picker) {
            f_search_condition_sel_change();
        });
    }

    let datepicker_to = document.querySelector('#datepicker_to');

    if (datepicker_to) {
        $("#datepicker_to").daterangepicker({
                singleDatePicker: true,
                showDropdowns: true,
                minYear: 1901,
                maxYear: parseInt(moment().format("YYYY"),12)
            }, function(start, end, label) {
                /*var years = moment().diff(start, "years");
                alert("You are " + years + " years old!");*/
            }
        );

        $('#datepicker_to').on('apply.daterangepicker', function(ev, picker) {
            f_search_condition_sel_change();
        });
    }

});

function f_application_booth_search_condition_init(){
    let date = new Date(); // Data 객체 생성
    let year = date.getFullYear().toString(); // 년도 구하기
    let month = date.getMonth() + 1; // 월 구하기
    month = month < 10 ? '0' + month.toString() : month.toString(); // 10월 미만 0 추가
    let day = date.getDate(); // 날짜 구하기
    day = day < 10 ? '0' + day.toString() : day.toString(); // 10일 미만 0 추가

    let todayFormat = month + '/' + day + '/' + year;

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#datepicker_from').val(todayFormat);
    $('#datepicker_to').val(todayFormat);
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#boothGbn').val('').select2({minimumResultsForSearch: Infinity});
    $('#discountYn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_booth_search();
}

function f_application_booth_search(){

    let search_box = $('#search_box option:selected').val();
    let search_text = $('#search_text').val();

    if(search_box === 'boothCnt'){
        let check = /^[0-9]+$/;
        if (!check.test(search_text)) {
            Swal.fire({
                title: '검색 조건 확인',
                html: '검색 조건이 신청갯수일 경우<br>숫자로만 검색 가능합니다.',
                icon: 'info',
                allowOutsideClick: false,
                confirmButtonColor: '#3085d6',
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
    let dataTbl = $('#kt_exhibitor_application_booth_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    if(nullToEmpty(search_text) === ""){
        jsonObj = {
            "transferYear": transferYear
        };
    }else{
        jsonObj = {
            "transferYear": transferYear,
            "condition": search_box,
            "searchText": search_text
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/application/booth/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_booth_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_booth_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let date_from_val = $('#datepicker_from').val().split('/');
    let date_from = date_from_val[2] + '-' + date_from_val[0] + '-' + date_from_val[1];
    let date_to_val = $('#datepicker_to').val().split('/');
    let date_to = date_to_val[2] + '-' + date_to_val[0] + '-' + date_to_val[1];

    let lang = $('#lang option:selected').val(); //참가신청언어
    let approvalYn = $('#approvalYn option:selected').val(); //승인여부
    let cancelYn = $('#cancelYn option:selected').val(); //참가취소여부
    let boothGbn = $('#boothGbn option:selected').val(); //부스구분
    let discountYn = $('#discountYn option:selected').val(); //부스구분

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        "dateFrom": date_from,
        "dateTo": date_to,
        "lang": lang,
        "approvalStatus": approvalYn,
        "cancelYn": cancelYn,
        "boothType": boothGbn,
        "discountYn": discountYn,
        "transferYear": transferYear,
        "condition": condition,
        "searchText": searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/application/booth/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_booth_table tbody td');
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

function f_application_booth_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/booth/detail.do?seq=' + id;
}

function f_application_booth_save(seq){

    Swal.fire({
        title: '입력된 정보를 저장하시겠습니까?',
        icon: 'info',
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
                url: '/mng/exhibitor/application/booth/update.do',
                method: 'POST',
                async: false,
                data: data,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        Swal.fire({
                            title: '전시 부스 정보 저장',
                            text: '전시 부스 정보가 저장되었습니다.',
                            icon: 'info',
                            allowOutsideClick: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_application_booth_modify_init_set(seq); // 재조회
                            }
                        });

                    } else {
                        showMessage('', 'error', '에러 발생', '전시 부스 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })

        }//isConfirmed
    })//then

}

function f_booth_form_data_setting(seq){

    let boothType = '등록비';
    let boothPrcSum = 0;

    // 부스 신청 - 등록비 - 수량
    let registration_cnt = parseInt(document.querySelector('#registration_cnt').value);

    // 부스 신청 - 등록비 - 금액
    let registration_fee = document.querySelector('#registration_fee').value;

    boothPrcSum += wonToNumber(registration_fee);

    // 부스 신청 - 독립부스 - 수량
    let stand_alone_booth_cnt = parseInt(document.querySelector('#stand_alone_booth_cnt').value);

    if(stand_alone_booth_cnt > 0){
        boothType += ',독립부스';
    }

    // 부스 신청 - 독립부스 - 금액
    let stand_alone_booth_fee = document.querySelector('#stand_alone_booth_fee').value;

    boothPrcSum += wonToNumber(stand_alone_booth_fee);

    // 부스 신청 - 조립부스 - 수량
    let assembly_booth_cnt = parseInt(document.querySelector('#assembly_booth_cnt').value);

    if(assembly_booth_cnt > 0){
        boothType += ',조립부스';
    }

    // 부스 신청 - 조립부스 - 금액
    let assembly_booth_fee = document.querySelector('#assembly_booth_fee').value;

    boothPrcSum += wonToNumber(assembly_booth_fee);

    // 부스 신청 - 온라인부스 - 수량
    let online_booth_cnt = parseInt(document.querySelector('select#online_booth_cnt option:checked').text);

    if(online_booth_cnt > 0){
        boothType += ',온라인부스';
    }

    // 부스 신청 - 온라인부스 - 금액
    let online_booth_fee = document.querySelector('#online_booth_fee').value;

    boothPrcSum += wonToNumber(online_booth_fee);

    let discountType = '';
    let discountPrcSum = 0;
    let boothTotalCnt = stand_alone_booth_cnt + assembly_booth_cnt + online_booth_cnt;

    // 할인적용 - 1차 조기신청
    let discount_early_1 = document.querySelector('input[type=checkbox][id=discount1]').checked;
    if(discount_early_1){
        discountType += ',1차조기신청';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 2차 조기신청
    let discount_early_2 = document.querySelector('input[type=checkbox][id=discount2]').checked;
    if(discount_early_2){
        discountType += ',2차조기신청';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 재참가할인 All
    let discount_re_all = document.querySelector('input[type=checkbox][id=discount3]').checked;
    if(discount_re_all){
        discountType += ',재참가할인';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 규모할인 1 (20부스 이상)
    let discount_scale_2 = document.querySelector('input[type=checkbox][id=discount5]').checked;
    if(discount_scale_2){
        discountType += ',규모할인1';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 규모할인 2 (40부스 이상)
    let discount_scale_3 = document.querySelector('input[type=checkbox][id=discount6]').checked;
    if(discount_scale_3){
        discountType += ',규모할인2';
        discountPrcSum += (boothTotalCnt * 450000);
    }

    // 할인적용 - 첫 참가 할인
    let discount_first = document.querySelector('input[type=checkbox][id=discount7]').checked;
    if(discount_first){
        discountType += ',첫참가';
        discountPrcSum += (boothTotalCnt * 500000);
    }

    // 할인적용 - 한국해양레저산업협회 할인
    let discount_leisure = document.querySelector('input[type=checkbox][id=discount8]').checked;
    if(discount_leisure){
        discountType += ',한국해양레저산업협회';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    if(discountType.substring(0,1) === ','){
        discountType = discountType.substring(1);
    }

    let discountYn = 'N';
    if(nvl(discountType,'') !== ''){
        discountYn = 'Y';
    }

    //let boothPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let booth_json_obj = {
        seq: seq,
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registration_cnt,
        registrationFee: Number.parseInt(wonToInt(registration_fee)),
        standAloneBoothCnt: stand_alone_booth_cnt,
        standAloneBoothFee: Number.parseInt(wonToInt(stand_alone_booth_fee)),
        assemblyBoothCnt: assembly_booth_cnt,
        assemblyBoothFee: Number.parseInt(wonToInt(assembly_booth_fee)),
        onlineBoothCnt: online_booth_cnt,
        onlineBoothFee: Number.parseInt(wonToInt(online_booth_fee)),
        discountEarly1: discount_early_1,
        discountEarly2: discount_early_2,
        discountReAll: discount_re_all,
        discountScale2: discount_scale_2,
        discountScale3: discount_scale_3,
        discountFirst: discount_first,
        discountLeisure: discount_leisure,
        discountYn: discountYn,
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum
    }

    return JSON.stringify(booth_json_obj);
}

function minCnt(el, cnt){
    let val = $.number($(el).val() || 0);
    if(val < cnt){
        if(val !== $.number(0)){
            alert('독립부스는 2부스부터 신청 가능합니다.');
            $(el).val(0);
            checkBooth();
            autoSum(1);
            autoTotalSum();
            return false;
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
                case 'discount7': discountPrc = '500000'; break;
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

    if(discountId === 'discount3'){
        let discount7Checked = $('#discount7').is(':checked');
        if(discount7Checked){
            $('#discount7').prop('checked',false);
        }
    }

    if(discountId === 'discount7'){
        let discount3Checked = $('#discount3').is(':checked');
        if(discount3Checked){
            $('#discount3').prop('checked',false);
        }
    }

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