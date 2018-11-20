<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>利用停止該当予約確認</title>
</head>
<body>
<body>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">
</head>
<body class="body">

<div class="div">
<header class="header"><p>会議室・備品予約システム</p>
<input class="logintop" type="submit" value="ログアウト">
</header>

<!-- javascript警告ラベル始まり -->
<div id="JavascriptLabelBox">
<div id = "JavascriptErrorLabel">
Javascriptを有効にしてください
</div>
</div>
<script type="text/javascript" src="/ReservationSystemKAT-UNE/script/JavascriptErrorLabel.js">	</script>
<!-- javascript警告ラベル終わり -->

<div class="contents">
<h2>利用停止該当予約確認</h2>
<p><font color="red"><c:out value="${messageForSuspensionUseConfirm}"/></font></p>

<div class="dialog">
<p>利用停止期間を適用しようとしているリソースは既に予約されています</p>
<p>リソースの利用停止期間を適用すると以下の予約は強制的に削除されます。</p>
<p><font color="red">この操作は取り消せません。</font></p>
<br>
<div class="scroll3" style="width:800px;height:200px;margin:0 auto;">
<table>
<tbody>
					<c:forEach var="obj" items="${reservationListForSuspensionUseConfirm}" varStatus="status">
					<tr>
					<td><c:out value="${obj.usageDate }"/></td>
					<td><c:out value="${obj.usageStartTime }"/>～<c:out value="${obj.usageEndTime }"/></td>
					<td>予約者：<c:out value="${obj.reservedPerson.familyName}"/></td>
					<td>&npsp;<c:out value="${obj.reservedPerson.firstName}"/></td>
					<td>(<c:out value="${obj.reservedPerson.mailAddress}"/></td>
					<td><c:out value="${obj.reservedPerson.phoneNumber }"/>)</td>
					</tr>
					</c:forEach>
					</tbody>
</table>
</div>
</div>

<br>
<br>
<br>
<form action = "PushDeleteButtonOnSuspensionUseConfirm" method = "post">
<input type="checkbox" name="checkedConfirm" value="checkedConfirm"/>確認しました
<br>
<br>
<td>
<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit" type = "submit" value = "削除する"></form>

<form action = "resourceregist" method = "get">
<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit" type = "submit" value = "戻る"></form></td>

</div>
<br>
<br>
<br>
<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>
</body>
</html>