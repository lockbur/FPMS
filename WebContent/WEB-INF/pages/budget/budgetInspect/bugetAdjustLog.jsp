<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算调整历史记录</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	//级联回显
	$("#type").combobox();
	
	
}
</script>
</head>
<body>
<p:authFunc funcArray="02070110"/>
<form action="" method="post">
<input type="hidden" name="bgtId" value="${searchInfo.bgtId }">
	<table >
		<tr>
			<th colspan="2">预算调整记录</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">调整类型</td>
			<td  class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="type" name="type" >
						<option value="">请选择</option>
						<option value="1" <c:if test="${searchInfo.type == '1'}">selected="selected"</c:if>>可用调整</option>
						<option value="2" <c:if test="${searchInfo.type == '2'}">selected="selected"</c:if>>占用调整</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" class="tdWhite"><p:button funcId="02070110" value="查找"/></td>
		</tr>
	</table>
	<table class="tableList">
		
		<tr>
			<th width="5%">调整序号</td>
			<th width="10%">调整类型</td>
			<th width="10%">调整值</td>
			<th width="5%">操作用户</td>
			<th width="10%">操作时间</td>
			<th width="30%">调整原因</td>
			<th width="30%">调整说明</td>
		</tr>
		<c:if test="${!empty bugetAdjustList }">
			<c:forEach items="${bugetAdjustList }" var="bean">
				<tr>
					<td>${bean.bgtIdSeq }</td>
					<td><c:if test="${bean.type eq '1' }">可用调整</c:if> <c:if test="${bean.type eq '2' }">占用调整</c:if> </td>
					<td>${bean.operVal }</td>
					<td>${bean.operUser }</td>
					<td>${bean.operDate } ${bean.operTime }</td>
					<td>${bean.operReson}</td>
					<td>${bean.operMemo}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty bugetAdjustList}">
					<tr><td style="text-align: center;" colspan="7"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
	</table>
	</form>
	<p:page/>
	<br/><br/><br/>
	<div>
		<input type="button" value="关闭" onclick="art.dialog.close()"/>
	</div>

</body>
</html>