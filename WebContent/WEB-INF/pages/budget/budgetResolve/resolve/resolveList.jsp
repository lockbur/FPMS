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
<script type="text/javascript">
/**
 * 页面初始化时执行
 */
function pageInit()
{
	App.jqueryAutocomplete();
	$("#dataType").combobox();
	$("#dataAttr").combobox();
	$("#dataYear").combobox();
	
// 	var tc = new TableCombineObj("resolveTable", 1, null, 0, 3, null, null, null, null);
// 	tc.rowCombine();
}

/**
 * 获取并跳转进入选中的预算分解详情(参数:预算模板ID、监控指标编码)
 */
function getResolve(tmpltId , montCode)
{
	var form = $("#resolveForm")[0];
	var url = "<%= request.getContextPath() %>/budget/budgetResolve/resolve/getResolve.do?<%=WebConsts.FUNC_ID_KEY %>=02060101&tmpltId="+tmpltId+"&montCode="+montCode;
	form.action = url;
	form.submit();
	form.action = "";
}

/**
 * 重置清空所有选中信息
 */
function resetAll(){
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
<p:authFunc funcArray="020601"/>
<form action="" method="post" id="resolveForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				预算分解查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft"  width="20%">预算类型</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="dataType" name="dataType" >
						<option value="">请选择</option>
						<option value="0" <c:if test="${budgetReplyed.dataType == '0'}">selected="selected"</c:if>>年初预算</option>
						<option value="1" <c:if test="${budgetReplyed.dataType == '1'}">selected="selected"</c:if>>追加预算</option>
					</select>
				</div>
			</td>
			<td class="tdLeft"  width="20%">有效年份</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="dataYear" name="dataYear">
							<option value="">请选择</option>		
							<forms:codeTable tableName="TB_BUDGET_TMPLT" selectColumn="DISTINCT (DATA_YEAR)"
						 		valueColumn="DATA_YEAR" textColumn="DATA_YEAR" orderColumn="DATA_YEAR" 
						 		orderType="ASC" selectedValue="${budgetReplyed.dataYear}"/>
					</select>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft"  width="20%">预算模板类型</td>
			<td class="tdRight" width="30%" colspan="3">
				<div class="ui-widget">
					<select id="dataAttr" name="dataAttr">
						<option value="">请选择</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
							 conditionStr="CATEGORY_ID = 'CNT_TYPE'" orderType="ASC" selectedValue="${budgetReplyed.dataAttr}"/>
					</select>
				</div>
			</td>
		</tr>
		
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="020601" value="查询"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
		
	</table>
	<br/>
	<table class="tableList" id="resolveTable">
		<tr class="collspan-control">
			<th width="25%">模板</th>
			<th width="25%">监控指标</th>
			<th width="20%">分配金额</th>
			<th width="20%">已分配金额</th>
			<th width="10%">操作</th>
		</tr>
		<c:forEach items="${budgetResolveList}" var="resolve">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
				<td title="${resolve.tmpltId}">
					${resolve.tmpltName}
				</td>
				<td title="${resolve.montCode}">
					${resolve.montName}
				</td>
				<td><fmt:formatNumber type="number" value="${resolve.replyAmt}" /></td>
				<td><fmt:formatNumber type="number" value="${resolve.splitedAmt}" /></td>
				<td>
	<%-- 				<div class="update"><a href="#" onclick="getResolve('${resolve.tmpltId}','${resolve.montCode}');" title="分解"></a></div> --%>
					<input type="button" value="分解" onclick="getResolve('${resolve.tmpltId}','${resolve.montCode}');" title="进入分解页面">
				</td>
			</tr>
		</c:forEach>
		<c:if test="${ empty budgetResolveList}">
			<tr>
				<td colspan="5" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>