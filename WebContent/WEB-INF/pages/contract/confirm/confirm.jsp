<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同确认</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
function hsm(subId,amt,strBuf,cntTrAmt,deductFlag,cntAmt){ 
    this.subId=subId; 
    this.amt=amt; 
    this.strBuf=strBuf;
    this.cntTrAmt = cntTrAmt;
    this.deductFlag = deductFlag;
    this.cntAmt = cntAmt;
}
function param(startDate,endDate,paramList){ 
    this.startDate = startDate;
    this.endDate = endDate;
    this.paramList = paramList; 
}

function matrStr(strBuf, execRatio, cntTrRatio, deductFlag, maxSid){
	this.strBuf = strBuf;
	this.execRatio = execRatio;
	this.cntTrRatio = cntTrRatio;
	this.deductFlag = deductFlag;
	this.maxSid = maxSid;
}

var matrStrList = new Array();
function getMatrStrList(){
	var cntAmt = '${cnt.cntAmt}';
	var totalNumTrList = $("#totalNumTable tr");
	var res = [];
	for(var i=1;i<totalNumTrList.length;i++){
		var flag = false;
    	var matrCode = $(totalNumTrList[i]).find("input[name='matrCode']").val();
    	var signalHsmAmt = $(totalNumTrList[i]).find("input[name='hsmAmt']").val();
    	var signalCntTrAmt = $(totalNumTrList[i]).find("input[name='cntTrAmt']").val();
    	var sid = $(totalNumTrList[i]).find("input[name='sid']").val();//序号
    	if(i==1) {
			res.push([matrCode,signalHsmAmt,signalCntTrAmt,sid]);
			flag = true;
		}else{
			for(var j=0;j<res.length;j++){
				if(matrCode == res[j][0]) {
					res[j][1]=Number(signalHsmAmt)+Number(res[j][1]);
					res[j][2]=Number(signalCntTrAmt)+Number(res[j][2]);
					res[j][3]=sid;//更新保留最大序号
					flag = true;
					break;
				}
			}
		}
    	if (!flag) {
    		res.push([matrCode,signalHsmAmt,signalCntTrAmt,sid]);
    	}
    }
	
    for(var i=1;i<totalNumTrList.length;i++){
    	var cglCode = $(totalNumTrList[i]).find("input[name='cglCode']").val();
    	var matrCode = $(totalNumTrList[i]).find("input[name='matrCode']").val();
    	var matrName = $(totalNumTrList[i]).find("input[name='matrName']").val();
    	var feeDeptName = $(totalNumTrList[i]).find("input[name='feeDeptName']").val();
    	var specialName = $(totalNumTrList[i]).find("input[name='specialName']").val();
    	var referenceName = $(totalNumTrList[i]).find("input[name='specialName']").val();
    	var signalHsmAmt = $(totalNumTrList[i]).find("input[name='hsmAmt']").val();
    	var signalCntTrAmt = $(totalNumTrList[i]).find("input[name='cntTrAmt']").val();
    	var deductFlag = $(totalNumTrList[i]).find("input[name='deductFlag']").val();
    	//strBuf = feeDeptName+ 物料编码+物料名称+核算码+specialName + referenceName; 下划线分隔
    	var strBuf = feeDeptName + "_" + matrCode + "_" + matrName + "_" +cglCode + "_" + specialName + "_" + referenceName;
    	var execRatio = 0, cntTrRatio = 0, maxSid = 0;
    	for (var j = 0; j<res.length; j++) {
    		if (matrCode == res[j][0]) {
    			execRatio = changeTwoDecimal(signalHsmAmt / Number(res[j][1]));
    			cntTrRatio = changeTwoDecimal(signalCntTrAmt / Number(res[j][2]));
    			maxSid = res[j][3];
    		}
    	}
    	var matrStrItem = new matrStr(strBuf, execRatio, cntTrRatio, deductFlag, maxSid);
    	matrStrList[i-1] = matrStrItem;
    }
}
//获取费用类型的弹出页
function feeTypePage(){
	var url="<%=request.getContextPath()%>/contract/confirm/feeTypePage.do?<%=WebConsts.FUNC_ID_KEY %>=0302010305";
	var data = {};
	
	if($.isBlank($("#tableDiv").html())){
		var tenancyList = '${cnt.tenanciesList}';
		if(tenancyList.length != 0){
			data['tenancyData'] = getyTenancy();
		}else{
			var feeStartDate = '${cnt.feeStartDate}';
		    var feeEndDate = '${cnt.feeEndDate}';
		    var feeType = '${cnt.feeType}';
		    var paramList = new Array();
		    var totalNumTrList = $("#totalNumTable tr");
		    var cntAmt = '${cnt.cntAmt}';//合同不含税金额
		    var taxAmt = parseFloat('${cnt.cntTaxAmt}');//合同总税额
		    var cntAllAmt = parseFloat('${cnt.cntAllAmt}');//合同总金额
		    var feeAmt = parseFloat('${cnt.feeAmt}');//合同金额确认部分
		    var feeAmtTmp = parseFloat('${cnt.feeAmt}');//剩余的合同金额确认部分
	    	var sumTemp = 0;
	    	var cAmt = 0;
	    	
	    	//将合同确定部分中不含税部分与税额分配到各个物料上，然后传到受益金额录入页面，罚金的分配在受益金额录入页面进行处理
		    for(var i=1;i<totalNumTrList.length;i++){
		    	var hsmAmt = 0;
		    	var sid = $(totalNumTrList[i]).find("input[name='sid']").val();//序号
		    	var taxNamt = $(totalNumTrList[i]).find("input[name='taxNamt']").val();
		    	var cntTrAmt = parseFloat($(totalNumTrList[i]).find("input[name='cntTrAmt']").val());//物料行上的税额
		    	var deductFlag = $(totalNumTrList[i]).find("input[name='deductFlag']").val();//是否可抵扣
		    	if(feeType == '1'){ // 含有约定罚金的物料金额处理
		    		cAmt = $(totalNumTrList[i]).find("input[name='hsmAmt']").val();//物料行上的不含税金额
		    		
		    		if (deductFlag == 'Y') {
		    			//如果是可抵扣时计算出合同确定部分中除去分配到当前物料的税的部分
		    			//剩余的合同金额确认部分-合同确认部分*物料的税额/合同总金额
						feeAmtTmp = changeTwoDecimal(feeAmtTmp - feeAmt*cntTrAmt/cntAllAmt);
					}
		    		if(i == totalNumTrList.length-1){
		    			//合同确认部分分配到最后一项物料的金额由剩余的合同确认部分减去前面合同确认部分已分配到物料上的之和
						hsmAmt = changeTwoDecimal(parseFloat(feeAmtTmp)-parseFloat(sumTemp));
		    		}else{
		    			var signalHsmAmt = parseFloat($(totalNumTrList[i]).find("input[name='hsmAmt']").val()); //物料不含税金额
		    			//将合同金额确定部分中不含税的部分按比例查分到各个物料
		    			//合同确认部分*物料不含税金额/合同总金额
			    		hsmAmt = changeTwoDecimal(feeAmt*signalHsmAmt/cntAllAmt); 
			    		if (deductFlag == 'N') {
			    			//如果是不可抵扣，则加上税的部分
			    			hsmAmt = changeTwoDecimal(parseFloat(hsmAmt) + feeAmt*cntTrAmt/cntAllAmt);
			    		}
			    		sumTemp += parseFloat(hsmAmt);
		    		}
		    	}else{
		    		hsmAmt = $(totalNumTrList[i]).find("input[name='hsmAmt']").val();
		    		
		    	}
		    	var getHsm;
		    	if (feeType == '1') {
		    		getHsm = new hsm(sid,hsmAmt,matrStrList[i-1].strBuf,cntTrAmt,deductFlag,cAmt);
		    	} else {
		    		hsmAmt = parseFloat(hsmAmt) + parseFloat(taxNamt);
		    		getHsm = new hsm(sid,hsmAmt,matrStrList[i-1].strBuf,"","","");
		    	}
		    	paramList[i-1] = getHsm;
		    }
		    
		    var paramData = new param(feeStartDate,feeEndDate,paramList);
		    data['paramData'] = paramData;
		    if(feeType == '1'){
		    	//受益期固定，金额不固定
		    	data['feeAmt'] = '${cnt.feeAmt}';
		    	data['feePenalty'] = '${cnt.feePenalty}';
		    	data['taxAmt'] = taxAmt;
		    }
		}
		
	}else{
		data['targetT'] = $("#tableDiv").html();
		
		var feeStartDate = '${cnt.feeStartDate}';
	    var feeEndDate = '${cnt.feeEndDate}';
	    var feeType = '${cnt.feeType}';
	    var paramList = new Array();
	    var totalNumTrList = $("#totalNumTable tr");
	    var cntAmt = '${cnt.cntAmt}';
	    var taxAmt = parseFloat('${cnt.cntTaxAmt}');//税额
	    var cntAllAmt = parseFloat('${cnt.cntAllAmt}');
	    var feeAmt = feeAmtTmp = parseFloat('${cnt.feeAmt}');
    	var penalty = '${cnt.feePenalty}';
    	var sumDevAmt = Number(cntAmt)-Number(penalty);
    	var sumTemp = 0;
    	var cAmt = 0;
    	
	    for(var i=1;i<totalNumTrList.length;i++){
	    	var hsmAmt = 0;
	    	var sid = $(totalNumTrList[i]).find("input[name='sid']").val();//序号
	    	var taxNamt = $(totalNumTrList[i]).find("input[name='taxNamt']").val();
	    	var cntTrAmt = parseFloat($(totalNumTrList[i]).find("input[name='cntTrAmt']").val());
	    	var deductFlag = $(totalNumTrList[i]).find("input[name='deductFlag']").val();//是否可抵扣
	    	if(feeType == '1'){ // 含有约定罚金的物料金额处理
	    		cAmt = $(totalNumTrList[i]).find("input[name='hsmAmt']").val();
	    		
	    		if (deductFlag == 'Y') {
					feeAmtTmp = changeTwoDecimal(feeAmtTmp - feeAmt*parseFloat(cntAmt)/cntAllAmt*cntTrAmt/parseFloat(cntAmt));
				}
	    		if(i == totalNumTrList.length-1){
					hsmAmt = changeTwoDecimal(parseFloat(feeAmtTmp)-parseFloat(sumTemp)); //最后一项物料金额由合同确定金额减去前面已计算的物料之和
	    		}else{
	    			var signalHsmAmt = parseFloat($(totalNumTrList[i]).find("input[name='hsmAmt']").val()); // 物料金额（包含罚金）
		    		hsmAmt = changeTwoDecimal(feeAmt*parseFloat(cntAmt)/cntAllAmt*signalHsmAmt/parseFloat(cntAmt)); //物料金额（将合同金额确定部分按比例查分到各个物料）
		    		if (deductFlag == 'N') {
		    			hsmAmt = changeTwoDecimal(parseFloat(hsmAmt) + feeAmt*parseFloat(cntAmt)/cntAllAmt*cntTrAmt/parseFloat(cntAmt));
		    		}
		    		sumTemp += parseFloat(hsmAmt);
	    		}
	    	}else{
	    		hsmAmt = $(totalNumTrList[i]).find("input[name='hsmAmt']").val();
	    		
	    	}
	    	var getHsm;
	    	if (feeType == '1') {
	    		getHsm = new hsm(sid,hsmAmt,matrStrList[i-1].strBuf,cntTrAmt,deductFlag,cAmt);
	    	} else {
	    		hsmAmt = parseFloat(hsmAmt) + parseFloat(taxNamt);
	    		getHsm = new hsm(sid,hsmAmt,matrStrList[i-1].strBuf,"","","");
	    	}
	    	paramList[i-1] = getHsm;
	    }
	    
	    var paramData = new param(feeStartDate,feeEndDate,paramList);
	    data['paramData'] = paramData;
	}
	window.dialogArguments=data;
	$.dialog.open(
			url,
			{	
				data:data,
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"费用受益金额录入",
			    id:"dialogCutPage",
				close: function(){
					var returnValue = art.dialog.data('returnValue'); 
					if(returnValue){
						$("#tableDiv").empty();
						$("#tableDiv").append(returnValue);
	// 					$("#confirmFeeForm").empty();
	// 					$("#confirmFeeForm").append(returnValue);
	// 					//关闭时ajax调用后台
						var url = "contract/confirm/confirmFeeAmt.do?<%=WebConsts.FUNC_ID_KEY%>=0302010309";
						App.ajaxSubmit(url, {data : $('#confirmForm').serialize(),async : false}, function(data){
							var info = eval(data);
							if (info.flag) {
								$("#confirmBtn").removeAttr("disabled").attr("aria-disabled",false).removeClass("ui-button-disabled ui-state-disabled");
							}
						});
					}
				}
			}
		 );
}


//计算所有年，得到一个年份数组
function yearsArr(startDate, endDate) {
	//开始年份 及 相隔年份数
	var startYear = startDate.split('-')[0];
	var yearNum = parseInt(endDate.split('-')[0]) - parseInt(startDate.split('-')[0]);
	var yearArr = new Array();
	
	//3.循环 年列表{2014,2015}，月列表{10,11,12,01,02,03,04}
	for(var i=0;i<=yearNum;i++)
	{
		yearArr.push(parseInt(startYear)+i);
	}
	
	return yearArr;
}

//按年计算月份数据，根据年来获取
function monthsArr(startDate, endDate) {
	//1.计算月份差
	var monthsNum = cntMonths(startDate,endDate);
	var startMonth = parseInt(startDate.split('-')[1],10);
	var monthArr = new Array();
	
	var yearNum = parseInt(endDate.split('-')[0]) - parseInt(startDate.split('-')[0]);
	
	//3.循环 年列表{2014,2015}，月列表{10,11,12,01,02,03,04}
	var times = monthsNum;
	for(var i=0;i<=yearNum;i++)
	{
		var subMArr = new Array();
		for(var j=1;j<=12;j++){
			if(times<=0){
				break;
			}
			if(i==0&&j==1){
				j= startMonth;
			}
			if(j<10){
				subMArr.push("0"+j);
				times--;
			}else{
				subMArr.push(j+"");
				times--;
			}
		}
		monthArr.push(subMArr);//3
	}
	
	return monthArr;
}

//房租类费用类型合同受益金额取值
function getyTenancy(){
	var feeStartDateShow = '${cnt.feeStartDateShow}';//实际合同受益开始日期
	var feeEndDateShow = '${cnt.feeEndDateShow}';//实际合同受益终止日期
	var paramDataList = new Array();
	var tenancyStr = '${cnt.tenanciesStr}';//阶段行
	var totalNumTrList = $("#totalNumTable tr:gt(0)");//物料行
	var tenancyListStr = eval(tenancyStr);//阶段行
	
	var yearTotal = yearsArr(feeStartDateShow, feeEndDateShow);
	var monthTotal = monthsArr(feeStartDateShow, feeEndDateShow);
	var targetList = new Array();
	//将每个月，每一条物料的信息，加工到bean里面去，存到list传到页面到拆。
	
	//循环实际合同受益终止日期区间所有的年月
	for (var i = 0; i < yearTotal.length; i++) {
		var monthT = monthTotal[i];
		for (var j = 0; j < monthT.length; j++) {
			//循环房租类的分段维护的物料
			for (var k = 0 ; k < tenancyListStr.length; k++) {
				var tenancyList = tenancyListStr[k].list;
				for (var h = 0 ; h < tenancyList.length; h++) {//循环每个物料对应的分段数，后面将月和年按月拆分。
					var execAmtTr = tenancyList[h].execAmtTr;//不含税金额
					var taxAmtTr = tenancyList[h].taxAmtTr;//税额
					var matrCodeFz = tenancyList[h].matrCodeFz;//物料编码
					var yearTr = yearsArr(tenancyList[h].fromDate, tenancyList[h].toDate);//分段的年
					var monthTr = monthsArr(tenancyList[h].fromDate, tenancyList[h].toDate);//分段的月份
					var sumSignal = 0;
					var sumSignalTax = 0;
					for (var l = 0; l < yearTr.length; l++) {
						var monthTrFz = monthTr[l];
						for (var m = 0; m < monthTrFz.length; m++) {
							for(var n=0; n<totalNumTrList.length; n++){//循环物料
								if (yearTotal[i] == yearTr[l] && monthT[j] == monthTrFz[m]) {
									if (matrCodeFz == (matrStrList[n].strBuf.split("_"))[1]) {//根据所有物料，来分配房租金额
										var sid = $(totalNumTrList[n]).find("input[name='sid']").val();//序号
										var taxAmtTmp = (parseFloat(taxAmtTr)*parseFloat(matrStrList[n].cntTrRatio)).toFixed(2);//根据占比，计算每行物料对应的税额
										var calAmtTmp = (parseFloat(execAmtTr)*parseFloat(matrStrList[n].execRatio)).toFixed(2);//根据占比，计算每行物料对应的不含税金额
										if (sid == matrStrList[n].maxSid) {//如果是最后一个相同物料，则用总的不含税金额减去前面所有不含税金额,总的税额减去前面所有税额
											calAmtTmp = execAmtTr - sumSignal;
											taxAmtTmp = taxAmtTr - sumSignalTax;
										}
										sumSignal = parseFloat(sumSignal) + parseFloat(calAmtTmp);
										sumSignalTax = parseFloat(sumSignalTax) + parseFloat(taxAmtTmp);
										var calAmt = (parseFloat(calAmtTmp) + parseFloat(matrStrList[n].deductFlag == 'N'? taxAmtTmp:0)).toFixed(2);//若物料税码为不可抵扣，则最终金额=不含税金额+不可抵扣税
										var bean = new cntFee(sid+"_"+matrStrList[n].strBuf,"",calAmt,calAmt,yearTotal[i],monthT[j]);
										targetList.push(bean);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	//paramDataList[0] = targetList;
	paramDataList.push(targetList.sort(sortBy));
	return paramDataList;
}

function sortBy(a,b){
	return (a.year+a.month+(a.subId.split("_"))[0]).localeCompare(b.year+b.month+(b.subId.split("_"))[0]);
}

//bean
function cntFee(subId,feeDate,calAmt,feeAmt,year,month){
	  this.subId=subId; 
	  this.feeDate=feeDate;
	  this.calAmt=calAmt; 
	  this.feeAmt=feeAmt; 
	  this.year=year;
	  this.month=month;
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

//当费用类型的查询条件发生改变时，清空tableDiv
function removeFeeInfoTable(){
	$("#tableDiv").empty();
}

//如果为订单退回的合同，则这个值不为空且值等于0
var cntOrder = '${cnt.isOrder}';

function pageInit() {
	//传递位置信息
	var location = "";
	$(".nav").children().each(
	function(){
				if($(this).text()!='您当前的位置：')
				{
					location += $(this).text() ;
				}
			 }); 
	$("#busMenu").val(location);
	App.jqueryAutocomplete();

	var cntType = '${cnt.cntType}';
	if(cntType == '0'){
		//资产类
		$("#confirmBtn").removeAttr("disabled").attr("aria-disabled",false).removeClass("ui-button-disabled ui-state-disabled");
	}else if(cntType == '1'){
		//费用类
		var feeType = '${cnt.feeType}';
		var ffflag = '${FFFlag}';		
		if(feeType == '2' || (ffflag == 'no')){
			//费用类型为其他
			$("#confirmBtn").removeAttr("disabled").attr("aria-disabled",false).removeClass("ui-button-disabled ui-state-disabled");
		}
	}
	getMatrStrList();
	if(getMonthOverFlag()){
		App.notyError("月结状态中，暂不能进行合同确认操作！");
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

function toOperate(cntNum, type){
	if(getMonthOverFlag()){
		App.notyError("月结状态中，暂不能进行合同确认操作！");
		return false;
	} 
	if(!$.isBlank(cntOrder)){
		//原合同里订单状态不为空，说明是订单退回的合同
	}
	var loc = $("#busMenu").val();
	var url = "";
	var title = "";
	if ('confirm'==type)
	{
		$('#orderdisplayid').show();
		var cntType = '${cnt.cntType}';
		var feeType = '${cnt.feeType}';
		var ffflag = '${FFFlag}';
		if((cntType == '1') && (feeType == '0' || feeType == '1') && ffflag == 'yes'){
			if($.isBlank($("#tableDiv").html())){
				App.notyError('请先录入费用受益金额！');
				return false;
			}
		}
		if(!checkProjAmt(cntNum)){
			return false;
		}
		$("#feeTypeAudit").val(feeType);
		url = "<%=request.getContextPath()%>/contract/confirm/confirmPass.do?<%=WebConsts.FUNC_ID_KEY %>=0302010302";
		title = "合同确认";
	}
	else
	{
		$('#orderdisplayid').hide();
		url = "<%=request.getContextPath()%>/contract/confirm/confirmReturn.do?<%=WebConsts.FUNC_ID_KEY %>=0302010303";
		title = "合同退回";
	}
	$("#auditDiv").dialog({
		title:title,
		closeOnEscape:false,
		autoOpen: true,
		height: 'auto',
		width: '40%',
		modal: true,
		zIndex:100,
		dialogClass: 'dClass',
		resizable: false,
		open:function()
		{
			$('#cntNum').val(cntNum);
			$('#location').val(loc);
			$("#feeTypeTable").empty().append($("#tableDiv").html());
		},
		buttons: {
			"确定": function() {
				//先必须选择是否生成订单
				var isOrder=$("#isOrder").val();
				if('confirm'==type){
					if("${cnt.cntType}" == '0' || ("${cnt.cntType}" == '1' && "${cnt.feeType}" == '2'))
					{
						if(isOrder==''||isOrder==null){
							App.notyError("请先选择是否生成订单!");
							return false;
						}
					}
				}

				if(cntOrder == '0'&&'confirm'==type){
					if(isOrder != '0'){
						//订单退回的合同选择不生成订单则判断该合下没有发送到FMS的订单才可以选择不生产订单
						if(checkSendOrder(cntNum)){
							App.notyError("该合同下有上送订单数据，必须生成订单!");
						    return false;
						}
					}	
				}
				
				if('return'==type){
					if(!App.valid("#auditForm")){return;}
				}
				if(isOrder == '0'&&'confirm'==type){
					//判断合同下的物料是否都是订单类物料
					if(checkNotOrderMatr(cntNum)){
						App.notyError("该合同下有非订单类物料，不能生成订单!");
						return false;
					}
					if(!confirm('是否确认生成订单？')){
					    return false;
					}
				}
				if(isOrder == '1'&&'confirm'==type){
				  if(!confirm('是否确认不生成订单？')){
					  return false;
				  }
				}
				if('return'==type){
				  if(!confirm('是否确定执行退回操作？')){
					  return false;
				  }
				}
				//确认提交
				$('#auditForm').attr("action",url);
				$('#auditForm').submit();
				
				
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
//检查是否有发送到FMS的订单
function checkSendOrder(cntNum){
	var list = false;
	var data = {};
	data['cntNum'] = cntNum;
	var url = "contract/confirm/checkSendOrder.do?VISIT_FUNC_ID=0302010310";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			list = data.isExist;
	});
	return list;
}
//检查合同下是否都是订单类物料
function checkNotOrderMatr(cntNum){
	var list = false;
	var data = {};
	data['cntNum'] = cntNum;
	var url = "contract/confirm/checkNotOrderMatr.do?VISIT_FUNC_ID=0302010311";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			list = data.isExist;
	});
	return list;
}
//确认提交前检查是否是订单退回的合同
/* function beforeSubmit(){
	if(cntOrder == '0'){
		if(order != '0')
			App.notyError("订单退回的合同必须生成订单!");
		return false;
	}
	return true;
}
 */
//校验合同采购金额是否超出预算
function checkProjAmt(cntNum){
	var result =  true;
	var url = "contract/confirm/checkProjAmt.do?<%=WebConsts.FUNC_ID_KEY%>=0302010304";
	var data = {};
	data['cntNum'] = cntNum;
	App.ajaxSubmit(url, {data : data,async : false}, 
    		function(data){
				var projName = data.projName;
				if("" != projName && null != projName){
					App.notyError("项目"+projName+"超出预算，请修改后再重新确认合同！");
					result = false;
				}
			});
	return result;
}
var order = "";
/* //改变是否生成订单
function changeIsOrder(obj){
	App.radioCheck(obj,"isOrderDiv");
	var changeVal = $("#isOrderDiv").find(".check-label").next().val();
	if(changeVal != order){
		order = changeVal;
	}
} */
function toSelectResult(s){
	if(s==$("#isOrder").val()){
		return false;
	}
	if($("#isOrder").val()!=''&&$("#isOrder").val()!=null){
		$("#waterMemo").val("");
	}
	if(s=='1'){
		$_showWarnWhenOverLen1(document.getElementById("waterMemo"),2000,'authmemoSpan');
		$("#isOrder").val("1");
	}else{
		//同意
		$_showWarnWhenOverLen1(document.getElementById("waterMemo"),2000,'authmemoSpan');
		$("#isOrder").val("0");
	}
}

function toggleTr(obj) {
		$(obj).siblings("tr").toggle();
}
</script>
</head>

<body>
<p:authFunc funcArray="03020103,0302010302"/>
<form action="" method="post" id="confirmForm" action="<%=request.getContextPath()%>/contract/confirm/confirmList.do?<%=WebConsts.FUNC_ID_KEY%>=03020103">
	<p:token/>
	<table>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">
							基础信息（合同编号：${cnt.cntNum}）
							<input type="hidden" name="cntNum" value="${cnt.cntNum}" />
						</th>
					</tr>
					<tr>
						<td class="tdLeft">集采编号</td>
						<td class="tdRight">${cnt.stockNum}</td>
						<td class="tdLeft">评审编号</td>
						<td class="tdRight">${cnt.psbh}</td>
					</tr>
					<tr>
						<td class="tdLeft">审批类别</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'CNT_APPROVE_TYPE'" selectedValue="${cnt.lxlx}"/>	
						</td>
						<td class="tdLeft">签报文号</td>
						<td class="tdRight">${cnt.qbh}</td>
					</tr>
					<tr>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'CNT_TYPE'" selectedValue="${cnt.cntType}"/>	
						</td>
						<td class="tdLeft">合同状态</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"
								 conditionStr="CATEGORY_ID = 'CNT_DATE_FLAG'" selectedValue="${cnt.dataFlag}"/>	
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同事项</td>
						<td class="tdRight">${cnt.cntName}</td>
						<td class="tdLeft">关联合同号</td>
						<td class="tdRight">${cnt.cntNumRelated}</td>
					</tr>
					<tr>
						<td class="tdLeft">审批数量</td>
						<td class="tdRight">${cnt.lxsl}</td>
						<td class="tdLeft">审批金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.lxje}"/></td>
					</tr>
					<c:if test="${cnt.lxlx eq '1'}">
					<tr>
						<td class="tdLeft" colspan="4">
							<table>
								<tr>
 									<th width="20%">缩位码</th>
									<th width="30%">审批编号</th>
									<th width="20%">日期</th>
									<th width="10%">审批数量</th>
<!-- 									<th width="10%">已执行数量</th> -->
									<th width="10%">数量</th>
									<th width="10%">审批金额</th>
<!-- 									<th width="11%">已执行金额</th> -->
<!-- 									<th width="10%">金额</th> -->
								</tr>
								<c:forEach items="${cnt.dzspInfos}" var="dzspItem">
									<tr>
										<td>${dzspItem.abcde}</td>
										<td>${dzspItem.projCrId}</td>
										<td>${dzspItem.createDate}</td>
										<td>${dzspItem.projCrNum}</td>
<%-- 										<td>${dzspItem.exeNum}</td> --%>
										<td>${dzspItem.abcdeNum}</td>
										<td><fmt:formatNumber type="number" value="${dzspItem.projCrAmt}"/></td>
<%-- 										<td><fmt:formatNumber type="number" value="${dzspItem.exeAmt}"/></td> --%>
<%-- 										<td><fmt:formatNumber type="number" value="${dzspItem.abcdeAmt}"/></td> --%>
									</tr>
								</c:forEach>
								
								<c:if test="${empty cnt.dzspInfos}">
									<tr>
										<td colspan="8" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>	
					</c:if>
					<tr>
						<td class="tdLeft">供应商</td>
						<td class="tdRight">
							<%-- <forms:codeName tableName="TB_PROVIDER" selectColumn="PROVIDER_CODE,PROVIDER_NAME"
								 valueColumn="PROVIDER_CODE" textColumn="PROVIDER_NAME" selectedValue="${cnt.providerCode}"/> --%>
								 ${cnt.providerName}
						</td>
						<c:if test="${'' != cnt.srcPoviderName && null != cnt.srcPoviderName}">
							<td class="tdLeft">内部供应商</td>
							<td class="tdRight">
								${cnt.srcPoviderName}
							</td>
						</c:if>
						<c:if test="${empty cnt.srcPoviderName}">
							<td class="tdLeft"></td>
							<td class="tdRight">
							</td>
						</c:if>
					</tr>
					<tr>
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
					<!-- 
					<tr>
						<td class="tdLeft">增值税率</td>
						<td class="tdRight">
							${cnt.providerTaxRate }
						</td>
						<td class="tdLeft">增值税金</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${cnt.providerTax }"/>
						</td>
					</tr>
					 -->
					<tr>
						<td class="tdLeft">签订日期</td>
						<td class="tdRight">${cnt.signDate}</td>
						<td class="tdLeft">专项包</td>
						<td class="tdRight">
							<c:if test="${'0'==cnt.isSpec}">是</c:if>
							<c:if test="${'1'==cnt.isSpec}">否</c:if>
						</td>
					</tr>
					<tr>
						<c:if test="${cnt.cntType == '0' && cnt.isSpec == '1' }">
							<tr>
								<td class="tdLeft">省行统购</td>
								<td class="tdRight" colspan="3">
									<c:if test="${'0'==cnt.isProvinceBuy}">是</c:if>
									<c:if test="${'1'==cnt.isProvinceBuy}">否</c:if>
								</td>
							</tr>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft">合同总金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.cntAllAmt}"/></td>
						<td class="tdLeft">其中合同质保金(%)</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.zbAmt}"/></td>
					</tr>
					<tr>
						<td class="tdLeft">不含税金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.cntAmt}"/></td>
						<td class="tdLeft">税额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.cntTaxAmt}"/></td>
					</tr>
					<tr>
						<td class="tdLeft">付款条件</td>
						<td class="tdRight" colspan="3">
							<c:if test="${'3' eq cnt.payTerm}">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"
								 conditionStr="CATEGORY_ID = 'CNT_STAGE_TYPE'" selectedValue="${cnt.stageType}"/>
							</c:if>
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"
								 conditionStr="CATEGORY_ID = 'CNT_PAY_TERM'" selectedValue="${cnt.payTerm}"/>	
						</td>
					</tr>
					<c:if test="${'3' eq cnt.payTerm}">
						<c:if test="${'0' eq cnt.stageType}">
						<tr>
							<td class="tdLeft">按进度分期付款</td>
							<td class="tdRight" colspan="3">
								<table>
								<c:forEach items="${cnt.stageInfos}" var="schItem" varStatus="status">
									<tr>
										<td>第${status.index+1}期 付款年月:${schItem.jdDate}    支付${schItem.jdzf}元</td>
									</tr>
								</c:forEach>
								</table>
							</td>
						</tr>	
						</c:if>
						<c:if test="${'1' eq cnt.stageType}">
						<tr>
							<td class="tdLeft">按日期分期付款</td>
							<td class="tdRight" colspan="3">
								<table>
								<c:forEach items="${cnt.stageInfos}" var="dateItem">
									<tr>
										<td>合同签订后 ${dateItem.rqtj}天 支付<fmt:formatNumber type="number" value="${dateItem.rqzf}"/>元</td>
									</tr>
								</c:forEach>
								</table>
							</td>
						</tr>	
						</c:if>
						<c:if test="${'2' eq cnt.stageType}">
						<tr>
							<td class="tdLeft">按条件分期付款</td>
							<td class="tdRight" colspan="3">
								到货支付<fmt:formatNumber type="number" value="${cnt.stageInfos[0].dhzf}"/>元    验收支付<fmt:formatNumber type="number" value="${cnt.stageInfos[0].yszf}"/>元 结算支付<fmt:formatNumber type="number" value="${cnt.stageInfos[0].jszf}"/>元 
							</td>
						</tr>	
						</c:if>
					</c:if>
					<tr>
						<td class="tdLeft">采购数量</td>
						<td class="tdRight" colspan="3">${cnt.totalNum}</td>
						<%-- <td class="tdLeft">是否生成订单</td>
						<td class="tdRight">
							<div class="ui-widget">
								<select id="order" name="order" onchange="changeIsOrder()" valid errorMsg="请选择是否生成订单">
									<option value="">--请选择--</option>						
									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
									 conditionStr="CATEGORY_ID = 'CNT_IS_YN'"
									 orderType="ASC"/>
								</select>
							</div>
						</td> --%>
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
						<th colspan="4">
							合同物料信息
						</th>
					</tr>
					
					<tr>
						<td class="tdLeft" colspan="4">
							<table id="totalNumTable">
								<tr>
									<th width="8%">项目</th>
									<th width="11%">费用承担部门</th>
									<th width="8%">物料类型</th>
									<th width="8%">监控指标</th>
									<th width="8%">设备型号</th>
									<th width="9%">专项</th>
									<th width="9%">参考</th>
									<th width="9%">税码</th>
									<th width="6%">数量</th>
									<th width="8%">单价(元)</th>
									<th width="8%">不含税金额(元)</th>
									<th width="8%">税额(元)</th>
									<th width="9%">保修期(年)</th>
									<th width="8%">制造商</th>
								</tr>
								<c:forEach items="${cnt.devices}" var="projItem">
									<tr>
										<td>${projItem.projName}<input type="hidden" name="sid" value="${projItem.subId}"></td>
										<td>${projItem.feeDeptName}<input type="hidden" name="feeDeptName" value="${projItem.feeDeptName}"/></td>
										<td>${projItem.matrName}<input type="hidden" name="matrName" value="${projItem.matrName}"/>
											<input type="hidden" name="matrCode" value="${projItem.matrCode}"/></td>
										<td>${projItem.montName}<input type="hidden" name="cglCode" value="${projItem.cglCode}"/></td>
										<td>${projItem.deviceModelName}</td>
										<td>${projItem.specialName}<input type="hidden" name="specialName" value="${projItem.specialName}"/></td>
								   		<td>${projItem.referenceName}<input type="hidden" name="referenceName" value="${projItem.referenceName}"/></td>
								   		<td>${projItem.taxCode }</td>
										<td>${projItem.execNum}</td>
										<td><fmt:formatNumber type="number" value="${projItem.execPrice}"/></td>
										<td><fmt:formatNumber type="number" value="${projItem.execAmt}"/><input type="hidden" name="hsmAmt" value="${projItem.execAmt}"></td>
										<td><fmt:formatNumber type="number" value="${projItem.cntTrAmt}"/><input type="hidden" name="cntTrAmt" value="${projItem.cntTrAmt}">
											<input type="hidden" name="taxNamt" value="${projItem.taxNamt}"/>
											<input type="hidden" name="deductFlag" value="${projItem.deductFlag}"/></td>
										<td>${projItem.warranty}</td>
										<td>${projItem.productor}</td>
									</tr>
								</c:forEach>
								
								<c:if test="${empty cnt.devices}">
									<tr>
										<td colspan="12" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">备注</td>
						<td class="tdRight" colspan="3">${cnt.memo}</td>
					</tr>
					<tr>
						<td class="tdLeft">付款责任中心</td>
						<td class="tdRight">${cnt.payDutyCodeName}</td>
						<td class="tdLeft">业务发起部门</td>
						<td class="tdRight">${cnt.createDeptName}</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${cnt.cntType eq '1'}">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">
							费用信息
						</th>
					</tr>
					<c:if test="${cnt.feeType eq '0'}">
					<tr>
						<td class="tdLeft">费用类型</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_TYPE'" selectedValue="${cnt.feeType}"/>	
						</td>
						<td class="tdLeft">费用子类型</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_SUB_TYPE'" selectedValue="${cnt.feeSubType}"/>	
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同受益起始日期</td>
						<td class="tdRight">${cnt.feeStartDate}</td>
						<td class="tdLeft">合同受益终止日期</td>
						<td class="tdRight">${cnt.actualFeeEndDate}</td>
					</tr>
					<tr>
						<td class="tdLeft">实际合同受益终止日期区间</td>
						<td class="tdRight">${cnt.feeStartDateShow} 至  ${cnt.feeEndDateShow}</td>
						<td class="tdLeft"></td>
						<td class="tdRight"></td>
					</tr>
					<c:if test="${cnt.feeSubType eq '1'}">
					<tr>
						<td class="tdLeft">地址(说明)</td>
						<td class="tdRight">${cnt.wydz}</td>
						<td class="tdLeft">租赁面积(数量)</td>
						<td class="tdRight">${cnt.area}平方米(项)</td>
					</tr>
					<tr>
						<td class="tdLeft">房产(项目)性质</td>
						<td class="tdRight" <c:if test="${cnt.houseKindId=='1'}">colspan="1"</c:if><c:if test="${cnt.houseKindId!='1'}">colspan="3"</c:if>>
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'HOUSE_KIND'" selectedValue="${cnt.houseKindId}"/>	
						</td>
						<c:if test="${cnt.houseKindId=='1'}">
							<td class="tdLeft">自助银行名称</td>
							<td class="tdRight">${cnt.autoBankName}</td>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft">租金递增条件 </td>
						<td class="tdRight" colspan="3">
								<c:forEach items="${cnt.tenanciesList}" var="tenanciesItem">
									<table>
										<tr class="collspan-con" style="cursor: pointer;" onclick="toggleTr(this)">
											<th colspan="6">
												<div style="display: block; float: center; ">${tenanciesItem.matrName }</div>
											</th>
										</tr>
										<tr>
											<td>序号</td>
											<td>开始日期</td>
											<td>结束日期</td>
											<td>合同总金额</td>
											<td>不含税金额</td>
											<td>税额</td>
										</tr>
										<c:forEach items="${tenanciesItem.list}" var="tcyItem" varStatus="status">
											<tr>
												<td><span name="tdSpan">${status.index+1}</span></td>
												<td><span name="fromDateShow">${tcyItem.fromDate}</span></td>
												<td><span name="toDate">${tcyItem.toDate}</span></td>
												<td><span name="cntAmtTr">${tcyItem.cntAmtTr}</span>&nbsp;元/月</td>
												<td><span name="execAmtTr">${tcyItem.execAmtTr}</span>&nbsp;元/月</td>
												<td><span name="taxAmtTr">${tcyItem.taxAmtTr}</span>&nbsp;元/月</td>
											</tr>
										</c:forEach>
									</table>
								</c:forEach>
								
								<c:if test="${empty cnt.tenanciesList}">
									<table>
										<tr>
											<td colspan="6" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
										</tr>
									</table>
								</c:if>
							
						</td>
					</tr>
					<tr>
						<td class="tdLeft">备注</td>
						<td class="tdRight" colspan="3">${cnt.remark}</td>
					</tr>
					</c:if>
					</c:if>
					
					<c:if test="${cnt.feeType eq '1'}">
					<tr>
						<td class="tdLeft">费用类型</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_TYPE'" selectedValue="${cnt.feeType}"/>	
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同受益起始日期</td>
						<td class="tdRight">${cnt.feeStartDate}</td>
						<td class="tdLeft">合同受益终止日期</td>
						<td class="tdRight">${cnt.actualFeeEndDate}</td>
					</tr>
					<tr>
						<td class="tdLeft">实际合同受益终止日期区间</td>
						<td class="tdRight">${cnt.feeStartDateShow} 至  ${cnt.feeEndDateShow}</td>
						<td class="tdLeft"></td>
						<td class="tdRight"></td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额（人民币）确定部分</td>
						<td class="tdRight">${cnt.feeAmt}</td>
						<td class="tdLeft">合同金额（人民币）约定罚金</td>
						<td class="tdRight">${cnt.feePenalty}</td>
					</tr>
					</c:if>
					
					<c:if test="${cnt.feeType eq '2'}">
					<tr>
						<td class="tdLeft">费用类型</td>
						<td class="tdRight">
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'CNT_FEE_TYPE'" selectedValue="${cnt.feeType}"/>	
						</td>
					</tr>
					</c:if>
				</table>
			</td>
		</tr>
		</c:if>
		<tr></tr>
		<tr>
			<td colspan="4">
				<c:if test="${!empty cnt.id}">
					<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.cntNum}','${cnt.icmsPkuuid }')"/>
				</c:if>
				<c:if test="${cnt.cntType == '1' && (cnt.feeType == '0' || cnt.feeType == '1') && (FFFlag == 'yes')}">
					<input type="button" value="受益金额录入" onclick="feeTypePage()"/>
				</c:if>
				<input id="confirmBtn" disabled="disabled" type="button" value="通过" onclick="toOperate('${cnt.cntNum}','confirm');">  
				<input type="button" value="退回" onclick="toOperate('${cnt.cntNum}','return');">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">  
			</td>
		</tr>
	</table>
	<div style="display:none" id="tableDiv"></div>
</form>
<p:page/>
<jsp:include page="audit.jsp" />
</body>
</html>