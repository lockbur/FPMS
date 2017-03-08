	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>回冲历史查询</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	
 	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
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

function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray="040604"/>
<form action="" method="post" id="queryForm" action="<%=request.getContextPath()%>/amortization/tradeBackwash/washbackHistory.do?<%=WebConsts.FUNC_ID_KEY%>=040604">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				回冲历史查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${backWashBean.cntNum}" class="base-input-text"/>
			</td>
			<td class="tdLeft">操作用户</td>
			<td class="tdRight">
				<input type="text" name="operUser" value="${backWashBean.operUser}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">操作日期区间</td>
			<td class="tdRight" colspan="3">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${backWashBean.befDate}" class="base-input-text" style="width:145px;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${backWashBean.aftDate}" class="base-input-text" style="width:145px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040604" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
</form>
	<br>
	<table class="tableList">
			<tr>
				<th width="10%">受益年月</th>
				<th width="30%">合同号</th>
				<th width="10%">状态</th>
				<th width="10%">操作用户</th>
				<th width="20%">操作日期</th>
				<th width="20%">操作时间</th>
			</tr>
		<c:forEach items="${bwHistory}" var="h">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${h.feeYyyymm}</td>
				<td>${h.cntNum}</td>
				<td>
				    <c:if test="${h.dataFlag == '0'}">录入</c:if>
				    <c:if test="${h.dataFlag == '1'}">已处理</c:if>
				</td>
				<td>${h.operUser}</td>
				<td>${h.operDate}</td>
				<td>${h.operTime}</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty bwHistory}">
			<tr>
				<td colspan="6" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>

<p:page/>
</body>
</html>