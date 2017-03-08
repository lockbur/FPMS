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
<title>专项包审批链新增</title>
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
function specAdd(){
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
	$("#noUpdateForm").attr("action","<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specAdd.do?<%=WebConsts.FUNC_ID_KEY %>=0812010201");
	$("#noUpdateForm").submit();
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
	if(!App.valid("#noUpdateForm")){
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
//上一步
function  backPage(){
	$("#tabsIndexSkip").val("1");
	var url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/specList.do?<%=WebConsts.FUNC_ID_KEY %>=08120102';
	$('#noUpdateForm').attr("action",url);
	App.submit( $("#noUpdateForm"));
}
</script>
</head>
<body>

<p:authFunc funcArray="01060109,0812010305"/>
<form action="" method="post" id="noUpdateForm">
<input type="hidden" name="tabsIndex" id="tabsIndexSkip">
<input type="hidden" name="org1Code" value="${selectInfo.org1Code }"/>
<input type="hidden" name="dataYear" value="${selectInfo.dataYear }"/>
<p:token/>
<table>
				<tr> <td>
				<table id="approveChainTable">
					<tr>
						<th colspan="2">${selectInfo.org1Name}审批链维护</th>
					</tr>
			         <tr>
						<th  width="20%" align="center">监控指标名称</td>
						<th   width="80%" align="center">物料</td>
					<tr>
					<c:forEach items="${noUpdateList}" var="list" varStatus="i">
					<tr>
						<td align="left" class="tdLeft" >
						<c:choose>
							<c:when test="${list.montCode eq '-1' }">
								<span style="color:red">这些物料没有分配给监控指标，不能维护进审批链</span>
							</c:when>
							<c:otherwise>
								<span title="${list.montCode}">${list.montName }</span>
								<span style="color:red">
									(
									<a style="color:red" href="javascript:void(0)" id="selectAllId" name="1" onclick="selectAll(this, 'mont${i.index}')">全选</a>
									<a style="color:red" href="javascript:void(0)" onclick="selectOther(this, 'mont${i.index}')">反选</a>
									)
								</span>
							</c:otherwise>
						</c:choose>
							
							<!-- 
							<input type="checkbox" style="margin-left: 0px;" value="${list.montCode }" id="mont${i.index}" onclick="selectAll(this, 'mont${i.index}')" />
							 -->
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
										<label title="sdfdfsdfdfdfdfsdfd"><c:if test="${list.montCode != '-1' }">
											<input type="checkbox" style="margin-left: 0px;" value="${list.montCode }_${matrs.matrCode }" name="matrs" class="mont${i.index }"/>
										</c:if>
										(${matrs.matrCode })<forms:StringTag length="10" direction="right"  value="${matrs.matrName}"/></label>
									</div>
								</c:if>
								<c:if test="${j.index>=3 }">
									<div class="${list.montCode }_hideDiv" style="width:33%;float: left; margin-left: 0px;padding: 0px; text-align: left;display: none">
											<label title="sdfdfdfdfdf"><c:if test="${list.montCode != '-1' }">
												<input type="checkbox" style="margin-left: 0px;" value="${list.montCode }_${matrs.matrCode }" name="matrs" class="mont${i.index }"/>
											</c:if>
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
						<td class="tdLeft">物料归口部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" readonly="readonly" id="matrAuditDeptName" name="matrAuditDeptName"   class="base-input-text"  valid  />
							<input type="hidden" id="matrAuditDept" name="matrAuditDept"   class="base-input-text"     />
							<forms:OrgSelPlugin   suffix="0031" triggerEle="#tjTable tr matrAuditDeptName,matrAuditDept::name,id" rootNodeId="${selectInfo.org1Code}" 
									rootLevel="1"   parentCheckFlag="false"  jsVarName="matrAuditDeptTree"  />
							
						</td>
						<td class="tdLeft">物料采购部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" readonly="readonly" id="matrBuyDeptName" name="matrBuyDeptName"   class="base-input-text"  valid  />
							<input type="hidden" id="matrBuyDept" name="matrBuyDept"   class="base-input-text"     />
							<forms:OrgSelPlugin   suffix="002" triggerEle="#tjTable tr matrBuyDeptName,matrBuyDept::name,id" rootNodeId="${selectInfo.org1Code}" 
									rootLevel="1"   parentCheckFlag="false"  jsVarName="matrBuyDeptTree"  />
						</td>
					</tr>
					<tr>
						<td class="tdLeft">本级财务管理部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
							<input type="text" readonly="readonly" id="ofincDeptSName" name="fincDeptSName"   class="base-input-text"  valid  />
								<input type="hidden" id="fincDeptS" name="fincDeptS"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="004" triggerEle="#tjTable tr fincDeptSName,fincDeptS::name,id" rootNodeId="${selectInfo.org1Code}" 
										rootLevel="1"   parentCheckFlag="false"  jsVarName="fincDeptSTree"  />
						</td>
						<td class="tdLeft">二级分行财务管理部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight">
								<input type="text" readonly="readonly" id="fincDept2Name" name="fincDept2SName"   class="base-input-text"  valid  />
								<input type="hidden" id="fincDept2" name="fincDept2"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="005" triggerEle="#tjTable tr fincDept2Name,fincDept2::name,id" rootNodeId="${selectInfo.org1Code}" 
										rootLevel="1"   parentCheckFlag="false"  jsVarName="fincDept2Tree"  />
						</td>
					</tr>
					<tr>
						
						
						<td class="tdLeft">一级分行财务管理部门<span style="color: #A41E1E">*</span></td>
						<td class="tdRight" colspan="3">
							<input type="text" readonly="readonly" id="fincDept1Name" name="fincDept1Name"   class="base-input-text"  valid  />
								<input type="hidden" id="fincDept1" name="fincDept1"   class="base-input-text"     />
								<forms:OrgSelPlugin   suffix="006" triggerEle="#tjTable tr fincDept1Name,fincDept1::name,id" rootNodeId="${selectInfo.org1Code}" 
										rootLevel="1"   parentCheckFlag="false"  jsVarName="fincDept1Tree"  />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" onclick="backPage()" value="上一步"/>
							<input type="button" onclick="specAdd()" value="提交"/>
						</td>
					</tr>
				</table>
				
				</td></tr>
				</table>
</form>

</body>
</html>