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
<title>物料类别详情</title>
</head>

<body>
<p:authFunc funcArray="110702,11070201,11070202"/>
<form action="<%=request.getContextPath()%>/rm/baseline/add.do" method="post" id="BLForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="4">
				物料类别详情
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">物料类型</td>
		    <td class="tdRight" width="25%" colspan="3">
		    	${mt.matrType}
		    </td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">物料编码</td>
			<td class="tdRight" width="25%">
				${mt.matrCode}	
			</td>
			<td class="tdLeft" width="25%">是否是订单类物料</td>
			<td class="tdRight" width="25%">
				${mt.isOrder}	
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">物料名称</td>
			<td class="tdRight" width="25%" >
				${mt.matrName}
			</td>
			<c:if test="${mt.matrType=='资产'}">
				<td class="tdLeft" width="25%">资产分类</td>
				<td class="tdRight" width="25%">
					${mt.isGDZC}	
				</td>
			</c:if>
			<c:if test="${mt.matrType=='费用'}">
				<td class="tdLeft" width="25%">费用分类</td>
				<td class="tdRight" width="25%" >
					${mt.isNotinfee}
				</td>
			</c:if>
		</tr>
		<tr>	
			<td class="tdLeft" width="25%">物料单位</td>
			<td class="tdRight" width="25%">
				${mt.matrUnit}
			</td>
		    		   
			<td class="tdLeft" width="25%">费用核算码</td>
			<td class="tdRight" width="25%">
				${mt.cglCode}
			</td>
					</tr>
		<c:if test="${mt.matrType=='费用'}">
			<tr>
			    <td class="tdLeft" width="25%">待摊核算码（长期）</td>
			    <td class="tdRight" width="25%">
			    	${mt.longPrepaidCode}
			    </td>
			    		    		
			    <td class="tdLeft" width="25%">待摊核算码（短期）</td>
			    <td class="tdRight" width="25%">
			    	${mt.shortPrepaidCode}
			    </td>		
			</tr>
			<tr>
			    <td class="tdLeft" width="25%">长期待摊固定对应的费用核算码</td>
			    <td class="tdRight" width="25%">
			   		${mt.longPrepaidCodeFee}
			    </td>
			    <td class="tdLeft" width="25%">是否是待摊预提类物料</td>
			    <td class="tdRight" width="25%">
			    	${mt.isPrepaidProvision}
			    </td>
			</tr>
		</c:if>
		<c:if test="${mt.matrType=='资产'}">
			<tr>
				<td class="tdLeft" width="25%">是否是房产类物料</td>
				<td class="tdRight" width="25%" colspan="3">
					${mt.isFcwl}	
				</td>
			</tr>
		</c:if>
		<tr>
		<td class="tdLeft" width="25%">说明</td>
		   <!--  <td class="tdRight" width="75%">
		    	${mt.memo}
		    </td>-->
		    <td class="tdRight" width="75%" colspan="3">
		        <textarea class="base-textArea"  name="memo" id="memo" disabled="disabled">${mt.memo}</textarea>
		    </td>
		    </tr>
		<!--  <tr>
		    <td class="tdLeft" width="25%">是否业务宣传费</td>
		    <td class="tdRight" width="25%">
		    	${mt.isPublicityPay}
		    </td>
		    <td class="tdLeft" width="25%">说明</td>
		    <td class="tdRight" width="75%">
		    	${mt.memo}
		    </td>		    
		</tr>-->
		<tr>
			<td colspan="4" class="tdWhite">
				 <input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
	</table>
	<br>
</form>
</body>
</html>