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
<title>暂收结清列表</title>
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
 	
	//设置时间插件
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

//暂收结清详细信息
function showDetail(normalPayId,sortId,cntNum)
{
	var form = $("#cleanDealForm")[0];
	form.action = "<%=request.getContextPath()%>/cleanpaydeal/cleanpayquery/detail.do?<%=WebConsts.FUNC_ID_KEY %>=03050502&normalPayId="+normalPayId+"&sortId="+sortId+"&cntNum="+cntNum;
	App.submit(form,true);
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
<p:authFunc funcArray="03050511,03050512,03050513"/>
<form action="" method="post" id="cleanDealForm"></form>
<form action="" method="post" id="cleanForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				暂收查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
				<input type="text" id="normalPayId" name="normalPayId" value="${selectInfo.normalPayId}" class="base-input-text"  maxlength="20"/>
			</td>
			<td class="tdLeft">付款日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate"  maxlength='10' readonly="readonly" value="${selectInfo.befDate}" class="base-input-text" style="width:35%;"/>
				至
				<input type="text" id="aftDate" name="aftDate"  maxlength='10' readonly="readonly" value="${selectInfo.aftDate}" class="base-input-text" style="width:35%;"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" id="cntTypeSelect" name="cntType">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
						 orderType="ASC" selectedValue="${selectInfo.cntType}"/>	
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >供应商</td>
			<td class="tdRight">
				<input type="hidden" id="providerCode"  name="providerCode" value="${selectInfo.providerCode}" class="base-input-text"/>
				<input type="text"  id="providerName" name="providerName" value="${selectInfo.providerName}"  class="base-input-text" maxlength="30"/>
				<a href="#" onclick="providerType();" title="供应商查询">
					<img border="0px;" height="100%;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
			<td class="tdLeft" ></td>
			<td class="tdRight"></td>
		</tr>
		<tr>
			<td class="tdWhite" colspan="4">
				<c:if test="${selectInfo.orgFlag=='1'}">
					<p:button funcId="03050511" value="查询"/>
				</c:if>
				<c:if test="${selectInfo.orgFlag=='2'}">
					<p:button funcId="03050512" value="查询"/> 
				</c:if>
				<c:if test="${selectInfo.orgFlag=='3'}">
					<p:button funcId="03050513" value="查询"/>
				</c:if>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList">
		<tr>
			<th width="10%">付款单号</th>
			<th width="8%">子序号</th>
			<th width="12%">合同号</th>
			<th width="11%">暂收付款日期</th>
			<th width="10%">收款单位</th>
			<th width="11%">暂收金额(元)</th>
			<th width="15%">已结清暂收金额(元)</th>
			<th width="11%">结清金额(元)</th>
			<th width="6%">操作</th>
		</tr>
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="bean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc">${bean.normalPayId}</td>
					<td class="tdc">${bean.sortId}</td>
					<td class="tdc"><a href="javascript:void(0);" onclick="gotoCntDtl('${bean.cntNum }');" class="text_decoration">${bean.cntNum }</a></td>
					<td class="tdc">${bean.suspenseDate}</td>
					<td>${bean.providerName}</td>
					<td class="tdr"><fmt:formatNumber type="number" value="${bean.suspenseAmt}" minFractionDigits="2"/></td>
					<td class="tdr"><fmt:formatNumber type="number" value="${bean.susTotalAmt}" minFractionDigits="2"/></td>
					<td class="tdr"><fmt:formatNumber type="number" value="${bean.cleanAmt}" minFractionDigits="2"/></td>					<td>
						<div class="detail">
							<a href="#" onclick="showDetail('${bean.normalPayId}','${bean.sortId}','${bean.cntNum}');" title="详情"></a>
						</div>
						
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list}">
			<tr><td style="text-align: center;" colspan="9"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>