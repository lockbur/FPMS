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
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	
	var tempDate =  new Date();
	$("#month").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymm",
	    defaultDate:tempDate
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



</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全国一级行月结情况</title>
</head>
<body>
<p:authFunc funcArray="030602"/>
<form action="" method="post" id="moForm">
<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">一级行号</td>
			<td class="tdRight">
				<div class="ui-widget">
				<select class="erp_cascade_select" id="org1Code" name="org1Code">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="TB_FNDWRR" selectColumn="DISTINCT ORG1_CODE,ORG1_NAME"
					 valueColumn="ORG1_CODE" textColumn="ORG1_NAME" orderColumn="ORG1_CODE" 
					 orderType="ASC" selectedValue="${bean.org1Code}"/>	
				</select>
			   </div>
			</td>
			<td class="tdLeft">月份</td>
			<td class="tdRight">
			   <div class="ui-widget">
					<input type="text" name="month" id="month" value="${bean.month}" valid class="base-input-text" readonly/>
				</div>
			</td>
			
		</tr>
		<tr>
			<td class="tdLeft">任务类型</td>
			<td class="tdRight">
				<div class="ui-widget">
				<select class="erp_cascade_select" id="taskType" name="taskType">
					<option value="" >--请选择--</option>
					<option value="1" <c:if test="${bean.taskType == '1'}">selected="selected"</c:if>>待摊预提</option>
					<option value="0" <c:if test="${bean.taskType == '0'}">selected="selected"</c:if>>预提冲销</option>				
				</select>
			   </div>
			</td>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
			   <div class="ui-widget">
					<select class="erp_cascade_select"  id="dataFlag" name="dataFlag" >
						<option value="">--请选择--</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'FMSCGL_DATA_FLAG'"
						 orderType="ASC" selectedValue="${bean.dataFlag}"/>	
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="030602" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
</form>
	<table class="tableList">
		<tr>
			<th width="10%">一级行号</th>
			<th width="20%">一级行名称</th>
			<th width="20%">月份</th>
			<th width="20%">任务类型</th>
			<th width="10%">状态</th>
			<th width="20%">操作日期</th>												
		</tr>
		<c:forEach items="${mbList}" var="mb" >
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td>${mb.org1Code}</td>
			<td>${mb.org1Name}</td>
			<td>${mb.month}</td>
			<td>${mb.taskName}</td>
			<td>${mb.dataFlag}</td>
			<td>${mb.instDate}</td>
			</tr>
		</c:forEach>
		<c:if test="${empty mbList}">
	   		<tr>
			<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
	    </c:if>
</table>


</body>
</html>