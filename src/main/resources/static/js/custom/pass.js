/***
 * mng/exhibitor/application/pass.js
 * 참가신청서 관리 > 출입증 신청
 * */
var transferYear = (new Date().getFullYear()).toString();
$(function(){

    // 출입증신청에서 추가 버튼 클릭 시
    $('.form_add_btn').on('click', function () {
        let passListCount = $('.formTbBd:last .addNum').text();
        if(passListCount < 50){
            // 마지막 .formTbBd 요소를 복사하여 추가
            let lastRow = $('.formTbBd:last');
            let newRow = lastRow.clone(true);

            // 추가된 .formTbBd의 .addNum 값 증가
            let passNum = parseInt(newRow.find('.addNum').text());
            newRow.find('.addNum').text(passNum + 1);

            // 추가된 .formTbBd의 입력 필드 초기화
            newRow.find('input').val('');

            // 추가된 .formTbBd를 .formPassBd에 추가
            lastRow.after(newRow);

            updatePassNum(); // .addNum 값 업데이트
        }else{
            alert('출입증 신청은 최대 50명까지만 등록 가능합니다.');
        }
    });

    $('.addDel').on('click', function () {
        Swal.fire({
            title: '해당 출입증 신청 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input[type=hidden]').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mng/exhibitor/application/pass/delete.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '출입증 신청 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.formTbBd').remove();
                updatePassNum(); // .addNum 값 업데이트
            }//isConfirmed
        }); //swal
    });

    // .addNum 값 업데이트 함수
    function updatePassNum() {
        $('.formTbBd').each(function (index) {
            $(this).find('.addNum').text(index + 1);
        });
    }

});

function f_application_pass_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_pass_search();
}

function f_application_pass_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_pass_table').DataTable();
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

    let resData = ajaxConnect('/mng/exhibitor/application/pass/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_pass_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_pass_table').DataTable();
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
    let resData = ajaxConnect('/mng/exhibitor/application/pass/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_pass_table tbody td');
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

function f_application_pass_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/pass/detail.do?seq=' + id;
}

function f_application_pass_save(seq){

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

            /* 유효성 검사 */
            let validCheck = f_pass_form_valid_check();

            if(validCheck){

                /* form data setting */
                let data = f_pass_form_data_setting(seq);

                $.ajax({
                    url: '/mng/exhibitor/application/pass/update.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            Swal.fire({
                                title: '출입증 신청 정보',
                                text: '출입증 신청 정보가 저장되었습니다.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_application_pass_modify_init_set(seq); // 재조회
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '출입증 신청 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })

            }//validCheck
        }//isConfirmed
    })//then

}

function f_pass_form_valid_check(){

    // pass table seq
    let passSeqList = $('input[type=hidden][name=passSeq]');

    // 출입증성명(국문)
    let passNameList = $('input[type=text][name=passName]');
    for(let i=0; i<passNameList.length; i++){
        let passName = passNameList.eq(i).val();
        if (nvl(passName,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '성명(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    // 이름(영문)
    let passFirstNameList = $('input[type=text][name=passFirstName]');
    for(let i=0; i<passFirstNameList.length; i++){
        let passFirstName = passFirstNameList.eq(i).val();
        if (nvl(passFirstName,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '성명(영문) - 이름을 입력해 주세요.', '');
            return false;
        }
    }

    // 성(영문)
    let passLastNameList = $('input[type=text][name=passLastName]');
    for(let i=0; i<passLastNameList.length; i++){
        let passLastName = passLastNameList.eq(i).val();
        if (nvl(passLastName,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '성명(영문) - 성을 입력해 주세요.', '');
            return false;
        }
    }

    // 직책(국문)
    let passPositionKoList = $('input[type=text][name=passPositionKo]');
    for(let i=0; i<passPositionKoList.length; i++){
        let passPositionKo = passPositionKoList.eq(i).val();
        if (nvl(passPositionKo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '직책(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    // 직책(영문)
    let passPositionEnList = $('input[type=text][name=passPositionEn]');
    for(let i=0; i<passPositionEnList.length; i++){
        let passPositionEn = passPositionEnList.eq(i).val();
        if (nvl(passPositionEn,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '직책(영문)을 입력해 주세요.', '');
            return false;
        }
    }

    // 비고
    let passNoteList = $('input[type=text][name=passNote]');

    return true;
}

function f_pass_form_data_setting(exhibitorSeq){

    // pass table seq
    let passSeqList = $('input[type=hidden][name=passSeq]');

    // 출입증성명(국문)
    let passNameList = $('input[type=text][name=passName]');

    // 이름(영문)
    let passFirstNameList = $('input[type=text][name=passFirstName]');

    // 성(영문)
    let passLastNameList = $('input[type=text][name=passLastName]');

    // 직책(국문)
    let passPositionKoList = $('input[type=text][name=passPositionKo]');

    // 직책(영문)
    let passPositionEnList = $('input[type=text][name=passPositionEn]');

    // 비고
    let passNoteList = $('input[type=text][name=passNote]');

    let passCnt = $('.formTbBd:last .addNum').text();

    let passListArr = [];
    for(let i=0; i<passCnt; i++){
        let jsonObj = {
            seq: passSeqList.eq(i).val(),
            id: exhibitorSeq,
            passName: passNameList.eq(i).val(),
            passFirstName: passFirstNameList.eq(i).val(),
            passLastName: passLastNameList.eq(i).val(),
            passPositionKo: passPositionKoList.eq(i).val(),
            passPositionEn: passPositionEnList.eq(i).val(),
            passNote: passNoteList.eq(i).val()
        }

        passListArr.push(jsonObj);
    }

    let exhibitorArr = {
        seq: exhibitorSeq,
        passList: passListArr
    };

    return JSON.stringify(exhibitorArr);
}

function f_exhibitor_pass_excel_export(){
    Swal.fire({
        title: '전체 출입증 정보 상세 다운로드',
        html: '전체 출입증 정보 상세를 다운로드하시겠습니까 ?',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        confirmButtonText: '다운로드',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 로딩페이지 */
            loadingBarShow();

            let form = document.createElement('form');
            form.setAttribute('action','/mng/exhibitor/pass/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', 'exhibitor_pass_list_' + getCurrentDate() + '.xlsx');

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