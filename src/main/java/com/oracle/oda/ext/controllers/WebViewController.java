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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oda.ext.domains.WebViewParams;
import com.oracle.oda.ext.services.WebViewParamsService;
import com.oracle.oda.ext.utils.StringUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.controllers
 * 
 *  File Name       : WebViewController.java
 * 
 *  Creation Date   : 2020年1月2日
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
@RequestMapping("/wv")
public class WebViewController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WebViewController.class);

	@Autowired
	private WebViewParamsService svc;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> save(@RequestBody WebViewParams o) {
		LOGGER.info("*** Got save request: " + o);
		JSONObject resp = new JSONObject();
		String uuid = StringUtil.uuid();
		o.setId(uuid);
		svc.insert(o);
		resp.put("ID", uuid);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	@RequestMapping(value = "/params/{id}", method = RequestMethod.GET)
	public ResponseEntity<WebViewParams> get(@PathVariable("id") String id) {
		LOGGER.info("*** Got get request: " + id);
		WebViewParams p = svc.get(id);
		return ResponseEntity.status(HttpStatus.OK).body(p);
	}
}
