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
<title>业务类型新增</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
 	$("#categoryId").combobox();
}


function addSubmit(){
	if(check()){
		var form = $("#projTypeAddForm")[0];
		form.action = "<%=request.getContextPath()%>/sysmanagement/businessType/addSubmit.do?<%=WebConsts.FUNC_ID_KEY%>=081505";
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
	<p:authFunc funcArray="081505" />
		<table>
			<tr class="collspan-control">
				<th colspan="4">业务类型新增</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%"><span class="red">*</span>业务类型</td>
				<td class="tdRight" width="25%" >
					<div class="ui-widget">
						<select id="categoryId" name="categoryId">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="SYS_SELECT_CATEGORY" selectColumn="CATEGORY_ID,CATEGORY_NAME" 
					 		valueColumn="CATEGORY_ID" textColumn="CATEGORY_NAME" orderColumn="CATEGORY_ID" 
					 		orderType="ASC" /> 
					</select>
					</div>
				</td>
				<td class="tdLeft">状态</td>
				<td class="tdRight">
					<div class="base-input-radio" id="isInvalid">
						<label for="isInvalidY" onclick="App.radioCheck(this,'isInvalid')" >失效</label><input type="radio" id="isInvalidY" name="isInvalid" value="Y" />
						<label for="isInvalidN" onclick="App.radioCheck(this,'isInvalid')" class="check-label">未失效</label><input type="radio" id="isInvalidN" name="isInvalid" value="N" checked="checked"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="red">*</span>
					参数名称
				</td>
				<td class="tdRight">
					<input type="text"  id="paramName" name="paramName" class="base-input-text"/>
				</td>
				<td class="tdLeft"><span class="red">*</span>
					参数值
				</td>
				<td class="tdRight">
					<input type="text"  id="paramValue" name="paramValue" class="base-input-text"/>
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