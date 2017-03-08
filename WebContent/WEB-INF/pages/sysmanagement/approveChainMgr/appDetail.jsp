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
<title>省行维护审批链修改</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
}
//单选按钮初始化
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
</script>
</head>
<body>
<p:authFunc funcArray="0812010204"/>
<form action="" method="post" id="reqtypeForm">
<p:token/>
	<table id="approveChainTable">
		<tr>
			<th colspan="4">明细</th>
		</tr>
		<tr>
			<td class="tdLeft"> 物料编码</td>
			<td class="tdRight">
				${bean.matrCode }
			</td>
			<td class="tdLeft">物料名称</td>
			<td class="tdRight">
				${bean.matrName}
			</td>
		</tr>
		<tr>
			<td class="tdLeft"> 监控指标编码</td>
			<td class="tdRight">
				${bean.montCode }
			</td>
			<td class="tdLeft">监控指标名称</td>
			<td class="tdRight">
				${bean.montName } 
			</td>
		</tr>
		<c:if test="${bean.aprvType ne '11' }">
			<tr>
				<td class="tdLeft">费用承担部门编码</td>
				<td class="tdRight" >
					${bean.feeCode}
				</td>
				<td class="tdLeft">费用承担部门名称</td>
				<td class="tdRight" >
					${bean.feeCodeName}
				</td>
			</tr>
			<tr>
				<td class="tdLeft">费用承担机构编码</td>
				<td class="tdRight" >
					${bean.orgCode}
				</td>
				<td class="tdLeft">费用承担机构名称</td>
				<td class="tdRight" >
					${bean.orgName}
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="tdLeft">物料归口部门编码</td>
			<td class="tdRight">
			
				${bean.matrAuditDept}
				
			</td>
			<td class="tdLeft">物料归口部门名称</td>
			<td class="tdRight">
			
				${bean.matrAuditName}
				
			</td>
		</tr>
		<tr>
			<td class="tdLeft">物料采购部门编码</td>
			<td class="tdRight">
			
				${bean.matrBuyDept}
				
			</td>
			<td class="tdLeft">物料采购部门名称</td>
			<td class="tdRight">
			
				${bean.matrBuyName}
				
			</td>
		</tr>
		<tr>
			<td class="tdLeft">本级财务管理部门编码</td>
			<td class="tdRight">
				${bean.fincDeptS}
			</td>
			<td class="tdLeft">本级财务管理部门名称</td>
			<td class="tdRight">
				${bean.fincDeptSName}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">二级财务管理部门编码</td>
			<td class="tdRight">
				${bean.fincDept2}
			</td>
			<td class="tdLeft">二级财务管理部门名称</td>
			<td class="tdRight">
				${bean.fincDept2Name}
			</td>
		</tr>
		<tr>
			<td class="tdLeft">一级财务管理部门编码</td>
			<td class="tdRight">
				${bean.fincDept1}
			</td>
			<td class="tdLeft">一级财务管理部门名称</td>
			<td class="tdRight">
				${bean.fincDept1Name}
			</td>
		</tr>
		<c:if test="${bean.aprvType ne '11' }">
			<tr>
				<td class="tdLeft">预算分解部门编码</td>
				<td class="tdRight">
					${bean.decomposeOrg}
				</td>
				<td class="tdLeft">预算分解部门名称</td>
				<td class="tdRight">
					${bean.decomposeOrgName}
				</td>
			</tr>
		</c:if>
		<tr>
			<td colspan="4" class="tdWhite">
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
</form>
</body>
</html>