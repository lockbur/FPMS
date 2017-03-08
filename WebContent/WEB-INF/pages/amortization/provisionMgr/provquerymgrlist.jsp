	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预提信息查询管理</title>

<style type="text/css">
	.tbHead th{
		text-align: center;
	}
	.tableList tr td{
		text-align: center;
	}
	.ui-datepicker-calendar{
		display:none;
	}
</style>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#provFlagSelect").combobox();
	$("#provDataFlagSelect").combobox();
	//设置时间插件
	$( "#operDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	});
	$( "#checkDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	});
	$( "#feeYyyymm" ).datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel:true,
	    dateFormat:"yy-mm-dd",
// 	    onOpen:function(dateText){
// 	    	console.info(dataText);
// 	    	console.info($(this));
// 	    	if(""!=dateText||null !=dateText){
// 	    		$(this).datepicker('defaultDate',new Date(dateText.substr(0,4),dateText.substr(4,6),1));
// 	    	}
// 	    },
// 	    defaultDate:new Date("2015","7","1"), 
	    
	    	//$(this).datepicker('getDate'),
	    onClose:function(dateText,inst){
	    	
// 	    	console.info(inst);
// 			console.info($("#feeYyyymm").val());
// 			console.info($(this).datepicker('getDate'));
			var month = inst.selectedMonth;
			var year = inst.selectedYear;
	    	var date=new Date(year,month,1);
	    	$(this).datepicker('setDate',date);
	    	
	    	var dateM=(date.getMonth()+1).toString();
	    	if (dateM.length==1) {
				dateM="0"+dateM;
			}
	    	$(this).val(date.getFullYear()+"-"+dateM);
	    },
	});
}
function resetAll() {
	$("select").val("");
	$(":text").val("");
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

//查看预提详情
function getProvDetail( provisionId , feeCglCode , provisionAmt , prepaidAmt ){
	//getProvDetail('${provPassBean.provMgrId}','${provPassBean.feeCglCode}','${provPassBean.provisionAmt}','${provPassBean.prepaidAmt}')"
	var form = document.forms[0];
	form.action="<%=request.getContextPath()%>/amortization/provisionMgr/getProvDetail.do?<%=WebConsts.FUNC_ID_KEY %>=04050501&provMgrId="+provisionId+"&feeCglCode="+feeCglCode+"&provisionAmt="+provisionAmt+"&prepaidAmt="+prepaidAmt;
	App.submit(form);
}

$(function(){
	
})

// function someTest(){
// 	alert($("#haoxu").val());
// 	$("#haoxu").val();
// }

</script>
</head>

<body>
<p:authFunc funcArray="040505,04050501"/>
<form action="" method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				预提查询
				<input type="hidden" name="org1Code" value="${provision.org1Code}">
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${provision.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">受益年月</td>
			<td class="tdRight">
				<input type="text" id="feeYyyymm" name="feeYyyymm" value="${provision.feeYyyymm}"  class="base-input-text" maxlength="6"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">是否预提</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="provFlagSelect" name="provFlag">
						<option value="">请选择</option>
						<option value="0" <c:if test="${provision.provFlag == '0'}">selected="selected"</c:if> >不需要预提</option>
						<option value="1" <c:if test="${provision.provFlag == '1'}">selected="selected"</c:if> >需要预提</option>
					</select>
				</div>
			</td>
			<td class="tdLeft">预提状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="provDataFlagSelect" name="dataFlag">
						<option value="">请选择</option>
						<option value="0" <c:if test="${provision.dataFlag == '0'}">selected="selected"</c:if> >待处理</option>
						<option value="1" <c:if test="${provision.dataFlag == '1'}">selected="selected"</c:if> >待复核</option>
						<option value="2" <c:if test="${provision.dataFlag == '2'}">selected="selected"</c:if> >复核退回</option>
						<option value="3" <c:if test="${provision.dataFlag == '3'}">selected="selected"</c:if> >复核通过</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">责任中心</td>
			<td class="tdRight" colspan="3" title="${provision.createDept}">
				<forms:OrgSelPlugin rootNodeId="${provision.org1Code}" initValue="${provision.createDept}" jsVarGetValue="createDept"  radioFlag="false" leafOnlyFlag="true"  triggerEle="#queryForm createDept,queryCreateDeptList::name"/>
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" dynamicUpdateFlag="true"  --%>
<%--  									jsVarName="treePlugin1" rootNodeId="${provision.org1Code}" leafOnlyFlag="true"  --%>
<%--  									initValue="${provision.createDept}" changeFun="" triggerEle="#queryForm createDept,queryCreateDeptList::name"/>  --%>
 				<input class="base-input-text" type="text" id="createDept" name="createDeptCutShow" readonly="readonly" valid errorMsg="请选择合同创建部门"/>
 				<input type="hidden" id="queryCreateDeptList" name="createDepts">
			</td>
		</tr>
		
		
<!-- 		<tr> -->
<!-- 			<td class="tdLeft">录入用户</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" id="operUser" name="operUser" value="${provision.operUser}" class="base-input-text" maxlength="40"/> --%>
<!-- 			</td> -->
<!-- 			<td class="tdLeft">录入日期</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" id="operDate" name="operDate" value="${provision.operDate}" class="base-input-text" maxlength="8"/> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		
<!-- 		<tr> -->
<!-- 			<td class="tdLeft">复核用户</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" id="checkUser" name="checkUser" value="${provision.checkUser}" class="base-input-text" maxlength="40"/> --%>
<!-- 			</td> -->
<!-- 			<td class="tdLeft">复核日期</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" id="checkDate" name="checkDate" value="${provision.checkDate}" class="base-input-text" maxlength="8"/> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040505" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
<!-- 				<input type="button" value="Test" onclick="someTest();"> -->
				
			</td>
		</tr>
	</table>
	<br>
	<div id="queryListAreaDiv">
		<table class="tableList" id="queryResultTb" width="95%">
			<tr class="tbHead">
				<th width="14%">合同号</th>
				<th width="8%">合同事项</th>
				<th width="8%">受益年月</th>
				<th width="11%">物料编码</th>
				<th width="16%">监控指标</th>
				<th width="8%">核算码</th>
				<!-- <th width="12%">AP发票校验状态</th> -->
				<th width="8%">预提金额</th>
				<th width="8%">待摊金额</th>
				<th width="18%">预提详情</th>
			</tr>
			
			
			<c:forEach items="${provPassList}" var="provPassBean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td title="【受益年月：${provPassBean.feeYyyymm}——复核状态：${provPassBean.dataFlag}】">${provPassBean.cntNum}</td>
					<td>${provPassBean.cntName}</td>
					<td title="${provPassBean.provMgrId}">${provPassBean.feeYyyymm}</td>
					<td>${provPassBean.matrCode}</td>
					<td>${provPassBean.montCode}</td>
					<td title="合同创建部门：${provPassBean.createDept}">${provPassBean.feeCglCode}</td>
					<%-- <td>${provPassBean.importStatus}</td> --%>
					<td>${provPassBean.provisionAmt}</td>
					<td>${provPassBean.prepaidAmt}</td>
<%-- 					<td>${provPassBean.operUser}-${provPassBean.operDate}</td> --%>
<%-- 					<td>${provPassBean.checkUser}-${provPassBean.checkDate}</td> --%>
					<td><input type="button" onclick="getProvDetail('${provPassBean.provMgrId}','${provPassBean.feeCglCode}','${provPassBean.provisionAmt}','${provPassBean.prepaidAmt}')" value="详情"></td>
				</tr>
			</c:forEach>
			
			<c:if test="${empty provPassList}">
				<tr>
					<td colspan="10" style="text-align: center;"><span class="red">没有查询到相关的的预提信息！</span></td>
				</tr>
			</c:if>
			
		</table>
		
	</div>
</form>
<p:page/>
</body>
</html>