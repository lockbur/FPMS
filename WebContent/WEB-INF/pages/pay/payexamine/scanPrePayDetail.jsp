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
function  pageInit(){
	App.jqueryAutocomplete();
	$("#payMode").combobox();
	//付款确认
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
	<table>
		<tr>
			<td>
				<table>
					<tr>
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
						<td class="tdLeft">合同总金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.cntAllAmt}" minFractionDigits="2"/><br/>
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
					<tr>
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
							${payInfo.bankName }
						</td>
						<td class="tdLeft">币别</td>
						<td class="tdRight">
							${payInfo.provActCurr }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">发票金额</td>
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
						<td class="tdLeft">发票说明</td>
						<td class="tdRight">
							${payInfo.invoiceMemo }
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
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
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,200,'authdealCommentSpan')" id="auditMemo" name="auditMemo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>