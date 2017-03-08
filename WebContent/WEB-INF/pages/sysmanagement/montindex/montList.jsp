<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控指标列表</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#montType").combobox();
}
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("input[type=checkbox]").attr("checked",false);
	$("#statusDiv1").find("label").eq("").click();
	
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
});
}
function Export(){
	if ($("#dataYear").val()==""||$("#montType").val()=="") {
		App.notyError("请至少选择指标类型和填写年份！");
		return false;
	}
	var data = {};
	data['montCode']=$("#montCode").val();
	if ($("#isSelectHis").is(":checked")) {
		data['montCodeHis']=$("#montCode").val();
	}
	data['org2Code']=$("#org2Code").val();
	data['montName']=$("#montName").val();
	data['montType']=$("#montType").val();
	data['dataYear']=$("#dataYear").val();
	data['matrCode']=$("#matrCode").val();
	data['matrName']=$("#matrName").val();
	var pass=false;
	App.ajaxSubmit("sysmanagement/montindex/download.do?<%=WebConsts.FUNC_ID_KEY%>=0812020301",
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
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
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
					pass =  true;
				}
				else
				{
					App.notyError("添加下载失败，请检查后重试!");
					pass =  false;
				}
			});
	return pass;
}
function doValidate(){
	if ($("#isSelectHis").is(":checked")) {
		$("#montCodeHis").val($("#montCode").val());
		$("#montFormSearch").submit();
		return true;
	}else {
		$("#montCodeHis").val("");
		$("#montFormSearch").submit();
		return true;
	}
}
function selectHis(){
	if($("#isSelectHis").is(":checked")){
		if($("#montCode").val()==""){
			App.notyError("监控指标代码不能为空!");
			return false;
		}
		if($("#matrCode").val()==""){
			App.notyError("物料编码不能为空!");
			return false;
		}
	}
	var year = $.trim($('#dataYear').val());
	if(year!=null && year!='' && !/^\d{4}$/.test(year)){
		App.notyError('请输入正确年份');
		return false;
	}
	var url ="<%=request.getContextPath()%>/sysmanagement/montindex/montList.do?VISIT_FUNC_ID=08120203";
	$("#montFormSearch").attr("action",url);
	App.submit($("#montFormSearch"));
}
</script>
</head>
<body>
<p:authFunc funcArray="08120203"/>
<form action="" method="post" id="montFormSearch">
	<table>
		<tr class="collspan-control">
			<th colspan="4">监控指标列表</th>
		</tr>
		<tr>
			<td class="tdLeft">监控指标代码</td>
			<td class="tdRight" >
				<input type="text"   name="montCode"  id="montCode" value="${bean.montCode}" class="base-input-text" maxlength="50"/>
			</td>
			<td class="tdLeft">监控指标名称</td>
			<td class="tdRight" >
				<input type="text" name="montName"   id="montName" value="${bean.montName}" class="base-input-text" maxlength="300"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">物料编码</td>
			<td class="tdRight">
				<input type="text" class="base-input-text" maxlength="50" id="matrCode" name="matrCode" value="${bean.matrCode}">
			</td>
			
			<td class="tdLeft">物料名称</td>
			<td class="tdRight">
				<input type="text" class="base-input-text" maxlength="300" id="matrName" name="matrName" value="${bean.matrName}">
			</td>
		</tr>
		<!-- 
		<tr>
			<td class="tdLeft">监控指标和物料变更历史</td>
			<td class="tdRight" colspan="3">
				<input type="checkbox" value="1" name="isSelectHis" id="isSelectHis"<c:if test="${bean.isSelectHis!='' and bean.isSelectHis!=null}">checked="checked"</c:if>>
				<span style="color:red">(查询监控指标和物料的历史变更记录，只需填写监控指标代码和物料编码)</span>
			</td>
		</tr>
		 -->
		<tr>
			<td class="tdLeft">指标类型</td>
			<td class="tdRight">
			<div class="ui-widget">
					<select id="montType" name="montType"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12' or PARAM_VALUE='21' or PARAM_VALUE='22')" selectedValue="${bean.montType}"/>
					</select>
				</div>
			</td>
			<td class="tdLeft">年份</td>
			<td class="tdRight">
					<input type="text" id="dataYear" name="dataYear" class="base-input-text" value="${bean.dataYear}">
			</td>
		</tr>
		<!-- 
		<tr>
			<td class="tdLeft">二级行或本部</td>
			<td class="tdRight">
				<input type="text" class="base-input-text" maxlength="50" id="org2Code" name="org2Code" value="${bean.org2Code}">
			</td>
		</tr>
		 -->
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button"    onclick="selectHis()" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
				<!-- 
				<input type="button" value="导出" onclick="Export()" >
				 -->
			</td>
		</tr>
	</table>
	<br>
	<br>
	<table class="tableList">
		<tr>
			<th width="5%">年份</th>
			<th width="10%">指标类型</th>
			<th width="10%">监控指标名称</th>
			<th width="20%">物料信息</th>
			<th width="10%">是否有效</th>
			<th width="10%">专项包类型</th>
			<th width="10%">一级行</th>
			<th width="10%">二级行</th>
			
			
		</tr>
		<c:forEach items="${ml}" var="item">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${item.dataYear}</td>
				<td>${item.montTypeName}</td>
				<td><span title="${item.montCode}">${item.montName}</span></td>
				<td>${item.matrCode} - ${item.matrName}</td>
				<td>
					<c:if test="${item.isValid eq '1' }">
						有效
					</c:if>
					<c:if test="${item.isValid eq '0' }">
						失效
					</c:if>
				</td>
				<td>${item.projType}</td>
				<td> 
					<c:if test="${item.montType eq '11' or item.montType eq '12'}">
						${bean2.org1Name }
					</c:if>
					<c:if test="${item.montType ne '11' and item.montType ne '12'}">
						-
					</c:if>
				</td>
				<td>
					<c:if test="${item.montType eq '21' or item.montType eq '22'}">
						${bean2.org2Name }
					</c:if>
					<c:if test="${item.montType ne '21' and item.montType ne '22'}">
						-
					</c:if>
				</td>
				
				
			</tr>
		</c:forEach>
		<c:if test="${empty ml}">
			<tr>
				<td colspan="8" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	
	
</form>
<p:page/>
</body>
</html>