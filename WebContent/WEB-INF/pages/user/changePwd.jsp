<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<script type="text/javascript">
function showTitle () {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    keyup();
}

function  keyup() {
    var __th = $("#upPwd");
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
	var newPassword=$("#upPwd").val();
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
</script>
<!-- 审批弹出框DIV -->
<div id="auditDiv" style="display:none;">
		<form action="<%=request.getContextPath()%>/user/gotoLogin.do" method="post" id="pwdForm">
		<input type="hidden" id="user_id" name="user_id"/>
		<p:token/>
			<table width="98%">
				<tr>
					<td class="tdRight" width="120px;"><span class="red">*</span>修改后密码:</td>
					<td  class="tdRight" >
						<span id="upPwdSpan"></span>
						 <div class="ywz_zhuce_xiaoxiaobao">
		   				<input id="upPwd" name="upPwd"  class="base-input-text" type="password" onfocus="showTitle()" onkeyup="keyup()" onblur="checkNewPassword()"valid errorMsg="请输入新密码。"><br>
			            <div class="ywz_zhuce_huixian" id='pwdLevel_1' style="float: left"> </div>
			            <div class="ywz_zhuce_huixian" id='pwdLevel_2' style="float: left"> </div>
			            <div class="ywz_zhuce_huixian" id='pwdLevel_3' > </div>
			            <br>
			            <div class="ywz_zhuce_hongxianwenzi"> 弱</div>
			            <div class="ywz_zhuce_hongxianwenzi"> 中</div>
			            <div class="ywz_zhuce_hongxianwenzi"> 强</div>
			          	</div>
			           <div class="ywz_zhuce_yongyu1" style="padding-left:0px;width:100%;"> 
				           	<span id="pwd_tip" style="color: #898989">
				           		<font style="color: #F00">*</font> 6-16位，至少由英文字符和数字组成
				           	</span> 
				           	<span id="pwd_err" style="color: #f00; display:none;">
				           		(6-16位)至少由英文字符和数字组成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				           	</span> 
			           	</div>
					</td>
				</tr>
				<tr>
					<td class="tdRight"><span class="red">*</span>确认密码:</td>
					<td  class="tdRight" >
						<input id="confirmPwd" name="confirmPwd" valid maxlength='100' class="base-input-text" type="password"/>
						<span id="confirmPwdSpan" style="color:red"></span>
					</td>
				</tr>
			</table>
		</form>
	</div>