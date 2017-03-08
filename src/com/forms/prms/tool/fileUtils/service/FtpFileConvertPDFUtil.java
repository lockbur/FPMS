package com.forms.prms.tool.fileUtils.service;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.forms.prms.tool.WebHelp;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * FTP服务器文件转换为pdf
 * @author wangzf
 * @date 2014-08-06
 */
public class FtpFileConvertPDFUtil {
	protected final Logger logger = Logger.getLogger(FtpFileConvertPDFUtil.class);
	
	private boolean fileIsExists = false;
	
	/**
	 * 原文件在ftp服务器存放的路径
	 */
	private String docFilePath;
	
	/**
	 * pdf文件
	 */
	private File pdfFile;
	
	/**
	 * 原文件的文件类型
	 */
	private String docFileType;
	
	/**
	 * 原文件的文件输入流
	 */
	private InputStream is;
	
	/**
	 * 文件的编码格式
	 */
	private static String encoding = System.getProperty("file.encoding");
	
	/**
     * 文件类型集合
     */
    private enum FILE_TYPE_COLLECT{
    	JPEG,JPG,BMP,GIF,PNG,TXT,PDF,XLSX,XLS,DOC,DOCX,PPT,PPTX
    }
    
    public FtpFileConvertPDFUtil(String docFilePath,File pdfFile,String docFileType){
    	this.docFilePath = docFilePath;
    	this.pdfFile = pdfFile;
    	this.docFileType = docFileType;
    }
    
    /**
	 * 将文件转换为pdf
	 */
    public void convert(){
    	boolean rst = this.readFile(docFilePath);
    	if(rst){
    		try {
            	FILE_TYPE_COLLECT fileType = Enum.valueOf(FILE_TYPE_COLLECT.class, docFileType);
            	switch(fileType){
            		case JPEG : imgConvertPDF();
            					break;
            		case JPG : imgConvertPDF();
    							break;
            		case BMP : imgConvertPDF();
    							break;
            		case GIF : imgConvertPDF();
            					break;
            		case TXT : textConvertPDF();
            					break;
            		case PNG : imgConvertPDF();
            					break;
            		case PDF : break;
            		default : fileConvertPDF();
            					break;
            	}
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("*********将文件转换为swf文件转换失败***************");
            }
    	}
    }
    
    /*
     * Office相关类型文件转为PDF
     * @param file
     */
    private void fileConvertPDF() throws Exception{
        if(fileIsExists){
            if(!pdfFile.exists()){
            	//转换工具的端口默认为8100，可改为读取配置文件
                OpenOfficeConnection connection=new SocketOpenOfficeConnection(8100);
                try{
                    connection.connect();
                    DocumentConverter converter=new OpenOfficeDocumentConverter(connection);
                    OutputStream os = new FileOutputStream(pdfFile);
                    DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
                    //根据原文件的格式类型指定消息头
                    DocumentFormat stw = formatReg.getFormatByFileExtension(docFileType.toLowerCase()); 
                    //指定转换后文件的消息头
                    DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");
                    converter.convert(is, stw, os, pdf);
                    connection.disconnect();
                }catch(ConnectException e){
                    e.printStackTrace();
                    throw e;
                }catch(OpenOfficeException e){
                    e.printStackTrace();
                    throw e;
                }catch(Exception e){
                    e.printStackTrace();
                    throw e;
                }
            }else{
            	logger.debug("**********pdf已存在无需转换***************");
            }
        }else{
        	logger.error("***********待转换的文件不存在************");
        }
    }
    
    /**
     * text文件转换为pdf
     * @throws Exception
     */
    private void textConvertPDF() throws Exception{
    	if(fileIsExists){
    		if(!pdfFile.exists()){
		    	Document document = new Document(PageSize.A4);
				//读取文本内容
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"gbk"));
				PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
				BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
						"UniGB-UCS2-H", false);
				Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK); 
				document.open();
				String line=reader.readLine();
				while(line!=null){
				  Paragraph pg = new Paragraph(line,fontChinese);
				  document.add(pg);
				  line=reader.readLine();
				}
				document.close();
				reader.close();
				is.close();
    		}else{
    			logger.debug("**********pdf已存在无需转换***************");
    		}
    	}else{
    		logger.error("***********待转换的文件不存在************");
    	}
    }
    
    /**
     * gif、bmp、jpeg格式的图片转换为PDF
     * @throws Exception
     */
    private void imgConvertPDF() throws Exception{
    	if(fileIsExists){
    		if(!pdfFile.exists()){
		    	Document document = new Document(PageSize.A4);
				PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int readLength = -1;
				int bufferSize = 1024;
				byte[] bytes = new byte[bufferSize];
				while((readLength = is.read(bytes, 0, bufferSize))!=-1){
					bos.write(bytes, 0, readLength);
				}
				byte[] strBytes = bos.toByteArray();
				is.close();
				bos.flush();
				bos.close();
				Image img = Image.getInstance(strBytes);
				float pdfHeight = document.getPageSize().getHeight();
				float pdfWidth = document.getPageSize().getWidth()-70;
				float imgHeight = img.getPlainHeight();
				float imgWidth = img.getPlainWidth();
				if(imgHeight>pdfHeight||imgWidth>pdfWidth){
					if(pdfHeight/imgHeight>pdfWidth/imgWidth){
						img.scalePercent(pdfWidth/imgWidth*100);
					}else{
						img.scalePercent(pdfHeight/imgHeight*100);
					}
				}
				document.open();
				document.add(img);
				document.close();
    		}else{
    			logger.error("**********pdf已存在无需转换***************");
    		}
    	}else{
    		logger.error("***********待转换的文件不存在************");
    	}
    }
    
    /**
     * 将ftp服务器的文件读入到内存中
     * @param docFilePath
     */
    private boolean readFile(String docFilePath){
		String ftpFileName = ""; //FTP服务器保存的文件名
		try{
			int reply;
			Map pmap = WebHelp.getParaValueList();
			FTPClient newFtpClient = new FTPClient();
			//创建ftp连接
			newFtpClient.connect((String)pmap.get("SYS_ATTACH_FTP_IP"),Integer.parseInt((String)pmap.get("SYS_ATTACH_FTP_PORT")));
			// 登录
			newFtpClient.login((String)pmap.get("SYS_ATTACH_UPLOAD_USER"), (String)pmap.get("SYS_ATTACH_UPLOAD_PWD"));
			// 设置文件传输类型为二进制
			newFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			newFtpClient.enterLocalPassiveMode();
			newFtpClient.setControlEncoding(encoding);
			// 获取ftp登录应答代码
			reply = newFtpClient.getReplyCode();
			// 验证是否登陆成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				newFtpClient.disconnect();
				logger.error("连接ftp服务器失败");
				return fileIsExists;
			}
			// 转移到FTP服务器目录至指定的目录下
			docFilePath = new String(docFilePath.getBytes(encoding),"iso-8859-1");
			docFilePath = docFilePath.replace("\\", "/");
			if(docFilePath.contains(".")){
				ftpFileName = docFilePath.substring(docFilePath.lastIndexOf("/")+1);
				docFilePath = docFilePath.substring(0,docFilePath.lastIndexOf("/"));
			}
			boolean isSuccess = newFtpClient.changeWorkingDirectory(docFilePath);
			if(isSuccess){
				// 获取文件列表
				FTPFile[] fs = newFtpClient.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().equals(ftpFileName)) {
						fileIsExists = true;
						// 读取文件
						is = newFtpClient.retrieveFileStream(ff.getName());
						break;
					}
				}
				newFtpClient.logout();
				try {
					if (newFtpClient.isConnected()) {
						newFtpClient.disconnect();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				logger.error("读取文件路径不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("读取文件失败");
		}
		return fileIsExists;
    }
}
