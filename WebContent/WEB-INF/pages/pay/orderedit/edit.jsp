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
<title>订单修改</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	$("#deptId").combobox();
	$("#signDate,#startDate,#endDate").datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
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
				if(!App.valid("#orderBackForm")){return;}
				var operMemo=$("#orderBackForm #operMemo").val();
				$("#editForm #operMemo").val(operMemo);
				//判断是否通过
				$('#editForm').attr('action', '<%=request.getContextPath()%>/pay/orderedit/back.do?<%=WebConsts.FUNC_ID_KEY%>=03070303');
				$('#editForm').submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function resetAll(){
	$("#orderEditTable input").val("");
	$("#orderEditTable textarea").val("");
	$("#orderEditTable select").val("");
}
//弹窗进行审批意见
function toAudit(button,url){
	$("#auditDiv").dialog( "option", "title", "订单退回" ).dialog( "open" );
}
/* function toSelectResult(s){
	if(s=='2'){
		//退回
		$("#orderBackForm #operMemo").val("");
		$("#orderBackForm #isAgree").val("2");
		
	}else{
		//同意
		$("#orderBackForm #operMemo").val("");
		$("#orderBackForm #isAgree").val("1");
	}
} */
function doValidate(){
    if((startDate!=""&&endDate=="")||(startDate==""&&endDate!="")){
		App.notyError("请同时选择开始日期和结束日期!");
		return false;
	}
  //校验服务开始时间和服务结束时间 
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	if(startDate!=""&&endDate!=""){
		if(startDate>endDate){
			App.notyError("合同起始日期必须在合同終止日期之前!");
			return false;
		}
	}
	return true;
}
//查询付款审批历史记录(弹窗显示)
function queryHis(orderId){
	var url = "<%=request.getContextPath()%>/pay/orderstart/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030105&orderId="+orderId;
	$.dialog.open(
			url,
			{
				width: "50%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"订单历史记录",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}
</script>
</head>
<body>
<p:authFunc funcArray="03070302,,03030104,03030105"/>
<form action="" method="post" id="editForm">
<input type="hidden" name="orderId" value="${orderInfo.orderId}"/>
<input type="hidden" name="operMemo"  id="operMemo"/>
<input type="hidden" name="cntNum"  id="cntNum" value="${orderInfo.cntNum}"/>
	<p:token/>
	<table id="orderEditTable">
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">订单号:${orderInfo.orderId}</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">采集编号</td>
						<td class="tdRight" width="30%">
							${orderInfo.stockNum}
						</td>
						<td class="tdLeft">采评会批复金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${orderInfo.stockAmt}" minFractionDigits="2"/>元 
						</td>
					</tr>
					<tr>
						<td class="tdLeft">采评会批复供应商</td>
						<td class="tdRight">
							${orderInfo.stockProv}
						</td>
						<td class="tdLeft">合同或协议编号</td>
						<td class="tdRight">
								${orderInfo.cntNum}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同或协议签署日期</td>
						<td class="tdRight">
							${orderInfo.signDate}
						</td>
						<td class="tdLeft">付款条件说明</td>
						<td class="tdRight">
								${orderInfo.payTermMemo}
						</td>
					</tr>
					<c:if test="${orderInfo.startDate!=null}">
						<tr>
						<td class="tdLeft">合同起始日期</td>
						<td class="tdRight">
							${orderInfo.startDate}
						</td>
						<td class="tdLeft">合同終止日期</td>
						<td class="tdRight">
								${orderInfo.endDate}
						</td>
					</tr>
					</c:if>
					<c:if test="${orderInfo.dataFlag == '11'}">
					<tr>
						<td class="tdLeft">错误原因</td>
						<td class="tdRight" colspan="3">
							${orderInfo.errorReason}
						</td>
					</tr>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		 <tr>
	         <td>
	         	<table class="tableList">
	         		<tr class="collspan-control">
						<th colspan="12">设备列表</th>
					</tr>
					<tr>
						<th>项目</th>
						<th>费用承担部门</th>
						<th>物料编码</th>
						<th>监控指标</th>
						<th>设备型号</th>
						<th>专项</th>
						<th>参考</th>
						<th>数量</th>
						<th>税码</th>
						<th>税额</th>
						<th>单价<br>(元)</th>
						<th>金额<br>(元)</th>
					</tr>
					<c:forEach items="${devList}" var="sedList">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
						<td class="tdc" ><c:out value="${sedList.projName}"></c:out></td>
						<td ><c:out value="${sedList.dutyName}"></c:out></td>
						<td class="tdc" ><c:out value="${sedList.matrCode}"/>-<c:out value="${sedList.matrName}"/></td>
						<td class="tdc"><c:out value="${sedList.montCode}"/>-<c:out value="${sedList.montName}"/>s</td>
						<td ><c:out value="${sedList.deviceModelName}"/></td>
						<td class="tdc" ><c:out value="${sedList.specialName}"/></td>
						<td><c:out value="${sedList.referenceName}"/></td>
						<td><c:out value="${sedList.execNum}"/></td>
						<td><c:out value="${sedList.taxCode}"/></td>
						<td>
							<fmt:formatNumber type="number" value="${sedList.cntTrAmt}" minFractionDigits="2"/>
						</td>
						<td>
							<fmt:formatNumber type="number" pattern="#,##0.##########" value="${sedList.execPrice}" minFractionDigits="2"/>
						</td>
						<td>
							<fmt:formatNumber type="number" value="${sedList.execAmt}" minFractionDigits="2"/>
						</td>
					</tr>
				</c:forEach>
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
						<th colspan="4">待修改信息</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">部门编码</td>
						<td class="tdRight" width="30%">
							<div class="ui-widget">
								<select id="deptId" name="deptId">
									<option value="0" <c:if test="${'0'==orderInfo.deptId}">selected="selected"</c:if> >0</option>
									<option value="${orderInfo.orderDutyCode}" <c:if test="${orderInfo.deptId==orderInfo.orderDutyCode}">selected="selected"</c:if> >${orderInfo.orderDutyCode}</option>
								</select>
							</div>
						</td>
						<td class="tdLeft">采购方式</td>
							<td class="tdRight">
								<div class="ui-widget">
									<select id="procurementRoute" name="procurementRoute"   class="erp_cascade_select">
										<option value="">-请选择-</option>
										<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
										 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PRUCHASE_WAY'" selectedValue="${orderInfo.procurementRoute }"/>
									</select>
								</div>	
			            </td>
					</tr>
					<c:if test="${orderInfo.startDate==null}">
					<tr>
						<td class="tdLeft">合同起始日期</td>
						<td class="tdRight">
							<input type="text" id="startDate" name="startDate"  readonly="readonly" class="base-input-text" >
						</td>
						<td class="tdLeft">合同終止日期</td>
						<td class="tdRight">
								<input type="text" id="endDate" name="endDate"  readonly="readonly" class="base-input-text" >
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="tdLeft">订单说明<br>(<span id="deptDescSpan">0/250</span>)</td>
						<td class="tdRight" colspan="3">
							<textarea   class="base-textArea" id="orderMemo" name="orderMemo" rows='5' onkeyup="$_showWarnWhenOverLen1(this,250,'deptDescSpan')" >${orderInfo.orderMemo}</textarea>
						</td>
				   </tr>
	         	</table>
	         </td>
		</tr>
	</table>
	<br>
	<div style="text-align:center;" >
		<input type="button" value="退回" onclick="toAudit();"/>
		<input type="button" value="查询订单历史记录" onclick="queryHis('${orderInfo.orderId}');">
		<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</form>
<div id="auditDiv" style="display: none;">
	<form action="" method="post" id="orderBackForm">
		<input type="hidden" name="orderId" value="${orderInfo.orderId}">
		<input type="hidden" id="isAgree" name="isAgree" value="2"/>
		<table width="98%">
			<tr>
				<td align="left" colspan="2">
					<br>转发意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="operMemo" name="operMemo" rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>