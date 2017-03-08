package com.forms.prms.web.sysmanagement.changePwd.service;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.EmailSender;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.common.sqnmanager.service.SqnManagerUtil;
import com.forms.prms.web.sysmanagement.changePwd.dao.ChangePwdDao;
import com.forms.prms.web.sysmanagement.changePwd.domain.PwdSafeBean;

@Service
public class ChangePwdService {
	@Autowired
	private ChangePwdDao dao;
	@Autowired
	private SqnManagerUtil  sqnManagerUtil;

	public PwdSafeBean mGetPwdSafeInfo(String userId, String questionSeq) 
	{
		String  loc_seq ="";
		if(questionSeq==null||"".equals(questionSeq))
		{
			loc_seq ="1";
		}
		else 
		{
		    loc_seq=Integer.parseInt(questionSeq)%3+1+"";
		}
		 PwdSafeBean param=new PwdSafeBean();
		param.setUserId(userId);
		param.setQuestionSeq(loc_seq);
		PwdSafeBean bean=dao.queryPwdSafe(param);
		return bean;
	}
	
	public boolean existUserId(PwdSafeBean pwdSafeBean){
		if(dao.existUserId(pwdSafeBean)!=null){
			return true;
		}else{
			return false;
		}
	}
	public boolean existPwdQuestion(PwdSafeBean pwdSafeBean){
		if(dao.existPwdQuestion(pwdSafeBean).size()==3){
			return true;
		}else{
			return false;
		}
	}
	public boolean existEmail(PwdSafeBean pwdSafeBean){
		if(dao.existEmail(pwdSafeBean)==null){
			return false;
		}else{
			return true;
		}
	}
	public boolean queryPwdAnswer(PwdSafeBean pwdSafeBean){
		 if(dao.queryPwdAnswer(pwdSafeBean).getAnswer().equals(pwdSafeBean.getQuestionAnswer())){
			 return true;
		 }else{
			 return false;
		 }
	}

	/**
	 * 发送重置密码邮件
	 * 
	 * @param path
	 *            email重置密码请求URL
	 * @param userId
	 *            用户ID
	 * @param toEmail
	 *            重置密码link收件人
	 * @throws Exception
	 */
	public void sendResetPassEmail(String path, String userId, String toEmail)
			throws Exception {
		// 生成tokenId
		sqnManagerUtil = SpringHelp.getBean(SqnManagerUtil.class);
//		String tokenId = sqnManagerUtil.mGetNextSeq("",
//				"EMAILRESETPASSTOKENID", "");
		//加密
		String tokenId = md5(userId + DateUtil.getDateTimeStrNo());// new EncryptUtil(userId).encryptNumStr(tokenId);

		// 构造邮件发送者
		EmailSender emailSender = new EmailSender();
		emailSender.setHost(WebHelp.getSysPara("MAIL_SMTP_HOST"));
		emailSender.setTo(toEmail);
		emailSender.setFrom(WebHelp.getSysPara("MAIL_FROM"));
		emailSender.setUserName(WebHelp.getSysPara("MAIL_USER_NAME"));
		emailSender.setPassword(WebHelp.getSysPara("MAIL_PASSWORD"));

		// 设置主题、生成email重置密码link
		emailSender.setSubject("【总行财务项目管理系统】找回您的账户密码");
		emailSender.setContent(generateResetPwdLink(path, userId, tokenId));

		// 发送重置密码邮件
		emailSender.sendMail();

		// 保存email重置密码用token
		PwdSafeBean psb = new PwdSafeBean();
		psb.setUserId(userId);
		psb.setTokenId(tokenId);
		dao.insertEmailResetPwd(psb);
	}

	/**
	 * 生成email重置密码link
	 * 
	 * @param path
	 *            email重置密码请求URL
	 * @param userId
	 *            userId
	 * @param tokenId
	 *            tokenId
	 * @return
	 */
	private String generateResetPwdLink(String path, String userId,
			String tokenId) {
		String link = path + "?userId=" + userId + "&" + "tokenId" + "="
				+ tokenId;
		StringBuffer sb = new StringBuffer();
		sb.append("亲爱的用户 " + userId + "：您好！<br/><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您收到这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。");
		sb.append("假如这不是您本人所申请, 请不用理会这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。<br/><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 要使用新的密码, 请使用以下链接启用密码。<br/><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='" + link + "'>" + link
				+ "</a><br/><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可。该链接使用后将立即失效。)<br/><br/>");
		// sb.append("&nbsp;&nbsp;&nbsp; 注意:请您在收到邮&#x4EF6;1个小时内使用，否则该链接将会失效。<br/><br/>");
		return sb.toString();
	}

	private static String md5(String string) throws Exception {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
			md.update(string.getBytes());
			byte[] md5Bytes = md.digest();
			return bytes2Hex(md5Bytes);
		} catch (Exception e) {
			throw e;
		}
	}

	private static String bytes2Hex(byte[] byteArray) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (byteArray[i] >= 0 && byteArray[i] < 16) {
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	}
	
	public boolean checkToken(PwdSafeBean pwdSafeBean) {
		PwdSafeBean psb = dao.selectEmailResetPwd(pwdSafeBean);
		if (psb == null) {
			return false;
		}
		if (!psb.getTokenId().equals(pwdSafeBean.getTokenId())) {
			return false;
		}
		dao.deleteEmailResetPwd(pwdSafeBean);
		if (psb.getTokenValidTime().compareTo(psb.getSysDate()) < 0) {
			return false;
		}
		return true;
	}
	
}
