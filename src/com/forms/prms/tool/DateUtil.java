package com.forms.prms.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 时间工具类
 * author : liy_nby <br>
 * date : 2014-6-19<br>
 */
public class DateUtil {

	private static SimpleDateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateDF1= new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateDF2= new SimpleDateFormat("yyyy 年 MM 月 dd 日");

	private static SimpleDateFormat dateTimeDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
    private static SimpleDateFormat dateTimeDF1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat timeDF = new SimpleDateFormat("HH:mm:ss");
	
	private static SimpleDateFormat dateDF3= new SimpleDateFormat("MMdd");
	private static SimpleDateFormat dateDF4= new SimpleDateFormat("yyMMdd");
	
	/**
	 * 计算两个时间的差值 “X天X小时X分钟X秒”
	 * @param start
	 * @param end
	 * @return
	 */
	public static String compareDate(String start,String end){
		try {
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = sim.parse(start);
			Date endDate = sim.parse(end);
			StringBuffer sb = new StringBuffer();
			long times=Math.abs(endDate.getTime()-startDate.getTime());//时间差的毫秒数的绝对值
			//计算出相差天数(之所以只精确到天，是因为不同的年和不同的月的天数不一样，换句话说，年和天不是一个确定的度量单位)
			long days=times/(24*3600*1000);
			//计算出小时数
			long leaveD=times%(24*3600*1000);//计算天数后剩余的毫秒数
			long hours=leaveD/(3600*1000);
			//计算相差分钟数
			long leaveH=leaveD%(3600*1000);//计算小时数后剩余的毫秒数
			long minutes=leaveH/(60*1000);
			//计算相差秒数
			long leaveM=leaveH%(60*1000);//计算分钟数后剩余的毫秒数
			long seconds=leaveM/1000;
			if(days>0){
				sb.append(days);
				sb.append("天");
			}
			if(hours>0){
				sb.append(hours);
				sb.append("小时");
			}
			if(minutes>0){
				sb.append(minutes);
				sb.append("分钟");
			}
			if(seconds>0){
				sb.append(seconds);
				sb.append("秒");
			}
			return new String(sb);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获得某些时间点包括的时间线（从第一周星期日到最后一周星期日）
	 * @param datePoints
	 * @return
	 */
	public static List<String> getDateLine(List<String> datePoints){
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateLine = new ArrayList<String>();
		//找到时间点中的最小值和最大值
		Calendar min = null;
		Calendar max = null;
		for(int i=0;i<datePoints.size();i++){
			if(datePoints.get(i)!=null&&datePoints.get(i).length()>0){
				Calendar temp = Calendar.getInstance();
				try {
					temp.setTime(sim.parse(datePoints.get(i)));
					if(min==null||temp.before(min)){
						min = Calendar.getInstance();
						min.setTime(temp.getTime());
					}
					if(max==null||temp.after(max)){
						max = Calendar.getInstance();
						max.setTime(temp.getTime());
					}
				} catch (ParseException e) {
					//donothing
				}
			}
		}
		if(min!=null&&max!=null){
			//找到min之前的第一个星期日
			while(min.get(Calendar.DAY_OF_WEEK)!=1){
				min.add(Calendar.DATE, -1);
			}
			//找到max之后的第一个星期日
			while(max.get(Calendar.DAY_OF_WEEK)!=1){
				max.add(Calendar.DATE, 1);
			}
			String indexStr = null;
			while(min.before(max)){
				indexStr = sim.format(min.getTime());
				dateLine.add(indexStr);
				min.add(Calendar.DATE, 1);
			}
			indexStr = sim.format(max.getTime());
			dateLine.add(indexStr);
		}
		return dateLine;
	}

	// 日期加减
	public static String getDaystr(int i)
	{
		/*
		 * java中对日期的加减操作 gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一.
		 * gc.add(3.-1)表示周减一. gc.add(5,-1)表示天减一. 以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.
		 * GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
		 * field参数表示年,月.日等. amount参数表示要加减的数量.
		 */
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, i);
		// gc.set(gc.get(gc.YEAR),gc.get(gc.MONTH),gc.get(gc.DATE));
		return df.format(gc.getTime());
	}
	
  public static String getDaystr(String srcDate, int days) throws ParseException
  {
    /*
     * java中对日期的加减操作 gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一.
     * gc.add(3.-1)表示周减一. gc.add(5,-1)表示天减一. 以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.
     * GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
     * field参数表示年,月.日等. amount参数表示要加减的数量.
     */
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date date = df.parse(srcDate);
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);
      gc.add(5, days);
      // gc.set(gc.get(gc.YEAR),gc.get(gc.MONTH),gc.get(gc.DATE));
      return df.format(gc.getTime());
  }
  
  
  public static String getNextDay(String srcDate, int days) throws ParseException
  {
    /*
     * java中对日期的加减操作 gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一.
     * gc.add(3.-1)表示周减一. gc.add(5,-1)表示天减一. 以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.
     * GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
     * field参数表示年,月.日等. amount参数表示要加减的数量.
     */
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      Date date = df.parse(srcDate);
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);
      gc.add(5, days);
      // gc.set(gc.get(gc.YEAR),gc.get(gc.MONTH),gc.get(gc.DATE));
      return df.format(gc.getTime());
  }

  /**
   * 月份加减
   * 
   * @param srcDate
   * @param n
   * @return
   * @throws ParseException
   */
  public static String getDaystr2(String srcDate, int n) throws ParseException
  {
    /*
     * java中对日期的加减操作 gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一.
     * gc.add(3.-1)表示周减一. gc.add(5,-1)表示天减一. 以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.
     * GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
     * field参数表示年,月.日等. amount参数表示要加减的数量.
     */
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date date = df.parse(srcDate);
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);
      gc.add(2, n);
      // gc.set(gc.get(gc.YEAR),gc.get(gc.MONTH),gc.get(gc.DATE));
      return df.format(gc.getTime());
  }
  
	/**
	 * 日期加上天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String dateAddDay(String date, int day)
	{
		// return getDateStr(year(date),month(date),day(date)+day);

		int x = days(date) + day;
		int year = 0;
		year += 400 * (x / 146097);// 每400年又146097天
		x = x % 146097;

		year += 100 * (x / 36524);// 每100年有 36524天
		x = x % 36524;

		year += 4 * (x / 1461);// 每4年有1461天
		x = x % 1461;

		year += x / 365;
		x = x % 365;
		if (x == 0)
		{
			return String.valueOf(year) + "-12-31";
		}
		year++;
		int mdays[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (DateUtil.isLeapYear(year))
			mdays[1] = 29;
		int i = 0;
		for (; i < 12; i++)
		{
			if (x <= mdays[i])
				break;
			x -= mdays[i];
		}
		String s = String.valueOf(year * 10000 + (i + 1) * 100 + x);
		return s.substring(0, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 8);
	}

	/**
	 * 日期加月
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static String dateAddMonth(String date, int month)
	{
		return getDateStr(year(date), month(date) + month, day(date));
	}

	/**
	 * 日期减月后最后一天
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static String dateRedMonthEnd(String date, int n)
	{
		String str = "";
		int year = year(date);
		int month = month(date);
		if (n >= 12)
		{
			int m = n % 12;
			int s = n / 12;
			month = 12 - m + month;
			year = year - s;
		}
		else month = month - n;
		if (month <= 10)
			str = "0" + month;
		return year + "-" + str + "-" + daysOfMonth(year, month);

	}

	/**
	 * 日期减月后第一天
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static String dateRedMonthBegin(String date, int n)
	{
		String str = "";
		int year = year(date);
		int month = month(date);
		if (n >= 12)
		{
			int m = n % 12;
			int s = n / 12;
			month = 12 - m + month;
			year = year - s;
		}
		else month = month - n;
		if (month <= 10)
			str = "0" + month;
		return year + "-" + str + "-01";

	}

	/**
	 * 日期加年
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static String dateAddYear(String date, int year)
	{
		return getDateStr(year(date) + year, month(date), day(date));
	}

	/**
	 * 取日期字符串的中的天
	 * 
	 * @param date
	 * @return
	 */
	public static int day(String date)
	{
		return day(date, "-");
	}

	/**
	 * 取日期字符串的中的天
	 * 
	 * @param date
	 * @param delimer
	 * @return
	 */
	public static int day(String date, String delimer)
	{
		return Integer.parseInt(divString(date, delimer)[2]);
	}

	private static String[] divString(String value, String mark)
	{
		// 参数检查
		if (value == null || mark == null)
		{
			return null;
		}
		String temp = value;
		int mark_length = mark.length();
		int pos = temp.indexOf(mark);
		int count = 1;
		// 计算分割的数目
		while (pos != -1)
		{
			count++;
			pos = temp.indexOf(mark, pos + mark_length);
		}

		String result[] = new String[count];

		temp = value;
		pos = temp.indexOf(mark);
		count = 0;
		int pos_begin = 0;
		// 分割字符
		while (pos != -1)
		{
			result[count] = temp.substring(pos_begin, pos);
			count++;
			pos_begin = pos + mark_length;
			pos = temp.indexOf(mark, pos_begin);
		}
		if (pos_begin >= temp.length())
			result[count] = "";
		else result[count] = temp.substring(pos_begin).trim();
		return result;
	}

	/**
	 * 返回日期在所在季度的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int dayOfQuarter(String date)
	{
		int month = month(date);
		int year = year(date);
		int day = 0;
		for (int i = month - (month - 1) % 3; i < month; i++)
		{
			day += daysOfMonth(year, i);
		}
		day += day(date);
		return day;
	}

	/**
	 * 返回日期在所在年的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int dayOfYear(String date)
	{
		int month = month(date);
		int year = year(date);
		int day = 0;
		for (int i = 1; i < month; i++)
		{
			day += daysOfMonth(year, i);
		}
		day += day(date);
		return day;
	}

	/**
	 * 返回日期自公元0年开始以来的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int days(String date)
	{
		int days = 0;
		int year = year(date) - 1;
		days += year * 365;// 每年365天
		days += year / 4;// 4年一个闰年
		days -= year / 100;// 每一百年不是闰年
		days += year / 400;// 每四百年一个闰年
		days += dayOfYear(date);
		return days;
	}

	/**
	 * 查询一个月的最大天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int daysOfMonth(int year, int month)
	{
		if (month <= 0 || month > 12)
			return -1;
		int day = 0;
		switch (month)
		{
			case 2:
				day = 28;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				day = 30;
				break;
			default:
				day = 31;
		}
		if (month == 2 && isLeapYear(year))
			day++;
		return day;
	}

	/**
	 * 获取系统日期时间（字符串格式）
	 * 
	 * @return 字符串日期时间
	 */
	public static String getDateStr()
	{
		return dateDF.format(new Date());
	}

    /**
     * 获取系统日期时间（字符串格式）不带下划线
     * 
     * @return 字符串日期时间
     */
    public static String getDateStrNO()
    {
        return dateDF1.format(new Date());
    }
    
    
    public static String getDateStrNO2()
    {
        return dateDF2.format(new Date());
    }
    
    /**
     * 获取系统日期时间（字符串格式）不带下划线没有年份  例如 0412
     * 
     * @return 字符串日期时间
     */
    public static String getDateStrNO3()
    {
        return dateDF3.format(new Date());
    }
    
    public static String getDateStrNO4()
    {
        return dateDF4.format(new Date());
    }
    
    /**
     * 将10位日期转换成不带下划线没有年份的日期  例如 传入2008-10-10  返回 1010
     * @return 字符串日期时间
     */
    public static String getDateStrNO3(String date)
    {
    	String sRtn = "";
    	try{
    		sRtn = dateDF3.format(dateDF.parse(date));
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return sRtn;
    }
    

	/**
	 * 根据年月日返回"YYYY-MM-DD"格式
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDateStr(int year, int month, int day)
	{

		Calendar cl = Calendar.getInstance();
		cl.set(year, 0, 1);
		cl.add(Calendar.MONTH, (month - 1));
		cl.add(Calendar.DATE, (day - 1));
		year = cl.get(Calendar.YEAR);
		month = cl.get(Calendar.MONTH) + 1;
		day = cl.get(Calendar.DATE);
		String date = String.valueOf(year * 10000 + month * 100 + day);
		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
	}

	/**
	 * 返回指定日期月第一天
	 */
	public static String getDateStrBefore(String str)
	{
		// String str = getDateStr();
		return str.substring(0, 4) + "-" + str.substring(5, 7) + "-" + "01";
	}

	/**
	 * 返回指定日期月最后一天
	 */
	public static String getDateStrEnd(String str)
	{
		return str.substring(0, 4)
				+ "-"
				+ str.substring(5, 7)
				+ "-"
				+ daysOfMonth(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str
						.substring(5, 7)));
	}

	/**
	 * 转换格式，date必须是 "YYYY-MM-DD" 或者是 "YYYY-MM-DD HH:MM:SS" 格式的
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDateStr(String date, String format)
	{
		if (date == null || date.length() < 10)
			return null;
		if (format.equals("YYYY-MM-DD"))
			return date.substring(0, 10);
		if (format.equals("YYYYMMDD"))
			return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		if (format.equals("YYMMDD"))
			return date.substring(2, 4) + date.substring(5, 7) + date.substring(8, 10);
		if (format.equals("YYYY-MM-DD HH:MM:SS"))
		{
			if (date.length() == 10)
				return date + " 00:00:00";
			return date.substring(0, 19);
		}
		return null;
	}

	/**
	 * 获取系统日期时间（字符串格式）
	 * 
	 * @return 字符串日期时间
	 */
	public static String getDateTimeStr()
	{
		return dateTimeDF.format(new Date());
	}
    /**
     * 不带下划线
     * 
     * @return 字符串日期时间
     */
    public static String getDateTimeStrNo()
    {
        return dateTimeDF1.format(new Date());
    }

	/**
	 * 获取系统当前时间
	 * 
	 * @return the system time
	 */
	public static String getTimeStr()
	{
		return timeDF.format(new Date());
	}

	/**
	 * 获取系统当前月份
	 * 
	 * @return the system month
	 */
	public static int getYear()
	{
		return Calendar.getInstance().get(1);
	}

	/**
	 * 获取系统当前月份
	 * 
	 * @return the system month
	 */
	public static int getMonth()
	{
		return Calendar.getInstance().get(2) + 1;
	}

	/**
	 * 获取系统当前月份
	 * 
	 * @return the system month
	 */
	public static int getDay()
	{
		return Calendar.getInstance().get(5);
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year)
	{
		return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
	}

	/**
	 * 取日期字符串的中的月
	 * 
	 * @param date
	 * @return
	 */
	public static int month(String date)
	{
		return month(date, "-");
	}

	/**
	 * 取日期字符串的中的月
	 * 
	 * @param date
	 * @param delimer
	 * @return
	 */
	public static int month(String date, String delimer)
	{
		return Integer.parseInt(divString(date, delimer)[1]);
	}

	/**
	 * 取日期字符串的中的年
	 * 
	 * @param date
	 * @return
	 */
	public static int year(String date)
	{
		return year(date, "-");
	}

	/**
	 * 取日期字符串的中的年
	 * 
	 * @param date
	 * @param delimer
	 * @return
	 */
	public static int year(String date, String delimer)
	{
		return Integer.parseInt(divString(date, delimer)[0]);
	}

	private DateUtil()
	{

	}

	/**
	 * 将 YYYY-MM-DD 格式转为 YYYY-MM-DD HH:MM:SS
	 * 
	 * @param date
	 * @return
	 */
	public static String toDateTime(String date)
	{
		return formatDateStr(date, "YYYY-MM-DD HH:MM:SS");
	}

	// 两日期相减,返回天数
	public static long getDaylong(String begin, String end) throws Exception
	{
		Date dbegin;
		Date dend;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			dbegin = df.parse(begin);
			dend = df.parse(end);
		}
		catch (Exception p)
		{
			throw new Exception("传入日期格式错误，格式必需为yyyy-mm-dd");
		}
		return (dend.getTime() - dbegin.getTime()) / 86400000;
	}

	// 把字符转换成日期型数据
	public static Date toDate(String strDate) throws Exception
	{
		Date date;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			date = df.parse(strDate);
		}
		catch (Exception p)
		{
			throw new Exception("传入日期格式错误，格式必需为yyyy-mm-dd");
		}
		return date;
	}

	/**
	 * 两个时间相减得到的分钟数
	 * 
	 * @param dateStr
	 * @return
	 */
	public static long getCompareTime(String dateStr)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = 0;
		try
		{
			Date date = df.parse(dateStr);
			Date date1 = new Date();

			l = (date1.getTime() - date.getTime()) / 60000;
			return l;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 将四位的日期转换为1位的   8707  -> 2008-07-07  
	 * 传入的日期格式为：第一位为年，超过9用字母替代（10：A  11：B），第二位为月，超过9也用字母替代，三四位为日期 
	 * @param date
	 * @return
	 */
	public static String getTransferDate(String value)
	{
		String year = "20" + transferChar2Num(value.substring(0,1));
		String month = transferChar2Num(value.substring(1,2));
		String date = value.substring(2,4);
		return year + "-" + month + "-" + date;
	}
	
	private static String transferChar2Num(String value)
	{
		if(Pattern.matches("\\d", value))
		{
			return "0".concat(value);
		}
		else
		{
			int code = value.toCharArray()[0];
			return (code - 55) + "";
		}
	}
}
