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
<title>导入Excel数据</title>

<script type="text/javascript">
	//页面初始化执行加载
	function pageInit(){
		App.jqueryAutocomplete();
	}
	
	/**
	 *[9-9使用]：【数据导入】按钮
	 *		提交数据迁移请求操作前，对是否上传文件进行相关校验，并复制信息到Form提交到后台
	 */
	function dataImport(){
		//1.验证表单，是否已上传文件
		var file1Flag = $("#attachmentsView1 input[name='filePath']").size();
		var file2Flag = $("#attachmentsView2 input[name='filePath']").size();
		if(!(file1Flag > 0 && file2Flag > 0 )){
			if( !(file1Flag > 0 )){
				App.notyWarning("[合同数据]未上传，请检查！");
			}else{
				App.notyWarning("[付款数据]未上传，请检查！");
			}
			return ;
		}
		//2.将上传的两个模板数据文件的相关信息复制到提交form中，用于后台接收该信息(write Upfile's info to form , used for upload) 
		dealUpFileInfo();
		//3.提交请求
		var form = $("#uploadFileForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/importDataToDBTB.do?<%=WebConsts.FUNC_ID_KEY%>=060201";
		form.submit();
	}
	/**
	 *	【数据导入】按钮
	 *		方法描述：将上传的Excel模板中数据迁移保存到数据库中操作
	**/
	function dataImportByFileType(){
		var fileObj1 = $("#impFile1")[0];
		var fileObj2 = $("#impFile2")[0];
		
		//1.校验需要上传的两个文件是否已经上传
		if( !validateUploadFlag( $("#impFile1") , $("#impFile2")) ){
			//console.info('第一步，不通过');
			return ;
		}
		//2.获取上传2文件的相关信息
		var impFile1 = getUpfileInfo( $("#impFile1") , new Array("xls" , "xlsx") );
		var impFile2 = getUpfileInfo( $("#impFile2") , new Array("xls" , "xlsx") );
		//3.校验上传文件格式是否符合
		if( !impFile1[2] || !impFile2[2] ){
			App.notyError( '上传文件格式不符合，请检查重新上传！ <br/> PS:只允许上传xls、xlsx格式' );
			return ;
		}
		//4.校验上传文件大小是否符合规定
		var allowSize = 50;					//允许上传的文件大小
		var valiResult = validateUpfileSize(fileObj1 , fileObj2 , allowSize);
		if(!valiResult){
			//上传文件大小校验不通过，不再向后执行
			return ;
		}
		//5.全部校验通过，执行Excel数据导入操作
		var form = $("#uploadFileForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/importDataToDBTB.do?<%=WebConsts.FUNC_ID_KEY%>=060201";
		form.submit();
	}
	
	//【HQQ-TEST】
	function testSqlldrDataImport(){
		//测试，忽略文件的校验部分，直接提交，执行Excel数据导入操作
		var form = $("#uploadFileForm")[0];
		form.action = "<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/importDataToDBTB.do?<%=WebConsts.FUNC_ID_KEY%>=060201";
		form.submit();
	}
	
	function testUtil(){
// 		console.info($("#impFile1"));
// 		console.info($("#impFile1")[0].files[0].size);
// 		console.info($("#impFile2").val());
// 		console.info($("#impFile2")[0].files[0].size);
	}
	
	//上传控件1配置信息：用于处理[合同数据]上传附件信息
	function uploadFileFun1( obj ){
		// 存放附件列表对象(上传域DIV的id)
		var fileListObj = "#attachmentsView1";
		// 配置
		var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
		// 上传
		UploadUtils.uploadAttachment(fileListObj,config);
	};
	
	//上传控件2配置信息：用于处理[付款数据]上传附件信息
	function uploadFileFun2( obj ){
		// 存放附件列表对象
		var fileListObj = "#attachmentsView2";
		// 配置
		var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
		// 上传
		UploadUtils.uploadAttachment(fileListObj,config);
	};
	
	
	//[数据导入]操作请求dataImport()开始之后，首先调用下面方法，获取文件路径+文件源名
	function dealUpFileInfo(  ){
		//1.获取平台上传控件返回的上传文件信息(已进行Ajax上传)
		var upFile1Path = $("#attachmentsView1 input[name='filePath']").val();
		var upFile1RealName = $("#attachmentsView1 input[name='fileRealName']").val();
		var upFile2Path = $("#attachmentsView2 input[name='filePath']").val();
		var upFile2RealName = $("#attachmentsView2 input[name='fileRealName']").val();
		//2.将需要传递到后台的信息写入Form中，等待提交Action请求时传送 
		$("#hideInfoPart").append("<input type='hidden' name='impFile1Path' value='"+upFile1Path+"'>");
		$("#hideInfoPart").append("<input type='hidden' name='impFile1OriginalName' value='"+upFile1RealName+"'>");
		$("#hideInfoPart").append("<input type='hidden' name='impFile2Path' value='"+upFile2Path+"'>");
		$("#hideInfoPart").append("<input type='hidden' name='impFile2OriginalName' value='"+upFile2RealName+"'>");
	}
	
</script>
</head>
<body>
<p:authFunc funcArray="0602,060201"/>
<form id="dataMigrateForm" method="post"></form>
<form action="" method="post" id="uploadFileForm" enctype="multipart/form-data">
	<p:token/>
	<table>
		<tr>
			<th colspan="4" id="hideInfoPart">
				数据迁移
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%"><span class="red">*</span>导入合同数据文件</td>
			<td class="tdRight" colspan="3"> 
				<input type="button" id="impFile1" value="合同数据Excel"  onClick="uploadFileFun1(this)"/>
<!-- 				<input type="hidden" id="f1" name="f1" valid> -->
				<div id="attachmentsView1">
					<ul style='padding:0;margin:0;'>
					</ul>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft" width="20%"><span class="red">*</span>导入付款数据文件</td>
			<td class="tdRight" colspan="3"> 
				<input type="button" id="impFile2" value="付款数据Excel"  onClick="uploadFileFun2(this)"/>
<!-- 				<input type="hidden" id="f2" name="f2" valid> -->
				<div id="attachmentsView2">
					<ul style='padding:0;margin:0;'>
					</ul>
				</div>
			</td>
		</tr>
		
		
<!-- 		<tr><td>【上面是用于测试的】</td></tr> -->
		
<!-- 		<tr> -->
<!-- 			<td class="tdLeft" width="25%"><span class="red">*</span>合同数据</td> -->
<!-- 			<td class="tdRight" width="75%" colspan="3"> -->
<!-- 				<input type="file" id="impFile1" name="impFile1" style="width: 300px;" class="base-input-text" valid errorMsg="请选择合同数据文件。" /> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td class="tdLeft" width="25%"><span class="red">*</span>付款数据</td> -->
<!-- 			<td class="tdRight" width="75%" colspan="3" > -->
<!-- 				<input type="file" id="impFile2" name="impFile2" style="width: 300px;" class="base-input-text" valid errorMsg="请选择付款数据文件。" /> -->
<!-- 			</td> -->
<!-- 		</tr> -->
		
		
		<!-- HQQ-TEST START-->
<!-- 		<tr> -->
<!-- 			<td class="tdLeft" width="25%"><span class="red">*</span>【测试】-SQLLDR数据</td> -->
<!-- 			<td class="tdRight" width="75%" colspan="3" > -->
<!-- 				<input type="file" id="impFile1" name="impFile1" style="width: 300px;" class="base-input-text" valid errorMsg="请选择测试的数据文件。" /> -->
<!-- 			</td> -->
<!-- 		</tr> -->
		<!-- HQQ-TEST END-->

		<tr>
			<td colspan="4">
				<input type="button" value="数据导入" onclick="dataImport()">
<!-- 				<input type="button" value="TestGetUpFileByPlugin" onclick="dealUpFileInfo()"> -->
<!-- 				<input type="button" value="测试-数据导入" onclick="testSqlldrDataImport()"> -->
				<input type="button" value="返回" onclick="backToLastPage('todataimportpage.do?VISIT_FUNC_ID=0602');">
<!-- 				<input type="button" value="返回" onclick="backToLastPage();"> -->
<!-- 				<input type="button" value="测试工具" onclick="testUtil();"> -->
			</td>
		</tr>
		
	</table>
</form>
<p:page/>
</body>
</html>