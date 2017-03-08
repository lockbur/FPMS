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
	countTotal('advancePayInvAmts','advanceCancelAmtTotalSpan');
}

function doValidate() {
	
}
//合同采购设备(预付款)发票行说明校验
function checkAdvPayIvrowMemos(obj){
	var invAmt = $(obj).val();	//发票分配金额
	//如果不为0且不为空,则发票行说明不能为空
	var objIvm =$(obj).parent().parent().find("#advancePayIvrowMemos");
	if(invAmt != null && invAmt != '' && invAmt != 0){
		$(objIvm).attr("valid",'');
		$(objIvm).attr("errorMsg","请输入发票行说明");
	}
	else{
		$(objIvm).removeAttr("valid");
		$(objIvm).removeAttr("errorMsg");
	}
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
//确定
function confirm(){
	//提交前调用
	if(!App.valid("#cancelForm")){
		return;
	}
	//判断有填写至少一条的发票核销金额
	var n = 0;
	$("input[name='advancePayInvAmts']").each(function(){
		if($(this).val() != 0){
			n++;
		}
	});
	if(n==0){
		App.notyError("请填写至少一条物料的发票分配金额，再确认！");
		return ;
	}
	//校验本次核销金额必须小于剩余金额
	var cancelAmt = $("#advanceCancelAmtSpan").html();//本次核销金额
	var remainCancel = $("#remainCancel").val();//剩余金额
	if(parseFloat(cancelAmt)>parseFloat(remainCancel))
	{
		App.notyError("预付款批次号：${payInfo.advancePayId }本次核销金额必须小于等于剩余核销金额(预付款金额-已核销金额)。");
		return ;
	}
	//校验发票分配金额（<=总金额-已付金额-冻结金额）
	var arr2 = 	$("#cancelTable").find("input[name='advancePayInvAmts']");
	var arr3 = 	$("#cancelTable").find("input[name='remainDeviceAmts']");
	for(var i=0;i<arr2.length;i++){
		var payInvAmt = parseFloat($(arr2[i]).val());
		var remainDeviceAmt = parseFloat($(arr3[i]).val());
		if(payInvAmt>remainDeviceAmt)
		{
			App.notyError("合同采购设备对应的发票分配金额必须小于等于设备剩余金额（总金额-已付金额-冻结金额）。");
			$($(arr2[i])).focus();
			return ;
		}
	}
	App.ajaxSubmit("pay/paymodify/cancel.do?<%=WebConsts.FUNC_ID_KEY %>=03030414",{
		data : $('#cancelForm').serialize(),
		async : false
	}, function(data) {
		if(data.flag){
			art.dialog.data('advanceCancelAmt',$("#advanceCancelAmtTotalSpan").html());
			var list =[];
			$("#cancelTable").find("input[name='advancePayInvAmts']").each(function(i){
				list[i]=parseFloat($(this).val())-parseFloat($(this).parent().find("input[name='beforeAdvancePayInvAmts']").val());
			});
			art.dialog.data('list',list);
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
			<td><fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/><input type="hidden" id="remainCancel" name="remainCancel" value="${payInfo.payAmt-payInfo.cancelAmt}"  class="base-input-text"/></td>
			<td><fmt:formatNumber type="number" value="${payInfo.cancelAmtTotal-payInfo.cancelAmt}" minFractionDigits="2"/></td>
			<td>
				<span id="advanceCancelAmtSpan"><fmt:formatNumber type="number" value="${payInfo.cancelAmt}" minFractionDigits="2"/></span>
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
		<th width="9%">设备型号</th>
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
			<td>${bean.deviceModelName }</td>
			<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
			<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/>
				<input type="hidden"  name="remainDeviceAmts" value="${bean.execAmt - bean.payedAmt-bean.freezeAmt+bean.subInvoiceAmt }"/>
			</td>
			<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
			<td>
			<input type="hidden" name="beforeAdvancePayInvAmts" value="${bean.subInvoiceAmt }" />
			<input id="advancePayInvAmts" onkeyup="$.clearNoNum(this);" onblur="checkAdvPayIvrowMemos(this);countTotal('advancePayInvAmts','advanceCancelAmtTotalSpan');$.onBlurReplace(this);" name="advancePayInvAmts" value="${bean.subInvoiceAmt }" maxlength="18"  class="base-input-text" style="width: 100px;" valid errorMsg="请输入预付款设备发票分配金额"/></td>
			<td><input id="advPayAddTaxAmts" name="advPayAddTaxAmts" value="${bean.addTaxAmt }" maxlength="18"  class="base-input-text" style="width: 100px;" valid errorMsg="请输入预付款设备增值税金额" onkeyup="$.clearNoNum(this);" onblur="checkAdvPayAddTaxAmts(this);$.onBlurReplace(this);"/></td>
			<td><input id="advancePayIvrowMemos" name="advancePayIvrowMemos" value="${bean.ivrowMemo }" class="base-input-text" style="width: 100px;" maxlength="250"/></td>
		</tr>
	</c:forEach>
</table>
<br>
<div align="center" style="margin-top: 10px;">
	<input type="button" value="确定" onclick="return confirm();"/>
	<input type="button" value="关闭" onclick="art.dialog.close();">
</div>
</form>
</body>
</html>