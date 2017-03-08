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
<meta name="decorator" content="tabs">
<title>付款查询</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#ouCode").combobox();
	$("#isOrder").combobox();
	$("#isCreditNote").combobox();
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
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
function selProvider(){
	App.submitShowProgress();
	var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	App.submitFinish();
	if(!proObj){//判断谷歌浏览器返回问题
		proObj = window.returnValue;
		if(proObj){
			$("#providerName").val(proObj.providerName);
		}
	}else{
		$("#providerName").val(proObj.providerName);
	}
}
function showDetail(payId,isPrePay,cntNum){
	var form = $("#queryDetailForm");
 	$("#queryDetailForm #payId1").val(payId);
	$("#queryDetailForm #isPrePay1").val(isPrePay);
	$("#queryDetailForm #cntNum1").val(cntNum);
	$("#queryDetailForm #fromFlag1").val("contract");
 	form.attr('action', '<%=request.getContextPath()%>/pay/payquery/detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030703');
	App.submit(form);
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
<p:authFunc funcArray="03030701"/>
<form action="" method="post" id="queryDetailForm">
	<input type="hidden" id="payId1" name="payId"  class="base-input-text"/>
	<input type="hidden" id="isPrePay1" name="isPrePay"  class="base-input-text"/>
	<input type="hidden" id="cntNum1" name="cntNum"  class="base-input-text"/>
	<input type="hidden" id="fromFlag1" name="fromFlag"  class="base-input-text"/>
</form>
<form  method="post" id="tempForm">
<input type="hidden" id="isPrePay" name="isPrePay">
	<p:token/>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
			 		<th width="11%">合同号</th>
					<th width="10%">付款单号</th>
					<th width="9%">付款日期</th>
					<th width="9%">付款金额(元)</th>
					<th width="7%">付款类型</th>
					<th width="11%">发票号</th>
					<th width="10%">发票金额(元)</th>
					<th width="8%">收款单位</th>
					<th width="7%">状态</th>
					<th width="12%">发票说明</th>
					<th width="6%">操作</th>
			</tr>
			 <c:forEach items="${list}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc" ><c:out value="${sedList.cntNum}"></c:out></td>
					<td class="tdc" ><c:out value="${sedList.payId}"></c:out></td>
					<td class="tdc" ><c:out value="${sedList.payDate}"/></td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.payAmt}" minFractionDigits="2"/></td>
					<td >
						<c:if test="${sedList.isPrePay=='Y'}">
							<c:out value="预付款"/>
						</c:if>
						<c:if test="${sedList.isPrePay=='N'}">
							<c:if test="${sedList.isCreditNote== '0'}">
								贷项通知单
							</c:if>
							<c:if test="${sedList.isCreditNote== '1'}">
								正常付款
							</c:if>
						</c:if>
					</td>
					<td><c:out value="${sedList.invoiceId}"/></td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.invoiceAmt}" minFractionDigits="2"/></td>
					<td><c:out value="${sedList.providerName}"/></td>
					<td>
						<c:out value="${sedList.payDataFlag}"/>
					</td>
					<td>
						<c:out value="${sedList.invoiceMemo}"/>
					</td>
						<td>
							<div class="detail">
								<a href="#" onclick="showDetail('${sedList.payId}','${sedList.isPrePay}','${sedList.cntNum}')" title="确认"></a>
							</div>
						</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
					<tr><td style="text-align: center;" colspan="100"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
</body>
</html>