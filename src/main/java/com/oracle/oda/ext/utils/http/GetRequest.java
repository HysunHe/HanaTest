/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.http;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
public class GetRequest implements Serializable {
	private static final long serialVersionUID = -5476427419473997242L;

	private String url;
	private Map<String, String> headers;
	private Map<String, String> params;

	public GetRequest() {
	}

	public GetRequest(String url, Map<String, String> headers,
			Map<String, String> params) {
		this.url = url;
		this.headers = headers;
		this.params = params;
	}

	public static GetRequest newInstance() {
		return new GetRequest();
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public GetRequest setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public GetRequest setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public GetRequest setParams(Map<String, String> params) {
		this.params = params;
		return this;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public GetRequest setParam(String key, String value) {
		if (this.params == null) {
			this.params = new HashMap<String, String>();
		}
		this.params.put(key, value);
		return this;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public GetRequest setHeader(String key, String value) {
		if (this.headers == null) {
			this.headers = new HashMap<String, String>();
		}
		this.headers.put(key, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetRequest [url=" + url + ", headers=" + headers + ", params="
				+ params + "]";
	}

}
