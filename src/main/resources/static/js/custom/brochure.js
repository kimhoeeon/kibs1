/***
 * mng/center/brochure.js
 * 정보센터>게시판관리
 * */

$(function(){

});

function f_board_brochure_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_center_board_brochure_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {};
    }else{
        jsonObj = {
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/center/board/brochure/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_center_board_brochure_table tbody td');
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

function f_brochure_detail_modal_set(rowId){
    /* 목록 상세 조회 */
    let jsonObj = {
        "id": rowId
    };

    let resData = ajaxConnect('/mng/center/board/brochure/selectSingle.do', 'post', jsonObj);

    /* 상세보기 Modal form Set */
    //console.log(resData);

    document.querySelector('#md_title').value = resData.title;
    document.querySelector('#md_writer').value = resData.writer;
    document.querySelector('#md_write_date').value = resData.writeDate;

    if(resData.gbn1==="1"){
        document.querySelector('#md_gbn1').checked = true;
    }else{
        document.querySelector('#md_gbn1').checked = false;
    }

    if(resData.gbn2==="1"){
        document.querySelector('#md_gbn2').checked = true;
    }else{
        document.querySelector('#md_gbn2').checked = false;
    }

    if(resData.gbn3==="1"){
        document.querySelector('#md_gbn3').checked = true;
    }else{
        document.querySelector('#md_gbn3').checked = false;
    }

    if(resData.noticeGbn==="1"){
        document.querySelector('#md_notice_gbn').checked = true;
    }else{
        document.querySelector('#md_notice_gbn').checked = false;
    }

    document.querySelector('#md_content').innerHTML = resData.content;
    document.querySelector('#md_view_cnt').value = resData.viewCnt;

    /* 파일 목록 상세 조회 */
    let jsonObj2 = {
        "userId": rowId
    };

    let file_list_el = document.getElementById('file_list');
    while (file_list_el.hasChildNodes()) {
        file_list_el.removeChild(file_list_el.firstChild);
    }

    let fileData = ajaxConnect('/file/upload/selectList.do', 'post', jsonObj2);
    if(nullToEmpty(fileData) !== ''){
        for(let i=0; i<fileData.length; i++){
            let file_list_el = document.getElementById('file_list');
            let input_el = document.createElement('input');
            input_el.type = 'text';
            input_el.classList.add('form-control');
            input_el.classList.add('form-control-lg');
            input_el.classList.add('form-control-solid-bg');
            input_el.classList.add('mb-2');
            input_el.value = fileData[i].fileName;
            input_el.readOnly = true;

            file_list_el.append(input_el);
        }
    }
}

function f_board_brochure_remove(rowId){
    //console.log('삭제버튼');
    if(nullToEmpty(rowId) !== ""){
        let jsonObj = {
            "id": rowId
        }
        Swal.fire({
            title: '선택한 E-브로슈어를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/board/brochure/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', 'E-브로슈어 삭제', 'E-브로슈어가 삭제되었습니다.', '');
                    f_board_brochure_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', 'E-브로슈어 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_board_brochure_modify_init_set(id){
    window.location.href = '/mng/center/board/brochure/detail.do?seq=' + id;
}

function f_board_brochure_save(id){
    //console.log(id + '변경내용저장 클릭');
    Swal.fire({
        title: '입력된 정보를 저장하시겠습니까?',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '변경내용저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then(async (result) => {
        if (result.isConfirmed) {

            /* form valid check */
            let validCheck = f_board_brochure_valid();

            if(validCheck){
                /* File upload */
                let fileIdList = '';
                let uploadFileList = document.getElementById('uploadFileList').children;
                let uploadFileListLen = uploadFileList.length;
                for(let i=0; i<uploadFileListLen; i++){
                    let fileId = uploadFileList[i].children[1].id;
                    //console.log(fileId);
                    fileIdList += fileId;
                    if((i+1) !== uploadFileListLen){
                        fileIdList += ',';
                    }
                }

                if(fileIdList !== ''){
                    let boardNoticeForm = document.getElementById('brochureForm');
                    let hidden_el = document.createElement('input');
                    hidden_el.type = 'hidden';
                    hidden_el.name = 'fileIdList';
                    hidden_el.value = fileIdList;
                    boardNoticeForm.append(hidden_el);
                }

                let serialData = JSON.parse(JSON.stringify($('#brochureForm').serializeArray()));
                let data = objectifyForm(serialData);
                //console.log(JSON.stringify(data));

                /* Modify */
                if(nvl(id, "") !== ""){
                    $.ajax({
                        url: '/mng/center/board/brochure/modifySave.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {
                                Swal.fire({
                                    title: 'E-브로슈어 정보 변경',
                                    text: 'E-브로슈어 정보가 변경되었습니다.',
                                    icon: 'info',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_board_brochure_modify_init_set(id); // 재조회
                                    }
                                });
                            } else {
                                showMessage('', 'error', '에러 발생', 'E-브로슈어 정보 변경을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                            }
                        },
                        error: function (xhr, status) {
                            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                        }
                    })//ajax
                }else { /* Insert */
                    $.ajax({
                        url: '/mng/center/board/brochure/insertSave.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {
                                Swal.fire({
                                    title: 'E-브로슈어 정보 등록',
                                    text: 'E-브로슈어 정보가 등록되었습니다.',
                                    icon: 'info',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        window.location.href = '/mng/center/board/brochure.do'; // 목록으로 이동
                                    }
                                });
                            } else {
                                showMessage('', 'error', '에러 발생', 'E-브로슈어 정보 등록을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                            }
                        },
                        error: function (xhr, status) {
                            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                        }
                    })//ajax
                }// id check

            }//validCheck

        }//result.isConfirmed
    })//swal

}//fn

function f_board_brochure_valid(){
    let title = document.querySelector('#title').value;
    let writer = document.querySelector('#writer').value;
    let writeDate = document.querySelector('#writeDate').value;
    let content = document.querySelector('#quill_content').value;

    if(nvl(title,"") === ""){ showMessage('#title', 'error', '[글 등록 정보]', '제목을 입력해 주세요.', ''); return false; }
    if(nvl(writer,"") === ""){ showMessage('#writer', 'error', '[글 등록 정보]', '작성자를 입력해 주세요.', ''); return false; }
    if(nvl(writeDate,"") === ""){ showMessage('', 'error', '[글 등록 정보]', '작성일을 입력해 주세요.', ''); return false; }
    if(nvl(content,"") === ""){ showMessage('', 'error', '[글 등록 정보]', '내용을 입력해 주세요.', ''); return false; }

    return true;
}

function objectifyForm(formArray) {
    //serialize data function
    let returnArray = {};
    for (let i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}