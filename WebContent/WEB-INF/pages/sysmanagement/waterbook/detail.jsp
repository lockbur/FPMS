<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流水明细</title>
</head>
<body>
<p:authFunc funcArray="030501"/>
<form action="<%=request.getContextPath()%>/sysmanagement/waterbook/getWBList.do" method="post" id="wbDtlForm">
	<table>
		<tr>
			<th colspan="4">
				明细
			</th>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.waterbook.wbNum"/></td>
			<td class="tdRight">
				${wbInfo.wbNum}
			</td>
			<td class="tdLeft"><fmt:message key="label.waterbook.busNum"/></td>
			<td class="tdRight">
				${wbInfo.busNum}
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.waterbook.busMenu"/></td>
			<td class="tdRight">
				${wbInfo.busMenu}
			</td>
			<td class="tdLeft"><fmt:message key="label.waterbook.oprType"/></td>
			<td class="tdRight">
				${wbInfo.operateType}
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.waterbook.oprDate"/></td>
			<td class="tdRight">
				${wbInfo.operateDate}
			</td>
			<td class="tdLeft"><fmt:message key="label.waterbook.oprTime"/></td>
			<td class="tdRight">
				${wbInfo.operateTime}
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.waterbook.operator"/></td>
			<td class="tdRight" colspan="3">
				${wbInfo.operator}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">  
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>