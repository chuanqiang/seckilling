package com.zcq.seckilling.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Description:(时间操作工具)
 * @author wang.zx
 * @date 2014-12-20 下午10:51:36
 * @version 1.0
 */
public class DateUtil {

	public static final String VIEW_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
	public static final String LONG_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}

	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String format1(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static int getWeek(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = Calendar.DAY_OF_WEEK;
		}
		return dayOfWeek;
	}

	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String getDate(java.util.Date date, String formatStr) {
		return format(date, formatStr);
	}

	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期相加
	 *
	 * @param date
	 *            Date
	 * @param day
	 *            int
	 * @return Date
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 *
	 * Class Name: DateUtil.java
	 * @Description: 加 n秒
	 * @author 周文斌
	 * @date 2017-2-23 下午6:07:55
	 * @modify	2017-2-23 下午6:07:55
	 * @version
	 * @param date
	 * @param second
	 * @return
	 */
	public static java.util.Date addSecond(java.util.Date date,int second){
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}
	/**
	 *
	 * Class Name: DateUtil.java
	 * @Description: 获取指定日期加几分钟后的具体日期
	 * @author yuchanglong
	 * @date 2016年2月26日 下午3:23:50
	 * @version 1.0
	 * @param date
	 * @param minute
	 * @return
	 */
	public static java.util.Date addDateMinute(java.util.Date date, int minute) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) minute) * 60 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 *
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减(返回秒值)
	 *
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 * @author
	 */
	public static Long diffDateTime(java.util.Date date, java.util.Date date1) {
		return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
	}

	public static java.util.Date getdate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static java.util.Date getdate2(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static java.util.Date getdate1(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static java.util.Date getMaxTimeByStringDate(String date) {
		try {
			String maxTime = date + " 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(maxTime);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得当前时间
	 *
	 * @return
	 */
	public static Date getCurrentDateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = DateUtil.getDateTime(date);
		try {
			return sdf.parse(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String getCurrentDateTimeToStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(getCurrentDateTime());
	}

	public static Long getWmsupdateDateTime() {
		Calendar cl = Calendar.getInstance();

		return cl.getTimeInMillis();
	}

	/** log日志对象 */
	protected final static Log log = LogFactory.getLog("log");

	/**
	 * 私有构造器防止new
	 */
	private DateUtil() {

	}

	/**
	 * 得到当前年月,如2009-02
	 *
	 * @return String
	 */
	public static String getCurYearMonth() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到当前年,如2009
	 *
	 * @return String
	 */
	public static int getCurYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 得到当前年,如2009
	 *
	 * @return String
	 */
	public static int getCurDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到当月份,如9
	 *
	 * @return String
	 */
	public static int getCurMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前月的第一天，yyyy-MM-dd格式
	 *
	 * @return String
	 */
	public static String getFirstDayOfCurMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到当前月的最后一天，yyyy-MM-dd格式
	 *
	 * @return String
	 */
	public static String getLastDayOfCurMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到上个月的第一天，yyyy-MM-dd格式
	 *
	 * @return String
	 */
	public static String getFirstDayOfLastMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 去年同期这个月的最后一天，yyyy-MM-dd格式
	 *
	 * @return String
	 */
	public static String getTongQiLastDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 去年同期这个月的第一天，yyyy-MM-dd格式
	 *
	 * @return String
	 */
	public static String getTongQiFirstDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到上个月的最后一天，yyyy-MM-dd格式
	 *
	 * @return String
	 */
	public static String getLastDayOfLastMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到当前年月日
	 *
	 * @return String
	 */
	public static String getCurrentDay(String pattern) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat(pattern);
		return simpleFormate.format(new Date());
	}

	/**
	 * 获得当前时间
	 *
	 * @param pattern
	 * @return
	 */
	public static Date getCurrentDate(String pattern) {
		Date date = new Date();
		String format = "yyyy-MM-dd";
		if (StringUtils.isNotBlank(pattern)) {
			format = pattern;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String result = DateUtil.getDateTime(date);
		try {
			return sdf.parse(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取昨天日期
	 *
	 * @param pattern
	 * @return
	 */
	public static String getYesterDay(String pattern) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_YEAR, -1);
		return simpleFormate.format(ca.getTime());
	}
	/**
	 * 获取明天日期
	 *
	 * @param pattern
	 * @return
	 */
	public static String getTomorrowDay(String pattern) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_YEAR, 1);
		return simpleFormate.format(ca.getTime());
	}
	/**
	 * 得到当前月,如02,12
	 *
	 * @return String
	 */
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		return month;
	}

	/**
	 * 格式化日期为指定格式
	 *
	 * @param date
	 *            Date 需要格式化的日期
	 * @param s
	 *            String 目标格式字符串
	 * @return String 格式化的日期
	 */
	public static String formatDateTime(Date date, String s) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
		try {
			return simpledateformat.format(date);
		} catch (Exception ex) {
			log.error("格式化日期为指定格式时发生异常", ex);
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析日期时间字符串，得到Date对象
	 *
	 * @param input
	 *            日期时间字符串
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date parseDate(String input, String pattern) {
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
			return simpledateformat.parse(input);
		} catch (ParseException ex) {
			log.error("解析日期时间字符串时发生异常", ex);
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Timestamp 转换成 String类型
	 *
	 * @param timestamp
	 * @param pattern
	 * @return
	 */
	public static String timestampToString(Timestamp timestamp, String pattern) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
		return simpledateformat.format(timestamp);
	}

	/**
	 * String 转换成 Timestamp类型
	 *
	 * @param dateTime
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Timestamp stringToTimestamp(String dateTime, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			log.error("String 转换成 Timestamp类型出现异常", e);
			e.printStackTrace();
		}
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}

	/**
	 * 获取某天的前后几天的日期
	 *
	 * @param dateTime
	 * @return
	 */
	public static String getDayByDateTime(String dateTime, int next) {
		String result = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date ldate = df.parse(dateTime);
			result = df.format(
					new Date(ldate.getTime() + next * 24 * 60 * 60 * 1000))
					.toString();
		} catch (ParseException e) {
			log.error("获取某天的前一天时发生异常", e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取某天的同期日期
	 *
	 * @param dateTime
	 * @return
	 */
	public static String getTongQiDayByDateTime(String dateTime) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleFormate.parse(dateTime));
			ca.add(Calendar.YEAR, -1);
		} catch (ParseException e) {
			log.error("获取某天的前一天时发生异常", e);
			e.printStackTrace();
		}
		return simpleFormate.format(ca.getTime());
	}

	/**
	 * 获取今天开始时间
	 *
	 * @return
	 */
	public static String getToDayStartTime() {
		return getCurrentDay("yyyy-MM-dd") + " 00:00:00";
	}

	/**
	 * 获取今天结束时间
	 *
	 * @return
	 */
	public static String getToDayEndTime() {
		return getCurrentDay("yyyy-MM-dd") + " 23:59:59";
	}

	/**
	 * 获取昨天开始时间
	 *
	 * @return
	 */
	public static String getYesterDayStartTime() {
		return getYesterDay("yyyy-MM-dd") + " 00:00:00";
	}

	/**
	 * 获取昨天结束时间
	 *
	 * @return
	 */
	public static String getYesterDayEndTime() {
		return getYesterDay("yyyy-MM-dd") + " 23:59:59";
	}

	/**
	 * 获取某月有多少天
	 *
	 * @param month
	 *            月份
	 * @return
	 */
	public static int getDayCountByMonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 根据指定时间获取年份
	 *
	 * @return 如:2010
	 */
	public static int getYearByDT(String dateTime, String pattern) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleFormate.parse(dateTime));
		} catch (ParseException e) {
			log.error("根据指定时间获取年份时发生异常", e);
			e.printStackTrace();
		}
		return ca.get(Calendar.YEAR);
	}

	/**
	 * 根据指定时间获取月份
	 *
	 * @return 如:05
	 */
	public static int getMonthByDT(String dateTime, String pattern) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleFormate.parse(dateTime));
		} catch (ParseException e) {
			log.error("根据指定时间获取月份时发生异常", e);
			e.printStackTrace();
		}
		return ca.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据指定时间获取天数
	 *
	 * @return 如:05
	 */
	public static int getDayByDT(String dateTime, String pattern) {
		SimpleDateFormat simpleFormate = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleFormate.parse(dateTime));
		} catch (ParseException e) {
			log.error("根据指定时间获取月份时发生异常", e);
			e.printStackTrace();
		}
		return ca.get(Calendar.DATE);
	}

	/**
	 * 根据时间获取上一个月
	 *
	 * @param dataTime
	 * @return
	 */
	public static String getLastMonthByDT(String dateTime, String pattern) {
		String strYear = String.valueOf(getYearByDT(dateTime, pattern));
		String strMonth = changeLen(String.valueOf(getMonthByDT(dateTime,
				pattern) - 1));
		String strDay = changeLen(String.valueOf(getDayByDT(dateTime, pattern)));
		return strYear + "-" + strMonth + "-" + strDay;
	}

	/**
	 * 根据时间获取去年同一月
	 *
	 * @param dataTime
	 * @return
	 */
	public static String getLastYearByDT(String dateTime, String pattern) {
		String strYear = String.valueOf(getYearByDT(dateTime, pattern) - 1);
		String strMonth = changeLen(String.valueOf(getMonthByDT(dateTime,
				pattern)));
		String strDay = changeLen(String.valueOf(getDayByDT(dateTime, pattern)));
		return strYear + "-" + strMonth + "-" + strDay;
	}

	/**
	 * 修改1-9数字位数,如1转换成01
	 *
	 * @param value
	 * @return
	 */
	public static String changeLen(String value) {
		if (value.length() == 1) {
			value = "0" + value;
		}
		return value;
	}

	/**
	 * 得到当前年月,如200902
	 *
	 * @return String
	 */
	public static String getCurYearMonthYYYYMM() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyyMM");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到当前年的上一月,如当前月为200902，则返回200901
	 *
	 * @return String
	 */
	public static String getLastYearMonthYYYYMM() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyyMM");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 得到当前年的上一年,如当前年为2009，则返回2008
	 *
	 * @return String
	 */
	public static String getLastYearYYYY(String year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy");
		return simpleFormate.format(calendar.getTime()).trim();
	}

	/**
	 * 获得给定日期的月份的最后一天
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期月份的最后一天
	 */
	public static int getLastDayOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 给指定日期加几天
	 *
	 * @param date
	 *            指定的日期
	 * @param numDays
	 *            需要往后加的天数
	 * @return 加好后的日期
	 */
	public static Date addDaysToDate(Date date, int numDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numDays);
		return c.getTime();
	}

	/**
	 * 给指定日期加几个月
	 *
	 * @param date
	 *            指定的日期
	 * @param numMonths
	 *            需要往后加的月数
	 * @return 加好后的日期
	 */
	public static Date addMonthsToDate(Date date, int numMonths) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, numMonths);
		return c.getTime();
	}

	/**
	 * 将一个指定的日期格式化成指定的格式
	 *
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 格式化好后的日期字符串
	 */
	public static String formatDate(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * @Description:(根据当前日期获取星期几)
	 * @author wang.zx
	 * @date 2015-1-4 下午7:03:55
	 * @version 1.0
	 * @param date
	 * @return
	 */
	public static int getWeekOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w == 0) {
			w = Calendar.DAY_OF_WEEK;
		} else if (w < 0) {
			w = 0;
		}
		return w;
	}

	/**
	 * @Description:根据当前获取推迟或者向前第几周的周一
	 * @author wang.zx
	 * @date 2015-1-29 下午11:23:40
	 * @version 1.0
	 * @param num
	 * @return
	 */
	public static Date getWeekOfDayByCurentDay(int num, int weekDay, Date date) {
		//处理星期的异常情况
		if(weekDay < 1 || weekDay > 7){
			weekDay = 2;
		}else{
			//由于西方的周日是一周的第一天，故在此做特殊处理
			weekDay++;
			if(weekDay == 8){
				weekDay = 1;
			}
		}
		Calendar cal = Calendar.getInstance();
		//设置日期为传递过来的日期,不设置则为当前时间
		cal.setTime(date);
		// n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		cal.add(Calendar.DATE, num * Calendar.DAY_OF_WEEK);
		// 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, weekDay);
		return cal.getTime();
	}

	/**
	 * firstWeekDay： 表示当前日期(也就是开班日期) nextWeekDay：表示开班类型中的第一个星期几，假如数组为{ 2,4,5,6
	 * }，则改值为2
	 *
	 * @param firstWeekDay
	 * @param nextWeekDay
	 * @return
	 */
	public static int getDaysBetweenTwoWeekDay(int firstWeekDay, int nextWeekDay) {
		int timeDiff = 0;
		if (firstWeekDay > 0 && nextWeekDay > 0) {
			// 假如a < b, 则需要找本周的某一个日期
			if (firstWeekDay < nextWeekDay) {
				timeDiff = nextWeekDay - firstWeekDay;
			} else if (firstWeekDay > nextWeekDay) { // 如果 a > b,则需要找下一周的某个日期
				timeDiff = nextWeekDay + Calendar.SATURDAY - firstWeekDay;
			} else {
				timeDiff = Calendar.SATURDAY;
			}
		}
		return timeDiff;
	}

	public static int dayOfWeek(Date day){
		Calendar cd = Calendar.getInstance();
		cd.setTime(day);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
	}

	public static Date formatDateToStartDateTime(String strDate) {
		java.util.Date date = null;
		try {
			date = convertStringToDate(LONG_DATE_TIME_PATTERN, strDate
					+ " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date formatDateToEndDateTime(String strDate) {
		java.util.Date date = null;
		try {
			date = convertStringToDate(LONG_DATE_TIME_PATTERN, strDate
					+ " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 *
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * 日期转为数字
	 */
	public static String convertDateToString(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 *
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return "yyyy-MM-dd";
	}

	public static int getMaxDaybyYearAndMonth(int year, int month) {
		int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (2 == month && 0 == (year % 4)
				&& (0 != (year % 100) || 0 == (year % 400))) {
			days[1] = 29;
		}
		return (days[month - 1]);
	}

	/**
	 * 获得显示格式时间 到分钟
	 * 
	 * @param date
	 * @author yan.b
	 * @return
	 */
	public static String viewformat(Date date) {
		return convertDateToString(VIEW_DATE_TIME_PATTERN, date);
	}

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param start
	 * @param end
	 * @author yan.b
	 * @return
	 */
	public static long calculateDaysBetween(Date start, Date end) {
		Calendar aCalendar = Calendar.getInstance();
		// 里面可以直接插入date类型
		aCalendar.setTime(start);
		aCalendar.set(Calendar.HOUR_OF_DAY, 0);
		aCalendar.set(Calendar.MINUTE, 0);
		aCalendar.set(Calendar.SECOND, 0);
		aCalendar.set(Calendar.MILLISECOND, 0);
		long time1 = aCalendar.getTimeInMillis();
		
		aCalendar.setTime(end);
		aCalendar.set(Calendar.HOUR_OF_DAY, 0);
		aCalendar.set(Calendar.MINUTE, 0);
		aCalendar.set(Calendar.SECOND, 0);
		aCalendar.set(Calendar.MILLISECOND, 0);
		
		long time2 = aCalendar.getTimeInMillis();
		
		// 求出两日期相隔天数
		return (time2-time1)/(1000*60*60*24);
	}

	public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        if (time <= 0) {
			return "00:00:00";
		} else {
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                if (hour > 99) {
					return "99:59:59";
				}
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
			retStr = "0" + Integer.toString(i);
		} else {
			retStr = "" + i;
		}
        return retStr;  
    } 
    /**
     * @Description: 解析课程开始、结束时间
     * @author wzx
     * @date 2015-5-25 下午5:23:42
     * @version 1.0
     * @param date
     * @param time
     * @return
     */
    public static long parseDateTime(String date, String time){
		long long0 = 0L;
		String maxTime = date + " "+time +":00:000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		try {
			Date day = sdf.parse(maxTime);
			return day.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return long0;
	}

	/**
	 * 计算两个时间相差多少分钟
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static long dateFormatMinute(Date beginTime, Date endTime){
		long between=(endTime.getTime()-beginTime.getTime())/1000;//除以1000是为了转换成秒
		long min=between/60;
		return min;
	}
}
