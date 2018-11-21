/**
 *
 */

function initChange(){
	hourChange('usageStartHour','usageStartMinute');
	hourChange('usageEndHour','usageEndMinute');
	hourChange('usageHour','usageMinute');
	hourChange('QuickStartHour','QuickStartMinute');
}
function hourChange(hourId,miniteId){

	if(document.getElementById(hourId) == null || document.getElementById(miniteId) == null){
		return;
	}
	// 値(数値)を取得
	var hour_selectid = document.getElementById(hourId).selectedIndex;

	// 値(数値)から値(value値)を取得
	var hourVal = parseInt(document.getElementById(hourId).options[ hour_selectid].value);


	if(hourVal == 24){
		document.getElementById(miniteId).innerHTML = "<option value='0'>00</option>";
	}
	else{
		if(document.getElementById(miniteId).childElementCount == 1){
		document.getElementById(miniteId).innerHTML
			  = "<option value='0'>00</option>" +
				"<option value='15'>15</option>" +
				"<option value='30'>30</option>" +
				"<option value='45'>45</option>";
		}
	}
};