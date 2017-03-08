package com.forms.prms.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImportUtil {
	/**
     * 如果文件路径不存在，则创建文件全部路径
     * @param filePath
     */
    public static void createFilePath(File filePath){
    	if(!judgePlateFlag(filePath)){
    		filePath.mkdirs();
    	}
    }
	//【工具方法】判断字符串是否能转换为数值类型
	public static boolean judgeStrToNum(String str){
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	  /**
     * 判断文件路径或客户端系统盘符是否存在
     */
    public static boolean judgePlateFlag(File panFu){
    	if(panFu.exists()){
    		return true;
    	}else{
    		return false;
    	}
    }

	/**
	 * 文件复制的工具类
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void fileCopyByBufferStream(File source, File target) throws IOException {  
        InputStream fis = null;  
        OutputStream fos = null;  
        try {  
            fis = new FileInputStream(source);  
            fos = new FileOutputStream(target);  
            byte[] buf = new byte[4096];  
            int i;  
            while ((i = fis.read(buf)) != -1) {  
                fos.write(buf, 0, i);  
            }  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	fis.close();
        	fos.close();
        }  
    }
}
