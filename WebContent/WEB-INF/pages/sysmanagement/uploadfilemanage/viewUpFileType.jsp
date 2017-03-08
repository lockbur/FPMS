<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新上传文件信息页面</title>

<style type="text/css">
	/* 文件描述框样式设置为不可改变大小 */
	textarea{
		resize : none;
	}
</style>
	
<script type="text/javascript">
	//页面初始化执行加载
	function pageInit(){
		App.jqueryAutocomplete();
	}
	
	
	function doValidate() {
		return true;
	}
	
	//上传文件信息修改
	function update(fileType){
		//1.更新操作提交前，首先校验是否有值发生改变(发生变化才提交，没变不提交)
// 		console.info(0);
// 		console.info(validateResult);
// 		console.info(1);
		if($("#reFileTypeDesc").val()){
			validateResult = true;
// 			console.info(2);
		}
		
		//3.校验通过，允许执行修改操作
		if(validateResult){
			var form = $('#UpFileInfoForm')[0];
			form.action = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/updateUpFileType.do?<%=WebConsts.FUNC_ID_KEY%>=08090305&fileType='+fileType;
			App.submit(form);
		}
	}
	
	//校验值是否有发生改变(取得页面加载后的原始值与提交前的值进行比较)
	function validateValueHasChange(sourceValue , changedObjValue){
		if( sourceValue == changedObjValue ){
			return false;
		}else{
			return true;
		}
	}
	
	
	//替换文本框的值，将B文本框的值替换掉A文本框的值(用于重命名上传文件名)
	function replaceFileName(sourceObj , targetObj){
// 		$("#newFName").val($("#replaceFileName").val());
		$(sourceObj).val($(targetObj).val());
	}
	
	//执行修改操作前校验(校验修改前后文件信息是否有改动，没有改动弹出提示并不提交后台执行修改操作，有改动时才执行后台update操作)
	function updateInfoHasChangeCheck(){
		//检查是否有需要重命名文件
		if($("#repalceFileName")){
			return true;
		}
		
		//更改前信息
		var sourceFName = '${upFileInfo.sourceFName}';		//#newFName有值时将会将sourceFName值替换
		var sourceFType = '${upFileInfo.fileType}';
		var sourceFDesc = '${upFileInfo.fileDesc}';
		//将要更改为的信息
		var upToFName = $("#newFName").val();
		var upToFType = $("#fileType").val();
		var upToFDesc = $("#fileDesc").val();
		if(sourceFType == upToFType && sourceFDesc == upToFDesc){
			if($("#newFName").val()){
				//重新上传了文件，但没有更改文件描述和文件类型
				return true;
			}else{
				App.notyError("文件信息没有更改，请检查更改后再提交!");
				return false;
			}
		}else{
	// 		console.info('信息已更新，即将执行修改操作!');
			return true;
		}
	}
	
	
	//【测试方法】将双斜杠文件路径替换为单斜杠路径
// 	function testChangeXiegan(){
// 		var sourceFPath = '${upFileInfo.sourceFPath}';
// 		console.info('原文件路径：'+sourceFPath);
// 		sourceFPath = sourceFPath.replace(/\\/g,"/");
// 		console.info('新的路径：'+sourceFPath);
// 		return sourceFPath;
// 	}

</script>
</head>
<body>
<p:authFunc funcArray="080902,08090203,08090204"/>
<form id="downloadForm" method="post"></form>
<form action="" method="post" id="UpFileInfoForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="5">
				上传文件信息修改
<%-- 				<input type="hidden" value="${upFileInfo.fileId}"> --%>
<%-- 				<input type="hidden" id="sourceFName" name="sourceFName" value="${upFileInfo.sourceFName}"> --%>
<%-- 				<input type="hidden" id="sourceFPath" name="sourceFPath" value="${upFileInfo.sourceFPath}"> --%>
<%-- 				<input type="hidden" id="" name="" value="${upFileInfo.fileId}"> --%>
			</th>
		</tr>
		<tr>
			<td class="tdLeft" title="类型ID：${upFileType.fileType}">文件类型描述</td>
			<td class="tdRight">
				${upFileType.fileTypeDesc} 
				<input type="hidden" id="fileTypeDesc" name="fileTypeDesc" value="${upFileType.fileTypeDesc}" class="base-input-text" maxlength="100"> 
			</td>
			<td class="tdLeft">重命名文件类型描述</td>
			<td class="tdRight">
				<input type="text" id="reFileTypeDesc" name="reFileTypeDesc" class="base-input-text" maxlength="100"> 
			</td>
		</tr>
		
		<tr>
			<td colspan="5" style="text-align: center">
<!-- 				<input type="button" value="获取文件名" onclick="updateFileNameAndPath()">	 -->
<!-- 				<input type="button" value="修改前校验" onclick="updateInfoHasChangeCheck()">	 -->
<!-- 				<input type="button" value="获取textArea值" onclick="getTextAreaValue()">	 -->
<!-- 				<input type="button" value="testChange" onclick="testChangeXiegan()">	 -->
				<input type="button" value="修改" onclick="update('${upFileType.fileType}')">	
				<input type="button" value="返回" onclick="backToLastPage('listUpFileType.do?VISIT_FUNC_ID=080903');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>