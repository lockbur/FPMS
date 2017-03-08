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
<title>用户列表</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
}
function forceQuit(userId) {
	url = "<%=request.getContextPath()%>/sysmanagement/user/forceQuitList.do?<%=WebConsts.FUNC_ID_KEY%>=01030702";
	ableCommon(url,"强退",userId);
}
function ableCommon(url,type,userId)
{
	if(userId==""){
		if ($('input[type=checkbox][name=delIds]:checked').size() <= 0) {
			$( "<div>请选择要"+ type +"的用户</div>" ).dialog({
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					Ok: function() {
						$( this ).dialog( "close" );
					}
				}
			});
			return;
		}
		
	}else{
		url = "<%=request.getContextPath()%>/sysmanagement/user/forceQuit.do?<%=WebConsts.FUNC_ID_KEY%>=01030701&quitUserId="+userId;
	}
	
	$( "<div>确认要"+ type +"选定的用户?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#userForm").attr("action",url);
				$("#userForm").submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function resetAll() {
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#deptId").val("");
	$("#fstdeptIdDiv,#seddeptIdDiv").css("display","none");
	
	$("#userTypeDiv").find("label").eq(0).click();
}
</script>
</head>

<body>
<p:authFunc funcArray="010307,01030701,01030702"/>
<form method="post" id="userForm" action="">
	<table id="listTb">
		<tr class="collspan-control">
			<th colspan="4">在线用户查询</th>
		</tr>	
		<tr>
			<td class="tdLeft">员工号</td>
			<td class="tdRight">
				<input type="text" name="userId" value="${queryCondition.userId}" class="base-input-text"/>
			</td>
			<td class="tdLeft"><fmt:message key="label.user.userName"/></td>
			<td class="tdRight">
				<input type="text" name="userName" value="${queryCondition.userName}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">所属责任中心</td>
			<td class="tdRight" colspan="3" >
				<forms:OrgSelPlugin   rootNodeId="${org21Code}"  jsVarGetValue="dutyCode"   ableQuery="true"  initValue="${queryCondition.dutyCode}" parentCheckFlag="false"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="010307" value="查找"/>
				<input type="button" value="强退" onclick="forceQuit('');">
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<tr>
			<th width="3%"><input type="checkbox" onclick="Tool.toggleCheck(this, 'delIds')"/></th>
			<th width="15%"><fmt:message key="label.user.userId"/></th>
			<th width="15%"><fmt:message key="label.user.userName"/></th>
			<th width="30%">所属机构</th>
			<th width="30%">责任中心</th>
			<th width="7%">强退</th>
		</tr>
		<c:forEach items="${onLineUserList}" var="userInfo">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>
					<c:if test="${userInfo.userId != userId}">
						<input name="delIds" type="checkbox" value="${userInfo.userId}"/>
					</c:if>
				</td>
				<td>${userInfo.userId}</td>
				<td>${userInfo.userName}</td>
				<td>${userInfo.orgName }</td>
				<td>${userInfo.dutyName }</td>
				<td>
					<c:if test="${userInfo.userId != userId}">
						<div class="update"><a href="javascript:void(0)"  onclick="forceQuit('${userInfo.userId}');" title="强退"></a></div>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty onLineUserList}">
				<tr>
					<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
	</table>
</form>
<p:page/>
</body>
</html>