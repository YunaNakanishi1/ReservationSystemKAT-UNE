<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>削除確認ダイアログ画面</title>
</head>
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
<h2>リソース削除確認</h2>
<p><font color="red"><c:out value="${messageForResourceDeleteConfirm}"/></font></p>

<div class="dialog">
<p>削除しようとしているリソースは既に予約されています</p>
<p>リソースの削除を進めると以下の予約は強制的に削除されます。</p>
<p><font color="red">この操作は取り消せません。</font></p>
<br>
<p>【リソースを使用している予約】</p>
<div class="scroll3" style="width:800px;height:200px;margin:0 auto;">
<tbody>
					<c:forEach var="obj" items="${reservationListForResourceDeleteConfirm}" varStatus="status">
					<tr>
					<td><c:out value="${obj.usageDate }"/></td>
					<td><c:out value="${obj.usageStartTime }"/>～<c:out value="${obj.usageEndTime }"/></td>
					<td>予約者：<c:out value="${obj.reservedPerson.familyName}"/></td>
					<td>&nbsp;<c:out value="${obj.reservedPerson.firstName}"/></td>
					<td>(<c:out value="${obj.reservedPerson.mailAddress}"/></td>
					<td><c:out value="${obj.reservedPerson.phoneNumber }"/>)</td>
					</tr>
					</c:forEach>
					</tbody>

</div>
</div>

<br>
<br>
<br>
<form action = "pushDeleteButtonOnResourcedeleteConfirm" method = "post">
<input type="checkbox" name="checkedConfirm" value="checkedConfirm"/>確認しました
<br>
<br>
 <table class="table3">
<tr>
<td>
<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit" type = "submit" value = "削除する"></form>
</td>
<td>
<form action = "resourcedetails" method = "get">
<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit" type = "submit" value = "戻る"></form></td>
</tr>
</table>
</div>
<br>
<br>
<br>
<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>
</body>
</html>
</body>
</html>