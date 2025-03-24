/***
 * mng/exhibitor/participant/visitor.js
 * 참가자관리>참관객목록
 * */
var transferYear = (new Date().getFullYear()).toString();

$(function(){

    // 이메일
    $('#email_select').on('change', function () {
        let selectedOption = $(this).val();
        let domain = $('#domain');

        if (selectedOption === '직접입력') {
            domain.prop('disabled', false).val('');
        } else {
            domain.prop('disabled', true).val(selectedOption);
        }
    });

    ///////////////// 동반자 추가 /////////////////
    let partnerInfoCount = 1;

    // .partner_info_box를 추가하는 이벤트 핸들러 추가
    $('#kt_partner_info_add').on('click', function () {
        let newPartnerInfoBox = $('.partner_info_box:first').clone();
        partnerInfoCount++;
        newPartnerInfoBox.find('.partnerInfoNum').text(partnerInfoCount);
        newPartnerInfoBox.find('input[type="text"]').val('');

        // 복제된 .display_info_box 내의 삭제 버튼 보이기
        newPartnerInfoBox.find('.partnerInfoDel').show();

        newPartnerInfoBox.find('.partnerInfoDel').on('click', function () {
            deletePartnerInfoBox();
        });
        $('.partner_info_box:last').after(newPartnerInfoBox);
        updatePartnerInfoNum();
    });

    // .partner_info_box를 삭제하는 이벤트 핸들러
    function deletePartnerInfoBox() {
        Swal.fire({
            title: '해당 동반자 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mng/exhibitor/participant/visitor/deletePartner.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '동반자 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.partner_info_box').remove();
                partnerInfoCount--;
                updatePartnerInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .partner_info_box의 .partnerInfoNum 번호 업데이트
    function updatePartnerInfoNum() {
        $('.partner_info_box').each(function (index) {
            $(this).find('.partnerInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .partner_info_box 내의 삭제 버튼 숨기기
    $('.partner_info_box:first .partnerInfoDel').hide();

    // 첫 번째 .partner_info_box의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.partnerInfoDel').on('click', function () {
        deletePartnerInfoBox();
    });

    // 동반자 여부 라디오 버튼 변경 시
    $('.form_add_partner').hide();
    $('input[name="partnerYn"]').on('change', function() {
        if ($(this).is(':checked') && $(this).attr('id') === 'partnerY') {
            $('.form_add_partner').show(); // .form_chuga 요소를 보여줌
        } else {
            $('.form_add_partner').hide(); // .form_chuga 요소를 숨김
        }
    });

});

function f_participant_visitor_search_condition_init(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#joinYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#visitorGbn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_participant_visitor_search();
}

function f_participant_visitor_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_participant_visitor_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {
            "joinYear": transferYear
        };
    }else{
        jsonObj = {
            "joinYear": transferYear,
            "condition": $('#search_box option:selected').val(),
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/participant/visitor/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_participant_visitor_table tbody td');
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
    let dataTbl = $('#kt_participant_visitor_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /*
    joinYn; //참석여부
    visitorGbn; //개인단체구분
    */

    let joinYn = $('#joinYn option:selected').val();
    let visitorGbn = $('#visitorGbn option:selected').val();

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        "joinYear": transferYear, //참관년도
        "joinYn": joinYn, //참석여부
        "visitorGbn": visitorGbn, //개인단체구분
        "condition": condition,
        "searchText": searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/participant/visitor/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_participant_visitor_table tbody td');
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

function f_participant_visitor_remove(rowId){
    //console.log('삭제버튼');
    if(nullToEmpty(rowId) !== ""){
        let jsonObj = {
            seq: rowId
        }
        Swal.fire({
            title: '선택한 참관객을 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/exhibitor/participant/visitor/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '참관객 삭제', '참관객이 삭제되었습니다.', '');
                    f_board_notice_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '참관객 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_participant_visitor_modify_init_set(id){
    window.location.href = '/mng/exhibitor/participant/visitor/detail.do?seq=' + id;
}

function f_participant_visitor_save(seq){

    let gbn = 'U';
    if(nvl(seq,"") === ""){ //insert
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
            let validCheck = f_visitor_form_valid_check();

            if(validCheck){

                /* form data setting */
                let data = f_visitor_form_data_setting(gbn);

                // console.log('data : ' + data);

                $.ajax({
                    url: '/mng/exhibitor/participant/visitor/update.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            let tableSeq = data.customValue; //tableSeq return 값

                            Swal.fire({
                                title: '참관객 정보 저장',
                                text: '참관객 정보가 저장되었습니다.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_participant_visitor_modify_init_set(tableSeq); // 재조회
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '참관객 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
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

function f_visitor_form_valid_check(){

    let partGbn = $('input[type=radio][name=partGbn]:checked').val();
    if(nvl(partGbn,'') === ''){
        showMessage('', 'info', '[참관 구분]', '참관 구분 항목을 선택해 주세요.', '');
        return false;
    }

    let name = $('#name').val();
    if (nvl(name,'') === '') {
        showMessage('', 'info', '[참관객 정보]', '성명을 입력해 주세요.', '');
        return false;
    }

    let phone = $('#phone').val();
    if (nvl(phone,'') === '') {
        showMessage('', 'info', '[참관객 정보]', '휴대전화를 입력해 주세요.', '');
        return false;
    }

    let email = $('#email').val();
    if (nvl(email,'') === '') {
        showMessage('', 'info', '[참관객 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }

    let domain = $('#domain').val();
    if (nvl(domain,'') === '') {
        showMessage('', 'info', '[참관객 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    if(nvl(partGbn,'') === '바이어'){
        let companyName = $('#companyName').val();
        if (nvl(companyName,'') === '') {
            showMessage('#companyName', 'info', '[참관객 정보]', '참관 구분>바이어 선택 시 직장명을 필수 입력해 주세요.', '');
            return false;
        }

        let companyAddress = $('#companyAddress').val();
        if (nvl(companyAddress,'') === '') {
            showMessage('#companyAddress', 'info', '[참관객 정보]', '참관 구분>바이어 선택 시 직장 주소를 필수 입력해 주세요.', '');
            return false;
        }
    }

    let partnerYn = $('input[type=radio][name=partnerYn]:checked').val();
    if(partnerYn === "Y"){
        let partnerNameList = $('input[type=text][name=partnerName]');
        for(let i=0; i<partnerNameList.length; i++){
            let partnerName = partnerNameList.eq(i).val();
            if (nvl(partnerName,'') === '') {
                showMessage('', 'info', '[참관객 정보]', '동반자가 있을 경우 동반자 이름을 입력해 주세요.', '');
                return false;
            }
        }

        let partnerAgeList = $('input[type=text][name=partnerAge]');
        for(let i=0; i<partnerAgeList.length; i++){
            let partnerAge = partnerAgeList.eq(i).val();
            if (nvl(partnerAge,'') === '') {
                showMessage('', 'info', '[참관객 정보]', '동반자가 있을 경우 동반자 나이를 입력해 주세요.', '');
                return false;
            }
        }
    }

    let sidoList = $('select[name=sido]');
    for(let i=0; i<sidoList.length; i++){
        let sido = sidoList.eq(i).val();
        if (nvl(sido,'시/도 선택') === '시/도 선택') {
            showMessage('', 'info', '[설문항목]', '지역 시/도를 선택해 주세요.', '');
            return false;
        }
    }

    /*let gugunList = $('select[name=gugun]');
    for(let i=0; i<gugunList.length; i++){
        let gugun = gugunList.eq(i).val();
        if (nvl(gugun,'구/군 선택') === '구/군 선택') {
            showMessage('', 'info', '[설문항목]', '지역 구/군을 선택해 주세요.', '');
            return false;
        }
    }*/

    if(nvl(partGbn,'') === '일반관람') {
        let observationGbn = $('input[type=checkbox][name=observationGbn]').is(':checked');
        if (!observationGbn) {
            showMessage('', 'info', '[설문항목]', '관람 구분을 하나 이상 체크해주세요.', '');
            return false;
        }
    }

    let visitPurpose = $('input[type=checkbox][name=visitPurpose]').is(':checked');
    if (!visitPurpose) {
        showMessage('', 'info', '[설문항목]', '보트쇼 방문 목적을 하나 이상 체크해주세요.', '');
        return false;
    }

    let interestItem = $('input[type=checkbox][name=interestItem]').is(':checked');
    if (!interestItem) {
        showMessage('', 'info', '[설문항목]', '관심품목을 하나 이상 체크해주세요.', '');
        return false;
    }

    let recognizePath = $('input[type=checkbox][name=recognizePath]').is(':checked');
    if (!recognizePath) {
        showMessage('', 'info', '[설문항목]', '인지경로를 하나 이상 체크해주세요.', '');
        return false;
    }

    let preObservationGbn = $('input[type=checkbox][name=preObservationGbn]').is(':checked');
    if (!preObservationGbn) {
        showMessage('', 'info', '[설문항목]', '지난 전시회 참관 여부를 하나 이상 체크해주세요.', '');
        return false;
    }

    return true;
}

function f_visitor_form_data_setting(gbn){

    let visitorForm = JSON.parse(JSON.stringify($('#visitorForm').serializeObject()));

    visitorForm.lang = 'KO';

    if(gbn === 'C'){
        visitorForm.joinYear = transferYear;
        visitorForm.joinYn = 'Y';
        visitorForm.visitorGbn = '개인';
    }

    let partnerInfoArr = [];
    if(visitorForm.partnerYn === 'Y'){
        //동반자 Setting
        let partnerInfoCnt = $('.partnerInfoNum:last').text();
        for(let i=0; i<partnerInfoCnt; i++){
            let partnerInfoObj = {
                seq: $('input[type=hidden][name=partnerSeq]').eq(i).val(),
                visitorSeq: $('input[type=hidden][name=visitorSeq]').val(),
                name: $('#name').val(),
                phone: $('#phone').val(),
                partnerName: $('input[name=partnerName]').eq(i).val(),
                partnerAge: $('input[name=partnerAge]').eq(i).val()
            };
            partnerInfoArr.push(partnerInfoObj);
        }
    }
    visitorForm.partner = partnerInfoArr;

    visitorForm.domain = $('#domain').val();
    visitorForm.ageGroup = visitorForm.age;
    visitorForm.regionSi = visitorForm.sido;
    visitorForm.regionGu = nvl(visitorForm.gugun,'-');

    // 참관 구분
    let observationGbnList = $('input[type=checkbox][name=observationGbn]:checked');
    let observationGbn = '';
    let partGbn = visitorForm.partGbn;
    if(partGbn === '일반관람') {
        for (let i = 0; i < observationGbnList.length; i++) {
            observationGbn += observationGbnList.eq(i).val();
            if ((i + 1) !== observationGbnList.length) {
                observationGbn += ',';
            }
        }
    }
    visitorForm.observationGbn = observationGbn;

    // 보트쇼 방문 목적
    let visitPurposeList = $('input[type=checkbox][name=visitPurpose]:checked');
    let visitPurpose = '';
    for(let i=0; i<visitPurposeList.length; i++){
        visitPurpose += visitPurposeList.eq(i).val();
        if((i+1) !== visitPurposeList.length){
            visitPurpose += ',';
        }
    }
    visitorForm.visitPurpose = visitPurpose;

    // 관심품목
    let interestItemList = $('input[type=checkbox][name=interestItem]:checked');
    let interestItem = '';
    for(let i=0; i<interestItemList.length; i++){
        interestItem += interestItemList.eq(i).val();
        if((i+1) !== interestItemList.length){
            interestItem += ',';
        }
    }
    visitorForm.interestItem = interestItem;

    // 인지경로
    let recognizePathList = $('input[type=checkbox][name=recognizePath]:checked');
    let recognizePath = '';
    for(let i=0; i<recognizePathList.length; i++){
        recognizePath += recognizePathList.eq(i).val();
        if((i+1) !== recognizePathList.length){
            recognizePath += ',';
        }
    }
    visitorForm.recognizePath = recognizePath;

    // 지난 전시회 참관 여부
    let preObservationGbnList = $('input[type=checkbox][name=preObservationGbn]:checked');
    let preObservationGbn = '';
    for(let i=0; i<preObservationGbnList.length; i++){
        preObservationGbn += preObservationGbnList.eq(i).val();
        if((i+1) !== preObservationGbnList.length){
            preObservationGbn += ',';
        }
    }
    visitorForm.preObservationGbn = preObservationGbn;

    // Whether you attended the last exhibition
    let preObservationGbnEnList = $('input[type=checkbox][name=preObservationGbnEn]:checked');
    let preObservationGbnEn = '';
    for(let i=0; i<preObservationGbnEnList.length; i++){
        preObservationGbnEn += preObservationGbnEnList.eq(i).val();
        if((i+1) !== preObservationGbnEnList.length){
            preObservationGbnEn += ',';
        }
    }
    visitorForm.preObservationGbnEn = preObservationGbnEn;

    return JSON.stringify(visitorForm);
}

function f_visitor_excel_export(){
    Swal.fire({
        title: '참관객 상세 다운로드',
        html: '참관객 상세를 다운로드하시겠습니까 ?<br><span style="font-weight: bold; font-size: 1rem;">' +
            '( 데이터가 많을 경우 시간이 소요될 수 있습니다. )<br>( 해당 창이 닫혀도 다운로드가 완료될 때까지<br>대기해 주세요. )</span>',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        confirmButtonText: '확인',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            let fileName = 'visitor_list_' + getCurrentDate() + '.xlsx';

            $.ajax({
                url: '/mng/visitor/download.do?fileName=' + fileName + '&joinYear=' + transferYear,
                method: 'get',
                /*async: false,*/
                xhrFields: {
                responseType: 'blob'
                },
                contentType: 'application/json; charset=utf-8', //server charset 확인 필요
                beforeSend : function(request){
                    // Performed before calling Ajax
                    $('#spinner').show();
                },
                success: function (blob) {
                    let link = document.createElement('a');
                    link.href = window.URL.createObjectURL(blob);
                    link.download = fileName;
                    link.click();

                    $('#spinner').hide();
                },
                error: function() {
                    // Do when ajax call fail
                    alert('오류가 발생했습니다. 관리자에게 문의해주세요.');
                    $('#spinner').hide();
                }
            })
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