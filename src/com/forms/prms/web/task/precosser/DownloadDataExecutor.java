package com.forms.prms.web.task.precosser;

import java.io.File;

import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.task.ftp.FtpDownLoadFile;
import com.forms.prms.web.task.model.LoadTask;
import com.forms.prms.web.task.util.FileUtil;
import com.forms.prms.web.util.GzipUtil;

public class DownloadDataExecutor
{

  private String workDir;

  private LoadTask task;

  private String fileName;

  private String batchDate;

  private String folder;
  

  private final String endFileName = "sendalready.temp";

  public DownloadDataExecutor(LoadTask task, String batchDate)
  {
    this.task = task;
    this.batchDate = batchDate;
//    workDir = (String) SystemParamManage.getInstance().getParamsMap().get(
//      "BATCH_DOWNLOAD_PATH");
    //从fms下载文件保存位置"c:/forms/bsznt/download"
    workDir = WebHelp.getSysPara("BATCH_DOWNLOAD_PATH");;
    workDir = FileUtil.formatPath(workDir, true);

    folder = FileUtil.formatPath(task.getFtpFileName(), true).substring(0,
      task.getFtpFileName().lastIndexOf("/"));
    folder = folder.replace("{dataDate}", batchDate.replace("-", ""));
  }

  /**
   * 从FTP上下载数据
   * 
   * @param task
   * @throws Exception
   */
  public void loadData() throws Exception
  {
    fileName = task.getFtpFileName();
    fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
      fileName.length());
    boolean isDownLoad = true;//是否需要下载
    fileName = fileName.replace("{dataDate}", batchDate.replace("-", ""));
    String cmdPath = task.getLoadCmd();
//    workDir = workDir + getCmdDir(cmdPath) + "/" + batchDate;
    workDir = workDir + cmdPath + "/" + batchDate;
    task.setWorkDir(workDir);
    //不下载数据也需要创建数据目录
    if(!isDownLoad)
    {
      File fileFold = new File(workDir);
      if (!fileFold.exists()) {
        fileFold.mkdirs();
      }
    }
    try
    {
      boolean flag = false;
      if(isDownLoad)
      {
        flag = FtpDownLoadFile.getFile(task.getHostAddr(),
          task.getPort(), task.getUesrName(), task.getPassword(), folder,
          workDir, fileName, false);
      }
      if (task.isRequired() && !flag)
      {
        throw new Exception("ERROR-201:数据文件\"" + fileName + "\"下载失败");
      }
    } catch (Exception e)
    {
      throw new Exception(e);
    }
  }

  /**
   * 从cmd路径中取出目录名
   * 
   * @param cmdPath
   * @return
   */
  private String getCmdDir(String cmdPath)
  {
    cmdPath = cmdPath.substring(cmdPath.lastIndexOf("/") + 1,
      cmdPath.lastIndexOf("."));
    return cmdPath;
  }

  /**
   * 执行任务数据下载
   * 
   * @param task
   * @param isAutoRun
   *          是否自动批量运行
   * @throws Exception
   */
  public void execute() throws Exception
  {
    loadData();
    if (task.isUnZip() && new File(workDir + "/" + fileName).exists())
    {
      unZip();
    }
    System.out.println("1.1-----从FTP上下载数据文件\"" + fileName + "\"");
  }

  /**
   * 数据解压
   * 
   * @throws Exception
   */
  public void unZip() throws Exception
  {
    String zip = workDir + "/" + fileName;
    try
    {
      GzipUtil.uncompressGZIP(zip, workDir, task.getFileName());
    } catch (Exception e)
    {
      throw new Exception("文件解压失败", e);
    }
  }

  /**
   * 检查FTP服务器上尾文件是否存在
   * 
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unused")
  private boolean checkEndFile() throws Exception
  {
    boolean flag = FtpDownLoadFile.getFile(task.getHostAddr(), task.getPort(),
      task.getUesrName(), task.getPassword(), folder, workDir, endFileName,
      false);
    return flag;
  }

}
