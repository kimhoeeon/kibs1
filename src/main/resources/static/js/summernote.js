/**
 * Summernote Custom Initialization Script (Stable Version)
 * - 줄간격, 구분선, 표 편집 기능 포함
 * - [수정] 외부 플러그인 제거 -> 'onPaste' 이벤트를 통해 텍스트 청소 기능 직접 구현 (오류 해결)
 * - [수정] fontNamesIgnoreCheck 추가 (폰트 변경 시 콘솔 오류 해결)
 */

$(document).ready(function() {
    // 폰트 리스트 변수로 분리 (중복 방지)
    var fontList = ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'];

    $('#summernote').summernote({
        height: 500,
        minHeight: null,
        maxHeight: null,
        focus: true,
        lang: 'ko-KR',
        placeholder: '내용을 입력해주세요.',

        // 툴바 설정
        toolbar: [
            ['font', ['fontname', 'fontsize', 'color', 'clear']],
            ['style', ['style', 'bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript']],
            ['para', ['ul', 'ol', 'paragraph', 'height']],
            ['insert', ['table', 'link', 'picture', 'hr']],
            ['view', ['codeview', 'help']]
        ],

        // 폰트 설정
        fontNames: fontList,

        // [핵심 수정] 폰트 설치 여부 검사 무시 (이게 없으면 toLowerCase 오류 발생)
        fontNamesIgnoreCheck: fontList,

        // 폰트 크기
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],

        // 표(Table) 편집 팝오버 메뉴
        popover: {
            image: [
                ['image', ['resizeFull', 'resizeHalf', 'resizeQuarter', 'resizeNone']],
                ['float', ['floatLeft', 'floatRight', 'floatNone']],
                ['remove', ['removeMedia']]
            ],
            table: [
                ['add', ['addRowDown', 'addRowUp', 'addColLeft', 'addColRight']],
                ['delete', ['deleteRow', 'deleteCol', 'deleteTable']],
            ]
        },

        // 콜백 함수 설정
        callbacks: {
            onImageUpload: function(files) {
                for (var i = 0; i < files.length; i++) {
                    uploadImageFile(files[i], this);
                }
            },
            onPaste: function (e) {
                var bufferText = ((e.originalEvent || e).clipboardData || window.clipboardData).getData('Text');
                e.preventDefault();
                document.execCommand('insertText', false, bufferText);
            }
        }
    });
});

/**
 * 이미지 파일 업로드 함수
 */
function uploadImageFile(file, editor) {
    var data = new FormData();
    data.append("image", file);

    $.ajax({
        data: data,
        type: "POST",
        url: "/tui-editor/image-upload",
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function(responsePath) {
            var imageUrl = "/tui-editor/image-print?filename=" + responsePath;
            $('#summernote').summernote('insertImage', imageUrl);
        },
        error: function(e) {
            console.error("Image Upload Error:", e);
            alert("이미지 업로드 실패");
        }
    });
}