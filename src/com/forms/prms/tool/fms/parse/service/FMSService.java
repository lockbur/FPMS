package com.forms.prms.tool.fms.parse.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.dealdata.LoadData;
import com.forms.dealdata.download.DownLoadBean;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.BeanUtils;
import com.forms.prms.tool.SFTPTool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.forms.prms.tool.fms.FMSDownloadBean;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.dao.IFMSDAO;
import com.forms.prms.tool.fms.parse.domain.ColumnBean;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.domain.FTPBean;
import com.forms.prms.tool.fms.parse.domain.FileBean;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.sysmanagement.homepage.domain.ExcepInfoBean;
import com.forms.prms.web.util.GzipUtil;

@Service
public class FMSService {

	@Autowired
	private IFMSDAO dao;
	
	/**
	 * 获取当前系统日期
	 * 
	 * @param format
	 * @return
	 */
	public String getSysdate(String format) {
		return dao.getSysdate(format);
	}

	/**
	 * 获取下载汇总列表的批次号
	 * 
	 * @param tradeType
	 * @param tradeDate
	 * @return
	 */
	public String getDownloadBatchNo(String tradeType, String tradeDate) {
		return dao.getDownloadBatchNo(tradeType, tradeDate);
	}

	/**
	 * 获取上传汇总列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<UpLoadBean> getSummaryList(UpLoadBean bean) {
		return dao.getSummaryList(bean);
	}

	/**
	 * 更新上传汇总表状态
	 * 
	 * @param bean
	 */
	public void updateStatus(UpLoadBean bean) {
		dao.updateStatus(bean);
	}

	/**
	 * 上传明细列表查询
	 * 
	 * @param pageKey
	 * @return
	 */
	public List<Map<String, String>> getFmsDataList(String pageKey, String sqlStr) {
		UpLoadBean bean = new UpLoadBean();
		bean.setSqlStr(sqlStr);
		return PageUtils.getPageDao(dao, pageKey).getFmsDataList(bean);
	}

	/**
	 * 获取本地文件，替换文件名参数
	 * 
	 * @param ouCode
	 * @param date8
	 * @return
	 */
	public File getLocalFileForWrite(FileBean file, String ouCode,
			String date8, String seqNo) {
		// 获取本地上传文件路径
		String path = SystemParamManage.getInstance().getParaValue("FMS_UPLOAD_LOCAL_FOLDER")  + "/" + Tool.DATE.getDateStrNO();
		CommonFileUtils.FileDirCreate(path);
		// 替换上传文件名中的参数
		String fileName = file.getFileName().replace("{oucode}", ouCode)
				.replace("{date8}", date8).replace("{seq}", seqNo);
		// 获取文件对象，如果目录为空则创建，如果文件已存在则删除
		File tempfile = new File(path, fileName);
		Tool.FILE.mkdirs(tempfile.getParentFile());
		if (tempfile != null && tempfile.exists()) {
			tempfile.delete();
		}
		return tempfile;
	}

	/**
	 * 将明细记录写入文件
	 * 
	 * @param fos
	 * @param dataList
	 * @throws Exception
	 */
	public void writeLocalFile(FileOutputStream fos, FMSBean fms,
			List<Map<String, String>> dataList) throws Exception {
		if (dataList == null || dataList.size() < 1) {
			return;
		}
		// 文件中各字段分隔符及文件字符集
		String charSet = fms.getFile().getCharSet();
		StringBuffer line = new StringBuffer();
		Map<String, String> data = null;
		Object content = null;
		for (int i = 0; i < dataList.size(); i++) {
			// 获取每一条记录，获取每个字段的内容，按照list配置的顺序拼接内容及分隔符
			data = dataList.get(i);
			if(null != data && data.size()>0)
			{
				String memo = data.get("MEMO");
				line.append(memo == null ? "" : memo);
			}
			line.append(FmsValues.LINE_SEPARATOR);
		}
		// 写入文件
		fos.write(line.toString().getBytes(charSet));
	}

	/**
	 * 写入文件结尾信息
	 * 
	 * @param fos
	 * @param params
	 * @param charSet
	 * @throws Exception
	 */
	public void writeEndLines(FileOutputStream fos, Map<String, String> params,
			String charSet) throws Exception {
		// 结尾信息字符串替换格式: ${XXX}
		Pattern p = Pattern.compile("\\$\\{\\w+\\}");
		String realEndLines = FmsValues.END_LINES;
		Matcher m = p.matcher(realEndLines);
		// 循环替换参数
		while (m.find()) {
			realEndLines = realEndLines.replace(m.group(),
					params.get(m.group()) == null ? "" : params.get(m.group()));
		}
		// 写入文件
		fos.write(realEndLines.getBytes(charSet));
	}

	/**
	 * 根据校验文件更新数据库记录状态
	 *-----------------------此方法暂时丢弃
	 * @param reader
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateFMS(BufferedReader reader, FMSBean fms) throws Exception {
		// 获取配置的数据库更新及主键字段信息
		List<ColumnBean> updateColumns = fms.getUpdateColumns();
		if (updateColumns == null || updateColumns.size() < 1) {
			throw new Exception("更新字段配置信息读取失败！");
		}
		// 获取配置的数据库字段信息
		List<ColumnBean> primaryColumns = fms.getPrimaryColumns();
		if (primaryColumns == null || primaryColumns.size() < 1) {
			throw new Exception("主键字段配置信息读取失败！");
		}

		List<FMSBean> lists = new ArrayList<FMSBean>();// 用来存一个文件中的所有数据

		String sep = fms.getFile().getSep();
		String line = null;
		String[] cols = null;
		ColumnBean column = null;
		int realRows = 0;
		int rows = 0;
		int temp = 0;
		try {
			// 读取文件内容更新明细数据状态
			while ((line = reader.readLine()) != null) {
				// 判断是否结尾，获取数据记录总数进行校验
				if (line.startsWith("|||||")) {
					if (line.startsWith("|||||RecNum=")) {
						realRows = Integer.parseInt((line
								.substring("|||||RecNum=".length()).trim()));
						break;
					} else {
						continue;
					}
				}
				rows++;
				cols = Tool.STRING.split(line, sep);
				// 根据配置字段ID值获取相关字段值在数组中的位置，将值放入字段bean中
				for (int i = 0; i < updateColumns.size(); i++) {
					column = updateColumns.get(i);
					column.setValue(cols[column.getId() - 1]);
				}
				for (int i = 0; i < primaryColumns.size(); i++) {
					column = primaryColumns.get(i);
					column.setValue(cols[column.getId() - 1]);
				}
				// 单条的数据更新改为批量的更新（下面）
				// temp = temp + dao.updateFms(fms);
				lists.add(fms);
				if (lists.size() > 0 && lists.size() % 2000 == 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("tableName", fms.getTableName());
					map.put("lists", lists);
					temp = temp + dao.updateFms(map);
					lists.clear();// 清空数据
				}
			}
			if (null != lists && lists.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tableName", fms.getTableName());
				map.put("lists", lists);
				temp = temp + dao.updateFms(map);
			}

			// 校验读取记录数是否正确
			if (rows != realRows) {
				throw new Exception("读取文件记录" + rows + "行，文件实际记录" + realRows
						+ "行！");
			}
			// 校验读取记录数是否全部入库
			if (rows != temp) {
				throw new Exception("读取文件记录" + rows + "行，实际入库" + temp + "行！");
			}

			// 循环更新对应表的数据状态

		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 新增下载明细数据
	 * 
	 * @param reader
	 * @param fms
	 * @throws Exception
	 */
	public int insertDetails(BufferedReader reader, FMSBean fms)
			throws Exception {
		// 获取配置的数据库字段信息
		String tableNames = fms.getTableName();
		String[] tableNameArr = tableNames.split(",");

		List<ColumnBean> fileColumns = fms.getFileColumns();
		if (fileColumns == null || fileColumns.size() < 1) {
			throw new Exception("文件字段配置信息读取失败！");
		}
		String sep = fms.getFile().getSep();
		String line = null;
		String[] cols = null;
		ColumnBean column = null;
		int realRows = 0;
		int rows = 0;
		int temp = 0;
		List<FMSBean> lists = new ArrayList<FMSBean>();
		FMSBean bean2 = null;
		try {
			// 读取文件内容
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("|||||")) {
					if (line.startsWith("|||||RecNum=")) {
						realRows = Integer.parseInt((line
								.substring("|||||RecNum=".length()).trim()));
						break;
					} else {
						continue;
					}
				}
				rows++;
				cols = Tool.STRING.split(line, sep);
				// 根据配置字段ID值获取相关字段值在数组中的位置，将值放入字段bean中
				for (int i = 0; i < fileColumns.size(); i++) {
					column = fileColumns.get(i);
					column.setValue(cols[column.getId() - 1]);
				}
				// 此处因引用传递的问题采用封装深度克隆的帮助类
				bean2 = (FMSBean) BeanUtils.deepCopyBean(fms);
				lists.add(bean2);
				if (lists.size() > 0 && lists.size() %50 == 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("fileColumns", fileColumns);
					map.put("lists", lists);
					for (String tableName : tableNameArr) {
						map.put("tableName",tableName);
						//fms.setTableName(tableName);
						temp = temp + dao.insertDetails(map);
					}
					//temp = temp + dao.insertCheckDetails(map);
					lists.clear();// 清空数据
				}
			}
			if (null != lists && lists.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileColumns", fileColumns);
				map.put("lists", lists);
				for (String tableName : tableNameArr) {
					map.put("tableName",tableName);
					//fms.setTableName(tableName);
					temp = temp + dao.insertDetails(map);
				}
				lists.clear();// 清空数据
			}
			fms.setTableName(tableNames);
			if (rows != realRows) {
				throw new Exception("读取文件记录" + rows + "行，文件实际记录" + realRows
						+ "行！");
			}
			if (rows != temp / tableNameArr.length) {
				throw new Exception("读取文件记录" + rows + "行，实际入库" + temp
						/ tableNameArr.length + "行！");
			}
		} catch (IOException e) {
			fms.setTableName(tableNames);
			throw e;
		}
		return rows;
	}
//	public int insertDetails(BufferedReader reader, FMSBean fms)
//			throws Exception {
//		// 获取配置的数据库字段信息
//		String tableNames = fms.getTableName();
//		String[] tableNameArr = tableNames.split(",");
//
//		List<ColumnBean> fileColumns = fms.getFileColumns();
//		if (fileColumns == null || fileColumns.size() < 1) {
//			throw new Exception("文件字段配置信息读取失败！");
//		}
//		String sep = fms.getFile().getSep();
//		String line = null;
//		String[] cols = null;
//		ColumnBean column = null;
//		int realRows = 0;
//		int rows = 0;
//		int temp = 0;
//		List<FMSBean> lists = new ArrayList<FMSBean>();
//		FMSBean bean2 = null;
//		try {
//			//璇诲彇鏂囦欢鍐呭
//			while ((line = reader.readLine()) != null) {
//				if(line.startsWith("|||||")){
//					if(line.startsWith("|||||RecNum=")){
//						realRows = Integer.parseInt((line.substring("|||||RecNum=".length()).trim()));
//						break;
//					}else{
//						continue;
//					}
//				}
//				rows++;
//				cols = Tool.STRING.split(line, sep);
//				//鏍规嵁閰嶇疆瀛楁ID鍊艰幏鍙栫浉鍏冲瓧娈靛�鍦ㄦ暟缁勪腑鐨勪綅缃紝灏嗗�鏀惧叆瀛楁bean涓�
//				for(int i = 0; i < fileColumns.size(); i++){
//					column = fileColumns.get(i);
//					column.setValue(cols[column.getId() - 1]);
//				}
//				
//				for(String tableName : tableNameArr)
//				{
//					fms.setTableName(tableName);
//					temp = temp + dao.insertDetails(fms);
//				}
//			}
//			fms.setTableName(tableNames);
//			if(rows != realRows){
//				throw new Exception("璇诲彇鏂囦欢璁板綍" + rows + "琛岋紝鏂囦欢瀹為檯璁板綍" + realRows + "琛岋紒");
//			}
//			if(rows != temp/tableNameArr.length){
//				throw new Exception("璇诲彇鏂囦欢璁板綍" + rows + "琛岋紝瀹為檯鍏ュ簱" + temp/tableNameArr.length + "琛岋紒");
//			}
//		} catch (IOException e) {
//			fms.setTableName(tableNames);
//			throw e;
//		}
//		return rows;
//	}
	/**
	 * 新增下载汇总记录
	 * 
	 * @param bean
	 * @return
	 */
	public int insertSummary(FMSDownloadBean bean) {
		return dao.insertSummary(bean);
	}

	public int deleteSummary(FMSDownloadBean bean) {
		return dao.deleteSummary(bean);
	}

	/**
	 * 下载汇总表状态更新
	 * 
	 * @param batchNo
	 * @param dataFlag
	 */
	public void updateStatus(String batchNo, String dataFlag) {
		FMSDownloadBean bean = new FMSDownloadBean();
		bean.setBatchNo(batchNo);
		bean.setDataFlag(dataFlag);
		dao.updateDlStatus(bean);
	}
	
	public void updateDlStatus(FMSDownloadBean bean) {
		dao.updateDlStatus(bean);
	}
	
	/**
	 * 新增明细和汇总数据
	 * 
	 * @param br
	 * @param fms
	 * @param downloadBeana
	 * @throws Exception
	 */
	public void addSummaryAndDetail(BufferedReader br, FMSBean fms,
			FMSDownloadBean downloadBeana) throws Exception {
		addSumAndDtlCommon(br, fms, downloadBeana);
	}
	
	public void insertException()
	{
		// 新增首页滚动信息
		ExcepInfoBean exceInfoBean = new ExcepInfoBean();
		exceInfoBean.setExcepId("");
		exceInfoBean.setExcepTitle("处理接口失败");
		exceInfoBean.setExcepInfo("连接FTP下载FMS回传文件失败");
		exceInfoBean.setExcepOrgId("00000");// TODO 待确认
		exceInfoBean.setAddUid("system");
		exceInfoBean.setExcepType("1");

		// 删除当日该类型FTP连接失败记录
		dao.deleteExceInfo(exceInfoBean);
		// 新增
		dao.insertExceInfo(exceInfoBean);
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertSummaryAndRoll(FMSDownloadBean bean) throws Exception {
		String batchNo = bean.getTradeType() + bean.getTradeDate()
				+ getDownloadBatchNo(bean.getTradeType(), bean.getTradeDate());
		bean.setBatchNo(batchNo);
		bean.setDownloadDate(getSysdate("YYYYMMDD"));
		bean.setDownloadTime(getSysdate("HH:MI:SS"));
		bean.setDataFlag(FmsValues.FMS_DOWNLOAD_CONFTP_FAIL);
		bean.setTradeDate(getSysdate("YYYYMMDD"));
		// 删除当日该类型FTP上传失败记录
		//deleteSummary(bean);
		// 新增数据
		//insertSummary(bean);

		// 新增首页滚动信息
		ExcepInfoBean exceInfoBean = new ExcepInfoBean();
		exceInfoBean.setExcepId("");
		exceInfoBean.setExcepTitle("处理接口失败");
		exceInfoBean.setExcepInfo("下载FMS文件，连接FTP失败");
		exceInfoBean.setExcepOrgId("00000");// TODO 待确认
		exceInfoBean.setAddUid("system");
		exceInfoBean.setExcepType("1");

		// 删除当日该类型FTP连接失败记录
		dao.deleteExceInfo(exceInfoBean);
		// 新增
		dao.insertExceInfo(exceInfoBean);
	}

	/**
	 * 更新明细和汇总数据（先删除，后新增）
	 * 
	 * @param br
	 * @param fms
	 * @param downloadBeana
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateSummaryAndDetail(BufferedReader br, FMSBean fms,
			FMSDownloadBean downloadBeana) throws Exception {
		// 删除该批次下的明细及汇总数据
		delDetailByBatchNO(downloadBeana.getBatchNo(), fms.getTableName());
		delDwnByBatchNO(downloadBeana.getBatchNo());
		// 新增数据
		addSumAndDtlCommon(br, fms, downloadBeana);
	}

	public void addSumAndDtlCommon(BufferedReader br, FMSBean fms,
			FMSDownloadBean downloadBeana) throws Exception {
		int rows = 0;
		rows = insertDetails(br, fms);
		downloadBeana.setAllCnt(rows);
		downloadBeana.setDataFlag(FmsValues.FMS_DOWNLOAD_FORDEAL);
		rows = insertSummary(downloadBeana);
		if (rows <= 0) {
			throw new Exception("下载汇总表数据新增失败！");
		}
		CommonLogger.info("批次号【" + downloadBeana.getBatchNo() + "】下载入库成功！");
	}

//	/**
//	 * 根据文件名得到批次号
//	 * 
//	 * @param tempFileName
//	 * @return
//	 */
//	public FMSDownloadBean getDownloadMsg(String tempFileName) {
//		return dao.getBathch(tempFileName);
//	}

	/**
	 * 新增下载校验文件明细数据
	 * 
	 * @param reader
	 * @param fms
	 * @throws Exception
	 */
	public int insertCheckDetails(BufferedReader reader, FMSBean fms)
			throws Exception {
		// 获取配置的数据库字段信息
		List<ColumnBean> fileColumns = fms.getFileColumns();
		if (fileColumns == null || fileColumns.size() < 1) {
			throw new Exception("文件字段配置信息读取失败！");
		}
		String sep = fms.getFile().getSep();
		String line = null;
		String[] cols = null;
		ColumnBean column = null;
		int realRows = 0;
		int rows = 0;
		int temp = 0;
		List<FMSBean> lists = new ArrayList<FMSBean>();
		FMSBean bean2 = null;
		try {
			// 读取文件内容
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("|||||")) {
					if (line.startsWith("|||||RecNum=")) {
						realRows = Integer.parseInt((line
								.substring("|||||RecNum=".length()).trim()));
						break;
					} else {
						continue;
					}
				}
				rows++;
				cols = Tool.STRING.split(line, sep);

				// 根据配置字段ID值获取相关字段值在数组中的位置，将值放入字段bean中
				for (int i = 0; i < fileColumns.size(); i++) {
					column = fileColumns.get(i);
					column.setValue(cols[column.getId() - 1]);
				}
				// 批量插入
				// temp = temp + dao.insertCheckDetails(fms);
				// 此处因引用传递的问题采用封装深度克隆的帮助类
				bean2 = (FMSBean) BeanUtils.deepCopyBean(fms);
				lists.add(bean2);
				if (lists.size() > 0 && lists.size() % 500 == 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("checkTableName", fms.getCheckTableName());
					map.put("fileColumns", fileColumns);
					map.put("lists", lists);
					temp = temp + dao.insertCheckDetails(map);
					lists.clear();// 清空数据
				}
			}
			if (null != lists && lists.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("checkTableName", fms.getCheckTableName());
				map.put("fileColumns", fileColumns);
				map.put("lists", lists);
				temp = temp + dao.insertCheckDetails(map);
				lists.clear();// 清空数据
			}

			if (rows != realRows) {
				throw new Exception("读取文件记录" + rows + "行，文件实际记录" + realRows
						+ "行！");
			}
			if (rows != temp) {
				throw new Exception("读取文件记录" + rows + "行，实际入库" + temp + "行！");
			}
		} catch (IOException e) {
			throw e;
		}
		return rows;
	}

	public FMSDownloadBean getFirstFailJob() {
		return dao.getFirstFailJob();
	}

	public FMSDownloadBean getDownloadFileBymd5(String md5Str) {
		return dao.getDownloadFileBymd5(md5Str);
	}

	public void deleteErrorLog(String tardeType) {
		dao.deleteErrorLog(tardeType);
	}

	public void addErrorLog(String tardeType, String errlog) {
		dao.addErrorLog(tardeType, errlog);
	}

	public void delDwnByBatchNO(String batchNo) {
		dao.delDwnByBatchNO(batchNo);
	}

	public void delDetailByBatchNO(String batchNo, String tableName) {
		String[] tableNameArr = tableName.split(",");
		if (null != tableNameArr && tableNameArr.length > 0) {
			for (String tableNameTmp : tableNameArr) {
				dao.delDetailByBatchNO(batchNo, tableNameTmp);
			}
		}
	}

	/**
	 * 从check表进行批量更新
	 * 
	 * @param fms
	 */
	public int updateBatchFms(FMSBean fms) {
		return dao.updateBatchFms(fms);
	}
	
	/**
	 * 从check表进行批量删除
	 * 
	 * @param fms
	 */
	public int deleteCheckData(FMSBean fms) {
		return dao.deleteCheckData(fms);
	}
	
	public int deleteCheckData(String batchNo,String tradeType) throws Exception {
		FMSBean fms = FMSConfig.getFMS(tradeType);
		fms.setBatchNo(batchNo);
		return dao.deleteCheckData(fms);
	}
	
	public int delCheckData(String checkTableName,String batchNo) {
		return dao.delCheckData(checkTableName,batchNo);
	}


	/**
	 * 校验文件中的数据
	 * @param batchNo
	 * @return
	 */
	public String checkFileData(Map<String, String> param) {
		return dao.checkFileData(param);
	}

	/**
	 * 添加校验失败的信息
	 * @param updateBean
	 * @return
	 */
	public int addCheckLog(UpLoadBean updateBean) {
		return dao.addCheckLog(updateBean);
	}

	public int addDealLog(FMSDownloadBean bean) {
		return dao.addDealLog(bean);
	}

	public int getDownLoadCount() {
		return dao.getDownLoadCount();
	}

	public void mergeDownload(String tradeDate, String[] tradeTypeArr) {
		FMSDownloadBean bean = new FMSDownloadBean();
		List<FMSDownloadBean> tradeTypeList = new ArrayList<FMSDownloadBean>();
		if(null != tradeTypeArr)
		{
			for(String tradeType : tradeTypeArr)
			{
				FMSDownloadBean subbean = new FMSDownloadBean();
				String batchNo = tradeType + tradeDate + getDownloadBatchNo(tradeType, tradeDate);
				subbean.setBatchNo(batchNo);
				subbean.setTradeDate(tradeDate);
				subbean.setTradeType(tradeType);
				subbean.setDealType("1");
				tradeTypeList.add(subbean);
			}
			bean.setTradeTypeList(tradeTypeList);
			dao.mergeDownload(bean);
		}
	}

	public String getDealDate(String tradeTypes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tradeTypes",  Arrays.asList(tradeTypes.split(",")));
		map.put("cnt", tradeTypes.split(",").length);		
		return dao.getDealDate(map);
	}
	
	public String downloadFile(DownLoadBean dlBean,UpLoadBean updateBean,FMSDownloadBean downBean) throws Exception
	{
		FTPBean ftp = new FTPBean();
		//创建目录
		String downloadpath = SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER") + "/" + dlBean.getTradeDate();
		CommonFileUtils.FileDirCreate(downloadpath);
		//判断是从本地直接下载还是从ftp下载
		if("1".equals(ftp.getDownloadFileLocal())){
			boolean res = false;
			try {
				res = downloadFromFtp(dlBean.getTradeType(),downloadpath,dlBean.getFileName());
			} catch (DownLoadException e) {
				downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
				downBean.setDealLog("连接FTP下载文件异常");
				updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
				updateBean.setDealLog("连接FTP下载文件异常");
				throw new Exception("连接FTP下载文件异常");
			}
			if (!res) 
			{
				CommonLogger.info("未从FTP获取到文件【" + dlBean.getFileName() + "】");
				downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_WAITFILE);
				downBean.setDealLog("未从FTP获取到文件，等待FMS返回文件");
				updateBean.setDataFlag(FmsValues.FMS_DOWNLOAD_WAITFILE);
				updateBean.setDealLog("未从FTP获取到文件，等待FMS返回文件");
//				throw new Exception("未从FTP获取到文件");//未下载到文件不抛出异常
				return null;
			}
		}
		else//暂时从FTP下载
		{
			boolean res = false;
			try {
				res = downloadFromFtp(dlBean.getTradeType(),downloadpath,dlBean.getFileName());
			} catch (DownLoadException e) {
				downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
				downBean.setDealLog("连接FTP下载文件异常");
				updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
				updateBean.setDealLog("连接FTP下载文件异常");
				throw new Exception("连接FTP下载文件异常");
			}
			if (!res) 
			{
				CommonLogger.info("未从FTP获取到文件【" + dlBean.getFileName() + "】");
				downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_WAITFILE);
				downBean.setDealLog("未从FTP获取到文件，等待FMS返回文件");
				updateBean.setDataFlag(FmsValues.FMS_DOWNLOAD_WAITFILE);
				updateBean.setDealLog("未从FTP获取到文件，等待FMS返回文件");
//				throw new Exception("未从FTP获取到文件");//未下载到文件不抛出异常
				return null;
			}
		}
		
		//上送sftp,用于集群下载
		String ftpPath = CommonFileUtils.getFtpPath(downloadpath);
		String localFilePath = downloadpath + "/" + dlBean.getFileName();
		CommonLogger.info("开始向sftp上传接口文件："+dlBean.getFileName()+",上传路径："+ftpPath);
		String shareIp = WebHelp.getSysPara("FTP_SHAREFILE_HOSTADD");//FTP文件共享服务器地址
		int sharePort = Integer.parseInt(WebHelp.getSysPara("FTP_SHAREFILE_PORT"));//FTP文件共享服务器端口
		String shareUser = WebHelp.getSysPara("FTP_SHAREFILE_USER");//FTP文件共享服务器用户名
		String sharePwd = WebHelp.getSysPara("FTP_SHAREFILE_PWD");//FTP文件共享服务器密码
		SFTPTool sftpTool = SFTPTool.getNewInstance();
		sftpTool.uploadFile(shareIp, sharePort, shareUser, sharePwd, 
				ftpPath, new File(localFilePath));
		
		String desUnZipFileName = "";
		try
		{
			desUnZipFileName = dlBean.getFileName();
			String filePath = SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER") + "/" + dlBean.getTradeDate();
			// 文件解压
			desUnZipFileName = dlBean.getFileName().replace(".gz", "");
			// 直接将压缩文件后缀去掉获得解压后文件名，该处根据最后确定的压缩文件名规则可能需要调整
			GzipUtil.uncompressGZIP( filePath+ File.separator + dlBean.getFileName(), 
					filePath,  desUnZipFileName);
			//获得需要进行处理的本地文件
			File downloadFile = new File(filePath, desUnZipFileName);
			if (downloadFile == null || !downloadFile.exists()) {
				CommonLogger.error("本地路径(" + filePath + ")下不存在文件【" + desUnZipFileName + "】");
				throw new Exception("本地路径(" + filePath + ")下不存在文件【" + desUnZipFileName + "】");
			}
			//保存下载路径
			String downloadPath = SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER") + "/" + dlBean.getTradeDate() + "/" + dlBean.getFileName();
			File downFile = new File(downloadPath);
			downloadPath = downFile.getAbsolutePath();
			if(dlBean.getTradeType().startsWith("3"))
			{
				UpLoadBean upBean = new UpLoadBean();
				upBean.setBatchNo(dlBean.getBatchNo());
				upBean.setDownloadPath(downloadPath);
				dao.updateUploadDownPath(upBean);
			}
			else
			{
				FMSDownloadBean bean = new FMSDownloadBean();
				bean.setBatchNo(dlBean.getBatchNo());
				bean.setDownloadPath(downloadPath);
				bean.setFileName(dlBean.getFileName());
				dao.updateDownDownPath(bean);
			}
		}
		catch(Exception e)
		{
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
			downBean.setDealLog("结果文件download处理异常");
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			updateBean.setDealLog("校验文件download处理异常");
			e.printStackTrace();
			throw new Exception("从FTP下载文件失败");
		}
		return desUnZipFileName;
		
	}
	
	public void fileDeal(DownLoadBean dlBean,UpLoadBean updateBean,FMSDownloadBean downBean) throws Exception {
		String filePath = SystemParamManage.getInstance().getParaValue("FMS_DOWNLOAD_LOCAL_FOLDER") + "/" + dlBean.getTradeDate();
		//加载数据
		int fileCount = 0;
		try 
		{
			fileCount = new LoadData().load(dlBean.getTradeType(), filePath, dlBean.getFileName(),updateBean,downBean);
		} 
		catch (Exception e1) 
		{
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			throw e1;
		}
		//查询临时表条数
		int allCnt = getLoadCount(dlBean.getTradeType());
		if(!"13".equals(dlBean.getTradeType()) && fileCount != allCnt)
		{
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
			downBean.setDealLog("sqlldr加载数据校验错误，接口文件行数不等于导入数据库行数");
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			updateBean.setDealLog("sqlldr加载数据校验错误，接口文件行数不等于导入数据库行数");
			throw new Exception("sqlldr加载数据校验错误，接口文件行数不等于导入数据库行数");
		}
		//转移临时表数据到正式表
		try {
			transTmpData(dlBean.getBatchNo(),dlBean.getTradeType());
		} catch (Exception e) {
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
			downBean.setDealLog("临时表转移数据到正式表出错");
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			updateBean.setDealLog("临时表转移数据到正式表出错");
			throw new Exception("临时表转移数据到正式表出错");
		}
		
		//更新数量和状态为处理中
		if(dlBean.getTradeType().startsWith("3"))
		{
			// 更新汇总表状态为“下载处理中”
			UpLoadBean bean = new UpLoadBean();
			bean.setBatchNo(dlBean.getBatchNo());
			bean.setDataFlag(FmsValues.FMS_DOWN_DEALING);
			updateStatus(bean);
		}
		else
		{
			FMSDownloadBean bean = new FMSDownloadBean();
			bean.setBatchNo(dlBean.getBatchNo());
			bean.setAllCnt(allCnt);
			bean.setDataFlag(FmsValues.FMS_DOWNLOAD_DEALING);
			updateDlStatus(bean);
		}
	}
	
	
	public int getLoadCount(String tradeType) throws Exception {
		FMSBean fms = FMSConfig.getFMS(tradeType);
		String tableNameTmp = fms.getTableNameTmp();
		return dao.getLoadCount(tableNameTmp);
	}

	public void transTmpData(String batchNo, String tradeType) throws Exception {
		FMSBean fms = FMSConfig.getFMS(tradeType);
		String tranTmpSql = fms.getFile().getTranTmpSql();
		if(null!=tranTmpSql && !"".equals(tranTmpSql.trim()))
		{
			tranTmpSql = tranTmpSql.replace("{0}", "'"+batchNo+"'");
			String tableName = "";
			if(tradeType.startsWith("3"))
			{
				tableName = fms.getCheckTableName();
			}
			else
			{
				tableName = fms.getTableName();
			}
			//先删除正式表该批次数据(检验文件为：check表；结果文件为tid正式表)
			dao.delFormalTableData(batchNo,tableName);
			dao.transTmpData(tranTmpSql);
		}
	}
	
	public void truncateTmpData(String truncateTmpSql) throws Exception
	{
		if(null!=truncateTmpSql && !"".equals(truncateTmpSql.trim()))
		{
			dao.truncateTmpData(truncateTmpSql);
		}
	}

	public void dealFmsFile(String prcName,String batchNo) {
		String fullProcName = prcName + "('" + batchNo + "')";
		dao.callProcedure(fullProcName);
	}

	public String getFirstTradeDate(String tradeTypes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tradeTypes",  Arrays.asList(tradeTypes.split(",")));
		map.put("cnt", tradeTypes.split(",").length);	
		return dao.getFirstTradeDate(map);
	}

	public String getDealBatchNo(String tradeDate, String tradeType) {
		return dao.getDealBatchNo(tradeDate,tradeType);
	}

	public List<FMSDownloadBean> unsuccTask(String tradeDate) {
		return dao.unsuccTask(tradeDate);
	}

	public List<UpLoadBean> getUploadTask() {
		return dao.getUploadTask();
	}

	public boolean downloadFromFtp(String tradeType, String downloadpath, String fileName) throws DownLoadException
	{
		CommonLogger.info("从ftp下载文件！,FMSService,downloadFromFtp()");
		FTPBean ftp = new FTPBean();
		boolean res = false;
		try {
			res = Tool.FTP.getFile1(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
					ftp.getDownloadFolder(tradeType), fileName, downloadpath, fileName);
		} catch (Exception e) {
			insertException();
			e.printStackTrace();
			CommonLogger.error("下载"+fileName+"连接FTP失败");
			throw new DownLoadException("连接FTP下载文件失败");
		}
		return res;
	}
	
	public boolean downloadFromLocal(String tradeType, String downloadpath, String fileName) throws DownLoadException
	{
//		CommonLogger.info("从本地下载文件！,FMSService,downloadFromFtp()");
//		FTPBean ftp = new FTPBean();
//		File[] files = new File(ftp.getDownloadFolder(tradeType)).listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File pathname) {
//				String pathFileName = pathname.getName();
//				return pathFileName.matches(fileName);
//			}
//		});
		return false;
	}

	public void tranDataCleanLog(String batchNo, String tradeType) throws Exception {
		FMSBean fms = FMSConfig.getFMS(tradeType);
		String tableNameTmp = fms.getTableNameTmp();
		dao.delCleanLog(batchNo);
		dao.insertCleanLog(batchNo,tableNameTmp);
	}
	
	public void updateCheckStatus(String mergeSql,String batchNo) throws Exception
	{
		if(null==mergeSql || "".equals(mergeSql))
		{
			throw new Exception("参数mergeSql不能为空");
		}
		mergeSql = mergeSql.replace("{0}", "'"+batchNo+"'");
		dao.updateCheckStatus(mergeSql);
	}

	public String getDataFlagByBatchNo(String batchNo) {
		return dao.getDataFlagByBatchNo(batchNo);
	}

	public void downloadInitStatus(String batchNo, String fmsDownloadFordeal) {
		dao.downloadInitStatus(batchNo,fmsDownloadFordeal);
		
	}

	public void uploadInitStatus(String batchNo) {
		dao.uploadInitStatus(batchNo);
	}

	public void updateCostTime(String batchNo,String costMills) {
		dao.updateCostTime(batchNo,costMills);
	}
}
