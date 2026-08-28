$(function() {
    // [메인 목록] 페이징 변수
    let currentPage = 1;
    const pageSize = 10;

    // [발송 이력] 페이징 변수
    let currentHistoryPage = 1;
    const historyPageSize = 10;

    // 초기 목록 로드
    loadClippingList(currentPage);

    $('#searchTitle').on('keyup', function(e) {
        if (e.key === 'Enter') {
            currentPage = 1;
            loadClippingList(currentPage);
        }
    });

    /* =========================================================
       1. 메인 AI 클리핑 목록 및 페이징 로직
    ========================================================= */
    function loadClippingList(page) {
        $.ajax({
            url: '/api/clipping/list',
            type: 'GET',
            data: { page: page, size: pageSize, title: $('#searchTitle').val() },
            success: function(res) {

                $('#clippingTotalCount').text(res.totalCount || 0);

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

                // 주석 해제 및 메인 페이징 렌더링 호출
                renderClippingPagination(res.totalCount, page, pageSize);
            }
        });
    }

    // 메인 목록 전용 페이징 렌더러
    function renderClippingPagination(totalCount, currentPage, pageSize) {
        let totalPages = Math.ceil(totalCount / pageSize);
        let paginationHtml = '<ul class="pagination">';

        if (totalPages === 0) {
            $('#clippingPagination').html('');
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
        $('#clippingPagination').html(paginationHtml);
    }

    // 메인 페이지 번호 클릭 이벤트
    $(document).on('click', '#clippingPagination .page-link', function(e) {
        e.preventDefault();
        let page = $(this).data('page');
        if (page) {
            currentPage = page;
            loadClippingList(currentPage);
        }
    });

    /* =========================================================
       2. 클리핑 상세/수정/삭제 모달 로직
    ========================================================= */
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

    /* =========================================================
       3. AI 클리핑 수동 생성 로직
    ========================================================= */
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
       4. 뉴스레터 발송 이력 (모달 팝업 페이징) 로직
    ========================================================= */
    $(document).on('click', '.btn-history', function() {
        let seq = $(this).data('seq');
        $('#historyClippingSeq').val(seq);
        $('#filterSendResult').val('');
        currentHistoryPage = 1;
        loadHistoryList(seq, currentHistoryPage);
        new bootstrap.Modal(document.getElementById('modalSendHistory')).show();
    });

    // 셀렉트 박스 변경 시 이력 다시 불러오기
    $(document).on('change', '#filterSendResult', function() {
        let seq = $('#historyClippingSeq').val();
        currentHistoryPage = 1; // 필터를 바꾸면 1페이지부터 다시 보여줌
        loadHistoryList(seq, currentHistoryPage);
    });

    function loadHistoryList(clippingSeq, page) {
        let sendResult = $('#filterSendResult').val(); // 선택된 필터 값 가져오기

        $.ajax({
            url: '/mng/center/board/clipping/history.ajax',
            type: 'GET',
            data: { clippingSeq: clippingSeq, sendResult: sendResult, page: page, size: historyPageSize }, // 파라미터 추가
            success: function(res) {
                let html = '';
                if(res.list.length === 0){
                    html = '<tr><td colspan="5" class="text-center text-muted py-10">해당 조건의 발송 이력이 없습니다.</td></tr>';
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

    $(document).on('click', '#historyPagination .page-link', function(e) {
        e.preventDefault();
        let page = $(this).data('page');
        if (page) {
            currentHistoryPage = page;
            let seq = $('#historyClippingSeq').val();
            loadHistoryList(seq, currentHistoryPage);
        }
    });

    /* =========================================================
       5. 키워드 관리 (팝업 및 추가/삭제) 로직
    ========================================================= */
    // 키워드 관리 버튼 클릭
    $('#btnKeywordMng').on('click', function() {
        $('#newKeyword').val('');
        loadKeywordList();
        new bootstrap.Modal(document.getElementById('modalKeywordMng')).show();
    });

    // 엔터키 입력 지원
    $('#newKeyword').on('keyup', function(e) {
        if (e.key === 'Enter') {
            $('#btnAddKeyword').click();
        }
    });

    // 키워드 목록 로드
    function loadKeywordList() {
        $.ajax({
            url: '/mng/center/board/clipping/keyword/list.ajax',
            type: 'GET',
            success: function(res) {
                let html = '';
                if(res.resultCode === "0") {

                    $('#keywordTotalCount').text(res.list.length);

                    if(res.list.length === 0){
                        html = '<tr><td class="text-center text-muted py-5">등록된 키워드가 없습니다.</td></tr>';
                    } else {
                        res.list.forEach(function(item) {
                            html += '<tr>';
                            html += '  <td class="ps-2"><div class="d-flex align-items-center"><i class="ki-duotone ki-right-square fs-3 text-primary me-2"><span class="path1"></span><span class="path2"></span></i><span>' + item.keyword + '</span></div></td>';
                            html += '  <td class="text-end pe-2"><button type="button" class="btn btn-sm btn-icon btn-light-danger btn-delete-keyword" data-seq="' + item.seq + '"><i class="ki-duotone ki-trash fs-5"><span class="path1"></span><span class="path2"></span><span class="path3"></span><span class="path4"></span><span class="path5"></span></i></button></td>';
                            html += '</tr>';
                        });
                    }
                    $('#keywordListBody').html(html);
                } else {
                    alert(res.resultMsg);
                }
            }
        });
    }

    // 키워드 추가 (중복 검사는 백엔드에서 처리)
    $('#btnAddKeyword').on('click', function() {
        let keyword = $('#newKeyword').val().trim();
        if(!keyword) {
            alert("키워드를 입력해주세요.");
            $('#newKeyword').focus();
            return;
        }

        $.ajax({
            url: '/mng/center/board/clipping/keyword/add.do',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ keyword: keyword }),
            success: function(res) {
                if(res.resultCode === "0") {
                    $('#newKeyword').val('');
                    loadKeywordList(); // 목록 즉시 갱신
                } else {
                    // 중복 등록 불가 alert 출력
                    alert(res.resultMsg);
                }
            },
            error: function() {
                alert("통신 중 오류가 발생했습니다.");
            }
        });
    });

    // 키워드 삭제
    $(document).on('click', '.btn-delete-keyword', function() {
        let seq = $(this).data('seq');

        if(confirm("해당 키워드를 정말 삭제하시겠습니까?")) {
            $.ajax({
                url: '/mng/center/board/clipping/keyword/delete.do',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ seq: seq }),
                success: function(res) {
                    if(res.resultCode === "0") {
                        loadKeywordList();
                    } else {
                        alert(res.resultMsg);
                    }
                }
            });
        }
    });
});