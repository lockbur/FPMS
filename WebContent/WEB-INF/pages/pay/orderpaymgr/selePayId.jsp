<%@page import="com.forms.platform.web.WebUtils"%>
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
<title>订单类付款选择确认</title>
<style type="text/css">
	tr{
		cursor: pointer;
	}
</style>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
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
//得到详细信息
<%-- function showDialog(batchNo,seqNo,flag) 
{  	
	App.submitShowProgress();
	var str = "&batchNo="+batchNo+"&seqNo="+seqNo+"&flag="+flag;
	var url = "<%=request.getContextPath()%>/pay/orderpaymgr/surePayType.do?<%=WebConsts.FUNC_ID_KEY%>=03070501"+str;
	window.showModalDialog(url,null,"dialogHeight=500px;dialogWidth=800px;center=yes;status=no;");
	App.submitFinish();
} --%>
function doValidate(){
	//提交前调用
    if(!App.valid("#orderPayForm")){
	 return;
	}
// 	var count = 0;
// 	$("#orderPayForm").find("input[name='radio']").each(function(){
// 		if($(this).is(':checked')){
// 			count++;
// 			selectedVal($(this));
// 		}
// 	});
// 	//alert(count);
// 	if(count==0){
// 		App.notyError("请选择一条付款！");
// 		return false;
// 	}
	return true;
}
//点击一行使被选中
function selectRow(obj){
	$(obj).find("input[name='radio']").attr("checked",true);
}
//被选中时给hidden值
function selectedVal(obj){
	var flag = "${orderPayInfo.flag}";
	if(flag == '0'){
		$("#erpPayId").val($(obj).parent().parent().find("#payIdSpan").val());
	}else if(flag == '1'){
		$("#normalPayId").val($(obj).parent().parent().find("#norPayIdSpan").val());
		$("#sortId").val($(obj).parent().parent().find("#sortIdSpan").val());
		$("#cleanAmt").val($(obj).parent().parent().find("#cleanAmtSpan").val());
	}
}
//点击行使之被选中
$(function(){
	$('table tr').click(function(){
		$("table tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
});
function sure(button, url){
	
	var count = 0;
	$("#orderPayForm").find("input[name='radio']").each(function(){
		if($(this).is(':checked')){
			count++;
			selectedVal($(this));
		}
	});
	//alert(count);
	if(count==0){
		App.notyError("请选择一条付款！");
		return false;
	}
	
	$( "<div>确认选择?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
// 				App.submitForm(button, url);
				var form = $('#orderPayForm')[0];
				form.action = url;
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="03070502"/>
<form  method="post" id="orderPayForm">
	<p:token/>
	<input type="hidden" id="batchNo" name="batchNo" value="${orderPayInfo.batchNo }"/>
	<input type="hidden" id="seqNo" name="seqNo" value="${orderPayInfo.seqNo }"/>
	<input type="hidden" id="payAmt" name="payAmt" value="${orderPayInfo.payAmt }"/>
	<input type="hidden" id="flag" name="flag" value="${orderPayInfo.flag }"/>
	<input type="hidden" id="payCancelState" name="payCancelState" value="${orderPayInfo.payCancelState }"/>
	
	<input type="hidden" id="erpPayId" name="erpPayId"/>
	
	<input type="hidden" id="normalPayId" name="normalPayId"/>
	<input type="hidden" id="sortId" name="sortId"/>
	<input type="hidden" id="cleanAmt" name="cleanAmt"/>
	
	<c:if test="${orderPayInfo.flag == '0' }">
		<table class="tableList">
			<tr>
				<th width="6%">选择</th>
				<th width="20%">ERP付款单号</th>
				<th width="20%">合同号</th>
				<th width="10%">发票金额</th>
				<th width="10%">付款金额</th>
				<th width="10%">暂收金额</th>
				<th width="10%">付款日期</th>
				<th width="14%">累计付款金额</th>
			</tr>
			<c:if test="${!empty list}">
			<c:forEach items="${list}" var="bean">
			<tr onclick="selectRow(this);" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td><input type="radio" name="radio"/></td>
				<td>${bean.payId }<input type="hidden" id="payIdSpan" value="${bean.payId }"></td>
				<td>${bean.cntNum }</td>
				<td><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${bean.suspenseAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
				<td>${bean.payDate }</td>
				<td>${bean.payTotalAmt }</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty list}">
				<tr>
					<td class="red" colspan="8" style="text-align: center;">没有找到相关信息</td>
				</tr>
			</c:if>
		</table>
	</c:if>
	<c:if test="${orderPayInfo.flag == '1' }">
		<table class="tableList">
			<tr>
				<th width="6%">选择</th>
				<th width="15%">正常付款单号</th>
				<th width="10%">子序号</th>
				<th width="15%">结清项目</th>
				<th width="15%">未结清金额</th>
				<th width="10%">结清金额</th>
				<th width="14%">结清说明</th>
				<th width="15%">结清原因</th>
			</tr>
			<c:if test="${!empty list}">
			<c:forEach items="${list}" var="bean">
			<tr onclick="selectRow(this);" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td><input type="radio" name="radio"/></td>
				<td>${bean.normalPayId} <input type="hidden" id="norPayIdSpan" value="${bean.normalPayId }"></td>
				<td>${bean.sortId }<input type="hidden" id="sortIdSpan" value="${bean.sortId }"></td>
				<td>${bean.cleanProject }</td>
				<td><fmt:formatNumber type="number" value="${bean.uncleanAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
				<td><fmt:formatNumber type="number" value="${bean.cleanAmt}" minFractionDigits="2" maxFractionDigits="2"/><input type="hidden" id="cleanAmtSpan" value="${bean.cleanAmt }"></td>
				<td>${bean.cleanMemo }</td>
				<td>${bean.cleanReason }</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty list}">
				<tr>
					<td class="red" colspan="8" style="text-align: center;">没有找到相关信息</td>
				</tr>
			</c:if>
		</table>
	</c:if>
	<br/>
	<p:button funcId="03070502" value="确认" onclick="sure"/>
	<input type="button" value="返回" onclick="backToLastPage('${uri}');"/>
</form>
</body>
</html>