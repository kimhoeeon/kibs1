/***
 * mng/exhibitor/participant/member.js
 * 전시회 > 참가자 관리 > 회원 목록
 * */

var transferYear = (new Date().getFullYear()).toString();

$(function(){

});

function f_member_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_member_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let now = new Date();
    let now_year = transferYear;
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {
            "transferYear": now_year
        };
    }else{
        jsonObj = {
            "transferYear": now_year,
            "condition": $('#search_box option:selected').val(),
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/participant/member/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_member_table tbody td');
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
    let dataTbl = $('#kt_member_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let gbn = $('#condition_gbn option:selected').val();
    let stat = $('#condition_status option:selected').val();
    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        gbn: gbn,
        stat: stat,
        transferYear: transferYear,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/participant/member/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_member_table tbody td');
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
    $('#condition_gbn').val('').select2({minimumResultsForSearch: Infinity});
    $('#condition_status').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_member_search();
}

function f_member_detail(seq){

    let infoMsg = '전시업체 상세보기 페이지로 이동합니다.';

    let gbn = seq.substring(0,1);
    if(gbn === 'V'){
        infoMsg = '참관객 상세보기 페이지로 이동합니다.';
    }

    Swal.fire({
        title: '상세보기',
        html: infoMsg,
        icon: 'info',
        allowOutsideClick: false,
        confirmButtonColor: '#3085d6',
        confirmButtonText: '확인'
    }).then((result) => {
        if (result.isConfirmed) {
            if(gbn === 'V'){
                //참관객
                window.location.href='/mng/exhibitor/participant/company/detail.do?seq=' + seq;
            }else{
                //전시업체
                window.location.href='/mng/exhibitor/participant/company/detail.do?seq=' + seq;
            }
        }
    });

}