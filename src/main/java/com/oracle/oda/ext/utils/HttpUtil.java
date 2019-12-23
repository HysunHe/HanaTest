/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.oracle.oda.ext.utils.http.GetRequest;
import com.oracle.oda.ext.utils.http.InputStreamFileObject;
import com.oracle.oda.ext.utils.http.ServiceCallClient;
import com.oracle.oda.ext.utils.http.ServiceCallResult;
import com.oracle.oda.ext.utils.http.SimpleFileObject;
import com.oracle.oda.ext.utils.soap.SoapConfigBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : PpmSyncMgr
 * 
 *  Package Name    : com.oracle.oda.ext.utils.http
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
public final class HttpUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpUtil.class);

	/**
	 * REST: Get operation
	 * 
	 * @param service
	 *            URL of the service to be called
	 */
	public static ServiceCallResult issueGetRequest(String service,
			Map<String, String> headers) throws IOException {
		HttpGet httpGet = new HttpGet(service);
		// Set header
		setHeader(httpGet, headers);

		return execute(httpGet);
	}

	/**
	 * REST: Get operation
	 * 
	 * @param GetRequest
	 *            URL of the service to be called
	 */
	public static ServiceCallResult issueGetRequest(GetRequest req)
			throws IOException {
		try {
			// Populate query string
			URIBuilder builder = new URIBuilder(req.getUrl());
			if (req.getParams() != null && !req.getParams().isEmpty()) {
				for (Map.Entry<String, String> pEntry : req.getParams()
						.entrySet()) {
					builder.addParameter(pEntry.getKey(), pEntry.getValue());
				}
			}

			// Construct Get request
			HttpGet httpGet = new HttpGet(builder.build());
			setHeader(httpGet, req.getHeaders());

			// Issue the get request to server and return result.
			return execute(httpGet);
		} catch (URISyntaxException e) {
			throw new IOException("URL syntax error: " + req.getUrl());
		}
	}

	/**
	 * REST: Post operation
	 * 
	 * @param service
	 * @param headers
	 * @param obj
	 * @return
	 */
	public static ServiceCallResult issuePostRequest(String service,
			Map<String, String> headers, Object data) throws IOException {
		if (data == null) {
			throw new NullPointerException("Nothing to post");
		}

		HttpPost httpPost = new HttpPost(service);
		// Set header
		setHeader(httpPost, headers);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonData = data instanceof String ? (String) data
					: mapper.writeValueAsString(data);
			httpPost.setEntity(
					new StringEntity(jsonData, ContentType.APPLICATION_JSON));
		} catch (JsonGenerationException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		return execute(httpPost);
	}

	/**
	 * REST: Post operation
	 * 
	 * @param service
	 * @param headers
	 * @param obj
	 * @return
	 */
	public static ServiceCallResult issueSoapRequest(String service,
			Map<String, String> headers, String envelope) throws IOException {
		if (envelope == null) {
			throw new NullPointerException("Nothing to post(Soap)");
		}

		HttpPost httpPost = new HttpPost(service);
		// Set header
		setHeader(httpPost, headers);

		httpPost.setEntity(new StringEntity(envelope,
				ContentType.create("text/xml", Consts.UTF_8)));

		return execute(httpPost);
	}

	/**
	 * REST: PUT operation
	 * 
	 * @param service
	 * @param headers
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static ServiceCallResult issuePutRequest(String service,
			Map<String, String> headers, Object data) throws IOException {
		if (data == null) {
			throw new NullPointerException("Nothing to put");
		}

		HttpPut httpPut = new HttpPut(service);
		// Set header
		setHeader(httpPut, headers);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonData = mapper.writeValueAsString(data);
			httpPut.setEntity(
					new StringEntity(jsonData, ContentType.APPLICATION_JSON));
		} catch (JsonGenerationException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		return execute(httpPut);
	}

	/**
	 * REST: PATCH operation
	 * 
	 * @param service
	 * @param headers
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static ServiceCallResult issuePatchRequest(String service,
			Map<String, String> headers, Object data) throws IOException {
		if (data == null) {
			throw new NullPointerException("Nothing to update");
		}

		HttpPatch httpPatch = new HttpPatch(service);
		// Set header
		setHeader(httpPatch, headers);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonData = mapper.writeValueAsString(data);
			httpPatch.setEntity(
					new StringEntity(jsonData, ContentType.APPLICATION_JSON));
		} catch (JsonGenerationException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		return execute(httpPatch);
	}

	/**
	 * REST: Delete Operation
	 * 
	 * @param service
	 * @param headers
	 * @return
	 * @throws IOException
	 */
	public static ServiceCallResult issueDeleteRequest(String service,
			Map<String, String> headers) throws IOException {
		HttpDelete httpDelete = new HttpDelete(service);

		// Set header
		setHeader(httpDelete, headers);

		return execute(httpDelete);
	}

	/**
	 * Execute an HTTP action (Get or POst ...)
	 * 
	 * @param action
	 * @return
	 * @throws IOException
	 */
	private static ServiceCallResult execute(HttpRequestBase action)
			throws IOException {
		ServiceCallClient client = new ServiceCallClient();
		ServiceCallResult result = new ServiceCallResult();
		try {
			HttpResponse resp = client.execute(action);
			StatusLine status = resp.getStatusLine();
			if (status != null) {
				result.setStatusCode(status.getStatusCode());
				result.setReasonPhase(status.getReasonPhrase());
			}
			String respString = getResponseString(resp.getEntity(),
					getEncodingFromResponse(resp));
			result.setResponseString(respString);

			return result;
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			try {
				action.releaseConnection();
				action.abort();
			} catch (Exception e) {
				LOGGER.warn("Execute action failed: " + action.toString(), e);
			}
			client = null;
			action = null;
		}
	}

	/**
	 * @param action
	 * @param headers
	 */
	private static void setHeader(HttpRequestBase action,
			Map<String, String> headers) {
		// Http headers
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> pEntry : headers.entrySet()) {
				Header header = new BasicHeader(pEntry.getKey(),
						String.valueOf(pEntry.getValue()));
				action.setHeader(header);
			}
		}
	}

	/**
	 * @param resp
	 * @return
	 */
	public static String getEncodingFromResponse(HttpResponse resp) {
		if (resp.getEntity() == null) {
			return null;
		}

		Header contentTypeHeader = resp.getEntity().getContentType();
		String encoding = null;
		if (contentTypeHeader != null) {
			String contentType = contentTypeHeader.getValue();
			encoding = (contentType == null
					|| contentType.indexOf("charset=") < 0) ? null
							: contentType.substring(
									contentType.indexOf("charset=") + 8);
		}
		return encoding;
	}

	/**
	 * @param resp
	 * @return
	 */
	public static String getResponseString(HttpEntity resp, String encoding) {
		if (resp == null) {
			return null;
		}

		BufferedReader in = null;
		final StringBuilder stringBuffer = new StringBuilder(255);
		if (encoding == null || encoding.trim().isEmpty()) {
			encoding = "UTF-8";
		}
		try {
			in = new BufferedReader(
					new InputStreamReader(resp.getContent(), encoding));
			String line = null;
			boolean isFirstLine = true;
			while ((line = in.readLine()) != null) {
				if (!isFirstLine) {
					stringBuffer.append("\n");
				} else {
					isFirstLine = false;
				}
				stringBuffer.append(line);
			}
			return stringBuffer.toString();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.warn("getResponseString - IOException", e);
					in = null;
				}
			}
		}
	}

	/**
	 * @param request
	 * @return
	 */
	public static String getClientAddress(final HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * REST: File transfer
	 */
	public static ServiceCallResult issueMultipartRequest(String service,
			Map<String, String> headers, Map<String, String> dataMap,
			SimpleFileObject... files) throws IOException {
		if (dataMap == null && files == null) {
			throw new NullPointerException(
					"! issueMultipartRequest:: Nothing to post");
		}

		MultipartEntity reqEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		if (dataMap != null) {
			for (Map.Entry<String, String> entry : dataMap.entrySet()) {
				reqEntity.addPart(entry.getKey(),
						new StringBody(entry.getValue(), Consts.UTF_8));
			}
		}

		if (files != null) {
			for (SimpleFileObject file : files) {
				reqEntity.addPart(file.getFilename(), file);
			}
		}

		HttpPost httpPost = new HttpPost(service);
		// Set header
		setHeader(httpPost, headers);
		httpPost.setEntity(reqEntity);

		return execute(httpPost);
	}

	/**
	 * REST: File transfer
	 */
	public static ServiceCallResult issueMultipartRequest(String service,
			Map<String, String> headers, Map<String, String> dataMap,
			InputStreamFileObject... files) throws IOException {
		if (dataMap == null && files == null) {
			throw new NullPointerException(
					"! issueMultipartRequest:: Nothing to post");
		}

		MultipartEntity reqEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		if (dataMap != null) {
			for (Map.Entry<String, String> entry : dataMap.entrySet()) {
				reqEntity.addPart(entry.getKey(),
						new StringBody(entry.getValue(), Consts.UTF_8));
			}
		}

		if (files != null) {
			for (InputStreamFileObject file : files) {
				reqEntity.addPart(file.getFilename(), file);
			}
		}

		HttpPost httpPost = new HttpPost(service);
		// Set header
		setHeader(httpPost, headers);
		httpPost.setEntity(reqEntity);

		return execute(httpPost);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SoapConfigBean soapConfigBean = new SoapConfigBean();
		soapConfigBean.setPackages("com.oracle.oda.ext.xml");
		String soap = soapConfigBean.findsoap("LIST_PROJECTS_SUMMARY");
		LOGGER.info(soap);

		Map<String, String> headers = new HashMap<String, String>();
		String authString = CodecBase64.encode("sangjin.lee:Dhipoc1#");
		headers.put("Authorization", "Basic " + authString);

		ServiceCallResult result = HttpUtil.issueSoapRequest(
				"https://ucf5-fap0243-prj.oracledemos.com/pjfProjectDefinition/ProjectDefinitionPublicServiceV2?invoke=",
				headers, soap);
		LOGGER.info("*** RESULT >>>", result);
	}
}
