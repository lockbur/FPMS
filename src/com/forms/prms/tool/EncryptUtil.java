package com.forms.prms.tool;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ibm.crypto.provider.IBMJCE;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : ERP<br>
 * JDK version used : jdk 1.6.0<br>
 * Description : 数据信息加密解密工具类<br>
 * Comments Name : com.forms.prms.tool.EncryptUtil.java <br>
 * author : GuoMingSheng <br>
 * date : 2014-6-24<br>
 * Version : 1.00 <br>
 * editor : GuoMingSheng<br>
 * editorDate : 2014-6-24<br>
 */
public class EncryptUtil
{
	private static final String DEFAULT_KEY_SOURCE = "com.forms.cdap.tool.encrypt.EncryptUtil";
	private static final String ENCRYPT_KEY_TYPE = "DES";
	private static final String ENCRYPT_TYPE = "DES/ECB/NoPadding";
	private static final byte ENCRYPT_ADDITION = 4;

	private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
	
	/**
	 * 这个是用于将byte数组转化为数字字符串，该数字不能小于228<br>
	 * （bye范围-128-127，且为了一个byte转化为三个字符，所以至少是228）
	 * */
	private static final int NUMBER_SUFFIX = 228;

	/** 密钥 */
	private String keySource = null;

	/**
	 * @return the keySource
	 */
	public String getKeySource()
	{
		return keySource;
	}

	/**
	 * @param keySource the keySource to set
	 */
	public void setKeySource(String keySource)
	{
		if (keySource == null || "".equals(keySource))
		{
			this.keySource = EncryptUtil.DEFAULT_KEY_SOURCE;
		}
		else
		{
			this.keySource = keySource;
		}
	}

	/**
	 * @param keySource
	 */
	public EncryptUtil(String keySource)
	{
		super();
		this.setKeySource(keySource);
	}

	/**
	 * 获取密钥
	 * 
	 * @param ip_keySrc
	 * @return
	 */
	private static Key getKey(String ip_keySrc) throws Exception
	{
		Key loc_result = null;
		try
		{
			KeyGenerator loc_generator = KeyGenerator.getInstance(EncryptUtil.ENCRYPT_KEY_TYPE);

			if (ip_keySrc == null || "".equals(ip_keySrc))
			{
				ip_keySrc = EncryptUtil.DEFAULT_KEY_SOURCE;
			}

			Security.addProvider(new IBMJCE());
            SecureRandom secureRandom = SecureRandom.getInstance("IBMSecureRandom",new IBMJCE());
            secureRandom.setSeed(ip_keySrc.getBytes());
			loc_generator.init(secureRandom);
			loc_result = loc_generator.generateKey();
			loc_generator = null;
		}
		catch (Exception e)
		{
			throw new Exception("ENCRYPT00 : could not get the secret key!", e);
		}
		return loc_result;
	}

	/**
	 * 加密字符串数据
	 * 
	 * @param srcStr
	 * @return
	 */
	public String encryptStr(String ip_srcStr) throws Exception
	{
		String loc_result = null;
		BASE64Encoder loc_base64en = new BASE64Encoder();
		try
		{
			byte[] loc_strBytes = ip_srcStr.getBytes(EncryptUtil.CHARSET_UTF_8);
			loc_strBytes = this.encryptBytes(loc_strBytes);
			loc_result = loc_base64en.encode(loc_strBytes);
		}
		catch (Exception e)
		{
			loc_result = null;
			throw new Exception("ENCRYPT10 : failed to encrypt the string!", e);
		}
		return loc_result;
	}

	/**
	 * 加密字符串数据(结果转化为数字字符串)
	 * 
	 * @param srcStr
	 * @return
	 */
	public String encryptNumStr(String ip_srcStr) throws Exception
	{
		String loc_result = null;
		try
		{
			byte[] loc_strBytes = this.encryptStr(ip_srcStr).getBytes(EncryptUtil.CHARSET_UTF_8);
			StringBuilder loc_sb = new StringBuilder();
			if (loc_strBytes != null)
			{
				for (int i = 0, lenI = loc_strBytes.length; i < lenI; i++)
				{
					loc_sb.append(String.valueOf((int) loc_strBytes[i] + EncryptUtil.NUMBER_SUFFIX));
				}
			}
			loc_result = loc_sb.toString();
		}
		catch (Exception e)
		{
			loc_result = null;
			throw new Exception("ENCRYPT11 : failed to encrypt the string of number!", e);
		}
		return loc_result;
	}

	/**
	 * 加密字节数组数据
	 * 
	 * @param srcBytes
	 * @return
	 */
	public byte[] encryptBytes(byte[] ip_srcBytes) throws Exception
	{
		byte[] loc_result = null;
		Cipher loc_cipher = null;
		int lenSrc = ip_srcBytes.length;
		int lenSuffix = lenSrc % 8;
		try
		{
			loc_cipher = Cipher.getInstance(EncryptUtil.ENCRYPT_TYPE);
			loc_cipher.init(Cipher.ENCRYPT_MODE, EncryptUtil.getKey(this.keySource));
			if (lenSuffix > 0)
			{
				lenSuffix = 8 - lenSuffix;
				loc_result = Arrays.copyOf(ip_srcBytes, lenSrc + lenSuffix);
				Arrays.fill(loc_result, lenSrc, lenSrc + lenSuffix, EncryptUtil.ENCRYPT_ADDITION);
			}
			else
			{
				loc_result = ip_srcBytes;
			}
			loc_result = loc_cipher.doFinal(loc_result);
		}
		catch (Exception e)
		{
			loc_result = null;
			throw new Exception("ENCRYPT12 : failed to encrypt the array of bytes!", e);
		}
		finally
		{
			loc_cipher = null;
		}
		return loc_result;
	}

	/**
	 * 解密字符串数据
	 * 
	 * @param ip_srcSrc
	 * @return
	 */
	public String decipherStr(String ip_srcStr) throws Exception
	{
		String loc_result = null;
		BASE64Decoder loc_base64de = new BASE64Decoder();
		try
		{
			byte[] loc_strBytes = loc_base64de.decodeBuffer(ip_srcStr);
			loc_strBytes = this.decipherBytes(loc_strBytes);
			loc_result = new String(loc_strBytes, EncryptUtil.CHARSET_UTF_8);
		}
		catch (Exception e)
		{
			loc_result = null;
			throw new Exception("ENCRYPT20 : failed to decipher the string!", e);
		}
		return loc_result;
	}

	/**
	 * 解密字符串数据(针对于encryptNumStr的解密运算)
	 * 
	 * @param ip_srcSrc
	 * @return
	 */
	public String decipherNumStr(String ip_srcNumStr) throws Exception
	{
		String loc_result = null;
		if (ip_srcNumStr == null || "".equals(ip_srcNumStr) || ip_srcNumStr.length() % 3 != 0)
		{
			throw new Exception("ENCRYPT21 : the source string of decipher is invalid!");
		}
		int lenI = ip_srcNumStr.length() / 3;
		byte[] loc_bytes = new byte[lenI];
		for (int i = 0; i < lenI; i++)
		{
			loc_bytes[i] = (byte)(Integer.parseInt(ip_srcNumStr.substring(i * 3, i * 3 + 3)) - EncryptUtil.NUMBER_SUFFIX);
		}
		loc_result = new String(loc_bytes, EncryptUtil.CHARSET_UTF_8);
		try
		{
			loc_result = this.decipherStr(loc_result);
		}
		catch (Exception e)
		{
			loc_result = null;
			throw new Exception("ENCRYPT22 : failed to decipher the string of number!", e);
		}
		return loc_result;
	}

	/**
	 * 解密字节数组数据
	 * 
	 * @param ip_srcBytes
	 * @return
	 */
	public byte[] decipherBytes(byte[] ip_srcBytes) throws Exception
	{
		Cipher loc_cipher = null;
		byte[] loc_result = null;
		try
		{
			loc_cipher = Cipher.getInstance(EncryptUtil.ENCRYPT_TYPE);
			loc_cipher.init(Cipher.DECRYPT_MODE, EncryptUtil.getKey(this.keySource));
			loc_result = loc_cipher.doFinal(ip_srcBytes);
			int i = loc_result.length - 1;
			for (; i > 0; i--)
			{
				if (loc_result[i] != EncryptUtil.ENCRYPT_ADDITION)
				{
					break;
				}
			}
			loc_result = Arrays.copyOf(loc_result, i + 1);
		}
		catch (Exception e)
		{
			throw new Exception("ENCRYPT23 : failed to decipher the array of bytes!", e);
		}
		finally
		{
			loc_cipher = null;
		}
		return loc_result;
	}
}
