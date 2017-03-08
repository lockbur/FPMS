package com.forms.prms.web.sysmanagement.uploadfilemanage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.sysmanagement.uploadfilemanage.domain.UpFileBean;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;
import com.forms.prms.web.util.ForwardPageUtils;
import com.sun.star.io.IOException;

/**
 * Title: UpFileManagerController Description: 文件上传管理模块Controller层 Copyright:
 * formssi
 * 
 * @author: HQQ
 * @project: ERP
 * @date: 2015-03-20
 * @version: 1.0
 */
@Controller
@RequestMapping("/sysmanagement/uploadfilemanage/")
public class UpFileManagerController {

	private static final String BASE_URL = "sysmanagement/uploadfilemanage/";

	@Autowired
	private UpFileManagerService service;
	@Autowired
	private FmsMgrService fmsService;

	@Autowired
	public DBFileOperUtil DbFileUtil;

	@Autowired
	// 用于文件下载功能
	private FmsMgrService fmsMgrService;

	/**
	 * @methodName listUpFile FUNC_ID：080901
	 *             上传文件列表查询页面：查询符合条件的上传文件信息列表，将查询结果返回JSP页面(无操作功能的查询页面)
	 * @param upFile
	 *            存放List的查询条件
	 * @return
	 */
	@RequestMapping("listUpFile.do")
	public String listUpFile(UpFileBean upFile) {
		List<UpFileBean> upFileList = service.list(upFile);
		WebUtils.setRequestAttr("upFile", upFile); // 查询条件回显
		WebUtils.setRequestAttr("upFileList", upFileList); // 查询结果展示
		return BASE_URL + "upFileListQuery";
	}

	/**
	 * @methodName listUpFileManage FUNC_ID：080902 上传文件列表管理页面，有操作权限才能访问的功能页面
	 * @param upFile
	 *            存放List的查询条件
	 * @return
	 */
	@RequestMapping("listUpFileManage.do")
	@AddReturnLink(id = "list", displayValue = "返回上传文件列表")
	public String listUpFileManage(UpFileBean upFile) {
		List<UpFileBean> upFileManageList = service.list(upFile);
		WebUtils.setRequestAttr("upFile", upFile); // 查询条件回显
		WebUtils.setRequestAttr("upFileList", upFileManageList); // 查询结果展示
		return BASE_URL + "upFileListManage";
	}

	/**
	 * @methodName preAddUpFile Func_ID:08090200 跳转至新增上传文件的JSP页面
	 * @return
	 */
	@RequestMapping("preAddUpFile.do")
	public String preAddUpFile() {
		return BASE_URL + "preAddUpFilePage";
	}

	/**
	 * @methodName saveUpFile Func_ID:08090201 新增上传文件，保存上传文件信息
	 * @param upFile
	 * @return
	 */
	@RequestMapping("saveUpFile.do")
	public String saveUpFile(UpFileBean upFile) {
		try {
			service.addUpFileInfo(upFile);
			WebUtils.getMessageManager().addInfoMessage("新增上传文件成功！");
			ReturnLinkUtils.setShowLink("list");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			WebUtils.getMessageManager().addErrorMessage(
					"新增上传文件发生异常！异常信息为：" + e.getMessage().substring(0, 300));
			ReturnLinkUtils.setShowLink("list");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * @methodName deleteUpFile FUNC_ID：08090202 删除上传文件信息及文件
	 * @param upFile
	 * @return
	 */
	@RequestMapping("deleteUpFile.do")
	public String deleteUpFile(UpFileBean upFile) {
		service.deleteUpFile(upFile);
		WebUtils.getMessageManager().addInfoMessage("删除文件成功！");
		ReturnLinkUtils.setShowLink("list");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * @methodName viewUpFileInfo FUNC_ID：08090203 查看上传文件明细信息
	 * @param upFile
	 * @return
	 */
	@RequestMapping("viewUpFileInfo.do")
	@AddReturnLink(id = "view", displayValue = "上传文件详情")
	public String viewUpFileInfo(UpFileBean upFile) {
		UpFileBean upFileInfo = service.getUpFileById(upFile.getFileId());
		WebUtils.setRequestAttr("upFileInfo", upFileInfo);
		return BASE_URL + "viewUpFile";
	}

	/**
	 * @methodName updateUpFileInfo FUNC_ID：08090204 修改更新上传文件信息
	 * @param upFile
	 * @return
	 */
	@RequestMapping("updateUpFileInfo.do")
	public String updateUpFileInfo(UpFileBean upFile) throws Exception {
		int result = service.updateFileInfo(upFile);
		if (result > 0) {
			// 更新操作成功
			WebUtils.setRequestAttr("upFileInfo",
					service.getUpFileById(upFile.getFileId()));
			WebUtils.getMessageManager().addInfoMessage("修改文件成功！");
			ReturnLinkUtils.setShowLink(new String[] { "view", "list" });
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("修改文件失败！");
			ReturnLinkUtils.setShowLink(new String[] { "list" });
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * @methodName upFileDownload FUNC_ID：08090206 文件下载功能操作
	 * @param request
	 * @param response
	 * @param upFile
	 *            上传文件(传递进来的对象仅带有fileId属性)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upFileDownload.do")
	public String upFileDownload(HttpServletRequest request,
			HttpServletResponse response, UpFileBean upFile) {
		try {
			service.upFileDownload(request, response, upFile);
		} catch (Exception e) {
			// 下载操作有错，提示错误信息，跳转至错误页面并返回链接按钮
			WebUtils.getMessageManager().addErrorMessage(
					"【下载异常】<br/>该文件在本地Server以及FTP中均不存在，<br/>请联系管理员检查，文件ID为："
							+ upFile.getFileId());
			ReturnLinkUtils.setShowLink("list");
			return ForwardPageUtils.getErrorPage();
		}
		// 下载操作无错，直接将文件下载
		return null;
	}

	/**
	 * @methodName checkDownFileExist FUNC_ID：08090207
	 *             Ajax校验需要下载的下载文件是否存在，当不存在时提示用户且不执行下载操作，否则执行下载操作
	 * @param fileId
	 *            需要下载的文件ID
	 * @return
	 */
	@RequestMapping("checkDownFileExist.do")
	@ResponseBody
	public String checkDownFileExist(String fileId) {
		AbstractJsonObject json = new SuccessJsonObject();
		boolean checkFlag = false; // 校验结果标识
		String checkMsg = ""; // 校验结果MSG(当校验结果为false时，该对象才会有值)
		try {
			File downFile = new File(service.getUpFileById(fileId)
					.getSourceFPath());
			if ((!downFile.exists()) || !(downFile.isFile())) {
				CommonLogger.debug("【文件存在性检测】：根本不存在  "
						+ service.getUpFileById(fileId).getSourceFPath());
				checkMsg = "需要下载的文件不存在当前Server，请检查！";
			} else {
				checkFlag = true;
				CommonLogger.debug("【文件存在性检测】：存在   "
						+ service.getUpFileById(fileId).getSourceFPath());
			}
		} catch (Exception e) {
			checkMsg = "需要下载的文件不存在当前Server，请检查！";
		}
		json.put("fileId", fileId); // 用于Ajax检测后的下载操作中参数传输
		json.put("checkFlag", checkFlag);
		json.put("checkMsg", checkMsg);
		return json.writeValueAsString();
	}

	/**
	 * 跳到日志文件下载页面
	 * 
	 * @return
	 */
	@RequestMapping("logdownload.do")
	public String logdownload(UpFileBean bean) {
		ReturnLinkUtils.addReturnLink("logdownload", "返回日志下载");
		// 得到系统日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date = df.format(new Date());
		String url="";
		if(bean.getStartDate()==null||bean.getEndDate()==null){
			url="logdownload";
		}
		else{
			url="logdownloadList";
		}
		if (bean.getStartDate() == null || bean.getStartDate() == "") {
			bean.setStartDate(date);
		}
		if (bean.getEndDate() == null || bean.getEndDate() == "") {
			bean.setEndDate(date);
		}
		// 找到日期区间的所有日志集合
		List<String> logList = logList1(bean);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("logList", logList);
		return BASE_URL + url;
	}

	private List<String> logList(UpFileBean bean) {
		Properties prop = new Properties();
		InputStream in = UpFileManagerController.class
				.getResourceAsStream("/log4j.properties");
		List<String> logList = new ArrayList<String>();
		try {
			prop.load(in);
			String commonPath = prop.getProperty(
					"log4j.appender.InfoLogger.File").trim();
			String errorPath = prop.getProperty(
					"log4j.appender.ErrorLogger.File").trim();
			Date start = new SimpleDateFormat("yyyy-MM-dd").parse(bean
					.getStartDate());
			Date end = new SimpleDateFormat("yyyy-MM-dd").parse(bean
					.getEndDate());
			List<Date> dates = this.getDates(start, end);
			for (int i = dates.size() - 1; i >= 0; i--) {
				Date date = dates.get(i);
				// for (Date date : dates) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				String nowDate = df.format(new Date());
				String dateTemp = new SimpleDateFormat("yyyy-MM-dd")
						.format(date);
				// 如果该日期为当前日期则下载common.log和error.log
				if (dateTemp.equals(nowDate)) {
					File commonFilePath = new File(commonPath);
					File errorFilePath = new File(errorPath);
					if (commonFilePath.exists()) {
						logList.add(commonFilePath.getName());
					}
					if (errorFilePath.exists()) {
						logList.add(errorFilePath.getName());
					}
				} else {
					File commonFilePath = new File(commonPath + "_" + dateTemp
							+ ".log");
					File errorFilePath = new File(errorPath + "_" + dateTemp
							+ ".log");
					if (commonFilePath.exists()) {
						logList.add(commonFilePath.getName());
					}
					if (errorFilePath.exists()) {
						logList.add(errorFilePath.getName());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logList;
	}

	private List<String> logList1(UpFileBean bean) {
		Properties props = System.getProperties(); // 获得系统属性集
		String osName = props.getProperty("os.name").toUpperCase();
		// WINDOW XP
		String sysParmter = null;
		if (osName.startsWith("WINDOWS")) {
			sysParmter = WebHelp
					.getSysPara("OSTYPE_WINDOWS_SERVER_FILE_ROOT_PATH");
		}
		// Unix
		else  {
			sysParmter = WebHelp
					.getSysPara("OSTYPE_UNIX_SERVER_FILE_ROOT_PATH");
		}
		String filePath = sysParmter + "/log";
		File file = new File(filePath);
		File[] tempList = file.listFiles();
		List<String> gzList = new ArrayList<String>();
		try {
			Date start = new SimpleDateFormat("yyyy-MM-dd").parse(bean
					.getStartDate());
			Date end = new SimpleDateFormat("yyyy-MM-dd").parse(bean
					.getEndDate());
			List<Date> dates = this.getDates(start, end);
			
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isDirectory()) {
					List<String> ipFolder = new ArrayList<String>();
					for (int j = dates.size() - 1; j >= 0; j--) {
						Date date = dates.get(j);
						// for (Date date : dates) {
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
						String nowDate = df.format(new Date());
						String dateTemp = new SimpleDateFormat("yyyyMMdd")
								.format(date);
						// 如果该日期为当前日期则下载common.log和error.log
						if (dateTemp.equals(nowDate)) {
							File commonFilePath = new File(filePath + "/"
									+ tempList[i].getName() + "/fpms_info.log");
							File errorFilePath = new File(filePath + "/"
									+ tempList[i].getName() + "/fpms_error.log");
							File debugFilePath = new File(filePath + "/"
									+ tempList[i].getName() + "/fpms_debug.log");
							if (commonFilePath.exists()) {
								ipFolder.add(commonFilePath.getName());
							}
							if (errorFilePath.exists()) {
								ipFolder.add(errorFilePath.getName());
							}
							if (debugFilePath.exists()) {
								ipFolder.add(errorFilePath.getName());
							}
						} else {
							File commonFilePath = new File(filePath + "/"
									+ tempList[i].getName() + "/fpms_info.log."
									+ dateTemp);
							File errorFilePath = new File(filePath + "/"
									+ tempList[i].getName()
									+ "/fpms_error.log." + dateTemp);
							File debugFilePath = new File(filePath + "/"
									+ tempList[i].getName()
									+ "/fpms_debug.log." + dateTemp);
							if (commonFilePath.exists()) {
								ipFolder.add(commonFilePath.getName());
							}
							if (errorFilePath.exists()) {
								ipFolder.add(errorFilePath.getName());
							}
							if (debugFilePath.exists()) {
								ipFolder.add(errorFilePath.getName());
							}
						}
					}
					if(ipFolder.size()>0){
						gzList.add(tempList[i].getName()+".gz");
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gzList;
	}

	/**
	 * 下载log文件
	 * 
	 * @return
	 */
	@RequestMapping("downloadLog.do")
	public String download(HttpServletResponse response, UpFileBean bean)
			throws Exception {
		try {
			// 下载时间段内的日志文件
			List<File> files = new ArrayList<File>();
			Date start = new SimpleDateFormat("yyyy-MM-dd").parse(bean
					.getStartDate());
			Date end = new SimpleDateFormat("yyyy-MM-dd").parse(bean
					.getEndDate());
			List<Date> dates = this.getDates(start, end);
			Properties props = System.getProperties(); // 获得系统属性集
			String osName = props.getProperty("os.name").toUpperCase();
			// WINDOW XP
			String sysParmter = null;
			if (osName.startsWith("WINDOWS")) {
				sysParmter = WebHelp
						.getSysPara("OSTYPE_WINDOWS_SERVER_FILE_ROOT_PATH");
			}
			// Unix
			else {
				sysParmter = WebHelp
						.getSysPara("OSTYPE_UNIX_SERVER_FILE_ROOT_PATH");
			}
			String oldGzName=bean.getGzName();//现保存压缩文件名供后面使用
			String  gzName=bean.getGzName();//得到要下载的压缩文件名称
			gzName=gzName.substring(0, gzName.lastIndexOf("."));//得到去掉后缀的名字
			String filePath = sysParmter + "/log/"+gzName;
			for (Date date : dates) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
				String nowDate = df.format(new Date());
				String dateTemp = new SimpleDateFormat("yyyyMMdd").format(date);
				// 如果该日期为当前日期则下载common.log和error.log
				if (dateTemp.equals(nowDate)) {
					File commonFilePath = new File(filePath+"/fpms_info.log");
					File errorFilePath = new File(filePath+"/fpms_error.log");
					File debugFilePath = new File(filePath+"/fpms_debug.log");
					if (commonFilePath.exists()) {
						files.add(commonFilePath);
					}
					if (errorFilePath.exists()) {
						files.add(errorFilePath);
					}
					if (debugFilePath.exists()) {
						files.add(debugFilePath);
					}
				} else {
					File commonFilePath = new File(filePath + "/fpms_info.log."+ dateTemp);
					File errorFilePath = new File(filePath+ "/fpms_error.log."+ dateTemp);
					File debugFilePath = new File(filePath+ "/fpms_debug.log."+ dateTemp);
					if (commonFilePath.exists()) {
						files.add(commonFilePath);
					}
					if (errorFilePath.exists()) {
						files.add(errorFilePath);
					}
					if (debugFilePath.exists()) {
						files.add(debugFilePath);
					}
				}
			}
			// 创建一个临时文件与Log日志路径相同
			String compreeFileFolder = sysParmter+"/log";
			File compreeFile = new File(compreeFileFolder);
			if (!compreeFile.exists()) {
				compreeFile.mkdirs();
			}
			File file = new File(compreeFileFolder +"/"+oldGzName );
			if (!file.exists()) {
				file.createNewFile();
			}
			response.reset();
			// response.getWriter()
			// 创建文件输出流
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			// 选择的日期中没有文件
			if (files.size() < 1) {
				ReturnLinkUtils.setShowLink("logdownload");
				WebUtils.getMessageManager().addInfoMessage("该日期区间不存在日志文件！");
				return ForwardPageUtils.getErrorPage();
			}
			compressFile(files, out, basedir);
			out.close();
			// zipFile(files, out);
			fileOutputStream.close();
			downloadZip(file, response, bean,oldGzName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private void compressFile(File file, ZipOutputStream out, String basedir) {
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[8192];
			while ((count = bis.read(data, 0, 8192)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Date> getDates(Date beginDate, Date endDate) {
		List lDate = new ArrayList();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		String start = new SimpleDateFormat("yyyy-MM-dd").format(beginDate);
		String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		// 如果开始时间和结束时间不同
		if (!(start.equals(end))) {
			lDate.add(endDate);// 把结束时间加入集合
		}
		return lDate;

	}

	public void downloadZip(File file, HttpServletResponse response,
			UpFileBean bean,String oldGzName) throws java.io.IOException, IOException {
		try {
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(
					file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			String filedisplay = null;
			// 设置显示的文件名
			String filenamedisplay = URLEncoder.encode(oldGzName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filenamedisplay);
			// 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} finally {
			try {
				File f = new File(file.getPath());
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void compressFile(List<File> files, ZipOutputStream outputStream,
			String basedir) throws Exception {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			compressFile(file, outputStream, basedir);
		}
	}

	public static void zipFile(File inputFile, ZipOutputStream ouputStream)
			throws Exception {
		try {
			if (inputFile.exists()) {
				/**
				 * 如果是目录的话这里是不采取操作的， 至于目录的打包正在研究中
				 */
				if (inputFile.isFile()) {
					FileInputStream IN = new FileInputStream(inputFile);
					BufferedInputStream bins = new BufferedInputStream(IN, 512);
					// org.apache.tools.zip.ZipEntry
					ZipEntry entry = new ZipEntry(inputFile.getName());
					try {
						ouputStream.putNextEntry(entry);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
					// 关闭创建的流对象
					bins.close();
					IN.close();
				} else {
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(files[i], ouputStream);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}