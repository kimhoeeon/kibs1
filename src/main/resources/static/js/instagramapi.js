/*let jsonObj = {
    refreshDate : refreshDate()
};

function refreshDate(){
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

    return year + '-' + month + '-' + day + ' ' + hour + ':' + minites + ':' + seconds;
}*/

$.ajax({
    url: '/insta/refresh/select.do',
    method: 'POST',
    contentType: 'application/json; charset=utf-8' //server charset 확인 필요
})
.done(function (data) {

    let refreshYn = data.refreshYn;
    let token = data.token;

    if(refreshYn === '1'){
        // 유효기간 60일
        // 59일 일때 갱신 처리
        $.ajax({
            type:'GET',
            url : 'https://graph.instagram.com/refresh_access_token?grant_type=ig_refresh_token&access_token=' + token,
            dataType:'json',
            success:function(data){
                token = data["access_token"];

                console.log('refresh token : ' + token);

                let jsonObj = {
                    token: token
                }

                $.ajax({
                    url: '/insta/refresh/update.do',
                    method: 'POST',
                    data: JSON.stringify(jsonObj),
                    contentType: 'application/json; charset=utf-8' //server charset 확인 필요
                })
                .done(function (data) {
                    console.log(data);
                    if(data.resultCode !== '0') {
                        console.log('Insta token update error');
                    }

                }).fail(function (xhr, status, errorThrown) {
                    /*$('body').html("오류가 발생했습니다.")
                        .append("<br>오류명: " + errorThrown)
                        .append("<br>상태: " + status);*/
                    alert('Insta Token Refresh 오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
                })

            },
            error:function(request, error) {
                alert("insta token refresh error " + error);
            }
        });
    }

    insta_set(token);

})
.fail(function (xhr, status, errorThrown) {
    /*$('body').html("오류가 발생했습니다.")
        .append("<br>오류명: " + errorThrown)
        .append("<br>상태: " + status);*/
    alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + errorThrown + "\n상태 : " + status);
})

function insta_set(token){
    $.ajax({
        type: "GET",
        dataType: "jsonp",
        cache: false,
        url: "https://graph.instagram.com/me/media?access_token=" + token + "&fields=id,caption,media_type,media_url,thumbnail_url,permalink",
        success: function(response) {
            //console.log(response);
            if (response.data !== undefined && response.data.length > 0) {
                for(let i = 0; i < 4; i++){
                    if(response.data[i]){
                        let item = response.data[i];
                        let image_url = "";
                        let post = "";

                        if(item.media_type === "VIDEO"){
                            image_url = item.thumbnail_url;
                        }else{
                            image_url = item.media_url;
                        }

                        post += '<div class="instagram_item instagram_item'+i+'">';
                            post += '<a href="'+ item.permalink +'" target="_blank" rel="noopener noreferrer" style="background-image: url(' + image_url + ');">';
                                post += '<p>'+ item.caption +'</p>';
                            post += '</a>';
                        post += '</div>';

                        $('#instagram').append(post);
                    }else{
                        // if no curent item
                        show_fallback();
                    }
                }
            }else{
                // if api error
                show_fallback();
            }
        },
        error :function(){
            // if http error
            show_fallback();
        }

    });
}


function show_fallback(){
    console.log('instagram load error');
}