package com.forms.prms.web.projmanagement.projectMgr.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.BusTypes;
import com.forms.prms.tool.constantValues.OperateValues;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.projmanagement.projectMgr.dao.ProjectMgrDAO;
import com.forms.prms.web.projmanagement.projectMgr.domain.Project;
import com.forms.prms.web.projmanagement.projectMgr.domain.ProjectDept;
import com.forms.prms.web.sysmanagement.waterbook.service.WaterBookService;

@Service
public class ProjectMgrService {
	@Autowired
	private ProjectMgrDAO pDao;

	@Autowired
	private WaterBookService wService;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;	
	
	//获得类实例
	public static ProjectMgrService getInstance(){
		return SpringUtil.getBean(ProjectMgrService.class);
	}

	/**
	 * 查找项目类型选择列表
	 * 
	 * @return
	 */
	public List<Project> getProjectType() {
		CommonLogger.info("查询项目类型信息列表,ProjectMgrService,getProjectType()");
		PageUtils.setPageSize(10);
		ProjectMgrDAO pageDao = PageUtils.getPageDao(pDao);
		return pageDao.getProjectType(WebHelp.getLoginUser().getOrg1Code());
	}

	/**
	 * 项目 列表
	 * 
	 * @param proj
	 * @return
	 */
	public List<Project> listProj(Project proj) {

		proj.setUserId(WebHelp.getLoginUser().getUserId());
		// 是否为超级管理员
		proj.setIsSuper(WebHelp.getLoginUser().getIsSuperAdmin());
		// 登陆人员所属责任中心
		proj.setuDutyCode(WebHelp.getLoginUser().getDutyCode());
		proj.setuOrg1Code(WebHelp.getLoginUser().getOrg1Code());
        proj.setuOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		
		ProjectMgrDAO pageDAO = PageUtils.getPageDao(pDao);
		CommonLogger.info("查询项目列表信息！,ProjectMgrService,listProj()");
		return pageDAO.listProj(proj);
	}

	/**
	 * 项目Option 列表
	 * 
	 * @param proj
	 * @return
	 */
	public List<Project> getProjOption(Project proj) {

		proj.setUserId(WebHelp.getLoginUser().getUserId());
		// 登陆人员所属责任中心
		proj.setuDutyCode(WebHelp.getLoginUser().getDutyCode());
		proj.setuOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		proj.setuOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		proj.setIsSuper(WebHelp.getLoginUser().getIsSuperAdmin());
		PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
		ProjectMgrDAO pageDAO = PageUtils.getPageDao(pDao);
		CommonLogger.info("查询项目选择列表信息！,ProjectMgrService,getProjOption()");
		return pageDAO.getProjOption(proj);
	}

	/**
	 * 项目修改列表
	 * 
	 * @param proj
	 * @return
	 */
	public List<Project> listUpdProj(Project proj) {
		proj.setUserId(WebHelp.getLoginUser().getUserId());
		// 登陆人员所属责任中心
		proj.setuDutyCode(WebHelp.getLoginUser().getDutyCode());
		proj.setIsSuper(WebHelp.getLoginUser().getIsSuperAdmin());
		ProjectMgrDAO pageDAO = PageUtils.getPageDao(pDao);
		CommonLogger.info("修改项目选择列表信息！(项目ID:"+proj.getProjId()+"),ProjectMgrService,listUpdProj()");
		return pageDAO.listUpdProj(proj);
	}

	/**
	 * 新增项目
	 * 
	 * @param proj
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addProj(Project proj) {
		boolean r = true;

		String isSuperAdmin = WebHelp.getLoginUser().getIsSuperAdmin();
		// 1.新增项目信息
		if("1".equals(isSuperAdmin)){//是超级管理员 一级行
			proj.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		}
		else{//非超级管理员 二级行
			proj.setOrg1Code(WebHelp.getLoginUser().getOrg2Code());
		}
		proj.setIsSuper(isSuperAdmin);
		proj.setInstDutyCode(WebHelp.getLoginUser().getDutyCode());
		int i = pDao.addProj(proj);
		// 2.新增项目版本
		proj.setOperType("新增");
		proj.setOperUser(WebHelp.getLoginUser().getUserId());
		proj.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		int pLi = pDao.addProjLog(proj);

		// 安全性 为责任中心
		if ("3".equals(proj.getScope())) {
			// 3.添加项目与责任中心责任表
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pdList", getPDList(proj));

			if (map.size() < 1) {
				r = false;
			}
			pDao.addProjDept(map);
			// 4.新增关系 版本
			pDao.addProjDeptLog(map);
		}

		if (i < 1 || pLi < 1) {
			r = false;
		}
		
		if(r){
			CommonLogger.info("项目新增成功！(项目ID:"+proj.getProjId()+"),ProjectMgrService,addProj()");
			wService.insert(proj.getProjId(), BusTypes.PROJECT, OperateValues.ADD, null, null, null);
		}

		return r;

	}

	/**
	 * 项目详情
	 * 
	 * @param projId
	 * @return
	 */
	public Project veiwProj(String projId) {
		CommonLogger.info("查看项目信息！(项目ID:"+projId+"),ProjectMgrService,veiwProj()");
		Project p = pDao.veiwProj(projId);
		if ("3".equals(p.getScope())) {
			List<String> dutyCodes = pDao.getDutyCodes(projId);
			String dutyC = "";
			for (String a : dutyCodes) {
				dutyC += a + ",";
			}
			dutyC = dutyC.length() > 0 ? dutyC.substring(0, dutyC.length() - 1) : dutyC;
			p.setDutyCode(dutyC);
		}
		
		return p;
	}

	/**
	 * 项目更新
	 * 
	 * @param proj
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updProject(Project proj) {
		// 1.更新项目信息
		int i = pDao.updProject(proj);
		// 2.添加项目版本
		Project p = pDao.veiwProj(proj.getProjId());
		p.setOperType("修改");
		p.setOperUser(WebHelp.getLoginUser().getUserId());
		p.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		pDao.addProjLog(p);
		// 安全性 为责任中心
		if ("3".equals(proj.getScope())) {
			// 3.【检查】更新关系表 项目与责任中心
			// 3.1 关系表项目与责任中心的相关记录
			pDao.deleteProjDept(proj.getProjId());
			proj.setVersionNo(p.getVersionNo());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pdList", getPDList(proj));
			pDao.addProjDept(map);
			// 4.【检查】添加关系版本表
			pDao.addProjDeptLog(map);
		}
		
		if(i > 0){
			CommonLogger.info("修改项目信息！(项目ID:"+proj.getProjId()+"),ProjectMgrService,updProject()");
			wService.insert(proj.getProjId(), BusTypes.PROJECT, OperateValues.UPDATE, null, null, null);
		}
		return i;
	}

	/**
	 * 更新终止日期
	 * 
	 * @param proj
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean endProj(Project proj) {
		// 1.更新终止时间
		int i = pDao.updEndDate(proj);
		if (i < 1) {
			return false;
		}
		// 2.添加项目版本
		for(String pId : proj.getProjIdList()){
		Project p = pDao.veiwProj(pId);
		p.setVersionNo(null);
		p.setOperType("终止");
		p.setOperUser(WebHelp.getLoginUser().getUserId());
		p.setOperDutyCode(WebHelp.getLoginUser().getDutyCode());
		p.setOperDate(Tool.DATE.getDate());
		p.setOperTime(Tool.DATE.getTime());
		pDao.addProjLog(p);
		CommonLogger.info("终止项目，更新项目终止时间！(项目ID:"+pId+";终止时间:"+proj.getEndDate()+"),ProjectMgrService,endProj()");		
		//终止意见添加 添加流水
		wService.insert(pId, BusTypes.PROJECT, OperateValues.PROJEND, proj.getEndMemo(), null, null);
		}

		
		
		return true;

	}

	/**
	 * 校验预算金额
	 * 
	 * @param proj
	 * @return
	 */
	public Project checkAmt(Project proj) {
		return pDao.checkAmt(proj);
	}

	/**
	 * 获取金额信息
	 * 
	 * @param projId
	 * @return
	 */
	public Project getAmtInfo(String projId) {
		return pDao.getAmtInfo(projId);
	}

	/**
	 * 获取责任中心列表
	 * 
	 * @param proj
	 * @return
	 */
	private List<ProjectDept> getPDList(Project proj) {
		List<ProjectDept> pdList = new ArrayList<ProjectDept>();
		int size = proj.getDutyCodeList().length;
		for (int i = 0; i < size; i++) {
			ProjectDept pd = new ProjectDept();
			pd.setProjId(proj.getProjId());
			pd.setVersionNo(proj.getVersionNo());
			pd.setDutyCode(proj.getDutyCodeList()[i]);
			pdList.add(pd);
		}
		return pdList;
	}

	/**
	 * 校验新增的冻结金额+历史冻结金额+历史合同执行金额 是否 大于项目预算金额
	 * 
	 * @param projId
	 * @param freezeTotalAmt
	 * @return
	 */
	public boolean inFreezeAmtIsValid(String projId, BigDecimal increaseFreezeAmt) {
		boolean r = true;
		Project p = getAmtInfo(projId);
		BigDecimal amtCount = increaseFreezeAmt.add(p.getFreezeTotalAmt()).add(p.getCntTotalAmt());
//		System.out.println(amtCount.compareTo(new BigDecimal(0)));
		if ((amtCount.compareTo(new BigDecimal(0)) < 0) || amtCount.compareTo(p.getBudgetAmt()) > 0) {
			r = false;
		}
		return r;
	}

	/**
	 * 修改冻结金额
	 * 
	 * @param inFreezeAmt
	 * @param projId
	 * @param taxNamt 
	 * @return
	 * 
	 *         若inFreezeAmt > 0 为增加冻结金额 若inFreezeAmt < 0 为减少冻结金额
	 * 
	 */
	public int updateFreezeAmt(String projId, BigDecimal inFreezeAmt, BigDecimal taxNamt) {
		CommonLogger.info("给项目编号为"+projId+",加上冻结金额"+inFreezeAmt+"不可抵扣金额"+taxNamt+",ProjectMgrService,updateFreezeAmt");
		int i = pDao.updateFreezeAmt(inFreezeAmt, projId,taxNamt);
		return i;
	}

	/**
	 * 校验 新增合约执行金额+历史合同执行金额 是否大于项目预算
	 * 
	 * @param projId
	 * @param cntTotalAmt
	 * @return
	 */
	public boolean incntAmtIsValid(String projId, BigDecimal increaseCntTotalAmt) {
		boolean r = true;
		Project p = getAmtInfo(projId);
		BigDecimal amtCount = increaseCntTotalAmt.add(p.getCntTotalAmt());
		if ((amtCount.compareTo(new BigDecimal(0)) < 0) || amtCount.compareTo(p.getBudgetAmt()) > 0) {
			r = false;
		}
		return r;
	}

	/**
	 * 修改合同执行金额
	 * 
	 * @param inCntAmt
	 * @param projId
	 * @return
	 * 
	 *         若inCntAmt > 0 为增加合同执行金额 若inCntAmt < 0 为减少合同执行金额
	 * 
	 */
	public int updateCntAmt(String projId, BigDecimal inCntAmt,BigDecimal taxNamt) {
		int i = pDao.updateCntAmt(inCntAmt, projId,taxNamt);
		return i;
	}

	/**
	 * 修改已付款金额
	 * 
	 * @param inPayAmt
	 * @param projId
	 * @return
	 * 
	 *         若inPayAmt > 0 为增加已付款金额 若inPayAmt < 0 为减少已付款金额
	 * 
	 */
	public int updatepayAmt(String projId, BigDecimal inPayAmt) {
		int i = pDao.updatePayAmt(inPayAmt, projId);
		return i;
	}

	// 1.合同建立时，生成冻结金额 
	// -- 修改为前台校验金额是否超过预算并给出提示，但不论是否超出，后台一律通过并加入冻结金额。
	@Transactional(rollbackFor = Exception.class)
	public boolean newCnt(String projId, BigDecimal newAmt, BigDecimal taxNamt) {
/*		if (inFreezeAmtIsValid(projId, newAmt)) {// 校验冻结金额大小
			if (updateFreezeAmt(projId, newAmt) > 0) {// 更新冻结金额
				// 冻结金额+历史冻结金额+历史合同金额 <预算金额
				// 冻结金额更新成功！
				return true;
			} else {
				// 冻结金额+历史冻结金额+历史合同金额 <预算金额
				// 冻结金额更新失败！
				return false;
			}
		} else {
			// 冻结金额+历史冻结金额+历史合同金额 >项目预算金额
			// 给予提示超过项目预算金额的提示！
			return false;
		}*/
		updateFreezeAmt(projId, newAmt,taxNamt);
		return true;
	}

	// 2.合同确立时
//	@Transactional(rollbackFor = Exception.class)
//	public boolean confirmCnt(String projId, BigDecimal confirmAmt) {
//		// 合同执行金额需要累加，校验金额大小是否超过项目预算金额
//		if (incntAmtIsValid(projId, confirmAmt)) {
//			// 合同执行金额大小符合要求
//			if (getAmtInfo(projId).getFreezeTotalAmt().add(new BigDecimal("-" + confirmAmt.toString()))
//					.compareTo(new BigDecimal(0)) < 0) {
//				// 冻结金额 减去 确认金额 小于0 ，不符合要求，返回false
//				return false;
//			}
//			if (updateCntAmt(projId, confirmAmt) > 0
//					&& updateFreezeAmt(projId, new BigDecimal("-" + confirmAmt.toString())) > 0) {
//				// 执行增加合同执行金额，减少冻结金额，成功返回true
//				return true;
//			} else {
//				// 执行增加合同执行金额，减少冻结金额，失败返回false
//				return false;
//			}
//		} else {
//			// 合同执行金额大小不符合要求，返回false;
//			return false;
//		}
//	}

	// 3.合同执行时
	@Transactional(rollbackFor = Exception.class)
	public boolean executeCnt(String projId, BigDecimal executeAmt) {
		Project p = getAmtInfo(projId);
		// 校验累加后的执行金额是否大于等于0
		if ((p.getPayTotalAmt().add(executeAmt)).compareTo(new BigDecimal(0)) < 0) {
			// 若否，则返回false;
			return false;
		} else {
			// 若金额大小符合要求，则执行数据更新
			if (updatepayAmt(projId, executeAmt) > 0) {
				// 已付款金额增加，成功返回true
				return true;
			} else {
				// 已付款金额增加，失败返回false
				return false;
			}
		}
	}
	
	/**
	 * @methodName orderBackFree
	 * desc 订单退回时，根据订单号，释放对应项目的占用金额 
	 * 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean orderBackFree(String orderId){
		List<CntDevice> cntDeviceList = pDao.getCntDeviceByOrder(orderId);
		boolean result = true;
		for(CntDevice cDevice : cntDeviceList){
			result = result && pDao.orderBackFree(cDevice.getProjId(),cDevice.getExecAmt(),cDevice.getTaxNamt()) == 1 ? true:false;
		}
		return result;
	}	
	/**
	 * 导出数据
	 * @param userInfo
	 * @return
	 * @throws Exception 
	 */
	public String exportData(Project bean) throws Exception {
		String sourceFileName = "项目数据导出";
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("userId", WebHelp.getLoginUser().getUserId());
		map.put("dutyCode", WebHelp.getLoginUser().getDutyCode());
		map.put("isSuper", WebHelp.getLoginUser().getIsSuperAdmin());
		map.put("org1Code", WebHelp.getLoginUser().getOrg1Code());
		map.put("org2Code", WebHelp.getLoginUser().getOrg2Code());
		
		map.put("projType", bean.getProjType());
		map.put("projName", bean.getProjName());
		map.put("startYear", bean.getStartYear());
		return exportDeal.execute(sourceFileName, "PROJ_TYPE_EXPORT", destFile , map);
	}

	public List<Project> exportExcute(Project bean) {
		return pDao.listProj(bean);
	}

	public List<Project> getProjOption1(Project proj) {
		proj.setuDutyCode(WebHelp.getLoginUser().getDutyCode());
		proj.setuOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		proj.setuOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
		ProjectMgrDAO pageDAO = PageUtils.getPageDao(pDao);
		CommonLogger.info("查询项目选择列表信息！,ProjectMgrService,getProjOption()");
		return pageDAO.getProjOption1(proj);
	}

}
