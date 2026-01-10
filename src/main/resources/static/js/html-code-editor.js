"use strict";

// Class definition
var KTHtmlCodeEditor = function () {

    // Private functions
    const initCodeEditor = () => {
        // 1. Ace Editor 초기화 (ID: code_editor)
        var editor = ace.edit("code_editor");

        // 2. 에디터 설정
        editor.setTheme("ace/theme/monokai"); // 테마 설정 (어두운 배경: monokai, 밝은 배경: chrome)
        editor.session.setMode("ace/mode/html"); // HTML 문법 강조 모드
        editor.setOptions({
            fontSize: "14px",          // 폰트 크기
            showPrintMargin: false,    // 프린트 여백 선 숨김
            wrap: true,                // 줄바꿈 허용 (가로 스크롤 방지)
            indentedSoftWrap: false,
            behavioursEnabled: true,   // 괄호/태그 자동 닫기 등
            tabSize: 4                 // 탭 크기
        });

        // 3. 기존 데이터 불러오기 (수정 모드일 경우)
        var hiddenInput = document.getElementById('html_content');
        if (hiddenInput && hiddenInput.value) {
            editor.setValue(hiddenInput.value, -1); // -1: 커서를 맨 앞으로
        }

        // 4. 내용 변경 이벤트 감지 -> hidden input 및 미리보기 동기화
        editor.session.on('change', function(delta) {
            var code = editor.getValue();

            // 1) DB 전송용 hidden input 업데이트
            if(hiddenInput) {
                hiddenInput.value = code;
            }

            // 2) [추가됨] 미리보기 영역(div) 실시간 업데이트
            var previewDiv = document.getElementById('html_preview');
            if(previewDiv) {
                previewDiv.innerHTML = code;
            }
        });
    }

    // Public methods
    return {
        init: function () {
            initCodeEditor();
        }
    };
}();

// On document ready
// jQuery가 있다면
$(document).ready(function() {
    KTHtmlCodeEditor.init();
});

// 또는 순수 JS 사용 시
/*
document.addEventListener("DOMContentLoaded", function() {
    KTHtmlCodeEditor.init();
});
*/