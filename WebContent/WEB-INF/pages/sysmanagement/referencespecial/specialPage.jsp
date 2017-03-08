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
<title>专项</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${projId}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('specialObj','');
	
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
			  App.notyError("请选择专项！");
			    return false;
		  }
	   		var node = nodeTR.children;
	   		var specialObj = {};
	   		specialObj.specialId=node[0].children[0].value;
	   		specialObj.specialName=node[2].innerText;
	   	    art.dialog.data('specialObj',specialObj);
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

//出库设备添加--跳出页面
function projTypePage()
{
	App.submitShowProgress();
	var pt = encodeURI($("#projType").val());
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projTypePage.do?<%=WebConsts.FUNC_ID_KEY %>=02100102&projType="+pt;
	var returnValue = window.showModalDialog(url, null, "dialogHeight=400px;dialogWidth=600px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		$("#projType").val(returnValue);
	}
}

//查询列表
function listSpecialOption()
{
 	var form=$("#searchForm")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/referencespecial/specialPage.do?<%=WebConsts.FUNC_ID_KEY%>=01120106";
	App.submit(form);
}

//重置查询条件
function initEvent(){
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
	
};
</script>
</head>

<body>
<form action="" method="post"  id="searchForm">		
	<table>
		<tr>
			<th colspan="4">
				专项查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">专项编号</td>
			<td class="tdRight">
				<input type="text" id="specialId" name="specialId" value="${spe.specialId}"  class="base-input-text" maxlength="200"/>
			</td>	
			<td class="tdLeft">专项名称</td>
			<td class="tdRight">
				<input type="text" id="specialName" name="specialName" value="${spe.specialName}"  class="base-input-text" maxlength="200"/>
			</td>	
		</tr>	
		<tr>
			<td colspan="4">
				<input type="button" value="查找" onclick="listSpecialOption();"/>
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<br/>
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="10%">选择</th>
			<th width="16%">专项编码</th>
			<th width="16%">专项名称</th>
		</tr>
		<c:forEach items="${specialList}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="checkCus" value="${p.specialId}"/>
				</td>
				<td>${p.specialId}</td>
				<td>${p.specialName}</td> 
			</tr>
		</c:forEach>
		<c:if test="${empty specialList}">
			<tr>
				<td colspan="3" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
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