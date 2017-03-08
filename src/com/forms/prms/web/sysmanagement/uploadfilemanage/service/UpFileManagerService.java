package com.forms.prms.web.sysmanagement.uploadfilemanage.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.SFTPTool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.sysmanagement.uploadfilemanage.dao.UpFileManagerDAO;
import com.forms.prms.web.sysmanagement.uploadfilemanage.domain.UpFileBean;

/**
 * Title: UpFileManagerService Description: 上传文件管理Service层 Copyright: formssi
 * 
 * @author： HQQ
 * @project： ERP
 * @date： 2015-03-18
 * @version： 1.0
 */
@Service
public class UpFileManagerService {

	@Autowired
	public UpFileManagerDAO dao;

	@Autowired
	public DBFileOperUtil DbFileUtil;

	@Autowired
	// 用于文件下载功能
	private FmsMgrService fmsMgrService;

	/**
	 * 获取文件上传管理Service的bean实例
	 * 
	 * @return
	 */
	public static UpFileManagerService getInstance() {
		return SpringUtil.getBean(UpFileManagerService.class);
	}

	/**
	 * @methodName list 查询上传文件信息列表(带查询条件的upFile)
	 * @param upFile
	 * @return
	 */
	public List<UpFileBean> list(UpFileBean upFile) {
		CommonLogger.info("[执行上传文件列表查询],UpFileManagerService,list()");
		UpFileManagerDAO pageDao = PageUtils.getPageDao(dao);
		return pageDao.getUpFileList(upFile);
	}

	/**
	 * @methodName getUpFileById 查询指定ID的上传文件信息
	 * @param upFile
	 * @return
	 */
	public UpFileBean getUpFileById(String fileId) {
		CommonLogger.info("[执行根据ID查询上传的文件],查询的文件ID为:" + fileId + ",UpFileManagerService,getUpFileById()");
		return dao.getUpFileById(fileId);
	}

	/**
	 * @methodName addUpFileInfo 新增保存上传文件信息 参数为UpFileBean
	 *             upFile，外部调用时，需要给upFile设置属性: sourceFName ：
	 *             上传文件源文件名、sourceFPath ：上传文件保存详细路径、fileType ：上传文件类型、fileDesc
	 *             ：上传文件描述、instOper ：文件上传/最终修改人员
	 * @param upFile
	 * @return
	 * @throws Exception
	 */
	public int addUpFileInfo(UpFileBean upFile) throws Exception {
		CommonLogger.info("[执行保存上传文件操作],保存文件的信息：[源文件名：" + upFile.getSourceFName() + "新文件名：" + upFile.getNewFName()
				+ "新文件路径：" + upFile.getNewFPath() + "],UpFileManagerService,addUpFileInfo()");
		// 1.将上传插件暂存的文件复制到指定的文件目录中
		String sourceFPath = upFile.getSourceFPath(); // 平台上传控件保存的源文件路径(X盘:/temp文件夹)
		String sourceFName = upFile.getSourceFPath().substring(upFile.getSourceFPath().lastIndexOf("/") + 1,
				upFile.getSourceFPath().length()); // 上传文件在重命名后的文件名
		String saveFileParentPath = WebHelp.getSysPara("UPFILE_MNGT_BASE_URL") + "/file_type" + upFile.getFileType();
		String saveFPath = saveFileParentPath + "/" + sourceFName; // 使用参数表配置上传文件保存路径
		String ftpPath = CommonFileUtils.getFtpPath(saveFileParentPath);
		CommonFileUtils.copyFile(sourceFPath, saveFPath, true); // 复制文件后，将源文件删除掉
		// 2.更新保存文件的相关信息(新路径+上传操作者+文件ID)
		upFile.setSourceFPath(saveFPath);
		upFile.setInstOper(WebHelp.getLoginUser().getUserId());
		upFile.setFileId(WebHelp.getLoginUser().getUserId() + "-" + dao.getDBTimeStamp()); // 需求：文件ID设置为操作者ID+时间戳
		CommonLogger.debug("[执行保存上传文件操作]:保存文件相关信息至DB中,文件ID：[" + upFile.getFileId()
				+ "],UpFileManagerService,addUpFileInfo()");
		int addCount = dao.saveUploadFileInfo(upFile);
		// 3.将上传的文件保存到数据库(进行集群管理)
		/*CommonLogger.info("[执行保存上传文件操作]：将上传文件保存至数据库,文件ID：[" + upFile.getFileId() + "文件保存路径:" + upFile.getNewFPath()
				+ "],UpFileManagerService,addUpFileInfo()");
		DbFileUtil.saveFileToDB(saveFPath);*/
		
		// 3.将上传的文件保存到FTP(进行集群管理)
		String shareIp = WebHelp.getSysPara("FTP_SHAREFILE_HOSTADD");//FTP文件共享服务器地址
		int sharePort = Integer.parseInt(WebHelp.getSysPara("FTP_SHAREFILE_PORT"));//FTP文件共享服务器端口
		String shareUser = WebHelp.getSysPara("FTP_SHAREFILE_USER");//FTP文件共享服务器用户名
		String sharePwd = WebHelp.getSysPara("FTP_SHAREFILE_PWD");//FTP文件共享服务器密码
		CommonLogger.info("[执行保存上传文件操作]：将上传文件保存至FTP,文件ID：[" + upFile.getFileId() + "文件保存路径:" + upFile.getNewFPath()
				+ "],UpFileManagerService,addUpFileInfo()");
		SFTPTool sftpTool = SFTPTool.getNewInstance();
		sftpTool.uploadFile(shareIp, sharePort, shareUser, sharePwd, 
				ftpPath, new File(saveFPath));
		return addCount;
	}

	/**
	 * @methodName updateFileInfo 更新修改上传文件信息
	 *             描述：如果用户重新上传了新的文件，则需要更改上传文件的[文件名]以及[文件路径],并且在SQL中更新最新的[上传时间]
	 * @param upFile
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateFileInfo(UpFileBean upFile) throws Exception {
		CommonLogger.info("[执行更新上传文件信息操作]：文件ID：[" + upFile.getFileId() + "更新的源文件名为:" + upFile.getSourceFName()
				+ "],UpFileManagerService,updateFileInfo()");
		String fileSourceName = upFile.getNewFName(); // 文件源文件名(NewFName为前端JSP中重命名的新文件名称，这里将会试用重命名的名称替换源文件名称)
		String fileTempName = ""; // 文件暂存名称
		String fileSavePath = ""; // 文件保存路径
		String saveFileParentPath = "";
		upFile.setInstOper(WebHelp.getLoginUser().getUserId()); // 更新该修改操作的操作者

		if (!Tool.CHECK.isEmpty(upFile.getNewFPath())) {
			// 当重新上传了文件时，进行文件流的复制(从平台控件临时保存地址，保存至服务器地址)
			fileTempName = upFile.getNewFPath().substring(upFile.getNewFPath().lastIndexOf("/") + 1,
					upFile.getNewFPath().length()); // 从插件中取得暂存文件名
			saveFileParentPath = WebHelp.getSysPara("UPFILE_MNGT_BASE_URL") + "/file_type" + upFile.getFileType();
			fileSavePath = saveFileParentPath + "/" + fileTempName; // 拼接最终保存文件的路径
			// 复制保存新上传的文件
			CommonFileUtils.copyFile(upFile.getNewFPath(), fileSavePath);
			// 因为重新上传了文件，所以更新文件的新文件名+文件路径
			upFile.setSourceFName(fileSourceName);
			upFile.setSourceFPath(fileSavePath);
		} else {
			if (!Tool.CHECK.isEmpty(upFile.getNewFName())) {
				// 如果没有上传新文件，由于上一个条件新文件名存在(只改了新的文件名)，则取文件源路径拼接新文件名，组合成新的文件路径
				upFile.setSourceFName(fileSourceName); // 将源文件名更改为新换的新文件名(文件路径不变)
			}
		}
		int updateCount = dao.updateUpFile(upFile);
		// 检查如果重新上传了文件，则将上传的文件保存到数据库(进行集群管理)
		/*if (!Tool.CHECK.isEmpty(upFile.getNewFPath())) {
			DbFileUtil.saveFileToDB(fileSavePath);
		}*/
		String ftpPath = CommonFileUtils.getFtpPath(saveFileParentPath);
		if (!Tool.CHECK.isEmpty(upFile.getNewFPath())) 
		{
			String shareIp = WebHelp.getSysPara("FTP_SHAREFILE_HOSTADD");//FTP文件共享服务器地址
			int sharePort = Integer.parseInt(WebHelp.getSysPara("FTP_SHAREFILE_PORT"));//FTP文件共享服务器端口
			String shareUser = WebHelp.getSysPara("FTP_SHAREFILE_USER");//FTP文件共享服务器用户名
			String sharePwd = WebHelp.getSysPara("FTP_SHAREFILE_PWD");//FTP文件共享服务器密码
			CommonLogger.info("[执行保存上传文件操作]：将上传文件保存至FTP,文件ID：[" + upFile.getFileId() + "文件保存路径:" + upFile.getNewFPath()
					+ "],UpFileManagerService,addUpFileInfo()");
			SFTPTool sftpTool = SFTPTool.getNewInstance();
			sftpTool.uploadFile(shareIp, sharePort, shareUser, sharePwd, 
					ftpPath, new File(fileSavePath));
		}
		
		return updateCount;
	}

	// 上传文件下载(重构方法)
	public boolean upFileDownload(HttpServletRequest request, HttpServletResponse response, String downFileId) {
		// 1.根据步骤一得到的文件ID值在UPLOAD_FILE_MNGT表中查找该上传文件对象
		UpFileBean upFile = this.getUpFileById(downFileId);
		boolean operResult = false;
		// 2.执行该上传文件的下载操作
		try {
			operResult = upFileDownload(request, response, upFile);
		} catch (Exception e) {
			e.printStackTrace();
			Throw.throwException(e);
		}
		return operResult;
	}

	/**
	 * 上传文件下载@methodName upFileDownload 方法操作逻辑：
	 * 1.判断当前服务器Server上是否存在该文件，存在时直接执行文件下载
	 * 2.当判断当前服务器Server上不存在该文件，先到集群数据库中将该文件下载到指定地址，再执行文件下载操作
	 * 
	 * @param request
	 * @param response
	 * @param upFile
	 * @return
	 */
	public boolean upFileDownload(HttpServletRequest request, HttpServletResponse response, UpFileBean upFile)
			throws Exception {
		upFile = this.getUpFileById(upFile.getFileId());
		// 调用集群处理方式执行文件下载操作
		boolean operFlag = false;
		try {
			operFlag = DbFileUtil.getFileDownFromPathAndDb(upFile.getSourceFPath(), upFile.getSourceFName(), request,
					response);
		} catch (Exception e) {
			// e.printStackTrace();
			CommonLogger.error("[文件下载操作]：文件下载发生异常    In upFileDownload() -- UpFileManagerService.class");
			Throw.throwException(e);
		}
		return operFlag;
	}

	/**
	 * @methodName deleteUpFile 删除上传文件的信息
	 * @param upFile
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteUpFile(UpFileBean upFile) {
		upFile = dao.getUpFileById(upFile.getFileId());
		File file = null;
		if (null != upFile.getSourceFPath() && "" != upFile.getSourceFPath()) {
			// 将该文件在集群DB中的备份保存删除掉
			DbFileUtil.deleteFileInDB(upFile.getSourceFPath());
			// 将当前服务器中，该文件删除掉
			file = new File(upFile.getSourceFPath());
			if (null != file) {
				// 如果存在该文件，则将该上传的物理文件删除
				file.delete();
			}
		}
		// 将上传文件管理表中该文件数据删除
		dao.deleteUpFile(upFile.getFileId());
	}

	public void someMethod() {
		// String clazzName = this.getClass().getName();
		// String methodName = this.getClass().getMethods();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("I ,doing something else!");
		System.out.println("ClassName:" + clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length())
				+ "; \n MethodName:" + method);

	}

	public static void main(String[] args) {
		UpFileManagerService ufms = new UpFileManagerService();
		ufms.someMethod();

		// String allFileName =
		// "D:/WorkSpace/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/HQERP/userUpload/20150331/311547398721.pdf";
		// String fileType =
		// allFileName.substring(allFileName.lastIndexOf("."),allFileName.length());
		// System.out.println(fileType);

		// List<String> upFileBaseUrls = getBaseUpFilePathUrlList();
	}

	public boolean changeFilePathByFileType(String sourceFilePath, String fileType) {
		// 根据上传文件更改了文件类型，所以该文件要从原始地址保存到新的地址，源文件删除
		boolean operResult = false;

		// 0.获取源文件的文件路径(全路径)+需要更改为的文件类型
		// 参数传进来的

		// 1.拼接获取新的类型文件夹路径
		String newFilePath = WebHelp.getSysPara("UPFILE_MNGT_BASE_URL") + "/" + fileType;

		// 2.复制文件,从源文件地址复制到新的文件类型路径
		try {
			CommonFileUtils.copyFile(sourceFilePath, newFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CommonLogger
					.debug("出现异常：在Step2 in UpFileManagerService CLASS , changeFilePathByFileType METHOD ! Please recheck !");
			return operResult;
		}

		// 3.删除原文件

		operResult = true;
		return operResult;
	}

	public void logDownload(HttpServletResponse response, String filePath) throws Exception {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				fis = new FileInputStream(file);
				OutputStream out = response.getOutputStream();
				bos = new BufferedOutputStream(out);
				byte[] b = new byte[8192];
				int data = 0;
				while ((data = fis.read(b)) != -1) {
					bos.write(b, 0, data);
				}
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
