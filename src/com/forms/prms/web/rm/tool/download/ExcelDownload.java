package com.forms.prms.web.rm.tool.download;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.forms.prms.web.rm.tool.download.bean.TableInfoBean;
import com.forms.prms.web.rm.tool.download.service.DownLoadService;

public class ExcelDownload 
{
	public void exportExcel(String downLoadId, String reportNm, HttpServletResponse response, List<?> list) throws Exception
	{
		this.exportExcel(reportNm, new String[]{downLoadId}, new String[]{reportNm}, response, new List<?>[]{list});
	}
	
	public void exportExcel(String reportNm, String[] downLoadIds, String[] sheetNms, HttpServletResponse response, List<?>[] dataLists) throws Exception
	{
		this.exportExcel(reportNm, downLoadIds, sheetNms, response, dataLists, null);
	}
	
	public void exportExcel(String reportNm, String[] downLoadIds, String[] sheetNms, HttpServletResponse response, List<?>[] dataLists, Object[] params) throws Exception
	{
		OutputStream os = null;
		
		response.setContentType("application/octet-stream;charset=GBK");
		response.addHeader("Content-Disposition", "attachment;filename=" + new String((reportNm+".xls").getBytes("GBK"), "iso-8859-1"));
		
		
		try
		{
			if(downLoadIds.length != sheetNms.length || downLoadIds.length != dataLists.length)
				throw new Exception("导出所需的表单数量与传入的数组长度不符。");
				
			os = response.getOutputStream();
			HSSFWorkbook wb = getWorkbook(downLoadIds, sheetNms, dataLists, params,null); // 生成xls文件
			
			if (wb != null) //向客户端写xls文件
			{
				wb.write(os);
			}
			os.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			os.close();
		}
	}
	
	public HSSFWorkbook getWorkbook(String[] downLoadIds, String[] sheetNms, List<?>[] dataLists, Object[] params,List<TableInfoBean> tabInfoList) throws Exception
	{
		DownLoadService downLoadService = new DownLoadService();
		return downLoadService.doExport(downLoadIds, sheetNms, 0, dataLists, params,tabInfoList);
		
	}
}
