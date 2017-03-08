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
<title>数据导出</title>
<script type="text/javascript">
//提交前调用
function doValidate() {
	if(!App.valid("#exportFormSearch")){return;}
	return true;
}
function pageInit(){
	App.jqueryAutocomplete();
	$("#proType").combobox();
	$(".sel").combobox();
	$("#status").combobox();
	$("#subMontTypeSelect").combobox();
	$("#subAprvTypeSelect").combobox();
}

// 
function ajaxDataExist(){
	if($("#proType").val() == ""){
		App.notyError("请选择数据类型！");
		return false;
	}
	if($("#subType").val() == ""){
		App.notyError("请选择子类型!");
		return false;
	}
	var data = {};
	orgType= $("#orgType").val() ;
	dataYear = $("#dataYear").val() ;
	proType=$("#proType").val();
	subType=$("#subType").val();
	data["orgType"] = orgType ;
	data["dataYear"] = dataYear ;
	data["proType"]=proType;
	data["subType"]=subType;
	var func_id="";
	if(orgType =='01'){
		func_id="0811010101";
	}else{
		func_id ="0811010201";
	}
	App.ajaxSubmit("sysmanagement/montAprvBatch/export/ajaxExport.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
			{data:data,async:false}, 
			function(data) {
				if(data.msgInfo){
					App.notyError(data.msgInfo);
					return false;
				}
				var result=data.value;
				var flag = result.flag;
				var msg = result.msg;
				if(flag == "N"){
					App.notyError(msg);
					return false;
				}else{
					Export(orgType,proType,subType,dataYear);
				}
			});

}
function Export(orgType,proType,subType,dataYear){
	var isPass=true;
	var data = {};
	data['orgType'] =  orgType;
	data['proType'] =  proType;
	data['subType'] =  subType;
	data['dataYear'] = dataYear;
	var func_id="";
	var parent_func_id = "";
	if(orgType =='01'){
		func_id="0811010102";
		parent_func_id="08110101";
	}else{
		func_id ="0811010202";
		parent_func_id="08110102";
	}
	App.ajaxSubmit("sysmanagement/montAprvBatch/export/download.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
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
							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId='+parent_func_id;
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
//提示文字信息
$(document).ready(function() {
	$(".prompt").each(function(){
		var html = $(this).html();
		check(this,html);
	});
	
	$("input").bind("keyup", function(){
		validateValue($(this));
	});
	
	$("textarea").bind("keyup", function(){
		validateValue($(this));
	});

});

function showYear(){
	var orgType = $("#orgType").val();
	var func_id="";
	if(orgType =='01'){
		func_id="0811010107";
	}else{
		func_id ="0811010207";
	}
	var proType=$("#proType").val();
	var subType=$("#subType").val();
	var data = {};
	data['orgType'] =  orgType;
	data['proType'] =  proType;
	data['subType'] =  subType;
	App.ajaxSubmit("sysmanagement/montAprvBatch/export/showYear.do?<%=WebConsts.FUNC_ID_KEY %>="+func_id,
			{data:data,async : false},
			function(data){
				var returnValue = data.data;
				if(returnValue.flag == true){
					var dataYear =returnValue.msg;
					if(""==dataYear || null == dataYear || "null" == dataYear ){
					}else{
						$("#exportButton").val("导出"+returnValue.msg+"年的数据");
						$("#dataYear").val(returnValue.msg);
					}
					
				}else{
					App.notyError(returnValue.msg);
				}
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="0811020102"/>
<form action="" method="post" id="exportFormSearch">
<input type="hidden" id="dataYear" name="dataYear" value="${dataYear }"/>
<input type="hidden" value="${bean.orgType }" id="orgType"/>
	<p:token/>
		<table>	
			<tr>
				<th colspan="2">导出</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">数据类型</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
					<select id="proType" name="proType" class="erp_cascade_select" >
						<option value="">-- 请选择 -- </option>
						<option value="01">监控指标</option>
						<option value="02">审批链</option>
<%-- 						<c:forEach items="${selectList}" var="item"> --%>
<%-- 							<option value="${item.value }">${item.desc }</option> --%>
<%-- 						</c:forEach> --%>
					</select>
				</div>	
				</td>
			</tr>
			<tr>
				<td class="tdLeft" width="25%">子类型</td>
				<td class="tdRight" width="25%">
					<c:if test="${bean.orgType=='01' }">
						<div class="ui-widget">
							<!-- <select   name="subType" id="subType" class="erp_cascade_select sel" onchange="showYear()">
								<option value="">-- 请选择 --</option>
								<option value="11">专项包</option>
								<option value="12">省行统购</option>
							</select> -->
							<select id="subType" name="subType"   class="erp_cascade_select sel" onchange="showYear()">
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')"/>
							</select>
						</div>
					</c:if>
					<c:if test="${bean.orgType=='02' }">
						<div class="ui-widget" id="sub1">
							<!-- <select  name="subType" id="subType" class="erp_cascade_select sel" onchange="showYear()">
								<option value="">-- 请选择 --</option>
								<option value="21">非省行统购资产</option>
								<option value="22">非专项包费用</option>
							</select -->
							<select id="subType" name="subType"   class="erp_cascade_select sel" onchange="showYear()">
								<option value="">-请选择-</option>
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='21' or PARAM_VALUE='22')"/>
							</select>
						</div>
					</c:if>
				</td>
			</tr>
			<tr>
					<td colspan="2" class="tdWhite" id="buttonTd">
						 <input  type="button" id="exportButton"  onclick="ajaxDataExist()"   value="导出${dataYear }年的数据"/>
					</td>
				</tr>
		</table>
</form>
<p:page/>
</body>
</html>