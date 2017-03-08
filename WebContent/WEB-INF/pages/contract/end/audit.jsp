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
			  <c:if test="${cnt.isPrepaidProvision == '0' && cnt.feeEndDate >= nowDate}">
			    <tr>
			    <td align="left" >
						待摊核算码上剩余金额：&nbsp;&nbsp;${cnt.prepaidRemaindAmt}元
						<br/>
						是否一次待摊：&nbsp;&nbsp;
						
						<div class="base-input-radio" id="oncePrepaid">
							<label for="yes" onclick="App.radioCheck(this,'oncePrepaid');toSelectResult('Y');" >是</label>
								<input type="radio" id="yes" name="dealFlag" value="Y" />
							<label for="no" onclick="App.radioCheck(this,'oncePrepaid');toSelectResult('N');"  >否</label>
								<input type="radio" id="no" name="dealFlag"  value="N" />
						</div>
			     </td>
			    </tr>
			 </c:if>
				<tr>
					<td align="left" >
						<input id="cntNum" name="cntNum" maxlength="80" type="hidden"/>
						<input type="hidden" id="prepaidStatus" name="prepaidStatus"/>
						<br>操作备注(<span id="authmemoSpan">0/2000</span>)：
						<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,2000,'authmemoSpan')"   id="waterMemo" name="waterMemo" valid errorMsg="请输入审批意见。"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>