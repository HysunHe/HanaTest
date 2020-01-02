/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oda.ext.dao.WebViewParamsMapper;
import com.oracle.oda.ext.domains.WebViewParams;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : WebViewParamsService.java
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
@Service
public class WebViewParamsService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WebViewParamsService.class);

	@Autowired
	private WebViewParamsMapper mapper;

	public WebViewParams get(String id) {
		try {
			return mapper.get(id);
		} catch (Exception e) {
			LOGGER.error("!!! get WebViewParams", e);
			throw new ApplicationException(e);
		}
	}

	public void insert(WebViewParams o) throws ApplicationException {
		LOGGER.info("*** Inserting WebViewParams: " + o);
		try {
			mapper.insert(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving WebViewParams: " + o, e);
			throw new ApplicationException(e);
		}
	}
}
