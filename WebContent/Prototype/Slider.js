/**
 *
 */

$(function() {

	var start = 360;
	var end = 540;
	var range = 60;

	var par = range / (end-start);

	var sliderWidth = 500;
	var handleWidth = sliderWidth * par;


	$('#slider-custom').slider();
	$('#slider-custom .ui-slider-handle').css({'width':handleWidth+'px'});
	$('#slider-custom').css({'width':sliderWidth-handleWidth+'px'});


});