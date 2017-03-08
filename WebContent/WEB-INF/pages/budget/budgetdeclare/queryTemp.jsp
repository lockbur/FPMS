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

//预算模板导出操作
function exportTemplate(tmpltId){
	var data = {};
	data['tmpltId'] =  tmpltId;
	App.ajaxSubmit("budget/budgetplan/exportBudgetPlanTempInfo.do?<%=WebConsts.FUNC_ID_KEY %>=02010303",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(flag){
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						title: "跳转至下载页面",
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
							        	 	var form = document.forms[0];
							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
											App.submit(form);
										}   
						         },
								{
									text:"取消",
									click:function(){
										$(this).dialog("close");
									}
								}
						]
					});
					isPass =  true;
				}
				else
				{
					App.notyError("添加下载失败，请重试!");
					isPass =  false;
				}
			});
	return isPass;
}

function viewBudget(tmpltId,dataAttr,obj,url){
	$("#budgetPlanListForm").attr("action",url+"&tmpltId=" + tmpltId+"&dataAttr="+dataAttr);
	App.submit($("#budgetPlanListForm")[0]);
}
</script>
</head>
<body>
<p:authFunc funcArray="020202,02020201,02020202,02020203"/>
<form action="" method="post" id="budgetPlanSearchForm" >
<p:token/>
<input type="hidden" name="queryFlag" value="true"/>
	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4" style="text-align: center;">可申报预算模板查询</th>
		</tr>
		<tr>
			<td class="tdLeft">有效年份</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="TB_BUDGET_TMPLT" selectColumn="DISTINCT DATA_YEAR"
					 		valueColumn="DATA_YEAR" textColumn="DATA_YEAR" orderColumn="DATA_YEAR" 
					 		orderType="ASC" selectedValue="${budgetDeclareBean.dataYear}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">预算监控指标</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataAttrSelect" name="dataAttr">
						<option value="">--请选择--</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
							 conditionStr="CATEGORY_ID = 'CNT_TYPE'" orderType="ASC" 
							 selectedValue="${budgetDeclareBean.dataAttr}"/>
					</select>
				</div>
			</td>
		</tr>
         <tr>
			<td class="tdLeft"> 预算类型</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select id="dataTypeSelect" name="dataType">
						<option value="">--请选择--</option>
						<option value="0" <c:if test="${budgetDeclareBean.dataType == '0'}">selected="selected"</c:if>>年初预算</option>
						<option value="1" <c:if test="${budgetDeclareBean.dataType == '1'}">selected="selected"</c:if>>追加预算</option>
					</select>
				</div>
			</td>
		<tr>
		<tr>
			<td colspan="4" class="tdWhite">
				    <p:button funcId="020202" value="查找"/>
					 <input type="button" value="重置" id="resetBtn">
			</td>
		</tr>
	</table>
</form>

<form action="" method="post" id="budgetPlanListForm">
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
				    <td title="${budget.tmpltId}">
				    	${budget.tmpltName}
				    </td>
					<td>${budget.org2Name}</td>
					<td>
						<p:button funcId="02020201" value="导出预算计划" onclick="exportTemplate('${budget.tmpltId}')" />
						<p:button funcId="02020202" value="进行申报" onclick="viewBudget('${budget.tmpltId}','${budget.dataAttr}')" />
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