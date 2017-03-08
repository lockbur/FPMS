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
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<title>订单查询</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#dataFlag").combobox();
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
	infoHide(11);
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
//得到详细信息
function showDetail(orderId) 
{  
	var form = $("#tempForm");
 	$("#tempForm #orderId").val(orderId);
 	//alert($("#id").val());
 	form.attr('action', '<%=request.getContextPath()%>/pay/orderquery/getInfo.do?<%=WebConsts.FUNC_ID_KEY%>=03030202');
	App.submit(form);
}
function exportData(tag){
	var isPass=true;
	var data = {};
	data['orgFlag'] =  "${selectInfo.orgFlag}";
	data['orderId'] =  $("#orderId").val();
	data['cntName'] = $("#cntName").val();
	data['cntNum'] = $("#startYear").val();
	if(orderDutyCode.getSelectOrgList()[0] != null){
		data['orderDutyCode'] =  orderDutyCode.getSelectOrgList()[0].id;
	}
	
	data['chkUser'] = $("#chkUser").val();
	data['dataFlag'] = $("#dataFlag").val();
	var url ="";
	if(tag =='1'){
		url ='pay/orderquery/exportData.do?<%=WebConsts.FUNC_ID_KEY %>=03030203';
	}else{
		url = 'pay/orderquery/exportDataDetail.do?<%=WebConsts.FUNC_ID_KEY %>=03030204';
	}
	App.ajaxSubmit(url,
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
</script>

</head>
<body>
<p:authFunc funcArray="03030201,03030211,03030212,03030213"/>
<form  method="post" id="tempForm">
	<p:token/>
	<table id="condition">
		<tr class="collspan-control">
			<th colspan="4">
				订单查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">订单号</td>
			<td class="tdRight">
				<input type="text" id="orderId" name="orderId" value="${selectInfo.orderId}" class="base-input-text" maxlength="20"/>
			</td>
			<td class="tdLeft">合同事项</td>
			<td class="tdRight">
            	<input type="text" id="cntName" name="cntName" value="${selectInfo.cntName}" class="base-input-text" maxlength="100" />
			</td>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${selectInfo.cntNum}" class="base-input-text"  maxlength="80"/>	
			</td>
			<td class="tdLeft">采购部门</td>
			<td class="tdRight">
				<forms:OrgSelPlugin   rootNodeId="${org1Code}" jsVarName="orderDutyCode"  jsVarGetValue="orderDutyCode" initValue="${selectInfo.orderDutyCode}"  ableQuery="true"   parentCheckFlag="false"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">采购员</td>
			<td class="tdRight">
				<input type="text" id="chkUser" name="chkUser" value="${selectInfo.chkUser}" class="base-input-text"  maxlength="80"/>	
			</td>
			<td class="tdLeft">状态</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataFlag" name="dataFlag"   class="erp_cascade_select"  >
						<option value="">-请选择-</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
						 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='ORDER_DATA_FLAG' " selectedValue="${selectInfo.dataFlag}"/>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">PO单号</td>
			<td class="tdRight">
				<input type="text" id="poNumber" name="poNumber" value="${selectInfo.poNumber}" class="base-input-text"  maxlength="80"/>	
			</td>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName" value="${selectInfo.providerName}" class="base-input-text"" readonly/>
				<a onclick="selProvider()">
					<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">物料编码</td>
			<td class="tdRight">
				<input type="text" id="matrCode" name="matrCode" value="${selectInfo.matrCode}" class="base-input-text"  maxlength="80"/>	
			</td>
			<td class="tdLeft">物料名称</td>
			<td class="tdRight">
				<input type="text" id="matrName" name="matrName" value="${selectInfo.matrName}" class="base-input-text"  maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">核算码</td>
			<td class="tdRight">
				<input type="text" id="cglCode" name="cglCode" value="${selectInfo.cglCode}" class="base-input-text"  maxlength="80"/>
			</td>
			<td class="tdLeft">项目名称</td>
			<td class="tdRight">
				<input type="text" id="projName" name="projName" value="${selectInfo.projName}" class="base-input-text"  maxlength="80"/>	
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<c:if test="${selectInfo.orgFlag=='1'}">
					<p:button funcId="03030211" value="查询"/> 
				</c:if>
				<c:if test="${selectInfo.orgFlag=='2'}">
					<p:button funcId="03030212" value="查询"/> 
				</c:if>
				<c:if test="${selectInfo.orgFlag=='3'}">
					<p:button funcId="03030213" value="查询"/>
				</c:if>
				
				
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="导出订单汇总信息" onclick="exportData('1');">
				<input type="button" value="导出订单明细信息" onclick="exportData('2');">
			</td>
		</tr>
	</table>
	<br/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
					<th width="20%">合同号</th>
					<th width="15%">合同事项</th>
					<th width="15%">订单号</th>
					<th width="15%">采购部门</th>
					<th width="10%">状态</th>
					<th width="8%">操作</th>
			</tr>
			 <c:forEach items="${list}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td class="tdc" ><a href="javascript:void(0);" onclick="gotoCntDtl('${sedList.cntNum }');" class="text_decoration">${sedList.cntNum }</a></td>
					<td class="tdc tdInfo">
					 	<c:out value="${sedList.cntName}"/>
					</td>
					<td class="tdc" ><c:out value="${sedList.orderId}"></c:out></td>
					<td ><c:out value="${sedList.orderDutyCodeName}"/></td>
					<td ><c:out value="${sedList.dataFlagName}"/></td>
					<td>
							<div class="detail"   style="padding-left: 5px">
					   	    	 <a href="#" onclick="showDetail('${sedList.orderId}')" title="详情"></a>
							</div>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
					<tr><td style="text-align: center;" colspan="6"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
</body>
</html>