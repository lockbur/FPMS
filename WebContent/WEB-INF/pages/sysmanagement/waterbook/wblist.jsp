<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流水列表</title>
<script type="text/javascript">
function gotoDtl(wbNum)
{
	var form = document.forms[0];
	form.wbNum.value = wbNum;
	form.action='<%=request.getContextPath()%>/sysmanagement/waterbook/getWBDtl.do?<%=WebConsts.FUNC_ID_KEY%>=030501';
	App.submit(form);
}
</script>
</head>

<body>
<p:authFunc funcArray="030501"/>
<form method="post" id="wbForm" action="<%=request.getContextPath()%>/sysmanagement/waterbook/list.do?<%=WebConsts.FUNC_ID_KEY%>=0305">
	<input type="hidden" id="wbNum" name="wbNum"/>
	<table class="tableList">
		<caption>流水列表</caption>
		<tr>
			<th><fmt:message key="label.waterbook.wbNum"/></th>
			<th><fmt:message key="label.waterbook.busNum"/></th>
			<th><fmt:message key="label.waterbook.oprType"/></th>
			<th><fmt:message key="label.waterbook.operator"/></th>
			<th><fmt:message key="label.waterbook.oprDate"/></th>
			<th><fmt:message key="label.waterbook.oprTime"/></th>
			<th><fmt:message key="label.waterbook.operate"/></th>
		</tr>
		<c:forEach items="${wbList}" var="wbInfo">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${wbInfo.wbNum}</td>
				<td>${wbInfo.busNum}</td>
				<td>${wbInfo.operateType}</td>
				<td>${wbInfo.operator}</td>
				<td>${wbInfo.operateDate}</td>
				<td>${wbInfo.operateTime}</td>
				<td >
  				    <div class="detail">
					    <a href="#" onclick="gotoDtl('${wbInfo.wbNum}');"></a>
					</div>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty wbList}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>