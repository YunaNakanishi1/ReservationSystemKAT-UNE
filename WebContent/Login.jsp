<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">
</head>


<body class="body1" background="/ReservationSystemKAT-UNE/css/kaigi.jpg">

<div id="div">
<header class="header"><p>会議室・備品予約システム</p>
<input class="logintop" type="submit" value="">
</header>


<div class="contents1">
<div class="haikei">
<p class="error"><c:out value ="${Emessage}"/></p>
<form action="login" method="get">
<table class="table">
<tr><td>ID:</td><td><input type="text" name="userId" value = "<c:out value = "${requestScope.userId}"/>"></td></tr>
<tr><td>パスワード:</td><td><input type="password" name="password"></td></tr>
</table>
<p><input class="submit" type="submit" value="ログイン"></p>
</form>
</div>


<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

<br>
</div>


<div class="footer1"><footer class="fotter2">copyright🄫KAT-UNE</footer></div>

</div>


</body>
</html>