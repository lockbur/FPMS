	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入合同查询</title>
<style type="text/css">
	.fontCenter td{
		text-align: center;
	}
</style>

<script type="text/javascript">

//页面初始化加载方法
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".selectC").combobox();
 	//设置时间插件
	$( "#befDate,#aftDate,#beginDate,#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}

//查询参数重置方法
function resetAll() {
	$("select").val("");
	$(":text").val("");
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

//查询合同详情
function toCntDtlPage(cntNum , batchNo){
	var form = $("#dtlForm")[0];
	form.action='<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/importCntDetail.do?<%=WebConsts.FUNC_ID_KEY%>=06020701&cntNum='+cntNum+'&batchNo='+batchNo;
	App.submit(form);
}

//页面加载执行
$(document).ready(function (){
	$("#cntType").trigger("change");
});


</script>
</head>

<body>
<p:authFunc funcArray="060207"/>
<form action="" method="post" id="dtlForm"></form>
<form method="post" id="queryForm" action="<%=request.getContextPath()%>/contract/query/queryList.do?<%=WebConsts.FUNC_ID_KEY%>=030206">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				导入合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同编号</td>
			<td class="tdRight">
				<!-- 由于该页面的"查找"功能需要使用到参数batchNo，所以这里将上一页面传过来的batchNo保存在这里,"查找"时再作为参数传递过去进行查询 -->
				<input type="hidden" id="batchNo" name="batchNo" value="${con.batchNo}">
				<input type="text" name="cntNum" value="${con.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			
			<td class="tdLeft">合同事项</td>
			<td class="tdRight">
				<input type="text" name="cntName" value="${con.cntName}" class="base-input-text" maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select class="selectC" id="cntType" name="cntType" style="width: 250px">
						<option value="">--请选择--</option>						
						<forms:codeTable tableName="SYS_SELECT"   selectColumn="PARAM_VALUE,PARAM_NAME"
		 					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG"  
		 					 conditionStr="CATEGORY_ID = 'CNT_TYPE'" 
		 					 orderType="ASC" selectedValue="${con.cntType}"/>	 
					</select>
				</div>
			</td>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" name="providerName" value="${con.providerName}" class="base-input-text" maxlength="200"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">录入责任中心</td>
			<td class="tdRight" colspan="3">
				<input type="text" name="payDutyName" value="${con.payDutyName}" class="base-input-text" maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="060207" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="返回" onclick="backToLastPage('todataimportpage.do?VISIT_FUNC_ID=0602');">
			</td>
		</tr>
	</table>	
		<br>
	<table class="tableList">
			<tr>
				<th width='15%'>合同号         </th>
				<th width='12%'>合同事项    </th>
				<th width='8%'>合同类型    </th>
				<th width='16%'>供应商         </th>
				<th width='12%'>合同金额    </th>
				<th width='10%'>签订日期    </th>
				<th width='17%'>录入责任中心</th>
				<th width='10%'>操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${cntItem.cntNum}</td>
				<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td class="tdc">
					<c:if test="${cntItem.cntType eq '0'}">资产类</c:if>
					<c:if test="${cntItem.cntType eq '1'}">费用类</c:if>
				</td>
<%-- 				<td class="tdc">${cntItem.cntTypeName}<c:if test="${cntItem.isOrder == 1}">/非订单</c:if><c:if test="${cntItem.isOrder == 0}">/订单</c:if></td> --%>
				<td>${cntItem.providerName}</td>
				<td class="tdr" title="供应商货币单位为：${cntItem.provActCurr}">
					<fmt:formatNumber type="number" value="${cntItem.cntAmt}" minFractionDigits="2"/>  
				</td>
				<td class="tdc">${cntItem.signDate}</td>
<%-- 				<td>${cntItem.createDeptName}</td> --%>
<%-- 				<td class="tdc">${cntItem.createDate}</td> --%>
				<td class="tdc">${cntItem.payDutyName}</td>
				<td class="tdc">
					<input type="button" value="详情" onclick="toCntDtlPage('${cntItem.cntNum}','${cntItem.batchNo}');">
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="10" style="text-align: center;"><span class="red">没有查询到符合条件的相关数据！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>