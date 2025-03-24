var pageNum = 1; // 페이지 번호 생성 시점에 따른 변수 초기화
var transferYear = (new Date().getFullYear()).toString();
$(function(){
    //페이지 오픈 시 default ()
    onlineProductList(pageNum);

});

const showPageCnt = 6; // 화면에 보일 페이지 번호 개수

/**
 * @param pageNum 출력 페이지 번호
 * */
function onlineProductList(pageNum) {
    // 데이터 조회
    searchPosts(pageNum);

    // 페이지당 건수(10, 30, 50)가 변경되면 재조회
    /*$('#countPerPage').change(function() {
        searchPosts(1);
    });*/

    // 페이지 번호 클릭
    $(document).on('click', '.paging>ol>li>a', function() {
        if (!$(this).hasClass('this')) {
            $(this).parent().find('a.this').removeClass('this');
            $(this).addClass('this');

            searchPosts(Number($(this).text()));
        }
    });

    // 페이징 Icon(<<, <, >, >>) 클릭
    $(document).on('click', '.paging>span', function() {
        const totalCnt = parseInt($('span.total').text());
        const countPerPage = 12;
        const totalPage = Math.ceil(totalCnt / countPerPage);

        const id = $(this).attr('id');

        if (id === 'first_page') { //<<
            searchPosts(1);
        } else if (id === 'prev_page') { //<
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const prevPage = Math.min(...arrPages) - 1;
            searchPosts(prevPage);
        } else if (id === 'next_page') { //>
            let arrPages = [];
            $('.paging>ol>li>a').each(function() {
                arrPages.push(Number($(this).text()));
            });
            const nextPage = Math.max(...arrPages) + 1;
            searchPosts(nextPage);
        } else if (id === 'last_page') { //>>
            searchPosts(totalPage);
        }
    });

}

/**
 * 페이지별 데이터를 조회합니다.
 * @param {int} pageNum - Page Number
 */
function searchPosts(pageNum) {
    const countPerPage = 12; // 페이지당 노출 개수
    let start = (pageNum - 1) * countPerPage;
    if(start < 0){
        start = 0;
    }
    let flag = true;
    /* 검색조건 */
    let productOptionBig_sel = $('#productOptionBig').val();
    let productOptionSmall_sel = $('#productOptionSmall').val();
    let productWidth_slider = $('#prdLengthAmount').val().split(' - ');
    let productWidth_min = productWidth_slider[0];
    let productWidth_max = productWidth_slider[1];
    let productHorsePower_slider = $('#prdHpAmount').val().split(' - ');
    let productHorsePower_min = productHorsePower_slider[0];
    let productHorsePower_max = productHorsePower_slider[1];

    let condition = $('#searchCondition option:selected').val();
    let searchText = $('#searchText').val();

    let jsonObj = {
        pageNum: start,
        rows: countPerPage,
        transferYear: transferYear,
        productOptionBig: productOptionBig_sel,
        productOptionSmall: productOptionSmall_sel,
        productWidthMin: productWidth_min,
        productWidthMax: productWidth_max,
        productHorsePowerMin: productHorsePower_min,
        productHorsePowerMax: productHorsePower_max,
        condition: condition,
        searchText: searchText
    };

    $.ajax({
        url: '/online/product/selectList.do',
        method: 'post',
        data: JSON.stringify(jsonObj),
        contentType: 'application/json; charset=utf-8' //server charset 확인 필요
    })
    .done(function (data, status){
        // console.log(status);
        //console.log(data);
        let results = JSON.parse(data).relatedItemList;
        let str = '';
        $.each(results , function(i){
            let seq = results[i].seq;
            let id = results[i].id;
            let companyNameKo = results[i].companyNameKo;
            let companyNameEn = results[i].companyNameEn;
            let productFullFilePath = results[i].productFullFilePath;
            if(nvl(productFullFilePath,"") !== ""){
                productFullFilePath = productFullFilePath.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
            }else{
                productFullFilePath = '';
            }
            let logoFullFilePath = results[i].logoFullFilePath;
            if(nvl(logoFullFilePath,"") !== ""){
                logoFullFilePath = logoFullFilePath.toString().replace('/usr/local/tomcat/webapps', '/../../../..');
            }else{
                logoFullFilePath = '';
            }
            let productNameKo = results[i].productNameKo;
            let productNameEn = results[i].productNameEn;
            let productOptionBig = results[i].productOptionBig;
            let productOptionSmall = results[i].productOptionSmall;

            //console.log(seq, id, productFullFilePath, logoFullFilePath, productNameKo, productNameEn, productOptionBig, productOptionSmall);

            str += '<li>';

            /*str += '<div class="btnLike">';
                str += '<img src="' + window.location.origin + '/img/icon_like.png" class="likeImg">';
            str += '</div>';*/
            let url = '/online/product_view.do?seq=' + seq;
            str += '<a href="' + url + '">';
                str += '<div class="thumbnail">';
                    str += '<div class="thumb75 thumbBox">';

                    if(productFullFilePath !== '' && !productFullFilePath.includes('.ai')){
                        str += '<img src="'+ productFullFilePath +'" class="thumbImg">';
                    }else{
                        let min = Math.ceil(1);
                        let max = Math.floor(6);
                        let value = Math.floor(Math.random() * (max - min + 1)) + min; //최댓값도 포함, 최솟값도 포함
                        str += '<img src="' + window.location.origin + '/img/thumb_exh_img_0' + value + '.jpg" class="thumbImg">';
                    }
                    str += '</div>';

                    str += '<div class="thumbLogo">';
                    str += '<p>';
                    if(logoFullFilePath !== '' && !logoFullFilePath.includes('.ai')){
                        str += '<img src="'+ logoFullFilePath +'">';
                    }else{
                        str += '<img src="' + window.location.origin + '/img/thumb_exh_logo_sample.png">';
                    }
                    str += '</p>';
                    str += '</div>';
                str += '</div>';
                str += '<div class="prdTxt">';
                    str += '<div class="name">';
                        str += companyNameKo;
                    str += '</div>';
                    str += '<div class="prdName">';
                    str += productNameKo + ' ' + productNameEn;
                    str += '</div>';
                    str += '<div class="type">';
                    str += productOptionBig + ' > ' + productOptionSmall;
                    str += '</div>';
                str += '</div>';
            str += '</a>';
            str += '</li>';

            if(results.length === (i+1)){ // each 문이 모두 실행되면 아래 페이징 정보 세팅 실행
                flag = false;
            }
        });

        $('.prdList').empty();
        $('.prdList').html(str);

        if(nvl(results,"") !== "") {
            // 맨 처음에만 total 값 세팅
            if (pageNum === 1) {
                $('span.total').text(results[0].totalRecords || 0);
            }
        }else{ // 데이터 없는 경우 ( 해당 권역에 양조장 없을 경우 )
            $('span.total').text(0);
            $('.paging ol').empty(); // 페이징 번호 없애기
            $('#first_page').hide();
            $('#prev_page').hide();
            $('#next_page').hide();
            $('#last_page').hide();

            let emptyStr = '';
            emptyStr += '<li>';
            emptyStr += '<div>';
            emptyStr += '<p style="font-size: 18px; font-weight: bold;">';
            emptyStr += '해당 조건으로 검색된 제품이 없습니다.';
            emptyStr += '</p>';
            emptyStr += '</div>';
            emptyStr += '</li>';

            $('.prdList').empty();
            $('.prdList').html(emptyStr);
        }
    })
    .fail(function(xhr, status, errorThrown) {
        $('body').html("오류가 발생했습니다.")
            .append("<br>오류명: " + errorThrown)
            .append("<br>상태: " + status);
    })
    .always(function() {
        if(!flag){ // flag = false 이면 아래 페이징 정보 세팅 실행
            // 페이징 정보 세팅
            setPaging(pageNum);
        }
    });
}

/**
 * 페이징 정보를 세팅합니다.
 * @param {int} pageNum - Page Number
 */
function setPaging(pageNum) {
    const totalCnt = parseInt($('span.total').text());
    const countPerPage = 12;

    const currentPage = pageNum;
    const totalPage = Math.ceil(totalCnt / countPerPage);

    showAllIcon();

    if (currentPage <= showPageCnt) {
        $('#first_page').hide();
        $('#prev_page').hide();
    }
    if (
        totalPage <= showPageCnt ||
        Math.ceil(currentPage / showPageCnt) * showPageCnt + 1 > totalPage
    ) {
        $('#next_page').hide();
        $('#last_page').hide();
    }

    let start = Math.floor((currentPage - 1) / showPageCnt) * showPageCnt + 1;
    let sPagesHtml = '';
    for (const end = start + showPageCnt; start < end && start <= totalPage; start++) {
        sPagesHtml += '<li><a class="' + (start === currentPage ? 'this' : 'other') + '" style="cursor: pointer">' + start + '</a></li>';
    }
    $('.paging ol').html(sPagesHtml);
}

/**
 * Icon(<<, <, >, >>) All Show
 */
function showAllIcon() {
    $('#first_page').show();
    $('#prev_page').show();
    $('#next_page').show();
    $('#last_page').show();
};