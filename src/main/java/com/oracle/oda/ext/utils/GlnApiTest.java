/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.oracle.oda.ext.utils.http.ServiceCallClient;
import com.oracle.oda.ext.utils.http.ServiceCallResult;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.utils
 * 
 *  File Name       : GlnApiTest.java
 * 
 *  Creation Date   : 2019年12月19日
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
public class GlnApiTest {
	static {
		ServiceCallClient.setHttpsPort(9000);
	}

	@SuppressWarnings("unchecked")
	private static String createUUID() { // Cache the uuid per user.
		String url = GlnApiUtil.GLN_API_BASE + "members/uuid-creation";
		Date date = new Date();

		Map<String, String> headers = GlnApiUtil.getGenericHttpHeader(date);
		JSONObject o2 = new JSONObject();
		o2.put("LOCALGLN_TEMP_UUID", String.valueOf(new Random()
				.ints(10000000, 100000000).limit(1).findFirst().getAsInt()));

		JSONObject data = new JSONObject();
		data.put("GLN_HEADER", GlnApiUtil.getGenericGlnHeader(date));
		data.put("GLN_BODY", o2);

		try {
			ServiceCallResult result = HttpUtil.issuePostRequest(url, headers,
					data);
			String jsonString = result.getResponseString();
			JSONObject json = (JSONObject) JSONValue.parse(jsonString);

			System.out.println("GLN Header >>>");
			JSONObject header = (JSONObject) json.get("GLN_HEADER");
			System.out.println(header);

			System.out.println("GLN Body >>>");
			JSONObject body = (JSONObject) json.get("GLN_BODY");
			System.out.println(body);

			return (String) body.get("LOCALGLN_UUID");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> genQrCodeContent(String uuid) {
		String url = GlnApiUtil.GLN_API_BASE_V2 + "payments/cpm/qr-generation";
		Date date = new Date();
		Map<String, String> headers = GlnApiUtil.getGenericHttpHeader(date);

		JSONObject o2 = new JSONObject();
		o2.put("LOCALGLN_UUID", uuid);
		o2.put("RCVR_NAT_CODE", "JP");
		// o2.put("RCVR_LOCALGLN_CODE", "NSTSJP");

		JSONObject data = new JSONObject();
		data.put("GLN_HEADER", GlnApiUtil.getGenericGlnHeader(date));
		data.put("GLN_BODY", o2);

		try {
			ServiceCallResult result = HttpUtil.issuePostRequest(url, headers,
					data);
			String jsonString = result.getResponseString();
			JSONObject json = (JSONObject) JSONValue.parse(jsonString);

			System.out.println("GLN Header >>>");
			JSONObject header = (JSONObject) json.get("GLN_HEADER");
			System.out.println(header);

			System.out.println("GLN Body >>>");
			JSONObject body = (JSONObject) json.get("GLN_BODY");
			System.out.println(body);

			Map<String, Object> retMap = new HashMap<>();
			retMap.put("PAY_CODE", body.get("PAY_K"));
			retMap.put("BAR_CODE", body.get("PAY_K_BAR"));
			retMap.put("QR_CODE", body.get("PAY_K_QR"));
			retMap.put("VALID_SECOND", body.get("VALID_SECOND"));

			return retMap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("*** Creating UUID ***");
		String uuid = createUUID();
		System.out.println("*** LOCALGLN_UUID: " + uuid);

		System.out.println("*** Generating QR Code Content ***");
		Map<String, Object> map = genQrCodeContent(uuid);
		System.out.println("*** Pay Code: " + map.get("PAY_CODE"));
		System.out.println("*** QR Code: " + map.get("QR_CODE"));
		System.out.println("*** BAR Code: " + map.get("BAR_CODE"));
		System.out.println("*** VALID SECOND: " + map.get("VALID_SECOND"));
	}
}
