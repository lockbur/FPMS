<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商详情</title>
<script type="text/javascript">
</script>
</head>
<body>
	<table >
		<tr>
			<th colspan="4">详情</th>
		</tr>
		<tr>
			<td class="tdRight"  colspan="4"><b>供应商编号:<c:out value="${providerInfo.providerCode}"></c:out></b></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="spanFont">供应商名称</span></td>
			<td class="tdRight">
				<c:out value="${providerInfo.providerName}"></c:out>
			</td>
			<td class="tdLeft"><span class="spanFont">供应商类型</span></td>
			<td class="tdRight">
				<c:out  value="${providerInfo.providerTypeName}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="spanFont">供应商地点</span></td>
			<td class="tdRight">
				<c:out value="${providerInfo.providerAddr}"></c:out>
			</td>
			<td class="tdLeft"><span class="spanFont">付款条件</span></td>
			<td class="tdRight">
				<c:out value="${providerInfo.payCondition}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="spanFont">所属财务中心代码</span></td>
			<td class="tdRight">
				<c:out value="${providerInfo.ouCode}"></c:out>
			</td>
			<td class="tdLeft"><span class="spanFont">所属财务中心名称</span></td>
			<td class="tdRight">
				<c:out value="${providerInfo.ouName}"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="spanFont">支付方式</span></td>
			<td class="tdRight">
				<c:out value="${providerInfo.payMode}"></c:out>
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight">
			</td>
		</tr>
	</table>
	<br>
	<table id="devList" class="tableList">
		<caption>
		银行卡号列表
		</caption>
		<tr >
			<th width="10%">银行账户编号</th>
			<th width="10%">银行账户币种 </th>
			<th width="10%">银行名称</th>
			<th width="10%">分行名称</th>
			<th width="10%">银行账户名称</th>
			<th width="10%">是否主要帐号 </th>
			<th width="10%">账户类型 </th>
			<th width="10%">开户行行号</th>
			<th width="10%">开户行详细信息</th>
			<th width="10%">银行地区码</th>
		</tr>
				<tr>
					<td  width="10%"><c:out value="${providerInfo.actNo}"></c:out></td>
					<td  width="10%"><c:out value="${providerInfo.actCurr}"></c:out></td>
					<td  width="10%" ><c:out value="${providerInfo.bankName}"/></td>
					<td  width="10%"><c:out value="${providerInfo.branchName}"/></td>
					<td  width="10%"><c:out value="${providerInfo.actName}"/></td>
					<td  width="10%">
						<c:if test="${providerInfo.isMasterAct=='Y'}">
			    	    	<c:out value="是"></c:out>
			   		    </c:if>
			            <c:if test="${providerInfo.isMasterAct=='N'}">
			    			<c:out value="否"></c:out>
			            </c:if>
					</td>
					<td  width="10%">
						<c:out value="${providerInfo.actTypeText}"/>
					</td>
					<td  width="10%"><c:out value="${providerInfo.bankCode}"/></td>
					<td  width="10%"><c:out value="${providerInfo.bankInfo}"/></td>
					<td  width="10%"><c:out value="${providerInfo.bankArea}"/></td>
				</tr>
	</table>
	<br>
	<div style="text-align:center;" >
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
	</div>	
</body>
</html>