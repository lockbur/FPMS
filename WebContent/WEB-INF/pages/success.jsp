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
<title>操作成功</title>
<style>
.exPanel .contentBox .title{
	background: url("<%=request.getContextPath()%>/common/images/deal_success.png") no-repeat;
}
</style>
<script type="text/javascript">
function printFace(buttonValue){
	if(buttonValue=="正常付款封面打印"){
		var payId = '${payId}';
		var isOrder = '${isOrder}';
		var invoiceId = '${invoiceId}';
		normalPrint(payId,isOrder,invoiceId);
		return;
	}else if(buttonValue=="预付款封面打印"){
		var payId = '${payId}';
		var isOrder = '${isOrder}';
		notNormalPrint(payId,isOrder);
		return;
	}else if(buttonValue=="暂收结清封面打印"){
		var normalPayId = '${normalPayId}';
		var sortId = '${sortId}';
		var cntNum = '${cntNum}';
		cleanPayPrint(normalPayId,sortId,cntNum);
		return;
	}else if(buttonValue="导入列表"){
		var form=$("#a")[0];
		form.action="<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101";
		App.submit(form);
	}
}
function normalPrint(payId,isOrder,invoiceId){
	var url = "<%=request.getContextPath()%>/pay/payAdd/print.do?<%=WebConsts.FUNC_ID_KEY%>=03030317&payId="+payId+"&isOrder="+isOrder+"&invoiceId="+invoiceId;
	$.dialog.open(
			url,
			{
				width: "65%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"正常付款封面打印",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}

function notNormalPrint(payId,isOrder){
	var url = "<%=request.getContextPath()%>/pay/payAdd/printAdv.do?<%=WebConsts.FUNC_ID_KEY%>=03030318&payId="+payId+"&isOrder="+isOrder;
	$.dialog.open(
			url,
			{
				width: "65%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"预付款封面打印",
			    id:"dialogCutPage",
				close: function(){
				}		
			}
		 );
}

function cleanPayPrint(normalPayId,sortId,cntNum){
	$.dialog.open(
			"<%=request.getContextPath()%>/cleanpaydeal/cleanpayquery/print.do?<%=WebConsts.FUNC_ID_KEY%>=03050503&normalPayId="+normalPayId+"&sortId="+sortId+"&cntNum="+cntNum,
			{
				
				width: "70%", 
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"暂收结清封面打印",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		)
}

</script>
</head>
<body>
<form id="a" method="post">
	<input type="hidden" id="imOutportType" name="imOutportType" value="0">
</form>
<div class="exPanel">
	<div class="contentBox">
		<span class="title">操作成功</span>
		<div class="exDetail">
			<p class="returnCode">描述:</p>
		</div>
		<p:message className="message-panel" infoCls="info-message" warningCls="warning-message" errorCls="error-message"/>
		<br/>
		<p:returnLink/>
		<br/>
		<c:if test="${!empty buttonValue}">
			<input type="button" value="${buttonValue}" onclick="printFace('${buttonValue}')">
		</c:if>
	</div>
</div>
</body>
</html>