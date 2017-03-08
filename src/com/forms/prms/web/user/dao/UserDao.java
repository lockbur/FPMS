package com.forms.prms.web.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.user.domain.CtrlFileBean;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.user.domain.UserContactData;

@Repository
public interface UserDao
{

	public User queryByUserId(String userId);

	/**
	 * 选择用户联系人数据
	 * 
	 * @param userIds
	 * @return
	 */
	public List<UserContactData> queryUsersContentData(List<String> userIds);
	
	/**
	 * 根据用户id查询在线用户信息
	 * @param userId
	 * @return
	 */
	public List<User> queryOnlineUser(@Param("userId")String userId);
	
	/**
	 * 根据用户id更新在线用户信息
	 * @param userId
	 * @return
	 */
	public int updateOnlineUser(User user);
	
	/**
	 * 新增一条在线用户记录
	 * @param user
	 * @return
	 */
	public int addOnlineUser(User user);
	
	/**
	 * 根据用户id删除在线用户信息
	 * @param userId
	 * @return
	 */
	public int deleteOnlineUser(@Param("userId")String userId);
	
	/**
	 * 根据sessionId删除在线用户信息
	 * @param sessionId
	 * @return
	 */
	public int deleteOnlineUserBySessionId(@Param("sessionId")String sessionId);
	/**
	 * 查找一条用户信息
	 * @param userId
	 * @return
	 */
	public User getUser(String userId);
	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 */
	public int changePwd(User user);
	/**
	 * 初始化在线用户表
	 */
	public void initOnlineUser();
	/**
	 * \往用户和密码中间表添加数据
	 * @param user
	 */
	public int insertUserPwd(User user);


	/**
	 * 获取所有控件下载列表
	 * @return
	 */
	public List<CtrlFileBean> getDownloadList();
	/**
	 * 修改用户密码输入错误次数
	 * @param user
	 */
	public void updatePwdErrorCount(User user);
	/**
	 * 将错误密码次数更新为0
	 */
	public void initPwdEroCount(User user);

	public String getUserRole(@Param("userId") String userId);
	/**
	 * 初始化重置密码
	 * @param user
	 */
	public void initErrorPwd(User user);
	
	/**
	 * 获取数据库时间
	 * @return 
	 * 
	 */
	public String getDate();

	public List<User> getRole(User userInfo);

}
