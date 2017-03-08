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
//查询订单操作历史记录(弹窗显示)
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
	<table id="orderIdTable">
		<tr>
			<td>
				<table>	
					<tr class="collspan-control">
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
						<td class="tdRight"><fmt:formatNumber type="number" value="${orderInfo.stockAmt}" minFractionDigits="2"/>元</td>
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
						<td class="tdLeft">付款条件说明</td>
						<td class="tdRight">
							${orderInfo.payTermMemo}
						</td>
					</tr>
				<c:if test="${orderInfo.startDate!=null}">
					<tr>
						<td class="tdLeft" >合同约定期限（开始日期）</td>
						<td class="tdRight"><c:out value="${orderInfo.startDate}"></c:out></td>
						<td class="tdLeft">合同约定期限（截止日期）</td>
						<td class="tdRight">
							<c:out value="${orderInfo.endDate}"></c:out>
						</td>
					</tr>
				</c:if>	
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
						
							<tr>
								<td class="tdLeft">PO单号(FMS订单编号)</td>
								<td class="tdRight" colspan="3">
									<c:if test="${!empty orderInfo.poNumber}">
									<c:out value="${orderInfo.poNumber}"></c:out>
									</c:if>
									<c:if test="${empty orderInfo.poNumber}"> 
									   未返回信息
									</c:if>
								</td>
							</tr>
						
					<tr>
						<td class="tdLeft" > 订单说明</td>
						<td class="tdRight" colspan="3">
							<textarea   class="base-textArea"  readonly="readonly" rows='5' >${orderInfo.orderMemo}</textarea>
						</td>
					</tr>
			</table>
		</td>
	</tr>
	<c:if test="${orderInfo.dataFlag!='03'}">
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
	</c:if>
	</table>		
	<br>
	<div style="text-align:center;" >
				<input type="button" value="查询订单历史记录" onclick="queryHis('${orderInfo.orderId}');">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</body>
</html>