<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FMS下载</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	
	//设置时间插件
/* 	$( "#tradeDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	}); */
}
</script>
</head>

<body>
<p:authFunc funcArray="040305,04030501"/>
<form method="post" id="queryForm" action="">
	<table class="tableList">
		<tr>
			<th colspan="2">文件下载</th>
		</tr>
		<%-- <tr>
			<td class="tdLeft">交易日期</td>
			<td class="tdRight">
				<input type="text" id="tradeDate" class="base-input-text" name="tradeDate" value="${tradeDate }"/>
			</td>
		</tr> --%>
		<tr>
			<td colspan="2"  style="text-align: center;">
				<p:button value="下载回盘文件" funcId="04030501"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>