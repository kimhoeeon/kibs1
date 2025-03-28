var pageNum = 1; // 페이지 번호 생성 시점에 따른 변수 초기화
$(function(){
    //페이지 오픈 시 default ()
    noticeList(pageNum);

});

const showPageCnt = 3; // 화면에 보일 페이지 번호 개수

/**
 * @param pageNum 출력 페이지 번호
 * */
function noticeList(pageNum) {
    // 데이터 조회
    searchPosts(pageNum);

    // 페이지당 건수(10, 30, 50)가 변경되면 재조회
    /*$('#countPerPage').change(function() {
        searchPosts(1);
    });*/

    // 페이지 번호 클릭
    $(document).on('click', '.paging>ol>li>a', function() {
        if (!$(this).hasClass('this')) {
            $(this).parent().find('a.this').removeClass('this');
            $(this).addClass('this');

            searchPosts(Number($(this).text()));
        }
    });

    // 페이징 Icon(<<, <, >, >>) 클릭
    $(document).on('click', '.paging>span', function() {
        const totalCnt = parseInt($('span.total').text());
        const countPerPage = 10;
        const totalPage = Math.ceil(totalCnt / countPerPage);

        const id = $(this).attr('id');

        if (id === 'first_page') { //<<
            searchPosts(1);
        } else if (id === 'prev_page') { //<
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const prevPage = Math.min(...arrPages) - 1;
            searchPosts(prevPage);
        } else if (id === 'next_page') { //>
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const nextPage = Math.max(...arrPages) + 1;
            searchPosts(nextPage);
        } else if (id === 'last_page') { //>>
            searchPosts(totalPage);
        }
    });

}

/**
 * 페이지별 데이터를 조회합니다.
 * @param {int} pageNum - Page Number
 */
function searchPosts(pageNum) {
    const countPerPage = 10; // 페이지당 노출 개수
    let start = (pageNum - 1) * countPerPage;
    if(start < 0){
        start = 0;
    }
    let flag = true;

    let link =  document.location.href;
    let lang_page = 'KO';
    if(link.includes('eng')){
        lang_page = 'EN';
    }
    /* 검색조건 */
    let searchText = $('#search_text').val();
    let condition = $('#search_box option:selected').val();

    let jsonObj = {
        pageNum: start,
        rows: countPerPage,
        lang: lang_page,
        condition: condition,
        searchText: searchText
    };

    $.ajax({
        url: '/guide/notice/selectList.do',
        method: 'post',
        data: JSON.stringify(jsonObj),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
    .done(function (data, status){
        // console.log(status);
        // console.log(data);
        let results = data;
        let str = '';
        $.each(results , function(i){
            let rownum = results[i].rownum;
            let id = results[i].id;
            let lang = results[i].lang;
            let noticeGbn = results[i].noticeGbn;
            let title = results[i].title;
            let writer = results[i].writer;
            let writeDate = results[i].writeDate;
            writeDate = writeDate.split(' ')[0].replaceAll('-','.');
            let viewCnt = results[i].viewCnt;

            //console.log(id, noticeGbn, title, writer, writeDate, viewCnt);

            if(noticeGbn === '1'){
                str += '<li class="postNt">';
                    str += '<div class="num">';
                    if(lang === 'KO'){
                        str += '중요';
                    }else{
                        str += 'Important';
                    }
                    str += '</div>';
            }else{
                str += '<li>';
                    str += '<div class="num">';
                    str += rownum;
                    str += '</div>';
            }

                if(lang === 'KO') {
                    str += '<div class="tit" onClick="location.href=\'/guide/noticeView.do?id=' + id + '\'">';
                }else{
                    str += '<div class="tit" onClick="location.href=\'/eng/board/noticeView.do?id=' + id + '\'">';
                }
                    str += title;
                str += '</div>';
                str += '<div class="write">';
                    if(lang === 'KO'){
                        str += '관리자';
                    }else{
                        str += 'Administrator';
                    }
                str += '</div>';
                str += '<div class="date">';
                    str += writeDate;
                str += '</div>';
                str += '<div class="count">';
                    str += viewCnt;
                str += '</div>';

            str += '</li>';

            if(results.length === (i+1)){ // each 문이 모두 실행되면 아래 페이징 정보 세팅 실행
                flag = false;
            }
        });

        $('.board_list_box .tBody').empty();
        $('.board_list_box .tBody').html(str);

        if(nvl(results,"") !== "") {
            // 맨 처음에만 total 값 세팅
            if (pageNum === 1) {
                $('span.total').text(results[0].totalRecords || 0);
            }
        }else{ // 데이터 없는 경우 ( 해당 권역에 양조장 없을 경우 )
            $('span.total').text(0);
            $('.paging ol').empty(); // 페이징 번호 없애기
            let emptyStr = '';
            emptyStr += '<li>';
            emptyStr += '<div>';
            if(lang_page === 'KO'){
                emptyStr += '해당 조건으로 검색된 공지사항이 없습니다.';
            }else{
                emptyStr += 'No announcements have been retrieved for this condition.';
            }
            emptyStr += '</div>';
            emptyStr += '</li>';
            $('.board_list_box .tBody').html(emptyStr);
        }
    })
    .fail(function(xhr, status, errorThrown) {
        $('body').html("오류가 발생했습니다.")
            .append("<br>오류명: " + errorThrown)
            .append("<br>상태: " + status);
    })
    .always(function() {
        if(!flag){ // flag = false 이면 아래 페이징 정보 세팅 실행
            // 페이징 정보 세팅
            setPaging(pageNum);
        }
    });
}

/**
 * 페이징 정보를 세팅합니다.
 * @param {int} pageNum - Page Number
 */
function setPaging(pageNum) {
    const totalCnt = parseInt($('span.total').text());
    const countPerPage = 10;

    const currentPage = pageNum;
    const totalPage = Math.ceil(totalCnt / countPerPage);

    showAllIcon();

    if (currentPage <= showPageCnt) {
        $('#first_page').hide();
        $('#prev_page').hide();
    }
    if (
        totalPage <= showPageCnt ||
        Math.ceil(currentPage / showPageCnt) * showPageCnt + 1 > totalPage
    ) {
        $('#next_page').hide();
        $('#last_page').hide();
    }

    let start = Math.floor((currentPage - 1) / showPageCnt) * showPageCnt + 1;
    let sPagesHtml = '';
    for (const end = start + showPageCnt; start < end && start <= totalPage; start++) {
        sPagesHtml += '<li><a class="' + (start === currentPage ? 'this' : 'other') + '" style="cursor: pointer">' + start + '</a></li>';
    }
    $('.paging ol').html(sPagesHtml);
}

/**
 * Icon(<<, <, >, >>) All Show
 */
function showAllIcon() {
    $('#first_page').show();
    $('#prev_page').show();
    $('#next_page').show();
    $('#last_page').show();
};