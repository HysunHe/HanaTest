/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.http;

import java.io.Serializable;

/***************************************************************************
 *<PRE>
 *  Project Name    : PpmSyncMgr
 * 
 *  Package Name    : com.oracle.sccpoc.doosan.utils.http
 * 
 *  File Name       : HttpUtil.java
 * 
 *  Creation Date   : 2016-11-03
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/
public class ServiceCallResult implements Serializable {
	private static final long serialVersionUID = -407190612131005334L;

	private int statusCode;
	private String reasonPhase;
	private String responseString;

	public ServiceCallResult() {
	}

	public ServiceCallResult(int statusCode, String reasonPhase,
			String responseString) {
		this.statusCode = statusCode;
		this.reasonPhase = reasonPhase;
		this.responseString = responseString;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the reasonPhase
	 */
	public String getReasonPhase() {
		return reasonPhase;
	}

	/**
	 * @param reasonPhase
	 *            the reasonPhase to set
	 */
	public void setReasonPhase(String reasonPhase) {
		this.reasonPhase = reasonPhase;
	}

	/**
	 * @return the responseString
	 */
	public String getResponseString() {
		return responseString;
	}

	/**
	 * @param responseString
	 *            the responseString to set
	 */
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceCallResult [statusCode=" + statusCode + ", reasonPhase="
				+ reasonPhase + ", responseString=" + responseString + "]";
	}

}
