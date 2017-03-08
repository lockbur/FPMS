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
<title>影像编辑申请查询</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#icmsEdit").combobox();
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function selProvider(){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商选择",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#providerName").val(object.providerName);
					}
					}		
			}
		 );
}
function showDetail(payId,isPrePay,cntNum,monDataFlag,icmsEdit,isCreditNote,invoiceIdBlue){
	var form = $("#queryDetailForm");
 	$("#queryDetailForm #payId1").val(payId);
	$("#queryDetailForm #isPrePay1").val(isPrePay);
	$("#queryDetailForm #cntNum1").val(cntNum);
	$("#queryDetailForm #isCreditNote1").val(isCreditNote);
	$("#queryDetailForm #invoiceIdBlue1").val(invoiceIdBlue);
 	form.attr('action', '<%=request.getContextPath()%>/pay/icmsapply/detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030610');
	App.submit(form);
}
function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="03030609"/>
<form action="" method="post" id="queryDetailForm">
	<input type="hidden" id="payId1" name="payId"  class="base-input-text"/>
	<input type="hidden" id="isPrePay1" name="isPrePay"  class="base-input-text"/>
	<input type="hidden" id="cntNum1" name="cntNum"  class="base-input-text"/>
	<input type="hidden" id="isCreditNote1" name="isCreditNote"  class="base-input-text"/>
	<input type="hidden" id="invoiceIdBlue1" name="invoiceIdBlue"  class="base-input-text"/>
</form>
<form  method="post" id="tempForm">
<input type="hidden" id="isPrePay" name="isPrePay">
	<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				付款查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text"  maxlength="80"/>
			</td>
			<td class="tdLeft">合同事项</td>
			<td class="tdRight">
				<input type="text" id="cntName" name="cntName" value="${selectInfo.cntName}" class="base-input-text"  maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
				<input type="text" id="payId" name="payId" value="${selectInfo.payId}" class="base-input-text" maxlength="20"/>&nbsp;&nbsp;	
			</td>
			<td class="tdLeft">发票编号</td>
			<td class="tdRight">
				<input type="text" id="invoiceId" name="invoiceId" value="${selectInfo.invoiceId}" class="base-input-text" maxlength="100"/>&nbsp;&nbsp;	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">影像编辑状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id= "icmsEdit" name="icmsEdit" class="erp_cascade_select">
						<option value=''>--请选择--</option>
						<option value='0' <c:if test="${selectInfo.icmsEdit == '0'}">selected</c:if>>无需编辑</option>
						<option value='1' <c:if test="${selectInfo.icmsEdit == '1'}">selected</c:if>>影像编辑中</option>
						<option value='2' <c:if test="${selectInfo.icmsEdit == '2'}">selected</c:if>>编辑完成</option>
					</select>
				</div>
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight"></td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="03030609" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
			 	<th width="13%">合同号</th>
				<th width="7%">合同事项</th>
				<th width="10%">合同类型</th>
				<th width="10%">付款单号</th>
				<th width="10%">付款日期</th>
				<th width="8%">付款类型</th>
				<th width="10%">发票金额(元)</th>
				<th width="13%">收款单位</th>
				<th width="10%">状态</th>
				<th width="13%">影像编辑状态</th>
				<th width="6%">操作</th>
			</tr>
			 <c:forEach items="${icmsList}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc" ><a href="javascript:void(0);" onclick="gotoCntDtl('${sedList.cntNum }');" class="text_decoration">${sedList.cntNum }</a></td>
					<td class="tdc" ><c:out value="${sedList.cntName}"></c:out></td>
					<td class="tdc" >${sedList.cntTypeName }<c:if test="${sedList.isOrder == 1}">/非订单</c:if><c:if test="${sedList.isOrder == 0}">/订单</c:if></td>
					<td class="tdc" ><c:out value="${sedList.payId}"></c:out></td>
					<td class="tdc" ><c:out value="${sedList.payDate}"/></td>
					<td >
						<c:if test="${sedList.isPrePay=='Y'}">
							<c:out value="预付款"/>
						</c:if>
						<c:if test="${sedList.isPrePay=='N'}">
							<c:if test="${sedList.isCreditNote== '0'}">
								贷项通知单
							</c:if>
							<c:if test="${sedList.isCreditNote== '1'}">
								正常付款
							</c:if>
						</c:if>
					</td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.invoiceAmt}" minFractionDigits="2"/></td>
					<td><c:out value="${sedList.providerName}"/></td>
					<td>
						<c:out value="${sedList.payDataFlag}"/>
					</td>
					<td >
						<c:if test="${sedList.icmsEdit== '0'}">
							无需编辑
						</c:if>
						<c:if test="${sedList.icmsEdit== '1'}">
							编辑申请中
						</c:if>
						<c:if test="${sedList.icmsEdit== '2'}">
							编辑完成
						</c:if>
					</td>
					<td>
						<div class="update">
							<a href="#" onclick="showDetail('${sedList.payId}','${sedList.isPrePay}','${sedList.cntNum}', '${sedList.icmsEdit}','${sedList.isCreditNote}','${sedList.invoiceIdBlue}')" title="确认"></a>
						</div>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty icmsList}">
					<tr><td style="text-align: center;" colspan="11"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
</body>
</html>