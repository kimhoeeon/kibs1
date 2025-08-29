/***
 * mng/exhibitor/application/gift.js
 * 참가신청서 관리 > 경품제공 신청
 * */
var transferYear = '2026';

$(function(){

    // 추가 버튼 클릭 시
    $('.form_add_btn').on('click', function () {
        // 마지막 .formTbBd 요소를 복사하여 추가
        let lastRow = $('.formTbBd:last');
        let newRow = lastRow.clone(true);

        newRow.find('.fileList').remove();

        // 추가된 .formTbBd의 .addNum 값 증가
        let passNum = parseInt(newRow.find('.addNum').text());
        newRow.find('.addNum').text(passNum + 1);

        // 추가된 .formTbBd의 입력 필드 초기화
        newRow.find('input[type=text]').val('');
        newRow.find('input[type=file]').val('');
        newRow.find('input[type=hidden]').val('');
        newRow.find('textarea').val('');

        // card 복제
        newRow.find('.card-header').attr('data-bs-target', '#kt_docs_card_collapsible' + (passNum + 1));
        newRow.find('.card-header i').removeClass('ki-down').addClass('ki-up');
        newRow.find('#kt_docs_card_collapsible' + passNum).attr('id', 'kt_docs_card_collapsible' + (passNum + 1));
        newRow.find('#kt_docs_card_collapsible' + (passNum + 1)).removeClass('show');

        newRow.find('input[type=file][name=giftPhotoFile]').eq(0).attr('id','giftPhotoFile'+(passNum + 1));
        newRow.find('input[type=file][name=giftPhotoFile]').eq(0).next('label').attr('for','giftPhotoFile'+(passNum + 1));
        newRow.find('input[type=file][name=giftCompanyLogoFile]').eq(0).attr('id','giftCompanyLogoFile'+(passNum + 1));
        newRow.find('input[type=file][name=giftCompanyLogoFile]').eq(0).next('label').attr('for','giftCompanyLogoFile'+(passNum + 1));

        // 추가된 .formTbBd를 .formPassBd에 추가
        lastRow.after(newRow);

        updateGiftNum(); // .addNum 값 업데이트
    });

    $('.addDel').on('click', function () {
        Swal.fire({
            icon: 'warning',
            title: '[ 경품제공 신청 ]',
            html: '<span style="font-size: 1.2em;">해당 경품 정보를 삭제하시겠습니까?</span>',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input[type=hidden][name=giftSeq]').val();
                let id = $(this).siblings('input[type=hidden][name=giftId]').val();
                let giftPhoto = $(this).siblings('input[type=hidden][name=giftPhoto_hid]').val();
                let giftCompanyLogo = $(this).siblings('input[type=hidden][name=giftCompanyLogo_hid]').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        seq: seq,
                        exSeq: id,
                        giftPhoto: giftPhoto,
                        giftCompanyLogo: giftCompanyLogo
                    };
                    let resData = ajaxConnect('/mng/exhibitorNew/application/gift/delete.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '경품 정보 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.formTbBd').remove();
                updateGiftNum(); // .addNum 값 업데이트
            }//isConfirmed
        }); //swal
    });

    // .addNum 값 업데이트 함수
    function updateGiftNum() {
        $('.formTbBd').each(function (index) {
            $(this).find('.addNum').text(index + 1);
        });
    }
})

function f_application_gift_new_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_gift_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
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

    let resData = ajaxConnect('/mng/exhibitorNew/application/gift/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_gift_new_table tbody td');
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

function f_application_gift_new_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');

    /* 재조회 */
    f_application_gift_new_search();
}

function f_application_gift_new_modify_init_set(seq){
    window.location.href = '/mng/exhibitorNew/application/gift/detail.do?seq=' + seq;
}

function f_application_gift_new_save(seq){

    Swal.fire({
        icon: 'info',
        title: '[ 경품제공 정보 ]',
        html: '<span style="font-size: 1.2em;">입력된 정보를 저장하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '변경내용저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 유효성 검사 */
            let validCheck = f_gift_form_valid_check();

            if(validCheck){

                /* form data setting */
                let data = f_gift_form_data_setting(seq);

                $.ajax({
                    url: '/mng/exhibitorNew/application/gift/update.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            /* file function */
                            f_gift_file_upload_call(seq, seq);

                            Swal.fire({
                                icon: 'info',
                                title: '[ 경품제공 정보 ]',
                                html: '<span style="font-size: 1.2em;">경품 신청 정보가 저장되었습니다.</span>',
                                allowOutsideClick: false,
                                confirmButtonColor: '#00a8ff',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_application_gift_new_modify_init_set(seq); // 재조회
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '경품 신청 정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
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

function f_gift_form_valid_check(){

    // 수량
    let giftCntList = $('input[type=text][name=giftCnt]');
    for(let i=0; i<giftCntList.length; i++){
        let giftCnt = giftCntList.eq(i).val();
        if (nvl(giftCnt,"") === "") {
            showMessage('', 'error', '[ 경품제공 정보 ]', '수량을 입력해 주세요.', '');
            return false;
        }
    }

    // 품목명
    let giftNameList = $('input[type=text][name=giftName]');
    for(let i=0; i<giftNameList.length; i++){
        let giftName = giftNameList.eq(i).val();
        if (nvl(giftName,"") === "") {
            showMessage('', 'error', '[ 경품제공 정보 ]', '품목명을 입력해 주세요.', '');
            return false;
        }
    }

    // 경품설명
    let giftContentList = $('input[type=text][name=giftContent]');
    for(let i=0; i<giftContentList.length; i++){
        let giftContent = giftContentList.eq(i).val();
        if (nvl(giftContent,"") === "") {
            showMessage('', 'error', '[ 경품제공 정보 ]', '경품설명을 입력해 주세요.', '');
            return false;
        }
    }

    // 소비자가
    let giftPriceList = $('input[type=text][name=giftPrice]');
    for(let i=0; i<giftPriceList.length; i++){
        let giftPrice = giftPriceList.eq(i).val();
        if (nvl(giftPrice,"") === "") {
            showMessage('', 'error', '[ 경품제공 정보 ]', '소비자가를 입력해 주세요.', '');
            return false;
        }
    }

    return true;
}

function f_gift_form_data_setting(exhibitorSeq){

    // gift table seq
    let giftSeqList = $('input[type=hidden][name=giftSeq]');

    // 수량
    let giftCntList = $('input[type=text][name=giftCnt]');

    // 분류
    let giftClassifyList = $('input[type=text][name=giftClassify]');

    // 품목명
    let giftNameList = $('input[type=text][name=giftName]');

    // 설명
    let giftContentList = $('textarea[name=giftContent]');

    // 경품사진 ID
    let giftPhotoList = $('input[type=file][name=giftPhotoFile]');

    // 경품회사사진 ID
    let giftCompanyLogoList = $('input[type=file][name=giftCompanyLogoFile]');

    // 소비자가
    let giftPriceList = $('input[type=text][name=giftPrice]');

    // 협찬가
    let giftSponsorPriceList = $('input[type=text][name=giftSponsorPrice]');

    // 비고
    let giftNoteList = $('textarea[name=giftNote]');

    let giftCnt = $('.formTbBd:last .addNum').text();

    let giftListArr = [];
    for(let i=0; i<giftCnt; i++){
        let jsonObj = {
            seq: giftSeqList.eq(i).val(),
            exSeq: exhibitorSeq,
            giftGbn: '행사용',
            giftCnt: giftCntList.eq(i).val(),
            giftClassify: giftClassifyList.eq(i).val(),
            giftName: giftNameList.eq(i).val(),
            giftContent: giftContentList.eq(i).val(),
            giftPhoto: giftPhotoList[i].id.replace('File',''),
            giftCompanyLogo: giftCompanyLogoList[i].id.replace('File',''),
            giftPrice: giftPriceList.eq(i).val(),
            giftSponsorPrice: giftSponsorPriceList.eq(i).val(),
            giftNote: giftNoteList.eq(i).val(),
        }

        giftListArr.push(jsonObj);
    }

    let exhibitorArr = {
        seq: exhibitorSeq,
        giftList: giftListArr
    };

    return JSON.stringify(exhibitorArr);
}