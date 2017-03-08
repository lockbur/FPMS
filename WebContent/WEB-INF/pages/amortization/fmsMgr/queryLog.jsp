<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>处理失败信息</title>
<script type="text/javascript">

</script>
</head>

<body>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="2">处理失败信息</th>
		</tr>
		<tr>
			<td class="tdLeft">原因</td>
			<td class="tdRight">
				<%
					String str = (String)request.getAttribute("dealLog");
					if(null != str && !"".equals(str))
					{
						String[] strLog = str.split(";");
						for (int i = 0; i < strLog.length; i++) {
							out.println("("+(i+1)+") "+strLog[i]+".");
					}
				%>
					<br/>
				<%
					}
				%>
				
			</td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<input type="button" class="base-input-button" value="关闭" onclick="art.dialog.close()"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>