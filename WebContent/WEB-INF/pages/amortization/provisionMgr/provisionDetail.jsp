<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预提信息详情页面</title>
	
<script type="text/javascript">
	//页面初始化执行加载
	function pageInit(){
		App.jqueryAutocomplete();
	 	$("#fileType").combobox();
	}
	
	$(function(){
		
	})
	
	
	//日期时间格式化方法(支持无字符串分隔的源格式，如20150601)
	function dateTimeFormat(formatStr , formatType){
		if(formatType == '1'){
			//日期格式化
			return formatStr.substr(0,4)+"-"+formatStr.substr(4,6)+"-"+formatStr.substr(6,8);
		}else if(formatType == '2'){
			//时间格式化
			return formatStr.substr(0,2)+":"+formatStr.substr(2,4)+":"+formatStr.substr(4,6);
		}
	}

</script>
</head>
<body>
<p:authFunc funcArray=""/>
<form action="" method="post" id="provDetailForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="5">
				预提信息详情
				<input type="hidden" value="${provision.provMgrId}">
			</th>
		</tr>
		
		<tr>
			<td class="tdLeft" width="20%">合同号</td>
			<td class="tdRight" width="20%">${provision.cntNum}</td>
			
			
			<td class="tdLeft" width="20%">受益年月</td>
			<td class="tdRight" width="30%">${provision.feeYyyymm}</td>
		</tr>
		
		<tr>
			<td class="tdLeft">核算码</td>
			<td class="tdRight">${provision.feeCglCode}</td>
			<td class="tdLeft">预提金额</td>
			<td class="tdRight">${provision.provisionAmt}</td>
		</tr>
		
		<tr>
			<td class="tdLeft">待摊金额</td>
			<td class="tdRight">${provision.prepaidAmt}</td>
			<td class="tdLeft">是否预提</td>
			<td class="tdRight">
				<c:if test="${provision.provFlag == '0'}">不需要预提</c:if>
				<c:if test="${provision.provFlag == '1'}">需要预提</c:if>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">预提状态</td>
			<td class="tdRight">
				<c:if test="${provision.dataFlag == '0'}">待处理</c:if>
				<c:if test="${provision.dataFlag == '1'}">待复核</c:if>
				<c:if test="${provision.dataFlag == '2'}">复核退回</c:if>
				<c:if test="${provision.dataFlag == '3'}">复核通过</c:if>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">录入用户</td>
			<td class="tdRight">${provision.operUser}</td>
			<td class="tdLeft">录入时间</td>
			<td class="tdRight">${provision.operDate}&nbsp;${provision.operTime}</td>
		</tr>
		
		<!-- 当预提状态为"非待预提"时，展示该预提的复核信息(否则该预提复核信息为空，就不展示) -->
		<c:if test="${provision.dataFlag != '0'}">
			<tr>
				<td class="tdLeft">复核用户</td>
				<td class="tdRight">${provision.checkUser}</td>
				<td class="tdLeft">复核时间</td>
				<td class="tdRight">${provision.checkDate}&nbsp;${provision.checkTime}</td>
			</tr>
		</c:if>
		
		<tr>
			<td colspan="4" style="text-align: center">
				<input type="button" value="返回" onclick="backToLastPage('provQueryMgr.do?VISIT_FUNC_ID=040505');">
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>