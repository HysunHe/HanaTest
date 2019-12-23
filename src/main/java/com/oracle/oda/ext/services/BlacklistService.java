/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oda.ext.dao.BlacklistMapper;
import com.oracle.oda.ext.domains.Blacklist;
import com.oracle.oda.ext.exceptions.ApplicationException;
import com.oracle.oda.ext.utils.DateUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : BlacklistService.java
 * 
 *  Creation Date   : 2019年2月20日
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
public class BlacklistService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlacklistService.class);

	@Autowired
	private BlacklistMapper blacklistMapper;

	public void save(Blacklist o) throws ApplicationException {
		o.updateTimestamp();
		try {
			blacklistMapper.save(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving blacklist: " + o, e);
			throw new ApplicationException(e);
		}
	}

	public List<Blacklist> listBlacklist(int offset, int limit) {
		try {
			return blacklistMapper.listBlacklist(offset, limit);
		} catch (Exception e) {
			LOGGER.error("!!! listBlacklist", e);
			throw new ApplicationException(e);
		}
	}

	public Blacklist get(String userId) {
		try {
			return blacklistMapper.get(userId);
		} catch (Exception e) {
			LOGGER.error("!!! get", e);
			throw new ApplicationException(e);
		}
	}

	public void unblock(String userId) throws ApplicationException {
		try {
			blacklistMapper.unblock(userId);
		} catch (Exception e) {
			LOGGER.error("!!! Error unblock blacklist: " + userId, e);
			throw new ApplicationException(e);
		}
	}

	public List<Blacklist> queryByTime(Date from, Date to) {
		try {
			return blacklistMapper.queryByTime(DateUtil.dateToTs(from), DateUtil.dateToTs(to));
		} catch (Exception e) {
			LOGGER.error("!!! queryByTime", e);
			throw new ApplicationException(e);
		}
	}
}
