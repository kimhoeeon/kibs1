/***
 * mng/exhibitor/transfer/transferCompany.js
 * 전시회 > 이관정보 > 지난행사 전시업체
 * */
var transferYear = (new Date().getFullYear()).toString();
$(function(){
    //이관년도
    $("select[name^=transferYear]").each(function () {
        let now = new Date();
        let now_year = transferYear;

        let selTransferYear = $(this);
        $("option", selTransferYear).remove();
        // selTransferYear.append("<option value=''>선택</option>");
        for(let i = now_year; i >= (now_year-6); i--) {
            selTransferYear.append("<option value='" + i + "'>" + i + "</option>");
        }
    });

    // 제품 검색하기 옵션
    let product0 = ["선택", "요트·보트", "무동력보트", "워크보트", "워터스포츠", "다이빙", "아웃도어", "해양관광", "해양부품", "마리나산업"];
    // 요트 보트
    let product1 = ["선택", "파워보트", "세일요트", "고무보트", "콤비보트", "FRP보트", "알루미늄보트", "카본보트", "복합소재보트"];
    // 무동력보트
    let product2 = ["선택", "카누", "카약", "조정", "노보트", "SUP", "딩기요트", "무동력보트"];
    // 워크보트
    let product3 = ["선택", "관공선", "소방선", "구조선", "감시선", "행정선", "고속단정", "특수선박"];
    // 워터스포츠
    let product4 = ["선택", "수상오토바이", "수상스키", "웨이크보드", "서핑 장비 및 의류"];
    // 다이빙
    let product5 = ["선택", "다이빙 장비", "다이빙 서비스", "다이빙 교육", "스킨스쿠버", "스노우쿨링"];
    // 아웃도어
    let product6 = ["선택", "트레일러", "견인장치", "캠핑카", "아웃도어용품"];
    // 해양관광
    let product7 = ["선택", "관광 서비스", "해양레저 서비스", "보트대여", "요트대여"];
    // 해양부품
    let product8 = ["선택", "선외기", "선내기", "스턴드라이브 엔진", "가이드보터", "프로펠러", "마린스피커", "케이블류", "엘커링", "무어링", "어군탐지기", "네비게이션", "레이더", "무선통신장비", "기타"];
    // 마리나산업
    let product9 = ["선택", "선박 보관 임대", "선박 유지보수", "방제장비", "워터프론트 개발", "보트용 전자장비", "도시/광택", "보트 소재 및 원료"];

    // 제품 검색하기 선택 박스 초기화
    $("select[name^=productOptionBig]").each(function () {
        let selproductOptionBig = $(this);
        $.each(eval(product0), function () {
            selproductOptionBig.append("<option value='" + this + "'>" + this + "</option>");
        });
        selproductOptionBig.parent('div').siblings('div').children('select').append("<option value=''>선택</option>");
    });

    // 옵션 1차 선택시 하위옵션 설정
    $("select[name^=productOptionBig]").on('change', function () {
        let product = "product" + $("option", $(this)).index($("option:selected", $(this)));
        let productOptionSmall = $(this).parent('div').siblings('div').children('select');
        $("option", productOptionSmall).remove();

        if (product === "product0") {
            productOptionSmall.prop("disabled", true).append("<option value=''>선택</option>");
        } else {
            $.each(eval(product), function () {
                productOptionSmall.append("<option value='" + this + "'>" + this + "</option>");
            });
            productOptionSmall.prop("disabled", false);
        }
    });

    // 옵션1 선택 시 하위옵션 활성화
    $("select[name^=productOptionBig]").parent('div').siblings('div').children('select').prop("disabled", true);

    /* 컨택내역 - 날짜 DatePicker */
    let contactDatePicker = document.getElementById('contactDate');
    if(contactDatePicker){
        contactDatePicker.flatpickr({
            enableTime: false,
            dateFormat: "Y-m-d",
        });
    }

    /* 참고사항 - 날짜 DatePicker */
    let referenceDatePicker = document.getElementById('referenceDate');
    if(referenceDatePicker){
        referenceDatePicker.flatpickr({
            enableTime: false,
            dateFormat: "Y-m-d",
        });
    }

    // 담당자 정보 input 이메일
    $('select[name=chargePersonEmail_sel]').on('change', function () {
        let selectedOption = $(this).val();
        let emailInput2 = $(this).prev('input');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }
    });

    // 바이어 정보 input 이메일
    $('#buyer_company_email_select').on('change', function () {
        let selectedOption = $(this).val();
        let emailInput2 = $('#buyer_company_email_input_2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }
    });

    ///////////////// 담당자 추가 /////////////////
    let chargeInfoCount = $('.charge_info_box:last .chargeInfoNum').text();

    // .charge_info_box를 추가하는 이벤트 핸들러 추가
    $('#kt_charge_info_add').on('click', function () {
        let newChargeInfoBox = $('.charge_info_box:first').clone(true);
        chargeInfoCount++;
        newChargeInfoBox.find('.chargeInfoNum').text(chargeInfoCount);
        newChargeInfoBox.find('input[type="text"]').val('');
        newChargeInfoBox.find('input[type="hidden"]').val('');
        newChargeInfoBox.find('input[type="tel"]').val('');

        // 복제된 .display_info_box 내의 삭제 버튼 보이기
        newChargeInfoBox.find('.chargeInfoDel').show();

        newChargeInfoBox.find('.chargeInfoDel').on('click', function () {
            deleteChargeInfoBox();
        });
        $('.charge_info_box:last').after(newChargeInfoBox);
        updateChargeInfoNum();
    });

    // .display_info_box를 삭제하는 이벤트 핸들러
    function deleteChargeInfoBox() {
        Swal.fire({
            title: '해당 담당자 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mng/exhibitor/participant/company/deleteCharge.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '담당자 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.charge_info_box').remove();
                chargeInfoCount--;
                updateChargeInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .display_info_box의 .exhiInfoNum 번호 업데이트
    function updateChargeInfoNum() {
        $('.charge_info_box').each(function (index) {
            $(this).find('.chargeInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .display_info_box 내의 삭제 버튼 숨기기
    $('.charge_info_box:first .chargeInfoDel').hide();

    // 첫 번째 .display_info_box의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.chargeInfoDel').on('click', function () {
        deleteChargeInfoBox();
    });

    ///////////////// 전시정보 추가 /////////////////
    let exhiInfoCount = $('.display_info_box:last .exhiInfoNum').text();

    // .exhiInfoBox를 추가하는 이벤트 핸들러 추가
    $('#kt_display_info_add').on('click', function () {
        let newExhiInfoBox = $('.display_info_box:first').clone();
        exhiInfoCount++;
        newExhiInfoBox.find('.exhiInfoNum').text(exhiInfoCount);
        newExhiInfoBox.find('input[type="text"]').val('');
        newExhiInfoBox.find('input[type="hidden"]').val('');

        // 복제된 .display_info_box 내의 삭제 버튼 보이기
        newExhiInfoBox.find('.exhiInfoDel').show();

        newExhiInfoBox.find('.exhiInfoDel').on('click', function () {
            deleteExhiInfoBox();
        });

        $('.display_info_box:last').after(newExhiInfoBox);
        updateExhiInfoNum();
    });

    // .display_info_box를 삭제하는 이벤트 핸들러
    function deleteExhiInfoBox() {
        Swal.fire({
            title: '해당 전시 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mng/exhibitor/participant/company/deleteDisplay.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '전시 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.display_info_box').remove();
                exhiInfoCount--;
                updateExhiInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .display_info_box의 .exhiInfoNum 번호 업데이트
    function updateExhiInfoNum() {
        $('.display_info_box').each(function (index) {
            $(this).find('.exhiInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .display_info_box 내의 삭제 버튼 숨기기
    $('.display_info_box:first .exhiInfoDel').hide();

    // 첫 번째 .display_info_box의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.exhiInfoDel').on('click', function () {
        deleteExhiInfoBox();
    });

    ///////////////// 홍보이미지 추가 /////////////////
    let proImgCount = $('.promotion_info_box:last .proImgNum').text();

    // .proImgBox를 추가하는 이벤트 핸들러 추가
    $('#kt_promotion_info_add').on('click', function () {
        let newProImgBox = $('.promotion_info_box:first').clone();
        proImgCount++;
        newProImgBox.find('.proImgNum').text(proImgCount);
        newProImgBox.find('input[type="text"]').val('');
        newProImgBox.find('input[type="hidden"]').val('');

        // 복제된 .promotion_info_box에서 삭제 버튼 보이기
        newProImgBox.find('.proImgDel').show();

        // 파일 입력 초기화 및 비활성화 속성 제거
        let fileInput = newProImgBox.find('.upload_hidden');
        let fileNameInput = newProImgBox.find('.upload_name');
        fileInput.val('').attr('id', 'promotionImageFile' + proImgCount);
        fileNameInput.val('').attr('disabled', true).attr('id', 'promotionImage' + proImgCount).attr('name', 'promotionImage');
        newProImgBox.find('label').attr('for', 'promotionImageFile' + proImgCount);

        newProImgBox.find('.proImgDel').on('click', function () {
            deleteProImgBox();
        });
        $('.promotion_info_box:last').after(newProImgBox);
        updateProImgNum();
    });

    // .promotion_info_box를 삭제하는 이벤트 핸들러
    function deleteProImgBox() {
        $(this).closest('.promotion_info_box').remove();
        proImgCount--;
        updateProImgNum();
    }

    // 각 .promotion_info_box의 .proImgNum 번호 업데이트
    function updateProImgNum() {
        $('.promotion_info_box').each(function (index) {
            $(this).find('.proImgNum').text(index + 1);
        });
    }

    // 첫 번째 .promotion_info_box 내의 삭제 버튼 숨기기
    $('.promotion_info_box:lt(3) .proImgDel').hide();

    // 첫 번째 .promotion_info_box의 삭제 버튼에 초기 이벤트 핸들러 추가
    $('.proImgDel').on('click', function () {
        deleteProImgBox();
    });

    ///////////////// 온라인 전시관 정보 추가 /////////////////
    let onlineInfoCount = $('.online_info_box:last .onlineInfoNum').text();

    // .onlineInfoBox를 추가하는 이벤트 핸들러 추가
    $('#kt_online_info_add').on('click', function () {
        let onlinePrdBoxes = $(this).closest('.online_info_box').find('.onlinePrdBox');
        let onlinePrdBoxCount = onlinePrdBoxes.length;
        // 아이디명 변경
        let newIdSuffix = onlinePrdBoxCount + 1;

        let newOnlineInfoBox = $('.online_info_box:first').clone(true);

        onlineInfoCount++;
        newOnlineInfoBox.find('.onlineInfoNum').text(onlineInfoCount);
        newOnlineInfoBox.find('textarea').val('');
        newOnlineInfoBox.find('input[type=text]').val('');
        newOnlineInfoBox.find('input[type=hidden]').val('');
        newOnlineInfoBox.find('input[type=checkbox]').prop('checked', false);

        // 복제된 .onlineInfoBox 는 제품 사진 하나만 남기기
        let onlinePrdBoxList = newOnlineInfoBox.find('.onlinePrdBox');
        for(let i=0; i<=onlinePrdBoxList.length; i++){
            if(i !== 0){
                onlinePrdBoxList.eq(i).remove();
            }
        }

        // 복제된 .onlineInfoBox 는 제품 기존 값 목록 제거
        newOnlineInfoBox.find('.preValueList').remove();

        // 복제된 .onlineInfoBox에서 삭제 버튼 보이기
        newOnlineInfoBox.find('.onlineInfoDel').show();

        newOnlineInfoBox.find('.onlyNumEng').on("blur keyup", function () {
            let exp = /[^A-Za-z0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;
            $(this).val($(this).val().replaceAll(exp, ''));
        });

        newOnlineInfoBox.find('.onlyNumDec').on("blur keyup", function () {
            var sanitizedValue = $(this).val().replace(/[^0-9.]/g, ''); // 숫자와 소수점 이외의 문자 제거
            var decimalParts = sanitizedValue.split("."); // 소수점을 기준으로 분할

            // 소수점이 하나 이상일 경우 마지막 소수점만 남기고 제거
            if (decimalParts.length > 2) {
                decimalParts.pop(); // 마지막 요소(소수점 이후 내용) 제거
                sanitizedValue = decimalParts.join(".");
            }

            $(this).val(sanitizedValue); // 처리된 값을 다시 입력 필드에 설정
        });

        // 파일 입력 초기화 및 비활성화 속성 제거
        let fileInput = newOnlineInfoBox.find('.upload_hidden');
        let fileNameInput = newOnlineInfoBox.find('.upload_name');
        fileInput.val('').attr('id', 'productImageFile' +  + onlineInfoCount + '_' + newIdSuffix);
        fileNameInput.val('').attr('disabled', true).removeAttr('id');
        newOnlineInfoBox.find('label.btn').attr('for', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix);

        $('.online_info_box:last').after(newOnlineInfoBox);
        updateOnlineInfoNum();
    });

    // .onlineInfoBox를 삭제하는 이벤트 핸들러
    function deleteOnlineInfoBox() {
        Swal.fire({
            title: '해당 제품 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mng/exhibitor/participant/company/deleteOnline.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '제품 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }

                    let file_id = $(this).parent('h4').parent('div').parent('div').find('input[type=hidden][name=productImageUploadFile]').attr('id');
                    if(nvl(file_id,"") !== ""){
                        let file_jsonObj = {
                            "id": file_id
                        };
                        let resData = ajaxConnect('/mng/exhibitor/participant/company/deleteFile.do','post',file_jsonObj);
                        if(resData.resultCode !== "0"){
                            showMessage('', 'error', '에러 발생', '파일 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                        }
                    }
                }

                $(this).closest('.online_info_box').remove();
                onlineInfoCount--;
                updateOnlineInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .onlineInfoBox의 .onlineInfoNum 번호 업데이트
    function updateOnlineInfoNum() {
        $('.online_info_box').each(function (index) {
            $(this).find('.onlineInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .onlineInfoBox 내의 삭제 버튼 숨기기
    $('.online_info_box:first .onlineInfoDel').hide();

    // 첫 번째 .onlineInfoBox의 삭제 버튼에 초기 이벤트 핸들러 추가
    $('.onlineInfoDel').on('click', function () {
        deleteOnlineInfoBox();
    });

    ///////////////// 동반자 추가 /////////////////
    let partnerInfoCount = 1;

    // .partner_info_box를 추가하는 이벤트 핸들러 추가
    $('#kt_partner_info_add').on('click', function () {
        let newPartnerInfoBox = $('.partner_info_box:first').clone();
        partnerInfoCount++;
        newPartnerInfoBox.find('.partnerInfoNum').text(partnerInfoCount);
        newPartnerInfoBox.find('input[type="text"]').val('');

        // 복제된 .display_info_box 내의 삭제 버튼 보이기
        newPartnerInfoBox.find('.partnerInfoDel').show();

        newPartnerInfoBox.find('.partnerInfoDel').on('click', function () {
            deletePartnerInfoBox();
        });
        $('.partner_info_box:last').after(newPartnerInfoBox);
        updatePartnerInfoNum();
    });

    // .partner_info_box를 삭제하는 이벤트 핸들러
    function deletePartnerInfoBox() {
        $(this).closest('.partner_info_box').remove();
        partnerInfoCount--;
        updatePartnerInfoNum();
    }

    // 각 .partner_info_box의 .partnerInfoNum 번호 업데이트
    function updatePartnerInfoNum() {
        $('.partner_info_box').each(function (index) {
            $(this).find('.partnerInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .partner_info_box 내의 삭제 버튼 숨기기
    $('.partner_info_box:first .partnerInfoDel').hide();

    // 첫 번째 .partner_info_box의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.partnerInfoDel').on('click', function(){deletePartnerInfoBox();});

    // 수출상담회 참가 여부 라디오 버튼 변경 시
    $('.form_add_buyer').hide();
    $('input[name="exportMeetingYn"]').on('change', function() {
        if ($(this).is(':checked') && $(this).attr('id') === 'exportMeetingY') {
            $('.form_add_buyer').show(); // .form_chuga 요소를 보여줌
        } else {
            $('.form_add_buyer').hide(); // .form_chuga 요소를 숨김
        }
    });

    // 모달창 닫힐 때 Event
    $('#kt_buyer_popup').on('hide.bs.modal', function () {
        f_buyer_init();
    })

    ///////////////// 온라인 전시관 정보 제품사진 추가 /////////////////
    // .onlinePrdAdd 클릭 시 새로운 .onlinePrdBox 추가
    $(document).on('click', '.onlinePrdAdd', function () {
        let onlinePrdBoxes = $(this).closest('.online_info_box').find('.onlinePrdBox');
        let onlinePrdBoxCount = parseInt(onlinePrdBoxes.length) + parseInt($(this).closest('.online_info_box').find('li.productImageFile_li').length);

        if (onlinePrdBoxCount < 5) {
            // 복제할 .onlinePrdBox 요소를 선택합니다.
            let newOnlinePrdBox = $('.onlinePrdBox:first').clone(true, true);

            // 아이디명 변경
            let onlineInfoCount = parseInt($(this).closest('.online_info_box').find('.onlineInfoNum').text());
            let newIdSuffix = parseInt($(this).closest('.online_info_box').find('li.productImageFile_li').length === 0 ? 0 : $(this).closest('.online_info_box').find('li.productImageFile_li').length)
                + parseInt($(this).closest('.online_info_box').find('input[type=text][name=productImage]').length === 0 ? 0 : $(this).closest('.online_info_box').find('input[type=text][name=productImage]').length)
                + 1;

            newOnlinePrdBox.find('.upload_hidden').attr('id', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix).attr('name', 'productImageFile');
            newOnlinePrdBox.find('.upload_name').attr('name', 'productImage');
            newOnlinePrdBox.find('label').attr('for', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix);

            // 새로운 .onlinePrdBox를 추가합니다.
            $(this).closest('.online_info_box').find('.onlinePrdBox:last').after(newOnlinePrdBox);

            // 새로 추가한 .onlinePrdBox의 .upload_name와 .upload_hidden 초기화
            newOnlinePrdBox.find('.upload_name').val('');
            newOnlinePrdBox.find('.upload_hidden').val('');

            // 새로 추가한 .onlinePrdBox의 .onlinePrdDel 이벤트 핸들러 설정
            newOnlinePrdBox.find('.onlinePrdDel').on('click', function () {
                $(this).closest('.onlinePrdBox').remove();
                updateOnlinePrdBox();
            });

            // 업데이트 후 최대 5개까지 보이도록 제한
            updateOnlinePrdBox();
        }else{
            showMessage('', 'info', '[온라인 전시관 정보]', '제품 사진은 제품당 최대 5장까지만 추가 가능합니다.', '');
        }
    });

    // .onlinePrdDel 이벤트 핸들러
    $(document).on('click', '.onlinePrdDel', function () {
        $(this).closest('.onlinePrdBox').remove();
        updateOnlinePrdBox();
    });

    // 초기 상태에서는 모든 .onlinePrdDel를 숨김
    $('.onlinePrdBox .onlinePrdDel').hide();

    // .onlinePrdBox 갯수를 업데이트하고 최대 5개까지 제한
    function updateOnlinePrdBox() {
        $('.online_info_box').each(function () {
            let onlinePrdBoxes = $(this).find('.onlinePrdBox');
            let onlinePrdBoxCount = onlinePrdBoxes.length;

            // 모든 .onlinePrdBox의 .onlinePrdDel를 숨기고 .onlinePrdAdd를 표시
            onlinePrdBoxes.find('.onlinePrdDel').hide();
            onlinePrdBoxes.find('.onlinePrdAdd').show();

            // 첫 번째 .onlinePrdBox에서 .onlinePrdAdd만 표시하고 .onlinePrdDel를 숨김
            onlinePrdBoxes.first().find('.onlinePrdAdd').show();
            onlinePrdBoxes.first().find('.onlinePrdDel').hide();

            // 나머지 .onlinePrdBox에서 .onlinePrdDel만 표시하고 .onlinePrdAdd를 숨김
            onlinePrdBoxes.not(':first').find('.onlinePrdAdd').hide();
            onlinePrdBoxes.not(':first').find('.onlinePrdDel').show();
        });
    }
});

function check_count(obj){
    let chkBox = document.getElementsByName('fieldPart');
    let chkCnt = 0;
    for(let i=0; i<chkBox.length; i++){
        if(chkBox[i].checked){
            chkCnt++;
        }
    }
    if(chkCnt > 3){
        showMessage('', 'error', '[참가분야]', '최대 3개까지만 선택할 수 있습니다.', '');
        obj.checked = false;
        return false;
    }
}

function f_buyer_add(){

    // 회사명
    let buyer_company_name = document.querySelector('#buyerCompanyName').value;
    /*if(buyer_company_name === ''){
        showMessage('', 'error', '[바이어 정보]', '회사명을 입력해 주세요.', '');
        return;
    }*/

    // 국가
    let buyer_country = document.querySelector('#buyerCompanyCountry').value;
    /*if(buyer_country === ''){
        showMessage('', 'error', '[바이어 정보]', '국가를 입력해 주세요.', '');
        return;
    }*/

    // 소재지
    let buyer_location = document.querySelector('#buyerCompanyLocation').value;
    /*if(buyer_location === ''){
        showMessage('', 'error', '[바이어 정보]', '소재지를 입력해 주세요.', '');
        return;
    }*/

    // 홈페이지
    let buyer_homepage = document.querySelector('#buyerCompanyHomepage').value;
    /*if(buyer_homepage === ''){
        showMessage('', 'error', '[바이어 정보]', '홈페이지 주소를 입력해 주세요.', '');
        return;
    }*/

    // 부서
    let buyer_depart = document.querySelector('#buyerCompanyDepart').value;

    // 직책
    let buyer_position = document.querySelector('#buyerCompanyPosition').value;

    // 이메일
    let buyer_email_input1 = document.querySelector('#buyer_company_email_input_1').value;
    let buyer_email_input2 = document.querySelector('#buyer_company_email_input_2').value;

    // 전화번호
    let buyer_tel = document.querySelector('#buyerCompanyTel').value;

    // 휴대전화
    let buyer_phone = document.querySelector('#buyerCompanyPhone').value;

    // 팩스
    let buyer_fax = document.querySelector('#buyerCompanyFax').value;

    // 취급품목
    let buyer_item = document.querySelector('#buyerCompanyItem').value;
    /*if(buyer_item === ''){
        showMessage('', 'error', '[바이어 정보]', '취급품목을 입력해 주세요.', '');
        return;
    }*/

    // 초청사유
    let buyer_invite_reason = document.querySelector('#buyerCompanyInviteReason').value;
    /*if(buyer_invite_reason === ''){
        showMessage('', 'error', '[바이어 정보]', '초청사유를 입력해 주세요.', '');
        return;
    }*/

    // 계약진행 여부
    let buyer_progress_yn = document.querySelector('#buyerCompanyProgressYn').value;

    // 희망사항
    let buyer_hope = document.querySelector('#buyerCompanyHope').value;
    /*if(buyer_hope === ''){
        showMessage('', 'error', '[바이어 정보]', '희망사항을 입력해 주세요.', '');
        return;
    }*/

    /* body 에 팝업 데이터를 포함한 Element 생성 */
    let buyer_add_btn = document.querySelector('#buyer_add_btn').parentElement;

    let form_chuga_list = document.createElement('div');
    form_chuga_list.className = 'form_chuga_list';

    let cont = document.createElement('div');
    cont.className = 'cont';
    let name = document.createElement('div');
    name.className = 'name';
    name.innerText = buyer_company_name;
    let gubun = document.createElement('div');
    gubun.className = 'gubun';
    gubun.innerText = buyer_country + ' / ' + buyer_location + ' / ' + buyer_depart + ' ' + buyer_position;

    /* buyer value input hidden 생성 */
    //회사명
    let buyerCompanyName_el = document.createElement('input');
    buyerCompanyName_el.type = 'hidden';
    buyerCompanyName_el.name = 'buyerCompanyName';
    buyerCompanyName_el.id = 'buyerCompanyName';
    buyerCompanyName_el.value = buyer_company_name;

    //국가
    let buyerCompanyCountry_el = document.createElement('input');
    buyerCompanyCountry_el.type = 'hidden';
    buyerCompanyCountry_el.name = 'buyerCompanyCountry';
    buyerCompanyCountry_el.id = 'buyerCompanyCountry';
    buyerCompanyCountry_el.value = buyer_country;

    //소재지
    let buyerCompanyLocation_el = document.createElement('input');
    buyerCompanyLocation_el.type = 'hidden';
    buyerCompanyLocation_el.name = 'buyerCompanyLocation';
    buyerCompanyLocation_el.id = 'buyerCompanyLocation';
    buyerCompanyLocation_el.value = buyer_location;

    //홈페이지
    let buyerCompanyHomepage_el = document.createElement('input');
    buyerCompanyHomepage_el.type = 'hidden';
    buyerCompanyHomepage_el.name = 'buyerCompanyHomepage';
    buyerCompanyHomepage_el.id = 'buyerCompanyHomepage';
    buyerCompanyHomepage_el.value = buyer_homepage;

    //부서
    let buyerCompanyDepart_el = document.createElement('input');
    buyerCompanyDepart_el.type = 'hidden';
    buyerCompanyDepart_el.name = 'buyerCompanyDepart';
    buyerCompanyDepart_el.id = 'buyerCompanyDepart';
    buyerCompanyDepart_el.value = buyer_depart;

    //직책
    let buyerCompanyPosition_el = document.createElement('input');
    buyerCompanyPosition_el.type = 'hidden';
    buyerCompanyPosition_el.name = 'buyerCompanyPosition';
    buyerCompanyPosition_el.id = 'buyerCompanyPosition';
    buyerCompanyPosition_el.value = buyer_position;

    //이메일
    let buyerCompanyEmail_el = document.createElement('input');
    buyerCompanyEmail_el.type = 'hidden';
    buyerCompanyEmail_el.name = 'buyerCompanyEmail';
    buyerCompanyEmail_el.id = 'buyerCompanyEmail';
    if(buyer_email_input1 !== "" && buyer_email_input2 !== ""){
        buyerCompanyEmail_el.value = buyer_email_input1 + '@' + buyer_email_input2;
    }else{
        buyerCompanyEmail_el.value = '';
    }

    //전화번호
    let buyerCompanyTel_el = document.createElement('input');
    buyerCompanyTel_el.type = 'hidden';
    buyerCompanyTel_el.name = 'buyerCompanyTel';
    buyerCompanyTel_el.id = 'buyerCompanyTel';
    buyerCompanyTel_el.value = buyer_tel;

    //휴대전화
    let buyerCompanyPhone_el = document.createElement('input');
    buyerCompanyPhone_el.type = 'hidden';
    buyerCompanyPhone_el.name = 'buyerCompanyPhone';
    buyerCompanyPhone_el.id = 'buyerCompanyPhone';
    buyerCompanyPhone_el.value = buyer_phone;

    //팩스
    let buyerCompanyFax_el = document.createElement('input');
    buyerCompanyFax_el.type = 'hidden';
    buyerCompanyFax_el.name = 'buyerCompanyFax';
    buyerCompanyFax_el.id = 'buyerCompanyFax';
    buyerCompanyFax_el.value = buyer_fax;

    //취급품목
    let buyerCompanyItem_el = document.createElement('input');
    buyerCompanyItem_el.type = 'hidden';
    buyerCompanyItem_el.name = 'buyerCompanyItem';
    buyerCompanyItem_el.id = 'buyerCompanyItem';
    buyerCompanyItem_el.value = buyer_item;

    //초청사유
    let buyerCompanyInviteReason_el = document.createElement('input');
    buyerCompanyInviteReason_el.type = 'hidden';
    buyerCompanyInviteReason_el.name = 'buyerCompanyInviteReason';
    buyerCompanyInviteReason_el.id = 'buyerCompanyInviteReason';
    buyerCompanyInviteReason_el.value = buyer_invite_reason;

    //계약진행 여부
    let buyerCompanyProgressYn_el = document.createElement('input');
    buyerCompanyProgressYn_el.type = 'hidden';
    buyerCompanyProgressYn_el.name = 'buyerCompanyProgressYn';
    buyerCompanyProgressYn_el.id = 'buyerCompanyProgressYn';
    buyerCompanyProgressYn_el.value = buyer_progress_yn;

    //희망사항
    let buyerCompanyHope_el = document.createElement('input');
    buyerCompanyHope_el.type = 'hidden';
    buyerCompanyHope_el.name = 'buyerCompanyHope';
    buyerCompanyHope_el.id = 'buyerCompanyHope';
    buyerCompanyHope_el.value = buyer_hope;

    //수정,삭제버튼
    let modifyFormBuyer = document.createElement('div');
    modifyFormBuyer.classList.add('modifyFormBuyer');
    modifyFormBuyer.classList.add('modifyFormList');
    modifyFormBuyer.innerText = '수정';
    modifyFormBuyer.onclick = function(){ f_buyer_modify_modal('create', this); }
    let delFormBuyer = document.createElement('div');
    delFormBuyer.classList.add('delFormBuyer');
    delFormBuyer.classList.add('delFormList');
    delFormBuyer.onclick = function(){ f_buyer_remove('create', this); }
    delFormBuyer.innerText = '삭제';

    cont.appendChild(name);
    cont.appendChild(gubun);
    form_chuga_list.appendChild(cont);
    form_chuga_list.appendChild(modifyFormBuyer);
    form_chuga_list.appendChild(delFormBuyer);

    form_chuga_list.appendChild(buyerCompanyName_el);
    form_chuga_list.appendChild(buyerCompanyCountry_el);
    form_chuga_list.appendChild(buyerCompanyLocation_el);
    form_chuga_list.appendChild(buyerCompanyHomepage_el);
    form_chuga_list.appendChild(buyerCompanyDepart_el);
    form_chuga_list.appendChild(buyerCompanyPosition_el);
    form_chuga_list.appendChild(buyerCompanyEmail_el);
    form_chuga_list.appendChild(buyerCompanyTel_el);
    form_chuga_list.appendChild(buyerCompanyPhone_el);
    form_chuga_list.appendChild(buyerCompanyFax_el);
    form_chuga_list.appendChild(buyerCompanyItem_el);
    form_chuga_list.appendChild(buyerCompanyInviteReason_el);
    form_chuga_list.appendChild(buyerCompanyProgressYn_el);
    form_chuga_list.appendChild(buyerCompanyHope_el);

    buyer_add_btn.before(form_chuga_list);

    /* 팝업창 닫기 */
    $('#kt_buyer_popup').modal('hide');

    f_buyer_init(); // input 초기화

}

function f_buyer_init(){
    // 회사명
    document.querySelector('#kt_buyer_popup #buyerCompanyName').value = null;
    // 국가
    document.querySelector('#kt_buyer_popup #buyerCompanyCountry').value = null;
    // 소재지
    document.querySelector('#kt_buyer_popup #buyerCompanyLocation').value = null;
    // 홈페이지
    document.querySelector('#kt_buyer_popup #buyerCompanyHomepage').value = null;
    // 부서
    document.querySelector('#kt_buyer_popup #buyerCompanyDepart').value = null;
    // 직책
    document.querySelector('#kt_buyer_popup #buyerCompanyPosition').value = null;
    // 이메일
    document.querySelector('#kt_buyer_popup #buyer_company_email_input_1').value = null;
    document.querySelector('#kt_buyer_popup #buyer_company_email_input_2').value = null;
    if(document.querySelector('#kt_buyer_popup #buyer_company_email_input_2').disabled){
        document.querySelector('#kt_buyer_popup #buyer_company_email_input_2').disabled = false;
    }
    document.querySelector('#kt_buyer_popup #buyer_company_email_select').selectedIndex = 0;
    // 전화번호
    document.querySelector('#kt_buyer_popup #buyerCompanyTel').value = null;
    // 휴대전화
    document.querySelector('#kt_buyer_popup #buyerCompanyPhone').value = null;
    // 팩스
    document.querySelector('#kt_buyer_popup #buyerCompanyFax').value = null;
    // 취급품목
    document.querySelector('#kt_buyer_popup #buyerCompanyItem').value = null;
    // 초청사유
    document.querySelector('#kt_buyer_popup #buyerCompanyInviteReason').value = null;
    // 계약진행여부
    $('#kt_buyer_popup #buyerCompanyProgressYn').val('Y').select2({minimumResultsForSearch: Infinity});
    // 희망사항
    document.querySelector('#kt_buyer_popup #buyerCompanyHope').value = null;
}

function f_buyer_modify_modal(gbn, value){
    console.log(gbn, value);
    if(gbn === 'select'){
        let jsonObj = {
            "seq": value
        };
        let resData = ajaxConnect('/mng/participant/company/list/buyerSingle.do','post',jsonObj);

        f_buyer_modal_set(resData);

    }else{
        let row_el = $(value).parent('div');
        let jsonObj = {
            "buyerCompanyName": row_el.find('input[type=hidden][name=buyerCompanyName]').val(),
            "buyerCompanyCountry": row_el.find('input[type=hidden][name=buyerCompanyCountry]').val(),
            "buyerCompanyLocation": row_el.find('input[type=hidden][name=buyerCompanyLocation]').val(),
            "buyerCompanyHomepage": row_el.find('input[type=hidden][name=buyerCompanyHomepage]').val(),
            "buyerCompanyDepart": row_el.find('input[type=hidden][name=buyerCompanyDepart]').val(),
            "buyerCompanyPosition": row_el.find('input[type=hidden][name=buyerCompanyPosition]').val(),
            "buyerCompanyEmail": row_el.find('input[type=hidden][name=buyerCompanyEmail]').val(),
            "buyerCompanyTel": row_el.find('input[type=hidden][name=buyerCompanyTel]').val(),
            "buyerCompanyPhone": row_el.find('input[type=hidden][name=buyerCompanyPhone]').val(),
            "buyerCompanyFax": row_el.find('input[type=hidden][name=buyerCompanyFax]').val(),
            "buyerCompanyItem": row_el.find('input[type=hidden][name=buyerCompanyItem]').val(),
            "buyerCompanyInviteReason": row_el.find('input[type=hidden][name=buyerCompanyInviteReason]').val(),
            "buyerCompanyProgressYn": row_el.find('input[type=hidden][name=buyerCompanyProgressYn]').val(),
            "buyerCompanyHope": row_el.find('input[type=hidden][name=buyerCompanyHope]').val()
        }

        f_buyer_modal_set(jsonObj);

    }

    $('#kt_buyer_popup').modal('show');
    document.querySelector('#kt_buyer_popup').scrollTop = 0;
}

function f_buyer_modal_set(jsonObj){
    $('#kt_buyer_popup #buyerCompanyName').val(jsonObj.buyerCompanyName);
    $('#kt_buyer_popup #buyerCompanyCountry').val(jsonObj.buyerCompanyCountry);
    $('#kt_buyer_popup #buyerCompanyLocation').val(jsonObj.buyerCompanyLocation);
    $('#kt_buyer_popup #buyerCompanyHomepage').val(jsonObj.buyerCompanyHomepage);
    $('#kt_buyer_popup #buyerCompanyDepart').val(jsonObj.buyerCompanyDepart);
    $('#kt_buyer_popup #buyerCompanyPosition').val(jsonObj.buyerCompanyPosition);
    $('#kt_buyer_popup #buyer_company_email_input_1').val(jsonObj.buyerCompanyEmail.split('@')[0]);
    $('#kt_buyer_popup #buyer_company_email_input_2').val(jsonObj.buyerCompanyEmail.split('@')[1]);

    let optionExists = false;
    $('#kt_buyer_popup #buyer_company_email_select option').each(
        function(){
            if (this.value === jsonObj.buyerCompanyEmail.split('@')[1]) {
                optionExists = true;
                return false;
            }
        }
    );

    if(optionExists){
        $('#kt_buyer_popup #buyer_company_email_select').val(jsonObj.buyerCompanyEmail.split('@')[1]).prop("selected",true);
    }else{
        $('#kt_buyer_popup #buyer_company_email_select option').eq(0).prop('selected',true);
    }

    $('#kt_buyer_popup #buyerCompanyTel').val(jsonObj.buyerCompanyTel);
    $('#kt_buyer_popup #buyerCompanyPhone').val(jsonObj.buyerCompanyPhone);
    $('#kt_buyer_popup #buyerCompanyFax').val(jsonObj.buyerCompanyFax);
    $('#kt_buyer_popup #buyerCompanyItem').val(jsonObj.buyerCompanyItem);
    $('#kt_buyer_popup #buyerCompanyInviteReason').val(jsonObj.buyerCompanyInviteReason);
    $('#kt_buyer_popup #buyerCompanyProgressYn').val(jsonObj.buyerCompanyProgressYn).select2({minimumResultsForSearch: Infinity});
    $('#kt_buyer_popup #buyerCompanyHope').val(jsonObj.buyerCompanyHope);
}

function f_buyer_remove(gbn, el){
    //console.log(gbn, el);

    Swal.fire({
        title: '선택한 바이어 정보를 삭제하시겠습니까?',
        icon: 'warning',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#d33',
        confirmButtonText: '삭제하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            if(gbn === 'select'){
                let jsonObj = {
                    "seq": value
                };
                let resData = ajaxConnect('/mng/participant/company/list/deleteBuyer.do','post',jsonObj);
                if(resData.resultCode === "0"){
                    $(el).closest('.form_chuga_list').remove();
                }
            }else{
                $(el).closest('.form_chuga_list').remove();
            }
        }//isConfirmed
    }); //swal

}

function f_transfer_company_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_transfer_company_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    let nowYear = transferYear;

    if(nvl(searchText,'') === ''){
        jsonObj = {
            nowYear: nowYear,
        };
    }else{
        jsonObj = {
            transferYear: $('#transferYear option:selected').val(),
            lang: $('#lang option:selected').val(),
            condition: $('#search_box option:selected').val(),
            searchText: searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/transfer/company/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_transfer_company_table tbody td');
    let cnt = 0;
    jb.each(function(index, item){
        let itemText = $(item).text();
        let itemText_trim = itemText.replaceAll(' ','');
        if(itemText_trim !== '' && !itemText.match('Actions')){
            $(item).attr('data-bs-toggle', 'tooltip');
            $(item).attr('data-bs-trigger', 'hover');
            $(item).attr('data-bs-custom-class', 'tooltip-inverse');
            $(item).attr('data-bs-placement', 'top');
            $(item).attr('title',itemText);
        }
        cnt++;
    })
    jb.tooltip();
}

function f_transfer_search_condition_box_change(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_transfer_company_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    let lang = $('#lang option:selected').val();
    let transferYear = $('#transferYear option:selected').val();

    let condition = $('#search_box option:selected').val();
    let searchText = $('#search_text').val();

    let jsonObj = {
        lang: lang, //언어
        transferYear: transferYear,
        condition: condition,
        searchText: searchText
    }

    //console.log(jsonObj);

    /* 목록 데이터 조회 */
    let resData = ajaxConnect('/mng/exhibitor/transfer/company/selectList.do', 'post', jsonObj);
    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_transfer_company_table tbody td');
    let cnt = 0;
    jb.each(function(index, item){
        let itemText = $(item).text();
        let itemText_trim = itemText.replaceAll(' ','');
        if(itemText_trim !== ''){
            $(item).attr('data-bs-toggle', 'tooltip');
            $(item).attr('data-bs-trigger', 'hover');
            $(item).attr('data-bs-custom-class', 'tooltip-inverse');
            $(item).attr('data-bs-placement', 'top');
            $(item).attr('title',itemText);
        }
        cnt++;
    })
    jb.tooltip();
}

function f_search_condition_init(){
    $('#search_box').val('').select2({minimumResultsForSearch: Infinity});
    $('#search_text').val('');
    $('#transferYear').val('').select2({minimumResultsForSearch: Infinity});
    $('#lang').val('').select2({minimumResultsForSearch: Infinity});

    /* 재조회 */
    f_transfer_company_search();
}

function f_transfer_company_detail(seq){
    window.location.href='/mng/exhibitor/transfer/company/detail.do?seq=' + seq;
}

function f_transfer_exhibitor_save(seq){

    if(nvl(seq,'') !== '') {
        let url = '/mng/exhibitor/transfer/company/update.do';

        Swal.fire({
            title: '입력된 정보를 저장하시겠습니까?',
            icon: 'info',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '변경내용저장',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                /* form data setting */
                let data = f_form_data_setting();

                //console.log('data : ' + data);

                $.ajax({
                    url: url,
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            /* file function */
                            let tableSeq = data.customValue; //tableSeq return 값
                            f_company_file_upload_call(tableSeq, tableSeq);

                            let timerInterval;
                            Swal.fire({
                                title: "정보 저장 중",
                                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해주세요.",
                                allowOutsideClick: false,
                                timer: 10000,
                                timerProgressBar: true,
                                didOpen: () => {
                                    Swal.showLoading();
                                    const timer = Swal.getPopup().querySelector("b");
                                    timerInterval = setInterval(() => {
                                        timer.textContent = `${Swal.getTimerLeft()}`;
                                    }, 1000);
                                },
                                willClose: () => {
                                    clearInterval(timerInterval);
                                }
                            }).then((result) => {
                                /* Read more about handling dismissals below */
                                if (result.dismiss === Swal.DismissReason.timer) {
                                    Swal.fire({
                                        title: '전시 업체 정보 저장',
                                        html: '전시 업체 정보가 저장되었습니다.',
                                        icon: 'info',
                                        allowOutsideClick: false,
                                        confirmButtonColor: '#3085d6',
                                        confirmButtonText: '확인'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            f_transfer_company_detail(tableSeq); // 재조회
                                        }
                                    })
                                }
                            });

                        } else {
                            showMessage('', 'error', '에러 발생', '전시 업체 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })

            }//isConfirmed
        })//then
    }else{
        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : NOT FOUND EXHIBITOR SEQ');
    }
}

function f_form_data_setting(){

    /* 상세정보 */
    let companyForm = JSON.parse(JSON.stringify($('#companyForm').serializeObject()));

    //언어
    companyForm.lang = 'KO';
    companyForm.transferYear = $('#preTransferYear').val();

    //참가비수납여부
    companyForm.prcYn = $('#condition_prc_yn option:selected').val();

    //담당자 Setting
    let chargeInfoCnt = $('.chargeInfoNum:last').text();
    let chargeInfoArr = [];
    for(let i=0; i<chargeInfoCnt; i++){
        let chargeInfoObj = {
            seq: $('input[type=hidden][name=chargeSeq]').eq(i).val(),
            userId: companyForm.id,
            transferYear: companyForm.transferYear,
            chargePersonName: $('input[name=chargePersonName]').eq(i).val(),
            chargePersonPosition: $('input[name=chargePersonPosition]').eq(i).val(),
            chargePersonDepart: $('input[name=chargePersonDepart]').eq(i).val(),
            chargePersonTel: $('input[name=chargePersonTel]').eq(i).val(),
            chargePersonPhone: $('input[name=chargePersonPhone]').eq(i).val(),
            chargePersonEmail: $('input[name=chargePersonEmail]').eq(i).val() + '@' + $('input[name=chargePersonDomain]').eq(i).val()
        };
        chargeInfoArr.push(chargeInfoObj);
    }
    companyForm.chargePersonList = chargeInfoArr;

    //기참가연도
    let prePartYearList = $('input[type=checkbox][name=prePartYear]:checked');
    let prePartYearListLen = prePartYearList.length;
    let prePartYear = '';
    for(let i=0; i<prePartYearListLen; i++){
        prePartYear += prePartYearList.eq(i).val();
        if((i+1) !== prePartYearListLen){
            prePartYear += ',';
        }
    }
    companyForm.prePartYear = prePartYear;

    //참가분야
    let fieldPartList = $('input[type=checkbox][name=fieldPart]:checked');
    let fieldPartListLen = fieldPartList.length;
    let fieldPart = '';
    for(let i=0; i<fieldPartListLen; i++){
        fieldPart += fieldPartList.eq(i).val();
        if((i+1) !== fieldPartListLen){
            fieldPart += ',';
        }
    }
    companyForm.fieldPart = fieldPart;

    //기업뱃지
    let companyBadgeList = $('input[type=checkbox][name=companyBadge]:checked');
    let companyBadgeLen = companyBadgeList.length;
    let companyBadge = '';
    for(let i=0; i<companyBadgeLen; i++){
        companyBadge += companyBadgeList.eq(i).val();
        if((i+1) !== companyBadgeLen){
            companyBadge += ',';
        }
    }
    companyForm.companyBadge = companyBadge;

    //전시정보 Setting
    let displayInfoCnt = $('.exhiInfoNum:last').text();
    let displayInfoArr = [];
    for(let i=0; i<displayInfoCnt; i++){
        let displayInfoObj = {
            seq: $('input[type=hidden][name=displayItemSeq]').eq(i).val(),
            userId: companyForm.id,
            transferYear: companyForm.transferYear,
            displayItem: $('input[name=displayItem]').eq(i).val(),
            displayBrand: $('input[name=displayBrand]').eq(i).val(),
            displayBoatCnt: $('input[name=displayBoatCnt]').eq(i).val()
        };
        displayInfoArr.push(displayInfoObj);
    }
    companyForm.displayList = displayInfoArr;

    //온라인전시회 Setting
    let onlineInfoCnt = $('.onlineInfoNum:last').text();
    let onlineInfoArr = [];
    for(let i=0; i<onlineInfoCnt; i++){
        let productOptionBig = '';
        let productOptionSmall = '';
        let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
        if(nvl(selProductOptionBig,"선택") !== "선택"){
            productOptionBig = $('select[name=productOptionBig]').eq(i).val();
            productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
        }else{
            productOptionBig = $('input[type=text][name=productOptionBig]').eq(i).val();
            productOptionSmall = $('input[type=text][name=productOptionSmall]').eq(i).val();
        }

        let onlineInfoObj = {
            seq: $('input[type=hidden][name=onlineItemSeq]').eq(i).val(),
            userId: companyForm.id,
            transferYear: companyForm.transferYear,
            productNameKo: $('input[name=productNameKo]').eq(i).val(),
            productNameEn: $('input[name=productNameEn]').eq(i).val(),
            productOptionBig: productOptionBig,
            productOptionSmall: productOptionSmall,
            productIntroKo: $('textarea[name=productIntroKo]').eq(i).val(),
            productIntroEn: $('textarea[name=productIntroEn]').eq(i).val(),
            productIntroVideo: $('input[name=productIntroVideo]').eq(i).val(),
            productWidth: $('input[name=productWidth]').eq(i).val(),
            productHorsePower: $('input[name=productHorsePower]').eq(i).val(),
            note: (i+1)
        };
        onlineInfoArr.push(onlineInfoObj);
    }
    companyForm.onlineExhibitList = onlineInfoArr;

    //수출상담회 Setting
    let exportInfoCnt = document.getElementsByClassName('form_chuga_list').length;
    let exportInfoArr = [];
    for(let i=0; i<exportInfoCnt; i++){
        let exportInfoObj = {
            seq: $('input[type=hidden][name=buyerItemSeq]').eq(i).val(),
            userId: companyForm.id,
            transferYear: companyForm.transferYear,
            buyerCompanyName: $('input[type=hidden][name=buyerCompanyName]').eq(i).val(),
            buyerCompanyCountry: $('input[type=hidden][name=buyerCompanyCountry]').eq(i).val(),
            buyerCompanyLocation: $('input[type=hidden][name=buyerCompanyLocation]').eq(i).val(),
            buyerCompanyHomepage: $('input[type=hidden][name=buyerCompanyHomepage]').eq(i).val(),
            buyerCompanyDepart: $('input[type=hidden][name=buyerCompanyDepart]').eq(i).val(),
            buyerCompanyPosition: $('input[type=hidden][name=buyerCompanyPosition]').eq(i).val(),
            buyerCompanyEmail: $('input[type=hidden][name=buyerCompanyEmail]').eq(i).val(),
            buyerCompanyTel: $('input[type=hidden][name=buyerCompanyTel]').eq(i).val(),
            buyerCompanyPhone: $('input[type=hidden][name=buyerCompanyPhone]').eq(i).val(),
            buyerCompanyFax: $('input[type=hidden][name=buyerCompanyFax]').eq(i).val(),
            buyerCompanyItem: $('input[type=hidden][name=buyerCompanyItem]').eq(i).val(),
            buyerCompanyInviteReason: $('input[type=hidden][name=buyerCompanyInviteReason]').eq(i).val(),
            buyerCompanyProgressYn: $('input[type=hidden][name=buyerCompanyProgressYn]').eq(i).val(),
            buyerCompanyHope: $('input[type=hidden][name=buyerCompanyHope]').eq(i).val()
        };
        exportInfoArr.push(exportInfoObj);
    }
    companyForm.buyerList = exportInfoArr;

    return JSON.stringify(companyForm);
}

function f_company_file_upload_call(id, path) {

    /* 사업자 등록증 */
    let companyLicenseFile = document.getElementById('companyLicenseFile').value;
    if (nvl(companyLicenseFile, '') !== '') {
        //console.log('사업자등록증 파일 업로드');
        f_company_file_upload(id, 'companyForm', 'companyLicenseFile', 'exhibitor/company/' + path);
    }

    /* 로고 */
    let logoFile = document.getElementById('logoFile').value;
    if (nvl(logoFile, '') !== '') {
        //console.log('로고 파일 업로드');
        f_company_file_upload(id, 'companyForm', 'logoFile', 'exhibitor/company/' + path);
    }

    /* 홍보이미지 */
    let promotionImageFileList = document.getElementsByName('promotionImageFile');
    for(let i=0; i<promotionImageFileList.length; i++){
        let promotionImageFile = promotionImageFileList[i].value;
        if (nvl(promotionImageFile, '') !== '') {
            //console.log('홍보이미지 파일 업로드' + (i+1));
            f_company_file_upload(id, 'companyForm', 'promotionImageFile'+(i+1), 'exhibitor/company/' + path);
        }
    }

    /* 제품사진 */
    let productImageFileList = $('input[type=file][name=productImageFile]');
    for(let i=0; i<productImageFileList.length; i++){
        let productImageNum = productImageFileList[i].id;
        productImageNum = productImageNum.toString().replace('productImageFile','');
        let productImageFile = productImageFileList[i].value;
        if (nvl(productImageFile, '') !== '') {
            //console.log('제품사진 파일 업로드' + (i+1));
            f_company_file_upload(id, 'companyForm', 'productImageFile' + productImageNum, 'exhibitor/company/' + path);
        }
    }
}

async function f_company_file_upload(userId, formId, elementId, path) {
    let uploadFileResponse = '';
    uploadFileResponse = await f_company_uploadFile(formId, elementId, path);
    if (nvl(uploadFileResponse, "") !== '') {
        let fullFilePath = uploadFileResponse.replaceAll('\\', '/');
        // ./tomcat/webapps/upload/center/board/notice/b3eb661d-34de-4fd0-bc74-17db9fffc1bd_KIBS_TV_목록_excel_20230817151752.xlsx

        let fullPath = fullFilePath.substring(0, fullFilePath.lastIndexOf('/') + 1);
        // ./tomcat/webapps/upload/center/board/notice/

        let pureFileNameSplit = fullFilePath.split('/');
        let fullFileName = pureFileNameSplit[pureFileNameSplit.length - 1];
        // b3eb661d-34de-4fd0-bc74-17db9fffc1bd_KIBS_TV_목록_excel_20230817151752.xlsx

        /*let uuid = fullFileName.substring(0, fullFileName.indexOf('_'));
        // b3eb661d-34de-4fd0-bc74-17db9fffc1bd

        let fileName = fullFileName.substring(fullFileName.indexOf('_') + 1);
        // KIBS_TV_목록_excel_20230817151752.xlsx*/

        let folderPath = pureFileNameSplit[pureFileNameSplit.length - 2];
        // notice

        let note = elementId.replace('File', '');

        let jsonObj = {
            userId: userId,
            fullFilePath: fullFilePath,
            fullPath: fullPath,
            folderPath: folderPath,
            fullFileName: fullFileName,
            /*uuid: uuid,*/
            fileName: fullFileName,
            fileYn: 'Y',
            note: note
        };
        let resData = ajaxConnect('/file/upload/save.do', 'post', jsonObj);
        if (resData.resultCode === "0") {
            /*let parents_el = document.querySelector('#' + note);
            let fileId_el = document.createElement('input');
            fileId_el.type = 'hidden';
            fileId_el.id = note+'1';
            fileId_el.name = note+'1';
            fileId_el.value = resData.fileId;

            parents_el.appendChild(fileId_el);*/
        }
    }
}

function f_company_uploadFile(formId, elementId, path) {
    /* 파일 업로드 */
    let fileForm = document.getElementById(formId);
    let formData = new FormData(fileForm);
    if(nvl(elementId,'') !== ''){
        let file = document.querySelector('#' + elementId);
        formData.append('request',file.files[0]);
    }

    return new Promise((resolve, reject) => {
        fetch('/file/upload.do?gbn=' + path, {
            method: 'post',
            body: formData
        })
            .then(function (response) {
                return response.json();
            })
            .then(res => {
                if( typeof res.uploadPath !== undefined){
                    resolve(res.uploadPath + '\\' + res.fileName);
                }
            })

    });
}

function f_transfer_company_excel_export(){
    Swal.fire({
        title: '전시업체 상세 다운로드',
        html: '전시업체 상세를 다운로드하시겠습니까 ?<br><span style="font-weight: bold; font-size: 1rem;">' +
            '( 데이터가 많을 경우 시간이 소요될 수 있습니다. )<br>( 해당 창이 닫혀도 다운로드가 완료될 때까지<br>대기해 주세요. )</span>',
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        confirmButtonText: '확인',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 로딩페이지 */
            loadingBarShow_time(7000);

            let form = document.createElement('form');
            form.setAttribute('action','/mng/exhibitor/transfer/download.do');
            form.setAttribute('method','get');

            let obj = document.createElement('input');
            obj.setAttribute('type', 'hidden');
            obj.setAttribute('name', 'fileName');
            obj.setAttribute('value', 'transfer_exhibitor_list_' + getCurrentDate() + '.xlsx');

            let obj2 = document.createElement('input');
            obj2.setAttribute('type', 'hidden');
            obj2.setAttribute('name', 'transferYear');
            obj2.setAttribute('value', transferYear);

            form.appendChild(obj);
            form.appendChild(obj2);
            document.body.appendChild(form);
            form.submit();
        }
    });
}

/*
@author https://github.com/macek/jquery-serialize-object
*/
$.fn.serializeObject = function () {
    "use strict";
    var result = {};
    var extend = function (i, element) {
        var node = result[element.name];
        if ("undefined" !== typeof node && node !== null) {
            if ($.isArray(node)) {
                node.push(element.value);
            } else {
                result[element.name] = [node, element.value];
            }
        } else {
            result[element.name] = element.value;
        }
    };

    $.each(this.serializeArray(), extend);
    return result;
};