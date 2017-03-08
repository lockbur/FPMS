<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<script type="text/javascript">
</script>
<!-- 审批弹出框DIV -->
<div id="backDiv" style="display:none;">
		<form action="" method="post" id="backForm">
		<input type="hidden" name="tmpltId" id="tmpltId2"/>
		<input type="hidden" name="matrAuditDept" id="matrAuditDept2"/>
		<input type="hidden" name="matrCode" id="matrCode2"/>
		<input type="hidden" name="addAmt" id="addAmt2"/>
		<input type="hidden" name="auditAmt" id="auditAmt2"/>
		<p:token/>
			<table width="98%">
				<tr>
					<td class="tdRight">退回原因<span class="red">*</span>:
						<br>(<span id="defrayedLogSpan">0/100</span>)
					</td>
					<td  class="tdRight" >
						<textarea class="base-textArea"  name="auditMemo" id="auditMemo" onkeyup="$_showWarnWhenOverLen1(this,100,'defrayedLogSpan')"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>