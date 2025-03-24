var pageNum = 1; // 페이지 번호 생성 시점에 따른 변수 초기화
var category = '전체보기';
$(function(){
    //페이지 오픈 시 default ()
    kibstvList(pageNum, '전체보기');

    $(document).on('click', '.cate', function(e) {
        category = $(e.target).data('value');
    });
});

const showPageCnt = 3; // 화면에 보일 페이지 번호 개수

/**
 * @param pageNum 출력 페이지 번호
 * @param categoryValue
 * */
function kibstvList(pageNum, categoryValue) {
    // 데이터 조회
    searchPosts(pageNum, categoryValue);

    // 페이지당 건수(10, 30, 50)가 변경되면 재조회
    /*$('#countPerPage').change(function() {
        searchPosts(1);
    });*/

    // 페이지 번호 클릭
    $(document).on('click', '.paging>ol>li>a', function() {
        if (!$(this).hasClass('this')) {
            $(this).parent().find('a.this').removeClass('this');
            $(this).addClass('this');

            searchPosts(Number($(this).text()), category);
        }
    });

    // 페이징 Icon(<<, <, >, >>) 클릭
    $(document).on('click', '.paging>span', function() {
        const totalCnt = parseInt($('span.total').text());
        const countPerPage = 12;
        const totalPage = Math.ceil(totalCnt / countPerPage);

        const id = $(this).attr('id');

        if (id === 'first_page') { //<<
            searchPosts(1, category);
        } else if (id === 'prev_page') { //<
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const prevPage = Math.min(...arrPages) - 1;
            searchPosts(prevPage, category);
        } else if (id === 'next_page') { //>
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const nextPage = Math.max(...arrPages) + 1;
            searchPosts(nextPage, category);
        } else if (id === 'last_page') { //>>
            searchPosts(totalPage, category);
        }
    });

}

/**
 * 페이지별 데이터를 조회합니다.
 * @param {int} pageNum - Page Number
 * @param categoryValue
 */
function searchPosts(pageNum, categoryValue) {
    const countPerPage = 12; // 페이지당 노출 개수
    let start = (pageNum - 1) * countPerPage;
    if(start < 0){
        start = 0;
    }
    let flag = true;

    let link =  document.location.href;
    let lang = 'KO';
    if(link.includes('eng')){
        lang = 'EN';
    }

    /* 검색조건 */
    let searchText = $('#search_text').val();
    let condition = $('#search_box option:selected').val();

    let jsonObj = {
        pageNum: start,
        rows: countPerPage,
        lang: lang,
        category: categoryValue,
        condition: condition,
        searchText: searchText
    };

    $.ajax({
        url: '/online/kibstv/selectList.do',
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
            let id = results[i].id;
            let title = results[i].title;
            let youtubeUrl = results[i].youtubeUrl; // wpqM_Ahpx_E
            let youtubeGbn = 'long';
            if(youtubeUrl.includes('/shorts/')){
                if(youtubeUrl.includes('?')){
                    youtubeUrl = youtubeUrl.substring(youtubeUrl.indexOf('/shorts/') + 8, youtubeUrl.indexOf('?'));
                }else{
                    youtubeUrl = youtubeUrl.substring(youtubeUrl.indexOf('/shorts/') + 8, youtubeUrl.length);
                }
                youtubeGbn = 'short';
            }
            //console.log(id, noticeGbn, title, writer, writeDate, viewCnt);

            str += '<li class="viewKibsTv">';
                str += '<a>';
                    str += '<div class="thumb169 thumbBox">';
                        str += '<img src="https://img.youtube.com/vi/' + youtubeUrl + '/mqdefault.jpg" data-value="' + youtubeGbn + '" class="thumbImg">';
                    str += '</div>';
                    str += '<div class="title">';
                        str += title;
                    str += '</div>';
                str += '</a>';
            str += '</li>';

            if(results.length === (i+1)){ // each 문이 모두 실행되면 아래 페이징 정보 세팅 실행
                flag = false;
            }
        });

        $('.tvList ul').empty();
        $('.tvList ul').html(str);

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
            if(lang === 'KO'){
                emptyStr += '해당 조건으로 검색된 KIBS Tube가 없습니다.';
            }else{
                emptyStr += 'No KIBS Tubes were retrieved.';
            }
            emptyStr += '</div>';
            emptyStr += '</li>';
            $('.tvList ul').html(emptyStr);
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
    const countPerPage = 12;

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
}

function f_kibstv_category_search(target, category){
    $(target).parent().siblings().removeClass('active');
    $(target).parent().addClass('active');

    /* 조회 */
    kibstvList(1, category);
}