<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单类发票异常数据列表</title>

<script type="text/javascript">
/**
 * 	页面初始化执行方法
 */
function pageInit(){
	App.jqueryAutocomplete();
 	$("#isDealed").combobox();
 	$("#org1CodeSearch").combobox();
}

function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function modify(cntNum,payId,payType){
	var form=$("#modifyForm")[0];
	form.action="<%=request.getContextPath()%>/amortization/payErrorData/modify.do?<%=WebConsts.FUNC_ID_KEY%>=04070201&cntNum="+cntNum+"&payId="+payId+"&payType="+payType;
	App.submit(form);
}
function detail(cntNum,payId,payType){
	var form=$("#detailForm")[0];
	form.action="<%=request.getContextPath()%>/amortization/payErrorData/detail.do?<%=WebConsts.FUNC_ID_KEY%>=04070202&cntNum="+cntNum+"&payId="+payId+"&payType="+payType;
	App.submit(form);
}
//处理
function deal(batchNo,seqNo){
	if(!confirm("是否确认标记为已处理？")){
		return false;
	}
	var url = '<%=request.getContextPath()%>/amortization/payErrorData/deal.do?<%=WebConsts.FUNC_ID_KEY%>=04070201';
	var orgFlag="${selectInfo.orgFlag}";
	url += "&batchNo="+batchNo+"&seqNo="+seqNo+"&orgFlag="+orgFlag;
	var form = $("#queryForm");
	form.attr('action', url);
	App.submit(form);
}
</script> 
</head>
<body>
<p:authFunc funcArray="04070203,04070204,04070205"/>
<form action="" method="post" id="modifyForm">
</form>
<form action="" method="post" id="detailForm">
</form>
<form action="" method="post" id="queryForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4" >订单类发票异常数据列表查询</th>
		</tr>
		<c:if test="${selectInfo.isCenterAdmin == '1' && selectInfo.orgFlag == '1'}">
			<tr>
				<td class="tdLeft" width="25%">一级行代码</td>
				<td class="tdRight" width="25%">
					<div class="ui-widget">
						<select name="org1CodeSearch" id="org1CodeSearch">
							<option value="">--请选择--</option>
							<c:forEach items="${orgList}" var="orginfo">
								<option value="${orginfo.org1Code }" ${orginfo.org1Code == selectInfo.org1CodeSearch ?  "selected='selected'" : ""} >${orginfo.org1Name }</option>
							</c:forEach> 
						</select>
					</div>
				</td>
				<td class="tdLeft" width="25%">一级行名称</td>
				<td class="tdRight" width="25%">
					<input  id="org1Name" name="org1Name" value="${payErrorDataBean.org1Name}" class="base-input-text" maxlength="80"/>
				</td>
			</tr>			
		</c:if>
		<tr>
			<td class="tdLeft" width="25%">合同号</td>
			<td class="tdRight" width="25%">
				<input  id="cntNum" name="cntNum" value="${payErrorDataBean.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft" width="25%">发票号</td>
			<td class="tdRight" width="25%">
				<input  id="invoiceId" name="invoiceId" value="${payErrorDataBean.invoiceId}" class="base-input-text" maxlength="80"/>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft" width="25%">付款单号</td>
			<td class="tdRight" width="25%">
				<input id="payId"  name="payId" value="${payErrorDataBean.payId}" class="base-input-text" maxlength="20"/>
			</td>
			<td class="tdLeft" width="25%">发票金额</td>
			<td class="tdRight" width="25%">
				<input id="invoiceAmt"  name="invoiceAmt"  onkeyup="$.clearNoNum(this);" value="${payErrorDataBean.invoiceAmt}" class="base-input-text" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">是否已处理</td>
			<td class="tdRight" width="25%">
				<div class="ui-widget">
					<select id="isDealed" name="isDealed" class="erp_cascade_select">
						<option value="">--请选择--</option>		
						<option value="Y" <c:if test="${payErrorDataBean.isDealed == 'Y'}">selected="selected"</c:if>>已处理</option>		
						<option value="N" <c:if test="${payErrorDataBean.isDealed == 'N'}">selected="selected"</c:if>>未处理</option>		
					</select>
				</div>
			</td>
			<td class="tdLeft" width="25%"></td>
			<td class="tdRight" width="25%">
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<c:if test="${selectInfo.orgFlag=='1'}">
					<p:button funcId="04070203" value="查询"/>
				</c:if>
				<c:if test="${selectInfo.orgFlag=='2'}">
					<p:button funcId="04070204" value="查询"/> 
				</c:if>
				<c:if test="${selectInfo.orgFlag=='3'}">
					<p:button funcId="04070205" value="查询"/>
				</c:if>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<tr>
			<th >合同号</th>
			<th >发票号</th>
			<th >付款单号</th>
			<th >发票金额（元）</th>
			<th >发票类型</th>
			<th >发票状态</th>
			<th >异常说明</th>
			<th >录入日期</th>
			<th >录入时间</th>
			<th >是否已处理</th>
			<th >操作</th>
		</tr>
		<c:if test="${!empty list }">
			<c:forEach items="${list }" var="bean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');">
					<td >
						<a href="javascript:void(0);" onclick="gotoCntDtl('${bean.cntNum}');" class="text_decoration">${bean.cntNum }</a>
					</td>
					<td >${bean.invoiceId }</td>
					<td >${bean.payId }</td>
					<td ><fmt:formatNumber type="number" value="${bean.invoiceAmt}" minFractionDigits="2"/></td>
					<td>
						${bean.invoiceType }
					</td>
					<td>
						<c:if test="${bean.invoiceCancelState == 'Y'}">已取消</c:if>
						<c:if test="${bean.invoiceCancelState == 'N'}">已创建</c:if>
					</td>
					<td>${bean.errMemo }</td>
					<td>${bean.instDate }</td>
					<td>${bean.instTime }</td>
					<td>
						<c:if test="${bean.isDealed == 'Y'}">已处理</c:if>
						<c:if test="${bean.isDealed == 'N'}">未处理</c:if>
					</td>
					<td>
						<c:if test="${bean.isDealed == 'N'}">
							<div class="update"  align="center">
						   	 	<a href="#" onclick="deal('${bean.batchNo }','${bean.seqNo }')" title="处理" ></a>	
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="15" style="text-align: center;"><span class="red">没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>