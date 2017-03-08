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
<title>付款新增</title>
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

//合同付款明细信息
function deail(cntNum){
	var form=$("#queryDetailForm")[0];
	form.action="<%=request.getContextPath()%>/pay/payAdd/deail.do?<%=WebConsts.FUNC_ID_KEY%>=03030301";
	$("#queryDetailForm #cntNum1").val(cntNum);
	App.submit(form);
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

function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray="030303"/>
<form action="" method="post" id="queryDetailForm">
	<input type="hidden" id="cntNum1" name="cntNum"  class="base-input-text"/>
</form>
<form action="" method="post" id="queryForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
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
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="hidden" id="providerCode"  name="providerCode" value="${selectInfo.providerCode}" class="base-input-text"/>
				<input type="text"  id="providerName" name="providerName" value="${selectInfo.providerName}"  class="base-input-text" maxlength="30"/>
				<a href="#" onclick="providerType();" title="供应商查询">
					<img border="0px;" height="100%;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
			<td class="tdLeft">签订日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate"  maxlength='10' readonly="readonly" value="${selectInfo.befDate}" class="base-input-text" style="width:35%;"/>
				至
				<input type="text" id="aftDate" name="aftDate"  maxlength='10' readonly="readonly" value="${selectInfo.aftDate}" class="base-input-text" style="width:35%;"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">付款机构</td>
			<td class="tdRight">
				<%-- <input type="text" name="createDeptName"  class="base-input-text"/>
				<input type="hidden" name="createDept" class="base-input-text"/>
				<forms:OrgSelPlugin rootLevel="0" parentCheckFlag="true" leafOnlyFlag="true" dialogFlag="true" radioFlag="false" triggerEle="#queryForm tr createDeptName,createDept::name" initValue="${selectInfo.createDept}"/> --%>
				<%-- <forms:OrgSelPlugin rootLevel="0" initValue="${selectInfo.createDept}" jsVarGetValue="createDept"/> --%>
				<forms:OrgSelPlugin suffix="c" rootNodeId="${org1Code}" initValue="${selectInfo.createDept}" jsVarGetValue="createDept" parentCheckFlag="false"/>
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight"></td>
		</tr>	
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="030303" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList">
		<tr>
			<th width='13%'>合同号 </th>
			<th width='10%'>合同事项    </th>
			<th width='10%'>合同类型    </th>
			<th width='9%'>供应商  </th>
			<th width='10%'>合同总金额(元)    </th>
			<th width='10%'>签订日期    </th>
			<th width='10%'>合同状态  </th>
			<th width='12%'>录入责任中心</th>
			<th width='10%'>录入时间    </th>
			<th width="6%">操作</th>
		</tr>
		<c:if test="${!empty payList}">
			<c:forEach items="${payList}" var="cntItem">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc"><a href="javascript:void(0);" onclick="gotoCntDtl('${cntItem.cntNum}');" class="text_decoration">${cntItem.cntNum }</a></td>
					<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
					<td class="tdc">${cntItem.cntTypeName}<c:if test="${cntItem.isOrder == 1}">/非订单</c:if><c:if test="${cntItem.isOrder == 0}">/订单</c:if></td>
					<td>${cntItem.providerName}</td>
					<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntAllAmt}" minFractionDigits="2"/></td>
					<td class="tdc">${cntItem.signDate}</td>
					<td>${cntItem.dataFlagName}</td>
					<td>${cntItem.dutyName}</td>
					<td class="tdc">${cntItem.createDate}</td>
					<td>
						<div class="update">
							<a href="#" onclick="deail('${cntItem.cntNum}');" title="新增付款"></a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty payList}">
			<tr><td style="text-align: center;" colspan="10"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>