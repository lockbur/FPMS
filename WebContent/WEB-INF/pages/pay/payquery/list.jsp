<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>付款查询</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#ouCode").combobox();
	$("#isOrder").combobox();
	$("#isCreditNote").combobox();
	$("#payDataFlag").combobox();
	$("#dataFlagInvoice").combobox();
	$("#dataFlagPay").combobox();
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function selProvider(){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商选择",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#providerName").val(object.providerName);
					}
					}		
			}
		 );
}
function showDetail(payId,isPrePay,cntNum,isCreditNote,invoiceIdBlue){
	var form = $("#queryDetailForm");
 	$("#queryDetailForm #payId1").val(payId);
	$("#queryDetailForm #isPrePay1").val(isPrePay);
	$("#queryDetailForm #cntNum1").val(cntNum);
	$("#queryDetailForm #isCreditNote1").val(isCreditNote);
	$("#queryDetailForm #invoiceIdBlue1").val(invoiceIdBlue);
 	form.attr('action', '<%=request.getContextPath()%>/pay/payquery/detail.do?<%=WebConsts.FUNC_ID_KEY%>=03030703');
	App.submit(form);
}
function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
function exportData(){
	var isPass=true;
	var data = {};
	data['orgFlag'] =  "${selectInfo.orgFlag}";
	data['payId'] =  $("#payId").val();
	data['cntName'] = $("#cntName").val();
	data['cntNum'] = $("#cntNum").val();
	data['cntType'] = $("#cntType").val();
	data['invoiceId'] = $("#invoiceId").val();
	data['payDataFlag'] = $("#payDataFlag").val();
	data['dataFlagInvoice'] = $("#dataFlagInvoice").val();
	data['dataFlagPay'] = $("#dataFlagPay").val();
	data['providerName'] = $("#providerName").val();
	data['befDate'] = $("#befDate").val();
	data['aftDate'] = $("#aftDate").val();
	data['projName'] = $("#projName").val();
	data['conCglCode'] = $("#conCglCode").val();
	App.ajaxSubmit("pay/payquery/exportData.do?<%=WebConsts.FUNC_ID_KEY %>=03030714",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(flag){
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						title: "跳转至下载页面",
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
							        	 	var form = document.forms[0];
							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId=010302';
											App.submit(form);
										}   
						         },
								{
									text:"取消",
									click:function(){
										$(this).dialog("close");
									}
								}
						]
					});
					isPass =  true;
				}
				else
				{
					App.notyError("添加下载失败，可能是因为表里没有该条数据，请检查后重试!");
					isPass =  false;
				}
			});
	return isPass;
}
//项目弹出页
function projOptionPage(){
	var orgFlag="${selectInfo.orgFlag}";
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projOptionPage.do?<%=WebConsts.FUNC_ID_KEY %>=021004&orgFlag="+orgFlag+"&isContract="+0;
	$.dialog.open(
			url,
			{
				width: "50%",
				height: "50%",
				lock: true,
			    fixed: true,
			    title:"项目选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('projectObj'); 
					if(proObj){
						$("#projName").val(proObj.projName);
					}
				}
			}
		 );
}
</script>
</head>
<body>
<p:authFunc funcArray="03030701,03030711,03030712,03030713"/>
<form action="" method="post" id="queryDetailForm">
	<input type="hidden" id="payId1" name="payId"  class="base-input-text"/>
	<input type="hidden" id="isPrePay1" name="isPrePay"  class="base-input-text"/>
	<input type="hidden" id="cntNum1" name="cntNum"  class="base-input-text"/>
	<input type="hidden" id="isCreditNote1" name="isCreditNote"  class="base-input-text"/>
	<input type="hidden" id="invoiceIdBlue1" name="invoiceIdBlue"  class="base-input-text"/>
</form>
<form  method="post" id="tempForm">
<input type="hidden" id="isPrePay" name="isPrePay">
	<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				付款查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text"  maxlength="80"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
            	<div class="ui-widget">
					<select id="cntType" name="cntType"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='CNT_TYPE'" selectedValue="${selectInfo.cntType}"/>
					</select>
				</div>	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">付款单号</td>
			<td class="tdRight">
				<input type="text" id="payId" name="payId" value="${selectInfo.payId}" class="base-input-text" maxlength="20"/>&nbsp;&nbsp;	
			</td>
			<%-- <td class="tdLeft">创建机构</td>
			<td class="tdRight">
				<forms:OrgSelPlugin   rootLevel="0" jsVarGetValue="createDept" rootNodeId="${org1Code}" initValue="${selectInfo.createDept}"/>
				<forms:OrgSelPlugin suffix="c" rootNodeId="${org1Code}" initValue="${selectInfo.createDept}" jsVarGetValue="createDept" parentCheckFlag="false"/>
			</td> --%>
			<td class="tdLeft">发票编号</td>
			<td class="tdRight">
				<input type="text" id="invoiceId" name="invoiceId" value="${selectInfo.invoiceId}" class="base-input-text" maxlength="50"/>&nbsp;&nbsp;	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName" value="${selectInfo.providerName}" class="base-input-text" maxlength="300"/>	
				<a href="#" onclick="selProvider()">
				<img  height="100%" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="查看供应商" style="border:0px;cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>	
			</td>
			<td class="tdLeft">付款日期区间</td>
			<td class="tdRight">
				<input id="befDate"  name="befDate" style="width: 35%;" readonly="readonly" value="${selectInfo.befDate}" class="base-input-text"/>至
				<input id="aftDate"  name="aftDate" style="width: 35%;" readonly="readonly" value="${selectInfo.aftDate}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">付款单状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="payDataFlag" name="payDataFlag"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='PAY_DATA_FLAG'" selectedValue="${selectInfo.payDataFlag}"/>
					</select>
				</div>	
			</td>
			<td class="tdLeft">项目名称</td>
			<td class="tdRight">
				<input type="text" id="projName" name="projName" value="${selectInfo.projName}" class="base-input-text" maxlength="200" />
				<a onclick="projOptionPage()">
					<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="查看合同项目" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">付款状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataFlagPay" name="dataFlagPay"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='DATA_FLAG_PAY'" selectedValue="${selectInfo.dataFlagPay}"/>
					</select>
				</div>	
			</td>
			<td class="tdLeft">发票状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataFlagInvoice" name="dataFlagInvoice"   class="erp_cascade_select">
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="DESC"  conditionStr="CATEGORY_ID='DATA_FLAG_INVOICE'" selectedValue="${selectInfo.dataFlagInvoice}"/>
					</select>
				</div>	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">核算码</td>
			<td class="tdRight">
				<input type="text" id="conCglCode" name="conCglCode" value="${con.conCglCode}" class="base-input-text" maxlength="200" />
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight">
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td class="tdLeft">是否订单</td> -->
<!-- 			<td class="tdRight"> -->
<!-- 			    <div class="ui-widget"> -->
<!-- 					<select id="isOrder" name="isOrder"   class="erp_cascade_select"> -->
<!-- 						<option value="">-请选择-</option> -->
<%-- 						<option value="0" <c:if test="${selectInfo.isOrder==0}">selected="selected"</c:if>>是</option> --%>
<%-- 						<option value="1" <c:if test="${selectInfo.isOrder==1}">selected="selected"</c:if>>否</option> --%>
<!-- 					</select> -->
<!-- 				</div>	 -->
<!-- 			</td> -->
<!-- 			<td class="tdLeft">是否贷项通知单</td> -->
<!-- 			<td class="tdRight"> -->
<!-- 				<div class="ui-widget"> -->
<!-- 					<select id="isCreditNote" name="isCreditNote"   class="erp_cascade_select"> -->
<!-- 						<option value="">-请选择-</option> -->
<%-- 						<option value="0" <c:if test="${selectInfo.isCreditNote==0}">selected="selected"</c:if> >是</option> --%>
<%-- 						<option value="1" <c:if test="${selectInfo.isCreditNote==1}">selected="selected"</c:if>>否</option> --%>
<!-- 					</select> -->
<!-- 				</div>	 -->
<!-- 			</td> -->
<!-- 		</tr>	 -->
		<tr>
			<td colspan="4" class="tdWhite">
				<c:if test="${selectInfo.orgFlag=='1'}">
					<p:button funcId="03030711" value="查询"/>
				</c:if>
				<c:if test="${selectInfo.orgFlag=='2'}">
					<p:button funcId="03030712" value="查询"/> 
				</c:if>
				<c:if test="${selectInfo.orgFlag=='3'}">
					<p:button funcId="03030713" value="查询"/>
				</c:if>
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="导出付款信息" onclick="exportData();">
			</td>
		</tr>
	</table>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
			 	<th width="9%">合同号</th>
				<th width="8%">合同事项</th>
				<th width="7%">合同类型</th>
				<th width="7%">付款单号</th>
				<th width="7%">付款日期</th>
				<th width="6%">付款类型</th>
				<th width="6%">发票金额(元)</th>
				<th width="8%">付款累计金额(元)(不含暂收)</th>
				<th width="8%">暂收金额</th>
				<th width="7%">暂收结清金额</th>
				<th width="8%">收款单位</th>
				<th width="6%">付款单状态</th>
				<th width="6%">发票状态</th>
				<th width="6%">付款状态</th>
				<th width="6%">操作</th>
			</tr>
			 <c:forEach items="${list}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc" ><a href="javascript:void(0);" onclick="gotoCntDtl('${sedList.cntNum }');" class="text_decoration">${sedList.cntNum }</a></td>
					<td class="tdc" ><c:out value="${sedList.cntName}"></c:out></td>
					<td class="tdc" >${sedList.cntTypeName }<c:if test="${sedList.isOrder == 1}">/非订单</c:if><c:if test="${sedList.isOrder == 0}">/订单</c:if></td>
					<td class="tdc" ><c:out value="${sedList.payId}"></c:out></td>
					<td class="tdc" ><c:out value="${sedList.payDate}"/></td>
					<td >
						<c:if test="${sedList.isPrePay=='Y'}">
							<c:out value="预付款"/>
						</c:if>
						<c:if test="${sedList.isPrePay=='N'}">
							<c:if test="${sedList.isCreditNote== '0'}">
								贷项通知单
							</c:if>
							<c:if test="${sedList.isCreditNote== '1'}">
								正常付款
							</c:if>
						</c:if>
					</td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.invoiceAmt}" minFractionDigits="2"/></td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.payTotalAmt}" minFractionDigits="2"/></td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.suspenseAmt}" minFractionDigits="2"/></td>
					<td class="tdr" ><fmt:formatNumber type="number" value="${sedList.susTotalAmt}" minFractionDigits="2"/></td>
					<td><c:out value="${sedList.providerName}"/></td>
					<td>
						<c:out value="${sedList.payDataFlag}"/>
					</td>
					<td>
						<c:out value="${sedList.dataFlagInvoice}"/>
					</td>
					<td>
						<c:out value="${sedList.dataFlagPay}"/>
					</td>
						<td>
							<div class="detail">
								<a href="#" onclick="showDetail('${sedList.payId}','${sedList.isPrePay}','${sedList.cntNum}','${sedList.isCreditNote}','${sedList.invoiceIdBlue}')" title="明细"></a>
							</div>
						</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
					<tr><td style="text-align: center;" colspan="100"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
</body>
</html>