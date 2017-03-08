<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同付款明细</title>
<script type="text/javascript">
function pageInit()
{
	var tabs = $( "#tabs" ).tabs({
		active: $('#tabIndex').val(),
		activate: function(){
			$('#tabIndex').val($(this).tabs('option', 'active'));
		}
	});	
}

//预付款明细
function detail(payId,isPrePay,cntNum)
{
	var form = $("#tempForm");
 	$("#tempForm #payId").val(payId);
	$("#tempForm #isPrePay").val(isPrePay);
	$("#tempForm #cntNum").val(cntNum);
 	form.attr('action', '<%=request.getContextPath()%>/pay/payquery/detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030703');
	App.submit(form);
}

function beforeSubmit(form) {
	if ($(form).find('input[name="tabsIndex"]').length > 0) {
		$(form).find('input[name="tabsIndex"]').val($('#tabIndex').val());
	} else {
		$(form).append("<input type='hidden' name='tabsIndex' value='" + $('#tabIndex').val() + "'/>");
	} 
}	
</script>
</head>

<body>
<form:hidden id="tabIndex" path="selectInfo.tabsIndex"/>
<p:authFunc funcArray="03030302,03030303"/>
<form action="" method="post" id="tempForm">
<input type="hidden" id="isPrePay" name="isPrePay">
<input type="hidden" id="payId" name="payId">
<input type="hidden" id="cntNum" name="cntNum">
	<table>
		<tr>
			<th colspan="4">
				合同信息
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">合同号</td>
			<td class="tdRight" width="25%">
				${cntInfo.cntNum}
			</td>
			<td class="tdLeft" width="25%">进度</td>
			<td class="tdRight" width="25%">
				<fmt:parseNumber value="${cntInfo.normarlTotalAmt+cntInfo.advanceTotalAmt}" var="a"/>
				<fmt:parseNumber value="${cntInfo.cntAllAmt}" var="b"/>
				<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同金额（人民币）</td>
			<td class="tdRight">
				<fmt:formatNumber type="number" value="${cntInfo.cntAmt}" minFractionDigits="2"/>元<br/>
				其中质保金：${cntInfo.zbAmt}%
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				<c:if test="${cntInfo.cntType=='0'}">
					<c:out value="资产类"></c:out>
				</c:if>
				<c:if test="${cntInfo.cntType=='1'}">
					<c:out value="费用类"></c:out>
				</c:if>
			</td>
		<tr>
						<td class="tdLeft">正常付款金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty cntInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${cntInfo.normarlTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty cntInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">预付款总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty cntInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${cntInfo.advanceTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty cntInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">冻结金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty cntInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${cntInfo.freezeTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty cntInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty cntInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${cntInfo.suspenseTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty cntInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
					</tr>
	</table>
	<br/>
	<div id="tabs" style="border: 0;">
		<ul>
			<li><a href="#tabs-1">正常付款列表</a></li>
			<li><a href="#tabs-2">预付款列表</a></li>
		</ul>
	    <div id="tabs-1" style="padding: 0;">	
			<table class="tableList">
				<tr>
					<th width="8%">付款单号</th>
					<th width="7%">收款单位</th>
					<th width="14%">发票号</th>
					<th width="11%">发票金额(元)</th>
					<th width="11%">付款金额(元)</th>
					<th width="11%">暂收金额(元)</th>
					<th width="13%">已结清暂收金额</th>
					<th width="13%">未结清暂收金额</th>
					<th width="6%">状态</th>
					<th width="6%">操作</th>
				</tr>
				<c:if test="${!empty payList}">
					<c:forEach items="${payList}" var="bean">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
							<td>${bean.payId}</td>
							<td>${bean.providerName}</td>
							<td>${bean.invoiceId}</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.suspenseAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.susTotalAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.uncleanAmt}" minFractionDigits="2"/></td>
							<td>${bean.payDataFlag}</td>
							<td>
								<div class="detail">
									<a href="#" onclick="detail('${bean.payId}','${bean.isPrePay}','${cntInfo.cntNum}');" title="明细"></a>
								</div>
	
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty payList}">
					<tr><td style="text-align: center;" colspan="9"><span class="red">没有找到相关信息</span></td></tr>
				</c:if>
			</table>
		</div>
	    <div id="tabs-2" style="padding: 0;">	
			<table class="tableList">
				<tr>
					<th width="20%">付款单号</th>
					<th width="18%">收款单位</th>
					<th width="20%">发票号</th>
					<th width="11%">发票金额(元)</th>
					<th width="11%">付款金额(元)</th>
					<th width="10">状态</th>
					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty prePayList}">
					<c:forEach items="${prePayList}" var="bean">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
							<td>${bean.payId}</td>
							<td>${bean.providerName}</td>
							<td>${bean.invoiceId}</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/></td>
							<td class="tdr">${bean.payDataFlag}</td>
							<td >
								<div class="detail">
									<a href="#" onclick="detail('${bean.payId}','${bean.isPrePay}','${cntInfo.cntNum}');" title="明细"></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty prePayList}">
					<tr><td style="text-align: center;" colspan="7"><span class="red">没有找到相关信息</span></td></tr>
				</c:if>
			</table>
	    </div>
    </div>
</form>
</br>
<div style="text-align:center;" >
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>
</body>
</html>