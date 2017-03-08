	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会计分录明细</title>
<script type="text/javascript">
function pageInit() {
	var tableCombine=new TableCombine();
	tableCombine.rowspanTable("tableListRadio", 0, null, 0, 1, null, null);
	
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
function doValidate(){
	if(!$.checkDate("startDate","endDate")){
		return false;
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray="040401"/>
<form action="" method="post" id="queryForm" action="">
   <table>
       <tr class="collspan-control">
			<th colspan="2">
				查询<input type="hidden" name="cntNum" value="${cntNum}"/>
			</th>
		</tr>
		<tr>
		    <td class="tdLeft">日期区间</td>
			<td class="tdRight">
				<input type="text" id="startDate" name="startDate" valid maxlength='10' readonly="readonly" value="${startDate}" class="base-input-text" style="width:145px;"/>
				至
				<input type="text" id="endDate" name="endDate" valid maxlength='10' readonly="readonly" value="${endDate}" class="base-input-text" style="width:145px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<p:button funcId="040401" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
   </table>
</form>
   <br/>
	<table class="tableList" id="tableListRadio">
		<c:forEach items="${accList}" var="acc">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${acc.tradeDate}</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;借：${acc.debitCglCode} &nbsp;&nbsp; ${acc.tradeAmt} </td>
				<td>${acc.errorReason}</td>
			</tr>
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${acc.tradeDate}</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贷：${acc.creditCglCode} &nbsp;&nbsp; ${acc.tradeAmt} </td>
				<td>${acc.errorReason}</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty accList}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>	
	</table>
	<br/>
	<div>
	    <input type="button" value="返回" onclick="backToLastPage('${uri}')">
	 </div>
</body>
</html>