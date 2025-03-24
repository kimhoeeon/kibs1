/***
 * mng/exhibitor/application/utility.js
 * 참가신청서 관리 > 유틸리티 신청
 * */
var transferYear = (new Date().getFullYear()).toString();
$(function(){

    // 유틸리티 계산
    $('#kt_utility_info input.num_sum').on('keyup', function () {
        let cnt = $("#kt_utility_info input.num_sum").length;
        //console.log(cnt);

        for (let i = 1; i < cnt; i++) {
            let sum = parseInt($(this).val() || 0);
            sum++
            //console.log(sum);
        }

        //콤마찍기
        function comma(str) {
            str = String(str);
            return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
        }

        let item1 = parseInt($("#utility_jugan_cnt").val() || 0);
        let item2 = parseInt($("#utility_day_cnt").val() || 0);
        let item3 = parseInt($("#utility_compressed_air_cnt").val() || 0);
        let item4 = parseInt($("#utility_water_basic_cnt").val() || 0);
        let item6 = parseInt($("#utility_internet_cnt").val() || 0);
        let item7 = parseInt($("#utility_pytex_new_cnt").val() || 0);
        let item8 = parseInt($("#utility_pytex_re_cnt").val() || 0);
        let item9 = parseInt($("#utility_barcode_cnt").val() || 0);

        let price1 = item1 * 70000;
        let price2 = item2 * 80000;
        let price3 = item3 * 180000;
        let price4 = item4 * 180000;
        let price6 = item6 * 160000;
        let price7 = item7 * 80000;
        let price8 = item8 * 50000;
        let price9 = item9 * 200000;
        let total = price1 + price2 + price3 + price4 + price6 + price7 + price8 + price9;

        //console.log(sum);
        $("#utility_jugan_fee").val("￦ " + comma(price1));
        $("#utility_day_fee").val("￦ " + comma(price2));
        $("#utility_compressed_air_fee").val("￦ " + comma(price3));
        $("#utility_water_basic_fee").val("￦ " + comma(price4));
        $("#utility_internet_fee").val("￦ " + comma(price6));
        $("#utility_pytex_new_fee").val("￦ " + comma(price7));
        $("#utility_pytex_re_fee").val("￦ " + comma(price8));
        $("#utility_barcode_fee").val("￦ " + comma(price9));
        $("#form_add_total").val("￦ " + comma(total));

        $(this).val($(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    });

});

function f_application_utility_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#utilityGbn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_utility_search();
}

function f_application_utility_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_utility_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {
            "transferYear": transferYear
        };
    }else{
        jsonObj = {
            "transferYear": transferYear,
            "condition": $('#search_box option:selected').val(),
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/application/utility/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_utility_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_utility_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let lang = $('#lang option:selected').val(); //참가신청언어
    let approvalYn = $('#approvalYn option:selected').val(); //승인여부
    let cancelYn = $('#cancelYn option:selected').val(); //참가취소여부
    let utilityGbn = $('#utilityGbn option:selected').val(); //유틸리티 항목

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        "lang": lang,
        "approvalStatus": approvalYn,
        "cancelYn": cancelYn,
        "utilityGbn": utilityGbn,
        "transferYear": transferYear,
        "condition": condition,
        "searchText": searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/application/utility/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_utility_table tbody td');
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

function f_application_utility_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/utility/detail.do?seq=' + id;
}

function f_application_utility_save(seq){

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
            let data = f_utility_form_data_setting(seq);

            $.ajax({
                url: '/mng/exhibitor/application/utility/update.do',
                method: 'POST',
                async: false,
                data: data,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        Swal.fire({
                            title: '유틸리티 신청 정보 저장',
                            text: '유틸리티 신청 정보가 저장되었습니다.',
                            icon: 'info',
                            allowOutsideClick: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_application_utility_modify_init_set(seq); // 재조회
                            }
                        });

                    } else {
                        showMessage('', 'error', '에러 발생', '유틸리티 신청 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })

        }//isConfirmed
    })//then

}

function f_utility_form_data_setting(exhibitorSeq){

    // 유틸리티 신청정보 - 주간단상 - 수량
    let utility_jugan_cnt = parseInt(document.querySelector('#utility_jugan_cnt').value);

    // 유틸리티 신청정보 - 주간단상 - 금액
    let utility_jugan_fee = document.querySelector('#utility_jugan_fee').value;

    // 유틸리티 신청정보 - 24시간용 - 수량
    let utility_day_cnt = parseInt(document.querySelector('#utility_day_cnt').value);

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_day_fee = document.querySelector('#utility_day_fee').value;

    // 유틸리티 신청정보 - 압축공기 - 수량
    let utility_compressed_air_cnt = parseInt(document.querySelector('#utility_compressed_air_cnt').value);

    // 유틸리티 신청정보 - 압축공기 - 금액
    let utility_compressed_air_fee = document.querySelector('#utility_compressed_air_fee').value;

    // 유틸리티 신청정보 - 급배수 - 수량
    let utility_water_basic_cnt = parseInt(document.querySelector('#utility_water_basic_cnt').value);

    // 유틸리티 신청정보 - 급배수 - 금액
    let utility_water_basic_fee = document.querySelector('#utility_water_basic_fee').value;

    // 유틸리티 신청정보 - 인터넷 - 수량
    let utility_internet_cnt = parseInt(document.querySelector('#utility_internet_cnt').value);

    // 유틸리티 신청정보 - 인터넷 - 금액
    let utility_internet_fee = document.querySelector('#utility_internet_fee').value;

    // 유틸리티 신청정보 - 파이텍스 (신품) - 수량
    let utility_pytex_new_cnt = parseInt(document.querySelector('#utility_pytex_new_cnt').value);

    // 유틸리티 신청정보 - 파이텍스 (신품) - 금액
    let utility_pytex_new_fee = document.querySelector('#utility_pytex_new_fee').value;

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 수량
    let utility_pytex_re_cnt = parseInt(document.querySelector('#utility_pytex_re_cnt').value);

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 금액
    let utility_pytex_re_fee = document.querySelector('#utility_pytex_re_fee').value;

    // 유틸리티 신청정보 - 바코드 리더기 - 수량
    let utility_barcode_cnt = parseInt(document.querySelector('#utility_barcode_cnt').value);

    // 유틸리티 신청정보 - 바코드 리더기 - 금액
    let utility_barcode_fee = document.querySelector('#utility_barcode_fee').value;

    let utilityPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let jsonObj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        utilityJuganCnt: utility_jugan_cnt,
        utilityJuganFee: wonToInt(utility_jugan_fee),
        utilityDayCnt: utility_day_cnt,
        utilityDayFee: wonToInt(utility_day_fee),
        utilityCompressedAirCnt: utility_compressed_air_cnt,
        utilityCompressedAirFee: wonToInt(utility_compressed_air_fee),
        utilityWaterBasicCnt: utility_water_basic_cnt,
        utilityWaterBasicFee: wonToInt(utility_water_basic_fee),
        utilityInternetCnt: utility_internet_cnt,
        utilityInternetFee: wonToInt(utility_internet_fee),
        utilityPytexNewCnt: utility_pytex_new_cnt,
        utilityPytexNewFee: wonToInt(utility_pytex_new_fee),
        utilityPytexReCnt: utility_pytex_re_cnt,
        utilityPytexReFee: wonToInt(utility_pytex_re_fee),
        utilityBarcodeCnt: utility_barcode_cnt,
        utilityBarcodeFee: wonToInt(utility_barcode_fee),
        utilityPrcSum: utilityPrcSum
    }

    return JSON.stringify(jsonObj);
}

function wonToInt(won){
    return won.replace(/\s/g, '').replace(/\￦/g, '').replace(/\,/g, '');
}