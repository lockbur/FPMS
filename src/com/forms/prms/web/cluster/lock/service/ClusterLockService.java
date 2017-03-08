package com.forms.prms.web.cluster.lock.service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.cluster.lock.dao.ClusterLockDAO;
import com.forms.prms.web.cluster.lock.domain.ClusterLock;

@Service
public class ClusterLockService {

	@Autowired
	private ClusterLockDAO cDao;
	private static int REMOVE_LOCK_TIMES = 3;

	/**
	 * 加锁
	 * 
	 * @param cl
	 */
	public boolean addLock(String taskType, String taskSubType, String instOper, String memo) {
		ClusterLock cl = new ClusterLock();
		try {
			if (Tool.CHECK.isEmpty(taskType) || Tool.CHECK.isEmpty(taskSubType)) {
				CommonLogger.error("加锁失败！参数不能为空！,ClusterLockService,addLock()");
				return false;
			} else {
				cl.setTaskType(taskType);
				cl.setTaskSubType(taskSubType);
			}
			cl.setIpAddress(getLocalName());//由ip改为本机名
			if (Tool.CHECK.isEmpty(instOper)) {
				cl.setInstOper(WebHelp.getLoginUser().getUserId());
			} else {
				cl.setInstOper(instOper);
			}
			cl.setMemo(memo);
			cDao.insertClusterLock(cl);
			return true;
		} catch (Exception e) {
			CommonLogger.error("加锁失败，任务已加锁!,ClusterLockService,addLock()", e);
			return false;
		}

	}

	/**
	 * 查询任务是否锁住
	 * 
	 * @param cl
	 * @return
	 */
	public boolean isLock(String taskType, String taskSubType) {
		ClusterLock cl = new ClusterLock();
		if (Tool.CHECK.isEmpty(taskType) || Tool.CHECK.isEmpty(taskSubType)) {
			CommonLogger.error("查询任务是否加锁失败！参数不能为空！,ClusterLockService,isLock()");
			return false;
		} else {
			cl.setTaskType(taskType);
			cl.setTaskSubType(taskSubType);
		}
		ClusterLock getCL = cDao.selectClusterLock(cl);
		if (Tool.CHECK.isEmpty(getCL)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 查询系统所有集群锁
	 * 
	 * @param cl
	 * @return
	 */
	public List<ClusterLock> getClusterLockList() {
		ClusterLockDAO pageDao = PageUtils.getPageDao(cDao);
		return pageDao.getClusterLockList();
	}

	/**
	 * 释放锁
	 * 
	 * @param cl
	 */
	public boolean removeLock(String taskType, String taskSubType, String instOper) {
		ClusterLock cl = new ClusterLock();
		if (Tool.CHECK.isEmpty(taskType) || Tool.CHECK.isEmpty(taskSubType)) {
			CommonLogger.error("释放任务锁失败！参数不能为空！,ClusterLockService,removeLock()");
			return false;
		} else {
			cl.setTaskType(taskType);
			cl.setTaskSubType(taskSubType);
		}
		if (Tool.CHECK.isEmpty(instOper)) {
			cl.setInstOper(WebHelp.getLoginUser().getUserId());
		} else {
			cl.setInstOper(instOper);
		}
		int times = 0;
		boolean flag = deleteClusterLock(cl);
		while (!flag && times < REMOVE_LOCK_TIMES) {
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				CommonLogger.error("释放任务锁失败！,ClusterLockService,removeLock() Exception:", e);
			}
			times++;
			flag = deleteClusterLock(cl);
		}
		return flag;
	}

	public boolean deleteClusterLock(ClusterLock cl) {
		try {
			cDao.deleteClusterLock(cl);
			CommonLogger.info("任务解锁成功！（操作柜员："+cl.getInstOper()+"）,ClusterLockService,deleteClusterLock()");
			return true;
		} catch (Exception e) {
			CommonLogger.error("任务解锁失败！（操作柜员："+cl.getInstOper()+"）,ClusterLockService,deleteClusterLock()", e);
			return false;
		}
	}

	/**
	 * 系统启动时清空本机占用锁
	 * 
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void clearLock() {
//		for (String ipAddress : ClusterLockService.getLocalIpList()) {
//			cDao.clearClusterLock(ipAddress);
//		}
		try {
			cDao.clearClusterLock(ClusterLockService.getLocalName());
			//在清空互斥锁之前修改互斥锁对应的数据的状态
			//修改监控指标的状态
			cDao.updateStatusB1(ClusterLockService.getLocalName(),"服务器"+ClusterLockService.getLocalName()+"启动时，中止正在复核的监控指标");
			//修改预算的状态
			cDao.updateStatusB2(ClusterLockService.getLocalName(),"服务器"+ClusterLockService.getLocalName()+"启动时，中止正在复核的预算");
			//修改责任中心撤并的状态
			cDao.updateStatusC1(ClusterLockService.getLocalName(),"服务器"+ClusterLockService.getLocalName()+"启动时，中止正在复核的责任中心撤并");
			
			cDao.clearClusterLock(ClusterLockService.getLocalName());
		} catch (UnknownHostException e) {
			CommonLogger.error("系统启动时清空本机占用锁出错异常！,ClusterLockService,clearLock()", e);
			//e.printStackTrace();
		}
	}

	/**
	 * 获取本机IP列表 取IP4地址
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static List<String> getLocalIpList() throws SocketException {
		List<String> list = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> cardipaddress = ni.getInetAddresses();
			while (cardipaddress.hasMoreElements()) {
				InetAddress inetAddress = cardipaddress.nextElement();
				String ipAddress = inetAddress.getHostAddress();
				if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
					list.add(ipAddress);
				}
			}
		}
		return list;
	}

	/**
	 * 获取本机名
	 * 
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException 
	 */
	public static String getLocalName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName().toString();
	}
}
