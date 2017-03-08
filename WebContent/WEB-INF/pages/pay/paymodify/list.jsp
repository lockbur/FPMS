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
<title>付款查询</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
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
function resetAll() {
	$("select").val("");
	$(":text").val("");
	//$(":hidden").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function providerType(){
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
						$("#providerCode").val(object.providerCode);
						$("#providerName").val(object.providerName);
					}
					}		
			}
		 );
}
//修改
function modify(cntNum,payId,payType,isCreditNote,invoiceIdBlue)
{
	var form=$("#modifyForm")[0];
	form.action="<%=request.getContextPath()%>/pay/paymodify/modify.do?<%=WebConsts.FUNC_ID_KEY%>=03030401&cntNum="+cntNum+"&payId="+payId+"&payType="+payType+"&isCreditNote="+isCreditNote+"&invoiceIdBlue="+invoiceIdBlue;
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
<p:authFunc funcArray="030304"/>
<form action="" method="post" id="modifyForm">
</form>
<form action="" method="post" id="queryForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4" >付款查询</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">合同号</td>
			<td class="tdRight" width="25%">
				<input  id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft" width="25%">合同类型</td>
			<td class="tdRight" width="25%">
				<div class="ui-widget">
					<select id="cntType" name="cntType" class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='CNT_TYPE'" selectedValue="${selectInfo.cntType}"/>
					</select>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft" width="25%">付款单号</td>
			<td class="tdRight" width="25%">
				<input id="payId"  name="payId" value="${selectInfo.payId}" class="base-input-text" maxlength="20"/>
			</td>
			<td class="tdLeft" width="25%">付款日期区间</td>
			<td class="tdRight" width="25%">
				<input id="befDate"  name="befDate" readonly="readonly" style="width: 35%" value="${selectInfo.befDate}" class="base-input-text"/>至
				<input id="aftDate"  name="aftDate" readonly="readonly" style="width: 35%" value="${selectInfo.aftDate}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">供应商</td>
			<td class="tdRight" width="25%">
				<input type="hidden" id="providerCode"  name="providerCode" value="${selectInfo.providerCode}" class="base-input-text"/>
				<input type="text"  id="providerName" name="providerName" value="${selectInfo.providerName}"  class="base-input-text" maxlength="30"/>
				<a href="#" onclick="providerType();"  title="供应商查询">
					<img border="0px;" height="100%;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
			<td class="tdLeft" width="25%">状态</td>
			<td class="tdRight" width="25%">
				<div class="ui-widget">
					<select id="dataFlag" name="dataFlag" class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='PAY_DATA_FLAG' AND PARAM_VALUE LIKE 'A_'" selectedValue="${selectInfo.dataFlag}"/>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="030304" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<tr>
			<th width="13%">合同号</th>
			<th width="10%">合同事项</th>
			<th width="10%">合同类型</th>
			<th width="10%">付款单号</th>
			<th width="10%">付款日期</th>
			<th width="8%">付款类型</th>
			<th width="10%">发票金额(元)</th>
			<th width="13%">收款单位</th>
			<th width="10%">状态</th>
			<th width="6%">操作</th>
		</tr>
		<c:if test="${!empty list }">
			<c:forEach items="${list }" var="bean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');">
					<td class="tdc" ><a href="javascript:void(0);" onclick="gotoCntDtl('${bean.cntNum }');" class="text_decoration">${bean.cntNum }</a></td>
					<td class="tdc" >${bean.cntName }</td>
					<td class="tdc" >${bean.cntTypeName }<c:if test="${bean.isOrder == 1}">/非订单</c:if><c:if test="${bean.isOrder == 0}">/订单</c:if></td>
					<td class="tdc" >${bean.payId }</td>
					<td class="tdc">${bean.payDate }</td>
					<td>
						<c:if test="${bean.payType == 'Y'}">
							<c:if test="${bean.isCreditNote== '0'}">
								贷项通知单
							</c:if>
							<c:if test="${bean.isCreditNote== '1'}">
								正常付款
							</c:if>
						</c:if>
						<c:if test="${bean.payType == 'N'}">
							预付款
						</c:if>
					</td>
					<td class="tdr"><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2"/></td>
					<td >${bean.providerName }</td>
					<td>${bean.dataFlagName }</td>
					<td>
						<div class="update">
							<a href="#" onclick="modify('${bean.cntNum }','${bean.payId }','${bean.payType }','${bean.isCreditNote }','${bean.invoiceIdBlue}');" title="修改"></a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="10" style="text-align: center;"><span class="red">没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>