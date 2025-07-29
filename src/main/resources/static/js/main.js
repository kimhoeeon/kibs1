var transferYear = '2026';

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
        subject: '[ 2026 경기국제보트쇼 ] 참가기업 인증 안내', //제목
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
            confirmButtonColor: '#3085d6',
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
                }
                //Fax
                let companyFax = preExhibitorInfo.companyFax;
                if(nvl(companyFax,'') !== ''){
                    $('#companyFax').val(companyFax);
                }
                //산업 분류
                let industryPart = preExhibitorInfo.industryPart;
                if(nvl(industryPart,'') !== ''){
                    $('#industryPart').val(industryPart).prop('selected', true).trigger('change');
                    if(industryPart === '기타'){
                        let industryPartEtc = preExhibitorInfo.industryPartEtc;
                        if(nvl(industryPartEtc,'') !== ''){
                            $('#industryPartEtc').val(industryPartEtc);
                        }
                    }
                }
                //임직원 수
                let employeeCnt = preExhibitorInfo.employeeCnt;
                if(nvl(employeeCnt,'') !== ''){
                    $('#employeeCnt').val(employeeCnt);
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
                //E-mail 마케팅정보 수신동의
                let emailMarketingYn = preExhibitorInfo.emailMarketingYn;
                if(nvl(emailMarketingYn,'') !== ''){
                    $('input[type=radio][name=emailMarketingYn][value="' + emailMarketingYn + '"]').prop('checked', true);
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
    let heightPixel = (window.outerHeight - window.innerHeight);
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
    let form = document.createElement('form');
    form.setAttribute('method', 'POST'); //POST 메서드 적용
    form.setAttribute('action', '/file/download.do');

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
}

function minCnt(el, cnt){
    let val = $(el).val() || 0;
    if(val < cnt){
        if(val !== 0){
            alert('독립부스는 2부스부터 신청 가능합니다.');
            $(el).val(0);
            calculateTotal();
        }
    }
}

function checkBooth(){
    let standAloneBoothCnt = $.number((parseInt($('#standAloneBoothCnt').val() || 0)));
    let assemblyBoothCnt = $.number((parseInt($('#assemblyBoothCnt').val() || 0)));
    let onlineBoothCnt = $.number((parseInt($('#onlineBoothCnt option:selected').val() || 0)));
    if((standAloneBoothCnt > 0 || assemblyBoothCnt > 0) && onlineBoothCnt > 0){
        alert('조립부스 또는 독립부스 신청 시, 온라인 부스는 무료 지원됩니다.');
        $('#onlineBoothCnt').val(0);
        $('#onlineBoothCnt option').eq(0).prop('selected',true);
        calculateTotal();
    }
}

/***************************************************************************************
 * 할인 조건 Function Start
 * *************************************************************************************/
const boothPrices = {
    standAlone: 1800000,
    assembly: 2100000,
    online: 1000000
};
const registrationFee = 100000; // 기본 등록비

// 현재 시간 (서버 시간 또는 사용자 로컬 시간)
const now = new Date();

// 1차 조기신청 할인 적용 마감일 (2025년 11월 21일 23:59:59)
const discount1Deadline = new Date('2025-11-21T23:59:59');

// 2차 조기신청 할인 적용 시작일 (2025년 11월 22일 00:00:00)
const discount2StartDate = new Date('2025-11-22T00:00:00');
// 2차 조기신청 할인 적용 마감일 (2025년 12월 19일 23:59:59)
const discount2Deadline = new Date('2025-12-19T23:59:59');
// 2차 조기신청 할인 선택 불가 시작일 (2025년 12월 20일 00:00:00)
const discount2DisableDate = new Date('2025-12-20T00:00:00');

// 첫 참가 할인 의 기본 할인 금액
const discount3BaseAmount = 500000;
// 첫 참가 할인 의 변경된 할인 금액
const discount3ChangedAmount = 300000;

// 단일 선택 그룹에 속하는 할인들 (5, 6, 7, 8, 9, 10)
const singleChoiceDiscounts = [
    '#discountScale1', '#discountScale2', '#discountScale3', '#discountScale4', '#discountScale5', '#discountScale6'
];

// 1차 조기신청 할인 체크박스 제어
function handleDiscountEarly1() {
    // 1차 조기신청 할인 적용 마감일 (2025년 11월 21일 23:59:59)
    const discount1Checkbox = $('#discountEarly1');
    const discount1Item = $('#discountItem1');

    // 최초 등록인지 여부 판단
    let isPrcTotal = Number.parseInt($('#prcTotal').val()) === 110000;

    if (now <= discount1Deadline) {
        // [수정] DB에서 데이터를 로드하지 않은 최초 접근 시에만 자동 선택
        //console.log(isPrcTotal , !discount1Checkbox.prop('checked'));
        if (isPrcTotal && !discount1Checkbox.prop('checked')) {
            discount1Checkbox.prop('checked', true);
        }
        discount1Item.removeClass('disabled');
        discount1Checkbox.prop('disabled', false);
        discount1Checkbox.off('click.preventCheck');

        let userUnchecked = false; // 사용자가 직접 해제했는지 추적
        discount1Checkbox.on('change.discountControl', function() { // 네임스페이스 추가
            if (!$(this).prop('checked')) {
                // 사용자가 체크를 해제한 경우
                if(confirm('1차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다. 해제하시겠습니까?')){
                    userUnchecked = true;
                }else{
                    $(this).prop('checked',true);
                }
                calculateTotal();
            } else {
                // 사용자가 다시 체크하려고 할 때 (마감일 이전에)
                if (userUnchecked) {
                    $(this).prop('checked', false); // 다시 체크되는 것을 막음
                    alert('1차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다.');
                    calculateTotal(); // 상태 변경 없으니 다시 계산 필요 없을 수 있으나 안전장치
                } else {
                    calculateTotal();
                }
            }
        });

    } else {
        // 마감일 이후: 선택 불가능, 클릭 시 알림, 자동 해제
        if (discount1Checkbox.prop('checked')) {
            discount1Checkbox.prop('checked', false);
        }
        discount1Checkbox.prop('disabled', true);
        discount1Item.addClass('disabled');
        discount1Checkbox.off('change.discountControl'); // change 이벤트 핸들러 제거

        discount1Item.on('click.preventCheck', function(e) {
            if (!$(e.target).is('input[type="checkbox"]')) {
                alert('1차 조기신청 할인은 현재 선택 불가합니다.');
            }
        });
    }
}

// 2차 조기신청 체크박스 제어
function handleDiscountEarly2() {
    const discount2Checkbox = $('#discountEarly2');
    const discount2Item = $('#discountItem2');

    // 최초 등록인지 여부 판단
    let isPrcTotal = Number.parseInt($('#prcTotal').val()) === 110000;

    if (now >= discount2StartDate && now <= discount2Deadline) {
        // [수정] DB에서 데이터를 로드하지 않은 최초 접근 시에만 자동 선택
        if (!isPrcTotal && !discount2Checkbox.prop('checked')) {
            discount2Checkbox.prop('checked', true);
        }
        discount2Item.removeClass('disabled');
        discount2Checkbox.prop('disabled', false);
        discount2Checkbox.off('click.preventCheck');

        let userUnchecked = false;
        discount2Checkbox.on('change.discountControl', function() { // 네임스페이스 추가
            if (!$(this).prop('checked')) {
                // 사용자가 체크를 해제한 경우
                if(confirm('2차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다. 해제하시겠습니까?')){
                    userUnchecked = true;
                }else{
                    $(this).prop('checked',true);
                }
                calculateTotal();
            } else {
                // 사용자가 다시 체크하려고 할 때 (마감일 이전에)
                if (userUnchecked) {
                    $(this).prop('checked', false); // 다시 체크되는 것을 막음
                    alert('2차 조기신청 할인은 자동으로 적용되며,\n한 번 해제하시면 다시 선택할 수 없습니다.');
                }
                calculateTotal(); // 상태 변경 없으니 다시 계산 필요 없을 수 있으나 안전장치
            }
        });

    }
    // 이외의 경우 (예: 이미 마감일이 지났지만, disabled 처리되지 않은 상태)
    // 초기화 및 비활성화 상태를 확실히 하기 위해 한 번 더 설정
    else {
        // 시작일 이전 또는 선택 불가 시작일 이후: 선택 불가능, 클릭 시 알림, 자동 해제
        if (discount2Checkbox.prop('checked')) {
            discount2Checkbox.prop('checked', false);
        }
        discount2Checkbox.prop('disabled', true);
        discount2Item.addClass('disabled');
        discount2Checkbox.off('change.discountControl'); // change 이벤트 핸들러 제거

        discount2Item.on('click.preventCheck', function(e) {
            if (!$(e.target).is('input[type="checkbox"]')) {
                alert('2차 조기신청 할인은 현재 선택 불가합니다.');
            }
        });
    }
}

function calculateTotal() {
    let standAloneQty = parseInt($('#standAloneBoothCnt').val()) || 0;
    let assemblyQty = parseInt($('#assemblyBoothCnt').val()) || 0;
    let onlineQty = parseInt($('#onlineBoothCnt').val()) || 0;

    let standAloneFee = standAloneQty * boothPrices.standAlone;
    let assemblyFee = assemblyQty * boothPrices.assembly;
    let onlineFee = onlineQty * boothPrices.online;

    // 금액 표시 단위를 '￦'으로 변경
    $('#standAloneBoothFee').val('￦ ' + standAloneFee.toLocaleString());
    $('#assemblyBoothFee').val('￦ ' + assemblyFee.toLocaleString());
    $('#onlineBoothFee').val('￦ ' + onlineFee.toLocaleString());

    let subtotal = standAloneFee + assemblyFee + onlineFee;
    let totalBooths = standAloneQty + assemblyQty + onlineQty;
    let totalDiscount = 0;

    // 첫 참가 할인 금액 결정 (규모할인 1~6 중 하나라도 선택되었는지 확인)
    let isSingleChoiceDiscountSelected = false;
    singleChoiceDiscounts.forEach(function(id) {
        if ($(id).prop('checked')) {
            isSingleChoiceDiscountSelected = true;
        }
    });

    let discount3Amount = discount3BaseAmount;
    if (isSingleChoiceDiscountSelected && $('#discountFirst').prop('checked')) {
        discount3Amount = discount3ChangedAmount; // 첫 참가 할인이 선택되었고 규모할인 1~6 중 하나라도 선택되었다면 30만원으로 변경
    }

    // 체크된 모든 할인 합산
    $('input[type="checkbox"]:checked').each(function() {
        const discountId = $(this).attr('id');
        let discountPerBooth = 0;

        if (discountId === 'discountFirst') {
            discountPerBooth = discount3Amount; // 첫 참가 할인은 결정된 금액 사용
        } else {
            discountPerBooth = parseInt($(this).data('discount'));
        }
        totalDiscount += discountPerBooth * totalBooths;
    });

    let finalAmount = subtotal - totalDiscount + registrationFee;

    // 총 금액 표시 단위를 '￦'으로 변경
    $('#totalAmount').val('￦ ' + Math.max(0, finalAmount).toLocaleString());
}

/***************************************************************************************
 * 할인 조건 Function End
 * *************************************************************************************/

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
            $(checkbox).prop('checked',false);
        }
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
        showMessage('#id', 'error', '[ 회원 정보 ]', 'ID를 입력해 주세요.', '');
        return false;
    }

    // ID 체크
    let jsonStr = { id : id , transferYear: transferYear};// 2026
    let seq = ajaxConnectSimple('/searchExhibitorNewSeq.do', 'post', jsonStr);
    if(nvl(seq,'') !== ''){
        Swal.fire({
            icon: 'warning',
            title: '[ 회원 정보 ]',
            html: '<span style="font-size: 1.2em;">해당 ID [ ' + id + ' ] 의<br>비밀번호 초기화를 요청하시겠습니까?</span>',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '요청하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let email = ajaxConnectSimple('/getExhibitorNewEmail.do', 'post', jsonStr);
                if(nvl(email,'') !== ''){
                    let jsonObj = {
                        subject: '[2026 경기국제보트쇼] 비밀번호 초기화 요청', //제목
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
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            });
                            return false;
                        }
                    }else{
                        Swal.fire({
                            icon: 'info',
                            title: '[ 회원 정보 ]',
                            html: '<span style="font-size: 1.2em;">해당 ID에 등록된 Email 주소로 메일 전송이 실패하였습니다.<br>경기국제보트쇼 사무국으로 문의 바랍니다.<br>Tel. 1670-8785</span>',
                            allowOutsideClick: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '확인'
                        });
                        return false;
                    }
                }else{
                    Swal.fire({
                        icon: 'info',
                        title: '[ 회원 정보 ]',
                        html: '<span style="font-size: 1.2em;">해당 ID에 등록된 Email 주소가 없습니다.<br>경기국제보트쇼 사무국으로 문의 바랍니다.<br>Tel. 1670-8785</span>',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
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
    let id = $('#id').val();

    if(nvl(id,'') !== ''){
        if(id.length < 10){
            $(el).siblings('.cmnt').css('color', '#AD1D1D');
            $(el).siblings('.cmnt').html('검사 결과 : 사업자 등록번호 10자리를 입력해 주세요.');
            $('#idCheck').val('false');
            return;
        }

        // ID 중복체크
        let jsonStr = { id : id , transferYear: transferYear};// 2026
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

function step_01_check(exhibitorSeq){

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
        if(!checkUrl(companyHomepage)){
            showMessage('#companyHomepage', 'error', '[ 참가업체 정보 ]', '홈페이지 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
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
        if(industryPart === '기타'){
            if(industryPartEtc === ''){
                showMessage('#industryPartEtc', 'error', '[ 참가업체 정보 ]', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    // 임직원 수
    let employeeCnt = $('#employeeCnt').val();
    if(nvl(employeeCnt,'') === ''){
        showMessage('#employeeCnt', 'error', '[ 참가업체 정보 ]', '임직원 수 항목을 입력해 주세요.', '');
        return false;
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

    // 이메일 마케팅 수신 동의 여부
    let email_marketing_yn_el = $('input[type=radio][name=emailMarketingYn]:checked');
    let email_marketing_yn;
    if(nvl(email_marketing_yn_el,'') === ''){
        showMessage('', 'error', '[ 담당자 정보 ]', 'E-mail 마케팅정보 수신동의여부를 체크해 주세요.', '');
        return false;
    }else{
        email_marketing_yn = email_marketing_yn_el.val();
    }
    //console.log(email_marketing_yn);

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
            let chargePersonEmail_val = charge_person_email_el.eq(i).val();
            let chargePersonEmail = '';
            if(nvl(chargePersonEmail_val,'') !== ''){
                chargePersonEmail = chargePersonEmail_val + '@' + charge_person_domain_el.eq(i).val();
            }
            let chargePersonList_json_obj = {
                seq: $('input[type=hidden][name=chargeSeq]').eq(i).val(),
                exSeq: exhibitorSeq,
                chargePersonName: charge_person_name_el.eq(i).val(),
                chargePersonPosition: charge_person_position_el.eq(i).val(),
                chargePersonDepart: charge_person_depart_el.eq(i).val(),
                chargePersonTel: charge_person_tel_el.eq(i).val(),
                chargePersonPhone: charge_person_phone_el.eq(i).val(),
                chargePersonEmail: chargePersonEmail,
            };
            chargePersonList_json_arr.push(chargePersonList_json_obj);
        }
    }

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

    /******************** 참가분야 ********************/
    
    // 참가분야
    let field_part_el = $('input[type=checkbox][name=fieldPart]:checked');
    let field_part_len = field_part_el.length;
    let fieldPart = '';
    if(field_part_len !== 0){
        for(let i=0; i<field_part_len; i++){
            fieldPart += field_part_el.eq(i).val();
            if((i+1) !== field_part_len){
                fieldPart += '^';
            }
        }
    }else{
        showMessage('', 'error', '[ 참가분야 ]', '참가분야를 하나 이상 선택해 주세요.', '');
        return false;
    }
    //console.log(field_part);

    /******************** 전시품 정보 ********************/

    let product_option_len = Number.parseInt($('.exhiInfoNum').text());

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
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 분류(품목) 첫 번째 항목을 선택해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 분류(품목) 두 번째 항목을 선택해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '제품명을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '수량을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '제조사(브랜드)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '길이(cm)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '너비(cm)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '높이(cm)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '중량(kg)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '소재를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '연식을 입력해 주세요.', '');
        return false;
    }

    // 제품 설명(국문)
    let product_intro_ko_el = $('textarea[name=productIntroKo]');
    /*let product_intro_ko_len = product_intro_ko_el.length;
    let product_intro_ko_flag = true;
    for(let i=0; i<product_intro_ko_len; i++){
        if(product_intro_ko_el.eq(i).val() === ''){
            product_intro_ko_flag = false;
        }
    }
    if(!product_intro_ko_flag){
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 설명(국문)을 입력해 주세요.', '');
        return false;
    }*/

    // 제품 설명(영문)
    let product_intro_en_el = $('textarea[name=productIntroEn]');
    /*let product_intro_en_len = product_intro_en_el.length;
    let product_intro_en_flag = true;
    for(let i=0; i<product_intro_en_len; i++){
        if(product_intro_en_el.eq(i).val() === ''){
            product_intro_en_flag = false;
        }
    }
    if(!product_intro_en_flag){
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 설명(영문)을 입력해 주세요.', '');
        return false;
    }*/

    // 제품사진
    let exhiPrdBox = $('.exhiPrdBox');
    for(let i=0; i<exhiPrdBox.length; i++){
        let inputFile = exhiPrdBox.eq(i).find('.upload_name').val();
        let preFileList = $('.exhiInfoBox').eq(i).find('.preValueList').find('li.productImageFile_li').length;
        if(nvl(inputFile,"") === "" && preFileList === 0){
            showMessage('', 'error', '[ 전시품 정보 ]', '제품사진을 첨부해 주세요.', '');
            return false;
        }
    }

    // 제품링크
    let product_link_el = $('input[type=text][name=productLink]');
    for(let i=0; i<product_link_el.length; i++){
        let product_link = product_link_el.eq(i).val();
        if(nvl(product_link,'') !== ''){
            if(!checkUrl(product_link)){
                showMessage('', 'error', '[ 전시품 정보 ]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
                return false;
            }
        }
    }

    //전시품정보 Json Create
    let productList_json_arr = [];
    let productCnt = Number.parseInt($('.exhiInfoNum:last').text());
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
            let productList_json_obj = {
                seq: $('input[type=hidden][name=productSeq]').eq(i).val(),
                exSeq: exhibitorSeq,
                productOptionBig: productOptionBig,
                productOptionSmall: productOptionSmall,
                productNameKo: product_name_ko_el.eq(i).val(),
                productQty: product_qty_el.eq(i).val(),
                productBrand: product_brand_el.eq(i).val(),
                productLength: product_length_el.eq(i).val(),
                productWidth: product_width_el.eq(i).val(),
                productHeight: product_height_el.eq(i).val(),
                productWeight: product_weight_el.eq(i).val(),
                productMaterial: product_material_el.eq(i).val(),
                productYear: product_year_el.eq(i).val(),
                productIntroKo: product_intro_ko_el.eq(i).val(),
                productIntroEn: product_intro_en_el.eq(i).val(),
                productLink: product_link_el.eq(i).val(),
                note: (i+1)
            };
            productList_json_arr.push(productList_json_obj);
        }
    }

    /******************** 온라인 전시관 정보 ********************/

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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 분류 첫 번째 항목을 선택해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 분류 두 번째 항목을 선택해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품명(국문)을 입력해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품사진
        let onlinePrdBox = $('.onlinePrdBox');
        for(let i=0; i<onlinePrdBox.length; i++){
            let inputFile = onlinePrdBox.eq(i).find('.upload_name').val();
            let preFileList = $('.onlineInfoBox').eq(i).find('.preValueList').find('li.onlineImageFile_li').length;
            if(nvl(inputFile,"") === "" && preFileList === 0){
                showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품사진을 첨부해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 설명(국문)을 입력해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품링크
        let online_link_el = $('input[type=text][name=onlineLink]');
        for(let i=0; i<online_link_el.length; i++){
            let online_link = online_link_el.eq(i).val();
            if(nvl(online_link,'') !== ''){
                if(!checkUrl(online_link)){
                    showMessage('', 'error', '[ 온라인 전시관 정보 ]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
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

        let onlineCnt = Number.parseInt($('.exhiInfoNum:last').text());
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
        employeeCnt: employeeCnt,
        prePartYear: prePartYear,
        memberCompanyYn: memberCompanyYn,
        /* 담당자 정보 */
        name: name,
        position: position,
        depart: depart,
        tel: tel,
        phone: phone,
        email: email1 + '@' + email2,
        emailMarketingYn: email_marketing_yn,
        /* 부담당자 정보 */
        chargePersonList: chargePersonList_json_arr,
        /* 상세 정보 */
        companyIntroVideo: companyIntroVideo,
        companyIntroKo: companyIntroKo,
        companyIntroEn: companyIntroEn,
        companyPurposeKo: companyPurposeKo,
        companyPurposeEn: companyPurposeEn,
        newItemIntroKo: newItemIntroKo,
        newItemIntroEn: newItemIntroEn,
        promotionPlan: promotionPlan,
        fieldPart: fieldPart,
        /* 전시품 정보 */
        productList: productList_json_arr,
        /* 온라인 전시관 정보 */
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
            f_company_uploadFile_call(exhibitorSeq, exhibitorSeq);

            let timerInterval;
            Swal.fire({
                title: "정보 저장 중",
                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해 주세요.<br>첨부된 파일의 용량/크기/갯수에 따라 시간이 조금 더 소요될 수 있습니다.",
                allowOutsideClick: false,
                timer: 5000,
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
                        title: '[ 참가업체 정보 ]',
                        html: '<span style="font-size: 1.2em;">기본 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {

                            /* 전시품 사진 번호 재부여 */
                            let product_File_json_obj = {
                                seq: exhibitorSeq,
                                productList: productList_json_arr
                            }
                            let product_res = ajaxConnect('/mypage/step/updateProductNewFileNote.do', 'post', product_File_json_obj);

                            /* 온라인 제품 사진 번호 재부여 */
                            let onlineFile_json_obj = {
                                seq: exhibitorSeq,
                                onlineList: onlineList_json_arr
                            }
                            let online_res = ajaxConnect('/mypage/step/updateOnlineNewFileNote.do', 'post', onlineFile_json_obj);

                            if(product_res.resultCode === "0" && online_res.resultCode === "0"){
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

    let boothType = '등록비';
    let boothPrcSum = 0;

    // 부스 신청 - 등록비 - 수량
    let registrationCnt = 1;

    // 부스 신청 - 등록비 - 금액
    let registrationFee = 100000;

    boothPrcSum += registrationFee;

    // 부스 신청 - 독립부스 - 수량
    let standAloneBoothCnt = parseInt($('#standAloneBoothCnt').val());

    if(standAloneBoothCnt > 0){
        boothType += ',독립부스';
    }

    // 부스 신청 - 독립부스 - 금액
    let standAloneBoothFee = $('#standAloneBoothFee').val();

    boothPrcSum += wonToNumber(standAloneBoothFee);

    // 부스 신청 - 조립부스 - 수량
    let assemblyBoothCnt = parseInt($('#assemblyBoothCnt').val());

    if(assemblyBoothCnt > 0){
        boothType += ',조립부스';
    }

    // 부스 신청 - 조립부스 - 금액
    let assemblyBoothFee = $('#assemblyBoothFee').val();

    boothPrcSum += wonToNumber(assemblyBoothFee);

    // 부스 신청 - 온라인부스 - 수량
    let onlineBoothCnt = parseInt($('#onlineBoothCnt option:checked').val());

    if(onlineBoothCnt > 0){
        boothType += ',온라인부스';
    }

    // 부스 신청 - 온라인부스 - 금액
    let onlineBoothFee = $('#onlineBoothFee').val();

    boothPrcSum += wonToNumber(onlineBoothFee);

    let discountType = '';
    let discountPrcSum = 0;
    let boothTotalCnt = standAloneBoothCnt + assemblyBoothCnt + onlineBoothCnt;

    // 할인적용 - 1차 조기신청
    let discountEarly1 = $('#discountEarly1').is(':checked');
    if(discountEarly1){
        discountType += ',1차조기신청';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 2차 조기신청
    let discountEarly2 = $('#discountEarly2').is(':checked');
    if(discountEarly2){
        discountType += ',2차조기신청';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 첫 참가 할인
    let discountFirst = $('#discountFirst').is(':checked');
    if(discountFirst){
        discountType += ',첫참가';
        
        // 규모할인 체크시 30만원으로 적용
        if($('.single-choice-discount input[type="checkbox"]').is(':checked')){
            discountPrcSum += (boothTotalCnt * 300000);
        }else{
            discountPrcSum += (boothTotalCnt * 500000);
        }
    }

    // 할인적용 - 재참가할인 All
    let discountRe = $('#discountRe').is(':checked');
    if(discountRe){
        discountType += ',재참가할인';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 규모할인 1 (10부스 이상)
    let discountScale1 = $('#discountScale1').is(':checked');
    if(discountScale1){
        discountType += ',규모할인1';
        discountPrcSum += (boothTotalCnt * 400000);
    }

    // 할인적용 - 규모할인 2 (20부스 이상)
    let discountScale2 = $('#discountScale2').is(':checked');
    if(discountScale2){
        discountType += ',규모할인2';
        discountPrcSum += (boothTotalCnt * 650000);
    }

    // 할인적용 - 규모할인 3 (30부스 이상)
    let discountScale3 = $('#discountScale3').is(':checked');
    if(discountScale3){
        discountType += ',규모할인3';
        discountPrcSum += (boothTotalCnt * 750000);
    }

    // 할인적용 - 규모할인 4 (40부스 이상)
    let discountScale4 = $('#discountScale4').is(':checked');
    if(discountScale4){
        discountType += ',규모할인4';
        discountPrcSum += (boothTotalCnt * 800000);
    }

    // 할인적용 - 규모할인 5 (50부스 이상)
    let discountScale5 = $('#discountScale5').is(':checked');
    if(discountScale5){
        discountType += ',규모할인5';
        discountPrcSum += (boothTotalCnt * 850000);
    }

    // 할인적용 - 규모할인 6 (60부스 이상)
    let discountScale6 = $('#discountScale6').is(':checked');
    if(discountScale6){
        discountType += ',규모할인6';
        discountPrcSum += (boothTotalCnt * 900000);
    }

    // 할인적용 - 한국해양레저산업협회 할인
    let discountLeisure = $('#discountLeisure').is(':checked');
    if(discountLeisure){
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

    if(boothTotalCnt === 0){
        showMessage('', 'error', '[ 전시부스 신청 ]', '부스(독립,조립,온라인)를 하나 이상 신청해 주세요.', '');
        return;
    }

    let booth_json_obj = {
        seq: exhibitorSeq,
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registrationCnt,
        registrationFee: registrationFee,
        standAloneBoothCnt: standAloneBoothCnt,
        standAloneBoothFee: Number.parseInt(wonToInt(standAloneBoothFee)),
        assemblyBoothCnt: assemblyBoothCnt,
        assemblyBoothFee: Number.parseInt(wonToInt(assemblyBoothFee)),
        onlineBoothCnt: onlineBoothCnt,
        onlineBoothFee: Number.parseInt(wonToInt(onlineBoothFee)),
        discountEarly1: discountEarly1,
        discountEarly2: discountEarly2,
        discountFirst: discountFirst,
        discountRe: discountRe,
        discountScale1: discountScale1,
        discountScale2: discountScale2,
        discountScale3: discountScale3,
        discountScale4: discountScale4,
        discountScale5: discountScale5,
        discountScale6: discountScale6,
        discountLeisure: discountLeisure,
        discountYn: discountYn,
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum
    }

    let resData = ajaxConnect('/apply/step/updateExhibitorNewBooth.do', 'post', booth_json_obj);

    let returnPath = "";
    //console.log(resData);
    if(resData.resultCode === "0") {

        Swal.fire({
            icon: 'info',
            title: '[ 전시부스 신청 ]',
            html: '<span style="font-size: 1.2em;">부스 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
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
        showMessage('', 'error', '[ 전시부스 신청 ]', '전시부스 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function wonToInt(won){
    return won.replace(/\s/g, '').replace(/\￦/g, '').replace(/\,/g, '');
}

/* //////////////////////////////// end:step_2_1 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_2 ////////////////////////////////// */
function step_2_2_check(exhibitorSeq){

    // 상호간판 신청 - 상호간판명 (국문)
    let companySignNameKo = $('#companySignNameKo').val();
    if(nvl(companySignNameKo,'') === ''){
        showMessage('#companySignNameKo', 'error', '[ 상호간판 신청 ]', '상호간판명(국문)을 입력해 주세요.', '');
        return false;
    }

    // 상호간판 신청 - 상호간판명 (영문)
    let companySignNameEn = $('#companySignNameEn').val();
    if(nvl(companySignNameEn,'') === ''){
        showMessage('#companySignNameEn', 'error', '[ 상호간판 신청 ]', '상호간판명(영문)을 입력해 주세요.', '');
        return false;
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
            confirmButtonColor: '#3085d6',
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
}

/* //////////////////////////////// end:step_2_2 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_3 ////////////////////////////////// */
function step_2_3_check(exhibitorSeq){

    // 유틸리티 신청정보 - 주간단상 - 수량
    let utility_jugan_cnt = parseInt($('#utility_jugan_cnt').val());

    // 유틸리티 신청정보 - 주간단상 - 금액
    let utility_jugan_fee = $('#utility_jugan_fee').val();

    // 유틸리티 신청정보 - 24시간용 - 수량
    let utility_day_cnt = parseInt($('#utility_day_cnt').val());

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_day_fee = $('#utility_day_fee').val();

    // 유틸리티 신청정보 - 압축공기 - 수량
    let utility_compressed_air_cnt = parseInt($('#utility_compressed_air_cnt').val());

    // 유틸리티 신청정보 - 압축공기 - 금액
    let utility_compressed_air_fee = $('#utility_compressed_air_fee').val();

    // 유틸리티 신청정보 - 급배수 - 수량
    let utility_water_basic_cnt = parseInt($('#utility_water_basic_cnt').val());

    // 유틸리티 신청정보 - 급배수 - 금액
    let utility_water_basic_fee = $('#utility_water_basic_fee').val();

    // 유틸리티 신청정보 - 인터넷 - 수량
    let utility_internet_cnt = parseInt($('#utility_internet_cnt').val());

    // 유틸리티 신청정보 - 인터넷 - 금액
    let utility_internet_fee = $('#utility_internet_fee').val();

    // 유틸리티 신청정보 - 파이텍스 (신품) - 수량
    let utility_pytex_new_cnt = parseInt($('#utility_pytex_new_cnt').val());

    // 유틸리티 신청정보 - 파이텍스 (신품) - 금액
    let utility_pytex_new_fee = $('#utility_pytex_new_fee').val();

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 수량
    let utility_pytex_re_cnt = parseInt($('#utility_pytex_re_cnt').val());

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 금액
    let utility_pytex_re_fee = $('#utility_pytex_re_fee').val();

    // 유틸리티 신청정보 - 참관객/바이어 바코드 리더기 - 수량
    let utility_barcode_cnt = parseInt($('#utility_barcode_cnt').val());

    // 유틸리티 신청정보 - 참관객/바이어 바코드 리더기 - 금액
    let utility_barcode_fee = $('#utility_barcode_fee').val();

    let utilityPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let utility_json_obj = {
        seq: exhibitorSeq,
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

    let resData = ajaxConnect('/apply/step/updateExhibitorNewUtility.do', 'post', utility_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 유틸리티 신청 ]',
            html: '<span style="font-size: 1.2em;">유틸리티 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
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
        showMessage('', 'error', '[ 유틸리티 신청 ]', '유틸리티 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
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
            confirmButtonColor: '#3085d6',
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
}

/* //////////////////////////////// end:step_2_4 ////////////////////////////////// */

/* //////////////////////////////// begin:step_2_5 ////////////////////////////////// */

function step_2_5_check(exhibitorSeq){

    let giftList_json_obj = {
        seq: exhibitorSeq,
        giftList: gift_add_json_arr,
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
            confirmButtonColor: '#3085d6',
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
                        giftFullFilePath = giftFullFilePath.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
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
            title: '참가업체 정보',
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
                    licenseFullFilePath = licenseFullFilePath.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
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
            confirmButtonColor: '#3085d6',
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
            confirmButtonColor: '#3085d6',
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
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result) => {
            if (result.isConfirmed) {

                let charge_jsonObj = {
                    exSeq: exhibitorSeq,
                }
                let charge_resData = ajaxConnect('/apply/step/directory/selectChargeNewInfo.do','post', charge_jsonObj);

                let jsonObj = makeJsonFormat(charge_resData);
                //console.log(JSON.stringify(jsonObj));
                let resData = ajaxConnect('/mail/send.do', 'post', jsonObj);
                //console.log(i , resData);
                if (resData.resultCode === "0") {
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

    // 이메일 마케팅 수신 동의 여부
    let email_marketing_yn_el = $('input[type=radio][name=emailMarketingYn]:checked');
    let email_marketing_yn;
    if(email_marketing_yn_el === null){
        showMessage('', 'error', '[ 회원 계정 정보 ]', 'E-mail 마케팅정보 수신동의여부를 체크해 주세요.', '');
        return false;
    }else{
        email_marketing_yn = email_marketing_yn_el.val();
    }
    //console.log(email_marketing_yn);

    let jsonObj = {
        seq: exhibitorSeq,
        password: password,
        name: name,
        position: position,
        depart: depart,
        tel: tel,
        phone: phone,
        email: email_input1 + '@' + email_input2,
        emailMarketingYn: email_marketing_yn,
    };

    let resData = ajaxConnect('/mypage/step/exhibitorNew/updateInfo.do','post',jsonObj);

    if(resData.resultCode === "0") {

            Swal.fire({
                icon: 'info',
                title: '[ 회원 계정 정보 ]',
                html: '<span style="font-size: 1.2em;">회원 계정 정보가 저장되었습니다.</span>',
                allowOutsideClick: false,
                confirmButtonColor: '#3085d6',
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

function my_step_01_check(exhibitorSeq){

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
        if(!checkUrl(companyHomepage)){
            showMessage('#companyHomepage', 'error', '[ 참가업체 정보 ]', '홈페이지 주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
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
        if(industryPart === '기타'){
            if(industryPartEtc === ''){
                showMessage('#industryPartEtc', 'error', '[ 참가업체 정보 ]', '산업 분류 기타 선택 시 항목을 입력해 주세요.', '');
                return false;
            }
        }
    }

    // 임직원 수
    let employeeCnt = $('#employeeCnt').val();
    if(nvl(employeeCnt,'') === ''){
        showMessage('#employeeCnt', 'error', '[ 참가업체 정보 ]', '임직원 수 항목을 입력해 주세요.', '');
        return false;
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

    // 이메일 마케팅 수신 동의 여부
    let email_marketing_yn_el = $('input[type=radio][name=emailMarketingYn]:checked');
    let email_marketing_yn;
    if(nvl(email_marketing_yn_el,'') === ''){
        showMessage('', 'error', '[ 담당자 정보 ]', 'E-mail 마케팅정보 수신동의여부를 체크해 주세요.', '');
        return false;
    }else{
        email_marketing_yn = email_marketing_yn_el.val();
    }
    //console.log(email_marketing_yn);

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
            let chargePersonEmail_val = charge_person_email_el.eq(i).val();
            let chargePersonEmail = '';
            if(nvl(chargePersonEmail_val,'') !== ''){
                chargePersonEmail = chargePersonEmail_val + '@' + charge_person_domain_el.eq(i).val();
            }
            let chargePersonList_json_obj = {
                seq: $('input[type=hidden][name=chargeSeq]').eq(i).val(),
                exSeq: exhibitorSeq,
                chargePersonName: charge_person_name_el.eq(i).val(),
                chargePersonPosition: charge_person_position_el.eq(i).val(),
                chargePersonDepart: charge_person_depart_el.eq(i).val(),
                chargePersonTel: charge_person_tel_el.eq(i).val(),
                chargePersonPhone: charge_person_phone_el.eq(i).val(),
                chargePersonEmail: chargePersonEmail,
            };
            chargePersonList_json_arr.push(chargePersonList_json_obj);
        }
    }

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

    /******************** 참가분야 ********************/

        // 참가분야
    let field_part_el = $('input[type=checkbox][name=fieldPart]:checked');
    let field_part_len = field_part_el.length;
    let fieldPart = '';
    if(field_part_len !== 0){
        for(let i=0; i<field_part_len; i++){
            fieldPart += field_part_el.eq(i).val();
            if((i+1) !== field_part_len){
                fieldPart += '^';
            }
        }
    }else{
        showMessage('', 'error', '[ 참가분야 ]', '참가분야를 하나 이상 선택해 주세요.', '');
        return false;
    }
    //console.log(field_part);

    /******************** 전시품 정보 ********************/

    let product_option_len = Number.parseInt($('.exhiInfoNum').text());

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
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 분류(품목) 첫 번째 항목을 선택해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 분류(품목) 두 번째 항목을 선택해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '제품명을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '수량을 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '제조사(브랜드)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '길이(cm)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '너비(cm)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '높이(cm)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '중량(kg)를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '소재를 입력해 주세요.', '');
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
        showMessage('', 'error', '[ 전시품 정보 ]', '연식을 입력해 주세요.', '');
        return false;
    }

    // 제품 설명(국문)
    let product_intro_ko_el = $('textarea[name=productIntroKo]');
    /*let product_intro_ko_len = product_intro_ko_el.length;
    let product_intro_ko_flag = true;
    for(let i=0; i<product_intro_ko_len; i++){
        if(product_intro_ko_el.eq(i).val() === ''){
            product_intro_ko_flag = false;
        }
    }
    if(!product_intro_ko_flag){
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 설명(국문)을 입력해 주세요.', '');
        return false;
    }*/

    // 제품 설명(영문)
    let product_intro_en_el = $('textarea[name=productIntroEn]');
    /*let product_intro_en_len = product_intro_en_el.length;
    let product_intro_en_flag = true;
    for(let i=0; i<product_intro_en_len; i++){
        if(product_intro_en_el.eq(i).val() === ''){
            product_intro_en_flag = false;
        }
    }
    if(!product_intro_en_flag){
        showMessage('', 'error', '[ 전시품 정보 ]', '제품 설명(영문)을 입력해 주세요.', '');
        return false;
    }*/

    // 제품사진
    let exhiPrdBox = $('.exhiPrdBox');
    for(let i=0; i<exhiPrdBox.length; i++){
        let inputFile = exhiPrdBox.eq(i).find('.upload_name').val();
        let preFileList = $('.exhiInfoBox').eq(i).find('.preValueList').find('li.productImageFile_li').length;
        if(nvl(inputFile,"") === "" && preFileList === 0){
            showMessage('', 'error', '[ 전시품 정보 ]', '제품사진을 첨부해 주세요.', '');
            return false;
        }
    }

    // 제품링크
    let product_link_el = $('input[type=text][name=productLink]');
    for(let i=0; i<product_link_el.length; i++){
        let product_link = product_link_el.eq(i).val();
        if(nvl(product_link,'') !== ''){
            if(!checkUrl(product_link)){
                showMessage('', 'error', '[ 전시품 정보 ]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
                return false;
            }
        }
    }

    //전시품정보 Json Create
    let productList_json_arr = [];
    let productCnt = Number.parseInt($('.exhiInfoNum:last').text());
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
            let productList_json_obj = {
                seq: $('input[type=hidden][name=productSeq]').eq(i).val(),
                exSeq: exhibitorSeq,
                productOptionBig: productOptionBig,
                productOptionSmall: productOptionSmall,
                productNameKo: product_name_ko_el.eq(i).val(),
                productQty: product_qty_el.eq(i).val(),
                productBrand: product_brand_el.eq(i).val(),
                productLength: product_length_el.eq(i).val(),
                productWidth: product_width_el.eq(i).val(),
                productHeight: product_height_el.eq(i).val(),
                productWeight: product_weight_el.eq(i).val(),
                productMaterial: product_material_el.eq(i).val(),
                productYear: product_year_el.eq(i).val(),
                productIntroKo: product_intro_ko_el.eq(i).val(),
                productIntroEn: product_intro_en_el.eq(i).val(),
                productLink: product_link_el.eq(i).val(),
                note: (i+1)
            };
            productList_json_arr.push(productList_json_obj);
        }
    }

    /******************** 온라인 전시관 정보 ********************/

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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 분류 첫 번째 항목을 선택해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 분류 두 번째 항목을 선택해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품명(국문)을 입력해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품사진
        let onlinePrdBox = $('.onlinePrdBox');
        for(let i=0; i<onlinePrdBox.length; i++){
            let inputFile = onlinePrdBox.eq(i).find('.upload_name').val();
            let preFileList = $('.onlineInfoBox').eq(i).find('.preValueList').find('li.onlineImageFile_li').length;
            if(nvl(inputFile,"") === "" && preFileList === 0){
                showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품사진을 첨부해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 설명(국문)을 입력해 주세요.', '');
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
            showMessage('', 'error', '[ 온라인 전시관 정보 ]', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }

        // 제품링크
        let online_link_el = $('input[type=text][name=onlineLink]');
        for(let i=0; i<online_link_el.length; i++){
            let online_link = online_link_el.eq(i).val();
            if(nvl(online_link,'') !== ''){
                if(!checkUrl(online_link)){
                    showMessage('', 'error', '[ 온라인 전시관 정보 ]', '주소는 http:// 나 https:// 를 포함하여 입력해 주세요.', '');
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

        let onlineCnt = Number.parseInt($('.exhiInfoNum:last').text());
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
        employeeCnt: employeeCnt,
        prePartYear: prePartYear,
        memberCompanyYn: memberCompanyYn,
        /* 담당자 정보 */
        name: name,
        position: position,
        depart: depart,
        tel: tel,
        phone: phone,
        email: email1 + '@' + email2,
        emailMarketingYn: email_marketing_yn,
        /* 부담당자 정보 */
        chargePersonList: chargePersonList_json_arr,
        /* 상세 정보 */
        companyIntroVideo: companyIntroVideo,
        companyIntroKo: companyIntroKo,
        companyIntroEn: companyIntroEn,
        companyPurposeKo: companyPurposeKo,
        companyPurposeEn: companyPurposeEn,
        newItemIntroKo: newItemIntroKo,
        newItemIntroEn: newItemIntroEn,
        promotionPlan: promotionPlan,
        fieldPart: fieldPart,
        /* 전시품 정보 */
        productList: productList_json_arr,
        /* 온라인 전시관 정보 */
        onlineList: onlineList_json_arr,
        /* 수출상담회 */
        exportMeetingYn: export_meeting_yn,
        buyerList: buyer_add_json_arr
    };

    //console.log('data : ' + JSON.stringify(exhibitor_json_obj));

    let resData = ajaxConnect('/mypage/step/updateExhibitorNew.do', 'post', exhibitor_json_obj);
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
                html: "입력하신 정보를 저장 중입니다.<br><b></b> milliseconds.<br>현재 화면을 유지해 주세요.",
                allowOutsideClick: false,
                timer: 5000,
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

                    let swal_html = '<span style="font-size: 1.2em;">기본 정보가 저장되었습니다.';
                    if(!link.includes('/mng/')){
                        swal_html = '<span style="font-size: 1.2em;">기본 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>';
                    }

                    Swal.fire({
                        icon: 'info',
                        title: '[ 참가업체 정보 ]',
                        html: swal_html,
                        allowOutsideClick: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {

                            /* 전시품 사진 번호 재부여 */
                            let product_File_json_obj = {
                                seq: exhibitorSeq,
                                productList: productList_json_arr
                            }
                            let product_res = ajaxConnect('/mypage/step/updateProductNewFileNote.do', 'post', product_File_json_obj);

                            /* 온라인 제품 사진 번호 재부여 */
                            let onlineFile_json_obj = {
                                seq: exhibitorSeq,
                                onlineList: onlineList_json_arr
                            }
                            let online_res = ajaxConnect('/mypage/step/updateOnlineNewFileNote.do', 'post', onlineFile_json_obj);

                            if(product_res.resultCode === "0" && online_res.resultCode === "0"){
                                if(!link.includes('/mng/')) {
                                    f_page_move('/mypage/step2_1.do', exhibitorSeq);
                                }else{
                                    window.location.reload();
                                }
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
        showMessage('', 'error', '[ 참가업체 정보 ]', '기본 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_1_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_2.do', exhibitorSeq);*/

    let boothType = '등록비';
    let boothPrcSum = 0;

    // 부스 신청 - 등록비 - 수량
    let registrationCnt = 1;

    // 부스 신청 - 등록비 - 금액
    let registrationFee = 100000;

    boothPrcSum += registrationFee;

    // 부스 신청 - 독립부스 - 수량
    let standAloneBoothCnt = parseInt($('#standAloneBoothCnt').val());

    if(standAloneBoothCnt > 0){
        boothType += ',독립부스';
    }

    // 부스 신청 - 독립부스 - 금액
    let standAloneBoothFee = $('#standAloneBoothFee').val();

    boothPrcSum += wonToNumber(standAloneBoothFee);

    // 부스 신청 - 조립부스 - 수량
    let assemblyBoothCnt = parseInt($('#assemblyBoothCnt').val());

    if(assemblyBoothCnt > 0){
        boothType += ',조립부스';
    }

    // 부스 신청 - 조립부스 - 금액
    let assemblyBoothFee = $('#assemblyBoothFee').val();

    boothPrcSum += wonToNumber(assemblyBoothFee);

    // 부스 신청 - 온라인부스 - 수량
    let onlineBoothCnt = parseInt($('#onlineBoothCnt option:checked').val());

    if(onlineBoothCnt > 0){
        boothType += ',온라인부스';
    }

    // 부스 신청 - 온라인부스 - 금액
    let onlineBoothFee = $('#onlineBoothFee').val();

    boothPrcSum += wonToNumber(onlineBoothFee);

    let discountType = '';
    let discountPrcSum = 0;
    let boothTotalCnt = standAloneBoothCnt + assemblyBoothCnt + onlineBoothCnt;

    // 할인적용 - 1차 조기신청
    let discountEarly1 = $('#discountEarly1').is(':checked');
    if(discountEarly1){
        discountType += ',1차조기신청';
        discountPrcSum += (boothTotalCnt * 300000);
    }

    // 할인적용 - 2차 조기신청
    let discountEarly2 = $('#discountEarly2').is(':checked');
    if(discountEarly2){
        discountType += ',2차조기신청';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 첫 참가 할인
    let discountFirst = $('#discountFirst').is(':checked');
    if(discountFirst){
        discountType += ',첫참가';

        // 규모할인 체크시 30만원으로 적용
        if($('.single-choice-discount input[type="checkbox"]').is(':checked')){
            discountPrcSum += (boothTotalCnt * 300000);
        }else{
            discountPrcSum += (boothTotalCnt * 500000);
        }
    }

    // 할인적용 - 재참가할인 All
    let discountRe = $('#discountRe').is(':checked');
    if(discountRe){
        discountType += ',재참가할인';
        discountPrcSum += (boothTotalCnt * 200000);
    }

    // 할인적용 - 규모할인 1 (10부스 이상)
    let discountScale1 = $('#discountScale1').is(':checked');
    if(discountScale1){
        discountType += ',규모할인1';
        discountPrcSum += (boothTotalCnt * 400000);
    }

    // 할인적용 - 규모할인 2 (20부스 이상)
    let discountScale2 = $('#discountScale2').is(':checked');
    if(discountScale2){
        discountType += ',규모할인2';
        discountPrcSum += (boothTotalCnt * 650000);
    }

    // 할인적용 - 규모할인 3 (30부스 이상)
    let discountScale3 = $('#discountScale3').is(':checked');
    if(discountScale3){
        discountType += ',규모할인3';
        discountPrcSum += (boothTotalCnt * 750000);
    }

    // 할인적용 - 규모할인 4 (40부스 이상)
    let discountScale4 = $('#discountScale4').is(':checked');
    if(discountScale4){
        discountType += ',규모할인4';
        discountPrcSum += (boothTotalCnt * 800000);
    }

    // 할인적용 - 규모할인 5 (50부스 이상)
    let discountScale5 = $('#discountScale5').is(':checked');
    if(discountScale5){
        discountType += ',규모할인5';
        discountPrcSum += (boothTotalCnt * 850000);
    }

    // 할인적용 - 규모할인 6 (60부스 이상)
    let discountScale6 = $('#discountScale6').is(':checked');
    if(discountScale6){
        discountType += ',규모할인6';
        discountPrcSum += (boothTotalCnt * 900000);
    }

    // 할인적용 - 한국해양레저산업협회 할인
    let discountLeisure = $('#discountLeisure').is(':checked');
    if(discountLeisure){
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

    if(boothTotalCnt === 0){
        showMessage('', 'error', '[ 전시부스 신청 ]', '부스(독립,조립,온라인)를 하나 이상 신청해 주세요.', '');
        return;
    }

    let booth_json_obj = {
        seq: exhibitorSeq,
        boothType: boothType,
        discountType: discountType,
        registrationCnt: registrationCnt,
        registrationFee: registrationFee,
        standAloneBoothCnt: standAloneBoothCnt,
        standAloneBoothFee: Number.parseInt(wonToInt(standAloneBoothFee)),
        assemblyBoothCnt: assemblyBoothCnt,
        assemblyBoothFee: Number.parseInt(wonToInt(assemblyBoothFee)),
        onlineBoothCnt: onlineBoothCnt,
        onlineBoothFee: Number.parseInt(wonToInt(onlineBoothFee)),
        discountEarly1: discountEarly1,
        discountEarly2: discountEarly2,
        discountFirst: discountFirst,
        discountRe: discountRe,
        discountScale1: discountScale1,
        discountScale2: discountScale2,
        discountScale3: discountScale3,
        discountScale4: discountScale4,
        discountScale5: discountScale5,
        discountScale6: discountScale6,
        discountLeisure: discountLeisure,
        discountYn: discountYn,
        boothPrcSum: boothPrcSum,
        discountPrcSum: discountPrcSum
    }

    let resData = ajaxConnect('/apply/step/updateExhibitorNewBooth.do', 'post', booth_json_obj);

    let returnPath = "";
    //console.log(resData);
    if(resData.resultCode === "0") {

        Swal.fire({
            icon: 'info',
            title: '[ 전시부스 신청 ]',
            html: '<span style="font-size: 1.2em;">부스 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
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
        showMessage('', 'error', '[ 전시부스 신청 ]', '전시부스 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}

function my_step_2_2_check(exhibitorSeq){

    /* 24.03.05 2024 보트쇼 종료로 인하여 바로 페이지 이동 */
    /*f_page_move('/mypage/step2_3.do', exhibitorSeq);*/

    // 상호간판 신청 - 상호간판명 (국문)
    let companySignNameKo = $('#companySignNameKo').val();
    if(nvl(companySignNameKo,'') === ''){
        showMessage('#companySignNameKo', 'error', '[ 상호간판 신청 ]', '상호간판명(국문)을 입력해 주세요.', '');
        return false;
    }

    // 상호간판 신청 - 상호간판명 (영문)
    let companySignNameEn = $('#companySignNameEn').val();
    if(nvl(companySignNameEn,'') === ''){
        showMessage('#companySignNameEn', 'error', '[ 상호간판 신청 ]', '상호간판명(영문)을 입력해 주세요.', '');
        return false;
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
            confirmButtonColor: '#3085d6',
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

    // 유틸리티 신청정보 - 주간단상 - 수량
    let utility_jugan_cnt = parseInt($('#utility_jugan_cnt').val());

    // 유틸리티 신청정보 - 주간단상 - 금액
    let utility_jugan_fee = $('#utility_jugan_fee').val();

    // 유틸리티 신청정보 - 24시간용 - 수량
    let utility_day_cnt = parseInt($('#utility_day_cnt').val());

    // 유틸리티 신청정보 - 24시간용 - 금액
    let utility_day_fee = $('#utility_day_fee').val();

    // 유틸리티 신청정보 - 압축공기 - 수량
    let utility_compressed_air_cnt = parseInt($('#utility_compressed_air_cnt').val());

    // 유틸리티 신청정보 - 압축공기 - 금액
    let utility_compressed_air_fee = $('#utility_compressed_air_fee').val();

    // 유틸리티 신청정보 - 급배수 - 수량
    let utility_water_basic_cnt = parseInt($('#utility_water_basic_cnt').val());

    // 유틸리티 신청정보 - 급배수 - 금액
    let utility_water_basic_fee = $('#utility_water_basic_fee').val();

    // 유틸리티 신청정보 - 인터넷 - 수량
    let utility_internet_cnt = parseInt($('#utility_internet_cnt').val());

    // 유틸리티 신청정보 - 인터넷 - 금액
    let utility_internet_fee = $('#utility_internet_fee').val();

    // 유틸리티 신청정보 - 파이텍스 (신품) - 수량
    let utility_pytex_new_cnt = parseInt($('#utility_pytex_new_cnt').val());

    // 유틸리티 신청정보 - 파이텍스 (신품) - 금액
    let utility_pytex_new_fee = $('#utility_pytex_new_fee').val();

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 수량
    let utility_pytex_re_cnt = parseInt($('#utility_pytex_re_cnt').val());

    // 유틸리티 신청정보 - 파이텍스 (재사용품) - 금액
    let utility_pytex_re_fee = $('#utility_pytex_re_fee').val();

    // 유틸리티 신청정보 - 참관객/바이어 바코드 리더기 - 수량
    let utility_barcode_cnt = parseInt($('#utility_barcode_cnt').val());

    // 유틸리티 신청정보 - 참관객/바이어 바코드 리더기 - 금액
    let utility_barcode_fee = $('#utility_barcode_fee').val();

    let utilityPrcSum = parseInt(wonToInt($('#form_add_total').val()));

    let utility_json_obj = {
        seq: exhibitorSeq,
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

    let resData = ajaxConnect('/apply/step/updateExhibitorNewUtility.do', 'post', utility_json_obj);

    //console.log(resData);
    if(resData.resultCode === "0") {

        /* 등록 성공 시 다음 단계로 이동 */
        Swal.fire({
            icon: 'info',
            title: '[ 유틸리티 신청 ]',
            html: '<span style="font-size: 1.2em;">유틸리티 신청 정보가 저장되었습니다.<br>다음 단계로 이동합니다.</span>',
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
            confirmButtonColor: '#3085d6',
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

    let giftList_json_obj = {
        seq: exhibitorSeq,
        giftList: gift_add_json_arr,
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
            confirmButtonColor: '#3085d6',
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
            confirmButtonColor: '#3085d6',
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
            title: '참가업체 정보',
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
                    home('ko');
                }
            }
        })
    }else{
        showMessage('', 'error', '[ 참가업체 정보 ]', '참가 신청 정보 저장에 실패하였습니다. 관리자에게 문의해 주세요.', '');
    }
}


function exibitloginFormSubmit() {

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
            icon: 'info',
            title: '[ 참관 신청 확인 ]',
            html: '<span style="font-size: 1.2em;">참관 신청 확인 되었습니다.<br>참관신청확인페이지로 이동합니다.</span>',
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
            icon: 'info',
            title: '[ 참관 신청 확인 ]',
            html: '<span style="font-size: 1.2em;">참관 신청 정보가 없습니다.<br>사전등록페이지로 이동합니다.</span>',
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
                            showMessage('', 'error', '[ERROR]', '직장명 검색에 실패하였습니다. 관리자에게 문의해 주세요. ', '');
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
                alert('오류가 발생했습니다. 관리자에게 문의해 주세요.');
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
                            showMessage('', 'error', '에러 발생', '참관객 사전 등록 저장을 실패하였습니다. 관리자에게 문의해 주세요. ' + data.resultMessage, '');
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
                    html: '이미 사전 등록된 성명, 휴대전화번호 입니다.<br>참관신청확인페이지에서 정보를 확인해 주세요.',
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
            showMessage('', 'info', '[설문항목]', '관람 구분을 하나 이상 체크해 주세요.', '');
            return false;
        }
    }

    let visitPurpose = $('input[type=checkbox][name=visitPurpose]').is(':checked');
    if (!visitPurpose) {
        showMessage('', 'info', '[설문항목]', '보트쇼 방문 목적을 하나 이상 체크해 주세요.', '');
        return false;
    }

    let interestItem = $('input[type=checkbox][name=interestItem]').is(':checked');
    if (!interestItem) {
        showMessage('', 'info', '[설문항목]', '관심품목을 하나 이상 체크해 주세요.', '');
        return false;
    }

    let recognizePath = $('input[type=checkbox][name=recognizePath]').is(':checked');
    if (!recognizePath) {
        showMessage('', 'info', '[설문항목]', '인지경로를 하나 이상 체크해 주세요.', '');
        return false;
    }

    let preObservationGbn = $('input[type=checkbox][name=preObservationGbn]').is(':checked');
    if (!preObservationGbn) {
        showMessage('', 'info', '[설문항목]', '지난 전시회 참관 여부를 하나 이상 체크해 주세요.', '');
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

    /* 전시품 정보 - 제품사진 */
    let productImageFileList = $('input[type=file][name=productImageFile]');
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
    }

    /* 온라인 전시관 정보 - 제품사진 */
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

            f_company_uploadFile(id, 'exhibitor_apply_form', 'onlineImageFile' + onlineImageNum, 'exhibitor/company/' + path);
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
        subject: '[2026 경기국제보트쇼] 참가업체 접수 완료', //제목
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