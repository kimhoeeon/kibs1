/***
 * mng/center/kibstv.js
 * 정보센터>게시판관리
 * */

$(function(){

    // 파일 입력 변경에 대한 이벤트 핸들러 추가
    /*$('#youtubeUrl').bind('paste',function(e){
        let el = $(this);
        setTimeout(function(){
            let fullUrl = $(el).val();
            //https://www.youtube.com/watch?v=6q4DSKFn_k8&list=PLxzLH_I1hMtFo56RjSYA2-URKE4KNMbtk&index=5
            let subStr = fullUrl.substring(fullUrl.indexOf("=")+1,fullUrl.indexOf("&"));
            //6q4DSKFn_k8
            $(el).val(subStr);
        },50);
    });*/

});

function f_board_kibstv_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_center_board_kibstv_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let jsonObj = {
        category: $('#search_category option:selected').val(),
        condition: $('#search_box option:selected').val(),
        searchText: $('#search_text').val()
    }

    let resData = ajaxConnect('/mng/center/board/kibstv/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_center_board_kibstv_table tbody td');
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

function f_kibstv_detail_modal_set(rowId){
    /* 목록 상세 조회 */
    let jsonObj = {
        id: rowId
    };

    let resData = ajaxConnect('/mng/center/board/kibstv/selectSingle.do', 'post', jsonObj);

    /* 상세보기 Modal form Set */
    //console.log(resData);

    $('#md_category').val(resData.category).prop('selected',true);

    document.querySelector('#md_title').value = resData.title;
    document.querySelector('#md_writer').value = resData.writer;
    document.querySelector('#md_write_date').value = resData.writeDate;

    $('#md_lang').val(resData.lang).prop('selected',true);

    if (resData.siteGbn === "1") {
        document.querySelector('#md_site_gbn').checked = true;
    } else {
        document.querySelector('#md_site_gbn').checked = false;
    }

    document.querySelector('#md_content').innerHTML = resData.content;
    document.querySelector('#md_youtube_url').value = resData.youtubeUrl;
}

function f_board_kibstv_remove(rowId){
    //console.log('삭제버튼');
    if(nvl(rowId,'') !== ''){
        let jsonObj = {
            id: rowId
        }
        Swal.fire({
            title: '선택한 KIBS TV를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/board/kibstv/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', 'KIBS TV 삭제', 'KIBS TV가 삭제되었습니다.', '');
                    f_board_kibstv_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', 'KIBS TV 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_board_kibstv_modify_init_set(id){
    window.location.href = '/mng/center/board/kibstv/detail.do?seq=' + id;
}

function f_board_kibstv_save(id){
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
            let validCheck = f_board_kibstv_valid();

            if(validCheck){

                let serialData = JSON.parse(JSON.stringify($('#kibstvForm').serializeArray()));
                let data = objectifyForm(serialData);

                if(!data.youtubeUrl.includes('/shorts/')){
                    data.youtubeUrl = youtubeId(data.youtubeUrl);
                }

                /* Modify */
                if(nvl(id, "") !== ""){
                    $.ajax({
                        url: '/mng/center/board/kibstv/modifySave.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {
                                Swal.fire({
                                    title: 'KIBS TV 정보 변경',
                                    text: 'KIBS TV 정보가 변경되었습니다.',
                                    icon: 'info',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_board_kibstv_modify_init_set(id); // 재조회
                                    }
                                });
                            } else {
                                showMessage('', 'error', '에러 발생', 'KIBS TV 정보 변경을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                            }
                        },
                        error: function (xhr, status) {
                            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                        }
                    })//ajax
                }else { /* Insert */
                    $.ajax({
                        url: '/mng/center/board/kibstv/insertSave.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {
                                Swal.fire({
                                    title: 'KIBS TV 정보 등록',
                                    text: 'KIBS TV 정보가 등록되었습니다.',
                                    icon: 'info',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        window.location.href = '/mng/center/board/kibstv.do'; // 목록으로 이동
                                    }
                                });
                            } else {
                                showMessage('', 'error', '에러 발생', 'KIBS TV 정보 등록을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
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

function f_board_kibstv_valid(){
    let category = document.querySelector('#category').value;
    let title = document.querySelector('#title').value;
    let writer = document.querySelector('#writer').value;
    let writeDate = document.querySelector('#writeDate').value;
    let lang = document.querySelector('#lang').value;
    let content = document.querySelector('#quill_content').value;
    let youtubeUrl = document.querySelector('#youtubeUrl').value;

    if(nvl(category,"") === ""){ showMessage('#title', 'error', '[글 등록 정보]', '카테고리를 선택해 주세요.', ''); return false; }
    if(nvl(title,"") === ""){ showMessage('#title', 'error', '[글 등록 정보]', '제목을 입력해 주세요.', ''); return false; }
    if(nvl(writer,"") === ""){ showMessage('#writer', 'error', '[글 등록 정보]', '작성자를 입력해 주세요.', ''); return false; }
    if(nvl(writeDate,"") === ""){ showMessage('', 'error', '[글 등록 정보]', '작성일을 입력해 주세요.', ''); return false; }
    if(nvl(lang,"") === ""){ showMessage('', 'error', '[글 등록 정보]', '언어를 선택해 주세요.', ''); return false; }
    if(nvl(content,"") === ""){ showMessage('', 'error', '[글 등록 정보]', '내용을 입력해 주세요.', ''); return false; }
    if(nvl(youtubeUrl,"") === ""){ showMessage('', 'error', '[글 등록 정보]', 'YouTube 주소를 입력해 주세요.', ''); return false; }

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

function youtubeId(url) {
    let tag = '';
    if(url)  {
        let regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        let matchs = url.match(regExp);
        if (matchs) {
            tag = matchs[7];
        }else {
            tag = url;
        }
    }
    return tag;
}

function youtubeShortsId(url) {
    let tag = '';
    if(url)  {
        let regExp = /^.*((youtube.com\/)|(v\/)|(\/u\/\w\/)|(shorts\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        let matchs = url.match(regExp);
        if (matchs) {
            tag = matchs[7];
        }else {
            tag = url;
        }
    }
    return tag;
}