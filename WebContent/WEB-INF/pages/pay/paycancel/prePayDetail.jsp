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
<title>预付款明细</title>
<script type="text/javascript">
function  pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
}
//付款取消
function cancelPay(button, url){
// 	App.submitForm(button, url);
if(!doValidate()){
	return false;
}
	var form = $('#payDetailForm')[0];
	form.action = url;
	App.submit(form);
	/* $( "<div>确认要取消吗?</div>" ).dialog({
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
function doValidate() {
	//提交前调用
	if(!App.valid("#payDetailForm")){
		return;
	}
	if(!$.checkMoney($("#preCancelPayAmt").val())){
		App.notyError("预付款取消金额格式有误！最多含两位小数的18位正浮点数。");
		$("#preCancelPayAmt").focus();
		return false;
	}
	if(!validateCancelAmt()){
		return false;
	}
	return true;
}
//预付款取消金额必须小于等于发票金额
function validateCancelAmt(){
	if(parseFloat($("#preCancelPayAmt").val())>parseFloat($("#invoiceAmt").val())){
		App.notyError("预付款取消金额必须小于等于发票金额"+$("#invoiceAmt").val()+"！");
		$("#preCancelPayAmt").focus();
		return false;
	}
	return true;
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
						 <c:out value="${prePayInfo.cntNum}"></c:out>
						 <input type="hidden" id="cntNum" name="cntNum" value="${prePayInfo.cntNum}"/>
					</td>
					<td class="tdLeft" width="20%"><span class="spanFont">进度</span></td>
					<td class="tdRight" width="30%">
						<fmt:parseNumber value="${prePayInfo.normarlTotalAmt+prePayInfo.advanceTotalAmt}" var="a"/>
						<fmt:parseNumber value="${prePayInfo.cntAllAmt}" var="b"/>
						<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
					</td>
				</tr>
				<tr>
					<td class="tdLeft" width="20%"><span class="spanFont">合同金额</span></td>
					<td class="tdRight" width="30%">
						<fmt:formatNumber type="number" value="${prePayInfo.cntAmt}" minFractionDigits="2"/>元  <br>
						其中质保金：${prePayInfo.zbAmt}%
					</td>
					<td class="tdLeft" width="20%"><span class="spanFont">合同类型</span></td>
					<td class="tdRight" width="30%">
						<c:if test="${prePayInfo.cntType=='0'}">
					    	<c:out value="资产类"></c:out>
					   	</c:if>
					    <c:if test="${prePayInfo.cntType=='1'}">
					    	<c:out value="费用类"></c:out>
					    </c:if>
					</td>
				</tr>
				<tr>
						<td class="tdLeft">正常付款金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty prePayInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${prePayInfo.normarlTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty prePayInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">预付款总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty prePayInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${prePayInfo.advanceTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty prePayInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">冻结金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty prePayInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${prePayInfo.freezeTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty prePayInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty prePayInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${prePayInfo.suspenseTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty prePayInfo.suspenseTotalAmt}">
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
						<th colspan="4">
							付款单号：${prePayInfo.payId}
							 <input type="hidden" id="payId" name="payId" value="${prePayInfo.payId}"/>
							 <input type="hidden" id="isPrePay" name="isPrePay" value="${selectInfo.isPrePay}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号</td>
						<td class="tdRight">
							${prePayInfo.invoiceId }
						</td>
						<td class="tdLeft">附件张数</td>
						<td class="tdRight"> 
							${prePayInfo.attachmentNum }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">收款单位</td>
						<td class="tdRight">
							${prePayInfo.providerName }
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							${prePayInfo.provActNo }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
							${prePayInfo.bankInfo }
						</td>
						<td class="tdLeft">币别</td>
						<td class="tdRight">
							${prePayInfo.provActCurr }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${prePayInfo.invoiceAmt}" minFractionDigits="2"/>
							<input type="hidden" id="invoiceAmt" name="invoiceAmt" value="${prePayInfo.invoiceAmt}"/>
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${prePayInfo.payDate }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">付款方式</td>
						<td class="tdRight">
							${prePayInfo.payModeName}
						</td>
						<td class="tdLeft"></td>
						<td class="tdRight">
					
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3" >
							${prePayInfo.invoiceMemo }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">预付款取消金额</td>
						<td class="tdRight">
							<input id="preCancelPayAmt" name="preCancelPayAmt" maxlength="18" onkeyup="$.clearNoNum(this);"  onblur="validateCancelAmt();$.onBlurReplace(this);" class="base-input-text" valid errorMsg="请输入预付款取消金额。"/>
						</td>
						<td class="tdLeft"></td>
						<td class="tdRight">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>			
	<br>
	<div style="text-align:center;" >
		<%-- <input type="button" value="取消付款" onclick="cancelPay('${prePayInfo.payId}');"> --%>
		<p:button funcId="03030802" value="取消付款" onclick="cancelPay"/>
		<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</form>
</body>
</html>