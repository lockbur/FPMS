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
<title>预付款明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
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
			    title:"查看流转日志",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}
function scan(){
	var url="<%=request.getContextPath()%>/common/pay/scan/preupdate.do?id=${payInfo.payId}&icmsPkuuid=${payInfo.icmsPkuuid}&dataFlag=${payInfo.dataFlag}&isPrePay=${payInfo.isPrePay}";
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    drag:false,
			    title:"中行影像前端控件",
			    id:"dialogCutPage",
				close: function(){
					var cntSuccScan = art.dialog.data('cntSuccScan');
					if(cntSuccScan){
						$("#scan").attr("disabled", true);
					    $("#scan").attr("aria-disabled",true);
					    $("#scan").addClass("ui-button-disabled");
					    $("#scan").addClass("ui-state-disabled"); 
					} else{
						$("#scan").removeAttr("disabled");
					    $("#scan").removeAttr("aria-disabled");
					    $("#scan").removeClass("ui-button-disabled");
					    $("#scan").removeClass("ui-state-disabled"); 
				   
					}
					//$("#scan").hide();
				}
			}
		 );
}
</script>
</head>
<body>
<p:authFunc funcArray="03030611"/>
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
							${payInfo.cntNum}
						</td>
						<td class="tdLeft" width="25%">进度</td>
						<td class="tdRight" width="25%">
							<fmt:parseNumber value="${payInfo.normarlTotalAmt+payInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${payInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%	
						</td>
					</tr>
					<tr>
						<td class="tdLeft">不含税金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.cntAmt}" minFractionDigits="2"/>
						</td>
						<td class="tdLeft">税额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.cntTaxAmt}" minFractionDigits="2"/>
							
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额（人民币）</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.cntAmt}" minFractionDigits="2"/>元<br/>
							其中质保金：${payInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							<c:if test="${payInfo.cntType=='0'}">
					    	<c:out value="资产类"></c:out>
					   	</c:if>
					    <c:if test="${payInfo.cntType=='1'}">
					    	<c:out value="费用类"></c:out>
					    </c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">正常付款金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.normarlTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">预付款总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.advanceTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">冻结金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.freezeTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额（人民币）</td>
						<td class="tdRight">
							<c:if test="${!empty payInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${payInfo.suspenseTotalAmt}" minFractionDigits="2"/>元
							</c:if>
							<c:if test="${empty payInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
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
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号</td>
						<td class="tdRight">
							${payInfo.invoiceId }
						</td>
						<td class="tdLeft">附件张数</td>
						<td class="tdRight"> 
							${payInfo.attachmentNum }张
						</td>
					</tr>
					<tr>
						<td class="tdLeft">收款单位</td>
						<td class="tdRight">
							${payInfo.providerName }
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							${payInfo.provActNo }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
							${payInfo.bankName}
						</td>
						<td class="tdLeft">币别</td>
						<td class="tdRight">
							${payInfo.provActCurr }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">预付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${payInfo.payDate }
						</td>
					</tr>
					<tr>
						<td class="tdLeft"><span class="spanFont">付款方式</span></td>
							<td class="tdRight">
								<c:out value="${payInfo.payModeName}"></c:out>
							</td>
						<td class="tdLeft">预付款说明</td>
						<td class="tdRight" >
							${payInfo.invoiceMemo }
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
		<br>
	<div style="text-align:center;" >
	<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
				<c:if test="${!empty payInfo.id}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
				<c:if test="${payInfo.icmsEdit == '1'}">
					<input type="button" id="scan" value="重新扫描" onclick="scan()"/>
				</c:if>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
<!-- 审批弹出框 -->
<div id="auditDiv" style="display: none">
	<form action="" method="post" id="auditForm">
		<input type="hidden" name="payId" value="${payInfo.payId}">
		<input type="hidden" name="isPrePay" value="${payInfo.isPrePay}"/>
		<table width="98%" id="auditTable">
			<input type="hidden" id="icmsEdit" name="icmsEdit" value="${payInfo.icmsEdit}"/>
			<input type="hidden" id="dataFlag" name="dataFlag" value="${payInfo.dataFlag}"/>
			<tr>
				<td align="left" colspan="2">
					<br>转发意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="auditMemo" name="auditMemo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>