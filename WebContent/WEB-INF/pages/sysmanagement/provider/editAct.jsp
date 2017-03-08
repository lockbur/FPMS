<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<title>添加银行信息</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#actType").combobox();
	$("#bankArea").combobox();
	var isMasterActVal="${actInfo.isMasterAct}";
	radioInit($("#isMasterActDiv"),"isMasterAct",isMasterActVal);
}
//单选按钮初始化
function radioInit(_obj, _name, _val){
	if(!_val)
	{
		_val = $(_obj).find("input[name^='"+ _name +"']:eq(0)").val();
	}
	$(_obj).find("input[name^='"+ _name +"']").attr("checked", false);
	$(_obj).find("input[name^='"+ _name +"']").each(function(){
		if( $(this).val() == _val ){
			$(this).attr("checked", true);
			$(this).parent().find("label[for='" + $(this).attr("id") +"']").click();
		}
	});
}
function getReturnValue(){
	var actNo=$("#actNo").val();
	var actCurr=$("#actCurr").val();
	var bankName=$("#bankName").val();
	var branchName=$("#branchName").val();
	var actName=$("#actName").val();
	var isMasterAct=$(":radio[name='isMasterAct']:checked").val();
	var actType=$("#actType").val();
	var bankCode=$("#bankCode").val();
	var bankInfo=$("#bankInfo").val();
	var bankArea=$("#bankArea").val();
	var actTypeText=$("#actType option:selected").text();
	 //如果为空不关闭弹窗
	if(!App.valid("#subAddForm")){
		 return;
		}
	else{
		var actObj = [actNo,actCurr,bankName,branchName,actName,isMasterAct,actType,bankCode,bankInfo,bankArea,actTypeText];
		window.returnValue = actObj;
		window.close();	
	}
}
function resetAll(){
    $("#actNo").val("${actInfo.actNo}");
	$("#actCurr").val("${actInfo.actCurr}");
	$("#bankName").val("${actInfo.bankName}");
	$("#branchName").val("${actInfo.branchName}");
	$("#actName").val("${actInfo.actName}");
	selectInit( $("#actType"), "${actInfo.actType}");
	selectInit( $("#bankArea"), "${actInfo.bankArea}");
	$("#bankCode").val("${actInfo.bankCode}");
	$("#bankInfo").val("${actInfo.bankInfo}");
	$("#bankArea").val("${actInfo.bankArea}"); 
	var isMasterAct="${actInfo.isMasterAct}";
	//恢复单选按钮值
	radioInit($("#isMasterActDiv"),"isMasterAct", isMasterAct);
}

//下拉列表初始化
function selectInit(obj, _selVal){
	var _newSel = $(obj).clone();
	$(obj).parent().append(_newSel);
	$(obj).remove();
	$(_newSel).val(_selVal);
	$(_newSel).combobox();
}
</script>
</head>
<body>
<p:authFunc funcArray=""/>
 <form action="" id="subAddForm">
	<table>
		<tr>
			<th colspan="4">修改</th>
		</tr>
		<tr>
			<td class="tdLeft">银行账户编号</td>
			<td class="tdRight">
				<input type="text" id="actNo" name="actNo" valid errorMsg="请输入银行账户编号。"  class="base-input-text"  value="${actInfo.actNo}" />&nbsp;&nbsp;	
			</td>
			<td class="tdLeft">银行账户名称</td>
			<td class="tdRight">
				<input type="text" id="actName" name="actName"  class="base-input-text"  value="${actInfo.actName}" />&nbsp;&nbsp;	
			</td>
		</tr>
        <tr>
			<td class="tdLeft">银行名称</td>
			<td class="tdRight">
				<input type="text" id="bankName" name="bankName" valid errorMsg="请输入银行名称。" class="base-input-text"  value="${actInfo.bankName}" />&nbsp;&nbsp;	
			</td>
			<td class="tdLeft">分行名称</td>
			<td class="tdRight">
				<input type="text" id="branchName" name="branchName"  class="base-input-text"  valid errorMsg="请输入分行名称。" value="${actInfo.branchName}"/>&nbsp;&nbsp;	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">银行账户币种</td>
			<td class="tdRight">
				<input type="text" id="actCurr" name="actCurr" valid errorMsg="请输入银行账户币种。"  class="base-input-text"   value="${actInfo.actCurr}"/>&nbsp;&nbsp;	
			</td>
			<td class="tdLeft">是否主要帐号</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isMasterActDiv">
					<label for="flag1" onclick="App.radioCheck(this, 'isMasterActDiv');">是</label><input id="flag1" type="radio" name="isMasterAct"  value="Y"  />
					<label for="flag0" onclick="App.radioCheck(this, 'isMasterActDiv');">否</label><input id="flag0" type="radio" name="isMasterAct" value="N" />
				</div>
			</td>
		</tr>
		 <tr>
			<td class="tdLeft">账户类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="actType" name="actType"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_ACCOUNT_TYPE'"  selectedValue="${actInfo.actType}"/>
					</select>
					</div>
			</td>
			<td class="tdLeft">银行地区码</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="bankArea" name="bankArea"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_BANK_AREA_CODE'" selectedValue="${actInfo.bankArea}"/>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">开户行行号</td>
			<td class="tdRight">
				<input type="text" id="bankCode" name="bankCode"  class="base-input-text" value="${actInfo.bankCode}"  />&nbsp;&nbsp;	
			</td>
			<td class="tdLeft">开户行详细信息</td>
			<td class="tdRight" >
				<input type="text" id="bankInfo" name="bankInfo"  class="base-input-text"  value="${actInfo.bankInfo}" />&nbsp;&nbsp;	
			</td>
			
		</tr>
		<tr>
		<td colspan="4" style="border:none;">
		<br/>
			<div style="text-align:center;" >
				<input type="button"  value="选择"  onclick="getReturnValue();" />
			    <input type="button" value="重置"  onclick="resetAll()">
				<input type="button" value="关闭" onclick="window.close()">
				<br>
			</div>
			<br>
		</td>
	</tr>
	</table>
</form>
</body>
</html>