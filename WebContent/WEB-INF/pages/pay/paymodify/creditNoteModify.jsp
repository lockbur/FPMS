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
<title>贷项通知单修改</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<style type="text/css">
	label{
		cursor: pointer;
	}
</style>
<script type="text/javascript">

Date.prototype.addDays = function(d){
	this.setDate(this.getDate()+d);
};
Date.prototype.addMonths = function(m){
	this.setMonth(this.getMonth()+m);
};
Date.prototype.Format = function(fmt)   
{ 
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};
//字符串转日期格式，strDate要转为日期格式的字符串
function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
     function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    return date;
}

function checkDxAmt(obj){
	var v = $(obj).val();
	if(v > 0){
		App.notyError("贷项通知单金额录入不能大于0！");
		$(obj).focus();
		return;
	}
}
function pageInit() {
	$_showWarnWhenOverLen1(document.getElementById("invoiceMemo"),1000,'memoSpan1');
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
 	
 	//设置暂收付款日期
	var dateStr = '${payInfo.payDate}'.replace(/-/g,'/');
	var date = new Date(dateStr);
	date.addDays(1);
	$( "#suspenseDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    minDate:date,
	    maxDate:"2099-12-31"
	});
	validateInvoiceId();
	countPayAmt('payInvAmts','payAddTaxAmts');
}

//AE状态的付款修改需要提示强制修改发票编号
function validateInvoiceId(){
	var flag = true;
	if('${payInfo.dataFlag}'=='AE'){
		if($("#invoiceIdBefore").val()==$("#invoiceId").val()){
			$( "<div>订单类发票创建不一致的数据修改：'发票号'必须修改（FMS不会重复接收）！</div>" ).dialog({
				resizable: false,
				height:170,
				width:'auto',
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"确认": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			flag = false;
		}
	}
	return flag;
}
//查询付款审批历史记录(弹窗显示)
function queryHis(payId){
	var url = "<%=request.getContextPath()%>/pay/payAdd/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030321&payId="+payId;
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "80%",
				lock: true,
			    fixed: true,
			    title:"查看审批历史记录",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}

function countTax(obj){
	var a = parseFloat($(obj).val());
	var b = parseFloat($(obj).parent().parent().find("#taxRate").val());
	if(b !=0 || b!= null){
	$(obj).parent().parent().find("#payAddTaxAmts").val(parseFloat(a*b).toFixed(2));
	}
	else{
		return;
	}
}

//计算物料行付款金额
function countPaySumAmt(obj){
	var a = parseFloat($(obj).parent().parent().find("#payInvAmts").val());
	var b = parseFloat($(obj).parent().parent().find("#payAddTaxAmts").val());
	$(obj).parent().parent().find("#paySumAmtSpan").html(parseFloat(parseFloat(a)+parseFloat(b)).toFixed(2));
}

function providerQuery(){
	var url=null;
	if($("#providerType").val() == 'VENDOR'){
		url='<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&flag=1&compare='+"common";
	}
	else{
		url='<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&compare='+"common";
	}
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

//提交前的校验
function doValidate() {
	//20151229 linjia控制暂收付款日期
	var suspAmt = $("#suspenseAmt1").val();
	if($.isBlank(suspAmt) || suspAmt==0){
		$("#suspenseDate").val("2099-12-31");
	}
	//提交前调用
	if(!App.valid("#payModifyForm")){
		return;
	}
	
	 if(!checkNum($("#attachmentNum").val()))
	{
		App.notyError("附件数量格式有误！只能是正整数。");
		$("#attachmentNum").focus();
		return false;	
	}
	//校验附件类型是否有被选中的值
	else if(!checkAttaType()){
		App.notyError("请选择附件的类型。");
		return false;
	}
	//校验发票金额
	else if(!validateMoney("invoiceAmt"))
	{
		return false;
	}
	//校验付款金额
	else if(!validateMoney("payAmt"))
	{
		return false;
	}
	
	else if(!validInvoiceAmt())
	{
		App.notyError("贷项通知单发票总金额必须等于贷项通知单付款总金额。");
		return false;
	}
	//校验冲销金额是否不超过剩余可冲销额度
	else if(!validateBlueAmtLeft()){
		App.notyError("贷项通知单付款金额的绝对值需小于等于（发票剩余可冲销金额）");
		return false;
	}
	
	//
	if(!validSubTaxLeft()){
		return false;
	}
	if(!validSubAmtLeft()){
		return false;
	}
	
	//校验税
	if(!validSubTax()){
		App.notyError("贷项通知单分配行税额总和必须等于发票头税额。");
		return false;
	}
   //校验不含税额
	if(!validSubNoTax()){
		App.notyError("贷项通知单分配行不含税金额总和必须等于发票头不含税金额。");
		return false;
	} 	
	
	//发票号
	if(!checkInvoiceId()){
		return false;
	}
	if($("#invoiceIdBefore").val()!=$("#invoiceId").val()){
		return ajaxCheckInvoiceId();
	}
	if(!validateInvoiceId()){
		return false;
	}
	//发票号去空
	$("#invoiceId").val($.trim($("#invoiceId").val()));
	return true;
	
}


//校验冲销不含税额 = sum(子不含税额)
function validSubNoTax()
{
	var totalMon = parseFloat($("#invoiceAmtNotax").val());
	var totalPayDevMon = 0;
	$("#payMon").find("input[name='payInvAmts']").each(function(){
		totalPayDevMon += parseFloat($(this).val());
	});
	if(totalMon.toFixed(2) == totalPayDevMon.toFixed(2))
	{
		return true;
	}
	return false;
}

//校验冲销税额 = sum(子税额)
function validSubTax()
{
	var totalMon = parseFloat($("#invoiceAmtTax").val());
	var totalPayDevMon = 0;
	$("#payMon").find("input[name='payAddTaxAmts']").each(function(){
		totalPayDevMon += parseFloat($(this).val());
	});
	if(totalMon.toFixed(2) == totalPayDevMon.toFixed(2))
	{
		return true;
	}
	return false;
}

//子AMT要小于等于剩余可冲销子AMT
function validSubAmtLeft(){
	var v_flag = true;
	//贷项通知单的发票金额分配问题（必须小于设备已付金额）
	$("#payMon").find("input[name='payInvAmts']").each(function(){
		var a = parseFloat($(this).parent().parent().find("#subAmtLeft").val());
		var b = parseFloat($(this).parent().parent().find("#payInvAmtOlds").val());
		if(a-b+parseFloat($(this).val())<0){
			v_flag = false;
			App.notyError("贷项通知单的物料分配行冲销不含税额的绝对值必须小于等于原蓝字发票物料行剩余可冲销不含税额。");
			$(this).focus();
			return false;
		}
	});
	return  v_flag;
}


//子税要小于等于剩余可冲销子税
function validSubTaxLeft(){
	var v_flag = true;
	//贷项通知单的发票金额分配问题（必须小于设备已付金额）
	$("#payMon").find("input[name='payAddTaxAmts']").each(function(){
		var a = parseFloat($(this).parent().parent().find("#subTexLeft").val());
		var b = parseFloat($(this).parent().parent().find("#payAddTaxAmtOlds").val());
		if(a-b+parseFloat($(this).val())<0){
			v_flag = false;
			App.notyError("贷项通知单的物料分配行冲销税额的绝对值必须小于等于原蓝字发票物料行剩余可冲销税额。");
			$(this).focus();
			return false;
		}
	});
	return  v_flag;
}

//校验发票金额和付款金额的格式
function validateMoney(id){
	var str = '';
	if(id=="invoiceAmt"){
		str = "发票";
	}else if(id=="payAmt"){
		str = "付款";
	}
	if(id=="invoiceAmt" && $("#"+id).val()==0)
	{
		App.notyError("贷项通知单"+str+"金额为0的付款没有意义，请重新输入！。");
		$("#"+id).focus();
		return false;
	}
	//如果是贷项通知单则可以是负数
	var regExp =new RegExp(/^(-)(0|[1-9][0-9]{0,15})(\.[\d]{1,2})?$/);	
	var money = $.trim($("#"+id).val());
	if(typeof money === "undefined" || money === null || !regExp.test(money))
	{
		App.notyError(str+"金额格式有误！贷项通知单的付款最多含两位小数的18位负浮点数。");
		$("#"+id).focus();
		return false;
	}else{
		return true;
	}
	return true;
}

//付款日期改变则对应改变暂收日期
function changeDate(obj){
	var payDate = $(obj).val();
	var suspenseDate = $( "#suspenseDate").val();
	
	if(suspenseDate <= payDate ){
		$( "#suspenseDate").val('');
		var dateStr = payDate.replace(/-/g,'/');
		var date = new Date(dateStr);
		date.addDays(1);
		$( "#suspenseDate" ).removeClass('hasDatepicker');
		$( "#suspenseDate" ).datepicker({
			changeMonth: true,
			changeYear: true,
		    dateFormat:"yy-mm-dd",
		    minDate:date,
		    maxDate:"2099-12-31"
		}); 
		$( "#suspenseDate" ).focus();
	}
	
}
//发票分配金额的校验（贷项通知单的格式校验）
function validateInvoiceAmts(){
	//预付款设备发票金额
	var v_flag = true;
	//贷项通知单的发票金额分配问题（必须小于设备已付金额）
	$("#payMon").find("input[name='payInvAmts']").each(function(){
		if(parseFloat($(this).parent().parent().find("#payedAmt").val())+parseFloat($(this).val())<0){
			v_flag = false;
			App.notyError("贷项通知单的合同采购设备发票分配金额的绝对值必须小于设备的（已付金额+冻结金额）。");
			$(this).focus();
			return ;
		}
	});
	return  v_flag;
}
//校验付款金额需小于等于可核销金额
function validatePayAmt(){
	var flag = false;
	var payAmt = parseFloat($("#payAmt").val());//付款金额
	var normarlTotalAmt = parseFloat($("#normarlTotalAmt").val());//正常付款金额
	var freezeTotalAmt = parseFloat($("#freezeTotalAmt").val());//冻结金额
	if(payAmt+normarlTotalAmt+freezeTotalAmt>=0){
		flag = true;
	}
	return flag;
}
//校验整数
function checkNum(str)
{
	var reg = /^[1-9]{1}[0-9]*$/g;	
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
//两个文本框输入只要有一个输入时，另一个自动填写相同的值
function setTwoValue(obj,id)
{	
	$("#"+id).text("");
	var _value = $(obj).val();
    //最后一位是小数点的话，移除
    //如果第一位是0，且0后面不是小数点
    if(_value){
    	$("#"+id).text(parseFloat(_value));
    }
    if(_value=="-"){
    	$("#"+id).text(_value.replace(/\-$/g, 0));
    }
}

//校验付款金额+暂收金额==正常付款合同采购发票分配金额总和
function validPayDeviceTotal()
{
	var totalMon = parseFloat($("#payAmt1").val());
	var totalPayDevMon = 0;
	$("#payMon").find("input[name='payInvAmts']").each(function(){
		totalPayDevMon += parseFloat($(this).val());
	});
	if(totalMon.toFixed(2) == totalPayDevMon.toFixed(2))
	{
		return true;
	}
	return false;
}

//校验发票金额 发票金额=付款金额
function validInvoiceAmt()
{
	var invAmt = parseFloat($("#invoiceAmt").val());//发票金额
	var totalMon =  parseFloat($("#payAmt").val());//付款金额
	if(invAmt.toFixed(2) == totalMon.toFixed(2))
		return true;
	return false;
}

//删除
function deletePayInfo(button, url)
{
	$( "<div>确认要删除?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				var form = $('#payModifyForm')[0];
				form.action = url;
				form.submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});

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
//合同采购设备(正常付款)增值税金额校验
function checkPayAddTaxAmts(obj){
	var invAmt = $(obj).val();	//发票分配金额
	//如果不为0且不为空,则发票行说明不能为空
	var objIvm =$(obj).parent().parent().find("#payAddTaxAmts");
	if(invAmt != null && invAmt != '' && invAmt != 0){
		$(objIvm).attr("valid",'');
		$(objIvm).attr("errorMsg","请输入增值税金额");
	}
	else{
		$(objIvm).removeAttr("valid");
		$(objIvm).removeAttr("errorMsg");
	}
}
//合同采购设备(正常付款)发票行说明校验
function checkPayIvrowMemos(obj){
	var invAmt = $(obj).val();	//发票分配金额
	//如果不为0且不为空,则发票行说明不能为空
	var objIvm =$(obj).parent().parent().find("#payIvrowMemos");
	if(invAmt != null && invAmt != '' && invAmt != 0){
		$(objIvm).attr("valid",'');
		$(objIvm).attr("errorMsg","请输入发票行说明");
	}
	else{
		$(objIvm).removeAttr("valid");
		$(objIvm).removeAttr("errorMsg");
	}
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
			}
		});
	}else{
		App.submitForm(button, url);
	}

}

//校验冲销金额是否不超过剩余可冲销额度
function validateBlueAmtLeft(){
	var flag = false;
	var blueSumLeft = parseFloat($("#blueSumLeft").val());//蓝字发票剩余可冲销金额
	var invoiceAmtNotax = parseFloat($("#invoiceAmtNotax").val());//不含税冲销金额
	var invoiceAmtTax = parseFloat($("#invoiceAmtTax").val());//税冲销金额
	
	var payInvAmtOldsSum = 0; //先前的不含税
	$("input[name='payInvAmtOlds']").each(function(){
			var thisVal=parseFloat($(this).val());
			payInvAmtOldsSum += thisVal;
	});
	var payAddTaxAmtOldsSum = 0; //先前的税额
	$("input[name='payAddTaxAmtOlds']").each(function(){
			var thisVal=parseFloat($(this).val());
			payAddTaxAmtOldsSum += thisVal;
	});
	
	if(blueSumLeft+invoiceAmtNotax+invoiceAmtTax-payInvAmtOldsSum-payAddTaxAmtOldsSum >=0 ){
		flag = true;
	}
	return flag;
}

//计算发票金额
function countInvoiceAmt(obj){
	//如果输入的不合法则不进行相加运算
	var invoiceAmtTax = parseFloat($("#invoiceAmtTax").val());
	var invoiceAmtNotax = parseFloat($("#invoiceAmtNotax").val());
	var invoiceAmtSpan=parseFloat(parseFloat(invoiceAmtNotax+invoiceAmtTax));
	
	$("#invoiceAmtSpan").html(invoiceAmtSpan.toFixed(2));
	$("#invoiceAmt").val(invoiceAmtSpan.toFixed(2));
}


//按比例分配到物料 选择框可用
function showIsCheck(){
	var money =  parseFloat($("#invoiceAmtNotax").val());
	if(money!=0 && money !='-'){
		$("#isCheck").removeAttr("disabled");
	}else{
		$("#isCheck").attr("disabled","disabled");
	}
	if($("#isCheck").is(':checked')){
		$("#isCheck").removeAttr("checked");
	}
}

function isCheckMoeny(){
	//被选中
	if($("#isCheck").is(':checked')){
	    //本次贷项通知单 不含税总金额
        var invoiceAmtNotax = parseFloat($("#invoiceAmtNotax").val());
        //原蓝字发票不含税总金额
        var invoiceAmtNotaxBlue = parseFloat($("#invoiceAmtNotaxBlue").val());
		//循环物料行，分配金额
		if(!isNaN(invoiceAmtNotax) && invoiceAmtNotax != 0){
			var count = 0;//统计数量
			var totalM = 0;//统计金额
			var temp = 0;//不含税金额
			var tax = 0; //税额
			$("#payMon").find("tr").find("input[id='subInvoiceAmt']").each(function(){
				var matrAmtBlue = parseFloat($(this).val());//原蓝字发票物料行金额
				var taxRate = parseFloat($(this).parent().parent().find("input[id='taxRate']").val());
				if($("#payMon").find("tr").find("input[id='subInvoiceAmt']").length-1==count){
					//若只有一行
					temp = parseFloat(invoiceAmtNotax-totalM);
					tax = parseFloat(temp * taxRate);
					$(this).parent().parent().find("input[id='payInvAmts']").val(temp.toFixed(2));
					$(this).parent().parent().find("input[id='payAddTaxAmts']").val(tax.toFixed(2));
				}else{
				    //多行
					temp = parseFloat(invoiceAmtNotax*matrAmtBlue/invoiceAmtNotaxBlue);
					tax = parseFloat(temp * taxRate);
					$(this).parent().parent().find("input[id='payInvAmts']").val(temp.toFixed(2));
					$(this).parent().parent().find("input[id='payAddTaxAmts']").val(tax.toFixed(2));
				}
				totalM += parseFloat(temp.toFixed(2));
				count++;
				countPaySumAmt($(this));
				checkPayIvrowMemos($(this).parent().parent().find("input[id='payInvAmts']"));
			});
			
			countPayAmt('payInvAmts','payAddTaxAmts');
			
		}else{
		  //若果分配金额为0
			$("#payMon").find("input[id='subInvoiceAmt']").each(function(){
				$(this).parent().parent().find("input[id='payInvAmts']").val(0);
				$(this).parent().parent().find("input[id='payAddTaxAmts']").val(0);
				countPaySumAmt($(this));
				checkPayIvrowMemos($(this).parent().parent().find("input[id='payInvAmts']"));
			});
			countPayAmt('payInvAmts','payAddTaxAmts');
		}
	}else{
		
	}
}

 
//校验两次发票号是否 一致
function checkInvoiceId(){
	var invoiceId = $.trim($("#invoiceId").val());
	var invoiceId1 = $.trim($("#invoiceId1").val());
	if(!$.isBlank(invoiceId) && !$.isBlank(invoiceId1)){
		if(invoiceId != invoiceId1){
			App.notyError("输入的两次发票号不一致，请核对。");
			return false;
		}
	}
	return true;
}

//发票号的ajax校验
function ajaxCheckInvoiceId(){
	var n = 0;
	var data={};
	data['invoiceId']=  $.trim($("#invoiceId").val());
	data['flag']= 0 ;
	data['modifyFlag'] = 0;
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

//计算付款金额
function countPayAmt(name,name2){
	
	//先检查输入的金额是否合法
	var countTotal = 0;
	var noTaxAmtTaotal = 0;
	
	$("input[name='"+name+"']").each(function(){
		if($(this).val()){
			var thisVal=parseFloat($(this).val());
			countTotal=parseFloat(countTotal);
			countTotal=parseFloat(thisVal+countTotal);
			noTaxAmtTaotal = parseFloat(noTaxAmtTaotal);
			noTaxAmtTaotal = parseFloat(thisVal+noTaxAmtTaotal);
		}
	});
	
	var taxAmtTaotal = 0;
	$("input[name='"+name2+"']").each(function(){
		if($(this).val()){
			var thisVal=parseFloat($(this).val());
			countTotal=parseFloat(countTotal);
			countTotal=parseFloat(thisVal+countTotal);
			taxAmtTaotal = parseFloat(taxAmtTaotal);
			taxAmtTaotal = parseFloat(thisVal+taxAmtTaotal);
		}
	});
	var payAmtTotalSpan=parseFloat(countTotal);
	
	$("#payAmtTotalSpan").html(payAmtTotalSpan.toFixed(2));
	$("#payAmtSpan").html(payAmtTotalSpan.toFixed(2));
	$("#payAmt").val(payAmtTotalSpan.toFixed(2));
	
	$("#noTaxAmtTaotalSpan").html(noTaxAmtTaotal.toFixed(2));
	$("#taxAmtTaotalSpan").html(taxAmtTaotal.toFixed(2));
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
					<tr class="collspan-control">
						<th colspan="4" >
							原蓝字发票信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">合同号</td>
						<td class="tdRight" width="30%">
							${invoiceBlue.cntNum}
						</td>
						<td class="tdLeft">付款单号</td>
						<td class="tdRight">
							${invoiceBlue.payId}
						</td>
					</tr>
					<tr>
					<tr>
						<td class="tdLeft" width="20%">原蓝字发票编号</td>
						<td class="tdRight" width="30%">
							${invoiceBlue.invoiceId}
						</td>
						<td class="tdLeft">发票总金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
					<tr>
						<td class="tdLeft" width="20%">预付款核销金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.advanceCancelAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">正常付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${invoiceBlue.payAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
					<tr>
						<td class="tdLeft" width="20%">暂收金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.suspenseAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft" width="20%">累计暂收结清金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.susTotalAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票不含税金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmtNotax}" minFractionDigits="2"/>元
							<input type="hidden" id="invoiceAmtNotaxBlue" value="${invoiceBlue.invoiceAmtNotax}"/>
						</td>
						<td class="tdLeft">发票税额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmtTax}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票剩余可冲销金额</td>
						<td class="tdRight" width="80%" colspan="3">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmtLeft-payInfo.invoiceAmtNotax-payInfo.invoiceAmtTax}" minFractionDigits="2"/>元
							<input type="hidden" id="blueSumLeft" value="${invoiceBlue.invoiceAmtLeft}">
						</td>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票状态</td>
						<td class="tdRight" width="30%">
							${invoiceBlue.dataFlagInvoiceName}
						</td>
						<td class="tdLeft">付款状态</td>
						<td class="tdRight">
							${invoiceBlue.dataFlagPayName}
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
							付款单号：${payInfo.payId}
							<input type="hidden" id="payId" name="payId" value="${payInfo.payId}"/>
							<input type="hidden" id="dataFlag" name="dataFlag" value="${payInfo.dataFlag}"/>
							<input type="hidden" id="isCreditNote" name="isCreditNote" value="0"/>
							<input type="hidden" id="suspenseAmt" name="suspenseAmt" value="0"/>
							<input type="hidden" id="advanceCancelAmt" name="advanceCancelAmt" value="0"/>
							<input type="hidden" id="suspenseDate" name="suspenseDate" value="${payInfo.suspenseDate}"/>
							<input type="hidden" id="invoiceIdBlue" name="invoiceIdBlue" value="${invoiceBlue.invoiceId}"/>
							<input type="hidden" id="payIdBlue" name="payIdBlue" value="${invoiceBlue.payId}"/>
							<input type="hidden" id="cntNum" name="cntNum" value="${payInfo.cntNum}"/>
							<c:if test="${!empty constractInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="${constractInfo.freezeTotalAmt-payInfo.payAmt}"/>
							</c:if>
							<c:if test="${empty constractInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="0"/>
							</c:if>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">红字发票号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="hidden" id="invoiceIdBefore" value='${payInfo.invoiceId }'/>
							<input type="text" id="invoiceId" name="invoiceId" maxlength="50" value='${payInfo.invoiceId }' onblur="checkInvoiceId();" class="base-input-text" valid errorMsg="请输入发票号"/>
						</td>
						<td class="tdLeft">确认红字发票号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceId1" name="invoiceId1" maxlength="50" value='${payInfo.invoiceId }' onblur="checkInvoiceId();" class="base-input-text" valid errorMsg="请再次确认输入发票号"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft prompt" width="20%">附件张数</td>
						<td class="tdRight" width="30%"> 
							<input type="text" id="attachmentNum"  value="${payInfo.attachmentNum}" name="attachmentNum" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/>
						</td>
						<td class="tdLeft" width="20%"></td>
						<td class="tdRight" width="30%"> 
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
						</td>
					</tr>
					<tr>
						<td class="tdLeft">收款单位<span class="red">*</span></td>
						<td class="tdRight">
							<input type="hidden" id="providerCode"  name="providerCode" value="${payInfo.providerCode}" class="base-input-text"/>
							<input type="hidden" id="providerType"  name="providerType" value="${payInfo.providerType}" class="base-input-text"/>
							<input type="hidden" id="providerAddr"  name="providerAddr" value="${payInfo.providerAddr}" class="base-input-text"/>
							<input type="hidden" id="providerAddrCode"  name="providerAddrCode" value="${payInfo.providerAddrCode}" class="base-input-text"/>
							<input type="hidden" id="actName"  name="actName" value="${payInfo.actName}" class="base-input-text"/>
							<input type="hidden" id="bankName" name="bankName"  class="base-input-text" value="${payInfo.bankName}"/>
							<input type="hidden" id="bankCode"  name="bankCode" value="${payInfo.bankCode}" class="base-input-text"/>
							<input type="hidden" id="bankArea"  name="bankArea" value="${payInfo.bankArea}" class="base-input-text"/>
							<input type="hidden" id="actType"  name="actType" value="${payInfo.actType}" class="base-input-text"/>							
							<input type="text"  id="providerName" name="providerName" value="${payInfo.providerName}" class="base-input-text" readonly="readonly" onclick="providerQuery();" valid errorMsg="请输入收款单位"/>
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							<span id="provActNoSpan">${payInfo.provActNo}</span>
							<input type="hidden" id="provActNo" name="provActNo"  class="base-input-text" value="${payInfo.provActNo}" readonly="readonly" />
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payInfo.provActCurr}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
							<span id="bankNameSpan">${payInfo.bankInfo}</span>
							<input type="hidden" id="bankInfo"  name="bankInfo" value="${payInfo.bankInfo}" class="base-input-text"/>
							
						</td>
						<td class="tdLeft">付款方式<span class="red">*</span></td>
						<td class="tdRight">
							<div class="ui-widget">
								<select id="payMode" name="payMode" valid errorMsg="请选择支付方式。"  class="erp_cascade_select" >
									<option value="">-请选择-</option>
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${payInfo.payMode}"/>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">红字发票总金额=(贷项通知单发票总金额)</td>
						<td class="tdRight" >
							<span id="invoiceAmtSpan">${payInfo.invoiceAmtNotax+payInfo.invoiceAmtTax}</span>
							<input type="hidden" id="invoiceAmt" name="invoiceAmt" maxlength="18" value="${payInfo.invoiceAmtNotax+payInfo.invoiceAmtTax}"   class="base-input-text" valid errorMsg="请输入发票金额"/>
							<input type="hidden" id="invoiceAmtOld" name="invoiceAmtOld" maxlength="18" value="${payInfo.invoiceAmtNotax+payInfo.invoiceAmtTax}"   class="base-input-text" valid />
						</td>
						<td class="tdRight" colspan="2">
							<label>
								<input type="checkbox" id="isCheck" name="isCheck" value="1"  onclick="isCheckMoeny();"/>金额是否按比例分配到物料
							</label>
						</td>
					</tr>
					<tr>
						<td class="tdLeft"  >不含税金额</td>
						<td class="tdRight">
							<input type="text" id="invoiceAmtNotax" name="invoiceAmtNotax" value="${payInfo.invoiceAmtNotax}" onblur="checkDxAmt(this);countInvoiceAmt(this);showIsCheck();" onkeyup="$.clearNoNum(this,true);" maxlength="18" value="0"  class="base-input-text" valid errorMsg="请输入发票不含税金额"/>
						</td>
						<td class="tdLeft"  >税额</td>
						<td class="tdRight">
							<input type="text" id="invoiceAmtTax" name="invoiceAmtTax" value="${payInfo.invoiceAmtTax}" onblur="checkDxAmt(this);checkPayAddTaxAmts(this);countInvoiceAmt(this);" onkeyup="$.clearNoNum(this,true);" maxlength="18" value="0"  class="base-input-text" valid errorMsg="请输入税额"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				
				<table id="payMon" class="tableList">
					<tr class="collspan-control">
						<th colspan="13">
						合同采购设备【贷项通知单总金额=<span id="payAmtTotalSpan">${payInfo.payAmt}</span>】
						<input type="hidden" id="payAmt" value="${payInfo.payAmt}" name="payAmt" maxlength="18" value="0"  class="base-input-text" valid readonly="readonly" />
						
						</th>
						
					</tr>
					<tr>
						<th width="7%">核算码</th>
						<th width="5%">项目</th>
						<th width="10%">物料名称</th>
						<th width="8%">费用部门</th>
						<th width="5%">不含税<br/>总金额(元)</th>
						<th width="5%">税码</th>
						<th width="5%">税额</th>
						<th width="5%">
							可冲销<br/>不含税余额
						</th>
						<th width="5%">
							可冲销<br/>税余额
						</th>
						<th width="15%">冲销<br/>不含税金额（元）<br/>
							【<span id="noTaxAmtTaotalSpan">0</span>】
						</th>
						<th width="15%">
							冲销<br/>税额（元）<br/>
							【<span id="taxAmtTaotalSpan">0</span>】	
						</th>
						<th width="5%">冲销<br/>总金额(元)</th>
						<th width="10%">发票行<br/>说明</th>
					</tr>
					<c:forEach items="${payDevices}" var="bean">
						<tr style="background:#EFC87C;" class="backCor">
							<td>${bean.cglCode }
								<input type="hidden" id="subIds" name="subIds" value="${bean.subId}" />
								<input type="hidden" id="deductFlags" value="${bean.deductFlag}" name="deductFlags" readonly="readonly" />
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td>
								<fmt:formatNumber type="number" value="${bean.subInvoiceAmtBlue}" minFractionDigits="2"/>
								<input type="hidden" id="subInvoiceAmt" value="${bean.subInvoiceAmt}"/>
							</td>
							<td>
								${bean.taxCode }
								<input type="hidden" id="taxRate" value="${bean.taxRate}"/>
							</td>
							<td><fmt:formatNumber type="number" value="${bean.addTaxAmtBlue}" minFractionDigits="2"/></td>
							<td>
								<fmt:formatNumber type="number" value="${bean.subInvoiceAmtLeft-bean.subInvoiceAmt}" minFractionDigits="2"/>
								<input type="hidden" id="subAmtLeft" value="${bean.subInvoiceAmtLeft}"/>
							</td>
							<td>
								<fmt:formatNumber type="number" value="${bean.addTaxAmtLeft-bean.addTaxAmt}" minFractionDigits="2"/>
								<input type="hidden" id="subTexLeft" value="${bean.addTaxAmtLeft}"/>
							</td>
							<td>
								<input id="payInvAmts" name="payInvAmts"  value="${bean.subInvoiceAmt}" onblur="checkDxAmt(this);countTax(this);checkPayIvrowMemos(this);countPayAmt('payInvAmts','payAddTaxAmts');countPaySumAmt(this);" onkeyup="$.clearNoNum(this,true);"  value="0"  maxlength="18" class="base-input-text backInput" valid errorMsg="请输入冲销的不含税金额！"/>
								<input type="hidden" id="payInvAmtOlds" name="payInvAmtOlds" value="${bean.subInvoiceAmt}"/>
								<input type="hidden" id="freezeAmtBefores" name="freezeAmtBefores" value="${bean.subInvoiceAmt }" />
								
							</td>
							<td>
							<c:choose>
							    <c:when test="${bean.taxRate eq 0 or bean.hasTaxrow eq 'N'}">
									<input id="payAddTaxAmts" name="payAddTaxAmts"  readonly="readonly" value="${bean.addTaxAmt}" onblur="checkDxAmt(this);checkPayAddTaxAmts(this);countPayAmt('payInvAmts','payAddTaxAmts');countPaySumAmt(this);" onkeyup="$.clearNoNum(this,true);" value="0" maxlength="18" class="base-input-text backInput" valid errorMsg="请输入冲销税额！"/>
								</c:when>
								<c:otherwise>
									<input id="payAddTaxAmts" name="payAddTaxAmts"  value="${bean.addTaxAmt}" onblur="checkDxAmt(this);checkPayAddTaxAmts(this);countPayAmt('payInvAmts','payAddTaxAmts');countPaySumAmt(this);" onkeyup="$.clearNoNum(this,true);" value="0" maxlength="18" class="base-input-text backInput" valid errorMsg="请输入冲销税额！"/>
								</c:otherwise>
							</c:choose>
								<input type="hidden" id="payAddTaxAmtOlds" name="payAddTaxAmtOlds" value="${bean.addTaxAmt}"/>
								<input type="hidden" id="freezeTaxAmtBefores" name="freezeTaxAmtBefores" value="${bean.addTaxAmt }" />
							</td>
							<td>
								<span id="paySumAmtSpan">${bean.subInvoiceAmt+bean.addTaxAmt }</span>
							</td>
							<td>
							<input id="payIvrowMemos" name="payIvrowMemos"  value="${bean.ivrowMemo}" maxlength="250" class="base-input-text backInput" style="width: 100px;" />
							</td>
						</tr>
					</c:forEach>
				</table>
				<table id="showAndHide">
					<tr class="collspan-control">
						<th colspan="4">贷项通知单付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft">贷项通知单付款总金额</td>
						<td class="tdRight">
							<span id="payAmtSpan">${payInfo.payAmt}</span>
						</td>
						<td class="tdLeft">付款日期<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" readonly="readonly" id="payDate" onchange="changeDate(this);" value="${payInfo.payDate}" name="payDate" class="base-input-text" valid errorMsg="请选择付款日期" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票说明<span class="red">*</span><br/>(<span id='memoSpan1'>0/250</span>)</td>
						<td class="tdRight" colspan="3">
							<textarea id="invoiceMemo" cols="60" rows="5"  name="invoiceMemo" onkeyup="$_showWarnWhenOverLen1(this,250,'memoSpan1')" class="base-textArea" valid errorMsg="请输入发票说明">${payInfo.invoiceMemo}</textArea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="查看原蓝字发票扫描文件" onclick="viewPayScanImg('${invoiceBlue.payId}','${invoiceBlue.icmsPkuuid }');"/>
				<c:if test="${!empty payInfo.icmsPkuuid}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid }');"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
				<c:if test="${payInfo.dataFlag != 'AX' }">
				<p:button funcId="03030402" value="保存为草稿"/>
		    	<p:button funcId="03030403" value="提交至待复核"/>
		    	</c:if>
		    	<p:button funcId="03030408" value="删除" onclick="deletePayInfo"/>
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>