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
<title>供应商新增</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#ouCode").combobox();
	$("#ouName").combobox();
	$("#payCondition").combobox();
	$("#payMode").combobox();
}
function resetAll(){
	$("#providerTable input[id!='providerType']").val("");
}
function subAddPage(){
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
}
function doValidate(){
	//提交前调用
    if(!App.valid("#reqtypeForm")){
	 return;
	}
	var devTrLength=$("#devList").find("tr").length;
	if(devTrLength<4){
		App.notySuccess("请至少添加一条银行信息!");
		return false;
	}
	if(checkProviderName()){
		App.notySuccess("供应商已存在!");
		return false;
	}
	return true;
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
</script>
</head>
<body>
<p:authFunc funcArray="010703"/>
<form action="" method="post" id="reqtypeForm">
<p:token/>
	<table id="providerTable">
		<tr>
			<th colspan="4">新增</th>
		</tr>
		<tr>
			<td class="tdLeft">供应商名称</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName"  class="base-input-text"  valid  errorMsg="请输入供应商。"  maxlength="150"/>&nbsp;&nbsp;
			</td>
			<td class="tdLeft">供应商类型</td>
			<td class="tdRight">
            	<input type="text" id="providerType" name="providerType" value="IAP" readonly="readonly" class="base-input-text">
			</td>
		</tr>
         <tr>
			<td class="tdLeft">供应商地点</td>
			<td class="tdRight" colspan="3">
				<input type="text" id="providerAddr" name="providerAddr"  class="base-input-text"  valid  errorMsg="请输入供应商地点。" maxlength="15" />&nbsp;&nbsp;	
			</td>
		<tr>
		<tr>
			<td class="tdLeft">付款条件</td>
			<td class="tdRight">
				<div class="ui-widget">
				<select id="payCondition" name="payCondition" valid errorMsg="请选择付款条件。"  class="erp_cascade_select">
					<option value="">-请选择-</option>
					<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_CONDITION'"/>
				</select>
				</div>
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
			<td class="tdLeft">所属财务中心代码</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="ouCode" name="ouCode" valid errorMsg="请选择财务中心代码。"  class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="${ouCode}">${ouCode}</option>
					</select>
				</div>
			</td>
			<td class="tdLeft">所属财务中心名称</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="ouName" name="ouName" valid errorMsg="请选择财务中心名称。"  class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="${ouName}">${ouName}</option>
					</select>
				</div>
			</td>
		</tr>
	</table>
	<br>
	<table id="devList" >
		<caption>
		银行卡号列表
		</caption>
		<tr>
		<td class="tdLeft" colspan="11">
			<input type="button" onclick="subAddPage();" value="增加记录"/>
		</td>
		</tr>
		<tr id="addTr">
			<th width="10%">银行账户编号</th>
			<th width="10%">银行账户币种 </th>
			<th width="10%">银行名称</th>
			<th width="10%">分行名称</th>
			<th width="10%">银行账户名称</th>
			<th width="10%">是否主要帐号 </th>
			<th width="10%">账户类型 </th>
			<th width="10%">开户行行号</th>
			<th width="10%">开户行详细信息</th>
			<th width="5%"> 银行地区码</th>
			<th width="5%">操作</th>
		</tr>
     <!--操作按钮-->
	<tr>
		<td colspan="11" style="border:none;">
		<br/>
			<div style="text-align:center;" >
				<p:button funcId="010703" value="提交"/>
			    <input type="button" value="重置"  onclick="resetAll()">
  			     <input type="button" value="返回" onclick="backToLastPage('${uri}')">
				<br>
			</div>
			<br>
		</td>
	</tr>
	</table>
</form>
</body>
</html>