var duration = parseInt(document.getElementById("usageTime").value,10);//利用時間
//alert(document.getElementById("usageTimeForReservationSelect"));
var start = parseInt(document.getElementById("usageStartTime").value,10);//利用可能開始時間
var end = parseInt(document.getElementById("usableEndTime").value,10); //利用可能終了時間
var handleWidthMin = 15;//つまみの最小幅
var sliderStartPoint = parseInt(document.getElementById("usableStartTime").value,10);	//スライダー初期位置

//スライダーを生成
$(function() {
	var slider = $('#slider').slider({
                      value: sliderStartPoint,
                      min: 0,
                      max: (end - start - duration),
                      step: 15,
                      slide: function( event, ui ) {
                      $( "#slider-timelabel" ).val( getSliderTimeStr( ui.value) );
                      $( "#usageStartTime" ).val( start + ui.value );
                      $( "#usageEndTime" ).val( start + ui.value + duration );

                      }
  });
	initLabel();
//引数の分だけスライダーを増減する（範囲外は超えない
  $('#minus15').click( function (){
  	slider.slider( "value",  $( "#slider" ).slider( "value" ) - 15);
  	initLabel();
  });
  $('#plus15').click( function (){
	  	slider.slider( "value",  $( "#slider" ).slider( "value" ) + 15);
	  	initLabel();
	  });

setHandleLength();
});

//スライダーの幅を設定
function setHandleLength(){

    var handleWidthRate = duration / (end - start); //スライダーの幅に対するつまみ幅の割合。会議時間/予約可能時間で表される

    var sliderWidth = parseInt($('#slider-box').css('width'));//スライダーの外枠を取得

    var handleWidth = sliderWidth * handleWidthRate;//つまみの幅を計算

    //つまみ最小幅をセット
    if(handleWidth < handleWidthMin){
        handleWidth = handleWidthMin;
    }
    //window.getComputedStyle($('#slider .ui-slider-handle')[0], '::after').setProperty('padding-right',handleWidth+"px");
   $('head').append('\
		   <style>\
		   #slider .ui-slider-handle::after {\
		   content: "";\
		   padding-right:'+handleWidth+'px;\
		   padding-bottom: 7px;\
		   border: 2px solid #333;\
		   border-right: 4px solid #333;\
		   border-bottom: 3px solid #333;\
		   background: #333 url(images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;\
		   font-weight: normal;\
		   color: #555;\
		   }</style>\
		   ');
   // $('#slider .ui-slider-handle').attr('data-width',handleWidth+"px");//つまみの幅をセット
    $('#slider').css('width',sliderWidth-handleWidth+"px");//スライダーの幅をセット

}
//スライダーの位置に合わせた時間の文字列を返す。
//06:30 ~ 7:30
function getSliderTimeStr(sliderValue){
    var startStr = getTimeFormatHHMM(start + sliderValue);
    var endStr = getTimeFormatHHMM(start + sliderValue + duration);
    return startStr + " ~ " + endStr;

}
//受け取った時間をhh:mmフォーマットに即して返す。引数は時間を分に換算したものを渡す
//390 → 06:30
function getTimeFormatHHMM(minits){
    //時間を計算
    var h = parseInt(minits/60);
    var m = minits % 60;
    //0埋めで2桁
    var h0 = ('00' + h).slice(-2);
    var m0 = ('00' + m).slice(-2);

    return h0 + ":" + m0;
}
function initLabel(){
	 $( "#slider-timelabel" ).val( getSliderTimeStr( $( "#slider" ).slider( "value" )) );
	  $( "#usageStartTime" ).val( start + $( "#slider" ).slider( "value" ).value );
	  $( "#usageEndTime" ).val( start + $( "#slider" ).slider( "value" ).value + duration );
	  $( "#slider-left-timelabel" ).val( getTimeFormatHHMM( start ));
	  $( "#slider-right-timelabel" ).val( getTimeFormatHHMM( end ));
	  $( "#slider-center-timelabel" ).val( getTimeFormatHM( duration ));
}
//受け取った時間をh時間m分フォーマットに即して返す。引数は時間を分に換算したものを渡す
//390 → 6時間30分
function getTimeFormatHM(minits){
  //時間を計算
  var h = parseInt(minits/60);
  var m = minits % 60;

  return h + "時間" + m +"分";
}

