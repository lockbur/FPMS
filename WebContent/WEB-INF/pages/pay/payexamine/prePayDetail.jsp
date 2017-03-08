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
	//$("#auditTable .ui-autocomplete-input").css("width","85%");
	//付款确认
	$("#payAddAuditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				//校验
				//if(!App.valid("#payAddForm")){return;}
				//先必须选择同意或者退回
				if($("#isAgree").val()==''||$("#isAgree").val==null){
					App.notyError("请先选择同意或者退回!");
					return false;
				}
				//判断是否通过
				if($("#isAgree").val()=='1'){
					$( "<div>确认通过?</div>" ).dialog({
						resizable: false,
						height:150,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
								$('#payAddForm').attr('action', '<%=request.getContextPath()%>/pay/payexamine/agree.do?<%=WebConsts.FUNC_ID_KEY%>=03030505');
								$('#payAddForm').submit();
							},
							"取消": function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
				}
				if($("#isAgree").val()=='2'){
					if(!App.valid("#payAddForm")){return;}
					$( "<div>确认退回?</div>" ).dialog({
						resizable: false,
						height:150,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
								$('#payAddForm').attr('action', '<%=request.getContextPath()%>/pay/payexamine/back.do?<%=WebConsts.FUNC_ID_KEY%>=03030506');
								$('#payAddForm').submit();
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
	$("#notPayAddAuditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"退回":function(){
				if(!App.valid("#notPayAddForm")){return;}
				var form = $("#notPayAddForm");
			 	form.attr('action', '<%=request.getContextPath()%>/pay/payexamine/back.do?<%=WebConsts.FUNC_ID_KEY%>=03030506');
				App.submit(form);
			},
				
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
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
//弹窗进行审批意见
function toAudit(button,url){
	//新增付款复核
	var payDataFlagValue="${payInfo.dataFlag}";
	if(payDataFlagValue=='B0'){
		$('#payAddForm').attr("action",url);
		$("#payAddAuditDiv").dialog( "option", "title", "付款复核" ).dialog( "open" );
	}
	else{
		$('#notPayAddForm').attr("action",url);
		$("#notPayAddAuditDiv").dialog( "option", "title", "付款复核" ).dialog( "open" );
	}
}
function toSelectResult(s){
	if(s==$("#isAgree").val()){
		return false;
	}
	if($("#isAgree").val()!=''&&$("#isAgree").val!=null){
		$("#payAddForm #auditMemo").val("");
	}
	if(s=='2'){
		//退回
		$("#payAddForm #payModeTr").css("display","none");
		$_showWarnWhenOverLen1(document.getElementById("auditMemo"),500,'authdealCommentSpan');
		$("#payAddForm #isAgree").val("2");
	}else{
		//同意
		$("#payAddForm #payModeTr").css("display","");
		$_showWarnWhenOverLen1(document.getElementById("auditMemo"),500,'authdealCommentSpan');
		$("#payAddForm #isAgree").val("1");
	}
}

</script>
</head>
<body>
<p:authFunc funcArray="03030503,03030504"/>
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
						<td class="tdLeft" width="20%">合同号</td>
						<td class="tdRight" width="30%">
							${payInfo.cntNum}
						</td>
						<td class="tdLeft" >进度</td>
						<td class="tdRight" >
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
					<tr class="collspan-control">
						<th colspan="4">
							付款单号：${payInfo.payId}
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">发票号</td>
						<td class="tdRight" width="30%">
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
	<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
				<c:if test="${!empty payInfo.id}">
					<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
				</c:if>
	<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
				<p:button funcId="03030504" value="付款复核" onclick="toAudit"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
<div id="payAddAuditDiv" >
	<form action="" method="post" id="payAddForm">
		<input type="hidden" name="dataFlag" value="${payInfo.dataFlag}">
		<input type="hidden" name="payId" value="${payInfo.payId}">
		<input type="hidden" name="isPrePay" value="${payInfo.isPrePay}"/>
		<input type="hidden" name="payModeName" value="${payInfo.payModeName}"/>
		<table width="98%" id="auditTable">
		   <tr>
				<td align="left" colspan="2">
					<input type="hidden" id="isAgree" name="isAgree" value=""/>
					<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('1');">同意</label><input type="radio" id="authdealFlag1" name="dealFlag" value="1" >
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
<!-- 			<tr id="payModeTr" style="display: none"> -->
<!-- 					<td class="tdLeft" style="width: 45%"> -->
<!-- 						支付方式 -->
<!-- 					</td> -->
<!-- 					<td class="tdRight" style="width: 55%"> -->
<!-- 					 	<div class="ui-widget" id="payModeDiv" > -->
<!-- 							<select id="payMode" name="payMode" valid errorMsg="请选择支付方式。"  class="erp_cascade_select"  > -->
<!-- 								<option value="">-请选择-</option> -->
<%-- 								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"  --%>
<%-- 								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${payInfo.payMode}"/> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<%-- 				</c:if> --%>
		</table>
	</form>
</div>	
<!-- 非付款新增复核弹出框 -->
<div id="notPayAddAuditDiv">
	<form action="" method="post" id="notPayAddForm">
		<input type="hidden" name="payId" value="${payInfo.payId}">
		<input type="hidden" name="isPrePay" value="${payInfo.isPrePay}"/>
		<input type="hidden" name="dataFlag" value="${payInfo.dataFlag}"/>
		<input type="hidden" name="payModeName" value="${payInfo.payModeName}"/>
		<table width="98%">
			<tr>
				<td align="left">
					<br>审批意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="auditMemo" name="auditMemo" rows="7" cols="45" valid errorMsg="请输入审批意见。"></textarea>
				</td>
			</tr>
			
		</table>
	</form>
</div>
</body>
</html>