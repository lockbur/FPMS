<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.tool.ConstantMgr"%>
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
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目类型选择</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${projType}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('projTypeObject','');
}

//设置页面关闭时返回值
function getReturnValue(){
//     var tr = $("input:checked").parent().parent();
//     var returnValue = getDataFromTr(tr);
// 	//window.returnValue = returnValue;
// 	if(window.opener){
// 		window.opener.returnValue = returnValue;
// 	}else{
// 		window.returnValue = returnValue;
// 	}
// 	window.close();
// 	return;
	var nodeTR = null; 
	var projType = document.getElementsByName("projType");
	if(projType != null)
	{
		if(projType.length == null)
		{	
	 		if(projType.checked)	
	 		 	nodeTR = projType.parentNode.parentNode;	   
		 }
		 else
		 {
			  for(var i = 0 ; i < projType.length; i++)
			  {
				   if(projType[i].checked)
					   
				   {			
					   nodeTR = projType[i].parentNode.parentNode;
				   }
			  }
			 
		  }
	  
		  if(nodeTR == null)
		  {
			  App.notyError("请选择项目类型！");
			    return false;
		  }
		   	var node = nodeTR.children;
		   	var projType = node[0].children[0].value;;
		   	var projTypeName = node[0].children[1].value;;
			var data={};
			data.projType=projType;
			data.projTypeName=projTypeName;
			art.dialog.data('projTypeObject',data);
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
<script type="text/javascript">
function getDataFromTable()
{
	var dataList = new Array();
	var index = 0;
	$("#findTr").parent().find("input[name='checkbox']:checked").each(function(){
		dataList[index++] = getDataFromTr( $(this).parent().parent() );
	});
	return dataList;
}
function getDataFromTr(obj)
{
	var data = {};
	$(obj).find("input").each(function(){
		data[ $(this).attr("name") ] = $(this).val();
	});
	return data;
}
</script>
</head>

<body>
<form action="" method="post"  id="addSeqForm">		
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="50%">选择</th>
			<th width="50%">项目类型</th>
		</tr>
		<c:forEach items="${pTypes}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite" style="cursor:pointer;">
				<td>
					<input type="radio" name="projType"  value="${p.projType}"/>
					<input type="hidden" name="projTypeName"  value="${p.projTypeName}"/>
				</td>
				<td>
					${p.projTypeName}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty pTypes}">
			<tr>
				<td colspan="2" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>

	<p:page/>
	<br/>
	<br/>
	<br/>
	<div align="center">
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="返回"  onclick="art.dialog.close()" />
	</div>


</body>
</html>