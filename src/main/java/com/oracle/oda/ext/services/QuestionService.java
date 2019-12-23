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

import com.oracle.oda.ext.dao.QuestionMapper;
import com.oracle.oda.ext.domains.Question;
import com.oracle.oda.ext.exceptions.ApplicationException;
import com.oracle.oda.ext.utils.DateUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway-springboot
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : QuestionService.java
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
public class QuestionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

	@Autowired
	private QuestionMapper mapper;

	public void save(Question o) throws ApplicationException {
		o.updateTimestamp();
		try {
			mapper.save(o);
		} catch (Exception e) {
			LOGGER.error("!!! Error saving Question: " + o, e);
			throw new ApplicationException(e);
		}
	}

	public List<Question> list(int offset, int limit) {
		try {
			return mapper.list(offset, limit);
		} catch (Exception e) {
			LOGGER.error("!!! list questions", e);
			throw new ApplicationException(e);
		}
	}

	public List<Question> queryByTime(Date from, Date to) {
		try {
			return mapper.queryByTime(DateUtil.dateToTs(from), DateUtil.dateToTs(to));
		} catch (Exception e) {
			LOGGER.error("!!! queryByTime", e);
			throw new ApplicationException(e);
		}
	}
}
