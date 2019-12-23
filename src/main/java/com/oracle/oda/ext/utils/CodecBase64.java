/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/***************************************************************************
 * <PRE>
 *  Project Name    : PpmSyncMgr
 * 
 *  Package Name    : com.oracle.sccpoc.doosan.utils
 * 
 *  File Name       : CodecBase64.java
 * 
 *  Creation Date   : 2016-11-04
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public final class CodecBase64 {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodecBase64.class);

	private CodecBase64() {
	}

	/**
	 * @param input
	 * @return
	 */
	public static final String encode(String input) {
		try {
			return Base64.encode(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn("UnsupportedEncodingException: UTF-8");
			return Base64.encode(input.getBytes());
		}
	}

	/**
	 * @param input
	 * @return
	 */
	public static final String decode(String input) {
		byte[] bytes = Base64.decode(input);
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn("UnsupportedEncodingException: UTF-8");
			return new String(bytes);
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		final String str = "hysunhe贺友胜test";
		System.out.println(" Original string: " + str);

		String encodedString = encode(str);
		System.out.println("\n Encoded string: " + encodedString);

		System.out.println("\n Decoded string: " + decode(encodedString));
	}
}
