<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同详情</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">

// 根据合同号+付款单号查询付款的明细信息(包括正常付款和预付款核销的物料信息列表)
function getPayDetail(payId , payType , batchNo , cntNum){
	var url ="<%=request.getContextPath()%>/reportmgr/dataMgr/datamigrate/importPayDetail.do?<%=WebConsts.FUNC_ID_KEY%>=06020702&payType="+payType+"&payId="+payId+"&batchNo="+batchNo;
	$.dialog.open(
			url,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"导入-正常付款详情",
			    id:"dialogCutPage",
			    close:function(){
			    	
			    }
			}
	);
}


$(function(){
	$(window).css("overflow-X","hidden");
	$("#scrollTableDiv").css("width",$("#conTable").width());
})
</script>
</head>

<body>
<p:authFunc funcArray="030206,03020601"/>
<form action="" method="post" id="queryForm" action="<%=request.getContextPath()%>/contract/query/queryList.do?<%=WebConsts.FUNC_ID_KEY%>=030206">
	<table>
		<tr>
			<td>
				<table class="tableList" id="conTable">
					<tr class="collspan-control">
						<th colspan="4">
							合同基础信息（合同编号：${cntRelateBean.cntNum}）
							<input type="hidden" id="batchNo" name="batchNo" value="${cntRelateBean.batchNo}">
						</th>
					</tr>
					<tr>
						<td class="tdLeft">合同事项</td>
						<td class="tdRight">${cntRelateBean.cntName}</td>
						<td class="tdLeft">合同类型</td>
						<td class="tdRight">
							<c:if test="${cntRelateBean.cntType eq '0'}">资产类</c:if>
							<c:if test="${cntRelateBean.cntType eq '1'}">费用类</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdLeft">付款责任中心</td>
						<td class="tdRight" title="责任中心代码编号：${cntRelateBean.payDutyCode}">${cntRelateBean.payDutyName}</td>
<!-- 						<td class="tdLeft">业务发起部门</td> -->
<%-- 						<td class="tdRight">${cntRelateBean.createDeptName}</td> --%>
						<td class="tdLeft">签订日期</td>
						<td class="tdRight">${cntRelateBean.signDate}</td>
					</tr>
					<tr>
						<td class="tdLeft">供应商</td>
						<td class="tdRight" title="供应商账号：${cntRelateBean.provActNo}">${cntRelateBean.providerName}</td>
						<td class="tdLeft">供应商币别</td>
						<td class="tdRight">${cntRelateBean.provActCurr}</td>
					</tr>
					
					<tr><td colspan="4"></td></tr>
					<tr>
						<td class="tdLeft">合同金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cntRelateBean.cntAmt}"/> &nbsp; ${cntRelateBean.provActCurr}</td>
						<td class="tdLeft">其中合同质保金(%)</td>
						<td class="tdRight">${cntRelateBean.zbAmt * 100}%</td>
					</tr>
					<tr>
						<td class="tdLeft">审批金额</td>
						<td class="tdRight"><fmt:formatNumber type="number" value="${cntRelateBean.lxje}"/> &nbsp; ${cntRelateBean.provActCurr}</td>
						<td class="tdLeft">审批数量</td>
						<td class="tdRight">${cntRelateBean.lxsl}</td>
					</tr>
					<tr>
						<td class="tdLeft">专项包</td>
						<td class="tdRight">
							<c:if test="${'0'==cntRelateBean.isSpec}">是</c:if>
							<c:if test="${'1'==cntRelateBean.isSpec}">否</c:if>
						</td>
						<c:if test="${cntRelateBean.cntType == '0' && cntRelateBean.isSpec == '1' }">
							<td class="tdLeft">省行统购</td>
							<td class="tdRight" colspan="3">
								<c:if test="${'0'==cntRelateBean.isProvinceBuy}">是</c:if>
								<c:if test="${'1'==cntRelateBean.isProvinceBuy}">否</c:if>
							</td>
						</c:if>
					</tr>
					<tr>
						<td class="tdLeft">集采编号</td>
						<td class="tdRight">${cntRelateBean.stockNum}</td>
						<td class="tdLeft">评审编号</td>
						<td class="tdRight">${cntRelateBean.psbh}</td>
<!-- 						<td class="tdLeft">合同状态</td> -->
<%-- 						<td class="tdRight">${cntRelateBean.dataFlagName}</td> --%>
					</tr>
					<tr>
						<td class="tdLeft">关联合同号</td>
						<td class="tdRight">
							<c:if test="${empty cntRelateBean.cntNumRelated}">
								无
							</c:if>
							<c:if test="${not empty cntRelateBean.cntNumRelated}">
								${cntRelateBean.cntNumRelated}
							</c:if>
						</td>
						<td class="tdLeft">审批类别</td>
						<td class="tdRight">${cntRelateBean.lxlx}</td>
<!-- 						<td class="tdLeft">签报文号</td> -->
<%-- 						<td class="tdRight">${cntRelateBean.qbh}</td> --%>
					</tr>
					<tr>
						<td class="tdLeft">增值税率</td>
						<td class="tdRight">
							${cntRelateBean.providerTaxRate * 100}%
						</td>
						<td class="tdLeft">增值税金</td>
						<td class="tdRight">
							<fmt:formatNumber type="number" value="${cntRelateBean.providerTax }"/>
						</td>
					</tr>
					
					<tr>
						<td class="tdLeft">付款条件</td>
						<td class="tdRight">
							<c:if test="${'0' eq cntRelateBean.payTerm}">合同签订后一次性付款</c:if>
							<c:if test="${'1' eq cntRelateBean.payTerm}">货到验收后一次性付款</c:if>
							<c:if test="${'2' eq cntRelateBean.payTerm}">合同完毕后一次性付款</c:if>
							<c:if test="${'3' eq cntRelateBean.payTerm}">分期付款   (${cntRelateBean.stageType})</c:if>
						</td>
						<td class="tdLeft">是否生成订单</td>
						<td class="tdRight">
							<c:if test="${'0'==cntRelateBean.isOrder}">是</c:if>
							<c:if test="${'1'==cntRelateBean.isOrder}">否</c:if>
						</td>
					</tr>
					<c:if test="${'3' eq cntRelateBean.payTerm}">
						<c:if test="${'按进度' eq cntRelateBean.stageType}">
							<tr>
								<td class="tdLeft">按进度分期付款</td>
								<td class="tdRight" colspan="3">
									<table>
										<c:forEach items="${cntRelateBean.cntRelFqfkList}" var="cntJdfkItem" varStatus="status">
											<tr>
												<td>第${status.index+1}期 - [付款年月]:${cntJdfkItem.jdDate}  [付款比例]:${cntJdfkItem.jdtj * 100 }%  [付款金额]:支付${cntJdfkItem.jdzf}元</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>	
						</c:if>
						<c:if test="${'按日期' eq cntRelateBean.stageType}">
							<tr>
								<td class="tdLeft">按日期分期付款</td>
								<td class="tdRight" colspan="3">
									<table>
										<c:forEach items="${cntRelateBean.cntRelFqfkList}" var="cntRqfqItem">
											<tr>
												<td>合同签订后 ${cntRqfqItem.rqtj}天 支付<fmt:formatNumber type="number" value="${cntRqfqItem.rqzf}"/>元</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>	
						</c:if>
						<c:if test="${'按条件' eq cntRelateBean.stageType}">
							<tr>
								<td class="tdLeft">按条件分期付款</td>
								<td class="tdRight" colspan="3">
									<!-- 需处理 -->
									到货支付<fmt:formatNumber type="number" value="${cntRelateBean.cntRelFqfkList[0].dhzf}"/>元；    
									验收支付<fmt:formatNumber type="number" value="${cntRelateBean.cntRelFqfkList[0].yszf}"/>元； 
									结算支付<fmt:formatNumber type="number" value="${cntRelateBean.cntRelFqfkList[0].jszf}"/>元； 
								</td>
							</tr>	
						</c:if>
					</c:if>
					<tr>
						<td class="tdLeft">合同备注</td>
						<td class="tdRight" colspan="3">${cntRelateBean.memo}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td></td></tr>
		<tr>
			<td>
				<table class="tableList">
					
			<!-- 当合同的类型为"费用类"时，才会出现下述房屋租赁相关信息 -->
					<c:if test="${cntRelateBean.cntType eq '1'}">
						<tr class="collspan-control">
							<th colspan="4">
								合同费用信息
							</th>
						</tr>
						<c:if test="${cntRelateBean.feeType eq '0'}">
							<tr>
								<td class="tdLeft">费用类型</td>
								<td class="tdRight">
									<c:if test="${cntRelateBean.feeType eq '0'}">金额固定、受益期固定</c:if>
									<c:if test="${cntRelateBean.feeType eq '1'}">受益期固定、合同金额不固定</c:if>
									<c:if test="${cntRelateBean.feeType eq '2'}">其他</c:if>
								</td>
								<td class="tdLeft">费用子类型</td>
								<td class="tdRight">
									<c:if test="${cntRelateBean.feeSubType eq '0'}">普通费用类</c:if>
									<c:if test="${cntRelateBean.feeSubType eq '1'}">房屋租赁类</c:if>
								</td>
							</tr>
							<tr>
								<td class="tdLeft">合同受益起始日期</td>
								<td class="tdRight">${cntRelateBean.feeStartDate}</td>
								<td class="tdLeft">合同受益终止日期</td>
								<td class="tdRight">${cntRelateBean.feeEndDate}</td>
							</tr>
						<c:if test="${cntRelateBean.feeSubType eq '1'}">
							<tr>
								<td class="tdLeft">甲方名称</td>
								<td class="tdRight">${cntRelateBean.jf}</td>
								<td class="tdLeft">乙方名称</td>
								<td class="tdRight" title="乙方编号：${cntRelateBean.yfId}">${cntRelateBean.yf}</td>
							</tr>
							<tr>
								<td class="tdLeft">地址(说明)</td>
								<td class="tdRight" colspan="3">${cntRelateBean.wydz}</td>
							</tr>
							<tr>
								<%-- <td class="tdLeft">管理部门</td>
								<td class="tdRight">${cnt.glbm}</td> --%>
								<td class="tdLeft">网点机构</td>
								<td class="tdRight" title="网点机构编号：${cntRelateBean.wdjgId}">${cntRelateBean.wdjgName}</td>
								<td class="tdLeft">自助银行名称</td>
								<td class="tdRight" colspan="3">${cntRelateBean.autoBankName}</td>
							</tr>
							<tr>
								<td class="tdLeft">租赁面积(数量)</td>
								<td class="tdRight">${cntRelateBean.area}平方米(项)</td>
								<td class="tdLeft">房产(项目)性质</td>
								<td class="tdRight">
									<c:if test="${cntRelateBean.houseKindId eq '0'}">网点营业用房</c:if>
									<c:if test="${cntRelateBean.houseKindId eq '1'}">自主银行用房</c:if>
								</td>
							</tr>
							<tr>
								<td class="tdLeft">管理费及其他</td>
								<td class="tdRight">${cntRelateBean.wyglf}元/月 </td>
								<td class="tdLeft">押金</td>
								<td class="tdRight">${cntRelateBean.yj}元</td>
							</tr>
							<tr>
								<td class="tdLeft">执行开始日期</td>
								<td class="tdRight">${cntRelateBean.beginDate}</td>
								<td class="tdLeft">执行结束日期</td>
								<td class="tdRight">${cntRelateBean.endDate}</td>
							</tr>
							<tr>
								<td class="tdLeft">租金递增条件 </td>
								<td class="tdRight" colspan="3">
									<table>
<!-- 												<tr><td ><font color="red">需处理</font></td></tr> -->
										<c:forEach items="${cntRelateBean.cntRelTenancyList}" var="tcyItem">
											<tr>
												<td >
													租金从[${tcyItem.fromDate}]至[${tcyItem.toDate}]
													${tcyItem.dzlx}${tcyItem.dzed}
<%-- 															<fmt:formatNumber type="number" value="${tcyItem.fdbl}"/> --%>
													<fmt:formatNumber type="number" value="${tcyItem.fdbl * 100 }"/> %
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													管理费及其他为：<fmt:formatNumber type="number" value="${tcyItem.glfy}"/>元/月 
												</td>
											</tr>
										</c:forEach>
										<c:if test="${empty cntRelateBean.cntRelTenancyList}">
											<tr>
												<td colspan="1" style="text-align: center;"><span class="red">无租金递增条件信息</span></td>
											</tr>
										</c:if>
									</table>
								</td>
							</tr>
							<tr>
								<td class="tdLeft">房屋租赁信息备注</td>
								<td class="tdRight" colspan="3">${cntRelateBean.fzRemark}</td>
							</tr>
							</c:if>
						</c:if>
								
						<c:if test="${cntRelateBean.feeType eq '1'}">
							<tr>
								<td class="tdLeft">费用类型
<%-- 									<c:if test="${cntRelateBean.isOrder == 1 }"> --%>
<%-- 										<img border='0' id='feeInfoImg' alt='费用信息' width='25px' src='<%=request.getContextPath()%>/common/images/search.jpg' onclick='queryFeeTypePage("${csnt.cntNum}")' align="center"/> --%>
<%-- 									</c:if> --%>
								</td>
								<td class="tdRight">${cntRelateBean.feeType}受益期固定、合同金额不固定</td>
							</tr>
							<tr>
								<td class="tdLeft">合同受益起始日期</td>
								<td class="tdRight">${cntRelateBean.feeStartDate}</td>
								<td class="tdLeft">合同受益终止日期</td>
								<td class="tdRight">${cntRelateBean.feeEndDate}</td>
							</tr>
							<tr>
								<td class="tdLeft">合同金额（人民币）确定部分</td>
								<td class="tdRight">${cntRelateBean.feeAmt}</td>
								<td class="tdLeft">合同金额（人民币）约定罚金</td>
								<td class="tdRight">${cntRelateBean.feePenalty}</td>
							</tr>
						</c:if>
							
						<c:if test="${cntRelateBean.feeType eq '2'}">
							<tr>
								<td class="tdLeft">费用类型</td>
								<td class="tdRight">${cntRelateBean.feeType}受益期不固定、付款时确认费用</td>
							</tr>
						</c:if>
					</c:if>
					<tr>
						<td class="tdLeft" colspan="4">
							<div id="scrollTableDiv" style="width:900px; overflow-x:scroll;">
								<!-- 此table设置固定宽度和滚动条明确出现显示 -->
								<table style="width: 1430px; overflow: scroll;">	
									<tr class="collspan-control">
										<th colspan="8" id="myTh" style="border-right-style: none;">合同关联物料列表</th>
										<th colspan="3" style="border-left-style: none;"></th>
									</tr>
									<tr>
<!-- 										<th nowrap="nowrap" width="80px">项目</th> -->
										<th nowrap="nowrap" width="70px">序号</th>
										<th nowrap="nowrap" width="180px">费用承担部门</th>
										<th nowrap="nowrap" width="250px">物料类型</th>
<!-- 										<th nowrap="nowrap" width="85px">监控指标</th> -->
										<th nowrap="nowrap" width="90px">设备型号</th>
										<th nowrap="nowrap" width="200px">专项</th>
										<th nowrap="nowrap" width="140px">参考</th>
										<th nowrap="nowrap" width="70px">数量</th>
										<th nowrap="nowrap" width="80px">单价(元)</th>
										<th nowrap="nowrap" width="80px">金额(元)</th>
										<th nowrap="nowrap" width="100px">保修期(年)</th>
										<th nowrap="nowrap" width="180px">制造商</th>
<!-- 										<th nowrap="nowrap" width="200px">审批意见</th> -->
<!-- 										<th nowrap="nowrap" width="150px">审批部门</th> -->
									</tr>
									<c:forEach items="${cntRelateBean.cntRelDeviceList}" var="deviceItem" varStatus="vs">
										<tr>
<%-- 											<td>${deviceItem.projName}</td> --%>
											<td class="tdc">${vs.index+1}</td>
											<td>${deviceItem.feeDeptName}</td>
											<td>${deviceItem.matrName}</td>
<%-- 											<td>${deviceItem.montName}</td> --%>
											<td class="tdc">${deviceItem.deviceModelName}</td>
											<td>
												<c:if test="${deviceItem.special eq '0'}">0</c:if>
												<c:if test="${deviceItem.special eq '1'}">个人金融总部第二次"开门红"营销费用</c:if>
												<c:if test="${deviceItem.special eq '2'}">个人金融总部第二次"开门红"营销费用</c:if>
												<c:if test="${deviceItem.special eq '3'}">夏季竞赛专线营销费用</c:if>
												<c:if test="${deviceItem.special eq '4'}">公司金融总部"开门红"营销费用</c:if>
												<c:if test="${deviceItem.special eq '5'}">燎原行动网点专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '6'}">重点城市行专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '7'}">代发薪专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '8'}">海内外联动专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '9'}">私人银行系列营销活动专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '10'}">商会客户源头营销活动专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '11'}">"融动校园"专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '12'}">手机银行营销活动专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '13'}">专业市场营销活动专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '14'}">"大众客户"专项营销费用</c:if>
												<c:if test="${deviceItem.special eq '15'}">金融IC卡专项营销费用</c:if>
											</td>
									   		<td>
									   			<c:if test="${deviceItem.reference eq '0'}">0</c:if>
									   			<c:if test="${deviceItem.reference eq '1'}">集体福利支出</c:if>
									   			<c:if test="${deviceItem.reference eq '2'}">S6475</c:if>
									   			<c:if test="${deviceItem.reference eq '3'}">教育经费</c:if>
									   			<c:if test="${deviceItem.reference eq '4'}">工会经费</c:if>
									   			<c:if test="${deviceItem.reference eq '5'}">劳务用工基本养老保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '6'}">劳务用工基本医疗保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '7'}">劳务用工失业保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '8'}">劳务用工工伤保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '9'}">劳务用工生育保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '10'}">编内职工固定性工资</c:if>
									   			<c:if test="${deviceItem.reference eq '11'}">编内外职工基本养老保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '12'}">编内外职工住房公积金</c:if>
									   			<c:if test="${deviceItem.reference eq '13'}">编内职工浮动性工资</c:if>
									   			<c:if test="${deviceItem.reference eq '14'}">个人福利支出</c:if>
									   			<c:if test="${deviceItem.reference eq '15'}">编内外职工基本医疗保险金</c:if>
									   			<c:if test="${deviceItem.reference eq '16'}">编内职工加班费</c:if>
									   			<c:if test="${deviceItem.reference eq '17'}">编内外职工失业保险金</c:if>
									   		</td>
											<td class="tdc">${deviceItem.execNum}</td>
											<td class="tdc"><fmt:formatNumber type="number" value="${deviceItem.execPrice}"/></td>
											<td class="tdc"><fmt:formatNumber type="number" value="${deviceItem.execPrice * deviceItem.execNum}"/></td>
											<td class="tdc">${deviceItem.warranty}</td>
											<td class="tdc">${deviceItem.productor}</td>
<%-- 											<td>${deviceItem.auditMemo}</td> --%>
<!-- 											<td> -->
<%-- 												${deviceItem.auditDeptName} --%>
<!-- 											</td> -->
										</tr>
									</c:forEach>
									
									<c:if test="${empty cntRelateBean.cntRelDeviceList}">
										<tr>
											<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
										</tr>
									</c:if>
								</table>
							</div>
						</td>
					</tr>
					
					
				</table>
			</td>
		</tr>
	
		
		<!-- HQQ-添加当前合同的相关付款信息 -->
		<tr>
			<td>
				<table class="tableList">
					<tr class="collspan-control">
						<th colspan="9">
							合同付款信息 
						</th>
					</tr>
					<tr>
						<th>序号</th>
						<th>付款单ID</th>
						<th>付款类型</th>
						<th>供应商名称</th>
						<th>发票编号</th>
						<th>发票金额</th>
						<th>付款金额</th>
						<th>付款状态</th>
						<th>操作</th>
					</tr>
					
					
					<c:forEach items="${cntRelateBean.cntRelPayInfoList }" var="payInfo" varStatus="status">
						<tr>
							<td style="text-align: center;">${status.index+1 }</td>	
							<td style="text-align: center;">${payInfo.payId }</td>	
							<td style="text-align: center;">
								<c:if test="${'01' eq payInfo.payType }">正常付款</c:if>
								<c:if test="${'02' eq payInfo.payType }">预付款</c:if>
							</td>	
							<td style="text-align: center;">${payInfo.providerName }</td>	
							<td style="text-align: center;">${payInfo.invoiceCode }</td>	
							<td style="text-align: center;">${payInfo.invoiceAmt }</td>	
							<td style="text-align: center;">${payInfo.payAmt }</td>	
							<td style="text-align: center;">${payInfo.status }</td>	
							<td>
								<div class="detail">
										<a href="#" onclick="getPayDetail('${payInfo.payId }','${payInfo.payType }', '${cntRelateBean.batchNo }','${cntRelateBean.cntNum}');" title="详情"></a>
								</div>
							</td>	
						</tr>
					</c:forEach>
					
				</table>
			</td>
		</tr>
		
		<tr style="border-left-style: none;border-right-style: none;border-bottom-style: none;">
			<td>
				<input type="button" value="返回" onclick="backToLastPage('dataDetail.do?VISIT_FUNC_ID=060207&batchNo='+'${cntRelateBean.batchNo}');">
			</td>
<!-- 			<td colspan="4"> -->
<%-- 				<c:if test="${!empty cnt.id}"> --%>
<%-- 					<input type="button" value="查看扫描文件" onclick="viewscan('${cnt.id}','${cnt.icmsPkuuid }')"/> --%>
<%-- 				</c:if> --%>
<%-- 				<c:if test="${cnt.isOrder == '0'}"> --%>
<%-- 					<input type="button" value="查看订单信息" onclick="viewOrder('${cnt.cntNum}')"/> --%>
<%-- 				</c:if> --%>
<%-- 				<input type="button" value="返回" onclick="backToLastPage('${uri}');">   --%>
<!-- 			</td> -->
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>