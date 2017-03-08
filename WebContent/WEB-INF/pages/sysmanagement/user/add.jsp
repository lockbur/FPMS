<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<style type="text/css">
#preview_wrapper{
    display:inline-block;
    width:200px;
    height:200px;
    background-color:#CCC;
}
#preview_fake{ /* 该对象用户在IE下显示预览图片 */
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);
}
#preview_size_fake{ /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
    visibility:hidden;
}
#preview{ /* 该对象用户在FF下显示预览图片 */
    width:200px;
    height:200px;
}
</style>

<script type="text/javascript">
function doValidate() 
{
	//提交前调用
	var checkFlag2=App.valid("#userForm");
	var checkFlag1= false;
	
	var email= $.trim($("input[name='notesId']").val());
	if(email!=null&&email!=''){
		var re = /^([\.a-zA-Z0-9_-]){2,10}@([a-zA-Z0-9_-]){2,10}(\.([a-zA-Z0-9]){2,}){1,4}$/;
		if(!re.test(email)){
	         App.notyError("邮箱地址格式有误！");
	         return;
	    }
	}
	if($('input[type=checkbox][name=roleId]:checked').size()== 0)
	{
		App.toShowNullCheck('userForm','roleIdDiv','请选择角色。');
		checkFlag1= false;
	}else{
		App.toHiddenNullCheck('userForm','roleIdDiv','roleIdTd');
		checkFlag1= true;
	}
	if(!checkFlag1 ||! checkFlag2 ){ return; }
	
	if(!userIdFlag){
		App.toShowCheckIdIsExist("userForm","userId","userIdTd","用户id已存在！",!userIdFlag);
		return false;
	}
	
	var obj=$("input[id='isSuperAdmin1']");
	var dutyCode=$("#dutyCode").val();
	/* if ($(obj).prev().attr("class")=='check-label'&&checkOrg1Code(dutyCode)!='A0001') {
		App.notyError("只有一级行为A0001的责任中心用户才能选择是否超级管理员！");
		$(obj).prev().removeAttr("class");
		$("#isSuperAdminDiv").find("label").eq(1).click();	
		$("#isSuperAdmin").val("0");
		return false;
	} */
	
	var roleLevel=$("input[type=radio][name=roleId]:checked").attr("roleLevel");
	if (roleLevel=='01'&&checkOrg1Code(dutyCode)!='A0001') {
		App.notyError("该角色为总行角色，只有责任中心所在一级行为A0001才可以选择！");
		$(obj).removeAttr("checked");
		return false;
	}
	
	if ($("#dutyName").val()!="") {
		return true;
	}

}


function checkUseId(){
	var data = {};
	data['userId'] =  $("#userId").val();
	App.ajaxSubmit("sysmanagement/user/checkUserId.do?<%=WebConsts.FUNC_ID_KEY%>=01030102",{data : data}, function(data) {
		if(data.isExist){
			userIdFlag=false;
		}else{
			userIdFlag = true;
		}
		App.toShowCheckIdIsExist("userForm","userId","userIdTd","用户id已存在！",data.isExist);
	});
}
function pageInit() {
	var userIdFlag=false;
	App.jqueryAutocomplete();
	$( "#userForm #fhDeptId" ).combobox();
	 $( "#userForm #fhDeptId" ).change(function(){
			$( "#userForm #deptId" ).val(this.value);
	   });
	$( "#userForm #deptType" ).combobox();
    $( "#userForm #fstdeptId" ).combobox();
    $( "#userForm #seddeptId" ).combobox();
    
    App.selectDeptCommon("userForm","sysmanagement/dept/getFstDeptLst.do?<%=WebConsts.FUNC_ID_KEY%>=010403","deptId","deptName");
	
}
function onUploadImgChange(sender){
    if( !sender.value.match( /.jpg|.gif|.png|.bmp/i ) ){
        App.notyError('图片格式无效！');
        return false;
    }
    var objPreview = document.getElementById( 'preview' );
    var objPreviewFake = document.getElementById( 'preview_fake' );
    var objPreviewSizeFake = document.getElementById( 'preview_size_fake' );
    if( sender.files && sender.files[0] ){
        objPreview.style.display = 'block';
        objPreview.style.width = 'auto';
        objPreview.style.height = 'auto';
        // Firefox 因安全性问题已无法直接通过 input[file].value 获取完整的文件路径
        objPreview.src = sender.files[0].getAsDataURL();
    }else if( objPreviewFake.filters ){
        // IE7,IE8 在设置本地图片地址为 img.src 时出现莫名其妙的后果
        //（相同环境有时能显示，有时不显示），因此只能用滤镜来解决
        // IE7, IE8因安全性问题已无法直接通过 input[file].value 获取完整的文件路径
        sender.select();
        var imgSrc = document.selection.createRange().text;
        objPreviewFake.filters.item(
            'DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;
        objPreviewSizeFake.filters.item(
            'DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;
        autoSizePreview( objPreviewFake,
            objPreviewSizeFake.offsetWidth, objPreviewSizeFake.offsetHeight );
        objPreview.style.display = 'none';
    }
    return true;
}
function onPreviewLoad(sender){
    autoSizePreview( sender, sender.offsetWidth, sender.offsetHeight );
}
function autoSizePreview( objPre, originalWidth, originalHeight ){
    var zoomParam = clacImgZoomParam( 200, 200, originalWidth, originalHeight );
    objPre.style.width = zoomParam.width + 'px';
    objPre.style.height = zoomParam.height + 'px';
    objPre.style.marginTop = zoomParam.top + 'px';
    objPre.style.marginLeft = zoomParam.left + 'px';
}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = { width:width, height:height, top:0, left:0 };
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        if( rateWidth > rateHeight ){
            param.width = maxWidth;
            param.height = height / rateWidth;
        }else{
            param.width = width / rateHeight;
            param.height = maxHeight;
        }
    }
    param.left = (maxWidth - param.width) / 2;
    param.top = (maxHeight - param.height) / 2;
    return param;
}

function checkOrg1Code(dutyCode){
	var data={};
	data['dutyCode']=dutyCode;
	var org1Code ;
	var url = "sysmanagement/user/checkOrg1Code.do?VISIT_FUNC_ID=01030103";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
		org1Code = data.org1Code;
	});
	return org1Code;
}

//此方法用于判断是否超级管理员的选择
function checkOrg(obj){
	var dutyCode=$("#dutyCode").val();
	if(dutyCode==""){
		return false;
	}
	/* if ($(obj).prev().attr("class")=='check-label'&&checkOrg1Code(dutyCode)!='A0001') {
		App.notyError("只有一级行为A0001的责任中心用户才能选择是否超级管理员！");
		$(obj).prev().removeAttr("class");
		$("#isSuperAdminDiv").find("label").eq(1).click();
		$("#isSuperAdmin").val("0");
	} */
}

function checkDutyCode(roleLevel,obj){
	var dutyCode=$("#dutyCode").val();
	if(dutyCode==""){
		return false;
	}
	if (roleLevel=='01'&&checkOrg1Code(dutyCode)!='A0001') {
		App.notyError("该角色为总行角色，只有责任中心所在一级行为A0001才可以选择！");
		$(obj).removeAttr("checked");
	}
}
function toSelectResult(s){
	if(s=='1'){
		$("#isSuperAdmin").val("1");
	}else{
		//同意
		$("#isSuperAdmin").val("0");
	}
}
</script>
</head>

<body>
<p:authFunc funcArray="01030101"/>
<form method="post" action="" enctype="multipart/form-data" id="userForm">
	<p:token/>
	<input type="hidden" id="isSuperAdmin" name="isSuperAdmin" value="0">
	<table>
		<tr>
			<th colspan="2">用户新增</th>
		</tr>
		<tr>
			<td class="tdLeft" width="50%"><span class="red">*</span><fmt:message key="label.user.userId"/></td>
			<td class="tdRight" id="userIdTd" width="50%">
				<input type="text" maxlength="7" id="userId" name="userId" class="base-input-text" valid errorMsg="请输入员工号。" onchange="checkUseId()"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span><fmt:message key="label.user.userName"/></td>
			<td class="tdRight">
				<input type="text" maxlength="100" name="userName" class="base-input-text" valid errorMsg="请输入员工姓名。"/>
			</td>
		</tr>
		<!-- 
		<tr>
			<td class="tdLeft"><span class="red">*</span><fmt:message key="label.user.password"/></td>
			<td class="tdRight">
				<input type="password" name="password" class="base-input-text" valid errorMsg="请输入密码。"/>
			</td>
		</tr>
		 -->
<!-- 		<tr> -->
<%-- 			<td class="tdLeft"><fmt:message key="label.user.logoImg"/></td> --%>
<!-- 			<td class="tdRight"> -->
<!-- 			    <div id="preview_wrapper"> -->
<!--         			<div id="preview_fake"> -->
<!--             			<img id="preview" onload="onPreviewLoad(this)"/> -->
<!--         			</div> -->
<!--     			</div> -->
<!-- 				<br/> -->
<!-- 				<input type="file" name="imgFile" id="upload_img" style="width: 320px;" class="base-input-text" onchange="onUploadImgChange(this)"/> -->
<!--     			<img id="preview_size_fake"/> -->
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr>
			<td class="tdLeft"><span class="red">*</span>所属责任中心</td>
			<td class="tdRight">
				<input type="text" id="dutyName" name="dutyName" readonly="readonly"  class="base-input-text"  valid  />
				<input type="hidden" id="dutyCode" name="dutyCode"   class="base-input-text"     />
				<forms:OrgSelPlugin  suffix="004" triggerEle="#userForm tr dutyName,dutyCode::name,id" rootNodeId="${org21Code}" 
						rootLevel="1" parentCheckFlag="false"  jsVarName="dutyCodeTree"  />
			</td>
		</tr>
		<tr>
			<td class="tdLeft"> <fmt:message key="label.user.phoneNumber"/></td>
			<td class="tdRight">
				<input type="text" maxlength="40" id="phoneNumber" name="phoneNumber" class="base-input-text" />
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft">邮箱</td>
			<td class="tdRight">
				<input type="text" maxlength="200" name="notesId" class="base-input-text"/>
				<!-- 系统添加的用户只能是虚拟用户 -->
				<input type="hidden" name="userType" value="2"/>
			</td>
		</tr>
		<!-- <tr>
			<td class="tdLeft">用户分类</td>
			<td class="tdRight">
				<div class="base-input-radio" id="userTypeDiv">
					<label for="userType1" onclick="App.radioCheck(this,'userTypeDiv')" class="check-label">实名</label><input type="radio" id="userType1" name="userType" value="1" checked="checked"/>
					<label for="userType2" onclick="App.radioCheck(this,'userTypeDiv')" >虚拟</label><input type="radio" id="userType2" name="userType" value="2"/>
				</div>
			</td>
		</tr> -->
		<tr>
			<td class="tdLeft">是否省行超级管理员</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isSuperAdminDiv">
					<label for="isSuperAdmin1" onclick="App.radioCheck(this,'isSuperAdminDiv');toSelectResult('1');">是</label><input type="radio" onclick="checkOrg(this);" id="isSuperAdmin1" name="isAdmin" value="1" />
					<label for="isSuperAdmin2" onclick="App.radioCheck(this,'isSuperAdminDiv');toSelectResult('0');" class="check-label">否</label><input type="radio" id="isSuperAdmin2" name="isAdmin" value="0" checked="checked"/>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">是否采购员</td>
			<td class="tdRight">
				<div class="base-input-radio" id="isBuyerDiv">
					<label for="isBuyer1" onclick="App.radioCheck(this,'isBuyerDiv')" >是</label><input type="radio" id="isBuyer1" name="isBuyer" value="Y" />
					<label for="isBuyer2" onclick="App.radioCheck(this,'isBuyerDiv')" class="check-label">否</label><input type="radio" id="isBuyer2" name="isBuyer" value="N" checked="checked"/>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>角色</td>
			<td class="tdRight" id="roleIdTd">
				<div id="roleIdDiv">
				<table class="tableList">
					<c:if test="${orgCode == 'A0001'}">
						<tr><th colspan="4" align="center">总行角色</th></tr><tr>
						<c:forEach items="${pageRoleList}" var="roleInfo" varStatus="status">
							<c:if test="${roleInfo.roleLevel != '02'}">
								<td><label><input type="checkbox" name="roleId" roleLevel="${roleInfo.roleLevel}" value="${roleInfo.roleId}" onclick="checkDutyCode('${roleInfo.roleLevel}',this);"/>
									<span> ${roleInfo.roleName}</span></label>
								</td></c:if>
							<c:if test="${status.count%4 == 0 && !status.last}"></tr></c:if>
						</c:forEach>
						<tr><th colspan="4" align="center">全局角色</th></tr><tr>
						<c:forEach items="${pageRoleListOrg}" var="roleInfo" varStatus="status">
							<c:if test="${roleInfo.roleLevel == '02'}">
								<td><label><input type="checkbox" name="roleId" roleLevel="${roleInfo.roleLevel}" value="${roleInfo.roleId}" onclick="checkDutyCode('${roleInfo.roleLevel}',this);"/>
									<span> ${roleInfo.roleName}</span></label>
								</td></c:if>
							<c:if test="${status.count%4 == 0 && !status.last}"></tr><tr></c:if>
						</c:forEach>
					</c:if>
					<c:if test="${orgCode != 'A0001'}">
						<tr>
						<c:forEach items="${roleList}" var="roleInfo" varStatus="status">
								<td><label><input type="checkbox" name="roleId" roleLevel="${roleInfo.roleLevel}" value="${roleInfo.roleId}" onclick="checkDutyCode('${roleInfo.roleLevel}',this);"/>
									<span> ${roleInfo.roleName}</span></label>
								</td>
							<c:if test="${status.count%4 == 0 && !status.last}"></tr><tr></c:if>
						</c:forEach>
					</c:if>
				</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="tdWhite">
				<p:button funcId="01030101" value="提交"/>
<%-- 				<input type="button" value="返回" onclick="backToLastPage('${uri}');"> --%>
			</td>
		</tr>
	</table>
</form>
</body>
</html>