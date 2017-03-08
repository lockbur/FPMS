<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算模板详情</title>
<script type="text/javascript">
	//平台页面加载初始化
	function pageInit(){
		App.jqueryAutocomplete();
		createTbByAuto();
	}
	
	//删除预算模板相关的责任中心关联(参数obj为删除图表，通过parent()找到td)
	function deleteBudgetOrgRel( obj )
	{
		//1.删除指定的TD
		$(obj).parent().remove();
		//2.创建数组(用于保存需要的TD数据)
		var orgArray = new Array();
		//3.将需要的TD复制至数组中
		$("#valiOrgListInfoTable").find("tr").find("td").each(function(){
			//如果TD中有值，则进行TD复制
			if($(this).text())
			{
				orgArray[orgArray.length] = $(this).clone().removeAttr("colspan").removeAttr("rowspan");
			}
		});
		//4.删除除TH的所有TR
		$("#valiOrgListInfoTable").find('tr').remove();
		//5.根据机构数，创建TR和TD并赋值
		if(orgArray.length != 0 ){
			//5.1创建赋值使用参数
			var lastTr ;			//最后一行TR
			var trTds = 3;			//每行TR的TD列数
			//5.2处理TD和TR，并添加到Table中
			for(var i=0;i<orgArray.length;i++){
				//第一次默认先创建一行TR，随后每超过3个TD创建一行TR
				if(i%trTds==0)
				{
					if(lastTr)
					{
						//如果最后一个TR存在，则将该TR添加到Table中
						$("#valiOrgListInfoTable").append(lastTr);
					}
					lastTr = document.createElement('tr');		//默认赋值最后一行TR
				}
				//将复制后的每个TD添加到当前行TR中去(每3个TD进行添加一次)
				$(lastTr).append(orgArray[i]);
			}
			//5.3处理最后一次的TR，添加到Table中(上面只创建了最后的TR，并没有添加到Table中)
				//当前TR如果TD个数不满足trTds个数(3个)，则创建空白的TD以塞满TR
			for( var i = $(lastTr).find("td").size(); i < trTds; i++ )
			{
				$(lastTr).append( document.createElement("td") );
			}
				//将该TR添加到Table中
			$("#valiOrgListInfoTable").append(lastTr);
		}
		//6.根据表格的Ids更新控件的值
			//删除后的机构列表，用于赋值给控件作为初始值
		var afterDelIds = getOrgIdsByTb();
			//删除后，通过控件提供的loadTagList方法更新控件的初始值
		treePlugin1.loadTagList(null,null,null,null,afterDelIds);
	}
	
	//获取当前机构表格【valiOrgListInfoTable】中所有TD的OrgIds
	function getOrgIdsByTb(){
		var afterDelIds = "";
		var allTd = $("#valiOrgListInfoTable").find("tr").find("td");			//表格中的所有TD
		$(allTd).each(function(){
			if($(this).find("span").attr("title")){												//如果该TD不为空，则取得TD的title值并拼接，即ids
				afterDelIds += $(this).find("span").attr("title") + ",";	
			}
		});
		return afterDelIds.substring(0,afterDelIds.length - 1);
	}
	
	//获取相关的Org的Id和Name信息
	function getOrgsInfoList( _obj ){
    	var orgInfos = $(_obj).val();
    	var orgArray = orgInfos.split(",");
    	return orgArray;
    };
    
    //根据责任中心信息自动创建展示Table(参数[names]和[ids]需要为数组格式)
    function createTbByAuto(length,names,ids){			//当参数为空时，即第一次，默认从后台取得所需数据
    	//查询到的用户个数
    	var numOfOrg;
    	if(length){
    		numOfOrg = length;
    	}else{
    		numOfOrg = $("#numOfOrgs").val().split(",").length;
    	}
        //生成表格的行数tRows,一行有三列(通过天花板ceil()函数计算表格的行)
        var tRows = Math.ceil(numOfOrg / 3);
        
        var orgNameArray;
        var orgIdArray;
        if(names){
        	orgNameArray = names;
        	orgIdArray = ids;
        }else{
        	orgNameArray = getOrgsInfoList($("#orgNameList"));
        	orgIdArray = getOrgsInfoList($("#orgIdList"));
        }
        var tableContent = "";		//表格内容
        for(var j=0;j<tRows;j++){
        	tableContent += "<tr>";
        		//为每一行添加指定的<td>
        		for(var n=(j*3);n<(3*(j+1));n++){
        			if(typeof orgNameArray[n] != "undefined"){
        				//如果对象存在，即将对象添加到td中
        				tableContent +=	"<td style='width:33%'>"
        									+"<span title="+orgIdArray[n]+" >"+orgNameArray[n]+"</span>"+
<%-- 											"<img border='0' width='8px' src='<%=request.getContextPath()%>/common/images/error_s.gif' alt='删除' onclick='deleteBudgetOrgRel(this)'>"+ --%>
        								"</td>";
        			}else{
        				//否则添加一个空的td
        				tableContent +="<td style='width:33%'></td>";
        			}
        		}
        	tableContent += "</tr>";
        }
		$("#valiOrgListInfoTable").append(tableContent);
    };
    
    //修改责任中心时，更新表格信息
    function updateZRZX(){
    	
     	var nodes = treePlugin1.getSelectOrgList();
     	var nodeNames = "";
     	var nodeIds = "";
     	if("undefined" != typeof nodes){
     		for(var i=0;i<nodes.length;i++){
        		nodeNames += nodes[i].name + ",";
        		nodeIds += nodes[i].id + ",";
    		}
        	nodeNames = nodeNames.substring(0,nodeNames.length - 1);
        	nodeIds = nodeIds.substring(0,nodeIds.length - 1);
        	
        	//重新生成机构列表
        	addToTable(nodes.length,nodeNames,nodeIds);
     	}
    }
	
    
    //添加到表格中
    function addToTable(length,names,ids){
    	//删除表格中除TH的所有TR
    	$("#valiOrgListInfoTable").find("tr").remove();
    	//根据新增的机构重新创建表格
    	if(length>0){
    		//只有机构控件中有选中值时，才重新创建表格
    		createTbByAuto(length,names.split(","),ids.split(","));	
    	}
    }
    
</script>
</head>

<body>
<p:authFunc funcArray=""/>
<form action="" method="post" id="budgetPlanDetailForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="4">
				预算模板详情
			</th>
		</tr>
		<tr>
			<td class="tdRight" colspan="3" style="border-right-style: none">
				预算模板ID： 【&nbsp; ${budgetInfo.tmpltId } &nbsp;】
				<input id="tmpltId"		type="hidden" name="tmpltId" value="${budgetInfo.tmpltId }"/>
				<input id="numOfOrgs" 	type="hidden" value="${orgsList}" >
				<input id="orgIdList" 	type="hidden" value="${budgetInfo.availableOrgList}"/>
				<input id="orgNameList" type="hidden" value="${budgetInfo.availableOrgNameList}"/>
			</td>
			<td style="text-align :right; border-left-style: none">
				 <input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">预算类型</td>
			<td class="tdRight" width="25%">
				<c:if test="${budgetInfo.dataType=='0'}" >年初预算</c:if>
				<c:if test="${budgetInfo.dataType=='1'}" >追加预算</c:if>
			</td>
			<td class="tdLeft" width="25%">有效年份</td>
			<td class="tdRight" width="25%">
				${budgetInfo.dataYear}
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">预算指标</td>
			<td class="tdRight" width="25%">
				<c:if test="${budgetInfo.dataAttr=='0'}" >资产类</c:if>
				<c:if test="${budgetInfo.dataAttr=='1'}" >费用类</c:if>
			</td>
		    <td class="tdLeft" width="25%">预算状态</td>
			<td class="tdRight" width="25%">
				<c:if test="${budgetInfo.dataFlag=='A0'}" >待提交</c:if>
				<c:if test="${budgetInfo.dataFlag=='00'}" >待处理</c:if>
				<c:if test="${budgetInfo.dataFlag=='01'}" >处理中</c:if>
				<c:if test="${budgetInfo.dataFlag=='02'}" >处理失败</c:if>
				<c:if test="${budgetInfo.dataFlag=='03'}" >处理完成</c:if>
				<c:if test="${budgetInfo.dataFlag=='04'}" >已提交</c:if>
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">模板文件名</td>
			<td class="tdRight" width="25%">         
				<!-- 参数为：下载路径,下载文件名,项目根路径名称(需要查找该路径下的ERP/fileUtils/fileDownload.do来执行下载操作) -->
				<a href="javascript:UploadUtils.downloadFile('${budgetInfo.serverFile}','${budgetInfo.sourceFileName }','/ERP')" >${budgetInfo.sourceFileName}</a>
			</td>
		    <td class="tdLeft" width="25%">创建柜员</td>
			<td class="tdRight" width="25%" >
				${budgetInfo.instOper}
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%">创建时间</td>
			<td class="tdRight" width="25%">
				${budgetInfo.instDate} &nbsp; ${budgetInfo.instTime}
			</td>
		    <td class="tdLeft" width="25%">提交时间</td>
			<td class="tdRight" width="25%">
				${budgetInfo.commitDate} &nbsp; ${budgetInfo.commitTime}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%" rowspan="2">责任中心</td>
<!-- 			<td class="tdRight" width="75%"  colspan="3"> -->
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" dynamicUpdateFlag="true"  --%>
<%--  									jsVarName="treePlugin1" rootNodeId="${budgetInfo.org21Code}" leafOnlyFlag="true"  --%>
<%--  									initValue="${budgetInfo.availableOrgList}" changeFun="updateZRZX"/>  --%>
<!-- 			</td> -->
		</tr>
		<tr>
			<td colspan="3">
				<input type="hidden" id="availableOrgList" name="availableOrgList">
				<table id="valiOrgListInfoTable" >
					<!-- 展示该模板的可用机构列表 -->
				</table>
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" width="25%" style="height: 60px">
		    	备注信息
		    </td>
			<td class="tdRight" width="25%" colspan="3" style="height: 60px">
				<textarea rows="4" cols="100" id="memo" name="memo" readonly="readonly">${budgetInfo.memo}</textarea>
			</td>
		</tr>
	</table>
	<P></P>

	<table id="budgetExcelInfoTable">
		<tr>
			<c:if test="${budgetInfo.dataAttr == '0'}">
				<!-- 资产类模板 -->
				<th>${budgetHeaderDetail.montCode}</th>
				<th>${budgetHeaderDetail.propertyType}</th>
				<th>${budgetHeaderDetail.acCode}</th>
				<th>${budgetHeaderDetail.columnOne}</th>
				<th>${budgetHeaderDetail.columnTwo}</th>
				<th>${budgetHeaderDetail.matrCode}</th>
				<th>${budgetHeaderDetail.matrName}</th>
				<th>${budgetHeaderDetail.referPriceHeader}</th>
				<th>${budgetHeaderDetail.referType}</th>
			</c:if>
			
			<c:if test="${budgetInfo.dataAttr == '1'}">
				<!-- 费用类模板 -->
				<th>${budgetHeaderDetail.montCode}</th>
				<th>${budgetHeaderDetail.jyZm}</th>
				<th>${budgetHeaderDetail.acCode}</th>
				<th>${budgetHeaderDetail.columnOne}</th>
				<th>${budgetHeaderDetail.columnTwo}</th>
				<th>${budgetHeaderDetail.matrCode}</th>
				<th>${budgetHeaderDetail.matrName}</th>
			</c:if>
		</tr>
		
		<c:if test="${!empty budgetBodyDetail}">
			<c:forEach items="${budgetBodyDetail}" var="bodyDetail" varStatus="status" >
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">	
					<c:if test="${budgetInfo.dataAttr == '0'}">
						<!-- 资产模板 -->
						<td>${bodyDetail.montCode}</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.propertyType == 'null'}"></c:when>
								<c:when test="${bodyDetail.propertyType != 'null'}">${bodyDetail.propertyType}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.acCode == 'null'}"></c:when>
								<c:when test="${bodyDetail.acCode != 'null'}">${bodyDetail.acCode}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.columnOne == 'null'}"></c:when>
								<c:when test="${bodyDetail.columnOne != 'null'}">${bodyDetail.columnOne}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.columnTwo == 'null'}"></c:when>
								<c:when test="${bodyDetail.columnTwo != 'null'}">${bodyDetail.columnTwo}</c:when>
							</c:choose>
						</td>
						<td>${bodyDetail.matrCode}</td>
						<td>${bodyDetail.matrName}</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.referPrice == '0'}"></c:when>
								<c:when test="${bodyDetail.referPrice != '0'}">${bodyDetail.referPrice}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.referType == 'null'}"></c:when>
								<c:when test="${bodyDetail.referType != 'null'}">${bodyDetail.referType}</c:when>
							</c:choose>
						</td>
					</c:if>
					
					<c:if test="${budgetInfo.dataAttr == '1'}">
						<!-- 费用类模板 -->
						<td>${bodyDetail.montCode}</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.jyZm == 'null'}"></c:when>
								<c:when test="${bodyDetail.jyZm != 'null'}">${bodyDetail.jyZm}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.acCode == 'null'}"></c:when>
								<c:when test="${bodyDetail.acCode != 'null'}">${bodyDetail.acCode}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.columnOne == 'null'}"></c:when>
								<c:when test="${bodyDetail.columnOne != 'null'}">${bodyDetail.columnOne}</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${bodyDetail.columnTwo == 'null'}"></c:when>
								<c:when test="${bodyDetail.columnTwo != 'null'}">${bodyDetail.columnTwo}</c:when>
							</c:choose>
						</td>
						<td>${bodyDetail.matrCode}</td>
						<td>${bodyDetail.matrName}</td>
					</c:if>
				</tr>
			</c:forEach> 
		</c:if>
		<c:if test="${empty budgetBodyDetail}">
			<tr>
				<c:if test="${budgetInfo.dataAttr == '0'}">
					<td style="text-align: center;" class="red" colspan="9"><span>没有找到相关信息</span></td>
				</c:if>
				<c:if test="${budgetInfo.dataAttr == '1'}">
					<td style="text-align: center;" class="red" colspan="7"><span>没有找到相关信息</span></td>
				</c:if>
			</tr>
		</c:if>
	</table>
	<p></p>
</form>
</body>
</html>