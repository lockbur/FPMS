<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<script type="text/javascript">
</script>
<!-- 审批弹出框DIV -->
<div id="auditDiv" style="display:none;">
		<form action="" method="post" id="auditForm">
		<input type="hidden" name="aprvType" id="alertAprvTypeAudit"/>
		<input type="hidden" name="matrCode" id="matrCodeAudit"/>
		<input type="hidden" name="matrName" id="matrNameAudit"/>
		<input type="hidden" name="montCode" id="montCodeAudit"/>
		<input type="hidden" name="montName" id="montNameAudit"/>
		<input type="hidden" name="matrBuyDept" id="matrBuyDeptAudit"/>
		<input type="hidden" name="feeCode" id="feeCodeAudit"/>
		<input type="hidden" name="orgCode" id="orgCodeAudit"/>
		<p:token/>
			<table width="98%">
				<tr>
					<td class="tdRight" width="180px;">归口部门<span class="red">*</span>:</td>
					<td  class="tdRight" >
						<span id="alertAuditDeptName"></span>
						<input id="alertAuditDept" name="matrAuditDept"   maxlength='100' class="base-input-text" type="hidden"/>
					</td>
				</tr>
				<tr>
					<td class="tdRight">修改后归口部门<span class="red">*</span>:</td>
					<td  class="tdRight" >
					<!-- 
						<input id="updateAuditDept"   maxlength='100' class="base-input-text" type="text"/>
						<input id="updateAuditDeptHide" name="updateMatrAuditDept"    type="hidden"/>
						<forms:OrgSelPlugin rootNodeId="${org21Code }" triggerEle="updateAuditDept::name" dialogFlag="true" rootLevel="1" suffix="0202" jsVarName="updateAuditDept"   />
						 --> 
						<input type="text" id="updateMatrAuditDeptName" name="updateMatrAuditDeptName"   class="base-input-text"  valid  />
						<input type="hidden" id="updateMatrAuditDept" name="updateMatrAuditDept"   class="base-input-text"     />
						<forms:OrgSelPlugin   suffix="001" triggerEle="#auditForm tr updateMatrAuditDeptName,updateMatrAuditDept::name,id" rootNodeId="${selectInfo.org1Code}" 
								rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="updateMatrBuyDeptTree"  />
								
					</td>
				</tr>
			</table>
		</form>
	</div>