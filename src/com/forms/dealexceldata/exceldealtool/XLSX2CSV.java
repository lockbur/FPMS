package com.forms.dealexceldata.exceldealtool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.forms.platform.core.util.Tool;
import com.forms.prms.web.init.SystemParamManage;

/**
 * A rudimentary XLSX -> CSV processor modeled on the POI sample program
 * XLS2CSVmra by Nick Burch from the package
 * org.apache.poi.hssf.eventusermodel.examples. Unlike the HSSF version, this
 * one completely ignores missing rows.
 * <p/>
 * Data sheets are read using a SAX parser to keep the memory footprint
 * relatively small, so this should be able to read enormous workbooks. The
 * styles table and the shared-string table must be kept in memory. The standard
 * POI styles table class is used, but a custom (read-only) class is used for
 * the shared string table because the standard POI SharedStringsTable grows
 * very quickly with the number of unique strings.
 * <p/>
 * Thanks to Eric Smith for a patch that fixes a problem triggered by cells with
 * multiple "t" elements, which is how Excel represents different formats (e.g.,
 * one word plain and one word bold).
 * 
 * @author Chris Lott
 */
public class XLSX2CSV {
	public XLSX2CSV(){
		
	}
    /**
     * The type of the data value is indicated by an attribute on the cell. The
     * value is usually in a "v" element within the cell.
     */
    enum xssfDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
    }

    /**
     * Derived from http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
     * <p/>
     * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
     * http://www.ecma-international.org/publications/standards/Ecma-376.htm
     * <p/>
     * A web-friendly version is http://openiso.org/Ecma/376/Part4
     */
    class MyXSSFSheetHandler extends DefaultHandler {

        /**
         * Table with styles
         */
        private StylesTable stylesTable;

        /**
         * Table with unique strings
         */
        private ReadOnlySharedStringsTable sharedStringsTable;

        /**
         * Destination for data
         */
        private final PrintStream output;

        /**
         * Number of columns to read starting with leftmost
         */
        private final int minColumnCount;

        // Set when V start element is seen
        private boolean vIsOpen;

        // Set when cell start element is seen;
        // used when cell close element is seen.
        private xssfDataType nextDataType;

        // Used to format numeric cell values.
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;

        private int thisColumn = -1;
        // The last column printed to the output stream
        private int lastColumnNumber = -1;

        // Gathers characters as they are seen.
        private StringBuffer value;

        /**
         * Accepts objects needed while parsing.
         * 
         * @param styles
         *            Table of styles
         * @param strings
         *            Table of shared strings
         * @param cols
         *            Minimum number of columns to show
         * @param target
         *            Sink for output
         */
        public MyXSSFSheetHandler(StylesTable styles,
                ReadOnlySharedStringsTable strings, int cols, PrintStream target) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
         * java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {

            if ("inlineStr".equals(name) || "v".equals(name)) {
                vIsOpen = true;
                // Clear contents cache
                value.setLength(0);
            }
            // c => cell
            else if ("c".equals(name)) {
                // Get the cell reference
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length(); ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }
                }
                thisColumn = nameToColumn(r.substring(0, firstDigit));

                // Set up defaults.
                this.nextDataType = xssfDataType.NUMBER;
                this.formatIndex = -1;
                this.formatString = null;
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                if ("b".equals(cellType))
                    nextDataType = xssfDataType.BOOL;
                else if ("e".equals(cellType))
                    nextDataType = xssfDataType.ERROR;
                else if ("inlineStr".equals(cellType))
                    nextDataType = xssfDataType.INLINESTR;
                else if ("s".equals(cellType))
                    nextDataType = xssfDataType.SSTINDEX;
                else if ("str".equals(cellType))
                    nextDataType = xssfDataType.FORMULA;
                else if (cellStyleStr != null) {
                    // It's a number, but almost certainly one
                    // with a special style or format
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                    if (this.formatString == null)
                        this.formatString = BuiltinFormats
                                .getBuiltinFormat(this.formatIndex);
                }
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
         * java.lang.String, java.lang.String)
         */
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            String thisStr = null;

            // v => contents of a cell
            if ("v".equals(name)) {
                // Process the value contents as required.
                // Do now, as characters() may be called more than once
                switch (nextDataType) {

                case BOOL:
                    char first = value.charAt(0);
                    thisStr = first == '0' ? "FALSE" : "TRUE";
                    break;

                case ERROR:
                    thisStr = "\"ERROR:" + value.toString() + '"';
                    break;

                case FORMULA:
                    // A formula could result in a string value,
                    // so always add double-quote characters.
                    thisStr = '"' + value.toString() + '"';
                    break;

                case INLINESTR:
                    // TODO: have seen an example of this, so it's untested.
                    XSSFRichTextString rtsi = new XSSFRichTextString(value
                            .toString());
                    thisStr = '"' + rtsi.toString() + '"';
                    break;

                case SSTINDEX:
                    String sstIndex = value.toString();
                    try {
                        int idx = Integer.parseInt(sstIndex);
                        XSSFRichTextString rtss = new XSSFRichTextString(
                                sharedStringsTable.getEntryAt(idx));
                        thisStr = '"' + rtss.toString() + '"';
                    } catch (NumberFormatException ex) {
                        output.println("Failed to parse SST index '" + sstIndex
                                + "': " + ex.toString());
                    }
                    break;

                case NUMBER:
                    String n = value.toString();
                    if (this.formatString != null)
                        thisStr = formatter.formatRawCellContents(Double
                                .parseDouble(n), this.formatIndex,
                                this.formatString);
                    else
                        thisStr = n;
                    break;

                default:
                    thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                    break;
                }

                // Output after we've seen the string contents
                // Emit commas for any fields that were missing on this row
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
                for (int i = lastColumnNumber; i < thisColumn; ++i)
                    output.print(',');

                // Might be the empty string.
                output.print(thisStr);

                // Update column
                if (thisColumn > -1)
                    lastColumnNumber = thisColumn;

            } else if ("row".equals(name)) {

                // Print out any missing commas if needed
                if (minColumns > 0) {
                    // Columns are 0 based
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    for (int i = lastColumnNumber; i < (this.minColumnCount); i++) {
                        output.print(',');
                    }
                }

                // We're onto a new row
                output.println();
                lastColumnNumber = -1;
            }

        }

        /**
         * Captures characters only if a suitable element is open. Originally
         * was just "v"; extended for inlineStr also.
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (vIsOpen)
                value.append(ch, start, length);
        }

        /**
         * Converts an Excel column name like "C" to a zero-based index.
         * 
         * @param name
         * @return Index corresponding to the specified name
         */
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
        }

    }

    // /////////////////////////////////////

    private OPCPackage xlsxPackage;
    private int minColumns;
    private PrintStream output;
    private static final String OUTPUT_CHARSET = SystemParamManage.getInstance().getParaValue("IMP_FILE_CHARSET");
    private static String SQLLDR_CHARSET = SystemParamManage.getInstance().getParaValue("IMP_FILE_CHARSET_SQLLDR");
 
    /**
     * Creates a new XLSX -> CSV converter
     * 
     * @param pkg
     *            The XLSX package to process
     * @param output
     *            The PrintStream to output the CSV to
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     */
    public XLSX2CSV(OPCPackage pkg, PrintStream output, int minColumns) {
        this.xlsxPackage = pkg;
        this.output = output;
        this.minColumns = minColumns;
    }
    
    //TODO catch exceptions
    public XLSX2CSV(String inputFilePath, String outputFilePath) throws Exception {
        xlsxPackage = OPCPackage.open(inputFilePath, PackageAccess.READ);
        output = new PrintStream(outputFilePath,OUTPUT_CHARSET);
        minColumns = -1;
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and
     * shared-strings tables.
     * 
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(StylesTable styles,
            ReadOnlySharedStringsTable strings, InputStream sheetInputStream)
            throws IOException, ParserConfigurationException, SAXException {

        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ContentHandler handler = new MyXSSFSheetHandler(styles, strings,
                this.minColumns, this.output);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     * @throws Exception 
     */
    public void process() throws Exception {
    	try {
    		  ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(
    	                this.xlsxPackage);
    	        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
    	        StylesTable styles = xssfReader.getStylesTable();
    	        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader
    	                .getSheetsData();
    	        int index = 0;
    	        while (iter.hasNext()) {
    	        	try {
    	        		InputStream stream = iter.next();
        	            String sheetName = iter.getSheetName();
        	            this.output.println(sheetName + " [index=" + index + "]:");
        	            processSheet(styles, strings, stream);
        	            stream.close();
        	            ++index;
					} catch (Exception e) {
//						--
					}
    	        		
    	            
    	        }
    	       
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("将XLSX文件转换为TXT文件出错");
		}finally{
			 this.output.close();
			 this.xlsxPackage.close();
		}
    }
    
    public static Map<String, Object> covertXLSX2CSV(String inputFilePath, String sheetName,
			int minColumns, String outFilePath, String outFileType,
			String taskBatchNo,String tempHead) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		BufferedReader br = null;
		BufferedWriter bw = null;
		String xlsl_txt = "";
		int row = 0;
		try {
			String indexStr = "";
			// .txt文件使用"|"分割 .csv文件用","分割
			if (".txt".equals(outFileType)) {
				indexStr = " | ";
			} else if (outFileType == null || outFileType.trim().length() == 0) {
				indexStr = " | ";
				outFileType = ".txt";
			} else {
				indexStr = ",";
			}
			// 判断.csv文件路径是否有输入
			
			if (outFilePath == null || outFilePath.trim().length() == 0) {
				String mkdirsPath = inputFilePath.substring(0,
						inputFilePath.lastIndexOf("."));
				xlsl_txt = mkdirsPath+"_temp"+outFileType;
				outFilePath = mkdirsPath + outFileType;
				// 判断文件是否存在
				File file = new File(outFilePath);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				// 创建文件
				file.createNewFile();
			}
			
			bw = new BufferedWriter(new OutputStreamWriter(  
	                new FileOutputStream(new File(outFilePath),true), SQLLDR_CHARSET));
			//将xlsl转换问txt
			XLSX2CSV xlsx2csv = new XLSX2CSV(inputFilePath, xlsl_txt);
			 xlsx2csv.process();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(xlsl_txt),OUTPUT_CHARSET));
			//读取xlsl转化为txt的文件
			String line = "";
			boolean startWrite= false;//读写开始标记
			boolean excelHead=false;//表头记录标识
			StringBuffer content = new StringBuffer();
			while ((line = br.readLine()) != null) 
			{
				if (!Tool.CHECK.isEmpty(line)) {
					if (line.contains("[index=") && !line.contains("Sheet1") && startWrite) {
						break;
					}
					if (line.contains("Sheet1") && line.contains("[index=")) {
						//第一个excel的数据
						startWrite = true;
						excelHead = true;//他为true则下一行数据是表头
						continue;
					}else {
						if (startWrite == true && excelHead ==true) {
							//这个是表头
							excelHead = false;
							//判断表头是否正确
							String excelHeadValue = line.replaceAll("\"", "");
							String tempHeadValue = tempHead.substring(1,tempHead.lastIndexOf("]")).replaceAll(" ", "");
							if (!tempHeadValue.equals(excelHeadValue.replaceAll(" ", ""))) {
								throw new Exception("模板表头应为【"+tempHeadValue+"】，实际为【"+excelHeadValue+"】");
							}
						}else if (startWrite == true && excelHead == false) {
							row = row + 1;
							//这个是数据
							content.append(taskBatchNo+""+indexStr);//批次号
							content.append(row +""+indexStr);
							String rowValues[] = line.split(",");
							String rowValues2[] = new String[minColumns];
							if (rowValues.length > minColumns) {
								throw new Exception("文件第"+row+"行的数据列数不对，应该为【"+minColumns+"】列,实际为【"+rowValues.length+"】列");
							}
							System.arraycopy(rowValues, 0, rowValues2, 0, rowValues.length);
							if(null!= rowValues2 && rowValues2.length>0){
								for (int i = 0; i < rowValues2.length; i++) {
									if (!Tool.CHECK.isEmpty(rowValues2[i])) {
										content.append(rowValues2[i].replaceAll("\"", "")+indexStr);
									}else {
										content.append(""+indexStr);
									}
									
								}
							}
							//换行
							content.append(ExcelDealValues.LINE_SEPARATOR);
							if (row % 10000 == 0 ) {
								bw.write(content.toString());
								bw.flush();
								content.setLength(0);
							}
						}
					}
				}
					
				
			}
			if (!startWrite) {
				//没有Sheet1 则报错
				throw new Exception("导入的Excel没有名称为Sheet1的Sheet，导入的数据必须放在Sheet1里面");
			}
			bw.write(content.toString()); // 写入文件
			bw.flush();
		}finally {
			br.close();
			bw.close();
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(bw);
		}
		map.put("outFilePath",outFilePath);
		map.put("row", row);
		return map;
	}
    public static void main(String []d) throws Exception {
        XLSX2CSV xlsx2csv = new XLSX2CSV("c:/123.xlsx", "c:/22.txt");
        xlsx2csv.process();
//        System.out.println("00");
//    	BufferedReader br = null;
//		BufferedWriter bw = null;
//		br = new BufferedReader(new FileReader("F:/22.txt"));
//		String line ="";
//		while ((line = br.readLine()) != null) 
//		{
//		  System.out.println(line);
//		}
//		System.gc();
//		File file = new File("F:/22.txt");
//		file.delete();
        
    }


}