<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同新增</title>
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
		data['targetT'] = $("#tableDiv").html();
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
		App.notyError("采购项目数量不能为0！");
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
	App.ajaxSubmitForm(url, $("#addForm"),  
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
		App.notyError("采购项目数量不能为0！");
		return false;
	}
	var pass = true;
	var projName = getProjAmt();
	if(null != projName && "" != projName){
		
		if(!confirm("项目["+projName+"]的采购金额超出剩余项目预算，是否继续新增合同？")){
			pass = false;
		}
	}
	return pass;
}

</script>
<script type="text/javascript">
var reg=/^[1-9]{1}[0-9]*$/;
function pageInit() {
	App.jqueryAutocomplete();
	$(".selectC").combobox();
	$(".selectD").combobox();
	$("#TenancyDzTable .ui-autocomplete-input").css("width","40px");
	$("#signDate,#feeStartDate,#feeEndDate,#beginDate,#endDate,.jdDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
	if(getMonthOverFlag()){
		App.notyError("月结状态中，暂不能进行合同新增操作！");
	}
}

function getMonthOverFlag(){
	var url = "contract/initiate/getMonthOverFlag.do?<%=WebConsts.FUNC_ID_KEY%>=0302010105";
	var returnValue ;
	App.ajaxSubmit(url, {data:{},async : false},  
   		function(data){
			var monthOverFlag = data.monthOverFlag;
			returnValue =  monthOverFlag;
		});
	return returnValue;
}

function doValidate(){
	if(getMonthOverFlag()){
		App.notyError("月结状态中，暂不能进行合同新增操作！");
		return false;
	}
	
	//提交前调用检查
	var lxlxVal = $("#lxlx").val();
	var cntTypeVal = $("#cntType").val();
	
	$("#qbh,#lxje,#lxsl").removeAttr("valid");
	//审批类别
	if(lxlxVal == 1){
		//电子审批
		if(!checkDZSP()){
			return false;
		}
	}
	else if(lxlxVal == 2){
		//签报文项
		$("#qbh,#lxje,#lxsl").attr("valid","");
	}else if(lxlxVal == 3){
		//部内审批
		$("#lxje,#lxsl").attr("valid","");
	}
	//合同类型
	$("#feeSubType,#feeStartDate,#feeEndDate,#feeAmt").removeAttr("valid");
	var feeTypeVal = $("#feeType").val();
	if(cntTypeVal == 0){
		//资产类
	}else if(cntTypeVal == 1){
		//费用类
		var feeStartDate = $("#feeStartDate").val();
		var feeEndDate = $("#feeEndDate").val();
		
		if(feeTypeVal == 0){
			//金额固定、受益期固定
			$("#feeStartDate,#feeEndDate").attr("valid","");
			if(feeStartDate > feeEndDate){
				App.notyError("合同受益起始日期不能大于合同受益结束日期！");
				return false;
			}
			if($("#feeSubType").val() == 1){
				//房屋租赁
				//金额的校验
				if(!$.isBlank($("#yj").val())){
					checkMoney($("#yj"));
				}
				if(!$.isBlank($("#rent").val())){
					checkMoney($("#rent"));
				}
				if(!$.isBlank($("#wyglf").val())){
					checkMoney($("#wyglf"));
				}
				if(!checkMoney($("#area"))){
					return false;
				} 
				//部门下拉框校验
				var yfDept = $("input[name='yf']").val();
				var glbmDept = $("input[name='glbmId']").val();
				var wdjgDept = $("input[name='wdjgId']").val();
				if($.isBlank(yfDept)){
					App.notyError("乙方部门不能为空！");
					return false;
				}
				if($.isBlank(glbmDept)){
					App.notyError("管理部门不能为空！");
					return false;
				}
				if($.isBlank(wdjgDept)){
					App.notyError("网点机构不能为空！");
					return false;
				}
			}
		}else if(feeTypeVal == 1){
			//受益期固定、合同金额不固定
			$("#feeStartDate,#feeEndDate,#feeAmt").attr("valid","");
			if(feeStartDate > feeEndDate){
				App.notyError("合同受益起始日期不能大于合同受益结束日期！");
				return false;
			}
		}else if(feeTypeVal == 2){
			//受益期不固定，付款时确认费用
			$("#feeAmt").attr("valid","");
		}else if(feeTypeVal == 3){
			//宣传费
		}
		//var isOrder = $("#isOrderDiv").find(".check-label").next().val();
		var isOrder = $("#isOrder").val();
		if("1" == isOrder){
			if(feeTypeVal != 2 && feeTypeVal != 3 ){
				if($.isBlank($("#tableDiv").html())){
					App.notyError("请先输入费用类型录入金额！（点击费用类型旁的查询按钮，录入数据后点击确认即可。）");
					return false;
				}
			}
		}
	}
	
	//校验分期付款
	if($("#payTerm").val() == 3){
		if(!checkFqfktj()){
			return false;
		}
	}
	
 	//数量校验(审批数量)
	if(!checkNum($("#lxsl"))){
		return false;
	} 
 	
 	//质保金不能大于100%
 	if($("#zbAmt").val() > 100 || !$.checkMoney($("#zbAmt").val())){
 		App.notyError("质保金不能大于100%且最多包含两位小数！");
 		$("#zbAmt").focus();
 		return false;
 	}
	
	//金额的校验(审批金额、合同金额与质保金)
	if(!checkMoney($("#lxje")) || !checkMoney($("#cntAmt"))){
		return false;
	} 
	
	//合同金额不能大于审批金额
	if(Number($("#lxje").val()) < $("#cntAmt").val()){
		App.notyError("合同金额不能大于审批金额！");
		return false;
	}
	
	//var totalNumTrList = $("#totalNumTable tr");
	if(!$.checkRegExp($("#totalNum").val(),reg)){
		App.notyError("采购数量不正确！");
		return false;
	}
	
	//校验采购金额必须等于合同金额
	if(!checkCntAmt()){
		App.notyError("合同金额不等于采购总金额！");
		return false;
	}
	
	if(!App.valid("#addForm")){return;} 
	
	//检查订单类合同的供应商不能为内部供应商
	//var isOrder = $("#isOrderDiv").find(".check-label").next().val();
	var isOrder = $("#isOrder").val();
	if(isOrder == 0){
		var providerType = $("#providerType").val();
		if(providerType != 'VENDOR'){
			App.notyError("订单类合同的供应商类型只能是外部供应商，请重新选择供应商！");
			return false;
		}
	}
	
/* 	if(!hasScaned()){
		App.notyError("请先点击扫描按钮以添加扫描附件！");
		return false;
	} */
	
	//校验项目金额是否超出预算
	if(!checkProjAmt()){
		return false;
	}
	return true;
}

//校验合同金额是否等于采购金额
function checkCntAmt(){
	var cntAmt = $("#cntAmt").val();
	var totalAmt = 0;
	var trList = $("#totalNumTable tr:gt(0)");
	for(var i=0; i<trList.length; i++){
		totalAmt += Number($(trList[i]).find("input[name='execAmt']").val());
	}
	if(cntAmt == totalAmt){
		return true;
	}
	return false;
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
		var abcdeAmt = $(DZSPTrList[i]).find("input[name='abcdeAmt']");
		if(!$.checkMoney($(abcdeAmt).val())){
			App.notyError("第【"+i+"】条电子审批记录的金额输入值不合法，请重新输入！");
			return false;
		}else if(!$.checkRegExp($(abcdeNum).val(),reg)){
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
		var abcdeAmt = $(DZSPTrList[i]).find("input[name='abcdeAmt']");
		totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat($(abcdeAmt).val().replace(/\,/g,''),3),2);
	}
	$("#lxje").val(totalAmt); 
}

//汇总电子审批数量
function getAbcdeNum(){
	var DZSPTrList = $("#DZSPTable tr");
	var totalNum=0;
	for(var i=1;i<DZSPTrList.length;i++)
	{
		var abcdeNum = $(DZSPTrList[i]).find("input[name='abcdeNum']").val();
		totalNum=(parseInt(totalNum)+parseInt(abcdeNum));
	}
	if($.isNumeric(totalNum)){
		$("#lxsl").val(totalNum);
	}
}

//重置表单
function resetAll(){
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}

//审批类别下拉框改变触发(除签报立项外，其他两种类型签报文号不能输入)
function changLxlx(){
	var changeVal = $("#lxlx").val();
	$("#addlxslImg,#DZSPTr,#qbhRedSpan,#lxjeRedSpan,#lxslRedSpan").hide();
	$("#qbh").attr("disabled","disabled").val("");
	$("#lxsl,#lxje").removeAttr("disabled");
	$("#DZSPTr").removeClass("collspan");
	if(changeVal == 1){
		//电子审批
		$("#addlxslImg,#DZSPTr").show();
		$("#lxsl,#lxje").attr("disabled","disabled");
		$("#DZSPTr").addClass("collspan");
		getAbcdeNum();
		getAbcdeAmt();
	}else if(changeVal == 2){
		//签报立项
		$("#qbhRedSpan,#lxjeRedSpan,#lxslRedSpan").show(); 
		//$("#lxsl").val(0);
		//$("#lxje").val(0);
		$("#qbh").removeAttr("disabled");
	}else if(changeVal == 3){
		//部内审批
		$("#lxjeRedSpan,#lxslRedSpan").show();
		//$("#lxsl").val(0);
		//$("#lxje").val(0);
	}
}

//合同类型下拉框改变触发
var curCntType = 0;
function changeCntType(){
	var changeVal = $("#cntType").val();
	
	if(changeVal != curCntType){
		$("#houseTr,#feeAmtTr,#tr2,#tr3").hide();
		$("#totalNumTable tr:gt(0)").remove();
		$("#totalNum").val(0);
		$("#tableDiv").empty();
		if(changeVal == 0){
			//资产类
			$("#execPriceTd").add('<input type="text" valid name="execPrice" class="base-input-text" style="width:100%" onblur="computTotalNum(this)" onchange="removeFeeInfoTable()">');
			$("#feeInfo").hide();
		}else if(changeVal == 1){
			//费用类
			$("#execPriceTd").add('1<input type="hidden" name="execPrice" value="1">');
			$("#feeInfo,#tr2,#tr3").show();
			changeFeeType();
			changeFeeSubType();
		}
		curCntType = changeVal;
	}
}

//费用类型下拉框改变触发
var curFeeType = '0';
function changeFeeType(){
	var changeVal = $("#feeType").val();
	//var isOrder = $("#isOrderDiv").find(".check-label").next().val();
	var isOrder = $("#isOrder").val();
	$("#feeSubTypeTdLeft,#feeSubTypeTdRight,#feeDateTr,#feeAmtTr,#houseTr").hide().removeClass("collspan");
	$("#feeInfoImg").show();
	if(changeVal == 0){
		//金额固定、受益期固定
		$("#feeSubTypeTdLeft,#feeSubTypeTdRight,#feeDateTr").show();
		$("#feeDateTr").addClass("collspan");
		changeFeeSubType();
		if(isOrder == 0){
			//订单类
			$("#feeInfoImg").hide();
		}
	}else if(changeVal == 1){
		//受益期固定、合同金额不固定
		$("#feeDateTr,#feeAmtTr").show().addClass("collspan");
		if(isOrder == 0){
			//订单类
			$("#feeInfoImg").hide();
		}
	}else if(changeVal == 2){
		//受益期不固定，付款时确认费用
		$("#feeAmtTr").show().addClass("collspan");
		$("#feeInfoImg").hide();
	}else if(changeVal == 3){
		//宣传费
		$("#feeInfoImg").hide();
	}
	if(((curFeeType == '0' || curFeeType == '1' || curFeeType == '2') && changeVal == '3')
			|| ((changeVal == '0' || changeVal == '1' || changeVal == '2') && curFeeType == '3')){

		$("#totalNumTable tr:gt(0)").remove();
		$("#totalNum").val(0);
		$("#tableDiv").empty();
	}
	curFeeType = changeVal;
}

//费用子类型下拉框改变触发
function changeFeeSubType(){
	var changeVal = $("#feeSubType").val();
	$("#houseTr").hide().removeClass("collspan");
	$(".houseValid").removeAttr("valid");
	
	if(changeVal == 0){
		//普通费用类
	}else if(changeVal == 1){
		//房屋租赁类
		$("#houseTr").show().addClass("collspan");
		$(".houseValid").attr("valid","");
	}
}

//付款条件下拉框改变触发
function changePayTerm(){
	var changeVal = $("#payTerm").val();
	$("#onScheduleTr,#onDateTr,#onTermTr").hide().removeClass("collspan");
	$("#stageTypeDiv").hide();
	if(changeVal == 3){
		//分期付款
		$("#stageTypeDiv").css("display","inline");
		$("#stageType").combobox("destroy").val("0").combobox();
		$("#onScheduleTr").show().addClass("collspan");
	}else{
		//其他付款方式
		$("#stageTypeDiv").hide();
	}
}

//付款条件子下拉框改变触发
function changeStageType(){
	var changeVal = $("#stageType").val();
	$("#onScheduleTr,#onDateTr,#onTermTr").hide().removeClass("collspan");
	if(changeVal == 0){
		//按进度
		$("#onScheduleTr").show().addClass("collspan");
	}else if(changeVal == 1){
		//按日期
		$("#onDateTr").show().addClass("collspan");
	}else if(changeVal == 2){
		//按条件
		$("#onTermTr").show().addClass("collspan");
	}
}

//新增审批数量记录
function addLxsl(){
	var projectcr = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/projectcrinfo/list.do?<%=WebConsts.FUNC_ID_KEY %>=011003",
			null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	if(!projectcr){
		projectcr = window.returnValue;
		if(!projectcr){
			return ;
		}
	}
	if(projectcr){
		var newProAbcde = projectcr.abCde;
		var DZSPTrList = $("#DZSPTable tr");
		for(var i=0;i<DZSPTrList.length;i++){
			var DZSPTrAbcde = $(DZSPTrList[i]).find("input[name='abcde']").val();
			if(newProAbcde == DZSPTrAbcde){
				App.notyError("所选择的电子审批已存在！");
				return false;
			}
		}
		var appendTr = "<tr>"+
							"<td>"+projectcr.abCde+"<input type='hidden' name='abcde' value='"+projectcr.abCde+"'></td>"+//缩位码
							"<td>"+projectcr.projCrId+"</td>"+//审批编号
							"<td>"+projectcr.createDate+"</td>"+//日期
							"<td>"+projectcr.projCrNum+"</td>"+//审批数量
							"<td>"+projectcr.exeNum+"</td>"+//已执行数量
							"<td><input type='text' name='abcdeNum' class='base-input-text' style='width:100%' onblur='getAbcdeNum()'></td>"+//数量
							"<td>"+projectcr.projCrAmt+"</td>"+//审批金额
							"<td>"+projectcr.exeAmt+"</td>"+//已执行金额
							"<td><input type='text' class='base-input-text' name='abcdeAmt' style='width:100%' onblur='getAbcdeAmt()'></td>"+//金额
							"<td><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteDZSPTr(this)'/></td>"+//操作
					"</tr>";
		$("#DZSPTable").append(appendTr);
	}
}

//供应商弹出页
function queryProvider(obj,flag){
	var providerCode = $("#providerCode").val();
	var providerType = $("#providerType").val();
	App.submitShowProgress();
	var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&providerCode="+providerCode+"&providerType="+providerType,
			null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	App.submitFinish();
	if(!proObj){//判断谷歌浏览器返回问题
		proObj = window.returnValue;
		if(proObj){
			$(obj).val(proObj.providerName);  //供应商名称
			$(obj).prev().val(proObj.providerCode);	//供应商编号
			$("#providerType").val(proObj.providerType);
			if('provider'==flag){
				$(obj).prev().prev().val(proObj.actNo);	//供应商银行账户
				$(obj).prev().prev().prev().val(proObj.actCurr);	//币种
				$('#providerTaxRate').val(proObj.providerTaxRate);  //增值税率
				$('#providerTaxRateSpan').text(proObj.providerTaxRate);  //增值税率
				if(!$.isBlank($('#cntAmt').val())){
					$('#providerTax').val(proObj.providerTaxRate * $('#cntAmt').val());  //增值税金
				}
				if(proObj.providerType == 'EMPLOYEE'){
					//员工供应商
					$("#srcPoviderTdLeft").show();
					$("#srcPoviderTdRight").show();
					$("#srcPovider").attr("valid","");
				}else{
					$("#srcPoviderTdLeft").hide();
					$("#srcPoviderTdRight").hide();
					$("#srcPovider").removeAttr("valid");
					$("#srcPovider").val("");
				}
			}
		}
	}else{
		$(obj).val(proObj.providerName);  //供应商名称
		$(obj).prev().val(proObj.providerCode);	//供应商编号
		$("#providerType").val(proObj.providerType);
		if('provider'==flag){
			$(obj).prev().prev().val(proObj.actNo);	//供应商银行账户
			$(obj).prev().prev().prev().val(proObj.actCurr);	//币种
			if(proObj.providerType == 'EMPLOYEE'){
				//员工供应商
				$("#srcPoviderTdLeft").show();
				$("#srcPoviderTdRight").show();
				$("#srcPovider").attr("valid","");
			}else{
				$("#srcPoviderTdLeft").hide();
				$("#srcPoviderTdRight").hide();
				$("#srcPovider").removeAttr("valid");
				$("#srcPovider").val("");
			}
		}
	 }
}

//内部供应商弹出页
function querySrcProvider(obj){
	App.submitShowProgress();
	var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProviderAct.do?<%=WebConsts.FUNC_ID_KEY %>=010711&flag=1",
			null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	App.submitFinish();
	if(!proObj){//判断谷歌浏览器返回问题
		proObj = window.returnValue;
		if(proObj){
			$("#srcPovider").val(proObj.providerName);  //内部供应商名称
		}
	}else{
		$("#srcPovider").val(proObj.providerName);  //内部供应商名称
	 }
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
				}
			}
		 );
}

//物料类型添加--跳出页面
function matrTypeOptionPage(obj){
 	var feeDeptObj = $(obj).parent().parent().find("input[name='feeDept']");
 	//var isOrder = $("#isOrderDiv").find(".check-label").next().val();
 	var isOrder = $("#isOrder").val();
	var cntTypeVal = $("#cntType").val();
	//var isProvinceBuyVal = $("#isProvinceBuyDiv").find(".check-label").next().val();
	var isProvinceBuyVal = $("#isProvinceBuy").val();
	var feeTypeVal = $("#feeType").val();
	if($.isBlank($(feeDeptObj).val())){
		App.notyWarning("请先选择对应的费用承担部门！");
		$(feeDeptObj).focus();
		return false;
	}
	if($.isBlank(isProvinceBuyVal)){
		App.notyWarning("请先选择是否省行统购！");
		return false;
	}
	if($.isBlank(isOrder)){
		App.notyWarning("请先选择是否生成订单！");
		return false;
	}
	var url="<%=request.getContextPath()%>/sysmanagement/matrtype/matrTypeOption.do?<%=WebConsts.FUNC_ID_KEY %>=010804&matrCode="+$(obj).prev().val();
	url += "&feeDept="+$(feeDeptObj).val()+"&isProvinceBuy="+isProvinceBuyVal+"&cntType="+cntTypeVal+"&isOrder="+isOrder;
	if(cntTypeVal==1){
		url += "&feeType="+feeTypeVal;
	}
	
	App.submitShowProgress();
	var returnValue = window.showModalDialog(url, null, "dialogHeight=600px;dialogWidth=800px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	App.submitFinish();
	if(!returnValue){
		returnValue = window.returnValue;
		if(!returnValue){
			return;
		}
	}
	if(returnValue != null){
		removeFeeInfoTable();
		$(obj).val(returnValue.matrName);
		$(obj).prev().val(returnValue.matrCode);
		$(obj).prev().prev().val(returnValue.cglCode);
	} 
} 

//新增采购数量
function addTotalNum(){
	var num = $('#totalNum').val();
	var cntType = $("#cntType").val();
	var execPriceTr = '';
	if(cntType == 0){
		//资产类
		execPriceTr = '<td><input type="text" valid name="execPrice" class="base-input-text" style="width:100%" onblur="computTotalNum(this)" onchange="removeFeeInfoTable()"></td>'; //单价(元)
	}else{
		//费用类
		execPriceTr = '<td>1<input type="hidden" name="execPrice" value="1"></td>'; //单价(元)
	}
	var appendTr = '<tr>'+
						'<td><input type="hidden" name="projId"/><input type="text" valid name="projName" onclick="projOptionPage(this)" class="base-input-text" style="width:100%"></td>'+//项目
				   		'<td><input name="feeDept" type="hidden"><input name="feeDeptName" type="text" class="base-input-text"/></td>'+//费用承担部门
				   		'<td><input type="hidden" name="trCglCode" /><input type="hidden" name="matrCode" /><input type="text" name="matrName" valid onclick="matrTypeOptionPage(this)" readonly class="base-input-text" style="width:100%"></td>'+//物料类型
				   		'<td><input type="text" valid name="deviceModelName" maxlength="20" class="base-input-text" style="width:100%"></td>' +//设备型号
				   		'<td>'+
				   			'<div class="ui-widget" style="display:inline" > '+
								'<select name="special" class="selectS"> '+
									 '<forms:codeTable tableName="TB_SPECIAL" selectColumn="SPECIAL_ID,SPECIAL_NAME" 
									 valueColumn="SPECIAL_ID" textColumn="SPECIAL_NAME" orderColumn="SPECIAL_ID"  
									 orderType="ASC" selectedValue="0"/> '+
								'</select> '+
							'</div>&nbsp;'+
				   		'</td>' + //专项
				   		'<td>'+
					   		'<div class="ui-widget" style="display:inline" > '+
								'<select name="reference" class="selectS"> '+
									 '<forms:codeTable tableName="TB_REFERENCE" selectColumn="REFERENCE_ID,REFERENCE_NAME" 
									 valueColumn="REFERENCE_ID" textColumn="REFERENCE_NAME" orderColumn="REFERENCE_ID"  
									 orderType="ASC" selectedValue="0"/> '+
								'</select> '+
							'</div>&nbsp;'+
				   		'</td>'+//参考
				   		'<td><input type="text" valid name="execNum" class="base-input-text" style="width:100%" onblur="computTotalNum(this)" onchange="removeFeeInfoTable()"></td>'+//数量
				   		execPriceTr + //单价
				   		'<td><input type="text" valid name="execAmt" readonly class="base-input-text" style="width:100%" ></td>'+//金额(元)
				   		'<td><input type="text" valid name="warranty" class="base-input-text" style="width:100%"></td>'+//保修期(年)
				   		'<td><input type="text" valid name="productor" maxlength="100" class="base-input-text" style="width:100%"></td>'+//制造商
				   		'<td><img border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteTotalNum(this)"/></td>'+//操作
				   '</tr>';
	$("#totalNumTable").append(appendTr);
 	//$("#totalNum").val(Math.floor($("#totalNum").val()) + 1 );
 	$(".selectS").combobox();
	$("#totalNumTable .ui-autocomplete-input").css("width","60px");
	feeDeptTag.tagInit();
	$("#totalNumTable").find("input[name='feeDeptName']").css("width","100px");
	$("#totalNumTable td").attr("style","padding-left: 3px;padding-right: 7px;");
}

//费用部门发生改变时
function changeFeeDept(){
	$("#totalNumTable tr input[name='feeDept']").each(function(){
		if( $(this).val() != $(this).next().attr("title") ){
			$(this).parent().parent().find("input[name='matrCode']").val("");
			$(this).parent().parent().find("input[name='matrName']").val("");
			$(this).parent().parent().find("input[name='cglCode']").val("");
			removeFeeInfoTable();
		}
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
	var amt = $(num).val() * $(price).val();
	$(obj).parent().parent().find("input[name='execAmt']").val(amt);
	computCntTotalNum();
}

//计算合同采购数量
function computCntTotalNum(){
	var trList = $("#totalNumTable tr:gt(0)");
	var totalNum = 0;
	for(var i=0; i<trList.length; i++){
		totalNum += Number($(trList[i]).find("input[name='execNum']").val());
	}
	$("#totalNum").val(totalNum);
}

//计算房租单价   元/月*平方米(元/月*项)
function computeUnitPrice(){
	var area = $("#area").val();
	var rent = $("#rent").val();
	if(!$.checkMoney(area)){
		$("#area").val(0);
	}else if(!$.checkMoney(rent)){
		$("#rent").val(0);
	}else{
		$("#unitPrice").val((rent / area).toFixed(2));
	}
}

var TenancyDzCnt = 0;
//新增房租递增记录
function addTenancyDz(){
	var appendTr = '<tr>'+
						'<td> <span name="tdSpan" style="display:none">'+TenancyDzCnt+'</span>'+
							'租金从&nbsp;<input type="text" name="fromDate" class="base-input-text fromDate" onfocus="getStartDate(this)" readonly style="width:75px">&nbsp; '+
							'至&nbsp;<input type="text" name="toDate" class="base-input-text toDate" onchange="checkTenancyDzToDate(this)" style="width:75px">&nbsp; '+
							'<div class="ui-widget" style="display:inline"> '+
								'<select name="dzlx" class="selectD" onchange="changeTenancyDzLx(this)"> '+
									 '<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE"  
									 conditionStr="CATEGORY_ID = 'TENANCY_DZLX'" orderType="ASC"/> '+
								'</select> '+
							'</div>&nbsp; '+
							'<input type="text" name="dzed" class="base-input-text" style="width:60px">&nbsp; '+
							'<div class="ui-widget" style="display:inline" > '+
								'<select name="dzdw" class="selectD"> '+
									 '<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE"  
									 conditionStr="CATEGORY_ID = 'TENANCY_DZDW'" orderType="ASC"/> '+
								'</select> '+
							'</div>&nbsp; '+
							'管理费及其他： '+
							'<input type="text" name="glfy" class="base-input-text" style="width:130px"/>&nbsp;元/月 '+
							'&nbsp;&nbsp;&nbsp; '+
							'<img border="0" alt="删除"  width="30px" height="30px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteTenancyDzTr(this)" style="vertical-align: middle;"/>'+ 
						'</td> '+
					'</tr>';
	$("#TenancyDzTable").append(appendTr);
	TenancyDzCnt++;
	$(".selectD").combobox();
	$("#TenancyDzTable .ui-autocomplete-input").css("width","35px");
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

//获取房租递增的起始日期
function getStartDate(obj){
	var beginDate = $("#beginDate").val();
	var tenancyDzTrList = $("#TenancyDzTable tr");
	var index = $(obj).parent().find("span[name='tdSpan']").html();
	if(index == 0){
		if(beginDate != ""){
			$(obj).val(beginDate);
		}
	}else{
		var prevToDate = $(tenancyDzTrList[parseInt(index)-1]).find("input[name='toDate']").val();
		if(prevToDate != ""){
			tempDate = addday(prevToDate,1);
			$(obj).val(tempDate);
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
	if(index == 0 && index<TenancyDzTrList.length){
		if(addday(toDatei,1).substring(8,10) != '01' && addday(toDatei,1).substring(8,10) != fromDatei.substring(8,10)){
			App.notyError('递增结束日期不符合规则,请重新选择!');
			$(obj).val("");
			return false;
		}
	}
	//判断第二条及以后递增条件输入规则月初规则(所有开始日期为01),月中规则(开始日期相同,例如都为15号)
	if(index>0){
		var i = index-1;
		var preFromDate = $(TenancyDzTrList[i]).find("input[name='fromDate']").val();
		var preToDate = $(TenancyDzTrList[i]).find("input[name='toDate']").val();
		if(preToDate==''){
			App.notyError('请先选择上一条递增条件结束日期!');
			$(obj).val("");
			return false;
		}else if(preFromDate == ''){
			App.notyError('请先选择上一条递增条件开始日期!');
			$(obj).val("");
			return false;
		}
		if(addday(preToDate,1).substring(8,10)!='01'&&addday(preToDate,1).substring(8,10)!=preFromDate.substring(8,10)){
			App.notyError('上一条递增条件结束日期不符合规则,请重新选择!');
			$(obj).val("");
			return false;
		}
		if(addday(preToDate,1)!=fromDatei){
			App.notyError('递增开始日期不符合规则,请重新选择!');
			$(obj).val("");
			return false;
		}
		else if(preFromDate.substring(8,10)!=fromDatei.substring(8,10)&&fromDatei.substring(8,10)!='01'){
			App.notyError('递增开始日期不符合规则,请重新选择!');
			$(obj).val("");
			return false;
		}
		if(index < TenancyDzTrList.length-1){
			if(addday(toDatei,1).substring(8,10)!=fromDatei.substring(8,10)){
				App.notyError('递增结束日期不符合规则,请重新选择!');
				$(obj).val("");
				return false;
			}
		}
		else{
			if(addday(toDatei,1).substring(8,10)!=fromDatei.substring(8,10) && endDate!=toDatei){
				App.notyError('递增结束日期不符合规则,请重新选择!');
				$(obj).val("");
				return false;
			}
		}
	}
	if(""!=toDatei){
		if(fromDatei>=toDatei){
			App.notyError('递增结束日期必须大于开始日期!');
			$(obj).val("");
			return false;
		}
		return true;
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

var scheduleTrCnt = 1; //初始的按进度付款记录期数为0
//按进度新增付款记录
function addOnScheduleTr(){
	scheduleTrCnt++;
	var appendTr = "<tr>"+
						"<td>第<span name='sceduleTrCnt'>"+scheduleTrCnt+"</span>期&nbsp;&nbsp;付款年月&nbsp;"+
						"<input type='text' name='jdDate' class='base-input-text jdDate'/>&nbsp;"+
						"<input type='text' name='jdtj' class='base-input-text' style='width:60px'>&nbsp;%支付&nbsp;"+
						"<input type='text' class='base-input-text' name='jdzf' style='width:60px'>&nbsp;元</td>"+
						"<td><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteScheduleTr(this)'/></td>"+
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
						"<td><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteTr(this)'/></td>"+
				"</tr>";
	$("#onDateTable").append(appendTr);
}

//删除一行数据
function deleteTr(obj){
	$(obj).parent().parent().remove();
}

//删除一行房租递增记录
function deleteTenancyDzTr(obj){
	TenancyDzCnt--;
	$(obj).parent().parent().remove();
	var trList = $("#TenancyDzTable tr");
	for(var i=0;i<trList.length;i++){
		$(trList[i]).find("span[name='tdSpan']").html(i+1);
	}
}

//删除一行进度新增记录
function deleteScheduleTr(obj){
	scheduleTrCnt--;
	$(obj).parent().parent().remove();
	var trList = $("#onScheduleTable tr");
	for(var i=0;i<trList.length;i++){
		$(trList[i]).find("span[name='sceduleTrCnt']").html(i+1);
	}
}

//删除一行电子审批记录
function deleteDZSPTr(obj){
	$(obj).parent().parent().remove();
	getAbcdeNum();
	getAbcdeAmt();
}

//删除一行采购数量记录
function deleteTotalNum(obj){
	$(obj).parent().parent().remove();
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
			var jdtjObj = $(trList[i]).find("input[name='jdtj']");
			var jdzfObj = $(trList[i]).find("input[name='jdzf']");
			if(!$.checkMoney($(jdzfObj).val())){
				App.notyError("第【"+(i+1)+"】条按进度分期付款的金额输入值不合法，请重新输入！");
				return false;
			}else if(!$.checkRegExp($(jdtjObj).val(),reg)){
				App.notyError("第【"+(i+1)+"】条按进度分期付款的百分比输入值不合法，请重新输入！");
				return false;
			}
			totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat($(jdzfObj).val().replace(/\,/g,''),3),2);
			totalJd=$.numberFormatAdd($.numberFormat(totalJd,3),$.numberFormat($(jdtjObj).val().replace(/\,/g,''),3),2);
		}
		if(totalJd!=100)
		{
			App.notyError('分期付款进度之和必须为100%!');
			return false;
		}
		if(totalAmt*1 != $("#cntAmt").val().replace(/\,/g,'')*1)
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
				App.notyError("第【"+(i+1)+"】条按日期分期付款的天数输入值不合法，请重新输入！");
				return false;
			}
			totalAmt=$.numberFormatAdd($.numberFormat(totalAmt,3),$.numberFormat($(rqzfObj).val().replace(/\,/g,''),3),2);
		}
		if(totalAmt*1!=$("#cntAmt").val().replace(/\,/g,'')*1)
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
		if(totalAmt.replace(/\,/g,'')*1 != $("#cntAmt").val().replace(/\,/g,'')*1)
		{
			App.notyError('分期付款金额之和必须与合同金额相等!');
			return false;
		}
	}
	return true; 
}
var provinceBuy = "";
//改变是否省行统购
function changeProvinceBuy(){
	var changeVal = $("#isProvinceBuy").val();
	if(changeVal != provinceBuy){
		$("#totalNumTable tr:gt(0)").remove();
		$("#totalNum").val(0);
		$("#tableDiv").empty();
		provinceBuy = changeVal;
	}
}

var order = "";
//改变是否生成订单
function changeIsOrder(){
	var changeVal = $("#isOrder").val();
	if(changeVal != order){
		$("#totalNumTable tr:gt(0)").remove();
		$("#totalNum").val(0);
		$("#tableDiv").empty();
		order = changeVal;
		changeFeeType();
	}
}

function scan(){
	window.showModalDialog("<%=request.getContextPath()%>/common/contract/scan/preadd.do?id=${selectInfo.cntNum }",
			'中行影像前端控件', "dialogHeight=600px;dialogWidth=1200px;center=yes;status=no;");
}

//查看是否扫描过合同
function hasScaned(){
	var url = "contract/initiate/hasScaned.do?<%=WebConsts.FUNC_ID_KEY%>=0302010104";
	var data = {};
	data["cntNum"] = '${selectInfo.cntNum }';
	var returnValue;
	App.ajaxSubmit(url, {data:data,async : false},  
   		function(data){
			var hasScaned = data.hasScaned;
			returnValue = hasScaned;
		});
	return returnValue;
}
</script>

</head>
<body >
<p:authFunc funcArray="03020104"/>
<form action="<%=request.getContextPath()%>/sysmanagement/projectMgr/add.do" method="post" id="addForm">
<p:token/>
<table>
	<tr>
		<td>
			<table class="tableList">
				<tr class="collspan-control">
					<th colspan="4">基础信息
						（合同编号：<c:out value="${selectInfo.cntNum }"></c:out>）
						<input type="hidden" value="${selectInfo.cntNum }" name="cntNum" >
					</th>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">集采编号</td>
					<td class="tdRight"><input type="text" name="stockNum" id="stockNum" maxlength="100" class="base-input-text"/></td>
					<td class="tdLeft">评审编号</td>
					<td class="tdRight"><input type="text" name="psbh" id="psbh" maxlength="400" class="base-input-text"/></td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">审批类别</td>
					<td class="tdRight">
						<div class="ui-widget">
							<select id="lxlx" name="lxlx" class="selectC" onchange="changLxlx()">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_APPROVE_TYPE'"
								 orderType="DESC" selectedValue="${selectInfo.lxlx }" />
							</select>
						</div>
					</td>
					<td class="tdLeft"> 签报文号<span id="qbhRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="qbh" id="qbh" maxlength="40" class="base-input-text"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">合同类型</td>
					<td class="tdRight">
						<div class="ui-widget">
							<select id="cntType" name="cntType" class="selectC" onchange="changeCntType()">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
								 orderType="ASC" selectedValue="${selectInfo.cntType }" />
							</select>
						</div>
					</td>
					<td class="tdLeft"> 审批金额<span id="lxjeRedSpan" class="red">*</span></td>
					<td class="tdRight"><input type="text" name="lxje" value="0" id="lxje" class="base-input-text"/></td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">
						审批数量<span id="lxslRedSpan" class="red">*</span>
						<img id="addlxslImg" style="display:none" border="0" vaild width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addLxsl()'>
					</td>
					<td class="tdRight" colspan="3">
						<input type="text" id="lxsl" name="lxsl" maxlength="10" value="0"   class="base-input-text">
					</td>
				</tr>
				<tr id="DZSPTr" style="display:none">
					<td colspan="4">
						<table id="DZSPTable" >
							<tr>
								<th width='10%'>缩位码</th>
								<th width='15%'>审批编号</th>
								<th width='10%'>日期</th>
								<th width='10%'>审批数量</th>
								<th width='10%'>已执行数量</th>
								<th width='10%'>数量</th>
								<th width='10%'>审批金额</th>
								<th width='10%'>已执行金额</th>
								<th width='10%'>金额</th>
								<th width='5%'>操作</th>
							</tr>
						</table>
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
			<table class="tableList">
				<tr class="collspan-control">
					<th colspan="4">合同信息</th>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">供应商<span id="providerCodeRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="hidden" name="providerType" id="providerType">
						<input type="hidden" name="provActCurr" id="provActCurr">
						<input type="hidden" name="provActNo" id="provActNo">
						<input type="hidden" name="providerCode" id="providerCode" />
						<input type="text" name="providerName" id="providerName" class="base-input-text" valid readonly onclick="queryProvider(this,'provider')"/>
						<%-- <img border="0" alt="查找" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/search.jpg"  onclick="queryProvider(this)" style="vertical-align: middle;"> --%>
					</td>
					<td class="tdLeft" id="srcPoviderTdLeft" style="display:none">内部供应商<span class="red">*</span></td>
					<td class="tdRight" id="srcPoviderTdRight" style="display:none">
						<input type="text" name="srcPoviderName" id="srcPovider" class="base-input-text"  readonly="readonly"/>
						<img border="0" alt="查找" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/search.jpg" onclick="querySrcProvider(this)" style="vertical-align: middle;margin-left: 20px">
					</td>
				</tr>
				<tr class="collspan"> 
					<td class="tdLeft">签订日期<span id="providerCodeRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="signDate" id="signDate" valid class="base-input-text" readonly/>
					</td>
					<td class="tdLeft">省行统购项目</td>
					<td class="tdRight">
						<div class="ui-widget" style="display:inline">
							<select id="isProvinceBuy" name="isProvinceBuy" class="selectC" onchange="changeProvinceBuy()" valid errorMsg="请选择是否省行统购">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_IS_YN'"
								 orderType="ASC" selectedValue="${selectInfo.isProvinceBuy }"/>
							</select>
						</div>
						<!-- <div class="base-input-radio" id="isProvinceBuyDiv">
							<label for="isProvinceBuyYes" onclick="changeProvinceBuy(this)" class="check-label">是</label>
							<input type="radio" id="isProvinceBuyYes" name="isProvinceBuy" value="0" checked="checked">
							<label for="isProvinceBuyNo" onclick="changeProvinceBuy(this)">否</label>
							<input type="radio" id="isProvinceBuyNo" name="isProvinceBuy" value="1">
						</div>  -->
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">合同金额<span id="cntAmtRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="cntAmt" id="cntAmt" valid class="base-input-text" 
						onblur="javascript:if(!$.isBlank($('#providerTaxRate').val())){
							$('#providerTax').val($('#providerTaxRate').val() * $('#cntAmt').val());  }"/>
					</td>
					<td class="tdLeft">其中合同质保金(%)<span id="zbAmtRedSpan" class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="zbAmt" valid id="zbAmt" class="base-input-text"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">增值税率</td>
					<td class="tdRight">
						<sapn id="providerTaxRateSpan"></sapn>
						<input type="hidden" id="providerTaxRate" name="providerTaxRate" />
					</td>
					<td class="tdLeft">增值税金<span class="red">*</span></td>
					<td class="tdRight">
						<input type="text" name="providerTax" valid id="providerTax" class="base-input-text"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">付款条件</td>
					<td class="tdRight" colspan="3">
						<div>
							<div class="ui-widget" style="display:inline">
								<select id="payTerm" name="payTerm" class="selectC" onchange="changePayTerm()">
									<option value="">--请选择--</option>						
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
									 conditionStr="CATEGORY_ID = 'CNT_PAY_TERM'"
									 orderType="ASC" selectedValue="${selectInfo.payTerm }"/>
								</select>
							</div>
							<div class="ui-widget" id="stageTypeDiv" style="display:none">
								<select id="stageType" name="stageType" onchange="changeStageType()" class="selectC">
									<option value="">--请选择--</option>						
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
									 conditionStr="CATEGORY_ID = 'CNT_STAGE_TYPE'"
									 orderType="ASC" />
								</select>
							</div>
						</div>
					</td>		
				</tr>
				<tr id="onScheduleTr" style="display:none">
					<td class="tdLeft">按进度分期付款<img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addOnScheduleTr()'></td>
					<td class="tdRight" colspan="3">
						<table id="onScheduleTable">
							<tr>
								<td>第1期&nbsp;&nbsp;付款年月
									<input type='text' name='jdDate' class='base-input-text jdDate'/>
									<input type='text' name='jdtj' class='base-input-text' style='width:60px'>&nbsp;%支付
									<input type='text' name='jdzf' class='base-input-text' style='width:60px'>&nbsp;元</td>
								<td><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteScheduleTr(this)'/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="onDateTr" style="display:none">
					<td class="tdLeft">按日期分期付款<img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addOnDateTr()'></td>
					<td class="tdRight" colspan="3">
						<table id="onDateTable">
							<tr>
								<td>合同签订后<input type='text' name='rqtj' class='base-input-text' style='width:60px'>&nbsp;天&nbsp;&nbsp;支付
									<input type='text' name='rqzf' class='base-input-text' style='width:60px'>&nbsp;元</td>
								<td><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteScheduleTr(this)'/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="onTermTr" style="display:none">
					<td class="tdLeft">按条件分期付款</td>
					<td class="tdRight" colspan="3">
						到货支付&nbsp;&nbsp;<input type="text" class="base-input-text" id="dhzf" name="dhzf" value="0.00">&nbsp;&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;
						验收支付&nbsp;&nbsp;<input type="text" class="base-input-text" id="yszf" name="yszf" value="0.00">&nbsp;&nbsp;元<br/>
						结算支付&nbsp;&nbsp;<input type="text" class="base-input-text" id="jszf" name="jszf" value="0.00">&nbsp;&nbsp;元
					</td>
				</tr>
				<tr id="otherTable1" class="collspan">
					<td class="tdLeft">采购数量<span class="red">*</span><img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick="addTotalNum()"></td>
					<td class="tdRight" >
						<input type="text" name="totalNum" readonly id="totalNum" value="0" valid class="base-input-text"/>
					</td>
					<td class="tdLeft">是否生成订单</td>
					<td class="tdRight">
						<div class="ui-widget" style="display:inline">
							<select id="isOrder" name="isOrder" class="selectC" onchange="changeIsOrder()" valid errorMsg="请选择是否生成订单">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_IS_YN'"
								 orderType="ASC" selectedValue="${selectInfo.isOrder }"/>
							</select>
						</div>
						<!-- <div class="base-input-radio" id="isOrderDiv">
							<label for="isOrderYes"onclick="changeIsOrder(this)" class="check-label">是</label>
							<input type="radio" id="isOrderYes" name="isOrder" value="0" checked="checked">
							<label for="isOrderNo" onclick="changeIsOrder(this)">否</label>
							<input type="radio" id="isOrderNo" name="isOrder" value="1">
						</div>  -->
					</td>
				</tr>
				<tr class="collspan">
					<td colspan="4">
						<table id="totalNumTable">
							<tr>
								<th width="9%">项目</th>
								<th width="11%">费用承担部门</th>
								<th width="8%">物料类型</th>
								<th width="8%">设备型号</th>
								<th width="12%">专项</th>
								<th width="12%">参考</th>
								<th width="7%">数量</th>
								<th width="7%">单价<br>(元)</th>
								<th width="7%">金额<br>(元)</th>
								<th width="8%">保修期<br>(年)</th>
								<th width="7%">制造商</th>
								<th width="4%">操作</th>
							</tr>
							<tr>
								<td><input type="hidden" name="projId"/><input type="text" valid name="projName" onclick="projOptionPage(this)" class="base-input-text" style="width:100%"></td>
								<td><input name="feeDept" type="hidden"><input name="feeDeptName" type="text" class="base-input-text"/></td>
								<td><input type="hidden" name="trCglCode" /><input type="hidden" name="matrCode" /><input type="text" name="matrName" valid onclick="matrTypeOptionPage(this)" readonly class="base-input-text" style="width:100%"></td>
								<td><input type="text" valid name="deviceModelName" maxlength="20" class="base-input-text" style="width:100%"/></td>
								<td>
									<div class="ui-widget" style="display:inline" >
										<select name="special" class="selectS">
										 <forms:codeTable tableName="TB_SPECIAL" selectColumn="SPECIAL_ID,SPECIAL_NAME" 
										 valueColumn="SPECIAL_ID" textColumn="SPECIAL_NAME" orderColumn="SPECIAL_ID"  
										 orderType="ASC" selectedValue="0"/> 
										</select> 
									</div>
								</td>
								<td>
									<div class="ui-widget" style="display:inline" > 
										<select name="reference" class="selectS">
											 <forms:codeTable tableName="TB_REFERENCE" selectColumn="REFERENCE_ID,REFERENCE_NAME" 
											 valueColumn="REFERENCE_ID" textColumn="REFERENCE_NAME" orderColumn="REFERENCE_ID"  
											 orderType="ASC" selectedValue="0"/>
										</select> 
									</div>
								</td>
								<td><input type="text" valid name="execNum" class="base-input-text" style="width:100%" onblur="computTotalNum(this)" onchange="removeFeeInfoTable()"></td>
								<td><input type="text" valid name="execAmt" readonly class="base-input-text" style="width:100%" ></td>
								<td id="execPriceTd">
									<!-- <input type="text" valid name="execPrice" class="base-input-text" style="width:100%" onblur="computTotalNum(this)" onchange="removeFeeInfoTable()">
									1<input type="hidden" name="execPrice" value="1"> -->
								</td>
								<td><input type="text" valid name="warranty" class="base-input-text" style="width:100%"></td>
								<td><input type="text" valid name="productor" maxlength="100" class="base-input-text" style="width:100%"></td>
								<td><img border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteTotalNum(this)"/></td>
							</tr>
						</table>
						<forms:OrgSelPlugin  triggerEle="#totalNumTable tr feeDeptName::name" rootNodeId="${rootFeeDept}" 
							initValue="${defaultFeeDept }" parentCheckFlag="false" dialogFlag="true" jsVarName="feeDeptTag" changeFun="changeFeeDept"/>
					</td>
				</tr>
				<tr class="collspan">
					<td class="tdLeft">备注(<span id="authmemoSpan">0/2000</span>)</td>
					<td class="tdRight" colspan="3">
						<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,2000,'authmemoSpan')" id="memo" name="memo"></textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr id="tr2" style="display:none">
		<td></td>
	</tr>
	<tr id="tr3" style="display:none">
		<td>
			<table id="feeInfo" style="display:none" class="tableList">
				<tr class="collspan-control">
					<th colspan="4">费用信息</th> 
				</tr>
				<tr class="collspan">
					<td class="tdLeft">费用类型
						<img border='0' id='feeInfoImg' alt='费用信息' width='25px' src='<%=request.getContextPath()%>/common/images/search.jpg' onclick='feeTypePage()' align="center"/>
					</td>
					<td class="tdRight">
						<div class="ui-widget">
							<select id="feeType" name="feeType" class="selectC" onchange="changeFeeType()">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_TYPE'"
								 orderType="ASC" selectedValue="${selectInfo.feeType }"/>
							</select>
						</div>
					</td>
					<td class="tdLeft" id="feeSubTypeTdLeft"> 费用子类型</td>
					<td class="tdRight" id="feeSubTypeTdRight">
						<div class="ui-widget">
							<select id="feeSubType" name="feeSubType" class="selectC" onchange="changeFeeSubType()">
								<option value="">--请选择--</option>						
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_SUB_TYPE'"
								 orderType="ASC" selectedValue="${selectInfo.feeSubType }"/>
							</select>
						</div>
					</td>
				</tr>
				<tr id="feeDateTr" class="collspan">
					<td class="tdLeft">合同受益起始日期<span id="feeStartDateRedSpan" class="red">*</span></td>
					<td class="tdRight"><input type="text" name="feeStartDate" id="feeStartDate" class="base-input-text" readonly onchange="removeFeeInfoTable()"/></td>
					<td class="tdLeft"> 合同受益终止日期<span id="feeEndDateRedSpan" class="red">*</span></td>
					<td class="tdRight"><input type="text" name="feeEndDate" id="feeEndDate" class="base-input-text" readonly onchange="removeFeeInfoTable()"/></td>
				</tr>
				<tr id="feeAmtTr">
					<td class="tdLeft">合同金额（人民币）确定部分<span id="feeAmtRedSpan" class="red">*</span></td>
					<td class="tdRight"><input type="text" name="feeAmt" id="feeAmt" class="base-input-text"/></td>
					<td class="tdLeft">合同金额（人民币）约定罚金</td>
					<td class="tdRight"><input type="text" name="feePenalty" id="feePenalty" class="base-input-text"/></td>
				</tr>
				<tr id="houseTr" style="display:none">
					<td colspan="4">
						<table id="houseTable" >
							<tr>
								<td class="tdLeft" >甲方名称<span class="red">*</span></td>
								<td class="tdRight">
									<input type="hidden" name="jfId" id="jfId">
									<input type="text" name="jf" readonly="readonly" class="base-input-text houseValid" id="jf"  onclick="queryProvider(this,'jf')">
								</td>
								<td class="tdLeft" >乙方名称<span class="red">*</span></td>
								<td class="tdRight">
									<forms:OrgSelPlugin suffix="c" jsVarGetValue="yfId" rootNodeId="${selectInfo.org1Code }" triggerEle="#houseTable yf::name"/>
									<input id="yf" name="yf" type="text" class="base-input-text"/>
								</td>		
							</tr>
							<tr>
								<td class="tdLeft" >地址(说明)<span class="red">*</span></td>
								<td class="tdRight" colspan="3">
									<input type="text" name="wydz" size="100" class="base-input-text houseValid">
								</td>
							</tr>
							<tr>
								<td class="tdLeft" >管理部门<span class="red">*</span></td>
								<td class="tdRight">
									<forms:OrgSelPlugin suffix="a" jsVarGetValue="glbmId" rootNodeId="${selectInfo.org1Code }" triggerEle="#houseTable glbm::name"/>
									<input id="glbm" name="glbm" type="text" class="base-input-text"/>
								</td>	
							    <td class="tdLeft" >网点机构<span class="red">*</span></td>
								<td class="tdRight">
									<forms:OrgSelPlugin suffix="b" jsVarGetValue="wdjgId" rootNodeId="${selectInfo.org1Code }" triggerEle="#houseTable wdjg::name"/>
									<input id="wdjg" name="wdjg" type="text" class="base-input-text"/>
								</td>
							</tr>
							<tr>
								<td class="tdLeft" >自助银行名称</td>
								<td class="tdRight" colspan="3">
									<input type="text" name="autoBankName" class="base-input-text">
								</td>
							</tr>
							<tr>
								<td class="tdLeft" >租赁面积(数量)<span class="red">*</span></td>
								<td class="tdRight">
									<input type="text" name="area" id="area" class="base-input-text houseValid" onchange="computeUnitPrice()"><br>平方米(项)
								</td>
								<td class="tdLeft" >月租金</td>
								<td class="tdRight">
									<input type="text" name="rent" id="rent" class="base-input-text houseValid" onchange="computeUnitPrice()"><br>元/月
								</td>		
							</tr>
							<tr>
								<td class="tdLeft" >单价</td>
								<td class="tdRight">
									<input type="text" class="base-input-text" name="unitPrice" id="unitPrice" readonly><br>元/月*平方米(元/月*项)
								</td>	
								<td class="tdLeft" >房产(项目)性质<span class="red">*</span></td>
								<td class="tdRight" >
									<div class="ui-widget">
										<select id="houseKindId" name="houseKindId" class="selectC">
											<option value="">--请选择--</option>						
											<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
											 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
											 conditionStr="CATEGORY_ID = 'HOUSE_KIND'"
											 orderType="ASC" selectedValue="0"/>
										</select>
									</div>
								</td>	
							</tr>
							<tr>
								<td class="tdLeft" >管理费及其他</td>
								<td class="tdRight">
									<input type="text" name="wyglf" id="wyglf" class="base-input-text"><br>元/月
								</td>
								<td class="tdLeft" >押金</td>
								<td class="tdRight" >
									<input type="text" name="yj" id="yj" class="base-input-text"><br>元
								</td>
							</tr>
							<tr>
								<td class="tdLeft" >执行开始日期<span class="red">*</span></td>
								<td class="tdRight">
									<input id='beginDate' name='beginDate' class='base-input-text houseValid' reaonly> 
								</td>		
							    <td class="tdLeft" >执行结束日期<span class="red">*</span></td>
								<td class="tdRight" >
									<input id='endDate' name='endDate' class='base-input-text houseValid' reaonly>
								</td>			
							</tr>
							<tr>  
								<td class="tdLeft">租金递增条件<img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick="addTenancyDz()">
									<font color="#FF3333"><br>(*时间格式为yyyy-mm-dd)</font></td>
								<td class="tdRight" colspan="3">
									<table id="TenancyDzTable">
									</table>
								</td>
							</tr>
							<tr>
								<td class="tdLeft" >备注</td>
								<td class="tdRight" colspan="3">
									<textarea class="base-textArea" id="remark" name="remark"></textarea>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="扫描" onclick="scan()"/>
			<p:button funcId="03020104" value="新增"/>
			<input type="button" value="重置" onclick="resetAll()">
		</td>
	</tr>
	<div id="tableDiv" style="display: none;"></div>
</table>
</form>
</body>
</html>