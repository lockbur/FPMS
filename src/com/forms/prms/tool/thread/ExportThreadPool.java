package com.forms.prms.tool.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.forms.prms.tool.WebHelp;

public class ExportThreadPool {
	private static ExportThreadPool pool = null;
	private ExecutorService executorService;
	
	private ExportThreadPool(){
		int size = Integer.parseInt(WebHelp.getSysPara("EXCEL_EXPORT_MAXSIZE"));
		if(size < 1){
			size = 10 * Runtime.getRuntime().availableProcessors();
		}
		executorService = Executors.newFixedThreadPool(size);
	}
	
	/**
	 * 采用单例获取线程池信息
	 * @return 线程池对象
	 */
	public static ExportThreadPool getInstance(){
		if(pool != null) {
			return pool;
		} else {
			synchronized (ExportThreadPool.class) {
				if(pool != null) {
					return pool;
				} else {
					return new ExportThreadPool();
				}
			}
		}
	}
	
	/**
	 * 处理线程
	 * @param 执行线程
	 * 
	 */
	public void put(Runnable thread){
		executorService.execute(thread);
	}
	
	/**
	 * 关闭线程池
	 */
	public void stop() {
		try
	    {
			executorService.shutdown();
			executorService.shutdownNow();
	    } catch (Exception e)
	    {
	    	executorService.shutdownNow();
	    } finally
	    {
	    	executorService = null;
	    }
	}
}
