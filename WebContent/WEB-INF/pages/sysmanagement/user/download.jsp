<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>控件下载</title>
<script type="text/javascript">

/**
 * 文件下载操作
 *		描述：1.先进行Ajax检测要下载的文件是否存在  	2.当文件存在时执行文件下载操作；当文件不存在时，提示用户"下载文件"不存在
 */
function downloadFile(fileId){
	var data = {};
	data["fileId"] = fileId;
	//Ajax检查文件是否存在校验
	App.ajaxSubmit("sysmanagement/uploadfilemanage/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>=08090207",
		{data:data,async:false}, 
		function(data) {
			var checkFlag = data.checkFlag;
			var checkMsg  = data.checkMsg;
			if(checkFlag){
				var form = $("#downloadForm")[0];
				var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/upFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=08090206';
				form.action = url + "&fileId="+data.fileId;
				form.submit();
			}else{
				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
				App.notyError(checkMsg);
			}
		}
	);
}

function pageInit(){
	App.jqueryAutocomplete();
	$("#fileType").combobox();
}
</script>
</head>

<body>
<p:authFunc funcArray="00010102,000101,080901,000104,08090206"/>

<form id="downloadForm" method="post"></form>
<form id="submitForm" method="post" action=""></form>
<form  method="post" id="upFileListForm">

<%-- action="<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/listUpFile.do" --%>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				控件列表查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">控件名称</td>
			<td class="tdRight">
				<input type="text" id="sourceFname" name="sourceFName" value="${upFile.sourceFName}" class="base-input-text" title="提示：可输入部分文件名模糊查询" maxlength="100"/>
			</td>
			<td class="tdLeft">操作人员</td>
			<td class="tdRight">
				<input type="text" name="instOper" value="${upFile.instOper}" class="base-input-text" title="提示：精确查找操作人员(可忽略大小写)" maxlength="7"/>
			</td>
		<tr>
			<td colspan="4" class="tdBottom">
<!-- 				<input type="button" value="查找"  onclick="downloadQuery()"> -->
				<p:button funcId="000101" value="查找"/>
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList" >
	<tr class="collspan-control">
		<th width="30%" style="text-align: center">文件名称</th>
		<th width="15%" style="text-align: center">文件类型</th>
		<th width="15%" style="text-align: center">操作人员</th>
		<th width="20%" style="text-align: center">上传时间</th>
	</tr>
	<c:forEach items="${upFileList}" var="upFileList">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
			<td style="text-align: center" title="描述：${upFileList.fileDesc}">
				<a href="#" style="text-decoration: underline;" title="下载文件：${upFileList.sourceFName}" onclick="downloadFile('${upFileList.fileId}')">
					${upFileList.sourceFName}
				</a>
			</td>
			<td style="text-align: center">${upFileList.fileTypeDesc}</td>
			<td style="text-align: center">${upFileList.instOper}</td>
			<td style="text-align: center">${upFileList.instDate} ${upFileList.instTime}</td>
		</tr>
	</c:forEach>
	<c:if test="${empty upFileList}">
		<tr>
			<td colspan="4" style="text-align: center"><span style="color: red">找不到相应的数据！请检查搜索条件再查询。</span></td>
		</tr>
	</c:if>
	
	</table>
</form>
<p:page/>
</body>
</html>