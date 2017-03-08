<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预提待摊</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#dataFlag").combobox();
	$("#taskType").combobox();
	
	//设置时间插件
	$( "#startDate,#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
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

//4
function dealCglBatch(org1Code,feeYyyymm,taskType){
	var form = $("#listForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/dealCglBatch.do?<%=WebConsts.FUNC_ID_KEY%>=04030401';
	form.action = url + "&org1Code="+org1Code+"&feeYyyymm="+feeYyyymm+"&taskType="+taskType;
	form.submit();
}

//3
function ajaxCheck31Upload(org1Code,feeYyyymm,taskType){
	var data = {};
	App.ajaxSubmit("amortization/fmsMgr/ajaxCheck31.do?VISIT_FUNC_ID=04030402",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					$("<div>应付发票存在异常状态!<br/>是否继续做预提待摊操作？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
											dealCglBatch(org1Code,feeYyyymm,taskType);
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
					return;
				}
				else
				{
					dealCglBatch(org1Code,feeYyyymm,taskType);
				}
			});
}

//1
function ajaxCheckMonthStatus(org1Code,feeYyyymm,taskType){
	var data = {};
	App.ajaxSubmit("monthOver/ajaxCheckMonthStatus.do?VISIT_FUNC_ID=03060103",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					$("<div>不在月结状态！不允许进行预提待摊任务。</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
											$(this).dialog("close");
										}   
						         }
						]
					});
					return;
				}
				else
				{
					//ajaxCheckDeadline(org1Code,feeYyyymm,taskType);
					ajaxCheck31Upload(org1Code,feeYyyymm,taskType);
				}
			});
}

//2
function ajaxCheckDeadline(org1Code,feeYyyymm,taskType){
	var data = {};
	App.ajaxSubmit("sysmanagement/parameter/ajaxCheckDeadline.do?VISIT_FUNC_ID=010503&yyyymm="+feeYyyymm,
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					$("<div>操作失败，预提待摊任务超出当月截止时间！请在次月初触发补做。</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
											$(this).dialog("close");
										}   
						         }
						]
					});
					return;
				}
				else
				{
					ajaxCheck31Upload(org1Code,feeYyyymm,taskType);
				}
			});
}

function doValidate(){
	if(!$.checkDate("startDate","endDate")){
		return false;
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray="040304,04030401,010503"/>
<form method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">预提待摊</th>
		</tr>
		<tr class="collspan-control">
			<th colspan="4" style="fontWeight:bold;color:red;">
				总账接口数据上送截止日期及时间【每月末最后${deadlineDay}个自然日${deadlineTime}时】！
			</th>
		</tr>
		<tr>
			<td class="tdLeft">任务类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="taskType" name="taskType">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'FMSCGL_TASK_TYPE'"
						 orderType="ASC"  selectedValue="${fmsCglBatch.taskType}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataFlag" name="dataFlag">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'FMSCGL_DATA_FLAG'"
						 orderType="ASC"  selectedValue="${fmsCglBatch.dataFlag}"/>
					</select>
				</div>
			</td>
		<tr>
			<td class="tdLeft">交易日期</td>
			<td class="tdRight" colspan="3">
				<input type="text" id="startDate" name="startDate" class="base-input-text" readonly value="${startDateString}"/>&nbsp;至&nbsp;
				<input type="text" id="endDate" name="endDate" class="base-input-text" readonly value="${endDateString}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040304" value="查询"/>
				<input type="button" class="base-input-button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
</form>
</br>
<form method="post" id="listForm" action="">
<table class="tableList">
	<tr>
		<th width="%">月份</th>
		<th width="%">任务类型</th>
		<th width="%">总笔数</th>
		<th width="%">成功笔数</th>
		<th width="%">状态</th>
		<th width="%">交易日期</th>
		<th width="%">交易时间</th>
		<th width="%">操作</th>
	</tr>
	<c:forEach items="${fmsCglBatchList }" var="fmsMgr">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');">
			<td>${fmsMgr.feeYyyymm}</td>             
			<td><forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
					valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
					conditionStr="CATEGORY_ID = 'FMSCGL_TASK_TYPE'" selectedValue="${fmsMgr.taskType}"/></td>             
			<td>${fmsMgr.allCnt}</td>             
			<td>${fmsMgr.sucCnt}</td>             
			<td><forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
					valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
					conditionStr="CATEGORY_ID = 'FMSCGL_DATA_FLAG'" selectedValue="${fmsMgr.dataFlag}"/></td>             
			<td>${fmsMgr.tradeDate   }</td>
			<td>${fmsMgr.tradeTime   }</td>
			<td>
				<c:choose>
					<c:when test="${fmsMgr.dataFlag=='00' || fmsMgr.dataFlag=='03'|| fmsMgr.dataFlag=='04'|| fmsMgr.dataFlag=='05'}">
						<input type="button" value="处理" class="base-input-button" onclick="ajaxCheckMonthStatus('${fmsMgr.org1Code}','${fmsMgr.feeYyyymm}','${fmsMgr.taskType}')"/>
					</c:when>
					<c:otherwise>
						<input type="button" value="处理" disabled="disabled" class="base-input-button"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${empty fmsCglBatchList}">
			<tr>
				<td colspan="8" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
</table>
</form>
<p:page/>
</body>
</html>