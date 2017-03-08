<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!-- 审批弹出框DIV -->
<div id="auditDiv" style="display:none;">
		<form action="" method="post" id="auditForm">
		<p:token/>
			<table width="98%">
				<tr>
					<td align="left">
					<div id="hideProjIdList" style="display:none;"></div>
						终止日期:
						<input disabled="disabled" id="endDate" name="endDate" valid maxlength='10' readonly="readonly" class="base-input-text" type="text"/>
					</td>
				</tr>
				<tr>
					<td align="left" >
						<br>终止意见(<span id="authmemoSpan">0/2000</span>)：
						<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,2000,'authmemoSpan')" id="endMemo" name="endMemo" errorMsg="请输入意见。"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>