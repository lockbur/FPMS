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
<title>订单类付款查询</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	//设置时间插件
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
//得到详细信息
function showDialog(batchNo,seqNo,payAmt,payCancelState,flag) 
{  	
	var str = "&batchNo="+batchNo+"&seqNo="+seqNo+"&payAmt="+payAmt+"&payCancelState="+payCancelState+"&flag="+flag;
	var url = "<%=request.getContextPath()%>/pay/orderpaymgr/surePayType.do?<%=WebConsts.FUNC_ID_KEY%>=03070501"+str;
	var form = $("#orderPayForm1")[0];
	form.action = url;
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
<p:authFunc funcArray="030705"/>
<form  method="post" id="orderPayForm1"></form>
<form  method="post" id="orderPayForm">
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
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
            	<input type="text" id="payId" name="payId" value="${selectInfo.payId}" class="base-input-text" maxlength="20" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">付款日期区间</td>
			<td class="tdRight" width="30%">
				<input id="befDate"  name="befDate" readonly="readonly" style="width: 35%" value="${selectInfo.befDate}" class="base-input-text"/>至
				<input id="aftDate"  name="aftDate" readonly="readonly" style="width: 35%" value="${selectInfo.aftDate}" class="base-input-text"/>
			</td>
			<td class="tdLeft">付款取消状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="payCancelState" name="payCancelState" class="erp_cascade_select">
						<option value="">--请选择--</option>
						<option value="Y" <c:if test="${selectInfo.payCancelState == 'Y'}">selected="selected"</c:if> >是</option>
						<option value="N" <c:if test="${selectInfo.payCancelState == 'N'}">selected="selected"</c:if> >否</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="030705" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>
				<th width="15%">合同编号</th>
				<th width="15%">FMS付款单号</th>
				<th width="10%">付款金额</th>
				<th width="10%">付款日期</th>
				<th width="18%">影像ID</th>
				<th width="11%">付款是否取消</th>
				<th width="21%">选择</th>
			</tr>
			<c:if test="${!empty lists}">
			<c:forEach items="${lists}" var="bean">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${bean.cntNum }</td>
				<td>${bean.payId }</td>
				<td><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2" maxFractionDigits="2"/></td>
				<td>${bean.payDate }</td>
				<td>${bean.imageId }</td>
				<td>
					<c:if test="${bean.payCancelState == 'N' || bean.payCancelState == 'n'}">
						否
					</c:if>
					<c:if test="${bean.payCancelState == 'Y' || bean.payCancelState == 'y'}">
						是
					</c:if>
				</td>
				<td>
					<input type="button" value="正常付款" onclick="showDialog('${bean.batchNo}','${bean.seqNo}','${bean.payAmt }','${bean.payCancelState}','0');"/>
					<input type="button" value="暂收结清" onclick="showDialog('${bean.batchNo}','${bean.seqNo}','${bean.payAmt }','${bean.payCancelState}','1');"/>
				</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty lists}">
				<tr>
					<td class="red" colspan="7" style="text-align: center;">没有找到相关信息</td>
				</tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
</body>
</html>