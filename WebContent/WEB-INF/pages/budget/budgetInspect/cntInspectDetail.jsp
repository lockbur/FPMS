<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同预算详细</title>
<base target="_self">
</head>
<body>
<p:authFunc funcArray="020702,02070201"/>
<form action="" method="post" id="BLForm">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">合同预算详情</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">合同号或付款单号</td>
			<td class="tdRight" width="75%" colspan="3">${bm.payId }</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">预算类型</td>
		    <td class="tdRight" width="25%">${bm.bgtType }</td>
		    <td class="tdLeft" width="25%">预算值</td>
		    <td class="tdRight" width="25%">${bm.bgtValue }</td>	
		     		
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">操作时间</td>
		    <td class="tdRight" width="25%">${bm.instDate }</td>
		    <td class="tdLeft" width="25%"></td>
		    <td class="tdRight" width="25%"></td>	
		</tr>
		<tr>
			<td class="tdLeft" width="25%">概要说明</td>
		    <td class="tdRight" width="75%" colspan="3">
		        <textarea class="base-textArea"  name="memo" id="memo" disabled="disabled">${bm.memo }</textarea>
		    </td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
					<input type="button"  value="关闭"  onclick="art.dialog.close();" />
			</td>
		</tr>
		
	</table>
	<br />
</form>
</body>
</html>