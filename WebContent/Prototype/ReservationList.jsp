<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>予約一覧</title>

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

<h2>予約一覧</h2>
<div class = "reframe">
<div class = "leftside">
<form action="newReservation" method="get">
<input class="submit dialog2" type="submit" value="新規予約">
</form>
　
<form action="newReservation" method="get">
<input class="submit dialog2" type="submit" value="今すぐ予約">
</form>
</div>
<div class = "rightside">
<form action="newReservation" method="get">
<input class="submit dialog2" type="submit" value="リソース一覧">
</form>
</div>

</div>
<br>
<div class = "frame">
<!--
<p><font color = "red">利用日が正しく入力されていません。（例：2018/10/18）</font></p>
 -->
<table class="table4">
<tbody>
<tr>
<td class="one" class="dialog"><b>　利用日</b><a class="red"> ※</a></td>
<td class="right2">
<div class="dialog2">
<input type="text" placeholder="2018/1/1（年は省略可）" name="usageDate"><font color = "red">×</font> 以降30日間表示
</div>
</td>
</tr>

<tr>
<td class="dialog"><b>　利用時間</b><a class="red"> ※</a></td>
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
<c:forEach begin="10" end="24"  varStatus="status">
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
<c:forEach begin="10" end="24"  varStatus="status">
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

<tr>
<td class="dialog"><b>事業所</b></td>
<td class="right2">
<select name ="office">
<option value="aaa" selected>全て</option>
<option value="aaa">晴海</option>
<option value="aaa">新横浜</option>
</select>
</td>
</tr>
<tr>
<td class="dialog"><b>カテゴリ</b></td>
<td class="right2">
<select name ="category">
<option value="aaa" selected>全て</option>
<option value="aaa">会議室</option>
<option value="aaa">UCS</option>
</select>
 </td>
</tr>
</table>

<input type="checkbox" name="example" value="ownReserve">自分の予約のみ表示　
<input type="checkbox" name="example" value="pastReserve">過去の予約も表示　
<input type="checkbox" name="example" value="pastReserve">削除済み予約を表示　
<br><br>
<table class="table3">
<tr>
<br>
<td>
<form action="setresource" method="post">
<input class="submit dialog2" type = "submit" value = "検索"></td>
</form>
</tr>

</table>


</div>
<br><br><br><br><br><br><br>

<div class="silver">　</div>
<br><br>

<p><font color = "red">検索結果は0件です</font></p>

</div><!-- diallogとじ -->
<br>
<form action = "">
<!--
<table id="design-table" class="table table-striped table-bordered" style="width: 90%;" >
					<thead>
						<tr style="background-color: white;">
							<th>利用日</th>
							<th>利用時間</th>
							<th>予約名称</th>
							<th>リソース</th>
							<th>事業所</th>
							<th>カテゴリ</th>
							<th>予約者</th>
							<th>削除</th>
						</tr>
					</thead>
					<tbody>
							<tr>
							<td>2020/12/25</td>
							<td>13:00～14:00</td>
							<td><a href="xxx">UCS</a></td>
							<td>UCS001</td>
							<td>晴海</td>
							<td>UCS</td>
							<td>理工五郎</td>
							<td>未削除</td>
							</tr>
							<tr style="background-color: white;">
							<td>2020/12/25</td>
							<td>18:00～19:00</td>
							<td><a href="xxx">時間ではじく</a></td>
							<td>晴海412S</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>理工五郎</td>
							<td>未削除</td>
							</tr>
							<tr>
							<td>2020/12/26</td>
							<td>15:00～18:00</td>
							<td><a href="xxx">新横浜</a></td>
							<td>新横浜412S</td>
							<td>新横浜</td>
							<td>会議室</td>
							<td>理工五郎</td>
							<td>未削除</td>
							</tr>
							<tr style="background-color: white;">
							<td>2020/12/28</td>
							<td>9:00～10:00</td>
							<td><a href="xxx">あいうえおあいうえおあいうえおあいうえおあいうえおあいうえお</a></td>
							<td>晴海412S</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>理工五郎</td>
							<td>未削除</td>
							</tr>
							<tr>
							<td>2020/12/28</td>
							<td>10:00～11:00</td>
							<td><a href="xxx">あ</a></td>
							<td>晴海415M</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>理工五郎</td>
							<td>未削除</td>
							</tr>
							<tr style="background-color: white;">
							<td>2020/12/31</td>
							<td>15:00～17:00</td>
							<td><a href="xxx">五郎なし</a></td>
							<td>晴海412S</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>理工三郎</td>
							<td>未削除</td>
							</tr>
							<tr>
							<td>2020/1/22</td>
							<td>15:00～17:00</td>
							<td><a href="xxx">共同予約者五郎</a></td>
							<td>晴海412S</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>理工三郎</td>
							<td>未削除</td>
							</tr>
							<tr style="background-color: white;">
							<td>2020/1/23</td>
							<td>15:00～17:00</td>
							<td><a href="xxx">30日目（晴海会議室）</a></td>
							<td>晴海412S</td>
							<td>晴海</td>
							<td>会議室</td>
							<td>理工五郎</td>
							<td>未削除</td>
							</tr>

					</tbody>
				</table>
				 -->
</form>
<br>
<br>
<br>


</div>

<div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>


</div>
</body>
</html>