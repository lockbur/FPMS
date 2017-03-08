<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.tool.ConstantMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>费用受益金额录入</title>
<base target="_self">

<script type="text/javascript">
//subId 受益总额Map
codeAmtMap=  new HashMap();
//当前日期
var nowDate = '${sysDate}';
var dateStr = nowDate.split('-');
var nowYear = dateStr[0];
var nowMonth = dateStr[1];
var nextYear = nowYear;
var nextMonth = nowMonth+1;
var feeAmt = 0;
var feePenalty = 0;
var taxAmt = 0;
//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	//获取参数{subId:序号,amt：测算总金额,strBuf：展示名}
	var paramD = window.parent.dialogArguments.paramData;
	var table = window.parent.dialogArguments.targetT;
	feeAmt = parseFloat(window.parent.dialogArguments.feeAmt); // 合同金额确定部分
	feePenalty = window.parent.dialogArguments.feePenalty; // 约定罚金
	taxAmt = parseFloat(window.parent.dialogArguments.taxAmt);//税额
	var confirmFlag = window.parent.dialogArguments.confirmFlag;
    //var feeStartFlag = window.parent.dialogArguments.feeStartFlag;
	var subIdLength = window.parent.dialogArguments.subIdLength;
	var hasModifyFee = window.parent.dialogArguments.hasModifyFee;
	var tenancyData = window.parent.dialogArguments.tenancyData;
	if($.isBlank(table)){
		if(!$.isBlank(tenancyData)){
			for(var i=0; i<tenancyData.length; i++){
				//inPage(tenancyData[i]);
				inPageTenancy(tenancyData[i]);
			}
			merge();
		}else{
			inPage(paramD);
			merge();
		}
	}else{
		$("#targetDiv").empty().append(table);
		var amtMap = new HashMap();
		amtMap = getAmtMap(paramD.paramList);
		codeAmtMap = amtMap;
		/* //合同变更时当月和下月的预提待摊受益金额不可修改
		if(!$.isBlank(confirmFlag)){
			$("#targetT tr:gt(0)").each(function(){
				var trYear = Number($(this).find("td[id='year']").text());
				var trMonth = Number($(this).find("td[id='month']").text());
				if((nowYear > trYear) || (nowYear == trYear && nowMonth >= trMonth) || (nextYear == trYear && nextMonth == trMonth)){
					$(this).find("input[name='cglFeeAmt']").attr("readonly","readonly");
				}
			});			
		} */
	}
}

//设置页面关闭时返回值
function getReturnValue(){
	if(!checkTotalSum()){return false;}
	fresh();
	var T = $("#targetDiv").clone();
	art.dialog.data('returnValue',T.html());
	art.dialog.close();	   	
}
//校验受益金额总和与测算金额总和是否一致
function checkTotalSum(){

	var codeKeys = codeAmtMap.keys();
 	for(var p in codeKeys)
	{
 		var str = codeKeys[p];
	    var findId = str.split("_")[0];
		var total=0;
		var measureAmt=0;
		$("#targetT").find("input[id="+findId+"]").each(function(){
		 	total = parseFloat(((total*1000+ parseFloat($(this).val())*1000)/1000).toFixed(2)); 
		});
		$("#targetT").find("input[id='cglAmt"+findId+"']").each(function(){
			measureAmt = parseFloat(((measureAmt*1000+ parseFloat($(this).val())*1000)/1000).toFixed(2)); 
		});
		var left = parseFloat((measureAmt-total).toFixed(2))>0?parseFloat((measureAmt-total).toFixed(2)):parseFloat((total-measureAmt).toFixed(2));
	    if(total != measureAmt){
	    	App.notyError("序号为【"+findId+"】的测算数据总和与受益数据总和不相等！测算金额总和为【"+measureAmt
	    			+"】受益金额总和为【"+total+"】相差金额为【"+left+"】");
	    	return false;
		}
		
	} 
	return true;
}

//更新输入框的值
function fresh(){
	$("#targetT").find("input[name='cglFeeAmt']").each(function(){
		var newValue = $(this).val();
		$(this).attr("value",newValue);
	});
}

//进入页面
function inPageTenancy(paramD){
	//var p[] = paramD;
	
	var accumulateAmt = new Array(); // 当月前的累积受益金额
	 for(var i=0;i<=paramD.length;i++){
		accumulateAmt[i] = 0;
	} 
	//6.循环打印列表
	for(var i=0;i<paramD.length;i++){
		var b = paramD[i];
		var trObj = createTr();
		var feeYyyymm = b.year+b.month+"";
		var str = b.subId+"";
		var arrStr = new Array();
		arrStr = str.split("_");//subId+feeDeptName+物料编码+物料名称+核算码+specialName + referenceName;
		$(trObj).append("<td id='year' rowspan='1'>"+b.year+"</td>");
		$(trObj).append("<td id='month' rowspan='1'>"+b.month+"</td>");
		$(trObj).append("<td><input type='hidden' value='"+feeYyyymm+"' name='feeYyyymm'/><input type='hidden' value='"+arrStr[0]+"' name='subId'/>"+arrStr[0]+"</td>");
		$(trObj).append("<td>"+arrStr[1]+"</td>");//费用承担部门
		$(trObj).append("<td>"+arrStr[2]+arrStr[3]+"</td>");//物料编码及名称
		$(trObj).append("<td>"+arrStr[4]+"</td>");//核算码
		$(trObj).append("<td>"+arrStr[5]+"</td>");//专项
		$(trObj).append("<td>"+arrStr[6]+"</td>");//参考
		$(trObj).append("<td><input type='hidden' value='"+b.calAmt+"' name='cglCalAmt' id='cglAmt"+arrStr[0]+"'/>"+b.calAmt+"</td>");//测算金额
		if((nowYear > b.year) || (nowYear == b.year && nowMonth > b.month)){
			//当月之前
			$(trObj).append("<td><input type='hidden' value='0' name='cglFeeAmt' id='"+arrStr[0]+"'/>0</td>");//收益金额
			accumulateAmt[arrStr[0]] = changeTwoDecimal(Number(accumulateAmt[arrStr[0]]) + Number(b.feeAmt));
		}else if(nowYear == b.year && nowMonth == b.month){
			//当月
			accumulateAmt[arrStr[0]] = changeTwoDecimal(Number(accumulateAmt[arrStr[0]]) + Number(b.feeAmt));
			$(trObj).append("<td><input type='text' onkeyup='$.clearNoNum(this);' onblur='$.onBlurReplace(this);' value='"+accumulateAmt[arrStr[0]]+"' name='cglFeeAmt' id='"+arrStr[0]+"'/></td>");//收益金额
		}else{
			//当月之后
			$(trObj).append("<td><input type='text' onkeyup='$.clearNoNum(this);' onblur='$.onBlurReplace(this);' value='"+b.feeAmt+"' name='cglFeeAmt' id='"+arrStr[0]+"'/></td>");//收益金额
		}
		$("#th").before(trObj);
	}
}

//进入页面
function inPage(paramD){
	var p = paramD;
	
	//1.计算月份差
	var monthsNum = cntMonths(p.startDate,p.endDate);
	var startMonth = parseInt(p.startDate.split('-')[1],10);
	var monthArr = new Array();
	
	//2.开始年份 及 相隔年份数
	var startYear = p.startDate.split('-')[0];
	var yearNum = parseInt(p.endDate.split('-')[0]) - parseInt(p.startDate.split('-')[0]);
	var yearArr = new Array();
	
	//3.循环 年列表{2014,2015}，月列表{10,11,12,01,02,03,04}
	var times = monthsNum;
	for(var i=0;i<=yearNum;i++)
	{
		yearArr.push(parseInt(startYear)+i);
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
	
	//4.计算subId+avgAmt 平均金额
	var avgAmtMap = new HashMap();
	avgAmtMap = getAvgAmt(p.paramList,monthsNum);
	var amtMap = new HashMap();
	amtMap = getAmtMap(p.paramList);
	codeAmtMap = amtMap;
	
	//5.拼目标bean列表
	var targetList = new Array();
	for(var i=0;i<yearArr.length;i++){//年份
		var a = new Array();
		a = monthArr[i];
		for(var j=0;j<a.length;j++){//月份	
			
			if(i==(yearArr.length-1)&&j ==(a.length-1))
			{//如果是最后一年的最后一个月
				var avgKey = avgAmtMap.keys();
				avgKey.sort();
				var penaltySum = 0;
				for(var k=0;k<avgKey.length;k++){
					var f = parseFloat(amtMap.get(avgKey[k])); // 单项物料金额(由合同确认页面传过来的该物料的确认部分金额之和)
					var a1 = 0;
					var g = parseFloat(avgAmtMap.get(avgKey[k])*(monthsNum-1));//除了最后一个月之外前面所有月份的受益金额之和
					if(feePenalty != 0 && !$.isBlank(feePenalty)){
						var penalty = 0; //罚金
						var d = amtMap.get(avgKey[k]+"deductFlag"); // 是否可抵扣
						var c = parseFloat(amtMap.get(avgKey[k]+"cntTrAmt")); // 单行税额
						var cntAmt = parseFloat(amtMap.get(avgKey[k]+"cntAmt")); // 单行不含税金额
						//罚金中税的部分配到单个物料行上的值
						//约定罚金*物料的税额/合同总金额
						var cFj = changeTwoDecimal(parseFloat(feePenalty)*c/(parseFloat(feePenalty)+feeAmt));
						//罚金中不含税的部分配到单个物料行上的值
						//约定罚金*物料的不含税金额/合同总金额
						var penaltyTmp = changeTwoDecimal(parseFloat(feePenalty)*cntAmt/(parseFloat(feePenalty)+feeAmt));
						
						if (k==avgKey.length-1) {
							//最后一项物料的罚金为总罚金减去已经分配到前面物料的罚金之和（如果是不可抵扣税，则减去税的部分）
							penalty = parseFloat(feePenalty) - parseFloat(penaltySum)-parseFloat(d == 'N'? 0:cFj);
						} else {
							//罚金（如果是不可抵扣税，则加上税的部分）
							penalty = parseFloat(penaltyTmp) + parseFloat(d == 'N'? cFj:0);
							penaltySum += (parseFloat(penaltyTmp)+parseFloat(cFj));
						}
						//受益金额=由合同确认页面传过来的该物料的确认部分金额之和-最后一个月之前全部受益金额之和+分配到物料的罚金
						a1 = Number(f).sub(g).add(penalty);
					}else{
						a1 = Number(f).sub(g);
					}
					
					a1 = changeTwoDecimal(a1);
					var bean = new cntFee(avgKey[k],"",a1,a1,yearArr[i],a[j]);
					//subId,feeDate,calAmt,feeAmt
					targetList.push(bean);
				}
			}else
			{//一般的月份
				var avgKey = avgAmtMap.keys();
				avgKey.sort();
				for(var k=0;k<avgKey.length;k++){
					var bean = new cntFee(avgKey[k],"",avgAmtMap.get(avgKey[k]),avgAmtMap.get(avgKey[k]),yearArr[i],a[j]);
					//subId,feeDate,calAmt,feeAmt
					targetList.push(bean);
				}
			}
		}
	}
	
	var accumulateAmt = new Array(); // 当月前的累积受益金额
	for(var i=0;i<=p.paramList.length;i++){
		accumulateAmt[i] = 0;
	}
	//6.循环打印列表
	for(var i=0;i<targetList.length;i++){
		var b = targetList[i];
		var trObj = createTr();
		var feeYyyymm = b.year+b.month+"";
		var str = b.subId+"";
		var arrStr = new Array();
		arrStr = str.split("_");//subId+feeDeptName+物料编码+物料名称+核算码+specialName + referenceName;
		$(trObj).append("<td id='year' rowspan='1'>"+b.year+"</td>");
		$(trObj).append("<td id='month' rowspan='1'>"+b.month+"</td>");
		$(trObj).append("<td><input type='hidden' value='"+feeYyyymm+"' name='feeYyyymm'/><input type='hidden' value='"+arrStr[0]+"' name='subId'/>"+arrStr[0]+"</td>");
		$(trObj).append("<td>"+arrStr[1]+"</td>");//费用承担部门
		$(trObj).append("<td>"+arrStr[2]+arrStr[3]+"</td>");//物料编码及名称
		$(trObj).append("<td>"+arrStr[4]+"</td>");//核算码
		$(trObj).append("<td>"+arrStr[5]+"</td>");//专项
		$(trObj).append("<td>"+arrStr[6]+"</td>");//参考
		$(trObj).append("<td><input type='hidden' value='"+b.calAmt+"' name='cglCalAmt' id='cglAmt"+arrStr[0]+"'/>"+b.calAmt+"</td>");//测算金额
		if((nowYear > b.year) || (nowYear == b.year && nowMonth > b.month)){
			//当月之前
			$(trObj).append("<td><input type='hidden' value='0' name='cglFeeAmt' id='"+arrStr[0]+"'/>0</td>");//收益金额
			accumulateAmt[arrStr[0]] = changeTwoDecimal(Number(accumulateAmt[arrStr[0]]) + Number(b.feeAmt));
		}else if(nowYear == b.year && nowMonth == b.month){
			//当月
			accumulateAmt[arrStr[0]] = changeTwoDecimal(Number(accumulateAmt[arrStr[0]]) + Number(b.feeAmt));
			$(trObj).append("<td><input type='text' onkeyup='$.clearNoNum(this);' onblur='$.onBlurReplace(this);' value='"+accumulateAmt[arrStr[0]]+"' name='cglFeeAmt' id='"+arrStr[0]+"'/></td>");//收益金额
		}else{
			//当月之后
			$(trObj).append("<td><input type='text' onkeyup='$.clearNoNum(this);' onblur='$.onBlurReplace(this);' value='"+b.feeAmt+"' name='cglFeeAmt' id='"+arrStr[0]+"'/></td>");//收益金额
		}
		$("#th").before(trObj);
	}
}


//合并单元格
function merge(){
	var years = new Array(); 
	var targetY = $();
	$("#targetT").find("td[id='year']").each(
			function(){
				var t = $(this).text()+"";
				if(have(years,t)>-1){
					var first = targetY;
					var k = first.attr("rowspan");
					first.attr("rowspan",parseInt(k)+1);
					$(this).remove();
				}else{
					years.push(t);
					targetY = $(this);
				}
			});
	var months = ""; 
	var targetM = $();
	$("#targetT").find("td[id='month']").each(
			function(){
				var t = $(this).text();
				if(months == t){
						var first = targetM;
						var k = first.attr("rowspan");
						first.attr("rowspan",parseInt(k)+1);
						$(this).remove();
				}else{
					months=t;
					targetM = $(this);
				}
				
			});
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

//创建tr
function createTr()
{
	var _tr = document.createElement("tr");
	return _tr;
}

//计算subId+avgAmt ,返回Map
function getAvgAmt(paramList,monthsNum){
	var avgMap = new HashMap();
	for(var i=0;i<paramList.length;i++){
		var keyStr = paramList[i].subId+"_"+paramList[i].strBuf;
		var avgAmt = changeTwoDecimal(parseFloat(paramList[i].amt)/monthsNum);
		avgMap.put(keyStr,avgAmt);
	}
	return avgMap;
}

function getAmtMap(paramList){
	var amtMap = new HashMap();
	for(var i=0;i<paramList.length;i++){
		var keyStr = paramList[i].subId+"_"+paramList[i].strBuf;
		var amt = parseFloat(paramList[i].amt);
		
		var deductFlag = paramList[i].deductFlag;
		var cntTrAmt = paramList[i].cntTrAmt;
		var cntAmt = paramList[i].cntAmt;
		amtMap.put(keyStr,amt);
		amtMap.put(keyStr+"deductFlag",deductFlag);
		amtMap.put(keyStr+"cntTrAmt",cntTrAmt);
		amtMap.put(keyStr+"cntAmt",cntAmt);
	}
	return amtMap;
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

function HashMap(){ 
	/**Map大小**/ 
	var size=0; 
	/**对象**/ 
	var entry=new Object(); 
	/**Map的存put方法**/ 
	this.put=function(key,value){ 
	if(!this.containsKey(key)){ 
	size++; 
	entry[key]=value; 
	} 
	} ;
	/**Map取get方法**/ 
	this.get=function(key){ 
	return this.containsKey(key) ? entry[key] : null; 
	} ;
	/**Map删除remove方法**/ 
	this.remove=function(key){ 
	if(this.containsKey(key) && ( delete entry[key] )){ 
	size--; 
	} 
	} ;
	/**是否包含Key**/ 
	this.containsKey= function (key){ 
	return (key in entry); 
	} ;
	/**是否包含Value**/ 
	this.containsValue=function(value){ 
	for(var prop in entry) 
	{ 
	if(entry[prop]==value){ 
	return true; 
	} 
	} 
	return false; 
	} ;
	/**所有的Value**/ 
	this.values=function(){ 
	var values=new Array(); 
	for(var prop in entry) 
	{ 
	values.push(entry[prop]); 
	} 
	return values; 
	} ;
	/**所有的 Key**/ 
	this.keys=function(){ 
	var keys=new Array(); 
	for(var prop in entry) 
	{ 
	keys.push(prop); 
	} 
	return keys; 
	} ;
	/**Map size**/ 
	this.size=function(){ 
	return size; 
	} ;
	/**清空Map**/ 
	this.clear=function(){ 
	size=0; 
	entry=new Object(); 
	} ;
	
} 

//减法  
Number.prototype.sub = function (arg){  
    return this.add(-arg);  
} ;
//加法  
Number.prototype.add = function(arg){  
    var r1,r2,m;  
    try{r1=this.toString().split(".")[1].length;}catch(e){r1=0;}  
    try{r2=arg.toString().split(".")[1].length;}catch(e){r2=0;}  
    m=Math.pow(10,Math.max(r1,r2))  ;
    return (this*m+arg*m)/m; 
} ;

function have(arr,testZi){
    
    for(var i=0;i<arr.length;i++){
            if(testZi===arr[i]){
                    return i;
            }
    }
    return -1;
};

function getPenalty(f){
	if(feePenalty != 0){
		var ratio = f/feeAmt; // 单项物料金额所占所有物料金额的比例
		return changeTwoDecimal(feePenalty*ratio);
	}
	return 0;
}
</script>
</head>
<body>
<form action="" method="post"  id="feeTypeForm">	
<div id="targetDiv">
	<table id="targetT" class="tableList">
		<tr>
			<th width="10%">年份</th>
			<th width="10%">月份</th>
			<th width="10%">序号</th>
			<th width="10%">费用承担部门</th>
			<th width="10%">物料名称</th>
			<th width="10%">核算码</th>
			<th width="10%">参考</th>
			<th width="10%">专项</th>
			<th width="10%">测算金额</th>
			<th width="20%">受益金额</th>
		</tr>
		<tr id="th"></tr>
	</table>
</div>	
	<br/>
	<div>
			<input type="button"  value="确认"  onclick="getReturnValue();" />
			<input type="button"  value="关闭"  onclick="art.dialog.close();" />
	</div>
	
</form>
</body>
</html>