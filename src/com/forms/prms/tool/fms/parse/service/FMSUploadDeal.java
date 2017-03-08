package com.forms.prms.tool.fms.parse.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.base.model.page.Page;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.FmsUploadLock;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.web.sysmanagement.uploadfilemanage.controller.UpFileManagerController;
import com.forms.prms.web.util.GzipUtil;

@Service
public class FMSUploadDeal {

	@Autowired
	private FMSService service;

	@Autowired
	private UpFileManagerController controller;

	/**
	 * 上传文件任务处理
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void execute(UpLoadBean bean) throws Exception {
		if (bean == null) {
			bean = new UpLoadBean();
		}
		if (StringUtils.isEmpty(bean.getBatchNo())) {
			bean.setTradeDate("sysdate");
		}
		// 查询待处理上传汇总列表
		if (Tool.CHECK.isBlank(bean.getBatchNo())) {
			bean.setDataFlag(FmsValues.FMS_UP_FORDEAL);
		} else {
			String[] dataFlags = { FmsValues.FMS_UP_FAIL, FmsValues.FMS_CREATE_SUCC};
			bean.setDataFlags(dataFlags);
		}
		CommonLogger.info("查询待处理上送汇总列表！,FMSUploadDeal,execute(),【getSummaryList(bean)】");
		List<UpLoadBean> summaryList = service.getSummaryList(bean);
		if (summaryList == null || summaryList.size() < 1) {
			return;
		}
		// 分页设置
		Page page = null;
		// 循环明细数据查询、文件写入、压缩及上传
		for (UpLoadBean tempbean : summaryList) {
			page = new Page();
			page.setPageSize(FmsValues.PAGESIZE);
			Tool.CACHE.setThreadCache(FmsValues.PAGEKEY, page);
			try {
				CommonLogger.info("循环明细数据查询、文件写入、压缩及上传！,FMSUploadDeal,execute(),【singleFileDeal(tempbean, page)】");
				singleFileDeal(tempbean, page);
			} catch (SendFileException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				//e.printStackTrace();
				CommonLogger.error("FMSUploadDeal execute() Exception:", e);
				// 若是从页面调度的单一批次处理，则抛出异常信息
				// 系统调度的批量处理，任一文件处理失败继续进行下一任务处理
				if (StringUtils.isNotEmpty(bean.getBatchNo())) {
					throw e;
				}
			}
		}
		// 唤醒上传线程，上传文件
		FmsUploadLock.newInstance().executeWake();
	}

	/**
	 * 单一任务处理
	 * 
	 * @param bean
	 *            bean中的批次号、交易类型、交易日期、ou代码和序号不为空的话，可以直接调用此方法进行文件处理
	 * @throws Exception
	 */
	public void singleFileDeal(UpLoadBean bean, Page page) throws Exception {

		CommonLogger.info("上传单一任务处理！,FMSUploadDeal,singleFileDeal()");
		// 获取FMS配置
		FMSBean fms = FMSConfig.getFMS(bean.getTradeType() + "up");

		// 更新汇总表状态为“上传处理中”
		UpLoadBean updateBean = new UpLoadBean();
		updateBean.setBatchNo(bean.getBatchNo());
		updateBean.setDataFlag(FmsValues.FMS_UP_DEALING);
		service.updateStatus(updateBean);

		// 设置fms批次号
		fms.setBatchNo(bean.getBatchNo());
		// 获取本地文件等待写入
		File fmsFile = service.getLocalFileForWrite(fms.getFile(), bean.getOuCode(), bean.getTradeDate(),
				bean.getSeqNo());

		FileOutputStream fos = null;
		List<Map<String, String>> dataList = null;
		int totalPage = 0;
		// 修改状态值为上传处理失败，若成功则修改为上传成功，其他情况更新汇总表状态为上传处理失败
		updateBean.setDataFlag(FmsValues.FMS_UP_FAIL);
		try {
			String fileSql = fms.getFile().getFileSql();
			// fileSql = MessageFormat.format(fileSql, new
			// String[]{"'"+bean.getBatchNo()+"'"});
			fileSql = fileSql.replace("{0}", "'" + bean.getBatchNo() + "'");
			// 获取文件输出流
			fos = new FileOutputStream(fmsFile);
			page.setCurrentPage(1);
			// 读取明细数据
			dataList = service.getFmsDataList(FmsValues.PAGEKEY, fileSql);
			// 写入本地文件
			service.writeLocalFile(fos, fms, dataList);
			// 分页查询数据并写入文件
			totalPage = page.getTotalPages();
			for (int i = 2; i <= totalPage; i++) {
				page.setCurrentPage(i);
				dataList = service.getFmsDataList(FmsValues.PAGEKEY, fileSql);
				service.writeLocalFile(fos, fms, dataList);
			}

			// 替换文件结尾信息参数，并将结尾信息写入文件
			Map<String, String> params = new HashMap<String, String>();
			params.put("${RecNum}", page.getTotalRecords() + "");
			params.put("${Sep}", "\" | \"");
			service.writeEndLines(fos, params, fms.getFile().getCharSet());
			fos.flush();
			// 压缩文件且文件名为文件名后缀改为gz
//			GzipUtil.compressFile(fmsFile);
			/*String gzFileName = fmsFile.getName().replace("TXT", "gz");
			fmsFile = new File(fmsFile.getParent(), gzFileName);*/
			
			String gzFileName = fmsFile.getName()+".gz";
			GzipUtil.compressGZIP(fmsFile.getAbsolutePath(),fmsFile.getParent(), gzFileName); 
			fmsFile = new File(fmsFile.getParent(), gzFileName); 
			
			updateBean.setDataFlag(FmsValues.FMS_CREATE_SUCC);
			updateBean.setUploadPath(fmsFile.getAbsolutePath());
		} catch (Exception e) {
			CommonLogger.error("FMSUploadDeal singleFileDeal() Exception:", e);
			throw e;
		} finally {
			// 执行汇总表更新
			service.updateStatus(updateBean);
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
