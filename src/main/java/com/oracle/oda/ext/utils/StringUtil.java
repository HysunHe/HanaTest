/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import java.util.UUID;
import java.util.regex.Pattern;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.utils
 * 
 *  File Name       : StringUtil.java
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
public final class StringUtil {
	private static final Pattern EMPTY_STRING_Pattern = Pattern.compile("\\s*");

	private StringUtil() {
	}

	/**
	 * To test whether a string is null or empty.
	 * 
	 * @param str : The string to be tested.
	 * @return: return true if str is null or is empty or is composed by
	 *          white-spaces.
	 */
	public static boolean isBlank(String str) {
		if (str == null)
			return true;
		else if (EMPTY_STRING_Pattern.matcher(str).matches())
			return true;
		else if ("null".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}

	/**
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		if (s == null)
			return null;
		return s.trim();
	}

	/**
	 * @param s
	 * @return
	 */
	public static String upper(String s) {
		if (s == null)
			return null;
		return s.toUpperCase();
	}

	/**
	 * @param s
	 * @return
	 */
	public static String trimAndUpper(String s) {
		s = trim(s);
		s = upper(s);
		return s;
	}

	/**
	 * If the str is an Integer return true else return false.
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isInt(String str) {
		boolean result = false;
		if (!isBlank(str)) {
			try {
				Integer.parseInt(str);
				result = true;
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * If the str is an Double return true else return false.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		boolean result = false;
		if (!isBlank(str)) {
			try {
				Double.parseDouble(str);
				result = true;
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * @return
	 */
	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "").toUpperCase();
	}
	
	public static void main(String[] args) {
		System.out.println(uuid());
	}
}
