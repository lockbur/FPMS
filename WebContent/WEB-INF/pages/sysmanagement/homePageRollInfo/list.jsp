<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页滚动信息维护</title>
<style type="text/css">
</style>
<script  type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<script type="text/javascript">

function pageInit(){
	App.jqueryAutocomplete();
	$("#visualGrade").combobox();
	infoHide(17);
	var date = new Date();
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    minDate:'${nowDate}'
	});
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    minDate:'${nowDate}'
	});
}
	
function update(rollId,lastDate){
	var form = $("#update")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/homePageRollInfo/update.do?<%=WebConsts.FUNC_ID_KEY%>=081301&rollId="+rollId+"&lastDate="+lastDate;
	form.submit();
	
}

function add(){
	var form = $("#add")[0];
	form.action = "<%=request.getContextPath()%>/sysmanagement/homePageRollInfo/add.do?<%=WebConsts.FUNC_ID_KEY%>=081302";
	form.submit();
}
function del(rollId){
	var form=document.forms[0];
	$( "<div>确认要删除 此条信息？</div>" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				form.rollId.value=rollId;
				form.action = "<%=request.getContextPath()%>/sysmanagement/homePageRollInfo/del.do?<%=WebConsts.FUNC_ID_KEY%>=081305";
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
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
</head>

<body>
	<form method="post" id="delete" action="">
		<input type="hidden" id="rollId" name="rollId" class="base-input-text"/>
	</form>
	<form method="post" id="add" action=""></form>
	<form method="post" id="update" action=""></form>
	<form method="post" id="rollInfoForm" action="">
	<p:authFunc funcArray="0813,081302" />
		<table>
			<tr class="collspan-control">
				<th colspan="4">首页滚动信息查询</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">名称</td>
				<td class="tdRight" width="25%">
					<input type="text" id="rollTitle" name="rollTitle" maxlength="60" value="${rollInfoBean.rollTitle}" class="base-input-text"/>
				</td>
			<td class="tdLeft" width="25%">创建人</td>
				<td class="tdRight" width="25%">
					<input type="text" id="addUid" name="addUid" value="${rollInfoBean.addUid}" class="base-input-text"/>
				</td>
			</tr>
			<tr>
				<td class="tdLeft">最后日期区间</td>
			<td class="tdRight">
				<input id="befDate"  name="befDate" style="width: 35%;" readonly="readonly" value="${rollInfoBean.befDate}" class="base-input-text"/>至
				<input id="aftDate"  name="aftDate" style="width: 35%;" readonly="readonly" value="${rollInfoBean.aftDate}" class="base-input-text"/>
			</td>
				<td class="tdLeft" width="25%">可见级别</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
					<c:if test="${rollInfoBean.isA0001SuperAdmin==1}">
					<select id="visualGrade" name="visualGrade" class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="1" <c:if test="${rollInfoBean.visualGrade=='1'}">selected="selected"</c:if>>-全国-</option>
						<forms:codeTable tableName="(select distinct org1_code, org1_name from TB_FNDWRR)" selectColumn="org1_code, org1_name" 
						 valueColumn="org1_code" textColumn="org1_name" orderColumn="org1_code" orderType="ASC"  selectedValue="${rollInfoBean.visualGrade}"/>
					</select>
					</c:if>
					<c:if test="${rollInfoBean.isA0001SuperAdmin==0}">
						<select id="visualGrade" name="visualGrade" class="erp_cascade_select">
						 <option value="">-请选择-</option>
						 <option value="2" <c:if test="${rollInfoBean.visualGrade=='2'}">selected="selected"</c:if>>全省</option>
						</select>
					</c:if>
				</div>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="0813" value="查找"/>
				<input type="button" value="新增" onclick="add();">
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
		</table>
		<br />
		<table class="tableList">
			<tr>
				<th width="21%">名称</th>
				<th width="25%">内容</th>
				<th width="8%">创建人</th>
				<th width="8%">创建日期</th>
				<th width="20%">可见级别</th>
				<th width="8%">最后日期</th>
				<th width="10%">操作</th>
			</tr>
			<c:forEach items="${list}" var="list">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');"onmouseout="setTrBgClass(this, 'trOther');">
					<td>${list.rollTitle}</td>
					<td class="tdInfo">${list.rollInfo}</td>
					<td>${list.addUid }</td>
					<td>${list.addTime }</td>
					<td>${list.visualGrade}</td>					
					<td>${list.lastDate }</td>
					<td>
						<div class="update">
							<a href="javascript:void(0)" onclick="update('${list.rollId}','${list.lastDate}');" title="<%=WebUtils.getMessage("button.update")%>"></a>
						</div>
						<a onclick="del('${list.rollId}');" title='<%=WebUtils.getMessage("button.delete")%>'>
							<img class="delete imageBtn" border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/>
						</a>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
				<tr>
					<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
	</form>
	<p:page />
</body>
</html>