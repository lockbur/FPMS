<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目新增</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#scope").combobox();
	
 	//设置时间插件
	$( "#startDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
 	//设置时间插件
	$( "#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    yearRange:'2005:2099'
	});
	//给结束日期默认值
	$("#endDate").val("2099-12-31");
}


function projTypePage()
{
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projTypePage.do?<%=WebConsts.FUNC_ID_KEY %>=02100102";
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "75%",
				lock: true,
			    fixed: true,
			    title:"项目类型选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('projTypeObject'); 
					if(proObj){
						$("#projType").val(proObj.projType);
						$("#projTypeName").val(proObj.projTypeName);
					}
				}
			}
		 );
}


function doValidate(){
	//提交前调用检查
	if(!App.valid("#ProjForm")){return false;} 
	if(!$.checkDate("startDate","endDate")){
		return false;
	}
	var dutyTreeList = dutyTree.getSelectOrgList();
	var scopeT = $("#scope").val();
	
	if ($("#projType").val()=="") {
		App.notyError("项目类型不能为空，请选择项目类型或填写！");
		return false;
	}
	
	if ($("#projName").val()=="") {
		App.notyError("项目名称不能为空，请填写！");
		return false;
	}	
	
	if(scopeT == '3' && dutyTreeList.length < 1){
 		App.notyError("责任中心不能为空，请选择责任中心！");
		return false;
	}
	
	var budgetAmt = $.trim($("#budgetAmt").val());
	if(budgetAmt == null || budgetAmt == "" || $("#budgetAmt").val()<=0 ){
		App.notyError("项目预算必须大于0！");
		$("#budgetAmt").focus();
		return false;
	}
	if(!$.checkMoney(budgetAmt))
	{
		App.notyError("数据有误！最多含两位小数的18位正浮点数。");
		$("#budgetAmt").focus();
		return false;
	}
	if ($("#startDate").val()=="") {
		App.notyError("起始日期不能为空，请选择日期！");
		return false;
	}
	if(!confirm("是否确认新增此项目？")){
		return false;
	}
	return true;
}

function showTr()
{
	var scope = $("#scope").val();
	if(scope == "1" || scope == "2")
	{
		$("#hiddenTr").attr("style","display: none;");	
		//机构树的校验
		$("#hiddenTr #orgSelectedPluginNodeNm").removeAttr("valid");	
		$("#hiddenTr #orgSelectedPluginNodeNm").removeAttr("errorMsg");	
	}
	if(scope == "3")
	{
		$("#hiddenTr").attr("style","display: table-row;");	
		
		$("#hiddenTr #orgSelectedPluginNodeNm").attr("valid","");	
		$("#hiddenTr #orgSelectedPluginNodeNm").attr("errorMsg","请选择责任中心。");	
	}
}

//重置表单
function resetAll(){
	self.location.reload();
}

</script>
</head>
<body>
<p:authFunc funcArray="02100101"/>
<form action="<%=request.getContextPath()%>/sysmanagement/projectMgr/add.do" method="post" id="ProjForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">
				项目新增
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%"><span class="red">*</span>项目类型</td>
			<td class="tdRight" width="50%">
				<input id="projTypeName" name="projTypeName"  class="base-input-text" valid errorMsg="项目类型不能为空，请选择项目类型！" type="text" style="width:235px;" readonly="readonly"   onclick="projTypePage()"/>
				<input id="projType" name="projType"    type="hidden" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>项目名称</td>
			<td class="tdRight">
				<input id="projName" name="projName" maxlength="200" class="base-input-text" valid errorMsg="项目名称不能为空，请填写！" type="text" style="width:235px;" />
			</td>
		</tr>
		<tr>
		    <td class="tdLeft"><span class="red">*</span>项目预算（人民币）</td>
			<td class="tdRight">
				<input id="budgetAmt" name="budgetAmt"  onkeyup="$.clearNoNum(this);" onblur="$.onBlurReplace(this);" value="0" class="base-input-text" valid errorMsg="项目预算不能为空。" type="text" maxlength="16" style="width:120px;"/>
			</td>
		   
		</tr>
		<tr>
			<td class="tdLeft">安全性</td>
			<td class="tdRight">
				 <div class="ui-widget" id="scopeDiv">
				 <c:if test="${isSuper == 1}">
				   <select id="scope" name="scope" onchange="showTr();" valid errorMsg="请选择安全性。">
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG"
						 conditionStr="CATEGORY_ID = 'PROJECT_SCOPE' AND PARAM_VALUE != '2'" 
						 orderType="ASC"/>				
					</select>
				 </c:if>
				 <c:if test="${isSuper == 0}">
				   <select id="scope" name="scope" onchange="showTr();" valid errorMsg="请选择安全性。">
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG"
						 conditionStr="CATEGORY_ID = 'PROJECT_SCOPE' AND PARAM_VALUE != '1'" 
						 orderType="ASC"/>				
					</select>
				 </c:if>	
				 </div>
			</td>
		</tr>
		<tr id="hiddenTr" style="display: none;">
			<td class="tdLeft" width="50%">责任中心</td>
			<td class="tdRight" width="50%">		
			  
			  <forms:OrgSelPlugin rootNodeId="${org}" leafOnlyFlag="true"  rootLevel="1" jsVarGetValue="dutyCodeList" radioFlag="false" jsVarName="dutyTree"/>  
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>起始日期</td>
			<td class="tdRight">
				<input id="startDate" name="startDate" maxlength='10' valid errorMsg="起始日期不能为空，请选择日期！" readonly="readonly" class="base-input-text" type="text"  style="width:120px;"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>结束日期</td>
			<td class="tdRight">
				<input id="endDate" name="endDate" maxlength='10' valid errorMsg="结束日期不能为空，请选择日期！" readonly="readonly" class="base-input-text" type="text"  style="width:120px;"/>
			</td>
		</tr>
		<tr>
		   <td class="tdLeft" width="25%">项目描述<br>(<span id="defrayedLogSpan">0/2000</span>)</td>
		    <td class="tdRight" width="75%">
		        <textarea class="base-textArea"  name="projDesc" id="projDesc" onkeyup="$_showWarnWhenOverLen1(this,2000,'defrayedLogSpan')"></textarea>
		    </td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="02100101" value="提交"/>
				<input type="button" value="重置" onclick="resetAll()"> 
			</td>
		</tr>
	</table>
	<br>
</form>
</body>
</html>