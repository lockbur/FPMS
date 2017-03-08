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
<title>合同复核</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
$(function(){
	$('#hideSubIds').html($('#projectTable').find("input[name='subIds']"));
})
//审批
function toAudit() {
	
	//复核
	$("#auditDiv").dialog({
		autoOpen: false,
		height: 'auto',
		width: 550,
		modal: true,
		dialogClass: 'dClass',
		resizable: false,
		open: function(){
			$('#cntNum').val('${cnt.cntNum}');
			$('#dataFlag').val('${cnt.dataFlag}');
		},
		buttons: {
			"通过": function() {	
				$( "<div>确认通过?</div>" ).dialog({
					resizable: false,
					height:150,
					modal: true,
					dialogClass: 'dClass',
					buttons: {
						"确定": function() {
							var form = $("#auditForm");
						 	form.attr('action', '<%=request.getContextPath()%>/contract/check/checkPass.do?<%=WebConsts.FUNC_ID_KEY%>=03020202');
							App.submit(form);
						},
						"取消": function() {
							$( this ).dialog( "close" );
						}
					}
				});
			},
			"退回":function(){
				if(!App.valid("#auditForm")){return;}
				$( "<div>确认退回?</div>" ).dialog({
					resizable: false,
					height:150,
					modal: true,
					dialogClass: 'dClass',
					buttons: {
						"确定": function() {
							var form = $("#auditForm");
						 	form.attr('action', '<%=request.getContextPath()%>/contract/check/checkGoBack.do?<%=WebConsts.FUNC_ID_KEY%>=03020203');
							App.submit(form);
						},
						"取消": function() {
							$( this ).dialog( "close" );
						}
					}
				});
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#auditDiv").dialog( "option", "title", "合同复核" ).dialog( "open" );
}

function toggleTr(obj) {
	$(obj).siblings("tr").toggle();
}
</script>
</head>

<body>
<p:authFunc funcArray="03020202,03020203"/>
<form action="" method="post" id="checkForm" action="">
	<p:token/>
	<table>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">
							基础信息（合同编号：${cnt.cntNum}）
							<input type="hidden" value="${cnt.cntNum}" name="cntNum"/>
						</th>
					</tr>
					<tr>
						<td class="tdLeft">集采编号</td>
						<td class="tdRight">${cnt.stockNum}</td>
						<td class="tdLeft">评审编号</td>
						<td class="tdRight">${cnt.psbh}</td>
					</tr>
					<tr>
						<td class="tdLeft">审批类别</td>
						<td class="tdRight">${cnt.lxlxName}</td>
						<td class="tdLeft">签报文号</td>
						<td class="tdRight">${cnt.qbh}</td>
					</tr>
					<tr>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">${cnt.cntTypeName}</td>
						<td class="tdLeft">合同状态</td>
						<td class="tdRight">${cnt.dataFlagName}</td>
					</tr>
					<tr>
						<td class="tdLeft">合同事项</td>
						<td class="tdRight">${cnt.cntName}</td>
						<td class="tdLeft">关联合同号</td>
						<td class="tdRight">${cnt.cntNumRelated}</td>
					</tr>
					<tr>
						<td class="tdLeft">审批数量</td>
						<td class="tdRight">${cnt.lxsl}</td>
						<td class="tdLeft">审批金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.lxje}"/></td>
					</tr>
					
					<c:if test="${cnt.lxlx eq '1'}">
					<tr>
						<td class="tdLeft" colspan="4">
							<table>
								<tr>
 									<th width="20%">缩位码</th>
									<th width="30%">审批编号</th>
									<th width="20%">日期</th>
									<th width="10%">审批数量</th>
<!-- 									<th width="10%">已执行数量</th> -->
									<th width="10%">数量</th>
									<th width="10%">审批金额</th>
<!-- 									<th width="11%">已执行金额</th> -->
<!-- 									<th width="10%">金额</th> -->
								</tr>
								<c:forEach items="${cnt.dzspInfos}" var="dzspItem">
									<tr>
										<td>${dzspItem.abcde}</td>
										<td>${dzspItem.projCrId}</td>
										<td>${dzspItem.createDate}</td>
										<td>${dzspItem.projCrNum}</td>
<%-- 										<td>${dzspItem.exeNum}</td> --%>
										<td>${dzspItem.abcdeNum}</td>
										<td><fmt:formatNumber type="number" value="${dzspItem.projCrAmt}"/></td>
<%-- 										<td><fmt:formatNumber type="number" value="${dzspItem.exeAmt}"/></td> --%>
<%-- 										<td><fmt:formatNumber type="number" value="${dzspItem.abcdeAmt}"/></td> --%>
									</tr>
								</c:forEach>
								
								<c:if test="${empty cnt.dzspInfos}">
									<tr>
										<td colspan="6" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>	
					</c:if>
					<tr>
						<td class="tdLeft">供应商</td>
						<td class="tdRight">${cnt.providerName}</td>
						<c:if test="${'' != cnt.srcPoviderName && null != cnt.srcPoviderName}">
							<td class="tdLeft">内部供应商</td>
							<td class="tdRight">
								${cnt.srcPoviderName}
							</td>
						</c:if>
						<c:if test="${empty cnt.srcPoviderName}">
							<td class="tdLeft"></td>
							<td class="tdRight">
							</td>
						</c:if>
					</tr>
					<tr >
					<td class="tdLeft">供应商信息</td>
					<td class="tdRight" colspan="3">
						<table >
							<tr>
								<td style="border: 0">银行名称：<span id="bankNameSpan">${cnt.bankName}</span></td>
								<td style="border: 0">供应商账号：<span id="provActNoSpan">${cnt.provActNo}</span></td>
							</tr>
							<tr>
								<td style="border: 0">供应商地址：<span id="providerAddrSpan">${cnt.providerAddr}</span></td>
								<td style="border: 0">银行账户名称：<span id="actNameSpan">${cnt.actName}</span></td>
							</tr>
							<tr>
								<td style="border: 0">开户行行号：<span id="bankCodeSpan">${cnt.bankCode}</span></td>
								<td style="border: 0">开户行详细信息：<span id="bankInfoSpan">${cnt.bankInfo}</span></td>
							</tr>
						</table>
					</td>
				</tr>
					 
					<tr>
						<td class="tdLeft">签订日期</td>
						<td class="tdRight">${cnt.signDate}</td>
						<td class="tdLeft">专项包</td>
						<td class="tdRight">
							<c:if test="${'0'==cnt.isSpec}">是</c:if>
							<c:if test="${'1'==cnt.isSpec}">否</c:if>
						</td>
					</tr>
					<c:if test="${cnt.cntType == '0' && cnt.isSpec == '1' }">
						<tr>
							<td class="tdLeft">省行统购</td>
							<td class="tdRight" colspan="3">
								<c:if test="${'0'==cnt.isProvinceBuy}">是</c:if>
								<c:if test="${'1'==cnt.isProvinceBuy}">否</c:if>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="tdLeft">合同总金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.cntAllAmt}"/></td>
						<td class="tdLeft">其中合同质保金(%)</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.zbAmt}"/></td>
					</tr>
					<tr>
						<td class="tdLeft">不含税金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.cntAmt}"/></td>
						<td class="tdLeft">税额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cnt.cntTaxAmt}"/></td>
					</tr>
					<tr>
						<td class="tdLeft">付款条件</td>
						<td class="tdRight" colspan="3">
							<c:if test="${'3' eq cnt.payTerm}">${cnt.stageTypeName}</c:if>
							${cnt.payTermName}</td>
					</tr>
					<c:if test="${'3' eq cnt.payTerm}">
						<c:if test="${'0' eq cnt.stageType}">
						<tr>
							<td class="tdLeft">按进度分期付款</td>
							<td class="tdRight" colspan="3">
								<table>
								<c:forEach items="${cnt.stageInfos}" var="schItem" varStatus="status">
									<tr>
										<td>第${status.index+1}期 付款年月:${schItem.jdDate}    支付${schItem.jdzf}元</td>
									</tr>
								</c:forEach>
								</table>
							</td>
						</tr>	
						</c:if>
						<c:if test="${'1' eq cnt.stageType}">
						<tr>
							<td class="tdLeft">按日期分期付款</td>
							<td class="tdRight" colspan="3">
								<table>
								<c:forEach items="${cnt.stageInfos}" var="dateItem">
									<tr>
										<td>合同签订后 ${dateItem.rqtj}天 支付<fmt:formatNumber type="number" value="${dateItem.rqzf}"/>元</td>
									</tr>
								</c:forEach>
								</table>
							</td>
						</tr>	
						</c:if>
						<c:if test="${'2' eq cnt.stageType}">
						<tr>
							<td class="tdLeft">按条件分期付款</td>
							<td class="tdRight" colspan="3">
								到货支付<fmt:formatNumber type="number" value="${cnt.stageInfos[0].dhzf}"/>元    验收支付<fmt:formatNumber type="number" value="${cnt.stageInfos[0].yszf}"/>元 结算支付<fmt:formatNumber type="number" value="${cnt.stageInfos[0].jszf}"/>元 
							</td>
						</tr>	
						</c:if>
					</c:if>
					<tr>
						<td class="tdLeft">采购数量</td>
						<td class="tdRight" colspan="3">${cnt.totalNum}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td></td></tr>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">
							合同物料信息
						</th>
					</tr>
					
					<tr>
						<td class="tdLeft" colspan="4">
							<table id="projectTable">
								<tr>
									<th width="7%">项目</th>
									<th width="11%">费用承担部门</th>
									<th width="8%">物料类型</th>
									<th width="8%">监控指标</th>
									<th width="8%">设备型号</th>
									<th width="7%">专项</th>
									<th width="7%">参考</th>
									<th width="10%">税码</th>
									<th width="5%">数量</th>
									<th width="5%">单价(元)</th>
									<th width="5%">不含税金额(元)</th>
									<th width="5%">税额(元)</th>
									<th width="3%">保修期(年)</th>
									<th width="7%">制造商</th>
									<th width="4%">状态</th>
								</tr>
								<c:forEach items="${deviceInfoList}" var="projItem">
									<tr>
										<td>${projItem.projName}<input type="hidden" name="subIds" value="${projItem.subId }" /></td>
										<td>${projItem.dutyName}</td>
										<td>${projItem.matrName}</td>
										<td>${projItem.montName}</td>
										<td>${projItem.deviceModelName}</td>
										<td>${projItem.specialName}</td>
								   		<td>${projItem.referenceName}</td>
								   		<td>${projItem.taxCode}</td>
										<td>${projItem.execNum}</td>
										<td><fmt:formatNumber type="number" value="${projItem.execPrice}"/></td>
										<td><fmt:formatNumber type="number" value="${projItem.execAmt}"/></td>
										<td><fmt:formatNumber type="number" value="${projItem.cntTrAmt}"/></td>
										<td>${projItem.warranty}</td>
										<td>${projItem.productor}</td>
										<td>${projItem.dataFlagDeviceName}<br>
											<c:if test="${not empty projItem.auditMemo}">审批意见：${projItem.auditMemo }</c:if>
										</td>
									</tr>
								</c:forEach>
								
								<c:if test="${empty deviceInfoList}">
									<tr>
										<td colspan="15" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
									</tr>
								</c:if>
							</table>	
						</td>
					</tr>
					<tr>
						<td class="tdLeft">备注</td>
						<td class="tdRight" colspan="3">${cnt.memo}</td>
					</tr>
					<tr>
						<td class="tdLeft">付款责任中心</td>
						<td class="tdRight">${cnt.payDutyCodeName}</td>
						<td class="tdLeft">业务发起部门</td>
						<td class="tdRight">${cnt.createDeptName}</td>
					</tr>
				</table>
			</td>
		</tr>
	<c:if test="${cnt.cntType eq '1'}">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="4">
							费用信息
						</th>
					</tr>
					<c:if test="${cnt.feeType eq '0'}">
					<tr>
						<td class="tdLeft">费用类型
						</td>
						<td class="tdRight">${cnt.feeTypeName}</td>
						<td class="tdLeft">费用子类型</td>
						<td class="tdRight">${cnt.feeSubTypeName}</td>
					</tr>
					<tr>
						<td class="tdLeft">合同受益起始日期</td>
						<td class="tdRight">${cnt.feeStartDate}</td>
						<td class="tdLeft">合同受益终止日期</td>
						<td class="tdRight">${cnt.actualFeeEndDate}</td>
					</tr>
					<tr>
						<td class="tdLeft">实际合同受益终止日期区间</td>
						<td class="tdRight">${cnt.feeStartDateShow} 至  ${cnt.feeEndDateShow}</td>
						<td class="tdLeft"></td>
						<td class="tdRight"></td>
					</tr>
					<c:if test="${cnt.feeSubType eq '1'}">
					<tr>
						<td class="tdLeft">地址(说明)</td>
						<td class="tdRight">${cnt.wydz}</td>
						<td class="tdLeft">租赁面积(数量)</td>
						<td class="tdRight">${cnt.area}平方米(项)</td>
					</tr>
					<tr>
						<%-- <td class="tdLeft">房产(项目)性质</td>
						<td class="tdRight">${cnt.houseKindName}</td>
						<td class="tdLeft">自助银行名称</td>
						<td class="tdRight">${cnt.autoBankName}</td> --%>
						<td class="tdLeft">房产(项目)性质</td>
						<td class="tdRight" <c:if test="${cnt.houseKindId=='1'}">colspan="1"</c:if><c:if test="${cnt.houseKindId!='1'}">colspan="3"</c:if>>
							<forms:codeName tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" 
								 conditionStr="CATEGORY_ID = 'HOUSE_KIND'" selectedValue="${cnt.houseKindId}"/>	
						</td>
						<c:if test="${cnt.houseKindId=='1'}">
							<td class="tdLeft">自助银行名称</td>
							<td class="tdRight">${cnt.autoBankName}</td>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft">租金递增条件 </td>
						<td class="tdRight" colspan="3">
								
							<c:forEach items="${cnt.tenanciesList}" var="tenanciesItem">
								<table>
									<tr class="collspan-con" style="cursor: pointer;" onclick="toggleTr(this)">
										<th colspan="6">
											<div style="display: block; float: center; ">${tenanciesItem.matrName }</div>
										</th>
									</tr>
									<tr>
										<td>序号</td>
										<td>开始日期</td>
										<td>结束日期</td>
										<td>合同总金额</td>
										<td>不含税金额</td>
										<td>税额</td>
									</tr>
									<c:forEach items="${tenanciesItem.list}" var="tcyItem" varStatus="status">
										<tr>
											<td><span name="tdSpan">${status.index+1}</span></td>
											<td><span name="fromDateShow">${tcyItem.fromDate}</span></td>
											<td><span name="toDate">${tcyItem.toDate}</span></td>
											<td><span name="cntAmtTr">${tcyItem.cntAmtTr}</span>&nbsp;元/月</td>
											<td><span name="execAmtTr">${tcyItem.execAmtTr}</span>&nbsp;元/月</td>
											<td><span name="taxAmtTr">${tcyItem.taxAmtTr}</span>&nbsp;元/月</td>
										</tr>
									</c:forEach>
								</table>
							</c:forEach>
								
							<c:if test="${empty cnt.tenanciesList}">
								<table>
									<tr>
										<td colspan="6" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
									</tr>
								</table>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">备注</td>
						<td class="tdRight" colspan="3">${cnt.remark}</td>
					</tr>
					</c:if>
					</c:if>
					
					<c:if test="${cnt.feeType eq '1'}">
					<tr>
						<td class="tdLeft">费用类型
						</td>
						<td class="tdRight">
							${cnt.feeTypeName}	
						</td>
					</tr>
					<tr>
						<td class="tdLeft">合同受益起始日期</td>
						<td class="tdRight">${cnt.feeStartDate}</td>
						<td class="tdLeft">合同受益终止日期</td>
						<td class="tdRight">${cnt.actualFeeEndDate}</td>
					</tr>
					<tr>
						<td class="tdLeft">实际合同受益终止日期区间</td>
						<td class="tdRight">${cnt.feeStartDateShow} 至  ${cnt.feeEndDateShow}</td>
						<td class="tdLeft"></td>
						<td class="tdRight"></td>
					</tr>
					<tr>
						<td class="tdLeft">合同金额（人民币）确定部分</td>
						<td class="tdRight">${cnt.feeAmt}</td>
						<td class="tdLeft">合同金额（人民币）约定罚金</td>
						<td class="tdRight">${cnt.feePenalty}</td>
					</tr>
					</c:if>
					
					<c:if test="${cnt.feeType eq '2'}">
					<tr>
						<td class="tdLeft">费用类型</td>
						<td class="tdRight">${cnt.feeTypeName}</td>
					</tr>
					</c:if>
					
				</table>
			</td>
		</tr>
		</c:if>
		<tr>
			<td>
				<c:if test="${!empty cnt.id}">
					<input type="button" value="查看合同扫描文件" onclick="viewCntScanImg('${cnt.id}','${cnt.icmsPkuuid }')"/>
				</c:if>
				<input type="button" value="合同复核" onclick="toAudit();"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
			</td>
		</tr>
	</table>
</form>
<jsp:include page="audit.jsp"></jsp:include>
</body>
</html>