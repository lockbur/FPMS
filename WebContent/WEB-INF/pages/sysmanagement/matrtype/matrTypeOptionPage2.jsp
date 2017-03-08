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
<title>物料类型选择</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	$("#montType").combobox();
	$("#tList").find("input[type='radio']").each(function(){
		if($(this).val() == '${matrCode}'){
			$(this).prop('checked','true');
			return;
		}
	});
    art.dialog.data('matrObj','');
}

//设置页面关闭时返回值
function getReturnValue(){
	if($("input:checked").length==0){
		App.notyError("请选择物料。");
		return ;
	}
    var tr = $("input:checked").parent().parent();
    var data = getDataFromTr(tr);
    art.dialog.data('matrObj',data);
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



//查询列表
function listMatrTypeOption()
{
 	var form=$("#searchForm")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/matrtype/matrTypeOption.do?<%=WebConsts.FUNC_ID_KEY%>=010804";
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
				物料查询 
			</th>
		</tr>
		<tr>
			<td class="tdLeft">监控指标名称</td>
			<td class="tdRight">
				<input type="text" id="montName" name="montName" value="${m.montName}"  class="base-input-text" maxlength="300"/>
				<input type="hidden" id="isSpec" name="isSpec" value="${m.isSpec}" />
				<input type="hidden" id="feeDept" name="feeDept" value="${m.feeDept}" />
				<input type="hidden" id="isProvinceBuy" name="isProvinceBuy" value="${m.isProvinceBuy}" />
				<input type="hidden" id="cntType" name="cntType" value="${m.cntType}" />
				<input type="hidden" id="feeType" name="feeType" value="${m.feeType}" />
			</td>
			<td class="tdLeft">物料名称</td>
			<td class="tdRight">
				<input type="text" id="matrName" name="matrName" value="${m.matrName}"  class="base-input-text" maxlength="300"/>
			</td>	
		</tr>	
		<tr>
			<c:if test="${allMontType eq '0' }">
			    <td class="tdLeft">核算码</td>
			    <td class="tdRight" colspan="3">
					<input type="text" id="cglCode" name="cglCode" value="${m.cglCode}"  class="base-input-text" maxlength="4"/>
			    </td>
			</c:if>
			<c:if test="${allMontType eq '1' }">
				<td class="tdLeft">核算码</td>
			    <td class="tdRight"  >
					<input type="text" id="cglCode" name="cglCode" value="${m.cglCode}"  class="base-input-text" maxlength="4"/>
			    </td>
			    <td class="tdLeft">监控指标类型</td>
			    <td class="tdRight"  >
			    	<div class="ui-widget">
						<select id="montType" name="montType" class="erp_cascade_select">
							<option value="">-请选择-</option>
							<option value="11" <c:if test="${m.montType=='11'}">selected="selected"</c:if>>专项包</option>
							<option value="12" <c:if test="${m.montType=='12'}">selected="selected"</c:if>>省行统购资产</option>
							<option value="21" <c:if test="${m.montType=='21'}">selected="selected"</c:if>>非省行统购资产</option>
							<option value="22" <c:if test="${m.montType=='22'}">selected="selected"</c:if>>非专项包费用</option>
						</select>
					</div>
			    </td>
			</c:if>
		</tr>
		<tr>
			<td colspan="4">
				<input type="button" value="查找" onclick="listMatrTypeOption();"/>
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<br/>
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="10%">选择</th>
			<th width="10%">监控指标类型</th>
			<th width="10%">监控指标名称</th>
			<th width="10%">物料编号</th>
			<th width="10%">物料名称</th>
			<th width="10%">核算码</th>
		</tr>
		<c:forEach items="${mOptions}" var="mOptions" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="matrCode" value="${mOptions.matrCode}"/>
				</td>
				<td>
					 ${mOptions.montTypeName }
				</td>
				<td>
					${mOptions.montName}<input type="hidden" name="montCode" value="${mOptions.montCode}"/><input type="hidden" name="montName" value="${mOptions.montName}"/>
				</td>
				<td>
					${mOptions.matrCode}<input type="hidden" name="cglCode" value="${mOptions.cglCode}"/>
				</td>
				<td>
					${mOptions.matrName}<input type="hidden" name="matrName" value="${mOptions.matrName}"/>
				</td>
				<td>
				   ${mOptions.cglCode}<input type="hidden" name="cglCode" value="${mOptions.cglCode}"/>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty mOptions}">
			<tr>
				<td colspan="5" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<p:page/>
	<br/>
	<br/>
	<br/>
	<div >
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="关闭"  onclick="art.dialog.close();" />
	</div>
</body>
</html>