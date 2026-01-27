var pageNum = 1; // 페이지 번호 생성 시점에 따른 변수 초기화
var mngYear = '전체';
var isSearching = false;  // 중복 요청 방지 플래그
$(function(){
    //페이지 오픈 시 default ()
    galleryList(pageNum, '전체');

    $(document).on('click', '.cate', function(e) {
        if (isSearching) return; // 검색 중이면 클릭 무시
        mngYear = $(e.target).data('value');
        f_gallery_category_search(e.target, mngYear);
    });
});

const showPageCnt = 10; // 화면에 보일 페이지 번호 개수

/**
 * @param pageNum 출력 페이지 번호
 * @param mngYearValue
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
    if (isSearching) return; // 중복 호출 방지
    isSearching = true;

    // 로딩바 표시 (선택 사항)
    // if(typeof loadingBarShow === 'function') loadingBarShow();

    const countPerPage = 12;
    let start = (pageNum - 1) * countPerPage;
    if(start < 0) start = 0;

    let link = document.location.href;
    let lang = link.includes('eng') ? 'EN' : 'KO';

    let searchText = $('#search_text').val();
    let condition = $('#search_box option:selected').val();

    let jsonObj = {
        pageNum: start,
        rows: countPerPage,
        mngYear: mngYearValue,
        condition: condition,
        searchText: searchText
    };

    $.ajax({
        url: '/board/gallery/selectList.do',
        method: 'post',
        data: JSON.stringify(jsonObj),
        contentType: 'application/json; charset=utf-8'
    })
        .done(function (data){
            let results = data;
            let htmlArr = []; // [성능개선] 배열을 사용하여 HTML 구성

            if(nvl(results, "") !== "" && results.length > 0) {
                $.each(results , function(i, item){
                    let title = (lang === 'KO') ? item.title : nvl(item.titleEn, item.title);
                    let writeDate = item.writeDate.split(' ')[0].replaceAll('-','.');

                    // 기본 이미지 설정
                    let thumbnailSrc = '/img/sample_img.jpg';

                    // [핵심] fileList에서 정보 가져오기
                    let fileList = item.fileList;

                    if(nvl(fileList, "") !== "" && fileList.length > 0){
                        // 첫 번째 파일을 썸네일로 사용
                        let firstFile = fileList[0];

                        // 1. 웹 경로 추출 (서버 절대 경로 제거)
                        // FileDTO의 fullFilePath가 '/usr/local/...' 형태라면 치환
                        let webPath = firstFile.fullFilePath.replace('/usr/local/tomcat/webapps', '');

                        // 2. 원본 파일명 (FileDTO의 fileName 필드가 원본명이라고 가정)
                        let originalName = firstFile.fileName;

                        // 3. 컨트롤러 호출 URL 생성 (경로와 파일명 인코딩 필수)
                        thumbnailSrc = '/file/imageView.do?path=' + encodeURIComponent(webPath) + '&fileName=' + encodeURIComponent(originalName);
                    }

                    let li = '<li style="cursor: pointer">';
                    li += '<a class="viewGallery">';
                    li += '<div class="thumb75 thumbBox">';
                    // [성능개선] loading="lazy" 추가
                    li += '<img class="thumbImg" loading="lazy" src="' + thumbnailSrc + '" alt="' + title + '">';

                    // 슬라이드용 이미지 경로를 hidden input으로 저장
                    if(nvl(fileList, "") !== "") {
                        $.each(fileList , function(j, file) {
                            let slideWebPath = file.fullFilePath.replace('/usr/local/tomcat/webapps', '');
                            let slideSrc = '/file/imageView.do?path=' + encodeURIComponent(slideWebPath) + '&fileName=' + encodeURIComponent(file.fileName);
                            li += '<input type="hidden" name="slideImg" value="' + slideSrc + '">';
                        });
                    }
                    li += '</div>';
                    li += '<div class="txtBox">';
                    li += '<div class="tit">' + title + '</div>';
                    li += '<div class="date">' + writeDate + '</div>';
                    li += '</div>';
                    li += '</a>';
                    li += '</li>';

                    htmlArr.push(li);
                });

                // 전체 개수 세팅 (첫 페이지일 때만 갱신하거나, 매번 갱신)
                $('span.total').text(Number(results[0].totalRecords).toLocaleString());

                // [성능개선] 한 번에 DOM 주입
                $('.board_gallery_box ul').empty().html(htmlArr.join(''));

                // 페이징 정보 세팅
                setPaging(pageNum);

            } else {
                // 데이터 없음
                $('span.total').text(0);
                $('.paging ol').empty();
                $('.board_gallery_box ul').html('<li><div style="width:100%; text-align:center; padding:50px 0;">해당 조건으로 검색된 자료가 없습니다.</div></li>');
                hidePagingIcons();
            }
        })
        .fail(function(xhr, status, errorThrown) {
            console.error("Gallery Load Error:", errorThrown);
            alert("자료를 불러오는 중 오류가 발생했습니다.");
        })
        .always(function() {
            isSearching = false; // 요청 완료 시 플래그 해제
            // if(typeof KTApp !== 'undefined') KTApp.hidePageLoading(); // 로딩바 숨김
        });
}

/**
 * 페이징 정보를 세팅합니다.
 * @param {int} pageNum - Page Number
 */
function setPaging(pageNum) {
    const totalCnt = parseInt($('span.total').text().replace(/,/g, '')) || 0;
    const countPerPage = 12;
    const currentPage = pageNum;
    const totalPage = Math.ceil(totalCnt / countPerPage);

    showAllIcon();

    // 이전/처음 버튼 숨김 처리
    if (currentPage <= 1) { // 1페이지면 안 보임 (또는 그룹 단위 로직 적용 가능)
        // 단순화: 1~10페이지 구간이면 prev 숨김 등을 처리하려면 showPageCnt 활용
        const startPage = Math.floor((currentPage - 1) / showPageCnt) * showPageCnt + 1;
        if(startPage === 1) {
            $('#first_page').hide();
            $('#prev_page').hide();
        }
    }

    // 다음/마지막 버튼 숨김 처리
    const startPage = Math.floor((currentPage - 1) / showPageCnt) * showPageCnt + 1;
    const endPage = startPage + showPageCnt - 1;
    if (endPage >= totalPage) {
        $('#next_page').hide();
        $('#last_page').hide();
    }

    let sPagesHtml = [];
    for (let i = startPage; i <= endPage && i <= totalPage; i++) {
        let activeClass = (i === currentPage) ? 'this' : 'other';
        sPagesHtml.push('<li><a class="' + activeClass + '" style="cursor: pointer">' + i + '</a></li>');
    }
    $('.paging ol').html(sPagesHtml.join(''));
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

function hidePagingIcons() {
    $('#first_page').hide();
    $('#prev_page').hide();
    $('#next_page').hide();
    $('#last_page').hide();
}

function f_gallery_category_search(target, mngYearValue){
    $(target).siblings().removeClass('active');
    $(target).addClass('active');

    /* 조회 */
    galleryList(1, mngYearValue);
}