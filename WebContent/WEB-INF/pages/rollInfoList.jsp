<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="json/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<style type="text/css">
</style>
<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title></title>
<script type="text/javascript">
$(document).ready(function() {
	infoHide(30);
	});
</script>
</head>
<body>
	<form method="post" id="rollInfoUpdateForm">
		<table class="tableList">
		<tr><th width="25%">名称</th><th width="15%">创建时间</th><th width="60%">滚动信息</th></tr>
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="list">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" class="trOther">
					<td>${list.rollTitle}</td>
					<td>${list.addTime}</td>
					<td class="tdInfo">${list.rollInfo}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list}">
			<tr>
				<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
		<tr>
			<td colspan="3" class="tdWhite tdc">
				<input type="button" value="关闭" onclick="art.dialog.close()" />
			</td>
		</tr>
		</table>
	</form>
		
	<p:page/>
</body>
</html>