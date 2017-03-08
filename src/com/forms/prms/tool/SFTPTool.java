package com.forms.prms.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SFTPTool
{
	 /** 
     * Sftp客户端对象 
     */ 
    private ChannelSftp sftp = null; 

    /** 
     * SFTP IP地址 
     */ 
    private String ip; 

    /** 
     * SFTP 端口 
     */ 
    private String port; 

    /** 
     * SFTP 用户名 
     */ 
    private String userName; 

    /** 
     * SFTP 密码 
     */ 
    private String password; 

    /** 
     * SFTP上传模式:BINARY 
     */ 
    // private static final int BINARY_FILE_TYPE = 2; 
    
    public ChannelSftp getSftp() 
    { 
        return sftp; 
    } 

    public void setSftp(ChannelSftp sftp) 
    { 
        this.sftp = sftp; 
    } 

    public String getIp() 
    { 
        return ip; 
    } 

    public void setIp(String ip) 
    { 
        this.ip = ip; 
    } 

    public String getPort() 
    { 
        return port; 
    } 

    public void setPort(String port) 
    { 
        this.port = port; 
    } 

    public String getUserName() 
    { 
        return userName; 
    } 

    public void setUserName(String userName) 
    { 
        this.userName = userName; 
    } 

    public String getPassword() 
    { 
        return password; 
    } 

    public void setPassword(String password) 
    { 
        this.password = password; 
    } 

    /** 
     * 
     * 获取实例 
     * 
     * @return SFTPTool newinstance实例 
     * 
     */ 
    public static SFTPTool getNewInstance() 
    { 
        return new SFTPTool(); 
    } 

    /** 
     * 初始化连接参数 
     * 
     * @param sftpIP 
     *            IP 
     * @param sftpPort 
     *            端口 
     * @param sftpUsername 
     *            用户名 
     * @param sftpPassword 
     *            密码 
     */ 
    public void init(String sftpIP, String sftpPort, String sftpUsername, String sftpPassword) 
    { 
        // 获取SFTP连接信息 
        this.ip = sftpIP; 
        this.port = sftpPort; 
        this.userName = sftpUsername; 
        this.password = sftpPassword; 
    } 

    /** 
     * 从SFTP将符合约定命名的文件都下载到本地 . 
     * 
     * @param sftpDir 
     *            SFTP服务器文件存放路径 
     * @param locDir 
     *            本地文件存放路径 
     * @param regex 
     *            指定文件名的格式 
     * @param needBackup 
     *            是否需要文件备份(true:是;false:否) 
     * @param deleteFtpFile 
     *            the delete ftp file 
     * @return 下载到本地的文件列表 
     */ 
    public List<File> synSFTPFileToLocal(String sftpDir, String locDir, String regex, 
             boolean deleteFtpFile) 
    { 
        List<File> files = new ArrayList<File>(); 
        try 
        { 
            this.connect(ip, Integer.parseInt(this.port), userName, password); 

            // 获得FTP上文件名称列表 
            List<String> ftpFileNameList = this.listFiles(sftpDir, regex); 

            File localFile = null; 

            int size = ftpFileNameList.size(); 

            // 根据每个FTP文件名称创建本地文件。 
            for (int i = 0; i < size; i++) 
            { 
                // 下载源文件 
                localFile = this.downloadFile(sftpDir, locDir, ftpFileNameList.get(i), deleteFtpFile); 
                if (localFile.exists()) 
                { 
                    files.add(localFile); 
                } 
            } 
        } 
        catch (Exception e) 
        { 
        	CommonLogger.error("synSFTPFileToLocal Exception" + e.getMessage());
        } 
        finally 
        { 
            try 
            {
				this.disconnect();
			} 
            catch (JSchException e) 
            {
				CommonLogger.error("disconnect Exception" + e.getMessage());
				e.printStackTrace();
			} 
        } 
        return files; 
    } 

    /** 
     * 连接sftp服务器 
     * 
     * @param sftpip 
     *            ip地址 
     * @param sftpport 
     *            端口 
     * @param sftpusername 
     *            用户名 
     * @param sftppassword 
     *            密码 
     * @return channelSftp 
     * @throws SPMException 
     */ 
    public ChannelSftp connect(String sftpip, int sftpport, String sftpusername, String sftppassword) 
    { 
        sftp = new ChannelSftp(); 
        try 
        { 
            JSch jsch = new JSch(); 
            Session sshSession = jsch.getSession(sftpusername, sftpip, sftpport); 
            CommonLogger.info("Session created");
            sshSession.setPassword(sftppassword); 
            Properties sshConfig = new Properties(); 
            sshConfig.put("StrictHostKeyChecking", "no"); 
            sshSession.setConfig(sshConfig); 
            // 设置超时时间为 
            sshSession.setTimeout(30*1000); 
            sshSession.connect(); 
            Channel channel = sshSession.openChannel("sftp"); 
            channel.connect(); 
            sftp = (ChannelSftp) channel; 

            // 设置文件类型 
            // ftpClient.setFileType(BINARY_FILE_TYPE); 

            // 与防火墙相关 
            // ftpClient.enterLocalPassiveMode(); 
        } 
        catch (JSchException e) 
        { 
        	CommonLogger.error("JSchException :"+e.getMessage());
        } 
        return sftp; 
    } 


    /** 
     * 创建指定文件夹 
     * 
     * @param dirName 
     *            dirName 
     */ 
    public void mkDir(String dirName) 
    { 
        String[] dirs = dirName.split("/"); 
        try 
        { 
            String now = sftp.pwd(); 
            sftp.cd("/");
            for (int i = 1; i < dirs.length; i++) 
            { 
                boolean dirExists = openDir(dirs[i]); 
                if (!dirExists) 
                { 
                    sftp.mkdir(dirs[i]); 
                    sftp.cd(dirs[i]); 
                } 
            } 
            sftp.cd(now); 
        } 
        catch (SftpException e) 
        { 
        	CommonLogger.error("mkDir Exception  :"+e.getMessage());
        } 
    } 

    /** 
     * 打开指定目录 
     * 
     * @param directory 
     *            directory 
     * @return 是否打开目录 
     */ 
    public boolean openDir(String directory) 
    { 
        try 
        { 
            sftp.cd(directory); 
            return true; 
        } 
        catch (SftpException e) 
        { 
        	CommonLogger.error("openDir Exception : "+e.getMessage());
            return false; 
        } 
    } 


    /** 
     * 上传文件 
     * 
     * @param directory 
     *            上传的目录 
     * @param file 
     *            要上传的文件 parentDir 上传目录的上级目录 
     * @return 是否上传 
     */ 
    public boolean uploadFile(String directory, File file) 
    { 
        boolean flag = false; 
        FileInputStream in = null; 
        try 
        { 
            String now = sftp.pwd(); 
            sftp.cd(directory); 
            in = new FileInputStream(file); 
            sftp.put(in, file.getName()); 
            sftp.cd(now); 
            if (file.exists()) 
            { 
                flag = true; 
            } 
            else 
            { 
                flag = false; 
            } 
        } 
        catch (Exception e) 
        { 
        	CommonLogger.error("uploadFile Exception : "+e.getMessage());
        } 
        finally 
        { 
            try 
            { 
                if (null != in) 
                { 
                    try 
                    { 
                        in.close(); 
                    } 
                    catch (IOException e) 
                    { 
                    	CommonLogger.error("IOException : "+e.getMessage());
                    } 
                } 
            } 
            catch (Exception e) 
            { 
            	CommonLogger.error("Exception : "+e.getMessage());
            } 
        } 
        return flag; 
    } 
    
    
    public boolean uploadFile(String sftpip, int sftpport, String sftpusername, String sftppassword,
    		String directory, File file) 
    { 
        boolean flag = false; 
        FileInputStream in = null; 
        try 
        { 
        	this.connect(sftpip, sftpport, sftpusername, sftppassword);
            String now = sftp.pwd(); 
            mkDir(directory);
            sftp.cd(directory); 
            in = new FileInputStream(file); 
            sftp.put(in, file.getName()); 
            sftp.cd(now); 
            if (file.exists()) 
            { 
                flag = true; 
            } 
            else 
            { 
                flag = false; 
            } 
        } 
        catch (Exception e) 
        { 
        	CommonLogger.error("uploadFile Exception : "+e.getMessage());
        } 
        finally 
        { 
            try 
            { 
                if (null != in) 
                { 
                    try 
                    { 
                        in.close(); 
                    } 
                    catch (IOException e) 
                    { 
                    	CommonLogger.error("IOException : "+e.getMessage());
                    } 
                } 
                this.disconnect();
            } 
            catch (Exception e) 
            { 
            	CommonLogger.error("Exception : "+e.getMessage());
            } 
        } 
        return flag; 
    }

    /** 
     * 下载文件. 
     * 
     * @param ftpDir 
     *            存放下载文件的SFTP路径 
     * @param locDir 
     *            下载的文件 SFTP上的文件名称 
     * @param ftpFileName 
     *            FTP上的文件名称 
     * @param deleteFtpFile 
     *            the delete ftp file 
     * @return 本地文件对象 
     * @throws FileNotFoundException 
     *             FileNotFoundException 
     */ 
    public File downloadFile(String ftpDir, String locDir, String ftpFileName, boolean deleteFtpFile) 
            throws FileNotFoundException 
    { 
        File file = null; 
        FileOutputStream output = null; 
        String localDir = CommonFileUtils.createFileDir(locDir).append("/") 
                .append(ftpFileName).toString();
        try 
        { 
            String now = sftp.pwd(); 
            sftp.cd(ftpDir); 
            file = new File(localDir); 
            output = new FileOutputStream(file); 
            sftp.get(ftpFileName, output); 
            sftp.cd(now); 
            if (deleteFtpFile) 
            { 
                sftp.rm(ftpFileName); 
            } 
        } 
        catch (SftpException e) 
        { 
        	CommonLogger.error("Failed to download "+e.getMessage());
        } 
        finally 
        { 
            if (null != output) 
            { 
                try 
                { 
                    output.close(); 
                } 
                catch (IOException e) 
                { 
                	CommonLogger.error("create localFile failed:"+e.getMessage());
                } 
            } 
        } 
        return file; 
    } 
    
    public File downloadFile(String sftpip, int sftpport, String sftpusername, String sftppassword,
    		String ftpDir, String locDir, String ftpFileName) 
	{ 
		File file = null; 
		FileOutputStream output = null; 
		String localDir = CommonFileUtils.createFileDir(locDir).append("/") 
		        .append(ftpFileName).toString();
		try 
		{ 
			this.connect(sftpip, sftpport, sftpusername, sftppassword);
		    String now = sftp.pwd(); 
		    sftp.cd(ftpDir); 
		    file = new File(localDir); 
		    output = new FileOutputStream(file); 
		    sftp.get(ftpFileName, output); 
		    sftp.cd(now);
		} 
		catch (Exception e) 
		{ 
			CommonLogger.error("Failed to download "+e.getMessage());
		} 
		finally 
		{ 
		    if (null != output) 
		    { 
		        try 
		        { 
		            output.close(); 
		        } 
		        catch (IOException e) 
		        { 
		        	CommonLogger.error("close outputstream failed:"+e.getMessage());
		        } 
		    } 
		    try
		    {
				this.disconnect();
			} 
		    catch (JSchException e) 
		    {
		    	CommonLogger.error("disconnect failed:"+e.getMessage());
				e.printStackTrace();
			}
		} 
		return file; 
	}    
    

    /** 
     * Description:断开FTP连接 <br> 
     * @throws JSchException 
     */ 
    public void disconnect() throws JSchException 
    { 
        if (null != sftp) 
        { 
            sftp.disconnect(); 

            if (null != sftp.getSession()) 
            { 
                sftp.getSession().disconnect(); 
            } 
        } 
    } 

    /** 
     * 删除文件 
     * 
     * @param directory 
     *            要删除文件所在目录 
     * @param deleteFile 
     *            要删除的文件 
     * @param sftp 
     */ 
    public void delete(String directory, String deleteFile) 
    { 
        try 
        { 
            String now = sftp.pwd(); 
            sftp.cd(directory); 
            sftp.rm(deleteFile); 
            sftp.cd(now); 
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
    } 

    /** 
     * 列出目录下的文件 
     * 
     * @param directory 
     *            要列出的目录 
     * @param regex 
     *            指定文件名的格式 
     * @return 文件列表 
     * @throws SftpException 
     *             SftpException 
     * @throws SPMException 
     *             SPMException 
     */ 
    @SuppressWarnings("unchecked") 
    public List<String> listFiles(String directory, String regex) 
            throws SftpException, Exception 
    { 
        List<String> ftpFileNameList = new ArrayList<String>(); 
        Vector<LsEntry> sftpFile = sftp.ls(directory); 
        LsEntry isEntity = null; 
        String fileName = null; 
        Iterator<LsEntry> sftpFileNames = sftpFile.iterator(); 
        while (sftpFileNames.hasNext()) 
        { 
            isEntity = (LsEntry) sftpFileNames.next(); 
            fileName = isEntity.getFilename(); 

            if (Tool.CHECK.isBlank(regex)) 
            { 
                ftpFileNameList.add(fileName); 
            } 
            else 
            { 
                if (fileName.matches(regex)) 
                { 
                    ftpFileNameList.add(fileName); 
                } 
            } 
        } 

        return ftpFileNameList; 
    } 
    
    public static void main(String[] args) throws SftpException, Exception 
    {
    	SFTPTool sftpTool = SFTPTool.getNewInstance();
//    	sftpTool.connect("22.188.44.246", 22, "fpms", "fpms1234");
//    	List<String> fileList = sftpTool.listFiles("/fpms", "");
//    	if(null != fileList && fileList.size() > 0)
//    	{
//    		for(String str : fileList)
//    		{
//    			System.out.println(str);
//    		}
//    	}
//    	sftpTool.downloadFile("/fpms", "C:/aaa", ".bash_profile", false);
//    	sftpTool.uploadFile("/fpms", new File("C:\\aaa\\abcd.txt"));
//    	sftpTool.disconnect();
    	
//    	sftpTool.uploadFile("22.188.44.248", 22, "fpms", "fpms1234", 
//    			"/fpms/sda/sad", new File("C:\\aaa\\abcd.txt"));
    	
//    	sftpTool.downloadFile("22.188.44.246", 22, "fpms", "fpms1234",
//    			"/fpms", "C:/aaa", "0219.log");
    	
    	System.out.println("C:/AAA/BBB/CCC.TXT".substring("C:/AAA/BBB/CCC.TXT".lastIndexOf("/")+1));
	}
}
