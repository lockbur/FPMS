<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单确认</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
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
	$_showWarnWhenOverLen1(document.getElementById("orderMemo"),500,'deptDescSpan');
	$("#auditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				if($("#isAgree").val()==null||$("#isAgree").val()==''){
					App.notyError("请先选择通过或者退回!");
					return false;
				}
				
				//判断是否通过
				var operMemo=$("#orderSureForm #operMemo").val();
				
				if($("#isAgree").val()=='2' && (operMemo ==null||operMemo =='')){
					App.notyError("请填写退回原因");
					return false;
				}
				
				$("#reqtypeForm #operMemo").val(operMemo);
// 				if($("#isAgree").val()=='1'){
// 					if(!doValidate()){
// 						return false;
// 					}
<%-- 					$('#reqtypeForm').attr('action', '<%=request.getContextPath()%>/pay/orderstart/start.do?<%=WebConsts.FUNC_ID_KEY%>=03030103'); --%>
// 					$('#reqtypeForm').submit();
// 				}
// 				if($("#isAgree").val()=='2'){
// 					//校验
// 					if(!App.valid("#orderSureForm")){return;}
<%-- 					$('#reqtypeForm').attr('action', '<%=request.getContextPath()%>/pay/orderstart/sure.do?<%=WebConsts.FUNC_ID_KEY%>=03030104'); --%>
// 					$('#reqtypeForm').submit();
// 				}
				
				//判断是否通过
				if($("#isAgree").val()=='1'){
					if(!App.valid("#orderSureForm")){return;}
					$( "<div>确认通过?</div>" ).dialog({
						resizable: false,
						height:150,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
								$('#reqtypeForm').attr('action', '<%=request.getContextPath()%>/pay/orderstart/start.do?<%=WebConsts.FUNC_ID_KEY%>=03030103');
								$('#reqtypeForm').submit();
							},
							"取消": function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
				}
				if($("#isAgree").val()=='2'){
					//校验
					if(!App.valid("#orderSureForm")){return;}
					$( "<div>确认退回?</div>" ).dialog({
						resizable: false,
						height:150,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
								//校验
								if(!App.valid("#orderSureForm")){return;}
								$('#reqtypeForm').attr('action', '<%=request.getContextPath()%>/pay/orderstart/sure.do?<%=WebConsts.FUNC_ID_KEY%>=03030104');
								$('#reqtypeForm').submit();
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
//弹窗进行审批意见
function toAudit(button,url){
	if(!doValidate()){
		return false;
	}
	$("#auditDiv").dialog( "option", "title", "订单确认" ).dialog( "open" );
}
function toSelectResult(s){
	if(s==$("#orderSureForm #isAgree").val()){
		return false;
	}
	if($("#isAgree").val()!=''&&$("#isAgree").val()!=null){
		$("#orderSureForm #operMemo").val("");
	}
	if(s=='2'){
		//退回
		$_showWarnWhenOverLen1($("#orderSureForm #operMemo")[0],500,'authdealCommentSpan');
		$("#orderSureForm #isAgree").val("2");
		
	}else{
		//同意
		$_showWarnWhenOverLen1($("#orderSureForm #operMemo")[0],500,'authdealCommentSpan');
		$("#orderSureForm #isAgree").val("1");
	}
}
function doValidate(){
  //校验服务开始时间和服务结束时间 
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	if(startDate!=""&&endDate!=""){
		if(startDate>endDate){
			App.notyError("合同起始日期必须在合同終止日期之前!");
			return false;
		}
	}
	if((startDate!=""&&endDate=="")||(startDate==""&&endDate!="")){
		App.notyError("请同时选择开始日期和结束日期!");
		return false;
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
<p:authFunc funcArray="03030103,03030104,03030105"/>
<form action="" method="post" id="reqtypeForm">
<input type="hidden" name="orderId" value="${orderId}"/>
<input type="hidden" name="operMemo"  id="operMemo"/>
<input type="hidden" name="cntNum"  id="cntNum" value="${orderBean.cntNum}"/>
<p:token/>
	<table id="orderIdTable">
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">订单号:${orderId}</th>
					</tr>
					<tr>
						<td class="tdLeft" width="20%">采集编号</td>
						<td class="tdRight" width="30%">
							${orderBean.stockNum}
						</td>
						<td class="tdLeft" width="20%">采评会批复金额</td>
						<td class="tdRight" width="30%">
								<fmt:formatNumber type="number" value="${orderBean.stockAmt}" minFractionDigits="2"/>元
						</td>
					</tr>
					<tr>
						<td class="tdLeft">采评会批复供应商</td>
						<td class="tdRight">
							${orderBean.stockProv}
						</td>
						<td class="tdLeft">合同或协议编号</td>
						<td class="tdRight">
								${orderBean.cntNum}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同或协议签署日期</td>
						<td class="tdRight">
							${orderBean.signDate}
						</td>
						<td class="tdLeft">付款条件说明</td>
						<td class="tdRight">
								${orderBean.payTermMemo}
						</td>
					</tr>
					<c:if test="${orderBean.startDate!=null}">
						<tr>
						<td class="tdLeft">合同起始日期</td>
						<td class="tdRight">
							${orderBean.startDate}
						</td>
						<td class="tdLeft">合同終止日期</td>
						<td class="tdRight">
								${orderBean.endDate}
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
						<td class="tdc"><c:out value="${sedList.montCode}"/>-<c:out value="${sedList.montName}"/></td>
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
						<th colspan="4">待补录信息</th>
					</tr>
					<tr>
						<td class="tdLeft">部门编码</td>
						<td class="tdRight">
<%-- 						<input type="text" id="detpId" name="deptId"  value="${orderBean.deptId}"  class="base-input-text"  readonly="readonly"> --%>
						<div class="ui-widget">
							<select id="deptId" name="deptId">
								<option value="0">0</option>
								<option value="${orderBean.chkDutyCode}">${orderBean.chkDutyCode}</option>
							</select>
						</div>
						</td>
						<td class="tdLeft">采购方式</td>
							<td class="tdRight">
								<div class="ui-widget">
									<select id="procurementRoute" name="procurementRoute"   class="erp_cascade_select">
										<option value="">-请选择-</option>
										<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
										 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PRUCHASE_WAY'" selectedValue=""/>
									</select>
								</div>	
			            </td>
					</tr>
					<c:if test="${orderBean.startDate==null}">
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
							<textarea   class="base-textArea" id="orderMemo" name="orderMemo" rows='5' onkeyup="$_showWarnWhenOverLen1(this,250,'deptDescSpan')" >${orderBean.orderMemo}</textarea>
						</td>
				   </tr>
	         	</table>
	         </td>
		</tr>
		</table>
		 <!--操作按钮-->
				<br/>
				<div style="text-align:center;" >
					<input type="button" value="订单确认" onclick="toAudit();"/>
					<input type="button" value="查询订单历史记录" onclick="queryHis('${orderId}');">
				    <input type="button" value="返回" onclick="backToLastPage('${uri}');">
					<br>
				</div>		
</form>	
<div id="auditDiv" style="display: none;">
	<form action="" method="post" id="orderSureForm">
		<p:token/>
		<input type="hidden" name="orderId" value="${orderId}">
		<table width="98%">
			 <tr>
				<td align="left" colspan="2">
					<input type="hidden" id="isAgree" name="isAgree" />
					<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('1');">同意</label><input type="radio" id="authdealFlag1" name="dealFlag" value="1" >
						<label for="authdealFlag2" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('2')" >退回</label><input type="radio" id="authdealFlag2" name="dealFlag" value="0">
					</div>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<br>转发意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="operMemo" name="operMemo" rows="7" cols="45"  errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>