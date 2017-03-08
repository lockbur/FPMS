package com.forms.prms.tool;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.platform.core.util.Tool;

/**
 * author : wuqm <br>
 * date : 2012-09-04<br>
 */
public class ExcelAnalysisXLS {
	  /** *//** 总行数 */
    private int totalRows = 0;
    
    /** *//** 总列数 */
    private int totalCells = 0;
    
    /** 
     * [根据流读取Excel文件]
     * @param inputStream
     * @param isExcel2003
     * @return
     */
    public List<List<String>> read(InputStream inputStream , int sheetIndex)
    {
        List<List<String>> dataLst = null;
        try
        {
            /** *//** 根据版本选择创建Workbook的方式 */
            Workbook wb = new HSSFWorkbook(inputStream);
            dataLst = read(wb , sheetIndex);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dataLst;
    }
    
    //如果没有传递sheetIndex参数，则默认读第一个Sheet
    public List<List<String>> read(InputStream inputStream){
    	return read(inputStream , 0);
    }
    
    
    /** 
     * [得到总行数]
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }
    
    /** 
     * [得到总列数]
     * @return
     */
    public int getTotalCells()
    {
        return totalCells;
    }
    
    /** 
     * [读取数据]
     * @param wb
     * @return
     */
    private List<List<String>> read(Workbook wb , int sheetIndex)
    {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        
        /** *//** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if(null == sheet){
        	//根据传递进来的下标读取的Sheet为空(找不到)，所以返回的Sheet内容也为空
        	return null;
        }
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (this.totalRows >= 1 && sheet.getRow(0) != null)
        {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        /** *//** 循环Excel的行 */
        for (int r = 0; r < this.totalRows; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            
            List<String> rowLst = new ArrayList<String>();
            /** *//** 循环Excel的列 */
            for (short c = 0; c < this.getTotalCells(); c++)
            {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (cell == null)
                {
                    rowLst.add(cellValue);
                    continue;
                }
                
                /** *//** 处理数字型的,自动去零 */
                if (Cell.CELL_TYPE_NUMERIC == cell.getCellType())
                {
                    /** *//** 在excel里,日期也是数字,在此要进行判断 */
                    if (HSSFDateUtil.isCellDateFormatted(cell))
                    {
                    	if(cell.getDateCellValue().toString().indexOf("00:00:00")>0){
                    		cellValue = Tool.DATE.getFormatDate(cell.getDateCellValue(),"yyyy-MM-dd");
                    	}else{
                    		cellValue = Tool.DATE.getFormatDate(cell.getDateCellValue(),"HH:mm:ss");
                    	}
                    }
                    else
                    {
                        cellValue = getRightStr(cell.getNumericCellValue() + "");
                    }
                }
                /** *//** 处理字符串型 */
                else if (Cell.CELL_TYPE_STRING == cell.getCellType())
                {
                    cellValue = cell.getStringCellValue();
                }
                /** *//** 处理布尔型 */
                else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType())
                {
                    cellValue = cell.getBooleanCellValue() + "";
                }
                /** *//** 其它的,非以上几种数据类型 */
                else
                {
                    cellValue = cell.toString() + "";
                }
                
                rowLst.add(cellValue);
            }
            dataLst.add(rowLst);
        }
        return dataLst;
    }
    
    /** 
     * [正确地处理整数后自动加零的情况]
     * @param sNum
     * @return
     */
    private String getRightStr(String sNum)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
        String resultStr = decimalFormat.format(new Double(sNum));
        if (resultStr.matches("^[-+]?\\d+\\.[0]+$"))
        {
            resultStr = resultStr.substring(0, resultStr.indexOf("."));
        }
        return resultStr;
    }
}
