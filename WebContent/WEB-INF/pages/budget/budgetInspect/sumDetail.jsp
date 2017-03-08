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

<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>明细详情</title>
</head>
<body>
<form action="" method="post" id="BLForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="4">
				下达明细
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">预算明细编码</td>
		    <td class="tdRight" width="25%">
		    	${bm.sdId}
		    </td>
		    <td class="tdLeft" width="25%">预算汇总编码</td>
			<td class="tdRight" width="25%">
				${bm.bgtId}	
			</td>
			
		</tr>
		<tr>
			<td class="tdLeft" width="25%">预算操作类型</td>
			<td class="tdRight" width="25%">
				${bm.bgtType}
			</td>
			<td class="tdLeft" width="25%">预算值</td>
			<td class="tdRight" width="25%">
				${bm.bgtValue}
			</td>
		    
		    
		</tr>
		
		<tr>
			<td class="tdLeft" width="25%">操作人</td>
			<td class="tdRight" width="25%">
				${bm.instOper}
			</td>
		    <td class="tdLeft" width="25%">操作时间</td>
		    <td class="tdRight" width="25%">
		    	${bm.instDate}  ${bm.instTime}
		    </td>
		    		    
		</tr>
	</table>
	<br>
</form>
<p:page/>
<br>
	<div style="text-align:center;" >
		<input type="button" value="关闭" onclick="art.dialog.close()" />
	</div>
</body>
</html>