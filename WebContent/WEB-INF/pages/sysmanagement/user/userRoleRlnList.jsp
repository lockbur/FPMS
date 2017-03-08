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
<title>用户职责信息导入</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#proType").combobox();
	$("#status").combobox();
	$("#subType").combobox();
}


function Del(batchNo){
	var url="<%=request.getContextPath()%>/sysmanagement/user/deleteUserRoleRln.do?<%=WebConsts.FUNC_ID_KEY%>=0103030203&batchNo="+batchNo;
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
	var url="<%=request.getContextPath()%>/sysmanagement/user/submitUserRoleRln.do?<%=WebConsts.FUNC_ID_KEY%>=0103030204&batchNo="+batchNo;
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


function importData(){
	var validateFlag = false ; 
	var func_id = "0103030208";
	var data={};
	data["batchNo"] = "1";
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/user/ajaxDataExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
		{data:data,async:false}, 
		function(data) {
			var result=data.resultValue;
			var flag = result.flag;
			var msg = result.msg;
			if(flag == "N"){
				App.notyError(msg); 
				validateFlag= false;
			}
			if(flag == "Y"){
				validateFlag= true;
			}
		}
	);
	if(validateFlag){
		var form=$("#exportFormSearch")[0];
		form.action="<%=request.getContextPath()%>/sysmanagement/user/preAddUserRoleRln.do?<%=WebConsts.FUNC_ID_KEY%>=0103030201";
		App.submit(form);
	}
}
function refresh(){
	var form=$("#exportFormSearch")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/user/userRoleRlnList.do?<%=WebConsts.FUNC_ID_KEY%>=01030302";
	App.submit(form);
}
function Detail(batchNo){
	var url="<%=request.getContextPath()%>/sysmanagement/user/getUserRoleRln.do?<%=WebConsts.FUNC_ID_KEY%>=0103030205&batchNo="+batchNo;
	$("#exportFormSearch").attr("action",url);
	$("#exportFormSearch").submit();
}
function fileDownload(batchNo){
	var url="";
	var func_id = "0103030206";
	var data = {};
	data["batchNo"] = batchNo;
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/user/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
		{data:data,async:false}, 
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#skipForm")[0];
				form.action = "<%=request.getContextPath()%>/sysmanagement/user/exportDownFile.do?<%=WebConsts.FUNC_ID_KEY%>=0103030207&batchNo="+data.batchNo;
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
<p:authFunc funcArray="01030302"/>
<form action="" method="post" id="skipForm">
</form>
<form action="" method="post" id="exportFormSearch">
<input type="hidden" name="orgType"  value="${orgType }"/>
	<p:token/>
		<table>	
			<tr class="collspan-control">
				
				<th colspan="4">
					导入
				</th>
			</tr>
			<tr>
				<td colspan="4" class="tdWhite">
					<input type="button" value="新增导入" onclick="importData()">
					<input type="button" value="刷新" onclick="refresh()">
				</td>
			</tr>
		</table>
		<br>
		<table class="tableList">
			<tr>
				<th width="15%">导入时间</th>
				<th width="15%">源文件名</th>
				<th width="10%">状态</th>
				<th width="10%">操作人</th>
				<th width="10%">操作人所在一级行</th>
				<th width="20%">备注</th>
				<th width="20%">操作</th>
			</tr>
			<c:forEach items="${list}" var="item">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td>${item.instDate}  ${item.instTime}</td>
					<td><a style="cursor:pointer;" class="text_decoration" onclick="fileDownload('${item.batchNo}');">${item.sourceFilename}</a></td>
					<td>${item.statusName }</td>	
					<td>${item.instUser}</td>
					<td>${item.org21Code}</td>
					<td>${item.memo}</td>
					<td>
						<c:if test="${item.excelStatus eq 'E1' || item.excelStatus eq 'E3' || item.excelStatus eq 'E4' }">
							<input type="button" value="删除" onclick="Del('${item.batchNo}')">
						</c:if>
						<c:if test="${item.excelStatus != 'E1' && item.excelStatus != 'E3' && item.excelStatus !='E4'}">
							<input type="button" value="删除" disabled="disabled">
						</c:if>
						<c:if test="${item.excelStatus eq 'E4'}">
							<input type="button" value="提交" onclick="Submit('${item.batchNo}')">
						</c:if>
						<c:if test="${item.excelStatus!='E4'}">
							<input type="button" value="提交" disabled="disabled">
						</c:if>						
						<input type="button" value="明细" onclick="Detail('${item.batchNo}')">	
					</td>			
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
			<tr>
				<td colspan="7" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		</table>
</form>
<p:page/>
</body>
</html>