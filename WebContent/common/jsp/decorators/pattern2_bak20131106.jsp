<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.base.model.user.IUser"%>
<%@page import="com.forms.prms.tool.Values"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 		<title><decorator:title default="default title"/></title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/layout2.css">
 		<jsp:include page="../include/common.jsp"></jsp:include>
        <decorator:head/>
        <style type="text/css">
        .ui-menu { 
			position: static; 
			width: 140px; 
		}
        </style>
        <script type="text/javascript">
        $(function(){
        	if ('' != App.FIRST_LEVEL_MENU) {
        		getMenu(App.FIRST_LEVEL_MENU);
        	}
        });
        
        function getMenu(funcId) {
        	var url = 'function/getSubMenu.do';
        	var data = {};
        	data[App.FUNC_KEY] = 'FUNC_QUERY_SUB_MENU';
        	data[App.FIRST_LEVEL_MENU_KEY] = funcId;
        	data['menuId'] = funcId;
        	App.ajaxSubmit(url, 
        		{data : data}, 
        		function(data){
        			var subMenuList = data.subMenuList;
        			var subMenuPanel = $('#submenupanel').empty();
        			var ul = $('<ul id="submenuUl" class="submenu"></ul>');
        			for (var i = 0; i < subMenuList.length; i++) {
	        			getSubMenu(ul, subMenuList[i]);
        			}
        			subMenuPanel.append(ul);
        			$('.submenu').menu();
        		});
        }
        
        function getSubMenu(parent, func) {
        	if (func.leaf) {
        		var li = $('<li><a href="' + App.ROOT + func.url + '?' + App.FUNC_KEY + '=' + func.funcId + '">' + func.funcName + '</a></li>');
        		parent.append(li);
        	} else {
        		var children = func.children;
       			var a = $('<a href="javascript:void(0);">' + func.funcName + '</a>');
       			var ul = $('<ul>'); 
        		for (var i = 0; i < children.length; i++) {
        			getSubMenu(ul, children[i]);
        		}
       			var li = $('<li>').append(a).append(ul);
       			parent.append(li);
        	}
        }
        </script>
    </head>
    <body>
    	<div class="container">
	       <div class="banner">
	       		Banner Here
	       		<span style="display: block; float: right;"><input type="button" value="登出" onclick="javascript:location.href='<%=request.getContextPath()%>/user/logout.do'"/></span>
				<p:layout className="systemStyle">
					<p:layoutOpt label="label.layout.1" value="/WEB-INF/decorators/decorators1.xml"/>
					<p:layoutOpt label="label.layout.2" value="/WEB-INF/decorators/decorators2.xml"/>
				</p:layout>
	       		<p:locale className="language">
					<p:localeOpt label="简" value="zh_CN"/>
					<p:localeOpt label="EN" value="en_US"/>
				</p:locale>
				<p:theme className="theme">
					<p:themeOpt label="label.theme.default" value="base"/>
					<p:themeOpt label="label.theme.spring" value="spring"/>
					<p:themeOpt label="label.theme.summer" value="summer"/>
					<p:themeOpt label="label.theme.autumn" value="autumn"/>
					<p:themeOpt label="label.theme.winter" value="winter"/>
				</p:theme>
	       </div>
	       <%
	       		IUser user = WebUtils.getUserModel();
	       		if (user != null) {
		       		ITree<Function> menuTree = user.getMenuTree();
		       		Function root = menuTree.getRoot();
		       		List<Function> subMenuList = null;
		       		if (root != null) {
	       %>
			<div class="menu">
				<ul>
					<%
						subMenuList = root.getChildren();
						if (subMenuList != null) {
									for (int i = 0; i < subMenuList.size(); i++) {
					%>
					<li id="menu<%=(i+1)%>" class="firstMenu"><a href="javascript:void(0);" onclick="getMenu('<%=subMenuList.get(i).getFuncId()%>');"><%=subMenuList.get(i).getFuncName()%></a></li>
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
			<div class="wrapper">
				<div id="submenupanel" class="submenupanel">
					<ul id="submenuUl" class="submenu"></ul>
				</div>
	       		<div class="contentWrapper">
		       		<div class="content ui-widget-content ui-corner-all">
		       			<p:navigator/>
		       			<%
		       			if (!"Y".equals(WebUtils.getRequestAttr(Values.IS_SUCCESS_PAGE))&&!"Y".equals(WebUtils.getRequestAttr(Values.IS_ERROR_PAGE))) {
		       				    %>
						<p:message className="message-panel" infoCls="info-message" warningCls="warning-message" errorCls="error-message"/>
								<%
		       				}
		       			%>
		    			<decorator:body />
		    		</div>
	    		</div>
	    	</div>
	       <div class="footer">
	       		<span>footer display here</span>
	       </div>
	  	</div>
    </body>
</html>