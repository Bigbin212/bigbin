package com.bigbincome.bigbin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS_SHORT="yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YYYYMMDD="yyyy-MM-dd";
	public static final int defaultTimezoneOffset=TimeZone.getDefault().getRawOffset()/(1000*60*60);
	
	/**
	 * 转换日期成指定格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate2String(Date date,String format) {
		String dateTimeStr="";
		if(date!=null) {
			if(StringUtil.isNullOrEmpty(format)){
				format = DATE_FORMAT_YYYYMMDDHHMMSS;
			}
			SimpleDateFormat sf=new SimpleDateFormat(format);
			dateTimeStr = sf.format(date);
		}
		return dateTimeStr;
	}
	
	/**
	 * 转换指定格式的字符串成日期
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date formatString2Date(String dateStr,String format) {
		Date date=null;
		if(dateStr!=null && !dateStr.isEmpty()) {
			try {
				SimpleDateFormat sf=new SimpleDateFormat(format);
				return sf.parse(dateStr);
			}catch(Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return date;
	}
	/**
	 * 调整日期时间
	 * @param date		当前时间
	 * @param field		调整域(年月日时分秒)
	 * @param amount	调整量(正值向后调,负值向前调)
	 * @return
	 */
	public static Date adjustDateTime(Date date,int field, int amount) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return c.getTime();
	}


	/**
	 * 调整日期
	 * @param date		当前时间
	 * @param amount	日期调整量(正值向后调,负值向前调)
	 * @return
	 */
	public static Date adjustDay(Date date,int amount) {
		return adjustDateTime(date,Calendar.DAY_OF_YEAR,amount);
	}

	/**
	 * 转换指定格式的字符串成日期(抛出异常)
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date formatStringToDateThrowE(String dateStr,String format) throws ParseException {
		Date date=null;
		if(dateStr!=null && !dateStr.isEmpty()) {
			SimpleDateFormat sf=new SimpleDateFormat(format);
			return sf.parse(dateStr);
		}
		return date;
	}
	/**
	 * 调整日期时间(返回字符串格式)
	 * @param date		当前时间
	 * @param field		调整域(年月日时分秒)
	 * @param amount	调整量(正值向后调,负值向前调)
	 * @param format    格式化方式
	 * @return
	 */
	public static String adjustDateTimeToString(Date date,int field, int amount,String format) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		Date date1=c.getTime();
		SimpleDateFormat sf=new SimpleDateFormat(format);
		return sf.format(date1);
	}
	/**
	 * 调整日期时间(返回字符串格式)(抛出异常)
	 * @param dateStr		当前时间（字符串）
	 * @param field		调整域(年月日时分秒)
	 * @param amount	调整量(正值向后调,负值向前调)
	 * @param format    格式化方式
	 * @return
	 */
	public static String adjustDateTimeStrToStr(String dateStr,int field, int amount,String format) throws ParseException {
		SimpleDateFormat sf=new SimpleDateFormat(format);
		Calendar c=Calendar.getInstance();
		c.setTime(sf.parse(dateStr));
		c.add(field, amount);
		Date date1=c.getTime();
		return sf.format(date1);
	}
	/**
	 * 根据时区转换成本地时间字符串,需指定时间格式
	 * @param time
	 * @param timezone
	 * @param dateFormt
	 * @return
	 */
	public static String convert2LocalTimeStr(String time, String timezone, String dateFormt) {
		Date date=formatString2Date(time, dateFormt);
		return convert2LocalTimeStr(date,timezone,dateFormt);
	}
	/**
	 * 根据时区转换成本地时间字符串, 默认格式:yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @param timezone
	 * @return
	 */
	public static String convert2LocalTimeStr(String time, String timezone) {
		return convert2LocalTimeStr(time,timezone,DATE_FORMAT_YYYYMMDDHHMMSS);
	}
	/**
	 * 把时间对象转换成本地时间字符串,需指定时间格式
	 * @param date
	 * @param timezone
	 * @param dateFormt
	 * @return
	 */
	public static String convert2LocalTimeStr(Date date, String timezone, String dateFormt) {
		return getSimpleDateFormatWithTimezone(timezone,dateFormt).format(date);
	}
	/**
	 * 把时间对象转换成本地时间字符串, 默认格式:yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param timezone
	 * @return
	 */
	public static String convert2LocalTimeStr(Date date, String timezone) {
		return convert2LocalTimeStr(date,timezone,DATE_FORMAT_YYYYMMDDHHMMSS);
	}
	/**
	 * 得到服务器的时区偏移小时
	 * @return
	 */
	public static int getDefaultTimeZoneOffset() {
		return defaultTimezoneOffset;
	}
	/**
	 * 把本地时间字符串转换成时间对象,需指定时间格式
	 * @param timeStr
	 * @param timezone
	 * @param dateFormt
	 * @return
	 * @throws Exception
	 */
	public static Date convert2LocalTime(String timeStr, String timezone, String dateFormt) throws Exception{
		return getSimpleDateFormatWithTimezone(timezone,dateFormt).parse(timeStr);
	}
	/**
	 * 把本地时间字符串转换成时间对象, 默认格式:yyyy-MM-dd HH:mm:ss
	 * @param timeStr
	 * @param timezone
	 * @return
	 * @throws Exception
	 */
	public static Date convert2LocalTime(String timeStr, String timezone) throws Exception{
		return convert2LocalTime(timeStr, timezone,DATE_FORMAT_YYYYMMDDHHMMSS);
	}
	/**
	 * 得到带时区的时间格式转换器
	 * @param timeZone	时区
	 * @param dateFormt	样式
	 * @return
	 */
	private static SimpleDateFormat getSimpleDateFormatWithTimezone(String timeZone, String dateFormt) {
		SimpleDateFormat sf=new SimpleDateFormat(dateFormt);
		try {
			int offset=Integer.parseInt(timeZone);//如果前端传过来的是整型时区
			logger.debug("Orig timezone :" + timeZone);
			int offsetTime=offset*60*60*1000;
			String[] ids = TimeZone.getAvailableIDs(offsetTime);
			if (ids.length > 0) {
				timeZone=ids[0];
			}else {
				timeZone=TimeZone.getDefault().getID();
			}
		}catch(Exception e) {			
		}
		sf.setTimeZone(TimeZone.getTimeZone(timeZone));
		logger.debug("Timezone :" + timeZone);
		return sf;
	}
}
