<%@page import="com.forms.platform.web.WebUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.forms.platform.web.consts.WebConsts"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同勾选详情</title>
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
	//org2CodeTree.afterDynamicBack();
}

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$("select[id='montNameNew']").combobox();
	var tabs = $( "#tabs" ).tabs({
		active: $('#tabIndex').val(),
		activate: function(){
			$('#tabIndex').val($(this).tabs('option', 'active'));
		}
	}); 
}




//全选
function checkAll(){
	if($("#checkItem").is(":checked")){
		$("input[name='cntNumSubIdMont']").prop('checked','true');
	}else{
		$("input[name='cntNumSubIdMont']").removeAttr("checked");
	}
}
function choiceCnt(){
	if($("input[name='cntNumSubIdMont']:checked").size() < 1){
		App.notyError("请至少勾选一个合同！");
		return false;
	}
	var cntNumSubIdMont=$("input[name='cntNumSubIdMont']:checked");
	for(var i=0;i<$("input[name='cntNumSubIdMont']:checked").size();i++){
		var montNameNew=$(cntNumSubIdMont[i]).parent().parent().find("select").val();
		if(montNameNew==null||montNameNew==''){
			App.notyError("勾选的合同有未选择的监控指标！");
			return false;
		}
	}
	$( "<div>确认提交勾选的合同?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				var form = $("#choiceForm")[0];
				var orgType="${bean.orgType}";
				//如果为分行
				if(orgType=='01'){
					form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/cntchoice/choiceCnt.do?<%=WebConsts.FUNC_ID_KEY%>=0811040201";
				}
				//省行
				else{
					form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/cntchoice/choiceCnt.do?<%=WebConsts.FUNC_ID_KEY%>=0811040102";
				}
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}
function changeCheckBox(obj){
	var cntNumSubIdMontTr=$(obj).parent().parent().parent();
	var cntNum=$(cntNumSubIdMontTr).find("input[id='cntNum']").val();
	var subId=$(cntNumSubIdMontTr).find("input[id='subId']").val();
	var mont=$(obj).val();
	$(cntNumSubIdMontTr).find("input[id='cntNumSubIdMont']").val(cntNum+":"+subId+":"+mont);
}
//明细
function gotoDtl(cntNum)
{
	var form = $("#cntDetail")[0];
	form.action='<%=request.getContextPath()%>/contract/query/cntDtl.do?<%=WebConsts.FUNC_ID_KEY %>=0811040103&cntNum='+cntNum;
<%-- 	form.action='<%=request.getContextPath()%>/contract/query/mainpage.do?<%=WebConsts.FUNC_ID_KEY%>=03020606&cntNum='+cntNum; --%>
	App.submit(form);
}
</script>
</head>
<body>
<form action="" id="cntDetail" method="post">
		<input type="hidden" id="batchNo" name="batchNo" value="${bean.batchNo}">
		<input type="hidden" id="orgType" name="orgType" value="${bean.orgType}">
		<input type="hidden"  name="isChoice" value="yes">
		<input type="hidden"  name="isCheck" value="${bean.isCheck}">
	</form>
<p:authFunc funcArray="08120102,0812010201,0811040102"/>
	<div id="tabs" style="border: 0;">
	<ul>
		<li><a href="#tabs-1">退回(${fn:length(waitManageBackList)}个)</a></li>
		<li><a href="#tabs-2">待审核(${fn:length(waitCheckList)}个)</a></li>
		<li><a href="#tabs-3">审核通过(${fn:length(checkPassList)}个)</a></li>
	</ul>
    <div id="tabs-1" style="padding: 0;">	
   <form action="" method="post" id="choiceForm">
	 	<input type="hidden" id="batchNo" name="batchNo" value="${bean.batchNo}">
	 	<input type="hidden" id="orgType" name="orgType" value="${bean.orgType}">
	 <table class="tableList" id="listTab">
		<tr>
			<th style="width:10%">合同号</th>
			<th style="width:10%" >物料编码</th>
			<th style="width:20%">物料名称</th>
			<th style="width:10%">旧监控指标代码</th>
			<th style="width:20%">旧监控指标名称</th>
			<th style="width:10%">新监控指标代码</th>
			<th style="width:20%">新监控指标名称</th>
		</tr>
		<c:forEach items="${waitManageBackList}" var="aList" varStatus="i">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<input type="hidden" id="cntNum" value="${aList.cntNum}">
			<input type="hidden" id="subId" value="${aList.subId}">
			<td>
			<a href="javascript:void(0);" onclick="gotoCntDtl('${aList.cntNum }');" class="text_decoration">${aList.cntNum }</a>
			</td>
			<td >${aList.matrCode}</td>
			<td >
				${aList.matrName }
			</td>
			<td >
				${aList.montCodeOld}
			</td>
			<td >
				${aList.montNameOld}
			</td>
			<td>
				${aList.montCodeNew }
			</td>
			<td>
				${aList.montNameNew }
			</td>
		</tr>
		</c:forEach>
		<c:if test="${empty waitManageBackList}">
	    <tr>
		<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>		
	</table>
	</form>
	<p:page pageKey="waitManageBack"/>
  	 <input type="button" value="返回" onclick="backToLastPage('${uri}')">
   </div>
    <div id="tabs-2" style="padding: 0;">
    	<form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102" method="post" id="noUpdateForm">
    		<table class="tableList" >
				<tr>
					<th style="width:10%">合同号</th>
					<th style="width:10%" >物料编码</th>
					<th style="width:20%">物料名称</th>
					<th style="width:10%">旧监控指标代码</th>
					<th style="width:20%">旧监控指标名称</th>
					<th style="width:10%">新监控指标代码</th>
					<th style="width:20%">新监控指标名称</th>
				</tr>
				<c:forEach items="${waitCheckList}" var="dList" varStatus="i">
					<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>
				<a href="javascript:void(0);" onclick="gotoCntDtl('${dList.cntNum }');" class="text_decoration">${dList.cntNum }</a>
				</td>
			<td >${dList.matrCode}</td>
			<td >
				${dList.matrName }
			</td>
			<td >
				${dList.montCodeOld}
			</td>
			<td >
				${dList.montNameOld}
			</td>
			<td>
				${dList.montCodeNew }
			</td>
			<td>
				${dList.montNameNew }
			</td>
			</tr>
				</c:forEach>
			   <c:if test="${empty waitCheckList}">
			    <tr>
				<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			   </c:if>					
			</table>
    		
    	</form>	
    	<p:page pageKey="waitCheck"/>
    	 <input type="button" value="返回" onclick="backToLastPage('${uri}')">
    </div>
    <div id="tabs-3" style="padding: 0;">
    	<form action="<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102" method="post" id="changeForm">
    		<table class="tableList" id="listTab1">
		<tr>
					<th style="width:10%">合同号</th>
					<th style="width:10%" >物料编码</th>
					<th style="width:20%">物料名称</th>
					<th style="width:10%">旧监控指标代码</th>
					<th style="width:20%">旧监控指标名称</th>
					<th style="width:10%">新监控指标代码</th>
					<th style="width:20%">新监控指标名称</th>
					
				</tr>
		<c:forEach items="${checkPassList}" var="aList" varStatus="i">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td>
			<a href="javascript:void(0);" onclick="gotoCntDtl('${aList.cntNum }');" class="text_decoration">${aList.cntNum }</a>
			</td>
			<td >${aList.matrCode}</td>
			<td >
				${aList.matrName }
			</td>
			<td >
				${aList.montCodeOld}
			</td>
			<td >
				${aList.montNameOld}
			</td>
			<td>
				${aList.montCodeNew }
			</td>
			<td>
				${aList.montNameNew }
			</td>
		</tr>
		</c:forEach>
		<c:if test="${empty checkPassList}">
	    <tr>
		<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>		
	</table>
    </form>	
    	<p:page pageKey="checkPass"/>
    	 <input type="button" value="返回" onclick="backToLastPage('${uri}')">
    </div>
  </div>
 
  	
</body>
</html>