
/**
 * 此方法用于表格中td类容过长时将过长类容隐藏，滑上去内容以div显示，
 * 使用方法为在页面pageInit()里加入此方法，len为当内容达到多长时才进行隐藏 在页面上引用此js文件，并在需要的元素class设为tdInfo
 */
function infoHide(len){
	$("<div class='infoDiv'></div>").appendTo("body");
	$(".infoDiv").hide();
	var tdInfo=$(".tdInfo");
	tdInfo.each(function(i){
	var txt = $.trim($(this).text());
	if(txt.length>len){
	var info = txt.substring(0,len);
		$(this).text(info+"...");
		$(this).mousemove(function (e){
			this.style.color="red";
			$(".infoDiv").html(txt);
			var scrollHeight = getScrollTop();// 滚动条距离顶部距离
			var le = getMousePos(event).x+15+"px";// 光标距离左边距离+15
			var to = getMousePos(event).y+20+"px";// 光标距离顶部距离+20
			var clientWidth = document.documentElement.clientWidth;// 窗口宽度
			var clientHeight = document.documentElement.clientHeight;// 窗口高度
			var divHeight = $(".infoDiv").outerHeight(true);// div高度
			var divWidth = $(".infoDiv").outerWidth(true);// div宽度
			var toHeight = getMousePos(event).y-scrollHeight;// 有滚动条时光标距离顶部距离
			var toWidth = getMousePos(event).x;// 光标距离左边距离
			// 当div接触到浏览器右边时，将其定在原地，div距离左边的距离不再变化。
			if(toWidth + divWidth > clientWidth - 20){
				le = clientWidth - divWidth - 5 + "px";
			}
			// 当div的宽度大于浏览器宽度时，将div宽度设为浏览器宽度。
			if(divWidth > clientWidth){
				le = 5 + "px";
			}
			// 当div接触到浏览器底部时，div距离顶部的距离不再变化。
			if(clientHeight<divHeight+toHeight+25){
				to = clientHeight - divHeight + scrollHeight - 5 + "px";
			}
			//当div接触到浏览器底部并且接触到浏览器右边时
			if((toWidth + divWidth > clientWidth - 21) && (clientHeight<divHeight+toHeight+24)){
				to = clientHeight - divHeight + scrollHeight - 5 + "px";
				le = getMousePos(event).x - 5 - divWidth + "px";
			}
			$(".infoDiv").attr("style","position:absolute;left:"+le+";top:"+to);
			$(".infoDiv").show();
		});
		$(this).mouseout(function (){
			$(".infoDiv").hide();
			this.style.color="#4c4944";
		})
	};});
}		
// 显示字段多少的长度
function infoLength(len){
	$("<div class='lengthDiv'></div>").appendTo("body");
	$(".lengthDiv").hide();
	var tdInfo=$(".tdInfoLength");
	tdInfo.each(function(i){
	var txt = $.trim($(this).text());
	if(txt.length>len){
	var info = txt.substring(0,len);
		$(this).text(info+"...");
	}
  });
}

// 获取光标坐标
function getMousePos(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var x = e.pageX || e.clientX + scrollX;
    var y = e.pageY || e.clientY + scrollY;
    return { 'x': x, 'y': y };
}

// 获取滚动条滚动的距离
function getScrollTop() {
	var scrollPos = 0; 
	if (window.pageYOffset) {
		scrollPos = window.pageYOffset; 
	} 
	else if (document.compatMode && document.compatMode != 'BackCompat') { 
		scrollPos = document.documentElement.scrollTop; 
	} 
	else if (document.body) { 
		scrollPos = document.body.scrollTop; 
	} 
	return scrollPos; 
	}
