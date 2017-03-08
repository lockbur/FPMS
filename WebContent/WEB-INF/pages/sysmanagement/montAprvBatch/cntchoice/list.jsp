<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>省行合同勾选</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#proType").combobox();
	$("#status").combobox();
	$("#subMontTypeSelect").combobox();
	$("#AuditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"确定": function() {
				//校验
				if(!App.valid("#auditForm")){return;}
					$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/audit.do?<%=WebConsts.FUNC_ID_KEY%>=0811030103&orgType=01');
					App.submit($('#auditForm'));
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}
//勾选合同
function toChoice(batchNo,isCheck){
	var form = $("#apprvFormSearch")[0];
	var orgType="${searchInfo.orgType}";
	//如果为省行
	if(orgType=='01'){
		form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/cntchoice/choiceDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0811040101&orgType=01&batchNo="+batchNo+"&isCheck="+isCheck;
	}
	else{
		form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/cntchoice/choiceDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0811040201&orgType=02&batchNo="+batchNo+"&isCheck="+isCheck;
	}
	App.submit(form);
}

function toSelectResult(s){
	if(s==2){
		$(".check").empty();
		$(".check").html('<td align="left"><br><span style="color:red;">*</span>信息备注(<span id="authdealCommentSpan">0/500</span>)：<textarea class="base-textArea" id="memo" name="memo" rows="7" cols="45" valid errorMsg="请输入意见。"></textarea></td>');
		return;
	}else{
		$(".check").empty();
		$(".check").html('<td align="left"><br>信息备注(<span id="authdealCommentSpan">0/500</span>)：<textarea class="base-textArea"id="memo" name="memo" rows="7" cols="45"></textarea></td>');
		return;
	}
	return;
}


function fileDownload(batchNo){
	var data = {};
	data["batchNo"] = batchNo;
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/montAprvBatch/apprv/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>=0811030102",
		{data:data,async:false},
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#downloadForm")[0];
				form.action = "<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/Export.do?<%=WebConsts.FUNC_ID_KEY%>=0811030101&batchNo="+data.batchNo;
				form.submit();
			}else{
				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
				App.notyError(checkMsg);
			}
		});
}

function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#statusDiv").find("label").eq(0).click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="08110401,0811030103,08110402"/>
<form id="downloadForm" method="post"></form>
<form action="" method="post" id="apprvFormSearch">
	<p:token/>
		<table>	
			<tr class="dis">
				<th colspan="4">
				合同勾选查询
				</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">数据类型</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
					<select id="proType" name="proType" class="erp_cascade_select" >
						<option value="">-请选择-</option>
						<option value="01" <c:if test="${searchInfo.proType=='01'}">selected="selected"</c:if>>监控指标</option>
						<option value="02" <c:if test="${searchInfo.proType=='02'}">selected="selected"</c:if>>审批链</option>
					</select>
				</div>	
				</td>
				<td class="tdLeft" width="25%">子类型</td>
				<td class="tdRight" width="25%" >
				<div class="ui-widget">
					<c:if test="${searchInfo.orgType eq '01' }">
							<%-- <select id="subMontTypeSelect" name="subType">
								<option value="">-请选择-</option>
								<option value="11"<c:if test="${searchInfo.subType=='11'}">selected="selected"</c:if>>专项包</option>
								<option value="12"<c:if test="${searchInfo.subType=='12'}">selected="selected"</c:if>>省行统购资产</option>
							</select> --%>
							<select id="subMontTypeSelect" name="subType">
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')" selectedValue="${searchInfo.subType}"/>
							</select>
					</c:if>
					<c:if test="${searchInfo.orgType eq '02' }">
						<%-- <select id="subMontTypeSelect" name="subType">
							<option value="">-请选择-</option>
							<option value="21"<c:if test="${searchInfo.subType=='21'}">selected="selected"</c:if>>非省行统购资产</option>
							<option value="22"<c:if test="${searchInfo.subType=='22'}">selected="selected"</c:if>>非专项包费用</option>
						</select> --%>
						<select id="subMontTypeSelect" name="subType">
							<option value="">-请选择-</option>
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='21' or PARAM_VALUE='22')" selectedValue="${searchInfo.subType}"/>
						</select>
					</c:if>
				</div>
				</td>
			</tr>
			<tr id="subMontType">
				<td class="tdLeft">年份</td>
				<td class="tdRight">
					<input type="text" value="${searchInfo.dataYear}" id="dataYear" name="dataYear" class="base-input-text"/>
				</td>
				<td class="tdLeft"></td>
				<td class="tdRight">
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
			<c:if test="${searchInfo.orgType eq '01' }">
			<p:button funcId="08110401" value="查找"/>
			</c:if>
			<c:if test="${searchInfo.orgType eq '02' }">
			<p:button funcId="08110402" value="查找"/>
			</c:if>
				
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
		</table>
		<br>
		<table class="tableList">
			<tr>
				<th width="7%">年份</th>
				<th width="17%">导入类型</th>
				<th width="21%">源文件名</th>
				<th width="10%">数据类型</th>
				<th width="11%">子类型</th>
				<th width="7%">状态</th>
				<th width="7%">操作人</th>
				<th width="10%">操作</th>
			</tr>
			<c:forEach items="${list}" var="item">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td>${item.dataYear}</td>
					<td>
					${item.instMemo }
					</td>
					<td><a style="cursor:pointer;" onclick="fileDownload('${item.batchNo}');">${item.sourceFileName}</a></td>
					<td>
						<c:if test="${item.proType=='01'}">监控指标</c:if>
						<c:if test="${item.proType=='02'}">审批链</c:if>
					</td>
					<td>
						<%-- <c:if test="${item.subType=='11'}">专项包</c:if>
						<c:if test="${item.subType=='12'}">省行统购资产</c:if>
						<c:if test="${item.subType=='21'}">非省行统购资产</c:if>
						<c:if test="${item.subType=='22'}">非专项包费用</c:if> --%>
						${item.subTypeName }
					</td>
					<td>${item.cntStatusName}</td>
					<td>${item.instUser}</td>	
					<td>
						<c:if test="${item.cntStatus=='C1' || item.cntStatus=='C3'}">
							<input type="button" value="经办" onclick="toChoice('${item.batchNo}','waitChoice')"></input>
						</c:if>
						<c:if test="${item.cntStatus=='C2'}">
							<input type="button" value="详情" onclick="toChoice('${item.batchNo}','waitCheck')"></input>
						</c:if>
					</td>			
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
			<tr>
				<td colspan="8" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		</table>
		
</form>


<p:page/>
</body>
</html>