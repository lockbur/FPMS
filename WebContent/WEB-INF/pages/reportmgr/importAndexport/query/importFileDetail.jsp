<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入任务详情页面</title>

<style type="text/css">
	/* 文件描述框样式设置为不可改变大小 */
	textarea{
		resize : none;
	}
</style>
	
<script type="text/javascript">
	//页面初始化执行加载
	function pageInit(){
		App.jqueryAutocomplete();
// 	 	$("#fileType").combobox();
	}
	
	function splitSendAndReceivedValue(obj,strValue){
		var valueArray = strValue.split(';');
		//组合TD中的最终字符串(包括内容+html标签<br/>)
		var resultStr = "";
		for(var i=0;i<valueArray.length;i++){
			resultStr += valueArray[i]+'<br/>';
		}
		//console.info(resultStr);
		//往TD中写入最终字符串
		$(obj).html(resultStr);
// 		console.info("原值："+strValue);
// 		console.info("更改值："+resultStr);
	}


	$(function(){
		$(".splitValue").each(function(){
			splitSendAndReceivedValue(this,$(this).html());		
		});
	})
	
function downloadFile(filePath,fileName){
		var  url = '<%=request.getContextPath()%>/fileUtils/fileDownload.do?filePath='+filePath+'&fileName='+fileName;
		location.href = url;
}
</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form id="downloadForm" method="post"></form>
<form action="" method="post" id="importFileDetailForm">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				导入任务详情
				<input type="hidden" value="${importTaskDetail.taskId}">
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">导入任务ID</td>
			<td class="tdRight" width="30%">
				${importTaskDetail.taskId}
<%-- 				${importTaskDetail.taskParams} --%>
<%-- 				${importTaskDetail.taskParams[impBatch]} --%>
			</td>
			<td class="tdLeft">任务状态</td>
			<td class="tdRight">
				<c:if test="${importTaskDetail.dataFlag == '00'}">待处理</c:if>
				<c:if test="${importTaskDetail.dataFlag == '01'}">处理中</c:if>
				<c:if test="${importTaskDetail.dataFlag == '02'}">处理失败</c:if>
				<c:if test="${importTaskDetail.dataFlag == '03'}">处理完成</c:if>
				<c:if test="${importTaskDetail.dataFlag == '04'}">部分完成</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">提交柜员</td>
			<td class="tdRight">
				${importTaskDetail.instOper}
			</td>
			<td class="tdLeft">待处理排名</td>
			<td class="tdRight">
				${importTaskDetail.waitSeq}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">提交时间</td>
			<td class="tdRight">
				${importTaskDetail.instDate} - ${importTaskDetail.instTime}
			</td>
			<td class="tdLeft">处理时间</td>
			<td class="tdRight" colspan="3">
				${importTaskDetail.procDate} - ${importTaskDetail.procTime}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">任务描述</td>
			<td class="tdRight" colspan="3">
				${importTaskDetail.taskDesc}
			</td>
<!-- 			<td class="tdLeft">其他</td> -->
<!-- 			<td class="tdRight"> -->
<!-- 			</td> -->
		</tr>
		<tr>
			<td class="tdLeft">
				导入备注
<!-- 				<br>(<span id="fileDescLogSpan">0/100</span>) -->
			</td>
			<td class="splitValue , tdRight" colspan="3" style="text-align: left;">
<%-- 				<textarea valid id="fileDesc" name="fileDesc" rows="10" cols="3"  class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,100,'fileDescLogSpan')">${importTaskDetail.procMemo}</textarea> --%>
				${importTaskDetail.procMemo}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">导入日志文件</td>
			<td class="tdRight" colspan="3"> 
				<c:if test="${!empty logFile}">
				log文件：<a href="#" onclick="downloadFile('${logFilePath}','${logFile }')">${logFile}</a>
				</c:if>
				<c:if test="${!empty badFile}">
					<br>
					bad文件：<a href="#" onclick="downloadFile('${badFilePath}','${badFile }')">${badFile}</a>
				</c:if>
			</td>
		</tr>		
		<tr>
			<td style="text-align: center;" colspan="4" class="tdWhite">
				<!-- 如果该明细查看请求没有携带批次号batchId参数，则返回全部导入任务的任务列表 -->
				<c:if test="${empty batchId }">
					<input type="button" value="返回" onclick="backToLastPage('getExportReportList.do?VISIT_FUNC_ID=060101&imOutportType=0');">
				</c:if>
				<!-- 如果该明细查看请求有携带批次号batchId参数，则返回该导入批次的任务列表(根据批次号精细查询任务列表) -->
				<c:if test="${!empty batchId }">
					<input type="button" value="返回" onclick="backToLastPage('getExportReportList.do?VISIT_FUNC_ID=060101&imOutportType=0&queryByOrg1=T&batchId='+'${batchId}'+'&lvl1ReturnLink='+'${lvl1ReturnLink}');">
				</c:if>
			</td>
		</tr>
	</table>
	
<!-- 	<br/> -->
<!-- 	<table class="tableList"> -->
<!-- 		<tr class="collspan-control"> -->
<!-- 			<th width="10%">序号</th> -->
<!-- 			<th width="13%">Excel模板</th> -->
<!-- 			<th width="13%">Sheet名称</th> -->
<!-- 			<th width="10%">行号</th> -->
<!-- 			<th width="54%">错误信息描述</th> -->
<!-- 		</tr> -->
<%-- 		<c:forEach items="${importErrMsgList}" var="importErrMsgRow" varStatus="vs"> --%>
<!-- 			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" > -->
<%-- 				<td style="text-align: center">${vs.index+1}</td> --%>
<!-- 				<td style="text-align: center"> -->
<!-- 					该值根据参数表查找(uploadType) -->
<%-- 					${importErrMsgRow.uploadType} --%>
<!-- 				</td> -->
<!-- 				<td style="text-align: center"> -->
<!-- 					该值根据参数表查找(uploadType+dataType) -->
<%-- 					${importErrMsgRow.dataType} --%>
<!-- 				</td> -->
<%-- 				<td style="text-align: center">第${importErrMsgRow.rowNo}行</td> --%>
<%-- 				<td style="text-align: left">${importErrMsgRow.errDesc}</td> --%>
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
<%-- 		<c:if test="${empty importErrMsgList}"> --%>
<!-- 			<tr><td style="text-align: center;" colspan="5"><span class="red">该导入任务校验为无错误数据</span></td></tr> -->
<%-- 		</c:if> --%>
<!-- 	</table> -->
</form>
<p:page/>
</body>
</html>