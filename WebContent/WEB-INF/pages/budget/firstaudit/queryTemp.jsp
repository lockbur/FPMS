<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算初审</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#dataTypeSelect").combobox();
	$("#dataAttrSelect").combobox();
	$("#valiYearSelect").combobox();
	
	var initEvent = function(){
		$("#resetBtn").click(function(){
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
		});
	};
	
	initEvent();
}


function viewBudget(tmpltId,dataAttr,obj,url){
	$("#budgetListForm").attr("action",url+"&tmpltId=" + tmpltId+"&dataAttr="+dataAttr);
	App.submit($("#budgetListForm")[0]);
}
</script>
</head>
<body>
<p:authFunc funcArray="020301,02030101,02030102,02030103"/>
<form action="" method="post" id="budgetPlanSearchForm" >
<p:token/>
<input type="hidden" name="queryFlag" value="true"/>
	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4" style="text-align: center;">预算模板查询</th>
		</tr>
		<tr>
			<td class="tdLeft">有效年份</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="TB_BUDGET_TMPLT" selectColumn="DISTINCT DATA_YEAR"
					 		valueColumn="DATA_YEAR" textColumn="DATA_YEAR" orderColumn="DATA_YEAR" 
					 		orderType="ASC" selectedValue="${firstAuditBean.dataYear}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">预算监控指标</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataAttrSelect" name="dataAttr">
						<option value="">请选择</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
							 conditionStr="CATEGORY_ID = 'CNT_TYPE'" orderType="ASC" 
							 selectedValue="${firstAuditBean.dataAttr}"/>
					</select>
				</div>
			</td>
		</tr>
         <tr>
			<td class="tdLeft"> 预算类型</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select id="dataTypeSelect" name="dataType">
						<option value="">请选择</option>
						<option value="0" <c:if test="${firstAuditBean.dataType == '0'}">selected="selected"</c:if>>年初预算</option>
						<option value="1" <c:if test="${firstAuditBean.dataType == '1'}">selected="selected"</c:if>>追加预算</option>
					</select>
				</div>
			</td>
		<tr>
		<tr>
			<td colspan="4">
				    <p:button funcId="020301" value="查询"/>
					 <input type="button" value="重置" id="resetBtn">
			</td>
		</tr>
	</table>
</form>

<form action="" method="post" id="budgetListForm">
	<br/>
	<table id="listTab" class="tableList">
			<tr>
			    <th>模板</th>
				<th>创建部门</th>
				<th>操作</th>
			</tr>
	<c:if test="${!empty list}">
			<c:forEach items="${list}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				    <td>${budget.tmpltName}</td>
					<td>${budget.org2Name}</td>
					<td>
						<p:button funcId="02030101" value="进入列表" onclick="viewBudget('${budget.tmpltId}','${budget.dataAttr}')" />
					</td>
				</tr>
			</c:forEach>
	</c:if>
	<c:if test="${empty list}">
	   <tr>
		<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	</c:if>
	</table>
</form>
<p:page/>

</body>
</html>