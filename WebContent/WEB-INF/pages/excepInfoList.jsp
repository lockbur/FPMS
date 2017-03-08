<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="json/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<style type="text/css">
</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>异常信息详情列表</title>
<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	infoHide(28);
	});
	
//全选
function checkAll(){
	if($("#checkItem").is(":checked")){
		$("input[name='excepId']").prop('checked','true');
	}else{
		$("input[name='excepId']").removeAttr("checked");
	}
}

function haveRead(){
	if($("input[name='excepId']:checked").size() < 1){
		App.notyError("请选择已阅的异常数据！");
		return false;
	}
	var excepInfoChecked = new Array();
	for(var i = 0; i<$("input[name='excepId']:checked").size();i++){
		excepInfoChecked[i] = $("input[name='excepId']:checked")[i].value;
	}
	var url = "homepage/excepInfoHaveRead.do?<%=WebConsts.FUNC_ID_KEY %>=000112&excepInfoChecked="+excepInfoChecked;
	App.ajaxSubmit(
			url, 
			{
				async:false
			}, 
			function(data)
			{
				getReturnValue(data.excepInfoNumb,data.excepInfos,data.excepIds);
			}, 
			function(failureMsg,data)
			{
				alert(failureMsg);
			}
		);	
}

//设置页面关闭时返回值
function getReturnValue(excepInfoNumb,excepInfos,excepIds){
	if((excepInfoNumb!=null&&excepInfoNumb!="")){
		art.dialog.data('excepInfoNumb', excepInfoNumb);
		art.dialog.data('excepInfos', excepInfos);
		art.dialog.data('excepIds', excepIds);
		art.dialog.close();
	}else{
		App.notyError("操作失败，后台传来的数据为空");
	}
}

function checkTr(obj){
	if($(obj).find(":checkbox").is(":checked")){
		$(obj).find(":checkbox").removeAttr("checked");
	}else{
		$(obj).find(":checkbox").prop('checked','true');			
	}
}

function check(obj){
	if($(obj).is(":checked")){
		$(obj).removeAttr("checked");
	}else{
		$(obj).prop('checked','true');	
	}
}

</script>
</head>
<body>
	<form method="post" id="rollInfoUpdateForm">
		<table class="tableList">
		<tr>
			<th width="10%"><input type="checkbox" id="checkItem" onclick="checkAll()"></th>
			<th width="20%">标题</th>
			<th width="15%">操作时间</th>
			<th width="55%">异常信息</th>
		</tr>
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="list">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" onclick="checkTr(this)" class="trOther" style="cursor:pointer">
					<td style="text-align: center;">
			    		<input type="checkbox" name="excepId" value="${list.excepId}" onclick="check(this)">
			    	</td>
					<td>${list.excepTitle}</td>
					<td>${list.addTime}</td>
					<td class="tdInfo">${list.excepInfo}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list}">
			<tr>
				<td colspan="4" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
		<tr>
			<td colspan="4" class="tdWhite tdc">
				<input type="button" value="已阅" onclick="haveRead()" />
				<input type="button" value="关闭" onclick="art.dialog.close()" />
			</td>
		</tr>
		</table>
	</form>
	<p:page/>
</body>
</html>