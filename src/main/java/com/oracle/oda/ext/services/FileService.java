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

import com.oracle.oda.ext.dao.FileMapper;
import com.oracle.oda.ext.domains.File;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway-springboot
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : FileService.java
 * 
 *  Creation Date   : 2019年2月23日
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
public class FileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileMapper mapper;

	public void save(File o) throws ApplicationException {
		o.updateTimestamp();
		try {
			mapper.save(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving File: " + o, e);
			throw new ApplicationException(e);
		}
	}

	public List<File> query(String commodityId, String batchNumber, int offset, int limit) {
		try {
			return mapper.query(commodityId, batchNumber, offset, limit);
		} catch (Exception e) {
			LOGGER.error("!!! Query files", e);
			throw new ApplicationException(e);
		}
	}
}
