/***
 * mng/participant/company.js
 * 참가업체 > 참가업체관리
 * */

$(function(){

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

});

function excelUpload(){

    /* 로딩페이지 */
    loadingBarShow();

    /* 테이블 데이터 지우기 */
    let dataTbl = $('#kt_participant_company_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let input = document.querySelector('#excel_file');
    let reader = new FileReader();
    reader.onload = function() {
        let fdata = reader.result;
        let read_buffer = XLSX.read(fdata, {type : 'binary'});

        const sheetName = read_buffer.SheetNames[1];          // @details 두번째 시트 정보 추출
        const dataSheet = read_buffer.Sheets[sheetName];       // @details 시트의 제목 추출

        let rowdata =XLSX.utils.sheet_to_json(dataSheet,{defval:''}); // Excel 입력 데이터
        //console.log('excel all : ' + JSON.stringify(rowdata));
        //console.log(rowdata[100]==null); //true

        for(let i=2; i<(rowdata.length-1); i++){
            let dataArr = [];

            JSON.parse(JSON.stringify(rowdata[i]), (key, value) => {
                dataArr.push(value);
            });

            let dataRow = makeJsonFormat('import', dataArr);
            dataTbl.row.add(dataRow).draw();
        }

        /* 조회 카운트 입력 */
        document.getElementById('search_cnt').innerText = dataTbl.data().length;
        /* modal close fn */
        f_modal_close('kt_modal_excel_upload');

        let jb = $('#kt_participant_company_table tbody td');
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

  /* excel upload save btn pop */
  document.getElementById('excel_upload_save_btn').classList.remove('d-none');
}

function f_excel_upload_save(){

    /* 로딩페이지 */
    loadingBarShow();

    //console.log('업로드 내용 저장 버튼 클릭');
    let tableData = $('#kt_participant_company_table').DataTable().data();
    let len = tableData.length;
    //$('#kt_participant_company_table').DataTable().data().rows(18).data();

    for(let i=0; i<len; i++){
        let rowData = tableData.rows(i).data();

        let jsonObj = makeJsonFormat("export",rowData);
        let resData = ajaxConnect('/mng/participant/company/list/save.do','post', jsonObj);
        //console.log(i , resData);
    }

    /* 업로드 내용 저장 버튼 display:none */
    document.getElementById('excel_upload_save_btn').classList.add('d-none');

    /* 재조회 */
    f_exhibitor_comp_search();
}

function makeJsonFormat(gbn, data){
  let parseData;
  if(gbn==="export"){
    let dataArr = [];
    JSON.parse(JSON.stringify(JSON.parse(JSON.stringify(data))[0]), (key, value) => {
      dataArr.push(value);
    });

    parseData = JSON.parse(JSON.stringify(dataArr));
  }else{
    parseData = JSON.parse(JSON.stringify(data));
  }

  let returnJsonObj = {
      rownum: replaceText(parseData[0]),	//순번
      chargePersonRep1: replaceText(parseData[1]),	//1차담당자
      chargePersonRep2: replaceText(parseData[2]),	//2차담당자
      booth24: replaceText(parseData[3]),	//2024 예상 부스
      tmReference24: replaceText(parseData[4]),	//2024 TM
      tmReference23: replaceText(parseData[5]),	//2023 TM 참고
      partWantYn: replaceText(parseData[6]),	//참가의향
      boothCnt23: replaceText(parseData[7]),	//23 부스수
      prePartYear: replaceText(parseData[8]),	//출처/참가년도
      companyGbn: replaceText(parseData[9]),	//기업유형
      companyRegion: replaceText(parseData[10]),	//지역
      companyNameKo: replaceText(parseData[11]),	//회사명
      companyHomepage: replaceText(parseData[12]),	//홈페이지
      companyAddress: replaceText(parseData[13]),	//주소
      companyCeo: replaceText(parseData[14]),	//대표자명
      fieldPart: replaceText(parseData[15]),	//참가분야
      classify: replaceText(parseData[16]),	//분류
      displayItem: replaceText(parseData[17]),	//전시품목
      companyFax: replaceText(parseData[18]),	//팩스
      chargePersonName1: replaceText(parseData[19]),	//담당자명1
      chargePersonPosition1: replaceText(parseData[20]),	//직위1
      chargePersonTel1: replaceText(parseData[21]),	//전화번호1
      chargePersonPhone1: replaceText(parseData[22]),	//휴대전화1
      chargePersonEmail1: replaceText(parseData[23]),	//이메일1
      chargePersonName2: replaceText(parseData[24]),	//담당자명2
      chargePersonPosition2: replaceText(parseData[25]),	//직위2
      chargePersonTel2: replaceText(parseData[26]),	//전화번호2
      chargePersonPhone2: replaceText(parseData[27]),	//휴대전화2
      chargePersonEmail2: replaceText(parseData[28]),	//이메일2
      chargePersonName3: replaceText(parseData[29]),	//담당자명3
      chargePersonPosition3: replaceText(parseData[30]),	//직위3
      chargePersonTel3: replaceText(parseData[31]),	//전화번호3
      chargePersonPhone3: replaceText(parseData[32]),	//휴대전화3
      chargePersonEmail3: replaceText(parseData[33]),	//이메일3
      companyNote: replaceText(parseData[34]),	//업체비고
      businessStatusLarge1: replaceText(parseData[35]),	//업태1-대분류
      businessStatusSmall1: replaceText(parseData[36]),	//업태1-중분류
      businessStatusLarge2: replaceText(parseData[37]),	//업태2-대분류
      businessStatusSmall2: replaceText(parseData[38]),	//업태2-중분류
      businessStatusLarge3: replaceText(parseData[39]),	//업태3-대분류
      businessStatusSmall3: replaceText(parseData[40]),	//업태3-중분류
      businessType: replaceText(parseData[41]),	//영위사업
      collectionType: replaceText(parseData[42]),	//수집유형
      intentionStep: replaceText(parseData[43]),	//의향단계
      contactProgressYn: replaceText(parseData[44]),	//컨택진행여부
      contactDttm: replaceText(parseData[45]),	//컨택일자
      contactResult: replaceText(parseData[46]),	//컨택결과
      contactTargetName: replaceText(parseData[47]),	//컨택대상자성명
      contactTargetPhone: replaceText(parseData[48]),	//컨택대상자연락처
      contactResultDetail: replaceText(parseData[49]),	//컨택결과세부내용
      transferYear: transferYear,
      lang: 'KO'
  }

  return returnJsonObj;
}

function f_exhibitor_comp_search(){

  /* 로딩페이지 */
  loadingBarShow();

  /* DataTable Data Clear */
  let dataTbl = $('#kt_participant_company_table').DataTable();
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

  let resData = ajaxConnect('/mng/participant/company/list/selectList.do', 'post', jsonObj);
  dataTbl.rows.add(resData).draw();

  /* 조회 카운트 입력 */
  document.getElementById('search_cnt').innerText = resData.length;

  /* DataTable Column tooltip Set */
  let jb = $('#kt_participant_company_table tbody td');
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

function f_exhibitor_comp_init_set(seq){
    let form = document.createElement('form');
    form.setAttribute('method', 'post'); //POST 메서드 적용
    form.setAttribute('action', '/mng/participant/company/list/detail.do');
    let hiddenField = document.createElement('input');
    hiddenField.setAttribute('type', 'hidden'); //값 입력
    hiddenField.setAttribute('name', 'seq');
    hiddenField.setAttribute('value', seq);
    form.appendChild(hiddenField);
    document.body.appendChild(form);
    form.submit();
}

function f_exhibitor_comp_modify(){
    //console.log('수정버튼');
    let selectRowData = $('#kt_participant_company_table').DataTable().rows('.selected').data()[0];
    if(nullToEmpty(selectRowData) !== ""){
        //console.log(selectRowData);
        let form = document.createElement('form');
        form.setAttribute('method', 'post'); //POST 메서드 적용
        form.setAttribute('action', '/mng/participant/company/list/detail.do');
        for ( let key in selectRowData) {	// key, value로 이루어진 객체 params
            let hiddenField = document.createElement('input');
            hiddenField.setAttribute('type', 'hidden'); //값 입력
            hiddenField.setAttribute('name', key);
            hiddenField.setAttribute('value', selectRowData[key]);
            form.appendChild(hiddenField);
        }
        document.body.appendChild(form);
        form.submit();
    }
}

function f_exhibitor_comp_remove(){
    //console.log('삭제버튼');
    let selectRowData = $('#kt_participant_company_table').DataTable().rows('.selected').data()[0];
    if(nullToEmpty(selectRowData) !== ""){
        Swal.fire({
            title: '선택한 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let resData = ajaxConnect('/mng/participant/company/list/deleteList.do', 'post', selectRowData);

                if (resData.resultCode === "0") {
                    Swal.fire({
                        title: '참가 업체 정보 삭제',
                        html: '참가 업체 정보가 삭제되었습니다.',
                        icon: 'info',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            f_exhibitor_comp_search(); // 삭제 성공 후 재조회 수행
                        }
                    });
                } else {
                    showMessage('', 'error', '에러 발생', '참가 업체 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_participant_company_save(seq){
    //console.log('변경내용저장 클릭');

    let companyNameKo = $('#companyNameKo').val();
    if(nvl(companyNameKo,'') === ''){
        showMessage('', 'info', '[업체 정보]', '회사명을 필수로 입력해 주세요.', '');
        return false;
    }

    let companyCeo = $('#companyCeo').val();
    if(nvl(companyCeo,'') === ''){
        showMessage('', 'info', '[업체 정보]', '대표자명을 필수로 입력해 주세요.', '');
        return false;
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
            let serialData = JSON.parse(JSON.stringify($('#participantCompanyModifyForm').serializeObject()));

            serialData.lang = 'KO';
            serialData.transferYear = transferYear;

            let data = JSON.stringify(serialData);

            $.ajax({
                url: '/mng/participant/company/list/modifySave.do',
                method: 'POST',
                async: false,
                data: data,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {
                        Swal.fire({
                            title: '참가 업체 정보 변경',
                            html: '참가 업체 정보가 변경되었습니다.',
                            icon: 'info',
                            allowOutsideClick: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_exhibitor_comp_init_set(seq); // 재조회
                            }
                        });
                    } else {
                        showMessage('', 'error', '에러 발생', '참가 업체 정보 변경을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })
        }
    })
}

function f_exhibitor_comp_excel_export(tableId , name){
    let dataTbl = $('#' + tableId).DataTable();
    let buttons = new $.fn.dataTable.Buttons(dataTbl, {
        buttons:[
            {
                extend: 'excelHtml5',
                title: name + '_목록_excel_' + getCurrentDate(),
                autoFilter: true,
                text: 'Export as Excel',
                className: 'btn btn-success btn-active-light-success',
                customize: function( xlsx ) {
                    let sheet = xlsx.xl.worksheets['sheet1.xml'];
                    //$('row:first c', sheet).attr( 's', '42' );

                    $('row c', sheet).attr( 's', '0' );

                    // blue
                    $('c[r=A2]', sheet).attr( 's', '47' ); //순번
                    $('c[r=F2]', sheet).attr( 's', '47' ); //2023 TM 참고
                    $('c[r=J2]', sheet).attr( 's', '47' ); //기업유형
                    $('c[r=K2]', sheet).attr( 's', '47' ); //지역
                    $('c[r=L2]', sheet).attr( 's', '47' ); //회사명
                    $('c[r=M2]', sheet).attr( 's', '47' ); //홈페이지
                    $('c[r=N2]', sheet).attr( 's', '47' ); //주소
                    $('c[r=O2]', sheet).attr( 's', '47' ); //대표자명

                    // green
                    $('c[r=B2]', sheet).attr( 's', '42' ); //1차담당자
                    $('c[r=C2]', sheet).attr( 's', '42' ); //2차담당자
                    $('c[r=D2]', sheet).attr( 's', '42' ); //24 예상부스
                    $('c[r=E2]', sheet).attr( 's', '42' ); //2024 TM
                    $('c[r=G2]', sheet).attr( 's', '42' ); //참가의향
                    $('c[r=H2]', sheet).attr( 's', '42' ); //23 부스수
                    $('c[r=I2]', sheet).attr( 's', '42' ); //출처/참가년도

                    // red
                    $('c[r=P2]', sheet).attr( 's', '37' ); //참가분야
                    $('c[r=Q2]', sheet).attr( 's', '37' ); //분류
                    $('c[r=R2]', sheet).attr( 's', '37' ); //전시품목
                    $('c[r=S2]', sheet).attr( 's', '37' ); //팩스

                    // gray
                    $('c[r=T2]', sheet).attr( 's', '32' ); //담당자1
                    $('c[r=U2]', sheet).attr( 's', '32' ); //직위1
                    $('c[r=V2]', sheet).attr( 's', '32' ); //전화번호1
                    $('c[r=W2]', sheet).attr( 's', '32' ); //휴대전화1
                    $('c[r=X2]', sheet).attr( 's', '32' ); //이메일1
                    $('c[r=Y2]', sheet).attr( 's', '32' ); //담당자2
                    $('c[r=Z2]', sheet).attr( 's', '32' ); //직위2
                    $('c[r=AA2]', sheet).attr( 's', '32' ); //전화번호2
                    $('c[r=AB2]', sheet).attr( 's', '32' ); //휴대전화2
                    $('c[r=AC2]', sheet).attr( 's', '32' ); //이메일2
                    $('c[r=AD2]', sheet).attr( 's', '32' ); //담당자3
                    $('c[r=AE2]', sheet).attr( 's', '32' ); //직위3
                    $('c[r=AF2]', sheet).attr( 's', '32' ); //전화번호3
                    $('c[r=AG2]', sheet).attr( 's', '32' ); //휴대전화3
                    $('c[r=AH2]', sheet).attr( 's', '32' ); //이메일3
                    $('c[r=AI2]', sheet).attr( 's', '32' ); //비고
                    $('c[r=AJ2]', sheet).attr( 's', '32' ); //업태1-대분류
                    $('c[r=AK2]', sheet).attr( 's', '32' ); //업태1-중분류
                    $('c[r=AL2]', sheet).attr( 's', '32' ); //업태2-대분류
                    $('c[r=AM2]', sheet).attr( 's', '32' ); //업태2-중분류
                    $('c[r=AN2]', sheet).attr( 's', '32' ); //업태3-대분류
                    $('c[r=AO2]', sheet).attr( 's', '32' ); //업태3-중분류
                    $('c[r=AP2]', sheet).attr( 's', '32' ); //영위사업
                    $('c[r=AQ2]', sheet).attr( 's', '32' ); //수집유형
                    $('c[r=AR2]', sheet).attr( 's', '32' ); //의향단계
                    $('c[r=AS2]', sheet).attr( 's', '32' ); //컨택진행여부
                    $('c[r=AT2]', sheet).attr( 's', '32' ); //컨택일자
                    $('c[r=AU2]', sheet).attr( 's', '32' ); //컨택결과
                    $('c[r=AV2]', sheet).attr( 's', '32' ); //컨택대상자성명
                    $('c[r=AW2]', sheet).attr( 's', '32' ); //컨택대상자연락처
                    $('c[r=AX2]', sheet).attr( 's', '32' ); //컨택결과세부내용

                }
            }
        ]
    }).container().appendTo($('#kt_datatable_excel_hidden_buttons'));

}

function objectifyForm(formArray) {
  //serialize data function
  let returnArray = {};
  for (let i = 0; i < formArray.length; i++){
    returnArray[formArray[i]['name']] = formArray[i]['value'];
  }
  return returnArray;
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