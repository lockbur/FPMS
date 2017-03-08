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
	<table>
		<tr>
			<th colspan="2">付款流水明细信息</th>
		</tr>
		<tr>
			<td class="tdLeft">批次号</td>
			<td class="tdRight">
				${payLogDetailBean.batchNo}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >序号</td>
			<td class="tdRight" >
				${payLogDetailBean.seqNo }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >合同号</td>
			<td class="tdRight" >
				${payLogDetailBean.cntNum}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >付款单号</td>
			<td class="tdRight" >
				${payLogDetailBean.payId}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >付款金额</td>
			<td class="tdRight" >
				<fmt:formatNumber type="number" value="${payLogDetailBean.payAmt}" minFractionDigits="2" maxFractionDigits="2"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >付款日期</td>
			<td class="tdRight" >
				${payLogDetailBean.payDate}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >影像ID</td>
			<td class="tdRight" >
				${payLogDetailBean.imageId}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >付款是否取消</td>
			<td class="tdRight" >
				<c:if test="${payLogDetailBean.payCancelState == 'Y'}">
					是
				</c:if>
				<c:if test="${payLogDetailBean.payCancelState == 'N'}">
					否
				</c:if>
				
			</td>
		</tr>
		<c:if test="${payLogDetailBean.payCancelState == 'Y'}">
		<tr>
			<td class="tdLeft" >付款取消日期</td>
			<td class="tdRight">
				${payLogDetailBean.payCancelDate}
			</td>
		</tr>
		</c:if>
	</table>
	<br/>
	<div>
   	 	<input type="button" value="关闭" onclick="art.dialog.close();">
	</div>
</body>
</html>