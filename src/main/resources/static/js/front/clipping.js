$(function() {
    let currentPage = 1;
    const pageSize = 12;
    let currentFilterKeyword = '';

    // 1. 함수들을 먼저 선언합니다 (호이스팅 에러 방지)
    function loadCategoryKeywords() {
        $.ajax({
            url: '/api/clipping/keyword/list',
            type: 'GET',
            success: function(res) {
                if(res.resultCode === "0") {
                    let html = '<div id="keywordFilterArea" style="display: flex; flex-wrap: wrap; justify-content: center; gap: 10px; margin-top: 25px; clear: both; padding-top: 30px;">';
                    html += '<a href="javascript:void(0);" class="btnSt01 filter-btn" data-keyword="" style="padding:8px 20px; border-radius:50px; display:inline-block;">전체</a>';

                    res.list.forEach(function(item) {
                        html += '<a href="javascript:void(0);" class="btnSt04 filter-btn" data-keyword="' + item.keyword + '" style="padding:8px 20px; border-radius:50px; display:inline-block;">#' + item.keyword + '</a>';
                    });
                    html += '</div>';

                    if ($('#keywordFilterArea').length > 0) {
                        $('#keywordFilterArea').replaceWith(html);
                    } else {
                        $('.board_search .inner').append(html);
                    }
                }
            }
        });
    }

    function loadClippingList(page) {
        $.ajax({
            url: '/api/clipping/list',
            type: 'GET',
            data: {
                page: page,
                size: pageSize,
                searchType: $('#searchType').val(),
                searchKeyword: $('#searchKeyword').val(),
                filterKeyword: currentFilterKeyword,
                displayYn: 'Y' // 프론트엔드는 승인(게시)된 기사만 노출
            },
            success: function(res) {
                if(res.resultCode === "0") {
                    renderList(res.list);
                    renderPagination(res.totalCount, page);
                }
            }
        });
    }

    // 2. 외부 JSP(검색폼 제출 등)에서 호출할 수 있도록 window 객체에 브릿지 연결
    window.loadClippingList = loadClippingList;

    // 3. 초기 실행 (함수가 모두 준비된 후 실행되므로 안전함)
    loadCategoryKeywords();
    loadClippingList(currentPage);

    // ==========================================
    // 리스트 및 페이징 렌더링 로직
    // ==========================================
    function renderList(list) {
        let html = '';
        if (list.length === 0) {
            html = '<li style="width:100%; text-align:center; padding:50px 0;">조건에 맞는 클리핑 기사가 없습니다.</li>';
        } else {
            list.forEach(function(item) {
                let pureText = item.content.replace(/<[^>]*>?/gm, '');
                let shortText = pureText.length > 100 ? pureText.substring(0, 100) + '...' : pureText;
                let displayDate = item.regDate ? item.regDate.substring(0, 10) : '';

                html += '<li>';
                html += '  <a href="javascript:void(0);" class="front-view-btn" data-seq="' + item.seq + '">';
                html += '    <div class="tit">' + item.title + '</div>';
                html += '    <div class="desc">' + shortText + '</div>';
                html += '    <div class="date">' + displayDate + '</div>';
                html += '  </a>';
                html += '</li>';
            });
        }
        $('#clippingListBody').html(html);
    }

    function renderPagination(totalCount, currentPage) {
        let totalPages = Math.ceil(totalCount / pageSize);
        if(totalPages === 0) totalPages = 1;

        const pageBlockSize = 10;
        let currentBlock = Math.ceil(currentPage / pageBlockSize);
        let startPage = (currentBlock - 1) * pageBlockSize + 1;
        let endPage = startPage + pageBlockSize - 1;

        if (endPage > totalPages) endPage = totalPages;

        let html = '';
        for (let i = startPage; i <= endPage; i++) {
            if(i === currentPage) {
                html += '<li><a href="javascript:void(0);" class="this">' + i + '</a></li>';
            } else {
                html += '<li><a href="javascript:void(0);" class="other front-page-btn" data-page="' + i + '">' + i + '</a></li>';
            }
        }
        $('#paginationArea').html(html);

        $('#first_page').off('click').on('click', function() { changePage(1); });
        $('#prev_page').off('click').on('click', function() { if(currentPage > 1) changePage(currentPage - 1); });
        $('#next_page').off('click').on('click', function() { if(currentPage < totalPages) changePage(currentPage + 1); });
        $('#last_page').off('click').on('click', function() { changePage(totalPages); });
    }

    function changePage(page) {
        currentPage = page;
        loadClippingList(page);
    }

    // ==========================================
    // 이벤트 위임 (동적 생성 요소 처리)
    // ==========================================
    $(document).on('click', '.filter-btn', function() {
        $('.filter-btn').removeClass('btnSt01').addClass('btnSt04');
        $(this).removeClass('btnSt04').addClass('btnSt01');
        currentFilterKeyword = $(this).data('keyword');
        changePage(1);
    });

    $(document).on('click', '.front-page-btn', function() {
        changePage($(this).data('page'));
    });

    $(document).on('click', '.front-view-btn', function() {
        let seq = $(this).data('seq');
        $.post('/api/clipping/view/' + seq, function() {
            location.href = '/board/clippingView.do?seq=' + seq;
        });
    });
});