//予約者・共同予約者の変更
//引数にはtrのidを指定すること
function closeSearch(id){
    var tr_object = document.getElementById(id);
    tr_object.innerHTML = '';
}
//予約者・共同予約者の変更
//引数にはtrのidを指定すること
function addSearch(id)
{
    var tr_object = document.getElementById(id);
    tr_object.innerHTML = '<td class="one" class="dialog"></td>\
    <td class="right2">\
    <hr>\
    <input type="text" name=""> <input class="button" type = "submit" value = "検索">\
    <br>\
    <select name="userList" size="5" style="width:200px">';

    var test = '<%=request.getAttribute("userListForReservationChange") %>';
    alert(test);
    for(var i=0;i<'<%=request.getAttribute("userListForReservationChange").size() %>';i++){
    	tr_object.innerHTML += '<option value="">'+'<%=request.getAttribute("userListForReservationChange").get(i).getFirstName") %>'+"</option>";
    }
    tr_object.innerHTML += '</select>\
    <br>\
    <input class="button" type = "submit" onclick="closeSearch(\''+id+'\');" value = "選択"> <input class="button" type = "submit" onclick="closeSearch(\''+id+'\');" value = "閉じる">\
    <hr>\
     </td>\
    	';
}
