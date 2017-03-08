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
<title>新增上传文件类型操作页面</title>

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
	}
	
	function addFileType(){
		//1.新增操作提交前的校验
		if( !checkBlank( $("#fileTypeDesc").val() )){
			if($.isBlank($("#fileTypeDesc").val())){
				App.notyError("[文件类型描述]值为空，请检查后再提交！");
			}
			return ;
		}
		//2.校验文件类型缓存路径的值
		
		//3.提交新增请求，执行操作
		var form = $('#AddUpFileTypeForm')[0];
		form.action = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/addUpFileType.do?<%=WebConsts.FUNC_ID_KEY%>=08090302';
		App.submit(form);
	}
	
	//校验是否为空
	function checkBlank(obj1 , obj2){
		if(!$.isBlank(obj1) && !$.isBlank(obj2)){
			return true;
		}else{
			return false;
		}
	}
	
	
</script>
</head>
<body>
<p:authFunc funcArray="08090302"/>
<form action="" method="post" id="AddUpFileTypeForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="5">添加上传文件操作</th>
		</tr>
		
		<tr>
			<td class="tdLeft" width="20%">文件类型描述</td>
			<td class="tdRight" width="30%">
				<input type="text" id="fileTypeDesc" name="fileTypeDesc" class="base-input-text">
			</td> 
			
			<td class="tdLeft" width="20%">文件类型缓存路径</td>
			<td class="tdRight" width="30%" >
				<!--需处理不能输入":"的问题，并且需要添加键入"\\"时的相关提醒 -->
			</td>
		</tr>
		
		
		<tr>
			<td colspan="5" style="text-align: center" class="tdWhite">
<%-- 				<p:button funcId="08090302" title="新增"/> --%>
				<input type="button" value="新增" onclick="addFileType()">
				<!-- <input type="button" value="新增" onclick="add()">	 -->
				<input type="button" value="返回" onclick="backToLastPage('listUpFileType.do?VISIT_FUNC_ID=080903');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>