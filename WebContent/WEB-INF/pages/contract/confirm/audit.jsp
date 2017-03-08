<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>

<!--审批弹出层 -->
<!-- 审批弹出框DIV -->
<div id="auditDiv" style="display:none;">
		<form action="" method="post" id="auditForm">
		<input type="hidden" name="feeType" id="feeTypeAudit"/>
		<p:token/>
			<table width="98%">
				<div id="feeTypeTable" style="display:none"></div>
				<c:if test="${cnt.cntType == '0' || (cnt.cntType == '1' && cnt.feeType == '2')}">
					<tr id="orderdisplayid">
						<td class="tdLeft" width="40%">是否生成订单</td>
						<td class="tdRight" width="60%">
							<div class="base-input-radio" id="authdealFlagDiv">
						<label for="authdealFlag1" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('0');" >是</label><input type="radio" id="authdealFlag1" name="dealFlag" value="0" >
						<label for="authdealFlag2" onclick="App.radioCheck(this,'authdealFlagDiv');toSelectResult('1')" >否</label><input type="radio" id="authdealFlag2" name="dealFlag" value="1">
						</div>
							<!--<div class="base-input-radio" id="isOrderDiv">
								<label for="isOrderYes" onclick="changeIsOrder(this)" >是</label>
								<input type="radio" id="isOrderYes" name="isOrderInput" value="0">
								<label for="isOrderNo" onclick="changeIsOrder(this)">否</label>
								<input type="radio" id="isOrderNo" name="isOrderInput" value="1">
							</div> -->
						</td>
					</tr>
				</c:if>
				<tr>
					<td align="left" colspan="2">
						<input type="hidden" id="cntNum" name="cntNum"/>
						<input type="hidden" id="location" maxlength="200" name="location"/>
						<input type="hidden" id="isOrder" name="isOrder"/>
						<br>操作备注(<span id="authmemoSpan">0/2000</span>)：
						<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,2000,'authmemoSpan')" id="waterMemo" name="waterMemo"   valid errorMsg="请输入操作备注。" rows="7" cols="45"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>