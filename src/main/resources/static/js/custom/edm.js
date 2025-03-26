/***
 * mng/center/edm/edm.js
 * 정보센터>edm관리>목록
 * */

$(function(){

});

function f_edm_hst_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_center_edm_list_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {};
    }else{
        jsonObj = {
            "condition": $('#search_box option:selected').val(),
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/center/edm/list/selectHstList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_center_edm_list_table tbody td');
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

function f_edm_hst_remove(rowId){
    //console.log('삭제버튼');
    if(nullToEmpty(rowId) !== ""){
        let jsonObj = {
            "id": rowId
        }
        Swal.fire({
            title: '선택한 메일 발송 이력을 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/edm/list/deleteHst.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '메일 발송 이력 삭제', '메일 발송 이력이 삭제되었습니다.', '');
                    f_edm_hst_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '메일 발송 이력 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_edm_detail_init_set(){
    window.location.href = '/mng/center/edm/list/detail.do';
}

function f_edm_detail_mail_send(){
    let tableData = $('#kt_center_edm_upload_table').DataTable().data();
    let len = tableData.length;
    //$('#kt_participant_company_table').DataTable().data().rows(18).data();

    if(len !== 0) {

        //제목
        let title = document.getElementById('title').value;
        if( nvl(title,"") === '' ){
            showMessage('#title', 'error', '[메일 발송 정보]', '메일 제목을 입력해 주세요.');
            return;
        }

        //내용
        let content = document.querySelector('#quill_content').value;
        if( nvl(content,"") === '' ){
            showMessage('', 'error', '[메일 발송 정보]', '메일 내용을 입력해 주세요.');
            return;
        }

        Swal.fire({
            title: '메일을 전송하시겠습니까?',
            icon: 'info',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '전송',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then(async (result) => {
            if (result.isConfirmed) {
                let sendCnt = len;
                let successCnt = 0;
                let failCnt = 0;

                for (let i = 0; i < len; i++) {
                    let rowData = tableData.rows(i).data();

                    let jsonObj = makeJsonFormat("mail", rowData);
                    //console.log(JSON.stringify(jsonObj));
                    let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                    //console.log(i , resData);
                    if (resData.resultCode === "0") {
                        successCnt++;
                    } else {
                        failCnt++;
                    }
                }

                let hstSaveJsonObj = {
                    "title": title,
                    "sendCnt": sendCnt,
                    "sendResult": "성공:" + successCnt + " / " + "실패:" + failCnt,
                    "sendContent": content
                };

                $.ajax({
                    url: '/mng/center/edm/list/insertHst.do',
                    method: 'POST',
                    async: false,
                    data: JSON.stringify(hstSaveJsonObj),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {
                            Swal.fire({
                                title: '메일 발송',
                                html: "메일 발송이 정상적으로 완료되어<br>발송 결과가 등록되었습니다.<br>메일 발송 & 이력 목록으로 이동합니다.",
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    window.location.href = '/mng/center/edm/list.do'; // 목록으로 이동
                                }
                            });
                        } else {
                            showMessage('', 'error', '에러 발생', '메일 발송 결과 등록을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })//ajax
            }
        });

    } else {
        Swal.fire({
            icon: 'error',
            allowOutsideClick: false,
            title: '[메일 발송 정보]',
            html: '엑셀 업로드를 통해 <br>수신 메일 주소를 등록해 주세요.'
        });
    }

}

function excelUpload(){
    /* 테이블 데이터 지우기 */
    let dataTbl = $('#kt_center_edm_upload_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let input = document.querySelector('#excel_file');
    let reader = new FileReader();
    reader.onload = function() {
        let fdata = reader.result;
        let read_buffer = XLSX.read(fdata, {type : 'binary'});
        read_buffer.SheetNames.forEach(function(sheetName) {
            let rowdata =XLSX.utils.sheet_to_json(read_buffer.Sheets[sheetName],{defval:''}); // Excel 입력 데이터
            //console.log('excel all : ' + JSON.stringify(rowdata));
            //console.log(rowdata[100]==null); //true

            if(rowdata.length > 1){
                for(let i=1; i<rowdata.length; i++){

                    let dataArr = [];

                    JSON.parse(JSON.stringify(rowdata[i]), (key, value) => {
                        dataArr.push(value);
                    });

                    let dataRow = makeJsonFormat('import', dataArr);
                    dataTbl.row.add(dataRow).draw();
                }
            }else{
                alert('첨부된 엑셀파일에 데이터가 없습니다.');
            }
        });

        /* 조회 카운트 입력 */
        document.getElementById('search_cnt').innerText = dataTbl.data().length;

        /* modal close fn */
        f_modal_close('kt_modal_excel_upload');

        let jb = $('#kt_center_edm_upload_table tbody td');
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
    reader.readAsBinaryString(input.files[0]);
}

function makeJsonFormat(gbn, data){
    let parseData;
    let returnJsonObj;
    if(gbn==="mail"){
        let dataArr = [];
        let receiverArr = [];
        let receiverObj = {};
        JSON.parse(JSON.stringify(JSON.parse(JSON.stringify(data))[0]), (key, value) => {
            dataArr.push(value);
        });

        parseData = JSON.parse(JSON.stringify(dataArr));
        receiverObj = {
            "email": replaceText(parseData[2]), //받는이 메일주소
            "name": replaceText(parseData[3]), //이름
            "phone": replaceText(parseData[4]) //연락처
        }
        receiverArr.push(receiverObj);

        let uploadFileNameList = $('input[type=hidden][name=uploadFile]');
        // ./src/main/resources/img/mail/ceae0528-622d-4afd-9935-c3d39d253fd0_23_main_bg.png
        let uploadFileArr = [];
        for(let i=0; i<uploadFileNameList.length; i++){
            let uploadFileNameStr = uploadFileNameList.eq(i).val();
            let uploadFileVal = uploadFileNameStr.substring(uploadFileNameStr.lastIndexOf('/')+1);
            let uploadFileObj = {
                "name": uploadFileVal
            }
            uploadFileArr.push(uploadFileObj);
        }

        returnJsonObj = {
            "id": replaceText(parseData[1]), //ID
            "subject": $('#title').val(), //제목
            "body": $('#quill_content').val(), //본문
            "receiver": receiverArr,
            "fileUrl": uploadFileArr
        }
        console.log(returnJsonObj);
    }else{
        parseData = JSON.parse(JSON.stringify(data));

        returnJsonObj = {
            "rownum": replaceText(parseData[0]), //연번
            "id": replaceText(parseData[1]), //ID
            "email": replaceText(parseData[2]), //메일주소
            "name": replaceText(parseData[3]), //이름
            "phone": replaceText(parseData[4]) //연락처
        }
    }

    return returnJsonObj;
}

function f_edm_send_content_set(content){
    document.querySelector('#md_content').innerHTML = content;
}

async function f_mail_file_upload(userId, formId, path) {
    let uploadFileListLen = document.getElementById('uploadFileList').children.length;
    if(uploadFileListLen < 6) { //첨부파일 최대 5개
        let uploadFileResponse = '';
        uploadFileResponse = await f_mail_uploadFile(formId, path);
        if (nvl(uploadFileResponse, "") !== '') {
            Swal.fire({
                title: '파일 업로드',
                text: "파일 업로드 성공",
                icon: 'info',
                allowOutsideClick: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: '확인'
            }).then((result) => {
                if (result.isConfirmed) {
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
                        let ul_el = document.getElementById('uploadFileList');
                        let img_el = document.createElement('img');
                        let li_el = document.createElement('li');
                        let a_el = document.createElement('a');

                        const previewFile = document.querySelector('#attachFileInput');
                        img_el.src = URL.createObjectURL(previewFile.files[0]);
                        img_el.classList.add('w-150px');
                        img_el.classList.add('mr10');
                        li_el.append(img_el);

                        /*a_el.href = 'javascript:f_file_download(' + '\'' + pureFileName + '\'' + ',' + '\'' + pureFilePath + '\'' +')';*/
                        /*a_el.href = '/file/download.do?path=' + path + '&fileName=' + fullFileName;*/
                        a_el.href = 'javascript:void(0);';
                        a_el.onclick = f_file_download(path, fullFileName);
                        a_el.text = fullFileName;
                        li_el.append(a_el);

                        let hidden_el = document.createElement('input');
                        hidden_el.type = 'hidden';
                        hidden_el.name = 'uploadFile';
                        hidden_el.id = resData.fileId;
                        hidden_el.value = fullFilePath;
                        li_el.append(hidden_el);

                        let button_el = document.createElement('button');
                        button_el.type = 'button';
                        button_el.className = 'ml10';
                        button_el.onclick = function () {
                            f_file_remove(this, resData.fileId)
                        }
                        button_el.innerHTML = '<i class="ki-duotone ki-abstract-11">\n' +
                            '<i class="path1"></i>\n' +
                            '<i class="path2"></i>\n' +
                            '</i>';
                        li_el.append(button_el);

                        ul_el.append(li_el);

                        /* modal 창 닫기 */
                        f_upload_modal_close('kt_modal_file_upload', 'attachFile');
                    }
                }
            });
        }
    }else{
        Swal.fire({
            icon: 'error',
            allowOutsideClick: false,
            title: '[메일 발송 정보]',
            html: '첨부파일은 최대 5개까지만 가능합니다.'
        });
    }
}

function f_mail_uploadFile(formId, path) {
    /* 파일 업로드 */
    let fileForm = document.getElementById(formId);
    let formData = new FormData(fileForm);

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

function f_modal_close(target_modal_id){
    /* modal close */
    // open : className="app-default modal-open" style="overflow: hidden; padding-right: 17px;">
    // close : className="app-default" style="">
    let body_el = document.querySelector('body');
    body_el.classList.remove('modal-open');
    body_el.style.removeProperty('overflow');
    body_el.style.removeProperty('padding-right');

    // open : <div className="modal fade show" id="kt_modal_excel_upload" tabIndex="-1" style="display: block;" aria-modal="true" role="dialog">
    // close : <div className="modal fade" id="kt_modal_excel_upload" tabIndex="-1" style="display: none;" aria-hidden="true">
    let modal_el = document.getElementById(target_modal_id);
    modal_el.classList.remove('show');
    modal_el.style.display = 'none';
    modal_el.removeAttribute('aria-modal');
    modal_el.removeAttribute('role');
    modal_el.setAttribute('aria-hidden','true');

    let modal_backdrop_el = document.querySelector('.modal-backdrop');
    modal_backdrop_el.remove();

    /* modal close */
    modalClose('excel');

}