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
<title>项目修改</title>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#scope").combobox();
	//备注字数统计
	$("#projDesc").keyup();
	
	//设置时间插件
	$( "#startDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    maxDate:'${proj.startDate}'
	});
 	
 	//设置时间插件
	$( "#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    yearRange:'2005:2099'
	});
	
	if('${proj.scope}' == '3')
	{
		$("#hiddenTr").attr("style","display: table-row;");	
	}
}


//出库设备添加--跳出页面
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

//重置
function resetAll()
{
    $("#projType").val('${proj.projType}');
    $("#projName").val('${proj.projName}');
    $("#projTypeName").val('${proj.projTypeName}');
    $("#budgetAmt").val('${proj.budgetAmt}');
    $("#projDesc").text('${proj.projDesc}');
    selectInit( $("#scope"), '${proj.scope}');
    showTr();
    dutyTree.tagReset();
}
//下拉列表初始化
function selectInit(obj, _selVal){
	var _newSel = $(obj).clone();
	$(obj).parent().append(_newSel);
	$(obj).remove();
	$(_newSel).val(_selVal);
	$(_newSel).combobox();
}

function doValidate(){
	
	//提交前调用检查
	if(!App.valid("#ProjForm")){return;} 
	
	var startTime = $("#startDate").val();
	if($('#endDate').val() < startTime){
		App.notyError("终止时间小于项目起始时间");
		return false;
	}
	
	var dutyTreeList = dutyTree.getSelectOrgList();
	var scopeT = $("#scope").val();
	if(scopeT == '3' && dutyTreeList.length < 1){
		App.notyError("责任中心不能为空，请选择责任中心！");
		return false;
	}
	
	var budgetAmt = $.trim($("#budgetAmt").val());
	if(budgetAmt == null || budgetAmt == ""|| $("#budgetAmt").val()<=0 ){
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
	
	if(!ajaxCheckAmt())
	{
		return false;
	}
	return true;
}

function showTr()
{
	var scope = $("#scope").val();
	if(scope == "1" || scope=="2")
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

function ajaxCheckAmt()
{
	
	var projId = $("#projId").val();
	var budgetAmt = $("#budgetAmt").val();
	
	if(budgetAmt<0){
		isPass =  false;
		App.notyError("项目预算不能小于零!");
		return isPass;
	}
	if($.trim(budgetAmt)==""){
		isPass =  false;
		App.notyError("项目预算不能为空!");
		return isPass;
	}
	if(!$.checkMoney(budgetAmt))
	{
		App.notyError("项目预算数据有误！最多含两位小数的18位正浮点数。");
		$("#budgetAmt").focus();
		return false;
	}
	
	
	var isPass = false;
	var data = {};
	data['projId'] =  projId;
	data['budgetAmt'] =  budgetAmt;
	App.ajaxSubmit("projmanagement/projectMgr/ajaxCheckAmt.do?VISIT_FUNC_ID=02100103",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("项目预算不能小于合同金额!");
					isPass =  false;
				}
				else
				{
					isPass =  true;
				}
			});
	return isPass;
}
</script>
</head>
<body>
<p:authFunc funcArray="02100301"/>
<form action="" method="post" id="ProjForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">
				项目编号：${proj.projId}
				<input id="projId" type="hidden" name="projId" value="${proj.projId}" maxlength="100"/>
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%">创建年份</td>
			<td class="tdRight" width="50%">
				${proj.startYear}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">项目类型</td>
			<td class="tdRight">
				<input id="projTypeName" name="projTypeName"  class="base-input-text" valid errorMsg="项目类型不能为空，请选择项目类型！" type="text" style="width:235px;" readonly="readonly" value="${proj.projTypeName}"   onclick="projTypePage()"/>
				<input id="projType" name="projType"    type="hidden" value="${proj.projType}"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">项目名称</td>
			<td class="tdRight">
				<input id="projName" name="projName" value="${proj.projName}" maxlength="200" valid errorMsg="项目名称不能为空，请填写！" class="base-input-text" type="text" />
			</td>
		</tr>
		<tr>
		    <td class="tdLeft">项目预算（人民币）</td>
			<td class="tdRight">
				<input id="budgetAmt" name="budgetAmt" onkeyup="$.clearNoNum(this);" onblur="$.onBlurReplace(this);" value="${proj.budgetAmt}" valid errorMsg="项目预算不能为空。" onchange="ajaxCheckAmt();" class="base-input-text" type="text" />
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%">合同金额</td>
			<td class="tdRight" width="50%">
				${proj.cntTotalAmt}
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
						 orderType="ASC" selectedValue="${proj.scope}"/>				
					</select>
				 </c:if>
				 <c:if test="${isSuper == 0}">
				   <select id="scope" name="scope" onchange="showTr();" valid errorMsg="请选择安全性。">
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG"
						 conditionStr="CATEGORY_ID = 'PROJECT_SCOPE' AND PARAM_VALUE != '1'" 
						 orderType="ASC" selectedValue="${proj.scope}"/>				
					</select>
				 </c:if>	
				 </div>
			</td>
		</tr>
		<tr id="hiddenTr" style="display: none;">
			<td class="tdLeft">责任中心</td>
			<td class="tdRight">
			  <forms:OrgSelPlugin rootNodeId="${org}" jsVarName="dutyTree"  jsVarGetValue="dutyCodeList" radioFlag="false" initValue="${proj.dutyCode}" leafOnlyFlag="true"/>
			</td>
		</tr>
		<tr>
		   <td class="tdLeft" >起始时间</td>
		    <td class="tdRight" >
		       <input id="startDate" name="startDate" value="${proj.startDate}" maxlength='10' valid errorMsg="起始日期不能为空，请选择日期！" readonly="readonly" class="base-input-text" type="text"  style="width:120px;"/>
		    </td>
		</tr>
		<tr>
		   <td class="tdLeft" >终止日期</td>
		    <td class="tdRight" >
		       <input id="endDate" name="endDate" maxlength='10' value="${proj.endDate}" valid errorMsg="结束日期不能为空，请选择日期！" readonly="readonly" class="base-input-text" type="text"  style="width:120px;"/>
		    </td>
		</tr>		
		<tr>
		   <td class="tdLeft" >项目描述<br>(<span id="defrayedLogSpan">0/2000</span>)</td>
		    <td class="tdRight" >
		       <textarea class="base-textArea" name="projDesc" id="projDesc" onkeyup="$_showWarnWhenOverLen1(this,2000,'defrayedLogSpan')">${proj.projDesc}</textarea>
		    </td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="02100301" value="提交"/>
			    <input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
	</table>
	<br>
</form>
</body>
</html>