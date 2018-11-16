<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	<script type="text/javascript" src="/ReservationSystemKAT-UNE/Slider.js">	</script>
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
<script type="text/javascript" src="/ReservationSystemKAT-UNE/JavascriptErrorLabel.js">	</script>
<!-- javascript警告ラベル終わり -->

<div class="contents">
<h2>予約登録</h2>
<p><font color = "red"><!-- メッセージ --></font></p>

<table class="table5">
<tbody>

<tr>
<td class="one" class="dialog"><b>　　　　　　リソース名</b></td>
<td class="right2">
<a href="/ReservationSystemKAT-UNE/reservesystem/showresourcedetailstab?reservationIdForReservationDetails=${resourceIdForReservationRegist}">
<c:out value="${resourceNameForReservationRegist}"></c:out></a></td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用日</b></td>
<td class="right2"><c:out value="${usageDateForReservationRegist}"></c:out></td>

</tr>

<tr>
<td class="dialog"><b>　　　　　　利用時間<b><a class="red">※</a></b></td>
<td class="right2">

<input type="hidden" id ="usageStartTimeForReservationRegist" value="${usageStartTimeForReservationRegist.timeMinutesValue}">
<input type="hidden" id ="usageEndTimeForReservationRegist" value="${usageEndTimeForReservationRegist.timeMinutesValue}">


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
        <div style="float:left;width:100px;margin-left:0px;">
        <input class="btn" type = "submit" value = "-15分">
		</div>

        <div style="width:270px;float:left;text-align:center;">
        <input type="text" id="slider-timelabel" class="timelabel" readonly="readonly" />
        </div>
        <div style="float:right;width:100px;margin-right:25px;">
			<input class="btn" type = "submit" value = "+15分">
        </div>
</div>

</td>
</tr>
<tr>
<td class="dialog"><b>　　　　　　予約名称</b></td>
<td class="right2">
<input type="text" name="meetingName" placeholder="名称なし"> (30文字以内)
 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用人数</b></td>
<td class="right2">
<input type="text" name="usagePeople" placeholder="半角数字のみ"> 名
 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　予約者</b></td>

<td class="right2">
<span class="reserve_name">理工 太郎</span>
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
<select name ="sankasyasyubetu">
<option>なし</option>
<option value="1">社内会議</option>
<option value="2">RG内会議</option>
<option value="3">RG外会議</option>

</select> </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　補足</b></td>

<td class="right2">
<textarea class="scroll2" name="supplement"></textarea>0/500
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
<td>　</td>

<td>
<form action = "" method = "get">
<input class="submit" type = "submit" value = "リソース選択画面へ">
</form>
</td>

</tr>
</table>
<br>
<a href="..">予約一覧へ</a>
<br>
<br>
<br>
</div>
<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

    </body>
</html>