var pageNum = 1; // 페이지 번호 생성 시점에 따른 변수 초기화
var mngYear = '전체';
$(function(){
    //페이지 오픈 시 default ()
    galleryList(pageNum, '전체');

    $(document).on('click', '.cate', function(e) {
        mngYear = $(e.target).data('value');
    });
});

const showPageCnt = 3; // 화면에 보일 페이지 번호 개수

/**
 * @param pageNum 출력 페이지 번호
 * */
function galleryList(pageNum, mngYearValue) {
    // 데이터 조회
    searchPosts(pageNum, mngYearValue);

    // 페이지당 건수(10, 30, 50)가 변경되면 재조회
    /*$('#countPerPage').change(function() {
        searchPosts(1);
    });*/

    // 페이지 번호 클릭
    $(document).on('click', '.paging>ol>li>a', function() {
        if (!$(this).hasClass('this')) {
            $(this).parent().find('a.this').removeClass('this');
            $(this).addClass('this');

            searchPosts(Number($(this).text()), mngYear);
        }
    });

    // 페이징 Icon(<<, <, >, >>) 클릭
    $(document).on('click', '.paging>span', function() {
        const totalCnt = parseInt($('span.total').text());
        const countPerPage = 12;
        const totalPage = Math.ceil(totalCnt / countPerPage);

        const id = $(this).attr('id');

        if (id === 'first_page') { //<<
            searchPosts(1, mngYear);
        } else if (id === 'prev_page') { //<
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const prevPage = Math.min(...arrPages) - 1;
            searchPosts(prevPage, mngYear);
        } else if (id === 'next_page') { //>
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const nextPage = Math.max(...arrPages) + 1;
            searchPosts(nextPage, mngYear);
        } else if (id === 'last_page') { //>>
            searchPosts(totalPage, mngYear);
        }
    });

}

/**
 * 페이지별 데이터를 조회합니다.
 * @param {int} pageNum - Page Number
 * @param mngYearValue - Category Value
 */
function searchPosts(pageNum, mngYearValue) {
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
        "pageNum": start,
        "rows": countPerPage,
        "mngYear": mngYearValue,
        "condition": condition,
        "searchText": searchText
    };

    $.ajax({
        url: '/board/gallery/selectList.do',
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
            let writeDate = results[i].writeDate;
            writeDate = writeDate.split(' ')[0].replaceAll('-','.');

            let thumbnailImage = '/img/sample_img.jpg';
            let fullFilePathList = results[i].fullFilePathList;
            if(nvl(fullFilePathList, "") !== ""){
                thumbnailImage = fullFilePathList[0];
                if(nvl(thumbnailImage,"") !== ""){
                    thumbnailImage = thumbnailImage.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
                }
            }

            str += '<li style="cursor: pointer">';
                str += '<a class="viewGallery">';
                    str += '<div class="thumb75 thumbBox">';
                        str += '<img class="thumbImg" src="' + thumbnailImage + '">';

                        if(nvl(fullFilePathList, "") !== "") {
                            $.each(fullFilePathList , function(i) {
                                str += '<input type="hidden" name="slideImg" value="' + fullFilePathList[i] + '">';
                            });
                        }

                    str += '</div>';
                    str += '<div class="txtBox">';
                        str += '<div class="tit">';
                            str += title;
                        str += '</div>';
                        str += '<div class="date">';
                            str += writeDate;
                        str += '</div>';
                    str += '</div>';
                str += '</a>';
            str += '</li>';

            if(results.length === (i+1)){ // each 문이 모두 실행되면 아래 페이징 정보 세팅 실행
                flag = false;
            }
        });

        $('.board_gallery_box ul').empty();
        $('.board_gallery_box ul').html(str);

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
            emptyStr += '해당 조건으로 검색된 자료가 없습니다.';
            emptyStr += '</div>';
            emptyStr += '</li>';
            $('.board_gallery_box ul').html(emptyStr);
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

function f_gallery_category_search(target, mngYearValue){
    $(target).siblings().removeClass('active');
    $(target).addClass('active');

    /* 조회 */
    galleryList(1, mngYearValue);
}