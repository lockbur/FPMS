<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增预算申报</title>
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
	$("#budgetPlanListForm").attr("action",url+"&tmpltId=" + tmpltId+"&dataAttr="+dataAttr);
	App.submit($("#budgetPlanListForm")[0]);
}

function checkInfo(button, url) {
	var objs = $("#listTab").find(":text[name='auditAmts']").filter(
	function(){
		if(this.value==""){
			return false;
		}else{
			return true;
		}
	}		
	);
	if ($(objs).size() <= 0) {
		App.notyError("至少填写一项审批金额！");
		return;
	}
	
	if($(objs).size()>0) {
		$( "<div>确认提交预算审批?</div>" ).dialog({
			resizable: false,
			height:140,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					var form = $('#budgetPlanListForm')[0];
					form.action = url;
					App.submit(form);
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}

function textCheck(id) {
	var value = $("#"+id).val();
	var tdId = id + "Td";
	if(value == '')
		return false;
	var reg = /^(?:([1-9]\d*|0)(\.?)(\d{0,2})|0)$/;
	var flag = !reg.test(value) || 0 > value;
	App.toShowCheckIdIsExist("budgetPlanListForm",id,tdId,"请输入至多两位小数的非负数",flag);
	if(flag)
		return false;
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="020301,02030101,02030102,02030103"/>
<form action="" method="post" id="budgetPlanListForm">
	<br/>
	<table id="listTab" class="tableList">
			<tr align="center">
			<th nowrap="nowrap">责任中心</th>
			<c:if test="${firstAuditBean.dataAttr == 0}">
			<th nowrap="nowrap">申报数量</th>
			</c:if>
			<th nowrap="nowrap">申报金额</th>
			</tr>
	<c:if test="${!empty list}">
			<c:forEach items="${list}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${budget.dutyName}</td>
				<c:if test="${firstAuditBean.dataAttr == 0}">
				<td>${budget.inNeedCnt}</td>
				</c:if>
				<td>${budget.inAmt}</td>
				</tr>
			</c:forEach>
	</c:if>
	<c:if test="${empty list}">
	   <tr>
		<td colspan="4" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	</c:if>
	</table>

<p:page/>
<table>
	<tr><td style="border: 0"><input type="button" value="返回" onclick="backToLastPage('${uri}')"></td></tr>
</table>
</form>
</body>
</html>