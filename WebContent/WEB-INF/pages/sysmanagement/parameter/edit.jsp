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
<title>参数修改</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#paramUpdateValue").unbind("keyup");
});

function updateSubmit(paramValue,regExp,regCheckInfo,isUseReg){
	if(check(paramValue,regExp,regCheckInfo,isUseReg)){
		$("#paramUpdateValue").val($.trim($("#paramUpdateValue").val()));
		var form = $("#paraForm")[0];
		form.action = "<%=request.getContextPath()%>/sysmanagement/parameter/edit.do?<%=WebConsts.FUNC_ID_KEY%>=0105010101";
		App.submit(form);
	}
}

function check(paramValue,regExp,regCheckInfo,isUseReg){
	var paramValue= $.trim($("#paramValue").val());
	var paramUpdateValue= $.trim($("#paramUpdateValue").val());
	var re = new RegExp(regExp);
		if(paramUpdateValue==''||paramUpdateValue==null){
			App.notyError("参数修改值不能为空");
			return false;
		}else{
			if(paramValue == paramUpdateValue){
				App.notyError("修改值和原始值相同，请重新输入！");
				return false;
			}else if(isUseReg=='1' && !re.test(paramUpdateValue)){//paramUpdateValue.match(re)==null){
	            App.notyError(regCheckInfo);
	            return false;
	        }
	        return true;
		}
}
//是否显示密码
function showPwd(obj){
	if ($(obj).attr("title")=="查看密码") {
		$(obj).attr("title","隐藏密码");
	} else {
		$(obj).attr("title","查看密码");
	}
	$(obj).parent().find("#isHidden").toggle();
	$(obj).parent().find("#isShow").toggle();
} 
</script>
</head>
<body>
<p:authFunc funcArray="0105010101"/>
<form action="<%=request.getContextPath()%>/sysmanagement/parameter/edit.do" method="post" id="paraForm">
        <input type="hidden" name="isPwdType" value="${paraUpdate.isPwdType }">
		<input type="hidden"  id="paramVarName" name="paramVarName" value="${paraUpdate.paramVarName}"/>
	<p:token/>
	<table>
		<tr>
			<th colspan="4">
				修改参数
			</th>
		</tr>
		<tr>
			<td class="tdLeft">参数类型</td>
			<td class="tdRight" >
				 ${paraUpdate.categoryName}
			</td>
			<td class="tdLeft">参数名称</td>
			<td class="tdRight">
				${paraUpdate.paramDispName}
<%-- 				<input type="text" maxlength='100' id="paramDispName" name="paramDispName" value="${paraUpdate.paramDispName}" class="base-input-text" valid errorMsg="请输入参数显示名称。"/> --%>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">
				参数值
			</td>
			<td class="tdRight">
				<c:if test="${paraUpdate.isPwdType == 0}"><c:out value="${paraUpdate.paramValue}" />  </c:if>
				 <c:if test="${paraUpdate.isPwdType == 1}">
					 <div><span id="isHidden"><c:out value="******" /></span> <span id="isShow" style="display: none;"><c:out value="${paraUpdate.paramValue}" /></span>
					 	<img alt="查看密码"  title="查看密码" style="cursor: pointer;" src="<%=request.getContextPath()%>/common/images/show.png" onclick="showPwd(this);">
					 </div>
				 </c:if>
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
			<td class="tdLeft">参数描述</td>
			<td class="tdRight" colspan="3">
				${paraUpdate.paramDesc}
<%-- 				<textarea class="base-textArea" id="paramDesc" name="paramDesc" rows='3'  onkeyup="$_showWarnWhenOverLen1(this,1000,'paramDescSpan')">${paraUpdate.paramDesc}</textarea> --%>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="提交" onclick="updateSubmit('${paraUpdate.paramValue}','${paraUpdate.regExp}','${paraUpdate.regCheckInfo}','${paraUpdate.isUseReg}')">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>