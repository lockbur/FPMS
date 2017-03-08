<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.tool.ConstantMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>受益金额测算金额查看</title>
<base target="_self">
</head>
<body>
<form action="" method="post"  id="feeTypeForm">	
<div id="targetDiv">
	<table id="targetT" class="tableList">
		<tr id="th">
			<th width="10%">受益年月</th>
			<th width="7%">序号</th>
			<th width="15%">费用承担部门</th>
			<th width="20%">物料名称</th>
			<th width="8%">核算码</th>
			<th width="10%">参考</th>
			<th width="10%">专项</th>
			<th width="10%">测算金额</th>
			<th width="10%">受益金额</th>
		</tr>
		<c:forEach items="${queryFeeTypeList}" var="feeInfo">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" class="trOther">
				<td>${feeInfo.feeYyyymm }</td>
				<td>${feeInfo.subId }</td>
				<td>${feeInfo.feeDeptName }</td>
				<td>${feeInfo.matrName }</td>
				<td>${feeInfo.cglCode }</td>
				<td>${feeInfo.referenceName }</td>
				<td>${feeInfo.specialName }</td>
				<td>${feeInfo.cglCalAmt }</td>
				<td>${feeInfo.cglFeeAmt }</td>
			</tr>
		</c:forEach>
		<c:if test="${empty queryFeeTypeList}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</div>	
<br/>
<div>
	<input type="button"  value="关闭"  onclick="art.dialog.close();" />
</div>
	
</form>
</body>
</html>