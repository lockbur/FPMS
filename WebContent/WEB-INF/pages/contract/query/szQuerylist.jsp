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
<title>合同查询</title>
<script type="text/javascript">

function doValidate(form)
{
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	
	return true;
}

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$(".selectC").combobox();
 	//设置时间插件
	$( "#befDate,#aftDate,#beginDate,#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	   	
	});
	
}

function resetAll() {
	var checkList=["20","21","25","30","32","35","40"];
	$("input[name='dataFlags']").each(function(){
		$(this).removeAttr("checked");
	});
	$("input[name='dataFlags']").each(function(){
		 for(var i=0;i<checkList.length;i++){
			if(checkList[i]==$(this).val()){
				$(this).prop('checked','true');
				break;
			}
			
		}
	});
	var date="${sysDate}";
	$("#befDate").val(date);
	$("#aftDate").val(date);
}

function addDownload(){
	
	if(!$.checkDate("befDate","aftDate")){
		return false;
	}
	
	App.ajaxSubmitForm("contract/query/szCntMatrExport.do?VISIT_FUNC_ID=03020616", $("#queryForm"),  
    		function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("添加下载失败，请重试!");
				}
				else
				{
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						title: "跳转下载页面",
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
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
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
				}
			});
	
}

</script>
</head>

<body  >
<p:authFunc funcArray="030206,03020615"/>
<form action="" method="post" id="dtlForm"></form>
<form method="post" id="queryForm" action="">
	<input type="hidden" value="Y" name="checkedFlag"/>
	<table>
		<tr class="collspan-control-cnt">
			<th colspan="4">
				合同基本信息查询
			</th>
		</tr>
		<tr class="collspan">
			<td class="tdLeft">操作类型</td>
			<td class="tdRight" colspan="3"> 
				<table id="dataFlagTable">
					<c:forEach items="${dataFlag}" var="bean" varStatus="status">
						<c:if test="${(status.index)%5==0}">
							<tr>
						</c:if>
						<td style="border: 0">
							<input type="checkbox" name="dataFlags" value='${bean.dataFlag }' 
								<c:forEach items="${dataFlagChecked}" var="beanChecked" varStatus="statusChecked">
									<c:if test="${bean.dataFlag==beanChecked}">checked</c:if>
								</c:forEach>
							>${bean.dataFlagName }
						</td>
						<c:if test="${(status.index+1)%5==0}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
		<td class="tdLeft">操作日期</td>
			<td class="tdRight">
				<input type="text" id="befDate" name="befDate" valid maxlength='10' readonly="readonly" value="${con.befDate}" class="base-input-text" style="width:125px;"/>
				至
				<input type="text" id="aftDate" name="aftDate" valid maxlength='10' readonly="readonly" value="${con.aftDate}" class="base-input-text" style="width:125px;"/>
			</td>
		</tr>
		
		
		<tr class="collspan">
			<td colspan="4" class="tdWhite">
				<p:button funcId="03020615" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="添加下载" onclick="addDownload();">
			</td>
		</tr>
	</table>	
	
	
	
	<br>
	<br>
	<table class="tableList">
			<tr>
				<th width='12%'>合同号</th>
				<th width='13%'>合同事项</th>
				<th width='10%'>合同总额(含税)</th>
				<th width='10%'>供应商 </th>
				<th width='5%'>采购行子序号</th>
				<th width='15%'>物料名称</th>
				<th width='5%'>数量</th>
				<th width='7%'>单价(元)</th>
				<th width='8%'>不含税金额(元)</th>
				<th width='5%'>税额(元)</th>
				<th width='5%'>合同状态 </th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td class="tdc">${cntItem.cntNum}</td>
				<td class="tdc"><forms:StringTag length="20" value="${cntItem.cntName}"/></td>
				<td class="tdc">${cntItem.cntAllAmt}</td>
				<td>${cntItem.providerName}</td>
				<td class="tdc">${cntItem.subIdSz}</td>
				<td class="tdc">${cntItem.matrCodeSz}-${cntItem.matrNameSz}</td>
				<td class="tdc">${cntItem.execNumSz}</td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.execPriceSz}" minFractionDigits="2"/></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.execAmtSz}" minFractionDigits="2"/></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${cntItem.taxAmtSz}" minFractionDigits="2"/></td>
				<td class="tdc">${cntItem.dataFlag}</td>
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