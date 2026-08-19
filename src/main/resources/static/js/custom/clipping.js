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
                        html += '  <td class="date">' + item.regDate + '</td>';
                        html += '  <td><button class="btn btn-sm btn-light-info btn-edit" data-seq="' + item.seq + '" data-title="' + item.title + '">수정 / 삭제</button></td>';
                        html += '</tr>';
                    });
                }
                $('#clippingListBody').html(html);
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
    $('#btnManualUpdate').off('click').on('click', function() {
        // 1. 오늘 날짜 생성 여부 프론트엔드 즉시 검사
        let today = new Date();
        let year = today.getFullYear();
        let month = String(today.getMonth() + 1).padStart(2, '0');
        let day = String(today.getDate()).padStart(2, '0');
        let todayStr = year + '-' + month + '-' + day; // YYYY-MM-DD 형식

        let isTodayExist = false;
        // 화면에 렌더링된 기사 날짜들을 순회하며 검사
        $('#clippingListBody .date').each(function() {
            if ($(this).text().trim() === todayStr) {
                isTodayExist = true;
            }
        });

        // 오늘 날짜 기사가 이미 있다면 팝업을 띄우지 않고 경고창 후 종료
        if (isTodayExist) {
            alert("오늘 날짜의 AI 클리핑 기사가 이미 존재합니다.\n새로 생성하시려면 기존 기사를 먼저 삭제해 주세요.");
            return;
        }

        // 2. 옵션 선택 팝업(모달) HTML 동적 생성
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

        // 이벤트: 취소 버튼
        $('#btnGenCancel').on('click', function() {
            $('#aiOptionOverlay').remove();
        });

        // 이벤트: 생성만 하기 버튼 클릭 (sendYn = 'N')
        $('#btnGenOnly').on('click', function() {
            $('#aiOptionOverlay').remove();
            executeManualUpdate('N');
        });

        // 이벤트: 생성 및 발송 버튼 클릭 (sendYn = 'Y')
        $('#btnGenAndSend').on('click', function() {
            $('#aiOptionOverlay').remove();
            executeManualUpdate('Y');
        });
    });

    // 실제 백엔드 통신 및 로딩 인디케이터 처리 함수
    function executeManualUpdate(sendYn) {
        // 로딩 인디케이터 HTML 동적 생성
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
            data: { sendYn: sendYn }, // 'Y' 또는 'N' 전달
            success: function(res) {
                $('#aiLoadingOverlay').remove(); // 로딩 제거

                if(res.resultCode === "0") {
                    alert(res.resultMsg); // 상황에 맞는 성공 메시지 출력
                    currentPage = 1;
                    loadClippingList(currentPage); // 리스트 갱신
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
                        loadClippingList(currentPage); // 발송 횟수 갱신을 위해 리스트 리로드
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
});