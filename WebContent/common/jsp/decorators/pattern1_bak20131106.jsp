<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.base.model.user.IUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.forms.prms.tool.Values"%>
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 		<title><decorator:title default="default title"/></title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/layout1.css">
 		<jsp:include page="../include/common.jsp"></jsp:include>
        <decorator:head/>
        <style type="text/css">
        .ui-menu {
        	width: 120px;
        	z-index: 99999;
        }
        </style>
        <script type="text/javascript">
        $(function(){
			$('.submenu').hide().menu();
        	
        	var subMenus = $('.submenu');
        	var menuItems = $('.firstMenu');
        	var sColor = menuItems[0].style.color;
        	var sBackgroundColor = menuItems[0].style.backgroundColor;
        	
        	menuItems.each(function(i){
        		$(menuItems[i]).mouseover(menuMouseover(menuItems[i], subMenus[i]));
        	});
        	
			function menuMouseover(item, subMenuItem) {
        		return function() {
        			menuItems.each(function(i){
            			menuItems[i].style.color = sColor;
            			menuItems[i].style.backgroundColor = sBackgroundColor;
            		});
                	item.style.backgroundColor = '#fff';
                	item.style.color = '#000';
                	subMenus.hide();
            		var menu = $(subMenuItem).show().position({
        				my: "left top",
        				at: "left bottom",
        				of: this
        			});
            		$( document ).one( "click", function() {
        				menu.hide();
        				item.style.backgroundColor = sBackgroundColor;
                    	item.style.color = sColor;
        			});
        		};
            }
        });
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
					<li id="menu<%=(i+1)%>" class="firstMenu"><%=subMenuList.get(i).getFuncName()%></li>
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
					for (int i = 0; i < subMenuList.size(); i++) {
						%>
			<ul id="submenu<%=(i+1)%>" class="submenu">
						<%=MenuUtils.getSubMenuStr(subMenuList.get(i))%>
			</ul>
						<%
					}
				}
			%>
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
			<div class="footer">
				<span>footer display here</span>
			</div>
		</div>
    </body>
</html>