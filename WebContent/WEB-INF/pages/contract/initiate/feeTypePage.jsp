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
<title>费用类型选择</title>
<base target="_self">

<script type="text/javascript">
//subId 受益总额Map
codeAmtMap=  new HashMap();
//当前日期
var nowDate = new Date();
var nowYear = nowDate.getFullYear();
var nowMonth = nowDate.getUTCMonth()+1;
var nextYear = nowYear;
var nextMonth = nowMonth+1;
//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	//获取参数{subId:序号,amt：测算总金额,strBuf：展示名}
	var paramD = window.parent.dialogArguments.paramData;
	var table = window.parent.dialogArguments.targetT;
	var confirmFlag = window.parent.dialogArguments.confirmFlag;
	var feeStartFlag = window.parent.dialogArguments.feeStartFlag;
	var subIdLength = window.parent.dialogArguments.subIdLength;
	var hasModifyFee = window.parent.dialogArguments.hasModifyFee;
	if($.isBlank(table)){
		inPage(paramD);
		merge();
	}else{
		$("#targetDiv").empty().append(table);
		if(nowMonth == 12){
			nextMonth = 1;
			nextYear += 1;
		}
		//合同修改时，如果预提待摊起始日期小于当前月，则展示之前的受益金额，并另外生成一列新的受益金额
	/* 	if(feeStartFlag == 'true' && hasModifyFee != '0'){
			var firstTr = $("#targetT tr:first");
			$(firstTr).find("th:last").attr("width","10%");
			$(firstTr).find("th:last").text("原受益金额");
			$(firstTr).append("<th width='10%'>受益金额</th>");
			$("#targetDiv").append("<input  type='hidden' value='0' id='hasModifyFee' />");
			var tdAccAmt = new Array();
			for(var i=0;i<subIdLength;i++){
				tdAccAmt[i] = 0;
			} */
			$("#targetT tr:gt(0)").each(function(){
				var lastTd = $(this).find("td:last");
				$(lastTd).text($(lastTd).find("input").val());
				var trYear = Number($(this).find("td[id='year']").text());
				var trMonth = Number($(this).find("td[id='month']").text());
				var tdYear = $(this).find("td:first").text(); //年份
				var tdMonth = $(this).find("td").eq(1).text(); //月份
				var tdSubId = Number($(this).find("td").eq(2).text()); //序号
				var tdCglCalAmt = $(this).find("input[name='cglCalAmt']").val(); //测算金额
				if((nowYear > tdYear) || (nowYear == tdYear && nowMonth > tdMonth)){
					//当月之前
					tdAccAmt[tdSubId] = changeTwoDecimal(Number(tdAccAmt[tdSubId]) + Number(tdCglCalAmt));
					$(this).append("<td><input type='hidden' value='0' name='cglFeeAmt' id='"+tdSubId+"' />0</td>");//受益金额
				}else if(nowYear == tdYear && nowMonth == tdMonth){
					//当月
					tdAccAmt[tdSubId] = changeTwoDecimal(Number(tdAccAmt[tdSubId]) + Number(tdCglCalAmt));
					$(this).append("<td><input type='text' value='"+tdAccAmt[tdSubId]+"' name='cglFeeAmt' id='"+tdSubId+"'/></td>");//受益金额
				}else{
					//当月之后
					$(this).append("<td><input type='text' value='"+tdCglCalAmt+"' name='cglFeeAmt' id='"+tdSubId+"'/></td>");//受益金额
				}
			});
		}
		//合同变更时当月和下月的预提待摊受益金额不可修改
		if(!$.isBlank(confirmFlag)){
			$("#targetT tr:gt(0)").each(function(){
				var trYear = Number($(this).find("td[id='year']").text());
				var trMonth = Number($(this).find("td[id='month']").text());
				if((nowYear > trYear) || (nowYear == trYear && nowMonth >= trMonth) || (nextYear == trYear && nextMonth == trMonth)){
					$(this).find("input[name='cglFeeAmt']").attr("readonly","readonly");
				}
			});			
		}
	}
}

//设置页面关闭时返回值
function getReturnValue(){
	if(!checkTotalSum()){return false;}
	fresh();
	var T = $("#targetDiv").clone();
	window.returnValue = T.html();
	window.close();
	return;
}
//校验受益金额总和与测算金额总和是否一致
function checkTotalSum(){

	var codeKeys = codeAmtMap.keys();
 	for(var p in codeKeys)
	{
 		var str = codeKeys[p];
	    var findId = str.split("_")[0];
		var total=0;
		$("#targetT").find("input[id="+findId+"]").each(function(){
		 	total = (total*1000+ parseFloat($(this).val())*1000)/1000; 
		});
		var left = parseFloat(codeAmtMap.get(codeKeys[p]))-total>0?parseFloat(codeAmtMap.get(codeKeys[p]))-total:total-parseFloat(codeAmtMap.get(codeKeys[p]));
	    if(total != codeAmtMap.get(codeKeys[p])){
	    	App.notyError("序号为【"+findId+"】的测算数据总和与受益数据总和不相等！测算金额总和为【"+codeAmtMap.get(codeKeys[p])
	    			+"】收益金额总和为【"+total+"】相差金额为【"+left+"】");
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
				for(var k=0;k<avgKey.length;k++){
					var f = parseFloat(amtMap.get(avgKey[k]));
					var g = parseFloat(avgAmtMap.get(avgKey[k])*(monthsNum-1));
					var a1 = Number(f).sub(g);
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
		$(trObj).append("<td><input type='hidden' value='"+b.calAmt+"' name='cglCalAmt'/>"+b.calAmt+"</td>");//测算金额
		if((nowYear > b.year) || (nowYear == b.year && nowMonth > b.month)){
			//当月之前
			$(trObj).append("<td><input type='hidden' value='0' name='cglFeeAmt' id='"+arrStr[0]+"'/>0</td>");//收益金额
			accumulateAmt[arrStr[0]] = changeTwoDecimal(Number(accumulateAmt[arrStr[0]]) + Number(b.feeAmt));
		}else if(nowYear == b.year && nowMonth == b.month){
			//当月
			accumulateAmt[arrStr[0]] = changeTwoDecimal(Number(accumulateAmt[arrStr[0]]) + Number(b.feeAmt));
			$(trObj).append("<td><input type='text' value='"+accumulateAmt[arrStr[0]]+"' name='cglFeeAmt' id='"+arrStr[0]+"'/></td>");//收益金额
		}else{
			//当月之后
			$(trObj).append("<td><input type='text' value='"+b.feeAmt+"' name='cglFeeAmt' id='"+arrStr[0]+"'/></td>");//收益金额
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
		amtMap.put(keyStr,amt);
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

//保留两位小数
changeTwoDecimal=function (floatvar)
{
var f_x = parseFloat(floatvar);
if (isNaN(f_x))
{
alert('function:changeTwoDecimal->parameter error');
return false;
}
var f_x = Math.round(f_x*100)/100;
var s_x = f_x.toString();
var pos_decimal = s_x.indexOf('.');
if (pos_decimal < 0)
{
pos_decimal = s_x.length;
s_x += '.';
}
while (s_x.length <= pos_decimal + 2)
{
s_x += '0';
}
return s_x;
} ;

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


</script>
</head>
<body>
<form action="" method="post"  id="feeTypeForm">	
<div id="targetDiv">
	<table id="targetT">
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
			<input type="button"  value="返回"  onclick="window.close();" />
	</div>
	
</form>
</body>
</html>