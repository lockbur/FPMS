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
<title>项目类型维护修改</title>
<script type="text/javascript">
function pageInit(){
	/* var flag = ${typeBean.useFlag};
	if(flag=='0'){
		$("#useFlagLabelY").removeClass("check-label"); 
		$("#useFlagY").removeAttr("checked");
	}else{
		$("#useFlagLabelN").removeClass("check-label"); 
		$("#useFlagN").removeAttr("checked");
	} */
	
}


function updateSubmit(){
	if(check()){
		if(confirm('确认修改')){
			var form = $('#projTypeUpdateForm')[0];
			form.action = "<%=request.getContextPath()%>/sysmanagement/projType/updateSubmit.do?<%=WebConsts.FUNC_ID_KEY%>=081404";
			App.submit(form);
		}
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
	<form method="post" id="projTypeUpdateForm">
	<p:authFunc funcArray="081404" />
		<table>
			<tr>
				<th colspan="4">项目类型更新</th>
			</tr>
			<tr>
				<td class="tdLeft" width="50%"><span class="red">*</span>名称</td>
				<td class="tdRight" width="50%">
					<input type="text" id="paramName"  value="${typeBean.paramName}" name="paramName" class="base-input-text" maxlength="60"/>
				</td>
				<td class="tdLeft" width="50%">状态</td>
				<td class="tdRight" width="50%">
					<div class="base-input-radio" id="useFlag">
				<%-- 	<input type="hidden" name="useFlag" value="${typeBean.useFlag}"> --%>
				<input type="hidden" name="paramValue" value="${typeBean.paramValue}" />
					<label for="useFlagY" id="useFlagLabelY" onclick="App.radioCheck(this,'useFlag')" <c:if test="${typeBean.useFlag=='1'}">class="check-label"</c:if>>启用</label><input type="radio" id="useFlagY" name="useFlag" value="1"  <c:if test="${typeBean.useFlag=='1'}">checked="checked"</c:if> />
					<label for="useFlagN" id="useFlagLabelN" onclick="App.radioCheck(this,'useFlag')" <c:if test="${typeBean.useFlag=='0'}">class="check-label"</c:if>>禁用</label><input type="radio" id="useFlagN" name="useFlag" value="0"  <c:if test="${typeBean.useFlag=='0'}">checked="checked"</c:if> />
				</div>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="提交" onclick="updateSubmit()">
				<input type="button" value="返回" onclick="backToLastPage('${url}');">
			</td>
		</tr>
		</table>
	</form>
	<p:page/>
</body>
</html>