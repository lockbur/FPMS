<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改供应商信息</title>
<script type="text/javascript">
//删除行
function delDev(obj){
	var trLength=$("#devList tr");
	if(trLength.length<4||trLength.length==4){
		$( "<div>确认要删除最后一条数据?</div>" ).dialog({
			resizable: false,
			height:140,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					$(obj).parent().parent().remove();
					$( this ).dialog( "close" );
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	else{
		$(obj).parent().parent().remove();
	}
}
function pageInit(){
	App.jqueryAutocomplete();
	var payCondition = "${bean.payCondition}";
	if(!payCondition)
	{
		payCondition = "";
	}
	$("#payCondition").val(payCondition);
	
	var payMode = "${bean.payMode}";
	if(!payMode)
	{
		payMode = "";
	}
	$("#payMode").val(payMode);
	var ouCode="${bean.ouCode}";
	if(!ouCode){
		ouCode="";
	}
	$("#ouCode").val(ouCode);
	var ouName="${bean.ouName}";
	if(!ouName){
		ouName="";
	}
	$("#ouName").val(ouName);
	
	$("#providerType").combobox();
	$("#payCondition").combobox();
	$("#payMode").combobox();
	$("#ouCode").combobox();
	$("#ouName").combobox();
}
function subEditActPage(obj,actNo,actCurr,bankName,branchName,actName,isMasterAct,actType,bankCode,bankInfo,bankArea){
	App.submitShowProgress();
	var url="<%=request.getContextPath()%>/sysmanagement/provider/subEditActPage.do?<%=WebConsts.FUNC_ID_KEY %>=010707&actNo="+actNo+"&actCurr="+actCurr+"&bankName="+bankName+"&branchName="+branchName+"&actName="+actName+"&isMasterAct="+isMasterAct+"&actType="+actType+"&bankCode="+bankCode+"&bankInfo="+bankInfo+"&bankArea="+bankArea;
	var returnValue = window.showModalDialog(encodeURI(url), null, "dialogHeight=600px;dialogWidth=1000px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		updateToTableList(obj,returnValue);
	}
}
function updateToTableList(obj,returnValue){
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
	trObj = $(obj).parent().parent().parent();
	//判断是否修改为已经存在的银行编号和币种
	var actNoNext=$(obj).parent().parent().parent().siblings().find("input[id='actNos']");
	var actCurrNext=$(obj).parent().parent().parent().siblings().find("input[id='actCurrs']");
	if(actNoNext.length>0&&actCurrNext.length>0){
		for(var i=0;i<actNoNext.length;i++){
			if((actNo == $(actNoNext[i]).val())&&(actCurr==$(actCurrNext[i]).val())){
				$( "<div>该银行编号和币种已经存在!</div>" ).dialog({
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
	$(trObj).find("input[id='actNos']").parent().find("span").html(actNo);
	$(trObj).find("input[id='actNos']").val(actNo);
	
	$(trObj).find("input[id='actCurrs']").parent().find("span").html(actCurr);
	$(trObj).find("input[id='actCurrs']").val(actCurr);
	
	$(trObj).find("input[id='bankNames']").parent().find("span").html(bankName);
	$(trObj).find("input[id='bankNames']").val(bankName);
	
	$(trObj).find("input[id='branchNames']").parent().find("span").html(branchName);
	$(trObj).find("input[id='branchNames']").val(branchName);
	
	$(trObj).find("input[id='actNames']").parent().find("span").html(actName);
	$(trObj).find("input[id='actNames']").val(actName);
	
	$(trObj).find("input[id='isMasterActs']").parent().find("span").html(isMasterAct);
	$(trObj).find("input[id='isMasterActs']").val(isMasterAct);
	
	
	$(trObj).find("input[id='actTypes']").parent().find("span").html(actTypeText);
	$(trObj).find("input[id='actTypes']").val(actType);
	
	$(trObj).find("input[id='bankCodes']").parent().find("span").html(bankCode);
	$(trObj).find("input[id='bankCodes']").val(bankCode);
	
	$(trObj).find("input[id='bankInfos']").parent().find("span").html(bankInfo);
	$(trObj).find("input[id='bankInfos']").val(bankInfo);
	
	$(trObj).find("input[id='bankAreas']").parent().find("span").html(bankArea);
	$(trObj).find("input[id='bankAreas']").val(bankArea);
	//设置修改方法
	var objParent = $(obj).parent();
	$(obj).remove();
	$(objParent).append('<a href="#" onclick="subEditActPage(this,'+"\'"+actNo+"\',\'"+actCurr+"\',\'"+bankName+"\',\'"+branchName+"\',\'"+actName+"\',\'"+isMasterAct+"\',\'"+actType+"\',\'"+bankCode+"\',\'"+bankInfo+"\',\'"+bankArea+'\');" title="修改"></a>');
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
	var isMasterActText='';
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
	    
	    var str = 'subEditActPage(this,"'+actNo+'","'+actCurr+'","'+bankName+'","'+branchName+'","'+actName+'","'+isMasterAct+'","'+actType+'","'+bankCode+'","'+bankInfo+'","'+bankArea+'");';
		var newTr ="<tr>"
	        +"<td width='10%'><span>"+actNo+"</span><input  type='hidden' id='actNos' name='actNo' class='base-input-text' style='width:100%' value='"+actNo+"'/></td>"
	        +"<td width='10%'><span>"+actCurr+"</span><input  type='hidden' id='actCurrs' name='actCurr'  class='base-input-text'style='width:100%' value='"+actCurr+"'/></td>"
	        +"<td width='10%'><span>"+bankName+"</span><input  type='hidden' id='bankNames' name='bankName' class='base-input-text'style='width:100%' value='"+bankName+"'/></td>"
	        +"<td width='7%'><span>"+branchName+"</span><input  type='hidden' id='branchNames' name='branchName' class='base-input-text'style='width:100%'value='"+branchName+"'/></td>"
	        +"<td width='10%'><span>"+actName+"</span><input  type='hidden' id='actNames' name='actName' class='base-input-text'style='width:100%' value='"+actName+"'/></td>"
	        +"<td width='5%'><span>"+isMasterAct+"</span><input  type='hidden' id='isMasterActs' name='isMasterAct' class='base-input-text'style='width:100%'value='"+isMasterAct+"'/></td>"
	        +"<td width='10%'><span>"+actTypeText+"</span><input  type='hidden' id='actTypes' name='actType' class='base-input-text' style='width:100%' value='"+actType+"'/></td>"
	        +"<td width='10%'><span>"+bankCode+"</span><input  type='hidden' id='bankCodes' name='bankCode' class='base-input-text' style='width:100%' value='"+bankCode+"'/></td>"
	        +"<td width='10%'><span>"+bankInfo+"</span><input  type='hidden' id='bankInfos' name='bankInfo' class='base-input-text' style='width:100%' value='"+bankInfo+"'/></td>"
	        +"<td width='5%'><span>"+bankArea+"</span><input  type='hidden' id='bankAreas' name='bankArea' class='base-input-text' style='width:100%' value='"+bankArea+"'/></td>"
	        +"<td width='8%'><div class='update' style='float: left;'><a href='#' onclick='"+str+"'></a></div><a href='javascript:void(0);' onclick='delDev(this);'><img border='0' alt='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif'/></a></td>"
	        +"</tr>";
	   $("#addTr").after(newTr); 
}
function doValidate(){
	//提交前调用
    if(!App.valid("#reqtypeForm")){
	 return;
	}
	var devTrLength=$("#devList").find("tr").length;
	if(devTrLength<4){
		App.notyError("请至少添加一条银行信息!");
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
<p:authFunc funcArray="010708"/>
<form action="<%=request.getContextPath()%>/sysmanagement/provider/editSubmit.do" method="post" id="reqtypeForm">
	<p:token/>
	<input type="hidden" name="providerCode" value="${bean.providerCode}">
	<table >
		<tr>
			<th colspan="4">修改</th>
		</tr>
		<tr>
			<td class="tdLeft"><span class="spanFont">供应商名称</span></td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName"  class="base-input-text"  value="${bean.providerName}" valid errorMsg="请输入供应商名称。"/>&nbsp;&nbsp;
			</td>
			<td class="tdLeft"><span class="spanFont">供应商地点</span></td>
			<td class="tdRight">
				<input type="text" id="providerAddr" name="providerAddr"  class="base-input-text"  valid  errorMsg="请输入供应商地点。"  value="${bean.providerAddr}"/>&nbsp;&nbsp;	
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="spanFont">所属财务中心代码</span></td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="ouCode" name="ouCode" valid errorMsg="请选择财务中心代码。"  class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="${ouCode}">${ouCode}</option>
					</select>
				</div>
			</td>
			<td class="tdLeft"><span class="spanFont">所属财务中心名称</span></td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="ouName" name="ouName" valid errorMsg="请选择财务中心名称。"  class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="${ouName}">${ouName}</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">支付方式</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="payMode" name="payMode" valid errorMsg="请选择支付方式。"  class="erp_cascade_select" >
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_WAY'" selectedValue="${bean.payMode}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft"><span class="spanFont">付款条件</span></td>
			<td class="tdRight">
			   <div class="ui-widget">
				<select id="payCondition" name="payCondition" valid errorMsg="请选择付款条件。"  class="erp_cascade_select">
					<option value="">-请选择-</option>
					<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='SYS_PAY_CONDITION'" selectedValue="${bean.payCondition}"/>
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
		<tr id="addTr" >
			<th width="10%">银行账户编号</th>
			<th width="10%">银行账户币种 </th>
			<th width="10%">银行名称</th>
			<th width="7%">分行名称</th>
			<th width="10%">银行账户名称</th>
			<th width="5%">是否主要帐号 </th>
			<th width="10%">账户类型 </th>
			<th width="10%">开户行行号</th>
			<th width="10%">开户行详细信息</th>
			<th width="5%">银行地区码</th>
			<th width="8%">操作</th>
		</tr>
		 <c:forEach items="${providerActList}" var="sedList">
				<tr>
					<td  width="10%" >
						<span>${sedList.actNo}</span>
						<input  type='hidden' name='actNo' id="actNos" class='base-input-text' style='width:100%' value="${sedList.actNo}"/>
					</td>
					<td  width="10%">
					    <span>${sedList.actCurr}</span>
						<input  type='hidden' name='actCurr' id="actCurrs" class='base-input-text' style='width:100%' value="${sedList.actCurr}"/>
					</td>
					<td  width="10%" >
					     <span>${sedList.bankName}</span>
					     <c:if test="${sedList.bankName=='' or sedList.bankName==null}">
					     	<input  type='hidden' name='bankName'  id="bankNames"  class='base-input-text' style='width:100%' value=" "/>
					     </c:if>
						  <c:if test="${sedList.bankName!='' and sedList.bankName!=null}">
					     	<input  type='hidden' name='bankName'  id="bankNames"  class='base-input-text' style='width:100%' value="${sedList.bankName}"/>
					     </c:if>
					</td>
					<td  width="7%">
						<span>${sedList.branchName}</span>
						<c:if test="${sedList.branchName=='' or sedList.branchName==null}">
							<input  type='hidden' name='branchName' id="branchNames" class='base-input-text' style='width:100%' value=" "/>	
						</c:if>
						<c:if test="${sedList.branchName!='' and sedList.branchName!=null}">
							<input  type='hidden' name='branchName' id="branchNames" class='base-input-text' style='width:100%' value="${sedList.branchName}"/>	
						</c:if>
					</td>
					<td  width="10%">
						<span>${sedList.actName}</span>
						<c:if test="${sedList.actName=='' or sedList.actName==null}">
							<input  type='hidden' name='actName' id="actNames" class='base-input-text' style='width:100%' value=" "/>	
						</c:if>
						<c:if test="${sedList.actName!='' and sedList.actName!=null}">
							<input  type='hidden' name='actName' id="actNames" class='base-input-text' style='width:100%' value="${sedList.actName}"/>	
						</c:if>
					</td>
					<td  width="5%">
					    <c:if test="${sedList.isMasterAct=='Y'}">
			    	    	<span>Y</span>
			   		    </c:if>
			            <c:if test="${sedList.isMasterAct=='N'}">
			    			<span>N</span>
			            </c:if>
					    <input  type='hidden' name='isMasterAct' id="isMasterActs" class='base-input-text' style='width:100%' value="${sedList.isMasterAct}"/>
					</td>
					<td  width="10%">
						<c:if test="${sedList.actType!='' and sedList.actType!=null}">
					    	<span>${sedList.actTypeText}</span>
					    	<input  type='hidden' name='actType' id="actTypes" class='base-input-text' style='width:100%' value="${sedList.actType}"/>
					    </c:if>	
			    	    <c:if test="${sedList.actType=='' or sedList.actType==null}">
			    			<span></span>
			    			<input  type='hidden' name='actType' id="actTypes" class='base-input-text' style='width:100%' value=" "/>
			    	    </c:if>
					</td>
					<td  width="10%" >
					    <span>${sedList.bankCode}</span>
					    <c:if test="${sedList.bankCode=='' or sedList.bankCode==null}">
							<input  type='hidden' name='bankCode' id="bankCodes" class='base-input-text' style='width:100%' value=" "/>	
						</c:if>
						<c:if test="${sedList.bankCode!='' and sedList.bankCode!=null}">
							<input  type='hidden' name='bankCode' id="bankCodes" class='base-input-text' style='width:100%' value="${sedList.bankCode}"/>	
						</c:if>
					</td>
					<td  width="10%">
						<span>${sedList.bankInfo}</span>
						<c:if test="${sedList.bankInfo=='' or sedList.bankInfo==null}">
							<input  type='hidden' name='bankInfo' id="bankInfos" class='base-input-text' style='width:100%' value=" "/>	
						</c:if>
						<c:if test="${sedList.bankInfo!='' and sedList.bankInfo!=null}">
							<input  type='hidden' name='bankInfo' id="bankInfos" class='base-input-text' style='width:100%' value="${sedList.bankInfo}"/>	
						</c:if>
					</td>
					<td  width="5%"> 
							<c:if test="${sedList.bankArea!='' and sedList.bankArea!=null}">
					    		<span>${sedList.bankArea}</span>
					    		<input  type='hidden' name='bankArea'  id="bankAreas" class='base-input-text' style='width:100%' value="${sedList.bankArea}"/>
					    	</c:if>	
				    	    <c:if test="${sedList.bankArea=='' or sedList.bankArea==null}">
				    			<span></span>
				    			<input  type='hidden' name='bankArea'  id="bankAreas" class='base-input-text' style='width:100%' value=" "/>
				    	    </c:if>
						
					</td>
					<td  width="5%">
						<div class="update" style="float: left;"><a href="#"  title="更新"  onclick="subEditActPage(this,'${sedList.actNo}','${sedList.actCurr}','${sedList.bankName}','${sedList.branchName}','${sedList.actName}','${sedList.isMasterAct}','${sedList.actType}','${sedList.bankCode}','${sedList.bankInfo}','${sedList.bankArea}');"></a></div>
						<a href='javascript:void(0);' onclick='delDev(this);'><img border='0' title='删除' width='25px' height='25px' src='<%=request.getContextPath()%>/common/images/delete1.gif'/></a>
					</td>
				</tr>
			</c:forEach>
				<tr>
					<td colspan="11" style="border:none;">
						<br/>
						<div style="text-align:center;" >
							<p:button funcId="010708" value="提交"/>
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