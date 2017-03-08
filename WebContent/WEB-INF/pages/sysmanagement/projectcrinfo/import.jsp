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
<title>电子审批信息导入</title>
<script type="text/javascript">
function doValidate() 
{
	//提交前调用
	if(!App.valid("#projectcrForm")){return;}
	var filePath = $("#projectcrFile").val();
	if(!checkFile(filePath))
	{
		return false;
	}
	return true;
}
//页面初始化
function pageInit() {
	App.jqueryAutocomplete();
}

function checkFile(filePath)
{
	var p = filePath.lastIndexOf(".");
	var fileName = filePath.substring(p, filePath.length).toLowerCase();
	if(fileName != ".txt")
	{
		alert("导入文件必须是txt文件！");
		return false;
	}
	return true;
}

function dowloadTemplate(){
	var form = $("#projectcrForm")[0];
	var url = '<%=request.getContextPath()%>/sysmanagement/projectcrinfo/txtDownload.do?<%=WebConsts.FUNC_ID_KEY%>=011001';
	form.action = url;
	form.submit();
}
</script>
</head>

<body>
<p:authFunc funcArray="011002,011003"/>
<form method="post" action="" enctype="multipart/form-data" id="projectcrForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="2">
				电子审批信息导入
				(<a style="color: red;" href="#" onclick="javascript:dowloadTemplate();">导入样式下载</a>)
			</th>
		</tr>
		<tr>
			<td class="tdLeft" ><span class="red">*</span>导入文件</td>
			<td class="tdRight">
				<input type="file" name="projectcrFile" id="projectcrFile" class="base-input-text" valid errorMsg="请选择要导入的TXT文件"/>
				(<span style='color:red;'>提示：先下载导入样式，导入文本数据格式要和导入样式一致</span>)
			</td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="011002" value="提交"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>