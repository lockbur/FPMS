package com.forms.prms.web.cluster.lock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.cluster.lock.domain.ClusterLock;

@Repository
public interface ClusterLockDAO {

	/**
	 * 加锁
	 * 
	 * @param cl
	 */
	public void insertClusterLock(ClusterLock cl);

	/**
	 * 查询任务是否锁住
	 * 
	 * @param cl
	 * @return
	 */
	public ClusterLock selectClusterLock(ClusterLock cl);

	/**
	 * 查询系统所有集群锁
	 * 
	 * @param cl
	 * @return
	 */
	public List<ClusterLock> getClusterLockList();

	/**
	 * 释放锁
	 * 
	 * @param cl
	 */
	public void deleteClusterLock(ClusterLock cl);

	/**
	 * 系统启动时清空锁
	 * 
	 * @param ipAddress
	 */
	public void clearClusterLock(String ipAddress);

	public void updateStatusB1(@Param("ipAddress") String localName,@Param("memo") String memo);

	public void updateStatusB2(@Param("ipAddress") String localName,@Param("memo") String memo);

	public void updateStatusC1(@Param("ipAddress")String localName, @Param("memo")String string);
}
