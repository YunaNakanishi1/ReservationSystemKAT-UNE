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
					<!-- nullの時全てってやつ -->
						<td class="one" class="dialog"><b>　　　事業所</b></td>
						<td class="right2">
						<select name ="office">
							<option value="全て"
								<c:if test="${obj==null}">
								 selected
								</c:if>>全て
							</option>

							<c:forEach var="obj" items="${officeListForResourceSelect}" varStatus="status">
							<option value="${obj.officeId}"
							<c:if test="${obj.officeId==officeIdForResourceSelect}">
							 selected
							</c:if>>
							<c:out value="${obj.officeName}"/></option>
							</c:forEach>
						</select>
					</td>
					</tr>
					<tr>
						<td class="dialog"><b>　　　カテゴリ</b></td>
						<td class="right2"><select name ="category">
							<option value="全て"
								<c:if test="${obj==null}">
								 selected
								</c:if>>全て
							</option>

							<c:forEach var="obj" items="${categoryListForResourceSelect}" varStatus="status">
							<option value="${obj.categoryId}"
							<c:if test="${obj.categoryId==categoryIdForResourceSelect}">
							 selected
							</c:if>>
							<c:out value="${obj.categoryName}"/></option>
							</c:forEach>
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
						<td class="right2">
						<c:out value = "${usageStartTimeForResourceSelect.hour}"/>:<c:out value = "${usageStartTimeForResourceSelect.minutes}"/>
						 ～
						<!--  09:45 ～-->

						<select name = "usageEndHour" id = "usageEndHour"  onchange="hourChange('usageEndHour','usageEndMinute')">
						<!--
						<select name="usageEndHourForResourceSelect"
							id="usageEndHourForResourceSelect"
							onchange="hourChange('usageEndHourForResourceSelect','QuickStartMinute')">
							 -->

								<c:forEach begin="0" end="9" varStatus="status">
									<option value="0<c:out value="${status.index}"/>"
										<c:if test="${usageEndHourForResourceSelect == 0 + status.index }">
selected
</c:if>>

										0<c:out value="${status.index}" />
									</option>
								</c:forEach>

								<c:forEach begin="10" end="24" varStatus="status">
									<option value="<c:out value="${status.index}"/>"
										<c:if test="${usageEndTimeForResourceSelect.hour == status.index }">
selected
</c:if><c:if test = "${status.index==10 }">selected</c:if>>

										<c:out value="${status.index}" />
									</option>
								</c:forEach>
						</select> :

	<select name = "usageEndMinute" id = "usageEndMinute"  onchange="hourChange('usageEndHour','usageEndMinute')">
						<!-- <select name="usageEndMinutesForResourceSelect" id="QuickEndMinute"> -->

				<option value="00"
				>00</option>
				<c:forEach begin="1" end="3" varStatus="status">
				<option value="<c:out value="${status.index * 15 }"/>"
				<c:if test="${status.index * 15 == usageEndTimeForResourceSelect.minutes }">
				selected
				</c:if>
				><c:out value="${status.index * 15 }"/>
				</option>
				</c:forEach>


						</select>

						</td>
					</tr>


				</tbody>
			</table>
			<br>


			<table class="table3">
				<tr>
					<td>
					<form action="/ReservationSystemKAT-UNE/reservesystem/pushSearchButtonOnQuickReservation" method="post">
							<input class="submit" class="dialog" type="submit" value="検索">
					</form>
					</td>
					<td></td>
					<td><form action="/ReservationSystemKAT-UNE/reservesystem/showfirstreservationlist" method="post">
							<input class="submit" type="submit" value="戻る">
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

