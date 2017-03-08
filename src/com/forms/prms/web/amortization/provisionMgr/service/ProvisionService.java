package com.forms.prms.web.amortization.provisionMgr.service;

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
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.dao.ICommonExcelDealDao;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.amortization.provisionMgr.dao.ProvisionDAO;
import com.forms.prms.web.amortization.provisionMgr.domain.ProvisionBean;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.user.domain.User;

/**
 * Title:			ProvisionService
 * Description:		预提管理的Service层
 * Copyright: 		formssi
 * @author 			HQQ
 * @project 		ERP	
 * @date 			2015-05-18
 * @version 		1.0
 */
@Service
public class ProvisionService {
	
	@Autowired
	private ProvisionDAO proDao;
	@Autowired
	private SysWarnCountService sysWarnCountService;
	
	@Autowired
	private ICommonExcelDealDao excelDao;
	
	@Autowired
	private ExcelExportGenernalDeal exportDeal;
	
	public static ProvisionService getInstance(){
		return SpringUtil.getBean(ProvisionService.class);
	}
	
	/**
	 * @methodName getProvisionList
	 * 		根据条件获取预提待经办列表
	 * @param provision
	 * @return
	 */
	public List<ProvisionBean> getProvHandleList(ProvisionBean provision){
	    CommonLogger.info("根据条件获取预提待经办列表！,ProvisionService,getProvHandleList()");
		ProvisionDAO pDao = PageUtils.getPageDao(proDao);
		//处理查询传递的参数
		String advCglList = proDao.getAdvCglList();							//获取广告宣传费核算码列表
		Map<String ,Object> argsMap = new HashMap<String , Object>();		//创建用于保存查询过滤参数的Map对象
		provision.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());//拿当前用户的一级行
		argsMap.put("advCglList", advCglList);								//参数1：参照的核算码列表
		argsMap.put("provision", provision);								//参数2：查询过滤条件provision
		argsMap.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		List<ProvisionBean> proList = pDao.listProvHandles(argsMap);
		return this.replaceCommaToNewLine(proList);
	}
	
	/**
	 * 获取预提金额汇总及待摊金额汇总
	 * @param provision
	 * @return
	 */
	public ProvisionBean getPPSumAmt (ProvisionBean provision){
		CommonLogger.info("根据条件获取预提、待摊汇总金额！,ProvisionService,getPPSumAmt()");
		//处理查询传递的参数
		String advCglList = proDao.getAdvCglList();							//获取广告宣传费核算码列表
		Map<String ,Object> argsMap = new HashMap<String , Object>();		//创建用于保存查询过滤参数的Map对象
		argsMap.put("advCglList", advCglList);								//参数1：参照的核算码列表
		argsMap.put("provision", provision);
		argsMap.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		return proDao.getPPSumAmt(argsMap);   
	}
	
	/**
	 * @methodName getAllProvHandleIdList
	 * 		【不分页】返回查询到的"所有"的预提经办的ID(以逗号分隔)  		
	 * 			--用于处理勾选[全选查询到的所有数据]时的提交操作，会将当次查询的所有ID(拼接方式为：合同号+受益年月)信息塞到前端，作为后续[提交]操作的方法参数传递
	 * @param provision
	 * @return	ids		返回所有查询到的ID，字符串类型
	 */
	public String getAllProvHandleIdList(ProvisionBean provision){
		String ids = "";
		//1.使用查询条件进行数据的查询
		Map<String ,Object> argsMap = new HashMap<String , Object>();
		argsMap.put("provision", provision);
		argsMap.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		List<ProvisionBean> provHandList = proDao.listProvHandles(argsMap);
		//2.拼接查询到的所有ID信息(只有在有数据时，才会做查询结果ID拼接组合操作)
		if(provHandList.size()>0){
			for(int i=0;i<provHandList.size();i++){
				ids += provHandList.get(i).getCntNum() + provHandList.get(i).getFeeYyyymm() + ",";
			}
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	/**
	 * @methodName getProvRecheckList
	 * 		根据条件获取预提复核列表
	 * @param provision
	 * @return
	 */
	public List<ProvisionBean> getProvRecheckList(ProvisionBean provision){
		CommonLogger.info("根据条件获取预提复核列表！,ProvisionService,getProvRecheckList()");
		ProvisionDAO pDao = PageUtils.getPageDao(proDao);
		//处理查询传递的参数
		String advCglList = proDao.getAdvCglList();							//获取广告宣传费核算码列表
		Map<String ,Object> argsMap = new HashMap<String , Object>();		//创建用于保存查询过滤参数的Map对象
		argsMap.put("advCglList", advCglList);								//参数1：参照的核算码列表
		argsMap.put("provision", provision);								//参数2：查询过滤条件provision
		argsMap.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		List<ProvisionBean> provRecheckList = pDao.listProvRechecks(argsMap);
		return this.replaceCommaToNewLine(provRecheckList);
	}
	
	
	//获取当次查询得到的预提复核的所有ID，并组合成字符串返回
	/**
	 * @methodName getAllProvRecheckIdList
	 * 		根据查询条件，获取复核的所有待复核状态的预提ID信息集合，用于做批量的预提复核操作  
	 * @param provision
	 * @return
	 */
	public String getAllProvRecheckIdList(ProvisionBean provision){
		String ids = "";
		Map<String ,Object> argsMap = new HashMap<String , Object>();
		argsMap.put("provision", provision);
		argsMap.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		List<ProvisionBean> provRecheckList = proDao.listProvRechecks(argsMap);
		if(provRecheckList.size()>0){
			for(int i=0;i<provRecheckList.size();i++){
				ids += provRecheckList.get(i).getCntNum() + provRecheckList.get(i).getFeeYyyymm() + ",";
			}
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	/**
	 * @methodName getProvByCntAndFeeYear
	 * 		获取指定的预提信息：  根据合同号和受益年月从TD_PROVISION_MANAGE表中查询指定的ProvisionBean对象
	 * @param cntNum	合同号(主键条件1)
	 * @param feeYyyymm	收益年月(主键条件2)
	 * @return
	 */
	public ProvisionBean getProvByCntAndFeeYear(String cntNum , String feeYyyymm){
		CommonLogger.info("获取指定的预提信息！,ProvisionService,getProvByCntAndFeeYear()【cntNum:"+cntNum+";feeYyyymm:"+feeYyyymm+";】");
		ProvisionBean provision = proDao.getProvByCntAndFeeYear(cntNum , feeYyyymm);
		return provision;
	}
	
	/**
	 * @methodName handleSubmit
	 * 		预提经办提交操作(分别处理一条或多条数据批量提交)
	 * 			更新字段：PROV_FLAG=0/1		DATA_FLAG=1待复核		OPER_USER=loginUser		OPER_DATE+OPER_TIME=数据库时间
	 * @param provision
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int handleSubmit(String provMgrIdList , String provFlag){
		CommonLogger.info("预提经办提交操作！,ProvisionService,handleSubmit()");
		int updateCount = 0;
		ProvisionBean proBean ;
		String id1 = "";
		String id2 = "";
		//System.out.println("【HQQTest：后台接收到的IDlist为："+provMgrIdList+"】");
		ProvisionBean provision = new ProvisionBean();
		provision.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		if(provMgrIdList.isEmpty()){
			provMgrIdList = this.getAllProvHandleIdList(provision);
		}
		//System.out.println("【HQQTest：后台接收到的IDlist为："+provMgrIdList+"】");
		String[] provIdArray = provMgrIdList.split(",");
		
		List<String> updateSysWarnCntList = new ArrayList<String>(); 								//[预提经办]完成后，更新SYS_WARN_PREPRO_INF表中关于合同的提示信息
				
		for(int i=0;i<provIdArray.length;i++){
			//获取需要经办提交的预提主键ID(合同号+受益年月)
			id1 = provIdArray[i].substring(0, provIdArray[i].length()-6);							//合同号
			id2 = provIdArray[i].substring((provIdArray[i].length()-6), provIdArray[i].length());	//受益年月
			//System.out.println("【HQQTest:合同号："+id1+"；受益年月：--"+id2+"】");
			//获取需要预提经办提交的预提
			proBean = this.getProvByCntAndFeeYear(id1, id2);
			//System.out.println("【HQQTest：预提受益年月|预提合同号：】"+proBean.getFeeYyyymm()+"::"+proBean.getCntNum());
			proBean.setOperUser(WebHelp.getLoginUser().getUserId());								//设置经办提交人信息
			proBean.setProvFlag(provFlag);															//设置是否需要预提provFlag
			proDao.handleSubimit(proBean);															//执行预提经办提交
			updateCount ++ ;
			updateSysWarnCntList.add(proBean.getCntNum());
		}
		
		//[9月9日]更新SYS_WARN_PREPRO_INF表(根据合同号+orgCode)的FUNC_TYPE
		updateProvFuncTypeInSysWarn(updateSysWarnCntList , WebHelp.getLoginUser().getOrg1Code(),"T4");
		return updateCount;																			//返回成功执行的预提经办条数
	}
	
	/**
	 * @methodName provRecheck
	 * 		预提复核操作：单条或批量通过/退回预提，录入或更新执行预提复核操作者的信息(通过预提复核将只在预提查询部分展示，退回复核将在预提经办列表中待经办)  
	 * @param provision
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public int provRecheck(ProvisionBean provision) throws Exception{		
		//复核
		int recheckCount = subProvRecheck(provision);
		return recheckCount;
	}
	
	
	/**
	 * 
	 * @param provision
	 * @return
	 */
	
	//预提复核操作(状态：  通过3/退回2)
	public int subProvRecheck(ProvisionBean provision){
		CommonLogger.info("预提复核操作！,ProvisionService,subProvRecheck()");
		int recheckCount = 0;
		ProvisionBean proBean ;
		provision.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		if(provision.getProvMgrIdList().isEmpty()){
			provision.setProvMgrIdList(getAllProvRecheckIdList(provision));
		}
		
		String[] provIdArray = provision.getProvMgrIdList().split(",");		//获取页面中提交的需要预提复核的预提ID信息
		String recheckDataFlag = provision.getDataFlag();					//获取页面用户选择的复核状态：通过=3、退回=2
		
		List<String> beUpdateCntList = new ArrayList<String>();				//[预提复核]完成后，待更新SYS_WARN_PREPRO_INF表中关于合同的提示信息的合同List
		for(int i=0;i<provIdArray.length;i++){		
			proBean = this.getProvByCntAndFeeYear(provIdArray[i].substring(0, provIdArray[i].length()-6), provIdArray[i].substring(provIdArray[i].length()-6, provIdArray[i].length()));
			proBean.setCheckUser(WebHelp.getLoginUser().getUserId());		//设置复核操作者信息
			proBean.setDataFlag(recheckDataFlag);							//分别设置每条复核的复核状态：复核通过=3 / 复核退回=2
			proDao.recheckPassOrReturn(proBean);							//根据参数进行每一条预提的复核
			recheckCount ++;
			beUpdateCntList.add(proBean.getCntNum());
		}
		
		//如果是复核通过，则删除SYS_WARN_PREPRO_INF的记录；如果是复核退回，则更新SYS_WARN_PREPRO_INF表的FUNC_TYPE;
		if("3".equals(recheckDataFlag)){
			//复核通过
			deleteProvFuncTypeInSysWarn(beUpdateCntList , WebHelp.getLoginUser().getOrg1Code());
		}else if("2".equals(recheckDataFlag)){
			//复核退回
			updateProvFuncTypeInSysWarn(beUpdateCntList , WebHelp.getLoginUser().getOrg1Code(),"T3");
		}
		
		/*************检查预提复核情况******************/
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String targetYYYYMM = proDao.getYYYYMM(org1Code);
		if(proDao.getNotPass(org1Code,targetYYYYMM) == null){//预提复核全部通过
			//0.获取广告宣传费核算码列表
			String advCglList = proDao.getAdvCglList();
			String yyyymm = targetYYYYMM;
			
			//1.更新total中不预提的合同，预提状态及下月冲销状态
			proDao.updateProviFlag(advCglList,targetYYYYMM);
			proDao.updateProviCancelFlag(advCglList,targetYYYYMM);
			
			if("12".equals(yyyymm.substring(4, 6))){
			//2.记录不预提的合同及物料存量受益金额 
			proDao.addLackBgt(targetYYYYMM);
			}
		}
		
		return recheckCount;
	}
	
	
	/**
	 * @methodName provQueryList
	 * 		根据条件查询获取的预提信息  
	 * @param provision
	 * @return
	 */
	public List<ProvisionBean> provQueryList(ProvisionBean provision){
		CommonLogger.info("查询预提信息！,ProvisionService,provQueryList()");
		ProvisionDAO pDao = PageUtils.getPageDao(proDao);
		String feeYm=provision.getFeeYyyymm();
		//转换日期格式
		if (!Tool.CHECK.isBlank(feeYm)) {
			provision.setFeeYyyymm(feeYm.substring(0,4)+feeYm.substring(5,7));
		}
		//处理查询传递的参数
		String advCglList = proDao.getAdvCglList();							//获取广告宣传费核算码列表
		Map<String ,Object> argsMap = new HashMap<String , Object>();		//创建用于保存查询过滤参数的Map对象
		argsMap.put("advCglList", advCglList);								//参数1：参照的核算码列表
		argsMap.put("provision", provision);								//参数2：查询过滤条件provision
		List<ProvisionBean> provisionList = pDao.getProvQueryList(argsMap);
		return this.replaceCommaToNewLine(provisionList);
	}
	
	/**
	 * @methodName getProvDetailService
	 * 		【预提详情】获取provision对象的详细信息：
	 * 			操作描述：根据用户点击列表中指定provision最后一列的[详情]按钮，获取页面上的provision对象
	 * 				【包括provMgrId(用于查找预提其他信息)、feeCglCode、provisionAmt、prepaidAmt(直接取值并赋值至provision详情页面)】
	 * @param provision
	 * @return
	 */
	public ProvisionBean getProvDetailService(ProvisionBean provision){
		String ProvId = provision.getProvMgrId();									//获取需要查询详情的预提主键ID
		System.out.println("【进入预提详情查询：】"+provision.getProvMgrId());
		String feeCglCode = provision.getFeeCglCode();		//预提中，费用核算码信息
		String provAmt ;
		String prepaidAmt ;
		//处理空值
		if("".equals(provision.getProvisionAmt())||null == provision.getProvisionAmt()){
			provAmt = "";
		}else{
			provAmt = provision.getProvisionAmt().toString();					//预提中，预提金额信息
		}
		if("".equals(provision.getPrepaidAmt())||null == provision.getPrepaidAmt()){
			prepaidAmt = "";
		}else{
			prepaidAmt = provision.getPrepaidAmt().toString();					//预提中，待摊金额信息
		}
		//根据ID查找预提的详细信息，并替换provision对象
		provision = this.getProvByCntAndFeeYear( ProvId.substring(0, ProvId.length()-6) ,  ProvId.substring(ProvId.length()-6, ProvId.length()) );
		//设置上一步List页面中传过来的三个预提关联信息
		provision.setFeeCglCode(feeCglCode);
		if(null!=provAmt && !"".equals(provAmt)){
			provision.setProvisionAmt(provAmt);
		}
		if(null!=prepaidAmt && !"".equals(prepaidAmt)){
			provision.setPrepaidAmt(prepaidAmt);
		}
		return provision;
	}
	
	
	/**
	 * @methodName judgeSuperAdmin
	 * 		 【公共工具方法】 根据判断用户是否超级管理员，并设置ProvisionBean的org1Code的值
	 * 			设置逻辑：为超级管理员时(org1Code=登录者一级行)  ；非超级管理员时(org1Code=登录者二级行/orgCode)
	 * @param loginUser
	 * @param provision
	 * @return
	 */
	public ProvisionBean judgeSuperAdmin(User loginUser , ProvisionBean provision){
		CommonLogger.info("根据判断用户是否超级管理员！,ProvisionService,judgeSuperAdmin()【loginUser:"+loginUser+";】");
		if("1".equals(loginUser.getIsSuperAdmin())){				//超级管理员标识 ：	1=是；0=否；
			//超级管理员
			provision.setOrg1Code(loginUser.getOrg1Code());			//超级管理员时取一级行下属所有责中
			provision.setOrgFlag("1");
		}else{
			//非超级管理员
			if(null == loginUser.getOrg2Code() || "".equals(loginUser.getOrg2Code())){			//判断用户所属二级行是否为空
				if(null == loginUser.getOrgCode() || "".equals(loginUser.getOrgCode())){		//判断用户orgCode是否为空
					provision.setOrg1Code(loginUser.getDutyCode());								//当orgCode为空时，取dutyCode
					provision.setOrgFlag("4");
				}else{
					provision.setOrg1Code(loginUser.getOrgCode());								//当orgCode不为空时，直接取orgCode
					provision.setOrgFlag("0");
				}
			}else{
				provision.setOrg1Code(loginUser.getOrg2Code());									//当用户二级行不为空时，取其二级行
				provision.setOrgFlag("2");
			}
		}
		return provision;
	}

	/**
	 * @methodName replaceCommaToNewLine
	 * 		【公共工具方法】将下述三项中的逗号替换为空格
	 * 				将预提列表中的"费用核算码"、"预提金额"、"待摊金额" 这三项中的逗号","更换为换行符"<br/>"  
	 * @param provisionList
	 * @return
	 */
	public List<ProvisionBean> replaceCommaToNewLine(List<ProvisionBean> provisionList){
		for(ProvisionBean bean : provisionList){
			if(!(null == bean.getMatrCode() || "".equals(bean.getMatrCode()))){
				bean.setMatrCode(bean.getMatrCode().replaceAll(",", "<br/>"));
			}
			if(!(null == bean.getMontCode() || "".equals(bean.getMontCode()))){
				bean.setMontCode(bean.getMontCode().replaceAll(",", "<br/>"));
			}
			if(!(null == bean.getFeeCglCode() || "".equals(bean.getFeeCglCode()))){
				bean.setFeeCglCode(bean.getFeeCglCode().replaceAll(",", "<br/>"));
			}
			if(null != bean.getProvisionAmt()){
				bean.setProvisionAmt(bean.getProvisionAmt().toString().replaceAll(",", "<br/>"));
			}
			if(null != bean.getPrepaidAmt()){
				bean.setPrepaidAmt(bean.getPrepaidAmt().toString().replaceAll(",", "<br/>"));
			}
		}
		return provisionList;
	}
	
	
	/**
	 * 获取预提待摊数据生成状态
	 * @return
	 */
	public String getPPStatus(String org1Code,String yyyymm){
		CommonLogger.info("获取预提待摊数据生成状态！,ProvisionService,getPPStatus()【org1Code:"+org1Code+";】");
		return proDao.getPPStatus(org1Code,yyyymm);
	}
	
	/**
	 * 检查是否为存在回冲的二级行
	 * @param org2Code
	 * @return
	 */
	public String checkOrg2Ok(String org2Code){
		CommonLogger.info("检查是否为存在回冲的二级行！,ProvisionService,checkOrg2Ok()【org2Code:"+org2Code+";】");
		String targetYYYYMM = proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code());
		return proDao.checkOrg2Ok(org2Code,targetYYYYMM);
	}
	
	//更新SYS_WARN_PREPRO_INF表关于预提经办(改为T4)/预提复核(退回时改为T3)的状态
	public void updateProvFuncTypeInSysWarn( List<String> beUpdateCntList , String dutyCode , String funcType ){
		CommonLogger.info("记录首页预提待摊提醒信息！,ProvisionService,updateProvFuncTypeInSysWarn()");
		for(int i=0;i<beUpdateCntList.size();i++){
			proDao.updateProvFuncTypeInSysWarn(beUpdateCntList.get(i) , dutyCode , funcType);
		}
		//[9月9日]林佳说孙凯提供每次对SYS_WARN_PERPRO_INF做更新后需调用下面方法：
		sysWarnCountService.DealSysWarnCount(dutyCode, "T");
	}
	
	//预提复核 通过后，更新SYS_WARN_PREPRO_INF表，将符合CNTNUM和ORGCODE查询的记录删除
	public void deleteProvFuncTypeInSysWarn( List<String> beDeleteCntList , String org1Code ){
		CommonLogger.info("更新首页预提经办提示记录！,ProvisionService,deleteProvFuncTypeInSysWarn()");
		for(int i=0;i<beDeleteCntList.size();i++){
			proDao.deleteProvFuncTypeInSysWarn(beDeleteCntList.get(i) , org1Code ); 
		}
		sysWarnCountService.DealSysWarnCount(org1Code, "T");
	}
	
	/**
	 * 
	 *  查询当年当月复核是否存在不通过
	 * @param org1Code
	 * @return
	 */
	public String getNotPass(String org1Code){
		String targetYYYYMM =  proDao.getYYYYMM(org1Code);
		String result = proDao.getNotPass(org1Code,targetYYYYMM);
		return result;
	}

	/**
	 * 根据查詢條件获取预提经办
	 * 
	 * @param con
	 * @return 返回生成的任务ID
	 * @throws Exception
	 */
	public String queryDownloadList(ProvisionBean proBean) throws Exception{
		CommonLogger.info("得到生成的一个任务ID,ProvisionService，queryDownloadList");
		String destFile = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"/proCheckExport.xlsx";
		proBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		Map<String,Object> map = new HashMap<String, Object>();
		if(null!=proBean.getCntNum() && !"".equals(proBean.getCntNum()))
		{
			map.put("cntNum", proBean.getCntNum());
		}
		if(null!=proBean.getCreateDepts() && !"".equals(proBean.getCreateDepts()))
		{
			map.put("createDepts", proBean.getCreateDepts());
		}
		if(null!=proBean.getDataFlag() && !"".equals(proBean.getDataFlag()))
		{
			map.put("dataFlag", proBean.getDataFlag());
		}
		return exportDeal.execute("预提经办", "PROCHECK",destFile,map);
	}

	public String queryDownloadCount(Map map){
		map.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		return proDao.getDownloadCount(map);
	}
	
	/** 
	 * @methodName queryContactList
	 * desc   获取指定范围的需下载数据
	 * 
	 * @param map 
	 */
	public List<ProvisionBean> queryContactList(Map map) {
		map.put("targetYYYYMM", proDao.getYYYYMM(WebHelp.getLoginUser().getOrg1Code()));
		return proDao.queryDownloadList(map);
	}

	public void updateExcelResult(CommonExcelDealBean bean) {
		excelDao.updateExportResult(bean);
	}
	
	public String getAdvCglList()
	{
		return proDao.getAdvCglList();
	}
	
	//获取当前省份预提待摊处理中的任务年月
    public String getYYYYMM(String org1Code){
    	return proDao.getYYYYMM(org1Code);
    }
	
}
