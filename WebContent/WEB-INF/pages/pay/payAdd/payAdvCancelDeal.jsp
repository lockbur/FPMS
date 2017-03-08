<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预付款核销</title>
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
 	var data = new Date();
 	var year = data.getFullYear();
 	var month = data.getMonth()+1;
 	if(month<10)
 	{
 		month = "0"+month;
 	}
 	var day = data.getDate();
 	if(day<10)
 	{
 		day = "0"+day;
 	}
	$("#payDate").val(year+"-"+month+"-"+day);
}
function providerQuery(){
	var proObj = null;
	if($("#providerType").val() == 'VENDOR')
	{
		proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&flag=1", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	}else{
		if($("#providerType").val() == 'IAP')
		{
			var providerCode = $("#providerCode").val();
			var providerType = $("#providerType").val();
			var provActNo = $("#provActNo").val();
			var provActCurr =$("#provActCurr").val();
			proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&providerCode="+providerCode+"&providerType="+providerType+"&provActNo="+provActNo+"&provActCurr="+provActCurr, null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
		}else{
			proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
		}
	}
	if(proObj){
		$("#providerCode").val(proObj.providerCode);
		$("#providerName").val(proObj.providerName);
		$("#providerType").val(proObj.providerType);
		$("#provActNo").val(proObj.actNo);
		$("#bankInfo").val(proObj.bankInfo);
		$("#provActCurr").val(proObj.actCurr);
	}
}
function doValidate() {
	//提交前调用
	if(!App.valid("#payAdvanceCancelForm")){
		return;
	}else if(!checkNum($("#attachmentNum").val())){
		App.notyError("附件数量格式有误！只能是正整数。");
		$("#attachmentNum").focus();
		return false;	
	}else if(!$.checkMoney($("#invoiceAmt").val())){
		//金额的校验
		App.notyError("发票金额格式有误！最多含两位小数的18位正浮点数。");
		$("#invoiceAmt").focus();
		return false;
	}else if(!$.checkMoney($("#advanceCancelAmt").val())){
		//金额的校验
		App.notyError("本次核销总金额格式有误！最多含两位小数的18位正浮点数。");
		$("#advanceCancelAmt").focus();
		return false;
	}else if($("#advanceCancelAmt").val()>$("#advanceTotalAmt").val()){
		App.notyError("本次预付款核销总金额不能大于合同预付款金额。");
		$("#advanceCancelAmt").focus();
		return false;
	}else if(!checkAttaType()){
		App.notyError("请选择附件类型。");
		return false;
	}else if(!validAdvPayTotal("cancelAmts")){
		App.notyError("预付款核销金额的总和必须等于预付款核销总金额。");
		return false;
	}else if(!validAdvPayTotal("advancePayInvAmts")){
		App.notyError("合同采购设备(预付款核销)发票分配金额总和必须等于预付款核销总金额。");
		return false;
	}else if(!validDeviceAmt()){
		return false;
	}
	var str = validCancelAmt();
	if(!$.isNull(str))
	{
		App.notyError("预付款批次号："+str+"本次核销金额必须小于等于剩余核销金额(预付款金额-已核销金额)。");
		return false;
	}
	if(!validateCanlelAmts()){
		return false;
	}
	return true;
}
//核销金额的校验（本次核销金额格式校验）
function validateCanlelAmts(){
	var v_flag = true;
	$("#advCancelId").find("input[id='cancelAmts']").each(function(){
		/* var cancelAmt = $(this).val(); */
		if(!$.checkMoney($(this).val()))
		{
			//金额的校验
			v_flag = false;
			App.notyError("核销金额格式有误！最多含两位小数的18位正浮点数。");
			$(this).focus();
			return ;
		}
	});
	return v_flag;
}
//预付款核销金额校验(本次核销金额<=剩余核销金额)
function validCancelAmt()
{
	var advancePayId = null;
	$("#advCancelId").find("input[id='cancelAmts']").each(function(){
		var cancelAmt = $(this).val();
		var remainCancel = $(this).parent().parent().find("input[id='remainCancel']").val();
		if(parseFloat(cancelAmt)>parseFloat(remainCancel))
		{
			advancePayId = $(this).parent().parent().find("input[id='advancePayIds']").val();
			return ;
		}
	});
	return advancePayId;
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
//校验附件类型是否有被选中的值
function checkAttaType(){
	var i = 0;
	$("#payAdvanceCancelForm").find("input[name='attachmentType']").each(function(){
		if($(this).is(':checked')){
			i++;
		}
	});
	if(i>0)
		return true;
	return false;
}
//校验预付款核销总金额 ==预付款核销本次金额的总和||预付款合同采购发票分配金额总和相等
function validAdvPayTotal(inputName)
{
	var advPayTotalMon = $("#advanceCancelAmt").val();
	var totalMon = 0;
	$("input[name='"+inputName+"']").each(function(){
		totalMon += parseFloat($(this).val());
	});
	if(advPayTotalMon == totalMon)
	{
		return true;
	}
	return false;
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
//发票金额和核销金额保持一致
function changeAmt(id1,id2){
	$("#"+id2).val($("#"+id1).val());//把Id1的值给ID2
}
//合同采购设备信息校验
function validDeviceAmt(){
	var v_flg = true;
	var arr2 = 	$("#cancelMon").find("input[name='advancePayInvAmts']");//预付款
	var arr3 = 	$("#cancelMon").find("input[name='remainDeviceAmts']");
	for(var i=0;i<arr2.length;i++){
		var payInvAmt = parseFloat($(arr2[i]).val());
		var remainDeviceAmt = parseFloat($(arr3[i]).val());
		if(payInvAmt>remainDeviceAmt)
		{
			v_flg = false;
			App.notyError("合同采购设备（预付款核销）对应的发票分配金额必须小于等于设备剩余金额（总金额-已付金额-冻结金额）。");
			$($(arr2[i])).focus();
			break ;
		}
	}
	return v_flg;
}
//预付款核销明细查询
function advanceCancelDetail(payId)
{
	var form=$("#payAdvanceCancelForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payAdd/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030309&flag=3&advancePayId="+payId;
	App.submit(form);	
}
</script>
</head>
<body>
<p:authFunc funcArray="03030307,03030308"/>
<form action="" method="post" id="payAdvanceCancelForm">
	<p:token/>
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<th colspan="4">
							合同信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="25%">合同号</td>
						<td class="tdRight" width="25%">
							${payAddInfo.cntNum}
							<input type="hidden" id="cntNum" name="cntNum" value="${payAddInfo.cntNum}"/>
						</td>
						<td class="tdLeft" width="25%">进度</td>
						<td class="tdRight" width="25%">
							<fmt:parseNumber value="${payAddInfo.normarlTotalAmt+payAddInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${payAddInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" minFractionDigits="2" value="${a/b*100}" maxFractionDigits="2"/>%
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payAddInfo.cntAmt}" minFractionDigits="2"/><br/>
							其中质保金：${payAddInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							${payAddInfo.cntTypeName}
							<input type="hidden" id="cntType" name="cntType" value="${payAddInfo.cntType}"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">正常付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${payAddInfo.normarlTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">预付款金额(人民币)</td>
						<td class="tdRight">
							<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="${payAddInfo.advanceTotalAmt}"/>
							<c:if test="${!empty payAddInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${payAddInfo.advanceTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						
					</tr>
					<tr>
						<td class="tdLeft">冻结金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${payAddInfo.freezeTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty payAddInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${payAddInfo.suspenseTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty payAddInfo.suspenseTotalAmt}">
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
					<tr>
						<th colspan="4">
							付款单号：${payAddInfo.payId}
							<input type="hidden" id="payId" name="payId" value="${payAddInfo.payId}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号</td>
						<td class="tdRight">
							${payAddInfo.invoiceId }
							<input type="hidden" id="invoiceId" name="invoiceId" maxlength="100" value='${payAddInfo.invoiceId }'  class="base-input-text" valid errorMsg="请输入发票号"/>
						</td>
						<td class="tdLeft">附件张数</td>
						<td class="tdRight"> 
							<input type="text" id="attachmentNum" name="attachmentNum" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">附件类型<span class="red">*</span></td>
						<td class="tdRight" colspan="3"> 
							<c:forEach items="${listAtTypes}" var="bean">
								<input type="checkbox" name="attachmentType" value='${bean.attachmentType }'>${bean.attachmentTypeName }&nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">收款单位<span class="red">*</span></td>
						<td class="tdRight">
							<input type="hidden" id="providerCode"  name="providerCode" value="${payAddInfo.providerCode}" class="base-input-text"/>
							<input type="hidden" id="providerType"  name="providerType" value="${payAddInfo.providerType}" class="base-input-text"/>
							<input type="text"  id="providerName" name="providerName" value="${payAddInfo.providerName}" class="base-input-text" readonly="readonly" valid errorMsg="请输入收款单位"/>
							<a href="#" onclick="providerQuery();" style="border: 0px;" title="供应商查询">
								<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
							</a>
						</td>
						<td class="tdLeft">收款帐号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="provActNo" name="provActNo"  class="base-input-text" value="${payAddInfo.provActNo}" readonly="readonly" valid errorMsg="请输入收款帐号"/>
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payAddInfo.provActCurr}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="bankInfo" name="bankInfo"  class="base-input-text" value="${payAddInfo.bankInfo}" readonly="readonly" valid errorMsg="请输入开户行信息"/>
						</td>
						<td class="tdLeft">发票金额<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceAmt" name="invoiceAmt" maxlength="18"  class="base-input-text" valid errorMsg="请输入发票金额" onblur="changeAmt('invoiceAmt','advanceCancelAmt');"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">本次预付款核销总金额<span class="red">*</span></td>
						<td class="tdRight">
							<input id="advanceCancelAmt" name="advanceCancelAmt" value="0"  class="base-input-text" onblur="changeAmt('advanceCancelAmt','invoiceAmt');" valid errorMsg="请输入预付款核销总金额"/>
							<input type="hidden" id="payAmt" name="payAmt"  maxlength="18"  class="base-input-text" value="0"/>
							<input type="hidden" id="suspenseAmt" name="suspenseAmt"  maxlength="18"  class="base-input-text" value="0"/>
						</td>
						<td class="tdLeft">核销日期<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="payDate" name="payDate"  class="base-input-text" valid errorMsg="请选择核销日期" readonly="readonly"/>
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
				<table id="advCancelId">
					<tr>
						<th colspan="5">预付款核销信息</th>
					</tr>
					<tr>
						<th width="25%">预付款批次</th>
						<th width="20%">预付款金额(元)</th>
						<th width="20%">已核销金额(元)</th>
						<th width="20%">本次核销金额(元)</th>
						<th width="15%">操作</th>
					</tr>
					<c:forEach items="${payAdvanceCancelList}" var="bean">
						<tr>
							<td>${bean.payId}<input type="hidden" id="advancePayIds" name="advancePayIds" value="${bean.payId}"  class="base-input-text"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/><input type="hidden" id="remainCancel" name="remainCancel" value="${bean.payAmt-bean.cancelAmt}"  class="base-input-text"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.cancelAmt}" minFractionDigits="2"/></td>
							<td><input id="cancelAmts" name="cancelAmts" value="0" style="width: 80%"  class="base-input-text"/></td>
							<td>
								<div class="detail">
									<a href="#" onclick="advanceCancelDetail('${bean.payId}');" title="明细"></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table id="cancelMon">
					<tr>
						<th colspan="9">合同采购设备(预付款核销)</th>
					</tr>
					<tr>
						<th width="9%">核算码</th>
						<th width="9%">项目</th>
						<th width="16%">物料类型</th>
						<th width="10%">设备型号</th>
						<th width="10%">总金额(元)</th>
						<th width="11%">已付金额(元)</th>
						<th width="11%">冻结金额(元)</th>
						<th width="13%">发票分配金额(元)</th>
						<th width="11%">发票行说明</th>
					</tr>
					<c:forEach items="${devices}" var="bean">
						<tr>
							<td>${bean.cglCode }
								<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" />
								<input type="hidden" id="remainDeviceAmts" name="remainDeviceAmts" value="${bean.execAmt - bean.payedAmt-bean.freezeAmt}"/>
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.deviceModelName }</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td><input id="advancePayInvAmts" name="advancePayInvAmts" value="0"  class="base-input-text" style="width: 100px;" valid errorMsg="请输入预付款核销设备发票分配金额" onblur="checkAdvPayIvrowMemos(this);"/></td>
							<td><input id="advancePayIvrowMemos" name="advancePayIvrowMemos" class="base-input-text" style="width: 100px;"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<p:button funcId="03030307" value="保存"/>
		    	<p:button funcId="03030308" value="提交"/>
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>