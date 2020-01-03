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

import com.oracle.oda.ext.dao.CpmTransactionMapper;
import com.oracle.oda.ext.domains.CpmTransaction;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : TransactionService.java
 * 
 *  Creation Date   : 2019年12月25日
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
public class TransactionService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TransactionService.class);

	@Autowired
	private CpmTransactionMapper mapper;

	public CpmTransaction get(String payCode, String status) {
		try {
			return mapper.get(payCode, status);
		} catch (Exception e) {
			LOGGER.error("!!! get CpmTransaction", e);
			throw new ApplicationException(e);
		}
	}

	public void insert(CpmTransaction o) throws ApplicationException {
		LOGGER.info("*** Inserting CpmTransaction: " + o);
		try {
			mapper.insert(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving CpmTransaction: " + o, e);
			throw new ApplicationException(e);
		}
	}
}
