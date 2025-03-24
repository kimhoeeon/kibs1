var transferYear = (new Date().getFullYear()).toString();

$(function() {

    /*if (!window.location.href.includes('localhost')) {
        if (window.location.protocol !== "https:") {
            window.location.href = "https:" + window.location.href.substring(window.location.protocol.length);
        }

        if (document.location.protocol === "http:") {
            document.location.href = document.location.href.replace('http:', 'https:');
        }
    }*/

})
function home(){
    window.location.href = '/';
}

function ajaxConnect(url, method, jsonStr){
    let result;
    $.ajax({
        url: url,
        method: method,
        async: false,
        data: JSON.stringify(jsonStr),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
        .done(function (data) {
            result = data;
        })
        .fail(function (xhr, status, errorThrown) {
            /*$('body').html("오류가 발생했습니다.")
                .append("<br>오류명: " + errorThrown)
                .append("<br>상태: " + status);*/

            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
            result = "fail";
        })
    return result;
}

function ajaxConnectSimple(url, method, jsonStr){
    let result = '';
    $.ajax({
        url: url,
        method: method,
        async: false,
        data: JSON.stringify(jsonStr),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
    .done(function (data) {
        result = data;
    })
    .fail(function (xhr, status, errorThrown) {
        /*$('body').html("오류가 발생했습니다.")
            .append("<br>오류명: " + errorThrown)
            .append("<br>상태: " + status);*/

        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
    })
    return result;
}

function showMessage(selector, icon, title, msg, confirmButtonColor) {
    if (typeof icon == "undefined" || title == null) icon = 'info';
    if (typeof title == "undefined" || title == null) title = '';
    if (typeof confirmButtonColor == "undefined" || confirmButtonColor == null || confirmButtonColor === '') confirmButtonColor = '#00a8ff';

    if( selector !== '' ) {
        $(':focus').trigger('blur');
    }

    Swal.fire({
        icon: icon,
        title: title,
        html: '<span style="font-size: 1.1em;">' + msg + '</span>',
        allowOutsideClick: false,
        confirmButtonColor: confirmButtonColor
    })
    .then(() => {
        if( selector && selector !== '' ){
            setTimeout(function() {
                $(selector).trigger('focus');
            }, 200);
        }
    });
}

function getCurrentDate() {
    let date = new Date(); // Data 객체 생성
    let year = date.getFullYear().toString(); // 년도 구하기

    let month = date.getMonth() + 1; // 월 구하기
    month = month < 10 ? '0' + month.toString() : month.toString(); // 10월 미만 0 추가

    let day = date.getDate(); // 날짜 구하기
    day = day < 10 ? '0' + day.toString() : day.toString(); // 10일 미만 0 추가

    let hour = date.getHours(); // 시간 구하기
    hour = hour < 10 ? '0' + hour.toString() : hour.toString(); // 10시 미만 0 추가

    let minites = date.getMinutes(); // 분 구하기
    minites = minites < 10 ? '0' + minites.toString() : minites.toString(); // 10분 미만 0 추가

    let seconds = date.getSeconds(); // 초 구하기
    seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString(); // 10초 미만 0 추가

    return year + month + day + hour + minites + seconds; // yyyymmddhhmmss 형식으로 리턴
}

function minCnt(el, cnt){
    let val = $.number($(el).val() || 0);
    if(val < cnt){
        if(val !== $.number(0)){
            alert('독립부스는 2부스부터 신청 가능합니다.');
            $(el).val(0);
            checkBooth();
            autoSum(1);
            autoTotalSum();
            return false;
        }
    }
}

function checkBooth(){
    let standAloneBoothCnt = $.number((parseInt($('#stand_alone_booth_cnt').val() || 0)));
    let assemblyBoothCnt = $.number((parseInt($('#assembly_booth_cnt').val() || 0)));
    let onlineBoothCnt = $.number((parseInt($('#online_booth_cnt option:selected').val() || 0)));
    if((standAloneBoothCnt > 0 || assemblyBoothCnt > 0) && onlineBoothCnt > 0){
        alert('조립부스 또는 독립부스 신청 시, 온라인 부스는 무료 지원됩니다.');
        $('#online_booth_cnt').val(0);
        $('#online_booth_cnt option').eq(0).prop('selected',true);
        autoSum(3);
    }
}

function autoSum(index){
    let boothCost = $('.booth_cost');
    let sum = $('.num_sum');
    let cost = wonToNumber(boothCost[index].innerText);

    let qty = 0;
    switch (index){
        case 1: qty = $.number((parseInt($('#stand_alone_booth_cnt').val() || 0))); break; //독립부스
        case 2: qty = $.number((parseInt($('#assembly_booth_cnt').val() || 0))); break; //조립부스
        case 3: qty = $.number((parseInt($('#online_booth_cnt option:selected').val() || 0))); break; //온라인부스
        default: break;
    }

    let totalSum = (cost * qty);

    sum[index].value = (numberToWon(totalSum));
}

function autoTotalSum(){
    let sum = $('.num_sum');
    let totalSum = 0;
    for(let i=0; i<4/*sum.length*/; i++){
        totalSum += wonToNumber(sum.eq(i).val());
    }

    $('#form_add_total').val(numberToWon(totalSum));

    // CheckBox 검사
    if ($('.form_disc input[type=checkbox]').is(":checked")){

        let names = [];

        $('.select input:checked').each(function() {
            names.push(this.id); //Check된 Check박스의 "Id"태그 얻기.
        });

        //console.log(names);

        for(let i=0; i<names.length; i++){
            let discountPrc = '0';
            switch (names[i]){
                case 'discount1': discountPrc = '300000'; break;
                case 'discount2': discountPrc = '200000'; break;
                case 'discount3': discountPrc = '200000'; break;
                case 'discount5': discountPrc = '300000'; break;
                case 'discount6': discountPrc = '450000'; break;
                case 'discount7': discountPrc = '500000'; break;
                case 'discount8': discountPrc = '200000'; break;
                default: break;
            }
            autoDiscountSum($('#'+names[i]),discountPrc);
        }
    }

}

function autoDiscountSum(checkbox, discountPrc){

    let discountId = $(checkbox).prop('id');

    if(discountId === 'discount1'){
        let discount2Checked = $('#discount2').is(':checked');
        if(discount2Checked){
            $('#discount2').prop('checked',false);
        }
    }

    if(discountId === 'discount2'){
        let discount1Checked = $('#discount1').is(':checked');
        if(discount1Checked){
            $('#discount1').prop('checked',false);
        }
    }

    if(discountId === 'discount3'){
        let discount7Checked = $('#discount7').is(':checked');
        if(discount7Checked){
            $('#discount7').prop('checked',false);
        }
    }

    if(discountId === 'discount7'){
        let discount3Checked = $('#discount3').is(':checked');
        if(discount3Checked){
            $('#discount3').prop('checked',false);
        }
    }

    if(discountId === 'discount5'){
        let discount6Checked = $('#discount6').is(':checked');
        if(discount6Checked){
            $('#discount6').prop('checked',false);
        }
    }

    if(discountId === 'discount6'){
        let discount5Checked = $('#discount5').is(':checked');
        if(discount5Checked){
            $('#discount5').prop('checked',false);
        }
    }

    // console.log(checkbox.id , $(checkbox).is(":checked"));
    // discount1 - 1 부스당 300,000 원 할인
    // discount2 - 1 부스당 200,000 원 할인
    // discount3 - 1 부스당 200,000 원 할인
    // discount5 - 1 부스당 300,000 원 할인
    // discount6 - 1 부스당 450,000 원 할인
    // discount7 - 1 부스당 500,000 원 할인
    // discount8 - 1 부스당 200,000 원 할인

    // 부스 수량 가져오기
    let boothSum = parseInt($('#stand_alone_booth_cnt').val() || 0)
        + parseInt($('#assembly_booth_cnt').val() || 0)
        + parseInt($('#online_booth_cnt option:selected').val());

    if(boothSum > 0) {

        let boothPrc = (parseInt($('#stand_alone_booth_cnt').val() || 0) * 1800000)
            + (parseInt($('#assembly_booth_cnt').val() || 0) * 2100000)
            + (parseInt($('#online_booth_cnt option:selected').val()) * 1000000)
            + 100000;

        let discount_checkBox = $('input[type=checkbox][name=discount]:checked');
        let discountPrc = 0;
        for(let i=0; i<discount_checkBox.length; i++){
            discountPrc += boothSum * parseInt(discount_checkBox.eq(i).val());
        }

        let totalSum = boothPrc;
        totalSum = wonToNumber(totalSum) - discountPrc;

        if(wonToNumber(totalSum) > 0){
            $('#form_add_total').val(numberToWon(totalSum));
        }else{
            $('#form_add_total').val(numberToWon("0"));
        }

    } else {
        alert("부스 신청 수량을 입력해 주세요.\nPlease enter the booth application quantity.");
        $(checkbox).removeAttr('checked');
    }
}

function wonToNumber(won){
    return Number.parseInt(won.toString().replaceAll("￦ ","").replaceAll(",",""), 10);
}

function numberToWon(number){
    return "￦ " + number.toLocaleString();
}

function execDaumPostcode(address, addressDetail) {
    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    new daum.Postcode({
        width: width, //생성자에 크기 값을 명시적으로 지정해야 합니다.
        height: height,
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                // document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                // document.getElementById("sample6_extraAddress").value = '';
            }

            if(nvl(address,"") !== "" && nvl(addressDetail,"") !== ""){
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById(address).value = '(' + data.zonecode + ') ' + addr;

                // 우편번호 클릭시 초기화
                document.getElementById(addressDetail).value = '';

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById(addressDetail).focus();
            }else{
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('address').value = '(' + data.zonecode + ') ' + addr;

                // 우편번호 클릭시 초기화
                document.getElementById('address_detail').value = '';

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('address_detail').focus();
            }
        }
    }).open({
        left: (window.screen.width / 2) - (width / 2),
        top: (window.screen.height / 2) - (height / 2),
        popupTitle: '우편번호 검색 팝업', //팝업창 타이틀 설정 (영문,한글,숫자 모두 가능)
        popupKey: 'popup1' //팝업창 Key값 설정 (영문+숫자 추천)
    });
}

function f_pw_init(){
    let id = $('#id').val();

    if(nvl(id,'') === ''){
        showMessage('#id', 'error', '[회원 정보]', 'ID를 입력해주세요.', '');
        return false;
    }

    // ID 체크
    let jsonStr = { id : id , transferYear: transferYear}; // 2025
    let checkDuplicateId = ajaxConnect('/checkDuplicateId.do', 'post', jsonStr);
    if(checkDuplicateId !== 0){
        Swal.fire({
            title: '[회원 정보]',
            html: '해당 ID [ ' + id + ' ] 의<br>비밀번호 초기화를 요청하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '요청하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let email = ajaxConnectSimple('/getExhibitorEmail.do', 'post', jsonStr);
                if(nvl(email,'') !== ''){
                    let jsonObj = {
                        "subject": '[2025 경기국제보트쇼] 비밀번호 초기화 요청', //제목
                        "body": "", //본문
                        "template": "12", //템플릿 번호
                        "receiver": [{"email": email}]
                    }

                    let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                    //console.log(i , resData);
                    if (resData.resultCode === "0") {
                        /* 비밀번호 초기화 */
                        let res = ajaxConnect('/updateExhibitorPasswordInit.do', 'post', jsonStr);
                        if(res.resultCode !== "0"){
                            showMessage('', 'error', '[회원 정보]', '비밀번호 초기화에 실패하였습니다. 관리자에게 문의해주세요.', '');
                            return false;
                        }else{
                            Swal.fire({
                                title: '회원 정보',
                                html: '해당 ID의 비밀번호가 초기화되었습니다.<br>초기화 정보는 [ ' + email + ' ] 로 전송되었습니다.<br>로그인하신 후 비밀번호를 변경하여 이용해주세요.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            });
                            return false;
                        }
                    }else{
                        Swal.fire({
                            title: '회원 정보',
                            html: '해당 ID에 등록된 Email 주소로 메일 전송이 실패하였습니다.<br>경기국제보트쇼 사무국으로 문의 바랍니다.<br>Tel. 1670-8785',
                            icon: 'info',
                            allowOutsideClick: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '확인'
                        });
                        return false;
                    }
                }else{
                    Swal.fire({
                        title: '회원 정보',
                        html: '해당 ID에 등록된 Email 주소가 없습니다.<br>경기국제보트쇼 사무국으로 문의 바랍니다.<br>Tel. 1670-8785',
                        icon: 'info',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    });
                    return false;
                }
            }
        })
    }else{
        showMessage('', 'error', '[회원 정보]', '해당 ID로 가입된 업체 정보가 없습니다.', '');
        return false;
    }
}

/* 참가업체 참가신청 ValidCheck Fn */
function f_apply_comp(step, seq){
    //console.log(step);
    switch (step){
        case "01":
            step_01_check(seq);
            break;
        case "2_1":
            step_2_1_check(seq);
            break;
        case "2_2":
            step_2_2_check(seq);
            break;
        case "2_3":
            step_2_3_check(seq);
            break;
        case "2_4":
            step_2_4_check(seq);
            break;
        case "2_5":
            step_2_5_check(seq);
            break;
        case "2_6":
            step_2_6_check(seq);
            break;
        case "2_8":
            step_2_8_check(seq);
            break;
        case "03":
            step_03_check(seq);
            break;
        default:
            break;
    }
}

/* //////////////////////////////// begin:step_01 ////////////////////////////////// */

function f_phone_number_valid_check(el){
    let phoneNum = $(el).val();
    if(nvl(phoneNum,'') !== ''){
        if(phoneNum.length > 2){
            let num = phoneNum.substring(0,3);
            if(num !== '010'){
                alert('휴대전화번호는 앞자리 "010"으로 시작하는 번호만 등록 가능합니다.');
                $(el).val('');
            }
        }
    }
}

function f_id_license_num_set(input){
    $('#companyLicenseNum').val($(input).val().replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1'));
}

function f_id_duplicate_check(el){
    // ID
    let id = document.querySelector('#id').value;

    if(nvl(id,'') !== ''){
        if(id.length < 10){
            $(el).siblings('.cmnt').css('color', '#AD1D1D');
            $(el).siblings('.cmnt').html('검사 결과 : 사업자 등록번호 10자리를 입력해주세요.');
            $('#idCheck').val('false');
            return;
        }

        // ID 중복체크
        let jsonStr = { id : id , transferYear: transferYear}; // 2025
        let checkDuplicateId = ajaxConnect('/checkDuplicateId.do', 'post', jsonStr);
        if(checkDuplicateId !== 0){
            $(el).siblings('.cmnt').css('color', '#AD1D1D');
            $(el).siblings('.cmnt').html('중복 검사 결과 : 사용할 수 없는 아이디입니다. 해당 아이디로 이미 신청된 업체가 존재합니다.');
            $('#idCheck').val('false');
        }else{
            $(el).siblings('.cmnt').css('color', '#1D5CAD');
            $(el).siblings('.cmnt').html('중복 검사 결과 : 사용 가능한 아이디입니다.');
            $('#idCheck').val('true');
        }
    }
}

function f_pw_status_change(el){
    $(el).next('.cmnt').next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
    $('#pwCheck').val('false');

    $('#passwordCheck').val('');
    $('#passwordCheck').next('.cmnt').html('비밀번호를 다시 입력해주세요.');
    $('#pwConfirmCheck').val('false');
}

function f_pw_check(el){
    let pw = $("#password").val();
    let number = pw.search(/[0-9]/g);
    let english = pw.search(/[a-z]/ig);
    let space = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

    if (pw.length < 8 && pw.length > 17) {
        $(el).next('.cmnt').next('.cmnt').html('8자리 이상, 16자리 이내로 입력해주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    } else if (pw.search(/\s/) !== -1) {
        $(el).next('.cmnt').next('.cmnt').html('비밀번호는 공백 없이 입력해주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    } else if (number < 0 || english < 0 || space < 0) {
        $(el).next('.cmnt').next('.cmnt').html('영문, 숫자, 특수문자를 혼합하여 입력해주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    } else if ((number < 0 && english < 0) || (english < 0 && space < 0) || (space < 0 && number < 0)) {
        $(el).next('.cmnt').next('.cmnt').html('영문, 숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    } else if (/(\w)\1\1\1/.test(pw)) {
        $(el).next('.cmnt').next('.cmnt').html('같은 문자를 4번 이상 사용하실 수 없습니다.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }

    if (false === reg.test(pw)) {
        $(el).next('.cmnt').next('.cmnt').html('비밀번호는 8~16자리 이어야 하며, 숫자/영문/특수문자를 모두 포함해야 합니다.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    } else {
        $(el).next('.cmnt').next('.cmnt').html('비밀번호가 정상적으로 입력되었습니다.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#1D5CAD');
        $('#pwCheck').val('true');
    }
}

function f_pw_confirm_check(el){
    let pw = $('#password').val();
    let pwCheck = $('#passwordCheck').val();
    if(pw !== '' && pwCheck !== ''){
        if(pw !== pwCheck){
            $(el).next('.cmnt').html('비밀번호가 일치하지 않습니다.');
            $(el).next('.cmnt').css('color', '#AD1D1D');
            $('#pwConfirmCheck').val('false');
        }else{
            $(el).next('.cmnt').html('비밀번호가 일치합니다.');
            $(el).next('.cmnt').css('color', '#1D5CAD');
            $('#pwConfirmCheck').val('true');
        }
    }
}

function checkUrl(strUrl) {
    let expUrl = /^http[s]?:\/\/([\S]{3,})/i;
    return expUrl.test(strUrl);
}

function step_01_check(exhibitorSeq){

    let sessionId = sessionStorage.getItem('id');

    // 전시회 참가규정
    let agree1 = $('input[type=radio][name=agree1]:checked').val();
    if(nvl(agree1,'') === '' || agree1 === 'N'){
        showMessage('', 'info', '[약관 동의]', '전시회 참가규정에 동의해 주세요.', '');
        return false;
    }

    // 개인정보 취급방침
    let agree2 = $('input[type=radio][name=agree2]:checked').val();
    if(nvl(agree2,'') === '' || agree2 === 'N'){
        showMessage('', 'info', '[약관 동의]', '개인정보 취급방침에 동의해 주세요.', '');
        return false;
    }

    // ID
    let id = $('#id').val();
    if(id === ''){
        showMessage('', 'error', '[회원 계정 정보]', '아이디를 입력해 주세요.', '');
        return false;
    }

    // ID
    let idCheck = $('#idCheck').val();
    if(idCheck === 'false' && nvl(sessionId,"") === ""){
        showMessage('', 'error', '[회원 계정 정보]', '아이디 중복 검사를 수행해 주세요.', '');
        return false;
    }

    // 비밀번호
    let password = $('#password').val();
    let passwordCheck = $('#passwordCheck').val();
    if(password === ''){
        showMessage('', 'error', '[회원 계정 정보]', '비밀번호를 입력해 주세요.', '');
        return false;
    }

    // 비밀번호 유효성
    let pwCheck = $('#pwCheck').val();
    if(pwCheck === 'false'){
        showMessage('', 'error', '[회원 계정 정보]', '비밀번호 유효성 검사를 수행해 주세요.', '');
        return false;
    }

    // 비밀번호 확인 유효성
    let pwConfirmCheck = $('#pwConfirmCheck').val();
    if(pwConfirmCheck === 'false'){
        showMessage('', 'error', '[회원 계정 정보]', '비밀번호 확인을 입력해 주세요.', '');
        return false;
    }

    // 성명
    let name = $('#name').val();
    if(name === ''){
        showMessage('', 'error', '[회원 계정 정보]', '성명을 입력해 주세요.', '');
        return false;
    }
    
    // 직위
    let position = $('#position').val();
    if(position === ''){
        showMessage('', 'error', '[회원 계정 정보]', '직위를 입력해 주세요.', '');
        return false;
    }

    // 부서
    let depart = $('#depart').val();

    // 이메일
    let email_input1 = $('#email_input1').val();
    let email_input2 = $('#email_input2').val();
    if(email_input1 === ''){
        showMessage('', 'error', '[회원 계정 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }
    if(email_input2 === ''){
        showMessage('', 'error', '[회원 계정 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    // 이메일 마케팅 수신 동의 여부
    let email_marketing_yn_el = $('input[type=radio][name=email_marketing_yn]:checked');
    let email_marketing_yn;
    if(email_marketing_yn_el === null){
        showMessage('', 'error', '[회원 계정 정보]', 'E-mail 마케팅정보 수신동의여부를 체크해주세요.', '');
        return false;
    }else{
        email_marketing_yn = email_marketing_yn_el.val();
    }
    //console.log(email_marketing_yn);

    // 전화번호
    let tel = $('#tel').val();
    if(tel === ''){
        showMessage('', 'error', '[회원 계정 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 휴대전화
    let phone = $('#phone').val();
    if(phone === ''){
        showMessage('', 'error', '[회원 계정 정보]', '휴대전화번호를 입력해 주세요.', '');
        return false;
    }
    if ( !/^010-[0-9]{4}-[0-9]{4}$/.test( phone ) ) {
        showMessage('', 'error', '[회원 계정 정보]', '올바른 휴대전화번호를 입력해 주세요.<br>(앞자리 010 만 가능합니다.)', '');
        return false;
    }

    // 회사명
    let company_name_ko = $('#companyNameKo').val();
    if(company_name_ko === ''){
        showMessage('', 'error', '[참가 업체 정보]', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_name_en = $('#companyNameEn').val();
    if(company_name_en === ''){
        showMessage('', 'error', '[참가 업체 정보]', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 주소
    let address = $('#companyAddress').val();
    if(address === ''){
        showMessage('', 'error', '[참가 업체 정보]', '주소를 입력해 주세요.', '');
        return false;
    }
    let address_detail = $('#companyAddressDetail').val();
    if(address_detail === ''){
        showMessage('', 'error', '[참가 업체 정보]', '상세주소를 입력해 주세요.', '');
        return false;
    }

    // 공장 주소
    let factory_address = $('#factoryAddress').val();
    let factory_address_detail = $('#factoryAddressDetail').val();

    // 대표자
    let company_ceo = $('#companyCeo').val();
    if(company_ceo === ''){
        showMessage('', 'error', '[참가 업체 정보]', '대표자명을 입력해 주세요.', '');
        return false;
    }

    // 전화
    let company_tel = $('#companyTel').val();
    if(company_tel === ''){
        showMessage('', 'error', '[참가 업체 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 홈페이지
    let company_homepage = $('#companyHomepage').val();
    if(nvl(company_homepage,'') !== ''){
        if(!checkUrl(company_homepage)){
            showMessage('', 'error', '[참가 업체 정보]', '홈페이지 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }
    }

    // Fax
    let company_fax = $('#companyFax').val();

    // 산업분류
    let industryPart = $('#industryPart').val();
    let industryPartEtc = $('#industryPartEtc').val();
    if(industryPart === ''){
        showMessage('', 'error', '[참가 업체 정보]', '산업 분류 항목을 선택해 주세요.', '');
        return false;
    }else{
        if(industryPart === '기타'){
            if(industryPartEtc === ''){
                showMessage('', 'error', '[참가 업체 정보]', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    // 임직원 수
    let employeeCnt = $('#employeeCnt').val();
    if(employeeCnt === ''){
        showMessage('', 'error', '[참가 업체 정보]', '임직원 수 항목을 입력해 주세요.', '');
        return false;
    }

    // 사업자등록증
    let companyLicenseFile_li = $('.companyLicenseFile_li').length;
    if(companyLicenseFile_li === 0){
        let companyLicense = $('#companyLicense').val();
        if (nvl(companyLicense,'') === '') {
            showMessage('', 'info', '[참가 업체 정보]', '사업자등록증을 첨부해주세요.', '');
            return false;
        }
    }

    // 사업자등록번호
    let company_license_number = $('#companyLicenseNum').val();
    if(company_license_number === ''){
        showMessage('', 'error', '[참가 업체 정보]', '사업자등록번호를 입력해 주세요.', '');
        return false;
    }

    // 기참가연도
    let prePartYear = '';
    let checked = $("input[type='checkbox'][name='prePartYear']:checked");
    for(let i=0; i<checked.length; i++){
        prePartYear += checked.eq(i).val();
        if((i+1) !== checked.length){
            prePartYear += ',';
        }
    }

    // 참가의향
    //let partWantYn = $("input[type='radio'][name='partWantYn']:checked").val();

    // 회원사 여부
    let memberCompanyYn = $("input[type='radio'][name='memberCompanyYn']:checked").val();

    // 블로그
    let sns_blog = $('#snsBlog').val();
    if(nvl(sns_blog,'') !== ''){
        if(!checkUrl(sns_blog)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_blog = sns_blog.toString().toLowerCase();
        }
    }

    // 페이스북
    let sns_facebook = $('#snsFacebook').val();
    if(nvl(sns_facebook,'') !== ''){
        if(!checkUrl(sns_facebook)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_facebook = sns_facebook.toString().toLowerCase();
        }
    }

    // 인스타그램
    let sns_instagram = $('#snsInstagram').val();
    if(nvl(sns_instagram,'') !== ''){
        if(!checkUrl(sns_instagram)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_instagram = sns_instagram.toString().toLowerCase();
        }
    }

    // 기타
    let sns_etc = $('#snsEtc').val();
    if(nvl(sns_etc,'') !== ''){
        if(!checkUrl(sns_etc)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_etc = sns_etc.toString().toLowerCase();
        }
    }

    // 담당자 성명
    let charge_person_name_el = $('input[type=text][name=chargePersonName]');
    let charge_person_name_len = charge_person_name_el.length;
    /*let charge_person_name_flag = true;
    for(let i=0; i<charge_person_name_len; i++){
        if(charge_person_name_el.eq(i).val() === ''){
            charge_person_name_flag = false;
        }
    }
    if(!charge_person_name_flag){
        showMessage('', 'error', '[담당자 정보]', '성명을 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 직위
    let charge_person_position_el = $('input[type=text][name=chargePersonPosition]');
    /*let charge_person_position_len = charge_person_position_el.length;
    let charge_person_position_flag = true;
    for(let i=0; i<charge_person_position_len; i++){
        if(charge_person_position_el.eq(i).val() === ''){
            charge_person_position_flag = false;
        }
    }
    if(!charge_person_position_flag){
        showMessage('', 'error', '[담당자 정보]', '직위를 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 부서
    let charge_person_depart_el = $('input[type=text][name=chargePersonDepart]');

    // 담당자 전화번호
    let charge_person_tel_el = $('input[type=tel][name=chargePersonTel]');
    /*let charge_person_tel_len = charge_person_tel_el.length;
    let charge_person_tel_flag = true;
    for(let i=0; i<charge_person_tel_len; i++){
        if(charge_person_tel_el.eq(i).val() === ''){
            charge_person_tel_flag = false;
        }
    }
    if(!charge_person_tel_flag){
        showMessage('', 'error', '[담당자 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 휴대전화
    let charge_person_phone_el = $('input[type=tel][name=chargePersonPhone]');
    /*let charge_person_phone_len = charge_person_phone_el.length;
    let charge_person_phone_flag = true;
    for(let i=0; i<charge_person_phone_len; i++){
        if(charge_person_phone_el.eq(i).val() === ''){
            charge_person_phone_flag = false;
        }

        if ( charge_person_phone_flag && !/^010-[0-9]{4}-[0-9]{4}$/.test( charge_person_phone_el.eq(i).val() ) ) {
            charge_person_phone_flag = false;
        }
    }
    if(!charge_person_phone_flag){
        showMessage('', 'error', '[담당자 정보]', '휴대전화를 입력해 주세요.<br>또는 올바른 휴대전화번호 형식으로 입력해주세요.(010 만 가능)', '');
        return false;
    }*/

    // 담당자 이메일
    let charge_person_email_el = $('input[type=email][name=chargePersonEmail]');
    /*let charge_person_email_len = charge_person_email_el.length;
    let charge_person_email_flag = true;
    for(let i=0; i<charge_person_email_len; i++){
        if(charge_person_email_el.eq(i).val() === ''){
            charge_person_email_flag = false;
        }
    }
    if(!charge_person_email_flag){
        showMessage('', 'error', '[담당자 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 이메일 도메인
    let charge_person_domain_el = $('input[type=email][name=chargePersonDomain]');
    /*let charge_person_domain_len = charge_person_domain_el.length;
    let charge_person_domain_flag = true;
    for(let i=0; i<charge_person_domain_len; i++){
        if(charge_person_domain_el.eq(i).val() === ''){
            charge_person_domain_flag = false;
        }
    }
    if(!charge_person_domain_flag){
        showMessage('', 'error', '[담당자 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 정보 Json Create
    let chargePersonList_json_arr = [];
    if(charge_person_name_len > 0){
        for(let i=0; i<charge_person_name_len; i++){
            let chargePersonList_json_obj = {
                seq: $('input[type=hidden][name=chargeSeq]').eq(i).val(),
                id: exhibitorSeq,
                userId: id,
                transferYear: transferYear,
                chargePersonName: charge_person_name_el.eq(i).val(),
                chargePersonPosition: charge_person_position_el.eq(i).val(),
                chargePersonDepart: charge_person_depart_el.eq(i).val(),
                chargePersonTel: charge_person_tel_el.eq(i).val(),
                chargePersonPhone: charge_person_phone_el.eq(i).val(),
                chargePersonEmail: charge_person_email_el.eq(i).val() + '@' + charge_person_domain_el.eq(i).val(),
            };
            chargePersonList_json_arr.push(chargePersonList_json_obj);
        }
    }

    // 참가분야
    let field_part_el = $('input[type=checkbox][name=fieldPart]:checked');
    let field_part_len = field_part_el.length;
    let field_part = '';
    if(field_part_len !== 0){
        for(let i=0; i<field_part_len; i++){
            field_part += field_part_el.eq(i).val();
            if((i+1) !== field_part_len){
                field_part += ',';
            }
        }
    }else{
        showMessage('', 'error', '[참가분야]', '참가분야를 하나 이상 선택해 주세요.', '');
        return false;
    }
    //console.log(field_part);

    // 전시품목
    let display_item_el = $('input[type=text][name=displayItem]');
    let display_item_len = display_item_el.length;
    let display_item_flag = true;
    for(let i=0; i<display_item_len; i++){
        if(display_item_el.eq(i).val() === ''){
            display_item_flag = false;
        }
    }
    if(!display_item_flag){
        showMessage('', 'error', '[전시정보]', '전시품목을 입력해 주세요.', '');
        return false;
    }

    // 전시품목 브랜드명
    let display_brand_el = $('input[type=text][name=displayBrand]');
    let display_brand_len = display_brand_el.length;
    let display_brand_flag = true;
    for(let i=0; i<display_brand_len; i++){
        if(display_brand_el.eq(i).val() === ''){
            display_brand_flag = false;
        }
    }
    if(!display_brand_flag){
        showMessage('', 'error', '[전시정보]', '브랜드명을 입력해 주세요.', '');
        return false;
    }

    // 전시품목 실물 보트 수
    let display_boat_cnt_el = $('input[type=text][name=displayBoatCnt]');
    let display_boat_cnt_len = display_boat_cnt_el.length;
    let display_boat_cnt_flag = true;
    for(let i=0; i<display_boat_cnt_len; i++){
        if(display_boat_cnt_el.eq(i).val() === ''){
            display_boat_cnt_flag = false;
        }
    }
    if(!display_boat_cnt_flag){
        showMessage('', 'error', '[전시정보]', '실물 보트 수를 입력해 주세요.', '');
        return false;
    }

    // 전시품목 전시품소개 국문
    let display_item_intro_ko_el = $('textarea[name=displayItemIntroKo]');
    let display_item_intro_ko_len = display_item_intro_ko_el.length;
    let display_item_intro_ko_flag = true;
    for(let i=0; i<display_item_intro_ko_len; i++){
        if(display_item_intro_ko_el.eq(i).val() === ''){
            display_item_intro_ko_flag = false;
        }
    }
    if(!display_item_intro_ko_flag){
        showMessage('', 'error', '[전시정보]', '전시품소개(국문)를 입력해 주세요.', '');
        return false;
    }

    // 전시품목 전시품소개 영문
    let display_item_intro_en_el = $('textarea[name=displayItemIntroEn]');
    let display_item_intro_en_len = display_item_intro_en_el.length;
    let display_item_intro_en_flag = true;
    for(let i=0; i<display_item_intro_en_len; i++){
        if(display_item_intro_en_el.eq(i).val() === ''){
            display_item_intro_en_flag = false;
        }
    }
    if(!display_item_intro_en_flag){
        showMessage('', 'error', '[전시정보]', '전시품소개(영문)를 입력해 주세요.', '');
        return false;
    }

    //전시정보 Json Create
    let displayList_json_arr = [];
    if(display_item_len > 0){
        for(let i=0; i<display_item_len; i++){
            let displayList_json_obj = {
                seq: $('input[type=hidden][name=displayItemSeq]').eq(i).val(),
                id: exhibitorSeq,
                userId: id,
                transferYear: transferYear,
                displayItem: display_item_el.eq(i).val(),
                displayBrand: display_brand_el.eq(i).val(),
                displayBoatCnt: display_boat_cnt_el.eq(i).val(),
                displayItemIntroKo: display_item_intro_ko_el.eq(i).val(),
                displayItemIntroEn: display_item_intro_en_el.eq(i).val()
            };
            displayList_json_arr.push(displayList_json_obj);
        }
    }

    // 회사소개
    let company_intro_ko = $('#companyIntroKo').val();
    if(company_intro_ko === ''){
        showMessage('', 'error', '[상세정보]', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_intro_en = $('#companyIntroEn').val();
    if(company_intro_en === ''){
        showMessage('', 'error', '[상세정보]', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    // KIBS 참가목적
    let company_purpose_ko = $('#companyPurposeKo').val();
    if(company_purpose_ko === ''){
        showMessage('', 'error', '[상세정보]', 'KIBS 참가목적(국문)을 선택해 주세요.', '');
        return false;
    }
    let company_purpose_en = $('#companyPurposeEn').val();
    if(company_purpose_en === ''){
        showMessage('', 'error', '[상세정보]', 'KIBS 참가목적(영문)을 선택해 주세요.', '');
        return false;
    }

    // 신제품출품 사항 소개
    let new_item_intro_ko = $('#newItemIntroKo').val();
    let new_item_intro_en = $('#newItemIntroEn').val();

    // 우리 기업 부스는 ..
    let booth_visit_reason = $('#boothVisitReason').val();
    if(booth_visit_reason === ''){
        showMessage('', 'error', '[상세정보]', '우리 기업 부스는 꼭..? 항목을 입력해 주세요.', '');
        return false;
    }

    // 행사/이벤트 진행계획
    let event_plan = $('#eventPlan').val();
    if(event_plan === ''){
        showMessage('', 'error', '[상세정보]', '행사/이벤트 진행계획을 입력해 주세요.', '');
        return false;
    }

    // 사무국 요청사항
    /*let office_request = $('#officeRequest').val();
    if(office_request === ''){
        showMessage('', 'error', '[상세정보]', '사무국 요청사항을 입력해 주세요.', '');
        return false;
    }*/

    // 로고
    let logoFile_li = $('.logoFile_li').length;
    if(logoFile_li === 0){
        let logo = $('#logo').val();
        if (nvl(logo,"") === "") {
            showMessage('', 'error', '[상세정보]', '로고 파일을 업로드해주세요.', '');
            return false;
        }
    }

    // 홍보 이미지
    let promotionImageFile_li = $('.promotionImageFile_li').length;
    let promotionImageListVal = $('input[type=text][name=promotionImage]').eq(0).val();
    if( (promotionImageFile_li === 0) && nvl(promotionImageListVal,"") === ""){
        showMessage('', 'info', '[홍보 이미지]', '홍보 이미지 파일을 업로드해주세요.', '');
        return false;
    }

    // 회사소개영상
    let company_intro_video = $('#companyIntroVideo').val();
    if(nvl(company_intro_video,'') !== ''){
        if(!checkUrl(company_intro_video)){
            showMessage('', 'error', '[온라인 전시관 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }
    }

    // 제품명(국문)
    let product_name_ko_el = $('input[type=text][name=productNameKo]');
    let product_name_ko_len = product_name_ko_el.length;
    let product_name_ko_flag = true;
    for(let i=0; i<product_name_ko_len; i++){
        if(product_name_ko_el.eq(i).val() === ''){
            product_name_ko_flag = false;
        }
    }
    if(!product_name_ko_flag){
        showMessage('', 'error', '[온라인 전시관 정보]', '제품명(국문)을 입력해주세요.', '');
        return false;
    }

    // 제품명(영문)
    let product_name_en_el = $('input[type=text][name=productNameEn]');
    let product_name_en_len = product_name_en_el.length;
    let product_name_en_flag = true;
    for(let i=0; i<product_name_en_len; i++){
        if(product_name_en_el.eq(i).val() === ''){
            product_name_en_flag = false;
        }
    }
    if(!product_name_en_flag){
        showMessage('', 'error', '[온라인 전시관 정보]', '제품명(영문)을 입력해주세요.', '');
        return false;
    }

    // 제품분류(대)
    let product_option_big_el = $('select[name=productOptionBig]');
    let product_option_big_len = product_option_big_el.length;
    let product_option_big_flag = true;
    for(let i=0; i<product_option_big_len; i++){
        if(product_option_big_el.eq(i).val() === '' || product_option_big_el.eq(i).val() === '선택'){
            if($('.preOptionList').length === 0){
                product_option_big_flag = false;
            }
        }
    }
    if(!product_option_big_flag){
        showMessage('', 'error', '[온라인 전시관 정보]', '제품분류(대)를 입력해주세요.', '');
        return false;
    }

    // 제품분류(소)
    let product_option_small_el = $('select[name=productOptionSmall]');
    let product_option_small_len = product_option_small_el.length;
    let product_option_small_flag = true;
    for(let i=0; i<product_option_small_len; i++){
        if(product_option_small_el.eq(i).val() === '' || product_option_small_el.eq(i).val() === '선택'){
            if($('.preOptionList').length === 0) {
                product_option_small_flag = false;
            }
        }
    }
    if(!product_option_small_flag){
        showMessage('', 'error', '[온라인 전시관 정보]', '제품분류(소)를 입력해주세요.', '');
        return false;
    }

    // 제품사진
    let onlineInfoBox = $('.onlineInfoBox');
    for(let i=0; i<onlineInfoBox.length; i++){
        let inputFile = onlineInfoBox.eq(i).find('.upload_name').val();
        let preFileList = onlineInfoBox.eq(i).find('li.productImageFile_li').length;
        if(nvl(inputFile,"") === "" && preFileList === 0){
            showMessage('', 'error', '[온라인 전시관 정보]', '제품 사진을 업로드해주세요.', '');
            return false;
        }
    }

    // 제품설명(국문)
    let product_intro_ko_el = $('textarea[name=productIntroKo]');
    let product_intro_ko_len = product_intro_ko_el.length;
    let product_intro_ko_flag = true;
    for(let i=0; i<product_intro_ko_len; i++){
        if(product_intro_ko_el.eq(i).val() === ''){
            product_intro_ko_flag = false;
        }
    }
    if(!product_intro_ko_flag){
        showMessage('', 'error', '[온라인 전시관 정보]', '제품 설명(국문)을 입력해 주세요.', '');
        return false;
    }

    // 제품설명(영문)
    let product_intro_en_el = $('textarea[name=productIntroEn]');
    let product_intro_en_len = product_intro_en_el.length;
    let product_intro_en_flag = true;
    for(let i=0; i<product_intro_en_len; i++){
        if(product_intro_en_el.eq(i).val() === ''){
            product_intro_en_flag = false;
        }
    }
    if(!product_intro_en_flag){
        showMessage('', 'error', '[온라인 전시관 정보]', '제품 설명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 제품영상
    let product_intro_video_el = $('input[type=text][name=productIntroVideo]');

    // 전장
    let product_width_el = $('input[type=text][name=productWidth]');

    // 마력
    let product_horse_power_el = $('input[type=text][name=productHorsePower]');

    // 온라인전시회 정보 Json Create
    let onlineInfoCnt = $('.onlineInfoNum:last').text();
    let onlineList_json_arr = [];
    if(onlineInfoCnt > 0){
        for(let i=0; i<onlineInfoCnt; i++){
            let productOptionBig = '';
            let productOptionSmall = '';
            let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
            if(nvl(selProductOptionBig,"선택") !== "선택"){
                productOptionBig = $('select[name=productOptionBig]').eq(i).val();
                productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
            }else{
                productOptionBig = $('input[type=text][name=preBigValue]').eq(i).val();
                productOptionSmall = $('input[type=text][name=preSmallValue]').eq(i).val();
            }

            let onlineList_json_obj = {
                seq: $('input[type=hidden][name=onlineItemSeq]').eq(i).val(),
                id: exhibitorSeq,
                userId: id,
                transferYear: transferYear,
                productNameKo: product_name_ko_el.eq(i).val(),
                productNameEn: product_name_en_el.eq(i).val(),
                productOptionBig: productOptionBig,
                productOptionSmall: productOptionSmall,
                productIntroKo: product_intro_ko_el.eq(i).val(),
                productIntroEn: product_intro_en_el.eq(i).val(),
                productIntroVideo: youtubeId(product_intro_video_el.eq(i).val()),
                productWidth: product_width_el.eq(i).val(),
                productHorsePower: product_horse_power_el.eq(i).val(),
                note: (i+1).toString()
            };
            onlineList_json_arr.push(onlineList_json_obj);
        }
    }

    // 수출상담회
    let export_meeting_yn = $('input[type=radio][name=exportMeetingYn]:checked').val();

    if(export_meeting_yn === 'N'){
        buyer_add_json_arr = [];
    }

    let lang = sessionStorage.getItem('lang');
    if(nvl(lang,"") === ""){
        let link =  document.location.href;
        if(link.includes('eng')){
            lang = 'EN';
        }else{
            lang = 'KO';
        }
    }

    let exhibitor_json_obj = {
        seq: exhibitorSeq,
        lang: lang,
        transferYear: transferYear,
        id: id,
        password: password,
        name: name,
        position: position,
        depart: depart,
        email: email_input1 + '@' + email_input2,
        emailMarketingYn: email_marketing_yn,
        tel: tel,
        phone: phone,
        companyNameKo: company_name_ko,
        companyNameEn: company_name_en,
        companyAddress: address,
        companyAddressDetail: address_detail,
        factoryAddress: factory_address,
        factoryAddressDetail: factory_address_detail,
        companyCeo: company_ceo,
        companyTel: company_tel,
        companyHomepage: company_homepage,
        companyFax: company_fax,
        industryPart: industryPart,
        industryPartEtc: industryPartEtc,
        employeeCnt: employeeCnt,
        companyLicenseNum: company_license_number,
        prePartYear: prePartYear,
        //partWantYn: partWantYn,
        memberCompanyYn: memberCompanyYn,
        snsBlog: sns_blog,
        snsFacebook: sns_facebook,
        snsInstagram: sns_instagram,
        snsEtc: sns_etc,
        chargePersonList: chargePersonList_json_arr,
        fieldPart: field_part,
        displayList: displayList_json_arr,
        companyIntroKo: company_intro_ko,
        companyIntroEn: company_intro_en,
        companyPurposeKo: company_purpose_ko,
        companyPurposeEn: company_purpose_en,
        //displayItemIntroKo: display_item_intro_ko,
        //displayItemIntroEn: display_item_intro_en,
        newItemIntroKo: new_item_intro_ko,
        newItemIntroEn: new_item_intro_en,
        boothVisitReason: booth_visit_reason,
        eventPlan: event_plan,
        //officeRequest: office_request,
        companyIntroVideo: youtubeId(company_intro_video),
        onlineExhibitList: onlineList_json_arr,
        exportMeetingYn: export_meeting_yn,
        buyerList: buyer_add_json_arr
    };

    //console.log('data : ' + JSON.stringify(exhibitor_json_obj));
    let resData = "";
    if(nvl(exhibitorSeq,"") !== ""){
        resData = ajaxConnect('/mypage/step/updateExhibitor.do', 'post', exhibitor_json_obj);
    }else{
        resData = ajaxConnect('/apply/step/insertExhibitor.do', 'post', exhibitor_json_obj);
    }
    //console.log(resData);

    /* 등록 성공 시 다음 단계로 이동 */
    //let returnPath = "";
    if(resData.resultCode === "0") {
        let exhibitorSeq = resData.customValue;

        if(nvl(exhibitorSeq,"") !== ""){

            /* step 01 등록 후 buyer popup json arr 초기화 */
            if(export_meeting_yn === 'Y'){
                buyer_add_json_arr = [];
            }

            /* 파일 업로드 */
            f_company_uploadFile_call(exhibitorSeq, exhibitorSeq);

            let timerInterval;
            Swal.fire({
                title: "정보 저장 중",
                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해주세요.<br>첨부된 파일의 용량/크기/갯수에 따라 시간이 조금 더 소요될 수 있습니다.",
                allowOutsideClick: false,
                timer: 13000,
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
                        title: '참가 업체 정보',
                        html: '기본 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
                        icon: 'info',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {

                            sessionStorage.setItem('id', id);

                            let onlineFile_json_obj = {
                                seq: exhibitorSeq,
                                onlineExhibitList: onlineList_json_arr
                            }

                            /* 온라인 제품 사진 번호 재부여 */
                            let res = ajaxConnect('/mypage/step/updateOnlineFileNote.do', 'post', onlineFile_json_obj);

                            if(res.resultCode === "0"){
                                f_page_move('/apply/step2_1.do', exhibitorSeq);
                            }
                        }
                    })
                }
            });

        }else{
            window.location.href = '/apply/step2_1.do';
        }
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

let buyer_add_json_arr = [];
function f_buyer_add(exSeq){

    // 회사명
    let buyer_company_name = document.querySelector('#buyer_company_name').value;
    /*if(buyer_company_name === ''){
        showMessage('', 'error', '[바이어 정보]', '회사명을 입력해 주세요.', '');
        return false;
    }*/

    // 국가
    let buyer_country = document.querySelector('#buyer_country').value;
    /*if(buyer_country === ''){
        showMessage('', 'error', '[바이어 정보]', '국가를 입력해 주세요.', '');
        return false;
    }*/

    // 소재지
    let buyer_location = document.querySelector('#buyer_location').value;
    /*if(buyer_location === ''){
        showMessage('', 'error', '[바이어 정보]', '소재지를 입력해 주세요.', '');
        return false;
    }*/

    // 홈페이지
    let buyer_homepage = document.querySelector('#buyer_homepage').value;
    /*if(buyer_homepage === ''){
        showMessage('', 'error', '[바이어 정보]', '홈페이지 주소를 입력해 주세요.', '');
        return false;
    }*/

    // 부서
    let buyer_depart = document.querySelector('#buyer_depart').value;

    // 직책
    let buyer_position = document.querySelector('#buyer_position').value;

    // 이메일
    let buyer_email_input1 = document.querySelector('#buyer_email_input1').value;
    let buyer_email_input2 = document.querySelector('#buyer_email_input2').value;

    // 전화번호
    let buyer_tel = document.querySelector('#buyer_tel').value;

    // 휴대전화
    let buyer_phone = document.querySelector('#buyer_phone').value;
    if ( nvl(buyer_phone,'') !== '' && !/^010-[0-9]{4}-[0-9]{4}$/.test( buyer_phone ) ) {
        showMessage('', 'error', '[바이어 정보]', '올바른 휴대전화번호를 입력해 주세요.<br>(앞자리 010 만 가능합니다.)', '');
        return false;
    }

    // 팩스
    let buyer_fax = document.querySelector('#buyer_fax').value;

    // 취급품목
    let buyer_item = document.querySelector('#buyer_item').value;
    /*if(buyer_item === ''){
        showMessage('', 'error', '[바이어 정보]', '취급품목을 입력해 주세요.', '');
        return false;
    }*/

    // 초청사유
    let buyer_invite_reason = document.querySelector('#buyer_invite_reason').value;
    /*if(buyer_invite_reason === ''){
        showMessage('', 'error', '[바이어 정보]', '초청사유를 입력해 주세요.', '');
        return false;
    }*/

    // 계약진행 여부
    let buyer_progress_yn = document.querySelector('#buyer_progress_yn').value;

    // 희망사항
    let buyer_hope = document.querySelector('#buyer_hope').value;
    /*if(buyer_hope === ''){
        showMessage('', 'error', '[바이어 정보]', '희망사항을 입력해 주세요.', '');
        return false;
    }*/

    /* json Array 에 push */

    let buyer_add_json_obj = {
        seq: '',
        id: exSeq,
        userId: sessionStorage.getItem('id'),
        transferYear: transferYear, // 2025
        buyerCompanyName: buyer_company_name,
        buyerCompanyCountry: buyer_country,
        buyerCompanyLocation: buyer_location,
        buyerCompanyHomepage: buyer_homepage,
        buyerCompanyDepart: buyer_depart,
        buyerCompanyPosition: buyer_position,
        buyerCompanyEmail: buyer_email_input1 + '@' + buyer_email_input2,
        buyerCompanyTel: buyer_tel,
        buyerCompanyPhone: buyer_phone,
        buyerCompanyFax: buyer_fax,
        buyerCompanyItem: buyer_item,
        buyerCompanyInviteReason: buyer_invite_reason,
        buyerCompanyProgressYn: buyer_progress_yn,
        buyerCompanyHope: buyer_hope
    };

    buyer_add_json_arr.push(buyer_add_json_obj);

    /* body 에 팝업 데이터를 포함한 Element 생성 */
    let buyer_add_btn = document.querySelector('#buyer_add_btn');

    let form_chuga_list = document.createElement('div');
    form_chuga_list.className = 'form_chuga_list';

    let cont = document.createElement('div');
    cont.className = 'cont';
    let name = document.createElement('div');
    name.className = 'name';
    name.innerText = buyer_company_name;
    let gubun = document.createElement('div');
    gubun.className = 'gubun';
    gubun.innerText = buyer_country + ' / ' + buyer_company_name + ' / ' + buyer_depart + ' ' + buyer_position;

    /* buyer value input hidden 생성 */
    //회사명
    let buyerItemSeq_el = document.createElement('input');
    buyerItemSeq_el.type = 'hidden';
    buyerItemSeq_el.name = 'buyerItemSeq';
    buyerItemSeq_el.value = '';

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

    let modifyFormBuyer = document.createElement('div');
    modifyFormBuyer.classList.add('modifyFormBuyer');
    modifyFormBuyer.classList.add('modifyFormList');
    modifyFormBuyer.onclick = function(){ f_buyer_modify_modal('create', this); }
    modifyFormBuyer.innerText = '수정';
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

    form_chuga_list.appendChild(buyerItemSeq_el);
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
    $('.popForm').toggleClass('on');
    /* body 스크롤 켜기 */
    $('body').toggleClass('lock_scroll');

    f_buyer_init(); // input 초기화
}

function f_buyer_init(){
    // 회사명
    document.querySelector('#buyer_company_name').value = null;
    // 국가
    document.querySelector('#buyer_country').value = null;
    // 소재지
    document.querySelector('#buyer_location').value = null;
    // 홈페이지
    document.querySelector('#buyer_homepage').value = null;
    // 부서
    document.querySelector('#buyer_depart').value = null;
    // 직책
    document.querySelector('#buyer_position').value = null;
    // 이메일
    document.querySelector('#buyer_email_input1').value = null;
    document.querySelector('#buyer_email_input2').value = null;
    if(document.querySelector('#buyer_email_input2').disabled){
        document.querySelector('#buyer_email_input2').disabled = false;
    }
    document.querySelector('#buyer_email_select').selectedIndex = 0;
    // 전화번호
    document.querySelector('#buyer_tel').value = null;
    // 휴대전화
    document.querySelector('#buyer_phone').value = null;
    // 팩스
    document.querySelector('#buyer_fax').value = null;
    // 취급품목
    document.querySelector('#buyer_item').value = null;
    // 초청사유
    document.querySelector('#buyer_invite_reason').value = null;
    // 계약진행여부
    document.querySelector('#buyer_progress_yn').selectedIndex = 1;
    // 희망사항
    document.querySelector('#buyer_hope').value = null;
}

function f_buyer_modify_modal(gbn, value){
    //console.log(gbn, value);
    if(gbn === 'select'){
        let jsonObj = {
            "seq": value
        };
        let resData = ajaxConnect('/mypage/step/selectBuyerSingle.do','post',jsonObj);

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

    $('.popFormBuyer').toggleClass('on');
    $('body').toggleClass('lock_scroll');
    document.querySelector('.pop_box').scrollTop = 0;
}

function f_buyer_modal_set(jsonObj){
    $('#buyer_popup #buyer_company_name').val(jsonObj.buyerCompanyName);
    $('#buyer_popup #buyer_country').val(jsonObj.buyerCompanyCountry);
    $('#buyer_popup #buyer_location').val(jsonObj.buyerCompanyLocation);
    $('#buyer_popup #buyer_homepage').val(jsonObj.buyerCompanyHomepage);
    $('#buyer_popup #buyer_depart').val(jsonObj.buyerCompanyDepart);
    $('#buyer_popup #buyer_position').val(jsonObj.buyerCompanyPosition);
    $('#buyer_popup #buyer_email_input1').val(jsonObj.buyerCompanyEmail.split('@')[0]);
    $('#buyer_popup #buyer_email_input2').val(jsonObj.buyerCompanyEmail.split('@')[1]);

    let optionExists = false;
    $('#buyer_popup #buyer_email_select option').each(
        function(){
            if (this.value === jsonObj.buyerCompanyEmail.split('@')[1]) {
                optionExists = true;
                return false;
            }
        }
    );

    if(optionExists){
        $('#buyer_popup #buyer_email_select').val(jsonObj.buyerCompanyEmail.split('@')[1]).prop("selected",true);
    }else{
        $('#buyer_popup #buyer_email_select option').eq(0).prop('selected',true);
    }

    $('#buyer_popup #buyer_tel').val(jsonObj.buyerCompanyTel);
    $('#buyer_popup #buyer_phone').val(jsonObj.buyerCompanyPhone);
    $('#buyer_popup #buyer_fax').val(jsonObj.buyerCompanyFax);
    $('#buyer_popup #buyer_item').val(jsonObj.buyerCompanyItem);
    $('#buyer_popup #buyer_invite_reason').val(jsonObj.buyerCompanyInviteReason);
    $('#buyer_popup #buyer_progress_yn').val(jsonObj.buyerCompanyProgressYn).prop("selected",true);
    $('#buyer_popup #buyer_hope').val(jsonObj.buyerCompanyHope);
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
                let resData = ajaxConnect('/mypage/step/deleteBuyer.do','post',jsonObj);
                if(resData.resultCode === "0"){
                    $(el).closest('.form_chuga_list').remove();
                }
            }else{
                $(el).closest('.form_chuga_list').remove();
            }
        }//isConfirmed
    }); //swal

}

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

/* //////////////////////////////// end:step_01 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_1 ////////////////////////////////// */
function step_2_1_check(exhibitorSeq){

    let boothType = '등록비';
    let boothPrcSum = 0;

    // 부스 신청 - 등록비 - 수량
    let registration_cnt = parseInt(document.querySelector('#registration_cnt').value);

    // 부스 신청 - 등록비 - 금액
    let registration_fee = document.querySelector('#registration_fee').value;

    boothPrcSum += wonToNumber(registration_fee);

    // 부스 신청 - 독립부스 - 수량
    let stand_alone_booth_cnt = parseInt(document.querySelector('#stand_alone_booth_cnt').value);

    if(stand_alone_booth_cnt > 0){
        boothType += ',독립부스';
    }

    // 부스 신청 - 독립부스 - 금액
    let stand_alone_booth_fee = document.querySelector('#stand_alone_booth_fee').value;

    boothPrcSum += wonToNumber(stand_alone_booth_fee);

    // 부스 신청 - 조립부스 - 수량
    let assembly_booth_cnt = parseInt(document.querySelector('#assembly_booth_cnt').value);

    if(assembly_booth_cnt > 0){
        boothType += ',조립부스';
    }

    // 부스 신청 - 조립부스 - 금액
    let assembly_booth_fee = document.querySelector('#assembly_booth_fee').value;

    boothPrcSum += wonToNumber(assembly_booth_fee);

    // 부스 신청 - 온라인부스 - 수량
    let online_booth_cnt = parseInt(document.querySelector('select#online_booth_cnt option:checked').text);

    if(online_booth_cnt > 0){
        boothType += ',온라인부스';
    }

    // 부스 신청 - 온라인부스 - 금액
    let online_booth_fee = document.querySelector('#online_booth_fee').value;

    boothPrcSum += wonToNumber(online_booth_fee);

    let discountType = '';
    let discountPrcSum = 0;
    let boothTotalCnt = stand_alone_booth_cnt + assembly_booth_cnt + online_booth_cnt;

    // 할인적용 - 1차 조기신청
    let discount_early_1 = document.querySelector('input[type=checkbox][id=discount1]').checked;
    if(discount_early_1){
        discountType += ',1차조기신청';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 2차 조기신청
    let discount_early_2 = document.querySelector('input[type=checkbox][id=discount2]').checked;
    if(discount_early_2){
        discountType += ',2차조기신청';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 재참가할인 All
    let discount_re_all = document.querySelector('input[type=checkbox][id=discount3]').checked;
    if(discount_re_all){
        discountType += ',재참가할인';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 규모할인 1 (20부스 이상)
    let discount_scale_2 = document.querySelector('input[type=checkbox][id=discount5]').checked;
    if(discount_scale_2){
        discountType += ',규모할인1';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 규모할인 2 (40부스 이상)
    let discount_scale_3 = document.querySelector('input[type=checkbox][id=discount6]').checked;
    if(discount_scale_3){
        discountType += ',규모할인2';
        discountPrcSum += (boothTotalCnt * 450000);
    }

    // 할인적용 - 첫 참가 할인
    let discount_first = document.querySelector('input[type=checkbox][id=discount7]').checked;
    if(discount_first){
        discountType += ',첫참가';
        discountPrcSum += (boothTotalCnt * 500000);
    }

    // 할인적용 - 한국해양레저산업협회 할인
    let discount_leisure = document.querySelector('input[type=checkbox][id=discount8]').checked;
    if(discount_leisure){
        discountType += ',한국해양레저산업협회';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    if(discountType.substring(0,1) === ','){
        discountType = discountType.substring(1);
    }

    let discountYn = 'N';
    if(nvl(discountType,'') !== ''){
        discountYn = 'Y';
    }

    //let boothPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    if(stand_alone_booth_cnt + assembly_booth_cnt + online_booth_cnt === 0){
        showMessage('', 'error', '[전시부스 신청정보]', '부스(독립,조립,온라인)를 하나 이상 신청해 주세요.', '');
        return false;
    }

    let booth_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear, //2025
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registration_cnt,
        registrationFee: Number.parseInt(wonToInt(registration_fee)),
        standAloneBoothCnt: stand_alone_booth_cnt,
        standAloneBoothFee: Number.parseInt(wonToInt(stand_alone_booth_fee)),
        assemblyBoothCnt: assembly_booth_cnt,
        assemblyBoothFee: Number.parseInt(wonToInt(assembly_booth_fee)),
        onlineBoothCnt: online_booth_cnt,
        onlineBoothFee: Number.parseInt(wonToInt(online_booth_fee)),
        discountEarly1: discount_early_1,
        discountEarly2: discount_early_2,
        discountReAll: discount_re_all,
        discountScale2: discount_scale_2,
        discountScale3: discount_scale_3,
        discountFirst: discount_first,
        discountLeisure: discount_leisure,
        discountYn: discountYn,
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum
    }

    let resData = ajaxConnect('/apply/step/updateExhibitBooth.do', 'post', booth_json_obj);

    let returnPath = "";
    //console.log(resData);
    if(resData.resultCode === "0") {

        Swal.fire({
            title: '참가 업체 정보',
            html: '부스 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step2_2.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '전시부스 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function wonToInt(won){
    return won.replace(/\s/g, '').replace(/\￦/g, '').replace(/\,/g, '');
}

/* //////////////////////////////// end:step_2_1 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_2 ////////////////////////////////// */
function step_2_2_check(exhibitorSeq){

    // 상호간판 신청 - 상호간판명 (국문)
    let company_sign_name_ko = $('#company_sign_name_ko').val();
    // 상호간판 신청 - 상호간판명 (영문)
    let company_sign_name_en = $('#company_sign_name_en').val();

    //let standAloneBoothCnt = parseInt(nvl($('#standAloneBoothCnt').val(),'0'));
    let assemblyBoothCnt = parseInt(nvl($('#assemblyBoothCnt').val(),'0'));

    if(nvl(company_sign_name_ko,'') === '' || nvl(company_sign_name_en,'') === ''){
        if(assemblyBoothCnt > 0) {
            showMessage('', 'error', '[상호간판 신청정보]', '조립부스를 신청하신 참가업체는<br>반드시 상호간판을 신청해 주셔야 합니다.', '');
            return false;
        }
    }else{
        if(assemblyBoothCnt === 0) {
            showMessage('', 'error', '[상호간판 신청정보]', '상호간판은 조립부스를 신청한 참가기업만 작성 가능합니다.', '');
            return false;
        }
    }

    let sign_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        companySignNameKo: company_sign_name_ko,
        companySignNameEn: company_sign_name_en
    }

    let resData = ajaxConnect('/apply/step/updateCompanySign.do', 'post', sign_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '상호간판 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step2_3.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '상호간판 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

/* //////////////////////////////// end:step_2_2 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_3 ////////////////////////////////// */
function step_2_3_check(exhibitorSeq){

    // 유틸리티 신청정보 - 주간단상 - 수량
    let utility_jugan_cnt = parseInt(document.querySelector('#utility_jugan_cnt').value);

    // 유틸리티 신청정보 - 주간단상 - 금액
    let utility_jugan_fee = document.querySelector('#utility_jugan_fee').value;

    // 유틸리티 신청정보 - 24시간용 - 수량
    let utility_day_cnt = parseInt(document.querySelector('#utility_day_cnt').value);

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_day_fee = document.querySelector('#utility_day_fee').value;

    // 유틸리티 신청정보 - 압축공기 - 수량
    let utility_compressed_air_cnt = parseInt(document.querySelector('#utility_compressed_air_cnt').value);

    // 유틸리티 신청정보 - 압축공기 - 금액
    let utility_compressed_air_fee = document.querySelector('#utility_compressed_air_fee').value;

    // 유틸리티 신청정보 - 급배수 - 수량
    let utility_water_basic_cnt = parseInt(document.querySelector('#utility_water_basic_cnt').value);

    // 유틸리티 신청정보 - 급배수 - 금액
    let utility_water_basic_fee = document.querySelector('#utility_water_basic_fee').value;

    // 유틸리티 신청정보 - 인터넷 - 수량
    let utility_internet_cnt = parseInt(document.querySelector('#utility_internet_cnt').value);

    // 유틸리티 신청정보 - 인터넷 - 금액
    let utility_internet_fee = document.querySelector('#utility_internet_fee').value;

    // 유틸리티 신청정보 - 파이텍스 (신품) - 수량
    let utility_pytex_new_cnt = parseInt(document.querySelector('#utility_pytex_new_cnt').value);

    // 유틸리티 신청정보 - 파이텍스 (신품) - 금액
    let utility_pytex_new_fee = document.querySelector('#utility_pytex_new_fee').value;

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 수량
    let utility_pytex_re_cnt = parseInt(document.querySelector('#utility_pytex_re_cnt').value);

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 금액
    let utility_pytex_re_fee = document.querySelector('#utility_pytex_re_fee').value;

    // 유틸리티 신청정보 - 참관객/바이어 바코드 리더기 - 수량
    let utility_barcode_cnt = parseInt(document.querySelector('#utility_barcode_cnt').value);

    // 유틸리티 신청정보 - 참관객/바이어 바코드 리더기 - 금액
    let utility_barcode_fee = document.querySelector('#utility_barcode_fee').value;

    let utilityPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let utility_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        utilityJuganCnt: utility_jugan_cnt,
        utilityJuganFee: wonToInt(utility_jugan_fee),
        utilityDayCnt: utility_day_cnt,
        utilityDayFee: wonToInt(utility_day_fee),
        utilityCompressedAirCnt: utility_compressed_air_cnt,
        utilityCompressedAirFee: wonToInt(utility_compressed_air_fee),
        utilityWaterBasicCnt: utility_water_basic_cnt,
        utilityWaterBasicFee: wonToInt(utility_water_basic_fee),
        utilityInternetCnt: utility_internet_cnt,
        utilityInternetFee: wonToInt(utility_internet_fee),
        utilityPytexNewCnt: utility_pytex_new_cnt,
        utilityPytexNewFee: wonToInt(utility_pytex_new_fee),
        utilityPytexReCnt: utility_pytex_re_cnt,
        utilityPytexReFee: wonToInt(utility_pytex_re_fee),
        utilityBarcodeCnt: utility_barcode_cnt,
        utilityBarcodeFee: wonToInt(utility_barcode_fee),
        utilityPrcSum: utilityPrcSum
    }

    let resData = ajaxConnect('/apply/step/updateExhibitUtility.do', 'post', utility_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '유틸리티 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step2_4.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '유틸리티 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

/* //////////////////////////////// end:step_2_3 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_4 ////////////////////////////////// */

function step_2_4_check(exhibitorSeq){

    // 성명(국문)
    let pass_name_el = document.querySelectorAll('input[type=text][name=pass_name]');
    let pass_name_len = pass_name_el.length;
    let pass_name_flag = true;
    for(let i=0; i<pass_name_len; i++){
        if(pass_name_el[i].value === ''){
            pass_name_flag = false;
        }
    }
    if(!pass_name_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '성명(국문)을 입력해 주세요.', '');
        return false;
    }

    // 출입증이름(영문)
    let pass_first_name_el = document.querySelectorAll('input[type=text][name=pass_first_name]');
    let pass_first_name_len = pass_first_name_el.length;
    let pass_first_name_flag = true;
    for(let i=0; i<pass_first_name_len; i++){
        if(pass_first_name_el[i].value === ''){
            pass_first_name_flag = false;
        }
    }
    if(!pass_first_name_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '성명(영문) 이름을 입력해 주세요.', '');
        return false;
    }

    // 출입증성(영문)
    let pass_last_name_el = document.querySelectorAll('input[type=text][name=pass_last_name]');
    let pass_last_name_len = pass_last_name_el.length;
    let pass_last_name_flag = true;
    for(let i=0; i<pass_last_name_len; i++){
        if(pass_last_name_el[i].value === ''){
            pass_last_name_flag = false;
        }
    }
    if(!pass_last_name_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '성명(영문) 성을 입력해 주세요.', '');
        return false;
    }

    // 직책(국문)
    let pass_position_ko_el = document.querySelectorAll('input[type=text][name=pass_position_ko]');
    let pass_position_ko_len = pass_position_ko_el.length;
    let pass_position_ko_flag = true;
    for(let i=0; i<pass_position_ko_len; i++){
        if(pass_position_ko_el[i].value === ''){
            pass_position_ko_flag = false;
        }
    }
    if(!pass_position_ko_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '직책(국문)을 입력해 주세요.', '');
        return false;
    }

    // 직책(영문)
    let pass_position_en_el = document.querySelectorAll('input[type=text][name=pass_position_en]');
    let pass_position_en_len = pass_position_en_el.length;
    let pass_position_en_flag = true;
    for(let i=0; i<pass_position_en_len; i++){
        if(pass_position_en_el[i].value === ''){
            pass_position_en_flag = false;
        }
    }
    if(!pass_position_en_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '직책(영문)을 입력해 주세요.', '');
        return false;
    }

    // 출입증비고
    let pass_note_el = document.querySelectorAll('input[type=text][name=pass_note]');

    let pass_json_arr = [];
    if(pass_name_len > 0){
        for(let i=0; i<pass_name_len; i++){
            let pass_json_obj = {
                seq: $('input[type=hidden][name=passSeq]').eq(i).val(),
                id: exhibitorSeq,
                userId: sessionStorage.getItem('id'),
                transferYear: transferYear,
                passName: pass_name_el[i].value,
                passFirstName: pass_first_name_el[i].value,
                passLastName: pass_last_name_el[i].value,
                passPositionKo: pass_position_ko_el[i].value,
                passPositionEn: pass_position_en_el[i].value,
                passNote: pass_note_el[i].value
            };
            pass_json_arr.push(pass_json_obj);
        }
    }

    let passList_json_arr = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        passList: pass_json_arr
    };

    let resData = ajaxConnect('/apply/step/insertExhibitPass.do', 'post', passList_json_arr);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '출입증 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step2_5.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '출입증 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

/* //////////////////////////////// end:step_2_4 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_5 ////////////////////////////////// */

function step_2_5_check(exhibitorSeq){

    let giftList_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        giftList: gift_add_json_arr,
    };

    let resData = ajaxConnect('/apply/step/insertExhibitGift.do', 'post', giftList_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        let exhibitorSeq = resData.customValue;

        gift_add_json_arr = [];

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '경품제공 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step2_8.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '경품제공 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

let gift_add_json_arr = [];
function f_gift_add(){

    // 구분
    let gift_gbn = document.querySelector('#gift_gbn').value;

    // 수량
    let gift_cnt = document.querySelector('#gift_cnt').value;
    if(gift_cnt === ''){
        showMessage('', 'error', '[경품제공 신청정보]', '수량을 입력해 주세요.', '');
        return false;
    }

    // 분류
    let gift_classify = document.querySelector('#gift_classify').value;

    // 품목명
    let gift_name = document.querySelector('#gift_name').value;
    if(gift_name === ''){
        showMessage('', 'error', '[경품제공 신청정보]', '품목명을 입력해 주세요.', '');
        return false;
    }

    // 경품설명
    let gift_content = document.querySelector('#gift_content').value;
    if(gift_content === ''){
        showMessage('', 'error', '[경품제공 신청정보]', '경품설명을 입력해 주세요.', '');
        return false;
    }

    // 경품사진
    let gift_photo = 'giftPhoto'.concat($('div.form_chuga_list').length+1);

    // 회사로고
    let gift_company_logo = 'giftCompanyLogo'.concat($('div.form_chuga_list').length+1);

    // 소비자가
    let gift_price = document.querySelector('#gift_price').value;
    if(gift_price === ''){
        showMessage('', 'error', '[경품제공 신청정보]', '소비자가를 입력해 주세요.', '');
        return false;
    }

    // 협찬가
    let gift_sponsor_price = document.querySelector('#gift_sponsor_price').value;

    // 비고
    let gift_note = document.querySelector('#gift_note').value;

    /* body 에 팝업 데이터를 포함한 Element 생성 */
    let gift_add_btn = document.querySelector('#gift_add_btn');

    let form_chuga_list = document.createElement('div');
    form_chuga_list.className = 'form_chuga_list';

    let cont = document.createElement('div');
    cont.className = 'cont';
    let name = document.createElement('div');
    name.className = 'name';
    name.innerText = gift_name;
    let gubun = document.createElement('div');
    gubun.className = 'gubun';
    gubun.innerText = gift_gbn + ' / ' + gift_cnt + '개';

    /* Gift value input hidden 생성 */
    // 구분
    let giftGbn_el = document.createElement('input');
    giftGbn_el.type = 'hidden';
    giftGbn_el.name = 'giftGbn';
    giftGbn_el.id = 'giftGbn';
    giftGbn_el.value = gift_gbn;

    // 수량
    let giftCnt_el = document.createElement('input');
    giftCnt_el.type = 'hidden';
    giftCnt_el.name = 'giftCnt';
    giftCnt_el.id = 'giftCnt';
    giftCnt_el.value = gift_cnt;

    // 분류
    let giftClassify_el = document.createElement('input');
    giftClassify_el.type = 'hidden';
    giftClassify_el.name = 'giftClassify';
    giftClassify_el.id = 'giftClassify';
    giftClassify_el.value = gift_classify;

    // 품목명
    let giftName_el = document.createElement('input');
    giftName_el.type = 'hidden';
    giftName_el.name = 'giftName';
    giftName_el.id = 'giftName';
    giftName_el.value = gift_name;

    // 경품설명
    let giftContent_el = document.createElement('input');
    giftContent_el.type = 'hidden';
    giftContent_el.name = 'giftContent';
    giftContent_el.id = 'giftContent';
    giftContent_el.value = gift_content;

    // 경품사진
    let giftPhoto_el = document.createElement('input');
    giftPhoto_el.type = 'hidden';
    giftPhoto_el.name = 'giftPhoto';
    giftPhoto_el.id = 'giftPhoto';
    giftPhoto_el.value = gift_photo;

    // 경품로고사진
    let giftCompanyLogo_el = document.createElement('input');
    giftCompanyLogo_el.type = 'hidden';
    giftCompanyLogo_el.name = 'giftCompanyLogo';
    giftCompanyLogo_el.id = 'giftCompanyLogo';
    giftCompanyLogo_el.value = gift_company_logo;

    // 소비자가
    let giftPrice_el = document.createElement('input');
    giftPrice_el.type = 'hidden';
    giftPrice_el.name = 'giftPrice';
    giftPrice_el.id = 'giftPrice';
    giftPrice_el.value = gift_price;

    // 협찬가
    let giftSponsorPrice_el = document.createElement('input');
    giftSponsorPrice_el.type = 'hidden';
    giftSponsorPrice_el.name = 'giftSponsorPrice';
    giftSponsorPrice_el.id = 'giftSponsorPrice';
    giftSponsorPrice_el.value = gift_sponsor_price;

    // 비고
    let giftNote_el = document.createElement('input');
    giftNote_el.type = 'hidden';
    giftNote_el.name = 'giftNote';
    giftNote_el.id = 'giftNote';
    giftNote_el.value = gift_note;

    //수정,삭제버튼
    let modifyFormGift = document.createElement('div');
    modifyFormGift.classList.add('modifyFormGift');
    modifyFormGift.classList.add('modifyFormList');
    modifyFormGift.innerText = '수정';
    modifyFormGift.onclick = function(){ f_gift_modify_modal('create', this); }
    let delFormGift = document.createElement('div');
    delFormGift.classList.add('delFormGift');
    delFormGift.classList.add('delFormList');
    delFormGift.onclick = function(){ f_gift_remove('create', this); }
    delFormGift.innerText = '삭제';

    cont.appendChild(name);
    cont.appendChild(gubun);
    form_chuga_list.appendChild(cont);
    form_chuga_list.appendChild(modifyFormGift);
    form_chuga_list.appendChild(delFormGift);

    form_chuga_list.appendChild(giftGbn_el);
    form_chuga_list.appendChild(giftCnt_el);
    form_chuga_list.appendChild(giftClassify_el);
    form_chuga_list.appendChild(giftName_el);
    form_chuga_list.appendChild(giftContent_el);
    form_chuga_list.appendChild(giftPhoto_el);
    form_chuga_list.appendChild(giftCompanyLogo_el);
    form_chuga_list.appendChild(giftPrice_el);
    form_chuga_list.appendChild(giftSponsorPrice_el);
    form_chuga_list.appendChild(giftNote_el);

    gift_add_btn.before(form_chuga_list);

    let jsonObj = {
        id: sessionStorage.getItem('id'),
        transferYear: transferYear
    };
    let resData = ajaxConnect('/getExhibitorSeq.do', 'post', jsonObj);

    //console.log(resData);
    if(resData.resultCode === "0") {
        let exhibitorSeq = resData.customValue;

        /* 파일 업로드 */
        f_gift_file_upload_call(exhibitorSeq, exhibitorSeq);

        /* json Array 에 push */
        let gift_add_json_obj = {
            id: exhibitorSeq,
            userId: sessionStorage.getItem('id'),
            transferYear: transferYear,
            giftGbn: gift_gbn,
            giftCnt: gift_cnt,
            giftClassify: gift_classify,
            giftName: gift_name,
            giftContent: gift_content,
            giftPhoto: gift_photo,
            giftCompanyLogo: gift_company_logo,
            giftPrice: gift_price,
            giftSponsorPrice: gift_sponsor_price,
            giftNote: gift_note
        };

        gift_add_json_arr.push(gift_add_json_obj);
    }

    /* 팝업닫기 */
    $('.popForm').toggleClass('on');
    $('body').toggleClass('lock_scroll');
    document.querySelector('.pop_box').scrollTop = 0;

    f_gift_init(); // input 초기화
}

function f_gift_modify_modal(gbn, value){
    //console.log(gbn, value);
    if(gbn === 'select'){
        let jsonObj = {
            "seq": value
        };
        let resData = ajaxConnect('/apply/step/selectExhibitGift.do','post',jsonObj);

        f_gift_modal_set(resData);

    }else{
        let row_el = $(value).parent('div');
        let jsonObj = {
            "id": $('input[type=hidden][name=exhibitorSeq]').val(),
            "giftGbn": row_el.find('input[type=hidden][name=giftGbn]').val(),
            "giftCnt": row_el.find('input[type=hidden][name=giftCnt]').val(),
            "giftClassify": row_el.find('input[type=hidden][name=giftClassify]').val(),
            "giftName": row_el.find('input[type=hidden][name=giftName]').val(),
            "giftContent": row_el.find('input[type=hidden][name=giftContent]').val(),
            "giftPhoto": row_el.find('input[type=hidden][name=giftPhoto]').val(),
            "giftCompanyLogo": row_el.find('input[type=hidden][name=giftCompanyLogo]').val(),
            "giftPrice": row_el.find('input[type=hidden][name=giftPrice]').val(),
            "giftSponsorPrice": row_el.find('input[type=hidden][name=giftSponsorPrice]').val(),
            "giftNote": row_el.find('input[type=hidden][name=giftNote]').val()
        }

        f_gift_modal_set(jsonObj);

        $('.popForm').toggleClass('on');
        $('body').toggleClass('lock_scroll');
        document.querySelector('.pop_box').scrollTop = 0;
    }

}

function f_gift_modal_set(jsonObj){
    $('.popFormGift #gift_cnt').val(jsonObj.giftCnt);
    $('.popFormGift #gift_classify').val(jsonObj.giftClassify);
    $('.popFormGift #gift_name').val(jsonObj.giftName);
    $('.popFormGift #gift_content').val(jsonObj.giftContent);
    $('.popFormGift #gift_price').val(jsonObj.giftPrice);
    $('.popFormGift #gift_sponsor_price').val(jsonObj.giftSponsorPrice);
    $('.popFormGift #gift_note').val(jsonObj.giftNote);

    let exhibitorSeq = jsonObj.id;
    if(nvl(exhibitorSeq,"") !== ""){
        let filejsonObj = {
            userId: exhibitorSeq
        };
        let file_resData = ajaxConnect('/file/selectFileList.do','post',filejsonObj);
        if(nvl(file_resData,"") !== ""){
            $.each(file_resData , function(i) {
                let fileFlag = false;
                let file_box_class = '';
                if (file_resData[i].note === jsonObj.giftPhoto){
                    fileFlag = true;
                    file_box_class = 'giftPhotoBox';
                }

                if (file_resData[i].note === jsonObj.giftCompanyLogo) {
                    fileFlag = true;
                    file_box_class = 'giftCompanyLogoBox';
                }

                if (fileFlag) {
                    let giftFullFilePath = file_resData[i].fullFilePath;
                    if (nvl(giftFullFilePath, "") !== "") {
                        giftFullFilePath = giftFullFilePath.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
                    }

                    let giftFolderPath = file_resData[i].folderPath;
                    let giftFullFileName = file_resData[i].fullFileName;
                    let giftFileName = file_resData[i].fileName;

                    let fileBoxStr = '<div class="file_list" style="margin-top: 10px;">';
                    fileBoxStr += '<img src="' + giftFullFilePath + '" class="mr10" style="border: 1px solid #009ef7; margin: 0 5px 0 0; max-width: 150px;"/>';
                    fileBoxStr += '<a href="/file/download.do?path=exhibitor/company/' + giftFolderPath + '&fileName=' + giftFullFileName + '">';
                    fileBoxStr += giftFileName;
                    fileBoxStr += '</a>';
                    fileBoxStr += '<button type="button" onClick="f_file_remove(this,\'' + file_resData[i].id + '\')" style="cursor:pointer; margin-left: 10px;">';
                    fileBoxStr += 'X';
                    fileBoxStr += '</button>';
                    fileBoxStr += '</div>';

                    $('.popFormGift .file_box .' + file_box_class).append(fileBoxStr);
                }
            })
        }

    }
}

function f_gift_remove(gbn, el){
    //console.log(gbn, el);

    Swal.fire({
        title: '선택한 경품제공 정보를 삭제하시겠습니까?',
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
                let resData = ajaxConnect('/apply/step/deleteExhibitGift.do','post',jsonObj);
                if(resData.resultCode === "0"){
                    $(el).closest('.form_chuga_list').remove();
                }
            }else{
                $(el).closest('.form_chuga_list').remove();
            }
        }//isConfirmed
    }); //swal

}

function f_gift_init(){
    // 구분
    document.querySelector('#gift_gbn').checked = true;
    // 수량
    document.querySelector('#gift_cnt').value = null;
    // 분류
    document.querySelector('#gift_classify').value = null;
    // 품목명
    document.querySelector('#gift_name').value = null;
    // 경품설명
    document.querySelector('#gift_content').value = null;
    // 경품사진
    document.querySelector('#gift_photo').value = null;
    // 회사로고
    document.querySelector('#gift_company_logo').value = null;
    document.querySelector('.upload_hidden').value = null;
    // 소비자가
    document.querySelector('#gift_price').value = null;
    // 협찬가
    document.querySelector('#gift_sponsor_price').value = null;
    // 비고
    document.querySelector('#gift_note').value = null;

    // 파일목록제거
    $('div.file_list').remove();
}

/* //////////////////////////////// end:step_2_5 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_6 ////////////////////////////////// */
function step_2_6_check(){

    // 웹배너 내용
    let webbanner_content = document.querySelector('#webbanner_content').value;

    let webbanner_json_obj = {
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        webbannerContent: webbanner_content
    }

    let resData = ajaxConnect('/apply/step/updateWebbanner.do', 'post', webbanner_json_obj);

    let returnPath = "";
    //console.log(resData);
    if(resData.resultCode === "0") {

        let jsonObj = {
            id: sessionStorage.getItem('id'),
            transferYear: transferYear
        };
        let getData = ajaxConnect('/getExhibitorSeq.do', 'post', jsonObj);

        //console.log(getData);
        if(getData.resultCode === "0") {
            let exhibitorSeq = getData.customValue;

            /* 파일 업로드 */
            f_web_file_upload_call(exhibitorSeq, 'exhibitor/company/' + exhibitorSeq);
        }

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '웹배너 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                window.location.href = '/apply/step2_8.do';
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '웹배너 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

/* //////////////////////////////// end:step_2_6 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_8 ////////////////////////////////// */

function f_exhibitor_info_call(){

    let sessionId = sessionStorage.getItem('id');

    if(nvl(sessionId,"") !== ""){

        /* exhibitor table */
        let exhibitor_jsonObj = {
            "id": sessionId,
            "transferYear": transferYear
        };
        let exhibitor_resData = ajaxConnect('/apply/step/directory/selectExhibitorInfo.do','post', exhibitor_jsonObj);

        let exhibitorSeq = exhibitor_resData.seq;

        $('#company_name_ko').val(exhibitor_resData.companyNameKo);
        $('#company_name_en').val(exhibitor_resData.companyNameEn);
        $('#company_address').val(exhibitor_resData.companyAddress);
        $('#company_address_detail').val(exhibitor_resData.companyAddressDetail);
        $('#company_ceo').val(exhibitor_resData.companyCeo);
        $('#company_tel').val(exhibitor_resData.companyTel);
        $('#company_homepage').val(exhibitor_resData.companyHomepage);
        $('#company_fax').val(exhibitor_resData.companyFax);
        $('#company_license_num').val(exhibitor_resData.companyLicenseNum);
        $('#sns_blog').val(exhibitor_resData.snsBlog);
        $('#sns_facebook').val(exhibitor_resData.snsFacebook);
        $('#sns_instagram').val(exhibitor_resData.snsInstagram);
        $('#sns_etc').val(exhibitor_resData.snsEtc);
        $('#company_intro_ko').val(exhibitor_resData.companyIntroKo);
        $('#company_intro_en').val(exhibitor_resData.companyIntroEn);
        $('#company_purpose_ko').val(exhibitor_resData.companyPurposeKo);
        $('#company_purpose_en').val(exhibitor_resData.companyPurposeEn);

        /* charge table */
        let charge_jsonObj = {
            "id": sessionId,
            "transferYear": transferYear
        };
        let charge_resData = ajaxConnect('/apply/step/directory/selectChargeInfo.do','post', charge_jsonObj);

        $.each(charge_resData , function(i){
            if(i===0){
                $('#charge_person_seq').val(charge_resData[i].seq);
                $('#charge_person_name').val(charge_resData[i].chargePersonName);
                $('#charge_person_position').val(charge_resData[i].chargePersonPosition);
                $('#charge_person_depart').val(charge_resData[i].chargePersonDepart);
                $('#charge_person_tel').val(charge_resData[i].chargePersonTel);
                $('#charge_person_phone').val(charge_resData[i].chargePersonPhone);
                let chargePersonEmail = charge_resData[i].chargePersonEmail.split('@');
                let email = chargePersonEmail[0];
                let domain = chargePersonEmail[1];
                $('#charge_person_email_input1').val(email);
                $('#charge_person_email_input2').val(domain);
                $('#charge_person_email_select').val(domain);
            }
        });

        /* display table */
        let display_jsonObj = {
            "id": sessionId,
            "transferYear": transferYear
        };
        let display_resData = ajaxConnectSimple('/apply/step/directory/selectDisplayInfo.do','post', display_jsonObj);
        $.each(display_resData , function(i){
            if(i===0){
                $('#display_seq').val(display_resData[i].seq);
                $('#display_item').val(display_resData[i].displayItem);
                $('#display_brand').val(display_resData[i].displayBrand);
                $('#display_boat_cnt').val(display_resData[i].displayBoatCnt);
            }
        });

        if($('div.file_box').length){
            /* file table */
            let file_jsonObj = {
                "userId": exhibitorSeq,
                "note": 'companyLicense'
            };

            let file_resData = ajaxConnectSimple('/apply/step/directory/selectFileInfo.do','post', file_jsonObj);
            if(nvl(file_resData,'') !== '') {
                let licenseFullFilePath = file_resData.fullFilePath;
                if (nvl(licenseFullFilePath, "") !== "") {
                    licenseFullFilePath = licenseFullFilePath.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
                }

                let licenseFolderPath = file_resData.folderPath;
                let licenseFullFileName = file_resData.fullFileName;
                let licenseFileName = file_resData.fileName;

                $('div.file_box').empty(); //비우기
                let fileBoxStr = '<div class="file_list" style="margin-top: 10px; display: flex; align-items: center;">';
                    fileBoxStr += '<img src="' + licenseFullFilePath + '" class="mr10" style="border: 1px solid #009ef7; margin: 0 5px 0 0; max-width: 150px;"/>';
                    fileBoxStr += '<a href="/file/download.do?path=exhibitor/company/' + licenseFolderPath + '&fileName=' + licenseFullFileName + '">';
                    fileBoxStr += licenseFileName;
                    fileBoxStr += '</a>';
                    /*fileBoxStr += '<button type="button" onClick="f_file_remove(this,\'' + file_resData.id + '\')" style="cursor:pointer; margin-left: 10px;">';
                    fileBoxStr += 'X';
                    fileBoxStr += '</button>';*/
                fileBoxStr += '</div>';

                $('div.file_box').append(fileBoxStr);
            }
        }
    }else{
        Swal.fire({
            title: '디렉토리 정보',
            html: '입력정보 불러오기를 실패하였습니다.<br>직접 입력하시거나 , 로그인 후 마이페이지에서 진행해주세요.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        });
    }
}

function step_2_8_check(exhibitorSeq){

    /*// 회사명
    let company_name_ko = document.querySelector('#company_name_ko').value;
    if(company_name_ko === ''){
        showMessage('', 'error', '[참가 업체 정보]', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_name_en = document.querySelector('#company_name_en').value;
    if(company_name_en === ''){
        showMessage('', 'error', '[참가 업체 정보]', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 주소
    let company_address = document.querySelector('#company_address').value;
    if(company_address === ''){
        showMessage('', 'error', '[참가 업체 정보]', '주소를 입력해 주세요.', '');
        return false;
    }
    let company_address_detail = document.querySelector('#company_address_detail').value;
    if(company_address_detail === ''){
        showMessage('', 'error', '[참가 업체 정보]', '상세주소를 입력해 주세요.', '');
        return false;
    }

    // 대표자
    let company_ceo = document.querySelector('#company_ceo').value;
    if(company_ceo === ''){
        showMessage('', 'error', '[참가 업체 정보]', '대표자명을 입력해 주세요.', '');
        return false;
    }

    // 전화
    let company_tel = document.querySelector('#company_tel').value;
    if(company_tel === ''){
        showMessage('', 'error', '[참가 업체 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 홈페이지
    let company_homepage = document.querySelector('#company_homepage').value;

    // Fax
    let company_fax = document.querySelector('#company_fax').value;

    // 사업자등록증
    if($('.file_box img').length === 0){
        showMessage('', 'error', '[참가 업체 정보]', '기본정보 탭에서 사업자등록증을 등록해주세요.', '');
        return false;
    }

    // 사업자등록번호
    let company_license_num = document.querySelector('#company_license_num').value;

    // 블로그
    let sns_blog = document.querySelector('#sns_blog').value;

    // 페이스북
    let sns_facebook = document.querySelector('#sns_facebook').value;

    // 인스타그램
    let sns_instagram = document.querySelector('#sns_instagram').value;

    // 기타
    let sns_etc = document.querySelector('#sns_etc').value;

    // 담당자 정보 SEQ
    let charge_person_seq_el = document.querySelectorAll('input[type=hidden][name=charge_person_seq]');

    // 성명
    let charge_person_name_el = document.querySelectorAll('input[type=text][name=charge_person_name]');
    let charge_person_name_len = charge_person_name_el.length;
    let charge_person_name_flag = true;
    for(let i=0; i<charge_person_name_len; i++){
        if(charge_person_name_el[i].value === ''){
            charge_person_name_flag = false;
        }
    }
    if(!charge_person_name_flag){
        showMessage('', 'error', '[담당자 정보]', '성명을 입력해 주세요.', '');
        return false;
    }

    // 직위
    let charge_person_position_el = document.querySelectorAll('input[type=text][name=charge_person_position]');
    let charge_person_position_len = charge_person_position_el.length;
    let charge_person_position_flag = true;
    for(let i=0; i<charge_person_position_len; i++){
        if(charge_person_position_el[i].value === ''){
            charge_person_position_flag = false;
        }
    }
    if(!charge_person_position_flag){
        showMessage('', 'error', '[담당자 정보]', '직위를 입력해 주세요.', '');
        return false;
    }

    // 부서
    let charge_person_depart_el = document.querySelectorAll('input[type=text][name=charge_person_depart]');

    // 전화번호
    let charge_person_tel_el = document.querySelectorAll('input[type=tel][name=charge_person_tel]');
    let charge_person_tel_len = charge_person_tel_el.length;
    let charge_person_tel_flag = true;
    for(let i=0; i<charge_person_tel_len; i++){
        if(charge_person_tel_el[i].value === ''){
            charge_person_tel_flag = false;
        }
    }
    if(!charge_person_tel_flag){
        showMessage('', 'error', '[담당자 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 휴대전화
    let charge_person_phone_el = document.querySelectorAll('input[type=tel][name=charge_person_phone]');
    let charge_person_phone_len = charge_person_phone_el.length;
    let charge_person_phone_flag = true;
    for(let i=0; i<charge_person_phone_len; i++){
        if(charge_person_phone_el[i].value === ''){
            charge_person_phone_flag = false;
        }
        if ( charge_person_phone_flag && !/^010-[0-9]{4}-[0-9]{4}$/.test( charge_person_phone_el[i].value ) ) {
            charge_person_phone_flag = false;
        }
    }
    if(!charge_person_phone_flag){
        showMessage('', 'error', '[담당자 정보]', '휴대전화를 입력해 주세요.<br>또는 올바른 휴대전화번호 형식으로 입력해주세요.(010 만 가능)', '');
        return false;
    }

    // 이메일
    let charge_person_email_input1_el = document.querySelectorAll('input[type=email][name=charge_person_email_input1]');
    let charge_person_email_input1_len = charge_person_email_input1_el.length;
    let charge_person_email_input1_flag = true;
    for(let i=0; i<charge_person_email_input1_len; i++){
        if(charge_person_email_input1_el[i].value === ''){
            charge_person_email_input1_flag = false;
        }
    }
    if(!charge_person_email_input1_flag){
        showMessage('', 'error', '[담당자 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }

    let charge_person_email_input2_el = document.querySelectorAll('input[type=email][name=charge_person_email_input2]');
    let charge_person_email_input2_len = charge_person_email_input2_el.length;
    let charge_person_email_input2_flag = true;
    for(let i=0; i<charge_person_email_input2_len; i++){
        if(charge_person_email_input2_el[i].value === ''){
            charge_person_email_input2_flag = false;
        }
    }
    if(!charge_person_email_input2_flag){
        showMessage('', 'error', '[담당자 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    //담당자 Json Create
    let charge_json_arr = [];
    if(charge_person_name_len > 0){
        for(let i=0; i<charge_person_name_len; i++){
            let charge_json_obj = {
                seq: charge_person_seq_el[i].value,
                chargePersonName: charge_person_name_el[i].value,
                chargePersonPosition: charge_person_position_el[i].value,
                chargePersonDepart: charge_person_depart_el[i].value,
                chargePersonTel: charge_person_tel_el[i].value,
                chargePersonPhone: charge_person_phone_el[i].value,
                chargePersonEmail: charge_person_email_input1_el[i].value + '@' + charge_person_email_input2_el[i].value,
            };
            charge_json_arr.push(charge_json_obj);
        }
    }

    // 전시 정보 SEQ
    let display_seq_el = document.querySelectorAll('input[type=hidden][name=display_seq]');

    // 전시품목
    let display_item_el = document.querySelectorAll('input[type=text][name=display_item]');
    let display_item_len = display_item_el.length;
    let display_item_flag = true;
    for(let i=0; i<display_item_len; i++){
        if(display_item_el[i].value === ''){
            display_item_flag = false;
        }
    }
    if(!display_item_flag){
        showMessage('', 'error', '[전시정보]', '전시품목을 입력해 주세요.', '');
        return false;
    }

    // 전시품목 브랜드명
    let display_brand_el = document.querySelectorAll('input[type=text][name=display_brand]');
    let display_brand_len = display_brand_el.length;
    let display_brand_flag = true;
    for(let i=0; i<display_brand_len; i++){
        if(display_brand_el[i].value === ''){
            display_brand_flag = false;
        }
    }
    if(!display_brand_flag){
        showMessage('', 'error', '[전시정보]', '브랜드명을 입력해 주세요.', '');
        return false;
    }

    // 전시품목 실물 보트 수
    let display_boat_cnt_el = document.querySelectorAll('input[type=text][name=display_boat_cnt]');
    let display_boat_cnt_len = display_boat_cnt_el.length;
    let display_boat_cnt_flag = true;
    for(let i=0; i<display_boat_cnt_len; i++){
        if(display_boat_cnt_el[i].value === ''){
            display_boat_cnt_flag = false;
        }
    }
    if(!display_boat_cnt_flag){
        showMessage('', 'error', '[전시정보]', '실물 보트 수를 입력해 주세요.', '');
        return false;
    }

    //전시정보 Json Create
    let displayList_json_arr = [];
    if(display_item_len > 0){
        for(let i=0; i<display_item_len; i++){
            let displayList_json_obj = {
                seq: display_seq_el[i].value,
                displayItem: display_item_el[i].value,
                displayBrand: display_brand_el[i].value,
                displayBoatCnt: display_boat_cnt_el[i].value
            };
            displayList_json_arr.push(displayList_json_obj);
        }
    }

    // 회사소개
    let company_intro_ko = document.querySelector('#company_intro_ko').value;
    if(company_intro_ko === ''){
        showMessage('', 'error', '[상세정보]', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_intro_en = document.querySelector('#company_intro_en').value;
    if(company_intro_en === ''){
        showMessage('', 'error', '[상세정보]', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    // KIBS 참가목적
    let company_purpose_ko = document.querySelector('#company_purpose_ko').value;
    if(company_purpose_ko === ''){
        showMessage('', 'error', '[상세정보]', 'KIBS 참가목적(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_purpose_en = document.querySelector('#company_purpose_en').value;
    if(company_purpose_en === ''){
        showMessage('', 'error', '[상세정보]', 'KIBS 참가목적(영문)을 입력해 주세요.', '');
        return false;
    }

    let lang = 'KO';
    if(document.location.href.includes('eng')){
        lang = 'EN';
    }

    let directory_json_obj = {
        lang: lang,
        userId: sessionStorage.getItem('id'),
        transferYear: transferYear,
        companyNameKo: company_name_ko,
        companyNameEn: company_name_en,
        companyAddress: company_address,
        companyAddressDetail: company_address_detail,
        companyCeo: company_ceo,
        companyTel: company_tel,
        companyHomepage: company_homepage,
        companyFax: company_fax,
        companyLicenseNum: company_license_num,
        snsBlog: sns_blog,
        snsFacebook: sns_facebook,
        snsInstagram: sns_instagram,
        snsEtc: sns_etc,
        chargeList: charge_json_arr,
        displayList: displayList_json_arr,
        companyIntroKo: company_intro_ko,
        companyIntroEn: company_intro_en,
        companyPurposeKo: company_purpose_ko,
        companyPurposeEn: company_purpose_en
    };

    let resData = ajaxConnect('/apply/step/insertDirectory.do', 'post', directory_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {
*/

    let directory_json_obj = {
        userId: sessionStorage.getItem('id'),
        transferYear: transferYear
    };

    let resData = ajaxConnect('/apply/step/insertDirectory.do', 'post', directory_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {


        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '디렉토리 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step03.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '디렉토리 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

/* //////////////////////////////// end:step_2_8 ////////////////////////////////// */

/* //////////////////////////////// begin:step_03 ////////////////////////////////// */
function step_03_check(exhibitorSeq){

    /*home();*/

    let apply_comp_json_obj = {
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        approvalStatus: '승인요청',
        applyComplt: 'Y'
    }

    let resData = ajaxConnect('/apply/step/updateExhibitStatus.do', 'post', apply_comp_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '[참가 업체 정보]',
            html: '참가 신청이 정상 완료되었습니다.<br>등록하신 담당자 메일로 신청완료 및 안내 메일이 발송됩니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {

                let charge_jsonObj = {
                    id: sessionStorage.getItem('id'),
                    transferYear: transferYear
                }
                let charge_resData = ajaxConnect('/apply/step/directory/selectChargeInfo.do','post', charge_jsonObj);

                let jsonObj = makeJsonFormat(charge_resData);
                //console.log(JSON.stringify(jsonObj));
                let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                //console.log(i , resData);
                if (resData.resultCode === "0") {
                    /* 등록 성공 시 다음 단계로 이동 */
                    home();
                }
            }
        })
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '참가 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

/* //////////////////////////////// end:step_03 ////////////////////////////////// */

/* 참가업체 마이페이지 ValidCheck Fn */
function f_mypage_comp(step, seq){
    //console.log(step);
    switch (step){
        case "01":
            my_step_01_check(seq);
            break;
        case "2_1":
            my_step_2_1_check(seq);
            break;
        case "2_2":
            my_step_2_2_check(seq);
            break;
        case "2_3":
            my_step_2_3_check(seq);
            break;
        case "2_4":
            my_step_2_4_check(seq);
            break;
        case "2_5":
            my_step_2_5_check(seq);
            break;
        /*case "2_6":
            step_2_6_check();
            break;*/
        case "2_8":
            my_step_2_8_check(seq);
            break;
        case "03":
            my_step_03_check(seq);
            break;
        default:
            break;
    }
}

function f_personal_info_save(exhibitorSeq){

    let sessionId = sessionStorage.getItem('id');

    // 비밀번호
    let password = document.querySelector('#password').value;
    let passwordCheck = document.querySelector('#passwordCheck').value;
    if(password === ''){
        showMessage('', 'error', '[회원 계정 정보]', '비밀번호를 입력해 주세요.', '');
        return false;
    }

    // 비밀번호 유효성
    let pwCheck = document.querySelector('#pwCheck').value;
    if(pwCheck === 'false'){
        showMessage('', 'error', '[회원 계정 정보]', '비밀번호 유효성 검사를 수행해 주세요.', '');
        return false;
    }

    // 비밀번호 확인 유효성
    let pwConfirmCheck = document.querySelector('#pwConfirmCheck').value;
    if(pwConfirmCheck === 'false'){
        showMessage('', 'error', '[회원 계정 정보]', '비밀번호 확인을 입력해 주세요.', '');
        return false;
    }

    // 성명
    let name = document.querySelector('#name').value;
    if(name === ''){
        showMessage('', 'error', '[회원 계정 정보]', '성명을 입력해 주세요.', '');
        return false;
    }

    // 직위
    let position = document.querySelector('#position').value;
    if(position === ''){
        showMessage('', 'error', '[회원 계정 정보]', '직위를 입력해 주세요.', '');
        return false;
    }

    // 부서
    let depart = document.querySelector('#depart').value;

    // 이메일
    let email_input1 = document.querySelector('#email_input1').value;
    let email_input2 = document.querySelector('#email_input2').value;
    if(email_input1 === ''){
        showMessage('', 'error', '[회원 계정 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }
    if(email_input2 === ''){
        showMessage('', 'error', '[회원 계정 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    // 이메일 마케팅 수신 동의 여부
    let email_marketing_yn_el = document.querySelector('input[type=radio][name=email_marketing_yn]:checked');
    let email_marketing_yn;
    if(email_marketing_yn_el === null){
        showMessage('', 'error', '[회원 계정 정보]', 'E-mail 마케팅정보 수신동의여부를 체크해주세요.', '');
        return false;
    }else{
        email_marketing_yn = email_marketing_yn_el.value;
    }
    //console.log(email_marketing_yn);

    // 전화번호
    let tel = document.querySelector('#tel').value;
    if(tel === ''){
        showMessage('', 'error', '[회원 계정 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 휴대전화
    let phone = document.querySelector('#phone').value;
    if(phone === ''){
        showMessage('', 'error', '[회원 계정 정보]', '휴대전화번호를 입력해 주세요.', '');
        return false;
    }

    let jsonObj = {
        seq: exhibitorSeq,
        password: password,
        name: name,
        position: position,
        depart: depart,
        email: email_input1 + '@' + email_input2,
        emailMarketingYn: email_marketing_yn,
        tel: tel,
        phone: phone
    };

    let resData = ajaxConnect('/mypage/step/exhibitor/updatePersonalInfo.do','post',jsonObj);

    if(resData.resultCode === "0") {

            Swal.fire({
                title: '회원 계정 정보',
                html: '회원 계정 정보가 저장되었습니다.',
                icon: 'info',
                allowOutsideClick: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: '확인'
            }).then((result) => {
                if (result.isConfirmed) {
                    f_page_move('/mypage/modify.do', exhibitorSeq);
                }
            });
            
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }

}

function my_step_01_check(seq){

    let id = sessionStorage.getItem('id');

    // 회사명
    let company_name_ko = document.querySelector('#companyNameKo').value;
    if(company_name_ko === ''){
        showMessage('', 'error', '[참가 업체 정보]', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_name_en = document.querySelector('#companyNameEn').value;
    if(company_name_en === ''){
        showMessage('', 'error', '[참가 업체 정보]', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 주소
    let address = document.querySelector('#companyAddress').value;
    if(address === ''){
        showMessage('', 'error', '[참가 업체 정보]', '주소를 입력해 주세요.', '');
        return false;
    }
    let address_detail = document.querySelector('#companyAddressDetail').value;
    if(address_detail === ''){
        showMessage('', 'error', '[참가 업체 정보]', '상세주소를 입력해 주세요.', '');
        return false;
    }

    // 공장 주소
    let factory_address = $('#factoryAddress').val();
    let factory_address_detail = $('#factoryAddressDetail').val();

    // 대표자
    let company_ceo = document.querySelector('#companyCeo').value;
    if(company_ceo === ''){
        showMessage('', 'error', '[참가 업체 정보]', '대표자명을 입력해 주세요.', '');
        return false;
    }

    // 전화
    let company_tel = document.querySelector('#companyTel').value;
    if(company_tel === ''){
        showMessage('', 'error', '[참가 업체 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 홈페이지
    let company_homepage = document.querySelector('#companyHomepage').value;
    if(nvl(company_homepage,'') !== ''){
        if(!checkUrl(company_homepage)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            company_homepage = company_homepage.toString().toLowerCase();
        }
    }

    // Fax
    let company_fax = document.querySelector('#companyFax').value;

    // 산업분류
    let industryPart = $('#industryPart').val();
    let industryPartEtc = $('#industryPartEtc').val();
    if(industryPart === ''){
        showMessage('', 'error', '[참가 업체 정보]', '산업 분류 항목을 선택해 주세요.', '');
        return false;
    }else{
        if(industryPart === '기타'){
            if(industryPartEtc === ''){
                showMessage('', 'error', '[참가 업체 정보]', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    // 임직원 수
    let employeeCnt = $('#employeeCnt').val();
    if(employeeCnt === ''){
        showMessage('', 'error', '[참가 업체 정보]', '임직원 수 항목을 입력해 주세요.', '');
        return false;
    }

    // 사업자등록증
    let companyLicenseFile_li = $('.companyLicenseFile_li').length;
    if(companyLicenseFile_li === 0){
        let companyLicense = $('#companyLicense').val();
        if (nvl(companyLicense,'') === '') {
            showMessage('', 'info', '입력 정보 확인', '사업자등록증을 첨부해주세요.', '');
            return false;
        }
    }

    // 사업자등록번호
    let company_license_number = document.querySelector('#companyLicenseNum').value;
    if(company_license_number === ''){
        showMessage('', 'error', '[참가 업체 정보]', '사업자등록번호를 입력해 주세요.', '');
        return false;
    }

    // 기참가연도
    let prePartYear = '';
    let checked = document.querySelectorAll("input[type='checkbox'][name='prePartYear']:checked");
    for(let i=0; i<checked.length; i++){
        prePartYear += checked[i].value;
        if((i+1) !== checked.length){
            prePartYear += ',';
        }
    }

    // 참가의향
    //let partWantYn = document.querySelector("input[type='radio'][name='partWantYn']:checked").value;

    // 회원사 여부
    let memberCompanyYn = document.querySelector("input[type='radio'][name='memberCompanyYn']:checked").value;

    // 블로그
    let sns_blog = document.querySelector('#snsBlog').value;
    if(nvl(sns_blog,'') !== ''){
        if(!checkUrl(sns_blog)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_blog = sns_blog.toString().toLowerCase();
        }
    }

    // 페이스북
    let sns_facebook = document.querySelector('#snsFacebook').value;
    if(nvl(sns_facebook,'') !== ''){
        if(!checkUrl(sns_facebook)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_facebook = sns_facebook.toString().toLowerCase();
        }
    }

    // 인스타그램
    let sns_instagram = document.querySelector('#snsInstagram').value;
    if(nvl(sns_instagram,'') !== ''){
        if(!checkUrl(sns_instagram)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_instagram = sns_instagram.toString().toLowerCase();
        }
    }

    // 기타
    let sns_etc = document.querySelector('#snsEtc').value;
    if(nvl(sns_etc,'') !== ''){
        if(!checkUrl(sns_etc)){
            showMessage('', 'error', '[참가 업체 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }else{
            sns_etc = sns_etc.toString().toLowerCase();
        }
    }

    // 담당자 성명
    let charge_person_name_el = document.querySelectorAll('input[type=text][name=chargePersonName]');
    let charge_person_name_len = charge_person_name_el.length;
    /*let charge_person_name_flag = true;
    for(let i=0; i<charge_person_name_len; i++){
        if(charge_person_name_el[i].value === ''){
            charge_person_name_flag = false;
        }
    }
    if(!charge_person_name_flag){
        showMessage('', 'error', '[담당자 정보]', '성명을 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 직위
    let charge_person_position_el = document.querySelectorAll('input[type=text][name=chargePersonPosition]');
    /*let charge_person_position_len = charge_person_position_el.length;
    let charge_person_position_flag = true;
    for(let i=0; i<charge_person_position_len; i++){
        if(charge_person_position_el[i].value === ''){
            charge_person_position_flag = false;
        }
    }
    if(!charge_person_position_flag){
        showMessage('', 'error', '[담당자 정보]', '직위를 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 부서
    let charge_person_depart_el = document.querySelectorAll('input[type=text][name=chargePersonDepart]');

    // 담당자 전화번호
    let charge_person_tel_el = document.querySelectorAll('input[type=tel][name=chargePersonTel]');
    /*let charge_person_tel_len = charge_person_tel_el.length;
    let charge_person_tel_flag = true;
    for(let i=0; i<charge_person_tel_len; i++){
        if(charge_person_tel_el[i].value === ''){
            charge_person_tel_flag = false;
        }
    }
    if(!charge_person_tel_flag){
        showMessage('', 'error', '[담당자 정보]', '전화번호를 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 휴대전화
    let charge_person_phone_el = document.querySelectorAll('input[type=tel][name=chargePersonPhone]');
    /*let charge_person_phone_len = charge_person_phone_el.length;
    let charge_person_phone_flag = true;
    for(let i=0; i<charge_person_phone_len; i++){
        if(charge_person_phone_el[i].value === ''){
            charge_person_phone_flag = false;
        }
    }
    if(!charge_person_phone_flag){
        showMessage('', 'error', '[담당자 정보]', '휴대전화를 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 이메일
    let charge_person_email_el = document.querySelectorAll('input[type=email][name=chargePersonEmail]');
    /*let charge_person_email_len = charge_person_email_el.length;
    let charge_person_email_flag = true;
    for(let i=0; i<charge_person_email_len; i++){
        if(charge_person_email_el[i].value === ''){
            charge_person_email_flag = false;
        }
    }
    if(!charge_person_email_flag){
        showMessage('', 'error', '[담당자 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 이메일 도메인
    let charge_person_domain_el = document.querySelectorAll('input[type=email][name=chargePersonDomain]');
    /*let charge_person_domain_len = charge_person_domain_el.length;
    let charge_person_domain_flag = true;
    for(let i=0; i<charge_person_domain_len; i++){
        if(charge_person_domain_el[i].value === ''){
            charge_person_domain_flag = false;
        }
    }
    if(!charge_person_domain_flag){
        showMessage('', 'error', '[담당자 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 정보 Json Create
    let chargePersonList_json_arr = [];
    if(charge_person_name_len > 0){
        for(let i=0; i<charge_person_name_len; i++){
            let chargePersonList_json_obj = {
                'seq': $('input[type=hidden][name=chargeSeq]').eq(i).val(),
                'id': seq,
                'userId': id,
                'transferYear': transferYear,
                'chargePersonName': charge_person_name_el[i].value,
                'chargePersonPosition': charge_person_position_el[i].value,
                'chargePersonDepart': charge_person_depart_el[i].value,
                'chargePersonTel': charge_person_tel_el[i].value,
                'chargePersonPhone': charge_person_phone_el[i].value,
                'chargePersonEmail': charge_person_email_el[i].value + '@' + charge_person_domain_el[i].value,
            };
            chargePersonList_json_arr.push(chargePersonList_json_obj);
        }
    }

    // 참가분야
    let field_part_el = document.querySelectorAll('input[type=checkbox][name=fieldPart]:checked');
    let field_part_len = field_part_el.length;
    let field_part = '';
    if(field_part_len !== 0){
        for(let i=0; i<field_part_len; i++){
            field_part += field_part_el[i].value;
            if((i+1) !== field_part_len){
                field_part += ',';
            }
        }
    }else{
        showMessage('', 'error', '[참가분야]', '참가분야를 하나 이상 선택해 주세요.', '');
        return false;
    }
    //console.log(field_part);

    // 전시품목
    let display_item_el = document.querySelectorAll('input[type=text][name=displayItem]');
    let display_item_len = display_item_el.length;
    let display_item_flag = true;
    for(let i=0; i<display_item_len; i++){
        if(display_item_el[i].value === ''){
            display_item_flag = false;
        }
    }
    if(!display_item_flag){
        showMessage('', 'error', '[전시정보]', '전시품목을 입력해 주세요.', '');
        return false;
    }

    // 전시품목 브랜드명
    let display_brand_el = document.querySelectorAll('input[type=text][name=displayBrand]');
    let display_brand_len = display_brand_el.length;
    let display_brand_flag = true;
    for(let i=0; i<display_brand_len; i++){
        if(display_brand_el[i].value === ''){
            display_brand_flag = false;
        }
    }
    if(!display_brand_flag){
        showMessage('', 'error', '[전시정보]', '브랜드명을 입력해 주세요.', '');
        return false;
    }

    // 전시품목 실물 보트 수
    let display_boat_cnt_el = document.querySelectorAll('input[type=text][name=displayBoatCnt]');
    let display_boat_cnt_len = display_boat_cnt_el.length;
    let display_boat_cnt_flag = true;
    for(let i=0; i<display_boat_cnt_len; i++){
        if(display_boat_cnt_el[i].value === ''){
            display_boat_cnt_flag = false;
        }
    }
    if(!display_boat_cnt_flag){
        showMessage('', 'error', '[전시정보]', '실물 보트 수를 입력해 주세요.', '');
        return false;
    }

    // 전시품목 전시품소개 국문
    let display_item_intro_ko_el = document.querySelectorAll('textarea[name=displayItemIntroKo]');
    let display_item_intro_ko_len = display_item_intro_ko_el.length;
    let display_item_intro_ko_flag = true;
    for(let i=0; i<display_item_intro_ko_len; i++){
        if(display_item_intro_ko_el[i].value === ''){
            display_item_intro_ko_flag = false;
        }
    }
    if(!display_item_intro_ko_flag){
        showMessage('', 'error', '[전시정보]', '전시품소개(국문)를 입력해 주세요.', '');
        //return false;
    }

    // 전시품목 전시품소개 영문
    let display_item_intro_en_el = document.querySelectorAll('textarea[name=displayItemIntroEn]');
    let display_item_intro_en_len = display_item_intro_en_el.length;
    let display_item_intro_en_flag = true;
    for(let i=0; i<display_item_intro_en_len; i++){
        if(display_item_intro_en_el[i].value === ''){
            display_item_intro_en_flag = false;
        }
    }
    if(!display_item_intro_en_flag){
        showMessage('', 'error', '[전시정보]', '전시품소개(영문)를 입력해 주세요.', '');
        return false;
    }

    //전시정보 Json Create
    let displayList_json_arr = [];
    if(display_item_len > 0){
        for(let i=0; i<display_item_len; i++){
            let displayList_json_obj = {
                'seq': $('input[type=hidden][name=displayItemSeq]').eq(i).val(),
                'id': seq,
                'userId': id,
                'transferYear': transferYear,
                'displayItem': display_item_el[i].value,
                'displayBrand': display_brand_el[i].value,
                'displayBoatCnt': display_boat_cnt_el[i].value,
                'displayItemIntroKo': display_item_intro_ko_el[i].value,
                'displayItemIntroEn': display_item_intro_en_el[i].value
            };
            displayList_json_arr.push(displayList_json_obj);
        }
    }

    // 회사소개
    let company_intro_ko = document.querySelector('#companyIntroKo').value;
    if(company_intro_ko === ''){
        showMessage('', 'error', '[상세정보]', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_intro_en = document.querySelector('#companyIntroEn').value;
    if(company_intro_en === ''){
        showMessage('', 'error', '[상세정보]', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    // KIBS 참가목적
    let company_purpose_ko = document.querySelector('#companyPurposeKo').value;
    if(company_purpose_ko === ''){
        showMessage('', 'error', '[상세정보]', 'KIBS 참가목적(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_purpose_en = document.querySelector('#companyPurposeEn').value;
    if(company_purpose_en === ''){
        showMessage('', 'error', '[상세정보]', 'KIBS 참가목적(영문)을 입력해 주세요.', '');
        return false;
    }

    // 신제품출품 사항 소개
    let new_item_intro_ko = document.querySelector('#newItemIntroKo').value;
    let new_item_intro_en = document.querySelector('#newItemIntroEn').value;

    // 우리 기업 부스는 ..
    let booth_visit_reason = document.querySelector('#boothVisitReason').value;
    if(booth_visit_reason === ''){
        showMessage('', 'error', '[상세정보]', '우리 기업 부스는 꼭..? 항목을 입력해 주세요.', '');
        return false;
    }

    // 행사/이벤트 진행계획
    let event_plan = document.querySelector('#eventPlan').value;
    if(event_plan === ''){
        showMessage('', 'error', '[상세정보]', '행사/이벤트 진행계획을 입력해 주세요.', '');
        return false;
    }

    // 로고
    let logoFile_li = $('.logoFile_li').length;
    if(logoFile_li === 0){
        let logo = $('#logo').val();
        if (nvl(logo,"") === "") {
            showMessage('', 'error', '[상세정보]', '로고 파일을 업로드해주세요.', '');
            return false;
        }
    }

    // 홍보 이미지
    let promotionImageFile_li = $('.promotionImageFile_li').length;
    let promotionImageListVal = $('input[type=text][name=promotionImage]').eq(0).val();
    if( (promotionImageFile_li === 0) && nvl(promotionImageListVal,"") === ""){
        showMessage('', 'info', '[홍보 이미지]', '홍보 이미지 파일을 업로드해주세요.', '');
        return false;
    }

    // 회사소개영상
    let company_intro_video = document.querySelector('#companyIntroVideo').value;
    /*if(nvl(company_intro_video,'') !== ''){
        if(!checkUrl(company_intro_video)){
            showMessage('', 'error', '[온라인 전시관 정보]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }
    }*/

    // 제품명(국문)
    let product_name_ko_el = $('input[type=text][name=productNameKo]');
    let product_name_ko_len = product_name_ko_el.length;
    for(let i=0; i<product_name_ko_el.length; i++){
        let productNameKo = product_name_ko_el.eq(i).val();
        if (nvl(productNameKo,"") === "") {
            showMessage('', 'error', '[온라인 전시관 정보]', '제품명(국문)을 입력해주세요.', '');
            return false;
        }
    }

    let product_name_en_el = $('input[type=text][name=productNameEn]');
    for(let i=0; i<product_name_en_el.length; i++){
        let productNameEn = product_name_en_el.eq(i).val();
        if (nvl(productNameEn,"") === "") {
            showMessage('', 'error', '[온라인 전시관 정보]', '제품명(영문)을 입력해주세요.', '');
            return false;
        }
    }

    // 제품분류(대)
    let product_option_big_el = $('.preValueList select[name=productOptionBig]');
    for(let i=0; i<product_option_big_el.length; i++){
        let productOptionBig = product_option_big_el.eq(i).val();
        if (nvl(productOptionBig,"선택") === "선택") {
            showMessage('', 'error', '[온라인 전시관 정보]', '제품분류(대)를 입력해주세요.', '');
            return false;
        }
    }

    // 제품분류(소)
    let product_option_small_el = $('.preValueList select[name=productOptionSmall]');
    for(let i=0; i<product_option_small_el.length; i++){
        let productOptionSmall = product_option_small_el.eq(i).val();
        if (nvl(productOptionSmall,"선택") === "선택") {
            showMessage('', 'error', '[온라인 전시관 정보]', '제품분류(소)를 입력해주세요.', '');
            return false;
        }
    }

    // 제품사진
    let onlineInfoBox = $('.onlineInfoBox');
    for(let i=0; i<onlineInfoBox.length; i++){
        let inputFile = onlineInfoBox.eq(i).find('.upload_name').val();
        let preFileList = onlineInfoBox.eq(i).find('li.productImageFile_li').length;
        if(nvl(inputFile,"") === "" && preFileList === 0){
            showMessage('', 'error', '[온라인 전시관 정보]', '제품 사진을 업로드해주세요.', '');
            return false;
        }
    }

    // 제품설명(국문)
    let product_intro_ko_el = $('textarea[name=productIntroKo]');
    for(let i=0; i<product_intro_ko_el.length; i++){
        let productIntroKo = product_intro_ko_el.eq(i).val();
        if (nvl(productIntroKo,"") === "") {
            showMessage('', 'error', '[온라인 전시관 정보]', '제품 설명(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    // 제품설명(영문)
    let product_intro_en_el = $('textarea[name=productIntroEn]');
    for(let i=0; i<product_intro_en_el.length; i++){
        let productIntroEn = product_intro_en_el.eq(i).val();
        if (nvl(productIntroEn,"") === "") {
            showMessage('', 'error', '[온라인 전시관 정보]', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }
    }

    // 제품영상
    let product_intro_video_el = document.querySelectorAll('input[type=text][name=productIntroVideo]');

    // 전장
    let product_width_el = document.querySelectorAll('input[type=text][name=productWidth]');

    // 마력
    let product_horse_power_el = document.querySelectorAll('input[type=text][name=productHorsePower]');

    // 온라인전시회 정보 Json Create
    let onlineInfoCnt = $('.onlineInfoNum:last').text();
    let onlineList_json_arr = [];
    if(onlineInfoCnt > 0){
        for(let i=0; i<onlineInfoCnt; i++){
            let productOptionBig = '';
            let productOptionSmall = '';
            let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
            if(nvl(selProductOptionBig,"선택") !== "선택"){
                productOptionBig = $('select[name=productOptionBig]').eq(i).val();
                productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
            }else{
                productOptionBig = $('input[type=text][name=preBigValue]').eq(i).val();
                productOptionSmall = $('input[type=text][name=preSmallValue]').eq(i).val();
            }

            let onlineList_json_obj = {
                'seq': $('input[type=hidden][name=onlineItemSeq]').eq(i).val(),
                'id': seq,
                'userId': id,
                'transferYear': transferYear,
                'productNameKo': product_name_ko_el[i].value,
                'productNameEn': product_name_en_el[i].value,
                'productOptionBig': productOptionBig,
                'productOptionSmall': productOptionSmall,
                'productIntroKo': product_intro_ko_el[i].value,
                'productIntroEn': product_intro_en_el[i].value,
                'productIntroVideo': youtubeId(product_intro_video_el[i].value),
                'productWidth': product_width_el[i].value,
                'productHorsePower': product_horse_power_el[i].value,
                'note': (i+1).toString()
            };
            onlineList_json_arr.push(onlineList_json_obj);
        }
    }

    // 수출상담회
    let export_meeting_yn = document.querySelector('input[type=radio][name=exportMeetingYn]:checked').value;

    if(export_meeting_yn === 'N'){
        buyer_add_json_arr = [];
    }/*else{
        let exportMeetingYn = $('input[type=radio][name=exportMeetingYn]:checked').val();
        if(exportMeetingYn === "Y"){
            let formChugaListLen = $('.form_chuga_list').length;
            if(formChugaListLen === 0){
                showMessage('', 'info', '[수출상담회]', '수출상담회 참가를 희망하실 경우 바이어를 등록해주세요.', '');
                return false;
            }
        }
    }*/

    let buyerCompanyName_el = $('input[type=hidden][name=buyerCompanyName]');
    for(let i=0; i<buyerCompanyName_el.length; i++){
        let buyer_add_json_obj = {
            seq: $('input[type=hidden][name=buyerItemSeq]').eq(i).val(),
            id: seq,
            userId: id,
            transferYear: transferYear,
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
        }

        buyer_add_json_arr.push(buyer_add_json_obj);
    }

    let lang = sessionStorage.getItem('lang');
    if(nvl(lang,"") === ""){
        let link =  document.location.href;
        if(link.includes('eng')){
            lang = 'EN';
        }else{
            lang = 'KO';
        }
    }

    let exhibitor_json_obj = {
        seq: seq,
        lang: lang,
        id: id,
        transferYear: transferYear,
        companyNameKo: company_name_ko,
        companyNameEn: company_name_en,
        companyAddress: address,
        companyAddressDetail: address_detail,
        factoryAddress: factory_address,
        factoryAddressDetail: factory_address_detail,
        companyCeo: company_ceo,
        companyTel: company_tel,
        companyHomepage: company_homepage,
        companyFax: company_fax,
        industryPart: industryPart,
        industryPartEtc: industryPartEtc,
        employeeCnt: employeeCnt,
        companyLicenseNum: company_license_number,
        prePartYear: prePartYear,
        //partWantYn: partWantYn,
        memberCompanyYn: memberCompanyYn,
        snsBlog: sns_blog,
        snsFacebook: sns_facebook,
        snsInstagram: sns_instagram,
        snsEtc: sns_etc,
        chargePersonList: chargePersonList_json_arr,
        fieldPart: field_part,
        displayList: displayList_json_arr,
        companyIntroKo: company_intro_ko,
        companyIntroEn: company_intro_en,
        companyPurposeKo: company_purpose_ko,
        companyPurposeEn: company_purpose_en,
        //displayItemIntroKo: display_item_intro_ko,
        //displayItemIntroEn: display_item_intro_en,
        newItemIntroKo: new_item_intro_ko,
        newItemIntroEn: new_item_intro_en,
        boothVisitReason: booth_visit_reason,
        eventPlan: event_plan,
        //officeRequest: office_request,
        companyIntroVideo: youtubeId(company_intro_video),
        onlineExhibitList: onlineList_json_arr,
        exportMeetingYn: export_meeting_yn,
        buyerList: buyer_add_json_arr
    };

    //console.log('data : ' + JSON.stringify(exhibitor_json_obj));

    let resData = ajaxConnect('/mypage/step/updateExhibitor.do', 'post', exhibitor_json_obj);
    //console.log(resData);

    /* 등록 성공 시 다음 단계로 이동 */
    //let returnPath = "";
    if(resData.resultCode === "0") {
        let exhibitorSeq = resData.customValue;

        if(nvl(exhibitorSeq,"") !== ""){

            /* step 01 등록 후 buyer popup json arr 초기화 */
            if(export_meeting_yn === 'Y'){
                buyer_add_json_arr = [];
            }

            /* 파일 업로드 */
            f_company_uploadFile_call(exhibitorSeq, exhibitorSeq);

            let timerInterval;
            Swal.fire({
                title: "정보 저장 중",
                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해주세요.",
                allowOutsideClick: false,
                timer: 6000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading();
                    const timer = Swal.getPopup().querySelector("b");
                    timerInterval = setInterval(() => {
                        timer.textContent = `${Swal.getTimerLeft()}`;
                    }, 100);
                },
                willClose: () => {
                    clearInterval(timerInterval);
                }
            }).then((result) => {
                /* Read more about handling dismissals below */
                if (result.dismiss === Swal.DismissReason.timer) {
                    Swal.fire({
                        title: '참가 업체 정보',
                        html: '기본 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
                        icon: 'info',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {

                            let onlineFile_json_obj = {
                                seq: exhibitorSeq,
                                onlineExhibitList: onlineList_json_arr
                            }

                            /* 온라인 제품 사진 번호 재부여 */
                            let res = ajaxConnect('/mypage/step/updateOnlineFileNote.do', 'post', onlineFile_json_obj);

                            if(res.resultCode === "0"){
                                f_page_move('/mypage/step2_1.do', exhibitorSeq);
                            }
                        }
                    });
                }
            });
        }else{
            /*returnPath = "/apply/step2_1.do";
            return returnPath;*/
            window.location.href = '/mypage/step2_1.do';
        }
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function my_step_2_1_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_2.do', exhibitorSeq);*/

    let boothType = '등록비';
    let boothPrcSum = 0;

    // 부스 신청 - 등록비 - 수량
    let registration_cnt = parseInt(document.querySelector('#registration_cnt').value);

    // 부스 신청 - 등록비 - 금액
    let registration_fee = document.querySelector('#registration_fee').value;

    boothPrcSum += wonToNumber(registration_fee);

    // 부스 신청 - 독립부스 - 수량
    let stand_alone_booth_cnt = parseInt(document.querySelector('#stand_alone_booth_cnt').value);

    if(stand_alone_booth_cnt > 0){
        boothType += ',독립부스';
    }

    // 부스 신청 - 독립부스 - 금액
    let stand_alone_booth_fee = document.querySelector('#stand_alone_booth_fee').value;

    boothPrcSum += wonToNumber(stand_alone_booth_fee);

    // 부스 신청 - 조립부스 - 수량
    let assembly_booth_cnt = parseInt(document.querySelector('#assembly_booth_cnt').value);

    if(assembly_booth_cnt > 0){
        boothType += ',조립부스';
    }

    // 부스 신청 - 조립부스 - 금액
    let assembly_booth_fee = document.querySelector('#assembly_booth_fee').value;

    boothPrcSum += wonToNumber(assembly_booth_fee);

    // 부스 신청 - 온라인부스 - 수량
    let online_booth_cnt = parseInt(document.querySelector('select#online_booth_cnt option:checked').text);

    if(online_booth_cnt > 0){
        boothType += ',온라인부스';
    }

    // 부스 신청 - 온라인부스 - 금액
    let online_booth_fee = document.querySelector('#online_booth_fee').value;

    boothPrcSum += wonToNumber(online_booth_fee);

    let discountType = '';
    let discountPrcSum = 0;
    let boothTotalCnt = stand_alone_booth_cnt + assembly_booth_cnt + online_booth_cnt;

    // 할인적용 - 1차 조기신청
    let discount_early_1 = document.querySelector('input[type=checkbox][id=discount1]').checked;
    if(discount_early_1){
        discountType += ',1차조기신청';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 2차 조기신청
    let discount_early_2 = document.querySelector('input[type=checkbox][id=discount2]').checked;
    if(discount_early_2){
        discountType += ',2차조기신청';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 재참가할인 All
    let discount_re_all = document.querySelector('input[type=checkbox][id=discount3]').checked;
    if(discount_re_all){
        discountType += ',재참가할인';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 규모할인 1 (20부스 이상)
    let discount_scale_2 = document.querySelector('input[type=checkbox][id=discount5]').checked;
    if(discount_scale_2){
        discountType += ',규모할인1';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 규모할인 2 (40부스 이상)
    let discount_scale_3 = document.querySelector('input[type=checkbox][id=discount6]').checked;
    if(discount_scale_3){
        discountType += ',규모할인2';
        discountPrcSum += (boothTotalCnt * 450000);
    }

    // 할인적용 - 첫 참가 할인
    let discount_first = document.querySelector('input[type=checkbox][id=discount7]').checked;
    if(discount_first){
        discountType += ',첫참가';
        discountPrcSum += (boothTotalCnt * 500000);
    }

    // 할인적용 - 한국해양레저산업협회 할인
    let discount_leisure = document.querySelector('input[type=checkbox][id=discount8]').checked;
    if(discount_leisure){
        discountType += ',한국해양레저산업협회';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    if(discountType.substring(0,1) === ','){
        discountType = discountType.substring(1);
    }

    let discountYn = 'N';
    if(nvl(discountType,'') !== ''){
        discountYn = 'Y';
    }

    //let boothPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    if(stand_alone_booth_cnt + assembly_booth_cnt + online_booth_cnt === 0){
        showMessage('', 'error', '[전시부스 신청정보]', '부스(독립,조립,온라인)를 하나 이상 신청해 주세요.', '');
        return false;
    }

    let booth_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear, //2024
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registration_cnt,
        registrationFee: wonToInt(registration_fee),
        standAloneBoothCnt: stand_alone_booth_cnt,
        standAloneBoothFee: wonToInt(stand_alone_booth_fee),
        assemblyBoothCnt: assembly_booth_cnt,
        assemblyBoothFee: wonToInt(assembly_booth_fee),
        onlineBoothCnt: online_booth_cnt,
        onlineBoothFee: wonToInt(online_booth_fee),
        discountEarly1: discount_early_1,
        discountEarly2: discount_early_2,
        discountReAll: discount_re_all,
        discountScale2: discount_scale_2,
        discountScale3: discount_scale_3,
        discountFirst: discount_first,
        discountLeisure: discount_leisure,
        discountYn: discountYn,
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum
    }

    let resData = ajaxConnect('/mypage/step/updateExhibitBooth.do', 'post', booth_json_obj);

    let returnPath = "";
    //console.log(resData);
    if(resData.resultCode === "0") {

        Swal.fire({
            title: '참가 업체 정보',
            html: '부스 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_2.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '전시부스 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function my_step_2_2_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_3.do', exhibitorSeq);*/

    // 상호간판 신청 - 상호간판명 (국문)
    let company_sign_name_ko = $('#company_sign_name_ko').val();
    // 상호간판 신청 - 상호간판명 (영문)
    let company_sign_name_en = $('#company_sign_name_en').val();

    //let standAloneBoothCnt = parseInt(nvl($('#standAloneBoothCnt').val(),'0'));
    let assemblyBoothCnt = parseInt(nvl($('#assemblyBoothCnt').val(),'0'));

    if(nvl(company_sign_name_ko,'') === '' || nvl(company_sign_name_en,'') === ''){
        if(assemblyBoothCnt > 0) {
            showMessage('', 'error', '[상호간판 신청정보]', '조립부스를 신청하신 참가업체는<br>반드시 상호간판을 신청해 주셔야 합니다.', '');
            return false;
        }
    }else{
        if(assemblyBoothCnt === 0) {
            showMessage('', 'error', '[상호간판 신청정보]', '상호간판은 조립부스를 신청한 참가기업만 작성 가능합니다.', '');
            return false;
        }
    }

    let sign_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        companySignNameKo: company_sign_name_ko,
        companySignNameEn: company_sign_name_en
    }

    let resData = ajaxConnect('/mypage/step/updateCompanySign.do', 'post', sign_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '상호간판 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_3.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '상호간판 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function my_step_2_3_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_4.do', exhibitorSeq);*/

    // 유틸리티 신청정보 - 주간단상 - 수량
    let utility_jugan_cnt = parseInt(document.querySelector('#utility_jugan_cnt').value);

    // 유틸리티 신청정보 - 주간단상 - 금액
    let utility_jugan_fee = document.querySelector('#utility_jugan_fee').value;

    // 유틸리티 신청정보 - 24시간용 - 수량
    let utility_day_cnt = parseInt(document.querySelector('#utility_day_cnt').value);

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_day_fee = document.querySelector('#utility_day_fee').value;

    // 유틸리티 신청정보 - 압축공기 - 수량
    let utility_compressed_air_cnt = parseInt(document.querySelector('#utility_compressed_air_cnt').value);

    // 유틸리티 신청정보 - 압축공기 - 금액
    let utility_compressed_air_fee = document.querySelector('#utility_compressed_air_fee').value;

    // 유틸리티 신청정보 - 급배수 - 수량
    let utility_water_basic_cnt = parseInt(document.querySelector('#utility_water_basic_cnt').value);

    // 유틸리티 신청정보 - 급배수 - 금액
    let utility_water_basic_fee = document.querySelector('#utility_water_basic_fee').value;

    // 유틸리티 신청정보 - 인터넷 - 수량
    let utility_internet_cnt = parseInt(document.querySelector('#utility_internet_cnt').value);

    // 유틸리티 신청정보 - 인터넷 - 금액
    let utility_internet_fee = document.querySelector('#utility_internet_fee').value;

    // 유틸리티 신청정보 - 파이텍스 (신품) - 수량
    let utility_pytex_new_cnt = parseInt(document.querySelector('#utility_pytex_new_cnt').value);

    // 유틸리티 신청정보 - 파이텍스 (신품) - 금액
    let utility_pytex_new_fee = document.querySelector('#utility_pytex_new_fee').value;

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 수량
    let utility_pytex_re_cnt = parseInt(document.querySelector('#utility_pytex_re_cnt').value);

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 금액
    let utility_pytex_re_fee = document.querySelector('#utility_pytex_re_fee').value;

    // 유틸리티 신청정보 - 바코드 리더기 - 수량
    let utility_barcode_cnt = parseInt(document.querySelector('#utility_barcode_cnt').value);

    // 유틸리티 신청정보 - 바코드 리더기 - 금액
    let utility_barcode_fee = document.querySelector('#utility_barcode_fee').value;

    let utilityPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let utility_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        utilityJuganCnt: utility_jugan_cnt,
        utilityJuganFee: wonToInt(utility_jugan_fee),
        utilityDayCnt: utility_day_cnt,
        utilityDayFee: wonToInt(utility_day_fee),
        utilityCompressedAirCnt: utility_compressed_air_cnt,
        utilityCompressedAirFee: wonToInt(utility_compressed_air_fee),
        utilityWaterBasicCnt: utility_water_basic_cnt,
        utilityWaterBasicFee: wonToInt(utility_water_basic_fee),
        utilityInternetCnt: utility_internet_cnt,
        utilityInternetFee: wonToInt(utility_internet_fee),
        utilityPytexNewCnt: utility_pytex_new_cnt,
        utilityPytexNewFee: wonToInt(utility_pytex_new_fee),
        utilityPytexReCnt: utility_pytex_re_cnt,
        utilityPytexReFee: wonToInt(utility_pytex_re_fee),
        utilityBarcodeCnt: utility_barcode_cnt,
        utilityBarcodeFee: wonToInt(utility_barcode_fee),
        utilityPrcSum: utilityPrcSum
    }

    let resData = ajaxConnect('/mypage/step/updateExhibitUtility.do', 'post', utility_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '유틸리티 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_4.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '유틸리티 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function my_step_2_4_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_5.do', exhibitorSeq);*/

    // 성명(국문)
    let pass_name_el = document.querySelectorAll('input[type=text][name=pass_name]');
    let pass_name_len = pass_name_el.length;
    let pass_name_flag = true;
    for(let i=0; i<pass_name_len; i++){
        if(pass_name_el[i].value === ''){
            pass_name_flag = false;
        }
    }
    if(!pass_name_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '성명(국문)을 입력해 주세요.', '');
        return false;
    }

    // 출입증이름(영문)
    let pass_first_name_el = document.querySelectorAll('input[type=text][name=pass_first_name]');
    let pass_first_name_len = pass_first_name_el.length;
    let pass_first_name_flag = true;
    for(let i=0; i<pass_first_name_len; i++){
        if(pass_first_name_el[i].value === ''){
            pass_first_name_flag = false;
        }
    }
    if(!pass_first_name_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '성명(영문) 이름을 입력해 주세요.', '');
        return false;
    }

    // 출입증성(영문)
    let pass_last_name_el = document.querySelectorAll('input[type=text][name=pass_last_name]');
    let pass_last_name_len = pass_last_name_el.length;
    let pass_last_name_flag = true;
    for(let i=0; i<pass_last_name_len; i++){
        if(pass_last_name_el[i].value === ''){
            pass_last_name_flag = false;
        }
    }
    if(!pass_last_name_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '성명(영문) 성을 입력해 주세요.', '');
        return false;
    }

    // 직책(국문)
    let pass_position_ko_el = document.querySelectorAll('input[type=text][name=pass_position_ko]');
    let pass_position_ko_len = pass_position_ko_el.length;
    let pass_position_ko_flag = true;
    for(let i=0; i<pass_position_ko_len; i++){
        if(pass_position_ko_el[i].value === ''){
            pass_position_ko_flag = false;
        }
    }
    if(!pass_position_ko_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '직책(국문)을 입력해 주세요.', '');
        return false;
    }

    // 직책(영문)
    let pass_position_en_el = document.querySelectorAll('input[type=text][name=pass_position_en]');
    let pass_position_en_len = pass_position_en_el.length;
    let pass_position_en_flag = true;
    for(let i=0; i<pass_position_en_len; i++){
        if(pass_position_en_el[i].value === ''){
            pass_position_en_flag = false;
        }
    }
    if(!pass_position_en_flag){
        showMessage('', 'error', '[출입증 신청 정보]', '직책(영문)을 입력해 주세요.', '');
        return false;
    }

    // 출입증비고
    let pass_note_el = document.querySelectorAll('input[type=text][name=pass_note]');

    let pass_json_arr = [];
    if(pass_name_len > 0){
        for(let i=0; i<pass_name_len; i++){
            let pass_json_obj = {
                seq: $('input[type=hidden][name=passSeq]').eq(i).val(),
                id: exhibitorSeq,
                userId: sessionStorage.getItem('id'),
                transferYear: transferYear,
                passName: pass_name_el[i].value,
                passFirstName: pass_first_name_el[i].value,
                passLastName: pass_last_name_el[i].value,
                passPositionKo: pass_position_ko_el[i].value,
                passPositionEn: pass_position_en_el[i].value,
                passNote: pass_note_el[i].value
            };
            pass_json_arr.push(pass_json_obj);
        }
    }

    let passList_json_arr = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        passList: pass_json_arr
    };

    let resData = ajaxConnect('/mypage/step/updateExhibitPass.do', 'post', passList_json_arr);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '출입증 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_5.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '출입증 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }

}

function my_step_2_5_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_8.do', exhibitorSeq);*/

    let giftList_json_obj = {
        seq: exhibitorSeq,
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        giftList: gift_add_json_arr,
    };

    let resData = ajaxConnect('/mypage/step/updateExhibitGift.do', 'post', giftList_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        gift_add_json_arr = [];

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '경품제공 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_8.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '경품제공 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function my_step_2_8_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step03.do', exhibitorSeq);*/

    let directory_json_obj = {
        userId: sessionStorage.getItem('id'),
        transferYear: transferYear
    };

    let resData = ajaxConnect('/apply/step/insertDirectory.do', 'post', directory_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '디렉토리 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step03.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '디렉토리 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}

function my_step_03_check(exhibitorSeq){

    let apply_comp_json_obj = {
        id: sessionStorage.getItem('id'),
        transferYear: transferYear,
        approvalStatus: '승인요청',
        applyComplt: 'Y'
    }

    let resData = ajaxConnect('/apply/step/updateExhibitStatus.do', 'post', apply_comp_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            title: '참가 업체 정보',
            html: '참가 신청이 정상 완료되었습니다.<br>등록하신 담당자 메일로 신청완료 및 안내 메일이 발송됩니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {

                let charge_jsonObj = {
                    id: sessionStorage.getItem('id'),
                    transferYear: transferYear
                }
                let charge_resData = ajaxConnect('/apply/step/directory/selectChargeInfo.do','post', charge_jsonObj);

                let jsonObj = makeJsonFormat(charge_resData);
                //console.log(JSON.stringify(jsonObj));
                let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                //console.log(i , resData);
                if (resData.resultCode === "0") {
                    /* 등록 성공 시 다음 단계로 이동 */
                    home();
                }
            }
        })
    }else{
        showMessage('', 'error', '[참가 업체 정보]', '참가 신청 정보 저장에 실패하였습니다. 관리자에게 문의해주세요.', '');
    }
}


function exibitloginFormSubmit() {
    let form = document.getElementById("login_form");
    let id = document.getElementById("exhibitId");
    let password = document.getElementById("exhibitPw");

    if (id.value.trim() === "" || password.value.trim() === "") {
        showMessage('', 'info', '입력 정보 확인', '아이디와 비밀번호를 입력해 주세요.', '');
        return false;
    }

    let jsonObj = {
        id: id.value,
        password: password.value,
        transferYear: transferYear
    };

    $.ajax({
        url: '/exhibit/loginForm.do',
        method: 'post',
        data: JSON.stringify(jsonObj),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
        .done(function (data) {
            if (data.resultCode === "0") {

                let hiddenField_id = document.createElement('input');
                hiddenField_id.type = 'hidden';
                hiddenField_id.name = 'id';
                hiddenField_id.value = id.value;
                let hiddenField_pw = document.createElement('input');
                hiddenField_pw.type = 'hidden';
                hiddenField_pw.name = 'password';
                hiddenField_pw.value = password.value;
                let hiddenField_year = document.createElement('input');
                hiddenField_year.type = 'hidden';
                hiddenField_year.name = 'transferYear';
                hiddenField_year.value = transferYear.toString();
                
                form.appendChild(hiddenField_id); //아이디
                form.appendChild(hiddenField_pw); //비밀번호
                form.appendChild(hiddenField_year); //참가년도

                document.body.appendChild(form);

                sessionStorage.setItem('id', id.value);

                form.submit(); // /mypage/index.do
            } else {
                showMessage('', 'info', '로그인 실패', '아이디와 비밀번호를 확인해주세요.', '');
            }
        })
        .fail(function (xhr, status, errorThrown) {
            /*$('body').html("오류가 발생했습니다.")
                .append("<br>오류명: " + errorThrown)
                .append("<br>상태: " + status);*/
            alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
        })

}

function f_pre_apply_check_login(){
    let name = $('#name').val();
    if(nvl(name,'') === ''){ showMessage('', 'info', '입력 정보 확인', '이름을 입력해 주세요.', ''); return false; }
    let phone = $('#phone').val();
    if(nvl(phone,'') === ''){ showMessage('', 'info', '입력 정보 확인', '휴대전화번호를 입력해 주세요.', ''); return false; }

    let jsonObj = {
        name: name,
        phone: phone,
        joinYear: transferYear
    };

    let resData = ajaxConnectSimple('/visitor/preApplyCheck.do', 'post', jsonObj);
    if(nvl(resData,'') !== ''){
        Swal.fire({
            title: '[참관 신청 확인]',
            html: '참관 신청 확인 되었습니다.<br>참관신청확인페이지로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/visitor/mypage.do?seq=' + resData.seq;
            }
        })
    }else{
        Swal.fire({
            title: '[참관 신청 확인]',
            html: '참관 신청 정보가 없습니다.<br>사전등록페이지로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/visitor/apply.do';
            }
        })
    }
}

function f_en_pre_apply_check_login(){
    let name = $('#name').val();
    if(nvl(name,'') === ''){ showMessage('', 'info', '[Confirm]', 'Please enter your name.', ''); return false; }
    let phone = $('#phone').val();
    if(nvl(phone,'') === ''){ showMessage('', 'info', '[Confirm]', 'Please enter your phone number.', ''); return false; }

    let jsonObj = {
        name: name,
        phone: phone,
        joinYear: transferYear
    };

    let resData = ajaxConnectSimple('/visitor/preApplyCheck.do', 'post', jsonObj);
    if(nvl(resData,'') !== ''){
        Swal.fire({
            title: '[Confirm]',
            html: 'Your application for observation has been confirmed.<br>Go to confirm application Page.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'OK'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/eng/buyer/mypage.do?seq=' + resData.seq;
            }
        })
    }else{
        Swal.fire({
            title: '[Confirm]',
            html: 'There is no information on the application for observation.<br>Go to the pre-registration page.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'OK'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/eng/buyer/apply.do';
            }
        })
    }
}

function f_company_search(){
    let companyName = $('#search_companyName').val();
    if(nvl(companyName,'') !== ''){
        let jsonObj = {
            wkplNm: companyName
        }
        
        $.ajax({
            url: '/visitor/companySearch.do',
            method: 'post',
            /*async: false,*/
            data: JSON.stringify(jsonObj),
            contentType: 'application/json; charset=utf-8', //server charset 확인 필요
            beforeSend : function(request){
                // Performed before calling Ajax
                $('#spinner').show();
            },
            success: function (data) {
                if(nvl(data,'') !== ''){
                    if(nvl(data.header,'') !== ''){
                        let resultCode = data.header.resultCode;
                        if(resultCode === '00'){
                            let totalCount = data.body.totalCount;
                            if(totalCount > 0){
                                let items = data.body.items.item;
                                let uniqueList = [];
                                items.forEach((element) => {
                                    let wkplNm = element.wkplNm;
                                    let wkplRoadNmDtlAddr = element.wkplRoadNmDtlAddr
                                    let temp_val = wkplNm + '/' + wkplRoadNmDtlAddr;
                                    if (!uniqueList.includes(temp_val)) {
                                        uniqueList.push(temp_val);
                                    }
                                });

                                let str = '';
                                uniqueList.forEach((val) => {
                                    let name = val.toString().split('/')[0];
                                    let addr = val.toString().split('/')[1];
                                    str += '<li>';
                                    str += '<a href="javascript:void(0);" onclick="f_company_info_add(this)">';
                                    str += '<div class="name">';
                                    str += name;
                                    str += '</div>';
                                    str += '<div class="address">';
                                    str += addr;
                                    str += '</div>';
                                    str += '</a>';
                                    str += '</li>';
                                });

                                $('.popCompanyName .companyList').html(str);
                            }else{
                                let str = '';
                                str += '<div class="companyNot">';
                                str += '검색결과가 없습니다.';
                                str += '<br>';
                                str += 'No results were found for your search.';
                                str += '</div>';
                                $('.popCompanyName .companyList').html(str);
                            }

                        }else{
                            showMessage('', 'error', '[ERROR]', '직장명 검색에 실패하였습니다. 관리자에게 문의해주세요. ', '');
                        }
                    }else{
                        alert('조회가 실패하였습니다. 직장명을 직접 입력해 주세요.');
                        $('#spinner').hide();
                    }

                }else{
                    let str = '';
                    str += '<div class="companyNot">';
                        str += '검색결과가 없습니다.';
                        str += '<br>';
                        str += 'No results were found for your search.';
                    str += '</div>';
                    $('.popCompanyName .companyList').html(str);
                }

                $('#spinner').hide();
            },
            error: function() {
                // Do when ajax call fail
                alert('오류가 발생했습니다. 관리자에게 문의해주세요.');
                $('#spinner').hide();
            }
        })
    }
}

function f_company_info_add(el){
    let companyName = $(el).find('.name').text();
    let companyAddr = $(el).find('.address').text();
    $('#companyName').val(companyName);
    $('#companyAddress').val(companyAddr);
    $('.popup .close').click();
}

function f_company_info_direct_add(){
    let companyName = $('.popDirectName #pop_companyName').val();
    let companyAddress = $('.popDirectName #pop_address').val();
    let companyAddressDetail = $('.popDirectName #pop_addressDetail').val();
    $('#companyName').val(companyName);
    $('#companyAddress').val(companyAddress + ', ' + companyAddressDetail);
    $('.popup .close').click();
}

function strCheck(str, type) {
    let REGEX = {
        EMAIL: /\S+@\S+\.\S+/,
        PWD_RULE: /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,16}$/,
        NAME_RULE: /^([ㄱ-ㅎㅏ-ㅣ가-힣ㆍᆢa-zA-Z\s]{2,20})$/,
        KOR_RULE: /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/
    };
    if (type === "email") {
        return REGEX.EMAIL.test(str);
    } else if (type === "pws") {
        return REGEX.PWD_RULE.test(str);
    } else if (type === "name") {
        return REGEX.NAME_RULE.test(str);
    } else if (type === "kor") {
        return REGEX.KOR_RULE.test(str);
    } else {
        return false;
    }
}

function f_visitor_apply(gbn){

    /* 유효성 검사 */
    let validCheck = f_visitor_form_valid_check(gbn);

    if(validCheck){

        let showMsg = '입력된 정보로 사전등록하시겠습니까?';
        let showBtn = '사전등록하기';
        if(gbn === 'U'){
            showMsg = '입력된 정보로 수정하시겠습니까?'
            showBtn = '수정하기';
        }

        Swal.fire({
            title: '[사전등록하기]',
            html: showMsg,
            icon: 'info',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: showBtn,
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                /* form data setting */
                let data = f_visitor_form_data_setting();

                $.ajax({
                    url: '/visitor/save.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            let visitorSeq = data.customValue; //visitorSeq return 값

                            let returnUrl = '/visitor/completed.do';
                            if(gbn === 'U'){
                                returnUrl = '/visitor/mypage.do?seq=' + visitorSeq;
                            }
                            window.location.href = returnUrl;

                            /*Swal.fire({
                                title: '[사전등록하기]',
                                html: '참관객 사전등록이 완료되었습니다.',
                                icon: 'info',
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                }
                            });*/

                        } else {
                            showMessage('', 'error', '에러 발생', '참관객 사전 등록 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })

            }//isConfirmed
        })//then

    }//validCheck

}

function f_visitor_form_valid_check(gbn){

    if(gbn === 'I'){
        let agree1 = $('input[type=radio][name=agree1]:checked').val();
        if(nvl(agree1,'') === '' || agree1 === 'N'){
            showMessage('', 'info', '[약관 동의]', '개인정보수집 및 이용안내 항목에 동의해 주세요.', '');
            return false;
        }

        let agree2 = $('input[type=radio][name=agree2]:checked').val();
        if(nvl(agree2,'') === '' || agree2 === 'N'){
            showMessage('', 'info', '[약관 동의]', '개인정보 제3자 제공에 대한 별도 동의 항목에 동의해 주세요.', '');
            return false;
        }

        let agree3 = $('input[type=radio][name=agree3]:checked').val();
        if(nvl(agree3,'') === '' || agree3 === 'N'){
            showMessage('', 'info', '[약관 동의]', '안전 준수 동의 항목에 동의해 주세요.', '');
            return false;
        }

        let partGbn = $('input[type=radio][name=partGbn]:checked').val();
        if(nvl(partGbn,'') === ''){
            showMessage('', 'info', '[참관 구분]', '참관 구분 항목을 선택해 주세요.', '');
            return false;
        }

        let name = $('#name').val();
        if (nvl(name,'') === '') {
            showMessage('', 'info', '[참관객 정보]', '성명을 입력해 주세요.', '');
            return false;
        }

        let phone = $('#phone').val();
        if (nvl(phone,'') === '') {
            showMessage('', 'info', '[참관객 정보]', '휴대전화를 입력해 주세요.', '');
            return false;
        }

        if(!strCheck(name,"name")){
            showMessage('#name', 'info', '[참관객 정보]', '올바른 이름을 입력해 주세요. (특수문자 제외)', '');
            return false;
        }else{
            let json = { joinYear: transferYear, name : name , phone: phone };
            let resData = ajaxConnectSimple('/visitor/preApplyCheck.do', 'post', json);
            if(nvl(resData,'') !== ''){
                Swal.fire({
                    title: '[기존 정보 존재]',
                    html: '이미 사전 등록된 성명, 휴대전화번호 입니다.<br>참관신청확인페이지에서 정보를 확인해주세요.',
                    icon: 'info',
                    allowOutsideClick: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '확인'
                });
                return false;
            }
        }

    }

    let email = $('#email').val();
    if (nvl(email,'') === '') {
        showMessage('', 'info', '[참관객 정보]', '이메일을 입력해 주세요.', '');
        return false;
    }else{
        if(email.includes('@')){
            showMessage('', 'info', '[참관객 정보]', '포함될 수 없는 특수문자(@)가 있습니다.<br>이메일과 도메인을 따로 입력해 주세요.', '');
            return false;
        }
    }

    let domain = $('#domain').val();
    if (nvl(domain,'') === '') {
        showMessage('', 'info', '[참관객 정보]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    if(nvl($('input[type=radio][name=partGbn]:checked').val(),'') === '바이어'){
        let companyName = $('#companyName').val();
        if (nvl(companyName,'') === '') {
            showMessage('#companyName', 'info', '[참관객 정보]', '참관 구분>바이어 선택 시 직장명을 필수 입력해 주세요.', '');
            return false;
        }

        let companyAddress = $('#companyAddress').val();
        if (nvl(companyAddress,'') === '') {
            showMessage('#companyAddress', 'info', '[참관객 정보]', '참관 구분>바이어 선택 시 직장 주소를 필수 입력해 주세요.', '');
            return false;
        }
    }

    let partnerYn = $('input[type=radio][name=partnerYn]:checked').val();
    if(partnerYn === "Y"){
        let partnerNameList = $('input[type=text][name=partnerName]');
        for(let i=0; i<partnerNameList.length; i++){
            let partnerName = partnerNameList.eq(i).val();
            if (nvl(partnerName,'') === '') {
                showMessage('', 'info', '[참관객 정보]', '동반자가 있을 경우 동반자 이름을 입력해 주세요.', '');
                return false;
            }
        }

        let partnerAgeList = $('input[type=text][name=partnerAge]');
        for(let i=0; i<partnerAgeList.length; i++){
            let partnerAge = partnerAgeList.eq(i).val();
            if (nvl(partnerAge,'') === '') {
                showMessage('', 'info', '[참관객 정보]', '동반자가 있을 경우 동반자 나이를 입력해 주세요.', '');
                return false;
            }
        }
    }

    let sex = $('input[type=radio][name=sex]:checked').val();
    if(nvl(sex,'') === ''){
        showMessage('', 'info', '[설문항목]', '성별을 선택해 주세요.', '');
        return false;
    }

    let sidoList = $('select[name=sido]');
    for(let i=0; i<sidoList.length; i++){
        let sido = sidoList.eq(i).val();
        if (nvl(sido,'시/도 선택') === '시/도 선택') {
            showMessage('', 'info', '[설문항목]', '지역 시/도를 선택해 주세요.', '');
            return false;
        }
    }

    /*let gugunList = $('select[name=gugun]');
    for(let i=0; i<gugunList.length; i++){
        let gugun = gugunList.eq(i).val();
        if (nvl(gugun,'구/군 선택') === '구/군 선택') {
            showMessage('', 'info', '[설문항목]', '지역 구/군을 선택해 주세요.', '');
            return false;
        }
    }*/

    let ageGroup = $('input[type=radio][name=ageGroup]:checked').val();
    if(nvl(ageGroup,'') === ''){
        showMessage('', 'info', '[설문항목]', '연령대를 선택해 주세요.', '');
        return false;
    }

    let partGbn = $('input[type=radio][name=partGbn]:checked').val();
    if(nvl(partGbn,'') === '일반관람') {
        let observationGbn = $('input[type=checkbox][name=observationGbn]').is(':checked');
        if (!observationGbn) {
            showMessage('', 'info', '[설문항목]', '관람 구분을 하나 이상 체크해주세요.', '');
            return false;
        }
    }

    let visitPurpose = $('input[type=checkbox][name=visitPurpose]').is(':checked');
    if (!visitPurpose) {
        showMessage('', 'info', '[설문항목]', '보트쇼 방문 목적을 하나 이상 체크해주세요.', '');
        return false;
    }

    let interestItem = $('input[type=checkbox][name=interestItem]').is(':checked');
    if (!interestItem) {
        showMessage('', 'info', '[설문항목]', '관심품목을 하나 이상 체크해주세요.', '');
        return false;
    }

    let recognizePath = $('input[type=checkbox][name=recognizePath]').is(':checked');
    if (!recognizePath) {
        showMessage('', 'info', '[설문항목]', '인지경로를 하나 이상 체크해주세요.', '');
        return false;
    }

    let preObservationGbn = $('input[type=checkbox][name=preObservationGbn]').is(':checked');
    if (!preObservationGbn) {
        showMessage('', 'info', '[설문항목]', '지난 전시회 참관 여부를 하나 이상 체크해주세요.', '');
        return false;
    }

    return true;
}

function f_visitor_form_data_setting(){

    let joinForm = JSON.parse(JSON.stringify($('#joinForm').serializeObject()));

    joinForm.lang = 'KO';
    joinForm.joinYear = transferYear;
    joinForm.joinYn = 'Y';
    joinForm.visitorGbn = '개인';

    joinForm.domain = $('#domain').val();

    joinForm.regionSi = joinForm.sido;
    joinForm.regionGu = nvl(joinForm.gugun,'-');
    joinForm.country = '';

    let partnerInfoArr = [];
    if(joinForm.partnerYn === 'Y'){
        //동반자 Setting
        let visitPartnerCnt = parseInt($('.visitPartnerNum:last').text());
        for(let i=0; i<visitPartnerCnt; i++){
            let visitPartnerObj = {
                seq: $('input[type=hidden][name=partnerSeq]').eq(i).val(),
                visitorSeq: $('input[type=hidden][name=visitorSeq]').val(),
                name: $('#name').val(),
                phone: $('#phone').val(),
                partnerName: $('input[name=partnerName]').eq(i).val(),
                partnerAge: $('input[name=partnerAge]').eq(i).val()
            };
            partnerInfoArr.push(visitPartnerObj);
        }
    }
    joinForm.partner = partnerInfoArr;

    // 참관 구분
    let observationGbnList = $('input[type=checkbox][name=observationGbn]:checked');
    let observationGbn = '';
    let partGbn = $('input[type=radio][name=partGbn]:checked').val();
    if(partGbn === '일반관람') {
        for (let i = 0; i < observationGbnList.length; i++) {
            observationGbn += observationGbnList.eq(i).val();
            if ((i + 1) !== observationGbnList.length) {
                observationGbn += ',';
            }
        }
    }
    joinForm.observationGbn = observationGbn;

    // 보트쇼 방문 목적
    let visitPurposeList = $('input[type=checkbox][name=visitPurpose]:checked');
    let visitPurpose = '';
    for(let i=0; i<visitPurposeList.length; i++){
        visitPurpose += visitPurposeList.eq(i).val();
        if((i+1) !== visitPurposeList.length){
            visitPurpose += ',';
        }
    }
    joinForm.visitPurpose = visitPurpose;

    // 관심품목
    let interestItemList = $('input[type=checkbox][name=interestItem]:checked');
    let interestItem = '';
    for(let i=0; i<interestItemList.length; i++){
        interestItem += interestItemList.eq(i).val();
        if((i+1) !== interestItemList.length){
            interestItem += ',';
        }
    }
    joinForm.interestItem = interestItem;

    // 인지경로
    let recognizePathList = $('input[type=checkbox][name=recognizePath]:checked');
    let recognizePath = '';
    for(let i=0; i<recognizePathList.length; i++){
        recognizePath += recognizePathList.eq(i).val();
        if((i+1) !== recognizePathList.length){
            recognizePath += ',';
        }
    }
    joinForm.recognizePath = recognizePath;

    // 지난 전시회 참관 여부
    let preObservationGbnList = $('input[type=checkbox][name=preObservationGbn]:checked');
    let preObservationGbn = '';
    for(let i=0; i<preObservationGbnList.length; i++){
        preObservationGbn += preObservationGbnList.eq(i).val();
        if((i+1) !== preObservationGbnList.length){
            preObservationGbn += ',';
        }
    }
    joinForm.preObservationGbn = preObservationGbn;

    return JSON.stringify(joinForm);
}

function f_en_visitor_apply(gbn){

    if(gbn === 'I'){

        let json = { joinYear: transferYear, name : $('#name').val() , phone: $('#phone').val() };
        let resData = ajaxConnectSimple('/visitor/preApplyCheck.do', 'post', json);
        if(nvl(resData,'') !== ''){
            Swal.fire({
                title: '[Already applied]',
                html: 'This is a pre-registered name, mobile number.<br>Please login and check the information.',
                icon: 'info',
                allowOutsideClick: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'OK'
            });
            return;
        }

    }

    let showMsg = 'Do you want to pre-register with the information entered?';
    if(gbn === 'U'){
        showMsg = 'Are you sure you want to modify the information you entered?'
    }

    Swal.fire({
        title: '[Pre-registration]',
        html: showMsg,
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: 'OK',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {

            /* 유효성 검사 */
            let validCheck = f_en_visitor_form_valid_check(gbn);

            if(validCheck){

                /* form data setting */
                let data = f_en_visitor_form_data_setting();

                $.ajax({
                    url: '/visitor/save.do',
                    method: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        if (data.resultCode === "0") {

                            let visitorSeq = data.customValue; //visitorSeq return 값

                            let returnUrl = '/eng/buyer/completed.do';
                            if(gbn === 'U'){
                                returnUrl = '/eng/buyer/mypage.do?seq=' + visitorSeq;
                            }
                            window.location.href = returnUrl;

                            /*Swal.fire({
                                title: '[사전등록하기]',
                                html: '참관객 사전등록이 완료되었습니다.',
                                icon: 'info',
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                }
                            });*/

                        } else {
                            showMessage('', 'error', '에러 발생', '참관객 사전 등록 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                    }
                })

            }//validCheck
        }//isConfirmed
    })//then

}

function f_en_visitor_form_valid_check(gbn){

    if(gbn === 'I'){
        let agree1 = $('input[type=radio][name=agree1]:checked').val();
        if(nvl(agree1,'') === '' || agree1 === 'N'){
            showMessage('', 'info', '[Agreement]', '개인정보수집 및 이용안내 항목에 동의해 주세요.', '');
            return false;
        }

        let agree2 = $('input[type=radio][name=agree2]:checked').val();
        if(nvl(agree2,'') === '' || agree2 === 'N'){
            showMessage('', 'info', '[Agreement]', '개인정보 제3자 제공에 대한 별도 동의 항목에 동의해 주세요.', '');
            return false;
        }

        let agree3 = $('input[type=radio][name=agree3]:checked').val();
        if(nvl(agree3,'') === '' || agree3 === 'N'){
            showMessage('', 'info', '[Agreement]', '안전 준수 동의 항목에 동의해 주세요.', '');
            return false;
        }

        let name = $('#name').val();
        if (nvl(name,'') === '') {
            showMessage('', 'info', '[Visitor Information]', 'Please enter your name.', '');
            return false;
        }

        let phone = $('#phone').val();
        if (nvl(phone,'') === '') {
            showMessage('', 'info', '[Visitor Information]', 'Please enter your mobile phone number.', '');
            return false;
        }
    }

    let email = $('#email').val();
    if (nvl(email,'') === '') {
        showMessage('', 'info', '[Visitor Information]', 'Please enter your email.', '');
        return false;
    }else{
        if(email.includes('@')){
            showMessage('', 'info', '[Visitor Information]', 'Please enter the email and domain separately.', '');
            return false;
        }
    }

    let domain = $('#domain').val();
    if (nvl(domain,'') === '') {
        showMessage('', 'info', '[Visitor Information]', 'Please enter your email domain.', '');
        return false;
    }

    let partnerYn = $('input[type=radio][name=partnerYn]:checked').val();
    if(partnerYn === "Y"){
        let partnerNameList = $('input[type=text][name=partnerName]');
        for(let i=0; i<partnerNameList.length; i++){
            let partnerName = partnerNameList.eq(i).val();
            if (nvl(partnerName,'') === '') {
                showMessage('', 'info', '[Visitor Information]', 'If you have a companion, please enter your companion name.', '');
                return false;
            }
        }

        let partnerAgeList = $('input[type=text][name=partnerAge]');
        for(let i=0; i<partnerAgeList.length; i++){
            let partnerAge = partnerAgeList.eq(i).val();
            if (nvl(partnerAge,'') === '') {
                showMessage('', 'info', '[Visitor Information]', 'If you have a companion, please enter your companion age.', '');
                return false;
            }
        }
    }

    let sex = $('input[type=radio][name=sex]:checked').val();
    if(nvl(sex,'') === ''){
        showMessage('', 'info', '[Visitor Survey]', 'Please choose a gender.', '');
        return false;
    }

    let countryList = $('select[name=country]');
    for(let i=0; i<countryList.length; i++){
        let country = countryList.eq(i).val();
        if (nvl(country,'select') === 'select') {
            showMessage('', 'info', '[Visitor Survey]', 'Please select a country.', '');
            return false;
        }
    }

    let ageGroup = $('input[type=radio][name=ageGroup]:checked').val();
    if(nvl(ageGroup,'') === ''){
        showMessage('', 'info', '[Visitor Survey]', 'Please select an age.', '');
        return false;
    }

    let observationGbn = $('input[type=checkbox][name=observationGbn]').is(':checked');
    if (!observationGbn) {
        showMessage('', 'info', '[Visitor Survey]', 'Please check at least one classification.', '');
        return false;
    }

    let visitPurpose = $('input[type=checkbox][name=visitPurpose]').is(':checked');
    if (!visitPurpose) {
        showMessage('', 'info', '[Visitor Survey]', 'Please check at least one purpose of visit.', '');
        return false;
    }

    let interestItem = $('input[type=checkbox][name=interestItem]').is(':checked');
    if (!interestItem) {
        showMessage('', 'info', '[Visitor Survey]', 'Please check at least one area of interest.', '');
        return false;
    }

    let recognizePath = $('input[type=checkbox][name=recognizePath]').is(':checked');
    if (!recognizePath) {
        showMessage('', 'info', '[Visitor Survey]', 'Please check at least one cognitive path.', '');
        return false;
    }

    let preObservationGbn = $('input[type=checkbox][name=preObservationGbn]').is(':checked');
    if (!preObservationGbn) {
        showMessage('', 'info', '[Visitor Survey]', 'Please check at least one visit to the last exhibition.', '');
        return false;
    }

    return true;
}

function f_en_visitor_form_data_setting(){

    let joinForm = JSON.parse(JSON.stringify($('#joinForm').serializeObject()));

    joinForm.lang = 'EN';
    joinForm.joinYear = transferYear;
    joinForm.joinYn = 'Y';
    joinForm.visitorGbn = '개인';

    joinForm.domain = $('#domain').val();

    joinForm.regionSi = '';
    joinForm.regionGu = '';

    let partnerInfoArr = [];
    if(joinForm.partnerYn === 'Y'){
        //동반자 Setting
        let visitPartnerCnt = parseInt($('.visitPartnerNum:last').text());
        for(let i=0; i<visitPartnerCnt; i++){
            let visitPartnerObj = {
                seq: $('input[type=hidden][name=partnerSeq]').eq(i).val(),
                visitorSeq: $('input[type=hidden][name=visitorSeq]').val(),
                name: $('#name').val(),
                phone: $('#phone').val(),
                partnerName: $('input[name=partnerName]').eq(i).val(),
                partnerAge: $('input[name=partnerAge]').eq(i).val()
            };
            partnerInfoArr.push(visitPartnerObj);
        }
    }
    joinForm.partner = partnerInfoArr;

    // 참관 구분
    let observationGbnList = $('input[type=checkbox][name=observationGbn]:checked');
    let observationGbn = '';
    for(let i=0; i<observationGbnList.length; i++){
        observationGbn += observationGbnList.eq(i).val();
        if((i+1) !== observationGbnList.length){
            observationGbn += ',';
        }
    }
    joinForm.observationGbn = observationGbn;

    // 보트쇼 방문 목적
    let visitPurposeList = $('input[type=checkbox][name=visitPurpose]:checked');
    let visitPurpose = '';
    for(let i=0; i<visitPurposeList.length; i++){
        visitPurpose += visitPurposeList.eq(i).val();
        if((i+1) !== visitPurposeList.length){
            visitPurpose += ',';
        }
    }
    joinForm.visitPurpose = visitPurpose;

    // 관심품목
    let interestItemList = $('input[type=checkbox][name=interestItem]:checked');
    let interestItem = '';
    for(let i=0; i<interestItemList.length; i++){
        interestItem += interestItemList.eq(i).val();
        if((i+1) !== interestItemList.length){
            interestItem += ',';
        }
    }
    joinForm.interestItem = interestItem;

    // 인지경로
    let recognizePathList = $('input[type=checkbox][name=recognizePath]:checked');
    let recognizePath = '';
    for(let i=0; i<recognizePathList.length; i++){
        recognizePath += recognizePathList.eq(i).val();
        if((i+1) !== recognizePathList.length){
            recognizePath += ',';
        }
    }
    joinForm.recognizePath = recognizePath;

    // 지난 전시회 참관 여부
    let preObservationGbnList = $('input[type=checkbox][name=preObservationGbn]:checked');
    let preObservationGbn = '';
    for(let i=0; i<preObservationGbnList.length; i++){
        preObservationGbn += preObservationGbnList.eq(i).val();
        if((i+1) !== preObservationGbnList.length){
            preObservationGbn += ',';
        }
    }
    joinForm.preObservationGbn = preObservationGbn;

    return JSON.stringify(joinForm);
}

function f_ask_request(companyName, id){
    let name = $('#askName').val();
    if(nvl(name,'') === ''){
        showMessage('', 'info', '문의하기', '문의자 이름을 작성해 주세요.', '');
        return false;
    }

    let phone = $('#askPhone').val();
    if(nvl(phone,'') === ''){
        showMessage('', 'info', '문의하기', '문의자 연락처를 작성해 주세요.', '');
        return false;
    }
    if (!/^010-[0-9]{4}-[0-9]{4}$/.test(phone)) {
        showMessage('', 'info', '문의하기', '올바른 휴대전화번호를 입력해 주세요.<br>(앞자리 010 만 가능합니다.)', '');
        return false;
    }

    let content = $('#askContent').val();
    if(nvl(content,'') === ''){
        showMessage('', 'info', '문의하기', '상담요청내용을 작성해 주세요.', '');
        return false;
    }

    Swal.fire({
        title: '입력된 정보로 문의하시겠습니까?',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '문의하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            let jsonStr = {id: id, transferYear: transferYear};

            let response = ajaxConnectSimple('/exhibitor/company/selectChargeEmail.do', 'post', jsonStr);
            if (nvl(response, '') !== '') {

                let smsMsg = '[ ' + companyName + ' ]님, 온라인전시관에서 [ ' + name + ' ](' + phone + ')님이 문의를 남기셨습니다.\n' +
                    '문의내용은 다음과 같습니다.\n' +
                    '[ 문의내용 ]\n' +
                    content;

                //foreach
                let chargePersonCnt = 0;
                let smsSendResultCnt = 0;
                $.each(response, function (i, data) {

                    chargePersonCnt += 1;

                    let jsonObj = {
                        sender: '031-995-8742', //킨텍스번호
                        receiver: data.chargePersonPhone,
                        message: smsMsg
                    }

                    let resData = ajaxConnect('/sms/send.do', 'post', jsonObj);
                    smsSendResultCnt += Number.parseInt(resData.result_code);
                });

                if (chargePersonCnt === smsSendResultCnt) {

                    Swal.fire({
                        title: '문의하기',
                        html: '작성하신 내용으로 문의가 정상 접수되었습니다.<br>접수하신 연락처로 문의 답변 예정입니다.<br>감사합니다.',
                        icon: 'info',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            f_ask_popup_close();
                        }
                    });

                } else {
                    showMessage('', 'info', '문의하기', '해당 업체에 등록된 담당자 정보가 올바르지 않습니다.<br>문의사항은 사무국으로 문의해 주세요.', '');
                    return false;
                }
            } else {
                showMessage('', 'info', '문의하기', '해당 업체에 등록된 담당자 정보가 올바르지 않습니다.<br>문의사항은 사무국으로 문의해 주세요.', '');
                return false;
            }
        }
    })
}

function f_ask_popup_open(){
    $('.popExhCs').toggleClass('on');
    $('body').toggleClass('lock_scroll');
}

function f_ask_popup_close(){
    //input 초기화
    $('#askName').val('');
    $('#askPhone').val('');
    $('#askContent').val('');
    $('#smsRemain').text('90');
    
    //팝업닫기
    $('.popup').removeClass('on');
    $('body').removeClass('lock_scroll');
}

function smsByteChk(content){
    let temp_str = content.value;
    let remain = document.getElementById("smsRemain");

    remain.innerText = String(90 - getByte(temp_str));
    //남은 바이트수를 표시 하기
    if(remain.innerText < 0) {
        alert("상담요청내용은 " + 90 + " Byte 를 초과할 수 없습니다.");

        while(remain.innerText < 0) {
            temp_str = temp_str.substring(0, temp_str.length-1);
            content.value = temp_str;
            remain.innerText = String(90 - getByte(temp_str));
        }

        content.focus();
    }

}

function getByte(str){
    let resultSize = 0;
    if(str == null) {
        return 0;
    }

    for(let i=0; i<str.length; i++) {
        let c = escape(str.charAt(i));
        if(c.length === 1)//기본 아스키코드
        {
            resultSize ++;
        }
        else if(c.indexOf("%u") !== -1)//한글 혹은 기타
        {
            resultSize += 2;
        }
        else
        {
            resultSize ++;
        }
    }

    return resultSize;
}

function f_company_uploadFile_call(id, path) {

    /* 사업자 등록증 */
    let companyLicenseFile = $('#companyLicense').val();
    if (nvl(companyLicenseFile, '') !== '') {

        let fileId = ($('.companyLicenseFile_li input[type=hidden][name=companyLicenseUploadFile]').attr('id'));
        if(nvl(fileId,'') !== ''){
            f_file_n_update({ id: fileId });
        }

        let fileName = companyLicenseFile;
        let fileDot = fileName.lastIndexOf('.');
        let fileType = fileName.substring(fileDot+1, fileName.length).toLocaleLowerCase();

        if(fileType === 'pdf'){
            f_company_file_upload_pdf(id, 'exhibitor_apply_form', 'companyLicenseFile', 'exhibitor/company/' + path);
        }else{
            f_company_uploadFile(id, 'exhibitor_apply_form', 'companyLicenseFile', 'exhibitor/company/' + path);
        }
    }

    /* 로고 */
    let logoFile = $('#logo').val();
    if (nvl(logoFile, '') !== '') {
        //console.log('로고 파일 업로드');
        let fileId = ($('.logoFile_li input[type=hidden][name=logoUploadFile]').attr('id'));
        if(nvl(fileId,'') !== ''){
            f_file_n_update({ id: fileId });
        }

        f_company_uploadFile(id, 'exhibitor_apply_form', 'logoFile', 'exhibitor/company/' + path);
    }

    /* 홍보이미지 */
    let promotionImageFileList = document.getElementsByName('promotionImageFile');
    for(let i=0; i<promotionImageFileList.length; i++){
        let promotionImageFile = promotionImageFileList[i].value;
        if (nvl(promotionImageFile, '') !== '') {
            //console.log('홍보이미지 파일 업로드' + (i+1));
            let fileId = ($('.promotionImageFile_li input[type=hidden][name=promotionImageUploadFile]').eq(i).attr('id'));
            if(nvl(fileId,'') !== ''){
                f_file_n_update({ id: fileId });
            }

            f_company_uploadFile(id, 'exhibitor_apply_form', 'promotionImageFile'+(i+1), 'exhibitor/company/' + path);
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
            f_company_uploadFile(id, 'exhibitor_apply_form', 'productImageFile' + productImageNum, 'exhibitor/company/' + path);
        }
    }
}

function f_gift_file_upload_call(id, path) {

    /* 경품사진 */
    let giftPhotoFileList = document.getElementsByName('giftPhotoFile');
    let photoIdx = parseInt($('div.form_chuga_list').length);
    for(let i=0; i<giftPhotoFileList.length; i++){
        let giftPhotoFile = giftPhotoFileList[i].value;
        if (nvl(giftPhotoFile, '') !== '') {
            f_company_uploadFile(id, 'giftForm', 'giftPhotoFile' + photoIdx, 'exhibitor/company/' + path);
        }
    }

    /* 회사로고 */
    let giftCompanyLogoFileList = document.getElementsByName('giftCompanyLogoFile');
    let logoIdx = parseInt($('div.form_chuga_list').length);
    for(let i=0; i<giftCompanyLogoFileList.length; i++){
        let giftCompanyLogoFile = giftCompanyLogoFileList[i].value;
        if (nvl(giftCompanyLogoFile, '') !== '') {
            f_company_uploadFile(id, 'giftForm', 'giftCompanyLogoFile' + logoIdx, 'exhibitor/company/' + path);
        }
    }

}

function f_web_file_upload_call(id, path) {

    /* 배너 이미지 */
    let webbannerImageFile = document.getElementById('webbannerImageFile').value;
    if (nvl(webbannerImageFile, '') !== '') {
        f_company_uploadFile(id, 'webbannerImageFile', path);
    }

    /* 로고 이미지 */
    let webbannerLogoImageFile = document.getElementById('webbannerLogoImageFile').value;
    if (nvl(webbannerLogoImageFile, '') !== '') {
        //console.log('로고 파일 업로드');
        f_company_uploadFile(id, 'webbannerLogoImageFile', path);
    }

}

function f_company_uploadFile(userId, formId, elementId, path) {
    /* 파일 업로드 */
    let file = document.querySelector('#' + elementId);
    const formData = new FormData();

    const reFile = file.files[0];

    if (!reFile) {
        return;
    }

    new Compressor(reFile, {
        strict: true, //압축된 이미지의 크기가 원래 이미지보다 클 때 압축된 이미지 대신 원본 이미지를 출력
        quality: 0.4, //출력 이미지의 품질. 0~1
        convertSize: 4000000, //PNG 파일 사이즈가 4MB 이상일 경우 JPEG로 변경
        maxWidth: 1000,
        maxHeight: 700,
        success(result) {
            // The third parameter is required for server
            formData.append('uploadFile', result, result.name);

            // Send the compressed image file to server with XMLHttpRequest.
            return new Promise((resolve) => {
                fetch('/file/upload.do?gbn=' + path, {
                    method: 'post',
                    body: formData
                })
                    .then(function (response) {
                        return response.json();
                    })
                    .then(res => {
                        if (typeof res.uploadPath !== undefined) {

                            let uploadFileResponse = res.uploadPath + '\\' + res.fileName;
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
                                    resolve(res.uploadPath + '\\' + res.fileName);
                                }
                            }
                        }
                    })
            });
        },
        error(err) {
            console.log(err.message);
        },
    });
}

async function f_company_file_upload_pdf(userId, formId, elementId, path) {
    let uploadFileResponse = '';
    uploadFileResponse = await f_company_uploadFile_pdf(formId, elementId, path);
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

function f_company_uploadFile_pdf(formId, elementId, path) {
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

function f_file_remove(el, fileId){
    /* 제품사진파일 : 사진파일이 하나만 등록된 상태에서 하나 남은 파일을 삭제할 경우 상단 file input, label id -> _1 로 초기화 */
    let productImageFile_li = $(el).parent().hasClass('productImageFile_li');
    if(productImageFile_li){
        let child_length = $(el).parent('li').children('button').length;
        if(child_length === 1){
            let fileInput = $(el).parent().parent().parent().parent('li').prev('li').find('input[type=file]');

            let fileInput_text = fileInput.siblings('input[type=text]');
            let fileInput_label = fileInput.siblings('label');

            let fileInput_id = fileInput.attr('id').toString();
            let fileInput_id_init = fileInput_id.split('_')[0] + '_1';
            fileInput.attr('id', fileInput_id_init);
            fileInput_label.attr('for', fileInput_id_init);

            let fileInput_text_id_init = fileInput_id_init.replace('File','');
            fileInput_text.attr('id', fileInput_text_id_init);
        }
    }

    let jsonObj = {
        id: fileId
    }

    let resData = ajaxConnect('/file/upload/update.do', 'post', jsonObj);
    if(resData.resultCode === "0"){
        $(el).parent().remove();
    }
}

function f_file_n_update(json){
    $.ajax({
        url: '/file/useYn/update.do',
        method: 'post',
        data: JSON.stringify(json),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    }).fail(function (xhr, status, errorThrown) {
        alert('파일 정보 업데이트 오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
    })
}

function f_main_exhibitor_preview_search(e){
    let searchText = $('#searchText').val();
    if(nvl(searchText,'') !== ''){
        if(e.keyCode === 13){
            let condition = $('#condition').val();
            if(condition === 'PRODUCT'){
                let jsonObj = {
                    "pageNum": 1,
                    "rows": 12,
                    "transferYear": transferYear,
                    "productNameKo": searchText
                };

                let form = document.createElement('form');
                form.setAttribute('method', 'POST'); //POST 메서드 적용
                form.setAttribute('action', '/online/product.do');

                let keys = Object.keys(jsonObj); //키를 가져옵니다. 이때, keys 는 반복가능한 객체가 됩니다.
                for (let i=0; i<keys.length; i++) {
                    let key = keys[i];
                    let hiddenField = document.createElement('input');
                    hiddenField.setAttribute('type', 'hidden'); //값 입력
                    hiddenField.setAttribute('name', key);
                    hiddenField.setAttribute('value', jsonObj[key]);
                    form.appendChild(hiddenField);
                }

                document.body.appendChild(form);
                form.submit();

            }else{

                let jsonObj = {
                    "pageNum": 1,
                    "rows": 12,
                    "transferYear": transferYear,
                    "companyNameKo": searchText
                };

                let form = document.createElement('form');
                form.setAttribute('method', 'POST'); //POST 메서드 적용
                form.setAttribute('action', '/online/company.do');

                let keys = Object.keys(jsonObj); //키를 가져옵니다. 이때, keys 는 반복가능한 객체가 됩니다.
                for (let i=0; i<keys.length; i++) {
                    let key = keys[i];
                    let hiddenField = document.createElement('input');
                    hiddenField.setAttribute('type', 'hidden'); //값 입력
                    hiddenField.setAttribute('name', key);
                    hiddenField.setAttribute('value', jsonObj[key]);
                    form.appendChild(hiddenField);
                }

                document.body.appendChild(form);
                form.submit();

            }

        }
    }
}

function makeJsonFormat(data){
    let returnJsonObj;
    let receiverArr = [];

    $.each(data , function(i){
        let receiverObj = {
            "email": data[i].chargePersonEmail //받는이 메일주소
        }
        receiverArr.push(receiverObj);
    });

    returnJsonObj = {
        "subject": '[2025 경기국제보트쇼] 참가업체 접수 완료', //제목
        "body": "", //본문
        "template": "6", //템플릿 번호
        "receiver": receiverArr
    }

    return returnJsonObj;
}

/**
 * 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다.
 * @param str			: 체크할 문자열
 * @param defaultStr	: string 비어있을경우 리턴할 기본 문자열
 */
function nvl(str, defaultStr){

    if(str === "" || str === null || str === "null" || str === undefined || typeof str === "undefined" || (typeof str === "object" && !Object.keys(str).length) ){
        str = defaultStr ;
    }

    return str ;
}

function f_page_move(url, param){
    let form = document.createElement('form');
    form.setAttribute('method', 'post'); //POST 메서드 적용
    form.setAttribute('action', url);

    let paramJson ;
    if(url === '/mypage/index.do'){
        paramJson = param;
    }else{
        paramJson = {
            seq : param
        }
    }
    let keys = Object.keys(paramJson); //키를 가져옵니다. 이때, keys 는 반복가능한 객체가 됩니다.
    for (let i=0; i<keys.length; i++) {
        let key = keys[i];
        let hiddenField = document.createElement('input');
        hiddenField.setAttribute('type', 'hidden'); //값 입력
        hiddenField.setAttribute('name', key);
        hiddenField.setAttribute('value', paramJson[key]);
        form.appendChild(hiddenField);
    }
    document.body.appendChild(form);
    form.submit();
}

function youtubeId(url) {
    let tag = '';
    if(nvl(url,'') !== '')  {
        let regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        let matchs = url.match(regExp);
        if (matchs) {
            tag = matchs[7];
        }
    }else{
        tag = url;
    }
    return tag;
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