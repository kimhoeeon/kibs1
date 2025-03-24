$(document).ready(function () {
    // dept1 메뉴 아이템에 대한 마우스 오버
    $('.nav .dept1 > li').on('mouseover', function () {
        $(this).find('.dept2').addClass('on');
    }).on('mouseout', function () {
        $(this).find('.dept2').removeClass('on');
    });

    $(".ft_other_site .select_box .btn").on('click', function () {
        var selectedMenu = $(this).siblings(".option_box");
        if (selectedMenu.is(':visible')) {
            selectedMenu.slideUp();
        } else {
            $(".ft_other_site .option_box").slideUp();
            selectedMenu.slideDown();
        }
    });

    $('.form_s .img_replace_cmnt .btn').on('mouseover', function () {
        $(this).siblings('.text').show();
    }).on('mouseout', function () {
        $(this).siblings('.text').hide();
    });

    // 모바일 슬라이드 메뉴
    $('.m_menu').on('click', function () {
        $(this).toggleClass('on');
        $('.hd_site_map').toggleClass('on');
    });

    // 모바일 메뉴
    function handleDept1Click() {
        // Check viewport width
        var viewportWidth = $(window).width();
        if (viewportWidth <= 1024) {
            // Check if any other .dept2 is visible
            var otherDept2 = $('.site_map_nav .dept1 > li').not($(this)).find('.dept2:visible');

            // Slide up the previously exposed .dept2 and expose the corresponding child element
            otherDept2.slideUp();
            $(this).find('.dept2').slideToggle();
        }
    }

    // Handle .dept1 click on page load
    $('.site_map_nav .dept1 > li').on('click', handleDept1Click);

    // Handle .dept1 click on window resize
    $( window ).on('resize', function() {
        $('.site_map_nav .dept1 > li').off('click', handleDept1Click);
        $('.site_map_nav .dept1 > li').on('click', handleDept1Click);
    });


    // 좋아요 버튼    
    $('.btnLike').on('click', function () {
        var likeImage = $(this).find('.likeImg');
        var currentSrc = likeImage.attr('src');
        var filename = currentSrc.substring(currentSrc.lastIndexOf('/') + 1);

        var path = currentSrc.replace(filename, '');

        if (filename === 'icon_like.png') {
            likeImage.attr('src', path + 'icon_like_on.png');
        } else {
            likeImage.attr('src', path + 'icon_like.png');
        }
    });


    // 마우스오버시 sns 이미지 변경
    /*$('.hd_top_sns img').on('hover', function () {
        var src = $(this).attr('src');
        var newSrc = src.replace('.png', '_on.png');
        $(this).attr('src', newSrc);
    }, function () {
        var src = $(this).attr('src');
        var newSrc = src.replace('_on.png', '.png');
        $(this).attr('src', newSrc);
    });*/

    // 서브 탭 클릭시 카테고리 노출
    $(".tabOption").on('click', function () {
        var selectedMenu = $(this).find(".tabOptSel");
        if (selectedMenu.is(':visible')) {
            selectedMenu.slideUp();
        } else {
            $(".tabOptSel").slideUp();
            selectedMenu.slideDown();
        }
    });

    // tab
    $('.tab_menu li').on('click', function () {
        var tab_id = $(this).attr('data-tab');

        $('.tab_menu li').removeClass('on');
        $('.tab_cont').removeClass('on');

        $(this).addClass('on');
        $("#" + tab_id).addClass('on');
    });

    //자주묻는 질문
    $(document).on("click", ".board_faq .ask", function() {
        let answer = $(this).next("div");
        $(this).toggleClass('on');

        if (answer.is(":visible")) {
            answer.slideUp();
        } else {
            answer.slideDown();
        }
    });

    // 팝업닫기    
    $('.apply_nav_list .list1 li').on('click', function () {
        $('.apply_nav_list .list2').toggleClass('on');
    });


    // 팝업닫기    
    $('.popup .close').on('click', function () {
        $('.popup').removeClass('on');
        $('body').removeClass('lock_scroll');
        $('.popCompanyName input[type=text]').val('');
        $('.popCompanyName .companyList').html('');
        $('.popDirectName input[type=text]').val('');
    });

    // 마이페이지 탈퇴
    $('.memberOut').on('click', function () {
        $('.popMemberOut').toggleClass('on');
        $('body').toggleClass('lock_scroll');
    });

    // 문의하기
    $('.exhCs').on('click', function () {
        $('.popExhCs').toggleClass('on');
        $('body').toggleClass('lock_scroll');
    });

    // 공유하기
    $('.btnShare').on('click', function () {
        $('.popShare').toggleClass('on');
        $('body').toggleClass('lock_scroll');
    });


    // kibstv
    $(document).on("click", ".viewKibsTv", function() {
        //console.log($(this).find('img').attr('src')); // https://img.youtube.com/vi/WMaA84_cixo/mqdefault.jpg
        let youtubeTitle = $(this).find('.title').text();
        let youtubeUrl = $(this).find('img').attr('src');
        let youtubeGbn = $(this).find('img').attr('data-value');
        if(youtubeGbn === 'long'){
            let youtubeSeq = youtubeUrl.toString()
                .replace('https://img.youtube.com/vi/','')
                .replace('/mqdefault.jpg',''); // WMaA84_cixo
            let youtubeIframeUrl = 'https://www.youtube.com/embed/' + youtubeSeq;
            $('.popKibsTv').find('.tit').text(youtubeTitle);
            $('.popKibsTv').find('iframe').attr('src', youtubeIframeUrl);
        }else{
            let youtubeSeq = youtubeUrl.toString()
                .replace('https://img.youtube.com/vi/','')
                .replace('/mqdefault.jpg',''); // WMaA84_cixo
            let youtubeIframeUrl = 'https://youtube.com/embed/' + youtubeSeq;
            $('.popKibsTv').find('.tit').text(youtubeTitle);
            $('.popKibsTv').find('iframe').attr('src', youtubeIframeUrl);
        }

        $('.popKibsTv').toggleClass('on');
        $('body').toggleClass('lock_scroll');
    });

    $('.tv_tab .tv_more').on('click', function () {
        $(this).toggleClass('on');
        $('.tv_tab .field').toggleClass('on');
    });

    /* 참가기업 검색하기 */
    $('.exh_search .badge_box').on('click', function () {
        $('.badge_list').toggleClass('on');
    });

    // 참가기업 목록에서 button클릭 시 a는 비활성화
    $('.exhList li button').on('click', function (event) {
        event.preventDefault();
        $(this).prop('disabled', true);
    });

    // 갤러리
    $(document).on("click", ".viewGallery", function() {
        let slideTitle = $(this).find('.tit').text();
        let slideImgList = $(this).find('input[type=hidden][name=slideImg]');

        let str = '';
        $.each(slideImgList , function(i) {
            let slideImgPath = slideImgList[i].value.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
            str += '<li class="swiper-slide thumb75 thumbBox">';
                str += '<img class="thumbImg" src="' + slideImgPath + '">';
            str += '</li>';
        });

        $('.popGallery .pop_tit .tit').text(slideTitle);
        $('.popGallery .pop_box .swiper_box ul').empty();
        $('.popGallery .pop_box .swiper_box ul').html(str);

        $('.popGallery').toggleClass('on');
        $('body').toggleClass('lock_scroll');
    });

    // 파일 입력 변경에 대한 이벤트 핸들러 추가
    $('.upload_hidden').on('change', function () {

        let fileName = $(this).val();
        if(nvl(fileName,'') !== ''){
            let fileExt = fileName.split('.').pop().toLowerCase(); //확장자분리
            let fileInputName = fileName.split('\\').pop().toLowerCase();
            fileInputName = fileInputName.slice(0,fileInputName.lastIndexOf(".")).toLowerCase();

            //console.log(fileInputName, fileExt);
            /*let empty_reg = /[\s]/g;
            if(empty_reg.test(fileInputName)) {
                alert("파일명에 사용할 수 없는 공백이 포함되어 있습니다.\n파일명에 구분이 필요한 경우 언더바 _ 로 변경해 주세요.");
                $(this).val(''); //업로드한 파일 제거
                let fileNameInput = $(this).siblings('.upload_name');
                fileNameInput.val('');
                return;
            }*/

            //특수문자가 속해있는지 확인하는 정규식
            let reg =   /[\{\}\/?,.;:|\[\]*~`!^\+<>@\#$%&\\\=\'\"]/gi;
            if(reg.test(fileInputName)){
                alert('파일명에 허용되지 않는 특수문자가 포함되어 있습니다.\n허용된 특수문자는 - _ ( ) 입니다.');
                $(this).val(''); //업로드한 파일 제거
                let fileNameInput = $(this).siblings('.upload_name');
                fileNameInput.val('');
                return;
            }

            let acceptArr = $(this).attr('accept').toString().replaceAll('.','').split(', ');
            if(!acceptArr.includes(fileExt)){
                let alertMsg = '파일 첨부는 ' + $(this).attr('accept').toString() + ' 파일만 가능합니다.';
                alert(alertMsg);
                $(this).val(''); //업로드한 파일 제거
                let fileNameInput = $(this).siblings('.upload_name');
                fileNameInput.val('');
                return;
            }

            if (this.files && this.files[0]) {
                let maxSize = 10 * 1024 * 1024; //* 10MB 사이즈 제한
                let file = this.files[0];
                if (file.size > maxSize) {
                    alert("파일 첨부는 10MB 이내 파일만 가능합니다.");
                    $(this).val(''); //업로드한 파일 제거
                    let fileNameInput = $(this).siblings('.upload_name');
                    fileNameInput.val('');
                    return;
                } else {
                    let fileName = $(this).val().split('\\').pop();
                    let fileNameInput = $(this).siblings('.upload_name');
                    fileNameInput.val(fileName);
                }
            }

        }else{
            $(this).val(''); //업로드한 파일 제거
            let fileNameInput = $(this).siblings('.upload_name');
            fileNameInput.val('');
        }
    });

    // 숫자만 입력
    $('.onlyNum').on("blur keyup", function () {
        $(this).val($(this).val().replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1'));
    });

    // 숫자랑 - 만 입력
    $('.onlyNumh').on("blur keyup", function () {
        $(this).val($(this).val().replace(/[^0-9-]/g, ''));
    });

    // 연락처 입력 시 자동으로 - 삽입과 숫자만 입력
    $('.onlyTel').on("blur keyup", function () {
        $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));
    });

    // 영문, 숫자만 입력
    $('.onlyNumEng').on("blur keyup", function () {
        let exp = /[^A-Za-z0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;
        $(this).val($(this).val().replaceAll(exp, ''));
    });

    // 숫자, 소수점 입력
    $('.onlyNumDec').on("blur keyup", function () {
        var sanitizedValue = $(this).val().replace(/[^0-9.]/g, ''); // 숫자와 소수점 이외의 문자 제거
        var decimalParts = sanitizedValue.split("."); // 소수점을 기준으로 분할

        // 소수점이 하나 이상일 경우 마지막 소수점만 남기고 제거
        if (decimalParts.length > 2) {
            decimalParts.pop(); // 마지막 요소(소수점 이후 내용) 제거
            sanitizedValue = decimalParts.join(".");
        }

        $(this).val(sanitizedValue); // 처리된 값을 다시 입력 필드에 설정
    });

    // 회원 계정 정보 input 이메일
    $('.form_s .email #email_select').on('change', function () {
        var selectedOption = $(this).val();
        var emailInput2 = $('.form_s .email #email_input2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }
    });

    // 담당자 정보 input 이메일
    $('.form_s .email #charge_person_email_select').on('change', function () {
        var selectedOption = $(this).val();
        var emailInput2 = $('.form_s .email #charge_person_email_input2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }
    });

    // 바이어 정보 input 이메일
    $('.form_s .email #buyer_email_select').on('change', function () {
        var selectedOption = $(this).val();
        var emailInput2 = $('.form_s .email #buyer_email_input2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }
    });

    // input 이메일
    $('#email_select').on('change', function () {
        var selectedOption = $(this).val();
        var emailInput2 = $(this).prev('.email_input2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }

    });

    // input 이메일
    $('select[name=chargePersonEmail_sel]').on('change', function () {
        var selectedOption = $(this).val();
        var emailInput2 = $(this).prev('.email_input2');

        if (selectedOption === '직접입력') {
            emailInput2.prop('disabled', false).val('');
        } else {
            emailInput2.prop('disabled', true).val(selectedOption);
        }

    });


    // 출입증신청에서 추가 버튼 클릭 시
    $('.form_add_btn').on('click', function () {
        let passListCount = $('.formTbBd:last .addNum').text();
        if(passListCount < 50){
            // 마지막 .formTbBd 요소를 복사하여 추가
            let lastRow = $('.formTbBd:last');
            let newRow = lastRow.clone(true);

            // 추가된 .formTbBd의 .addNum 값 증가
            let passNum = parseInt(newRow.find('.addNum').text());
            newRow.find('.addNum').text(passNum + 1);

            // 추가된 .formTbBd의 입력 필드 초기화
            newRow.find('input').val('');

            // 추가된 .formTbBd를 .formPassBd에 추가
            lastRow.after(newRow);

            updatePassNum(); // .addNum 값 업데이트
        }else{
            alert('출입증 신청은 최대 50명까지만 등록 가능합니다.');
        }
    });

    $('.addDel').on('click', function () {
        Swal.fire({
            title: '해당 출입증 신청 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(this).siblings('input[type=hidden]').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mypage/step/deleteExhibitPass.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '출입증 신청 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(this).closest('.formTbBd').remove();
                updatePassNum(); // .addNum 값 업데이트
            }//isConfirmed
        }); //swal
    });

    // .addNum 값 업데이트 함수
    function updatePassNum() {
        $('.formTbBd').each(function (index) {
            $(this).find('.addNum').text(index + 1);
        });
    }

    // 수출상담회 참가 여부 라디오 버튼 변경 시
    $('.form_add_buyer').hide();
    $('input[name="exportMeetingYn"]').on('change', function () {
        if ($(this).is(':checked') && $(this).attr('id') === 'exportMeetingY') {
            $('.form_add_buyer').show(); // .form_chuga 요소를 보여줌
        } else {
            $('.form_add_buyer').hide(); // .form_chuga 요소를 숨김
        }
    });

    // 경품제공 신청 팝업
    $('.addFormGift , .modifyFormGift').on('click', function () {
        $('.popFormGift').toggleClass('on');
        $('body').toggleClass('lock_scroll');
        document.querySelector('.pop_box').scrollTop = 0;

        /* 경품사진 , 회사로고 ID Setting */
        let formChugaListLength = parseInt($('div.form_chuga_list').length) + 1;
        $('input[type=file][name=giftPhotoFile]').eq(0).attr('id','giftPhotoFile'+formChugaListLength);
        $('input[type=file][name=giftPhotoFile]').eq(0).next('label').attr('for','giftPhotoFile'+formChugaListLength);
        $('input[type=file][name=giftCompanyLogoFile]').eq(0).attr('id','giftCompanyLogoFile'+formChugaListLength);
        $('input[type=file][name=giftCompanyLogoFile]').eq(0).next('label').attr('for','giftCompanyLogoFile'+formChugaListLength);

        return false;
    });

    // 수출상담회 바이어 신청 팝업
    $('.addFormBuyer, .modifyFormBuyer').on('click', function () {
        $('.popFormBuyer').toggleClass('on');
        $('body').toggleClass('lock_scroll');
        document.querySelector('.pop_box').scrollTop = 0;
        return false;
    });

    // 경품제공 신청, 수출상담회 바이어 팝업 저장 버튼 클릭 시
    /*$('.popForm .btnSave').on('click', function () {
        $('.popForm').toggleClass('on');
        $('body').toggleClass('lock_scroll');
        document.querySelector('.pop_box').scrollTop = 0;
        return false;
    });*/

    // 경품제공 신청, 수출상담회 바이어 삭제버튼 클릭 시 리스트 삭제
    /*$('.form_chuga').on('click', '.delFormList', function () {
        $(this).closest('.form_chuga_list').remove();
    });*/


    // 참관객 신청 직장명 검색
    $('.companyNameSh').on('click', function () {
        $('.popCompanyName').toggleClass('on');
        $('body').toggleClass('lock_scroll');
    });

    $('.directCompanyName').on('click', function () {
        $('.popDirectName').toggleClass('on');
    });

    // 유틸리티 계산
    $('.form_util input.num_sum').on('keyup', function () {
        var cnt = $(".form_util input.num_sum").length;
        //console.log(cnt);

        for (var i = 1; i < cnt; i++) {
            var sum = parseInt($(this).val() || 0);
            sum++
            //console.log(sum);
        }

        //콤마찍기
        function comma(str) {
            str = String(str);
            return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
        }

        function inputNumberFormat(obj) {
            obj.value = comma(uncomma(obj.value));
        }

        var item1 = parseInt($("#utility_jugan_cnt").val() || 0);
        var item2 = parseInt($("#utility_day_cnt").val() || 0);
        var item3 = parseInt($("#utility_compressed_air_cnt").val() || 0);
        var item4 = parseInt($("#utility_water_basic_cnt").val() || 0);
        var item5 = parseInt($("#form_add_item5").val() || 0);
        var item6 = parseInt($("#utility_internet_cnt").val() || 0);
        var item7 = parseInt($("#utility_pytex_new_cnt").val() || 0);
        var item8 = parseInt($("#utility_pytex_re_cnt").val() || 0);
        var item9 = parseInt($("#utility_barcode_cnt").val() || 0);

        var price1 = item1 * 70000;
        var price2 = item2 * 80000;
        var price3 = item3 * 180000;
        var price4 = item4 * 180000;
        var price5 = item5 * 60000;
        var price6 = item6 * 160000;
        var price7 = item7 * 80000;
        var price8 = item8 * 50000;
        var price9 = item9 * 200000;
        var total = price1 + price2 + price3 + price4 + price5 + price6 + price7 + price8 + price9;
        // var vat = total * 10 / 100;
        // var sum = total + vat;

        //console.log(sum);
        $("#utility_jugan_fee").val("￦ " + comma(price1));
        $("#utility_day_fee").val("￦ " + comma(price2));
        $("#utility_compressed_air_fee").val("￦ " + comma(price3));
        $("#utility_water_basic_fee").val("￦ " + comma(price4));
        $("#form_add_price5").val("￦ " + comma(price5));
        $("#utility_internet_fee").val("￦ " + comma(price6));
        $("#utility_pytex_new_fee").val("￦ " + comma(price7));
        $("#utility_pytex_re_fee").val("￦ " + comma(price8));
        $("#utility_barcode_fee").val("￦ " + comma(price9));
        $("#form_add_total").val("￦ " + comma(total));
        // $("#form_add_vat").val(comma(Math.ceil(vat)));
        // $("#form_add_sum").val(comma(Math.ceil(sum)));

        $(this).val($(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    });


    ///////////////// 담당자정보 추가 /////////////////
    let managerInfoCount = $('.managerInfoBox:last .managerInfoNum').text();

    // .managerInfoBox를 추가하는 이벤트 핸들러 추가
    $('.managerInfoAdd').on('click', function () {
        if (managerInfoCount < 3) { // 현재 담당자 정보 개수가 3개 미만인 경우에만 추가
            let newManagerInfoBox = $('.managerInfoBox:first').clone(true);
            managerInfoCount++;
            newManagerInfoBox.find('.managerInfoNum').text(managerInfoCount);
            newManagerInfoBox.find('input[type="hidden"]').val('');
            newManagerInfoBox.find('input[type="text"]').val('');
            newManagerInfoBox.find('input[type="tel"]').val('');
            newManagerInfoBox.find('input[type="email"]').val('');
            newManagerInfoBox.find('select option').prop('selected',false);

            // 복제된 .managerInfoBox 내의 삭제 버튼 보이기
            newManagerInfoBox.find('.managerInfoDel').show();

            newManagerInfoBox.find('.managerInfoDel').on('click', function () {
                deleteManagerInfoBox(this);
            });
            $('.managerInfoBox:last').after(newManagerInfoBox);
            updateManagerInfoNum();
        }else{
            alert('담당자 정보는 최대 3명까지만 등록 가능합니다.');
        }
    });

    // .managerInfoBox를 삭제하는 이벤트 핸들러
    function deleteManagerInfoBox(el) {
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
                let seq = $(el).siblings('input[type=hidden]').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mypage/step/deleteCharge.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '담당자 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(el).closest('.managerInfoBox').remove();
                managerInfoCount--; // 개수를 감소시킴
                updateManagerInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .managerInfoBox의 .managerInfoNum 번호 업데이트
    function updateManagerInfoNum() {
        $('.managerInfoBox').each(function (index) {
            $(this).find('.managerInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .managerInfoBox 내의 삭제 버튼 숨기기
    $('.managerInfoBox:first .managerInfoDel').hide();

    // 첫 번째 .managerInfoBox의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.managerInfoDel').on('click', function() {
        deleteManagerInfoBox(this);
    });

    ///////////////// 전시정보 추가 /////////////////
    let exhiInfoCount = $('.exhiInfoBox:last .exhiInfoNum').text();

    // .exhiInfoBox를 추가하는 이벤트 핸들러 추가
    $('.exhiInfoAdd').on('click', function () {
        if(exhiInfoCount < 20){
            let newExhiInfoBox = $('.exhiInfoBox:first').clone(true);
            exhiInfoCount++;
            newExhiInfoBox.find('.exhiInfoNum').text(exhiInfoCount);
            newExhiInfoBox.find('input[type="text"]').val('');
            newExhiInfoBox.find('textarea').val('');
            newExhiInfoBox.find('input[type="hidden"]').val('');

            // 복제된 .exhiInfoBox 내의 삭제 버튼 보이기
            newExhiInfoBox.find('.exhiInfoDel').show();

            newExhiInfoBox.find('.exhiInfoDel').on('click', function () {
                deleteExhiInfoBox(this);
            });
            $('.exhiInfoBox:last').after(newExhiInfoBox);
            updateExhiInfoNum(this);
        }else{
            alert('전시정보는 최대 20개까지만 등록 가능합니다.');
        }
    });

    // .exhiInfoBox를 삭제하는 이벤트 핸들러
    function deleteExhiInfoBox(el) {
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
                let seq = $(el).siblings('input[type=hidden]').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mypage/step/deleteDisplay.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '전시 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(el).closest('.exhiInfoBox').remove();
                exhiInfoCount--;
                updateExhiInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .exhiInfoBox의 .exhiInfoNum 번호 업데이트
    function updateExhiInfoNum() {
        $('.exhiInfoBox').each(function (index) {
            $(this).find('.exhiInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .exhiInfoBox 내의 삭제 버튼 숨기기
    $('.exhiInfoBox:first .exhiInfoDel').hide();

    // 첫 번째 .exhiInfoBox의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.exhiInfoDel').on('click', function () {
        deleteExhiInfoBox(this);
    });


    ///////////////// 홍보이미지 추가 /////////////////
    let proImgCount = $('.proImgBox:last .proImgNum').text();

    // .proImgBox를 추가하는 이벤트 핸들러 추가
    $('.proImgAdd').on('click', function () {
        if(proImgCount < 3){
            let newProImgBox = $('.proImgBox:first').clone(true);
            proImgCount++;
            newProImgBox.find('item').find('p').text(newProImgBox.find('item').find('p').text().replaceAll(' (메인)'), '');
            newProImgBox.find('.proImgNum').text(proImgCount);
            newProImgBox.find('input[type="text"]').val('');
            newProImgBox.find('input[type="hidden"]').val('');

            // 복제된 .proImgBox에서 삭제 버튼 보이기
            newProImgBox.find('.proImgDel').show();

            // 파일 입력 초기화 및 비활성화 속성 제거
            let fileInput = newProImgBox.find('.upload_hidden');
            let fileNameInput = newProImgBox.find('.upload_name');
            fileInput.val('').attr('id', 'promotionImageFile' + proImgCount);
            fileNameInput.val('').attr('disabled', true).attr('id', 'promotionImage' + proImgCount).attr('name', 'promotionImage');
            newProImgBox.find('label').attr('for', 'promotionImageFile' + proImgCount);

            newProImgBox.find('.proImgDel').on('click', function () {
                deleteProImgBox(this);
            });
            $('.proImgBox:last').after(newProImgBox);
            updateProImgNum(this);
        }else{
            alert('홍보이미지는 최대 3개까지 등록 가능합니다.');
        }
    });

    // .proImgBox를 삭제하는 이벤트 핸들러
    function deleteProImgBox(el) {
        $(el).closest('.proImgBox').remove();
        proImgCount--;
        updateProImgNum(el);
    }

    // 각 .proImgBox의 .proImgNum 번호 업데이트
    function updateProImgNum(el) {
        $('.proImgBox').each(function (index) {
            $(el).find('.proImgNum').text(index + 1);
        });
    }

    // 첫 번째 .proImgBox 내의 삭제 버튼 숨기기
    $('.proImgBox:lt(3) .proImgDel').hide();

    // 첫 번째 .proImgBox의 삭제 버튼에 초기 이벤트 핸들러 추가
    $('.proImgDel').on('click', function () {
        deleteProImgBox(this);
    });

    ///////////////// 참관객 동반자 추가 /////////////////

    //  참관객 동반자 여부 라디오 버튼 변경 시
    $('.visitPartnerBox, .visitPartnerAdd').hide();
    $('input[name="partnerYn"]').on('change', function () {
        if ($(this).is(':checked') && $(this).attr('id') === 'Y') {
            $('.visitPartnerBox, .visitPartnerAdd').show(); // .form_chuga 요소를 보여줌
        } else {
            $('.visitPartnerBox, .visitPartnerAdd').hide(); // .form_chuga 요소를 숨김
        }
    });

    let visitPartnerCount = $('.visitPartnerBox:last .visitPartnerNum').text();

    // .visitPartnerBox를 추가하는 이벤트 핸들러 추가
    $('.visitPartnerAdd').on('click', function () {
        if(visitPartnerCount < 3){
            let newVisitPartner = $('.visitPartnerBox:first').clone(true);
            visitPartnerCount++;
            newVisitPartner.find('.visitPartnerNum').text(visitPartnerCount);
            newVisitPartner.find('input[type="text"]').val('');
            newVisitPartner.find('input[type="hidden"]').val('');

            // 복제된 .visitPartnerBox 내의 삭제 버튼 보이기
            newVisitPartner.find('.visitPartnerDel').show();

            newVisitPartner.find('.visitPartnerDel').on('click', function () {
                deleteVisitPartner(this);
            });
            $('.visitPartnerBox:last').after(newVisitPartner);
            updateExhiInfoNum();
        }else{
            alert('동반자는 최대 3명까지 등록 가능합니다.\nUp to 3 people can register');
        }
    });

    // .visitPartnerBox를 삭제하는 이벤트 핸들러
    function deleteVisitPartner(el) {
        Swal.fire({
            title: '[동반자 삭제]',
            html: '해당 동반자 정보를 삭제하시겠습니까?',
            icon: 'warning',
            allowOutsideClick: false,
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: '삭제하기',
            cancelButtonColor: '#A1A5B7',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                let seq = $(el).siblings('input[type=hidden]').val();
                if(nvl(seq,'') !== ''){
                    let jsonObj = {
                        seq: seq
                    };
                    let resData = ajaxConnect('/visitor/mypage/deletePartner.do','post',jsonObj);
                    if(resData.resultCode !== '0'){
                        showMessage('', 'error', '에러 발생', '전시 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }
                }

                $(el).closest('.visitPartnerBox').remove();
                visitPartnerCount--;
                updateExhiInfoNum();
            }//isConfirmed
        }); //swal
    }

    // 각 .visitPartnerBox의 .visitPartnerNum 번호 업데이트
    function updateExhiInfoNum() {
        $('.visitPartnerBox').each(function (index) {
            $(this).find('.visitPartnerNum').text(index + 1);
        });
    }

    // 첫 번째 .visitPartnerBox 내의 삭제 버튼 숨기기
    $('.visitPartnerBox:first .visitPartnerDel').hide();

    // 첫 번째 .visitPartnerBox의 삭제 버튼에 대한 초기 이벤트 핸들러 추가
    $('.visitPartnerDel').on('click', function () {
        deleteVisitPartner(this);
    });


    ///////////////// 온라인 전시관 정보 추가 /////////////////
    // 온라인 전시관 정보 추가 시 사용할 카운터 변수
    let onlineInfoCount = $('.onlineInfoBox:last .onlineInfoNum').text();

    // .onlineInfoBox를 추가하는 이벤트 핸들러 추가
    $('.onlineInfoAdd').on('click', function () {
        if(onlineInfoCount < 30) {
            let onlinePrdBoxes = $(this).closest('.onlineInfoBox').find('.onlinePrdBox');
            let onlinePrdBoxCount = onlinePrdBoxes.length;
            // 아이디명 변경
            let newIdSuffix = onlinePrdBoxCount + 1;

            let newOnlineInfoBox = $('.onlineInfoBox:first').clone(true);

            onlineInfoCount++;
            newOnlineInfoBox.find('.onlineInfoNum').text(onlineInfoCount);
            newOnlineInfoBox.find('textarea').val('');
            newOnlineInfoBox.find('input[type=text]').val('');
            newOnlineInfoBox.find('input[type=hidden]').val('');

            // 복제된 .onlineInfoBox 는 제품 사진 하나만 남기기
            let onlinePrdBoxList = newOnlineInfoBox.find('.onlinePrdBox');
            for (let i = 0; i <= onlinePrdBoxList.length; i++) {
                if (i !== 0) {
                    onlinePrdBoxList.eq(i).remove();
                }
            }

            // 복제된 .onlineInfoBox 는 제품 기존 값 목록 제거
            newOnlineInfoBox.find('.preValueList').remove();
            newOnlineInfoBox.find('.preOptionList').remove();

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

            // 새로 추가한 .onlineInfoBox 내에서 select 요소 및 id, name 설정
            let select1 = newOnlineInfoBox.find('select[name="productOptionBig"]');
            let select2 = newOnlineInfoBox.find('select[name="productOptionSmall"]');

            let productOptionBigId = 'productOptionBig_' + onlineInfoCount;
            let productOptionSmallId = 'productOptionSmall_' + onlineInfoCount;
            let productOptionBigName = 'productOptionBig';
            let productOptionSmallName = 'productOptionSmall';

            select1.attr('id', productOptionBigId);
            select2.attr('id', productOptionSmallId);
            select1.attr('name', productOptionBigName);
            select2.attr('name', productOptionSmallName);

            // 파일 입력 초기화 및 비활성화 속성 제거
            let fileInput = newOnlineInfoBox.find('.upload_hidden');
            let fileNameInput = newOnlineInfoBox.find('.upload_name');
            fileInput.val('').attr('id', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix);
            fileNameInput.val('').attr('disabled', true).attr('id', 'productImage' + onlineInfoCount + '_' + newIdSuffix);
            newOnlineInfoBox.find('label').attr('for', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix);

            $('.onlineInfoBox:last').after(newOnlineInfoBox);
            updateOnlineInfoNum(this);
        }else{
            alert('온라인 전시관 제품 정보는 최대 30개까지만 등록 가능합니다.');
        }
    });

    // .onlineInfoBox를 삭제하는 이벤트 핸들러
    function deleteOnlineInfoBox(el) {
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
                let seq = $(el).siblings('input').val();
                if(nvl(seq,"") !== ""){
                    let jsonObj = {
                        "seq": seq
                    };
                    let resData = ajaxConnect('/mypage/step/deleteOnline.do','post',jsonObj);
                    if(resData.resultCode !== "0"){
                        showMessage('', 'error', '에러 발생', '제품 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                    }

                    let fileList = $(el).parent().parent().find('.preValueList').find('.file_box ul .productImageFile_li').find('input[type=hidden][name=productImageUploadFile]');
                    if(nvl(fileList, "") !== ""){
                        for(let i=0; i<fileList.length; i++){
                            let file_id = fileList[i].id;
                            if(nvl(file_id,"") !== ""){
                                let file_jsonObj = {
                                    "id": file_id
                                };
                                let resData = ajaxConnect('/mypage/step/deleteFile.do','post',file_jsonObj);
                                if(resData.resultCode !== "0"){
                                    showMessage('', 'error', '에러 발생', '파일 정보 삭제를 실패하였습니다. 관리자에게 문의해주세요. ' + resData.resultMessage, '');
                                }
                            }
                        }
                    }
                }

                $(el).closest('.onlineInfoBox').remove();
                onlineInfoCount--;
                updateOnlineInfoNum(el);
            }//isConfirmed
        }); //swal
    }

    // 각 .onlineInfoBox의 .onlineInfoNum 번호 업데이트
    function updateOnlineInfoNum(el) {
        $('.onlineInfoBox').each(function (index) {
            $(el).find('.onlineInfoNum').text(index + 1);
        });
    }

    // 첫 번째 .onlineInfoBox 내의 삭제 버튼 숨기기
    $('.onlineInfoBox:first .onlineInfoDel').hide();

    // 첫 번째 .onlineInfoBox의 삭제 버튼에 초기 이벤트 핸들러 추가
    $('.onlineInfoDel').on('click', function () {
        deleteOnlineInfoBox(this);
    });

    ///////////////// 온라인 전시관 정보 제품사진 추가 /////////////////
    // .onlinePrdAdd 클릭 시 새로운 .onlinePrdBox 추가
    $('.onlinePrdAdd').on('click', function () {
        let onlinePrdBoxes = $(this).closest('.onlineInfoBox').find('.onlinePrdBox');
        let onlinePrdBoxCount = parseInt(onlinePrdBoxes.length) + parseInt($(this).closest('.onlineInfoBox').find('li.productImageFile_li').length);

        if (onlinePrdBoxCount < 5) {
            // 복제할 .onlinePrdBox 요소를 선택합니다.
            let newOnlinePrdBox = $('.onlinePrdBox:first').clone(true, true);

            // 아이디명 변경
            let onlineInfoCount = parseInt($(this).closest('.onlineInfoBox').find('.onlineInfoNum').text());
            /*let newIdSuffix = parseInt($(this).closest('.onlineInfoBox').find('li.productImageFile_li').length === 0 ? 0 : $(this).closest('.onlineInfoBox').find('li.productImageFile_li').length)
                + parseInt($(this).closest('.onlineInfoBox').find('input[type=text][name=productImage]').length === 0 ? 0 : $(this).closest('.onlineInfoBox').find('input[type=text][name=productImage]').length)
                + 1;*/
            let lastPrdTextInputID = $(this).closest('.onlinePrdBox').nextAll().last().find('.upload_name').attr('id');
            let newIdSuffix = 0;
            if(nullToEmpty(lastPrdTextInputID) !== ''){
                newIdSuffix = parseInt(lastPrdTextInputID.split('_')[1])+1;
            }else{
                lastPrdTextInputID = $(this).closest('.onlinePrdBox').find('.upload_name').attr('id');
                newIdSuffix = parseInt(lastPrdTextInputID.split('_')[1])+1;
            }

            newOnlinePrdBox.find('.upload_hidden').attr('id', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix).attr('name', 'productImageFile');
            newOnlinePrdBox.find('.upload_name').attr('name', 'productImage').attr('id', 'productImage' + onlineInfoCount + '_' + newIdSuffix);
            newOnlinePrdBox.find('label').attr('for', 'productImageFile' + onlineInfoCount + '_' + newIdSuffix);

            // 새로운 .onlinePrdBox를 추가합니다.
            $(this).closest('.onlineInfoBox').find('.onlinePrdBox:last').after(newOnlinePrdBox);

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
    $('.onlinePrdDel').on('click', function () {
        $(this).closest('.onlinePrdBox').remove();
        updateOnlinePrdBox();
    });

    // 초기 상태에서는 모든 .onlinePrdDel를 숨김
    $('.onlinePrdBox .onlinePrdDel').hide();

    // .onlinePrdBox 갯수를 업데이트하고 최대 5개까지 제한
    function updateOnlinePrdBox() {
        $('.onlineInfoBox').each(function () {
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

    //기참가연도
    $('input[type=checkbox][name=prePartYear]').on('change', function(){
        let prePartYear = $(this).val();
        if(prePartYear === 'first'){
            $('input[type=checkbox][name=prePartYear]:not(:first)').prop('checked',false);
        }else{
            $('input[type=checkbox][name=prePartYear]:first').prop('checked',false);
        }
    })

    //지난 전시회 참관 여부
    $('input[type=checkbox][name=preObservationGbn]').on('change', function(){
        let preObservationGbn = $(this).val();
        if(preObservationGbn === 'first'){
            $('input[type=checkbox][name=preObservationGbn]:not(:first)').prop('checked',false);
        }else{
            $('input[type=checkbox][name=preObservationGbn]:first').prop('checked',false);
        }
    })

    $('#industryPart').on('change', function(){
        let industryPart = $(this).val();
        if(industryPart === '기타'){
            $('#industryPartEtc').prop('disabled', false).val('');
            $('#industryPartEtc').focus();
        }else{
            $('#industryPartEtc').prop('disabled', true).val('');
        }
    })

    $('#companyPurposeKo, #companyPurposeEn').on('change', function(){
        let idx = $(this).find('option').index($(this).find('option:selected'));
        $('#companyPurposeKo').find('option').eq(idx).prop('selected', true);
        $('#companyPurposeEn').find('option').eq(idx).prop('selected', true);
    })

    // 주요프로그램 탭
    function setHoverEvent() {
        if ($(window).width() <= 1199) {
            $('.hoverBtn').off('mouseover mouseleave').on('click', function (e) {
                e.stopPropagation(); // 이벤트 버블링 방지
                var tab_id = $(this).attr('data-hover');
                var target = $("#" + tab_id);

                // 현재 클릭한 요소가 열려 있다면 닫고, 아니면 열기
                if (target.hasClass('on')) {
                    target.removeClass('on');
                } else {
                    $('.hoverCont').removeClass('on');
                    target.addClass('on');
                }
            });

            // 문서의 다른 곳을 클릭하면 닫기
            $(document).off('click.hoverClose').on('click.hoverClose', function () {
                $('.hoverCont').removeClass('on');
            });

        } else {
            $('.hoverBtn').off('click').on('mouseover', function () {
                var tab_id = $(this).attr('data-hover');
                $('.hoverCont').removeClass('on');
                $("#" + tab_id).addClass('on');
            }).on('mouseleave', function () {
                var tab_id = $(this).attr('data-hover');
                $("#" + tab_id).removeClass('on');
            });

            // PC 모드에서는 클릭 이벤트 해제
            $(document).off('click.hoverClose');
        }
    }

    // 페이지 로드 시 실행
    $(document).ready(function () {
        setHoverEvent();
    });

    // 화면 크기 변경 시 다시 체크
    $(window).on('resize', function () {
        setHoverEvent();
    });

});

function nullToEmpty(nullStr){
    let convertStr;
    if(nullStr === null || nullStr === 'null' || nullStr === undefined || nullStr === 'undefined' || Object.keys(nullStr).length === 0){
        convertStr = '';
    }else{
        convertStr = nullStr;
    }
    return convertStr;
}