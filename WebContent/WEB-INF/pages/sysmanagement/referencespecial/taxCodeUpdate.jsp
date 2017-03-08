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
<title>税码更新</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
 	
}

function doValidate(){
	//提交前调用检查
	if(!App.valid("#rForm")){return;} 
	var cglPerCnt=$("#totalNumTable tr");
	var decudtHidden=$("#decudtHidden").val();
	var regExp =new RegExp(/^(0|[1-9][0-9]{0,6})(\.[\d]{1,2})?$/);	
	var checkCgl=/^\d{4}$/;
	if(decudtHidden=='Y'){
		if(cglPerCnt.length<2){
			App.notyError("可抵扣税码必须对应至少一条房产类核算码！");
			return false;
		}
		else{
			for(var i=1;i<cglPerCnt.length;i++){
				var perCnt = $(cglPerCnt[i]).find("input[name='perCnts']").val(); // 百分比
				var cglCodes = $(cglPerCnt[i]).find("input[name='cglCodes']").val(); // 房租类核算码
				if(perCnt>0&&perCnt<100){
					if(!regExp.test(perCnt)){
						App.notyError("第"+i+"条百分比输入有误，请输入0到100（不包括0）的小数点后最多两位的数！");
						return false;
					}
				}
				else{
					if(perCnt!=100){
						App.notyError("第"+i+"条百分比输入有误，请输入0到100（不包括0）的小数点后最多两位的数！");
				        return false;
					}
				}
				if(!checkCgl.test(cglCodes)){
					App.notyError("第"+i+"条房产类核算码有误，请输入四位数字！");
					return false;
				}
			}
		}
		//校验百分比之和知否为100
		var totalPerCnt=0.00;
		for(var i=1;i<cglPerCnt.length;i++){
			var perCnt = $(cglPerCnt[i]).find("input[name='perCnts']").val();
			var floatVal=parseFloat(perCnt);
			totalPerCnt = totalPerCnt+floatVal;
		} 
		if(totalPerCnt!='100'){
			App.notyError("房产类核算码所分配的百分比之和必须为100！");
			return false;
		}
		//判断房产类核算码不能有相同的
		var c=new Array(cglPerCnt.length-1);
		var k=0;
		for(i=0;i<cglPerCnt.length-1;i++){
			c[k]=$(cglPerCnt[i+1]).find("input[name='cglCodes']").val();
			if(k<=cglPerCnt.length-1){
				k=k+1;
			}
		}
		var b;
		for(i=0;i<c.length;i++){
			b=c[i];
			for(j=i+1;j<c.length;j++){
				if(b==c[j]){
					App.notyError("进项税核算码不能有相同的！");
					return false;	
				}
			}
		}
		
	}
	$( "<div>确定修改此税码信息?</div>" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
		    url2 = "<%=request.getContextPath()%>/sysmanagement/referencespecial/taxCodeUpdate.do?<%=WebConsts.FUNC_ID_KEY%>=01120305";
			$("#rForm").attr("action",url2);
			$("#rForm").submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
				return false;
			}
		}
	});
}
function addTotalNum(){
	var appendTr = '<tr>'+
		'<td></td>'+
		'<td style="text-align:center"><input type="text" valid name="perCnts" class="base-input-text" maxlength="9" style="width:70px"/>&nbsp;&nbsp;&nbsp;&nbsp;%</td>'+//百分比
		'<td style="text-align:center"><input type="text" valid name="cglCodes" maxlength="4" class="base-input-text" style="width:70px"/></td>'+//房产类核算码
		'<td style="text-align:center"><a><img border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteTotalNum(this)"/></a></td>'+//操作
'</tr>';
$("#totalNumTable").append(appendTr);
}
//删除一行电子审批记录
function deleteTotalNum(obj){
	$(obj).parent().parent().parent().remove();
}
</script>
</head>
<body>
<p:authFunc funcArray="01120305"/>
<form action="" method="post" id="rForm">
<input type="hidden" id="decudtHidden" value="${bean.deductFlag}" name="deductFlag" >
	<p:token/>
	<table class="tableList">
		<tr>
			<th colspan="2">
				税码更新
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%">税码</td>
			<td class="tdRight" width="50%">${bean.taxCode}
				<input type="hidden" id="taxCode" name="taxCode" value="${bean.taxCode}" class="base-input-text" maxlength="7" readonly="readonly"/>	
			</td>
		</tr>
		<tr>
			<td class="tdLeft">税率</td>
			<td class="tdRight"><fmt:formatNumber type='number' value="${bean.taxRate}" minFractionDigits="4"/>
			</td>
		</tr>		
		<tr>
			<td class="tdLeft">是否可抵扣</td>
			<td class="tdRight">
			${bean.deductFlagName}
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>是否产生税行</td>
			<td class="tdRight">
				<div class="base-input-radio" id="hasTaxrowDiv">
					<label for="hasTaxrow1" onclick="App.radioCheck(this,'hasTaxrowDiv')" <c:if test="${bean.hasTaxrow=='Y'}">class="check-label"</c:if>>产生</label><input type="radio" id="hasTaxrow1" name="hasTaxrow" value="Y" <c:if test="${bean.hasTaxrow=='Y'}">checked="checked"</c:if>/>
					<label for="hasTaxrow2" onclick="App.radioCheck(this,'hasTaxrowDiv')" <c:if test="${bean.hasTaxrow=='N'}">class="check-label"</c:if>>不产生</label><input type="radio" id="hasTaxrow2" name="hasTaxrow" value="N" <c:if test="${bean.hasTaxrow=='N'}">checked="checked"</c:if>/>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>是否启用</td>
			<td class="tdRight">
				<div class="base-input-radio" id="validFlagDiv">
					<label for="validFlag1" onclick="App.radioCheck(this,'validFlagDiv')" <c:if test="${bean.validFlag=='Y'}">class="check-label"</c:if>>启用</label><input type="radio" id="validFlag1" name="validFlag" value="Y" <c:if test="${bean.validFlag=='Y'}">checked="checked"</c:if>/>
					<label for="validFlag2" onclick="App.radioCheck(this,'validFlagDiv')" <c:if test="${bean.validFlag=='N'}">class="check-label"</c:if>>停用</label><input type="radio" id="validFlag2" name="validFlag" value="N" <c:if test="${bean.validFlag=='N'}">checked="checked"</c:if>/>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">进项税核算码</td>
			<td class="tdRight">
			${bean.cglCode}
			</td>
		</tr>
		<c:if test="${!empty perCntCglList}">
			<tr id="DZSPTr"class="collspan">
				<td colspan="2" >
					<table id="totalNumTable" >
							<tr id="thTr">
								<th width="20%"><a><img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick="addTotalNum()"></a>&nbsp;&nbsp;可抵扣核算码比例设置</th>			
								<th width='40%'>百分比</th>
								<th width='30%'>房产类核算码</th>
								<th width='10%'>操作</th>
							</tr>
							<c:forEach items="${perCntCglList}" var="tl" varStatus="status">
							<tr>
								<td></td>
								<td style="text-align:center"><input type="text" valid name="perCnts" class="base-input-text" maxlength="9" style="width:70px" value="<fmt:formatNumber type="number" value="${tl.perCnt*100}" minFractionDigits="2"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;%</td>
								<td style="text-align:center"><input type="text" valid name="cglCodes" maxlength="4" class="base-input-text" style="width:70px" value="${tl.cglCode}"/></td>
								<td style="text-align:center"><a><img border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif" onclick="deleteTotalNum(this)"/></a></td>
							</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>	
	</table>
	<br>
		<div style="text-align:center;" >
			<p:button funcId="01120305" value="更新"/>
			<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
	   </div>
</form>
</body>
</html>