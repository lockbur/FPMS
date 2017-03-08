package com.forms.prms.tool.fms;

import com.forms.platform.core.logger.CommonLogger;

public class FmsScanLock
{
	private static FmsScanLock instance = null;

	  private static boolean isLocked = false;

	  private FmsScanLock()
	  {
	  }

	  public static FmsScanLock newInstance()
	  {
	    if (instance == null)
	    {
	      synchronized (FmsScanLock.class)
	      {
	        if (instance == null)
	        {
	          instance = new FmsScanLock();
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
	      CommonLogger.error("阻塞扫描线程失败：", e);
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
	    	CommonLogger.error("唤醒扫描线程失败：", e);
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
