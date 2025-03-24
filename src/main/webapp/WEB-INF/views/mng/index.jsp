<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="title" property="og:title" content="2025 경기국제보트쇼">
    <meta name="description" property="og:description" content="2025 경기국제보트쇼">
    <meta name="author" content="2025 경기국제보트쇼">
    <meta name="format-detection" content="telephone=no"/>
    <title>2025 경기국제보트쇼 관리자</title>
    <link href="/css/reset.css" rel="stylesheet">
    <link href="/css/mngStyle.css" rel="stylesheet">

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
</head>

<script type="text/javascript">
    window.history.forward();
    function noBack(){window.history.forward();}
</script>


<%-- 페이지 뒤로가기 막기 --%>
<%--<script>
    history.pushState(null, null, location.href);
    window.onpopstate = function (event) {
        history.go(1);
    };
</script>--%>

<body onload="noBack();" onpageshow="if(event.persisted) noBack();" onunload="">

    <div id="main_container">
        <div id="login">
            <form id="login_form" name="login_form" action="/mng/main.do" method="post" onsubmit="return false;">
                <div>
                    <p class="brand-logo">
                        <img src="/img/logo.png" class="mngLogo">
                        <br><br>
                        <span id="logo_txt">[ 관리자 시스템 ]</span>
                    </p>
                    <p class="mb20 mt30">
                        <input type="text" name="adminId" id="adminId" class="inp" placeholder="아이디" autocomplete="off">
                    </p>
                    <p class="mb40">
                        <input type="password" name="adminPw" id="adminPw" class="inp" placeholder="비밀번호" autocomplete="off">
                    </p>
                </div>
            </form>

            <p class="mt30 mb40">
                <button id="login_submit_btn" class="btn-login" onclick="loginFormSubmit()">로그인</button>
            </p>
        </div>
    </div>

<c:import url="../inc/commonMngJS.jsp" charEncoding="UTF-8"/>

<script type="text/javascript">

    $.ajax({
        url: 'https://api.ip.pe.kr/json',
        method: 'get'
    }).done(function(api) {
        let ipAddress = api.ip;
        console.log(ipAddress);

        let jsonObj = {ip: ipAddress};

        $.ajax({
            url: '/mng/admin/access/selectIp.do',
            method: 'post',
            data: JSON.stringify(jsonObj),
            contentType: 'application/json; charset=utf-8' //server charset 확인 필요
        })
            .done(function (data, status){
                // console.log(status);
                // console.log(data);
                let pass = false;
                if(Number.parseInt(data.customValue) > 0) {
                    pass = true;
                }

                if(!pass){
                    alert('KIBS 관리자 시스템에 접근 권한이 없는 IP입니다.\n메인페이지로 이동합니다.');
                    window.location.href = '/';
                }
            })
            .fail(function(xhr, status, errorThrown) {
                $('body').html("오류가 발생했습니다.")
                    .append("<br>오류명: " + errorThrown)
                    .append("<br>상태: " + status);
            })
            .always(function() {
            });

        /*let ipArr = [
            '211.208.232.85' /!*킨텍스*!/
            ,'118.36.143.89' /!*미팅팬*!/
            ,'210.121.176.15'
            ,'61.80.16.77' /!*미팅팬*!/
            ,'175.113.156.128' /!*미팅팬2*!/
            ,'183.102.14.160' /!*미팅팬 작업자*!/
            ,'211.105.95.173' /!*킨텍스*!/
            ,'211.105.95.177'
            ,'211.105.95.162'
        ];*/
    });

    document.addEventListener("keyup", function(event) {
        if (event.key === 'Enter') {
            loginFormSubmit();
        }
    });
</script>

</body>

</html>