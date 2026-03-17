var pageNum = 1; // 현재 페이지 번호
var mngYear = '전체'; // 현재 선택된 연도 카테고리
var isSearching = false;  // 중복 요청 방지 플래그

$(function(){
    // 1. 페이지 오픈 시 초기 조회
    galleryList(pageNum, mngYear);

    // 2. 카테고리(연도) 탭 클릭 이벤트 바인딩
    $(document).on('click', '.year_tab_box .cate', function() {
        if (isSearching) return; // 통신 중 중복 클릭 방지

        // 탭 UI 활성화 처리
        $(this).siblings().removeClass('active');
        $(this).addClass('active');

        // 전역 연도 변수 갱신 및 1페이지부터 다시 검색
        mngYear = $(this).data('value');
        galleryList(1, mngYear);
    });

    // 3. 페이지 번호 클릭 이벤트 (중복 방지를 위해 단 1번만 선언)
    $(document).on('click', '.paging>ol>li>a', function() {
        if (!$(this).hasClass('this')) {
            $(this).parent().find('a.this').removeClass('this');
            $(this).addClass('this');

            let targetPage = Number($(this).text());
            galleryList(targetPage, mngYear); // 현재 유지중인 연도로 검색
        }
    });

    // 4. 페이징 화살표(<<, <, >, >>) 클릭 이벤트
    $(document).on('click', '.paging>span', function() {
        const totalCnt = parseInt($('span.total').text().replace(/,/g, '')) || 0;
        const countPerPage = 12;
        const totalPage = Math.ceil(totalCnt / countPerPage);
        const id = $(this).attr('id');

        let arrPages = [];
        $('.paging>ol>li>a').each(function() {
            arrPages.push(Number($(this).text()));
        });

        if (id === 'first_page') { // <<
            galleryList(1, mngYear);
        } else if (id === 'prev_page') { // <
            const prevPage = Math.min(...arrPages) - 1;
            if(prevPage >= 1) galleryList(prevPage, mngYear);
        } else if (id === 'next_page') { // >
            const nextPage = Math.max(...arrPages) + 1;
            if(nextPage <= totalPage) galleryList(nextPage, mngYear);
        } else if (id === 'last_page') { // >>
            galleryList(totalPage, mngYear);
        }
    });
});

// ▼▼▼ 텍스트 검색 버튼 또는 엔터키 입력 시 호출 ▼▼▼
function f_gallery_text_search() {
    galleryList(1, mngYear);
}

// ▼▼▼ 메인 리스트 조회 API 함수 ▼▼▼
function galleryList(targetPageNum, targetYear) {
    if (isSearching) return;
    isSearching = true;

    const countPerPage = 12;
    let start = (targetPageNum - 1) * countPerPage;
    if(start < 0) start = 0;

    let link = document.location.href;
    let lang = link.includes('eng') ? 'EN' : 'KO';

    let searchText = $('#search_text').val();
    let condition = $('#search_box option:selected').val();

    let jsonObj = {
        pageNum: start,
        rows: countPerPage,
        mngYear: targetYear,
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
            let htmlArr = [];

            if(nvl(results, "") !== "" && results.length > 0) {
                $.each(results , function(i, item){
                    let title = (lang === 'KO') ? item.title : nvl(item.titleEn, item.title);

                    let writeDate = "";
                    if(item.writeDate) {
                        writeDate = item.writeDate.split(' ')[0].replaceAll('-','.');
                    }

                    let thumbnailSrc = '/img/sample_img.jpg';
                    let fileList = item.fileList;

                    if(nvl(fileList, "") !== "" && fileList.length > 0){
                        let firstFile = fileList[0];
                        let webPath = firstFile.fullFilePath.replace('/usr/local/tomcat/webapps', '');
                        let originalName = firstFile.fileName;
                        thumbnailSrc = '/file/imageView.do?path=' + encodeURIComponent(webPath) + '&fileName=' + encodeURIComponent(originalName);
                    }

                    let li = '<li style="cursor: pointer">';
                    li += '<a class="viewGallery">';
                    li += '<div class="thumb75 thumbBox">';
                    li += '<img class="thumbImg" loading="lazy" src="' + thumbnailSrc + '" alt="' + title + '">';

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

                // 전체 개수 세팅
                $('span.total').text(Number(results[0].totalRecords).toLocaleString());
                // 한 번에 화면에 그리기
                $('.board_gallery_box ul').empty().html(htmlArr.join(''));
                // 페이징 정보 세팅
                setPaging(targetPageNum);

            } else {
                // 검색된 데이터가 없을 경우
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
            isSearching = false; // 완료 후 무조건 락(Lock) 해제
        });
}

/**
 * 페이징 정보를 세팅합니다.
 */
function setPaging(currentPage) {
    const totalCnt = parseInt($('span.total').text().replace(/,/g, '')) || 0;
    const countPerPage = 12;
    const showPageCnt = 10;
    const totalPage = Math.ceil(totalCnt / countPerPage);

    showAllIcon();

    const startPage = Math.floor((currentPage - 1) / showPageCnt) * showPageCnt + 1;
    const endPage = startPage + showPageCnt - 1;

    // 이전/처음
    if (startPage === 1) {
        $('#first_page').hide();
        $('#prev_page').hide();
    }

    // 다음/마지막
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

// -------------------------------------------------------------
// nvl 헬퍼 함수
function nvl(value, defaultValue) {
    if (value === null || value === undefined || value === "" || value === "null" || (typeof value === "object" && !Object.keys(value || {}).length)) {
        return defaultValue;
    }
    return value;
}