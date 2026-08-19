$(function() {
    let currentPage = 1;
    const pageSize = 10;

    // 발송 이력용 페이징 변수
    let currentHistoryPage = 1;
    const historyPageSize = 10;

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
                        html += '  <td><span class="badge badge-light-primary">' + sendStatus + '</span><br>';
                        html += '      <button class="btn btn-sm btn-light-success btn-history mt-2" data-seq="' + item.seq + '">이력보기</button></td>';
                        html += '  <td class="date">' + item.regDate + '</td>';
                        html += '  <td><button class="btn btn-sm btn-light-info btn-edit" data-seq="' + item.seq + '" data-title="' + item.title + '">수정 / 삭제</button></td>';
                        html += '</tr>';
                    });
                }
                $('#clippingListBody').html(html);
                // 메인 목록 페이징 렌더링 호출 로직 (기존 구현되어 있다고 가정)
                //renderClippingPagination(res.totalCount, page, pageSize);
            }
        });
    }

    // 상세/수정 모달 열기
    $(document).on('click', '.btn-edit', function() {
        let seq = $(this).data('seq');
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

    // AI 클리핑 수동 생성 (옵션 팝업 및 중복 방어)
    $('#btnManualUpdate').off('click').on('click', function() {
        let today = new Date();
        let year = today.getFullYear();
        let month = String(today.getMonth() + 1).padStart(2, '0');
        let day = String(today.getDate()).padStart(2, '0');
        let todayStr = year + '-' + month + '-' + day;

        let isTodayExist = false;
        $('#clippingListBody .date').each(function() {
            let regDateText = $(this).text().trim();
            if (regDateText.startsWith(todayStr)) {
                isTodayExist = true;
            }
        });

        if (isTodayExist) {
            alert("오늘 날짜의 AI 클리핑 기사가 이미 존재합니다.\n새로 생성하시려면 기존 기사를 먼저 삭제해 주세요.");
            return;
        }

        let popupHtml = `
            <div id="aiOptionOverlay" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); z-index: 9998; display: flex; justify-content: center; align-items: center;">
                <div style="background: #fff; padding: 30px; border-radius: 8px; width: 420px; text-align: center; box-shadow: 0 4px 15px rgba(0,0,0,0.2);">
                    <h3 style="margin-bottom: 20px; font-size: 20px; font-weight: bold; color: #333;">AI 클리핑 수동 생성</h3>
                    <p style="margin-bottom: 25px; color: #555; font-size: 15px; word-break: keep-all; line-height: 1.5;">
                        AI 클리핑 기사를 수집하고 요약합니다.<br>생성 후 구독자에게 뉴스레터를 즉시 발송하시겠습니까?
                    </p>
                    <div style="display: flex; justify-content: space-between; gap: 10px;">
                        <button type="button" id="btnGenOnly" style="flex: 1; padding: 12px; background: #6c757d; color: white; border: none; border-radius: 5px; font-size: 14px; font-weight: bold; cursor: pointer;">
                            생성만 하기
                        </button>
                        <button type="button" id="btnGenAndSend" style="flex: 1; padding: 12px; background: #0d6efd; color: white; border: none; border-radius: 5px; font-size: 14px; font-weight: bold; cursor: pointer;">
                            생성 및 발송 (기본)
                        </button>
                    </div>
                    <div style="margin-top: 15px;">
                        <button type="button" id="btnGenCancel" style="padding: 8px 20px; background: transparent; color: #888; border: 1px solid #ccc; border-radius: 5px; font-size: 13px; cursor: pointer;">
                            취소
                        </button>
                    </div>
                </div>
            </div>
        `;

        $('body').append(popupHtml);

        $('#btnGenCancel').on('click', function() { $('#aiOptionOverlay').remove(); });
        $('#btnGenOnly').on('click', function() { $('#aiOptionOverlay').remove(); executeManualUpdate('N'); });
        $('#btnGenAndSend').on('click', function() { $('#aiOptionOverlay').remove(); executeManualUpdate('Y'); });
    });

    // 실제 수동 생성 백엔드 통신
    function executeManualUpdate(sendYn) {
        let loadingHtml = `
            <div id="aiLoadingOverlay" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.8); z-index: 9999; display: flex; flex-direction: column; justify-content: center; align-items: center; color: white;">
                <div class="spinner-border text-primary" role="status" style="width: 4rem; height: 4rem; margin-bottom: 20px; border-width: 0.3em;">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <h3 style="color: white; font-weight: bold; margin-bottom: 15px; font-size: 24px;">AI 클리핑 기사를 생성하고 있습니다.</h3>
                <p style="font-size: 16px; color: #dddddd; text-align: center;">뉴스 수집 및 상세 요약에 30초 ~ 1분 정도 소요됩니다.<br>작업이 완료될 때까지 창을 닫거나 새로고침하지 마세요.</p>
            </div>
        `;
        $('body').append(loadingHtml);

        $.ajax({
            url: '/mng/center/board/clipping/manual-update.do',
            type: 'POST',
            data: { sendYn: sendYn },
            success: function(res) {
                $('#aiLoadingOverlay').remove();
                if(res.resultCode === "0") {
                    alert(res.resultMsg);
                    currentPage = 1;
                    loadClippingList(currentPage);
                } else {
                    alert(res.resultMsg || "생성 중 오류가 발생했습니다.");
                }
            },
            error: function() {
                $('#aiLoadingOverlay').remove();
                alert("서버 통신 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    }

    // 모달 내 기존 기사로 즉시 발송
    $('#btnSendNewsletter').on('click', function() {
        let seq = $('#editSeq').val();

        if(!seq) {
            alert("기사 정보가 없습니다.");
            return;
        }

        if(confirm("현재 보고 계신 기사 내용으로 뉴스레터를 즉시 발송하시겠습니까?")) {
            let originalText = $(this).text();
            $(this).prop('disabled', true).text('발송 중...');

            $.ajax({
                url: '/mng/center/board/clipping/send.do',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ seq: seq }),
                success: function(res) {
                    if(res.resultCode === "0") {
                        alert(res.resultMsg);
                        bootstrap.Modal.getInstance(document.getElementById('modalClippingDetail')).hide();
                        loadClippingList(currentPage);
                    } else {
                        alert(res.resultMsg || "발송에 실패했습니다.");
                    }
                },
                error: function() {
                    alert("서버 통신 중 오류가 발생했습니다.");
                },
                complete: function() {
                    $('#btnSendNewsletter').prop('disabled', false).text(originalText);
                }
            });
        }
    });

    /* =========================================================
       발송 이력 (모달 및 페이징) 기능 추가 부분
    ========================================================= */

    // 이력보기 버튼 클릭 시 모달 열기
    $(document).on('click', '.btn-history', function() {
        let seq = $(this).data('seq');
        $('#historyClippingSeq').val(seq);
        currentHistoryPage = 1;
        loadHistoryList(seq, currentHistoryPage);
        new bootstrap.Modal(document.getElementById('modalSendHistory')).show();
    });

    // 이력 목록 데이터를 AJAX로 불러오기
    function loadHistoryList(clippingSeq, page) {
        $.ajax({
            url: '/mng/center/board/clipping/history.ajax',
            type: 'GET',
            data: { clippingSeq: clippingSeq, page: page, size: historyPageSize },
            success: function(res) {
                let html = '';
                if(res.list.length === 0){
                    html = '<tr><td colspan="5" class="text-center text-muted py-10">해당 기사의 발송 이력이 없습니다.</td></tr>';
                } else {
                    res.list.forEach(function(item, index) {
                        let no = res.totalCount - ((page - 1) * historyPageSize) - index;
                        let badgeClass = item.sendResult === '발송성공' ? 'badge-light-success' : 'badge-light-danger';

                        html += '<tr>';
                        html += '  <td>' + no + '</td>';
                        html += '  <td class="text-start text-dark">' + item.subscriberEmail + '</td>';
                        html += '  <td><span class="badge ' + badgeClass + '">' + item.sendResult + '</span></td>';
                        html += '  <td class="text-start">' + (item.sendResultMsg || '-') + '</td>';
                        html += '  <td>' + item.sendDate + '</td>';
                        html += '</tr>';
                    });
                }
                $('#historyListBody').html(html);
                renderHistoryPagination(res.totalCount, page, historyPageSize);
            }
        });
    }

    // 이력 팝업 전용 페이징 렌더러
    function renderHistoryPagination(totalCount, currentPage, pageSize) {
        let totalPages = Math.ceil(totalCount / pageSize);
        let paginationHtml = '<ul class="pagination">';

        if (totalPages === 0) {
            $('#historyPagination').html('');
            return;
        }

        let startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
        let endPage = Math.min(startPage + 4, totalPages);

        if (currentPage > 1) {
            paginationHtml += '<li class="page-item previous"><a href="#" class="page-link" data-page="' + (currentPage - 1) + '"><i class="previous"></i></a></li>';
        } else {
            paginationHtml += '<li class="page-item previous disabled"><a href="#" class="page-link"><i class="previous"></i></a></li>';
        }

        for (let i = startPage; i <= endPage; i++) {
            let activeClass = (i === currentPage) ? 'active' : '';
            paginationHtml += '<li class="page-item ' + activeClass + '"><a href="#" class="page-link" data-page="' + i + '">' + i + '</a></li>';
        }

        if (currentPage < totalPages) {
            paginationHtml += '<li class="page-item next"><a href="#" class="page-link" data-page="' + (currentPage + 1) + '"><i class="next"></i></a></li>';
        } else {
            paginationHtml += '<li class="page-item next disabled"><a href="#" class="page-link"><i class="next"></i></a></li>';
        }

        paginationHtml += '</ul>';
        $('#historyPagination').html(paginationHtml);
    }

    // 이력 팝업 페이지 번호 클릭 이벤트
    $(document).on('click', '#historyPagination .page-link', function(e) {
        e.preventDefault();
        let page = $(this).data('page');
        if (page) {
            currentHistoryPage = page;
            let seq = $('#historyClippingSeq').val();
            loadHistoryList(seq, currentHistoryPage);
        }
    });
});