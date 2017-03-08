<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专项更新</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
 	
}

function doValidate(){
	//提交前调用检查
	if(!App.valid("#sForm")){return;} 
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="01120205"/>
<form action="" method="post" id="sForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">
				专项新增
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%"><span class="red">*</span>专项编码</td>
			<td class="tdRight" width="50%">${bean.specialId}
				<input type="hidden" id="specialId" name="specialId" value="${bean.specialId}" class="base-input-text" maxlength="9" readonly="readonly"/>	
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>专项说明</td>
			<td class="tdRight">
				<input type="text" id="specialName" name="specialName" value="${bean.specialName}"  class="base-input-text" maxlength="200" valid/>	
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>安全性</td>
			<td class="tdRight">
				<div class="base-input-radio" id="statusDiv1" style="width: 350px;">
					<label for="isAll" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${bean.scope=='3'}">class="check-label"</c:if>>全局</label>
					<input type="radio" id="isAll" name="scope" value="3" <c:if test="${bean.scope=='3'}">checked="checked"</c:if>>
					<label for="ZH" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${bean.scope=='1'}">class="check-label"</c:if>>总行</label>
					<input type="radio" id="ZH" name="scope" value="1" <c:if test="${bean.scope=='1'}">checked="checked"</c:if>>
					<label for="FH" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${bean.scope=='2'}">class="check-label"</c:if>>分行</label>
					<input type="radio" id="FH" name="scope" value="2" <c:if test="${bean.scope=='2'}">checked="checked"</c:if>>
					<label for="TY" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${bean.scope=='4'}">class="check-label"</c:if>>停用</label>
					<input type="radio" id="TY" name="scope" value="4" <c:if test="${bean.scope=='4'}">checked="checked"</c:if>>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="01120205" value="更新"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
	</table>
	<br>
</form>
</body>
</html>