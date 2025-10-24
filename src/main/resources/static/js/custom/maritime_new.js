/***
 * mng/exhibitor/application/maritime.js
 * 참가신청서 관리 > 해상전시회 신청
 * */
var transferYear = '2026';

$(function(){

});

function f_application_maritime_new_search_condition_init(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#maritimeExhibitionYn').val('').select2({minimumResultsForSearch: Infinity});
    $('#maritimeExhibitionGbn').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_application_maritime_new_search();
}

function f_application_maritime_new_search(){

    let search_box = $('#search_box option:selected').val();
    let search_text = $('#search_text').val();
    let maritimeExhibitionYn = $('#maritimeExhibitionYn option:selected').val();
    let maritimeExhibitionGbn = $('#maritimeExhibitionGbn option:selected').val();

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_maritime_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    if(nvl(search_text) === ""){
        jsonObj = {
            transferYear: transferYear,
            maritimeExhibitionYn: maritimeExhibitionYn,
            maritimeExhibitionGbn: maritimeExhibitionGbn
        };
    }else{
        jsonObj = {
            transferYear: transferYear,
            condition: search_box,
            searchText: search_text,
            maritimeExhibitionYn: maritimeExhibitionYn,
            maritimeExhibitionGbn: maritimeExhibitionGbn
        }
    }

    let resData = ajaxConnect('/mng/exhibitorNew/application/maritime/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_maritime_new_table tbody td');
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
    let dataTbl = $('#kt_exhibitor_application_maritime_new_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let maritimeExhibitionYn = $('#maritimeExhibitionYn option:selected').val();
    let maritimeExhibitionGbn = $('#maritimeExhibitionGbn option:selected').val();

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        transferYear: transferYear,
        condition: condition,
        searchText: searchText,
        maritimeExhibitionYn: maritimeExhibitionYn,
        maritimeExhibitionGbn: maritimeExhibitionGbn
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitorNew/application/maritime/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_maritime_new_table tbody td');
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

function f_application_maritime_new_modify_init_set(seq){
    window.location.href = '/mng/exhibitorNew/application/maritime/detail.do?seq=' + seq;
}

function f_application_maritime_new_save(seq){

    Swal.fire({
        icon: 'info',
        title: '[ 해상전시회 신청 ]',
        html: '<span style="font-size: 1.2em;">입력된 정보를 저장하시겠습니까?</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '변경내용저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            const participationChoice = $('input[name="maritimeExhibitionYn"]:checked').val();
            const isSeaChecked = $('#maritimeDetailBox input[name="maritimeExhibitionSea"]').is(':checked');
            const isLandChecked = $('#maritimeDetailBox input[name="maritimeExhibitionLand"]').is(':checked');

            // 1. '참가'를 선택한 경우
            if (participationChoice === 'Y') {
                // 1-1. 해상 전시 또는 육상 전시 둘 다 선택하지 않은 경우
                if (!isSeaChecked && !isLandChecked) {
                    Swal.fire({
                        icon: 'error',
                        title: '[ 해상전시회 신청 ]',
                        text: '해상전시회 참가를 선택했을 경우, 참가 항목(해상/육상)을 1개 이상 선택해주세요.',
                        confirmButtonColor: '#00a8ff',
                        confirmButtonText: '확인'
                    });
                    return; // 서버 전송 중단
                }
            }

            let formData = {
                seq: seq,
                maritimeExhibitionYn: participationChoice,
                maritimeExhibitionSea: isSeaChecked,
                maritimeExhibitionLand: isLandChecked
            };

            $.ajax({
                url: '/mng/exhibitorNew/application/maritime/update.do',
                method: 'POST',
                async: false,
                data: JSON.stringify(formData),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        Swal.fire({
                            icon: 'info',
                            title: '[ 해상전시회 신청 ]',
                            html: '<span style="font-size: 1.2em;">해상전시회 정보가 저장되었습니다.</span>',
                            allowOutsideClick: false,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_application_maritime_new_modify_init_set(seq); // 재조회
                            }
                        });

                    } else {
                        showMessage('', 'error', '에러 발생', '해상전시회 정보 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })

        }//isConfirmed
    })//then

}