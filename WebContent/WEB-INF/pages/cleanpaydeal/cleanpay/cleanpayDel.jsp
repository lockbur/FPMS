<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>正常付款暂收结清</title>
<script type="text/javascript">
function doValidate() {
	//提交前调用
	if(!App.valid("#paySuspenseDealForm")){
		return;
	}
	else if(!$.checkMoney($("#cleanAmt").val()))
	{
		App.notyError("结清金额格式有误！最多含两位小数的18位正浮点数。");
		$("#cleanAmt").focus();
		return false;
	}
	//判断结清金额应小于暂收金额-已结清金额-正在结清总金额
	else if(!checkCleanAmt())	
	{
		App.notyError("结清金额应小于等于(暂收金额-已结清金额-正在结清总金额)。");
		$("#cleanAmt").focus();
		return false;
	}
	
// 	var uncleanAmtBef = '${payInfo.uncleanAmt}';
// 	var cleanAmt = $("#cleanAmt").val();
// 	var uncleanAmt = parseFloat($("#uncleanAmt").val());
// 	var leftAmt = parseFloat(uncleanAmtBef) - parseFloat(cleanAmt);
// 	//判断剩余结清金额应小于实际剩余结清金额
// 	if(uncleanAmt < leftAmt)
// 	{
// 		alert("本次付款金额+本次暂收金额小于原暂收金额 \n若差额部分继续暂收，请将差额部分录入本次暂收栏位； \n若差额部分退回，请将差额部分暂收结清，并新增贷项通知单收回。");
// 	}
	return true;
}
//校验结清金额应小于等于暂收金额-已结清金额-正在结清总金额
function checkCleanAmt()
{
	var cleanAmt = parseFloat($("#cleanAmt").val());//结清金额
	var remainAmt = parseFloat($("#suspenseAmt").val()) - parseFloat($("#susTotalAmt").val()) - parseFloat($("#cleanAmtIngTotal").val());
	if(cleanAmt > remainAmt)
	{
		return false;
	}
	return true;
}
//已结清明细
function suspenseDetail(normalPayId,sortId)
{
	var form=$("#paySuspenseDetailForm")[0];
	var url = "<%=request.getContextPath()%>/cleanpaydeal/cleanpay/queryCleanedDetail.do?<%=WebConsts.FUNC_ID_KEY%>=03050102&flag=1";
	url += "&normalPayId="+normalPayId;
	url += "&sortId="+sortId;
	url += "&cntNum="+$("#cntNum").val();
	form.action= url;
	App.submit(form);
}
</script>
</head>
<body>
<p:authFunc funcArray="03050103,03050104"/>
<form action="" method="post" id="paySuspenseDetailForm"></form>
<form action="" method="post" id="paySuspenseDealForm">
	<p:token/>
	<table>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">
							合同信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="25%">合同号</td>
						<td class="tdRight" width="25%">
							${constractInfo.cntNum}
							<input type="hidden" id="cntNum" name="cntNum" value="${constractInfo.cntNum}"/>
						</td>
						<td class="tdLeft" width="25%">进度</td>
						<td class="tdRight" width="25%">
							<fmt:parseNumber value="${constractInfo.normarlTotalAmt+constractInfo.advanceTotalAmt}" var="a"/>
							<fmt:parseNumber value="${constractInfo.cntAllAmt}" var="b"/>
							<fmt:formatNumber type="number" value="${a/b*100}" minFractionDigits="2" maxFractionDigits="2"/>%
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额(人民币)</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${constractInfo.cntAmt}" minFractionDigits="2"/><br/>
							其中质保金：${constractInfo.zbAmt}%
						</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							${constractInfo.cntTypeName}
						</td>
					</tr>
					<tr>
						<td class="tdLeft">正常付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.normarlTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.normarlTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">预付款金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.advanceTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.advanceTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">冻结金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.freezeTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.freezeTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
						<td class="tdLeft">暂收总金额(人民币)</td>
						<td class="tdRight">
							<c:if test="${!empty constractInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="${constractInfo.suspenseTotalAmt}" minFractionDigits="2"/>
							</c:if>
							<c:if test="${empty constractInfo.suspenseTotalAmt}">
								<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
							</c:if>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr class="collspan-control">
						<th colspan="4">
							付款信息
						</th>
					</tr>
					<tr>
						<td class="tdLeft" width="25%">付款单号</td>
						<td class="tdRight" width="25%">
							${payInfo.payId}
							<input type="hidden" id="normalPayId" name="normalPayId" value="${payInfo.payId}"/>
						</td>
						<td class="tdLeft" width="25%">发票号</td>
						<td class="tdRight" width="25%">
							${payInfo.invoiceId }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">附件张数</td>
						<td class="tdRight"> 
							${payInfo.attachmentNum }
						</td>
						<td class="tdLeft"></td>
						<td class="tdRight"> 
						</td>
					</tr>
					<tr>
						<td class="tdLeft">收款单位</td>
						<td class="tdRight">
							${payInfo.providerName }
						</td>
						<td class="tdLeft">收款帐号</td>
						<td class="tdRight">
							${payInfo.provActNo }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">银行名称</td>
						<td class="tdRight">
							${payInfo.bankName }
						</td>
						<td class="tdLeft">发票金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.invoiceAmt}" minFractionDigits="2"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">付款金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.payAmt}" minFractionDigits="2"/>
						</td>
						<td class="tdLeft">付款日期</td>
						<td class="tdRight">
							${payInfo.payDate }
						</td>
					</tr>
					<tr>
						<td class="tdLeft">暂收金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.suspenseAmt}" minFractionDigits="2"/>
							<input type="hidden" id="suspenseAmt" name="suspenseAmt" value="${payInfo.suspenseAmt}"/>
						</td>
						<td class="tdLeft">已结清暂收金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.susTotalAmt}" minFractionDigits="2"/>
							<input type="hidden" id="susTotalAmt" name="susTotalAmt" value="${payInfo.susTotalAmt}"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">正在暂收结清的总金额</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${payInfo.cleanAmtIngTotal}" minFractionDigits="2"/>
							<input type="hidden" id="cleanAmtIngTotal" name="cleanAmtIngTotal" value="${payInfo.cleanAmtIngTotal}"/>
						</td>
						<td class="tdLeft">发票说明</td>
						<td class="tdRight">
							${payInfo.invoiceMemo }
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">已结清列表</th>
					</tr>
					<tr>
						<th width="30%">正常付款单号</th>
						<th width="30%">子序号</th>
						<th width="30%">结清金额(元)</th>
						<th width="10%">操作</th>
					</tr>
					<c:if test="${!empty payCleanedPayInfoList}">
						<c:forEach items="${payCleanedPayInfoList }" var="bean">
							<tr>
								<td>${bean.normalPayId }</td>
								<td>${bean.sortId }</td>
								<td><fmt:formatNumber type="number" value="${bean.cleanAmt}" minFractionDigits="2"/></td>
								<td>
									<div class="detail">
										<a href="#" onclick="suspenseDetail('${bean.normalPayId}','${bean.sortId}');" title="明细"></a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty payCleanedPayInfoList}">
					<tr><td style="text-align: center;" colspan="4"><span class="red">没有已结清信息</span></td></tr>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td class="tdLeft">结清金额(人民币)<span class="red">*</span></td>
						<td class="tdRight">
							<input id="cleanAmt" name="cleanAmt" class="base-input-text" valid errorMsg="请输入结清金额" maxlength="18" onkeyup="$.clearNoNum(this);" onblur="$.onBlurReplace(this);"/>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">结清原因<span class="red">*</span><br/>(<span id='memoSpan1'>0/200</span>)</td>
						<td class="tdRight">
							<textarea id="cleanReason" cols="60" rows="5"  name="cleanReason" onkeyup="$_showWarnWhenOverLen1(this,200,'memoSpan1')" class="base-textArea" valid errorMsg="请输入结清原因"></textArea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<p:button funcId="03050103" value="保存"/>
				<%-- <p:button funcId="03050104" value="提交"/> --%>
		    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
</body>
</html>