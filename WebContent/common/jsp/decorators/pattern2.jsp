<%@page import="com.forms.prms.web.user.domain.User"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.prms.tool.WebHelp"%>
<%@page import="com.forms.platform.web.base.model.user.IUser"%>
<%@page import="com.forms.prms.tool.Values"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
 		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- 解决弹出框关闭IE自动开启兼容视图模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
 		<!--
 		<title>总行财务项目管理系统<decorator:title default="default title"/>  修改，使用固定的页面title</title>
 		<link rel="icon" href="<%=request.getContextPath()%>/common/images/123.ico" type="image/x-ico"/>   修改页面title图片
 		-->
 		<title>总行财务项目管理系统</title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/layout2.css">
 		<jsp:include page="../include/common.jsp"></jsp:include>
        <decorator:head/>
        <style type="text/css">
        .ui-menu { 
			position: static; 
			width: 140px; 
			z-index: 99999;
		}
		
		
		#userinfo{
	position:absolute;
	top:62px;
	right:90px;
	display:none;
	border:#C0B8B8 1px solid;
	background-color: #E0E8F7;
	z-index:1210;
	padding:5px;
	width: 258px;
	height:162px;
	overflow-x:hidden; 
}
#userinfo table{
	padding: 1px;
	background-color: #F2F7FF;
}
#userinfo table tr{
	height:20px;
}
#userinfo table tr td{
	line-height: 17px;
	font-size: 12px;
}
#userinfo table tr td img{
	height:60px;
	width:60px;
}
#iframe_userinfo{
	position:absolute;
	display:none;
	z-index:1200;
	padding: 6px;
}
        </style>
        <script type="text/javascript">
        $(function(){
  
        	if ('' != App.FIRST_LEVEL_MENU) {
        		getMenu(App.FIRST_LEVEL_MENU);
        	}
        });
        
        function getMenu(funcId) { 
        	
        	$(".firstMenu").removeClass("menu1-selected");
        	$("#" + funcId).addClass("menu1-selected");
        
        	var url = 'common/function/getSubMenu.do';
        	var data = {};
        	data[App.FIRST_LEVEL_MENU_KEY] = funcId;
        	data['menuId'] = funcId;
        	App.ajaxSubmit(url, 
        		{data : data}, 
        		function(data){
        			if(data.userNotFound){
        				location.href = "<%=request.getContextPath()%>/common/jsp/error/relogin.jsp";
        			}
        			var subMenuList = data.subMenuList;
        			var subMenuPanel = $('#submenupanel').empty();
        			var ul = $('<ul id="submenuUl" class="accordion"></ul>');
        			for (var i = 0; i < subMenuList.length; i++) {
	        			getSubMenu(ul, subMenuList[i], 2, false);
        			}
        			subMenuPanel.append(ul);
        			//$('.submenu').menu();
        			var lis = $('#submenupanel li');
        			for (var i = 0; i < lis.size(); i++) {
        				if (lis[i].id == App.CURR_FUNC_ID) {
        					var li = $(lis[i]);
        					li.parent().css('display','block');
        					var subLi = li;
        					while (subLi.parent().parent('li').size() > 0) {
        						subLi = subLi.parent().parent('li');
        						subLi.parent().css('display','block');
        					}
        					li.children().addClass('active');
        					 if($(li).attr("name")=='4'){
        						 //说明是最下面的菜单 那么 就给顶级菜单 标志为展开状态
        						 li.parent().parent().parent().parent().children().addClass('active_mn');
        					 }
        					li.parent().parent().children().addClass('active_mn');
        					li.parent().attr("name","show");
        					break;
        				}
        			}
        			//点击一级菜单首页跳转到它下的二级菜单的欢迎页
	       			/* if(funcId == '11'){
	        			var aObj = $("#submenuUl #0001").find("a");
	        			url = '/ERP/homepage/main.do?VISIT_FUNC_ID=0001';
						if (typeof url != "undefined") {
			        		$(aObj).addClass('active');
			        		App.submitShowProgress();
			        		//$(".main").attr("style") == "display: none;"
			        		if($(".main").attr("style") == "display: none;"){
			        			//location.href = url;
			        			menuClick($(aObj),2,url);
			        		}
				        }
	       			} */
        		});
        }
        
        function getSubMenu(parent, func, level, hidden) {
        	if (hidden) {
        		hiddenCls = ' menu-hidden';
        	}
        	
        	if (func.leaf) {
        		var li = $("<li id=\"" + func.funcId + "\" name=\"" + level + "\"  style=\"border-bottom:1px solid #c9c9c9;\"   ><a href=\"javascript:menuClick(this, " + level +", '" + App.ROOT + func.url + "?" + App.FUNC_KEY + "=" + func.funcId + "');\">" +func.funcName + "</a></li>");
        		parent.append(li);
        		//点击一级菜单首页跳转到它下的二级菜单的欢迎页
	        	if(func.funcId == '0001'){
	        		url = App.ROOT + func.url + "?" + App.FUNC_KEY + "=" + func.funcId ;
	        		var aObj = $(li).find("a");
	        		if (typeof url != "undefined") {
		        		$(aObj).addClass('active');
		        		App.submitShowProgress();
		        		if($(".main").css('display') == "none"){
		        			menuClick($(aObj),2,url);
		        		}
			        }
	        	} 
        	} else {
        		var children = func.children;
        		if(func.funcName.length>12){
        			var a = $('<a href="javascript:void(0);" title='+func.funcName+' id='+func.funcId+' onclick="menuClick(this, ' + level + ');">' + func.funcName + '</a>');
        		}
        		else{
        			var a = $('<a href="javascript:void(0);"  id='+func.funcId+' onclick="menuClick(this, ' + level + ');">' + func.funcName + '</a>');
        		}
       			var ul="";
       			if(level == "2"){
       				ul = $('<ul class=\"sub-menu\" id="'+func.funcId+'ul" >'); 
       			}else if(level == "3"){
       				ul = $('<ul class=\"sub-menu2\" id="'+func.funcId+'ul" style=\"display: none;\">'); 
       			};
       			 
        		for (var i = 0; i < children.length; i++) {
        			getSubMenu(ul, children[i], level + 1, true);
        		}
       			var li = $('<li id="' + func.funcId + '" name="' + level + '"  >').append(a).append(ul);
       			parent.append(li);
        	}
        }
        
        function menuClick(elem, level, url) {
        	if (typeof url != "undefined") {
        		$(elem).addClass('active');
        		App.submitShowProgress();
        		location.href = url;
        		//alert(url);
        	} else {
        		var accordion_head = $('.accordion > li > a'),
				accordion_body = $('.accordion li > .sub-menu');
        		
        		var accordion_head2 = $('.sub-menu > li > a'),
				accordion_body2 = $('.sub-menu li > .sub-menu2');
        		
        		var accordion_head3=$('.sub-menu2 > li > a');
        		
        		//第一层
    			// Store variables
    			if(level == '2'){
					if ($(elem).attr('class') != 'active'){
						if($(elem).attr('class') == 'active_mn'){
							//记录点击的菜单的上级菜单是展开状态 那么 就走下面的流程
							accordion_body.slideUp('normal');
							$(elem).removeClass('active');
							$(elem).removeClass('active_mn');
						}else{
							accordion_body.slideUp('normal');//将所有展开的收起来
							$(elem).next().slideToggle('normal');//将点击的展开来
							accordion_head2.removeClass('active');
							accordion_head.removeClass('active');
							accordion_head.removeClass('active_mn');
						    accordion_head3.removeClass('active');//移除选中状态
							$(elem).addClass('active');
						}
					}else{
					accordion_body.slideUp('normal');
					$(elem).removeClass('active');
				 
					}
    			}else if(level == '3'){
    		 
    				//第二层
    				if ($(elem).attr('class') != 'active' ){
    					if($(elem).attr('class') == 'active_mn'){
    						$(elem).next().stop(true,true).slideUp('normal');
    	    				$(elem).removeClass('active');
    	    				$(elem).parent().removeClass('active');
							$(elem).removeClass('active_mn');
    					}else{
    						accordion_body2.slideUp('normal');
        					$(elem).next().slideToggle('normal');
        					accordion_head.removeClass('active');
        					accordion_head2.removeClass('active');
        					accordion_head2.removeClass('active_mn');
        				    accordion_head3.removeClass('active');
        				    $(elem).parent().parent().parent().children().addClass("active_mn");
        					$(elem).addClass('active');
    					}
    				}else{
    				$(elem).next().stop(true,true).slideUp('normal');
    				$(elem).removeClass('active');
    				$(elem).parent().removeClass('active');
    				}
    				
    			}else if(level == '4'){
    				accordion_head3.removeClass('active');
    				accordion_head3.removeClass('active_mn');
    				$(elem).addClass('active');
    			}
    			
        	}
        }
        
        function loginOut()
        {
          if(!confirm("您真的要离开吗?"))
          {
        	  return false;
          }	
          location.href='<%=request.getContextPath()%>/user/logout.do';
        }
        
        $(function (){
        	$("#userInfoHandler").hover(showUserInfo, hideUserInfo);
        	
        });
        
        
        function showUserInfo()
        {	
        	$("#iframe_userinfo").css({
        		"top":$("#userinfo").css("top"),
        		"right":$("#userinfo").css("right"),
        		"width":$("#userinfo").css("width"),
        		"height":$("#userinfo").css("height")
        	});
        	$("#iframe_userinfo").fadeIn();
        	$("#userinfo").fadeIn();
        }

        function hideUserInfo()
        {
        	$("#userinfo").fadeOut();
        	$("#iframe_userinfo").fadeOut();
        }
        
        
		
		//点击浏览器右上角关闭按钮
		function beforeCloseUnload()
		{
			if(getOs()==1){//IE浏览器
		        var n = window.event.screenX - window.screenLeft;
		        var b = n > document.documentElement.scrollWidth-20;
		        if(b && window.event.clientY<0 || window.event.altKey){//是关闭而非刷新
		            //alert("");
		            //closeBro();
		        	location.href='<%=request.getContextPath()%>/user/logout.do';
		        }else{
		            //alert("是刷新而非关闭");
		        }
		    }
// 			else if(getOs() == 3)
// 			{
// 		    	//谷歌无法判断是刷新页面还是关闭页面
// 				event.returnValue = '你正试图离开${user.userId}';
// 				return "";//for chrome,safari
// 			}
		}
		
        //关闭浏览器触发事件（关闭前调用）
		window.onbeforeunload = beforeCloseUnload;
		
		//ajax关闭浏览器时删除在线用户
		function closeBro(){
			var url = 'user/closeBro.do';
        	var data = {};
        	App.ajaxSubmit(url, 
        		{data: data , async: false},
        		function(data){
        			//var flag = data.flag;
        			
        		});
		}
		
		//浏览器判断
		function getOs(){  
	        if(navigator.userAgent.indexOf("MSIE")>0)return 1;//IE  
	        if(isFirefox=navigator.userAgent.indexOf("Firefox")>0)return 2;//Firefox  
	     	if(isSafari=navigator.userAgent.indexOf("Chrome")>0)return 3;//Chrome  
	        if(isSafari=navigator.userAgent.indexOf("Safari")>0)return 4;//Safari  
	        if(isCamino=navigator.userAgent.indexOf("Camino")>0)return 5;//Camino  
	        if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0)return 6;//Gecko  
	      	if(isOpera=navigator.userAgent.indexOf('Opera') >= 0) return 7;//Opera  
	        //other...  
	        return 0;  
	    }  
        </script>
    </head>
    <body>
<!--     	<div class="container"> -->
	       <div class="top">
	      	<div class="about_user">
<!-- 				<div  class="font"><a href="#">简</a></div> -->
<!-- 				<div  class="layout"><a href="#"></a></div> -->
<!-- 				<div  class="style"><a href="#"></a></div> -->
				<div class="exit"><a href="#" onclick="loginOut();">退出</a></div>
				<div class="user"  id="userInfoHandler">
					<%-- <a href="#" onclick="javascript:location.href='<%=request.getContextPath()%>/user/main.do?<%=WebConsts.FUNC_ID_KEY%>=0001'">
					测试</a> --%>
					<a href="javascript:void(0);" onclick="return false;">
					${user.userName}</a>
				</div>
					<div id="userinfo" >
						<table width="100%" cellpadding="0" cellspacing="1" >
							<tr>
								<td align="left" width="60" nowrap="nowrap">员工编号:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" nowrap="nowrap" valign="bottom">${user.userId}</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">员工名称:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" nowrap="nowrap" valign="bottom">${user.userName}</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">员工角色:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" nowrap="nowrap" valign="bottom">${user.roleName}</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">责任中心:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" nowrap="nowrap" valign="bottom">${user.dutyCode}</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">责任中心:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" nowrap="nowrap" valign="bottom">${user.dutyName}</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">机构编号:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" colspan="2" nowrap="nowrap" valign="bottom">${user.orgCode}</td>
							</tr>
							<tr>
								<td align="left" nowrap="nowrap">机构名称:</td>
								<td align="left" style="border-bottom: #B3D0FF solid 1px;" colspan="2" nowrap="nowrap" valign="bottom">${user.orgName}</td>
							</tr>
						</table>
					</div>
					<iframe id="iframe_userinfo" frameborder="0" scrolling="no" marginheight="0" ></iframe>
	       </div>
	       <div class="clear"></div>
<%-- 	       		<span style="display: block; float: right;"><input type="button" value="登出" onclick="javascript:location.href='<%=request.getContextPath()%>/user/logout.do'"/></span> --%>
<%-- 				<p:layout className="systemStyle"> --%>
<%-- 					<p:layoutOpt label="label.layout.1" value="/WEB-INF/decorators/decorators1.xml"/> --%>
<%-- 					<p:layoutOpt label="label.layout.2" value="/WEB-INF/decorators/decorators2.xml"/> --%>
<%-- 				</p:layout> --%>
	       <%
	       		IUser user = WebUtils.getUserModel();
	       		if (user != null) {
		       		ITree<Function> menuTree = user.getMenuTree();
		       		Function root = menuTree.getRoot();
		       		List<Function> subMenuList = null;
		       		if (root != null) {
	       %>
			<div class="menu1">
				<ul>
					<li class="empty"></li>
					<%
						subMenuList = root.getChildren();
						if (subMenuList != null) {
									for (int i = subMenuList.size()-1; i >= 0; i--) {
					%>
					<li id="<%=subMenuList.get(i).getFuncId()%>" class="firstMenu"><a href="javascript:void(0);" onclick="getMenu('<%=subMenuList.get(i).getFuncId()%>');menuTip._showMenuTip('<%=subMenuList.get(i).getFuncId()%>');"><%=subMenuList.get(i).getFuncName()%></a></li>
							<%
							if (i != subMenuList.size() - 1) {
								%>
					<li class="spliter"></li>
								<%
								}
							}
						}
					%>
				</ul>
			</div>
					<%
					}
	       		}
					%>
	<!--        <hr /> -->
		</div>
		<img style="display:none" src="<%=request.getContextPath()%>/common/images/time_gif.gif"><!-- 提前加载等待效果图片，解决谷歌浏览器中等效效果图片不显示的bug -->
		<img name="show" class="top_trigger" src="<%=request.getContextPath()%>/common/images/topMenuStatus_show.png"/>
		<img name="show" class="left_trigger" src="<%=request.getContextPath()%>/common/images/leftMenuStatus_show.png"/>
		<input type="hidden" id="positionDef" style="position:absolute;top:0px;left:0px"/><!-- 滚动定位器，用于判断是向上滚动还是向下滚动 -->
	   <%-- <!-- 1，2级菜单描述 -->
	   <%
       		if (user != null) {
	       		ITree<Function> menuTree = user.getMenuTree();
	       		Function root = menuTree.getRoot();
	       		List<Function> subMenuList = null;
	       		if (root != null) {
       %>
		<div id="menuDesc">
			<ul>
				<%
					subMenuList = root.getChildren();
					if (subMenuList != null) {
						for (int i = subMenuList.size()-1; i >= 0; i--) {
				%>
				<li id="<%=subMenuList.get(i).getFuncId()%>_MenuDesc" ><div><div class="menuName"><%=subMenuList.get(i).getFuncName()%></div><div class="menuTxt">这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单这是一级菜单</div></div>
						<%
						    List<Function> secMenuList = subMenuList.get(i).getChildren();
							if (secMenuList != null) {
								for (int j = 0; j <= secMenuList.size()-1; j++) {
								
						%>
									<div><div class="menuName"><%=secMenuList.get(j).getFuncName()%></div><div class="menuTxt">这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单这是二级菜单</div></div>
						<% 
								}
							}
						%>
				</li>
				<%
					}
				}
				%>
			</ul>
		</div>
		<%
				}
       		}
		%> --%>
		<div id="menuDesc" onselectstart="return false">
		<%-- <img alt="" src="<%=request.getContextPath()%>/common/images/welcome.jpg"> --%>
		<%
       		if (user != null) {
	       		ITree<Function> menuTree = user.getMenuTree();
	       		Function root = menuTree.getRoot();
	       		List<Function> subMenuList = null;
	       		if (root != null) {
	       			subMenuList = root.getChildren();
					if (subMenuList != null) {
						for (int i = subMenuList.size()-1; i >= 0; i--) {
       %>
<%--        <div id="<%=subMenuList.get(i).getFuncId()%>_MenuDesc" class="_menuDesc"><%=subMenuList.get(i).getFuncName()%>，<%=subMenuList.get(i).getMemo()%></div> --%>
       <%
					}
				}
			}
		}
		%>
		</div>
		<div class="body_wrap">
		<div class="left">
			<div id="submenupanel" class="menus2">
				<ul id="submenuUl" class="submenus"></ul>
			</div>
		</div>
       		<div class="main">
<!-- 		       		<div class="content ui-widget-content ui-corner-all"> -->
	       			<p:navigator className="nav"/>
	       			<%
	       			if (!"Y".equals(WebUtils.getRequestAttr(Values.IS_SUCCESS_PAGE))&&!"Y".equals(WebUtils.getRequestAttr(Values.IS_ERROR_PAGE))) {
	       				    %>
					<p:message className="message-panel" infoCls="info-message" warningCls="warning-message" errorCls="error-message"/>
							<%
	       				}
	       			%>
	    			<decorator:body />
<!-- 		    		</div> -->
	
 

    		</div>
    	</div>
<!-- 	       <div class="footer"> -->
<!-- 	       		<span>footer display here</span> -->
<!-- 	       </div> -->
<!-- 	  	</div> -->
    </body>
</html>