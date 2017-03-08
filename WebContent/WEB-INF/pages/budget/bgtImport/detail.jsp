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
<title>导入预算汇总详情页面</title>

	
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
		//往TD中写入最终字符串
		$(obj).html(resultStr);
	}


	$(function(){
		$(".splitValue").each(function(){
			splitSendAndReceivedValue(this,$(this).html());		
		});
		
		
		
		$(".shortDesc").each(function(){
			var originalVal = $(this).html();
			var shortVal = originalVal.substring(0,45) + "......";
			$(this).html(shortVal);
			$(this).parent().attr("title",originalVal);
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
			<th colspan="4" title="【导入批次：${bean.batchNo}】">
				预算导入详情
				<input type="hidden" value="${bean.batchNo}" name="batchNo">
			</th>
		</tr>
		<tr>
			<td class="tdLeft">批次号</td>
			<td class="tdRight">
			${bean.batchNo }
			</td>
			<td class="tdLeft" width="20%">预算年份</td>
			<td class="tdRight" width="30%">
				${bean.bgtYear}
			</td>
		
		</tr>
			<tr>
			<td class="tdLeft" width="20%">预算类型</td>
			<td class="tdRight" width="30%">
				${bean.bgtTypeName}
			</td>
			<td class="tdLeft">预算子类型</td>
			<td class="tdRight">
			${bean.subTypeName }
			</td>
		</tr>
		<tr>
			<td class="tdLeft">数据状态</td>
			<td class="tdRight">
				${bean.statusName }
			</td>
			<td class="tdLeft">导入操作员</td>
			<td class="tdRight">
				${bean.instOper}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">导入时间</td>
			<td class="tdRight" colspan="3">
				${bean.instDate}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">操作备注</td>
			<td class="tdRight" colspan="3"> 
				<b>${bean.memo}</b>
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
		
		
	</table>
	
	<br/>
	<table class="tableList">
		<tr class="collspan-control" title="导入信息Table">
			<th width="10%">Excel行号</th>
			<th width="12%">预算组织</th>
			<th width="16%">监控指标</th>
			<th width="14%">物料编码</th>
			<th width="14%">总预算金额(元)</th>
			<th width="17%">错误原因</th>
		</tr>
		<c:forEach items="${errorList}" var="errBean" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
				<td style="text-align: center" title="预算导入批次号：${errBean.batchNo}">第 ${errBean.rowNo} 行</td>
				<td style="text-align: center">${errBean.bgtOrgcode}</td>
				<td style="text-align: center">${errBean.bgtMontcode}</td>
				<td style="text-align: center">${errBean.bgtMatrcode}</td>
				<td style="text-align: center"><fmt:formatNumber>${errBean.bgtSum}</fmt:formatNumber> </td>
				<td style="text-align: center ;height:33px">
				${errBean.errDesc}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty errorList}">
			<tr><td style="text-align: center;" colspan="6"><span class="red">该预算汇总无导入详情数据</span></td></tr>
		</c:if>
	</table>
</form>
<p:page/>
<br><br><br>
	<input type="button" value="返回" onclick="backToLastPage('${uri}')">
</body>
</html>