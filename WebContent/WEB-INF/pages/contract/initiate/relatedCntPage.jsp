<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.tool.ConstantMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关联合同号选择</title>
<base target="_self">

<script type="text/javascript">

function pageInit(){
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#createDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}

function getReturnValue(){
	var data = {};
	data.cntNum = $("input:checked").val();
	art.dialog.data('cntObj',data);
	art.dialog.close();	   		
}

$(function(){
	$('table tr').click(function(){
		$("table tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
});

//查询列表
function getRelatedCntPage()
{
 	var form=$("#searchForm")[0];
	form.action="<%=request.getContextPath()%>/contract/initiate/relatedCntPage.do?<%=WebConsts.FUNC_ID_KEY%>=0302010107";
	App.submit(form);
}

//重置查询条件
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
	
};
</script>
</head>
<body>
<form action="" method="post"  id="searchForm">
	<table>
		<th colspan="4">
			关联合同查询 
		</th>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight"><input type="text" class="base-input-text" name="cntNum" id="cntNum" maxlength="80" value="${selectInfo.cntNum }"/></td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="cntType" name="cntType" >
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
						 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
						 orderType="ASC" selectedValue="${selectInfo.cntType }" />
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同事项</td>
			<td class="tdRight"><input type="text" class="base-input-text" name="cntName" id="cntName" maxlength="100" value="${selectInfo.cntName }"/></td>
			<td class="tdLeft">创建日期</td>
			<td class="tdRight"><input type="text" class="base-input-text" name="createDate" id="createDate" readonly maxlength="10" value="${selectInfo.createDate }"/></td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite" align="center" style="text-align: center;" >
				<input type="button" value="查询" onclick="getRelatedCntPage()"/>
				<input type="button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
</form>	
<br>
<table id="targetT" class="tableList">
	<tr>
		<th width="5%">选择</th>
		<th width="20%">合同号</th>
		<th width="15%">合同事项</th>
		<th width="10%">合同类型</th>
		<th width="10%">合同金额(不含税)</th>
		<th width="10%">税额</th>
		<th width="20%">供应商</th>
		<th width="10%">创建日期</th>
	</tr>
	<c:forEach items="${cntList}" var="cnt">
		<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
			<td><input type="radio" name="cntNum" value="${cnt.cntNum }"/></td>
			<td>${cnt.cntNum }</td>
			<td>${cnt.cntName }</td>
			<td>${cnt.cntTypeName}</td>
			<td>${cnt.cntAmt }</td>
			<td>${cnt.cntTaxAmt }</td>
			<td>${cnt.providerName }</td>
			<td>${cnt.createDate }</td>
		</tr>
	</c:forEach>
</table>
<p:page/>
<br/>
<br/>
<br/>
<div>
	<input type="button"  value="确认"  onclick="getReturnValue();" />
	<input type="button"  value="返回"  onclick="art.dialog.close()"  />
</div>
</body>
</html>