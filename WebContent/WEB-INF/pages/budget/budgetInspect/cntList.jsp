<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同维度</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	initYear($("#bgtYear"));
	$("#bgtYear").combobox();
	combineCell();
}
function initYear(_obj){
	var currentYear = new Date().getFullYear();
	var start = 2016;
	var range = currentYear-start+1;
	for(var i=0 ; i<range+1 ; i++){
		$(_obj).append("<option value='"+(start+i)+"'>"+(start+i)+"年</option>");
	}
	var dataYear = '${queryCondition.bgtYear}';
	$("#bgtYear").val(dataYear);
}
function resetAll() {
	$("select").val("");
	$(":text").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function getList(){
	var orgFlag = '${queryCondition.orgFlag}';
	var form=$("#cntSumForm")[0];
	if(orgFlag == '1')
	{
		form.action = '<%=request.getContextPath() %>/budget/budgetInspect/org1CntList.do?<%=WebConsts.FUNC_ID_KEY %>=02070202';
	}
	else if(orgFlag == '2')
	{
		form.action = '<%=request.getContextPath() %>/budget/budgetInspect/org2CntList.do?<%=WebConsts.FUNC_ID_KEY %>=02070203';
	}
	else if(orgFlag == '3')
	{
		form.action = '<%=request.getContextPath() %>/budget/budgetInspect/dutyCodeCntList.do?<%=WebConsts.FUNC_ID_KEY %>=02070204';
	}
	App.submit(form);
}
function detail(cntNum, scId){
	var url = '';
	var judge = '${queryCondition.bgtId}';
	var form=$("#detail")[0];
	if(judge == null || judge == ''){
		form.action="<%=request.getContextPath()%>/budget/budgetInspect/cntDetail.do?<%=WebConsts.FUNC_ID_KEY%>=02070201&cntNum="+cntNum+"&scId="+scId;
	} else {
		form.action="<%=request.getContextPath()%>/budget/budgetInspect/toCntDetail.do?<%=WebConsts.FUNC_ID_KEY%>=02070104&cntNum="+cntNum+"&scId="+scId+"&bgtId="+judge;
	}
	App.submit(form);
}
//合并单元格
function combineCell(){
	table("year_td_h_","year_td_");
	table("cnt_td_h_","cnt_td_");
	table("duty_td_h_","duty_td_");
	table("mont_td_h_","mont_td_");
	table("matr_td_h_","matr_td_");
	table("refe_td_h_","refe_td_");
	table("spec_td_h_","spec_td_");
}
//隐藏对应的<td>
function table(inputId,tdId){
	var str = "";
	var table = $("#listTab");
	var len = table[0].rows.length;
	for(var i=1;i<=len-1;i++){
		str = $("#"+inputId+i).val();
		var k = 0;
		for(var j=i+1;j<=len;j++){
			var val = $("#"+inputId+j).val();
			if(str == val){
				k++;
				$("#"+tdId+j).remove();
			} else {
				break;
			}
		}
		$("#"+tdId+i).attr("rowspan", parseInt(k)+1);
		i += k;
	}
}
//更新设置<tr></tr>背景方式,使显示更为合理
function setTrBgClassUpdate(trObjectRef, bgClassName){
	if(trObjectRef){
	    trObjectRef.className=bgClassName;
		var tds = ["year_td_","cnt_td_","duty_td_","mont_td_","matr_td_","refe_td_","spec_td_"];
		var indexStr = $(trObjectRef).find('input').attr('id');
		var index = indexStr.substr(indexStr.lastIndexOf('_') + 1);
		for(var i=0; i<tds.length; i++){
			if($('#'+tds[i]+index).val() == undefined){
				var tdId = '#'+tds[i];
				for(var j=parseInt(index)-1; j>0; j--){
					if($(tdId+j).val() != undefined){
						$(tdId+j).attr('class',bgClassName);
						break;
					}
				}
			} else {
				$('#'+tds[i]+index).attr('class',bgClassName);
			} 
		}
    }
}
function exportUseDtlDataList(bgtId , exportType){
	var form = $("#cntSumForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/exportBgtInspectQueryData.do?<%=WebConsts.FUNC_ID_KEY %>=02070107&bgtId="+bgtId+"&exportType="+exportType;
	App.submit(form);
}
function exportData(bgtId){
	var isPass=true;
	var data = {};
	data['exportType']='2';
	data['bgtId'] =  bgtId;
	App.ajaxSubmit("budget/budgetInspect/exportBgtInspectQueryData.do?<%=WebConsts.FUNC_ID_KEY %>=02070107",
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

</script>
</head>
<body>
<p:authFunc funcArray="0207,020701"/>
<form method="post" id="detail" action=""></form>
<form method="post" id="cntSumForm" action="<%=request.getContextPath() %>/budget/budgetInspect/cntList.do?<%=WebConsts.FUNC_ID_KEY %>=020702">
	<p:token/>
	<c:choose>  
		<c:when test="${empty queryCondition.bgtId }"> 
		<table>
		<tr class="collspan-control">
			<th colspan="4">合同维度查询条件</th>
		</tr>
		<tr>
			<td class="tdLeft">预算年份</td>
			<td class="tdRight">
				<div class="ui-widget">
				<select id="bgtYear" name="bgtYear" class="erp_cascade_select">
					<option value="">-请选择-</option>
				</select>
				</div>				
			</td>
			<td class="tdLeft">合同号</td>
			<td class="tdRight"><input type="text" name="cntNum" value="${queryCondition.cntNum}" class="base-input-text" /></td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input id="cntList" type="button" value="查找" onclick="getList()" />
				<input id="reset" type="button" value="重置" onclick="resetAll()" />
			</td>
		</tr>
	</table>
	<br />
	</c:when>    
	<c:otherwise>
	<table>
		<tr class="collspan-control">
			<th colspan="4">预算使用明细</th>
		</tr>
	</table>
	</c:otherwise>  
	</c:choose>  
	
	<table id="listTab" class="tableList">
		<tr>
			<th>预算年份</th>
			<th>合同号</th>
			<th>费用承担部门</th>
			<th>监控指标</th>
			<th>物料名称</th>
			<th>参考</th>
			<th>专项</th>
			<th>总占用预算</th>
			<th>冻结预算</th>
			<th>透支预算</th>
			<th>操作</th>
		</tr>
		<tr>
			<c:forEach items="${sumCntList}" var="sumCnt" varStatus="i">
				<tr onmousemove="setTrBgClassUpdate(this, 'trOnOver2')" onmouseout="setTrBgClassUpdate(this, 'trOther')">
					<td class="compare" id='year_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='year_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.bgtYear }">
						${sumCnt.bgtYear }
					</td>
					<td class="compare" id='cnt_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='cnt_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.cntNum }">
						${sumCnt.cntNum }
					</td>
					<td class="compare" id='duty_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='duty_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.dutyName }">
						${sumCnt.dutyName }
					</td>
					<td class="compare" id='mont_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='mont_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.montName }">
						${sumCnt.montName }
					</td>
					<td class="compare" id='matr_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='matr_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.matrName }">
						${sumCnt.matrName }
					</td>
					<td class="compare" id='refe_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='refe_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.referenceName }">
						${sumCnt.referenceName }
					</td>
					<td class="compare" id='spec_td_<c:out value="${i.index+1}"/>' rowspan='1'>
						<input type="hidden" id='spec_td_h_<c:out value="${i.index+1}"/>' value="${sumCnt.specialName }">
						${sumCnt.specialName }
					</td>
					<td>${sumCnt.bgtUsedSum }</td>
					<td>${sumCnt.bgtFrozen }</td>
					<td>${sumCnt.bgtOverdraw }</td>
					<td>
						<div class="detail">
					    	<a href="#" onclick="detail('${sumCnt.cntNum}','${sumCnt.scId}');" title="<%=WebUtils.getMessage("button.view")%>"></a>
						</div>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty sumCntList }">
				<tr>
					<td colspan="11" class="red" style="text-align: center;"><span>没有找到相关信息!</span></td>
				</tr>
			</c:if>	
		</tr>
		<c:if test="${not empty queryCondition.bgtId }">
			<tr>
				<td rowspan="1" colspan="11" class="tdWhite" style="text-align: center">
					<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
					<input type="button" value="查询数据导出" onclick="exportData('${sumCnt.bgtId}')"> 
				</td>
			</tr>
		</c:if>	
	</table>
</form>
<p:page/>
</body>
</html>