<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@page import="com.forms.platform.web.consts.WebConsts"%><html>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>省行统购审批链新增</title>
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
//全选
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
	return true;
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
function add(){
	if($("input[name='matrs']").is(":checked")){
	}else{
		App.notyError("物料不能为空！");
		return false;
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
		var decomposeCodeList = decomposeOrgTree.getSelectOrgList();
		if(decomposeCodeList.length < 1){
			App.notyError("预算分解部门不能为空，请选择物料分解部门！");
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
		$("#reqtypeForm").attr("action","<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specTgAdd.do?<%=WebConsts.FUNC_ID_KEY %>=0812010207");
		$("#reqtypeForm").submit();
}
function isProvince(type){
	var feeCode =$("#feeCode").val();
	var data = {};
	data['feeCode'] =  feeCode;
	data['isProvinceBuy'] =  type;
	App.ajaxSubmit("sysmanagement/approveChainMgr/isProvince.do?<%=WebConsts.FUNC_ID_KEY%>=0812010306",{data : data,async:false}, function(data) {
		var montMatrs=data.data;
		var htm="";
		htm+="<tr><th  width='20%' align='center'>监控指标名称</td><th   width='80%' align='center'>物料</td></tr>";
		for(var i=0;i<montMatrs.length;i++){
			var montName = montMatrs[i].montName;
			var montCode = montMatrs[i].montCode;
			var matrList = montMatrs[i].matrs;
			var montCount = montMatrs[i].montCount;
			var trHtm ="";
			trHtm += "<tr><td >";
			trHtm+=montName;
			trHtm+="<span style='color:red'>(";
			trHtm+="<a style='color:red' href='javascript:void(0)' id='selectAllId' name='1' onclick='selectAll(this, \"mont"+i+"\")'>全选</a>";
			trHtm+="<a style='color:red' href='javascript:void(0)' onclick='selectOther(this, \"mont"+i+"\")'>反选</a>";
			trHtm+=")</span>";
			trHtm+="</td>";
			trHtm+="<td>";
			trHtm+="<div style='width:5%;float: left; margin-left: 0px;padding: 0px; text-align: left;'>";
			if(montCount>3){
				trHtm+="<a style='color: red' href='javascript:void(0)' id='s' onclick='openDiv(\""+montCode+"\",this)'>";
				trHtm+="展开</a>";
			}else{
				trHtm+=" ";
			}
			trHtm+="</div>";
			trHtm+="<div style='width:94%;float: left; margin-left: 0px;padding: 0px; text-align: left;'>";
			var matrHtm="";
			for(var j=0;j<matrList.length;j++){
				var matrName = matrList[i].matrName;
				var matrCode = matrList[i].matrCode;
				if(j<3){
					matrHtm+="<div   style='width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;'>";
					matrHtm+="<label>";
					matrHtm+="<input type='checkbox' style='margin-left: 0px;' value='"+montCode+"_"+matrCode+"' name='matrs' class='mont"+i+"'/>";
					matrHtm+="("+matrCode+")";
					var showName = "";
					if(matrName.length>10){
						showName = matrName.substring(matrName.length-10);
					}else{
						showName = matrName;
					}
					matrHtm+=showName+"</label>";
					matrHtm+="</div>"; 
				}else{
					matrHtm+="<div   style='width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;display: none'>";
					matrHtm+="<label>";
					matrHtm+="<input type='checkbox' style='margin-left: 0px;' value='"+montCode+"_"+matrCode+"' name='matrs' class='mont"+i+"'/>";
					matrHtm+="("+matrCode+")";
					var showName = "";
					if(matrName.length>10){
						showName = matrName.substring(matrName.length-10);
					}else{
						showName = matrName;
					}
					matrHtm+=showName+"</label>";
					matrHtm+="</div>";
				}
				
			}
			
			trHtm+=matrHtm;
			trHtm+="</div></td></tr>";
			htm+=trHtm;
		}
		$("#approveChainTable").html(htm);
	});
}
</script>
</head>
<body>

<p:authFunc funcArray="01060109,0812010305"/>
<form action="" method="post" id="reqtypeForm">
	<input type="hidden" value="${bean.dutyCode }" name="feeCode" id="feeCode"/>
	<input type="hidden" value="${bean.aprvType }" name="aprvType" id="aprvType"/>
	<input type="hidden" value="${bean.dataYear }" name="dataYear" id="dataYear"/>
	<input type="hidden" name="isProvinceBuy" value="0"/>
<p:token/>
<table>

	<tr> <td>
  <table id="approveChainTable">
         <tr>
			<th  width="20%" align="center">监控指标名称</th>
			<th   width="80%" align="center">物料</th>
		<tr>
		<c:forEach items="${specNoWhMatrs}" var="list" varStatus="i">
		<tr>
			<td align="left" class="tdLeft">
				${list.montName }
				<span style="color:red">
					(
					<a style="color:red" href="javascript:void(0)" id="selectAllId"   onclick="selectAll(this, 'mont${i.index}')">全选</a>
					<a style="color:red" href="javascript:void(0)" onclick="selectOther(this, 'mont${i.index}')">反选</a>
					)
				</span>
			</td>
			<td class="tdRight">
				<div style="width:5%;float: left; margin-left: 0px;padding: 0px; text-align: left;">
					<c:if test="${list.matrCount>3  }">
						<a style="color: red" href="javascript:void(0)" id="s" onclick="openDiv('${list.montCode }',this)"><!-- 0是展开操作1是隐藏操作 -->
							展开
						</a>
					</c:if>
					<c:if test="${list.matrCount<=3  }">
					&nbsp;
					</c:if>
				</div>
				<div style="width:94%;float: left; margin-left: 0px;padding: 0px; text-align: left;">
				<c:forEach items="${list.matrs}" var="matrs" varStatus="j">
					<c:if test="${j.index<3 }">
						<div   style="width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;">
							<label > 
								<input type="checkbox" style="margin-left: 0px;" value="${list.montCode }_${matrs.matrCode }" name="matrs" class="mont${i.index }"/>
							(${matrs.matrCode })<forms:StringTag length="10" direction="right"  value="${matrs.matrName}"/></label>
						</div>
					</c:if>
					<c:if test="${j.index>=3 }">
						<div class="${list.montCode }_hideDiv" style="width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;display: none">
								<label title="sdfdfdfdfdf"> 
									<input type="checkbox" style="margin-left: 0px;" value="${list.montCode }_${matrs.matrCode }" name="matrs" class="mont${i.index }"/>
								(${matrs.matrCode })<forms:StringTag direction="right" length="10" value="${matrs.matrName}"/></label>
						</div>
					</c:if>
				</c:forEach>
				</div>
			</td>
		</tr>
		</c:forEach>
	</table>
	</td>
	<tr><td></td></tr>
	<tr><td>
	<table id="tjTable">
		<tr>
			<th colspan="4">${bean.dutyName}审批链维护</th>
		</tr>

		<tr>
			<td class="tdLeft">物料归口部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
				<input type="text" id="matrAuditDeptName" name="matrAuditDeptName"   class="base-input-text"  valid  />
				<input type="hidden" id="matrAuditDept" name="matrAuditDept"   class="base-input-text"     />
				<forms:OrgSelPlugin   suffix="0021" triggerEle="#tjTable tr matrAuditDeptName,matrAuditDept::name,id" rootNodeId="${bean.org1Code}" 
						rootLevel="1"   parentCheckFlag="false"  jsVarName="matrAuditDeptTree"  />
				
			</td>
			<td class="tdLeft">物料采购部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
				<input type="text" id="matrBuyDeptName" name="matrBuyDeptName"   class="base-input-text"  valid  />
				<input type="hidden" id="matrBuyDept" name="matrBuyDept"   class="base-input-text"     />
				<forms:OrgSelPlugin   suffix="002" triggerEle="#tjTable tr matrBuyDeptName,matrBuyDept::name,id" rootNodeId="${bean.org1Code}" 
						rootLevel="1"   parentCheckFlag="false"  jsVarName="matrBuyDeptTree"  />
			</td>
		</tr>
		<tr>
		
			<td class="tdLeft">预算分解部门<span style="color: #A41E1E">*</span></td>
				<td class="tdRight">
					<input type="text" id="decomposeOrgName" name="decomposeOrgName"   class="base-input-text"  valid  />
					<input type="hidden" id="decomposeOrg" name="decomposeOrg"   class="base-input-text"     />
					<forms:OrgSelPlugin   suffix="003" triggerEle="#tjTable tr decomposeOrgName,decomposeOrg::name,id" rootNodeId="${bean.org1Code}" 
							rootLevel="1"   parentCheckFlag="false"  jsVarName="decomposeOrgTree"  />
			</td>
			<td class="tdLeft">本级财务管理部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
				<input type="text" id="ofincDeptSName" name="fincDeptSName"   class="base-input-text"  valid  />
					<input type="hidden" id="fincDeptS" name="fincDeptS"   class="base-input-text"     />
					<forms:OrgSelPlugin   suffix="004" triggerEle="#tjTable tr fincDeptSName,fincDeptS::name,id" rootNodeId="${bean.org1Code}" 
							rootLevel="1"   parentCheckFlag="false"  jsVarName="fincDeptSTree"  />
			</td>
		</tr>
		<tr>
			
			<td class="tdLeft">二级分行财务管理部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
					<input type="text" id="fincDept2Name" name="fincDept2SName"   class="base-input-text"  valid  />
					<input type="hidden" id="fincDept2" name="fincDept2"   class="base-input-text"     />
					<forms:OrgSelPlugin   suffix="005" triggerEle="#tjTable tr fincDept2Name,fincDept2::name,id" rootNodeId="${bean.org1Code}" 
							rootLevel="1"   parentCheckFlag="false"  jsVarName="fincDept2Tree"  />
			</td>
			<td class="tdLeft">一级分行财务管理部门<span style="color: #A41E1E">*</span></td>
			<td class="tdRight">
				<input type="text" id="fincDept1Name" name="fincDept1Name"   class="base-input-text"  valid  />
					<input type="hidden" id="fincDept1" name="fincDept1"   class="base-input-text"     />
					<forms:OrgSelPlugin   suffix="006" triggerEle="#tjTable tr fincDept1Name,fincDept1::name,id" rootNodeId="${bean.org1Code}" 
							rootLevel="1"   parentCheckFlag="false"  jsVarName="fincDept1Tree"  />
			</td>
		</tr>
	</table>
	
	</td></tr>
	<tr>
			<td colspan="4" align="center">
				<input type="button" value="提交" onclick="add()"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
</form>

</body>
</html>