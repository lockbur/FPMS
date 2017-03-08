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
<title>订单详情</title>
<script type="text/javascript">
//订单复核
function orderCheck(){
	<%-- var form=$("#orderCheckForm")[0];
	form.action="<%=request.getContextPath()%>/pay/paymodify/modify.do?<%=WebConsts.FUNC_ID_KEY%>=03030401&orderId="+orderId;
	App.submit(form); --%>
	//$('#orderCheckForm').attr("action",url);
	//
	//App.submitForm(button, url);
	$("#auditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				//校验
				if(!App.valid("#orderCheckForm")){return;}
				var form=$("#orderCheckForm")[0];
				form.action="<%=request.getContextPath()%>/pay/ordercheck/orderCheck.do?<%=WebConsts.FUNC_ID_KEY%>=03070402";
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#auditDiv").dialog( "option", "title", "订单复核" ).dialog( "open" );
}
function toSelectResult(s){
	if(s=='2'){
		//退回
		$("#orderCheckForm #auditMemo").val("");
		$("#orderCheckForm #isAgree").val("2");
		
	}else{
		//同意
		$("#orderCheckForm #auditMemo").val("");
		$("#orderCheckForm #isAgree").val("1");
	}
}
</script>
</head>
<body>
<p:authFunc funcArray="03070402"/>
<form action="" method="post" id="">
	<table>
		<tr>
			<th colspan="4">订单号:${orderInfo.orderId}</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">合同号</td>
			<td class="tdRight" width="30%"><c:out value="${orderInfo.cntNum}"></c:out></td>
			<td class="tdLeft" width="20%">采集编号</td>
			<td class="tdRight" width="30%">
				<c:out value="${orderInfo.stockNum}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >采评会批复金额</td>
			<td class="tdRight"><c:out value="${orderInfo.stockAmt}"></c:out>元</td>
			<td class="tdLeft">采评会批复供应商</td>
			<td class="tdRight">
				<c:out value="${orderInfo.stockProv}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >部门编码</td>
			<td class="tdRight"><c:out value="${orderInfo.deptId}"></c:out></td>
			<td class="tdLeft">采购方式</td>
			<td class="tdRight">
				<c:out value="${orderInfo.procurementRoute}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >合同或协议签署日期</td>
			<td class="tdRight"><c:out value="${orderInfo.signDate}"></c:out></td>
			<td class="tdLeft">订单说明</td>
			<td class="tdRight">
				<c:out value="${orderInfo.orderMemo}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >合同约定期限（开始日期）期</td>
			<td class="tdRight"><c:out value="${orderInfo.startDate}"></c:out></td>
			<td class="tdLeft">合同约定期限（截止日期）</td>
			<td class="tdRight">
				<c:out value="${orderInfo.endDate}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >创建责任中心</td>
			<td class="tdRight"><c:out value="${orderInfo.instDutyCode}"></c:out></td>
			<td class="tdLeft">创建用户</td>
			<td class="tdRight">
				<c:out value="${orderInfo.instUser}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" > 创建日期</td>
			<td class="tdRight"><c:out value="${orderInfo.instDate}"></c:out></td>
			<td class="tdLeft">创建时间</td>
			<td class="tdRight">
				<c:out value="${orderInfo.instTime}"></c:out>
			</td>
		</tr>
			<c:if test="${!empty orderInfo.poNumber}">
				<tr>
					<td class="tdLeft">PO单号</td>
					<td class="tdRight" colspan="3">
						<c:out value="${orderInfo.poNumber}"></c:out>
					</td>
				</tr>
			</c:if>
		<tr>
			<td class="tdLeft" > 付款条件说明</td>
			<td class="tdRight" colspan="3"><textarea  class="base-textArea" readonly="readonly">${orderInfo.payTermMemo}</textarea></td>
		</tr>
	</table>
	<br>
	<div style="text-align:center;" >
				<%-- <p:button funcId="03070402" value="订单复核" onclick="orderCheck"/> --%>
				<input type="button" value="订单复核" onclick="orderCheck();">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</form>
<div id="auditDiv" style="display: none;">
	<form action="" method="post" id="orderCheckForm">
		<p:token/>
		<input type="hidden" name="orderId" value="${orderInfo.orderId}">
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
					<br>转发意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="auditMemo" name="auditMemo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>