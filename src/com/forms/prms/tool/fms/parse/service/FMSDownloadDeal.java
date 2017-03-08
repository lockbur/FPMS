package com.forms.prms.tool.fms.parse.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.FTPUtils;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.domain.FTPBean;
import com.forms.prms.tool.fms.receive.service.ReceiveService;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.util.GzipUtil;

@Service
public class FMSDownloadDeal {

	@Autowired
	private FMSService service;

	@Autowired
	private ReceiveService receiveService;

	/**
	 * 校验文件处理
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void execute(UpLoadBean bean) throws Exception {
		CommonLogger.info("校验文件处理！,FMSDownloadDeal,execute()");
		if (bean == null) {
			bean = new UpLoadBean();
		}
		// 查询上传汇总待校验文件处理列表
		if(StringUtils.isEmpty(bean.getDataFlag())){
			String[] dataFlags={FmsValues.FMS_DOWN_FORDEAL,FmsValues.FMS_UPDATE_CHK_FAIL}; 
			bean.setDataFlags(dataFlags);
		}
		List<UpLoadBean> downList = service.getSummaryList(bean);
		if (downList == null || downList.size() < 1) {
			return;
		}
		// 循环进行文件下载、解压、读取、更新明细数据处理
		for (UpLoadBean tempbean : downList) {
			try {
				singleFileDeal(tempbean);
			} catch (Exception e) {
				//e.printStackTrace();
				CommonLogger.error("FMSDownloadDeal execute() Exception:", e);
				// 若是从页面调度的单一批次处理，则抛出异常信息
				// 系统调度的批量处理，任一文件处理失败继续进行下一任务处理
				if (StringUtils.isNotEmpty(bean.getBatchNo())) {
					e.printStackTrace();
					throw e;
				}
			}
		}
	}

	/**
	 * 单一文件处理 bean中的批次号、交易类型、上传文件全路径不为空的话，可以直接调用此方法进行文件处理
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void singleFileDeal(UpLoadBean bean) throws Exception {
		CommonLogger.info("单一文件处理 bean中的批次号、交易类型、上传文件全路径不为空的话，可以直接调用此方法进行文件处理！,FMSDownloadDeal,singleFileDeal()");
		// 获取FMS配置
		FMSBean fms = FMSConfig.getFMS(bean.getTradeType());

		// 设置fms批次号
		fms.setBatchNo(bean.getBatchNo());

		// 根据上传文件路径截取待下载文件名称
		String fileName = bean.getUploadPath();
		fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);

		// 从fms配置中获取ftp信息，下载文件
		FTPBean ftp = new FTPBean();
		
		//判断是从本地直接下载还是先从ftp下载
		if("1".equals(ftp.getDownloadFileLocal())){
			CommonLogger.info("从FTP下载文件！,FMSDownloadDeal,singleFileDeal()");
			boolean res = Tool.FTP.getFile1(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
					ftp.getDownloadFolder(bean.getTradeType()), fileName, SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER"), fileName);
			if (!res) {
				CommonLogger.info("未从FTP获取到校验文件【" + fileName + "】");
				return ;
			}
		}
		else
		{
			File localFile = new File(SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER"), fileName);
			if (localFile == null || !localFile.exists()) 
			{
				CommonLogger.info("未从本地获取到校验文件【" + fileName + "】");
				return ;
			}
		}
		
		

		// 根据fms配置进行文件解压
		if (fms.getFile().isUnZip()) {
			// 直接将压缩文件后缀去掉获得解压后文件名，该处根据最后确定的压缩文件名规则可能需要调整
			GzipUtil.uncompressGZIP(SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER") + "/" + fileName, SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER"), fileName.replace(".gz", ""));
			fileName = fileName.replace(".gz", "");
		}
		// 获得需要进行处理的本地文件
		File validFile = new File(SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER"), fileName);
		if (validFile == null || !validFile.exists()) {
			throw new Exception("本地路径(" + SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER") + ")下不存在文件【" + fileName + "】");
		}
		
		// 更新汇总表状态为“下载处理中”
		UpLoadBean updateBean = new UpLoadBean();
		updateBean.setBatchNo(bean.getBatchNo());
		updateBean.setDataFlag(FmsValues.FMS_DOWN_DEALING);
		service.updateStatus(updateBean);
		
		// 修改状态值为检验失败，若成功则修改为检验成功，其他情况更新汇总表状态为检验失败
		updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
		// 获得文件后进行后续处理：读取文件信息，更新明细数据的状态和备注信息
		BufferedReader br = null;
		BufferedReader br2 = null;
		try {
			// 读取文件，使用fms配置中的字符集
			br = new BufferedReader(new InputStreamReader(new FileInputStream(validFile), fms.getFile().getCharSet()));
			br2 = new BufferedReader(new InputStreamReader(new FileInputStream(validFile), fms.getFile().getCharSet()));
			//将数据插入校验表
			service.insertCheckDetails(br2,fms);
			
			//校验回复的数据与上传的数据数量是否一致（同一批次同一付款单）；（同一批次同一付款单）回复的数据是否状态统一
			//不通过需要执行service.deleteCheckData(fms);
			Map<String, String> param = new HashMap<String, String>();
			param.put("typeName", "0");
			param.put("fId", fms.getId());
			param.put("batchNo", fms.getBatchNo());
			param.put("tableName", fms.getTableName());
			param.put("checkTableName", fms.getCheckTableName());
			param.put("infoMsg", "");
			service.checkFileData(param);
			String infoMsg = param.get("infoMsg");
			if(("31".equals(fms.getId())||"32".equals(fms.getId())||"34".equals(fms.getId()))&&!Tool.CHECK.isBlank(infoMsg)){//TODO 此处可添加提示信息到表中
				updateBean.setDealLog(infoMsg);
				updateBean.setDataFlag(FmsValues.FMS_UPDATE_CHK_FAIL);
				CommonLogger.info(infoMsg);
				// 删除文件对应check数据库的数据
				service.deleteCheckData(fms);
			}else{
				// 更新明细数据
				//updateFMS(br, fms, bean);
				//从check表进行批量更新
				service.updateBatchFms(fms);
				receiveService.dealFmsFile(bean.getTradeType(), bean.getBatchNo());
				//成功后更新状态
				updateBean.setDataFlag(FmsValues.FMS_UPDATE_SUCC);
				updateBean.setDownloadPath(validFile.getAbsolutePath().replace("/", "\\"));
				updateBean.setDealLog("校验文件处理成功");
			}
			
			//删除FTP上的文件
			Boolean isdelFile = WebHelp.getSysParaBoolean("FTP_DEL_FILE_FLAG");
			if("1".equals(ftp.getDownloadFileLocal()) && isdelFile)
			{
				boolean delRes = FTPUtils.deleteFile(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
						ftp.getDownloadFolder(bean.getTradeType()), fileName);
				CommonLogger.info("删除FTP上的文件【"+fileName+"】" + (delRes ? "成功！" : "失败！"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if(("31".equals(fms.getId())||"32".equals(fms.getId())||"34".equals(fms.getId()))){
				// 删除文件对应数据库的数据
				service.deleteCheckData(fms);
			}
			updateBean.setDealLog("fms校验文件下载处理异常");
			CommonLogger.error("FMSDownloadDeal singleFileDeal() Exception:", e);
			throw e;
		} finally {
			// 执行汇总表更新
			service.updateStatus(updateBean);
			//添加log信息
			service.addCheckLog(updateBean);
			if (br != null) {
				try {
					br.close();
					br2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 进行事务管理 
	 * @param br
	 * @param fms
	 * @param updateBean
	 * @param validFile
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	private void updateFMS(BufferedReader br, FMSBean fms, UpLoadBean updateBean) throws Exception {
		service.updateFMS(br, fms);
		receiveService.dealFmsFile(updateBean.getTradeType(), updateBean.getBatchNo());
	}

}
