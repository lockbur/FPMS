<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<script>
function dowloadTemplate(){
	var width=window.screen.availWidth;
	var url = '<%=request.getContextPath()%>/sysmanagement/user/exportTemlate.do?<%=WebConsts.FUNC_ID_KEY%>=01030602&width='+width;
	encodeURI(url);
	window.open(url);
}

</script>

</head>

<body>
<p:authFunc funcArray="01030601,01030602"/>
<form method="post" action="<%=request.getContextPath()%>/sysmanagement/user/saveImportInfo.do?<%=WebConsts.FUNC_ID_KEY%>=01030601" enctype="multipart/form-data" id="userForm" enctype="multipart/form-data">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">用户导入</th>
		</tr>
		<tr>
			<td class="tdLeft">导入文件</td>
			<td class="tdRight">
				<input type="file" name="userFile" style="width: 300px;" class="base-input-text" />
				<a href="#" onclick="dowloadTemplate();"><span class="red">下载模板</span></a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<p:button funcId="01030601" />
			</td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<td align="left" style="background-color: #FAF9F0;">温馨提示：<br>
			（1） 导入请按照导入模板进行导入<br>
			（2）分行管理员只允许导入自己分行下的用户<br>
			（3）用户导入后初始化密码为【111111】
			</td>
		</tr>
	</table>
</form>
</body>
</html>