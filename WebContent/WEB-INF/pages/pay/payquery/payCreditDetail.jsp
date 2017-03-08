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
<title>贷项通知单明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">

//付款封面打印
function printFace(payId,isOrder,cntNum){
	var url = "<%=request.getContextPath()%>/pay/payAdd/print.do?<%=WebConsts.FUNC_ID_KEY%>=03030317&payId="+payId+"&isOrder="+isOrder+"&cntNum="+cntNum;
	$.dialog.open(
			url,
			{
				width: "70%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"付款封面打印",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}

//查询付款审批历史记录(弹窗显示)
function queryHis(payId){
	var url = "<%=request.getContextPath()%>/pay/payAdd/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030321&payId="+payId;
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "80%",
				lock: true,
			    fixed: true,
			    title:"查询审批历史记录",
			    id:"dialogCutPage",
				close: function(){
				}		
			}
		 );
}

//付款明细22文件
function queryPay22Detail(invoiceId){
	var url = "<%=request.getContextPath()%>/pay/payquery/queryPay22Detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030709&invoiceId="+invoiceId;
	$.dialog.open(
		url,
		{
			width: "60%",
			height: "80%",
			lock: true,
		    fixed: true,
		    title:"查看付款记录",
		    id:"queryPay22Detail",
			close: function(){
				
			}		
		}
	 );
}
</script>
</head>
<body>
<form action="" id="print" method="post"></form>
<form action="" id="payDetailForm" method="post">
	<table>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4" >
							原蓝字发票信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">合同号</td>
						<td class="tdRight" width="30%">
							${invoiceBlue.cntNum}
						</td>
						<td class="tdLeft">付款单号</td>
						<td class="tdRight">
							${invoiceBlue.payId}
						</td>
					</tr>
					<tr>
					<tr>
						<td class="tdLeft" width="20%">原蓝字发票编号</td>
						<td class="tdRight" width="30%">
							${invoiceBlue.invoiceId}
						</td>
						<td class="tdLeft">发票总金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
					<tr>
						<td class="tdLeft" width="20%">预付款核销金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.advanceCancelAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">正常付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${invoiceBlue.payAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
					<tr>
						<td class="tdLeft" width="20%">暂收金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.suspenseAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft" width="20%">累计暂收结清金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.susTotalAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票不含税金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmtNotax}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">发票税额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmtTax}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票剩余可冲销金额</td>
						<td class="tdRight" width="80%" colspan="3">
							<fmt:formatNumber type="number" value="${invoiceBlue.invoiceAmtLeft}" minFractionDigits="2"/>元
							<input type="hidden" id="blueSumLeft" value="${invoiceBlue.invoiceAmtLeft}">
						</td>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票状态</td>
						<td class="tdRight" width="30%">
							${invoiceBlue.dataFlagInvoiceName}
						</td>
						<td class="tdLeft">付款状态</td>
						<td class="tdRight">
							${invoiceBlue.dataFlagPayName}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">
							付款单号：${payInfo.payId}
						</th>
					</tr>
					<tr>
						<td class="tdLeft">红字发票号</td>
						<td class="tdRight" colspan="3">
							${payInfo.invoiceId}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">附件张数</td>
						<td class="tdRight"> 
							${payInfo.attachmentNum}
						</td>
						<td class="tdLeft">是否贷项通知单</td>
						<td class="tdRight">
							<c:if test="${payInfo.isCreditNote=='0'}">
								<c:out value="是"></c:out>
							</c:if>
							<c:if test="${payInfo.isCreditNote=='1'}">
								<c:out value="否"></c:out>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">收款单位</td>
						<td class="tdRight">
							${payInfo.providerName}
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							${payInfo.provActNo}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
							${payInfo.bankName}
						</td>
						<td class="tdLeft">红字发票总金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft"  >不含税金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmtNotax}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft"  >税额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmtTax}" minFractionDigits="2"/>元
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table >
					<tr class="collspan-control">
						<th colspan="4">贷项通知单付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft">贷项通知单付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${payInfo.payDate}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3" >
							${payInfo.invoiceMemo}
						</td>
					</tr>
				</table>
				<c:if test="${!empty payDevices}">
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="10">合同采购设备(贷项通知单)</th>
					</tr>
					<tr>
						<th width="10%">核算码</th>
						<th width="10%">项目</th>
						<th width="10%">物料名称</th>
						<th width="10%">费用部门</th>
						<th width="15%">冲销<br/>不含税金额</th>
						<th width="15%">冲销<br/>税额</th>
						<th width="10%">冲销<br/>总金额</th>
						<th width="10%">入账<br/>核算码</th>
						<th width="10%">发票行<br/>说明</th>
					</tr>
					<c:forEach items="${payDevices}" var="bean">
						<tr>
							<td>${bean.cglCode }</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt }" minFractionDigits="2"/>元</td>
							<td><fmt:formatNumber type="number" value="${bean.addTaxAmt }" minFractionDigits="2"/>元</td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt+bean.addTaxAmt}" minFractionDigits="2"/>元</td>
							<td>借：${bean.inCglCode }</td>
							<td>${bean.ivrowMemo }</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="查看原蓝字发票扫描文件" onclick="viewPayScanImg('${invoiceBlue.payId}','${invoiceBlue.icmsPkuuid }');"/>
				<c:if test="${!empty payInfo.icmsPkuuid}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid }');"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
				<c:if test="${payInfo.dataFlag == 'C0' or payInfo.dataFlag=='C1'}">
				<input type="button" value="打印封面" onclick="printFace('${payInfo.payId}','${constractInfo.isOrder }','${constractInfo.cntNum}');">
				</c:if>
				<input type="button" value="查询付款明细" onclick="queryPay22Detail('${payInfo.invoiceId}')">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>