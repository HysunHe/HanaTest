/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.http;

import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************************************************
 * <PRE>
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
 * </PRE>
 ***************************************************************************/
public class ServiceCallClient extends DefaultHttpClient {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceCallClient.class);

	public static final int REQUEST_TIMEOUT = 15 * 60 * 1000;
	public static final int WAIT_TIMEOUT = 15 * 60 * 1000;
	public static final int BUFFER_SIZE = 8192;

	private volatile static int HTTP_PORT = 80;
	private volatile static int HTTPS_PORT = 443;

	public ServiceCallClient() {
		super();
		this.init();
	}

	/**
	 * @param httpPort
	 *            the httpPort to set
	 */
	public static void setHttpPort(int httpPort) {
		HTTP_PORT = httpPort;
	}

	/**
	 * @param httpsPort
	 *            the httpsPort to set
	 */
	public static void setHttpsPort(int httpsPort) {
		HTTPS_PORT = httpsPort;
	}

	/**
	 * Get the server IP address. The IP address is very important for service
	 * call.
	 */
	private void init() {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, WAIT_TIMEOUT);
		HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);
		this.setParams(httpParameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.http.impl.client.AbstractHttpClient#
	 * createClientConnectionManager ()
	 */
	protected ClientConnectionManager createClientConnectionManager() {
		Scheme httpScheme = new Scheme("http", HTTP_PORT,
				PlainSocketFactory.getSocketFactory());
		LOGGER.info("*** HTTP Scheme " + HTTP_PORT);

		SSLSocketFactory sslFactory = createSSLFactory();
		Scheme httpsScheme = new Scheme("https", HTTPS_PORT, sslFactory);
		LOGGER.info("*** HTTPS Scheme " + HTTPS_PORT);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(httpScheme);
		schemeRegistry.register(httpsScheme);

		return new BasicClientConnectionManager(schemeRegistry);
	}

	/**
	 * @return
	 */
	private final SSLSocketFactory createSSLFactory() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null,
					new TrustManager[] {
							InternalServiceCallTrustManager.getInstance() },
					new SecureRandom());
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot create SSL connection!",
					e);
		}

		SSLSocketFactory factory = new SSLSocketFactory(sslContext,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		return factory;
	}
}
