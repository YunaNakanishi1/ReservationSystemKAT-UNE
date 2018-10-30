<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>今すぐ予約</title>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet"
	href="/ReservationSystemKAT-UNE/header_footer.css">
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="pulldownControll.js">

</script>

</head>
<body class="body" onload="initChange();">
	<div class="div">

		<header class="header">
			<p>会議室・備品予約システム</p>
			<form action="/ReservationSystemKAT-UNE/logout" method="get">
				<input class="logintop" type="submit" value="ログアウト">
			</form>
		</header>
		<!-- javascript警告ラベル始まり -->
		<div id="JavascriptLabelBox">
			<div id="JavascriptErrorLabel">Javascriptを有効にしてください</div>
		</div>
		<script type="text/javascript" src="JavascriptErrorLabel.js">

		</script>
		<!-- javascript警告ラベル終わり -->
		<div class="contents">
			<div class="dialog">
				<h2>今すぐ予約</h2>
				<p>
					<font color="red"><!--メッセージ  --></font>
				</p>
			</div>

			<table class="table2" style="width:790px">
				<tbody>

					<tr>
						<td class="one" class="dialog"><b>　　　事業所</b></td>
						<td class="right2"><select name="category">
								<option value="aaa" selected>全て</option>
								<option value="aaa">晴海</option>
								<option value="aaa">新横浜</option>
						</select></td>
					</tr>
					<tr>
						<td class="dialog"><b>　　　カテゴリ</b></td>
						<td class="right2"><select name="category">
								<option value="aaa" selected>全て</option>
								<option value="aaa">会議室</option>
								<option value="aaa">UCS</option>
						</select></td>
					</tr>
					<tr>
						<td class="dialog"><b>　　　定員</b></td>
						<td class="right2">(※定員がないものは0人で登録されています)<br> <input
							type="text" name="capacity" placeholder="半角数字のみ">人以上
						</td>
					</tr>

					<tr>
						<td class="dialog"><b>　　　利用時間</b></td>
						<td class="right2">09:45 ～ <select name="QuickStartHour"
							id="QuickStartHour"
							onchange="hourChange('QuickStartHour','QuickStartMinute')">
								<c:forEach begin="0" end="9" varStatus="status">
									<option value="0<c:out value="${status.index}"/>"
										<c:if test="${hasResourceData && stopStartHour == 0 + status.index }">
selected
</c:if>>
										0
										<c:out value="${status.index}" />
									</option>
								</c:forEach>
								<c:forEach begin="10" end="24" varStatus="status">
									<option value="<c:out value="${status.index}"/>"
										<c:if test="${hasResourceData && stopStartHour == status.index }">
selected
</c:if>>
										<c:out value="${status.index}" />
									</option>
								</c:forEach>
						</select> : <select name="QuickStartMinute" id="QuickStartMinute"">
								<option value="aaa">00</option>
								<!-- <option value="aaa">15</option>
								<option value="aaa">30</option>
								<option value="aaa">45</option> -->
						</select>
						</td>
					</tr>


				</tbody>
			</table>
			<br>


			<table class="table3">
				<tr>
					<td>
						<form action="setresource" method="post">
							<input class="submit" class="dialog" type="submit" value="検索">
					</td>
					</form>
					<td></td>
					<td><form action="${returnPage}" method="get">
							<input type="hidden" name="resourceId"
								value="<c:out value = "${resourceId}"/>"> <input
								class="submit" type="submit" value="戻る">
						</form></td>
				</tr>
			</table>
			<br>

		</div>


		<div class="footer1" class=>
			<footer class="fotter2">copyright🄫KAT-UNE</footer>
		</div>
	</div>

</body>
</html>