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
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>原蓝字发票下的贷项通知单列表</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${projId}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('projectObj','');
	
}

//查询列表
function creditList()
{
 	var form=$("#searchForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payAdd/creditList.do?<%=WebConsts.FUNC_ID_KEY %>=03030332";
	App.submit(form);
}

//查询详情
function creditDetail(cntNum,payId,invoiceIdBlue)
{
	var isCreditNote = '0';
 	var url = "<%=request.getContextPath()%>/pay/payAdd/queryPayDetail.do?<%=WebConsts.FUNC_ID_KEY %>=03030311&payId="+payId+"&cntNum="+cntNum+"&isCreditNote="+isCreditNote+"&invoiceIdBlue="+invoiceIdBlue;
 	$.dialog.open(
			url,
			{
				width: "80%",
				height: "100%",
				lock: true,
			    fixed: true,
			    title:"贷项通知单明细",
			   // id:"dialogCutPage",
				close: function(){
				}		
			}
		 );
}


//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#projType").val("");	
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
};
</script>
</head>

<body>
<form action="" method="post"  id="searchForm">		
	<table>
		<tr>
			<th colspan="4">
				贷项通知单查询 
				<input type="hidden" name="invoiceIdBlue" name="invoiceIdBlue" value="${searchBean.invoiceIdBlue}"/>
			</th>
		</tr>
		<tr>
			<td class="tdLeft">贷项通知单发票编号</td>
			<td class="tdRight">
				<input type="text" id="invoiceId" name="invoiceId"  value="${searchBean.invoiceId}"  class="base-input-text" />
			</td>	
			<td class="tdLeft">贷项通知单付款单号</td>
			<td class="tdRight">
				<input type="text" id="payId" name="payId"  value="${searchBean.payId}"  class="base-input-text" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">贷项通知单状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" id="dataFlag" name="dataFlag">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'PAY_DATA_FLAG'"
						 orderType="ASC" selectedValue="${searchBean.dataFlag}"/>	
					</select>
				</div>
			</td>	
			<td class="tdLeft">贷项通知单发票状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" id="dataFlagInvoice" name="dataFlagInvoice">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'DATA_FLAG_INVOICE'"
						 orderType="ASC" selectedValue="${searchBean.dataFlagInvoice}"/>	
					</select>
				</div>
			</td>
		</tr>	
		<tr>
			<td class="tdLeft">贷项通知单付款单状态</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select class="erp_cascade_select" id="dataFlagPay" name="dataFlagPay">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'DATA_FLAG_PAY'"
						 orderType="ASC" selectedValue="${searchBean.dataFlagPay}"/>	
					</select>
				</div>
			</td>	
		</tr>
		<tr>
			<td colspan="4">
				<input type="button" value="查找" onclick="creditList();"/>
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<br/>
<form action="" method="post"  id="creditForm">		
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="18%">付款单号</th>
			<th width="17%">贷项通知单发票号</th>
			<th width="10%">发票总金额</th>
			<th width="10%">不含税金额</th>
			<th width="7%">税额</th>
			<th width="2">贷项通知单状态</th>
			<th width="8%">发票状态</th>
			<th width="8%">付款状态</th>
			<th width="10%">操作</th>
		</tr>
		<c:forEach items="${creditList}" var="c" varStatus="status">
			<tr  onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					${c.payId}
				</td>
				<td>
					${c.invoiceId}
				</td>
				<td>
					${c.invoiceAmt}
				</td>
				<td>
					${c.invoiceAmtNotax}
				</td>
				<td>
					${c.invoiceAmtTax}
				</td>
				<td>
					${c.payDataFlagName}
				</td>
				<td>
					${c.dataFlagInvoiceName}
				</td>
				<td>
					${c.dataFlagPayName}
				</td>
				<td>
					<input type="button" value="明细" onclick="creditDetail('${c.cntNum}','${c.payId}','${c.invoiceIdBlue}');"/>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty creditList}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	</form>
	<p:page/>
	<br/>
	<br/>
	<br/>
	<div align="center">
				<input type="button"  value="关闭"  onclick="art.dialog.close();" />
	</div>
</body>
</html>