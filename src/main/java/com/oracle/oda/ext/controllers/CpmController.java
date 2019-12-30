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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oda.ext.domains.CpmTransaction;
import com.oracle.oda.ext.domains.LocalGlnUser;
import com.oracle.oda.ext.domains.ReqOrg;
import com.oracle.oda.ext.dto.JsonResponse;
import com.oracle.oda.ext.services.LocalGlnUserService;
import com.oracle.oda.ext.services.ReqOrgService;
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
public class CpmController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CpmController.class);

	@Autowired
	private ReqOrgService orgSvc;

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
		// { // Sample Request Payload
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

		// { // Sample Response Payload
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
		CpmTransaction txOrig = txSvc.get(glnTxNo, "Generated");
		LocalGlnUser user = glnUserSvc.get(txOrig.getUserId());
		tx.setUserId(txOrig.getUserId());
		tx.setOrigBalance(user.getBalance());
		tx.setReqOrgCode(guid);
		tx.setGlnTxNo(glnTxNo);
		tx.setPayCode(payCode);
		tx.setTxAmt(Float.valueOf(amount));
		tx.setNewBalance(tx.getOrigBalance() - tx.getTxAmt());
		tx.setTxExRate(Float.valueOf(exchangeRate));
		tx.setTxCur(currencyCode);
		tx.setStatus(status);
		tx.setApproveDateTime(resTxDateTime);
		txSvc.insert(tx);

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gencode", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> genCodeContent(
			@RequestParam(value = "userId", defaultValue = "OAA00018") String userId) {
		LOGGER.info("*** Got genCodeContent request ***" + userId);
		LocalGlnUser user = glnUserSvc.get(userId);
		LOGGER.info("*** Got user " + user);
		if (user == null) {
			JSONObject resp = new JSONObject();
			resp.put("ResMsg", "Invalid user ID: " + userId);
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		ReqOrg org = orgSvc.get(user.getOrgCode());
		LOGGER.info("*** Got org " + org);
		if (org == null) {
			JSONObject resp = new JSONObject();
			resp.put("ResMsg", "Invalid REQ ORG: " + user.getOrgCode());
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		if (StringUtil.isBlank(user.getLocalGlnUuid())) {
			String uuid = GlnApiUtil.createUUID(org.getOrgCode(),
					org.getAuthSecret());
			user.setLocalGlnUuid(uuid);
			glnUserSvc.updateGlnUuid(userId, uuid);
			LOGGER.info("*** Generated LOCALGLN_UUID: " + user);
		}

		if (StringUtil.isBlank(user.getLocalGlnUuid())) {
			JSONObject resp = new JSONObject();
			resp.put("ResMsg", "Cannot get LOCALGLN_UUID!");
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		JSONObject payload = GlnApiUtil.genCodeContent(user.getLocalGlnUuid(),
				org.getOrgCode(), org.getAuthSecret());
		if (payload == null) {
			JSONObject resp = new JSONObject();
			resp.put("ResMsg", "Error generate qr/bar code!");
			resp.put("Status", HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}

		JSONObject body = (JSONObject) payload.get("GLN_BODY");
		JSONObject resp = new JSONObject();
		resp.put("PAY_CODE", body.get("PAY_K"));
		resp.put("BAR_CODE", body.get("PAY_K_BAR"));
		resp.put("QR_CODE", body.get("PAY_K_QR"));
		resp.put("VALID_SECOND", body.get("VALID_SECOND"));

		LOGGER.info("*** Pay Code: " + resp.get("PAY_CODE"));
		LOGGER.info("*** QR Code: " + resp.get("QR_CODE"));
		LOGGER.info("*** BAR Code: " + resp.get("BAR_CODE"));
		LOGGER.info("*** VALID SECOND: " + resp.get("VALID_SECOND"));

		JSONObject header = (JSONObject) payload.get("GLN_HEADER");

		CpmTransaction tx = new CpmTransaction();
		tx.setUserId(userId);
		tx.setReqOrgCode(user.getLocalGlnUuid());
		tx.setGlnTxNo((String) header.get("GLN_TX_NO"));
		tx.setQrCode((String) resp.get("QR_CODE"));
		tx.setBarCode((String) resp.get("BAR_CODE"));
		tx.setReqOrgTxDate(String.valueOf(header.get("REQ_ORG_TX_DATE")));
		tx.setReqOrgTxTime(String.valueOf(header.get("REQ_ORG_TX_TIME")));
		tx.setValidSecond(
				Integer.valueOf(String.valueOf(resp.get("VALID_SECOND"))));
		tx.setOrigBalance(user.getBalance());
		tx.setNewBalance(user.getBalance());
		tx.setStatus("Generated");
		txSvc.insert(tx);

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
}
