<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<div class="dialog">
<h2>リソース詳細</h2>
<p><font color = "blue">${Pmessage}</font></p>
</div>

<table class="table2">
<tbody>
<tr>
<td class="one" class="dialog"><b>リソース名</b></td>
<td class="right2"><c:out value="${resource.resourceName}" /></td>
</tr>
<tr>
<td class="dialog"><b>カテゴリ</b></td>
<td class="right2"><c:out value="${resource.category}" /></td>
</tr>
<tr>
<td class="dialog"><b>定員</b></td>
<c:choose>
	<c:when test="${resource.capacity!=0}">
	<td class="right2"><c:out value="${resource.capacity}" /></td>
	</c:when>
	<c:otherwise><td class="right2">×</td></c:otherwise>
</c:choose>
</tr>
<tr>
<td class="dialog"><b>事業所</b></td>
<td class="right2"><c:out value="${resource.officeName}" /></td>
</tr>
<tr>
<td class="dialog"><b>設備</b></td>
<td><div class="scroll3"><!-- ホワイトボード有<br>
						プロジェクタ有<br>来客優先<br>
						UCS常設<br>TV会議システム<br>
						OAボード -->
<c:forEach var="obj" items="${resource.facility}" varStatus="status">
<c:out value="${obj}" />
<br>
</c:forEach>
</div></td>
</tr>
<tr>
<td><b>利用停止開始日時</b></td>
<td class="right2" class="dialog"><c:out value="${stopStartDate}" /></td>
</tr>
<tr>
<td><b>利用停止終了日時</b></td>
<td class="right2" class="dialog"><c:out value="${stopEndDate}" /></td>
</tr>
<tr>
<td class="dialog"><b>補足</b></td>
<td class="right2"><div class="scroll2"><c:out value="${resource.supplement}" /></div></td>
</tr>
</tbody>
</table>
<br>

<c:if test="${authorityOfLoggedIn == 0}">
<c:if test="${resource.deleted == 0}">


<table class="table3">
<tr>
<td>
<form action="resourcechange" method="get">
<input type="hidden" name="resourceId" value = "<c:out value = "${resource.resourceId}"/>" >
<input type="hidden" name="type" value="change">
<input class="submit" class="dialog" type = "submit" value = "変更">
</form>
</td>
<td>　</td>
<td><form action = "/ReservationSystemKAT-UNE/reservesystem/pushDeleteButtonOnResourceDetails" method = "post">
<input type="hidden" name="resourceId" value = "<c:out value = "${resource.resourceId}"/>" >
<input class="submit" type = "submit" value = "削除"></form>
</td>
</tr>
</table>
</c:if>
</c:if>
<br>

<a class="dialog" href = "/ReservationSystemKAT-UNE/reservesystem/resourcelist" method="get">一覧に戻る</a>

</div>


<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

</body>
</html>