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
<title>合同查询</title>
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


function accEntry(cntNum)
{
	var form = $("#listForm")[0];
	form.action='<%=request.getContextPath()%>/amortization/accEntry/getAccEntry.do?<%=WebConsts.FUNC_ID_KEY%>=040401&cntNum='+cntNum;
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
</script>
</head>

<body>
<p:authFunc funcArray="0404,040401,040402,040403,040404"/>
<form action="" method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${con.cntNum}" class="base-input-text"/>
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
				<input type="text" id="providerName" name="providerName" value="${con.providerName}" class="base-input-text"/>
				<a href="#" onclick="selProvider()" title="供应商查询">
					<img border="0;" src="<%=request.getContextPath()%>/common/images/search1.jpg" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
			<td class="tdLeft">签订日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${con.befDate}" class="base-input-text" style="width:35%;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${con.aftDate}" class="base-input-text" style="width:35%;"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">创建机构</td>
			<td class="tdRight" colspan="3">
				<%-- <input type="text" name="createDept" value="${con.createDept}" class="base-input-text"/> --%>
				<forms:OrgSelPlugin rootNodeId="${user.org1Code}" initValue="${con.createDept}" jsVarGetValue="createDept" parentCheckFlag="false"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
			<c:if test="${type == 1}">
				<p:button funcId="040402" value="查找"/>
			</c:if>
			<c:if test="${type == 2}">
				<p:button funcId="040403" value="查找"/>
			</c:if>
			<c:if test="${type == 3}">
				<p:button funcId="040404" value="查找"/>
			</c:if>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
</form>

	<br>
<form action="" method="post" id="listForm" action="">
	<table class="tableList">
			<tr>
				<th width="18%">合同号</th>
				<th width="9%">合同类型</th>
				<th width="12%">是否预提待摊类</th>
				<th width="9%">合同金额</th>
				<th width="13%">创建机构</th>
				<th width="11%">受益开始日期</th>
				<th width="11%">受益结束日期</th>
				<th width="11%">状态</th>
				<th width="6%">操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${cntItem.cntNum}</td>
				<td>
					<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"
						 conditionStr="CATEGORY_ID = 'CNT_TYPE'" selectedValue="${cntItem.cntType}"/>	
				</td>
				<td>
				    <c:if test="${cntItem.isPrepaidProvision == '0'}">是</c:if>
				    <c:if test="${cntItem.isPrepaidProvision == '1'}">否</c:if>
				</td>
				<td><fmt:formatNumber type="number" value="${cntItem.cntAmt}" /></td>
				<td>${cntItem.createDeptName}</td>
				<td>${cntItem.feeStartDate}</td>
				<td>${cntItem.feeEndDate}</td>
				<td>
					<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME"
						 conditionStr="CATEGORY_ID = 'CNT_DATE_FLAG'" selectedValue="${cntItem.dataFlag}"/>	
				</td>
				<td>
					<div class="detail">
					    <a href="#" onclick="accEntry('${cntItem.cntNum}');"></a>
					</div>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>