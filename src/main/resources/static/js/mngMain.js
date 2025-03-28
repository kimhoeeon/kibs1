/***
 * mngMain.js
 * */

$(function(){

    if (!window.location.href.includes('localhost')) {
        if (window.location.protocol !== "https:") {
            window.location.href = "https:" + window.location.href.substring(window.location.protocol.length);
        }

        if (document.location.protocol === "http:") {
            document.location.href = document.location.href.replace('http:', 'https:');
        }
    }

    // 숫자만 입력
    $('.onlyNum').on("blur keyup", function () {
        $(this).val($(this).val().replaceAll(/[^0-9]/g, '').replace(/(\..*)\./g, '$1'));
    });

    // 연락처 입력 시 자동으로 - 삽입과 숫자만 입력
    $('.onlyTel').on("blur keyup", function () {
        $(this).val($(this).val().replaceAll(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));
    });

    // 영문, 숫자만 입력
    $('.onlyNumEng').on("blur keyup", function () {
        let exp = /[^A-Za-z0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;
        $(this).val($(this).val().replaceAll(exp, ''));
    });

    // 숫자, 소수점 입력
    $('.onlyNumDec').on("blur keyup", function () {
        var sanitizedValue = $(this).val().replace(/[^0-9.]/g, ''); // 숫자와 소수점 이외의 문자 제거
        var decimalParts = sanitizedValue.split("."); // 소수점을 기준으로 분할

        // 소수점이 하나 이상일 경우 마지막 소수점만 남기고 제거
        if (decimalParts.length > 2) {
            decimalParts.pop(); // 마지막 요소(소수점 이후 내용) 제거
            sanitizedValue = decimalParts.join(".");
        }

        $(this).val(sanitizedValue); // 처리된 값을 다시 입력 필드에 설정
    });

    // 파일 입력 변경에 대한 이벤트 핸들러 추가
    $(document).on('change', '.upload_hidden', function () {

        let fileName = $(this).val();
        if(nvl(fileName,'') !== ''){
            let fileNameVal = $(this).val().split('\\').pop();
            let fileNameInput = $(this).parent('div').siblings('div').find('.upload_name');

            let _lastDot = fileNameVal.lastIndexOf('.');
            let realFileName = fileNameVal.substring(0, _lastDot).toLowerCase();

            // 파일명에 특수문자 체크
            let pattern = /[\{\}\/?,.;:|\[\]*~`!^\+<>@\#$%&\\\=\'\"]/gi;
            if(pattern.test(String(realFileName)) ){
                $(this).val(''); //업로드한 파일 제거
                fileNameInput.val('File');
                alert('파일명에 허용되지 않는 공백 및 특수문자가 포함되어 있습니다.\n허용된 특수문자는 - _ ( ) 입니다.');
            }else{
                if (this.files && this.files[0]) {
                    let maxSize = 10 * 1024 * 1024; //* 10MB 사이즈 제한
                    let file = this.files[0];
                    if (file.size > maxSize) {
                        alert("파일 첨부는 10MB 이내 파일만 가능합니다.");
                        $(this).val(''); //업로드한 파일 제거
                        let fileNameInput = $(this).parent('div').siblings('div').find('.upload_name');
                        fileNameInput.val('File');
                    } else {
                        let fileName = $(this).val().split('\\').pop();
                        let fileNameInput = $(this).parent('div').siblings('div').find('.upload_name');
                        fileNameInput.val(fileName);
                    }
                }
            }

        }else{
            $(this).val(''); //업로드한 파일 제거
            let fileNameInput = $(this).parent('div').siblings('div').find('.upload_name');
            fileNameInput.val('File');
        }

    });

    let customDatepicker = document.getElementById("kt_td_picker_custom_icons");
    if(customDatepicker){
        new tempusDominus.TempusDominus(customDatepicker, {
            display: {
                icons: {
                    time: "ki-outline ki-time fs-1",
                    date: "ki-outline ki-calendar fs-1",
                    up: "ki-outline ki-up fs-1",
                    down: "ki-outline ki-down fs-1",
                    previous: "ki-outline ki-left fs-1",
                    next: "ki-outline ki-right fs-1",
                    today: "ki-outline ki-check fs-1",
                    clear: "ki-outline ki-trash fs-1",
                    close: "ki-outline ki-cross fs-1",
                },
                buttons: {
                    today: true,
                    clear: true,
                    close: true,
                },
            }
        });
    }

    /* 작성일 */
    let writeDatePicker = document.getElementById('writeDate');
    if(writeDatePicker) {
        writeDatePicker.flatpickr({
            enableTime: true,
            dateFormat: "Y-m-d H:i:S"
        });
    }

    const exportButtons = document.querySelectorAll('button[data-kt-export]');
    exportButtons.forEach(exportButton => {
        exportButton.addEventListener('click', e => {
            e.preventDefault();

            // Get clicked export value
            const exportValue = e.target.getAttribute('data-kt-export');
            const target = document.querySelector('.dt-buttons .buttons-' + exportValue);

            // Trigger click event on hidden datatable export buttons
            target.click();
        });
    });

    //기참가연도
    $('input[type=checkbox][name=prePartYear]').on('change', function(){
        let prePartYear = $(this).val();
        if(prePartYear === 'first'){
            $('input[type=checkbox][name=prePartYear]:not(:first)').prop('checked',false);
        }else{
            $('input[type=checkbox][name=prePartYear]:first').prop('checked',false);
        }
    })

    //지난 전시회 참관 여부
    $('input[type=checkbox][name=preObservationGbn]').on('change', function(){
        let preObservationGbn = $(this).val();
        if(preObservationGbn === 'first'){
            $('input[type=checkbox][name=preObservationGbn]:not(:first)').prop('checked',false);
        }else{
            $('input[type=checkbox][name=preObservationGbn]:first').prop('checked',false);
        }
    })

});

function loginFormSubmit() {
    let form = document.getElementById("login_form");
    let id = document.getElementById("adminId");
    let password = document.getElementById("adminPw");

    if (id.value.trim() === "" || password.value.trim() === "") {
        showMessage('', 'info', '입력 정보 확인', '아이디와 비밀번호를 입력해 주세요.', '');
        return false;
    }

    let jsonObj = {
        id: id.value,
        password: password.value
    };

    $.ajax({
        url: '/mng/login.do',
        method: 'post',
        data: JSON.stringify(jsonObj),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
        .done(function (data) {
            if (data !== "") {
                form.submit(); // /mng/main.do
            } else {
                showMessage('', 'info', '로그인 실패', '관리자 아이디와 비밀번호를 확인해주세요.', '');
            }
        })
        .fail(function (xhr, status, errorThrown) {
            /*$('body').html("오류가 발생했습니다.")
                .append("<br>오류명: " + errorThrown)
                .append("<br>상태: " + status);*/
            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
        })

}

function logout() {
    if(confirm('로그아웃 하시겠습니까?')){
        $.ajax({
            url: '/mng/logoutCheck.do',
            method: 'post',
            contentType: 'application/json; charset=utf-8' //server charset 확인 필요
        })
        .done(function (data) {
            if (data !== "") {
                window.location.href = '/mng/index.do';
            } else {
                showMessage('', 'info', '로그아웃 실패', '관리자에게 문의해주세요.', '');
            }
        })
        .fail(function (xhr, status, errorThrown) {
            /*$('body').html("오류가 발생했습니다.")
                .append("<br>오류명: " + errorThrown)
                .append("<br>상태: " + status);*/
            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
        })
    }

}

function f_file_download(path, fileName){
    let form = document.createElement('form');
    form.setAttribute('method', 'POST'); //POST 메서드 적용
    form.setAttribute('action', '/file/download.do');

    let hiddenField_path = document.createElement('input');
    hiddenField_path.setAttribute('type', 'hidden'); //값 입력
    hiddenField_path.setAttribute('name', 'path');
    hiddenField_path.setAttribute('value', path);
    form.appendChild(hiddenField_path);

    let hiddenField_fileName = document.createElement('input');
    hiddenField_fileName.setAttribute('type', 'hidden'); //값 입력
    hiddenField_fileName.setAttribute('name', 'fileName');
    hiddenField_fileName.setAttribute('value', fileName);
    form.appendChild(hiddenField_fileName);

    document.body.appendChild(form);
    form.submit();
}

function replaceText(value){
    if(nullToEmpty(value) !== ''){
        value = value.toString().replaceAll('\r','');
        value = value.toString().replaceAll('\n','');
        value = value.trim();
    }
    return value;
}

// 엑셀업로드
function showMessage(selector, icon, title, msg, confirmButtonColor) {
    if (typeof icon == "undefined" || title == null) icon = 'info';
    if (typeof title == "undefined" || title == null) title = '';
    if (typeof confirmButtonColor == "undefined" || confirmButtonColor == null || confirmButtonColor === '') confirmButtonColor = '#00a8ff';

    if( selector !== '' ) {
        $(':focus').trigger('blur');
    }

    Swal.fire({
        icon: icon,
        title: title,
        html: '<span style="font-size: 1.1em;">' + msg + '</span>',
        allowOutsideClick: false,
        confirmButtonColor: confirmButtonColor
    })
    .then(() => {
        if( selector && selector !== '' ){
            setTimeout(function() {
                $(selector).trigger('focus');
            }, 200);
        }
    });
}

function ajaxConnect(url, method, jsonStr) {
    let result;
    $.ajax({
        url: url,
        method: method,
        async: false,
        data: JSON.stringify(jsonStr),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
        .done(function (data) {
            result = data;
        })
        .fail(function (xhr, status, errorThrown) {
            /*$('body').html("오류가 발생했습니다.")
                .append("<br>오류명: " + errorThrown)
                .append("<br>상태: " + status);*/

            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
        })
    return result;
}

function getCurrentDate() {
    let date = new Date(); // Data 객체 생성
    let year = date.getFullYear().toString(); // 년도 구하기

    let month = date.getMonth() + 1; // 월 구하기
    month = month < 10 ? '0' + month.toString() : month.toString(); // 10월 미만 0 추가

    let day = date.getDate(); // 날짜 구하기
    day = day < 10 ? '0' + day.toString() : day.toString(); // 10일 미만 0 추가

    let hour = date.getHours(); // 시간 구하기
    hour = hour < 10 ? '0' + hour.toString() : hour.toString(); // 10시 미만 0 추가

    let minites = date.getMinutes(); // 분 구하기
    minites = minites < 10 ? '0' + minites.toString() : minites.toString(); // 10분 미만 0 추가

    let seconds = date.getSeconds(); // 초 구하기
    seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString(); // 10초 미만 0 추가

    return year + month + day + hour + minites + seconds; // yyyymmddhhmmss 형식으로 리턴
}

function nullToEmpty(nullStr){
    let convertStr;
    if(nullStr === null || nullStr === 'null' || nullStr === undefined || nullStr === 'undefined' || Object.keys(nullStr).length === 0){
        convertStr = '';
    }else{
        convertStr = nullStr;
    }
    return convertStr;
}

/**
 * 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다.
 * @param str			: 체크할 문자열
 * @param defaultStr	: string 비어있을경우 리턴할 기본 문자열
 */
function nvl(str, defaultStr){

    if(str === "" || str === null || str === "null" || str === undefined || typeof str === "undefined" || (typeof str === "object" && !Object.keys(str).length) ){
        str = defaultStr ;
    }

    return str ;
}

function loadingBarShow(){
    const loadingEl = document.createElement("div");
    document.body.prepend(loadingEl);
    loadingEl.classList.add("page-loader");
    loadingEl.classList.add("flex-column");
    loadingEl.classList.add("bg-dark");
    loadingEl.classList.add("bg-opacity-25");
    loadingEl.innerHTML = '<span class="spinner-border text-primary" role="status"></span><span class="text-gray-800 fs-6 fw-semibold mt-5">Loading...</span>';

    // Show page loading
    KTApp.showPageLoading();

    // Hide after 3 seconds
    setTimeout(function() {
        KTApp.hidePageLoading();
        loadingEl.remove();
    }, 1500);
}

function loadingBarShow_time(time){
    const loadingEl = document.createElement("div");
    document.body.prepend(loadingEl);
    loadingEl.classList.add("page-loader");
    loadingEl.classList.add("flex-column");
    loadingEl.classList.add("bg-dark");
    loadingEl.classList.add("bg-opacity-25");
    loadingEl.innerHTML = '<span class="spinner-border text-primary" role="status"></span><span class="text-gray-800 fs-6 fw-semibold mt-5">Loading...</span>';

    // Show page loading
    KTApp.showPageLoading();

    // Hide after 3 seconds
    setTimeout(function() {
        KTApp.hidePageLoading();
        loadingEl.remove();
    }, time);
}

function f_excel_export(tableId , name){
    let dataTbl = $('#' + tableId).DataTable();
    let buttons = new $.fn.dataTable.Buttons(dataTbl, {
        buttons:[
            {
                extend: 'excelHtml5',
                title: name + '_목록_excel_' + getCurrentDate(),
                autoFilter: true,
                text: 'Export as Excel',
                className: 'btn btn-success btn-active-light-success'
            }
        ]
    }).container().appendTo($('#kt_datatable_excel_hidden_buttons'));

}

function f_mng_uploadFile(formId, path) {
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
                    //2024METS참가기업모집공고및신청서_20240701104636.hwp
                }
            })

    });
}

function modalClose(name){
    document.querySelector('input[type=text][name=' + name + ']').value = '';
    document.querySelector('input[type=file][name=file]').value = null;
}

function f_gift_file_upload_call(id, path) {

    /* 경품사진 */
    let giftPhotoFileList = document.getElementsByName('giftPhotoFile');
    let photoIdx = parseInt($('.formTbBd:last').find('.addNum').text());
    for(let i=0; i<giftPhotoFileList.length; i++){
        let giftPhotoFile = giftPhotoFileList[i].value;
        if (nvl(giftPhotoFile, '') !== '') {
            f_company_file_upload(id, 'giftPhotoFile' + photoIdx, path);
        }
    }

    /* 회사로고 */
    let giftCompanyLogoFileList = document.getElementsByName('giftCompanyLogoFile');
    let logoIdx = parseInt($('.formTbBd:last').find('.addNum').text());
    for(let i=0; i<giftCompanyLogoFileList.length; i++){
        let giftCompanyLogoFile = giftCompanyLogoFileList[i].value;
        if (nvl(giftCompanyLogoFile, '') !== '') {
            f_company_file_upload(id, 'giftCompanyLogoFile' + logoIdx, path);
        }
    }

}

async function f_company_file_upload(userId, elementId, path) {
    let uploadFileResponse = '';
    uploadFileResponse = await f_company_uploadFile(elementId, path);
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

function f_company_uploadFile(elementId, path) {
    /* 파일 업로드 */
    let file = document.querySelector('#' + elementId);
    let formData = new FormData();
    formData.append('request',file.files[0]);

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

async function f_attach_file_upload(userId, formId, path) {
    let file = $('#attachFileInput').val();
    if(nvl(file,'') !== '') {
        let uploadFileResponse = '';
        uploadFileResponse = await f_mng_uploadFile(formId, path);
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

                    let jsonObj = {
                        userId: userId,
                        fullFilePath: fullFilePath,
                        fullPath: fullPath,
                        folderPath: folderPath,
                        fullFileName: fullFileName,
                        /*uuid: uuid,*/
                        fileName: fullFileName,
                        fileYn: 'Y'
                    };
                    let resData = ajaxConnect('/file/upload/save.do', 'post', jsonObj);
                    if (resData.resultCode === "0") {
                        let ul_el = document.getElementById('uploadFileList');
                        let li_el = document.createElement('li');
                        let a_el = document.createElement('a');

                        li_el.style.marginBottom = '5px';

                        if(fullFileName.toLowerCase().includes('.jpg')
                            || fullFileName.toLowerCase().includes('.jpeg')
                            || fullFileName.toLowerCase().includes('.png')) {
                            let img_el = document.createElement('img');
                            img_el.src = fullFilePath.replace('/usr/local/tomcat/webapps', '/../../../..');
                            img_el.classList.add('w-250px', 'mr10');
                            img_el.style.border = '1px solid #009ef7';

                            li_el.append(img_el);
                        }

                        /*a_el.href = 'javascript:f_file_download(' + '\'' + pureFileName + '\'' + ',' + '\'' + pureFilePath + '\'' +')';*/
                        /*a_el.href = '/file/download.do?path=' + path + '&fileName=' + fullFileName;*/
                        a_el.href = 'javascript:void(0);';
                        a_el.onclick = function () {
                            f_file_download(path, fullFileName);
                        };
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
                            f_file_remove(this, resData.fileId);
                        };
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
        alert('첨부된 파일이 없습니다.');
    }
}

function f_upload_modal_close(target_modal_id, target_modal_form_init_id){
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
    modalClose(target_modal_form_init_id);
}

function f_file_remove(el, fileId){
    /* 제품사진파일 : 사진파일이 하나만 등록된 상태에서 하나 남은 파일을 삭제할 경우 상단 file input, label id -> _1 로 초기화 */
    let productImageFile_li = $(el).parent().hasClass('productImageFile_li');
    if(productImageFile_li){
        let child_length = $(el).parent('li').children().length;
        if(child_length === 1){
            let fileInput = $(el).closest('div').parent().prev('div').find('input[type=file]');

            let fileInput_text = fileInput.siblings('input[type=text]');
            let fileInput_label = fileInput.siblings('label');

            let fileInput_id = fileInput.attr('id').toString();
            let fileInput_id_init = fileInput_id.split('_')[0] + '_1';
            fileInput.attr('id', fileInput_id_init);
            fileInput_label.attr('for', fileInput_id_init);

            let fileInput_text_id_init = fileInput_id_init.replace('File','');
            fileInput_text.attr('id', fileInput_text_id_init);
        }
    }

    let jsonObj = {
        id: fileId
    }

    let resData = ajaxConnect('/file/upload/update.do', 'post', jsonObj);
    if(resData.resultCode === "0"){
        $(el).parent('li').remove();
    }
}

function execDaumPostcode(address, addressDetail) {
    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    new daum.Postcode({
        width: width, //생성자에 크기 값을 명시적으로 지정해야 합니다.
        height: height,
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                // document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                // document.getElementById("sample6_extraAddress").value = '';
            }

            if(nullToEmpty(address) !== '' && nullToEmpty(addressDetail) !== ''){
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById(address).value = '(' + data.zonecode + ') ' + addr;

                // 우편번호 클릭시 초기화
                document.getElementById(addressDetail).value = '';

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById(addressDetail).focus();
            }else{
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('address').value = '(' + data.zonecode + ') ' + addr;

                // 우편번호 클릭시 초기화
                document.getElementById('address_detail').value = '';

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('address_detail').focus();
            }
        }
    }).open({
        left: (window.screen.width / 2) - (width / 2),
        top: (window.screen.height / 2) - (height / 2),
        popupTitle: '우편번호 검색 팝업', //팝업창 타이틀 설정 (영문,한글,숫자 모두 가능)
        popupKey: 'popup1' //팝업창 Key값 설정 (영문+숫자 추천)
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