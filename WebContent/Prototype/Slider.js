var duration = 10;//1時間
var start = 0;//午前６時
var end = 24*60-10;//午前9時
var handleWidthMin = 15;//つまみの最小幅

//スライダーを生成
$(function() {
  $('#slider').slider({
                      value:0,
                      min: 0,
                      max: (end - start - duration),
                      step: 10,
                      slide: function( event, ui ) {
                      $( "#slider-timelabel" ).val( getSliderTimeStr( ui.value) );
                      }
  });
  $( "#slider-timelabel" ).val( getSliderTimeStr( $( "#slider" ).slider( "value" )) );
  $( "#slider-left-timelabel" ).val( getTimeFormatHHMM( start ));
  $( "#slider-right-timelabel" ).val( getTimeFormatHHMM( end ));
  $( "#slider-center-timelabel" ).val( getTimeFormatHM( duration ));



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

    $('#slider .ui-slider-handle').css('width',handleWidth+"px");//つまみの幅をセット
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

//受け取った時間をh時間m分フォーマットに即して返す。引数は時間を分に換算したものを渡す
//390 → 6時間30分
function getTimeFormatHM(minits){
  //時間を計算
  var h = parseInt(minits/60);
  var m = minits % 60;

  return h + "時間" + m +"分";
}
