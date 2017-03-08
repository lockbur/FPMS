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
<title>税码新增</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">

function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
 	
}

//校验参数编码是否存在
function checkTaxCode(){
	var taxCode = $("#taxCode").val();
	var isPass = false;
	var data = {};
	data['taxCode'] = taxCode;
	var url = "sysmanagement/referencespecial/checkTaxCode.do?VISIT_FUNC_ID=01120302";
	App.ajaxSubmit(url,{data:data,async:false},
			function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("税码已存在，请重新输入!");
					isPass =  false;
				}
				else
				{
					isPass =  true;
				} 
			});
	return isPass;
}

function doValidate(){
	//提交前调用检查
	if(!App.valid("#rForm")){return;} 
	if(!checkTaxCode())
	{
		return false;
	}
	//校验税率是否合法
	var taxRate=$("#taxRate").val();
	if(taxRate>0&&taxRate<100){
		var checkTaxRate =new RegExp(/^(0|[1-9][0-9]{0,1})(\.[\d]{1,2})?$/);	
		if(!checkTaxRate.test(taxRate)){
			App.notyError("税率格式有误，只能是0到100之间小数点后最多二位！");
	        return false;
		}
	}
	else{
		if(taxRate!=100&&taxRate!=0){
			App.notyError("税率格式有误，只能是0到100之间小数点后最多二位！");
	        return false;
		}
	}
	//当税率大于0时必须选择产生税行
	if(taxRate>0){
		var chkObjs=document.getElementsByName("hasTaxrow");
		for(var i=0;i<chkObjs.length;i++){
			if(chkObjs[i].checked){
				var checkVal=chkObjs[i].value;
				if(checkVal=='N'){
					App.notyError("税率大于0时必须选择产生税行！");
			        return false;
				}
			}
		}
	}
	/* if(taxRate>0&&taxRate<1){
		if(!checkTaxRate.test(taxRate)){
			App.notyError("税率格式有误，只能是0到1之间小数点后四位！");
	        return false;
		}
	}
	else{
		if(taxRate!=1&&taxRate!=0){
			App.notyError("税率格式有误，只能是0到1之间小数点后四位！");
	        return false;
		}
	} */
	//校验核算码
	var checkCgl=/^\d{4}$/;
	var cglCode=$("#cglCode").val();
	if(cglCode!=""&&cglCode!=null){
		if(!checkCgl.test(cglCode)){
			App.notyError("进项税核算码有误，只能4位数字！");
	        return false;	
		}
	}
	var cglPerCnt=$("#totalNumTable tr");
	var decudtHidden=$("#decudtHidden").val();
	var regExp =new RegExp(/^(0|[1-9][0-9]{0,1})(\.[\d]{1,2})?$/);	
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
	var taxCode=$("#taxCode").val();
	$( "<div>此税码为"+taxCode+",税率为"+taxRate+"%;是否确认新增?</div>" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
		    url2 = "<%=request.getContextPath()%>/sysmanagement/referencespecial/taxCodeAdd.do?<%=WebConsts.FUNC_ID_KEY%>=01120303";
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
function changeCgl(deductFlag){
	if(deductFlag=='Y'){
		$("#decudtHidden").val("Y");
		$("#cglTr").show();
		$("#cglCode").attr("valid","valid");
		$("#DZSPTr").show();
	}
	else{
		$("#decudtHidden").val("N");
		$("#cglTr").hide();
		$("#DZSPTr").hide();
		$("#totalNumTable").find("tr[id!='thTr']").each(function(){
			$(this).remove();
		}); 
		$("#cglCode").val("");
		$("#cglCode").removeAttr("valid");
		}
	
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
<p:authFunc funcArray="01120303"/>
<form action="<%=request.getContextPath()%>/sysmanagement/referencespecial/referenceAdd.do" method="post" id="rForm">
<input type="hidden" id="decudtHidden" value="Y" >
	<p:token/>
	<table class="tableList">
		<tr>
			<th colspan="2">
				税码新增
			</th>
		</tr>
		<tr>
		    <td class="tdLeft" width="50%"><span class="red">*</span>税码</td>
			<td class="tdRight" width="50%">
				<input id="taxCode" name="taxCode" maxlength="15" style="width:140px" valid class="base-input-text" type="text" onblur="checkTaxCode();"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>税率</td>
			<td class="tdRight">
				<input id="taxRate" name="taxRate" maxlength="9" valid class="base-input-text" type="text" style="width:80px" />&nbsp;&nbsp;&nbsp;&nbsp;%<span class="red">(0到100且最多含两位小数的浮点数)</span>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>是否产生税行</td>
			<td class="tdRight">
				<div class="base-input-radio" id="hasTaxrowDiv">
					<label for="hasTaxrow1" onclick="App.radioCheck(this,'hasTaxrowDiv')" class="check-label">产生</label><input type="radio" id="hasTaxrow1" name="hasTaxrow" value="Y" checked="checked" />
					<label for="hasTaxrow2" onclick="App.radioCheck(this,'hasTaxrowDiv')" >不产生</label><input type="radio" id="hasTaxrow2" name="hasTaxrow" value="N" />
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>是否启用</td>
			<td class="tdRight">
				<div class="base-input-radio" id="validFlagDiv">
					<label for="validFlag1" onclick="App.radioCheck(this,'validFlagDiv')" class="check-label">启用</label><input type="radio" id="validFlag1" name="validFlag" value="Y" checked="checked" />
					<label for="validFlag2" onclick="App.radioCheck(this,'validFlagDiv')" >停用</label><input type="radio" id="validFlag2" name="validFlag" value="N" />
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>是否可抵扣</td>
			<td class="tdRight">
				<div class="base-input-radio" id="deductFlagDiv">
					<label for="deductFlag1" onclick="App.radioCheck(this,'deductFlagDiv')" class="check-label">可抵扣</label><input type="radio" id="deductFlag1" name="deductFlag" value="Y" checked="checked" onclick="changeCgl('Y')"/>
					<label for="deductFlag2" onclick="App.radioCheck(this,'deductFlagDiv')" >不可抵扣</label><input type="radio" id="deductFlag2" name="deductFlag" value="N" onclick="changeCgl('N')"/>
				</div>
			</td>
		</tr>
		<tr id="cglTr">
			<td class="tdLeft"><span class="red">*</span>进项税核算码</td>
			<td class="tdRight">
				<input id="cglCode" name="cglCode" maxlength="4" valid class="base-input-text" type="text" style="width: 30px" />
			</td>
		</tr>
		<tr id="DZSPTr"class="collspan">
			<td colspan="2" >
				<table id="totalNumTable" >
						<tr id="thTr">
							<th width="20%"><a><img border="0" width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick="addTotalNum()"></a>&nbsp;&nbsp;可抵扣核算码比例设置</th>			
							<th width='40%'>百分比</th>
							<th width='30%'>房产类核算码</th>
							<th width='10%'>操作</th>
						</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
		<div style="text-align:center;" >
			<p:button funcId="01120303" value="提交"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
	</div>
</form>
</body>
</html>