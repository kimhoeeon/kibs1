/***
 * mng/exhibitor/participant/exhibitorNew.js
 * 전시회 > 참가자 관리 > 전시업체 목록
 * */

var transferYear = '2026';

$(function(){
    let myModalEl = document.getElementById('kt_modal_approval_status');

    if(myModalEl){

        let myModal = new bootstrap.Modal('#kt_modal_approval_status', {
            focus: true
        });

        myModalEl.addEventListener('hidden.bs.modal', event => {
            // input init
            $('.exhibitor_list').empty();
            $('#md_approval_stat').val('').select2({minimumResultsForSearch: Infinity});
            $('#approval_cancel_reason').val('');
            $('input[type=hidden][name=checkVal]').remove();
        })

        // input
        $('#md_approval_stat').on('change', function () {
            let selectedOption = $(this).val();
            let approvalCancelReasonInput = $('#approval_cancel_reason');

            if (selectedOption === '참가취소(기타)') {
                approvalCancelReasonInput.parent('div').parent('div').removeClass('d-none');
            } else {
                approvalCancelReasonInput.parent('div').parent('div').addClass('d-none');
            }

            approvalCancelReasonInput.val('');
        });

        $('#approval_status_btn').on('click', function () {

            let checkbox_el = $('.exhibitor_check input[type=checkbox]:checked');
            let checkbox_len = checkbox_el.length;
            let checkbox_data_val = '';
            let checkbox_val = '';
            if(checkbox_len !== 0){
                let i = 0;
                $(checkbox_el).each(function() {
                    checkbox_data_val += (i+1) + '. ';
                    checkbox_data_val += $(this).data('value');
                    checkbox_val += $(this).val();
                    if((i+1) !== checkbox_len){
                        checkbox_data_val += '<br>';
                        checkbox_val += ',';
                    }
                    i++;
                });

                if(nvl(checkbox_val,'') !== ''){
                    let input_hidden = document.createElement('input');
                    input_hidden.type = 'hidden';
                    input_hidden.name = 'checkVal'
                    input_hidden.value = checkbox_val;

                    $('#modal_form .exhibitor_list').html(checkbox_data_val);
                    $('#modal_form .exhibitor_list').append(input_hidden);

                    myModal.show();
                }
            }else{
                showMessage('', 'error', '[ 참가 상태 변경 ]', '상태를 변경할 업체를<br>하나 이상 선택해 주세요.', '');
                return false;
            }

        })
    }//myModalEl

    let myModalEl2 = document.getElementById('kt_modal_prc_yn_status');

    if(myModalEl2){

        let myModal = new bootstrap.Modal('#kt_modal_prc_yn_status', {
            focus: true
        });

        myModalEl2.addEventListener('hidden.bs.modal', event => {
            // input init
            $('.exhibitor_list2').empty();
            $('#md_prc_yn').val('').select2({minimumResultsForSearch: Infinity});
            $('input[type=hidden][name=checkVal2]').remove();
        })

        $('#prc_yn_btn').on('click', function () {

            let checkbox_el = $('.exhibitor_check input[type=checkbox]:checked');
            let checkbox_len = checkbox_el.length;
            let checkbox_data_val = '';
            let checkbox_val = '';
            if(checkbox_len !== 0){
                let i = 0;
                $(checkbox_el).each(function() {
                    checkbox_data_val += (i+1) + '. ';
                    checkbox_data_val += $(this).data('value');
                    checkbox_val += $(this).val();
                    if((i+1) !== checkbox_len){
                        checkbox_data_val += '<br>';
                        checkbox_val += ',';
                    }
                    i++;
                });

                if(nvl(checkbox_val,'') !== ''){
                    let input_hidden = document.createElement('input');
                    input_hidden.type = 'hidden';
                    input_hidden.name = 'checkVal2'
                    input_hidden.value = checkbox_val;

                    $('#modal_form2 .exhibitor_list2').html(checkbox_data_val);
                    $('#modal_form2 .exhibitor_list2').append(input_hidden);

                    myModal.show();
                }
            }else{
                showMessage('', 'error', '[ 입금 상태 변경 ]', '상태를 변경할 업체를<br>하나 이상 선택해 주세요.', '');
                return false;
            }

        })
    }//myModalEl2
    
    //이관년도
    $("select[name^=transferYear]").each(function () {
        let now = new Date();
        let now_year = transferYear;

        let selTransferYear = $(this);
        $("option", selTransferYear).remove();
        // selTransferYear.append("<option value=''>선택</option>");
        for(let i = now_year; i >= (now_year-6); i--) {
            selTransferYear.append("<option value='" + i + "'>" + i + "</option>");
        }
    });

    $('#modal_exhibitor_new_mypage_info').on('hidden.bs.modal', function () {
        // 팝업이 닫힐 때, 업체로 로그인 중인 상태였는지 확인
        if (isAdminImpersonating) {
            // 상태 변수를 즉시 초기화
            isAdminImpersonating = false;

            // 서버에 관리자 세션 복구 API 호출
            $.ajax({
                url: '/mng/exhibitor/logoutAs',
                type: 'POST',
                async: false, // 페이지가 이동하기 전에 요청이 완료되도록 동기식으로 설정
                success: function(response) {
                    if (response.resultCode === '0') {
                        // 세션 복구 성공 시, 페이지를 새로고침하여 관리자 상태를 완전히 복원
                        location.reload();
                    } else {
                        alert('관리자 세션 복구에 실패했습니다. 페이지를 새로고침합니다.');
                        location.reload();
                    }
                },
                error: function() {
                    alert('세션 복구 중 서버 오류가 발생했습니다. 페이지를 새로고침합니다.');
                    location.reload();
                }
            });
        }

        // 팝업이 닫힐 때마다 iframe 내용 초기화
        $('#exhibitorDetailForm').attr('src', 'about:blank');
    });

});

function check_count(obj){
    let chkBox = document.getElementsByName('fieldPart');
    let chkCnt = 0;
    for(let i=0; i<chkBox.length; i++){
        if(chkBox[i].checked){
            chkCnt++;
        }
    }
    if(chkCnt > 3){
        showMessage('', 'error', '[ 참가분야 ]', '최대 3개까지만 선택할 수 있습니다.', '');
        obj.checked = false;
        return false;
    }
}

function f_exhibitor_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nvl(searchText,'') === ''){
        jsonObj = {
            transferYear: transferYear
        };
    }else{
        jsonObj = {
            transferYear: transferYear,
            condition: $('#search_box option:selected').val(),
            searchText: searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitorNew/participant/company/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_new_table tbody td');
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

function f_search_condition_box_change(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

     /*
     fieldParticipatory; //행사구분
     boothType; //부스타입
     discountYn; //할인여부
     taxYn; //세금계산서 발행여부
     prcYn; //참가비 수납여부
     applyComplt; //신청서 완료여부
     approvalStatus; //승인여부
     cancelYn; //참가취소포함여부
     refundYn; //환불내역존재여부
     */

    let fieldParticipatory = $('#condition_field_participatory option:selected').val();
    let boothType = $('#condition_booth_type option:selected').val();
    let discountYn = $('#condition_discount_yn option:selected').val();
    let taxYn = $('#condition_tax_yn option:selected').val();
    let prcYn = $('#condition_prc_yn option:selected').val();
    let lang = $('#condition_lang option:selected').val();
    let applyComplt = $('#condition_apply_complt option:selected').val();
    let approvalStatus = $('#condition_approval_status option:selected').val();
    let cancelYn = $('#condition_cancel_yn option:selected').val();
    let refundYnVal = $('#condition_refund_yn').is(':checked');
    let refundYn = '';
    if(refundYnVal === true){
        refundYn = 'Y';
    }

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        fieldParticipatory: fieldParticipatory, //행사구분
        boothType: boothType, //부스타입
        discountYn: discountYn, //할인여부
        taxYn: taxYn, //세금계산서 발행여부
        prcYn: prcYn, //참가비 수납여부
        lang: lang, //언어
        applyComplt: applyComplt, //신청서 완료여부
        approvalStatus: approvalStatus, //승인여부
        cancelYn: cancelYn, //참가취소포함여부
        refundYn: refundYn, //환불내역존재여부
        transferYear: transferYear,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitorNew/participant/company/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_new_table tbody td');
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

function f_search_condition_init(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#condition_field_participatory').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_booth_type').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_discount_yn').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_tax_yn').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_prc_yn').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_apply_complt').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_approval_status').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_cancel_yn').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_refund_yn').prop('checked',false);

    /* 재조회 */
    f_exhibitor_search();
}

function f_exhibitor_detail(seq){
    $('#applyDetailForm').removeAttr('src');

    $('#applyDetailForm').attr('src','/mng/exhibitorNew/participant/company/detail.do?seq=' + seq);
}

function f_exhibitor_remove(seq){

    if(nvl(seq, "") !== ""){
        Swal.fire({
            icon: 'warning',
            title: '[ 참가업체 정보 ]',
            html: '<span style="font-size: 1.2em;">선택한 참가업체 정보를 삭제하시겠습니까?<br/>삭제한 정보는 복구할 수 없습니다.</span>',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let jsonObj = {
                    seq: seq
                }
                let resData = ajaxConnect('/mng/exhibitorNew/participant/company/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '[ 참가업체 정보 ]', '참가업체 정보가 삭제되었습니다.', '');
                    f_exhibitor_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '참가업체 정보 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_company_file_upload_call(id, path) {

    /* 사업자 등록증 */
    let companyLicenseFile = $('#companyLicense').val();
    if (nvl(companyLicenseFile, '') !== '') {

        let fileId = ($('.companyLicenseFile_li input[type=hidden][name=companyLicenseUploadFile]').attr('id'));
        if(nvl(fileId,'') !== ''){
            f_file_n_update({ id: fileId });
        }

        let fileName = companyLicenseFile;
        let fileDot = fileName.lastIndexOf('.');
        let fileType = fileName.substring(fileDot+1, fileName.length).toLocaleLowerCase();

        if(fileType === 'pdf'){
            f_company_file_upload_pdf(id, 'companyForm', 'companyLicenseFile', 'exhibitor/company/' + path);
        }else{
            f_company_uploadFile(id, 'companyForm', 'companyLicenseFile', 'exhibitor/company/' + path);
        }
    }

    /* 로고 */
    let logoFile = $('#logo').val();
    if (nvl(logoFile, '') !== '') {
        //console.log('로고 파일 업로드');
        let fileId = ($('.logoFile_li input[type=hidden][name=logoUploadFile]').attr('id'));
        if(nvl(fileId,'') !== ''){
            f_file_n_update({ id: fileId });
        }

        f_company_uploadFile(id, 'companyForm', 'logoFile', 'exhibitor/company/' + path);
    }

    /* 홍보이미지 */
    let promotionImageFileList = document.getElementsByName('promotionImageFile');
    for(let i=0; i<promotionImageFileList.length; i++){
        let promotionImageFile = promotionImageFileList[i].value;
        if (nvl(promotionImageFile, '') !== '') {
            //console.log('홍보이미지 파일 업로드' + (i+1));
            let fileId = ($('.promotionImageFile_li input[type=hidden][name=promotionImageUploadFile]').eq(i).attr('id'));
            if(nvl(fileId,'') !== ''){
                f_file_n_update({ id: fileId });
            }
            f_company_uploadFile(id, 'companyForm', 'promotionImageFile'+(i+1), 'exhibitor/company/' + path);
        }
    }

    /* 제품사진 */
    let productImageFileList = $('input[type=file][name=productImageFile]');
    for(let i=0; i<productImageFileList.length; i++){
        let productImageNum = productImageFileList[i].id;
        productImageNum = productImageNum.toString().replace('productImageFile','');
        let productImageFile = productImageFileList[i].value;
        if (nvl(productImageFile, '') !== '') {
            //console.log('제품사진 파일 업로드' + (i+1));
            f_company_uploadFile(id, 'companyForm', 'productImageFile' + productImageNum, 'exhibitor/company/' + path);
        }
    }
}

async function f_company_file_upload(userId, formId, elementId, path) {
    let uploadFileResponse = '';
    uploadFileResponse = await f_company_uploadFile(formId, elementId, path);
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

function f_company_uploadFile(userId, formId, elementId, path) {
    /* 파일 업로드 */
    return new Promise((resolve, reject) => { // Promise로 감싸기
        let file = document.querySelector('#' + elementId);
        const formData = new FormData();

        if (!file || !file.files || file.files.length === 0) {
            resolve(); // 파일이 없으면 바로 성공 처리
            return;
        }

        const reFile = file.files[0];

        new Compressor(reFile, {
            strict: true,
            quality: 0.4,
            convertSize: 4000000,
            maxWidth: 1000,
            maxHeight: 700,
            success(result) {
                formData.append('uploadFile', result, result.name);

                fetch('/file/upload.do?gbn=' + path, {
                    method: 'post',
                    body: formData
                })
                    .then(response => response.json())
                    .then(res => {
                        if (res.uploadPath) { // res.uploadPath 체크 방식 수정
                            let uploadFileResponse = res.uploadPath + '\\' + res.fileName;
                            if (nvl(uploadFileResponse, "") !== '') {
                                let fullFilePath = uploadFileResponse.replaceAll('\\', '/');
                                let fullPath = fullFilePath.substring(0, fullFilePath.lastIndexOf('/') + 1);
                                let pureFileNameSplit = fullFilePath.split('/');
                                let fullFileName = pureFileNameSplit[pureFileNameSplit.length - 1];
                                let folderPath = pureFileNameSplit[pureFileNameSplit.length - 2];
                                let note = elementId.replace('File', '');

                                let jsonObj = {
                                    userId: userId,
                                    fullFilePath: fullFilePath,
                                    fullPath: fullPath,
                                    folderPath: folderPath,
                                    fullFileName: fullFileName,
                                    fileName: fullFileName,
                                    fileYn: 'Y',
                                    note: note
                                };

                                // DB 저장 (비동기 처리를 위해 ajaxConnect 대신 fetch 또는 ajax 사용 권장하나, 기존 로직 유지)
                                // ajaxConnect는 async: false이므로 여기서 멈춥니다.
                                let resData = ajaxConnect('/file/upload/save.do', 'post', jsonObj);
                                if (resData.resultCode === "0") {
                                    resolve(res.uploadPath + '\\' + res.fileName); // 성공 시 resolve
                                } else {
                                    reject(new Error('File DB Save Error')); // 실패 시 reject
                                }
                            } else {
                                resolve(); // 경로가 비어있으면 통과
                            }
                        } else {
                            reject(new Error('Upload Path Missing'));
                        }
                    })
                    .catch(err => {
                        console.log(err);
                        reject(err); // fetch 에러 시 reject
                    });
            },
            error(err) {
                console.log(err.message);
                reject(err); // 압축 에러 시 reject
            },
        });
    });
}

async function f_company_file_upload_pdf(userId, formId, elementId, path) {
    //id, 'companyForm', 'companyLicenseFile', 'exhibitor/company/' + path
    let uploadFileResponse = '';
    uploadFileResponse = await f_company_uploadFile_pdf(formId, elementId, path);
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

function f_company_uploadFile_pdf(formId, elementId, path) {
    //id, 'companyForm', 'companyLicenseFile', 'exhibitor/company/' + path
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

function f_file_n_update(json){
    $.ajax({
        url: '/file/useYn/update.do',
        method: 'post',
        data: JSON.stringify(json),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    }).fail(function (xhr, status, errorThrown) {
        alert('파일 정보 업데이트 오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
    })
}

function f_directory_excel_export(){
    Swal.fire({
        icon: 'info',
        title: '[ 디렉토리 정보 다운로드 ]',
        html: '디렉토리 정보를 다운로드하시겠습니까 ?',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '확인',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 로딩페이지 */
            loadingBarShow();

            let form = document.createElement('form');
            form.setAttribute('action','/mng/directory/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', 'directory_' + getCurrentDate() + '.xlsx');

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

function f_exhibitor_excel_export(){
    Swal.fire({
        icon: 'info',
        title: '[ 전시업체 상세 다운로드 ]',
        html: '전시업체 상세를 다운로드하시겠습니까 ?',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '확인',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 로딩페이지 */
            loadingBarShow();

            let form = document.createElement('form');
            form.setAttribute('action','/mng/exhibitor/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', 'exhibitor_list_' + getCurrentDate() + '.xlsx');

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

function f_approval_status_btn_yn(){

    let seqArr = $('input[type=hidden][name=checkVal]').val();
    if (nvl(seqArr,'') !== ''){

        let md_approval_stat_val = $('#md_approval_stat').val();
        if(nvl(md_approval_stat_val,'') !== ''){

            let approval_cancel_reason_input = $('#approval_cancel_reason').val();

            let applyComplt = 'N';
            let cancelYn = 'N';
            switch (md_approval_stat_val){
                case '작성중':
                    break;
                case '승인요청':
                    applyComplt = 'Y';
                    break;
                case '참가승인':
                    applyComplt = 'Y';
                    break;
                default:
                    cancelYn = 'Y';
                    break;
            }

            Swal.fire({
                icon: 'info',
                title: '[ 참가 상태 변경 ]',
                html: '<span style="font-size: 1.2em;">참가 상태를 변경하시겠습니까 ?<br>[ ' + md_approval_stat_val + ' ]</span>',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#00a8ff',
                confirmButtonText: '확인',
                cancelButtonColor: '#A1A5B7',
                cancelButtonText: '취소'
            }).then((result) => {
                if (result.isConfirmed) {
                    let seqSplit = seqArr.split(',');
                    let jsonArr = [];
                    for(let i=0; i<seqSplit.length; i++){
                        let seq = seqSplit[i];

                        if(md_approval_stat_val === '참가승인'){

                            let jsonStr = {seq: seq};
                            let emailArr = ajaxConnectSimple('/getExhibitorNewEmailList.do', 'post', jsonStr);

                            for(let j=0; j<emailArr.length; j++){
                                let email = emailArr[j];
                                let jsonObj = {
                                    subject: '[ 2026 경기국제보트쇼 ] 참가기업 승인 안내', //제목
                                    body: "", //본문
                                    template: "171", //템플릿 번호
                                    receiver: [{ email: email }]
                                }
                                ajaxConnect('/mail/send.do', 'post', jsonObj);
                            }

                        }

                        let jsonObj = {
                            seq: seq,
                            applyComplt: applyComplt,
                            approvalStatus: md_approval_stat_val,
                            approvalStatusCancelReason: approval_cancel_reason_input,
                            cancelYn: cancelYn
                        }

                        jsonArr.push(jsonObj);

                    } // for

                    let resData = ajaxConnect('/mng/exhibitorNew/participant/company/updateExhibitorNewApprovalStatus.do', 'post', jsonArr);

                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '참가 상태 변경을 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                        return false;
                    }else{
                        showMessage('', 'info', '[ 참가 상태 변경 ]', '참가 상태 변경이 정상 완료되었습니다.', '');

                        $('#kt_modal_approval_status').modal('hide');

                        /* 재조회 */
                        f_exhibitor_search();
                    }
                }
            });
        }else{
            showMessage('', 'error', '[ 참가 상태 변경 ]', '변경할 상태를 선택해 주세요.', '');
            return false;
        }
    }

}

function f_prc_yn_btn(){

    let idArr = $('input[type=hidden][name=checkVal2]').val();
    if (nvl(idArr,'') !== ''){

        let md_prc_yn_val = $('#md_prc_yn').val();
        if(nvl(md_prc_yn_val,'') !== ''){

            let selTxt = $('#md_prc_yn option:selected').text();
            Swal.fire({
                icon: 'info',
                title: '[ 입금 상태 변경 ]',
                html: '<span style="font-size: 1.2em;">입금 상태를 변경하시겠습니까 ?<br>[ ' + selTxt + ' ]</span>',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#00a8ff',
                confirmButtonText: '확인',
                cancelButtonColor: '#A1A5B7',
                cancelButtonText: '취소'
            }).then((result) => {
                if (result.isConfirmed) {
                    let idSplit = idArr.split(',');
                    let jsonArr = [];
                    for(let i=0; i<idSplit.length; i++){
                        let jsonObj = {
                            seq: idSplit[i],
                            prcYn: md_prc_yn_val
                        }

                        jsonArr.push(jsonObj);

                    } // for

                    let resData = ajaxConnect('/mng/exhibitorNew/participant/company/updateExhibitorNewPrcYn.do', 'post', jsonArr);

                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '입금 상태 변경을 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                        return false;
                    }else{
                        showMessage('', 'info', '[ 입금 상태 변경 ]', '입금 상태 변경이 정상 완료되었습니다.', '');

                        $('#kt_modal_prc_yn_status').modal('hide');

                        /* 재조회 */
                        f_exhibitor_search();
                    }
                }
            });
        }else{
            showMessage('', 'error', '[ 입금 상태 변경 ]', '변경할 상태를 선택해 주세요.', '');
            return false;
        }
    }

}

function f_exhibitor_invoice_detail(seq){
    let hiddenField_seq = document.createElement('input');
    hiddenField_seq.type = 'hidden';
    hiddenField_seq.name = 'seq';
    hiddenField_seq.value = seq;

    let sendForm = document.createElement('form');
    sendForm.method = 'POST';
    sendForm.appendChild(hiddenField_seq);

    document.body.appendChild(sendForm);
    sendForm.action = '/mng/exhibitorNew/participant/company/invoice/detail.do';

    sendForm.submit();
}

// 현재 관리자가 업체로 로그인 중인지 상태를 기억하는 변수
let isAdminImpersonating = false;

function f_exhibitor_select_login(seq, companyNameKo){
    // 1. '업체로 로그인' API 호출
    $.ajax({
        url: '/mng/exhibitor/loginAs',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ seq: seq }),
        success: function(response) {
            if (response.resultCode === '0') {
                // 1. 업체로 로그인에 성공하면, 상태 변수를 true로 설정
                isAdminImpersonating = true;

                // 2. 로그인 성공 시, iframe에 마이페이지를 로드
                $('#exhibitorDetailForm').attr('src', '/mypage/step01.do');

                // 3. 팝업(Modal) 열기
                const mypageModal = new bootstrap.Modal(document.getElementById('modal_exhibitor_new_mypage_info'));
                mypageModal.show();
            } else {
                alert('업체로 로그인하는 데 실패했습니다: ' + response.resultMsg);
            }
        },
        error: function() {
            alert('서버 통신 중 오류가 발생했습니다.');
        }
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