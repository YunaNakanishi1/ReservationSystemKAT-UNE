//ページが読み込まれるたび、「Javascriptが有効ではない」と表示されるラベルを
//Javascriptで消す。Javascriptが無効化されていたら消せないので表示される
//window.onload =function(){
//	var jsBox = document.getElementById("JavascriptLabelBox");
//	if(jsBox != null){
//		jsBox.innerHTML = "";
//	}
//
//}

document.observe("dom:loaded",
		function() {
	var jsBox = document.getElementById("JavascriptLabelBox");
	if(jsBox != null){
		jsBox.innerHTML = "";
	}
});