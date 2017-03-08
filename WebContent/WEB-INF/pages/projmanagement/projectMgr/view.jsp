<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目明细</title>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	if('${proj.scope}' == '3')
	{
		$("#hiddenTr").attr("style","display: table-row;");	
	}
}
</script>
</head>
<body>
<form action="" method="post" id="ProjForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">
				项目编号：${proj.projId}
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%">创建年份</td>
			<td class="tdRight" width="50%">
				${proj.startYear}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">项目类型</td>
			<td class="tdRight">
				${proj.projTypeName}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">项目名称</td>
			<td class="tdRight">
				${proj.projName}
			</td>
		</tr>
		<tr>
		    <td class="tdLeft">项目预算（人民币）</td>
			<td class="tdRight">
				<fmt:formatNumber type="number" value="${proj.budgetAmt}" />
			</td>
		</tr>
		<tr>
		    <td class="tdLeft">合同金额</td>
			<td class="tdRight">
				<c:choose>
				  <c:when test="${empty proj.cntTotalAmt}">
				  0
				  </c:when>
				  <c:otherwise>
				  <fmt:formatNumber type="number" value="${proj.cntTotalAmt}" />
				  </c:otherwise>
				  </c:choose>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">安全性</td>
			<td class="tdRight">
				<c:if test="${proj.scope == 1}">全省</c:if>
				<c:if test="${proj.scope == 2}">二级行辖内</c:if>
				<c:if test="${proj.scope == 3}">指定责任中心</c:if>
			</td>
		</tr>
		<tr id="hiddenTr" style="display: none;">
			<td class="tdLeft">责任中心</td>
			<td class="tdRight">
			   <forms:OrgSelPlugin rootLevel="1" radioFlag="false" showInputFlag="false" leafOnlyFlag="true" initValue="${proj.dutyCode}"/> 
			</td>
		</tr>
		<tr>
		   <td class="tdLeft" >项目描述</td>
		    <td class="tdRight" >
		       ${proj.projDesc}
		    </td>
		</tr>
		<tr>
		   <td class="tdLeft" >起始时间</td>
		    <td class="tdRight" >
		       ${proj.startDate}
		    </td>
		</tr>
		<tr>
		   <td class="tdLeft" >终止日期</td>
		    <td class="tdRight" >
		       ${proj.endDate}
		    </td>
		</tr>
		<tr>
		   <td class="tdLeft" >操作人员</td>
		    <td class="tdRight" >
		       ${proj.operUser}
		    </td>
		</tr>
		<tr>
		   <td class="tdLeft" >操作责任中心代码</td>
		    <td class="tdRight" >
		       ${proj.operDutyCode}
		    </td>
		</tr>
		<tr>
		   <td class="tdLeft" >操作时间</td>
		    <td class="tdRight" >
		       ${proj.operDate}&nbsp;${proj.operTime}
		    </td>
		</tr>		
		<tr>
			<td colspan="2" class="tdWhite">
				<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
	</table>
	<br>
</form>
</body>
</html>