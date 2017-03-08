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
function doValidate(){
	if(!App.valid("#reqtypeForm")){return false;} 
	var feeDeptOld  =$("#feeDeptOld").val();
	var isProvinceBuyOld  =$("#isProvinceBuyOld").val();
	var feeDept = $("#feeDept").val();
	var isProvinceBuy = $("input[type=radio][name='isProvinceBuy']:checked").val();
	if(feeDeptOld != feeDept || isProvinceBuyOld != isProvinceBuy){
		return check(feeDept,feeDeptOld,isProvinceBuy,isProvinceBuyOld); 
	}
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="0812010204"/>
<form action="" method="post" id="reqtypeForm">
<input type="hidden" name="org1Code" value="${bean.org1Code }"/>
<input type="hidden" name="matrCode" value="${bean.matrCode }"/>
<input type="hidden" name="montCode" value="${bean.montCode }"/>
<input type="hidden" name="aprvType" value="${bean.aprvType }"/>
<input type="hidden" name="feeCode" value="${bean.feeCode }"/>
<input type="hidden" name="dataYear" value="${bean.dataYear }"/>
<input type="hidden" name="orgCode" value="${bean.orgCode }"/>
<input type="hidden" value="${bean.matrAuditDept}" name="matrAuditDept"  />
<p:token/>
	<table id="approveChainTable">
		<tr>
			<th colspan="4">编辑</th>
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
		<c:if test="${bean.aprvType eq '12' }">
			<tr>
				<td class="tdLeft">费用承担部门编码</td>
				<td class="tdRight" >
					${bean.feeCode}
				</td>
				<td class="tdLeft">费用承担机构编码</td>
				<td class="tdRight" >
					${bean.orgCode}
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="tdLeft">物料采购部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
			
				<forms:OrgSelPlugin suffix="s1" rootNodeId="${bean.org1Code}" initValue="${bean.matrBuyDept}" jsVarGetValue="matrBuyDept" parentCheckFlag="false"/>
				
			</td>
			<td class="tdLeft">物料归口部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
			<forms:OrgSelPlugin suffix="s2" rootNodeId="${bean.org1Code}" initValue="${bean.matrAuditDept}"   jsVarGetValue="updateMatrAuditDept" parentCheckFlag="false"/>
				
			</td>
		</tr>
		<tr>
			<td class="tdLeft">本级财务管理部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
			<forms:OrgSelPlugin suffix="s3" rootNodeId="${bean.org1Code}" initValue="${bean.fincDeptS}" jsVarGetValue="fincDeptS" parentCheckFlag="false"/>
			</td>
			<td class="tdLeft">二级分行财务管理部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
					<forms:OrgSelPlugin suffix="s4" rootNodeId="${bean.org1Code}" initValue="${bean.fincDept2}" jsVarGetValue="fincDept2" parentCheckFlag="false"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">一级分行财务管理部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" colspan="3">
				<forms:OrgSelPlugin suffix="s5" rootNodeId="${bean.org1Code}" initValue="${bean.fincDept1}" jsVarGetValue="fincDept1" parentCheckFlag="false"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				    <p:button funcId="0812010204" value="提交"/>
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
</form>
</body>
</html>