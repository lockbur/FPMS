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
<title></title>
<script type="text/javascript">
function haveRead(){
	var excepId=$("#excepId").val();
	var data={};
	data['excepId'] =  excepId;
	var url = "homepage/excepInfoHaveRead.do?<%=WebConsts.FUNC_ID_KEY %>=000112";
	App.ajaxSubmit(
			url, 
			{
				data:data,async:false
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
</script>
</head>
<body>
	<form method="post" id="rollInfoUpdateForm">
	<input type="hidden" name="excepId" id="excepId" value="${excepInfo.excepId}"/>
	<p:authFunc funcArray="081304" />
		<table>
			<tr>
				<th colspan="4">异常信息</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">名称</td>
				<td class="tdRight" width="25%">
					${excepInfo.excepTitle}
				</td>
				<td class="tdLeft" width="25%">创建时间</td>
				<td class="tdRight" width="25%">
					${excepInfo.addTime}
				</td>
			</tr>
			<tr align="left">  
				<td class="tdLeft" width="25%">可见机构</td>
				<td class="tdRight" width="25%">
					${excepInfo.excepOrgId}
				</td>
				<td class="tdLeft" width="25%">创建人</td>
					
				<td class="tdRight" width="25%">${excepInfo.addUid}</td>
			</tr>
<!-- 				<td class="tdLeft" width="25%"></td> -->
<%-- 				<td class="right" width="25%">${roll.addUid}</td> --%>
			<tr>
				<td class="tdLeft">内容</td>
				<td class="tdRight" colspan="3">
					${excepInfo.excepInfo}
				</td>
			</tr>
			<tr><td colspan="4" class="tdWhite tdc">
				<input type="button" value="已阅" onclick="haveRead()" />
				<input type="button" value="关闭" onclick="art.dialog.close()" />
			</td></tr>
		</table>
		
	</form>
	<p:page/>
</body>
</html>