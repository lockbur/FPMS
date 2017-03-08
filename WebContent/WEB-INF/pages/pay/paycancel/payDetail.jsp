<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>正常付款明细</title>
<script type="text/javascript">
function  pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
}
//预付款核销明细查询
function advanceCancelDetail(advancePayId)
{
	<%-- var form=$("#payDetailForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payquery/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030704&advancePayId="+advancePayId;
	App.submit(form);	 --%>
	
	var url ="<%=request.getContextPath()%>/pay/payquery/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030704&advancePayId="+advancePayId;
// 	App.submitShowProgress();
// 	window.showModalDialog(url, null, "dialogHeight=500px;dialogWidth=900px;center=yes;status=no;");
// 	App.submitFinish();
	$.dialog.open(
		url,
		{
			width: "60%",
			height: "80%",
			lock: true,
		    fixed: true,
		    title:"预付款详情",
		    id:"dialogAdvCancelInfoDetail",
			close: function(){
			}		
		}
	)
}
function doValidate() {
	//提交前调用
	if(!App.valid("#payDetailForm")){
		return;
	}
	if(!$.checkMoney($("#cancelInvoAmt").val())){
		App.notyError("发票取消金额格式有误！最多含两位小数的18位正浮点数。");
		$("#cancelInvoAmt").focus();
		return false;
	}
	if(!$.checkMoney($("#cancelAdvClAmt").val())){
		App.notyError("预付款核销取消金额格式有误！最多含两位小数的18位正浮点数。");
		$("#cancelAdvClAmt").focus();
		return false;
	}
	if(!$.checkMoney($("#cancelPayAmt").val())){
		App.notyError("付款取消金额格式有误！最多含两位小数的18位正浮点数。");
		$("#cancelPayAmt").focus();
		return false;
	}
	if(!$.checkMoney($("#cancelSusAmt").val())){
		App.notyError("暂收取消金额格式有误！最多含两位小数的18位正浮点数。");
		$("#cancelSusAmt").focus();
		return false;
	}
	//校验发票取消金额=预付款核销取消金额+付款取消金额+暂收取消金额
	if(!vaidateCancelAmt()){
		App.notyError("发票取消金额必须等于预付款核销取消金额+付款取消金额+暂收取消金额！");
		//$("#cancelInvoAmt").focus();
		return false;
	}
	//校验预付款核销取消金额=预付款本次核销金额之和
	if(!vaidateCancelAdvAmt1()){
		App.notyError("预付款核销取消金额必须等于预付款本次核销金额之和！");
		//$("#cancelAdvClAmt").focus();
		return false;
	}
	//预付核销取消金额=预付款设备取消金额之和
	if(!vaidateCancelAdvAmt2()){
		App.notyError("预付款核销取消金额必须等于预付款设备取消金额之和！");
		//$("#cancelAdvClAmt").focus();
		return false;
	}
	//付款取消金额+暂收取消金额=正常付款设备取消金额之和
	if(!vaidateCancelPayAmt()){
		App.notyError("付款取消金额+暂收取消金额必须等于正常付款设备取消金额之和！");
		//$("#cancelPayAmt").focus();
		return false;
	}
	if(!validateInvoCancelAmt()){
		return false;
	}
	if(!validateThisAdvCancelAmt()){
		return false;
	}
	if(!validateThisAdvDevCancelAmt()){
		return false;
	}
	if(!validateThisDevCancelAmt()){
		return false;
	}
	if(!validateAdvAmt()){
		return false;
	}
	if(!validatePayAmt()){
		return false;
	}
	if(!validateSusAmt()){
		return false;
	}

	return true;
}
//暂收取消金额必须小于等于暂收金额
function validateSusAmt(){
	if(parseFloat($("#cancelSusAmt").val())>parseFloat($("#suspenseAmt").val())){
		App.notyError("暂收取消金额必须小于等于暂收金额"+$("#suspenseAmt").val()+"！");
		$("#cancelSusAmt").focus();
		return false;
	}
	return true;
}
//付款取消金额必须小于等于付款金额
function validatePayAmt(){
	if(parseFloat($("#cancelPayAmt").val())>parseFloat($("#payAmt").val())){
		App.notyError("付款取消金额必须小于等于付款金额"+$("#payAmt").val()+"！");
		$("#cancelPayAmt").focus();
		return false;
	}
	return true;
}

//校验预付款核销取消金额必须小于等于预付款核销金额
function validateAdvAmt(){
	if(parseFloat($("#cancelAdvClAmt").val())>parseFloat($("#advanceCancelAmt").val())){
		App.notyError("预付款核销取消金额必须小于等于预付款核销金额"+$("#advanceCancelAmt").val()+"！");
		$("#cancelAdvClAmt").focus();
		return false;
	}
	return true;
}

//校验发票取消金额必须小于等于发票金额
function validateInvoCancelAmt(){
	var invoiceAmt = parseFloat($("#invoiceAmt").val());
	var cancelInvoAmt =parseFloat($("#cancelInvoAmt").val());
	if(cancelInvoAmt>invoiceAmt){
		App.notyError("发票取消金额必须小于等于发票金额"+$("#invoiceAmt").val()+"！");
		$("#cancelInvoAmt").focus();
		return false;
	}
	return true;
}
//校验发票取消金额=预付款核销取消金额+付款取消金额+暂收取消金额
function vaidateCancelAmt(){
	var cancelInvoAmt =parseFloat($("#cancelInvoAmt").val());
	var cancelAdvClAmt =parseFloat($("#cancelAdvClAmt").val());
	var cancelPayAmt =parseFloat($("#cancelPayAmt").val());
	var cancelSusAmt =parseFloat($("#cancelSusAmt").val());
	if(cancelInvoAmt.toFixed(2)==(cancelAdvClAmt+cancelPayAmt+cancelSusAmt).toFixed(2)){
		return true;
	}
	return false;
}
//校验预付款核销取消金额=预付款本次核销金额之和
function vaidateCancelAdvAmt1(){
	var cancelAdvClAmt =parseFloat($("#cancelAdvClAmt").val());
	var preCancelTotalAmt = 0;
	$("#prePayCancleTr").find("input[name='preCancelAmts']").each(function(){
		preCancelTotalAmt += parseFloat($(this).val());
	});
	if(cancelAdvClAmt.toFixed(2) == preCancelTotalAmt.toFixed(2)){
		return true;
	}
	return false;
}
//预付核销取消金额=预付款设备取消金额之和
function vaidateCancelAdvAmt2(){
	var cancelAdvClAmt =parseFloat($("#cancelAdvClAmt").val());
	var advancePayCancelTotalAmt = 0;
	$("#advancePayCancelTa").find("input[name='advancePayCancelAmts']").each(function(){
		advancePayCancelTotalAmt += parseFloat($(this).val());
	});
	if(cancelAdvClAmt.toFixed(2) == advancePayCancelTotalAmt.toFixed(2)){
		return true;
	}
	return false;
}
//付款取消金额+暂收取消金额=正常付款设备取消金额之和
function vaidateCancelPayAmt(){
	var cancelPayAmt =parseFloat($("#cancelPayAmt").val());
	var cancelSusAmt =parseFloat($("#cancelSusAmt").val());
	var payCancelTotalAmt = 0;
	$("#payCancelTa").find("input[name='payCancelAmts']").each(function(){
		payCancelTotalAmt += parseFloat($(this).val());
	});
	if((cancelPayAmt+cancelSusAmt).toFixed(2) == payCancelTotalAmt.toFixed(2)){
		return true;
	}
	return false;
}
//校验预付款核销本次取消金额必须小于等于本次核销金额
function validateThisAdvCancelAmt(){
	$("#prePayCancleTr").find("input[name='preCancelAmts']").each(function(){
		var money = parseFloat($(this).parent().parent().find("#preCancelAmt").val());
		if(parseFloat($(this).val())>money){
			App.notyError("预付款核销本次取消金额必须小于等于预付款核销信息的本次核销金额"+money+"！");
			$(this).focus();
			return false;
		}
	});
	return true;
}
//校验预付款设备取消金额必须小于发票分配金额
function validateThisAdvDevCancelAmt(){
	$("#advancePayCancelTa").find("input[name='advancePayCancelAmts']").each(function(){
		var money = parseFloat($(this).parent().parent().find("#preSubInvoiceAmt").val());
		 if(parseFloat($(this).val())>money){
			App.notyError("预付款设备取消金额必须小于等于发票分配金额"+money+"！");
			$(this).focus();
			return false;
		 }
	});
	return true;
}
//校验正常付款设备取消金额必须小于发票分配金额
function validateThisDevCancelAmt(){
	$("#payCancelTa").find("input[name='payCancelAmts']").each(function(){
		 var money = parseFloat($(this).parent().parent().find("#norSubInvoiceAmt").val());
		 if(parseFloat($(this).val())>money){
			App.notyError("正常付款设备取消金额必须小于等于发票分配金额"+money+"！");
			$(this).focus();
			return false;
		 }
	});
	return true;
}
//统计正常付款设备的取消金额
function countAmt(name,spanId){
	var countTotal = 0;
	$("input[name='"+name+"']").each(function(){
		if($(this).val()){
			var thisVal=parseFloat($(this).val());
			countTotal=parseFloat(countTotal);
			countTotal=parseFloat(thisVal+countTotal);
		}
	});
	$("#"+spanId).html(countTotal);
}

/* function clearNoNum(obj,isNegative){
	//响应鼠标事件，允许左右方向键移动 
    var event = window.event || event;
    if (event.keyCode == 37 | event.keyCode == 39 || event.keyCode == 9) {
        return;
    }
    if(isNegative){
		obj.value = obj.value.replace(/[^\d.-]/g,""); //清除"数字"和".","-"以外的字符
		obj.value = obj.value.replace(/\-{2,}/g,"-"); //只保留第一个. 清除多余的
		obj.value = obj.value.replace("-","$#$").replace(/\-/g,"").replace("$#$","-");
		//确保中间不会出现"-"
		if(obj.value.substr(0,obj.value.indexOf("-")).length>0){
			obj.value = obj.value.replace(/-/g,""); 
		}
    }else{
		obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    }
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是点
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}
function onBlurReplace(obj){
	var _value = $(obj).val();
    //最后一位是小数点的话，移除
    //$(obj).val(_value.replace(/\.$/g, ""));
    //如果第一位是0，且0后面不是小数点
    if(_value){
		$(obj).val(parseFloat(_value));
    }
}  */

//付款取消
function cancelPay(button, url){
// 	App.submitForm(button, url);
if(!doValidate()){
	return false;
}
		var form = $('#payDetailForm')[0];
		form.action = url;
		App.submit(form);
	/* doValidate();
	$( "<div>确认要取消吗?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submitForm(button, url);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	}); */
	<%-- var form = $("#payDetailForm");
	form.attr('action', '<%=request.getContextPath()%>/pay/paycancel/cancelPay.do?<%=WebConsts.FUNC_ID_KEY%>=03030802&payId='+payId);
	App.submit(form); --%>
}
//计算发票金额
function countInvoiceAmt(obj){
	//如果输入的不合法则不进行相加运算
	var cancelAdvClAmt=parseFloat($("#cancelAdvClAmt").val());
	var cancelPayAmt=parseFloat($("#cancelPayAmt").val());
	var cancelSusAmt=parseFloat($("#cancelSusAmt").val());
	var invoiceAmtSpan=parseFloat(cancelAdvClAmt+cancelPayAmt+cancelSusAmt);
	$("#cancelInvoAmt").val(invoiceAmtSpan.toFixed(2));
	$("#cancelInvoAmtSpan").html(invoiceAmtSpan);
}
</script>
</head>

<body>
<p:authFunc funcArray="03030802"/>
<form action="" id="payDetailForm" method="post">
<p:token/>
<table>
	<tr>
		<td>
			<table >
				<tr class="collspan-control">
					<th  colspan="4"><b>合同信息</b></th>
				</tr>
				<tr>
					<td class="tdLeft" width="20%"><span class="spanFont">合同号</span></td>
					<td class="tdRight" width="30%">
						 <c:out value="${payInfo.cntNum}"></c:out>
						 <input type="hidden" id="cntNum" name="cntNum" value="${payInfo.cntNum}"/>
					</td>
					<td class="tdLeft" width="20%"><span class="spanFont">进度</span></td>
					<td class="tdRight" width="30%">
						<fmt:parseNumber value="${payInfo.normarlTotalAmt+payInfo.advanceTotalAmt}" var="a"/>
						<fmt:parseNumber value="${payInfo.cntAllAmt}" var="b"/>
						<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
					</td>
				</tr>
				<tr>
					<td class="tdLeft" width="20%"><span class="spanFont">合同金额</span></td>
					<td class="tdRight" width="30%">
						<fmt:formatNumber type="number" value="${payInfo.cntAmt}" minFractionDigits="2"/>元  <br>
						其中质保金：${payInfo.zbAmt}%
					</td>
					<td class="tdLeft" width="20%"><span class="spanFont">合同类型</span></td>
					<td class="tdRight" width="30%">
						<c:if test="${payInfo.cntType=='0'}">
					    	<c:out value="资产类"></c:out>
					   	</c:if>
					    <c:if test="${payInfo.cntType=='1'}">
					    	<c:out value="费用类"></c:out>
					    </c:if>
					</td>
				</tr>
				<tr>
						<td class="tdLeft">正常付款金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.normarlTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">预付款总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.advanceTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">冻结金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.freezeTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.suspenseTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
					</tr>
			</table>
		</td>
	</tr>
	<tr>
			<td></td>
	</tr>
	<tr>
		<td>
			<table>
					<tr class="collspan-control">
						<th colspan="4">付款单号：${payInfo.payId}
							<input type="hidden" id="payId" name="payId" value="${payInfo.payId}"/>
							<input type="hidden" id="isPrePay" name="isPrePay" value="${selectInfo.isPrePay}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft" ><span class="spanFont">发票号</span></td>
						<td class="tdRight" colspan="3">
							<c:out value="${payInfo.invoiceId}"></c:out>
						</td>
						
					</tr>
					<tr>
						<td class="tdLeft">是否贷项通知单</td>
						<td class="tdRight">
							<c:if test="${payInfo.isCreditNote=='0'}">
								<c:out value="是"></c:out>
							</c:if>
							<c:if test="${payInfo.isCreditNote=='1'}">
								<c:out value="否"></c:out>
							</c:if>
						</td>
						<td class="tdLeft"><span class="spanFont">附件张数</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.attachmentNum}"></c:out>张
						</td>
					</tr>
					<tr>
						<td class="tdLeft"><span class="spanFont">收款单位</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.providerName}"></c:out>
						</td>
						<td class="tdLeft"><span class="spanFont">收款账号</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.provActNo}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="tdLeft"><span class="spanFont">开户行信息</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.bankInfo}"></c:out>
						</td>
						<c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}">
							<td class="tdLeft"><span class="spanFont">付款方式</span></td>
							<td class="tdRight">
								<c:out value="${payInfo.payModeName}"></c:out>
							</td>
						</c:if>
						<c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">
							<td class="tdLeft"></td>
							<td class="tdRight"></td>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft"><span class="spanFont">发票金额(=预付款核销金额+付款金额+暂收金额)</span></td>
						<td class="tdRight" >
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft" ></td>
						<td class="tdRight">
						</td>
					</tr>
					<tr>
						<td class="tdRight" colspan="4" style="text-align: center;padding: 0" >
							<%-- <span class="spanFont">预付款核销金额</span>
							【<fmt:formatNumber type="number" value="${payInfo.advanceCancelAmt}" minFractionDigits="2"/>】元
							&nbsp;&nbsp;<span class="spanFont">付款金额</span>
							【<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>】元&nbsp;&nbsp;
							<span class="spanFont" >暂收金额</span>
							<c:if test="${!empty payInfo.suspenseAmt}">
								【<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>】元&nbsp;&nbsp;
							</c:if>
							<c:if test="${empty payInfo.suspenseAmt}">
								【<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元】&nbsp;&nbsp;
							</c:if> --%>
							<table style="border: 0">
								<tr>
									<td class="tdLeft" style="border-bottom: 0;border-top: 0;border-left: 0"><span class="spanFont">预付款核销金额</span></td>
									<td class="tdRight" style="border-bottom: 0;border-top: 0;width: 13%"><fmt:formatNumber type="number" value="${payInfo.advanceCancelAmt}" minFractionDigits="2"/>元</td>
									<td class="tdLeft" style="border-bottom: 0;border-top: 0"><span class="spanFont">付款金额</span></td>
									<td class="tdRight" style="border-bottom: 0;border-top: 0;width: 13%"><fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>元</td>
									<td class="tdLeft" style="border-bottom: 0;border-top: 0"><span class="spanFont">暂收金额</span></td>
									<td class="tdRight" style="border-bottom: 0;border-top: 0;border-right:0;width: 13%">
										<c:if test="${!empty payInfo.suspenseAmt}">
											<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>元
										</c:if>
										<c:if test="${empty payInfo.suspenseAmt}">
											<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
										</c:if>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票取消金额<span class="red">*</span></td>
						<td class="tdRight">
							<span id="cancelInvoAmtSpan">0</span>
							<input type="hidden" id="cancelInvoAmt" name="cancelInvoAmt" onkeyup="$.clearNoNum(this);" onblur="validateInvoCancelAmt();$.onBlurReplace(this);" maxlength="18" class="base-input-text" valid errorMsg="请输入发票取消金额。"/>
						</td>
						<td class="tdLeft"></td>
						<td class="tdRight"></td>
<!-- 						<td class="tdLeft">预付款核销取消金额<span class="red">*</span></td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							<input id="cancelAdvClAmt" name="cancelAdvClAmt" onchange="validateAdvAmt();" <c:if test="${payInfo.advanceCancelAmt==0}">readonly="readonly" style="color: rgb(84, 84, 84);"</c:if>  maxlength="18" class="base-input-text" value="0" valid errorMsg="请输入预付款核销取消金额。"/> --%>
<!-- 						</td> -->
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">付款取消金额<span class="red">*</span></td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							<input id="cancelPayAmt" name="cancelPayAmt" onchange="validatePayAmt();" <c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">readonly="readonly" style="color: rgb(84, 84, 84);"</c:if> maxlength="18" class="base-input-text" value="0" valid errorMsg="请输入付款取消金额。"/> --%>
<!-- 						</td> -->
<!-- 						<td class="tdLeft">暂收取消金额<span class="red">*</span></td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							<input id="cancelSusAmt" name="cancelSusAmt" onchange="validateSusAmt();" <c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">readonly="readonly" style="color: rgb(84, 84, 84);"</c:if> maxlength="18" class="base-input-text" value="0" valid errorMsg="请输入暂收取消金额。"/> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<td class="tdLeft" colspan="4">
							预付款核销取消金额<span class="red">*</span><input id="cancelAdvClAmt" name="cancelAdvClAmt" onkeyup="$.clearNoNum(this);" onblur="countInvoiceAmt(this);validateAdvAmt();$.onBlurReplace(this);" <c:if test="${payInfo.advanceCancelAmt==0}">readonly="readonly" style="color: rgb(84, 84, 84);"</c:if>  maxlength="18" class="base-input-text" value="0" valid errorMsg="请输入预付款核销取消金额。"/>
							付款取消金额<span class="red">*</span><input id="cancelPayAmt" name="cancelPayAmt" onkeyup="$.clearNoNum(this);" onblur="countInvoiceAmt(this);validatePayAmt();$.onBlurReplace(this);" <c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">readonly="readonly" style="color: rgb(84, 84, 84);"</c:if> maxlength="18" class="base-input-text" value="0" valid errorMsg="请输入付款取消金额。"/>
							暂收取消金额<span class="red">*</span><input id="cancelSusAmt" name="cancelSusAmt" onkeyup="$.clearNoNum(this);" onblur="countInvoiceAmt(this);validateSusAmt();$.onBlurReplace(this);" <c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">readonly="readonly" style="color: rgb(84, 84, 84);"</c:if> maxlength="18" class="base-input-text" value="0" valid errorMsg="请输入暂收取消金额。"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${payInfo.advanceCancelAmt!=0}">
			<tr>
			<td></td>
		   </tr>
			<tr>
				<td>
					<table id="prePayCancleTr" class="tableList">
						<tr class="collspan-control">
							<th colspan="6"><b>预付款核销信息【预付款核销总金额=<span id="advanceCancelAmtSpan">${payInfo.advanceCancelAmt}</span>】</b></th>
						</tr>
						<tr>
							<th width="20%">预付款批次</th>
							<th width="15%">预付款金额</th>
							<th width="15%">已核销金额</th>
							<th width="20%">本次核销金额【累计金额=<span id="">${payInfo.advanceCancelAmt}</span>】</th>
							<th width="20%">本次核销取消金额【累计金额=<span id="cancelAmtsTotalSpan">0</span>】</th>
							<th width="10%">操作</th>
						</tr>
						<c:forEach items="${prePayCancleList}" var="sedList">
						<tr >
							<td>
								<c:out value="${sedList.advancePayId}"></c:out>
								<input type="hidden" id="advancePayIds" name="advancePayIds" value="${sedList.advancePayId}"/>
							</td>
							<td class="tdr">
								<fmt:formatNumber type="number" value="${sedList.payAmt}" minFractionDigits="2"/>
							</td>
							<td class="tdr">
								<fmt:formatNumber type="number" value="${sedList.cancelAmtTotal}" minFractionDigits="2"/>
							</td>
							<td class="tdr">
								<fmt:formatNumber type="number" value="${sedList.cancelAmt}" minFractionDigits="2"/>
								<input type="hidden" id="preCancelAmt" name="preCancelAmt" value="${sedList.cancelAmt}"/>
							</td>
							<td class="tdr">
								<input id="preCancelAmts" name="preCancelAmts" onkeyup="$.clearNoNum(this);" onblur="validateThisAdvCancelAmt();countAmt('preCancelAmts','cancelAmtsTotalSpan');$.onBlurReplace(this);" maxlength="18" class="base-input-text" style="width: 90%" value="0" valid errorMsg="请输入本次核销取消金额。"/>
							</td>
							<td align="center">
								<div class="detail" align="center">
									<a href="#" onclick="advanceCancelDetail('${sedList.advancePayId}');" title="明细"></a>
								</div>
							</td>
						</tr>	
						</c:forEach>
					</table>
					<table id="advancePayCancelTa" class="tableList">
						<tr class="collspan-control">
						<th colspan="11">合同采购设备(预付款)【总金额=<span id="advanceCancelAmtTotalSpan">${payInfo.advanceCancelAmt}</span>】</th>
						</tr>
						<tr>
							<th width="8%">核算码</th>
							<th width="7%">项目</th>
							<th width="9%">物料名称</th>
							<th width="9%">设备型号</th>
							<th width="9%">总金额</th>
							<th width="9%">已付金额</th>
							<th width="9%">冻结金额</th>
							<th width="13%">发票分配金额(元)<br>【累计金额=<span id="">${payInfo.advanceCancelAmt}</span>】</th>
							<th width="10%">增值税金额</th>
							<th width="10%">发票行说明</th>
							<th width="7%">取消金额【累计金额=<span id="advancePayInvAmtsTotalSpan">0</span>】</th>
						</tr>
						<c:forEach items="${prePayDeviceList}" var="bean">
							<tr>
								<td>${bean.cglCode }<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" /></td>
								<td>${bean.projName }</td>
								<td>${bean.matrName }</td>
								<td>${bean.deviceModelName }</td>
								<td class="tdr"><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
								<td class="tdr"><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
								<td class="tdr"><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
								<td class="tdr">
									<fmt:formatNumber type="number" value="${bean.subInvoiceAmt}" minFractionDigits="2"/>
									<input type="hidden" id="preSubInvoiceAmt" name="preSubInvoiceAmt" value="${bean.subInvoiceAmt}"/>	
								</td>
								<td class="tdr"><fmt:formatNumber type="number" value="${bean.addTaxAmt}" minFractionDigits="2"/></td>
								<td>${bean.ivrowMemo}</td>
								<td><input id="advancePayCancelAmts" name="advancePayCancelAmts" onkeyup="$.clearNoNum(this);" onblur="validateThisAdvDevCancelAmt();countAmt('advancePayCancelAmts','advancePayInvAmtsTotalSpan');$.onBlurReplace(this);" valid errorMsg="请输入取消金额。" value="0" style="width: 90px;" maxlength="18" class="base-input-text"/></td>
							</tr>
						</c:forEach>		
					</table>
				</td>
			</tr>
		</c:if>
		<c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				
				<table id="payCancelTa" class="tableList">
					<tr class="collspan-control">
						<th colspan="11">合同采购设备(正常付款金额=付款金额+暂收金额)【正常付款总金额=<span id="payAmtTotalSpan">${payInfo.payAmt+payInfo.suspenseAmt}</span>】</th>
					</tr>
					<tr>
						<th width="8%">核算码</th>
						<th width="7%">项目</th>
						<th width="9%">物料名称</th>
						<th width="9%">设备型号</th>
						<th width="9%">总金额</th>
						<th width="9%">已付金额</th>
						<th width="9%">冻结金额</th>
						<th width="13%">发票分配金额<br>【累计金额=<span id="">${payInfo.payAmt+payInfo.suspenseAmt}</span>】</th>
						<th width="10%">增值税金额</th>
						<th width="10%">发票行说明</th>
						<th width="7%">取消金额<br>【累计金额=<span id="payCancelAmtsTotalSpan">0</span>】</th>
					</tr>
					<c:forEach items="${payDeviceList}" var="bean">
						<tr>
							<td>${bean.cglCode }<input type="hidden" id="subIds" name="subIds" value="${bean.subId}" /></td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.deviceModelName }</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td class="tdr">
								<fmt:formatNumber type="number" value="${bean.subInvoiceAmt}" minFractionDigits="2"/>
								<input type="hidden" id="norSubInvoiceAmt" name="norSubInvoiceAmt" value="${bean.subInvoiceAmt}"/>	
							</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.addTaxAmt}" minFractionDigits="2"/></td>
							<td>${bean.ivrowMemo}</td>
							<td><input id="payCancelAmts" name="payCancelAmts" onblur="validateThisDevCancelAmt();countAmt('payCancelAmts','payCancelAmtsTotalSpan');$.onBlurReplace(this);" onkeyup="$.clearNoNum(this,true);"  value="0" valid errorMsg="请输入取消金额。"  style="width: 70px;" maxlength="18" class="base-input-text"/></td>
						</tr>
					</c:forEach>			
				</table>
				<table>
					<tr class="collspan-control">
						<th colspan="4">正常付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">付款金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${payInfo.payDate}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.suspenseAmt}">
								<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.suspenseAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }">
							<td class="tdLeft">暂收付款日期</td>
							<td class="tdRight">${payInfo.suspenseDate }</td>
						</c:if>
						<c:if test="${payInfo.suspenseAmt == null || payInfo.suspenseAmt ==0 }">
							<td class="tdLeft"></td>
							<td class="tdRight"></td>
						</c:if>
						
					</tr>
					<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }">
					<tr>
						<td class="tdLeft">暂收项目</td>
						<td class="tdRight">
							${payInfo.suspenseName}
						</td>
						<td class="tdLeft">预处理时间</td>
						<td class="tdRight">
							${payInfo.suspensePeriod}月
						</td>
					</tr>
					</c:if>
					<tr>
					</tr>
					<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }">
					<tr>
						<td class="tdLeft">挂帐原因</td>
						<td class="tdRight" colspan="3" >
							${payInfo.suspenseReason}
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3">
							${payInfo.invoiceMemo}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</c:if>
	</table>			
	<br>
	<div style="text-align:center;" >
		<!-- <input type="button" value="取消付款" onclick="cancelPay(button, url);"> -->
		<p:button funcId="03030802" value="取消付款" onclick="cancelPay"/>
		<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</form>
</body>
</html>