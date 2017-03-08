<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同变更确认</title>
<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<style type="text/css">
	a:HOVER {
		cursor: pointer;
	}
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">


function hsm(subId,amt,strBuf){ 
    this.subId=subId; 
    this.amt=amt; 
    this.strBuf=strBuf;
}
function param(startDate,endDate,paramList){ 
    this.startDate = startDate;
    this.endDate = endDate;
    this.paramList = paramList; 
}
//获取费用类型的弹出页
function feeTypePage(){
	var url="<%=request.getContextPath()%>/contract/initiate/feeTypePage.do?<%=WebConsts.FUNC_ID_KEY %>=0302010102";
	var data = {};
	if(!checkFeeAmt()){
		App.notyError("采购项目总金额与合同金额不一致！");
		return false;
	}
	if($.isBlank($("#tableDiv").html())){
		var feeStartDate = $("#feeStartDate").val();
	    var feeEndDate = $("#feeEndDate").val();
	    if($.isBlank(feeStartDate)){
	    	App.notyError("请先输入受益起始日期！");
	    	return false;
	    }else if($.isBlank(feeEndDate)){
	    	App.notyError("请先输入受益结束日期！");
	    	return false;
	    }
	    var paramList = new Array();
	    var totalNumTrList = $("#totalNumTable tr");
	    if(totalNumTrList.length < 2){
	    	App.notyError("请先选择采购项目！");
	    	return false;
	    }
	    for(var i=1;i<totalNumTrList.length;i++){
	    	var cglCode = $(totalNumTrList[i]).find("input[name='trCglCode']").val();
	    	var matrCode = $(totalNumTrList[i]).find("input[name='matrCode']").val();
	    	var matrName = $(totalNumTrList[i]).find("input[name='matrName']").val();
	    	var hsmAmt = $(totalNumTrList[i]).find("input[name='execAmt']").val();
	    	var feeDept = $(totalNumTrList[i]).find("input[name='feeDept']").val();
	    	var feeDeptName = $(totalNumTrList[i]).find("input[name='feeDeptName']").val();
	    	var specialName = $(totalNumTrList[i]).find("select[name='special']").next().val();
	    	var special = $(totalNumTrList[i]).find("select[name='special']").val();
	    	var referenceName = $(totalNumTrList[i]).find("select[name='reference']").next().val();
	    	var reference = $(totalNumTrList[i]).find("select[name='reference']").val();
	    	if($.isBlank(cglCode)){
	    		App.notyError("请先选择物料类型！");
	    		$(totalNumTrList[i]).find("input[name='matrName']").focus();
	        	return false;
	    	}
	    	if($.isBlank(feeDept)){
	    		App.notyError("请先选择费用承担部门信息！");
	    		$(totalNumTrList[i]).find("input[name='feeDept']").focus();
	        	return false;
	    	}
	    	if($.isBlank(hsmAmt)){
	    		App.notyError("请先输入物料采购的单价和数量！");
	    		$(totalNumTrList[i]).find("input[name='execPrice']").focus();
	        	return false;
	    	}
	    	if($.isBlank(special)){
	    		App.notyError("请先输入专项信息！");
	    		$(totalNumTrList[i]).find("select[name='special']").focus();
	        	return false;
	    	}
	    	if($.isBlank(reference)){
	    		App.notyError("请先输入参考信息！");
	    		$(totalNumTrList[i]).find("select[name='reference']").focus();
	        	return false;
	    	}
	    	//var strBuf = feeDeptName+ 物料编码+物料名称+核算码+specialName + referenceName; 下划线分隔
	    	var strBuf = feeDeptName + "_" + matrCode + "_" + matrName + "_" +cglCode + "_" + specialName + "_" + referenceName;
	    	var getHsm = new hsm(i,hsmAmt,strBuf);
	    	paramList[i-1] = getHsm;
	    }
	    var paramData = new param(feeStartDate,feeEndDate,paramList);
	    data['paramData'] = paramData;
	}else{
		var totalNumTrList = $("#totalNumTable tr");
		data['subIdLength'] = totalNumTrList.length;
		data['targetT'] = $("#tableDiv").html();
		data['feeStartFlag'] = '${feeStartFlag}';
		if(typeof $("#hasModifyFee") != 'undefined'){
			data['hasModifyFee'] = $("#hasModifyFee").val();
		}
	}
	App.submitShowProgress();
	window.dialogArguments=data;
	var returnValue = window.showModalDialog(url, data, "dialogHeight=600px;dialogWidth=1200px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		$("#tableDiv").empty();
		$("#tableDiv").append(returnValue);
	}
}
//当费用类型的查询条件发生改变时，清空tableDiv
function removeFeeInfoTable(){
	$("#tableDiv").empty();
}
//校验采购项目与合同金额是否匹配
function checkFeeAmt(){
    var totalNumTrList = $("#totalNumTable tr");
	var totalAmt = 0;
	if(totalNumTrList.length < 2){
		App.notyError("采购项目数量不能为空！");
		return false;
	}
	for(var i=1;i<totalNumTrList.length;i++){
		var execAmt = $(totalNumTrList[i]).find("input[name='execAmt']").val();
		totalAmt = $.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat(execAmt,3),2);
	} 
	if(totalAmt*1!=$("#cntAmt").val().replace(/\,/g,'')*1){
		return false;
	}
	return true;
}
//获取项目预算，并校验是否超出
function getProjAmt(){
	var overProjName = "";
	var url = "contract/modify/checkProAmt.do?<%=WebConsts.FUNC_ID_KEY%>=0302010203";
	App.ajaxSubmitForm(url, $("#modifyForm"),  
    		function(data){
				var projName = data.projName;
				overProjName = projName;
			});
	return overProjName;
}

// 添加项目预算金额校验
function checkProjAmt(){
	var totalNumTrList = $("#totalNumTable tr");
	if(totalNumTrList.length < 2){
		App.notyError("采购项目数量不能为空！");
		return false;
	}
	var pass = true;
	var projName = getProjAmt();
	if(null != projName && "" != projName){
		App.notyError("项目"+projName+"超出预算，请修改后再重新确认合同！");
		pass = false;
	}
	return pass;
}



//校验合同采购金额是否超出预算
// function checkProjAmt(cntNum){
// 	var result =  true;
<%-- 	var url = "contract/confirm/checkProjAmt.do?<%=WebConsts.FUNC_ID_KEY%>=0302010304"; --%>
// 	var data = {};
// 	data['cntNum'] = cntNum;
// 	App.ajaxSubmit(url, {data : data,async : false}, 
//     		function(data){
// 				var projName = data.projName;
// 				if("" != projName && null != projName){
// 					App.notyError("项目"+projName+"超出预算，请修改后再重新确认合同！");
// 					result = false;
// 				}
// 			});
// 	return result;
// }

</script>
<script type="text/javascript">
var isRelatedYN = '';
var curFeeType = '${cnt.feeType}'; //&& '' != '${cnt.feeType}' ? '${cnt.feeType}':'0';
var reg=/^[1-9]{1}[0-9]*$/;
function pageInit() {
	infoHide(46);
	$_showWarnWhenOverLen1(document.getElementById("remark"),2000,'authmemoSpan');
	$_showWarnWhenOverLen1(document.getElementById("memo"),2000,'authmemoSpan');
	App.jqueryAutocomplete();
	$(".selectC").combobox();
	$(".selectD").combobox();
	$("#TenancyDzTable .ui-autocomplete-input").css("width","40px");
	$("#signDate,#feeStartDate,#feeEndDate,#beginDate,#toDate,input[name='fromDate'],input[name='toDate'],.jdDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
	//初始化是否存在关联合同号下拉框
	var relateCntNull = '${cnt.cntNumRelated}';
	$("#cntNumRelated").removeAttr("valid");
	$("#isRelatedYN").parent().parent().attr("colspan","3");
	if($.isBlank(relateCntNull)){
		isRelatedYN = '1';
		$(".relateCntTd").hide();
		$("#isRelatedYN").combobox("destroy").val("1").combobox();
	}else{
		isRelatedYN = '0';
		$(".relateCntTd").show();
		$("#relateCntNull").attr("valid","valid");
		$("#isRelatedYN").combobox("destroy").val("0").combobox();
		$("#isRelatedYN").parent().parent().attr("colspan","1");
	}
	
	if($.isBlank(curFeeType)){
		$("#feeType").combobox("destroy").val("0").combobox();
	}
	
	if($.isBlank($("#feeSubType").val())){
		$("#feeSubType").combobox("destroy").val("0").combobox();
	}
	computTotalNum(this);
	changLxlx("init");
// 	changeCntType("init");
	changePayTerm("init");
// 	changeIsSpec();
	if('3'=='${cnt.payTerm}'){
		changeStageType();
	}
	
	$("#providerName").parent().attr("colspan","3");
	if(''!='${cnt.srcPoviderName}' && null!='${cnt.srcPoviderName}'){
		//员工供应商
		$("#srcPoviderTdLeft").show();
		$("#srcPoviderTdRight").show();
		$("#srcPovider").attr("valid","");
		$("#providerName").parent().attr("colspan","1");
	}

 	$(".selectS").combobox();
	$("#totalNumTable .ui-autocomplete-input").css("width","80px");
	$("#totalNumTable").find("input[name='feeDeptName']").css("width","190px");
	//$("#totalNumTable td").attr("style","padding-left: 3px;padding-right: 7px;");
	
	var hasSucOrderDev = '${hasSucOrderDev}';
	if(hasSucOrderDev || hasSucOrderDev == 'true'){
		orderBackInit();
	}
	//var tableCombine=new TableCombine();
	//tableCombine.rowspanTable("totalNumTable", 2, null, 1, 1, null, null);
	//tableCombine.rowspanTableTd("totalNumTable", "2", "2");
	
	var changeVal = $("#feeSubType").val();
	$("#houseTr").hide().removeClass("collspan");
	
	if(changeVal == 1){
		//房屋租赁类
		$("#houseTr").show().addClass("collspan");
	}
	
}
//合并单元格
$(function (){
	table("feeDeptH","feeDetpTd");//隐藏框id  td 的id
	
});
function table(inputId,tdId){
	var str1="";
	var table1 = $("#totalNumTable");
	var trs = table1[0].rows;
	for(var i=1;i<=trs.length-1;i++){
		var flag1_1 = true;
		if(str1 == $("#"+inputId+i).val() ){
			//当着两者相同时 就不让他重复循环
			continue;
		}
		str1=$("#"+inputId+i).val();
		for(var j=i+1;j<=trs.length;j++){
			if(flag1_1 == true){
				//当为true时才继续循环下去
				var val1 = $("#"+inputId+i).val();
				var val2 = $("#"+inputId+j).val();
				if(val1==val2){
					var k = $("#"+tdId+i).attr("rowspan");
					$("#"+tdId+i).attr("rowspan",parseInt(k)+1);
					$("#"+tdId+j).remove();
				}else{
					//当有一个不等时不让他继续循环
					flag1_1 = false;
				}
			}
			
		}
	}
}
//有部分订单退回的合同修改初始化
function orderBackInit(){
	$("#cntAmt").hide().after($("#cntAmt").val());
	$(".orderBackDiv").each(function(){
		$(this).hide().after($(this).find(":selected").text());
	});
	$("#deleteBtn").attr("disabled","disabled").addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-disabled ui-state-disabled base-button");
}

//校验合同金额是否等于采购金额
function checkCntAmt(){
// 	var cntAmt = $("#cntAmt").val();
// 	var totalAmt = 0;
// 	var trList = $("#totalNumTable tr:gt(0)");
// 	for(var i=0; i<trList.length; i++){
// 		totalAmt += Number($(trList[i]).find("input[name='execAmt']").val());
// 	}
// 	if(cntAmt == totalAmt){
// 		return true;
// 	}
// 	return false;
return true;
}

//金额校验函数，参数为所需校验的对象
function checkMoney(obj){
	if(!$.checkMoney($(obj).val()))
	{
		App.notyError("数据有误！最多含两位小数的18位正浮点数。");
		$(obj).focus();
		return false;
	} 
	return true;
}

//数量校验函数
function checkNum(obj){
	if(!$.checkRegExp($(obj).val(),reg)){
		App.notyError("数据有误，请输入正整数！");
		$(obj).focus();
		return false;
	}
	return true;
}

//校验电子审批审批数量
function checkDZSP(){
	var DZSPTrList = $("#DZSPTable tr");
	if(DZSPTrList.length < 2){
		App.notyError("请至少选择一条电子审批记录！");
		return false;
	}
	for(var i=1;i<DZSPTrList.length;i++){
		var abcdeNum = $(DZSPTrList[i]).find("input[name='abcdeNum']");
// 		var abcdeAmt = $(DZSPTrList[i]).find("input[name='abcdeAmt']");
// 		if(!$.checkMoney($(abcdeAmt).val())){
// 			App.notyError("第【"+i+"】条电子审批记录的金额输入值不合法，请重新输入！");
// 			return false;
// 		}else 
		if(!$.checkRegExp($(abcdeNum).val(),reg)){
			App.notyError("第【"+i+"】条电子审批记录的数量输入值不合法，请重新输入！");
			return false;
		}
	}
	getAbcdeAmt();
	getAbcdeNum();
	return true;
}

//汇总电子审批金额
function getAbcdeAmt(){
	var DZSPTrList = $("#DZSPTable tr");
	var totalAmt=0;
	for(var i=1;i<DZSPTrList.length;i++)
	{
		var abcdeAmt = $(DZSPTrList[i]).find("input[name='projCrAmt']").val();
		totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat(abcdeAmt,3),2);
	}
	$("#lxje").val(totalAmt); 
	$("#lxjeSpan").text(totalAmt);
}

//汇总电子审批数量
function getAbcdeNum(){
	var totalNum=0;

	$("#DZSPTable tr").find("input[name='abcdeNum']").each(function(i){
			totalNum=(parseInt(totalNum)+parseInt($(this).val()));
	});
	if($.isNumeric(totalNum)){
		$("#lxsl").val(totalNum);
		$("#lxslSpan").text(totalNum);
	}
}

// //重置表单
// function resetAll(){
// 	$("select").val("");
// 	$(":text").val("");
// 	$(":selected").prop("selected",false);
// }

//审批类别下拉框改变触发
function changLxlx(type){
	var changeVal = $("#lxlx").val();
	$("#addlxslImg,#DZSPTr,#lxjeRedSpan,#lxslRedSpan,#qbhTdLeft,#qbhTdRight").hide();
	$("#lxsl,#lxje").show();
	$("#lxslSpan,#lxjeSpan").text("");
	$("#DZSPTr").removeClass("collspan");
	$("#lxlx").parent().parent().attr("colspan","3");
	$("#qbh").removeAttr("valid");
	if(changeVal == 1){
		//电子审批
		$("#addlxslImg,#DZSPTr").show();
		$("#lxsl,#lxje").hide();
		$("#lxslSpan,#lxjeSpan").text(0);
		$("#DZSPTr").addClass("collspan");
		getAbcdeNum();
		getAbcdeAmt();
		$("#qbh").val("");
	}else if(changeVal == 2){
		//签报立项
		$("#qbhTdLeft,#qbhTdRight,#lxjeRedSpan,#lxslRedSpan").show(); 
		$("#lxlx").parent().parent().attr("colspan","1");
		$("#qbh").attr("valid","");
		$("#lxsl").val('${cnt.lxsl}');
		$("#lxje").val('${cnt.lxje}');
	}else if(changeVal == 3){
		//部内审批
		$("#lxjeRedSpan,#lxslRedSpan").show();
		$("#qbh").val("");
		$("#lxsl").val('${cnt.lxsl}');
		$("#lxje").val('${cnt.lxje}');
	}
}


//合同类型下拉框改变触发
var curCntType = '${cnt.cntType}';
// function changeCntType(type){
// 	var changeVal = $("#cntType").val();
// 	//$(".feeClass").hide();
// 	if("init"==type || changeVal != curCntType){
// 		if(changeVal == 0){
// 			//资产类
// 			$(".feeClass").hide().removeClass("colspan");
// 			$("#feeStartDate").removeAttr("valid");
// 			$("#feeEndDate").removeAttr("valid");
// 		}else if(changeVal == 1){
// 			//费用类
// 			$(".feeClass").show().addClass("collspan");
// 			changeFeeType();
// 			//changeFeeSubType();
// 		}
// 		if("init"!=type){
// 			removeDeviceTr();
// 			addTotalNum();//增加一行初始行
// 			$("#isProvinceBuy").combobox("destroy").val("").combobox();
// 			$("#isProvinceBuyTr").hide();
// 			$("#isSpec").combobox("destroy").val("").combobox();
// 			//合同受益起始和结束日期清空
// 			$("#feeStartDate").val("");
// 			$("#feeEndDate").val("");
// 			curCntType = changeVal;
// 		}
// 	}
// }

//合同物料查询条件变动时，删除已录入的可修改物料
function removeDeviceTr(){
	$("#totalNumTable tr:gt(0):not(.noChgTr)").remove();
	computCntTotalNum();
	$("#tableDiv").empty();
}

//费用类型下拉框改变触发
function changeFeeType(){
	var changeVal = $("#feeType").val();
	$("#feeSubTypeTdLeft,#feeSubTypeTdRight,#feeDateTr,#feeCntTr,#feeAmtTr,#houseTr").hide().removeClass("collspan");
	if(changeVal == '0'){
		//金额固定、受益期固定
		$("#feeSubTypeTdLeft,#feeSubTypeTdRight,#feeDateTr,#feeCntTr").show();
		$("#feeDateTr,#feeCntTr").addClass("collspan");
		changeFeeSubType();
	}else if(changeVal == '1'){
		//受益期固定、合同金额不固定
		$("#feeDateTr,#feeAmtTr,#feeCntTr").show().addClass("collspan");
	}else if(changeVal == '2'){
		//其它
		
	}
	curFeeType = changeVal;
}

//费用子类型下拉框改变触发
function changeFeeSubType(){
	var changeVal = $("#feeSubType").val();
	$("#houseTr").hide().removeClass("collspan");
	
	if(changeVal == 0){
		//普通费用类
	}else if(changeVal == 1){
		//房屋租赁类
		$("#houseTr").show().addClass("collspan");
	}
}

//付款条件下拉框改变触发
function changePayTerm(tag){
	var changeVal = $("#payTerm").val();
	$("#onScheduleTr,#onDateTr,#onTermTr").hide();
	$("#stageTypeDiv").hide();
	if(changeVal == 3){
		//分期付款
		$("#stageTypeDiv").css("display","inline");
		if("init" != tag){
			$("#stageType").combobox("destroy").val("0").combobox();
		}
		$("#onScheduleTr").show();
	}else{
		//其他付款方式
		$("#stageTypeDiv").hide();
	}
}

//付款条件子下拉框改变触发
function changeStageType(){
	var changeVal = $("#stageType").val();
	$("#onScheduleTr,#onDateTr,#onTermTr").hide().removeClass("collspan");
	if(changeVal == '0'){
		//按进度
		$("#onScheduleTr").show().addClass("collspan");
	}else if(changeVal == '1'){
		//按日期
		$("#onDateTr").show().addClass("collspan");
	}else if(changeVal == '2'){
		//按条件
		$("#onTermTr").show().addClass("collspan");
	}
}

//新增审批数量记录
function addLxsl(){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/projectcrinfo/list.do?<%=WebConsts.FUNC_ID_KEY %>=011003',
			{
				width: "80%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"审批类别选择",
			    id:"dialogCutPage",
				close: function(){
					var projectcr = art.dialog.data('projectcr'); 
					if(projectcr){
					if(projectcr.abCde!=""&&projectcr.abCde!=null){
						var newProAbcde = projectcr.abCde;
						var DZSPTrList = $("#DZSPTable tr");
						for(var i=0;i<DZSPTrList.length;i++){
							var DZSPTrAbcde = $(DZSPTrList[i]).find("input[name='abcde']").val();
							if(newProAbcde == DZSPTrAbcde){
								App.notyError("所选择的电子审批已存在！");
								for (var i in projectcr) {
									if (projectcr.hasOwnProperty(i) && i !== 'DOM') delete projectcr[i];
								};
								return;
							}
						}
						var appendTr = "<tr>"+
											"<td>"+projectcr.abCde+"<input type='hidden' name='abcde' value='"+projectcr.abCde+"'></td>"+//缩位码
											"<td>"+projectcr.projCrId+"</td>"+//审批编号
											"<td>"+projectcr.createDate+"</td>"+//日期
											"<td>"+projectcr.projCrNum+"</td>"+//审批数量
											//"<td>"+projectcr.exeNum+"</td>"+//已执行数量
											"<td><input type='text' name='abcdeNum' class='base-input-text' style='width:100%' onblur='getAbcdeNum()'></td>"+//数量
											"<td><input type='hidden' name='projCrAmt' value='"+projectcr.projCrAmt+"'/>"+projectcr.projCrAmt+"</td>"+//审批金额
											//"<td>"+projectcr.exeAmt+"</td>"+//已执行金额
											//"<td><input type='text' class='base-input-text' name='abcdeAmt' style='width:100%' onblur='getAbcdeAmt()'></td>"+//金额
											"<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteDZSPTr(this)'/></a></td>"+//操作
									"</tr>";
						$("#DZSPTable").append(appendTr);					
						}
					}
					for (var i in projectcr) {
						if (projectcr.hasOwnProperty(i) && i !== 'DOM') delete projectcr[i];
					};
				}
			}
		 );
}

//供应商弹出页
function queryProvider(obj,flag){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&compare='+"common",
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
						$("#providerType").val(proObj.providerType);
						$("#provActCurr").val(proObj.actCurr);
						$("#provActNo").val(proObj.actNo);
						$("#providerCode").val(proObj.providerCode);
						$("#providerName").val(proObj.providerName);
						$("#providerAddr").val(proObj.providerAddr);
						$("#actName").val(proObj.actName);
						$("#bankInfo").val(proObj.bankInfo);
						$("#bankCode").val(proObj.bankCode);
						$("#bankArea").val(proObj.bankArea);
						$("#bankName").val(proObj.bankName);
						$("#actType").val(proObj.actType);
						$("#payMode").val(proObj.payMode);
						
						$("#bankNameSpan").text(proObj.bankName);
						$("#provActNoSpan").text(proObj.actNo);
						$("#providerAddrSpan").text(proObj.providerAddr);
						$("#actNameSpan").text(proObj.actName);
						$("#bankCodeSpan").text(proObj.bankCode);
						$("#bankInfoSpan").text(proObj.bankInfo);
						
						if('provider'==flag){
							$(obj).parent().attr("colspan","3");
							if(proObj.providerType == 'EMPLOYEE'){
								//员工供应商
								$("#srcPoviderTdLeft").show();
								$("#srcPoviderTdRight").show();
								$("#srcPovider").attr("valid","");
								$(obj).parent().attr("colspan","1");
							}else{
								$("#srcPoviderTdLeft").hide();
								$("#srcPoviderTdRight").hide();
								$("#srcPovider").removeAttr("valid");
								$("#srcPovider").val("");
							}
						}
					}
				}		
			}
		 );
}

//内部供应商弹出页
function querySrcProvider(obj){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&compare='+"employee",
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商账号选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('empProObj'); 
					if(proObj){
						$("#srcPovider").val(proObj.providerName);  //内部供应商名称
					}
				}
			}
		 );
}
//甲方供应商弹出页
function queryJfProvider(obj,name){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&compare='+"jf",
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商账号选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('jfProObj'); 
					if(proObj){
						$(obj).val(proObj.providerName);  //供应商名称
						$(obj).prev().val(proObj.providerCode);	//供应商编号
						$("#providerType").val(proObj.providerType);
					}
				}
			}
		 );
}

//项目弹出页
function projOptionPage(obj){
	var checkId = $(obj).prev().val();
	var signDate = $("#signDate").val();
	if(signDate =="" || signDate ==null){
		App.notyWarning("合同签订日期为空！");
		return false;
	}
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projOptionPage.do?<%=WebConsts.FUNC_ID_KEY %>=021004&projId="+checkId+"&signDate="+signDate;
	$.dialog.open(
			url,
			{
				width: "90%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"项目选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('projectObj'); 
					if(proObj){
						$(obj).val(proObj.projName);
						$(obj).attr('title',proObj.projName);
						$(obj).prev().val(proObj.projId);
					}
					setDeviceChg(obj,2);
				}
			}
		 );
}
 
//物料类型添加--跳出页面
function matrTypeOptionPage(obj){
 	var feeDeptObj = $(obj).parent().parent().find("input[name='feeDept']");
	var cntTypeVal = $("#cntType").val();
	var isSpecVal = $("#isSpec").val();
	var isProvinceBuyVal = $("#isProvinceBuy").val();
	var feeTypeVal = $("#feeType").val();
	if($.isBlank(isSpecVal)){
		App.notyWarning("请先选择是否专项包！");
		return false;
	}
	if($.isBlank($(feeDeptObj).val())){
		App.notyWarning("请先选择对应的费用承担部门！");
		$(feeDeptObj).focus();
		return false;
	}
	var url="<%=request.getContextPath()%>/sysmanagement/matrtype/matrTypeOption.do?<%=WebConsts.FUNC_ID_KEY %>=010804&matrCode="+$(obj).prev().val();
	url += "&feeDept="+$(feeDeptObj).val()+"&cntType="+cntTypeVal+"&isSpec="+isSpecVal;
	if(curCntType == 0){
		if(isSpecVal == 1){
			if($.isBlank(isProvinceBuyVal)){
				App.notyWarning("请先选择是否省行统购！");
				return false;
			}else{
				url += "&isProvinceBuy="+isProvinceBuyVal;
			}
		}
	}
	if(cntTypeVal==1){
		url += "&feeType="+feeTypeVal;
	}
	$.dialog.open(
			url,
			{
				width: "70%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"物料类型选择",
			    id:"dialogCutPage",
				close: function(){
					removeFeeInfoTable();
					setDeviceChg(obj,2);
					var proObj = art.dialog.data('matrObj'); 
					if(proObj){
						$(obj).val(proObj.matrName);
						$(obj).attr('title',proObj.matrName);
						$(obj).prev().val(proObj.matrCode);
						$(obj).prev().prev().val(proObj.cglCode);
						$(obj).parent().next().find("input[name='montCode']").val(proObj.montCode);
						$(obj).parent().next().find("span[name='montName']").text(proObj.montName);
					}
				}
			}
		 );
}

//新增采购数量
function addTotalNum(){
}

//计算合同采购数量
function computCntTotalNum(){
	var trList = $("#totalNumTable tr:gt(0)");
	var totalNum = 0;
	for(var i=0; i<trList.length; i++){
		totalNum += Number($(trList[i]).find("input[name='execNum']").val());
	}
	$("#totalNum").val(totalNum);
	$("#totalNumSpan").text(totalNum);
}

//费用部门发生改变时
function changeFeeDept(){
	$("#totalNumTable tr:not(.noChgTr) input[name='feeDept']").each(function(){
 		/* if( $(this).val() != $(this).next().attr("title") ){
			$(this).parent().parent().find("input[name='matrCode']").val("");
			$(this).parent().parent().find("input[name='matrName']").val("");
			$(this).parent().parent().find("input[name='cglCode']").val("");
			removeFeeInfoTable();
 		} */
		$(this).val( $(this).next().attr("title") );
	});
}

//计算采购金额
function computTotalNum(obj){
	var num = $(obj).parent().parent().find("input[name='execNum']");
	var price = $(obj).parent().parent().find("input[name='execPrice']");
	if($.isBlank($(num).val())){
		$(num).val(0);
	}else if($.isBlank($(price).val())){
		$(price).val(0);
	}
	var amt = ($(num).val() * $(price).val()).toFixed(2);
	$(obj).parent().parent().find("input[name='execAmt']").val(amt);
	$(obj).parent().parent().find("span[name='execAmtText']").text(amt);
	computCntTotalNum();
}

//计算房租单价   元/月*平方米(元/月*项)
/* function computeUnitPrice(){
	var area = $("#area").val();
	var rent = $("#rent").val();
	if(!$.checkMoney(area)){
		$("#area").val(0);
	}else if(!$.checkMoney(rent)){
		$("#rent").val(0);
	}else{
		$("#unitPrice").val((rent / area).toFixed(2));
	}
} */

var TenancyDzCnt = parseInt('${tenancyLength}');
//新增房租递增记录
function addTenancyDz(){
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	var tenancyDzTrList = $("#TenancyDzTable tr");
	var index = tenancyDzTrList.length;
	var prevToDate = $(tenancyDzTrList[parseInt(index)-1]).find("input[name='toDate']").val();
	if(beginDate==null||beginDate==''){
		App.notyError("请先选择执行开始日期!");
		return false;
	}
	if(index != 0){
	   if(prevToDate>=endDate){
		  App.notyError("已到执行结束日期上限,若需添加请修改执行结束日期!");
		  return false;
	   }
	   if(prevToDate == ""||prevToDate == null){
		  App.notyError("上条结束日期为空!");
		   return false;
	   }
	}
	var appendTr = '<tr>'+
						'<td> <span name="tdSpan" style="display:none">'+TenancyDzCnt+'</span>'+
							'租金从&nbsp;<input type="text" name="fromDate" class="base-input-text fromDate" onfocus="getStartDate(this)" readonly style="width:80px">&nbsp; '+
							'至&nbsp;<input type="text" name="toDate" class="base-input-text toDate" onchange="checkTenancyDzToDate(this)" style="width:80px">&nbsp; '+
							'<div class="ui-widget" style="display:inline"> '+
								'<select name="dzlx" class="selectD" onchange="changeTenancyDzLx(this)"> '+
									 '<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE"  
									 conditionStr="CATEGORY_ID = 'TENANCY_DZLX'" orderType="ASC"/> '+
								'</select> '+
							'</div>&nbsp;&nbsp; '+
							'<input type="text" name="dzed" class="base-input-text" style="width:60px">&nbsp; '+
							'<div class="ui-widget" style="display:inline" > '+
								'<select name="dzdw" class="selectD" onchange="changeTenancyDzdw(this)"> '+
									 '<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE"  
									 conditionStr="CATEGORY_ID = 'TENANCY_DZDW'" orderType="ASC"/> '+
								'</select> '+
							'</div>&nbsp;&nbsp; '+
							'管理费及其他： '+
							'<input type="text" name="glfy" class="base-input-text" style="width:100px"/>&nbsp;元/月 '+
							'&nbsp;&nbsp;&nbsp; '+
							'<a><img border="0" alt="删除"  width="30px" height="30px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteTenancyDzTr(this)" style="vertical-align: middle;"/></a>'+ 
						'</td> '+
					'</tr>';
	$("#TenancyDzTable").append(appendTr);
	TenancyDzCnt++;
	$(".selectD").combobox();
	$("#TenancyDzTable .ui-autocomplete-input").css("width","40px");
	$(".toDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}

//改变递增类型
function changeTenancyDzLx(obj){
	var changeVal = $(obj).val();
	var dzdwObj = $(obj).parent().parent().find("select[name='dzdw']");
	if(changeVal == 1){
		$(dzdwObj).combobox("destroy").val("1").combobox();
	}else{
		$(dzdwObj).combobox("destroy").val("2").combobox();
	}
	$("#TenancyDzTable .ui-autocomplete-input").css("width","40px");
}

function changeTenancyDzdw(obj){
	var changeVal = $(obj).val();
	var dzdwObj = $(obj).parent().parent().find("select[name='dzlx']");
	if(changeVal == '1'){
		$(dzdwObj).combobox("destroy").val("1").combobox();
	}else{
		$(dzdwObj).combobox("destroy").val("2").combobox();	
	}
	$("#TenancyDzTable .ui-autocomplete-input").css("width","40px");
}

//获取房租递增的起始日期
function getStartDate(obj){
	$("input[name='fromDate'],input[name='toDate']").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	var beginDate = $("#beginDate").val();
	var tenancyDzTrList = $("#TenancyDzTable tr");
	var index = $(obj).parent().find("span[name='tdSpan']").html();
	if(index == 0){
		if(beginDate != ""){
			$(obj).val(beginDate);
		}
	}else{
		var prevToDate = $(tenancyDzTrList[parseInt(index)-1]).find("input[name='toDate']").val();
		if(prevToDate != ""||prevToDate != null){
			tempDate = addday(prevToDate,1);
			$(obj).val(tempDate);
		}else{
			$(obj).val("");
			App.notyError("上条结束日期为空! ");
		}
	}
}

//校验租金递增结束日期
function checkTenancyDzToDate(obj){
	var TenancyDzTrList = $("#TenancyDzTable tr");
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	var index = $(obj).parent().find("span[name='tdSpan']").html();
	var fromDatei = $(TenancyDzTrList[index]).find("input[name='fromDate']").val();
	var toDatei = $(obj).val();
	
	if("" == beginDate){
		App.notyError("请选择执行开始日期!");
		return false;
	}
	if("" == endDate){
		App.notyError('请选择执行结束日期!');
		return false;
	}
	if(fromDatei == ""){
		App.notyError('请先选择递增开始日期!');
		$(obj).val("");
		return false;
	}
// 	if(index == 0 && index<TenancyDzTrList.length){
		if(addday(toDatei,1).substring(8,10) != '01' && addday(toDatei,1).substring(8,10) != fromDatei.substring(8,10)){
			App.notyError('递增结束日期不符合规则,请重新选择!');
			$(obj).val("");
			return false;
		}
// 	}
	//判断第二条及以后递增条件输入规则月初规则(所有开始日期为01),月中规则(开始日期相同,例如都为15号)
	if(index>0){
		$("#TenancyDzTable").find("input[name='toDate']").each(function(i){
			if(i<$("#TenancyDzTable").find("input[name='fromDate']").length-1){
				var f = $("#TenancyDzTable").find("input[name='fromDate']")[i].value;
				var t = $("#TenancyDzTable").find("input[name='toDate']")[i].value;
				if(f==""||f==null||t==""||t==null){
					$(obj).val("");
					App.notyError('前面的日期条件不能为空,请先填好前面的值!');
					return false;
				}
			}
		});
// 		var preFromDate = $(TenancyDzTrList[i]).find("input[name='fromDate']").val();
// 		var preToDate = $(TenancyDzTrList[i]).find("input[name='toDate']").val();
		if(fromDatei!=""||fromDatei!=null){
			if(addday(toDatei,1).substring(8,10) != '01' && addday(toDatei,1).substring(8,10) != fromDatei.substring(8,10)){
				$(obj).val("");
				App.notyError('递增结束日期不符合规则,请重新选择!');
				return false;
			}
		}else{
			$(obj).val("");
			App.notyError('本条递增开始日期为空!');
		}
	}
	if(""!=toDatei){
		if(fromDatei>=toDatei){
			$(obj).val("");
			App.notyError('递增结束日期必须大于开始日期!');
			return false;
		}if(toDatei>endDate && (toDatei.substring(0,4)!=endDate.substring(0,4)||toDatei.substring(5,7)!=endDate.substring(5,7))){
			$(obj).val("");
			App.notyError('递增结束日期必须小于执行结束日期! ');
			return false;
		}
	}
}

//日期加减   参数：日期date  要增加的天数days
function addday(date,days){
	var tempDate =  new Date();
	str = date.split('-'); 
	tempDate.setUTCFullYear(str[0], str[1] - 1, str[2]); 
	tempDate.setUTCHours(0, 0, 0, 0); 
	tempDate.setDate(tempDate.getDate()+days);
	tempDate = $.datepicker.formatDate( "yy-mm-dd",tempDate);
	return tempDate;
}

var scheduleTrCnt = '0'=='${cnt.stageType}'?'${stageInfoLength}':1; //初始的按进度付款记录期数为已有记录条数

//按进度新增付款记录
function addOnScheduleTr(){
	scheduleTrCnt++;
	var appendTr = "<tr>"+
						"<td>第<span name='sceduleTrCnt'>"+scheduleTrCnt+"</span>期&nbsp;&nbsp;付款年月&nbsp;"+
						"<input type='text' name='jdDate' readonly class='base-input-text jdDate' style='width:120px'/>&nbsp;"+
						" 支付&nbsp;"+
						"<input type='text' class='base-input-text' name='jdzf' style='width:60px'>&nbsp;元</td>"+
						"<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteScheduleTr(this)'/></a></td>"+
				"</tr>";
	$("#onScheduleTable").append(appendTr);
	$(".jdDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
}

//按日期新增付款记录
function addOnDateTr(){
	var appendTr = "<tr>"+
						"<td>合同签订后<input type='text' name='rqtj' class='base-input-text' style='width:60px'>&nbsp;天&nbsp;&nbsp;支付&nbsp;"+
						"<input type='text' name='rqzf' class='base-input-text' style='width:60px'>&nbsp;元</td>"+
						"<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteTr(this)'/></a></td>"+
				"</tr>";
	$("#onDateTable").append(appendTr);
}

//删除一行数据
function deleteTr(obj){
	$(obj).parent().parent().parent().remove();
}

//删除一行房租递增记录
function deleteTenancyDzTr(obj){
	TenancyDzCnt--;
	$(obj).parent().parent().parent().remove();
	var trList = $("#TenancyDzTable tr");
	for(var i=0;i<trList.length;i++){
		$(trList[i]).find("span[name='tdSpan']").html(i);
	}
}

//删除一行进度新增记录
function deleteScheduleTr(obj){
	scheduleTrCnt--;
	$(obj).parent().parent().parent().remove();
	var trList = $("#onScheduleTable tr");
	for(var i=0;i<trList.length;i++){
		$(trList[i]).find("span[name='sceduleTrCnt']").html(i+1);
	}
}
//删除一行电子审批记录
function deleteDZSPTr(obj){
	$(obj).parent().parent().parent().remove();
	getAbcdeNum();
	getAbcdeAmt();
}

//删除一行采购数量记录
function deleteToltalNum(obj){
	$(obj).parent().parent().parent().remove();
	computCntTotalNum();
	$("#tableDiv").empty();
} 

//提交验证分期付款条件
function checkFqfktj()
{
	var changeVal = $("#stageType").val();
	if(changeVal == 0){
		//按进度
		var totalAmt = 0;
		var totalJd = 0;
		var trList = $("#onScheduleTable tr");
		if(trList.length < 1){
			App.notyError("请添加进度记录！");
			return false;
		}
		for(var i=0;i<trList.length;i++){
// 			var jdtjObj = $(trList[i]).find("input[name='jdtj']");
			var jdzfObj = $(trList[i]).find("input[name='jdzf']");
			if(!$.checkMoney($(jdzfObj).val())){
				App.notyError("第【"+(i+1)+"】条按进度分期付款的金额输入值不合法，请重新输入！");
				return false;
// 			}else if(!$.checkRegExp($(jdtjObj).val(),reg)){
			}
// 			else if(!$.checkMoney($(jdtjObj).val())){
// 				App.notyError("第【"+(i+1)+"】条按进度分期付款的数量输入值不合法，请重新输入！");
// 				return false;
// 			}
			totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat($(jdzfObj).val().replace(/\,/g,''),3),2);
// 			totalJd=$.numberFormatAdd($.numberFormat(totalJd,3),$.numberFormat($(jdtjObj).val().replace(/\,/g,''),3),2);
		}
// 		if(totalJd!=100)
// 		{
// 			App.notyError('进度之和必须为100%!');
// 			return false;
// 		}
		if(totalAmt*1 != $("#cntAllAmt").val().replace(/\,/g,'')*1)
		{
			App.notyError('分期付款金额之和必须与合同金额相等!');
			return false;
		}
	}else if(changeVal == 1){
		//按日期
		var totalAmt=0;
		var trList = $("#onDateTable tr");
		if(trList.length < 1){
			App.notyError("请添加进度记录！");
			return false;
		}
		for(var i=0;i<trList.length;i++){
			var rqtjObj = $(trList[i]).find("input[name='rqtj']");
			var rqzfObj = $(trList[i]).find("input[name='rqzf']");
			if(!$.checkMoney($(rqzfObj).val())){
				App.notyError("第【"+(i+1)+"】条按日期分期付款的金额输入值不合法，请重新输入！");
				return false;
			}else if(!$.checkRegExp($(rqtjObj).val(),reg)){
				App.notyError("第【"+(i+1)+"】条按日期分期付款的数量输入值不合法，请重新输入！");
				return false;
			}
			totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat($(rqzfObj).val().replace(/\,/g,''),3),2);
		}
		if(totalAmt*1!=$("#cntAllAmt").val().replace(/\,/g,'')*1)
		{
			App.notyError('分期付款金额之和必须与合同金额相等!');
			return false;
		}
	}else if(changeVal == 2){
		//按条件
		var totalAmt=0;
		if(!checkMoney($("#dhzf")) || !checkMoney($("#yszf")) || !checkMoney($("#jszf")))
		{
			return false;
		}
		totalAmt=$.numberFormatAdd($.numberFormat($("#dhzf").val().replace(/\,/g,''),3),$.numberFormat($("#yszf").val().replace(/\,/g,''),3),2);
		totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat($("#jszf").val().replace(/\,/g,''),3),2);
		if(totalAmt.replace(/\,/g,'')*1 != $("#cntAllAmt").val().replace(/\,/g,'')*1)
		{
			App.notyError('分期付款金额之和必须与合同金额相等!');
			return false;
		}
	}
	return true; 
}
function setDeviceChg(obj,level){
	$("input[name=deviceChg]").val("Y");
	if(level == 2){
		$(obj).parent().parent().find("input[name=projDevChg]").val(true);
	}else if(level == 3){
		$(obj).parent().parent().parent().find("input[name=projDevChg]").val(true);
	}
}

var boolSpec = '${cnt.isSpec}';
//改变是否专项包
function changeIsSpec(){
	var changeVal = $("#isSpec").val();
	if(changeVal != boolSpec){
		removeDeviceTr();
		addTotalNum();//增加一行初始行
		boolSpec = changeVal;
	}
	$("#isProvinceBuyTr").hide();
	if(curCntType == 0){
		if(boolSpec == 0){
			//是专项包
		}else{
			$("#isProvinceBuyTr").show();
		}
	}
}

var provinceBuy = '${cnt.isProvinceBuy}';
//改变是否省行统购
function changeProvinceBuy(){
	var changeVal = $("#isProvinceBuy").val();
	if(changeVal != provinceBuy){
		removeDeviceTr();
		addTotalNum();//增加一行初始行
		provinceBuy = changeVal;
	}
}

function changeIsRelatedCnt(){
	var changeVal = $("#isRelatedYN").val();
	if(changeVal == 0){
		$(".relateCntTd").show();
		$("#cntNumRelated").attr("valid","valid");
		$("#isRelatedYN").parent().parent().attr("colspan","1");
	}
	else if(changeVal==1){
		$("#isRelatedYN").parent().parent().attr("colspan","3");
		$(".relateCntTd").hide();
		$("#cntNumRelated").removeAttr("valid").val("");
	}
}

function scan0(){
	var url="<%=request.getContextPath()%>/common/contract/scan/preadd.do?id=${cnt.cntNum}";
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"中行影像前端控件",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}

function scan(){
	var  url="<%=request.getContextPath()%>/common/contract/scan/preupdate.do?id=${cnt.cntNum}";
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"中行影像前端控件",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}


function deleteCnt(cntNum){
	var url = "<%=request.getContextPath()%>/contract/modify/delete.do?<%=WebConsts.FUNC_ID_KEY %>=0302010204&cntNum="+cntNum;
	$( "<div>确认删除?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				window.location = url;
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

//关联合同号弹出页
function relatedCnt(){
	$.dialog.open(
			'<%=request.getContextPath()%>/contract/initiate/relatedCntPage.do?<%=WebConsts.FUNC_ID_KEY%>=0302010107',
			{
				width: "90%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"关联合同号选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('cntObj'); 
					if(proObj){
						$("#cntNumRelated").val(proObj.cntNum);
					}
				}
			}
		 );
}

$(function(){
	$(window).css("overflow-X","hidden");
	$("#scrollTableDiv").css("overflow-Y","hidden");
	$("#scrollTableDiv").css("width",$("#conTable").width());
})  

//校验项目和物料信息
function checkProjMatr(){
	var trList = $("#totalNumTable tr:gt(0):not(.noChgTr)");
	var totalNum = 0;
	if(trList.length < 1){
		App.notyError("请添加采购项目和物料信息！");
		return false;
	}
	for(var i=0;i<trList.length;i++){
		var execNum = $(trList[i]).find("input[name='execNum']").val(); // 数量
		var execPrice = $(trList[i]).find("input[name='execPrice']").val(); // 单价
// 		var warranty = $(trList[i]).find("input[name='warranty']").val(); // 保修期
		if(!$.checkMoney(execNum)){
			App.notyError("第"+(i+1)+"条采购项目的数量输入有误，请输入最多含两位小数的18位正浮点数！");
			return false;
		}
		if(!$.checkMoney(execPrice)){
			App.notyError("第"+(i+1)+"条采购项目的单价输入有误，请输入最多含两位小数的18位正浮点数！");
			return false;
		}
// 		if(!$.checkRegExp(warranty,reg)){
// 			App.notyError("第"+(i+1)+"条采购项目的保修期输入有误，请输入正整数！");
// 			return false;
// 		}
	}
	return true;
}

var pageOneValid = false;
var pageTwoValid = false;
var pageThreeValid = false;

//第一页校验
function pageOne(){
	if(!App.valid("#table1")){return false;} 
 	$("#table1 input").css("border-color",""); // 校验通过后去除红色边框警示
	var lxlxVal = $("#lxlx").val();
	if(!$.checkMoney($("#cntAmt").val())){
		App.notyError("合同金额格式有误，请输入最多含两位小数的18位正浮点数！");
		$("#cntAmt").focus();
		return false;
	}
	//质保金不能大于100%
 	if($("#zbAmt").val() > 100 || !$.checkMoney($("#zbAmt").val())){
 		App.notyError("质保金不能大于100%且最多包含两位小数！");
 		$("#zbAmt").focus();
 		return false;
 	}
 	if(lxlxVal == 1){
		//电子审批
		if(!checkDZSP()){
			return false;
		}
	}
 	if(trim($("#lxsl").val())!=''&&!$.checkRegExp($("#lxsl").val(),reg)){
 		App.notyError("审批数量格式有误，请输入正整数！");
 		$("#lxsl").focus();
		return false;
 	}
 	if(!$.checkMoney($("#lxje").val())){
		App.notyError("审批金额格式有误，请输入最多含两位小数的18位正浮点数！");
		$("#lxje").focus();
		return false;
	}
	if(Number($("#lxje").val()) < $("#cntAllAmtSpan").html()){
		App.notyError("审批金额不能小于合同金额！");
		$("#lxje").focus();
		return false;
	}
 	pageOneValid = true;
 	return true;
}

//第一页跳转下一页
function pageOneNext(){
	if(pageOne()){
		$("#table1,#table3,#table4").hide();
		$("#table2").show();
	}
}

//第二页跳转前一页
function pageTwoBefore(){
	$("#table2,#table3,#table4").hide();
	$("#table1").show();
} 

//产品性质选择自助银行时，给出自助银行名称选项
function fcSelect(){
	var houseKindId = $("#houseKindId").val();
	if (houseKindId == '1') {
		$("#fc_select").find("td:eq(1)").attr("colspan",1);
		$("#fc_select").append("<td class='tdLeft'>自助银行名称</td><td class='tdRight'><input type='text' name='autoBankName' maxlength='1000' class='base-input-text'></td>");
	} else {
		$("#fc_select").find("td:eq(1)").attr("colspan",3);
		$("#fc_select").find("td:gt(1)").remove();
	}
}

//第二页校验
function pageTwo(){
	
	if(curCntType == 0){
		if(boolSpec == ""){
			App.notyError("请先选择是否专项包！");
			return false;
		}
		if(boolSpec == "1"){
			//非专项包
			if(provinceBuy == ""){
				App.notyError("请先选择是否省行统购！");
				return false;
			}
		}
	}

	//校验分期付款
	if($("#payTerm").val() == 3){
		if(!checkFqfktj()){
			return false;
		}
	}
	
	if(curCntType == 1){
		//费用类
		var feeStartDate = $("#feeStartDate").val();
		var feeEndDate = $("#feeEndDate").val();
		var feeTypeVal = $("#feeType").val();
		if(feeTypeVal == "0" || feeTypeVal == "1"){
			if($.isBlank($("#isSpec").val())){
				App.notyError("请先选择是否专项包！");
				return false;
			}
			if(!App.valid("#table2")){
				return false;
				} 
		}
		$("#feeStartDate,#feeEndDate,#feeAmt").removeAttr("valid");
		if(feeTypeVal == 0){
			//金额固定、受益期固定
			$("#feeStartDate,#feeEndDate").attr("valid","");
			if(feeStartDate > feeEndDate){
				App.notyError("合同受益起始日期不能大于合同受益结束日期！");
				return false;
			}
			if($("#feeSubType").val() == 1){
				if(!$.checkMoney($("#area").val())){
					App.notyError("租赁面积输入有误，请输入最多含两位小数的18位正浮点数！");
					return false;
				}
				if($.isBlank($("#wydz").val())){
					App.notyError("地址说明不能为空！");
					return false;
				}
				//租金递增条件校验
				 var trList = $("#TenancyDzTable tr");
				if(trList.length > 0){
					for(var i=0;i<trList.length;i++){
						var fromDate = $(trList[i]).find("input[name='fromDate']");
						var toDate = $(trList[i]).find("input[name='toDate']");
						var dzedObj = $(trList[i]).find("input[name='dzfz']");
						var glfyObj = $(trList[i]).find("input[name='glfy']");
						if(fromDate.val() == "" || fromDate.val() == null){
							App.notyError("请选择房租递增开始日期！");
							return false;
						}else if(toDate.val() == "" || toDate.val() == null){
							App.notyError("请选择房租递增结束日期！ ");
							return false;
						}else if(!$.checkMoney($(dzedObj).val())){
							App.notyError("第【"+(i+1)+"】条租金变动金额输入值不合法，请重新输入！");
							$(dzedObj).focus();
							return false;
						}else if(!$.checkMoney($(glfyObj).val())){
							App.notyError("第【"+(i+1)+"】条管理费及其它金额输入值不合法，请重新输入！");
							$(glfyObj).focus();
							return false;
						}
						if(i>0){
							if($(trList[i]).find("input[name='fromDate']").val()<$(trList[i-1]).find("input[name='toDate']").val()){
								App.notyError("租金递增条件第【"+(i+1)+"】条有误，上一条的结束日期不能大于下一条的开始日期！");
								return;
							}
						}
						if(fromDate.val()>=toDate.val()){
							App.notyError("租金递增条件第【"+(i+1)+"】条有误，开始日期不能大于结束日期！");
							return;							
						}
					}
				
				} 
			}
		}else if(feeTypeVal == 1){
			//受益期固定、合同金额不固定
			var feeAmt = $("#feeAmt").val();
			var feePenalty = $("#feePenalty").val();
			var cntAmt = $("#cntAmt").val();
			var cntAllAmt = $("#cntAllAmt").val();
			if((Number(feeAmt)+Number(feePenalty)) != Number(cntAllAmt)){
				App.notyError("合同金额确定部分与约定罚金之和不等于合同总金额！");
				return false;
			}
			$("#feeStartDate,#feeEndDate,#feeAmt").attr("valid","");
			if(feeStartDate > feeEndDate){
				App.notyError("合同受益起始日期不能大于合同受益结束日期！");
				return false;
			}
		}else if(feeTypeVal == 2){
			//其他
			$("#feeStartDate").removeAttr("valid");
			$("#feeEndDate").removeAttr("valid");
		}
	}
	if(!App.valid("#table2")){
		return false;} 
	$("#table2 input").css("border-color",""); // 校验通过后去除红色边框警示
	//校验采购金额必须等于合同金额
	
 	pageTwoValid = true;
	return true;
}

/* //第二页跳转下一页
function pageTwoNext(){
	if(pageTwo()){
		$("#table1,#table2,#table4").hide();
		$("#table3").show();
	}
}  */

//第二页跳转下一页
function pageTwoNext(){
	if(pageTwo()){
		$("#table1,#table2,#table4").hide();
		$("#table3").show();
		
		if ($("#feeSubType").val() == '1' && $("#cntType").val() =='1') {
			$("#table3 #fz_select").show();
			$("#table3 #fz_notselect").hide();
		} else if($("#feeSubType").val() != '1' || $("#cntType").val() =='0' ){
			$("#table3 #fz_select").hide();
			$("#table3 #fz_notselect").show();
		}
	}
} 

//第三页跳转前一页
function pageThreeBefore(){
	$("#table1,#table3,#table4").hide();
	$("#table2").show();
} 

//计算月份差
function cntMonths(statDate,enDate){
	//两个日期
	var date1 = statDate;
	var date2 = enDate;
	// 拆分年月日
	date1 = date1.split('-');
	// 得到月数
	date1 = parseInt(date1[0]) * 12 + parseInt(date1[1],10);
	// 拆分年月日
	date2 = date2.split('-');
	// 得到月数
	date2 = parseInt(date2[0]) * 12 + parseInt(date2[1],10);
	var m = Math.abs(date1 - date2)+1;
	return m;
}

//过滤掉重复物料行
function distinctMatr(){
	var res = [];
	var totalNumTableList = $("#totalNumTable tr:gt(0)");
	for (var i =0;i<totalNumTableList.length;i++) {
		var flag = false;
		var matrCode = $(totalNumTableList[i]).find("input[name='matrCode']").val();
		var matrName = $(totalNumTableList[i]).find("input[name='matrName']").val();
		var execAmt = $(totalNumTableList[i]).find("input[name='execAmt']").val();
		var cntTrAmt = $(totalNumTableList[i]).find("input[name='cntTrAmt']").val();
		if(i==0) {
			res.push([matrCode,matrName,execAmt,cntTrAmt]);
		}else{
			
			for(var j=0;j<res.length;j++){
				if(matrCode == res[j][0]) {
					res[j][2]=Number(execAmt)+Number(res[j][2]);
					res[j][3]=Number(cntTrAmt)+Number(res[j][3]);
				}else{
					flag = true;
				}
			}
		}
		if(flag){
			res.push([matrCode,matrName,execAmt,cntTrAmt]);
		}
	} 
	return res;
}

//ajax查询该物料下的房租递增信息
function getMatrDzList(matrCode){
	var matrDzList = "";
	var data = {};
	data['matrCode'] =  matrCode;
	data['cntNum'] = "${cnt.cntNum }";
	var url = "contract/modify/getMatrDzList.do?<%=WebConsts.FUNC_ID_KEY%>=0302010206";
	App.ajaxSubmit(url, {data : data,async:false}, function(data){
				matrDzList = data.matrDzList;
	});
	return matrDzList;
}

//第三页跳转下一页
function pageThreeNext(){
	if(true/* pageThree() */){
		$("#table1,#table2,#table3").hide();
		$("#table4").show();
	}
	
	//将合同金额、不含税金额、税额显示在第四页表头
	$("#cntAmt4").text($("#cntAllAmt").val());
	$("#execAmt4").text($("#cntAmt").val());
	$("#cntTaxAmt4").text($("#cntTaxAmt").val());

	var feeStartDate = ($("#feeStartDate").val()).substring(0,7);
	var matrList = distinctMatr();
	for (var i=0;i<matrList.length;i++) {
		
		var matrDzList = getMatrDzList(matrList[i][0]);
		
		//该物料不含税总金额
		var execAmt = matrList[i][2];
		//该物料总税额
		var cntTrAmt = matrList[i][3];
		//该物料总金额
		var cntAmtMt = Number(execAmt)+Number(cntTrAmt);
		
		var execAmtLj = execAmt;
		var cntTrAmtLj = cntTrAmt;
		var cntAmtMtLj = cntAmtMt;
		
		var appendTr = '';
		
		for(var j=0;j<matrDzList.length;j++){
			
			var fromDate = matrDzList[j].fromDate;
			var toDate = matrDzList[j].toDate;
			var cntMons = cntMonths(fromDate+'-01', toDate);
			var hejijine = (Number(matrDzList[j].cntAmtTr) * parseFloat(cntMons)).toFixed(2);
				
			appendTr+='<tr class="tenancyDz" >'+
			'<td><span name="tdSpan">'+(j+1)+'</span><input type="hidden" value="${matrDzList[j].matrCodeFz}"></td>'+
			'<td><span name="fromDateShow">'+matrDzList[j].fromDate+'</span><input type="hidden" name="matrCodeFz" id="matrCodeFz" value="'+matrList[i][0]+'"><input type="hidden" id="fromDate" name="fromDate" value="'+matrDzList[j].fromDate+'" class="base-input-text fromDate"></td>'+
			'<td><span name="toDate">'+matrDzList[j].toDate+'</span><input type="hidden" name="toDate" class="base-input-text toDate" valid value="'+matrDzList[j].toDate+'" readonly style="width:75px"></td>'+
			'<td><span >'+matrDzList[j].cntAmtTr+'</span><input type="hidden" maxlength="18" name="cntAmtTr" class="base-input-text" value="'+matrDzList[j].cntAmtTr+'" valid style="width:100px">&nbsp;元/月</td>'+
			'<td><span >'+matrDzList[j].execAmtTr+'</span><input type="hidden" maxlength="18" name="execAmtTr" class="base-input-text" value="'+matrDzList[j].execAmtTr+'" valid style="width:100px">&nbsp;元/月</td>'+
			'<td><span >'+matrDzList[j].taxAmtTr+'</span><input type="hidden" maxlength="18" name="taxAmtTr" class="base-input-text" value="'+matrDzList[j].taxAmtTr+'" valid style="width:100px">&nbsp;元/月</td>'+
			'<td><span name="heji">'+hejijine+'</span></td>'+
			'</tr>';
			
		}
		
		
		var trList = $(".collspan-con");
		var flag = true;
		for (var j=0;j<trList.length;j++) {
			var matrCode = $(trList[j]).find("#matrCode").val();
			if (matrList[i][0] == matrCode) {
				flag = false;
			}
		}
		if (flag) {
			
			var appendTable ='<table id="TenancyDzTdTable">'+
								'<tr class="collspan-con" style="cursor: pointer;">'+
									'<th colspan="8"><input type="hidden" id="matrCode" value="'+matrList[i][0]+'">'+
									'<div><div style="display: block; float: center; ">'+matrList[i][1]+'</div>'+
									'</div>'+
								'</th></tr>'+
								'<tr>'+
									'<td>序号</td>'+
									'<td>开始日期<span class="red">*</span></td>'+
									'<td>结束日期<span class="red">*</span></td>'+
									'<td>合同总金额（总金额：<span id="totalCntAmtMt">'+cntAmtMt+'</span> ， 累计金额：<span id="ljCntAmtMt">'+cntAmtMtLj+'</span>）<span class="red">*</span></td>'+
									'<td>不含税金额（总金额：<span id="totalExecAmt">'+execAmt+'</span> ， 累计金额：<span id="ljExecAmt">'+execAmtLj+'</span>）<span class="red">*</span></td>'+
									'<td>税额（总金额： <span id="totalCntTrAmt">'+cntTrAmt+'</span>， 累计金额：<span id="ljCntTrAmt">'+cntTrAmtLj+'</span>）<span class="red">*</span></td>'+
									'<td>本行合计</td>'+
								'</tr>'+
								
								appendTr+
								
 								
 							'</table>';
			$("#TenancyDzTd").append(appendTable);
		}
				
		$(".collspan-con").unbind("click");
		
		$(".collspan-con").bind("click",function(){
			$(this).siblings("tr").toggle();
		});
	}
	
	var trList = $(".collspan-con");
	for (var i=0;i<trList.length;i++) {
		var matrCode = $(trList[i]).find("#matrCode").val();
		var flag = true;
		for (var j=0;j<matrList.length;j++) {
			if (matrList[j][0] == matrCode) {
				flag = false;
			}
		}
		if (flag) {
			$(trList[i]).parent().remove();
		}
	}
	
	
	var _parseDate = $.datepicker.parseDate;
	$.datepicker.parseDate = function (format,value,settings){
		if (format == 'yy-mm')
			return _parseDate.apply(this,['yy-mm-dd',value+'-01',settings]);
		else 
			return _parseDate.apply(this,arguments);
	}
	$(".toDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm"
	});
	
} 

//第四页跳转前一页
function pageFourBefore(){
	
	$("table[id='TenancyDzTdTable']").remove();
	$("#table1,#table2,#table4").hide();
	$("#table3").show();
} 

//第三页校验
function pageThree(){
	if(!checkProjMatr()){
		return false;
	}
	 if(!checkFeeDept()){
		return false;
	} 
	//采购金额等于合同金额
	if(!checkCntAmt()){
		App.notyError("合同金额不等于采购总金额！");
		return false;
	}
	if(!App.valid("#table3")){return false;} 
	$("#table3 input").css("border-color",""); // 校验通过后去除红色边框警示
	return true;
}
//校验费用承担部门是否合理
 function checkFeeDept(){
	var feeDeptValue=0;//新的
	var flag=true;
 	$(".feeId").each(function(i){
 		var feeDeptOld = $(this).val();//旧的
 		var feeDeptLength=$("input[id="+$(this).val()+"]").length;	
 		$("input[id="+$(this).val()+"]").each(function(){
			 feeDeptValue=$(this).val();
		});
	 	if(feeDeptLength>1){
				$("input[id="+$(this).val()+"]").each(function(){
					if(!($(this).val()==feeDeptValue)){
						App.notyError("相同的费用承担部门只能变更为同一个费用承担部门!");
						flag= false;
					}
				});
		};
		if(flag){
			//校验不能存在1-》2 ，2-》3  或者2-》3 ，1-》2的情况
			if(feeDeptOld != feeDeptValue){
				$(".feeDeptNew").each(function(j){
					var feeDeptValue2 = $(this).val();//新的
					var feeDeptOld2 = $(this).next().next().val();//旧的
					if(feeDeptValue2 != feeDeptOld2){
						if(i!=j){
							if(feeDeptOld == feeDeptValue2){
								App.notyError("第"+(i+1)+"行的变更前的费用承担部门和第"+(j+1)+"行变更后的费用承担部门不能相同，可能导致预算处理错误");
								flag = false;
							}
							if(feeDeptValue == feeDeptOld2){
								App.notyError("第"+(j+1)+"行的变更前的费用承担部门和第"+(i+1)+"行变更后的费用承担部门不能相同，可能导致预算处理错误");
								flag = false;
							}
						}
					}
				})
			}
		}
		
 	});
 	
 	
 	return flag;
}  
//第三页点击合同更新
// function pageThreeSubmit(){
// 	if(!pageThree()){
// 		return false;
// 	}else{
// 		//校验项目金额是否超出预算
// 		if(!checkProjAmt()){
// 			pageThreeBefore();
// 			return false;
// 		}
// 		if(!pageTwoValid){
// 			pageThreeBefore();
// 			pageTwo();
// 		}
// 		if(!pageOneValid){
// 			pageTwoBefore();
// 			pageOne();
// 		}
// 		var form = $("#modifyForm");
<%-- 		form.attr('action', '<%=request.getContextPath()%>/contract/modify/modify.do?<%=WebConsts.FUNC_ID_KEY%>=0302010202'); --%>
// 		App.submit(form);
// 	}
// }
function pageThreeSubmit(){
	if(!pageThree()){
		return false;
	}else{
		if(!pageTwoValid){
			pageThreeBefore();
			pageTwo();
		}
		if(!pageOneValid){
			pageTwoBefore();
			pageOne();
		}
		if(!checkProjAmt()){
			return false;
		};
		
		$( "<div>确认变更?</div>" ).dialog({
			resizable: false,
			height:150,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					var form = $("#modifyForm");
					form.attr('action', '<%=request.getContextPath()%>/contract/confirmchg/confirmChg.do?<%=WebConsts.FUNC_ID_KEY%>=0302040202');
					App.submit(form);
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}

function pageFourSubmit(){
	if(!pageFour()){
		return false;
	}else{
		if(!pageTwoValid){
			pageThreeBefore();
			pageTwo();
		}
		if(!pageOneValid){
			pageTwoBefore();
			pageOne();
		}
		if(!checkProjAmt()){
			return false;
		};
		
		$( "<div>确认变更?</div>" ).dialog({
			resizable: false,
			height:150,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					var form = $("#modifyForm");
					form.attr('action', '<%=request.getContextPath()%>/contract/confirmchg/confirmChg.do?<%=WebConsts.FUNC_ID_KEY%>=0302040202');
					App.submit(form);
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}

//第四页校验
function pageFour(){
	
	if($.trim($('#wydz').val())==''||$.trim($('#wydz').val())==null)
	{
		App.notyError("地址不能为空");
		return false;
	}
	if($.trim($('#area').val())==''||$.trim($('#area').val())==null)
	{
		App.notyError("租赁面积不能为空");
		return false;
	}
	if(!$.checkMoney($("#area").val()) || $("#area").val()==0){
		App.notyError("租赁面积输入有误，请输入最多含两位小数的18位正浮点数！");
		return false;
	}
	if($.trim($('#houseKindId').val())==''||$.trim($('#houseKindId').val())==null)
	{
		App.notyError("请选择租赁类型");
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="0302010202,0302010204"/>
<form action="<%=request.getContextPath()%>/contract/modify/modify.do?<%=WebConsts.FUNC_ID_KEY%>=0302010202" method="post" id="modifyForm">
<p:token/>
<!-- <table class="tableList"> -->
<!-- <tr> -->
<%-- 	<td class="tdLeft" width="25%">变更内容说明</td><td width="75%" class="tdRight">${Info.operateLog}</td> --%>
<!-- </tr> -->
<!-- </table> -->
<!-- <br/> -->
<table id="table1">
	<tr>
		<td>
			<table class="tableList" id="conTable">
				<tr>
					<th colspan="4">
						<div>
							<div style="display:block;float: left">
								基础信息&nbsp;&nbsp;
								（合同编号：<c:out value="${cnt.cntNum }"></c:out>）&nbsp;&nbsp;&nbsp;&nbsp;</div>
								<div class="tdInfo" style="display:block;float: left;font-weight:500">变更内容说明：${Info.operateLog}</div>
							<div align="right" style="display:block;float: right;" ><span>第1页</span></div>
						</div>
						<input type="hidden" value="${cnt.cntNum }" name="cntNum" id="cntNum"/>
						<input type="hidden" value="${cnt.dataFlag }" name="dataFlag"/>
						<input type="hidden" value="${cnt.org1Code }" name="org1Code"/>
						<input type="hidden" value="${cnt.createDept }" name="createDept"/>
						<input type="hidden" value="${cnt.payDutyCode }" name="payDutyCode"/>
						<input type="hidden" value="${cnt.currency }" name="currency"/>
						<input type="hidden" value="${cnt.isOrder }" name="isOrder"/>
						<input type="hidden" value="${cnt.bgtType }" name="bgtType">
						<input type="hidden" name="deviceChg"/>
						<input type="hidden" id="feeStartFlag" value="${feeStartFlag }"/>
					</th>
				</tr>
				<tr >
					<td class="tdLeft">合同事项<span class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="cntName" id="cntName" maxlength="100" class="base-input-text" valid value="${cnt.cntName}"/>
					</td>
					<td class="tdLeft">合同总金额(不含税金额+税额)</td>
					<td class="tdRight"  >
						<span id="cntAllAmtSpan">${cnt.cntAllAmt}</span>
						<input type="hidden" name="cntAllAmt" value="${cnt.cntAllAmt}" id="cntAllAmt" maxlength="100" class="base-input-text" />
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">合同金额（不含税金额）<span id="cntAmtRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="hidden" name="cntAmt" id="cntAmt" valid class="base-input-text" value="${cnt.cntAmt}"/>
						${cnt.cntAmt}
					</td>
					<td class="tdLeft">税额<span   class="red">*</span></td>
					<td class="tdRight" >
						<input type="hidden" value="${cnt.cntTaxAmt}" name="cntTaxAmt" id="cntTaxAmt"    class="base-input-text" />
						${cnt.cntTaxAmt}
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">供应商<span id="providerCodeRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="hidden" name="providerType" id="providerType" value="${cnt.providerType}">
						<input type="hidden" name="provActCurr" id="provActCurr" value="${cnt.provActCurr}">
						<input type="hidden" name="provActNo" id="provActNo" value="${cnt.provActNo}">
						<input type="hidden" name="providerCode" id="providerCode" value="${cnt.providerCode}"/>
						
						<input type="hidden" name="providerAddr" id="providerAddr" value="${cnt.providerAddr}"/>
						<input type="hidden" name="actName" id="actName" value="${cnt.actName}"/>
						<input type="hidden" name="bankInfo" id="bankInfo" value="${cnt.bankInfo}"/>
						<input type="hidden" name="bankCode" id="bankCode" value="${cnt.bankCode}"/>
						<input type="hidden" name="bankArea" id="bankArea" value="${cnt.bankArea}"/>
						<input type="hidden" name="bankName" id="bankName" value="${cnt.bankName}"/>
						<input type="hidden" name="actType" id="actType" value="${cnt.actType}"/>
						<input type="hidden" name="payMode" id="payMode" value="${cnt.payMode}"/>
						
						<input type="text" name="providerName" id="providerName" class="base-input-text" valid readonly onclick="queryProvider(this,'provider')" value="${cnt.providerName}"/>
					</td>
					<td class="tdLeft" id="srcPoviderTdLeft" style="display:none">内部供应商<span class="red">*</span></td>
					<td class="tdRight" id="srcPoviderTdRight" style="display:none">
						<input type="text" name="srcPoviderName" id="srcPovider" readonly="readonly"  class="base-input-text" value="${cnt.srcPoviderName}"/>
						<a><img border="0" alt="查找" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/search.jpg" onclick="querySrcProvider(this)" style="vertical-align: middle;margin-left: 10px"></a>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">供应商信息</td>
					<td class="tdRight" colspan="3">
						<table >
							<tr>
								<td style="border: 0">银行名称：<span id="bankNameSpan">${cnt.bankName}</span></td>
								<td style="border: 0">供应商账号：<span id="provActNoSpan">${cnt.provActNo}</span></td>
							</tr>
							<tr>
								<td style="border: 0">供应商地址：<span id="providerAddrSpan">${cnt.providerAddr}</span></td>
								<td style="border: 0">银行账户名称：<span id="actNameSpan">${cnt.actName}</span></td>
							</tr>
							<tr>
								<td style="border: 0">开户行行号：<span id="bankCodeSpan">${cnt.bankCode}</span></td>
								<td style="border: 0">开户行详细信息：<span id="bankInfoSpan">${cnt.bankInfo}</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">
						是否存在关联合同
					</td>
					<td class="tdRight">
						<div class="ui-widget" style="display:inline">
							<select id="isRelatedYN" class="selectC" onchange="changeIsRelatedCnt()">
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_IS_YN'" orderType="ASC" />
							</select>
						</div>
					</td>
					<td class="tdLeft relateCntTd">关联合同号<span class="red">*</span></td>
					<td class="tdRight relateCntTd">
						<input type="text" id="cntNumRelated" name="cntNumRelated" class="base-input-text" value="${cnt.cntNumRelated }" onclick="relatedCnt()" valid readonly/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">审批类别</td>
					<td class="tdRight">
						<div class="ui-widget">
							<select id="lxlx" name="lxlx" class="selectC" onchange="changLxlx('change')">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_APPROVE_TYPE'"
								 orderType="DESC" selectedValue="${cnt.lxlx }" />
							</select>
						</div>
					</td>
					<td class="tdLeft" id="qbhTdLeft"> 签报文号<span id="qbhRedSpan" class="red">*</span></td>
					<td class="tdRight" id="qbhTdRight">
						<input type="text" name="qbh" id="qbh" maxlength="40" valid  class="base-input-text" value="${cnt.qbh}"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">集采编号</td>
					<td class="tdRight"><input type="text" name="stockNum" id="stockNum" maxlength="100" class="base-input-text" value="${cnt.stockNum}" /></td>
					<td class="tdLeft">评审编号</td>
					<td class="tdRight"><input type="text" name="psbh" id="psbh" maxlength="400" class="base-input-text" value="${cnt.psbh}"/></td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">其中合同质保金(%)<span id="zbAmtRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="zbAmt" valid id="zbAmt" class="base-input-text" value="${cnt.zbAmt}"/>
					</td>
					<td class="tdLeft">签订日期<span id="providerCodeRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="signDate" id="signDate" valid class="base-input-text" value="${cnt.signDate}" readonly/>
					</td>
				</tr>
				<tr class="collspan" style="display: none">
					<td class="tdLeft">增值税率</td>
					<td class="tdRight" id="providerTaxRateSpan">
						<input type="text" id="providerTaxRate" name="providerTaxRate" value="${cnt.providerTaxRate }" class="base-input-text"/>
					</td>
					<td class="tdLeft">增值税金</td>
					<td class="tdRight">
						<input type="text" name="providerTax" id="providerTax" value="${cnt.providerTax }" class="base-input-text"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">审批数量
<%-- 						<a><img id="addlxslImg" style="display:none" border="0" vaild width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addLxsl()'></a> --%>
					</td>
					<td class="tdRight">
						<input type="text" id="lxsl" name="lxsl" maxlength="10" class="base-input-text" value="${cnt.lxsl}"><span id="lxslSpan"></span>
					</td>
					<td class="tdLeft"> 审批金额<span id="lxjeRedSpan" class="red">*</span></td>
					<td class="tdRight"><input type="text" name="lxje" id="lxje" class="base-input-text" value="${cnt.lxje}"/><span id="lxjeSpan"></span></td>
				</tr>
				<tr id="DZSPTr" style="display:none">
					<td colspan="4">
						<table id="DZSPTable" >
							<tr>
								<th width='20%'>缩位码</th>
								<th width='20%'>审批编号</th>
								<th width='10%'>日期</th>
								<th width='15%'>审批数量</th>
<!-- 								<th width='10%'>已执行数量</th> -->
								<th width='15%'>数量</th>
								<th width='15%'>审批金额</th>
<!-- 								<th width='15%'>已执行金额</th> -->
<!-- 								<th width='10%'>金额</th> -->
								<th width='5%'>
									<a><img id="addlxslImg" style="display:none" width="90%"
									src='<%=request.getContextPath()%>/common/images/add2.jpg' 
									alt='添加' onclick='addLxsl()'></a>
								</th>
							</tr>
							<c:forEach items="${cnt.dzspInfos}" var="dzspItem" varStatus="status">
								<tr id="dzsp${status.index+1}">
									<td>${dzspItem.abcde}<input type="hidden" name="abcde" value="${dzspItem.abcde}"/></td>
									<td>${dzspItem.projCrId}</td>
									<td>${dzspItem.createDate}</td>
									<td><input type='hidden' name='projCrAmt' value='${dzspItem.projCrAmt}'/><fmt:formatNumber type="number" value="${dzspItem.projCrAmt}"/></td>
<%-- 									<td>${dzspItem.exeNum}</td> --%>
									<td><input type="text" name="abcdeNum" class="base-input-text" style="width:100%" value="${dzspItem.abcdeNum}"/></td>
									<td><fmt:formatNumber type="number" value="${dzspItem.projCrAmt}"/></td>
<%-- 									<td><fmt:formatNumber type="number" value="${dzspItem.exeAmt}"/></td> --%>
<%-- 									<td><input type="text" class="base-input-text" name="abcdeAmt" style="width:100%" value="${dzspItem.abcdeAmt}"/></td> --%>
									<td><a><img border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteDZSPTr(this)"/></a></td>
									
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" class="base-input-button" value="下一页" onclick="pageOneNext()"/>
		</td>
	</tr>
</table>
<table id="table2" style="display:none;" >
	<tr>
		<td>
			<table class="tableList">
				<tr>
<!-- 				<tr class="collspan-control"> -->
					<th colspan="4">
						<div>
							<div style="display:block;float: left">
								合同信息（合同编号：<c:out value="${cnt.cntNum }"></c:out>）&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							<div class="tdInfo" style="display:block;float: left;font-weight:500">变更内容说明：${Info.operateLog}</div>
							<div style="display:block;float: right;">
								第2页
							</div>							
						</div>
					</th>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">合同类型<span class="red">*</span></td>
					<td class="tdRight" >
						${cnt.cntTypeName}
						<div class="ui-widget orderBackDiv" style="display: none;">
							<select id="cntType" name="cntType" class="selectC" onchange="changeCntType('change')" valid errorMsg="请选择合同类型">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
								 orderType="ASC" selectedValue="${cnt.cntType }" />
							</select>
						</div>
					</td>
					<td class="tdLeft"><c:if test="${cnt.feeType ne '2' }">是否专项包<span class="red">*</span></c:if></td>
					<td class="tdRight">
						<c:if test="${cnt.feeType ne '2' }">
							<c:if test="${cnt.isSpec=='0'}">
							是
						</c:if>
						<c:if test="${cnt.isSpec=='1'}">
							否
						</c:if>
						<div class="ui-widget orderBackDiv" style="display:none">
							<select id="isSpec" name="isSpec" class="selectC" onchange="changeIsSpec()" valid errorMsg="请选择是否专项包">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_IS_YN'"
								 orderType="ASC" selectedValue="${cnt.isSpec }"/>
							</select>
						</div>
						</c:if>
						
					</td>
				</tr>
				<tr id="isProvinceBuyTr" style="display:none">
					<td class="tdLeft">省行统购项目<span class="red">*</span></td>
					<td class="tdRight" colspan="3">
					<c:if test="${cnt.isProvinceBuy=='0'}">
							是
						</c:if>
						<c:if test="${cnt.isProvinceBuy=='1'}">
							否
						</c:if>
						<div class="ui-widget orderBackDiv" style="display:none">
							<select id="isProvinceBuy" name="isProvinceBuy" class="selectC" onchange="changeProvinceBuy()" errorMsg="请选择是否省行统购">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_IS_YN'"
								 orderType="ASC" selectedValue="${cnt.isProvinceBuy }"/>
							</select>
						</div>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">付款条件<span class="red">*</span></td>
					<td class="tdRight" colspan="3">
						<div>
							<div class="ui-widget" style="display:inline">
								<select id="payTerm" name="payTerm" class="selectC" onchange="changePayTerm('change')">
									<option value="">--请选择--</option>						
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
									 conditionStr="CATEGORY_ID = 'CNT_PAY_TERM'"
									 orderType="ASC" selectedValue="${cnt.payTerm }"/>
								</select>
							</div>
							<div class="ui-widget" id="stageTypeDiv" style="display:none">
								<select id="stageType" name="stageType" onchange="changeStageType()" class="selectC">
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
									 conditionStr="CATEGORY_ID = 'CNT_STAGE_TYPE'"
									 orderType="ASC" selectedValue="${cnt.stageType }"/>
								</select>
							</div>
						</div>
					</td>		
				</tr>
				<tr id="onScheduleTr" style="display:none">
					<td class="tdLeft" valign="top">按进度分期付款<a><img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addOnScheduleTr()'></a></td>
					<td class="tdRight" colspan="3">
						<table id="onScheduleTable">
						<c:choose>
							<c:when test="${cnt.stageType == '0'}">
							<c:forEach items="${cnt.stageInfos}" var="schItem" varStatus="status">
							<tr>
								<td>第<span name='sceduleTrCnt'>${status.index+1}</span>期&nbsp;&nbsp;付款年月
									<input type='text' name='jdDate' readonly class='base-input-text jdDate' value="${schItem.jdDate}"/>
									 支付
									<input type='text' name='jdzf' class='base-input-text' style='width:60px' value="${schItem.jdzf}">&nbsp;元
								</td>
								<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteScheduleTr(this)'/></a></td>
							</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
							<!-- 
							<tr>
								<td>第1期&nbsp;&nbsp;付款年月
									<input type='text' name='jdDate' class='base-input-text jdDate' style="width: 120px"/>
									<input type='text' name='jdtj' class='base-input-text' style='width:60px'>&nbsp;%支付
									<input type='text' name='jdzf' class='base-input-text' style='width:60px'>&nbsp;元</td>
								<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteScheduleTr(this)'/></a></td>
							</tr>
							 -->
							</c:otherwise>
						</c:choose>
						</table>
					</td>
				</tr>
				<tr id="onDateTr" style="display:none">
					<td class="tdLeft" valign="top">按日期分期付款<a><img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addOnDateTr()'></a></td>
					<td class="tdRight" colspan="3">
						<table id="onDateTable">
						<c:choose>
							<c:when test="${'1'==cnt.stageType}">
							<c:forEach items="${cnt.stageInfos}" var="dateItem">
								<tr>
									<td>合同签订后<input type='text' name='rqtj' class='base-input-text' style='width:60px' value="${dateItem.rqtj}">&nbsp;天&nbsp;&nbsp;支付
										<input type='text' name='rqzf' class='base-input-text' style='width:60px' value="${dateItem.rqzf}">&nbsp;元
									</td>
									<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteTr(this)'/></a></td>
								</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td>合同签订后<input type='text' name='rqtj' class='base-input-text' style='width:60px'>&nbsp;天&nbsp;&nbsp;支付
										<input type='text' name='rqzf' class='base-input-text' style='width:60px'>&nbsp;元</td>
									<td><a><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteTr(this)'/></a></td>
								</tr>
							</c:otherwise>
						</c:choose>
						</table>
					</td>
				</tr>
				<tr id="onTermTr" style="display:none">
					<td class="tdLeft">按条件分期付款</td>
					<td class="tdRight" colspan="3">
						到货支付&nbsp;&nbsp;<input type="text" class="base-input-text" id="dhzf" name="dhzf" value="${cnt.stageInfos[0].dhzf}">&nbsp;&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;
						验收支付&nbsp;&nbsp;<input type="text" class="base-input-text" id="yszf" name="yszf" value="${cnt.stageInfos[0].yszf}">&nbsp;&nbsp;元<br/>
						结算支付&nbsp;&nbsp;<input type="text" class="base-input-text" id="jszf" name="jszf" value="${cnt.stageInfos[0].jszf}">&nbsp;&nbsp;元
					</td>
				</tr>
				<c:if test="${cnt.cntType eq '1' }">
				<tr class="feeClass">
				
					<td class="tdLeft">费用类型</td>
					<td class="tdRight">
						<c:if test="${cnt.feeType=='0'}">
							金额固定、受益期固定
						</c:if>
						<c:if test="${cnt.feeType=='1'}">
							受益期固定、合同金额不固定
						</c:if>
						<c:if test="${cnt.feeType=='2'}">
							其他
						</c:if>
						<div class="ui-widget orderBackDiv" style="display:none">
							<select id="feeType" name="feeType" class="selectC" onchange="changeFeeType()">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_TYPE'"
								 orderType="ASC" selectedValue="${cnt.feeType }"/>
							</select>
						</div>
					</td>
					<td class="tdLeft" id="feeSubTypeTdLeft"> 费用子类型<span class="red">*</span></td>
					<td class="tdRight" id="feeSubTypeTdRight">
						<c:if test="${cnt.feeSubType=='0'}">
							普通费用类
						</c:if>
						<c:if test="${cnt.feeSubType=='1'}">
							房屋租赁类
						</c:if>
						<div class="ui-widget" style="display:none">
							<select id="feeSubType" name="feeSubType" class="selectC" onchange="changeFeeSubType()">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_SUB_TYPE'"
								 orderType="ASC" selectedValue="${cnt.feeSubType }"/>
							</select>
						</div>
					</td>
				</tr>
				</c:if>
				<tr id="feeDateTr" class="feeClass" style="display:none">
					<td class="tdLeft">合同受益起始日期</td>
					<td class="tdRight">${cnt.feeStartDate}<input type="hidden" name="feeStartDate" id="feeStartDate" class="base-input-text" value="${cnt.feeStartDate}" readonly onchange="removeFeeInfoTable()"/></td>
					<td class="tdLeft"> 合同受益终止日期</td>
					<td class="tdRight"> ${cnt.actualFeeEndDate}<input type="hidden" name="actualFeeEndDate" id="actualFeeEndDate" class="base-input-text" value="${cnt.actualFeeEndDate}" readonly onchange="removeFeeInfoTable()"/></td>
				</tr>
				<tr id="feeCntTr" class="feeClass" style="display:none">
					<td class="tdLeft">实际合同受益终止日期区间</td>
					<td class="tdRight">${cnt.feeStartDateShow} 至  ${cnt.feeEndDateShow}<input type="hidden" name="feeEndDate" id="feeEndDate" value="${cnt.feeEndDate}"/></td>
					<td class="tdLeft"></td>
					<td class="tdRight"></td>
				</tr>
				<tr id="feeAmtTr" class="feeClass" style="display:none">
					<td class="tdLeft">合同金额（人民币）确定部分</td>
					<td class="tdRight">${cnt.feeAmt}<input type="hidden" name="feeAmt" id="feeAmt" class="base-input-text" value="${cnt.feeAmt}"/></td>
					<td class="tdLeft">合同金额（人民币）约定罚金</td>
					<td class="tdRight"> ${cnt.feePenalty}<input type="hidden" name="feePenalty" id="feePenalty" class="base-input-text" value="${cnt.feePenalty}"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="上一页" onclick="pageTwoBefore()"/>
			<input id="pageTwoNextButton" type="button" value="下一页" onclick="pageTwoNext()"/>
		</td>
	</tr>
</table>
<table id="table3" style="display:none">
	<tr id="tr3" >
		<td>
			<table id="feeInfo" class="tableList">
				<tr>
<!-- 				<tr class="collspan-control"> -->
					<th colspan="4" id="number3PageTh">
						<div>
							<div style="display:block;float: left">
								物料采购信息（合同编号：<c:out value="${cnt.cntNum }"></c:out>）&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							<div class="tdInfo" style="display:block;float: left;font-weight:500">变更内容说明：${Info.operateLog}</div>
							<div style="display:block;float: right;">
								第3页
							</div>							
						</div>	
					</th>
				</tr>
				<tr class="collspan">
					<td class="tdLeft" style="width:25%;">采购数量</td>
					<td class="tdRight" style="width:75%;">
						<input type="hidden" name="totalNum" id="totalNum" value="${cnt.totalNum}" onchange="setDeviceChg('noMark',0)"/>
						<span id="totalNumSpan">${cnt.totalNum}</span>
					</td>
				</tr>
				<tr class="collspan">
					<td colspan="2" style="padding-left:5px;">
						<div id="scrollTableDiv" style="overflow-x:scroll;width:1350px;">
							<table id="totalNumTable" style="table-layout: fixed;">
								<tr>
<%-- 									<th  width="100px"><a><img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick="addTotalNum()"></a>&nbsp;&nbsp;项目</th>			 --%>
									<th  width="100px">项目</th>			
									<th  width="190px">费用承担部门</th> 
									<th  width="150px">物料类型</th>      
									<th  width="100px">监控指标</th>      
									<th  width="100px">设备型号</th>      
									<th  width="110px">专项</th>         
									<th  width="110px">参考</th> 
									<th  width="110px">税码</th>         
									<th  width="60px">数量</th>          
									<th  width="60px">单价(元)</th>  
									<th  width="80px">不含税金额(元)</th>
									<th  width="80px">税额(元)</th>  
									<th  width="70px">保修期(年)</th>
									<th  width="70px">制造商</th>        
									<th  width="100px">审批意见</th>
<!-- 									<th  width="50px">操作</th>       -->
								</tr>
								<c:forEach items="${cntDev}" var="projItem">
									<tr>
										<td><input type="hidden" valid name="projId" value="${projItem.projId}"/><input type="text" readonly name="projName" onclick="projOptionPage(this)" class="base-input-text" style="width:100px" value="${projItem.projName}" title="${projItem.projName}"></td>
										<td ><input type="hidden" name="feeDept" value="${projItem.feeDept}"  id="${projItem.feeDept}" class="feeDeptNew">
										     <input name="feeDeptName" title="${projItem.feeDept}" type="text" class="base-input-text" style="width:190px" value="${projItem.feeDeptName}" onclick="setDeviceChg(this,2)"/>
										     <input type="hidden" name="feeDeptOld" value="${projItem.feeDept}" id="feeDeptOld" >
										</td>
							   		    <td><input type="hidden" name="trCglCode" value="${projItem.cglCode}"/><input type="hidden" name="matrCode" value="${projItem.matrCode}"/>
							   				<input type="hidden" name="matrName" valid onclick="matrTypeOptionPage(this)" readonly class="base-input-text" style="width:150px" title="${projItem.matrName}" value="${projItem.matrName}"> ${projItem.matrName}</td>
							   		    <td><input type="hidden" name="montCode" value="${projItem.montCode }"><span name="montName" style="width:100px">${projItem.montName }</span></td>
							   		    <td><input type="hidden"  name="deviceModelName" maxlength="20" class="base-input-text" style="width:100px" value="${projItem.deviceModelName}" onchange="setDeviceChg(this,2)">${projItem.deviceModelName}</td>
							   		    <td>
							   		    ${projItem.specialName}
							   		    	<div class="ui-widget" style="display:none;">
									   		    <select name="special" class="selectC" onchange="setDeviceChg(this,3)"> 
													 <forms:codeTable tableName="TB_SPECIAL" selectColumn="SPECIAL_ID,SPECIAL_NAME" 
													 valueColumn="SPECIAL_ID" textColumn="SPECIAL_NAME" orderColumn="SPECIAL_ID"  
													 orderType="ASC" selectedValue="${projItem.special}"/>
												</select>
											</div>
										</td>
							   		    <td>
							   		    ${projItem.referenceName}
							   		    	<div class="ui-widget" style="display:none">
									   		    <select name="reference" class="selectC" onchange="setDeviceChg(this,3)">
													 <forms:codeTable tableName="TB_REFERENCE" selectColumn="REFERENCE_ID,REFERENCE_NAME" 
													 valueColumn="REFERENCE_ID" textColumn="REFERENCE_NAME" orderColumn="REFERENCE_ID"  
													 orderType="ASC" selectedValue="${projItem.reference}"/>
												</select>
											</div>
										</td>
										<td><input type="hidden" name="taxCode" value="${projItem.taxCode }">${projItem.taxCode}
										<input type="hidden" name="taxRate" value="${projItem.taxRate }">
										</td>
							   		    <td>
							   		    	 <c:if test="${'0'==cnt.cntType}">
								   		    	<input type="hidden" valid name="execNum" class="base-input-text" style="width:60px" onblur="computTotalNum(this)" value="${projItem.execNum}" onchange="setDeviceChg(this,2);removeFeeInfoTable()">
								   		    	${projItem.execNum}
								   		    </c:if>
								   		    <c:if test="${'1'==cnt.cntType}">
								   		    	${projItem.execNum}<input type="hidden" name="execNum" value="${projItem.execNum}">
								   		    </c:if>
							   		    </td>
							   		    <td>
							   		    	<input type="hidden" valid name="execPrice" class="base-input-text" style="width:60px" onblur="computTotalNum(this)" value="${projItem.execPrice}" onchange="setDeviceChg(this,2);removeFeeInfoTable()">
							   		    	${projItem.execPrice}
							   		    </td>
							   		    <td>${projItem.execAmt }
							   		    	<input type="hidden" name="execAmt" value="${projItem.execAmt }" class="base-input-text" style="width:60px"/>
							   		    </td>
							   		    <td><input type="hidden"   name="cntTrAmt" readonly class="base-input-text" style="width:80px" value="${projItem.cntTrAmt}" onchange="setDeviceChg(this,2)">${projItem.cntTrAmt}
							   		    <input type="hidden" name="deductFlag" value="${projItem.deductFlag }" class="base-input-text" style="width:60px"/>
							   		    </td>
							   		    <td><input type="hidden"  name="warranty" maxlength="8" class="base-input-text" style="width:70px" value="${projItem.warranty}" onchange="setDeviceChg(this,2)">${projItem.warranty}</td>
							   		    <td><input type="hidden"  name="productor" class="base-input-text" style="width:70px" value="${projItem.productor}" onchange="setDeviceChg(this,2)">${projItem.productor}</td>
							   		    <td>
							   		    	<c:if test="${empty projItem.auditMemo}">
												<p align="center">——————</p>
											</c:if>
											<c:if test="${!empty projItem.auditMemo}">
												${projItem.auditMemo}
											</c:if>
<%-- 												${projItem.auditMemo} --%>
											<input type="hidden" name="auditMemo" value="${projItem.auditMemo }"/>
							   		    	<input type="hidden" valid name="devDataFlag" value="${projItem.dataFlag}"/>
							   		    	<input type="hidden" valid name="projDevChg" value="false"/>
							   		    	<input type="hidden" name="isOrderSucDev" value="1" />		
											</td>
<!-- 										<td> -->
<%-- 							   		    	<a><img border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteToltalNum(this);setDeviceChg('noMark',0);"/></a> --%>
<!-- 							   		    </td> -->
									</tr>
								</c:forEach>
							</table>
						</div>
						<forms:OrgSelPlugin  triggerEle="#totalNumTable tr feeDeptName::name" rootNodeId="${rootFeeDept}" initValue="${defaultFeeDept }"
									 parentCheckFlag="false" dialogFlag="true" jsVarName="feeDeptTag" changeFun="changeFeeDept"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft" style="width:25%;">备注(<span id="authmemoSpan">0/2000</span>)</td>
					<td class="tdRight" style="width:75%;">
						<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,2000,'authmemoSpan')" id="memo" name="memo">${cnt.memo}</textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr id="fz_select" style="display: none">
		<td>
			<input type="button" value="上一页" onclick="pageThreeBefore()"/>
			<input id="pageThreeNextButton" type="button" value="下一页" onclick="pageThreeNext()"/>
		</td>
	</tr>
	<tr id="fz_notselect">
		<td>
			<input type="button" value="上一页" onclick="pageThreeBefore()"/>
			<div id="pageThreeSubmitButton" style="display:inline;">
				<c:if test="${!empty cnt.id}">
					<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.cntNum}','${cnt.icmsPkuuid }')"/>
					<input type="button" value="重新扫描" onclick="scan()"/>
				</c:if>
				<input type="button" value="更新" onclick="pageThreeSubmit()"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</div>
		</td>
	</tr>
	<tr style="display: none;">
		<td id="tableDiv">
		<c:if test="${!empty cnt.benefit}">
			<table id="targetT">
				<tr id="th">
					<th width="10%">年份</th>
					<th width="10%">月份</th>
					<th width="10%">序号</th>
					<th width="10%">费用承担部门</th>
					<th width="10%">物料名称</th>
					<th width="10%">核算码</th>
					<th width="10%">参考</th>
					<th width="10%">专项</th>
					<th width="15%">测算金额</th>
					<th width="15%">受益金额</th>
				</tr>
				<c:forEach items="${cnt.benefit}" var="cntItem">
				<tr>
					<td id="year" rowspan="1">${fn:substring(cntItem.feeYyyymm,0,4)}</td>
			        <td id="month" rowspan="1">${fn:substring(cntItem.feeYyyymm,4,6)}</td>
			        <td><input type="hidden" value="${cntItem.feeYyyymm}" name="feeYyyymm"/><input type="hidden" value="${cntItem.subId}" name="subId"/>${cntItem.subId}</td>
			        <td>${cntItem.feeDeptName}</td>
					<td>${cntItem.matrCode}${cntItem.matrName}</td>
					<td>${cntItem.cglCode}</td>
					<td>${cntItem.specialName}</td>
					<td>${cntItem.referenceName}</td>
					<td><input type="hidden" value="${cntItem.cglCalAmt}" name="cglCalAmt"/>${cntItem.cglCalAmt}</td>
					<td><input type="text" value="${cntItem.cglFeeAmt}" name="cglFeeAmt" id="${cntItem.subId}"/></td>
				</tr>
				</c:forEach>
			</table>
		</c:if>
		</td>
	</tr>
	
</table>	
<table id="table4" style="display: none;">
	<tr id="tr4">
		<td>
			<table id="feeInfoDz" class="tableList">
				<tr>
					<th colspan="4">
						<div>
							<div style="display:block;float: left;">
								物料采购信息（合同编号：<c:out value="${cnt.cntNum }"></c:out>）
							</div>
							<div style="display: block; float: left; margin-left: 20px;">
								合同总金额：<span id="cntAmt4"></span> &nbsp;&nbsp;
								合同不含税金额：<span id="execAmt4"></span> &nbsp;&nbsp;
								合同税额：<span id="cntTaxAmt4"></span>
							</div>
							<div style="display: block; float: right;">第4页</div>
						</div>
					</th>
				</tr>
				<tr id="houseTr" style="display: none" class="feeClass">
					<td colspan="4">
						<table id="houseTable">
							<tr>
								<td class="tdLeft">地址(说明)<span class="red">*</span></td>
								<td class="tdRight"><input type="text" name="wydz" id="wydz" maxlength="1000" valid class="base-input-text" value="${cnt.wydz }"/></td>
								<td class="tdLeft">租赁面积(数量)<span class="red">*</span></td>
								<td class="tdRight"><input type="text" name="area" id="area" valid onkeyup="$.clearNoNum(this);" class="base-input-text" value="${cnt.area }"/><br>平方米(项)</td>
							</tr>
							<tr id="fc_select">
								<td class="tdLeft">租赁类型<span class="red">*</span></td>
								<td class="tdRight" colspan="3">
									<div class="ui-widget">
										<select id="houseKindId" name="houseKindId" class="selectD" valid onchange="fcSelect();">
											<option value="">--请选择--</option>
											<forms:codeTable tableName="SYS_SELECT"
												selectColumn="PARAM_VALUE,PARAM_NAME"
												valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"
												orderColumn="PARAM_VALUE"
												conditionStr="CATEGORY_ID = 'HOUSE_KIND'" orderType="ASC"
												selectedValue="${cnt.houseKindId }" />
										</select>
										
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class="collspan">
					<td style="padding-left: 5px;" colspan="4" id="TenancyDzTd">
						<!-- <div id="scrollTableDivDz"
							style="width: 1500px; overflow-x: scroll;">
						</div> -->
					</td>
				</tr>
				<tr>
					<td class="tdLeft">备注(<span id="remarkSpan">0/2000</span>)
					</td>
					<td class="tdRight" colspan="3"><textarea
							class="base-textArea"
							onkeyup="$_showWarnWhenOverLen1(this,2000,'remarkSpan')"
							id="remark" name="remark"></textarea></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td><input type="button" value="上一页" onclick="pageFourBefore()" />
			<div id="pageThreeSubmitButton" style="display:inline;">
				<c:if test="${!empty cnt.id}">
					<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.cntNum}','${cnt.icmsPkuuid }')"/>
					<input type="button" value="重新扫描" onclick="scan()"/>
				</c:if>
				<input type="button" value="更新" onclick="pageFourSubmit()"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			
			</div>
		</td>
	</tr>
</table>
<table id="feeTable" style="display: none">
	<c:forEach items="${feeDeptList}" var="feeDept">
		<input  type="hidden" value="${feeDept}" id="feeId" class="feeId"></input>
	</c:forEach>
</table>
</form>
</body>
</html>