package com.forms.prms.web.sysmanagement.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.user.domain.UserInfo;

@Repository("userInfoDAO")
public interface IUserDAO {
	int insertUser(UserInfo userInfo);

	void insertRole(UserInfo userInfo);

	List<HashMap<String, String>> getRoleList(@Param("withoutRoleIds") String withoutRoleIds);

	List<UserInfo> getUserList(UserInfo userInfo);
	
	List<UserInfo> getExportUserList(UserInfo userInfo);
	
	UserInfo getBaseInfo(String userId);

	String getRoleInfo(String userId);

//	UserInfo getImgInfo(String userId);

	public void updateUserInfo(UserInfo userInfo);

	void delRoleById(String userId);

	public List<String> selAllUserId();

	public void insertUserBatch(@Param("batch") List<HashMap> batchParams);

	public void insertUserRoleBatch(@Param("batch") List<HashMap> batchParams);

	public List<UserInfo> getOnLineUserList(UserInfo userInfo);

	List<UserInfo> getQuitUserList(@Param("delIdLst") List<String> delIdLst);

	List<UserInfo> getOnlineUser(String userId);

	void deleteOnlineUser(String userId);

	public String getPasswordById(@Param("userId") String userId);

	public String checkPasswordCount(@Param("userId") String userId, @Param("passwordEqual") String passwordEqual);


	public String getPwdValidityDate();// 得到密码的有效期

	public void insertUserPwd(UserInfo userInfo);// 插入用户密码表中数据

	public void updateUser(UserInfo userInfo);// 更新用户密码信息
	
	public void simpleUpdate(UserInfo userInfo);//修改资料

	List<HashMap<String, String>> getAllRoleList(@Param("withoutRoleIds") String withoutRoleIds);

	public void initPwd(UserInfo userInfo);//初始化密码

	void updateLeaveDate(UserInfo userInfo);//修改离职时间
	
	public void lock(UserInfo userInfo);//修改密码出错次数
	
	public List<UserInfo> getUserPwdProtectInfo(UserInfo userInfo);
	
	public void pwdProtectDelete(UserInfo userInfo);
	
	public void pwdProtectSet(UserInfo userInfo);

	public void editInfo(UserInfo userInfo);

	public String checkOrg1Code(String dutyCode);

	public String isSuperAdmin(String userId);

	List<UserInfo> selectRole(UserInfo userInfo);
	
	public List<UserInfo> userRoleList(UserInfo userInfo);
	
	/**
	 * 得到主键
	 * @param bean
	 */
	public String getId(ImportBean bean);
	
	/**
	 * 添加汇总信息
	 * @param bean
	 */
	public void addData(ImportBean bean);

	public List<ImportBean> userRoleRlnList(UserInfo userInfo);
	
	/**
	 * 删除汇总信息
	 */
	public void deleteBatch(ImportBean montBean);
	
	/**
	 * 删除明细信息
	 */
	public void deleteDetail(ImportBean montBean);

	/**
	 * 用户职责信息提交存储过程
	 * @param batchNo
	 * @param org1Code 
	 */
	public void submitUserRoleRln(@Param("batchNo")String batchNo,@Param("isCheck")String isCheck,@Param("userId")String userId);

	
	public ImportBean getPath(@Param("batchNo") String batchNo);
	
	public ImportBean getDetail(ImportBean bean);
	
	public List<ImportBean> getErrData(ImportBean bean);
	
	public int isHaveNotOver(ImportBean bean);
}
