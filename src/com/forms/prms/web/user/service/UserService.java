package com.forms.prms.web.user.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.authority.ILoginController;
import com.forms.platform.authority.domain.LoginObject;
import com.forms.platform.core.Common;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.user.dao.UserDao;
import com.forms.prms.web.user.domain.CtrlFileBean;
import com.forms.prms.web.user.domain.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public static UserService getInstance() {
		return SpringUtil.getBean(UserService.class);
	}

	public User getUser() {
		return WebHelp.getLoginUser();
	}

	// public User checkPwd(String userId, String password) {
	// User user = userDao.queryByUserId(userId);
	// // if (user == null ||
	// !user.getPassword().equals(WebHelp.encryptPassword(userId, password))) {
	// String inputPassword=null;
	// try
	// {
	// inputPassword = new EncryptUtil("").encryptNumStr(password);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	//
	//
	// if (user == null || !user.getPassword().equals(inputPassword)) {
	// Common.getMessageManager().addErrorMessage("login.failed");
	// return null;
	// }
	// user.setPassword(null);
	// return user;
	// }

	public void login(LoginObject lo) {
		ILoginController lc = Common.getAuthoriseComponent().getLoginController();
		if (lc.isLogin((String) lo.getKey())) {
			lc.logout((String) lo.getKey());
		}
		lc.login(lo);
	}

	public void logout(LoginObject lo) {
		if (lo != null)
			Common.getAuthoriseComponent().getLoginController().logout(lo);
	}

	/**
	 * 查询在线用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<User> queryOnlineUser(String userId) {
		return userDao.queryOnlineUser(userId);
	}

	/**
	 * 新增一条在线用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int addOnlineUser(User user) {
		// 当前登录时间
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();
		String nowStr = sim.format(now);
		user.setLoginDateTime(nowStr);
		// 当前登录客户端IP
		String loginIp = WebUtils.getRequest().getRemoteAddr();
		user.setLoginIp(loginIp);
		return userDao.addOnlineUser(user);
	}

	/**
	 * 根据用户id更新在线用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int updateOnlineUser(User user) {
		// 当前登录时间
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();
		String nowStr = sim.format(now);
		user.setLoginDateTime(nowStr);
		return userDao.updateOnlineUser(user);
	}

	/**
	 * 删除在线用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteOnlineUser(String userId) {
		return userDao.deleteOnlineUser(userId);
	}

	/**
	 * 根据sessionId删除在线用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteOnlineUserBySessionId(String sessionId) {
		return userDao.deleteOnlineUserBySessionId(sessionId);
	}

	/**
	 * 密码修改时间为null则为初始密码
	 * 
	 * @param userId
	 * @return
	 */
	public boolean checkInitPwd(String userId) {
		User userInfo = userDao.getUser(userId);
		if (null != userInfo && Tool.CHECK.isEmpty(userInfo.getPwdChangeDate())) {
			return true;
		}
		return false;
	}

	/**
	 * 查询实体
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(String userId) {
		return userDao.getUser(userId);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param password
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean changePwd(User user) {
		boolean flag1 = userDao.changePwd(user) > 0;
		// 往用户和密码历史表添加数据
		boolean flag2 = userDao.insertUserPwd(user) > 0;
		if (flag1 && flag2) {
			return true;
		} else {
			return false;
		}
	}

	public void initOnlineUser() {
		userDao.initOnlineUser();
	}

	/**
	 * 获取所有控件下载列表
	 * 
	 * @return
	 */
	public List<CtrlFileBean> getDownloadList() {
		return userDao.getDownloadList();
	}

	/**
	 * 控件下载
	 * 
	 * @param request
	 * @param response
	 * @param ctrlFileBean
	 * @throws Exception
	 */
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, CtrlFileBean ctrlFileBean)
			throws Exception {

		String fileRealPath = ctrlFileBean.getSourceFpath();
		fileRealPath = fileRealPath.replace("\\", "/");
		File file = new File(fileRealPath);
		if (!file.exists()) {
			throw new Exception("找不到指定文件：" + fileRealPath);
		} else {
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = ctrlFileBean.getSourceFname()
					+ ctrlFileBean.getSourceFpath().substring(ctrlFileBean.getSourceFpath().lastIndexOf("."));
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			BufferedOutputStream bos = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] b = new byte[8192];
				int data = 0;
				while ((data = fis.read(b)) != -1) {
					bos.write(b, 0, data);
				}
				bos.flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 修改用户密码错误次数
	 * 
	 * @param user
	 */
	public void updatePwdErrorCount(User user) {
		// TODO Auto-generated method stub
		userDao.updatePwdErrorCount(user);

	}

	/**
	 * 将错误密码输入错误为0
	 * 
	 * @param nUser
	 */
	public void initPwdEroCount(User nUser) {
		// TODO Auto-generated method stub
		userDao.initPwdEroCount(nUser);
	}

	public String getUserRole(String userId) {
		// TODO Auto-generated method stub
		return userDao.getUserRole(userId);
	}

	/**
	 * 初始化用户登录bean
	 * 
	 * @param nUser
	 */
	public void initUser(User nUser) {
		// 系统信息提示初始是open状态
		// nUser.setSystemMsgType("open");
		nUser.setRoleId((userDao.getUserRole(nUser.getUserId())));// 用户角色
		userDao.initPwdEroCount(nUser);// 登陆成功 将错误密码输入次数更新为0
		userDao.addOnlineUser(nUser);
	}

	/**
	 * 初始化错误密码记录
	 * 
	 * @param user
	 */
	public void initErrorPwd(User user) {
		userDao.initErrorPwd(user);

	}

	/**
	 * 获取数据库时间
	 * 
	 * @return
	 */
	public String getDate() {
		return userDao.getDate();
	}

	public List<User> getRole(User userInfo) {
		return userDao.getRole(userInfo);
	}

}
