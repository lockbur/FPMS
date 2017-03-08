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
<title>预付款明细</title>
</head>
<body>
	<table>
		<tr>
			<th colspan="4">预付款明细信息</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">预付款单号</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.payId}
			</td>
			<td class="tdLeft" width="25%">合同号</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.cntNum }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">发票号</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.invoiceId}
			</td>
			<td class="tdLeft" width="25%">附件张数</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.attachmentNum}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">供应商</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.providerName}
			</td>
			<td class="tdLeft" width="25%">银行帐号</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.provActNo}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">币别</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.provActCurr}
			</td>
			<td class="tdLeft" width="25%">发票金额</td>
			<td class="tdRight" width="25%">
				<fmt:formatNumber type="number" value="${payAdvCancelInfo.invoiceAmt}" minFractionDigits="2"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">发票说明</td>
			<td class="tdRight" colspan="3" >
				${payAdvCancelInfo.invoiceMemo}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">付款金额</td>
			<td class="tdRight" width="25%">
				<fmt:formatNumber type="number" value="${payAdvCancelInfo.payAmt}" minFractionDigits="2"/>
			</td>
			<td class="tdLeft" width="25%">付款日期</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.payDate}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">状态</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.payDataFlagName}
			</td>
			<td class="tdLeft" width="25%">发票状态</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.dataFlagInvoiceName}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">付款状态</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.dataFlagPayName}
			</td>
			<td class="tdLeft" width="25%">创建责任中心</td>
			<td class="tdRight" width="25%">
				${payAdvCancelInfo.instDutyName}
			</td>
		</tr>
	</table>
	<br/>
	<div>
    	<input type="button" value="关闭" onclick="art.dialog.close();">
	</div>
</body>
</html>