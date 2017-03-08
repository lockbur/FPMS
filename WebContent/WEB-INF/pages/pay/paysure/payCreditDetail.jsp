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
<title>贷项通知单信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
function  pageInit(){
	App.jqueryAutocomplete();
	$("#payMode").combobox();
	//$("#auditTable .ui-autocomplete-input").css("width","85%");
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
			 	form.attr('action', '<%=request.getContextPath()%>/pay/paysure/agree.do?<%=WebConsts.FUNC_ID_KEY%>=03030604');
				App.submit(form);
			},
			"退回":function(){
				if(!App.valid("#auditForm")){return;}
				var form = $("#auditForm");
			 	form.attr('action', '<%=request.getContextPath()%>/pay/paysure/back.do?<%=WebConsts.FUNC_ID_KEY%>=03030605');
				App.submit(form);
			},
				
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
/* //审批
function toAudit(button, url) {
	$('#auditForm').attr("action",url);
	$("#auditDiv").dialog( "option", "title", "付款确认" ).dialog( "open" );
} */
//预付款核销明细查询
function advanceCancelDetail(advancePayId)
{
	<%-- var form=$("#payDetailForm")[0];
	form.action="<%=request.getContextPath()%>/pay/paysure/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030606&advancePayId="+advancePayId;
	App.submit(form);	 --%>
	var url ="<%=request.getContextPath()%>/pay/paysure/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030606&advancePayId="+advancePayId;
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
		title:'付款确认',
		autoOpen: true,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				//先必须选择同意或者退回
				if($("#isAgree").val()==''||$("#isAgree").val==null){
					App.notyError("请先选择同意或者退回!");
					return false;
				}
// 				//判断是否通过
// 				if($("#isAgree").val()=='1'){
<%-- 					$('#auditForm').attr('action', '<%=request.getContextPath()%>/pay/paysure/agree.do?<%=WebConsts.FUNC_ID_KEY%>=03030604'); --%>
// 					$('#auditForm').submit();
// 				}
// 				if($("#isAgree").val()=='2'){
// 					//校验
// 					if(!App.valid("#auditForm")){return;}
<%-- 					$('#auditForm').attr('action', '<%=request.getContextPath()%>/pay/paysure/back.do?<%=WebConsts.FUNC_ID_KEY%>=03030605'); --%>
// 					$('#auditForm').submit();
// 				}
				
				//判断是否通过
				if($("#isAgree").val()=='1'){
					$( "<div>确认通过?</div>" ).dialog({
						resizable: false,
						height:150,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
								$('#auditForm').attr('action', '<%=request.getContextPath()%>/pay/paysure/agree.do?<%=WebConsts.FUNC_ID_KEY%>=03030604');
			 					$('#auditForm').submit();
							},
							"取消": function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
				}
				if($("#isAgree").val()=='2'){
					//校验
					if(!App.valid("#auditForm")){return;}
					$( "<div>确认退回?</div>" ).dialog({
						resizable: false,
						height:150,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
								$('#auditForm').attr('action', '<%=request.getContextPath()%>/pay/paysure/back.do?<%=WebConsts.FUNC_ID_KEY%>=03030605');
			 					$('#auditForm').submit();
							},
							"取消": function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
				}
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		

		}
	});
}
function toSelectResult(s){
	if(s==$("#isAgree").val()){
		return false;
	}
	if($("#isAgree").val()!=''&&$("#isAgree").val!=null){
		$("#auditForm #auditMemo").val("");
	}
	if(s=='2'){
		//退回
		$("#auditForm #payModeTr").css("display","none");
		$("#auditForm #auditMemo").val("");
		$_showWarnWhenOverLen1(document.getElementById("auditMemo"),500,'authdealCommentSpan');
		$("#auditForm #isAgree").val("2");
		
	}else{
		//同意
		$("#auditForm #payModeTr").css("display","");
		$("#auditForm #auditMemo").val("");
		$_showWarnWhenOverLen1(document.getElementById("auditMemo"),500,'authdealCommentSpan');
		$("#auditForm #isAgree").val("1");
	}
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
<p:authFunc funcArray="03030603"/>
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
						<th width="120%">发票行<br/>说明</th>
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
	<input type="button" value="查看原蓝字发票扫描文件" onclick="viewPayScanImg('${invoiceBlue.payId}','${invoiceBlue.icmsPkuuid }');"/>
				<c:if test="${!empty payInfo.icmsPkuuid}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid }');"/>
				</c:if>
				<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
				<p:button funcId="03030603" value="付款确认" onclick="toAudit"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
<!-- 审批弹出框 -->
<div id="auditDiv">
	<form action="" method="post" id="auditForm">
		<input type="hidden" name="payId" value="${payInfo.payId}">
		<input type="hidden" name="isPrePay" value="${payInfo.isPrePay}"/>
		<input type="hidden" name="isFrozenBgt" value="${payInfo.isFrozenBgt}"/>
		<table width="98%" id="auditTable" >
		   <tr>
				<td align="left" colspan="2">
					<input type="hidden" id="isAgree" name="isAgree" value=""/>
					<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('1');" >同意</label><input type="radio" id="authdealFlag1" name="dealFlag" value="1" >
						<label for="authdealFlag2" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('2')" >退回</label><input type="radio" id="authdealFlag2" name="dealFlag" value="0">
					</div>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<br>转发意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="auditMemo" name="auditMemo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
<%-- 			<c:if test="${payInfo.advanceCancelAmt != payInfo.invoiceAmt}"> --%>
<!-- 			<tr id="payModeTr" style="display: none;"> -->
<!-- 					<td class="tdLeft" style="width:45%"> -->
<!-- 						支付方式 -->
<!-- 					</td> -->
<!-- 					<td class="tdLeft" style="width:55%;"> -->
<!-- 					 	<div class="ui-widget" id="payModeDiv" > -->
<!-- 							<select id="payMode" name="payMode" valid   class="erp_cascade_select" > -->
<!-- 								<option value="">-请选择-</option> -->
<%-- 								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"  --%>
<%-- 								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${payInfo.payMode}"/> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<%-- 			</c:if> --%>
		</table>
	</form>
</div>
</body>
</html>