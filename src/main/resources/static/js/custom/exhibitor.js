/***
 * mng/exhibitor/participant/exhibitor.js
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
    let dataTbl = $('#kt_exhibitor_table').DataTable();
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
    let jb = $('#kt_exhibitor_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_table').DataTable();
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
    let jb = $('#kt_exhibitor_table tbody td');
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

function f_exhibitor_pre_page_move(){
    let referrer = document.referrer;

    if(referrer.includes('company.do')){

        // 전시업체 페이지에서 이동 - /mng/exhibitor/participant/company.do
        window.location.href = '/mng/exhibitor/participant/company.do';

    }else if(referrer.includes('member.do')){

        // 회원목록 페이지에서 이동 - /mng/exhibitor/participant/member.do
        window.location.href = '/mng/exhibitor/participant/member.do';

    }

}

function f_exhibitor_detail_modal_set(seq){
    /* 목록 상세 조회 */
    let jsonObj = {
        "seq": seq
    };

    /* TODO: 상세페이지가 아닌 이력 조회 해야함. 지금은 임시*/
    let resData = ajaxConnect('/mng/exhibitor/participant/company/selectSingle.do', 'post', jsonObj);

    /* 상세보기 Modal form Set */
    console.log(resData);

    /*document.querySelector('#md_title').value = resData.title;
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

    /!* 파일 목록 상세 조회 *!/
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
    }*/
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

function f_exhibitor_save(seq){

    let gbn = 'U';
    let url = '/mng/exhibitor/participant/company/update.do';
    if(nvl(seq,"") === ""){ //insert
        gbn = 'C';
        url = '/mng/exhibitor/participant/company/insert.do';
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
            let validCheck = f_company_form_valid_check(gbn);

            if(validCheck){

                /* form data setting */
                let data = f_form_data_setting(gbn);

                //console.log('data : ' + data);

                $.ajax({
                    url: url,
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            /* file function */
                            let tableSeq = data.customValue; //tableSeq return 값
                            f_company_file_upload_call(tableSeq, tableSeq);

                            let timerInterval;
                            Swal.fire({
                                title: "정보 저장 중",
                                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해 주세요.",
                                allowOutsideClick: false,
                                timer: 5000,
                                timerProgressBar: true,
                                didOpen: () => {
                                    Swal.showLoading();
                                    const timer = Swal.getPopup().querySelector("b");
                                    timerInterval = setInterval(() => {
                                        timer.textContent = `${Swal.getTimerLeft()}`;
                                    }, 1000);
                                },
                                willClose: () => {
                                    clearInterval(timerInterval);
                                }
                            }).then((result) => {
                                /* Read more about handling dismissals below */
                                if (result.dismiss === Swal.DismissReason.timer) {
                                    Swal.fire({
                                        title: '전시 업체 정보 저장',
                                        html: '전시 업체 정보가 저장되었습니다.',
                                        icon: 'info',
                                        allowOutsideClick: false,
                                        confirmButtonColor: '#00a8ff',
                                        confirmButtonText: '확인'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            f_exhibitor_detail(tableSeq); // 재조회
                                        }
                                    })
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '전시 업체 정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })

            }//validCheck
        }//isConfirmed
    })//then

}

function f_company_form_valid_check(gbn){

    if(gbn === 'C'){ // insert
        let id = $('#newId').val();
        if (nvl(id,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '아이디를 입력해 주세요.', '');
            return false;
        }
    }

    let prcYn = $('#condition_prc_yn option:selected').val();
    if (nvl(prcYn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '참가비 수납 상태를 선택해 주세요.', '');
        return false;
    }

    let companyNameKo = $('#companyNameKo').val();
    if (nvl(companyNameKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }

    let companyNameEn = $('#companyNameEn').val();
    if (nvl(companyNameEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    let companyAddress = $('#companyAddress').val();
    if (nvl(companyAddress,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '주소를 검색해 주세요.', '');
        return false;
    }

    let companyAddressDetail = $('#companyAddressDetail').val();
    if (nvl(companyAddressDetail,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '상세주소를 입력해 주세요.', '');
        return false;
    }

    let companyCeo = $('#companyCeo').val();
    if (nvl(companyCeo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '대표자명을 입력해 주세요.', '');
        return false;
    }

    let companyTel = $('#companyTel').val();
    if (nvl(companyTel,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '전화번호를 입력해 주세요.', '');
        return false;
    }
    
    let industryPart = $('#industryPart').val();
    if(nvl(industryPart,'') === ''){
        showMessage('', 'info', '입력 정보 확인', '산업 분류를 선택해 주세요.', '');
        return false;
    }else{
        if(industryPart === '기타'){
            let industryPartEtc = $('#industryPartEtc').val();
            if(nvl(industryPartEtc,'') === ''){
                showMessage('', 'info', '입력 정보 확인', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    let employeeCnt = $('#employeeCnt').val();
    if(nvl(employeeCnt,'') === ''){
        showMessage('', 'info', '입력 정보 확인', '임직원 수 항목을 입력해 주세요.', '');
        return false;
    }

    if(gbn === 'C') { // insert
        let companyLicense = $('#companyLicense').val();
        if (nvl(companyLicense,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '사업자등록증을 첨부해 주세요.', '');
            return false;
        }
    }else{
        let companyLicenseFile_li = $('.companyLicenseFile_li').length;
        if(companyLicenseFile_li === 0){
            let companyLicense = $('#companyLicense').val();
            if (nvl(companyLicense,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '사업자등록증을 첨부해 주세요.', '');
                return false;
            }
        }
    }

    let companyLicenseNum = $('#companyLicenseNum').val();
    if (nvl(companyLicenseNum,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '사업자등록번호를 입력해 주세요.', '');
        return false;
    }

    let memberCompanyYn = $('input[type=radio][name=memberCompanyYn]:checked').val();
    if(nvl(memberCompanyYn,"") === ""){
        showMessage('', 'info', '입력 정보 확인', '회원사 여부를 체크해 주세요.', '');
        return false;
    }

    let chargePersonNameList = $('input[type=text][name=chargePersonName]');
    /*for(let i=0; i<chargePersonNameList.length; i++){
        let chargePersonName = chargePersonNameList.eq(i).val();
        if (nvl(chargePersonName,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '담당자 성명을 입력해 주세요.', '');
            return false;
        }
    }*/

    let chargePersonPositionList = $('input[type=text][name=chargePersonPosition]');
    /*for(let i=0; i<chargePersonPositionList.length; i++){
        let chargePersonPosition = chargePersonPositionList.eq(i).val();
        if (nvl(chargePersonPosition,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '담당자 직위를 입력해 주세요.', '');
            return false;
        }
    }*/

    let chargePersonTelList = $('input[type=tel][name=chargePersonTel]');
    /*for(let i=0; i<chargePersonTelList.length; i++){
        let chargePersonTel = chargePersonTelList.eq(i).val();
        if (nvl(chargePersonTel,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '담당자 전화번호를 입력해 주세요.', '');
            return false;
        }
    }*/

    let chargePersonPhoneList = $('input[type=tel][name=chargePersonPhone]');
    /*for(let i=0; i<chargePersonPhoneList.length; i++){
        let chargePersonPhone = chargePersonPhoneList.eq(i).val();
        if (nvl(chargePersonPhone,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '담당자 휴대전화번호를 입력해 주세요.', '');
            return false;
        }
    }*/

    let chargePersonEmailList = $('input[type=email][name=chargePersonEmail]');
    /*for(let i=0; i<chargePersonEmailList.length; i++){
        let chargePersonEmail = chargePersonEmailList.eq(i).val();
        if (nvl(chargePersonEmail,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '담당자 이메일 주소를 입력해 주세요.', '');
            return false;
        }
    }*/

    let chargePersonDomainList = $('input[type=email][name=chargePersonDomain]');
    /*for(let i=0; i<chargePersonDomainList.length; i++){
        let chargePersonDomain = chargePersonDomainList.eq(i).val();
        if (nvl(chargePersonDomain,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '담당자 이메일 도메인 주소를 입력해 주세요.', '');
            return false;
        }
    }*/

    let fieldPart = $('input[type=checkbox][name=fieldPart]').is(':checked');
    if (!fieldPart) {
        showMessage('', 'info', '입력 정보 확인', '참가분야를 하나 이상 체크해 주세요.', '');
        return false;
    }

    let displayItemList = $('input[type=text][name=displayItem]');
    for(let i=0; i<displayItemList.length; i++){
        let displayItem = displayItemList.eq(i).val();
        if (nvl(displayItem,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '전시품목을 입력해 주세요.', '');
            return false;
        }
    }

    let displayBrandList = $('input[type=text][name=displayBrand]');
    for(let i=0; i<displayBrandList.length; i++){
        let displayBrand = displayBrandList.eq(i).val();
        if (nvl(displayBrand,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '전시품목 브랜드명을 입력해 주세요.', '');
            return false;
        }
    }

    let displayBoatCntList = $('input[type=text][name=displayBoatCnt]');
    for(let i=0; i<displayBoatCntList.length; i++){
        let displayBoatCnt = displayBoatCntList.eq(i).val();
        if (nvl(displayBoatCnt,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '실물 보트수(단위:척)을 입력해 주세요.', '');
            return false;
        }
    }

    let displayItemIntroKoList = $('textarea[name=displayItemIntroKo]');
    for(let i=0; i<displayItemIntroKoList.length; i++){
        let displayItemIntroKo = displayItemIntroKoList.eq(i).val();
        if (nvl(displayItemIntroKo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '전시품소개(국문)를 입력해 주세요.', '');
            return false;
        }
    }

    let displayItemIntroEnList = $('textarea[name=displayItemIntroEn]');
    for(let i=0; i<displayItemIntroEnList.length; i++){
        let displayItemIntroEn = displayItemIntroEnList.eq(i).val();
        if (nvl(displayItemIntroEn,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '전시품소개(영문)를 입력해 주세요.', '');
            return false;
        }
    }

    let companyIntroKo = $('#companyIntroKo').val();
    if (nvl(companyIntroKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }

    let companyIntroEn = $('#companyIntroEn').val();
    if (nvl(companyIntroEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    let companyPurposeKo = $('#companyPurposeKo').val();
    if (nvl(companyPurposeKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', 'KIBS 참가목적(국문)을 선택해 주세요.', '');
        return false;
    }

    let companyPurposeEn = $('#companyPurposeEn').val();
    if (nvl(companyPurposeEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', 'KIBS 참가목적(영문)을 선택해 주세요.', '');
        return false;
    }

    let boothVisitReason = $('#boothVisitReason').val();
    if (nvl(boothVisitReason,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '\'우리 기업 부스는 꼭 들려야 될 이유가 있다면?\' 을 입력해 주세요.', '');
        return false;
    }

    let eventPlan = $('#eventPlan').val();
    if (nvl(eventPlan,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '행사/이벤트 진행계획을 입력해 주세요.', '');
        return false;
    }

    /*let officeRequest = $('#officeRequest').val();
    if (nvl(officeRequest,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '사무국 요청사항을 입력해 주세요.', '');
        return false;
    }*/

    if(gbn === 'C') {
        let logo = $('#logo').val();
        if (nvl(logo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '로고 이미지를 첨부해 주세요.', '');
            return false;
        }
    }else{
        let logoFile_li = $('.logoFile_li').length;
        if(logoFile_li === 0){
            let logo = $('#logo').val();
            if (nvl(logo,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '로고 이미지를 첨부해 주세요.', '');
                return false;
            }
        }
    }

    if(gbn === 'C'){
        let promotionImageList = $('input[type=text][name=promotionImage]');
        let promotionImage = promotionImageList.eq(0).val();
        if (nvl(promotionImage,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '홍보 이미지1 은 필수로 첨부해 주세요.', '');
            return false;
        }
    }else{
        let promotionImageFile_li = $('.promotionImageFile_li').length;
        let promotionImageListVal = $('input[type=text][name=promotionImage]').eq(0).val();
        if( (promotionImageFile_li === 0) && nvl(promotionImageListVal,"") === ""){
            showMessage('', 'info', '입력 정보 확인', '홍보 이미지1 은 필수로 첨부해 주세요.', '');
            return false;
        }
    }

    if(gbn === 'C'){
        let productImageList = $('input[type=text][name=productImage]');
        for(let i=0; i<productImageList.length; i++){
            let productImage = productImageList.eq(i).val();
            if (nvl(productImage,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '제품 사진을 첨부해 주세요.', '');
                return false;
            }
        }
    }else{
        let onlineInfoBox = $('.online_info_box');
        for(let i=0; i<onlineInfoBox.length; i++){
            let inputFile = onlineInfoBox.eq(i).find('.upload_name').val();
            let preFileList = onlineInfoBox.eq(i).find('li.productImageFile_li').length;
            if(nvl(inputFile,"") === "" && preFileList === 0){
                showMessage('', 'info', '입력 정보 확인', '제품 사진을 첨부해 주세요.', '');
                return false;
            }
        }
    }

    let productNameKoList = $('input[type=text][name=productNameKo]');
    for(let i=0; i<productNameKoList.length; i++){
        let productNameKo = productNameKoList.eq(i).val();
        if (nvl(productNameKo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품명(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    let productNameEnList = $('input[type=text][name=productNameEn]');
    for(let i=0; i<productNameEnList.length; i++){
        let productNameEn = productNameEnList.eq(i).val();
        if (nvl(productNameEn,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품명(영문)을 입력해 주세요.', '');
            return false;
        }
    }

    if(gbn === 'C'){
        let productOptionBigList = $('select[name=productOptionBig]');
        for(let i=0; i<productOptionBigList.length; i++){
            let productOptionBig = productOptionBigList.eq(i).val();
            if (nvl(productOptionBig,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 첫 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }

        let productOptionSmallList = $('select[name=productOptionSmall]');
        for(let i=0; i<productOptionSmallList.length; i++){
            let productOptionSmall = productOptionSmallList.eq(i).val();
            if (nvl(productOptionSmall,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 두 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }
    }else{
        let productOptionBigList = $('.preValueList select[name=productOptionBig]');
        for(let i=0; i<productOptionBigList.length; i++){
            let productOptionBig = productOptionBigList.eq(i).val();
            if (nvl(productOptionBig,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 첫 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }

        let productOptionSmallList = $('.preValueList select[name=productOptionSmall]');
        for(let i=0; i<productOptionSmallList.length; i++){
            let productOptionSmall = productOptionSmallList.eq(i).val();
            if (nvl(productOptionSmall,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 두 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }
    }

    let productIntroKoList = $('textarea[name=productIntroKo]');
    for(let i=0; i<productIntroKoList.length; i++){
        let productIntroKo = productIntroKoList.eq(i).val();
        if (nvl(productIntroKo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품 설명(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    let productIntroEnList = $('textarea[name=productIntroEn]');
    for(let i=0; i<productIntroEnList.length; i++){
        let productIntroEn = productIntroEnList.eq(i).val();
        if (nvl(productIntroEn,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }
    }

    /*let exportMeetingYn = $('input[type=radio][name=exportMeetingYn]:checked').val();
    if(exportMeetingYn === "Y"){
        let formChugaListLen = $('.form_chuga_list').length;
        if(formChugaListLen === 0){
            showMessage('', 'info', '입력 정보 확인', '수출상담회 참가를 희망하실 경우 바이어를 등록해 주세요.', '');
            return false;
        }
    }*/

    return true;
}

function f_form_data_setting(gbn){

    /* 상세정보 */
    let companyForm = JSON.parse(JSON.stringify($('#companyForm').serializeObject()));

    //언어
    companyForm.lang = 'KO';

    if(gbn === 'C'){
        companyForm.transferYear = transferYear;

        //참가상태
        companyForm.participationStatus = '참가확정';

        //신청폼작성여부
        companyForm.applyComplt = 'N'; //작성중

        //승인구분
        companyForm.approvalStatus = '승인요청';

        //부스번호 - 최초 등록
        companyForm.boothNum = $('#newBoothNum').val();

        //아이디 - 최초 등록
        companyForm.id = $('#newId').val();
    }

    //참가비수납여부
    companyForm.prcYn = $('#condition_prc_yn option:selected').val();

    //담당자 Setting
    let chargeInfoCnt = $('.chargeInfoNum:last').text();
    let chargeInfoArr = [];
    for(let i=0; i<chargeInfoCnt; i++){
        let chargeInfoObj = {
            "seq": $('input[type=hidden][name=chargeSeq]').eq(i).val(),
            "userId": companyForm.id,
            "transferYear": companyForm.transferYear,
            "chargePersonName": $('input[name=chargePersonName]').eq(i).val(),
            "chargePersonPosition": $('input[name=chargePersonPosition]').eq(i).val(),
            "chargePersonDepart": $('input[name=chargePersonDepart]').eq(i).val(),
            "chargePersonTel": $('input[name=chargePersonTel]').eq(i).val(),
            "chargePersonPhone": $('input[name=chargePersonPhone]').eq(i).val(),
            "chargePersonEmail": $('input[name=chargePersonEmail]').eq(i).val() + '@' + $('input[name=chargePersonDomain]').eq(i).val()
        };
        chargeInfoArr.push(chargeInfoObj);
    }
    companyForm.chargePersonList = chargeInfoArr;

    //기참가연도
    let prePartYearList = $('input[type=checkbox][name=prePartYear]:checked');
    let prePartYearListLen = prePartYearList.length;
    let prePartYear = '';
    for(let i=0; i<prePartYearListLen; i++){
        prePartYear += prePartYearList.eq(i).val();
        if((i+1) !== prePartYearListLen){
            prePartYear += ',';
        }
    }
    companyForm.prePartYear = prePartYear;

    //참가분야
    let fieldPartList = $('input[type=checkbox][name=fieldPart]:checked');
    let fieldPartListLen = fieldPartList.length;
    let fieldPart = '';
    for(let i=0; i<fieldPartListLen; i++){
        fieldPart += fieldPartList.eq(i).val();
        if((i+1) !== fieldPartListLen){
            fieldPart += ',';
        }
    }
    companyForm.fieldPart = fieldPart;

    //기업뱃지
    let companyBadgeList = $('input[type=checkbox][name=companyBadge]:checked');
    let companyBadgeLen = companyBadgeList.length;
    let companyBadge = '';
    for(let i=0; i<companyBadgeLen; i++){
        companyBadge += companyBadgeList.eq(i).val();
        if((i+1) !== companyBadgeLen){
            companyBadge += ',';
        }
    }
    companyForm.companyBadge = companyBadge;

    //전시정보 Setting
    let displayInfoCnt = $('.exhiInfoNum:last').text();
    let displayInfoArr = [];
    for(let i=0; i<displayInfoCnt; i++){
        let displayInfoObj = {
            "seq": $('input[type=hidden][name=displayItemSeq]').eq(i).val(),
            "userId": companyForm.id,
            "transferYear": companyForm.transferYear,
            "displayItem": $('input[name=displayItem]').eq(i).val(),
            "displayBrand": $('input[name=displayBrand]').eq(i).val(),
            "displayBoatCnt": $('input[name=displayBoatCnt]').eq(i).val(),
            "displayItemIntroKo": $('textarea[name=displayItemIntroKo]').eq(i).val(),
            "displayItemIntroEn": $('textarea[name=displayItemIntroEn]').eq(i).val()
        };
        displayInfoArr.push(displayInfoObj);
    }
    companyForm.displayList = displayInfoArr;

    //온라인전시회 Setting
    let onlineInfoCnt = $('.onlineInfoNum:last').text();
    let onlineInfoArr = [];
    for(let i=0; i<onlineInfoCnt; i++){
        let productOptionBig = '';
        let productOptionSmall = '';
        if(gbn === 'C'){
            productOptionBig = $('select[name=productOptionBig]').eq(i).val();
            productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
        }else{
            let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
            if(nvl(selProductOptionBig,"선택") !== "선택"){
                productOptionBig = $('select[name=productOptionBig]').eq(i).val();
                productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
            }else{
                productOptionBig = $('input[type=text][name=productOptionBig]').eq(i).val();
                productOptionSmall = $('input[type=text][name=productOptionSmall]').eq(i).val();
            }
        }

        let onlineInfoObj = {
            "seq": $('input[type=hidden][name=onlineItemSeq]').eq(i).val(),
            "userId": companyForm.id,
            "transferYear": companyForm.transferYear,
            "productNameKo": $('input[name=productNameKo]').eq(i).val(),
            "productNameEn": $('input[name=productNameEn]').eq(i).val(),
            "productOptionBig": productOptionBig,
            "productOptionSmall": productOptionSmall,
            "productIntroKo": $('textarea[name=productIntroKo]').eq(i).val(),
            "productIntroEn": $('textarea[name=productIntroEn]').eq(i).val(),
            "productIntroVideo": $('input[name=productIntroVideo]').eq(i).val(),
            "productWidth": $('input[name=productWidth]').eq(i).val(),
            "productHorsePower": $('input[name=productHorsePower]').eq(i).val(),
            "note": (i+1)
        };
        onlineInfoArr.push(onlineInfoObj);
    }
    companyForm.onlineExhibitList = onlineInfoArr;

    //수출상담회 Setting
    let exportInfoCnt = document.getElementsByClassName('form_chuga_list').length;
    let exportInfoArr = [];
    for(let i=0; i<exportInfoCnt; i++){
        let exportInfoObj = {
            "seq": $('input[type=hidden][name=buyerItemSeq]').eq(i).val(),
            "userId": companyForm.id,
            "transferYear": companyForm.transferYear,
            "buyerCompanyName": $('input[type=hidden][name=buyerCompanyName]').eq(i).val(),
            "buyerCompanyCountry": $('input[type=hidden][name=buyerCompanyCountry]').eq(i).val(),
            "buyerCompanyLocation": $('input[type=hidden][name=buyerCompanyLocation]').eq(i).val(),
            "buyerCompanyHomepage": $('input[type=hidden][name=buyerCompanyHomepage]').eq(i).val(),
            "buyerCompanyDepart": $('input[type=hidden][name=buyerCompanyDepart]').eq(i).val(),
            "buyerCompanyPosition": $('input[type=hidden][name=buyerCompanyPosition]').eq(i).val(),
            "buyerCompanyEmail": $('input[type=hidden][name=buyerCompanyEmail]').eq(i).val(),
            "buyerCompanyTel": $('input[type=hidden][name=buyerCompanyTel]').eq(i).val(),
            "buyerCompanyPhone": $('input[type=hidden][name=buyerCompanyPhone]').eq(i).val(),
            "buyerCompanyFax": $('input[type=hidden][name=buyerCompanyFax]').eq(i).val(),
            "buyerCompanyItem": $('input[type=hidden][name=buyerCompanyItem]').eq(i).val(),
            "buyerCompanyInviteReason": $('input[type=hidden][name=buyerCompanyInviteReason]').eq(i).val(),
            "buyerCompanyProgressYn": $('input[type=hidden][name=buyerCompanyProgressYn]').eq(i).val(),
            "buyerCompanyHope": $('input[type=hidden][name=buyerCompanyHope]').eq(i).val()
        };
        exportInfoArr.push(exportInfoObj);
    }
    companyForm.buyerList = exportInfoArr;

    return JSON.stringify(companyForm);
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