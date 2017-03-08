<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="json/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>供应商账号列表</title>
<style type="text/css">

</style>
<script type="text/javascript">
function getProvider(){	
	var nodeTR = null; 
	var checkCUS = document.getElementsByName("checkCus");
	if(checkCUS != null)
	{
		if(checkCUS.length == null)
		{	
	 		if(checkCUS.checked)	
	 		 	nodeTR = checkCUS.parentNode.parentNode;	   
		 }
		 else
		 {
			  for(var i = 0 ; i < checkCUS.length; i++)
			  {
				   if(checkCUS[i].checked)
					   
				   {			
					   nodeTR = checkCUS[i].parentNode.parentNode;
				   }
			  }
			 
		  }
		  if(nodeTR == null)
		  {
			  App.notyError("请选择供应商！");
			    return false;
		  }
	   		var node = nodeTR.children;
	   		var proObj = {};
	   		proObj.providerName=node[0].innerText;
	   		proObj.providerAddr=node[1].innerText;
	   		proObj.providerType=node[14].children[0].value;
	   		proObj.bankName=node[3].innerText;
	   		proObj.actNo=node[4].innerText;
	   		proObj.actCurr=node[5].innerText;
	   		proObj.providerCode=node[6].children[0].value;
	   		proObj.providerTaxRate=node[7].children[0].value;
	   		proObj.actName=node[8].children[0].value;
	   		proObj.bankInfo=node[9].children[0].value;
	   		proObj.bankCode=node[10].children[0].value;
	   		proObj.bankArea=node[11].children[0].value;
	   		proObj.actType=node[12].children[0].value;
	   		proObj.payMode=node[13].children[0].value;
	   		proObj.providerAddrCode=node[15].children[0].value;
	   		var compare="${selectInfo.compare}";
	   		if(compare=='common'){
	   			art.dialog.data('proObj',proObj);
	   		}
	   		else if(compare=='employee'){
	   			art.dialog.data('empProObj',proObj);
	   		}
	   		else if(compare=='jf'){
	   			art.dialog.data('jfProObj',proObj);
	   		}
	   	}
		art.dialog.close();	   			 		   		 
}
function pageInit(){
	App.jqueryAutocomplete();
	$("#providerTypePop").combobox();
	$("#actTypePop").combobox();
	//rowspanTableTd("tableListRadio", "1", "1");
	//rowspanTableTd("tableListRadio", "0", "1");
	//var tableCombine=new TableCombine();
	//tableCombine.rowspanTable("tableListRadio", 1, null, 0, 2, null, null);
}

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
$(function(){
	$("table tr[id='notIAPTr']").click(function(){
		$("table tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
});
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
function addProviderAct(){
	//window.close();	
	location.href="<%=request.getContextPath()%>/sysmanagement/provider/preAddPop.do?<%=WebConsts.FUNC_ID_KEY %>=010713";
	<%-- var proObj = window.showModalDialog("<%=request.getContextPath()%>/sysmanagement/provider/preAddPop.do?<%=WebConsts.FUNC_ID_KEY %>=010713", null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;");
	window.returnValue = proObj; --%>	
}
</script>
</head>
<body>
<p:authFunc funcArray="010702,010711"/>
<form action="" id="tempForm" method="post">
<input type="hidden" name="providerType" value="${selectInfo.providerType}">
<input type="hidden" name="providerCode" value="${selectInfo.providerCode}">
<input type="hidden" name="compare" value="${selectInfo.compare}">
   	<table  >
   		<tr class="collspan-control">
   			<th colspan="4">
				供应商账户列表
			<input type="hidden"  id="flag" name="flag" value="${selectInfo.flag}">	
			</th>
		</tr>
		<tr>
			<td width="20%" class="tdLeft">供应商名称</td>
			<td width="30%" class="tdRight">
				<input type="text" id="providerName" name="providerName"  class="base-input-text"  value="${selectInfo.providerName}" maxlength="100"/>&nbsp;&nbsp;
			</td>
			<td width="20%" class="tdLeft">供应商类型</td>
			<td width="30%" class="tdRight">
				<c:if test="${selectInfo.flag=='1'}">
					<div class="ui-widget">
						<select id="providerTypePop" name="providerTypePop"   class="erp_cascade_select" >
							<option value="">-请选择-</option>
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='PROVIDER_TYPE' AND PARAM_VALUE ='VENDOR'" selectedValue="${selectInfo.providerTypePop}" />
						</select>
					</div>
				</c:if>
				<c:if test="${selectInfo.flag=='2'}">
					<div class="ui-widget">
						<select id="providerTypePop" name="providerTypePop"   class="erp_cascade_select" >
							<option value="">-请选择-</option>
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='PROVIDER_TYPE' AND PARAM_VALUE !='EMPLOYEE'" selectedValue="${selectInfo.providerTypePop}" />
						</select>
					</div>
				</c:if>
				<c:if test="${selectInfo.flag=='' or selectInfo.flag==null}">
						<div class="ui-widget">
							<select id="providerTypePop" name="providerTypePop"   class="erp_cascade_select" >
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='PROVIDER_TYPE'" selectedValue="${selectInfo.providerTypePop}" />
							</select>
						</div>
				</c:if>
			</td>	
		</tr>
		<tr>
			<td width="20%" class="tdLeft">银行账户编号</td>
			<td width="30%" class="tdRight">
				<input type="text" id="actNoPop" name="actNoPop"  class="base-input-text"  value="${selectInfo.actNoPop}"/>&nbsp;&nbsp;
			</td>
			<td width="20%" class="tdLeft">供应商银行帐户类型</td>
			<td width="30%" class="tdRight">
				<div class="ui-widget">
					<select id="actTypePop" name="actTypePop"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_ACCOUNT_TYPE'"  selectedValue="${selectInfo.actTypePop}"/>
					</select>
			    </div>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tdLeft">供应商开户银行</td>
			<td width="30%" class="tdRight">
				<input type="text" id="bankNamePop" name="bankNamePop"  class="base-input-text"  value="${selectInfo.bankNamePop}"/>&nbsp;&nbsp;
			</td>
			<td width="20%" class="tdLeft">供应商开户行行号</td>
			<td width="30%" class="tdRight">
				<input type="text" id="bankCodePop" name="bankCodePop"  class="base-input-text"  value="${selectInfo.bankCodePop}"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center" style="text-align: center;" class="tdWhite">
				<p:button funcId="010711" value="查找"/>
				<input type="button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
	<br>
	<table  id="tableListRadio" class="tableList">
			<tr>    
					<th width="20%">供应商名称</th>
					<th width="15%">供应商地点</th>
					<th width="16%">供应商类型</th>
					<th width="18%">供应商开户银行</th>
					<th width="15%" >供应商银行帐号</th>
					<th width="10%">币别</th>
					<th width="10%">选择</th>
			</tr>
		<c:if test="${empty providerActList}">
			<tr>
				<td colspan="7" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		 <c:forEach items="${providerActList}" var="list" varStatus="status">
		 		<tr ondblclick="return getProvider();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor: pointer" class="trOther" id="notIAPTr">
					<td><c:out value="${list.providerName}"/></td>
					<td><c:out value="${list.providerAddr}"/></td>
					<td><c:out value="${list.providerTypeName}"/></td>
					<td><c:out value="${list.bankNamePop}"/></td>
					<td><c:out value="${list.actNoPop}"/></td>
					<td><c:out value="${list.actCurrPop}"/></td>
					<td>
						<input type="radio" name="checkCus"  value="${list.providerCode}"/>
					</td>
					<td style="display: none">
						<input type="hidden" id="taxRate" value="${list.taxRate}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="actName" value="${list.actName}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="bankInfo" value="${list.bankInfo}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="bankCode" value="${list.bankCode}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="bankArea" value="${list.bankArea}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="actType" value="${list.actType}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="payMode" value="${list.payMode}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="providerType" value="${list.providerType}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="providerAddrCode" value="${list.providerAddrCode}"></input>
					</td>
				</tr>
		</c:forEach>
	</table>
	</form>
	<p:page/>
	<br><br><br>
			<input type="button" name="button" value="确定" class="button" onclick="return getProvider();" />
			<input type="button" value="关闭" onclick="art.dialog.close();" />
</body>
</html>