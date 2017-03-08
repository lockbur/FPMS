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
<title>合同修改</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	
 	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
	
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
function gotoModifyDtl(cntNum,dataFlag)
{
	var form = $("#motifyForm")[0];
	//form.cntNum.value = cntNum;
	form.action='<%=request.getContextPath()%>/contract/modify/modifyDtl.do?<%=WebConsts.FUNC_ID_KEY%>=0302010201&cntNum='+cntNum+'&dataFlag='+dataFlag;
	App.submit(form);
}
function selProvider()
{	
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

function doValidate(){
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	return true;
}
</script>
</head>

<body>
<p:authFunc funcArray="03020102,0302010201"/>
<form action="" id="motifyForm" method="post"></form>
<form action="" method="post" id="listForm" action="<%=request.getContextPath()%>/contract/modify/modifyList.do?<%=WebConsts.FUNC_ID_KEY%>=03020102">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${con.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
			<div class="ui-widget">
				<select class="erp_cascade_select" id="cntType" name="cntType">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
					 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
					 orderType="ASC" selectedValue="${con.cntType}"/>	
				</select>
			</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" id="providerName" readonly name="providerName" value="${con.providerName}" class="base-input-text" onclick="selProvider()"/>
				<%-- <a href="#" onclick="selProvider()" title="供应商查询">
					<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a> --%>
			</td>
			<td class="tdLeft">签订日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${con.befDate}" class="base-input-text" style="width:135px;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${con.aftDate}" class="base-input-text" style="width:135px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="03020102" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table id="listTab" class="tableList">
			<tr id="sd">
				<th width='13%'>合同号 </th>
				<th width='10%'>合同事项    </th>
				<th width='10%'>合同类型    </th>
				<th width='9%'>供应商  </th>
				<th width='10%'>合同金额 (不含税)   </th>
				<th width='10%'>税额   </th>
				<th width='8%'>签订日期    </th>
				<th width='10%'>合同状态  </th>
				<th width='12%'>录入责任中心</th>
				<th width='8%'>录入时间    </th>
				<th width='6%'>操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr  onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${cntItem.cntNum}</td>
				<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td class="tdc">${cntItem.cntTypeName}</td>
				<td>${cntItem.providerName}</td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntAmt}" minFractionDigits="2" /></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.cntTaxAmt}" minFractionDigits="2" /></td>
				<td class="tdc">${cntItem.signDate}</td>
				<td>${cntItem.dataFlagName}</td>
				<td>${cntItem.createDeptName}</td>
				<td class="tdc">${cntItem.createDate}</td>
				<td>
					<div class="update">
					    <a href="#" onclick="gotoModifyDtl('${cntItem.cntNum}','${cntItem.dataFlag}');" title="修改"></a>
					</div>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="11" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>