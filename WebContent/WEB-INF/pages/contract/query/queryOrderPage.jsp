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
<meta name="decorator" content="tabs">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单信息</title>
<base target="_self">
<script type="text/javascript">
	function backToLastPage2(uri)
	{
		if(uri != null && uri != '')
		{
			window.top.location.href = uri;
		}
		else
		{
			history.back();
		}
	}
	
	function orderDtl(orderId,flag)
	{
// 		alert(orderId);
		var form = $("#orderDtl")[0];
		form.action="<%=request.getContextPath()%>/contract/query/getOrderInfo.do?<%=WebConsts.FUNC_ID_KEY %>=0302060501&orderId="+orderId+"&flag="+flag;
		App.submit(form);
	}

</script>
</head>
<body>
<br/>
<p:authFunc funcArray="0302"/>
<form action="" method="post" id="orderDtl"></form>
<form action="" method="post" id="feeTypeForm">	
<div id="targetDiv">
	<table class="tableList" id="targetT">
		<tr id="th">
			<th width="12%">订单号</th>
			<th width="10%">采购方式</th>
			<th width="15%">采购部门</th>
			<th width="8%">状态</th>
			<th width="12%">操作说明</th>
			<th width="20%">订单说明</th>
			<th width="5%">操作</th>
		</tr>
		<c:forEach items="${orderList}" var="order">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" class="trOther">
				<td>${order.orderId }</td>
				<td>${order.procurementRoute }</td>
				<td>${order.orderDutyCodeName }</td>
				<td>${order.dataFlag }</td>
				<td>${order.operMemo }</td>
				<td>${order.orderMemo }</td>
				<td>
					<div class="detail">
					    <a href="#"  onclick="orderDtl('${order.orderId}','${flag}');" title="订单详细" ></a>
					</div>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty orderList}">
			<tr>
				<td colspan="7" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</div>	
<br/>
	<c:if test="${flag==0 }">
		<div style="text-align:center;" >
			<input type="button" value="返回" onclick="backToLastPage2('${contracturi}');">
		</div>
	</c:if>
	<c:if test="${empty flag}">
		<div style="text-align:center;" >
			<input type="button" value="关闭" onclick="art.dialog.close()">
		</div>
	</c:if>
</form>
</body>
</html>