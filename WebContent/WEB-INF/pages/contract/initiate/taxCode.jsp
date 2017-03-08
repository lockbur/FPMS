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
<title>参考</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${taxCode}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('taxCodeObj','');
	
}

//设置页面关闭时返回值
function getReturnValue(){
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
			  App.notyError("请选择税码！");
			    return false;
		  }
	   		var node = nodeTR.children;
	   		var taxCodeObj = {};
	   		taxCodeObj.taxCode=node[0].children[0].value;
	   		taxCodeObj.taxRate=node[2].innerText;
	   		taxCodeObj.deductFlag=node[3].children[0].value;
	   	    art.dialog.data('taxCodeObj',taxCodeObj);
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
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="10%">选择</th>
			<th width="20%">税码</th>
			<th width="20%">税率</th>
			<th  >是否可以抵扣</th>
		</tr>
		<c:forEach items="${taxCodeList}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="checkCus" value="${p.taxCode}"/>
				</td>
				<td>${p.taxCode}</td>
				<td>${p.taxRate}</td>
				<td ><input type="hidden" name="deductFlag" value="${p.deductFlag }"/><c:if test="${p.deductFlag eq 'Y' }">可抵扣</c:if><c:if test="${p.deductFlag eq 'N' }">不可抵扣</c:if></td> 
			</tr>
		</c:forEach>
		<c:if test="${empty taxCodeList}">
			<tr>
				<td colspan="4" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<p:page/>
	<br/>
	<br/>
	<br/>
	<div align="center">
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="关闭"  onclick="art.dialog.close();" />
	</div>

</body>
</html>