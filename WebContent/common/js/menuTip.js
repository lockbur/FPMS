/**
 * 菜单说明交互
 * 当用户点击一级菜单时
 * 1，显示菜单说明div
 * 2，当前一级菜单颜色变为"#BE2F4D"，然后在x轴和y轴上依次减弱
 * 3，当前菜单宽度增加，其他菜单宽度减少
 */
var menuTip = {
		menuTipInitHeight:40,
		startRGB:new RGB(190,47,77),//暗红
		endRGB:new RGB(241,158,158),//浅红
		menuTipInitColor:"#eeeadc",
		maxWidth:450,
		
		_showMenuTip:function(funcId){
			//.main 先隐藏
			$(".main").hide();
			/*var length = $("#menuDesc>ul>li").length;
			var index ;
			var initWidth = 150;
			//根据菜单数量设置初始化宽度
			var pingWidth = parseFloat(($(window).width()-300)/length);
			if(pingWidth>=80){
				pingWidth = 80;
			}
			//(1)
			$("#menuDesc").show();
			//还原
			for(var j=0;j<length;j++){
				$("#menuDesc>ul>li:eq("+j+")").animate({ 
				    width: pingWidth+"px"
				}, 500 );
				$("#menuDesc>ul>li:eq("+j+")").find(".menuName").animate({ 
				    width: pingWidth+"px"
				}, 500 );
				$("#menuDesc>ul>li:eq("+j+")>div").find(".menuName").css("background-color",menuTip.menuTipInitColor);
				$("#menuDesc>ul>li:eq("+j+")>div").find(".menuName").css("color","#4c4944");
				$("#menuDesc>ul>li:eq("+j+")>div").find(".menuTxt").css("background-color",menuTip.menuTipInitColor);
				$("#menuDesc>ul>li:eq("+j+")>div").find(".menuTxt").css("display","none");
			}
			//(3)
			if(length>1){
				var newWidth;
				if((menuTip.maxWidth-initWidth)/(length-1)>(pingWidth/2)){
					newWidth = pingWidth/2;
				}else{
					newWidth = pingWidth - (menuTip.maxWidth-initWidth)/(length-1);
				}
				for(var i=0;i<length;i++){
					if($("#menuDesc>ul>li:eq("+i+")").attr("id")==(funcId+"_MenuDesc")){
						index = i;
						$("#menuDesc>ul>li:eq("+i+")").animate({ 
						    width: menuTip.maxWidth+"px"
						}, 500 );
						$("#menuDesc>ul>li:eq("+i+")>div").find(".menuName").animate({ 
						    width: initWidth+"px"
						}, 500 );
						$("#menuDesc>ul>li:eq("+i+")>div").find(".menuName").css("color","white");
						$("#menuDesc>ul>li:eq("+i+")>div").find(".menuTxt").css("color","white");
						
						$("#menuDesc>ul>li:eq("+i+")>div").find(".menuTxt").css("display","block");
						$("#menuDesc>ul>li:eq("+i+")>div").find(".menuTxt").css("width",(menuTip.maxWidth-initWidth-6)+"px")
					}else{
						$("#menuDesc>ul>li:eq("+i+")").animate({ 
						    width: newWidth+"px"
						}, 500 );
						$("#menuDesc>ul>li:eq("+i+")>div").find(".menuName").animate({ 
						    width: newWidth+"px"
						}, 500 );
					}
				}
			}else if(length>0){
				index = 0;
				$("#menuDesc>ul>li:eq(0)").animate({ 
				    width: menuTip.maxWidth+"px"
				}, 500 );
				$("#menuDesc>ul>li:eq(0)>div").find(".menuTxt").css("display","block");
			}
			//处理渐变色
			//y轴
			var yLength = $("#menuDesc>ul>li:eq("+index+")>div").length;
			for(var i = 0;i<yLength;i++){
				var temp = calcuteRGB(menuTip.startRGB,menuTip.endRGB,yLength,i);
				$("#menuDesc>ul>li:eq("+index+")>div:eq("+i+")>div").css("background-color","rgb("+temp.R+","+temp.G+","+temp.B+")");
			}
			//后x轴
			for(var i=index;i>=0&&index>0;i--){
				temp = calcuteRGB(menuTip.startRGB,menuTip.endRGB,(index+1),index-i);
				$("#menuDesc>ul>li:eq("+i+")>div:eq(0)>div:eq(0)").css("background-color","rgb("+temp.R+","+temp.G+","+temp.B+")");
				$("#menuDesc>ul>li:eq("+i+")>div:eq(0)>div:eq(0)").css("color","white");
			}
			//前x轴
			for(var i=0;i<length-index&&index<length-1;i++){
				temp = calcuteRGB(menuTip.startRGB,menuTip.endRGB,(length-index),i);
				$("#menuDesc>ul>li:eq("+(i+index)+")>div:eq(0)>div:eq(0)").css("background-color","rgb("+temp.R+","+temp.G+","+temp.B+")");
				$("#menuDesc>ul>li:eq("+(i+index)+")>div:eq(0)>div:eq(0)").css("color","white");
			}*/
			$("#menuDesc").show();
			$("._menuDesc").css("display","");
			$("#"+funcId+"_MenuDesc").show();
		}
}
/**
 * RGB对象
 */
function RGB(r,g,b){
	this.R = r;
	this.G = g;
	this.B = b;
}
/**
 * 已知初始RGB值和目标RGB值还有过渡梯度总数以及当前梯度（从0开始）求当前RGB值
 */
function calcuteRGB(startRGB,endRGB,totalItem,nowItem){
	if(startRGB==null||endRGB==null||totalItem==null||totalItem<=1||nowItem==null||nowItem<0){
		//参数异常
	}else{
		var rIndex = (endRGB.R-startRGB.R)/(totalItem-1);
		var gIndex = (endRGB.G-startRGB.G)/(totalItem-1);
		var bIndex = (endRGB.B-startRGB.B)/(totalItem-1);
		var resultR = parseInt(startRGB.R + rIndex*nowItem);
		var resultG = parseInt(startRGB.G + gIndex*nowItem);
		var resultB = parseInt(startRGB.B + bIndex*nowItem);
		return new RGB(resultR,resultG,resultB);
	}
}









