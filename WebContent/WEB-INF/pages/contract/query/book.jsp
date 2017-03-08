	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看操作日志</title>
</head>
<body>
<p:authFunc funcArray="03020604"/>
<form action="<%=request.getContextPath()%>/contract/query/book.do?<%=WebConsts.FUNC_ID_KEY%>=03020604" method="post" id="waterBook">
	<p:token/>
	<table class="tableList">
		<tr>
			<!-- <th width="15%">流水号</th>
			<th width="15%">业务号</th> -->
			<th width="15%">业务类型</th>
			<!-- <th width="5%">业务菜单</th> -->
			<th width="25%">操作日志</th>
			<th width="15%">操作类型</th>
			<th width="15%">操作时间</th>
			<th width="10%">操作人</th>
			<th width="10%">旧数据状态</th>
			<th width="10%">新数据状态</th>			
		</tr>
		<c:if test="${!empty wblist}">
		<c:forEach items="${wblist}" var="wb">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<%-- <td>${wb.wbNum}</td>
				<td>${wb.busNum}</td> --%>
				<td>${wb.busType}</td>
				<%-- <td>${wb.busMenu}</td> --%>
				<td>${wb.operateLog}</td>
				<td>${wb.operateType}</td>
				<td>${wb.operateDate} ${wb.operateTime}</td>
				<td>${wb.operator}</td>
				<td>${wb.oldDataFlag}</td>
				<td>${wb.newDataFlag}</td>
		</c:forEach>
		</c:if>
		<c:if test="${empty wblist}">
	   		<tr>
			<td colspan="10" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>	
	</table>
</form>
<p:page/>
<br><br><br>
<input style="text-align: center;"  type="button" value="返回" onclick="backToLastPage('${uri}');">
</body>
</html>