$(function () {
    const $form = $('#brochure_form');
    const $submitBtn = $('#brochure_submit_btn');

    /**
     * 공통으로 사용할 Swal (SweetAlert) 유효성 검사 알림 함수
     * @param {string} message - 사용자에게 보여줄 메시지
     */
    const showValidationError = (message) => {
        Swal.fire({
            icon: 'warning',
            title: '입력 오류',
            text: message,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        });
    };

    /**
     * [수정] 파일 첨부 시 파일명(확장자 포함) 자동 입력 및 PDF 확장자 검사
     */
    $('.brochure-file-input').on('change', function() {
        const fileInput = this;
        const $fileInput = $(this);
        // 'this.id' (예: "mainKoFile")를 사용하여 연결된 텍스트 입력을 찾습니다.
        const $titleInput = $('input[data-file-input-id="' + this.id + '"]');

        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            const fileName = file.name; // 예: "brochure.pdf"

            // 1. PDF 확장자 검사
            if (!fileName.toLowerCase().endsWith('.pdf')) {
                showValidationError('PDF 파일만 첨부할 수 있습니다.\n\n파일: ' + fileName);
                $fileInput.val(''); // 잘못된 파일 첨부 초기화

                // 만약 텍스트 필드가 이전에 자동 입력되었다면, 비워줍니다.
                if ($titleInput.data('auto-filled') === true) {
                    $titleInput.val('');
                    $titleInput.data('auto-filled', false);
                }
                return;
            }

            // 2. 파일명 자동 입력
            // 텍스트 박스가 비어있을 경우에만 자동 입력
            if ($titleInput.val().trim() === '') {
                // --- ▼▼▼ [핵심 수정] ▼▼▼ ---
                // 확장자를 포함한 전체 파일명을 입력합니다.
                $titleInput.val(fileName);
                // --- ▲▲▲▲▲▲▲▲▲▲▲▲▲ ---
                $titleInput.data('auto-filled', true); // 자동 입력되었음을 표시
            }
        } else {
            // 파일 선택이 취소된 경우, 자동 입력된 텍스트를 지웁니다.
            if ($titleInput.data('auto-filled') === true) {
                $titleInput.val('');
                $titleInput.data('auto-filled', false);
            }
        }
    });

    /**
     * [신규] 사용자가 텍스트를 직접 수정하면, 자동 입력 상태를 해제합니다.
     */
    $('.brochure-title').on('input', function() {
        $(this).data('auto-filled', false);
    });

    /**
     * 1. '저장' 버튼 클릭 시 유효성 검사
     */
    $form.on('submit', function (e) {

        // [참고] PDF 확장자 검사는 'change' 이벤트에서 이미 처리되었으므로
        // submit 시점에서는 파일명(title)과 파일 존재 여부(required)만 검사합니다.

        // --- 1. 필수 값 가져오기 ---
        const mainKoTitle = $('input[name="mainKoTitle"]').val();
        const mainEnTitle = $('input[name="mainEnTitle"]').val();

        const mainKoFile = $('input[name="mainKoFile"]')[0].files;
        const mainEnFile = $('input[name="mainEnFile"]')[0].files;

        const mainKoFileExisting = $('input[name="mainKoFile_existing"]').val();
        const mainEnFileExisting = $('input[name="mainEnFile_existing"]').val();

        // --- 2. 유효성 검사 (required 항목) ---

        // 2-1. 메인 국문 브로슈어 (제목)
        if (!mainKoTitle || mainKoTitle.trim() === '') {
            e.preventDefault();
            showValidationError('메인 국문 브로슈어의 파일명을 입력해주세요.');
            $('input[name="mainKoTitle"]').focus();
            return;
        }

        // 2-2. 메인 국문 브로슈어 (파일)
        if (mainKoFile.length === 0 && (!mainKoFileExisting || mainKoFileExisting === '')) {
            e.preventDefault();
            showValidationError('메인 국문 브로슈어 파일을 첨부해주세요.');
            $('input[name="mainKoFile"]').focus();
            return;
        }

        // 2-3. 메인 영문 브로슈어 (제목)
        if (!mainEnTitle || mainEnTitle.trim() === '') {
            e.preventDefault();
            showValidationError('메인 영문 브로슈어의 파일명을 입력해주세요.');
            $('input[name="mainEnTitle"]').focus();
            return;
        }

        // 2-4. 메인 영문 브로슈어 (파일)
        if (mainEnFile.length === 0 && (!mainEnFileExisting || mainEnFileExisting === '')) {
            e.preventDefault();
            showValidationError('메인 영문 브로슈어 파일을 첨부해주세요.');
            $('input[name="mainEnFile"]').focus();
            return;
        }

        // 모든 유효성 검사 통과 시
        $submitBtn.attr('data-kt-indicator', 'on').prop('disabled', true);
    });

    /**
     * 2. '초기화' 버튼 클릭 시 확인창
     */
    $form.find('button[type="reset"]').on('click', function (e) {
        e.preventDefault();

        Swal.fire({
            icon: 'warning',
            title: '초기화 확인',
            text: '작성 중인 내용을 모두 초기화하시겠습니까?',
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            cancelButtonColor: '#f1416c',
            confirmButtonText: '네, 초기화합니다.',
            cancelButtonText: '아니요'
        }).then((result) => {
            if (result.isConfirmed) {
                $form[0].reset();
                // 자동 입력 상태도 모두 초기화
                $('.brochure-title').data('auto-filled', false);
            }
        });
    });

});