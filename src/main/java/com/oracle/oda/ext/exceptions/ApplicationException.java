/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.exceptions;

/***************************************************************************
 *<PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.exceptions
 * 
 *  File Name       : ApplicationException.java
 * 
 *  Creation Date   : 2019年2月19日
 * 
 *  Author          : hysun
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 3080773205666453343L;

	/**
	 * 
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * @param msg
	 */
	public ApplicationException(String msg) {
		super(msg);
	}

	/**
	 * @param t
	 */
	public ApplicationException(Throwable t) {
		super(t);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public ApplicationException(String msg, Throwable t) {
		super(msg, t);
	}
}