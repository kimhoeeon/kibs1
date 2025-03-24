/***
 * mng/event/eventProduct.js
 * 부대행사>올해의제품>참가자목록
 * */
var transferYear = (new Date().getFullYear()).toString();

$(function(){

});

function f_event_product_search_condition_init(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#awardYear').val('').select2({minimumResultsForSearch: Infinity});
    $('#gbn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_event_product_search();
}

function f_event_product_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_event_product_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nvl(searchText,'') === ''){
        jsonObj = {};
    }else{
        jsonObj = {
            condition: $('#search_box option:selected').val(),
            searchText: searchText
        }
    }

    let resData = ajaxConnect('/mng/event/product/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_event_product_table tbody td');
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
    let dataTbl = $('#kt_event_product_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let awardYear = $('#awardYear option:selected').val();
    let gbn = $('#gbn option:selected').val();

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        awardYear: awardYear, //수상연도
        gbn: gbn, //분류
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/event/product/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_event_product_table tbody td');
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

function f_event_product_remove(seq){
    //console.log('삭제버튼');
    if(nullToEmpty(seq) !== ""){
        let jsonObj = {
            seq: seq
        }
        Swal.fire({
            title: '선택한 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/event/product/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '참가자 삭제', '참가자가 삭제되었습니다.', '');
                    f_event_product_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '참가자 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_event_product_modify_init_set(seq){
    window.location.href = '/mng/event/product/detail.do?seq=' + seq;
}

function f_event_product_save(seq){

    let gbn = 'U';
    if(nvl(seq,'') === ''){ //insert
        gbn = 'C';
    }

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
            let validCheck = f_product_from_valid_check(gbn);

            if(validCheck){

                /* form data setting */
                let data = f_visitor_form_data_setting();

                $.ajax({
                    url: '/mng/event/product/update.do',
                    method: 'post',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            /* file function */
                            let tableSeq = data.customValue; //tableSeq return 값
                            f_award_file_upload_call(tableSeq, tableSeq);

                            Swal.fire({
                                title: '참가자 정보 저장',
                                text: '참가자 정보가 저장되었습니다.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_event_product_modify_init_set(tableSeq); // 재조회
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '참가자 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
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

function f_product_from_valid_check(cu){

    let awardYear = $('#awardYear').val();
    if (nvl(awardYear,'') === '') {
        showMessage('', 'info', '입력 정보 확인', '수상연도를 선택해 주세요.', '');
        return false;
    }

    let gbn = $('input[type=checkbox][name=gbn]').is(':checked');
    if (!gbn) {
        showMessage('', 'info', '입력 정보 확인', '분류를 하나 이상 체크해주세요.', '');
        return false;
    }

    let companyNameKo = $('#companyNameKo').val();
    if (nvl(companyNameKo,'') === '') {
        showMessage('', 'info', '입력 정보 확인', '업체명(국문)을 입력해 주세요.', '');
        return false;
    }

    let companyNameEn = $('#companyNameEn').val();
    if (nvl(companyNameEn,'') === '') {
        showMessage('', 'info', '입력 정보 확인', '업체명(영문)을 입력해 주세요.', '');
        return false;
    }

    let productNameKo = $('#productNameKo').val();
    if (nvl(productNameKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '제품명(국문)을 입력해 주세요.', '');
        return false;
    }

    let productNameEn = $('#productNameEn').val();
    if (nvl(productNameEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '제품명(영문)을 입력해 주세요.', '');
        return false;
    }

    if(cu === 'C') { // insert
        let productPhoto = $('#productPhoto').val();
        if (nvl(productPhoto,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품사진을 첨부해 주세요.', '');
            return false;
        }
    }else{
        let productPhotoFile_li = $('.productPhotoFile_li').length;
        if(productPhotoFile_li === 0){
            let productPhotoFile = $('#productPhotoFile').val();
            if (nvl(productPhotoFile,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '제품사진을 첨부해 주세요.', '');
                return false;
            }
        }
    }

    let productIntroKo = $('#productIntroKo').val();
    if (nvl(productIntroKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '상세설명(국문)을 입력해 주세요.', '');
        return false;
    }

    let productIntroEn = $('#productIntroEn').val();
    if (nvl(productIntroEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '상세설명(영문)을 입력해 주세요.', '');
        return false;
    }

    let homepage = $('#homepage').val();
    if (nvl(homepage,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '홈페이지 주소를 입력해 주세요.', '');
        return false;
    }

    return true;
}

function f_visitor_form_data_setting(){

    let productForm = JSON.parse(JSON.stringify($('#productForm').serializeObject()));

    //분류
    let gbnList = $('input[type=checkbox][name=gbn]:checked');
    let gbnListLen = gbnList.length;
    let gbn = '';
    for(let i=0; i<gbnListLen; i++){
        gbn += gbnList.eq(i).val();
        if((i+1) !== gbnListLen){
            gbn += ',';
        }
    }
    productForm.gbn = gbn;

    return JSON.stringify(productForm);
}

function f_award_file_upload_call(id, path) {
    /* 제품사진 */
    let productPhotoFile = document.getElementById('productPhotoFile').value;
    if (nvl(productPhotoFile, '') !== '') {
        f_award_file_upload(id, 'productForm', 'productPhotoFile', 'event/product/' + path);
    }
}

async function f_award_file_upload(userId, formId, elementId, path) {
    let uploadFileResponse = '';
    uploadFileResponse = await f_award_uploadFile(formId, elementId, path);
    if (nvl(uploadFileResponse, "") !== '') {
        let fullFilePath = uploadFileResponse.replaceAll('\\', '/');
        // ./tomcat/webapps/upload/center/board/notice/b3eb661d-34de-4fd0-bc74-17db9fffc1bd_KIBS_TV_목록_excel_20230817151752.xlsx

        let fullPath = fullFilePath.substring(0, fullFilePath.lastIndexOf('/') + 1);
        // ./tomcat/webapps/upload/center/board/notice/

        let pureFileNameSplit = fullFilePath.split('/');
        let fullFileName = pureFileNameSplit[pureFileNameSplit.length - 1];
        // b3eb661d-34de-4fd0-bc74-17db9fffc1bd_KIBS_TV_목록_excel_20230817151752.xlsx

        /*let uuid = fullFileName.substring(0, fullFileName.indexOf('_'));
        // b3eb661d-34de-4fd0-bc74-17db9fffc1bd

        let fileName = fullFileName.substring(fullFileName.indexOf('_') + 1);
        // KIBS_TV_목록_excel_20230817151752.xlsx*/

        let folderPath = pureFileNameSplit[pureFileNameSplit.length - 2];
        // notice

        let note = elementId.replace('File', '');

        let jsonObj = {
            userId: userId,
            fullFilePath: fullFilePath,
            fullPath: fullPath,
            folderPath: folderPath,
            fullFileName: fullFileName,
            /*uuid: uuid,*/
            fileName: fullFileName,
            fileYn: 'Y',
            note: note
        };
        let resData = ajaxConnect('/file/upload/save.do', 'post', jsonObj);
        if (resData.resultCode === "0") {
            /*let parents_el = document.querySelector('#' + note);
            let fileId_el = document.createElement('input');
            fileId_el.type = 'hidden';
            fileId_el.id = note+'1';
            fileId_el.name = note+'1';
            fileId_el.value = resData.fileId;

            parents_el.appendChild(fileId_el);*/
        }
    }
}

function f_award_uploadFile(formId, elementId, path) {
    /* 파일 업로드 */
    let fileForm = document.getElementById(formId);
    let formData = new FormData(fileForm);
    if(nvl(elementId,'') !== ''){
        let file = document.querySelector('#' + elementId);
        formData.append('request',file.files[0]);
    }

    return new Promise((resolve, reject) => {
        fetch('/file/upload.do?gbn=' + path, {
            method: 'post',
            body: formData
        })
            .then(function (response) {
                return response.json();
            })
            .then(res => {
                if( typeof res.uploadPath !== undefined){
                    resolve(res.uploadPath + '\\' + res.fileName);
                }
            })

    });
}

/*
@author https://github.com/macek/jquery-serialize-object
*/
$.fn.serializeObject = function () {
    "use strict";
    var result = {};
    var extend = function (i, element) {
        var node = result[element.name];
        if ("undefined" !== typeof node && node !== null) {
            if ($.isArray(node)) {
                node.push(element.value);
            } else {
                result[element.name] = [node, element.value];
            }
        } else {
            result[element.name] = element.value;
        }
    };

    $.each(this.serializeArray(), extend);
    return result;
};