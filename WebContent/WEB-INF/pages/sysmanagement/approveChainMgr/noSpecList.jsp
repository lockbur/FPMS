<%@page import="com.forms.platform.web.WebUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.forms.platform.web.consts.WebConsts"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批链查询</title>
<script type="text/javascript">
function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#isProvinceBuyDiv").find("label").eq('').click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
			$(this).find("option:eq(0)").attr("selected",true);
			$(this).find("option:gt(0)").removeAttr("selected");
		}
		
	});
	$("#aprvTypeDiv").find("label").eq(0).click();
}
//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$("#aprvTypeSearch").combobox();
	$("#valiYearSelect").combobox();
	$("#valiYearSelect2").combobox();
	$("#valiYearSelect3").combobox();
	$("#aprvType2").combobox();
	var tabs = $( "#tabs" ).tabs({
		active: $('#tabsIndex').val(),
		activate: function(){
			$('#tabsIndex').val($(this).tabs('option', 'active'));
			
			if($('#tabsIndex').val()=="0")
			{
				haveUp();
			}
			if($('#tabsIndex').val()=="1")
			{
				noUp();
			}
			if($('#tabsIndex').val()=="2")
			{
				change();
			}

		}
	});
	
}
function haveUp(){
	$("#tabsIndexSkip").val("0");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
	
<%-- 	var url="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103"; --%>
// 	$("#pageSkipForm").val("action",url);
// 	$("#tabsIndexSkip2").val("0");
// 	$("#pageSkipForm").submit();
}
function noUp(){
	$("#tabsIndexSkip").val("1");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
	
<%-- 	var url="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103"; --%>
// 	$("#pageSkipForm").val("action",url);
// 	$("#tabsIndexSkip2").val("1");
// 	$("#pageSkipForm").submit();
}

function change(){
	$("#tabsIndexSkip").val("2");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
	
<%-- 	var url="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103"; --%>
// 	$("#pageSkipForm").val("action",url);
// 	$("#tabsIndexSkip2").val("2");
// 	$("#pageSkipForm").submit();
}
function edit(feeCode,montCode,matrCode,dataYear,orgCode){
	$("#feeCodeSkip").val(feeCode);
	$("#montCodeSkip").val(montCode);
	$("#matrCodeSkip").val(matrCode);
	$("#dataYearSkip").val(dataYear);
	$("#orgCodeSkip").val(orgCode);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecPreEdit.do?<%=WebConsts.FUNC_ID_KEY%>=0812010301';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//未审批明细
function detail(dutyCode,dutyName,dataYear){
	var aprvType = $("#aprvType2").val();
	$("#aprvTypeSkip").val(aprvType);
	$("#dutyCodeSkip").val(dutyCode);
	$("#dutyNameSkip").val(dutyName);
	$("#dataYearSkip").val(dataYear);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecNoWhDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0812010304';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
// function changeDuty(){
// 	var dataYear= $("#valiYearSelect3").val();
// 	$.dialog.open(
<%-- 			'<%=request.getContextPath()%>/sysmanagement/approveChainMgr/changeDutyList.do?<%=WebConsts.FUNC_ID_KEY %>=0812010308&dataYear='+dataYear+'&aprvType=03', --%>
// 			{
// 				width: "75%",
// 				height: "90%",
// 				lock: true,
// 			    fixed: true,
// 			    title:"变动责任中心选择",
// 			    id:"dialogCutPage",
// 			    close:function(){
// 			    	var data=art.dialog.data('data');
// 					if(data){
// 						$("#dutyCode3").val(data.dutyCode);
// 					}else{
// 						$("#dutyCode3").val("");
// 					}
// 			    }
// 			}
// 	);
// }
//取消审批链
function noSpecCancel(dutyCode,montCode,matrCode,dataYear,orgCode){
	$("<div>是否确认解除该审批链？</div>").dialog({
		resizable: false,
		height:165,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#feeCodeSkip").val(dutyCode);
				$("#montCodeSkip").val(montCode);
				$("#matrCodeSkip").val(matrCode);
				$("#dataYearSkip").val(dataYear);
				$("#orgCodeSkip").val(orgCode);
				var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecDel.do?<%=WebConsts.FUNC_ID_KEY%>=0812010302';
				$('#skipForm').attr("action",url);
				App.submit( $("#skipForm"));
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
function batchUp(dataYear){
	$("#dataYearSkip").val(dataYear);
	var aprvType = $("#aprvType2").val();
	$("#aprvTypeSkip").val(aprvType);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/batchEdit.do?<%=WebConsts.FUNC_ID_KEY%>=0812010310';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//未维护 责任中心查询
function selectDutyList(){
	$("#tabsIndexSkip").val("1");
	$("#dataYearSkip").val($("#valiYearSelect2").val());
	
	$("#dutyCodeSkip").val($("#dutyCode2").val());
	var aprvType = $("#aprvType2").val();
	$("#aprvTypeSkip").val(aprvType);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//合并单元格
$(function (){
	var tabsIndex = "${selectInfo.tabsIndex}";
	if(tabsIndex == '0'){
		table("fee_td_h_","fee_td_");//隐藏框id  td 的id
		table("audit_td_h_","audit_td_");
		table("buy_td_h_","buy_td_");
	}else if(tabsIndex == '2'){
		table2("mont_td_h_","mont_td_");//隐藏框id  td 的id
		table2("matr_td_h_","matr_td_");//隐藏框id  td 的id
	}
	
});
function table(inputId,tdId){
	var str1="";
	var table1 = $("#listTab");
	var trs = table1[0].rows;
	for(var i=1;i<=trs.length-1;i++){
		var flag1_1 = true;
		if(str1 == $("#"+inputId+i).val() ){
			//当着两者相同时 就不让他重复循环
			continue;
		}
		str1=$("#"+inputId+i).val();
		for(var j=i+1;j<=trs.length;j++){
			if(flag1_1 == true){
				//当为true时才继续循环下去
				var val1 = $("#"+inputId+i).val();
				var val2 = $("#"+inputId+j).val();
				if(val1==val2){
					var k = $("#"+tdId+i).attr("rowspan");
					$("#"+tdId+i).attr("rowspan",parseInt(k)+1);
					$("#"+tdId+j).remove();
				}else{
					//当有一个不等时不让他继续循环
					flag1_1 = false;
				}
			}
			
		}
	}
}
function table2(inputId,tdId){
	var str1="";
	var table1 = $("#listTab1");
	var trs = table1[0].rows;
	for(var i=1;i<=trs.length-1;i++){
		var flag1_1 = true;
		if(str1 == $("#"+inputId+i).val() ){
			//当着两者相同时 就不让他重复循环
			continue;
		}
		str1=$("#"+inputId+i).val();
		for(var j=i+1;j<=trs.length;j++){
			if(flag1_1 == true){
				//当为true时才继续循环下去
				var val1 = $("#"+inputId+i).val();
				var val2 = $("#"+inputId+j).val();
				if(val1==val2){
					var k = $("#"+tdId+i).attr("rowspan");
					$("#"+tdId+i).attr("rowspan",parseInt(k)+1);
					$("#"+tdId+j).remove();
				}else{
					//当有一个不等时不让他继续循环
					flag1_1 = false;
				}
			}
			
		}
	}
}
function updateFee(aprvType,feeDept,feeDeptName,dataYear){
	//修改
	$("#alertFeeCodeName").html(feeDeptName);
	$("#alertFeeCode").val(feeDept);
	$("#alertAprvType").val(aprvType);
	$("#dataYearSkip").val(dataYear);
	setSearch('feeForm','1');
	var url = "<%=request.getContextPath()%>/sysmanagement/approveChainMgr/batchUpFee.do?<%=WebConsts.FUNC_ID_KEY %>=0812010313&dataYear="+dataYear;
	$("#feeDiv").dialog({
		title:"修改审批链中物料的费用承担部门",
		closeOnEscape:false,
		autoOpen: true,
		height: 'auto',
		width: 600,
		modal: true,
		zIndex:100,
		dialogClass: 'dClass',
		resizable: false,
		open:function()
		{
			 
		},
		buttons: {
			"确定": function() {
				if(!App.valid("#feeForm")){return;}
				$('#feeForm').attr("action",url);
				App.submitShowProgress();//锁屏
				$('#feeForm').submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
function setSearch(formName,tag){
	$("#"+formName+" #matrCodeAudit").val($("#matrCodeSearch").val());
	$("#"+formName+" #matrNameAudit").val($("#matrNameSearch").val());
	$("#"+formName+" #montCodeAudit").val($("#montCodeSearch").val());
	$("#"+formName+" #montNameAudit").val($("#montNameSearch").val());
	if(tag != '3'){
		$("#"+formName+" #matrBuyDeptAudit").val($("#matrBuyDeptSearch").val());
	}
	if(tag !='2'){
		$("#"+formName+" #matrAuditDeptAudit").val($("#matrAuditDeptSearch").val());
	}
	if(tag !='1'){
		$("#"+formName+" #feeCodeAudit").val($("#feeCodeSearch").val());
	}
	$("#"+formName+" #orgCodeAudit").val($("#orgCodeSearch").val());
}
function updateAudit(aprvType,auditDept,auditDeptName,dataYear){
	//修改
	$("#alertAuditDeptName").html(auditDeptName);
	$("#alertAuditDept").val(auditDept);
	$("#alertAprvTypeAudit").val(aprvType);
	$("#dataYearSkip").val(dataYear);
	setSearch('auditForm','2');
	var url = "<%=request.getContextPath()%>/sysmanagement/approveChainMgr/batchUpAudit.do?<%=WebConsts.FUNC_ID_KEY %>=0812010314&dataYear="+dataYear;
	$("#auditDiv").dialog({
		title:"修改审批链中物料的归口管理部门",
		closeOnEscape:false,
		autoOpen: true,
		height: 'auto',
		width: 600,
		modal: true,
		zIndex:100,
		dialogClass: 'dClass',
		resizable: false,
		open:function()
		{
			 
		},
		buttons: {
			"确定": function() {
				if(!App.valid("#auditForm")){return;}
				$('#auditForm').attr("action",url);
				App.submitShowProgress();//锁屏
				$('#auditForm').submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
function updateBuy(aprvType,matrBuyDept,matrBuyDeptName,dataYear){
	//修改
	$("#alertmatrBuyDeptName").html(matrBuyDeptName);
	$("#alertmatrBuyDept").val(matrBuyDept);
	$("#alertAprvTypeBuy").val(aprvType);
	$("#dataYearSkip").val(dataYear);
	setSearch('buyForm','3');
	var url = "<%=request.getContextPath()%>/sysmanagement/approveChainMgr/batchUpBuy.do?<%=WebConsts.FUNC_ID_KEY %>=0812010315&dataYear="+dataYear;
	$("#buyDiv").dialog({
		title:"修改审批链中物料的采购部门",
		closeOnEscape:false,
		autoOpen: true,
		height: 'auto',
		width: 600,
		modal: true,
		zIndex:100,
		dialogClass: 'dClass',
		resizable: false,
		open:function()
		{
			 
		},
		buttons: {
			"确定": function() {
				if(!App.valid("#buyForm")){return;}
				$('#buyForm').attr("action",url);
				App.submitShowProgress();//锁屏
				$('#buyForm').submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
$(function(){
	//查询这个归口下面的所有审批链是否已经转移了
	var tabsIndex = "${selectInfo.tabsIndex}";
	if(tabsIndex=="0"){
		var data = {};
		data['type'] = "02";
		App.ajaxSubmit("common/montAprvTransfer/aprvTransfer.do", {
			data : data,
			async : false
		}, function(data) {
			var resultMap= data.data;
			var flag = resultMap.flag;
			var msg = resultMap.msg;
			if(flag == false){
				eval(msg);
			}

		});
	}
	
});
//选择一个审批链
function forUpdate(montCodeNew,matrCode,aprvType,dataYear){
	$("#montCodeNewSkip").val(montCodeNew);
	$("#matrCodeSkip").val(matrCode);
	$("#orgTypeSkip").val("02");
	$("#aprvTypeSkip").val(aprvType);
	$("#dataYearSkip").val(dataYear);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/forUpdate.do?<%=WebConsts.FUNC_ID_KEY %>=0812010317';
	$("#skipForm").attr("action",url);
	$("#skipForm").submit();
	
}
</script>
</head>

<body>
<form:hidden id="tabsIndex" path="selectInfo.tabsIndex"/>
<form id="pageSkipForm" action="" method="post">
<input type="hidden" name="tabsIndex" id="tabsIndexSkip2"/>
</form>
<p:authFunc funcArray="08120103"/>
<form id="skipForm" action="" method="post">
	<input type="hidden" name="dutyCode" id="dutyCodeSkip">
	<input type="hidden" name="dutyName" id="dutyNameSkip">
	<input type="hidden" name="feeCode" id="feeCodeSkip">
	<input type="hidden" name="matrCode" id="matrCodeSkip">
	<input type="hidden" name="montCode" id="montCodeSkip">
	<input type="hidden" name="tabsIndex" id="tabsIndexSkip">
	<input type="hidden" name="dataYear" id="dataYearSkip">
	<input type="hidden" name="aprvType" id="aprvTypeSkip">
	<input type="hidden" name="orgCode" id="orgCodeSkip">
	<input type="hidden" name="montCodeNew" id="montCodeNewSkip">
	<input type="hidden" name="changeData" id="changeDataSkip">
	<input type="hidden" name="orgType" id="orgTypeSkip">
</form>
	<div id="tabs" style="border: 0;">
	<ul>
		<li><a href="#tabs-1" >已维护</a></li>
		<li><a href="#tabs-2">未维护</a></li>
		<li><a href="#tabs-3">待修改</a></li>
	</ul>
	<c:if test="${selectInfo.tabsIndex eq '0' }">
    <div id="tabs-1" style="padding: 0;">	
   <form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103" method="post" id="haveUpForm">
	<input type="hidden" name="tabsIndex" value="0">
	<table id="table1">
		<tr class="collspan-control">
			<th colspan="4">
				审批链查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">物料编码</td>
			<td class="tdRight" width="30%">
				<input type="text" name="matrCode" value="${selectInfo.matrCode }"  id="matrCodeSearch" class="base-input-text" />
			</td>
			<td class="tdLeft" width="20%">物料名称</td>
			<td class="tdRight" width="30%" colspan="3">
				<input type="text" name="matrName" value="${selectInfo.matrName }"   id="matrNameSearch" class="base-input-text" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">监控指标代码</td>
			<td class="tdRight"  >
				<input   name="montCode" maxlength="200" value="${selectInfo.montCode }" id="montCodeSearch" class="base-input-text" type="text"   />
			</td>
			<td class="tdLeft">监控指标名称</td>
			<td class="tdRight"  >
				<input  name="montName" maxlength="200" value="${selectInfo.montName }" id="montNameSearch" class="base-input-text" type="text"   />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">费用承担部门编码</td>
			<td class="tdRight">
				<input  name="feeCode" maxlength="200" value="${selectInfo.feeCode }" id="feeCodeSearch" class="base-input-text" type="text"   />
			</td>
			<td class="tdLeft">费用承担机构编码</td>
			<td class="tdRight">
				<input   name="orgCode" maxlength="200" value="${selectInfo.orgCode }" id="orgCodeSearch" class="base-input-text" type="text"   />
				
			</td>
		</tr>
		<tr>
		<td class="tdLeft">物料归口部门编码</td>
			<td class="tdRight">
				<input   name="matrAuditDept" maxlength="200" value="${selectInfo.matrAuditDept }" id="matrAuditDeptSearch" class="base-input-text" type="text"   />
				
			</td>
		<td class="tdLeft">物料采购部门编码</td>
			<td class="tdRight">
				<input  name="matrBuyDept" maxlength="200" value="${selectInfo.matrBuyDept }" id="matrBuyDeptSearch" class="base-input-text" type="text"   />
			</td>
			
		</tr>
		<tr>
			
			<td class="tdLeft">审批链类型</td>
			<td class="tdRight"  >
				<div class="base-input-radio" id="aprvTypeDiv">
					<label for="aprvType1" onclick="App.radioCheck(this,'aprvTypeDiv')" <c:if test="${'21'==selectInfo.aprvType}">class="check-label"</c:if>>非省行统购资产</label><input type="radio" id="aprvType1" name="aprvType" value="21" <c:if test="${'21'==selectInfo.aprvType}">checked="checked"</c:if>>
					<label for="aprvType3" onclick="App.radioCheck(this,'aprvTypeDiv')" <c:if test="${'22'==selectInfo.aprvType}">class="check-label"</c:if>>非专项包费用</label><input type="radio" id="aprvType3" name="aprvType" value="22" <c:if test="${'22'==selectInfo.aprvType}">checked="checked"</c:if>>
				</div>
			</td>
			<td class="tdLeft"></td>
				<td class="tdRight">
			</td>
		</tr>
		
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="08120103" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	 <table class="tableList" id="listTab">
		<tr>
			<th style="width:10%">审批链类型</th>
			<th style="width:20%" >物料名称</th>
			<th style="width:20%%">监控指标名称</th>
			<th style="width:10%">费用承担部门编码</th>
			<th style="width:10%">费用承担机构编码</th>
			<th style="width:10%">物料归口部门编码</th>
			<th style="width:10%">物料采购部门编码</th>
			<th style="width:10%" align="center">操作</th>
		</tr>
		<c:forEach items="${noSpecList}" var="aList" varStatus="i">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td  >
				${aList.aprvTypeName}
			</td>
			<td ><span title="${aList.matrCode }">${aList.matrName}</span></td>
			<td  >
				<span title="${aList.montCode }">${aList.montName }</span>
			</td>
			<td class="compare"  id='fee_td_<c:out value="${i.index+1}"/>' rowspan='1'>
				
				<input type="hidden" id='fee_td_h_<c:out value="${i.index+1}"/>' value="${aList.feeCode}">
					<c:if test="${aList.feeCode !=null && '' != aList.feeCode}">
						<span title="${aList.feeName }">${aList.feeCode }</span>
						<div class="update"><a href="javascript:void(0)" onclick="updateFee('${aList.aprvType}','${aList.feeCode}','${aList.feeName }','${aList.dataYear }')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
					</c:if>
			</td>
			<td  >
				<span title="${aList.orgName }">${aList.orgCode }</span>
			</td>
			<td class="compare" id='audit_td_<c:out value="${i.index+1}"/>' rowspan='1'>
				<input type="hidden" id='audit_td_h_<c:out value="${i.index+1}"/>' value="${aList.matrAuditDept}">
				<span title="${aList.matrAuditName }">${aList.matrAuditDept}</span>
				<div class="update"><a href="javascript:void(0)" onclick="updateAudit('${aList.aprvType}','${aList.matrAuditDept}','${aList.matrAuditName }','${aList.dataYear }')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
			</td>
			<td class="compare" id='buy_td_<c:out value="${i.index+1}"/>' rowspan='1'>
				<input type="hidden" id='buy_td_h_<c:out value="${i.index+1}"/>' value="${aList.matrBuyDept}">
				<span title="${aList.matrBuyName }">${aList.matrBuyDept}</span>
				<div class="update"><a href="javascript:void(0)" onclick="updateBuy('${aList.aprvType}','${aList.matrBuyDept}','${aList.matrBuyName }','${aList.dataYear }')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
			</td>
			<td>
				<div class="update"><a href="#" onclick="edit('${aList.feeCode}','${aList.montCode }','${aList.matrCode }','${aList.dataYear }','${aList.orgCode }');" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
				<a onclick="noSpecCancel('${aList.feeCode}','${aList.montCode }','${aList.matrCode }','${aList.dataYear }','${aList.orgCode }');" ><img class="delete imageBtn" border="0" alt="解除关联" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/></a>
			</td>
			
		</tr>
		</c:forEach>
		<c:if test="${empty noSpecList}">
	    <tr>
		<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>		
	</table>
	</form>
	<p:page pageKey="noSpecHaveWh"/>
	 <jsp:include page="updateFee.jsp" />
   <jsp:include page="updateAudit.jsp" />
   <jsp:include page="updateBuy.jsp" />
	
   </div>
   </c:if>
   <c:if test="${selectInfo.tabsIndex eq '1' }">
    <div id="tabs-2" style="padding: 0;">
    	<form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103" method="post" id="noUpdateForm">
    	<input type="hidden" name="tabsIndex" value="1">
    	<table id="noUpTables" >
		<tr class="collspan-control">
			<th colspan="4">
				查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">类型<input type="hidden" name="dataYear" value="${thisYear }"/></td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="aprvType2" name="aprvType">
						<option value="21" <c:if test="${selectInfo.aprvType eq '21'}">selected="selected"</c:if>>--非省行统购资产--</option>
						<option value="22" <c:if test="${selectInfo.aprvType eq '22'}">selected="selected"</c:if>>--非专项包费用--</option>
					</select>
				</div>
			</td>
			<td class="tdLeft">费用承担部门编码</td>
			<td class="tdRight" >
				<input    maxlength="200" id="dutyCode2" value="${selectInfo.dutyCode }" class="base-input-text" type="text"   />
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" onclick="selectDutyList()" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="批量维护" onclick="batchUp('${selectInfo.dataYear}');">
			</td>
		</tr>
	</table>
    		<table class="tableList" >
				<tr>
					<th > 费用承担部门名称</th>
					<th  >可维护数量</th>
					<th   >明细</th>
				</tr>
				<c:forEach items="${noSpecNoWhList}" var="bList" varStatus="i">
					<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
						<td>${bList.dutyCode }-${bList.dutyName }</td>
						<td>${bList.noUpCount }</td>
						<td>
							<div class="update"><a href="#" onclick="detail('${bList.dutyCode }','${bList.dutyName }','${bList.dataYear }');" title='<%=WebUtils.getMessage("button.update")%>'></a></div>
						</td>
					</tr>
				</c:forEach>
	   <c:if test="${empty noSpecNoWhList}">
	    <tr>
		<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>					
			</table>
    	</form>	
    	<p:page pageKey="noSpecNoWh"/>
    </div>
   </c:if>
   <c:if test="${selectInfo.tabsIndex eq '2' }">
    <div id="tabs-3" style="padding: 0;">
    	<form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/noSpecList.do?<%=WebConsts.FUNC_ID_KEY %>=08120103" method="post" id="changeForm">
    	<input type="hidden" name="tabsIndex" value="2">
    	<table >
		<tr class="collspan-control">
			<th colspan="4">
				监控指标合并引起的审批链待更改查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">新监控指标代码</td>
			<td class="tdRight" >
				<input   name="montCodeNew" maxlength="200" value="${selectInfo.montCodeNew }" class="base-input-text" type="text" style="width:235px;" />
			</td>
			<td class="tdLeft">新监控指标名称</td>
			<td class="tdRight" >
				<input  name="montNameNew" maxlength="200" value="${selectInfo.montNameNew }" class="base-input-text" type="text" style="width:235px;" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">物料代码</td>
			<td class="tdRight" >
				<input   name="matrCode" maxlength="200" value="${selectInfo.matrCode }" class="base-input-text" type="text" style="width:235px;" />
			</td>
			<td class="tdLeft">物料名称</td>
			<td class="tdRight" >
				<input  name="matrName" maxlength="200" value="${selectInfo.matrName }" class="base-input-text" type="text" style="width:235px;" />
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="08120103" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
    		<table class="tableList" id="listTab1">
		<tr>
			<th style="width:10%">新监控指标代码</th>
			<th style="width:20%">新监控指标名称</th>
			<th style="width:10%" >物料代码</th>
			<th style="width:20%" >物料名称</th>
			<th style="width:10%">审批链类型</th>
			<th  align="center">操作</th>
		</tr>
		<c:forEach items="${changeList}" var="aList" varStatus="i">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td  >
				<span  >${aList.montCodeNew}</span>
			</td>
			<td   >
				<span >${aList.montNameNew}</span>
			</td>
			<td  >
				${aList.matrCode}
			</td>
			<td >
				${aList.matrName}
			</td>
			<td  >
					${aList.aprvTypeName }
			</td>
			<td>
				<input type="button" value="修改" onclick="forUpdate('${aList.montCodeNew}','${aList.matrCode }','${aList.aprvType }','${aList.dataYear }')"/>
			</td>
			
		</tr>
		</c:forEach>
		<c:if test="${empty changeList}">
	    <tr>
		<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>		
	</table>
    	</form>	
    	<p:page pageKey="noSpecChange"/>
    </div>
  </c:if>
  </div>

</body>
</html>
