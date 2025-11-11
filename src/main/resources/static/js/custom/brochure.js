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
     * 1. 파일 첨부 시: 파일명(확장자 포함) 자동 입력 및 PDF 확장자 검사
     */
    $('.brochure-file-input').on('change', function() {
        const fileInput = this;
        const $fileInput = $(this);
        // 'this.id' (예: "mainKoFile")를 사용하여 연결된 텍스트 입력을 찾습니다.
        const $titleInput = $('input[data-file-input-id="' + this.id + '"]');

        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            const fileName = file.name; // 예: "brochure.pdf"

            // 1-1. PDF 확장자 검사
            if (!fileName.toLowerCase().endsWith('.pdf')) {
                showValidationError('PDF 파일만 첨부할 수 있습니다.\n\n파일: ' + fileName);
                $fileInput.val(''); // 잘못된 파일 첨부 초기화

                if ($titleInput.data('auto-filled') === true) {
                    $titleInput.val('');
                    $titleInput.data('auto-filled', false);
                }
                return;
            }

            // 1-2. 파일명 자동 입력 (텍스트 박스가 비어있을 경우)
            if ($titleInput.val().trim() === '') {
                $titleInput.val(fileName); // 확장자 포함 전체 파일명
                $titleInput.data('auto-filled', true); // 자동 입력되었음을 표시
            }
        } else {
            // 1-3. 파일 선택이 취소된 경우
            if ($titleInput.data('auto-filled') === true) {
                $titleInput.val('');
                $titleInput.data('auto-filled', false);
            }
        }
    });

    /**
     * 2. 수기 입력 시: .pdf 확장자 자동 추가 (on blur)
     */
    $('.brochure-title').on('blur', function() {
        const $input = $(this);
        let currentValue = $input.val().trim();

        // 1. 값이 없으면 아무것도 안 함
        if (currentValue.length === 0) {
            return;
        }

        // 2. 마지막 '.'의 위치를 찾음
        const lastDotIndex = currentValue.lastIndexOf('.');

        // 3. '.'이 존재하고, 그 뒤의 확장자가 'pdf'가 아닌 경우
        if (lastDotIndex > -1) { // '.'이 하나라도 있다면
            const extension = currentValue.substring(lastDotIndex + 1);

            if (extension.toLowerCase() !== 'pdf') {
                // 확장자가 'pdf'가 아니면(예: .jpg, .backup, 또는 그냥 .)
                // 마지막 '.'부터 끝까지 모두 제거
                currentValue = currentValue.substring(0, lastDotIndex);
            }
        }

        // 4. 정리된 값의 끝이 .pdf가 아니면 붙여줌
        // (값이 비어있지 않으면서, .pdf로 끝나지 않는 경우)
        currentValue = currentValue.trim(); // (예: 'test.' 입력 시 'test'만 남음)
        if (currentValue.length > 0 && !currentValue.toLowerCase().endsWith('.pdf')) {
            currentValue = currentValue + '.pdf';
        }

        $input.val(currentValue); // 최종 정리된 값으로 업데이트
        $input.data('auto-filled', false); // 사용자가 수정한 것으로 간주
    });

    /**
     * 3. 수기 입력 시: 자동 입력 상태 해제 (on input)
     */
    $('.brochure-title').on('input', function() {
        // 사용자가 한 글자라도 직접 입력하면, 자동 입력 상태를 해제
        $(this).data('auto-filled', false);
    });

    /**
     * 4. '저장' 버튼 클릭 시 유효성 검사
     */
    $form.on('submit', function (e) {

        // --- 필수 값 가져오기 ---
        const mainKoTitle = $('input[name="mainKoTitle"]').val();
        const mainEnTitle = $('input[name="mainEnTitle"]').val();

        const mainKoFile = $('input[name="mainKoFile"]')[0].files;
        const mainEnFile = $('input[name="mainEnFile"]')[0].files;

        const mainKoFileExisting = $('input[name="mainKoFile_existing"]').val();
        const mainEnFileExisting = $('input[name="mainEnFile_existing"]').val();

        // --- 유효성 검사 (required 항목) ---

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

        // 모든 유효성 검사 통과 시 스피너 활성화
        $submitBtn.attr('data-kt-indicator', 'on').prop('disabled', true);
    });

    /**
     * 5. '초기화' 버튼 클릭 시 확인창
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