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
	countTotal('subInvoiceAmt');
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
	$("#advanceCancelAmtSpan").html(countTotal.toFixed(2));
}
function countAmt(){
	var total = 0;
	$("#cancelTable").find("input[name='advancePayCancelAmts']").each(function(){
		total=parseFloat(total)+parseFloat($(this).val());
	});
	$("#cancelAmtsTotalSpan").html(total);
	$("#cancelAmtSpan").html(total.toFixed(2));
}
//校验预付款设备取消金额必须小于发票分配金额
function validateThisAdvDevCancelAmt(){
	$("#cancelTable").find("input[name='advancePayCancelAmts']").each(function(){
		var money = parseFloat($(this).parent().parent().find("#subInvoiceAmt").val());
		 if(parseFloat($(this).val())>money){
			App.notyError("预付款核销设备取消金额必须小于等于发票分配金额"+money+"！");
			$(this).focus();
			return false;
		 }
	});
	return true;
}
//确定
function confirm(){
	//判断有填写至少一条的发票核销取消金额
	var n = 0;
	$("input[name='advancePayCancelAmts']").each(function(){
		if($(this).val() != 0){
			n++;
		}
	});
	if(n==0){
		App.notyError("请填写至少一条物料的取消金额，再确认！");
		return ;
	}
	if(!validateThisAdvDevCancelAmt()){
		return false;
	}
	App.ajaxSubmit("pay/paycancel/cancelDevice.do?<%=WebConsts.FUNC_ID_KEY %>=03030804",{
		data : $('#cancelForm').serialize(),
		async : false
	}, function(data) {
		if(data.flag){
			art.dialog.data('cancelAmtSpan',$("#cancelAmtSpan").html());
			art.dialog.close();	  
		}
	});
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
		<th colspan="5">预付款核销信息</th>
	</tr>
	<tr>
		<th width="15%">预付款批次</th>
		<th width="20%">预付款金额(元)</th>
		<th width="20%">已核销金额(元)</th>
		<th width="20%">本次核销金额(元)</th>
		<th width="25%">本次核销取消金额【累计取消金额=<span id="cancelAmtsTotalSpan">0</span>】</th>
	</tr>
		<tr>
			<td>${payInfo.advancePayId}</td>
			<td><fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/></td>
			<td><fmt:formatNumber type="number" value="${payInfo.cancelAmt}" minFractionDigits="2"/></td>
			<td>
				<span id="advanceCancelAmtSpan">0.00</span>
			</td>
			<td>
				<span id="cancelAmtSpan">0.00</span>
			</td>
		</tr>
</table>
<table id="cancelTable" class="tableList">
	<tr>
		<th colspan="11">合同采购设备(${payInfo.advancePayId }预付款核销)</th>
	</tr>
	<tr>
		<th width="8%">核算码</th>
		<th width="7%">项目</th>
		<th width="9%">物料名称</th>
		<th width="9%">设备型号</th>
		<th width="9%">总金额</th>
		<th width="9%">已付金额</th>
		<th width="9%">冻结金额</th>
		<th width="13%">发票分配金额(元)</th>
		<th width="10%">增值税金额</th>
		<th width="10%">发票行说明</th>
		<th width="7%">取消金额</th>
	</tr>
	<c:forEach items="${devices}" var="bean">
		<tr>
			<td>${bean.cglCode }
				<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" />
			</td>
			<td>${bean.projName }</td>
			<td>${bean.matrName }</td>
			<td>${bean.deviceModelName }</td>
			<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
			<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/>
			</td>
			<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
			<td>${bean.subInvoiceAmt }<input type="hidden" id='subInvoiceAmt' name="subInvoiceAmt" value="${bean.subInvoiceAmt }"></td>
			<td>${bean.addTaxAmt }</td>
			<td>${bean.ivrowMemo }</td>
			<td><input  name="advancePayCancelAmts" onkeyup="$.clearNoNum(this);" onblur="validateThisAdvDevCancelAmt();countAmt();$.onBlurReplace(this);" valid errorMsg="请输入取消金额。" value="0" style="width: 90px;" maxlength="18" class="base-input-text"/></td>
		</tr>
	</c:forEach>
</table>
<br>
<div align="center" style="margin-top: 10px;">
	<input type="button" value="确定" onclick="return confirm();">
	<input type="button" value="关闭" onclick="art.dialog.close();">
</div>
</form>
</body>
</html>