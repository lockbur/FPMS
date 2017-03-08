<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料类别列表</title>
<script type="text/javascript">

//页面初始化
function pageInit() {   
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#platformTypeId").combobox();
	$("#softTypeId").combobox();
	$("#softNameId").combobox();
	
	//级联回显
	 if('${bl.softTypeId}' != null && '${bl.softTypeId}' != "")
	{
		 $("#softTypeId").change();
		 $("#softNameIdDiv").find('input[id!="softName"]').val('${bl.softName}');  
	} 
	
		
}

//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#matrTypeDiv").find("label").eq('').click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
};

//查询列表
function listMT()
{
 	var form=$("#mtFormSearch")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/matrtype/list.do?<%=WebConsts.FUNC_ID_KEY%>=010806";
	App.submit(form);
}

//详情
function detail(matrCode,jspNmae){
	var form=$("#mtForm")[0];
	form.action="<%=request.getContextPath()%>/sysmanagement/matrtype/view.do?<%=WebConsts.FUNC_ID_KEY%>=01080601&matrCode="+matrCode+"&jspName="+jspNmae;
	App.submit(form);
}


<%-- 
//物料类型添加--跳出页面
function matrTypeOptionPage()
{
	App.submitShowProgress();
	var url="<%=request.getContextPath()%>/sysmanagement/matrtype/matrTypeOption.do?<%=WebConsts.FUNC_ID_KEY %>=010804";
	//!!!参数在此加入
	url = url +"&isSpec=1&feeDept=02273&isProvinceBuy=0&cntType=1";
	var returnValue = window.showModalDialog(url, null, "dialogHeight=600px;dialogWidth=700px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		alert(returnValue.matrCode + "*****"+returnValue.matrName+"*****"+returnValue.montCode+"*****"+returnValue.montName);
	}
} 
 --%>
</script> 
</head>

<body>
<p:authFunc funcArray="0108,010801,010806,010805"/>
<form action="" method="post" id="mtFormSearch">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				物料类别查询<!--  <input type="button" value="test" onclick="matrTypeOptionPage();"/> -->
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">物料类型</td>
			<!--  <td class="tdRight" width="25%">
				<input type="text" id="matrType" name="matrType" value="${mt.matrType}" class="base-input-text" maxlength="11" />				
			</td>-->
			<td class="tdRight">
				<div class="base-input-radio" id="matrTypeDiv">
				 	<label for="matrType" onclick="App.radioCheck(this,'matrTypeDiv')"  <c:if test="${'0'==mt.matrType}">class="check-label"</c:if>>全部</label><input type="radio" id="matrType" name="matrType" value="0" <c:if test="${'0'==mt.matrType or mt.matrType==null}">checked="checked"</c:if>>
					<label for="matrType1" onclick="App.radioCheck(this,'matrTypeDiv')" <c:if test="${'1'==mt.matrType}">class="check-label"</c:if>>资产</label><input type="radio" id="matrType1" name="matrType" value="1" <c:if test="${'1'==mt.matrType}">checked="checked"</c:if>>
					<label for="matrType2" onclick="App.radioCheck(this,'matrTypeDiv')" <c:if test="${'3'==mt.matrType}">class="check-label"</c:if>>费用</label><input type="radio" id="matrType2" name="matrType" value="3" <c:if test="${'3'==mt.matrType}">checked="checked"</c:if>>
				</div>
			</td>
			<td class="tdLeft" width="25%">物料编码</td>
			<td class="tdRight" width="25%">
				<input type="text" id="matrCode" name="matrCode" value="${mt.matrCode}"  class="base-input-text" maxlength="300"/>	
			</td>		
		</tr>
		<tr>
			<td class="tdLeft" width="25%">物料名称</td>
			<td class="tdRight" width="25%">
				<input type="text" id="matrName" name="matrName" value="${mt.matrName}"  class="base-input-text" />
			</td>	
			<td class="tdLeft" width="25%">物料单位</td>
			<td class="tdRight" width="25%">
				<input type="text" id="matrUnit" name="matrUnit" value="${mt.matrUnit}"  class="base-input-text" maxlength="10"/>
			</td>	
		</tr>	
		<tr>
			<td class="tdLeft" width="25%">费用核算码</td>
			<td class="tdRight" width="25%" colspan="3">
				<input style="width: 40px;" type="text" id="cglCode" name="cglCode" class="base-input-text" value="${mt.cglCode}" maxlength="4"/>
			</td>	
		</tr>	
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="查找" onclick="listMT();"/>
				<%-- <p:button funcId="010801" value="导入"/> --%>
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<form action="" method="post" id="mtForm">
	<br/>	
	<table id="listTab" class="tableList">		
		<tr>		
			<th width="15%">物料类型</th>
			<th width="15%">物料编码</th>
			<th width="40%">物料名称</th>
			<th width="12%">物料单位</th>
			<th width="13%">费用核算码</th>
			<th width="5%">详情</th>	

		</tr>
		<c:forEach items="${mtList}" var="mt" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				<td>${mt.matrType}</td>								
				<td>${mt.matrCode}</td>	
				<td>${mt.matrName}</td>					
				<td>${mt.matrUnit}</td>	
				<td>${mt.cglCode}</td>	
				<td>
  				    <div class="detail">
					    <a href="#" onclick="detail('${mt.matrCode}','view');" title="<%=WebUtils.getMessage("button.view")%>"></a>
					</div>
				</td>	
			</tr>
		</c:forEach>
		<c:if test="${empty mtList}">
	    <tr>
		<td colspan="6" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>
	</table>

</form>
<p:page/>
</body>
</html>