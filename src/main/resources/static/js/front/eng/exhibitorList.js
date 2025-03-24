var pageNum = 1; // 페이지 번호 생성 시점에 따른 변수 초기화
var transferYear = (new Date().getFullYear()).toString();
$(function(){
    //페이지 오픈 시 default ()
    exhibitorList(pageNum, transferYear);

    $(document).on('click', '.cate', function(e) {
        transferYear = $(e.target).data('value');
    });
});

const showPageCnt = 3; // 화면에 보일 페이지 번호 개수

/**
 * @param pageNum 출력 페이지 번호
 * */
function exhibitorList(pageNum, transferYearValue) {
    // 데이터 조회
    searchPosts(pageNum, transferYearValue);

    // 페이지당 건수(10, 30, 50)가 변경되면 재조회
    /*$('#countPerPage').change(function() {
        searchPosts(1);
    });*/

    // 페이지 번호 클릭
    $(document).on('click', '.paging>ol>li>a', function() {
        if (!$(this).hasClass('this')) {
            $(this).parent().find('a.this').removeClass('this');
            $(this).addClass('this');

            searchPosts(Number($(this).text()), transferYear);
        }
    });

    // 페이징 Icon(<<, <, >, >>) 클릭
    $(document).on('click', '.paging>span', function() {
        const totalCnt = parseInt($('span.total').text());
        const countPerPage = 15;
        const totalPage = Math.ceil(totalCnt / countPerPage);

        const id = $(this).attr('id');

        if (id === 'first_page') { //<<
            searchPosts(1, transferYear);
        } else if (id === 'prev_page') { //<
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const prevPage = Math.min(...arrPages) - 1;
            searchPosts(prevPage, transferYear);
        } else if (id === 'next_page') { //>
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const nextPage = Math.max(...arrPages) + 1;
            searchPosts(nextPage, transferYear);
        } else if (id === 'last_page') { //>>
            searchPosts(totalPage, transferYear);
        }
    });

}

/**
 * 페이지별 데이터를 조회합니다.
 * @param {int} pageNum - Page Number
 */
function searchPosts(pageNum, transferYearValue) {
    const countPerPage = 15; // 페이지당 노출 개수
    let start = (pageNum - 1) * countPerPage;
    if(start < 0){
        start = 0;
    }
    let flag = true;

    if(transferYearValue === ''){
        transferYearValue = $('.year_tab_box a.active').data('value');
    }

    /* 검색조건 */
    let searchText = $('#search_text').val();

    let jsonObj = {
        pageNum: start,
        rows: countPerPage,
        searchText: searchText,
        transferYear: transferYearValue
    };

    $.ajax({
        url: '/eng/exhibitor/selectList.do',
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
            let company = results[i].company;
            let telephone = results[i].telephone;
            let homepage = results[i].homepage;
            let category = results[i].category;

            str += '<tr>';
                str += '<td>';
                    str += '<div class="txtBlue">';
                        str += company;
                    str += '</div>';
                str += '</td>';
                str += '<td>';
                    str += telephone;
                str += '</td>';
                str += '<td>';
                if(nvl(homepage,'') === '' || homepage === '-') {
                    str += homepage;
                }else{
                    str += '<a href="' + getRightURL(homepage) + '" target="_blank">';
                    str += getRightURL(homepage);
                    str += '</a>';
                }
                str += '</td>';
                str += '<td>';
                    str += category;
                str += '</td>';
            str += '</tr>';

            if(results.length === (i+1)){ // each 문이 모두 실행되면 아래 페이징 정보 세팅 실행
                flag = false;
            }
        });

        $('.table_box table tbody').empty();
        $('.table_box table tbody').html(str);

        if(nvl(results,'') !== '') {
            // 맨 처음에만 total 값 세팅
            if (pageNum === 1) {
                $('span.total').text(results[0].totalRecords || 0);
            }
        }else{

            $('span.total').text(0);
            $('.paging ol').empty(); // 페이징 번호 없애기
            let emptyStr = '';
            emptyStr += '<tr>';
                emptyStr += '<td colspan="4">';
                emptyStr += 'No companies were discovered under this condition.';
                emptyStr += '</td>';
            emptyStr += '</tr>';
            $('.table_box table tbody').html(emptyStr);
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

function getRightURL(n) {
    let tmpURL = n.replace(/\s/g, "")
    let tmp = tmpURL.toLowerCase();
    if( tmp.indexOf("http://") === 0 ||
        tmp.indexOf("https://") === 0 ||
        tmp.indexOf("ftp://") === 0 ||
        tmp.indexOf("mailto://") === 0 ||
        tmp.indexOf("mms://") === 0 ||
        tmp.indexOf("pnm://") === 0 ||
        tmp.indexOf("telnet://") === 0 ||
        tmp.indexOf("rlogin://") === 0 ||
        tmp.indexOf("news://") === 0 ||
        tmp.indexOf("file://") === 0)
            return tmpURL;
    else
            return "http://"+tmpURL;
}

/**
 * 페이징 정보를 세팅합니다.
 * @param {int} pageNum - Page Number
 */
function setPaging(pageNum) {
    const totalCnt = parseInt($('span.total').text());
    const countPerPage = 15;

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

function f_exhibitor_list_search(target, transferYearValue){
    $(target).siblings().removeClass('active');
    $(target).addClass('active');

    /* 조회 */
    exhibitorList(1, transferYearValue);
}