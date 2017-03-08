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
<title>集群锁列表</title>

<script type="text/javascript">

//页面初始化
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
}

//详情
function deleteLock(taskType1,taskSubType1){
	var taskType = taskType1;
	var taskSubType = taskSubType1;
	$("<div>是否确认删除集群锁？</div>").dialog({
		resizable: false,
		height:165,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				var form = $('#lockForm')[0];
				form.action = '<%=request.getContextPath()%>/cluster/lock/deleteClusterLock.do?<%=WebConsts.FUNC_ID_KEY%>=010901&taskType='
						+taskType+"&taskSubType="+taskSubType;
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
</script> 
</head>

<body>
<p:authFunc funcArray="010901"/>
<form action="" method="post" id="lockForm">
	<br/>	
	<div id="listDiv" style="width: 100%; overflow-X: auto; position: relative; float: right">

	<table id="listTab" class="tableList">		
		<tr>		
			<th width="10%">任务类型</th>
			<th width="10%">任务子类</th>
			<th width="15%">IP地址</th>
			<th width="10%">操作柜员</th>
			<th width="10%">开始日期</th>	
			<th width="10%">开始时间</th>	
			<th width="25%">备注</th>	
			<th width="10%">操作</th>
		</tr>
		<c:forEach items="${cList}" var="c" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				<td>${c.taskType}</td>								
				<td>${c.taskSubType}</td>	
				<td>${c.ipAddress}</td>					
				<td>${c.instOper}</td>	
				<td>${c.instDate}</td>
				<td>${c.instTime}</td>	
				<td>${c.memo}</td>
				<td >
					<c:if test="${c.delFlag eq 'DELETE'}">
	  				    <a href="#" onclick="deleteLock('${c.taskType}','${c.taskSubType}');" ><img class="delete imageBtn" border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/></a>
	  				</c:if>
				</td>	
			</tr>
		</c:forEach>
		<c:if test="${empty cList}">
				<tr>
					<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
	</table>
</div>
</form>
<p:page/>
</body>
</html>