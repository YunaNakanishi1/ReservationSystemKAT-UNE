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
<!--
<header class="header"><p>会議室・備品予約システム</p>
<form action="/ReservationSystemKAT-UNE/logout" method="get">
<input class="logintop" type="submit" value="ログアウト">
</form>
</header>
-->

<div class="contents">
<div class="dialog">
<h2>リソース詳細</h2>

</div>

<table class="table2">
<tbody>
<tr>
<td class="one" class="dialog"><b>リソース名</b></td>
<td class="right2"><c:out value="${resourceDTOForResourceDetailsTab.resourceName}" /></td>
</tr>
<tr>
<td class="dialog"><b>カテゴリ</b></td>
<td class="right2"><c:out value="${resourceDTOForResourceDetailsTab.category}" /></td>
</tr>
<tr>
<td class="dialog"><b>定員</b></td>
<c:choose>
	<c:when test="${resourceDTOForResourceDetailsTab.capacity!=0}">
	<td class="right2"><c:out value="${resourceDTOForResourceDetailsTab.capacity}" /></td>
	</c:when>
	<c:otherwise><td class="right2">×</td></c:otherwise>
</c:choose>
</tr>
<tr>
<td class="dialog"><b>事業所</b></td>
<td class="right2"><c:out value="${resourceDTOForResourceDetailsTab.officeName}" /></td>
</tr>
<tr>
<td class="dialog"><b>設備</b></td>
<td><div class="scroll3"><!-- ホワイトボード有<br>
						プロジェクタ有<br>来客優先<br>
						UCS常設<br>TV会議システム<br>
						OAボード -->
<c:forEach var="obj" items="${resourceDTOForResourceDetailsTab.facility}" varStatus="status">
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
<td class="right2"><div class="scroll2"><c:out value="${resourceDTOForResourceDetailsTab.supplement}" /></div></td>
</tr>
</tbody>
</table>

</div>


<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

</body>
</html>