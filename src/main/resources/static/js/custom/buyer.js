/***
 * mng/exhibitor/application/buyer.js
 * 참가신청서 관리 > 초청희망바이어 신청
 * */
var transferYear = (new Date().getFullYear()).toString();
$(function(){

    // 바이어 정보 input 이메일
    $('#buyer_company_email_select').on('change', function () {
        let selectedOption = $(this).val();
        let emailInput2 = $('#buyer_company_email_input_2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }
    });

    // 추가 버튼 클릭 시
    $('.form_add_btn').on('click', function () {
        // 마지막 .formTbBd 요소를 복사하여 추가
        let lastRow = $('.formTbBd:last');
        let newRow = lastRow.clone(true);

        // 추가된 .formTbBd의 .addNum 값 증가
        let buyerNum = parseInt(newRow.find('.addNum').text());
        newRow.find('.addNum').text(buyerNum + 1);

        // 추가된 .formTbBd의 입력 필드 초기화
        newRow.find('input').val('');
        newRow.find('textarea').val('');
        newRow.find('select option').prop('selected',false);

        // card 복제
        newRow.find('.card-header').attr('data-bs-target','#kt_docs_card_collapsible' + (buyerNum + 1));
        newRow.find('.card-header i').removeClass('ki-down').addClass('ki-up');
        newRow.find('#kt_docs_card_collapsible' + buyerNum).attr('id', 'kt_docs_card_collapsible' + (buyerNum + 1));
        newRow.find('#kt_docs_card_collapsible' + (buyerNum + 1)).removeClass('show');

        // 추가된 .formTbBd를 .formPassBd에 추가
        lastRow.after(newRow);

        updateBuyerNum(); // .addNum 값 업데이트
    });

    $('.addDel').on('click', function () {
        Swal.fire({
            title: '해당 바이어 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input[type=hidden]').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mng/exhibitor/application/buyer/delete.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '바이어 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.formTbBd').remove();
                updateBuyerNum(); // .addNum 값 업데이트
            }//isConfirmed
        }); //swal
    });

    // .addNum 값 업데이트 함수
    function updateBuyerNum() {
        $('.formTbBd').each(function (index) {
            $(this).find('.addNum').text(index + 1);
        });
    }
});

function f_application_buyer_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_buyer_search();
}

function f_application_buyer_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_buyer_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {
            "transferYear": transferYear
        };
    }else{
        jsonObj = {
            "transferYear": transferYear,
            "condition": $('#search_box option:selected').val(),
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/application/buyer/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_buyer_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_buyer_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let lang = $('#lang option:selected').val(); //참가신청언어
    let approvalYn = $('#approvalYn option:selected').val(); //승인여부
    let cancelYn = $('#cancelYn option:selected').val(); //참가취소여부

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        "lang": lang,
        "approvalStatus": approvalYn,
        "cancelYn": cancelYn,
        "transferYear": transferYear,
        "condition": condition,
        "searchText": searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/application/buyer/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_buyer_table tbody td');
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

function f_application_buyer_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/buyer/detail.do?seq=' + id;
}

function f_application_buyer_save(seq){

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
            let validCheck = f_buyer_form_valid_check();

            if(validCheck){

                /* form data setting */
                let data = f_buyer_form_data_setting(seq);

                $.ajax({
                    url: '/mng/exhibitor/application/buyer/update.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            Swal.fire({
                                title: '바이어 정보',
                                text: '바이어 정보가 저장되었습니다.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_application_buyer_modify_init_set(seq); // 재조회
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '바이어 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
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

function f_buyer_form_valid_check(){

    // 회사명
    let buyerCompanyNameList = $('input[type=text][name=buyerCompanyName]');
    for(let i=0; i<buyerCompanyNameList.length; i++){
        let buyerCompanyName = buyerCompanyNameList.eq(i).val();
        if (nvl(buyerCompanyName,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '회사명을 입력해 주세요.', '');
            return false;
        }
    }

    // 국가
    let buyerCompanyCountryList = $('input[type=text][name=buyerCompanyCountry]');
    for(let i=0; i<buyerCompanyCountryList.length; i++){
        let buyerCompanyCountry = buyerCompanyCountryList.eq(i).val();
        if (nvl(buyerCompanyCountry,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '국가를 입력해 주세요.', '');
            return false;
        }
    }

    // 소재지
    let buyerCompanyLocationList = $('input[type=text][name=buyerCompanyLocation]');
    for(let i=0; i<buyerCompanyLocationList.length; i++){
        let buyerCompanyLocation = buyerCompanyLocationList.eq(i).val();
        if (nvl(buyerCompanyLocation,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '소재지를 입력해 주세요.', '');
            return false;
        }
    }

    // 홈페이지
    let buyerCompanyHomepageList = $('input[type=text][name=buyerCompanyHomepage]');
    for(let i=0; i<buyerCompanyHomepageList.length; i++){
        let buyerCompanyHomepage = buyerCompanyHomepageList.eq(i).val();
        if (nvl(buyerCompanyHomepage,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '홈페이지 주소를 입력해 주세요.', '');
            return false;
        }
    }

    // 취급품목
    let buyerCompanyItemList = $('input[type=text][name=buyerCompanyItem]');
    for(let i=0; i<buyerCompanyItemList.length; i++){
        let buyerCompanyItem = buyerCompanyItemList.eq(i).val();
        if (nvl(buyerCompanyItem,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '취급품목을 입력해 주세요.', '');
            return false;
        }
    }

    // 초청사유
    let buyerCompanyInviteReasonList = $('input[type=text][name=buyerCompanyInviteReason]');
    for(let i=0; i<buyerCompanyInviteReasonList.length; i++){
        let buyerCompanyInviteReason = buyerCompanyInviteReasonList.eq(i).val();
        if (nvl(buyerCompanyInviteReason,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '초청사유를 입력해 주세요.', '');
            return false;
        }
    }

    // 희망사항
    let buyerCompanyHopeList = $('input[type=text][name=buyerCompanyHope]');
    for(let i=0; i<buyerCompanyHopeList.length; i++){
        let buyerCompanyHope = buyerCompanyHopeList.eq(i).val();
        if (nvl(buyerCompanyHope,"") === "") {
            showMessage('', 'error', '[바이어 등록 정보]', '희망사항을 입력해 주세요.', '');
            return false;
        }
    }

    return true;
}

function f_buyer_form_data_setting(exhibitorSeq){

    // pass table seq
    let buyerSeqList = $('input[type=hidden][name=buyerSeq]');

    // 회사명
    let buyerCompanyNameList = $('input[type=text][name=buyerCompanyName]');

    // 국가
    let buyerCompanyCountryList = $('input[type=text][name=buyerCompanyCountry]');

    // 지역
    let buyerCompanyLocationList = $('input[type=text][name=buyerCompanyLocation]');

    // 홈페이지
    let buyerCompanyHomepageList = $('input[type=text][name=buyerCompanyHomepage]');

    // 부서
    let buyerCompanyDepartList = $('input[type=text][name=buyerCompanyDepart]');

    // 직책
    let buyerCompanyPositionList = $('input[type=text][name=buyerCompanyPosition]');

    // 이메일
    let buyerCompanyEmailList = $('input[type=text][name=buyerCompanyEmail]');

    // 도메인
    let buyerCompanyDomainList = $('input[type=text][name=buyerCompanyDomain]');

    // 전화번호
    let buyerCompanyTelList = $('input[type=text][name=buyerCompanyTel]');

    // 휴대전화
    let buyerCompanyPhoneList = $('input[type=text][name=buyerCompanyPhone]');

    // FAX
    let buyerCompanyFaxList = $('input[type=text][name=buyerCompanyFax]');

    // 취급품목
    let buyerCompanyItemList = $('input[type=text][name=buyerCompanyItem]');

    // 초청사유
    let buyerCompanyInviteReasonList = $('textarea[name=buyerCompanyInviteReason]');

    // 계약진행 여부
    let buyerCompanyProgressYnList = $('select[name=buyerCompanyProgressYn] option:checked');

    // 희망사항
    let buyerCompanyHopeList = $('input[type=text][name=buyerCompanyHope]');

    let buyerCnt = $('.formTbBd:last .addNum').text();

    let buyerListArr = [];
    for(let i=0; i<buyerCnt; i++){
        let jsonObj = {
            seq: buyerSeqList.eq(i).val(),
            id: exhibitorSeq,
            buyerCompanyName: buyerCompanyNameList.eq(i).val(),
            buyerCompanyCountry: buyerCompanyCountryList.eq(i).val(),
            buyerCompanyLocation: buyerCompanyLocationList.eq(i).val(),
            buyerCompanyHomepage: buyerCompanyHomepageList.eq(i).val(),
            buyerCompanyDepart: buyerCompanyDepartList.eq(i).val(),
            buyerCompanyPosition: buyerCompanyPositionList.eq(i).val(),
            buyerCompanyEmail: buyerCompanyEmailList.eq(i).val() + '@' + buyerCompanyDomainList.eq(i).val(),
            buyerCompanyTel: buyerCompanyTelList.eq(i).val(),
            buyerCompanyPhone: buyerCompanyPhoneList.eq(i).val(),
            buyerCompanyFax: buyerCompanyFaxList.eq(i).val(),
            buyerCompanyItem: buyerCompanyItemList.eq(i).val(),
            buyerCompanyInviteReason: buyerCompanyInviteReasonList.eq(i).val(),
            buyerCompanyProgressYn: buyerCompanyProgressYnList.eq(i).val(),
            buyerCompanyHope: buyerCompanyHopeList.eq(i).val()
        }

        buyerListArr.push(jsonObj);
    }

    let exhibitorArr = {
        seq: exhibitorSeq,
        buyerList: buyerListArr
    };

    return JSON.stringify(exhibitorArr);
}