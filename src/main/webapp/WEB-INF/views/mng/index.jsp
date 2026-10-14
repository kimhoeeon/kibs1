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
    <title>2027 경기국제보트쇼 관리자</title>

    <link href="/css/reset.css" rel="stylesheet">
    <link href="/css/mngStyle.css" rel="stylesheet">

    <%-- favicon --%>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon" sizes="16X16" />
</head>

    <script type="text/javascript">
        window.history.forward();
        function noBack(){window.history.forward();}

        // --- 엔터키 입력 감지 및 폼 전송 이벤트 리스너 ---
        document.addEventListener("DOMContentLoaded", function() {
            var adminIdInput = document.getElementById("adminId");
            var adminPwInput = document.getElementById("adminPw");

            // 아이디 입력 필드에서 엔터키 입력 시
            if(adminIdInput) {
                adminIdInput.addEventListener("keydown", function(event) {
                    if (event.keyCode === 13) {
                        event.preventDefault(); // 폼 기본 제출 방지
                        loginFormSubmit();
                    }
                });
            }

            // 비밀번호 입력 필드에서 엔터키 입력 시
            if(adminPwInput) {
                adminPwInput.addEventListener("keydown", function(event) {
                    if (event.keyCode === 13) {
                        event.preventDefault(); // 폼 기본 제출 방지
                        loginFormSubmit();
                    }
                });
            }
        });
        // ----------------------------------------------------
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

</body>

</html>