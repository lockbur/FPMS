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
<title>机构明细</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
}
</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form action="" method="post" id="orgForm">
<p:token/>
	<table id="orgFormTable">
		<tr>
			<th colspan="4">编辑</th>
		</tr>
		<tr>
			<td class="tdLeft"> 责任中心编码</td>
			<td class="tdRight">
				${bean.dutyCode }
			</td>
			<td class="tdLeft">责任中心名称</td>
			<td class="tdRight">
				${bean.dutyName }
			</td>
		</tr>
		<tr>
			<td class="tdLeft">二级行编码</td>
			<td class="tdRight">
				${bean.org2Code }				
			</td>
			<td class="tdLeft">二级行名称</td>
			<td class="tdRight">
				${bean.org2Name }
			</td>
		</tr>
		<tr>
			<td class="tdLeft">一级行编码</td>
			<td class="tdRight">
				${bean.org1Code }
			</td>
			<td class="tdLeft">一级行名称</td>
			<td class="tdRight">
				${bean.org1Name }
			</td>
		</tr>
		<tr>
			<td class="tdLeft"> 机构编码</td>
			<td class="tdRight">
				${bean.orgCode }
			</td>
			<td class="tdLeft">机构名称</td>
			<td class="tdRight">
				${bean.orgName }
			</td>
		</tr>
		<tr>
			<td class="tdLeft"> ou编码</td>
			<td class="tdRight">
				${bean.ouCode }
			</td>
			<td class="tdLeft">ou名称</td>
			<td class="tdRight">
				${bean.ouName }
			</td>
		</tr>
			<tr>
			<td class="tdLeft"> 库存组织编码</td>
			<td class="tdRight">
				${bean.stackOrgCode }
			</td>
			<td class="tdLeft">库存组织名称</td>
			<td class="tdRight">
				${bean.stackOrgName }
			</td>
			</tr>
			
			<tr>
			<td class="tdLeft">ICMS地址</td>
			<td class="tdRight">
				${bean.icms }
			</td>
			<td class="tdLeft"> </td>
			<td class="tdRight">
				 
			</td>
			</tr>
			
		<tr>
			<td colspan="4" class="tdWhite">
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
</form>
</body>
</html>