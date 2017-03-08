<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>省行数据导入</title>
<script type="text/javascript">
function pageInit(){
	//getCurrentYear($("#dataYear"),2);
	App.jqueryAutocomplete();
	$("#proType").combobox();
	$("#status").combobox();
	$("#subType").combobox();
	//$("#dataYear").combobox();
}

/* function getCurrentYear( _obj , yearCount ){
	var currentYear = new Date().getFullYear();
	for(var i=0 ; i<yearCount ; i++){
		$(_obj).append("<option value='"+(currentYear-i+1)+"'>"+(currentYear-i+1)+" 年</option>");
	}
	var selectYear="${searchInfo.dataYear}";
	$("#dataYear").val(selectYear);
} */

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
function search(){
	var year = $.trim($('#dataYear').val());
	if(year!=null && year!='' && !/^\d{4}$/.test(year)){
		App.notyError('请输入正确年份');
		return;
	}
	var form=document.forms[1];
	if("${orgType}" == '01'){
		form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/shList.do?<%=WebConsts.FUNC_ID_KEY%>=08110201";
	}else{
		form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/fhList.do?<%=WebConsts.FUNC_ID_KEY%>=08110202";	
	}
	App.submit(form);
}
function Del(batchNo,proType){
	var url="";
	if("${orgType}" == '01'){
		url="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/delete.do?<%=WebConsts.FUNC_ID_KEY%>=0811020103&batchNo="+batchNo+"&proType="+proType;
	}else{
		url="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/delete.do?<%=WebConsts.FUNC_ID_KEY%>=0811020203&batchNo="+batchNo+"&proType="+proType;
	}
	$( "<div>确认要删除该条数据?</div>" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#exportFormSearch").attr("action",url);
				$("#exportFormSearch").submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
function Submit(batchNo){
	var url="";
	if("${orgType}" == '01'){
		
		url="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/submit.do?<%=WebConsts.FUNC_ID_KEY%>=0811020104&batchNo="+batchNo;
	}else{
		url="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/submit.do?<%=WebConsts.FUNC_ID_KEY%>=0811020204&batchNo="+batchNo;
	}
	$( "<div>确认要提交该条数据?</div>" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#exportFormSearch").attr("action",url);
				$("#exportFormSearch").submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}


// function Export(batchNo,proType,subType,dataYear){
// 	if (proType=='01') {
// 		var tableName="tbl_mont_name";
// 	} else if (proType=='02') {
// 		var tableName="tbl_aprv_chain";
// 	}
// 	var data = {};
// 	data['batchNo'] =  batchNo;
// 	data['proType'] =  proType;
// 	data['subType'] =  subType;
// 	data['dataYear'] = dataYear;
// 	var url="";
// 	var parent_func_id=""
// 	if("${orgType}" == '01'){
<%-- 		url = "sysmanagement/montAprvBatch/import/download.do?<%=WebConsts.FUNC_ID_KEY %>=0811020105"; --%>
// 		parent_func_id = "08110201";
// 	}else{
<%-- 		url = "sysmanagement/montAprvBatch/import/download.do?<%=WebConsts.FUNC_ID_KEY %>=0811020205"; --%>
// 		parent_func_id = "08110202";
// 	}
// 	App.ajaxSubmit(url,
// 			{data:data,async : false},
// 			function(data){
// 				flag = data.pass;
// 				if(flag){
// 					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
// 						resizable: false,
// 						width: 290,
// 						height:'auto',
// 						modal: true,
// 						dialogClass: 'dClass',
// 						title: "跳转至下载页面",
// 						buttons:[
// 						         {
// 						        	 text:"确认",
// 										click:function(){
// 							        	 	var form = document.forms[0];
// 							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
// 							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
// 							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
// 							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
// 							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
// 							        		$("form #upStepParams").val(upStepParams);
<%-- 											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId='+parent_func_id; --%>
// 											App.submit(form);
// 										}   
// 						         },
// 								{
// 									text:"取消",
// 									click:function(){
// 										$(this).dialog("close");
// 									}
// 								}
// 						]
// 					});
// 					isPass =  true;
// 				}
// 				else
// 				{
// 					App.notyError("添加下载失败，可能是因为"+tableName+"表里没有该条数据，请检查后重试!");
// 					isPass =  false;
// 				}
// 			});
// 	return isPass;
// }
function importData(){
	var form=$("#skipForm")[0];
	if("${orgType}" == '01'){
		form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/preAdd.do?<%=WebConsts.FUNC_ID_KEY%>=0811020106";
	}else{
		form.action="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/preAdd.do?<%=WebConsts.FUNC_ID_KEY%>=0811020206";
	}
	App.submit(form);
}
function Detail(batchNo){
	var url="";
	if("${orgType}" == '01'){
       url="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/getDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0811020107&batchNo="+batchNo;
	}else{
		url="<%=request.getContextPath()%>/sysmanagement/montAprvBatch/import/getDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0811020207&batchNo="+batchNo;
	}
	$("#exportFormSearch").attr("action",url);
	$("#exportFormSearch").submit();
}
function fileDownload(batchNo){
	var url="";
	var func_id = "";
	if("${orgType}" == '01'){
		func_id="0811030102";
	}else{
		func_id="0811030202";
	}
	var data = {};
	data["batchNo"] = batchNo;
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/montAprvBatch/apprv/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
		{data:data,async:false}, 
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#skipForm")[0];
				if("${orgType}" == '01'){
					
					form.action = "<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/Export.do?<%=WebConsts.FUNC_ID_KEY%>=0811020108&batchNo="+data.batchNo;
				}else{
					form.action = "<%=request.getContextPath()%>/sysmanagement/montAprvBatch/apprv/Export.do?<%=WebConsts.FUNC_ID_KEY%>=0811020208&batchNo="+data.batchNo;
				}
				form.submit();
			}else{
				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
				App.notyError(checkMsg);
			}
		}
	);
}
</script>
</head>
<body>
<p:authFunc funcArray="08110101"/>
<form action="" method="post" id="skipForm">
<input type="hidden" name="orgType" id="orgType" value="${orgType }"/>
</form>
<form action="" method="post" id="exportFormSearch">
<input type="hidden" name="orgType"  value="${orgType }"/>
	<p:token/>
		<table>	
			<tr class="collspan-control">
				
				<th colspan="4">
					查询
				</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">数据类型</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
					<select id="proType" name="proType" class="erp_cascade_select">
						<option value="">-请选择-</option>
						<option value="01" <c:if test="${searchInfo.proType=='01'}">selected="selected"</c:if>>监控指标</option>
						<option value="02" <c:if test="${searchInfo.proType=='02'}">selected="selected"</c:if>>审批链</option>
					</select>
				</div>	
				</td>
				<td class="tdLeft" width="25%">子类型</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
						<c:if test="${orgType eq '01' }">
							<%-- <select id="subType" name="subType">
								<option value="">-请选择-</option>
								<option value="11"<c:if test="${searchInfo.subType=='11'}">selected="selected"</c:if>>专项包</option>
								<option value="12"<c:if test="${searchInfo.subType=='12'}">selected="selected"</c:if>>省行统购资产</option>
							</select> --%>
							<select id="subType" name="subType"   class="erp_cascade_select" >
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')" selectedValue="${searchInfo.subType}"/>
							</select>
						</c:if>
						<c:if test="${orgType eq '02' }">
							<%-- <select id="subType" name="subType">
								<option value="">-请选择-</option>
								<option value="21"<c:if test="${searchInfo.subType=='21'}">selected="selected"</c:if>>非省行统购资产</option>
								<option value="22"<c:if test="${searchInfo.subType=='22'}">selected="selected"</c:if>>非专项包费用</option>
							</select> --%>
							<select id="subType" name="subType"   class="erp_cascade_select" >
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='21' or PARAM_VALUE='22')" selectedValue="${searchInfo.subType}"/>
							</select>
						</c:if>
						
					</div>
				</td>
			</tr>
			<tr>
				
				<td class="tdLeft" width="25%">年份</td>
				<td class="tdRight" width="25%" colspan="3">
					<input type="text" value="${searchInfo.dataYear}" id="dataYear" name="dataYear" class="base-input-text"/>
				</td>					
			</tr>				
			<tr>
				<td colspan="4" class="tdWhite">
					<input type="button" value="查询" onclick="search()"/>
					<input type="button" value="重置" onclick="resetAll()"> 
					<input type="button" value="导入" onclick="importData()">
				</td>
			</tr>
		</table>
		<br>
		<table class="tableList">
			<tr>
				<th width="5%">年份</th>
				<th width="13%">导入类型</th>
				<th width="14%">导入时间</th>
				<th width="13%">源文件名</th>
				<th width="8%">数据类型</th>
				<th width="7%">子类型</th>
				<th width="6%">状态</th>
				<th width="6%">操作人</th>
				<th width="18%">操作</th>
			</tr>
			<c:forEach items="${list}" var="item">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td>${item.dataYear}</td>
					<td>${item.instMemo}</td>
					<td>${item.instDate}  ${item.instTime}</td>
					<td><a style="cursor:pointer;" onclick="fileDownload('${item.batchNo}');">${item.sourceFilename}</a></td>
					<td>
						<c:if test="${item.proType=='01'}">监控指标</c:if>
						<c:if test="${item.proType=='02'}">审批链</c:if>
					</td>
					<td>
						<%-- <c:if test="${item.subType=='11'}">专项包</c:if>
						<c:if test="${item.subType=='12'}">省行统购资产</c:if>
						<c:if test="${item.subType=='21'}">非省行统购资产</c:if>
						<c:if test="${item.subType=='22'}">非专项包费用</c:if> --%>
						${item.subTypeName}
					</td>
					<td>
						${item.statusName }
					</td>	
					<td>${item.instUser}</td>
					<td>
						<c:if test="${item.status eq 'E1' || item.status eq 'E2' || item.status eq 'E3' || item.status eq 'E6'}">
							<input type="button" value="删除" onclick="Del('${item.batchNo}','${item.proType}')">
						</c:if>
						<c:if test="${item.status != 'E1' && item.status != 'E2' && item.status != 'E3' && item.status !='E6'}">
							<input type="button" value="删除" disabled="disabled">
						</c:if>
						<c:if test="${item.status eq 'E2'}">
							<input type="button" value="提交" onclick="Submit('${item.batchNo}')">
						</c:if>
						<c:if test="${item.status!='E2'}">
							<input type="button" value="提交" disabled="disabled">
						</c:if>						
	
							<input type="button" value="明细" onclick="Detail('${item.batchNo}')">	
	
					</td>			
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		</table>
</form>
<p:page/>
</body>
</html>