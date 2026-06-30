/***
 * mng/exhibitor/application/utility.js
 * 참가신청서 관리 > 유틸리티 신청
 * */
var transferYear = '2027';
$(function(){

    // 유틸리티 계산
    $('#kt_utility_info input.num_sum').on('keyup', function () {
        let cnt = $("#kt_utility_info input.num_sum").length;
        //console.log(cnt);

        for (let i = 1; i < cnt; i++) {
            let sum = parseInt($(this).val() || 0);
            sum++
            //console.log(sum);
        }

        //콤마찍기
        function comma(str) {
            str = String(str);
            return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
        }

        let item1 = parseInt($("#utility_jugan_cnt").val() || 0);
        let item2 = parseInt($("#utility_day_cnt").val() || 0);
        let item3 = parseInt($("#utility_work_cnt").val() || 0);
        let item4 = parseInt($("#utility_compressed_air_cnt").val() || 0);
        let item5 = parseInt($("#utility_water_basic_cnt").val() || 0);
        let item6 = parseInt($("#utility_internet_cnt").val() || 0);
        let item7 = parseInt($("#utility_pytex_new_cnt").val() || 0);
        let item8 = parseInt($("#utility_pytex_re_cnt").val() || 0);
        let item9 = parseInt($("#utility_barcode_cnt").val() || 0);

        var price1 = item1 * 80000; //주간 단상 220v
        var price2 = item2 * 100000; //24시간용 220v
        var price3 = item3 * 80000; //작업전기
        var price4 = item4 * 200000; //압축공기 기본형
        var price5 = item5 * 200000; //급배수 기본형
        var price6 = item6 * 200000; //인터넷
        var price7 = item7 * 80000; //파이텍스 (신품)
        var price8 = item8 * 50000; //파이텍스 (재사용품)
        var price9 = item9 * 200000; //참관객/바이어 바코드 리더기
        var total = price1 + price2 + price3 + price4 + price5 + price6 + price7 + price8 + price9;

        //console.log(sum);
        $("#utility_jugan_fee").val("￦ " + comma(price1));
        $("#utility_day_fee").val("￦ " + comma(price2));
        $("#utility_work_fee").val("￦ " + comma(price3));
        $("#utility_compressed_air_fee").val("￦ " + comma(price4));
        $("#utility_water_basic_fee").val("￦ " + comma(price5));
        $("#utility_internet_fee").val("￦ " + comma(price6));
        $("#utility_pytex_new_fee").val("￦ " + comma(price7));
        $("#utility_pytex_re_fee").val("￦ " + comma(price8));
        $("#utility_barcode_fee").val("￦ " + comma(price9));
        $("#form_add_total").val("￦ " + comma(total));

        $(this).val($(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    });

    let myModalEl = document.getElementById('kt_modal_invoice_form');

    if(myModalEl){

        let myModal = new bootstrap.Modal('#kt_modal_invoice_form', {
            focus: true
        });

        myModalEl.addEventListener('hidden.bs.modal', event => {
            // input init
            $('#detailForm').removeAttr('src');
        })

        $('#previewBtn').on('click', function () {
            let seq = $('input[type=checkbox][name=invoiceSeq]:checked').val();
            if(nvl(seq,'') !== ''){
                $('#detailForm').removeAttr('src');
                $('#detailForm').attr('src','/mng/exhibitorNewNew/application/utility/invoice/detail.do?seq=' + seq);
                myModal.show();
            }else{
                showMessage('', 'info', '[ 인보이스 ]', '인보이스 내역을 선택해 주세요.', '');
            }
        })
    }//myModalEl
});

function f_application_utility_new_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});
    $('#approvalYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#cancelYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#utilityGbn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_utility_new_search();
}

function f_application_utility_new_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_utility_new_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nvl(searchText,'') === ""){
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

    let resData = ajaxConnect('/mng/exhibitorNewNew/application/utility/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_utility_new_new_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_utility_new_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let lang = $('#lang option:selected').val(); //참가신청언어
    let approvalYn = $('#approvalYn option:selected').val(); //승인여부
    let cancelYn = $('#cancelYn option:selected').val(); //참가취소여부
    let utilityGbn = $('#utilityGbn option:selected').val(); //유틸리티 항목

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        lang: lang,
        approvalStatus: approvalYn,
        cancelYn: cancelYn,
        utilityGbn: utilityGbn,
        transferYear: transferYear,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitorNewNew/application/utility/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_utility_new_new_table tbody td');
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

function f_application_utility_new_modify_init_set(id){
    window.location.href = '/mng/exhibitorNewNew/application/utility/detail.do?seq=' + id;
}

function f_application_utility_new_save(seq){

    Swal.fire({
        icon: 'info',
        title: '[ 유틸리티 정보 ]',
        html: '<span style="font-size: 1.2em;">입력된 정보를 저장하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '변경내용저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* form data setting */
            let data = f_utility_form_data_setting(seq);

            $.ajax({
                url: '/mng/exhibitorNewNew/application/utility/update.do',
                method: 'POST',
                async: false,
                data: data,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        Swal.fire({
                            icon: 'info',
                            title: '[ 유틸리티 정보 ]',
                            html: '<span style="font-size: 1.2em;">유틸리티 신청 정보가 저장되었습니다.</span>',
                            allowOutsideClick: false,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_application_utility_new_modify_init_set(seq); // 재조회
                            }
                        });

                    } else {
                        showMessage('', 'error', '에러 발생', '유틸리티 신청 정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })

        }//isConfirmed
    })//then

}

function f_utility_form_data_setting(exhibitorSeq){

    // 유틸리티 신청정보 - 주간단상 - 수량
    let utility_jugan_cnt = parseInt($('#utility_jugan_cnt').val());

    // 유틸리티 신청정보 - 주간단상 - 금액
    let utility_jugan_fee = $('#utility_jugan_fee').val();

    // 유틸리티 신청정보 - 24시간용 - 수량
    let utility_day_cnt = parseInt($('#utility_day_cnt').val());

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_day_fee = $('#utility_day_fee').val();

    // 작업전기 - 수량
    let utility_work_cnt = parseInt($('#utility_work_cnt').val());

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_work_fee = $('#utility_work_fee').val();

    // 유틸리티 신청정보 - 압축공기 - 수량
    let utility_compressed_air_cnt = parseInt($('#utility_compressed_air_cnt').val());

    // 유틸리티 신청정보 - 압축공기 - 금액
    let utility_compressed_air_fee = $('#utility_compressed_air_fee').val();

    // 유틸리티 신청정보 - 급배수 - 수량
    let utility_water_basic_cnt = parseInt($('#utility_water_basic_cnt').val());

    // 유틸리티 신청정보 - 급배수 - 금액
    let utility_water_basic_fee = $('#utility_water_basic_fee').val();

    // 유틸리티 신청정보 - 인터넷 - 수량
    let utility_internet_cnt = parseInt($('#utility_internet_cnt').val());

    // 유틸리티 신청정보 - 인터넷 - 금액
    let utility_internet_fee = $('#utility_internet_fee').val();

    // 유틸리티 신청정보 - 파이텍스 (신품) - 수량
    let utility_pytex_new_cnt = parseInt($('#utility_pytex_new_cnt').val());

    // 유틸리티 신청정보 - 파이텍스 (신품) - 금액
    let utility_pytex_new_fee = $('#utility_pytex_new_fee').val();

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 수량
    let utility_pytex_re_cnt = parseInt($('#utility_pytex_re_cnt').val());

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 금액
    let utility_pytex_re_fee = $('#utility_pytex_re_fee').val();

    // 유틸리티 신청정보 - 바코드 리더기 - 수량
    let utility_barcode_cnt = parseInt($('#utility_barcode_cnt').val());

    // 유틸리티 신청정보 - 바코드 리더기 - 금액
    let utility_barcode_fee = $('#utility_barcode_fee').val();

    let utilityPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let jsonObj = {
        seq: exhibitorSeq,
        utilityJuganCnt: utility_jugan_cnt,
        utilityJuganFee: wonToInt(utility_jugan_fee),
        utilityDayCnt: utility_day_cnt,
        utilityDayFee: wonToInt(utility_day_fee),
        utilityWorkCnt: utility_work_cnt,
        utilityWorkFee: wonToInt(utility_work_fee),
        utilityCompressedAirCnt: utility_compressed_air_cnt,
        utilityCompressedAirFee: wonToInt(utility_compressed_air_fee),
        utilityWaterBasicCnt: utility_water_basic_cnt,
        utilityWaterBasicFee: wonToInt(utility_water_basic_fee),
        utilityInternetCnt: utility_internet_cnt,
        utilityInternetFee: wonToInt(utility_internet_fee),
        utilityPytexNewCnt: utility_pytex_new_cnt,
        utilityPytexNewFee: wonToInt(utility_pytex_new_fee),
        utilityPytexReCnt: utility_pytex_re_cnt,
        utilityPytexReFee: wonToInt(utility_pytex_re_fee),
        utilityBarcodeCnt: utility_barcode_cnt,
        utilityBarcodeFee: wonToInt(utility_barcode_fee),
        utilityPrcSum: utilityPrcSum
    }

    return JSON.stringify(jsonObj);
}

function wonToInt(won){
    return won.replace(/\s/g, '').replace(/\￦/g, '').replace(/\,/g, '');
}

function f_invoice_checkbox_sel(el){
    let checkboxes = document.getElementsByName(el.name);
    checkboxes.forEach((item) => {
        if (item !== el) {
            item.checked = false;
        }
    });
}

function f_invoice_utility_create(exSeq, companyNameKo){
    Swal.fire({
        icon: 'info',
        title: '[ 인보이스 ]',
        html: '<span style="font-size: 1.2em;">해당 업체의 인보이스를 생성하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '생성',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            KTApp.showPageLoading();

            let data = {
                exSeq: exSeq
            }
            $.ajax({
                url: '/mng/exhibitorNewNew/application/utility/invoice/insert.do',
                method: 'POST',
                async: false,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        let seq = data.customValue; // invoice Seq

                        companyNameKo = companyNameKo.replace(/[^a-zA-Z0-9ㄱ-힣]/g, '');

                        let param = {
                            seq: seq,
                            companyNameKo: companyNameKo
                        }
                        createAndUploadPdfFromIframe(param, 'detailForm', 'exhibitor/invoice/utility/' + exSeq)
                            .then(r =>

                                KTApp.hidePageLoading(),

                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    html: '<span style="font-size: 1.2em;">인보이스가 생성되었습니다.</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {

                                        f_application_utility_new_modify_init_set(exSeq);

                                    }
                                })

                            );
                    }else{
                        KTApp.hidePageLoading();
                        showMessage('', 'error', '에러 발생', '인보이스 생성을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                }
            })
        }
    });
}

function f_invoice_utility_delete(exSeq){
    let sendStatus = $('input[type=checkbox][name=invoiceSeq]:checked').parents('td').siblings('.td_sendStatus').find('span').text();
    if(sendStatus === '미발송'){
        let seq = $('input[type=checkbox][name=invoiceSeq]:checked').val();
        if(nvl(seq,'') !== ''){
            Swal.fire({
                icon: 'info',
                title: '[ 인보이스 ]',
                html: '<span style="font-size: 1.2em;">해당 인보이스 정보를 삭제하시겠습니까?</span>',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#d33',
                confirmButtonText: '삭제',
                cancelButtonColor: '#A1A5B7',
                cancelButtonText: '취소'
            }).then((result) => {
                if (result.isConfirmed) {

                    let data = {
                        seq: seq,
                        delYn : 'Y'
                    }
                    $.ajax({
                        url: '/mng/exhibitorNewNew/application/utility/invoice/delete.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {

                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    html: '<span style="font-size: 1.2em;">인보이스가 삭제되었습니다.</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_application_utility_new_modify_init_set(exSeq); // 재조회
                                    }
                                });

                            }else{
                                showMessage('', 'error', '에러 발생', '인보이스 삭제를 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                            }
                        }
                    })
                }
            });
        }else{
            showMessage('', 'info', '[ 인보이스 ]', '인보이스 내역을 선택해 주세요.', '');
        }
    }else{
        showMessage('', 'info', '[ 인보이스 ]', '인보이스를 이미 발송하신 경우</br>내역 삭제가 불가합니다.', '');
    }
}

function f_invoice_utility_send(exSeq){
    let invoiceSelect_el = $('input[type=checkbox][name=invoiceSeq]:checked');
    let checkLength = invoiceSelect_el.length;

    if(checkLength > 0){
        let seq = invoiceSelect_el.val();
        let sendStatus = invoiceSelect_el.parents('td').siblings('.td_sendStatus').find('span').text().toString();
        let swal_html = '';
        if(sendStatus === '미발송'){
            swal_html = '<span style="font-size: 1.2em;">해당 인보이스를 발송하시겠습니까?</span>';
        }else{
            // 열람, 미열람
            swal_html = '<span style="font-size: 1.2em;">이미 발송한 인보이스입니다.</br>다시 발송하시겠습니까?</span>';
        }

        Swal.fire({
            icon: 'info',
            title: '[ 인보이스 ]',
            html: swal_html,
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '발송',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let jsonStr = { seq: exSeq };
                let result = ajaxConnect('/mng/getExhibitorNewInfo.do', 'post', jsonStr);
                if(nvl(result,'') !== ''){
                    let companyNameKo = result.companyNameKo;
                    let email = result.email;

                    let filePath = $('input[type=checkbox][name=invoiceSeq]:checked').siblings('input[type=hidden][name=filePath]').val();
                    let folderPath_r = filePath.replace('/usr/local/tomcat/webapps/','');
                    let folderPath_s = folderPath_r.substring(0, folderPath_r.lastIndexOf('/') + 1);
                    let fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length);
                    let uploadFileArr = [{
                        name: encodeURI(fileName)
                    }];
                    let jsonObj = {
                        subject: '[KIBS 2027] ' + companyNameKo + ' 유틸리티 참가비용 인보이스 발송', //제목
                        body: "", //본문
                        template: "164", //템플릿 번호
                        receiver: [{email: email, note1: encodeURI('https://kibs.com/mng/exhibitorNewNew/application/invoice/mail/open/update.do?gbn=IU&seq=' + seq)}],
                        gbn: 'UTILITY',
                        folderPath: encodeURI(folderPath_s),
                        fileUrl: uploadFileArr
                    }
                    let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                    //console.log(i , resData);
                    let sendStatus = '';
                    let sendResult = '';
                    if (resData.resultCode === "0") {
                        sendStatus = '미열람';
                        sendResult = '발송성공';
                    }else{
                        sendStatus = '미발송';
                        sendResult = '발송실패';
                    }

                    let sendResultMsg = resData.resultMessage;

                    let sendObj = {
                        seq: seq,
                        sendStatus: sendStatus,
                        sendResult: sendResult,
                        sendResultMsg: sendResultMsg
                    }

                    $.ajax({
                        url: '/mng/exhibitorNewNew/application/utility/invoice/mail/result/update.do',
                        method: 'POST',
                        async: false,
                        data: JSON.stringify(sendObj),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (data) {
                            if (data.resultCode === "0") {

                                Swal.fire({
                                    icon: 'info',
                                    title: '[ 인보이스 ]',
                                    html: '<span style="font-size: 1.2em;">인보이스가 발송되었습니다.</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        f_application_utility_new_modify_init_set(exSeq); // 재조회
                                    }
                                });
                            }

                        }
                    })

                }else{
                    showMessage('', 'info', '[ 인보이스 ]', '참가업체 데이터를 찾지 못했습니다. 관리자에게 문의해주세요.', '');
                }
            }
        })
    }else{
        showMessage('', 'info', '[ 인보이스 ]', '인보이스 내역을 선택해 주세요.', '');
    }
}

function f_invoice_utility_print(){
    window.frames["detailForm"].focus();
    window.frames["detailForm"].print();
}

function f_invoice_utility_download(){
    const iframe = document.getElementById('detailForm');

    // iframe이 로드되지 않았거나 내용을 가져올 수 없는 경우
    if (!iframe || !iframe.contentWindow || !iframe.contentWindow.document) {
        alert('Iframe이 로드되지 않았거나 접근할 수 없습니다.');
        return;
    }

    const iframeDoc = iframe.contentWindow.document;
    const filePath_val = iframeDoc.getElementById('filePath');
    let filePath = filePath_val.value.toString();
    // /usr/local/tomcat/webapps/upload/exhibitor/invoice/booth/EN0000004/20250704_2026_보트쇼_인보이스_회사명국_전시부스.pdf
    let folderPath_r = filePath.replace('/usr/local/tomcat/webapps/upload/','');
    let folderPath_s = folderPath_r.substring(0, folderPath_r.lastIndexOf('/'));
    let fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length);

    let form = document.createElement('form');
    form.setAttribute('method', 'POST'); //POST 메서드 적용
    form.setAttribute('action', '/file/download.do');

    let hiddenField_path = document.createElement('input');
    hiddenField_path.setAttribute('type', 'hidden'); //값 입력
    hiddenField_path.setAttribute('name', 'path');
    hiddenField_path.setAttribute('value', folderPath_s);
    form.appendChild(hiddenField_path);

    let hiddenField_fileName = document.createElement('input');
    hiddenField_fileName.setAttribute('type', 'hidden'); //값 입력
    hiddenField_fileName.setAttribute('name', 'fileName');
    hiddenField_fileName.setAttribute('value', fileName);
    form.appendChild(hiddenField_fileName);

    document.body.appendChild(form);
    form.submit();
}

// PDF 생성 및 서버 업로드 함수
async function createAndUploadPdfFromIframe(param , iframeId, uploadPath) {

    let seq = param.seq;
    let companyNameKo = param.companyNameKo;

    let fileName = getCurrentDate_notime() + '_2027_보트쇼_인보이스_' + companyNameKo + '_유틸리티';
    let url = '/mng/exhibitorNewNew/application/utility/invoice/detail.do?seq=' + seq;

    // HTML 문자열 가져오기
    const response = await fetch(url, { mode: "cors" });
    const htmlText = await response.text();

    // 가상 DOM에 삽입
    const virtualFrame = document.createElement("iframe");
    virtualFrame.style.position = "absolute";
    virtualFrame.style.left = "-9999px";
    // iframe의 크기를 A4 비율과 유사하게 설정하여 html2canvas 렌더링에 도움
    virtualFrame.style.width = '210mm';
    virtualFrame.style.height = '297mm';
    document.body.appendChild(virtualFrame);
    const doc = virtualFrame.contentDocument || virtualFrame.contentWindow.document;
    doc.open();
    // UTF-8 메타 태그를 추가하여 한글 깨짐 방지
    doc.write('<meta charset="utf-8">' + htmlText);
    doc.close();

    // 렌더 완료 대기
    await new Promise(resolve => {
        virtualFrame.onload = () => {
            // iframe 내부의 모든 이미지가 로드될 때까지 기다림
            const images = doc.querySelectorAll('img');
            const imagePromises = Array.from(images).map(img => {
                if (img.complete) return Promise.resolve();
                return new Promise(imgResolve => {
                    img.onload = imgResolve;
                    img.onerror = imgResolve; // 이미지 로드 실패 시에도 진행
                });
            });
            Promise.all(imagePromises).then(resolve);
        };
        setTimeout(resolve, 3000); // 충분한 대기 시간 (fallback)
    });

    try {
        // html2canvas로 HTML을 캔버스 이미지로 변환
        const canvas = await html2canvas(doc.body, {
            scale: 1, // 해상도 조절 (높을수록 선명하지만 파일 크기 증가)
            useCORS: true,
            logging: true // 디버깅을 위한 로그 활성화
        });

        // 캔버스 유효성 검사
        if (!canvas || canvas.width === 0 || canvas.height === 0) {
            console.error('오류: html2canvas가 유효한 캔버스를 생성하지 못했습니다. iframe 내용이 비어있거나 접근 불가능할 수 있습니다.');
            alert('PDF를 생성할 수 없습니다: iframe 내용이 없거나 접근할 수 없습니다.');
            // iframe 제거
            document.body.removeChild(virtualFrame);
            return;
        }

        const imgData = canvas.toDataURL('image/png', 0.8); // PNG 이미지 데이터 URL

        // imgData 유효성 검사
        if (!imgData || imgData.length < 100) {
            console.error('오류: 이미지 데이터가 유효하지 않습니다. 캡처된 내용이 없거나 변환에 실패했습니다.');
            alert('PDF를 생성할 수 없습니다: 이미지 데이터가 유효하지 않습니다.');
            // iframe 제거
            document.body.removeChild(virtualFrame);
            return;
        }
        console.log('이미지 데이터 생성 완료. 길이:', imgData.length);

        // 2. jspdf를 사용하여 PDF 생성
        const { jsPDF } = window.jspdf || {};
        if (!jsPDF) {
            console.error('오류: jsPDF 라이브러리가 로드되지 않았거나 window.jspdf 객체를 찾을 수 없습니다.');
            alert('PDF 생성 라이브러리가 로드되지 않았습니다. CDN 링크를 확인하세요.');
            // iframe 제거
            document.body.removeChild(virtualFrame);
            return;
        }

        const pdf = new jsPDF('p', 'mm', 'a4'); // 'p': Portrait, 'mm': Millimeters, 'a4': A4 사이즈

        const a4Width = 210; // A4 너비 (mm)
        const a4Height = 297; // A4 높이 (mm)

        // --- 여백 설정 ---
        // 좌우 여백을 균등하게 만들기 위해 이제는 margin을 직접 X 계산에 사용하지 않습니다.
        const margin = 5; // 상하 여백 (mm) - 상단 여백은 Y 시작점에 사용

        // 이미지의 최종 크기를 A4 페이지에 맞게 계산
        const contentWidth = a4Width - (margin * 2); // 좌우 여백을 제외한 최대 너비
        const contentHeight = a4Height - (margin * 2); // 상하 여백을 제외한 최대 높이

        const canvasAspectRatio = canvas.width / canvas.height;
        const pageAspectRatio = contentWidth / contentHeight;

        let finalImgWidth;
        let finalImgHeight;

        if (canvasAspectRatio > pageAspectRatio) {
            finalImgWidth = contentWidth;
            finalImgHeight = contentWidth / canvasAspectRatio;
        } else {
            finalImgHeight = contentHeight;
            finalImgWidth = contentHeight * canvasAspectRatio;
        }

        // --- 이미지 X축 시작점 계산 (가운데 정렬) ---
        const finalImgX = (a4Width - finalImgWidth) / 2;
        const finalImgY = (a4Height - finalImgHeight) / 2; // 세로도 가운데 정렬하고 싶다면 사용

        // 계산된 X 좌표와 상단 여백(margin)을 Y 좌표로 사용하여 이미지 추가
        pdf.addImage(imgData, 'PNG', finalImgX, margin, finalImgWidth, finalImgHeight);
        // 만약 세로로도 완전히 중앙 정렬하고 싶다면:
        pdf.addImage(imgData, 'PNG', finalImgX, finalImgY, finalImgWidth, finalImgHeight);

        // 3. PDF 파일을 Blob 형태로 얻기
        const pdfBlob = pdf.output('blob'); // Blob 형태로 출력

        // 4. 서버로 PDF 파일 전송
        const formData = new FormData();
        formData.append('uploadFile', pdfBlob, fileName + '.pdf'); // 'uploadFile' 서버에서 받을 필드 이름

        console.log('PDF 파일을 서버에 업로드 중...');

        const response = await fetch('/file/upload.do?gbn=' + uploadPath, {
            method: 'POST',
            body: formData,
            // 'Content-Type'은 FormData 사용 시 자동으로 설정되므로 명시할 필요 없음
        });

        if (response.ok) {
            const result = await response.json();
            console.log('PDF 업로드 성공:', result);

            let filePath = result.uploadPath + '/' + result.fileName;
            let data = {
                seq: seq,
                filePath : filePath
            }
            $.ajax({
                url: '/mng/exhibitorNewNew/application/utility/invoice/filePath/update.do',
                method: 'POST',
                async: false,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {
                        console.log('Invoice File Path Update 성공');
                    }
                }
            })

            //alert('PDF가 성공적으로 생성되고 서버에 업로드되었습니다.');
        } else {
            const errorText = await response.text();
            console.error('PDF 업로드 실패:', response.status, errorText);
            alert(`PDF 업로드 실패: ${response.status} - ${errorText}`);
        }
    } catch (error) {
        console.error('PDF 생성 또는 업로드 중 예측 불가능한 오류 발생:', error);
        alert('PDF 생성 또는 업로드 중 심각한 오류가 발생했습니다. 콘솔을 확인하세요.');
    } finally {
        // 작업 완료 후 iframe 제거 (오류 발생 시에도 제거되도록 finally 블록에 추가)
        if (virtualFrame && document.body.contains(virtualFrame)) {
            document.body.removeChild(virtualFrame);
        }
    }

}

function f_exhibitor_utility_excel_export(){
    Swal.fire({
        title: '[ 전체 유틸리티 정보 상세 다운로드 ]',
        html: '전체 유틸리티 정보 상세를 다운로드하시겠습니까 ?',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '다운로드',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 로딩페이지 */
            loadingBarShow();

            let form = document.createElement('form');
            form.setAttribute('action','/mng/exhibitor/utility/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', '유틸리티_정보_' + getCurrentDate() + '.xlsx');

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