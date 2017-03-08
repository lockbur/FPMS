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
		}
		
	});

	$("#aprvTypeDiv").find("label").eq(0).click();
	//org2CodeTree.afterDynamicBack();
}

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	App.jqueryAutocomplete();
	$("#aprvTypeSearch").combobox();
	$("#valiYearSelect").combobox();
	$("#valiYearSelect2").combobox();
	$("#valiYearSelect3").combobox();
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
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
	
// 	alert("ss")
<%-- 	var url="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102"; --%>
// 	alert("ss2")
// 	$("#pageSkipForm").val("action",url);
// 	$("#tabsIndexSkip2").val("0");
// 	$("#pageSkipForm").submit();
}
function noUp(){
	$("#tabsIndexSkip").val("1");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
<%-- 	var url="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102"; --%>
// 	$("#pageSkipForm").val("action",url);
// 	$("#tabsIndexSkip2").val("1");
// 	$("#pageSkipForm").submit();
}
function change(){
	$("#tabsIndexSkip").val("2");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
	
<%-- 	var url="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102"; --%>
// 	$("#pageSkipForm").val("action",url);
// 	$("#tabsIndexSkip2").val("2");
// 	$("#pageSkipForm").submit();
}
function openDiv(hideDivId,obj){
	 $("."+hideDivId+"_hideDiv").toggle();
	 if($("."+hideDivId+"_hideDiv:hidden").size() == 0 )
	{
		 $(obj).html("隐藏");
		 
	}else{
		$(obj).html("展开");
	}
}
//全选
function selectAll(item ,tag){
	$('input[type=checkbox][class=' + tag + ']').each(function(){
		this.checked = true;
	});
}
//反选
function selectOther(item,tag){
	$('input[type=checkbox][class=' + tag + ']').each(function(){
		if(this.checked == false){
			this.checked = true;
		}else{
			this.checked = false;
		}
	});
}
function specAdd(){
	var matrBuyDeptList = matrBuyDeptTree.getSelectOrgList();
	if(matrBuyDeptList.length < 1){
		App.notyError("物料采购部门不能为空，请选择物料采购部门！");
		return false;
	}
	var matrAuditDeptList = matrAuditDeptTree.getSelectOrgList();
	if(matrAuditDeptList.length < 1){
		App.notyError("物料归口部门不能为空，请选择物料归口部门！");
		return false;
	}
	var fincDeptSList = fincDeptSTree.getSelectOrgList();
	if(fincDeptSList.length < 1){
		App.notyError("本级财务管理部门不能为空，请选择本级财务管理部门！");
		return false;
	}
	var fincDept2List = fincDept2Tree.getSelectOrgList();
	if(fincDept2List.length < 1){
		App.notyError("二级分行财务管理部门不能为空，请选择二级分行财务管理部门部门！");
		return false;
	}
	var fincDept1List = fincDept1Tree.getSelectOrgList();
	if(fincDept1List.length < 1){
		App.notyError("一级分行财务管理部门不能为空，请选择一级分行财务管理部门！");
		return false;
	}
	$("#noUpdateForm").attr("action","<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specAdd.do?<%=WebConsts.FUNC_ID_KEY %>=0812010201");
	$("#noUpdateForm").submit();
}

function edit(org1Code,montCode,matrCode,feeCode,aprvType,dataYear,aprvType,orgCode){
	$("#org1CodeSkip").val(org1Code);
	$("#montCodeSkip").val(montCode);
	$("#matrCodeSkip").val(matrCode);
	$("#feeCodeSkip").val(feeCode);
	$("#aprvTypeSkip").val(aprvType);
	$("#dataYearSkip").val(dataYear);
	$("#orgCodeSkip").val(orgCode);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specPreEdit.do?<%=WebConsts.FUNC_ID_KEY%>=0812010203';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//单个取消关联
function cancel(org1Code,montCode,matrCode,feeCode,aprvType,dataYear,orgCode){
	$("<div>是否确认解除该审批链？</div>").dialog({
		resizable: false,
		height:165,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#org1CodeSkip").val(org1Code);
				$("#montCodeSkip").val(montCode);
				$("#matrCodeSkip").val(matrCode);
				$("#feeCodeSkip").val(feeCode);
				$("#aprvTypeSkip").val(aprvType);
				$("#dataYearSkip").val(dataYear);
				$("#orgCodeSkip").val(orgCode);
				var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specDel.do?<%=WebConsts.FUNC_ID_KEY%>=0812010202';
				$('#skipForm').attr("action",url);
				App.submit( $("#skipForm"));
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
//未维护跳转页面
function noWhSkip(){
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specNoWhSkip.do?<%=WebConsts.FUNC_ID_KEY%>=0812010205';
	$('#noUpdateForm').attr("action",url);
	App.submit( $("#noUpdateForm"));
	
}
//未审批明细
function detail(dutyCode,dutyName,dataYear,aprvType){
	$("#dutyCodeSkip").val(dutyCode);
	$("#dutyNameSkip").val(dutyName);
	$("#dataYearSkip").val(dataYear);
	$("#aprvTypeSkip").val(aprvType);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specNoWhDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0812010206';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//上一步
function  backPage(){
	$("#tabsIndexSkip").val("1");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//未维护 责任中心查询
function selectDutyList(dataYear){
	$("#aprvTypeSkip").val("12");
	
// 	$("#dutyCodeSkip").val(dutyCode2.getSelectOrgList()[0].id);
	$("#dutyCodeSkip").val($("#dutyCode2").val());
	$("#dataYearSkip").val(dataYear);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specNoWhSkip.do?<%=WebConsts.FUNC_ID_KEY %>=0812010205';
	$('#skipForm').attr("action",url);
	App.submit( $("#skipForm"));
}
//批量维护页面
function batchUp(dataYear){
	$("#dataYearSkip").val(dataYear);
	$("#dataYearSkip").val(dataYear);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specBatchEdit.do?<%=WebConsts.FUNC_ID_KEY%>=0812010208';
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
// function changeDuty(){
// 	var dataYear= $("#valiYearSelect3").val();
// 	$.dialog.open(
<%-- 			'<%=request.getContextPath()%>/sysmanagement/approveChainMgr/changeDutyList.do?<%=WebConsts.FUNC_ID_KEY %>=0812010308&dataYear='+dataYear+'&aprvType=02', --%>
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
// 						$("#montCode").val(data.montCode);  //内部供应商名称
// 						$("#dutyCode3").val(data.dutyCode);
// 						$("#dutyName3").val(data.dutyName);
// 					}
// 			    }
// 			}
// 	);
// }
$(function(){
	//查询这个归口下面的所有审批链是否已经转移了
	var tabsIndex = "${selectInfo.tabsIndex}";
	if(tabsIndex=="0"){
		var data = {};
		data['type'] = "01";
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
	$("#orgTypeSkip").val("01");
	$("#aprvTypeSkip").val(aprvType);
	$("#dataYearSkip").val(dataYear);
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/forUpdate.do?<%=WebConsts.FUNC_ID_KEY %>=0812010216';
	$("#skipForm").attr("action",url);
	$("#skipForm").submit();
	
}
</script>
</head>

<body>
<form id="pageSkipForm" action="" method="post">
<input type="hidden" name="tabsIndex" id="tabsIndexSkip2"/>
</form>
<form action="" id="skipForm" method="post">
	<input type="hidden" name="org1Code" id="org1CodeSkip"/>
	<input type="hidden" name="montCode" id="montCodeSkip"/>
	<input type="hidden" name="matrCode" id="matrCodeSkip"/>
	<input type="hidden" name="dutyCode" id="dutyCodeSkip">
	<input type="hidden" name="dutyName" id="dutyNameSkip">
	<input type="hidden" name="tabsIndex" id="tabsIndexSkip">
	<input type="hidden" name="aprvType" id="aprvTypeSkip" value = "${selectInfo.aprvType }">
	<input type="hidden" name="feeCode" id="feeCodeSkip">
	<input type="hidden" name="dataYear" id="dataYearSkip">
	<input type="hidden" name="changeData" id="changeData"/>
	<input type="hidden" name="orgCode" id="orgCodeSkip"/>
	<input type="hidden" name="montCodeNew" id="montCodeNewSkip">
	<input type="hidden" name="changeData" id="changeDataSkip">
	<input type="hidden" name="orgType" id="orgTypeSkip">
</form>
<form:hidden id="tabsIndex" path="selectInfo.tabsIndex"/>
<p:authFunc funcArray="08120102,0812010201,0812010208"/>
	<div id="tabs" style="border: 0;">
	<ul>
		<li><a href="#tabs-1" >已维护</a></li>
		<li><a href="#tabs-2">未维护</a></li>
		<li><a href="#tabs-3">待修改</a></li>
	</ul>
	<c:if test="${selectInfo.tabsIndex eq '0' }">
    <div id="tabs-1" style="padding: 0;">	
   <form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102" method="post" id="haveUpForm">
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
			<td class="tdRight" width="30%"    >
				<input type="text" name="matrName" value="${selectInfo.matrName }"    id="matrNameSearch"  class="base-input-text" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">监控指标代码</td>
			<td class="tdRight"  >
				<input   name="montCode" maxlength="50" value="${selectInfo.montCode }" id="montCodeSearch" class="base-input-text" type="text"   />
			</td>
			<td class="tdLeft">监控指标名称</td>
			<td class="tdRight"  >
				<input   name="montName" maxlength="200" value="${selectInfo.montName }" id="montNameSearch" class="base-input-text" type="text"   />
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">费用承担部门编码</td>
			<td class="tdRight" width="30%">
				<input   name="feeCode" maxlength="50" value="${selectInfo.feeCode }" id="feeCodeSearch" class="base-input-text" type="text"   />
			</td>
			<td class="tdLeft">费用承担机构编码</td>
			<td class="tdRight">
				<input   name="orgCode" maxlength="50" value="${selectInfo.orgCode }"  id="orgCodeSearch" class="base-input-text" type="text"   />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">物料归口部门编码</td>
			<td class="tdRight"  >
				<input   name="matrAuditDept" maxlength="50" value="${selectInfo.matrAuditDept }" id="matrAuditDeptSearch"  class="base-input-text" type="text"   />
			</td>
			<td class="tdLeft">物料采购部门编码</td>
			<td class="tdRight">
				<input   name="matrBuyDept" maxlength="50" value="${selectInfo.matrBuyDept }" id="matrBuyDeptSearch" class="base-input-text" type="text"   />
			</td>
			
			<!-- 
			<td class="tdLeft">本级财务管理部门</td>
			<td class="tdRight">
				<input   name="fincDeptS" maxlength="50" value="${selectInfo.fincDeptS }" class="base-input-text" type="text"   />
			</td>
			 -->
		</tr>
		<!-- 
		<tr>
			
			<td class="tdLeft">二级分行财务管理部门</td>
			<td class="tdRight">
				<input   name="fincDept2" maxlength="50" value="${selectInfo.fincDept2 }" class="base-input-text" type="text"   />
			</td>
			<td class="tdLeft">一级分行财务管理部门</td>
			<td class="tdRight" >
				<input   name="fincDept1" maxlength="50" value="${selectInfo.fincDept1 }" class="base-input-text" type="text"   />
			</td>
		</tr>
		 -->
		<tr>
			
			<td class="tdLeft">审批链类型</td>
			<td class="tdRight"  >
<!-- 				<div class="ui-widget"> -->
<!-- 					<select id="aprvTypeSearch" name="aprvType"   class="erp_cascade_select"> -->
<!-- 						<option value="">-请选择-</option> -->
<%-- 						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"  --%>
<%-- 						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')" selectedValue="${selectInfo.aprvType}"/> --%>
<!-- 					</select>  -->
<!-- 				</div> -->
				<div class="base-input-radio" id="aprvTypeDiv">
					<label for="aprvType1" onclick="App.radioCheck(this,'aprvTypeDiv')" <c:if test="${'11'==selectInfo.aprvType}">class="check-label"</c:if>>专项包</label><input type="radio" id="aprvType1" name="aprvType" value="11" <c:if test="${'11'==selectInfo.aprvType}">checked="checked"</c:if>>
					<label for="aprvType2" onclick="App.radioCheck(this,'aprvTypeDiv')" <c:if test="${'12'==selectInfo.aprvType}">class="check-label"</c:if>>省行统购资产</label><input type="radio" id="aprvType2" name="aprvType" value="12" <c:if test="${'12'==selectInfo.aprvType}">checked="checked"</c:if>>
				</div>
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight">
			<!-- 
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear">
						<option value="${thisYear }" <c:if test="${thisYear == selectInfo.dataYear }">selected="selected"</c:if>>--${thisYear}--</option>
						<option value="${thisYear+1 }" <c:if test="${thisYear+1 == selectInfo.dataYear }">selected="selected"</c:if>>--${thisYear+1}--</option>
					</select>
				</div> -->
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="08120102" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	 <table class="tableList" id="listTab">
		<tr>
			<th style="width:10%">审批链类型</th>
			<th style="width:20%" >物料名称</th>
			<th style="width:20%">监控指标名称</th>
			<th style="width:10%">费用承担部门编码</th>
			<th style="width:10%">费用承担机构编码</th>
			<th style="width:10%">物料归口部门编码</th>
			<th style="width:10%">物料采购部门编码</th>
			<!-- 
			<th style="width:220px">本级财务管理部门</th>
			<th style="width:220px">二级分行财务管理部门</th>
			<th style="width:220px">一级分行财务管理部门</th>
			 -->
			<th style="width:10%" align="center">操作</th>
		</tr>
		<c:forEach items="${specHaveWhList}" var="aList" varStatus="i">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td>
				${aList.aprvTypeName}
			</td>
			
			<td class="compare" id='mart_td_<c:out value="${i.index+1}"/>'  rowspan='1'>
				<input type="hidden" id='mart_td_h_<c:out value="${i.index+1}"/>' value="${aList.matrCode}">
				<span title="${aList.matrCode }" >${aList.matrName}</span>
			</td>
			<td class="compare" id='mont_td_<c:out value="${i.index+1}"/>' rowspan='1'   >
				<input type="hidden" id='mont_td_h_<c:out value="${i.index+1}"/>' value="${aList.montCode}">
				 
				<span title="${aList.montCode }" >${aList.montName}</span>
			</td>
			<td  class="compare"  id='fee_td_<c:out value="${i.index+1}"/>' rowspan='1' >
				<c:if test="${aList.aprvType == '12' }">
					
					<c:if test="${aList.feeCode !=null && '' != aList.feeCode}">
						<input type="hidden" id='fee_td_h_<c:out value="${i.index+1}"/>' value="${aList.feeCode}">
						 <span title="${aList.feeName }" >${aList.feeCode}</span>
							<div class="update"><a href="javascript:void(0)" onclick="updateFee('${aList.aprvType }','${aList.feeCode}','${aList.feeName }','${aList.dataYear }')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
					</c:if>
				</c:if>
			</td>
			<td  class="compare"    rowspan='1' >
				<c:if test="${aList.aprvType == '12' }">
					<span title="${aList.orgName}">${aList.orgCode }</span>
				</c:if>
			</td>
			<td class="compare" id='audit_td_<c:out value="${i.index+1}"/>' rowspan='1'>
				<input type="hidden" id='audit_td_h_<c:out value="${i.index+1}"/>' value="${aList.matrAuditDept}">
				 
				 <span title="${aList.matrAuditName }" >${aList.matrAuditDept}</span>
				<div class="update"><a href="javascript:void(0)" onclick="updateAudit('${aList.aprvType}','${aList.matrAuditDept}','${aList.matrAuditName }','${aList.dataYear }')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
			</td>
			<td   class="compare" id='buy_td_<c:out value="${i.index+1}"/>' rowspan='1'>
				<input type="hidden" id='buy_td_h_<c:out value="${i.index+1}"/>' value="${aList.matrBuyDept}">
				 
				 <span title="${aList.matrBuyName }" >${aList.matrBuyDept}</span>
				<div class="update"><a href="javascript:void(0)" onclick="updateBuy('${aList.aprvType}','${aList.matrBuyDept}','${aList.matrBuyName }','${aList.dataYear }')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
			</td>
			<td>
				<div class="update"><a href="#" onclick="edit('${aList.org1Code}','${aList.montCode }','${aList.matrCode }','${aList.feeCode }','${aList.aprvType }','${aList.dataYear }','${aList.aprvType }','${aList.orgCode }');" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
				<a onclick="cancel('${aList.org1Code}','${aList.montCode }','${aList.matrCode }','${aList.feeCode }','${aList.aprvType }','${aList.dataYear }','${aList.orgCode }');" ><img class="delete imageBtn" border="0" alt="解除关联" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/></a>
			</td>
			
		</tr>
		</c:forEach>
		<c:if test="${empty specHaveWhList}">
	    <tr>
		<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>		
	</table>
	</form>
	<p:page pageKey="specHaveWh"/>
   </div>
   <jsp:include page="updateFee.jsp" />
   <jsp:include page="updateAudit.jsp" />
   <jsp:include page="updateBuy.jsp" />
  </c:if>
   <c:if test="${selectInfo.tabsIndex eq '1' }">
    <div id="tabs-2" style="padding: 0;">
    	<form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102" method="post" id="noUpdateForm">
    		<input type="hidden" name="tabsIndex" value="1">
    		<input type="hidden" name="org1Code" value="${selectInfo.org1Code }"/>
    		<c:if test="${noWhTag == '01' }">
    			<table id="tjTable">
    				<tr><th colspan="2">请选择审批链类型</th></tr>
    				
    				<!-- 
    				<tr>
    					<td class="tdLeft">年份</td>
							<td class="tdRight">
								<div class="ui-widget">
									<select id="valiYearSelect2" name="dataYear">
										<option value="${thisYear }">--${thisYear}--</option>
										<option value="${thisYear+1 }">--${thisYear+1}--</option>
									</select>
								</div>
						</td>
    				</tr>
    				 -->
    				<tr>
						<td class="tdLeft">审批链类型<input type="hidden" name="dataYear" value="${thisYear }"/></td>
						<td class="tdRight">
							<label><input type="radio" name="aprvType" value="11" checked="checked">专项包</label>
							<label><input type="radio" name="aprvType" value="12">省行统购</label>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="tdWhite">
							<input type="button" value="下一步" onclick="noWhSkip();">
						</td>
					</tr>
    			</table>
    		</c:if>
    		<c:if test="${noWhTag == '03' }">
    			<table id="noUpTables" >
					<tr class="collspan-control">
						<th colspan="4">
							查询
						</th>
						<tr>
						<td class="tdLeft">费用承担部门编码</td>
						<td class="tdRight">
							<input id="dutyCode2"   name="dutyCode" maxlength="50" value="${selectInfo.dutyCode }" class="base-input-text" type="text"   />
<%-- 						<forms:OrgSelPlugin suffix="s20" rootNodeId="${selectInfo.org1Code}" initValue="${selectInfo.dutyCode}" jsVarGetValue="dutyCode2" jsVarName="dutyCode2" parentCheckFlag="false"/> --%>
						</td>
					</tr>
					<tr>
						<td colspan="4" class="tdWhite">
							<input type="button" onclick="selectDutyList('${selectInfo.dataYear}')" value="查找"/>
							<input type="button" onclick="backPage()" value="上一步"/>
							<input type="button" value="重置" onclick="resetAll();">
							<input type="button" value="批量维护" onclick="batchUp('${selectInfo.dataYear}');">
						</td>
					</tr>
			</table>
    		<table class="tableList" >
				<tr>
					<th  >费用承担部门名称</th>
					<th  >可维护数量</th>
					<th   >明细</th>
				</tr>
				<c:forEach items="${specTgDutyList}" var="dList" varStatus="i">
					<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
						<td>${dList.dutyCode }-${dList.dutyName }</td>
						<td>${dList.noUpCount }</td>
						<td>
							<div class="update"><a href="#" onclick="detail('${dList.dutyCode }','${dList.dutyName }','${selectInfo.dataYear }','12');" title='<%=WebUtils.getMessage("button.update")%>'></a></div>
						</td>
					</tr>
				</c:forEach>
			   <c:if test="${empty specTgDutyList}">
			    <tr>
				<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			   </c:if>					
			</table>
    		</c:if>
    		
    	</form>	
    	<p:page pageKey="specNoWh"/>
    </div>
   </c:if>
    <!-- 审批链因为监控指标合并导致的需修改的 -->
    <c:if test="${selectInfo.tabsIndex eq '2' }">
    <div id="tabs-3" style="padding: 0;">
    	<form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102" method="post" id="changeForm">
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
				<p:button funcId="08120102" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
    		<table class="tableList" id="listTab1">
		<tr>
		<th style="width:20%">新监控指标代码</th>
			<th style="width:20%">新监控指标名称</th>
			<th style="width:10%" >物料代码</th>
			<th style="width:30%" >物料名称</th>
			<th style="width:10%">审批链类型</th>
			<th   align="center">操作</th>
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
    	<p:page pageKey="specChange"/>
    </div>
  </c:if>
  </div>
 
</body>
</html>