<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同付款明细</title>
<style type="text/css">
	.trClas{
		font-family: 'Microsoft Yahei',Arial;
		font-size: 13px;
		background:#FBFBF9;
		color:#4c4944; 
	}
</style>
<script type="text/javascript">

function pageInit()
{
	var tabs = $( "#tabs" ).tabs({
		active: $('#tabIndex').val(),
		activate: function(){
			$('#tabIndex').val($(this).tabs('option', 'active'));

			if($('#tabIndex').val()=="0")
			{
				payList();
			}
			else if($('#tabIndex').val()=="1")
			{
				advPayList();
			}
		}
	});	
}

function payList()
{
	var cntNum = '${selectInfo.cntNum}';
	var form = $("#payAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/deail.do?<%=WebConsts.FUNC_ID_KEY%>=03030301&tabsIndex=0&cntNum="+cntNum;
	App.submit(form,true);
}

function advPayList()
{
	var cntNum = '${selectInfo.cntNum}';
	var form = $("#payAdvAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/deail.do?<%=WebConsts.FUNC_ID_KEY%>=03030301&tabsIndex=1&cntNum="+cntNum;
	App.submit(form,true);
}
//正常付款新增或核销
function addPay(cntNum,monDataFlag,flag)
{
	if(flag == 2){//预提待摊有回冲可以执行付款
		
	}else{
		if(monDataFlag == 0){//月结中
			App.notyError("正在月结中，不允许新增付款。");
			return ;
		}
	}
	var form = $("#payAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/addPay.do?<%=WebConsts.FUNC_ID_KEY %>=03030306&cntNum="+cntNum;
	App.submit(form,true);
}
//贷项通知单
function addCreditNote(cntNum,invoiceIdBlue,monDataFlag,flag)
{
	if(flag == 2){//预提待摊有回冲可以执行付款
		
	}else{
		if(monDataFlag == 0){//月结中
			App.notyError("正在月结中，不允许新增付款。");
			return ;
		}
	}
	var form = $("#payCreditAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/addCreditNote.do?<%=WebConsts.FUNC_ID_KEY %>=03030330&cntNum="+cntNum+"&invoiceIdBlue="+invoiceIdBlue;
	App.submit(form,true);
}
//预付款新增
function addPayAdvance(cntNum,monDataFlag,flag)
{
	if(flag == 2){//预提待摊有回冲可以执行付款
		
	}else{
		if(monDataFlag == 0){//月结中
			App.notyError("正在月结中，不允许新增付款。");
			return ;
		}
	}
	var form = $("#payAdvAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/addPayAdvance.do?<%=WebConsts.FUNC_ID_KEY %>=03030303&cntNum="+cntNum;
	App.submit(form,true);
}
//预付款明细
function advanceDetail(payId,cntNum)
{
	var form = $("#payAdvAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/queryPayAdvDetail.do?<%=WebConsts.FUNC_ID_KEY %>=03030310&payId="+payId+"&cntNum="+cntNum;
	App.submit(form,true);
}
//正常付款明细
function payDetail(payId,cntNum)
{
	var form = $("#payAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/queryPayDetail.do?<%=WebConsts.FUNC_ID_KEY %>=03030311&payId="+payId+"&cntNum="+cntNum;
	App.submit(form,true);
}
//预付款核销
function payAdvanceDeal(payId,cntNum)
{
	var form = $("#payAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/queryPayAdvCancelDeal.do?<%=WebConsts.FUNC_ID_KEY %>=03030312&payId="+payId+"&cntNum="+cntNum;
	App.submit(form,true);
}
//查看原蓝字发票下的贷项通知单列表
function getCreditListByInvoiceidBlue(invoiceIdBlue)
{
	
	var url="<%=request.getContextPath()%>/pay/payAdd/creditList.do?<%=WebConsts.FUNC_ID_KEY %>=03030332&invoiceIdBlue="+invoiceIdBlue;
	$.dialog.open(
			url,
			{
				width: "80%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"贷项通知单列表",
			    id:"dialogCutPage",
				close: function(){
					}
			}
			
		 );
}

function suspenseDeal(payId,cntNum)
{
	var form = $("#payAddForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/queryPaySuspenseDeal.do?<%=WebConsts.FUNC_ID_KEY %>=03030313&payId="+payId+"&cntNum="+cntNum;
	App.submit(form,true);
}

function getBlueInvoiceList(){
	var form = $("#payCreditSearchForm")[0];
	form.action = "<%=request.getContextPath()%>/pay/payAdd/deail.do?<%=WebConsts.FUNC_ID_KEY %>=03030301";
	App.submit(form,true);
}

//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#projType").val("");	
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
};
</script>
</head>

<body>
<form:hidden id="tabIndex" path="selectInfo.tabsIndex"/>
<p:authFunc funcArray="03030302,03030303"/>
	<table>
		<tr>
			<th colspan="4">
				合同信息
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">合同号</td>
			<td class="tdRight" width="25%">
				${payAddInfo.cntNum}
			</td>
			<td class="tdLeft" width="25%">进度</td>
			<td class="tdRight" width="25%">
				<fmt:parseNumber value="${payAddInfo.normarlTotalAmt+payAddInfo.advanceTotalAmt}" var="a"/>
				<fmt:parseNumber value="${payAddInfo.cntAllAmt}" var="b"/>
				<fmt:formatNumber type="number" value="${a/b*100}" maxFractionDigits="2" minFractionDigits="2"/>%
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同总金额(人民币)</td>
			<td class="tdRight">
				<fmt:formatNumber type="number" value="${payAddInfo.cntAllAmt}" minFractionDigits="2" maxFractionDigits="2"/><br/>
				<input type="hidden" id="cntAmt"  name="cntAmt" value="${payAddInfo.cntAmt}"/>
				<input type="hidden" id="totalAmt"  name="totalAmt" value="${payAddInfo.normarlTotalAmt+payAddInfo.advanceTotalAmt+payAddInfo.freezeTotalAmt+payAddInfo.suspenseTotalAmt }"/>
				其中质保金：${payAddInfo.zbAmt}%
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
				${payAddInfo.cntTypeName}
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">不含税金额(人民币)</td>
			<td class="tdRight">
				<fmt:formatNumber type="number" value="${payAddInfo.cntAmt}" minFractionDigits="2"/>
			</td>
			<td class="tdLeft">税额(人民币)</td>
			<td class="tdRight">
				<fmt:formatNumber type="number" value="${payAddInfo.cntTaxAmt}" minFractionDigits="2"/>
				
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">正常付款金额(人民币)</td>
			<td class="tdRight">
				<c:if test="${!empty payAddInfo.normarlTotalAmt}">
					<fmt:formatNumber type="number" value="${payAddInfo.normarlTotalAmt}" minFractionDigits="2"/>
				</c:if>
				<c:if test="${empty payAddInfo.normarlTotalAmt}">
					<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
				</c:if>
			</td>
			<td class="tdLeft">预付款金额(人民币)</td>
			<td class="tdRight">
				<c:if test="${!empty payAddInfo.advanceTotalAmt}">
					<fmt:formatNumber type="number" value="${payAddInfo.advanceTotalAmt}" minFractionDigits="2"/>
				</c:if>
				<c:if test="${empty payAddInfo.advanceTotalAmt}">
					<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">冻结金额(人民币)</td>
			<td class="tdRight">
				<c:if test="${!empty payAddInfo.freezeTotalAmt}">
					<fmt:formatNumber type="number" value="${payAddInfo.freezeTotalAmt}" minFractionDigits="2"/>
				</c:if>
				<c:if test="${empty payAddInfo.freezeTotalAmt}">
					<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
				</c:if>
			</td>
			<td class="tdLeft">暂收总金额(人民币)</td>
			<td class="tdRight">
				<c:if test="${!empty payAddInfo.suspenseTotalAmt}">
					<fmt:formatNumber type="number" value="${payAddInfo.suspenseTotalAmt}" minFractionDigits="2"/>
				</c:if>
				<c:if test="${empty payAddInfo.suspenseTotalAmt}">
					<fmt:formatNumber type="number" value="0" minFractionDigits="2"/>
				</c:if>
			</td>
		</tr>
	</table>
	<br/>
	<div id="tabs" style="border: 0;">
		<ul>
			<li><a href="#tabs-1">正常付款列表</a></li>
			<li><a href="#tabs-2">预付款列表</a></li>
			<li><a href="#tabs-3" onclick="getBlueInvoiceList();">贷项通知单列表</a></li>
		</ul>
	    <div id="tabs-1" style="padding: 0;">
	    	<form method="post" id="payAddForm">
			<table class="tableList">
				<tr class="trClas">
					<th width="11%">付款单号</th>
					<th width="8%">收款单位</th>
					<th width="20%">发票号</th>
					<th width="11%">发票金额(元)</th>
					<th width="11%">付款金额(元)</th>
					<th width="11%">暂收金额(元)</th>
					<th width="16%">已结清暂收金额(元)</th>
					<th width="6%">状态</th>
					<th width="6%">明细</th>
				</tr>
				<c:if test="${!empty payList}">
					<c:forEach items="${payList}" var="bean">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
							<td>${bean.payId}</td>
							<td>${bean.providerName}</td>
							<td>${bean.invoiceId}</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.suspenseAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.susTotalAmt}" minFractionDigits="2"/></td>
							<td>${bean.dataFlagName}</td>
							<td>
								<div class="detail">
									<a href="#" onclick="payDetail('${bean.payId}','${payAddInfo.cntNum}');" title="明细"></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty payList}">
					<tr><td style="text-align: center;" colspan="9"><span class="red">没有找到相关信息</span></td></tr>
				</c:if>
			</table>
			</form>
			<p:page pageKey="ADD_PAY_PAGE_KEY"/>
			<div>
			<!-- 合同状态非32的（32的只能做贷项通知单）、收益期开始后（收益期后的合同可做贷项通知单）且合同金额没有被占满 -->
			<c:if test="${payAddInfo.dataFlag != '32' }">
				<c:if test="${empty payAddInfo.feeStartDate || payAddInfo.feeStartDate <= payAddInfo.sDate}">
					<c:if test="${payAddInfo.cntAllAmt > (payAddInfo.normarlTotalAmt+payAddInfo.advanceTotalAmt+(payAddInfo.freezeTotalAmt>0 ? payAddInfo.freezeTotalAmt : 0)+payAddInfo.suspenseTotalAmt) || advCancelOver == 'N'  }">
						<input type="button" value="正常付款新增" onclick="addPay('${payAddInfo.cntNum}','${monDataFlag }','${flag }')"/>
					</c:if>
				</c:if>
	    	</c:if>
	    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	    	<br/><br/>
	    	</div>
		</div>
	    <div id="tabs-2" style="padding: 0;">	
	    	<form method="post" id="payAdvAddForm">
			<table class="tableList">
				<tr class="trClas">
					<th width="20%">付款单号</th>
					<th width="15%">收款单位</th>
					<th width="22%">发票号</th>
					<th width="11%">发票金额(元)</th>
					<th width="11%">付款金额(元)</th>
					<th width="11">状态</th>
					<th width="10%">明细</th>
				</tr>
				<c:if test="${!empty payAdvanceList}">
					<c:forEach items="${payAdvanceList}" var="bean">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
							<td>${bean.payId}</td>
							<td>${bean.providerName}</td>
							<td>${bean.invoiceId}</td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2"/></td>
							<td class="tdr"><fmt:formatNumber type="number" value="${bean.payAmt}" minFractionDigits="2"/></td>
							<td>${bean.dataFlagName}</td>
							<td >
								<div class="detail">
									<a href="#" onclick="advanceDetail('${bean.payId}','${payAddInfo.cntNum}');" title="明细"></a>
								</div>
							</td>
							
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty payAdvanceList}">
					<tr><td style="text-align: center;" colspan="7"><span class="red">没有找到相关信息</span></td></tr>
				</c:if>
			</table>
			</form>
			<p:page pageKey="ADD_ADVPAY_PAGE_KEY"/>
			<div>
			<!-- 没到收益期的可做预付款payAddInfo.feeStartDate > payAddInfo.sDate -->
			<c:if test="${payAddInfo.dataFlag != '32' && payAddInfo.cntAllAmt > (payAddInfo.normarlTotalAmt+payAddInfo.advanceTotalAmt+(payAddInfo.freezeTotalAmt>0 ? payAddInfo.freezeTotalAmt : 0)+payAddInfo.suspenseTotalAmt) }">
	    		<input type="button" value="预付款新增" onclick="addPayAdvance('${payAddInfo.cntNum}','${monDataFlag }','${flag}')"/>
	    	</c:if>
	    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	    	<br/><br/>
	    	</div>
	    </div>
	    <div id="tabs-3" style="padding: 0;">	
	     <form method="post" id="payCreditSearchForm">
	     	<table>
	     	 <tr>
	     	 	<th colspan="4">
	     	 		原蓝字发票查询
	     	 		<input type="hidden" name="tabsIndex" value="2" />
	     	 		<input type="hidden" name="cntNum" value="${payAddInfo.cntNum}"/>
	     	 	</th>
	     	 </tr>
	     	 <tr>
	     	 	<td class="tdLeft">原蓝字发票编号</td>
	     	 	<td class="tdRight">
	     	 		<input type="text" id="invoiceIdBlue" name="invoiceIdBlue"  value="${selectCreditInfo.invoiceIdBlue}"  class="base-input-text" />
	     	 	</td>
	     	 </tr>
	     	 <tr>
	     	 	<td colspan="4">
	     	 		<input type="button" value="查找" onclick="getBlueInvoiceList();"/>
				    <input type="button" value="重置" onclick="initEvent();"/>	
	     	 	</td>
	     	 </tr>
	     	</table>
	     </form>
	     <br/><br/>
	     <form method="post" id="payCreditAddForm">
	    	<table class="tableList">
				<tr class="trClas">
					<th width="22%">原蓝字发票编码
					<input type="hidden" name="tabsIndex" value="2" />
					</th>
					<th width="10%">原蓝字发票总金额</th>
					<th width="10%">不含税金额</th>
					<th width="8%">税额</th>
					<th width="12%">剩余可冲销额度<br/>(含税金额)</th>
					<th width="17%">原蓝字发票状态</th>
					<th width="26%">操作</th>
				</tr>
				<c:if test="${!empty getBlueInvoiceList}">
					<c:forEach items="${getBlueInvoiceList}" var="bean">
						<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
							<td>${bean.invoiceId}</td>
							<td>${bean.invoiceAmt}</td>
							<td>${bean.invoiceAmtNotax}</td>
							<td>${bean.invoiceAmtTax}</td>
							<td>${bean.invoiceAmtLeft}</td>
							<td>${bean.dataFlagInvoiceName}</td>
							<td>
								<div class="detail">
								<input type="button" value="新增" onclick="addCreditNote('${payAddInfo.cntNum}','${bean.invoiceId}','${monDataFlag }','${flag}')"/>
	    	                    <input type="button" value="贷项通知单列表" onclick="getCreditListByInvoiceidBlue('${bean.invoiceId}');"/>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty getBlueInvoiceList}">
					<tr><td style="text-align: center;" colspan="7"><span class="red">没有找到相关信息</span></td></tr>
				</c:if>
			</table>
			</form>
			<p:page pageKey="blueinvoicepage"/>
			<br/>
			<div>
	    	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	    	</div>
	    </div>
    </div>

</body>
</html>