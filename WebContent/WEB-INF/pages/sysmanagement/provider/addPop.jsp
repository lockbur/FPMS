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
<meta name="decorator" content="dialog">
<base target="_self">
<title>供应商新增</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#ouCode").combobox();
	$("#ouName").combobox();
	$("#payCondition").combobox();
	$("#payMode").combobox();
	$("#actType").combobox();
}
function resetAll(){
// 	$("#providerTable input[id!='providerType']").val("");
    $("input[type='text']").val("");
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
<%-- function subAddPage(){
	App.submitShowProgress();
	var url="<%=request.getContextPath()%>/sysmanagement/provider/subAddPage.do?<%=WebConsts.FUNC_ID_KEY %>=010704";
	var returnValue = window.showModalDialog(url, null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		addToTableList(returnValue);
	}
	 
}
function addToTableList(returnValue){
	 //拿到paramList
	var actNo=returnValue[0];
	var actCurr=returnValue[1];
	var bankName=returnValue[2];
    if(bankName==''||bankName==null){
		bankName=' ';
	} 
	var branchName=returnValue[3];
	if(branchName==''||branchName==null){
		branchName=' ';
	}
	var actName=returnValue[4];
	if(actName==''||actName==null){
		actName=' ';
	}
	var isMasterAct=returnValue[5];
	var actType=returnValue[6];
	var actTypeText=returnValue[10];
	if(actType==''||actType==null){
		actType=' ';
		actTypeText='';
	}
	var bankCode=returnValue[7];
	if(bankCode==''||bankCode==null){
		bankCode=' ';
	}
	var bankInfo=returnValue[8];
	if(bankInfo==''||bankInfo==null){
		bankInfo=' ';
	}
	var bankArea=returnValue[9];
	if(bankArea==''||bankArea==null){
		bankArea=' ';
	}
	var actNos = $("#devList").find("input[name='actNo']");
	var actCurrs = $("#devList").find("input[name='actCurr']");
	//判断是否重复添加银行编号和币种
	if(actNos.length>0&&actCurrs.length>0){
		for(var i=0;i<actNos.length;i++){
			if((actNo == $(actNos[i]).val())&&(actCurr==$(actCurrs[i]).val())){
				$( "<div>该银行编号和币种已经添加!</div>" ).dialog({
					modal: true,
					dialogClass: 'dClass',
					buttons: {
						Ok: function() {
							$( this ).dialog( "close" );
						}
					}
				});
				return ;
			}
		}
	}
		var newTr ="<tr>"
	        +"<td width='10%'><input  type='hidden' name='actNo' class='base-input-text' style='width:100%' value='"+actNo+"'/>"+actNo+"</td>"
	        +"<td width='10%'><input  type='hidden' name='actCurr'  class='base-input-text'style='width:100%' value='"+actCurr+"'/>"+actCurr+"</td>"
	        +"<td width='10%'><input  type='hidden' name='bankName'   class='base-input-text'style='width:100%' value='"+bankName+"'/>"+bankName+"</td>"
	        +"<td width='10%'><input  type='hidden' name='branchName' class='base-input-text'style='width:100%'value='"+branchName+"'/>"+branchName+"</td>"
	        +"<td width='10%'><input  type='hidden' name='actName' class='base-input-text'style='width:100%' value='"+actName+"'/>"+actName+"</td>"
	        +"<td width='10%'><input  type='hidden' name='isMasterAct' class='base-input-text'style='width:100%'value='"+isMasterAct+"'/>"+isMasterAct+"</td>"
	        +"<td width='10%'><input  type='hidden' name='actType' class='base-input-text' style='width:100%' value='"+actType+"'/>"+actTypeText+"</td>"
	        +"<td width='10%'><input  type='hidden' name='bankCode' class='base-input-text' style='width:100%' value='"+bankCode+"'/>"+bankCode+"</td>"
	        +"<td width='10%'><input  type='hidden' name='bankInfo' class='base-input-text' style='width:100%' value='"+bankInfo+"'/>"+bankInfo+"</td>"
	        +"<td width='5%'><input  type='hidden' name='bankArea' class='base-input-text' style='width:100%' value='"+bankArea+"'/>"+bankArea+"</td>"
	        +"<td><a href='javascript:void(0);' onclick='delDev(this);'><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif'/></a></td>"
	        +"</tr>";
	   $("#addTr").after(newTr);
}
//删除行
function delDev(obj){
	$(obj).parent().parent().remove();
} --%>
function doValidate(){
	//提交前调用
    if(!App.valid("#reqtypeForm")){
	 return;
	}
	if(checkProviderName()){
		App.notySuccess("供应商已存在!");
		return false;
	}
	var proObj = {};
	if(submitProvider()){
		proObj.providerCode=$("#providerCode").val();
		proObj.providerName=$("#providerName").val();
		proObj.providerType=$("#providerType").val();
		proObj.bankName=$("#bankName").val();
		proObj.actNo=$("#actNo").val();
		proObj.actCurr=$("#actCurr").val();
		proObj.providerTaxRate=$("#taxRate").val();
	}
	if (window.opener) {  
		window.opener.returnValue = proObj;
	}
	else {       
		window.returnValue = proObj;
	}	
	window.close();
}	
function submitProvider(){
		var isSuccess=false;
		var data = {};
		data['providerName'] =  $("#providerName").val();
		data['payMode'] =  $("#payMode").val();
		data['bankName'] =  $("#bankName").val();
		data['bankCode'] =  $("#bankCode").val();
		data['actNo'] =  $("#actNo").val();
		data['actName'] =  $("#actName").val();
		data['actType'] =  $("#actType").val();
		data['providerCode'] =  $("#providerCode").val();
		data['taxRate'] =  $("#taxRate").val();
		var url = "sysmanagement/provider/addPop.do?VISIT_FUNC_ID=010714";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			isSuccess = data.isSuccess;
		});
		return isSuccess;
}
//检查供应商名称是否重复
function checkProviderName(){
	var list = false;
	var data = {};
	data['providerName'] =  $("#providerName").val();
	var url = "sysmanagement/provider/checkProviderName.do?VISIT_FUNC_ID=010712";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			list = data.isExist;
	});
	return list;
}
function getProvider(){
	var proObj = {};
		proObj.providerCode=$("#providerCode").val();
		proObj.providerName=$("#providerName").val();
		proObj.providerType=$("#providerType").val();
		proObj.bankName=$("#bankName").val();
		proObj.actNo=$("#actNo").val();
		proObj.actCurr=$("#actCurr").val();
		/* proObj.providerCode=; */
	 	//设置返回值；	  
		window.returnValue = proObj;	
		window.close();	
}
</script>
</head>
<body>
<p:authFunc funcArray="010714"/>
<form action="" method="post" id="reqtypeForm">
<input type="hidden" id="providerCode"  name="providerCode" value="${providerCode}">
<input type="hidden" id="providerType" 	name="providerType" value="IAP">
<input type="hidden" id="actCurr" 		name="actCurr" 		value="RMB">
<p:token/>
	<table id="providerTable">
		<tr>
			<th colspan="4">新增</th>
		</tr>
		<tr>
			<td class="tdLeft">供应商名称</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName"  class="base-input-text"  valid  errorMsg="请输入供应商。"  maxlength="100"/>&nbsp;&nbsp;
			</td>
			<td class="tdLeft">支付方式</td>
				<td class="tdRight">
					<div class="ui-widget">
					<select id="payMode" name="payMode" valid errorMsg="请选择支付方式。"  class="erp_cascade_select" >
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'"/>
					</select>
					</div>
				</td>
		</tr>
		 <tr>
			<td class="tdLeft">开户银行名称</td>
			<td class="tdRight">
				<%-- <forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" parentCheckFlag="true" leafOnlyFlag="true" triggerEle="#reqtypeForm bankName,availableOrgList::name" changeFun="writeCheckedValue" jsVarName="treePlugin1" rootNodeId="${currentUserOrg21Code}" /> --%>
				<%-- <forms:OrgSelPlugin suffix="1"  rootLevel="2" triggerEle="#reqtypeForm bankName,availableOrgList::name" jsVarName="treePlugin1"  /> --%>
				<input class="base-input-text" type="text" id="bankName" name="bankName" valid  readonly="readonly"/> 
				<input type="hidden" id="bank" name="bank" class="base-input-text" />
				<forms:OrgSelPlugin rootLevel="0" parentCheckFlag="true" leafOnlyFlag="true"  radioFlag="true" triggerEle="#reqtypeForm tr bankName,bank::name" />
			</td>
			<td class="tdLeft">开户行行号</td>
			<td class="tdRight">
				<input type="text" id="bankCode" name="bankCode"  class="base-input-text"  value="" maxlength="10"  valid errorMsg="请输入开户行行号。"/>&nbsp;&nbsp;	
			</td>
		<tr>
		<tr>
			<td class="tdLeft">银行账户编号</td>
			<td class="tdRight">
				<input type="text" id="actNo" name="actNo"  class="base-input-text"  valid errorMsg="请输入银行账户编号。" maxlength="19">&nbsp;&nbsp;
			</td>
			<td class="tdLeft">银行账户名称</td>
			<td class="tdRight">
				<input type="text" id="actName" name="actName"  class="base-input-text"  value=""  valid errorMsg="请输入银行账户名称。" maxlength="60"/>&nbsp;&nbsp;	
			</td>
		</tr>
		 <tr>
			<td class="tdLeft">账户类型</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="actType" name="actType"   class="erp_cascade_select" valid errorMsg="请选择账户类型。" >
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_ACCOUNT_TYPE'"  selectedValue="${actInfo.actType}"/>
					</select>
					</div>
			</td>
			<td class="tdLeft">税率</td>
			<td class="tdRight">
				<input type="text" id="taxRate" name="taxRate"  class="base-input-text"  value=""  valid errorMsg="请输入税率。" maxlength="9"/>&nbsp;&nbsp;	
			</td>
		<tr>
		 <!--操作按钮-->
		<tr>
			<td colspan="11" style="border:none;">
			<br/>
				<div style="text-align:center;" >
					<!-- <input type="button" value="提交"  > -->
					 <p:button funcId="010714" value="提交" />
				    <input type="button" value="重置"  onclick="resetAll()">
	  			    <input type="button" value="关闭" onclick="window.close()" />
					<br>
				</div>
				<br>
			</td>
		</tr>
	</table>
    
</form>
</body>
</html>