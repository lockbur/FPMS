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
				余额明细
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">核算码</td>
		    <td class="tdRight" width="25%">
		    	${bean.cglCode }
		    </td>
		    <td class="tdLeft" width="25%">核算码名称</td>
			<td class="tdRight" width="25%">
				${bean.cglName }		
			</td>
			
		</tr>
		<tr>
			<td class="tdLeft" width="25%">公司间段</td>
			<td class="tdRight" width="25%">
				${bean.compId }
			</td>
			<td class="tdLeft" width="25%">公司间段名称</td>
			<td class="tdRight" width="25%">
				${bean.compName }
			</td>
		    
		    
		</tr>
		
		<tr>
			<td class="tdLeft" width="25%">贷方余额</td>
			<td class="tdRight" width="25%">
				${bean.crAmt }
			</td>
		    <td class="tdLeft" width="25%">借方余额</td>
		    <td class="tdRight" width="25%">
		    	${bean.drAmt }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">币种</td>
			<td class="tdRight" width="25%">
				${bean.currCode }
			</td>
		    <td class="tdLeft" width="25%">物料编码</td>
		    <td class="tdRight" width="25%">
		    	${bean.matrCode }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">责任中心代码</td>
			<td class="tdRight" width="25%">
				${bean.dutyCode }
			</td>
		    <td class="tdLeft" width="25%">责任中心名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.dutyName }
		    </td>
		    		    
		</tr>
		
		<tr>
			<td class="tdLeft" width="25%">所属一级行代码</td>
			<td class="tdRight" width="25%">
				${bean.org1Code }
			</td>
		    <td class="tdLeft" width="25%">所属一级行名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.org1Name }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">机构代码</td>
			<td class="tdRight" width="25%">
				${bean.orgCode }
			</td>
		    <td class="tdLeft" width="25%">机构名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.orgName }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">财务中心名称</td>
			<td class="tdRight" width="25%">
				${bean.ouName }
			</td>
		    <td class="tdLeft" width="25%">监控指标</td>
		    <td class="tdRight" width="25%">
		    	${bean.montCode }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">产品段</td>
			<td class="tdRight" width="25%">
				${bean.prodId }
			</td>
		    <td class="tdLeft" width="25%">产品段名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.prodName }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">参考段</td>
			<td class="tdRight" width="25%">
				${bean.referenceId }
			</td>
		    <td class="tdLeft" width="25%">参考段名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.referenceName }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">备用段1</td>
			<td class="tdRight" width="25%">
				${bean.rsv1Code }
			</td>
		    <td class="tdLeft" width="25%">备用段1名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.rsv1Name }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">备用段2</td>
			<td class="tdRight" width="25%">
				${bean.rsv2Code }
			</td>
		    <td class="tdLeft" width="25%">备用段2名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.rsv2Name }
		    </td>
		    		    
		</tr>
		<tr>
			<td class="tdLeft" width="25%">专项段</td>
			<td class="tdRight" width="25%">
				${bean.specialId }
			</td>
		    <td class="tdLeft" width="25%">专项段名称</td>
		    <td class="tdRight" width="25%">
		    	${bean.specialName }
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