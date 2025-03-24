/***
 * mng/exhibitor/application/sign.js
 * 참가신청서 관리 > 상호간판 신청
 * */
var transferYear = (new Date().getFullYear()).toString();
$(function(){

});

function f_application_sign_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_sign_search();
}

function f_application_sign_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_sign_table').DataTable();
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

    let resData = ajaxConnect('/mng/exhibitor/application/sign/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_sign_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_sign_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let lang = $('#lang option:selected').val(); //참가신청언어
    let approvalYn = $('#approvalYn option:selected').val(); //승인여부
    let cancelYn = $('#cancelYn option:selected').val(); //참가취소여부

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        "lang": lang,
        "approvalStatus": approvalYn,
        "cancelYn": cancelYn,
        "transferYear": transferYear,
        "condition": condition,
        "searchText": searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/application/sign/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_sign_table tbody td');
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

function f_application_sign_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/sign/detail.do?seq=' + id;
}

function f_application_sign_save(seq){

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

            let validCheck = f_sign_form_valid_check();

            if(validCheck){
                /* form data setting */
                let data = f_sign_form_data_setting(seq);

                $.ajax({
                    url: '/mng/exhibitor/application/sign/update.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            Swal.fire({
                                title: '상호 간판 신청 정보 저장',
                                text: '상호 간판 신청 정보가 저장되었습니다.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_application_sign_modify_init_set(seq); // 재조회
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '상호 간판 신청 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })
            }

        }//isConfirmed
    })//then

}

function f_sign_form_valid_check(){

    let companySignNameKo = $('#companySignNameKo').val();
    if (nvl(companySignNameKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '상호간판명(국문)을 입력해 주세요.', '');
        return false;
    }

    let companySignNameEn = $('#companySignNameEn').val();
    if (nvl(companySignNameEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '상호간판명(영문)을 입력해 주세요.', '');
        return false;
    }

    return true;
}

function f_sign_form_data_setting(seq){

    // 상호간판명(국문)
    let companySignNameKo = document.querySelector('#companySignNameKo').value;

    // 상호간판명(영문)
    let companySignNameEn = document.querySelector('#companySignNameEn').value;

    let jsonObj = {
        seq: seq,
        companySignNameKo: companySignNameKo,
        companySignNameEn: companySignNameEn
    }

    return JSON.stringify(jsonObj);
}