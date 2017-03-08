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
<title>正常付款明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
//预付款核销明细查询
function advanceCancelDetail(payId)
{
<%-- 	var form=$("#payDetailForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payAdd/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030309&flag=2&advancePayId="+payId;
	App.submit(form); --%>	
	var url = "<%=request.getContextPath()%>/pay/payAdd/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030309&flag=2&advancePayId="+payId;
// 	App.submitShowProgress();
// 	window.showModalDialog(url,null,"dialogHeight=600px;dialogWidth=900px;center=yes;status=no;");
// 	App.submitFinish();
	$.dialog.open(
		url,
		{
			padding:0,
			width: "65%",
			height: "80%",
			lock: true,
		    fixed: true,
			esc: true,
			resize:	false,
		    title:"预付款详情",
		    id:"dialogAdvCancelInfoDetail",
		    close: function(){
			}		
		}
	)
}

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
				width: "75%",
				height: "90%",
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
						<th colspan="4">
							合同信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="25%">合同号</td>
						<td class="tdRight" width="25%">
							${constractInfo.cntNum}
							<input type="hidden" id="cntNum" name="cntNum" value="${constractInfo.cntNum}"/>
						</td>
						<td class="tdLeft" width="25%">进度</td>
						<td class="tdRight" width="25%">
							<fmt:parseNumber value="${constractInfo.normarlTotalAmt+constractInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${constractInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" minFractionDigits="2" value="${a/b*100}" maxFractionDigits="2"/>%
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同总金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${constractInfo.cntAllAmt}" minFractionDigits="2"/><br/>
							其中质保金：${constractInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							${constractInfo.cntTypeName}
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">不含税金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${constractInfo.cntAmt}" minFractionDigits="2"/>
						</td>
						<td class="tdLeft">税额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${constractInfo.cntTaxAmt}" minFractionDigits="2"/>
							
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">正常付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.normarlTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">预付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.advanceTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						
					</tr>
					<tr>
						<td class="tdLeft">冻结金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.freezeTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.suspenseTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
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
							<input type="hidden" id="payId" name="payId" value="${payInfo.payId}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号</td>
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
							${payInfo.bankInfo}
						</td>
						<td class="tdLeft">发票金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft">不含税金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmtNotax}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">税额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmtTax}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft">预付款核销金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.advanceCancelAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.suspenseAmt}">
								<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.suspenseAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}">
						<td class="tdLeft">付款方式</td>
						<td class="tdRight">
							${payInfo.payMode}
						</td>
						</c:if>
						<c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">
						<td class="tdLeft"></td>
						<td class="tdRight">
						</td>
						</c:if>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${payInfo.advanceCancelAmt != 0}">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">预付款核销信息</th>
					</tr>
					<tr>
						<th width="30%">预付款批次</th>
						<th width="30%">预付款金额(元)</th>
						<th width="30%">已核销金额(元)</th>
						<th width="10%">操作</th>
					</tr>
					<c:forEach items="${payAdvanceCancelList}" var="bean">
						<tr>
							<td>${bean.payId}</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.cancelAmt}" minFractionDigits="2"/></td>
							<td>
								<div class="detail">
									<a href="#" onclick="advanceCancelDetail('${bean.payId}');" title="明细"></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="100">合同采购设备(预付款)</th>
					</tr>
					<tr>
						<th width="5%" rowspan="2">核算码</th>
						<th width="6%" rowspan="2">项目</th>
						<th width="6%" rowspan="2">物料名称</th>
						<th width="6%" rowspan="2">费用部门</th>
						<th width="23%" colspan="3">不含税金额(元)</th>
						<th width="5%" rowspan="2">税码</th>
						<th width="23%" colspan="3">税额(元)</th>
						<th width="20%" colspan="2">发票分配金额(元)</th>
						<th width="6%" rowspan="2">发票行说明</th>
					</tr>
					<tr>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>不含税金额</th>
						<th>税额</th>
					</tr>
					<c:forEach items="${payAdvDevices}" var="bean">
						<tr>
							<td>${bean.cglCode }</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td>${bean.taxCode }</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.subInvoiceAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.addTaxAmt}" minFractionDigits="2"/></td>
							<td>${bean.ivrowMemo }</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		</c:if>
		<%-- <c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}"> --%>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table >
					<tr class="collspan-control">
						<th colspan="4">正常付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft">付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${payInfo.payDate}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>
						</td>
						<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }">
						<td class="tdLeft">暂收付款日期</td>
						<td class="tdRight">
							${payInfo.suspenseDate}
						</td>
						</c:if>
						<c:if test="${payInfo.suspenseAmt == null || payInfo.suspenseAmt ==0 }">
							<td class="tdLeft"></td>
							<td class="tdRight">
							</td>
						</c:if>
					</tr>
<%-- 					<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }"> --%>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">暂收项目</td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							${payInfo.suspenseName} --%>
<!-- 						</td> -->
<!-- 						<td class="tdLeft">预处理时间（月份）</td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							${payInfo.suspensePeriod} --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 					</c:if> --%>
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3" >
							${payInfo.invoiceMemo}
						</td>
					</tr>
<%-- 					<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }"> --%>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">挂帐原因</td> -->
<!-- 						<td class="tdRight" colspan="3" > -->
<%-- 							${payInfo.suspenseReason} --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 					</c:if> --%>
				</table>
				<c:if test="${!empty payDevices}">
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="100">合同采购设备(正常付款)</th>
					</tr>
					<tr>
						<tr>
						<th width="5%" rowspan="2">核算码</th>
						<th width="5%" rowspan="2">项目</th>
						<th width="5%" rowspan="2">物料名称</th>
						<th width="5%" rowspan="2">费用部门</th>
						<th width="23%" colspan="3">不含税金额(元)</th>
						<th width="5%" rowspan="2">税码</th>
						<th width="23%" colspan="3">税额(元)</th>
						<th width="20%" colspan="2">发票分配金额(元)</th>
						<th width="6%" rowspan="2">发票行说明</th>
					</tr>
					<tr>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>总额</th>
						<th>已付</th>
						<th>冻结</th>
						<th>不含税金额</th>
						<th>税额</th>
					</tr>
					</tr>
					<c:forEach items="${payDevices}" var="bean">
						<tr>
							<td>${bean.cglCode }</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td>${bean.taxCode }</td>
							<td><fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.addTaxAmt}" minFractionDigits="2"/></td>
							<td>${bean.ivrowMemo }</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			</td>
		</tr>
		<%-- </c:if> --%>
		<tr>
			<td>
				<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
				<c:if test="${!empty payInfo.id}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
				<c:if test="${payInfo.dataFlag == 'C0' or payInfo.dataFlag == 'C1'}">
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