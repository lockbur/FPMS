<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础预算模板下载</title>
<script type="text/javascript">

function pageInit(){
	App.jqueryAutocomplete();
}


//【测试使用】
function toBgtSumImportPage(){
	var form = $("#exportBudgetForm")[0];
	form.action = '<%=request.getContextPath()%>/budget/budgetSum/toBgtSumImportPage.do?<%=WebConsts.FUNC_ID_KEY %>=02010703';
	App.submit(form);
}


//Ajax验证是否存在监控指标未维护(并让用户决定是否继续导出操作)
function ajaxValidateExcelInfos(dataAttr){
	var data = {};
	var budgetAttrType = dataAttr;
	data['dataAttr'] =  dataAttr;
	App.ajaxSubmit("budget/budgetplan/ajaxValidateExcelInfos.do?<%=WebConsts.FUNC_ID_KEY %>=02010702",
			{data:data,async : false},
			function(data){
				var count = data.countResult;	//查询到Mont_code为null的条目数(即未维护状态)
				if(count>0){
					//提示用户未维护状态，让用户决定是否继续进行导出操作
					$("<div><font color='red'><div id='valiTips' style='text-align:left'>提示 ：</div> "+
										"<br/>有【"+count+"】条"+(budgetAttrType=='0'?'资产':'费用')+"类物料未维护到监控指标！</font></div>").dialog({
						resizable: false,
						width: 350,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						title: "监控指标全维护校验",
						buttons:[
						         {
						        	 text:"继续导出",
										click:function(){
											//调用Excel导出组件，并将任务添加到线程池中排序执行
											exportBudgetBasicTemp(dataAttr);
											$(this).dialog("close");
										}   
						         },
								{
									text:"取消导出",
										click:function(){
											$(this).dialog("close");
										}
								}
						]
					});
				}else{
					//监控指标无"未维护状态"条目时，直接调用Excel导出操作
					exportBudgetBasicTemp(dataAttr);
				}
			}
	);
}

//导出基础预算模板操作(并让用户决定是否跳转到导出Excel的下载页面)
function exportBudgetBasicTemp(dataAttr){
	var data = {};
	data['dataAttr'] =  dataAttr;
	App.ajaxSubmit("budget/budgetplan/budgetTmpExport.do?<%=WebConsts.FUNC_ID_KEY%>=02010701",
			{data:data,async : false},
			function(data){
				flag = data.pass;	//获取下载数据成功标识
				var exportTaskId = data.exportTaskId;
				if(flag){
					$("<div>Excel导出流水号【 "+exportTaskId+" 】<br\> 已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 350,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						title: "跳转至下载页面",
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
							        	 	var form = document.forms[0];
							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&taskId='+exportTaskId;
											App.submit(form);
										}   
						         },
								{
									text:"取消",
										click:function(){
											$(this).dialog("close");
										}
								}
						]
					});
				}
				else
				{
					App.notyError("添加导出数据有异常，请重试！");
				}
			});
}

</script>
</head>
<body>
<p:authFunc funcArray="060101,02010701,02010702"/>
<form action="" method="post" id="exportBudgetForm" encType="multipart/form-data">
<p:token/>
	<table id="basicBudgetTmpDownloadTable">
		<tr>
			<th colspan="2">基础预算模板下载</th>
		</tr>
        <tr>
			<td class="tdLeft">资产类预算</td>
			<td class="tdRight" >
				<input type="button" value="基础模板下载" onclick="ajaxValidateExcelInfos('0')"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td class="tdLeft">费用类预算</td>
			<td class="tdRight" >
				<input type="button" value="基础模板下载" onclick="ajaxValidateExcelInfos('1')"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td class="tdLeft">【测试-预算汇总的】</td>
			<td class="tdRight">
				<input id="testToBgtSumImpPage" type="button" value="跳转至预算汇总数据导入Page" onclick="toBgtSumImportPage()">
			</td>
		</tr>
	</table>
</form>
</body>
</html>