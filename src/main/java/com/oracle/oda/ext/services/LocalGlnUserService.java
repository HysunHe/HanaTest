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

import com.oracle.oda.ext.dao.LocalGlnUserMapper;
import com.oracle.oda.ext.domains.LocalGlnUser;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : LocalGlnUserService.java
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
@Service
public class LocalGlnUserService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LocalGlnUserService.class);

	@Autowired
	private LocalGlnUserMapper mapper;

	public LocalGlnUser get(String orgCode) {
		try {
			return mapper.get(orgCode);
		} catch (Exception e) {
			LOGGER.error("!!! get LocalGlnUser", e);
			throw new ApplicationException(e);
		}
	}

	public void insert(LocalGlnUser o) throws ApplicationException {
		LOGGER.info("*** Inserting LocalGlnUser: " + o);
		try {
			mapper.insert(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving LocalGlnUser: " + o, e);
			throw new ApplicationException(e);
		}
	}
}
