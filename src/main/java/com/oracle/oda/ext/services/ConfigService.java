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
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oda.ext.constants.Constants;
import com.oracle.oda.ext.dao.ConfigMapper;
import com.oracle.oda.ext.domains.Config;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : ConfigService.java
 * 
 *  Creation Date   : 2019年2月19日
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
public class ConfigService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);

	@Autowired
	private ConfigMapper configMapper;

	@Transactional
	public void replaceSystemSettings(List<Config> list) throws ApplicationException {
		try {
			configMapper.deleteKind(Constants.CONFIG_TYPE_SYSTEM);
			configMapper.insertAll(list);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving config(list).", e);
			throw new ApplicationException(e);
		}
	}

	public List<Config> listByKind(String kind) {
		try {
			return configMapper.listConfigByKind(kind);
		} catch (Exception e) {
			LOGGER.error("!!! listByKind", e);
			throw new ApplicationException(e);
		}
	}

	public Config getProperty(String kind, String key) {
		try {
			return configMapper.getProperty(kind, key);
		} catch (Exception e) {
			LOGGER.error("!!! getProperty", e);
			throw new ApplicationException(e);
		}
	}
}
