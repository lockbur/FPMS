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
<title>待审批参数列表</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
 	$("#categoryId").combobox();
}
function pass(paramVarName){
	$("#passDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		buttons: {
			"通过": function() {
				var form = $("#parameterForm");
				$("#paramVarName").val(paramVarName);
				form.attr('action', '<%=request.getContextPath()%>/sysmanagement/parameter/pass.do?<%=WebConsts.FUNC_ID_KEY%>=01050201');
				App.submit(form);
			},
			"退回":function(){
				var form = $("#parameterForm");
				$("#paramVarName").val(paramVarName);
			 	form.attr('action', '<%=request.getContextPath()%>/sysmanagement/parameter/refuse.do?<%=WebConsts.FUNC_ID_KEY%>=01050202');
				App.submit(form);				
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#passDiv").dialog( "option", "title", "参数审批" ).dialog( "open" );	
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
//是否显示密码
function showPwd(obj){
	if ($(obj).attr("title")=="查看密码") {
		$(obj).attr("title","隐藏密码");
	} else {
		$(obj).attr("title","查看密码");
	}
	$(obj).parent().find("#isHidden").toggle();
	$(obj).parent().find("#isShow").toggle();
} 
</script>
</head>

<body>
<p:authFunc funcArray="010502,01050201,01050202"/>
<form action="" method="post" id="parameterForm">
    <input  type="hidden"  name="paramVarName"  id="paramVarName"  />
	<table>
	
		<tr class="collspan-control">
			<th colspan="4">
				参数审批查询
			</th>
		</tr>
		<tr>
	       <td class="tdLeft">参数类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="categoryId" name="categoryId">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="SYS_PARAMETER_CATEGORY" selectColumn="CATEGORY_ID,CATEGORY_NAME" 
					 		valueColumn="CATEGORY_ID" textColumn="CATEGORY_NAME" orderColumn="CATEGORY_ID"    conditionStr="is_deleted = '0'"
					 		orderType="ASC" selectedValue="${parameter.categoryId}"/> 
					</select>
				</div>
			</td>
			<td class="tdLeft">参数名称</td>
			<td class="tdRight">
				<input type="text" id="paramDispName" name="paramDispName" value="${parameter.paramDispName}" class="base-input-text"/>
			</td>		
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="010502" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
</form>

	<br/>
	<table class="tableList">
	<tr>
	    <th>参数类型</th>
		<th>参数名称</th>
		<th>修改前参数值</th>
		<th>修改后参数值</th>
		<th>修改人</th>
		<th>修改时间</th> 
		<th>审批</th>
	</tr>
	<c:forEach items="${list}" var="list">
		<tr onmouseover="setTrBgClass(this, 'trOnOver2');"  onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
			<td>${list.categoryName}</td>
			<td>${list.paramDispName}</td>
			<td>
				<c:if test="${list.isPwdType == 0}"><c:out value="${list.paramValue}" />  </c:if>
				 <c:if test="${list.isPwdType == 1}">
					 <span><span id="isHidden"><c:out value="******" /></span> <span id="isShow" style="display: none;"><c:out value="${list.paramValue}" /></span>
					 	<img alt="查看密码"  title="查看密码" style="cursor: pointer;" src="<%=request.getContextPath()%>/common/images/show.png" onclick="showPwd(this);">
					 </span>
				 </c:if>
			</td>
			<td>${list.paramUpdateValue}</td>
			<td>${list.applyUserId}</td>
			<td>${list.applyTime}</td>
			<td align="center">
					<div class="update"><a href="javascript:void(0);" onclick="pass('${list.paramVarName}')" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
			</td>
		</tr>
	</c:forEach>
		<c:if test="${empty list}">
	    <tr>
		<td colspan="7" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	
	</table>
<div id="passDiv" style="display:none">
	<form action="" method="post" id="passForm">
	<p>点击通过，参数修改生效并更新参数表；点击退回，参数修改不生效；取消关闭对话框！</p>
	</form>
</div>	
<p:page/>
</body>
</html>