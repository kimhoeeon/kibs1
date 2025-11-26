/***
 * mng/center/dataroom.js
 * 정보센터>게시판관리
 * */

$(function(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
});

function f_board_dataroom_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_center_board_dataroom_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let jsonObj = {
        condition: $('#search_box option:selected').val(),
        searchText: $('#search_text').val()
    }

    let resData = ajaxConnect('/mng/center/board/dataroom/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_center_board_dataroom_table tbody td');
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

function f_dataroom_detail_modal_set(rowId){
    /* 목록 상세 조회 */
    let jsonObj = {
        id: rowId
    };

    let resData = ajaxConnect('/mng/center/board/dataroom/selectSingle.do', 'post', jsonObj);

    /* 상세보기 Modal form Set (jQuery로 통일) */
    $('#md_title').val(resData.title);
    $('#md_title_en').val(resData.titleEn);
    $('#md_writer').val(resData.writer);
    $('#md_write_date').val(resData.writeDate);
    $('#md_mng_year').val(resData.mngYear).prop('selected', true);

    $('#md_site_gbn').prop('checked', (resData.siteGbn === "1"));

    /* 파일 목록 상세 조회 */
    let fileJsonObj = {
        userId: rowId
    };

    let $fileListEl = $('#file_list');

    // 기존 파일 목록 초기화 (첫 번째 요소는 남기고 나머지 삭제 - 기존 로직 유지)
    // 만약 첫 번째 요소도 지워야 한다면 $fileListEl.empty(); 로 변경하세요.
    $fileListEl.children().not(':first').remove();

    let fileData = ajaxConnect('/file/upload/selectList.do', 'post', fileJsonObj);

    if(nullToEmpty(fileData) !== ''){
        // for loop 대신 forEach 사용으로 가독성 향상
        fileData.forEach(function(file) {
            // jQuery로 요소 생성 및 속성 부여
            let $input = $('<input>', {
                type: 'text',
                class: 'form-control form-control-lg form-control-solid-bg mb-2',
                value: file.fileName,
                readonly: true
            });

            $fileListEl.append($input);
        });
    }
}

function f_board_dataroom_remove(rowId){
    //console.log('삭제버튼');
    if(nvl(rowId,'') !== ""){
        let jsonObj = {
            id: rowId
        }
        Swal.fire({
            icon: 'warning',
            title: '[ 갤러리 ]',
            html: '<span style="font-size: 1.2em;">선택한 자료를 삭제하시겠습니까?</span>',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/board/dataroom/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '[ 갤러리 ]', '자료가 삭제되었습니다.', '');
                    f_board_dataroom_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '자료 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_board_dataroom_modify_init_set(id){
    window.location.href = '/mng/center/board/dataroom/detail.do?seq=' + id;
}

function f_board_dataroom_save(id){
    //console.log(id + '변경내용저장 클릭');
    Swal.fire({
        icon: 'info',
        title: '[ 갤러리 ]',
        html: '<span style="font-size: 1.2em;">입력된 정보로 저장하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then(async (result) => {
        if (result.isConfirmed) {

            /* form valid check */
            if(!f_board_dataroom_valid()) return; // 유효성 검사

            /* File upload ID 수집 (jQuery 활용으로 안정성 확보) */
            let fileIdArr = [];
            $('#uploadFileList li').each(function() {
                // children[1] 같은 인덱스 접근보다 명시적인 선택자 사용 권장
                let fileId = $(this).find('input[name="uploadFile"]').attr('id');
                if(fileId) fileIdArr.push(fileId);
            });

            let boardNoticeForm = $('#dataroomForm');
            // 기존 hidden input이 있다면 제거 후 새로 생성 (중복 방지)
            boardNoticeForm.find('input[name="fileIdList"]').remove();

            if(fileIdArr.length > 0){
                $('<input>').attr({
                    type: 'hidden',
                    name: 'fileIdList',
                    value: fileIdArr.join(',')
                }).appendTo(boardNoticeForm);
            }

            let serialData = boardNoticeForm.serializeArray(); // jQuery serializeArray 사용
            let data = objectifyForm(serialData);
            if(data.siteGbn === 'on'){
                data.siteGbn = '1';
            }else{
                data.siteGbn = '0';
            }

            /* Modify */
            let isModify = nvl(id, "") !== "";
            let url = isModify ? '/mng/center/board/dataroom/modifySave.do' : '/mng/center/board/dataroom/insertSave.do';

            $.ajax({
                url: url,
                method: 'POST',
                async: false,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {
                        Swal.fire({
                            icon: 'info',
                            title: '[ 갤러리 ]',
                            html: '<span style="font-size: 1.2em;">저장되었습니다.</span>',
                            allowOutsideClick: false,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                if (isModify) {
                                    f_board_dataroom_modify_init_set(id); // 수정: 재조회
                                } else {
                                    window.location.href = '/mng/center/board/dataroom.do'; // 등록: 목록 이동
                                }
                            }
                        });
                    } else {
                        showMessage('', 'error', '에러 발생', '갤러리 정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })//ajax

        }//result.isConfirmed
    })//swal

}//fn

function f_board_dataroom_valid(){
    let title = $('#title').val();
    let writer = $('#writer').val();
    let writeDate = $('#writeDate').val();
    let mngYear = $('#mngYear').val();

    if(nvl(title,"") === ""){ showMessage('#title', 'error', '[ 갤러리 ]', '제목을 입력해 주세요.', ''); return false; }
    if(nvl(writer,"") === ""){ showMessage('#writer', 'error', '[ 갤러리 ]', '작성자를 입력해 주세요.', ''); return false; }
    if(nvl(writeDate,"") === ""){ showMessage('', 'error', '[ 갤러리 ]', '작성일을 입력해 주세요.', ''); return false; }
    if(nvl(mngYear,"") === ""){ showMessage('', 'error', '[ 갤러리 ]', '관리년도를 선택해 주세요.', ''); return false; }

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