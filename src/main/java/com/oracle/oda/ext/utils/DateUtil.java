/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.utils
 * 
 *  File Name       : DateUtil.java
 * 
 *  Creation Date   : 2019年2月18日
 * 
 *  Author          : hysun
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public final class DateUtil {

	/**
	 * Transform the specified dater to string, by specifying the format the
	 * returned value to be.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		if (date == null)
			throw new NullPointerException("date is null: " + format);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Transform the specified dateStr to Date, by specifying the date format.
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date string2Date(String dateStr, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date: " + dateStr + " | format: " + format + " | Exception: " + e);
		}
	}

	/**
	 * @return
	 */
	public static Date now() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * @return
	 */
	public static Timestamp nowTs() {
		Calendar calendar = Calendar.getInstance();
		long ts = calendar.getTimeInMillis();
		return new Timestamp(ts);
	}

	/**
	 * @param d
	 * @return
	 */
	public static Timestamp dateToTs(Date d) {
		return new Timestamp(d.getTime());
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date getTomorrow(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date getYesterday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	public static int getTimeDiffMins(Date d1, Date d2) {
		long mins = (d1.getTime() - d2.getTime()) / (1000 * 60);
		return Long.valueOf(mins).intValue();
	}
}
