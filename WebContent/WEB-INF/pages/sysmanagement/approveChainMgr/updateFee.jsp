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
<div id="feeDiv" style="display:none;">
		<form action="" method="post" id="feeForm">
		<input type="hidden" name="aprvType" id="alertAprvType"/>
		<input type="hidden" name="matrCode" id="matrCodeAudit"/>
		<input type="hidden" name="matrName" id="matrNameAudit"/>
		<input type="hidden" name="montCode" id="montCodeAudit"/>
		<input type="hidden" name="montName" id="montNameAudit"/>
		<input type="hidden" name="matrBuyDept" id="matrBuyDeptAudit"/>
		<input type="hidden" name="matrAuditDept" id="matrAuditDeptAudit"/>
		<input type="hidden" name="orgCode" id="orgCodeAudit"/>
		<p:token/>
			<table width="98%">
				<tr>
					<td class="tdRight" width="180px;">费用承担部门<span class="red">*</span>:</td>
					<td  class="tdRight" >
						<span id="alertFeeCodeName"></span>
						<input id="alertFeeCode" name="feeCode" valid maxlength='100' class="base-input-text" type="hidden"/>
					</td>
				</tr>
				<tr>
					<td class="tdRight">修改后费用承担部门<span class="red">*</span>:</td>
					<td  class="tdRight" >
						
						<input type="text" id="updateFeeCodeName" name="updateFeeCodeName"   class="base-input-text"  valid  />
						<input type="hidden" id="updateFeeCode" name="updateFeeCode"   class="base-input-text"     />
						<forms:OrgSelPlugin   suffix="0012" triggerEle="#feeForm tr updateFeeCodeName,updateFeeCode::name,id" rootNodeId="${selectInfo.org21Code}" 
								rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="updateFeeCodeTree"  />
					</td>
				</tr>
			</table>
		</form>
	</div>