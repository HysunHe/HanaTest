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

import com.oracle.oda.ext.dao.ReqOrgMapper;
import com.oracle.oda.ext.domains.ReqOrg;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 *<PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.domains
 * 
 *  File Name       : ReqOrgService.java
 * 
 *  Creation Date   : 2019年12月30日
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/
@Service
public class ReqOrgService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReqOrgService.class);

	@Autowired
	private ReqOrgMapper mapper;

	public ReqOrg get(String orgCode) {
		try {
			return mapper.get(orgCode);
		} catch (Exception e) {
			LOGGER.error("!!! get ReqOrg", e);
			throw new ApplicationException(e);
		}
	}

	public void insert(ReqOrg o) throws ApplicationException {
		LOGGER.info("*** Inserting ReqOrg: " + o);
		try {
			mapper.insert(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving ReqOrg: " + o, e);
			throw new ApplicationException(e);
		}
	}
}
