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

    // ============================================================
    // 기본 블록 태그를 <p>에서 <div>로 변경
    // 설명: <table> 등 복잡한 HTML이 <p> 태그로 감싸져서 깨지는 현상을 방지합니다.
    // ============================================================
    var Block = Quill.import('blots/block');
    Block.tagName = 'DIV';
    Quill.register(Block, true);
    // ============================================================

    // ============================================================
    // 이미지 속성(크기, 정렬 style 등) 보존을 위한 Custom Image Blot 설정
    // ============================================================
    var BaseImageFormat = Quill.import('formats/image');
    const ImageFormatAttributesList = ['alt', 'height', 'width', 'style', 'class'];

    class CustomImage extends BaseImageFormat {
        static formats(domNode) {
            return ImageFormatAttributesList.reduce(function(formats, attribute) {
                if (domNode.hasAttribute(attribute)) {
                    formats[attribute] = domNode.getAttribute(attribute);
                }
                return formats;
            }, {});
        }
        format(name, value) {
            if (ImageFormatAttributesList.indexOf(name) > -1) {
                if (value) {
                    this.domNode.setAttribute(name, value);
                } else {
                    this.domNode.removeAttribute(name);
                }
            } else {
                super.format(name, value);
            }
        }
    }
    Quill.register(CustomImage, true);
    // ============================================================

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
                toolbar: toolbarOptions,
                // ▼▼▼ 이미지 리사이즈 모듈 설정 추가 ▼▼▼
                imageResize: {
                    displaySize: true // 사이즈 조절 시 크기 텍스트 표시 여부
                }
                // ▲▲▲ 이미지 리사이즈 모듈 설정 추가 ▲▲▲
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

        // 2. 붙여넣기(Paste) 이벤트 핸들러 추가
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

        // (선택사항) 드래그 앤 드롭
        quill.root.addEventListener('drop', function(e) {
            if (e.dataTransfer && e.dataTransfer.files && e.dataTransfer.files.length) {
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
        fileInput.setAttribute('accept', 'image/*');

        fileInput.click();

        fileInput.addEventListener("change", function () {
            const file = fileInput.files[0];
            if(file) {
                serverUpload(file, quill);
            }
        });
    }

    // [공통] 서버 업로드 및 에디터 삽입 함수
    function serverUpload(file, quill) {
        let formData = new FormData();
        formData.append('uploadFile', file);

        // 기존 URL 유지
        fetch('/file/upload.do?gbn=quill', {
            method: 'POST',
            body: formData
        })
            .then(res => res.json())
            .then(res => {
                let range = quill.getSelection();
                let index = range ? range.index : quill.getLength();

                if(res.uploadPath) {
                    res.uploadPath = res.uploadPath.replace(/\\/g, '/');
                }

                let imgUrl = res.uploadPath + "/" + res.fileName;

                quill.insertEmbed(index, 'image', imgUrl);
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
            initQuill();
        }
    };
}();

// On document ready
KTUtil.onDOMContentLoaded(function () {
    KTQuillEditor.init();
});