<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="tabs">
<title>已立项未签合同项目查询</title>
<script type="text/javascript">
function pageInit(){
	
	
	var initEvent = function(){
		$("#resetBtn").click(function(){
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
		});
	};
	
	initEvent();
	rowspanTableTd("listTab", "7", "1", "0");
	rowspanTableTd("listTab", "4", "1", "0,2");
	rowspanTableTd("listTab", "3", "1", "0,2");
	rowspanTableTd("listTab", "2", "1", "0");
	rowspanTableTd("listTab", "1", "1", "0");
	rowspanTableTd("listTab", "0", "1");
}

/**
 * 合并单元格 (如果一个表需要合并多个列，需要从后往前合并)
 * _tableId: 表名ID
 * _colIndex: 需要合同的列号
 * _startRow: 合并开始的行数
 * _otherCol: 合并时需要判断其它列是否相同的列号
 */
function rowspanTableTd(_tableId, _colIndex, _startRow, _otherCol) {
	if(!_colIndex) {
		return;
	}
	if(!_startRow) {
		return;
	}
	var tabObj = $("table[id='" + _tableId + "']");
	if(tabObj) {
		var checkTr = $(tabObj).find("tr:eq(" + _startRow + ")");
		var checkTrList = $(tabObj).find("tr:gt(" + _startRow +")");
		var checkNum = $(checkTrList).size();
		
		if( checkNum > 0 ) {
			var index = 1;
			for(var i = 0; i < checkNum; i++) {
				var otherColFlag = true;
				if(_otherCol) {
					var _otherCols = _otherCol.split(",");
					for(var j=0; j<_otherCols.length; j++) {
						if($(checkTrList[i]).find("td:eq(" + _otherCols[j] + ")").text() != $(checkTr).find("td:eq(" + _otherCols[j] + ")").text()) {
							otherColFlag = false;
							break;
						}
					}
				}
				if(otherColFlag && $(checkTrList[i]).find("td:eq(" + _colIndex + ")").text() == $(checkTr).find("td:eq(" + _colIndex + ")").text()) {
					index++;
					$(checkTrList[i]).find("td:eq(" + _colIndex + ")").remove();
				}else {
					break;
				}
			}
			if( index != 1 ) {
				$(checkTr).find("td:eq(" + _colIndex + ")").attr("rowspan", index);
			}
			_startRow = parseInt(_startRow) + index;
			rowspanTableTd(_tableId, _colIndex, _startRow, _otherCol);
		}
	}else {
		return;
	}
}
</script>
</head>
<body>
<p:authFunc funcArray="02020103"/>




		<form action="" method="post" id="unSignContSearchForm" >
		<p:token/>
			<table id="approveChainTable">
				<tr class="collspan-control"><th colspan="4">已立项未签合同项目查询</th></tr>
				<tr>
					<td class="tdLeft">项目</td>
					<td class="tdRight">
						<input type="text" id="projId" name="projId" value="${stockBudgetBean.projId }" class="base-input-text" maxlength="100"/>
					</td>
					<td class="tdLeft">合同</td>
					<td class="tdRight">
						<input type="text" id="cntNum" name="cntNum" value="${stockBudgetBean.cntNum }" class="base-input-text" maxlength="80"/>
					</td>
				<tr>
				<tr>
					<td colspan="4" class="tdWhite">
						<p:button funcId="02020103" value="查找"/>
						<input type="button" value="重置" id="resetBtn">
					</td>
				</tr>
			</table>
		</form>
		
		<br/>
		
		<table id="listTab" class="tableList">
			<tr>
			    <th nowrap="nowrap" width="12.5%">项目</th>
			    <th nowrap="nowrap" width="12.5%">项目总金额</th>
			    <th nowrap="nowrap" width="12.5%">合同</th>
			    <th nowrap="nowrap" width="12.5%">合同总金额</th>
			    <th nowrap="nowrap" width="12.5%">费用承担部门</th>
				<th nowrap="nowrap" width="12.5%">物料</th>
				<th nowrap="nowrap" width="12.5%">物料金额</th>
				<th nowrap="nowrap" width="12.5%">项目剩余额度</th>
			</tr>
			<c:forEach items="${list}" var="budget" varStatus="status">
				<tr>
				    <td>${budget.projId}</td>
				    <td style="text-align: right;"><fmt:formatNumber type="currency" value="${budget.budgetAmt}"/></td>
				    <td>${budget.cntNum}</td>
				    <td style="text-align: right;"><fmt:formatNumber type="currency" value="${budget.cntAmt}"/></td>
				    <td>${budget.dutyName}</td>
				    <td>${budget.matrName}</td>
				    <td style="text-align: right;"><fmt:formatNumber type="currency" value="${budget.execAmt}"/></td>
				    <td style="text-align: right;"><fmt:formatNumber type="currency" value="${budget.amt}"/></td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
				<tr>
					<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
		<p:page/>
	

</body>
</html>