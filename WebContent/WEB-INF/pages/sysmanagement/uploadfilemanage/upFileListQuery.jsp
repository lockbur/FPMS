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
<title>文件上传查询</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
 	$("#fileType").combobox();
}

function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}

/**
 * 文件下载操作
 *		描述：1.先进行Ajax检测要下载的文件是否存在  	2.当文件存在时执行文件下载操作；当文件不存在时，提示用户"下载文件"不存在
 */
function downloadFile(fileId){
// 	var data = {};
// 	data["fileId"] = fileId;
// 	//Ajax检查文件是否存在校验
<%-- 	App.ajaxSubmit("sysmanagement/uploadfilemanage/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>=08090207", --%>
// 		{data:data,async:false}, 
// 		function(data) {
// 			var checkFlag = data.checkFlag;
// 			var checkMsg  = data.checkMsg;
// 			if(checkFlag){
// 				var form = $("#downloadForm")[0];
<%-- 				var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/upFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=08090206'; --%>
// 				form.action = url + "&fileId="+data.fileId;
// 				form.submit();
// 			}else{
// 				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
// 				App.notyError(checkMsg);
// 			}
// 		}
// 	);
	
	//【集群文件处理】直接进行文件下载，当根据文件路径查找不到文件时，后台代码会从数据库中寻找该文件，并复制到该地址，接着才会进行文件下载操作
	var form = $("#downloadForm")[0];
	var url = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/upFileDownload.do?<%=WebConsts.FUNC_ID_KEY%>=08090206';
	form.action = url + "&fileId="+fileId;
	form.submit();
}

</script>
</head>

<body>
<p:authFunc funcArray="080901"/>
<form id="downloadForm" method="post"></form>
<form action="<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/listUpFile.do" method="post" id="upFileListForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				文件上传查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">文件名称</td>
			<td class="tdRight">
				<input type="text" id="sourceFName" name="sourceFName" value="${upFile.sourceFName}" class="base-input-text" title="提示：可输入部分文件名模糊查询" maxlength="100"/>
			</td>
			<td class="tdLeft">文件类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="fileType" name="fileType">
						<option value="">--请选择--</option>		
						<option value="01" <c:if test="${upFile.fileType == '01'}">selected="selected"</c:if>>控件</option>		
						<option value="02" <c:if test="${upFile.fileType == '02'}">selected="selected"</c:if>>系统参数</option>		
						<option value="03" <c:if test="${upFile.fileType == '03'}">selected="selected"</c:if>>其他</option>
					</select>
				</div>
<%-- 				<input type="text" id="fileType" name="fileType" value="${upFile.fileType}" class="base-input-text"/> --%>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">操作人员</td>
			<td class="tdRight" colspan="3">
				<input type="text" name="instOper" value="${upFile.instOper}" class="base-input-text" title="提示：精确查找操作人员(可忽略大小写)" maxlength="7"/>
			</td>
<!-- 			<td class="tdLeft">文件描述</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input type="text" id="fileDesc" name="fileDesc" value="${upFile.fileDesc}" class="base-input-text" title="提示：可输入部分描述模糊查询" maxlength="100"/> --%>
<!-- 			</td> -->
		</tr>
		
		<tr>
			<td colspan="4" class="tdBottom tdWhite">
				<p:button funcId="080901" value="查询"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
	<tr class="collspan-control">
		<th width="35%" style="text-align: center">文件名称</th>
		<th width="20%" style="text-align: center">文件类型</th>
		<th width="20%" style="text-align: center">操作人员</th>
		<th width="25%" style="text-align: center">操作时间</th>
<!-- 		<th>操作</th> -->
	</tr>
	<c:forEach items="${upFileList}" var="upFileInfo">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
			<td title="描述：${upFileInfo.fileDesc}" style="text-align: center">
<%-- 				<a style="text-decoration: underline;" title="下载文件：${upFileInfo.sourceFName}" onclick="javascript:UploadUtils.downloadFile('${upFileInfo.sourceFPath}','${upFileInfo.sourceFName}','/ERP')"> --%>
				<a href="#" style="text-decoration: underline;" title="下载文件：${upFileInfo.sourceFName}" onclick="downloadFile('${upFileInfo.fileId}')">
					${upFileInfo.sourceFName}
				</a>
				<input type="hidden" name="fileId" id="fileId" value="${upFileInfo.fileId}">
			</td>
			<td style="text-align: center">${upFileInfo.fileTypeDesc}</td>
			<td style="text-align: center">${upFileInfo.instOper}</td>
			<td style="text-align: center">${upFileInfo.instDate} ${upFileInfo.instTime}</td>
		</tr>
	</c:forEach>
	<c:if test="${empty upFileList}">
		<tr>
			<td colspan="6" style="text-align: center;"><span style="color: red">找不到相应的数据！请检查搜索条件再查询。</span></td>
		</tr>
	</c:if>
	
	</table>
</form>
<p:page/>
</body>
</html>