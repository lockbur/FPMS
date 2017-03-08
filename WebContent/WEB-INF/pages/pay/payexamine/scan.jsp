<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加扫描批次</title>
<script>
function doValidate(){
	//提交前调用
    if(!App.valid("#tempForm")){
	 return;
	}
     if(!checkNum($("#mainCnt").val()))
	{
		App.notyError("主件数量格式有误！只能是正整数。");
		return false;	
	}
    if(!checkNum($("#attachCnt").val()))
	{
		App.notyError("总页数数量格式有误！只能是正整数。");
		return false;	
	}
    if(parseFloat(($("#mainCnt").val()))>parseFloat(($("#attachCnt").val()))){
    	App.notyError("主件数必须小于等于总页数。");
    	return false;
    }
	return true;
}
//校验整数
function checkNum(str)
{
	var reg = /^[0-9]*$/g;	
	if(!reg.test(str))
	{
		return false;
	}
	return true;
}

function resetAll(){
	$(":text").val("");
}
</script>
</head>
<body>
<p:authFunc funcArray="0303090101"/>
<form  method="post" id="tempForm">
	<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				付款扫描
			</th>
		</tr>
		<tr>
			<td class="prompt tdLeft" width="20%">主件数</td>
			<td class="tdRight" width="30%">
				<input type="text" id="mainCnt" name="mainCnt" maxlength="5" class="base-input-text" valid errorMsg="请输入主件数。" value="0"/>
			</td>
			<td class="prompt tdLeft" width="20%">总页数</td>
			<td class="tdRight" width="30%">
           		<input type="text" id="attachCnt" name="attachCnt" maxlength="5" class="base-input-text" valid errorMsg="请输入总页数。" value="0"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="0303090101" value="提交"/>
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>