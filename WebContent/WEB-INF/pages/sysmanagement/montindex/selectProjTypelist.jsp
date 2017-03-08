<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择专项包类型列表</title>
<script type="text/javascript">
function pageInit() {
	
App.jqueryAutocomplete();
	
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${projType}'){
			$(this).prop('checked','true');
			return;
		}
	});
}
function resetAll() {
	$(":text").val("");
}

//设置页面关闭时返回值
function getReturnValue(){
	var nodeTR = null; 
	var paramName = document.getElementsByName("paramName");
	if(paramName != null)
	{
		if(paramName.length == null)
		{	
	 		if(paramName.checked)	
	 		 	nodeTR = paramName.parentNode.parentNode;	   
		 }
		 else
		 {
			  for(var i = 0 ; i < paramName.length; i++)
			  {
				   if(paramName[i].checked)
					   
				   {			
					   nodeTR = paramName[i].parentNode.parentNode;
				   }
			  }
			 
		  }
	  
		  if(nodeTR == null)
		  {
			  App.notyError("请选择专项包类型！");
			    return false;
		  }
		   	var node = nodeTR.children;
		   	var paramName = node[0].children[0].value;
			var data = {};
			data.paramName = paramName;
			art.dialog.data('proTypeObject',data);
		}			
			art.dialog.close();	 
}

$(function(){
	$('table tr').click(function(){
		$("table tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
});



</script>
</head>
<body>
<p:authFunc funcArray="0812020110"/>
<form action="" method="post" id="projTypeForm">
<!-- 	<table > -->
<!-- 		<tr class="collspan-control" > -->
<!-- 			<th colspan="4"> -->
<!-- 				项目类型查询 -->
<!-- 			</th> -->
<!-- 		</tr> -->
<!-- 		<tr > -->
<!-- 			<td class="tdLeft">参数名称</td> -->
<!-- 			<td class="tdRight"> -->
<!-- 				<input type="text" id="paramName" name="paramName" value="" class="base-input-text"  maxlength="50"/> -->
<!-- 			</td> -->
<!-- 			<td class="tdLeft">参数值</td> -->
<!-- 			<td class="tdRight"> -->
<!-- 				<input type="text" id="paramValue" name="paramValue" value="" class="base-input-text"  maxlength="20"/> -->
<!-- 			</td> -->
<!-- 		</tr> -->
		
<!-- 		<tr> -->
<!-- 			<td colspan="4"> -->
<%-- 				<p:button funcId="0812020110" value="查找"/> --%>
<!-- 				<input type="button" value="重置" onclick="resetAll();"> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
	<br>
	<table class="tableList" id="tList">
		<tr id="findTr">
			<th width="40%">选择</th>
			<th width="60%">参数值</th>
		</tr>
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="bean">
				<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td><input type="radio" name="paramName" value="${bean.paramName}" maxlength="20"/></td>
				<td>${bean.paramName}
				</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list}">
			<tr><td style="text-align: center;" colspan="3"><span class="red">没有找到相关信息</span></td></tr>
		</c:if>
	</table>
</form>
<p:page/>

<br><br><br>
	<div>
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="返回"  onclick="art.dialog.close();	" />
	</div>
</body>
</html>