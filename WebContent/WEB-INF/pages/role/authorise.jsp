<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>角色修改</title>
<script type="text/javascript">
var tree = null;

function pageInit() {
	<%
	String jsonData = WebUtils.getRequestAttr("funcTree");
	%>
	var treeObj = <%=Tool.CHECK.isEmpty(jsonData) ? "null" : jsonData%>;
	
	if (treeObj != null) {
		tree = new dhtmlXTreeObject("treepanel","100%","100%",0);
		tree.setImagesPath('<%=request.getContextPath()%>/component/dtree/css/imgs/');
		tree.enableTreeLines(true);
		tree.enableCheckBoxes(true);
		tree.enableThreeStateCheckboxes(true);
		tree.loadJSONObject(treeObj);
		tree.openAllItems(1);
	}
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#roleLevel").combobox();
}
function doValidate() {
	var checkedList = tree.getAllCheckedBranches();
	$('#funcIdList').val(checkedList);
	
	var checkFlag2=App.valid("#roleForm");
	var checkFlag1= false;
	if(trim($('#funcIdList').val())=='')
	{
		App.toShowNullCheck('roleForm','treepanel','请选择角色权限。');
		checkFlag1= false;
	}else{
		App.toHiddenNullCheck('roleForm','treepanel','treeTd');
		checkFlag1= true;
	}
	
	if(!checkFlag1 ||! checkFlag2 ){ return; }
	return true;
}

</script>
</head>
<body>
<p:authFunc funcArray="0102010101"/>
<form action="<%=request.getContextPath()%>/role/authorise.do" method="post" id="roleForm">
	<p:token/>
	<input type="hidden" id="funcIdList" name="funcIdList">
	<input type="hidden" id="roleId" name="roleId" value="${roleInfo.roleId}">
	<table>
		<tr>
			<th colspan="4">角色修改</th>
		</tr>
		<tr>
			<td class="tdLeft">角色ID</td>
			<td class="tdRight">${roleInfo.roleId}</td>
			<td class="tdLeft"><span class="red">*</span>角色名</td>
			<td class="tdRight"><input type="text" maxlength="100" name="roleName" class="base-input-text" value="${roleInfo.roleName}" valid errorMsg="请输入角色名。"/></td>
		</tr>
		<tr>
			<td class="tdLeft">录入人</td>
			<td class="tdRight">${roleInfo.instUser}</td>
			<td class="tdLeft"> 录入时间</td>
			<td class="tdRight">${roleInfo.instDate} </td>
		</tr>
		<tr>
			<td class="tdLeft">最后修改人</td>
			<td class="tdRight">${roleInfo.updateUser}</td>
			<td class="tdLeft"> 最后修改时间</td>
			<td class="tdRight">${roleInfo.updateDate} </td>
		</tr>
		<tr>
			<td class="tdLeft">角色使用范围</td>
			<td class="tdRight">
            	<div class="ui-widget">
					<select id="roleLevel" name="roleLevel" class="erp_cascade_select">
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_ROLE_LEVEL'" selectedValue="${roleInfo.roleLevel}"/>
					</select>
				</div>	
			</td>
			<td class="tdRight" colspan="2"></td>
		</tr>
		<tr>
			<td class="tdLeft" valign="top"><span class="red">*</span>角色权限</td>
			<td class="tdRight" colspan="3" id="treeTd">
				<div id="treepanel"></div>
			</td>	
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="0102010101" value="提交"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>