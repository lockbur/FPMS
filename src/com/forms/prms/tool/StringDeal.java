package com.forms.prms.tool;

/*
 *  ��    �ߣ�  chenxiaolong
 *  ����˵��  �ַ����� 
 *            
 *  ��    �ڣ�  2006-02-15
 *  �޸���ʷ�� 
 *            
 *  ��Ȩ���У� �����ķ��������޹�˾
 */

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringDeal {
	/***************************************************************************
	 * ���ص�ǰ���ڣ���ʽΪyyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate1() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();

		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * ���ص�ǰ���ڣ���ʽΪyyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentDate2() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = new Date();

		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * ������yyyymmddת����yyyy-mm-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String transferDateFormat1(String date) {
		if (!Check.isNull(date)) {
			date = date.trim();
		} else {
			return null;
		}

		if (!Check.isDigit(date) || !Check.checkLength(date, 8)) {
			return date;
		}

		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
				+ date.substring(6);
	}

	/***************************************************************************
	 * ������yyyy-mm-ddת����yyyymmdd
	 * 
	 * @param date
	 * @return
	 */
	public static String transferDateFormat2(String date) {
		if (!Check.isNull(date)) {
			date = date.trim();
		} else {
			return null;
		}

		if (!Check.isDate(date)) {
			return date;
		}

		return replaceAll(date, "-", "");
	}

	/***************************************************************************
	 * ������yyyy-mm-dd��ʽ��ʱ���ַ�ת�����������
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date formatDate(String date) {
		if (!Check.isNull(date)) {
			date = date.trim();
		} else {
			return null;
		}

		if (!Check.isDate(date)) {
			return null;
		}

		return java.sql.Date.valueOf(date);
	}

	/***************************************************************************
	 * ������hhmmssת����hh:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String transferTimeFormat1(String time) {
		if (!Check.isNull(time)) {
			time = time.trim();
		} else {
			return null;
		}
		if (!Check.isDigit(time) || !Check.checkLength(time, 6)) {
			return time;
		}

		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
				+ time.substring(4);
	}

	/***************************************************************************
	 * ������hh:mm:ssת����hhmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String transferTimeFormat2(String time) {
		if (!Check.isNull(time)) {
			time = time.trim();
		} else {
			return null;
		}
		if (!Check.isTime(time)) {
			return time;
		}

		return replaceAll(time, ":", "");
	}

	/***************************************************************************
	 * ������hh:mm:ss��ʽ��ʱ���ַ�ת��ʱ�������
	 * 
	 * @param time
	 * @return
	 */
	public static java.sql.Time formatTime(String time) {
		if (!Check.isNull(time)) {
			time = time.trim();
		} else {
			return null;
		}

		if (!Check.isTime(time)) {
			return null;
		}

		return java.sql.Time.valueOf(time);
	}

	/***************************************************************************
	 * ���ص�ǰʱ�䣬��ʽΪHH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();

		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * ���ص�ǰ���ں�ʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDateAndTime1() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * ���ص�ǰ���ں�ʱ�䣬��ʽΪyyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getCurrentDateAndTime2() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date currentDate = new Date();
		String date1 = sdf.format(currentDate);

		return date1;
	}

	/***************************************************************************
	 * ��ǰʱ��Ƚ�
	 * 
	 * @param oldCalendar
	 * @param yearOffset
	 * @param monthOffset
	 * @param dayOffset
	 * @return
	 */
	public static boolean beforeCurrentTime(Calendar calendar, int yearOffset,
			int monthOffset, int dayOffset, int hourOffset, int minuteOffset,
			int secondOffset) {
		// У�����
		if (Check.isNull(calendar)) {
			return false;
		}

		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.add(Calendar.YEAR, -yearOffset);
		currentCalendar.add(Calendar.MONTH, -monthOffset);
		currentCalendar.add(Calendar.DATE, -dayOffset);
		currentCalendar.add(Calendar.HOUR, -hourOffset);
		currentCalendar.add(Calendar.MINUTE, -minuteOffset);
		currentCalendar.add(Calendar.SECOND, -secondOffset);

		return currentCalendar.after(calendar);
	}

	/***************************************************************************
	 * �����ʽ��yyyy-MM-dd HH:mm:ss��ʱ���ַ�
	 * 
	 * ��ʽ���󽫷���null
	 * 
	 * @param time
	 * @return
	 */
	public static Calendar parseTime(String time) {
		// У�����
		if (Check.isNull(time)) {
			return null;
		}

		Calendar calendar = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(time);
			calendar = Calendar.getInstance();
			calendar.setTime(date);

		} catch (ParseException pe) {

		}

		return calendar;
	}

	/***************************************************************************
	 * �ڴ�д��ĸ��������»���
	 * 
	 * @param s
	 * @return
	 */
	public static String addUnderlineBeforeCapital(String s) {
		// У�����
		if (Check.isNull(s)) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			// ����ĸ�Ǵ�д�ĳ���
			if (i != 0 && Character.isUpperCase(s.charAt(i))) {
				// ǰһ���ַ����»��ߵĳ���
				if (s.charAt(i - 1) != '_') {
					stringBuffer.append('_');
				}
			}
			stringBuffer.append(s.charAt(i));
		}

		return stringBuffer.toString();
	}

	/***************************************************************************
	 * ������aBc�ĸ�ʽת���� A_BC��ʽ������ת��java����������ݿ����������� �����ȫ��д��ʽ��ת������$������滻��_
	 * 
	 * @param s
	 * @return
	 */
	public static String javaToDatabase(String s) {
		if (!Check.isNull(s)) {
			if (s.indexOf("_") > -1) {
				// if exist '_', not change
				return s;
			}
			String s_temp = replaceAll(s, "$", "_"); // ȥ$���

			// ���ȫ�Ǵ�����ĸ������Ϊ����ݿ�����ʽ������ת��
			if (s_temp.toUpperCase().equals(s_temp)) {
				return s_temp;
			}
			return addUnderlineBeforeCapital(s_temp).toUpperCase();
		}
		return null;
	}

	/***************************************************************************
	 * ����ݿ�ĸ�ʽת����java�ĸ�ʽ
	 * 
	 * @param s
	 * @return
	 */
	public static String databaseToJava(String s) {
		if (Check.isNull(s)) {
			return null;
		}

		s = s.toLowerCase();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char code = s.charAt(i);
			if (code == '_') {
				if (i == 0 || i == 1 || i == s.length() - 1) {
					result.append('$');
				} else if (i != s.length() - 1 && s.charAt(i + 1) <= '9'
						&& s.charAt(i + 1) >= '0') {
					result.append('$');
				}
				if (i < s.length() - 1) {
					result.append(Character.toUpperCase(s.charAt(i + 1)));
				}
				i++;
			} else {
				result.append(code);
			}
		}
		return result.toString();
	}

	/***************************************************************************
	 * �滻�ַ�
	 * 
	 * @param obj
	 *            ������ַ�
	 * @param src
	 *            Ҫ�滻���ַ�
	 * @param target
	 *            ��4�滻���ַ�
	 * @return
	 */
	public static String replaceAll(String obj, String src, String target) {
		// ������
		if (Check.isNull(obj) || Check.isNull(src) || Check.isNull(target)) {
			return obj;
		}
		// �ָ�
		StringTokenizer sR = new StringTokenizer(obj, src);
		StringBuffer ret = new StringBuffer();
		int iTokens = sR.countTokens();

		int j = 1;
		// ȡ��
		while (sR.hasMoreTokens()) {

			if (j < iTokens) {
				ret.append(sR.nextToken());
				ret.append(target);
			} else {
				ret.append(sR.nextToken());
			}
			j++;
		}
		// β��
		if (obj.endsWith(src)) {
			ret.append(target);
		}
		// ͷ��
		if (obj.startsWith(src)) {
			ret.insert(0, target);
		}
		return ret.toString();
	}

	/***************************************************************************
	 * ȥ����Ҫ���ַ�ո�
	 * 
	 * @param s
	 *            ������ַ�
	 * @param c
	 *            Ҫ�滻���ַ�����
	 * 
	 */
	public static String filt(String s, String[] c) {
		if (Check.isNull(s, "������ַ�") || Check.isNull(c, "Ҫ�滻���ַ�����")) {
			return s;
		}

		for (int i = 0; i < c.length; i++) {
			s = replaceAll(s, c[i], "");
		}

		return s;
	}

	/***************************************************************************
	 * �ϲ�2��ѡ�����
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String unitTwoSelCond(String s1, String s2) {
		// ������
		if (Check.isNull(s1, "s1") || Check.isNull(s2, "s2")) {
			return null;
		}

		// ��һ���򷵻ضԷ�
		if (s1.equals(""))
			return s2;
		if (s2.equals(""))
			return s1;

		//
		String s1_temp1 = s1.trim();
		String s2_temp1 = s2.trim();
		String s1_temp = s1_temp1.toUpperCase();
		String s2_temp = s2_temp1.toUpperCase();
		if (s1_temp.indexOf("WHERE") == 0) {
			if (s2_temp.indexOf("WHERE") == 0) {
				return s1_temp1 + " AND " + s2_temp1.substring(5);
			} else {
				return s1_temp1 + " AND " + s2_temp1;
			}
		} else {
			if (s2_temp.indexOf("WHERE") == 0) {
				return s2_temp1 + " AND " + s1_temp1;
			} else {
				return "WHERE " + s1_temp1 + " AND " + s2_temp1;
			}
		}
	}

	/***************************************************************************
	 * �ϲ�2���������
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String unitTwoSortCond(String s1, String s2) {
		// ������
		if (Check.isNull(s1, "s1") || Check.isNull(s2, "s2")) {
			return null;
		}

		// ��һ���򷵻ضԷ�
		if (s1.equals(""))
			return s2;
		if (s2.equals(""))
			return s1;

		//
		String s1_temp1 = s1.trim();
		String s2_temp1 = s2.trim();
		String s1_temp = s1_temp1.toUpperCase();
		String s2_temp = s2_temp1.toUpperCase();
		if (s1_temp.indexOf("ORDER") == 0) {
			if (s2_temp.indexOf("ORDER") == 0) {
				return s1_temp1 + ", " + s2_temp1.substring(8);
			} else {
				return s1_temp1 + ", " + s2_temp1;
			}
		} else {
			if (s2_temp.indexOf("ORDER") == 0) {
				return s2_temp1 + ", " + s1_temp1;
			} else {
				return "ORDER BY " + s1_temp1 + ", " + s2_temp1;
			}
		}
	}

	/***************************************************************************
	 * ת��ѡ�������java to db��
	 * 
	 * @param selCond
	 * @return
	 */
	public static String transSelCond(String selCond) {
		// ������
		if (Check.isNull(selCond, "selCond")) {
			return null;
		}

		// 
		String[] temp_1 = selCond.split("'", -1);

		for (int i = 0; i < temp_1.length; i += 2) {
			// ��db2 ����е����Ӧ����˫���

			String[] temp_11 = temp_1[i].split(" ", -1);
			StringBuffer temp_111 = new StringBuffer();
			for (int j = 0; j < temp_11.length; j++) {
				temp_11[j] = javaToDatabase(temp_11[j]);
				temp_111.append(' ');
				temp_111.append(temp_11[j]);
			}

			temp_1[i] = temp_111.toString();
		}

		StringBuffer temp_2 = new StringBuffer();
		for (int i = 0; i < temp_1.length; i++) {
			temp_2.append(temp_1[i]);
			temp_2.append('\'');
		}

		temp_2.deleteCharAt(temp_2.length() - 1).toString();

		return temp_2.toString();
	}

	/***************************************************************************
	 * ת�루ISO8859-1 -> GBK�� ������GBK�������ٽ���ת��
	 * 
	 * @param s
	 * @return
	 */
	public static String transCode(String s) {
		if (Check.isNull(s, "s")) {
			return null;
		}

		try {
			byte[] b = s.getBytes("ISO-8859-1");
			if (s.indexOf("?") > -1) {
				String s_temp = replaceAll(s, "?", "");
				byte[] b_temp = s_temp.getBytes("ISO-8859-1");
				for (int i = 0; i < b_temp.length; i++) {
					if (b_temp[i] == 63) {
						return s;
					}
				}

			} else {
				for (int i = 0; i < b.length; i++) {
					if (b[i] == 63) {
						return s;
					}
				}
			}
			return new String(b, "GBK");
		} catch (Exception e) {
		}
		return s;
	}

	/***************************************************************************
	 * ����ַ�
	 * 
	 * @param fillType
	 *            (Left or Right)
	 * @param src
	 * @param target
	 * @param length
	 * @return
	 */
	public static String buildFillString(String fillType, String src,
			String fillString, int length) {
		if (Check.isNull(fillType, "fillType")
				|| Check.isNull(fillString, "fillString")
				|| Check.isNull(src, "src")) {
			return null;
		}

		if (!Check.equals(fillType, "Left") && !Check.equals(fillType, "Right")) {
			return null;
		}

		int fillLength = length - src.getBytes().length;
		if (fillLength == 0) {
			// not need to fill
			return src;
		} else if (fillLength < 0) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		while (stringBuffer.length() < fillLength) {
			stringBuffer.append(fillString);
		}
		stringBuffer.delete(fillLength, stringBuffer.length());

		if (fillType.equalsIgnoreCase("Left")) {
			return stringBuffer + src;
		} else if (fillType.equalsIgnoreCase("Right")) {
			return src + stringBuffer;
		}

		return null;
	}

	/***************************************************************************
	 * ȥ������
	 * 
	 * @param fillType
	 *            (Left or Right)
	 * @param src
	 * @param fillString
	 * @return
	 */
	public static String deleteFillString(String fillType, String src,
			String fillString) {
		if (Check.isNull(fillType, "fillType")
				|| Check.isNull(fillString, "fillString")
				|| Check.isNull(src, "src")) {
			return null;
		}

		if (!Check.equals(fillType, "Left") && !Check.equals(fillType, "Right")) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		while (stringBuffer.length() < src.length()) {
			stringBuffer.append(fillString);
		}

		if (fillType.equals("Left")) {
			for (int i = 0; i < src.length(); i++) {
				if (stringBuffer.charAt(i) != src.charAt(i)) {
					return src.substring(i, src.length());
				}
			}
		} else if (fillType.equals("Right")) {
			for (int i = src.length() - 1; i >= 0; i--) {
				if (stringBuffer.charAt(i) != src.charAt(i)) {
					return src.substring(0, i + 1);
				}
			}
		}

		return null;
	}

	/***************************************************************************
	 * �ַ�ת�������תΪȫ��
	 * 
	 * @param type
	 *            (0 or 1)
	 * @param src
	 * @return
	 */
	public static String StrChange(String src) {
		if (Check.isNull(src, "src")) {
			return src;
		}
		String strcn = "���������������������������c£ãģţƣǣȣɣʣˣ̣ͣΣϣУѣңӣԣգ֣ףأ٣ڣࣱ�����������������������ܡ��������磥�ޣ#������ߣ���ۣݣ�������������������	";
		String stren = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=\\~!*#$%^@&()_+|[]{};':\",./<> ?	";

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < src.length(); i++) {
			int k = stren.indexOf(src.charAt(i));
			if (k != -1) {
				stringBuffer.append(strcn.charAt(k));
			} else {
				stringBuffer.append(src.charAt(i));
			}
		}
		return stringBuffer.toString();
	}

	public static boolean checkIsNum(String src) {
		for (int i = 0; i < src.length(); i++) {
			if (!((src.charAt(i)) >= '0' && (src.charAt(i)) <= '9')) {
				return false;
			}
		}
		return true;
	}

	/***************************************************************************
	 * �ַ��ȡ���������ô����NULL
	 * 
	 * @param type
	 *            (0 or 1)
	 * @param src
	 * @return
	 */
	public static String msSustring(String src, int beginIndex, int endIndex) {
		if (src == null || src.length() == 0) {
			return null;
		} else if (beginIndex > endIndex) {
			return null;
		} else if (endIndex > src.length()) {
			if (beginIndex <= src.length()) {
				return src.substring(beginIndex);
			} else {
				return null;
			}
		} else {
			return src.substring(beginIndex, endIndex);
		}
	}

	/**
	 * ��str�н����������ַ���ȡ��4 wengyy 2008-04-18
	 * @param str
	 * @return
	 */
	public static String subCNStr(String str)
	{	
		StringBuffer buf = new StringBuffer("");
		if(str != null)
		{		
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
			Matcher m = p.matcher(str);
			
			while(m.find())
			{
				buf.append(m.group());
			}
		}
		return buf.toString();
	}

	
	/**
	 * �ж��Ƿ�ȫ���ɰ���ַ����(δ��ȫ����)<br>
	 * ע�������ַ����ڰ�Ǳ��뷶Χ<br>
	 *    ����Ĳ���Ϊnull����ַ�ʱ������true<br>
	 * wengyy 2008-04-29
	 * @param str
	 * @return
	 */
	public static boolean isFullDBC(String str)
	{	
		if(str != null && !"".equals(str))
		{		
			Pattern p = Pattern.compile("^[\u0000-\u00ff]+$");
			Matcher m = p.matcher(str);
			
			return m.find();
		}
		else
		{
			return true;
		}
	}
	
	public static void main(String[] args){
		System.out.println(isFullSBC("1"));
	}
	
	/**
	 * �ж��Ƿ�ȫ����ȫ���ַ����(δ��ȫ����)<br>
	 * ע�������ַ�Ҳ����ȫ�Ǳ��뷶Χ<br>
	 *    ����Ĳ���Ϊnull����ַ�ʱ������true<br>
	 * wengyy 2008-04-29
	 * @param str
	 * @return 
	 */
	public static boolean isFullSBC(String str)
	{	
		if(str != null && !"".equals(str))
		{		
			Pattern p = Pattern.compile("[\u0000-\u00ff]+");
			Matcher m = p.matcher(str);
			return m.find() ? false : true;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * �ж��Ƿ�ȫΪ����
	 * @param str
	 * @return
	 */
	public static boolean isFullDigit(String str)
	{	
		if(str != null && !"".equals(str))
		{		
			Pattern p = Pattern.compile("^[0-9]+$");
			Matcher m = p.matcher(str);
			return m.find() ? true : false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * �жϽ��,��Ҫ����0
	 * @param str
	 * @return
	 */
	public static boolean isAmt(String str)
	{	
		if(str != null && !"".equals(str)){
			Pattern p = Pattern.compile("^(([1-9]\\d*(\\.\\d{1,2})?)|(0(\\.\\d{1,2})?))$");
			Matcher m = p.matcher(str);
			return m.find() ? true : false;
		}
		else
		{
			return false;
		}
	}
	
	/*public static void main(String[] args){
		
		//System.out.println(isAmt("1.0"));
	}*/
	
	/**
	 * ���ַ�ǰ��0ȥ����0001dfef335 -> 1dfef335 
	 */
	public static String trimStartZero(String str){
		String ret = null;
		Pattern p = Pattern.compile("^0*");
		Matcher m = p.matcher(str);
		if (m.find()) {
			String s = m.group();
			ret = str.replaceFirst(s, "");
		}
		return ret;
	}	
}
