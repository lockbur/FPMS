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
}

//设置页面关闭时返回值
function getReturnValue(){
	    var dutyCode = $("input:checked").val();
	    var dutyName = $("#"+dutyCode+"Tr").html();
	    var data={};
	    data.dutyCode=dutyCode;
	    data.dutyName=dutyName;
	    art.dialog.data('data',data);
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
<div>
 <DIV>
<form action="" method="post"  id="addSeqForm">		
   
    <table class="tableList" id="tList">
		<tr id="findTr">
			<th width="20%">选择</th>
			<th width="30%">撤并${desc }代码</th>
			<th width="50%">撤并${desc }名称</th>
		</tr>
		<c:forEach items="${list}" var="p" varStatus="status">
			<tr ondblclick="return getReturnValue();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td>
					<input type="radio" name="dutyCode"  value="${p.dutyCode}"/>
				</td>
				<td>
					${p.dutyCode}
				</td>
				<td id="${p.dutyCode }Tr">
					${p.dutyName}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty list}">
			<tr>
				<td colspan="3" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<br>
	<p:page />
	
</form>
</DIV>
<br>
	<table style="cellpadding="0px" cellspacing="0px">
		<tr><td style="border:0px;">
				<input type="button"  value="选择"  onclick="getReturnValue();" />
				<input type="button"  value="返回"  onclick="art.dialog.close()" /></td></tr>
	</table>
	</div>
</body>
</html>