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
<title>预算模板管理页面</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#dataTypeSelect").combobox();
	$("#dataAttrSelect").combobox();
	$("#valiYearSelect").combobox();
// 	radioInit($("#isProvinceBuyDiv"),"isProvinceBuy");

	/* var dataType="${searchBean.dataType}";
	if(dataType){
		$("#dataTypeSelect").find("option").each(function(){
			if($(this).val() == dataType){
				$(this).attr("selected",true);
				//$("#dataTypeSelect").parent().find("input[class='ui-autocomplete-input ui-widget']").val($(this).html());
				$("#dataTypeSelect").parent().find("input").val($(this).html());
				//$("#dataTypeSelect").combobox();
				return ;
			}	
		}); 
		
	} */
	//$("#dataTypeSelect").parent().find("input[class='ui-autocomplete-input ui-widget']").val(dataType);
}


//删除
function deleteBudget( tmpltId,dutyCode ){
	var url = "<%=request.getContextPath()%>/budget/budgetInput/delete.do?<%=WebConsts.FUNC_ID_KEY %>=02020301&tmpltId=" + tmpltId+"&dutyCode="+dutyCode;
	var form = $("#budgetListForm")[0];
	form.action = url;
	$( "<div>确认删除?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submit(form,true);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

//详情
function viewBudget(tmpltId,dataType,dutyCode){
	var form = $("#budgetListForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInput/view.do?<%=WebConsts.FUNC_ID_KEY %>=02020302&tmpltId=" + tmpltId+"&dataType="+dataType+"&dutyCode="+dutyCode;
	App.submit(form);
}

//提交预算
function submitBudget(tmpltId,dutyCode){
	var form = $("#budgetListForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInput/input.do?<%=WebConsts.FUNC_ID_KEY %>=02020304&tmpltId=" + tmpltId+"&dutyCode="+dutyCode;
	App.submit(form);
}

//查询列表
function listBudget()
{
	alert('Into listBudget');
 	var form = $("#budgetSearchForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetplan/list.do?<%=WebConsts.FUNC_ID_KEY %>=020103";
	App.submit(form);
}
//


//申报明细
function exportBudgetExcel(tmpltId,tmpltType){
	var data = {};
	data['tmpltId'] =  tmpltId;
	data['tmpltType'] = tmpltType;
	App.ajaxSubmit("budget/budgetInput/download.do?<%=WebConsts.FUNC_ID_KEY %>=02020303",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(flag){
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
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
function resetAll(){
	$(":text").val("");
	$("select").val("");
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
<p:authFunc funcArray="020203,02020301,02020302,02020303,02020304"/>
<form action="" method="post" id="budgetSearchForm" >

	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4">预算模板查询</th>
		</tr>
		<tr >
			<td class="tdLeft"> 预算类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataTypeSelect" name="dataType" >
						<option value="">请选择</option>
						<option value="0" <c:if test="${searchBean.dataType == '0'}">selected="selected"</c:if>>年初预算</option>
						<option value="1" <c:if test="${searchBean.dataType == '1'}">selected="selected"</c:if>>追加预算</option>
					</select>
				</div>
			</td>
			<td class="tdLeft">有效年份</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="TB_BUDGET_TMPLT" selectColumn="DISTINCT DATA_YEAR"
					 		valueColumn="DATA_YEAR" textColumn="DATA_YEAR" orderColumn="DATA_YEAR" 
					 		orderType="ASC" selectedValue="${searchBean.dataYear}" />
					</select>
				</div>
			</td>
		</tr>
         <tr>
			<td class="tdLeft">预算指标</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataAttrSelect" name="dataAttr">
						<option value="">请选择</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
							 conditionStr="CATEGORY_ID = 'CNT_TYPE'" orderType="ASC" selectedValue="${searchBean.dataAttr}" />
					</select>
				</div>
			</td>
		<tr>
		<tr>
			<td colspan="4" class="tdWhite">
					<p:button funcId="020203"  value="查找" />
<%-- 				    <p:button funcId="020105" value="提交" /> --%>
					 <input type="button" value="重置"  onclick="resetAll()">
			</td>
		</tr>
	</table>
</form>

<form action="" method="post" id="budgetListForm">
	<br/>	
	<table id="listTab" class="tableList">		
		<tr>		
<!-- 		    <th width="5%"><input type="checkbox" id="budgetIdAll" name="budgetIdAll" onclick="Tool.toggleCheck(this,'budgetIdList');"/></th> -->
		    <th width="15%" style="text-align: center">模板</th>	
			<th width="15%" style="text-align: center">模板创建部门</th>
			<th width="15%" style="text-align: center">预算申报部门</th>
			<th width="20%" style="text-align: center">状态</th>
			<th width="25%" style="text-align: center">操作</th>
		</tr>
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
<%-- 				    <td><input type="checkbox" name="budgetIdList" value="${budget.tmpltId}"/></td> --%>
				    <td style="text-align: center" title="${budget.tmpltId}">
				    	${budget.dataYear} - ${budget.dataAttrName} - ${budget.dataTypeName}
				    </td>	
					<td style="text-align: center">
						${budget.org21Name}
						<input type="hidden" value="${budget.org21Code}"> 
					</td>	
					<td  style="text-align: center">
						${budget.dutyName}
						
						
					</td>
					<td  style="text-align: center">
						${budget.dataFlagName}
						<input type="hidden" value="${budget.dataFlag}"> 
					</td>	
					<td style="text-align: center">
							
						    <p:button funcId="02020302" type="button" value="明细" onclick="viewBudget('${budget.tmpltId}','${budget.dataType}','${budget.dutyCode}');" />
							<c:if test="${budget.dataFlag == '04' }">
								<input type="button" value="删除" disabled="disabled"/>
							</c:if>
							<c:if test="${budget.dataFlag != '04' }">
								<p:button funcId="02020301" value="删除" onclick="deleteBudget('${budget.tmpltId}','${budget.dutyCode}')" />
							</c:if>
							 <input type="button" value="导出" onclick="exportBudgetExcel('${budget.tmpltId}','${budget.dataAttr}')"/>
							<c:if test="${budget.dataFlag == '03'}">
								<p:button funcId="02020304" value="提交" onclick="submitBudget('${budget.tmpltId}','${budget.dutyCode}')" />
							</c:if>
							<c:if test="${budget.dataFlag != '03'}">
								<input type="button" value="提交" disabled="disabled"/>
							</c:if>
					</td>	
				</tr>
			</c:forEach> 
		</c:if>
		<c:if test="${empty list}">
			<tr>
				<td colspan="5" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>

</body>
</html>