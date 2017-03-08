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
<title>付款修改</title>
<script type="text/javascript">
function pageInit() {
	//初始化附件类型的被选择的值
	var attTypeStr = "${payInfo.attachmentType}";
	var attTypeArr = attTypeStr.split(",");//逗号切分成数组
	for(var i=0;i<attTypeArr.length;i++){
		$("#payModifyForm").find("input[name='attachmentType']").each(function(){
			if($(this).val() == attTypeArr[i]){
				$(this).attr('checked',true);
				return ;
			}
		});
	}
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	
 	//设置时间插件
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}
function providerQuery(){
	App.submitShowProgress();
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
	App.submitFinish();
	if(!proObj){//判断谷歌浏览器返回问题
		proObj = window.returnValue;
		if(proObj){
			$("#providerCode").val(proObj.providerCode);
			$("#providerName").val(proObj.providerName);
			$("#providerType").val(proObj.providerType);
			$("#provActNo").val(proObj.actNo);
			$("#bankName").val(proObj.bankName);
			$("#provActCurr").val(proObj.actCurr);
		}
	}else{
		$("#providerCode").val(proObj.providerCode);
		$("#providerName").val(proObj.providerName);
		$("#providerType").val(proObj.providerType);
		$("#provActNo").val(proObj.actNo);
		$("#bankName").val(proObj.bankName);
		$("#provActCurr").val(proObj.actCurr);
	 }
}
function doValidate() {
	//提交前调用
	if(!App.valid("#payModifyForm")){
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
	}else if($("#cancelAmt").val()>$("#advanceTotalAmt").val()){
		App.notyError("本次预付款核销总金额不能大于合同预付款金额。");
		$("#cancelAmt").focus();
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
	$("#payModifyForm").find("input[name='attachmentType']").each(function(){
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
	var form=$("#payModifyForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payAdd/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030309&flag=4&advancePayId="+payId;
	App.submit(form);	
}


//删除
function deletePayInfo(button, url)
{
	$( "<div>确认要删除?</div>" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
// 				App.submitForm(button, url);
				var form = $('#payModifyForm')[0];
				form.action = url;
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});

}
</script>
</head>

<body>
<p:authFunc funcArray="03030402,03030403,03030408"/>
<form action="" method="post" id="payModifyForm">
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
							${constractInfo.cntNum}
							<input type="hidden" id="cntNum" name="cntNum" value="${constractInfo.cntNum}"/>
						</td>
						<td class="tdLeft" width="25%">进度</td>
						<td class="tdRight" width="25%">
							<fmt:parseNumber value="${constractInfo.normarlTotalAmt+constractInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${constractInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额(人民币)</td>
						<td class="tdRight">
							<input type="hidden" id="cntAmt" name="cntAmt" value="${constractInfo.cntAmt}"/>
							<fmt:formatNumber type="number" value="${constractInfo.cntAmt}" minFractionDigits="2"/><br/>
							其中质保金：${constractInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							${constractInfo.cntTypeName}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">正常付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.normarlTotalAmt}">
								<input type="hidden" id="normarlTotalAmt" name="normarlTotalAmt" value="${constractInfo.normarlTotalAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.normarlTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.normarlTotalAmt}">
								<input type="hidden" id="normarlTotalAmt" name="normarlTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">预付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.advanceTotalAmt}">
								<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="${constractInfo.advanceTotalAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.advanceTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.advanceTotalAmt}">
								<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						
					</tr>
					<tr>
						<td class="tdLeft">冻结金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="${constractInfo.freezeTotalAmt-payInfo.payAmt-payInfo.suspenseAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.freezeTotalAmt-payInfo.payAmt-payInfo.suspenseAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.suspenseTotalAmt}">
								<input type="hidden" id="suspenseTotalAmt" name="suspenseTotalAmt" value="${constractInfo.suspenseTotalAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.suspenseTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.suspenseTotalAmt}">
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
					<tr>
						<th colspan="4">
							付款单号：${payInfo.payId}
							<input type="hidden" id="payId" name="payId" value="${payInfo.payId}"/>
							<input type="hidden" id="dataFlag" name="dataFlag" value="${payInfo.dataFlag}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号<span class="red">*</span></td>
						<td class="tdRight">
							${payInfo.invoiceId}
						</td>
						<td class="tdLeft">附件张数<span class="red">*</span></td>
						<td class="tdRight"> 
							<input type="text" id="attachmentNum"  value="${payInfo.attachmentNum}" name="attachmentNum" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/>
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
							<input type="hidden" id="providerCode"  name="providerCode" value="${payInfo.providerCode}" class="base-input-text"/>
							<input type="hidden" id="providerType"  name="providerType" value="${payInfo.providerType}" class="base-input-text"/>
							<input type="text"  id="providerName" name="providerName" value="${payInfo.providerName}" class="base-input-text" readonly="readonly" valid errorMsg="请输入收款单位"/>
							<a href="#" onclick="providerQuery();" style="border: 0px;" title="供应商查询">
								<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
							</a>
						</td>
						<td class="tdLeft">收款帐号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="provActNo" name="provActNo"  class="base-input-text" value="${payInfo.provActNo}" readonly="readonly" valid errorMsg="请输入收款帐号"/>
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payInfo.provActCurr}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">银行名称<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="bankName" name="bankName"  class="base-input-text" value="${payInfo.bankName}" readonly="readonly" valid errorMsg="请输入银行名称"/>
						</td>
						<td class="tdLeft">发票金额<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceAmt" name="invoiceAmt" maxlength="18" value="${payInfo.invoiceAmt}" onblur="changeAmt('invoiceAmt','advanceCancelAmt');"  class="base-input-text" valid errorMsg="请输入发票金额"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">本次预付款核销总金额<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="advanceCancelAmt" name="advanceCancelAmt" value="${payInfo.advanceCancelAmt}" maxlength="18" onblur="changeAmt('advanceCancelAmt','invoiceAmt');"  class="base-input-text" valid errorMsg="请输入预付款核销总金额"/>
							<input type="hidden" id="payAmt" name="payAmt"  maxlength="18"  class="base-input-text" value="0"/>
							<input type="hidden" id="suspenseAmt" name="suspenseAmt"  maxlength="18"  class="base-input-text" value="0"/>
							<input type="hidden" id="suspensePeriod" name="suspensePeriod"  maxlength="18"  class="base-input-text" value="0"/>
						</td>
						<td class="tdLeft">核销日期<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" readonly="readonly" id="payDate" value="${payInfo.payDate}" name="payDate" class="base-input-text" valid errorMsg="请选择付款日期" readonly="readonly"/>
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
							<td>${bean.advancePayId}<input type="hidden" id="advancePayIds" name="advancePayIds" value="${bean.advancePayId}"  class="base-input-text"/></td>
							<td class="tdr">${bean.payAmt}<input type="hidden" id="remainCancel" name="remainCancel" value="${bean.payAmt-bean.cancelAmt}"  class="base-input-text"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.cancelAmtTotal-bean.cancelAmt}" minFractionDigits="2"/></td>
							<td><input id="cancelAmts" name="cancelAmts" value="${bean.cancelAmt}"  class="base-input-text"/></td>
							<td>
								<div class="detail">
									<a href="#" onclick="advanceCancelDetail('${bean.advancePayId}');" title="明细"></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table id="advPayMon">
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
					<c:forEach items="${payAdvDevices}" var="bean">
						<tr>
							<td>${bean.cglCode }
								<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" />
								<input type="hidden" id="remainDeviceAmts" name="remainDeviceAmts" value="${bean.execAmt - bean.payedAmt-bean.freezeAmt}"/>
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.deviceModelName }</td>
							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td>
								<input type="hidden" id="freezeAmtAdvBefores" name="freezeAmtAdvBefores" value="${bean.subInvoiceAmt }" readonly="readonly"  class="base-input-text"/>
								<c:if test="${bean.execAmt == bean.payedAmt }">
									<input id="advancePayInvAmts" name="advancePayInvAmts" value="0" readonly="readonly"  class="base-input-text" style="width: 100px;"/>
								</c:if>
								<c:if test="${bean.execAmt != bean.payedAmt }">
									<input id="advancePayInvAmts" name="advancePayInvAmts" value="${bean.subInvoiceAmt }"  class="base-input-text" style="width: 100px;" onblur="checkAdvPayIvrowMemos(this);" valid errorMsg="请填写付款发票分配金额"/>
								</c:if>
							</td>
							<td><input id="advancePayIvrowMemos" name="advancePayIvrowMemos" class="base-input-text" style="width: 100px;" value="${bean.ivrowMemo }"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="打印封面" onclick=""/>
				<p:button funcId="03030402" value="保存"/>
		    	<p:button funcId="03030403" value="提交"/>
		    	<p:button funcId="03030408" value="删除" onclick="deletePayInfo"/>
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>