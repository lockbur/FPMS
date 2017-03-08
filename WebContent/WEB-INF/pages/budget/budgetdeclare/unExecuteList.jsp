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
<title>已签合同未执行完毕项目查询</title>
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
	rowspanTableTd("listTab", "3", "1", "0,1");
	rowspanTableTd("listTab", "2", "1", "0,1");
	rowspanTableTd("listTab", "1", "1", "0");
	rowspanTableTd("listTab", "0", "1");
	
	radioInit('confirmState');
}

//单选按钮初始化
function radioInit(_name){
	var flag = "${stockBudgetBean.confirmState}";
	if(!flag){
		flag = "";
	}
	$("input[name='"+ _name +"']").each(function(){
		if( $(this).val() == flag ){
			$(this).parent().find("label[for='" + $(this).attr("id") +"']").click();
		}
	});
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

function getValue(objs) {
	var needConfirmAmt = '';
	$(objs).each(function() {
		var value = $(this).parent().prev("td").find("input").val();
		var id = $(this).parent().prev("td").find("input").attr("id");
		if(value == '') {
			App.notyError("请输入选择的预算年度需确认费用！");
			return;
		}else if(!textCheck(id)) {
			return;
		}
		needConfirmAmt += (needConfirmAmt==''?'':',') + value;
	});
	return needConfirmAmt;
}
function checkInfo(button, url) {
	var objs = $("#listTab").find(":checkbox:checked:enabled[name='unionPrimaryKey']");
	if ($(objs).size() <= 0) {
		App.notyError("请选择需要确认的预算费用！");
		return;
	}
	var needConfirmAmt = getValue(objs);
	
	if(needConfirmAmt != '' && $(objs).size() == needConfirmAmt.split(",").length) {
		$( "<div>确认提交所选预算年度费用?</div>" ).dialog({
			resizable: false,
			height:140,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					var form = $('#unSignContSearchForm')[0];
					form.action = url + '&needConfirmAmt=' + needConfirmAmt;
					App.submit(form);
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
}

function textCheck(id) {
	var value = $("#"+id).val();
	var tdId = id + "Td";
	if(value == '')
		return false;
	var reg = /^(?:([1-9]\d*|0)(\.?)(\d{0,2})|0)$/;
	var flag = !reg.test(value) || 0 > value;
	App.toShowCheckIdIsExist("unSignContSearchForm",id,tdId,"请输入至多两位小数的非负数",flag);
	if(flag)
		return false;
	return true;
}
</script>
</head>
<body>
<p:authFunc funcArray="02020101,02020102"/>



	
		<form action="" method="post" id="unSignContSearchForm" >
		<p:token/>
			<table id="approveChainTable">
				<tr class="collspan-control"><th colspan="4">已签合同未执行完毕项目查询</th></tr>
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
					<td class="tdLeft">费用确认状态</td>
					<td class="tdRight" colspan="3">
						<div class="base-input-radio" id="isFlag">
							<label for="isAll" onclick="App.radioCheck(this,'isFlag')" >全部</label>
								<input type="radio" id="isAll" name="confirmState" value="" />
							<label for="isUndo" onclick="App.radioCheck(this,'isFlag')" >未确认</label>
								<input type="radio" id="isUndo" name="confirmState" value="未确认" />
							<label for="isDo" onclick="App.radioCheck(this,'isFlag')" >已确认</label>
								<input type="radio" id="isDo" name="confirmState" value="已确认" />
						</div>
					</td>
				<tr>
				</tr>
				<tr>
					<td colspan="4" class="tdWhite">
						<p:button funcId="02020101" value="查找"/>
						<input type="button" value="重置" id="resetBtn">
					</td>
				</tr>
			</table>
		
			<br/>
			
			<table id="listTab" class="tableList">
				<tr>
				    <th nowrap="nowrap">项目</th>
				    <th nowrap="nowrap">合同</th>
				    <th nowrap="nowrap">合同总金额</th>
				    <th nowrap="nowrap">费用承担部门</th>
					<th nowrap="nowrap">物料</th>
					<th nowrap="nowrap">已确认费用</th>
					<th nowrap="nowrap">确认状态</th>
					<th nowrap="nowrap">预算年度需确认费用</th>
					<th><input type="checkbox" onclick="Tool.toggleCheck(this,'unionPrimaryKey');"/></th>
				</tr>
				<c:forEach items="${list}" var="budget" varStatus="status">
					<tr>
					    <td>${budget.projId}</td>
					    <td>${budget.cntNum}</td>
					    <td style="text-align: right;" nowrap="nowrap"><fmt:formatNumber type="currency" value="${budget.cntAmt}"/></td>
					    <td>${budget.dutyName}</td>
					    <td>${budget.matrName}</td>
					    <td style="text-align: right;" nowrap="nowrap"><fmt:formatNumber type="currency" value="${budget.sumConfirmedAmt}"/></td>
					    <td>${budget.confirmState}</td>
					    <td id="confirmAmt${status.index}Td">
					    	<input type="text" value="${budget.needConfirmAmt }" id="confirmAmt${status.index}"
					    		<c:if test="${budget.confirmState == '已确认' or budget.hasTemplate == '0'}">disabled="disabled"</c:if>
					    		style="width: 125px;" onblur="textCheck('confirmAmt${status.index}')" class="base-input-text"/>
					    </td>
					    <td><input type="checkbox" name="unionPrimaryKey" 
					    		<c:if test="${budget.confirmState == '已确认' or budget.hasTemplate == '0'}">disabled="disabled"</c:if>
					    		value="${budget.projId}~~${budget.cntNum}~~${budget.dutyCode}~~${budget.matrCode}"/>
					    </td>
					</tr>
				</c:forEach>
				<c:if test="${empty list}">
					<tr>
						<td colspan="9" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
					</tr>
				</c:if>
			</table>
			<div style="text-align:right;margin: 10px;" >
				<p:button funcId="02020102" value="确定" onclick="checkInfo"/>
			</div>
		</form>
		<p:page/>

</body>
</html>