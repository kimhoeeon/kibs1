/***
 * mng/exhibitor/application/online.js
 * 참가신청서 관리 >  요트/보트 출품 정보
 * */
var transferYear = '2027';
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
    $('#transferYear').val('').select2({minimumResultsForSearch: Infinity});
    $('#boatEntryYn').val('').select2({minimumResultsForSearch: Infinity});

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

    let resData = ajaxConnect('/mng/exhibitorNewNew/application/product/selectList.do', 'post', jsonObj);

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

    let transferYear = $('#transferYear option:selected').val(); //참가년도
    let boatEntryYn = $('#boatEntryYn option:selected').val(); //출품여부

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        transferYear: transferYear,
        boatEntryYn: boatEntryYn,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitorNewNew/application/product/selectList.do', 'post', jsonObj);
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
    window.location.href = '/mng/exhibitorNewNew/application/product/detail.do?seq=' + seq;
}

function f_exhibitor_product_new_excel_export(){
    Swal.fire({
        title: '[ 전체 요트/보트 출품 정보 상세 다운로드 ]',
        html: '전체 요트/보트 출품 정보 상세를 다운로드하시겠습니까 ?',
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
            form.setAttribute('action','/mng/exhibitor/product/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', '전시품_신청_정보_' + getCurrentDate() + '.xlsx');

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