<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
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
<title>省行监控指标列表</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	$("#montType").combobox();
	$("#valiYearSelect").combobox();
	
}
function resetAll() {
	$("select").val("");
	$(":text").val("");
	$("#deptId").val("");
	$("#fstdeptIdDiv,#seddeptIdDiv").css("display","none");
	
	$("#userTypeDiv").find("label").eq(0).click();
	$("#isDeletedDiv").find("label").eq(0).click();
		$("select").each(function(){
			var id = $(this).attr("id");
			if(id!=""){
				var year = $("#"+id+" option:first").text();
				$(this).val(year);
				 $(this).next().val(year) ;
			}
			
	});
}
//编辑页面
function edit(montCode,montType,org21Code){
	$("#org21Code1").val(org21Code);
	$("#montCode1").val(montCode);
	$("#montType1").val(montType);
	var url = '<%=request.getContextPath()%>/sysmanagement/montindex/preEdit.do?<%=WebConsts.FUNC_ID_KEY%>=0812020105';
	$('#editForm').attr("action",url);
	App.submit( $("#editForm"));
}
function del(org21Code,montCode,montType){
	//省行检查监控指标是否存在于合同设备表中
		var data = {};
		data['montCode'] =  montCode;
		var orgType = $("#orgType").val();
		var url = "";
		var url2 = "";
		if(orgType =='01'){
			 url = "sysmanagement/montindex/checkMontCode.do?VISIT_FUNC_ID=0812020112";
			 url2 = "<%=request.getContextPath()%>/sysmanagement/montindex/del.do?<%=WebConsts.FUNC_ID_KEY%>=0812020108";
		}else{
			 url = "sysmanagement/montindex/checkMontCode.do?VISIT_FUNC_ID=0812020210";
			 url2 = '<%=request.getContextPath()%>/sysmanagement/montindex/del.do?<%=WebConsts.FUNC_ID_KEY%>=0812020208';
		}
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
				var resultMap = data.data;
				if(!resultMap.flag){
					App.notyError(resultMap.msg);
					return false;
				}else{
					$("#org21Code1").val(org21Code);
					$("#montCode1").val(montCode);
					$("#montType1").val(montType);
					$( "<div>确认要删除该监控指标?与该监控指标相关的所有审批链会一并删除</div>" ).dialog({
						resizable: false,
						height:180,
						modal: true,
						dialogClass: 'dClass',
						buttons: {
							"确定": function() {
									$("#editForm").attr("action",url2);
									$("#editForm").submit();
							},
							"取消": function() {
								$( this ).dialog( "close" );
							}
						}
					});
				}
		});
	
	
}


</script>
</head>

<body>
<p:authFunc funcArray="011301,0812020101,08120201,0812020201,08120202"/>
<form method="post" id="editForm" action="" >
	<input type="hidden"  name="montCode"  id="montCode1" value="${selectInfo.montCode}" />
	<input type="hidden"  name="montType"  id="montType1" />
	<input type="hidden"  name="org21Code"  id="org21Code1"  />
	<input type="hidden"  name="orgType" id="orgType"  value="${orgType }"  />
</form>
<form method="post" id="userForm" action="">
<input type="hidden"  name="orgType"  value="${orgType }"  />
	<table>
		<tr class="collspan-control">
			<th colspan="4">监控指标查询</th>
		</tr>	
		<tr>
			<td class="tdLeft">监控指标代码</td>
			<td class="tdRight">
				<input type="text"  name="montCode"  id="montCode" value="${selectInfo.montCode}" class="base-input-text" maxlength="50"/>
			</td>
			<td class="tdLeft">监控指标名称</td>
			<td class="tdRight">
				<input type="text" name="montName"   id="montName" value="${selectInfo.montName}" class="base-input-text" maxlength="300"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">指标类型</td>
			<td class="tdRight">
			<div class="ui-widget">
					<c:if test="${orgType eq '01' }">
						<select id="montType" name="montType"   class="erp_cascade_select" valid errorMsg="请选择项目类型。" >
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')" selectedValue="${selectInfo.montType}"/>
					</select>
					</c:if>
					<c:if test="${orgType eq '02' }">
						<select id="montType" name="montType"   class="erp_cascade_select" valid errorMsg="请选择项目类型。" >
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='21' or PARAM_VALUE='22')" selectedValue="${selectInfo.montType}"/>
					</select>
					</c:if>
				</div>
			</td>
			<td class="tdLeft">年份</td>
			<td class="tdRight">
				<input type="text"  name="dataYear"  id="dataYear" value="${selectInfo.dataYear}" class="base-input-text" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				
				
				<c:if test="${orgType eq '01' }">
				<p:button funcId="08120201" value="查找"/>
					<p:button funcId="0812020101" value="新增" type="add"/>
					
				</c:if>
				<c:if test="${orgType eq '02' }">
				<p:button funcId="08120202" value="查找"/>
					<p:button funcId="0812020201" value="新增" type="add"/>
				</c:if>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList" id="tableId">
		<tr>
			<th width="10%">年份</th>
			<th width="20%">机构</th>
			<th width="10%">指标类型</th>
			<th width="20%">监控指标代码</th>
			<th width="20%">监控指标名称</th>
			<th width="20%">操作</th>
		</tr>
		<c:forEach items="${list}" var="mont">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${mont.dataYear }</td>
				<td>${mont.org21Name}
				</td>
				<td>${mont.montTypeName}
				</td>
				<td>${mont.montCode}
				</td>
				<td>${mont.montName}</td> 
				<td>
					<div class="update"><a href="javascript:void(0)" onclick="edit('${mont.montCode }','${mont.montType}','${mont.org21Code}');" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
					<a onclick="del('${mont.org21Code}','${mont.montCode }','${mont.montType}');" ><img class="delete imageBtn" border="0" alt="解除关联" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/></a>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty list}">
	    <tr>
		<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	
	</table>
</form>
<p:page/>
</body>
</html>