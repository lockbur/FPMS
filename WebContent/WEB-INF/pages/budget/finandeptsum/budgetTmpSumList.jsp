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
<title>本级汇总模板筛选</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/Source/jBox.min.js"></script>		
<script type="text/javascript">
//页面初始化加载
function pageInit(){
	App.jqueryAutocomplete();
 	$("#dataTypeSelect").combobox();
	$("#dataAttrSelect").combobox();
	$("#valiYearSelect").combobox();
}

//重置清空所有选中信息
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

//根据模板ID查看预算模板本行汇总信息
function budgetTmpSelLvlSum(tmpltId,dataYear,tmpltType){
	$("#tmpltId").val(tmpltId);
	$("#dataYear").val(dataYear);
	$("#tmpltType").val(tmpltType);
	$("#budgetPlanSearchForm").attr("action","<%=request.getContextPath()%>/budget/finandeptsum/selList.do?<%=WebConsts.FUNC_ID_KEY %>=02040101");
	$("#budgetPlanSearchForm").submit();
}


//申报明细
function exportBudgetExcel(tmpltId,matrAuditDept,matrCode){
	var data = {};
	data['tmpltId'] =  tmpltId;
	data['type'] = '0'
	App.ajaxSubmit("budget/finandeptsum/tmpltDetailDown.do?<%=WebConsts.FUNC_ID_KEY %>=02040303",
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

</script>
</head>
<body>
<p:authFunc funcArray="020401,02040101"/>
<form action="" method="post" id="budgetPlanSearchForm" >
<input type="hidden" id="tmpltId" name="tmpltId"/>
	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4" >预算模板查找</th>
		</tr>
		<tr>
			<td class="tdLeft"> 预算类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataTypeSelect" name="dataType"  >
						<option value="">--请选择--</option>
							<option value="0" <c:if test="${budgetBean.dataType == '0'}">selected="selected"</c:if>>年初预算</option>
						<option value="1" <c:if test="${budgetBean.dataType == '1'}">selected="selected"</c:if>>追加预算</option>
					</select>
				</div>
			</td>
			<td class="tdLeft">有效年份</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="TB_BUDGET_TMPLT" selectColumn="DISTINCT DATA_YEAR"
					 		valueColumn="DATA_YEAR" textColumn="DATA_YEAR" selectedValue="${budgetBean.dataYear}" orderColumn="DATA_YEAR" 
					 		orderType="ASC"  />
					</select>
				</div>
			</td>
		</tr>
         <tr>
			<td class="tdLeft">预算指标</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataAttrSelect" name="dataAttr">
						<option value="">--请选择--</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
							 conditionStr="CATEGORY_ID = 'CNT_TYPE'" selectedValue="${budgetBean.dataAttr}" orderType="ASC" />
					</select>
				</div>
			</td>
		<tr>
		<tr>
			<td colspan="4" class="tdWhite">
					<p:button funcId="020401"  value="查找" />
					<input type="button" value="重置"  onclick="resetAll()">
			</td>
		</tr>
	</table>
</form>

<form action="" method="post" id="budgetForm">
	<br/>	
	<div id="listDiv" style="width: 100%; overflow-X: auto; position: relative; float: right">

	<table id="listTab" class="tableList">		
		<tr class="collspan-control">		
		    <th width="18%" style="text-align: center">模板ID</th>	
			<th width="30%" style="text-align: center">预算模板名称</th>
			<th width="32%" style="text-align: center">创建部门</th>
			<th width="20%" style="text-align: center">操作</th>
		</tr>
		<c:if test="${!empty budgetTmpList}">
			<c:forEach items="${budgetTmpList}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				    <td style="text-align: center">
				    	${budget.tmpltId}
				    </td>
				    
				    <td style="text-align: center">
				    	<c:if test="${budgetBean.sumLvl=='1'}">
				    		${budget.tmpltName}
				    	</c:if>
				    	<c:if test="${budgetBean.sumLvl != '1'}">
				    		${budget.dataYear} - ${budget.dataAttrName} - 
				    		<c:if test="${budget.dataType=='0'}" >年初预算</c:if>
				    		<c:if test="${budget.dataType=='1'}" >追加预算</c:if>
				    	</c:if>
				    </td>	
					<td style="text-align: center">
						${budget.org21Code}
						<c:if test="${!empty budget.org21Code}"> - </c:if>
						${budget.org2Name}
						<input type="hidden" value="${budget.org21Code}"> 
					</td>	
					<td style="text-align: center;vertical-align: middle">
						<input type="button" value="本级汇总" onclick="budgetTmpSelLvlSum('${budget.tmpltId}','${budget.dataYear }','${budget.dataType }');"/>
					    <input type="button" value="导出" onclick="exportBudgetExcel('${budget.tmpltId}')"/>
					</td>	
				</tr>
			</c:forEach> 
		</c:if>
		<c:if test="${empty budgetTmpList}">
			<tr>
				<td colspan="4" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
</div>
</form>
<p:page/>

</body>
</html>