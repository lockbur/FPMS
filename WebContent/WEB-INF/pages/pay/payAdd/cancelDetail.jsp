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
<title>核销</title>

<script type="text/javascript">
function pageInit() {
	countTotal('subInvoiceAmt','advanceCancelAmtTotalSpan');
}

function doValidate() {
	
}
//计算核销总金额、正常付款发票总金额、预付款发票总金额
function countTotal(name,parentId){
	//先检查输入的金额是否合法
	var countTotal = 0;
	$("input[name='"+name+"']").each(function(){
		if($(this).val()){
			var thisVal=parseFloat($(this).val());
			countTotal=parseFloat(countTotal);
			countTotal=parseFloat(thisVal+countTotal);
		}
	});
	$("#"+parentId).html(countTotal);
	$("#advanceCancelAmtSpan").html(countTotal.toFixed(2));
}

</script>
</head>

<body>
<form action="" id="cancelForm">
<input type="hidden" id="payId" name="payId" value="${payInfo.payId }"/>
<input type="hidden" id="advancePayId" name="advancePayId" value="${payInfo.advancePayId }"/>
<input type="hidden" id="cntNum" name="cntNum" value="${payInfo.cntNum }"/>
<table id="advCancelId" class="tableList">
	<tr class="collspan-control">
		<th colspan="4">预付款核销信息</th>
	</tr>
	<tr>
		<th width="20%">预付款批次</th>
		<th width="20%">预付款金额(元)</th>
		<th width="25%">已核销金额(元)</th>
		<th width="25%">本次核销金额(元)</th>
	</tr>
		<tr>
			<td>${payInfo.advancePayId}</td>
			<td><fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/></td>
			<td><fmt:formatNumber type="number" value="${payInfo.cancelAmt}" minFractionDigits="2"/></td>
			<td>
				<span id="advanceCancelAmtSpan">0</span>
			</td>
		</tr>
</table>
<table id="cancelTable" class="tableList">
	<tr>
		<th colspan="10">合同采购设备(${payInfo.advancePayId }预付款核销)【本次累计核销金额=<span id="advanceCancelAmtTotalSpan">0</span>】</th>
	</tr>
	<tr>
		<th width="7%">核算码</th>
		<th width="7%">项目</th>
		<th width="9%">物料名称</th>
		<th width="9%">费用部门</th>
		<th width="10%">总金额(元)</th>
		<th width="11%">已付金额(元)</th>
		<th width="11%">冻结金额(元)</th>
		<th width="13%">发票分配金额(元)</th>
		<th width="12%">增值税金额(元)</th>
		<th width="11%">发票行说明</th>
	</tr>
	<c:forEach items="${devices}" var="bean">
		<tr>
			<td>${bean.cglCode }
				<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" />
			</td>
			<td>${bean.projName }</td>
			<td>${bean.matrName }</td>
			<td>${bean.feeDeptName }</td>
			<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
			<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/>
			</td>
			<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
			<td>${bean.subInvoiceAmt }<input type="hidden" name="subInvoiceAmt" value="${bean.subInvoiceAmt }"></td>
			<td>${bean.addTaxAmt }</td>
			<td>${bean.ivrowMemo }</td>
		</tr>
	</c:forEach>
</table>
<br>
<div align="center" style="margin-top: 10px;">
	<input type="button" value="关闭" onclick="art.dialog.close();">
</div>
</form>
</body>
</html>