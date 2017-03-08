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
<title>付款补录</title>
<style type="text/css">
	
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
	//查询付款审批历史记录(弹窗显示)
	function queryHis(payId){
		App.submitShowProgress();
		var url = "<%=request.getContextPath()%>/pay/payAdd/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030321&payId="+payId;
		window.showModalDialog(url,null,"dialogHeight=500px;dialogWidth=800px;center=yes;status=no;");
		App.submitFinish();
	}
</script>
</head>

<body>
<%-- <p:authFunc funcArray="03030402,03030403,03030408"/> --%>
<form action="" method="post" id="payModifyForm">
	<p:token/>
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
							<input type="hidden" id="isOrder" name="isOrder" value="${constractInfo.isOrder}"/>
							<input type="hidden" id="cntNum" name="cntNum" value="${constractInfo.cntNum}"/>
						</td>
						<td class="tdLeft" width="25%">进度</td>
						<td class="tdRight" width="25%">
							<fmt:parseNumber value="${constractInfo.normarlTotalAmt+constractInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${constractInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额(人民币)</td>
						<td class="tdRight">
							<input type="hidden" id="cntAmt" name="cntAmt" value="${constractInfo.cntAmt}"/>
							<fmt:formatNumber type="number" value="${constractInfo.cntAmt}" minFractionDigits="2"/><br/>
							其中质保金：${constractInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							${constractInfo.cntTypeName}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">正常付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.normarlTotalAmt}">
								<input type="hidden" id="normarlTotalAmt" name="normarlTotalAmt" value="${constractInfo.normarlTotalAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.normarlTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.normarlTotalAmt}">
								<input type="hidden" id="normarlTotalAmt" name="normarlTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">预付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.advanceTotalAmt}">
								<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="${constractInfo.advanceTotalAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.advanceTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.advanceTotalAmt}">
								<input type="hidden" id="advanceTotalAmt" name="advanceTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						
					</tr>
					<tr>
						<td class="tdLeft">冻结金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="${constractInfo.freezeTotalAmt-payInfo.payAmt-payInfo.suspenseAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.freezeTotalAmt-payInfo.payAmt-payInfo.suspenseAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.freezeTotalAmt}">
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="0"/>
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.suspenseTotalAmt}">
								<input type="hidden" id="suspenseTotalAmt" name="suspenseTotalAmt" value="${constractInfo.suspenseTotalAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.suspenseTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.suspenseTotalAmt}">
								<input type="hidden" id="suspenseTotalAmt" name="suspenseTotalAmt" value="0"/>
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
							<input type="hidden" id="dataFlag" name="dataFlag" value="${payInfo.dataFlag}"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">发票号</td>
						<td class="tdRight">
							${payInfo.invoiceId}
						</td>
						<td class="tdLeft">附件张数</td>
						<td class="tdRight"> 
<%-- 							<input type="text" id="attachmentNum"  value="${payInfo.attachmentNum}" name="attachmentNum" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/> --%>
								${payInfo.attachmentNum}
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">附件类型</td> -->
<!-- 						<td class="tdRight" colspan="3">  -->
<%-- 							<table id="atTypesTable">
								
<%-- 							</table> --%>
<%-- 							<c:forEach items="${listAtTypes}" var="bean" varStatus="vs"> --%>
<%-- 								<label><input type="checkbox" name="attachmentType" value='${bean.attachmentType }'>${bean.attachmentTypeName }</label> --%>
<%-- 								<c:if test="${(vs.index+1)%4 == 0}"> --%>
<%-- 									<br/> --%>
<%-- 								</c:if> --%>
<%-- 							</c:forEach> --%>
<!-- 							<table> -->
<%-- 								<c:forEach items="${listAtTypes}" var="bean" varStatus="status"> --%>
<%-- 									<c:if test="${(status.index)%5==0}"> --%>
<!-- 										<tr> -->
<%-- 									</c:if> --%>
<!-- 									<td class="tdRight" style="border: 0"> -->
<%-- 										<label><input type="checkbox" name="attachmentType" value='${bean.attachmentType }'>${bean.attachmentTypeName }</label> --%>
<!-- 									</td> -->
<%-- 									<c:if test="${(status.index+1)%5==0}"> --%>
<!-- 										</tr> -->
<%-- 									</c:if> --%>
<%-- 								</c:forEach> --%>
<!-- 							</table> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<td class="tdLeft">收款单位</td>
						<td class="tdRight">
							<input type="hidden" id="providerCode"  name="providerCode" value="${payInfo.providerCode}" class="base-input-text"/>
							<input type="hidden" id="providerType"  name="providerType" value="${payInfo.providerType}" class="base-input-text"/>
<%-- 							<input type="text"  id="providerName" name="providerName" value="${payInfo.providerName}" class="base-input-text" readonly="readonly" valid errorMsg="请输入收款单位"/> --%>
<!-- 							<a href="#" onclick="providerQuery();" style="border: 0px;" title="供应商查询"> -->
<%-- 								<img height="100%" border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/> --%>
<!-- 							</a> -->
								${payInfo.providerName}
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
<%-- 							<input type="text" id="provActNo" name="provActNo"  class="base-input-text" value="${payInfo.provActNo}" readonly="readonly" valid errorMsg="请输入收款帐号"/> --%>
								${payInfo.provActNo}
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payInfo.provActCurr}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">开户行信息</td>
						<td class="tdRight">
<%-- 							<input type="text" id="bankName" name="bankName"  class="base-input-text" value="${payInfo.bankName}" readonly="readonly" valid errorMsg="请输入银行名称"/> --%>
								${payInfo.bankInfo}
						</td>
						<td class="tdLeft">发票金额</td>
						<td class="tdRight">
<%-- 							<input type="text" id="invoiceAmt" name="invoiceAmt" maxlength="18" value="${payInfo.invoiceAmt}"   class="base-input-text" valid errorMsg="请输入发票金额"/> --%>
								${payInfo.invoiceAmt}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">预付款核销金额</td>
						<td class="tdRight">
<%-- 							<input type="text" id="advanceCancelAmt" name="advanceCancelAmt" onchange="validateCancelAmt();" value="${payInfo.advanceCancelAmt}" maxlength="18"  class="base-input-text" valid errorMsg="请输入预付款核销总金额"/> --%>
								${payInfo.advanceCancelAmt}
						</td>
						<td class="tdLeft">付款金额</td>
						<td class="tdRight">
<%-- 							<input type="text" id="payAmt1"  maxlength="18" value="${payInfo.payAmt}" class="base-input-text" valid errorMsg="请输入付款金额" onkeyup="setTwoValue(this,'payAmt2','请输入付款金额');"/> --%>
							${payInfo.payAmt}
							<input type="hidden"  name="payAmtBefore" maxlength="18" value="${payInfo.payAmt}" class="base-input-text" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
<%-- 							<input type="text" id="suspenseAmt1" value="${payInfo.suspenseAmt}"  maxlength="18"   class="base-input-text"  onkeyup="setTwoValue(this,'suspenseAmt2','请输入暂收金额');"  onblur="showInfo(this);" valid errorMsg="请输入暂收金额" /> --%>
								${payInfo.suspenseAmt}
							<input type="hidden"  name="suspenseAmtBefore" value="${payInfo.suspenseAmt}"  maxlength="18"  class="base-input-text"/>
						</td>
						<td class="tdLeft">付款方式</td>
						<td class="tdRight">
<!-- 							<div class="ui-widget"> -->
<!-- 								<select id="payMode" name="payMode" valid errorMsg="请选择支付方式。"  class="erp_cascade_select" > -->
<!-- 									<option value="">-请选择-</option> -->
<%-- 									<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"  --%>
<%-- 									 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${payInfo.payMode}"/> --%>
<!-- 								</select> -->
<!-- 							</div> -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
<%-- 			<c:if test="${!empty payAdvanceCancelList}"> --%>
<!-- 		<tr> -->
<!-- 			<td></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td> -->
<!-- 				<table> -->
<!-- 					<tr class="collspan-control"> -->
<!-- 						<th colspan="5">预付款核销信息</th> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th width="25%">预付款批次</th> -->
<!-- 						<th width="20%">预付款金额(元)</th> -->
<!-- 						<th width="20%">已核销金额(元)</th> -->
<!-- 						<th width="20%">本次核销金额(元)</th> -->
<!-- 						<th width="15%">操作</th> -->
<!-- 					</tr> -->
<%-- 					<c:forEach items="${payAdvanceCancelList}" var="bean"> --%>
<!-- 						<tr> -->
<%-- 							<td>${bean.advancePayId}<input type="hidden" id="advancePayIds" name="advancePayIds" value="${bean.advancePayId}"  class="base-input-text"/></td> --%>
<%-- 							<td>${bean.payAmt}<input type="hidden" id="remainCancel" name="remainCancel" value="${bean.payAmt-bean.cancelAmt}"  class="base-input-text"/></td> --%>
<%-- 							<td><fmt:formatNumber type="number" value="${bean.cancelAmtTotal-bean.cancelAmt}" minFractionDigits="2"/></td> --%>
<%-- 							<td><input id="cancelAmts" name="cancelAmts" value="${bean.cancelAmt}" maxlength="18" class="base-input-text"/></td> --%>
<!-- 							<td> -->
<!-- 								<div class="detail"> -->
<%-- 									<a href="#" onclick="advanceCancelDetail('${bean.advancePayId}');" title="明细"></a> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<!-- 				</table> -->
<!-- 				<table id="advPayMon"> -->
<!-- 					<tr class="collspan-control"> -->
<!-- 						<th colspan="9">合同采购设备(预付款)</th> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th width="9%">核算码</th> -->
<!-- 						<th width="9%">项目</th> -->
<!-- 						<th width="16%">物料类型</th> -->
<!-- 						<th width="10%">设备型号</th> -->
<!-- 						<th width="10%">总金额(元)</th> -->
<!-- 						<th width="11%">已付金额(元)</th> -->
<!-- 						<th width="11%">冻结金额(元)</th> -->
<!-- 						<th width="13%">发票分配金额(元)</th> -->
<!-- 						<th width="11%">发票行说明</th> -->
<!-- 					</tr> -->
<%-- 					<c:forEach items="${payAdvDevices}" var="bean"> --%>
<!-- 						<tr> -->
<%-- 							<td>${bean.cglCode } --%>
<%-- 								<input type="hidden" id="advSubIds" name="advSubIds" value="${bean.subId}" /> --%>
<!-- 							</td> -->
<%-- 							<td>${bean.projName }</td> --%>
<%-- 							<td>${bean.matrName }</td> --%>
<%-- 							<td>${bean.deviceModelName }</td> --%>
<%-- 							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td> --%>
<%-- 							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td> --%>
<%-- 							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td> --%>
<!-- 							<td> -->
<%-- 								<input type="hidden" id="freezeAmtAdvBefores" name="freezeAmtAdvBefores" value="${bean.subInvoiceAmt }" readonly="readonly"  class="base-input-text"/> --%>
<%-- 								<c:if test="${bean.execAmt == bean.payedAmt }"> --%>
<!-- 									<input id="advancePayInvAmts" name="advancePayInvAmts" value="0" readonly="readonly"  class="base-input-text" style="width: 100px;"/> -->
<%-- 								</c:if> --%>
<%-- 								<c:if test="${bean.execAmt != bean.payedAmt }"> --%>
<%-- 									<input id="advancePayInvAmts" name="advancePayInvAmts" maxlength="18" value="${bean.subInvoiceAmt }"  class="base-input-text" style="width: 100px;" onchange="checkAdvPayIvrowMemos(this);" valid errorMsg="请填写付款发票分配金额"/> --%>
<%-- 								</c:if> --%>
<!-- 							</td> -->
<%-- 							<td><input id="advancePayIvrowMemos" maxlength="250" name="advancePayIvrowMemos" class="base-input-text" style="width: 100px;" value="${bean.ivrowMemo }"/></td> --%>
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<!-- 				</table> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<%-- 		</c:if> --%>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">正常付款信息</th>
					</tr>
					<tr>
						<td class="tdLeft">付款金额</td>
						<td class="tdRight">
<%-- 							<input type="text" id="payAmt2" value="${payInfo.payAmt}" name="payAmt" maxlength="18" class="base-input-text" valid errorMsg="请输入付款金额" onkeyup="setTwoValue(this,'payAmt1','请输入付款金额');"/> --%>
								${payInfo.payAmt}
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
<%-- 							<input type="text" readonly="readonly" id="payDate" onchange="changeDate(this);" value="${payInfo.payDate}" name="payDate" class="base-input-text" valid errorMsg="请选择付款日期" readonly="readonly"/> --%>
							${payInfo.payDate}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
<%-- 							<input type="text" id="suspenseAmt2" value="${payInfo.suspenseAmt}" name="suspenseAmt" value="0" maxlength="18" class="base-input-text"  onkeyup="setTwoValue(this,'suspenseAmt1','请输入暂收金额');" onblur="showInfo(this);" valid errorMsg="请输入暂收金额" /> --%>
							${payInfo.suspenseAmt}
						</td>
						<td class="tdLeft"><div id="sdP">暂收付款日期</div></td>
						<td class="tdRight">
<%-- 							<div id="sdvP" style="display: none;"><input type="text" id="suspenseDate" name="suspenseDate" value="${payInfo.suspenseDate}"  class="base-input-text"  readonly="readonly"/></div> --%>
							${payInfo.suspenseDate}
						</td>
					</tr>
<!-- 					<tr style="display: none;" id="suspenseTr"> -->
<!-- 						<td class="tdLeft">暂收项目</td> -->
<!-- 						<td class="tdRight"> -->
<%-- <%-- 							<input type="text" id="suspenseName"  value="${payInfo.suspenseName}" name="suspenseName" maxlength="80" class="base-input-text" /> --%>
<%-- 							${payInfo.suspenseName} --%>
<!-- 						</td> -->
<!-- 						<td class="tdLeft">预处理时间（月份）</td> -->
<!-- 						<td class="tdRight"> -->
<%-- <%-- 							<input type="text" id="suspensePeriod"  value="${payInfo.suspensePeriod}" name="suspensePeriod" class="base-input-text" /> --%>
<%-- 							${payInfo.suspensePeriod} --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr id="suspenseReasonTr" style="display: none;"> -->
<!-- 						<td class="tdLeft">挂帐原因<br/>(<span id='memoSpan2'>0/2000</span>)</td> -->
<!-- 						<td class="tdRight" colspan="3"> -->
<%-- 							<textarea id="suspenseReason" cols="60" rows="5"  name="suspenseReason" onkeyup="$_showWarnWhenOverLen1(this,2000,'memoSpan2')" class="base-textArea">${payInfo.suspenseReason}</textArea> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3">
<%-- 							<textarea id="invoiceMemo" readonly="readonly" cols="60" rows="5"  name="invoiceMemo" class="base-textArea">${payInfo.invoiceMemo}</textArea> --%>
							${payInfo.invoiceMemo}
						</td>
					</tr>
				</table>
				<tr><td></td></tr>
				<tr>
				<td>
				<table id="payMon">
					<tr class="collspan-control">
						<th colspan="9">合同采购设备(正常付款)</th>
					</tr>
					<tr>
						<th width="9%">核算码</th>
						<th width="9%">项目</th>
						<th width="16%">物料类型</th>
						<th width="10%">设备型号</th>
						<th width="10%">总金额(元)</th>
						<th width="11%">已付金额(元)</th>
						<th width="11%">冻结金额(元)</th>
						<th width="13%">发票分配金额(元)</th>
						<th width="11%">发票行说明</th>
					</tr>
					<c:if test="${! empty payDevices }">
					<c:forEach items="${payDevices}" var="bean">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');">
							<td>${bean.cglCode }
								<input type="hidden" id="subIds" name="subIds" value="${bean.subId}" />
							</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.deviceModelName }</td>
							<td><fmt:formatNumber type="number" value="${bean.execAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.payedAmt}" minFractionDigits="2"/></td>
							<td><fmt:formatNumber type="number" value="${bean.freezeAmt}" minFractionDigits="2"/></td>
							<td>
								<input type="hidden" id="freezeAmtBefores" name="freezeAmtBefores" value="${bean.subInvoiceAmt }" readonly="readonly"  class="base-input-text"/>
								<input type="hidden" id="remainDeviceAmts" name="remainDeviceAmts" value="${bean.execAmt - bean.payedAmt-bean.freezeAmt}"/>
								<c:if test="${bean.execAmt == bean.payedAmt }">
									<input id="payInvAmts" name="payInvAmts" value="0" readonly="readonly" style="width: 100px;" class="base-input-text"/>
								</c:if>
								<c:if test="${bean.execAmt != bean.payedAmt }">
									<input id="payInvAmts" name="payInvAmts" value="${bean.subInvoiceAmt }" maxlength="18" style="width: 100px;"  class="base-input-text" onchange="checkPayIvrowMemos(this);"/>
								</c:if>
							</td>
							<td><input id="payIvrowMemos" name="payIvrowMemos" class="base-input-text" style="width: 100px;" maxlength="250" value="${bean.ivrowMemo }" /></td>
						</tr>
					</c:forEach>
					</c:if>
				<c:if test="${empty payDevices }">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息</span></td>
			</tr>
		</c:if>
		</table>
		</td>
		</tr>
		<tr>
			<td>
				<c:if test="${!empty payInfo.id}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
<%-- 				<p:button funcId="03030402" value="提交"/> --%>
				<input type="button" value="提交" onclick="">
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>