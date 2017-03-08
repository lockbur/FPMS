<%@page import="com.forms.prms.tool.WebHelp"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String theme = (String)request.getSession().getAttribute(WebConsts.THEME);
if (theme == null || "".equals(theme)) {
	theme = "erp";
}
request.getSession().setAttribute("user", WebHelp.getLoginUser());
%>

<!-- Prevent Cache - START -->

 <%
	response.setHeader("Cache-Control", "must-revalidate"); //HTTP 1.1
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>
<!-- Prevent Cache -  END  -->
<%@page import="java.util.List"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<html>
    <head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 		<title>Common</title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/common.css">
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/common-<%=theme %>.css">
 		<%
 			if ("erp".equals(theme)) {
 				%>
 		<link rel="stylesheet" href="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/themes/base/jquery.ui.all.css">
 				<%
 			} else {
 				%>
 		<link rel="stylesheet" href="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/themes/<%=theme %>/jquery-ui-1.9.2.custom.css">
 				<%
 			}
 		%>
 		
 		
 		
 		
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/text-<%=theme %>.css">
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/left_nav.css">
 		
 		
 		<script type="text/javascript">
 		
	 		//定义全局的系统路径变量
	 		var $publicSystemPath$ = getBasePath();
	
	
	 		function getBasePath() {
	 		    //var curWwwPath = window.document.location.href;
	 		    var pathName = window.top.location.pathname;
	 		    if(pathName.substring(0,1)!='/')
	 		    {
	 		    	pathName="/"+pathName;
	 		    }
	 		    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	 		    return projectName;
	 		}
 		</script>
 		
 		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.min.js"></script>
 		<script type="text/javascript" src="<%=request.getContextPath()%>/component/jquery/jquery-1.9.1.js"></script>
 		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jQueryRotate.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/direction.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/menuScroll.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/menuTip.js"></script>
 		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.core.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.widget.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.button.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.position.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.accordion.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.menu.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.mouse.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.draggable.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.resizable.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.tabs.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.slider.js"></script>
		<script id="date" data="<%=request.getSession().getAttribute("nowDate")%>" src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.datepicker.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery-ui-timepicker-addon.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/i18n/jquery-ui-timepicker-zh-CN.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.autocomplete.js"></script>
		<script src="<%=request.getContextPath()%>/component/artDialog/jquery.artDialog.source.js?skin=opera"></script>
		<script src="<%=request.getContextPath()%>/component/artDialog/plugins/iframeTools.js"></script>
		<script src="<%=request.getContextPath()%>/component/jquery-ui-1.9.2/ui/jquery.ui.dialog.js"></script>
		<script src="<%=request.getContextPath()%>/common/js/common.js"></script>
		<script src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
		<script src="<%=request.getContextPath()%>/common/js/public.js"></script>
		<script src="<%=request.getContextPath()%>/common/js/json2.js"></script>
		<script src="<%=request.getContextPath()%>/component/noty/packaged/jquery.noty.packaged.js"></script>
		<script src="<%=request.getContextPath()%>/common/js/ajax_upload.js"></script>
				
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
		
		<!-- 密码强度css引入-->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/xiniu.css">
		<!-- 密码强度end -->
		
<!-- 		提示框 -->
		<link href="<%=request.getContextPath()%>/common/css/jBox.css" rel="stylesheet">
		<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/jBox.min.js"></script>

		<!-- zTree多功能树js引入  Start-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/component/ztree/css/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="<%=request.getContextPath()%>/component/ztree/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/component/ztree/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/component/ztree/js/jquery.ztree.exedit-3.5.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/forms.ztree.commons.eventcss.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/forms.ztree.commons.function.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/forms.ztree.filter.function.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/forms.ztree.dynamic.manager.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/forms.ztree.plugin.js"></script>
		<!-- zTree多功能树js引入 End -->
		
		<!-- 表格合并 start -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TableCombine.function.commons.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TableCombine.apporule.commons.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TableCombine.check.commons.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TableCombine.process.commons.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TableCombine.object.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TableCombine.js"></script>
		 
		<script type="text/javascript">
			App.ROOT = '<%=WebUtils.getRoot()%>';
			App.FUNC_KEY =  '<%=WebConsts.FUNC_ID_KEY%>';
			<%
				List<Function> list = WebUtils.getSessionAttr(WebConsts.VISIT_MENU_LIST);
				if (list != null && list.size() > 0) {
					%>
			App.FIRST_LEVEL_MENU = '<%=list.get(list.size() - 1).getFuncId()%>';
			App.CURR_FUNC_ID = '<%=list.get(0).getFuncId()%>'
					<%
				}
			%>
			
			/*
			 * "input type='text' maxlength='??'"对中文长度无法限制的问题解决
			 */
			$(window).ready(function (){
				$("input[type='text']").keyup(function (){
					var maxLength = -1;
					var checkLength = 0;
					if($(this).attr("maxlength")!=null){
						maxLength = parseFloat($(this).attr("maxlength"));
					}
					if(maxLength>=0){
						for(var i = 0; i < $(this).val().length; i++){
							if(isChinese($(this).val().charCodeAt(i))){
								checkLength += 2;
							}else{
								checkLength += 1;
							}
							if(checkLength>maxLength){
								break;
							}
						}
						if(checkLength>maxLength){
							var newValue = $(this).val().substring(0,i);
							$(this).val(newValue);
						}
					}
				});
			});
			
			
			$(document).ready (function(){
				/*var showimg = $("<img>").attr("name","show").attr("class","coll")
					.attr("src","<%=request.getContextPath()%>/common/images/topMenuStatus_show.png").attr("title","隐藏");
				var hideimg = $("<img>").attr("name","hide").attr("class","coll").css("display","none")
					.attr("src","<%=request.getContextPath()%>/common/images/topMenuStatus_hidden.png").attr("title","显示");
				
				$(".collspan-control th:last-child,.collspan-control td:last-child").append(showimg).append(hideimg);
				
				$(".collspan-control img[name='show']").click(function(){
					$(this).parents("tr").siblings().hide();
					$(this).hide();
					$(this).siblings("img[name='hide']").show();
				});
				
				$(".collspan-control img[name='hide']").click(function(){
					$(this).parents("tr").siblings().show();
					$(this).hide();
					$(this).siblings("img[name='show']").show();
				});*/
				
				
				$(".collspan-control").css("cursor","pointer").click(function(){
					var siblings = $(this).siblings();
					if(siblings.is(".collspan")){
						siblings.filter(".collspan").toggle();
					}else{
						siblings.toggle();
					}
				
					$(this).attr("title",function (){
						if(this.title=="收缩"){
							return "展开";
						}else{
							return "收缩";
						}
					});
				}
				).attr("title","收缩");
				
			});
			
			function validateValue(textbox)  
	        {  
	             var IllegalString = "\`~^*|\\:\"<>=,\'";  
	             var textboxvalue = textbox.val();
	             var index = textboxvalue.length - 1;
	               
	             var s = textbox.val().charAt(index);  
	               
	             if(IllegalString.indexOf(s)>=0 && ""!=s)
	             {  
	            	alert("请勿输入非法字符："+s);
	                s = textboxvalue.substring(0,index);  
	                textbox.val(s);
	             }  
	        } 

			//提示文字信息
			$(document).ready(function() {
				$(".prompt").each(function(){
					var html = $(this).html();
					check(this,html);
				});
				
				$("input").bind("keyup", function(){
					validateValue($(this));
				});
				
				$("textarea").bind("keyup", function(){
					validateValue($(this));
				});

			});
			function check(obj,html){
				var data = {};
				data['name'] =  html;
				App.ajaxSubmit("common/toolTip/getMsg.do",{data : data,async:false}, function(data) {
					var returnData=data.data;
					var promptInfo = returnData.value;
					if(returnData.flag== true){
						//有数据
						var text ="<span style= 'border-bottom: 1px dashed #B50028'>"+html+"</span>";
						$(obj).html(text);
						$(obj).jBox('Tooltip', {
							closeOnMouseleave:true,
							  content: promptInfo,
							  offset: {
						            x: 0,
						            y: 2
						        }
						});
					}
				});
			}
			//下面是禁用当点击backspace键时后退页面
			function banBackSpace(e){   
			    var ev = e || window.event;//获取event对象   
			    var obj = ev.target || ev.srcElement;//获取事件源   
			    var t = obj.type || obj.getAttribute('type');//获取事件源类型  
			    //获取作为判断条件的事件类型
			    var vReadOnly = obj.getAttribute('readonly');
			    //处理null值情况
			    vReadOnly = (vReadOnly == "") ? false : vReadOnly;
			    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
			    //并且readonly属性为true或enabled属性为false的，则退格键失效
			    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") 
			                && vReadOnly=="readonly")?true:false;
			    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
			    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")
			                ?true:false;        
			    
			    //判断
			    if(flag2){
			        return false;
			    }
			    if(flag1){   
			        return false;   
			    }   
			}
			window.onload=function(){
				//禁止后退键 作用于Firefox、Opera
				document.onkeypress=banBackSpace;
				//禁止后退键  作用于IE、Chrome
				document.onkeydown=banBackSpace;
			};
			
// 			$(document).ready(function() {
// 				var tab=document.getElementById("listTab");
// 				var tabFirstChild=$("#listTab tr:first-child");
// 				var tabSecondChild=$("#listTab tr")[1];
// 				if (!tab) {
// 					return false;
// 				}else if (!tabFirstChild) {
// 					return false;
// 				}else if (!tabSecondChild) {
// 					return false;
// 				}
// 				else{		
// 					$("#listTab tr:first-child").children().each(function(i,domEle1){
// 						$("#listTab tr:nth-child(2)").children().each(function(j,domEle2){
// 							if(i==j){
// 								var width = $(domEle1).width();
// 								$(domEle2).css({"width":width});
// 							}
// 						});
// 					});
				
// 					tableFixed("#listTab");
// 				}
// 			});
			
// 			function tableFixed(tableObj){
// 				var listTab = $(tableObj);	
// 				var firstTrObj = $("#listTab tr:first-child");
// 				var tableListTop = listTab[0].offsetTop;	
// 				//获取TableList对象表格的最顶部TOP值
// 			$(window).scroll(function(){
// 				//获取window竖向滚动条的滚动距离
// 				var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
// // 				console.info(scrollTop+":"+tableListTop);
// 				//如果滚动距离小于表格的最顶部，则去掉新增样式，采用默认的样式
// 				if(scrollTop <= tableListTop){  
// 					$(firstTrObj).removeClass("tr-fixed-ie");
// 				}else{
// 				//如果滚动距离大于或等于表格的最顶部，则为表头行添加样式、设置表头行的宽度和固定在主窗体的顶部(设置top属性)、并为TR中首个TH赋值，处理TR被无故缩小
// 					$(firstTrObj).addClass("tr-fixed-ie");
// 					//width=TableList值列表第一行的长度、top=样式.top+样式.menu1两者的高度相加
// 					$(firstTrObj).css({"width":$(tableObj).find("tr")[1].offsetWidth, "top": ($(".top")[0].offsetHeight + $(".menu1")[0].offsetHeight)});  		//, "top": 127(需要在整合页面取得 .memu1 的offsetHeight+offsetTop值的和)
// 					//将TableList首行首TD的宽度赋值给表格头TR的首个TH，以解决该tr被无故缩小,解决方案为：设置样式width于tr的第一个th时(原因不明)
//  					$(firstTrObj).find("th")[0].width = $($(tableObj).find("tr")[1]).find("td")[0].offsetWidth;	

// 				}
// 			});
// 			}
			
			
			
			
		</script>
    </head>
</html>