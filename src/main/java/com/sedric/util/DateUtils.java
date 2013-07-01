package com.sedric.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	/**
	 * 默认日期格式
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到天的日期格式
	 */
	public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 默认构造函数
	 */
	private DateUtils() {}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @throws java.text.ParseException
	 */
	public static Date str2Date(String str, String format) {
		if (null == str || "".equals(str)) {
			return null;
		}
		// 如果没有指定字符串转换的格式，则用默认格式进行转换
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		}
		catch (ParseException e) {
			logger.error(e.toString());
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timestamp2Str(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(date, DEFAULT_FORMAT);
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, DEFAULT_FORMAT);
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取两个时间相差的天数，不足一天补一天。
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getCeilDays(Date startDate, Date endDate) {
		if (null == startDate || null == endDate) {
			return 0;
		}
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		return (int) Math.ceil((endTime - startTime) / (1000f * 60 * 60 * 24));
	}

	/**
	 * 获取两个时间相差的分钟数，不足一分钟补一分钟。
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer getCeilMinutes(Date startDate, Date endDate) {
		if (null == startDate || null == endDate) {
			return null;
		}
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		return (int) Math.ceil((endTime - startTime) / (1000f * 60));
	}

	/**
	 * 根据日期和偏移数计算新的日期。<br/>
	 * days > 0,日期加；days < 0,日期减。
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDateOfDay(Date date, int days) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}

	/**
	 * 根据日期、偏移字段、偏移数计算新的日期。<br/>
	 * days > 0,日期加；days < 0,日期减。
	 * 
	 * @param date
	 * @param field
	 *            如：Calendar.DAY_OF_YEAR
	 * @param amount
	 * @return
	 */
	public static Date getDateOfTime(Date date, int field, int amount) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	/**
	 * 返回一个日期当天最晚的时间，精确至23:59:59。<br/>
	 * 如2012-11-07 10:23 返回 2012-11-07 23:59:59。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOfDate(Date date) {
		return str2Date(date2Str(date, SHORT_DATE_FORMAT) + " 23:59:59", NORMAL_DATE_FORMAT);
	}

	/**
	 * 返回一个日期 00:00:00 <br/>
	 * 如2012-11-07 10:23 返回 2012-11-07 00:00:00。
	 * 
	 * @param date
	 * @return
	 */
	public static Date get0TimeOfDate(Date date) {
		return str2Date(date2Str(date, SHORT_DATE_FORMAT) + " 00:00:00", NORMAL_DATE_FORMAT);
	}

	/**
	 * 返回一个日期 24:00:00 <br/>
	 * 如2012-11-07 10:23 返回 2012-11-07 24:00:00。
	 * 
	 * @param date
	 * @return
	 */
	public static Date get24TimeOfDate(Date date) {
		return str2Date(date2Str(date, SHORT_DATE_FORMAT) + " 24:00:00", NORMAL_DATE_FORMAT);
	}

	/**
	 * 获取精确到天的日期<br/>
	 * 如2012-11-07 10:23 返回2012-11-07
	 * 
	 * @param date
	 * @return
	 */
	public static Date truncDate(Date date) {
		if (null == date) {
			return null;
		}
		return str2Date(date2Str(date, SHORT_DATE_FORMAT), SHORT_DATE_FORMAT);
	}

	/**
	 * 获取当前日期下一天精确到天的日期<br/>
	 * 如2012-11-07 10:23 返回2012-11-08
	 * 
	 * @param date
	 * @return
	 */
	public static Date ceilDate(Date date) {
		if (null == date) {
			return null;
		}
		return getDateOfDay(truncDate(date), 1);
	}

	public static int converSecondsToDays(int seconds) {
		if (seconds <= 0) {
			return 0;
		}

		return seconds / 60 / 60 / 24;
	}
}