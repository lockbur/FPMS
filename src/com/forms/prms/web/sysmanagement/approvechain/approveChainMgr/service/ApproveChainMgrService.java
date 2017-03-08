package com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.b;

import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.contract.deliver.dao.DeliverDAO;
import com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.dao.ApproveChainMgrDao;
import com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.domain.ApproveChainMgrBean;

@Service
public class ApproveChainMgrService {
	@Autowired
	private ApproveChainMgrDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	@Autowired
	private DeliverDAO deliverDAO;
	/**
	 * 省行维护 已维护
	 * @param bean
	 * @param string
	 * @return
	 */
	public List<ApproveChainMgrBean> specHaveWhList(ApproveChainMgrBean bean,
			String pageKey) {
		CommonLogger.info("省行物料审批链已维护查询列表,ApproveChainMgrService,specHaveWhList");
		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao,pageKey);
		return pagedao.specHaveWhList(bean);
	}
	/**
	 * 省行未维护 责任中心 省行统购
	 * @param bean
	 * @param string
	 * @return
	 */
	public List<ApproveChainMgrBean> specNoWhListTg(ApproveChainMgrBean bean,
			String pageKey) {
		CommonLogger.info("省行未维护中责任中心列表,ApproveChainMgrService,specNoWhListTg");
		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao,pageKey);
		return pagedao.specNoWhDutyList(bean);
	}
	/**
	 * 统购省 行未维护 明细页面
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> specNoWhMatrs(ApproveChainMgrBean bean) {
		CommonLogger.info("省行统购审批链未维护中责任中心"+bean.getDutyCode()+"可维护的物料,ApproveChainMgrService,specNoWhMatrs");
		List<ApproveChainMgrBean> approveChainBeans = dao.specNoWhMatrs(bean);
		return    this.buildMatrs(approveChainBeans);
	}
	/**
	 * 省行维护 未维护 专项
	 * @param bean
	 * @param string
	 * @return
	 */
	public List<Map<String, Object>> specNoWhList(ApproveChainMgrBean bean,
			String pageKey) {
		CommonLogger.info("省行专项审批链未维护页面,ApproveChainMgrService,specNoWhList");
		List<ApproveChainMgrBean> approveChainBeans = dao.specNoWhList(bean);
		return this.buildMatrs(approveChainBeans);
	}
	/**
	 * 省行维护审批链新增
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void specAdd(ApproveChainMgrBean bean) {//
		CommonLogger.info("省行维护审批链新增，省行("+bean.getOrg1Code()+"),ApproveChainMgrService,specAdd");
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		String [] matrs =bean.getMatrs();
		if (null != matrs && matrs.length>0) {
			List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
			int index =1;
			for(int i=0;i<matrs.length;i++){
				index  = index + 1;
				String[] mont_code = matrs[i].split("_");
				String montCode = mont_code[0];
				String matrCode = mont_code[1];
				Map<String, String> map = new HashMap<String, String>();
				map.put("montCodeMap", montCode);
				map.put("matrCodeMap", matrCode);
				list2.add(map);
				if(index % 500 == 0){
					//防止数量太多 每满500条提交一次
					bean.setMontMatrList(list2);
					dao.specAdd(bean);
					list2 = new ArrayList<Map<String,String>>();
				}
			}
			if (list2 != null && list2.size()>0) {
				bean.setMontMatrList(list2);
				dao.specAdd(bean);
			}
		}
	}
	/**
	 * 省行审批链编辑页
	 * @param bean
	 * @return
	 */
	public ApproveChainMgrBean specPreEdit(ApproveChainMgrBean bean) {
		if ("11".equals(bean.getAprvType())) {
			CommonLogger.info("审批链编辑页面,主键信息(一级行"+bean.getOrg1Code()+",监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"),ApproveChainMgrService,specPreEdit");
		}else {
			CommonLogger.info("审批链编辑页面,主键信息(责任中心"+bean.getFeeCode()+",监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"),ApproveChainMgrService,specPreEdit");
		}
		return dao.specPreEdit(bean);
	}
	/**
	 * 省行审批链编辑
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void specEdit(ApproveChainMgrBean bean) {
		// TODO Auto-generated method stub;
		bean.setIsSigle("1");
		if(!bean.getMatrAuditDept().equals(bean.getUpdateMatrAuditDept())){
			//归口部门变化了 得修改首页提示信息
			dao.updateSysWarnC1(bean);
		};
		dao.specEdit(bean);
		//重新计算数量
		deliverDAO.callWarnCount(bean.getMatrAuditDept());
		deliverDAO.callWarnCount(bean.getUpdateMatrAuditDept());
	}
	/**
	 * 解除审批链
	 * @param bean
	 * @return
	 */
	public boolean specDel(ApproveChainMgrBean bean) {
		if ("11".equals(bean.getAprvType())) {
			CommonLogger.info("省行专项审批链编辑执行,主键信息(一级行"+bean.getOrg1Code()+",监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"),ApproveChainMgrService,specPreEdit");
		}else {
			CommonLogger.info("省行统购审批链编辑执行,主键信息(责任中心"+bean.getFeeCode()+",监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"),ApproveChainMgrService,specPreEdit");
		}
		return dao.specDel(bean)>0;
	}
	/**
	 * 省行机构撤并引起的审批链变化
	 * @param bean
	 * @param string 
	 * @return
	 */
	public List<ApproveChainMgrBean> getAprvChange(ApproveChainMgrBean bean, String pageKey) {
		if ("01".equals(bean.getOrgType())) {
			CommonLogger.info("省行维护审批链待修改页面,ApproveChainMgrService,getAprvChange");
		}else {
			CommonLogger.info("分行维护审批链待修改页面,ApproveChainMgrService,getAprvChange");
		}
		
		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao,pageKey);
		return pagedao.getAprvChange(bean);
	}
	/**
	 * 省行批量维护责任中心
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> getDutyByOrg1ForSpec(ApproveChainMgrBean bean) {
		CommonLogger.info("省行统购审批链批量维护页面,ApproveChainMgrService,getDutyByOrg1ForSpec");
		List<ApproveChainMgrBean> dutyList = dao.getDutyByOrg1ForSpec(bean);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (null != dutyList && dutyList.size()>0) {
			List<String> containOrg2 = new ArrayList<String>();
			for(int i=0;i<dutyList.size();i++){
				if (containOrg2.contains(dutyList.get(i).getOrg2Code())) {
					//这个org2Code已经循环一次了
					continue;
				}
				containOrg2.add(dutyList.get(i).getOrg2Code());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("org2Code", dutyList.get(i).getOrg2Code());
				map.put("org2Name", dutyList.get(i).getOrg2Name());
				map.put("lvl", dutyList.get(i).getLvl());
				List<Map<String,String>> detailList = new ArrayList<Map<String,String>>();
				for(int j=0;j<dutyList.size();j++){
					if (dutyList.get(i).getOrg2Code().equals(dutyList.get(j).getOrg2Code())) {
						Map<String, String> map2 = new HashMap<String, String>();
						map2.put("dutyCode", dutyList.get(j).getDutyCode());
						map2.put("dutyName", dutyList.get(j).getDutyName());
						map2.put("noUpCount",dutyList.get(j).getNoUpCount());
						detailList.add(map2);
					}
				}
				map.put("dutyList", detailList);
				map.put("org2DutyCount",detailList.size());
				list.add(map);
			}
		}
		return list;
	}
	//=======================================分行====================================
	/**
	 * 分行已维护的
	 * @param bean
	 * @param string
	 * @return
	 */
	public List<ApproveChainMgrBean> noSpecHaveWhList(ApproveChainMgrBean bean,
			String pageKey) {
		CommonLogger.info("分行物料审批链已维护查询列表,ApproveChainMgrService,noSpecHaveWhList");
		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao,pageKey);
		return pagedao.noSpecHaveWhList(bean);
	}
	/**
	 * 分行未维护
	 * @param bean
	 * @param string
	 * @return
	 */
	public List<ApproveChainMgrBean> noSpecNoWhList(ApproveChainMgrBean bean,
			String pageKey) {
		CommonLogger.info("分行物料审批链未维护查询列表,ApproveChainMgrService,noSpecNoWhList");
		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao,pageKey);
		return pagedao.noSpecNoWhList(bean);
	}
	
	/**
	 * 分行未维护 明细页面
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> noSpecNoWhMatrs(ApproveChainMgrBean bean) {
		CommonLogger.info("分行物料审批链责任中心"+bean.getDutyCode()+"可维护的物料信息,ApproveChainMgrService,noSpecNoWhMatrs");
		List<ApproveChainMgrBean> approveChainBeans = dao.noSpecNoWhMatrs(bean);
		return    this.buildMatrs(approveChainBeans);
	}
	/**
	 * 分行审批链新增
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void noSpecAdd(ApproveChainMgrBean bean) {
		CommonLogger.info("分行维护审批链新增，),ApproveChainMgrService,specAdd");
		String [] matrs =bean.getMatrs();
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		if (null != matrs && matrs.length>0) {
			List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
			
			int index = 1;
			for(int i=0;i<matrs.length;i++){
				index = index + 1;
				String[] mont_code = matrs[i].split("_");
				String montCode = mont_code[0];
				String matrCode = mont_code[1];
				Map<String, String> map = new HashMap<String, String>();
				map.put("montCodeMap", montCode);
				map.put("matrCodeMap", matrCode);
				list2.add(map);
				if (index % 500 == 0) {
					bean.setMontMatrList(list2);
					dao.noSpecAdd(bean);
					list2 = new ArrayList<Map<String,String>>();
				}
			}
			if (null != list2 && list2.size()>0){
				bean.setMontMatrList(list2);
				dao.noSpecAdd(bean);
			}
			
		}
	}
	/**
	 * 分行进入审批链维护界面
	 * @param bean
	 * @return
	 */
	public ApproveChainMgrBean noSpecPreEdit(ApproveChainMgrBean bean) {
		// TODO Auto-generated method stub
		CommonLogger.info("分行审批链编辑界面,主键信息（费用承担部门"+bean.getFeeCode()+"，监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"）ApproveChainMgrService,noSpecPreEdit");
		return dao.noSpecPreEdit(bean);
	}
	/**
	 * 分行审批链编辑
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void noSpecEdit(ApproveChainMgrBean bean) {
		// TODO Auto-generated method stub
		CommonLogger.info("分行审批链编辑执行,主键信息（费用承担部门"+bean.getFeeCode()+"，监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"）ApproveChainMgrService,noSpecPreEdit");
		
		bean.setIsSigle("1");
		if(!bean.getMatrAuditDept().equals(bean.getUpdateMatrAuditDept())){
			//归口部门变化了 得修改首页提示信息
			dao.updateSysWarnC1(bean);
		};
		dao.noSpecEdit(bean);
		//重新计算数量
		deliverDAO.callWarnCount(bean.getMatrAuditDept());
		deliverDAO.callWarnCount(bean.getUpdateMatrAuditDept());
		
	}
	/**
	 * 分行审批链解除
	 * @param bean
	 * @return
	 */
	public boolean noSpecDel(ApproveChainMgrBean bean) {
		CommonLogger.info("分行审批链删除,主键信息（费用承担部门"+bean.getFeeCode()+"，监控指标"+bean.getMontCode()+",物料"+bean.getMatrCode()+"）ApproveChainMgrService,noSpecPreEdit");
		return dao.noSpecDel(bean)>0;
	}
//	/**
//	 * 分行机构撤并引起的审批链变化
//	 * @param bean
//	 * @param string 
//	 * @return
//	 */
//	public List<ApproveChainMgrBean> getNoSpecChange(ApproveChainMgrBean bean, String pageKey) {
//		CommonLogger.info("分行审批链待修改页面,ApproveChainMgrService,getNoSpecChange");
//		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao,pageKey);
//		return pagedao.getNoSpecChange(bean);
//	}
	/**
	 * 得到撤并的责任中心
	 * @param bean
	 * @return
	 */
//	public List<ApproveChainMgrBean> getChangeDutyList(ApproveChainMgrBean bean) {
//		CommonLogger.info("得到撤并的责任中心,ApproveChainMgrService,getChangeDutyList");
//		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
//		ApproveChainMgrDao pagedao = PageUtils.getPageDao(dao);
//		PageUtils.setPageSize(10);
//		if (!"11".equals(bean.getAprvType())) {
//			//分行维护的
//			return pagedao.getChangeDutyListForNospec(bean);
//		}else {
//			return pagedao.getChangeDutyListForSpec(bean);
//		}
//		
//	}
	/**
	 * 分行批量维护查询所有责任中心
	 * @param bean
	 * @return
	 */
	public List<ApproveChainMgrBean> getDutyByOrg2(ApproveChainMgrBean bean) {
		CommonLogger.info("分行批量维护 的所有责任中心,ApproveChainMgrService,getDutyByOrg2");
		return dao.getDutyByOrg2(bean);
	}
	/**
	 * 得到多个责任中心的共同可维护的物料
	 * @param bean
	 * @return
	 */
	public Map selecstPublicMatrs(ApproveChainMgrBean bean) {
		CommonLogger.info("分行审批链得到多个共同的可维护物料,ApproveChainMgrService,selecstPublicMatrs");
		String [] dutyArray = bean.getDutyArray().split(",");
		if (null != dutyArray && dutyArray.length>0) {
			bean.setDutyList(Arrays.asList(dutyArray));
		}
		if (Tool.DATE.getDate().substring(0,4).equals(bean.getDataYear())) {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else {
			//同年
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		List<ApproveChainMgrBean> list =  dao.selecstPublicMatrs(bean);
		List<String> containMonts = new ArrayList<String>();
		if (null != list && list.size()>0) {
			for(int i=0 ; i < list.size() ; i++){
				if (containMonts.contains(list.get(i).getMontCode())) {
					continue;
				}
				containMonts.add(list.get(i).getMontCode());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("monts", containMonts);
		map.put("matrs", list);
		return map;
	}
	/**
	 * 未维护明细页面数据组装
	 * @param approveChainBeans
	 * @return
	 */
	public List<Map<String, Object>> buildMatrs(List<ApproveChainMgrBean> approveChainBeans){
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		if (null!=approveChainBeans && approveChainBeans.size()>0) {
			List<String> containMonts = new ArrayList<String>();
			for(int i=0;i<approveChainBeans.size();i++){
				if (containMonts.contains(approveChainBeans.get(i).getMontCode())) {
					continue;
				}
				containMonts.add(approveChainBeans.get(i).getMontCode());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("montCode", approveChainBeans.get(i).getMontCode());
				map.put("montName", approveChainBeans.get(i).getMontName());
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				for(int j=0;j<approveChainBeans.size();j++){
					if (approveChainBeans.get(i).getMontCode().equals(approveChainBeans.get(j).getMontCode())) {
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("matrCode",approveChainBeans.get(j).getMatrCode() );
						map2.put("matrName",approveChainBeans.get(j).getMatrName());
						list.add(map2);
					}
				}
				map.put("matrs", list);
				map.put("matrCount", list.size());
				detailList.add(map);
			}
		}
		return detailList;
	}
	/**
	 * 分行批量维护
	 * 防止循环太多导致数据库失去连接
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean noSpecBatchEditExecute(ApproveChainMgrBean chain) {
		chain.setInstOper(WebHelp.getLoginUser().getUserId());
		boolean flag = true;
		CommonLogger.info("批量维护执行,ApproveChainMgrService,noSpecBatchEditExecute");
		
		if (!Tool.CHECK.isEmpty(chain.getDutys())) {
			chain.setDutyList(Arrays.asList(chain.getDutys()));
			if(null !=chain.getDutys() && chain.getDutys().length>0){
				for (int i = 0; i < chain.getDutys().length; i++) {
					chain.setDutyCode(chain.getDutys()[i]);
					if (null != chain.getMatrA() && chain.getMatrA().length>0) {
						List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
						for(int j=0;j<chain.getMatrA().length;j++){
							String[] mont_code = chain.getMatrA()[j].split("_");
							String montCode = mont_code[0];
							String matrCode = mont_code[1];
							Map<String, String> map = new HashMap<String, String>();
							map.put("montCodeMap", montCode);
							map.put("matrCodeMap", matrCode);
							list2.add(map);
							if (j>0 && j%500 ==0) {
								chain.setMontMatrList(list2);
								dao.noSpecBatchEditExecute(chain);
								list2 = new ArrayList<Map<String,String>>();
							}
						}
						if (null != list2 && list2.size()>0) {
							chain.setMontMatrList(list2);
							dao.noSpecBatchEditExecute(chain);
						}
						
					}
				}
				
			}
		}
		return flag;
	}
//	@Transactional(rollbackFor = Exception.class)
//	public boolean noSpecBatchEditExecute(ApproveChainMgrBean chain) {
//		chain.setInstOper(WebHelp.getLoginUser().getUserId());
//		boolean flag = true;
//		CommonLogger.info("批量维护执行,ApproveChainMgrService,noSpecBatchEditExecute");
//		if (null != chain.getMatrA() && chain.getMatrA().length>0) {
//			List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
//			for(int i=0;i<chain.getMatrA().length;i++){
//				String[] mont_code = chain.getMatrA()[i].split("_");
//				String montCode = mont_code[0];
//				String matrCode = mont_code[1];
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("montCodeMap", montCode);
//				map.put("matrCodeMap", matrCode);
//				list2.add(map);
//			}
//			chain.setMontMatrList(list2);
//		}
//		if (!Tool.CHECK.isEmpty(chain.getDutys())) {
//			chain.setDutyList(Arrays.asList(chain.getDutys()));
//			if(null !=chain.getDutys() && chain.getDutys().length>0){
//				for (int i = 0; i < chain.getDutys().length; i++) {
//					chain.setDutyCode(chain.getDutys()[i]);
//					int affect = dao.noSpecBatchEditExecute(chain);
//					if (affect<=0) {
//						flag = false;
//					}
//				}
//				
//			}
//		}
//		return flag;
//	}
	/**
	 * 费用承担部门
	 * @param bean
	 * @return
	 */
	public boolean updateFee(ApproveChainMgrBean bean) {
		if ("01".equals(bean.getAprvType())) {
			CommonLogger.info("省行费用承担部门批量修改，原费用承担部门:"+bean.getFeeCode()+",现费用承担部门:"+bean.getUpdateFeeCode()+",ApproveChainMgrService,updateFee");
		}else {
			CommonLogger.info("分行费用承担部门批量修改，原费用承担部门:"+bean.getFeeCode()+",现费用承担部门:"+bean.getUpdateFeeCode()+",ApproveChainMgrService,updateFee");
		}
		
		return dao.updateFee(bean);
	}
	/**
	 * 采购部门
	 * @param bean
	 * @return
	 */
	public boolean updateBuy(ApproveChainMgrBean bean) {
		if ("01".equals(bean.getAprvType())) {
			CommonLogger.info("省行采购部门批量修改，原采购部门:"+bean.getMatrBuyDept()+",现采购部门:"+bean.getUpdateMatrBuyDept()+",ApproveChainMgrService,updateBuy");
		}else {
			CommonLogger.info("分行采购部门批量修改，原采购部门:"+bean.getMatrBuyDept()+",现采购部门:"+bean.getUpdateMatrBuyDept()+",ApproveChainMgrService,updateBuy");
		}
		
		return dao.updateBuy(bean);
	}
	/**
	 * 物料归口部门
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateAudit(ApproveChainMgrBean bean) {
		if ("01".equals(bean.getAprvType())) {
			CommonLogger.info("省行物料归口部门批量修改，原物料归口部门:"+bean.getMatrAuditDept()+",现物料归口部门:"+bean.getUpdateMatrAuditDept()+",ApproveChainMgrService,updateAudit");
		}else {
			CommonLogger.info("分行物料归口部门批量修改，原物料归口部门:"+bean.getMatrAuditDept()+",现物料归口部门:"+bean.getUpdateMatrAuditDept()+",ApproveChainMgrService,updateAudit");
		}
		dao.updateSysWarnC1(bean);
		dao.updateAudit(bean);
		
		//修改数量
		deliverDAO.callWarnCount(bean.getMatrAuditDept());
		deliverDAO.callWarnCount(bean.getUpdateMatrAuditDept());
	}
	
	/**
	 * 物料审批链查询
	 * @param bean
	 * @return
	 */
	public List<ApproveChainMgrBean> appList(ApproveChainMgrBean bean) {
		CommonLogger.info("审批链查询,ApproveChainMgrService,appList");
		ApproveChainMgrDao pageDao=PageUtils.getPageDao(dao);
		String year = Tool.DATE.getDateStrNO().substring(0,4);
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(year);
		}
		if (year.equals(bean.getDataYear())) {
			//查询当前年的 就在正式表里
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
		}else if (Integer.valueOf(year) > Integer.valueOf(bean.getDataYear())) {
			//查询历史记录
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_HIS");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_HIS");
		}else if (Integer.valueOf(year) < Integer.valueOf(bean.getDataYear())) {
			//查询FUT表
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
		}
		
		return pageDao.HisAppList(bean);
	}
	/**
	 * 审批链查询时弹出监控指标列表
	 * @param bean
	 * @return
	 */
	public List<ApproveChainMgrBean> getMont(ApproveChainMgrBean bean) {
		CommonLogger.info("审批链查询条件中的监控指标,ApproveChainMgrService,getMont");
		String org2Code=WebHelp.getLoginUser().getOrg2Code();
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		
		bean.setOrg2Code(org2Code);
		bean.setOrg1Code(org1Code);
		PageUtils.setPageSize(10);
		ApproveChainMgrDao pageDao=PageUtils.getPageDao(dao);
		return pageDao.getMont(bean);
	}
	public Object download(ApproveChainMgrBean bean) throws Exception {
		String aprvType="";
		if (bean.getAprvType().equals("01")) {
			aprvType="专项包审批链";
		}else if (bean.getAprvType().equals("02")) {
			aprvType="省行统购审批链";
		}else{
			aprvType="非省行统购非专项包审批链";
		}
		String dataYear=bean.getDataYear();
		String sourceFileName=dataYear+aprvType+"导出";
		createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("feeCode", bean.getFeeCode());
		map.put("montCode", bean.getMontCode());
		map.put("matrBuyDept", bean.getMatrBuyDept());
		map.put("matrAuditDept", bean.getMatrAuditDept());	
		map.put("decomposeOrg", bean.getDecomposeOrg());
		map.put("fincDeptS", bean.getFincDeptS());	
		map.put("fincDept2", bean.getFincDept2());	
		map.put("fincDept1", bean.getFincDept1());			
		map.put("dataYear", bean.getDataYear());
		map.put("matrCode", bean.getMatrCode());
		map.put("matrName",	bean.getMatrName());
		map.put("org1Code", bean.getOrg1Code());
		map.put("isProvinceBuy", bean.getIsProvinceBuy());	
		map.put("aprvType", bean.getAprvType());
		map.put("org21Code", bean.getOrg21Code());
		if (bean.getAprvType().equals("01")) {
			return exportDeal.execute(sourceFileName, "APRV_ZX_EXPORT", destFile , map); 
		}else {
			return exportDeal.execute(sourceFileName, "APRV_EXPORT", destFile , map); 
		}
		
	}
	 /**
     * 如果文件路径不存在，则创建文件全部路径
     * @param filePath
     */
	private void createFilePath(File file) {
    	if(!judgePlateFlag(file)){
    		file.mkdirs();
    	}
	}
	  /**
     * 判断文件路径或客户端系统盘符是否存在
     */
	private boolean judgePlateFlag(File file) {
    	if(file.exists()){
    		return true;
    	}else{
    		return false;
    	}
	}
	public static ApproveChainMgrService getInstance() {
		return SpringUtil.getBean(ApproveChainMgrService.class);
	}
	
	public List<ApproveChainMgrBean> aprvList(ApproveChainMgrBean bean) {
		String year = Tool.DATE.getDateStrNO().substring(0,4);
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(year);
		}
		if (year.equals(bean.getDataYear())) {
			//查询当前年的 就在正式表里或者是在FUT表里
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
			return dao.HisAppList(bean);
		}else if (Integer.valueOf(year) > Integer.valueOf(bean.getDataYear())) {
			//查询历史记录
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_HIS");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_HIS");
			return dao.HisAppList(bean);
		}else if (Integer.valueOf(year) < Integer.valueOf(bean.getDataYear())) {
			//查询FUT表
			bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
			bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
			return dao.futAppList(bean);
		}else {
			return null;
		}
		
	}
	/**
	 * 提交修改数据
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void selectSubmit(ApproveChainMgrBean bean) {
		String changeData = bean.getChangeData();
		if (!Tool.CHECK.isEmpty(changeData)) {
			String [] changeDatas = changeData.split(",");
			if (null != changeDatas && changeDatas.length>0) {
				for (int i = 0; i < changeDatas.length; i++) {
					
					String [] datas = changeDatas[i].split("-");
					if (null != datas && datas.length>0) {
						bean.setGroupId(datas[0]);
						bean.setMontCode(datas[1]);
						String aprvType = datas[2];
						String dataYear = datas[3];
						bean.setAprvType(aprvType);
						bean.setDataYear(dataYear);
						bean.setThisYear(Tool.DATE.getDateStrNO().substring(0,4));
						if ("11".equals(aprvType)) {
							//专项包
							if (Tool.DATE.getDateStrNO().subSequence(0, 4).equals(dataYear)) {
								bean.setSpecTableName("TB_APRV_CHAIN_SPEC");
								
							}else {
								bean.setSpecTableName("TB_APRV_CHAIN_SPEC_FUT");
							}
							dao.insertDataFromUpSpec(bean);
						}else {
							//非专项包
							if (Tool.DATE.getDateStrNO().subSequence(0, 4).equals(dataYear)) {
								bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC");
							}else {
								bean.setNoSpecTableName("TB_APRV_CHAIN_NOSPEC_FUT");
							}
							dao.insertDataFromUpNoSpec(bean);
						}
						
						dao.deleteDataUp(bean);
					}
				}
			}
		}
		
	}
	public ApproveChainMgrBean selectDetail(ApproveChainMgrBean bean) {
		return dao.selectDetail(bean);
	}
	/**
	 * 根据合并后的监控指标和物料找出哪些要合并
	 * @param bean
	 * @return
	 */
	public List<ApproveChainMgrBean> forUpdate(ApproveChainMgrBean bean) {
		return dao.forUpdate(bean);
	}
}
