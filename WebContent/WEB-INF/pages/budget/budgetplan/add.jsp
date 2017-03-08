<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算模板新增</title>
<script type="text/javascript">

//平台页面加载初始化
function pageInit(){
	App.jqueryAutocomplete();
	//加载时为有效年份添加初始值
	getCurrentYear($("#valiYearSelect"),6);
	$("#dataTypeSelect").combobox();
	$("#dataAttrSelect").combobox();
	$("#valiYearSelect").combobox();
}

$(function(){
});

//为Select下拉框添加年份
function getCurrentYear( _obj , yearCount ){
	var currentYear = new Date().getFullYear();
	for(var i=0 ; i<yearCount ; i++){
		$(_obj).append("<option value='"+(currentYear+i)+"'>"+(currentYear+i)+" 年</option>");
	}
}

//获取树控件checked选中值
function writeCheckedValue(){
	writeValue(treePlugin1 , $("#availableOrgList"));
}

//获取树控件选中值，并写入指定的文本框(参数：控件别名(直接对象)、写入的文本框obj)
//调用格式：writeValue(treePlugin1 , $("#checkedValue"));
function writeValue( _jsVarName , _writeObj ){
	var checkedNodes = _jsVarName.getSelectOrgList();
	var checkedIdList = "";
	for( var i = 0 ; i < checkedNodes.length ; i++ ){
		checkedIdList += trim(checkedNodes[i].id)+",";
	}
	checkedIdList = checkedIdList.substring(0,checkedIdList.lastIndexOf(","));
	$(_writeObj).val(checkedIdList);
}

//提交前调用
function doValidate() {
	//获取导入Excel文件名
	getSourceFileName($("input[name='fileRealName']"),$("#ExcelSourceFileName"));
	$("#serverFile").val($("input[name='filePath']").val());
	//验证表单
	if(!App.valid("#addBudgetForm")){return;}
	//验证上传文件格式
	if(!uploadFileTypeCheck($("input[name='fileRealName']").val())){return;}
	
	//年初预算校验(如果选择年初预算则进行校验)
	if($("#dataTypeSelect").val()=="0"){
		if(!ajaxCheckYearFirstBudgetCount()){return;}
	}
	return true;
}

//获取上传Excel文件名(拿值地方,写值地方)
function getSourceFileName( _getObj , _writeObj){
	//拿到上传文件名全路径，截取后，并写入指定的地方
	var sourceName = $(_getObj).val();		
	if( null != sourceName && '' != sourceName ){
		sourceName = sourceName.substr(sourceName.lastIndexOf("\\")+1);
// 		alert('源文件名:'+sourceName);
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

//重置查询条件
function resetAll(){
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
	$("#excelFile").val("");
};

function uploadFileFun( obj ){
// 	if($("#attachmentsView a").size()>0){			//该功能校验上传下载工具类已实现，具体做法为：在config中配置uploadMode:"single"即可
// 		App.notyError("只允许上传一个Excel模板");
// 		return;
// 	}
	// 存放附件列表对象
	var fileListObj = "#attachmentsView";
	// 配置
	var config = {filePath:"filePath",fileName:"fileRealName",uploadMode:"single",contextPath:'<%=request.getContextPath()%>',passSuffix:"xls,XLS,XLs,Xls,xlsx,XLSX,XLsx,Xlsx"};
	// 上传
	UploadUtils.uploadAttachment(fileListObj,config);
};

//检查年初预算数量
function ajaxCheckYearFirstBudgetCount()
{
	var validateFlag = false ; 
	var data = {};
	//如果后台接收参数格式为String，则后台参数=dataYear
	var selectedYear = $("#valiYearSelect").val();
	data["dataYear"] = selectedYear ;
	App.ajaxSubmit("budget/budgetplan/ajaxCheckYearFirstBudgetCount.do?<%=WebConsts.FUNC_ID_KEY %>=02010101",
		{data:data,async:false}, 
		function(data) {
			var count=data.countResult;
			if(count>=1){
				App.notyError(selectedYear+" 年初预算已经存在!");
			}else{
				validateFlag = true;
			}
		}
	);
	return validateFlag;
}

function getTest(){
}

</script>
</head>
<body>
<p:authFunc funcArray="020102,02010101"/>
<form action="" method="post" id="addBudgetForm" encType="multipart/form-data">
<p:token/>
	<table id="approveChainTable">
		<tr>
			<th colspan="4">预算模板新增</th>
<!-- 			style="text-align: left" -->
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>预算类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataTypeSelect" name="dataType" valid errorMsg="请选择预算类型">
						<option value="">请选择</option>
						<option value="0">年初预算</option>
						<option value="1">追加预算</option>
					</select>
				</div>
			</td>
			<td class="tdLeft"><span class="red">*</span>有效年份</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear" valid errorMsg="请选择有效年份">
						<option value="">请选择</option>
					</select>
				</div>
			</td>
		</tr>
        <tr>
			<td class="tdLeft"><span class="red">*</span>预算指标</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataAttrSelect" name="dataAttr" valid errorMsg="请选择预算指标">
						<option value="">请选择</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
						 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
						 orderType="ASC" />
					</select>
				</div>
			</td>
			<td class="tdLeft"><span class="red">*</span>选择可用机构</td>
			<td class="tdRight" >
<%-- 					<forms:OrgSelPlugin suffix="1" radioFlag="false" parentCheckFlag="false" selHalfFlag="false" changeFun="writeCheckedValue" jsVarName="treePlugin1" rootNodeId="${currentUserOrg21Code}"/> --%>
<!-- 					<input type="hidden" id="availableOrgList" name="availableOrgList" > -->
				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" parentCheckFlag="true" leafOnlyFlag="true" triggerEle="#addBudgetForm orgIdName,availableOrgList::name" changeFun="writeCheckedValue" jsVarName="treePlugin1" rootNodeId="${currentUserOrg21Code}" />
				<input type="hidden" id="availableOrgList" name="availableOrgList" class="base-input-text" />
				<input class="base-input-text" type="text" id="orgIdName" name="orgIdName" readonly="readonly" valid errorMsg="请选择可用机构"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >
				<span class="red">*</span>上传模板  
			</td>
			<td colspan="3" style="text-align: left" class="tdRight">
				<input id="ExcelSourceFileName" type="hidden" name="sourceFileName"/>
				<input id="serverFile" type="hidden" name="serverFile"/>
				<input type="button" id="uploadFile" value="上传Excel"  onClick="uploadFileFun(this)"/> 
				<div id="attachmentsView">
					<ul style='padding:0;margin:0;'>
					</ul>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4">
			    <p:button funcId="020102" value="提交"/>
				<input type="button" value="重置"  onclick="resetAll()">
<!-- 				<input type="button" value="测试btn" onclick="getTest()"> -->
			</td>
		</tr>
	</table>
</form>
</body>
</html>