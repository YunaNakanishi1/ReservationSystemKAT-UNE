<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>リソース一覧</title>
  <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
  <link rel="stylesheet" href="deco.css">
  <link rel="stylesheet" href="header_footer.css">
  <!-- jQuery -->
  <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
  <!-- DataTables -->
 <!--  <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>-->

    <script src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>

<script>
$(document).ready(function(){
	  $('#design-table').dataTable( {
	    "bPaginate": true,
	    "bLengthChange": false,
	    "bFilter": false,
	    "bSort": false,
	    "bInfo": true,
	    "bAutoWidth": true
	  });
	});

</script>
</head>
<body>

<div class="div">
<header class="header"><p>会議室・備品予約システム</p>
<input class="logintop" type="submit" value="ログアウト">
</header>

<div class="contents">

<h2>リソース一覧</h2>
<p><font color = "blue">${Emessage}</font></p>
<p><a href = "xxx">予約一覧に戻る</a></p>
<input class="submit" type = "submit" value = "リソース登録">

<!-- リソースが0件の場合は以下を表示しない -->
<c:if test="${resourceListSize != 0}">
<table id="design-table" class="table table-striped table-bordered" style="width:90%">
<thead>
<tr>
<th>リソース名</th>
<th>事業所名</th>
<th>カテゴリ名</th>
<th>定員</th>
<th>補足</th>
<th>ステータス</th>
</tr>
</thead>
<tbody>

<c:forEach var="obj" items="${resourceList}" varStatus="status">
<tr>
<td><a href = "resourcedetails?resourceId=${obj.resourceId}"><c:out value="${obj.resourceName}" /></a></td>
<td><c:out value="${obj.officeName}" /></td>
<td><c:out value="${obj.category}" /></td>
<td><c:out value="${obj.capacity}" /></td>
<td><c:out value="${obj.supplement}" /></td>
<td>利用可能にしとくよ</td>
</tr>
</c:forEach>

</tbody>
</table>
</c:if>

</div>

<div class="footer1"><footer class="fotter">copyright🄫KAT-UNE</footer></div>
</div>



</body>
</html>