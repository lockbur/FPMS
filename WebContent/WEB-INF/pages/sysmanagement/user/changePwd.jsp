<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<script type="text/javascript">
//提交前校验
function doValidate(){
	//提交前调用
    if(!App.valid("#reqtypeForm")){
	 return;
	}
	//ajax匹配查询密码
	if(!checkOldPassword()){
		App.notyError("输入密码与旧密码不匹配!");
		return false;
	}
	//校验确认密码和新密码是否相同
	var surePassword=$("#surePassword").val();
	var password=$("#password").val();
	var oldPassword=$("#oldPassword").val();
	if (oldPassword==password) {
		App.notyError("旧密码和新密码不能相同！");
		return false;
	}
	if(password.length>6||password.length==6){
		  if(!checkNewPassword()){
			return false;
		 	} 
		  else{
			 if((surePassword!=password)){
				App.notyError("确认密码与新密码不相同!");
				return false;
			 } 
		  }
	}
	else{
		return false;
	}
	//判断是否超过设置次数
	if(checkPasswordCount()){
		App.notyError("此密码已达到设置次数上限!");
		return false;
	}
	return true;
}
//检查输入的密码和旧密码是否匹配
function checkOldPassword(){
	var list = false;
	var data = {};
	data['oldPassword'] =  $("#oldPassword").val();
	var url = "sysmanagement/user/checkPassword.do?VISIT_FUNC_ID=01060102";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
		list = data.isEqual;
	});
	return list;
}
function showTitle () {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    keyup();
}

function  keyup() {
    var __th = $("#password");
    if (!__th.val()) {
        $('#pwd_tip').hide();
        $('#pwd_err').show();
        Primary();
        return;
    }
    if (__th.val().length < 6) {
        $('#pwd_tip').hide();
        $('#pwd_err').show();
        Weak();
        return;
    }
    var _r = checkPassword(__th);
    if (_r < 1) {
        $('#pwd_tip').hide();
        $('#pwd_err').show();
        Primary();
        return;
    }

    if (_r > 0 && _r < 2) {
        Weak();
    } else if (_r >= 2 && _r < 4) {
        Medium();
    } else if (_r >= 4) {
        Tough();
    }

    $('#pwd_tip').hide();
    $('#pwd_err').hide();
}



function Primary() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_huixian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}

function Weak() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}

function Medium() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}

function Tough() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_hongxian3');
}




function checkPassword(pwdinput) {
    var maths, smalls, bigs, corps, cat, num;
    var str = $(pwdinput).val()
    var len = str.length;

    var cat = /.{16}/g
    if (len == 0) return 1;
    if (len > 16) { $(pwdinput).val(str.match(cat)[0]); }
    cat = /.*[\u4e00-\u9fa5]+.*$/
    if (cat.test(str)) {
        return -1;
    }
    cat = /\d/;
    var maths = cat.test(str);
    cat = /[a-z]/;
    var smalls = cat.test(str);
    cat = /[A-Z]/;
    var bigs = cat.test(str);
    var corps = corpses(pwdinput);
    var num = maths + smalls + bigs + corps;

    if (len < 6) { return 1; }

    if (len >= 6 && len <= 8) {
        if (num == 1) return 1;
        if (num == 2 || num == 3) return 2;
        if (num == 4) return 3;
    }

    if (len > 8 && len <= 11) {
        if (num == 1) return 2;
        if (num == 2) return 3;
        if (num == 3) return 4;
        if (num == 4) return 5;
    }

    if (len > 11) {
        if (num == 1) return 3;
        if (num == 2) return 4;
        if (num > 2) return 5;
    }
}

function corpses(pwdinput) {
    var cat = /./g
    var str = $(pwdinput).val();
    var sz = str.match(cat)
    for (var i = 0; i < sz.length; i++) {
        cat = /\d/;
        maths_01 = cat.test(sz[i]);
        cat = /[a-z]/;
        smalls_01 = cat.test(sz[i]);
        cat = /[A-Z]/;
        bigs_01 = cat.test(sz[i]);
        if (!maths_01 && !smalls_01 && !bigs_01) { return true; }
    }
    return false;
}
function checkNewPassword(){
	var newPassword=$("#password").val();
	if(newPassword.length>6||newPassword.length==6){
		var passwordExp=/((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,16}$/;
		if(passwordExp.test(newPassword)){
			$('#pwd_err').hide();
			return true;
		}
		else{
			$('#pwd_err').show();
			return false;
		}
		
	}else{
		return false;
	}
}
//检验设置的新密码是否超过修改次数
function checkPasswordCount(){
	var list = false;
	var data = {};
	data['password'] =  $("#password").val();
	var url = "sysmanagement/user/checkPasswordCount.do?VISIT_FUNC_ID=01060101";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
		list = data.isOut;
	});
	return list;
}
</script>
</head>
<body>
 <p:authFunc funcArray="01030802,01030805,01030804,01060103"/>
<form action="<%=request.getContextPath()%>/rm/mandayservice/storagemanage/edit.do" method="post" id="reqtypeForm">
	<p:token/>
	<table>
		<tr>
			<th colspan="4">密码修改
			</th>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>旧密码：</td>
			<td class="tdRight" colspan="3">
				<input type="password" id="oldPassword" name="oldPassword"  class="base-input-text"  valid errorMsg="请输入旧密码。" maxlength="100"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>	
			<td class="tdLeft"><span class="red">*</span>新密码：</td>
			<td class="tdRight" colspan="3">
				<br><br>
			 <div class="ywz_zhuce_xiaoxiaobao">
   				<input id="password" name="password"  class="base-input-text" type="password" onfocus="showTitle()" onkeyup="keyup()" onblur="checkNewPassword()"valid errorMsg="请输入新密码。" maxlength="100"><br>
	            <div class="ywz_zhuce_huixian" id='pwdLevel_1' style="float: left"> </div>
	            <div class="ywz_zhuce_huixian" id='pwdLevel_2' style="float: left"> </div>
	            <div class="ywz_zhuce_huixian" id='pwdLevel_3' > </div>
	            <br>
	            <div class="ywz_zhuce_hongxianwenzi"> 弱</div>
	            <div class="ywz_zhuce_hongxianwenzi"> 中</div>
	            <div class="ywz_zhuce_hongxianwenzi"> 强</div>
	          </div>
	           <div class="ywz_zhuce_yongyu1"> <span id="pwd_tip" style="color: #898989"><font style="color: #F00">*</font> 6-16位，至少由英文字符和数字组成</span> <span id="pwd_err" style="color: #f00; display:none;">6-16位，至少由英文字符和数字组成</span> </div>
			</td>
		</tr>
         <tr>
            <td class="tdLeft" ><span class="red">*</span>确认密码：</td>
            <td class="tdRight" colspan="3">
            	<input type="password" id="surePassword" name="surePassword"  class="base-input-text" maxlength="100" valid errorMsg="请输入确认密码。"/>&nbsp;&nbsp;	
            </td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="01060103" value="密码修改" />
<%-- 				<input type="button" value="返回" onclick="backToLastPage('${uri}')"> --%>
			</td>
		</tr>
	</table>
</form>
<p:page/>
</body>
</html>