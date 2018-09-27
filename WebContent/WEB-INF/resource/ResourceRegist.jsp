<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>リソース入力</title>
<link rel="stylesheet" href="deco.css">
<link rel="stylesheet" href="header_footer.css">
</head>
<body class="body">
<div class="div">
<header class="header"><p>会議室・備品予約システム</p>
<input class="logintop" type="submit" value="ログアウト">
</header>
<div class="contents">
<div class="dialog">
<h2>リソース入力</h2>
<p><font color = "blue">${Pmessage}</font></p>
</div>

<table class="table2">
<tbody>
<form action="resourseregist" method="post">

<tr>
<td class="one" class="dialog"><b>リソース名</b></td>

<td class="right2">
<input type="text" name="resourceName"
<c:if test="${checkDisplay}">
 value=" <c:out value="${resourceName}" /> "
 </c:if>
 >
 </td>
</tr>
<tr>
<td class="dialog"><b>カテゴリ</b></td>
<td class="right2">
<select name ="category">
<c:forEach var="obj" items="${categoryList}" varStatus="status">

<option value="${obj}"
<c:if test="${checkDisplay && category == obj}">
 selected
</c:if>
>
<c:out value="${obj}"/></option>
</c:forEach>
</select>
 </td>
</tr>
<tr>
<td class="dialog"><b>定員</b></td>
<td class="right2">
<input type="text" name="capacity"
<c:if test="${checkDisplay}">
 value=" <c:out value="${capacity}" /> "
 </c:if>
 >
</td>
</tr>
<tr>
<td class="dialog"><b>事業所</b></td>
<td class="right2">
<select name ="officeName">
<c:forEach var="obj" items="${officeList}" varStatus="status">
<option value="<${obj}"
<c:if test="${checkDisplay && officeName == obj}">
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
<c:if test ="${checkDisplay && selectedFacility[status.index]}">
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
<input type="text" name="stopStartDay"
<c:if test ="${checkDisplay && stopStartDay!= null}">
value="${stopStartDay}"
</c:if>
>
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
<c:if test="${checkDisplay && stopStartHour == 0 + status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="23"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${checkDisplay && stopStartHour == status.index }">
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
<c:if test="${checkDisplay && stopStartMinute == status.index+'0' }">
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
<input type="text" name="stopEndDay"
<c:if test ="${checkDisplay && stopEndDay!= null}">
value="${stopEndDay}"
</c:if>
>
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
<c:if test="${checkDisplay && stopEndHour =='0'+status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="23"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${checkDisplay && stopEndHour ==status.index }">
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
<c:if test="${checkDisplay && stopEndMinute ==status.index +'0'}">
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
<td class="dialog"><b>詳細</b></td>
<<<<<<< HEAD
<td class="right2">
<textarea class="scroll2" name="supplement">
<c:if test="${hasResourceData && resource.supplement != null}">
=======
<td><div class="scroll2">
<textarea name="supplement">
<c:if test="${checkDisplay && resource.supplement != null}">
>>>>>>> branch 'develop' of https://github.com/YunaNakanishi1/ReservationSystemKAT-UNE.git
<c:out value="${resource.supplement}" />
</c:if>
</textarea>
</div></td>
</tr>
</tbody>
</table>
<br>


<table class="table3">
<tr>
<td><input class="submit" class="dialog" type = "submit" value = "登録"></td>
</form>
<td>　</td>
<td><form action = "${returnPage}" method = "post">
<input type="hidden" name="resourceId" value = "<c:out value = "${resource.resourceId}"/>" >
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