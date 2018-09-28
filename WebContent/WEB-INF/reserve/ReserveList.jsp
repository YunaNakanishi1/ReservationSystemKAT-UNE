<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="false" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>リソース詳細</title>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">
</head>
<body class="body">

<div class="div">
<header class="header"><p>会議室・備品予約システム</p>
<form action="/ReservationSystemKAT-UNE/logout" method="get">
<input class="logintop" type="submit" value="ログアウト">
</form>
</header>

<div class="contents">
<h2>ダミーページ（予約一覧）</h2>
<form action="/ReservationSystemKAT-UNE/reservesystem/resourcelist" method="get">
<input type="submit" name="reserveList" value="リソース一覧">
</form>
</div>


<div class="footer1"><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

</body>
</html>