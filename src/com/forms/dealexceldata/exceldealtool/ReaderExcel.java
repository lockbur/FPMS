package com.forms.dealexceldata.exceldealtool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读Excel
 * 
 * @author user
 * 
 */
public class ReaderExcel {
	public static void main(String[] args) throws Exception{
		String inputFilePath = "C:/Documents and Settings/user/Desktop/aprvBatchExport.xlsx";
		System.out.println(File.separator+","+File.separatorChar+","+File.pathSeparator);
		System.out.println(inputFilePath.substring(0, inputFilePath.lastIndexOf("/")));
//		List<String> list = readerExcel(inputFilePath);
//		System.out.println(list.size());
//		System.out.println(list.toString());
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
//		List<String[]> list1 = CovertXLSX2CSV.readerExcel(inputFilePath,
//				"Sheet1", 20);
//		System.out.println(Arrays.toString(list1.get(0)).replaceAll("\"", ""));
		String classPath = "NNNN.xxxxx.IImprot";
		String bean = classPath.substring(classPath.lastIndexOf(".")+1);
//		String beanName = bean.replaceFirst(bean., arg1);
//		System.out.println(beanName);
		String fileName = "231205540747.txt";
		String filePath ="C:/ERP/EXCEL/UPLOAD/20160123";
		File file = new File(filePath, fileName);
		if (!file.exists()) {
			System.out.println("111");
			throw new Exception("" + fileName + " is not exist");
		} else {
			System.out.println("112");
			filePath = file.getParent();
		}
	}

	/**
	 * 获取excel第一行的数据
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<String> readerExcel(String filePath) throws IOException, InvalidFormatException {
		List<String> list = new ArrayList<String>();
		File file = new File(filePath);
		OPCPackage opcPackage = OPCPackage.open(file);
		XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
//		// 3.得到Excel工作表对象
		XSSFSheet sheet = workbook.getSheetAt(0);
//		// 总行数
		int trLength = sheet.getPhysicalNumberOfRows();
		// 4.得到Excel工作表的行
		XSSFRow row = sheet.getRow(0);
		// 总列数
		int tdLength = row.getPhysicalNumberOfCells();
		for(int i=0;i<tdLength;i++){
			XSSFCell cell = row.getCell(i);
			list.add(cell.getStringCellValue());
		}
		opcPackage.close();
		return list;
	}
}
