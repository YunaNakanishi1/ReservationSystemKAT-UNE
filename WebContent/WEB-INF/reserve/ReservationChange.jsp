<%@page import="dto.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>予約変更</title>
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

<script>

function hyoji(notView)
{
  if (notView)
  {
    document.getElementById("reserve_pare").style.display="none";
  }
  else
  {
    document.getElementById("reserve_pare").style.display="";
  }
}
window.onload= function() {
	  hyoji(true);
}
</script>

<div class="contents">
<h2>予約変更</h2>

<p><font color="red"><c:out value = "${messageForReservationChange}"/></font></p>
<form action = "/ReservationSystemKAT-UNE/reservesystem/changereservation" method = "post">


<table class="table5">
<tbody>

<tr>
<td class="one" class="dialog"><b>　　　　　　リソース名</b></td>
<td class="right2">
<c:out value =  "${reservationDTOForReservationChange.resource.resourceName}"/>
 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用日</b></td>
<td class="right2">
<fmt:parseDate var="date" value="${reservationDTOForReservationChange.usageDate }" type="DATE" dateStyle="LONG" />
<fmt:formatDate value="${date}" type="DATE" dateStyle="FULL" />

 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用時間<b><a class="red">※</a></b></td>
<td class="right2">

<input type="hidden" name="usageStartTime" id ="usageStartTime" value="${usableStartTimeForReservationChange.timeMinutesValue}"/>
<input type="hidden" name="usageEndTime" id ="usableEndTime" value="${usableEndTimeForReservationChange.timeMinutesValue}"/>
<input type="hidden" id ="usageTime" value="${usageEndTimeForReservationChange}"/>
<input type="hidden" id ="usableStartTime" value="${usageStartTimeForReservationChange.timeMinutesValue}"/>


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
<!--
    <input type = "hidden" name = "usageStartTime" id = "usageStartTime" value = "">
    <input type = "hidden" name = "usageEndTime" id = "usageEndTime" value = "">
 -->
</td>
</tr>
<tr>
<td class="dialog"><b>　　　　　　予約名称</b></td>
<td class="right2">
<input type="text" name="reservationName" placeholder="名称なし"
value = "<c:out value="${reservationNameForReservationChange}" />"
> (30文字以内)
 </td>
</tr>

<tr>
<td class="dialog"><b>　　　　　　利用人数</b></td>
<td class="right2">
<input type="text" name="numberOfParticipants" placeholder="半角数字のみ"
value = "<c:out value="${numberOfParticipantsForReservationChange}" />"
> 名
 </td>
</tr>

<tr>
<td 　　　　　　class="dialog"><b>　　　　　　予約者</b></td>

<td class="right2">
<span class="reserve_name"><c:out value =  "${reservationDTOForReservationChange.reservedPerson.familyName}"/>　<c:out value =  "${reservationDTOForReservationChange.reservedPerson.firstName}"/></span>
 </td>
</tr>

<tr id="reserve_myself">
</tr>

<tr>
<td class="dialog"><b>　　　　　　共同予約者</b></td>

<td class="right2">
<!-- IDしか持ってこれないよー -->
<span class="reserve_name">
<input type="text" value="${coReservedPersonNameForReservationChange}" id="co-reserved-person-name" readonly="readonly"/>
</span>
<input class="button" type = "button" onclick="hyoji(false)" value = "変更">
<input class="button" type = "button" onclick="selectClearButton()" value = "クリア">
<input type="hidden" id ="coReservedPersonId" value="${coReservedPersonNameForReservationChange}"/>

 </td>
</tr>

<tr id="reserve_pare">
<td class="one" class="dialog"></td>
    <td class="right2">
    <hr>
    <input type="text" name=""> <input class="button" type = "button" value = "検索">
    <br>
    <select id="userList" size="5" style="width:200px">
    </select>

    <br>
    <input class="button" type = "button" onclick="selectUserButton();hyoji(true)" value = "選択">
    <input class="button" type = "button" onclick="hyoji(true)" value = "閉じる">
    <hr>
     </td>
</tr>

<script type="text/javascript">
		var selectColumn = document.getElementById('userList');

		<%
		List<User> userList = (List<User>)request.getAttribute("userListForReservationChange");
		int userListLength = userList.size();
		String output = "";


		for(int i=0;i<userListLength;i++){
		    User user = userList.get(i);
		    output += "<option value='"+user.getUserId()+"'>"+user.getFamilyName()+"　"+user.getFirstName()+"("+user.getUserId()+")</option>";
		    }
		%>
    	selectColumn.innerHTML ="<%=output%>";

    	function selectUserButton(){
    		var coUserName = document.getElementById('co-reserved-person-name');
    		var coUserId = document.getElementById('coReservedPersonId');

    		var selectedUser = document.getElementById('userList');

    		var idx = selectedUser.selectedIndex;       //インデックス番号を取得
    		var val = selectedUser.options[idx].value;  //value値を取得
    		var txt  = selectedUser.options[idx].text;  //ラベルを取得

			coUserName.value =txt.split("(")[0];
			coUserId.value = val;
    	}

    	function selectClearButton(){
    		var coUserName = document.getElementById('co-reserved-person-name');
    		var coUserId = document.getElementById('coReservedPersonId');

			coUserName.value ="-----";
			coUserId.value = "";
    	}

</script>

<tr>
<td class="dialog"><b>　　　　　　参加者種別</b></td>

<td class="right2">
<select name ="attendanceTypeId">
<option value = "">なし
</option>
<c:forEach var="obj" items="${attendanceTypeListForReservationChange}" varStatus="status">
<option value="${obj.attendanceTypeId}"
<c:if test="${obj.attendanceTypeId==attendanceTypeIdForReservationChange}">
 selected
</c:if>>
<c:out value="${obj.attendanceTypeName}"/></option>
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


<table class="table3">
<tr>

<br>
<td>
<input type="hidden" name="reserveId" value="${reservationDTOForReservationChange.reservationId}">
<input class="submit" class="dialog" type = "submit" value = "変更">
</td>
<td>
</form>
　
</td>
<td>
<form action = "/ReservationSystemKAT-UNE/reservesystem/showReservationDetails" method = "post">
<input class="submit" class="dialog" type = "submit" value = "戻る">
</form>
</td>
</tr>
</table>


<br>
</div>
<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

<script type="text/javascript" src="/ReservationSystemKAT-UNE/Slider.js"></script>
</body>
</html>