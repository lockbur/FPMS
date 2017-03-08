<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批历史记录</title>
</head>
<body>
	<table class="tableList">
		<tr>
			<th colspan="6">审批历史记录</th>
		</tr>
		<tr>
			<td>审批序号</td>
			<td>状态</td>
			<td>操作说明</td>
			<td>操作柜员</td>
			<td>操作日期</td>
			<td>操作时间</td>
		</tr>
		<c:if test="${!empty hisList }">
			<c:forEach items="${hisList }" var="bean">
				<tr>
					<td>${bean.innerNo }</td>
					<td>${bean.dataFlagName }</td>
					<td>${bean.operMemo }</td>
					<td>${bean.instUser }</td>
					<td>${bean.instDate }</td>
					<td>${bean.instTime }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty hisList}">
					<tr><td style="text-align: center;" colspan="6"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
	</table>
	<br/>
	<div>
		<input type="button" value="关闭" onclick="art.dialog.close()"/>
	</div>
</body>
</html>