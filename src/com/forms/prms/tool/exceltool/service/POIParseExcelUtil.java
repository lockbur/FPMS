package com.forms.prms.tool.exceltool.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * POI解析Excel工具类
 * Title:		POIParseExcelUtil
 * Description:	包括获取Excel工作簿、工作表、单元格信息等工具类
 * Copyright: 	formssi
 * @author：		创建人
 * @project：	ERP
 * @date： 		日期
 * @version： 	1.0
 */
public class POIParseExcelUtil {

	public static void main(String[] args) {
//		HSSFWorkbook workbook = getWorkbookByFilePath("c:akb.xls");
//		HSSFSheet sheet0 = getSheetByIndex(workbook , 0);
//		HSSFCell cell = getCellBySheetInFuzzy(sheet0 , "A20" , Cell.CELL_TYPE_STRING);
//		System.out.println("A20单元格获取的值为："+cell.getStringCellValue());
		
		
		boolean valiResult = validateCellValue("c:akb.xls", 0, 19, 0, Cell.CELL_TYPE_STRING, "9528");
		System.out.println("校验结果："+valiResult);
		
		
		HSSFWorkbook workbook = getWorkbookByFilePath("c:akb.xls");
		int wbSheetNums = workbook.getNumberOfSheets();
		System.out.println("该表中一共有Sheet:"+wbSheetNums+"个！");
		
		
//      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
//             sheet=workbook.getSheetAt(i);
//             for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {//获取每行
//                HSSFRow row=sheet.getRow(j);
//                for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {//获取每个单元格
//                    System.out.print(row.getCell(k)+"\t");
//                }
//                System.out.println("---Sheet表"+i+"处理完毕---");
//            }
//		}
//        
//        
//        sheet.getRow(19).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//        HSSFCell cell = sheet.getRow(19).getCell(0);
//        System.out.println("单元格值的类型："+cell.getCellType());
//        System.out.println("单元格的值为:"+cell.getStringCellValue());
		
        
        
		
//		TableExcelHandler excelHandler = new TableExcelHandler("xls");
//		excelHandler.dataExcelPath = "c:akb.xls";  
//		excelHandler.openfile();
//		HSSFSheet sheet0 = (HSSFSheet)excelHandler.wbook.getSheetAt(0);
		
//		String sheet0Name = sheet0.getSheetName();
//		System.out.println(sheet0Name);
//		HSSFCell cell1900 = sheet0.getRow(19).getCell(0);
//		cell1900.setCellType(Cell.CELL_TYPE_STRING);
//		System.out.println(cell1900.getStringCellValue());
//		System.out.println("Owari");
		

		
		
		/**
		 *	测试使用系统自带的Excel解析器解读Excel的内容为List<List<String>>
			FileInputStream is = null;
			try {
				is = new FileInputStream(new File("c:akb.xls"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			ExcelAnalysisXLS excelAnalaysis = new ExcelAnalysisXLS();
			List<List<String>> contentList = excelAnalaysis.read(is , 2);
			System.out.println("开始展示内容...");
			
			for(int i=0;i<contentList.size();i++){
				System.out.println("第"+(i+1)+"行信息：");
				for(int j=0;j<contentList.get(i).size();j++){
					System.out.print(contentList.get(i).get(j)+" ");
					if(j == contentList.get(i).size()-1 ){
						System.out.println(" ");
					}
				}
			}
			
			System.out.println("展示内容结束...");
		*/
	}
	
	//根据地址(文件路径)获取工作簿对象
	public static HSSFWorkbook getWorkbookByFilePath(String excelFilePath){
		HSSFWorkbook workbook = null ;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(new File(excelFilePath)));
		} catch (FileNotFoundException e) {
			System.out.println("【getWorkbookByFilePath】-- File Not Found (excelFilePath)!!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("【getWorkbookByFilePath】-- Get Some IOException!");
			e.printStackTrace();
		}
		return workbook;
	}
	
	//根据工作簿和Sheet的下标值获取工作表对象
	public static HSSFSheet getSheetByIndex( HSSFWorkbook workbook , int sheetIndex ){
		HSSFSheet targetSheet = workbook.getSheetAt(sheetIndex);
		return targetSheet;
	}
	
	//根据工作表Sheet、行下标rowIndex、列下标colIndex获取单元格Cell对象(返回前设置Cell的数据类型)
		//CELL_TYPE_STRING、CELL_TYPE_NUMBRIC
	public static HSSFCell getCellBySheetInSpecific( HSSFSheet sheet , int rowIndex , int colIndex , int cellType ){
		HSSFCell cell = sheet.getRow(rowIndex).getCell(colIndex);
		if(null != cell){
			cell.setCellType(cellType);
			return cell;
		}else{
			return null;
		}
	}
	
	//根据Cell的下标获取单元格Cell对象(返回前设置Cell的数据类型)
	public static HSSFCell getCellBySheetInFuzzy( HSSFSheet sheet , String cellIndex , int cellType ){
		
		//1.将单元格的下标值转换成行+列的下标值
		int num1 = 0;
		int num2 = 0;
		char tempChar ;
		String rowStr = "";
		String colStr = "";
		
		for(int i=0;i<cellIndex.length();i++){
			tempChar = cellIndex.charAt(i);
			if( 65 <= tempChar && tempChar <= 90 ){
				num1 += (int)tempChar;
			}else{
				num2 = Integer.parseInt(cellIndex.substring(i , cellIndex.length()));
				break;
			}
		}
		num1 -= 65;
		num2 -= 1;
		System.out.println("num1为:"+num1);
		System.out.println("num2为:"+num2);
		
		//调用精确查找Cell对象的方法获取Cell对象
		return getCellBySheetInSpecific( sheet , num2 , num1 , cellType );
	}
	
	//校验传递参数值是否与指定Cell值一致，一致时返回true,否则返回false（传递Cell的下标为参数）
	public static boolean validateCellValue( String filePath , int sheetIndex , String cellIndex , int cellType , String compareValue ){
		HSSFWorkbook workbook = getWorkbookByFilePath(filePath);
		HSSFSheet sheet0 = getSheetByIndex(workbook , sheetIndex);
		HSSFCell cell = getCellBySheetInFuzzy(sheet0 , cellIndex , cellType);
		if(compareValue.equals(cell.getStringCellValue()) ){		//需校验值与Cell值一致
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * @methodName validateCellValue
	 * 		描述：校验传递参数值是否与指定Cell值一致，一致时返回true,否则返回false（传递Cell的下标为参数）	  
	 * @param filePath		Excel文件路径
	 * @param sheetIndex	工作表Sheet下标
	 * @param rowIndex		行下标
	 * @param colIndex		列下标
	 * @param cellType		Cell单元格值的格式
	 * @param compareValue	预计的单元格值
	 * @return
	 */
	public static boolean validateCellValue( String filePath , int sheetIndex , int rowIndex , int colIndex , int cellType , String compareValue ){
		HSSFWorkbook workbook = getWorkbookByFilePath(filePath);
		HSSFSheet sheet0 = getSheetByIndex(workbook , sheetIndex);
		HSSFCell cell = getCellBySheetInSpecific(sheet0, rowIndex, colIndex, cellType);
		if(compareValue.equals(cell.getStringCellValue()) ){		//需校验值与Cell值一致
			return true;
		}else{
			return false;
		}
	}
	
}
