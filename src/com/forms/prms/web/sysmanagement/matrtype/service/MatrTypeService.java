package com.forms.prms.web.sysmanagement.matrtype.service;

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
import com.forms.prms.tool.exceltool.loadthread.ExcelImportGenernalDeal;
import com.forms.prms.web.sysmanagement.matrtype.dao.MatrTypeDAO;
import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;

@Service
public class MatrTypeService
{
	@Autowired
	private MatrTypeDAO mDao;
	@Autowired
	public ExcelImportGenernalDeal importGenernalDeal;		//Excel导入Service

	public List<MatrType> list(MatrType mt)
	{
		MatrTypeDAO pageDAO = PageUtils.getPageDao(mDao);
		CommonLogger.info("查询物料类别列表,MatrTypeService,list");
		return pageDAO.list(mt);
	}
	
	
	public List<MatrType> listCglCode(MatrType mt)
	{
		MatrTypeDAO pageDAO = PageUtils.getPageDao(mDao);
		CommonLogger.info("费用核算码列表,MatrTypeService,listCglCode");
		return pageDAO.listCglCode(mt);
	}
	public void addPrepaidCode(MatrType mt)
	{
		mDao.addPrepaidCode(mt);
		CommonLogger.info("费用核算码新增,MatrTypeService,addPrepaidCode");
	}
	public void updatePrepaidCode(MatrType mt)
	{
		mDao.updatePrepaidCode(mt);
		CommonLogger.info("费用核算码更新,MatrTypeService,updatePrepaidCode");
	}
	
	/**
	 * 明细
	 * @param matrCode
	 * @return
	 */
	public MatrType view(String matrCode)
	{
		CommonLogger.info("查询物料类别明细，主键信息（物料编码："+matrCode+"）,MatrTypeService,view");
		return mDao.view(matrCode);
	}
	
	
	
	
   public String imp(MatrType mt) throws Exception
   {
		String excelUrl = mt.getPath();
		String filePath = excelUrl.substring(0,excelUrl.lastIndexOf("/")+1);
		String fileSecName = excelUrl.substring(excelUrl.lastIndexOf("/")+1,excelUrl.length());
		//添加调用Excel导入组件时的传递参数
		Map<String,String> beans = new HashMap<String,String>();
		beans.put("matrCode", mt.getMatrCode());
		beans.put("matrName", mt.getMatrName());
		beans.put("cglCode", mt.getCglCode());
		beans.put("matrUnit", mt.getMatrUnit());
		beans.put("lossCode", mt.getLossCode());
		beans.put("matrCode1code", mt.getMatrCode1code());
		beans.put("matrType", mt.getMatrType());
		beans.put("provisionCode", mt.getProvisionCode());
		beans.put("prepaidCode", mt.getPrepaidCode());
		beans.put("isPublicityPay", mt.getIsPublicityPay());
		beans.put("memo",mt.getMemo());
		beans.put("impTempType","物料信息导入模板");
		//Excel文件路径、Excel重命名文件名、Excel描述、ConfigId(数组)、携带参数
		try {
			importGenernalDeal.execute(filePath, fileSecName, "物料信息导入", new String[]{"MATR_TYPE"}, beans);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("物料信息导入出现异常！MatrTypeService,imp");
			return e.getMessage();
		}
	     
   }
   /**
    * 核算码
    * @param cglCode
    * @return
    */
   public MatrType propertyList(String cglCode){
	   CommonLogger.info("查询核算码，MatrTypeService,propertyList");
	   return mDao.propertyList(cglCode);
   }
   
   
   /**
    * 核算码
    * @param cglCode
    * @return
    */
   public int checkCglCode(String cglCode){
	   CommonLogger.info("查询核算码，MatrTypeService,checkCglCode");
	   return mDao.checkCglCode(cglCode);
   }
   
   
   /**
    * 物料类型选择页面
    * @param m
    * @return
    */
   public List<MatrType> matrTypeOption(MatrType m){
	   PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
	   MatrTypeDAO pageDAO = PageUtils.getPageDao(mDao);
	   m.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
	   CommonLogger.info("查询物料类别(物料编码："+m.getMatrCode()+";物料名称："+m.getMatrName()+"),MatrTypeService,matrTypeOption");
	   return pageDAO.matrTypeOption(m);
   }
 
   
   /**
    * 物料新增
    * @param m
    * @return
    */
   @Transactional(rollbackFor = Exception.class)
   public String insert(MatrType m){
	   
	   if (m.getMatrCode()=="") {
		CommonLogger.info("新增物料类别失败，物料编码不能为空!,MatrTypeService,insert");   
		return "新增物料类别失败，物料编码不能为空!,MatrTypeService,insert";
	   }
	   if (m.getMatrName()=="") {
	    CommonLogger.info("新增物料类别失败，物料名称不能为空!,MatrTypeService,insert");   
		return "新增物料类别失败，物料名称不能为空!,MatrTypeService,insert";
	   }
	   m.setMatrCode(m.getMatrCode());
	   m.setMatrName(m.getMatrName());
	   m.setCglCode(m.getCglCode());
	   m.setMatrUnit(m.getMatrUnit());
	   m.setLossCode(m.getLossCode());
	   m.setMatrCode1code(m.getMatrCode1code());
	   m.setMatrType("1".equalsIgnoreCase(m.getMatrCode().substring(0,1))?"1":"3");
	   m.setProvisionCode(m.getProvisionCode());
	   m.setPrepaidCode(m.getPrepaidCode());
	   m.setIsPublicityPay(m.getIsPublicityPay());
	   m.setMemo(m.getMemo());
	   m.setIsGDZC(m.getIsGDZC());//固定资产
	   m.setIsOrder(m.getIsOrder());//是否是订单类资产
	   
	   if("1".equals(m.getMatrType())){
		   m.setIsNotinfee("N");
		   m.setIsPrepaidProvision("N");
	   } else {
		   m.setIsGDZC("N");//固定资产
		   m.setIsFcwl("N");//是否是房产类物料
	   }
	   
	   mDao.insertMatr(m);
	   
	   //费用类才需要增加待摊核算码
	   if("3".equals(m.getMatrType())){
			// 增加核算码
			MatrType mt = mDao.propertyList(m.getCglCode());
			// 如果此核算码不存在于表中则增加一条记录
			if (mt == null) {
				mDao.addCglCode(m);
			}
	   }
	   CommonLogger.info("新增物料类别(物料编码："+m.getMatrCode()+";物料名称："+m.getMatrName()+"),MatrTypeService,insert");
	   
	   return null;
   }

    public static MatrTypeService getInstance() {
    return SpringUtil.getBean(MatrTypeService.class);
}

	public void importMt(List<MatrType> matrTypes) {
		CommonLogger.info("导入物料信息，MatrTypeService,importMt");
		mDao.importMt(matrTypes);
	}

    /**
     * 获取物料核算码
     * @return
     */	
	public List getcglList() {
		// TODO Auto-generated method stub
		return mDao.cglList();
	}
}
