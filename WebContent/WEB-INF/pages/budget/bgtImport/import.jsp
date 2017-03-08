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
		getCurrentYear($("#bgtYearSelect"),2);
		$("#bgtYearSelect").combobox();
		$("#bgtTypeSelect").combobox();
		$("#subTypeSelect").combobox();
// 		$("#operSubTypeSelect").combobox();
	}
	
	//为Select下拉框添加年份
	function getCurrentYear( _obj , yearCount ){
		var currentYear = new Date().getFullYear();
		for(var i=0 ; i<yearCount ; i++){
			$(_obj).append("<option value='"+(currentYear+i)+"'>"+(currentYear+i)+" 年</option>");
		}
	}
	
	//预算导入
	function dataImport(){
		if(!App.valid("#uploadFileForm")){
			App.notyError("请检查必输项信息是否已填，请填写后再提交！");
			return ;
		}
		//获取导入Excel文件名
		getSourceFileName($("input[name='fileRealName']"),$("#ExcelSourceFileName"));
		$("#path").val($("input[name='filePath']").val());
		
		if(!uploadFileTypeCheck($("input[name='fileRealName']").val())){return;}
		
		//1.验证表单，是否已上传文件(fileFlag对象找得到说明已上传Excel)
		var file1Flag = $("#attachmentsView input[name='filePath']").size();
		if(!(file1Flag > 0 )){
			$("#impFile").css("border-color","red");
			App.notyError("预算汇总EXCEL导入数据文件未上传，请检查！");
			return ;
		}
		if(!ajaxCheckData()){
			return ;
		}
		//
		var form = $("#uploadFileForm")[0];
		if('1'==$("#orgType").val()){
			//省行
			funcId = "02090103";
		}else{
			funcId = "02090203";
		}
		form.action = '<%=request.getContextPath()%>/budget/bgtImport/bgtImport.do?<%=WebConsts.FUNC_ID_KEY%>='+funcId;
		App.submit(form);
		
	}
	//校验是否可以导入  1：同类型的监控指标不能在审核中状态
	function ajaxCheckData(){
		var funcId = "";
		if('1'==$("#orgType").val()){
			//省行
			funcId = "02090102";
		}else{
			funcId = "02090202";
		}
		var operResult = false;
		var data = {};
		data['bgtYear'] =  $("#bgtYearSelect").val();
		data['subType'] =  $("#subTypeSelect").val();
		data['orgType'] =  $("#orgType").val();
		data['bgtType'] =  $("#bgtTypeSelect").val();
// 		data['operSubType'] =  $("#operSubTypeSelect").val();
		App.ajaxSubmit("budget/bgtImport/importPageAjax.do?<%=WebConsts.FUNC_ID_KEY%>="+funcId,
				{data:data,async : false},
				function(data){
					var returnValue = data.data;
					if(returnValue.flag){
						operResult = true;			//校验通过，允许进行数据导入操作
					}else{
						//校验不通过，不允许执行数据导入，并且提示错误信息
						App.notyError(returnValue.msg);
						operResult = false;
					}
				}
		);
		return operResult;
	}
	//获取上传Excel文件名(拿值地方,写值地方)
	function getSourceFileName( _getObj , _writeObj){
		//拿到上传文件名全路径，截取后，并写入指定的地方
		var sourceName = $(_getObj).val();		
		if( null != sourceName && '' != sourceName ){
			sourceName = sourceName.substr(sourceName.lastIndexOf("\\")+1);
			$(_writeObj).val( sourceName );
		}
	}
	//验证文件格式 (参数：字符串)
	function uploadFileTypeCheck( F_obj ){
		//验证是否以上传模板Excel
		if(F_obj == '' || typeof F_obj == 'undefined'){
			App.notyError("未上传模板文件");
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
	//上传控件1配置信息：用于处理[合同数据]上传附件信息
	function uploadFileFun( obj ){
		// 存放附件列表对象
		var fileListObj = "#attachmentsView";
		// 配置
		var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
		// 上传
		UploadUtils.uploadAttachment(fileListObj,config);
	};
	
	//导出
	function  downloadTemp(){
		var bgtYear = $("#bgtYearSelect").val();
		if(""==bgtYear){
			App.notyError("请选择预算年份");
			return false;
		}
		var bgtType = $("#bgtTypeSelect").val();
		if(""==bgtType){
			App.notyError("请选择预算类型");
			return false;
		}
		var subType = $("#subTypeSelect").val();
		if(""==subType){
			App.notyError("请选择预算子类型");
			return false;
		}
		var orgType = $("#orgType").val();
		var funcId="";
		var importPageId="";
		var upStepUrlOri="";
		if(orgType=="1"){
			funcId = "02090107";
			importPageId="020901";
			upStepUrlOri = "budget/bgtImport/shList.do";
		}else{
			funcId = "02090207";
			importPageId="020902";
			upStepUrlOri = "budget/bgtImport/fhList.do";
		}
		var data = {};
		data['bgtYear'] 	= bgtYear;
		data['bgtType'] 	= bgtType;
		data['subType'] 	= subType;
		data['orgType'] 	= orgType;
		App.ajaxSubmit("budget/bgtImport/downloadTemp.do?<%=WebConsts.FUNC_ID_KEY%>="+funcId,
				{data:data,async : false},
				function(data){
					flag = data.pass;
					if(flag){
						$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
							resizable: false,
							width: 290,
							height:'auto',
							modal: true,
							dialogClass: 'dClass',
							title: "跳转至下载页面",
							buttons:[
							         {
							        	 text:"确认",
											click:function(){
								        	 	var form = document.forms[0];
								        	 	var upStepUrl = upStepUrlOri;				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
								        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
								        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
								        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
								        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
								        		$("form #upStepParams").val(upStepParams);
												form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId='+importPageId;
												App.submit(form);
											}   
							         },
									{
										text:"取消",
										click:function(){
											$(this).dialog("close");
										}
									}
							]
						});
						isPass =  true;
					}
					else
					{
						App.notyError("添加下载失败，可能是因为表里没有该条数据，请检查后重试!");
						isPass =  false;
					}
				});
		return isPass;
	}
	//年初 还是追加
// 	function impType(){
// 		var impType = $("#operSubTypeSelect").val();
// 		if('1'==impType){
// 			$("#bgtTypeTr").hide();
// 		}else{
// 			$("#bgtTypeTr").show();
// 		}
			
			
// 	}
</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form id="budgetSumImportForm" method="post"></form>
<form action="" method="post" id="uploadFileForm" enctype="multipart/form-data">
<input type="hidden" id="orgType" name = "orgType" value="${bean.orgType }">
	<p:token/>
	<table>
		<tr>
			<th colspan="2" id="hideInfoPart">
				预算汇总导入
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%"><span class="red">*</span>预算年份</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="bgtYearSelect" name="bgtYear" valid errorMsg="请选择有效年份">
						<option value="">请选择</option>
					</select>
				</div>
			</td>
		</tr>
		<!-- 
		<tr>
		<td class="tdLeft" width="20%"><span class="red">*</span>导入类型</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="operSubTypeSelect" onchange="impType()" name="operSubType" valid errorMsg="请选择下达类型">
						<option value="">请选择</option>
						<option value="0">年初预算</option>
						<option value="1">追加预算</option>
					</select>
				</div>
			</td>
		</tr>
		 -->
		<tr id="bgtTypeTr">
			<td class="tdLeft" width="20%"><span class="red">*</span>预算类型</td>
			<td class="tdRight" width="30%">
				<div class="ui-widget">
					<select id="bgtTypeSelect" name="bgtType" valid errorMsg="请选择预算类型">
						<option value="">请选择</option>
						<option value="00">临时预算</option>
						<option value="01">正式预算</option>
						<option value="02">追加预算</option>
					</select>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft" width="20%"><span class="red">*</span>预算子类型</td>
			<td class="tdRight" width="30%" >
				<div class="ui-widget">
					<!-- 省行标签页  orgType=1省行 -->
					<c:if test="${bean.orgType eq '1'}">
						<select id="subTypeSelect" name="subType" valid errorMsg="请选择省行预算子类型">
							<option value="">请选择</option>
							<option value="11">专项包</option>
							<option value="12">省行统购资产</option>
						</select>
					</c:if>
					<!-- 分行标签页  orgType=2分行-->
					<c:if test="${bean.orgType eq '2'}">
						<select id="subTypeSelect" name="subType" valid errorMsg="请选择分行预算子类型">
							<option value="">请选择</option>
							<option value="21">非省行统购资产</option>
							<option value="22">非专项包费用</option>
							
						</select>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%"><span class="red">*</span>导入数据文件</td>
			<td class="tdRight"  > 
					<input id="ExcelSourceFileName" type="hidden" name="sourceFilename"/>
						<input id="path" type=hidden name="path"/>
					
				<div style="float: left">
				<input type="button" title="请选择Excel格式文件上传" id="impFile" value="上传预算汇总数据"  onClick="uploadFileFun(this)"/>
				<div id="attachmentsView"  >
					<ul style='padding:0;margin:0;'>
					</ul>
				</div></div>
				<div style="float: left;padding-top: 5px;margin-left: 40px;">
				<a href="#" onclick="downloadTemp()" style="color: red;text-decoration: underline;">(导入模板下载)</a></div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" title="校验上传文件" value="数据导入" onclick="dataImport()">
				<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
	
</form>
<p:page/>
</body>
</html>