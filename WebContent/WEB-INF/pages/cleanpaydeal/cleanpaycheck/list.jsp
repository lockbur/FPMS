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
<title>暂收结清待复核列表</title>
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
	App.submitShowProgress();
	var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	App.submitFinish();
	if(!proObj){//判断谷歌浏览器返回问题
		proObj = window.returnValue;
		if(proObj){
			$("#providerCode").val(proObj.providerCode);
			$("#providerName").val(proObj.providerName);
		}
	}else{
		$("#providerCode").val(proObj.providerCode);
		$("#providerName").val(proObj.providerName);
	}
}

//暂收结清复核信息查询
function showDetail(cleanPayId,payId,cntNum)
{
	var form = $("#cleanDealForm")[0];
	form.action = "<%=request.getContextPath()%>/cleanpaydeal/cleanpaycheck/detail.do?<%=WebConsts.FUNC_ID_KEY %>=03050302&cleanPayId="+cleanPayId+"&payId="+payId+"&cntNum="+cntNum;
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
<p:authFunc funcArray="03050301"/>
<form action="" method="post" id="cleanDealForm"></form>
<form action="" method="post" id="cleanForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				暂收查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">暂收结清编号</td>
			<td class="tdRight">
				<input type="text" id="cleanPayId" name="cleanPayId" value="${selectInfo.cleanPayId}" class="base-input-text" />
			</td>
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
				<input type="text" id="payId" name="payId" value="${selectInfo.payId}" class="base-input-text" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="erp_cascade_select" name="cntType">
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
			<td class="tdLeft">付款日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate"  maxlength='10' readonly="readonly" value="${selectInfo.befDate}" class="base-input-text" style="width:135px;"/>
				至
				<input type="text" id="aftDate" name="aftDate"  maxlength='10' readonly="readonly" value="${selectInfo.aftDate}" class="base-input-text" style="width:135px;"/>
			</td>
			<td class="tdLeft" >供应商</td>
			<td class="tdRight">
				<input type="hidden" id="providerCode"  name="providerCode" value="${selectInfo.providerCode}" class="base-input-text"/>
				<input type="text"  id="providerName" name="providerName" value="${selectInfo.providerName}" class="base-input-text"/>
				<a href="#" onclick="providerType();" title="供应商查询">
					<img border="0px;" height="100%;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<p:button funcId="03050301" type="search"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList">
		<tr>
			<th width="11%">暂收结清编号</th>
			<th width="9%">付款单号</th>
			<th width="12%">合同号</th>
			<th width="9%">暂收日期</th>
			<th width="10%">收款单位</th>
			<th width="11%">暂收金额(元)</th>
			<th width="15%">已结清暂收金额(元)</th>
			<th width="11%">结清金额(元)</th>
			<th width="6%">状态</th>
			<th width="6%">操作</th>
		</tr>
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="bean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td>${bean.cleanPayId}</td>
					<td>${bean.normalPayId}</td>
					<td>${bean.cntNum}</td>
					<td>${bean.suspenseDate}</td>
					<td>${bean.providerName}</td>
					<td><fmt:formatNumber type="number" value="${bean.suspenseAmt}" minFractionDigits="2"/></td>
					<td><fmt:formatNumber type="number" value="${bean.susTotalAmt}" minFractionDigits="2"/></td>
					<td><fmt:formatNumber type="number" value="${bean.cleanAmt}" minFractionDigits="2"/></td>
					<td>${bean.dataFlagName}</td>
					<td>
						<div class="update">
							<a href="#" onclick="showDetail('${bean.cleanPayId}','${bean.normalPayId}','${bean.cntNum}');" title="复核"></a>
						</div>
						
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list}">
			<tr><td style="text-align: center;" colspan="10"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>