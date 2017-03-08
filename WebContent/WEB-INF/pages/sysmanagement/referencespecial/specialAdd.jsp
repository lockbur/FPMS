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
<title>专项新增</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
 	
}

//校验专项编码是否存在
function checkSID(){
	var specialId = $("#specialId").val();
	var isPass = false;
	var data = {};
	data['specialId'] = specialId;
	var url = "sysmanagement/referencespecial/checkSID.do?VISIT_FUNC_ID=01120201";
	App.ajaxSubmit(url,{data:data,async:false},
			function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("专项编号已存在!");
					isPass =  false;
				}
				else
				{
					isPass =  true;
				} 
			});
	return isPass;
}

function doValidate(){
	//提交前调用检查
	if(!App.valid("#sForm")){return;} 
	if(!checkSID())
	{
		return false;
	}
	return true;
}


</script>
</head>
<body>
<p:authFunc funcArray="01120203,01120201"/>
<form action="<%=request.getContextPath()%>/sysmanagement/referencespecial/specialAdd.do" method="post" id="sForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">
				专项新增
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%"><span class="red">*</span>专项编码</td>
			<td class="tdRight" width="50%">
				<input id="specialId" name="specialId" maxlength="9" valid class="base-input-text" type="text" onblur="checkSID();"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>专项说明</td>
			<td class="tdRight">
				<input id="specialName" name="specialName" maxlength="200" valid class="base-input-text" type="text" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>安全性</td>
			<td class="tdRight">
				<div class="base-input-radio" id="statusDiv1" style="width: 350px;">
					<label for="isAll" onclick="App.radioCheck(this,'statusDiv1')" class="check-label">全局</label>
					<input type="radio" id="isAll" name="scope" value="3" checked="checked">
					<label for="ZH" onclick="App.radioCheck(this,'statusDiv1')">总行</label>
					<input type="radio" id="ZH" name="scope" value="1" >
					<label for="FH" onclick="App.radioCheck(this,'statusDiv1')">分行</label>
					<input type="radio" id="FH" name="scope" value="2" >
					<label for="TY" onclick="App.radioCheck(this,'statusDiv1')">停用</label>
					<input type="radio" id="TY" name="scope" value="4" >
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="01120203" value="保存"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
	</table>
	<br>
</form>
</body>
</html>