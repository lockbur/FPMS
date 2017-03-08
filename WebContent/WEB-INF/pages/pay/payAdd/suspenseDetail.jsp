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
<title>已结清明细</title>
</head>
<body>
	<table>
		<tr>
			<th colspan="4">已结清明细</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">暂收结清编号</td>
			<td class="tdRight" width="25%">
				${suspenseDetaiInfo.cleanPayId}
			</td>
			<td class="tdLeft" width="25%">正常付款单号</td>
			<td class="tdRight" width="25%">
				${suspenseDetaiInfo.normalPayId}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">结清金额(录入)</td>
			<td class="tdRight" width="25%">
				<fmt:formatNumber type="number" value="${suspenseDetaiInfo.cleanAmt}" minFractionDigits="2"/>
			</td>
			<td class="tdLeft" width="25%">结清金额(FMS)</td>
			<td class="tdRight" width="25%">
				<c:if test="${!empty suspenseDetaiInfo.cleanAmtFms}">
					<fmt:formatNumber type="number" value="${suspenseDetaiInfo.cleanAmtFms}" minFractionDigits="2"/>
				</c:if>
				<c:if test="${empty suspenseDetaiInfo.cleanAmtFms}">
					<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">结清原因</td>
			<td class="tdRight" width="25%">
				${suspenseDetaiInfo.cleanReason}
			</td>
			<td class="tdLeft" width="25%">状态</td>
			<td class="tdRight" width="25%">
				${suspenseDetaiInfo.dataFlagName}
			</td>
		</tr>
	</table>
	<br/>
	<div>
    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>
</body>
</html>