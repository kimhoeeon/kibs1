/***
 * mng/center/press.js
 * 정보센터>게시판관리
 * */

$(function(){

});

function f_board_press_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_center_board_press_table').DataTable();
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

    let resData = ajaxConnect('/mng/center/board/press/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_center_board_press_table tbody td');
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

function f_press_detail_modal_set(rowId){
    /* 목록 상세 조회 */
    let jsonObj = {
        "id": rowId
    };

    let resData = ajaxConnect('/mng/center/board/press/selectSingle.do', 'post', jsonObj);

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

function f_board_press_remove(rowId){
    //console.log('삭제버튼');
    if(nullToEmpty(rowId) !== ""){
        let jsonObj = {
            "id": rowId
        }
        Swal.fire({
            title: '[ 보도자료 ]',
            html: '<span style="font-size: 1.2em;">' + '선택한 보도자료를 삭제하시겠습니까?' + '</span>',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/board/press/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '보도자료 삭제', '보도자료가 삭제되었습니다.', '');
                    f_board_press_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '보도자료 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_board_press_modify_init_set(id){
    window.location.href = '/mng/center/board/press/detail.do?seq=' + id;
}

function f_board_press_save(id){
    //console.log(id + '변경내용저장 클릭');

    if(!f_board_press_valid()) return;

    Swal.fire({
        title: '[ 보도자료 ]',
        html: '<span style="font-size: 1.2em;">' + '입력된 정보를 저장하시겠습니까?' + '</span>',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then(async (result) => {
        if (result.isConfirmed) {

            /* File upload */
            let fileIdList = '';
            let uploadFileList = document.getElementById('uploadFileList').children;
            let uploadFileListLen = uploadFileList.length;
            for(let i=0; i<uploadFileListLen; i++){
                // 순서 상관없이 input[name=uploadFile] 요소를 직접 찾습니다.
                let fileInput = uploadFileList[i].querySelector('input[name="uploadFile"]');

                if (fileInput) {
                    let fileId = fileInput.id;
                    fileIdList += fileId;
                    // 마지막 요소가 아니면 콤마 추가
                    if((i+1) !== uploadFileListLen){
                        fileIdList += ',';
                    }
                }
            }

            if(fileIdList !== ''){
                let boardNoticeForm = document.getElementById('pressForm');
                let hidden_el = document.createElement('input');
                hidden_el.type = 'hidden';
                hidden_el.name = 'fileIdList';
                hidden_el.value = fileIdList;
                boardNoticeForm.append(hidden_el);
            }

            let form = JSON.parse(JSON.stringify($('#pressForm').serializeObject()));
            form.content = $('#summernote').summernote('code');

            let url = '/mng/center/board/press/insertSave.do';
            if(nvl(id, "") !== "") {
                url = '/mng/center/board/press/modifySave.do';
            }

            $.ajax({
                url: url,
                method: 'POST',
                async: false,
                data: JSON.stringify(form),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {
                        Swal.fire({
                            title: '[ 보도자료 ]',
                            html: '<span style="font-size: 1.2em;">' + '입력된 정보가 저장되었습니다.' + '</span>',
                            icon: 'info',
                            allowOutsideClick: false,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                if(nvl(id, "") !== ""){
                                    f_board_press_modify_init_set(id); // 재조회
                                }else{
                                    window.location.href = '/mng/center/board/press.do'; // 목록으로 이동
                                }
                            }
                        });
                    } else {
                        showMessage('', 'error', '에러 발생', '보도자료 정보 변경을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })//ajax

        }//result.isConfirmed
    })//swal

}//fn

function f_board_press_valid(){
    let title = document.querySelector('#title').value;
    let writer = document.querySelector('#writer').value;
    let writeDate = document.querySelector('#writeDate').value;

    // [수정 포인트 2] 유효성 검사 시에도 Summernote 내용 가져오기
    // Summernote가 비어있는지 확인하는 전용 함수 이용 ('isEmpty')
    let isSummernoteEmpty = $('#summernote').summernote('isEmpty');
    // 내용을 문자열로 가져오려면 아래 코드 사용
    let content = $('#summernote').summernote('code');

    if(nvl(title,"") === ""){ showMessage('#title', 'error', '[ 글 등록 정보 ]', '제목을 입력해 주세요.', ''); return false; }
    if(nvl(writer,"") === ""){ showMessage('#writer', 'error', '[ 글 등록 정보 ]', '작성자를 입력해 주세요.', ''); return false; }
    if(nvl(writeDate,"") === ""){ showMessage('', 'error', '[ 글 등록 정보 ]', '작성일을 입력해 주세요.', ''); return false; }

    // [수정 포인트 3] 내용 빈값 체크 (isEmpty 사용 권장)
    // Summernote는 내용이 없어도 <p><br></p> 태그가 남을 수 있어 단순 null 체크보다 isEmpty가 정확함
    if(isSummernoteEmpty){
        showMessage('', 'error', '[ 글 등록 정보 ]', '내용을 입력해 주세요.', '');
        return false;
    }

    return true;
}