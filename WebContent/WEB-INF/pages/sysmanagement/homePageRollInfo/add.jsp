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
<title>首页滚动信息维护新增</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#visualGrade").combobox();
	var date = new Date();
	$( "#lastDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    minDate:'${nowDate}'
	});
}


function addSubmit(userId){
	if(check()){
		$("#rollTitle").val($.trim($('#rollTitle').val()));
		var form = $("#rollInfoAddForm")[0];
		form.action = "<%=request.getContextPath()%>/sysmanagement/homePageRollInfo/addSubmit.do?<%=WebConsts.FUNC_ID_KEY%>=081303&userId="+userId;
		App.submit(form);
	}	
}

function check(){
	if($('input').val()==''||$('input')==null){
		if($.trim($('#rollTitle').val())==''||$.trim($('#rollTitle').val())==null){
		App.notyError("滚动信息的名称不能为空");
		return false;
		}
		if($('#lastDate').val()==''||$('#lastDate').val()==null){
		App.notyError("滚动信息的最后日期不能为空");
		return false;
		}
		if($('#visualGrade').val()==''||$('#visualGrade').val()==null){
		App.notyError("请选择可见级别");
		return false;
		}
		if($('#rollInfo').val()==''||$('#rollInfo').val()==null){
		App.notyError("滚动信息的内容不能为空");
		return false;
		}
	return true;
	}
}

</script>
</head>

<body>
	<form method="post" id="rollInfoAddForm">
	<p:authFunc funcArray="081303" />
		<table>
			<tr>
				<th colspan="4">首页滚动信息新增</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%"><span class="red">*</span>名称</td>
				<td class="tdRight" width="25%">
					<input type="text" id="rollTitle" name="rollTitle" maxlength="60" class="base-input-text"/>
				</td>
				
				
			</tr>
			<tr>
			<td class="tdLeft" width="25%"><span class="red">*</span>最后日期</td>
				<td class="tdRight" width="25%">
					<input type="text" id="lastDate" name="lastDate" class="base-input-text" valid errorMsg="请选择付款日期" readonly="readonly"/>
				</td>
				<td class="tdLeft" width="25%"><span class="red">*</span>可见级别</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
					<c:if test="${isA0001SuperAdmin=='1'}">
					<select id="visualGrade" name="visualGrade" class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="1">全国</option>
						<forms:codeTable tableName="(select distinct org1_code, org1_name from TB_FNDWRR)" selectColumn="org1_code, org1_name" 
						 valueColumn="org1_code" textColumn="org1_name" orderColumn="org1_code" orderType="ASC"  />
					</select>
					</c:if>
					<c:if test="${isA0001SuperAdmin=='0'}">
						<select id="visualGrade" name="visualGrade" class="erp_cascade_select">
						 <option value="2" selected="selected">全省</option>
						</select>
					</c:if>
				</div>
				</td>				
			</tr>
			<tr>
				<td class="tdLeft"><span class="red">*</span>内容<br/>(<span id='memoSpan1'>0/250</span>)</td>
				<td class="tdRight" colspan="3">
					<textarea class="base-textArea" rows="5" cols="50" id="rollInfo" name="rollInfo" onkeyup="$_showWarnWhenOverLen1(this,250,'memoSpan1')"></textarea>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="保存" onclick="addSubmit('${userId}');">
				<input type="button" value="返回" onclick="backToLastPage('${url}');">
			</td>
		</tr>
		</table>
	</form>
	<p:page/>
</body>
</html>