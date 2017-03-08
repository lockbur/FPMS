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
$(document).ready (function(){
	$(".collspan-control-pay").css("cursor","pointer").click(function(){
		var siblings = $(this).parent().parent().parent().parent().parent().siblings();
		if(siblings.is(".collspan")){
			siblings.filter(".collspan").toggle();
		}else{
			siblings.toggle();
		}
	
		$(this).attr("title",function (){
			if(this.title=="收缩"){
				return "展开";
			}else{
				return "收缩";
			}
		});
	}
	).attr("title","收缩");
	
	
	$(".collspan-control1").css("cursor","pointer").click(function(){
		var siblings = $(this).siblings();
		//判断是否有暂收
		if($("#suspenseAmt1").val()==0){
			siblings.not("#suspenseTr,#suspenseReasonTr").toggle();
		}else{
			siblings.toggle();
		}
		
	});
});
//日期加1
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
function pageInit() {
	//默认显示合同供应商编码、地点编码对应的预付款
	jugleProvider('${payAddInfo.providerCode}','${payAddInfo.providerAddrCode}');
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();

 	//设置时间插件
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
	//给付款日期默认值
	var nDate = "${nowDate}";
	var date = eval('new Date(' + nDate.replace(/\d+(?=-[^-]+$)/, function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
 	var year = date.getFullYear();
 	var month = date.getMonth()+1;
 	var day = date.getDate();
 	if(month<10)
 	{
 		month = "0"+month;
 	}
 	if(day<10)
 	{
 		day = "0"+day;
 	}
	var dd = year+"-"+month+"-"+day;
	$("#payDate").val(dd);
	
	date.addDays(1);
	//暂收日期设置
	$( "#suspenseDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    minDate:date,
	    maxDate:"2099-12-31"

	});
	
	//判断合同是否有对应的预付款核销信息
	var flag ="${flag}";
	if(flag == "false")
	{
		$("#advanceCancelAmt").val(0);
		$("#advanceCancelAmtTotalSpan").html(0);
		$("#advanceCancelAmtSpan").html(0);
		$("#advanceCancelAmt").hide();
		$("#advanceCancelAmtSp").show();
		$("#red1").hide();
	}
	
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
						jugleProvider(proObj.providerCode,proObj.providerAddrCode);
					}		
				}
			}
		 );
}

//计算发票金额
function checkPayTax(){
	var invoiceAmt=parseFloat($("#invoiceAmt").val());
	var payAmt1=parseFloat($("#invoiceAmtNotax").val());
	var invoiceAmtTax=parseFloat($("#invoiceAmtTax").val());
	if(invoiceAmt != parseFloat((payAmt1 + invoiceAmtTax).toFixed(2)))
	{
		return false;
	}
	return true;
}

function doValidate() {
	//20151229 linjia控制暂收付款日期
	var suspAmt = $("#suspenseAmt1").val();
	if($.isBlank(suspAmt) || suspAmt==0){
		$("#suspenseDate").val("2099-12-31");
	}
	//提交前调用
	if(!App.valid("#addPayForm")){
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
	else if(!validateMoney("invoiceAmt"))
	{
		return false;
	}
	else if(!validateMoney("invoiceAmtNotax"))
	{
		return false;
	}
	else if(!validateMoney("invoiceAmtTax"))
	{
		return false;
	}
	else if(!checkPayTax())
	{
		App.notyError("发票总金额必须等于不含税金额 + 税额。 ");
		$("#invoiceAmt").focus();
		return false;
	}
	else if(!$.checkMoney($("#advanceCancelAmt").val()))
	{
		App.notyError("预付款核销金额格式有误！最多含两位小数的18位正浮点数。");
		$("#advanceCancelAmt").focus();
		return false;
	}
	else if(!validateMoney("payAmt1"))
	{
		return false;
	}
	else if(!$.checkMoney($("#suspenseAmt1").val()))
	{
		App.notyError("暂收金额格式有误！最多含两位小数的18位正浮点数。");
		$("#suspenseAmt1").focus();
		return false;
	}
	else if(!validAdvPayTotal("cancelAmts",""))
	{
		App.notyError("预付款核销金额的总和必须等于预付款核销总金额。");
		return false;
	}
	else if(!validInvoiceAmt())
	{
		App.notyError("发票金额必须等于暂收金额+付款金额+预付款核销金额。");
		return false;
	}
	
	var str = validCancelAmt();
	if(!$.isNull(str))
	{
		App.notyError("预付款批次号："+str+"本次核销金额必须小于等于剩余核销金额（预付款金额-已核销金额）。");
		return false;
	}
	if(!validDeviceAmt("${flag}"))
	{
		return false;
	}
	if(!validaInvoiceAmt())
	{
		App.notyError("付款金额+暂收金额不能大于合同剩余金额（合同总金额-正常付款金额-预付款金额 -冻结金额-暂收金额）。");
		$("#payAmt1").focus();
		return false;
	}
	//合同采购设备(正常付款)不含税金额总和必须等于（付款金额+暂收金额）不含税金额
	else if(!validatePayNoTax())
	{
		App.notyError("合同采购设备(正常付款)不含税金额总和必须等于（付款金额+暂收金额）不含税金额。");
		return false;
	}
	//合同采购设备(正常付款)税额总和必须等于（付款金额+暂收金额）税额
	else if(!validatePayTax())
	{
		App.notyError("合同采购设备(正常付款)税额总和必须等于（付款金额+暂收金额）税额。");
		return false;
	}
	//合同采购设备(预付款)不含税金额总和必须等于（付款金额+暂收金额）不含税金额
	else if(!validateAdvPayNoTax())
	{
		App.notyError("合同采购设备(预付款)不含税金额总和必须等于（付款金额+暂收金额）不含税金额。");
		return false;
	}
	//合同采购设备(预付款)税额总和必须等于（付款金额+暂收金额）税额
	else if(!validateAdvPayTax())
	{
		App.notyError("合同采购设备(预付款)税额总和必须等于（付款金额+暂收金额）税额。");
		return false;
	}
	
	else if(!validPayDeviceTotal())
	{
		App.notyError("合同采购设备(正常付款)发票分配金额总和必须等于付款金额+暂收金额。");
		return false;
	}
	else if(!validAdvPayTotal("advancePayInvAmts","advPayAddTaxAmts"))
	{
		App.notyError("合同采购设备(预付款)发票分配金额总和必须等于预付款核销总金额。");
		return false;
	}
	if(!validateCanlelAmts()){
		return false;
	}
	
	if(!validateCancelAmt()){
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

function validatePayNoTax()
{
	var totalPayDevMon = 0;
	var payAmtTotal = parseFloat((parseFloat($("#payAmt1").val()) + parseFloat($("#suspenseAmt1").val())).toFixed(2));
	//不含税金额、税额、总额
	var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
	var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额
	var totalAmt = parseFloat((parseFloat(noTaxAmt) + parseFloat(taxAmt)).toFixed(2));//总额

	var notaxPay = parseFloat((payAmtTotal*noTaxAmt/totalAmt).toFixed(2));//分配到不含税金额
	var taxPay = parseFloat((payAmtTotal - notaxPay).toFixed(2)); //分配到税额

	$("#payMon").find("input[name='payInvAmts']").each(function(){
		totalPayDevMon += parseFloat($(this).val());
	});
	totalPayDevMon = parseFloat(totalPayDevMon.toFixed(2));

	if(notaxPay != totalPayDevMon)
	{
		return false;
	}
	return true;
}

function validatePayTax()
{
	var totalPayDevMon = 0;
	var payAmtTotal = parseFloat((parseFloat($("#payAmt1").val()) + parseFloat($("#suspenseAmt1").val())).toFixed(2));
	//不含税金额、税额、总额
	var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
	var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额
	var totalAmt = parseFloat((parseFloat(noTaxAmt) + parseFloat(taxAmt)).toFixed(2));//总额

	var notaxPay = parseFloat((payAmtTotal*noTaxAmt/totalAmt).toFixed(2));//分配到不含税金额
	var taxPay = parseFloat((payAmtTotal - notaxPay).toFixed(2)); //分配到税额

	$("#payMon").find("input[name='payAddTaxAmts']").each(function(){
		totalPayDevMon += parseFloat($(this).val());
	});
	totalPayDevMon = parseFloat(totalPayDevMon.toFixed(2));

	if(taxPay != totalPayDevMon)
	{
		return false;
	}
	return true;
	
}

function validateAdvPayNoTax()
{
	var advanceCancelAmt = parseFloat($("#advanceCancelAmt").val());
	var totalMon = 0;

	//不含税金额、税额、总额
	var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
	var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额
	var totalAmt = parseFloat((parseFloat(noTaxAmt) + parseFloat(taxAmt)).toFixed(2));//总额

	var notaxPay = parseFloat((advanceCancelAmt*noTaxAmt/totalAmt).toFixed(2));//分配到不含税金额
	var taxPay = parseFloat((advanceCancelAmt - notaxPay).toFixed(2)); //分配到税额

	$("input[name='advancePayInvAmts']").each(function(){
		totalMon += parseFloat($(this).val());
	});
	totalMon = parseFloat(totalMon.toFixed(2));

	if(totalMon != notaxPay)
	{
		return false;
	}
	return true;
}

function validateAdvPayTax()
{
	var advanceCancelAmt = parseFloat($("#advanceCancelAmt").val());
	var totalMon = 0;

	//不含税金额、税额、总额
	var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
	var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额
	var totalAmt = parseFloat((parseFloat(noTaxAmt) + parseFloat(taxAmt)).toFixed(2));//总额

	var notaxPay = parseFloat((advanceCancelAmt*noTaxAmt/totalAmt).toFixed(2));//分配到不含税金额
	var taxPay = parseFloat((advanceCancelAmt - notaxPay).toFixed(2)); //分配到税额

	$("input[name='advPayAddTaxAmts']").each(function(){
		totalMon += parseFloat($(this).val());
	});
	totalMon = parseFloat(totalMon.toFixed(2));

	if(totalMon != taxPay)
	{
		return false;
	}
	return true;
}

//校验发票金额和付款金额的格式
function validateMoney(id){
	var str = '';
	if(id=="invoiceAmt"){
		str = "发票总金额";
	}else if(id=="payAmt1"){
		str = "付款金额";
	}
	else if(id=="invoiceAmtNotax"){
		str = "不含税金额";
	}
	else if(id=="invoiceAmtTax"){
		str = "税额";
	}
	if(id=="invoiceAmt" && $("#"+id).val()==0)
	{
		App.notyError(str+"为0的付款没有意义，请重新输入！。");
		$("#"+id).focus();
		return false;
	}
	//如果是非贷项通知单则正常校验
	if(!$.checkMoney($("#"+id).val()))
	{
		App.notyError(str+"格式有误！最多含两位小数的18位正浮点数。");
		$("#"+id).focus();
		return false;
	}
	return true;
}
//核销金额的校验（本次核销金额格式校验）
function validateCanlelAmts(){
	var v_flag = true;
	$("#advCancelId").find("input[id='cancelAmts']").each(function(){
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
//校验预付款核销金额
function validateCancelAmt(){
	var advanceTotalAmt = parseFloat($("#advanceTotalAmt").val());//预付款金额
	var advanceCancelAmt = parseFloat($("#advanceCancelAmt").val());//预付款核销金额
	if(advanceTotalAmt < advanceCancelAmt){
		App.notyError("预付款核销金额不能大于预付款金额。");
		$("#advanceCancelAmt").focus();
		return false;
	}else{
		return true;
	}
	
}
//校验发票金额
function validaInvoiceAmt()
{
	var cntAmt = parseFloat($("#cntAmt").val());//合同金额
	var freezeTotalAmt =parseFloat($("#freezeTotalAmt").val());//冻结金额
	var normarlTotalAmt = parseFloat($("#normarlTotalAmt").val());//正常金额
	var advanceTotalAmt = parseFloat($("#advanceTotalAmt").val());//预付款金额
	var suspenseTotalAmt = parseFloat($("#suspenseTotalAmt").val());//暂收金额
	
	var payAmt = parseFloat($("#payAmt1").val());//付款金额
	var supAmt = parseFloat($("#suspenseAmt1").val());//暂收金额
	
	if(parseFloat((payAmt+supAmt).toFixed(2))>parseFloat((cntAmt-normarlTotalAmt-advanceTotalAmt-freezeTotalAmt-suspenseTotalAmt).toFixed(2)))
	{
		return false;
	}
	return true;
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
//根据暂收金额显示其他暂收信息
function showInfo(obj)
{
	if($.isBlank($(obj).val()) || $(obj).val() == 0)
	{
		
		$("#suspenseDate").removeAttr("valid");
		$("#suspenseDate").removeAttr("errorMsg");
		
		$("#sdP").hide();
		$("#sdvP").hide();
		

	}else
	{
		
		//linjia 20151229 若暂收金额不为0，暂收付款日设置为付款日期+1
		var payDate = $("#payDate").val();
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
		
		$("#suspenseDate").attr("valid",'');
		$("#suspenseDate").attr("errorMsg",'请选择暂收付款日期');
		
		$("#sdP").show();
		$("#sdvP").show();
	}
}

//校验预付款核销总金额 ==预付款核销本次金额的总和||预付款合同采购发票分配金额总和相等
function validAdvPayTotal(inputName,taxName)
{
	var advPayTotalMon = parseFloat($("#advanceCancelAmt").val());
	var totalMon = 0;
	var totalMonTax = 0;
	$("input[name='"+inputName+"']").each(function(){
		totalMon += parseFloat($(this).val());
	});
	if(""!=taxName)
	{
		$("input[name='"+taxName+"']").each(function(){
			totalMonTax += parseFloat($(this).val());
		});
	}
	
	if(parseFloat(advPayTotalMon.toFixed(2)) == parseFloat((parseFloat(totalMon) + parseFloat(totalMonTax)).toFixed(2)))
	{
		return true;
	}
	return false;
}
//校验付款金额+暂收金额==正常付款合同采购发票分配金额总和
function validPayDeviceTotal()
{
	var totalMon = parseFloat($("#payAmt1").val()) + parseFloat($("#suspenseAmt1").val());
	var totalPayDevMon = 0;
	var totalPayDevMonTax = 0;
	
	$("#payMon").find("input[name='payInvAmts']").each(function(){
		totalPayDevMon += parseFloat($(this).val());
	});
	$("#payMon").find("input[name='payAddTaxAmts']").each(function(){
		totalPayDevMonTax += parseFloat($(this).val());
	});
	
	if(parseFloat(totalMon.toFixed(2)) == parseFloat((parseFloat(totalPayDevMon) + parseFloat(totalPayDevMonTax)).toFixed(2)))
	{
		return true;
	}
	return false;
}
//校验发票金额 发票金额=暂收金额+付款金额+预付款核销金额
function validInvoiceAmt()
{
	var invAmt = parseFloat($("#invoiceAmt").val());//发票金额
	var totalMon = parseFloat($("#advanceCancelAmt").val()) + parseFloat($("#payAmt1").val()) + parseFloat($("#suspenseAmt1").val());
	if(parseFloat(invAmt.toFixed(2)) == parseFloat(totalMon.toFixed(2)))
		return true;
	return false;
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
//合同采购设备信息校验(先判断是否有核销信息)
function validDeviceAmt(flag)
{
	var v_flg = true;
	if(flag == "true")
	{
		var arr1 = 	$("#advPayMon").find("input[name='advancePayInvAmts']");//预付款不含税
		var arrtax1 = 	$("#advPayMon").find("input[name='advPayAddTaxAmts']");//预付款税额
		var arr2 = 	$("#payMon").find("input[name='payInvAmts']");//正常付款不含税
		var arrtax2 = 	$("#payMon").find("input[name='payAddTaxAmts']");//正常付款税额
		var arr3 = 	$("#payMon").find("input[name='remainDeviceAmts']");
		var arrtax3 = 	$("#payMon").find("input[name='remainDeviceTaxAmts']");
		for(var i=0;i<arr2.length;i++){
			var payInvAmt = parseFloat($(arr2[i]).val())+parseFloat($(arr1[i]).val());
			var remainDeviceAmt = parseFloat($(arr3[i]).val());
			if(parseFloat(payInvAmt.toFixed(2))>parseFloat(remainDeviceAmt.toFixed(2)))
			{
				v_flg = false;
				App.notyError("合同采购设备（正常付款和预付款）对应的不含税金额之和必须小于等于设备剩余不含税金额（不含税金额-已付不含税金额-冻结不含税金额）。");
				$($(arr2[i])).focus();
				break ;
			}

			var payInvTaxAmt = parseFloat($(arrtax2[i]).val())+parseFloat($(arrtax1[i]).val());
			var remainDeviceTaxAmt = parseFloat($(arrtax3[i]).val());
			if(parseFloat(payInvTaxAmt.toFixed(2))>parseFloat(remainDeviceTaxAmt.toFixed(2)))
			{
				v_flg = false;
				App.notyError("合同采购设备（正常付款和预付款）对应的税额之和必须小于等于设备剩余税额（税额-已付税额-冻结税额）。");
				$($(arrtax2[i])).focus();
				break ;
			}
		}
	}
	else
	{
		var arr2 = 	$("#payMon").find("input[name='payInvAmts']");//正常付款不含税金额
		var arrtax2 = 	$("#payMon").find("input[name='payAddTaxAmts']");//正常付款税额
		var arr3 = 	$("#payMon").find("input[name='remainDeviceAmts']");
		var arrtax3 = 	$("#payMon").find("input[name='remainDeviceTaxAmts']");
		for(var i=0;i<arr2.length;i++){
			var payInvAmt = parseFloat($(arr2[i]).val());
			var remainDeviceAmt = parseFloat($(arr3[i]).val());
			if(parseFloat(payInvAmt.toFixed(2))>parseFloat(remainDeviceAmt.toFixed(2)))
			{
				v_flg = false;
				App.notyError("合同采购设备（正常付款）对应的不含税金额必须小于等于设备剩余不含税金额（不含税金额-已付不含税金额-冻结不含税金额）。");
				$($(arr2[i])).focus();
				break ;
			}

			var payInvTaxAmt = parseFloat($(arrtax2[i]).val());
			var remainDeviceTaxAmt = parseFloat($(arrtax3[i]).val());
			if(parseFloat(payInvTaxAmt.toFixed(2))>parseFloat(remainDeviceTaxAmt.toFixed(2)))
			{
				v_flg = false;
				App.notyError("合同采购设备（正常付款）对应的税额必须小于等于设备剩余税额（税额-已付税额-冻结税额）。");
				$($(arrtax2[i])).focus();
				break ;
			}
		}
	}
	return v_flg;
}
//预付款核销明细查询
function advanceCancelDetail(payId)
{
	var url="<%=request.getContextPath()%>/pay/payAdd/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030309&flag=1&advancePayId="+payId;
	$.dialog.open(
		url,
		{
			padding:0,
			width: "65%",
			height: "80%",
			lock: true,
		    fixed: true,
			esc: true,
			resize:	false,
		    title:"预付款详情",
		    id:"dialogAdvCancelInfoDetail",
		    close: function(){
			}		
		}
	)
	
}


//校验附件类型是否有被选中的值
function checkAttaType(){
	var i = 0;
	$("#addPayForm").find("input[name='attachmentType']").each(function(){
		if($(this).is(':checked')){
			i++;
		}
	});
	if(i>0)
		return true;
	return false;
}
//合同采购设备(预付款)增值税金额校验
function checkAdvPayAddTaxAmts(obj){
	var invAmt = $(obj).val();	//发票分配金额
	//如果不为0且不为空,则发票行说明不能为空
	var objIvm =$(obj).parent().parent().find("#advPayAddTaxAmts");
	if(invAmt != null && invAmt != '' && invAmt != 0){
		$(objIvm).attr("valid",'');
		$(objIvm).attr("errorMsg","请输入增值税金额");
	}
	else{
		$(objIvm).removeAttr("valid");
		$(objIvm).removeAttr("errorMsg");
	}
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

//字符串转日期格式，strDate要转为日期格式的字符串
function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
     function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    return date;
}
//付款提交时校验物料的预算(提交前校验)
function submitAdd(button,url){
	if(!doValidate()){
		return ;
	}
	//20151229 linjia控制暂收付款日期
	var suspAmt = $("#suspenseAmt1").val();
	if($.isBlank(suspAmt) || suspAmt==0){
		$("#suspenseDate").val("2099-12-31");
	}
	//校验用户所在OU下的供应商信息
	var data = {};
   	data['providerCode'] = $("#providerCode").val();
	data['providerAddrCode'] = $("#providerAddrCode").val();
	App.ajaxSubmit("pay/payAdd/checkOuprovider.do?<%=WebConsts.FUNC_ID_KEY %>=03030331",
				{data:data,async:false}, 
				function(data) {
					var result=data.isExist;
					if(result){
						$( "<div>您所在OU下没有此供应商信息，是否继续？<br/>如果继续会被FMS退回。</div>" ).dialog({
							resizable: false,
							width: 390,
							height:'auto',
							modal: true,
							dialogClass: 'dClass',
							buttons: 
							[
								{
									text:"确认",click:function() {
										$( this ).dialog( "close" );
										bgtFrozenSubmit(button,url);
									}
								},
								{ 
									text:"取消",click:function() {
										$( this ).dialog( "close" );
									}
								}
							]
						});
					}else{
						bgtFrozenSubmit(button,url);
					}
				}
		);
	
}

function bgtFrozenSubmit(button,url)
{
	// 1）非待摊预提类合同，付款单新增时冻结预算（贷项通知单无需冻结） 
    // 2）待摊预提类合同，受益期之后的付款新增也调用此
	//ajax
	//非贷项通知单且非预提待摊 || 非贷项通知单且是预提待摊且 受益期月份后
	//》》非贷项通知单且预算处理类型是非预提待摊类
	if($("#isCreditNote").val()== '1' && '${payAddInfo.bgtType}' == '0'){
		App.ajaxSubmit("pay/payAdd/checkBgtFrozen.do?<%=WebConsts.FUNC_ID_KEY %>=03030323",{
			data : $('#addPayForm').serialize(),
			async : false
		}, function(data) {
			var flag = data.flag;
			if(flag == '1'){//冻结成功
				var form = $('#addPayForm')[0];
				form.action = '<%=request.getContextPath()%>/pay/payAdd/changePayStatus.do?<%=WebConsts.FUNC_ID_KEY %>=03030324';
				App.submit(form,false);
			}else if(flag =='0'){//冻结失败
				var bgtMsg = data.bgtMsg;
				var payId = data.payId;
				$.dialog({   
				    content: bgtMsg,   
					lock: true,
				    fixed: true,
					esc: true,
					resize:	false,
				    title:"预算冻结失败",
				    id:"dialogbgtMsg",
				    drag:false,
				    button :[{name: '查看明细', callback: function () {
				    	//查看明细
						var url="<%=request.getContextPath()%>/pay/paymodify/queryBgtFrozenFailMsg.do?<%=WebConsts.FUNC_ID_KEY%>=03030411&payId="+payId;
							$.dialog.open(
								url,
								{
									width: "70%",
									height: "80%",
									lock: true,
								    fixed: true,
								    title:"预算冻结失败明细",
								    id:"dialogbgtMsgInfoDetail",
									close: function(){
										//删除预算临时数据
										var data = {}; 
										data['payId'] = payId;
										App.ajaxSubmit("pay/paymodify/deleteBgtFrozenTemp.do?<%=WebConsts.FUNC_ID_KEY %>=03030412",{
											data : data,
											async : false
										}, function(data) {
											
										});
									}		
								}
							);
				    	
				    }}],
				    cancelVal: '关闭',   
				    cancel: function(){
				    	//删除预算临时数据
						var data = {}; 
						data['payId'] = payId;
						App.ajaxSubmit("pay/paymodify/deleteBgtFrozenTemp.do?<%=WebConsts.FUNC_ID_KEY %>=03030412",{
							data : data,
							async : false
						}, function(data) {
							
						});
				    }   
				});
			}else if(flag == '2'){
				//校验互斥锁失败
				alert(data.errorMsg);
			}
			else{
				alert("预算处理有误！");
			}
		});
	//  预提待摊类合同，处于正常预提待摊状态下的合同，合同状态为('20','21','23','25','32','35')，
	//1）在受益期内且合同不终止 
	//2）在受益期内合同终止且非一次待摊
	//3）在受益期内合同终止且一次待摊且系统月份<=终止日期月份
	//>>非贷项通知单且预算处理类型为预提待摊类的
	}else if($("#isCreditNote").val()== '1' && '${payAddInfo.bgtType}' == '1'){
		//校验预算是否透支，是则不让付款
		App.ajaxSubmit("pay/payAdd/checkBgtOverdraw.do?<%=WebConsts.FUNC_ID_KEY %>=03030325",{
			data : $('#addPayForm').serialize(),
			async : false
		}, function(data) {
			var bgtMsg = data.bgtMsg;
			if($.isBlank(bgtMsg)){
				//直接提交
				App.submitForm(button, url);
			}else{
				$( "<div>"+bgtMsg.replace(/&/g,'<br/>')+"</div>" ).dialog({
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
	}else{
		//直接提交
		App.submitForm(button, url);
	}
}

//计算发票金额
function countInvoiceAmt(obj){
	//如果输入的不合法则不进行相加运算
	var advanceCancelAmt=parseFloat($("#advanceCancelAmt").val());
	
	var payAmt1=parseFloat($("#payAmt1").val());
	var suspenseAmt1=parseFloat($("#suspenseAmt1").val());
	//var invoiceAmtSpan=parseFloat(advanceCancelAmt+payAmt1+suspenseAmt1);
	//$("#invoiceAmt").val(invoiceAmtSpan);
	$("#payAmtTotalSpan").html(parseFloat(payAmt1+suspenseAmt1).toFixed(2));
	$("#advanceCancelAmtTotalSpan").html(parseFloat(advanceCancelAmt).toFixed(2));
	$("#advanceCancelAmtSpan").html(parseFloat(advanceCancelAmt).toFixed(2));
}

function sumInvoiceAmt()
{
	var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
	var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额

	var sumAmt = (noTaxAmt + taxAmt).toFixed(2);
	$("#invoiceAmt").val(sumAmt);
	$("#invoiceAmtSpan").html(sumAmt);
}

//计算核销总金额、正常付款发票总金额、预付款发票总金额
function countTotal(name,taxname,parentId){
	//先检查输入的金额是否合法
	var countTotal = 0;
	var countTotalTax = 0;
	var totalAmt = 0;
	$("input[name='"+name+"']").each(function(){
		if($(this).val()){
			var thisVal=parseFloat($(this).val());
			countTotal=parseFloat(countTotal);
			countTotal=parseFloat(thisVal+countTotal);
		}
	});
	if(""!=taxname)
	{
		$("input[name='"+taxname+"']").each(function(){
			if($(this).val()){
				var thisVal=parseFloat($(this).val());
				countTotalTax=parseFloat(countTotalTax);
				countTotalTax=parseFloat(thisVal+countTotalTax);
			}
		});
	}
	totalAmt = parseFloat(countTotal) + parseFloat(countTotalTax);
	$("#"+parentId).html(totalAmt.toFixed(2));
}
//判断选择的供应商的编码和地点编码来显示可核销的预付款
function jugleProvider(providerCode,providerAddrCode){
	var num = 0;
	$("#advCancel2").find("input[name='providerCodes']").each(function(){
		var proCode = $(this).val();
		var proAddCode = $(this).parent().find("input[name='providerAddrCodes']").val();
		if(providerCode==proCode && providerAddrCode == proAddCode){
			//显示
			$(this).parent().parent().show();
			num++;
		}else{
			//隐藏并清空值
			$(this).parent().parent().hide();
			$(this).parent().parent().find("input[name='cancelAmts']").val(0);
			//清空物料的值，改为初始值
			$("#advPayMon").find("input[name='advancePayInvAmts']").each(function(){
				$(this).val(0);
				$(this).parent().parent().find("input[name='advPayAddTaxAmts']").val(0);
				$(this).parent().parent().find("input[name='advancePayIvrowMemos']").val('');
			});
		}
	});
	if(num==0){
		$("#advCancel2").hide();
		$("#advanceCancelAmt").val(0);
		$("#advanceCancelAmtTotalSpan").html(0);
		$("#advanceCancelAmtSpan").html(0);
		$("#advanceCancelAmt").hide();
		$("#advanceCancelAmtSp").show();
		$("#red1").hide();
	}else{
		$("#advCancel2").show();
		$("#advanceCancelAmt").show();
		$("#advanceCancelAmtSp").hide();
		$("#red1").show();
		$( "<div>当前存在预付款可核销！</div>" ).dialog({
			resizable: false,
			height:150,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确认": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}
//是否可选择按比例分配到物料
function showIsCheck(){
	var money =  parseFloat($("#payAmt1").val())+parseFloat($("#suspenseAmt1").val());
	if(money!=0 && money !='-'){
		$("#isCheck").removeAttr("disabled");
	}else{
		$("#isCheck").attr("disabled","disabled");
	}
	if($("#isCheck").is(':checked')){
		$("#isCheck").removeAttr("checked");
	}
}

//是否可选择按比例分配到物料
function showIsAdvCheck(){
	var money = parseFloat($("#advanceCancelAmt").val());
	if(money!=0 && money !='-'){
		$("#isAdvCheck").removeAttr("disabled");
	}else{
		$("#isAdvCheck").attr("disabled","disabled");
	}
	if($("#isAdvCheck").is(':checked')){
		$("#isAdvCheck").removeAttr("checked");
	}
}

function removeCheck()
{
	if($("#isCheck").is(':checked')){
		$("#isCheck").removeAttr("checked");
	}
	if($("#isAdvCheck").is(':checked')){
		$("#isAdvCheck").removeAttr("checked");
	}
}
//按比例分配到物料
function isCheckMoeny(){

	var invoiceAmt=parseFloat($("#invoiceAmt").val());
	var payAmt1=parseFloat($("#invoiceAmtNotax").val());
	var invoiceAmtTax=parseFloat($("#invoiceAmtTax").val());
	if(isNaN(invoiceAmt) || isNaN(payAmt1) || isNaN(invoiceAmtTax) || $("#invoiceAmt").val()==0)
	{
		$( "<div>请先输入发票头金额，确定分配比例。</div>" ).dialog({
			resizable: false,
			height:180,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确认": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		removeCheck();
		return ;
	}
	if(parseFloat(invoiceAmt.toFixed(2)) != parseFloat((payAmt1 + invoiceAmtTax).toFixed(2)))
	{
		$( "<div>发票总金额必须等于不含税金额 + 税额。</div>" ).dialog({
			resizable: false,
			height:150,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确认": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		removeCheck();
		return ;
	}
	//被选中
	if($("#isCheck").is(':checked')){
		var payAmtTotal = parseFloat((parseFloat($("#payAmt1").val())+parseFloat($("#suspenseAmt1").val())).toFixed(2));//正常付款+暂收金额
		var cntAmt = '${payAddInfo.cntAmt}';
		cntAmt = parseFloat(cntAmt);//合同不含税金额

		var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
		var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额
		var totalAmt = parseFloat((parseFloat(noTaxAmt) + parseFloat(taxAmt)).toFixed(2));

		var totalAssignAmt = 0;

		//正常付款物料
		if(!isNaN(payAmtTotal) && payAmtTotal != 0){
			var count = 0;//统计数量
			var totalM = 0;//统计不含税金额
			var totalN = 0;//统计税额
			var temp = 0;
			var tempTax = 0;
			var notaxPay = parseFloat((payAmtTotal*noTaxAmt/totalAmt).toFixed(2));//分配到不含税金额
			$("#payMon").find("tr").filter(".showedMatr").find("input[name='execAmts']").each(function(){
				var matrAmt = parseFloat($(this).val());//物料行的总金额
				var taxRate = parseFloat($(this).parent().parent().find("input[name='taxRates']").val());//税率
				if($("#payMon").find("tr").filter(".showedMatr").find("input[name='execAmts']").length-1==count){
					temp = parseFloat(notaxPay-totalM);
					tempTax = parseFloat(temp * taxRate);
					$(this).parent().parent().find("input[name='payInvAmts']").val(temp.toFixed(2));
					$(this).parent().parent().find("input[name='payAddTaxAmts']").val(tempTax.toFixed(2));
				}else{
					temp = parseFloat(notaxPay*matrAmt/cntAmt);
					tempTax = parseFloat(temp * taxRate);
					$(this).parent().parent().find("input[name='payInvAmts']").val(temp.toFixed(2));
					$(this).parent().parent().find("input[name='payAddTaxAmts']").val(tempTax.toFixed(2));
				}
				totalM += parseFloat(temp.toFixed(2));
				totalN += parseFloat(tempTax.toFixed(2));
				count++;
				checkPayIvrowMemos($(this).parent().parent().find("input[name='payInvAmts']"));
			});
			totalAssignAmt = (parseFloat(totalM) + parseFloat(totalN)).toFixed(2);
			$("#payInvAmtsSpan").html(totalAssignAmt);
		}else{
			$("#payMon").find("input[name='execAmts']").each(function(){
				$(this).parent().parent().find("input[name='payInvAmts']").val(0);
				$(this).parent().parent().find("input[name='payAddTaxAmts']").val(0);
				checkPayIvrowMemos($(this).parent().parent().find("input[name='payInvAmts']"));
				checkPayIvrowMemos($(this).parent().parent().find("input[name='payAddTaxAmts']"));
			});
			$("#payInvAmtsSpan").html(0);
		}
	}else{
		
	}
}

//按比例分配到物料
function isAdvCheckMoeny(){

	var invoiceAmt=parseFloat($("#invoiceAmt").val());
	var payAmt1=parseFloat($("#invoiceAmtNotax").val());
	var invoiceAmtTax=parseFloat($("#invoiceAmtTax").val());
	if(isNaN(invoiceAmt) || isNaN(payAmt1) || isNaN(invoiceAmtTax) || $("#invoiceAmt").val()==0)
	{
		$( "<div>请先输入发票头金额，确定分配比例。</div>" ).dialog({
			resizable: false,
			height:180,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确认": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		removeCheck();
		return ;
	}
	if(parseFloat(invoiceAmt.toFixed(2)) != parseFloat((payAmt1 + invoiceAmtTax).toFixed(2)))
	{
		$( "<div>发票总金额必须等于不含税金额 + 税额。</div>" ).dialog({
			resizable: false,
			height:150,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确认": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		removeCheck();
		return ;
	}

	//被选中
	if($("#isAdvCheck").is(':checked')){
		var canceAmtTotal = parseFloat($("#advanceCancelAmt").val());//核销金额
		var cntAmt = '${payAddInfo.cntAmt}';
		cntAmt = parseFloat(cntAmt);//合同不含税金额

		var noTaxAmt = parseFloat($("#invoiceAmtNotax").val());//不含税金额
		var taxAmt = parseFloat($("#invoiceAmtTax").val());//税额
		var totalAmt = parseFloat((parseFloat(noTaxAmt) + parseFloat(taxAmt)).toFixed(2));

		var totalAssignAmt = 0;

		//预付款核销物料
		if(!isNaN(canceAmtTotal) && canceAmtTotal != 0){
			var count = 0;//统计数量
			var totalM = 0;//统计不含税金额
			var totalN = 0;//统计税额
			var temp = 0;
			var tempTax = 0;
			var notaxPay = parseFloat((canceAmtTotal*noTaxAmt/totalAmt).toFixed(2));//分配到不含税金额
			$("#advPayMon").find("input[name='execAmts']").each(function(){
				var matrAmt = parseFloat($(this).val());//物料行的总金额
				var taxRate = parseFloat($(this).parent().parent().find("input[name='taxRates']").val());//税率
				if($("#advPayMon").find("input[name='execAmts']").length-1==count){
					temp = parseFloat((notaxPay-totalM).toFixed(2));
					tempTax = parseFloat(temp * taxRate);
					$(this).parent().parent().find("input[name='advancePayInvAmts']").val(temp.toFixed(2));
					$(this).parent().parent().find("input[name='advPayAddTaxAmts']").val(tempTax.toFixed(2));
				}else{
					temp= parseFloat(notaxPay*matrAmt/cntAmt);
					tempTax = parseFloat(temp * taxRate);
					$(this).parent().parent().find("input[name='advancePayInvAmts']").val(temp.toFixed(2));
					$(this).parent().parent().find("input[name='advPayAddTaxAmts']").val(tempTax.toFixed(2));
				}
				totalM += parseFloat(temp.toFixed(2));
				totalN += parseFloat(tempTax.toFixed(2));
				count++;
				checkAdvPayIvrowMemos($(this).parent().parent().find("input[name='advancePayInvAmts']"));
			});	
			totalAssignAmt = (parseFloat(totalM) + parseFloat(totalN)).toFixed(2);
			$("#advancePayInvAmtsSpan").html(totalAssignAmt);
		}else{
			$("#advPayMon").find("input[name='execAmts']").each(function(){
				$(this).parent().parent().find("input[name='advancePayInvAmts']").val(0);
				$(this).parent().parent().find("input[name='advPayAddTaxAmts']").val(0);
				checkAdvPayIvrowMemos($(this).parent().parent().find("input[name='advancePayInvAmts']"));
				checkAdvPayIvrowMemos($(this).parent().parent().find("input[name='advPayAddTaxAmts']"));
			});	
			$("#advancePayInvAmtsSpan").html(0);
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
	data['invoiceId']= $.trim($("#invoiceId").val());
	data['payId']= $.trim($("#payId").val());
	data['flag']= 0 ;
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

function countTaxValue(noTaxObj,taxName,taxRateName)
{
	var notaxValue = noTaxObj.value;
	var taxRate = $(noTaxObj).parent().parent().find("input:hidden[name='"+taxRateName+"']").val();
	var taxValue = (parseFloat(notaxValue) * parseFloat(taxRate)).toFixed(2);
	$(noTaxObj).parent().parent().find("input[name='"+taxName+"']").val(taxValue);
}

function trimblank(obj)
{
	if($.isBlank(obj.value))
	{
		$(obj).val('');
	}
}
</script>
</head>

<body>
<p:authFunc funcArray="03030307,03030308,03030309"/>
<form action="" method="post" id="addPayForm">
	<p:token/>
	<table>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4" >
							合同信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">合同号</td>
						<td class="tdRight" width="30%">
							${payAddInfo.cntNum}
							<input type="hidden" id="feeEndDate" name="feeEndDate" value="${payAddInfo.feeEndDate}"/>
							<input type="hidden" id="isPrepaidProvision" name="isPrepaidProvision" value="${payAddInfo.isPrepaidProvision}"/>
							<input type="hidden" id="isOrder" name="isOrder" value="${payAddInfo.isOrder}"/>
							<input type="hidden" id="oncePrepaid" name="oncePrepaid" value="${payAddInfo.oncePrepaid}"/>
							<input type="hidden" id="stopDate" name="stopDate" value="${payAddInfo.stopDate}"/>
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
							<input type="hidden" id="feeType" name="feeType" value="${payAddInfo.feeType}"/>
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
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="${payAddInfo.freezeTotalAmt}"/>
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
							<input type="hidden" id="isCreditNote" name="isCreditNote" value="1"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceId" name="invoiceId" maxlength="50" value='' onblur="checkInvoiceId();" class="base-input-text" valid errorMsg="请输入发票号"/>
						</td>
						<td class="tdLeft">确认发票号<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="invoiceId1" name="invoiceId1" maxlength="50" value='' onblur="checkInvoiceId();" class="base-input-text" valid errorMsg="请再次确认输入发票号"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft prompt" width="20%">附件张数</td>
						<td class="tdRight" width="30%"> 
							<input type="text" id="attachmentNum" name="attachmentNum" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/>
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
							<input type="hidden" id="providerCode"  name="providerCode" value="${payAddInfo.providerCode}" class="base-input-text"/>
							<input type="hidden" id="providerType"  name="providerType" value="${payAddInfo.providerType}" class="base-input-text"/>
							<input type="hidden" id="providerAddr"  name="providerAddr" value="${payAddInfo.providerAddr}" class="base-input-text"/>
							<input type="hidden" id="providerAddrCode"  name="providerAddrCode" value="${payAddInfo.providerAddrCode}" class="base-input-text"/>
							<input type="hidden" id="actName"  name="actName" value="${payAddInfo.actName}" class="base-input-text"/>
							<input type="hidden" id="bankName" name="bankName"  class="base-input-text" value="${payAddInfo.bankName}" />
							<input type="hidden" id="bankCode"  name="bankCode" value="${payAddInfo.bankCode}" class="base-input-text"/>
							<input type="hidden" id="bankArea"  name="bankArea" value="${payAddInfo.bankArea}" class="base-input-text"/>
							<input type="hidden" id="actType"  name="actType" value="${payAddInfo.actType}" class="base-input-text"/>

							<input type="text"  id="providerName" name="providerName" value="${payAddInfo.providerName}" class="base-input-text" readonly="readonly" valid errorMsg="请输入收款单位" onclick="providerQuery();"/>
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							<span id="provActNoSpan">${payAddInfo.provActNo}</span>
							<input type="hidden" id="provActNo" name="provActNo"  class="base-input-text" value="${payAddInfo.provActNo}" readonly="readonly" />
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payAddInfo.provActCurr}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
							<span id="bankNameSpan">${payAddInfo.bankInfo}</span>
							<input type="hidden" id="bankInfo"  name="bankInfo" value="${payAddInfo.bankInfo}" class="base-input-text"/>
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
						<td class="tdLeft" rowspan="2">发票总金额=(预付款核销金额+付款金额+暂收金额)<span class="red">*</span></td>
						<td class="tdRight" rowspan="2">
							<span id="invoiceAmtSpan">0</span>
							<input type="hidden" id="invoiceAmt" onkeyup="$.clearNoNum(this);removeCheck();" onblur="$.onBlurReplace(this);" name="invoiceAmt" maxlength="18" value="0"  class="base-input-text" valid errorMsg="请输入发票金额"/>
						</td>
						<td class="tdLeft">不含税金额<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" onkeyup="$.clearNoNum(this);removeCheck();" onblur="$.onBlurReplace(this);sumInvoiceAmt();" id="invoiceAmtNotax" name="invoiceAmtNotax" maxlength="18" value="0"  class="base-input-text" valid errorMsg="请输入发票金额"/>
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">税额<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" onkeyup="$.clearNoNum(this);removeCheck();" onblur="$.onBlurReplace(this);sumInvoiceAmt();" id="invoiceAmtTax" name="invoiceAmtTax" maxlength="18" value="0"  class="base-input-text" valid errorMsg="请输入发票金额"/>
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft" colspan="4" >
							<span>预付款核销金额<span class="red" id="red1">*</span>&nbsp;&nbsp;<span id="advanceCancelAmtSp" style="display: none;margin-right: 150px;">0</span><input  type="text" id="advanceCancelAmt" name="advanceCancelAmt" onkeyup="$.clearNoNum(this);showIsAdvCheck();" onblur ="validateCancelAmt();countInvoiceAmt(this);$.onBlurReplace(this);" value="0" maxlength="18" style="width: 150px" class="base-input-text" valid errorMsg="请输入预付款核销总金额"  />
							&nbsp;&nbsp;付款金额<span class="red" id="red3">*</span>&nbsp;&nbsp;<span id="payAmt1Sp" style="display: none;margin-right: 150px;">0</span><input onkeyup="$.clearNoNum(this);showIsCheck();"  onblur="countInvoiceAmt(this);$.onBlurReplace(this);setTwoValue(this,'payAmtSpan');" type="text" id="payAmt1" name="payAmt"  maxlength="18" style="width: 150px" class="base-input-text" value="0" valid errorMsg="请输入付款金额" />
							&nbsp;&nbsp;暂收金额<span class="red" id="red2">*</span>&nbsp;&nbsp;<span id="suspenseAmt1Sp" style="display: none;margin-right: 150px;">0</span><input onkeyup="$.clearNoNum(this);showIsCheck();" onblur="countInvoiceAmt(this);showInfo(this);$.onBlurReplace(this);setTwoValue(this,'suspenseAmtSpan');" type="text" id="suspenseAmt1" name="suspenseAmt" maxlength="18" style="width: 150px" value="0"  class="base-input-text"  valid errorMsg="请输入暂收金额" />
							</span>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${!empty payAdvanceCancelList}">
		<tr id="advCancel1">
			<td></td>
		</tr>
		<tr id="advCancel2">
			<td>
				<table id="advCancelId" class="tableList">
					<tr class="collspan-control">
						<th colspan="5">预付款核销信息【预付款核销总金额=<span id="advanceCancelAmtSpan">0</span>】</th>
					</tr>
					<tr>
						<th width="20%">预付款批次</th>
						<th width="20%">预付款金额(元)</th>
						<th width="20%">已核销金额(元)</th>
						<th width="25%">本次核销金额(元)【累计金额=<span id="cancelAmtsSpan">0</span>】</th>
						<th width="15%">操作</th>
					</tr>
					<c:forEach items="${payAdvanceCancelList}" var="bean">
						<tr>
							<td>${bean.payId}
							<input type="hidden" id="advancePayIds" name="advancePayIds" value="${bean.payId}"  class="base-input-text"/>
							<input type="hidden" name="providerCodes" value="${bean.providerCode}"/>
							<input type="hidden" name="providerAddrCodes" value="${bean.providerAddrCode}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/><input type="hidden" id="remainCancel" name="remainCancel" value="${bean.payAmt-bean.cancelAmt}"  class="base-input-text"/></td>
							<td><fmt:formatNumber type="number" value="${bean.cancelAmt}" minFractionDigits="2"/></td>
							<td><input id="cancelAmts" name="cancelAmts" value="0"  class="base-input-text" maxlength="18" onkeyup="$.clearNoNum(this);" onblur="countTotal('cancelAmts','','cancelAmtsSpan');$.onBlurReplace(this);"/></td>
							<td>
								<div class="detail">
									<a href="#" onclick="advanceCancelDetail('${bean.payId}');" title="明细"></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table id="advPayMon" class="tableList">
					<tr>
						<th colspan="100">
							<table>
								<tr>
									<td style="border:0;text-align:right;" width="65%" class="collspan-control-pay"><b>合同采购设备(预付款)【核销总金额=<span id="advanceCancelAmtTotalSpan">0</span>】</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td style="border:0;text-align:right;" width="35%">
										<label>
											<input type="checkbox" id="isAdvCheck" name="isAdvCheck" value="1" disabled="disabled" onclick="isAdvCheckMoeny();"/>金额是否按比例分配到物料
										</label>
									</td>
								</tr>
							</table>
						</th>
					</tr>
					<tr>
						<th width="5%" rowspan="2">核算码</th>
						<th width="6%" rowspan="2">项目</th>
						<th width="6%" rowspan="2">物料名称</th>
						<th width="6%" rowspan="2">费用部门</th>
						<th width="23%" colspan="3">不含税金额(元)</th>
						<th width="5%" rowspan="2">税码</th>
						<th width="23%" colspan="3">税额(元)</th>
						<th width="20%" colspan="2">发票分配金额(元) <br>【累计金额=<span id="advancePayInvAmtsSpan">0</span>】</th>
						<th width="5%" rowspan="2">发票行说明</th>
					</tr>
					<tr>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>不含税金额</th>
						<th>税额</th>
					</tr>
					<c:forEach items="${devices}" var="bean">
					<c:if test="${bean.totalDeviceAmt != bean.totalPayedAmt+bean.totalFreezeAmt}">
						<tr>
							<td>${bean.cglCode }
								<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" />
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/>
							<input type="hidden" id="execAmts" name="execAmts" value="${bean.execAmt}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							
							<td>
								${bean.taxCode }
								<input type="hidden" id="taxRates" name="taxRates" value="${bean.taxRate}" />
							</td>
							
							<td><fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/>
							<input type="hidden" id="taxAmts" name="taxAmts" value="${bean.taxAmt}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/></td>
							
							<td><input id="advancePayInvAmts" onkeyup="$.clearNoNum(this);" onblur="checkAdvPayIvrowMemos(this);$.onBlurReplace(this);countTaxValue(this,'advPayAddTaxAmts','taxRates');countTotal('advancePayInvAmts','advPayAddTaxAmts','advancePayInvAmtsSpan');" name="advancePayInvAmts" value="0" maxlength="18"  class="base-input-text" style="width: 100px;" valid errorMsg="请输入预付款设备发票分配金额"/></td>
							<td>
								<c:choose>
								    <c:when test="${bean.taxRate eq 0 or bean.hasTaxrow eq 'N'}">
								        <input id="advPayAddTaxAmts" name="advPayAddTaxAmts" readonly="readonly" value="0" maxlength="18"  class="base-input-text" style="width: 100px;" valid errorMsg="请输入预付款设备税额" onkeyup="$.clearNoNum(this);" onblur="checkAdvPayAddTaxAmts(this);countTotal('advancePayInvAmts','advPayAddTaxAmts','advancePayInvAmtsSpan');$.onBlurReplace(this);" title="税率为0，税额不允许更改"/>
									</c:when>
									<c:otherwise>
										<input id="advPayAddTaxAmts" name="advPayAddTaxAmts" value="0" maxlength="18"  class="base-input-text" style="width: 100px;" valid errorMsg="请输入预付款设备税额" onkeyup="$.clearNoNum(this);" onblur="checkAdvPayAddTaxAmts(this);countTotal('advancePayInvAmts','advPayAddTaxAmts','advancePayInvAmtsSpan');$.onBlurReplace(this);"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td><input id="advancePayIvrowMemos" name="advancePayIvrowMemos" onkeyup="trimblank(this)" class="base-input-text" style="width: 100px;" maxlength="80"/></td>
						</tr>
					</c:if>
					</c:forEach>
				</table>
			</td>
		</tr>
		</c:if>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table id="payMon" class="tableList">
					<tr>
						<th colspan="100">
							<table>
								<tr>
									<td style="border:0;text-align:right;" width="78%" class="collspan-control-pay"><b>合同采购设备(正常付款金额=付款金额+暂收金额)【正常付款总金额=<span id="payAmtTotalSpan">0</span>】</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td style="border:0;text-align:right;" width="22%">
										<label>
											<input type="checkbox" id="isCheck" name="isCheck" value="1" disabled="disabled" onclick="isCheckMoeny();"/>金额是否按比例分配到物料
										</label>
									</td>
								</tr>
							</table>
						</th>
					</tr>
					
					<tr>
						<th width="5%" rowspan="2">核算码</th>
						<th width="6%" rowspan="2">项目</th>
						<th width="6%" rowspan="2">物料名称</th>
						<th width="6%" rowspan="2">费用部门</th>
						<th width="23%" colspan="3">不含税金额(元)</th>
						<th width="5%" rowspan="2">税码</th>
						<th width="23%" colspan="3">税额(元)</th>
						<th width="20%" colspan="2">发票分配金额(元) <br>【累计金额=<span id="payInvAmtsSpan">0</span>】</th>
						<th width="6%" rowspan="2">发票行说明</th>
					</tr>
					<tr>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>不含税金额</th>
						<th>税额</th>
					</tr>
					<c:forEach items="${devices}" var="bean">
						<c:if test="${(bean.totalDeviceAmt == bean.totalPayedAmt+bean.totalFreezeAmt)}">
						<tr style="background:#EFC87C;" class="backCor">
							<td>${bean.cglCode}
								<input type="hidden" id="subIds" name="subIds" value="${bean.subId}" />
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td>
							<fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/>
							<input type="hidden" id="execAmts" name="execAmts" value="${bean.execAmt}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/><input type="hidden" id="payedAmt" value="${bean.payedAmt}"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/><input type="hidden" id="freezeAmt" value="${bean.freezeAmt}"/></td>
							
							<td>
								${bean.taxCode}
								<input type="hidden" id="taxRates" name="taxRates" value="${bean.taxRate}" />
							</td>
							
							<td>
							<fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/>
							<input type="hidden" id="taxAmts" name="taxAmts" value="${bean.taxAmt}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/><input type="hidden" id="payedAmt" value="${bean.payedAmt}"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/><input type="hidden" id="freezeAmt" value="${bean.freezeAmt}"/></td>
							
							<td>
								<input type="hidden" id="remainDeviceAmts" name="remainDeviceAmts" value="${bean.execAmt - bean.payedAmt-bean.freezeAmt}"/>
								<span class="backSpan">0</span>
								<input  id="payInvAmts" name="payInvAmts"  value="0" maxlength="18" class="base-input-text backInput" style="width: 100px;display: none;" valid errorMsg="请输入付款设备发票分配金额" onkeyup="$.clearNoNum(this);" onblur="checkPayIvrowMemos(this);countTotal('payInvAmts','payAddTaxAmts','payInvAmtsSpan');$.onBlurReplace(this);"/>
							</td>
							<td>
								<input type="hidden" id="remainDeviceTaxAmts" name="remainDeviceTaxAmts" value="${bean.taxAmt - bean.payedAmtTax-bean.freezeAmtTax}"/>
								<span class="backSpan">0</span>
								<input  id="payAddTaxAmts" name="payAddTaxAmts" value="0" maxlength="18" class="base-input-text backInput" style="width: 100px;display: none;" valid errorMsg="请输入预付款设备税额" onkeyup="$.clearNoNum(this);" onblur="checkPayAddTaxAmts(this);countTotal('payInvAmts','payAddTaxAmts','payInvAmtsSpan');$.onBlurReplace(this);"/>
							</td>
							<td>
							<span class="backSpan"></span>
							<input  id="payIvrowMemos" name="payIvrowMemos" onkeyup="trimblank(this)" maxlength="80" class="base-input-text backInput" style="width: 100px;display: none;" /></td>
						</tr>
						</c:if>
						
						<c:if test="${bean.totalDeviceAmt != bean.totalPayedAmt+bean.totalFreezeAmt}">
						<tr class="showedMatr">
							<td>${bean.cglCode}
								<input type="hidden" id="subIds" name="subIds" value="${bean.subId}" />
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/>
							<input type="hidden" id="execAmts" name="execAmts" value="${bean.execAmt}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/><input type="hidden" id="payedAmt" value="${bean.payedAmt}"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/><input type="hidden" id="freezeAmt" value="${bean.freezeAmt}"/></td>
							
							<td>
								${bean.taxCode}
								<input type="hidden" id="taxRates" name="taxRates" value="${bean.taxRate}" />
							</td>
							
							<td><fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/>
							<input type="hidden" id="taxAmts" name="taxAmts" value="${bean.taxAmt}" />
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/><input type="hidden" id="payedAmtTax" value="${bean.payedAmtTax}"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/><input type="hidden" id="freezeAmtTax" value="${bean.freezeAmtTax}"/></td>
							
							<td>
								<input type="hidden" id="remainDeviceAmts" name="remainDeviceAmts" value="${bean.execAmt - bean.payedAmt-bean.freezeAmt}"/>
								<input id="payInvAmts" name="payInvAmts"  value="0" maxlength="18" class="base-input-text backInput" style="width: 100px;" valid errorMsg="请输入付款设备发票分配金额" onkeyup="$.clearNoNum(this);" onblur="checkPayIvrowMemos(this);$.onBlurReplace(this);countTaxValue(this,'payAddTaxAmts','taxRates');countTotal('payInvAmts','payAddTaxAmts','payInvAmtsSpan');"/>
								<span class="backSpan" style="display: none;">0</span>
							</td>
							<td>
								<input type="hidden" id="remainDeviceTaxAmts" name="remainDeviceTaxAmts" value="${bean.taxAmt - bean.payedAmtTax-bean.freezeAmtTax}"/>
								<c:choose>
								    <c:when test="${bean.taxRate eq 0 or bean.hasTaxrow eq 'N'}">
								        <input id="payAddTaxAmts" name="payAddTaxAmts"  readonly="readonly" value="0" maxlength="18" class="base-input-text backInput" style="width: 100px;" valid errorMsg="请输入付款设备税额" onkeyup="$.clearNoNum(this);" onblur="checkPayAddTaxAmts(this);countTotal('payInvAmts','payAddTaxAmts','payInvAmtsSpan');$.onBlurReplace(this);" title="税率为0，税额不允许更改"/>
									</c:when>
									<c:otherwise>
										<input id="payAddTaxAmts" name="payAddTaxAmts"  value="0" maxlength="18" class="base-input-text backInput" style="width: 100px;" valid errorMsg="请输入付款设备税额" onkeyup="$.clearNoNum(this);" onblur="checkPayAddTaxAmts(this);countTotal('payInvAmts','payAddTaxAmts','payInvAmtsSpan');$.onBlurReplace(this);"/>
									</c:otherwise>
								</c:choose>
								<span class="backSpan" style="display: none;">0</span>
							</td>
							<td>
							<span class="backSpan" style="display: none;"></span>
							<input id="payIvrowMemos" name="payIvrowMemos" onkeyup="trimblank(this)" maxlength="80" class="base-input-text backInput" style="width: 100px;" /></td>
						</tr>
						</c:if>
					</c:forEach>
				</table>
				<table id="showAndHide">
					<tr class="collspan-control1">
						<th colspan="4" >正常付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft">付款金额</td>
						<td class="tdRight">
							<span id="payAmtSpan">0</span>
						</td>
						<td class="tdLeft">付款日期<span class="red">*</span></td>
						<td class="tdRight">
							<input type="text" id="payDate" name="payDate" onchange="changeDate(this);" class="base-input-text" valid errorMsg="请选择付款日期" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
							<span id="suspenseAmtSpan">0</span>
						</td>
						<td class="tdLeft"><div id="sdP" style="display: none;">暂收付款日期<span class="red">*</span></div></td>
						<td class="tdRight">
							<div id="sdvP" style="display: none;"><input type="text" id="suspenseDate" name="suspenseDate"  class="base-input-text" readonly="readonly"/></div>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票说明<span class="red">*</span><br/>(<span id='memoSpan1'>0/200</span>)</td>
						<td class="tdRight" colspan="3">
							<textarea id="invoiceMemo" cols="60" rows="5"  name="invoiceMemo"  onkeyup="$_showWarnWhenOverLen1(this,200,'memoSpan1');trimblank(this);" class="base-textArea" valid errorMsg="请输入发票说明"></textArea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
				<p:button funcId="03030307" value="保存为草稿"/>
		    	<p:button funcId="03030308" value="提交至待复核" onclick="submitAdd"/>
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>