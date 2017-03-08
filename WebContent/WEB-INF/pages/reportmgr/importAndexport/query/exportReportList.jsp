<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入导出查询</title>
<script type="text/javascript">
//全局变量
var dataFlag = "${imExCommonBean.dataFlag}";
//页面初始化执行方法
function pageInit()
{
	var lastPage = '${lastPageLink}';		//跳转至该导入导出页，之前的链接地址
// 	console.info(lastPage);
	App.jqueryAutocomplete();
	if( !dataFlag )
	{
		dataFlag = "";
	}
	$("#dataFlag").val(dataFlag);
	$("#dataFlag").combobox();
	$("#imOutportType").combobox();
	//设置时间插件
	//如果[提交时间]有初始值，则设置该查询条件的初始值
	if("" != '${imExCommonBean.instDate}'){
		$( "#instDate" ).val('${imExCommonBean.instDate}');
	}
	$( "#instDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}
//重置按钮功能
function resetAll() {
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

function exportReportFileLoad( taskId )
{
	window.location.href = "<%=request.getContextPath()%>/reportmgr/importAndexport/query/exportReportFileLoad.do?<%=WebConsts.FUNC_ID_KEY %>=06010101&taskId=" + taskId;
}

//查询导入任务的详情功能(参数：导入任务Id)
function getImportTaskDetail(taskId){
	var form = $("#exportReportForm")[0];
	//添加查询条件[批次号batchId]的处理，用于"返回"按钮链接的控制
	var batchId = '${batchId}';
	var url = "";
	if(""!=batchId){
		if('${empty lvl1ReturnLink}' == 'true'){
			url = "<%=request.getContextPath()%>/reportmgr/importAndexport/query/getImportFileDetailInfo.do?<%=WebConsts.FUNC_ID_KEY %>=06010102&taskId="+taskId+"&batchId="+batchId+"&lvl1ReturnLink="+'${lastPageLink}';
		}else{
			//如果存在一级返回链接"lvl1ReturnLink",则将该一级返回链接作为参数传递到导入任务的详情页面(当返回导入列表时，再作为参数返回，用于导入列表页面的"返回"按钮链接)
			url = "<%=request.getContextPath()%>/reportmgr/importAndexport/query/getImportFileDetailInfo.do?<%=WebConsts.FUNC_ID_KEY %>=06010102&taskId="+taskId+"&batchId="+batchId+"&lvl1ReturnLink="+'${lvl1ReturnLink}';
		}
	}else{
		url = "<%=request.getContextPath()%>/reportmgr/importAndexport/query/getImportFileDetailInfo.do?<%=WebConsts.FUNC_ID_KEY %>=06010102&taskId="+taskId+"&lvl1ReturnLink="+'${lastPageLink}';
	}
	form.action=url;
	App.submit(form);
}

//页面加载时执行
$(function(){
	
	//1.截取指定长度字符(用于内容太长不便于展示时使用，使用的地方需要添加class='cutValue')
	var cutedVal = "";
	$(".cutValue").each(function(){
		cutedVal = $(this).html();
		if(cutedVal.length > 18){
			$(this).html(cutedVal.substr(0,18)+"......");
		}
	});
})


</script>
</head>

<body>
<p:authFunc funcArray="060101,06010101,06010102"/>
<form action="" method="post" id="exportReportForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				导入导出查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">查询类型</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="imOutportType" name="imOutportType" >
						<option value="1" <c:if test="${imExCommonBean.imOutportType == '1'}">selected="selected"</c:if>>导出数据</option>
						<option value="0" <c:if test="${imExCommonBean.imOutportType == '0'}">selected="selected"</c:if>>导入数据</option>
					</select>
				</div>
			</td>
			<td class="tdLeft"  width="20%">任务状态</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="dataFlag" name="dataFlag" >
						<option value="">请选择</option>
						<option value="00" <c:if test="${imExCommonBean.dataFlag eq '00'}">selected="selected"</c:if>>待处理</option>
						<option value="01" <c:if test="${imExCommonBean.dataFlag eq '01'}">selected="selected"</c:if>>处理中</option>
						<option value="02" <c:if test="${imExCommonBean.dataFlag eq '02'}">selected="selected"</c:if>>处理失败</option>
						<option value="03" <c:if test="${imExCommonBean.dataFlag eq '03'}">selected="selected"</c:if>>处理完成</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"  width="20%">提交时间</td>
			<td class="tdRight" width="30%" colspan="3">
				<input type="text" id="instDate" name="instDate" value="${exportReport.instDate}" readonly="readonly" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdBottom tdWhite">
				<p:button funcId="060101" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
<%-- 				<c:if test="${! empty lvl1ReturnLink}"> --%>
<%-- 					<input type="button" value="返回" onclick="backToLastPage('${lvl1ReturnLink}');"> --%>
<%-- 				</c:if> --%>
<%-- 				<c:if test="${empty lvl1ReturnLink}"> --%>
<%-- 					<c:if test="${empty lastPageLink }"> --%>
<!-- 						<input type="button" value="返回" onclick="backToLastPage('');"> -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${!empty lastPageLink }"> --%>
<%-- 						<input type="button" value="返回" onclick="backToLastPage('${lastPageLink}');"> --%>
<%-- 					</c:if> --%>
					
<%-- 				</c:if> --%>
			</td>
		</tr>
		
	</table>
	
	<br/>
	
	<table class="tableList">
		<!-- 表头行 -->
		<tr>
			<c:if test="${imExCommonBean.imOutportType == '1'}">
				<th width="16%" title="描述：即导入的Excel类型">导出任务描述</th>
			</c:if>
			<c:if test="${imExCommonBean.imOutportType == '0'}">
				<th width="16%" title="描述：即导入的Excel类型">导入任务描述</th>
			</c:if>
			
			<th width="10%">任务状态</th>
			<th width="10%">待处理排名</th>
			<th width="10%">提交柜员</th>
			<th width="10%">提交时间</th>
			<th width="10%">处理时间</th>
			
			<c:if test="${imExCommonBean.imOutportType == '1'}">
				<th width="19%">操作备注</th>
				<th width="15%">结果文件</th>
			</c:if>
			<c:if test="${imExCommonBean.imOutportType == '0'}">
				<th width="19%">错误数据</th>
				<th width="15%">导入详情</th>
			</c:if>
		</tr>
		
		<c:forEach items="${getImporExporTaskList}" var="exportReport">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
				<!-- 模板类型值：根据【Task-Control-Upload-Select表进行关联】 -->
				<c:if test="${imExCommonBean.imOutportType == '1'}">
					<td title="导出任务ID：${exportReport.taskId}" style="text-align: center">${exportReport.taskDesc}</td>
				</c:if>
				<c:if test="${imExCommonBean.imOutportType == '0'}">
					<td title="导入批次号：${exportReport.taskBatchNo},导入任务ID:${exportReport.taskId}" style="text-align: center">${exportReport.taskDesc}</td>
				</c:if>
				<td style="text-align: center">${exportReport.dataFlagName}</td>
				<td style="text-align: center">${exportReport.waitSeq}</td>
				<td style="text-align: center">${exportReport.instOper}</td>
				<td style="text-align: center"<c:if test="${not empty exportReport.instDate }">title="${exportReport.instDate}&nbsp;${exportReport.instTime}"</c:if> >${exportReport.instDate}<br/>${exportReport.instTime}</td>
				<td style="text-align: center"<c:if test="${not empty exportReport.procDate }">title="${exportReport.procDate}&nbsp;${exportReport.procTime}"</c:if> >${exportReport.procDate}<br/>${exportReport.procTime}</td>
				<c:if test="${imExCommonBean.imOutportType == '1'}">
					<td class="cutValue" title="${exportReport.procMemo}" style="text-align: center;">${exportReport.procMemo}</td>
				</c:if>
				<!-- 当前导入任务错误行数值：根据【根据导入任务去GroupBy统计】 -->
				<c:if test="${imExCommonBean.imOutportType == '0'}">
					<td style="text-align: center">${exportReport.errRowCount}行</td>
				</c:if>
				<td style="text-align: center;" >
					<c:if test="${imExCommonBean.imOutportType == '1'}">
						<c:if test="${ exportReport.dataFlag == '03' && not empty exportReport.destFile  }">
							<a href="#" onclick="exportReportFileLoad('${exportReport.taskId}');">下载</a>
						</c:if>
					</c:if>
					<c:if test="${imExCommonBean.imOutportType == '0'}">
						<input type="button" value="明细" onclick="getImportTaskDetail('${exportReport.taskId}')">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty getImporExporTaskList}">
			<tr><td style="text-align: center;" colspan="8"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>