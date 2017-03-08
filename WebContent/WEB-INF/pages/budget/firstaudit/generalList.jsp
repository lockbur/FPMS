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


function viewBudget(tmpltId,matrCode,dataAttr){
	
	var url="<%=request.getContextPath()%>/budget/firstaudit/detailList.do?VISIT_FUNC_ID=02030102";
	$("#budgetPlanListForm").attr("action",url+"&tmpltId=" + tmpltId+"&matrCode="+matrCode+"&dataAttr="+dataAttr);
	App.submit($("#budgetPlanListForm")[0]);
}

function checkInfo(button, url) {
	var cks = $(":checkbox:checked[name='matrCodes']");
	if (cks.size() <= 0) {
		App.notyError("至少选择一项物料进行操作！");
		return;
	}
	var eflag = false;
	cks.each(function(){
		var input = $(this).parent().prev().find(":text[name='auditAmt']");
		if(input.val()==""){
			eflag = true;
			App.notyError("请填写审批金额！");
			input.focus();
			return;
		}
	});
	
	if(eflag){
		return;
	}
	
	if(cks.size()>0) {
		$( "<div>确认提交预算审批?</div>" ).dialog({
			resizable: false,
			height:140,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					//组装已选数据
					$(":checkbox[name='matrCodes']").each(function(){
						var val = $(this).parent().prev().find(":text[name='auditAmt']").val();
						var input = $("<input>").attr("name","auditAmts").attr("type","hidden").val(val);
						input.insertBefore($(this));
					});
					
					
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

//详情
function detail(tmpltId,dutyCode,matrCode){
	var form=$("#budgetPlanListForm")[0];
	form.action="<%=request.getContextPath()%>/budget/firstaudit/view.do?<%=WebConsts.FUNC_ID_KEY%>=02030105&tmpltId="+tmpltId+"&dutyCode="+dutyCode+"&matrCode="+matrCode;
	App.submit(form);
}

function submitAmt(index,tmpltId,dutyCode,matrCode,auditFlag){
	if(!checkMoney($("#auditAmt"+index))){
		alert("输入金额不正确或为空，请重新输入！");
		return false;
	}
	var data = {};
	data['auditAmt']=$("#auditAmt"+index).val();
	data['tmpltId']=tmpltId;
	data['dutyCode']=dutyCode;
	data['matrCode']=matrCode;
	data['auditFlag']=auditFlag;
	App.ajaxSubmit("budget/firstaudit/submitAmt.do?<%=WebConsts.FUNC_ID_KEY%>=02030106",{data : data,async:false}, function(data) {
	
		var res=data.result;
		if (res) {
// 			$("#auditAmt"+index).attr("readonly",true);
// 			$("#auditAmt"+index).attr("disabled",true);
			$("#auditAmt"+index).after("<span>"+$("#auditAmt"+index).val()+"</span>");
			$("#auditAmt"+index).remove();
			$("#submitAuditAmt"+index).remove;
			window.location.reload(true);
	} else {
        App.notyError("提交失败！");
	}
	});
}
function checkMoney(obj){
	if(!$.checkMoney($(obj).val()))
	{
		return false;
	} 
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="020301,02030101,02030102,02030103,02030106"/>
<form action="" method="post" id="budgetPlanListForm">
	<%-- <input type="hidden" name="tmpltId" value="${firstAuditBean.tmpltId}"/> --%>
	<br/>
	<table id="listTab" class="tableList">
			<tr >
			    <th align="center">物料编码</th>
				<th align="center">物料名称</th>
				<th align="center">存量预算</th>
				<th align="center">申报总金额</th>
				<th align="center">审批金额</th>
				<th align="center">审批状态</th>
				<th>详情</th>
			</tr>
	<c:if test="${!empty list}">
			<c:forEach items="${list}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				    <td>${budget.matrCode}</td>
				    <td>${budget.matrName}</td>
				    <td>${budget.stockAmt}</td>
					<td>
						<c:if test="${budget.applyAmt!=0}">
						<a href="javascript:viewBudget('${budget.tmpltId}','${budget.matrCode}','${budget.dataAttr}')">${budget.applyAmt}</a>
						</c:if>
						<c:if test="${budget.applyAmt==0}">
						${budget.applyAmt}
						</c:if>
					</td>
					<td>
                        <c:if test="${budget.auditFlag=='00' or budget.auditFlag=='03'}">
                        <input id="auditAmt${status.index}"  name="auditAmt" style="width:80px;" value="${budget.auditAmt}" class="base-input-text">
                        <input type="button" id="submitAuditAmt${status.index}" onclick="submitAmt('${status.index}','${budget.tmpltId}','${firstAuditBean.dutyCode}','${budget.matrCode}','${budget.auditFlag}');" style="left: 10px" value="提交"/>
                        </c:if>
                        <c:if test="${budget.auditFlag !='00' and budget.auditFlag !='03'}">
                        ${budget.auditAmt}
                        </c:if>                   			    
					</td>
					<td>
					<c:if test="${budget.auditFlag=='00'}"> 
					待初审
					</c:if>
					<c:if test="${budget.auditFlag=='01'}"> 
					初审通过
					</c:if>
					<c:if test="${budget.auditFlag=='02'}"> 
					本级汇总审批通过
					</c:if>
					<c:if test="${budget.auditFlag=='03'}"> 
					本级汇总审批退回
					</c:if>	
					<c:if test="${budget.auditFlag=='04'}"> 
					二级汇总审批通过
					</c:if>																
					</td>
					<td>
					<div class="detail">
					    <a href="#" onclick="detail('${budget.tmpltId}','${firstAuditBean.dutyCode}','${budget.matrCode}');" title="<%=WebUtils.getMessage("button.view")%>"></a>
					</div>
<%-- 					<c:if test="${budget.auditFlag =='00' or budget.auditFlag =='03'}"> --%>
<%-- 					<input type="checkbox" name="matrCodes" value="${budget.matrCode}"/>  --%>
<%-- 					</c:if> --%>
					</td>
				</tr>				
			</c:forEach>
	</c:if>
	<c:if test="${empty list}">
	   <tr>
		<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	</c:if>
	</table>
</form>
<p:page/>
<table>
	<tr><td style="border: 0"><input type="button" value="返回" onclick="backToLastPage('${uri}')"></td></tr>
</table>

</body>
</html>