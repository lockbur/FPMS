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
<title>付款新增</title>
<style type="text/css">
	label{
		cursor: pointer;
	}
</style>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	
 	//设置时间插件
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	var nDate = "${nowDate}";
	var date = eval('new Date(' + nDate.replace(/\d+(?=-[^-]+$)/, function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
	var year = date.getFullYear();
 	var month = date.getMonth()+1;
 	if(month<10)
 	{
 		month = "0"+month;
 	}
 	var day = date.getDate();
 	if(day<10)
 	{
 		day = "0"+day;
 	}
	$("#payDate").val(year+"-"+month+"-"+day);
	
}

function providerQuery(){
	url='<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&flag=1&compare='+"common";
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商账号选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('proObj'); 
					if(proObj){
						$("#providerCode").val(proObj.providerCode);
						$("#providerName").val(proObj.providerName);
						$("#providerType").val(proObj.providerType);
						$("#provActNo").val(proObj.actNo);
						$("#provActNoSpan").html(proObj.actNo);
						$("#bankName").val(proObj.bankName);
						$("#bankNameSpan").html(proObj.bankInfo);
						$("#provActCurr").val(proObj.actCurr);
						$("#providerAddr").val(proObj.providerAddr);
						$("#providerAddrCode").val(proObj.providerAddrCode);
						$("#actName").val(proObj.actName);
						$("#bankInfo").val(proObj.bankInfo);
						$("#bankCode").val(proObj.bankCode);
						$("#bankArea").val(proObj.bankArea);
						$("#actType").val(proObj.actType);						
					}		
				}
			}
		 );
}

function doValidate() {
	//提交前调用
	if(!App.valid("#addPayAdvanceForm")){
		return;
	}
	
	if(!checkNum($("#attachmentNum").val()))
	{
		App.notyError("附件数量格式有误！只能是正整数。");
		$("#attachmentNum").focus();
		return false;	
	}
	else if(!$.checkMoney($("#invoiceAmt").val()))
	{
		//金额的校验
		App.notyError("预付款金额格式有误！最多含两位小数的18位正浮点数。");
		$("#invoiceAmt").focus();
		return false;
	}
	if(!validaInvoiceAmt())
	{
		App.notyError("预付款金额不能大于合同剩余金额（合同金额-正常付款金额-预付款金额-冻结金额-暂收金额）。");
		$("#invoiceAmt").focus();
		return false;
	}
	else if($("#invoiceAmt").val()<=0)
	{
		//金额的校验
		App.notyError("预付款金额必须是大于0的正数。");
		$("#invoiceAmt").focus();
		return false;
	}
	//校验供应商类型--只能是外部供应商
	if($("#providerType").val() != 'VENDOR')
	{
		App.notyError("预付款的供应商类型只能是外部供应商。");
		$("#providerName").focus();
		return false;
	}
	//校验附件类型是否有被选中的值
	if(!checkAttaType()){
		App.notyError("请选择附件的类型。");
		return false;
	}
	//发票号
	if(!checkInvoiceId()){
		return false;
	}
	if(!ajaxCheckInvoiceId()){
		return false;
	}
	//发票号去空
	$("#invoiceId").val($.trim($("#invoiceId").val()));
	return true;
}
//校验整数
function checkNum(str)
{
	var reg = /^[0-9]*$/g;	
	if(!reg.test(str))
	{
		return false;
	}
	return true;
}
//校验数字和字母
function checkNumAZ(str)
{
	var reg = /^[0-9A-Za-z]*$/g;	
	if(!reg.test(str))
	{
		return false;
	}
	return true;
}
//校验预付款金额
function validaInvoiceAmt()
{
	var cntAmt = parseFloat($("#cntAmt").val());//合同金额
	var freezeTotalAmt =parseFloat($("#freezeTotalAmt").val());//冻结金额
	var invoiceAmt = parseFloat($("#invoiceAmt").val());//预付款金额
	var normarlTotalAmt = parseFloat($("#normarlTotalAmt").val());//正常金额
	var advanceTotalAmt = parseFloat($("#advanceTotalAmt").val());//预付款金额
	var suspenseTotalAmt = parseFloat($("#suspenseTotalAmt").val());//暂收金额
	if(invoiceAmt>(cntAmt-normarlTotalAmt-advanceTotalAmt-freezeTotalAmt-suspenseTotalAmt))
	{
		return false;
	}
	return true;
}
//校验附件类型是否有被选中的值
function checkAttaType(){
	var i = 0;
	$("#addPayAdvanceForm").find("input[name='attachmentType']").each(function(){
		if($(this).is(':checked')){
			i++;
		}
	});
	if(i>0)
		return true;
	return false;
}
//提交
function submitPay(button, url)
{
	if($("#isOrder").val() == "0"){
		$( "<div>该付款合同是订单类合同，需要做订单补录！！</div>" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					App.submitForm(button, url);
				}
				/* "取消": function() {
					$( this ).dialog( "close" );
				} */
			}
		});
	}else{
		App.submitForm(button, url);
	}

}
//校验两次发票号是否 一致
function checkInvoiceId(){
	var invoiceId = $.trim($("#invoiceId").val());
	var invoiceId1 = $.trim($("#invoiceId1").val());
	if(!$.isBlank(invoiceId) && !$.isBlank(invoiceId1)){
		if(invoiceId != invoiceId1){
			App.notyError("输入的两次发票号不一致，请核对。");
// 			$("#invoiceId1").focus();
			return false;
		}
	}
	return true;
}

//发票号的ajax校验
function ajaxCheckInvoiceId(){
	var n = 0;
	var data={};
	data['invoiceId']= $.trim($("#invoiceId").val());
	data['payId']= $.trim($("#payId").val());
	
	data['flag']= 1 ;
	App.ajaxSubmit("pay/payAdd/ajaxCheckInvoiceId.do?<%=WebConsts.FUNC_ID_KEY %>=03030329",{
		data :data,//$('#addPayForm').serialize(),
		async : false
	}, function(data) {
		if(data.isTrue){
			n =1;
		}else{
			$( "<div>发票号在系统中已存在，请修改！</div>" ).dialog({
				resizable: false,
				height:'auto',
				width:'auto',
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"关闭": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}
	});
	if(n==0){
		return false;
	}else{
		return true;
	}
}
</script>
</head>

<body>
<p:authFunc funcArray="03030304,03030305"/>
<form action="" method="post" id="addPayAdvanceForm">
	<p:token/>
	<table>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">
							合同信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">合同号</td>
						<td class="tdRight" width="30%">
							${payAddInfo.cntNum}
							<input type="hidden" id="isOrder" name="isOrder" value="${payAddInfo.isOrder}"/>
							<input type="hidden" id="cntNum" name="cntNum" value="${payAddInfo.cntNum}"/>
						</td>
						<td class="tdLeft">进度</td>
						<td class="tdRight">
							<fmt:parseNumber value="${payAddInfo.normarlTotalAmt+payAddInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${payAddInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同总金额(人民币)</td>
						<td class="tdRight">
							<input type="hidden" id="cntAmt" name="cntAmt" value="${payAddInfo.cntAllAmt}"/>
							<fmt:formatNumber type="number" value="${payAddInfo.cntAllAmt}" minFractionDigits="2"/><br/>
							其中质保金：${payAddInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							${payAddInfo.cntTypeName}
							<input type="hidden" id="cntType" name="cntType" value="${payAddInfo.cntType}"/>
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">不含税金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payAddInfo.cntAmt}" minFractionDigits="2"/>
						</td>
						<td class="tdLeft">税额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payAddInfo.cntTaxAmt}" minFractionDigits="2"/>
							
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">正常付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.normarlTotalAmt}">
								<input type="hidden" id="normarlTotalAmt" name="normarlTotalAmt" value="${payAddInfo.normarlTotalAmt}"/>
								<fmt:formatNumber type="number" value="${payAddInfo.normarlTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.normarlTotalAmt}">
								<input type="hidden" id="normarlTotalAmt" name="normarlTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">预付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.advanceTotalAmt}">
								<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="${payAddInfo.advanceTotalAmt}"/>
								<fmt:formatNumber type="number" value="${payAddInfo.advanceTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.advanceTotalAmt}">
								<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">冻结金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="${payAddInfo.freezeTotalAmt}"/>
								<fmt:formatNumber type="number" value="${payAddInfo.freezeTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.suspenseTotalAmt}">
								<input type="hidden" id="suspenseTotalAmt" name="suspenseTotalAmt" value="${payAddInfo.suspenseTotalAmt}"/>
								<fmt:formatNumber type="number" value="${payAddInfo.suspenseTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.suspenseTotalAmt}">
								<input type="hidden" id="suspenseTotalAmt" name="suspenseTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
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
							付款单号：${payAddInfo.payId}
							<input type="hidden" id="payId" name="payId" value="${payAddInfo.payId}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票号<span class="red">*</span></td>
						<td class="tdRight" width="30%">
<%-- 							${payAddInfo.invoiceId } --%>
							<input type="text" id="invoiceId" name="invoiceId"  maxlength="50" value="${payAddInfo.payId}" onblur="checkInvoiceId();" class="base-input-text" valid errorMsg="请输入发票号"/>
						</td>
						<td class="tdLeft">确认发票号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceId1" name="invoiceId1" maxlength="50" value="${payAddInfo.payId}" onblur="checkInvoiceId();" class="base-input-text" valid errorMsg="请再次确认输入发票号"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft prompt">附件张数</td>
						<td class="tdRight" colspan="3"> 
							<input type="text" id="attachmentNum" name="attachmentNum" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">附件类型<span class="red">*</span></td>
						<td class="tdRight" colspan="3"> 
							<table>
								<c:forEach items="${listAtTypes}" var="bean" varStatus="status">
									<c:if test="${(status.index)%5==0}">
										<tr>
									</c:if>
									<td style="border: 0">
										<label><input type="checkbox" name="attachmentType" value='${bean.attachmentType }'>${bean.attachmentTypeName }</label>
									</td>
									<c:if test="${(status.index+1)%5==0}">
										</tr>
									</c:if>
								</c:forEach>
							</table>
							<%-- <forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='ANNEX_TYPE'"/> --%>
						</td>
					</tr>
					
					<!-- <tr>
						<td class="tdLeft">收款单位类型</td>
						<td class="tdRight">
							<div class="ui-widget">
								<select class="erp_cascade_select" name="providerType" id="providerType">
									<option value="EMPLOYEE">员工供应商</option>
									<option value="VENDOR">外部供应商</option>
									<option value="IAP">IAP供应商</option>
								</select>
							</div>
						</td>
						<td class="tdLeft"></td>
						<td class="tdRight">
							
						</td>
					</tr> -->
					<tr>
						<td class="tdLeft">收款单位<span class="red">*</span></td>
						<td class="tdRight">
							<input type="hidden" id="providerType"  name="providerType" value="${payAddInfo.providerType}" class="base-input-text"/>
							<input type="hidden" id="providerCode"  name="providerCode" value="${payAddInfo.providerCode}" class="base-input-text"/>
							<input type="hidden" id="providerAddr"  name="providerAddr" value="${payAddInfo.providerAddr}" class="base-input-text"/>
							<input type="hidden" id="providerAddrCode"  name="providerAddrCode" value="${payAddInfo.providerAddrCode}" class="base-input-text"/>
							<input type="hidden" id="actName"  name="actName" value="${payAddInfo.actName}" class="base-input-text"/>
							<input type="hidden" id="bankName" name="bankName"  class="base-input-text" value="${payAddInfo.bankName}"/>
							<input type="hidden" id="bankCode"  name="bankCode" value="${payAddInfo.bankCode}" class="base-input-text"/>
							<input type="hidden" id="bankArea"  name="bankArea" value="${payAddInfo.bankArea}" class="base-input-text"/>
							<input type="hidden" id="actType"  name="actType" value="${payAddInfo.actType}" class="base-input-text"/>
							<input type="text"  id="providerName" name="providerName" value="${payAddInfo.providerName}" class="base-input-text" onclick="providerQuery();" readonly="readonly" valid errorMsg="请输入收款单位"/>
							<%-- <a href="#" onclick="providerQuery();" style="border: 0px;" title="供应商查询">
								<img height="100%" border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
							</a> --%>
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							<span id="provActNoSpan">${payAddInfo.provActNo}</span>
							<input type="hidden" id="provActNo" name="provActNo"  class="base-input-text" value="${payAddInfo.provActNo}" readonly="readonly" />
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payAddInfo.provActCurr}"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
							<span id="bankNameSpan">${payAddInfo.bankInfo}</span>
							<input type="hidden" id="bankInfo"  name="bankInfo" value="${payAddInfo.bankInfo}" class="base-input-text"/>
						</td>
						<td class="tdLeft">预付款金额<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceAmt" name="invoiceAmt" maxlength="18" class="base-input-text" valid errorMsg="请输入预付款金额" onkeyup="$.clearNoNum(this);" onblur="validaInvoiceAmt();$.onBlurReplace(this);"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">付款日期<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="payDate" name="payDate" readonly="readonly" class="base-input-text" valid errorMsg="请选择付款日期"/>
						</td>
						<td class="tdLeft">付款方式<span class="red">*</span></td>
						<td class="tdRight">
							<div class="ui-widget">
								<select id="payMode" name="payMode" valid errorMsg="请选择支付方式。"  class="erp_cascade_select" >
									<option value="">-请选择-</option>
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${payAddInfo.payMode}"/>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<!-- <td class="tdLeft">项目说明<span class="red">*</span></td>
						<td class="tdRight" colspan="3">
							<input type="text" id="invoiceMemo" name="invoiceMemo"  class="base-input-text"  valid errorMsg="请输入项目说明"/>
						</td> -->
						<td class="tdLeft">预付款说明<span class="red">*</span><br/>(<span id='memoSpan'>0/200</span>)</td>
						<td class="tdRight" colspan="3">
							<textarea id="invoiceMemo" cols="60" rows="5"  name="invoiceMemo" onkeyup="$_showWarnWhenOverLen1(this,200,'memoSpan')" class="base-textArea" valid errorMsg="请输入预付款说明"></textArea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
				<p:button funcId="03030304" value="保存为草稿"/>
		    	<p:button funcId="03030305" value="提交至待复核"/>
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>