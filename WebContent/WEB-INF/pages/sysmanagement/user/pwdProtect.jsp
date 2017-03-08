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
<title>密保设置</title>
<script type="text/javascript">

function pageInit() {
	
	App.jqueryAutocomplete();
	$( ".erp_cascade_select" ).combobox();
	var tabs = $( "#tabs" ).tabs({
		active: $('#tabIndex').val(),
		activate: function(){
			$('#tabIndex').val($(this).tabs('option', 'active'));
		}
	});	
}

function pwdProtect(userId){
	var sel=$("select[name='questionId']");
	if(sel[0].value==""||sel[1].value==""||sel[2].value==""){
		App.notyError("问题不能为空");
		return;
	}
	if(sel[0].value==sel[1].value||sel[0].value==sel[2].value||sel[2].value==sel[1].value){
		App.notyError("问题不能相同");
		return;
	}
	var que = $("input[name='questionAnswer']");
	if($.trim(que[0].value)==""||$.trim(que[1].value)==""||$.trim(que[2].value)==""){
		App.notyError("问题的答案不能为空");
		return;
	}
	que.each(function(i){
		$(que[i]).val($.trim(que[i].value));
	});
	var form = $("#pwdProtectForm")[0];
	form.action = '<%=request.getContextPath()%>/sysmanagement/user/pwdProtectSubmit.do?<%=WebConsts.FUNC_ID_KEY%>=01060201&userId='+userId;
	App.submit(form);
}

function pwdProtectEmail(userId){
	if ($.trim($("#notesId").val())=="") {
		App.notyError("邮箱地址不能为空");
		return;
	}
	var email= $.trim($("#notesId").val());
	var re = /^([\.a-zA-Z0-9_-]){6,18}@([a-zA-Z0-9_-]){2,10}(\.([a-zA-Z0-9]){2,}){1,4}$/;
		if(!re.test(email)){
	         App.notyError("邮箱地址格式有误！");
	         return;
	     }
	$("#notesId").val($.trim($("#notesId").val()));
	var form = $("#pwdProtectFormEmail")[0];
	form.action = '<%=request.getContextPath()%>/sysmanagement/user/pwdProtectSubmit.do?<%=WebConsts.FUNC_ID_KEY%>=01060201&userId='+userId;
	App.submit(form);
}

function resetAll() {
	$("select.erp_cascade_select").val("");
	$(":text").focus();
	$(":text").blur();
	$(":selected").prop("selected",false);
	$("input[name='questionAnswer']").val("");
}

function resetEmail(){
	$("input[name='notesId']").val("");
}
</script>
</head>
<body>
<form:hidden id="tabIndex" path="selectInfo.tabsIndex"/>
<p:authFunc funcArray="01060201,01060202"/>
<div id="tabs" style="border: 0;">
	<ul>
		<li><a href="#tabs-1" >通过密保问题设置密保</a></li>
		<li><a href="#tabs-2">通过邮箱地址设置密保</a></li>
	</ul>
<div id="tabs-1" style="padding: 0;">
<form method="post" id="pwdProtectForm">
<input type="hidden" name="flag" value="0">
	<table>
		<tr class="collspan-control">
			<th colspan="4">密保设置</th>
		</tr>
		<c:if test="${!empty pwdProtectInfo}">
		<c:forEach items="${pwdProtectInfo}" var="pwdProtectInfo">
		<tr>
			<td class="tdLeft">问题<c:if test="${pwdProtectInfo.seq==1}">一</c:if><c:if test="${pwdProtectInfo.seq==2}">二</c:if><c:if test="${pwdProtectInfo.seq==3}">三</c:if></td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" name="questionId" >
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG"
 						 conditionStr="CATEGORY_ID = 'SYS_PASSWORD_QUESTION'"
 						 orderType="ASC" selectedValue="${pwdProtectInfo.id}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">答案</td>
			<td class="tdRight">
				<input type="text" id="questionAnswer" name="questionAnswer" value="${pwdProtectInfo.answer}" class="base-input-text"/>
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty pwdProtectInfo}">
		<tr>
			<td class="tdLeft">问题一</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" name="questionId" >
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'SYS_PASSWORD_QUESTION'"
						 orderType="ASC" selectedValue=""/>	
					</select>
				</div>
			</td>
			<td class="tdLeft">答案</td>
			<td class="tdRight">
				<input type="text" id="questionAnswer" name="questionAnswer" value="" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">问题二</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" name="questionId" >
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'SYS_PASSWORD_QUESTION'"
						 orderType="ASC" selectedValue=""/>	
					</select>
				</div>
			</td>
			<td class="tdLeft">答案</td>
			<td class="tdRight">
				<input type="text" id="questionAnswer" name="questionAnswer" value="" class="base-input-text"/>
			</td>
		</tr>
		<tr>
		<td class="tdLeft">问题三</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select class="erp_cascade_select" name="questionId">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'SYS_PASSWORD_QUESTION'"
						 orderType="ASC" selectedValue=""/>	
					</select>
				</div>
			</td>
			<td class="tdLeft">答案</td>
			<td class="tdRight">
				<input type="text" id="questionAnswer" name="questionAnswer" value="" class="base-input-text"/>
			</td>
		</tr>
		</c:if>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="提交" onclick="pwdProtect('${userInfo.userId}');">
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	</form>
	
	<p:page/>
	</div>
	<div id="tabs-2" style="padding: 0;">
	<form method="post" id="pwdProtectFormEmail">
	<input type="hidden" name="flag" value="1">
	<table>
		<tr class="collspan-control">
			<th colspan="4">密保设置</th>
		</tr>
		<tr>
			<td class="tdLeft" colspan="1"><span class="red">*</span>&nbsp;邮箱</td>
			<td class="tdRight" colspan="3">
				<input type="text" maxlength="200" id="notesId" name="notesId" value="${baseInfo.notesId}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="提交" onclick="pwdProtectEmail('${userInfo.userId}');">
				<input type="button" value="重置" onclick="resetEmail();">
			</td>
		</tr>
		</table>
		</form>
	</div>
</div>
	<br>
</body>
</html>