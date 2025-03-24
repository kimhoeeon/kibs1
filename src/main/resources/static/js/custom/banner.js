/***
 * mng/exhibitor/application/banner.js
 * 참가신청서 관리 > 웹 배너 신청
 * */

$(function(){

});

function f_application_banner_search(){

    /* 로딩페이지 */
    loadingBarShow();

    /* DataTable Data Clear */
    let dataTbl = $('#kt_exhibitor_application_banner_table').DataTable();
    dataTbl.clear();
    dataTbl.draw(false);

    /* TM 및 잠재DB 목록 데이터 조회 */
    let jsonObj;
    let searchText = $('#search_text').val();
    if(nullToEmpty(searchText) === ""){
        jsonObj = {
            "joinYear": '2023'
        };
    }else{
        jsonObj = {
            "joinYear": '2023',
            "condition": $('#search_box option:selected').val(),
            "searchText": searchText
        }
    }

    let resData = ajaxConnect('/mng/exhibitor/participant/visitor/selectList.do', 'post', jsonObj);

    dataTbl.rows.add(resData).draw();

    /* 조회 카운트 입력 */
    document.getElementById('search_cnt').innerText = resData.length;

    /* DataTable Column tooltip Set */
    let jb = $('#kt_exhibitor_application_banner_table tbody td');
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

function f_board_notice_remove(rowId){
    //console.log('삭제버튼');
    if(nullToEmpty(rowId) !== ""){
        let jsonObj = {
            "id": rowId
        }
        Swal.fire({
            title: '선택한 공지사항을 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {

                let resData = ajaxConnect('/mng/center/board/notice/delete.do', 'post', jsonObj);

                if (resData.resultCode === "0") {
                    showMessage('', 'info', '공지사항 삭제', '공지사항이 삭제되었습니다.', '');
                    f_board_notice_search(); // 삭제 성공 후 재조회 수행
                } else {
                    showMessage('', 'error', '에러 발생', '공지사항 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                }
            }
        });
    }
}

function f_application_banner_modify_init_set(id){
    window.location.href = '/mng/exhibitor/application/banner/detail.do?seq=' + id;
}

function f_application_banner_save(seq){

    let gbn = 'U';
    let url = '/mng/exhibitor/participant/company/update.do';
    if(nvl(seq,"") === ""){ //insert
        gbn = 'C';
        url = '/mng/exhibitor/participant/company/insert.do';
    }

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

            /* 유효성 검사 */
            let validCheck = f_banner_form_valid_check(gbn);

            if(validCheck){

                /* form data setting */
                let data = f_banner_form_data_setting(gbn);

                console.log('data : ' + data);

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
                            f_company_file_upload_call(tableSeq, 'exhibitor/company/' + tableSeq);

                            Swal.fire({
                                title: '전시 업체 정보 저장',
                                text: '전시 업체 정보가 저장되었습니다.',
                                icon: 'info',
                                allowOutsideClick: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: '확인'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    f_exhibitor_detail(tableSeq); // 재조회
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

            }//validCheck
        }//isConfirmed
    })//then

}

function f_banner_form_valid_check(gbn){

    if(gbn === 'C'){ // insert
        let id = $('#newId').val();
        if (nvl(id,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '아이디를 입력해 주세요.', '');
            return false;
        }
    }

    let companyNameKo = $('#companyNameKo').val();
    if (nvl(companyNameKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사명(국문)을 입력해 주세요.', '');
        return false;
    }

    let companyNameEn = $('#companyNameEn').val();
    if (nvl(companyNameEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사명(영문)을 입력해 주세요.', '');
        return false;
    }

    let companyAddress = $('#companyAddress').val();
    if (nvl(companyAddress,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '주소를 검색해주세요.', '');
        return false;
    }

    let companyAddressDetail = $('#companyAddressDetail').val();
    if (nvl(companyAddressDetail,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '상세주소를 입력해 주세요.', '');
        return false;
    }

    let companyCeo = $('#companyCeo').val();
    if (nvl(companyCeo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '대표자명을 입력해 주세요.', '');
        return false;
    }

    let companyTel = $('#companyTel').val();
    if (nvl(companyTel,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '전화번호를 입력해 주세요.', '');
        return false;
    }

    if(gbn === 'C') { // insert
        let companyLicense = $('#companyLicense').val();
        if (nvl(companyLicense,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '사업자등록증을 첨부해주세요.', '');
            return false;
        }
    }else{
        let companyLicenseFile_li = $('.companyLicenseFile_li').length;
        if(companyLicenseFile_li === 0){
            let companyLicense = $('#companyLicense').val();
            if (nvl(companyLicense,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '사업자등록증을 첨부해주세요.', '');
                return false;
            }
        }
    }

    let companyLicenseNum = $('#companyLicenseNum').val();
    if (nvl(companyLicenseNum,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '사업자등록번호를 입력해 주세요.', '');
        return false;
    }

    let memberCompanyYn = $('input[type=radio][name=memberCompanyYn]:checked').val();
    if(nvl(memberCompanyYn,"") === ""){
        showMessage('', 'info', '입력 정보 확인', '회원사 여부를 체크해주세요.', '');
        return false;
    }

    let chargePersonName = $('#chargePersonName').val();
    if (nvl(chargePersonName,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '담당자 성명을 입력해 주세요.', '');
        return false;
    }

    let chargePersonPosition = $('#chargePersonPosition').val();
    if (nvl(chargePersonPosition,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '담당자 직위를 입력해 주세요.', '');
        return false;
    }

    let chargePersonTel = $('#chargePersonTel').val();
    if (nvl(chargePersonTel,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '담당자 전화번호를 입력해 주세요.', '');
        return false;
    }

    let chargePersonPhone = $('#chargePersonPhone').val();
    if (nvl(chargePersonPhone,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '담당자 휴대전화번호를 입력해 주세요.', '');
        return false;
    }

    let chargePersonEmailInput1 = $('#charge_person_email_input_1').val();
    let chargePersonEmailInput2 = $('#charge_person_email_input_2').val();
    if (nvl(chargePersonEmailInput1,"") === "" || nvl(chargePersonEmailInput2,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '담당자 이메일 주소를 입력해 주세요.', '');
        return false;
    }

    let fieldPart = $('input[type=checkbox][name=fieldPart]').is(':checked');
    if (!fieldPart) {
        showMessage('', 'info', '입력 정보 확인', '참가분야를 하나 이상 체크해주세요.', '');
        return false;
    }

    let displayItemList = $('input[type=text][name=displayItem]');
    for(let i=0; i<displayItemList.length; i++){
        let displayItem = displayItemList.eq(i).val();
        if (nvl(displayItem,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '전시품목을 입력해 주세요.', '');
            return false;
        }
    }

    let displayBrandList = $('input[type=text][name=displayBrand]');
    for(let i=0; i<displayBrandList.length; i++){
        let displayBrand = displayBrandList.eq(i).val();
        if (nvl(displayBrand,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '전시품목 브랜드명을 입력해 주세요.', '');
            return false;
        }
    }

    let displayBoatCntList = $('input[type=text][name=displayBoatCnt]');
    for(let i=0; i<displayBoatCntList.length; i++){
        let displayBoatCnt = displayBoatCntList.eq(i).val();
        if (nvl(displayBoatCnt,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '실물 보트수(단위:척)을 입력해 주세요.', '');
            return false;
        }
    }

    let companyIntroKo = $('#companyIntroKo').val();
    if (nvl(companyIntroKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사소개(국문)을 입력해 주세요.', '');
        return false;
    }

    let companyIntroEn = $('#companyIntroEn').val();
    if (nvl(companyIntroEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '회사소개(영문)을 입력해 주세요.', '');
        return false;
    }

    let companyPurposeKo = $('#companyPurposeKo').val();
    if (nvl(companyPurposeKo,"") === "") {
        showMessage('', 'info', '입력 정보 확인', 'KIBS 참가목적(국문)을 입력해 주세요.', '');
        return false;
    }

    let companyPurposeEn = $('#companyPurposeEn').val();
    if (nvl(companyPurposeEn,"") === "") {
        showMessage('', 'info', '입력 정보 확인', 'KIBS 참가목적(영문)을 입력해 주세요.', '');
        return false;
    }

    let signVisitReason = $('#signVisitReason').val();
    if (nvl(signVisitReason,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '\'우리 기업 부스는 꼭 들려야 될 이유가 있다면?\' 을 입력해 주세요.', '');
        return false;
    }

    let eventPlan = $('#eventPlan').val();
    if (nvl(eventPlan,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '행사/이벤트 진행계획을 입력해 주세요.', '');
        return false;
    }

    /*let officeRequest = $('#officeRequest').val();
    if (nvl(officeRequest,"") === "") {
        showMessage('', 'info', '입력 정보 확인', '사무국 요청사항을 입력해 주세요.', '');
        return false;
    }*/

    if(gbn === 'C') {
        let logo = $('#logo').val();
        if (nvl(logo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '로고 이미지를 첨부해주세요.', '');
            return false;
        }
    }else{
        let logoFile_li = $('.logoFile_li').length;
        if(logoFile_li === 0){
            let logo = $('#logo').val();
            if (nvl(logo,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '로고 이미지를 첨부해주세요.', '');
                return false;
            }
        }
    }

    if(gbn === 'C'){
        let promotionImageList = $('input[type=text][name=promotionImage]');
        let promotionImage = promotionImageList.eq(0).val();
        if (nvl(promotionImage,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '홍보 이미지1 은 필수로 첨부해주세요.', '');
            return false;
        }
    }else{
        let promotionImageFile_li = $('.promotionImageFile_li').length;
        let promotionImageListVal = $('input[type=text][name=promotionImage]').eq(0).val();
        if( (promotionImageFile_li === 0) && nvl(promotionImageListVal,"") === ""){
            showMessage('', 'info', '입력 정보 확인', '홍보 이미지1 은 필수로 첨부해주세요.', '');
            return false;
        }
    }

    if(gbn === 'C'){
        let productImageList = $('input[type=text][name=productImage]');
        for(let i=0; i<productImageList.length; i++){
            let productImage = productImageList.eq(i).val();
            if (nvl(productImage,"") === "") {
                showMessage('', 'info', '입력 정보 확인', '제품 사진을 첨부해주세요.', '');
                return false;
            }
        }
    }else{
        let onlineInfoBoxLen = parseInt($('.online_info_box:last .onlineInfoNum').text());
        let productImageFile_li = parseInt($('.productImageFile_li').length);
        let productImageFileCnt = 0;
        let productImageFileList = $('input[type=file][name=productImageFile]');
        for(let i=0; i<productImageFileList.length; i++){
            let productImageFile = productImageFileList.eq(i).val();
            if(nvl(productImageFile,"") !== ""){
                productImageFileCnt ++;
            }
        }

        if((productImageFile_li+productImageFileCnt) !== onlineInfoBoxLen){
            showMessage('', 'info', '입력 정보 확인', '제품 사진을 첨부해주세요.', '');
            return false;
        }
    }

    let productNameKoList = $('input[type=text][name=productNameKo]');
    for(let i=0; i<productNameKoList.length; i++){
        let productNameKo = productNameKoList.eq(i).val();
        if (nvl(productNameKo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품명(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    let productNameEnList = $('input[type=text][name=productNameEn]');
    for(let i=0; i<productNameEnList.length; i++){
        let productNameEn = productNameEnList.eq(i).val();
        if (nvl(productNameEn,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품명(영문)을 입력해 주세요.', '');
            return false;
        }
    }

    if(gbn === 'C'){
        let productOptionBigList = $('select[name=productOptionBig]');
        for(let i=0; i<productOptionBigList.length; i++){
            let productOptionBig = productOptionBigList.eq(i).val();
            if (nvl(productOptionBig,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 첫 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }

        let productOptionSmallList = $('select[name=productOptionSmall]');
        for(let i=0; i<productOptionSmallList.length; i++){
            let productOptionSmall = productOptionSmallList.eq(i).val();
            if (nvl(productOptionSmall,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 두 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }
    }else{
        let productOptionBigList = $('.preValueList select[name=productOptionBig]');
        for(let i=0; i<productOptionBigList.length; i++){
            let productOptionBig = productOptionBigList.eq(i).val();
            if (nvl(productOptionBig,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 첫 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }

        let productOptionSmallList = $('.preValueList select[name=productOptionSmall]');
        for(let i=0; i<productOptionSmallList.length; i++){
            let productOptionSmall = productOptionSmallList.eq(i).val();
            if (nvl(productOptionSmall,"선택") === "선택") {
                showMessage('', 'info', '입력 정보 확인', '제품명(분류) 두 번째 항목을 선택해 주세요.', '');
                return false;
            }
        }
    }

    let productIntroKoList = $('textarea[name=productIntroKo]');
    for(let i=0; i<productIntroKoList.length; i++){
        let productIntroKo = productIntroKoList.eq(i).val();
        if (nvl(productIntroKo,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품 설명(국문)을 입력해 주세요.', '');
            return false;
        }
    }

    let productIntroEnList = $('textarea[name=productIntroEn]');
    for(let i=0; i<productIntroEnList.length; i++){
        let productIntroEn = productIntroEnList.eq(i).val();
        if (nvl(productIntroEn,"") === "") {
            showMessage('', 'info', '입력 정보 확인', '제품 설명(영문)을 입력해 주세요.', '');
            return false;
        }
    }

    /*let exportMeetingYn = $('input[type=radio][name=exportMeetingYn]:checked').val();
    if(exportMeetingYn === "Y"){
        let formChugaListLen = $('.form_chuga_list').length;
        if(formChugaListLen === 0){
            showMessage('', 'info', '입력 정보 확인', '수출상담회 참가를 희망하실 경우 바이어를 등록해주세요.', '');
            return false;
        }
    }*/

    return true;
}

function f_banner_form_data_setting(gbn){

    /* 상세정보 */
    let companyForm = JSON.parse(JSON.stringify($('#companyForm').serializeObject()));

    //언어
    companyForm.lang = 'KO';

    if(gbn === 'C'){
        //참가상태
        companyForm.participationStatus = '참가확정';

        //신청폼작성여부
        companyForm.applyComplt = 'N'; //작성중

        //승인구분
        companyForm.approvalStatus = '승인요청';

        //부스번호 - 최초 등록
        companyForm.signNum = $('#newsignNum').val();

        //아이디 - 최초 등록
        companyForm.id = $('#newId').val();
    }

    //담당자 이메일 Setting
    companyForm.chargePersonEmail = $('#charge_person_email_input_1').val() + '@' + $('#charge_person_email_input_2').val();

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
            "seq": $('input[type=hidden][name=displayItemSeq]').eq(i).val(),
            "displayItem": $('input[name=displayItem]').eq(i).val(),
            "displayBrand": $('input[name=displayBrand]').eq(i).val(),
            "displayBoatCnt": $('input[name=displayBoatCnt]').eq(i).val()
        };
        displayInfoArr.push(displayInfoObj);
    }
    companyForm.display = displayInfoArr;

    //온라인전시회 Setting
    let onlineInfoCnt = $('.onlineInfoNum:last').text();
    let onlineInfoArr = [];
    for(let i=0; i<onlineInfoCnt; i++){
        let productOptionBig = '';
        let productOptionSmall = '';
        if(gbn === 'C'){
            productOptionBig = $('select[name=productOptionBig]').eq(i).val();
            productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
        }else{
            let selProductOptionBig = $('select[name=productOptionBig]').eq(i).val();
            if(nvl(selProductOptionBig,"선택") !== "선택"){
                productOptionBig = $('select[name=productOptionBig]').eq(i).val();
                productOptionSmall = $('select[name=productOptionSmall]').eq(i).val();
            }else{
                productOptionBig = $('input[type=text][name=productOptionBig]').eq(i).val();
                productOptionSmall = $('input[type=text][name=productOptionSmall]').eq(i).val();
            }
        }

        let onlineInfoObj = {
            "seq": $('input[type=hidden][name=onlineItemSeq]').eq(i).val(),
            "productNameKo": $('input[name=productNameKo]').eq(i).val(),
            "productNameEn": $('input[name=productNameEn]').eq(i).val(),
            "productOptionBig": productOptionBig,
            "productOptionSmall": productOptionSmall,
            "productIntroKo": $('textarea[name=productIntroKo]').eq(i).val(),
            "productIntroEn": $('textarea[name=productIntroEn]').eq(i).val(),
            "productIntroVideo": $('input[name=productIntroVideo]').eq(i).val(),
            "productWidth": $('input[name=productWidth]').eq(i).val(),
            "productHorsePower": $('input[name=productHorsePower]').eq(i).val(),
            "note": i+1
        };
        onlineInfoArr.push(onlineInfoObj);
    }
    companyForm.online = onlineInfoArr;

    //수출상담회 Setting
    let exportInfoCnt = document.getElementsByClassName('form_chuga_list').length;
    let exportInfoArr = [];
    for(let i=0; i<exportInfoCnt; i++){
        let exportInfoObj = {
            "seq": $('input[type=hidden][name=buyerItemSeq]').eq(i).val(),
            "buyerCompanyName": $('input[type=hidden][name=buyerCompanyName]').eq(i).val(),
            "buyerCompanyCountry": $('input[type=hidden][name=buyerCompanyCountry]').eq(i).val(),
            "buyerCompanyLocation": $('input[type=hidden][name=buyerCompanyLocation]').eq(i).val(),
            "buyerCompanyHomepage": $('input[type=hidden][name=buyerCompanyHomepage]').eq(i).val(),
            "buyerCompanyDepart": $('input[type=hidden][name=buyerCompanyDepart]').eq(i).val(),
            "buyerCompanyPosition": $('input[type=hidden][name=buyerCompanyPosition]').eq(i).val(),
            "buyerCompanyEmail": $('input[type=hidden][name=buyerCompanyEmail]').eq(i).val(),
            "buyerCompanyTel": $('input[type=hidden][name=buyerCompanyTel]').eq(i).val(),
            "buyerCompanyPhone": $('input[type=hidden][name=buyerCompanyPhone]').eq(i).val(),
            "buyerCompanyFax": $('input[type=hidden][name=buyerCompanyFax]').eq(i).val(),
            "buyerCompanyItem": $('input[type=hidden][name=buyerCompanyItem]').eq(i).val(),
            "buyerCompanyInviteReason": $('input[type=hidden][name=buyerCompanyInviteReason]').eq(i).val(),
            "buyerCompanyProgressYn": $('input[type=hidden][name=buyerCompanyProgressYn]').eq(i).val(),
            "buyerCompanyHope": $('input[type=hidden][name=buyerCompanyHope]').eq(i).val()
        };
        exportInfoArr.push(exportInfoObj);
    }
    companyForm.buyer = exportInfoArr;

    return JSON.stringify(companyForm);
}

function objectifyForm(formArray) {
    //serialize data function
    let returnArray = {};
    for (let i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}