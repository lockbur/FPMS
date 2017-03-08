package com.forms.prms.web.budget.budgetplan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.web.budget.budgetplan.dao.BudgetPlanDAO;
import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTempDetailBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTmpltDutyBean;
import com.forms.prms.web.budget.budgetplan.domain.TaskLoadBean;
import com.forms.prms.web.user.domain.User;

@Service
public class BudgetPlanService{
	
	@Autowired
	public BudgetPlanDAO budgetDAO;
	
	@Autowired
	public ExcelImportGenernalDeal importGenernalDeal;		//Excel导入Service
	
	@Autowired
	private ExcelExportGenernalDeal exportDeal;				//Excel导出Service
	
	/**
	 * 获得BudgetService实例(使用于Excel导入、Excel导出时的业务处理)
	 * @return
	 */
	public static BudgetPlanService getInstance(){
		return SpringUtil.getBean(BudgetPlanService.class);				
	}

	/**
	 * 新增预算模板、新增模板关联关系、Excel导入操作
	 * @param budget
	 * @param sourceFPath
	 * @throws IOException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addBudgetPlanAndRel( BudgetPlanBean budget ){
		//1.[新增预算模板]操作，预算Bean处理，保存模板信息至数据库
		User instOper = WebHelp.getLoginUser();				//获取创建柜员
		
		//1.5【复制Excel文件另存保存】将上传的Excel文件另存为服务器指定物理路径
		//上传组件保存的文件原始文件路径
		String uploadFileUrl = budget.getServerFile();
		//上传组件自命名的文件名
		String uploadFileRename = uploadFileUrl.substring(uploadFileUrl.lastIndexOf("/")+1,uploadFileUrl.length());	
		String targetFileUrl = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR")+"\\"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"\\"+uploadFileRename;		//复制到服务器本地路径+日期+上传组件重命名文件名保存(路径+日期文件夹+生成文件名)
		//【创建文件夹路径】判断目标文件夹路径，不存在则创建
		this.createFilePath(new File(targetFileUrl.substring(0, targetFileUrl.lastIndexOf("\\"))));
		//【复制文件】将文件复制到服务器本地指定地址保存
		try {
			this.fileCopyByBufferStream(new File(uploadFileUrl), new File(targetFileUrl));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//【更改DB保存路径】更改上传文件保存信息
		targetFileUrl = targetFileUrl.replaceAll("\\\\", "/");
		budget.setServerFile( targetFileUrl ); 		//上传组件在源文件下载时需要"/"标识符寻找路径
		
		budget.setInstOper(instOper.getUserId());
		String orgCode = instOper.getOrg2Code();
		if(null == orgCode || "".equals(orgCode)){
			//预算所属机构：如果操作柜员的二级行为空，则取其一级行
			orgCode = instOper.getOrg1Code();
		}
		budget.setOrg21Code(orgCode);
		budget.setDataFlag("00");							//初始插入状态为"00"(待处理)
		budgetDAO.addBudgetPlan(budget);					//插入预算基本信息
		
		CommonLogger.info("预算模板新增操作:新增模板及关联可用机构并导入模板Excel信息,主键信息("+budget.getTmpltId()+"),BudgetPlanService,addBudgetPlanAndRel");
		
		//2.[添加预算模板 与 可使用责任中心的关系]操作
		addBudgetRelDuty(budget);

		//3.开始进行[导入Excel]处理
		boolean ExcelImportFlag = false;
		try {
			CommonLogger.info("预算模板新增操作:执行新增时Excel模板导入操作,导入配置(资产类:BUDGET_TEMP01,费用类:BUDGET_TEMP02),BudgetPlanService,importExcel");
			ExcelImportFlag = this.importExcel(budget);
		} catch (Exception e) {
			CommonLogger.info("预算模板新增操作:导入Excel模板时发生异常"+e.getMessage().substring(0, 100)+",BudgetPlanService,importExcel");
			e.printStackTrace();
		}
		CommonLogger.debug("[ImportExcel导入操作已启用]："+ExcelImportFlag);
	}

	/**
	 * 根据预算Id进行预算模板[提交]操作
	 * 		DESC：	确认操作,只有Excel导入返回状态为"03处理完成",该submit按钮才在List页面可见,
	 * 				该操作将dataFlag更改为"04",更改后状态下可以开始进行预算申报
	 * @param budgetId	预算模板ID
	 */
	public void submitBudget(String budgetId){
		CommonLogger.info("预算模板提交操作:提交模板,主键信息("+budgetId+"),BudgetPlanService,submitBudget");
		BudgetPlanBean budget = budgetDAO.getBudgetById(budgetId);
		budgetDAO.submitBudget(budget);
	}
	
	/**
	 * 分页查找预算模板
	 * @param budget
	 * @return
	 */
	public List<BudgetPlanBean> list(BudgetPlanBean budget){
		budget.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());	//【limao 3-31要求添加List查找前通过org2Code过滤】
		budget.setOrg2Name(WebHelp.getLoginUser().getOrg2Name());
		BudgetPlanDAO pageDAO = PageUtils.getPageDao(budgetDAO);
		return pageDAO.list(budget);
	}
	
	/**
	 * 调用导入Excel操作
	 * @param 	budget	导入Excel相关的预算模板
	 * @return			Excel导入操作是否已执行(与导入结果无关)
	 * @throws 	Exception
	 */
	public boolean importExcel(BudgetPlanBean budget) throws Exception{
		boolean flag = false;
		String excelUrl = budget.getServerFile();
		String filePath = excelUrl.substring(0,excelUrl.lastIndexOf("/")+1);
		String fileSecName = excelUrl.substring(excelUrl.lastIndexOf("/")+1,excelUrl.length());
		//2.Excel导入参数处理
		String tmpltId = budget.getTmpltId();
		String excelDesc = "";
		if("0".equals(budget.getDataAttr())){
			//资产类
			budget.setConfigId("BUDGET_TEMP01");
			excelDesc = "Import Excel "+",预算模板ID:【"+tmpltId+"】";
		}else if("1".equals(budget.getDataAttr())){
			//费用类
			budget.setConfigId("BUDGET_TEMP02");
			excelDesc = "Import Excel "+",预算模板ID:【"+tmpltId+"】";
		}
		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("tmpltId", tmpltId);
		//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
		importGenernalDeal.execute(filePath, fileSecName, excelDesc, new String[]{budget.getConfigId()}, beans);	
		//导入操作已进行=true
		flag = true;
		return flag;
	}
	
	/**
	 * 删除预算模板及其关联的可用机构信息、关联Excel数据
	 * @param budgetId
	 */
	public void deleteBudgetPlanAndRel(String budgetId){
		CommonLogger.info("预算模板删除操作:删除模板及其相关操作,主键信息("+budgetId+"),BudgetPlanService,deleteBudgetPlanAndRel");
		//1.将上传的源Excel模板文件删除掉
		BudgetPlanBean budget = budgetDAO.getBudgetById(budgetId);
			//通过指定路径找到上传源文件，并删除该文件
		File sourceFile = new File(budget.getServerFile());			
		if(sourceFile.exists()){
			sourceFile.delete();
		}
		//2.删除该模板中存在于DB的Excel数据
		budgetDAO.deleteBudgetDetailInfo(budgetId);
		//3.删除该模板关联的责任中心信息
		budgetDAO.deleteBudgetRel(budgetId);
		//4.删除模板文件
		budgetDAO.deleteBudgetPlan(budgetId);
	}
	
	/**
	 * 修改更新预算模板的主体信息
	 * @param budget
	 */
	public void updateBudgetPlan(BudgetPlanBean budget){
		budgetDAO.updateBudgetPlan(budget);
	}
	
	/**
	 * 修改预算模板操作：更新模板及其关联关系
	 * @param budget
	 */
	public void updateBudgetPlanAndRel(BudgetPlanBean budget){
		CommonLogger.info("预算模板修改操作:修改预算模板及关联信息,主键信息("+budget.getTmpltId()+"),BudgetPlanService,updateBudgetPlanAndRel");
		//1.删除预算模板与可用机构的关系
		budgetDAO.deleteBudgetRel(budget.getTmpltId());
		//2.新增预算模板与可用机构的关系
		addBudgetRelDuty(budget);
	}
	
	/**
	 * 新增预算模板与可用机构的关系
	 * @param budget
	 */
	public void addBudgetRelDuty(BudgetPlanBean budget){
		CommonLogger.info("预算模板新增或修改模板时关联可用责任中心操作:主键信息("+budget.getTmpltId()+"),BudgetPlanService,addBudgetRelDuty");
		BudgetTmpltDutyBean budgetRelDuty = new BudgetTmpltDutyBean();
		//获取页面上勾选的可用机构列表信息
		String[] budgetDutyList = budget.getAvailableOrgList().split(",");
		budgetRelDuty.setTmpltId(budget.getTmpltId());			//插入关联预算模板关系
		//循环插入关系操作
		for(int i=0;i<budgetDutyList.length;i++){
			budgetRelDuty.setDutyCode(budgetDutyList[i]);		//取得每一个可用机构信息
			budgetDAO.addBudgetDuty(budgetRelDuty);
		}
	}
	
	/**
	 * 预算模板明细
	 * @param budgetId	预算模板ID
	 * @return
	 */
	public BudgetPlanBean view(String budgetId){
		CommonLogger.info("预算模板明细查看操作:主键信息("+budgetId+"),BudgetPlanService,view");
		return budgetDAO.view(budgetId);
	}
	
	/**
	 * 预算模板及其关联信息明细查看(包括预算模板中Excel文件内容详情+模板可用机构信息)
	 * @param budgetId	预算模板ID
	 * @return
	 */
	public Map<String, Object> mapView(String budgetId){
		CommonLogger.info("预算模板及其关联信息明细查看操作:用于查询获取多个bean并Set到前端页面中,主键信息("+budgetId+"),BudgetPlanService,mapView");
		Map<String, Object> objMap = new HashMap<String, Object>();

		BudgetPlanBean budget = budgetDAO.getBudgetById(budgetId);
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		
		List<BudgetTempDetailBean> budgetHeaderDetail =  budgetDAO.getBudgetPlanHeaderDetail(budgetId);
		List<BudgetTempDetailBean> budgetBodyDetail = budgetDAO.getBudgetPlanBodyDetail(budgetId,dutyCode);
		
		//将Excel中RowInfo的数据split赋值给BudgetTempDetail的各个属性
		if("0".equals(budget.getDataAttr())){
			//资产类模板处理
			if(budgetHeaderDetail.size()>0){
				rowInfoConvertToZC(budgetHeaderDetail.get(0));
			}
			for(int i=0;i<budgetBodyDetail.size();i++){
				rowInfoConvertToZC(budgetBodyDetail.get(i));
			}
		}else if("1".equals(budget.getDataAttr())){
			//费用类模板处理
			if(budgetHeaderDetail.size()>0){
				rowInfoConvertToFee(budgetHeaderDetail.get(0));
			}
			for(int i=0;i<budgetBodyDetail.size();i++){
				rowInfoConvertToFee(budgetBodyDetail.get(i));
			}
		}
		
		objMap.put("budgetPlan", budgetDAO.view(budgetId));		//预算模板Budget的信息
		objMap.put("budgetHeaderDetail", budgetHeaderDetail);		//Excel表格头信息
		objMap.put("budgetBodyDetail", budgetBodyDetail);	//Excel表格详情信息
		
		//保存预算模板对应的可用机构信息
		List<BudgetTmpltDutyBean> orgsList = this.getBudgetOrgs(budgetId);
		String orgIds = "";
		String orgNames = "";
		for(int i=0;i<orgsList.size();i++){
			if(i == (orgsList.size()-1)){
				orgIds += orgsList.get(i).getDutyCode();
				orgNames += orgsList.get(i).getDutyName();
			}else{
				orgIds += orgsList.get(i).getDutyCode()+",";
				orgNames += orgsList.get(i).getDutyName()+",";
			}
		}
		objMap.put("orgIds",orgIds );
		objMap.put("orgNames",orgNames );
		
		return objMap;
	}
	
	/**
	 * 查找Budget相关的Orgs列表信息
	 * @param budgetId
	 * @return			相关的Orgs列表
	 */
	public List<BudgetTmpltDutyBean> getBudgetOrgs(String budgetId){
		return budgetDAO.getBudgetOrgs(budgetId);
	}
	
	/**
	 * 更新预算模板的状态(用于导入Excel时的前后逻辑处理)
	 * @param tmpltId	预算模板ID
	 * @param dataFlag	要更新为的状态
	 */
	public void updateBudgetStatus(String tmpltId,String dataFlag){
		BudgetPlanBean budget = budgetDAO.getBudgetById(tmpltId);
		budget.setDataFlag(dataFlag);
		budgetDAO.updateBudgetStatus(budget);
	}
	
	/**
	 * 根据Excel导入任务结果更新状态(线程调用End()之后)
	 * @param batchNo	Excel导入任务流水ID	
	 * @param dataFlag	要更新为的状态
	 */
	public void updateTaskLoadStatus(String batchNo , String dataFlag){
		TaskLoadBean taskLoad = new TaskLoadBean();
		taskLoad.setTaskId(batchNo);
		taskLoad.setDataFlag(dataFlag);
		budgetDAO.updateTaskLoadStatus(taskLoad);
	}
	
	/**
	 * 将Excel数据保存到数据库
	 * @param budgetTempDetail
	 */
	public void insertBudgetTempDetail(BudgetTempDetailBean budgetTempDetail){
		budgetDAO.insertBudgetTempDetail(budgetTempDetail);
	}
	
	/**
	 * 文件复制的工具类
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public void fileCopyByBufferStream(File source, File target) throws IOException {  
        InputStream fis = null;  
        OutputStream fos = null;  
        try {  
            fis = new FileInputStream(source);  
            fos = new FileOutputStream(target);  
            byte[] buf = new byte[4096];  
            int i;  
            while ((i = fis.read(buf)) != -1) {  
                fos.write(buf, 0, i);  
            }  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	fis.close();
        	fos.close();
        }  
    }
	
	 /**
     * 获取复制的目标路径
     * @param panFus	盘符数组
     * @param subPath	子路径地址
     * @return
     */
    public static String getTargetPath(String[] panFus , String subPath){
    	//判断盘符是否存在
    	String targetDir = "";								//复制目标路径(=盘符+自路径)
    	boolean findPanFlag = false;						//是否找到盘符标识
		for(int i=0;i<panFus.length;i++){
    		if(judgePlateFlag(new File(panFus[i]))){
    			if(!findPanFlag){
    				targetDir = panFus[i]+subPath;
    			}
    			findPanFlag = true;
    		}
    	}
    	//判断文件夹路径是否存在(不存在则创建)
    	File CopyFileDir = new File(targetDir);
    	if(!judgePlateFlag(CopyFileDir)){
    		CopyFileDir.mkdir();
    	}
    	return targetDir;
    }
    
    /**
     * 判断文件路径或客户端系统盘符是否存在
     */
    public static boolean judgePlateFlag(File panFu){
    	if(panFu.exists()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 如果文件路径不存在，则创建文件全部路径
     * @param filePath
     */
    public void createFilePath(File filePath){
    	if(!judgePlateFlag(filePath)){
    		filePath.mkdirs();
    	}
    }
	
	//【工具方法】判断字符串是否能转换为数值类型
	public static boolean judgeStrToNum(String str){
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 查询年初预算个数
	 * @param budget
	 * @return
	 */
	public int checkYearFirstBudgetPlan(String selectedYear){
		BudgetPlanBean budget = new BudgetPlanBean();
		budget.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		budget.setDataYear(selectedYear);
		int count = budgetDAO.checkYearFirstBudgetPlan(budget);
		return count;
	}
	
	/**
	 * 将RowInfo的信息拆分并赋值于资产bean
	 * @param zcBean
	 */
	public void rowInfoConvertToZC(BudgetTempDetailBean zcBean){
		String[] infoStr = zcBean.getRowInfo().split("\\|");
		zcBean.setMontCode(infoStr[0]);
		zcBean.setPropertyType(infoStr[1]);
		zcBean.setAcCode(infoStr[2]);
		zcBean.setColumnOne(infoStr[3]);
		zcBean.setColumnTwo(infoStr[4]);
		zcBean.setMatrCode(infoStr[5]);
		zcBean.setMatrName(infoStr[6]);
		if(judgeStrToNum(infoStr[7])){
			//单价信息(表中List时：int类型)
			if(Integer.parseInt(infoStr[7])>0){
				zcBean.setReferPrice(Integer.parseInt(infoStr[7]));
			}
		}else{
			//单价信息(表头数据时：字符串类型)
			zcBean.setReferPriceHeader(infoStr[7]);
		}
		zcBean.setReferType(infoStr[8]);
		if(infoStr.length>9){
			zcBean.setScrapNum(infoStr[9]);
			zcBean.setDemandNum(infoStr[10]);
			zcBean.setBudgetAmount(infoStr[11]);
			zcBean.setMemoDescr(infoStr[12]);
		}
		
	}
	
	/**
	 * 将RowInfo的信息拆分并赋值于费用bean
	 * @param feeBean
	 */
	public void rowInfoConvertToFee(BudgetTempDetailBean feeBean){
		String[] infoStr = feeBean.getRowInfo().split("\\|");
		feeBean.setMontCode(infoStr[0]);
		feeBean.setJyZm(infoStr[1]);
		feeBean.setAcCode(infoStr[2]);
		feeBean.setColumnOne(infoStr[3]);
		feeBean.setColumnTwo(infoStr[4]);
		feeBean.setMatrCode(infoStr[5]);
		feeBean.setMatrName(infoStr[6]);
		if(infoStr.length>7){
			feeBean.setBudgetAmount(infoStr[7]);
			feeBean.setMemoDescr(infoStr[8]);
		}
	}
	
	/**
	 * 下载导出预算的基础模板(资产和费用类)
	 * 		调用Excel导出功能组件
	 * 		@param 	dataAttr(资产类0/费用类1)
	 * 		@return 	返回生成的任务ID
	 * 		@throws 	Exception
	 */
	public String budgetBasicTmpExport(String dataAttr) throws Exception{
		CommonLogger.info("预算模板-基础模板导出操作:下载文件为预算新增操作时Excel导入的基础模板,基础模板类型:("+dataAttr+"),BudgetPlanService,budgetBasicTmpExport");
		String downloadPath =WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR");		//导出文件的路径
		//创建导出路径
		this.createFilePath(new File(downloadPath));		
		
		String org21Code = "";		//监控指标所属机构，用于关联查询归口部门
		String montType = null;
		String destFile = "";						//文件导出详细路径+文件名
		String excelDesc = "";
		String exportConf = "";
		
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		if("0".equals(dataAttr)){
			//资产类导出参数设置(监控类型=1，取一级行，下载文件名做相应修改)
			montType = "1";
			org21Code = WebHelp.getLoginUser().getOrg1Code();
			destFile = downloadPath+"/.xlsx";					//【导出文件目标地址前置路径+导出文件的后缀格式(xlsx类型)】
			excelDesc = "资产类预算-基础模板下载";
			exportConf = "BUDGET_ZCBASIC_TEMP";			//配置文件中定义
		}else if("1".equals(dataAttr)){
			//费用类导出参数设置(监控类型=3，取二级行，下载文件名做相应修改)
			montType = "3";
			org21Code = WebHelp.getLoginUser().getOrg2Code();
			destFile = downloadPath+"/.xlsx";
			excelDesc = "费用类预算-基础模板下载";
			exportConf = "BUDGET_FEEBASIC_TEMP";				//配置文件中定义
		}
		paramsMap.put("dataAttr", dataAttr);
		paramsMap.put("org21Code", org21Code);
		paramsMap.put("montType", montType);
		//参数说明：任务描述、export-config调用配置、输出目标路径+文件名、导出所需的参数传递
		return exportDeal.execute(excelDesc,exportConf,destFile,paramsMap);
	}
	
	/**
	 * 基础模板导出数据查询(Excel导出Deal中调用)
	 * 		描述：主要查询预算监控指标、物料编码、物料名称；用于导出Excel时写到Excel中去
	 * @param org21Code
	 * @param montType
	 * @return
	 */
	public List<BudgetTempDetailBean> exportBasicBudgetInfo(String org21Code , String montType) {
		return budgetDAO.exportBasicBudgetInfo(org21Code , montType);
	}
	
	/**
	 * 执行预算模板Excel导出操作
	 * @param destFile	Excel导出保存地址
	 * @param budget	需要导出的预算模板
	 * @return
	 * @throws Exception
	 */
	public String budgetTempExport(BudgetPlanBean budget) throws Exception{
		CommonLogger.info("预算模板Excel导出操作:用于预算申报前模板导出,主键信息("+budget.getTmpltId()+",导出类型:"+budget.getDataAttr()+"),BudgetPlanService,budgetTempExport");
//		budget = budgetDAO.getBudgetById(budget.getTmpltId());
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		String sourceFileName = budget.getSourceFileName();
		createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+"-"+budget.getTmpltId()+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dutyCode", dutyCode);
		map.put("tmpltId", budget.getTmpltId());	//拿到指定预算模板信息
		if("0".equals(budget.getDataAttr())){
			//资产类
			map.put("dataAttr", budget.getDataAttr());
			return exportDeal.execute(sourceFileName+"【"+budget.getTmpltId()+"】-预算模板下载", "BUDGET_ZC_TEMP", destFile , map);
		}else if("1".equals(budget.getDataAttr())){
			//费用类
			map.put("dataAttr", budget.getDataAttr());
			return exportDeal.execute(sourceFileName+"【"+budget.getTmpltId()+"】-预算模板下载", "BUDGET_FEE_TEMP", destFile , map);
		}else{
			return null;
		}
	}
	
	/**
	 * 预算模板导出数据查询(资产类+费用类)，Excel导出Deal中调用
	 * 		描述：将数据库中的RowInfo数据信息取到，写到导出的Excel中
	 * @param dutyCode
	 * @param tmpltId
	 * @return
	 */
	public List<BudgetTempDetailBean> getBudgetTmpExcelExpInfo(String dutyCode , String tmpltId){
		return budgetDAO.getBudgetTmpExcelExpInfo(dutyCode, tmpltId);
	}
	
	/**
	 * Ajax校验物料-监控之间的维护状态
	 * 		描述：Ajax校验模板下载操作时，是否存在指标中物料信息未维护到监控指标的条目，并返回未维护的条目个数
	 * @param dataAttr
	 * @return
	 */
	public int validateMaintainByMatrMont(String dataAttr){
		String org21Code = "";
		String montType = "";
		if("0".equals(dataAttr)){
			org21Code = WebHelp.getLoginUser().getOrg1Code();
			montType = "1";
		}else if("1".equals(dataAttr)){
			org21Code = WebHelp.getLoginUser().getOrg2Code();
			montType = "3";
		}
		int resultCount = budgetDAO.ajaxCountDisMaintainMont(org21Code , montType);
		return resultCount;
	}
}
