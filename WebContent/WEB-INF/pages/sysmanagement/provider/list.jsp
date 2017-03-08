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
<title>供应商查询</title>
<script>
function pageInit()
{   
	$(".ui-widget").find("input").attr("style","readonly:readonly");
	App.jqueryAutocomplete();
	
	var providerType = "${selectInfo.providerType}";
	if(!providerType)
	{
		providerType = "";
	}
	$("#providerType").val(providerType);
	$("#providerType").combobox();
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

function showDetail(providerCode,providerTypeName,providerName,providerAddr,payCondition,payMode,bankName,branchName,
		actName,actNo,actCurr,isMasterAct,actTypeText,bankInfo,bankCode,bankArea,ouCode,ouName) 
{  
	var form = $("#queryDetailForm");
 	$("#queryDetailForm #providerCode1").val(providerCode);
 	$("#queryDetailForm #providerType1").val(providerTypeName);
 	$("#queryDetailForm #providerName1").val(providerName);
 	$("#queryDetailForm #providerAddr1").val(providerAddr);
 	$("#queryDetailForm #payCondition1").val(payCondition);
 	$("#queryDetailForm #payMode1").val(payMode);
 	$("#queryDetailForm #bankName1").val(bankName);
 	$("#queryDetailForm #branchName1").val(branchName);
 	$("#queryDetailForm #actName1").val(actName);
 	$("#queryDetailForm #actNo1").val(actNo);
 	$("#queryDetailForm #actCurr1").val(actCurr);
 	$("#queryDetailForm #isMasterAct1").val(isMasterAct);
 	$("#queryDetailForm #actTypeText1").val(actTypeText);
 	$("#queryDetailForm #bankInfo1").val(bankInfo);
 	$("#queryDetailForm #bankCode1").val(bankCode);
 	$("#queryDetailForm #bankArea1").val(bankArea);
 	$("#queryDetailForm #ouCode1").val(ouCode);
 	$("#queryDetailForm #ouName1").val(ouName);
 	
 	//alert($("#id").val());
 	form.attr('action', '<%=request.getContextPath()%>/sysmanagement/provider/getInfo.do?<%=WebConsts.FUNC_ID_KEY%>=010705');
	App.submit(form);
}
//编辑供应商信息
function modify(providerCode) {
	var form = $('#queryDetailForm')[0];
	form.action = '<%=request.getContextPath()%>/sysmanagement/provider/preEdit.do?<%=WebConsts.FUNC_ID_KEY%>=010706';
	$('#tempForm #providerCode').val(providerCode);
	App.submit(form);
}
function ableCommon(button, url)
{
	if ($('input[type=checkbox][name=delIds]:checked').size() <= 0) {
		$( "<div>请选择要删除的数据</div>" ).dialog({
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
				}
			}
		});
		return;
	}
	
	$( "<div>确认要删除选定的数据?</div>" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submitForm(button, url);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}


</script>
</head>
<body>
<p:authFunc funcArray="010701,010702,010703,010709"/>
<form action="" method="post" id="queryDetailForm">
	<input type="hidden" id="providerCode1" name="providerCode"  class="base-input-text"/>
	<input type="hidden" id="providerType1" name="providerTypeName"  class="base-input-text"/>
	<input type="hidden" id="providerName1" name="providerName"  class="base-input-text"/>
	<input type="hidden" id="providerAddr1" name="providerAddr"  class="base-input-text"/>
	<input type="hidden" id="payCondition1" name="payCondition"  class="base-input-text"/>
	<input type="hidden" id="payMode1" name="payMode"  class="base-input-text"/>
	<input type="hidden" id="bankName1" name="bankName"  class="base-input-text"/>
	<input type="hidden" id="branchName1" name="branchName"  class="base-input-text"/>
	<input type="hidden" id="actName1" name="actName"  class="base-input-text"/>
	<input type="hidden" id="actNo1" name="actNo"  class="base-input-text"/>
	<input type="hidden" id="actCurr1" name="actCurr"  class="base-input-text"/>
	<input type="hidden" id="isMasterAct1" name="isMasterAct"  class="base-input-text"/>
	<input type="hidden" id="actTypeText1" name="actTypeText"  class="base-input-text"/>
	<input type="hidden" id="bankInfo1" name="bankInfo"  class="base-input-text"/>
	<input type="hidden" id="bankArea1" name="bankArea"  class="base-input-text"/>
	<input type="hidden" id="bankCode1" name="bankCode"  class="base-input-text"/>
	<input type="hidden" id="ouCode1" name="ouCode"  class="base-input-text"/>
	<input type="hidden" id="ouName1" name="ouName"  class="base-input-text"/>
	
	
	
</form>
<form  method="post" id="tempForm">

	<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				供应商查询
			</th>
		</tr>
		<tr class="col">
			<td class="tdLeft">供应商名称</td>
			<td class="tdRight">
            	<input type="text" id="providerName" name="providerName" value="${selectInfo.providerName}" class="base-input-text"  maxlength="100"/>&nbsp;&nbsp;
			</td>
			<td class="tdLeft">供应商地点</td>
			<td class="tdRight">
            	<input type="text" id="providerAddr" name="providerAddr" value="${selectInfo.providerAddr}" class="base-input-text"  maxlength="100"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>	
			<td class="tdLeft">供应商类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="providerType" name="providerType"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='PROVIDER_TYPE'" selectedValue="${actInfo.providerType}"/>
					</select>
				</div>	
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight">
			</td>
		</tr>
		<tr class="col">
			<td colspan="4" class="tdWhite">
			   <%--  <p:button funcId="010702" value="新增"/> --%>
				<p:button funcId="010701" value="查询"/> 
				<!-- 
				<p:button funcId="010709" value="删除" onclick="ableCommon"/>
				 -->
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
				<%--  <c:if test="${!empty providerList}">
			        <th><input type="checkbox" onclick="Tool.toggleCheck(this, 'delIds')"/></th>
			     </c:if> --%>
					<th width="20%">供应商名称</th>
					<th width="25%">供应商地点</th>
					<th width="20%">供应商类型</th>
					<th width="25%">财务中心编号-名称</th>
					<th width="10%">操作</th>
			</tr>
			 <c:forEach items="${providerList}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<%-- <td width="5%">
						<c:if test="${sedList.providerType=='IAP'}">
							<input name="delIds" type="checkbox" value="${sedList.providerCode}"/>
						</c:if>
						<c:if test="${sedList.providerType=='VENDOR' or sedList.providerType=='EMPLOYEE'}">
							<input name="disabledDel" type="checkbox" disabled="disabled">
						</c:if>
					</td> --%>
					<td><c:out value="${sedList.providerName}"></c:out></td>
					<td><c:out value="${sedList.providerAddr}"></c:out></td>
					<td><c:out value="${sedList.providerTypeName}"/></td>
					<td>(<c:out value="${sedList.ouCode}"/>-<c:out value="${sedList.ouName}"/>)</td>
					<td>
						<div class="detail"   style="padding-left: 25px">
								
					   	    	 <a href="#" onclick="showDetail('${sedList.providerCode}','${sedList.providerTypeName}',
					   	    	 '${sedList.providerName}','${sedList.providerAddr}','${sedList.payCondition}',
					   	    	 '${sedList.payMode}','${sedList.bankName}','${sedList.branchName}','${sedList.actName }',
					   	    	 '${sedList.actNo}','${sedList.actCurr}','${sedList.isMasterAct}','${sedList.actTypeText}',
					   	    	 '${sedList.bankInfo}','${sedList.bankCode}','${sedList.bankArea}','${sedList.ouCode}','${sedList.ouName}'
					   	    	 )" title="详情"></a>
					   	 </div>   	 
						<%-- <c:if test="${sedList.providerType=='IAP'}">
							 <!-- 
							<div class="update" style="padding-left: 17px" ><a href="#" onclick="modify('${sedList.providerCode}');" title='更新'></a></div>
							 -->
							<div class="detail"  >
					   	    	 <a href="#" onclick="showDetail('${sedList.providerCode}')" title="详情"></a>
							</div>
						</c:if>
						<c:if test="${sedList.providerType=='VENDOR' or sedList.providerType=='EMPLOYEE'}">
		
									
							
						</c:if> --%>
					</td>
				</tr>
			</c:forEach>
		<c:if test="${empty providerList}">
	    <tr>
		<td colspan="5" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	
		</table>
	</div>
</div>
</form>
<p:page/>
</body>
</html>