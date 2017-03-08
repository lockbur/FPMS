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
<title>长短期待摊核算码维护</title>
<style type="text/css">
</style>
<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<script type="text/javascript">

function pageInit(){
	
}
	
function update(cglCode){
	var form = $("#update")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/matrtype/preEditPrepaidCode.do?<%=WebConsts.FUNC_ID_KEY%>=01080703&cglCode="+cglCode;
	form.submit();
	
}

function add(){
	var form = $("#add")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/matrtype/preAddPrepaidCode.do?<%=WebConsts.FUNC_ID_KEY%>=01080701";
	form.submit();
}
function del(paramValue){
	var form=document.forms[0];
	$( "<div>确认要删除 此条信息？</div>" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				form.paramValue.value=paramValue;
				form.action = "<%=request.getContextPath()%>/sysmanagement/projType/del.do?<%=WebConsts.FUNC_ID_KEY%>=081405";
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function doValidate(){
	return true;
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
	<form method="post" id="add" action=""></form>
	<form method="post" id="update" action=""></form>
	<form method="post" id="projTypeForm" action="">
	<p:authFunc funcArray="010807,01080701" />
		<table>
			<tr class="collspan-control">
				<th colspan="4">费用核算码查询</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">费用核算码</td>
				<td class="tdRight" width="25%" colspan="3">
					<input type="text" id="cglCode" name="cglCode" value="${cglCode}" class="base-input-text" maxlength="4"/>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="010807" value="查找"/>
				<input type="button" value="新增" onclick="add();">
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
		</table>
		<br />
		<table class="tableList">
			<tr>
				<th width="20%">费用核算码</th>
				<th width="20%">短期待摊核算码</th>
				<th width="20%">长期待摊核算码</th>
				<th width="30%">长期待摊固定对应的费用核算码</th>
				<th width="10%">操作</th>
			</tr>
			<c:forEach items="${mtList}" var="list">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');"onmouseout="setTrBgClass(this, 'trOther');">
					<td>${list.cglCode}</td>
					<td>${list.shortPrepaidCode}</td>
					<td>${list.longPrepaidCode}</td>
					<td>${list.longPrepaidCodeFee}</td>
					<td>
						<div class="update">
							<a href="javascript:void(0)" onclick="update('${list.cglCode}');" title="<%=WebUtils.getMessage("button.update")%>"></a>
						</div>
						<%-- <a onclick="del('${list.paramValue}');" title='<%=WebUtils.getMessage("button.delete")%>'>
							<img class="delete imageBtn" border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/>
						</a> --%>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty mtList}">
				<tr>
					<td colspan="5" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
	</form>
	<p:page />
</body>
</html>