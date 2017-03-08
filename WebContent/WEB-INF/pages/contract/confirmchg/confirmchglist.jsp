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
<title>变更确认</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	
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
function gotoDtl(cntNum)
{
	var form = $("#queryForm")[0];
	form.action='<%=request.getContextPath()%>/contract/confirmchg/confirmChgDtl.do?<%=WebConsts.FUNC_ID_KEY%>=0302040201&cntNum='+cntNum;
	App.submit(form);
}
function selProvider()
{	
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
function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}

//查询列表
function listEnd()
{
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
 	var form=$("#endFormSearch")[0];
 	var orgFlag = '${con.orgFlag}';
 	if(orgFlag == '1')
 	{
 		form.action="<%=request.getContextPath()%>/contract/confirmchg/org1List.do?<%=WebConsts.FUNC_ID_KEY%>=03020402";
 	}
 	else if(orgFlag == '2')
 	{
 		form.action="<%=request.getContextPath()%>/contract/confirmchg/org2List.do?<%=WebConsts.FUNC_ID_KEY%>=03020405";
 	}
 	else if(orgFlag == '3')
 	{
 		form.action="<%=request.getContextPath()%>/contract/confirmchg/dutyCodeList.do?<%=WebConsts.FUNC_ID_KEY%>=03020406";
 	}
	App.submit(form);
}
</script>
</head>

<body>
<p:authFunc funcArray="03020402,0302040201"/>
<form method="post" id="queryForm" action=""></form>
<form method="post" id="endFormSearch" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${con.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
			<div class="ui-widget">
				<select class="erp_cascade_select" id="cntType" name="cntType">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
					 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
					 orderType="ASC" selectedValue="${con.cntType}"/>	
				</select>
			</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName" value="${con.providerName}" class="base-input-text" onclick="selProvider()" readonly/>
				<%-- <a href="#" onclick="selProvider()" title="供应商查询">
					<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a> --%>
			</td>
			<td class="tdLeft">签订日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${con.befDate}" class="base-input-text" style="width:135px;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${con.aftDate}" class="base-input-text" style="width:135px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="查找" onclick="listEnd();"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList">
			<tr>
				<th width='10%'>合同号 </th>
				<th width='10%'>合同事项    </th>
				<th width='8%'>合同类型    </th>
				<th width='10%'>供应商  </th>
				<th width='8%'>合同金额（不含税）    </th>
				<th width='8%'>税额  </th>
				<th width='8%'>签订日期    </th>
				<th width='8%'>合同状态  </th>
				<th width='8%'>录入责任中心</th>
				<th width='8%'>录入时间    </th>
				<th width='8%'>发起变更人    </th>
				<th width='6%'>操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${cntItem.cntNum}</td>
				<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td class="tdc">${cntItem.cntTypeName}/<c:if test="${cntItem.isOrder == 1}">非订单</c:if><c:if test="${cntItem.isOrder == 0}">订单</c:if></td>
				<td>${cntItem.providerName}</td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntAmt}" minFractionDigits="2"/></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntTaxAmt}" minFractionDigits="2"/></td>
				<td class="tdc">${cntItem.signDate}</td>
				<td>${cntItem.dataFlagName}</td>
				<td class="tdc">${cntItem.createDeptName}</td>
				<td class="tdc">${cntItem.createDate}</td>
				<td class="tdc">${cntItem.instUser}</td>
				<td>
					<c:if test="${cntItem.isEnable=='Y'}">
						<div class="update">
						    <a href="#" onclick="gotoDtl('${cntItem.cntNum}');" title="确认"></a>
						</div>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="12" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>