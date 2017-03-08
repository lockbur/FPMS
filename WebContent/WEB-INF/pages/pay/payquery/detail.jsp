<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同付款信息</title>
<script type="text/javascript">
function  pageInit(){
	
}
function showDetail(payId,isPrePay){
	var form = $("#tempForm");
 	$("#tempForm #payId").val(payId);
	$("#tempForm #isPrePay").val(isPrePay);
 	form.attr('action', '<%=request.getContextPath()%>/pay/payquery/payDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030703');
	App.submit(form);
}
</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form  method="post" id="tempForm">
<input type="hidden" id="isPrePay" name="isPrePay">
<input type="hidden" id="payId" name="payId">
</form>
	<table >
		<tr>
			<th  colspan="4"><b>合同信息</b></th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%"><span class="spanFont">合同号</span></td>
			<td class="tdRight" width="30%">
				 <c:out value="${cntInfo.cntNum}"></c:out>
			</td>
			<td class="tdLeft" width="20%"><span class="spanFont">进度</span></td>
			<td class="tdRight" width="30%">
				<fmt:parseNumber value="${cntInfo.normarlTotalAmt+cntInfo.advanceTotalAmt}" var="a"/>
				<fmt:parseNumber value="${cntInfo.cntAllAmt}" var="b"/>
				<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%	
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%"><span class="spanFont">合同金额(元)</span></td>
			<td class="tdRight" width="30%">
				<fmt:formatNumber type="number" value="${cntInfo.cntAmt}" minFractionDigits="2"/>&nbsp;&nbsp;元  <br>
				质保金:<fmt:formatNumber type="number" value="${cntInfo.zbAmt}" minFractionDigits="2"/>元
			</td>
			<td class="tdLeft" width="20%"><span class="spanFont">已付金额(元)</span></td>
			<td class="tdRight" width="30%">
				<fmt:formatNumber type="number" value="${cntInfo.normarlTotalAmt+cntInfo.advanceTotalAmt}" minFractionDigits="2"/>元
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%"><span class="spanFont">合同类型</span></td>
			<td class="tdRight" width="30%">
				<c:if test="${cntInfo.cntType=='0'}">
			    	<c:out value="资产类"></c:out>
			   	</c:if>
			    <c:if test="${cntInfo.cntType=='1'}">
			    	<c:out value="费用类"></c:out>
			    </c:if>
			</td>
			<td class="tdLeft" width="20%"><span class="spanFont">预付款金额(人民币)</span></td>
			<td class="tdRight" width="30%">
				<fmt:formatNumber type="number" value="${cntInfo.advanceTotalAmt}" minFractionDigits="2"/>元
			</td>
		</tr>
	</table>
	<c:if test="${cntInfo.isPrePay=='Y'}">
		<table >
			<tr>
				<td  colspan="8"><b>预付款列表</b></td>
			</tr>
			<tr>
				<th>付款单号</th>
				<th >付款类型</th>
				<th>收款单位</th>
				<th >发票号</th>
				<th >发票金额</th>
				<th >付款金额</th>
				<th >状态</th>
				<th >操作</th>
			</tr>
		<c:forEach items="${payList}" var="sedList">
			<tr>
				<td><c:out value="${sedList.payId}"></c:out></td>
				<td><c:out value="预付款"></c:out></td>
				<td><c:out value="${sedList.providerName}"></c:out></td>
				<td><c:out value="${sedList.invoiceId}"></c:out></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${sedList.invoiceAmt}"  minFractionDigits="2" /></td>
				<td class="tdr">
					<fmt:formatNumber type="number" value="${sedList.payAmt}"  minFractionDigits="2" />
				</td>
				<td><c:out value="${sedList.payDataFlagText}"></c:out></td>
				<td  width="15%"><a onclick="showDetail('${sedList.payId}','${sedList.isPrePay}')" style="cursor: pointer;"><c:out value="明细"/></a></td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
	<c:if test="${cntInfo.isPrePay=='N'}">
		<table >
			<tr>
				<td  colspan="9"><b>正常付款列表</b></td>
			</tr>
			<tr>
				<th>付款单号</th>
				<th >付款类型</th>
				<th>收款单位</th>
				<th >发票号</th>
				<th >发票金额</th>
				<th >付款金额</th>
				<th >暂收金额</th>
				<th >状态</th>
				<th >操作</th>
			</tr>
		<c:forEach items="${payList}" var="sedList">
			<tr>
				<td><c:out value="${sedList.payId}"></c:out></td>
				<td><c:out value="正常付款"></c:out></td>
				<td><c:out value="${sedList.providerName}"></c:out></td>
				<td><c:out value="${sedList.invoiceId}"></c:out></td>
				<td><fmt:formatNumber type="number" value="${sedList.invoiceAmt}"  minFractionDigits="2" /></td>
				<td>
					<fmt:formatNumber type="number" value="${sedList.payAmt}"  minFractionDigits="2" />
				</td>
				<td>
					<fmt:formatNumber type="number" value="${sedList.suspenseAmt}"  minFractionDigits="2" />
				</td>
				<td><c:out value="${sedList.payDataFlagText}"></c:out></td>
				<td  width="15%"><a onclick="showDetail('${sedList.payId}','${sedList.isPrePay}')" style="cursor: pointer;"><c:out value="明细"/></a></td>
			</tr>
		</c:forEach>
	</table>
  </c:if>
	<br>
	<div style="text-align:center;" >
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</body>
</html>