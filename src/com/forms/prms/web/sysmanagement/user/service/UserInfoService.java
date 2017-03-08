package com.forms.prms.web.sysmanagement.user.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.authority.domain.LoginObject;
import com.forms.platform.core.Common;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.platform.core.util.impl.DateUtils;
import com.forms.prms.tool.EncryptUtil;
import com.forms.prms.tool.ExcelAnalysisXLS;
import com.forms.prms.tool.ExcelAnalysisXLSX;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.InitPwd;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.dao.ImportDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.user.dao.IUserDAO;
import com.forms.prms.web.sysmanagement.user.domain.UserInfo;
import com.forms.prms.web.user.domain.User;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserInfoService {
	@Autowired
	@Qualifier("userInfoDAO")
	private IUserDAO dao;
	@Autowired
	private ImportDao importdao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;	
	
	@Autowired
	public ExcelImportGenernalDeal importGenernalDeal;		//Excel导入Service
	
	//获得类实例
	public static UserInfoService getInstance(){
		return SpringUtil.getBean(UserInfoService.class);
	}

	/**
	 * 新增
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void insert(UserInfo userInfo) throws Exception {
		// 校验待新增员工号是否已存在
		if (this.getUserById(userInfo.getUserId()) != null) {
			CommonLogger.error("员工号:" + userInfo.getUserId() + "已存在，请核实！");
			throw new Exception("员工号：" + userInfo.getUserId() + "已存在，请核实！");
		}

		String password = null;
		try {
			password = new EncryptUtil(userInfo.getUserId()).encryptNumStr(InitPwd.pwd);
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("加密密码时发现异常！");
		}
		userInfo.setPassword(password);
		userInfo.setFirstAddUserId(WebHelp.getLoginUser().getUserId());
		userInfo.setFirstAddDate(DateUtils.getSingleInstance().getFormatDate(new Date(), "yyyy-MM-dd"));
		userInfo.setFirstAddTime(DateUtils.getSingleInstance().getTime());

		// 存储用户基本信息
		CommonLogger.debug("存储用户基本信息,员工号(" + userInfo.getUserId() + "),UserInfoService,insert");
		int affect = dao.insertUser(userInfo);
		if (affect == 0) {
			throw new Exception("用户信息添加失败！可能是责任中心选择错误");
		}
		// 存储用户角色信息
		CommonLogger.debug("存储用户角色信息,员工号(" + userInfo.getUserId() + "),UserInfoService,insert");
		this.insertRole(userInfo);

	}

	/**
	 * 修改
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(UserInfo userInfo) {

		userInfo.setLastModifyUserId(WebHelp.getLoginUser().getUserId());
		userInfo.setLastModifyDate(DateUtils.getSingleInstance().getFormatDate(new Date(), "yyyy-MM-dd"));
		userInfo.setLastModifyTime(DateUtils.getSingleInstance().getTime());
		if ("3".equals(userInfo.getStatus())) {
			CommonLogger.debug("修改用户信息,员工号(" + userInfo.getUserId() + "),UserInfoService,update");
			dao.updateUserInfo(userInfo);
			CommonLogger.debug("修改用户离职日期,员工号(" + userInfo.getUserId() + "),UserInfoService,update");
			dao.updateLeaveDate(userInfo);
		} else {
			// 修改用户基本信息
			CommonLogger.debug("修改用户信息,员工号(" + userInfo.getUserId() + "),UserInfoService,update");
			dao.updateUserInfo(userInfo);
		}
		// 删除原角色
		CommonLogger.debug("删除原角色,员工号(" + userInfo.getUserId() + "),UserInfoService,update");
		dao.delRoleById(userInfo.getUserId());

		// 插入新角色
		CommonLogger.debug("插入新角色,员工号(" + userInfo.getUserId() + "),UserInfoService,update");
		this.insertRole(userInfo);
	}

	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public List<HashMap<String, String>> getRoleList(String withoutRoleIds) {
		CommonLogger.info("获取角色列表,UserInfoService,getRoleList");
		return dao.getRoleList(withoutRoleIds);
	}

	/**
	 * 获取用户列表
	 * 
	 * @param userInfo
	 * @return
	 */
	public List<UserInfo> getUserList(UserInfo userInfo) {
		CommonLogger.info("获取用户列表,UserInfoService,getUserList");
		IUserDAO page = PageUtils.getPageDao(dao);
		return page.getUserList(userInfo);
	}

	/**
	 * 根据员工号获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserById(String userId) {
		CommonLogger.debug("根据员工号获取用户信息,员工号(" + userId + "),UserInfoService,getUserById");
		UserInfo userInfo = dao.getBaseInfo(userId);
		if (userInfo != null) {
			userInfo.setRoleId(dao.getRoleInfo(userId));
		}
		return userInfo;
	}

	/**
	 * 根据员工号获取头像
	 * 
	 * @param userId
	 * @param response
	 * @throws Exception
	 */
	/*public void getImgById(String userId, HttpServletResponse response) throws Exception {
		UserInfo userInfo = dao.getImgInfo(userId);
		OutputStream outputSream = response.getOutputStream();
		if (userInfo != null) {
			byte[] data = userInfo.getImgData();
			response.setContentType("image/jpeg");
			response.setCharacterEncoding("UTF-8");
			InputStream in = new ByteArrayInputStream(data);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				outputSream.write(buf, 0, len);
			}
			outputSream.close();
		} else {
			// 不存在则返回默认图像
			String basicUrl = this.getClass().getResource("/").toString();// 项目根目录
			String defaultPersonFileName = basicUrl.substring(6, basicUrl.length()) + "images/defaultPerson.png";// 去掉前缀“file:”
			File file = new File(defaultPersonFileName);
			FileInputStream iStream = new FileInputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = iStream.read(buf, 0, 1024)) != -1) {
				outputSream.write(buf, 0, len);
			}
			outputSream.close();
		}
	}*/

	/**
	 * 插入用户角色信息
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	private void insertRole(UserInfo userInfo) {
		CommonLogger.debug("插入用户角色信息，角色ID(" + userInfo.getUserId() + ")，UserInfoService，insertRole");
		String roles = userInfo.getRoleId();
		if (roles != null && roles.length() != 0) {
			String[] roleArr = roles.split(",");
			for (String roleId : roleArr) {
				userInfo.setRoleId(roleId);
				dao.insertRole(userInfo);
			}
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param userInfo
	 */
	/*private void readImgData(UserInfo userInfo) throws Exception {
		InputStream is = null;
		try {
			MultipartFile imgFile = userInfo.getImgFile();
			int size = (int) imgFile.getSize();
			if (size > 0) {
				byte[] imgData = new byte[size];
				is = imgFile.getInputStream();
				is.read(imgData);
				userInfo.setImgData(imgData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}*/

	/**
	 * 修改
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void toImport(UserInfo userInfo) {
		try {
			// 取所有的Role
			List<HashMap<String, String>> roleList = this.getRoleList(null);
			Map<String, String> allRoleMap = new HashMap<String, String>();
			for (HashMap<String, String> meteMap : roleList) {
				String roleId = meteMap.get("roleId");
				String roleName = meteMap.get("roleName");
				allRoleMap.put(roleId, roleName);
			}

			List<List<String>> dataList = toAnalysis(userInfo);
			dataList.remove(0);
			String[] columns = { "userId", "userName", "deptId", "phoneNumber", "notesId", "userType", "roleId" };
			String impUser = WebHelp.getLoginUser().getUserId();
			String date = Tool.DATE.getDate();
			String time = Tool.DATE.getTime();
			List<String> allUserId = dao.selAllUserId();
			List<String> checkDeptId = new ArrayList<String>();
			// if(WebHelp.getLoginUser().getRoleIds().indexOf(WebHelp.getSysPara("ROLE_R01"))==-1){
			// if(WebHelp.getLoginUser().getDeptLevel().equals("2")){
			// checkDeptId=deptDao.checkDeptId(WebHelp.getLoginUser().getUpDeptId());
			// }else if(WebHelp.getLoginUser().getDeptLevel().equals("1")){
			// checkDeptId=deptDao.checkDeptId(WebHelp.getLoginUser().getDeptId());
			// }
			// }else{
			// checkDeptId=deptDao.checkDeptId("");
			// }
			List<HashMap> userRoleList = new ArrayList<HashMap>();
			List<HashMap> userLst = new ArrayList<HashMap>();
			HashMap hashMap = null;
			// HashMap roleHash = null;
			for (int j = 0; j < dataList.size(); j++) {
				List<String> data = dataList.get(j);
				hashMap = new HashMap();
				// roleHash = new HashMap();
				if (columns.length != data.size()) {
					throw new Exception("第" + (j + 2) + "行字段长度与导入字段长度不一致或存在空！");
				}
				String outerRoleIdArray[] = null;
				for (int x = 0; x < data.size(); x++) {
					if ("userType".equals(columns[x])) {
						String userType = data.get(x);
						if (!("1".equals(userType) || "2".equals(userType))) {
							throw new Exception("第" + (j + 2) + "行用户分类只能是 1： 实名  2：虚拟!");
						}
					}
					if ("roleId".equals(columns[x])) {
						String roleId = data.get(x);
						if (roleId != null && !"".equals(roleId)) {
							String roleIdArray[] = roleId.split(",|，");
							for (String roleStr : roleIdArray) {
								if (!allRoleMap.containsKey(roleStr)) {
									throw new Exception("第" + (j + 2) + "行选择角色有误！");
								}
							}
							outerRoleIdArray = roleIdArray;
						}
					}
					if ("userId".equals(columns[x])) {
						String userId = data.get(x);
						if (Tool.CHECK.isEmpty(userId) || allUserId.contains(userId)) {
							throw new Exception("第" + (j + 2) + "行用户ID为空或已存在！");
						}
					}
					if ("deptId".equals(columns[x])) {
						String deptId = data.get(x);
						if (Tool.CHECK.isEmpty(checkDeptId) || Tool.CHECK.isEmpty(deptId)
								|| !checkDeptId.contains(deptId)) {
							throw new Exception("第" + (j + 2) + "行所属机构有误！");
						}
					}
					hashMap.put(columns[x], data.get(x));
				}
				/*
				 * if(Tool.CHECK.isEmpty(hashMap.get("userId")) ||
				 * allUserId.contains(hashMap.get("userId").toString()) ){ throw
				 * new Exception("第"+(j+2)+"行用户ID为空或已存在！"); }
				 */
				/*
				 * if(!Tool.CHECK.isEmpty(checkDeptId) &&
				 * !Tool.CHECK.isEmpty(hashMap.get("deptId")) &&
				 * !checkDeptId.contains(hashMap.get("deptId")) ){ throw new
				 * Exception("第"+(j+2)+"行所属机构有误！"); }
				 */
				// hashMap.put("password",
				// WebHelp.encryptPassword(hashMap.get("userId").toString(),
				// "111111"));
				// hashMap.put("password", arg1)
				hashMap.put("firstAddUserId", impUser);
				hashMap.put("firstAddDate", date);
				hashMap.put("firstAddTime", time);
				hashMap.put("isDeleted", "0");
				userLst.add(hashMap);
				for (String roleId : outerRoleIdArray) {
					HashMap<String, String> userRoleMap = new HashMap<String, String>();
					userRoleMap.put("roleId", roleId);
					userRoleMap.put("userId", hashMap.get("userId") + "");
					userRoleList.add(userRoleMap);
				}
			}
			dao.insertUserBatch(userLst);
			dao.insertUserRoleBatch(userRoleList);
		} catch (Exception e) {
			Throw.throwException("导入用户出现异常！" + e.getMessage(), e);
		}

	}

	// 解析文档
	public List<List<String>> toAnalysis(UserInfo userInfo) {
		List<List<String>> dataList = null;
		try {
			/** */
			/** 检查文件名是否为空或者是否是Excel格式的文件 */
			if (userInfo.getUserFile() == null
					|| !userInfo.getUserFile().getOriginalFilename().matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				return null;
			}
			boolean isExcel2003 = true;
			/** */
			/** 对文件的合法性进行验证 */
			if (userInfo.getUserFile().getOriginalFilename().matches("^.+\\.(?i)(xlsx)$")) {
				isExcel2003 = false;
			}
			if (isExcel2003) {
				ExcelAnalysisXLS anaXLS = new ExcelAnalysisXLS();
				dataList = anaXLS.read(userInfo.getUserFile().getInputStream());
			} else {
				ExcelAnalysisXLSX anaXLSX = new ExcelAnalysisXLSX();
				// anaXLSX.setSheetName("Sheet2");
				anaXLSX.process(userInfo.getUserFile().getInputStream());
				dataList = anaXLSX.getDatas();
			}
		} catch (Exception e) {
			Throw.throwException("解析Excel出现异常！", e);
		}
		return dataList;
	}

	/**
	 * 在线用户查询
	 * 
	 * @param userInfo
	 * @return
	 */
	public Object getOnLineUserList(UserInfo userInfo) {
		CommonLogger.info("在线用户查询，UserInfoService，getOnLineUserList");
		IUserDAO page = PageUtils.getPageDao(dao);
		return page.getOnLineUserList(userInfo);
	}

	/**
	 * 强制退出
	 * 
	 * @param userInfo
	 */
	public void forceQuitList(UserInfo userInfo) {
		// userService.deleteOnlineUse
		userInfo.setDelIdLst(Arrays.asList(userInfo.getDelIds()));
		if (null != userInfo.getDelIdLst() && userInfo.getDelIdLst().size() > 0) {

			// 销毁session
			CommonLogger.debug("得到要删除的用户信息,UserInfoService,forceQuitList");
			CommonLogger.debug("删除在线用户信息,UserInfoService,forceQuitList");
			List<UserInfo> users = dao.getQuitUserList(userInfo.getDelIdLst());
			for (int i = 0; i < users.size(); i++) {
				String session = users.get(i).getSessionId();
				String key = users.get(i).getUserId();
				LoginObject lo = new LoginObject(session, key);
				this.logout(lo);
				dao.deleteOnlineUser(users.get(i).getUserId());
			}

		}

	}

	public void forceQuit(String userId) {

		// 销毁session
		CommonLogger.debug("得到要删除的用户信息,UserInfoService,forceQuit");
		List<UserInfo> users = dao.getOnlineUser(userId);
		for (int i = 0; i < users.size(); i++) {
			String session = users.get(i).getSessionId();
			String key = users.get(i).getUserId();
			LoginObject lo = new LoginObject(session, key);
			this.logout(lo);
			// ServletHelp.getSession().removeAttribute(session);
		}

		// WebUtils.getSession().invalidate();

		dao.deleteOnlineUser(userId);
	}

	public void logout(LoginObject lo) {
		if (lo != null)
			Common.getAuthoriseComponent().getLoginController().logout(lo);

	}

	/**
	 * 判断输入密码和原来密码的一致性
	 * 
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String oldPassword) {
		CommonLogger.debug("判断输入密码和原来密码的一致性，原密码(" + oldPassword + "),UserInfoService,checkPassword");
		String password = dao.getPasswordById(WebHelp.getLoginUser().getUserId());
		// String inputPassword =
		// WebHelp.encryptPassword(WebHelp.getLoginUser().getUserId(),
		// oldPassword);
		// String inputPassword = AES.encrypt(oldPassword);
		String inputPassword = null;
		try {
			inputPassword = new EncryptUtil(WebHelp.getLoginUser().getUserId()).encryptNumStr(oldPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CommonLogger.error("获取密码时发现异常！UserInfoService,checkPassword");
		}

		boolean isEqual = false;
		if (password != null && inputPassword != null) {
			if (password.equals(inputPassword)) {
				isEqual = true;
			} else {
				isEqual = false;
			}
		}
		return isEqual;
	}

	/**
	 * 判断输入的新密码是否超过设置的次数
	 * 
	 * @param password
	 * @return
	 */
	public boolean checkPasswordCount(String password, String userId) {
		String loginId = "";
		if (Tool.CHECK.isEmpty(userId)) {
			loginId = WebHelp.getLoginUser().getUserId();
		} else {
			loginId = userId;
		}
		String passwordEqual = null;
		try {
			passwordEqual = new EncryptUtil(loginId).encryptNumStr(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonLogger.info("得到修改的密码已使用的次数,用户ID:" + loginId + ",密码：" + passwordEqual
				+ ",UserInfoservice,checkPasswordCount");
		String count = dao.checkPasswordCount(loginId, passwordEqual);// 得到修改的密码已使用的次数
		String countTop = WebHelp.getSysPara("PWD_REPEAT_TIMES");// 得到可以使用的上限
		boolean isOut = false;
		if (Tool.CHECK.isBlank(count)) {
			count = "0";
		}
		if (Integer.parseInt(count) > Integer.parseInt(countTop)) {
			isOut = true;
		}
		return isOut;
	}

	/**
	 * 修改密码
	 * 
	 * @param userInfo
	 */
	public void changePwd(UserInfo userInfo) {
		// userInfo.setPassword(WebHelp.encryptPassword(WebHelp.getLoginUser().getUserId(),
		// userInfo.getPassword()));
		String password = null;
		try {
			password = new EncryptUtil(WebHelp.getLoginUser().getUserId()).encryptNumStr(userInfo.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// userInfo.setPassword(AES.encrypt(userInfo.getPassword()));
		userInfo.setPassword(password);
		userInfo.setPwd(password);
		userInfo.setUserId(WebHelp.getLoginUser().getUserId());// 得到登录人ID
		CommonLogger.info("用户修改密码,UserInfoService,changePwd");
		dao.updateUser(userInfo);// 更新用户信息
		CommonLogger.info("向密码历史使用表中加入记录,UserInfoService,changePwd");
		dao.insertUserPwd(userInfo);// 向密码历史使用表中加入记录
	}

	/**
	 * 修改资料
	 * 
	 * @param userInfo
	 */
	public void simpleUpdate(UserInfo userInfo) {

		/*try {
			this.readImgData(userInfo);
		} catch (Exception e) {
			Throw.throwException("读取图像信息异常！", e);
		}*/

		String userId = WebHelp.getLoginUser().getUserId();
		userInfo.setUserId(userId);
		dao.simpleUpdate(userInfo);
	}

	public List<HashMap<String, String>> getAllRoleList(String withoutRoleIds) {
		return dao.getAllRoleList(withoutRoleIds);
	}

	public void initPwd(UserInfo userInfo) {

		String password = null;

		try {
			password = new EncryptUtil(userInfo.getUserId()).encryptNumStr(InitPwd.pwd);
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("初始化密码失败，UserInfoService,initPwd");
		}
		userInfo.setPassword(password);
		CommonLogger.info("初始化密码，UserInfoService,initPwd");
		dao.initPwd(userInfo);
	}

	public void lock(UserInfo userInfo) {
		CommonLogger.info("解锁用户，UserInfoService,lock");
		dao.lock(userInfo);
	}

	/**
	 * @methodName getUserPwdProtectInfo desc 获得该用户密保信息
	 * @param userInfo
	 * @return
	 */
	public List<UserInfo> getUserPwdProtectInfo(UserInfo userInfo) {
		CommonLogger.debug("获得该用户密保信息，员工号：" + WebHelp.getLoginUser().getUserId()
				+ ",UserInfoService,getUserPwdProtectInfo");
		userInfo.setUserId(WebHelp.getLoginUser().getUserId());
		return dao.getUserPwdProtectInfo(userInfo);
	}

	/**
	 * @methodName pwdProtectSet desc 设置密保问题
	 * @param userInfo
	 */
	@Transactional(rollbackFor = Exception.class)
	public void pwdProtectSet(UserInfo userInfo) {
		CommonLogger.info("删除密保信息,UserInfoService,pwdProtectSet");
		dao.pwdProtectDelete(userInfo);
		String[] questionAnswer = userInfo.getQuestionAnswer();
		String[] questionId = userInfo.getQuestionId();
		String[] questionSeq = { "1", "2", "3" };
		userInfo.setQuestionSeq(questionSeq);
		if (null != questionAnswer && questionAnswer.length > 0 && null != questionId && questionId.length > 0) {
			List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
			for (int i = 0; i < questionAnswer.length; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("questionAnswer", questionAnswer[i]);
				map.put("questionId", questionId[i]);
				map.put("questionSeq", questionSeq[i]);
				list2.add(map);
			}
			userInfo.setPwdQuestionList(list2);
			CommonLogger.info("设置密保信息,UserInfoService,pwdProtectSet");
			dao.pwdProtectSet(userInfo);
		}

	}

	public void pwdProtectSetEmail(UserInfo userInfo) {
		CommonLogger.info("设置邮箱,UserInfoService,pwdProtectSet");
		if (!Tool.CHECK.isBlank(userInfo.getNotesId())) {
			dao.editInfo(userInfo);
		}
	}

	public String checkOrg1Code(String dutyCode) {
		CommonLogger.info("查询用户所选责任中心一级行,UserInfoService,pwdProtectSet");
		String org1Code = dao.checkOrg1Code(dutyCode);
		return org1Code;
	}

	public String isSuperAdmin(String userId) {
		return dao.isSuperAdmin(userId);
	}
	
	public List<UserInfo> selectRole(UserInfo userInfo) {
		CommonLogger.info("角色列表查询,UserInfoService,selectRole");
		IUserDAO page = PageUtils.getPageDao(dao);
		return page.selectRole(userInfo);
	}
	/**
	 * 用户导出
	 * @param userInfo
	 * @return
	 * @throws Exception 
	 */
	public String userExport(UserInfo userInfo) throws Exception {
		String sourceFileName = "用户数据导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("userId", userInfo.getUserId());
		map.put("userName", userInfo.getUserName());
		map.put("dutyCode", userInfo.getDutyCode());
		map.put("dutyName", userInfo.getDutyName());
		map.put("orgCode", userInfo.getOrgCode());
		map.put("orgName", userInfo.getOrgName());
		map.put("ouCode", userInfo.getOuCode());
		map.put("roleId", userInfo.getRoleId());
		map.put("haveRole", userInfo.getHaveRole());
		map.put("isSuper", userInfo.getIsSuper());
		map.put("isSuperAdmin", WebHelp.getLoginUser().getIsSuperAdmin());
		map.put("isBuyer", userInfo.getIsBuyer());
		map.put("org1Code", WebHelp.getLoginUser().getOrg1Code());
		if ("1".equals(WebHelp.getLoginUser().getIsSuperAdmin())) {
			map.put("org21Code", WebHelp.getLoginUser().getOrg1Code());
		}else {
			map.put("org21Code", WebHelp.getLoginUser().getOrg2Code());
		}
		return exportDeal.execute(sourceFileName, "USER_EXPORT", destFile , map);
	}

	public List<UserInfo> userExportExcute(UserInfo bean) {
		return dao.getExportUserList(bean);
	}
	
	public String userRoleExport(UserInfo bean) throws Exception{
		CommonLogger.info("得到生成的一个任务ID,UserInfoService,userRoleExport");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		String isSuperAdmin = WebHelp.getLoginUser().getIsSuperAdmin();
		String destFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/userRoleRlnExport.xlsx";
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		if ("0".equals(isSuperAdmin)) {
			map.put("org21Code", org2Code);
		} else {
			map.put("org21Code", org1Code);
		}
		map.put("isSuperAdmin", isSuperAdmin);
		map.put("flag", bean.getFlag());
		return exportDeal.execute("用户职责信息导出", "USER_ROLE_RLN_EXPORT",destFile,map);
	}
	
	/** 
	 * @methodName userRoleList
	 * desc   获取指定范围的需下载数据
	 * 
	 * @param map 
	 */
	public List<UserInfo> userRoleList(UserInfo bean) {
		return dao.userRoleList(bean);
	}
	
	/**
	 * 导入
	 * @param bean
	 * @throws Exception 
	 */
	public void addExcel(ImportBean bean) throws Exception {
		//1.[新增预算模板]操作，预算Bean处理，保存模板信息至数据库
		User instOper = WebHelp.getLoginUser();				//获取创建柜员
		//1.5【复制Excel文件另存保存】将上传的Excel文件另存为服务器指定物理路径
		//上传组件保存的文件原始文件路径
		String uploadFileUrl = bean.getPath();
		//上传组件自命名的文件名
		String uploadFileRename = uploadFileUrl.substring(uploadFileUrl.lastIndexOf("/")+1,uploadFileUrl.length());	
		String targetFileUrl = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"+uploadFileRename;		//复制到服务器本地路径+日期+上传组件重命名文件名保存(路径+日期文件夹+生成文件名)
		//【创建文件夹路径】判断目标文件夹路径，不存在则创建
		ImportUtil.createFilePath(new File(targetFileUrl.substring(0, targetFileUrl.lastIndexOf("/"))));
		//【复制文件】将文件复制到服务器本地指定地址保存
		ImportUtil.fileCopyByBufferStream(new File(uploadFileUrl), new File(targetFileUrl));
		//将文件保存到数据库
//					dbFileOperUtil.saveFileToDB(targetFileUrl);
		//【更改DB保存路径】更改上传文件保存信息
		targetFileUrl = targetFileUrl.replaceAll("\\\\", "/");
		bean.setPath( targetFileUrl ); 		//上传组件在源文件下载时需要"/"标识符寻找路径
		
		bean.setInstUser(instOper.getUserId());
		bean.setOrg21Code(instOper.getOrg1Code());
		
		//取得批次号
		String batchNo = dao.getId(bean);
		bean.setBatchNo(batchNo);
		bean.setExcelStatus(MontAprvType.EXCEL_E0);	
		dao.addData(bean);	
		
		//3.开始进行[导入Excel]处理
		boolean ExcelImportFlag = false;
		try {
			ExcelImportFlag = this.importExcel(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonLogger.debug("[ImportExcel导入操作已启用]："+ExcelImportFlag);
	}
	
	
	/**
	 * 调用导入Excel操作
	 * @param 	budget	导入Excel相关的预算模板
	 * @return			Excel导入操作是否已执行(与导入结果无关)
	 * @throws 	Exception
	 */
	public boolean importExcel(ImportBean bean) throws Exception{
		boolean flag = false;
		String excelUrl = bean.getPath();
		String filePath = excelUrl.substring(0,excelUrl.lastIndexOf("/")+1);
		String fileSecName = excelUrl.substring(excelUrl.lastIndexOf("/")+1,excelUrl.length());
		//2.Excel导入参数处理
		String excelDesc = "";
		excelDesc = "用户职责信息导入";
		bean.setConfigId("USER_ROLE_RLN_IMPORT");
		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("org21Code", bean.getOrg21Code());
		beans.put("org1Code", WebHelp.getLoginUser().getOrg1Code());
		beans.put("impBatch", bean.getBatchNo());
		beans.put("proType", bean.getProType());
		beans.put("loadType", "01");
		beans.put("instType", bean.getInstType());
		//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
		importGenernalDeal.execute(filePath, fileSecName, excelDesc, new String[]{bean.getConfigId()}, beans);	
		 
		//导入操作已进行=true
		flag = true;
		return flag;
	}
	
	public List<ImportBean> userRoleRlnList(UserInfo userInfo){
		userInfo.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		IUserDAO page = PageUtils.getPageDao(dao);
		return page.userRoleRlnList(userInfo);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserRoleRln(ImportBean eb) {
		importdao.deleteTaskLoad(eb);
		dao.deleteBatch(eb);
		importdao.deleteError(eb);
		dao.deleteDetail(eb);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void submitUserRoleRln(String batchNo,String isCheck,String userId) {
		dao.submitUserRoleRln(batchNo,isCheck,userId);
	}
	
	public ImportBean getPath(String batchNo) {
		return dao.getPath(batchNo);
	}
	
	public ImportBean getDetail(ImportBean bean) {
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		return dao.getDetail(bean);
	}
	
	public List<ImportBean> getErrData(ImportBean bean) {
		
		IUserDAO page = PageUtils.getPageDao(dao);
		return page.getErrData(bean);
	}
	
	/**
	 * 判断是否已经导入过 了
	 * @param bean
	 * @return 
	 */
	public Map<String, Object> ajaxDataExist() {
		ImportBean bean = new ImportBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String flag ="Y";
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		//看看有没有待提交的数据 ，若有不准导入新的数据
		int count3 = dao.isHaveNotOver(bean);
		if (count3>0) {
			map.put("msg", "存在待复核的数据尚未提交，请提交后再新增导入!");
			map.put("flag", "N");
			return map;
		}
		map.put("msg", msg);
		map.put("flag", flag);
		return map;
	}
	
}
