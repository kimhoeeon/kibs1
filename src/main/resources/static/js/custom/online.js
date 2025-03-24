/***
 * mng/exhibitor/application/online.js
 * 참가신청서 관리 > 온라인 전시관
 * */

$(function(){
    $('#kt_online_info .form-check-label').css({'color': '#000', 'opacity': 1});
    $('#kt_online_info .form-check-input').css({'color': '#000', 'opacity': 1});
    $('#kt_online_info .form-control').css({'color': '#000', 'opacity': 1});

    $('#online_view_yn_btn').on('click', function () {
        let seq = $(this).attr('value');
        let viewYn = $(this).attr('data-view');
        f_online_view_yn(seq , viewYn);
    });
});

function f_application_online_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#transfer_year').val('').select2({minimumResultsForSearch: Infinity});
    $('#company_online_view_yn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_online_search();
}

function f_application_online_search(){

    let search_box = $('#search_box option:selected').val();
    let search_text = $('#search_text').val();

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_online_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    if(nullToEmpty(search_text) === ""){
        jsonObj = {};
    }else{
        jsonObj = {
            "condition": search_box,
            "searchText": search_text
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/application/online/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_online_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_online_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let transferYear = $('#transfer_year option:selected').val(); //참가년도
    let companyOnlineViewYn = $('#company_online_view_yn option:selected').val(); //노출여부

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        "transferYear": transferYear,
        "companyOnlineViewYn": companyOnlineViewYn,
        "condition": condition,
        "searchText": searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/application/online/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_online_table tbody td');
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

function f_application_online_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/online/detail.do?seq=' + id;
}

function f_online_view_yn(seq, viewYn){
    if(nvl(seq,"") === ""){
        showMessage('', 'info', '노출 상태 변경', '상태를 변경할 데이터를 선택 후 클릭해주세요.', '');
    }else{
        let companyOnlineViewYn = 'N';
        let viewStatus = '<span style="font-weight: bold; color: #009ef7;">노출</span>';
        if(viewYn === 'N'){
            viewStatus = '<span style="font-weight: bold; color: #f1416c;">미노출</span>';
            companyOnlineViewYn = 'Y';
        }

        Swal.fire({
            title: '노출 상태 변경',
            html: '노출 상태를 변경하시겠습니까 ?<br>현재 노출 상태 : ' + viewStatus,
            icon: 'info',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let jsonObj = {
                    seq: seq,
                    companyOnlineViewYn: companyOnlineViewYn
                }
                let resData = ajaxConnect('/mng/exhibitor/application/online/updateViewYn.do', 'post', jsonObj);

                if(resData.resultCode !== "0"){
                    showMessage('', 'error', '에러 발생', '노출 상태 변경을 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }else{
                    showMessage('', 'info', '노출 상태 변경', '노출 상태 변경이 정상 완료되었습니다.', '');

                    /* 재조회 */
                    f_application_online_search();
                }
            }
        });
    }
}