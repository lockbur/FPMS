<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>新增功能</title>
<script type="text/javascript">
function doValidate() {
	//提交前调用
	if(!App.valid("#addForm")){return;}
	return true;
}

function pageInit() {
	$("#backBtn").click(function(){
		$("#addForm").attr("action","<%=request.getContextPath()%>/function/search.do?VISIT_FUNC_ID=010101");
		$("#addForm").submit();
	});
	
	var menuArray = new Array();
	menuArray.push(['ROOT', '0', '菜单树']);
	<%
	ITree<Function> menuTree = WebUtils.getRequestAttr("menuTree");
	if (menuTree != null) {
		List<Function> menuList = menuTree.getNodeList();
		if (menuList != null && !menuList.isEmpty()) {
			for (Function f : menuList) {
				%>
	menuArray.push(['<%=f.getFuncId()%>', '<%=(Tool.CHECK.isEmpty(f.getParentFuncId()) ? "ROOT" : f.getParentFuncId())%>', '<%=WebUtils.getMessage(f.getFuncName())%>']);
				<%
			}
		}
	}
	%>
	
	$( '#treepanel' ).hide();
	if (menuArray != null) {
		var tree = new dhtmlXTreeObject("treepanel","100%","100%",0);
		tree.setImagesPath('<%=request.getContextPath()%>/component/dtree/css/imgs/');
		tree.loadJSArray(menuArray);
		tree.setOnClickHandler(function(id, pid, text) {
			var parentId = (id == '0' ? '' : id);
			$( '#parentFuncDisp' ).val(this.getSelectedItemText() + '(' + parentId + ')');
			$( '#parentFuncId' ).val(parentId); 
			$( "#treepanel" ).dialog("close");
		}); 
	}
	$( '#parentFuncDisp' ).click(function(){
		showTree();
	});
	
	function showTree() {
		$( "#treepanel" ).dialog({
			modal: true,
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}

</script>
</head>

<body>
<p:authFunc funcArray="0101010101"/>
<form action="<%=request.getContextPath() %>/function/add.do?VISIT_FUNC_ID=FUNC_ADD" method="post" id="addForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="4">新增功能</th>
		</tr>
		<tr>
			<td class="tdLeft"><span style="color:red;">*</span><fmt:message key="label.funcId"/></td>
			<td class="tdRight">
				<input type="text" id="funcId" name="funcId" maxlength="50" class="base-input-text" valid errorMsg="请输入功能ID。"/>
			</td>
			<td class="tdLeft"><fmt:message key="label.parentId"/></td>
			<td class="tdRight">
				<input type="text" id="parentFuncDisp" readonly="readonly" name="parentFuncDisp" class="base-input-text"/>
				<input type="hidden" id="parentFuncId" name="parentFuncId" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span style="color:red;">*</span><fmt:message key="label.funcName"/></td>
			<td class="tdRight">
				<input type="text" id="funcName" name="funcName" maxlength="200" class="base-input-text" valid errorMsg="请输入功能名称。"/>
			</td>
			<td class="tdLeft"><fmt:message key="label.iconPath"/></td>
			<td class="tdRight">
				<input type="text" id="iconPath" name="iconPath" maxlength="1000" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.url"/></td>
			<td class="tdRight">
				<input type="text" id="url" name="url" maxlength="1000" class="base-input-text"/>
			</td>
			<td class="tdLeft"><fmt:message key="label.isMenu"/></td>
			<td class="tdRight">
				<div class="base-input-radio" id="isMenuDiv">
					<label for="isMenu1" onclick="App.radioCheck(this,'isMenuDiv')" class="check-label"><fmt:message key="label.funcY"/></label><input type="radio" id="isMenu1" name="isMenu" value="Y" checked="checked">
					<label for="isMenu2" onclick="App.radioCheck(this,'isMenuDiv')" ><fmt:message key="label.funcN"/></label><input type="radio" id="isMenu2" name="isMenu" value="N">
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.needLogin"/></td>
			<td class="tdRight">
				<div class="base-input-radio" id="needLoginDiv">
					<label for="needLogin1" onclick="App.radioCheck(this,'needLoginDiv')" class="check-label"><fmt:message key="label.funcY"/></label><input type="radio" id="needLogin1" name="needLogin" value="Y" checked="checked">
					<label for="needLogin2" onclick="App.radioCheck(this,'needLoginDiv')" ><fmt:message key="label.funcN"/></label><input type="radio" id="needLogin2" name="needLogin" value="N">
				</div>
			</td>
			<td class="tdLeft"><fmt:message key="label.dependParent"/></td>
			<td class="tdRight">
				<div class="base-input-radio" id="dependParentDiv">
					<label for="dependParent1" onclick="App.radioCheck(this,'dependParentDiv')" class="check-label"><fmt:message key="label.funcY"/></label><input type="radio" id="dependParent1" name="dependParent" value="Y" checked="checked">
					<label for="dependParent2" onclick="App.radioCheck(this,'dependParentDiv')" ><fmt:message key="label.funcN"/></label><input type="radio" id="dependParent2" name="dependParent" value="N">
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.isDisplay"/></td>
			<td class="tdRight">
				<div class="base-input-radio" id="isDisplayDiv">
					<label for="isDisplay1" onclick="App.radioCheck(this,'isDisplayDiv')" class="check-label"><fmt:message key="label.funcY"/></label><input type="radio" id="isDisplay1" name="isDisplay" value="Y" checked="checked">
					<label for="isDisplay2" onclick="App.radioCheck(this,'isDisplayDiv')" ><fmt:message key="label.funcN"/></label><input type="radio" id="isDisplay2" name="isDisplay" value="N">
				</div>
			</td>
			<td class="tdLeft"><fmt:message key="label.isDefault"/></td>
			<td class="tdRight">
				<div class="base-input-radio" id="isDefaultDiv">
					<label for="isDefault1" onclick="App.radioCheck(this,'isDefaultDiv')" class="check-label"><fmt:message key="label.funcY"/></label><input type="radio" id="isDefault1" name="isDefault" value="Y" checked="checked">
					<label for="isDefault2" onclick="App.radioCheck(this,'isDefaultDiv')" ><fmt:message key="label.funcN"/></label><input type="radio" id="isDefault2" name="isDefault" value="N">
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.memo"/><br/>(<span id="memoSpan">0/4000</span>)</td>
			<td class="tdRight" colspan="3">
				<textarea class="base-textArea" rows="5" cols="50" id="memo" name="memo" onkeyup="$_showWarnWhenOverLen1(this,4000,'memoSpan')">${func.memo}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<p:button funcId="0101010101" value="提交"/>&nbsp;
				<input type="button" id="backBtn" value="返回"/>
			</td>
		</tr>
	</table>
</form>

<div id="treepanel"></div>
</body>
</html>