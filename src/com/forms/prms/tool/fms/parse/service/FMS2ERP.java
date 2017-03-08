package com.forms.prms.tool.fms.parse.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.MD5Util;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.FMSDownloadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.FTPUtils;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.domain.FTPBean;
import com.forms.prms.tool.fms.receive.service.ReceiveService;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.util.GzipUtil;


@Service
public class FMS2ERP {

	@Autowired
	private FMSService service;
	@Autowired
	private ReceiveService receiveService;
	
//	private final String FMSID = "FMS2ERP";
	
	
	/**
	 * 下载明细数据新增处理任务
	 * @param tradeTypes
	 * @param date
	 * @throws Exception
	 */
	public void execute(String tradeTypes,String FMSID) throws Exception{
		
		if(StringUtils.isEmpty(tradeTypes)){
			tradeTypes = FMSConfig.getFMS(FMSID).getTradeTypes();
		}
		CommonLogger.info("下载明细数据新增处理任务!,FMS2ERP,execute()【tradeTypes:"+tradeTypes+";FMSID:"+FMSID+";】");
		String date = service.getSysdate("YYYYMMDD");
		FMSDownloadBean bean = new FMSDownloadBean();
		bean.setTradeDate(date);
		String[] tradeTypeArr = tradeTypes.split(",");
		for(String tardeType : tradeTypeArr){
			bean.setTradeType(tardeType);
			List<String> fileNames = new ArrayList<String>();
			try
			{
				//下载文件失败，抛出异常
				FMSBean fms = FMSConfig.getFMS(bean.getTradeType());
				FTPBean ftp = new FTPBean();
				final String reg = fms.getFile().getFileName().replace("{date8}", "*");
				final String regstr = fms.getFile().getFileName().replace("{date8}", "[0-9]*");
				//判断是从本地直接下载还是从ftp下载
				if("1".equals(ftp.getDownloadFileLocal())){
					CommonLogger.info("从ftp下载结果文件！,FMS2ERP,execute()");
					fileNames = FTPUtils.getAllFile(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
							ftp.getDownloadFolder(tardeType), SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER"), reg);
				}else{
					CommonLogger.info("从本地直接下载结果文件！,FMS2ERP,execute()");
					File[] files = new File(ftp.getDownloadFolder(tardeType)).listFiles(new FileFilter() {
						@Override
						public boolean accept(File pathname) {
							String pathFileName = pathname.getName();
							return pathFileName.matches(regstr);
						}
					});
					for(int i=0;i<files.length;i++){
						//fileNames.add(SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER")+files[i].getName());
						fileNames.add(files[i].getName());
					}
				}
			}
			catch(Exception e)
			{
				//连接FTP失败，记录处理失败日志
				service.insertSummaryAndRoll(bean);
				//抛出异常
				e.printStackTrace();
				CommonLogger.error("连接FTP失败！,FMS2ERP,execute()");
				throw new Exception("连接FTP失败！");
			}
			
			try {
				//删除失败处理日志
//				service.deleteErrorLog(tardeType);
				//单个任务失败继续进行下一个任务处理
				singleTradeTypeDeal(bean,fileNames);
			} catch (Exception e) {
				//保存错误日志
				//service.addErrorLog(tardeType,e.getMessage().substring(0, 1200));
				//e.printStackTrace();
				CommonLogger.error("FMS2ERP execute() Exception:", e);
			}
		}
	}
	
	public void singleTradeTypeDeal(FMSDownloadBean bean,List<String> fileNames) throws Exception{
		// 获取FMS配置
		FMSBean fms = FMSConfig.getFMS(bean.getTradeType());
		if(!CollectionUtils.isEmpty(fileNames)){
			Collections.sort(fileNames);//按照字符顺序排序，依次处理
			for(String fileName : fileNames){
				try {
					//文件处理失败停止执行该类型接口文件
					fileDeal(fms, bean, fileName);
				}catch (Exception e) {
					//e.printStackTrace();
					CommonLogger.error("FTP文件【"+fileName+"】下载处理失败", e);
					throw new Exception(e);
				}
			}
		}
	}
	/**
	 * 
	 * @param fms
	 * @param bean
	 * @param srcZipFileName 下载到本地的压缩文件名
	 * @throws Exception
	 */
	private void fileDeal(FMSBean fms, FMSDownloadBean bean, String srcZipFileName) throws Exception{
		String md5Str = "";
		String desUnZipFileName = srcZipFileName;
		String filePath = SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER");
		// 根据fms配置进行文件解压
		if (fms.getFile().isUnZip()) {
			desUnZipFileName = srcZipFileName.replace(".gz", ".TXT");
			// 直接将压缩文件后缀去掉获得解压后文件名，该处根据最后确定的压缩文件名规则可能需要调整
			GzipUtil.uncompressGZIP( filePath+ "/" + srcZipFileName, 
					filePath,  desUnZipFileName);
		}
		// 获得需要进行处理的本地文件
		File downloadFile = new File(filePath, desUnZipFileName);
		if (downloadFile == null || !downloadFile.exists()) {
			CommonLogger.error("本地路径(" + filePath + ")下不存在文件【" + desUnZipFileName + "】");
			throw new Exception("本地路径(" + filePath + ")下不存在文件【" + desUnZipFileName + "】");
		}
		File downloadFileZip = new File(filePath + "/" + srcZipFileName);
		md5Str = MD5Util.getFileMD5String(downloadFileZip);
		
		// 获得文件后进行后续处理：读取文件信息，插入明细表中
		BufferedReader br = null;
		try {
			// 读取文件，使用fms配置中的字符集
			br = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFile), fms.getFile().getCharSet()));
			
			//设置下载批次号、下载文件路径
			//首先 根据文件名看ti_download表中这个文件是否存在如果不存在生成新的批次号 如果存在则用存在的批次号
			String batchNo ="";
			String isMd5Exist = "";//01-MD5不存在走正常流程 ;02-md5存在状态为不成功状态得删除明细数据；03-md5存在且成功只需要删除文件
			//检查文件MD5值
			FMSDownloadBean checkmd5Bean = service.getDownloadFileBymd5(md5Str);//md5存在且是02/06也就是处理成功 就什么都不做 只删除文件
			
			if(null == checkmd5Bean )
			{
				//不存在MD5是新文件
				isMd5Exist = "01";
				batchNo = bean.getTradeType() + bean.getTradeDate() + service.getDownloadBatchNo(bean.getTradeType(), bean.getTradeDate());
			}else if (!"02".endsWith(checkmd5Bean.getDataFlag()) && !"06".equals(checkmd5Bean.getDataFlag())) {
				//存在MD5 但不是02、06 就重新处理 
				isMd5Exist = "02";
				batchNo = checkmd5Bean.getBatchNo();
			}else {
				//不是新文件，且是 成功状态，则在下面删除文件即可。
				isMd5Exist = "03";
			}
			bean.setBatchNo(batchNo);
			bean.setDownloadPath(filePath+ "/" + srcZipFileName);
			bean.setMd5Str(md5Str);
			
			//设置fms批次号 
			fms.setBatchNo(batchNo);
			
			//如果文件内容（文件MD5值）一样，就不写明细表及汇总表
			//如果文件名一样，当已经处理过的文件，删除以前明细及汇总数据，重新添加明细及汇总数据（批次号不变）
			if("02".equals(isMd5Exist))
			{
				//删除该批次下的明细及汇总数据,读取文件内容添加入明细表，并新增汇总表数据
				service.updateSummaryAndDetail(br, fms, bean);
			}
			else
			{
				// 读取文件内容添加入明细表，并新增汇总表数据
				service.addSummaryAndDetail(br, fms, bean);
			}
			//校验文件的内容是否有效（同一批次同一付款单(或同一订单)下的物料是否相同，状态是否相同）
			Map<String, String> param = new HashMap<String, String>();
			param.put("typeName", "1");
			param.put("fId", fms.getId());
			param.put("batchNo", fms.getBatchNo());
			param.put("tableName", fms.getTableName());
			param.put("checkTableName", fms.getCheckTableName());
			param.put("infoMsg", "");
//			service.checkFileData(param);
			//基础信息不需要下面这个校验
			if (!"11".equals(bean.getTradeType()) && !"12".equals(bean.getTradeType()) && !"13".equals(bean.getTradeType())) {
				service.checkFileData(param);
			}
			String infoMsg = param.get("infoMsg");
			if(("21".equals(fms.getId()) || "25".equals(fms.getId())) && !Tool.CHECK.isBlank(infoMsg)){
				bean.setDealLog(infoMsg);
				CommonLogger.info(infoMsg);
				//删除tid的数据
				service.delDetailByBatchNO(bean.getBatchNo(),fms.getTableName());
				//service.delDwnByBatchNO(bean.getBatchNo());
				//更新状态为处理失败
				service.updateStatus(bean.getBatchNo(),FmsValues.FMS_DOWNLOAD_CHK_FAIL);
			}else{
				//如果是正常流程，或者是存在文件且处理不成功则
				if (!"03".equals(isMd5Exist))
				{
					dealFmsFile(bean);	
				}
				
				//删除FTP上的文件
				FTPBean ftp = new FTPBean();
				Boolean isdelFile = WebHelp.getSysParaBoolean("FTP_DEL_FILE_FLAG");
				if("1".equals(ftp.getDownloadFileLocal()) && isdelFile)
				{
					boolean delRes = FTPUtils.deleteFile(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
							ftp.getDownloadFolder(bean.getTradeType()), srcZipFileName);
					CommonLogger.info("删除FTP上的文件【"+srcZipFileName+"】" + (delRes ? "成功！" : "失败！"));
				}
				bean.setDealLog("结果文件处理成功");
			}
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
			bean.setDealLog(e.getMessage().substring(0,200));
			CommonLogger.error("FMS2ERP fileDeal() Exception:", e);
			throw e;
		} 
		finally 
		{
			//添加结果文件的log信息
			service.addDealLog(bean);
			if (br != null) 
			{
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 不需要整体做事务控制
	 * @param bean
	 * @throws Exception
	 */
	private void dealFmsFile(FMSDownloadBean bean) throws Exception {
		try {
			service.updateStatus(bean.getBatchNo(),FmsValues.FMS_DOWNLOAD_DEALING);
			receiveService.dealFmsFile(bean.getTradeType(), bean.getBatchNo());
		} catch (Exception e) {
			e.printStackTrace();
			service.updateStatus(bean.getBatchNo(),FmsValues.FMS_DOWNLOAD_FAIL);
			throw e;
		}
	}
	
	
	public static void main(String[] args) 
	{
		int a = 1;
		System.out.println(a==1);
	}
	
}
