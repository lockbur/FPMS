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
<title>机构撤并明细</title>
<script type="text/javascript">
function doValidate() {
	
}
</script>
</head>
<body>
	<table>
		<tr>
			<th colspan="2">
				撤并明细
			</th>
		</tr>
		<tr>
			<td class="tdLeft">撤并类型</td>
			<td class="tdRight"  >
				<c:if test="${dMBean.changeType =='01' }">责任中心撤并</c:if>
				<c:if test="${dMBean.changeType=='02' }">机构撤并</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >撤并状态</td>
			<td class="tdRight" >
				<c:if test="${dMBean.status == '01' }">
					待复核					
				</c:if>
				<c:if test="${dMBean.status == '02' }">
					复核通过						
				</c:if>
				<c:if test="${dMBean.status == '03' }">
					复核不通过						
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >撤并前</td>
			<td class="tdRight">
				${dMBean.codeBef }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >撤并后</td>
			<td class="tdRight">
				${dMBean.codeCur }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">审批日期</td>
			<td class="tdRight" width="25%">
				${dMBean.aprvDate }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">审批时间</td>
			<td class="tdRight" width="25%">
				${dMBean.aprvTime }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">审批用户</td>
			<td class="tdRight" width="25%">
				${dMBean.aprvUser }
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">审批备注</td>
			<td class="tdRight" width="25%">
				${dMBean.memo }
			</td>
		</tr>
	</table>
	<br/>
   	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
</body>
</html>