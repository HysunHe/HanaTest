/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.domains;

import java.sql.Timestamp;

import com.oracle.oda.ext.utils.DateUtil;
import com.oracle.oda.ext.utils.StringUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.domains
 * 
 *  File Name       : BaseObject.java
 * 
 *  Creation Date   : 2019年2月18日
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
public abstract class BaseObject {
	protected String id;
	protected Timestamp created_at;
	protected Timestamp updated_at;

	public BaseObject() {
		id = StringUtil.uuid();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the created_at
	 */
	public Timestamp getCreated_at() {
		if (created_at == null) {
			created_at = DateUtil.nowTs();
		}
		return created_at;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the updated_at
	 */
	public Timestamp getUpdated_at() {
		if (updated_at == null) {
			updated_at = DateUtil.nowTs();
		}
		return updated_at;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * Update the updated_at field to be now.
	 */
	public void updateTimestamp() {
		this.updated_at = DateUtil.nowTs();
	}
}
