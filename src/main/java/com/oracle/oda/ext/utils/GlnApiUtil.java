/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oracle.oda.ext.exceptions.ApplicationException;
import com.oracle.oda.ext.utils.http.ServiceCallClient;
import com.oracle.oda.ext.utils.http.ServiceCallResult;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.utils
 * 
 *  File Name       : GlnApiUtil.java
 * 
 *  Creation Date   : 2019年12月18日
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
public final class GlnApiUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlnApiUtil.class);

	public static final String GLN_API_BASE = "https://test-api.glnpay.com:9000/api/v1/";
	public static final String GLN_API_BASE_V2 = "https://test-api.glnpay.com:9000/api/v2/";
	public static final String REQ_ORG_CODE_GSKOAA = "GSKOAA";
	public static final String COUNTRY_CODE = "AA";
	public static final String CURRENCY_CODE = "AAD";
	public static final String ALGORITHM = "HmacSHA256";
	public static final String DATEFORMAT = "yyyyMMddHHmmss";
	public static final String AUTH_SECRET = "3342504754796D56567A746E797A7953324837476F456F634B61423538463344";

	static {
		ServiceCallClient.setHttpsPort(9000);
	}

	public static String generateGLNAuthnToken(String authId, String authSecret,
			String timestamp) {
		String hashValue = generateHMACSHA256(timestamp, authSecret);
		String authorization = authId + ":" + hashValue;
		return "Basic "
				+ Base64.getEncoder().encodeToString(authorization.getBytes());
	}

	public static String generateHMACSHA256(String in, String apikey) {
		byte[] key = hexaStringDecode(apikey);
		SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
		Mac mac;
		try {
			mac = Mac.getInstance(ALGORITHM);
			mac.init(keySpec);
			byte[] out = mac.doFinal(in.getBytes("UTF-8"));
			return hexaStringEncode(out);
		} catch (NoSuchAlgorithmException | InvalidKeyException
				| IllegalStateException | UnsupportedEncodingException e) {
			LOGGER.error("generateHMACSHA256", e);
			throw new ApplicationException("generateHMACSHA256 error", e);
		}
	}

	public static String hexaStringEncode(byte[] in) {
		return DatatypeConverter.printHexBinary(in);
	}

	public static byte[] hexaStringDecode(String in) {
		return DatatypeConverter.parseHexBinary(in);
	}

	public static Map<String, String> getGenericHttpHeader(Date date,
			String client) {
		String timestamp = String.valueOf(date.getTime());
		String authorization = generateGLNAuthnToken(client, AUTH_SECRET,
				timestamp);
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json;charset=utf-8");
		headers.put("Accept-Language", "en");
		headers.put("X-Auth-Type", "GLN");
		headers.put("guid", "");
		headers.put("Timestamp", timestamp);
		headers.put("Authorization", authorization);
		return headers;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject getGenericGlnHeader(Date date, String client) {
		String txDate = DateUtil.date2String(date, "yyyyMMdd");
		String txTime = DateUtil.date2String(date, "HHmmss");
		String txNo = txDate + "0" + String.valueOf(new Random()
				.ints(10000000, 100000000).limit(1).findFirst().getAsInt());
		JSONObject o1 = new JSONObject();
		o1.put("REQ_ORG_CODE", client);
		o1.put("REQ_ORG_TX_DATE", txDate);
		o1.put("REQ_ORG_TX_TIME", txTime);
		o1.put("REQ_ORG_TX_NO", txNo);
		o1.put("GLN_TX_NO", "");
		o1.put("RES_ORG_TX_NO", "");
		o1.put("RES_CODE", "");
		o1.put("REQ_ORG_AREA", "");
		return o1;
	}

	@SuppressWarnings("unchecked")
	public static String createUUID(String authId) {
		String url = GlnApiUtil.GLN_API_BASE + "members/uuid-creation";
		Date date = new Date();

		Map<String, String> headers = GlnApiUtil.getGenericHttpHeader(date,
				authId);
		JSONObject o2 = new JSONObject();
		o2.put("LOCALGLN_TEMP_UUID", String.valueOf(new Random()
				.ints(10000000, 100000000).limit(1).findFirst().getAsInt()));

		JSONObject data = new JSONObject();
		data.put("GLN_HEADER", GlnApiUtil.getGenericGlnHeader(date, authId));
		data.put("GLN_BODY", o2);

		try {
			ServiceCallResult result = HttpUtil.issuePostRequest(url, headers,
					data);
			String jsonString = result.getResponseString();
			JSONObject json = (JSONObject) JSONValue.parse(jsonString);

			LOGGER.info("GLN Header >>>");
			JSONObject header = (JSONObject) json.get("GLN_HEADER");
			if (header == null) {
				LOGGER.error("createUUID null header: " + json.get("status"));
				return null;
			}
			LOGGER.info(header.toString());

			LOGGER.info("GLN Body >>>");
			JSONObject body = (JSONObject) json.get("GLN_BODY");
			if (body == null) {
				LOGGER.error("createUUID null body: " + json.get("status"));
				return null;
			}
			LOGGER.info(body.toString());

			return (String) body.get("LOCALGLN_UUID");
		} catch (IOException e) {
			LOGGER.error("createUUID error: " + e.toString(), e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static JSONObject genCodeContent(String uuid, String authId) {
		String url = GlnApiUtil.GLN_API_BASE_V2 + "payments/cpm/qr-generation";
		Date date = new Date();
		Map<String, String> headers = GlnApiUtil.getGenericHttpHeader(date,
				authId);

		JSONObject o2 = new JSONObject();
		o2.put("LOCALGLN_UUID", uuid);
		o2.put("RCVR_NAT_CODE", "JP");
		// o2.put("RCVR_LOCALGLN_CODE", "NSTSJP");

		JSONObject data = new JSONObject();
		data.put("GLN_HEADER", GlnApiUtil.getGenericGlnHeader(date, authId));
		data.put("GLN_BODY", o2);

		try {
			ServiceCallResult result = HttpUtil.issuePostRequest(url, headers,
					data);
			String jsonString = result.getResponseString();
			JSONObject json = (JSONObject) JSONValue.parse(jsonString);

			LOGGER.info("GLN Header >>>");
			JSONObject header = (JSONObject) json.get("GLN_HEADER");
			if (header == null) {
				LOGGER.error("createUUID null header: " + json.get("status"));
				return null;
			}
			LOGGER.info(header.toString());

			LOGGER.info("GLN Body >>>");
			JSONObject body = (JSONObject) json.get("GLN_BODY");
			if (body == null) {
				LOGGER.error("createUUID null body: " + json.get("status"));
				return null;
			}
			LOGGER.info(body.toString());

			JSONObject retMap = new JSONObject();
			retMap.put("PAY_CODE", body.get("PAY_K"));
			retMap.put("BAR_CODE", body.get("PAY_K_BAR"));
			retMap.put("QR_CODE", body.get("PAY_K_QR"));
			retMap.put("VALID_SECOND", body.get("VALID_SECOND"));

			return retMap;
		} catch (IOException e) {
			LOGGER.error("genCodeContent error: " + e.toString(), e);
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("*** Creating UUID ***");
		String uuid = GlnApiUtil.createUUID(GlnApiUtil.REQ_ORG_CODE_GSKOAA);
		System.out.println("*** LOCALGLN_UUID: " + uuid);

		System.out.println("*** Generating QR Code Content ***");
		JSONObject map = GlnApiUtil.genCodeContent(uuid,
				GlnApiUtil.REQ_ORG_CODE_GSKOAA);
		System.out.println("*** Pay Code: " + map.get("PAY_CODE"));
		System.out.println("*** QR Code: " + map.get("QR_CODE"));
		System.out.println("*** BAR Code: " + map.get("BAR_CODE"));
		System.out.println("*** VALID SECOND: " + map.get("VALID_SECOND"));
	}
}
