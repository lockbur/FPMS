<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<title>导入-正常付款详情页面</title>

<style type="text/css">
	/* 文件描述框样式设置为不可改变大小 */
	textarea{
		resize : none;
	}
</style>
	
<script type="text/javascript">
	//页面初始化执行加载
	function pageInit(){
		App.jqueryAutocomplete();
// 	 	$("#fileType").combobox();
	}


</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form id="downloadForm" method="post"></form>
<form action="" method="post" id="importFileDetailForm">
	<p:token/>
	
	<c:if test="${! empty normalPayInfo}">
		<table id="normalPayTb">
			<tr class="collspan-control">
				<th colspan="4">
					导入-正常付款详情[关联合同号：${normalPayInfo.cntNum}]
					<input type="hidden" value="${normalPayInfo.payId}">
				</th>
			</tr>
			<tr>
				<td class="tdLeft">付款类型</td>
				<td class="tdRight">
					正常付款
				</td>
				<td class="tdLeft" width="20%">付款ID</td>
				<td class="tdRight" width="30%">${normalPayInfo.payId}</td>
			</tr>
			<tr>
				<td class="tdLeft">是否贷项通知单</td>
				<td class="tdRight">
					
					<c:if test="${normalPayInfo.isCreditNote eq '0'}">是</c:if>
					<c:if test="${normalPayInfo.isCreditNote eq '1'}">否</c:if>
				</td>
				<td class="tdLeft">供应商名称</td>
				<td class="tdRight">
					${normalPayInfo.providerName}
				</td>
			</tr>
			<tr>
				<td class="tdLeft">发票金额</td>
				<td class="tdRight">
					${normalPayInfo.invoiceAmt}
				</td>
				<td class="tdLeft">付款金额</td>
				<td class="tdRight" colspan="3">
					${normalPayInfo.payAmt}
				</td>
			</tr>
			<tr>
				<td class="tdLeft">发票备注</td>
				<td class="tdRight" colspan="3">
					${normalPayInfo.invoiceMemo}
				</td>
	<!-- 			<td class="tdLeft">其他</td> -->
	<!-- 			<td class="tdRight"> -->
	<!-- 			</td> -->
			</tr>
			
			<tr>
				<c:if test="${empty normalPayInfo.payAdCancelList}">
					<td colspan="4" style="color: red;">
						<br/>
						本次付款无关联预付款核销数据
					</td>
				</c:if>
				<c:if test="${! empty normalPayInfo.payAdCancelList}">
					<td colspan="4">
						&nbsp;
						<table class="tableList">
							<tr><th colspan="4">预付款核销信息</th></tr>
							<tr class="collspan-control">
								<th width="10%">序号</th>
								<th width="13%">正常付款单号</th>
								<th width="13%">关联预付款单号</th>
								<th width="13%">本次核销金额</th>
							</tr>
							<c:forEach items="${normalPayInfo.payAdCancelList}" var="payAdCancelBean" varStatus="vs">
								<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
									<td style="text-align: center">${vs.index+1}</td>
									<td style="text-align: center">${payAdCancelBean.normalPayId}</td>
									<td style="text-align: center">${payAdCancelBean.advancePayId}</td>
									<td style="text-align: center">${payAdCancelBean.cancelAmt}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</c:if>
			</tr>
			
			<c:if test="${! empty normalPayInfo.payDeviceList}">
				<tr>
					<td colspan="4">
						&nbsp;
						<table class="tableList">
							<tr><th colspan="7">关联导入物料信息</th></tr>
							<tr class="collspan-control">
								<th width="6%">序号</th>
								<th width="13%">付款单号</th>
								<th width="9%">付款类型</th>
								<th width="11%">物料编号</th>
								<th width="22%">物料名称</th>
								<th width="15%">发票分配金额(元)</th>
								<th width="24%">发票行说明</th>
							</tr>
							<c:forEach items="${normalPayInfo.payDeviceList}" var="payDeviceBean" varStatus="vs">
								<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" >
									<td style="text-align: center">${vs.index+1}</td>
									<td style="text-align: center">${payDeviceBean.payId}</td>
									<td style="text-align: center">
										<c:if test="${'1' eq payDeviceBean.payType}">正常付款</c:if> 
										<c:if test="${'2' eq payDeviceBean.payType}">预付款</c:if> 
									</td>
									<td style="text-align: center">${payDeviceBean.matrCode}</td>
									<td style="text-align: center">${payDeviceBean.matrName}</td>
									<td style="text-align: center">${payDeviceBean.subInvoiceAmt}</td>
									<td style="text-align: center">${payDeviceBean.ivrowMemo}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:if>
			
			
			<tr>
				<td style="text-align: center;" colspan="4" class="tdWhite">
					<input type="button" value="关闭" onclick="art.dialog.close()" />
				</td>
			</tr>
		</table>
	
	</c:if>
	
	<c:if test="${! empty advPayInfo}">
		<table id="normalPayTb">
			<tr class="collspan-control">
				<th colspan="4">
					导入-预付款详情[关联合同号：${advPayInfo.cntNum}]
					<input type="hidden" value="${advPayInfo.payId}">
				</th>
			</tr>
			<tr>
				<td class="tdLeft">付款类型</td>
				<td class="tdRight">
					预付款
				</td>
				<td class="tdLeft" width="20%">预付款ID</td>
				<td class="tdRight" width="30%">${advPayInfo.payId}</td>
			</tr>
			<tr>
				<td class="tdLeft">供应商名称</td>
				<td class="tdRight">
					${advPayInfo.providerName}
				</td>
				<td class="tdLeft">供应商账号</td>
				<td class="tdRight">
					${advPayInfo.provActNo}
				</td>
			</tr>
			<tr>
				<td class="tdLeft">供应商币别</td>
				<td class="tdRight">
					${advPayInfo.provActNo}
				</td>
				<td class="tdLeft">发票金额</td>
				<td class="tdRight">
					${advPayInfo.invoiceAmt}
				</td>
			</tr>
			<tr>
				<td class="tdLeft">付款方式</td>
				<td class="tdRight">
					${advPayInfo.payMode}
				</td>
				<td class="tdLeft">付款日期</td>
				<td class="tdRight">
					${advPayInfo.payDate}
				</td>
			</tr>
			<tr>
				<td class="tdLeft">发票说明</td>
				<td class="tdRight" colspan="3">
					${advPayInfo.invoiceMemo}
				</td>
	<!-- 			<td class="tdLeft">其他</td> -->
	<!-- 			<td class="tdRight"> -->
	<!-- 			</td> -->
			</tr>
			
			
			<tr>
				<td style="text-align: center;" colspan="4" class="tdWhite">
					<input type="button" value="关闭" onclick="art.dialog.close()" />
				</td>
			</tr>
		</table>
	
	</c:if>
	<br/>
<!-- 	<table class="tableList"> -->
<!-- 		<tr class="collspan-control"> -->
<!-- 			<th width="10%">序号</th> -->
<!-- 			<th width="13%">Excel模板</th> -->
<!-- 			<th width="13%">Sheet名称</th> -->
<!-- 			<th width="10%">行号</th> -->
<!-- 			<th width="54%">错误信息描述</th> -->
<!-- 		</tr> -->
<%-- 		<c:forEach items="${importErrMsgList}" var="importErrMsgRow" varStatus="vs"> --%>
<!-- 			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" > -->
<%-- 				<td style="text-align: center">${vs.index+1}</td> --%>
<!-- 				<td style="text-align: center"> -->
<!-- 					该值根据参数表查找(uploadType) -->
<%-- 					${importErrMsgRow.uploadType} --%>
<!-- 				</td> -->
<!-- 				<td style="text-align: center"> -->
<!-- 					该值根据参数表查找(uploadType+dataType) -->
<%-- 					${importErrMsgRow.dataType} --%>
<!-- 				</td> -->
<%-- 				<td style="text-align: center">第${importErrMsgRow.rowNo}行</td> --%>
<%-- 				<td style="text-align: left">${importErrMsgRow.errDesc}</td> --%>
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
<%-- 		<c:if test="${empty importErrMsgList}"> --%>
<!-- 			<tr><td style="text-align: center;" colspan="5"><span class="red">该导入任务校验为无错误数据</span></td></tr> -->
<%-- 		</c:if> --%>
<!-- 	</table> -->
</form>
<p:page/>
</body>
</html>