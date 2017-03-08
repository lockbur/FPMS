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
<title>预算分解查询</title>
<style type="text/css">
	.base-input-text-other{
		width: 200px;
		height: 20px;
		background-color: white;
 		font-family: Arial;
/* 		font-family:msyh; */
		font-size: 12px;
		vertical-align: middle;
		border: #D8D4C4 solid 1px;
		padding-bottom: 0px;
		padding-top: 0px;
		padding-left: 3px;
	}
</style>

<script type="text/javascript">
function pageInit()
{
	App.jqueryAutocomplete();
// 	var tc = new TableCombineObj("resolveDetail", 1, null, 0, 2, null, null, null, null);
// 	tc.rowCombine();
}

</script>
</head>

<body>
<p:authFunc funcArray="02060201"/>
<form action="" method="post" id="resolveForm">
	<table>
		<tr>
			<th colspan="6">
				预算分解信息
<!-- 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				【测试用】模板ID： -->
				<input type="hidden" name="tmpltId" value="${budgetReplyed.tmpltId}">
				<input type="hidden" name="montCode" value="${budgetReplyed.montCode}"> 
			</th>
		</tr>
		
		<tr>
			<td class="tdLeft"  style="width:10%;">监控指标</td>
			<td class="tdRight" style="width:20%;" title="${budgetReplyed.montCode}">
				${budgetReplyed.montName}
			</td>
			<td class="tdLeft"  style="width:10%;">批复金额</td>
			<td class="tdRight" style="width:20%;">
				<input type="hidden" id="replyAmt" name="replyAmt" value="${budgetReplyed.replyAmt}"> 
				${budgetReplyed.replyAmt}
			</td>
			<td class="tdLeft"  style="width:10%;">已分解金额</td>
			<td class="tdRight" style="width:30%;" id="splitedAmtTd">
				${budgetReplyed.splitedAmt}
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList" id="resolveDetail">
		<tr class="collspan-control">
			<th colspan="3" style="text-align: center">预算分解明细</th>
		</tr>
		<tr>
			<th width="30%">支行/部门</th>
			<th width="40%">物料</th>
			<th width="30%">分解金额</th>
		</tr>
		
		<c:forEach items="${resolveDetailList}" var="resolveDetail">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
				<td title="${resolveDetail.dutyCode}">
					<input type="hidden" name="dutyCode" value="${resolveDetail.dutyCode}">
					${resolveDetail.dutyName}
				</td>
				<td title="${resolveDetail.matrCode}">
					<input type="hidden" name="matrCode" value="${resolveDetail.matrCode}">
					${resolveDetail.matrName}
				</td>
				<td>
					<input type="hidden" name="sumAmt" value="${resolveDetail.sumAmt}">
					${resolveDetail.sumAmt}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${ empty resolveDetailList}">
			<tr>
				<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
	<p></p>
	<p></p>
	<input style="text-align: center" type="button" value="返回" onclick="backToLastPage('${uri}');">
</form>
</body>
</html>