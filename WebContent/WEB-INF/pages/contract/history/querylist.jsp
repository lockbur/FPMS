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
<title>合同历史列表</title>
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

function gotoDtl(cntNum,versionNo)
{
	var form = $("#dtlForm")[0];
	form.action='<%=request.getContextPath()%>/contract/history/cntDtl.do?<%=WebConsts.FUNC_ID_KEY%>=03020801&cntNum=' + cntNum +'&versionNo=' + versionNo;
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
<p:authFunc funcArray="030208,03020801"/>
<form action="" method="post" id="dtlForm"></form>
<form method="post" id="queryForm" action="<%=request.getContextPath()%>/contract/history/queryList.do?<%=WebConsts.FUNC_ID_KEY%>=030208">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同历史记录查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${con.cntNum}" class="base-input-text" maxlength="80"/>
				<input type="hidden" name="versionNo" />
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
				<input type="text" id="providerName" name="providerName" value="${con.providerName}" class="base-input-text" onclick="selProvider()" readonly/>
			</td>
			<td class="tdLeft">签订日期区间</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${con.befDate}" class="base-input-text" style="width:135px;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${con.aftDate}" class="base-input-text" style="width:135px;"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">创建机构</td>
			<td class="tdRight" colspan="3">
				<forms:OrgSelPlugin rootNodeId="${user.org1Code}" initValue="${con.createDept}" jsVarGetValue="createDept" parentCheckFlag="false"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="030208" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList">
			<tr>
				<th width="13%">合同号</th>
				<th width="15%">合同事项</th>
				<th width="10%">修改次数</th>
				<th width="15%">创建机构</th>
				<th width="10%">创建日期</th>
				<th width="10%">最后操作人</th>
				<th width="12%">最后操作日期</th>
				<th width="10%">状态</th>
				<th width="5%">操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${cntItem.cntNum}</td>
				<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}" /></td>
				<td class="tdc">${cntItem.versionNo }</td>
				<td>${cntItem.dutyName}</td>
				<td class="tdc">${cntItem.createDate}</td>
				<td>${cntItem.operUser}</td>
				<td class="tdc">${cntItem.operDate}</td>
				<td>
					<c:if test="${empty cntItem.dataFlag}">
						合同已删除
					</c:if>
					<c:if test="${!empty cntItem.dataFlag}">
						${cntItem.dataFlagName}
					</c:if>
				</td>
				<td>
					<div class="detail">
					    <a href="#" title="合同详细" onclick="gotoDtl('${cntItem.cntNum}','1');"></a>
					</div>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="10" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>
</body>
</html>