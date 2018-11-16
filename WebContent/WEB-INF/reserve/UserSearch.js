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
    <select name="userList" size="5" style="width:200px">\
    <option value="u0000003">理工三郎 (u0000003)</option>\
    <option value="u0000004">理工四郎 (u0000004)</option>\
    <option value="u0000005">理工五郎 (u0000005)</option>\
    <option value="u0000006">理工六郎 (u0000006)</option>\
    <option value="u0000022">山田二郎 (u0000022)</option>\
    <option value="u0000033">理工三郎 (u0000033)</option>\
    </select>\
    <br>\
    <input class="button" type = "submit" onclick="closeSearch(\''+id+'\');" value = "選択"> <input class="button" type = "submit" onclick="closeSearch(\''+id+'\');" value = "閉じる">\
    <hr>\
     </td>\
    	';
}
