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
<title>参数列表</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
 	$("#categoryId").combobox();
}

function modify(paramVarName) 
{
	var form = $('#parameterForm')[0];
	$('#paramVarName').val(paramVarName);
	form.action = '<%=request.getContextPath()%>/sysmanagement/parameter/preEdit.do?<%=WebConsts.FUNC_ID_KEY%>=01050101';
	App.submit(form);
}

function applyHisList(paramVarName)
{
	var form = $('#parameterForm')[0];
	$('#paramVarName').val(paramVarName);
	form.action = '<%=request.getContextPath()%>/sysmanagement/parameter/applyHisList.do?<%=WebConsts.FUNC_ID_KEY%>=01050103';
	App.submit(form);
	
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


function hideSub(obj,index)
{
	var hideFlag=$("#inputClass"+index).val();
	if(hideFlag=='0')
	{
		$(obj).find("img").attr({src :"<%=request.getContextPath()%>/common/images/minus.gif"});
		$("TR[name='tr"+index+"']").show();
		$("#inputClass"+index).val('1');
	}
	else if(hideFlag=='1')
	{
		$(obj).find("img").attr({src :"<%=request.getContextPath()%>/common/images/plus.gif"});
		$("TR[name='tr"+index+"']").hide();
		$("#inputClass"+index).val('0');
	}
	
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
<p:authFunc funcArray="01050102"/>
<form action="<%=request.getContextPath()%>/sysmanagement/parameter/list.do" method="post" id="parameterForm">
		<input type="hidden" id="paramVarName" name="paramVarName"/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				参数查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">参数类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="categoryId" name="categoryId">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="SYS_PARAMETER_CATEGORY" selectColumn="CATEGORY_ID,CATEGORY_NAME" 
					 		valueColumn="CATEGORY_ID" textColumn="CATEGORY_NAME" orderColumn="CATEGORY_ID"  conditionStr="is_deleted = '0'"
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
			<td colspan="4" class="tdBottom tdWhite">
				<p:button funcId="01050102" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
	   <c:forEach items="${paramsClassList}" var="paramsClass"  varStatus="status">
	   <tr  onclick="hideSub(this,'${status.index}');"  >
			<th   colspan="3"  align="left" style="text-align: left;padding-left: 0px;">
	        <input type="hidden" id="inputClass${status.index}" name="showFlag"  value="0"/>
			<img  src="<%=request.getContextPath()%>/common/images/plus.gif" style="position: relative;top:3px">${paramsClass.categoryName}
			(&nbsp;<span class="red">${paramsClass.numb}</span>&nbsp;)
			</th>
	   </tr>
	   <c:forEach items="${paramsClass.params}"  var="paramBean">
		   <tr  name="tr${status.index}"  onmouseover="setTrBgClass(this, 'trOnOver2');"   onmouseout="setTrBgClass(this, 'trWhite');"   class="trWhite" style="display:none">
				<td  width="30%" >  <c:out value="${paramBean.paramDispName}" /></td>
				<td width="60%">  
				 <c:if test="${paramBean.isPwdType == 0}"><c:out value="${paramBean.paramValue}" />  </c:if>
				 <c:if test="${paramBean.isPwdType == 1}">
					 <span><span id="isHidden"><c:out value="******" /></span> <span id="isShow" style="display: none;"><c:out value="${paramBean.paramValue}" /></span>
					 	<img title="查看密码" style="cursor: pointer;" src="<%=request.getContextPath()%>/common/images/show.png" onclick="showPwd(this);">
					 </span>
				 </c:if>
					<c:if test="${!empty paramBean.paramUpdateValue}">
					>>  <span  style="color: red;">${paramBean.paramUpdateValue}</span>
					</c:if>
				</td>
				<td width="10%" align="center"  style="text-align: center;">
						<div class="update"><a href="javascript:void(0);" onclick="modify('${paramBean.paramVarName}');return false;" title="<%=WebUtils.getMessage("button.update")%>"></a></div>
						<div class="detail"><a href="javascript:void(0);"  title="修改历史" onclick="applyHisList('${paramBean.paramVarName}');return false;"></a></div>
				</td>
			</tr>
	   </c:forEach>
	   </c:forEach>
		<c:if test="${empty paramsClassList}">
	    <tr>
		<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	 
	</table>
</form>
</body>
</html>