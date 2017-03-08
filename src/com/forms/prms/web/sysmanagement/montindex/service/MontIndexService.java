package com.forms.prms.web.sysmanagement.montindex.service;

import java.io.File;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.sysmanagement.montindex.dao.MontIndexDao;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;
import com.sybase.jdbc3.tds.e;

@Service
public class MontIndexService {
	@Autowired
	private MontIndexDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service

	public List<MontIndexBean> shList(MontIndexBean bean) {
		CommonLogger.info("省行监控指标列表查询,MontIndexService,shList");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
		bean.setOrg21Code(org1Code);
		MontIndexDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.shList(bean);
	}

	public List<MontIndexBean> fhList(MontIndexBean bean) {
		CommonLogger.info("分行监控指标列表查询,MontIndexService,fhList");
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在的二级行
		bean.setOrg21Code(org2Code);
		MontIndexDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.fhList(bean);
	}

	public List<MontIndexBean> fyzcList(MontIndexBean bean) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
		bean.setOrg1Code(org1Code);
//		String projType;
//		try {
//			projType = java.net.URLDecoder.decode(bean.getProjType(), "UTF-8");
//			bean.setProjType(projType);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		List<MontIndexBean> dataNoUpMatrs = dao.fyzcList(bean);
//		List<String> pageNoUpMatrs = new ArrayList<String>();
//		if (!Tool.CHECK.isBlank(bean.getSelectNoUpMatrs())) {
//			String[] getNoUpMatrs = bean.getSelectNoUpMatrs().split(",");
//			if (null != getNoUpMatrs && getNoUpMatrs.length > 0) {
//				pageNoUpMatrs = Arrays.asList(getNoUpMatrs);
//			}
//		}
		List<String> list = new ArrayList<String>();
		if (!Tool.CHECK.isEmpty(bean.getSelectNoUpMatrs())) {
			int length =bean.getSelectNoUpMatrs().length();
			String matrs = bean.getSelectNoUpMatrs();
			for (int i = 0; i < (length/3); i++) {
				list.add(String.valueOf(matrs).substring(0, 3));
				matrs = matrs.substring(3); 
			}
		}
		if (null != dataNoUpMatrs && dataNoUpMatrs.size() > 0) {
			for (int i = 0; i < dataNoUpMatrs.size(); i++) {
				if (list.contains(dataNoUpMatrs.get(i).getSeq())) {
					dataNoUpMatrs.get(i).setIsChecked("1");
				}
			}
		}
		return dataNoUpMatrs;
	}
	public List<MontIndexBean> fyList(MontIndexBean bean) {
		String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在的二级行
		bean.setOrg21Code(org2Code);
		List<MontIndexBean> dataNoUpMatrs = dao.fyList(bean);
//		List<String> pageNoUpMatrs = new ArrayList<String>();
//		if (!Tool.CHECK.isBlank(bean.getSelectNoUpMatrs())) {
//			String[] getNoUpMatrs = bean.getSelectNoUpMatrs().split(",");
//			if (null != getNoUpMatrs && getNoUpMatrs.length > 0) {
//				pageNoUpMatrs = Arrays.asList(getNoUpMatrs);
//			}
//		}
		List<String> list = new ArrayList<String>();
		if (!Tool.CHECK.isEmpty(bean.getSelectNoUpMatrs())) {
			int length =bean.getSelectNoUpMatrs().length();
			String matrs = bean.getSelectNoUpMatrs();
			for (int i = 0; i < (length/3); i++) {
				list.add(String.valueOf(matrs).substring(0, 3));
				matrs = matrs.substring(3); 
			}
		}
		if (null != dataNoUpMatrs && dataNoUpMatrs.size() > 0) {
			for (int i = 0; i < dataNoUpMatrs.size(); i++) {
				if (list.contains(dataNoUpMatrs.get(i).getSeq())) {
					dataNoUpMatrs.get(i).setIsChecked("1");
				}
			}
		}
		return dataNoUpMatrs;
	}

	public List<MontIndexBean> zcList(MontIndexBean bean) {
		if ("12".equals(bean.getMontType())) {
			String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
			bean.setOrg21Code(org1Code);
		} else if ("21".endsWith(bean.getMontType())) {
			String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在的二级行
			bean.setOrg21Code(org2Code);
		}
		List<MontIndexBean> dataNoUpMatrs = dao.zcList(bean);
//		List<String> pageNoUpMatrs = new ArrayList<String>();
//		if (!Tool.CHECK.isBlank(bean.getSelectNoUpMatrs())) {
//			String[] getNoUpMatrs = bean.getSelectNoUpMatrs().split(",");
//			if (null != getNoUpMatrs && getNoUpMatrs.length > 0) {
//				pageNoUpMatrs = Arrays.asList(getNoUpMatrs);
//			}
//		}
		List<String> list = new ArrayList<String>();
		if (!Tool.CHECK.isEmpty(bean.getSelectNoUpMatrs())) {
			int length =bean.getSelectNoUpMatrs().length();
			String matrs = bean.getSelectNoUpMatrs();
			for (int i = 0; i < (length/3); i++) {
				list.add(String.valueOf(matrs).substring(0, 3));
				matrs = matrs.substring(3); 
			}
		}
		if (null != dataNoUpMatrs && dataNoUpMatrs.size() > 0) {
			for (int i = 0; i < dataNoUpMatrs.size(); i++) {
				if (list.contains(dataNoUpMatrs.get(i).getSeq())) {
					dataNoUpMatrs.get(i).setIsChecked("1");
				}
			}
		}
		return dataNoUpMatrs;
	}

	/**
	 * 根据监控指标名称查询对象
	 * 
	 * @param providerName
	 * @return
	 */
	public String checkMont(MontIndexBean bean) {
		return dao.checkMont(bean);
	}

	/**
	 * 查询监控指标这个类型的个数
	 * 
	 * @param bean
	 * @return
	 */
	public String selectCount(MontIndexBean bean) {
		return dao.selectCount(bean);
	}
	public void checkExecute(MontIndexBean bean) throws Exception{
		//校验 是否有正在导入中批次
		int count = dao.selectImpBatch(bean);
		if (count>0) {
			//有没走完的流程，不能新增
	//					throw Exception("该类型的监控指标有还没有走完流程的导入数据，请在导入完成后再新增。");
			CommonLogger.error("该类型的监控指标有还没有走完流程的导入数据，请在导入完成后再新增。MontIndexService,addMont");
			throw new Exception("该类型的监控指标有还没有走完流程的导入数据，请在导入完成后再新增。");
		}
	}

	/**
	 * 添加监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addMont(MontIndexBean bean) throws Exception {
		CommonLogger.info("监控指标新增,监控指标代码:" + bean.getMontCode() + ",监控指标名称:" + bean.getMontName() + ",监控指标类型:"
				+ bean.getMontType() + ",二级行或一级行:" + bean.getOrg21Code() + "MontIndexService,addMont");
		
		List<String> matrLstList = new ArrayList<String>();
		if (null != bean.getMatrs() && bean.getMatrs().length > 0) {
			matrLstList.addAll(Arrays.asList(bean.getMatrs()));
		}
		if (null != bean.getNoUpMatrs() && bean.getNoUpMatrs().length > 0) {
			matrLstList.addAll(Arrays.asList(bean.getNoUpMatrs()));
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (null != matrLstList && matrLstList.size()>0) {
			Map<String,Object> map = null;
			for (int i = 0; i < matrLstList.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("matrCode", matrLstList.get(i));
				map.put("isValid", "1");
				list.add(map);
				
			}
		}
		bean.setList(list);
		// 增加监控指标
		int affect2 = dao.addMont(bean);
		// 增加监控指标和物料对应关系
		CommonLogger.info("监控指标新增时增加和物料的对应关系,监控指标:" + bean.getMontCode() + "MontIndexService,addMont");
		String instOper = WebHelp.getLoginUser().getUserId();
		bean.setInstOper(instOper);
		bean.setMontCodeHis(bean.getMontCode()+",");
		dao.addMontMatr(bean);
	}

	/**
	 * 根据监控指标得到监控指标和物料的对应关系
	 * 
	 * @param bean
	 * @return
	 */
	public List<MontIndexBean> getMontMatrByMontCode(MontIndexBean bean) {
		CommonLogger.info("根据监控指标查询和物料对应关系,MontIndexService,getMontMatrByMontCode");
		return dao.getMontMatrByMontCode(bean);
	}

	/**
	 * 根据监控指标得到详细信息
	 * 
	 * @param bean
	 * @return
	 */
	public MontIndexBean getMontInfoByMontCode(MontIndexBean bean) {
		CommonLogger.info("根据监控指标查询详细信息,MontIndexService,getMontInfoByMontCode");
		return dao.getMontInfoByMontCode(bean);
	}

	/**
	 * 查询所有已维护物料
	 * 
	 * @return
	 */
	public List<MontIndexBean> selectHaveMontMatrs(MontIndexBean bean) {

		if ("11".endsWith(bean.getMontType()) || "12".endsWith(bean.getMontType())) {
			String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在的一级行
			bean.setOrg21Code(org1Code);
		} else {
			String org2Code = WebHelp.getLoginUser().getOrg2Code();// 得到登录人所在的二级行
			bean.setOrg21Code(org2Code);
		}
//		List<String> selectMatrs = dao.getSelectMatrs(bean.getMontCode());		
//		CommonLogger.info("查询是所有已维护的物料,MontIndexService,selectHaveMontMatrs");
//		List<String> pageHaveUpMatrs = new ArrayList<String>();
//		if (null != selectMatrs && selectMatrs.size()>0) {
//			if (null != matrs && matrs.length > 0) {
//				pageHaveUpMatrs = Arrays.asList(matrs);
//				bean.setMatrLst(Arrays.asList(matrs));
//			}
//		}
//		bean.setMatrLst(Arrays.asList(matrs));
//		// 数据库中已维护的
//		List<MontIndexBean> dataHaveUpMatrs = dao.getMontMatrByMontCode(bean);
//		
//		
//		//将 数据库已维护 的 在页面传过来的集合里面进行标记
//		if (null != dataHaveUpMatrs && dataHaveUpMatrs.size() > 0) {
//			for (int i = 0; i < dataHaveUpMatrs.size(); i++) {
//				for (int j = 0; j < pageHaveUpMatrs.size(); j++) {
//					if (null != dataHaveUpMatrs.get(i) && !Tool.CHECK.isEmpty(dataHaveUpMatrs.get(i).getMatrCode()) && !Tool.CHECK.isEmpty(pageHaveUpMatrs.get(j))) {
//						if (dataHaveUpMatrs.get(i).getMatrCode().equals(pageHaveUpMatrs.get(j))) {
//							dataHaveUpMatrs.get(i).setIsChecked("1");
//						}
//					}
//					
//				}
//			}
//		}
		List<MontIndexBean> dataHaveUpMatrs = dao.getMontMatrByMontCode(bean);
		if (null != dataHaveUpMatrs && dataHaveUpMatrs.size() > 0) {
			for (int i = 0; i < dataHaveUpMatrs.size(); i++) {
				dataHaveUpMatrs.get(i).setIsChecked("1");
			}
		}
		return dataHaveUpMatrs;
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delMontMatr(MontIndexBean bean) {

		// 删除监控指标物料表
		CommonLogger.info("删除监控指标里面的物料,主键信息(二级行或一级行:" + bean.getOrg21Code() + ",监控指标:" + bean.getMontCode() + ",物料:"
				+ bean.getMontCode() + ",MontIndexService,delMontMatr");
		dao.delMatr(bean);
		// 如果为专向包则删除专向包的审批链
		if ("11".equals(bean.getMontType())) {
			dao.delApproveChainSpecMatr(bean);
			// 删除专向包审批链对应的数据
			CommonLogger.info("删除监控指标某一个物料在专向包中的审批链,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
					+ ",MontIndexService,delMontMatr");
		} else {
			// 删除非专向包审批链对应的数据
			dao.delApproveChainNoSpecMatr(bean);
			CommonLogger.info("删除监控指标某一个物料在非专向包中的审批链,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
					+ ",MontIndexService,delMontMatr");
		}
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean del(MontIndexBean bean) {
		// 删除监控指标表
		CommonLogger.info("删除监控指标信息,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
				+ ",MontIndexService,del");
		int affect = dao.delMont(bean);
		// 删除监控指标和物料对应关系
		CommonLogger.info("删除监控指标和物料对应关心信息,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
				+ ",MontIndexService,del");
		int affect2 = dao.delMontMatr(bean);
		// 如果为专向包则删除专向包的审批链
		if ("11".equals(bean.getMontType())) {
			dao.delApproveChainSpec(bean);
			// 删除专向包审批链对应的数据
			CommonLogger.info("删除监控指标在专向包中的审批链,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
					+ ",MontIndexService,del");
		} else {
			// 删除非专向包审批链对应的数据
			dao.delApproveChainNoSpec(bean);
			CommonLogger.info("删除监控指标在非专向包中的审批链,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
					+ ",MontIndexService,del");
		}
		if (affect > 0 && affect2 > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改物料监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void editMartMont(MontIndexBean bean) {
//		boolean isSuccess = false;
//		// 修改物料和监控指标关系
//		List<String> matrLstList = new ArrayList<String>();
//		if (null != bean.getMatrs() && bean.getMatrs().length > 0) {
//			matrLstList.addAll(Arrays.asList(bean.getMatrs()));
//		}
//		if (null != bean.getNoUpMatrs() && bean.getNoUpMatrs().length > 0) {
//			matrLstList.addAll(Arrays.asList(bean.getNoUpMatrs()));
//		}
//		bean.setMatrLst(matrLstList);
//		// 得到删除掉的物料编码用最初的减去还剩的已维护的物料
//		List<String> fristMatrLstList = new ArrayList<String>();
//		if (null != bean.getFristMatrs() && bean.getFristMatrs().length > 0) {
//			fristMatrLstList.addAll(Arrays.asList(bean.getFristMatrs()));
//		}
//		if (null != bean.getMatrs() && bean.getMatrs().length > 0) {
//			fristMatrLstList.removeAll(Arrays.asList(bean.getMatrs()));
//		}
//		bean.setFristMatrsList(fristMatrLstList);
//		// 删除不存在的已维护物料
//		if (bean.getFristMatrsList().size() > 0) {
//			dao.delNotExistMatr(bean);
//			CommonLogger.info("删除监控指标对应的物料关系,监控指标:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
//					+ ",MontIndexService,editMartMont");
//			// 删除不存在的已维护物料对应的物料审批链
//			// 如果为专向包则删除专向包的审批链
//			if ("11".equals(bean.getMontType())) {
//				dao.delNotExistApproveChainSpec(bean);
//				// 删除专向包审批链对应的数据
//				CommonLogger.info("删除监控指标在专向包中的审批链,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
//						+ ",MontIndexService,del");
//			} else {
//				// 删除非专向包审批链对应的数据
//				dao.delNotExistApproveChainNoSpec(bean);
//				CommonLogger.info("删除监控指标在非专向包中的审批链,监控指标代码:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
//						+ ",MontIndexService,del");
//			}
//		}
//		/*
//		 * // 删除之前先检查是否存在该监控指标 MontIndexBean checkBean =
//		 * dao.selectCheckBean(bean); int affect3 = 0; if (checkBean != null) {
//		 * // 删除监控指标和物料关系
//		 * CommonLogger.info("修改物料和监控指标关系的时候删除以前监控指标对应的物料的日志,监控指标:" +
//		 * bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code() +
//		 * ",MontIndexService,editMartMont"); affect3 = dao.delMontMatr(bean); }
//		 */
//		// 增加监控指标和物料关系
//		CommonLogger.info("修改监控指标时增加监控指标和物料关系,监控指标:" + bean.getMontCode() + ",二级行或一级行:" + bean.getOrg21Code()
//				+ ",MontIndexService,editMartMont");
//		String instOper = WebHelp.getLoginUser().getUserId();
//		bean.setInstOper(instOper);
//		bean.setMontCodeHis(bean.getMontCode()+",");
//		dao.editAddMontMatr(bean);
//		// 修改监控指标
//		int affect2 = dao.editMont(bean);
//		if (affect2 > 0) {
//			isSuccess = true;
//		} else {
//			isSuccess = false;
//		}
//		return isSuccess;
		CommonLogger.info("监控指标维护，指标代码:"+bean.getMontCode()+",MontIndexService,editMartMont");
		//先删除监控指标下的所有物料，
		dao.deleteByMontCode(bean);
		List<String> matrLstList = new ArrayList<String>();
		if (null != bean.getMatrs() && bean.getMatrs().length > 0) {
			matrLstList.addAll(Arrays.asList(bean.getMatrs()));
		}
		if (null != bean.getNoUpMatrs() && bean.getNoUpMatrs().length > 0) {
			matrLstList.addAll(Arrays.asList(bean.getNoUpMatrs()));
		}
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (null != matrLstList && matrLstList.size()>0) {
			Map<String,Object> map = null;
			for (int i = 0; i < matrLstList.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("matrCode", matrLstList.get(i));
				map.put("isValid", bean.getIsValids()[i]);
				list.add(map);
				
			}
		}
		bean.setList(list);
		//删除审批链
		dao.delAprvChain(bean);
		dao.addMontMatr(bean);
		dao.updateMont(bean);
		
	}

	public List<MontIndexBean> montList(MontIndexBean bean) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();// 得到登录人所在一级行
		bean.setOrg1Code(org1Code);
		if (bean.getOrg2Code()==null||bean.getOrg2Code().equals("")) {//如果二级行为空，则赋值用户所在二级行
			bean.setUserOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		}
		Calendar a = Calendar.getInstance();
		// 得到系统当前的年份
		String nowYear = Integer.toString(a.get(Calendar.YEAR));
		String year=bean.getDataYear();
		MontIndexDao pageDao = PageUtils.getPageDao(dao);
		// 如果选择的年份为当前年份或者没有选择年份则去mont表中查询数据
		if (year == null || year.equals("") || nowYear.equals(year)) {
			return pageDao.montList(bean);
		}
		// 去历史表中去查询数据
		else if (Integer.parseInt(year)>Integer.parseInt(nowYear)) {
			return pageDao.montFutList(bean);
		}else if (Integer.parseInt(year)<Integer.parseInt(nowYear)) {
			return pageDao.montHisList(bean);
		}
		return null;
	}

	public List<MontIndexBean> montHisList(MontIndexBean bean) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String org2Code = WebHelp.getLoginUser().getOrg2Code();
		bean.setOrg1Code(org1Code);
		bean.setOrg2Code(org2Code);
		MontIndexDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.montHisList(bean);
	}

	public List<MontIndexBean> selectProjType(MontIndexBean bean) {
		MontIndexDao pageDao = PageUtils.getPageDao(dao);
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		return pageDao.selectProjType(bean);
	}

	/**
	 * 根据监控指标代码检测是否存在于合同设备表中
	 * 
	 * @param montCode
	 * @return
	 */
	public Map<String,Object> checkMontCode(String montCode,String matrCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		String mont_code = dao.checkMontCode(montCode,matrCode);
		map.put("flag", true);
		if (!Tool.CHECK.isEmpty(mont_code)) {
			map.put("flag", false);
			if (!Tool.CHECK.isEmpty(matrCode)) {
				map.put("msg", "该监控指标和物料在合同中已经使用，不能删除");
			}else {
				map.put("msg", "该监控指标在合同中已经使用，不能删除");
			}
			
		}else {
			mont_code = dao.checkMontIsBud(montCode,matrCode);
			if (!Tool.CHECK.isEmpty(mont_code)) {
				map.put("flag", false);
				if (!Tool.CHECK.isEmpty(matrCode)) {
					map.put("msg", "该监控指标和物料在预算中已经使用，不能删除");
				}else {
					map.put("msg", "该监控指标在预算中已经使用，不能删除");
				}
			}
		}
		
		return map;
	}
	public List<MontIndexBean> allMontList(MontIndexBean bean) {
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		if (bean.getOrg2Code()==null||bean.getOrg2Code().equals("")) {
			bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		}
		MontIndexDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.allMontList(bean);
	}
	//excel导出专用
	public List<MontIndexBean> alMontList(MontIndexBean bean) {
		return dao.allMontList(bean);
	}
	//excel导出专用
	public List<MontIndexBean> hisMontList(MontIndexBean bean) {
		return dao.montHisList(bean);
	}
	//excel导出专用
	public List<MontIndexBean> mmontList(MontIndexBean bean) {
		return dao.montList(bean);
	} 

	public static MontIndexService getInstance() {
		return SpringUtil.getBean(MontIndexService.class);
	}

	public Object download(MontIndexBean bean) throws Exception {
		String montTypeName = "";
		String montType=bean.getMontType();
		if (montType.equals("11")) {
			montTypeName="专项包";
		}else if (montType.equals("12")) {
			montTypeName="省行统购资产";
		}else if (montType.equals("21")) {
			montTypeName="非省行统购类资产";
		}else if (montType.equals("22")) {
			montTypeName="非专项包费用类";
		}
		String sourceFileName=bean.getDataYear()+montTypeName+"监控指标导出";
		createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("montCode", bean.getMontCode());
		map.put("montType", bean.getMontType());
		map.put("montCodeHis", bean.getMontCodeHis());
		map.put("montName", bean.getMontName());
		map.put("dataYear", bean.getDataYear());
		map.put("matrCode", bean.getMatrCode());
		map.put("matrName",	bean.getMatrName());
		map.put("org1Code", bean.getOrg1Code());
		map.put("org2Code",	bean.getOrg2Code());
		if ("11"==bean.getMontType()) {
			return exportDeal.execute(sourceFileName, "MONT_ZXB_EXPORT", destFile , map);
		} else{
			return exportDeal.execute(sourceFileName, "MONT_EXPORT", destFile , map);
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
	/**
	 * 监控指标查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<MontIndexBean> selectMont(MontIndexBean bean) {
		MontIndexDao pageDao = PageUtils.getPageDao(dao);
//		if (Tool.CHECK.isBlank(bean.getIsSelectHis())) {
//			return pageDao.selectMontThisYear(bean);
//			
//		}else {
//			return pageDao.selectHis(bean);
//		}
		
		
		return pageDao.selectMontThisYear(bean);
	}

	public String preAddIsTrue(MontIndexBean bean) {
		return dao.preAddIsTrue(bean);
	}
	/**
	 * 得到所有物料的核算吗
	 * @return
	 */
	public List<MontIndexBean> getCglCodeList() {
		return dao.getCglCodeList();
	}

	public List<MontIndexBean> getMontMatrByMontCodeInvalid(MontIndexBean bean) {
		CommonLogger.info("根据监控指标查询和物料废弃的对应关系,MontIndexService,getMontMatrByMontCodeInvalid");
		return dao.getMontMatrByMontCodeInvalid(bean);
	}
	/**
	 * 启用
	 * @param bean
	 */
//	public void changeValid(MontIndexBean bean) {
//		CommonLogger.info("启用监控指标和物料的关系,监控指标代码:"+bean.getMontCode()+",MontIndexService,changeValid");
//		String [] valids = bean.getInValids().split(",");
//		List<String> validList = new ArrayList<String>();
//		if (!Tool.CHECK.isEmpty(valids) && valids.length>0) {
//			validList =   Arrays.asList(bean.getInValids());
//		}
//		
//		bean.setInValidList(validList);
//		dao.changeValid(bean);
//	}

	public MontIndexBean getOrg21Name(String org2Code) {
		return dao.getOrg21Name(org2Code);
	}

}
