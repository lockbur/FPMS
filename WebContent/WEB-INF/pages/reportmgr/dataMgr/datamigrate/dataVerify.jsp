<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据迁移合同数据列表</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	
 	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
}
function resetAll() {
	$("select").val("");
	$(":text").val("");
	//$(":hidden").val("");
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

//合同付款明细信息
function deail(cntNum){
	var form=$("#queryDetailForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payAdd/deail.do?<%=WebConsts.FUNC_ID_KEY%>=03030301";
	$("#queryDetailForm #cntNum1").val(cntNum);
	App.submit(form);
}

function providerType(){
	App.submitShowProgress();
	var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	App.submitFinish();
	if(!proObj){//判断谷歌浏览器返回问题
		proObj = window.returnValue;
		if(proObj){
			$("#providerCode").val(proObj.providerCode);
			$("#providerName").val(proObj.providerName);
		}
	}else{
		$("#providerCode").val(proObj.providerCode);
		$("#providerName").val(proObj.providerName);
	}
}

function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray=""/>
<form action="" method="post" id="queryDetailForm">
	<input type="hidden" id="cntNum1" name="cntNum"  class="base-input-text"/>
</form>
<form action="" method="post" id="queryForm">

	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" name="cntType">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
						 orderType="ASC" selectedValue="${selectInfo.cntType}"/>	
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<p:button funcId="030303" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	
	<br>
	<table class="tableList">
		<tr>
		    <th width="10%">合同事项</th>
			<th width="10%">合同号</th>
			<th width="8%">合同类型</th>
			<th width="8%">是否订单</th>
			<th width="12%">供应商</th>
			<th width="8%">合同金额(元)</th>
			<th width="16%">付款责任中心</th>
			<th width="12%">操作</th>
		</tr>
		<c:if test="${!empty cntList}">
			<c:forEach items="${cntList}" var="cntItem">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc">${cntItem.cntName}</td>
					<td class="tdc">${cntItem.cntNum}</td>
					<td class="tdc">${cntItem.cntType}</td>
					<td class="tdc">
						<c:if test="${cntItem.isOrder == '0'}">
							是
						</c:if>
						<c:if test="${cntItem.isOrder == '1'}">
							否
						</c:if>
					</td>
					<td>${cntItem.providerName}</td>
					<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntAmt}" minFractionDigits="2"/></td>
					<td class="tdc">${cntItem.payDutyName}</td>
					
					<td>
						<input type="button" value="合同" onclick=""> 
						<input type="button" value="付款" onclick=""> 
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty cntList}">
			<tr><td style="text-align: center;" colspan="9"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
		
	</table>
	<p:page/>
</form>

</body>
</html>