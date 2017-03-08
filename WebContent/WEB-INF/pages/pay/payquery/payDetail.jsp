<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>付款信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
function  pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
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
//预付款核销明细查询
function advanceCancelDetail(advancePayId)
{
	<%-- var form=$("#payDetailForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payquery/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030704&advancePayId="+advancePayId;
	App.submit(form);	 --%>
	
	var url ="<%=request.getContextPath()%>/pay/payquery/queryPayAdvCancelDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030704&advancePayId="+advancePayId;
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
//暂收与正常付款的相互转换
function change(flag,batchNo,seqNo,payId,payAmt,payCancelState){
	var url = "<%=request.getContextPath()%>/pay/payquery/change.do?<%=WebConsts.FUNC_ID_KEY%>=03030705";
	url += "&flag="+flag;
	url += "&batchNo="+batchNo;
	url += "&seqNo="+seqNo;
	url += "&payId="+payId;
	url += "&payAmt="+payAmt;
	url += "&payCancelState="+payCancelState;

	$("#auditDiv").dialog({
		title:'正常付款转暂收',
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
				$('#auditForm').attr('action', url);
				$('#auditForm').submit();
			},
			"取消": function() {
// 				$("#dataFlagPayDiv").find("input").val('');
// 				$("#dataFlagPayDiv").find("input").removeAttr("style");
				$( this ).dialog( "close" );
			}
		}
	});
}
//付款流水的明细
function queryPayLogDetail(batchNo,seqNo){
	var url ="<%=request.getContextPath()%>/pay/payquery/queryPayLogDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03030707&batchNo="+batchNo+"&seqNo="+seqNo;
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
		    id:"dialogPayLogInfoDetail",
			close: function(){
			}
		}
	);
}

function printFace(payId,cntNum,isPrePay,isOrder){
	var url = "<%=request.getContextPath()%>/pay/payquery/print.do?<%=WebConsts.FUNC_ID_KEY%>=03030708&payId="+payId+"&cntNum="+cntNum+"&isPrePay="+isPrePay+"&isOrder="+isOrder;
	$.dialog.open(
			url,
			{
				width: "70%",
				height: "75%",
				lock: true,
			    fixed: true,
			    title:"付款打印封面",
			    id:"dialogCutPage"
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
<p:authFunc funcArray="03030703"/>
<form action="" id="print" method="post"></form>
	<jsp:include page="payDetailCommon.jsp"></jsp:include>			
	<br>
	<div style="text-align:center;" >
	<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
					<c:if test="${!empty payInfo.id}">
						<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
					</c:if>
			<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
			<c:if test="${payInfo.dataFlag == 'C0' or payInfo.dataFlag == 'C1'}">
			<input type="button" value="打印封面" onclick="printFace('${payInfo.payId}','${payInfo.cntNum}','${payInfo.isPrePay}','${payInfo.isOrder}');">
			</c:if>
			<input type="button" value="查询付款明细" onclick="queryPay22Detail('${payInfo.invoiceId}')">
			<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	

<!-- 审批弹出框 -->
<div id="auditDiv" style="display: none;">
	<form action="" method="post" id="auditForm">
		<table width="98%" style="width: 98%;">
			<%-- <c:if test="${flag == false}"> --%>
				<tr id="dataFlagPayTr">
					<td class="tdLeft">
						付款状态
					</td>
					<td class="tdLeft">
					 	<div class="ui-widget" id="dataFlagPayDiv">
							<select id="dataFlagPay" name="dataFlagPay" valid errorMsg="请选择付款状态。"  class="erp_cascade_select" >
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='DATA_FLAG_PAY'" selectedValue="${payInfo.dataFlagPay}"/>
							</select>
						</div>
					</td>
				</tr>
			<%-- </c:if> --%>
			<!-- <tr>
				<td colspan="2">确认修改吗？</td>
			</tr> -->
		</table>
	</form>
</div>	
<%-- <div id="auditDiv2" style="display: none;">
	<form action="" method="post" id="auditForm2">
		<table width="98%">
				<tr id="dataFlagPayTr2">
					<td class="tdLeft">
						付款状态
					</td>
					<td class="tdLeft">
					 	<div class="ui-widget" id="dataFlagPayDiv2">
							<select id="dataFlagPay" name="dataFlagPay" valid errorMsg="请选择付款状态。"  class="erp_cascade_select" >
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${payInfo.payMode}"/>
							</select>
						</div>
					</td>
				</tr>
			<tr>
				<td colspan="3">确认修改吗？</td>
			</tr>
		</table>
	</form>
</div>	 --%>
</body>
</html>