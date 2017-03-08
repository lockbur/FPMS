<%@ page import="com.forms.platform.core.util.Tool"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>用户修改</title>
<style type="text/css">
#preview_wrapper {
	display: inline-block;
	width: 200px;
	height: 200px;
	background-color: #CCC;
}

#preview_fake { /* 该对象用户在IE下显示预览图片 */
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale
		);
}

#preview_size_fake { /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image
		);
	visibility: hidden;
}

#preview { /* 该对象用户在FF下显示预览图片 */
	width: 200px;
	height: 200px;
}
</style>
<script type="text/javascript">
	function doValidate() 
	{
		//提交前调用
		var checkFlag2=App.valid("#userForm");
		var checkFlag1= false;		
		
		//检查电话和邮箱的输入格式,对于已存在的错误数据不检查
 		var phoneNumber=$("#phoneNumber").val();
 		var phoneInfo = '${userInfo.phoneNumber}';
		var phoneNumberExp=/^((0\d{2,3}-\d{7,8})|(1\d{10}))$/;
 		if(phoneNumber != phoneInfo){
 			if(!phoneNumberExp.test(phoneNumber)){
 				App.notyError("不可用的联系电话,请重新填写!");
 				return false;
 			}
 		}
 		var notesId = $('#notesId').val();
 		var emailInfo = '${userInfo.notesId}';
 		var notesExp = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9])+/;
 		if(notesId != emailInfo && !notesExp.test(notesId)){
 			App.notyError("不可用的邮箱,请重新填写!");
 			return fales;
 		}
 		
 		if($('input[type=checkbox][name=roleId]:checked').size()== 0)
		{
			if(confirm('是否确定不选择角色？')){
				checkFlag1= true;
			}
			else{
				App.toShowNullCheck('userForm','roleIdDiv','请选择角色。');
				checkFlag1= false;
			} 			
		}else{
			App.toHiddenNullCheck('userForm','roleIdDiv','roleIdTd');
			checkFlag1= true;
		}
		
		if(!checkFlag1 ||! checkFlag2){ return; }
		return true;
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
	
function setDeptId()
{
	var options = $("#fhDeptId option");
	for(var i=0; i<options.size(); i++)
	{	
		if(options.eq(i).prop("selected"))
		{
			$("#deptId").val(options.eq(i).val());
			break;
		}		
	}	
}
//解锁用户
function lock(userId){
	var form = $("#lock")[0];
	form.action = '<%=request.getContextPath()%>/sysmanagement/user/lock.do?<%=WebConsts.FUNC_ID_KEY%>=01030403&userId='+userId;
	form.submit();
}
//初始化密码
function initPwd(userId){
	var url='<%=request.getContextPath()%>/sysmanagement/user/initPwd.do?<%=WebConsts.FUNC_ID_KEY%>=01030402'; 
		$("<div>确定要重置用户" + userId + "密码?</div>").dialog({
			resizable : false,
			height : 160,
			modal : true,
			dialogClass : 'dClass',
			buttons : {
				"确定" : function() {
					var form = document.forms[2];
					form.userId.value = userId;
					form.action = url;
					App.submit(form);
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	}
</script>
</head>

<body>
	<form method="post" action="" id="lock"></form>
	<form method="post" action="" id="pwdProtect"></form>
	<p:authFunc funcArray="01030401" />
	<form method="post" action="" enctype="multipart/form-data"
		id="userForm">
		<p:token />
		<table>
			<tr>
				<th colspan="2">用户修改</th>
			</tr>
			<tr>
				<td class="tdLeft"><span class="red">*</span>
				<fmt:message key="label.user.userId" /></td>
				<td class="tdRight"><c:out value="${userInfo.userId}"></c:out>
					<input type="hidden" name="userId" value="${userInfo.userId}" /></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="red">*</span>
				<fmt:message key="label.user.userName" /></td>
				<td class="tdRight"><c:out value="${userInfo.userName}"></c:out>
				</td>
			</tr>
			<tr>
				<td class="tdLeft">所属责任中心代码</td>
				<td class="tdRight">${userInfo.dutyCode}</td>
			</tr>
			<tr>

				<td class="tdLeft">所属责任中心名称<span class="red">*</span></td>
				<td class="tdRight">
					<c:choose>
						<c:when test="${org1Code=='A0001' and isSuperAdmin=='1'}">
							<forms:OrgSelPlugin rootNodeId="${userInfo.org1Code}"
							jsVarGetValue="dutyCode" ableQuery="true"
							initValue="${userInfo.dutyCode}" parentCheckFlag="false" />
						</c:when>
						<c:otherwise>
							<input type="hidden" name="dutyCode" value="${userInfo.dutyCode }"/>
							${userInfo.dutyName}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="tdLeft">所属机构代码</td>
				<td class="tdRight">${userInfo.orgCode}</td>
			</tr>
			<tr>
				<td class="tdLeft">所属机构名称</td>
				<td class="tdRight">${userInfo.orgName}</td>
			</tr>
			<tr>
				<td class="tdLeft">所属财务中心</td>
				<td class="tdRight">${userInfo.ouName}(${userInfo.ouCode})</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.user.phoneNumber" /></td>
				<td class="tdRight"><input type="text" maxlength="40"
					id="phoneNumber" name="phoneNumber" value="${userInfo.phoneNumber}"
					class="base-input-text" /></td>
			</tr>
			<tr>
				<td class="tdLeft">邮箱</td>
				<td class="tdRight"><input type="text" maxlength="200"
					id="notesId" name="notesId" value="${userInfo.notesId}"
					class="base-input-text" /></td>
			</tr>
			<tr>
				<td class="tdLeft">用户类型</td>
				<td class="tdRight"><c:if test="${'1'==userInfo.userType}">实名用户</c:if>
					<c:if test="${'2'==userInfo.userType}">虚拟用户</c:if></td>
			</tr>
			<tr>
				<td class="tdLeft">是否采购员</td>
				<td class="tdRight">
					<c:choose>
						<c:when test="${org1Code=='A0001' and isSuperAdmin=='1'}">
							<div class="base-input-radio" id="isBuyerDiv">
								<label for="isBuyer1" onclick="App.radioCheck(this,'isBuyerDiv')" <c:if test="${'Y'==userInfo.isBuyer}">class="check-label"</c:if>>是</label><input type="radio" id="isBuyer1" name="isBuyer" value="Y" <c:if test="${'Y'==userInfo.isBuyer}">checked="checked"</c:if>>
								<label for="isBuyer2" onclick="App.radioCheck(this,'isBuyerDiv')" <c:if test="${'N'==userInfo.isBuyer}">class="check-label"</c:if>>否</label><input type="radio" id="isBuyer2" name="isBuyer" value="N" <c:if test="${'N'==userInfo.isBuyer}">checked="checked"</c:if>>
							</div>
						</c:when>	
						<c:otherwise>
							<input type="hidden" value="${userInfo.isBuyer}" name="isBuyer"/>
							<c:if test="${userInfo.isBuyer=='Y'}">
								是
							</c:if> <c:if test="${userInfo.isBuyer=='N'}">
								否
							</c:if>
							
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.user.status" /></td>
				<td class="tdRight">
					<c:if test="${'2'==userInfo.userType}">
						<div class="base-input-radio" id="isDeletedDiv">
							<label for="status1"
								onclick="App.radioCheck(this,'isDeletedDiv')"
								<c:if test="${userInfo.status!='3'}">class="check-label"</c:if>>启用</label>
							<input type="radio" id="status1" name="status" value="2"
								<c:if test="${userInfo.status!='3'}">checked="checked"</c:if>>
							<label for="status2"
								onclick="App.radioCheck(this,'isDeletedDiv')"
								<c:if test="${userInfo.status=='3'}">class="check-label"</c:if>>禁用</label>
							<input type="radio" id="status2" name="status" value="3"
								<c:if test="${userInfo.status=='3'}">checked="checked"</c:if>>
						</div>
					</c:if> 
					<c:if test="${'1'==userInfo.userType}">
						${userInfo.statusName }
						<input type="hidden" id="realuserstatus" name="status" value="${userInfo.status}"/>
					</c:if>
				</td>
			</tr>
				<tr>
				<td class="tdLeft">是否员工</td>
				<td class="tdRight">${userInfo.isYg}</td>
			</tr>
			<%-- <c:if test="${userInfo.org1Code=='A0001'}"> --%>
			<tr>
				<td class="tdLeft">是否省行超级管理员</td>
				<td class="tdRight">
					<div class="base-input-radio" id="isSuperAdminDiv">
						<label for="isSuperAdmin1"
							onclick="App.radioCheck(this,'isSuperAdminDiv')"
							<c:if test="${'1'==userInfo.isSuperAdmin}">class="check-label"</c:if>>是</label><input
							type="radio" id="isSuperAdmin1" name="isSuperAdmin" value="1"
							<c:if test="${'1'==userInfo.isSuperAdmin}">checked="checked"</c:if> />
						<label for="isSuperAdmin2"
							onclick="App.radioCheck(this,'isSuperAdminDiv')"
							<c:if test="${'0'==userInfo.isSuperAdmin}">class="check-label"</c:if>>否</label><input
							type="radio" id="isSuperAdmin2" name="isSuperAdmin" value="0"
							<c:if test="${'0'==userInfo.isSuperAdmin}">checked="checked"</c:if> />
					</div>
				</td>
			</tr>
			<%-- </c:if> --%>
			<tr>
				<td class="tdLeft"><fmt:message key="label.user.roleId" /></td>
				<td class="tdRight" id="roleIdTd">
					<div id="roleIdDiv">
						<c:if test="${userInfo.org1Code == 'A0001'}">
							<table class="tableList">
								<tr><th colspan="4" align="center">总行角色</th></tr>
								<tr>
								<c:forEach items="${pageRoleList}" var="pageRole"  varStatus="status">
									<c:if test="${pageRole.roleLevel != '02'}">
										<td>
											<label><input type="checkbox"
													<c:if test="${pageRole.isCheck=='1' }">checked="checked" </c:if>
													name="roleId" value="${pageRole.roleId}" /> <span>${pageRole.roleName}</span>
											</label>
										</td>
									</c:if>
									<c:if test="${(status.index+1) %4==0}">
									</tr>
									</c:if>
								</c:forEach>
								<tr><th colspan="4" align="center">全局角色</th></tr>
								<tr>
								<c:forEach items="${pageRoleListOrg}" var="pageRole"  varStatus="status">
									<c:if test="${pageRole.roleLevel == '02'}">
										<td>
											<label><input type="checkbox"
													<c:if test="${pageRole.isCheck=='1' }">checked="checked" </c:if>
													name="roleId" value="${pageRole.roleId}" /> <span>${pageRole.roleName}</span>
											</label>
										</td>
									</c:if>
									<c:if test="${(status.index+1) %4==0}">
									</tr>
									</c:if>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${userInfo.org1Code != 'A0001'}">
						<table class="tableList">
							<tr>
							<c:forEach items="${pageAllRoleList}" var="pageRole"  varStatus="status">
								<td>
									<label><input type="checkbox"
											<c:if test="${pageRole.isCheck=='1' }">checked="checked" </c:if>
											name="roleId" value="${pageRole.roleId}" /> <span>${pageRole.roleName}</span>
									</label>
								</td>
								<c:if test="${(status.index+1) %4==0}">
								</tr>
								</c:if>
							</c:forEach>
						</table>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tdWhite"><input type="button"
					value="初始化密码" onclick="initPwd('${userInfo.userId}')"> <p:button
						funcId="01030401" value="提交" /> <c:if test="${T}">
						<input type="button" value="解锁"
							onclick="lock('${userInfo.userId}');">
					</c:if> <input type="button" value="返回"
					onclick="backToLastPage('${uri}');"></td>
			</tr>
		</table>
	</form>
</body>
</html>