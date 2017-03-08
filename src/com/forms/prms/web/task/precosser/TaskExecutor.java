package com.forms.prms.web.task.precosser;

import java.util.List;

import com.forms.prms.tool.DateUtil;
import com.forms.prms.web.task.model.LoadTask;

/**
 * Copy Right Information : Forms<br>
 * Project : ZJDELT <br>
 * JDK version used : jdk1.6.0 <br>
 * Description : 后台批量运行线程<br>
 * class Name : TaskExecutor <br>
 * 
 * @author hgc<br>
 *         date: 2012-9-7 <br>
 *         Version : 1.00 <br>
 */
public class TaskExecutor
  extends Thread
{

  private String batchDate;

  @Override
  public void run()
  {
     //从FMS中下载数据
     try {
//    	 this.downloadDataFromFms(tasks);
    	 //导入数据
//		if (null != tasks && tasks.size()>0) {
//			for(int i=0;i<tasks.size();i++){
//				String workDir = tasks.get(i).getWorkDir();
//				String fileName = tasks.get(i).getFileName();
//				String type = tasks.get(i).getTaskId();
//				TaskManageService.getInstance().insertFmsData(type,DateUtil.getDateStr(),workDir,fileName);
//			}
//		}
    	 for(int i=1;i<4;i++){
    		 String type = i+"" ;
//    		 TaskManageService.getInstance().insertFmsData(type);
    	 }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  /**
   * 下载数据
   * 
   * @param tasks
   * @throws Exception
   */
  private void downloadDataFromFms(List<LoadTask> tasks) throws Exception
  {
    DownloadDataExecutor downloadDataExecutor = null;
    this.batchDate  = DateUtil.getDateStr();
    for (LoadTask task : tasks)
    {
      downloadDataExecutor = new DownloadDataExecutor(task, batchDate);
      downloadDataExecutor.execute();
    }
  }

  /**
   * 导数
   * 
   * @param tasks
   * @throws Exception
   */
  public void importData(List<LoadTask> tasks) throws Exception
  {
    
  }
  
  public void execBatchShell(String batchDate) throws Exception{
	 
  }
  /**
   * 和数据库同步数据
   * @param id  批量ID 对应 数据库中的任务类型
   * @param date 批量时间
   		
   */
  public void insertData(String id,String date){
	  
  }
  

}
