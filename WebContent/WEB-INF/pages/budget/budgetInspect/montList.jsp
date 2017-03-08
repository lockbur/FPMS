<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="json/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>监控指标列表</title>
<script type="text/javascript">
function getProvider(){	
	var nodeTR = null; 
	var checkCUS = document.getElementsByName("checkCus");
	if(checkCUS != null)
	{
		if(checkCUS.length == null)
		{	
	 		if(checkCUS.checked)	
	 		 	nodeTR = checkCUS.parentNode.parentNode;	   
		 }
		 else
		 {
			  for(var i = 0 ; i < checkCUS.length; i++)
			  {
				   if(checkCUS[i].checked)
					   
				   {			
					   nodeTR = checkCUS[i].parentNode.parentNode;
				   }
			  }
			 
		  }
	  
		  if(nodeTR == null)
		  {
			  App.notyError("请选择监控指标！");
			    return false;
		  }
		   	var node = nodeTR.children;
		   	var montCode = node[0].children[0].value;;
			var montName = node[2].innerText;
			var object={};
			object.montCode=montCode;
			object.montName=montName;
			art.dialog.data('object',object);
		}
		art.dialog.close();	   		 
}

function pageInit(){
	App.jqueryAutocomplete();
	$("#providerType").combobox();
	art.dialog.data('object','');
}
function radioInit(_obj, _name, _val){
	if(!_val)
	{
		_val = $(_obj).find("input[name^='"+ _name +"']:eq(0)").val();
	}
	
	$(_obj).find("input[name^='"+ _name +"']").attr("checked", false);
	$(_obj).find("input[name^='"+ _name +"']").each(function(){
		if( $(this).val() == _val ){
			$(this).attr("checked", true);
			$(this).parent().find("label[for='" + $(this).attr("id") +"']").click();
		}
	});
}
$(function(){
	$('table tr').click(function(){
		$("table tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
});
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="02070111"/>
<form action="" id="tempForm" method="post">
<input type="hidden" value="${searchInfo.orgType }" name="orgType"/>
<input type="hidden" value="${searchInfo.montType }" name="montType"/>
	<table >
		<tr>
			<td class="tdLeft" width="20%">监控指标代码</td>
			<td  class="tdRight" width="30%"><input type="text" class="base-input-text" name="montCode" value="${searchInfo.montCode }"></td>
			<td class="tdLeft" width="20%">监控指标名称</td>
			<td  class="tdRight" width="30%"><input type="text" class="base-input-text" name="montName" value="${searchInfo.montName }" ></td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="tdWhite"><p:button funcId="02070111" value="查找"/></td>
		</tr>
	</table>
	<br>
	<table class="tableList" id="tableListRadio">
			<tr>    
					<th width="10%">选择</th>
					<th width="25%">监控指标代码</th>
					<th width="20%">监控指标名称</th>
			</tr>
		<c:if test="${empty montList}">
			<tr>
				<td colspan="3" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		 <c:forEach items="${montList}" var="list" varStatus="status">
			<tr    ondblclick="return getProvider();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor: pointer" class="trOther">
				<td>
					<input  type="radio" name="checkCus"  value="${list.montCode}"/>
				</td>
				<td><c:out value="${list.montCode}"/></td>
				<td><c:out value="${list.montName}"/></td>
			</tr>
		</c:forEach>
	</table>
</form>
<p:page/>
<br><br><br>
	<div style="text-align:center;" >
		<input type="button" name="button" value="确定" class="button" onclick="return getProvider();" />
		<input type="button" value="关闭" onclick="art.dialog.close()" />
	</div>
</body>
</html>