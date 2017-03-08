<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电子审批列表</title>
<script type="text/javascript">
function pageInit() {
	App.jqueryAutocomplete();
	$( "#startDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	});
	$( "#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	});
}
function resetAll(){
	$("#conditionTable input[type='text']").val("");
}

function checkSelect(){
	var obj = $(".tableList tr").find(":radio[name='projectR'][checked='checked']");
	if($(obj).length <= 0){
		alert("至少选中一项！");
		return;
	}
	var info = $(obj).val();
	//window.returnValue = getProjectcrObj(info);
	var projectcr = getProjectcrObj(info);
	art.dialog.data('projectcr',projectcr);
	art.dialog.close();	   	
}

function selectOne(obj){
	$(obj).find(":radio").attr("checked", true);
}

function commitOne(obj){
	$(obj).find(":radio").attr("checked", true);
	var info = $(obj).find(":radio").val();
	//window.returnValue = getProjectcrObj(info);
	var projectcr = getProjectcrObj(info);
	art.dialog.data('projectcr',projectcr);
	art.dialog.close();	   	
}

function getProjectcrObj(info){
	var arr = info.split(",");
	var projectcr = new Object();
	projectcr.abCde = arr[0];
	projectcr.projCrId = arr[1];
	projectcr.createDate = arr[2];
	projectcr.projCrNum = arr[3];
	projectcr.exeNum = arr[4];
	projectcr.projCrAmt = arr[5];
	projectcr.exeAmt = arr[6];
	return projectcr;
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
<p:authFunc funcArray="011003"/>
<form method="post" id="projectcrForm" action="<%=request.getContextPath()%>/sysmanagement/projectcrinfo/list.do?<%=WebConsts.FUNC_ID_KEY%>=011003">
	<table id="conditionTable">
		<tr class="collspan-control">
			<th colspan="4">审批列表查询</th>
		</tr>	
		<tr>
			<td class="tdLeft" width="15%">缩位码</td>
			<td class="tdRight" width="35%">
				<input type="text" name="abCde" value="${ProjectcrInfo.abCde}" class="base-input-text"/>
			</td>
			<td class="tdLeft" width="15%">审批编号</td>
			<td class="tdRight" width="35%">
				<input type="text" name="projCrId" value="${ProjectcrInfo.projCrId}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">日期</td>
			<td class="tdRight" colspan="3">
				<input type="text" name="startDate" id="startDate" value="${ProjectcrInfo.startDate}" class="base-input-text"/>
				&nbsp;&nbsp;至&nbsp;&nbsp;<input type="text" name="endDate" id="endDate" value="${ProjectcrInfo.endDate}" class="base-input-text"/>
			</td>
		</tr>

		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="011003" type="search"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<caption>电子审批列表</caption>
		<tr>
			<th width="5%"></th>
			<th width="20%">缩位码</th>
			<th width="20%">审批编号</th>
			<th width="15%">日期</th>
			<th width="20%">审批数量</th>
			<!-- <th width="12%">已执行数量</th> -->
			<th width="20%">审批金额</th>
			<!-- <th width="12%">已执行金额</th> -->
		</tr>
		<c:forEach items="${ProjectcrInfoList}" var="projectcr">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');"
				onclick="selectOne(this);" ondblclick="commitOne(this);">
				<td>
					<input type="radio" name="projectR" 
					value="${projectcr.abCde},${projectcr.projCrId },${projectcr.createDate },<fmt:formatNumber pattern='###0' value='${projectcr.projCrNum}'/>,<fmt:formatNumber pattern='###0' value='${projectcr.exeNum}'/>,<fmt:formatNumber pattern='###0.00' value='${projectcr.projCrAmt}'/>,<fmt:formatNumber pattern='###0.00' value='${projectcr.exeAmt}'/>" />
				</td>
				<td>${projectcr.abCde }</td>
				<td>${projectcr.projCrId }</td>
				<td>${projectcr.createDate }</td>
				<td><fmt:formatNumber pattern="#,##0" value="${projectcr.projCrNum}"/></td>
				<%-- <td><fmt:formatNumber pattern="#,##0" value="${projectcr.exeNum}"/></td> --%>
				<td><fmt:formatNumber pattern="#,##0.00" value="${projectcr.projCrAmt}"/></td>
				<%-- <td><fmt:formatNumber pattern="#,##0.00" value="${projectcr.exeAmt}"/></td> --%>
			</tr>
		</c:forEach>
	</table>
</form>
<p:page/>
<table align="center">
	<tr>
		<td align="center" style="border: none;">
			<input type="button" value="提交" onclick="checkSelect();">&nbsp;&nbsp;
			<input type="button" value="取消" onclick="art.dialog.close()">
		</td>
	</tr>	
</table>
</body>
</html>