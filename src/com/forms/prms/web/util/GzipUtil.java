package com.forms.prms.web.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GzipUtil {

	/**
	 * 解压GZ包，GZ包中只包含单个文件。
	 * 
	 * @param fileName
	 *            gz包的完整路径
	 * @param desPath
	 *            gz解压后文件存放的路径
	 * @param desFileName
	 *            gz解压后的文件名
	 * @throws Exception
	 */
	public static void uncompressGZIP(String fileName, String desPath, String desFileName) throws Exception {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(fileName);
			// 如果路径不存在，则创建
			File tempFile = new File(desPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			File outFile = new File(desPath, desFileName);
			out = new FileOutputStream(outFile);
			GZIPInputStream gin = new GZIPInputStream(in);
			byte[] buffer = new byte[4 * 1024];
			int readLen = 0;
			while ((readLen = gin.read(buffer)) != -1) {
				out.write(buffer, 0, readLen);
				out.flush();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 压缩GZ包，只能针对单个文件。
	 * 
	 * @param fileName
	 *            需压缩的文件
	 * @param desPath
	 *            压缩后存放的目录
	 * @throws Exception
	 */
	public static void compressGZIP(String fileName, String desPath) throws Exception {
		compressGZIP(fileName, desPath, fileName.substring(fileName.lastIndexOf("/") + 1) + ".gz");
	}

	/**
	 * 压缩GZ包，只能针对单个文件。
	 * 
	 * @param fileName
	 *            需压缩的文件
	 * @param desPath
	 *            压缩后存放的目录
	 * @param destFileName
	 *            压缩后的文件名
	 * @throws Exception
	 */
	public static void compressGZIP(String fileName, String desPath, String destFileName) throws Exception {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(fileName);
			// 如果路径不存在，则创建
			File tempFile = new File(desPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			File outFile = new File(desPath, destFileName);
			out = new FileOutputStream(outFile);
			GZIPOutputStream gout = new GZIPOutputStream(out);
			byte[] buffer = new byte[4 * 1024];
			int readLen = 0;
			while ((readLen = in.read(buffer)) != -1) {
				gout.write(buffer, 0, readLen);
				gout.flush();
			}
			gout.finish();
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// GzipUtil.UncompressGZIP("F:/住房公积金管理/资料/gz包/0702020D.INT.gz",
		// "F:/住房公积金管理/资料/gz包/");

		// GzipUtil.compressGZIP("F:/住房公积金管理/xml/TsRetFil_06_2010-12-16_01.xml",
		// "F:/住房公积金管理/xml/");
		// String
		// fileName="c:/forms/bsznt/download//FROM_FMS/PROVIDER/2015-01-14/TOERP_VENDORS_20150114.gz";
		// String
		// desPath="c:/forms/bsznt/download//FROM_FMS/PROVIDER/2015-01-14";
		// String desFileName="provider2.txt";
		// uncompressGZIP(fileName, desPath, desFileName);

		// String
		// fileName="c:/forms/bsznt/download//FROM_FMS/PROVIDER/provider.txt";
		// String desPath="c:/forms/bsznt/download//FROM_FMS/PROVIDER";
		String fileName = "c:/forms/bsznt/download//FROM_FMS/USER/testData.txt";
		String desPath = "c:/forms/bsznt/download//FROM_FMS/USER";
		// String fileName="c:/forms/bsznt/download//FROM_FMS/ORG/testData.txt";
		// String desPath="c:/forms/bsznt/download//FROM_FMS/ORG";
		compressGZIP(fileName, desPath);
	}

	/**
	 * 压缩后的文件名称为*.gz
	 * 
	 * @param fmsFile
	 * @throws IOException 
	 */
	public static void compressFile(File fmsFile) throws IOException {
		FileOutputStream fileOutputStream = null;
		CheckedOutputStream cos = null;
		ZipOutputStream out = null;
		BufferedInputStream bis = null;
		try {
//			int index =  fmsFile.getAbsolutePath().trim().lastIndexOf(".");
			String gzFile =fmsFile.getAbsolutePath()+".gz";
			File file = new File(gzFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			out = new ZipOutputStream(cos);
			String basedir = "";
			bis = new BufferedInputStream(new FileInputStream(fmsFile));
			ZipEntry entry = new ZipEntry(basedir + fmsFile.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[8192];
			while ((count = bis.read(data, 0, 8192)) != -1) {
				out.write(data, 0, count);
				out.flush();
			}
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally
		{
			if(null != bis)
			{
				bis.close();
			}
			if(null != out)
			{
				out.close();
			}
			if(null != fileOutputStream)
			{
				fileOutputStream.close();
			}
			if(null != cos)
			{
				cos.close();
			}
		}

	}
}
