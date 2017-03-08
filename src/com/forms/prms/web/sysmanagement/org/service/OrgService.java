package com.forms.prms.web.sysmanagement.org.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.org.cachebean.CachedZtree;
import com.forms.prms.web.sysmanagement.org.dao.OrgDao;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.domain.Node;
import com.forms.prms.web.sysmanagement.org.domain.Org;
import com.forms.prms.web.tag.service.SystemCommonService;

@Service
public class OrgService {
	@Autowired
	private OrgDao orgDao;
	@Autowired
	private SystemCommonService systemCommonService;
	/**
	 * 查询机构列表
	 * @return
	 */
	public List<BaseBean> getOrgList(BaseBean baseBean)
	{
		return orgDao.getOrgList(baseBean);
	}
	
	/**
	 * 机构控件查询列表
	 * @return
	 */
	public List<BaseBean> getOrgListTag(BaseBean baseBean)
	{
		if( "1".equalsIgnoreCase(baseBean.getTreeType()) )
		{
			if( Tool.CHECK.isBlank(baseBean.getRootLevel()) && Tool.CHECK.isBlank(baseBean.getRootNodeId()) )
			{
				baseBean.setRootLevel("0");
			}
			return orgDao.getDevTypeListTag(baseBean);
		}
		else
		{
			return orgDao.getOrgListTag(baseBean);
		}
		
	}
	
	//@CacheEvict()
//	@CachePut(value="base")
//	public void setCachedZtreeTag()
//	throws Exception
//	{
//		getCachedZtreeTag(new BaseBean());
//	}
	
	/**
	 * 机构控件查询列表
	 * @return
	 */
//	@Cacheable(value="base")
//	public CachedZtree getCachedZtreeTag(BaseBean baseBean)
//	throws Exception
//	{
//		
//		long startTime = new Date().getTime();
//		long endTime = 0;
//		List<BaseBean> dataList = orgDao.getCachedZtreeTag(baseBean);
//		endTime = new Date().getTime();
//		System.out.println( "责任中心查询sql，执行所用时间：" + ( endTime - startTime) / 1000.00 );
//		
//		startTime = new Date().getTime();
//		DutyToolService dtService = new DutyToolService(dataList);
//		CachedZtree cachedZtree = dtService.getCachedZtree();
//		endTime = new Date().getTime();
//		System.out.println( "处理责任中心查询结果为map所用时间：" + ( endTime - startTime) / 1000.00 );
//		
//		return cachedZtree;
//	}
	public CachedZtree getCachedZtreeTag(BaseBean baseBean,String rootNodeId)
	throws Exception
	{
		
		long startTime = new Date().getTime();
		long endTime = 0;
//		List<BaseBean> dataList = orgDao.getCachedZtreeTag(baseBean);
//		List<BaseBean> dataList = systemCommonService.getBaseBeanList(WebHelp.getLoginUser().getOrg1Code());
		endTime = new Date().getTime();
		startTime = new Date().getTime();
		
		String address = WebHelp.getSysPara("MEMORY_TB_FNDWRR");
		String myAddress=InetAddress.getLocalHost().getHostName().toString();
		boolean flag = false;
		if (!Tool.CHECK.isEmpty(address)) {
			String[] inetAdress = address.split(" | ");
			for (int i = 0; i < inetAdress.length; i++) {
				if (myAddress.equals(inetAdress[i])) {
					//直接从内存中拿
					flag = true;
				}
			}
			if (!flag) {
				//需要重置所有的机构树
				systemCommonService.refreshTree();
			}
		}else {
			//没有这个参数，说明系统跑完初始化机构树相关信息失败
		}
		CachedZtree cachedZtree = systemCommonService.cachedZtree(rootNodeId);
		endTime = new Date().getTime();
		System.out.println( "处理责任中心查询结果为map所用时间：" + ( endTime - startTime) / 1000.00 );
		
		return cachedZtree;
	}
	
	public CachedZtree getCachedZtree(List<BaseBean> dataList) throws com.sun.star.uno.Exception{
		DutyToolService dtService = new DutyToolService(dataList);
		CachedZtree cachedZtree = dtService.getCachedZtree();
		return cachedZtree;
	}
//	
//	@CacheEvict(value="base")
//	public void removeCachedZtreeTag(BaseBean baseBean)
//	{
//		CommonLogger.info("更新机构表后，清除机构树缓存成功");
//	}
	public void removeCachedZtreeTag(BaseBean baseBean) throws UnknownHostException
	{
		systemCommonService.refreshTree();
		CommonLogger.info("更新机构表后，清除机构树缓存成功");
	}
	
	/**
	 * 根据机构查询子机构列表（包括机构本身）
	 * @return
	 */
	public List<Org> getOrgAndChildren(Org org)
	{
		return orgDao.getOrgAndChildren(org);
	}
	
	/**
	 * 查询机构
	 * @return
	 */
	public Org getOrg(Org org){
		return orgDao.getOrg(org);
	}

	/**
	 * 修改机构信息
	 * @param org
	 * @return
	 */
	public boolean updateOrg(Org org){
		return orgDao.updateOrg(org) != 0 ? true:false;
	}
	
	/**
	 * 删除机构信息
	 * @param org
	 * @return
	 */
	public boolean deleteOrg(Org org){
		return orgDao.deleteOrg(org) != 0 ? true:false;
	}
	
	/**
	 * 新增机构信息
	 * @param org
	 * @return
	 */
	public boolean addOrg(Org org){
		return orgDao.addOrg(org) != 0 ? true:false;
	}
	
	/**
	 * 拖拽修改机构信息
	 * @param org
	 * @return
	 */
	public boolean dragUpdateOrg(Node node){
		//1.获取目标节点信息
		Org targetOrg = new Org();
		targetOrg.setDeptId(node.getTargetId());
		targetOrg = orgDao.getOrg(targetOrg);
		
		//2.获取更改节点的列表
		Org newOrg = new Org();
		newOrg.setDeptId(node.getId());
		List<Org> list = getOrgAndChildren(newOrg);
		
		int changeLevel = 0;
		for(int i=0; i<list.size(); i++){
			Org updateOrg = list.get(i);
			if(i == 0){
				int oldLevel = Integer.parseInt(updateOrg.getDeptLevel());
				if(node.getMoveType().equals("next"))
				{
					updateOrg.setUpDeptId(targetOrg.getUpDeptId());//目标的父节点
					updateOrg.setDeptLevel(targetOrg.getDeptLevel());//目标的层级
					changeLevel = Integer.parseInt(updateOrg.getDeptLevel()) - oldLevel;
				}
				else if(node.getMoveType().equals("inner"))
				{
					updateOrg.setUpDeptId(targetOrg.getDeptId());//目标节点
					updateOrg.setDeptLevel(Integer.parseInt(targetOrg.getDeptLevel())+1+"");//目标节点+1
					changeLevel = Integer.parseInt(updateOrg.getDeptLevel()) - oldLevel;
				}
			}else{
				updateOrg.setDeptLevel((Integer.parseInt(updateOrg.getDeptLevel()) + changeLevel)+"");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgList", list);
		int updateCnt = orgDao.updateDragOrg(map);
		return updateCnt != 0 ? true:false;
	}
	
	/**
	 * 启用/禁用机构信息
	 * @param org
	 * @return
	 */
	public boolean isViladOrg(Org org){
		int updateCnt = orgDao.isValidOrg(org);
		return updateCnt != 0 ? true:false;
	}

	public void updateInteAddress(String myAddress) {
		orgDao.updateInteAddress(myAddress);
		
	}

	public void insertInteAddress() {
		orgDao.insertInteAddress();
		
	}

	public String getOrg1Code(String rootId) {
		return orgDao.getOrg1Code(rootId);
	}
}
