<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOG日志下载</title>
<script type="text/javascript">
function downloadFile(gzName){
	var form = $("#queryForm")[0];
	var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/downloadLog.do?<%=WebConsts.FUNC_ID_KEY%>=08090301&gzName='+gzName;
	form.action = url ;
	form.submit();
}
</script>
</head>
<body>
<p:authFunc funcArray="080903"/>
<form method="post" id="queryForm" action="">
<input type="hidden" name="startDate" value="${bean.startDate}">
<input type="hidden" name="endDate" value="${bean.endDate}">
	<table class="tableList">	
	    <tr>
			<th>文件列表</th>
		</tr>
		<c:forEach items="${logList}" var="log" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				<td><a href="#" style="text-decoration: underline;" title="下载文件：${log}" onclick="downloadFile('${log}')">
					${log}
				</a></td>								
			</tr>
		</c:forEach>
		<c:if test="${empty logList}">
		    <tr>
			<td style="text-align: center;" class="red"><span>没有找到相关文件</span></td>
			</tr>
	   </c:if>
		
	</table>
	<br><br>
	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
</form>
</body>
</html>