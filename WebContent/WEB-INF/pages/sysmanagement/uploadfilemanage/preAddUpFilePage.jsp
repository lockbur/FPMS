<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 	prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 	prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增上传文件操作页面</title>

<style type="text/css">
	/* 文本区域框样式设置为不可缩放大小 */
	textarea{
		resize : none;
	}
</style>

<script type="text/javascript">

	//页面初始化加载调用
	function pageInit(){
		App.jqueryAutocomplete();
	 	$("#fileType").combobox();
	 	$("#deleteFlag").combobox();
	}
	
	//Ajax上传文件操作
	function uploadFileFun( obj ){
		if($("#attachmentsView a").size()>0){
			App.notyError("只允许上传一个文件");
			return;
		}
		// 存放附件列表的元素对象		(PS:#attachmentsView是上传控件在页面展示的div)
		var fileListObj = "#attachmentsView";
		// 上传控件配置
		var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:'',contextPath:'<%=request.getContextPath()%>'};
		// 执行上传附件操作
		UploadUtils.uploadAttachment(fileListObj,config);
	};
	
	//获取并写入上传文件的文件名以及文件路径,并作非空校验
	function updateFileNameAndPath(){
		//获取上传文件的原文件名+上传文件保存到服务器中的路径
		$("#sourceFName").val($("input[name='fileRealName']").val());
		$("#sourceFPath").val($("input[name='filePath']").val());
		//判断用户是否已经进行文件的上传
		if( "" == $("#sourceFName").val()){
			App.notyError("未上传文件,请先上传文件后再提交！");
			return false;
		}
		return true;
	}
	
	//提交上传请求前进行的上传文件信息校验
	function doValidate() {
		//1.必填校验
		if(!App.valid("#AddUpFileForm")){
			App.notyError("请检查必填信息是否已填完整再提交！");
			return;
		}
		//2.校验是否已上传文件，并将上传控件返回的文件名和临时保存路径取得，手动赋值作为参数传输到后台
		if(!updateFileNameAndPath()){return;};
		//checkFileSize();		//3.校验上传文件的大小	 Ps:控件中已作上传大小限制校验(在配置文件中配置file.size即可)
		if(!$("#fileType").val()){
			App.notyError("请选择文件上传的类型！");
			return;
		}
		return true;
	}

</script>
</head>
<body>
<p:authFunc funcArray="080902,08090201"/>
<form action="" method="post" id="AddUpFileForm">
	<p:token/>
	<table class="tableList">
		<tr>
			<th colspan="5">添加上传文件操作</th>
		</tr>
		
		<tr>
			<td class="tdLeft"><span class="red">*</span>上传文件类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="fileType" name="fileType" valid>
						<option value="">--请选择--</option>		
						<option value="01">控件 </option>		
						<option value="02">系统参数</option>		
						<option value="03">其他</option>
					</select>
				</div>
			</td>
			<td class="tdLeft"><span class="red">*</span>上传文件名称</td>
			<td class="tdRight" style="height: 36px; width: 15px ;border-right-style: none" nowrap="nowrap" >
<!-- 			style="border-right-style: none" -->
				<input type="hidden" id="sourceFName" name="sourceFName" class="base-input-text">
				<input type="hidden" id="sourceFPath" name="sourceFPath" class="base-input-text">
				
				<input type="button" id="uploadFile" value="上传文件"  align="right" onClick="uploadFileFun(this)"/>
			</td>
			<td style="border-left-style: none">
				<div id="attachmentsView">
					<ul style='padding:0;margin:0;'>
					</ul>
				</div>
			</td>
<!-- 			<td style="border-left-style: none;"> -->
<!-- 				<input type="button" id="uploadFile" value="上传文件"  onClick="uploadFileFun(this)"/> -->
<!-- 			</td> -->

		</tr>
		<tr style="display: none">
			<td class="tdLeft"><span class="red">*</span>是否允许删除</td>
			<td class="tdRight" colspan="4">
				<div class="ui-widget">
					<select id="deleteFlag" name="deleteFlag" valid>
 					 	<option value="T">是</option>
 					 	<option value="F">否</option>
					</select>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">
				<span class="red">*</span>文件描述(<span id="fileDescLogSpan">0/100</span>)
				<!-- <br> -->
			</td>
			<td class="tdRight" colspan="4">
				<textarea valid id="fileDesc" name="fileDesc" rows="10" cols="3"  class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,100,'fileDescLogSpan')"></textarea>
			</td>
		</tr>
		
		<tr>
			<td colspan="5" class="tdWhite" style="text-align: center">
				<p:button funcId="08090201" title="新增"/>
				<!-- <input type="button" value="新增" onclick="add()">	 -->
				<input type="button" value="返回" onclick="backToLastPage('listUpFileManage.do?VISIT_FUNC_ID=080902');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>