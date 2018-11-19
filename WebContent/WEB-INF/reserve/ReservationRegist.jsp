<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>予約登録</title>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link type="text/css" rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.min.css" />
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/Slider.css">

	<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/ReservationSystemKAT-UNE/UserSearch.js">	</script>

</head>
<body class="body">
<div class="div">

<header class="header"><p>会議室・備品予約システム</p>
<form action="/ReservationSystemKAT-UNE/logout" method="get">
<input class="logintop" type="submit" value="ログアウト">
</form>
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
<h2>予約登録</h2>
<p><font color = "red"><!-- メッセージ --></font></p>

<table class="table5">
<tbody>
<form action="/ReservationSystemKAT-UNE/reservesystem/registreserve" method="post">
<tr>
<td class="one" class="dialog"><b>　　　　　　リソース名</b></td>
<td class="right2">
<a href="/ReservationSystemKAT-UNE/reservesystem/showresourcedetailstab?resourceId=${resourceIdForReservationRegist}" target="_blank">
<c:out value="${resourceNameForReservationRegist}"></c:out></a></td>
<input type = "hidden"  name = "resourceId" value="${resourceIdForReservationRegist }">
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用日</b></td>
<td class="right2">
<fmt:parseDate var="date" value="${usageDateForReservationRegist }" type="DATE" dateStyle="LONG" />
<fmt:formatDate value="${date}" type="DATE" dateStyle="FULL" />
</td>

</tr>

<tr>
<td class="dialog"><b>　　　　　　利用時間<b><a class="red">※</a></b></td>
<td class="right2">

<input type="hidden" id ="usageStartTimeForReservationRegist" value="${usageStartTimeForReservationRegist.timeMinutesValue}"/>
<input type="hidden" id ="usableEndTimeForReservationRegist" value="${usableEndTimeForReservationRegist.timeMinutesValue}"/>
<input type="hidden" id ="usageTimeForReservationSelect" value="${usageTimeForResourceSelect.timeMinutesValue}"/>


<div id="slider-area">
        <div style="float:left;width:100px;margin-left:0px;">
            <input type="text" id="slider-left-timelabel" class="timelabel"  readonly="readonly" style="text-align: left;width:100%"/>
        </div>
        <div style="width:270px;float:left;text-align:center;">
            <input type="text" id="slider-center-timelabel"  class="timelabel" readonly="readonly" style="text-align: center;width:100%"/>
        </div>
        <div style="float:right;width:100px;margin-right:25px;">
            <input type="text" id="slider-right-timelabel"  class="timelabel" readonly="readonly" style="text-align: right;width:100%"/>
        </div>
        <br>
        <div id="slider-box">
        <div id="slider"></div>
        </div>
        <div id="minus15" style="float:left;width:100px;margin-left:0px;">
        <input class="btn" type = "button" value = "-15分">
		</div>

        <div style="width:270px;float:left;text-align:center;">
        <input type="text" id="slider-timelabel" class="timelabel" readonly="readonly" />
        </div>
        <div id="plus15" style="float:right;width:100px;margin-right:25px;">
			<input class="btn" type = "button" value = "+15分">
        </div>
</div>
	<input type = "hidden" name = "usageStartTime" id = "usageStartTime" value = "">
    <input type = "hidden" name = "usageEndTime" id = "usageEndTime" value = "">
</td>
</tr>
<tr>
<td class="dialog"><b>　　　　　　予約名称</b></td>
<td class="right2">
<input type="text" name="reservationName" placeholder="名称なし" value="<c:out value="${reservationNameForReservationRegist }" />"> (30文字以内)
 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用人数</b></td>
<td class="right2">
<input type="text" name="numberOfParticipants" placeholder="半角数字のみ" value="<c:out value="${displayNumberOfParticipantsForReservationRegist }"/>" > 名

 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　予約者</b></td>

<td class="right2">
<span class="reserve_name"><c:out value="${familyNameOfLoggedIn }"/> <c:out value="${firstNameOfLoggedIn }"/></span>
 </td>
</tr>

<tr id="reserve_myself">
</tr>

<tr>
<td class="dialog"><b>　　　　　　共同予約者</b></td>

<td class="right2">
<span class="reserve_name">----<!-- ---- --></span> <input class="button" type = "submit" onclick="addSearch('reserve_pare')" value = "変更"> <input class="button" type = "submit" value = "クリア">
</td>
</tr>

<tr id="reserve_pare">
</tr>

<tr>
<td class="dialog"><b>　　　　　　参加者種別</b></td>

<td class="right2">
<select name ="attendanceTypeId">
<option value="">なし</option>
<c:forEach var="obj" items="${attendanceTypeListForReservationRegist}" varStatus="status">
<option value="<c:out value="${obj.attendanceTypeId }"/>"
<c:if test="${obj.attendanceTypeId == attendanceTypeIdForReservationRegist }">
selected
</c:if>
>
<c:out value="${obj.attendanceTypeName }"/>
</option>
</c:forEach>
</select> </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　補足</b></td>

<td class="right2">
<textarea id="supplementArea" class="scroll2" name="supplement"onkeyup="ShowLength();" <c:out value="${reserveSupplementForReservationRegist }"/> ><c:out value="${reserveSupplementForReservationRegist }"/></textarea><span id="inputlength">0/500</span>
</td>
</tr>

</tbody>
</table>

<br>
<table class="table3">
<tr>

<td>
<input class="submit" class="dialog" type = "submit" value = "登録">
</td>
</form>
<td>　</td>

<td>
<form action = "/ReservationSystemKAT-UNE/reservesystem/searchResourceList" method = "post">
<input class="submit" type = "submit" value = "リソース選択画面へ">
</form>
</td>

</tr>
</table>
<br>
<form method="post" name="form1" action="/ReservationSystemKAT-UNE/reservesystem/showfirstreservationlist">
    <a href="javascript:form1.submit()">予約一覧へ</a>
</form>
<br>
<br>
<br>
</div>
<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>
<script type="text/javascript" src="/ReservationSystemKAT-UNE/Slider.js">	</script>
<script>
window.onload = function() {
	ShowLength();
}
function ShowLength() {
	   document.getElementById("inputlength").innerHTML = document.getElementById("supplementArea").value.length+"/500";
	}
</script>
    </body>
</html>