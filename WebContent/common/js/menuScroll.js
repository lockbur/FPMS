/*
 * y轴滚动菜单处理
 * 1，点击top_trigger让top菜单在显示和隐藏的状态之间切换
 * 2，top菜单和left菜单以及top_trigger随着页面的滚动而滚动
 * 3，若是向上滚动且当前滚动距离超过top菜单的高度则显示top菜单
 */
var $liy_initTop = 0;
var $liy_initLeftTop = 107;
var $liy_initTriggerTop = 107;
$(window).scroll(function (){
	var topHeight;
	var status = $(".top_trigger").attr("name");
	if(status!=null){
		
		//通过css把position改为fixed实现随着屏幕的滚动而滚动（js实现时position是absolute）
//		$liy_scrollBy(".top_trigger");
//		$liy_scrollBy(".left");
//		if("show" == status){
//			//显示状态，三者都随着页面的滚动而滚动
//			$liy_scrollBy(".top");
//		}else if("hidden" == status){
//			//隐藏状态，只有left菜单和top_trigger随着页面的滚动而滚动
//		}
		//(3)
		//定位器随着页面的滚动而滚动
		var oldDefTop = parseFloat($("#positionDef").css("top").substring(0,$("#positionDef").css("top").length-2));
		$liy_scrollBy("#positionDef");
		var nowDefTop = parseFloat($("#positionDef").css("top").substring(0,$("#positionDef").css("top").length-2));
		if(nowDefTop<oldDefTop){
//			$liy_showTopMenu();
		}
	}
});
/**
 * 监听菜单切换
 */
$(window).ready(function (){
	if($(".top").css("height")!=null){
		//(1)
		$(".top_trigger").click(function (){
			var status = $(".top_trigger").attr("name");
			if("show"==status){
				$liy_hiddenTopMenu();
			}else if("hidden"==status){
				$liy_showTopMenu();
			}else{
				//error status
			}
		});
	}
	if($(".left").css("width")!=null){
		$(".left_trigger").click(function (){
			var leftStatus = $(".left_trigger").attr("name");
			if("show"==leftStatus){
				$liy_hiddenLeftMenu();
				$("#scrollTableDiv").css("width",$("#scrollTableDiv").parent().width());
				
				//ERP对账管理模块添加侧边栏展开处理
				$("#hideDiv").css("width",$("#listDiv").width());
				$("#scrollTab").css("width",$("#listDiv").width());
			}else if("hidden"==leftStatus){
				$liy_showLeftMenu();
				$(window).css("overflow-X","hidden");
				$("#scrollTableDiv").css("overflow-Y","hidden");
				$("#scrollTableDiv").css("width",$("#scrollTableDiv").parent().width()*0.85);
				
				//ERP对账管理模块添加侧边栏缩进处理
				$("#hideDiv").css("width",$("#listDiv").width());
				
				$("#scrollTab").css("width",$("#listDiv").width());
				
			}else{
				//error status
			}
		});
	}
});
/**
 * 根据状态切换初始化高度
 */
function $liy_triggerInitTop(status){
	switch(status){
		case "show":
			$liy_initTop = 0;
			$liy_initLeftTop = 127;
			$liy_initTriggerTop = 127;
			break;
		case "hidden":
			$liy_initTop = 0;
			$liy_initLeftTop = 1;//3px间隙 - 2px边框
			$liy_initTriggerTop = 1;//3px间隙 - 2px边框
			break;
		default:
			break;
	}
}
/**
 * 某元素随着页面的滚动而滚动
 */
function $liy_scrollBy(objSelector){
	var initTop = 0;
	switch(objSelector){
		case ".top":
			initTop = $liy_initTop;
			break;
		case ".left":
			initTop = $liy_initLeftTop;
			break;
		case ".top_trigger":
			initTop = $liy_initTriggerTop;
			break;
		default:
			break;
	}
	var newTop = initTop+getScrollTop();
	$(objSelector).css("top",newTop+"px");
}
/**
 * 显示顶部菜单
 */
function $liy_showTopMenu(){
	var topHeight = parseFloat($(".top").css("height").substring(0,$(".top").css("height").length-2));
	var status = $(".top_trigger").attr("name");
	var oldTriggerTop;
	var newTriggerTop;
	var newLeftTop;
	var newTop;
	if("hidden"==status){
		$(".top").fadeIn("slow");
		oldTriggerTop = parseFloat($(".top_trigger").css("top").substring(0,$(".top_trigger").css("top").length-2));
		newTop = oldTriggerTop-1;////3px间隙 - 2px边框
//		$(".top").css("top",newTop+"px");
		newTriggerTop = oldTriggerTop+topHeight+3;
		$(".top_trigger").css("top",newTriggerTop+"px");
		$(".left_trigger").css("top",newTriggerTop+"px");
		$(".top_trigger").attr("src","/fpms/common/images/topMenuStatus_show.png");
		newLeftTop = newTriggerTop;
		$(".left").css("top",newLeftTop+"px");
		$(".top_trigger").attr("name","show");
		$(".main").css("top","124px");
		$("#menuDesc").css("margin-top","200px");
		$liy_triggerInitTop("show");
	}
}
/*
 * 隐藏顶部菜单
 */
function $liy_hiddenTopMenu(){
	var topHeight = parseFloat($(".top").css("height").substring(0,$(".top").css("height").length-2));
	var status = $(".top_trigger").attr("name");
	var oldTriggerTop;
	var newTriggerTop;
	var newLeftTop;
	var newTop;
	if("show"==status){
		$(".top").hide();
//		$(".top").css("display","none");
		oldTriggerTop = parseFloat($(".top_trigger").css("top").substring(0,$(".top_trigger").css("top").length-2));
		newTriggerTop = oldTriggerTop-topHeight-3;
		$(".top_trigger").css("top",newTriggerTop+"px");
		$(".left_trigger").css("top",newTriggerTop+"px");
		$(".top_trigger").attr("src","/fpms/common/images/topMenuStatus_hidden.png");
		newLeftTop = newTriggerTop;
		$(".left").css("top",newLeftTop+"px");
		$(".top_trigger").attr("name","hidden");
		$(".main").css("top","0px");
		$("#menuDesc").css("margin-top","0px");
		$liy_triggerInitTop("hidden");
	}
}
/**
 * 显示左侧菜单
 */
function $liy_showLeftMenu(){
	var status = $(".left_trigger").attr("name");
	var newMainLeft;
	var newTriggerLeft;
	if("hidden"==status){
		//竖条背景图右移
		$("body").css("background-position","205px");
		$(".left").fadeIn("slow");
		newMainLeft = 240;
		$(".main").css("margin-left",newMainLeft+"px");
		$("#menuDesc").css("margin-left","350px");
		$("._menuDesc").css("left","140px");
		newTriggerLeft = 240;
		$(".left_trigger").css("left",newTriggerLeft+"px");
		$(".top_trigger").css("left","248px");
		$(".left_trigger").attr("src","/fpms/common/images/leftMenuStatus_show.png");
		$(".left_trigger").attr("name","show");
	}
}
/*
 * 隐藏左侧菜单
 */
function $liy_hiddenLeftMenu(){
	var status = $(".left_trigger").attr("name");
	var newMainLeft;
	var newTriggerLeft;
	if("show"==status){
		//竖条背景图左移
		$("body").css("background-position","0px");
		$(".left").hide();
		newMainLeft = 3;
		$(".main").css("margin-left",newMainLeft+"px");
		$("#menuDesc").css("margin-left","33px");
		$("._menuDesc").css("left","140px");
		newTriggerLeft = 0;
		$(".left_trigger").css("left",newTriggerLeft+"px");
		$(".top_trigger").css("left","15px");
		$(".left_trigger").attr("src","/fpms/common/images/leftMenuStatus_hidden.png");
		$(".left_trigger").attr("name","hidden");
	}
}
/** 
 * 获取滚动条距离顶端的距离 
 */  
function getScrollTop() {  
        var scrollPos;  
        if (window.pageYOffset) {  
        	scrollPos = window.pageYOffset; 
        }else if (document.compatMode && document.compatMode != 'BackCompat'){ 
        	scrollPos = document.documentElement.scrollTop; 
        }else if (document.body) {
        	scrollPos = document.body.scrollTop; 
        }   
        return scrollPos;   
}  
