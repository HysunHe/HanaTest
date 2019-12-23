/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oda.ext.dao.TopicMapper;
import com.oracle.oda.ext.domains.Topic;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway-springboot
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : TopicService.java
 * 
 *  Creation Date   : 2019年2月22日
 * 
 *  Author          : hysun
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class TopicService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicService.class);

	@Autowired
	private TopicMapper mapper;

	public void insert(Topic o) throws ApplicationException {
		LOGGER.info("*** Inserting topic: " + o);
		try {
			mapper.insert(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving Topic: " + o, e);
			throw new ApplicationException(e);
		}
	}

	public void update(Topic o) throws ApplicationException {
		o.updateTimestamp();
		try {
			mapper.update(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error updating Topic: " + o, e);
			throw new ApplicationException(e);
		}
	}

	public List<Topic> list(String category, int offset, int limit) {
		try {
			return mapper.list(category, offset, limit);
		} catch (Exception e) {
			LOGGER.error("!!! list topic: " + category, e);
			throw new ApplicationException(e);
		}
	}

	public void deleteAll() {
		try {
			mapper.deleteAll();
		} catch (Exception e) {
			LOGGER.error("!!! deleteAll topic", e);
			throw new ApplicationException(e);
		}
	}
}
