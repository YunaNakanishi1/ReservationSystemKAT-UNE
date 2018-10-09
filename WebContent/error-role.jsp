<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>システムエラー</title>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">
</head>
<body class="body">

<div class="div">
<header class="header"><p>会議室・備品予約システム</p>
<form action="/ReservationSystemKAT-UNE/logout" method="get">
<c:if test="${userId != null}">
<input class="logintop" type="submit" value="ログアウト">
</c:if>
</form>
</header>

<div class="contents">
<div class="dialog">
<br>
<br>
<p>システムエラーが発生しました。</p><p>
申し訳ございませんが、システム管理者にお問い合わせください。</p>

</div>
</div>

<div class="footer1"><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>
</body>
</html>