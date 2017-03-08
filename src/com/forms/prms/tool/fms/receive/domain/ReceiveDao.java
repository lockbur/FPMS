package com.forms.prms.tool.fms.receive.domain;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.user.domain.User;

@Repository
public interface ReceiveDao {

	/**
	 * 接收fms回的文件进行处理21
	 * 
	 * @param batchNo
	 */
	public void dealFms21(@Param("batchNo") String batchNo);

	/**
	 * 接收fms回的文件进行处理22
	 * 
	 * @param batchNo
	 */
	public void dealFms22(@Param("batchNo") String batchNo);

	/**
	 * 接收fms回的文件进行处理31
	 * 
	 * @param batchNo
	 */
	public void dealFms31(@Param("batchNo") String batchNo);

	/**
	 * 接收fms回的文件进行处理32
	 * 
	 * @param batchNo
	 */
	public void dealFms32(@Param("batchNo") String batchNo);

	/**
	 * 接受fms第一次回的文件进行处理
	 * 
	 * @param batchNo
	 */
	public void dealFms34(@Param("batchNo") String batchNo,@Param("instOper") String instOper);

	/**
	 * 接受fms第二次回的文件进行处理
	 * 
	 * @param batchNo
	 */
	public void dealFms25(@Param("batchNo") String batchNo,@Param("instOper") String instOper);

	/**
	 * 预提待摊  第二次回文件（结果文件处理）
	 * @param batchNo
	 */
	public void dealFms23(@Param("batchNo")String batchNo);
	
	/**
	 * 预提待摊 第一次回文件（校验文件处理）
	 * @param batchNo
	 */
	public void dealFms33(@Param("batchNo")String batchNo);
	/**
	 *用户文件
	 * @param batchNo
	 * @param pwd 
	 */
	public void dealFms11(@Param("batchNo")String batchNo);
	/**
	 * 机构文件
	 * @param batchNo
	 */
	public void dealFms12(@Param("batchNo")String batchNo);
	/**
	 * 供应商
	 * @param batchNo
	 */
	public void dealFms13(@Param("batchNo")String batchNo);
	
	/**
	 * 查询初始密码
	 * @return
	 */
	public List<User> selectInitUser();
	
	public void updateUserPwd(@Param("userId")String userId, @Param("password")String password);

}
