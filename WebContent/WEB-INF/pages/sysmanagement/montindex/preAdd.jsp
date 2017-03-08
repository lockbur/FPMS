<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>省行监控指标新增</title>
<style type="text/css">
img{
border:none; 
}
</style>
<script type="text/javascript">
//提示框请求后台

function pageInit(){
	App.jqueryAutocomplete();
 	$("#montType").combobox();
 	$("#dataYear").combobox();
}
//选择物料
function selectMatr()
{	
	var montType=$("#montType").val();
	if(montType==null||montType==''){
		App.notyError("请先选择指标类型!");
		return false;
	}
	//未维护dao数据库的
	var selectNoUpMatrs="";
	$('input[type=checkbox][name="noUpMatrs"]').each(function(){
		var matrCode = $(this).attr("title");
		selectNoUpMatrs =selectNoUpMatrs+matrCode;
	});
	var orgType = $("#orgType").val();
	var url = "";
	if(orgType == '01'){
		var projType=$("#projType").val();
		url="<%=request.getContextPath()%>/sysmanagement/montindex/addMatrs.do?<%=WebConsts.FUNC_ID_KEY %>=0812020102&selectNoUpMatrs="+selectNoUpMatrs+"&montType="+montType+"&projType="+projType;
	}else{
		 url="<%=request.getContextPath()%>/sysmanagement/montindex/addMatrs.do?<%=WebConsts.FUNC_ID_KEY %>=0812020205&selectNoUpMatrs="+selectNoUpMatrs+"&montType="+montType;
	}
	$.dialog.open(
			encodeURI(encodeURI(url)),
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"物料选择",
			    id:"dialogCutPage",
				close: function(){
					var returnValue = art.dialog.data('returnValue'); 
					if(returnValue){
						$("#matrsDiv").html(returnValue);
					}
				}		
			}
		 );
}
function cancle(matrCode){
	//$(obj).parent().parent().remove();
	$("#"+matrCode+"_div").remove();
}
function doValidate() {
	if(!App.valid("#montForm")){
		 return;
	}
	
	//检查物料是否选择
	if($('input[type="checkbox"]').length<=0){
		App.notyError("请选择物料!");
		return false;
	}
	if(checkMont()){
		App.notyError("该监控指标已存在!");
		return false;
	}
	//校验 当年数据在数据库里是否存在 如不存在则不让手动添加
	var data = {};
	data['montType'] =  $("#montType").val();
	data['dataYear'] = $("#dataYear").val(); 
	var orgType = $("#orgType").val();
	var url="";
	if(orgType=='01'){
		url = "sysmanagement/montindex/preAddIsTrue.do?VISIT_FUNC_ID=0812020113";
	}else{
		url = "sysmanagement/montindex/preAddIsTrue.do?VISIT_FUNC_ID=0812020113";
	}
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			 var flag = data.isExist;
			 if(flag == false){
				 //不存在
				 var msg = data.msg;
				 App.notyError(msg);
			 }else{
				 $("#montForm").submit();
			 }
	});
}
//检查监控指标是否存在
function checkMont(){
	var list = false;
	var data = {};
	data['montName'] =  $("#montName").val();
	data['org21Code'] = "${montBean.org21Code}"; 
	var orgType = $("#orgType").val();
	var url = "";
	if(orgType == '01'){
		url = "sysmanagement/montindex/checkMont.do?VISIT_FUNC_ID=0812020103";
	}else{
		url = "sysmanagement/montindex/checkMont.do?VISIT_FUNC_ID=0812020203";
	}
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			list = data.isExist;
	});
	return list; 
}
//通过指标类型来判断是否需要项目类型
function changeProjType(){
	var montType=$("#montType").val();
	if(montType != $("#mt").val() && montType != null && montType != ''){
		$("#matrsDiv").html("");
	}
	if(montType!=null &&montType!=''){
		if(montType==11){
			$("#projTypeTd").show();
			$("#projTypeTd1").show();
			//$("#projType").attr("valid","");
		}
		else{
			$("#projTypeTd").hide();
			$("#projTypeTd1").hide();
			$("#projType").val("");
			//$("#projType").removeAttr("valid");
		}
	}
	if($("#montType").val() != null && $("#montType").val() != ''){
		$("#mt").val($("#montType").val());//每次点击存入
	}
}
function selectProjType(){
	var projType = $("#projType").val();
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/montindex/selectProjType.do?<%=WebConsts.FUNC_ID_KEY %>=0812020110&projType='+projType,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"专项包类型选择",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('proTypeObject'); 
					if(object){
						$("#projType").val(object.paramName);
					}
					}		
			}
		 );
}
function changeMatr(){
// 	var montType=$("#montType").val();
// 	if(montType != $("#mt").val() && montType != null && montType != ''){
// 		$("#matrsDiv").html("");
// 	}
// 	if($("#montType").val() != null && $("#montType").val() != ''){
// 		$("#mt").val($("#montType").val());//每次点击存入
// 	}
	$("#matrsDiv").html("");
}
</script>
</head>
<body>

<p:authFunc funcArray="011305,011306,011307,011302,011301,0701,0812020104"/>
<form action="" method="post" id="montForm">
<input type="hidden" value="${montBean.orgType }" id="orgType" name="orgType"/>
<p:token/>
<table>
	<tr class="collspan-control">
			<th colspan="4">新增</th>
		</tr>
	<tr><td>
	<table id="approveChainTable">
		<tr>
			<td class="tdLeft"> 指标类型<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" id="montNameTd">
				<div class="ui-widget">
					<input type="hidden" id="mt" name="mt"/>
					<c:if test="${montBean.orgType eq '01' }">
						<select id="montType" name="montType"   class="erp_cascade_select" valid errorMsg="请选择指标类型。"  onchange="changeProjType(),changeMatr()">
							<option value="">-请选择-</option>
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')"/>
						</select>
					</c:if>
					<c:if test="${montBean.orgType eq '02' }">
						<select id="montType" name="montType"   class="erp_cascade_select" valid errorMsg="请选择指标类型。"  onchange="changeProjType()">
							<option value="">-请选择-</option>
							<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='21' or PARAM_VALUE='22')"/>
						</select>
					</c:if>
				</div>
			</td>
			<td class="tdLeft"  id="ss"> 监控指标名称<span style="color: #A41E1E">*</span></td>
			<td class="tdRight"  id="montNameTd">
				<input type="text" id="montName" name="montName"   class="base-input-text"  valid  errorMsg="请输入监控指标名称。"  maxlength="300"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td class="tdLeft">物料<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
				<a href="#" onclick="selectMatr()">  
					<img src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="选择物料" style="border:none; cursor:pointer;vertical-align: middle;margin-left:15px;heigth:30px;width:30px;"/>
				</a>
			</td>
			
			<td class="tdLeft">年份<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataYear" name="dataYear">
						<option value="${thisYear }">--${thisYear}--</option>
						<option value="${thisYear+1 }">--${thisYear+1}--</option>
					</select>
				</div>
			</td>
			
		</tr>
		<c:if test="${montBean.orgType eq '01' }">
		<tr>
			<td class="tdLeft"  id="projTypeTd" > 专项包类型</td>
			<td class="tdRight" id="projTypeTd1" colspan="3">
					<input type="text" id="projType" name="projType"   class="base-input-text" maxlength="100" style="width:200px;"><a href="#" onclick="selectProjType()"><img src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="选择项目类型" style="border:none; cursor:pointer;vertical-align: middle;margin-left:15px;heigth:30px;width:30px;"/></a>
			</td>
		</tr>
		</c:if>
	</table></td></tr>
	<tr></tr>
	<tr><td>
	<table>
		<tr>
			<th>物料列表</th>
		</tr>
		<tr>
			<td class="tdRight">
			<div id="matrsDiv"></div>
			</td>
		</tr>
		<tr>
			<td class="tdWhite">
				    <p:button funcId="0812020104" value="提交"/>
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
	</td></tr>
</table>
</form>
</body>
</html>