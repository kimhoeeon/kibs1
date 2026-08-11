$(function() {
    let currentPage = 1;
    const pageSize = 10;

    loadClippingList(currentPage);

    $('#searchTitle').on('keyup', function(e) {
        if (e.key === 'Enter') {
            currentPage = 1;
            loadClippingList(currentPage);
        }
    });

    function loadClippingList(page) {
        $.ajax({
            url: '/api/clipping/list',
            type: 'GET',
            data: { page: page, size: pageSize, title: $('#searchTitle').val() },
            success: function(res) {
                let html = '';
                if(res.list.length === 0){
                    html = '<tr><td colspan="7" class="text-center text-muted py-10">생성된 클리핑 기사가 없습니다.</td></tr>';
                } else {
                    res.list.forEach(function(item, index) {
                        let no = res.totalCount - ((page - 1) * pageSize) - index;
                        let sendStatus = item.sendSuccessCnt + "건 발송 (" + item.sendFailCnt + " 실패)";

                        html += '<tr>';
                        html += '  <td>' + no + '</td>';
                        html += '  <td class="text-start text-dark fw-bold">' + item.title + '</td>';
                        html += '  <td>' + item.viewCnt + '</td>';
                        html += '  <td>' + item.shareCnt + '</td>';
                        html += '  <td><span class="badge badge-light-primary">' + sendStatus + '</span></td>';
                        html += '  <td>' + item.regDate + '</td>';
                        html += '  <td><button class="btn btn-sm btn-light-info btn-edit" data-seq="' + item.seq + '" data-title="' + item.title + '">수정 / 삭제</button></td>';
                        html += '</tr>';
                    });
                }
                $('#clippingListBody').html(html);

                // Content 데이터를 html attribute에 넣으면 깨질 수 있으므로 상세조회 API를 붙이거나 모달 클릭시 처리
            }
        });
    }

    // 상세/수정 모달 열기
    $(document).on('click', '.btn-edit', function() {
        let seq = $(this).data('seq');
        // 상세 데이터 조회 후 모달에 세팅 (구현 API 호출)
        $.ajax({
            url: '/mng/center/board/clipping/detail.ajax?seq=' + seq,
            type: 'GET',
            success: function(res) {
                $('#editSeq').val(res.data.seq);
                $('#editTitle').val(res.data.title);
                $('#editContent').val(res.data.content);
                new bootstrap.Modal(document.getElementById('modalClippingDetail')).show();
            }
        });
    });

    // 클리핑 저장 (수정)
    $('#btnSaveClipping').on('click', function() {
        let seq = $('#editSeq').val();
        let title = $('#editTitle').val();
        let content = $('#editContent').val();

        $.ajax({
            url: '/mng/center/board/clipping/update.do',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ seq: seq, title: title, content: content }),
            success: function(res) {
                alert("수정되었습니다.");
                bootstrap.Modal.getInstance(document.getElementById('modalClippingDetail')).hide();
                loadClippingList(currentPage);
            }
        });
    });

    // 클리핑 삭제
    $('#btnDeleteClipping').on('click', function() {
        if(confirm("해당 기사를 정말 삭제하시겠습니까?")) {
            let seq = $('#editSeq').val();
            $.ajax({
                url: '/mng/center/board/clipping/delete.do',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ seq: seq }),
                success: function(res) {
                    alert("삭제되었습니다.");
                    bootstrap.Modal.getInstance(document.getElementById('modalClippingDetail')).hide();
                    loadClippingList(currentPage);
                }
            });
        }
    });

    // AI 클리핑 수동 생성 (업데이트)
    $('#btnManualUpdate').on('click', function() {
        if(!confirm("AI 클리핑을 수동으로 생성하시겠습니까?\n기사 수집 및 AI 요약에 약 10~20초 정도 소요될 수 있습니다.")) {
            return;
        }

        let $btn = $(this);
        let originalHtml = $btn.html();

        // 중복 클릭 방지 및 로딩 상태 표시
        $btn.prop('disabled', true).text('기사 수집 및 요약 중...');

        $.ajax({
            url: '/mng/center/board/clipping/manual-update.do',
            type: 'POST',
            success: function(res) {
                if(res.resultCode === "0") {
                    alert("AI 클리핑이 성공적으로 생성 및 구독자에게 발송되었습니다.");
                    currentPage = 1;
                    loadClippingList(currentPage); // 생성 완료 후 목록 갱신
                } else {
                    alert(res.resultMsg || "생성 중 오류가 발생했습니다.");
                }
            },
            error: function() {
                alert("서버 통신 중 오류가 발생했습니다.");
            },
            complete: function() {
                // 통신 완료 후 버튼 원상복구
                $btn.prop('disabled', false).html(originalHtml);
            }
        });
    });
});