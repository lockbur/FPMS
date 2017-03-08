<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#proType").combobox();
	$(".sel").combobox();
	$("#status").combobox();
	$("#subMontTypeSelect").combobox();
	$("#subAprvTypeSelect").combobox();
}
function doValidate() {
	if($("#subType").val() == ""){
		App.notyError("请选择子类型!");
		return false;
	}
	var type = $("input[type=radio]:checked").val();
	if (type == "" || type == null){
		App.notyError("请选择导入类型!");
		return false;
	}
	if(type !="03"){
		
		//获取导入Excel文件名
		getSourceFileName($("input[name='fileRealName']"),$("#ExcelSourceFileName"));
		$("#path").val($("input[name='filePath']").val());
		//验证表单
		if(!App.valid("#addBudgetForm")){return;}
		//验证上传文件格式
		if(!uploadFileTypeCheck($("input[name='fileRealName']").val())){return;}

	}else{
		//是copy数据 就检查 下一年是否有了审核通过的数据，如果有了 就不让他添加了。
		if(!ajaxCopyExists("03")){
			return;
		}
		
	}
	if(type =="01"){
		//检查下一年数据是否存在  存在就不让重新导入本年
		if(!ajaxCopyExists("01")){
			return;
		}
	}
	if(!ajaxDataExist()){
		return;
	}
	return;
	App.submit($("#exportFormSearch"));
}
//检查copy的对应数据是否已经维护过了
function ajaxCopyExists(instType){
	var validateFlag = false ; 
	var data={};
	data["instType"] = $("#instType").val() ;
	data["orgType"] = $("#orgType").val() ;
	data["proType"]=$("#proType").val();
	data["subType"]=$("#subType").val();
	data["dataYear"] = $("#dataYear").val() ;
	var func_id="";
	if($("#orgType").val() == "01"){
		func_id = "0811020112";
	}else{
		func_id = "0811020212";
	}
	App.ajaxSubmit("sysmanagement/montAprvBatch/import/ajaxCopyExists.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
			{data:data,async:false}, 
			function(data) {
				var result=data.map;
				if(result.flag == false){
					App.notyError(result.msg); 
					validateFlag= false;
				}else{
					validateFlag= true;
				}
			}
		);
	return validateFlag;
}
//检查是否存在
function ajaxDataExist(){
	
	var validateFlag = false ; 
	var data = {};
	data["instType"] = $("#instType").val();
	data["orgType"] = $("#orgType").val() ;
	data["dataYear"] = $("#dataYear").val() ;
	data["proType"]=$("#proType").val();
	data["subType"]=$("#subType").val();
	if($("#dataYear").val() ==''){
		App.notyError("导出年份不能为空 ！");
	}
	var func_id="";
	if($("#orgType").val() == "01"){
		func_id = "0811020101";
	}else{
		func_id = "0811020201";
	}
	App.ajaxSubmit("sysmanagement/montAprvBatch/import/ajaxDataExist.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
			{data:data,async:false}, 
			function(data) {
				var result=data.resultValue;
				var flag = result.flag;
				var msg = result.msg;
				if(flag == "Y"){
					App.submit($("#exportFormSearch"));
				}
				if(flag == "N"){
					App.notyError(msg);		
				 
				}
			}
		);
	return validateFlag;
}
 
//获取上传Excel文件名(拿值地方,写值地方)
function getSourceFileName( _getObj , _writeObj){
	//拿到上传文件名全路径，截取后，并写入指定的地方
	var sourceName = $(_getObj).val();		
	if( null != sourceName && '' != sourceName ){
		sourceName = sourceName.substr(sourceName.lastIndexOf("\\")+1);
// 		alert('源文件名:'+sourceName);
		$(_writeObj).val( sourceName );
	}
}
function proTypeChange(obj){
	if($("#orgType").val()=='02'){
		if($(obj).val()=='01'){
			$("#sub1").show();
			$("#sub2").hide();
		}else{
			$("#sub1").hide();
			$("#sub2").show();
		}
	}
	
}
//验证文件格式 (参数：字符串)
function uploadFileTypeCheck( F_obj ){
	//验证是否以上传模板Excel
	if(F_obj == '' || typeof F_obj == 'undefined'){
		App.notyError("未上传模板文件");
		return false;
	}
	 //验证文件格式是否为Excel格式
	 var regExcelType = new RegExp(/\.(xls|XLS|XLs|Xls|xlsx|XLSX|XLsx|Xlsx)$/);
	 if(!F_obj.match(regExcelType)){
		 App.notyError("请上传正确的格式文件(.xlsx)"); 
	     return false;
	 }
	 return true;
}
function uploadFileFun( obj ){
// 	if($("#attachmentsView a").size()>0){			//该功能校验上传下载工具类已实现，具体做法为：在config中配置uploadMode:"single"即可
// 		App.notyError("只允许上传一个Excel模板");
// 		return;
// 	}
	// 存放附件列表对象
	var fileListObj = "#attachmentsView";
	// 配置
	var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
	// 上传
	UploadUtils.uploadAttachment(fileListObj,config);
};
//检查是否存在
function add(type,memo){
	$("#instType").val(type);
	$("#instMemo").val(memo);
	if("01" == type || "02" == type){
		$("#impTr").show();
	}else{
		$("#impTr").hide();
	}
	
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>详情</title>
</head>
<body>
<p:authFunc funcArray="0811020102,0811020202"/>
<form action="" method="post" id="exportFormSearch">
<input type="hidden" value="${bean.orgType }"  name="orgType" id="orgType"/>
	<p:token/>
		<table>	
			<tr>
				<th colspan="2">导入</th>
			</tr>
			<tr id="subMontType"  >
				<td class="tdLeft" width="25%">年份<span style="color: #A41E1E">*</span></td>
				<td class="tdRight" width="25%" >
				${dataYear }
				<input type="hidden" id="dataYear" name="dataYear" value="${dataYear }"/>
				</td>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">数据类型<span style="color: #A41E1E">*</span></td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
					<select id="proType" name="proType" class="erp_cascade_select" onchange="proTypeChange(this)">
						<option value="01">监控指标</option>
						<option value="02">审批链</option>
<%-- 						<c:forEach items="${selectList}" var="item"> --%>
<%-- 							<option value="${item.value }">${item.desc }</option> --%>
<%-- 						</c:forEach> --%>
					</select>
				</div>	
				</td>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">子类型<span style="color: #A41E1E">*</span></td>
				<td class="tdRight" width="25%">
					<c:if test="${bean.orgType=='01' }">
						<div class="ui-widget">
							<select id="subType" name="subType"  class="erp_cascade_select sel"  >
								<option value="">-- 请选择 --</option>
								<option value="11">专项包</option>
								<option value="12">省行统购资产</option>
							</select>
						</div>
					</c:if>
					<c:if test="${bean.orgType=='02' }">
						<div class="ui-widget" >
							<select id="subType" name="subType" class="erp_cascade_select sel">
								<option value="">-- 请选择 --</option>
								<option value="21">非省行统购资产</option>
								<option value="22">非专项包费用</option>
							</select>
						</div>
					</c:if>
				</td>
			</tr>
			<tr >
				<td class="tdLeft" > 导入类型<span style="color: #A41E1E">*</span>
				</td>
				<td  style="text-align: left" class="tdRight">
					<label><input type="radio" name="type" onclick="add('01','${dataYear }年重新导入')" value="01"/> ${dataYear }年重新导入</label>
					<label><input type="radio" name="type" onclick="add('02','${dataYear+1 }年数据导入')" value="02"/> ${dataYear+1 }年数据导入</label>
					<label><input type="radio" name="type" onclick="add('03','拷贝${dataYear }年的数据到${dataYear+1 }年')" value="03"/> 拷贝${dataYear }年的数据到${dataYear+1 }年</label>
					<input type="hidden" name="instType" id="instType"/>
					<input type="hidden" name="instMemo" id="instMemo"/>
				</td>
			</tr>
			<tr id="impTr">
				<td class="tdLeft" >
					<span class="red"></span>导入<span style="color: #A41E1E">*</span>
				</td>
				<td  style="text-align: left" class="tdRight">
					<input id="ExcelSourceFileName" type="hidden" name="sourceFilename"/>
					<input id="path" type=hidden name="path"/>
					<input type="button" id="uploadFile" value="导入Excel"  onClick="uploadFileFun(this)"/> 
					<div id="attachmentsView">
						<ul style='padding:0;margin:0;'>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
					<td colspan="2" class="tdWhite">
					<c:if test="${bean.orgType == '01'}">
						<p:button funcId="0811020102" />
					</c:if>
					<c:if test="${bean.orgType == '02'}">
						<p:button funcId="0811020202" />
					</c:if>	
						<input type="button" value="返回" onclick="backToLastPage('${uri}')">
					</td>
				</tr>
		</table>
</form>
</body>
</html>