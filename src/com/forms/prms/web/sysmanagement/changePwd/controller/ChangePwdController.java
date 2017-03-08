package com.forms.prms.web.sysmanagement.changePwd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.EncryptUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.changePwd.domain.PwdSafeBean;
import com.forms.prms.web.sysmanagement.changePwd.service.ChangePwdService;
import com.forms.prms.web.sysmanagement.user.service.UserInfoService;
import com.forms.prms.web.user.controller.UserController;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.user.service.UserService;

@Controller
@RequestMapping("/common/changePwd")
public class ChangePwdController {
	@Autowired
	private ChangePwdService service;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@RequestMapping("checkInitPwd.do")
	@ResponseBody
	public String checkInitPwd(User userInfo) throws Exception{
		try {
			
		
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		jsonObject.put("pwdError", false);
		String userId = userInfo.getUserId();
		String password = userInfo.getPassword();
		User user = userService.getUser(userId);
		String flag = "";
		String msg = "";
		if (null == user) {
			flag = "N";
			msg="用户名不存在！";
			jsonObject.put("flag",flag);
			jsonObject.put("msg", msg);
			return jsonObject.writeValueAsString();
		} else {
			if ("3".equals(user.getStatus())
					&& !Tool.CHECK.isEmpty(user.getLeaveDate())) {
				flag = "N";
				msg="该用户已离职，不能登陆！";
				jsonObject.put("flag",flag);
				jsonObject.put("msg", msg);
				return jsonObject.writeValueAsString();
			}
			if (!"员工".equals(user.getIsYg())) {
				flag = "N";
				msg="该用户不是员工，不能登陆！";
				jsonObject.put("flag",flag);
				jsonObject.put("msg", msg);
				return jsonObject.writeValueAsString();
			}
			if (!Tool.CHECK.isEmpty(user.getPwdFailTimes()) && !Tool.CHECK.isEmpty(user.getPwdErrDate()) && !user.getPwdErrDate().equals(Tool.DATE.getDate())) {
				//当 错误次数不为空  且 错误时间不为空 且 错误不是今天的时候 说明是 昨天导致的错误 今天就应该重置为0
				userService.initErrorPwd(user);
				user.setPwdFailTimes("0");
				user.setPwdErrDate("");
			}
			if(!Tool.CHECK.isEmpty(user.getPwdFailTimes()) &&  Integer.parseInt(user.getPwdFailTimes()) > Integer
					.parseInt(WebHelp.getSysPara("PWD_ERROR_TIMES"))) {
				if (null!=user.getPwdErrDate() && user.getPwdErrDate().equals(Tool.DATE.getDate())) {
					//当 错误日期 不为空 且 是今天的时候
					// 密码错误次数超标
					flag = "N";
					msg="密码已被锁定，请明天登陆或者找管理员解锁。";
					jsonObject.put("flag",flag);
					jsonObject.put("msg", msg);
					return jsonObject.writeValueAsString();
				}else {
					// 有错误次数 ，而且错误记录日期不是今天 就给他 重置为0 那样在 下一步校验的时候 
					userService.initErrorPwd(user);
				}
				
			}
			boolean fg = false;
			if(Tool.CHECK.isBlank(user.getPassword())){
				if("111111".equals(password)){
					fg = true;
				}
			}else{
				if (user.getPassword().equals(
					new EncryptUtil(userId).encryptNumStr(password))) {
					fg = true;
				}
			}
			if(!fg){
				
				//密码错误
				flag = "N";
				int count = Integer.parseInt(WebHelp.getSysPara("PWD_ERROR_TIMES"))-Integer.parseInt(user.getPwdFailTimes());
				
				if (count < 0) {
					msg="密码错误错误次数已超标，用户已被锁定！";
				}else {
					msg="密码错误,还有"+count+"次输入机会，超过用户将被锁定！";
				}
				// 密码输入错误，修改用户的密码输入错误次数
				if (Integer.parseInt(user.getPwdFailTimes())+1 > Integer.parseInt(WebHelp.getSysPara("PWD_ERROR_TIMES"))) {
					// 用户密码错误次数 加上 这一次错误 大于设定的次数 就锁定用户
					user.setIsLocked("1");
				}else {
					user.setIsLocked("0");
				}
				userService.updatePwdErrorCount(user);
				jsonObject.put("flag",flag);
				jsonObject.put("msg", msg);
				return jsonObject.writeValueAsString();
			}
			
			String pwdOutDate = "";
			if (!Tool.CHECK.isEmpty(user.getPwdChangeDate())) {
				pwdOutDate = Tool.DATE.getDaystr(user.getPwdChangeDate(),
						Integer.parseInt(WebHelp
								.getSysPara("PWD_VALIDITY_DATE")));
			}
			
			
			if (Tool.DATE.getDate().compareTo(pwdOutDate) > 0 && !(pwdOutDate.equals(""))) {
				flag = "O";
				msg="密码已过期，必须修改密码！";
				jsonObject.put("flag",flag);
				jsonObject.put("msg", msg);
				return jsonObject.writeValueAsString();
			}
			if (Tool.CHECK.isEmpty(user.getPwdChangeDate())) {
				flag = "I";
				msg="密码是初始密码，必须修改密码！";
				jsonObject.put("flag",flag);
				jsonObject.put("msg", msg);
				return jsonObject.writeValueAsString();
			} 
			//校验用户是否有角色
			List<User>  roleList=userService.getRole(userInfo);
			if (Tool.CHECK.isEmpty(roleList)) {
				flag = "noRole";
				msg="该用户没有对应的角色，不能登录！";
				jsonObject.put("flag",flag);
				jsonObject.put("msg", msg);
				return jsonObject.writeValueAsString();
			} 
		}
		jsonObject.put("flag",true);
		
		return jsonObject.writeValueAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
	/**
	 * Ajax检验是否超过设置的次数
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("checkPasswordCount.do")
	@ResponseBody
	public String checkPasswordCount(User user ) {
		String password = user.getPassword();
		String userId = user.getUserId();
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean isOut = userInfoService.checkPasswordCount(password,userId);
		jsonObject.put("isOut", isOut);
		return jsonObject.writeValueAsString();
	}
	@RequestMapping("changePwd.do")
	@ResponseBody
	public String changePwd(User user){
		String password = "";
		try {
			password = new EncryptUtil(user.getUserId()).encryptNumStr(user.getConfirmPwd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setPassword(password);
		user.setPwdChangeDate(DateUtil.getDateStr());
		boolean flag = userService.changePwd(user);
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		jsonObject.put("flag", flag);
		return jsonObject.writeValueAsString();
	}
	
	
	@RequestMapping("openFindPassword.do")
	public String openFindPassword(HttpServletRequest request)
	{
		return "user/findpassword";
		
	}
	@RequestMapping("getPwdSafeAjax.do")
	@ResponseBody
	public String getPwdSafeAjax(String userId,String questionSeq)
	{
		PwdSafeBean pwdSafebean=service.mGetPwdSafeInfo(userId,questionSeq);
		AbstractJsonObject jsonObj = null;
		if (pwdSafebean != null)
		{
			jsonObj = new SuccessJsonObject();
			jsonObj.put("pwdSafebean", pwdSafebean);
		}
		else
		{
			jsonObj = new FailureJsonObject("message.pwdsafe.not.found");
		}
		return jsonObj.writeValueAsString();
	}
	/**
	 * @methodName getUserInfoAjax
	 * desc  
	 * 根据输入的用户名，校验此用户名是否存在以及是否存在密保问题
	 * @param userId
	 * @param questionSeq
	 * @return
	 */
	
	@RequestMapping("getUserInfoAjax.do")
	@ResponseBody
	public String getUserInfoAjax(PwdSafeBean pwdSafeBean)
	{
		AbstractJsonObject jsonObj = null;
		if (service.existUserId(pwdSafeBean) == false){
			jsonObj = new FailureJsonObject("您输入的用户不存在");
			return jsonObj.writeValueAsString();
		}else if (service.existPwdQuestion(pwdSafeBean) == false && service.existEmail(pwdSafeBean) == false){
			jsonObj = new FailureJsonObject("您输入的用户没有设置相关密保,请尽快设置！");
			return jsonObj.writeValueAsString();
		}
		jsonObj = new SuccessJsonObject();
//		if(service.existPwdQuestion(pwdSafeBean) == true && service.existUserId(pwdSafeBean) == true)
//		{
//			jsonObj.put("success", "成功");
//		}
//		
		User user = userService.getUser(pwdSafeBean.getUserId());
		if(StringUtils.isNotEmpty(user.getNotesId())){
			jsonObj.put("notesId", user.getNotesId());
		}
		return jsonObj.writeValueAsString();
	}
	/**
	 * @methodName checkExistQuestion
	 * desc  
	 * 检查是否设置了密保问题
	 * @param pwdSafeBean
	 * @return
	 */
	@RequestMapping("checkExistQuestion.do")
	@ResponseBody
	public String checkExistQuestion(PwdSafeBean pwdSafeBean)
	{
		AbstractJsonObject jsonObj = null;
		if (service.existPwdQuestion(pwdSafeBean) == false){
			jsonObj = new FailureJsonObject("您没有设置密保问题，可能是设置了邮箱密保！");
			return jsonObj.writeValueAsString();
		}
		jsonObj = new SuccessJsonObject();
		jsonObj.put("success", "成功");
		return jsonObj.writeValueAsString();
	}
	
	/**
	 * @methodName queryPwdQuestion
	 * desc  
	 * 判断是否设置了密保问题以及答案是否正确
	 * @param pwdSafeBean
	 * @return
	 */
	@RequestMapping("queryPwdQuestion.do")
	@ResponseBody
	public String queryPwdQuestion(PwdSafeBean pwdSafeBean)
	{
		AbstractJsonObject jsonObj = null;
		if (service.existPwdQuestion(pwdSafeBean) == false){
			jsonObj = new FailureJsonObject("您没有设置密保问题，可能是设置了邮箱密保！");
			return jsonObj.writeValueAsString();
		}
		if (service.queryPwdAnswer(pwdSafeBean) == true)
		{
			jsonObj = new SuccessJsonObject();
			jsonObj.put("success", "成功");
		}else{
			jsonObj = new FailureJsonObject("当前密保问题答案错误");
		}
		return jsonObj.writeValueAsString();
	}
	/**
	 * @methodName updatePwd
	 * desc  
	 * 修改密码
	 * @param pwdSafeBean
	 * @return
	 */
	@RequestMapping("updatePwd.do")
	public String updatePwd(PwdSafeBean pwdSafeBean)
	{
		String userId = pwdSafeBean.getUserId();
		String password = pwdSafeBean.getPassword();
		User user = new User();
		try {
			password = new EncryptUtil(userId).encryptNumStr(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setPassword(password);
		user.setUserId(userId);
		user.setPwdChangeDate(DateUtil.getDateStr());
		userService.changePwd(user);
		UserController.user= userService.getUser(userId);
		WebUtils.getMessageManager().addInfoMessage("设置新密码成功!");
		return "success2";
		
	}
	
	/**
	 * @methodName sendResetPassEmail
	 * desc  
	 * 发送重置密码邮件
	 * @param pwdSafeBean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("sendResetPassEmail.do")
	@ResponseBody
	public String sendResetPassEmail(HttpServletRequest request,
			PwdSafeBean pwdSafeBean) {
		AbstractJsonObject jsonObj = null;
		try {

			String path = request.getRequestURL().substring(0,
					request.getRequestURL().lastIndexOf("/") + 1)
					+ "resetPassByEmail.do";

			service.sendResetPassEmail(path, pwdSafeBean.getUserId(),
					pwdSafeBean.getEmail());

			jsonObj = new SuccessJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj.writeValueAsString();
	}
	
	/**
	 * @methodName resetPassByEmail
	 * desc
	 * 通过邮件重置密码
	 * @param pwdSafeBean
	 * @return
	 */
	@RequestMapping("resetPassByEmail.do")
	public String resetPassByEmail(HttpServletRequest request,
			PwdSafeBean pwdSafeBean) {
		if (service.checkToken(pwdSafeBean)) {// 检查token有效性
			request.setAttribute("resetpwdreq", "RPR");
			request.setAttribute("userId", pwdSafeBean.getUserId());
			return "user/findpassword";
		} else {
			WebUtils.getMessageManager().addInfoMessage("链接已失效，请重新申请!");
			return "user/failure";
		}
	}
	
}
