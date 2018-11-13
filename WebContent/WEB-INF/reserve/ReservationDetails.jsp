<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
 <html lang="ja">
 <head>
 <meta charset="UTF-8">
 <title>予約詳細</title>
 <link rel="stylesheet" href="/ReservationSystemKAT-UNE/deco.css">
 <link rel="stylesheet" href="/ReservationSystemKAT-UNE/header_footer.css">
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
<script type="text/javascript" src="JavascriptErrorLabel.js">	</script>
<!-- javascript警告ラベル終わり -->



<div class="contents">
 <div class="dialog">
 <h2>予約詳細</h2>


 <!-- メッセージを表示 -->
 <p><font color = "blue">${messageForReservationRegist}
  ${messageForDeleteomCpleted} ${messageForReservationChange} </font></p>
 </div>

<table class="table2">
 <tbody>
 <tr>
 <td class="dialog"><b>リソース</b></td>
 <td class="right2">
 <c:if test="${linkToResourceDetails == true} }">
 	 <a href="showresourcedetailstab?resourceId=${reservationDTOForReservationDetails.resource.resourceId}">
 <c:out value="${obj.resourceName}" /></a> </c:if>


 <c:out value="${reservationDTOForReservationDetails.resource.resourceName}" /></a></td>
 </tr>




 <tr>
 <td class="one" class="dialog"><b>利用日</b></td>
 <td class="right2">
 <c:out value="${reservationDTOForReservationDetails.usageDate}"/></td>
 </tr>
 <tr>
 <td class="dialog"><b>利用時間</b></td> <!-- ●●：●●～●●：●● -->
 <td class="right2">
 <c:out value="${reservationDTOForReservationDetails.usageStartTime.hour}"/>
  :
 <c:out value="${reservationDTOForReservationDetails.usageStartTime.minutes}"/>
   ～
 <c:out value="${reservationDTOForReservationDetails.usageEndTime.hour}"/>
  :
 <c:out value="${reservationDTOForReservationDetails.usageEndTime.minutes}"/>  </td>
 </tr>
 <tr>
 <td class="dialog"><b>予約名称</b></td>
 <td class="right2">
 <c;out value="${reservationDTOForReservationDetails.reservationName}"/>
 </td>
 </tr>
 <tr>
 <td class="dialog"><b>利用人数</b></td>
 <td class="right2">
 <c:out value="${reservationDTOForReservationDetails.numberOfParticipants}"/></td>
 </tr>
 <tr>
 <td><b>予約者</b></td>
 <td class="right2" class="dialog">
 <c:out value="${reservationDTOForReservationDetails.reservedPerson.familyName}"/><!-- 苗字 -->
 <c:out value="${reservationDTOForReservationDetails.reservedPerson.firstName}"/><!-- 名前 --></td>
 </tr>

 <tr>
 <td><b>共同予約者</b></td>
 <td class="right2" class="dialog">
 <c:out value="${reservationDTOForReservationDetails.coReservedPerson.familyName}"/><!-- 苗字 -->
 <c:out value="${reservationDTOForReservationDetails.coReservedPerson.firstName}"/><!-- 名前 -->
 </td>

 </tr>
 <tr>
 <td><b>参加者種別</b></td>
 <td class="right2" class="dialog">
 <c:out value="${reservationDTOForReservationDetails.attendanceTypeDto}"/>
 </td>
 </tr>
 <tr>
 <td class="dialog"><b>詳細</b></td>
 <td class="right2"><div class="scroll2">&lt;script&gt;alert("B");&lt;/script&gt;</div></td>
 </tr>
 </tbody>
 </table>
 <br>






 <table class="table3">
 <tr>
 <td>
 <form action="resourcechange" method="get">
 <input class="submit" class="dialog" type = "submit" value ="変更">
 </form>
 </td>
 <td>　</td>
 <td><form action = "deleteresource" method = "post">





<input class="submit" type = "submit" value = "コピーして予約"></form>
 </td>
 <td>　</td>
 <td><form action = "deleteresource" method = "post">
 <input class="submit" type = "submit" value = "削除"></form>
 </td>
 </tr>
 </table>





<br>





<a class="dialog" href = "/ReservationSystemKAT-UNE/reservesystem/resourcelist" method="get">予約一覧に戻る</a>





</div>






 <div class="footer1" class=><footer class="fotter2">copyright🄫KAT-UNE</footer></div>
 </div>





</body>
 </html>