<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Load Excel</title>
<script type="text/javascript">
function pageInit() {
	App.jqueryAutocomplete();
}

function doValidate() {
	//获取导入Excel文件名
	getSourceFileName($("input[name='fileRealName']"),$("#ExcelSourceFileName"));
	$("#path").val($("input[name='filePath']").val());
	if(!uploadFileTypeCheck($("input[name='fileRealName']").val())){return;}
	//提交前调用
	if(!App.valid("#processForm")){return;}	
	return true;
}

function dowloadTemplate(){
	var url = '<%=request.getContextPath()%>/sysmanagement/matrtype/tempDownload.do?<%=WebConsts.FUNC_ID_KEY%>=01080102';
	encodeURI(url);
	window.open(url);
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
function uploadFileFun( obj ){
	var fileListObj = "#attachmentsView";
	// 配置
	var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
	// 上传
	UploadUtils.uploadAttachment(fileListObj,config);
};

//验证文件格式 (参数：字符串)
function uploadFileTypeCheck( F_obj ){
	//验证是否以上传模板Excel
	if(F_obj == '' || typeof F_obj == 'undefined'){
		App.notyError("未上传文件");
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
</script>
</head>

<body>
<p:authFunc funcArray="01080101"/>
<form action="" method="post" id="processForm" >
	<p:token/>
	<table>
		<tr>
			<th colspan="4">Excel导入
			(<a style="color: red;" href="#" onclick="javascript:dowloadTemplate();">导入模板下载</a>)
			</th>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>文件选择</td>
<!-- 			<td class="tdRight" colspan="3" id="impFileTd"> -->
<!-- 				<input type="file" id="impFile" name="impFile" style="width: 300px;" class="base-input-text"/>&nbsp;&nbsp;(必须为xls或xlsx格式文件) -->
<!-- 			</td> -->
				<td class="tdRight">
					<input id="ExcelSourceFileName" type="hidden" name="sourceFilename"/>
					<input id="path" type="hidden" name="path"/>
					<input type="button" id="uploadFile" value="导入Excel"  onClick="uploadFileFun(this)"/> 
					<div id="attachmentsView">
						<ul style='padding:0;margin:0;'>
						</ul>
					</div>
				</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="01080101" value="提交"/>
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>