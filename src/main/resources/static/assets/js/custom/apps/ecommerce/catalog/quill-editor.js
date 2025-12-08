"use strict";

// specify the fonts you would
var fonts = ['Arial', 'Courier', 'Garamond', 'Tahoma', 'Times New Roman', 'Verdana', 'Dotum', 'Roboto'];
// generate code friendly names
function getFontName(font) {
    return font.toLowerCase().replace(/\s/g, "-");
}
var fontNames = fonts.map(font => getFontName(font));
// add fonts to style
var fontStyles = "";
fonts.forEach(function(font) {
    var fontName = getFontName(font);
    fontStyles += ".ql-snow .ql-picker.ql-font .ql-picker-label[data-value=" + fontName + "]::before, .ql-snow .ql-picker.ql-font .ql-picker-item[data-value=" + fontName + "]::before {" +
        "content: '" + font + "';" +
        "font-family: '" + font + "', sans-serif;" +
        "}" +
        ".ql-font-" + fontName + "{" +
        " font-family: '" + font + "', sans-serif;" +
        "}";
});
var node = document.createElement('style');
node.innerHTML = fontStyles;
document.body.appendChild(node);

// Class definition
var KTQuillEditor = function () {

    // Add fonts to whitelist
    var Font = Quill.import('formats/font');
    Font.whitelist = fontNames;
    Quill.register(Font, true);

    // Private functions
    var toolbarOptions = [
        [{ 'font': fontNames }],
        ['bold', 'italic', 'underline'],
        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
        [{ 'indent': '-1'}, { 'indent': '+1' }],
        [{ 'size': ['small', false, 'large', 'huge'] }],
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
        [{ 'color': [] }, { 'background': [] }],
        [{ 'align': [] }],
        ['image'],
        ['link']
    ];

    // Init quill editor
    const initQuill = () => {

        // Init quill --- more info: https://quilljs.com/docs/quickstart/
        var quill = new Quill('#quill_editor_content', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '내용',
            theme: 'snow' // or 'bubble'
        });

        // 텍스트 변경 이벤트
        quill.on('text-change', function() {
            document.getElementById('quill_content').value = quill.root.innerHTML;
        });

        // 1. 툴바 이미지 버튼 핸들러
        quill.getModule('toolbar').addHandler('image', function () {
            selectLocalImage(quill);
        });

        // 2. 붙여넣기(Paste) 이벤트 핸들러 추가 (여기가 핵심입니다)
        quill.root.addEventListener('paste', function(e) {
            var clipboardData = e.clipboardData || window.clipboardData;
            if (clipboardData && clipboardData.items) {
                var items = clipboardData.items;
                for (var i = 0; i < items.length; i++) {
                    // 붙여넣은 항목이 이미지인 경우
                    if (items[i].type.indexOf('image') !== -1) {
                        e.preventDefault(); // 기본 Base64 변환 동작 중단
                        var file = items[i].getAsFile();
                        serverUpload(file, quill); // 서버 업로드 함수 호출
                        return;
                    }
                }
            }
        });

        // (선택사항) 드래그 앤 드롭으로 이미지 넣을 때도 업로드 처리하려면 아래 주석 해제
        quill.root.addEventListener('drop', function(e) {
            if (e.dataTransfer && e.dataTransfer.files && e.dataTransfer.files.length) {
                // 첫 번째 파일만 처리 (필요시 반복문 처리 가능)
                if (e.dataTransfer.files[0].type.indexOf('image') !== -1) {
                    e.preventDefault();
                    serverUpload(e.dataTransfer.files[0], quill);
                }
            }
        });
    }

    function selectLocalImage(quill) {
        const fileInput = document.createElement('input');
        fileInput.setAttribute('type', 'file');
        fileInput.setAttribute('accept', 'image/*'); // 이미지 파일만 허용

        fileInput.click();

        fileInput.addEventListener("change", function () {
            const file = fileInput.files[0];
            if(file) {
                serverUpload(file, quill); // 공통 업로드 함수 호출
            }
        });
    }

    // [공통] 서버 업로드 및 에디터 삽입 함수
    function serverUpload(file, quill) {
        let formData = new FormData();
        formData.append('uploadFile', file);

        // 기존에 사용하시던 컨트롤러 URL 그대로 사용
        fetch('/file/upload.do?gbn=quill', {
            method: 'POST',
            body: formData
        })
            .then(res => res.json())
            .then(res => {
                // 커서 위치 파악 (없으면 맨 뒤)
                let range = quill.getSelection();
                let index = range ? range.index : quill.getLength();

                // 윈도우 경로 역슬래시 치환
                if(res.uploadPath) {
                    res.uploadPath = res.uploadPath.replace(/\\/g, '/');
                }

                // 서버가 준 웹 경로를 그대로 사용하여 직접 접근
                let imgUrl = res.uploadPath + "/" + res.fileName;

                // 에디터에 이미지 태그 삽입
                quill.insertEmbed(index, 'image', imgUrl);

                // 이미지 삽입 후 커서를 이미지 뒤로 이동
                quill.setSelection(index + 1);
            })
            .catch(err => {
                console.error("Image Upload Failed", err);
                alert("이미지 업로드에 실패했습니다.");
            });
    }

    // Public methods
    return {
        init: function () {
            // Init forms
            initQuill();
        }
    };
}();

// On document ready
KTUtil.onDOMContentLoaded(function () {
    KTQuillEditor.init();
});