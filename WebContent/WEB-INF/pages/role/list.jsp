<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色查询</title>
<script type="text/javascript">
function authorise(roleId) {
	var form = $('#tempForm')[0];
	form.action = '<%=request.getContextPath()%>/role/preAuthorise.do?<%=WebConsts.FUNC_ID_KEY%>=01020101';
	$('#roleId').val(roleId);
	App.submit(form);
}

function checkIfRoleUsed(roleId){
	var data={};
	data['roleId']=roleId;
	var url= "role/checkIfRoleUsed.do?<%=WebConsts.FUNC_ID_KEY%>=0102010201";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
		list = data.isExist;
});
	return list;
}

function toDel(roleId)
{
	var url='<%=request.getContextPath()%>/role/delRole.do?<%=WebConsts.FUNC_ID_KEY%>=01020102&roleId='+roleId;
	if (checkIfRoleUsed(roleId)) {
		$( "<div>该角色已被用户使用，确认要删除选定的角色?</div>" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					$("#tempForm").attr("action",url);
					$("#tempForm").submit();
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		return false;
	}
	$( "<div>该角色无用户使用，确认要删除选定的角色?</div>" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#tempForm").attr("action",url);
				$("#tempForm").submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
</script>
</head>

<body>
<p:authFunc funcArray="010201,01020101,01020102,01020105,0102010201"/>
<form action="" method="post" id="tempForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				角色查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft"><fmt:message key="label.roleId"/></td>
			<td class="tdRight">
				<input type="text" id="roleId" name="roleId" value="${role.roleId}" class="base-input-text"/>
			</td>
			<td class="tdLeft prompt"><fmt:message key="label.roleName"/></td>
			<td class="tdRight">
				<input type="text" name="roleName" value="${role.roleName}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="010201" value="查找"/>
<%-- 				<p:button funcId="01020102" value="删除" onclick="toDel"/> --%>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList">
			<tr>
<!-- 				<th><input type="checkbox" onclick="Tool.toggleCheck(this, 'delIds')"/></th> -->
				<th width="20%">角色ID</th>
				<th width="20%">角色名称</th>
				<th width="20%">角色使用范围</th>
				<th width="10%">录入人</th>
				<th width="10%">录入日期</th>
				<th width="20%">操作</th>
			</tr>
		<c:forEach items="${roleList}" var="roleItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
<!-- 				<td style="text-align: center"> -->
<%-- 					<c:if test="${roleItem.enableDel=='0'}"><input name="disabledDel" type="checkbox" disabled="disabled"/></c:if> --%>
<%-- 					<c:if test="${roleItem.enableDel=='1'}"><input id="delIds" name="delIds" type="checkbox" value="${roleItem.roleId}"/></c:if> --%>
<!-- 				</td> -->
				<td>${roleItem.roleId}</td>
				<td>${roleItem.roleName}</td>
				<td>
					<c:if test="${roleItem.roleLevel=='01'}">总行</c:if>
					<c:if test="${roleItem.roleLevel=='02'}">全局</c:if>
				</td>
				<td>${roleItem.instUser}</td>
				<td>${roleItem.instDate}</td>
				<c:choose>
					<c:when test="${roleItem.roleId=='C110'}">
						<td  align="center">
							<div class="update" style="margin-left: 50px;">
							    <a href="#" onclick="authorise('${roleItem.roleId}');" title='<%=WebUtils.getMessage("button.update")%>'></a>
							</div>
						</td>
					</c:when>
					<c:otherwise>
						<td  align="center">
							<div class="update" style="margin-left: 50px;">
							    <a href="#" onclick="authorise('${roleItem.roleId}');" title='<%=WebUtils.getMessage("button.update")%>'></a>
							</div>
							<div class="delete" style="margin-left: 50px;">
								<a href="javascript:void(0)" onclick="toDel('${roleItem.roleId}');" title='<%=WebUtils.getMessage("button.delete")%>'></a>
							</div>
						</td>
					</c:otherwise>
				</c:choose>
				
			</tr>
		</c:forEach>
		<c:if test="${empty roleList}">
				<tr>
					<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>