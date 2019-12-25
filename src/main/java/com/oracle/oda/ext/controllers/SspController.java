/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.controllers;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oda.ext.domains.CpmTransaction;
import com.oracle.oda.ext.domains.LocalGlnUser;
import com.oracle.oda.ext.dto.JsonResponse;
import com.oracle.oda.ext.services.LocalGlnUserService;
import com.oracle.oda.ext.services.TransactionService;
import com.oracle.oda.ext.utils.DateUtil;
import com.oracle.oda.ext.utils.GlnApiUtil;
import com.oracle.oda.ext.utils.StringUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.controllers
 * 
 *  File Name       : SspController.java
 * 
 *  Creation Date   : 2019年12月24日
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
@RestController
@RequestMapping("/cpm")
public class SspController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SspController.class);

	@Autowired
	private LocalGlnUserService glnUserSvc;

	@Autowired
	private TransactionService txSvc;

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> ping() {
		LOGGER.info("*** Ping the ssp proxy server ...");
		return JsonResponse.inst("OK", HttpStatus.OK, "Ack").toResponseEntity();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> approvePayment(
			@RequestBody JSONObject o) {
		LOGGER.info("*** Got approvePayment request: " + o);
		// {
		// "guid": "TOSSKRGCX00000110",
		// "glnTxNo": "201912190419058570806789535744",
		// "reqTxDateTime": "20191219131905",
		// "payCode": "30183959813002",
		// "paymentNo": "TSI-PayMentNo20191219-Test03",
		// "amount": "120",
		// "won": "1315",
		// "exchangeRate": "10.958333",
		// "currencyCode": "KRW",
		// "receiptQR": "4|GN|TSI-PayMentNo20191219-Test03||MTRj",
		// "settlementDate": "20191220",
		// "apiKey": "sk_gln_4Q2kz1R8Wya0kz1bgzvW"
		// }
		JSONObject resp = new JSONObject();
		String guid = (String) o.get("guid");
		String glnTxNo = (String) o.get("glnTxNo");
		String payCode = (String) o.get("payCode");
		String paymentNo = (String) o.get("paymentNo");
		String amount = (String) o.get("amount");
		String exchangeRate = (String) o.get("exchangeRate");
		String currencyCode = (String) o.get("currencyCode");

		if (StringUtil.isBlank(guid) || StringUtil.isBlank(glnTxNo)
				|| StringUtil.isBlank(payCode) || StringUtil.isBlank(paymentNo)
				|| StringUtil.isBlank(amount)
				|| StringUtil.isBlank(exchangeRate)
				|| StringUtil.isBlank(currencyCode)) {
			resp.put("ResMsg", "Missing required parameter(s).");
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		if (!StringUtil.isDouble(amount)) {
			resp.put("ResMsg", "Parameter format error: amount - " + amount);
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		// {
		// "resultType": "SUCCESS",
		// "status": "Completed",
		// "glnTxNo": "201912200715390987153813291013",
		// "resTxDateTime": "20191220161545"
		// }
		final String resTxDateTime = DateUtil.date2String(DateUtil.now(),
				"yyyyMMddHHmmss");
		final String status = "Completed";
		resp.put("resultType", "SUCCESS");
		resp.put("status", status);
		resp.put("glnTxNo", glnTxNo);
		resp.put("resTxDateTime", resTxDateTime);

		CpmTransaction tx = new CpmTransaction();
		tx.setReqOrgCode(guid);
		tx.setGlnTxNo(glnTxNo);
		tx.setPayCode(payCode);
		tx.setTxAmt(Float.valueOf(amount));
		tx.setTxExRate(Float.valueOf(exchangeRate));
		tx.setTxCur(currencyCode);
		tx.setStatus(status);
		tx.setApproveDateTime(resTxDateTime);
		txSvc.insert(tx);

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gencode", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> genCodeContent() {
		LOGGER.info("*** Got genCodeContent request ***");
		String authId = GlnApiUtil.REQ_ORG_CODE_GSKOAA;
		LocalGlnUser glnUser = glnUserSvc.get(authId);
		if (glnUser == null) {
			String uuid = GlnApiUtil.createUUID(authId);
			glnUser = new LocalGlnUser(authId, uuid);
			glnUserSvc.insert(glnUser);
		}
		LOGGER.info("*** Generated LOCALGLN_UUID: " + glnUser);
		if (StringUtil.isBlank(glnUser.getLocalGlnUuid())) {
			JSONObject resp = new JSONObject();
			resp.put("ResMsg", "Cannot get LOCALGLN_UUID!");
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		JSONObject resp = GlnApiUtil.genCodeContent(glnUser.getLocalGlnUuid(),
				authId);
		LOGGER.info("*** Pay Code: " + resp.get("PAY_CODE"));
		LOGGER.info("*** QR Code: " + resp.get("QR_CODE"));
		LOGGER.info("*** BAR Code: " + resp.get("BAR_CODE"));
		LOGGER.info("*** VALID SECOND: " + resp.get("VALID_SECOND"));

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
}
