<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="java.util.List"%>
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
<title>审批链批量维护</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
}
//单选按钮初始化
function radioInit(_obj, _name, _val){
	if(!_val)
	{
		_val = $(_obj).find("input[name^='"+ _name +"']:eq(0)").val();
	}
	$(_obj).find("input[name^='"+ _name +"']").attr("checked", false);
	$(_obj).find("input[name^='"+ _name +"']").each(function(){
		if( $(this).val() == _val ){
			$(this).attr("checked", true);
			$(this).parent().find("label[for='" + $(this).attr("id") +"']").click();
		}
	});
}
//全选 二级行下的责任中心
function selectAll(item ,tag){
	$('input[type=checkbox][class=' + tag + ']').each(function(){
		this.checked = true;
	});
	 
}
//反选
function selectOther(item,tag){
	$('input[type=checkbox][class=' + tag + ']').each(function(){
		if(this.checked == false){
			this.checked = true;
		}else{
			this.checked = false;
		}
	});
	 
}
function doValidate() {
	if(!App.valid("#reqtypeForm")){
		 return;
	}
	var matrBuyDeptList = matrBuyDeptTree.getSelectOrgList();
	if(matrBuyDeptList.length < 1){
		App.notyError("物料采购部门不能为空，请选择物料采购部门！");
		return false;
	}
	var matrAuditDeptList = matrAuditDeptTree.getSelectOrgList();
	if(matrAuditDeptList.length < 1){
		App.notyError("物料归口部门不能为空，请选择物料归口部门！");
		return false;
	}
	var decomposeOrgList = decomposeOrgTree.getSelectOrgList();
	if(decomposeOrgList.length < 1){
		App.notyError("预算分解部门不能为空，请选择预算分解部门！");
		return false;
	}
	var fincDeptSList = fincDeptSTree.getSelectOrgList();
	if(fincDeptSList.length < 1){
		App.notyError("本级财务管理部门不能为空，请选择本级财务管理部门！");
		return false;
	}
	var fincDept2List = fincDept2Tree.getSelectOrgList();
	if(fincDept2List.length < 1){
		App.notyError("二级分行财务管理部门不能为空，请选择二级分行财务管理部门部门！");
		return false;
	}
	var fincDept1List = fincDept1Tree.getSelectOrgList();
	if(fincDept1List.length < 1){
		App.notyError("一级分行财务管理部门不能为空，请选择一级分行财务管理部门！");
		return false;
	}
	return true;
}
//得到公共的未维护物料
function selectPublicMatrs(){
	var aprvType = $("#aprvType").val();
	var dutys="";
	$("input[type=checkbox][name='dutys']:checked").each(function(){
		var duty = $(this).val();
		if(dutys==""){
			dutys = duty;
		}else{
			dutys =dutys+","+duty;
		}
	});
	var data = {};
	data['dutyArray'] = dutys;
	data['aprvType']=aprvType;
	data['dataYear']=$("#dataYear").val();
	App.ajaxSubmit("sysmanagement/approveChainMgr/specSelecstPublicMatrs.do?<%=WebConsts.FUNC_ID_KEY%>=0812010210",{data : data,async:false}, function(data) {
		var resultValue=data.data;
		$("#matrTables").html("");
		var html="";
		$("#matrTables").html("<tr class='collspan-control'><th width='20%' align='center'>监控指标名称</th><th width='80%' algin='center'>物料</th></tr>");
		if(null  == resultValue){
			$("#matrTables").append("");
		}else{
			var monts = resultValue.monts;
			var matrs = resultValue.matrs;
			for(var i=0;i<monts.length;i++){
				//这里是监控指标
				html="<tr ><td class='tdLeft' >";
				html+="<div style='float:left' id='"+monts[i]+"_td'></div>";
				html+="<div style='float:left'><span style='color:red'>(";
				html+='<a style="color:red" href="javascript:void(0)" id="selectAllId"   onclick="selectAll(this, \'mont_'+monts[i]+'\')">全选</a>';
				html+='<a style="color:red" href="javascript:void(0)"   onclick="selectOther(this, \'mont_'+monts[i]+'\')">反选</a>';
				html+=")</span></div>";
				html+="</td>";
				html+="<td class='tdRight'>";
				//这里放展开和隐藏的链接
				html+='<div style="width:5%;float: left; margin-left: 0px;padding: 0px; text-align: left;" id="'+monts[i]+'_divId">';
				html+='<a style="color: red" href="javascript:void(0)" id="s" onclick="openDiv(\''+monts[i]+'\',this)">';
				html+='展开';
				html+='</a></div>';
				//这里是放的物料内容
				html+='<div  style="width:94%;float: left; margin-left: 0px;padding: 0px; text-align: left;">';
				var montNameShow="";
				var matrIndex = 0;
				for(var j = 0 ; j < matrs.length ; j++){
					var montCode = matrs[j].montCode;
					var montName = matrs[j].montName;
					var matrCode = matrs[j].matrCode;
					var matrName = matrs[j].matrName;
					var showName = "";
					if(matrName.length>18){
						showName = matrName.substring(matrName.length-18);
					}else{
						showName = matrName;
					}
					if(montCode == monts[i]){
						matrIndex = matrIndex+1;
						montNameShow = montName;
						if(matrIndex>3){
							html +="<div class='"+montCode+"_hideDiv' style='width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left; display:none'>";
						}else{
							html +="<div style='width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;'>";
						}
						
						html +="<label><input type='checkbox' style='margin-left: 0px;' value='"+montCode+"_"+matrCode+"' name='matrA' class='mont_"+montCode+"'   >";
						html+=showName;
						html+="</label></div>";
					}				
					
				}
				html+="</div>";
				html+="</td></tr>";
				$("#matrTables").append(html);
				$("#"+monts[i]+"_td").html(montNameShow);
				if(matrIndex<=3){
					$("#"+monts[i]+"_divId").html("&nbsp");
				}
			}
		}
		
		
	});
}
function openDiv(hideDivId,obj){
	 $("."+hideDivId+"_hideDiv").toggle();
	 if($("."+hideDivId+"_hideDiv:hidden").size() == 0 )
	{
		 $(obj).html("隐藏");
		 
	}else{
		$(obj).html("展开");
	}
}

function dutyOpenDiv(hideDivId,obj){
	 $("."+hideDivId+"_hideDiv").toggle();
	 if($("."+hideDivId+"_hideDiv:hidden").size() == 0 )
	{
		 $(obj).html("隐藏");
		 
	}else{
		$(obj).html("展开");
	}
}

//全选责任中心
function selectAllDuty(item ,tag){
	$('input[type=checkbox][name = dutys]').each(function(){
		this.checked = true;
	});
	selectPublicMatrs();	
}
//反选责任中心
function selectOtherDuty(item,tag){
	
	$('input[type=checkbox][name=dutys]').each(function(){
		if(this.checked == false){
			this.checked = true;
		}else{
			this.checked = false;
		}
	});
	selectPublicMatrs();
}
//提示信息
$(document).ready(function() {
$(".dutyToolTip").each(function(){
	var id = $(this).attr("id");
	var value = $("#"+id+"_hide").val();
	$(this).jBox('Tooltip', {
		closeOnMouseleave:true,
		  content: "该责任中心还可以维护<span style='color:red'>"+value+"</span>条审批链",
		  offset: {
	            x: 0,
	            y: 2
	        }
	});
});
});
</script>
</head>
<body>
<p:authFunc funcArray="0812010209"/>
<form action="" method="post" id="reqtypeForm">
<input type="hidden" name="aprvType" id="aprvType" value="12"/>
<input type="hidden" name="dataYear" id="dataYear" value="${bean.dataYear }"/>
<p:token/>
	<table>
		<tr>
			<th>审批链批量维护
				<span style="color:red">
						(
						<a style="color:red" href="javascript:void(0)" id="selectAllId" name="1" onclick="selectAllDuty(this, 'duty')">全选</a>
						<a style="color:red" href="javascript:void(0)" onclick="selectOtherDuty(this, 'duty')">反选</a>
						)
					</span>
			</th>
		</tr>
		<tr>
			<td class="tdRight" style="text-align: left;">
				<table id="approveChainTable">
					<tr class="collspan-control">
						<th  width="20%" align="center">二级行或本部机构</th>
						<th  width="80%" align="center">责任中心</th>
					<tr>
					<c:forEach items="${list}" var="list" varStatus="i">
					<tr>
						<td align="left"class="tdLeft" style="text-align: left;">
							(${list.org2Code })${list.org2Name }
							<span style="color:red">
								(
								<a style="color:red" href="javascript:void(0)" id="selectAllId"   onclick="selectAll(this, 'org2${i.index}')">全选</a>
								<a style="color:red" href="javascript:void(0)" onclick="selectOther(this, 'org2${i.index}')">反选</a>
								)
							</span>
						</td>
						<td class="tdRight">
							<div style="width:5%;float: left; margin-left: 0px;padding: 0px; text-align: left;">
								<c:if test="${list.org2DutyCount>3  }">
									<a style="color: red" href="javascript:void(0)" id="s" onclick="dutyOpenDiv('${list.org2Code }',this)"><!-- 0是展开操作1是隐藏操作 -->
										展开
									</a>
								</c:if>
								<c:if test="${list.matrCount<=3  }">
								&nbsp;
								</c:if>
							</div>
							<div style="width:94%;float: left; margin-left: 0px;padding: 0px; text-align: left;">
							<c:forEach items="${list.dutyList}" var="duty" varStatus="j">
								<c:if test="${j.index<3 }">
									<div   style="width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;">
										<label > 
											<input type="checkbox" style="margin-left: 0px;" value="${duty.dutyCode }" name="dutys" class="org2${i.index }" onclick="selectPublicMatrs()"/>
										(${duty.dutyCode })<forms:StringTag length="17" direction="right" value="${duty.dutyName}"/></label>
									</div>
								</c:if>
								<c:if test="${j.index>=3 }">
									<div class="${list.org2Code }_hideDiv" style="width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;display: none">
											<label > 
												<input type="checkbox" style="margin-left: 0px;" value="${duty.dutyCode}" name="dutys" class="org2${i.index }" onclick="selectPublicMatrs()"/>
											(${duty.dutyCode })<forms:StringTag length="15" direction="right" value="${duty.dutyName}"/></label>
									</div>
								</c:if>
							</c:forEach>
							</div>
						</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				<table id="matrTables">
					<tr class="collspan-control">
						<th  width="20%" align="center">监控指标名称</th>
						<th   width="80%" align="center">物料</th>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				<table id="tjTable">
					<tr>
						<td class="tdLeft">物料归口部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" id="matrAuditDeptName" name="matrAuditDeptName"   class="base-input-text"  valid  />
							<input type="hidden" id="matrAuditDept" name="matrAuditDept"   class="base-input-text"     />
							<forms:OrgSelPlugin   suffix="0021" triggerEle="#tjTable tr matrAuditDeptName,matrAuditDept::name,id" rootNodeId="${userOrg1Code}" 
									rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="matrAuditDeptTree"  />
							
						</td>
						<td class="tdLeft">物料采购部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" id="matrBuyDeptName" name="matrBuyDeptName"   class="base-input-text"  valid  />
							<input type="hidden" id="matrBuyDept" name="matrBuyDept"   class="base-input-text"     />
							<forms:OrgSelPlugin   suffix="002" triggerEle="#tjTable tr matrBuyDeptName,matrBuyDept::name,id" rootNodeId="${userOrg1Code}" 
									rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="matrBuyDeptTree"  />
						</td>
					</tr>
					<tr>
					
						<td class="tdLeft">预算分解部门<span style="color: #A41E1E">*</span></td>
							<td class="tdRight">
								<input type="text" id="decomposeOrgName" name="decomposeOrgName"   class="base-input-text"  valid  />
								<input type="hidden" id="decomposeOrg" name="decomposeOrg"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="003" triggerEle="#tjTable tr decomposeOrgName,decomposeOrg::name,id" rootNodeId="${userOrg1Code}" 
										rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="decomposeOrgTree"  />
						</td>
						<td class="tdLeft">本级财务管理部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" id="ofincDeptSName" name="fincDeptSName"   class="base-input-text"  valid  />
								<input type="hidden" id="fincDeptS" name="fincDeptS"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="004" triggerEle="#tjTable tr fincDeptSName,fincDeptS::name,id" rootNodeId="${userOrg1Code}" 
										rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="fincDeptSTree"  />
						</td>
					</tr>
					<tr>
						
						<td class="tdLeft">二级分行财务管理部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
								<input type="text" id="fincDept2Name" name="fincDept2SName"   class="base-input-text"  valid  />
								<input type="hidden" id="fincDept2" name="fincDept2"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="005" triggerEle="#tjTable tr fincDept2Name,fincDept2::name,id" rootNodeId="${userOrg1Code}" 
										rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="fincDept2Tree"  />
						</td>
						<td class="tdLeft">一级分行财务管理部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" id="fincDept1Name" name="fincDept1Name"   class="base-input-text"  valid  />
								<input type="hidden" id="fincDept1" name="fincDept1"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="006" triggerEle="#tjTable tr fincDept1Name,fincDept1::name,id" rootNodeId="${userOrg1Code}" 
										rootLevel="1"   parentCheckFlag="false" dialogFlag="true" jsVarName="fincDept1Tree"  />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<p:button funcId="0812010209" value="提交"/>
							<input type="button" value="返回" onclick="backToLastPage('${uri}')">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
	</table>
</form>

</body>
</html>