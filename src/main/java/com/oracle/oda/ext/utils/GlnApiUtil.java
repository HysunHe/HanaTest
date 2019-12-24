/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oracle.oda.ext.exceptions.ApplicationException;

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
	public static final String REQ_ORG_CODE = "GSKOAA";
	public static final String COUNTRY_CODE = "AA";
	public static final String CURRENCY_CODE = "AAD";
	public static final String ALGORITHM = "HmacSHA256";
	public static final String DATEFORMAT = "yyyyMMddHHmmss";
	public static final String AUTH_SECRET = "3342504754796D56567A746E797A7953324837476F456F634B61423538463344";

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

	public static Map<String, String> getGenericHttpHeader(Date date) {
		String timestamp = String.valueOf(date.getTime());
		String authorization = generateGLNAuthnToken(REQ_ORG_CODE, AUTH_SECRET,
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
	public static JSONObject getGenericGlnHeader(Date date) {
		String txDate = DateUtil.date2String(date, "yyyyMMdd");
		String txTime = DateUtil.date2String(date, "HHmmss");
		String txNo = txDate + "0" + String.valueOf(new Random()
				.ints(10000000, 100000000).limit(1).findFirst().getAsInt());
		JSONObject o1 = new JSONObject();
		o1.put("REQ_ORG_CODE", GlnApiUtil.REQ_ORG_CODE);
		o1.put("REQ_ORG_TX_DATE", txDate);
		o1.put("REQ_ORG_TX_TIME", txTime);
		o1.put("REQ_ORG_TX_NO", txNo);
		o1.put("GLN_TX_NO", "");
		o1.put("RES_ORG_TX_NO", "");
		o1.put("RES_CODE", "");
		o1.put("REQ_ORG_AREA", "");
		return o1;
	}

	public static void main(String[] args) {
		// String authId = "GSKOUP";
		// String authSecret =
		// "6D386E4B3475417430725379656869747275304838413079764279536D6E764E";
		// header 1.
		// set httpheader name : Timestamp
		// String timestamp = String.valueOf(System.currentTimeMillis());

		// header 2.
		// set httpheader name : Authorization
		String authorization = generateGLNAuthnToken(REQ_ORG_CODE, AUTH_SECRET,
				String.valueOf(System.currentTimeMillis()));

		System.out.printf("%s\n", authorization);

		// header 3.
		// set httpheader name : X-Auth-Type = GLN
		// header 4.
		// set httpheader name : Accept = application/json
		// header 5.
		// set httpheader name : Content-Type = application/json
	}
}
