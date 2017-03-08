<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>功能修改</title>
<script type="text/javascript">
function pageInit() {
// 	$('#isMenu')[0].value = '${func.isMenu}';
// 	$('#dependParent')[0].value = '${func.dependParent}';
// 	$('#needLogin')[0].value = '${func.needLogin}';
// 	$('#isDisplay')[0].value = '${func.isDisplay}';
// 	$('#isDefault')[0].value = '${func.isDefault}';
	
	
	<%
	String jsonData = WebUtils.getRequestAttr("funcTree");
	%>
	var treeObj = <%=Tool.CHECK.isEmpty(jsonData) ? "null" : jsonData%>;
	
	if (treeObj != null) {
		tree = new dhtmlXTreeObject("treepanel","100%","100%",0);
		tree.setImagesPath('<%=request.getContextPath()%>/component/dtree/css/imgs/');
		tree.enableTreeLines(true);
		tree.enableDragAndDrop(true, false);
		tree.setDragBehavior('complex', true);
		tree.makeAllDraggable();
		tree.attachEvent("onDrag", function(sId,tId,id,sObject,tObject){
			return confirm("确认要移动指定功能？");
		});
		tree.attachEvent("onDrop", function(sId,tId,id){
			var form = $('#funcForm')[0];
			form.action = App.ROOT + 'function/move.do?' + App.FUNC_KEY + '=FUNC_MOVE';
			var subItems = tree.getSubItems(tId);
			var subItemsArray = subItems.split(',');
			var targetFuncList = {};
			for (var i = 0; i < subItemsArray.length; i++) {
				$('<input type="hidden" name="funcIds" value="' + subItemsArray[i] + '"/>').appendTo(form);
				$('<input type="hidden" name="funcSeqs" value="' + (i + 1) + '"/>').appendTo(form);
			}
			$('#funcId').val(sId);
			$('#parentId').val(tId);
			form.submit();
// 			console.log('onDrop');			
		});
		tree.enableCheckBoxes(true);
// 		tree.enableThreeStateCheckboxes(true);
		tree.loadJSONObject(treeObj);
		//tree.openAllItems(0);
		//tree. setOnDblClickHandler("treeOnDblClick");
// 		tree.setOnClickHandler(menuclick);
	}
	
	
	
	
	
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
	
	$( '#treep' ).hide();
	if (menuArray != null) {
		var tree = new dhtmlXTreeObject("treep","100%","100%",0);
		tree.setImagesPath('<%=request.getContextPath()%>/component/dtree/css/imgs/');
		tree.loadJSArray(menuArray);
		tree.setOnClickHandler(function(id, pid, text) {
			var parentId = (id == '0' ? '' : id);
			$( '#parentFuncDisp' ).val(this.getSelectedItemText() + '(' + parentId + ')');
			$( '#parentFuncId' ).val(parentId); 
			$( "#treep" ).dialog("close");
		}); 
	}
	$( '#parentFuncDisp' ).click(function(){
		showTree();
	});
	
	function showTree() {
		$( "#treep" ).dialog({
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
<p:authFunc funcArray="0101010201,010101"/>
<form action="<%=request.getContextPath() %>/function/update.do" method="post" id="updateForm">
	<p:token/>
	<input type="hidden" id="parentId" name="parentId"/>
	<input type="hidden" id="funcId" name="funcId"/>
	<input type="hidden" id="delIds" name="delIds"/>
	<table>
		<tr>
			<td  width="35%">
			<div   style="width:100%;height:450px;overflow: scroll;">
				<div id="treepanel">
				
				</div>
			</div>
			</td>
			<td width="10%" valign="middle">
				<table  style="border: 0px;" >
				</table>
			</td>
			<td width="55%"  valign="top">
				<table id="subtable">
		<thead>
			<tr>
				<th colspan="2" class="thTop">
					功能修改
				</th>
			</tr>
		</thead>
		<tbody>
		
			<tr>
				<td class="tdLeft"><fmt:message key="label.parentId"/></td>
				<td class="tdRight">
					<c:out value="${func.parent.funcName}(${func.parentFuncId})"></c:out>
					<form:hidden id="parentFuncId" path="func.parentFuncId"/>
				</td>
				
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.funcId"/></td>
				<td class="tdRight">
					<c:out value="${func.funcId}"></c:out>
				</td>
				
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.funcName"/></td>
				<td class="tdRight">
					<form:input path="func.funcName" id="funcName" class="base-input-text"/>
				</td>
				
			</tr>
			
			<tr>
				<td class="tdLeft"><fmt:message key="label.iconPath"/></td>
				<td class="tdRight">
					<form:input path="func.iconPath" id="iconPath" class="base-input-text"/>
				</td>
			</tr>
			
			
			<tr>
				<td class="tdLeft"><fmt:message key="label.url"/></td>
				<td class="tdRight">
					<form:input id="url" path="func.url" class="base-input-text"/>
				</td>
			</tr>	
			<tr>
				<td class="tdLeft"><fmt:message key="label.isMenu"/></td>
				<td class="tdRight">
				<select id="isMenu" name="isMenu" onchange="" >
									<option  value="" >请选择</option>
									<option value="Y" <c:if test="${'Y'==func.isMenu}">selected="selected"</c:if>>是</option>
									<option value="N" <c:if test="${'N'==func.isMenu}">selected="selected"</c:if>>否</option>
				</select>
				
					
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.needLogin"/></td>
				<td class="tdRight">
					<select id="needLogin" name="needLogin" onchange="" >
									<option  value="">请选择</option>
									<option value="Y" <c:if test="${'Y'==func.needLogin}">selected="selected"</c:if>>是</option>
									<option value="N" <c:if test="${'N'==func.needLogin}">selected="selected"</c:if>>否</option>
				</select>
					
				</td>
			</tr>	
			<tr>	
				<td class="tdLeft"><fmt:message key="label.dependParent"/></td>
				<td class="tdRight">
				<select id="dependParent" name="dependParent" onchange="" >
									<option  value="">请选择</option>
									<option value="Y" <c:if test="${'Y'==func.dependParent}">selected="selected"</c:if>>是</option>
									<option value="N" <c:if test="${'N'==func.dependParent}">selected="selected"</c:if>>否</option>
				</select>
				
				
					
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.isDisplay"/></td>
				<td class="tdRight">
				<select id="isDisplay" name="isDisplay" onchange="" >
									<option  value="">请选择</option>
									<option value="Y" <c:if test="${'Y'==func.isDisplay}">selected="selected"</c:if>>是</option>
									<option value="N" <c:if test="${'N'==func.isDisplay}">selected="selected"</c:if>>否</option>
				</select>
					
				</td>
			</tr>	
			<tr>	
				<td class="tdLeft"><fmt:message key="label.isDefault"/></td>
				<td class="tdRight">
					<select id="isDefault" name="isDefault" onchange="">
									<option  value="">请选择</option>
									<option value="Y" <c:if test="${'Y'==func.isDefault}">selected="selected"</c:if>>是</option>
									<option value="N" <c:if test="${'N'==func.isDefault}">selected="selected"</c:if>>否</option>
				</select>
					
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.memo"/></td>
				<td class="tdRight">
					<form:textarea cssClass="base-textArea" path="func.memo" id="memo" rows="5" cols="50"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<p:button funcId="0101010201"/> 
				<p:button funcId="010101" value="取消更新"/>
				</td>
			</tr>
			
		</tbody>
		
	</table>
<!-- 	<div id="add" style="display:none;"> -->
<%-- 				<p:button funcId="0101010101" value="提交"/> --%>
<%-- 				<p:button funcId="010101" value="取消新增"/> --%>
			
<!-- 			</div> -->
			</td>
		</tr>
		
	</table>
</form>












<div id="treep">
</div>
</body>
</html>