package com.forms.prms.tool.fms;

import com.forms.platform.core.logger.CommonLogger;

public class FmsUploadLock
{
	private static FmsUploadLock instance = null;

	  private static boolean isLocked = false;

	  private FmsUploadLock()
	  {
	  }

	  public static FmsUploadLock newInstance()
	  {
	    if (instance == null)
	    {
	      synchronized (FmsUploadLock.class)
	      {
	        if (instance == null)
	        {
	          instance = new FmsUploadLock();
	        }
	      }
	    }
	    return instance;
	  }

	  public void executeWait()
	  {
	    try
	    {
	      synchronized (this)
	      {
	        wait();
	      }
	    } catch (Exception e)
	    {
	      CommonLogger.error("阻塞上传线程失败：", e);
	    }
	  }

	  public void executeWake()
	  {
	    try
	    {
	      synchronized (this)
	      {
	        notifyAll();
	      }
	    } catch (Exception e)
	    {
	    	CommonLogger.error("唤醒上传线程失败：", e);
	    }
	  }

	  public synchronized void lock() throws Exception
	  {
	    if (isLocked)
	    {
	      throw new Exception(" [ 正在处理中，请稍后再操作！ ] ");
	    }
	    isLocked = true;
	  }

	  public synchronized void unlock()
	  {
	    isLocked = false;
	  }
}
