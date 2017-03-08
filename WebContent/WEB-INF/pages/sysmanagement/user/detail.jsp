<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function fileDownload(batchNo){
	var data = {};
	var orgType= $("#orgType").val();
	var func_id="";
	var func_id_1="";
	data["batchNo"] = batchNo;
	if (orgType=="01") {
		func_id="0811030102";
		func_id_1="0811020108";
	} else {
		func_id="0811030202";
		func_id_1="0811020208";
	}
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/montAprvBatch/apprv/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
		{data:data,async:false}, 
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#detailForm")[0];
				form.action = "<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/Export.do?<%=WebConsts.FUNC_ID_KEY%>="+func_id_1+"&batchNo="+data.batchNo;
				form.submit();
			}else{
				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
				App.notyError(checkMsg);
			}
		}
	);
}
function downloadFile(filePath,fileName){
	var  url = '<%=request.getContextPath()%>/fileUtils/fileDownload.do?filePath='+filePath+'&fileName='+fileName;
	location.href = url;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>详情</title>
</head>
<body>
<input type="hidden" value="${bean.orgType}" id="orgType"/>
	<table class="tableList">
		<tr>
			<th colspan="4">数据详情</th>
		</tr>
		<tr>
			<td class="tdLeft">批次号</td>
			<td class="tdRight">${bean.batchNo}</td>
			<td class="tdLeft">源文件名</td>
			<td class="tdRight">
			  ${bean.sourceFilename}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">操作人</td>
			<td class="tdRight">${bean.instUser}</td>
			<td class="tdLeft">操作时间</td>
			<td class="tdRight">${bean.instDate} - ${bean.instTime}</td>
		</tr>
		<tr>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
				${bean.statusName }
			</td>
			<td class="tdLeft">备注</td>
			<td class="tdRight">${bean.memo}</td>	
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
	<br>
	<br>

	<table class="tableList">
		<tr class="collspan-control" title="导入信息Table">
			<th width="20%">Excel行号</th>
			<th width="20%">员工号</th>
			<th width="20%">是否省行超级管理员</th>
			<th width="20%">所属角色名称</th>
			<th width="20%">错误原因</th>
		</tr>
		<c:forEach items="${err}" var="errBean" varStatus="vs">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
				<td style="text-align: center" >第 ${errBean.rowNo} 行</td>
				<td style="text-align: center">${errBean.userId}</td>
				<td style="text-align: center">${errBean.isAdmin}</td>
				<td style="text-align: center">${errBean.roleName}</td>
				<td style="text-align: center ;height:33px">${errBean.memo}</td>
			</tr>
		</c:forEach>
		<c:if test="${empty err}">
			<tr><td style="text-align: center;" colspan="5"><span class="red">无导入错误数据</span></td></tr>
		</c:if>
		
	</table>
	<p:page/>
	
<br><br><br>
<input type="button" value="返回" onclick="backToLastPage('${uri}');">
</body>
</html>