<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>税码维护列表</title>


<script type="text/javascript">


//查询列表
function listS()
{
 	var form=$("#sFormSearch")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/referencespecial/specialList.do?<%=WebConsts.FUNC_ID_KEY%>=011202";
	App.submit(form);
}

//详情
function modify(taxCode){
	$("#taxCodeHidden").val(taxCode);
	var form=$("#special")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/referencespecial/preTaxCodeUpdate.do?<%=WebConsts.FUNC_ID_KEY%>=01120304";
	App.submit(form);
}

//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$("#taxRate").val("");
	$(":selected").prop("selected",false);
	$("#statusDiv1").find("label").eq("").click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
};
function clearNoNum(obj){
obj.value=obj.value.replace(/[^\d.]/g,"");
obj.value=obj.value.replace(/^\./g,"");
obj.value=obj.value.replace(/\.{2,}/g,".");
obj.value=obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

function doValidate(){
	var taxTate=$("#taxRateShow").val();
	var newTaxTate=($("#taxRateShow").val())/100;
	if(taxTate!=null && taxTate!=''){
		$("#taxRate").val(newTaxTate);
		if(newTaxTate=='0'){
			$("#isZero").val("Y");
		}
	}
	else{
		$("#taxRate").val("");
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray="01120301,011203"/>
<form action="" method="post" id="special">
	<input type="hidden" name="taxCode" id="taxCodeHidden">
	
</form>
<form action="" method="post" id="sFormSearch">
<input type="hidden" name="isZero" id="isZero">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				税码查询 
			</th>
		</tr>
		<tr>
			<td class="tdLeft" >税码</td>
			<td class="tdRight" >
				<input type="text" id="taxCode" style="width: 140px" name="taxCode" value="${t.taxCode}" class="base-input-text" maxlength="15" />				
			</td>
			<td class="tdLeft" >税率</td>
			<td class="tdRight">
				<c:if test="${t.isZero=='' or t.isZero==null}">
					<input type="text" style="width:80px" onkeyup="clearNoNum(this)" id="taxRateShow" name="taxRateShow"  <c:if test="${t.taxRate!='' and t.taxRate!=null}">value="<fmt:formatNumber type='number' value='${t.taxRate*100}' minFractionDigits="2"/>"</c:if>  class="base-input-text" maxlength="9"/>%	
				</c:if>
				<c:if test="${t.isZero=='Y'}">
				    <input type="text" style="width:80px" onkeyup="clearNoNum(this)" id="taxRateShow" name="taxRateShow"  value="0.00" class="base-input-text" maxlength="9"/>%
				</c:if>
				<input type="hidden" id="taxRate" style="width: 140px" name="taxRate" value="${t.taxRate*100}" class="base-input-text" maxlength="15" />				
			</td>		
		</tr>
		<tr>
			<td class="tdLeft" >进项税核算码</td>
			<td class="tdRight">
				<input type="text" style="width: 30px" id="cglCode" name="cglCode" value="${t.cglCode}"  class="base-input-text" maxlength="4"/>	
			</td>
			<td class="tdLeft" ></td>
			<td class="tdRight">
			</td>
		</tr>	
		<tr>
			<td class="tdLeft" >是否可抵扣</td>
			<td class="tdRight" colspan="3">
				<div class="base-input-radio" id="statusDiv1" style="width: 350px;">
					<label for="all" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${t.deductFlag=='' or t.deductFlag==null}">class="check-label"</c:if>>全部</label>
					<input type="radio" id="all" name="deductFlag" value="" <c:if test="${t.deductFlag=='' or t.deductFlag==null}">checked="checked"</c:if>>
					<label for="yes" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${t.deductFlag=='Y'}">class="check-label"</c:if>>可抵扣</label>
					<input type="radio" id="yes" name="deductFlag" value="Y" <c:if test="${t.deductFlag=='Y'}">checked="checked"</c:if>>
					<label for="no" onclick="App.radioCheck(this,'statusDiv1')" <c:if test="${t.deductFlag=='N'}">class="check-label"</c:if>>不可抵扣</label>
					<input type="radio" id="no" name="deductFlag" value="N" <c:if test="${t.deductFlag=='N'}">checked="checked"</c:if>>
				</div>
			</td>	
		</tr>	
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="011203" value="查找"/>		
				<input type="button" value="重置" onclick="initEvent();"/>	
				<p:button funcId="01120301" value="新增"/>			
			</td>
		</tr>
	</table>
	
</form>
<form action="" method="post" id="sForm">
	<br/>	
	<table id="listTab" class="tableList">		
		<tr>		
			<th width="20%">税码</th>
			<th width="15%">税率</th>
			<th width="15%">是否可抵扣</th>
			<th width="20%">进项税核算码</th>
			<th width="20%">是否产生税行</th>
			<th width="10%">操作</th>
			
		</tr>
		<c:forEach items="${tList}" var="tl" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				<td>${tl.taxCode}</td>								
				<td><fmt:formatNumber type="number" value="${tl.taxRate*100}" minFractionDigits="2"/>%</td>	
				<td>${tl.deductFlagName}</td>	
				<td>${tl.cglCode}</td>	
				<td>${tl.hasTaxrowName}</td>					
				<td >
					<div class="update" >
						<a href="#" onclick="modify('${tl.taxCode}');" title="<%=WebUtils.getMessage("button.update")%>"></a>
					</div>
				</td>	
			</tr>
		</c:forEach>
		<c:if test="${empty tList}">
	    <tr>
		<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	
	</table>

</form>
<p:page/>
</body>
</html>