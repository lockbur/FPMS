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
		if($(this).val() == '${projId}'){
			$(this).prop('checked','true');
			return;
		}
	});
	art.dialog.data('projectObj','');
	
}

//设置页面关闭时返回值
function getReturnValue(){
		if($("input:checked").length==0){
			App.notyError("请选择项目。");
			return ;
		}
	    var tr = $("input:checked").parent().parent();
	   	var data = getDataFromTr(tr);
		//window.returnValue = returnValue;
		art.dialog.data('projectObj',data);
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

//项目类型--跳出页面
function projTypePage()
{
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projTypePage.do?<%=WebConsts.FUNC_ID_KEY %>=02100102";
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "75%",
				lock: true,
			    fixed: true,
			    title:"项目类型选择",
			    id:"dialogCutPage1",
				close: function(){
					var proObj = art.dialog.data('projTypeObject'); 
					if(proObj){
						$("#projType").val(proObj.projType);
						$("#projTypeName").val(proObj.projTypeName);
					}
				}
			}
		 );
}
//查询列表
function listProjOption()
{
 	var form=$("#searchForm")[0];
	form.action="<%=request.getContextPath()%>/projmanagement/projectMgr/projOptionPage.do?<%=WebConsts.FUNC_ID_KEY%>=021004";
	App.submit(form);
}

//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#projType").val("");	
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
				项目查询 
				<input type="hidden" name="signDate" id="signDate" value="${proj.signDate}"/>
			</th>
		</tr>
		<tr>
			<td class="tdLeft">项目类型</td>
			<td class="tdRight">
<%-- 				<input type="text" id="projType" name="projType" value="${proj.projType}"  class="base-input-text" maxlength="200"/> --%>
<!-- 				<a href="#" onclick="projTypePage()"> -->
<%-- 				<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="查看项目类型" style="cursor:pointer;vertical-align: middle;heigth:25px;width:25px;"/> --%>
<!-- 				</a> -->
				<input id="projTypeName" name="projTypeName"  class="base-input-text" valid errorMsg="项目类型不能为空，请选择项目类型！" type="text" style="width:235px;" readonly="readonly" value="${proj.projTypeName}"   onclick="projTypePage()"/>
				<input id="projType" name="projType"    type="hidden" value="${proj.projType}"/>
			</td>	
			<td class="tdLeft">项目名称</td>
			<td class="tdRight">
				<input type="text" id="projName" name="projName" value="${proj.projName}"  class="base-input-text" maxlength="200"/>
			</td>	
		</tr>	
		<tr>
			<td colspan="4">
				<input type="button" value="查找" onclick="listProjOption();"/>
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<br/>
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="10%">选择</th>
			<th width="10%">创建年份</th>
			<!-- <th width="20%">项目编号</th> -->
			<th width="16%">项目类型</th>
			<th width="16%">项目名称</th>
			<th width="16%">项目描述</th>
			<!--<th width="16%">预算总金额</th> -->
			<!--<th width="16%">冻结金额</th> -->
		</tr>
		<c:forEach items="${pList}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="projId" value="${p.projId}"/>
				</td>
				<td>
					${p.startYear}
				</td>
				<%-- <td>
					${p.projId}
				</td> --%>
				<td>
					${p.projTypeName}
				</td> 
				<td>
					${p.projName}<input type="hidden" name="projName" value="${p.projName}"/>
				</td>
				<td>
					${p.projDesc}
				</td>
				<%-- <td>
					${p.budgetAmt}
				</td>
				<td>
					${p.freezeTotalAmt}
				</td>--%>
			</tr>
		</c:forEach>
		<c:if test="${empty pList}">
			<tr>
				<td colspan="5" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
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