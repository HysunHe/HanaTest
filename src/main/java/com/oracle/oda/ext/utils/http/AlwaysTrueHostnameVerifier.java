/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

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
public class AlwaysTrueHostnameVerifier implements HostnameVerifier {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
	 * javax.net.ssl.SSLSession)
	 */
	public boolean verify(String hostname, SSLSession session) {
		// Ignore host name check for SSL connection
		return true;
	}
}
