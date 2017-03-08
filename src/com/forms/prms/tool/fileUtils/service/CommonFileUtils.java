package com.forms.prms.tool.fileUtils.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.fileUtils.controller.UploadFileUtils;

@Service
public class CommonFileUtils{
//	@Autowired
//	UploadFileUtils uFileUtils;
	
//	public static void main(String[] args) {
//		try {
////			copyFile("C:\\1.txt","C:\\1_copy.txt");
//			System.out.println("Come in Baby");
//			copyFile("D:\\ERP_FILE_MNGT\\FILE_TYPE\\02\\付款数据0730uploadTest.xls","C:\\akb.xls");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	/*	String path1 = "D:\\ERP_FILE_MNGT\\FILE_TYPE\\02\\付款数据0730uploadTest.xls";
		String path2 = "C:/AKB/BKC/ZHANGSANFENG/ZHANGXUEYOU/付款数据.txt";
		
		String result = changeRightPryToLeftPry(path1);
		System.out.println(result);*/
//		if(path2.contains("/")){
//			System.out.println("Path2 has  / 哦！！");
//		}
		
		
		
//		String filePath = "D:\\ERP_FILE_MNGT\\FILE_TYPE\\02";
//		File file = new File(filePath);
//		//判断文件路径是否存在，不存在则创建文件的路径，存在时则不管
//		if(!file.exists()){
//			createFileFolder(filePath);
//		}else{
//			System.out.println("文件路径已存在，不再创建");
//		}
//	}
	
	
	
	/**
	 * @methodName changeRightPryToLeftPry
	 * 		【工具类】：将路径上的右斜杠"\\"转换为左斜杠"/",再将最终结果路径返回
	 * @param pathStr
	 * @return
	 */
	public static String changeRightPryToLeftPry(String pathStr){
		CommonLogger.debug("执行文件夹路径分隔符[\\]替换为[/]操作,替换前路径为：["+pathStr+"],CommonFileUtils,changeRightPryToLeftPry");
		if(pathStr.contains("\\")){
			pathStr = pathStr.replaceAll("\\\\", "/");
		}
		CommonLogger.debug("文件夹路径分隔符替换后路径为：["+pathStr+"],CommonFileUtils,changeRightPryToLeftPry");
		return pathStr;
	}
	
	/**
	 * @methodName createFileFolder
	 * 		【工具类】：根据所给路径创建文件夹  
	 * 					根据所给路径，判断是否需要创建文件夹路径
	 * @param filePath		文件全路径(后续会截取文件夹路径)
	 * @param folderPath	文件夹路径
	 * @return
	 */
	public static File createFileFolder(String filePath , String folderPath){
		CommonLogger.debug("执行文件夹路径的创建操作,CommonFileUtils,createFileFolder()");
		
		//0.取得需要创建的文件夹路径,并统一文件夹分隔符
		String finalFolderPath = (Tool.CHECK.isEmpty(folderPath)? filePath : folderPath);		//有参数二时直接取值folderPath路径
		finalFolderPath = changeRightPryToLeftPry(finalFolderPath);
		//当路径取值为filePath时，取得最终需要创建的文件夹路径
		if(Tool.CHECK.isEmpty(folderPath)){			//判断参数二(folderPath)是否为空，不为空时，直接取该值作为创建的路径
			//当创建路径取值为文件路径filePath(文件)时，则截取最后部分不使用
			finalFolderPath = finalFolderPath.substring(0, finalFolderPath.lastIndexOf("/"));
		}
		
		//1.首先应该判断该文件夹路径是否存在，如果不存在则创建，存在就不管；
		if(!(new File(finalFolderPath).exists())){
			CommonLogger.debug("文件夹路径["+finalFolderPath+"]不存在，将会被创建!,CommonFileUtils,createFileFolder()");
			//若文件路径不存在，则创建uFile的工具类实例(用于创建文件夹路径)
			UploadFileUtils uFileUtils = new UploadFileUtils();
			File createdFolder = null ;
			try {
				createdFolder = uFileUtils.invokeBuildFloder(finalFolderPath);
				CommonLogger.debug("文件路径["+finalFolderPath+"]已创建完成！,CommonFileUtils,createFileFolder()");
			} catch (Exception e) {
				CommonLogger.error("执行创建文件夹路径发生异常，文件创建路径["+finalFolderPath+"]有误，请检查！,CommonFileUtils,createFileFolder()");
				e.printStackTrace();
				Throw.throwException(e);
			}
			return createdFolder;
		}else{
			CommonLogger.debug("文件夹路径["+finalFolderPath+"]已经存在，不需要创建！！,CommonFileUtils,createFileFolder()");
			return null;
		}
	}
	
	/**
	 * @methodName copyFile
	 *		【工具类】：  IO流复制多个文件(将list中的流文件复制到指定的url中)
	 * @param impFiles	多个文件(List<MultipartFile>)
	 * @param urls		目标路径(数组)
	 * @throws IOException
	 */
	public static void copyFiles(List<MultipartFile> impFiles , String[] urls) throws IOException{
		CommonLogger.debug("执行批量文件复制操作START-----------,CommonFileUtils,copyFiles()");
		for(int i=0;i<urls.length;i++){
			CommonLogger.debug("[批量复制文件中]第"+(i+1)+"次,复制源文件是："+impFiles.get(i).getOriginalFilename()+"复制到路径："+urls[i]+",CommonFileUtils,copyFiles()");
			OutputStream out = null;
			InputStream in = null;
			try {
				out = new FileOutputStream(urls[i]);
				in = impFiles.get(i).getInputStream();
				IOUtils.copy(in, out);
			} catch (IOException e) {
				throw e; 
			}finally{
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
		}
		CommonLogger.debug("执行批量文件复制操作END-----------,CommonFileUtils,copyFiles()");
	}
	
	/**
	 * @methodName copyFile
	 * 		【工具类】：复制文件，从地址A复制到地址B  
	 * @param sourceFileUrl	 源文件路径
	 * @param targetFileUrl	目标文件路径
	 * @param delSourceFile	是否删除源文件(布尔值)，为true时代表将文件复制之后将源文件删除；为false时代表复制文件后仍保留源文件
	 * @throws Exception
	 */
	public static void copyFile(String sourceFileUrl , String targetFileUrl , boolean delSourceFile) throws Exception{
		CommonLogger.info("[进入执行复制文件操作],CommonFileUtils,copyFile()");
		//1.首先校验需要复制的源文件是否存在(当源文件都不存在时，就不用执行文件复制的操作)
		if(!judgeFileExist(sourceFileUrl)){
			System.out.println("待复制的源文件根本不存在!");
			CommonLogger.error("复制文件时发生异常，复制的源文件不存在，路径为："+sourceFileUrl);
			throw new Exception("[异常]：待复制的源文件不存在,无法执行文件复制操作！源文件路径为："+sourceFileUrl);
		}
		//2.替换统一路径(将右斜杠替换为左斜杠)
		sourceFileUrl = changeRightPryToLeftPry(sourceFileUrl);		
		targetFileUrl = changeRightPryToLeftPry(targetFileUrl);
		OutputStream out = null;
		FileInputStream in = null;
		//3.创建目标文件路径，启动文件复制操作
		try {
			//目标文件路径如果不存在，则负责创建文件夹路径
			createFileFolder(targetFileUrl,null);		//需要传入文件路径
			//设置输入输出流，copy文件
			out = new FileOutputStream(targetFileUrl);
			in = new FileInputStream(sourceFileUrl);
			IOUtils.copy(in, out);
			//根据第三个参数delSourceFile决定是否删除复制的源文件
			if(delSourceFile){
				CommonFileUtils.deleteFileByPath(sourceFileUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Throw.throwException("copyFile Step3 has Some Error!!");
		} finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		
	}
	
	
	/**
	 * @methodName copyFile
	 * 		描述：copyFile的重构方法，当无第三个参数时，默认不将源文件删除  
	 * @param sourceFileUrl
	 * @param targetFileUrl
	 * @throws Exception
	 */
	public static void copyFile(String sourceFileUrl , String targetFileUrl) throws Exception{
		CommonLogger.debug("执行文件复制操作,由["+sourceFileUrl+"]复制至["+targetFileUrl+"],本次复制后将不删除源文件,CommonFileUtils,copyFiles");
		copyFile(sourceFileUrl , targetFileUrl , false);
	}
	
	
	/**
	 * @methodName FileDirCreate  文件目录不存在就创建它
	 * @param filePath	路径
	 */
	public static void FileDirCreate(String filePath) 
	{
		CommonLogger.debug("执行文件目录创建操作,创建的路径为"+filePath+",CommonFileUtils,FileDirCreate");
		File dir=new File(filePath);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
	}
	
	public static StringBuffer createFileDir(String filePath) 
	{
		CommonLogger.debug("执行文件目录创建操作,创建的路径为"+filePath+",CommonFileUtils,createFileDir");
		File dir=new File(filePath);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		return new StringBuffer(dir.getAbsolutePath());
	}
	
	public static String getFtpPath(String localPath) throws Exception
	{
		if(null == localPath || "".equals(localPath))
		{
			throw new Exception();
		}
		return localPath.substring(localPath.indexOf(":") + 1,
				localPath.length());
	}
	
	/**
	 * 文件下载
	 * @param response		响应
	 * @param filePath		下载的文件路径
	 * @param showFileName	下载文件保存名称
	 * @throws Exception 
	 */
	public void downloadFile( HttpServletResponse response, String filePath , String showFileName) throws Exception {
		CommonLogger.debug("执行文件下载操作,下载文件路径为"+filePath+",CommonFileUtils,downloadFile");
		this.downloadLocal(response , filePath , showFileName );
	}
	
	/**
	 * 本地文件下载
	 * @param templateFilePath
	 * @param response
	 * @throws Exception
	 */
	private void downloadLocal(HttpServletResponse response , String filePath , String showFileName) throws Exception
	{
		CommonLogger.debug("执行本地文件下载操作START-------,下载文件路径为:["+filePath+"],CommonFileUtils,downloadLocal()");
		try {
			//设置response响应参数
			response.setContentType("application/x-msdownload;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(showFileName.getBytes("GB2312"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			CommonLogger.error("[本地文件下载操作]：设置response时出现【异常】，请检查!下载文件路径为:["+filePath+"],显示文件名：["+showFileName+"]");
			e.printStackTrace();
			Throw.throwException(e);
		}
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		CommonLogger.debug("执行文件流复制操作,下载文件路径为:["+filePath+"],CommonFileUtils,downloadLocal()");
		try{	
			String fileRealPath = changeRightPryToLeftPry(filePath);			//统一文件分隔符"\\"---->"/"
			File file = new File(fileRealPath);
			if(!file.exists()){
				throw new Exception("[Exception]:找不到指定的下载文件，文件路径为："+fileRealPath);
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
			String simplename = e.getClass().getSimpleName();
			if(!"ClientAbortException".equals(simplename)){
				CommonLogger.debug("忽略客户端被无故关闭的异常！CommonFileUtils,downloadLocal()");
			}else{
				CommonLogger.error("[本地文件下载操作]：执行本地文件下载时出现异常!CommonFileUtils,downloadLocal()");
				e.printStackTrace();
				Throw.throwException(e);
			}
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
	
	//判断文件是否存在于指定的路径上
	public static boolean judgeFileExist( String filePath ){
		CommonLogger.info("进入文件是否存在判断，目标路径文件为：["+filePath+"],CommonFileUtils,judgeFileExist");
		File targetFile = new File(filePath);
		boolean judgeResult = targetFile.exists(); 
		CommonLogger.debug("文件是否存在："+judgeResult);
		return judgeResult;
	}
	
	
	//根据文件路径删除指定文件(首先会判断文件是否存在，再对文件进行删除)
	public static boolean deleteFileByPath( String filePath ){
		CommonLogger.info("进入文件删除操作，目标删除文件为：["+filePath+"],CommonFileUtils,deleteFileByPath");
		boolean operFlag = false;
		if(judgeFileExist(filePath)){
			CommonLogger.debug("指定路径["+filePath+"]文件存在，将开始执行文件删除操作");
			(new File(filePath)).delete();
			operFlag = true;
		}else{
			CommonLogger.debug("指定路径["+filePath+"]文件不存在，无需执行文件删除操作");
		}
		return operFlag; 
	}
	
	/**
	 * 【工具类】
	 * @methodName getExcelInfoByPath
	 * 		描述：	根据filePath文件路径获取上传Excel文件的相关信息(前缀路径+文件名等)  
	 * @param 	filePath
	 * @return	String[]:0=上传工具保存文件的路径、1=上传文件的前缀路径、2=上传文件重命名后的名称
	 */
	public static String[] getExcelInfoByPath(String filePath){
		CommonLogger.info("公共工具类-根据文件路径["+filePath+"]获取Excel文件的相关信息,CommonFileUtils,getExcelInfoByPath()");
		//1.统一路径分隔符：若存在，则将"\\"转换为"/"；
		filePath = changeRightPryToLeftPry(filePath);
		
		String[] excelInfos = new String[3];
		String filePrefixPath = filePath.substring(0, filePath.lastIndexOf("/")+1);					//上传文件的前缀路径
		String fileRename = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length()); 	//上传文件重命名后的名称
		excelInfos[0] = filePath; 
		excelInfos[1] = filePrefixPath; 
		excelInfos[2] = fileRename; 	
		return excelInfos;
	}
	
	/**
	 * @methodName replacePathSeparator
	 * 		【工具类】：替换文件路径分隔符，将路径中的"\\"替换为"/"，并将替换后的结果返回
	 * @param sourcePath  原始文件路径
	 * @return
	 */
	public static String replacePathSeparator(String sourcePath){
		//String filePath = "C:\\df\\dsfsd\\sdfsdf\\asfdfs/sdfsdf\\sdfsdf/sfsdgre/ehrerg\\egrg/erher.xlsx";
		String replacePath = "";
		if(sourcePath.contains("\\")){
			replacePath = sourcePath.replaceAll("\\\\", "/");
		}else{
			replacePath = sourcePath;
		}
		return replacePath;
	}
	
	
	
	public static void main(String[] args) {
		String filePath1 = "C:/HqqFileTest001.txt";
		String filePath2 = "C:/HqqFileTest002.txt";
		File file1 = new File(filePath1);
		File file2 = new File(filePath2);
		System.out.println("判断文件1是否存在："+CommonFileUtils.judgeFileExist(filePath1));
		System.out.println("判断文件2是否存在："+CommonFileUtils.judgeFileExist(filePath2));
		System.out.println("=============================================");
		
		System.out.println("删除文件1："+deleteFileByPath(filePath1));
		
	}

}
