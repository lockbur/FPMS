<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<title>预算冻结失败详情</title>
<script type="text/javascript">
</script>
</head>

<body>
<form action="" method="post" id="">
	<table class="tableList">
		<tr>
			<th width='20%'>物料名称 </th>
			<th width='20%'>费用承担部门</th>
			<th width='20%'>监控指标</th>
			<th width='10%'>参考</th>
			<th width='10%'>专项</th>
			<th width='20%'>失败原因 </th>
		</tr>
		<c:if test="${!empty bgtList}">
			<c:forEach items="${bgtList}" var="cntItem">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc">${cntItem.matrName}</td>
					<td class="tdc">${cntItem.feeDeptName}</td>
					<td class="tdc">${cntItem.montName}</td>
					<td class="tdc">${cntItem.referenceName}</td>
					<td class="tdc">${cntItem.specialName}</td>
					<td class="tdc">${cntItem.memo}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty bgtList}">
			<tr><td style="text-align: center;" colspan="6"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
	</table>
	<br/>
	<div>
    	<input type="button" value="关闭" onclick="art.dialog.close();">
	</div>
</form>
<p:page/>
</body>
</html>