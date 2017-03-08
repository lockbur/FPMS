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
}
function doValidate() {
		
	//获取导入Excel文件名
	getSourceFileName($("input[name='fileRealName']"),$("#ExcelSourceFileName"));
	$("#path").val($("input[name='filePath']").val());
	//验证表单
	if(!App.valid("#addBudgetForm")){return;}
	//验证上传文件格式
	if(!uploadFileTypeCheck($("input[name='fileRealName']").val())){return;}

	App.submit($("#importFormSearch"));
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
	// 存放附件列表对象
	var fileListObj = "#attachmentsView";
	// 配置
	var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
	// 上传
	UploadUtils.uploadAttachment(fileListObj,config);
};
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>详情</title>
</head>
<body>
<p:authFunc funcArray="0103030202"/>
<form action="" method="post" id="importFormSearch">
<input type="hidden" value="${bean.orgType }"  name="orgType" id="orgType"/>
	<p:token/>
		<table>	
			<tr>
				<th colspan="2">导入</th>
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
					<p:button funcId="0103030202" />
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">
				</td>
			</tr>
		</table>
</form>
</body>
</html>