package com.forms.prms.web.rm.tool.download.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.forms.platform.web.WebUtils;
import com.forms.prms.web.rm.tool.download.bean.ServletBean;
import com.forms.prms.web.rm.tool.download.bean.ServletClassBean;
import com.forms.prms.web.rm.tool.download.bean.TableInfoBean;
import com.forms.prms.web.rm.tool.download.init.DownLoadInit;
import com.forms.prms.web.rm.tool.download.model.BigDownloadBean;
import com.forms.prms.web.rm.tool.download.model.CellSpanInfoBean;
import com.forms.prms.web.rm.tool.download.model.DataColsBean;
import com.forms.prms.web.rm.tool.download.model.DownLoadBean;
import com.forms.prms.web.rm.tool.download.model.ExcelBean;
import com.forms.prms.web.rm.tool.download.model.HeaderBean;
import com.forms.prms.web.rm.tool.download.model.HeaderColsBean;
import com.forms.prms.web.rm.tool.download.model.HeaderRowBean;
import com.forms.prms.web.rm.tool.download.model.LayerBean;
import com.forms.prms.web.rm.tool.download.model.RowColNumBean;
import com.forms.prms.web.rm.tool.download.model.RowColNumBean2;

/**
 * Excel下载主要服务程序
 * @see 
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31 
 */
public class DownLoadService
{

	/*
	 *  用于下载用map封装的,不确定字段的数据集合 
	 */
	public HSSFWorkbook doExport(String[] ids, String[] sheetNms, int methodIndex, List<?>[] dataLists, Object[] params,List<TableInfoBean> tInfoList) throws Exception
	{
		HSSFWorkbook wb = null;
		try
		{
			/**
			 * 创建EXCEL并设置格式
			 */
			wb = new HSSFWorkbook();
			setStyle(wb);
			for(int sheetNum=0; sheetNum<ids.length; sheetNum++) {
				String id = ids[sheetNum];
				String sheetNm = sheetNms[sheetNum];
				List<?> list = dataLists[sheetNum];
				
				int index = sheetNm.indexOf("（");
				String tmp = sheetNm;
				try{
					tmp = sheetNm.substring(0, index);
				}catch(Exception e){	
					tmp = sheetNm;
				}
				
				if(null != tmp && tmp.length() >= 30)
				{
					tmp = tmp.substring(0, 30);
				}
				
				/**
				 * 创建标识当前行列的javaBean
				 */
				RowColNumBean rowColNumBean = null;
				HSSFSheet sheet = null;
				DownLoadBean downLoadBean = null;
				BigDownloadBean bean = null;
				String realPath = WebUtils.getRealPath();
				if(tInfoList!=null)
				{
					DownLoadBean downLoadBeanTemp = (DownLoadBean) DownLoadInit.getInstance(realPath).getDownLoadMap().get(id);
					downLoadBean=getDownLoadBean(downLoadBeanTemp,tInfoList);
				}
				else
				{
					downLoadBean = (DownLoadBean) DownLoadInit.getInstance(realPath).getDownLoadMap().get(id);
				}
				/**
	             * 创建表单
	             */
	            int rowNum=list.size();
	            int constant = 60000;
	            int samllNum=rowNum/constant;
	            for(int i=0;i<=samllNum;i++)
	            {
	                int j=i+1;
	                List tmpList=new ArrayList();
	                if(i==samllNum)
	                    tmpList= list.subList(i*constant,rowNum);
	                else
	                    tmpList= list.subList(i*constant,j*constant);
	                
	                
	                if(rowNum>=constant)
	                {
	                    sheet = wb.createSheet(tmp+"("+j+")");  
	                }
	                else
	                {
	                    sheet = wb.createSheet(tmp);  
	                }
	
	                //wb.setSheetName(0, tmp, HSSFWorkbook.ENCODING_UTF_16);
	                rowColNumBean = new RowColNumBean();
	                
	                setHead(wb,sheet,downLoadBean,rowColNumBean,id, params);
	                
	                //生成报表数据            
	                setExcelData(sheetNm, wb, sheet, downLoadBean, methodIndex, rowColNumBean, bean, tmpList);
	            }
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return wb;
	}
	public DownLoadBean getDownLoadBean(DownLoadBean bean,List<TableInfoBean> tabInfoList)
	{
		DownLoadBean dlbean=bean;
		ExcelBean exbean=new ExcelBean();
		
		DataColsBean dataColsBean= new DataColsBean();
		String[] colNames= new String[tabInfoList.size()];
		boolean[] canSpan= new boolean[tabInfoList.size()];
		String[] format= new String[tabInfoList.size()];
		String[] textVal= new String[tabInfoList.size()];
		for(int i=0;i<tabInfoList.size();i++)
		{
			colNames[i]=tabInfoList.get(i).getColNameDb();
			canSpan[i]=false;
			
		}
		dataColsBean.setColCanSpan(canSpan);
		dataColsBean.setColName(colNames);
		dataColsBean.setInList(false);
		dataColsBean.setFormat(format);
		dataColsBean.setColTextValue(textVal);
		dataColsBean.setLayerBean(null);
		
		exbean.setDataColsBean(dataColsBean);
		
		HeaderBean headerBean = new HeaderBean();
		List<HeaderRowBean> headlist= new ArrayList<HeaderRowBean>();
		List<HeaderColsBean> hcbList= new ArrayList<HeaderColsBean>();
		HeaderRowBean hrb = new HeaderRowBean();
		for(int i=0;i<tabInfoList.size();i++)
		{
			HeaderColsBean hcb  = new HeaderColsBean();
			hcb.setCapName(tabInfoList.get(i).getColComm());
			hcb.setColspan("1");
			hcb.setRowspan("1");
			hcbList.add(hcb);
		}
		hrb.setHeadColsList(hcbList);
		headlist.add(hrb);
		headerBean.setHeadList(headlist);
		exbean.setHeaderBean(headerBean);
		
		dlbean.setExcelBean(exbean);
		return dlbean;
	}
	
	public void setHead(HSSFWorkbook wb,HSSFSheet sheet,DownLoadBean downLoadBean,RowColNumBean rowColNumBean,String id, Object[] params)
	{
		//为标题预留三行，两行为大标题，一行为副标题
		rowColNumBean.setRowNum(3);
		rowColNumBean.setMaxRowNum(3);
		
		/**
		 * 取得下载配置信息
		 */
		
		ExcelBean excelBean = null;
		excelBean = downLoadBean.getExcelBean();

		//生成报表头
		List headList  = excelBean.getHeaderBean().getHeadList();
		RowColNumBean2 rowColNumBean2 = new RowColNumBean2();
		for(int i=0; i<rowColNumBean2.getColRowNum().length; i++)
		{
			rowColNumBean2.getColRowNum()[i] = 3;
		}
		rowColNumBean2.setRowNum(3);
		List headColsList = null;
		HeaderRowBean headerRowBean = null;
		for(int i=0; i<headList.size(); i++)
		{
			headerRowBean = (HeaderRowBean)headList.get(i);
			headColsList = headerRowBean.getHeadColsList();
			rowColNumBean2.setColNum();
			rowColNumBean2.setRowNum(3+i);
			for(int j=0; j<headColsList.size(); j++)
			{
				setHeader(wb, sheet, (HeaderColsBean)headColsList.get(j), rowColNumBean2, params);
			}	
		}
		
		rowColNumBean.setRowNum(rowColNumBean2.getColRowNum()[0]);
		rowColNumBean.setMaxRowNum(rowColNumBean2.getColRowNum()[0]);
	}

	/**
	 * 生成Excel数据
	 * @param wb
	 * @param sheet
	 * @param downLoadBean
	 * @param methodIndex
	 * @param request
	 * @param rowColNumBean
	 */
	public void setExcelData(String reportNm, HSSFWorkbook wb, HSSFSheet sheet, DownLoadBean downLoadBean, 
			int methodIndex, RowColNumBean rowColNumBean,  
			BigDownloadBean bigDownloadBean, List<?> dataList)throws Exception
	{
		try
		{
			ServletBean servletBean = downLoadBean.getServletBean();
			ServletClassBean[] classArray = servletBean.getClasses();
			ServletClassBean serviceClass = classArray[0];

			String  returnType = serviceClass.getReturnInnerEmType();
			int rowNum = rowColNumBean.getMaxRowNum();//开始行
			@SuppressWarnings("unused")
			int colNum = 0;

			DataColsBean dataColsBean = downLoadBean.getExcelBean().getDataColsBean();
			
			/** 跨行跨列处理1 start */
			boolean[] canSpan = dataColsBean.getColCanSpan();
			CellSpanInfoBean cellSpanInfoBean_curr = null;
			CellSpanInfoBean cellSpanInfoBean_next = null;
			List<CellSpanInfoBean> cellSpanInfoList = new ArrayList<CellSpanInfoBean>();
			while(cellSpanInfoList.size() < canSpan.length)
			{
				cellSpanInfoList.add(null);
			}
			/** 跨行跨列处理1 end */
			
			String[] cols = new String[dataColsBean.getColName().length];	
			for(int i=0; i<cols.length; i++)
			{
				cols[i] = ServiceTool.getElVal(WebUtils.getRequest(),dataColsBean.getColName()[i]);
			}
			
			/** List<List<Bean>>特殊处理1 start */
			boolean dataInList = dataColsBean.isInList();
			int[] colListIndex = dataColsBean.getListIndex();
			/** List<List<Bean>>特殊处理1 end */
			
			String[] colFormat = new String[dataColsBean.getFormat().length];
			for(int i=0; i<colFormat.length; i++)
			{
				colFormat[i] = ServiceTool.getElVal(WebUtils.getRequest(),dataColsBean.getFormat()[i]);
			}
			
			String format = "";
			LayerBean layerBean = dataColsBean.getLayerBean();
			int layerValue = 0;
			int reportLayer = 0;
			String layerNm = null;
			if (layerBean != null)
			{
				layerNm = layerBean.getLayerName();
				reportLayer = layerBean.getLayerNumber();
			}
			setLayerStyle(wb,reportLayer,colFormat);

			Class clzz = Class.forName(returnType);
			Method[] methods = clzz.getMethods();
			
			for (int i = 0; i < dataList.size(); i++)
			{
				HSSFRow xlsRow = sheet.createRow(rowNum++);
				colNum = 0;
				Object dataBean = dataList.get(i);
				Object bean = dataBean;
				
				if (layerBean != null)
				{
					Object layerVal = getCellVal(layerNm, null, dataBean, methods);
					layerValue = Integer.parseInt(String.valueOf(layerVal));
				}
				
				for (int j = 0,indexFlag = -1; j < cols.length; j++)
				{
					/** List<List<Bean>>特殊处理2 start */
					if(dataInList && indexFlag != colListIndex[j])
					{
						try
						{
							bean = ((List) dataBean).get(((List) dataBean).size() > colListIndex[j] ? colListIndex[j]: 0);
							indexFlag = colListIndex[j];
						}catch(Exception e)
						{
							bean = dataBean;
						}
					}
					/** List<List<Bean>>特殊处理2 end */
					
					HSSFCell cell = xlsRow.createCell(j);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					Object value = getCellVal(cols[j], dataColsBean.getColTextValue()[j], bean, methods);		
					format = getElVal(colFormat[j],bean, methods);
					
					if (value instanceof Number)
					{
						
						String tmp = "##0.00";
						if(!(null == format) && !"".equals(format))
						{
							tmp = format.replaceAll(",", "").replaceAll("%", "00");
						}
						DecimalFormat nf = new DecimalFormat(tmp);

						cell.setCellValue(Double.parseDouble(nf.format(value)));
						HSSFCellStyle cellStyle = getStyle(layerValue,"NUM",j);
						
						if(colFormat[j]!=null && colFormat[j].endsWith("@response"))
						{
							HSSFCellStyle style = wb.createCellStyle();
							
							//style.setFont(cellStyle.getFont(wb));										//字体颜色
							style.setFillForegroundColor(cellStyle.getFillForegroundColor());			//背景颜色
							
							style.setBorderBottom((short)1);							//下边框    
							style.setBorderLeft((short)1);								//左边框    
							style.setBorderRight((short)1);								//右边框    
							style.setBorderTop((short)1);								//上边框
							
							style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);		//填充方式
							style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//垂直居中
							style.setAlignment(cellStyle.getAlignment());								//水平方向
							style.setVerticalAlignment(cellStyle.getVerticalAlignment());
							style.setDataFormat(wb.createDataFormat().getFormat(ServiceTool.getDefaultFormat(format, "NUM")));
							cell.setCellStyle(style);
						}else
						{
							cell.setCellStyle(cellStyle);
						}

					} else
					{
						String strVal = escapeColumnValue(value);
						if (layerValue != 0 && strVal != null)
						{
							String temp = "";
							for (int k = 0; k < layerValue; k++)
							{
								temp += "   ";
							}
							strVal = temp + strVal;
						}
						cell.setCellValue(strVal);
//						if(strVal == null)
//						{
//							cell.setCellStyle(getStyle(layerValue,"NUM",j));
//						}else
//						{
							cell.setCellStyle(getStyle(layerValue,"STR",j));
//						}
						
					}
					
					/** 跨行跨列处理2 start */
					if(canSpan[j])
					{
						//没有跨行跨列的信息则存储当前cell信息
						cellSpanInfoBean_curr = cellSpanInfoList.get(j);
						if(cellSpanInfoBean_curr == null)
						{
							cellSpanInfoBean_curr = new CellSpanInfoBean();
							cellSpanInfoBean_curr.setCellValue(getCellVal(cell));
							cellSpanInfoBean_curr.setStartRow(rowNum-1);
							cellSpanInfoBean_curr.setStartCol((short)j);
							cellSpanInfoBean_curr.setEndRow(rowNum-1);
							cellSpanInfoBean_curr.setEndCol((short)j);
							cellSpanInfoList.set(j, cellSpanInfoBean_curr);
						}
						else
						{
							// 如果当前cell的内容等于该列的存储值则跨行+1
							if(getCellVal(cell).equals(cellSpanInfoBean_curr.getCellValue()))
							{
								cellSpanInfoBean_curr.setEndRow(cellSpanInfoBean_curr.getEndRow()+ 1);
							}
							else
							{
								for(int k = j + 1, len = canSpan.length; k < len; k++)
								{
									if(!canSpan[k])
									{
										cellSpanInfoBean_curr = null;
										continue;
									}
									if(cellSpanInfoBean_curr == null)
									{
										cellSpanInfoBean_curr = cellSpanInfoList.get(k);
										continue;
									}
									else
									{
										cellSpanInfoBean_next = cellSpanInfoList.get(k);

										//如果下列的值和开始行号和结束行号都与当前的相等则跨列+1，下列清空
										if(cellSpanInfoBean_curr.getCellValue().equals(cellSpanInfoBean_next.getCellValue())
												&& cellSpanInfoBean_curr.getStartRow() == cellSpanInfoBean_next.getStartRow()
												&& cellSpanInfoBean_curr.getEndRow() == cellSpanInfoBean_next.getEndRow())
										{
											cellSpanInfoBean_curr.setEndCol((short)(cellSpanInfoBean_curr.getEndCol()+1));
											cellSpanInfoList.set(k, null);
										}
										else
										{
											cellSpanInfoBean_curr = cellSpanInfoBean_next;
										}
									}
								}
								for(int m = j, len = canSpan.length; m < len; m++)
								{
									cellSpanInfoBean_curr = cellSpanInfoList.get(m);
									if(cellSpanInfoBean_curr != null
											&& (cellSpanInfoBean_curr.getStartCol() != cellSpanInfoBean_curr.getEndCol()
													|| cellSpanInfoBean_curr.getStartRow() != cellSpanInfoBean_curr.getEndRow()))
									{
										sheet.addMergedRegion(
												new CellRangeAddress(cellSpanInfoBean_curr.getStartRow(), 
														cellSpanInfoBean_curr.getEndRow(), 
														cellSpanInfoBean_curr.getStartCol(), 
														cellSpanInfoBean_curr.getEndCol()));
									}
									cellSpanInfoList.set(m, null);
								}
								
								//跨行跨列完成后存储该列数据
								cellSpanInfoBean_curr = new CellSpanInfoBean();
								cellSpanInfoBean_curr.setCellValue(getCellVal(cell));
								cellSpanInfoBean_curr.setStartRow(rowNum-1);
								cellSpanInfoBean_curr.setStartCol((short)j);
								cellSpanInfoBean_curr.setEndRow(rowNum-1);
								cellSpanInfoBean_curr.setEndCol((short)j);
								cellSpanInfoList.set(j, cellSpanInfoBean_curr);
							}
						}
					}
					/** 跨行跨列处理2 end */
				}
				/** 跨行跨列处理3 start */
				//最后输出完须处理一次遗留跨行跨列
				if(i + 1 == dataList.size())
				{
					cellSpanInfoBean_curr = cellSpanInfoList.get(0);
					for(int k = 1, len = canSpan.length; k < len; k++)
					{
						if(!canSpan[k])
						{
							cellSpanInfoBean_curr = null;
							continue;
						}
						if(cellSpanInfoBean_curr == null)
						{
							cellSpanInfoBean_curr = cellSpanInfoList.get(k);
							continue;
						}
						else
						{
							cellSpanInfoBean_next = cellSpanInfoList.get(k);
							//如果下列的值和开始行号和结束行号都与当前的相等则跨列+1，下列清空
							if(cellSpanInfoBean_curr.getCellValue().equals(cellSpanInfoBean_next.getCellValue())
									&& cellSpanInfoBean_curr.getStartRow() == cellSpanInfoBean_next.getStartRow()
									&& cellSpanInfoBean_curr.getEndRow() == cellSpanInfoBean_next.getEndRow())
							{
								cellSpanInfoBean_curr.setEndCol((short)(cellSpanInfoBean_curr.getEndCol()+1));
								cellSpanInfoList.set(k, null);
							}
							else
							{
								cellSpanInfoBean_curr = cellSpanInfoBean_next;
							}
						}
					}
					for(int m = 0, len = canSpan.length; m < len; m++)
					{
						cellSpanInfoBean_curr = cellSpanInfoList.get(m);
						if(cellSpanInfoBean_curr != null
								&& (cellSpanInfoBean_curr.getStartCol() != cellSpanInfoBean_curr.getEndCol()
										|| cellSpanInfoBean_curr.getStartRow() != cellSpanInfoBean_curr.getEndRow()))
						{
							sheet.addMergedRegion(
									new CellRangeAddress(cellSpanInfoBean_curr.getStartRow(), 
											cellSpanInfoBean_curr.getEndRow(), 
											cellSpanInfoBean_curr.getStartCol(), 
											cellSpanInfoBean_curr.getEndCol()));
						}
						cellSpanInfoList.set(m, null);
					}
				}
				/** 跨行跨列处理3 end */
			}
			
			short length = (short)(cols.length-1);
			setTitle(wb, sheet,length, reportNm);
			setColumnWidth(sheet,length);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 生成Excel数据
	 * @param wb
	 * @param sheet
	 * @param downLoadBean
	 * @param methodIndex
	 * @param request
	 * @param rowColNumBean
	 */
	@SuppressWarnings("unchecked")
	public short setExcelData(HSSFWorkbook wb, HSSFSheet sheet, DownLoadBean downLoadBean,RowColNumBean rowColNumBean,List dataList)throws Exception
	{
		short  length = 0;
		
		try
		{
			ServletBean servletBean = downLoadBean.getServletBean();
			ServletClassBean[] classArray = servletBean.getClasses();
			ServletClassBean serviceClass = classArray[0];

			String returnType = serviceClass.getReturnInnerEmType();
			int rowNum = rowColNumBean.getMaxRowNum();
			int colNum = 0;

			DataColsBean dataColsBean = downLoadBean.getExcelBean().getDataColsBean();
			String[] cols = new String[dataColsBean.getColName().length];	
			for(int i=0; i<cols.length; i++)
			{
				cols[i] = ServiceTool.getElVal(WebUtils.getRequest(),dataColsBean.getColName()[i]);
			}
			
			String[] colFormat = new String[dataColsBean.getFormat().length];
			for(int i=0; i<colFormat.length; i++)
			{
				colFormat[i] = ServiceTool.getElVal(WebUtils.getRequest(),dataColsBean.getFormat()[i]);
			}
		
			String format = "";
			LayerBean layerBean = dataColsBean.getLayerBean();
			int layerValue = 0;
			int reportLayer = 0;
			String layerNm = null;
			
			if (layerBean != null)
				{
					layerNm = layerBean.getLayerName();
					reportLayer = layerBean.getLayerNumber();
				}
				setLayerStyle(wb,reportLayer,colFormat);

			Class clzz = Class.forName(returnType);
			Method[] methods = clzz.getMethods();
			
			for (int i = 0; i < dataList.size(); i++)
			{
				HSSFRow xlsRow = sheet.createRow(rowNum++);
				rowColNumBean.setMaxRowNum(rowNum);
				colNum = 0;
				Object bean = dataList.get(i);
				
				if (layerBean != null)
				{
					Object layerVal = getCellVal(layerNm, null, bean, methods);
					layerValue = Integer.parseInt(String.valueOf(layerVal));
				}
				 
				
				for (int j = 0; j < cols.length; j++)
				{	
					HSSFCell cell = xlsRow.createCell(colNum++);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					Object value = getCellVal(cols[j], dataColsBean.getColTextValue()[j], bean, methods);		
					format = getElVal(colFormat[j],bean, methods);
					
					if (value instanceof Number)
					{
						
						String tmp = "##0.00";
						if(format != null)
						{
							tmp = format.replaceAll(",", "").replaceAll("%", "00");
						}
						DecimalFormat nf = new DecimalFormat(tmp);

						cell.setCellValue(Double.parseDouble(nf.format(value)));
						
						HSSFCellStyle cellStyle = getStyle(layerValue,"NUM",j);
							
							if(colFormat[j]!=null && colFormat[j].endsWith("@response"))
							{
								HSSFCellStyle style = wb.createCellStyle();
								
								//style.setFont(cellStyle.getFont(wb));										//字体颜色
								style.setFillForegroundColor(cellStyle.getFillForegroundColor());			//背景颜色
								
								style.setBorderBottom((short)1);							//下边框    
								style.setBorderLeft((short)1);								//左边框    
								style.setBorderRight((short)1);								//右边框    
								style.setBorderTop((short)1);								//上边框
								
								style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);		//填充方式
								style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//垂直居中
								style.setAlignment(cellStyle.getAlignment());								//水平方向
								style.setVerticalAlignment(cellStyle.getVerticalAlignment());
								style.setDataFormat(wb.createDataFormat().getFormat(ServiceTool.getDefaultFormat(format, "NUM")));
								cell.setCellStyle(style);
							}else
							{
								cell.setCellStyle(cellStyle);
							}
							
					} else
					{
						String strVal = escapeColumnValue(value);
						if (layerValue != 0 && strVal != null)
						{
							String temp = "";
							for (int k = 0; k < layerValue; k++)
							{
								temp += "   ";
							}
							strVal = temp + strVal;
						}
						cell.setCellValue(strVal);
						
						if(strVal == null)
						{
							cell.setCellStyle(getStyle(layerValue,"NUM",j));	
						}else
						{
							cell.setCellStyle(getStyle(layerValue,"STR",j));
						}
					}	
				}
			}
			
			length = (short)(cols.length-1);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return length;
	}
	
	public String getElVal(String src,Object bean, Method[] methods)
	{
		String ft = src;
		if(src!=null && src.endsWith("@response"))
		{
			String[] tmp1 = src.split("@");
			String value = (String)getCellVal(tmp1[0], null, bean, methods);
			for(int i=1; i<tmp1.length-1; i++)
			{
				String[] tmp2 = tmp1[i].split(":");
				if(value.equalsIgnoreCase(tmp2[0])||"default".equalsIgnoreCase(tmp2[0]))
				{
					ft = tmp2[1];
					break;
				}
			}
			
			if(ft == src)
			{
				ft = "#,##0.00";
			}
		}
		
		return ft;
	}
	
	/**
	 * 设置列宽
	 * @param sheet
	 * @param length
	 */
	private void setColumnWidth(HSSFSheet sheet, short length)
	{	
		for(int i=0; i<=length; i++)
		{
			try{
				sheet.autoSizeColumn(i);
			}catch(Exception e)
			{
				sheet.setColumnWidth(i, (sheet.getColumnWidth(i)+2000));
			}
			
			
			if(sheet.getColumnWidth(i)<1255)
			{
				sheet.setColumnWidth(i, (sheet.getColumnWidth(i)+2000));
			}else
			{
				sheet.setColumnWidth(i, (int)(sheet.getColumnWidth(i)*1.229));
			}
		}
	}
	
	/**
	 * 设置标题：大标题和副标题
	 * @param wb
	 * @param sheet
	 * @param request
	 * @param length
	 */
	public void setTitle(HSSFWorkbook wb,HSSFSheet sheet, short length, String reportNm)
	{		
		int index = reportNm.indexOf("（");
		String tmp1 = reportNm;
		String tmp2 = "";
		
		if(index != -1)
		{	
			tmp1 = reportNm.substring(0, index);
			tmp2 = reportNm.substring(index);
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, length));
		setMergeStyle(wb,sheet,0, (short)0, 1, length, tmp1, this.titleStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, length));
		setMergeStyle(wb,sheet,2, (short)0, 2, length, tmp2, this.subTitleStyle);	
	}
	
	/**
	 * 设置合并单元格（样式，字体，边框，值，背景色，位置，编码）
	 * @param wb
	 * @param sheet
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @param value
	 * @param style
	 */
	private void setMergeStyle(HSSFWorkbook wb,HSSFSheet sheet,int row1,short column1,int row2,short column2,String value,HSSFCellStyle style)
	{
		HSSFRow row = null; 
		HSSFCell cell = null;
		
		for( int i = row1; i <= row2; i++)
		{
			row = sheet.getRow(i);
			if(null == row)
			{
				row = sheet.createRow(i);
			}
            for(int j = column1; j <= column2; j++)
            {
            	cell = row.createCell(j);
            	cell.setCellStyle(style);
            	
            	if(i==row1 && j==column1)
                {
//            	cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
            	cell.setCellValue(value);
                }
            }  
		}
	}

	/**
	 * 返回Cell的内容
	 * @param ip_cell
	 * @return
	 */
	private String getCellVal(HSSFCell ip_cell)
	{
		String returnValue = null;
		if(ip_cell != null)
		{
			if(ip_cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
			{
				returnValue = String.valueOf(ip_cell.getBooleanCellValue());
			}
			else if(ip_cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
			{
				returnValue = String.valueOf(ip_cell.getNumericCellValue());
			}
			else
			{
				returnValue = ip_cell.getStringCellValue();
			}
		}
		return returnValue;
	}
	
	
	/**
	 * 取得一个表格的值
	 * @param col
	 * @param bean
	 * @param methods
	 * @return Object cell value
	 */
	@SuppressWarnings("unchecked")
	private Object getCellVal(String col, String defaultText, Object bean, Method[] methods)
	{
		Object value = null;
		try
		{
			if (StringUtils.isEmpty(col))
			{
				value = defaultText;
			}
			else
			{
				if (bean instanceof Map)
				{
					value = ((Map)bean).get(ServiceTool.datebaseToJava(col));
				}
				else
				{
					for (int k = 0; k < methods.length; k++)
					{
						if (methods[k].getName().equals("get" + col.substring(0, 1).toUpperCase() + col.substring(1)))
						{
							value = methods[k].invoke(bean, null);
							break;
						}
					}
				}
				
			}
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return value;
	}


	private void setHeader(HSSFWorkbook wb,HSSFSheet sheet, HeaderColsBean col, RowColNumBean2 rowColNumBean2, Object[] params)
	{
		short rowspan = Short.parseShort(col.getRowspan());
		short colspan = Short.parseShort(col.getColspan());
		
		int beginRow = rowColNumBean2.getRowNum();
		int beginCol = rowColNumBean2.getColNum();
		if(beginRow < rowColNumBean2.getColRowNum()[beginCol])
		{
			for(int i = beginCol + 1, len = rowColNumBean2.getColRowNum().length; i < len; i++)
			{
				if(beginRow == rowColNumBean2.getColRowNum()[i])
				{
					beginCol = i;
					break;
				}
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(beginRow , beginRow + rowspan-1, beginCol, (beginCol + colspan-1)));
		
		String value = col.getCapName();
		try
		{
			if (col.getParamsIndex() != null && col.getParamsIndex().length != 0)
			{
				Object[] loc_params = new Object[col.getParamsIndex().length];
				for (int i = 0; i < loc_params.length; i++)
				{
					loc_params[i] = params[col.getParamsIndex()[i]];
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		setMergeStyle(wb,sheet,beginRow, (short) beginCol, beginRow + rowspan-1, (short) (beginCol + colspan-1),ServiceTool.getElVal(WebUtils.getRequest(), value), this.headStyle);

		rowColNumBean2.setColNum(rowColNumBean2.getColNum()+colspan);
		for(int i=beginCol; i<beginCol+colspan; i++)
		{
			rowColNumBean2.getColRowNum()[i] = (short)(beginRow + rowspan);
		}
		
	}

	// patch from Karsten Voges
	/**
	 * Escape certain values that are not permitted in excel cells.
	 * @param rawValue the object value
	 * @return the escaped value
	 */
	protected String escapeColumnValue(Object rawValue)
	{
		if (rawValue == null)
		{
			return null;
		}
		String returnString = ObjectUtils.toString(rawValue);
		// escape the String to get the tabs, returns, newline explicit as \t \r \n
		returnString = StringEscapeUtils.escapeJava(StringUtils.trimToEmpty(returnString));
		// remove tabs, insert four whitespaces instead
		returnString = StringUtils.replace(StringUtils.trim(returnString), "\\t", "    ");
		// remove the return, only newline valid in excel
		returnString = StringUtils.replace(StringUtils.trim(returnString), "\\r", " ");
		// unescape so that \n gets back to newline
		returnString = StringEscapeUtils.unescapeJava(returnString);
		return returnString;
	}

	
	/**
	 * 含层级报表格式初始化
	 * @param wb
	 * @param layerNumber
	 */
	@SuppressWarnings("unchecked")
	private void setLayerStyle(HSSFWorkbook wb,int layerNumber,String[] format)
	{		
		
		for(int i=0; i<format.length; i++)
		{
			String ft = format[i];
			switch(layerNumber)
			{
				case 2:
					this.strDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray2.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray2.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray3.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray3.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					break;
				case 3:
					this.strDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray2.add(getStyle(wb,HSSFColor.RED.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray2.add(getStyle(wb,HSSFColor.RED.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray3.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray3.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray4.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray4.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					break;
				case 4:
					this.strDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray2.add(getStyle(wb,HSSFColor.RED.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray2.add(getStyle(wb,HSSFColor.RED.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray3.add(getStyle(wb,HSSFColor.GREEN.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray3.add(getStyle(wb,HSSFColor.GREEN.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray4.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray4.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray5.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray5.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					break;
				case 5:
					this.strDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray1.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray2.add(getStyle(wb,HSSFColor.RED.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray2.add(getStyle(wb,HSSFColor.RED.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray3.add(getStyle(wb,HSSFColor.GREEN.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray3.add(getStyle(wb,HSSFColor.GREEN.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray4.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray4.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray5.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray5.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					this.strDataStyleArray.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray.add(getStyle(wb,HSSFColor.BLUE.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					break;
				default:
					this.strDataStyleArray.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_50_PERCENT.index,HSSFCellStyle.ALIGN_LEFT,ft,"STR"));
					this.numDataStyleArray.add(getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_40_PERCENT.index,HSSFCellStyle.ALIGN_RIGHT,ft,"NUM"));
					break;
			}
		}	
	}
	
	/**
	 * 格式初始化
	 * @param wb
	 */
	public void setStyle(HSSFWorkbook wb)
	{
		HSSFPalette pt = wb.getCustomPalette();
		pt.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index, (byte)204, (byte)255, (byte)204);	//表头背景颜色
		pt.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte)255, (byte)255, (byte)204);	//字符串型数据背景颜色
		pt.setColorAtIndex(HSSFColor.GREY_50_PERCENT.index, (byte)153, (byte)204, (byte)255);	//数字型数据背景颜色
		
		this.titleStyle = getStyle(wb,HSSFColor.BLACK.index,HSSFColor.WHITE.index,HSSFCellStyle.ALIGN_CENTER,null,null);
		HSSFFont font = this.titleStyle.getFont(wb);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight((short)300);
		
		this.subTitleStyle = getStyle(wb,HSSFColor.BLACK.index,HSSFColor.WHITE.index,HSSFCellStyle.ALIGN_CENTER,null,null);
		this.headStyle = getStyle(wb,HSSFColor.BLACK.index,HSSFColor.GREY_25_PERCENT.index,HSSFCellStyle.ALIGN_CENTER,null,null);
	}
	
	/**
	 * 创建最一般的格式
	 * @param wb
	 * @return
	 */
	private HSSFCellStyle getStyle(HSSFWorkbook wb,short color,short bgColor,short align,String format,String type)
	{
		HSSFCellStyle style = wb.createCellStyle();
		String tmp = ServiceTool.getElVal(WebUtils.getRequest(), format);
		if(format==null || ! format.endsWith("@response"))
		{
			style.setDataFormat(wb.createDataFormat().getFormat(ServiceTool.getDefaultFormat(tmp, type)));
		}
		
		
		HSSFFont font = wb.createFont();
		font.setColor(color);
		style.setFont(font);										//字体颜色
		style.setFillForegroundColor(bgColor);						//背景颜色
		
		style.setBorderBottom((short)1);							//下边框    
		style.setBorderLeft((short)1);								//左边框    
		style.setBorderRight((short)1);								//右边框    
		style.setBorderTop((short)1);								//上边框
		
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);		//填充方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//垂直居中
		style.setAlignment(align);									//水平方向
		//style.setWrapText(true);									//自动换行
		
		return style;
	}

	
	private HSSFCellStyle getStyle(int layer,String type,int index)
	{	
		Object obj = null;
		
		switch(layer)
		{
		case 1:
			obj = "NUM".equalsIgnoreCase(type)?this.numDataStyleArray1.get(index):this.strDataStyleArray1.get(index);
			break;
		case 2:
			obj = "NUM".equalsIgnoreCase(type)?this.numDataStyleArray2.get(index):this.strDataStyleArray2.get(index);
			break;
		case 3:
			obj = "NUM".equalsIgnoreCase(type)?this.numDataStyleArray3.get(index):this.strDataStyleArray3.get(index);
			break;
		case 4:
			obj = "NUM".equalsIgnoreCase(type)?this.numDataStyleArray4.get(index):this.strDataStyleArray4.get(index);
			break;
		case 5:
			obj = "NUM".equalsIgnoreCase(type)?this.numDataStyleArray5.get(index):this.strDataStyleArray5.get(index);
			break;
		default:
			obj = "NUM".equalsIgnoreCase(type)?this.numDataStyleArray.get(index):this.strDataStyleArray.get(index);
			break;
		}
		
		HSSFCellStyle style = (HSSFCellStyle)obj;
		return style;
	}
	
	private HSSFCellStyle titleStyle = null;
	private HSSFCellStyle subTitleStyle = null;
	private HSSFCellStyle headStyle = null;
	
	private List strDataStyleArray = new ArrayList();
	private List numDataStyleArray = new ArrayList();
	private List strDataStyleArray1 = new ArrayList();
	private List strDataStyleArray2 = new ArrayList();
	private List strDataStyleArray3 = new ArrayList();
	private List strDataStyleArray4 = new ArrayList();
	private List strDataStyleArray5 = new ArrayList();
	private List numDataStyleArray1 = new ArrayList();
	private List numDataStyleArray2 = new ArrayList();
	private List numDataStyleArray3 = new ArrayList();
	private List numDataStyleArray4 = new ArrayList();
	private List numDataStyleArray5 = new ArrayList();
	
}
