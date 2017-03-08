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
<title>物料新增</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/check.js"></script>
<script type="text/javascript">

 function checkMatrCode(){
	 	var list = false;
	 	var data = {};
		data['matrCode'] =  $("#matrCode").val();
		data['cglCode'] = '';
		var url = "sysmanagement/matrtype/checkMatrType.do?<%=WebConsts.FUNC_ID_KEY%>=01080502";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
				list = data.isExist;
		});
		return list;

}
 
 function contralPrepaidCode(type){
	if(type == '1'){
		$("#lCode,#dCode").hide();
		$("#longPrepaidCode").val('');
		$("#shortPrepaidCode").val('');
		
		$("#tr_isNotinfee").hide();
		$("#tr_isPrepaidProvision").hide();	//是否是待摊预提
		$("#prepaidCodeSpan").hide();
		$("#cglCode").val('');
		
		
		$("#tr_isGDZC").show();	//资产分类
		$("#tr_isFcwl").show();	//是否是房产类物料
	} 	
	else{
		$("#lCode,#dCode").show();
		$("#tr_isNotinfee").show();	
		$("#tr_isPrepaidProvision").show();	//是否是待摊预提
		$("#prepaidCodeSpan").hide();
		$("#cglCode").val('');
		
		$("#tr_isGDZC").hide();	//资产分类
		$("#tr_isFcwl").hide();	//是否是房产类物料
	}	
 }
 
 //
 function resetAll(){
	$(":text").val("");
	$("#longPrepaidCode").val('');
	$("#shortPrepaidCode").val('');  
	$("#memo").val('');
	$("#defrayedLogSpan").text('0/100');
	$("#longPrepaidCode,#shortPrepaidCode").attr("valid","valid");
	$("#matrType").find("label").eq(0).click();
	$("#isGDZC").find("label").eq(0).click();
	$("#isFcwl").find("label").eq(0).click();
	$("#isNotinfee").find("label").eq(0).click();
	$("#isOrder").find("label").eq(0).click();
	$("#isPrepaidProvision").find("label").eq(0).click();
 }
 
 //根据输入的核算码获取对应的长短期待摊核算码
 function get8521Code(feeCode){
	 if(feeCode.length == 4){
    	var data = {};
    	data['matrCode'] = '';
		data['cglCode'] = feeCode;		
		App.ajaxSubmit("sysmanagement/matrtype/checkMatrType.do?<%=WebConsts.FUNC_ID_KEY %>=01080502",
				{data:data,async:false}, 
				function(data) {
					var result=data.isExist;
					if(result){
						$("#longPrepaidCode").val(data.longPrepaidCode);
						$("#shortPrepaidCode").val(data.shortPrepaidCode); 
						$("#longPrepaidCode,#shortPrepaidCode").attr("disabled",true);
					}else{
						$("#longPrepaidCode").val('');
						$("#shortPrepaidCode").val('');  
						$("#longPrepaidCode,#shortPrepaidCode").attr("disabled",false);
					}
				}
		);
	 } else{
		 $("#longPrepaidCode").val('');
		 $("#shortPrepaidCode").val(''); 
		 $("#longPrepaidCode,#shortPrepaidCode").attr("disabled","false");
	 };
 }

 function checkCglCode(){
	 	var list = false;
	 	var matrType={};
	 	var data = {};
		data['cglCode'] =  $("#cglCode").val();
		var url = "sysmanagement/matrtype/checkCglCode.do?VISIT_FUNC_ID=01080503";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
				list = data.isExist;
				matrType=data.matrType;
		});
		if(list){
			$("#longPrepaidCode,#shortPrepaidCode").hide();
			$("#longPrepaidCode,#shortPrepaidCode").removeAttr("valid");
			$("#longPrepaidCode").val(matrType.longPrepaidCode);
			$("#shortPrepaidCode").val(matrType.shortPrepaidCode);
			$("#longCodeSpan,#shortCodeSpan").show();
			$("#longCodeSpan").text(matrType.longPrepaidCode);
			$("#shortCodeSpan").text(matrType.shortPrepaidCode);
		}
		else{
			$("#longPrepaidCode,#shortPrepaidCode").show();			
			$("#longPrepaidCode,#shortPrepaidCode").attr("valid","valid");
			$("#longPrepaidCode").val("");
			$("#shortPrepaidCode").val("");
			$("#longCodeSpan,#shortCodeSpan").hide();
		}
	}
function checkMatr1Code(){
	var matrCode=$("#matrCode").val();
	if ($("input[name='matrType']:checked").val()!=matrCode[0]) {
 		App.notyError("物料编码资产类1开头，费用类3开头，请检查后重试！");
		return false;
	}
	return true;
} 
function doValidate(){	
	var matrType = $("input[name='matrType']:checked").val();
	var isPrepaidProvision = $("input[name='isPrepaidProvision']:checked").val();
	if("3" == matrType){
		$("#longPrepaidCode,#shortPrepaidCode").removeAttr("valid");
	}	
	if(!App.valid("#matrForm")){return false;} 
	if(!checkMatr1Code()){		
		return false;
	}
	if (checkMatrCode()) {
		App.notyError("物料编码已存在，请重新输入！");
		return false;
	}
	var cglCode=$("#cglCode").val();
	//var provisionCode=$("#provisionCode").val();
	//var prepaidCode=$("#prepaidCode").val();
	var longPrepaidCode = $("#longPrepaidCode").val();
	var shortPrepaidCode = $("#shortPrepaidCode").val();
 	var matrCode=$("#matrCode").val();
 	if (matrCode.length!=11) {
 		App.notyError("物料编码长度不为11，请检查！");
		return false;
	}
	if (cglCode.length!=4) {
		App.notyError("核算码长度不为4，请检查！");
		return false;
	}
	if('3' == matrCode[0]){
		/* if(longPrepaidCode == '' || null == longPrepaidCode)
		{
			App.notyError("请输入待摊核算码（长期）");
		}
		if(shortPrepaidCode == '' || null == shortPrepaidCode)
		{
			App.notyError("请输入待摊核算码（短期）");
		}
		
		if (longPrepaidCode.length!=4) {
			App.notyError("长期待摊核算码长度不为4，请检查！");
			return false;
		}
		if (shortPrepaidCode.length!=4) {
			App.notyError("短期待摊核算码长度不为4，请检查！");
			return false;
		} */
		if(isPrepaidProvision=='Y'){
			if($("#longPrepaidCode").val()==''||$("#shortPrepaidCode").val()==''){
				App.notyError("请选择有长短期待摊预提核算码的费用核算码！");
				return false;
			}
		}
	}
	/**if (provisionCode.length!=4) {
		App.notyError("预提科目长度不为4，请检查！");
		return false;
	}
	if (prepaidCode.length!=4) {
		App.notyError("待摊科目长度不为4，请检查！");
		return false;
	}*/	
	if(!confirm('是否确认增加？')){
		return false;
	}
	return true;
}

function queryCglCode(obj,flag){
	var matrType = $("input[name='matrType']:checked").val();
	
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/matrtype/searchCglCode.do?<%=WebConsts.FUNC_ID_KEY %>=01080504&compare='+"common",
			{
				width: "60%",
				height: "80%",
				lock: true,
			    fixed: true,
			    title:"费用核算码选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('proObj'); 
					if(proObj){
						$("#cglCode").val(proObj.cglCode);
						$("#shortPrepaidCode").val(proObj.shortPrepaidCode);
						$("#longPrepaidCode").val(proObj.longPrepaidCode);
						if(proObj.shortPrepaidCode == '' || null == proObj.shortPrepaidCode){
							proObj.shortPrepaidCode = '无';
						}
						if(proObj.longPrepaidCode == '' || null == proObj.longPrepaidCode){
							proObj.longPrepaidCode = '无';
						}
						if(proObj.longPrepaidCodeFee == '' || null == proObj.longPrepaidCodeFee){
							proObj.longPrepaidCodeFee = '无';
						}
						$("#shortPrepaidCodeSpan")[0].innerText=proObj.shortPrepaidCode;
						$("#longPrepaidCodeSpan")[0].innerText=proObj.longPrepaidCode;
						$("#longPrepaidCodeFeeSpan")[0].innerText=proObj.longPrepaidCodeFee;
						
						if("3" == matrType){
							
							$("#prepaidCodeSpan").show();
						}
					}
				}		
			}
		 );
}

</script>
</head>
<body>

<p:authFunc funcArray="01080501"/>
<form  method="post" id="matrForm">
  <p:token/>
  <table>
       <tr>
          <th colspan="2">
                                     物料新增
          </th>
       </tr>
       <tr >
            <td class="tdLeft">物料类型</td>
			<td class="tdRight">
				<div class="base-input-radio" id="matrType">
					<label for="matrType2" onclick="App.radioCheck(this,'matrType'), contralPrepaidCode(0)" class="check-label">费用</label><input type="radio" id="matrType2" name="matrType" value="3" checked="checked"/>
					<label for="matrType1" onclick="App.radioCheck(this,'matrType'), contralPrepaidCode(1)" >资产</label><input type="radio" id="matrType1" name="matrType" value="1" />
				</div>
			</td>
       </tr>
       <tr>
       		<td class="tdLeft" width="30%"><span class="red">*</span>物料编码</td>
			<td class="tdRight" id="matrTypeTd" width="70%">
				<input style="width: 80px;" id="matrCode" name="matrCode" maxlength="11" class="base-input-text" valid errorMsg="请输入物料编码。" type="text" />长度为11位		
			</td>
       </tr>
       <tr>
       		<td class="tdLeft"><span class="red">*</span>物料名称</td>
			<td class="tdRight" id="matrNameTd">
				<input id="matrName" name="matrName" maxlength="300" class="base-input-text" valid errorMsg="请输入物料名称。"  type="text" />不超过300位字符(150个汉字)
			</td>
       </tr>
       <tr>
            <td class="tdLeft"><span class="red">*</span>物料单位</td>
			<td class="tdRight">
				<input style="width: 70px;" id="matrUnit" name="matrUnit" maxlength="10" valid errorMsg="请输入物料单位。" class="base-input-text" type="text" />最大长度10位
			</td>
       </tr>
       <tr>
            <td class="tdLeft"><span class="red">*</span>费用核算码</td>
			<td class="tdRight">
				<input style="width: 40px;" id="cglCode" name="cglCode" maxlength="4" class="base-input-text" readonly onclick="queryCglCode(this,'1')" valid errorMsg="请输入核算码。" type="text" />
				<span id="prepaidCodeSpan" style="display: none">
				&nbsp;&nbsp;&nbsp;&nbsp;
				长期-待摊核算码:<span  id="longPrepaidCodeSpan"></span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				短期-待摊核算码:<span  id="shortPrepaidCodeSpan"></span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				长期待摊固定对应的费用核算码:<span  id="longPrepaidCodeFeeSpan"></span>
				</span>
				<input id="longPrepaidCode" name="longPrepaidCode" type="hidden" />
				<input id="shortPrepaidCode" name="shortPrepaidCode" type="hidden" />
			</td>
			
       </tr>
       <tr id="tr_isOrder">
            <td class="tdLeft"><span class="red">*</span>是否是订单类物料</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isOrder">
					<label for="isOrderY" onclick="App.radioCheck(this,'isOrder')" class="check-label">是</label><input type="radio" id="isOrderY" name="isOrder" value="Y" checked="checked"/>
					<label for="isOrderN" onclick="App.radioCheck(this,'isOrder')" >否</label><input type="radio" id="isOrderN" name="isOrder" value="N" />
				</div>
			</td>
       </tr>
       <tr id="tr_isPrepaidProvision">
            <td class="tdLeft"><span class="red">*</span>是否是待摊预提类物料</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isPrepaidProvision">
					<label for="isPrepaidProvisionY" onclick="App.radioCheck(this,'isPrepaidProvision')" class="check-label">是</label><input type="radio" id="isPrepaidProvisionY" name="isPrepaidProvision" value="Y" checked="checked"/>
					<label for="isPrepaidProvisionN" onclick="App.radioCheck(this,'isPrepaidProvision')" >否</label><input type="radio" id="isPrepaidProvisionN" name="isPrepaidProvision" value="N" />
				</div>
			</td>
       </tr>
       <tr id="tr_isNotinfee">
            <td class="tdLeft"><span class="red">*</span>费用分类</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isNotinfee">
					<label for="isNotinfeeY" onclick="App.radioCheck(this,'isNotinfee')" class="check-label">不入库费用</label><input type="radio" id="isNotinfeeY" name="isNotinfee" value="Y" checked="checked"/>
					<label for="isNotinfeeN" onclick="App.radioCheck(this,'isNotinfee')" >入库费用</label><input type="radio" id="isNotinfeeN" name="isNotinfee" value="N" />
				</div>
			</td>
       </tr>
       <tr id="tr_isFcwl" style="display: none">
            <td class="tdLeft"><span class="red">*</span>是否是房产类物料</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isFcwl">
					<label for="isFcwlY" onclick="App.radioCheck(this,'isFcwl')" class="check-label">是</label><input type="radio" id="isFcwlY" name="isFcwl" value="Y" checked="checked"/>
					<label for="isFcwlN" onclick="App.radioCheck(this,'isFcwl')" >否</label><input type="radio" id="isFcwlN" name="isFcwl" value="N" />
				</div>
			</td>
       </tr>
       <tr id="tr_isGDZC" style="display: none">
            <td class="tdLeft"><span class="red">*</span>资产分类</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isGDZC">
					<label for="isGDZCN" onclick="App.radioCheck(this,'isGDZC')" class="check-label">非固定资产</label><input type="radio" id="isGDZCN" name="isGDZC" value="N" checked="checked"/>
					<label for="isGDZCY" onclick="App.radioCheck(this,'isGDZC')" >固定资产</label><input type="radio" id="isGDZCY" name="isGDZC" value="Y" />
				</div>
			</td>
       </tr>
     
       <!--  <tr>
            <td class="tdLeft"><span class="red">*</span>损益子目</td>
			<td class="tdRight">
				<input style="width: 30px;" id="lossCode" name="lossCode" maxlength="4" valid errorMsg="请输入损益子目。" class="base-input-text" type="text" />
			</td>
			<td class="tdLeft">说明</td>
			<td class="tdRight">最大长度4位</td>
       </tr>-->       
      <!--   <tr>
            <td class="tdLeft"><span class="red">*</span>预提科目</td>
			<td class="tdRight">
				<input style="width: 30px;" id="provisionCode" name="provisionCode" maxlength="4" valid errorMsg="请输入预提科目" class="base-input-text" type="text" />
			</td>
			<td class="tdLeft">说明</td>
			<td class="tdRight">长度为4位</td>
       </tr>-->
       <!--  <tr>
            <td class="tdLeft"><span class="red">*</span>待摊科目</td>
			<td class="tdRight">
				<input style="width: 30px;" id="prepaidCode" name="prepaidCode" maxlength="4" valid errorMsg="请输入待摊科目" class="base-input-text" type="text" />
			</td>
			<td class="tdLeft">说明</td>
			<td class="tdRight">长度为4位</td>
       </tr> -->
       <tr style="display: none">
            <td class="tdLeft">是否业务宣传费</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isPublicityPay">
					<label for="isPublicityPay1" onclick="App.radioCheck(this,'isPublicityPay')" class="check-label">是</label><input type="radio" id="isPublicityPay1" name="isPublicityPay" value="1" checked="checked"/>
					<label for="isPublicityPay2" onclick="App.radioCheck(this,'isPublicityPay')" >否</label><input type="radio" id="isPublicityPay2" name="isPublicityPay" value="0"/>
				</div>
			</td>
       </tr>
		<tr>
		   <td class="tdLeft" >说明<br>(<span id="defrayedLogSpan">0/100</span>)</td>
		    <td class="tdRight">
		        <textarea class="base-textArea"  name="memo" id="memo" onkeyup="$_showWarnWhenOverLen1(this,100,'defrayedLogSpan')"></textarea>
		    </td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="01080501" value="提交"/>
				<input type="button" value="重置" onclick="resetAll()">
			</td>
		</tr>		                     
</table>
</form>

</body>
</html>