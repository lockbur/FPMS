package com.forms.prms.web.tag.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.org.cachebean.CachedZtree;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.service.OrgService;
import com.forms.prms.web.tag.dao.TagCommonDao;
import com.sun.star.uno.Exception;

@Service
public class SystemCommonService {
	private static SystemCommonService instance = null;
	@Autowired
	private TagCommonDao dao;
	@Autowired
	private OrgService orgService;
	@Autowired
	private TagCommonService tagCommonService;
	
	
	//交叉表的内存存储
	public static List<Map<String, Object>> org1MapList = new ArrayList<Map<String, Object>>();
	
		
	public static SystemCommonService getInstance() {
		return SpringUtil.getBean(SystemCommonService.class);
	}
	// 系统初始化信息
	public void init() {
		CommonLogger.info("系统初始化机构树信息");
		try {
			//往参数表增加机构树刷新标识数据
			orgService.insertInteAddress();
			refreshTree();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 初始化 机构树
	 */
	public void initOrgTree() {
		// 初始化 机构树信息
		if (null == org1MapList) {
			org1MapList = new ArrayList<Map<String, Object>>();
		}
		CommonLogger.info("初始化机构树信息");
		List<String> org1List = dao.getOrg1List();
		if (null != org1List && org1List.size() > 0) {
			for (int i = 0; i < org1List.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				List<BaseBean> detailList = dao.getDetailList(org1List.get(i));
				map.put("org1Code", org1List.get(i));
//				map.put(org1List.get(i), detailList);
				try {
					CachedZtree cachedZtree =orgService.getCachedZtree(detailList);
					map.put(org1List.get(i)+"CacheTree", cachedZtree);
				} catch (Exception e) {
					e.printStackTrace();
					CommonLogger.error("初始化机构树信息失败!"+e);
				}
				org1MapList.add(map);
			}
		}
	}

	/**
	 * 得到一级行下面的交叉信息
	 * 
	 * @param org1Code 这个可能是一级行 可能是二级行
	 * @return
	 * @throws Exception 
	 */
	public CachedZtree cachedZtree(String rootId) throws Exception {
		if (Tool.CHECK.isEmpty(rootId)) {
			rootId = WebHelp.getLoginUser().getOrg1Code();
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String org1Code = orgService.getOrg1Code(rootId);
		if (Tool.CHECK.isEmpty(org1Code)) {
			throw new Exception("该机构树在交叉表中不存在");
		}
		CachedZtree cachedZtree = null;	
		boolean flag = false;
		if (null != org1MapList && org1MapList.size() > 0) {
			for (int i = 0; i < org1MapList.size(); i++) {
				if (org1Code.equals(org1MapList.get(i).get("org1Code"))) {
					// 如果这个一级行数据在内存中存在 从内存中取
					flag = true;
//					baseBeanList = (List<BaseBean>) org1MapList.get(i).get(
//							org1Code);
					cachedZtree = (CachedZtree) org1MapList.get(i).get(org1Code+"CacheTree");
				}
			}
			
		}
		if (!flag) {
			//一级行数据在内存中不存在
			baseBeanList = dao.getDetailList(org1Code);
			//将数据放入内存
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("org1Code", org1Code);
//			map.put(org1Code, baseBeanList);
			try {
				cachedZtree = orgService.getCachedZtree(baseBeanList);
				map.put(org1Code+"CacheTree", cachedZtree);
			} catch (Exception e) {
				e.printStackTrace();
				CommonLogger.error("从数据库中加载机构树信息失败,根节点为"+org1Code);
			}
			org1MapList.add(map);
		}
		return cachedZtree;

	}
	/**
	 * 交叉表重新导入的时候 刷新
	 * @throws UnknownHostException 
	 */
	public void refreshTree() throws UnknownHostException{
		org1MapList = null;
		this.initOrgTree();
		//更新本机器名到参数表
		String myAddress;
		myAddress = InetAddress.getLocalHost().getHostName().toString();
		orgService.updateInteAddress(myAddress);
		
		
	}

}
