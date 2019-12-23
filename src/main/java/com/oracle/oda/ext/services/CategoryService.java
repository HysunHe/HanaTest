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

import com.oracle.oda.ext.dao.CategoryMapper;
import com.oracle.oda.ext.domains.Category;
import com.oracle.oda.ext.exceptions.ApplicationException;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway-springboot
 * 
 *  Package Name    : com.oracle.oda.ext.services
 * 
 *  File Name       : CategoryService.java
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
public class CategoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryMapper categoryMapper;

	public Category save(Category o) throws ApplicationException {
		o.updateTimestamp();
		LOGGER.info("*** Inserting Category: " + o);
		try {
			categoryMapper.save(o);
			return o;
		} catch (Exception e) {
			LOGGER.error("!!! Error saving Category: " + o, e);
			throw new ApplicationException(e);
		}
	}

	public List<Category> listCategories() {
		try {
			return categoryMapper.list();
		} catch (Exception e) {
			LOGGER.error("!!! listCategories", e);
			throw new ApplicationException(e);
		}
	}

	public Category get(String name) {
		try {
			return categoryMapper.get(name);
		} catch (Exception e) {
			LOGGER.error("!!! get category", e);
			throw new ApplicationException(e);
		}
	}

	public void deleteAll() {
		try {
			categoryMapper.deleteAll();
		} catch (Exception e) {
			LOGGER.error("!!! deleteAll category", e);
			throw new ApplicationException(e);
		}
	}
}
