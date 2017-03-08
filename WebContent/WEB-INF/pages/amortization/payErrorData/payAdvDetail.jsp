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
<title>预付款数据明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
function pageInit() {
	//初始化附件类型的被选择的值
	var attTypeStr = "${payInfo.attachmentType}";
	var attTypeArr = attTypeStr.split(",");//逗号切分成数组
	for(var i=0;i<attTypeArr.length;i++){
		$("#payAdvModifyForm").find("input[name='attachmentType']").each(function(){
			if($(this).val() == attTypeArr[i]){
				$(this).attr('checked',true);
				return ;
			}
		});
	}
	
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
}

//查询付款审批历史记录(弹窗显示)
function queryHis(payId){
	$.dialog.open(
			'<%=request.getContextPath()%>/pay/payAdd/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030321&payId='+payId,
			{
				width: "70%",
				height: "90%",
				lock: true,
			    fixed: true,
			    cancelVal:null,
			    title:"查看流转日志",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		);
}
</script>
</head>

<body>
<form action="" method="post" id="payAdvModifyForm">
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
								<input type="hidden" id="freezeTotalAmt" name="freezeTotalAmt" value="${constractInfo.freezeTotalAmt-payInfo.invoiceAmt}"/>
								<fmt:formatNumber type="number" value="${constractInfo.freezeTotalAmt-payInfo.invoiceAmt}" minFractionDigits="2"/>
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
<%-- 							<input type="text" id="attachmentNum" name="attachmentNum" value="${payInfo.attachmentNum}" maxlength="10"  class="base-input-text" valid errorMsg="请输入附件张数"/> --%>
							${payInfo.attachmentNum}
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="tdLeft">附件类型</td> -->
<!-- 						<td class="tdRight" colspan="3">  -->
<!-- 							<table> -->
<%-- 								<c:forEach items="${listAtTypes}" var="bean" varStatus="status"> --%>
<%-- 									<c:if test="${(status.index)%5==0}"> --%>
<!-- 										<tr> -->
<%-- 									</c:if> --%>
<!-- 									<td class="tdRight" style="border: 0"> -->
<%-- 										<label><input type="checkbox" name="attachmentType" value='${bean.attachmentType }'>${bean.attachmentTypeName }</label> --%>
<!-- 									</td> -->
<%-- 									${bean.attachmentTypeName} --%>
<%-- 									<c:if test=""></c:if> --%>
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
							<input type="hidden" id="providerType"  name="providerType" value="${payInfo.providerType}" class="base-input-text"/>
							<input type="hidden" id="providerCode"  name="providerCode" value="${payInfo.providerCode}" class="base-input-text"/>
<%-- 							<input type="text"  id="providerName" name="providerName" value="${payInfo.providerName}" class="base-input-text" valid errorMsg="请输入收款单位" readonly="readonly"/> --%>
							${payInfo.providerName}
<!-- 							<a href="#" onclick="providerQuery();" style="border: 0px;" title="供应商查询"> -->
<%-- 								<img height="100%" border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/> --%>
<!-- 							</a> -->
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
<%-- 							<input type="text" id="provActNo" name="provActNo"  class="base-input-text" value="${payInfo.provActNo}" readonly="readonly" valid errorMsg="请输入收款帐号"/> --%>
							${ payInfo.provActNo}
							<input type="hidden" id="provActCurr" name="provActCurr"  class="base-input-text" value="${payInfo.provActCurr}"/>
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
<%-- 							<input type="text" id="invoiceAmt" name="invoiceAmt" value="${payInfo.invoiceAmt}" maxlength="18" class="base-input-text" valid errorMsg="请输入发票金额" onchange="validaInvoiceAmt();"/> --%>
							${payInfo.invoiceAmt}
							<input type="hidden" id="freezeAmt" name="freezeAmt" value="${payInfo.invoiceAmt}" maxlength="18" class="base-input-text" />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
<%-- 							<input type="text" readonly="readonly" id="payDate" name="payDate" value="${payInfo.payDate}" class="base-input-text" valid errorMsg="请选择付款日期"/> --%>
							${payInfo.payDate}
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
							${payInfo.payMode}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight" colspan="3">
<%-- 							<textarea id="invoiceMemo" readonly="readonly" cols="60" rows="5"  name="invoiceMemo" class="base-textArea">${payInfo.invoiceMemo}</textArea> --%>
							${payInfo.invoiceMemo}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${!empty payInfo.id}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>