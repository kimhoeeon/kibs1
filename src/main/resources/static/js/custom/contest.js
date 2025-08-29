/***
 * mng/center/contest.js
 * 정보센터>게시판관리
 * */

$(function(){
});

function f_board_contest_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_center_board_contest_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let jsonObj = {
        condition: $('#search_box option:selected').val(),
        searchText: $('#search_text').val()
    }

    let resData = ajaxConnect('/mng/center/board/contest/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_center_board_contest_table tbody td');
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

function f_contest_detail_modal_set(rowId){
    /* 목록 상세 조회 */
    let jsonObj = {
        id: rowId
    };

    let resData = ajaxConnect('/mng/center/board/contest/selectSingle.do', 'post', jsonObj);

    /* 상세보기 Modal form Set */
    //console.log(resData);

    $('#md_gbn').val(resData.gbn);
    $('#md_prize_gbn').val(resData.prizeGbn);
    $('#md_title').val(resData.title);
    $('#md_writer').val(resData.writer);
    $('#md_write_date').val(resData.writeDate);

    /* 파일 목록 상세 조회 */
    let jsonObj2 = {
        userId: rowId
    };

    let file_list_el = document.getElementById('file_list');
    while (file_list_el.hasChildNodes()) {
        file_list_el.removeChild(file_list_el.firstChild);
    }

    let fileData = ajaxConnect('/file/upload/selectList.do', 'post', jsonObj2);
    if(nvl(fileData,'') !== ''){
        let file_list_el = document.getElementById('file_list');
        let label_el = document.createElement('label');
        label_el.classList.add('form-label');
        label_el.innerText = '첨부파일';
        file_list_el.append(label_el);
        for(let i=0; i<fileData.length; i++){
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

function f_board_contest_remove(rowId){
    //console.log('삭제버튼');
    if(nvl(rowId,'') !== ''){
        let jsonObj = {
            id: rowId
        }
        Swal.fire({
            icon: 'warning',
            title: '[ 공모전 ]',
            html: '<span style="font-size: 1.2em;">' + '선택한 자료를 삭제하시겠습니까?' + '</span>',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/board/contest/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '[ 공모전 ]', '자료가 삭제되었습니다.', '');
                    f_board_contest_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '자료 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_board_contest_modify_init_set(id){
    window.location.href = '/mng/center/board/contest/detail.do?seq=' + id;
}

function f_board_contest_save(id){
    //console.log(id + '변경내용저장 클릭');
    Swal.fire({
        icon: 'info',
        title: '[ 공모전 ]',
        html: '<span style="font-size: 1.2em;">' + '입력된 정보를 저장하시겠습니까?' + '</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then(async (result) => {
        if (result.isConfirmed) {

            /* form valid check */
            let validCheck = f_board_contest_valid();

            if(validCheck){

                /* File upload */
                let fileIdList = '';
                let uploadFileList = $('#uploadFileList li');
                let uploadFileListLen = uploadFileList.length;
                for(let i=0; i<uploadFileListLen; i++){
                    let fileId = uploadFileList.eq(i).find('input[type=hidden]').attr('id');
                    //console.log(fileId);
                    fileIdList += fileId;
                    if((i+1) !== uploadFileListLen){
                        fileIdList += ',';
                    }
                }

                if(fileIdList !== ''){
                    let boardNoticeForm = document.getElementById('contestForm');
                    let hidden_el = document.createElement('input');
                    hidden_el.type = 'hidden';
                    hidden_el.name = 'fileIdList';
                    hidden_el.value = fileIdList;
                    boardNoticeForm.append(hidden_el);
                }

                let serialData = JSON.parse(JSON.stringify($('#contestForm').serializeArray()));
                let data = objectifyForm(serialData);
                data.gbn = nvl(data.gbn, 1);
                //console.log(JSON.stringify(data));

                /* Modify */
                if(nvl(id, "") !== ""){
                    $.ajax({
                        url: '/mng/center/board/contest/modifySave.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {
                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 공모전 ]',
                                    html: '<span style="font-size: 1.2em;">' + '정보가 저장되었습니다.' + '</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_board_contest_modify_init_set(id); // 재조회
                                    }
                                });
                            } else {
                                showMessage('', 'error', '에러 발생', '정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                            }
                        },
                        error: function (xhr, status) {
                            alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                        }
                    })//ajax
                }else { /* Insert */
                    $.ajax({
                        url: '/mng/center/board/contest/insertSave.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {
                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 공모전 ]',
                                    html: '<span style="font-size: 1.2em;">' + '정보가 저장되었습니다.' + '</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        window.location.href = '/mng/center/board/contest.do'; // 목록으로 이동
                                    }
                                });
                            } else {
                                showMessage('', 'error', '에러 발생', '정보 등록을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                            }
                        },
                        error: function (xhr, status) {
                            alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                        }
                    })//ajax
                }// id check

            }//validCheck

        }//result.isConfirmed
    })//swal

}//fn

function f_board_contest_valid(){
    let prizeGbn = $('#prizeGbn').val();
    let title = $('#title').val();
    let writer = $('#writer').val();
    let writeDate = $('#writeDate').val();

    if(nvl(prizeGbn,"") === ""){ showMessage('', 'error', '[ 공모전 ]', '입상 구분을 선택해 주세요.', ''); return false; }
    if(nvl(title,"") === ""){ showMessage('#title', 'error', '[ 공모전 ]', '제목을 입력해 주세요.', ''); return false; }
    if(nvl(writer,"") === ""){ showMessage('#writer', 'error', '[ 공모전 ]', '작성자를 입력해 주세요.', ''); return false; }
    if(nvl(writeDate,"") === ""){ showMessage('', 'error', '[ 공모전 ]', '작성일을 입력해 주세요.', ''); return false; }

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