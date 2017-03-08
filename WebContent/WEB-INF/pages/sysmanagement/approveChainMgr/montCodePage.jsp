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
<title>监控指标选择</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${montCode}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('data','');
}

//设置页面关闭时返回值
function getReturnValue(){
	var nodeTR = null; 
	var montCode = document.getElementsByName("montCode");
	if(montCode != null)
	{
		if(montCode.length == null)
		{	
	 		if(montCode.checked)	
	 		 	nodeTR = montCode.parentNode.parentNode;	   
		 }
		 else
		 {
			  for(var i = 0 ; i < montCode.length; i++)
			  {
				   if(montCode[i].checked)
					   
				   {			
					   nodeTR = montCode[i].parentNode.parentNode;
				   }
			  }
			 
		  }
	  
		  if(nodeTR == null)
		  {
			  App.notyError("请选择监控指标！");
			    return false;
		  }
		   	var node = nodeTR.children;
		   	var montCode = node[0].children[0].value;
			var montName = node[2].innerText;
			var data = {};
			data.montCode = montCode;
			data.montName = montName;
			art.dialog.data('data',data);
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
<form action="" method="post"  id="montForm">		
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th>选择</th>
			<th>监控指标代码</th>
			<th>监控指标名称</th>
		</tr>
		<c:forEach items="${ml}" var="ml" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="montCode"  value="${ml.montCode}"/>
				</td>
				<td>
					${ml.montCode}
				</td>
				<td>
				    ${ml.montName}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty ml}">
			<tr>
				<td colspan="3" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<br>
</form>
<p:page/>
<br><br><br>
	<div>
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="关闭"  onclick="art.dialog.close();" />
	</div>
</body>
</html>