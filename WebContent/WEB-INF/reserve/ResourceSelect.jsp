<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>リソース選択</title>

<link rel="stylesheet"
	href="/ReservationSystemKAT-UNE/header_footer.css">

<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">

<link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>

<script
	src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>

<script>
$(document).ready(function(){
	  $('#design-table').dataTable( {
	    "bPaginate": true,
	    "bLengthChange": false,
	    "bFilter": false,
	    "bSort": false,
	    "bInfo": true,
	    "bAutoWidth": true,
		});

	  hyoji1();
	  });

</script>


<script>

flag=true;
function hyoji1()
{
  if (flag)
  {
    document.getElementById("disp").style.display="none";
  }
  else
  {
    document.getElementById("disp").style.display="block";
  }
  flag = !flag;
}

</script>
<script>
    $(function () {
      $("#clear").click( function() {
        //利用日の入力欄をクリアする
        $("#usageDate").val("");
      });
    });
</script>

	<script type="text/javascript" src="/ReservationSystemKAT-UNE/pulldownControll.js">	</script>

</head>

<body class="body" onload="initChange();">
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
<div class="dialog">
<h2>リソース選択</h2>


<div class = "frame">
<p><font color="red"><c:out value = "${messageForResourceSelectUpper}"/></font></p>

<form action = "/ReservationSystemKAT-UNE/reservesystem/pushSearchButtonOnResourceSelect" method = "post">

<table class="table4">
<tbody>
<tr>
<td class="one" class="dialog" ><b>　利用日</b><a class="red"> ※</a></td>
<td class="right2">
<input type="text" placeholder="2018/1/1（年は省略可）" id="usageDate" name="date"
<c:if test ="${usageDateForReservationRegist!= null}">
value = "<c:out value="${usageDateForReservationRegist}" />"
</c:if>
>
<a class="red" id="clear">
×
</a>
</td>
</tr>

<tr>
<td><b>　利用時間</b><a class="red"> ※</a></td>
<td class="right2"><div class="dialog2">

<select name = "startHour" id = "usageStartHour" onchange="hourChange('usageStartHour','usageStartMinute')">
<c:forEach begin="0" end="9"  varStatus="status">
<option value= "0<c:out value="${status.index}"/>"
<c:if test="${usageStartTimeForResourceSelect.hour == 0 + status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="24"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${usageStartTimeForResourceSelect.hour == status.index }">
selected
</c:if>
>
<c:out value="${status.index}"/>
</option>
</c:forEach>
</select>

：
<select name = "startMinutes" id="usageStartMinute">
<option value="00"
		>00</option>
		<c:forEach begin="1" end="3" varStatus="status">
		<option value="<c:out value="${status.index * 15 }"/>"
		<c:if test="${status.index * 15 == usageStartTimeForResourceSelect.minutes }">
		selected
		</c:if>
		><c:out value="${status.index * 15 }"/>
		</option>
		</c:forEach>

</select>

～
<select name = "endHour" id="usageEndHour" onchange="hourChange('usageEndHour','usageEndMinute');">
<c:forEach begin="0" end="9"  varStatus="status">
<option value= "0<c:out value="${status.index}"/>"
<c:if test="${usageEndTimeForResourceSelect.hour == 0 + status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="24"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${usageEndTimeForResourceSelect.hour == status.index }">
selected
</c:if>
>
<c:out value="${status.index}"/>
</option>
</c:forEach>
</select>
：
<select name = "endMinutes" id="usageEndMinute">
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

の中で<br>
<select name = "actualUseTimeHour"  id = "usageHour" onchange="hourChange('usageHour','usageMinute')">
<c:forEach begin="0" end="9"  varStatus="status">
<option value= "0<c:out value="${status.index}"/>"
<c:if test="${usageTimeForResourceSelect.hour == 0 + status.index }">
selected
</c:if>
>
0<c:out value="${status.index}"/>
</option>
</c:forEach>
<c:forEach begin="10" end="24"  varStatus="status">
<option value= "<c:out value="${status.index}"/>"
<c:if test="${usageTimeForResourceSelect.hour == status.index }">
selected
</c:if>
>
<c:out value="${status.index}"/>
</option>
</c:forEach>
</select>
時間
<select name = "actualUseTimeMinutes" id="usageMinute">
<option value="00"
		>00</option>
		<c:forEach begin="1" end="3" varStatus="status">
		<option value="<c:out value="${status.index * 15 }"/>"
		<c:if test="${status.index * 15 == usageTimeForResourceSelect.minutes }">
		selected
		</c:if>
		><c:out value="${status.index * 15 }"/>
		</option>
		</c:forEach>
</select>
分　利用する
</div>
</td>
<td>
</tr>
<tr>
<td class="dialog"><b>事業所／カテゴリ</b></td>
<td class="right2">
<select class="office" name ="office">
	<option value="">全て
	</option>

	<c:forEach var="obj" items="${officeListForResourceSelect}" varStatus="status">
		<option value="${obj.officeId}"
			<c:if test="${obj.officeId==officeIdForResourceSelect}">
			selected
			</c:if>>
			<c:out value="${obj.officeName}"/>
		</option>
	</c:forEach>
</select>
／
<select name ="category">
<option value="">全て
</option>

<c:forEach var="obj" items="${categoryListForResourceSelect}" varStatus="status">
	<option value="${obj.categoryId}"
		<c:if test="${obj.categoryId==categoryIdForResourceSelect}">
		 selected
		</c:if>>
	<c:out value="${obj.categoryName}"/></option>
</c:forEach>
</select>

 </td>
</tr>
</tbody>
</table>

<br>
<!--
<form>  -->
<input class="more-details" class="dialog2" type="button" value="もっと詳しく" onclick="hyoji1()">
<!--
</form> -->
<br>
<div id="disp" style="display:block;" >
<table class="table4">
<tbody>
<tr>
<td class="dialog"><b>定員</b></td>
<td class="right2">
(※定員がないものは0人で登録されています)

<div class="dialog2">
<input type="text" name="capacity"
value = "<c:out value="${displayCapacityForResourceSelect}" />"
>人以上
</div>
</td>
</tr>
<tr>
<td class="one" class="dialog"><b>リソース名</b></td>
<td class="right2">
<input type="text" name="resourceName"
<c:if test ="${resourceNameForResourceSelect!= null}">
value = "<c:out value="${resourceNameForResourceSelect}" />"
</c:if>
>
</td>
</tr>

<tr>
<td class="dialog"><b>リソース特性</b></td>
<td><div class="scroll4">
<c:forEach var="obj" items="${facilityListForResourceSelect}" varStatus="status">
<input type="checkbox" name = "resourceCharacteristics" value = <c:out value="${obj.facilityId}" />
<c:out value = "${obj.facilityName}" />
<c:forEach var="selectObj" items="${facilityIdListForResourceSelect}" varStatus="status">
<c:if test ="${obj.facilityId==selectObj}">
checked
</c:if>
</c:forEach>
>
<c:out value="${obj.facilityName}" />
<br>
</c:forEach>

<br>
</div></td>
</tr>
</table>
</div>
<br>
<table class="table3">
<tr>

<td>

<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit dialog2" type = "submit" value = "戻る" formaction="${returnPageForResourceSelect}">

</td>
<td>　</td>

<td>

<input class="submit dialog2" type = "submit" value = "検索">
</form>
</td>
</tr>

</table>

</div>

<br><br><br><br><br><br>
<p><font color = "red"><c:out value = "${messageForResourceSelectLower}"/></font></p>

<form action = "/ReservationSystemKAT-UNE/reservesystem/showreserveregist" method = "post">
<c:if test="${fn:length(availableListForResourceSelect) >0 }">
<table id="design-table" class="table table-striped table-bordered" style="width: 90%;">
					<thead>
						<tr>
							<th></th>
							<th>利用時間</th>
							<th>リソース名</th>
							<th>定員</th>
							<th>事業所</th>
							<th>カテゴリ</th>
							<th>補足</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="obj" items="${availableListForResourceSelect}" varStatus="status">
						<tr>
							<td>
								<form action="/ReservationSystemKAT-UNE/reservesystem/showreserveregist" method="post">
									<input type="hidden" name="resourceId" value="${obj.resourceId}">
									<input type="hidden" name="usableStartTime" value="${obj.startResource.timeMinutesValue}">
									<input type="hidden" name="usableEndTime" value="${obj.endResource.timeMinutesValue}">
									<input class="nuime" type = "submit" value = "予約">
								</form>
							</td>
							<td>
								<c:out value="${obj.startResource}" />～<c:out value="${obj.endResource}" />
							</td>
							<td><a href="showresourcedetailstab?resourceId=${obj.resourceId}" target="_blank">
							<c:out value="${obj.resourceName}" /></a></td>

							<c:choose>
								<c:when test="${obj.capacity!=0}">
									<td><c:out value="${obj.capacity}名" /></td>
								</c:when>
								<c:otherwise>
									<td>×</td>
								</c:otherwise>
							</c:choose>

							<td><c:out value="${obj.officeName}" /></td>
							<td><c:out value="${obj.categoryName}" /></td>

							<c:choose>
								<c:when test="${obj.hasSupplement}">
									<td>有</td>
								</c:when>
								<c:otherwise>
									<td>無</td>
								</c:otherwise>
							</c:choose>

						</tr>
					</c:forEach>

					</tbody>
				</table>
</c:if>
				</form>
</div>

<br>

<br>

</div>


<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

</body>
</html>