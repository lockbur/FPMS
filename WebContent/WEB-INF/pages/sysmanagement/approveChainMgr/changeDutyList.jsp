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
<title>撤并的责任中心选择</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${dutyCode}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('data','');
}

//设置页面关闭时返回值
function getReturnValue(){
		var nodeTR = null; 
		var dutyCode = document.getElementsByName("dutyCode");
		if(dutyCode != null)
		{
			if(dutyCode.length == null)
			{	
		 		if(dutyCode.checked)	
		 		 	nodeTR = dutyCode.parentNode.parentNode;	   
			 }
			 else
			 {
				  for(var i = 0 ; i < dutyCode.length; i++)
				  {
					   if(dutyCode[i].checked)
						   
					   {			
						   nodeTR = dutyCode[i].parentNode.parentNode;
					   }
				  }
				 
			  }
		  
			  if(nodeTR == null)
			  {
				  App.notyError("请选择责任中心！");
				    return false;
			  }
			   	var node = nodeTR.children;
			   	var dutyCode = node[0].children[0].value;
			    var data = {};
				data.dutyCode = dutyCode;
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
</head>

<body>
<p:authFunc funcArray="0812010308"/>
<div>
 <DIV>
<form action="" method="post"  id="addSeqForm">	
<input type="hidden" value="${selectInfo.aprvType }" name="aprvType">	
<input type="hidden" value="${selectInfo.dataYear }" name="dataYear">	
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="20%">选择</th>
			<th width="30%">责任中心代码</th>
		</tr>
		<c:forEach items="${list}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="dutyCode"  value="${p.dutyCode}"/>
				</td>
				<td>
					${p.dutyCode}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty list}">
			<tr>
				<td colspan="2" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<br>

	
</form>
</DIV>
<p:page />
<br>
	<table style="cellpadding:0px; cellspacing:0px">
		<tr><td style="border:0px;">
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="返回"  onclick="art.dialog.close();" /></td></tr>
	</table>
	</div>
</body>
</html>