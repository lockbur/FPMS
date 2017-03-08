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
<title>省行数据复核</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#proType").combobox();

	$("#proType+input").focus();
	$("#proType+input").blur();

	
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
				var flag1  = $("#flag1").val();
				var flag = "";
				if(flag1 == "1"){
					flag= $("input[type=radio][name='auditFlag3']:checked").val();
				};
				if(flag1 == "2"){
					flag= $("input[type=radio][name='auditFlag2']:checked").val();
				};
				$("#auditFlag1").val(flag);
				if(flag == "02" && $("#memo").val() == ""){
					App.notyError("请填写审批意见！");
					return false;
				}
				if(!App.valid("#auditForm")){return;}
				var orgType = "${searchInfo.orgType}";
				if(orgType == '01'){
					$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/audit.do?<%=WebConsts.FUNC_ID_KEY%>=0811030103');
				}else{
					$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/audit.do?<%=WebConsts.FUNC_ID_KEY%>=0811030203');
				}
					App.submit($('#auditForm'));
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}

function toAudit(batchNo,proType,instType,subType,org21Code,dataYear,status,cntStatus,excelStatus,flag){
		//如果proType为01监控指标且cntStatus为C2的时候再进行复核合同勾选操作
		if(flag == '1'){
			$("#audit1").show();
			$("#audit2").hide();
		}
		if(flag == '2'){
			$("#audit2").show();
			$("#audit1").hide();
		}
		$("#flag1").val(flag);
		$("#memoDiv").html("");
		var orgType = $("#orgType").val();
		$("#auditBatchNo").val(batchNo);
		$("#auditOrgType").val(orgType);
		$("#auditStatus").val(status);
		$("#auditProType").val(proType);
		if(proType == '01' && status == 'C2'.toUpperCase()){
			var orgType = "${searchInfo.orgType}";
			if(orgType == '01'){
				$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/cntChooseList.do?<%=WebConsts.FUNC_ID_KEY%>=0811030107');
			}else{
				$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/cntChooseList.do?<%=WebConsts.FUNC_ID_KEY%>=0811030207');
			}
			
			App.submit($('#auditForm'));
		}else if(status == 'E4' || status == 'C6'){
			$("#auditBatchNo").val(batchNo);
			$("#auditInstType").val(instType);
			$("#auditSubType").val(subType);
			$("#auditOrg21Code").val(org21Code);
			$("#auditDataYear").val(dataYear);
			if(status == 'C6'){
				$("#auditFalseMemo").show();
				var memo = $("#"+batchNo+"_memo").val();
				$("#memoDiv").val(memo);
			}
			$("#AuditDiv").dialog( "option", "title", "复核" ).dialog( "open" );
			
		}else{
			App.notyError("数据有误,请检查修改！");
		}
		
		
}
function auditDetail(batchNo){
	var orgType = $("#orgType").val();
	$("#auditBatchNo").val(batchNo);
	$("#auditOrgType").val(orgType);
	var orgType = "${searchInfo.orgType}";
	if(orgType == '01'){
		$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/cntChooseList.do?<%=WebConsts.FUNC_ID_KEY%>=0811030110&isDetail=1');
	}else{
		$('#auditForm').attr('action', '<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/cntChooseList.do?<%=WebConsts.FUNC_ID_KEY%>=0811030210&isDetail=1');
	}
	
	
	App.submit($('#auditForm'));
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

function getCurrentYear( _obj , yearCount ){
	var currentYear = new Date().getFullYear();
	for(var i=0 ; i<yearCount ; i++){
		$(_obj).append("<option value='"+(currentYear-i+1)+"'>"+(currentYear-i+1)+" 年</option>");
	}
	var dataYear = '${searchInfo.dataYear}';
	$("#dataYear").val(dataYear);
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
<p:authFunc funcArray="08110301,0811030103,0811030110,08110302"/>
<form id="downloadForm" method="post">
	<input type="hidden" id="orgType" name="orgType" value="${searchInfo.orgType }"/>
</form>
<form action="" method="post" id="apprvFormSearch">
	<p:token/>
		<table>	
			<tr class="dis">
				<th colspan="4">
					查询
				</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">数据类型</td>
				<td class="tdRight" width="25%"  >
					<div class="ui-widget">
					<select id="proType" name="proType"  class="erp_cascade_select sel"  >
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
							<%-- <select id="subMontTypeSelect" name="subType" class="erp_cascade_select sel" >
								<option value="">-请选择-</option>
								<option value="11"<c:if test="${searchInfo.subType=='11'}">selected="selected"</c:if>>专项包</option>
								<option value="12"<c:if test="${searchInfo.subType=='12'}">selected="selected"</c:if>>省行统购资产</option>
							</select> --%>
							<select id="subMontTypeSelect" name="subType" class="erp_cascade_select sel" >
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')" selectedValue="${searchInfo.subType}"/>
							</select>
					</c:if>
					<c:if test="${searchInfo.orgType eq '02' }">
						<%-- <select id="subMontTypeSelect" name="subType" class="erp_cascade_select sel" >
							<option value="">-请选择-</option>
							<option value="21"<c:if test="${searchInfo.subType=='21'}">selected="selected"</c:if>>非省行统购资产</option>
							<option value="22"<c:if test="${searchInfo.subType=='22'}">selected="selected"</c:if>>非专项包费用</option>
						</select> --%>
						<select id="subMontTypeSelect" name="subType" class="erp_cascade_select sel" >
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
				<td class="tdRight" colspan="3"> 
					<input type="text" value="${searchInfo.dataYear}" id="dataYear" name="dataYear" class="base-input-text"/>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="tdWhite">
			<c:if test="${searchInfo.orgType eq '01' }">
				<p:button funcId="08110301" value="查找"/>
				</c:if>
				<c:if test="${searchInfo.orgType eq '02' }">
				<p:button funcId="08110302" value="查找"/>
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
					<input type="hidden" value="${item.memo}" id="${item.batchNo }_memo">
					<td>${item.dataYear}</td>
					<td>${item.instMemo}</td>
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
					<td>
						${item.statusName }
					</td>
					<td>${item.instUser}</td>	
					<td>
						<c:if test="${item.dataYear eq thisYear }">
							<c:if test="${item.status=='E4' || item.status=='C2'|| item.status=='C6'}">
								<input type="button"value="复核" onclick="toAudit('${item.batchNo}','${item.proType}','${item.instType}','${item.subType}','${item.org21Code}','${item.dataYear}','${item.status}','${item.cntStatus }','${item.excelStatus}','1')"/>
							</c:if>
							<c:if test="${item.status=='C3'}">
								<input type="button"   value="复核明细" onclick="auditDetail('${item.batchNo}')" />
							</c:if>
						</c:if>
						<c:if test="${item.dataYear ne thisYear }">
							<c:if test="${item.status=='E4' || item.status=='C2'|| item.status=='C6'}">
								<input type="button"value="复核" onclick="toAudit('${item.batchNo}','${item.proType}','${item.instType}','${item.subType}','${item.org21Code}','${item.dataYear}','${item.status}','${item.cntStatus }','${item.excelStatus}','2')"/>
							</c:if>
							<c:if test="${item.status=='C3'}">
								<input type="button"   value="复核明细" onclick="auditDetail('${item.batchNo}')" />
							</c:if>
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

<div id="AuditDiv">
	<form action="" method="post" id="auditForm">
	<input type="hidden" id="auditOrgType" name="orgType"/>
	<input type="hidden" name="batchNo" id="auditBatchNo"/>
		<input type="hidden" name="proType" id="auditProType">  
  		<input type="hidden" name="instType" id="auditInstType">  
  		<input type="hidden" name="subType" id="auditSubType">  
  		<input type="hidden" name="org21Code" id="auditOrg21Code"> 
  		<input type="hidden" name="dataYear" id="auditDataYear">  
  		<input type="hidden" name="status" id="auditStatus">  
  		<input type="hidden" name="auditFlag" id="auditFlag1"/>
  		<input type="hidden" id="flag1"/>
		<table width="98%">
			<tr>
				<td align="left" colspan="2">
					<div class="base-input-radio" id="audit1">
							<label for="status5" onclick="App.radioCheck(this,'audit1');" class="check-label">通过</label><input type="radio" id="status5" name="auditFlag3" value="01" checked="checked"/>
							<label for="status6" onclick="App.radioCheck(this,'audit1');" >退回</label><input type="radio" id="status6" name="auditFlag3" value="02"/>
					</div>
					<div class="base-input-radio" id="audit2">
							<label for="status7" onclick="App.radioCheck(this,'audit2');" class="check-label">退回</label><input type="radio" id="status7" name="auditFlag2" value="02"  checked="checked"/>
							(下一年的数据只能退回。)
					</div>
				</td>
			</tr>
			<tr id="auditFalseMemo"style="display: none;" >
				<td align="left">
					<br>审核失败原因：
					<textarea id="memoDiv" disabled="disabled" class="base-textArea"  rows="2" cols="45">
					</textarea>
				</td>
			</tr>
			<tr class="check">
				<td align="left">
					<br>信息备注(<span id="authdealCommentSpan">0/150</span>)：
					<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,150,'authdealCommentSpan')" id="memo" name="memo" rows="7" cols="45"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

<p:page/>
</body>
</html>