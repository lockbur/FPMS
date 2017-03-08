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

function  pageInit(){
	App.jqueryAutocomplete();
	$("#payMode").combobox();
	//复核
	$("#auditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"通过": function() {	
				if(!App.valid("#auditForm")){return;}
				var form = $("#auditForm");
			 	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/scanAgree.do?<%=WebConsts.FUNC_ID_KEY%>=0303050805');
				App.submit(form);
			},
			"退回":function(){
				if(!App.valid("#auditForm")){return;}
				var form = $("#auditForm");
			 	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/scanBack.do?<%=WebConsts.FUNC_ID_KEY%>=0303050806');
				App.submit(form);
			},
				
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

//预付款核销明细查询
function advanceCancelDetail(advancePayId)
{
	var url ="<%=request.getContextPath()%>/pay/payexamine/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0303050901&advancePayId="+advancePayId;
// 	App.submitShowProgress();
// 	window.showModalDialog(url, null, "dialogHeight=500px;dialogWidth=900px;center=yes;status=no;");
// 	App.submitFinish();
	$.dialog.open(
		url,
		{
			width: "60%",
			height: "80%",
			lock: true,
		    fixed: true,
		    title:"预付款详情",
		    id:"dialogAdvCancelInfoDetail",
			close: function(){
			}		
		}
	)
}
//弹窗进行审批意见
function toAudit(button,url){
	$("#auditDiv").dialog({
		title:'扫描复核',
		autoOpen: true,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				//校验
				if(!App.valid("#auditForm")){return;}
				//判断是否通过
				if($("#isAgree").val()=='1'){
					$('#auditForm').attr('action', '<%=request.getContextPath()%>/pay/payexamine/scanAgree.do?<%=WebConsts.FUNC_ID_KEY%>=0303050805');
					$('#auditForm').submit();
				}
				if($("#isAgree").val()=='2'){
					$('#auditForm').attr('action', '<%=request.getContextPath()%>/pay/payexamine/scanBack.do?<%=WebConsts.FUNC_ID_KEY%>=0303050806');
					$('#auditForm').submit();
				}
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		

		}
	});
}
function toSelectResult(s){
	if(s=='2'){
		//退回
		$("#auditForm #payModeTr").css("display","none");
		$("#auditForm #auditMemo").val("");
		$("#auditForm #isAgree").val("2");
		
	}else{
		//同意
		$("#auditForm #payModeTr").css("display","");
		$("#auditForm #auditMemo").val("");
		$("#auditForm #isAgree").val("1");
	}
}
</script>
</head>
<body>
<p:authFunc funcArray="03030508"/>
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
				<c:if test="${!empty payDeviceList}">
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
						<th width="120%">发票行<br/>说明</th>
					</tr>
					<c:forEach items="${payDeviceList}" var="bean">
						<tr>
							<td>${bean.cglCode }</td>
							<td>${bean.projName }</td>
							<td>${bean.matrName }</td>
							<td>${bean.feeDeptName }</td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt }" minFractionDigits="2"/>元</td>
							<td><fmt:formatNumber type="number" value="${bean.addTaxAmt }" minFractionDigits="2"/>元</td>
							<td><fmt:formatNumber type="number" value="${bean.subInvoiceAmt+bean.addTaxAmt}" minFractionDigits="2"/>元</td>
							<td>${bean.ivrowMemo }</td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
			</td>
		</tr>
	</table>
</form>
	<br>
		<div style="text-align:center;" >
				<c:if test="${payScanBean.dataFlag != '03'}">		
				  <input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payScanBean.payId}','${payScanBean.icmsPkuuid}')"/>
				</c:if>
				<%-- 
				<c:if test="${payScanBean.dataFlag eq '02'}">	
				  <input type="button" value="扫描复核" onclick="toAudit()">
				</c:if>
				--%>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
<!-- 审批弹出框 -->
<div id="auditDiv">
	<form action="" method="post" id="auditForm">
		<input type="hidden" name="dataFlag" value="${payInfo.dataFlag}">
		<input type="hidden" name="payId" value="${payInfo.payId}">
		<input type="hidden" name="isPrePay" value="${payInfo.isPrePay}"/>
		<input type="hidden" name="batchNo" value="${scanBatchNo}"/>
		<input type="hidden" name="icmsPkuuid" value="${payScanBean.icmsPkuuids}"/>		
		<table width="98%">
		   <tr>
				<td align="left" colspan="2">
					<input type="hidden" id="isAgree" name="isAgree" value="1"/>
					<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('1');" class="check-label">同意</label><input type="radio" id="authdealFlag1" name="dealFlag" value="1" checked="checked">
						<label for="authdealFlag2" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('2')" >退回</label><input type="radio" id="authdealFlag2" name="dealFlag" value="0">
					</div>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<br>转发意见(<span id="authdealCommentSpan">0/200</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,200,'authdealCommentSpan');" id="auditMemo" name="auditMemo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>