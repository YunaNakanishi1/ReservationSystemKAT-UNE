<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>リソース入力</title>
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
<link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">
  <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>

</head>
<body class="body">
<div class="div">
<script>
    $(function () {
      $("#clear").click( function() {
        //利用停止開始日時の入力欄をクリアする
        $("#stopStartDay").val("");
      });
    });

    $(function () {
        $("#clear2").click( function() {
          //利用停止終了日時の入力欄をクリアする
          $("#stopEndDay").val("");
        });
      });
</script>
<header class="header"><p>会議室・備品予約システム</p>
<form action="/ReservationSystemKAT-UNE/logout" method="get">
<input class="logintop" type="submit" value="ログアウト">
</form>
</header>
<div class="contents">
<div class="dialog">
<h2>リソース入力</h2>
<p><font color = "blue">${Pmessage}</font></p>
<p><font color = "red">${Emessage}</font></p>
</div>

<table class="table2">
<tbody>
<form action="PushRegistButtonOnResourceRegist" method="post">

<tr>
<td class="one" class="dialog"><b>リソース名</b><a class="red">　※</a></td>

<td class="right2">
<input type="text" name="resourceName"
<c:if test="${hasResourceData}">
 value="<c:out value="${resourceName}" />"
 </c:if>
 >
 </td>
</tr>
<tr>
<td class="dialog"><b>カテゴリ</b><a class="red">　※</a></td>
<td class="right2">
<select name ="category">
<c:forEach var="obj" items="${categoryList}" varStatus="status">

<option value="${obj}"
<c:if test="${hasResourceData && obj==category}">
 selected
</c:if>
>
<c:out value="${obj}"/></option>
</c:forEach>
</select>
 </td>
</tr>
<tr>
<td class="dialog"><b>定員</b><a class="red">　※</a></td>
<td class="right2">
<input type="text" name="capacity"
<c:if test="${hasResourceData}">
 value="<c:out value="${capacity}" />"
 </c:if>
 >
</td>
</tr>
<tr>
<td></td>
<td class="right2">
※定員がないものは0と入力
</td>
</tr>
<tr>
<td class="dialog"><b>事業所</b><a class="red">　※</a></td>
<td class="right2">
<select name ="officeName">
<c:forEach var="obj" items="${officeList}" varStatus="status">
<option value="${obj}"
<c:if test="${hasResourceData && obj==officeName}">
 selected
</c:if>
>
<c:out value="${obj}"/></option>
</c:forEach>
</select>
</td>
</tr>
<tr>
<td class="dialog"><b>設備</b></td>
<td><div class="scroll3">
<c:forEach var="obj" items="${facilityList}" varStatus="status">
<input type="checkbox" name = "facility" value = <c:out value="${obj}" />
<c:if test ="${hasResourceData && selectedFacility[status.index]}">
checked
</c:if>
>
<c:out value="${obj}" />
<br>
</c:forEach>
</div></td>
</tr>
<tr>
<td class="dialog"><b>利用停止開始日時</b></td>
<td class="right2">
<input type="text" id="stopStartDay" name="stopStartDay" placeholder="2018/01/01"
<c:if test ="${hasResourceData && stopStartDay!= null}">
value = "<c:out value="${stopStartDay}" />"
</c:if>
>

<a class="red" id="clear">
×
</a>
</td>
</tr>
<tr>
<td></td>
<td class="right2">
※年は省略可
</td>
</tr>
<tr>

<td><b>利用停止開始時間</b></td>
<td class="right2">
<select name = "stopStartHour">
<c:forEach begin="0" end="9"  varStatus="status">
<option value= "0<c:out value="${status.index}"/>"
<c:if test="${hasResourceData && stopStartHour == 0 + status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="23"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${hasResourceData && stopStartHour == status.index }">
selected
</c:if>
>
<c:out value="${status.index}"/>
</option>
</c:forEach>
</select>
時
<select name = "stopStartMinute">
<c:forEach begin="0" end="5" varStatus="status">
<option value="<c:out value="${status.index}" />0"
<c:if test="${hasResourceData && stopStartMinute == (status.index*10) }">
selected
</c:if>
>
<c:out value="${status.index}" />0
</option>
</c:forEach>
</select>
分
</td>




</tr>
<tr>
<td class="dialog"><b>利用停止終了日時</b></td>
<td class="right2">
<input type="text" id="stopEndDay" name="stopEndDay" placeholder="2018/01/01"
<c:if test ="${hasResourceData && stopEndDay!= null}">
value="<c:out value="${stopEndDay}" />"
</c:if>
>
<a class="red" id="clear2">
×
</a>
</td>
</tr>

<tr>
<td></td>
<td class="right2">
※年は省略可
</td>
</tr>
<tr>

<td><b>利用停止終了時間</b></td>
<td class="right2">
<select name = "stopEndHour">
<c:forEach begin="0" end="9"  varStatus="status">
<option value= "0<c:out value="${status.index}"/>"
<c:if test="${hasResourceData && stopEndHour =='0'+status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="23"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${hasResourceData && stopEndHour ==status.index }">
selected
</c:if>
>
<c:out value="${status.index}"/>
</option>
</c:forEach>
</select>
時
<select name = "stopEndMinute">
<c:forEach begin="0" end="5" varStatus="status">
<option value="<c:out value="${status.index}" />0"
<c:if test="${hasResourceData && stopEndMinute ==status.index *10}">
selected
</c:if>
>
<c:out value="${status.index}" />0
</option>
</c:forEach>
</select>
分
</td>

</tr>


<tr>
<td class="dialog"><b>補足</b></td>

<td class="right2">
<textarea class="scroll2" name="supplement"><c:if test="${hasResourceData && supplement != null}"><c:out value="${supplement}" /></c:if></textarea>
</div></td>
</tr>
</tbody>
</table>
<br>


<table class="table3">
<tr>
<td>
<input type="hidden" name = "type" value ="<c:out value="${type}"/>">
<c:if test="${hasResourceData&&resourceId!=null }">
<input type = "hidden" name="resourceId" value="<c:out value="${resourceId }" />">
</c:if>
<input class="submit" class="dialog" type = "submit" value = "登録"></td>
</form>
<td>　</td>
<td><form action = "${returnPage}" method = "get">
<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit" type = "submit" value = "戻る"></form>
</td>
</tr>
</table>
<br>

</div>


<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

</body>
</html>