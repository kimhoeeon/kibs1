var transferYear = '2027';
// main.js 최상단에 전역 변수 선언
let deletedFileIds = [];
let isSubmitProceeding = false;
$(function() {

    if (!window.location.href.includes('localhost')) {
        if (window.location.protocol !== "https:") {
            window.location.href = "https:" + window.location.href.substring(window.location.protocol.length);
        }

        if (document.location.protocol === "http:") {
            document.location.href = document.location.href.replace('http:', 'https:');
        }
    }

})

function f_lang_get(){
    let lang = 'K';
    if(window.location.href.includes('eng')){
        lang = 'E';
    }
    return lang;
}

function home(lang){
    let url = '/main.do';
    if(lang === 'en'){
        url = '/eng/index.do';
    }
    window.location.href = url;
}

function maskingEmail(email) {
    const mask = "*".repeat(email.split("@")[0].length - 1);
    return email[0] + mask + email.slice(mask.length + 1, email.length);
}

var certifyCode = '000000';
let countdown; // 카운트다운을 관리하는 변수
function certificateEmail(el){
    let email = $(el).prop('id');
    //console.log(email);
    certifyCode = generateRandomCode(5);
    //console.log(certifyCode);
    let jsonObj = {
        subject: '[ 2027 경기국제보트쇼 ] 참가기업 인증 안내', //제목
        body: "", //본문
        template: "160", //템플릿 번호
        receiver: [{ email: email , note1: certifyCode }]
    }
    let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
    if (resData.resultCode === "0") {
        $(el).prop('disabled', true);

        $('#certifyBox').show();

        showMessage('', 'info', '[ 참가기업 인증 ]', '선택하신 이메일 주소로<br>인증번호가 발송되었습니다.', '');

        let time = 60*5;
        const certifyTimer = function(){
            if (time > 0) { // >= 0 으로하면 -1까지 출력된다.
                time = time - 1; // 여기서 빼줘야 5분에서 5분 또 출력되지 않고, 바로 2분 59초로 넘어간다.
                let min = String(Math.floor(time / 60)).padStart(2, '0');
                let sec = String(time % 60).padStart(2, '0');
                $('#remainMin').text(min);
                $('#remainSec').text(sec);
                // time = time - 1
            } else {
                // 시간 오버됐을 경우
                $('#certifyCloseBtn').trigger('click');
            }
        }

        clearInterval(countdown);
        countdown = setInterval(certifyTimer, 1000);
    }else{
        showMessage('', 'error', '[ 참가기업 인증 ]', '인증번호 메일 전송에 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
    }
}

function f_certify_confirm(){
    if(certifyCode === $('#certifyNum').val()){
        if($('#certifyFlag').val() === 'false') {
            showMessage('', 'info', '[ 참가기업 인증 ]', '인증되었습니다.<br>[ 불러오기 ] 버튼을 눌러 진행해 주세요.', '');

            $('#certifyNum').attr('readonly', true);
            $('#certifyBtn').attr('style','cursor: not-allowed;');
            $('#certifyFlag').val('true');
            $('#remainMin').text('인증').attr('style','color: #0DA6C0');
            $('#colon').text('');
            $('#remainSec').text('완료').attr('style','color: #0DA6C0');

            clearInterval(countdown);
        }
    }else{
        showMessage('', 'error', '[ 참가기업 인증 ]', '[ 인증 실패 ] 인증번호가 다릅니다.', '');
    }
}

function f_certify_close(){
    let emailList_form = $('#popFormInfoLoad .list');
    emailList_form.empty();
    $('#certifyBox').hide();
    $('#certifyNum').val('').attr('readonly', false);
    $('#certifyBtn').attr('style','cursor: pointer;');
    $('#certifyFlag').val('false');
    $('#remainMin').text('05').attr('style','color: #AB0000');
    $('#remainSec').text('00').attr('style','color: #AB0000');
    $('#colon').text(':');

    clearInterval(countdown);
    certifyCode = '000000';
}

function f_pre_exhibitor_info_call(){
    let id = $('#id').val();
    let certifyFlag = $('#certifyFlag').val();
    if(certifyFlag === 'true'){
        //console.log(certifyFlag, id);
        let preExhibitorInfo = ajaxConnect('/apply/exhibitor/pre/selectSingle.do', 'post', {id: id});

        let transferYear = preExhibitorInfo.transferYear;
        Swal.fire({
            icon: 'info',
            title: '[ 참가신청 정보 불러오기 ]',
            html: '<span style="font-size: 1.2em;">' + transferYear + ' 년도 신청정보를 조회하였습니다.<br>입력되지 않은 정보는 직접 입력해 주세요.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                //회사명(국)
                let companyNameKo = preExhibitorInfo.companyNameKo;
                if(nvl(companyNameKo,'') !== ''){
                    $('#companyNameKo').val(companyNameKo);
                }
                //회사명(영)
                let companyNameEn = preExhibitorInfo.companyNameEn;
                if(nvl(companyNameEn,'') !== ''){
                    $('#companyNameEn').val(companyNameEn);
                }
                //본사주소
                let companyAddress = preExhibitorInfo.companyAddress;
                if(nvl(companyAddress,'') !== ''){
                    $('#companyAddress').val(companyAddress);
                }
                //본사상세주소
                let companyAddressDetail = preExhibitorInfo.companyAddressDetail;
                if(nvl(companyAddressDetail,'') !== ''){
                    $('#companyAddressDetail').val(companyAddressDetail);
                }
                //공장주소
                let factoryAddress = preExhibitorInfo.factoryAddress;
                if(nvl(factoryAddress,'') !== ''){
                    $('#factoryAddress').val(factoryAddress);
                }
                //공장상세주소
                let factoryAddressDetail = preExhibitorInfo.factoryAddressDetail;
                if(nvl(factoryAddressDetail,'') !== ''){
                    $('#factoryAddressDetail').val(factoryAddressDetail);
                }
                //대표자
                let companyCeo = preExhibitorInfo.companyCeo;
                if(nvl(companyCeo,'') !== ''){
                    $('#companyCeo').val(companyCeo);
                }
                //전화
                let companyTel = preExhibitorInfo.companyTel;
                if(nvl(companyTel,'') !== ''){
                    $('#companyTel').val(companyTel);
                }
                //홈페이지
                let companyHomepage = preExhibitorInfo.companyHomepage;
                if(nvl(companyHomepage,'') !== ''){
                    $('#companyHomepage').val(companyHomepage);
                    $('input[type=checkbox][name=noPage]').prop('checked', false);
                }
                //Fax
                let companyFax = preExhibitorInfo.companyFax;
                if(nvl(companyFax,'') !== ''){
                    $('#companyFax').val(companyFax);
                }
                //기참가연도
                let prePartYear = preExhibitorInfo.prePartYear;
                if(nvl(prePartYear,'') !== ''){
                    let yearArr = prePartYear.toString().split('\^');
                    for(let i=0; i<yearArr.length; i++){
                        $('input[type=checkbox][name=prePartYear][value="' + yearArr[i] + '"]')
                            .prop('checked', true)
                            .trigger('change');
                    }
                }
                //한국해양레저산업협회 회원사 여부
                let memberCompanyYn = preExhibitorInfo.memberCompanyYn;
                if(nvl(memberCompanyYn,'') !== ''){
                    $('input[type=radio][name=memberCompanyYn][value="' + memberCompanyYn + '"]').prop('checked', true);
                }

                /*대표담당자*/
                //성명
                let name = preExhibitorInfo.name;
                if(nvl(name,'') !== ''){
                    $('#name').val(name);
                }
                //직위
                let position = preExhibitorInfo.position;
                if(nvl(position,'') !== ''){
                    $('#position').val(position);
                }
                //부서
                let depart = preExhibitorInfo.depart;
                if(nvl(depart,'') !== ''){
                    $('#depart').val(depart);
                }
                //전화번호
                let tel = preExhibitorInfo.tel;
                if(nvl(tel,'') !== ''){
                    $('#tel').val(tel);
                }
                //휴대전화
                let phone = preExhibitorInfo.phone;
                if(nvl(phone,'') !== ''){
                    $('#phone').val(phone);
                }
                //이메일
                let fullEmail = preExhibitorInfo.email;
                if(nvl(fullEmail,'') !== ''){
                    let domainArr = ['naver.com','daum.net','nate.com','hanmail.net','gmail.com'];
                    let emailArr = fullEmail.toString().split('@');
                    let email = emailArr[0];
                    $('#email1').val(email);

                    let domain = emailArr[1];
                    if(domainArr.some(i => domain.includes(i))){
                        $('#email_select').val(domain).prop('selected',true).trigger('change');
                    }else{
                        $('#email_select').val("직접입력").prop('selected',true).trigger('change');
                        $('#email2').val(domain);
                    }
                }
                //회사소개영상
                let companyIntroVideo = preExhibitorInfo.companyIntroVideo;
                if(nvl(companyIntroVideo,'') !== ''){
                    $('#companyIntroVideo').val(companyIntroVideo);
                }
                //회사소개(국)
                let companyIntroKo = preExhibitorInfo.companyIntroKo;
                if(nvl(companyIntroKo,'') !== ''){
                    $('#companyIntroKo').val(companyIntroKo);
                }
                //회사소개(영)
                let companyIntroEn = preExhibitorInfo.companyIntroEn;
                if(nvl(companyIntroEn,'') !== ''){
                    $('#companyIntroEn').val(companyIntroEn);
                }
                //KIBS 참가목적
                let companyPurposeKo = preExhibitorInfo.companyPurposeKo;
                if(nvl(companyPurposeKo,'') !== ''){
                    $('#companyPurposeKo').val(companyPurposeKo).prop('selected', true).trigger('change');
                }
                //신제품출품 사항 소개(국)
                let newItemIntroKo = preExhibitorInfo.newItemIntroKo;
                if(nvl(newItemIntroKo,'') !== ''){
                    $('#newItemIntroKo').val(newItemIntroKo);
                }
                //신제품출품 사항 소개(영)
                let newItemIntroEn = preExhibitorInfo.newItemIntroEn;
                if(nvl(newItemIntroEn,'') !== ''){
                    $('#newItemIntroEn').val(newItemIntroEn);
                }
                //프로모션 정보
                let eventPlan = preExhibitorInfo.eventPlan;
                if(nvl(eventPlan,'') !== ''){
                    $('#promotionPlan').val(eventPlan);
                }
                //참가분야
                let fieldPart = preExhibitorInfo.fieldPart;
                if(nvl(fieldPart,'') !== ''){
                    let partArr = fieldPart.toString().split('\^');
                    for(let i=0; i<partArr.length; i++){
                        $('input[type=checkbox][name=fieldPart][value="' + partArr[i] + '"]')
                            .prop('checked', true)
                            .trigger('change');
                    }
                }

                //팝업닫기
                $('#certifyCloseBtn').trigger('click');
            }
        })

    }else{
        showMessage('', 'error', '[ 참가기업 인증 ]', '메일로 발송된<br>인증번호를 입력해 주세요.', '');
    }
}

function f_pre_exhibitor_info_call_init(){
    window.location.reload();
}

function emailCheck(email_address){
    let email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
    return email_regex.test(email_address);
}

function generateRandomCode(n) {
    let str = ''
    for (let i = 0; i < n; i++) {
        str += Math.floor(Math.random() * 10)
    }
    return str
}

function f_show_main_popup(param){
    let popupObj = JSON.parse(JSON.stringify(param));
    let id = popupObj.id;
    let lang = popupObj.lang;
    let widthPixel = popupObj.widthPixel;
    let heightPixel = 600;
    let leftPixel = popupObj.leftPixel;
    let topPixel = popupObj.topPixel;
    let align = popupObj.align;

    let features = 'toolbar=no, menubar=no, status=no, location=no, resizable=no, directories=no,';
    features += 'width=' + widthPixel + ', height=' + heightPixel + ', top=' + topPixel;
    if(align === 'B_LEFT'){
        features += ', left=' + leftPixel;
    }else{
        features += ', left=' + (window.innerWidth - leftPixel);
    }

    //console.log(features);
    let cookie_name = 'popup_' + id;
    if( getStorage(cookie_name)){
        return;
    }

    window.open('', 'id_popup_' + id, features);

    let form = document.createElement("form");
    form.setAttribute("charset", "UTF-8");
    form.setAttribute("method", "POST");
    form.setAttribute("target", 'id_popup_'+ id);
    form.setAttribute("action", "/popup.do");

    let hiddenField_id = document.createElement("input");
    hiddenField_id.setAttribute("type", "hidden");
    hiddenField_id.setAttribute("name", "id");
    hiddenField_id.setAttribute("id", "id");
    hiddenField_id.setAttribute("value", id);
    form.appendChild(hiddenField_id);

    let hiddenField_lang = document.createElement("input");
    hiddenField_lang.setAttribute("type", "hidden");
    hiddenField_lang.setAttribute("name", "lang");
    hiddenField_lang.setAttribute("id", "lang");
    hiddenField_lang.setAttribute("value", lang);
    form.appendChild(hiddenField_lang);

    document.body.appendChild(form);
    form.submit();
}

function closeMainPopup(pop_id) {
    let id_today_name	= 'id_today_' + pop_id;
    let cookie_name		= 'popup_' + pop_id;
    if( $(":input:checkbox[id='" + id_today_name + "']:checked").size() > 0 ){
        setStorage(cookie_name, 1);
    }

    window.close();
}

function setStorage(name, exp){
    // 만료 시간 구하기(exp를 ms단위로 변경)
    let date = new Date();
    date = date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);

    // 로칼 스토리지에 저장하기
    // (값을 따로 저장하지 않고 만료 시간을 저장)
    localStorage.setItem(name, date);
}

function getStorage(name){
    let now = new Date();
    now = now.setTime(now.getTime());
    // 현재 시각과 스토리지에 저장된 시각을 각각 비교하여
    // 시간이 남아 있으면 true, 아니면 false 리턴
    return parseInt(localStorage.getItem(name)) > now
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

            alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
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

        alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
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
        html: '<span style="font-size: 1.2em;">' + msg + '</span>',
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

function f_file_download(path, fileName){
    // 1. 다운로드 전용 숨김 iframe 생성 (없으면 생성)
    if ($('#download_iframe').length === 0) {
        $('body').append('<iframe id="download_iframe" name="download_iframe" style="display:none;"></iframe>');
    }

    let form = document.createElement('form');
    form.setAttribute('method', 'POST'); //POST 메서드 적용
    form.setAttribute('action', '/file/download.do');

    // [핵심] 폼 전송 대상을 현재 창이 아닌 iframe으로 지정하여 페이지 이탈 감지 회피
    form.setAttribute('target', 'download_iframe');

    let hiddenField_path = document.createElement('input');
    hiddenField_path.setAttribute('type', 'hidden'); //값 입력
    hiddenField_path.setAttribute('name', 'path');
    hiddenField_path.setAttribute('value', path);
    form.appendChild(hiddenField_path);

    let hiddenField_fileName = document.createElement('input');
    hiddenField_fileName.setAttribute('type', 'hidden'); //값 입력
    hiddenField_fileName.setAttribute('name', 'fileName');
    hiddenField_fileName.setAttribute('value', fileName);
    form.appendChild(hiddenField_fileName);

    document.body.appendChild(form);
    form.submit();

    // 전송 후 폼 제거 (깔끔한 DOM 유지를 위해)
    setTimeout(function(){
        document.body.removeChild(form);
    }, 100);
}

function minCnt(el, cnt){
    let val = $(el).val() || 0;
    if(val < cnt){
        if(val !== 0){
            alert('독립부스는 2부스부터 신청 가능합니다.');
            $(el).val(0);
            calculateTotal('booth');
        }
    }
}

function checkBooth(){
    let standAloneBoothCnt = $.number((parseInt($('#standAloneBoothCnt').val() || 0)));
    let assemblyBoothCnt = $.number((parseInt($('#assemblyBoothCnt').val() || 0)));
    //let onlineBoothCnt = $.number((parseInt($('#onlineBoothCnt option:selected').val() || 0)));
    if((standAloneBoothCnt > 0 || assemblyBoothCnt > 0)/* && onlineBoothCnt > 0*/){
        //alert('조립부스 또는 독립부스 신청 시, 온라인 부스는 무료 지원됩니다.');
        //$('#onlineBoothCnt').val(0);
        //$('#onlineBoothCnt option').eq(0).prop('selected',true);
        calculateTotal('booth');
    }
}

/***************************************************************************************
 * 할인 조건 Function Start
 * *************************************************************************************/
// --- 전역 상수 설정 ---

const boothPrices = {
    standAlone: 1800000,
    assembly: 2100000,
    online: 1000000
};
const registrationFee = 100000; // 기본 등록비

// 🗑️ 삭제: 새로운 할인 로직에서는 더 이상 사용되지 않는 상수들입니다.
// const discount3BaseAmount = 500000;
// const discount3ChangedAmount = 300000;
// const singleChoiceDiscounts = [ ... ];

// --- 조기신청 할인 기간 설정 ---

// 현재 시간 (페이지 로드 시점 기준)
const now = new Date();

// 1차 조기신청 마감일
const discount1Deadline = new Date('2025-11-14T23:59:59');

// 2차 조기신청 기간
const discount2StartDate = new Date('2025-11-15T00:00:00');
const discount2Deadline = new Date('2025-12-12T23:59:59');

/**
 * 오프라인 부스 수량에 따라 규모 할인을 자동으로 선택하고 비활성화하는 함수
 */
function updateScaleDiscountState(physicalBooths) {
    const scaleCheckboxes = $('.single-choice-discount input[type="checkbox"]');

    // 1. 모든 규모 할인 체크박스를 비활성화하여 사용자가 직접 수정하지 못하게 합니다.
    //scaleCheckboxes.prop('disabled', true);

    // 2. 모든 규모 할인 체크를 초기화합니다.
    scaleCheckboxes.prop('checked', false);

    // 3. 부스 수량에 맞는 할인 항목을 찾아 자동으로 체크합니다.
    let targetCheckbox = null;
    if (physicalBooths >= 100) {
        targetCheckbox = $('#discountScale6');
    } else if (physicalBooths >= 50) {
        targetCheckbox = $('#discountScale5');
    } else if (physicalBooths >= 40) {
        targetCheckbox = $('#discountScale4');
    } else if (physicalBooths >= 30) {
        targetCheckbox = $('#discountScale3');
    } else if (physicalBooths >= 20) {
        targetCheckbox = $('#discountScale2');
    } else if (physicalBooths >= 10) {
        targetCheckbox = $('#discountScale1');
    }

    if (targetCheckbox) {
        targetCheckbox.prop('checked', true);
    }
}

/**
 * 1차 조기신청 할인 체크박스의 상태와 이벤트를 제어합니다.
 * 기간에 따라 자동으로 체크하거나 비활성화합니다.
 */
function handleDiscountEarly1() {
    const discount1Checkbox = $('#discountEarly1');
    const discount1Item = $('#discountItem1');
    const currentPrcTotal = $('#prcTotal').val(); // uncomma 함수로 "￦ 110,000" -> 110000 변환
    const isInitialState = uncomma(currentPrcTotal) === 110000; // 최초 신청 상태(등록비)인지 숫자값으로 비교

    if (now <= discount1Deadline) { // 1차 할인 기간 내
        // DB에 저장된 값이 없는 최초 신청 시 자동으로 체크
        if (isInitialState) {
            discount1Checkbox.prop('checked', true);
        }
        discount1Item.removeClass('disabled');
        discount1Checkbox.prop('disabled', false);

        // 사용자가 직접 할인을 해제하고 다시 선택하지 못하게 하는 로직
        let userUnchecked = false;
        discount1Checkbox.off('change.discountControl').on('change.discountControl', async function() {
            if (!$(this).prop('checked')) {
                if(confirm('1차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다. 해제하시겠습니까?')){
                    userUnchecked = true;
                } else {
                    $(this).prop('checked', true);
                }
            } else if (userUnchecked) {
                $(this).prop('checked', false);
                alert('1차 조기신청 할인은 한 번 해제하시면 다시 선택할 수 없습니다.');
            }
            // ★ 수정: main.js의 calculateTotal 함수 호출 (API 방식)
            if (typeof calculateTotal === 'function') {
                await calculateTotal('booth'); // API 호출이므로 await
            }
        });
    } else { // 1차 할인 기간 종료
        if (discount1Checkbox.prop('checked')) {
            discount1Checkbox.prop('checked', false);
        }
        discount1Checkbox.prop('disabled', true);
        discount1Item.addClass('disabled');
        discount1Item.off('click.preventCheck').on('click.preventCheck', () => {
            alert('1차 조기신청 할인은 기간이 종료되어 선택할 수 없습니다.');
        });
    }
}

/**
 * 2차 조기신청 할인 체크박스의 상태와 이벤트를 제어합니다.
 * 기간에 따라 자동으로 체크하거나 비활성화합니다.
 */
function handleDiscountEarly2() {
    const discount2Checkbox = $('#discountEarly2');
    const discount2Item = $('#discountItem2');
    const currentPrcTotal = $('#prcTotal').val(); // uncomma 함수로 "￦ 110,000" -> 110000 변환
    const isInitialState = uncomma(currentPrcTotal) === 110000; // 최초 신청 상태(등록비)인지 숫자값으로 비교

    if (now >= discount2StartDate && now <= discount2Deadline) { // 2차 할인 기간 내
        // 1차 할인 기간을 놓친 최초 신청자에게 자동 체크
        if(isInitialState) {
            discount2Checkbox.prop('checked', true);
        }
        discount2Item.removeClass('disabled');
        discount2Checkbox.prop('disabled', false);

        let userUnchecked = false;
        discount2Checkbox.off('change.discountControl').on('change.discountControl', async function() {
            if (!$(this).prop('checked')) {
                if(confirm('2차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다. 해제하시겠습니까?')){
                    userUnchecked = true;
                } else {
                    $(this).prop('checked', true);
                }
            } else if (userUnchecked) {
                $(this).prop('checked', false);
                alert('2차 조기신청 할인은 한 번 해제하시면 다시 선택할 수 없습니다.');
            }
            // ★ 수정: main.js의 calculateTotal 함수 호출 (API 방식)
            if (typeof calculateTotal === 'function') {
                await calculateTotal('booth'); // API 호출이므로 await
            }
        });
    } else { // 2차 할인 기간이 아니면
        if (discount2Checkbox.prop('checked')) {
            discount2Checkbox.prop('checked', false);
        }
        discount2Checkbox.prop('disabled', true);
        discount2Item.addClass('disabled');
        discount2Item.off('click.preventCheck').on('click.preventCheck', () => {
            alert('2차 조기신청 할인은 현재 신청 기간이 아닙니다.');
        });
    }
}

/**
 * 첫 참가 할인 선택 여부에 따라 재참가 할인 체크박스를 활성/비활성화 하는 함수
 */
function updateReParticipantDiscountState() {
    const isFirstTimerChecked = $('#discountFirstUnder10').is(':checked') || $('#discountFirstOver10').is(':checked');
    const reParticipantCheckbox = $('#discountRe');

    if (isFirstTimerChecked) {
        // 첫 참가 할인이 선택된 경우, 재참가 할인을 비활성화하고 체크 해제
        reParticipantCheckbox.prop('checked', false);
        reParticipantCheckbox.prop('disabled', true);
    } else {
        // 첫 참가 할인이 선택되지 않은 경우, 재참가 할인을 활성화
        reParticipantCheckbox.prop('disabled', false);
    }
}

/**
 * 참가 신청 관련 총액을 다시 계산하는 함수 (백엔드 API 호출 방식으로 수정됨)
 * 부스비, 할인액 등을 종합하여 최종 금액을 산출하고,
 * 호출된 페이지 타입('booth' 또는 'utility')에 따라 다른 ID에 값을 표시합니다.
 */
async function calculateTotal(pageType) { // async 키워드 추가, pageType 파라미터 추가

    // 1. 계산에 필요한 모든 입력값을 DOM에서 수집
    const inputData = {
        // 부스 정보 (hidden input 또는 화면 input에서 가져오기)
        registrationCnt: 1,
        standAloneBoothCnt: parseInt($('#standAloneBoothCnt').val()) || parseInt($('#hiddenStandAloneCnt').val()) || 0,
        assemblyBoothCnt: parseInt($('#assemblyBoothCnt').val()) || parseInt($('#hiddenAssemblyCnt').val()) || 0,
        //onlineBoothCnt: parseInt($('#onlineBoothCnt').val()) || parseInt($('#hiddenOnlineCnt').val()) || 0,

        // 유틸리티 정보 (hidden input 또는 화면 input에서 계산)
        utilityPrcSum: (pageType === 'utility') ? calculateCurrentUtilitySum() : (parseInt($('#utilityPrcSum').val()) || 0),

        // 기본 할인 정보 (hidden input 또는 화면 체크박스에서 가져오기)
        discountEarly1: $('#discountEarly1').is(':checked') || $('#discountEarly1Checked').val() === 'true',
        discountEarly2: $('#discountEarly2').is(':checked') || $('#discountEarly2Checked').val() === 'true',
        discountFirstUnder10: $('#discountFirstUnder10').is(':checked') || $('#discountFirstUnder10Checked').val() === 'true',
        discountFirstOver10: $('#discountFirstOver10').is(':checked') || $('#discountFirstOver10Checked').val() === 'true',
        discountRe: $('#discountRe').is(':checked') || $('#discountReChecked').val() === 'true',
        discountScale1: $('#discountScale1').is(':checked') || $('#discountScale1Checked').val() === 'true',
        discountScale2: $('#discountScale2').is(':checked') || $('#discountScale2Checked').val() === 'true',
        discountScale3: $('#discountScale3').is(':checked') || $('#discountScale3Checked').val() === 'true',
        discountScale4: $('#discountScale4').is(':checked') || $('#discountScale4Checked').val() === 'true',
        discountScale5: $('#discountScale5').is(':checked') || $('#discountScale5Checked').val() === 'true',
        discountScale6: $('#discountScale6').is(':checked') || $('#discountScale6Checked').val() === 'true',
        discountLeisure: $('#discountLeisure').is(':checked') || $('#discountLeisureChecked').val() === 'true',

        // 특별 할인 정보 (hidden input 등에서 가져오기)
        discountSpecial1Yn: $('#discountSpecial1Yn').val() === 'true',
        discountSpecial2Yn: $('#discountSpecial2Yn').val() === 'true',
        discountSpecial2Amount: parseInt($('#discountSpecial2Amount').val()) || 0,
        discountSpecial3Yn: $('#discountSpecial3Yn').val() === 'true',
        discountSpecial3Amount: parseInt($('#discountSpecial3Amount').val()) || 0,

        // 발전 기금용 정보
        memberCompanyYn: $("#memberCompanyYn").val(), // JSP hidden input 값

        // 선금
        deposit: parseInt(uncomma($('#depositText').text())) || parseInt($('#deposit').val()) || 0 // JSP span 또는 hidden input 값
    };

    // --- 부스 페이지 관련 UI 업데이트 (부스 페이지에서만 실행) ---
    if (pageType === 'booth') {
        let standAloneQty = parseInt($('#standAloneBoothCnt').val()) || 0;
        let assemblyQty = parseInt($('#assemblyBoothCnt').val()) || 0;
        //let onlineQty = parseInt($('#onlineBoothCnt').val()) || 0;
        let standAloneFee = standAloneQty * boothPrices.standAlone;
        let assemblyFee = assemblyQty * boothPrices.assembly;
        //let onlineFee = onlineQty * boothPrices.online;

        $('#standAloneBoothFee').val(numberToWon(standAloneFee));
        $('#assemblyBoothFee').val(numberToWon(assemblyFee));
        //$('#onlineBoothFee').val(numberToWon(onlineFee));

        // 오프라인 부스 총 수량 계산
        let physicalBooths = standAloneQty + assemblyQty;
        // 부스 수량에 따라 규모 할인 자동 업데이트 (main.js 함수)
        updateScaleDiscountState(physicalBooths);
        // 첫 참가 할인 자동 업데이트 (main.js 함수 - firstUnder10 요소에 data-db-val 확인 필요)
        const firstUnder10 = $('#discountFirstUnder10');
        if (firstUnder10.data('db-val') === 'first') {
            const firstOver10 = $('#discountFirstOver10');
            if (physicalBooths > 0 && physicalBooths < 10) {
                firstUnder10.prop('checked', true); firstOver10.prop('checked', false);
            } else if (physicalBooths >= 10) {
                firstUnder10.prop('checked', false); firstOver10.prop('checked', true);
            } else {
                firstUnder10.prop('checked', false); firstOver10.prop('checked', false);
            }
            // inputData 업데이트
            inputData.discountFirstUnder10 = firstUnder10.is(':checked');
            inputData.discountFirstOver10 = firstOver10.is(':checked');
        }
        // 재참가 할인 상태 업데이트 (main.js 함수) 및 inputData 반영
        updateReParticipantDiscountState();
        inputData.discountRe = $('#discountRe').is(':checked');
        // 규모 할인 상태 inputData 반영
        inputData.discountScale1 = $('#discountScale1').is(':checked');
        inputData.discountScale2 = $('#discountScale2').is(':checked');
        inputData.discountScale3 = $('#discountScale3').is(':checked');
        inputData.discountScale4 = $('#discountScale4').is(':checked');
        inputData.discountScale5 = $('#discountScale5').is(':checked');
        inputData.discountScale6 = $('#discountScale6').is(':checked');
    }
    // --- ▲▲▲ 부스 페이지 UI 업데이트 종료 ▲▲▲ ---

    // 2. 백엔드 미리보기 API 호출 (비동기)
    try {
        // 사용자용 API 엔드포인트 호출
        const response = await fetch('/calculate-preview.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(inputData)
        });

        if (!response.ok) {
            throw new Error('계산 서버 오류: ' + response.statusText);
        }

        // CalculationResultDTO 객체 받기
        const result = await response.json();

        // 3. 계산된 결과를 페이지 타입에 맞게 UI에 반영
        if (pageType === 'booth') {
            // --- 부스 페이지 UI 업데이트 ---
            $('#boothPrcSum').text($.number(result.boothPrcSum));
            $('#discountPrcSum').text($.number(result.basicDiscountSum));
            $('#specialDiscountTotal_display').text($.number(result.specialDiscountTotal)); // ※ JSP span 필요
            $('#developmentFund').text(numberToWon(result.developmentFund)); // ※ JSP span 필요

            // --- 올해의 제품상 할인 ---
            let discountSpecialFee = (result.boothPrcSum - result.basicDiscountSum) * 0.5;
            $('#discountSpecial1').data('discount', discountSpecialFee);
            $('#discountSpecial1Note').text(discountSpecialFee.toLocaleString() + '원');

            // --- 부스 페이지 UI 업데이트 ---
            $('#boothPrcSumDisplay').val(numberToWon(result.boothSubtotal)); // 유틸리티 소계 ※ JSP span 필요

            // ★ 부스 총액 (VAT 포함) 표시 ★
            $('#boothTotalAmount').val(numberToWon(result.boothTotal)); // ※ JSP span 필요

            // --- 참고용 최종 총계 표시 ---
            $('#prcSum').text($.number(result.prcSum));
            $('#prcVat').text($.number(result.prcVat));
            $('#prcTotal').text($.number(result.prcTotal));
            $('#balance').text($.number(result.balance));

        } else if (pageType === 'utility') {
            // --- 유틸리티 페이지 UI 업데이트 ---
            $('#utilityPrcSumDisplay').val(numberToWon(result.utilityPrcSum)); // 유틸리티 소계 ※ JSP span 필요

            // ★ 유틸리티 총액 (VAT 포함) 표시 ★
            $('#utilityTotalAmount').val(numberToWon(result.utilityTotal)); // ※ JSP span 필요

            // --- 참고용 최종 총계 표시 ---
            $('#prcSum').text($.number(result.prcSum));
            $('#prcVat').text($.number(result.prcVat));
            $('#prcTotal').text($.number(result.prcTotal));
            $('#balance').text($.number(result.balance));
        }

        // 4. 서버 전송용 hidden input 값 업데이트 (공통)
        // (저장 시에는 항상 최종 총계 기준 값을 보냅니다)
        $("input[name='boothPrcSum']").val(result.boothPrcSum);
        $("input[name='discountPrcSum']").val(result.basicDiscountSum);
        // 유틸리티 페이지가 아니어도 utilitySubtotal 값을 hidden에 넣어줘야 할 수 있음 (저장 로직 확인 필요)
        $("input[name='utilityPrcSum']").val(result.utilitySubtotal);
        $("input[name='prcSum']").val(result.prcSum);
        $("input[name='prcVat']").val(result.prcVat);
        $("input[name='prcTotal']").val(result.prcTotal);
        // $("input[name='developmentFund']").val(result.developmentFund); // DB 컬럼이 있다면

    } catch (error) {
        console.error("금액 계산 중 오류 발생:", error);
        alert("금액을 계산하는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        // 에러 시 금액 표시 초기화 (선택 사항)
        $('#boothTotalAmount, #utilityTotalAmount, #prcSum, #prcVat, #prcTotal, #balance').text('-');
    }
}

/***************************************************************************************
 * 할인 조건 Function End
 * *************************************************************************************/

/**
 * 콤마 제거 유틸리티 함수 (main.js에도 있어야 함)
 */
function uncomma(str) {
    if (typeof str === 'number') {
        return str;
    }
    str = String(str);
    // "￦ " 기호와 "," 콤마를 모두 제거
    return parseInt(str.replace(/￦\s*|,/g, ''), 10) || 0;
}

function autoSum(index){
    let boothCost = $('.booth_cost');
    let sum = $('.num_sum');
    let cost = wonToNumber(boothCost[index].innerText);

    let qty = 0;
    switch (index){
        case 0: qty = $.number((parseInt($('#stand_alone_booth_cnt').val() || 0))); break; //독립부스
        case 1: qty = $.number((parseInt($('#assembly_booth_cnt').val() || 0))); break; //조립부스
        case 2: qty = $.number((parseInt($('#online_booth_cnt option:selected').val() || 0))); break; //온라인부스
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
                case 'discountEarly1': discountPrc = '300000'; break;
                case 'discountEarly2': discountPrc = '200000'; break;
                case 'discountFirst': discountPrc = '500000'; break;
                case 'discountRe': discountPrc = '200000'; break;
                case 'discountScale1': discountPrc = '400000'; break;
                case 'discountScale2': discountPrc = '650000'; break;
                case 'discountScale3': discountPrc = '750000'; break;
                case 'discountScale4': discountPrc = '800000'; break;
                case 'discountScale5': discountPrc = '850000'; break;
                case 'discountScale6': discountPrc = '900000'; break;
                default: break;
            }
            autoDiscountSum($('#'+names[i]),discountPrc);
        }
    }

}

function autoDiscountSum(checkbox){

    let memberCompanyYn = $('#memberCompanyYn').val();
    if(nvl(memberCompanyYn,'') !== ''){
        let memberCompanyYn_id = $(checkbox).prop('id');
        let memberCompanyYn_flag = $(checkbox).prop('checked');
        if(memberCompanyYn === 'N' && memberCompanyYn_id === 'discountLeisure' && memberCompanyYn_flag){
            alert("한국해양레저산업협회 회원사 여부 - \'아니오\'\n체크 시 해당 할인은 불가합니다.");
            $(checkbox).prop('checked',false);
            return;
        }
    }

    let discountId = $(checkbox).prop('id');

    if(discountId === 'discountScale1'){
        let discountFirstChecked = $('#discountFirst').is(':checked');
        if(discountFirstChecked){
            let discountScale1Checked = $('#discountScale1').is(':checked');
            if(discountScale1Checked){
                alert("첫 참가할인과 규모할인을 함께 적용할 경우,\n부스당 첫 참가할인 금액은 30만원으로 조정됩니다.");
            }
        }
    }

    let form_add_total = wonToNumber($('#form_add_total').val());
    if(form_add_total > 100000){

        // 부스 수량 가져오기
        let boothSum = parseInt($('#stand_alone_booth_cnt').val() || 0)
            + parseInt($('#assembly_booth_cnt').val() || 0)
            /*+ parseInt($('#online_booth_cnt option:selected').val())*/;

        if(boothSum > 0) {

            let boothPrc = (parseInt($('#stand_alone_booth_cnt').val() || 0) * 1800000)
                + (parseInt($('#assembly_booth_cnt').val() || 0) * 2100000)
                /*+ (parseInt($('#online_booth_cnt option:selected').val()) * 1000000)*/
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
            $(checkbox).prop('checked',false);
        }
    }

}

function wonToNumber(won){
    return Number.parseInt(won.toString().replaceAll("￦ ","").replaceAll(",",""), 10);
}

function numberToWon(number) {
    if (isNaN(number)) return "￦ 0";
    return "￦ " + Number(number).toLocaleString();
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
        showMessage('#id', 'error', '[ 회원 정보 ]', 'ID를 입력해 주세요.', '');
        return false;
    }

    // ID 체크
    let jsonStr = { id : id , transferYear: transferYear};// 2027
    let seq = ajaxConnectSimple('/searchExhibitorNewSeq.do', 'post', jsonStr);
    if(nvl(seq,'') !== ''){
        Swal.fire({
            icon: 'warning',
            title: '[ 회원 정보 ]',
            html: '<span style="font-size: 1.2em;">해당 ID [ ' + id + ' ] 의<br>비밀번호 초기화를 요청하시겠습니까?</span>',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '요청하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let email = ajaxConnectSimple('/getExhibitorNewEmail.do', 'post', jsonStr);
                if(nvl(email,'') !== ''){
                    let jsonObj = {
                        subject: '[2027 경기국제보트쇼] 비밀번호 초기화 요청', //제목
                        body: "", //본문
                        template: "12", //템플릿 번호
                        receiver: [{email: email}]
                    }

                    let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                    //console.log(i , resData);
                    if (resData.resultCode === "0") {
                        /* 비밀번호 초기화 */
                        let jsonStr1 = { seq : seq };
                        let res = ajaxConnect('/updateExhibitorNewPasswordInit.do', 'post', jsonStr1);
                        if(res.resultCode !== "0"){
                            showMessage('', 'error', '[ 회원 정보 ]', '비밀번호 초기화에 실패하였습니다. 관리자에게 문의해 주세요.', '');
                            return false;
                        }else{
                            Swal.fire({
                                icon: 'info',
                                title: '[ 회원 정보 ]',
                                html: '<span style="font-size: 1.2em;">해당 ID의 비밀번호가 초기화되었습니다.<br>초기화 정보는 [ ' + email + ' ] 로 전송되었습니다.<br>로그인하신 후 비밀번호를 변경하여 이용해 주세요.</span>',
                                allowOutsideClick: false,
                                confirmButtonColor: '#00a8ff',
                                confirmButtonText: '확인'
                            });
                            return false;
                        }
                    }else{
                        Swal.fire({
                            icon: 'info',
                            title: '[ 회원 정보 ]',
                            html: '<span style="font-size: 1.2em;">해당 ID에 등록된 Email 주소로 메일 전송이 실패하였습니다.<br>경기국제보트쇼 사무국으로 문의 바랍니다.<br>Tel. 031-995-8946/8912</span>',
                            allowOutsideClick: false,
                            confirmButtonColor: '#00a8ff',
                            confirmButtonText: '확인'
                        });
                        return false;
                    }
                }else{
                    Swal.fire({
                        icon: 'info',
                        title: '[ 회원 정보 ]',
                        html: '<span style="font-size: 1.2em;">해당 ID에 등록된 Email 주소가 없습니다.<br>경기국제보트쇼 사무국으로 문의 바랍니다.<br>Tel. 031-995-8946/8912</span>',
                        allowOutsideClick: false,
                        confirmButtonColor: '#00a8ff',
                        confirmButtonText: '확인'
                    });
                    return false;
                }
            }
        })
    }else{
        showMessage('', 'error', '[ 회원 정보 ]', '해당 ID로 가입된 업체 정보가 없습니다.', '');
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
        case "2_10":
            step_2_10_check(seq);
            break;
        case "2_9":
            step_2_9_check(seq);
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
        /*case "2_6":
            step_2_6_check(seq);
            break;*/
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

function f_phone_number_valid_check(el) {
    // 순수 DOM 요소를 jQuery 객체로 변환하여 $el 변수에 할당합니다.
    let $el = $(el);

    let phoneNum = $el.val();
    // 하이픈 제거 후 순수 숫자만 추출
    let pureNum = phoneNum.replace(/-/g, "");

    // 값이 비어있지 않은 경우에만 검사
    if (pureNum.length > 0) {
        // 1) 010으로 시작하는지 검사
        if (pureNum.substring(0, 3) !== "010") {
            alert('휴대전화번호는 "010"으로 시작해야 합니다.');
            $el.val(''); // 값 초기화
            $el.trigger('focus'); // 다시 입력하도록 포커스 이동
            return false;
        }

        // 2) 전체 자릿수 검사 (010 국번은 최소 11자리 숫자여야 함)
        if (pureNum.length < 11) {
            alert('휴대전화번호 형식이 올바르지 않습니다.\n(예: 010-1234-5678)');
            $el.trigger('focus'); // 다시 입력하도록 포커스 이동
            return false;
        }
    }
}

function f_id_license_num_set(input){
    $('#companyLicenseNum').val($(input).val().replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1'));
}

function f_id_duplicate_check(el){
    // ID
    let id = $('#id').val();

    if(nvl(id,'') !== ''){
        if(id.length < 10){
            $(el).siblings('.cmnt').css('color', '#AD1D1D');
            $(el).siblings('.cmnt').html('검사 결과 : 사업자 등록번호 10자리를 입력해 주세요.');
            $('#idCheck').val('false');
            return;
        }

        // ID 중복체크
        let jsonStr = { id : id , transferYear: transferYear};// 2027
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

    f_pw_check(el);

    $('#passwordCheck').val('');
    $('#passwordCheck').next('.cmnt').css('color', '#AD1D1D');
    $('#passwordCheck').next('.cmnt').html('비밀번호를 다시 입력해 주세요.');
    $('#pwConfirmCheck').val('false');
}

function f_pw_check(el){
    let pw = $('#password').val();
    let number = pw.search(/[0-9]/g);
    let english = pw.search(/[a-z]/ig);
    let space = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

    if (pw.length < 8 || pw.length > 17) {
        $(el).next('.cmnt').next('.cmnt').html('8자리 이상, 16자리 이내로 입력해 주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }

    if (pw.search(/\s/) !== -1) {
        $(el).next('.cmnt').next('.cmnt').html('비밀번호는 공백 없이 입력해 주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }

    if (number < 0 || english < 0 || space < 0) {
        $(el).next('.cmnt').next('.cmnt').html('영문, 숫자, 특수문자를 혼합하여 입력해 주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }

    if ((number < 0 && english < 0) || (english < 0 && space < 0) || (space < 0 && number < 0)) {
        $(el).next('.cmnt').next('.cmnt').html('영문, 숫자, 특수문자 중 2가지 이상을 혼합하여 입력해 주세요.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }

    if (/(\w)\1\1\1/.test(pw)) {
        $(el).next('.cmnt').next('.cmnt').html('같은 문자를 4번 이상 사용하실 수 없습니다.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }

    /*if (false === reg.test(pw)) {
        $(el).next('.cmnt').next('.cmnt').html('비밀번호는 8~16자리 이어야 하며, 숫자/영문/특수문자를 모두 포함해야 합니다.');
        $(el).next('.cmnt').next('.cmnt').css('color', '#AD1D1D');
        $('#pwCheck').val('false');
        return false;
    }*/

    $(el).next('.cmnt').next('.cmnt').html('비밀번호가 정상적으로 입력되었습니다.');
    $(el).next('.cmnt').next('.cmnt').css('color', '#1D5CAD');
    $('#pwCheck').val('true');

    return true;
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

async function step_01_check(exhibitorSeq){

    // 전시회 참가규정
    let agree1 = $('input[type=radio][name=agree1]:checked').val();
    if(nvl(agree1,'') === '' || agree1 === 'N'){
        showMessage('', 'info', '[ 약관 동의 ]', '전시회 참가규정에 동의해 주세요.', '');
        return false;
    }

    // 개인정보 취급방침
    let agree2 = $('input[type=radio][name=agree2]:checked').val();
    if(nvl(agree2,'') === '' || agree2 === 'N'){
        showMessage('', 'info', '[ 약관 동의 ]', '개인정보 취급방침에 동의해 주세요.', '');
        return false;
    }

    // ID
    let id = $('#id').val();
    if(nvl(id,'') === ''){
        showMessage('#id', 'error', '[ 회원 계정 정보 ]', '아이디를 입력해 주세요.', '');
        return false;
    }

    // ID
    let idCheck = $('#idCheck').val();
    if(idCheck === 'false'){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '아이디 중복 검사를 수행해 주세요.', '');
        return false;
    }

    // 비밀번호
    let password = $('#password').val();
    if(nvl(password,'') === ''){
        showMessage('#password', 'error', '[ 회원 계정 정보 ]', '비밀번호를 입력해 주세요.', '');
        return false;
    }
    
    // 비밀번호 확인
    let passwordCheck = $('#passwordCheck').val();
    if(nvl(passwordCheck,'') === ''){
        showMessage('#password', 'error', '[ 회원 계정 정보 ]', '비밀번호 확인을 입력해 주세요.', '');
        return false;
    }

    // 비밀번호 유효성
    let pwCheck = $('#pwCheck').val();
    if(pwCheck === 'false'){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '비밀번호 유효성 검사를 수행해 주세요.', '');
        return false;
    }

    // 비밀번호 확인 유효성
    let pwConfirmCheck = $('#pwConfirmCheck').val();
    if(pwConfirmCheck === 'false'){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '비밀번호를 확인해 주세요.', '');
        return false;
    }

    /******************** 참가업체 정보 ********************/

    // 사업자등록번호
    let companyLicenseNum = $('#companyLicenseNum').val();
    if(nvl(companyLicenseNum,'') === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '사업자등록번호를 입력해 주세요.', '');
        return false;
    }

    // 회사명
    let companyNameKo = $('#companyNameKo').val();
    if(nvl(companyNameKo,'') === ''){
        showMessage('#companyNameKo', 'error', '[ 참가업체 정보 ]', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }
    let companyNameEn = $('#companyNameEn').val();
    if(nvl(companyNameEn,'') === ''){
        showMessage('#companyNameEn', 'error', '[ 참가업체 정보 ]', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 본사 주소
    let companyAddress = $('#companyAddress').val();
    if(nvl(companyAddress,'') === ''){
        showMessage('#companyAddress', 'error', '[ 참가업체 정보 ]', '본사 주소를 입력해 주세요.', '');
        return false;
    }
    let companyAddressDetail = $('#companyAddressDetail').val();
    if(nvl(companyAddressDetail,'') === ''){
        showMessage('#companyAddressDetail', 'error', '[ 참가업체 정보 ]', '본사 상세 주소를 입력해 주세요.', '');
        return false;
    }

    // 공장 주소
    let factoryAddress = $('#factoryAddress').val();
    let factoryAddressDetail = $('#factoryAddressDetail').val();

    // 대표자
    let companyCeo = $('#companyCeo').val();
    if(nvl(companyCeo,'') === ''){
        showMessage('#companyCeo', 'error', '[ 참가업체 정보 ]', '대표자명을 입력해 주세요.', '');
        return false;
    }

    // 전화
    let companyTel = $('#companyTel').val();
    if(nvl(companyTel,'') === ''){
        showMessage('#companyTel', 'error', '[ 참가업체 정보 ]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 홈페이지
    let companyHomepage = $('#companyHomepage').val();
    if(nvl(companyHomepage,'') !== ''){
        let no_companyHomepage = $('input[type=checkbox][name=noPage]').is(':checked');
        if(no_companyHomepage === false){
            if(!checkUrl(companyHomepage)){
                showMessage('#companyHomepage', 'error', '[ 참가업체 정보 ]', '홈페이지 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
                return false;
            }
        }
    }else{
        let no_companyHomepage = $('input[type=checkbox][name=noPage]').is(':checked');
        if(no_companyHomepage === false){
            showMessage('#companyHomepage', 'error', '[ 참가업체 정보 ]', '홈페이지가 없다면 \"홈페이지 없음\" 에 체크해 주세요.', '');
            return false;
        }
    }

    // Fax
    let companyFax = $('#companyFax').val();

    // 산업분류
    let industryPart = $('#industryPart').val();
    let industryPartEtc = $('#industryPartEtc').val();
    if(nvl(industryPart,'') === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '산업 분류 항목을 선택해 주세요.', '');
        return false;
    }else{
        if(industryPart.includes('기타')){
            if(industryPartEtc === ''){
                showMessage('#industryPartEtc', 'error', '[ 참가업체 정보 ]', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    // 사업자등록증
    let companyLicenseFile_li = $('.companyLicenseFile_li').length;
    if(companyLicenseFile_li === 0){
        let companyLicense = $('#companyLicense').val();
        if (nvl(companyLicense,'') === '') {
            showMessage('', 'info', '[ 참가업체 정보 ]', '사업자등록증을 첨부해 주세요.', '');
            return false;
        }
    }

    // 로고
    let logoFile_li = $('.logoFile_li').length;
    if(logoFile_li === 0){
        let logo = $('#logo').val();
        if (nvl(logo,'') === '') {
            showMessage('', 'error', '[ 참가업체 정보 ]', '로고 파일을 업로드해 주세요.', '');
            return false;
        }
    }

    // 기참가연도
    let prePartYear = '';
    let prePartYearChecked = $("input[type='checkbox'][name='prePartYear']:checked");
    let prePartYearCheckedLength = prePartYearChecked.length;
    if(prePartYearCheckedLength > 0){
        for(let i=0; i<prePartYearCheckedLength; i++){
            prePartYear += prePartYearChecked.eq(i).val();
            if((i+1) !== prePartYearCheckedLength){
                prePartYear += '^';
            }
        }
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '기참가연도를 선택해 주세요.', '');
        return false;
    }

    // 한국해양레저산업협회 회원사 여부
    let memberCompanyYn = $("input[type='radio'][name='memberCompanyYn']:checked").val();

    /******************** 담당자 정보 ********************/
    /******************** 대표담당자 정보 ********************/

    // 성명
    let name = $('#name').val();
    if(nvl(name,'') === ''){
        showMessage('#name', 'error', '[ 담당자 정보 ]', '성명을 입력해 주세요.', '');
        return false;
    }
    
    // 직위
    let position = $('#position').val();
    if(nvl(position,'') === ''){
        showMessage('#position', 'error', '[ 담당자 정보 ]', '직위를 입력해 주세요.', '');
        return false;
    }

    // 부서
    let depart = $('#depart').val();

    // 전화번호
    let tel = $('#tel').val();
    if(nvl(tel,'') === ''){
        showMessage('#tel', 'error', '[ 담당자 정보 ]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 휴대전화
    let phone = $('#phone').val();
    if(nvl(phone,'') === ''){
        showMessage('#phone', 'error', '[ 담당자 정보 ]', '휴대전화번호를 입력해 주세요.', '');
        return false;
    }else{
        if ( !/^010-[0-9]{4}-[0-9]{4}$/.test( phone ) ) {
            showMessage('#phone', 'error', '[ 담당자 정보 ]', '올바른 휴대전화번호를 입력해 주세요.<br>(앞자리 010 만 가능합니다.)', '');
            return false;
        }
    }

    // 이메일
    let email1 = $('#email1').val();
    let email2 = $('#email2').val();
    if(nvl(email1,'') === ''){
        showMessage('#email1', 'error', '[ 담당자 정보 ]', '이메일을 입력해 주세요.', '');
        return false;
    }
    if(nvl(email2,'') === ''){
        showMessage('', 'error', '[ 담당자 정보 ]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    /******************** 담당자 정보 ********************/
    /******************** 부담당자 정보 ********************/

    // 담당자 성명
    let charge_person_name_el = $('input[type=text][name=chargePersonName]');
    /*let charge_person_name_flag = true;
    for(let i=0; i<charge_person_len; i++){
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
        showMessage('', 'error', '[담당자 정보]', '휴대전화를 입력해 주세요.<br>또는 올바른 휴대전화번호 형식으로 입력해 주세요.(010 만 가능)', '');
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
    let chargePersonCnt = Number.parseInt($('.managerInfoNum:last').text());
    if(chargePersonCnt > 0){
        for(let i=0; i<chargePersonCnt; i++){
            let chargePersonName = charge_person_name_el.eq(i).val();
            if(nvl(chargePersonName,'') !== ''){
                let chargePersonEmail_val = charge_person_email_el.eq(i).val();
                let chargePersonEmail = '';
                if(nvl(chargePersonEmail_val,'') !== ''){
                    chargePersonEmail = chargePersonEmail_val + '@' + charge_person_domain_el.eq(i).val();
                }
                let chargePersonList_json_obj = {
                    seq: $('input[type=hidden][name=chargeSeq]').eq(i).val(),
                    exSeq: exhibitorSeq,
                    chargePersonName: chargePersonName,
                    chargePersonPosition: charge_person_position_el.eq(i).val(),
                    chargePersonDepart: charge_person_depart_el.eq(i).val(),
                    chargePersonTel: charge_person_tel_el.eq(i).val(),
                    chargePersonPhone: charge_person_phone_el.eq(i).val(),
                    chargePersonEmail: chargePersonEmail,
                };
                chargePersonList_json_arr.push(chargePersonList_json_obj);
            }
        }
    }

    /******************** 참가행사 및 분야 ********************/

    // 참가행사 및 분야
    let fieldParticipatory = $('input[type=radio][name=fieldParticipatory]:checked').val();
    let fieldParticipatory1 = '';
    let fieldParticipatory2 = '';
    let fieldParticipatory3 = '';
    if(nvl(fieldParticipatory,'') === ''){
        showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가행사 및 분야를 선택해 주세요.', '');
        return false;
    }else{
        fieldParticipatory1 = $('select[name=fieldParticipatory1]').val();
        if(nvl(fieldParticipatory1,'') === ''){
            showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가분야 1순위를 선택해 주세요.', '');
            return false;
        }

        fieldParticipatory2 = $('select[name=fieldParticipatory2]').val();
        /*if(nvl(fieldParticipatory2,'') === ''){
            showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가분야 2순위를 선택해 주세요.', '');
            return false;
        }*/

        fieldParticipatory3 = $('select[name=fieldParticipatory3]').val();
        /*if(nvl(fieldParticipatory3,'') === ''){
            showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가분야 3순위를 선택해 주세요.', '');
            return false;
        }*/
    }
    //console.log(field_part);

    /******************** 상세정보 ********************/

    // 회사소개영상
    let companyIntroVideo = $('#companyIntroVideo').val();
    if(nvl(companyIntroVideo,'') !== ''){
        if(!checkUrl(companyIntroVideo)){
            showMessage('#companyIntroVideo', 'error', '[ 상세정보 ]', '영상 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }
    }

    // 회사소개
    let companyIntroKo = $('#companyIntroKo').val();
    if(nvl(companyIntroKo,'') === ''){
        showMessage('#companyIntroKo', 'error', '[ 상세정보 ]', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }
    let companyIntroEn = $('#companyIntroEn').val();
    if(nvl(companyIntroEn,'') === ''){
        showMessage('#companyIntroEn', 'error', '[ 상세정보 ]', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    // KIBS 참가목적
    let companyPurposeKo = $('#companyPurposeKo').val();
    if(nvl(companyPurposeKo,'') === ''){
        showMessage('', 'error', '[ 상세정보 ]', 'KIBS 참가목적(국문)을 선택해 주세요.', '');
        return false;
    }
    let companyPurposeEn = $('#companyPurposeEn').val();
    if(nvl(companyPurposeEn,'') === ''){
        showMessage('', 'error', '[ 상세정보 ]', 'KIBS 참가목적(영문)을 선택해 주세요.', '');
        return false;
    }

    // 신제품출품 사항 소개
    let newItemIntroKo = $('#newItemIntroKo').val();
    let newItemIntroEn = $('#newItemIntroEn').val();

    // 프로모션 정보
    let promotionPlan = $('#promotionPlan').val();

    /******************** 제품 노출 정보 ********************/

    let onlineList_json_arr = [];

    // 제품명(국문)
    let online_name_ko_el = $('input[type=text][name=onlineNameKo]');
    let online_name_ko_len = online_name_ko_el.length;
    let online_name_ko_flag = true;
    for(let i=0; i<online_name_ko_len; i++){
        if(nvl(online_name_ko_el.eq(i).val(),'') === ''){
            online_name_ko_flag = false;
            break;
        }
    }
    if(online_name_ko_flag){
        // 제품분류(대)
        let online_option_big_el = $('select[name=onlineOptionBig]');
        let online_option_big_len = online_option_big_el.length;
        let online_option_big_flag = true;
        for(let i=0; i<online_option_big_len; i++){
            if(online_option_big_el.eq(i).val() === '선택' || online_option_big_el.eq(i).val() === '선택'){
                if($('.preOptionList').length === 0){
                    online_option_big_flag = false;
                    break;
                }
            }
        }
        if(!online_option_big_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 분류 첫 번째 항목을 선택해 주세요.', '');
            return false;
        }

        // 제품분류(소)
        let online_option_small_el = $('select[name=onlineOptionSmall]');
        let online_option_small_len = online_option_small_el.length;
        let online_option_small_flag = true;
        for(let i=0; i<online_option_small_len; i++){
            if(online_option_small_el.eq(i).val() === '선택' || online_option_small_el.eq(i).val() === '선택'){
                if($('.preOptionList').length === 0) {
                    online_option_small_flag = false;
                    break;
                }
            }
        }
        if(!online_option_small_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 분류 두 번째 항목을 선택해 주세요.', '');
            return false;
        }

        // 제품명(국문)
        let online_name_ko_el = $('input[type=text][name=onlineNameKo]');
        let online_name_ko_len = online_name_ko_el.length;
        let online_name_ko_flag = true;
        for(let i=0; i<online_name_ko_len; i++){
            if(nvl(online_name_ko_el.eq(i).val(),'') === ''){
                online_name_ko_flag = false;
                break;
            }
        }
        if(!online_name_ko_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품명(국문)을 입력해 주세요.', '');
            return false;
        }

        // 제품명(영문)
        let online_name_en_el = $('input[type=text][name=onlineNameEn]');
        let online_name_en_len = online_name_en_el.length;
        let online_name_en_flag = true;
        for(let i=0; i<online_name_en_len; i++){
            if(nvl(online_name_en_el.eq(i).val(),'') === ''){
                online_name_en_flag = false;
                break;
            }
        }
        if(!online_name_en_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품사진
        let onlinePrdBox = $('.onlinePrdBox');
        for(let i=0; i<onlinePrdBox.length; i++){
            let inputFile = onlinePrdBox.eq(i).find('.upload_name').val();
            let preFileList = $('.onlineInfoBox').eq(i).find('.preValueList').find('li.onlineImageFile_li').length;
            if(nvl(inputFile,"") === "" && preFileList === 0){
                showMessage('', 'error', '[ 제품 노출 정보 ]', '제품사진을 첨부해 주세요.', '');
                return false;
            }
        }

        // 제품 설명(국문)
        let online_intro_ko_el = $('textarea[name=onlineIntroKo]');
        let online_intro_ko_len = online_intro_ko_el.length;
        let online_intro_ko_flag = true;
        for(let i=0; i<online_intro_ko_len; i++){
            if(online_intro_ko_el.eq(i).val() === ''){
                online_intro_ko_flag = false;
            }
        }
        if(!online_intro_ko_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 설명(국문)을 입력해 주세요.', '');
            return false;
        }

        // 제품 설명(영문)
        let online_intro_en_el = $('textarea[name=onlineIntroEn]');
        let online_intro_en_len = online_intro_en_el.length;
        let online_intro_en_flag = true;
        for(let i=0; i<online_intro_en_len; i++){
            if(online_intro_en_el.eq(i).val() === ''){
                online_intro_en_flag = false;
            }
        }
        if(!online_intro_en_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품링크
        let online_link_el = $('input[type=text][name=onlineLink]');
        for(let i=0; i<online_link_el.length; i++){
            let online_link = online_link_el.eq(i).val();
            if(nvl(online_link,'') !== ''){
                if(!checkUrl(online_link)){
                    showMessage('', 'error', '[ 제품 노출 정보 ]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
                    return false;
                }
            }
        }

        // 길이
        let online_length_el = $('input[type=text][name=onlineLength]');

        // 너비
        let online_width_el = $('input[type=text][name=onlineWidth]');

        // 높이
        let online_height_el = $('input[type=text][name=onlineHeight]');

        // 중량
        let online_weight_el = $('input[type=text][name=onlineWeight]');

        // 소재
        let online_material_el = $('input[type=text][name=onlineMaterial]');

        // 연식
        let online_year_el = $('input[type=text][name=onlineYear]');

        let onlineCnt = Number.parseInt($('.onlineInfoNum:last').text());
        if(onlineCnt > 0){
            for(let i=0; i<onlineCnt; i++){
                let onlineOptionBig = '';
                let onlineOptionSmall = '';
                let selOnlineOptionBig = $('select[name=onlineOptionBig]').eq(i).val();
                if(nvl(selOnlineOptionBig,'선택') !== '선택'){
                    onlineOptionBig = $('select[name=onlineOptionBig]').eq(i).val();
                    onlineOptionSmall = $('select[name=onlineOptionSmall]').eq(i).val();
                }else {
                    onlineOptionBig = $('input[type=text][name=preOnlineOptionBig]').eq(i).val();
                    onlineOptionSmall = $('input[type=text][name=preOnlineOptionSmall]').eq(i).val();
                }
                let onlineList_json_obj = {
                    seq: $('input[type=hidden][name=onlineSeq]').eq(i).val(),
                    exSeq: exhibitorSeq,
                    onlineOptionBig: onlineOptionBig,
                    onlineOptionSmall: onlineOptionSmall,
                    onlineNameKo: online_name_ko_el.eq(i).val(),
                    onlineNameEn: online_name_en_el.eq(i).val(),
                    onlineIntroKo: online_intro_ko_el.eq(i).val(),
                    onlineIntroEn: online_intro_en_el.eq(i).val(),
                    onlineLink: online_link_el.eq(i).val(),
                    onlineLength: online_length_el.eq(i).val(),
                    onlineWidth: online_width_el.eq(i).val(),
                    onlineHeight: online_height_el.eq(i).val(),
                    onlineWeight: online_weight_el.eq(i).val(),
                    onlineMaterial: online_material_el.eq(i).val(),
                    onlineYear: online_year_el.eq(i).val(),
                    note: (i+1)
                };
                onlineList_json_arr.push(onlineList_json_obj);
            }
        }
    }else{
        onlineList_json_arr = [];
    }

    // 수출상담회 참가 희망 여부
    let export_meeting_yn = $('input[type=radio][name=exportMeetingYn]:checked').val();
    if(export_meeting_yn === 'N'){
        buyer_add_json_arr = [];
    }

    let lang = 'KO';
    let link =  window.location.href;
    if(link.includes('eng')){
        lang = 'EN';
    }

    let exhibitor_json_obj = {
        seq: exhibitorSeq,
        lang: lang,
        transferYear: transferYear,
        id: id,
        password: password,
        passwordYn: 'Y',
        /* 참가업체 정보 */
        companyLicenseNum: companyLicenseNum,
        companyNameKo: companyNameKo,
        companyNameEn: companyNameEn,
        companyAddress: companyAddress,
        companyAddressDetail: companyAddressDetail,
        factoryAddress: factoryAddress,
        factoryAddressDetail: factoryAddressDetail,
        companyCeo: companyCeo,
        companyTel: companyTel,
        companyHomepage: companyHomepage,
        companyFax: companyFax,
        industryPart: industryPart,
        industryPartEtc: industryPartEtc,
        prePartYear: prePartYear,
        memberCompanyYn: memberCompanyYn,
        /* 담당자 정보 */
        name: name,
        position: position,
        depart: depart,
        tel: tel,
        phone: phone,
        email: email1 + '@' + email2,
        /* 부담당자 정보 */
        chargePersonList: chargePersonList_json_arr,
        /* 참가분야 */
        fieldParticipatory: fieldParticipatory,
        fieldParticipatory1: fieldParticipatory1,
        fieldParticipatory2: fieldParticipatory2,
        fieldParticipatory3: fieldParticipatory3,
        /* 상세 정보 */
        companyIntroVideo: companyIntroVideo,
        companyIntroKo: companyIntroKo,
        companyIntroEn: companyIntroEn,
        companyPurposeKo: companyPurposeKo,
        companyPurposeEn: companyPurposeEn,
        newItemIntroKo: newItemIntroKo,
        newItemIntroEn: newItemIntroEn,
        promotionPlan: promotionPlan,
        /* 제품 노출 정보 */
        onlineList: onlineList_json_arr,
        /* 수출상담회 */
        exportMeetingYn: export_meeting_yn,
        buyerList: buyer_add_json_arr
    };

    //console.log('data : ' + JSON.stringify(exhibitor_json_obj));

    let resData = "";
    if(nvl(exhibitorSeq,'') !== ''){
        resData = ajaxConnect('/mypage/step/updateExhibitorNew.do', 'post', exhibitor_json_obj);
    }else{
        resData = ajaxConnect('/apply/step/insertExhibitorNew.do', 'post', exhibitor_json_obj);
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
            // 로딩바 표시 (업로드 시작 전)
            Swal.fire({
                title: "정보 및 파일 저장 중",
                html: "파일을 업로드하고 있습니다.<br>잠시만 기다려 주세요.",
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });

            try {
                // 3-1. 사용자가 X 눌렀던 파일들 실제 삭제 (await)
                await processDeletedFiles();

                // [핵심 수정] 파일 업로드가 끝날 때까지 기다림 (await)
                await f_company_uploadFile_call(exhibitorSeq, exhibitorSeq);

                // 업로드 완료 후 성공 메시지 표시
                Swal.fire({
                    icon: 'info',
                    title: '[ 참가업체 정보 ]',
                    html: '<span style="font-size: 1.2em;">기본 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        /* 온라인 제품 사진 번호 재부여 */
                        let onlineFile_json_obj = {
                            seq: exhibitorSeq,
                            onlineList: onlineList_json_arr
                        }
                        let online_res = ajaxConnect('/mypage/step/updateOnlineNewFileNote.do', 'post', onlineFile_json_obj);

                        if(online_res.resultCode === "0"){
                            isSubmitProceeding = true;

                            f_page_move('/apply/step2_1.do', exhibitorSeq);
                        }
                    }
                });
            } catch (err) {
                console.error("File processing error:", err);
                Swal.fire("오류", "파일 처리 중 오류가 발생했습니다.", "error");
            }
        }else{
            window.location.href = '/apply/step2_1.do';
        }
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

let buyer_add_json_arr = [];
function f_buyer_add(exSeq){

    // 회사명
    let buyer_company_name = $('#buyer_company_name').val();
    /*if(buyer_company_name === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '회사명을 입력해 주세요.', '');
        return false;
    }*/

    // 국가
    let buyer_country = $('#buyer_country').val();
    /*if(buyer_country === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '국가를 입력해 주세요.', '');
        return false;
    }*/

    // 소재지
    let buyer_location = $('#buyer_location').val();
    /*if(buyer_location === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '소재지를 입력해 주세요.', '');
        return false;
    }*/

    // 홈페이지
    let buyer_homepage = $('#buyer_homepage').val();
    /*if(buyer_homepage === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '홈페이지 주소를 입력해 주세요.', '');
        return false;
    }*/

    // 담당자 성명
    let buyer_charge = $('#buyer_charge').val();

    // 부서
    let buyer_depart = $('#buyer_depart').val();

    // 직책
    let buyer_position = $('#buyer_position').val();

    // 이메일
    let buyer_email_input1 = $('#buyer_email_input1').val();
    let buyer_email_input2 = $('#buyer_email_input2').val();

    // 전화번호
    let buyer_tel = $('#buyer_tel').val();

    // 휴대전화
    let buyer_phone = $('#buyer_phone').val();
    if ( nvl(buyer_phone,'') !== '' && !/^010-[0-9]{4}-[0-9]{4}$/.test( buyer_phone ) ) {
        showMessage('', 'error', '[ 바이어 정보 ]', '올바른 휴대전화번호를 입력해 주세요.<br>(앞자리 010 만 가능합니다.)', '');
        return false;
    }

    // 팩스
    let buyer_fax = $('#buyer_fax').val();

    // 취급품목
    let buyer_item = $('#buyer_item').val();
    /*if(buyer_item === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '취급품목을 입력해 주세요.', '');
        return false;
    }*/

    // 초청사유
    let buyer_invite_reason = $('#buyer_invite_reason').val();
    /*if(buyer_invite_reason === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '초청사유를 입력해 주세요.', '');
        return false;
    }*/

    // 계약진행 여부
    let buyer_progress_yn = $('#buyer_progress_yn').val();

    // 희망사항
    let buyer_hope = $('#buyer_hope').val();
    /*if(buyer_hope === ''){
        showMessage('', 'error', '[ 바이어 정보 ]', '희망사항을 입력해 주세요.', '');
        return false;
    }*/

    /* json Array 에 push */

    let buyer_add_json_obj = {
        seq: '',
        exSeq: exSeq,
        buyerCompanyName: buyer_company_name,
        buyerCompanyCountry: buyer_country,
        buyerCompanyLocation: buyer_location,
        buyerCompanyHomepage: buyer_homepage,
        buyerCompanyCharge: buyer_charge,
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

    //buyer_add_json_arr.push(buyer_add_json_obj);

    /* body 에 팝업 데이터를 포함한 Element 생성 */
    let buyer_add_btn = $('#buyer_add_btn');

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

    //담당자성명
    let buyerCompanyCharge_el = document.createElement('input');
    buyerCompanyCharge_el.type = 'hidden';
    buyerCompanyCharge_el.name = 'buyerCompanyCharge';
    buyerCompanyCharge_el.id = 'buyerCompanyCharge';
    buyerCompanyCharge_el.value = buyer_charge;

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
    delFormBuyer.onclick = function(){ f_buyer_remove('create', this, ''); }
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
    form_chuga_list.appendChild(buyerCompanyCharge_el);
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
    // 담당자성명
    document.querySelector('#buyer_charge').value = null;
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
            seq: value
        };
        let resData = ajaxConnect('/mypage/step/selectBuyerSingle.do','post',jsonObj);

        f_buyer_modal_set(resData);

    }else{
        let row_el = $(value).parent('div');
        let jsonObj = {
            buyerCompanyName: row_el.find('input[type=hidden][name=buyerCompanyName]').val(),
            buyerCompanyCountry: row_el.find('input[type=hidden][name=buyerCompanyCountry]').val(),
            buyerCompanyLocation: row_el.find('input[type=hidden][name=buyerCompanyLocation]').val(),
            buyerCompanyHomepage: row_el.find('input[type=hidden][name=buyerCompanyHomepage]').val(),
            buyerCompanyCharge: row_el.find('input[type=hidden][name=buyerCompanyCharge]').val(),
            buyerCompanyDepart: row_el.find('input[type=hidden][name=buyerCompanyDepart]').val(),
            buyerCompanyPosition: row_el.find('input[type=hidden][name=buyerCompanyPosition]').val(),
            buyerCompanyEmail: row_el.find('input[type=hidden][name=buyerCompanyEmail]').val(),
            buyerCompanyTel: row_el.find('input[type=hidden][name=buyerCompanyTel]').val(),
            buyerCompanyPhone: row_el.find('input[type=hidden][name=buyerCompanyPhone]').val(),
            buyerCompanyFax: row_el.find('input[type=hidden][name=buyerCompanyFax]').val(),
            buyerCompanyItem: row_el.find('input[type=hidden][name=buyerCompanyItem]').val(),
            buyerCompanyInviteReason: row_el.find('input[type=hidden][name=buyerCompanyInviteReason]').val(),
            buyerCompanyProgressYn: row_el.find('input[type=hidden][name=buyerCompanyProgressYn]').val(),
            buyerCompanyHope: row_el.find('input[type=hidden][name=buyerCompanyHope]').val()
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
    $('#buyer_popup #buyer_charge').val(jsonObj.buyerCompanyCharge);
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

function f_buyer_remove(gbn, el, seq){
    //console.log(gbn, el);

    Swal.fire({
        icon: 'warning',
        title: '[ 바이어 정보 ]',
        html: '<span style="font-size: 1.2em;">선택한 바이어 정보를 삭제하시겠습니까?</span>',
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
                    seq: seq
                };
                let resData = ajaxConnect('/mypage/step/deleteBuyer.do','post', jsonObj);
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
        showMessage('', 'error', '[ 참가분야 ]', '최대 3개까지만 선택할 수 있습니다.', '');
        obj.checked = false;
        return false;
    }
}

/* //////////////////////////////// end:step_01 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_1 ////////////////////////////////// */
function step_2_1_check(exhibitorSeq){

    let utilityPrcSum = parseInt($('#utilityPrcSum').val()) || 0;

    // --- 1. 부스 정보 수집 ---
    const registrationCnt = 1;
    const registrationFee = 100000;
    const standAloneBoothCnt = parseInt($('#standAloneBoothCnt').val()) || 0;
    const assemblyBoothCnt = parseInt($('#assemblyBoothCnt').val()) || 0;
    //const onlineBoothCnt = parseInt($('#onlineBoothCnt').val()) || 0;
    const physicalBooths = standAloneBoothCnt + assemblyBoothCnt ;

    const standAloneBoothFee = standAloneBoothCnt * boothPrices.standAlone;
    const assemblyBoothFee = assemblyBoothCnt * boothPrices.assembly;
    //const onlineBoothFee = onlineBoothCnt * boothPrices.online;

    // 부스 관련 총액 (등록비 포함)
    const boothPrcSum = registrationFee + standAloneBoothFee + assemblyBoothFee/* + onlineBoothFee*/;

    let boothType = '등록비';
    if (standAloneBoothCnt > 0) boothType += ',독립부스';
    if (assemblyBoothCnt > 0) boothType += ',조립부스';
    //if (onlineBoothCnt > 0) boothType += ',온라인부스';

    // --- 2. 할인 정보 수집 및 계산 (전면 수정) ---
    let discountType = '';
    let discountPrcSum = 0;
    // 체크된 모든 할인을 순회하며 할인 총액과 할인 타입을 동적으로 생성합니다.
    $('input[name="discount"]:checked').each(function() {
        const id = $(this).attr('id');
        const discountAmount = parseInt($(this).data('discount')) || 0;
        discountPrcSum += (physicalBooths * discountAmount);

        // discountType 문자열을 ID 기반으로 생성
        switch(id) {
            case 'discountEarly1': discountType += ',(1차)조기신청'; break;
            case 'discountEarly2': discountType += ',(2차)조기신청'; break;
            case 'discountScale1': discountType += ',규모할인(10+)'; break;
            case 'discountScale2': discountType += ',규모할인(20+)'; break;
            case 'discountScale3': discountType += ',규모할인(30+)'; break;
            case 'discountScale4': discountType += ',규모할인(40+)'; break;
            case 'discountScale5': discountType += ',규모할인(50+)'; break;
            case 'discountScale6': discountType += ',규모할인(100+)'; break;
            case 'discountRe': discountType += ',재참가'; break;
            case 'discountFirstUnder10': discountType += ',첫참가(10미만)'; break;
            case 'discountFirstOver10': discountType += ',첫참가(10이상)'; break;
            case 'discountLeisure': discountType += ',해양레저산업협회'; break;
        }
    });

    if (discountType.startsWith(',')) {
        discountType = discountType.substring(1);
    }

    // --- 3. 발전기금 계산 (참가자가 보는 화면 계산용) ---
    /*let developmentFund = 0;
    const isMember = $('#memberCompanyYn').val() === 'Y';
    const isLeisureDiscountChecked = $('#discountLeisure').is(':checked');
    if (isMember || isLeisureDiscountChecked) {
        developmentFund = Math.floor(boothPrcSum * 0.1);
    }

    // --- 4. 최종 금액 계산 ---
    // 공급가액 = ((부스총액 + 발전기금) - 할인총액) + 유틸리티총액
    const prcSum = ((boothPrcSum + developmentFund) - discountPrcSum) + utilityPrcSum;
    const prcVat = Math.floor(prcSum * 0.1);
    const prcTotal = prcSum + prcVat;*/

    // --- 5. 유효성 검사 및 서버 전송 데이터 구성 ---
    const totalBooths = standAloneBoothCnt + assemblyBoothCnt/* + onlineBoothCnt*/;
    if (totalBooths === 0) {
        showMessage('', 'error', '[ 전시부스 신청 ]', '부스(독립, 조립)를 하나 이상 신청해 주세요.', '');
        return;
    }

    // 서버로 보낼 최종 데이터 객체
    const booth_json_obj = {
        seq: exhibitorSeq,
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registrationCnt,
        registrationFee: registrationFee,
        standAloneBoothCnt: standAloneBoothCnt,
        standAloneBoothFee: standAloneBoothFee,
        assemblyBoothCnt: assemblyBoothCnt,
        assemblyBoothFee: assemblyBoothFee,
        /*onlineBoothCnt: onlineBoothCnt,
        onlineBoothFee: onlineBoothFee,*/
        discountEarly1: $('#discountEarly1').is(':checked'),
        discountEarly2: $('#discountEarly2').is(':checked'),
        discountFirstUnder10: $('#discountFirstUnder10').is(':checked'),
        discountFirstOver10: $('#discountFirstOver10').is(':checked'),
        discountRe: $('#discountRe').is(':checked'),
        discountScale1: $('#discountScale1').is(':checked'),
        discountScale2: $('#discountScale2').is(':checked'),
        discountScale3: $('#discountScale3').is(':checked'),
        discountScale4: $('#discountScale4').is(':checked'),
        discountScale5: $('#discountScale5').is(':checked'),
        discountScale6: $('#discountScale6').is(':checked'),
        discountLeisure: $('#discountLeisure').is(':checked'),
        discountYn: (discountType !== '') ? 'Y' : 'N',
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum,
        prcSum: parseInt($("input[name='prcSum']").val()) || 0,
        prcVat: parseInt($("input[name='prcVat']").val()) || 0,
        prcTotal: parseInt($("input[name='prcTotal']").val()) || 0
    };

    let resData = ajaxConnect('/apply/step/updateExhibitorNewBooth.do', 'post', booth_json_obj);
    if(resData.resultCode === "0") {

        Swal.fire({
            icon: 'info',
            title: '[ 전시부스 신청 ]',
            html: '<span style="font-size: 1.2em;">부스 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step2_9.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 전시부스 신청 ]', '전시부스 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function wonToInt(won){
    return won.replace(/\s/g, '').replace(/\￦/g, '').replace(/\,/g, '');
}
/* //////////////////////////////// end:step_2_1 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_10 ////////////////////////////////// */
function step_2_10_check(exhibitorSeq){

    Swal.fire({
        icon: 'info',
        title: '[ 해상전시회 신청 ]',
        html: '<span style="font-size: 1.2em;"><br><span style="background-color:#00a8ff; color:#ffffff;">지금 계속하기</span> 클릭 시 내용 저장 후<br>다음 페이지로 넘어갑니다.<br><br><span style="background-color:#A1A5B7; color:#ffffff;">나중에 하기</span> 클릭 시 다음 페이지로 넘어가며,<br>언제든 로그인하여 이어서 작성할 수 있습니다.</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '지금 계속하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '나중에 하기',
        reverseButtons: true
    }).then((result) => {

        if (result.isConfirmed) {

            const participationChoice = $('input[name="maritimeExhibitionYn"]:checked').val();
            const isSeaChecked = $('#maritimeDetailBox input[name="maritimeExhibitionSea"]').is(':checked');
            const isLandChecked = $('#maritimeDetailBox input[name="maritimeExhibitionLand"]').is(':checked');

            // 1. '참가'를 선택한 경우
            if (participationChoice === 'Y') {
                // 1-1. 해상 전시 또는 육상 전시 둘 다 선택하지 않은 경우
                if (!isSeaChecked && !isLandChecked) {
                    Swal.fire({
                        icon: 'error',
                        title: '[ 해상전시회 신청 ]',
                        text: '해상전시회 참가를 선택했을 경우, 참가 항목(해상/육상)을 1개 이상 선택해주세요.',
                        confirmButtonColor: '#00a8ff',
                        confirmButtonText: '확인'
                    });
                    return; // 서버 전송 중단
                }
            }

            let formData = {
                seq: exhibitorSeq,
                maritimeExhibitionYn: participationChoice,
                maritimeExhibitionSea: isSeaChecked,
                maritimeExhibitionLand: isLandChecked
            };

            let resData = ajaxConnect('/apply/step/updateExhibitorNewMaritime.do', 'post', formData);

            //console.log(resData);
            if(resData.resultCode === "0") {

                /* 등록 성공 시 다음 단계로 이동 */
                Swal.fire({
                    icon: 'info',
                    title: '[ 해상전시회 신청 ]',
                    html: '<span style="font-size: 1.2em;">해상전시회 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        /* 등록 성공 시 다음 단계로 이동 */
                        f_page_move('/apply/step2_9.do', exhibitorSeq);
                    }
                });
            }else{
                showMessage('', 'error', '[ 해상전시회 신청 ]', '해상전시회 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
            }

        } else if (result.isDismissed) {
            f_page_move('/apply/step2_9.do', exhibitorSeq);
        }

    })
}
/* //////////////////////////////// end:step_2_10 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_9 ////////////////////////////////// */
function step_2_9_check(exhibitorSeq){

    Swal.fire({
        icon: 'info',
        title: '[ 요트/보트 출품 정보 ]',
        html: '<span style="font-size: 1.2em;"><br><span style="background-color:#00a8ff; color:#ffffff;">지금 계속하기</span> 클릭 시 내용 저장 후<br>다음 페이지로 넘어갑니다.<br><br><span style="background-color:#A1A5B7; color:#ffffff;">나중에 하기</span> 클릭 시 다음 페이지로 넘어가며,<br>언제든 로그인하여 이어서 작성할 수 있습니다.</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '지금 계속하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '나중에 하기',
        reverseButtons: true
    }).then((result) => {

        if (result.isConfirmed) {

            /******************** 요트/보트 출품 정보 ********************/
            let productList_json_arr = [];

            let boatEntryYn = nvl($('input[type=radio][name=boatEntryYn]:checked').val(),'N');
            if(boatEntryYn === 'Y') {
                /*showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '요트/보트 출품 여부 항목을 선택해 주세요.', '');
                return false;*/

                // 제품분류(대)
                let product_option_big_el = $('select[name=productOptionBig]');
                let product_option_big_len = product_option_big_el.length;
                let product_option_big_flag = true;
                for (let i = 0; i < product_option_big_len; i++) {
                    if (product_option_big_el.eq(i).val() === '선택' || product_option_big_el.eq(i).val() === '선택') {
                        if ($('.preOptionList').length === 0) {
                            product_option_big_flag = false;
                            break;
                        }
                    }
                }
                if (!product_option_big_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품 분류(품목) 첫 번째 항목을 선택해 주세요.', '');
                    return false;
                }

                // 제품분류(소)
                let product_option_small_el = $('select[name=productOptionSmall]');
                let product_option_small_len = product_option_small_el.length;
                let product_option_small_flag = true;
                for (let i = 0; i < product_option_small_len; i++) {
                    if (product_option_small_el.eq(i).val() === '선택' || product_option_small_el.eq(i).val() === '선택') {
                        if ($('.preOptionList').length === 0) {
                            product_option_small_flag = false;
                            break;
                        }
                    }
                }
                if (!product_option_small_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품 분류(품목) 두 번째 항목을 선택해 주세요.', '');
                    return false;
                }

                // 제품명(국문)
                let product_name_ko_el = $('input[type=text][name=productNameKo]');
                let product_name_ko_len = product_name_ko_el.length;
                let product_name_ko_flag = true;
                for (let i = 0; i < product_name_ko_len; i++) {
                    if (nvl(product_name_ko_el.eq(i).val(), '') === '') {
                        product_name_ko_flag = false;
                        break;
                    }
                }
                if (!product_name_ko_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품명을 입력해 주세요.', '');
                    return false;
                }

                // 수량
                let product_qty_el = $('input[type=text][name=productQty]');
                let product_qty_len = product_qty_el.length;
                let product_qty_flag = true;
                for (let i = 0; i < product_qty_len; i++) {
                    if (nvl(product_qty_el.eq(i).val(), '') === '') {
                        product_qty_flag = false;
                        break;
                    }
                }
                if (!product_qty_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '수량을 입력해 주세요.', '');
                    return false;
                }

                // 제조사(브랜드)
                let product_brand_el = $('input[type=text][name=productBrand]');
                let product_brand_len = product_brand_el.length;
                let product_brand_flag = true;
                for (let i = 0; i < product_brand_len; i++) {
                    if (nvl(product_brand_el.eq(i).val(), '') === '') {
                        product_brand_flag = false;
                        break;
                    }
                }
                if (!product_brand_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제조사(브랜드)를 입력해 주세요.', '');
                    return false;
                }

                // 특징 유효성 검사
                let product_feature_el = $('textarea[name=productFeature]');
                let product_feature_len = product_feature_el.length;
                let product_feature_flag = true;
                for (let i = 0; i < product_feature_len; i++) {
                    if (nvl(product_feature_el.eq(i).val(), '') === '') {
                        product_feature_flag = false;
                        break;
                    }
                }
                if (!product_feature_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품 특징을 입력해 주세요.', '');
                    return false;
                }

                // 길이
                let product_length_el = $('input[type=text][name=productLength]');
                let product_length_len = product_length_el.length;
                let product_length_flag = true;
                for (let i = 0; i < product_length_len; i++) {
                    if (nvl(product_length_el.eq(i).val(), '') === '') {
                        product_length_flag = false;
                        break;
                    }
                }
                if (!product_length_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '길이(cm)를 입력해 주세요.', '');
                    return false;
                }

                // 너비
                let product_width_el = $('input[type=text][name=productWidth]');
                let product_width_len = product_width_el.length;
                let product_width_flag = true;
                for (let i = 0; i < product_width_len; i++) {
                    if (nvl(product_width_el.eq(i).val(), '') === '') {
                        product_width_flag = false;
                        break;
                    }
                }
                if (!product_width_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '너비(cm)를 입력해 주세요.', '');
                    return false;
                }

                // 높이
                let product_height_el = $('input[type=text][name=productHeight]');
                let product_height_len = product_height_el.length;
                let product_height_flag = true;
                for (let i = 0; i < product_height_len; i++) {
                    if (nvl(product_height_el.eq(i).val(), '') === '') {
                        product_height_flag = false;
                        break;
                    }
                }
                if (!product_height_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '높이(cm)를 입력해 주세요.', '');
                    return false;
                }

                // 중량
                let product_weight_el = $('input[type=text][name=productWeight]');
                let product_weight_len = product_weight_el.length;
                let product_weight_flag = true;
                for (let i = 0; i < product_weight_len; i++) {
                    if (nvl(product_weight_el.eq(i).val(), '') === '') {
                        product_weight_flag = false;
                        break;
                    }
                }
                if (!product_weight_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '중량(kg)를 입력해 주세요.', '');
                    return false;
                }

                // 소재
                let product_material_el = $('input[type=text][name=productMaterial]');
                let product_material_len = product_material_el.length;
                let product_material_flag = true;
                for (let i = 0; i < product_material_len; i++) {
                    if (nvl(product_material_el.eq(i).val(), '') === '') {
                        product_material_flag = false;
                        break;
                    }
                }
                if (!product_material_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '소재를 입력해 주세요.', '');
                    return false;
                }

                // 연식
                let product_year_el = $('input[type=text][name=productYear]');
                let product_year_len = product_year_el.length;
                let product_year_flag = true;
                for (let i = 0; i < product_year_len; i++) {
                    if (nvl(product_year_el.eq(i).val(), '') === '') {
                        product_year_flag = false;
                        break;
                    }
                }
                if (!product_year_flag) {
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '연식을 입력해 주세요.', '');
                    return false;
                }

                //전시품정보 Json Create
                let productNames = $('input[name=productNameKo]');
                let productCnt = productNames.length;
                if (productCnt > 0) {
                    for (let i = 0; i < productCnt; i++) {
                        let productOptionBig = '';
                        let productOptionSmall = '';
                        let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
                        if (nvl(selProductOptionBig, '선택') !== '선택') {
                            productOptionBig = $('select[name=productOptionBig]').eq(i).val();
                            productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
                        } else {
                            productOptionBig = $('input[type=text][name=preProductOptionBig]').eq(i).val();
                            productOptionSmall = $('input[type=text][name=preProductOptionSmall]').eq(i).val();
                        }
                        let isNewVal = $('input[name="productIsNew_' + (i + 1) + '"]:checked').val();
                        let productList_json_obj = {
                            seq: $('input[type=hidden][name=productSeq]').eq(i).val(),
                            exSeq: exhibitorSeq,
                            productOptionBig: productOptionBig,
                            productOptionSmall: productOptionSmall,
                            productIsNew: nvl(isNewVal, 'N'),
                            productNameKo: product_name_ko_el.eq(i).val(),
                            productQty: product_qty_el.eq(i).val(),
                            productBrand: product_brand_el.eq(i).val(),
                            productFeature: product_feature_el.eq(i).val(),
                            productLength: product_length_el.eq(i).val(),
                            productWidth: product_width_el.eq(i).val(),
                            productHeight: product_height_el.eq(i).val(),
                            productWeight: product_weight_el.eq(i).val(),
                            productMaterial: product_material_el.eq(i).val(),
                            productYear: product_year_el.eq(i).val(),
                            note: (i + 1)
                        };
                        productList_json_arr.push(productList_json_obj);
                    }
                }
            }else{
                productList_json_arr = [];
            }

            if(nvl(exhibitorSeq,'') !== ''){

                let exhibitor_json_obj = {
                    seq: exhibitorSeq,
                    boatEntryYn: boatEntryYn,
                    productList: productList_json_arr
                };
                let resData = ajaxConnect('/apply/step/saveProductNew.do', 'post', exhibitor_json_obj);

                if(resData.resultCode === "0") {

                    let timerInterval;
                    Swal.fire({
                        title: "정보 저장 중",
                        html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해 주세요.",
                        allowOutsideClick: false,
                        timer: 2500,
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
                                icon: 'info',
                                title: '[ 요트/보트 출품 정보 ]',
                                html: '<span style="font-size: 1.2em;"> 요트/보트 출품 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                                allowOutsideClick: false,
                                confirmButtonColor: '#00a8ff',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_page_move('/apply/step2_2.do', exhibitorSeq);
                                }
                            })
                        }
                    });
                }else{
                    showMessage('', 'error', '[ 요트/보트 출품 정보 ]', ' 요트/보트 출품 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
                }
            }

        } else if (result.isDismissed) {
            f_page_move('/apply/step2_2.do', exhibitorSeq);
        }

    })
}
/* //////////////////////////////// end:step_2_9 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_2 ////////////////////////////////// */
function step_2_2_check(exhibitorSeq){

    Swal.fire({
        icon: 'info',
        title: '[ 상호간판 신청 ]',
        html: '<span style="font-size: 1.2em;"><br><span style="background-color:#00a8ff; color:#ffffff;">지금 계속하기</span> 클릭 시 내용 저장 후<br>다음 페이지로 넘어갑니다.<br><br><span style="background-color:#A1A5B7; color:#ffffff;">나중에 하기</span> 클릭 시 다음 페이지로 넘어가며,<br>언제든 로그인하여 이어서 작성할 수 있습니다.</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '지금 계속하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '나중에 하기',
        reverseButtons: true
    }).then((result) => {

        if (result.isConfirmed) {

            let companySignNameKo = '';
            let companySignNameEn = '';
            let assemblyBoothCnt = $('#assemblyBoothCnt').val();
            if(assemblyBoothCnt > 0){
                // 상호간판 신청 - 상호간판명 (국문)
                companySignNameKo = $('#companySignNameKo').val();
                if(nvl(companySignNameKo,'') === ''){
                    showMessage('#companySignNameKo', 'error', '[ 상호간판 신청 ]', '조립부스 참가업체는 상호간판명(국문)을 필수 입력해 주세요.', '');
                    return false;
                }

                // 상호간판 신청 - 상호간판명 (영문)
                companySignNameEn = $('#companySignNameEn').val();
                if(nvl(companySignNameEn,'') === ''){
                    showMessage('#companySignNameEn', 'error', '[ 상호간판 신청 ]', '조립부스 참가업체는 상호간판명(영문)을 필수 입력해 주세요.', '');
                    return false;
                }
            }

            let sign_json_obj = {
                seq: exhibitorSeq,
                companySignNameKo: companySignNameKo,
                companySignNameEn: companySignNameEn
            }

            let resData = ajaxConnect('/apply/step/updateExhibitorNewCompanySign.do', 'post', sign_json_obj);

            //console.log(resData);
            if(resData.resultCode === "0") {

                /* 등록 성공 시 다음 단계로 이동 */
                Swal.fire({
                    icon: 'info',
                    title: '[ 상호간판 신청 ]',
                    html: '<span style="font-size: 1.2em;">상호간판 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        /* 등록 성공 시 다음 단계로 이동 */
                        f_page_move('/apply/step2_3.do', exhibitorSeq);
                    }
                });
            }else{
                showMessage('', 'error', '[ 상호간판 신청 ]', '상호간판 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
            }

        } else if (result.isDismissed) {
            f_page_move('/apply/step2_3.do', exhibitorSeq);
        }

    })
}
/* //////////////////////////////// end:step_2_2 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_3 ////////////////////////////////// */
function step_2_3_check(exhibitorSeq){

    Swal.fire({
        icon: 'info',
        title: '[ 유틸리티 신청 ]',
        html: '<span style="font-size: 1.2em;"><br><span style="background-color:#00a8ff; color:#ffffff;">지금 계속하기</span> 클릭 시 내용 저장 후<br>다음 페이지로 넘어갑니다.<br><br><span style="background-color:#A1A5B7; color:#ffffff;">나중에 하기</span> 클릭 시 다음 페이지로 넘어가며,<br>언제든 로그인하여 이어서 작성할 수 있습니다.</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '지금 계속하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '나중에 하기',
        reverseButtons: true
    }).then((result) => {

        if (result.isConfirmed) {

            // --- 1. 유틸리티 신청정보 수집 ---
            let utility_jugan_cnt = parseInt($('#utility_jugan_cnt').val()) || 0;
            let utility_jugan_fee = parseInt(wonToInt($('#utility_jugan_fee').val())) || 0;
            let utility_day_cnt = parseInt($('#utility_day_cnt').val()) || 0;
            let utility_day_fee = parseInt(wonToInt($('#utility_day_fee').val())) || 0;
            let utility_work_cnt = parseInt($('#utility_work_cnt').val()) || 0;
            let utility_work_fee = parseInt(wonToInt($('#utility_work_fee').val())) || 0;
            let utility_compressed_air_cnt = parseInt($('#utility_compressed_air_cnt').val()) || 0;
            let utility_compressed_air_fee = parseInt(wonToInt($('#utility_compressed_air_fee').val())) || 0;
            let utility_water_basic_cnt = parseInt($('#utility_water_basic_cnt').val()) || 0;
            let utility_water_basic_fee = parseInt(wonToInt($('#utility_water_basic_fee').val())) || 0;
            let utility_internet_cnt = parseInt($('#utility_internet_cnt').val()) || 0;
            let utility_internet_fee = parseInt(wonToInt($('#utility_internet_fee').val())) || 0;
            let utility_pytex_new_cnt = parseInt($('#utility_pytex_new_cnt').val()) || 0;
            let utility_pytex_new_fee = parseInt(wonToInt($('#utility_pytex_new_fee').val())) || 0;
            let utility_pytex_re_cnt = parseInt($('#utility_pytex_re_cnt').val()) || 0;
            let utility_pytex_re_fee = parseInt(wonToInt($('#utility_pytex_re_fee').val())) || 0;
            let utility_barcode_cnt = parseInt($('#utility_barcode_cnt').val()) || 0;
            let utility_barcode_fee = parseInt(wonToInt($('#utility_barcode_fee').val())) || 0;

            // 유틸리티 총액을 화면이 아닌, 각 항목의 합계로 직접 계산
            const utilityPrcSum = utility_jugan_fee + utility_day_fee + utility_work_fee + utility_compressed_air_fee +
                utility_water_basic_fee + utility_internet_fee + utility_pytex_new_fee +
                utility_pytex_re_fee + utility_barcode_fee;

            let utility_json_obj = {
                seq: exhibitorSeq,
                utilityJuganCnt: utility_jugan_cnt,
                utilityJuganFee: utility_jugan_fee,
                utilityDayCnt: utility_day_cnt,
                utilityDayFee: utility_day_fee,
                utilityWorkCnt: utility_work_cnt,
                utilityWorkFee: utility_work_fee,
                utilityCompressedAirCnt: utility_compressed_air_cnt,
                utilityCompressedAirFee: utility_compressed_air_fee,
                utilityWaterBasicCnt: utility_water_basic_cnt,
                utilityWaterBasicFee: utility_water_basic_fee,
                utilityInternetCnt: utility_internet_cnt,
                utilityInternetFee: utility_internet_fee,
                utilityPytexNewCnt: utility_pytex_new_cnt,
                utilityPytexNewFee: utility_pytex_new_fee,
                utilityPytexReCnt: utility_pytex_re_cnt,
                utilityPytexReFee: utility_pytex_re_fee,
                utilityBarcodeCnt: utility_barcode_cnt,
                utilityBarcodeFee: utility_barcode_fee,
                utilityPrcSum: utilityPrcSum/*,
                prcSum: prcSum,
                prcVat: prcVat,
                prcTotal: prcTotal*/
            }

            let resData = ajaxConnect('/apply/step/updateExhibitorNewUtility.do', 'post', utility_json_obj);

            //console.log(resData);
            if(resData.resultCode === "0") {

                /* 등록 성공 시 다음 단계로 이동 */
                Swal.fire({
                    icon: 'info',
                    title: '[ 유틸리티 신청 ]',
                    html: '<span style="font-size: 1.2em;">유틸리티 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        /* 등록 성공 시 다음 단계로 이동 */
                        f_page_move('/apply/step2_4.do', exhibitorSeq);
                    }
                });
            }else{
                showMessage('', 'error', '[ 유틸리티 신청 ]', '유틸리티 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
            }

        } else if (result.isDismissed) {
            f_page_move('/apply/step2_4.do', exhibitorSeq);
        }

    })
}

/* //////////////////////////////// end:step_2_3 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_4 ////////////////////////////////// */

function step_2_4_check(exhibitorSeq){

    Swal.fire({
        icon: 'info',
        title: '[ 출입증 정보 ]',
        html: '<span style="font-size: 1.2em;"><br><span style="background-color:#00a8ff; color:#ffffff;">지금 계속하기</span> 클릭 시 내용 저장 후<br>다음 페이지로 넘어갑니다.<br><br><span style="background-color:#A1A5B7; color:#ffffff;">나중에 하기</span> 클릭 시 다음 페이지로 넘어가며,<br>언제든 로그인하여 이어서 작성할 수 있습니다.</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '지금 계속하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '나중에 하기',
        reverseButtons: true
    }).then((result) => {

        if (result.isConfirmed) {

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
                showMessage('', 'error', '[ 출입증 신청 ]', '성명(국문)을 입력해 주세요.', '');
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
                showMessage('', 'error', '[ 출입증 신청 ]', '성명(영문) 이름을 입력해 주세요.', '');
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
                showMessage('', 'error', '[ 출입증 신청 ]', '성명(영문) 성을 입력해 주세요.', '');
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
                showMessage('', 'error', '[ 출입증 신청 ]', '직책(국문)을 입력해 주세요.', '');
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
                showMessage('', 'error', '[ 출입증 신청 ]', '직책(영문)을 입력해 주세요.', '');
                return false;
            }

            // 출입증비고
            let pass_note_el = document.querySelectorAll('input[type=text][name=pass_note]');

            let pass_json_arr = [];
            if(pass_name_len > 0){
                for(let i=0; i<pass_name_len; i++){
                    let pass_json_obj = {
                        seq: $('input[type=hidden][name=passSeq]').eq(i).val(),
                        exSeq: exhibitorSeq,
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
                passList: pass_json_arr
            };

            let resData = ajaxConnect('/apply/step/insertPassNew.do', 'post', passList_json_arr);

            //console.log(resData);
            if(resData.resultCode === "0") {

                /* 등록 성공 시 다음 단계로 이동 */
                Swal.fire({
                    icon: 'info',
                    title: '[ 출입증 신청 ]',
                    html: '<span style="font-size: 1.2em;">출입증 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        /* 등록 성공 시 다음 단계로 이동 */
                        f_page_move('/apply/step2_5.do', exhibitorSeq);
                    }
                });
            }else{
                showMessage('', 'error', '[ 출입증 신청 ]', '출입증 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
            }

        } else if (result.isDismissed) {
            f_page_move('/apply/step2_5.do', exhibitorSeq);
        }

    })
}

/* //////////////////////////////// end:step_2_4 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_5 ////////////////////////////////// */
// 1. 페이지 로드 시 '미신청' 상태면 등록 버튼과 리스트를 숨김 처리
$(function() {
    if($('input[name="giftApplyYn"]:checked').val() === 'N') {
        $('#gift_add_btn').hide();
        $('.form_chuga_list').hide();
    }
});

// 2. 라디오 버튼 변경 이벤트
$(document).on('change', 'input[name="giftApplyYn"]', function() {
    let applyYn = $(this).val();

    if (applyYn === 'N') {
        // 기존에 등록된 경품(화면 리스트)이나 방금 추가한 경품(배열)이 있는 경우 경고
        if (gift_add_json_arr.length > 0 || $('.form_chuga_list').length > 0) {
            Swal.fire({
                title: '미신청으로 변경 시 기존에 작성/등록된 경품 정보가 모두 삭제됩니다.\n변경하시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                confirmButtonText: '변경 및 삭제',
                cancelButtonColor: '#A1A5B7',
                cancelButtonText: '취소',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) {
                    $('#gift_add_btn').hide();
                    $('.form_chuga_list').remove();
                    gift_add_json_arr = [];       // 방금 추가한 배열 데이터 초기화
                } else {
                    // 취소 시 다시 '신청(Y)' 라디오 버튼으로 원복
                    $('input[name="giftApplyYn"][value="Y"]').prop('checked', true);
                }
            });
        } else {
            // 등록된 데이터가 없으면 바로 숨김
            $('#gift_add_btn').hide();
        }
    } else {
        // 신청(Y) 선택 시 다시 노출
        $('#gift_add_btn').show();
        $('.form_chuga_list').show();
    }
});

function step_2_5_check(exhibitorSeq){

    let giftApplyYn = $('input[name="giftApplyYn"]:checked').val() || 'N';

    // '신청(Y)'인데 등록된 경품이 단 1개도 없는 경우 진행 차단
    if (giftApplyYn === 'Y' && gift_add_json_arr.length === 0 && $('.form_chuga_list').length === 0) {
        showMessage('', 'error', '[ 경품제공 신청 ]', '신청 시 최소 1개 이상의 경품을 등록해 주세요.', '');
        return false;
    }

    Swal.fire({
        icon: 'info',
        title: '[ 경품제공 신청 ]',
        html: '<span style="font-size: 1.2em;"><br><span style="background-color:#00a8ff; color:#ffffff;">지금 계속하기</span> 클릭 시 내용 저장 후<br>다음 페이지로 넘어갑니다.<br><br><span style="background-color:#A1A5B7; color:#ffffff;">나중에 하기</span> 클릭 시 다음 페이지로 넘어가며,<br>언제든 로그인하여 이어서 작성할 수 있습니다.</span>',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '지금 계속하기',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '나중에 하기',
        reverseButtons: true
    }).then((result) => {

        if (result.isConfirmed) {

            // 라디오버튼 상태(giftApplyYn)를 서버로 함께 전송
            let giftList_json_obj = {
                seq: exhibitorSeq,
                giftApplyYn: giftApplyYn,
                giftList: gift_add_json_arr
            };

            let resData = ajaxConnect('/apply/step/insertGiftNew.do', 'post', giftList_json_obj);

            //console.log(resData);
            if(resData.resultCode === "0") {

                gift_add_json_arr = [];

                /* 등록 성공 시 다음 단계로 이동 */
                Swal.fire({
                    icon: 'info',
                    title: '[ 경품제공 신청 ]',
                    html: '<span style="font-size: 1.2em;">경품제공 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        /* 등록 성공 시 다음 단계로 이동 */
                        f_page_move('/apply/step2_8.do', exhibitorSeq);
                    }
                });
            }else{
                showMessage('', 'error', '[ 경품제공 신청 ]', '경품제공 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
            }

        } else if (result.isDismissed) {
            f_page_move('/apply/step2_8.do', exhibitorSeq);
        }

    })
}

let gift_add_json_arr = [];
function f_gift_add(exSeq){

    // 구분
    let gift_gbn = $('#gift_gbn').val();

    // 수량
    let gift_cnt = $('#gift_cnt').val();
    if(gift_cnt === ''){
        showMessage('', 'error', '[ 경품제공 신청 ]', '수량을 입력해 주세요.', '');
        return false;
    }

    // 분류
    let gift_classify = $('#gift_classify').val();

    // 품목명
    let gift_name = $('#gift_name').val();
    if(gift_name === ''){
        showMessage('', 'error', '[ 경품제공 신청 ]', '품목명을 입력해 주세요.', '');
        return false;
    }

    // 경품설명
    let gift_content = $('#gift_content').val();
    if(gift_content === ''){
        showMessage('', 'error', '[ 경품제공 신청 ]', '경품설명을 입력해 주세요.', '');
        return false;
    }

    // 경품사진
    let gift_photo = 'giftPhoto'.concat($('div.form_chuga_list').length+1);

    // 회사로고
    let gift_company_logo = 'giftCompanyLogo'.concat($('div.form_chuga_list').length+1);

    // 소비자가
    let gift_price = $('#gift_price').val();
    if(gift_price === ''){
        showMessage('', 'error', '[ 경품제공 신청 ]', '소비자가를 입력해 주세요.', '');
        return false;
    }

    // 협찬가
    let gift_sponsor_price = $('#gift_sponsor_price').val();

    // 비고
    let gift_note = $('#gift_note').val();

    /* body 에 팝업 데이터를 포함한 Element 생성 */
    let gift_add_btn = $('#gift_add_btn');

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

    let exhibitorSeq = exSeq;

    /* 파일 업로드 */
    f_gift_file_upload_call(exhibitorSeq, exhibitorSeq);

    /* json Array 에 push */
    let gift_add_json_obj = {
        exSeq: exhibitorSeq,
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
                        giftFullFilePath = giftFullFilePath.toString().replace('/usr/local/tomcat/webapps', '');
                    }

                    let giftFolderPath = file_resData[i].folderPath;
                    let giftFullFileName = file_resData[i].fullFileName;
                    let giftFileName = file_resData[i].fileName;

                    let fileBoxStr = '<div class="file_list" style="margin-top: 10px;">';
                    fileBoxStr += '<img src="' + giftFullFilePath + '" class="mr10" style="border: 1px solid #009ef7; margin: 0 5px 0 0; max-width: 150px;"/>';
                    /*fileBoxStr += '<a href="/file/download.do?path=exhibitor/company/' + giftFolderPath + '&fileName=' + giftFullFileName + '">';*/
                    fileBoxStr += '<a href="javascript:void(0);" onClick="f_file_download(\'exhibitor/company/' + giftFolderPath + '\', \'' + giftFullFileName + '\')">';
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
    document.querySelector('#gift_cnt').value = 0;
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
    document.querySelector('#gift_price').value = 0;
    // 협찬가
    document.querySelector('#gift_sponsor_price').value = 0;
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
            title: '참가업체 정보',
            html: '웹배너 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                window.location.href = '/apply/step2_8.do';
            }
        });
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '웹배너 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
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
                    licenseFullFilePath = licenseFullFilePath.toString().replace('/usr/local/tomcat/webapps', '');
                }

                let licenseFolderPath = file_resData.folderPath;
                let licenseFullFileName = file_resData.fullFileName;
                let licenseFileName = file_resData.fileName;

                $('div.file_box').empty(); //비우기
                let fileBoxStr = '<div class="file_list" style="margin-top: 10px; display: flex; align-items: center;">';
                    fileBoxStr += '<img src="' + licenseFullFilePath + '" class="mr10" style="border: 1px solid #009ef7; margin: 0 5px 0 0; max-width: 150px;"/>';
                    /*fileBoxStr += '<a href="/file/download.do?path=exhibitor/company/' + licenseFolderPath + '&fileName=' + licenseFullFileName + '">';*/
                    fileBoxStr += '<a href="javascript:void(0);" onClick="f_file_download(\'exhibitor/company/' + licenseFolderPath + '\', \'' + licenseFullFileName + '\')">';
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
            html: '입력정보 불러오기를 실패하였습니다.<br>직접 입력하시거나 , 로그인 후 마이페이지에서 진행해 주세요.',
            icon: 'info',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        });
    }
}

function step_2_8_check(exhibitorSeq){

    /*// 회사명
    let company_name_ko = document.querySelector('#company_name_ko').value;
    if(company_name_ko === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }
    let company_name_en = document.querySelector('#company_name_en').value;
    if(company_name_en === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 주소
    let company_address = document.querySelector('#company_address').value;
    if(company_address === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '주소를 입력해 주세요.', '');
        return false;
    }
    let company_address_detail = document.querySelector('#company_address_detail').value;
    if(company_address_detail === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '상세주소를 입력해 주세요.', '');
        return false;
    }

    // 대표자
    let company_ceo = document.querySelector('#company_ceo').value;
    if(company_ceo === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '대표자명을 입력해 주세요.', '');
        return false;
    }

    // 전화
    let company_tel = document.querySelector('#company_tel').value;
    if(company_tel === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 홈페이지
    let company_homepage = document.querySelector('#company_homepage').value;

    // Fax
    let company_fax = document.querySelector('#company_fax').value;

    // 사업자등록증
    if($('.file_box img').length === 0){
        showMessage('', 'error', '[ 참가업체 정보 ]', '기본정보 탭에서 사업자등록증을 등록해 주세요.', '');
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
        showMessage('', 'error', '[담당자 정보]', '휴대전화를 입력해 주세요.<br>또는 올바른 휴대전화번호 형식으로 입력해 주세요.(010 만 가능)', '');
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
        exSeq: exhibitorSeq
    };
    let resData = ajaxConnect('/apply/step/insertDirectory.do', 'post', directory_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 디렉토리 정보 ]',
            html: '<span style="font-size: 1.2em;">디렉토리 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/apply/step03.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 디렉토리 정보 ]', '디렉토리 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

/* //////////////////////////////// end:step_2_8 ////////////////////////////////// */

/* //////////////////////////////// begin:step_03 ////////////////////////////////// */
function step_03_check(exhibitorSeq){

    /*home();*/

    let apply_comp_json_obj = {
        seq: exhibitorSeq,
        approvalStatus: '승인요청',
        applyComplt: 'Y'
    }
    let resData = ajaxConnect('/apply/step/updateExhibitorNewStatus.do', 'post', apply_comp_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 참가업체 정보 ]',
            html: '<span style="font-size: 1.2em;">참가 신청이 정상 완료되었습니다.<br>등록하신 담당자 메일로 신청완료 및 안내 메일이 발송됩니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {

                let exhibitor_new_jsonObj = {
                    seq: exhibitorSeq
                }
                let emailArr = ajaxConnectSimple('/getExhibitorNewEmailList.do','post', exhibitor_new_jsonObj);
                let mailResultFlag = false;
                for(let j=0; j<emailArr.length; j++){
                    let email = emailArr[j];
                    let jsonObj = {
                        subject: '[2027 경기국제보트쇼] 참가업체 접수 완료', //제목
                        body: "", //본문
                        template: "6", //템플릿 번호
                        receiver: [{email: email}]
                    }
                    ajaxConnect('/mail/send.do', 'post', jsonObj);

                    if(j === (emailArr.length-1)){
                        mailResultFlag = true;
                    }
                }
                if (mailResultFlag) {
                    /* 등록 성공 시 다음 단계로 이동 */
                    home('ko');
                }
            }
        })
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '참가 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
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
        case "2_10":
            my_step_2_10_check(seq);
            break;
        case "2_9":
            my_step_2_9_check(seq);
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

    // 비밀번호
    let password = $('#password').val();
    let passwordCheck = $('#passwordCheck').val();
    if(password === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '비밀번호를 입력해 주세요.', '');
        return false;
    }

    // 비밀번호 유효성
    let pwCheck = $('#pwCheck').val();
    if(pwCheck === 'false'){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '비밀번호 유효성 검사를 수행해 주세요.', '');
        return false;
    }

    // 비밀번호 확인 유효성
    let pwConfirmCheck = $('#pwConfirmCheck').val();
    if(pwConfirmCheck === 'false'){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '비밀번호 확인을 입력해 주세요.', '');
        return false;
    }

    // 성명
    let name = $('#name').val();
    if(name === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '성명을 입력해 주세요.', '');
        return false;
    }

    // 직위
    let position = $('#position').val();
    if(position === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '직위를 입력해 주세요.', '');
        return false;
    }

    // 부서
    let depart = $('#depart').val();

    // 전화번호
    let tel = $('#tel').val();
    if(tel === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 휴대전화
    let phone = $('#phone').val();
    if(phone === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '휴대전화번호를 입력해 주세요.', '');
        return false;
    }

    // 이메일
    let email_input1 = $('#email_input1').val();
    let email_input2 = $('#email_input2').val();
    if(email_input1 === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '이메일을 입력해 주세요.', '');
        return false;
    }
    if(email_input2 === ''){
        showMessage('', 'error', '[ 회원 계정 정보 ]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    let jsonObj = {
        seq: exhibitorSeq,
        password: password,
        name: name,
        position: position,
        depart: depart,
        tel: tel,
        phone: phone,
        email: email_input1 + '@' + email_input2
    };

    let resData = ajaxConnect('/mypage/step/exhibitorNew/updateInfo.do','post',jsonObj);

    if(resData.resultCode === "0") {

            Swal.fire({
                icon: 'info',
                title: '[ 회원 계정 정보 ]',
                html: '<span style="font-size: 1.2em;">회원 계정 정보가 저장되었습니다.</span>',
                allowOutsideClick: false,
                confirmButtonColor: '#00a8ff',
                confirmButtonText: '확인'
            }).then((result) => {
                if (result.isConfirmed) {
                    f_page_move('/mypage/modify.do', exhibitorSeq);
                }
            });
            
    }else{
        showMessage('', 'error', '[ 회원 계정 정보 ]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }

}

async function my_step_01_check(exhibitorSeq){

    /******************** 참가업체 정보 ********************/
    //ID
    let id = $('#id').val();

    // 사업자등록번호
    let companyLicenseNum = $('#companyLicenseNum').val();
    if(nvl(companyLicenseNum,'') === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '사업자등록번호를 입력해 주세요.', '');
        return false;
    }

    // 회사명
    let companyNameKo = $('#companyNameKo').val();
    if(nvl(companyNameKo,'') === ''){
        showMessage('#companyNameKo', 'error', '[ 참가업체 정보 ]', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }
    let companyNameEn = $('#companyNameEn').val();
    if(nvl(companyNameEn,'') === ''){
        showMessage('#companyNameEn', 'error', '[ 참가업체 정보 ]', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    // 본사 주소
    let companyAddress = $('#companyAddress').val();
    if(nvl(companyAddress,'') === ''){
        showMessage('#companyAddress', 'error', '[ 참가업체 정보 ]', '본사 주소를 입력해 주세요.', '');
        return false;
    }
    let companyAddressDetail = $('#companyAddressDetail').val();
    if(nvl(companyAddressDetail,'') === ''){
        showMessage('#companyAddressDetail', 'error', '[ 참가업체 정보 ]', '본사 상세 주소를 입력해 주세요.', '');
        return false;
    }

    // 공장 주소
    let factoryAddress = $('#factoryAddress').val();
    let factoryAddressDetail = $('#factoryAddressDetail').val();

    // 대표자
    let companyCeo = $('#companyCeo').val();
    if(nvl(companyCeo,'') === ''){
        showMessage('#companyCeo', 'error', '[ 참가업체 정보 ]', '대표자명을 입력해 주세요.', '');
        return false;
    }

    // 전화
    let companyTel = $('#companyTel').val();
    if(nvl(companyTel,'') === ''){
        showMessage('#companyTel', 'error', '[ 참가업체 정보 ]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 홈페이지
    let companyHomepage = $('#companyHomepage').val();
    if(nvl(companyHomepage,'') !== ''){
        let no_companyHomepage = $('input[type=checkbox][name=noPage]').is(':checked');
        if(no_companyHomepage === false){
            if(!checkUrl(companyHomepage)){
                showMessage('#companyHomepage', 'error', '[ 참가업체 정보 ]', '홈페이지 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
                return false;
            }
        }
    }else{
        let no_companyHomepage = $('input[type=checkbox][name=noPage]').is(':checked');
        if(no_companyHomepage === false){
            showMessage('#companyHomepage', 'error', '[ 참가업체 정보 ]', '홈페이지가 없다면 \"홈페이지 없음\" 에 체크해 주세요.', '');
            return false;
        }
    }

    // Fax
    let companyFax = $('#companyFax').val();

    // 산업분류
    let industryPart = $('#industryPart').val();
    let industryPartEtc = $('#industryPartEtc').val();
    if(nvl(industryPart,'') === ''){
        showMessage('', 'error', '[ 참가업체 정보 ]', '산업 분류 항목을 선택해 주세요.', '');
        return false;
    }else{
        if(industryPart.includes('기타')){
            if(industryPartEtc === ''){
                showMessage('#industryPartEtc', 'error', '[ 참가업체 정보 ]', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    // 사업자등록증
    let companyLicenseFile_li = $('.companyLicenseFile_li').length;
    if(companyLicenseFile_li === 0){
        let companyLicense = $('#companyLicense').val();
        if (nvl(companyLicense,'') === '') {
            showMessage('', 'info', '[ 참가업체 정보 ]', '사업자등록증을 첨부해 주세요.', '');
            return false;
        }
    }

    // 로고
    let logoFile_li = $('.logoFile_li').length;
    if(logoFile_li === 0){
        let logo = $('#logo').val();
        if (nvl(logo,'') === '') {
            showMessage('', 'error', '[ 참가업체 정보 ]', '로고 파일을 업로드해 주세요.', '');
            return false;
        }
    }

    // 기참가연도
    let prePartYear = '';
    let prePartYearChecked = $("input[type='checkbox'][name='prePartYear']:checked");
    let prePartYearCheckedLength = prePartYearChecked.length;
    if(prePartYearCheckedLength > 0){
        for(let i=0; i<prePartYearCheckedLength; i++){
            prePartYear += prePartYearChecked.eq(i).val();
            if((i+1) !== prePartYearCheckedLength){
                prePartYear += '^';
            }
        }
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '기참가연도를 선택해 주세요.', '');
        return false;
    }

    // 한국해양레저산업협회 회원사 여부
    let memberCompanyYn = $("input[type='radio'][name='memberCompanyYn']:checked").val();

    /******************** 담당자 정보 ********************/
    /******************** 대표담당자 정보 ********************/

    // 성명
    let name = $('#name').val();
    if(nvl(name,'') === ''){
        showMessage('#name', 'error', '[ 담당자 정보 ]', '성명을 입력해 주세요.', '');
        return false;
    }

    // 직위
    let position = $('#position').val();
    if(nvl(position,'') === ''){
        showMessage('#position', 'error', '[ 담당자 정보 ]', '직위를 입력해 주세요.', '');
        return false;
    }

    // 부서
    let depart = $('#depart').val();

    // 전화번호
    let tel = $('#tel').val();
    if(nvl(tel,'') === ''){
        showMessage('#tel', 'error', '[ 담당자 정보 ]', '전화번호를 입력해 주세요.', '');
        return false;
    }

    // 휴대전화
    let phone = $('#phone').val();
    if(nvl(phone,'') === ''){
        showMessage('#phone', 'error', '[ 담당자 정보 ]', '휴대전화번호를 입력해 주세요.', '');
        return false;
    }else{
        if ( !/^010-[0-9]{4}-[0-9]{4}$/.test( phone ) ) {
            showMessage('#phone', 'error', '[ 담당자 정보 ]', '올바른 휴대전화번호를 입력해 주세요.<br>(앞자리 010 만 가능합니다.)', '');
            return false;
        }
    }

    // 이메일
    let email1 = $('#email1').val();
    let email2 = $('#email2').val();
    if(nvl(email1,'') === ''){
        showMessage('#email1', 'error', '[ 담당자 정보 ]', '이메일을 입력해 주세요.', '');
        return false;
    }
    if(nvl(email2,'') === ''){
        showMessage('', 'error', '[ 담당자 정보 ]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    /******************** 담당자 정보 ********************/
    /******************** 부담당자 정보 ********************/

        // 담당자 성명
    let charge_person_name_el = $('input[type=text][name=chargePersonName]');
    /*let charge_person_name_flag = true;
    for(let i=0; i<charge_person_len; i++){
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
        showMessage('', 'error', '[담당자 정보]', '휴대전화를 입력해 주세요.<br>또는 올바른 휴대전화번호 형식으로 입력해 주세요.(010 만 가능)', '');
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
    let chargePersonCnt = Number.parseInt($('.managerInfoNum:last').text());
    if(chargePersonCnt > 0){
        for(let i=0; i<chargePersonCnt; i++){
            let chargePersonName = charge_person_name_el.eq(i).val();
            if(nvl(chargePersonName,'') !== '') {
                let chargePersonEmail_val = charge_person_email_el.eq(i).val();
                let chargePersonEmail = '';
                if (nvl(chargePersonEmail_val, '') !== '') {
                    chargePersonEmail = chargePersonEmail_val + '@' + charge_person_domain_el.eq(i).val();
                }
                let chargePersonList_json_obj = {
                    seq: $('input[type=hidden][name=chargeSeq]').eq(i).val(),
                    exSeq: exhibitorSeq,
                    chargePersonName: chargePersonName,
                    chargePersonPosition: charge_person_position_el.eq(i).val(),
                    chargePersonDepart: charge_person_depart_el.eq(i).val(),
                    chargePersonTel: charge_person_tel_el.eq(i).val(),
                    chargePersonPhone: charge_person_phone_el.eq(i).val(),
                    chargePersonEmail: chargePersonEmail,
                };
                chargePersonList_json_arr.push(chargePersonList_json_obj);
            }
        }
    }

    // 참가행사 및 분야
    let fieldParticipatory = $('input[type=radio][name=fieldParticipatory]:checked').val();
    let fieldParticipatory1 = '';
    let fieldParticipatory2 = '';
    let fieldParticipatory3 = '';
    if(nvl(fieldParticipatory,'') === ''){
        showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가행사 및 분야를 선택해 주세요.', '');
        return false;
    }else{
        fieldParticipatory1 = $('select[name=fieldParticipatory1]').val();
        if(nvl(fieldParticipatory1,'') === ''){
            showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가분야 1순위를 선택해 주세요.', '');
            return false;
        }

        fieldParticipatory2 = $('select[name=fieldParticipatory2]').val();
        /*if(nvl(fieldParticipatory2,'') === ''){
            showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가분야 2순위를 선택해 주세요.', '');
            return false;
        }*/

        fieldParticipatory3 = $('select[name=fieldParticipatory3]').val();
        /*if(nvl(fieldParticipatory3,'') === ''){
            showMessage('', 'error', '[ 참가행사 및 분야 ]', '참가분야 3순위를 선택해 주세요.', '');
            return false;
        }*/
    }
    //console.log(field_part);

    /******************** 상세정보 ********************/

    // 회사소개영상
    let companyIntroVideo = $('#companyIntroVideo').val();
    if(nvl(companyIntroVideo,'') !== ''){
        if(!checkUrl(companyIntroVideo)){
            showMessage('#companyIntroVideo', 'error', '[ 상세정보 ]', '영상 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
            return false;
        }
    }

    // 회사소개
    let companyIntroKo = $('#companyIntroKo').val();
    if(nvl(companyIntroKo,'') === ''){
        showMessage('#companyIntroKo', 'error', '[ 상세정보 ]', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }
    let companyIntroEn = $('#companyIntroEn').val();
    if(nvl(companyIntroEn,'') === ''){
        showMessage('#companyIntroEn', 'error', '[ 상세정보 ]', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    // KIBS 참가목적
    let companyPurposeKo = $('#companyPurposeKo').val();
    if(nvl(companyPurposeKo,'') === ''){
        showMessage('', 'error', '[ 상세정보 ]', 'KIBS 참가목적(국문)을 선택해 주세요.', '');
        return false;
    }
    let companyPurposeEn = $('#companyPurposeEn').val();
    if(nvl(companyPurposeEn,'') === ''){
        showMessage('', 'error', '[ 상세정보 ]', 'KIBS 참가목적(영문)을 선택해 주세요.', '');
        return false;
    }

    // 신제품출품 사항 소개
    let newItemIntroKo = $('#newItemIntroKo').val();
    let newItemIntroEn = $('#newItemIntroEn').val();

    // 프로모션 정보
    let promotionPlan = $('#promotionPlan').val();

    /******************** 제품 노출 정보 ********************/

    let onlineList_json_arr = [];

    // 제품명(국문)
    let online_name_ko_el = $('input[type=text][name=onlineNameKo]');
    let online_name_ko_len = online_name_ko_el.length;
    let online_name_ko_flag = true;
    for(let i=0; i<online_name_ko_len; i++){
        if(nvl(online_name_ko_el.eq(i).val(),'') === ''){
            online_name_ko_flag = false;
            break;
        }
    }
    if(online_name_ko_flag){
        // 제품분류(대)
        let online_option_big_el = $('select[name=onlineOptionBig]');
        let online_option_big_len = online_option_big_el.length;
        let online_option_big_flag = true;
        for(let i=0; i<online_option_big_len; i++){
            if(online_option_big_el.eq(i).val() === '선택' || online_option_big_el.eq(i).val() === '선택'){
                if($('.preOptionList').length === 0){
                    online_option_big_flag = false;
                    break;
                }
            }
        }
        if(!online_option_big_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 분류 첫 번째 항목을 선택해 주세요.', '');
            return false;
        }

        // 제품분류(소)
        let online_option_small_el = $('select[name=onlineOptionSmall]');
        let online_option_small_len = online_option_small_el.length;
        let online_option_small_flag = true;
        for(let i=0; i<online_option_small_len; i++){
            if(online_option_small_el.eq(i).val() === '선택' || online_option_small_el.eq(i).val() === '선택'){
                if($('.preOptionList').length === 0) {
                    online_option_small_flag = false;
                    break;
                }
            }
        }
        if(!online_option_small_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 분류 두 번째 항목을 선택해 주세요.', '');
            return false;
        }

        // 제품명(국문)
        let online_name_ko_el = $('input[type=text][name=onlineNameKo]');
        let online_name_ko_len = online_name_ko_el.length;
        let online_name_ko_flag = true;
        for(let i=0; i<online_name_ko_len; i++){
            if(nvl(online_name_ko_el.eq(i).val(),'') === ''){
                online_name_ko_flag = false;
                break;
            }
        }
        if(!online_name_ko_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품명(국문)을 입력해 주세요.', '');
            return false;
        }

        // 제품명(영문)
        let online_name_en_el = $('input[type=text][name=onlineNameEn]');
        let online_name_en_len = online_name_en_el.length;
        let online_name_en_flag = true;
        for(let i=0; i<online_name_en_len; i++){
            if(nvl(online_name_en_el.eq(i).val(),'') === ''){
                online_name_en_flag = false;
                break;
            }
        }
        if(!online_name_en_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품사진
        let onlinePrdBox = $('.onlinePrdBox');
        for(let i=0; i<onlinePrdBox.length; i++){
            let inputFile = onlinePrdBox.eq(i).find('.upload_name').val();
            let preFileList = $('.onlineInfoBox').eq(i).find('.preValueList').find('li.onlineImageFile_li').length;
            if(nvl(inputFile,"") === "" && preFileList === 0){
                showMessage('', 'error', '[ 제품 노출 정보 ]', '제품사진을 첨부해 주세요.', '');
                return false;
            }
        }

        // 제품 설명(국문)
        let online_intro_ko_el = $('textarea[name=onlineIntroKo]');
        let online_intro_ko_len = online_intro_ko_el.length;
        let online_intro_ko_flag = true;
        for(let i=0; i<online_intro_ko_len; i++){
            if(online_intro_ko_el.eq(i).val() === ''){
                online_intro_ko_flag = false;
            }
        }
        if(!online_intro_ko_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 설명(국문)을 입력해 주세요.', '');
            return false;
        }

        // 제품 설명(영문)
        let online_intro_en_el = $('textarea[name=onlineIntroEn]');
        let online_intro_en_len = online_intro_en_el.length;
        let online_intro_en_flag = true;
        for(let i=0; i<online_intro_en_len; i++){
            if(online_intro_en_el.eq(i).val() === ''){
                online_intro_en_flag = false;
            }
        }
        if(!online_intro_en_flag){
            showMessage('', 'error', '[ 제품 노출 정보 ]', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품링크
        let online_link_el = $('input[type=text][name=onlineLink]');
        for(let i=0; i<online_link_el.length; i++){
            let online_link = online_link_el.eq(i).val();
            if(nvl(online_link,'') !== ''){
                if(!checkUrl(online_link)){
                    showMessage('', 'error', '[ 제품 노출 정보 ]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
                    return false;
                }
            }
        }

        // 길이
        let online_length_el = $('input[type=text][name=onlineLength]');

        // 너비
        let online_width_el = $('input[type=text][name=onlineWidth]');

        // 높이
        let online_height_el = $('input[type=text][name=onlineHeight]');

        // 중량
        let online_weight_el = $('input[type=text][name=onlineWeight]');

        // 소재
        let online_material_el = $('input[type=text][name=onlineMaterial]');

        // 연식
        let online_year_el = $('input[type=text][name=onlineYear]');

        let onlineCnt = Number.parseInt($('.onlineInfoNum:last').text());
        if(onlineCnt > 0){
            for(let i=0; i<onlineCnt; i++){
                let onlineOptionBig = '';
                let onlineOptionSmall = '';
                let selOnlineOptionBig = $('select[name=onlineOptionBig]').eq(i).val();
                if(nvl(selOnlineOptionBig,'선택') !== '선택'){
                    onlineOptionBig = $('select[name=onlineOptionBig]').eq(i).val();
                    onlineOptionSmall = $('select[name=onlineOptionSmall]').eq(i).val();
                }else {
                    onlineOptionBig = $('input[type=text][name=preOnlineOptionBig]').eq(i).val();
                    onlineOptionSmall = $('input[type=text][name=preOnlineOptionSmall]').eq(i).val();
                }
                let onlineList_json_obj = {
                    seq: $('input[type=hidden][name=onlineSeq]').eq(i).val(),
                    exSeq: exhibitorSeq,
                    onlineOptionBig: onlineOptionBig,
                    onlineOptionSmall: onlineOptionSmall,
                    onlineNameKo: online_name_ko_el.eq(i).val(),
                    onlineNameEn: online_name_en_el.eq(i).val(),
                    onlineIntroKo: online_intro_ko_el.eq(i).val(),
                    onlineIntroEn: online_intro_en_el.eq(i).val(),
                    onlineLink: online_link_el.eq(i).val(),
                    onlineLength: online_length_el.eq(i).val(),
                    onlineWidth: online_width_el.eq(i).val(),
                    onlineHeight: online_height_el.eq(i).val(),
                    onlineWeight: online_weight_el.eq(i).val(),
                    onlineMaterial: online_material_el.eq(i).val(),
                    onlineYear: online_year_el.eq(i).val(),
                    note: (i+1)
                };
                onlineList_json_arr.push(onlineList_json_obj);
            }
        }
    }else{
        onlineList_json_arr = [];
    }

    // 수출상담회 참가 희망 여부
    let export_meeting_yn = $('input[type=radio][name=exportMeetingYn]:checked').val();
    if(export_meeting_yn === 'N'){
        buyer_add_json_arr = [];
    }

    let buyerCompanyName_el = $('input[type=hidden][name=buyerCompanyName]');
    for(let i=0; i<buyerCompanyName_el.length; i++){
        let buyer_add_json_obj = {
            seq: $('input[type=hidden][name=buyerItemSeq]').eq(i).val(),
            exSeq: exhibitorSeq,
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

    let lang = 'KO';
    let link =  window.location.href;
    if(link.includes('eng')){
        lang = 'EN';
    }

    let exhibitor_json_obj = {
        seq: exhibitorSeq,
        lang: lang,
        id: id,
        transferYear: transferYear,
        passwordYn: 'N',
        /* 참가업체 정보 */
        boothNum: $('#boothNum').val(),
        companyLicenseNum: companyLicenseNum,
        companyNameKo: companyNameKo,
        companyNameEn: companyNameEn,
        companyAddress: companyAddress,
        companyAddressDetail: companyAddressDetail,
        factoryAddress: factoryAddress,
        factoryAddressDetail: factoryAddressDetail,
        companyCeo: companyCeo,
        companyTel: companyTel,
        companyHomepage: companyHomepage,
        companyFax: companyFax,
        industryPart: industryPart,
        industryPartEtc: industryPartEtc,
        prePartYear: prePartYear,
        memberCompanyYn: memberCompanyYn,
        /* 담당자 정보 */
        name: name,
        position: position,
        depart: depart,
        tel: tel,
        phone: phone,
        email: email1 + '@' + email2,
        /* 부담당자 정보 */
        chargePersonList: chargePersonList_json_arr,
        /* 참가행사 및 분야 */
        fieldParticipatory: fieldParticipatory,
        fieldParticipatory1: fieldParticipatory1,
        fieldParticipatory2: fieldParticipatory2,
        fieldParticipatory3: fieldParticipatory3,
        /* 상세 정보 */
        companyIntroVideo: companyIntroVideo,
        companyIntroKo: companyIntroKo,
        companyIntroEn: companyIntroEn,
        companyPurposeKo: companyPurposeKo,
        companyPurposeEn: companyPurposeEn,
        newItemIntroKo: newItemIntroKo,
        newItemIntroEn: newItemIntroEn,
        promotionPlan: promotionPlan,
        /* 제품 노출 정보 */
        onlineList: onlineList_json_arr,
        /* 수출상담회 */
        exportMeetingYn: export_meeting_yn,
        buyerList: buyer_add_json_arr
    };

    let resData = ajaxConnect('/mypage/step/updateExhibitorNew.do', 'post', exhibitor_json_obj);

    if(resData.resultCode === "0") {
        let exhibitorSeq = resData.customValue;

        if(nvl(exhibitorSeq,"") !== ""){

            /* step 01 등록 후 buyer popup json arr 초기화 */
            if(export_meeting_yn === 'Y'){
                buyer_add_json_arr = [];
            }

            /* 파일 업로드 */
            // 로딩바 표시 (업로드 시작 전)
            Swal.fire({
                title: "정보 및 파일 저장 중",
                html: "파일을 업로드하고 있습니다.<br>잠시만 기다려 주세요.",
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });

            try{

                // 3-1. 사용자가 X 눌렀던 파일들 실제 삭제 (await)
                await processDeletedFiles();

                // [핵심 수정] 파일 업로드가 끝날 때까지 기다림 (await)
                await f_company_uploadFile_call(exhibitorSeq, exhibitorSeq);

                // [안전 장치 1] 관리자 여부 확인 시 에러 방지
                let isAdmin = false;
                try {
                    // parent.window 접근 시 cross-origin 등의 에러가 발생할 수 있으므로 try-catch로 감쌉니다.
                    if (parent && parent.window && parent.window.location && parent.window.location.href) {
                        if (parent.window.location.href.includes('/mng/')) {
                            isAdmin = true;
                        }
                    }
                } catch (e) {
                    // 에러나면 일반 사용자로 간주하거나, 필요시 true로 설정
                    isAdmin = false;
                }

                // ★★★ [핵심 수정] 기존 로딩창 강제 종료 ★★★
                Swal.close();

                let swal_html = '<span style="font-size: 1.2em;">기본 정보가 저장되었습니다.';
                if(!isAdmin){
                    swal_html += '<br>다음 단계로 이동합니다.</span>';
                } else {
                    swal_html += '</span>';
                }

                // 약간의 딜레이 후 성공 팝업 출력 (로딩바 잔상 제거용)
                setTimeout(function() {
                    Swal.fire({
                        icon: 'info',
                        title: '[ 참가업체 정보 ]',
                        html: swal_html,
                        allowOutsideClick: false,
                        confirmButtonColor: '#00a8ff',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {

                            /* 온라인 제품 사진 번호 재부여 */
                            let onlineFile_json_obj = {
                                seq: exhibitorSeq,
                                onlineList: onlineList_json_arr
                            }
                            let online_res = ajaxConnect('/mypage/step/updateOnlineNewFileNote.do', 'post', onlineFile_json_obj);

                            if (online_res.resultCode === "0") {

                                isSubmitProceeding = true;

                                if (!isAdmin) {
                                    // 일반 사용자: 다음 단계 이동
                                    f_page_move('/mypage/step2_1.do', exhibitorSeq);
                                } else {
                                    console.log(">> [DEBUG] 7. [관리자] 페이지 새로고침(재진입)");
                                    // 관리자 모드: 현재 페이지 주소로 다시 이동 (새로고침 효과)
                                    // window.location.pathname은 현재 페이지의 주소(예: /mypage/modify.do)를 가져옵니다.
                                    //f_page_move(window.location.pathname, exhibitorSeq);
                                    parent.window.location.reload();
                                    window.location.href='/mng/exhibitorNewNew/participant/company/detail.do?seq=' + exhibitorSeq + '&lang=KO';
                                }
                            }
                        }
                    });
                }, 200); // 0.2초 딜레이
            } catch (err) {
                console.error(">> [DEBUG] CRITICAL ERROR catch block:", err);
                Swal.fire("오류", "처리 중 치명적인 오류가 발생했습니다.<br>" + err, "error");
            }

        }else{
            window.location.href = '/mypage/step2_1.do';
        }
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_9_check(exhibitorSeq){

    /******************** 요트/보트 출품 정보 ********************/
    //전시품정보 Json Create
    let productList_json_arr = [];

    let boatEntryYn = nvl($('input[type=radio][name=boatEntryYn]:checked').val(),'N');
    if(boatEntryYn === 'Y'){
        /*showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '요트/보트 출품 여부 항목을 선택해 주세요.', '');
        return false;*/

        // 제품분류(대)
        let product_option_big_el = $('select[name=productOptionBig]');
        let product_option_big_len = product_option_big_el.length;
        let product_option_big_flag = true;
        for(let i=0; i<product_option_big_len; i++){
            if(product_option_big_el.eq(i).val() === '선택' || product_option_big_el.eq(i).val() === '선택'){
                if($('.preOptionList').length === 0){
                    product_option_big_flag = false;
                    break;
                }
            }
        }
        if(!product_option_big_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품 분류(품목) 첫 번째 항목을 선택해 주세요.', '');
            return false;
        }
    
        // 제품분류(소)
        let product_option_small_el = $('select[name=productOptionSmall]');
        let product_option_small_len = product_option_small_el.length;
        let product_option_small_flag = true;
        for(let i=0; i<product_option_small_len; i++){
            if(product_option_small_el.eq(i).val() === '선택' || product_option_small_el.eq(i).val() === '선택'){
                if($('.preOptionList').length === 0) {
                    product_option_small_flag = false;
                    break;
                }
            }
        }
        if(!product_option_small_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품 분류(품목) 두 번째 항목을 선택해 주세요.', '');
            return false;
        }
    
        // 제품명(국문)
        let product_name_ko_el = $('input[type=text][name=productNameKo]');
        let product_name_ko_len = product_name_ko_el.length;
        let product_name_ko_flag = true;
        for(let i=0; i<product_name_ko_len; i++){
            if(nvl(product_name_ko_el.eq(i).val(),'') === ''){
                product_name_ko_flag = false;
                break;
            }
        }
        if(!product_name_ko_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품명을 입력해 주세요.', '');
            return false;
        }
    
        // 수량
        let product_qty_el = $('input[type=text][name=productQty]');
        let product_qty_len = product_qty_el.length;
        let product_qty_flag = true;
        for(let i=0; i<product_qty_len; i++){
            if(nvl(product_qty_el.eq(i).val(),'') === ''){
                product_qty_flag = false;
                break;
            }
        }
        if(!product_qty_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '수량을 입력해 주세요.', '');
            return false;
        }
    
        // 제조사(브랜드)
        let product_brand_el = $('input[type=text][name=productBrand]');
        let product_brand_len = product_brand_el.length;
        let product_brand_flag = true;
        for(let i=0; i<product_brand_len; i++){
            if(nvl(product_brand_el.eq(i).val(),'') === ''){
                product_brand_flag = false;
                break;
            }
        }
        if(!product_brand_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제조사(브랜드)를 입력해 주세요.', '');
            return false;
        }

        // 특징 유효성 검사
        let product_feature_el = $('textarea[name=productFeature]');
        let product_feature_len = product_feature_el.length;
        let product_feature_flag = true;
        for (let i = 0; i < product_feature_len; i++) {
            if (nvl(product_feature_el.eq(i).val(), '') === '') {
                product_feature_flag = false;
                break;
            }
        }
        if (!product_feature_flag) {
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '제품 특징을 입력해 주세요.', '');
            return false;
        }
    
        // 길이
        let product_length_el = $('input[type=text][name=productLength]');
        let product_length_len = product_length_el.length;
        let product_length_flag = true;
        for(let i=0; i<product_length_len; i++){
            if(nvl(product_length_el.eq(i).val(),'') === ''){
                product_length_flag = false;
                break;
            }
        }
        if(!product_length_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '길이(cm)를 입력해 주세요.', '');
            return false;
        }
    
        // 너비
        let product_width_el = $('input[type=text][name=productWidth]');
        let product_width_len = product_width_el.length;
        let product_width_flag = true;
        for(let i=0; i<product_width_len; i++){
            if(nvl(product_width_el.eq(i).val(),'') === ''){
                product_width_flag = false;
                break;
            }
        }
        if(!product_width_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '너비(cm)를 입력해 주세요.', '');
            return false;
        }
    
        // 높이
        let product_height_el = $('input[type=text][name=productHeight]');
        let product_height_len = product_height_el.length;
        let product_height_flag = true;
        for(let i=0; i<product_height_len; i++){
            if(nvl(product_height_el.eq(i).val(),'') === ''){
                product_height_flag = false;
                break;
            }
        }
        if(!product_height_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '높이(cm)를 입력해 주세요.', '');
            return false;
        }
    
        // 중량
        let product_weight_el = $('input[type=text][name=productWeight]');
        let product_weight_len = product_weight_el.length;
        let product_weight_flag = true;
        for(let i=0; i<product_weight_len; i++){
            if(nvl(product_weight_el.eq(i).val(),'') === ''){
                product_weight_flag = false;
                break;
            }
        }
        if(!product_weight_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '중량(kg)를 입력해 주세요.', '');
            return false;
        }
    
        // 소재
        let product_material_el = $('input[type=text][name=productMaterial]');
        let product_material_len = product_material_el.length;
        let product_material_flag = true;
        for(let i=0; i<product_material_len; i++){
            if(nvl(product_material_el.eq(i).val(),'') === ''){
                product_material_flag = false;
                break;
            }
        }
        if(!product_material_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '소재를 입력해 주세요.', '');
            return false;
        }
    
        // 연식
        let product_year_el = $('input[type=text][name=productYear]');
        let product_year_len = product_year_el.length;
        let product_year_flag = true;
        for(let i=0; i<product_year_len; i++){
            if(nvl(product_year_el.eq(i).val(),'') === ''){
                product_year_flag = false;
                break;
            }
        }
        if(!product_year_flag){
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', '연식을 입력해 주세요.', '');
            return false;
        }

        let productNames = $('input[name=productNameKo]');
        let productCnt = productNames.length;
        if(productCnt > 0){
            for(let i=0; i<productCnt; i++){
                let productOptionBig = '';
                let productOptionSmall = '';
                let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
                if(nvl(selProductOptionBig,'선택') !== '선택'){
                    productOptionBig = $('select[name=productOptionBig]').eq(i).val();
                    productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
                }else {
                    productOptionBig = $('input[type=text][name=preProductOptionBig]').eq(i).val();
                    productOptionSmall = $('input[type=text][name=preProductOptionSmall]').eq(i).val();
                }
                let isNewVal = $('input[name="productIsNew_' + (i + 1) + '"]:checked').val();
                let productList_json_obj = {
                    seq: $('input[type=hidden][name=productSeq]').eq(i).val(),
                    exSeq: exhibitorSeq,
                    productOptionBig: productOptionBig,
                    productOptionSmall: productOptionSmall,
                    productIsNew: nvl(isNewVal, 'N'),
                    productNameKo: product_name_ko_el.eq(i).val(),
                    productQty: product_qty_el.eq(i).val(),
                    productBrand: product_brand_el.eq(i).val(),
                    productFeature: product_feature_el.eq(i).val(),
                    productLength: product_length_el.eq(i).val(),
                    productWidth: product_width_el.eq(i).val(),
                    productHeight: product_height_el.eq(i).val(),
                    productWeight: product_weight_el.eq(i).val(),
                    productMaterial: product_material_el.eq(i).val(),
                    productYear: product_year_el.eq(i).val(),
                    note: (i+1)
                };
                productList_json_arr.push(productList_json_obj);
            }
        }
    }else{
        productList_json_arr = [];
    }
    
    if(nvl(exhibitorSeq,'') !== ''){

        let exhibitor_json_obj = {
            seq: exhibitorSeq,
            boatEntryYn: boatEntryYn,
            productList: productList_json_arr
        };
        let resData = ajaxConnect('/apply/step/saveProductNew.do', 'post', exhibitor_json_obj);

        if(resData.resultCode === "0") {

            let timerInterval;
            Swal.fire({
                title: "정보 저장 중",
                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해 주세요.",
                allowOutsideClick: false,
                timer: 2500,
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
                        icon: 'info',
                        title: '[ 요트/보트 출품 정보 ]',
                        html: '<span style="font-size: 1.2em;"> 요트/보트 출품 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                        allowOutsideClick: false,
                        confirmButtonColor: '#00a8ff',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            f_page_move('/mypage/step2_2.do', exhibitorSeq);
                        }
                    })
                }
            });
        }else{
            showMessage('', 'error', '[ 요트/보트 출품 정보 ]', ' 요트/보트 출품 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
        }
    }
}

function my_step_2_1_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_2.do', exhibitorSeq);*/

    let utilityPrcSum = parseInt($('#utilityPrcSum').val()) || 0;
    
    // --- 1. 부스 정보 수집 ---
    const registrationCnt = 1;
    const registrationFee = 100000;
    const standAloneBoothCnt = parseInt($('#standAloneBoothCnt').val()) || 0;
    const assemblyBoothCnt = parseInt($('#assemblyBoothCnt').val()) || 0;
    //const onlineBoothCnt = parseInt($('#onlineBoothCnt').val()) || 0;
    const physicalBooths = standAloneBoothCnt + assemblyBoothCnt;

    const standAloneBoothFee = standAloneBoothCnt * boothPrices.standAlone;
    const assemblyBoothFee = assemblyBoothCnt * boothPrices.assembly;
    //const onlineBoothFee = onlineBoothCnt * boothPrices.online;

    // 부스 관련 총액 (등록비 포함)
    const boothPrcSum = registrationFee + standAloneBoothFee + assemblyBoothFee/* + onlineBoothFee*/;

    let boothType = '등록비';
    if (standAloneBoothCnt > 0) boothType += ',독립부스';
    if (assemblyBoothCnt > 0) boothType += ',조립부스';
    //if (onlineBoothCnt > 0) boothType += ',온라인부스';

    // --- 2. 할인 정보 수집 및 계산 (전면 수정) ---
    let discountType = '';
    let discountPrcSum = 0;
    // 체크된 모든 할인을 순회하며 할인 총액과 할인 타입을 동적으로 생성합니다.
    $('input[name="discount"]:checked').each(function() {
        const id = $(this).attr('id');
        const discountAmount = parseInt($(this).data('discount')) || 0;
        discountPrcSum += (physicalBooths * discountAmount);

        // discountType 문자열을 ID 기반으로 생성
        switch(id) {
            case 'discountEarly1': discountType += ',(1차)조기신청'; break;
            case 'discountEarly2': discountType += ',(2차)조기신청'; break;
            case 'discountScale1': discountType += ',규모할인(10+)'; break;
            case 'discountScale2': discountType += ',규모할인(20+)'; break;
            case 'discountScale3': discountType += ',규모할인(30+)'; break;
            case 'discountScale4': discountType += ',규모할인(40+)'; break;
            case 'discountScale5': discountType += ',규모할인(50+)'; break;
            case 'discountScale6': discountType += ',규모할인(100+)'; break;
            case 'discountRe': discountType += ',재참가'; break;
            case 'discountFirstUnder10': discountType += ',첫참가(10미만)'; break;
            case 'discountFirstOver10': discountType += ',첫참가(10이상)'; break;
            case 'discountLeisure': discountType += ',해양레저산업협회'; break;
        }
    });

    if (discountType.startsWith(',')) {
        discountType = discountType.substring(1);
    }

    // --- 3. 발전기금 계산 (참가자가 보는 화면 계산용) ---
    /*let developmentFund = 0;
    const isMember = $('#memberCompanyYn').val() === 'Y';
    const isLeisureDiscountChecked = $('#discountLeisure').is(':checked');
    if (isMember || isLeisureDiscountChecked) {
        developmentFund = Math.floor(boothPrcSum * 0.1);
    }

    // --- 4. 최종 금액 계산 ---
    // 공급가액 = ((부스총액 + 발전기금) - 할인총액) + 유틸리티총액
    const prcSum = ((boothPrcSum + developmentFund) - discountPrcSum) + utilityPrcSum;
    const prcVat = Math.floor(prcSum * 0.1);
    const prcTotal = prcSum + prcVat;*/

    // --- 5. 유효성 검사 및 서버 전송 데이터 구성 ---
    const totalBooths = standAloneBoothCnt + assemblyBoothCnt/* + onlineBoothCnt*/;
    if (totalBooths === 0) {
        showMessage('', 'error', '[ 전시부스 신청 ]', '부스(독립, 조립, 온라인)를 하나 이상 신청해 주세요.', '');
        return;
    }

    // 서버로 보낼 최종 데이터 객체
    const booth_json_obj = {
        seq: exhibitorSeq,
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registrationCnt,
        registrationFee: registrationFee,
        standAloneBoothCnt: standAloneBoothCnt,
        standAloneBoothFee: standAloneBoothFee,
        assemblyBoothCnt: assemblyBoothCnt,
        assemblyBoothFee: assemblyBoothFee,
        /*onlineBoothCnt: onlineBoothCnt,
        onlineBoothFee: onlineBoothFee,*/
        discountEarly1: $('#discountEarly1').is(':checked'),
        discountEarly2: $('#discountEarly2').is(':checked'),
        discountFirstUnder10: $('#discountFirstUnder10').is(':checked'),
        discountFirstOver10: $('#discountFirstOver10').is(':checked'),
        discountRe: $('#discountRe').is(':checked'),
        discountScale1: $('#discountScale1').is(':checked'),
        discountScale2: $('#discountScale2').is(':checked'),
        discountScale3: $('#discountScale3').is(':checked'),
        discountScale4: $('#discountScale4').is(':checked'),
        discountScale5: $('#discountScale5').is(':checked'),
        discountScale6: $('#discountScale6').is(':checked'),
        discountLeisure: $('#discountLeisure').is(':checked'),
        discountYn: (discountType !== '') ? 'Y' : 'N',
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum/*,
        prcSum: prcSum,
        prcVat: prcVat,
        prcTotal: prcTotal*/
    };

    let resData = ajaxConnect('/apply/step/updateExhibitorNewBooth.do', 'post', booth_json_obj);
    if(resData.resultCode === "0") {

        Swal.fire({
            icon: 'info',
            title: '[ 전시부스 신청 ]',
            html: '<span style="font-size: 1.2em;">부스 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_9.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 전시부스 신청 ]', '전시부스 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_10_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_3.do', exhibitorSeq);*/

    const participationChoice = $('input[name="maritimeExhibitionYn"]:checked').val();
    const isSeaChecked = $('#maritimeDetailBox input[name="maritimeExhibitionSea"]').is(':checked');
    const isLandChecked = $('#maritimeDetailBox input[name="maritimeExhibitionLand"]').is(':checked');

    // 1. '참가'를 선택한 경우
    if (participationChoice === 'Y') {
        // 1-1. 해상 전시 또는 육상 전시 둘 다 선택하지 않은 경우
        if (!isSeaChecked && !isLandChecked) {
            Swal.fire({
                icon: 'error',
                title: '[ 해상전시회 신청 ]',
                text: '해상전시회 참가를 선택했을 경우, 참가 항목(해상/육상)을 1개 이상 선택해주세요.',
                confirmButtonColor: '#00a8ff',
                confirmButtonText: '확인'
            });
            return; // 서버 전송 중단
        }
    }

    let formData = {
        seq: exhibitorSeq,
        maritimeExhibitionYn: participationChoice,
        maritimeExhibitionSea: isSeaChecked,
        maritimeExhibitionLand: isLandChecked
    };

    let resData = ajaxConnect('/apply/step/updateExhibitorNewMaritime.do', 'post', formData);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 해상전시회 신청 ]',
            html: '<span style="font-size: 1.2em;">해상전시회 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_9.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 해상전시회 신청 ]', '해상전시회 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_2_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_3.do', exhibitorSeq);*/

    let companySignNameKo = '';
    let companySignNameEn = '';
    let assemblyBoothCnt = $('#assemblyBoothCnt').val();
    if(assemblyBoothCnt > 0){
        // 상호간판 신청 - 상호간판명 (국문)
        companySignNameKo = $('#companySignNameKo').val();
        if(nvl(companySignNameKo,'') === ''){
            showMessage('#companySignNameKo', 'error', '[ 상호간판 신청 ]', '조립부스 참가업체는 상호간판명(국문)을 필수 입력해 주세요.', '');
            return false;
        }

        // 상호간판 신청 - 상호간판명 (영문)
        companySignNameEn = $('#companySignNameEn').val();
        if(nvl(companySignNameEn,'') === ''){
            showMessage('#companySignNameEn', 'error', '[ 상호간판 신청 ]', '조립부스 참가업체는 상호간판명(영문)을 필수 입력해 주세요.', '');
            return false;
        }
    }

    //let standAloneBoothCnt = parseInt(nvl($('#standAloneBoothCnt').val(),'0'));
    //let assemblyBoothCnt = parseInt(nvl($('#assemblyBoothCnt').val(),'0'));

    /*if(nvl(company_sign_name_ko,'') === '' || nvl(company_sign_name_en,'') === ''){
        if(assemblyBoothCnt > 0) {
            showMessage('', 'error', '[상호간판 신청정보]', '조립부스를 신청하신 참가업체는<br>반드시 상호간판을 신청해 주셔야 합니다.', '');
            return false;
        }
    }else{
        if(assemblyBoothCnt === 0) {
            showMessage('', 'error', '[상호간판 신청정보]', '상호간판은 조립부스를 신청한 참가기업만 작성 가능합니다.', '');
            return false;
        }
    }*/

    let sign_json_obj = {
        seq: exhibitorSeq,
        companySignNameKo: companySignNameKo,
        companySignNameEn: companySignNameEn
    }

    let resData = ajaxConnect('/apply/step/updateExhibitorNewCompanySign.do', 'post', sign_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 상호간판 신청 ]',
            html: '<span style="font-size: 1.2em;">상호간판 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_3.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 상호간판 신청 ]', '상호간판 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_3_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_4.do', exhibitorSeq);*/

    // --- 1. 유틸리티 신청정보 수집 ---
    let utility_jugan_cnt = parseInt($('#utility_jugan_cnt').val()) || 0;
    let utility_jugan_fee = parseInt(wonToInt($('#utility_jugan_fee').val())) || 0;
    let utility_day_cnt = parseInt($('#utility_day_cnt').val()) || 0;
    let utility_day_fee = parseInt(wonToInt($('#utility_day_fee').val())) || 0;
    let utility_work_cnt = parseInt($('#utility_work_cnt').val()) || 0;
    let utility_work_fee = parseInt(wonToInt($('#utility_work_fee').val())) || 0;
    let utility_compressed_air_cnt = parseInt($('#utility_compressed_air_cnt').val()) || 0;
    let utility_compressed_air_fee = parseInt(wonToInt($('#utility_compressed_air_fee').val())) || 0;
    let utility_water_basic_cnt = parseInt($('#utility_water_basic_cnt').val()) || 0;
    let utility_water_basic_fee = parseInt(wonToInt($('#utility_water_basic_fee').val())) || 0;
    let utility_internet_cnt = parseInt($('#utility_internet_cnt').val()) || 0;
    let utility_internet_fee = parseInt(wonToInt($('#utility_internet_fee').val())) || 0;
    let utility_pytex_new_cnt = parseInt($('#utility_pytex_new_cnt').val()) || 0;
    let utility_pytex_new_fee = parseInt(wonToInt($('#utility_pytex_new_fee').val())) || 0;
    let utility_pytex_re_cnt = parseInt($('#utility_pytex_re_cnt').val()) || 0;
    let utility_pytex_re_fee = parseInt(wonToInt($('#utility_pytex_re_fee').val())) || 0;
    let utility_barcode_cnt = parseInt($('#utility_barcode_cnt').val()) || 0;
    let utility_barcode_fee = parseInt(wonToInt($('#utility_barcode_fee').val())) || 0;

    // 유틸리티 총액을 화면이 아닌, 각 항목의 합계로 직접 계산
    const utilityPrcSum = utility_jugan_fee + utility_day_fee + utility_work_fee + utility_compressed_air_fee +
        utility_water_basic_fee + utility_internet_fee + utility_pytex_new_fee +
        utility_pytex_re_fee + utility_barcode_fee;

    let utility_json_obj = {
        seq: exhibitorSeq,
        utilityJuganCnt: utility_jugan_cnt,
        utilityJuganFee: utility_jugan_fee,
        utilityDayCnt: utility_day_cnt,
        utilityDayFee: utility_day_fee,
        utilityWorkCnt: utility_work_cnt,
        utilityWorkFee: utility_work_fee,
        utilityCompressedAirCnt: utility_compressed_air_cnt,
        utilityCompressedAirFee: utility_compressed_air_fee,
        utilityWaterBasicCnt: utility_water_basic_cnt,
        utilityWaterBasicFee: utility_water_basic_fee,
        utilityInternetCnt: utility_internet_cnt,
        utilityInternetFee: utility_internet_fee,
        utilityPytexNewCnt: utility_pytex_new_cnt,
        utilityPytexNewFee: utility_pytex_new_fee,
        utilityPytexReCnt: utility_pytex_re_cnt,
        utilityPytexReFee: utility_pytex_re_fee,
        utilityBarcodeCnt: utility_barcode_cnt,
        utilityBarcodeFee: utility_barcode_fee,
        utilityPrcSum: utilityPrcSum/*,
        prcSum: prcSum,
        prcVat: prcVat,
        prcTotal: prcTotal*/
    }

    let resData = ajaxConnect('/apply/step/updateExhibitorNewUtility.do', 'post', utility_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 유틸리티 신청 ]',
            html: '<span style="font-size: 1.2em;">유틸리티 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_4.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 유틸리티 신청 ]', '유틸리티 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
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
        showMessage('', 'error', '[ 출입증 신청 ]', '성명(국문)을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 출입증 신청 ]', '성명(영문) 이름을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 출입증 신청 ]', '성명(영문) 성을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 출입증 신청 ]', '직책(국문)을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 출입증 신청 ]', '직책(영문)을 입력해 주세요.', '');
        return false;
    }

    // 출입증비고
    let pass_note_el = document.querySelectorAll('input[type=text][name=pass_note]');

    let pass_json_arr = [];
    if(pass_name_len > 0){
        for(let i=0; i<pass_name_len; i++){
            let pass_json_obj = {
                seq: $('input[type=hidden][name=passSeq]').eq(i).val(),
                exSeq: exhibitorSeq,
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
        passList: pass_json_arr
    };

    let resData = ajaxConnect('/apply/step/insertPassNew.do', 'post', passList_json_arr);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 출입증 신청 ]',
            html: '<span style="font-size: 1.2em;">출입증 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_5.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 출입증 신청 ]', '출입증 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }

}

function my_step_2_5_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_8.do', exhibitorSeq);*/

    let giftApplyYn = $('input[name="giftApplyYn"]:checked').val() || 'N';

    // '신청(Y)'인데 등록된 경품이 단 1개도 없는 경우 진행 차단
    if (giftApplyYn === 'Y' && gift_add_json_arr.length === 0 && $('.form_chuga_list').length === 0) {
        showMessage('', 'error', '[ 경품제공 신청 ]', '신청 시 최소 1개 이상의 경품을 등록해 주세요.', '');
        return false;
    }

    let giftList_json_obj = {
        seq: exhibitorSeq,
        giftApplyYn: giftApplyYn,
        giftList: gift_add_json_arr
    };

    let resData = ajaxConnect('/apply/step/insertGiftNew.do', 'post', giftList_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        gift_add_json_arr = [];

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 경품제공 신청 ]',
            html: '<span style="font-size: 1.2em;">경품제공 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step2_8.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 경품제공 신청 ]', '경품제공 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_8_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step03.do', exhibitorSeq);*/

    let directory_json_obj = {
        exSeq: exhibitorSeq
    };

    let resData = ajaxConnect('/apply/step/insertDirectory.do', 'post', directory_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 디렉토리 정보 ]',
            html: '<span style="font-size: 1.2em;">디렉토리 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {
                /* 등록 성공 시 다음 단계로 이동 */
                f_page_move('/mypage/step03.do', exhibitorSeq);
            }
        });
    }else{
        showMessage('', 'error', '[ 디렉토리 정보 ]', '디렉토리 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_03_check(exhibitorSeq){

    let apply_comp_json_obj = {
        seq: exhibitorSeq,
        approvalStatus: '승인요청',
        applyComplt: 'Y'
    }
    let resData = ajaxConnect('/apply/step/updateExhibitorNewStatus.do', 'post', apply_comp_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 참가업체 정보 ]',
            html: '<span style="font-size: 1.2em;">참가 신청이 정상 완료되었습니다.<br>등록하신 담당자 메일로 신청완료 및 안내 메일이 발송됩니다.</span>',
            allowOutsideClick: false,
            confirmButtonColor: '#00a8ff',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {

                let exhibitor_new_jsonObj = {
                    seq: exhibitorSeq
                }
                let emailArr = ajaxConnectSimple('/getExhibitorNewEmailList.do','post', exhibitor_new_jsonObj);
                let mailResultFlag = false;
                for(let j=0; j<emailArr.length; j++){
                    let email = emailArr[j];
                    let jsonObj = {
                        subject: '[2027 경기국제보트쇼] 참가업체 접수 완료', //제목
                        body: "", //본문
                        template: "6", //템플릿 번호
                        receiver: [{email: email}]
                    }
                    ajaxConnect('/mail/send.do', 'post', jsonObj);

                    if(j === (emailArr.length-1)){
                        mailResultFlag = true;
                    }
                }
                if (mailResultFlag) {
                    /* 등록 성공 시 다음 단계로 이동 */
                    home('ko');
                }
            }
        })
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '참가 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function exibitLoginFormSubmit() {

    let form = document.getElementById("login_form");
    let id = $("#exhibitId").val();
    let password = $("#exhibitPw").val();

    if (nvl(id,'') === "" || nvl(password,'') === "") {
        showMessage('', 'info', '입력 정보 확인', '아이디와 비밀번호를 입력해 주세요.', '');
        return false;
    }

    let jsonObj = {
        id: id,
        password: password,
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

                let hiddenField_seq = document.createElement('input');
                hiddenField_seq.type = 'hidden';
                hiddenField_seq.name = 'seq';
                hiddenField_seq.value = data.customValue;

                form.appendChild(hiddenField_seq); //seq

                document.body.appendChild(form);

                form.submit(); // /mypage/index.do
            } else {
                showMessage('', 'info', '[ 로그인 실패 ]', '아이디와 비밀번호를 확인해 주세요.', '');
            }
        })
        .fail(function (xhr, status, errorThrown) {
            /*$('body').html("오류가 발생했습니다.")
                .append("<br>오류명: " + errorThrown)
                .append("<br>상태: " + status);*/
            alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
        })

}

// 3. [통합된 로그인 체크 함수]
function f_pre_apply_check_login(){

    // --- [이름 검사] ---
    let name = $('#name').val();
    if (nvl(name, '') === '') {
        setTimeout(function() {
            Swal.fire({
                icon: 'info', title: '입력 정보 확인',
                html: '<span style="font-size: 1.2em;">이름을 입력해 주세요.</span>',
                confirmButtonColor: '#00a8ff', confirmButtonText: '확인',
                allowOutsideClick: false, allowEscapeKey: false,
                returnFocus: false // 닫힐 때 이전 포커스로 돌아가지 않음
            }).then((result) => {
                isLoginProcess = false; // 잠금 해제
                setTimeout(function(){ $('#name').trigger('focus'); }, 100);
            });
        }, 300);
        return false;
    }

    // --- [휴대전화 검사 (통합됨)] ---
    let phone = $('#phone').val();
    let pureNum = phone.replace(/-/g, ""); // 하이픈 제거한 순수 숫자

    // 1) 값 입력 여부 확인
    if (pureNum.length === 0) {
        setTimeout(function() {
            Swal.fire({
                icon: 'warning', title: '입력 정보 확인',
                html: '<span style="font-size: 1.2em;">휴대전화번호를 입력해 주세요.</span>',
                confirmButtonColor: '#00a8ff', confirmButtonText: '확인',
                allowOutsideClick: false, allowEscapeKey: false,
                returnFocus: false // 닫힐 때 이전 포커스로 돌아가지 않음
            }).then((result) => {
                isLoginProcess = false; // 잠금 해제
                setTimeout(function(){ $('#phone').trigger('focus'); }, 100);
            });
        }, 300);
        return false;
    }

    // 2) 010 시작 여부 확인
    if (pureNum.substring(0, 3) !== "010") {
        setTimeout(function() {
            Swal.fire({
                icon: 'warning', title: '입력 정보 확인',
                html: '<span style="font-size: 1.2em;">휴대전화번호는 "010"으로 시작해야 합니다.</span>',
                confirmButtonColor: '#00a8ff', confirmButtonText: '확인',
                allowOutsideClick: false, allowEscapeKey: false,
                returnFocus: false // 닫힐 때 이전 포커스로 돌아가지 않음
            }).then((result) => {
                isLoginProcess = false; // 잠금 해제
                // 입력값 초기화 후 포커스
                $('#phone').val('');
                setTimeout(function(){ $('#phone').trigger('focus'); }, 100);
            });
        }, 300);
        return false;
    }

    // 3) 전체 길이(11자리) 확인
    if (pureNum.length !== 11) {
        setTimeout(function() {
            Swal.fire({
                icon: 'warning', title: '입력 정보 확인',
                html: '<span style="font-size: 1.2em;">휴대전화번호 형식이 올바르지 않습니다.<br>(010-0000-0000)</span>',
                confirmButtonColor: '#00a8ff', confirmButtonText: '확인',
                allowOutsideClick: false, allowEscapeKey: false,
                returnFocus: false // 닫힐 때 이전 포커스로 돌아가지 않음
            }).then((result) => {
                isLoginProcess = false; // 잠금 해제
                setTimeout(function(){ $('#phone').trigger('focus'); }, 100);
            });
        }, 300);
        return false;
    }

    let jsonObj = {
        name: name,
        phone: phone,
        joinYear: transferYear // JSP 상단 혹은 공통 js에 선언된 변수로 가정
    };

    // AJAX 호출
    let resData = ajaxConnectSimple('/visitor/preApplyCheck.do', 'post', jsonObj);

    // 결과 팝업 (딜레이 적용)
    setTimeout(function() {
        if(nvl(resData,'') !== ''){
            // [CASE 1: 데이터 있음]
            Swal.fire({
                icon: 'info', title: '[ 참관 신청 확인 ]',
                html: '<span style="font-size: 1.2em;">참관 신청 확인 되었습니다.<br>참관신청확인페이지로 이동합니다.</span>',
                allowOutsideClick: false, allowEscapeKey: false,
                confirmButtonColor: '#00a8ff', confirmButtonText: '확인',
                returnFocus: false // 닫힐 때 이전 포커스로 돌아가지 않음
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/visitor/mypage.do?seq=' + resData.seq;
                } else {
                    isLoginProcess = false;
                }
            });
        } else {
            // [CASE 2: 데이터 없음]
            Swal.fire({
                icon: 'info', title: '[ 참관 신청 확인 ]',
                html: '<span style="font-size: 1.2em;">참관 신청 정보가 없습니다.<br>사전등록페이지로 이동하시겠습니까?</span>',
                allowOutsideClick: false, allowEscapeKey: false,
                showCancelButton: true,
                confirmButtonColor: '#00a8ff', confirmButtonText: '사전등록하기',
                cancelButtonColor: '#D33', cancelButtonText: '취소',
                returnFocus: false // 닫힐 때 이전 포커스로 돌아가지 않음
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/visitor/apply.do';
                } else {
                    isLoginProcess = false;
                }
            });
        }
    }, 300); // 0.3초 딜레이

    return false;
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
            confirmButtonColor: '#00a8ff',
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
            confirmButtonColor: '#00a8ff',
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

    if(nvl(companyName,'') === ''){
        alert('직장명을 입력해주세요.');
        return;
    }

    let jsonObj = { wkplNm: companyName }

    $.ajax({
        url: '/visitor/companySearch.do',
        method: 'post',
        data: JSON.stringify(jsonObj),
        contentType: 'application/json; charset=utf-8', //server charset 확인 필요
        beforeSend : function(request){
            // Performed before calling Ajax
            $('#spinner').show();
        },
        success: function (data) {
            // 데이터 유효성 검사 강화
            if(data && data.header && data.header.resultCode === '00' && data.body){
                let totalCount = data.body.totalCount;

                if(totalCount > 0 && data.body.items){
                    let items = data.body.items.item;

                    // API가 1건일 때 Object로 주는 경우 배열로 변환
                    if (!Array.isArray(items)) {
                        items = [items];
                    }

                    let uniqueList = new Set(); // Set을 사용하여 중복 제거 효율화
                    let htmlStr = '';

                    items.forEach((element) => {
                        let wkplNm = nvl(element.wkplNm, '');
                        let wkplRoadNmDtlAddr = nvl(element.wkplRoadNmDtlAddr, '');
                        let temp_val = wkplNm + '::' + wkplRoadNmDtlAddr; // 구분자 변경

                        if (!uniqueList.has(temp_val)) {
                            uniqueList.add(temp_val);

                            htmlStr += '<li>';
                            htmlStr += '<a href="javascript:void(0);" onclick="f_company_info_add(this)">';
                            htmlStr += '<div class="name">' + wkplNm + '</div>';
                            htmlStr += '<div class="address">' + wkplRoadNmDtlAddr + '</div>';
                            htmlStr += '</a>';
                            htmlStr += '</li>';
                        }
                    });

                    $('.popCompanyName .companyList').html(htmlStr);

                } else {
                    renderNoResult();
                }
            } else {
                // API 호출은 성공했으나, 비즈니스 로직상 실패(키 오류, 트래픽 초과 등)
                renderNoResult();
                console.warn("API 조회 실패 또는 결과 코드 오류");
            }
        },
        error: function(xhr, status, error) {
            alert('오류가 발생했습니다. 관리자에게 문의해 주세요.');
            console.error(error);
        },
        complete: function() {
            $('#spinner').hide();
        }
    });
}

function renderNoResult() {
    let str = '<div class="companyNot">검색결과가 없습니다.<br>No results were found for your search.</div>';
    $('.popCompanyName .companyList').html(str);
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

    /* 이메일 공백 제거 */
    // 화면의 값을 미리 변경해야 유효성 검사와 데이터 셋팅 모두 공백 없는 값으로 처리됩니다.
    let $email = $('#email');
    if ($email.length > 0) {
        $email.val($email.val().replace(/\s/g, '')); // 모든 공백 제거
    }

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
            title: '[ 사전등록 ]',
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
                    data: JSON.stringify(data),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function (result) {
                        if (result.resultCode === "0") {

                            if(gbn === 'I'){
                                /* 참관객 완료 메일 전송 */
                                let subject = '2027 경기국제보트쇼 <1차 사전등록(무료)> 신청 완료';
                                let template = '178';
                                let timeGbn = data.timeGbn;
                                if(timeGbn === '2차'){
                                    subject = '2027 경기국제보트쇼 <2차 사전등록> 신청 완료';
                                    template = '181';
                                }
                                let email = data.email + '@' + data.domain;

                                let jsonObj = {
                                    subject: subject,
                                    body: "", //본문
                                    template: template, //템플릿 번호
                                    receiver: [{email: email}]
                                }
                                let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                                if (resData.resultCode === "0") {
                                    window.location.href = '/visitor/completed.do';
                                }else{
                                    showMessage('', 'warning', '부분 오류 발생', '참관객 사전 등록은 정상 완료되었으나,<br>참관객 안내 메일 전송에 실패하였습니다.<br>관리자에게 문의해 주세요.', '');
                                }
                            }else{
                                Swal.fire({
                                    icon: 'info',
                                    title: '[사전등록정보]',
                                    html: '<span style="font-size: 1.2em;">' + '참관객 사전 등록 정보가 수정되었습니다.' + '</span>',
                                    allowOutsideClick: false,
                                    confirmButtonColor: '#00a8ff',
                                    confirmButtonText: '확인'
                                }).then((rst) => {
                                    if (rst.isConfirmed) {
                                        let visitorSeq = result.customValue; //visitorSeq return 값
                                        window.location.href = '/visitor/mypage.do?seq=' + visitorSeq;
                                    }
                                });
                            }

                        } else {
                            showMessage('', 'error', '에러 발생', '참관객 사전 등록 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + result.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
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
            showMessage('', 'info', '[ 약관 동의 ]', '개인정보수집 및 이용안내 항목에 동의해 주세요.', '');
            return false;
        }

        let agree2 = $('input[type=radio][name=agree2]:checked').val();
        if(nvl(agree2,'') === '' || agree2 === 'N'){
            showMessage('', 'info', '[ 약관 동의 ]', '개인정보 제3자 제공에 대한 별도 동의 항목에 동의해 주세요.', '');
            return false;
        }

        let agree3 = $('input[type=radio][name=agree3]:checked').val();
        if(nvl(agree3,'') === '' || agree3 === 'N'){
            showMessage('', 'info', '[ 약관 동의 ]', '안전 준수 동의 항목에 동의해 주세요.', '');
            return false;
        }

        let partGbn = $('input[type=radio][name=partGbn]:checked').val();
        if(nvl(partGbn,'') === ''){
            showMessage('', 'info', '[참관 구분]', '참관 구분 항목을 선택해 주세요.', '');
            return false;
        }

        let name = $('#name').val();
        if (nvl(name,'') === '') {
            showMessage('', 'info', '[ 참관객 정보 ]', '성명을 입력해 주세요.', '');
            return false;
        }

        let phone = $('#phone').val();
        if (nvl(phone,'') === '') {
            showMessage('', 'info', '[ 참관객 정보 ]', '휴대전화를 입력해 주세요.', '');
            return false;
        }

        if(!strCheck(name,"name")){
            showMessage('#name', 'info', '[ 참관객 정보 ]', '올바른 이름을 입력해 주세요. (특수문자 제외)', '');
            return false;
        }else{
            let json = { joinYear: transferYear, phone: phone };
            let resData = ajaxConnectSimple('/visitor/preApplyCheck.do', 'post', json);
            if(nvl(resData,'') !== ''){
                Swal.fire({
                    title: '[기존 정보 존재]',
                    html: '이미 사전 등록된 휴대전화번호 입니다.<br>참관신청확인페이지에서 정보를 확인해 주세요.',
                    icon: 'info',
                    allowOutsideClick: false,
                    confirmButtonColor: '#00a8ff',
                    confirmButtonText: '확인'
                });
                return false;
            }
        }

    } // 'I'

    let email = $('#email').val();
    if (nvl(email,'') === '') {
        showMessage('', 'info', '[ 참관객 정보 ]', '이메일을 입력해 주세요.', '');
        return false;
    }else{
        if(email.includes('@')){
            showMessage('', 'info', '[ 참관객 정보 ]', '포함될 수 없는 특수문자(@)가 있습니다.<br>이메일과 도메인을 따로 입력해 주세요.', '');
            return false;
        }
    }

    let domain = $('#domain').val();
    if (nvl(domain,'') === '') {
        showMessage('', 'info', '[ 참관객 정보 ]', '이메일 도메인을 입력해 주세요.', '');
        return false;
    }

    if(nvl($('input[type=radio][name=partGbn]:checked').val(),'') === '바이어'){
        let companyName = $('#companyName').val();
        if (nvl(companyName,'') === '') {
            showMessage('#companyName', 'info', '[ 참관객 정보 ]', '참관 구분>바이어 선택 시 직장명을 필수 입력해 주세요.', '');
            return false;
        }

        let companyAddress = $('#companyAddress').val();
        if (nvl(companyAddress,'') === '') {
            showMessage('#companyAddress', 'info', '[ 참관객 정보 ]', '참관 구분>바이어 선택 시 직장 주소를 필수 입력해 주세요.', '');
            return false;
        }
    }

    /* 행사 구분 유효성 검사 추가 */
    let eventGbn = $('input[type=radio][name=eventGbn]:checked').val();
    if(nvl(eventGbn,'') === ''){
        showMessage('', 'info', '[ 참관객 정보 ]', '행사 구분 항목을 선택해 주세요.', '');
        return false;
    }

    let partnerYn = $('input[type=radio][name=partnerYn]:checked').val();
    if(partnerYn === "Y"){
        let partnerNameList = $('input[type=text][name=partnerName]');
        for(let i=0; i<partnerNameList.length; i++){
            let partnerName = partnerNameList.eq(i).val();
            if (nvl(partnerName,'') === '') {
                showMessage('', 'info', '[ 참관객 정보 ]', '동반자가 있을 경우 동반자 이름을 입력해 주세요.', '');
                return false;
            }
        }

        let partnerAgeList = $('input[type=text][name=partnerAge]');
        for(let i=0; i<partnerAgeList.length; i++){
            let partnerAge = partnerAgeList.eq(i).val();
            if (nvl(partnerAge,'') === '') {
                showMessage('', 'info', '[ 참관객 정보 ]', '동반자가 있을 경우 동반자 나이를 입력해 주세요.', '');
                return false;
            }else{
                if(partnerAge > 18){
                    showMessage('', 'info', '[ 참관객 정보 ]', '동반자는 만 0~18세 사이만 등록 가능합니다.', '');
                    return false;
                }
            }
        }
    }

    let sex = $('input[type=radio][name=sex]:checked').val();
    if(nvl(sex,'') === ''){
        showMessage('', 'info', '[ 설문항목 ]', '성별을 선택해 주세요.', '');
        return false;
    }

    let sidoList = $('select[name=sido]');
    for(let i=0; i<sidoList.length; i++){
        let sido = sidoList.eq(i).val();
        if (nvl(sido,'시/도 선택') === '시/도 선택') {
            showMessage('', 'info', '[ 설문항목 ]', '지역 시/도를 선택해 주세요.', '');
            return false;
        }
    }

    let ageGroup = $('input[type=radio][name=ageGroup]:checked').val();
    if(nvl(ageGroup,'') === ''){
        showMessage('', 'info', '[ 설문항목 ]', '연령대를 선택해 주세요.', '');
        return false;
    }

    let partGbn = $('input[type=radio][name=partGbn]:checked').val();
    if(nvl(partGbn,'') === '일반관람') {
        let observationGbn = $('input[type=checkbox][name=observationGbn]').is(':checked');
        if (!observationGbn) {
            showMessage('', 'info', '[ 설문항목 ]', '관람 구분을 하나 이상 체크해 주세요.', '');
            return false;
        }
    }

    let visitPurpose = $('input[type=checkbox][name=visitPurpose]').is(':checked');
    if (!visitPurpose) {
        showMessage('', 'info', '[ 설문항목 ]', '보트쇼 방문 목적을 하나 이상 체크해 주세요.', '');
        return false;
    }

    let interestItem = $('input[type=checkbox][name=interestItem]').is(':checked');
    if (!interestItem) {
        showMessage('', 'info', '[ 설문항목 ]', '관심품목을 하나 이상 체크해 주세요.', '');
        return false;
    }

    let recognizePath = $('input[type=checkbox][name=recognizePath]').is(':checked');
    if (!recognizePath) {
        showMessage('', 'info', '[ 설문항목 ]', '인지경로를 하나 이상 체크해 주세요.', '');
        return false;
    }

    // 지난 전시회 참관 여부: 행사구분이 '경기국제보트쇼'일 때만 필수 체크
    if (eventGbn === '경기국제보트쇼') {
        let preObservationGbn = $('input[type=checkbox][name=preObservationGbn]').is(':checked');
        if (!preObservationGbn) {
            showMessage('', 'info', '[ 설문항목 ]', '지난 전시회 참관 여부를 하나 이상 체크해 주세요.', '');
            return false;
        }
    }

    return true;
}

function f_visitor_form_data_setting(){

    let joinForm = JSON.parse(JSON.stringify($('#joinForm').serializeObject()));

    joinForm.lang = 'KO';
    joinForm.joinYear = transferYear;
    joinForm.joinYn = 'Y';
    joinForm.visitorGbn = '개인';

    let nowTime = getCurrentDate(); //yyyymmddhhmmss
    if(nowTime < '20260216000000'){ // 1차 사전등록
        joinForm.timeGbn = '1차';
    }else{ // 2차 사전등록
        joinForm.timeGbn = '2차';
    }

    joinForm.domain = $('#domain').val();

    joinForm.regionSi = joinForm.sido;
    joinForm.regionGu = nvl(joinForm.gugun,'-');
    joinForm.country = '';

    let partnerInfoArr = [];
    if(joinForm.partnerYn === 'Y'){
        $('.visitPartnerBox').each(function(index, element) {
            // 첫 번째 박스가 템플릿(빈 값) 역할인지, 실제 입력값인지 확인 필요
            // 여기서는 이름이 입력된 경우만 수집하도록 처리
            let pName = $(this).find('input[name=partnerName]').val();

            if (nvl(pName, '') !== '') {
                let visitPartnerObj = {
                    seq: $(this).find('input[type=hidden][name=partnerSeq]').val(),
                    visitorSeq: $('input[type=hidden][name=visitorSeq]').val(),
                    // 신청자 본인 정보 (부모값)
                    name: $('#name').val(),
                    phone: $('#phone').val(),
                    // 동반자 정보 (현재 row 값)
                    partnerName: pName,
                    partnerAge: $(this).find('input[name=partnerAge]').val()
                };
                partnerInfoArr.push(visitPartnerObj);
            }
        });
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

    return joinForm;
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
                confirmButtonColor: '#00a8ff',
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
                                confirmButtonColor: '#00a8ff',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                }
                            });*/

                        } else {
                            showMessage('', 'error', '에러 발생', '참관객 사전 등록 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
                        }
                    },
                    error: function (xhr, status) {
                        alert('오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
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

    let nowTime = getCurrentDate(); //yyyymmddhhmmss
    if(nowTime < '20260216000000'){ // 1차 사전등록
        joinForm.timeGbn = '1차';
    }else{ // 2차 사전등록
        joinForm.timeGbn = '2차';
    }

    joinForm.domain = $('#domain').val();

    joinForm.regionSi = '';
    joinForm.regionGu = '';

    let partnerInfoArr = [];
    if(joinForm.partnerYn === 'Y'){
        $('.visitPartnerBox').each(function(index, element) {
            // 첫 번째 박스가 템플릿(빈 값) 역할인지, 실제 입력값인지 확인 필요
            // 여기서는 이름이 입력된 경우만 수집하도록 처리
            let pName = $(this).find('input[name=partnerName]').val();

            if (nvl(pName, '') !== '') {
                let visitPartnerObj = {
                    seq: $(this).find('input[type=hidden][name=partnerSeq]').val(),
                    visitorSeq: $('input[type=hidden][name=visitorSeq]').val(),
                    // 신청자 본인 정보 (부모값)
                    name: $('#name').val(),
                    phone: $('#phone').val(),
                    // 동반자 정보 (현재 row 값)
                    partnerName: pName,
                    partnerAge: $(this).find('input[name=partnerAge]').val()
                };
                partnerInfoArr.push(visitPartnerObj);
            }
        });
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
        icon: 'info',
        title: '입력된 정보로 문의하시겠습니까?',
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
                        icon: 'info',
                        title: '문의하기',
                        html: '작성하신 내용으로 문의가 정상 접수되었습니다.<br>접수하신 연락처로 문의 답변 예정입니다.<br>감사합니다.',
                        allowOutsideClick: false,
                        confirmButtonColor: '#00a8ff',
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
        if(window.location.href.includes('/eng/')){
            alert("Content cannot exceed " + 90 + " bytes.");
        }else{
            alert("상담요청내용은 " + 90 + " Byte 를 초과할 수 없습니다.");
        }

        while(remain.innerText < 0) {
            temp_str = temp_str.substring(0, temp_str.length-1);
            content.value = temp_str;
            remain.innerText = String(90 - getByte(temp_str));
        }

        content.trigger('focus');
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

async function f_company_uploadFile_call(id, path) {

    const uploadPromises = []; // 프로미스 배열

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
            uploadPromises.push(f_company_file_upload_pdf(id, 'exhibitor_apply_form', 'companyLicenseFile', 'exhibitor/company/' + path));
        }else{
            uploadPromises.push(f_company_uploadFile(id, 'exhibitor_apply_form', 'companyLicenseFile', 'exhibitor/company/' + path));
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

        uploadPromises.push(f_company_uploadFile(id, 'exhibitor_apply_form', 'logoFile', 'exhibitor/company/' + path));
    }

    /* 요트/보트 출품 정보 - 제품사진 */
    /*let productImageFileList = $('input[type=file][name=productImageFile]');
    for(let i=0; i<productImageFileList.length; i++){
        let productImageNum = productImageFileList[i].id;
        productImageNum = productImageNum.toString().replace('productImageFile','');
        let productImageFile = productImageFileList[i].value;
        if (nvl(productImageFile, '') !== '') {
            //console.log('제품사진 파일 업로드' + (i+1));
            let productImageNum_child = Number.parseInt(productImageNum.split('_')[1]);
            if(productImageNum_child > 5){
                let productImageUploadFile_el = $('.productImageFile_li input[type=hidden][name=productImageUploadFile]');
                let fileId = (productImageUploadFile_el.eq(productImageUploadFile_el.length - 1).attr('id'));
                if(nvl(fileId,'') !== ''){
                    f_file_n_update({ id: fileId });
                }
            }

            f_company_uploadFile(id, 'exhibitor_apply_form', 'productImageFile' + productImageNum, 'exhibitor/company/' + path);
        }
    }*/

    /* 제품 노출 정보 - 제품사진 */
    let onlineImageFileList = document.getElementsByName('onlineImageFile');
    let onlineImageFileList_len = onlineImageFileList.length;
    for(let i=0; i<onlineImageFileList_len; i++){
        let onlineImageNum = onlineImageFileList[i].id;
        onlineImageNum = onlineImageNum.toString().replace('onlineImageFile','');
        let onlineImageFile = onlineImageFileList[i].value;
        if (nvl(onlineImageFile, '') !== '') {
            //console.log('홍보이미지 파일 업로드' + (i+1));
            let onlineImageNum_child = Number.parseInt(onlineImageNum.split('_')[1]);
            if(onlineImageNum_child > 5){
                let onlineImageUploadFile_el = $('.onlineImageFile_li input[type=hidden][name=onlineImageUploadFile]');
                let fileId = (onlineImageUploadFile_el.eq(onlineImageUploadFile_el.length - 1).attr('id'));
                if(nvl(fileId,'') !== ''){
                    f_file_n_update({ id: fileId });
                }
            }

            uploadPromises.push(f_company_uploadFile(id, 'exhibitor_apply_form', 'onlineImageFile' + onlineImageNum, 'exhibitor/company/' + path));
        }
    }

    // [핵심 수정] 업로드할 파일이 없으면 바로 종료 (로딩 멈춤 방지)
    if (uploadPromises.length === 0) {
        console.log(">> 업로드할 파일 없음 - 즉시 완료");
        return;
    }

    // 모든 업로드가 끝날 때까지 기다림
    try {
        await Promise.all(uploadPromises);
        console.log("All files uploaded successfully");
    } catch (err) {
        console.error("File upload error:", err);
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
    return new Promise((resolve, reject) => { // Promise로 감싸기
        let file = document.querySelector('#' + elementId);
        const formData = new FormData();

        if (!file || !file.files || file.files.length === 0) {
            resolve(); // 파일이 없으면 바로 성공 처리
            return;
        }

        const reFile = file.files[0];

        new Compressor(reFile, {
            strict: true,
            quality: 0.4,
            convertSize: 4000000,
            maxWidth: 1000,
            maxHeight: 700,
            success(result) {
                formData.append('uploadFile', result, result.name);

                fetch('/file/upload.do?gbn=' + path, {
                    method: 'post',
                    body: formData
                })
                    .then(response => response.json())
                    .then(res => {
                        if (res.uploadPath) { // res.uploadPath 체크 방식 수정
                            let uploadFileResponse = res.uploadPath + '\\' + res.fileName;
                            if (nvl(uploadFileResponse, "") !== '') {
                                let fullFilePath = uploadFileResponse.replaceAll('\\', '/');
                                let fullPath = fullFilePath.substring(0, fullFilePath.lastIndexOf('/') + 1);
                                let pureFileNameSplit = fullFilePath.split('/');
                                let fullFileName = pureFileNameSplit[pureFileNameSplit.length - 1];
                                let folderPath = pureFileNameSplit[pureFileNameSplit.length - 2];
                                let note = elementId.replace('File', '');

                                let jsonObj = {
                                    userId: userId,
                                    fullFilePath: fullFilePath,
                                    fullPath: fullPath,
                                    folderPath: folderPath,
                                    fullFileName: fullFileName,
                                    fileName: fullFileName,
                                    fileYn: 'Y',
                                    note: note
                                };

                                // DB 저장 (비동기 처리를 위해 ajaxConnect 대신 fetch 또는 ajax 사용 권장하나, 기존 로직 유지)
                                // ajaxConnect는 async: false이므로 여기서 멈춥니다.
                                ajaxConnectSimple('/file/upload/save.do', 'post', jsonObj);
                                resolve(res.uploadPath + '\\' + res.fileName); // 성공 시 resolve
                            } else {
                                resolve(); // 경로가 비어있으면 통과
                            }
                        } else {
                            resolve(); // 경로 없어도 통과
                        }
                    })
                    .catch(err => {
                        console.log(err);
                        resolve(); // 경로 없어도 통과
                    });
            },
            error(err) {
                console.log(err.message);
                resolve(); // 경로 없어도 통과
            },
        });
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
        if(file && file.files && file.files.length > 0){
            formData.append('request', file.files[0]);
        } else {
            return Promise.resolve(); // 파일 없으면 즉시 종료
        }
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
                if(res && res.uploadPath){
                    // PDF는 별도 DB 저장 로직이 필요하다면 여기에 추가 (기존 main.js에는 외부에 있었음)
                    // f_company_file_upload_pdf 함수에서 DB저장을 하고 있었으므로 여기선 경로만 리턴
                    resolve(res.uploadPath + '\\' + res.fileName);
                } else {
                    resolve(); // [수정] 실패/없음 경우에도 resolve 호출 (필수!)
                }
            })
            .catch(err => {
                console.error("PDF Upload Error:", err);
                resolve(); // [수정] 에러 발생 시에도 resolve 호출하여 전체 로직 진행
            });

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

    // 2. 삭제할 파일 ID를 배열에 저장 (서버 삭제는 나중에)
    if (fileId && fileId !== 'undefined' && fileId !== '') {
        deletedFileIds.push(fileId);
    }

    $(el).parent().remove();

    /*let jsonObj = {
        id: fileId
    }

    let resData = ajaxConnect('/file/upload/update.do', 'post', jsonObj);
    if(resData.resultCode === "0"){
        $(el).parent().remove();
    }*/
}

// 저장 시점에 일괄 삭제를 처리할 함수
async function processDeletedFiles() {
    if (deletedFileIds.length === 0) return;

    // 병렬로 삭제 요청 처리
    const deletePromises = deletedFileIds.map(fileId => {
        return new Promise((resolve) => {
            $.ajax({
                url: '/file/upload/update.do', // 삭제(사용안함) 처리 API
                method: 'post',
                data: JSON.stringify({ id: fileId }),
                contentType: 'application/json; charset=utf-8',
                success: function() { resolve(); },
                error: function(xhr) {
                    console.error("File delete failed:", fileId, xhr);
                    resolve(); // 에러가 나더라도 프로세스는 계속 진행
                }
            });
        });
    });

    await Promise.all(deletePromises);
    deletedFileIds = []; // 목록 초기화
}

function f_file_n_update(json){
    $.ajax({
        url: '/file/useYn/update.do',
        method: 'post',
        data: JSON.stringify(json),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    }).fail(function (xhr, status, errorThrown) {
        alert('파일 정보 업데이트 오류가 발생했습니다. 관리자에게 문의해 주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
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
            email: data[i].chargePersonEmail //받는이 메일주소
        }
        receiverArr.push(receiverObj);
    });

    returnJsonObj = {
        subject: '[2027 경기국제보트쇼] 참가업체 접수 완료', //제목
        body: "", //본문
        template: "6", //템플릿 번호
        receiver: receiverArr
    }

    return returnJsonObj;
}

/**
 * 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다.
 * @param value
 * @param defaultValue
 */
function nvl(value, defaultValue) {
    // null, undefined, "", "null" (문자열), 빈 객체를 처리하고 기본값 반환
    if (
        value === null ||                         // null 처리
        value === undefined ||                   // undefined 처리
        value === "" ||                          // 빈 문자열 처리
        value === "null" ||                      // "null" 문자열 처리
        (typeof value === "object" && !Object.keys(value || {}).length) // 빈 객체 처리
    ) {
        return defaultValue;
    }
    return value; // 해당 조건에 걸리지 않으면 원본 값 반환
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
            seq : param,
            transferYear : transferYear
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