/***
 * mng/exhibitor/application/online.js
 * 참가신청서 관리 > 전시품 정보
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

function f_application_product_new_search_condition_init(){

    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#transfer_year').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_product_new_search();
}

function f_application_product_new_search(){

    let search_box = $('#search_box option:selected').val();
    let search_text = $('#search_text').val();

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_product_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    if(nvl(search_text,'') === ""){
        jsonObj = {};
    }else{
        jsonObj = {
            condition: search_box,
            searchText: search_text
        }
    }

    let resData = ajaxConnect('/mng/exhibitorNew/application/product/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_product_new_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_product_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let transferYear = $('#transfer_year option:selected').val(); //참가년도

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        transferYear: transferYear,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitorNew/application/product/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_product_new_table tbody td');
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

function f_application_product_new_modify_init_set(seq){
    window.location.href = '/mng/exhibitorNew/application/product/detail.do?seq=' + seq;
}