<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务类型修改</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#paramUpdateValue").unbind("keyup");
});

function updateSubmit(){
	if(check()){
		$("#paramUpdateValue").val($.trim($("#paramUpdateValue").val()));
		$("#paramUpdateName").val($.trim($("#paramUpdateName").val()));
		var form = $("#paraForm")[0];
		form.action = "<%=request.getContextPath()%>/sysmanagement/businessType/edit.do?<%=WebConsts.FUNC_ID_KEY%>=081504";
		App.submit(form);
	}
}

function check(){
	var paramValue= $.trim($("#paramValue").val());
	var paramUpdateValue= $.trim($("#paramUpdateValue").val());
	var paramName= $.trim($("#paramName").val());
	var paramUpdateName= $.trim($("#paramUpdateName").val());
	if(paramUpdateValue==''||paramUpdateValue==null){
		App.notyError("参数修改值不能为空");
		return false;
	}
	if(paramUpdateName==''||paramUpdateName==null){
		App.notyError("参数名称修改值不能为空");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="081504"/>
<form action="<%=request.getContextPath()%>/sysmanagement/businessType/edit.do" method="post" id="paraForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="4">
				修改业务类型
			</th>
		</tr>
		<tr>
			<td class="tdLeft">业务类型</td>
			<td class="tdRight" >
				 ${paraUpdate.categoryName}
				 <input type="hidden"  id="categoryId" name="categoryId" value="${paraUpdate.categoryId}"/>
			</td>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isInvalid">
					<label for="isInvalidY" onclick="App.radioCheck(this,'isInvalid')" <c:if test="${paraUpdate.isInvalid=='Y'}">class="check-label"</c:if>>失效</label><input type="radio" id="isInvalidY" name="isInvalid" value="Y" <c:if test="${paraUpdate.isInvalid=='Y'}">checked="checked"</c:if>/>
					<label for="isInvalidN" onclick="App.radioCheck(this,'isInvalid')" <c:if test="${paraUpdate.isInvalid=='N'}">class="check-label"</c:if>>未失效</label><input type="radio" id="isInvalidN" name="isInvalid" value="N" <c:if test="${paraUpdate.isInvalid=='N'}">checked="checked"</c:if>/>
				</div>
			</td>
			
		</tr>
		<tr>
			<td class="tdLeft">
				参数名称
			</td>
			<td class="tdRight">
				<c:out value="${paraUpdate.paramName}" />
				<input type="hidden"  id="paramName" name="paramName" value="${paraUpdate.paramName}"/>
			</td>
			<td class="tdLeft">
				<span class="red">*</span>参数名称修改为
			</td>
			<td class="tdRight">
				<input type="text" maxlength='1000' id="paramUpdateName" name="paramUpdateName" value="${paraUpdate.paramUpdateName}" class="base-input-text" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">
				参数值
			</td>
			<td class="tdRight">
				<c:out value="${paraUpdate.paramValue}" />
				<input type="hidden"  id="paramValue" name="paramValue" value="${paraUpdate.paramValue}"/>
			</td>
			<td class="tdLeft">
				<span class="red">*</span>参数值修改为
			</td>
			<td class="tdRight">
				<input type="text" maxlength='1000' id="paramUpdateValue" name="paramUpdateValue" value="${paraUpdate.paramUpdateValue}" class="base-input-text" />
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="提交" onclick="updateSubmit();">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>