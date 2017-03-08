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
<title>预算初审详情</title>
</head>

<body>
<p:authFunc funcArray="02030105"/>
<form action="<%=request.getContextPath()%>/budget/firstaudit/view.do" method="post" id="FirstAuditForm">
	<p:token/>
	<table>
		<tr>
			<th align="center">模板编号</th>
			<th align="center">归口部门</th>
			<th align="center">物料名称</th>
			<th align="center">内部序号</th>
			<th align="center">审批位置</th>
			<th align="center">审批前金额</th>
			<th align="center">审批后金额</th>
			<th align="center">审批柜员</th>
			<th align="center">审批时间</th>
			<th align="center">审批备注</th>
		</tr>
		<c:if test="${!empty list}">
		<c:forEach items="${list}" var="view" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td>${view.tmpltId}</td>
			<td>${view.dutyName}</td>
			<td>${view.matrName}</td>
			<td>${view.auditSeq}</td>
			<td>${view.auditLvl}</td>
			<td>${view.beforeAmt}</td>
			<td>${view.afterAmt}</td>
			<td>${view.auditOper}</td>
			<td>${view.auditDate} ${view.auditTime}</td>
			<td>${view.auditMemo}</td>
			</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty list}">
	   		<tr>
			<td colspan="10" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>	
		<tr>
			<td colspan="10">
				 <input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
	</table>
	<br>
</form>
<p:page/>
</body>
</html>