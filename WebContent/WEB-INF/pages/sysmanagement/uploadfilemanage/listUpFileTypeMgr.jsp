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
<title>上传文件类型管理</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
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

function view(fileType){
	var form = $('#submitForm')[0];
	form.action = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/getUpFileType.do?<%=WebConsts.FUNC_ID_KEY%>=08090304&fileType='+fileType;
	App.submit(form);
}

//删除指定的上传文件
function deleteFile(fileType){
	var form = $('#submitForm')[0];
	form.action = '<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/delUpFileType.do?<%=WebConsts.FUNC_ID_KEY%>=08090303&fileType='+fileType;
	$( "<div>确认删除该文件类型?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submit(form,true);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

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


$(function(){
// 	console.info($(".trWhite")[0].outerHTML);
// 	alert($(".trWhite")[0].outerHTML);
// 	console.info($(".trWhite")[0].innerHtml);
// 	alert($(".trWhite")[0]);
})

</script>
</head>

<body>
<p:authFunc funcArray="080901,080902,08090200,08090201,08090202,08090203,080903, 08090301,08090304"/>
<form id="downloadForm" method="post"></form>
<form id="submitForm" method="post"></form>
<form action="<%=request.getContextPath()%>/sysmanagement/uploadfilemanage/listUpFile.do" method="post" id="upFileListForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				上传文件类型查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">文件类型编号</td>
			<td class="tdRight">
				<input type="text" id="fileType" name="fileType" value="${upFileType.fileType}" class="base-input-text" maxlength="2"/>
			</td>
			
			<td class="tdLeft">文件类型描述</td>
			<td class="tdRight">
				<input type="text" id="fileTypeDesc" name="fileTypeDesc" value="${upFileType.fileTypeDesc}" class="base-input-text" title="提示：可输入部分文件名模糊查询" maxlength="100"/>
			</td>
		</tr>
		
		<tr>
			<td colspan="4" class="tdBottom">
				<!-- <input type="button" value="新增" onclick="addFile();">  -->
<%-- 				<p:button funcId="08090301" type="add" value="新增"/> --%>
				<p:button funcId="080903" 	value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList" >
	<tr class="collspan-control">
		<th width="15%" style="text-align: center">文件类型编号</th>
		<th width="30%" style="text-align: center">文件类型描述</th>
		<th width="30%" style="text-align: center">上传文件缓存路径</th>
		<th width="25%" style="text-align: center">操作</th>
	</tr>
	<c:forEach items="${fileTypeList}" var="upFileTypeInfo">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
			<td style="text-align: center" >
				${upFileTypeInfo.fileType}
			</td>
			<td style="text-align: center">${upFileTypeInfo.fileTypeDesc}</td>
			<td style="text-align: center">
				<p:button funcId="08090304" value="修改" onclick="view('${upFileTypeInfo.fileType}');"/>
				
<%-- 				<input type="button"  value="删除" onclick="deleteFile('${upFileTypeInfo.fileType}');"/> --%>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${empty fileTypeList}">
		<tr>
			<td colspan="4" style="text-align: center"><span style="color: red">找不到相应的数据！请检查搜索条件再查询。</span></td>
		</tr>
	</c:if>
	
	</table>
</form>
<p:page/>
</body>
</html>