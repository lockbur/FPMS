<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<title>付款流水明细</title>
</head>
<body>
	<table class="tableList">
		<tr>
			<th colspan="5">付款流水明细信息</th>
		</tr>
		<tr>
			<th>合同号</th>
			<th>付款单</th>
			<th>发票号</th>
			<th>付款金额</th>
			<th>付款日期</th>
		</tr>
		<c:if test="${!empty list }">
			<c:forEach items="${list }" var="bean">
				<tr>
					<td>${bean.cntNum }</td>
					<td>${bean.payId }</td>
					<td>${bean.invoiceId }</td>
					<td>${bean.payAmt }</td>
					<td>${bean.payDate }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="5" style="text-align: center;"><span class="red">没有付款记录</span></td>
			</tr>
		</c:if>
	</table>
	<br/>
	<div>
   	 	<input type="button" value="关闭" onclick="art.dialog.close();">
	</div>
</body>
</html>