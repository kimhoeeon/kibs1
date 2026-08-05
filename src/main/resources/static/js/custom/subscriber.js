$(function() {
    let currentPage = 1;
    const pageSize = 10;

    // 초기 로드
    loadSubscriberList(currentPage);

    // 검색 및 초기화 버튼
    $('#btnSearch').on('click', function() {
        currentPage = 1;
        loadSubscriberList(currentPage);
    });

    $('#btnReset').on('click', function() {
        $('#searchText').val('');
        $('#receiveStatus').val('전체');
        $('#startDate').val('');
        $('#endDate').val('');
        currentPage = 1;
        loadSubscriberList(currentPage);
    });

    // 전체 체크박스 제어
    $('#checkAll').on('change', function() {
        $('.chk-item').prop('checked', this.checked);
    });

    // 선택 삭제
    $('#btnDeleteSelected').on('click', function() {
        let selectedSeqs = [];
        $('.chk-item:checked').each(function() {
            selectedSeqs.push($(this).val());
        });

        if (selectedSeqs.length === 0) {
            alert("삭제할 구독자를 선택해주세요.");
            return;
        }

        if (confirm("선택한 구독자를 완전히 삭제하시겠습니까?")) {
            // (퍼블리셔 연동을 위해 임시로 AJAX 구현)
            $.ajax({
                url: '/mng/center/board/subscriber/delete.do', // 해당 API 필요
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(selectedSeqs),
                success: function(res) {
                    alert("삭제가 완료되었습니다.");
                    loadSubscriberList(currentPage);
                    $('#checkAll').prop('checked', false);
                }
            });
        }
    });

    // 선택 수신거부 처리
    $('#btnRejectSelected').on('click', function() {
        let selectedSeqs = [];
        $('.chk-item:checked').each(function() {
            selectedSeqs.push($(this).val());
        });

        if (selectedSeqs.length === 0) {
            alert("수신거부 처리할 구독자를 선택해주세요.");
            return;
        }

        if (confirm("선택한 구독자를 수신거부 상태로 변경하시겠습니까?")) {
            $.ajax({
                url: '/mng/center/board/subscriber/reject.do', // 해당 API 필요
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(selectedSeqs),
                success: function(res) {
                    alert("수신거부 처리가 완료되었습니다.");
                    loadSubscriberList(currentPage);
                    $('#checkAll').prop('checked', false);
                }
            });
        }
    });

    // 엑셀 다운로드 (기존 프로젝트 엑셀 모듈과 연결)
    $('#btnExcelDownload').on('click', function() {
        let form = $('#searchForm').serialize();
        window.location.href = '/mng/center/board/subscriber/excelDownload.do?' + form;
    });

    function loadSubscriberList(page) {
        let reqData = {
            page: page,
            size: pageSize,
            searchText: $('#searchText').val(),
            receiveStatus: $('#receiveStatus').val(),
            startDate: $('#startDate').val(),
            endDate: $('#endDate').val()
        };

        // 관리자 API 호출 (구현 필요)
        $.ajax({
            url: '/mng/api/newsletter/subscriber/list',
            type: 'GET',
            data: reqData,
            success: function(res) {
                renderTable(res.list);
                renderPagination(res.totalCount, page);
            }
        });
    }

    function renderTable(list) {
        let html = '';
        if (!list || list.length === 0) {
            html = '<tr><td colspan="8" class="text-center text-muted py-10">구독자 내역이 없습니다.</td></tr>';
        } else {
            list.forEach(function(item) {
                let statusBadge = item.receiveStatus === '수신중' ? '<span class="badge badge-light-success">수신중</span>' : '<span class="badge badge-light-danger">수신거부</span>';

                html += '<tr>';
                html += '  <td><div class="form-check form-check-sm form-check-custom form-check-solid"><input class="form-check-input chk-item" type="checkbox" value="' + item.seq + '"/></div></td>';
                html += '  <td class="text-gray-800 fw-bold">' + item.name + '</td>';
                html += '  <td>' + item.email + '</td>';
                html += '  <td>' + (item.privacyAgreeYn === 'Y' ? '동의' : '미동의') + '</td>';
                html += '  <td>' + (item.adAgreeYn === 'Y' ? '동의' : '미동의') + '</td>';
                html += '  <td>' + item.regDate + '</td>';
                html += '  <td>' + (item.lastSendDate ? item.lastSendDate : '-') + '</td>';
                html += '  <td>' + statusBadge + '</td>';
                html += '</tr>';
            });
        }
        $('#listBody').html(html);
    }

    function renderPagination(totalCount, currentPage) {
        // 기존 시스템에서 사용하시던 페이징 모듈 또는 간단한 부트스트랩 페이징 생성 로직 (생략 시 기본 1 구현)
        let totalPages = Math.ceil(totalCount / pageSize);
        let html = '<ul class="pagination">';
        for (let i = 1; i <= totalPages; i++) {
            let activeStr = (i === currentPage) ? 'active' : '';
            html += '<li class="page-item ' + activeStr + '"><a href="javascript:void(0);" class="page-link" onclick="changePage(' + i + ')">' + i + '</a></li>';
        }
        html += '</ul>';
        $('#paginationArea').html(html);
    }

    window.changePage = function(page) {
        currentPage = page;
        loadSubscriberList(page);
    };
});