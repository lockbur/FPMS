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
<br/>
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="10%">选择</th>
			<th width="16%">项目名称</th>
			<!--<th width="16%">预算总金额</th> -->
			<!--<th width="16%">冻结金额</th> -->
		</tr>
		<c:forEach items="${pList}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="projId" value="${p.projId}"/>
				</td>
				<td>
					${p.projName}<input type="hidden" name="projName" value="${p.projName}"/>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty pList}">
			<tr>
				<td colspan="2" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
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