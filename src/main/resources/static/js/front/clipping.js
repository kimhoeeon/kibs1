$(function() {
    let currentPage = 1;
    const pageSize = 12;

    loadClippingList(currentPage);

    function loadClippingList(page) {
        $.ajax({
            url: '/api/clipping/list',
            type: 'GET',
            data: { page: page, size: pageSize },
            success: function(res) {
                if(res.resultCode === "0") {
                    renderList(res.list);
                    renderPagination(res.totalCount, page);
                }
            }
        });
    }

    function renderList(list) {
        let html = '';
        if (list.length === 0) {
            html = '<li style="width:100%; text-align:center; padding:50px 0;">생성된 AI 클리핑 기사가 없습니다.</li>';
        } else {
            list.forEach(function(item) {
                // HTML 태그 제거 후 텍스트만 추출 (최대 100자)
                let pureText = item.content.replace(/<[^>]*>?/gm, '');
                let shortText = pureText.length > 100 ? pureText.substring(0, 100) + '...' : pureText;

                // 표시 날짜 포맷 (YYYY-MM-DD)
                let displayDate = item.regDate ? item.regDate.substring(0, 10) : '';

                html += '<li>';
                // 클릭 시 조회수 증가 및 상세보기 페이지 이동 (만약 상세 페이지가 없다면 모달이나 펼쳐보기 처리 가능)
                html += '  <a href="javascript:void(0);" onclick="viewClipping(\'' + item.seq + '\')">';
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

        let html = '';
        for (let i = 1; i <= totalPages; i++) {
            if(i === currentPage) {
                html += '<li><a href="javascript:void(0);" class="this">' + i + '</a></li>';
            } else {
                html += '<li><a href="javascript:void(0);" class="other" onclick="changePage(' + i + ')">' + i + '</a></li>';
            }
        }
        $('#paginationArea').html(html);

        // 이전/다음 등 버튼 이벤트 매핑
        $('#first_page').off('click').on('click', function() { changePage(1); });
        $('#prev_page').off('click').on('click', function() { if(currentPage > 1) changePage(currentPage - 1); });
        $('#next_page').off('click').on('click', function() { if(currentPage < totalPages) changePage(currentPage + 1); });
        $('#last_page').off('click').on('click', function() { changePage(totalPages); });
    }

    window.changePage = function(page) {
        currentPage = page;
        loadClippingList(page);
    };

    window.viewClipping = function(seq) {
        // 조회수 증가 API 호출
        $.post('/api/clipping/view/' + seq, function() {
            // 상세 페이지가 구축되어 있다면 이동
            location.href = '/board/clippingView.do?seq=' + seq;
        });
    };
});