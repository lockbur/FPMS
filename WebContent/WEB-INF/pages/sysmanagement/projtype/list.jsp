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
<title>项目类型维护</title>
<style type="text/css">
</style>
<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<script type="text/javascript">

function pageInit(){
	
}
	
function update(paramValue){
	var form = $("#update")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/projType/update.do?<%=WebConsts.FUNC_ID_KEY%>=081401&paramValue="+paramValue;
	form.submit();
	
}

function add(){
	var form = $("#add")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/projType/add.do?<%=WebConsts.FUNC_ID_KEY%>=081402";
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
	<form method="post" id="delete" action="">
		<input type="hidden" id="paramValue" name="paramValue" class="base-input-text"/>
	</form>
	<form method="post" id="add" action=""></form>
	<form method="post" id="update" action=""></form>
	<form method="post" id="projTypeForm" action="">
	<p:authFunc funcArray="0814,081402" />
		<table>
			<tr class="collspan-control">
				<th colspan="4">项目类型查询</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">名称</td>
				<td class="tdRight" width="25%" colspan="3">
					<input type="text" id="paramName" name="paramName" value="${projTypeBean.paramName}" class="base-input-text"/>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="0814" value="查找"/>
				<input type="button" value="新增" onclick="add();">
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
		</table>
		<br />
		<table class="tableList">
			<tr>
				<th width="10%">序号</th>
				<th width="20%">名称</th>
				<th width="5%">状态</th>
				<th width="10%">新增用户</th>
				<th width="10%">新增日期</th>
				<th width="10%">新增时间</th>
				<th width="10%">更新用户</th>
				<th width="10%">更新日期</th>
				<th width="10%">更新时间</th>
				<th width="5%">操作</th>
			</tr>
			<c:forEach items="${list}" var="list">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');"onmouseout="setTrBgClass(this, 'trOther');">
					<td>${list.projId}</td>
					<td>${list.paramName}</td>
					<td>${list.useFlag}</td>
					<td>${list.instUser}</td>
					<td>${list.instDate}</td>
					<td>${list.instTime}</td>
					<td>${list.updUser}</td>
					<td>${list.updDate}</td>
					<td>${list.updTime}</td>
					<td>
						<div class="update">
							<a href="javascript:void(0)" onclick="update('${list.paramValue}');" title="<%=WebUtils.getMessage("button.update")%>"></a>
						</div>
						<%-- <a onclick="del('${list.paramValue}');" title='<%=WebUtils.getMessage("button.delete")%>'>
							<img class="delete imageBtn" border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/>
						</a> --%>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
				<tr>
					<td colspan="10" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
	</form>
	<p:page />
</body>
</html>