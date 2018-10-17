<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	  });




</script>


<script>

flag=false;
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



</head>
<body class="body">
<div class="div">

<header class="header"><p>会議室・備品予約システム</p>
<form action="/ReservationSystemKAT-UNE/logout" method="get">
<input class="logintop" type="submit" value="ログアウト">
</form>
</header>
<div class="contents">
<div class="dialog">
<h2>リソース選択</h2>
<div class = "frame">
<p><font color = "red">メッセージ</font></p>

<table class="table4">
<tbody>
<tr>
<td class="dialog"><b>利用日</b><a class="red"> ※</a></td>
<td class="right2">
<input type="text" placeholder="2018/1/1（年は省略可）" name="usageDate">
</td>
</tr>

<tr>
<td class="dialog"><b>利用時間</b><a class="red"> ※</a></td>
<td class="right2">
<select name = "usageStartHour">
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
：
<select name = "usageStartMinute">
<option value="aaa">00</option>
<option value="aaa">15</option>
<option value="aaa">30</option>
<option value="aaa">45</option>
</select>

～
<select name = "usageEndHour">
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
：
<select name = "usageEndMinute">
<option value="aaa">00</option>
<option value="aaa">15</option>
<option value="aaa">30</option>
<option value="aaa">45</option>
</select>

の中で<br>
<select name = "usageHour">
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
時間
<select name = "usageMinute">
<option value="aaa">00</option>
<option value="aaa">15</option>
<option value="aaa">30</option>
<option value="aaa">45</option>
</select>
分利用する
</td>
<td>
</tr>
<tr>
<td class="dialog"><b>事業所／カテゴリ</b></td>
<td class="right2">
<select name ="office">
<option value="aaa" selected>全て</option>
<option value="aaa">晴海</option>
<option value="aaa">新横浜</option>
</select>
／
<select name ="category">
<option value="aaa" selected>全て</option>
<option value="aaa">会議室</option>
<option value="aaa">UCS</option>
</select>
 </td>
</tr>
</tbody>
</table>

<form>
<input class="submit2 dialog2" type="button" value="詳細表示" onclick="hyoji1()">
</form>

<div id="disp" style="display:none;">
<table class="table4">
<tbody>
<tr>
<td class="dialog"><b>利用人数</b></td>
<td class="right2">
<input type="text" name="participants">人以上
</td>
</tr>
<tr>
<td class="dialog"><b>リソース名</b></td>
<td class="right2">
<input type="text" name="resourceName">
</td>
</tr>

<tr>
<td class="dialog"><b>リソース特性</b></td>
<td><div class="scroll4">
<input type="checkbox" name = "facility" value = "ホワイトボード有" />ホワイトボード有<br>
<input type="checkbox" name = "facility" value = "プロジェクター有" />プロジェクター有<br>
<input type="checkbox" name = "facility" value = "外部スピーカー有" />外部スピーカー有<br>
<input type="checkbox" name = "facility" value = "ホワイトボード有" />ホワイトボード有<br>
<input type="checkbox" name = "facility" value = "プロジェクター有" />プロジェクター有<br>

<br>
</div></td>
</tr>
</table>
</div>
<br>
<table class="table3">
<tr>

<td><form action = "${returnPage}" method = "get">
<input type="hidden" name="resourceId" value = "<c:out value = "${resourceId}"/>" >
<input class="submit dialog2" type = "submit" value = "戻る"></form>
</td>
<td>　</td>
<td>
<form action="setresource" method="post">
<input class="submit dialog2" type = "submit" value = "検索"></td>
</form>
</tr>

</table>

</div>
<br><br>
<p><font color = "red">メッセージ</font></p>
<form action = "">
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
							<tr>
							<td><input type = "submit" value = "予約"></td>
							<td>13:00～18:00</td>
							<td><a href="resourcedetails?resourceId=${obj.resourceId}">"晴海412S"</a></td>
							<td>5</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>有</td>
							</tr>
							<tr>
							<td><input type = "submit" value = "予約"></td>
							<td>12:00～14:00</td>
							<td><a href="resourcedetails?resourceId=${obj.resourceId}">"晴海4207"</a></td>
							<td>25</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>無</td>
							</tr>
							<tr>
							<td><input type = "submit" value = "予約"></td>
							<td>10:00～14:00</td>
							<td><a href="resourcedetails?resourceId=${obj.resourceId}">"新横浜会議室4F"</a></td>
							<td>20</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>有</td>
							</tr>
							<tr>
							<td><input type = "submit" value = "予約"></td>
							<td>10:00～14:00</td>
							<td><a href="resourcedetails?resourceId=${obj.resourceId}">"新横浜会議室4F"</a></td>
							<td>20</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>有</td>
							</tr>
							<tr>
							<td><input type = "submit" value = "予約"></td>
							<td>10:00～14:00</td>
							<td><a href="resourcedetails?resourceId=${obj.resourceId}">"新横浜会議室4F"</a></td>
							<td>20</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>有</td>
							</tr>

					</tbody>
				</table>
				</form>
</div>

<br>


<br>

</div>


<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
</div>

</body>
</html>