<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<script type="text/javascript">
// updateFeeDept.getSelectOrgList();
// $(""#updateFeeDept)
</script>
<!-- 审批弹出框DIV -->
<div id="buyDiv" style="display:none;">
		<form action="" method="post" id="buyForm">
		<input type="hidden" name="aprvType" id="alertAprvTypeBuy"/>
		<input type="hidden" name="matrCode" id="matrCodeAudit"/>
		<input type="hidden" name="matrName" id="matrNameAudit"/>
		<input type="hidden" name="montCode" id="montCodeAudit"/>
		<input type="hidden" name="montName" id="montNameAudit"/>
		<input type="hidden" name="matrAuditDept" id="matrAuditDeptAudit"/>
		<input type="hidden" name="feeCode" id="feeCodeAudit"/>
		<input type="hidden" name="orgCode" id="orgCodeAudit"/>
		<p:token/>
			<table width="98%">
				<tr>
					<td class="tdRight" width="180px;">物料采购部门<span class="red">*</span>:</td>
					<td  class="tdRight" >
						<span id="alertmatrBuyDeptName"></span>
						<input id="alertmatrBuyDept" name="matrBuyDept" valid maxlength='100' class="base-input-text" type="hidden"/>
					</td>
				</tr>
				<tr>
					<td class="tdRight">修改后物料采购部门<span class="red">*</span>:</td>
					<td  class="tdRight" >
						
						<input type="text" id="updateMatrBuyDeptName" name="updateMatrBuyDeptName"   class="base-input-text"  valid  />
						<input type="hidden" id="updateMatrBuyDept" name="updateMatrBuyDept"   class="base-input-text"     />
						<forms:OrgSelPlugin   suffix="0011" triggerEle="#buyForm tr updateMatrBuyDeptName,updateMatrBuyDept::name,id" rootNodeId="${selectInfo.org1Code}" 
								rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="updateMatrBuyDeptTree"  />
					</td>
				</tr>
			</table>
		</form>
	</div>