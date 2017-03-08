<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="json/html; charset=UTF-8">
<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>费用核算码列表</title>
<style type="text/css"></style>
<script type="text/javascript">

function getProvider(){	
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
			App.notyError("请选择费用核算码！");
			return false;
		}
		var node = nodeTR.children;
		var proObj = {};
		proObj.cglCode=node[0].innerText;
		proObj.shortPrepaidCode=node[5].children[0].value;
		proObj.longPrepaidCode=node[6].children[0].value;
		proObj.longPrepaidCodeFee=node[7].children[0].value;
		art.dialog.data('proObj',proObj);
   	}
	art.dialog.close();
}

function pageInit(){
	App.jqueryAutocomplete();
	$("#providerTypePop").combobox();
	$("#actTypePop").combobox();
	//rowspanTableTd("tableListRadio", "1", "1");
	//rowspanTableTd("tableListRadio", "0", "1");
	//var tableCombine=new TableCombine();
	//tableCombine.rowspanTable("tableListRadio", 1, null, 0, 2, null, null);
}

function radioInit(_obj, _name, _val){
	if(!_val)
	{
		_val = $(_obj).find("input[name^='"+ _name +"']:eq(0)").val();
	}
	
	$(_obj).find("input[name^='"+ _name +"']").attr("checked", false);
	$(_obj).find("input[name^='"+ _name +"']").each(function(){
		if( $(this).val() == _val ){
			$(this).attr("checked", true);
			$(this).parent().find("label[for='" + $(this).attr("id") +"']").click();
		}
	});
}

$(function(){
	$("table tr[id='notIAPTr']").click(function(){
		$("table tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
});

function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="01080504"/>
<form action="" id="tempForm" method="post">
   	<table>
   		<tr class="collspan-control">
   			<th colspan="2">
				费用核算码列表
			</th>
		</tr>
		<tr>
			<td width="50%" class="tdLeft">费用核算码</td>
			<td width="50%" class="tdRight">
				<input type="text" id="cglCode" name="cglCode"  class="base-input-text"  value="${cglCode}" maxlength="100"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="text-align: center;" class="tdWhite">
				<p:button funcId="01080504" value="查找"/>
				<input type="button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
	<br>
	<table id="tableListRadio" class="tableList">
			<tr>    
					<th width="20%">费用核算码</th>
					<th width="20%">短期待摊核算码</th>
					<th width="20%">长期待摊核算码</th>
					<th width="30%">长期待摊固定对应的费用核算码</th>
					<th width="10%">选择</th>
			</tr>
		<c:if test="${empty mtList}">
			<tr>
				<td colspan="5" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		 <c:forEach items="${mtList}" var="list" varStatus="status">
		 		<tr ondblclick="return getProvider();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor: pointer" class="trOther" id="notIAPTr">
					<td><c:out value="${list.cglCode}"/></td>
					<td><c:out value="${list.shortPrepaidCode}"/></td>
					<td><c:out value="${list.longPrepaidCode}"/></td>
					<td><c:out value="${list.longPrepaidCodeFee}"/></td>
					<td>
						<input type="radio" name="checkCus"  value="${list.cglCode}"/>
					</td>
					<td style="display: none">
						<input type="hidden" id="shortPrepaidCode" value="${list.shortPrepaidCode}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="longPrepaidCode" value="${list.longPrepaidCode}"></input>
					</td>
					<td style="display: none">
						<input type="hidden" id="longPrepaidCodeFee" value="${list.longPrepaidCodeFee}"></input>
					</td>
				</tr>
		</c:forEach>
	</table>
	
	</form>
	<p:page/>
	<br><br><br>
			<input type="button" name="button" value="确定" class="button" onclick="return getProvider();" />
			<input type="button" value="关闭" onclick="art.dialog.close();" />
</body>
</html>