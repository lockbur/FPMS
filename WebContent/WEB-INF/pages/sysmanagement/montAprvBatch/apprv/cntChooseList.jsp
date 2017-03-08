<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
a:HOVER{  		
	color: red; 
}
</style>
<script type="text/javascript">
//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$("select[id='montNameNewId']").combobox();
	var tabs = $( "#tabs" ).tabs({
// 		active: $('#tabsIndex').val(),
// 		activate: function(){
// 			$('#tabsIndex').val($(this).tabs('option', 'active'));
// 		}
	});
}
//全选/反选所有数据
function checkAll(flag){
	if($("#checkPageAll").is(":checked")){
		$("#checkPageAll").removeAttr("checked");
	}
	if($("#flag").val()=="0"){//是全选当页数据的时候点击全选所有
		$("#flag").val(flag);
		return ;
	}
	if(flag){
		if($("#checkItem").is(":checked")){
			$("#checkItem").removeAttr("checked");
		}else{
			$("#checkItem").prop("checked","true");
		}
		if($("#checkItem").is(":checked")){
			$("input[name='cntNumSubId']").prop('checked','true');
			$("#flag").val(flag);
		}else{
			$("input[name='cntNumSubId']").removeAttr("checked");
			$("#flag").val("");//取消全选当前页数据
		}
	}else{
		if($("#checkItem").is(":checked")){
			$("input[name='cntNumSubId']").prop('checked','true');
			$("#flag").val("0");//全选当前页数据
		}else{
			$("input[name='cntNumSubId']").removeAttr("checked");
			$("#flag").val("");//取消全选当前页数据
		}
	}
}
//全选/反选当页数据
function checkPageAll(flag){
	if($("#checkAll").is(":checked")){
		$("#checkAll").removeAttr("checked");
	}
	if($("#flag").val()=="1"){//是全选所有数据的时候点击全选当页
		$("#flag").val(flag);
		return ;
	}
	if(flag){
		if($("#checkItem").is(":checked")){
			$("#checkItem").removeAttr("checked");
		}else{
			$("#checkItem").prop("checked","true");
		}
		if($("#checkItem").is(":checked")){
			$("input[name='cntNumSubId']").prop('checked','true');
			$("#flag").val(flag);
		}else{
			$("input[name='cntNumSubId']").removeAttr("checked");
			$("#flag").val("");//取消全选当前页数据
		}
	}else{
		if($("#checkItem").is(":checked")){
			$("input[name='cntNumSubId']").prop('checked','true');
			$("#flag").val("0");//全选当前页数据
		}else{
			$("input[name='cntNumSubId']").removeAttr("checked");
			$("#flag").val("");//取消全选当前页数据
		}
	}
}
//数据中有一个被点击就改变全选的值
function changeCheck(obj){
	if($("#checkPageAll").is(":checked") || $("#checkAll").is(":checked")){//存在全选的
		if(!$(obj).is(":checked")){
			$("#checkItem").removeAttr("checked");
			$("#checkAll").removeAttr("checked");
			$("#checkPageAll").removeAttr("checked");
			$("#flag").val("");//取消全选当前页数据
		}
	}else{
		//判断当页是否全选
		if($(obj).is(":checked")){
			var flag = true;
			$("input[name='cntNumSubId']").each(function(){
				if(!$(this).is(":checked")){
					flag = false ;
				}
			});
			if(flag){
				$("#checkItem").prop("checked","true");
				$("#checkPageAll").prop("checked","true");
				$("#flag").val("0");//全选当前页数据
			}
		}
	}
}
//处理
function checkDeal(cntNum,montCodeOld,matrCode){
	if(cntNum){//单个处理不用校验
		$("#cntNum").val(cntNum);
		$("#montCodeOld").val(montCodeOld);
		$("#matrCode").val(matrCode);
		toAudit("单个");
	}else{//多个提交需校验是否有被选中的值
		//校验
		if($('input:checkbox[name="cntNumSubId"]:checked').val()== null){
			App.notyError("请选中需要审核的数据！");
			return false;
		}
		var str;
		if($("#flag").val()=="0"){
			str = "确认批量处理当页的数据吗？";
		}else if($("#flag").val()=="1"){
			str = "确认批量处理所有的数据吗？";
		}else{
			str = "确认批量处理选中的数据吗？";
		}
		$( "<div>"+str+"</div>" ).dialog({
			resizable: false,
			height:150,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					$( this ).dialog( "close" );
					toAudit("批量");
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}
//通过（单个和批量）
// function checkPass(cntNum,montCodeOld,matrCode){//cntNum判断是单个还是批量
// 	if(cntNum){//单个提交不用校验
// 		$("#cntNum").val(cntNum);
// 		$("#montCodeOld").val(montCodeOld);
// 		$("#matrCode").val(matrCode);
<%-- 		$('#aprovForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/checkPass.do?<%=WebConsts.FUNC_ID_KEY%>=0811030108'); --%>
// 		App.submit($('#aprovForm'));
// 	}else{//多个提交需校验是否有被选中的值
// 		//校验
// 		if($('input:checkbox[name="cntNumSubId"]:checked').val()== null){
// 			App.notyError("请选中需要审核的数据！");
// 			return false;
// 		}
// 		var str;
// 		if($("#flag").val()=="0"){
// 			str = "确认通过当页的数据吗？";
// 		}else if($("#flag").val()=="1"){
// 			str = "确认通过所有的数据吗？";
// 		}else{
// 			str = "确认通过选中的数据吗？";
// 		}
// 		$( "<div>"+str+"</div>" ).dialog({
// 			resizable: false,
// 			height:150,
// 			modal: true,
// 			dialogClass: 'dClass',
// 			buttons: {
// 				"确定": function() {
<%-- 					$('#aprovForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/checkPass.do?<%=WebConsts.FUNC_ID_KEY%>=0811030108'); --%>
// 					App.submit($('#aprovForm'));
// 				},
// 				"取消": function() {
// 					$( this ).dialog( "close" );
// 				}
// 			}
// 		});
// 	}
// }

// //退回（单个和批量）
// function checkBack(cntNum,montCodeOld,matrCode){//cntNum判断是单个还是批量
// 	if(cntNum){//单个提交不用校验
// 		$("#cntNum").val(cntNum);
// 		$("#montCodeOld").val(montCodeOld);
// 		$("#matrCode").val(matrCode);
<%-- 		$('#aprovForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/checkBack.do?<%=WebConsts.FUNC_ID_KEY%>=0811030109'); --%>
// 		App.submit($('#aprovForm'));
// 	}else{//多个提交需校验是否有被选中的值
// 		//校验
// 		if($('input:checkbox[name="cntNumSubId"]:checked').val()== null){
// 			App.notyError("请选中需要审核的数据！");
// 			return false;
// 		}
// 		var str;
// 		if($("#flag").val()=="0"){
// 			str = "确认退回当页的数据吗？";
// 		}else if($("#flag").val()=="1"){
// 			str = "确认退回所有的数据吗？";
// 		}else{
// 			str = "确认退回选中的数据吗？";
// 		}
// 		$( "<div>"+str+"</div>" ).dialog({
// 			resizable: false,
// 			height:150,
// 			modal: true,
// 			dialogClass: 'dClass',
// 			buttons: {
// 				"确定": function() {
<%-- 					$('#aprovForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/checkBack.do?<%=WebConsts.FUNC_ID_KEY%>=0811030109'); --%>
// 					App.submit($('#aprovForm'));
// 				},
// 				"取消": function() {
// 					$( this ).dialog( "close" );
// 				}
// 			}
// 		});
// 	}
	
// }
//弹窗进行审批意见
function toAudit(str){
	$("#auditDiv").dialog({
		title:'合同勾选复核处理('+str+')',
		autoOpen: true,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				var auditMemo = $("#auditMemo").val();
				$("#auditMemoSubmit").val(auditMemo);
				//判断radio是否被选中
				if($('input:radio[name="dealFlag"]:checked').val()==null){
					App.notyError("请先选择通过或退回!");
					return false;
				}
				var url ;
				if($('input:radio[name="dealFlag"]:checked').val() == 1){//通过
					$("#auditMemo").css('border-color','');
					$("#auditMemo").removeAttr('valid');
					$("#auditFlag").val("01");
				}else if($('input:radio[name="dealFlag"]:checked').val() == 2){//退回
					$("#auditMemo").attr('valid','');
					$("#auditFlag").val("02");
				}
				var orgType = $("#orgType").val();
				if("01"==orgType){
					url = '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/audit.do?<%=WebConsts.FUNC_ID_KEY%>=0811030103';
				}else{
					url = '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/audit.do?<%=WebConsts.FUNC_ID_KEY%>=0811030203';
				}
				//校验
				if(!App.valid("#aprovForm")){return;}
				
				$('#aprovForm').attr('action',url);
				$('#aprovForm').submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		

		}
	});
}
//明细
function gotoDtl(cntNum)
{
	var form = $("#cntDetail")[0];
	var orgType = $("#orgType").val();
	if(orgType == '01'){
		form.action='<%=request.getContextPath()%>/contract/query/cntDtl.do?<%=WebConsts.FUNC_ID_KEY %>=0811030108&cntNum='+cntNum;
	}else{
		form.action='<%=request.getContextPath()%>/contract/query/cntDtl.do?<%=WebConsts.FUNC_ID_KEY %>=0811030208&cntNum='+cntNum;
	}
	
	App.submit(form);
}
</script>
</head>
<body>
	<form action="" id="cntDetail" method="post">
		<input type="hidden" id="batchNo" name="batchNo" value="${batchNo}">
		<input type="hidden" id="orgType" name="orgType" value="${orgType}">
	</form>
	<div id="tabs" style="border: 0;">
		<ul>
			<li><a href="#tabs-1">待审核(${fn:length(list1)}个)</a></li>
			<li><a href="#tabs-2">退回(${fn:length(list2)}个)</a></li>
			<li><a href="#tabs-3">审核通过(${fn:length(list3)}个)</a></li>
		</ul>
		<div id="tabs-1" style="padding: 0;">
			<form method="post" id="aprovForm">
				<p:token/>
				<input type="hidden" id="cntNum" name="cntNum" value="">
				<input type="hidden" id="montCodeOld" name="montCodeOld" value="">
				<input type="hidden" id="matrCode" name="matrCode" value="">
				<input type="hidden" id="batchNo" name="batchNo" value="${batchNo}">
				<input type="hidden" id="orgType" name="orgType" value="${orgType}">
				<input type="hidden" value="" id="flag" name="flag" >
				<input type="hidden" value="" id="auditMemoSubmit" name="auditMemo" >
				<input type="hidden" value="" id="auditFlag" name="auditFlag" >
				<input type="hidden" value="${status }" name="status" >
				<input type="hidden" value="${proType }" name="proType" >
				<table class="tableList">
					<tr>
						<th width="3%"><input type="checkbox" id="checkItem"  disabled="disabled"></th>
						<th width="15%">合同号</th>
						<th width="15%">物料编码</th>
						<th width="20%">物料名称</th>
						<th width="20%">旧监控指标</th>
						<th width="20%">新监控指标</th>
						<th width="7%">操作</th>
					</tr>
					<c:if test="${!empty list1}">
						<c:forEach items="${list1}" var="bean" varStatus="status">
							<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
								<td><input type="checkbox" name='cntNumSubId' value="${bean.cntNum }:${bean.matrCode}:${bean.montCodeOld}" onclick="changeCheck(this)"></td>
								<td><a href="javascript:void(0);" onclick="gotoCntDtl('${bean.cntNum }');" class="text_decoration">${bean.cntNum }</a></td>
								<td>${bean.matrCode }</td>
								<td>${bean.matrName }</td>
								<td>${bean.montNameOld }</td>
								<td>${bean.montNameNew }</td>
								<td>
									<c:if test="${isDetail ne '1' }">
									<input type="button" value="处理" onclick="checkDeal('${bean.cntNum}','${bean.montCodeOld }','${bean.matrCode }');">
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty list1}">
						<tr>
							<td colspan="9" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
						</tr>
					</c:if>
				</table>	
			</form>
			<p:page pageKey="pageKey1"/>
			<br>
			<c:if test="${isDetail ne '1' }">
			<div style="float: left;">
				<label><input type="checkbox" value="全选/反选当页数据" id="checkPageAll" onclick="checkPageAll('0')"/>全选/反选当页数据</label>
				<label><input type="checkbox" value="全选/反选所有数据" id="checkAll" onclick="checkAll('1')"/>全选/反选所有数据</label>
			</div>
			</c:if>
			<br><br>
			<div>
			<c:if test="${isDetail ne '1' }">
				<input type="button" value="批量处理" onclick="checkDeal();">
			</c:if>
				
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</div>
		</div>
		
		<div id="tabs-2" style="padding: 0;">
			<form method="post">
				<table class="tableList">
					<tr>
						<th width="20%">合同号</th>
						<th width="20%">物料编码</th>
						<th width="20%">物料名称</th>
						<th width="20%">旧监控指标名称</th>
						<th width="20%">新监控指标名称</th>
					</tr>
					<c:if test="${!empty list2}">
						<c:forEach items="${list2}" var="bean" varStatus="status">
							<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
								<td><a href="javascript:void(0);" onclick="gotoCntDtl('${bean.cntNum }');" class="text_decoration">${bean.cntNum }</a></td>
								<td>${bean.matrCode }</td>
								<td>${bean.matrName }</td>
								<td>${bean.montNameOld }</td>
								<td>${bean.montNameNew }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty list2}">
						<tr>
							<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
						</tr>
					</c:if>
				</table>	
			</form>
			<p:page pageKey="pageKey2"/>
			<br><br>
			<div>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</div>
		</div>
		
		<div id="tabs-3" style="padding: 0;">
			<form method="post" >
				<table class="tableList">
					<tr>
						<th width="20%">合同号</th>
						<th width="20%">物料编码</th>
						<th width="20%">物料名称</th>
						<th width="20%">旧监控指标名称</th>
						<th width="20%">新监控指标名称</th>
					</tr>
					<c:if test="${!empty list3}">
						<c:forEach items="${list3}" var="bean" varStatus="status">
							<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
								<td>
								<a href="javascript:void(0);" onclick="gotoCntDtl('${bean.cntNum }');" class="text_decoration">${bean.cntNum }</a>
								</td>
								<td>${bean.matrCode }</td>
								<td>${bean.matrName }</td>
								<td>${bean.montNameOld }</td>
								<td>${bean.montNameNew }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty list3}">
						<tr>
							<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
						</tr>
					</c:if>
				</table>	
			</form>
			<p:page pageKey="pageKey3"/>
			<br><br>
			<div>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</div>
		</div>
	</div>
<!-- 审批弹出框 -->
<div id="auditDiv" style="display: none;">
		<table width="98%" id="auditTable" >
			 <tr>
				<td align="left" colspan="2">
					<input type="hidden" id="isAgree" name="isAgree" value=""/>
					<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv'); ">通过</label><input type="radio" id="authdealFlag1" name="dealFlag" value="1" >
						<label for="authdealFlag2" onclick="App.radioCheck(this,'authdealFlagDiv'); " >退回</label><input type="radio" id="authdealFlag2" name="dealFlag" value="2">
					</div>
				</td>
			</tr>
			<tr  >
				<td align="left" colspan="2">
					<br>审批意见(<span id="authdealCommentSpan">0/500</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,500,'authdealCommentSpan')" id="auditMemo"  rows="7" cols="45" valid errorMsg="请输入转发意见。"></textarea>
				</td>
			</tr>
		</table>
</div>
</body>
</html>