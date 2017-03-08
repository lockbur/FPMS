<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数修改历史信息列表</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
}
function pass(paramVarName){
	$("#passDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"通过": function() {
				var form = $("#parameterForm");
				$("#paramVarName").val(paramVarName);
				form.attr('action', '<%=request.getContextPath()%>/sysmanagement/parameter/pass.do?<%=WebConsts.FUNC_ID_KEY%>=01050201');
				App.submit(form);
			},
			"退回":function(){
				var form = $("#parameterForm");
				$("#paramVarName").val(paramVarName);
			 	form.attr('action', '<%=request.getContextPath()%>/sysmanagement/parameter/refuse.do?<%=WebConsts.FUNC_ID_KEY%>=01050202');
				App.submit(form);				
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#passDiv").dialog( "option", "title", "参数审批" ).dialog( "open" );	
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
<p:authFunc funcArray="010502,01050201,01050202"/>
	<br/>
	<table class="tableList">
	<tr>
		<th>参数名称</th>
		<th>修改前参数值</th>
		<th>修改后参数值</th>
		<th>修改人</th>
		<th>修改时间</th> 
		<th>审批人</th>
		<th>审批时间</th> 
		<th>审批结果</th> 
	</tr>
	<c:forEach items="${applyHisList}" var="list">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');"  onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
			<td width="10%">${list.paramDispName}</td>
			<td width="20%">${list.paramOrigValue}</td>
			<td width="20%">${list.paramUpdateValue}</td>
			<td width="10%">${list.applyUserId}</td>
			<td width="10%">${list.applyTime}</td>
			<td width="10%">${list.approveUserId}</td>
			<td  width="10%">${list.approveTime}</td>
			<td  width="10%">
				<c:if test="${list.applyStatus=='1'}">退回</c:if>
				<c:if test="${list.applyStatus=='2'}">通过</c:if>
			</td>
		</tr>
	</c:forEach>
		<c:if test="${empty applyHisList}">
	    <tr>
		<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	
	</table>
    <p:page/>
	<div id="passDiv" style="display:none">
		<form action="" method="post" id="passForm">
		<p>点击通过，参数修改生效并更新参数表；点击退回，参数修改不生效；取消关闭对话框！</p>
		</form>
	</div>	
	<br/>
	<br/>
	<br/>
	  <input type="button" value="返回" onclick="backToLastPage('${uri}');">
	
</body>
</html>