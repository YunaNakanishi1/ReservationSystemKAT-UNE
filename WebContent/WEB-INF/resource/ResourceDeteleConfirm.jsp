<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="JavascriptErrorLabel.js">	</script>
<!-- javascript警告ラベル終わり -->

<div class="contents">
<h2>リソース削除確認</h2>
<p><font color="red">$<messageForResourceDeleteConfirm></font></p>

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
					<td><c:out value="${obj.usageStartTime }"/>～<c:out value="${obj.usageEndTime }"/></td>
					<td><c:out value="${obj.reservedPerson.familyName}"/></td>
					<td><c:out value="${obj.reservedPerson.firstName}"/></td>
					<td><c:out value="${obj.reserved}"/></td>
					<td><c:out value="${obj.reservedPerson.familyName }"/><c:out value="${obj.reservedPerson.firstName }"/></td>
					</tr>
					</c:forEach>
					</tbody>

</div>
</div>
<form>
<input type="checkbox" name = "checkedConfirm" value = "checkedConfirm" />確認しました
</form>
<br>
<br>

 <td><form action = "/deleteresource" method = "post">
<input type="hidden" name="resourceId" value = "<c:out value = "${obj.resourceId}"/>" >
<input class="submit" type = "submit" value = "削除する"></form>
</td>


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