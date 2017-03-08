<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOL日志下载</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	
	//设置时间插件
 	$("#startDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	}); 
 	
 	//给付款日期默认值
	var nDate = "${bean.startDate}";
	var date = eval('new Date(' + nDate.replace(/\d+(?=-[^-]+$)/, function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
 	/* var year = date.getFullYear();
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
	$("#startDate").val(dd);
	$("#endDate").val(dd); */
	date.addDays(4);
	$("#endDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    maxDate:date
	});  
}

function downloadFile(){
	//提交前调用
	if(!App.valid("#queryForm")){
		return;
	}
	//校验开始日期只能在结束日期之前
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	if(startDate>endDate){
		App.notyError("开始日期不能在结束日期之后!");
		return false;
	}
	var form = $("#queryForm")[0];
	var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/downloadLog.do?<%=WebConsts.FUNC_ID_KEY%>=08090301';
	form.action = url ;
	form.submit();
}
//查询列表
function logList()
{
	if(!App.valid("#queryForm")){
		return;
	}
	//校验开始日期只能在结束日期之前
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	if(startDate>endDate){
		App.notyError("开始日期不能在结束日期之后!");
		return false;
	}
	var form = $("#queryForm")[0];
	var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/logdownload.do?<%=WebConsts.FUNC_ID_KEY%>=080903';
	form.action = url ;
	form.submit();
}

//日期加4
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
//付款日期改变则对应改变暂收日期
function changeDate(obj){
	var payDate = $(obj).val();
	var suspenseDate = $( "#endDate").val();
	$( "#endDate").val('');
	var dateStr = payDate.replace(/-/g,'/');
	var date = new Date(dateStr);
	date.addDays(4);
	$( "#endDate" ).removeClass('hasDatepicker');
	$( "#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    maxDate:date
	}); 
	$( "#endDate" ).focus();
}
</script>
</head>

<body>
<p:authFunc funcArray="080903"/>
<form method="post" id="queryForm" action="">
	<table class="tableList">
		<tr>
			<th colspan="4">系统日志下载</th>
		</tr>
		 <tr>
			<td class="tdLeft">开始日期</td>
			<td class="tdRight">
				<input type="text" id="startDate" value="${bean.startDate}" class="base-input-text" name="startDate" valid  onchange="changeDate(this);"/>
			</td>
			<td class="tdLeft">结束日期</td>
			<td class="tdRight">
				<input type="text" id="endDate" value="${bean.endDate}" class="base-input-text" name="endDate" valid />
			</td>
		</tr> 
		<tr>
			<td colspan="4"  style="text-align: center;">
				<input type="button" value="枚举" onclick="logList();"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>