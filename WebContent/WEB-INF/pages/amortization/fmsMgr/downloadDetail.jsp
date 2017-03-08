<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<title>下载明细</title>
<style type="text/css">
	a:HOVER {
		cursor: pointer;
	}
}
</style>
<script type="text/javascript">
function fmsDownloadFile(filePath){
	var form = $("#detailForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/fmsDownloadFile.do?<%=WebConsts.FUNC_ID_KEY%>=04030304&filePath='+filePath;
	form.action = url;
	form.submit();
}
</script>
</head>

<body>
<form action="" id="detailForm" method="post">
<table>
	<tr>
		<td class="tdLeft">交易类型：</td>
		<td class="tdRight">
		<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
					valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
 					conditionStr="CATEGORY_ID = 'DOWNLOAD_TRADE_TYPE' or CATEGORY_ID = 'UPLOAD_TRADE_TYPE'" selectedValue="${fmsDownload.tradeType}"/>
		</td>
	<tr>
		<td class="tdLeft">FMS下载文件：</td>
		<td class="tdRight"><a href="#" onclick="fmsDownloadFile('${filePath}');">${baseFile}</a></td>
	</tr>
	<tr>
		<td class="tdLeft">导数废弃文件：</td>
		<td class="tdRight"><a href="#" onclick="fmsDownloadFile('${badFilePath}');">${badFile}</a></td>
	</tr>
	<tr>
		<td class="tdLeft">导数日志文件：</td>
		<td class="tdRight"><a href="#" onclick="fmsDownloadFile('${logFilePath}');">${logFile}</a></td>
	</tr>
	<c:if test="${!empty uploaddeallog}">
	<tr>
		<td class="tdLeft">处理信息：</td>
		<td class="tdRight">${uploaddeallog}</td>
	</tr>
	</c:if>
</table>
<br>
<div align="center" style="margin-top: 10px;">
	<input type="button" value="关闭" onclick="art.dialog.close();">
</div>
</form>
</body>
</html>