<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目类型新增</title>
<script type="text/javascript">
function pageInit(){
}


function addSubmit(){
	if(check()){
		var form = $("#projTypeAddForm")[0];
		form.action = "<%=request.getContextPath()%>/sysmanagement/projType/addSubmit.do?<%=WebConsts.FUNC_ID_KEY%>=081403";
		App.submit(form);
	}	
}

function check(){
	if($.trim($('#paramName').val())==''||$.trim($('#paramName').val())==null)
	{
		App.notyError("项目类型名称不能为空");
		return false;
	}
	return true;
}

</script>
</head>

<body>
	<form method="post" id="projTypeAddForm">
	<p:authFunc funcArray="081403" />
		<table>
			<tr>
				<th colspan="4">项目类型新增</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%"><span class="red">*</span>名称</td>
				<td class="tdRight" width="25%" colspan="3">
					<input type="text" id="paramName" name="paramName" class="base-input-text" maxlength="60"/>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="新增" onclick="addSubmit();">
				<input type="button" value="返回" onclick="backToLastPage('${url}');">
			</td>
		</tr>
		</table>
	</form>
	<p:page/>
</body>
</html>