package com.forms.prms.web.amortization.fmsMgr.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.FTPUtils;
import com.forms.prms.tool.fms.parse.domain.FTPBean;
import com.forms.prms.web.amortization.fmsMgr.dao.FmsMgrDAO;
import com.forms.prms.web.amortization.fmsMgr.domain.DealCountBean;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsCglBatch;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsDownload;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsMgr;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsUpload;
import com.forms.prms.web.monthOver.service.MonthOverService;

@Service
public class FmsMgrService {

	@Autowired
	private FmsMgrDAO dao;
	@Autowired
	private MonthOverService monthOverService;
	
	@Autowired
	public DBFileOperUtil dbFileUtil;
	/**
	 * 订单付款
	 * @return
	 */
	public FmsMgr getCntInfo(){
		String monDataFlag = monthOverService.getMaxDataFlag();//拿到月结的状态
		String org1Code = WebHelp.getLoginUser().getOrg1Code();//登录人所在的一级行号
		
		//应付发票
		CommonLogger.info("应付发票待上送信息查询！,FmsMgrService,getCntInfo(),【getPayCntInfo(),一级行号："+org1Code+",月结状态："+monDataFlag+"】");
		FmsMgr fmsMgr = new FmsMgr();
		fmsMgr = dao.getPayCntInfo(org1Code,monDataFlag);
		if(fmsMgr.getPaySumAmt().compareTo(new BigDecimal(0)) != 0){
			List<FmsMgr> fmsMgr1 = dao.getPaySumAmt(org1Code,monDataFlag);
			String str ="";
			for(int i=0;i<fmsMgr1.size();i++){
				FmsMgr fm = fmsMgr1.get(i);
				str += fm.getInvoiceType()+"("+fm.getPaySumAmt()+")";
				if(i != fmsMgr1.size()-1){
					str+="&";
				}
			}
			fmsMgr.setPaySumStr(str);
		}
		fmsMgr.setPayCnt(fmsMgr.getPayCnt().subtract(fmsMgr.getPayNotSendCnt()));
		fmsMgr.setPayOuCnt(fmsMgr.getPayOuCnt().subtract(fmsMgr.getPayNotSendOuCnt()));
		
	/*	//预付款核销
		CommonLogger.info("预付款核销待上送信息查询！,FmsMgrService,getCntInfo(),【getAdvCntInfo(),一级行号："+org1Code+"】");
		FmsMgr advFmsMgr = dao.getAdvCntInfo(org1Code);
		fmsMgr.setAdvCnt(advFmsMgr.getAdvCnt().subtract(advFmsMgr.getAdvNotSendCnt()));
		fmsMgr.setAdvOuCnt(advFmsMgr.getAdvOuCnt().subtract(advFmsMgr.getAdvNotSendOuCnt()));
		fmsMgr.setAdvSumAmt(advFmsMgr.getAdvSumAmt());
		fmsMgr.setAdvNotSendCnt(advFmsMgr.getAdvNotSendCnt());*/
		
		//订单
		CommonLogger.info("订单待上送信息查询！,FmsMgrService,getCntInfo(),【getOrderCntInfo(),一级行号："+org1Code+"】");
		FmsMgr orderFmsMgr = dao.getOrderCntInfo(org1Code);
		fmsMgr.setOrderCnt(orderFmsMgr.getOrderCnt().subtract(orderFmsMgr.getOrdNotSendCnt()));
		fmsMgr.setOrderOuCnt(orderFmsMgr.getOrderOuCnt().subtract(orderFmsMgr.getOrdNotSendOuCnt()));
		fmsMgr.setOrderSumAmt(orderFmsMgr.getOrderSumAmt());
		fmsMgr.setOrdNotSendCnt(orderFmsMgr.getOrdNotSendCnt());
		
		return fmsMgr;
	}

	/**
	 * fms下载查询
	 * @param bean
	 * @return
	 */
	public List<FmsDownload> getFmsDownLoadList(FmsDownload bean) {
		
		List<FmsDownload> list = null;
		FmsMgrDAO pagDao = PageUtils.getPageDao(dao);
		//转换日期格式
		bean.setStartDate(DateUtil.formatDateStr(bean.getStartDate(), "YYYYMMDD"));
		bean.setEndDate(DateUtil.formatDateStr(bean.getEndDate(), "YYYYMMDD"));	
		CommonLogger.info("查询FMS下载文件列表！,FmsMgrService,getFmsDownLoadList()");
		list = pagDao.getFmsDownloadList(bean);
		return list;
	}
	
	/**
	 * fms下载查询(根据批次号)
	 * @param bean
	 * @return
	 */
	public FmsDownload getFmsDownLoad(FmsDownload bean) {
		CommonLogger.info("查询FMS下载文件！,FmsMgrService,getFmsDownLoad()【batchNo:"+bean.getBatchNo()+";】");
		return dao.getFmsDownload(bean);
	}
	
	/**
	 * fms上传查询
	 * @param bean
	 * @return
	 */
	public List<FmsUpload> getFmsUpLoadList(FmsUpload bean) {
		
		bean.setUser(WebHelp.getLoginUser());
		
		CommonLogger.info("查询上传文件列表！,FmsMgrService,getFmsUpLoadList()");
		List<FmsUpload> list = null;
		FmsMgrDAO pagDao = PageUtils.getPageDao(dao);
		
		//转换日期格式
		bean.setStartDate(DateUtil.formatDateStr(bean.getStartDate(), "YYYYMMDD"));
		bean.setEndDate(DateUtil.formatDateStr(bean.getEndDate(), "YYYYMMDD"));	
		
		list = pagDao.getFmsUploadList(bean);
		return list;
	}
	
	public List<FmsUpload> getOuList(FmsUpload bean) 
	{
		bean.setUser(WebHelp.getLoginUser());
		List<FmsUpload> list = null;
		list = dao.getOuList(bean);
		return list;
	}

	/**
	 * fms上传查询(根据批次号)
	 * @param bean
	 * @return
	 */
	public FmsUpload getFmsUpLoad(FmsUpload bean) {
		CommonLogger.info("查询FMS上传文件！,FmsMgrService,getFmsUpLoad()【batchNo:"+bean.getBatchNo()+";】");
		return dao.getFmsUpload(bean);
	}
	
	/**
	 * 预提待摊
	 * @param bean
	 * @return
	 */
	public List<FmsCglBatch> getCglBatchList(FmsCglBatch bean){
		//获取登陆用户所属一级行代码
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		//转换日期格式
		bean.setStartDate(DateUtil.formatDateStr(bean.getStartDate(), "YYYYMMDD"));
		bean.setEndDate(DateUtil.formatDateStr(bean.getEndDate(), "YYYYMMDD"));	
		
		CommonLogger.info("查询预提待摊任务列表！,FmsMgrService,getCglBatchList(),【Org1Code:"+bean.getOrg1Code()+"】");
		FmsMgrDAO pageDao = PageUtils.getPageDao(dao);
		return pageDao.getCglBatchList(bean);
	}
	
	
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param filePath
	 * @throws Exception 
	 */
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception {
		this.dowloadLocal(filePath, response);
	}
	
	/**
	 * 本地文件下载
	 * @param templateFilePath
	 * @param response
	 * @throws Exception
	 */
	private void dowloadLocal(String filePath, HttpServletResponse response) throws Exception
	{
		response.setContentType("application/x-msdownload;charset=UTF-8");
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		try{	
			String fileRealPath = filePath;
			fileRealPath = filePath.replace("\\", "/");
			File file = new File(fileRealPath);
			if(!file.exists())
			{
				try
				{
					dbFileUtil.downloadFileFromFTP(fileRealPath);
				}
				catch (Exception e) 
				{
					CommonLogger.error("[集成文件下载操作]：从FTP下载文件到本地服务器时出现【异常】，请检查!	"+this.getClass().getName()+ "	--	findFileFromFTP()" + e.getMessage());
				}
			}
			fis = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			bos = new BufferedOutputStream(out);
			byte[] b = new byte[8192];
			int data = 0;
			while ((data = fis.read(b)) != -1)
			{
				bos.write(b, 0, data);
			}
			bos.flush();
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{
				if(bos != null){
					bos.close();
				}
				if(fis != null){
					fis.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 检查应付发票是否存在异常
	 * @return
	 */
	public Boolean check31Upload(){
		CommonLogger.info("检查应付发票是否存在未上传数据！,FmsMgrService,check31Upload()");
		if(!"0".equals(dao.check31Upload(WebHelp.getLoginUser().getOrg1Code()))){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查当月冲销预提的状态
	 * @return
	 */
	public Boolean checkProvision(){		
		CommonLogger.info("检查当月冲销预提的状态！,FmsMgrService,checkProvision()");
		FmsCglBatch batch = dao.checkProvision(WebHelp.getLoginUser().getOrg1Code());
		if(batch != null && !"02".equals(batch.getDataFlag())){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查做冲销预提时系统时间是否与任务列表中年月一致
	 * @param yyyyMM
	 * @return
	 */
	public Boolean checkProvisionMonth(String yyyyMM){
		CommonLogger.info("检查做冲销预提时系统时间是否与任务列表中年月一致！,FmsMgrService,checkProvisionMonth()");
		String re = dao.checkProvisionMonth(yyyyMM);
		if("1".equals(re)){//当前系统时间等于任务时间则通过
			return true ;
		}
		return false;
	}
	
	/**
	 * 检查当前系统时间是否大于任务时间 
	 * @param yyyyMM
	 * @return
	 */
	public Boolean checkPPMonth(String yyyyMM){
		CommonLogger.info("检查当前系统时间是否大于任务时间！,FmsMgrService,checkPPMonth()");
		String re = dao.checkPPMonth(yyyyMM);
		if("1".equals(re)){//当前系统时间大于任务时间，则通过。
			return true;
		}
		return false;
	}

	/**
	 * 查询log日志信息
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String querLog(String batchNo, String tradeType) {
		CommonLogger.info("查询log日志信息！,FmsMgrService,querLog()【batchNo:"+batchNo+";tradeType:"+tradeType+";】");
		String tableName = "";
		//如果tradeType第一位是3则是校验文件log,否则是结果文件
		if(tradeType.charAt(0)== '3'){
			tableName = "TI_UPLOAD";
		}else{
			tableName = "TI_DOWNLOAD";
		}
		String infoLog = dao.querLog(batchNo,tradeType,tableName);
		return infoLog;
	}

	public DealCountBean getDealCount(String org1Code) throws Exception {
		CommonLogger.info("查询未处理fms文件数量！,FmsMgrService,getDealCount()【org1Code:"+org1Code+"】");
		int count11 = getWaitDealcount("11");
		int count12 = getWaitDealcount("12");
		int count13 = getWaitDealcount("13");
		int count21 = getWaitDealcount("21");
		int count22 = getWaitDealcount("22");
		int count23 = getWaitDealcount("23");
		int count25 = getWaitDealcount("25");
		int count31 = getWaitDealcount("31");
//		int count32 = getWaitDealcount("32");
		int count33 = getWaitDealcount("33");
		int count34 = getWaitDealcount("34");
		
		DealCountBean bean = new DealCountBean();
		bean.setCount11(count11);
		bean.setCount12(count12);
		bean.setCount13(count13);
		bean.setCount21(count21);
		bean.setCount22(count22);
		bean.setCount23(count23);
		bean.setCount25(count25);
		bean.setCount31(count31);
//		bean.setCount32(count32);
		bean.setCount33(count33);
		bean.setCount34(count34);
		
		return bean;
	}
	
	private int getWaitDealcount(String tradeType) throws Exception
	{
		FTPBean ftp = new FTPBean();
		List<String> fileNameList = new ArrayList<String>();
		String resultReg = "";
		String regStr = "";
		
		if(tradeType.startsWith("3"))
		{
			resultReg = FMSConfig.getFMS(tradeType).getFile().getFileName().replace("{oucode}_{date8}_{seq}.TXT", "*");
			regStr = resultReg.replace("*", ".*");
		}
		else
		{
			resultReg = FMSConfig.getFMS(tradeType).getFile().getFileName().replace("{date8}", "*");
			regStr = resultReg.replace("*.gz", ".*");
		}
		final String matchReg = regStr;
		
		if("1".equals(ftp.getDownloadFileLocal()))//从ftp下载
	    {
			fileNameList = FTPUtils.getOrgFile(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
							ftp.getDownloadFolder(tradeType), resultReg);
	    }
		else//本地
		{
			fileNameList = FTPUtils.getOrgFile(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
					ftp.getDownloadFolder(tradeType), resultReg);
//			File[] files = new File(SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER")).listFiles(new FileFilter() {
//				@Override
//				public boolean accept(File pathname) {
//					String pathFileName = pathname.getName();
//					return pathFileName.matches(matchReg);
//				}
//			});
//			for(int i=0;i<files.length;i++){
//				fileNameList.add(files[i].getName());
//			}
		}
		
		return null==fileNameList?0:fileNameList.size();
	}
	
	/**
	 * 初始化预提待摊任务
	 */
	@Transactional(rollbackFor = Exception.class)
	public void initCglBatch(){
		CommonLogger.info("初始化预提待摊任务！,FmsMgrService,initCglBatch()");
		dao.initCglBatchPriv(WebHelp.getLoginUser().getOrg1Code());
		dao.initCglBatchPP(WebHelp.getLoginUser().getOrg1Code());
	}
	
	
}
