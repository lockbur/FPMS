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
	 	$("#fileType").combobox();
	}
	
	$(function(){
		//页面加载时进行文件描述文字个数统计
		$_showWarnWhenOverLen1($("#fileDesc")[0],250,'fileDescLogSpan');
	})
	
	function doValidate() {
		if(!App.valid("#paraForm")){return;}
		return true;
	}
	
	//上传文件信息修改
	function update(fileId){
		//修改前，获取上传文件的信息(文件名+文件路径)
		updateFileNameAndPath();
		if(updateInfoHasChangeCheck()){
			//修改原文件名————>用户指定的文件名
			replaceFileName($("#newFName") , $("#replaceFileName"));
			
// 		console.info('新的文件名：'+$("#newFName").val()+"-----新的路径："+$("#newFPath").val());
			var form = $('#UpFileInfoForm')[0];
			form.action = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/updateUpFileInfo.do?<%=WebConsts.FUNC_ID_KEY%>=08090204&fileId='+fileId;
			App.submit(form);
		}
	}
	
	//替换文本框的值，将B文本框的值替换掉A文本框的值(用于重命名上传文件名)
	function replaceFileName(sourceObj , targetObj){
// 		$("#newFName").val($("#replaceFileName").val());
		//判断是否存在需要替换的新文件名
		if($(targetObj).val()){
			var targetName = $(targetObj).val();
			//判断是否需要取得文件名的后缀
			if(targetName.lastIndexOf(".") < 0 ){
				var sourceName = '${upFileInfo.sourceFName}';
				var sourceFileSuffix = sourceName.substring(sourceName.lastIndexOf("."),sourceName.length);
				targetName += sourceFileSuffix;
			}
			//替换新文件名
			$(sourceObj).val(targetName);
		}
	}
	
	function testTest(){
		var fileString0 = "合同数据";
		var fileString1 = "合同数据.xlsx";
		var fileSuffix = fileString1.substring(fileString1.lastIndexOf("."),fileString1.length);
// 		console.info("文件后缀："+fileSuffix);
		var index = fileString0.lastIndexOf(".");				//-1
// 		console.info(index);
		
	}
	
	//【上传下载组件】Ajax上传文件操作
	function uploadFileFun( obj ){
		if($("#attachmentsView a").size()>0){
			App.notyError("只允许上传一个文件");
			return;
		}
		// 存放附件列表对象
		var fileListObj = "#attachmentsView";
		// 配置
		var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:'',contextPath:'<%=request.getContextPath()%>'};
		// 上传附件
		UploadUtils.uploadAttachment(fileListObj,config);
	}
	
	//获取并写入上传文件的文件名以及文件路径
	function updateFileNameAndPath(){
		//获取上传文件的原文件名+上传文件保存到服务器中的路径
		if($("input[name='fileRealName']")[0]){ //非"undefined"(即使用上传组件上传文件后)
			//如果用户选择了重新上传文件(在没有删除新文件情况下，则会产生下述的input框)
			$("#newFName").val($("input[name='fileRealName']").val());
			$("#newFPath").val($("input[name='filePath']").val());
		}
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
	
	//文件下载
	function downloadFile(fileId){
		var form = $("#downloadForm")[0];
		var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/upFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=08090206';
		form.action = url + "&fileId="+fileId;
		form.submit();
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
			<th colspan="4">
				上传文件信息修改
				<input type="hidden" value="${upFileInfo.fileId}">
				<input type="hidden" id="sourceFName" name="sourceFName" value="${upFileInfo.sourceFName}">
				<input type="hidden" id="sourceFPath" name="sourceFPath" value="${upFileInfo.sourceFPath}">
				<input type="hidden" id="" name="" value="${upFileInfo.fileId}">
			</th>
		</tr>
		<tr>
			<td class="tdLeft">文件名</td>
			<td class="tdRight" colspan="3">
				<input type="hidden" id="newFName" name="newFName">
				<input type="hidden" id="newFPath" name="newFPath">
				<%-- <a id="showSourceFName" style="text-decoration: underline;" title="下载文件：${upFileInfo.sourceFName}" onclick="javascript:UploadUtils.downloadFile('${upFileInfo.sourceFPath}','${upFileInfo.sourceFName}','/ERP')">${upFileInfo.sourceFName}</a> --%>
				<a href="#" id="showSourceFName" style="text-decoration: underline;" title="下载文件：${upFileInfo.sourceFName}" onclick="downloadFile('${upFileInfo.fileId}')">${upFileInfo.sourceFName}</a>
				<br/>
				<div id="attachmentsView" style="float: left;">
					<ul style='padding:0;margin:0;'>
					</ul>
				</div>
				<%-- <c:if test="${'F' != upFileInfo.deleteFlag}"> --%>
					<div style="margin-left: 100px;float: right;">
					<input type="button" id="uploadFile" value="重新上传"  onClick="uploadFileFun(this)"/>
					</div>
				<%-- </c:if> --%>
			</td>
			<!-- <td style="text-align :right; border-left-style: none" colspan="2"> -->
			<!-- </td> -->
			<td class="tdLeft" style="display: none">重命名文件名</td>
			<td class="tdRight" title="【注意】：重命名需填写文件后缀名" style="display: none">
				<input type="text" id="replaceFileName" name="replaceFileName" class="base-input-text" maxlength="100">
			</td>
		</tr>
		<tr>
			<td class="tdLeft">文件ID</td>
			<td class="tdRight" >
				${upFileInfo.fileId} 
			</td>
			<td class="tdLeft">文件类型</td>
			<td class="tdRight" id="fileTypeTd">
				<c:if test="${'F' == upFileInfo.deleteFlag}">
					<c:if test="${upFileInfo.fileType == '01'}">控件 </c:if>
					<c:if test="${upFileInfo.fileType == '02'}">系统参数</c:if>
					<c:if test="${upFileInfo.fileType == '03'}">其他</c:if>
				</c:if>
				<c:if test="${'F' != upFileInfo.deleteFlag}">
					<div class="ui-widget">
						<select id="fileType" name="fileType">
							<option value="">--请选择--</option>		
							<option value="01" <c:if test="${upFileInfo.fileType == '01'}">selected="selected"</c:if>>控件</option>		
							<option value="02" <c:if test="${upFileInfo.fileType == '02'}">selected="selected"</c:if>>系统参数</option>		
							<option value="03" <c:if test="${upFileInfo.fileType == '03'}">selected="selected"</c:if>>其他</option>
						</select>
					</div>
				</c:if>
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td class="tdLeft">是否可删除</td> -->
<!-- 			<td class="tdRight" colspan="2"> -->
<%-- 				<c:if test="${'T' eq upFileInfo.deleteFlag}">是</c:if> --%>
<%-- 				<c:if test="${'F' eq upFileInfo.deleteFlag}">否</c:if> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr>
			<td class="tdLeft">操作人员</td>
			<td class="tdRight">
				${upFileInfo.instOper}
			</td>
			<td class="tdLeft">操作时间</td>
			<td class="tdRight">
				<c:if test="${!empty upFileInfo.instDate}">
					${upFileInfo.instDate}  ${upFileInfo.instTime}
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">
				文件描述
				<c:if test="${'F' != upFileInfo.deleteFlag}"><br>(<span id="fileDescLogSpan">0/250</span>)</c:if>
			</td>
			<td class="tdRight" colspan="3">
				<%-- <c:if test="${'F' != upFileInfo.deleteFlag}"> --%>
					<textarea valid id="fileDesc" name="fileDesc" rows="10" cols="3"  class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,250,'fileDescLogSpan')">${upFileInfo.fileDesc}</textarea>
				<%-- </c:if>
				<c:if test="${'F' == upFileInfo.deleteFlag}">
					<textarea id="fileDesc" name="fileDesc" rows="10" cols="3" disabled="disabled"  class="base-textArea">${upFileInfo.fileDesc}</textarea>
				</c:if> --%>
			</td>
		</tr>
		
		<tr>
			<td colspan="4" class="tdWhite" style="text-align: center">
<!-- 				<input type="button" value="测试" onclick="testTest()">	 -->
<!-- 				<input type="button" value="获取文件名" onclick="updateFileNameAndPath()">	 -->
<!-- 				<input type="button" value="修改前校验" onclick="updateInfoHasChangeCheck()">	 -->
<!-- 				<input type="button" value="获取textArea值" onclick="getTextAreaValue()">	 -->
<!-- 				<input type="button" value="testChange" onclick="testChangeXiegan()">	 -->
				<%-- <c:if test="${'F' != upFileInfo.deleteFlag}"> --%>
					<input type="button" value="修改" onclick="update('${upFileInfo.fileId}')">
				<%-- </c:if> --%>
				<input type="button" value="返回" onclick="backToLastPage('listUpFileManage.do?VISIT_FUNC_ID=080902');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>