<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>

<table>
	<tr>
		<td>
					<table >
				<tr class="collspan-control">
					<th  colspan="4"><b>合同信息</b></th>
				</tr>
				<tr>
					<td class="tdLeft" width="20%"><span class="spanFont">合同号</span></td>
					<td class="tdRight" width="30%">
						 <c:out value="${payInfo.cntNum}"></c:out>
						 <input type="hidden" id="cntNum" name="cntNum" value="${payInfo.cntNum}"/>
					</td>
					<td class="tdLeft" width="20%"><span class="spanFont">进度</span></td>
					<td class="tdRight" width="30%">
						<fmt:parseNumber value="${payInfo.normarlTotalAmt+payInfo.advanceTotalAmt}" var="a"/>
						<fmt:parseNumber value="${payInfo.cntAllAmt}" var="b"/>
						<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
					</td>
				</tr>
				<tr>
					<td class="tdLeft" width="20%"><span class="spanFont">合同总金额(人民币)</span></td>
					<td class="tdRight" width="30%">
						<fmt:formatNumber type="number" value="${payInfo.cntAllAmt}" minFractionDigits="2"/>元  <br>
						其中质保金：${payInfo.zbAmt}%
					</td>
					<td class="tdLeft" width="20%"><span class="spanFont">合同类型</span></td>
					<td class="tdRight" width="30%">
						<c:if test="${payInfo.cntType=='0'}">
					    	<c:out value="资产类"></c:out>
					   	</c:if>
					    <c:if test="${payInfo.cntType=='1'}">
					    	<c:out value="费用类"></c:out>
					    </c:if>
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
						<th   colspan="4">付款单号：${payInfo.payId}
							<input type="hidden" id="payId" name="payId" value="${payInfo.payId}"/></th>
					</tr>
					<tr>
						<td class="tdLeft"  ><span class="spanFont">发票号</span></td>
						<td class="tdRight"  colspan="3">
							<c:out value="${payInfo.invoiceId}"></c:out>
						</td>
						
					</tr>
					<tr>
						<td class="tdLeft">是否贷项通知单</td>
						<td class="tdRight">
							<c:if test="${payInfo.isCreditNote=='0'}">
								<c:out value="是"></c:out>
							</c:if>
							<c:if test="${payInfo.isCreditNote=='1'}">
								<c:out value="否"></c:out>
							</c:if>
						</td>
						<td class="tdLeft"><span class="spanFont">附件张数</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.attachmentNum}"></c:out>张
						</td>
					</tr>
					<tr>
						<td class="tdLeft"><span class="spanFont">收款单位</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.providerName}"></c:out>
						</td>
						<td class="tdLeft"><span class="spanFont">收款账号</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.provActNo}"></c:out>
						</td>
					</tr>
					<tr>
						<td class="tdLeft"><span class="spanFont">开户行信息</span></td>
						<td class="tdRight">
							<c:out value="${payInfo.bankName}"></c:out>
						</td>
						<c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}">
							<td class="tdLeft"><span class="spanFont">付款方式</span></td>
							<td class="tdRight">
								<c:out value="${payInfo.payModeName}"></c:out>
							</td>
						</c:if>
						<c:if test="${payInfo.advanceCancelAmt == payInfo.invoiceAmt}">
							<td class="tdLeft"></td>
							<td class="tdRight"></td>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft">发票状态</td>
						<td class="tdRight" >
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='DATA_FLAG_INVOICE' AND PARAM_VALUE='${payInfo.dataFlagInvoice}'"/>
						</td>
						<!-- 如果发票状态是取消的，付款状态是初始状态的 则不显示付款状态（当天成功当天取消不会给22付款文件） -->
						<c:if test="${payInfo.dataFlagInvoice=='3' && payInfo.dataFlagPay=='0'}">
							<td class="tdLeft" ></td>
							<td class="tdRight">
							</td>
						</c:if>
						<c:if test="${payInfo.dataFlagInvoice !='3' ||  payInfo.dataFlagPay !='0'}">
							<td class="tdLeft" >付款状态</td>
							<td class="tdRight">
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='DATA_FLAG_PAY'  AND PARAM_VALUE='${payInfo.dataFlagPay}'"/>
							</td>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft" rowspan="2">发票总金额=(预付款核销金额+付款金额+暂收金额)</td>
						<td class="tdRight" rowspan="2">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">不含税金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmtNotax}" minFractionDigits="2"/>元
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">税额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmtTax}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft" >付款累计金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.payTotalAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft" ></td>
						<td class="tdRight">
						</td>
					</tr>
					<tr>
						<td class="tdRight" colspan="4" style="text-align: center;padding: 0" >
							<%-- <span class="spanFont">预付款核销金额</span>
							【<fmt:formatNumber type="number" value="${payInfo.advanceCancelAmt}" minFractionDigits="2"/>】元
							&nbsp;&nbsp;<span class="spanFont">付款金额</span>
							【<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>】元&nbsp;&nbsp;
							<span class="spanFont" >暂收金额</span>
							<c:if test="${!empty payInfo.suspenseAmt}">
								【<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>】元&nbsp;&nbsp;
							</c:if>
							<c:if test="${empty payInfo.suspenseAmt}">
								【<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元】&nbsp;&nbsp;
							</c:if> --%>
							<table style="border: 0">
								<tr>
									<td class="tdLeft" style="border-bottom: 0;border-top: 0;border-left: 0"><span class="spanFont">预付款核销金额</span></td>
									<td class="tdRight" style="border-bottom: 0;border-top: 0;width: 13%"><fmt:formatNumber type="number" value="${payInfo.advanceCancelAmt}" minFractionDigits="2"/>元</td>
									<td class="tdLeft" style="border-bottom: 0;border-top: 0"><span class="spanFont">付款金额</span></td>
									<td class="tdRight" style="border-bottom: 0;border-top: 0;width: 13%"><fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>元</td>
									<td class="tdLeft" style="border-bottom: 0;border-top: 0"><span class="spanFont">暂收金额</span></td>
									<td class="tdRight" style="border-bottom: 0;border-top: 0;border-right:0;width: 13%">
										<c:if test="${!empty payInfo.suspenseAmt}">
											<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>元
										</c:if>
										<c:if test="${empty payInfo.suspenseAmt}">
											<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>元
										</c:if>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${payInfo.advanceCancelAmt!=0}">
			<tr>
			<td></td>
		   </tr>
			<tr>
				<td>
					<table class="tableList">
						<tr class="collspan-control">
							<th colspan="5"><b>预付款核销信息【预付款核销总金额=<span id="advanceCancelAmtSpan">${payInfo.advanceCancelAmt}</span>】</b></th>
						</tr>
						<tr>
							<th>预付款批次</th>
							<th>预付款金额(元)</th>
							<th>已核销金额(元)</th>
							<th>本次核销金额(元)【累计金额=<span id="cancelAmtsSpan">${payInfo.advanceCancelAmt}</span>】</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${prePayCancleList}" var="sedList">
						<tr>
							<td>
								<c:out value="${sedList.advancePayId}"></c:out>
							</td>
							<td class="tdr">
								<fmt:formatNumber type="number" value="${sedList.payAmt}" minFractionDigits="2"/>
							</td>
							<td class="tdr">
								<fmt:formatNumber type="number" value="${sedList.cancelAmtTotal}" minFractionDigits="2"/>
							</td>
							<td>
								<fmt:formatNumber type="number" value="${sedList.cancelAmt}" minFractionDigits="2"/>
							</td>
							<td align="center">
								<div class="detail" align="center">
									<a href="#" onclick="advanceCancelDetail('${sedList.advancePayId}');" title="明细"></a>
								</div>
							</td>
						</tr>	
						</c:forEach>
					</table>
					<table class="tableList">
						<tr class="collspan-control">
						<th colspan="15">合同采购设备(预付款)【核销总金额=<span id="advanceCancelAmtTotalSpan">${payInfo.advanceCancelAmt}</span>】</th>
					</tr>
					<tr>
						<th width="5%" rowspan="2">核算码</th>
						<th width="5%" rowspan="2">项目</th>
						<th width="6%" rowspan="2">物料名称</th>
						<th width="6%" rowspan="2">费用部门</th>
						<th width="21%" colspan="3">不含税金额(元)</th>
						<th width="5%" rowspan="2">税码</th>
						<th width="21%" colspan="3">税额(元)</th>
						<th width="20%" colspan="2">发票分配金额(元)<br>【累计金额=<span id="advancePayInvAmtsSpan">${payInfo.advanceCancelAmt}</span>】</th>
						<th width="6%" rowspan="2">发票行说明</th>
						<th width="5%" rowspan="2">入账核算码</th>
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
					<c:forEach items="${prePayDeviceList}" var="bean">
						<tr>
							<td>${bean.cglCode }
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/>
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td>${bean.taxCode }</td>
							<td><fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/>
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.addTaxAmt}" minFractionDigits="2"/></td>
							<td>${bean.ivrowMemo}</td>
							<td>借：${bean.inCglCode}</td>
						</tr>
					</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="15">合同采购设备(正常付款金额=付款金额+暂收金额)【正常付款总金额=<span id="payAmtTotalSpan">${payInfo.payAmt+payInfo.suspenseAmt}</span>】</th>
					</tr>
					
					<tr>
						<th width="5%" rowspan="2">核算码</th>
						<th width="5%" rowspan="2">项目</th>
						<th width="6%" rowspan="2">物料名称</th>
						<th width="6%" rowspan="2">费用部门</th>
						<th width="21%" colspan="3">不含税金额(元)</th>
						<th width="5%" rowspan="2">税码</th>
						<th width="21%" colspan="3">税额(元)</th>
						<th width="20%" colspan="2">发票分配金额(元) <br>【累计金额=<span id="payInvAmtsSpan">${payInfo.payAmt+payInfo.suspenseAmt}</span>】</th>
						<th width="6%" rowspan="2">发票行说明</th>
						<th width="5%" rowspan="2">入账核算码</th>
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
					<c:forEach items="${payDeviceList}" var="bean">
						<tr>
							<td>${bean.cglCode}</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td>
							<fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/>
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td>${bean.taxCode}</td>
							<td>
							<fmt:formatNumber type="number" value="${bean.taxAmt}" minFractionDigits="2"/>
							</td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmtTax}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.addTaxAmt}" minFractionDigits="2"/></td>
							<td>${bean.ivrowMemo}</td>
							<td>借：${bean.inCglCode}</td>
						</tr>
					</c:forEach>
	</table>
	<table>
					<tr class="collspan-control">
						<th colspan="4">正常付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">付款金额</td>
						<td class="tdRight" width="30%">
							<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>元
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${payInfo.payDate}
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
						<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }">
							<td class="tdLeft">暂收付款日期</td>
							<td class="tdRight">${payInfo.suspenseDate }</td>
						</c:if>
						<c:if test="${payInfo.suspenseAmt == null || payInfo.suspenseAmt ==0 }">
							<td class="tdLeft"></td>
							<td class="tdRight"></td>
						</c:if>
						
					</tr>
<%-- 					<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }"> --%>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">暂收项目</td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							${payInfo.suspenseName} --%>
<!-- 						</td> -->
<!-- 						<td class="tdLeft">预处理时间</td> -->
<!-- 						<td class="tdRight"> -->
<%-- 							${payInfo.suspensePeriod}月 --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 					</c:if> --%>
<!-- 					<tr> -->
<!-- 					</tr> -->
<%-- 					<c:if test="${payInfo.suspenseAmt != null && payInfo.suspenseAmt !=0 }"> --%>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">挂帐原因</td> -->
<!-- 						<td class="tdRight" colspan="3" > -->
<%-- 							${payInfo.suspenseReason} --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 					</c:if> --%>
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3">
							${payInfo.invoiceMemo}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</c:if>
	</table>
